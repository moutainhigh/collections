//栏目标签信息
(function(){
	var oTabItem = {
		type : 'channel',
		desc : function(context){
			if(context && context.host && (context.host.objType==WCMConstants.OBJ_TYPE_CHANNELMASTER 
				|| context.host.objType==WCMConstants.OBJ_TYPE_CHANNEL)){
				return wcm.LANG['CHILDCHANNEL'] || '子栏目';
			}
			return wcm.LANG['CHANNEL'] || '栏目';
		},
		url : WCMConstants.WCM6_PATH + 'channel/channel_thumb.html',
		rightIndex : '-1',
		order : '2',
		isVisible : function(context){
			var host = context.host;
			if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
				return false;
			}
			return true;
		}
	}
	//TODO maybe remove some items
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({isdefault:true}, oTabItem)]
	});
})();
(function(){
	var tabType = 'channel';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'channel/channel_classic_thumb.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();