com.trs.wcm.DocSynColMaster = com.trs.wcm.DocsyncolMaster = Class.create("wcm.DocsyncolMaster");
Object.extend(com.trs.wcm.DocsyncolMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.DocsyncolMaster.prototype, com.trs.wcm.DocSynMaster);
Object.extend(com.trs.wcm.DocsyncolMaster.prototype, {	
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];
		//设置权限
		this.setObjectsRight(_oPageParameters["RightValue"] || "0000000");
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "docSynColInChannel");

		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, "channel");			
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "docSynCol");			
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "docSynCols");			
		}

		//设置Host的信息，必须和Host的操作类型一致
		this.setHostObject("channel", _oPageParameters["channelid"]);		

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
			case "docSynCol":
			case "docSynCols":
			case "docSynColInChannel":
				var oFunc = DocSynColMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("DocSynColMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(DocSynColMgr, sObjectIds, hostParams);
				break;
			case "channel":
				if(com.trs.wcm.domain.ChannelMgr == null){
					alert("没有引入com.trs.wcm.domain.ChannelMgr");
					return;
				}
				var oFunc = $channelMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("com.trs.wcm.domain.ChannelMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call($channelMgr, hostParams["channelid"]);
				break;	
		}
	}
});
