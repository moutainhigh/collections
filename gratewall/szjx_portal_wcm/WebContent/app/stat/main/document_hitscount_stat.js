var pageController = {
	/*
	*  初始化绑定页面的事件
	*/
	initEvent : function(){
		Event.observe(document,"click",this.Mouse_Click_Event);
	},
	Mouse_Click_Event : function(evt){
		var event = evt || window.event;
		Event.stop(event);
		var element = event.srcElement || event.target;
		if(element.tagName != 'A')
			return;
		element = pageController.getSpecialElement(element,"TD");
		if(!element) return;
		var params = pageController.makeBasicParam(element);
		window.open("../../document/document_show.jsp?" + parseJsonToParams(params));
	},
	makeBasicParam : function(element){
		//基本检索参数
		var params = {};
		element = pageController.getSpecialElement(element,"TR");
		var paramDocIdValue = element.getAttribute("docId");
		var paramChannelIdValue = element.getAttribute("channelId");
		var paramChanlDocIdValue = element.getAttribute("ChnlDocId");
		Ext.apply(params,{
			"DocumentId" : paramDocIdValue,
			"ChannelId" : paramChannelIdValue,
			"ChnlDocId" : paramChanlDocIdValue
		});
		return params;
	},
	getSpecialElement : function(element,attributeValue){
		while(element && element.tagName != "BODY"){
			if(element.tagName == attributeValue)
				break;
			element = element.parentNode;
		}
		return element;
	},
	/*
	*  页面初始化
	*/
	init : function(){
		this.initEvent();
	},
	/*
	*  页面卸载
	*/
	destroy:function(){
		
	}
}
Event.observe(window,"load",function(){
	pageController.init();
});