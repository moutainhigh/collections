Ext.ns('wcm.util.special');
(function(){
	wcm.util.Draggable = function(){};

	/*
	* 如果鼠标离页面的顶部 或者离页面的底部较近，则需要移动滚动条
	*/
	var rollPage = function(x,y,el){
		return;
		var speed = 10;
		// 获取鼠标离页面顶部的距离
		var topDistance = y-document.documentElement.scrollTop;
		// 获取鼠标离页面底部的距离
		var bottomDistance = document.documentElement.clientHeight-topDistance;
		// ========移动滚动条==========
		while (document.documentElement.scrollTop>=0 )
		if(topDistance<50){//如果距离顶部不足50px
			document.documentElement.scrollTop-=3;
		}
		else if(bottomDistance<50){// 如果距离底部不足50px
			document.documentElement.scrollTop+=3;
		}
	}

	Ext.apply(wcm.util.Draggable.prototype, {
		init : function(){
			Ext.EventManager.on(document, 'mousedown', this.handleMouseDown, this);
			Ext.EventManager.on(document, 'mouseover', this.handleMouseOver, this);
		},
		findDragEl : function(e){
		},
		getXY : function(e){
			return [parseInt(e.getPageX(), 10), parseInt(e.getPageY(), 10)];
		},
		handleMouseOver : function(e){
		},
		handleMouseDown : function(e){
			//获取当前单击的元素是否为需要拖动的元素，如果不是，则直接退出
			this.dragEl = this.findDragEl(e);
			if(!this.dragEl) return;

			//重置拖动状态，以便重新执行onDragStart接口
			this.dragging = false;
			wcm.util.Draggable.dragging = false;

			//记录拖动的开始位置及偏移量
			var xy = this.getXY(e);
			this.lastPointer = xy;
			var page = Position.cumulativeOffset(this.dragEl);
			this.deltaX = xy[0] - parseInt(page[0], 10);
			this.deltaY = xy[1] - parseInt(page[1], 10);
			//window.status = xy.join(":")+":"+page.join(":");

			//注册移动相应的事件
			Ext.EventManager.on(document, "mousemove", this.handleMouseMove, this);
			Ext.EventManager.on(document, "mouseup", this.handleMouseUp, this);						
		},
		handleMouseMove : function(e){
			if(!this.dragEl)return;
			//判断鼠标是否有小范围的移动
			var xy = this.getXY(e);
			if(Math.abs(xy[0] - this.lastPointer[0]) < 2 && Math.abs(xy[1] - this.lastPointer[1]) < 2){
				return;
			}
			if(!this.dragging){
				this.dragging = true;
				wcm.util.Draggable.dragging = true;
				this.onDragStart(xy[0], xy[1], e);
			}
			this.onDrag(xy[0], xy[1], e);
			// 如果鼠标离页面的顶部和底部较近，需要移动滚动条的位置
			rollPage(xy[0], xy[1],this.dragEl);
		},
		handleMouseUp : function(e){
			Ext.EventManager.un(document, "mousemove", this.handleMouseMove, this);
			Ext.EventManager.un(document, "mouseup", this.handleMouseUp, this);
			if(this.dragging){
				var xy = this.getXY(e);
				this.onDragEnd(xy[0], xy[1], e);
			}
			this.dragEl = null;		
			this.dragging = false;
			wcm.util.Draggable.dragging = false;
		},
		onDragStart : function(x, y, e){
			//override in subclasss.
		},
		onDrag : function(x, y, e){
			//override in subclasss.
		},
		onDragEnd : function(x, y, e){
			//override in subclasss.
		},
		destroy : function(){
			Ext.EventManager.un(document, "mousedown", this.handleMouseDown, this);
		}
	});
})();


