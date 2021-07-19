//视图标签信息
(function(){
	var oTabItem = {
		type : 'metaview',
		desc : wcm.LANG['METAVIEW'] || '视图',
		url : WCMConstants.WCM6_PATH + 'metaview/metaview_thumb.html',
		rightIndex : '-2',
		order : '4',
		isVisible : function(context){
			if(context.params['SITETYPE'] == '4' && (context.params['CHANNELTYPE'] == '0' || !context.params['CHANNELTYPE'] )){
				return true;
			}
			return false;	
		}
	}
	//*
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '5'}, oTabItem)]
	});//*/
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
		items : [Ext.applyIf({order : '1.2',rightIndex : '13'}, oTabItem)]
	});
})();
(function(){
	var tabType = 'metaview';
	var wrapper = function(context, tabItem){
		if(context.params['VIEWID'] || context.params['CHANNELID']){
			return Ext.applyIf({
				url : WCMConstants.WCM6_PATH + 'metaviewfield/metaviewfield_list.html'
			}, tabItem);
		}
		return tabItem;
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);	
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);	
})();//*/

(function(){
	var tabType = 'metaview';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		if(context.params['VIEWID'] || context.params['CHANNELID'])return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'metaview/metaview_classic_thumb.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'metaview';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		if(!context.params['VIEWID'] && !context.params['CHANNELID'])return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'metaviewfield/metaviewfield_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();