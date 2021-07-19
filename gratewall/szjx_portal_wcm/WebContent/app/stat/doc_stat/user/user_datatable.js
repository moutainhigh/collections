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
		var dom = Event.element(event);
		if(dom.tagName!="A") return;
		var tdEl = pageController.getSpecialElement(dom,"TD");
		if(!tdEl) return;
		//站点和栏目的特殊转跳
		var sSiteUserName = tdEl.getAttribute("forSite",2);
		var sChannelUserName = tdEl.getAttribute("forChannel",2);
		if(sSiteUserName && sSiteUserName.trim() != ""){
			var params = window.location.search.parseQuery();
			// 由于子页面中检索项可能不一样，故需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				"UserName" : sSiteUserName,
				ReturnUrl : sReturnUrl
			});
			location.href = "../site/site_datatable_user_group.jsp?" + parseJsonToParams(params);
			return;
		}
		else if(sChannelUserName && sChannelUserName.trim() != ""){
			var params = window.location.search.parseQuery();
			// 由于子页面中检索项可能不一样，故需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				"UserName" : sChannelUserName,
				ReturnUrl : sReturnUrl
			});
			location.href = "../channel/chnl_datatable_user_group.jsp?" + parseJsonToParams(params);
		}else{
			//获取基本的检索参数
			var params = pageController.makeBasicParam(tdEl);
			// 由于子页面中检索项可能不一样，故需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				ReturnUrl : sReturnUrl
			});
			location.href = "doc_detail_list.jsp?" + parseJsonToParams(params);
		}
	},
	makeBasicParam : function(tdEl){
		//基本检索参数
		var params = {};
		var trEl = pageController.getSpecialElement(tdEl,"TR");
		var sUserName = trEl.getAttribute("UserName");
		var docStatus = tdEl.getAttribute("docStatus");
		var docModal = tdEl.getAttribute("docModal");
		var docForm = tdEl.getAttribute("docForm");
		Ext.apply(params,{
			"UserName" : sUserName,
			"DocStatus" : docStatus,
			"DocModal" : docModal,
			"DocForm" : docForm
			});
		Ext.apply(params,window.location.search.parseQuery());
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
		export_cmd:function(){
			//导出为Excel
			exportExcel("export_user_doc_stat.jsp");
			
		}
	});
	Stat.SearchBar.UI.init(SEARCH_CONFIG.USER_DEFAULT);
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	Stat.Tab.UI.init(TABS_CONFIG.USER_DEFAULT);

});
