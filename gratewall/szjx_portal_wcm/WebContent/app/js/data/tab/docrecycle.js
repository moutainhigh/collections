//废稿箱标签信息
(function(){
	var oTabItem = {
		type : 'docrecycle',
		desc : wcm.LANG['DOCRECYCLE'] || '废稿箱',
		url : WCMConstants.WCM6_PATH + 'docrecycle/docrecycle_list.html',
		rightIndex : '18',
		order : '9',
		isVisible : function(context){
			var host = context.host;
			if(context.params['SITETYPE'] == '1' || context.params['SITETYPE'] == '2'){
				return false;
			}
			if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE']==0){
				return false;
			}
			return true;
		}//*/
	}
	//TODO maybe remove some items
	/*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '9'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '9'}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({order : '9'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'docrecycle';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'docrecycle/docrecycle_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();