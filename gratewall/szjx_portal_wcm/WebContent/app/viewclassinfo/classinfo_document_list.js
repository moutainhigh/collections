/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_ClassInfoView',
	methodName : 'jQueryDocuments',
	initParams : {
		"FieldsToHTML" : "DocTitle,DocChannel.Name",
		"ChnlDocSelectFields" : "WCMChnlDoc.DOCKIND,WCMChnlDoc.DOCID,WCMChnlDoc.ChnlId,WCMChnlDoc.DocStatus,WCMChnlDoc.DocChannel,WCMChnlDoc.DocOrderPri,WCMChnlDoc.Modal,WCMChnlDoc.RecId",
		"DocumentSelectFields" : "DOCID,DocTitle,DocType,CrUser,CrTime,AttachPic,FLOWOPERATIONMARK",
		"classinfoId" : getParameter("HostId"),
		"withChildren" : true
	}
});


/*-------------指定列表上的过滤器--------------*/
PageContext.filterEnable = false;
function loadViewFilters(){
	PageContext.filterEnable = true;
	var filters = [
		{desc:wcm.LANG['FILTER_METAVIEWDATA_ALL']||'全部资源', type:0}
	];
	var oParams = {
		classinfoId : getParameter("HostId")
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call("wcm61_ClassInfoView", "queryViewsByClassInfo", oParams, false, function(_trans, _json){
		var aMetaViews = $a(_json, "METAVIEWS.METAVIEW") || [];
		for (var i = 0, nLen = aMetaViews.length; i < nLen; i++){
			var sViewName = $v(aMetaViews[i], "VIEWDESC");
			var nViewId = $v(aMetaViews[i], "VIEWINFOID");
			filters.push({desc:sViewName,type:nViewId, fn:loadViewDatas.bind(window, nViewId)});
		}
		PageContext.setFilters(filters, {displayNum : 4});
		if(wcm.PageFilter)wcm.PageFilter.init(PageContext.getFilters());
	});
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		loadViewFilters();
	}
});

function loadViewDatas(nViewId){
	window.location.href = "../viewclassinfo/metarecdata_list.html?filterType="+nViewId
		+"&HostType=classinfo&TabHostType=classinfo&HostId="+getParameter("HostId")+"&ViewId="+nViewId;
}


/*-------------检索框信息--------------*/
PageContext.searchEnable = true;
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name : 'DOCTITLE', desc : '文档标题', type : 'string'},
			{name : 'CrUser', desc : '创建者', type : 'string'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply(PageContext.params, params));
		}
	});
});

 
/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型;
*第二块和第三块面板的类型通过如下方式获取:
*如果列表页面没有选择记录,则为导航树节点对应的类型(如:website,channel);
*如果列表当前选中一条记录,则类型为当前列表的rowType(如:chnldoc);
*如果列表当前选中多条记录,则类型为当前列表的rowType+s(如:chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中;
*/
PageContext.setRelateType('notexists');

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METARECDATA_UNITNAME || '个',
	TypeName : wcm.LANG.METARECDATA_TYPENAME || '记录'
});


/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		wcm.domain.MetaRecDataMgr['edit'](event);
	},
	'view' : function(event){
		wcm.domain.MetaViewDataMgr['view'](event);
	},
	'delete' : function(event){
		wcm.domain.MetaViewDataMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中;
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'metarecdata'
});


/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_METARECDATA
});

