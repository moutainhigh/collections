/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	contextMenuEnable : true,
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
PageContext.operEnable = false;
PageContext.setRelateType("metarecdataInClassinfo");

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
			container : 'query_box', 
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
		var tds = $('query_box').getElementsByTagName("td");
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

ClassicList.cfg = {
	toolbar : [
		{
			id : 'metarecdata_new',
			fn : function(event, elToolbar){
				wcm.domain.MetaRecDataMgr['add'](event);
			},
			name : '新建',
			desc : '新建一条记录...',
			isHost : true,
			rightIndex : -1
		}, {
			id : 'metarecdata_delete',
			fn : function(event, elToolbar){
				wcm.domain.MetaRecDataMgr['delete'](event);
			},
			name : wcm.LANG.METAVIEWDATA_78 || '删除',
			desc : wcm.LANG.METAVIEWDATA_57 || '将记录放入废稿箱',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},{
			id : 'metarecdata_preview',
			fn : function(event, elToolbar){
				wcm.domain.MetaRecDataMgr.preview(event);
			},
			name : wcm.LANG.METAVIEWDATA_79 || '预览',
			desc : wcm.LANG.METAVIEWDATA_64 || '预览这些记录发布效果',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 38
		},{
			id : 'metarecdata_publish',
			fn : function(event, elToolbar){
				wcm.domain.MetaRecDataMgr.basicpublish(event);
			},
			name : wcm.LANG.METAVIEWDATA_80 || '快速发布',
			desc : wcm.LANG.METAVIEWDATA_66 || '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 39
		},{
			id : 'metarecdata_move',
			fn : function(event, elToolbar){
				wcm.domain.MetaRecDataMgr.move(event);
			},
			name : wcm.LANG.METAVIEWDATA_81 || '移动',
			desc : wcm.LANG.METAVIEWDATA_74 || '移动这些记录到...',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 33
		},/*{
			id : 'metarecdata_copy',
			fn : function(event, elToolbar){
				wcm.domain.MetaRecDataMgr.copy(event);
			},
			name : wcm.LANG.METAVIEWDATA_82 || '复制',
			desc : wcm.LANG.METAVIEWDATA_75 || '复制这些记录到...',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},{
			id : 'metarecdata_quote',
			fn : function(event, elToolbar){
				wcm.domain.MetaRecDataMgr.quote(event);
			},
			name : wcm.LANG.METAVIEWDATA_83 || '引用',
			desc : wcm.LANG.METAVIEWDATA_76 || '引用这些记录到...',
			isDisabled : function(event){
				if(!event || event.length() <= 0) return true;
			},
			rightIndex : 34
		},*/
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