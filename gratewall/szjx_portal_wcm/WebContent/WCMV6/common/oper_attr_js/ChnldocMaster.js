com.trs.wcm.ChnldocMaster = Class.create("wcm.ChnldocMaster");
Object.extend(com.trs.wcm.ChnldocMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ChnldocMaster.prototype,{
	onMore : function(){
		var sRight = this.ObjectRight;
		var sId = this.params["ObjectId"];
		var oHostParams ={
				docids: this.params['docids'],
				channelid: this.params['channelids']
		};
		if(isAccessable4WcmObject(sRight,32)){
			$chnlDocMgr['edit'](sId,oHostParams);
		}
		else if(isAccessable4WcmObject(sRight,30)){
			$chnlDocMgr['view'](sId,oHostParams);
		}
		else{
			$alert('您没有权限查看/修改这篇[文档-'+sId+'].');
		}
	},
	getBasic4Save : function(){
		if(!this.params['docids']||this.params['docids'].length==0){
			return this.getHostParams();
		}
		else{
			var params = {
				channelid: this.params['channelids'],
				objectid: this.params['docids']
			};
		}
		return params;
	},
	initParams : function(_oPageParameters){
		this.IsSearch = (_oPageParameters['disable_sheet'] == true);

		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		this.OpersCanNotDo = this.OpersCanNotDo || {};
		//设置第一块操作面板的类型
		var sHostObjectType,sHostObjectId;
		if(this.IsSearch) {
			sHostObjectType = null;
		}else{
			if(_oPageParameters["channelid"]){
				this.setOperPanelType(1,'documentInChannel');
				sHostObjectType = 'channel';
				sHostObjectId = _oPageParameters["channelid"];
//				this.OpersCanNotDo["documentInChannel"] = {
//				}
				this.OpersCanNotDo["chnldoc"] = this.OpersCanNotDo["chnldocs"] = {
					'docpositionset':false
				}
			}
			else if(_oPageParameters["siteid"]){
				this.setOperPanelType(1,'documentInSite');
				sHostObjectType = 'website';
				sHostObjectId = _oPageParameters["siteid"];
				//文档在站点下面不应该有改变状态功能
				this.OpersCanNotDo["chnldoc"] = this.OpersCanNotDo["chnldocs"] = {
					'docpositionset':true
//					'changeStatus':true
				}
				this.OpersCanNotDo["documentInSite"] = {
					'copy':true,
					'move':true
				}
			}
		}
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){
			//一般与HostObject类型一致
			if(this.IsSearch) {
				this.setOperPanelType(2, 'docsearchinfo');
				this.setHostObject('docsearchinfo');//TODO
			}else{
				this.setOperPanelType(2, sHostObjectType);			
			}
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "chnldoc");
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "chnldocs");			
		}
		this.params['docids'] = _oPageParameters['docids'];
		this.params['channelids'] = _oPageParameters['channelids'] || _oPageParameters['channelid'];	
		if(Array.isArray(_oPageParameters['channelids'])&&_oPageParameters['channelids'].length==0){
			this.params['channelids'] = _oPageParameters['channelid'];
		}
		
		//设置Host的信息
		if(!this.IsSearch) {
			this.setHostObject(sHostObjectType, sHostObjectId);
		}
		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}		
	},
	exec : function(_sOperType, _sOperName){//操作响应
		var sObjectIds = this.getObjectIds();
		var oHostParams = this.getHostParams();
		for( var sName in oHostParams){
			if(sName == null || oHostParams[sName] == null) {
				delete oHostParams[sName];
			}
		}

		//alert($toQueryStr(PageContext.params));
		switch(_sOperType){
			case 'document':
				//$documentMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				//break;
			case 'chnldoc':
				oHostParams['channelids'] = this.params['channelids'];
				oHostParams['docids'] = this.params['docids'];
				$chnlDocMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				break;
			case 'chnldocs':
				oHostParams['channelids'] = this.params['channelids'];
				oHostParams['docids'] = this.params['docids'];
				$chnlDocMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				break;
			case 'documentInChannel':
			case 'documentInSite':
				oHostParams['channelids'] = this.params['channelids'];
				$chnlDocMgr[_sOperName.toLowerCase()](0,oHostParams);
				break;
			case 'channel':
				$channelMgr[_sOperName.toLowerCase()](sObjectIds);
				break;
			case 'website':
				$webSiteMgr[_sOperName.toLowerCase()](sObjectIds);
				break;
			default:
				alert('错误的操作类型.');
		}
	},
	call : function(){
		//alert($toQueryStr(this.params));
		this.params['ObjectType'] = 'chnldoc';
		var oPostData = {
				ObjectId: this.params['ObjectId'],
				channelid: this.params['channelids'],
				fieldsToHtml: 'DocChannel.Name,DocTitle,DocPeople,SubDocTitle,DocSourceName,DOCKEYWORDS'
		};
		if(this.isHostObjectAttribute){
			Object.extend(oPostData,{"ContainsRight":true});
		}
		var fOnSuccess = this.getOnSuccess();
		BasicDataHelper.call('wcm6_viewdocument', 'findbyid', oPostData, true, function(_trans, _json){
			if(this.IsSearch) {
				HostOperatorPanel.hide();
			}			
			fOnSuccess(_trans, _json);
			PageContext.loadSources();
		}.bind(this));

		delete oPostData;	
	},
	updateAttr : function(){
		var oPostData = this.lastPostData;
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		//alert($toQueryStr(oPostData) + '\n' + $toQueryStr(this.RowColumnInfo))
		if(oPostData['docstatus']){ // 保存状态
			var params = {
				objectids: this.params['ObjectId'],
				statusid: oPostData['docstatus']
			}
			oHelper.call('wcm6_viewdocument', 'changeStatus', params, true, function(){
				$MessageCenter.sendMessage('main','Grid.updateColumn','Grid', this.RowColumnInfo);
			}.bind(this));
		}else{
			oHelper.call('wcm6_viewdocument', 'save', oPostData, true, function(){
				//TODO 需要提供wcm6_viewdocument.findbyid方法
				var docStatus = UIFilterSelect.getValue('selStatus');
				if(docStatus == '10') { // 如果原先状态为'已发'，则强制为新稿
					//直接刷新
					$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[null, false]);
				}else{
					$MessageCenter.sendMessage('main','Grid.updateColumn','Grid', this.RowColumnInfo);				
				}
				
			}.bind(this));
		}//*/
	}
});
var DocSourceHelper = {
	NotifyInput : function(){
		$('DocSourceName').value = $('DocSource').value;
		PageContext.saveDocSource();
	},
	NotifySelect : function(){
		$('DocSource').value = $('DocSourceName').value;
	}
};
Object.extend(PageContext, {
	updateAfterPublish : function(_pubInfo){
		var arIds = _pubInfo['ObjectIds'];
		if(arIds.length != 1) {
			return;
		}
		var sStatus = _pubInfo['StatusValue'];
		// begin to update 
		UIFilterSelect.setValue('selStatus', sStatus);
	},
	loadSources : function(){
		var nRightIndex = $('SourceContainer').getAttribute("rightIndex",2);
		var sRight = OperAttrPanel.ObjectRight;
		if(nRightIndex!=null && !isAccessable4WcmObject(sRight,nRightIndex)){
			Element.hide('divDocSource');
			PageContext.__SourceRight = false;
			return;
		}
		Element.show('divDocSource');
		PageContext.__SourceRight = true;
		$('DocSourceName').readOnly = false;
		var sHtml = '<select name="DocSource" id="DocSource" onclick="DocSourceHelper.NotifySelect();"' 
			+ ' onchange="DocSourceHelper.NotifyInput();" >'
		sHtml += '<option value="">请选择...</option>';
		var allSources = PageContext.getAllDocSource();
		for (var i = 0; i < allSources.length; i++){
			sHtml += '<option value="' + allSources[i].label + '">' + allSources[i].title + '</option>';
		}
		sHtml += '</select>';
		$('divDocSource').innerHTML = sHtml;
	},
	SourceName : function(_sName,_sId){
		if(parseInt(_sId)<=0)return '';
		return _sName;
	},
	SourceMouseOver : function(){
		if(PageContext.__SourceRight)
			$('SourceContainer').className = 'active';
	},
	SourceMouseOut : function(){
		if(PageContext.__SourceRight&&!PageContext.__SourceClickInput)
			$('SourceContainer').className = 'deactive';
	},
	SourceInputClick : function(){
		if(!$('DocSourceName').readOnly){
			PageContext.__SourceClickInput = true;
			$('DocSourceName').className = 'source_input_active';
//			$('SourceContainer').className = 'active';
		}
	},
	SourceInputBlur : function(){
		if(!$('DocSourceName').readOnly){
			PageContext.__SourceClickInput = false;
			setTimeout(function(){
				PageContext.saveDocSource();
			},200);
			$('DocSourceName').className = 'source_input_deactive';
//			$('SourceContainer').className = 'deactive';
		}
	},
	saveDocSource : function(){
		var sLastDocSourceName = $('SourceContainer').getAttribute('lastValue',2) || '';
		var nDocSourceName = $('DocSourceName').value;
		if(sLastDocSourceName.trim() != nDocSourceName.trim()){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var nCurrDocId = OperAttrPanel.params["docids"];
			var nCurrChannelId = OperAttrPanel.params["channelids"];
			this._saveDocumentSourceName(nDocSourceName, nCurrDocId, nCurrChannelId);
		}
	},
	_saveDocumentSourceName : function(_nDocSourceName, _nCurrDocId, _nDocChannelId){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var oPostData = {
			ObjectId : _nCurrDocId,
			ChannelId : _nDocChannelId,
			DocSourceName : _nDocSourceName
		}
		oHelper.Call('wcm6_document','save',oPostData,true,null
//			function(_transport,_json){
//				$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[null, false]);
//			}
		);
	}
});