(function(){
	var destPositionHtml = "<div class='wcm-drag-destPosition' id='wcm-drag-destPosition'></div>";

	var dragElCls = 'wcm-drag-cls';

	var getTargets = function(dom){
		var doms = [];
		var columns = document.getElementsByClassName('trs_column', dom);
		for(var index = 0; index < columns.length; index++){
			var column = columns[index];
			var widgets = document.getElementsByClassName('trs-widget', column);
			if(widgets.length > 0){
				for(var j = 0; j < widgets.length; j++){

					//组合资源内部有资源，自身不需要作为判断的容器
					if(Element.hasClassName(Element.first(widgets[j]), 'trs-composite')){
						continue;
					}
					doms.push(widgets[j]);
				}
			}else{
				doms.push(column);
			}
		}

		return doms;
	}

	var getBoxOfColumn = function(column){
		return Element.first(column);
	}

	var inElement = function(x, y, element){
		var page = Position.cumulativeOffset(element);
		if(x < page[0] || y < page[1]) return 0;

		var width = parseInt(element.offsetWidth, 10);
		var height = parseInt(element.offsetHeight, 10);
		if(x > page[0] + width || y > page[1] + height) return 0;

		if(Element.hasClassName(element, 'trs_column')){//空的列
			return 2;
		}

		if(y >= page[1] && y < page[1] + height / 2){//资源前面
			return -1;
		}
		return 1;//资源后面
	}
	/*
	* 拷贝资源实例，并产生新资源实例的解析代码
	*/
	var copyElem = function(el,callback){
		// 发送服务，拷贝资源块实例
		var ui = wcm.special.widget.InstanceMgr;
		ui.copy(el,function(newWidgetInstanceId){
			ui.parse(newWidgetInstanceId,function(sVisualHtml){
				//将获取的内容添加到页面上，不触发afteradd事件
				var sHtml = String.format(
					ui.sInstanceTemplate,
					ui.instanceCls,
					ui.idPrefix,
					newWidgetInstanceId,
					sVisualHtml,
					el.getAttribute("wName") || ""
				);
				new Insertion["Before"]($('temp-widget'), sHtml);
				Element.remove($('temp-widget'));
				var newElem = ui.getInstance(newWidgetInstanceId)
				if(callback)callback(newElem,el);
				ui.fireEvent('afterrefresh', newElem);
			})
		});
		// 不直接调用ui.move方法，由于该方法会触发saveStatus事件
		//ui.move(el,el,"After","copy",callback);
	}
	/*
	* 创建拖动过程中，在布局列中占位的元素，和当前拖动的资源高都相同，宽度自适应
	*/
	var dragStartDoWith = function(dragEl){
		//使当前拖动的资源位置绝对化
		document.body.appendChild(dragEl);
		Element.addClassName(dragEl, dragElCls);
		Element.hide(dragEl);
		Position.clone($('wcm-drag-destPosition'), dragEl);
		Element.show(dragEl);
	}

	wcm.util.special.Dragger = new wcm.util.Draggable();

	Ext.apply(wcm.util.special.Dragger, {
		handleMouseOver : function(e){
			if(this.dragging || wcm.util.Draggable.dragging)
				return;
			this.dragEl = this.findDragEl(e);
			if(!this.dragEl) return;
			this.dragEl.style.cursor="move";
		},
		findDragEl : function(event){
			var dom = Event.element(event.browserEvent);

			if(Element.hasClassName(dom, 'c-box')){
				return null;
			}

			if(Element.hasClassName(dom, 'trs-widget')){
				return null;
			}

			var widget = Element.find(dom, null, 'trs-widget');
			if(widget && !widget.getAttribute("isLocked")){
				return widget;
			}
			if(widget && widget.getAttribute("isLocked")){
				widget.style.cursor="default";
			}
			return null;
		},
		onDragStart : function(x, y, e){
			Event.stop(e.browserEvent);
			var dragEl = this.dragEl;
			var destPosition = $('wcm-drag-destPosition');
			if(!destPosition){
				new Insertion.Before(dragEl, destPositionHtml);
				destPosition = $('wcm-drag-destPosition');
			}
			destPosition.style.height = dragEl.offsetHeight-8+"px";
			//开始拖动时停止资源块的标识
			wcm.special.widget.InstanceUI.unidentify();
			wcm.special.layout.OperUI.unIdentify();
			dragStartDoWith(dragEl);
			// 是否按住了ctrl键，看是否需要复制资源实例
			if(e.browserEvent.ctrlKey){
				//为了防止在复制时产生闪动，首先创建临时的对象，等新的对象创建完成后再删除临时对象
				var tempNode = dragEl.cloneNode(true);
				tempNode.id = "temp-widget";
				// 如果是在资源块的下方进行拖动，则在把临时的资源块添加到上方
				if(y-dragEl.offsetTop-dragEl.offsetHeight/2>0){
					new Insertion.Before(destPosition,tempNode.outerHTML);
				}else{
					new Insertion.After(destPosition,tempNode.outerHTML);
				}
				copyElem(dragEl,function(newEl,srcEl){
					//添加新的资源块时，重新获取所有拖动目标
					wcm.util.special.Dragger.targets = getTargets(Element.first(document.body));
				});
			}else{// 如果没有按住ctrl键，则直接拖动
				this.targets = getTargets(Element.first(document.body));
			}

			// 创建遮罩
			var mask = $("special-mask");
			if(!mask){
				mask = document.createElement("DIV");
				mask.id = "special-mask";
				document.body.appendChild(mask);
			}
			mask.style.display = "block";
		},
		onDrag : function(x, y, e){
			var dragEl = this.dragEl;
			dragEl.style.left = (x - this.deltaX) + "px";
			dragEl.style.top = (y - this.deltaY) + "px";
			//window.status = (x - this.deltaX)+":"+(y - this.deltaY);
			var targets = this.targets;
			// 如果没有目标，直接返回
			if(!targets)return;
			
			for(var index = 0; index < targets.length; index++){
				var target = targets[index];
				var position = inElement(x, y, target);
				if(position){
					//添加移开之处的“单击添加资源节点”
					var destPosition = $('wcm-drag-destPosition');
					if(Element.first(destPosition.parentNode) == Element.last(destPosition.parentNode)){
						new Insertion.Before(destPosition, PageController.getTopWin().sEmptyColumnHtml);
						//new Insertion.Before(destPosition, '<div class="c-empty-column">点击添加资源</div>');
					}
					//重置占位元素的高度
					destPosition.style.height = dragEl.offsetHeight-8+"px";
					if(position == -1){
						var related = target;
						target.parentNode.insertBefore(destPosition, related);
					}else if(position == 1){
						var related = Element.next(target);
						target.parentNode.insertBefore(destPosition, related);
					}else{
						getBoxOfColumn(target).appendChild(destPosition);
					}

					//移除目标之处的“单击添加资源节点”
					//var destPosition = $('wcm-drag-destPosition');					
					var next = Element.next(destPosition);
					if(next && Element.hasClassName(next, 'c-empty-column')){
						//将占位元素的高度设置为“单击添加资源”节点的高度，防止出现高度变化而出现来回闪动的现象
						if(destPosition.offsetHeight<next.offsetHeight)
							destPosition.style.height = next.offsetHeight-8+"px";
						Element.remove(next);
					}
					var previous = Element.previous(destPosition);
					if(previous && Element.hasClassName(previous, 'c-empty-column')){
						//将占位元素的高度设置为“单击添加资源”节点的高度，防止出现高度变化而出现来回闪动的现象
						if(destPosition.offsetHeight<previous.offsetHeight)
							destPosition.style.height = previous.offsetHeight-8+"px";
						Element.remove(previous);
					}
					break;
				}
			}
		},
		onDragEnd : function(x, y, e){
			var destPosition = $('wcm-drag-destPosition');
			destPosition.parentNode.insertBefore(this.dragEl, destPosition);
			Element.removeClassName(this.dragEl, dragElCls);
			this.dragEl.style.width = 'auto';
			this.dragEl.style.height = 'auto';
			this.dragEl.style.zoom = 1;
			this.dragEl = null;
			Element.remove(destPosition);
			this.widgets = null;
			$("special-mask").style.display = "none";
			//防止在复制时，由于targets不为空，而导致拖动元素时不产生占位元素
			this.targets = null;
			//todo...此处应该想办法做事件调用，否则都要找到此处代码进行修改
			PageController.getStateHandler().saveState();
			PageController.refreshTree();
		}
	});
})();

