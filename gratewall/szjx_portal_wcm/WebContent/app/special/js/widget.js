Ext.ns('wcm.util');
wcm.util.ElementFinder = {
	findElement : function(dom, sAttrName, sClassName, aIgnoreAttr){
		aIgnoreAttr = aIgnoreAttr || [];
		while(dom){
			if(!dom.tagName || dom.tagName == 'BODY') break;
			for (var i = 0; i < aIgnoreAttr.length; i++){
				if(dom.getAttribute(aIgnoreAttr[i]) != null) return 0;
			}
			if(sAttrName && dom.getAttribute(sAttrName) != null){
				return dom;
			}
			if(sClassName && Element.hasClassName(dom, sClassName)){
				return dom;
			}
			dom = dom.parentNode;
		}
		return null;
	}
};



Ext.ns('wcm.special.widget');
(function(){
	/*
	*向服务器发送请求，解析指定资源块实例的内容，在回调函数中传入获取的可视化编辑的内容
	*/
	function parse(nInstanceId, fCallBack){
		var search = window.frameElement.src;
		var index = search.indexOf('?');
		search = search.substring(index);
		var data = {
			objectType : getParameter("HostType", search) || 101, 
			objectId : getParameter("HostId", search) || 0,
			WidgetInstanceId : nInstanceId
		};
		BasicDataHelper.call('wcm61_visualtemplate', 'parseWidgetInstance', data, true, function(transport){
			if(fCallBack) fCallBack(transport.responseText);
		});
	}

	function CWidgetInstanceMgr(){
		this.addEvents('beforemark', 'aftermark', 'beforeadd', 'add', 'afteradd', 'beforeedit', 'afteredit', 'beforeremove', 'afterremove', 'beforerefresh', 'afterrefresh','beforemove','aftermove');			
	};
	
	/*
	*向服务端发送请求，拷贝相关的资源块
	*/
	function copy(nInstanceId, fCallBack){
		var data = {
			objectId : nInstanceId
		};
		BasicDataHelper.call('wcm61_visualtemplate', 'copyWidgetInstance', data, true, function(transport){
			if(fCallBack) fCallBack(transport.responseText);
		});
	}
	//TODO 需要考虑从服务端获取，否则客户端和服务器端都存在这个html结构
	var sInstanceTemplate = "<div class='{0}' id='{1}{2}' wName='{4}'>{3}</div>";

	Ext.extend(CWidgetInstanceMgr, wcm.util.Observable, {
		/**
		*资源实例的id前缀信息
		*/
		instanceCls : 'trs-widget',
		/**
		*资源实例的id前缀信息
		*/
		idPrefix : 'trs-widget-',
		/**
		*资源实例激活选中时的class信息
		*/
		activeCls : 'mark-widget',

		isInstance : function(dom){
			return Element.hasClassName(dom, this.instanceCls);
		},
		markId : null,
		/**
		*取消选中当前的实例
		*/
		unmark : function(){
			if(!this.markId) return;
			Element.removeClassName(this.getInstance(this.markId), this.activeCls);
			this.markId = null;
		},
		/**
		*选中当前的实例
		*/
		mark : function(dom, scrolltoview){
			dom = $(dom);
			if(!dom || Element.hasClassName(dom, this.activeCls)) return;
			this.fireEvent('beforemark', dom);
			Element.addClassName(dom, this.activeCls);
			if(scrolltoview && PageController) //dom.scrollIntoView(true);
				PageController.getTopWin().scrollInToView(dom,window);
			this.markId = this.getInstanceId(dom);
			this.fireEvent('aftermark', dom);
		},
		positionMapping : {
			"Top" : 'first',
			"Bottom" : 'last',
			"Before" : 'previous',
			"After" : 'next'
		},
		getInstanceId : function(_el){
			var dom = _el || el;
			var info = dom.id.split("-");
			return info[info.length - 1];
		},
		getInstance : function(id){
			return $(this.idPrefix + id);
		},
		find : function(dom){
			return wcm.util.ElementFinder.findElement(dom, null, this.instanceCls);
		},
		/**
		*在指定的资源块后面添加新的资源块
		*dom
		*position 可选值为：Top,Bottom,Before,After
		*/
		add : function(dom, position){
			position = position || "After";
			dom = $(dom);
			if(!dom) return;
			//先触发beforeadd事件
			if(this.fireEvent('beforeadd', dom, position) === false) return;

			var search = window.frameElement.src;
			var index = search.indexOf('?');
			search = search.substring(index);
			var objectType = getParameter("HostType", search) || 101;

			//打开资源块选择页面
			var caller = this;
			wcm.CrashBoarder.get('add-widget-to-page').show({
				title : '添加资源块',
				src : 'widget/widget_select_index.jsp',
				width:'850px',
				height:'450px',
				maskable:'true',
				params : {
					widgetType : objectType == 605 ? "2" : "1",
					templateid : getParameter("TemplateId", window.frameElement.src) || 0,
					pageStyleId : PageController.getPageStyleId() || 0
				},
				callback : function(params){	
					var nNewInstanceId = params['id'];
					//根据资源块实例id，获取可视化编辑内容
					parse(nNewInstanceId, function(sVisualHtml){
						//将获取的内容添加到页面上，并触发afteradd事件
						var sHtml = String.format(
							sInstanceTemplate,
							wcm.special.widget.InstanceMgr.instanceCls,
							wcm.special.widget.InstanceMgr.idPrefix,
							nNewInstanceId,
							sVisualHtml,
							 params['wName'] || ""
						);
						caller.fireEvent('add', dom, position);
						new Insertion[position](dom, sHtml);
						caller.fireEvent('afteradd', caller.getInstance(nNewInstanceId), dom);
					});
				}
			});		
		},
		/**
		*删除指定的资源块
		*/
		remove : function(dom){
			dom = $(dom);
			if(!dom) return;
			//先触发beforeremove事件
			if(this.fireEvent('beforeremove', dom) === false) return;
			//删除资源块实例的可视化编辑内容，并触发afterremove事件
			var parentNode = dom.parentNode;
			var id = this.getInstanceId(dom);
			Element.remove(dom);
			this.fireEvent('afterremove', parentNode, id);
		},
		/**
		*修改指定的资源块
		*/
		edit : function(dom){
			dom = $(dom);
			if(!dom) return;
			var id = this.getInstanceId(dom);
			//先触发beforeedit事件
			if(this.fireEvent('beforeedit', dom) === false) return;
			//打开资源块属性修改页面
			var caller = this;
			wcm.CrashBoarder.get('set_widgetparameter_value').show({
				title : '设置资源属性',
				src : 'widget/widgetparameter_set.jsp',
				width:'850px',
				height:'400px',
				maskable:'true',
				params : {
					widgetInstanceId : id, 
					pageStyleId : PageController.getPageStyleId() || 0,
					bAdd : 0
				},//由于打开就已经创建了实例，所以传入参数来判断是新建还是修改
				callback : function(params){
					//根据资源块实例id，获取可视化编辑内容
					parse(id, function(sVisualHtml){
						//将获取的内容更新到页面上，并触发afteredit事件
						Element.update(dom, sVisualHtml);
						caller.fireEvent('afteredit', dom);
					});
				}
			});
		},
		/**
		*修改为其他资源
		*/
		changeWidgetToOther : function(dom){
			dom = $(dom);
			if(!dom) return;
			var id = this.getInstanceId(dom);
			//先触发beforeedit事件
			if(this.fireEvent('beforeedit', dom) === false) return;

			var search = window.frameElement.src;
			var index = search.indexOf('?');
			search = search.substring(index);
			var objectType = getParameter("HostType", search) || 101;

			//打开资源更改页面
			var caller = this;

			wcm.CrashBoarder.get('add-widget-to-page').show({
				title : '更改为其他资源',
				src : 'widget/widget_select_index.jsp',
				width:'850px',
				height:'450px',
				maskable:'true',
				params : {
					oldWidgetInstanceId : id, 
					widgetType : objectType == 605 ? "2" : "1",
					templateid : getParameter("TemplateId", window.frameElement.src) || 0,
					pageStyleId : PageController.getPageStyleId() || 0
				},
				callback : function(params){	
					var nNewInstanceId = params['id'];

					//根据资源块实例id，获取可视化编辑内容
					parse(nNewInstanceId, function(sVisualHtml){

						//将获取的内容添加到页面上，并触发afteradd事件
						var sHtml = String.format(
							sInstanceTemplate,
							wcm.special.widget.InstanceMgr.instanceCls,
							wcm.special.widget.InstanceMgr.idPrefix,
							nNewInstanceId,
							sVisualHtml,
							 params['wName'] || ""
						);

						new Insertion['After'](dom, sHtml);
						var newDom = Element.next(dom);
						dom.parentNode.removeChild(dom);

						//将获取的内容更新到页面上，并触发afteredit事件
						caller.fireEvent('afteredit', newDom);
					});
				}
			});
		},
		
		/**
		*刷新指定的资源块
		*/
		refresh : function(dom){
			dom = $(dom);
			if(!dom) return;
			//先触发beforerefresh事件
			if(this.fireEvent('beforerefresh', dom) === false) return;
			//根据返回的资源块实例id，获取可视化编辑内容
			var caller = this;
			var id = this.getInstanceId(dom);
			parse(id, function(sVisualHtml){
				//将获取的内容添加到页面上，并触发beforeedit事件
				Element.update(dom, sVisualHtml);
				caller.fireEvent('afterrefresh', dom);
			});
		},
		/*
		*	移动资源块
		*	@参数：源资源块对象,目标资源块对象,位置,是复制还是剪切,回调函数
		*/
		move : function(srcElem,tagElem,position,oper,callback){
			position = position ||'After';
			srcElem = $(srcElem);
			tagElem = $(tagElem);
			caller = this;
			switch(oper){
				case "cut":
					var nInstanceId = this.getInstanceId(srcElem);
					if(this.fireEvent('beforemove', srcElem,tagElem) === false) return;
					parse(nInstanceId, function(sVisualHtml){
						var sHtml = String.format(
							sInstanceTemplate,
							wcm.special.widget.InstanceMgr.instanceCls,
							wcm.special.widget.InstanceMgr.idPrefix,
							nInstanceId,
							sVisualHtml,
							srcElem.getAttribute("wName") || ""
						);
						new Insertion[position](tagElem, sHtml);
						var parentNode = srcElem.parentNode;
						Element.remove(srcElem);
						var newElem = caller.getInstance(nInstanceId);
						if(callback)callback(newElem);
						caller.fireEvent('aftermove',newElem, parentNode);
					});
					// 只能剪切一次					
					PageController.getTopWin().PageController.clipboard = {};
					break;
				case "copy":
					this.copy(srcElem,function(newWidgetInstanceId){
						//根据资源块实例id，获取可视化编辑内容
						parse(newWidgetInstanceId, function(sVisualHtml){
							//将获取的内容添加到页面上，并触发afteradd事件
							var sHtml = String.format(
								sInstanceTemplate,
								wcm.special.widget.InstanceMgr.instanceCls,
								wcm.special.widget.InstanceMgr.idPrefix,
								newWidgetInstanceId,
								sVisualHtml,
								srcElem.getAttribute("wName") || ""
							);
							caller.fireEvent('add', tagElem, position);
							new Insertion[position](tagElem, sHtml);
							var newElem = caller.getInstance(newWidgetInstanceId)
							if(callback)callback(newElem,srcElem);
							caller.fireEvent('afteradd', newElem, tagElem);
						});
					});
					break;
			}
		},
		/*
		*  标识需要拷贝的资源实例对象
		*/
		markCopyEl : function(el){
			PageController.getTreeWin().ViewTree.treeMgr.removeCutStatu();
			PageController.getTopWin().PageController.clipboard={
				elemId : el.id,
				oper : "copy",
				type : "widget"
			}
		},
		/*
		*  向服务端发送请求，拷贝相关的资源块
		*/
		copy : function(el, fCallBack){
			var nInstanceId = this.getInstanceId(el);
			return copy(nInstanceId,fCallBack);
		},
		parse : function(nInstanceId, fCallBack){
			return parse(nInstanceId, fCallBack)
		},
		/*
		*  同步资源给子栏目，主要是同步logo，栏目导航，版权块
		*  防止每一个子页面都上传logo，写版权，插入栏目导航的问题
		*/
		async : function(el,fCallBack){
			var search = window.frameElement.src;
			var index = search.indexOf('?');
			search = search.substring(index);
			var data = {
				ChnlId : getParameter("HostId", search) || 0,
				WidgetInstanceId : this.getInstanceId(el)
			};
			BasicDataHelper.call('wcm61_visualtemplate', 'syncWidgetInstancesToChildChnl', data, true, function(transport){
				if(fCallBack) fCallBack(transport.responseText);
			});
		},
		sInstanceTemplate : sInstanceTemplate
	});

	wcm.special.widget.InstanceMgr = new CWidgetInstanceMgr();
})();


