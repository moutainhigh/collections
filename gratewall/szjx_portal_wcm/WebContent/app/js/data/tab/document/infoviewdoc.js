//注册表单列表的tab
(function(){
	var tabType = 'document';
	var wrapper = function(context, tabItem){
		if(context.params['CHANNELTYPE']!=13)return tabItem;
		return Ext.applyIf({
			desc : wcm.LANG['INFOVIEWDOC'] || '表单',
			url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
			isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'document';
	var wrapper = function(context, tabItem){
		if(context.params['CHANNELTYPE']!=13)return tabItem;
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'infoview/infoviewdoc_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();