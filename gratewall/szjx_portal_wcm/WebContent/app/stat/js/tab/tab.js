/*
*  tab组件，支持
*
*/
(function(){
	var options = {
		renderTo:"tab-nav",
		items:[{
			key:"ss",
			url:"http://g.cn",
			desc:"图表"
		}],
		default_item:""
	};
	var HTML_TEMPLATE =[
		'<div class="tab tab-{0}" id="{0}" url="{1}">',
			'<div class="l">',
				'<div class="r">',
					'<div class="c">',
						'<div class="desc">{2}</div>',
					'</div>',
				'</div>',
			'</div>',
		'</div>'
	].join("");
	Stat.Tab = function(){};
	Ext.apply(Stat.Tab.prototype,{
		/*
		*  页面初始化，包括配置项
		*/
		init : function(opt){
			// 配置项
			Ext.apply(options,opt);
			// 绘制HTML元素
			this.initHTML();
			// 事件初始化
			this.initEvent();
			this.initDefault();
		},
		/*
		*  输出HTML结构
		*/
		initHTML : function(){
			var aHtml = [];
			for(var i= 0;i<options.items.length;i++){
				var item = options.items[i];
				aHtml.push(String.format(HTML_TEMPLATE, item.key, item.url, item.desc));
			}
			var renderTo = $(options.renderTo);
			renderTo.innerHTML = aHtml.join("");
			Element.addClassName(Element.first(Element.first(renderTo)),"first-tab");
			Element.addClassName(Element.first(Element.last(renderTo)),"last-tab");
		},
		/*
		*  初始化默认值
		*/
		initDefault : function(){
			var def_tab_el = null;
			if(options.default_item)
				def_tab_el = $(options.default_item);
			if(!def_tab_el)
				def_tab_el = Element.first($(options.renderTo));
			if(!def_tab_el)return;
			this.toggleActive(def_tab_el, 'addClassName');
		},
		toggleActive : function(dom, sMethod){
			Element[sMethod](dom,"active");
			Element[sMethod](Element.previous(dom),"active-left");
			Element[sMethod](Element.next(dom),"active-right");
		},
		/*
		*  初始化点击事件
		*/
		initEvent : function(){
			var caller = this;
			Event.observe($(options.renderTo), "click", function(e){
				var dom = Event.element(e);
				var target = Element.find(dom, null, 'tab');
				if(!target) return;
				if(Element.hasClassName(target, "active"))return;

				var brothers = target.parentNode.childNodes;
				for(var i=0;i<brothers.length;i++){
					caller.toggleActive(brothers[i], 'removeClassName');
				}
				caller.toggleActive(target, 'addClassName');
				//页面跳转 和 执行事件
				if(target.getAttribute("cmd")){
					eval(target.getAttribute("cmd"));
				}
				if(target.getAttribute("url")){
					window.location.href = target.getAttribute("url")+"?"+caller._getUrlString();
				}
			});
		},
		_getUrlString : function(){
			var queryJson = window.location.search.parseQuery();
			if(Stat.SearchBar.UI){
				Ext.apply(queryJson,Stat.SearchBar.UI.getUrlString().parseQuery());
			}
			return parseJsonToParams(queryJson);
		},
		/*
		*  页面卸载
		*/
		destroy:function(){
		}
	});
	Stat.Tab.UI = new Stat.Tab();
})()
