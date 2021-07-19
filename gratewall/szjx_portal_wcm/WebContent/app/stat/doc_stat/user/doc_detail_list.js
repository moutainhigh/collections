
/*
*  文档的查看页面
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
	$openMaxWin("../../../document/document_show.jsp?" + parseJsonToParams(params));
}
Event.observe(window,"load",function(){
	Event.observe(document,"click",document_click_event);
});

Event.observe(window,"load",function(){
	TABS_CONFIG.USER_DEFAULT.items = [{
		key:'table',
		url:'user_datatable.jsp',
		desc:'数据表'
	}];
	
	Stat.SearchBar.UI.init({
		select_item:[{
			text:"文档标题",
			value:"doctitle"
		}],
		select_item_defaultValue :"doctitle" 
		});
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	Stat.Tab.UI.init(TABS_CONFIG.USER_DEFAULT);
});