/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'metaviewdata_query.jsp',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{desc:wcm.LANG['FILTER_METAVIEWDATA_ALL']||'全部资源', type:0},
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
PageContext.setRelateType(
	!!getParameter("ChannelId") ? 'metaviewdataInChannel' :
				(!!getParameter("SiteId") ? 'metaviewdataInSite' : 'metaviewdataInRoot')
);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG.METAVIEWDATA_102 || '个',
	TypeName : wcm.LANG.METAVIEWDATA_103 || '资源'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		var obj = event.getObj();
		var nObjId = obj.getProperty("docid")||obj.getId();		
		var sTitle = (nObjId == 0)?(wcm.LANG.METAVIEWDATA_77 || "新建"):(wcm.LANG.METAVIEWDATA_106 || "修改");
		sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
		var contextParams = event.getContext().params;
		var nViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
		var oParams = {
			ObjectId:nObjId,
			ChannelId:event.getHost().getId(),
			ChnlDocId:event.getObj().getPropertyAsInt("recid",0),
			FlowDocId:contextParams.FlowDocId || 0,
			ViewId:nViewId
		};
		$openMaxWin(WCMConstants.WCM6_PATH + 'application/' + nViewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
	},
	'delete' : function(event){
		wcm.domain.MetaViewDataMgr['delete'](event);
	},
	'view' : function(event){
		var _objId = event.getObj().getPropertyAsInt("docid",0);
		var nViewId = event.getContext().params["VIEWID"];
		var urlParams = "?objectId=" + _objId;
		var url = "application/" + nViewId + "/viewdata_detail.jsp" + urlParams;
		$openMaxWin(WCMConstants.WCM6_PATH + url);
	},
	'downloadAppendix' : function(event){
		event = window.event || event;
		var dom = Event.element(event);
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
	activeTabType : 'document'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_METAVIEWDATA
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
	_buildParams : function(wcmEvent, actionType, valueDom){
		if(wcmEvent.length() <= 0) return; 
		var obj = wcmEvent.getObjs().getAt(0);
		if(actionType=='save' && wcmEvent.getObjs().getType()==WCMConstants.OBJ_TYPE_METAVIEWDATA){
			var host = wcmEvent.getHost();
			return {
				Force : {
					ObjectId : obj ? obj.getProperty("docid") : 0
				},
				ChannelId : getParameter("ChannelId") || 0,
				SiteId : getParameter("SiteId") || 0
			}
		}else if(actionType=='changestatus'){
			return {
				objectIds : obj.getId(),
				StatusId : valueDom.getAttribute("_fieldValue", 2)
			};
		}
	}
});
//在栏目下资源列表，其objtype为metaviewdata，并没有添加对chnldoc对象（导航树右键点击新建）的监听。
//对列表页面可能产生影响的各个入口进行监听。
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CHNLDOC,//WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afteradd : function(event){
		//if event.getHost()==PageContext.hostType\hostId then to do, else return;
		var host = event.getHost();

		if(PageContext.hostType != host.getType()
				|| PageContext.hostId != host.getId()){
			return;
		}
		PageContext.updateCurrRows(event.getIds());
	},
	afteredit : function(event){
		//if event.getIds() obtains list to do, else return;
		var objId = event.getObj().getId();
		PageContext.updateCurrRows(event.getIds());
	}
});