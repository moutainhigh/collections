com.trs.wcm.DocumentMaster = Class.create("wcm.DocumentMaster");
Object.extend(com.trs.wcm.DocumentMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.DocumentMaster.prototype,{
	onMore : function(){
		var sRight = this.ObjectRight;
		var sId = this.params["ObjectId"];
		var oHostParams = this.getHostParams();
		if(isAccessable4WcmObject(sRight,32)){
			$chnldocMgr['edit'](sId,oHostParams);
		}
		else if(isAccessable4WcmObject(sRight,30)){
			$chnldocMgr['view'](sId,oHostParams);
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
		this.OpersCanNotDo = this.OpersCanNotDo||{};
		//设置第一块操作面板的类型
		var sHostObjectType,sHostObjectId;
		if(_oPageParameters["channelid"]){
			this.setOperPanelType(1,'documentInChannel');
			sHostObjectType = 'channel';
			sHostObjectId = _oPageParameters["channelid"];
		}
		else if(_oPageParameters["siteid"]){
			this.setOperPanelType(1,'documentInSite');
			sHostObjectType = 'website';
			sHostObjectId = _oPageParameters["siteid"];
			//文档在站点下面不应该有改变状态功能
			this.OpersCanNotDo["document"] = this.OpersCanNotDo["documents"] = {
				'changeStatus':true
			}
		}
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){
			//一般与HostObject类型一致
			this.setOperPanelType(2, sHostObjectType);			
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "document");
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "documents");			
		}
		this.params['docids'] = _oPageParameters['docids'];
		this.params['channelids'] = _oPageParameters['channelids'];	

		//设置Host的信息
		this.setHostObject(sHostObjectType, sHostObjectId);		
		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}		
	},
	saveSuccess : function(_bIgnoreUpdate){
		if(!_bIgnoreUpdate){
			$MessageCenter.sendMessage('main','Grid.updateColumn','Grid',this.RowColumnInfo);
//			$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',this.getObjectIds());
		}
	},
	exec : function(_sOperType, _sOperName){//操作响应
		var sObjectIds = this.getObjectIds();
		var oHostParams = this.getHostParams();
		switch(_sOperType){
			case 'document':
				oHostParams['channelids'] = PageContext.params['channelids'];
				oHostParams['docids'] = PageContext.params['docids'];
				$chnlDocMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				break;
			case 'documents':
				oHostParams['channelids'] = PageContext.params['channelids'];
				oHostParams['docids'] = PageContext.params['docids'];
				$chnlDocMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				break;
			case 'documentInChannel':
			case 'documentInSite':
				oHostParams['channelids'] = PageContext.params['channelids'];
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
//		alert($toQueryStr(this.params));
		this.params['ObjectType'] = 'document';
		var oPostData = {
				ObjectId: this.params['docids'],
				channelid: this.params['channelids']
		};
		if(this.isHostObjectAttribute){
			Object.extend(oPostData,{"ContainsRight":true});
		}
		BasicDataHelper.call('wcm6_document', 'findbyid', oPostData, true, this.getOnSuccess());

		delete oPostData;
	}
});
