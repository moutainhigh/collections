//元数据标签信息
(function(){
	var oTabItem = {
		type : 'metadbtable',
		desc : wcm.LANG['METADBTABLE'] || '元数据',
		url : WCMConstants.WCM6_PATH + 'metadbtable/metadbtable_thumb.html',
		rightIndex : '-2',
		order : '4',
		isVisible : function(context){			
			return context.params['SITETYPE'] == '4';			
		}		
	}	
	
	wcm.TabManager.register({
		hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
		items : [Ext.applyIf({order : '4'}, oTabItem)]
	});
})();

(function(){
	var tabType = 'metadbtable';
	var wrapper = function(context, tabItem){
		if($MsgCenter.getActualTop().m_bClassicList)return tabItem;
		if(!context.params['TABLEINFOID'])return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'metadbfield/metadbfield_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);	
})();//*/
(function(){
	var tabType = 'metadbtable';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		if(context.params['TABLEINFOID'])return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'metadbtable/metadbtable_classic_thumb.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
	var tabType = 'metadbtable';
	var wrapper = function(context, tabItem){
		if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
		if(!context.params['TABLEINFOID'])return tabItem;
		return Ext.applyIf({
			url : WCMConstants.WCM6_PATH + 'metadbfield/metadbfield_classic_list.html'
		}, tabItem);
	}
	var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
	wcm.TabManager.createWrapper(oTabItem, wrapper);	
})();//*/