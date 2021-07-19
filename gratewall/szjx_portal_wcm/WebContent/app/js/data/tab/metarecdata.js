//版本管理标签信息
(function(){
	var oTabItem = {
		type : 'metarecdata',
		desc : wcm.LANG['METARECDATA'] || '记录列表',
		url : WCMConstants.WCM6_PATH + 'metarecdata/metarecdata_list.html',
		rightIndex : '-1',
		order : '8'
	}
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CLASSINFO,
		items : [Ext.applyIf({order : '8'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'metarecdata';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'metarecdata/metarecdata_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CLASSINFO, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'metarecdata';
	var wrapper = function(context, tabItem){
		if(context.params.VIEWID == "0" || context.params.FILTERTYPE){
			var sUrl = 'viewclassinfo/classinfo_document_list.html';
			if(context.params.VIEWID != "0"){
				sUrl = 'viewclassinfo/metarecdata_list.html';
			}
			return Ext.applyIf({
				url : WCMConstants.WCM6_PATH + sUrl
			}, tabItem);
		}
		return tabItem;
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CLASSINFO, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();