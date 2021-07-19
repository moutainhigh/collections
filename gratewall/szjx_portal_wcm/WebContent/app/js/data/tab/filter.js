//管理过滤器标签信息
(function(){
	var oTabItem = {
		type : 'filter',
		desc : '筛选器',
		url : WCMConstants.WCM6_PATH + 'filter/filter_list.jsp',
		fittable : false,
		rightIndex : '13',
		order : '10',
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
		items : [Ext.applyIf({order : '10'}, oTabItem)]
	});
})();