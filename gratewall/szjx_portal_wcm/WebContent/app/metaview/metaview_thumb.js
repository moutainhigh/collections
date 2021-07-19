Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : true,
	gridDraggable : false,
	searchEnable : true,
	listOrderEnable : true,
	serviceId : 'wcm61_metaview',
	methodName : 'jThumbQuery',
	objectType : WCMConstants.OBJ_TYPE_METAVIEW,
	initParams : {
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
	UnitName : wcm.LANG.METAVIEW_UNITNAME||'个',
	TypeName : wcm.LANG.METAVIEW_TYPENAME||'视图'
});

wcm.ListQuery.register({
	container : 'search', 
	appendQueryAll : true,
	autoLoad : PageContext.searchEnable,
	items : [
		{name: 'queryViewDesc', desc: wcm.LANG.METAVIEW_VIEWDESC||'视图名称', type: 'string'},
		{name: 'CrUser', desc: wcm.LANG.METAVIEW_CRUSER||'创建者', type: 'string'},
		{name: 'queryViewId', desc: wcm.LANG.METAVIEW_VIEWID||'视图ID', type: 'int'}
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
		{name : 'default', desc : wcm.LANG.METAVIEW_DEFAULTORDER||'默认排序', isDefault : true, isActive : true},
		{name : 'ViewDesc', desc : wcm.LANG.METAVIEW_VIEWDESC||'视图名称'},
		{name : 'CrTime', desc : wcm.LANG.METAVIEW_CRTIME||'创建时间'},
		{name : 'CrUser', desc : wcm.LANG.METAVIEW_CRUSER||'创建者'}
	],
	callback : function(sOrder){
		PageContext.loadList(Ext.apply(PageContext.params, {
			orderBy : sOrder.indexOf("default") == -1?sOrder:"",
			SelectIds : myThumbList.getIds()
		}));
	}
});

Ext.apply(PageContext.literator, {
	enable : true
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});