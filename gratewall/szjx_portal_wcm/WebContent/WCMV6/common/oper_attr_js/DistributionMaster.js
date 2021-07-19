com.trs.wcm.DistributionMaster = Class.create("wcm.DistributionMaster");
Object.extend(com.trs.wcm.DistributionMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.DistributionMaster.prototype, {	
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.HostRight = _oPageParameters["RightValue"] || "0000000";
		this.setObjectsRight(_oPageParameters["RightValue"] || "0000000");
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "distributionInSite");
		
			

		//设置第二块操作面板的类型		
			//===未选中对象====//
			if(pObjectIds == null || pObjectIds.length == 0){			
				this.setOperPanelType(2, "website");			
			}
			//===选中单个对象====//
			else if(pObjectIds != null && pObjectIds.length == 1){
				this.setOperPanelType(2, "distribution");			
			}
			//===选中多个对象====//
			else if(pObjectIds != null && pObjectIds.length > 1){
				this.setOperPanelType(2, "distributions");			
			}
		

		//设置Host的信息，必须和Host的操作类型一致
		this.setHostObject("website", _oPageParameters["siteid"]);		

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
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();

		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType){
			case "distribution":
			case "distributions":
			case "distributionInSite":
				var oFunc = DistributionMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("DistributionMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(DistributionMgr, sObjectIds, hostParams);
				break;
			case "website":
				if(com.trs.wcm.domain.WebSiteMgr == null){
					alert("没有引入com.trs.wcm.domain.WebSiteMgr");
					return;
				}
				var oFunc = $webSiteMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("com.trs.wcm.domain.WebSiteMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call($webSiteMgr, sObjectIds, hostParams);
				break;			
		}
	}
});
