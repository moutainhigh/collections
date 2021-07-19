com.trs.wcm.FieldInfoMaster = Class.create("wcm.FieldInfoMaster");
Object.extend(com.trs.wcm.FieldInfoMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.FieldInfoMaster.prototype, {	
	extraParams : function(){
		return Object.extend({fieldsToHtml:'name,desc'}, this.getHostParams());
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters["RightValue"] || "000000000");
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "FieldInfoRoot");
		
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, "WebSiteRoot");			
			//设置Host的信息，必须和Host的操作类型一致
			this.setHostObject("WebSiteRoot", _oPageParameters["SiteType"] || 4);
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "FieldInfo");	
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "FieldInfos");			
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}
	},
	call : function(){
		var sServiceId = CONST.ServiceId[this.params["ObjectType"]];
		var sMethodName = 'findDBFieldInfoById';
		var oPost = Object.extend(this._getBasic4Get(),{
			"ObjectId":this.params["ObjectId"],
			"ContainsRight":true
		});
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(sServiceId,sMethodName,oPost,true,(this.getOnSuccess()));
	},
	saveSuccess : function(objectId){
		$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[objectId]);
	},
	updateAttr : function(_oPostData,_eEditItem){
		FieldInfoMgr.save(this.params["ObjectId"], _oPostData, null, this.master.saveSuccess.bind(this.master, this.params["ObjectId"]));
	},
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType.toLowerCase()){
			case "fieldinforoot":
			case "fieldinfo":
			case "fieldinfos":
				var oFunc = FieldInfoMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("FieldInfoMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(FieldInfoMgr, sObjectIds, hostParams);
				break;
		}
	}
});
