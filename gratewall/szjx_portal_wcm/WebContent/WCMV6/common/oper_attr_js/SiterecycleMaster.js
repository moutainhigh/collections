com.trs.wcm.SiterecycleMaster = Class.create("wcm.SiterecycleMaster");
Object.extend(com.trs.wcm.SiterecycleMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.SiterecycleMaster.prototype, {	
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1,'siterecycleHost');

		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){
			this.setOperPanelType(2, 'WebSiteRoot');
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, 'siterecycle');			
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, 'siterecycles');
		}
		//设置Host的信息
		this.setHostObject('WebSiteRoot', _oPageParameters['SiteType']);
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
			case 'siterecycle':
			case 'siterecycles':
				$siteRecycleMgr[_sOperName.toLowerCase()](sObjectIds,oHostParams);
				break;
			case 'siterecycleHost':
				$siteRecycleMgr[_sOperName.toLowerCase()](this.getHostParams()['SiteType'], oHostParams, nRecNum);
				break;
			default:
				alert('错误的操作类型.');
		}
	},
	call : function(){
		var oPost = {
			objectid: this.getObjectIds(),
			fieldsToHtml: 'sitedesc,sitename'
		}
		if(this.isHostObjectAttribute){			
			Object.extend(oPost,{"ContainsRight":true});
		}
		Object.extend(oPost, {"SelectFields":""});
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];
		aCombine.push(oHelper.Combine('wcm6_website', 'findbyid', oPost));
		aCombine.push(oHelper.Combine('wcm6_publish', 'getSitePublishConfig', oPost));
		oHelper.MultiCall(aCombine, this.getOnSuccess());			
	}
});
