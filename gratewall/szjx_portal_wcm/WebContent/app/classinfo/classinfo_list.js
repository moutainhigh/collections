/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_classinfo',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"orderby" : "crtime desc"
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
PageContext.setRelateType(
	!!getParameter("ChannelId") ? 'classinfoInChannel' :
				(!!getParameter("SiteId") ? 'classinfoInSite' : 'classinfoInRoot')
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.CLASSINFO_2 || '个',
	TypeName : wcm.LANG.CLASSINFO_3 || '分类法'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		wcm.domain.classinfoMgr['edit'](event);
	},
	'delete' : function(event){
		wcm.domain.classinfoMgr['delete'](event);
	},
	'config' : function(event){
		wcm.domain.classinfoMgr['config'](event);
	}		
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'classinfo'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CLASSINFO
});

/*-------------检索框信息--------------*/
PageContext.searchEnable = true;
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : PageContext.searchEnable,
		/*检索项的内容*/
		items : [
			{name: 'queryName', desc: wcm.LANG.CLASSINFO_4 || '分类法名称', type: 'string'},
			{name: 'queryDesc', desc: wcm.LANG.CLASSINFO_5 || '分类法描述', type: 'string'},
			{name: 'CrUser', desc: wcm.LANG.CLASSINFO_6 || '创建者', type: 'string'},
			{name: 'queryId', desc: wcm.LANG.CLASSINFO_7 || '分类法ID', type: 'int'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
});

function listeningForMyObjs(){
	$MsgCenter.on({
		objType : PageContext.objectType,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
		afteradd : function(event){
			var host = event.getHost();
			if(host && host.context) return;
			PageContext.refreshList(PageContext.params, [event.getIds()]);
		},
		afteredit : function(event){
			var id = event.getObj().getId();
			if(!$("tr_" + id)) return;
			PageContext.refreshList(PageContext.params, 
				PageContext.getIds ? PageContext.getIds(event) : event.getIds()
			);
		},
		afterdelete : function(event){
			var id = event.getObj().getId();
			if(!$("tr_" + id)) return;
			PageContext.loadList(PageContext.params);
		}
	});
}