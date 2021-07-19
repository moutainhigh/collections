$package('com.trs.wcm.domain');

$import('com.trs.dialog.Dialog');
$import('com.trs.wcm.domain.PublishMgr');
$import('com.trs.wcm.domain.TemplateMgr');
$import('com.trs.crashboard.CrashBoarder');

com.trs.wcm.domain.ViewInfoMgr=Class.create('wcm.domain.website.ViewInfoMgr');
com.trs.wcm.domain.ViewInfoMgr.prototype = {
	servicesName		: 'wcm6_MetaDataDef',
	saveMethodName		: 'saveView',
	deleteMethodName	: 'deleteView',
	setViewFieldsMethodName		: 'setViewFields',
	setViewEmployerByChannel : 'setViewEmployerByChannel',
	generateMethodName	: 'createViewRelation',
	findViewMethodName	: 'findViewById',
	initialize: function(){
	},
	getInstance : function(){
		if($viewInfoMgr == null) {
			$viewInfoMgr = new com.trs.wcm.domain.ViewInfoMgr();
		}
		return $viewInfoMgr;
	},
	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();
	},
	/********************************************************
	* 定义业务逻辑
	*********************************************************
	*/
	simpleEdit : function(_sId, _oPageParams){
		var url = './metadata/viewinfo_add_edit.html?objectId=' + _sId;
		FloatPanel.open(url, '修改视图', 400, 120);		
	},
	add : function(_sId, _oPageParams){
		//this.edit(0, _oPageParams);
		this.addEditStepOne(0, _oPageParams);
	},
	edit : function(_sId, params){
		if(!params || params["isSingleTable"]){
			this.addEditStepOne(_sId, params);
		}else{
			this.editMultiTable(_sId, params);
		}
	},
	editMultiTable : function(_sId, params){
		if(this.editMultiTabler == null) {
			TRSCrashBoard.setMaskable(true);
		}
		var sTitle = '修改视图';
		this.editMultiTabler = TRSDialogContainer.register('editMultiTabler', sTitle, './metadata/build_to_view.html', '800px', '500px', true, true, true);
		TRSDialogContainer.display('editMultiTabler', {viewId: _sId});	
	},
	addEditStepOne : function(_sId, params){
		var urlParams = "objectId=" + _sId;
		if(params && params["channelId"]){
			urlParams += "&channelId=" + params["channelId"];
		}
		var url = './metadata/viewinfo_add_edit_step1.html?' + urlParams;
		FloatPanel.open(url, '新建/修改视图步骤1:新建或选择表', 400, 120);
	},
	addEditStepTwo : function(_sId, params){
		if(!params || !params["tableId"]){
			this.getHelper().call(this.servicesName, "findViewById", {objectId : _sId}, true, function(transport, json){
				var tableId = com.trs.util.JSON.value(json, "METAVIEW.MAINTABLEID");
				this._addEditStepTwo(_sId, Object.extend({tableId : tableId}, params || {}));
			}.bind(this));
		}else{
			this._addEditStepTwo(_sId, params);
		}
	},
	_addEditStepTwo : function(_sId, params){
		var urlParams = "tableInfoId=" + params["tableId"] + "&viewId=" + _sId;
		var url = './metadata/viewinfo_add_edit_step2.html?' + urlParams;
		FloatPanel.open(url, '新建/修改视图步骤2:新建或选择择物理字段', 800, 400);
	},
	getViewFromChannel : function(_schannelId, params, fBeforeSave, fAfterSave){
		var oPostData = Object.extend({channelId : _schannelId}, params || {});
		(fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, "getViewFromChannel", oPostData, true, function(transport, json){
			(fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},
	findById : function(_sId, params, fBeforeSave, fAfterSave){
		var oPostData = {objectId : _sId};
		(fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.findViewMethodName, oPostData, true, function(transport, json){
			(fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},
	generate : function(_sId, params, fBeforeSave, fAfterSave){
		$beginSimplePB("正在生成应用,请稍后...");
		var oPostData = Object.extend({ViewIds : _sId || 0}, params || {});
		(fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.generateMethodName, oPostData, true, function(transport, json){
			$endSimplePB();
			var isSuccess = com.trs.util.JSON.value(json, "REPORTS.IS_SUCCESS");
			if(isSuccess == false || isSuccess == 'false'){
				ReportsDialog.show(json, "生成应用结果", function(){
                    $dialog().hide(); 
				});
			}
			(fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},
	setViewFields : function(_sId, params, fBeforeSave, fAfterSave){
		var oPostData = Object.extend({ViewId : _sId || 0}, params || {});
		(fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.setViewFieldsMethodName, oPostData, true, function(transport, json){
			(fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},
	setChannelView : function(_sId, params, fBeforeSave, fAfterSave){
		var oPostData = Object.extend({ViewId : _sId || 0}, params || {});
		(fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.setViewEmployerByChannel, oPostData, true, function(transport, json){
			(fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},
	save : function(_sId, params, fBeforeSave, fAfterSave){
		var oPostData = Object.extend({objectId : _sId || 0}, params || {});
		(fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.saveMethodName, oPostData, true, function(transport, json){
			(fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},
	'delete' : function(_arrIds){
		_arrIds = _arrIds + "";
		if(!confirm('确实要将这' + _arrIds.split(",").length + '个视图删除吗? ')){
			return;
		}
		ProcessBar.init('进度执行中，请稍后...');
		ProcessBar.addState('正在删除视图');			
		ProcessBar.addState('删除完成');
		ProcessBar.start();
		this.getHelper().call(this.servicesName, this.deleteMethodName, {objectids:_arrIds}, true, function(transport, json){
			ProcessBar.next();
			setTimeout(function(){
				ProcessBar.next();
				setTimeout(function(){
					ProcessBar.exit();
					var isSuccess = com.trs.util.JSON.value(json, "REPORTS.IS_SUCCESS");
					if(isSuccess != "true"){
						ReportsDialog.show(json, '视图删除结果', function(){
							$MessageCenter.sendMessage('main', 'ViewInfos.loadPage', "ViewInfos", [null, null]);
						});
					}
					$MessageCenter.sendMessage('main', 'ViewInfos.loadPage', "ViewInfos", [null, null]);
				},100);
			},100);
		});
	},
	selectView : function(_sIds, params, fCallBack){
		if(this.objectSelector == null) {
			TRSCrashBoard.setMaskable(true);
		}
		var sTitle = '选择视图';
		this.objectSelector = TRSDialogContainer.register('viewInfo_Select', sTitle, './metadata/viewinfo_select_list.html', '370px', '300px', true, true, true);
		this.objectSelector.onFinished = function(_args){
			fCallBack(_args);
		};
		TRSDialogContainer.display('viewInfo_Select', {ids : _sIds+""});	
	},
	trace : function(viewId, params){
		var oParams = ((top.actualTop||top).location_search || $main().location.search).toQueryParams();
		oParams = Object.extend(oParams || {}, Object.extend({viewId:viewId}, params || {}));
		var urlParams = '?' + $toQueryStr(oParams);
		$changeSheet(urlParams, 'viewinfo');
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
	}
};

var $viewInfoMgr = new com.trs.wcm.domain.ViewInfoMgr();