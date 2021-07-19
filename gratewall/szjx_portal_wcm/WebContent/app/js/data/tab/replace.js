//替换内容标签信息
(function(){
	var oTabItem = {
		type : 'replace',
		desc : wcm.LANG['REPLACE'] || '替换内容',
		url : WCMConstants.WCM6_PATH + 'replace/replace_list.html',
		rightIndex : '13',
		order : '6',
		
		isVisible : function(context){
			var host = context.host;
			if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
				return false;
			}
			return true;
		}
	}
	//TODO maybe remove some items
	/*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '6'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '6'}, oTabItem)]
	});
	/*wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({order : '6'}, oTabItem)]
	});*/
})();
(function(){
	var tabType = 'replace';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'replace/replace_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();