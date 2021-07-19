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

new Stat.Tab().init(Ext.applyIf(TABS_CONFIG.SITE_DEFAULT,{
	items:[{
		key:'table',
		url:'site_datatable_user_group.jsp',
		desc:'数据表'
	}]
}));

var pageController = {
	/*
	*  初始化绑定页面的事件
	*/
	initEvent : function(){
		//Event.observe(document,"click",this.Click_Detail);
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
			location.href = "doc_statofsite_list.jsp"+window.location.search+"&siteIds="+nSiteId+"&statType="+nType+"&case="+nCase;
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