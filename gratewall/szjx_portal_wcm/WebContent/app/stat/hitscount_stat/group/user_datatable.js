/*
* 流量统计主页面JS，负责登录、标签切换等
* 
*/

var pageController = {
	/*
	*  初始化绑定页面的事件
	*/
	initEvent : function(){
		Event.observe(document,"click",this.Mouse_Click_Event);
	},
	Mouse_Click_Event : function(evt){
		var event = evt || window.event;
		var element = event.srcElement || event.target;
		if(element.tagName != 'A')
			return;
		element = pageController.getSpecialElement(element,"TD");
		if(!element) return;
		//获取基本的检索参数
		var params = pageController.makeBasicParam(element);
		location.href = "./../document/document_hitscount_table.jsp" + window.location.search + "&" + Object.toQueryString(params);
	},
	makeBasicParam : function(element){
		//基本检索参数
		var params = {};
		element = pageController.getSpecialElement(element,"TR");
		var paramValue = element.getAttribute("userId");
		Object.extend(params,{
			"UserId" : paramValue
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
var tabConfig = {
	items:[{
			key:'table',
			url:'document_hitscount_table.jsp',
			desc:'数据表'
		}]
}
var searchConfig = {
	select_item:[{
			text:"发稿人",
			value:"DocCrUser"
		},{
			text:"部门名称",
			value:"GroupName"
		}
		],
	export_cmd:function(){
		var sRequestUrl = 'export_user_hitscount.jsp';
		exportExcel(sRequestUrl);
	}
}

Object.toQueryString = function(source){
	var queryString = [];
	for (var property in source) queryString.push(encodeURIComponent(property) + '=' + encodeURIComponent(source[property]));
	return queryString.join('&');
};
Event.observe(window,"load",function(){
	pageController.init();
	//设置检索字段
	Stat.SearchBar.UI.init(searchConfig);
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	new Stat.Tab().init(tabConfig);
	//初始化检索字段信息
	initSearchParam();
		
});

function hitsTimeOptionClick(nType){
	var json = getUrlJson();
	json['HitsTimeItem'] = nType;
	json['TimeItem'] = $("select_time_").value;
	var paramStr = Object.toQueryString(json);
	location.href = 'user_datatable.jsp?' + paramStr;
}
function getUrlJson(){
	var json = {};
	var sUrl = location.href;
	if(sUrl.indexOf('?') > 0){
		sUrl = sUrl.substring(sUrl.indexOf('?') + 1, sUrl.length);
		json = sUrl.parseQuery();
	}
	return json;
}

function initSearchParam(){
	var crTimeParam = getParameter("TimeItem");
	if(!crTimeParam)
		crTimeParam = 0;
	var selTimeEl = $("select_time_");
	if(selTimeEl){
		var optionEls = selTimeEl.options;
		for(var i=0; i<optionEls.length; i++){
			var optionEl = optionEls[i];
			if(optionEl.value == crTimeParam){
				optionEl.selected = true;
				break;
			}
		}
	}
	initHitsTimeOption();
}
function initHitsTimeOption(){
	var hitsTimeParam = getParameter("HitsTimeItem");
	if(!hitsTimeParam){
		$('HitsTimeItem_0').checked = true;
	}else{
		$('HitsTimeItem_' + hitsTimeParam).checked = true;
	}
}
 