$import('com.trs.dialog.Dialog');
var ClassInfoClsMgr = {
	servicesName : 'wcm6_ClassInfo',
	deleteMethodName : 'deleteClassInfo',
	saveMethodName : 'saveClassInfo',
	existsMethodName : 'exists',
	findMethodName : 'findById',

	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	
	//=====================================================//
	//====================业务行为==========================//
	//=====================================================//
	//删除单个和多个对象
	"delete" : function(_sObjectIds, _oPageParams){
		_sObjectIds = '' + _sObjectIds;
		var nCount = _sObjectIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		if (confirm('确实要将这' + sHint + '个分类法删除吗? ')){
			ProcessBar.init('删除进度');
			ProcessBar.addState('正在删除...', 2);
			ProcessBar.start();
			this.getHelper().call(this.servicesName, 
				this.deleteMethodName, //远端方法名				
				{"ObjectIds": _sObjectIds}, //传入的参数
				true, //post,get
				function(){//响应函数
					ProcessBar.next();
					try{
						top.$ClassInfoNav().deleteNode(_sObjectIds);
					}catch(error){
						alert(error.message);
						//just skip it.
					}
				}
			);
		}
	},	
	
	save : function(_sobjectId, _oParams, _fBeforeSave, _fAfterSave){
		(_fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.saveMethodName,
				Object.extend({ObjectId : _sobjectId}, _oParams), true, function(transport, json){
			(_fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},

	add : function(_sObjectIds, _parameters){
		this._doAddOrEdit(0, _parameters);
	},

	edit : function(_sObjectIds, _parameters){
		return; //no edit.
		this._doAddOrEdit(_sObjectIds, _parameters);
	},
	
	_doAddOrEdit : function(_nObjectId, _parameters){
		FloatPanel.open('./metadata/classinfo_add_edit.html?objectId='+_nObjectId, '新建分类法', 400, 150);
	},


	findById : function(_nObjectId, _parameters, fCallBack){
		var oParams = Object.extend({objectId : _nObjectId}, _parameters || {});
		this.getHelper().Call(this.servicesName, this.findMethodName, oParams, true, function(transport, json){
			if(fCallBack) fCallBack(transport, json);
		});
	},

	exists : function(oParams, fValid, fInvalid){
		this.getHelper().Call(this.servicesName, this.existsMethodName, oParams, true, function(transport, json){
			$v = com.trs.util.JSON.value;
			var sClassInfoId = $v(json, "CLASSINFO.CLASSINFOID");
			var cName = $v(json, "CLASSINFO.CNAME");
			if(!cName){
				(fInvalid || Prototype.emptyFunction)("notFound");
			}else{
				(fValid || Prototype.emptyFunction)('', sClassInfoId, cName, transport, json);
			}
		});
	},

	"import" : function(){
		FloatPanel.open('./metadata/classinfo_import.jsp', '导入分类法', 500, 180);
	},

	config : function(_nObjectId, _parameters){
		var params = 'objectId=' + _nObjectId;
		var oRow = $main().$("row_" + _nObjectId);
		if(oRow){
			params += "&objectName=" + oRow.getAttribute("objectName");
		}
		FloatPanel.open('./metadata/classinfocls_config.html?'+params, '分类法维护', 500, 300);
	}
};
