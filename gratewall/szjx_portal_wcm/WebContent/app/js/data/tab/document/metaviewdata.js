//注册元数据的记录列表的tab
(function(){
	var tabType = 'document';
	var wrapper = function(context, tabItem){
		var bClassicList = 0;
		if(context.params['SITETYPE'] != 4)return tabItem;
		//���ܹ����Ѿ�ʵ�֣������������˲��Է�Χ��������ʱ������Ԫ�����µľ�����ͼ
		if($MsgCenter.getActualTop().m_bClassicList) bClassicList = 0;
		return Ext.applyIf({
			desc : wcm.LANG['METAVIEWDATA'] || '资源',
			url : WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_list_redirect.jsp?bClassicList=' + bClassicList ,
			isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
