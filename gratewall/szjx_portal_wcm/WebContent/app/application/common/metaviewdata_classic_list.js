/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'metaviewdata_query.jsp',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.filterEnable  = false;

/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.operEnable  = false;
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
	'preview' : function(event){//单击列表上预览图标时执行的操作
		wcm.domain.MetaViewDataMgr['preview'](event);
	},
	'edit' : function(event){
		wcm.domain.MetaViewDataMgr['edit'](event);
	},
	'delete' : function(event){
		wcm.domain.MetaViewDataMgr['delete'](event);
	},
	'view' : function(event){
		wcm.domain.MetaViewDataMgr['view'](event);
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
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		container : 'query_box', 
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
	var tds = $('query_box').getElementsByTagName("td");
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


ClassicList.cfg = {
	toolbar : [
		{
			id : 'metaviewdata_new',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewDataMgr['add'](event);
			},
			name : '新建',
			desc : '新建一条记录...',
			isHost : true,
			rightIndex : 31
		}, {
			id : 'metaviewdata_delete',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewDataMgr['delete'](event);
			},
			name : wcm.LANG.METAVIEWDATA_78 || '删除',
			desc : wcm.LANG.METAVIEWDATA_57 || '将记录放入废稿箱',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'metaviewdata_preview',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewDataMgr.preview(event);
			},
			name : wcm.LANG.METAVIEWDATA_79 || '预览',
			desc : wcm.LANG.METAVIEWDATA_64 || '预览这些记录发布效果',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 38
		},{
			id : 'metaviewdata_publish',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewDataMgr.basicpublish(event);
			},
			name : wcm.LANG.METAVIEWDATA_80 || '快速发布',
			desc : wcm.LANG.METAVIEWDATA_66 || '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'metaviewdata_move',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewDataMgr.move(event);
			},
			name : wcm.LANG.METAVIEWDATA_81 || '移动',
			desc : wcm.LANG.METAVIEWDATA_74 || '移动这些记录到...',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'metaviewdata_copy',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewDataMgr.copy(event);
			},
			name : wcm.LANG.METAVIEWDATA_82 || '复制',
			desc : wcm.LANG.METAVIEWDATA_75 || '复制这些记录到...',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'metaviewdata_quote',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewDataMgr.quote(event);
			},
			name : wcm.LANG.METAVIEWDATA_83 || '引用',
			desc : wcm.LANG.METAVIEWDATA_76 || '引用这些记录到...',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.METAVIEWDATA_84 || '刷新',
			desc : wcm.LANG.METAVIEWDATA_85 || '刷新列表'
		}
	],
	listTitle : wcm.LANG.METAVIEWDATA_86 || '资源列表管理',
	path : ''
}
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
		if(!event.getIds()) {
			return;
		}
		PageContext.updateCurrRows(event.getIds());
	}
});