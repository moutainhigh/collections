//站点标签信息
(function(){
	var oTabItem = {
		type : 'website',
		desc : wcm.LANG['WEBSITE'] || '站点',
		url : WCMConstants.WCM6_PATH + 'website/website_thumb.html',
		rightIndex : '-1',
		order : '1'
	}
	//TODO maybe remove some items
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({isdefault:true}, oTabItem)]
	});
})();
(function(){
	var tabType = 'website';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'website/website_classic_thumb.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();