Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : true,
	gridDraggable : false,
	searchEnable : true,
	serviceId : 'wcm61_metadbfield',
	methodName : 'jSelectQuery',
	/**/
	objectType : WCMConstants.OBJ_TYPE_METADBFIELD,
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_METADBFIELD;
	},
	rowInfo : {
	}
});
Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		var bIsSite = !!getParameter("SiteId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'metadbfieldInChannel' :
				(bIsSite ? 'metadbfieldInSite' : 'metadbfieldInRoot')
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
			//TODO type filters here
		]);
		return filters;
	}()),
	pageTabs : (function(){
		if(!PageContext.tabEnable)return null;
		var tabs = wcm.PageTab.getTabs({
			hostType : PageContext.tabHostType,
			displayNum : 6,
			activeTabType : 'metadbfield'
		});
		return tabs;
	}()),
	gridFunctions : function(){
		var myMgr = wcm.domain.MetaDBFieldMgr;
		wcm.Grid.addFunction('edit', function(event){
			myMgr['edit'](event);
		});
		wcm.Grid.addFunction('delete', function(event){
			myMgr['delete'](event);
		});
	}
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METADBFIELD_19 || '个',
	TypeName : wcm.LANG.METADBFIELD_21 || '元数据字段'
});
//检索框信息
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		container : 'query_box', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : [
			{name: 'queryAnotherName', desc: wcm.LANG.METADBFIELD_22 || '中文名称', type: 'string'},
			{name: 'queryName', desc: wcm.LANG.METADBFIELD_23 || '英文名称', type: 'string'},
			{name: 'CrUser', desc: wcm.LANG.METADBFIELD_24|| '创建者', type: 'string'},
			{name: 'FieldInfoId', desc: wcm.LANG.METADBFIELD_25 || '字段ID', type: 'int'},
			{name: 'classId', desc: wcm.LANG.METADBFIELD_26 || '分类法ID', type: 'int'}
		],
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
});
//路径信息
Ext.apply(PageContext.literator, {
	enable : false,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});

ClassicList.cfg = {
toolbar : [
	{
		id : 'metadbfield_add',
		fn : function(event, elToolbar){
			wcm.domain.MetaDBFieldMgr['add'](event);
		},
		name :  wcm.LANG.METADBFIELD_27 || '新建',
		desc :  wcm.LANG.METADBFIELD_28 || '新建一个字段',
		rightIndex : -1,
		isDisabled : function(event){
			if(PageContext.getContext().params["HASRIGHT"]){
				return false;
			}
			return true;
		}
	}
]
}

//按钮
window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.METADBFIELD_1 || '确定',
			cmd : function(){
				var aSelectedRowIds = wcm.Grid.getRowIds();
				var cbr = wcm.CrashBoarder.get(window);
				var _viewId = getParameter("viewId");
				
				var myHelper = new com.trs.web2frame.BasicDataHelper;
				ProcessBar.start(wcm.LANG.METADBFIELD_44 || '进度执行中，请稍候...');
				myHelper.call("wcm61_metaview", "setViewFields", {DBFieldIds:aSelectedRowIds, viewId: _viewId}, true, function(transport, json){
					ProcessBar.exit();
					cbr.hide();
					cbr.notify();
					cbr.close();
				});
				return false;
			}
		},
		{
			text : wcm.LANG.METADBFIELD_2 || '取消'
		}
	]
};

