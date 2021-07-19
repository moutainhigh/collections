window.onload = function(){
	Stat.SearchBar.UI.init(Ext.apply(SEARCH_CONFIG.SITE_DEFAULT,{
		export_cmd:function(){
			var oSearch = window.location.search;
			var url = "export_site_doc_stat.jsp"+oSearch;
			new Ajax.Request(url,{
				contentType : 'application/x-www-form-urlencoded',
				method : 'post',
				parameters : null,
				onSuccess : function(_transport){
					var sResult = _transport.responseText;
					if(sResult && sResult.indexOf("<excelfile>") != -1){
						var ix = sResult.indexOf("<excelfile>") + 11;
						var ixx = sResult.indexOf("</excelfile>");
						sResult = sResult.substring(ix,ixx);		
						var frm = $("ifrmDownload");
						var sUrl = "../../../file/read_file.jsp?FileName="+sResult; 	
						frm.src = sUrl	
					}else{
						CTRSAction_alert("导出统计结果到Excel失败！");
					}
				}
			});
		}
	}));
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
}

new Stat.Tab().init(TABS_CONFIG.SITE_DEFAULT);

var pageController = {
	/*
	*  初始化绑定页面的事件
	*/
	initEvent : function(){
		Event.observe(document,"click",this.Click_Detail);
		Event.observe(document,"click",this.ViewByGroup);
		Event.observe(document,"click",this.ViewByUser);
	},
	Click_Detail : function(evt){
		var event = window.event || evt;
		var dom = Event.element(event);
		var el = Element.find(dom,"statType");
		if(el){
			var parentEl = Element.find(el,"siteId");
			var nSiteId = parentEl.getAttribute("siteId");
			var nType = el.getAttribute("statType");
			var nCase = el.getAttribute("case");
			var params = window.location.search.parseQuery();
			// 由于子页面中检索项可能不一样，帮需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				ReturnUrl : sReturnUrl,
				siteIds : nSiteId,
				statType : nType,
				"case" : nCase
			});
			location.href = "doc_statofsite_list.jsp?"+parseJsonToParams(params);
		}
	},
	ViewByGroup : function(evt){
		var event = window.event || evt;
		var dom = Event.element(event);
		var el = Element.find(dom,"byGroup");
		if(el){
			var parentEl = Element.find(el,"siteId");
			var nSiteId = parentEl.getAttribute("siteId");
			var sQueryString = window.location.search;
			var params = sQueryString.parseQuery();
			// 由于子页面中检索项可能不一样，帮需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				ReturnUrl : sReturnUrl,
				SiteId : nSiteId
			});
			location.href = "../group/group_site_datatable.jsp?"+parseJsonToParams(params);
		}
	},
	ViewByUser : function(evt){
		var event = window.event || evt;
		var dom = Event.element(event);
		var el = Element.find(dom,"byUser");
		if(el){
			var parentEl = Element.find(el,"siteId");
			var nSiteId = parentEl.getAttribute("siteId");
			var sQueryString = window.location.search;
			var params = sQueryString.parseQuery();
			// 由于子页面中检索项可能不一样，帮需要去除检索项选项
			Ext.apply(params,{
				SelectItem:"",
				SearchValue:"",
				PageSize:getBestPageSize(),//去掉分页信息，设置为最佳分页
				CurrPage:"",
				ReturnUrl : sReturnUrl,
				SiteId :nSiteId
			});
			location.href = "../user/user_site_datatable.jsp?"+parseJsonToParams(params);
		}
	},
	/*
	*  页面初始化
	*/
	init : function(){
		this.initEvent();
		//var dt = new Date();
		//$("datetime").innerHTML = dt.getFullYear()+"-"+(dt.getMonth()+1)+"-"+dt.getDate();
	}
}
Event.observe(window,"load",function(){
	pageController.init();
});