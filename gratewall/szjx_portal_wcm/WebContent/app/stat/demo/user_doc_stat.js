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
		element = pageController.getSpecialElement(element,"TD");
		if(!element) return;
		var bRedirect = false;
		//获取基本的检索参数
		var params = pageController.makeBasicParam(element);
		// 获取特定属性的元素，确定详细检索参数
		var status = element.getAttribute("docStatus",2);
		if(status && status.trim() != ""){
			Object.extend(params,{"docStatus" : status});
			bRedirect = true;
		}
		var form = element.getAttribute("docForm",2);
		if(form && form.trim() != ""){
			bRedirect = true;
			Object.extend(params,{"docForm" : form});
		}
		var modal = element.getAttribute("docModal",2);
		if(modal && modal.trim() != ""){
			bRedirect = true;
			Object.extend(params,{"docModal" : modal, "OnlyForCopy" : true});
		}
		if(bRedirect)
			location.href = "doc_detail_list.html?" + Object.toQueryString(params);
	},
	makeBasicParam : function(element){
		//基本检索参数
		var params = {};
		element = pageController.getSpecialElement(element,"TR");
		var paramValue = element.getAttribute("Curruser");
		Object.extend(params,{"Cruser" : paramValue});
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

Object.toQueryString = function(source){
	var queryString = [];
	for (var property in source) queryString.push(encodeURIComponent(property) + '=' + encodeURIComponent(source[property]));
	return queryString.join('&');
};

Event.observe(window,"load",function(){
	pageController.init();
	
	//设置检索字段
	Ext.apply(SEARCH_CONFIG.USER_DEFAULT,{
		select_item:[{
			text:"发稿人",
			value:"username"
		},{
			text:"所属部门",
			value:"gname"
		}],
		search_url:"user_datatable.jsp"
	});
	Stat.SearchBar.UI.init(SEARCH_CONFIG.USER_DEFAULT);
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	Stat.Tab.UI.init(TABS_CONFIG.USER_DEFAULT);
});
