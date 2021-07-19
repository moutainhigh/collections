var init = function(){
	var tabType = decodeURIComponent(getParameter('tabType'));
	var tabs = wcm.PageTab.getTabs({
		hostType : PageContext.tabHostType,
		displayNum : 6,
		activeTabType : tabType
	});
	tabs.enable = true;
	wcm.PageTab.init(tabs);
	var currTab = tabs.get(tabType);
	var tabUrl = decodeURIComponent(getParameter('tabUrl')) || currTab.url;
	if(Ext.isFunction(currTab.renderUrl)){
		var cJoin = tabUrl.indexOf('?')==-1?'?':'&';
		tabUrl = tabUrl + cJoin 
			+ $toQueryStr(currTab.renderUrl(location.search));
	}else if(tabUrl!=null){
		var cJoin = tabUrl.indexOf('?')==-1?'?':'&';
		tabUrl = tabUrl + cJoin 
			+ location.search.substring(1);
	}
	//如果将init函数体直接写在load事件里面，将不会出现ie6浏览器一直加载的情况，
	//但如果是在load事件里面采用调用函数的形式,将会出现一直加载的情况,所以这里用了
	//时间为0的setTimeout
	setTimeout(function(){
		$('iframe_cnt').innerHTML = [
			'<iframe src="',
			tabUrl || 'about:blank',
			'" width="100%" height="100%" scrolling="no"',
			'allowtransparency="false" frameborder="0"></iframe>'
		].join('');
	}, 0);
};

Event.observe(window, 'load', function(){
	init();
});
Ext.apply(PageContext, {
	loadList : function(){
		init();
	},
	refreshList : function(){
		init();
	}
});