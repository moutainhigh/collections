//站点回收站标签信息
(function(){
	var oTabItem = {
		type : 'siterecycle',
		desc : wcm.LANG['SITERECYCLE'] || '站点回收站',
		url : WCMConstants.WCM6_PATH + 'siterecycle/siterecycle_list.html',
		rightIndex : '-1,2',
		order : '96',
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
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '96'}, oTabItem)]
	});//
	/*wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '0'}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({order : '0'}, oTabItem)]
	});*/
})();
(function(){
	var tabType = 'siterecycle';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'siterecycle/siterecycle_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();