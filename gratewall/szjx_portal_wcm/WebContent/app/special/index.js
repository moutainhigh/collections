Event.observe(window, 'load', function(){
	var bAdvanceManageStyle = getParameter("AdvanceManageStyle") == 1;
	var advanceManageStyleEl = $('AdvanceManageStyle');
	if(bAdvanceManageStyle){
		Element.show(advanceManageStyleEl);
	}else{
		Element.hide(advanceManageStyleEl);
	}
	Event.observe('nav-tabs', 'click', function(event){
		//set the new url for the list.
		var dom = Event.element(event);
		var sUrl = dom.getAttribute('url', 2);
		$('list').contentWindow.location.href = sUrl;
		refreshTabWithUrl(sUrl);	
	});

	refreshTabWithUrl($('list').contentWindow.location.href);
	initUserName();
});

function refreshTabWithUrl(sUrl){
	//toggle the active class for the nav tab.
	var dom = Element.first($('nav-tabs'));
	while(dom){
		Element.removeClassName(dom, 'active');
		if(sUrl.endsWith(dom.getAttribute('url', 2))){
			Element.addClassName(dom, 'active');
		}
		dom = Element.next(dom);
	}
}

function initUserName(){
	var sUserName = decodeURIComponent(getParameter("userName"))||" ";
	$('user').innerHTML = sUserName;
}