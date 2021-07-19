//TrsServer标签信息
(function(){
	var oTabItem = {
		type : 'trsserver',
		desc : wcm.LANG['DOCUMENT'] || '文档',
		url : WCMConstants.WCM6_PATH + 'trsserver/trsserver_classic_list.html?ViewType=1', //'trsserver/trsserver_classic_list.html?ViewType=1',
		rightIndex : '-1',
		order : 1,
		isdefault : true
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_TRSSERVERLIST,
		items :[oTabItem]
	});
})();