/**
*Identify the widget by toggling the header when user moves the mouse, 
*so user can operate the widget.
*such as modify the widget or append a new widget to the layout.
*/
wcm.special.widget.InstanceUI = function(){
	var el;
	var simpleMenu;
	var ignoreAttr = ['ignore4Instance'];
	var opers = [];
	var isLocked;

	/*
	*资源块
	*/
	var sTemplate = [
		'<div id="{0}" class="{0}" style="display:none;" ignore4Instance="1">',
			'<div class="header-desc" id="{0}-desc"></div>',
			'<div class="header-oper" title="点击这里，进行资源块的操作">',
				'<div class="oper-desc">操作</div>',
			'</div>',
		'</div>'
	].join("");

	var sInstanceIdentifyId = 'widget-instance-identify';
	var init = function(){
		if($(sInstanceIdentifyId)) return;
		new Insertion.Bottom(document.body, String.format(sTemplate, sInstanceIdentifyId));

		//init the action for the header.
		Event.observe(sInstanceIdentifyId, 'click', function(event){
			var dom = Event.element(event);
			dom = wcm.util.ElementFinder.findElement(dom, null, 'header-oper');
			if(!dom) return;
			if(!simpleMenu) {
				simpleMenu = new com.trs.menu.SimpleMenu({sBaseCls : 'widget-instance-oper-menu'});
				simpleMenu.addListener('show', function(){isLocked = true;});
				simpleMenu.addListener('hide', function(){isLocked = false;});
			}
			var position = getPosition(event);
			simpleMenu.show(opers, {el:el,x:position[0],y:position[1]});
		});
	};

	var getPosition = function(event){
		var dom = Event.element(event);
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		var frameElement = win.frameElement;
		var topOffset = Position.getPageInTop(frameElement);
		var pageOffsetX = event.clientX;
		var pageOffsetY = event.clientY;
		return [topOffset[0] + pageOffsetX, topOffset[1] + pageOffsetY];
	};

	return {
		/**
		*在资源块实例顶部产生头部标示元素
		*/
		identify : function(dom){
			if(isLocked) return;
			if(dom == el) return;
			this.unidentify();
			if(!dom) return;
			init();
			Element.show(sInstanceIdentifyId);
			var offsetLeft = parseInt(dom.offsetWidth, 10) - parseInt($(sInstanceIdentifyId).offsetWidth, 10);
			Position.clone(dom, $(sInstanceIdentifyId), {offsetLeft:offsetLeft-1, offsetTop:1, setWidth:false, setHeight:false});
			el = dom;
		},
		/**
		*取消前一次资源块实例顶部的头部标示元素
		*/
		unidentify : function(){
			if(isLocked) return;
			if(!el)return;
			if($(sInstanceIdentifyId)) Element.hide(sInstanceIdentifyId);
			el = null;
		},
		/**
		*注册一些属性，如果鼠标在这些属性节点的子孙节点下移动，identify及unidentify将不响应
		*/
		addIgnoreAttr : function(sAttr){
			ignoreAttr.push(sAttr);
		},
		getIgnoreAttr : function(sAttr){
			return ignoreAttr;
		},
		/**
		*给资源块实例注册相应的操作
		*/
		addOpers : function(_opers){
			for (var i = 0; i < _opers.length; i++){
				opers.push(_opers[i]);
			}
		},
		getInstanceId : function(_el){
			var dom = _el || el;
			var info = dom.id.split("-");
			return info[info.length - 1];
		},
		destroy : function(){
			if(simpleMenu) simpleMenu.destroy();
			simpleMenu = null;
			el = null;
		}
	};
}();

