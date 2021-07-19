//分类法标签信息
(function(){
	var oTabItem = {
		type : 'classinfo',
		desc : wcm.LANG['CLASSINFO'] || '分类法',
		url : WCMConstants.WCM6_PATH + 'classinfo/classinfo_list.html',
		rightIndex : '-2',
		order : '6',
		isVisible : function(context){			
			return context.params['SITETYPE'] == '4';			
		}
	}
	
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '6'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'classinfo';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'classinfo/classinfo_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();