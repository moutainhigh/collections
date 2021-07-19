/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_metadbfield',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.filterEnable = false;

/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.operEnable = false;
PageContext.setRelateType(
	!!getParameter("ChannelId") ? 'metadbfieldInChannel' :
				(!!getParameter("SiteId") ? 'metadbfieldInSite' : 'metadbfieldInRoot')
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METADBFIELD_19 || '个',
	TypeName :  wcm.LANG.METADBFIELD_21 || '元数据字段'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		wcm.domain.MetaDBFieldMgr['edit'](event);
	},		
	'delete' : function(event){
		wcm.domain.MetaDBFieldMgr['delete'](event);
	},
	'add' : function(event){
		wcm.domain.MetaDBFieldMgr['add'](event);
	}	
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'metadbtable'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_METADBFIELD
});

/*-------------检索框信息--------------*/
PageContext.searchEnable = true;
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : PageContext.searchEnable,
		/*检索项的内容*/
		items : [
			{name: 'queryAnotherName', desc: wcm.LANG.METADBFIELD_22 || '中文名称', type: 'string'},
			{name: 'queryName', desc: wcm.LANG.METADBFIELD_23 || '英文名称', type: 'string'},
			{name: 'CrUser', desc: wcm.LANG.METADBFIELD_24 || '创建者', type: 'string'},
			{name: 'FieldInfoId', desc: wcm.LANG.METADBFIELD_25 || '字段ID', type: 'int'},
			{name: 'classId', desc: wcm.LANG.METADBFIELD_26 || '分类法ID', type: 'int'}
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

ClassicList.cfg = {
	toolbar : [
		{
			id : 'metadbfield_add',
			fn : function(event, elToolbar){
				wcm.domain.MetaDBFieldMgr['add'](event);
			},
			name : wcm.LANG.METADBFIELD_27 || '新建',
			desc : wcm.LANG.METADBFIELD_28 || '新建一个字段',
			isHost:true,
			rightIndex : -1
		},{
			id : 'metadbfield_edit',
			fn : function(event, elToolbar){
				wcm.domain.MetaDBFieldMgr['edit'](event);
			},
			name : wcm.LANG.METADBFIELD_29 || '修改',
			desc : wcm.LANG.METADBFIELD_30 || '修改这个字段',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
				return event.getIds().length != 1;
			},
			rightIndex : -1
		}, {
			id : 'metadbfield_delete',
			fn : function(event, elToolbar){
				wcm.domain.MetaDBFieldMgr['delete'](event);
			},
			name : wcm.LANG.METADBFIELD_31 || '删除',
			desc : wcm.LANG.METADBFIELD_32 || '删除这个/些字段',
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
			name : wcm.LANG.METADBFIELD_33 || '刷新',
			desc : wcm.LANG.METADBFIELD_34 || '刷新列表'
		}
	],
	listTitle : '<span class="returnTableInfoList" id="returnTableInfoId">' + (wcm.LANG.METADBFIELD_35 || '返回') + '</span><span class="" id="tableInfo" style="font-weight:bold;"></span>',
	path : ''
}