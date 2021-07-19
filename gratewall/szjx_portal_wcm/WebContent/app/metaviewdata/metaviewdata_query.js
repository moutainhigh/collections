var aFieldType = ['check' , 'appendix' ,'radio' , 'editor'];

Event.observe(window, 'load', function(){
	Event.observe(document.body, 'click', function(event){
		EventHandler.dispatch(window.event || event);
	}, false)
});


var EventHandler = {
	dispatch : function(event){
		//先找到事件触发元素
		var dom = Event.element(event);
		//在事件元素外（的target元素上）包含事件产生所需要的属性。
		dom = this.findTarget(dom);
		if(!dom) return;
		//根据属性调用方法
		var type = dom.getAttribute('_type');
		//是单个数据类型的问题交给单个来处理。
		(this[type] || Ext.emptyFn)(dom, event);
	},
	findTarget : function(dom){
		while(dom){
			if(dom.tagName == "BODY") return null;
			if(dom.getAttribute("_type")) return dom;
			dom = dom.parentNode;
		}
		return null;
	},
	appendix : function(dom, event){
		var bIsTitleField = Element.hasClassName(dom.parentNode,"titleField");
		if(!bIsTitleField){
			var sFileName = dom.getAttribute("value");
			if(sFileName != ""){
				FileDownloader.download("/wcm/file/read_file.jsp?FileName=" + sFileName);
			}
		}
	}
};
