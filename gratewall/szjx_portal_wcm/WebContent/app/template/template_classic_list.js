/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_template',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"HostType" : PageContext.intHostType,
		"hostId" : PageContext.hostId,
		ContainsChildren : true,
		OrderBy : "lastModifiedTime desc"
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.filterEnable = false;
PageContext.setFilters([
	{desc:wcm.LANG.TEMPLATE_ALL||'全部模板', type:0},
	{desc:wcm.LANG.TEMPLATE_OUTLINE||'概览模板', type:11},
	{desc:wcm.LANG.TEMPLATE_XILAN||'细览模板', type:12},
	{desc:wcm.LANG.TEMPLATE_NESTING||'嵌套模板', type:10},
	{desc:wcm.LANG.TEMPLATE_USED||'已被使用的', type:18},
	{desc:wcm.LANG.TEMPLATE_NON||'未被使用的', type:19},
	{desc:wcm.LANG.TEMPLATE_CRBYME||'我创建的', type:4},
	{desc:wcm.LANG.TEMPLATE_LASTWEEK||'最近一周', type:6}
]);

/*-------------指定列表上的检索--------------*/
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
			{name : 'TempName', desc : wcm.LANG.TEMPLATE_TEMPNAME||'模板名称', type : 'string'},
			{name : 'TempDesc', desc : wcm.LANG.TEMPLATE_TEMPDESC||'模板描述', type : 'string'},
			{name : 'CrUser', desc : wcm.LANG.TEMPLATE_TEMPCRUSER||'创建者', type : 'string'},
			{name : 'TempId', desc : wcm.LANG.TEMPLATE_TEMPID||'模板Id', type : 'int'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			//alert(Object.parseSource(params));
			PageContext.loadList(params);
		}
	});
});

/*-------------页面递归显示子的操作--------------*/
function queryWithChildren(){
	if($("show_children").className == "unfold_mode"){
		Object.extend(PageContext.params,{"ContainsChildren":false});
		$("show_children").title = wcm.LANG.HIDTEMP || "点击切换递归显示所有模板&#13;当前为：仅显示当前";
		$("show_children").className = "fold_mode";
	}else{
		Object.extend(PageContext.params,{"ContainsChildren":true});	
		$("show_children").title = wcm.LANG.SHOWTEMP || "点击切换递归显示所有模板&#13;当前为：递归显示";
		$("show_children").className = "unfold_mode";
	}
	PageContext.loadList(PageContext.params);
};

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
	!!PageContext.getParameter("ChannelId") ? "templateInChannel" : "templateInSite"
);

/*-------------属性面板上的操作--------------*/
Ext.apply(PageContext, {
	contextMenuEnable : true,
	previewFolder : function(_tempType){
		var oMgr = wcm.domain.TemplateMgr;		
		oMgr.previewFolder(Ext.applyIf({tempType:_tempType},this.event));
	},
	showArrangeEmployment : function(_tempType){
		var oMgr = wcm.domain.TemplateMgr;
		oMgr.showArrangeEmployment(Ext.applyIf({tempType:_tempType}, this.event));
	}
});

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.UNIT || '个',
	TypeName : wcm.LANG.TEMPLATE || '模板'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'preview' : function(event){//单击列表上预览图标时执行的操作
		var oMgr = wcm.domain.TemplateMgr;
		oMgr.preview(event);
	},
	'edit' : function(event){//单击列表上标题链接时执行的操作
		var oMgr = wcm.domain.TemplateMgr;
		oMgr.edit(event);
	}

});


/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'template'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_TEMPLATE
});

//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});

/*
*点击模板findbyid页面（第三快面板上）的更多打开的页面
*/
function showCrashBoard(){
	wcm.CrashBoarder.get('crash-board').show({
		renderTo:document.body,
		maskable:true,
		title : wcm.LANG.TEMPLATE_USED_1 || '模板被引用情况',
		el : $('divBox'),
		left : '300px',
		top : '200px',
		width :'420px',
		height : '200px',
		btns : [
			{
				text : (wcm.LANG.CLOSE||'关闭') 
			}
		]
	});
}
/*
*监听模板保存时,做的刷新处理
*/
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afteredit : function(event){
		var context = event.getContext();
		if(context.eventType=='add' || context.eventType=='edit'){
			PageContext.refreshList(PageContext.params, [context.objId]);
			return false;
		}
	}
});

/*-------------经典模式操作按钮的定义--------------*/
ClassicList.cfg = {
	toolbar : [
		{
			id : 'template_new',
			fn : function(event, elToolbar){
				wcm.domain.TemplateMgr['new'](event);
			},
			name : wcm.LANG.TEMPLATE_32||'新建',
			desc : wcm.LANG.TEMPLATE_33||'新建一个模板',
			isHost : true,
			rightIndex : 21
		}, {
			id : 'template_import',
			fn : function(event, elToolbar){
				wcm.domain.TemplateMgr['import'](event);
			},
			name : wcm.LANG.TEMPLATE_34||'导入',
			desc : wcm.LANG.TEMPLATE_13||'导入模板',
			isHost : true,
			rightIndex : 21
		},{
			id : 'template_export',
			fn : function(event, elToolbar){
				wcm.domain.TemplateMgr['export'](event);
			},
			name : wcm.LANG.TEMPLATE_35||'导出',
			desc : wcm.LANG.TEMPLATE_36||'导出模板',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 24
		},{
			id : 'template_check',
			fn : function(event, elToolbar){
				wcm.domain.TemplateMgr['check'](event);
			},
			name : wcm.LANG.TEMPLATE_25||'模板校验',
			desc : wcm.LANG.TEMPLATE_25||'模板校验',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 25
		},{
			id : 'template_synch',
			fn : function(event, elToolbar){
				wcm.domain.TemplateMgr['synch'](event);
			},
			name : wcm.LANG.TEMPLATE_37||'同步附件',
			desc : wcm.LANG.TEMPLATE_37||'同步附件',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 28
		},{
			id : 'template_arrange',
			fn : function(event, elToolbar){
				var nType = event.getObj().getPropertyAsInt('tempType');
				PageContext.showArrangeEmployment(nType);
			},
			name : wcm.LANG.TEMPLATE_142||'分配',
			desc : wcm.LANG.TEMPLATE_142||'分配',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 24
		},{
			id : 'template_delete',
			fn : function(event, elToolbar){
				wcm.domain.TemplateMgr['delete'](event);
			},
			name : wcm.LANG.TEMPLATE_38||'删除',
			desc : wcm.LANG.TEMPLATE_39||'删除这个/些模板',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 22
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.TEMPLATE_40||'刷新',
			desc : wcm.LANG.TEMPLATE_41||'刷新列表'
		}
	],
	listTitle : wcm.LANG.TEMPLATE_42||'模板列表管理',
	path : ''
}