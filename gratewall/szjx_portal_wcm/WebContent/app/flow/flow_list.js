/*-------------中间内容请求的服务相关信息--------------*/
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
//检索信息
Event.observe(window, 'load', function(){
	if(PageContext.hostType == WCMConstants.OBJ_TYPE_WEBSITE){
		Element.hide('divDispMode');
	}
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search',
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
//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
});
Ext.apply(PageContext.literator.params, {
	tracesitetype : true
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
		var right = event.getObj().getPropertyAsString("right");
		var canEdit = wcm.AuthServer.hasRight(right, 42);
		wcm.domain.FlowMgr[canEdit ? "edit" : "view"](event);
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
		_eFirer.title = (wcm.LANG['FLOW_46'] || '点击切换递归显示所有工作流\n当前为:递归显示');
		_eFirer.className = "view_mode_recursive";
	}else{
		Object.extend(PageContext.params,{"containsChildren":false});
		_eFirer.title = (wcm.LANG['FLOW_47'] || '点击切换递归显示所有工作流\n当前为:仅显示当前');
		_eFirer.className = "view_mode_normal";
	}
	 PageContext.loadList(PageContext.params);
}

function listchnlsusingme(event){
	var _id = event.getIds().join();
	FloatPanel.open(WCMConstants.WCM6_PATH +'flow/channel_list.html?FlowId='+_id, wcm.LANG['FLOW_59'] || '配置该工作流的栏目列表',500,250);
}
