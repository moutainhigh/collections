//版本管理操作信息和Mgr定义
Ext.ns('wcm.domain.MetaRecDataMgr');
(function(){
	var m_oMgr = wcm.domain.MetaRecDataMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function _addEdit(_id,event){				
		var nObjId = _id || 0;		
		var sTitle = (nObjId == 0)?(wcm.LANG.METAVIEWDATA_77 || "新建"):(wcm.LANG.METAVIEWDATA_106 || "修改");
		sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
		var contextParams = event.getContext().params;		
		var nViewId = event.getObj().getProperty('dockind') || contextParams.VIEWID;
		var oParams = {
			ObjectId:nObjId,			
			FlowDocId:contextParams.FLOWDOCID || 0,
			ViewId:nViewId || 0,
			ClassInfoId : event.getHost().getId()
		};
		$openMaxWin(WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
		/*
		new wcm.CrashBoard({
				title : sTitle,
				src : 'metaviewdata/metaviewdata_addedit.jsp',
				width:'500px',
				height:'210px',
				border:false,				
				params : oParams,
				callback : function(){
					CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();			
				}
		}).show();
		*/
	}
	Ext.apply(wcm.domain.MetaRecDataMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		//*
		add	 : function(event){
			_addEdit(0,event);
		},
		edit : function(event){
			var obj = event.getObj();
			_addEdit(obj.getProperty("docid")||obj.getId(),event);
		},
		"import" : function(event){
			//alert(Ext.toSource(event));
			var _nViewId = event.getContext().params["VIEWID"];
            var _nDocId = event.getHost().getId();
			try{
				if(!_nViewId){
					Ext.Msg.alert(wcm.LANG.METARECDATA_ALERT_10 || "没有指定视图ID[VIEWID]");
					return;
				}
			}catch(error){
				Ext.Msg.alert("ViewTemplateMgr.import:" + error.message);
			}
			var oParams = {
				DocumentId : _nDocId,
				ViewId	 : _nViewId,
				ChannelId : 0,
				SiteId : 0
			}
			FloatPanel.open( WCMConstants.WCM6_PATH + 'metarecdata/view_data_import_cls.jsp?' + $toQueryStr(oParams), wcm.LANG.METARECDATA_FLOAT_TITLE_1 || '导入记录', CMSObj.afteredit(event));
		},
		createfromexcel : function(event){
			wcm.domain.MetaViewDataMgr.createfromexcel(event);
		},
		preview : function(event){
			wcm.domain.MetaViewDataMgr.preview(event);
		},
		basicpublish : function(event){
			wcm.domain.MetaViewDataMgr.basicpublish(event);
		},
		directpublish : function(event){
			wcm.domain.MetaViewDataMgr.directpublish(event);
		},
		detailpublish : function(event){
			wcm.domain.MetaViewDataMgr.detailpublish(event);
		},
		directRecallpublish : function(event){
			wcm.domain.MetaViewDataMgr.directRecallpublish(event);	
		},
		recallpublish : function(event){
			wcm.domain.MetaViewDataMgr.recallpublish(event);
		},
		"export" : function(event){
			wcm.domain.MetaViewDataMgr['export'](event);
		},
		"delete" : function(event){
			wcm.domain.MetaViewDataMgr['delete'](event);
		},
		changestatus : function(event){
			wcm.domain.MetaViewDataMgr.changestatus(event);
		},
		move : function(event){
			var _nclassinfoId = event.getHost().getId();
			var _sDocIds = event.getObjs().getPropertys("docid").join(',');
			var oPostData = {
				'classinfoId' : _nclassinfoId,
				'docIds' : _sDocIds
			}
			FloatPanel.open(  WCMConstants.WCM6_PATH + 'metarecdata/record_moveto_cls.jsp?' + $toQueryStr(oPostData), wcm.LANG.METARECDATA_FLOAT_TITLE_2 || '移动记录', CMSObj.afteredit(event));
		}
		//*/
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.MetaRecDataMgr;
	var reg = wcm.SysOpers.register;
	reg({
		key : 'edit',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_EDIT_DESC || '修改这条记录',
		title : wcm.LANG.METARECDATA_OPERS_EDIT_TITLE || '修改这条记录',
		rightIndex : 32,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'E'
	});
	reg({
		key : 'preview',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_PREVIEW_DESC || '预览这条记录',
		title : wcm.LANG.METARECDATA_OPERS_PREVIEW_TITLE || '预览这条记录发布效果',
		rightIndex : 38,
		order : 2,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'basicpublish',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_BASICPUBLISH_DESC || '发布这条记录',
		title : wcm.LANG.METARECDATA_OPERS_BASICPUBLISH_TITLE || '发布这条记录，生成这条记录的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 3,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'detailpublish',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_DESC || '仅发布这条记录细览',
		title : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_TITLE || '仅发布这条记录细览，仅重新生成这条记录的细览页面',
		rightIndex : 39,
		order : 4,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_ALERT_13 || '直接发布这条记录',
		title : wcm.LANG.METARECDATA_ALERT_14 || '发布这条记录细览，同时发布此记录的所有引用记录',
		rightIndex : 39,
		order : 4,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_DESC || '撤销发布这条记录',
		title : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_TITLE || '撤销发布这条记录，撤回已发布目录或页面',
		rightIndex : 39,
		order : 5,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_ALERT_15 || '直接撤销发布这条记录',
		title : wcm.LANG.METARECDATA_ALERT_16 || '撤回当前记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
		rightIndex : 39,
		order : 5,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'export',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_EXPORT_DESC || '导出这条记录',
		title : wcm.LANG.METARECDATA_OPERS_EXPORT_TITLE || '将这条记录导出成zip文件',
		rightIndex : 34,
		order : 6,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'seperate',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_SEPERATE_DESC || '分隔线',
		title : wcm.LANG.METARECDATA_OPERS_SEPERATE_TITLE || '分隔线',
		rightIndex : -1,
		order : 7,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'changestatus',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_DESC || '改变这条记录的状态',
		title : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_TITLE || '改变这条记录的状态',
		rightIndex : 35,
		order : 8,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'delete',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_DELETE_DESC || '将记录放入废稿箱',
		title : wcm.LANG.METARECDATA_OPERS_DELETE_TITLE || '将记录放入废稿箱',
		rightIndex : 33,
		order : 9,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'move',
		type : 'metarecdata',
		desc : wcm.LANG.METARECDATA_OPERS_MOVE_DESC || '移动这条记录到...',
		title : wcm.LANG.METARECDATA_OPERS_MOVE_DESC || '移动这条记录到...',
		rightIndex : 32,
		order : 10,
		fn : pageObjMgr['move']
	});
	reg({
		key : 'add',
		type : 'metarecdataInClassinfo',
		desc : '新建一条记录',
		title : '新建一条记录...',
		rightIndex : -1,
		order : 11,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'metarecdataInClassinfo',
		desc : wcm.LANG.METARECDATA_OPERS_IMPORT_DESC || '从外部导入记录',
		title : wcm.LANG.METARECDATA_OPERS_IMPORT_TITLE || '从外部导入记录...',
		rightIndex : -1,
		order : 12,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'createfromexcel',
		type : 'metarecdataInClassinfo',
		desc : wcm.LANG.METARECDATA_OPERS_CREATEFROMEXCEL_DESC || '从Excel创建记录',
		title : wcm.LANG.METARECDATA_OPERS_CREATEFROMEXCEL_TITLE || '从Excel创建记录...',
		rightIndex : -1,
		order : 13,
		fn : pageObjMgr['createfromexcel']
	});
	reg({
		key : 'preview',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_PREVIEW_DESCS || '预览这些记录',
		title : wcm.LANG.METARECDATA_OPERS_PREVIEW_TITLES || '预览这些记录发布效果',
		rightIndex : 38,
		order : 14,
		fn : pageObjMgr['preview'],
		quickKey : 'R'
	});
	reg({
		key : 'basicpublish',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_ALERT_12 || '发布这些记录',
		title : wcm.LANG.METARECDATA_OPERS_BASICPUBLISH_TITLES || '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
		rightIndex : 39,
		order : 15,
		fn : pageObjMgr['basicpublish'],
		quickKey : 'P'
	});
	reg({
		key : 'detailpublish',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_DESCS || '仅发布这些记录细览',
		title : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_TITLES || '仅发布这些记录细览，仅重新生成这些记录的细览页面',
		rightIndex : 39,
		order : 16,
		fn : pageObjMgr['detailpublish']
	});
	reg({
		key : 'directpublish',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_ALERT_17 || '直接发布这些记录',
		title : wcm.LANG.METARECDATA_ALERT_18 || '发布这些记录细览，同时发布这些记录的所有引用记录',
		rightIndex : 39,
		order : 16,
		fn : pageObjMgr['directpublish']
	});
	reg({
		key : 'recallpublish',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_DESCS || '撤销发布这些记录',
		title : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_TITLES || '撤销发布这些记录，撤回已发布目录或页面',
		rightIndex : 39,
		order : 17,
		fn : pageObjMgr['recallpublish']
	});
	reg({
		key : 'directRecallpublish',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_ALERT_19 || '直接撤销发布这些记录',
		title : wcm.LANG.METARECDATA_ALERT_20 || '撤回这些记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
		rightIndex : 39,
		order : 17,
		fn : pageObjMgr['directRecallpublish']
	});
	reg({
		key : 'export',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_EXPORT_DESCS || '导出这些记录',
		title : wcm.LANG.METARECDATA_OPERS_EXPORT_TITLES || '将这些记录导出成zip文件',
		rightIndex : 34,
		order : 18,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'seperate',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_SEPERATE_DESC || '分隔线',
		title : wcm.LANG.METARECDATA_OPERS_SEPERATE_TITLE || '分隔线',
		rightIndex : -1,
		order : 19,
		fn : pageObjMgr['seperate']
	});
	reg({
		key : 'changestatus',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_DESCS || '改变这些记录的状态',
		title : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_DESCS || '改变这些记录的状态',
		rightIndex : 35,
		order : 20,
		fn : pageObjMgr['changestatus']
	});
	reg({
		key : 'delete',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_DELETE_DESC || '将记录放入废稿箱',
		title : wcm.LANG.METARECDATA_OPERS_DELETE_TITLE || '将记录放入废稿箱',
		rightIndex : 33,
		order : 21,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'move',
		type : 'metarecdatas',
		desc : wcm.LANG.METARECDATA_OPERS_MOVE_DESCS || '移动这些记录到...',
		title : wcm.LANG.METARECDATA_OPERS_MOVE_DESCS || '移动这些记录到...',
		rightIndex : 32,
		order : 22,
		fn : pageObjMgr['move']
	});

})();