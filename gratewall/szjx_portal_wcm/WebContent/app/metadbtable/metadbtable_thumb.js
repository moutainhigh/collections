Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : true,
	filterEnable : true,
	gridDraggable : false,
	searchEnable : true,
	listOrderEnable : true,
	serviceId : 'wcm61_metadbtable',
	methodName : 'jThumbQuery',
	objectType : WCMConstants.OBJ_TYPE_METADBTABLE,
	initParams : {
		"PageSize" : -1,
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

wcm.MyThumbItem = Ext.extend(wcm.ThumbItem, {
	itemType : function(){
		return WCMConstants.OBJ_TYPE_METADBTABLE;
	},
	itemInfo : {
		itemId : true,
		rightValue : true
	}			
});

Ext.apply(wcm.MyThumbItem, {
	type : "metadbtable"
});
wcm.ThumbItemMgr.registerType(wcm.MyThumbItem["type"], wcm.MyThumbItem);


wcm.ThumbItemMgr.createListeners(wcm.MyThumbItem, function(){
	this.on('dblclick', function(event){		
		$MsgCenter.$main({
			objId : 4,
			objType : WCMConstants.OBJ_TYPE_WEBSITEROOT,
			tabType : 'metadbtable',
			params  : "TableInfoId=" + this.getId()
		}).redirect();
	});	
});


var myThumbList = new wcm.ThumbList(wcm.MyThumbItem["type"]);

/*
myThumbList.addCmds({
	edit : wcm.domain.MetaDBTableMgr['edit'],
	delete : wcm.domain.MetaDBTableMgr['delete']
});
//*/

Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'metadbtableInChannel' :
				(bIsSite ? 'metadbtableInSite' : 'metadbtableInRoot')
		});
		return context;
	},
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		//防止在视图和元数据标签之间切换时viewid传入元数据thumb列表。
		if(this.params["VIEWID"]){
			 this.params["VIEWID"] = 0;
		}
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
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
			activeTabType : 'metadbtable'
		});
		return tabs;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METADBTABLE_6 || '个',
	TypeName : wcm.LANG.METADBTABLE_7 || '元数据'
});

wcm.ListQuery.register({
	container : 'search', 
	appendQueryAll : true,
	autoLoad : PageContext.searchEnable,
	items : [		
		{name: 'queryTableAnotherName', desc: wcm.LANG.METADBTABLE_8 || '元数据别名', type: 'string'},
		{name: 'queryTableName', desc: wcm.LANG.METADBTABLE_9 || '元数据名称', type: 'string'},
		{name: 'queryTableDesc', desc: wcm.LANG.METADBTABLE_10 || '元数据描述', type: 'string'},
		{name: 'CrUser', desc: wcm.LANG.METADBTABLE_11 || '创建者', type: 'string'},
		{name: 'queryTableId', desc: wcm.LANG.METADBTABLE_12 || '元数据ID', type: 'int'}		
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
		{name : 'default', desc : wcm.LANG.METADBTABLE_13 || '默认排序', isDefault : true, isActive : true},
		{name : 'AnotherName', desc : wcm.LANG.METADBTABLE_14 || '元数据别名'},		
		{name : 'crtime', desc : wcm.LANG.METADBTABLE_15 || '创建时间'},
		{name : 'cruser', desc : wcm.LANG.METADBTABLE_16 || '创建者'}
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