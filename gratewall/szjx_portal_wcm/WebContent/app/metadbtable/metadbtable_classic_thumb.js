Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : false,
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
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name: 'queryTableAnotherName', desc: wcm.LANG.METADBTABLE_8 || '元数据别名', type: 'string'},
			{name: 'queryTableName', desc: wcm.LANG.METADBTABLE_9 || '元数据名称', type: 'string'},
			{name: 'queryTableDesc', desc: wcm.LANG.METADBTABLE_10 || '元数据描述', type: 'string'},
			{name: 'CrUser', desc: wcm.LANG.METADBTABLE_11 || '创建者', type: 'string'},
			{name: 'queryTableId', desc: wcm.LANG.METADBTABLE_12 || '元数据ID', type: 'int'}		
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			//alert(Object.parseSource(params));
			PageContext.loadList( Ext.apply( {
				//some params must remember here
			},params));
		}
	});
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

ClassicList.cfg = {
	toolbar : [
		{
			id : 'metatable_new',
			fn : function(event, elToolbar){
				wcm.domain.MetaDBTableMgr['add'](event);
			},
			name : wcm.LANG.METADBTABLE_17 || '新建',
			desc : wcm.LANG.METADBTABLE_18 || '新建一个元数据',
			isHost:true,
			rightIndex : -1
		},{
			id : 'metatable_edit',
			fn : function(event, elToolbar){
				wcm.domain.MetaDBTableMgr['edit'](event);
			},
			name : wcm.LANG.METADBTABLE_19 || '修改',
			desc : wcm.LANG.METADBTABLE_20 || '修改这个元数据',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;				
				return event.getIds().length != 1;
			},
			rightIndex : -1
		}, {
			id : 'metatable_delete',
			fn : function(event, elToolbar){
				wcm.domain.MetaDBTableMgr['delete'](event);
			},
			name : wcm.LANG.METADBTABLE_21 || '删除',
			desc : wcm.LANG.METADBTABLE_22 || '删除这个/些元数据',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : -1
		},{
			id : 'metatable_view',
			fn : function(event, elToolbar){
					wcm.domain.MetaDBTableMgr['build'](event);
				},
			name : wcm.LANG.METADBTABLE_23 || '生成视图',
			desc : wcm.LANG.METADBTABLE_23 || '生成视图',
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
			name : wcm.LANG.METADBTABLE_24|| '刷新',
			desc : wcm.LANG.METADBTABLE_25 || '刷新列表'
		}
	],
	listTitle : wcm.LANG.METADBTABLE_26 || '元数据列表',
	path : ''
}