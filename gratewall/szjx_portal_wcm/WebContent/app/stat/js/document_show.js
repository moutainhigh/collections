/*
*  子页面中需要引入的JS文件
*	这个文件中定义了搜索组件 和tab标签组件的使用，防止需要在每一个子页面中进行配置，导致对这些组件配置项进行修改时的繁琐
*/
/*
*  定义文档列表页面中引入的JS，统一使用该JS调用文档的查看页面
*/
function document_click_event(e){
	var event = e || window.event;
	Event.stop(event);
	var dom = Event.element(event);
	if(dom.tagName !="A") return;
	while(dom.tagName!="BODY" && dom.tagName!="TR"){
		dom = dom.parentNode;
	}
	if(dom.tagName=="BODY") return;
	var params = {
		"DocumentId" : dom.getAttribute("docId"),
		"ChannelId" : dom.getAttribute("chnlId"),
		"ChnlDocId" : dom.getAttribute("chnlDocId")
	}
	window.open("../../../document/document_show.jsp?" + parseJsonToParams(params))
}
Event.observe(window,"load",function(){
	Event.observe(document,"click",document_click_event);
});