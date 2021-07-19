/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'metaviewdata_query.jsp',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		'HostType' : PageContext.intHostType,
		'ClassInfoIds' : PageContext.hostId
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{desc:wcm.LANG['FILTER_ALL']||'全部文档', type:0},
	{desc:wcm.LANG['FILTER_NEW']||'新稿', type:1},
	{desc:wcm.LANG['FILTER_CANPUB']||'可发', type:2},
	{desc:wcm.LANG['FILTER_PUBED']||'已发', type:3},
	{desc:wcm.LANG['FILTER_REJECTED']||'已否', type:8},
	{desc:wcm.LANG['FILTER_MY']||'我创建的', type:4},
	{desc:wcm.LANG['FILTER_LAST3']||'最近三天', type:5},
	{desc:wcm.LANG['FILTER_LASTWEEK']||'最近一周', type:6},
	{desc:wcm.LANG['FILTER_LASTMONTH']||'最近一月', type:7}
]);

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
	},
	'downloadAppendix' : function(event){
		var context = event.getContext();
		var dom = context.get("srcElement");
		var sFileName = dom.getAttribute("title");
		if(!sFileName) return;
		var topWin = $MsgCenter.getActualTop();
		var frm = topWin.$('iframe4download');
		if(!frm) {
			frm = topWin.document.createElement('iframe');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			topWin.document.body.appendChild(frm);
		}
		frm.src = WCMConstants.WCM6_PATH+"../file/read_file.jsp?FileName=" + sFileName;		
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
var DBTypeMapping = {4:'int', 6:'float', 8:'float', 12:'string', 93:'date'};
Event.observe(window, 'load', function(){
	PageContext.searchEnable = wcmListQueryItems.length > 0;

	//normal search
	if(!PageContext.searchEnable) return;
	wcm.ListQuery.register({
		container : 'search', 
		appendQueryAll : true,
		autoLoad : PageContext.searchEnable,
		items : wcmListQueryItems,
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
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
		var urlParams = "ChannelId=" + nChannelId + "&ObjectId=" + nObjectId ;
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'application/' + nViewId + '/advance_search.jsp?' + urlParams,
			title : wcm.LANG.METAVIEWDATA_105 || '高级检索',
			callback : function(params){
				PageContext.loadList(Ext.apply({
				//some params must remember here
				}, params));
			}
		});
	});
	
});

/*   操作面板需要执行相关函数 */
Ext.apply(PageContext, {
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
	}
});
