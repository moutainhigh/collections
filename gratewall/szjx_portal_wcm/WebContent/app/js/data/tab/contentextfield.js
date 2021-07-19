//扩展字段标签信息
(function(){
	var oTabItem = {
		type : 'contentextfield',
		desc : wcm.LANG['CONTENTEXTFIELD'] || '扩展字段',
		url : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_list.html',
		rightIndex : 19,
		order : '7',
		isVisible : function(context){
			var host = context.host;
			//排除掉表单栏目
			if(Ext.isTrue(host.isVirtual) || Ext.isTrue(context.params.CHANNELTYPE == 13)){
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
		items :[Ext.applyIf({order : '6'}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items :[Ext.applyIf({order : '3'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'contentextfield';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();