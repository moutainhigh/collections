//模板标签信息
(function(){
	var oTabItem = {
		type : 'templateArg',
		desc : wcm.LANG['TEMPLATEARG'] || '模板变量',
		url : WCMConstants.WCM6_PATH + 'template/template_arg_list.html',
		rightIndex : '53',
		order : '100'
		/*
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
		items : [Ext.applyIf({order : '100'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '100'}, oTabItem)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({order : '100'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'templateArg';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'template/template_arg_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();