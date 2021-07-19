/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_viewdocument',
	methodName : 'RecycleQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"HostType" : PageContext.intHostType,
		"hostId" : PageContext.hostId,
		"SiteIds" : (PageContext.intHostType==103)?PageContext.hostId:'',
		"ChannelIds" : (PageContext.intHostType==101)?PageContext.hostId:'',
		OrderBy : "WCMChnlDoc.DocOrderPri desc, WCMChnlDoc.OperTime desc"
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
			{name : 'DocTitle', desc : wcm.LANG.DOCTITLE || '文档标题', type : 'string'},
			{name : 'DocKeywords', desc : wcm.LANG.DOCKEYWORDS || '关键字', type : 'string'},
			{name : 'WCMChnlDoc.OperUser', desc : wcm.LANG.OPERUSER || '删除者', type : 'string'}
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
	!!PageContext.getParameter("ChannelId") ? "docrecycleInChannel" :
		(!!PageContext.getParameter("SiteId") ? "docrecycleInSite" : "docrecycleInRoot")
);
 

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.DOCRECYCLE_UNIT || '篇',
	TypeName : wcm.LANG.DOCRECYLE || '文档'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'view' : function(event){
		wcm.domain.DocRecycleMgr['view'](event);
	},
	'delete' : function(event){
		wcm.domain.DocRecycleMgr['delete'](event);
	},
	'restore' : function(event){
		wcm.domain.DocRecycleMgr['restore'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'docrecycle'
});


/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_DOCRECYCLE
});


//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 1
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		var topWin = $MsgCenter.getActualTop();
		var isDeletingAll = topWin.isDeletingAll;
		var isRestoringAll = topWin.isRestoringAll;
		topWin.isDeletingAll = null;
		topWin.isRestoringAll = null; 
		var dom = Element.first($('grid_body'));
		if(isDeletingAll && dom && Element.hasClassName(dom, 'grid_row')){
			Ext.Msg.alert(wcm.LANG.DOCRECYLE_24 || "部分文档所在的栏目已经在回收站,无法彻底删除这些文档!");
		}
		if(isRestoringAll && dom && Element.hasClassName(dom, 'grid_row')){
			Ext.Msg.alert(wcm.LANG.DOCRECYLE_28 || "部分文档所在的栏目已经在回收站,无法还原所有文档!");
		}
	}
});
