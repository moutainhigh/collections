/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_metaviewfield',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});
//控制在低分辨率下是否隐藏一些操作
wcm.SysOpers.resolutionRelateInfo.enable = false;
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
	!!getParameter("ChannelId") ? 'metaviewfieldInChannel' :
				(!!getParameter("SiteId") ? 'metaviewfieldInSite' : 'metaviewfieldInRoot')
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METAVIEWFIELD_UNITNAME||'个',
	TypeName : wcm.LANG.METAVIEWFIELD_TYPENAME||'视图字段'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		wcm.domain.MetaViewFieldMgr['edit'](event);
	},
	'delete' : function(event){
		wcm.domain.MetaViewFieldMgr['delete'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'metaview'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_METAVIEWFIELD
});

/*-------------检索框信息--------------*/
PageContext.searchEnable = true;
wcm.ListQuery.register({
	container : 'search', 
	appendQueryAll : true,
	autoLoad : PageContext.searchEnable,
	items : [
		{name: 'queryAnotherName', desc: wcm.LANG.METAVIEWFIELD_ANOTHERNAME||'中文名称', type: 'string'},
		{name: 'queryFieldName', desc: wcm.LANG.METAVIEWFIELD_FIELDNAME||'英文名称', type: 'string'},
		{name: 'CrUser', desc: wcm.LANG.METAVIEWFIELD_CRUSER||'创建者', type: 'string'}
	],
	callback : function(params){
		PageContext.loadList(Ext.apply({
			//some params must remember here
		}, params));
	}
});

Event.observe(window, 'load', function(){
	Event.observe("returnTableInfoId","click",function(){
		$MsgCenter.$main({
			objId : 4,
			objType : WCMConstants.OBJ_TYPE_WEBSITEROOT,
			tabType : 'metaview'			
		}).redirect();
	});

	//advance search
	var dom = document.createElement("td");
	dom.className = 'advance_search';
	var tds = $('search').getElementsByTagName("td");
	var lastTd = tds[tds.length - 1];
	lastTd.parentNode.appendChild(dom);
	Event.observe(dom, 'click', function(event){
		var nObjectId = PageContext.params['OBJECTID'] || 0;
		var params = PageContext.getContext().params;
		var nChannelId = params['CHANNELID'] || 0;
		var nViewId = params['VIEWID'] || 0;
		var urlParams = "nViewId=" + nViewId ;
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'metaviewfield/advance_search.jsp?' + urlParams,
			title : wcm.LANG.METAVIEWDATA_105 || '高级检索',
			callback : function(params){
				PageContext.loadList(Ext.apply({
				//some params must remember here
				}, params));
			}
		});
	});
});