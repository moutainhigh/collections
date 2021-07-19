
//废稿箱标签信息
(function(){
	var tabType = 'docrecycle';
	var wrapper = function(context, tabItem){
		if(context.params['SITETYPE']!=2)return tabItem;
		return Ext.applyIf({
			desc : wcm.LANG.videorecycle_101 || '废稿箱',
			url : WCMConstants.WCM6_PATH + 'video/videorecycle_thumb.html',
			rightIndex : '18',
			order : '9',
			isVisible : function(context){
				return true;
			}
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'docrecycle';
	var wrapper = function(context, tabItem){
		if(context.params['SITETYPE']!=2)return tabItem;
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'video/videorecycle_thumb.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();