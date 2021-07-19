$import('com.trs.dialog.Dialog');
var ClassInfoMgr = {
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
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
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

	selectClassInfo : function(_sIds, params, fCallBack){
		if(this.objectSelector == null) {
			TRSCrashBoard.setMaskable(true);
		}
		var sTitle = '选择分类法';
		var sURL = getWebURL() + "WCMV6/metadata/classinfo_select_list.html";
		//var sURL = "./metadata/classinfo_select_list.html";
		this.objectSelector = TRSDialogContainer.register('classInfo_Select', sTitle, sURL, '370px', '320px', true, true, true);
		this.objectSelector.onFinished = function(_args){
			if(fCallBack){
				fCallBack(_args)
			}
		};
		TRSDialogContainer.display('classInfo_Select', {ids : _sIds+""});	
	},

	findById : function(_nObjectId, _parameters, fCallBack){
		var oParams = Object.extend({objectId : _nObjectId}, _parameters || {});
		this.getHelper().Call(this.servicesName, this.findMethodName, oParams, true, function(transport, json){
			if(fCallBack) fCallBack(transport, json);
		});
	},

	selectClassInfoTree : function(params, fCallBack){
		if(!params || !params["objectId"]){
			alert("没有指定分类法ID信息");
			return;
		}
		if(!params["objectName"]){
			this.findById(params["objectId"], params, function(transport, json){
				Object.extend(params, {objectName: com.trs.util.JSON.value(json, "CLASSINFO.CNAME")});
				this._selectClassInfoTree.call(this, params, fCallBack);
			}.bind(this));
		}else{
			this._selectClassInfoTree.apply(this, arguments);
		}
	},

	_selectClassInfoTree: function(params, fCallBack){
		if(this.objectTreeSelector == null) {
			TRSCrashBoard.setMaskable(true);
		}
		var sTitle = '选择分类法节点';
		var sURL = getWebURL() + "WCMV6/metadata/classinfo_select_tree.html?treeType="+(params["treeType"]||0);
		//var sURL = "./metadata/classinfo_select_tree.html";
		this.objectTreeSelector = TRSDialogContainer.register('classInfo_Select_Tree', sTitle, sURL, '370px', '290px', true, true, true);
		this.objectTreeSelector.onFinished = function(_args){
			if(fCallBack){
				fCallBack(_args)
			}
		};
		TRSDialogContainer.display('classInfo_Select_Tree', params);			
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
		FloatPanel.open('./metadata/classinfo_config.html?'+params, '分类法维护', 500, 300);
	}
};
