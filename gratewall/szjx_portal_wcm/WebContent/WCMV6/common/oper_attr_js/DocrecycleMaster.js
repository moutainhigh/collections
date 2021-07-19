com.trs.wcm.DocrecycleMaster = Class.create("wcm.DocrecycleMaster");
Object.extend(com.trs.wcm.DocrecycleMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.DocrecycleMaster.prototype, {	
	extraParams : function(){
		return Object.extend({fieldsToHtml: 'DocTitle,DocChannel.Name'}, this.getHostParams());
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		
		//设置第一块操作面板的类型
		var sHostObjectType,sHostObjectId;
		if(_oPageParameters["channelid"]){
			this.setOperPanelType(1,'docrecycleInChannel');
			sHostObjectType = 'channel';
			sHostObjectId = _oPageParameters["channelid"];
		}
		else if(_oPageParameters["siteid"]){
			this.setOperPanelType(1,'docrecycleInSite');
			sHostObjectType = 'website';
			sHostObjectId = _oPageParameters["siteid"];
		}

		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){
			//一般与HostObject类型一致
			this.setOperPanelType(2, sHostObjectType);			
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "docrecycle");			
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "docrecycles");			
		}
		//设置Host的信息
		this.setHostObject(sHostObjectType, sHostObjectId);		
		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}		
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
		var nRecNum = PageContext.params.ListRecNum || 0;
		switch(_sOperType){
			case 'docrecycle':
			case 'docrecycles':
				$docrecycleMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				break;
			case 'docrecycleInChannel':
				$docrecycleMgr[_sOperName.toLowerCase()](this.getHostParams()['channelid'], oHostParams, nRecNum);
				break;
			case 'docrecycleInSite':
				$docrecycleMgr[_sOperName.toLowerCase()](this.getHostParams()['siteid'], oHostParams, nRecNum);
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
