$package('com.trs.wcm.domain');

$import('com.trs.dialog.Dialog');
$import('com.trs.wcm.domain.PublishMgr');
$import('com.trs.wcm.domain.TemplateMgr');
$import('com.trs.crashboard.CrashBoarder');

com.trs.wcm.domain.TableInfoMgr=Class.create('wcm.domain.website.TableInfoMgr');
com.trs.wcm.domain.TableInfoMgr.prototype = {
	servicesName		: 'wcm6_MetaDataDef',
	saveMethodName		: 'saveDBTableInfo',
	deleteMethodName	: 'deleteDBTableInfo',
	initialize: function(){
	},
	getInstance : function(){
		if($tableInfoMgr == null) {
			$tableInfoMgr = new com.trs.wcm.domain.TableInfoMgr();
		}
		return $tableInfoMgr;
	},
	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();
	},
	/********************************************************
	* 定义业务逻辑
	*********************************************************
	*/
	add : function(_sId, _oPageParams){
		this.edit(0, _oPageParams);
	},
	edit : function(_sId, params){
		var url = './metadata/tableinfo_add_edit.html?objectId=' + _sId;
		FloatPanel.open(url, '新建/修改元数据', 400, 150);
	},
	save : function(_sId, params, fBeforeSave, fAfterSave){
		var oPostData = Object.extend({objectId : _sId || 0}, params || {});
		(fBeforeSave || Prototype.emptyFunction)();
		this.getHelper().call(this.servicesName, this.saveMethodName, oPostData, true, function(transport, json){
			(fAfterSave || Prototype.emptyFunction)(transport, json);
		});
	},
	build : function(_sId, params){
		if(this.tableToViewer == null) {
			TRSCrashBoard.setMaskable(true);
		}
		var sTitle = '生成视图';
		this.tableToViewer = TRSDialogContainer.register('tableToViewer', sTitle, './metadata/build_to_view.html', '800px', '500px', true, true, true);
		TRSDialogContainer.display('tableToViewer', {tableIds: _sId});	
	},
	'delete' : function(_arrIds){
		_arrIds = _arrIds + "";
		var DIALOG_TABLE_INFO_DELETE = 'table_info_dialog_delete';
		TRSCrashBoard.setMaskable(true);
		this.tableInfoDelete = TRSDialogContainer.register(DIALOG_TABLE_INFO_DELETE, '系统提示信息'
		, './metadata/tableinfo_delete_info.html', '500px', '280px', true, true, true);
		this.tableInfoDelete.onFinished = function(){
			ProcessBar.init('进度执行中，请稍候...');
			ProcessBar.addState('正在删除元数据');			
			ProcessBar.addState('删除完成');
			ProcessBar.start();
			this.getHelper().call(this.servicesName, this.deleteMethodName, {objectids:_arrIds}, true, function(){
				ProcessBar.next();
				setTimeout(function(){
					ProcessBar.next();
					setTimeout(function(){
						ProcessBar.exit();
						$MessageCenter.sendMessage('main', 'TableInfos.loadPage', "TableInfos", [null, null]);
					},100);
				},100);
			});

		}.bind(this);
		TRSDialogContainer.display(DIALOG_TABLE_INFO_DELETE, {objectids: _arrIds});	
	},
	trace : function(tableInfoId, params){
		/*
        (top.actualTop || top).location_search0 = window.location.search;
		var urlParams = 'tableInfoId=' + tableInfoId + "&" + $toQueryStr(params);
		window.location.href = 'fieldinfo_list.html?' + urlParams;
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
		*/
		var oParams = ((top.actualTop||top).location_search || $main().location.search).toQueryParams();
		oParams = Object.extend(oParams || {}, Object.extend({tableInfoId:tableInfoId}, params || {}));
		var urlParams = '?' + $toQueryStr(oParams);
		window.status = urlParams;
		$changeSheet(urlParams, 'tableinfo');
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
	}
};

var $tableInfoMgr = new com.trs.wcm.domain.TableInfoMgr();