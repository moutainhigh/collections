/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_metarecdata',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		'HostType' : PageContext.intHostType,
		'ClassInfoIds' : PageContext.hostId
	}
});

/*-------------指定列表上的过滤器--------------*/
/*-------------指定列表上的过滤器--------------*/
PageContext.filterEnable = false;
function loadViewFilters(){
	PageContext.filterEnable = true;
	var filters = [
		{desc:wcm.LANG['FILTER_METAVIEWDATA_ALL']||'全部资源', type:0,fn:loadAllViewDatas}
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
			filters.push({desc:sViewName, type:nViewId, fn:loadViewDatas.bind(window, nViewId)});
		}
		PageContext.setFilters(filters, {displayNum : 4,filterType : getParameter('filterType')||0});
		if(wcm.PageFilter)wcm.PageFilter.init(PageContext.getFilters());
	});
}

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		loadViewFilters();
	}
});
function loadAllViewDatas(){
	window.location.href = "../viewclassinfo/classinfo_document_list.html?filterType=0&HostType=classinfo&TabHostType=classinfo&ViewId=0&HostId="+getParameter("HostId");
}
function loadViewDatas(nViewId){
	window.location.href = "../viewclassinfo/metarecdata_list.html?filterType="+nViewId
		+"&HostType=classinfo&TabHostType=classinfo&HostId="+getParameter("HostId")+"&ViewId="+nViewId;
}

/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.setRelateType('metarecdataInClassinfo');

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
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'metarecdata'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_METARECDATA
});

/*-------------检索框信息--------------*/
PageContext.searchEnable = true;
function getSearchFieldInfo(queryItem){
	if(arguments.callee.invoked){
		return;
	}
	arguments.callee.invoked = true;
	if(queryItem.length > 0){
		wcm.ListQuery.register({
			container : 'search', 
			appendQueryAll : true,
			autoLoad : PageContext.searchEnable,
			items : queryItem,
			callback : function(params){
				PageContext.loadList(Ext.apply({
					//some params must remember here
				}, params));
			}
		});
		
		//高级检索
		var dom = document.createElement("td");
		dom.className = 'advance_search';
		var tds = $('search').getElementsByTagName("td");
		var lastTd = tds[tds.length - 1];
		lastTd.parentNode.appendChild(dom);
		//Element.first($('search')).style.width = '280px';

		Event.observe(dom, 'click', function(event){
			var objectid = PageContext.params['OBJECTID'] || 0;
			var urlParams = "ViewId=" + PageContext.params['VIEWID'] + "&ObjectId=" + objectid ;
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'metaviewdata/advance_search.jsp?' + urlParams,
				title : wcm.LANG.METARECDATA_ADVANCESEARCH || '高级检索对象',
				callback : function(params){
					PageContext.loadList(Ext.apply({
					//some params must remember here
					}, params));
				}
			});
		});
		
	}
};
/*   操作面板需要执行相关函数 */
Ext.apply(PageContext, {
	//gridDraggable : true,
	//在属性面板中，构造属性保存时，需要提交的参数
	_buildParams : function(wcmEvent, actionType){
		if(wcmEvent.length() <= 0) return; 
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_METAVIEWDATA){
			var obj = wcmEvent.getObjs().getAt(0);
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getProperty("docid") : 0
				},
				ChannelId : getParameter("ChannelId") || 0,
				SiteId : getParameter("SiteId") || 0
			}
		}
	}//,

});