/*
*资源资源块实例操作注册
*/
wcm.special.widget.InstanceUI.addOpers([
	{
		oprKey : 'editWidgetMenuItem',
		desc : "修改资源",
		iconCls : 'edit',
		cmd : function(args){
			wcm.special.widget.InstanceMgr.edit(args.el);
		}
	},
	{
		oprKey : 'changeWidgetToOther',
		desc : "更换为其他资源",
		iconCls : 'changeWidgetToOther',
		cmd : function(args){
			wcm.special.widget.InstanceMgr.changeWidgetToOther(args.el);
		}
	},
	'/',
	{
		oprKey : 'copyWidgetMenuItem',
		desc : "复制资源",
		iconCls : 'copy',
		cmd : function(args){
			wcm.special.widget.InstanceMgr.markCopyEl(args.el);
		}
	},
	{
		oprKey : 'pasteWidgetBeforeMenuItem',
		desc : "粘贴资源到前面",
		iconCls : 'pasteBefore',
		cls : function(args){
			var clipboard = PageController.getTopWin().PageController.clipboard;
			if(!clipboard.elemId ||
				clipboard.type != "widget")
				return "display-none";
		},
		cmd : function(args){
			var ui=wcm.special.widget.InstanceMgr;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args.el,'Before',clipboard.oper);
		}
	},
	{
		oprKey : 'pasteWidgetAfterMenuItem',
		desc : "粘贴资源到后面",
		iconCls : 'pasteAfter',
		cls : function(args){
			var clipboard = PageController.getTopWin().PageController.clipboard;
			if(!clipboard.elemId ||
				clipboard.type != "widget")
				return "display-none";
		},
		cmd : function(args){
			var ui=wcm.special.widget.InstanceMgr;
			var clipboard = PageController.getTopWin().PageController.clipboard;
			ui.move(clipboard.elemId,args.el,'After',clipboard.oper);
		}
	},
	'/',
	{
		oprKey : 'addWidgetBeforeMenuItem',
		desc : "在前面插入资源",
		iconCls : 'addBefore',
		cmd : function(args){
			wcm.special.widget.InstanceMgr.add(args.el,"Before");
		}
	},
	{
		oprKey : 'addWidgetAfterMenuItem',
		desc : "在后面插入资源",
		iconCls : 'addAfter',
		cmd : function(args){
			wcm.special.widget.InstanceMgr.add(args.el);
		}
	},
	{
		oprKey : 'asyncWidgetMenuItem',
		desc : "同步资源到子栏目",
		iconCls : 'async',
		cls : function(args){
			// 只有logo，栏目导航，版权资源块才可见
			// TODO===需要换成使用trs-logo的方式？
			var wname = args.el.getAttribute("WNAME");
			return {"LOGO":1,"栏目导航":1,"版权信息":1}[wname]? "" : "display-none";
		},
		cmd : function(args){
			wcm.special.widget.InstanceMgr.async(args.el);
		}
	},
	'/',
	{
		oprKey : 'setWidgetBackgroundImage',
		desc : "设置背景图片",
		iconCls : 'setWidgetBackgroundImage',
		cmd : function(args){
			var doms = document.getElementsByClassName('p_w_content', args.el);
			var dom;
			var oParam = {};
			if(doms.length > 0){
				dom = doms[0];
				oParam = {
					background : dom.style.background,
					backgroundRepeat : dom.style.backgroundRepeat,
					backgroundPosition : dom.style.backgroundPosition
				}
			}
			
			
			wcm.CrashBoarder.get('setWidgetBackgroundImage-widget').show({
				title : '设置资源背景图片',
				src : 'upload_background_image.html',
				width:'780px',
				height:'350px',
				maskable:true,
				params:oParam,
				callback : function(result){
					if(!dom)
						return;

					dom = doms[0];
					dom.style.background = result['background'];
					var bHasImage = result['hasImage'];
					// 调用服务保存资源实例的资源变量值
					var sBackgroundStyle = "";
					if(bHasImage){
						sBackgroundStyle = "background:"+result['background']+";";
					}
					
					var sId =  wcm.special.widget.InstanceUI.getInstanceId(args.el);
					var oParam = {
						WIDGETINSTANCEID:sId,
						BACKGROUND:sBackgroundStyle
					}

					// 保存模板变量的值
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm61_visualtemplate', 'saveWidgetInstParameters', oParam, true, function(_trans,json){
					});
					wcm.special.widget.InstanceMgr.fireEvent('afteredit', dom);

				}
			});
		}
	},
	{
		oprKey : 'setAutoFitHeight',
		desc : "自适应高度",
		iconCls : 'setAutoFitHeight',
		cmd : function(args){
			var widget = args.el;
			var doms = document.getElementsByClassName('p_w_content', widget);
			if(doms.length <= 0) return;
			doms[0].style.height = 'auto';

			var sId =  wcm.special.widget.InstanceUI.getInstanceId(widget);
			var oParam = {
				WIDGETINSTANCEID:sId,
				"WHEIGHT":'auto'
			}

			// 保存模板变量的值
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call('wcm61_visualtemplate', 'saveWidgetInstParameters', oParam, true, function(_trans,json){
				wcm.special.widget.InstanceMgr.fireEvent('afteredit', widget);
			});

		}
	},
	'/',
	{
		oprKey : 'refreshWidgetMenuItem',
		desc : "刷新资源",
		iconCls : 'refresh',
		cmd : function(args){
			wcm.special.widget.InstanceMgr.refresh(args.el);
		}
	},
	{
		oprKey : 'removeWidgetMenuItem',
		desc : "删除资源",
		iconCls : 'remove',
		cmd : function(args){
			wcm.special.widget.InstanceMgr.remove(args.el);
		}
	}
	/*
	{
		oprKey : 'NavigateToTree',
		desc : "定位到结构树",
		iconCls : 'NavigateToTree',
		cmd : function(args){
			PageController.getTreeWin().focusNode(args.el.id);
		}
	},
	{
		oprKey : 'LockWidget',
		desc : "加锁当前资源",
		title : "锁住当前资源，使其不能拖动",
		iconCls : 'lockWidget',
		cls : function(args){
			return args.el.getAttribute("isLocked") == null ? "" : "display-none";
		},
		cmd : function(args){
			args.el.setAttribute("isLocked", 'true');
		}
	},
	{
		oprKey : 'unLockWidget',
		desc : "解锁当前资源",
		title : "解锁当前资源，使其可以拖动",
		iconCls : 'unlockWidget',
		cls : function(args){
			return args.el.getAttribute("isLocked") != null ? "" : "display-none";
		},
		cmd : function(args){
			args.el.removeAttribute("isLocked");
		}
	}*/
]);


