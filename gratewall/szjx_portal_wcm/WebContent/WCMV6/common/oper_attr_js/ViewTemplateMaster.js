com.trs.wcm.ViewTemplateMaster = Class.create("wcm.ViewTemplateMaster");
Object.extend(com.trs.wcm.ViewTemplateMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ViewTemplateMaster.prototype, {	
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
		this.setOperPanelType(1, "ViewTemplateRoot");
		
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, "channel");			
			//设置Host的信息，必须和Host的操作类型一致
			this.setHostObject("channel", _oPageParameters["channelid"]);
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "ViewTemplate");	
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "ViewTemplates");			
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}	
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

		return;

		this.getOnSuccess()(null, {});
		return;
		var sDocId = ViewTemplateMgr.preProcessParams(this.params["ObjectId"]).docIds;
		var sServiceId = CONST.ServiceId[this.params["ObjectType"]];
		var sMethodName = 'findViewDataById';
		var oPost = Object.extend(this._getBasic4Get(),{
			"ChannelId": this.params["channelId"],
			"ViewId": this.params["viewId"],
			"ObjectId":sDocId,
			"ContainsRight":true,
			SelectFields : 'DOCSTATUS,CHANNELID'
		});
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(sServiceId,sMethodName,oPost,true,(this.getOnSuccess()));
	},
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType.toLowerCase()){
			case "viewtemplateroot":
			case "viewtemplate":
			case "viewtemplates":
				var oFunc = ViewTemplateMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("ViewTemplateMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call(ViewTemplateMgr, sObjectIds, hostParams, this.origParams);
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
