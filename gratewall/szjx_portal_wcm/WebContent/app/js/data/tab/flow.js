//工作流标签信息
(function(){
	var oTabItem = {
		type : 'flow',
		desc : wcm.LANG['FLOW'] || '工作流',
		url : WCMConstants.WCM6_PATH + 'flow/flow_list.html',
		rightIndex : '41-45',
		order : '5',
		isVisible : function(context){
			var host = context.host;
			if(Ext.isTrue(host.isVirtual)){
				return false;
			}
			return true;
		}
	}
	//TODO maybe remove some items
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({url : WCMConstants.WCM6_PATH + 'flow/flow_chnl_list.html',
							  rightIndex : '13,41-45'
							}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items :[Ext.applyIf({order : '3'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'flow';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'flow/flow_classic_list.html'
		}, tabItem);
	}
	var wrapper1 = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'flow/flow_classic_chnl_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper1);
})();