com.trs.wcm.ViewClsTemplateMaster = Class.create("wcm.ViewClsTemplateMaster");
Object.extend(com.trs.wcm.ViewClsTemplateMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ViewClsTemplateMaster.prototype, {	
	extraParams : function(){
		return {};
	},
	initParams : function(_oPageParameters){
		this.params['channelId'] = _oPageParameters['channelid'];
		this.params['viewId'] = _oPageParameters['viewId'];
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters["objectrights"] || _oPageParameters["RightValue"] || "000000000");
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "ViewClsTemplateRoot");
		
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, "ClassInfoCls");			
			//设置Host的信息，必须和Host的操作类型一致
			this.setHostObject("ClassInfoCls", _oPageParameters["classinfoid"]);
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "ViewClsTemplate");	
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "ViewClsTemplates");			
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}	
	},
	mapResultType : function(sObjType){
		if(sObjType.toLowerCase() == 'classinfocls'){
			return 'classinfo';
		}
		return this.mapType(sObjType);
	},
	saveSuccess : function(){
		
	},
	updateAttr : function(_oPostData,_eEditItem){

	},
	call : function(){
		var oPostData = {
				ObjectId: this.params['ObjectId'],
				channelid: this.params['channelids']||this.params['channelid'],
				fieldsToHtml: 'CrUser,CrTime,DocChannel.Name,DocStatus'
		};
		if(this.isHostObjectAttribute){
			Object.extend(oPostData,{"ContainsRight":true});
		}
		var fOnSuccess = this.getOnSuccess();
		BasicDataHelper.call('wcm6_viewdocument', 'findbyid', oPostData, true, function(_trans, _json){
			fOnSuccess(_trans, (_json||{})["VIEWDOCUMENT"]);
		}.bind(this));
	},
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		var otherParams = Object.clone(this.origParams);
		try{
			if(!otherParams["channelid"]){
				var nChannelId = $main().getPageParams()["channelId"] || 0;
				otherParams["channelid"] = nChannelId;
			}
		}catch(error){
			//alert(error.message);
		}
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType.toLowerCase()){
			case "viewclstemplateroot":
			case "viewclstemplate":
			case "viewclstemplates":
				var oFunc = ViewClsTemplateMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("ViewClsTemplateMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(ViewClsTemplateMgr, sObjectIds, hostParams, otherParams);
				break;
			case "classinfocls":
				if(ClassInfoClsMgr == null){
					alert("没有引入ClassInfoClsMgr");
					return;
				}
				var oFunc = ClassInfoClsMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("ClassInfoClsMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(ClassInfoClsMgr, otherParams["classinfoid"]);
				break;	
		}
	}
});
