/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_metaviewfield',
	methodName : 'jQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : ""
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
Event.observe(window, 'load', function(){
	ClassicList.makeLoad();
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'query_box', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : PageContext.searchEnable,
		/*检索项的内容*/
		items : [
			{name: 'queryAnotherName', desc: wcm.LANG.METAVIEWFIELD_ANOTHERNAME||'中文名称', type: 'string'},
			{name: 'queryFieldName', desc: wcm.LANG.METAVIEWFIELD_FIELDNAME||'英文名称', type: 'string'},
			{name: 'CrUser', desc: wcm.LANG.METAVIEWFIELD_CRUSER||'创建者', type: 'string'},
			{name: 'ViewFieldInfoId', desc: wcm.LANG.METAVIEWFIELD_VIEWFIELDINFOID||'字段ID', type: 'int'},
			{name: 'classId', desc: wcm.LANG.METAVIEWFIELD_CLASSID||'分类法ID', type: 'int'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
			PageContext.loadList(Ext.apply({
				//some params must remember here
			}, params));
		}
	});
});

ClassicList.cfg = {
	toolbar : [
		{
			id : 'metaviewfield_setView',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewFieldMgr['setView'](event);
			},
			name : wcm.LANG.METAVIEWFIELD_PROCESS_7 || '设置视图',
			desc : wcm.LANG.METAVIEWFIELD_PROCESS_7 || '设置视图',
			isHost:true,
			rightIndex : 13,
			isDisabled : function(event){
				if(!PageContext.getContext().isChannel){
					return true;
				}
				return false;
			}
			
		},{
			id : 'metaviewfield_selectFields',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewFieldMgr['selectFields'](event);
			},
			name : wcm.LANG.METAVIEWFIELD_PROCESS_1 || '维护字段',
			desc : wcm.LANG.METAVIEWFIELD_PROCESS_1 || '维护字段',
			isHost:true,
			rightIndex : 13,
			isDisabled : function(event){
				var context = PageContext.getContext();
				if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1)){
					return false;
				}
				return true;
			}
		}/*,{
			id : 'metaviewfield_selectDBFields',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewFieldMgr['selectDBFields'](event);
			},
			name : wcm.LANG.METAVIEWFIELD_PROCESS_2 || '维护物理字段',
			desc : wcm.LANG.METAVIEWFIELD_PROCESS_2 || '维护物理字段',
			isHost:true,
			rightIndex : 13,
			isDisabled : function(event){
				var context = PageContext.getContext();
				if(!context.params['ISSINGLETABLE']){
					return true;
				}
				if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1)){
					return false;
				}
				return true;
				
			}
		}*/,{
			id : 'metaviewfield_generate',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewFieldMgr['generate'](event);
			},
			name : wcm.LANG.METAVIEWFIELD_PROCESS_3||'生成应用',
			desc : wcm.LANG.METAVIEWFIELD_PROCESS_3||'生成应用',
			isHost:true,
			rightIndex : 13,
			isDisabled : function(event){
				var context = PageContext.getContext();
				if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1)){
					return false;
				}
				return true;
				
			}
		},{
			id : 'metaviewfield_viewFieldPositionSet',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewFieldMgr['viewFieldPositionSet'](event);
			},
			name : wcm.LANG.METAVIEWFIELD_PROCESS_8 || '调整顺序',
			desc : wcm.LANG.METAVIEWFIELD_PROCESS_8 || '调整顺序',
			rightIndex : 13,
			isDisabled : function(event){
				 var context = PageContext.getContext();
				 if(context.isChannel){
					if(context.params["HASRIGHT"] == 0){
						return true;
					}
				 }
				 if(!event || event.length() <= 0) return true;
				return event.getIds().length != 1;
			}
		},{
			id : 'metaviewfield_setAsTitle',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewFieldMgr['setAsTitle'](event);
			},
			name : wcm.LANG.METAVIEWFIELD_PROCESS_4 || '设为标题',
			desc : wcm.LANG.METAVIEWFIELD_PROCESS_4 || '设为标题',			
			rightIndex : 13,
			isDisabled : function(event){
				var context = PageContext.getContext();
				if(context.isChannel){
					if(context.params["HASRIGHT"] == 0){
						return true;
					}
				}
				if(!event || event.length() <= 0) return true;
				return event.getIds().length != 1;
			}
		},{
			id : 'metaviewfield_delete',
			fn : function(event, elToolbar){
				wcm.domain.MetaViewFieldMgr['delete'](event);
			},
			name : wcm.LANG.METAVIEWFIELD_PROCESS_5 || '删除',
			desc : wcm.LANG.METAVIEWFIELD_PROCESS_5 || '删除',
			rightIndex : 13,
			isDisabled : function(event){
				var context = PageContext.getContext();
				if(context.isChannel){
					if(context.params["HASRIGHT"] == 0){
						return true;
					}
				}
				if(!event || event.length() <= 0) return true;
				return false;
			}
		},
		'/', {
			id : 'action_refresh',
			fn : function(event, elToolbar){
				PageContext.loadList({CurrPage:1});
			},
			name : wcm.LANG.METAVIEWFIELD_BUTTON_2 || '刷新',
			desc : wcm.LANG.METAVIEWFIELD_BUTTON_3 || '刷新列表'
		}
	],
	listTitle : '<span class="returnTableInfoList" id="returnTableInfoId">' + (wcm.LANG.METAVIEWFIELD_BUTTON_4 || '返回') + '</span><span class="" id="viewInfo" style="font-weight:bold;"></span>',
	path : ''
}
