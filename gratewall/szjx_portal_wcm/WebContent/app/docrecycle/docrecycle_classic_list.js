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
PageContext.filterEnable = false;
PageContext.setFilters([
	//TODO type filters here
]);

//检索框信息
PageContext.searchEnable = true;
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
PageContext.operEnable = false;
PageContext.setRelateType(
	!!PageContext.getParameter("ChannelId") ? "docrecycleInChannel" :
		(!!PageContext.getParameter("SiteId") ? "docrecycleInSite" : "docrecycleInRoot")
);
 

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.docrecycle_UNIT || '篇',
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
	width : 350,
	doBefore : function(){
		ClassicList.makeLoad();
	}
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});

/*-------------经典模式操作按钮的定义--------------*/
ClassicList.cfg = {
	toolbar : [
		{
			id : 'docrecycle_restore',
			fn : function(event, elToolbar){
				wcm.domain.DocRecycleMgr['restore'](event);
			},
			name : wcm.LANG.DOCRECYLE_12 || '还原文档',
			desc : wcm.LANG.DOCRECYLE_13 || '还原这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'docrecycle_restoreAll',
			fn : function(event, elToolbar){
				wcm.domain.DocRecycleMgr['restoreall'](event);
			},
			name : wcm.LANG.DOCRECYLE_14 || '还原所有文档',
			desc : wcm.LANG.DOCRECYLE_14 || '还原所有文档',
			isHost : true,
			rightIndex : 33
		}, {
			id : 'docrecycle_delete',
			fn : function(event, elToolbar){
				wcm.domain.DocRecycleMgr['delete'](event);
			},
			name : wcm.LANG.DOCRECYLE_15 || '彻底删除文档',
			desc : wcm.LANG.DOCRECYLE_16 || '彻底删除这篇/些文档',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'docrecycle_deleteAll',
			fn : function(event, elToolbar){
				wcm.domain.DocRecycleMgr['deleteall'](event);
			},
			name : wcm.LANG.DOCRECYLE_17 || '清空废稿箱',
			desc : wcm.LANG.DOCRECYLE_17 || '清空废稿箱',
			isHost : true,
			rightIndex : 33
		},{
			id : 'docrecycle_return',
			fn : function(event, elToolbar){
				var oParams = window.location.search.parseQuery();
				document.location.href = WCMConstants.WCM6_PATH + 'document/document_classic_list.html?' + $toQueryStr(oParams);
			},
			name : wcm.LANG.DOCRECYLE_18 || '返回文档列表',
			desc : wcm.LANG.DOCRECYLE_18 || '返回文档列表'
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.DOCRECYLE_19 || '刷新',
			desc : wcm.LANG.DOCRECYLE_20 || '刷新列表'
		}
	],
	listTitle : wcm.LANG.DOCRECYLE_21 || '废稿箱列表管理',
	path : ''
}