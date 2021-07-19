//视图字段操作信息和Mgr定义
Ext.ns('wcm.domain.MetaViewFieldMgr');
(function(){

    var m_oMgr = wcm.domain.MetaViewFieldMgr;
	var m_sServiceId = "wcm61_metaviewfield";

	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	

	function _doAddOrEdit(_nObjectId, event){
		var urlParams = 'objectId='+ _nObjectId;
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + 'metaviewfield/viewfieldinfo_add_edit.jsp?' + urlParams,
			title : wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_1 || '新建/修改视图字段',
			callback : function(info){
				CMSObj[_nObjectId>0 ? 'afteredit' : 'afteradd'](event)(info);
				FloatPanel.close();
			}
		});
	}

	Ext.apply(wcm.domain.MetaViewFieldMgr, {
		generate : function(event){
			var nViewId = event.getContext().params["VIEWID"];
			wcm.domain.MetaViewService['generate'](nViewId);
		},
		//通过右侧面板调用的操作
		edit : function(event){
			var _sObjectIds = event.getIds();
			_doAddOrEdit(_sObjectIds,event);
		},
		//删除单个和多个对象
		'delete' : function(event){
			var aObjectIds = event.getIds();
			var nCount = aObjectIds.length;
			var sHint = (aObjectIds==1)?'':' '+nCount+' ';
			var msg = String.format("确实要将这{0}个视图字段删除吗?",sHint);
			Ext.Msg.confirm(msg,{
				yes : function(){
					getHelper().call(m_sServiceId, 'deleteViewField', //远端方法名				
					{"ObjectIds": aObjectIds}, //传入的参数
					true, //post,get
					function(){//响应函数						
						CMSObj.afterdelete(event)();
					}
				);
				},
				no : function(){}
			});			
		},
		setAsTitle : function(event){
            var _sObjectId = event.getObj().getId();
            var _params =  {titleField : 1, inOutline : 1};
			var params = Object.extend(_params, {ObjectId : _sObjectId})
			getHelper().call(m_sServiceId, 'saveViewField', params, true, function(transport, json){
				event.getObj().afteredit();
			});
		},
		//通过右侧面板调用的操作
		selectFields : function(event){			
			var _sId = event.getContext().params['VIEWID'];
			var sDialogId = "editMultiTabler";
			wcm.CrashBoarder.get(sDialogId).show({
				id	: sDialogId,
				title : wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_2 || "修改视图",
				src : "metadbtable/build_to_view.html",
				width : "800px",
				height : "500px",
				maskable:true,
				params : {viewId: _sId},
				callback : function(params){
					var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
					CMSObj.createFrom(info, event.getContext()).afteredit();
				}
			});

			//wcm.domain.MetaViewService.editMultiTable(PageContext.params['VIEWID']);

		},
		selectDBFields : function(event){
			var isSingleTable = event.getContext().params["ISSINGLETABLE"];
			if(isSingleTable == false){
				wcm.domain.MetaViewService.editMultiTable(event.getContext().params['VIEWID']);
			}else if(isSingleTable == true){
				var _params = {tableInfoId : event.getContext().params['MAINTABLEID'], 
					viewId : event.getContext().params['VIEWID']
					};
				wcm.domain.MetaViewService.addEditStepTwo(_params, function (){
						var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
						CMSObj.createFrom(info, event.getContext()).afteredit();
				});
			}
		},
		setView : function(event){
			var nViewId = event.getContext().params['VIEWID']
			wcm.MetaViewSelector.selectView({selectIds : nViewId},function(args){
				var viewId = args.ViewId || 0;
				event.getContext().viewId = viewId;
				var isContainsChildren = args["ContainsChildren"] || false;
				var channelId = getParameter("channelid");
				wcm.MetaViewSelector.setChannelView(viewId, {
					channelid : channelId,
					ContainsChildren : isContainsChildren
				}, function(){
					var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
					CMSObj.createFrom(info, event.getContext()).afteredit();
				});
			});
			
		},
		addView : function(event){
 			var _id = 0 ;
			var oParams = {objectId : _id};
			var channelId = event.getContext().params["CHANNELID"] || 0;
			if(channelId){
				oParams['channelId'] = channelId;
			}
			var url = 'metaview/viewinfo_add_edit_step1.jsp?' + $toQueryStr(oParams);
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + url,
				title : wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_3 || '新建/修改视图步骤1:新建或选择表',
				callback : function(_tableInfoId, _viewId){				
					//CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();
					var _params = {tableInfoId : _tableInfoId , viewId : _viewId};
					wcm.domain.MetaViewService.addEditStepTwo(_params, function(){
						var channelId = getParameter("channelid");
						wcm.MetaViewSelector.setChannelView(_viewId, {
							channelid : channelId,
							ContainsChildren : false //默认不包含子栏目，可以自定义。
						}, function(){
							var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
							CMSObj.createFrom(info, event.getContext()).afteredit();
						});
					});
				}
			});	
		},
		viewFieldPositionSet : function(event){
			//alert(Ext.toSource(event));
			var oPageParams = {};
			var nObjectId = event.getObj().getId();
			Object.extend(oPageParams,{"ObjectId":nObjectId});
			var url = 'metaviewfield/viewfield_position_set.jsp?' + $toQueryStr(oPageParams);			
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + url,
				title : (wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_4 || '字段—调整位置到...'),
				callback : function(){
					CMSObj.afteredit(event)();
				}
			});	
		}
	});
})();

