com.trs.wcm.ClassInfoClsMaster = Class.create("wcm.ClassInfoClsMaster");
Object.extend(com.trs.wcm.ClassInfoClsMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ClassInfoClsMaster.prototype, {	
	extraParams : function(){
		return Object.extend({fieldsToHtml:'cname,cdesc'}, this.getHostParams());
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters["RightValue"] || "000000000");
		
		//===选中单个对象====//
		if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "ClassInfoCls");	
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "ClassInfoClss");			
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}
	},
	updateAttr : function(_oPostData, _eEditItem){
		var objectId = this.params["ObjectId"];
		ClassInfoClsMgr.save(objectId, _oPostData, null, function(){
			if(_oPostData["NAME"] != null){
				top.$ClassInfoNav().refreshNode({classInfoId : objectId, classInfoName : _oPostData["NAME"]});
			}
		});
	},
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType.toLowerCase()){
			case "classinfocls":
			case "classinfos":
				var oFunc = ClassInfoClsMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("ClassInfoClsMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(ClassInfoClsMgr, sObjectIds, hostParams);
				break;
		}
	}
});
