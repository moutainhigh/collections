com.trs.wcm.WatermarkMaster = Class.create("wcm.WatermarkMaster");
Object.extend(com.trs.wcm.WatermarkMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.WatermarkMaster.prototype, {	
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"] || '';

		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0000000000000000000000');
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "watermarkInSite");
		var sHostObjectType = "website";
		var sHostObjectId = _oPageParameters["siteid"];
		
	
		//设置第二块操作面板的类型		
			//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, sHostObjectType);
		}
		
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){			
			this.setOperPanelType(2, "watermark");					
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "watermarks");			
		}
		

		//设置Host的信息，必须和Host的操作类型一致		
		this.setHostObject(sHostObjectType, sHostObjectId);

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}		
	},
	saveSuccess : function(){
		$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[]);
	},
	
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		var sOprName = _sOprName.toLowerCase();
		switch(_sOprType){
			case "watermarkInSite":
			case "watermark":
			case "watermarks":
				if (window.WatermarkMgr == null) {
					alert("没有引入WatermarkMgr");
					return;
				}
				var oFunc = WatermarkMgr[sOprName];
				if(oFunc == null){
					alert("WatermarkMgr 中没有定义["+sOprName()+"]方法");
					return;
				}
				oFunc.call(WatermarkMgr, sObjectIds, hostParams);
				break;
			case "channel": 
				if(com.trs.wcm.domain.ChannelMgr == null){
					alert("没有引入com.trs.wcm.domain.ChannelMgr");
					return;
				}
				var oFunc = $channelMgr[sOprName()];
				if(oFunc == null){
					alert("com.trs.wcm.domain.ChannelMgr 中没有定义["+sOprName()+"]方法");
					return;
				}
				oFunc.call($channelMgr, sObjectIds, hostParams);
				break;

			case "website":
				if(com.trs.wcm.domain.WebSiteMgr == null){
					alert("没有引入com.trs.wcm.domain.WebSiteMgr");
				}
				var oFunc = $webSiteMgr[sOprName];
				oFunc.call($webSiteMgr,sObjectIds,hostParams);
				break;

			default:
				alert("[WatermarkMgr.js]此操作类型未定义! _sOprType=[" + _sOprType + "]");
				break;
		}
	}
});
