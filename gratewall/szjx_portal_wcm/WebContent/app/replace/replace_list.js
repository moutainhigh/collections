/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_replace',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//TODO type filters here
]);

//检索框信息
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name : 'replacename', desc : wcm.LANG.REPLACENAME || '标题', type : 'string'},
			{name : 'replacecontent', desc : wcm.LANG.REPLACECONTENT || '内容', type : 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(params);
		}
	});
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
	!!PageContext.getParameter("ChannelId") ? "replaceInChannel" :
		(!!PageContext.getParameter("SiteId") ? "replaceInSite" : "replaceInRoot")
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.REPLACE_UNIT||'个',
	TypeName : wcm.LANG.REPLACE||'替换内容'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		wcm.domain.ReplaceMgr['edit'](event);
	},
	'delete' : function(event){
		wcm.domain.ReplaceMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'replace'
});


/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_REPLACE
});

//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
});
Ext.apply(PageContext.literator.params, {
	tracesite : false
});