/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	filterEnable : false,
	serviceId : 'metaviewdata_select_query.jsp',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"BCKXORRADIO" : parent.SELECT_TYPE || 'checkbox'
	}
});

Ext.apply(wcm.Grid, {
	rowType : function(){
		return "MetaViewData";
	},
	rowInfo : {
		docid : true,
		doctitle : true,
		channelid : true,
		viewid:true
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
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
		wcm.domain.MetaViewDataMgr['edit'](event);
	},
	'delete' : function(event){
		wcm.domain.MetaViewDataMgr['delete'](event);
	},
	'view' : function(event){
		wcm.domain.MetaViewDataMgr['view'](event);
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
PageContext.tabEnable = false;
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

var PRV_OBJ_TYPE_DOCUMENT = parent.PRV_OBJ_TYPE_DOCUMENT || WCMConstants.OBJ_TYPE_DOCUMENT;
var PRV_OBJ_TYPE_CHNLDOC = parent.PRV_OBJ_TYPE_CHNLDOC || WCMConstants.OBJ_TYPE_CHNLDOC;
var PRV_OBJ_TYPE_CURRPAGE = parent.PRV_OBJ_TYPE_CURRPAGE || WCMConstants.OBJ_TYPE_CURRPAGE;
$MsgCenter.un($MsgCenter.getListener('sys_gridrow'));
$MsgCenter.un($MsgCenter.getListener('sys_gridcell'));
$MsgCenter.un($MsgCenter.getListener('sys_allcmsobjs'));
PageContext.m_CurrPage = $MsgCenter.$currPage().privateMe(PRV_OBJ_TYPE_CURRPAGE);
wcm.GridRow.prototype._buildContext = function(_row){
	return wcm.Grid.getRowInfo(_row, wcm.Grid.rowInfo);
}
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	afterunselect : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		var cmsobj = CMSObj.createFrom(context);
		cmsobj.privateMe(PRV_OBJ_TYPE_CHNLDOC).afterunselect();
	},
	beforeclick : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		var dom = context.row.dom;
		return (!Element.hasClassName(dom, "grid_selectdisable_row"));
	},
	afterselect : function(event){
		if(event.from()!=wcm.getMyFlag())return;
		var context = event.getContext();
		if(context==null)return;
		var cmsobj = CMSObj.createFrom(context);
		cmsobj.privateMe(PRV_OBJ_TYPE_CHNLDOC).afterselect();
	}
});
$MsgCenter.on({
	objType : PRV_OBJ_TYPE_DOCUMENT,
	afterunselect : function(event){
		var nDocId = event.getIds().join();
		var rows = wcm.Grid.filterRows('docid', nDocId);
		if(rows.length==0)return;
		for(var i=0, n=rows.length; i<n; i++){
			wcm.Grid.unselectRow(rows[i], true);
		}
	},
	afterselect : function(event){
		if(event.from()==wcm.getMyFlag())return;
		var objs = event.getObjs();
		for(var i=0, n=objs.length(); i<n; i++){
			var nDocId = objs.getAt(i).getId();
			var rows = wcm.Grid.filterRows('docid', nDocId);
			if(rows.length==0)continue;
			for(var j=0, jn=rows.length; j<jn; j++){
				wcm.Grid.selectRow(rows[j], true);
			}
		}
	}
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_GRIDROW,
	afterselect : function(event){
		setTimeout(function(){
			var dom = $('wcm_table_grid');
			if(!dom) return;
			Element.addClassName(dom, 'fix-ie6-redraw');
			Element.removeClassName(dom, 'fix-ie6-redraw');
		}, 0);
	},
	afterunselect : function(event){
		setTimeout(function(){
			var dom = $('wcm_table_grid');
			if(!dom) return;
			Element.addClassName(dom, 'fix-ie6-redraw');
			Element.removeClassName(dom, 'fix-ie6-redraw');
		}, 0);
	}
});