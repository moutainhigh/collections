window.onload = function(){
	var searchItem = {
		select_item:[{
				text:"稿件名称",
				value:"doctitle"
		},{
				text:"发稿人",
				value:"cruser"
		}]
	}
	Stat.SearchBar.UI.init(searchItem);
	wcm.TRSCalendar.get({input:'_search_start_time_',handler:'_start_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'_search_end_time_',handler:'_end_time_select_btn_',wot:false,withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
}


var pageController = {
	/*
	*  初始化绑定页面的事件
	*/
	initEvent : function(){
		Event.observe(document,"click",this.Click_Detail);
	},
	Click_Detail : function(evt){
		var event = window.event || evt;
		var dom = Event.element(event);
		Event.stop(event);
		if(dom.tagName !="A") return;
		var el = Element.find(dom,null,"doctitle");
		if(el){
			var parentEl = Element.find(el,"docId");
			if(!parentEl)return;
			var oParams = {
				DocumentId : parentEl.getAttribute("docId"),
				ChannelId : parentEl.getAttribute("chnlId"),
				ChnlDocId : parentEl.getAttribute("chnlDocId"),
				FromRecycle : 0
			};
			$openMaxWin('/wcm/app/document/document_show.jsp?' + $toQueryStr(oParams));
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