var TABS_CONF={
	items:[{
		key:'table',
		url:'#PREFIX#_datatable.jsp',
		desc:'数据表'
	},{
		key:'barchart',
		url:'#PREFIX#_barchart.jsp',
		desc:'柱状图'
	},{
		key:'piechart',
		url:'#PREFIX#_piechart.jsp',
		desc:'饼状图'
	},{
		key:'trendchart',
		url:'#PREFIX#_trendchart.jsp',
		desc:'走势图'
	}]
}
var SEARCH_CONF={
	select_time_defaultValue:0,
	select_item:[{
			text:"名称", //TODO
			value:"name"
	}]
}
var SELECT_HTML_TEMPLATE = [
	'<select name="" id="_sql_index_select_">',
		'<option value="docNum" sqlindex="1">总发稿量</option>',
	'</select>'].join("");

Event.observe(window,'load',function(){
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
		
	new Stat.Tab().init(TABS_CONF);
});