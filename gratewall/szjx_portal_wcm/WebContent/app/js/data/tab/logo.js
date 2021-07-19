//管理LOGO标签信息
(function(){
	var oTabItem = {
		type : 'logo',
		desc : wcm.LANG['LOGO'] || '栏目Logo',
		url : WCMConstants.WCM6_PATH + 'logo/logo_list.jsp',
		fittable : false,
		rightIndex : '13',
		order : '8',
		extraCls : 'logoTabCls',
		renderUrl : function(search){
			var params = search.parseQuery();
			Ext.apply(params, {
				HostId : PageContext.hostId,
				HostType : PageContext.intHostType
			});
			return params;
		}
	}
	//TODO maybe remove some items
	/*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '8'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '8'}, oTabItem)]
	});
})();