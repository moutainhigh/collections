$package('com.trs.wcm.domain');

$import('com.trs.dialog.Dialog');
var $templateMgr = com.trs.wcm.domain.TemplateMgr = {
	serviceId : 'wcm6_template',
	helpers : {},
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	'new' : function(_sId, _oPageParams){
		this.edit(0, _oPageParams);
	},
	edit : function(_sId, _oPageParams, _fun){
		//alert($toQueryStr(_oPageParams))
		var sParams = this.__getCurrentParams(_sId, _oPageParams, true);
		if(_fun && PageContext) {
			PageContext.notifyAfterEdit = _fun;
		}else{//否则，进行重置
			delete PageContext.notifyAfterEdit;
		}
		if(_oPageParams && _oPageParams.isTypeStub) {
			sParams += '&typestub=1';
		}
		$openMaxWin('/wcm/WCMV6/template/template_add_edit.html?' + sParams);
		/*if(parseInt(_sId) == 0) {
			$openMaxWin('/wcm/WCMV6/template/template_add_edit.html?' + sParams);
		}else{
			$openMaxWin('/wcm/WCMV6/template/template_add_edit.html?' + sParams, 'template_edit_' + _sId);
		}//*/
	},
	'import' : function(_sId, _oPageParams){
		var sParams = this.__getCurrentParams(_sId, _oPageParams, true)
		FloatPanel.open('./template/template_import.html?' + sParams, '模板导入', 480, 290);
	},
	afterImport : function(_args){
		try{
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);			
		}catch (ex){
			//TODO logger
			//alert(ex.message);
		}
	},		
	'export' : function(_sTempIds, _oHostInfos){
		if(!this.__checkNoRecords(_oHostInfos)) {
			return;
		}
		$beginSimplePB('正在导出模板..',  _oHostInfos.isSolo ? 2 : 4);
		var oPostData = this.__getCurrentPostData(_sTempIds, _oHostInfos, 'exportAll');
		this.getHelper().call(this.serviceId, 'export', oPostData, true, function(_oXMLHttp, _json){
			$endSimplePB();
			var sFileUrl = _oXMLHttp.responseText;
		
			var frm = document.all("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = "/wcm/file/read_file.jsp?DownName=TEMPLATE&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		}.bind(this));//*/
	},
	preview : function(_sTempId, _oPageParams){
		window.open('../template/template_preview.jsp?TempId=' + _sTempId);
	},
	previewFolder : function(_sEmploryId, _nObjectType, _nTempId){
		var oParams = {
			objecttype: _nObjectType,
			objectids: _sEmploryId,
			templateid: _nTempId
		}
		this.getHelper().call('wcm6_publish', 'preview', oParams, true, function(_trans, _json){
			if($v(_json, 'urlcount') != '1') {
				try{
					var errInfo = {
						message: _json.DATA[0].EXCEPTION, 
						detail: _json.DATA[0].EXCEPTIONDETAIL
					};
					$render500Err(_trans, errInfo, true);
				}catch(err){
					//TODO logger
					//alert(err.message);
				}
				return;
			}
			//else
			try{
				var sUrl = $v(_json, 'data')[0]['URLS'];
				window.open(sUrl, 'preview_page');		
			}catch(err){
				//TODO logger
				//alert(err.message);
			}
		})

	}, 
	synch : function(_sTempIds, _oHostInfos){
		if(!this.__checkNoRecords(_oHostInfos)) {
			return;
		}
		if(!_oHostInfos.isSolo && !confirm('此操作将重新分发模板附件，可能需要较长时间。确实要同步附件吗？')) {
			return;
		}
		$beginSimplePB('正在同步模板附件..', _oHostInfos.isSolo ? 2 : 4);
		var oPostData = this.__getCurrentPostData(_sTempIds, _oHostInfos, 'RedistributeAll');
		var dStart = new Date().getTime();
		this.getHelper().call(this.serviceId, 'redistributeAppendixes', oPostData, true, function(){
			var sInterval = (new Date().getTime() - dStart)/1000;
			$endSimplePB();
			$success('成功同步了模板附件！实际耗时<br><b> ' + sInterval + '</b>s.');
		}.bind(this));
	},
	check : function(_sTempIds, _oHostInfos, _bRecheck){
		if(!this.__checkNoRecords(_oHostInfos)) {
			return;
		}
		if(_bRecheck != true && !_oHostInfos.isSolo && !confirm('此操作将校验模板的语法和文法细则，可能需要较长时间。确实要进行模板校验吗？')) {
			return;
		}
		if(_bRecheck != true) {
			$beginSimplePB('正在校验模板..', _oHostInfos.isSolo ? 2 : 5);
		}
		var oPostData = this.__getCurrentPostData(_sTempIds, _oHostInfos, 'CheckAll');
		this.getHelper().call(this.serviceId, 'check', oPostData, true, function(_trans, _json){
			$endSimplePB();
			//ge gfc modify @ 2007-7-6 9:01 指定需要打开模板编辑页面
			//ReportsDialog.show(_json, '模板校验');
			var func = _bRecheck != true ? null : function(){
				$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', null,  true, true);
			}
			ReportsDialog.show(_json, '模板校验', func, {
				option: 'edit',
				renderOption:
					function(_sTempId, _params){
						this.edit(_sTempId, _params, function(_nSavedTempId){
							this.check(_sTempIds, _oHostInfos, true);
						}.bind(this));
					}.bind(this)
			});
		}.bind(this));
	},	
	'delete' : function(_sTempIds,_oPageParams){
		_sTempIds = _sTempIds + '';
		var nCount = (_sTempIds.indexOf(',') == -1) ? 1 : _sTempIds.split(',').length;
		var sHint = (nCount==1)?'':' '+nCount+' ';
		//TODO
		if (confirm('确实要将这' + sHint + '个模板删除吗? ')){
			this.getHelper().call(this.serviceId,'delete', Object.extend(_oPageParams,{"ObjectIds": _sTempIds, "drop": true}), true, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
				}
			);
		}
	},
	synchTemplates : function(_nFolderId, _nObjType, _fCallBack){
		this.getHelper().call(this.serviceId, 'impartTemplateConfig', 
			{objectId: _nFolderId, ObjectType:_nObjType}, true, _fCallBack);
	},
	selectTemplate : function(_pageParams, _fun){
		if(this.m_eTempSelector == null) {
			TRSCrashBoard.setMaskable(true);
		}
		/*
		var sTitle = '为' + this.__getEmployerTypeName(_pageParams.employerType) + '选择'
			+ this.__getTempTypeName(_pageParams.tempType) + '模板';
		*/
		var sTitle = '选择模板';
		this.m_eTempSelector = TRSDialogContainer.register('Template_Select_Solo', sTitle, './template/template_select_multi.html', '370px', '290px');
		var oParams = {
			objecttype: _pageParams.employerType,
			objectid: _pageParams.employerId,
			templatetype: _pageParams.tempType,
			templateids: [_pageParams.rawTempId]
		};
		this.m_eTempSelector.onFinished = function(_args){
			var oPostData = {
				objectid: oParams.objectid, 
				objecttype: oParams.objecttype,
				templateid: _args.selectedIds[0] || 0,
				templatetype: oParams.templatetype
			};
			if(oParams.templateids == 0 && oPostData.templateid == 0)
				return;
			this.getHelper().call('wcm6_template', 'setDefaultTemplate', oPostData, true, _fun);
		}.bind(this);
		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));
		var x = positions[0] - 370;
		var y = positions[1] - 280;
		if(y <= 0){
			y = positions[1] + 20;
		}
		TRSDialogContainer.display('Template_Select_Solo', oParams, x, y);	
	},
	__getCurrentParams : function(_sIds, _oRawParams, _bIsSingle){
		var result = '';
		if(_oRawParams) {
			if(_oRawParams['hostObjectType'] == null) {
				result += $toQueryStr(_oRawParams);
			}else{
				var sHostObjectType = _oRawParams['hostObjectType'].toLowerCase();
				result = 'hosttype=' + (sHostObjectType == 'website' ? 103 : 101);
				var nHostObjectId = _oRawParams['hostObjectId'];
				result += '&hostid=' + parseInt(isNaN(nHostObjectId) ? 0 : nHostObjectId);			
			}
		}

		result += '&' + (_bIsSingle ? 'objectid' : 'objectids') + '=' + _sIds;

		return result;
	},
	__getCurrentPostData : function(_sTempIds, _oHostInfos, _sRenderAllName){
		if(_oHostInfos['hosttype'] != null) {
			_oHostInfos['ObjectIds'] = _sTempIds;
			return _oHostInfos;
		}
		var hostPanelType = _oHostInfos.hostPanelType;
		var oPostData = {};
		if(hostPanelType == 'templateInSite') {//站点下所有模板
			oPostData.hostid = _oHostInfos.hostObjectId;
			oPostData.hosttype = 103;
		}else if(hostPanelType == 'templateInChannel') { //栏目下所有模板
			oPostData.hostid = _oHostInfos.hostObjectId;
			oPostData.hosttype = 101;
		}
		if(hostPanelType == 'template' || hostPanelType == 'templates') { //选中的模板
			oPostData.ObjectIds = _sTempIds;
			var sHostObjectType = _oHostInfos['hostObjectType'].toLowerCase();
			oPostData.hosttype = (sHostObjectType == 'website' ? 103 : 101);
			var nHostObjectId = _oHostInfos['hostObjectId'];
			oPostData.hostid = parseInt(isNaN(nHostObjectId) ? 0 : nHostObjectId);
		}else if(_sRenderAllName) {
			oPostData[_sRenderAllName] = true;
		}else{
			return _oHostInfos;
		}
		oPostData.containsChildren = _oHostInfos.containsChildren || true;
		return oPostData;
	},
	__checkNoRecords : function(_oHostInfos){
		if(_oHostInfos.num <= 0) {
			$fail('没有任何要操作的模板！');
			return false;
		}
		return true;
	},
	__getTempTypeName : function(_type){
		if(this.m_arrTempTypeName == null) {
			this.m_arrTempTypeName = ['嵌套', '概览', '细览'];
		}
		_type = parseInt(_type);
		if(isNaN(_type) || _type < 0 ||  _type > 2) {
			return '';
		}
		return this.m_arrTempTypeName[_type];
	},
	__getEmployerTypeName : function(_type){
		var result = '';
		if(this.m_arrEmployerTypeName == null) {
			this.m_arrEmployerTypeName = [];
			this.m_arrEmployerTypeName['101'] = '栏目';
			this.m_arrEmployerTypeName['103'] = '站点';
			this.m_arrEmployerTypeName['603'] = '文档';
		}
		_type = _type + '';
		if((result = this.m_arrEmployerTypeName[_type]) == null) {
			return '';
		}

		return result;		
	}
};

