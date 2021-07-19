//文档标签信息
(function(){
	var oTabItem = {
		type : 'document',
		desc : wcm.LANG.DOCUMENT || '文档',
		url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
		rightIndex : '30-34,38-39',
		order : 1
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({isdefault:true}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items : [oTabItem]
	});
})();
(function(){
	var tabType = 'document';
	var wrapper = function(context, tabItem){
		//if(context.params['SITETYPE']!=0)return tabItem;
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'document/document_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();