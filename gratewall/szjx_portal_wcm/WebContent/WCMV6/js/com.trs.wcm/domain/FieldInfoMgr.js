$import('com.trs.dialog.Dialog');
var FieldInfoMgr = {
	serviceName : 'wcm6_MetaDataDef',
	deleteMethodName : 'deleteDBFieldInfo',
	saveMethodName : 'saveDBFieldInfo',
	mainWin : null,
	mappingDataType : {
		'selfdefine' : {},
		'password' : {dbType : 'char'},
		'normaltext' : {dbType : 'char'},
		'multitext' : {dbType : 'char'},
		'trueorfalse' : {dbType : 'int'},
		'radio' : {containerType : 'enmValueContainer', dbType : 'char'},
		'checkbox' : {containerType : 'enmValueContainer', dbType : 'char'},
		'select' : {containerType : 'enmValueContainer', dbType : 'char'},
		'appendix' : {dbType : 'char'},
		'classinfo' : {containerType : 'classIdContainer', dbType : 'char'},
		'timestamp' : {dbType : 'timestamp'},
		'editor' : {dbType : 'text'},
		'editorchar' : {dbType : 'char'},
		'relation' : {dbType : 'char'},
		'inputselect' : {containerType : 'enmValueContainer', dbType : 'char'},
		'suggestion' : {containerType : 'enmValueContainer', dbType : 'char'},
		'selfenm' : {containerType : 'enmValueContainer', dbType : 'char'}
	},
	mappingDBDataType : {
		'float': {containerType : 'dbScaleContainer'},
		'double' : {containerType : 'dbScaleContainer'}
	},

	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	
	//=====================================================//
	//====================业务行为==========================//
	//=====================================================//
	belongToSelf : function(){
		var mainWin = this.mainWin || $main();
		if(!mainWin || !mainWin.PageContext 
				|| mainWin.PageContext.ObjectType != "FieldInfo"){
			return; // no self list, nothing will be done.
		}
		return mainWin;
	},
	//删除单个和多个对象
	"delete" : function(_sObjectIds, _oPageParams){
		var mainWin = this.belongToSelf();
		if(!mainWin) return;
		if(typeof _sObjectIds == 'string'){
			_sObjectIds = _sObjectIds.split(",");
		}
		if(Array.isArray(_sObjectIds) && _sObjectIds.length <=0) return;
		var objectIds = [];
		for (var i = 0; i < _sObjectIds.length; i++){
			var eRow = mainWin.$("row_" + _sObjectIds[i]);
			if(eRow){
				 var realId = eRow.getAttribute("realId");
				 if(realId){
					 objectIds.push(realId);
				 }else{
					Element.remove(eRow);
				 }
			}
		}
		if(objectIds.length <= 0){
			return;
		}
		_sObjectIds = '' + objectIds;
		var nCount = _sObjectIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '个元数据字段删除吗? ')){
			ProcessBar.init('删除进度');
			ProcessBar.addState('正在删除...', 2);
			ProcessBar.start();
			this.getHelper().call(this.serviceName, 
				this.deleteMethodName, //远端方法名				
				{"ObjectIds": _sObjectIds}, //传入的参数
				true, //post,get
				function(){//响应函数
					ProcessBar.next();
					//$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
					mainWin.PageContext.RefreshList();
				}
			);
		}
	},	
	
	setEnumValue : function(sEnumValue, fCallBack){
		if(this.objectEnumValue == null) {
			TRSCrashBoard.setMaskable(true);
		}
		var sTitle = '构造枚举值<font style="font-size:12px;font-weight:normal;color:green;">[按Enter追加新行]</font>';
		this.objectEnumValue = TRSDialogContainer.register('setEnumValue', sTitle, './metadata/enumvalue_create.html', '370px', '290px', true, true);
		this.objectEnumValue.onFinished = function(_args){
			if(fCallBack){
				fCallBack(_args)
			}
		};
		TRSDialogContainer.display('setEnumValue', {enumValue : sEnumValue});			
	},

	cancel : function(_sObjectIds, _oPageParams){
		var mainWin = this.belongToSelf();
		if(!mainWin) return;
		mainWin.Grid.cancel.apply(Grid, arguments);
	},

	save : function(_sobjectId, _oParams, _fBeforeSave, _fAfterSave){
		if(typeof _oParams == 'string'){//form id.
			var params = _oParams;
		}else{
			var params = Object.extend({ObjectId : _sobjectId}, _oParams);
		}
		(_fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.serviceName, this.saveMethodName, params, true, function(transport, json){
			(_fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},

	editinrow : function(_sRowId, oParams){
		var mainWin = this.belongToSelf();
		if(!mainWin) return;
		if(mainWin.Grid.editInRow){
			mainWin.Grid.editInRow.call(mainWin.Grid, _sRowId);
		}
	},

	add : function(_sObjectIds, _parameters){
		this._doAddOrEdit(0, _parameters);
	},

	quickAdd : function(){
		var mainWin = this.belongToSelf();
		if(!mainWin) return;
		if(mainWin.Grid.quickAdd){
			mainWin.Grid.quickAdd.apply(mainWin.Grid, arguments);
		}
	},

	edit : function(_sObjectIds, _parameters){
		if(_sObjectIds != 0){
			var mainWin = this.belongToSelf();
			var eRow = mainWin.$("row_" + _sObjectIds);
			if(eRow){
				_sObjectIds = eRow.getAttribute("realId");
				if(!_sObjectIds) return;
			}
		}
		this._doAddOrEdit(_sObjectIds, _parameters);
	},
	
	_doAddOrEdit : function(_nObjectId, _parameters){
		var tableInfoId = getParameter("tableInfoId", $main().location.search);
		var tableAnotherName = getParameter("tableAnotherName", $main().location.search);
		var urlParams = 'objectId='+_nObjectId + '&tableInfoId=' + tableInfoId + '&tableAnotherName=' + tableAnotherName;
		if(_nObjectId == 0){
			var sTilte = "新建元数据字段<span style='font-size:12px;'>--新建第<font color='blue'>[1]</font>个</span>";
		}else{
			var sTilte = "修改元数据字段";
		}
		FloatPanel.open('./metadata/fieldinfo_add_edit.jsp?' + urlParams, sTilte, 570, 350);
	}
};
