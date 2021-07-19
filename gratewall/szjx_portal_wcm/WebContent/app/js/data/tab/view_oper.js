//权限标签信息
(function(){
	var oTabItem = {
		type : 'viewoper',
		desc : wcm.LANG['VIEWOPER'] || '访问控制',
		fittable : false,
		url : WCMConstants.WCM6_PATH + 'auth/view_oper_set.jsp',
		rightIndex : -1,//可见和可操作
		order : 4,
		renderUrl : function(search){//为页面整理必要的参数,fittable为false时有效
			var params = search.parseQuery();
			Ext.apply(params, {
				ObjId : PageContext.hostId,
				ObjType : PageContext.intHostType,
				FilterRight : 0//是否过滤不能管理的用户及组织
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
		items :[ oTabItem]
	});
})();