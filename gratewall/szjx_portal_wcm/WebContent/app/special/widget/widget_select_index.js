window.m_cbCfg = {
	btns : [
		{
			extraCls : 'wcm-btn-close',
			text : '关闭'
		}
	]
};
m_lastOverWidgetId = "widgetSelect_All";
m_lastOutWidgetId = "widgetSelect_All";
function init(param){
	m_queryString = $toQueryStr2(param);

	$("right_query_iframe").src = './widget_select_list.html?'+m_queryString;
	$("widgetSelect_All").className = "select_btn_hover";
}
Event.observe(document,'click', function(event){
	event = window.event || event;
	var sWidgetId = (Event.element(event).id);
	var sQueryType = sWidgetId.substring(sWidgetId.indexOf("_")+1,sWidgetId.length);
	if (sQueryType == "All"){
		$("right_query_iframe").src = './widget_select_list.html?'+m_queryString;
	}else if (sQueryType == "Recently"){
		$("right_query_iframe").src = './widget_recentlyused_list.jsp?'+m_queryString;
	}else if(sQueryType == "selectTitle"){
		return false;
	}else {
		$("right_query_iframe").src = './widget_select_list.html?'+m_queryString+'&WidgetCategory='+sQueryType;
	}
	//document.getElementsByClassName("select_btn_hover")[0].className = "select_btn";
	if (m_lastOverWidgetId != sWidgetId){
		$(m_lastOverWidgetId).className = "select_btn";
		m_lastOverWidgetId = sWidgetId;
	}
	if($(sWidgetId)) $(sWidgetId).className = "select_btn_hover";

});

/*
Event.observe(window,'load', function(){
	$("right_query_iframe").src = './widget_select_list.html';
	$("widgetSelect_All").className = "select_btn_hover";
});
*/
/*
Event.observe(document,'mouseover', function(event){
	event = window.event || event;
	var sWidgetId = (Event.element(event).id);
	if (sWidgetId.indexOf("widgetSelect")<0) return false;
	$(sWidgetId).className = "select_btn_hover";
});
Event.observe(document,'mouseout', function(event){
	event = window.event || event;
	var sWidgetId = (Event.element(event).id);
	if (sWidgetId.indexOf("widgetSelect")<0) return false;
	if (m_lastOutWidgetId != sWidgetId){
		$(m_lastOutWidgetId).className = "select_btn";
		m_lastOutWidgetId = sWidgetId;
	}
	$(sWidgetId).className = "select_btn_hover";
});
*/