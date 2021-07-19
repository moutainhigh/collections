//工作流标签信息
(function(){
	var oTabItem1 = {
		type : 'flowdoc1',
		desc : wcm.LANG['FLOWDOC_FORDEAL'] || '待处理',
		url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=1',
		rightIndex : '-1',
		order : 1,
		isdefault : true
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
		items :[oTabItem1]
	});
	oTabItem2 = {
		type : 'flowdoc2',
		desc : wcm.LANG['FLOWDOC_DEALED'] || '已处理',
		url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=2',
		rightIndex : '-1',
		order : 2
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
		items :[oTabItem2]
	});
	oTabItem3 = {
		type : 'flowdoc3',
		desc : wcm.LANG['FLOWDOC_FROMME'] || '我发起',
		url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=3',
		rightIndex : '-1',
		order : 3
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
		items :[oTabItem3]
	});
})();
(function(){
	var tabType = 'flowdoc1';
	var wrapper = function(context, tabItem){
		//if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=1'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'flowdoc2';
	var wrapper = function(context, tabItem){
		//if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=2'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'flowdoc3';
	var wrapper = function(context, tabItem){
		//if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=3'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();