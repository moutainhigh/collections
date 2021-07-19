//权限标签信息
(function(){
	var oTabItem = {
		type : 'right',
		desc : wcm.LANG['RIGHTSET'] || '权限',
		fittable : false,
		url : WCMConstants.WCM6_PATH + 'auth/right_set.jsp',
		rightIndex : -1,//'30-34,38-39',
		order : '4.5',
		renderUrl : function(search){//为页面整理必要的参数,fittable为false时有效
			var params = search.parseQuery();
			Ext.apply(params, {
				ObjId : PageContext.hostId,
				ObjType : PageContext.intHostType
			});
			return params;
		}
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [oTabItem]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[oTabItem]
	});
})();