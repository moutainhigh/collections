//导读标签信息
(function(){
	var oTabItem = {
		type : 'region',
		desc : wcm.LANG.region_2345 || '导读',
		url : WCMConstants.WCM6_PATH + 'region/region_list.html',
		rightIndex : '-1',
		order : '6',
		isVisible : function(context){
			var host = context.host;
			if(Ext.isTrue(host.isVirtual)){
				return false;
			}
			if(context.params['SITETYPE']==0 || context.params['SITETYPE']==4){
				return true;
			}
			return false;
		}
	}
	//TODO maybe remove some items
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items :[Ext.applyIf({rightIndex:48}, oTabItem)]// 导读的权限位改为48
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({rightIndex:48}, oTabItem)]// 导读的权限位改为48
	});
})();
(function(){
	var tabType = 'region';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'region/region_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();