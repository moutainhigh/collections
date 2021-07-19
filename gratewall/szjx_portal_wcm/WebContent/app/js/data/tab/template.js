//模板标签信息
(function(){
	var oTabItem = {
		type : 'template',
		desc : wcm.LANG['TEMPLATE'] || '模板',
		url : WCMConstants.WCM6_PATH + 'template/template_list.html',
		rightIndex : '21-25,28',
		order : '3',
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
		items :[Ext.applyIf({}, oTabItem)]
	});
})();
(function(){
	var tabType = 'template';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'template/template_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();