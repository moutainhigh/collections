Ext.ns('wcm.special.design');

/**
*顶层设计页面顶部工具栏管理器。
*用于向页面中新添加的命令按钮，注册相应的处理。
*为什么需要单独设计类ToolBar，而不是每个扩展者自己
*去添加html节点，并绑定该节点的click事件呢？
*这么处理主要基于以下考虑：
*1）避免每个扩展命令者，不必要的绑定多个事件（每一个命令按钮都绑定了事件），
*	以及避免内存泄漏的可能（直接使用onclick）；
*2）便于进行一些全局控制，如：后期可能在鼠标移动时，给命令项添加一些效果，
*	而这个不应该是每一个命令扩展者应该关注的内容，
*	他只应该关注添加一个什么命令（该命令图标的表现，可以通过html和css控制），
*	以及该命令将做何种处理（这一点通过调用addItems接口实现）。
*/
(function(){
	//鼠标在item上时，将添加的样式
	var hoverCls = 'toolbaritemhover';

	//鼠标单击item时，将添加的样式
	var activeCls = 'toolbaritemactive';

	//鼠标单击item时，将添加的样式
	var disableCls = 'toolbaritemdisable';

	var isDisabled = function(dom){
		return Element.hasClassName(dom, disableCls);
	};

	var maxOrder = 0;

	//缓存已经注册的所有命令按钮
	var cache = {};
		
	var identify = 'cmd';//工具栏中，触发何种命令的标示
	
	//用于构造命令按钮html代码的模板
	var template = [
		'<div class="toolbaritem" ', identify, '="{1}" id="item-{1}" title="{3}">',
			'<div class="r">',
				'<div class="c">',
					'<div class="icon {0}" unselectable="on"></div>',
					'<div class="desc" unselectable="on" id="item-{1}-text">{2}</div>',
				'</div>',
			'</div>',
		'</div>'
	].join("");

	wcm.special.design.ToolBar = {
		id : 'toolbar',//工具栏容器id

		/**
		*初始化工具栏处理器
		*/
		init : function(){
			//没有找到工具栏容器，则不进行任何处理
			if(!$(this.id)) return;
			//渲染内容
			this.render();
			//处理工具栏的交互行为
			this.initAction();

			this.refresh();
		},
		/**
		*注册新的工具栏按钮
		*/
		addItems : function(item){
			for (var i = 0; i < arguments.length; i++){
				item = arguments[i];
				if(!item) continue;
				item['order'] = item['order'] || (++maxOrder);
				if(item['order'] > maxOrder){
					maxOrder = item['order'];
				}
				cache[item.oprKey.toUpperCase()] =item;
			}
		},
		/**
		*取消注册相应的工具栏按钮
		*/
		removeItems : function(oprKey){
			for (var i = 0; i < arguments.length; i++){
				oprKey = arguments[i];
				delete cache[oprKey.toUpperCase()];
			}
		},
		/**
		*获取已经注册的工具栏按钮，返回一个已经排序的数组
		*/
		getItems : function(){
			//convert to array.
			var items = [];
			for(var sOprKey in cache){
				if(!cache[sOprKey]['cmd']) continue;
				items.push(cache[sOprKey]);
			}

			//sort the array
			return items.sort(function(item1, item2){
				return item1.order - item2.order;
			});
		},
		/**
		*构造工具栏html，并绚丽到页面上
		*/
		render : function(){
			var aHtml = [];
			var items = this.getItems();
			for(var i = 0; i < items.length; i++){
				var item = items[i];
				aHtml.push(
					String.format(
						template, 
						item['cls'] || item['oprKey'], 
						item['oprKey'], 
						item['desc'],
						item['title'] || item['desc']
					)
				);
			}
			Element.update(this.id, aHtml.join(""));
		},
		/**
		*重新生成命令按钮的状态
		*/
		refresh : function(){
			var dom = Element.first($(this.id));
			while(dom){
				var cmd = dom.getAttribute(identify);
				if(cmd){
					var item = cache[cmd.toUpperCase()];
					if(item['disabled']){
						var disabled = item['disabled']();
						if(disabled){
							Element.removeClassName(dom, hoverCls);
							Element['addClassName'](dom, disableCls);
							$(dom.id + "-text").setAttribute('disabled', 'disabled');
						}else{
							Element['removeClassName'](dom, disableCls);
							$(dom.id + "-text").removeAttribute('disabled');
						}
					}
					if(item['visibled']){
						var visibled = item['visibled']();
						Element[visibled === false ? 'hide' : 'show'](dom);
					}
				}
				dom = Element.next(dom);
			}
		},
		/**
		*初始化工具按钮的交互动作
		*/
		initAction : function(){
			Event.observe(this.id, 'mousedown', function(event){
				var dom = Event.element(event);
				dom = Element.find(dom, identify);
				if(!dom || isDisabled(dom)) return;
				Element.addClassName(dom, activeCls);
			}, false);
			Event.observe(this.id, 'mouseup', function(event){
				var dom = Event.element(event);
				dom = Element.find(dom, identify);
				if(!dom || isDisabled(dom)) return;
				Element.removeClassName(dom, activeCls);
			}, false);
			Event.observe(this.id, 'click', function(event){
				var dom = Event.element(event);
				dom = Element.find(dom, identify);
				if(!dom || isDisabled(dom)) return;
				var sCmd = dom.getAttribute(identify);
				var item = cache[sCmd.toUpperCase()];
				if(!item || !item['cmd']) return;
				item['cmd'](item);
			}, false);

			var dom = Element.first($(this.id));
			while(dom){
				Event.observe(dom, 'mouseout', function(event){
					if(isDisabled(this)) return;
					if(!Element.hasClassName(this, hoverCls)) return;
					var ignoreEl = Element.find(event.toElement, 'ignore');
					if(ignoreEl) return;
					Element.removeClassName(this, hoverCls);
					var cmd = this.getAttribute(identify);
					var item = cache[cmd.toUpperCase()];
					if(item['out']) item['out'](this, event);
				}.bind(dom));
				Event.observe(dom, 'mouseover', function(event){
					if(isDisabled(this)) return;
					if(Element.hasClassName(this, hoverCls)) return;
					Element.addClassName(this, hoverCls);
					var cmd = this.getAttribute(identify);
					var item = cache[cmd.toUpperCase()];
					if(item['over']) item['over'](this, event);
				}.bind(dom), false);

				dom = Element.next(dom);
			}			
		},		
		destroy : function(){
			//TODO...
		}
	};
})();
