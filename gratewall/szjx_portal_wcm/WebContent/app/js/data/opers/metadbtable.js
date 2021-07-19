//元数据操作信息和Mgr定义
Ext.ns('wcm.domain.MetaDBTableMgr');
(function(){
	var m_oMgr = wcm.domain.MetaDBTableMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function addEdit(_id,event){
		var url = 'metadbtable/tableinfo_add_edit.jsp?objectId=' + _id;
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + url,
			title : wcm.LANG.METADBTABLE_29 || '新建/修改元数据',
			callback : function(){				
				CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();				
			}
		});			
	}
	Ext.apply(wcm.domain.MetaDBTableMgr, {
		serviceId	: "wcm61_metadbtable",		
		add : function(event){
			addEdit(0,event);
		},
		edit : function(event){
			addEdit(event.getObj().getId(),event);		
		},
		
		build : function(event){
			var sDialogId = "tableToViewer";
			wcm.CrashBoarder.get(sDialogId).show({
				id	: sDialogId,
				title : wcm.LANG.METADBTABLE_23 || "生成视图",
				src : "metadbtable/build_to_view.html",
				width : "800px",
				height : "500px",
				maskable:true,
				params : {tableIds : event.getIds()}
			});			
		},
		'delete' : function(event){							
			var sId = 'table_info_dialog_delete';
			wcm.CrashBoarder.get(sId).show({
				id : sId,
				maskable : true,
				title : wcm.LANG.METADBTABLE_30 || '系统提示信息',
				src : 'metadbtable/tableinfo_delete_info.jsp',
				width:'500px',
				height:'210px',
				border:false,
				params : {
					objectids:event.getObjs().getIds()
				},
				callback : function(){
					ProcessBar.init(wcm.LANG.METADBTABLE_21 || '删除');
					ProcessBar.start();
					BasicDataHelper.call("wcm61_metadbtable", "deleteDBTableInfo", {objectids:event.getObjs().getIds()}, true, function(trans,json){
						ProcessBar.close();
						var isSuccess = $v(json, "REPORTS.IS_SUCCESS");
						if(isSuccess == 'true'){
							event.getObjs().afterdelete();
							return;
						}
						wcm.ReportDialog.show(json, '元数据删除结果', function(){
							event.getObjs().afterdelete();
						});				
						this.close();
					});
				}
			});			
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.MetaDBTableMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'build',
		type : 'MetaDBTable',
		desc : '生成视图',
		title : '生成视图...',
		rightIndex : -1,
		order : 1,
		fn : pageObjMgr['build']
	});
	reg({
		key : 'edit',
		type : 'MetaDBTable',
		desc : '修改这个元数据',
		title : '修改这个元数据...',
		rightIndex : -1,
		order : 2,
		fn : pageObjMgr['edit'],
		quickKey : ['E']
	});
	reg({
		key : 'delete',
		type : 'MetaDBTable',
		desc : '删除这个元数据',
		title : '删除这个元数据...',
		rightIndex : -1,
		order : 3,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'MetaDBTableInRoot',
		desc : '新建一个元数据',
		title : '新建一个元数据...',
		rightIndex : -1,
		order : 4,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'delete',
		type : 'MetaDBTables',
		desc : '删除这些元数据',
		title : '删除这些元数据...',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'build',
		type : 'MetaDBTables',
		desc : '生成视图',
		title : '生成视图...',
		rightIndex : -1,
		order : 6,
		fn : pageObjMgr['build']
	});

})();