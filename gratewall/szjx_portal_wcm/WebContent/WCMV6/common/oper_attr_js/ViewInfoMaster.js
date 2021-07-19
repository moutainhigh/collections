com.trs.wcm.ViewInfoMaster = Class.create("wcm.ViewInfoMaster");
Object.extend(com.trs.wcm.ViewInfoMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ViewInfoMaster.prototype, {	
	extraParams : function(){
		return Object.extend({fieldsToHtml:'viewname,viewdesc'}, this.getHostParams());
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters["RightValue"] || "000000000");
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "ViewInfoRoot");
		
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, "WebSiteRoot");			
			//设置Host的信息，必须和Host的操作类型一致
			this.setHostObject("WebSiteRoot", _oPageParameters["SiteType"] || 4);
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "ViewInfo");	
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "ViewInfos");			
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}	
	},
	saveSuccess : function(){
		
	},
	updateAttr : function(_oPostData,_eEditItem){
		$viewInfoMgr.save(this.params["ObjectId"], _oPostData, null, function(){
			$MessageCenter.sendMessage('main','ViewInfos.updateObject','ViewInfos',_oPostData);
		});
	},
	call : function(){
		var sServiceId = CONST.ServiceId[this.params["ObjectType"]];
		var sMethodName = 'findViewById';
		var oPost = Object.extend(this._getBasic4Get(),{
			"ObjectId":this.params["ObjectId"],
			"ContainsRight":true
		});
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(sServiceId,sMethodName,oPost,true,(this.getOnSuccess()));
	},
	//操作响应
	exec : function(_sOprType, _sOprName, _oParams1, _oParams2){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType.toLowerCase()){
			case "viewinforoot":
			case "viewinfo":
			case "viewinfos":
				var oFunc = $viewInfoMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("ViewInfoMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				if(_sOprName.toLowerCase() != 'edit'){
					oFunc.call($viewInfoMgr, sObjectIds, hostParams);
				}else{//之所以会有这个if,else，是为了确保原功能的正确性
					oFunc.call($viewInfoMgr, sObjectIds, _oParams2);
				}
				break;
		}
	}
});
