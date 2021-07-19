//站点分发标签信息
(function(){
	var oTabItem = {
		type : 'publishdistribution',
		desc : wcm.LANG['PUBLISHDISTRIBUTION'] || '站点分发',
		url : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_list.html',
		rightIndex : '1',
		order : '7'
		/*,
		isVisible : function(context){
			var host = context.host;
			if(Ext.isTrue(host.isVirtual)){
				return false;
			}
			return true;
		}//*/
	}
	//TODO maybe remove some items
	/*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '7'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({order : '7'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'publishdistribution';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();