wcm.special.widget.WidgetController = {
	/**
	*促使鼠标在资源块实例上移动的时，使资源块实例高亮激活
	*/
	enableWidgetInstanceHover : function(){
		Event.observe(document, 'mousemove', function(event){
			var dom = Event.element(event);
			var sInstanceCls = wcm.special.widget.InstanceMgr.instanceCls;
			var aIgnoreAttr = wcm.special.widget.InstanceUI.getIgnoreAttr();
			var target = wcm.util.ElementFinder.findElement(dom, null, sInstanceCls, aIgnoreAttr);
			if(target == 0) return;
			// 在拖动资源块时不进行标识
			if(wcm.util.special.Dragger.dragging) return;
			wcm.special.widget.InstanceUI.identify(target);
		});
		Event.observe(document, 'click', function(event){
			var dom = Event.element(event);
			if(Element.hasClassName(dom, 'c-empty-column')){
				wcm.special.widget.InstanceMgr.add(dom.parentNode, 'Top');
			}
		});
	},
	/**
	*页面加载完成时，需要触发的初始化处理
	*/
	init : function(){
		this.enableWidgetInstanceHover();
	},	
	/**
	*页面卸载时，需要触发的销毁处理
	*/
	destroy : function(){
	}	
};


//双击修改资源
Event.observe(window, 'load', function(){
	Event.observe(document, 'dblclick', function(event){
		var dom = Event.element(event || window.event);
		var widget = wcm.util.ElementFinder.findElement(dom, '', 'trs-widget');
		if(!widget) return;
		wcm.special.widget.InstanceMgr.edit(widget);
	});
});