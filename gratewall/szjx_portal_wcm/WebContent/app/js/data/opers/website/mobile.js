(function(){
	var fnIsVisible = function(event){
		var srcChannelId = event.getObj().getId();
		//通过同步的ajax请求，获取当前栏目是否可创建移动栏目
		var transport = ajaxRequest({
			url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isCanCreateMobile',
			method : 'GET',
			parameters : 'objectid='+srcChannelId +'&objecttype=103',
			asyn : false//执行同步请求
		});
		var json = parseXml(loadXml(transport.responseText));
		if(json.RESULT == 'false'){
			return false;
		}
		return true;
	}
	wcm.SysOpers.register({
		key : 'create4mobile',
		type : 'website',
		desc : '智能创建移动站点',
		title : '智能创建移动站点',
		rightIndex : -2,
		order : 2,
		fn : function(event){
			var siteId = event.getObj().getId();
			$openMaxWin(WCMConstants.WCM6_PATH + 'mobile/mobile_create_from_site.html?siteid='+siteId, window.location.hostname.replace(/\.|-/g, "_") + 'mobile_create_from_site', true, true);
		},
		isVisible : fnIsVisible
	});
})();