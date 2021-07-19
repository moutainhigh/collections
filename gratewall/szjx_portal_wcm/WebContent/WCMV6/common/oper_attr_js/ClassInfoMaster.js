com.trs.wcm.ClassInfoMaster = Class.create("wcm.ClassInfoMaster");
Object.extend(com.trs.wcm.ClassInfoMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ClassInfoMaster.prototype, {	
	extraParams : function(){
		return Object.extend({fieldsToHtml:'cname,cdesc'}, this.getHostParams());
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters["RightValue"] || "000000000");
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "ClassInfoRoot");
		
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, "WebSiteRoot");			
			//设置Host的信息，必须和Host的操作类型一致
			this.setHostObject("WebSiteRoot", _oPageParameters["SiteType"] || 4);
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "ClassInfo");	
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "ClassInfos");			
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}
	},
	saveSuccess : function(objectId){
		$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[objectId]);
	},
	updateAttr : function(_oPostData,_eEditItem){
		ClassInfoMgr.save(this.params["ObjectId"], _oPostData, null, this.master.saveSuccess.bind(this.master, this.params["ObjectId"]));
	},
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType.toLowerCase()){
			case "classinforoot":
			case "classinfo":
			case "classinfos":
				var oFunc = ClassInfoMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("ClassInfoMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(ClassInfoMgr, sObjectIds, hostParams);
				break;
		}
	}
});
