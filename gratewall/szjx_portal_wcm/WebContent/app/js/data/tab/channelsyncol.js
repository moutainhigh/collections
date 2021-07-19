//文档同步标签信息
(function(){
	var oTabItem = {
		type : 'channelsyncol',
		desc : wcm.LANG['DOCUMENTSYN'] || '文档同步',
		url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyncol_list.html',
		rightIndex : '13',
		order : '8'
		/*,
		isVisible : function(context){
			var host = context.host;
			if(Ext.isTrue(host.isVirtual)){
				return false;
			}
			return true;
		}//*/
	}
	/*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '8'}, oTabItem)]
	});*/
})();
(function(){
	var tabType = 'channelsyncol';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyncol_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();