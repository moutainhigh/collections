com.trs.wcm.TableInfoMaster = Class.create("wcm.TableInfoMaster");
Object.extend(com.trs.wcm.TableInfoMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.TableInfoMaster.prototype, {	
	extraParams : function(){
		return Object.extend({fieldsToHtml:'tablename,anothername,tabledesc'}, this.getHostParams());
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters["RightValue"] || "000000000");
		
		//设置第一块操作面板的类型
		this.setOperPanelType(1, "TableInfoRoot");
		
		//设置第二块操作面板的类型		
		//===未选中对象====//
		if(pObjectIds == null || pObjectIds.length == 0){			
			this.setOperPanelType(2, "WebSiteRoot");			
			//设置Host的信息，必须和Host的操作类型一致
			this.setHostObject("WebSiteRoot", _oPageParameters["SiteType"] || 4);
		}
		//===选中单个对象====//
		else if(pObjectIds != null && pObjectIds.length == 1){
			this.setOperPanelType(2, "TableInfo");	
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "TableInfos");			
		}

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}	
	},
	saveSuccess : function(){
		
	},
	updateAttr : function(_oPostData,_eEditItem){
		if(_oPostData["TableName"] && containKeyWords(_oPostData["TableName"])){
			$alert("[<font color='red'>" + _oPostData["TableName"] + "</font>]为系统保留字！", function(){
				$dialog().hide();
				UIEditPanel.setValue(_eEditItem, _eEditItem.getAttribute("value"));
				UIEditPanel.edit(_eEditItem);
			});
			return;
		}

		$tableInfoMgr.save(this.params["ObjectId"], _oPostData, null, function(){
			$MessageCenter.sendMessage('main','TableInfos.updateObject','TableInfos',_oPostData);
		});
	},
	call : function(){
		var sServiceId = CONST.ServiceId[this.params["ObjectType"]];
		var sMethodName = 'findDBTableInfoById';
		var oPost = Object.extend(this._getBasic4Get(),{
			"ObjectId":this.params["ObjectId"],
			"ContainsRight":true
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
			case "tableinforoot":
			case "tableinfo":
			case "tableinfos":
				var oFunc = $tableInfoMgr[_sOprName.toLowerCase()];
				if(oFunc == null){
					alert("TableInfoMgr 中没有定义["+_sOprName.toLowerCase()+"]方法");
					return;
				}
				oFunc.call($tableInfoMgr, sObjectIds, hostParams);
				break;
		}
	}
});
