/*!
 * File:WorkflowMgr.js,工作流相关操作对象
 *
 *	History			Who			What
 *	2007-03-22		wenyh		created.
 *
 */

var WorkflowMgr = {
	serviceId : 'wcm6_process',
	add : function(_sObjectIds, _parameters){
		this._addEdit(0, _parameters);
	},

	edit : function(_sObjectIds, _parameters){
		this._addEdit(_sObjectIds, _parameters);
	},
	
	'delete' : function(_sFlowIds,_oPageParams){
		var DIALOG_WORKFLOW_EMPLOYMENT_INFO = 'workflow_info_dialog';
		var aTop = (top.actualTop||top);
		TRSCrashBoard.setMaskable(true);
		aTop.m_eEmploymenttInfo = TRSDialogContainer.register(DIALOG_WORKFLOW_EMPLOYMENT_INFO, '系统提示信息'
		, '/wcm/WCMV6/workflow/workflow_employment_info.html', '500px', '280px', true);
		aTop.m_eEmploymenttInfo.setAdjustedPlacing(true);
		aTop.m_eEmploymenttInfo.onFinished = function(){
			BasicDataHelper.call(this.serviceId,'delete', Object.extend(_oPageParams,{'ObjectIds': _sFlowIds, 'drop': true}), true, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);			
		}.bind(this);
		
		TRSDialogContainer.display(DIALOG_WORKFLOW_EMPLOYMENT_INFO, {objectids: _sFlowIds});	
	},
	
	assign : function(_sFlowIds,_oPageParams){		
		Object.extend(_oPageParams,{FlowId:_sFlowIds});
		FloatPanel.open('./workflow/channel_select.html?'+$toQueryStr(_oPageParams),'分配流程',300,370);
	},

	_addEdit : function(_sIds, _params){
		//alert($toQueryStr(_params));// + '\n' + $toQueryStr(PageContext.params))
		var params = {
			objectid: _sIds,
			OwnerId: _params['OwnerId'],
			OwnerType: _params['OwnerType']
		}
		var aTop = (top.actualTop || top);
		var DIALOG_WORKFLOW_EDITOR = 'Dialog_Workflow_Editor';
		if(aTop.m_eFlowEditorDialog == null) {
			var sUrl = '/wcm/WCMV6/workflow/workflow_addedit.jsp';
			var dim = this.getEditorDimensions();
			//单独处理52中的尺寸设置
			if((window.screen.width / 1024) == 1) {
				dim.height = dim.height - 50;
				dim.width  = dim.width + 80; 
			}

			aTop.m_eFlowEditorDialog = TRSDialogContainer.register(DIALOG_WORKFLOW_EDITOR, '新建/修改工作流', sUrl, 
				dim.width, dim.height, true, true, true);		
		}
		aTop.m_eFlowEditorDialog.onFinished = function(_args){
			if(_params['flowOfChannelid'] && _sIds == 0 && _args['id'] > 0) {
				BasicDataHelper.call('wcm6_process', 'enableFlowToChannel', {ObjectId: _params['flowOfChannelid'], FlowId: _args['id']}, true, function(_transport,_json){					
					$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', _args['id'], true);
				});
			}else{
				$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', _args['id'], true);
			}
		}
		TRSCrashBoard.setMaskable(true);
		TRSDialogContainer.display(DIALOG_WORKFLOW_EDITOR, params);
	},
	
	disableflow : function(_id,_oPageparams){				
		if (confirm('确实要取消该栏目上的流程吗? ')){
			BasicDataHelper.call(this.serviceId,'disableFlowToChannel', {'ObjectId': _id, 'FlowId': _oPageparams['ObjectId']}, false, 
				function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', []);
				}
			);
		}
	},
	
	setemployer : function(_id,_oPageparams){
		var id = _id || 0;
		var params = {
			FlowId: id,
			OwnerType: _oPageparams['OwnerType'],
			OwnerId: _oPageparams['OwnerId']
		};
		if(_oPageparams['flowOfChannelid']) {
			Object.extend(params, {
				OwnerType: 101,
				OwnerId: _oPageparams['flowOfChannelid'],
				flowOfChannelid: _oPageparams['flowOfChannelid']
			});
		}

		if(this.flowSelector == null){
			this.flowSelector = new FlowSelector();
		}
		this.flowSelector.selectFlow(params);
	},
	'export' : function(_ids, _params){
		if(!this.__checkNoRecords(_params)) {
			return;
		}
		$beginSimplePB('正在导出工作流..',  3);
		var oPostData = {
			objectIds: _ids
		};
		BasicDataHelper.call(this.serviceId, 'exportFlows', oPostData, true, function(_trans, _json){
			$endSimplePB();
			var sFileUrl = _trans.responseText;
		
			var frm = document.all("ifrmDownload");
			if(frm == null) {
				frm = document.createElement('iframe');
				frm.style.height = 0;
				frm.style.width = 0;
				document.body.appendChild(frm);
			}
			sFileUrl = "/wcm/file/read_file.jsp?DownName=WORKFLOW&FileName=" + sFileUrl;
			frm.src = sFileUrl;
		}.bind(this));
			
	},
	'import' : function(_sId, _params){
		var params = {
			OwnerId: _params['OwnerId'],
			OwnerType: _params['OwnerType']
		}
		FloatPanel.open('./workflow/workflow_import.html?' + $toQueryStr(params)
			, '工作流导入', 400, 100);
	},
	afterImport : function(_args){
		try{
			$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);			
		}catch (ex){
			//TODO logger
			//alert(ex.message);
		}
	},	
	createfrom : function(_ids, _params){
		if(!this.__checkNoRecords(_params)) {
			return;
		}
		if(_params && _params['infoview'] === true) {
			var sHtml = '<br><input type="checkbox" id="chkWithFields">'
					+ '连同表单字段信息进行类似创建<br>';
			$confirm(sHtml, function(){
				$dialog().hide();
				this.__renderCreateFrom(_ids, $dialogElement('chkWithFields').checked);
			}.bind(this), function(){
				$dialog().hide();
				return;
			});
			return;
		}
		//else
		this.__renderCreateFrom(_ids, false);
	},
	__renderCreateFrom : function(_ids, _bWithFields){
		if(_bWithFields == null) {
			_bWithFields = false;
		}
		$beginSimplePB('正在类似创建工作流..',  3);
		var oPostData = {
			objectIds: _ids,
			createWithFlowNodeFields: _bWithFields
		};
		BasicDataHelper.call(this.serviceId, 'createfrom', oPostData, true, function(_trans, _json){
			$endSimplePB();
			var bSucess = $v(_json, 'REPORTS.IS_SUCCESS');
			ReportsDialog.show(_json, '工作流类似创建结果', function(){
				if(bSucess != 'true') {
					return;
				}
				//通知列表页面刷新
				PageContext.RefreshList();
			});
		}.bind(this));		
	},
	__checkNoRecords : function(_oHostInfos){
		if(_oHostInfos.num <= 0) {
			$fail('没有任何要操作的工作流！');
			return false;
		}
		return true;
	},
	getEditorDimensions : function(){
		var nRateWidth  = window.screen.width / 1024;
		var nRateHeight = window.screen.height / 768;
		return {width: nRateWidth * 650, height: nRateHeight * 500};
	},
	showFlowEditorOfMain : function(){
		try{
			if($main() && $main().showFlowEditor) {
				$main().showFlowEditor();
			}
			//TRSDialogContainer.close(DIALOG_WORKFLOW_EDITOR);
		}catch(err){
			//just skip it
		}		
	},
	hideFlowEditorOfMain : function(){
		try{
			if($main() && $main().hideFlowEditor) {
				$main().hideFlowEditor();
			}				
		}catch(err){
			//just skip it
		}		
	}
}
var $workflowMgr = WorkflowMgr;
var DIALOG_WORKFLOW_SELECTOR = 'Dialog_Workflow_Selector';
FlowSelector = Class.create('FlowSelector');
FlowSelector.prototype = {
	initialize : function(){
		var aTop = (top.actualTop || top);
		aTop.m_eFlowSelectorDialog = TRSDialogContainer.register(DIALOG_WORKFLOW_SELECTOR, '选择工作流'
			, './workflow/workflow_select.html', '370px', '320px', true);
		var _self = this;
		aTop.m_eFlowSelectorDialog.onFinished = function(_args){			
			var nFlowId = _args['FlowId'];
			if(_args['disabled'] == true && _self.oldFlowId != 0){
				BasicDataHelper.call('wcm6_process','disableFlowToChannel',{ObjectId:_args['ChannelId'],FlowId: 0},true,function(_transport,_json){					
					$MessageCenter.sendMessage('main', 'PageContext.refreshCurrRows', 'PageContext', []);
				});
			}else if(nFlowId != _self.oldFlowId){							
				BasicDataHelper.call('wcm6_process','enableFlowToChannel',{ObjectId:_args['ChannelId'],FlowId:nFlowId},true,function(_transport,_json){					
					$MessageCenter.sendMessage('main', 'PageContext.refreshCurrRows', 'PageContext', []);
				});			
			}
		};

		TRSCrashBoard.setMaskable(true);		
	},
	selectFlow : function(_args){
		var nFlowId = parseInt(_args.FlowId);
		if(isNaN(nFlowId)){
			this.oldFlowId = 0;
		}else{
			this.oldFlowId = nFlowId;
		}
		
		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));		
		
		var aTop = (top.actualTop || top);

		TRSDialogContainer.display(DIALOG_WORKFLOW_SELECTOR, _args,positions[0]-300, positions[1]-25);
	}
}

