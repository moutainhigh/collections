com.trs.wcm.DocsearchresultMaster = Class.create("wcm.DocsearchresultMaster");
Object.extend(com.trs.wcm.DocsearchresultMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.DocsearchresultMaster.prototype,{
	onMore : function(){
		var sRight = this.ObjectRight;
		var sId = this.params["ObjectId"];
		var oHostParams = this.getHostParams();
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
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		
		var sHostObjectType,sHostObjectId;
		//隐藏第一块操作面板
		sHostObjectType = null;

		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){
			//一般与HostObject类型一致
			this.setOperPanelType(2, "docsearchinfo");
			this.setHostObject('docsearchinfo');//TODO
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "document");
			// 设置检索文档详细信息所需的额外参数
			//this.setObjectQuery({'channel':  _oPageParameters['channelid']});

			//设置Host的信息
			//alert(_oPageParameters["channelid"] + ', ' + pObjectIds);
			this.setHostObject('channel', _oPageParameters["channelid"]);
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "chnldocs");
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}		
	},
	callBack : function(){
		HostOperatorPanel.hide();
	},
	saveSuccess : function(){
		$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[]);
	},
	saveFailure : function(transport){
		//TODO
		$alert(transport.responseText);
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
				$documentMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				break;
			case 'chnldocs':
				oHostParams['channelids'] = PageContext.params['channelids'];
				oHostParams['docids'] = PageContext.params['docids'];
				oHostParams['siteids'] = PageContext.params['siteids'];
				$chnlDocMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
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
	}
});
