com.trs.wcm.PhotoMaster = Class.create("wcm.PhotoMaster");
Object.extend(com.trs.wcm.PhotoMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.PhotoMaster.prototype, {	
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];		
//		alert("location:" + location.href + "\n[PhotoMaster.js]\nparams=[" + _oPageParameters + "]\nparseSource(_oPageParameters)=[" + Object.parseSource(_oPageParameters) + "]\npObjectIds=[" + pObjectIds + "]\nparseSource(pObjectIds)=[" + Object.parseSource(pObjectIds) + "]");

		//设置权限
//		this.setObjectsRight(_oPageParameters["RightValue"] || "0000000");
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		
		//设置第一块操作面板的类型
//		this.setOperPanelType(1, "photoInChannel");
		var sHostObjectType, sHostObjectId;
		if(_oPageParameters["channelid"]){
			this.setOperPanelType(1, "photoInChannel");
			sHostObjectType = 'channel';
			sHostObjectId = _oPageParameters["channelid"];
		}
		else if(_oPageParameters["siteid"]){
			this.setOperPanelType(1, 'photoInSite');
			sHostObjectType = 'website';
			sHostObjectId = _oPageParameters["siteid"];
		}
	
		//设置第二块操作面板的类型		
			//===未选中对象====//
			if(pObjectIds == null || pObjectIds.length == 0){				
				this.setOperPanelType(2, sHostObjectType);
			}
			//===选中单个对象====//
			else if(pObjectIds != null && pObjectIds.length == 1){
				this.setOperPanelType(2, "photo");				
			}
			//===选中多个对象====//
			else if(pObjectIds != null && pObjectIds.length > 1){
				this.setOperPanelType(2, "photos");			
			}
		

		//设置Host的信息，必须和Host的操作类型一致
//		this.setHostObject("channel", _oPageParameters["channelid"]);		
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
		Object.extend(hostParams,{DocId:this.origParams["docids"]});
		
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		var sOprName = _sOprName.toLowerCase();
		switch(_sOprType){
			case "photoInChannel":
			case "photoInSite":
				if (window.PhotoMgr == null) {
					alert("没有引入PhotoMgr");
					return;
				}
				var oFunc = PhotoMgr[sOprName];
				if(oFunc == null){
					alert("PhotoMgr 中没有定义["+sOprName+"]方法");
					return;
				}
				oFunc.call(PhotoMgr, sObjectIds, hostParams);
				break;

			case "photo":
			case "photos":
				var oFunc = PhotoMgr[sOprName];				
				if(oFunc != null){
					oFunc.call(PhotoMgr, sObjectIds, hostParams);
				}else{				
					if($chnlDocMgr == null){
						alert("没有引入com.trs.wcm.domain.ChnlDocMgr");
						return;
					}
					oFunc = $chnlDocMgr[sOprName];
					if(oFunc == null){
						alert("com.trs.wcm.domain.ChnlDocMgr 中没有定义["+sOprName+"]方法");
						return;
					}
					if(sOprName == "publish"){
						oFunc.call($chnlDocMgr, sObjectIds,"detailPublish",hostParams);
					}else{
						oFunc.call($chnlDocMgr, sObjectIds,hostParams);
					}
				}				
				break;

			case "channel": 
				if(com.trs.wcm.domain.ChannelMgr == null){
					alert("没有引入com.trs.wcm.domain.ChannelMgr");
					return;
				}
				var oFunc = $channelMgr[sOprName];
				if(oFunc == null){
					alert("com.trs.wcm.domain.ChannelMgr 中没有定义["+sOprName+"]方法");
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
				alert("[PhotoMaster.js]此操作类型未定义! _sOprType=[" + _sOprType + "]");
				break;
		}
	},
	updateAttr : function(_oPostData, _eEditItem){
		var oPost = Object.extend({"objectid": _eEditItem.getAttribute("_docId",2)}, _oPostData);
		Object.extend(oPost, this._getBasic4Save());

		this.lastPostData = oPost;
		var sFieldName = _eEditItem.getAttribute("_fieldName",2);
		this.RowColumnInfo = {
			"objectid" : _eEditItem.getAttribute("_docId",2),
			"fieldName" : sFieldName,
			"fieldValue" : _oPostData[sFieldName],
			"fieldLabel" : _eEditItem.getAttribute("label",2)
		};		
		
		var sServiceId = "wcm6_viewdocument";//use document service.
		var sMethodName = 'save';

		var bIgnoreRU = null;
		if((bIgnoreRU = _eEditItem.getAttribute('_ignoreRowUpdate', 2)) != null) {
			bIgnoreRU = (bIgnoreRU + '').toLowerCase().trim();
			bIgnoreRU = (bIgnoreRU == 'true' || bIgnoreRU == '1');
		}else{
			bIgnoreRU = false;
		}
		
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(sServiceId,sMethodName,oPost,true,
			function(){
				this._saveSuccess(bIgnoreRU);
			}.bind(this),this._saveFailure.bind(this));
	
		delete _eEditItem;	
	}
});
function drawCalendarIfNeed(_dt){
	var rightValue = PageContext.params["objectrights"];		
	if(isAccessable4WcmObject(rightValue.toString(),32)){
		return TRSCalendar.getHTMLWithTime("DocRelTimeTop",_dt)			
	}
	return "";
};	
function setDisplay(_status){return 10==_status?'inline':'none';};