//短消息标签信息
(function(){
	var oTabItem1 = {
		type : 'message0',
		desc : wcm.LANG['MESSAGE_tab_34'] || '最新消息',
		url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=0',
		rightIndex : '-1',
		order : '1',
		isdefault : true
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
		items : [Ext.applyIf({order : '3'}, oTabItem)]
	});//*/
	oTabItem2 = {
		type : 'message2',
		desc : wcm.LANG['MESSAGE_tab_35'] || '已收消息',
		url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=2',
		rightIndex : '-1',
		order : 2
	}
	oTabItem3 = {
		type : 'message1',
		desc : wcm.LANG['MESSAGE_tab_36'] || '已发消息',
		url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=1',
		rightIndex : '-1',
		order : 3
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
		items : [Ext.applyIf({order : '1'}, oTabItem1)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
		items : [Ext.applyIf({order : '2'}, oTabItem2)]
	});
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
		items :[Ext.applyIf({order : '3'}, oTabItem3)]
	});
})();
(function(){
	var tabType = 'message0';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=0'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'message2';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=2'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'message1';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=1'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();