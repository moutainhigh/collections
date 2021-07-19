//热词管理标签信息
(function(){
	var oTabItem = {
		type : 'channelcontentlink',
		desc : wcm.LANG['CHANNELCONTENTLINK'] || '热词',
		url : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_list.html',
		rightIndex : '13',
		order : '10',
		//*,
		isVisible : function(context){
			var host = context.host;
			if(Ext.isTrue(host.isVirtual) || (context.params.SITETYPE != 0 && context.params.SITETYPE != 4)){
				return false;
			}
			return true;
		}//*/
	}
	//TODO maybe remove some items
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items : [Ext.applyIf({order : '6',rightIndex:'1'}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '10'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'channelcontentlink';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();