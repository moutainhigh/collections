//娉ㄥ唽鍏冩暟鎹殑璁板綍鍒楄〃鐨則ab
(function(){
	var tabType = 'document';
	var wrapper = function(context, tabItem){
		var bClassicList = 0;
		if(context.params['SITETYPE'] != 4)return tabItem;
		//尽管功能已经实现，但由于增大了测试范围，所以暂时先屏蔽元数据下的经典视图
		if($MsgCenter.getActualTop().m_bClassicList) bClassicList = 0;
		return Ext.applyIf({
			desc : wcm.LANG['METAVIEWDATA'] || '璧勬簮',
			url : WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_list_redirect.jsp?bClassicList=' + bClassicList ,
			isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