(function(){
	var pageObjMgr = wcm.domain.MetaViewFieldMgr;
	var reg = wcm.SysOpers.register;
	var fnIsVisible = function(event){
		var context = event.getContext();
		if(context.host.objType == "channel"){
			if(context.params["HASRIGHT"] == 0){
				return false;
			}
		}
		return true;
	};

	reg({
		key : 'generate',
		type : 'metaviewfieldInRoot',
		desc : '生成应用',
		title : '生成应用...',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['generate']
	});
	reg({
		key : 'selectFields',
		type : 'metaviewfieldInRoot',
		desc : '维护字段',
		title : '维护字段...',
		rightIndex : -2,
		order : 1,
		fn : pageObjMgr['selectFields']
	});
	/*
	reg({
		key : 'selectDBFields',
		type : 'metaviewfieldInRoot',
		desc : wcm.LANG.METAVIEWFIELD_PROCESS_2||'维护物理字段',
		title : wcm.LANG.METAVIEWFIELD_PROCESS_2||'维护物理字段',
		rightIndex : -2,
		order : 2,
		fn : pageObjMgr['selectDBFields'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["ISSINGLETABLE"]){
				return true;
			}
			return false;
		}
	});
	*/
	//
	reg({
		key : 'setAsTitle',
		type : 'metaviewfield',
		desc : wcm.LANG.METAVIEWFIELD_PROCESS_4||'设为标题',
		title : wcm.LANG.METAVIEWFIELD_PROCESS_4||'设为标题',
		rightIndex : -1,
		order : 4,
		fn : pageObjMgr['setAsTitle'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'delete',
		type : 'metaviewfield',
		desc : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
		title : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete'],
		isVisible : fnIsVisible
	});
	reg({
		key : 'delete',
		type : 'metaviewfields',
		desc : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
		title : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
		rightIndex : -1,
		order : 6,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete'],
		isVisible : fnIsVisible
	});
	//
	reg({
		key : 'generate',
		type : 'metaviewfieldInChannel',
		desc : '生成应用',
		title : '生成应用...',
		rightIndex : 13,
		order : 3,
		fn : pageObjMgr['generate'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1)){
				return true;
			}
			return false;
		}
	});
	reg({
		key : 'add',
		type : 'metaviewfieldInChannel',
		desc : wcm.LANG.METAVIEWFIELD_PROCESS_6||'新建视图',
		title : wcm.LANG.METAVIEWFIELD_PROCESS_6||'新建视图',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['addView'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["VIEWID"]){
				return false;
			}
			return true;
		},
		quickKey : 'N'
	});
	reg({
		key : 'setView',
		type : 'metaviewfieldInChannel',
		desc : '设置视图',
		title : '设置视图...',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['setView']
	});
	reg({
		key : 'selectFields',
		type : 'metaviewfieldInChannel',
		desc : '维护字段',
		title : '维护字段...',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['selectFields'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1)){
				return true;
			}
			return false;
		}
	});
	/*
	reg({
		key : 'selectDBFields',
		type : 'metaviewfieldInChannel',
		desc : wcm.LANG.METAVIEWFIELD_PROCESS_2||'维护物理字段',
		title : wcm.LANG.METAVIEWFIELD_PROCESS_2||'维护物理字段',
		rightIndex : 13,
		order : 2,
		fn : pageObjMgr['selectDBFields'],
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1) && context.params["ISSINGLETABLE"]){
				return true;
			}
			return false;
		}
	});
	*/
	reg({
		key : 'viewFieldPositionSet',
		type : 'metaviewfield',
		desc : '调整顺序',
		title : '调整顺序',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['viewFieldPositionSet'],
		isVisible : fnIsVisible
	});
	//
})();

(function(){
	function selectFieldGroup(event){
		var nViewId = event.getContext().params['VIEWID']
		var sURL = WCMConstants.WCM6_PATH + "metaviewfieldgroup/metaviewfieldgroup_list.jsp";
		var sTitle = '字段分组维护列表';		
		wcm.CrashBoarder.get('fieldgroup_Select').show({
			title : sTitle,
			src : sURL,
			width: '650px',
			height: '400px',
			reloadable : true,
			params :  {MetaViewId : nViewId},
			maskable : true,
			callback : function(_args){			 
			}
		});
	}
	var reg = wcm.SysOpers.register;
	//库视图字段操作任务面板注册“维护分组”
	reg({
		key : 'maintainFieldGroup',
		type : 'metaviewfieldInRoot',
		desc : '维护分组',
		title : '维护视图字段分组',
		rightIndex : -2,
		order : 3.1,
		fn :selectFieldGroup,
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["ISSINGLETABLE"]){
				return true;
			}
			return false;
		}
	});
	reg({
		key : 'maintainFieldGroup',
		type : 'metaviewfieldInChannel',
		desc : '维护分组',
		title : '维护视图字段分组',
		rightIndex : -2,
		order : 1.1,
		fn :selectFieldGroup,
		isVisible : function(event){
			var context = event.getContext();
			if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1) && context.params["ISSINGLETABLE"]){
				return true;
			}
			return false;
		}
	});
})();