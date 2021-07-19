//注册图片列表的tab
(function(){
	var tabType = 'document';
	var wrapper = function(context, tabItem){
		if(context.params['SITETYPE']!=2)return tabItem;
		return Ext.applyIf({
			desc : wcm.LANG.video_1201 || '视频',
			url : WCMConstants.WCM6_PATH + 'video/video_thumb.html',
			rightIndex : '30-34,38-39',
			order : '1',
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
		if(context.params['SITETYPE']!=2)return tabItem;
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'video/video_thumb.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();