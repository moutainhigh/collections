//管理顾问标签信息
(function(){
	var oTabItem = {
		type : 'advisor',
		desc : '顾问',
		url : WCMConstants.WCM6_PATH + 'advisor/advisor_list.jsp',
		fittable : false,
		rightIndex : '13',
		order : '9',
		renderUrl : function(search){
			var params = search.parseQuery();
			Ext.apply(params, {
				HostId : PageContext.hostId,
				HostType : PageContext.intHostType
			});
			return params;
		}
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '9'}, oTabItem)]
	});
})();