//视图操作信息和Mgr定义
Ext.ns('wcm.domain.MetaViewMgr','wcm.domain.MetaViewService');
(function(){
	var m_oMgr = wcm.domain.MetaViewMgr;
	var m_sServiceId = "wcm61_metaview";
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	//传入event不合适的函数
	var addEditStepOne = function(_id, event){
		var oParams = {objectId : _id};
		var channelId = event.getContext().params['CHANNELID'];
		if(channelId){
			oParams['channelId'] = channelId;
		}
		var url = 'metaview/viewinfo_add_edit_step1.jsp?' + $toQueryStr(oParams);
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + url,
			title : wcm.LANG.METAVIEW_WINDOW_TITLE_1 || '新建/修改视图步骤1:新建或选择表',
			callback : function(_tableInfoId, _viewId){				
				CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();
				var _params = {tableInfoId : _tableInfoId , viewId : _viewId};
				wcm.domain.MetaViewService.editMultiTable(_viewId);
				//wcm.domain.MetaViewService.addEditStepTwo(_params);
			}
		});			
	};
//-------------------------------------------------------------
	//供外部和内部共同调用的方法。
	Ext.apply(wcm.domain.MetaViewService, {
		generate : function(objIds){
			ProcessBar.start(wcm.LANG.METAVIEW_PROCESSBAR_TIP_1 || "生成应用");
			getHelper().call(m_sServiceId, 'createViewRelation', {viewIds : objIds}, true, function(transport, json){
				ProcessBar.exit();
				var isSuccess = com.trs.util.JSON.value(json, "REPORTS.IS_SUCCESS");
				if(isSuccess == true || isSuccess == 'true'){
					//var reportDailog = wcm.ReportDialog;
					//wcm.ReportDialog.show(json, wcm.LANG.METAVIEW_GENAPP_RPT || '生成应用结果');
				}
			});
		},
		addEditStepTwo : function(params,fCallBack){
			//var urlParams = "tableInfoId=" + params['tableInfoId'] + "&viewId=" + params['viewId'];
			var url = './metadbfield/metadbfield_list_select.html?' + $toQueryStr(params);
			wcm.CrashBoarder.get('crash-board').show({
					id : 'crash-board',
					title : wcm.LANG.METAVIEW_WINDOW_TITLE_2 || '新建/修改视图步骤2:新建或选择物理字段',
					src : url,
					width:'800px',
					height:'400px',
					maskable:true,
					callback : function (args) {
						(fCallBack || Ext.emptyFn)(args)
				}
			});
		},
		editMultiTable : function(_sId){
			wcm.CrashBoarder.get('editMultiTabler').show({
				id	: 'editMultiTabler',
				title : wcm.LANG.METAVIEW_WINDOW_TITLE_3 || "修改视图",
				src : "metadbtable/build_to_view.html",
				width : "800px",
				height : "500px",
				maskable:true,
				params : {viewId: _sId}
			});
		}
	});
//-------------------------------------------------------------

	Ext.apply(wcm.domain.MetaViewMgr, {
		'delete' : function(event){
			var _arrIds = event.getObjs().getIds();
			if(!confirm(String.format("确实要将这{0}个视图删除吗?",_arrIds.length ))){
				return;
			}
			getHelper().call(m_sServiceId, "deleteView", {objectids:_arrIds + ""}, true, function(transport, json){
				var isSuccess = $v(json, "REPORTS.IS_SUCCESS");
				if(isSuccess == 'true'){
					event.getObjs().afterdelete();
					return;
				}
				wcm.ReportDialog.show(json, wcm.LANG.METAVIEW_ALERT_3 || '视图删除结果', function(){
					event.getObjs().afterdelete();
				});				
			});
		},

		/**
		 * @param : wcm.CMSObjEvent event
		 */
		//
		edit : function(event){
			var isSingleTable = event.getObj().getPropertyAsString("isSingleTable") || true;
			if(isSingleTable =="true"||isSingleTable ==true )
				isSingleTable = true;
			else{
				isSingleTable = false;
			}
			var _sId = event.getObj().getId();
			if(isSingleTable){
				addEditStepOne(_sId, event);
			}else{
				wcm.domain.MetaViewService.editMultiTable(_sId);
			}
		},
		add : function(event){
			addEditStepOne(0, event);
		},
		'export' : function(event){
			if(event.length()==0){
				Ext.Msg.$alert(wcm.LANG.metaview_1001 || '必须选中至少一个视图!');
				return;
			}
			var oPostData = {
				ViewIds: event.getIds().join()
			};
			BasicDataHelper.call(m_sServiceId, 'exportViews', oPostData, true, function(_trans, _json){
				var sFileUrl = _trans.responseText;
				var frm = document.getElementById("ifrmDownload");
				if(frm == null) {
					frm = document.createElement('iframe');
					frm.style.height = 0;
					frm.style.width = 0;
					document.body.appendChild(frm);
				}
				sFileUrl = WCMConstants.WCM6_PATH + "file/read_file.jsp?DownName=" + sFileUrl + "&FileName=" + sFileUrl;
				frm.src = sFileUrl;
			
			}.bind(this));	
		},
		'import' : function(event){
			var context = event.getContext();
			var params = {
				OwnerId: context.OwnerId,
				OwnerType: context.OwnerType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'metaview/metaview_import.html?' + $toQueryStr(params), 
				wcm.LANG.metaview_1002 || '视图导入', CMSObj.afteradd(event));
		},
		'setsynrule' : function(event){
			//获取到视图Id
			var nViewId = event.getObj().getId();
			FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/syn_rule_set.html?synRuleSetFrom=metaView&viewId=' + nViewId, (wcm.LANG.METAVIEWDATA_34 || '设置数据同步到WCMDocument的规则'), CMSObj.afteredit(event));
		}

	});

})();


(function(){
	var pageObjMgr = wcm.domain.MetaViewMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'add',
		type : 'MetaViewInRoot',
		desc : '新建一个视图',
		title : '新建一个视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'MetaViewInRoot',
		desc :  '导入视图',
		title : '导入视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['import'],
		quickKey : 'I'
	});
	reg({
		key : 'edit',
		type : 'MetaView',
		desc : '修改这个视图',
		title : '修改这个视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'E'
	});
	reg({
		key : 'delete',
		type : 'MetaView',
		desc : '删除这个视图',
		title : '删除这个视图...',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'export',
		type : 'MetaView',
		desc : '导出这个视图',
		title : '将当前视图以xml文件导出',
		rightIndex : -1,
		order : 3,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'setsynrule',
		type : 'MetaView',
		desc : '设置同步规则',
		title : '设置同步规则...',
		rightIndex : -1,
		order : 4,
		fn : pageObjMgr['setsynrule']
	});
	reg({
		key : 'delete',
		type : 'MetaViews',
		desc : '删除这些视图',
		title : '删除这些视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'export',
		type : 'MetaViews',
		desc : '导出这些视图',
		title : '将这些视图以xml文件导出',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});

})();