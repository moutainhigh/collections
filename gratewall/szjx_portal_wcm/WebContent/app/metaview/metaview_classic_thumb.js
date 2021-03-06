Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	searchEnable : true,
	listOrderEnable : true,
	serviceId : 'wcm61_metaview',
	methodName : 'jThumbQuery',
	objectType : WCMConstants.OBJ_TYPE_METAVIEW,
	initParams : {
		"PageSize" : -1,
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_METAVIEW;
	},
	itemInfo : {
		itemId : true,
		right : true,
		isSingleTable : true
	}			
});

Ext.apply(wcm.MyThumbItem, {
	type : "metaview"
});
wcm.ThumbItemMgr.registerType(wcm.MyThumbItem["type"], wcm.MyThumbItem);


wcm.ThumbItemMgr.createListeners(wcm.MyThumbItem, function(){

	this.on('dblclick', function(event){
		$MsgCenter.$main({
			objId : 4,
			objType : WCMConstants.OBJ_TYPE_WEBSITEROOT,
			tabType : 'metaview',
			params : 'ViewId=' + this.getId()
		}).redirect();
	});
	//TODO...
});


var myThumbList = new wcm.ThumbList(wcm.MyThumbItem["type"]);

/*
myThumbList.addCmds({
	edit : wcm.domain.MetaViewMgr['edit'],
	delete : wcm.domain.MetaViewMgr['delete']
});
//*/

Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'metaviewInChannel' :
				(bIsSite ? 'metaviewInSite' : 'metaviewInRoot')
		});
		return context;
	},
	/**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		Ext.applyIf(this.params, {
			HostType : PageContext.intHostType,
			hostId : PageContext.hostId
		});
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	//*/
	/**/
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([
			//TODO type filters here
		]);
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'metaview'
		});
		return tabs;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METAVIEW_UNITNAME||'???',
	TypeName : wcm.LANG.METAVIEW_TYPENAME||'??????'
});

Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		container : 'query_box', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : [
			{name: 'queryViewDesc', desc: wcm.LANG.METAVIEW_VIEWDESC||'????????????', type: 'string'},
			{name: 'CrUser', desc: wcm.LANG.METAVIEW_CRUSER||'?????????', type: 'string'},
			{name: 'queryViewId', desc: wcm.LANG.METAVIEW_VIEWID||'??????ID', type: 'int'}
		],
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});

	wcm.ListOrder.register({
		container : 'list-order-box', 
		appendTip : false,
		autoLoad : PageContext.listOrderEnable,
		items : [
			{name : 'default', desc : wcm.LANG.METAVIEW_DEFAULTORDER||'????????????', isDefault : true, isActive : true},
			{name : 'ViewDesc', desc : wcm.LANG.METAVIEW_VIEWDESC||'????????????'},
			{name : 'CrTime', desc : wcm.LANG.METAVIEW_CRTIME||'????????????'},
			{name : 'CrUser', desc : wcm.LANG.METAVIEW_CRUSER||'?????????'}
		],
		callback : function(sOrder){
			PageContext.loadList(Ext.apply(PageContext.params, {
				orderBy : sOrder.indexOf("default") == -1?sOrder:"",
				SelectIds : myThumbList.getIds()
			}));
		}
	});
});

Ext.apply(PageContext.literator, {
	enable : true
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});

ClassicList.cfg = {
	toolbar : [
		{
			id : 'metaview_new',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewMgr.add(event);
			},
			name : wcm.LANG.METAVIEW_BUTTON_2 || '??????',
			desc : wcm.LANG.METAVIEW_PROCESS_1 || '??????????????????',
			isHost:true,
			rightIndex : -1
		},{
			id : 'metaview_edit',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewMgr['edit'](event);
			},
			name : wcm.LANG.METAVIEW_BUTTON_3 || '??????',
			desc : wcm.LANG.METAVIEW_PROCESS_2 || '??????????????????',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
				return event.getIds().length != 1;
			},
			rightIndex : -1
		},{
			id : 'metaview_delete',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewMgr['delete'](event);
			},
			name : wcm.LANG.METAVIEW_BUTTON_4 || '??????',
			desc : wcm.LANG.METAVIEW_BUTTON_5 || '????????????/?????????',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : -1
		},{
			id : 'metaview_import',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewMgr['import'](event);
			},
			name : wcm.LANG.METAVIEW_BUTTON_9 || '??????',
			desc : wcm.LANG.METAVIEW_BUTTON_10 || '????????????',
			isHost:true,
			rightIndex : -1
		},{
			id : 'metaview_export',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewMgr['export'](event);
			},
			name : wcm.LANG.METAVIEW_BUTTON_11 || '??????',
			desc : wcm.LANG.METAVIEW_BUTTON_12 || '????????????/?????????',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : -1
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.METAVIEW_BUTTON_6 || '??????',
			desc : wcm.LANG.METAVIEW_BUTTON_7 || '????????????'
		}
	],
	listTitle : wcm.LANG.METAVIEW_BUTTON_8 || '??????????????????',
	path : ''
}