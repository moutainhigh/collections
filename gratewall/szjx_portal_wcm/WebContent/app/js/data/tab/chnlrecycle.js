//栏目回收站标签信息
(function(){
	var oTabItem = {
		type : 'chnlrecycle',
		desc : wcm.LANG['CHNLRECYCLE'] || '栏目回收站',
		url : WCMConstants.WCM6_PATH + 'chnlrecycle/chnlrecycle_list.html',
		rightIndex : '-1,12',
		order : '98'
		/*,
		isVisible : function(context){
			var host = context.host;
			if(host.isVirtual){
				return false;
			}
			return true;
		}//*/
	}
	//TODO maybe remove some items
	/*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '0'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '98'}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({order : '97'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'chnlrecycle';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'chnlrecycle/chnlrecycle_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();