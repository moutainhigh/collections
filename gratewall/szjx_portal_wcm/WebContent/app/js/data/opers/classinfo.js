//分类法操作信息和Mgr定义
WCMConstants.OBJ_TYPE_CLASSINFO = 'classinfo';;
Ext.ns('wcm.domain.classinfoMgr');
(function(){
	var m_oMgr = wcm.domain.classinfoMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	function config(_id,_name){
		var params = {objectId : _id, objectName : _name};
		var url = 'classinfo/classinfo_config.html?' + $toQueryStr(params);		
		FloatPanel.open({
			src : WCMConstants.WCM6_PATH + url,
			title : wcm.LANG.CLASSINFO_25 || '分类法维护'
		});				
	}
	Ext.apply(wcm.domain.classinfoMgr, {		
		"delete" : function(event){
			var objsOrHost = event.getObjsOrHost();
			var aIds = objsOrHost.getIds();
			var nCount = aIds.length;
			var sHint = (nCount == 1)? '' : nCount;
			Ext.Msg.confirm( String.format("确实要将这{0}个分类法删除吗?", sHint),{				
				yes : function(){
					getHelper().call("wcm61_classinfo", 
					"deleteClassInfo", //远端方法名				
					{"ObjectIds": aIds.join(",")}, //传入的参数
					true, 
					function(){//响应函数	
						event.getObjsOrHost()["afterdelete"]();
					}
				)}
			});
		},					
		add : function(event){
			var url = 'classinfo/classinfo_add_edit.html';
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + url,
				title : wcm.LANG.CLASSINFO_9 || '新建一个分类法',
				callback : function(objId, objName){	
					var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
					CMSObj.createFrom(info, null)['afteradd']();
					FloatPanel.close();					
					config.apply(window, arguments);
				}
			});				
		},
		config : function(event){
			var nObjectId = event.getObj().getId();
			var sObjName = event.getObj().getPropertyAsString("objectName")
			config(nObjectId,sObjName);
		},
		'import' : function(event){
			var context = event.getContext();
			var params = {
				OwnerId: context.OwnerId,
				OwnerType: context.OwnerType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'classinfo/classinfo_import.jsp?' + $toQueryStr(params), 
				wcm.LANG.CLASSINFO_13 || '导入分类法', CMSObj.afteradd(event));
		},
		'export' : function(event){
			if(event.length()==0 && event.getObj().getId()==""){
				Ext.Msg.$alert(wcm.LANG.classinfo_1001 || '必须选中至少一个分类法!');
				return;
			}
			var oPostData = {
				ClassInfoIds: event.getIds().join()||event.getObj().getId()
			};
			BasicDataHelper.call('wcm61_classinfo', 'exportClassInfos', oPostData, true, function(_trans, _json){
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
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.classinfoMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'config',
		type : 'ClassInfo',
		desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		rightIndex : -2,
		order : 1,
		fn : pageObjMgr['config'],
		quickKey : ['E']
	});
	reg({
		key : 'delete',
		type : 'ClassInfo',
		desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		rightIndex : -2,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'export',
		type : 'ClassInfo',
		desc : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
		title : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'config',
		type : 'ClassInfoCls',
		desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['config'],
		quickKey : ['E']
	});
	reg({
		key : 'delete',
		type : 'ClassInfoCls',
		desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
		rightIndex : -2,
		order : 4,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'ClassInfoInRoot',
		desc : '新建一个分类法',
		title : '新建一个分类法...',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['add'],
		quickKey : ['N']
	});
	reg({
		key : 'import',
		type : 'ClassInfoInRoot',
		desc : '导入分类法',
		title : '导入分类法...',
		rightIndex : -1,
		order : 6,
		fn : pageObjMgr['import']
	});
	reg({
		key : 'export',
		type : 'ClassInfos',
		desc : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
		title : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
		rightIndex : -2,
		order : 3,
		fn : pageObjMgr['export']
	});
	reg({
		key : 'delete',
		type : 'ClassInfos',
		desc : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
		title : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
		rightIndex : -2,
		order : 7,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();