Event.observe(window, 'load', function(){
	wcm.util.special.Dragger.init();

	wcm.special.widget.HightDragger.init();
});



(function(){
	//对资源底部支持拖动改变高度
	wcm.special.widget.HightDragger = new wcm.util.Draggable();

	Ext.apply(wcm.special.widget.HightDragger,{
		minHeight : 50,
		handleMouseOver : function(e){
			if(this.dragging || wcm.util.Draggable.dragging)
				return;

			var dragEl = this.findDragEl(e);

			if(dragEl){
				dragEl.style.cursor = 's-resize';
			}
		},
		findDragEl : function(event){
			var dom = Event.element(event.browserEvent);
			if(Element.hasClassName(dom, 'trs-widget')){
				return dom;
			}
			return null;
		},
		onDragStart : function(x, y, e){
			Event.stop(e.browserEvent);

			this.pwContent = null;//默认为null，兼容不存在p_w_content的情况
			var doms = document.getElementsByClassName('p_w_content', this.dragEl);
			if(doms.length <=0) return;

			this.pwContent = doms[0];
			this.orignHeight = this.pwContent.offsetHeight;
			this.pwContent.style.height = this.orignHeight + 'px';
		},
		onDrag : function(x, y, e){
			if(!this.pwContent) return;

			//计算鼠标移动距离
			var distanceY = y-this.lastPointer[1];

			//如果宽度小于最小的宽度，则不能再缩小
			if(this.orignHeight + distanceY < this.minHeight)
				return;

			this.pwContent.style.height = this.orignHeight+distanceY+"px";
		},
		onDragEnd : function(x, y, e){
			if(!this.pwContent) return;
			var dragEl = this.dragEl;
			var height = 0;
			var paddingTop = parseInt(this.getCssValue(this.pwContent,"paddingTop"))|| 0;
			var paddingBottom = parseInt(this.getCssValue(this.pwContent,"paddingBottom"))|| 0;
			height = this.pwContent.offsetHeight-paddingTop-paddingBottom + 'px';
			dragEl.setAttribute('trs-WHEIGHT', height);
			var sId =  wcm.special.widget.InstanceUI.getInstanceId(dragEl);
			var oParam = {
				WIDGETINSTANCEID:sId,
				"WHEIGHT":height
			}
			delete this.pwContent;
			// 保存模板变量的值
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call('wcm61_visualtemplate', 'saveWidgetInstParameters', oParam, true, function(_trans,json){
				wcm.special.widget.InstanceMgr.fireEvent('afteredit', dragEl);
			});
		},
		//获取css样式文件中的样式值
		getCssValue:function(obj,styleName){
			//如果该属性存在于style[]中，则它最近被设置过(且就是当前的)
			var myObj = typeof obj == "string" ? document.getElementById(obj) : obj;
			if(document.all){  
				return eval("myObj.currentStyle." + styleName);  
			} else {  
				return eval("document.defaultView.getComputedStyle(myObj,null)." + styleName);  
			}
		}

	});
})();





