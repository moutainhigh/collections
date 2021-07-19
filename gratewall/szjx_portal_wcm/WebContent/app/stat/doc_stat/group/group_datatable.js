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
		var dom = event.srcElement || event.target;
		if(dom.tagName!="A") return;
		if(bStatByChannel) return;
		var tdEl = pageController.getSpecialElement(dom,"TD");
		if(!tdEl) return;
		//站点和栏目的特殊转跳
		var sSiteGroupId = tdEl.getAttribute("forSite",2);
		var sChannelGroupId = tdEl.getAttribute("forChannel",2)
		if(sSiteGroupId && sSiteGroupId.trim() != ""){
			var params = window.location.search.parseQuery();
			// 由于子页面中检索项可能不一样，故需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				"GroupId" : sSiteGroupId,// 参数名错误，导致点进去以后的标题，由“组织”变成了“用户”
				ReturnUrl : sReturnUrl
			});
			location.href = "../site/site_datatable_user_group.jsp?" + parseJsonToParams(params);
		}else if(sChannelGroupId && sChannelGroupId.trim() != ""){
			var params = window.location.search.parseQuery();
			// 由于子页面中检索项可能不一样，故需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				"GroupId" : sChannelGroupId,
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
			

			location.href = "doc_list_forGroup.jsp?" + parseJsonToParams(params);
		}
	},
	makeBasicParam : function(tdEl){
		//基本检索参数
		var params = {};
		var trEl = pageController.getSpecialElement(tdEl,"TR");
		var nGroupId = trEl.getAttribute("GroupId");
		var docStatus = tdEl.getAttribute("docStatus");
		var docModal = tdEl.getAttribute("docModal");
		var docForm = tdEl.getAttribute("docForm");
		Ext.apply(params,{
			"GroupId" : nGroupId,
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
	Ext.apply(SEARCH_CONFIG.GROUP_DEFAULT,{
		select_item:[{
			text:"所属部门",
			value:"gname"
		}],
		export_cmd:function(){
			//导出为Excel
			var sUrl = "export_group_doc_stat.jsp";
			exportExcel(sUrl);
		}
	});
	Stat.SearchBar.UI.init(SEARCH_CONFIG.GROUP_DEFAULT);
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	Stat.Tab.UI.init(TABS_CONFIG.GROUP_DEFAULT);
});
