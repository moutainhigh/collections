com.trs.wcm.VideoMaster = Class.create("wcm.VideoMaster");
Object.extend(com.trs.wcm.VideoMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.VideoMaster.prototype, {

	initParams : function(_oPageParameters){
//		$alert("[VideoMaster.js] initParams():\nparse(_oPageParameters)=" + Object.parseSource(_oPageParameters));

		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		
		//设置第一块操作面板的类型
		var sHostObjectType, sHostObjectId;
		if(_oPageParameters["channelid"]){
			this.setOperPanelType(1, "videoInChannel");
			sHostObjectType = 'channel';
			sHostObjectId = _oPageParameters["channelid"];
		}
		else if(_oPageParameters["siteid"]){
			this.setOperPanelType(1, 'videoInSite');
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
			this.setOperPanelType(2, "video");		
		}
		//===选中多个对象====//
		else if(pObjectIds != null && pObjectIds.length > 1){
			this.setOperPanelType(2, "videos");			
		}

		this.params['docids'] = _oPageParameters['docids'];
		this.params['channelids'] = _oPageParameters['channelid'] || _oPageParameters['channelids'];

		//设置Host的信息，必须和Host的操作类型一致
		this.setHostObject(sHostObjectType, sHostObjectId);

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}		
	},
	
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		var lowerOprName = _sOprName.toLowerCase();
		var oFunc = null;
		var actualMgr = null;
		
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		switch(_sOprType){
			case "videoInChannel":
			case "videoInSite":
				actualMgr = VideoMgr;
				break;

			case "video":
			case "videos":
				hostParams['channelids'] = this.params['channelids'];;
				hostParams['docids'] = this.params['docids'];
				if (lowerOprName == 'play') {
					actualMgr = VideoMgr;
				} else {
					actualMgr = $chnlDocMgr;
				}
				break;

			case "channel":
				actualMgr = $channelMgr;
				break;

			case "website": // ls@2007-4-11 视频站点中选择文档tab时的面板操作
				actualMgr = $webSiteMgr;
				break;

			default:
				alert("[VideoMaster.js]此操作类型未定义! _sOprType=[" + _sOprType + "]\n\n更多信息: _sOprName=[" + _sOprName + "]");
				break;
		}

		if (actualMgr == null) {
			alert("没有找到对应的Mgr! _sOprType=" + _sOprType);
			return;
		}
		oFunc = actualMgr[lowerOprName];
		if(oFunc == null){
// TODO ls@2007-4-17 how to display the Mgr? need a getClass() method!
			alert("Mgr中没有定义[ " + lowerOprName + " ]方法!\n_sOprType=" + _sOprType + ", _sOprName=[" + lowerOprName);
			return;
		}
//		alert("[VideoMaster.js] exec(): case (_sOprType=[ " + _sOprType + " ]\n_sOprName=[" + lowerOprName + "], sObjectIds=" + sObjectIds + "\nlocation=" + location.href + "\nparse(hostParams): " + Object.parseSource(hostParams) + "\nparse(this.params): " + Object.parseSource(this.params) + "\n\nprepare call " + actualMgr + "'s function " + lowerOprName + "():\n" + oFunc);

		oFunc.call(actualMgr, sObjectIds, hostParams);

	},

	/**
	 * Just Override the abstract implements.
	 * @see AbstractMaster in OperAttrPanel.js
	 * @since ls@2007-4-19
	 */
	saveSuccess : function() {
		$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[null, false]);
	},

	call : function(){
		//alert($toQueryStr(this.params));
		this.params['ObjectType'] = 'video';
		var oPostData = {
				ObjectId: this.params['ObjectId'],
				channelid: this.params['channelids'],
				SelectFieldsOfDocument: "DOCID,DocStatus,DocChannel,DocType,DocTitle,DocPubTime,ATTRIBUTE,SubDocTitle,DOCKEYWORDS,DocAbstract,DOCPEOPLE,DocPubUrl,CrUser",
				fieldsToHtml: 'DocChannel.Name,DocTitle'
		};
		if(this.isHostObjectAttribute){
			Object.extend(oPostData,{"ContainsRight":true});
		}
		var fOnSuccess = this.getOnSuccess();
		BasicDataHelper.call('wcm6_viewdocument', 'findbyid', oPostData, true, function(_trans, _json){
			fOnSuccess(_trans, _json);
		}.bind(this));

		delete oPostData;	
	},

	getBasic4Save : function(){
//		$alert("[VideoMaster.js] getBasic4Save(): " + Object.parseSource(this.params));
		if(!this.params['docids']||this.params['docids'].length==0){
			return this.getHostParams();
		}
		else{
			var params = {
				channelid: this.params['channelids'],
				objectid: this.params['docids']
			};
		}
		return params;
	}
});
