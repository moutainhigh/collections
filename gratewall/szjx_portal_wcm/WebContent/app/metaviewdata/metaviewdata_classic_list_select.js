Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_metaviewdata',
	methodName : 'queryViewDatasNoView',
	/**/
	objectType : WCMConstants.OBJ_TYPE_METAVIEWDATA,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_METAVIEWDATA;
	},
	rowInfo : {
		chnlId:true
	}
});
Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'metaviewdataInChannel' :
				(bIsSite ? 'metaviewdataInSite' : 'metaviewdataInRoot')
		});
		return context;
	},
	
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([			
		]);		
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'document'
		});
		return tabs;
	}()),
	gridFunctions : function(){
		var myMgr = wcm.domain.MetaViewDataMgr;	
		wcm.Grid.addFunction('delete', function(event){
			myMgr['deleteEntity'](event);
		});
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METAVIEWDATA_102 || '个',
	TypeName : wcm.LANG.METAVIEWDATA_103 || '资源'
});

function setSearchFieldInfo(_querItems){	
	if(arguments.callee.invoked){
		return;
	}
	arguments.callee.invoked = true;
	wcm.ListQuery.register({
		container : 'query_box', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : _querItems,
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
}

Event.observe(window, 'load', function(){
	ClassicList.makeLoad();	
	Element.hide(Element.first($("classic_cnt")));
});

window.m_cbCfg = {
	btns : [           
		{text : wcm.LANG.METAVIEWDATA_107 || '取消'}
	]
};

//路径信息
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350
});

ClassicList.cfg = {
	toolbar : [
		{
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.METAVIEWDATA_84 || '刷新',
			desc : wcm.LANG.METAVIEWDATA_85 || '刷新列表'
		}
	],
	path : ''
}

function unSelect(eleCheckBox){
	var aResult = [];
	aResult.id = 0;
	var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
	cbSelf.hide();
	cbSelf.notify(aResult);
}