(function(){
	var destPositionHtml = "<div class='wcm-drag-destPosition' id='wcm-drag-destPosition'></div>";

	var dragElCls = 'wcm-drag-cls';

	var getTargets = function(dom){
		var doms = [];
		var columns = document.getElementsByClassName('trs_column', dom);
		for(var index = 0; index < columns.length; index++){
			var column = columns[index];
			var widgets = document.getElementsByClassName('trs-widget', column);
			if(widgets.length > 0){
				for(var j = 0; j < widgets.length; j++){

					//组合资源内部有资源，自身不需要作为判断的容器
					if(Element.hasClassName(Element.first(widgets[j]), 'trs-composite')){
						continue;
					}
					doms.push(widgets[j]);
				}
			}else{
				doms.push(column);
			}
		}

		return doms;
	}

	var getBoxOfColumn = function(column){
		return Element.first(column);
	}

	var inElement = function(x, y, element){
		var page = Position.getPageInTop(element);
		//var page = Position.cumulativeOffset(element);
		
		if(x < page[0] || y < page[1]) return 0;

		var width = parseInt(element.offsetWidth, 10);
		var height = parseInt(element.offsetHeight, 10);
		if(x > page[0] + width || y > page[1] + height) return 0;

		if(Element.hasClassName(element, 'trs_column')){//空的列
			return 2;
		}

		if(y >= page[1] && y < page[1] + height / 2){//资源前面
			return -1;
		}
		return 1;//资源后面
	}

	function widgetDragEnd(){
		wcm.special.widget.InstanceMgr.add(dom.parentNode, 'Top');

	}

	wcm.util.AccessWidgetDragger = {

		onDragStart : function(){
			wcm.util.Draggable.dragging = true;
			var destPosition = $('wcm-drag-destPosition');
			if(!destPosition){
				new Insertion.Bottom(document.body, destPositionHtml);
				destPosition = $('wcm-drag-destPosition');
				destPosition.style.height = '94px';
			}

			//开始拖动时停止资源块的标识
			wcm.special.widget.InstanceUI.unidentify();
			wcm.special.layout.OperUI.unIdentify();
			
			this.targets = getTargets(Element.first(document.body));

			// 创建遮罩
			var mask = $("special-mask");
			if(!mask){
				mask = document.createElement("DIV");
				mask.id = "special-mask";
				document.body.appendChild(mask);
			}
			mask.style.display = "block";
		},
		onDrag : function(x, y){
			var targets = this.targets;
			// 如果没有目标，直接返回
			if(!targets)return;
			
			for(var index = 0; index < targets.length; index++){
				var target = targets[index];
				var position = inElement(x, y, target);
				if(position){
					//添加移开之处的“单击添加资源节点”
					var destPosition = $('wcm-drag-destPosition');
					if(Element.first(destPosition.parentNode) == Element.last(destPosition.parentNode)){
						new Insertion.Before(destPosition, PageController.getTopWin().sEmptyColumnHtml);
						//new Insertion.Before(destPosition, '<div class="c-empty-column">点击添加资源</div>');
					}
					//重置占位元素的高度
					if(position == -1){
						var related = target;
						target.parentNode.insertBefore(destPosition, related);
					}else if(position == 1){
						var related = Element.next(target);
						target.parentNode.insertBefore(destPosition, related);
					}else{
						getBoxOfColumn(target).appendChild(destPosition);
					}

					//移除目标之处的“单击添加资源节点”
					//var destPosition = $('wcm-drag-destPosition');					
					var next = Element.next(destPosition);
					if(next && Element.hasClassName(next, 'c-empty-column')){
						//将占位元素的高度设置为“单击添加资源”节点的高度，防止出现高度变化而出现来回闪动的现象
						if(destPosition.offsetHeight<next.offsetHeight)
							destPosition.style.height = next.offsetHeight-8+"px";
						Element.remove(next);
					}
					var previous = Element.previous(destPosition);
					if(previous && Element.hasClassName(previous, 'c-empty-column')){
						//将占位元素的高度设置为“单击添加资源”节点的高度，防止出现高度变化而出现来回闪动的现象
						if(destPosition.offsetHeight<previous.offsetHeight)
							destPosition.style.height = previous.offsetHeight-8+"px";
						Element.remove(previous);
					}
					break;
				}
			}
		},
		onDragEnd : function(x, y, e, draginfo){
			wcm.util.Draggable.dragging = false;

			this.targets = null;
			$("special-mask").style.display = "none";

			var destPosition = $('wcm-drag-destPosition');

			if(draginfo.widgetId){//资源节点拖动结束
				position = position || "Before";
				//先触发beforeadd事件
				if(wcm.special.widget.InstanceMgr.fireEvent('beforeadd', destPosition, position) === false) return;

				var oPostData = {
					templateId : draginfo.templateId,
					widgetId : draginfo.widgetId
				};
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.Call('wcm61_visualtemplate', 'saveWidgetInstance', oPostData, true, function(_trans,json){
					var instanceId = $v(json,"RESULT");
					var oparams = {
						bAdd : 1,
						pageStyleId : PageController.getPageStyleId() || 0,
						widgetInstanceId : instanceId
					};
						//需要给专题页面返回资源实例id
					wcm.CrashBoarder.get('set_widgetparameter_value').show({
						title : '设置资源属性',
						src : 'widget/widgetparameter_set.jsp',
						width:'850px',
						height:'400px',
						params : oparams,
						maskable:'true',
						flag : 1,//当点击叉关闭弹出框时标示是否已经点击确定添加按钮
						close : function(){//重写关闭函数
							wcm.CrashBoarder.get('set_widgetparameter_value').hide();
							if(this.flag){
								if(Element.first(destPosition.parentNode) == Element.last(destPosition.parentNode)){
									new Insertion.Before(destPosition, PageController.getTopWin().sEmptyColumnHtml);
								}
								Element.remove(destPosition);
								this.flag=1;
							}
						},
						callback : function(params){
							if(params){//单击了确定
								var ui = wcm.special.widget.InstanceMgr;
								ui.parse(instanceId, function(sVisualHtml){
									//将获取的内容添加到页面上，不触发afteradd事件
									var sHtml = String.format(
										ui.sInstanceTemplate,
										ui.instanceCls,
										ui.idPrefix,
										instanceId,
										sVisualHtml,
										""
									);
									new Insertion["Before"](destPosition, sHtml);
									var widget = Element.previous(destPosition);
									Element.remove(destPosition);
									ui.fireEvent('afteradd', widget);
								});
								this.flag = 0;
							}else{//单击了取消
								if(Element.first(destPosition.parentNode) == Element.last(destPosition.parentNode)){
									new Insertion.Before(destPosition, PageController.getTopWin().sEmptyColumnHtml);
								}
								Element.remove(destPosition);
							}
						}
					});	
				});
			
			}else{//栏目节点拖动结束

				var position = Position.cumulativeOffset(destPosition);

				var oParam = {
					templateId: draginfo.templateId,
					channelId: draginfo.channelId,
					objectId: draginfo.objectId,
					objectType: draginfo.objectType,
					left : position[0],
					top : position[1],
					width : parseInt(destPosition.offsetWidth, 10)
				};
				
				var oHelper = new com.trs.web2frame.BasicDataHelper();
				oHelper.Call('wcm61_visualtemplate', 'createWidgetInstanceFromWidgetAcceptor', oParam, true, function(_trans, json){
					var instanceId = _trans.responseText;
					var ui = wcm.special.widget.InstanceMgr;
					ui.parse(instanceId, function(sVisualHtml){
						//将获取的内容添加到页面上，不触发afteradd事件
						var sHtml = String.format(
							ui.sInstanceTemplate,
							ui.instanceCls,
							ui.idPrefix,
							instanceId,
							sVisualHtml,
							""
						);
						new Insertion["Before"](destPosition, sHtml);
						var widget = Element.previous(destPosition);
						Element.remove(destPosition);
						ui.fireEvent('afteradd', widget);
					})
				});
			}
		},
		destroy : function(){
			if(!this.targets){
				//表示onDragEnd已经被正常调用，所以此处不需要再调用
				return;
			}

			var destPosition = $('wcm-drag-destPosition');
			if(destPosition){
				var destPosition = $('wcm-drag-destPosition');
				if(Element.first(destPosition.parentNode) == Element.last(destPosition.parentNode)){
					new Insertion.Before(destPosition, PageController.getTopWin().sEmptyColumnHtml);
				}
				Element.remove(destPosition);
			}
			$("special-mask").style.display = "none";
			this.targets = null;
		}
	};
})();