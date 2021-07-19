Ext.apply(PageContext, {
	serviceId : 'wcm61_flow',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		OrderBy : 'crtime desc',
		containsChildren : true,
		OwnerType : PageContext.intHostType,
		OwnerId : PageContext.hostId
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{
		desc : wcm.LANG['FOLW_LIST'] || '工作流列表',
		type : 0
	}
]);

Event.observe(window, 'load', function(){
		ClassicList.makeLoad();
		if(PageContext.hostType == WCMConstants.OBJ_TYPE_WEBSITE){
			Element.hide('divDispMode');
		}
        wcm.ListQuery.register({
            /*检索控件追加到的容器*/
            container : 'query_box', 
            /*是否追加“全部”这个检索项, default to false*/
            appendQueryAll : true,
            /*是否自动加载, default to true*/
            autoLoad : !getParameter('ChannelId'),
            /*检索项的内容*/
            items : [
                {name : 'FlowName', desc : wcm.LANG['FLOW_NAME'] || '工作流名称', type : 'string'},
                {name : 'FlowDesc', desc : wcm.LANG['FLOW_DESC'] || '工作流描述', type : 'string'},
                {name : 'CrUser', desc : wcm.LANG['SELECT_CRUSER'] || '查询创建者', type : 'string'},
                {name : 'FlowId', desc : wcm.LANG['FLOW_ID'] || '工作流ID', type : 'int'}
            ],
            /*执行检索按钮时执行的回调函数*/
            callback : function(params){
               PageContext.loadList(params);

            }
        });
    });
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});
Ext.apply(PageContext.literator.params, {
	tracesitetype : true,
	tracesite : true
});

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
	!!getParameter("ChannelId")? 'flowInChannel' : 
		(!!PageContext.getParameter("siteid")?'flowInSite':'flowInSys')
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['FLOW_2'] || '个',
	TypeName : wcm.LANG['FLOW'] || '工作流'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'listChnlsUsingMe' : function(event){
		listchnlsusingme(event);
	},
	'edit' : function(event){
		wcm.domain.FlowMgr.edit(event);
	}
});
/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'flow'
});

 /*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_FLOW,
	getContext : function(){
		var context = this.getContext0();
		Ext.apply(context, {
			OwnerType : PageContext.intHostType,
			OwnerId : PageContext.hostId
			
		});
		return context;
	}
});
//是否显示子对象
function switchChildrenDisp(){
	var _eFirer = $("divDispMode");
	if(_eFirer.className =="view_mode_normal"){
		Object.extend(PageContext.params,{"containsChildren":true});
		_eFirer.title = _eFirer.getAttribute('_alt', 2) + wcm.LANG['FLOW_46'] || '递归显示';
		_eFirer.className = "view_mode_recursive";
	}else{
		Object.extend(PageContext.params,{"containsChildren":false});	
		_eFirer.title = _eFirer.getAttribute('_alt', 2) + wcm.LANG['FLOW_47'] || '仅显示当前';
		_eFirer.className = "view_mode_normal";
	}
	 PageContext.loadList(PageContext.params);
}

function listchnlsusingme(event){
	var _id = event.getIds().join();
	FloatPanel.open(WCMConstants.WCM6_PATH +'flow/channel_list.html?FlowId='+_id,wcm.LANG['FLOW_59'] || '配置该工作流的栏目列表',500,250);
}
ClassicList.cfg = {
	toolbar : [
		{
			id : 'flow_new',
			fn : function(event, elToolbar){
				wcm.domain.FlowMgr['add'](event);
			},
			name : wcm.LANG.FLOW_50 || '新建',
			desc : wcm.LANG.FLOW_ADD || '新建工作流',
			rightIndex : 41
		}, {
			id : 'flow_import',
			fn : function(event, elToolbar){
				wcm.domain.FlowMgr['import'](event);
			},
			name : wcm.LANG['FLOW_76'] || '导入',
			desc : wcm.LANG['FLOW_37'] || '导入工作流',
			rightIndex : 45
		},{
			id : 'flow_edit',
			fn : function(event, elToolbar){
				wcm.domain.FlowMgr['edit'](event);
			},
			name : wcm.LANG['FLOW_77'] || '编辑',
			desc : wcm.LANG['FLOW_EDIT'] || '编辑这个工作流',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 42
		},{
			id : 'flow_delete',
			fn : function(event, elToolbar){
				wcm.domain.FlowMgr['delete'](event);
			},
			name : wcm.LANG.FLOW_DELETE1 || '删除',
			desc : wcm.LANG.FLOW_52 || '删除这个/些工作流',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 43
		},{
			id : 'flow_export',
			fn : function(event, elToolbar){
				wcm.domain.FlowMgr['export'](event);
			},
			name : wcm.LANG['FLOW_78'] || '导出',
			desc : wcm.LANG['FLOW_79'] || '导出这个/些工作流',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 44
		},{
			id : 'flow_assign',
			fn : function(event, elToolbar){
				wcm.domain.FlowMgr['assign'](event);
			},
			name : wcm.LANG['FLOW_80'] || '分配',
			desc : wcm.LANG['FLOW_81'] || '分配这个工作流到栏目',
			isDisabled : function(event){
				if(!event || event.length() != 1) return true;
			},
			rightIndex : 42
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.FLOW_REFRESH || '刷新',
			desc : wcm.LANG.FLOW_53 || '刷新列表'
		}
	],
	listTitle : wcm.LANG.FLOW_54 || '工作流列表管理',
	path : [
		'<span class="view_mode_recursive" id="divDispMode" title="',
		wcm.LANG.FLOW_61 || "点击切换递归显示所有工作流&#13;当前为: 递归显示",
		'" onclick="switchChildrenDisp()" _alt="',
		wcm.LANG.FLOW_62 || "点击切换递归显示所有工作流&#13;当前为: ",
		'" </span>'
			].join('')
}