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
	Mouse_Click_Event : function(e){
		var e = window.event || e;
		var dom = Event.element(e);
		var linkUrl = "";
		// 获取有url属性的父元素
		var el = Element.find(dom,"url");
		var query = {};
		if(el){
			// 设置右边iframe为当前元素设置的url属性
			var sUrl = el.getAttribute("url");
			if(sUrl.indexOf("?")>=0){
				query=sUrl.substring(sUrl.indexOf("?")+1,sUrl.length).parseQuery();
				sUrl = sUrl.substring(0,sUrl.indexOf("?"));
			}
			query["PageSize"] = getBestPageSize();
			$("right_iframe").src = sUrl+"?"+parseJsonToParams(query);//el.getAttribute("url");
			// 删除原来的active元素
			var els = document.getElementsByClassName("active");
			for(var i=0;i<els.length;i++){
				Element.removeClassName(els[i],"active");
			}
			// 添加active样式
			Element.addClassName(el,"active");
		}else{
			var el = Element.find(dom,null,"stat_items");
			if(!el)return;
			var next = Element.next(el);
			// 收缩相关的ul元素
			if(next && next.tagName=="UL" && !Element.hasClassName(next,"display-none")){
				var lis = document.getElementsByClassName("active",next);
				if(!lis || lis.length==0)Element.first(next).click();
				return;
			}
			var uls = document.getElementsByTagName("ul");
			for(var i=0;i<uls.length;i++){
				Element.addClassName(uls[i],"display-none");
			}
			Element.removeClassName(next,"display-none");
			Element.first(next).fireEvent('onclick');
		}
	},
	/*
	*  页面初始化
	*/
	init : function(){
		this.initEvent();
		var dt = new Date();
		$("datetime").innerHTML = dt.getFullYear()+"-"+(dt.getMonth()+1)+"-"+dt.getDate();
	},
	/*
	*  页面卸载
	*/
	destroy:function(){
		
	}
}
Event.observe(window,"load",function(){
	pageController.init();
});

//extend firefox
if(window.HTMLElement){
	HTMLElement.prototype.fireEvent = function(sType){
		sType = sType.replace(/^on/, "");
		var evtObj = document.createEvent('MouseEvents');     
		evtObj.initMouseEvent(sType, true, true, document.defaultView, 1, 0, 0, 0, 0, false, false, true, false,   0, null);     
		this.dispatchEvent(evtObj);
	};
}


