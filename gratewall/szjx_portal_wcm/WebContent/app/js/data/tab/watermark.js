//水印标签信息
(function(){
	var oTabItem = {
		type : 'watermark',
		desc : wcm.LANG['WATERMARK'] || '水印',
		url : WCMConstants.WCM6_PATH + 'watermark/watermark_thumb.html',
		rightIndex : '-1',
		order : '8',
		//*,
		isVisible : function(context){
			if(context.params['SITETYPE']!=1){
				return false;
			}
			return true;
		}//*/
	}
	//TODO maybe remove some items
	/*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '8'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
		items :[Ext.applyIf({order : '8'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'watermark';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'watermark/watermark_classic_thumb.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();