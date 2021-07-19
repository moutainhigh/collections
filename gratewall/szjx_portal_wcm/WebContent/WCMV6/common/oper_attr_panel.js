var baseUrl = self.location.href.replace(/(\/|\\)[^\\\/]*\.html(\?.*|#.*)?/g,'');
Object.extend(PageContext,{
	response : function(_oParams){
		if(this.windowLoaded&&!this.running){
			this.render(_oParams);
		}
		else{
			this.wait(_oParams);
		}
	},
	render : function(_oParams){
		this.running = true;
		this.waitingParams = null;
		var sType = _oParams["objecttype"];
		//TODO
		if(_oParams["OperAttrPanelPath"]){
			OperAttrPanel.Path[sType] = {
					"templater":baseUrl+_oParams["OperAttrPanelPath"]["templater"],
					"js":baseUrl+_oParams["OperAttrPanelPath"]["js"]
			};
		}
		else{
			OperAttrPanel.Path[sType] = {
					"templater":baseUrl+'/oper_attr_templater/',
					"js":baseUrl+'/oper_attr_js/'
				};
		}
		this.refresh("type="+sType);
		Object.extend(this.params, _oParams);
//		setTimeout(function(){
			try{
				OperAttrPanel.loadData(_oParams);
			}catch(error){
				//TODO logger
				/*
				*fix a bug. _oParams may be no attribute object. eg. _oParams = {};
				*because the PageContext was deleted by $destroy(PageContext) in the index page;
				*/
				this.running = false;
			}
//		},100);
	},
	wait : function(_oParams){
		this.waitingParams = _oParams;
	},
	notify : function(){
		if(!this.running){
			if(this.waitingParams){
				this.render(this.waitingParams);
			}
		}
	},
	reload : function(){
		try{
			PageContext.response(PageContext.params);
		}catch(err){
			//TODO logger
			//alert(err.message);
		}
		
	},
	updateAttribute : function(_oPostData, _eEditItem){
		OperAttrPanel.updateAttribute(_oPostData, _eEditItem);
		delete _eEditItem;
	},
	getAllDocSource : function(){
		return com.trs.wcm.AllDocSource;
	},
	filterDocStatus : function(_sRight){
		var statuses = com.trs.wcm.AllDocStatus;
		var retArr = statuses.findAll(function(e){
			return isAccessable4WcmObject(_sRight,e["rightindex"]);
		});
		return retArr;
	},
	getAllDocStatus : function(){
		return com.trs.wcm.AllDocStatus;
	},
	refresh : function(_params){
		var sType = getParameter("type",_params);
		PageContext.params = {"type":sType};
		OperAttrPanel.init(PanelMasterFactory.create(sType));
		OperAttrPanel.cachedHostObject = null;
	},
	getAllTempTypes : function(){
		return [
				{label: '概览', value: 1}, 
				{label: '细览', value: 2},
				{label: '嵌套', value: 0}
			];
    },
	setContainsChildren: function(_bVal){
		PageContext.params.containsChildren = _bVal || false;
	},
	getQueryDocStatusFunction : function(_sDocumentId,_iChannelId){
		return function(_fCallBack){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.call('wcm6_status','queryDocumentStatuses',
				{"ChannelId":_iChannelId,"ChnlDocIds":_sDocumentId}, true, 
				function(_transport,_json){
					var Json = com.trs.util.JSON;
					var statuses = Json.array(_json,'Statuses.Status')||[];
					var statusIds = [];
					statuses.each(function(e){
						statusIds.push(Json.value(e,'StatusId'));
					});
					_fCallBack(statusIds);
				}
			);
		};
	}
});

//channel master define
com.trs.wcm.ChannelMaster = Class.create("wcm.ChannelMaster");
Object.extend(com.trs.wcm.ChannelMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ChannelMaster.prototype,{
	onMore : function(){
		var sRight = this.ObjectRight;
		var sId = this.params["ObjectId"];
		if(isAccessable4WcmObject(sRight, 13)){
			$channelMgr['edit'](sId);
		}
		else if(isAccessable4WcmObject(sRight, 64)){
			$channelMgr['view'](sId);
		}
		else{
			$alert('您没有权限查看/修改[栏目-' + sId + ']!');
		}
	},
	arrangeParams : function(_oInfo){
		var params = {};
		var channelIds = _oInfo['objectids'];
		if(_oInfo['channelid']){
			//params["HostObjectType"] = channelIds.length == 0 ? 'channel' : 'channelMaster';
			params["HostObjectType"] = 'channelMaster';
			params["HostType"] = 'channelHost';
			params["HostObjectId"] = _oInfo["channelid"];
		}
		else if(_oInfo['siteid']){
			//alert('curr site :??' + PageContext["siteid"])
			params["HostObjectType"] = 'website';
			params["HostType"] = 'websiteHost';
			params["HostObjectId"] = _oInfo["siteid"];
			PageContext.params.isChannelSiteView = true;
		}
		if(channelIds.length == 0){
			//alert('cur channel' + ': ' + PageContext["channelid"])
			this.isHostObjectAttribute = true;
			params["ObjectIds"] = null;
			params["ObjectType"] = params["HostObjectType"];
			params["ObjectId"] = params["HostObjectId"];
		}else if(channelIds.length == 1){
			//alert('channel sel')
			this.isHostObjectAttribute = false;
			params["ObjectIds"] = null;
			params["ObjectId"] = channelIds[0];
			//params['ObjectType'] = 'channelMaster';
			params['ObjectType'] = 'channel';
			this.ObjectRight = _oInfo['rights'][0];
		}else{
			//alert('channels sel')
			this.isHostObjectAttribute = false;
			params["ObjectIds"] = channelIds;
			params['ObjectType'] = 'channels';
			this.ObjectRight = mergeRights(_oInfo['rights']);
		}
		//alert($toQueryStr(_oInfo) + '\n\n' + $toQueryStr(params))
		Object.extend(PageContext, PageContext.params);
		Object.extend(PageContext, params);
		return params;
	},
	saveSuccess : function(){
		//TODO
	},
	doAfterInitialize : function(_params){
		//alert($toQueryStr(PageContext.params));
		var chnlType = PageContext.params['chnltype'];
		chnlType = parseInt(chnlType);
		if(isNaN(chnlType) || chnlType < 0) {
			try{
				var nodeInfo = $nav().getFocusedNodeInfo();
				chnlType = parseInt(nodeInfo['chnltype']);
			}catch(err){
				return;
			}
		}
		if(chnlType == null || isNaN(chnlType) || chnlType < 0) {
			return;
		}
		//else 
		this.OpersCanNotDo = this.OpersCanNotDo || {};
		if(chnlType != 0) {
			//头条/图片/链接栏目没有”同步模板到子栏目“操作
			var noChnlOpts = {
				synch: true	
			};
			//链接栏目没有”管理评论“操作
			if(chnlType == 11) {
				Object.extend(noChnlOpts, {
					commentmgr: true	
				});
			}		
			//filterOperator
			this.OpersCanNotDo['channelMaster'] = this.OpersCanNotDo['channel'] = noChnlOpts;
		}else{
			this.OpersCanNotDo['channelMaster'] = this.OpersCanNotDo['channel'] = {};
		}
	},
	call : function(){
		try{
			var oPostData = {
					SelectFields : '', 
					ObjectId: this.params.ObjectId,
					ObjectType: 101,
					fieldsToHtml: 'chnldesc,chnlname,parent.name'
				};
//			if(this.isHostObjectAttribute){
				Object.extend(oPostData,{"ContainsRight":true});
//			}
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var aCombine = [];
			aCombine.push(oHelper.combine('wcm6_channel','findbyid'
				,oPostData));
			aCombine.push(oHelper.combine('wcm6_publish','getChannelPublishConfig'
				,oPostData));
			var fOnSuccess = this.getOnSuccess();
			oHelper.multiCall(aCombine, function(_oXmlTrans, _json){
				(fOnSuccess)(_oXmlTrans, _json);
				//alert($toQueryStr(this.params));
				var type = $F('hdChnlType');
				//ge gfc modify @ 2008-1-15 兼容表单栏目类型(类型值：13)
				//if(type != 0) {
				if(type != 0 && type != 13) {
					//HostOperatorPanel.hide();
					Element.hide('divPublishAttrs');
					Element.hide('divPubUrl');
				}
				if(type == 11) {
					Element.show('divLinkUrl');
				}
				//链接栏目，在栏目-文档列表视图下，需要隐藏第一块面板
				if((type == 11)
					&& this.params['HostType'] == 'documentInChannel') {
					HostOperatorPanel.hide();
				}
			}.bind(this));

			delete oPostData;	
		}catch (ex){
			//TODO logger
			//alert(ex.description);
		}
	},
	mapType : function(_sRawType){
		return _sRawType == 'channelMaster' ? 'channel' : _sRawType;
	},
	
	updateAttr : function(){
		var eEditItem = arguments[1];
		var _sType = eEditItem.getAttribute('__type');
		var oPostData = this.lastPostData;
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		if(_sType == 'publish') {
			if(oPostData['datapath']){//判断存放路径是否已经存在
				var siteId = 0;
				var parentId = 0;
				if(PageContext.params.isChannelSiteView){
					siteId = PageContext["HostObjectId"];
				}else{
					parentId = PageContext["HostObjectId"];
				}					
				oHelper.call('wcm6_channel', 'existsSimilarDataPath', Object.extend({
					siteid: siteId,
					parentid: parentId
				}, oPostData), true, function(_trans, _json){
					var result = _trans.responseText;
					if(result != null && result != ''){
						$fail('存放位置与栏目[' + result + ']重复，请重新输入！', function(){
							$dialog().hide();
							//TODO focus到存放位置	
						});					
					}else{
						var oHelper = new com.trs.web2frame.BasicDataHelper();
						oHelper.call('wcm6_publish', 'saveChannelPublishConfig', oPostData, true);											
					}
				}); 
			}else{ // datapath以外的其他发布属性
				oHelper.call('wcm6_publish', 'saveChannelPublishConfig', oPostData, true);
			}
		}else{
			oHelper.call('wcm6_channel', 'save', oPostData, true, function(){
				var oParams = {
					fieldName: eEditItem.getAttribute('_fieldName'), 
					fieldValue: eEditItem.getAttribute('title')
				};
				
				if(this.isHostObjectAttribute) {
					oParams.isChnlHost = true;
				}else{
					oParams.id = oPostData.objectid;
				}
				var treeWindow = $nav();
				var mainWindow = $main();
				treeWindow.doAfterModifyChannel(oPostData.objectid, true, function(){
					if(mainWindow.Channels && mainWindow.Channels.updateChannels){
						$MessageCenter.sendMessage('main', 'Channels.updateContent', 'Channels', oParams);
					}else{
						treeWindow.refreshMain();
					}
				});
			}.bind(this));
		}

		delete eEditItem;
	},
	callBack : function(){
		var arEditTemplates = document.getElementsByName('channel_editTemplate');
		var arSelectTemplates = document.getElementsByName('channel_selectTemplate');
		for (var i = 0; i < arEditTemplates.length; i++){
			var editRightValue = arEditTemplates[i].getAttribute("rightValue");
			var editRightIndex = arEditTemplates[i].getAttribute("rightIndex");
			if(!checkRight(editRightValue, editRightIndex)){
				var templateId = arEditTemplates[i].getAttribute("templateId");
				var newTemplateRightIndex = 21;//新建模板的权限
				var editTargetRightIndex = 13;//修改栏目的权限
				if(templateId != "0" || !checkRight(this.ObjectRight, newTemplateRightIndex)
						|| !checkRight(this.ObjectRight, editTargetRightIndex)){
					arEditTemplates[i].removeAttribute("href");
					arEditTemplates[i].onclick = null;
				}
			}
		}
		for (var i = 0; i < arSelectTemplates.length; i++){
			var selectRightIndex = arSelectTemplates[i].getAttribute("rightIndex");
			if(!checkRight(this.ObjectRight, selectRightIndex)){			
				arSelectTemplates[i].style.display = "none";
			}
		}
		//pub url
		var ePubUrl = $('divPubUrl');
		var pubRightIndex = ePubUrl.getAttribute('rightIndex', 2);
		if(!checkRight(this.ObjectRight, pubRightIndex)){
			Element.hide(ePubUrl);
		}
	}	
});

//websiteroot master define
com.trs.wcm.WebSiteRootMaster = Class.create("wcm.WebSiteRootMaster");
Object.extend(com.trs.wcm.WebSiteRootMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.WebSiteRootMaster.prototype,{
	arrangeParams : function(_oInfo){
		//TODO logger
		return {};
		//alert("怎么会调到这里来了");
		var params = {};
		var objectids = _oInfo["objectids"];
		if(_oInfo["SiteType"]){
			params["HostObjectType"] = 'WebSiteRoot';
			params["HostType"] = 'WebSiteRoot';
			params["HostObjectId"] = _oInfo["SiteType"];
		}
		if(objectids.length==0){
			this.isHostObjectAttribute = true;
			params["ObjectIds"] = null;
			params["ObjectType"] = params["HostObjectType"];
			params["ObjectId"] = params["HostObjectId"];
		}
		return params;
	},
	call : function(){
		var sServiceId = CONST.ServiceId[this.params["ObjectType"]];
		var sMethodName = 'findSiteTypeDesc';
		var oPost = Object.extend(this._getBasic4Get(),{"ObjectId":this.params["ObjectId"]});
		if(this.isHostObjectAttribute){
			Object.extend(oPost,{"ContainsRight":true});
		}
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.call(sServiceId,sMethodName,oPost,true,(this.getOnSuccess()));
	}
});

//website master define
com.trs.wcm.WebsiteMaster = Class.create("wcm.WebsiteMaster");
Object.extend(com.trs.wcm.WebsiteMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.WebsiteMaster.prototype,{
	onMore : function(){
		var sRight = this.ObjectRight;
		var sId = this.params["ObjectId"];
		if(isAccessable4WcmObject(sRight, 1)){
			$webSiteMgr['edit'](sId, {view:false});
		}else{
			$webSiteMgr['edit'](sId, {view:true});
		}
	},
	arrangeParams : function(_oInfo){
		var params = {};
		var objectids = _oInfo["objectids"];
		if(_oInfo["SiteType"]){
			params["HostObjectType"] = 'WebSiteRoot';
			params["HostType"] = 'WebSiteRoot';
			params["HostObjectId"] = _oInfo["SiteType"];
		}
		{//filterOperator
			this.OpersCanNotDo = this.OpersCanNotDo || {};
			this.OpersCanNotDo["WebSiteRoot"] = {
					'confphotolib':true,

					'confvideolib':true
			};
			if(params["HostObjectId"] == 0){
				this.OpersCanNotDo["WebSiteRoot"] = {
						'confphotolib':true,
						'confvideolib':true
/*						'quicknew':false,
						'import' : false		*/		
				};
			}else if(params["HostObjectId"] == 1){
				this.OpersCanNotDo["WebSiteRoot"] = {
						'confphotolib':false,
						'confvideolib':true,
						'quicknew':true/*,
						'import' : true			*/	
				};		
			}else if(params["HostObjectId"] == 2){
				this.OpersCanNotDo["WebSiteRoot"] = {
						'confphotolib':true,
						'confvideolib':false,
						'quicknew':true
/*						'import' : true			*/	
				};				
			}else if(params["HostObjectId"] == 4){
				this.OpersCanNotDo["WebSiteRoot"] = {
						'confphotolib':true,
						'confvideolib':true,
						'quicknew':true
/*						'import' : true			*/	
				};				
			}	
		}
		if(objectids.length==0){
			this.isHostObjectAttribute = true;
			params["ObjectIds"] = null;
			params["ObjectType"] = params["HostObjectType"];
			params["ObjectId"] = params["HostObjectId"];
		}
		else if(objectids.length==1){
			this.isHostObjectAttribute = false;
			params["ObjectIds"] = null;
			params["ObjectId"] = objectids[0];
			params['ObjectType'] = 'website';
			this.ObjectRight = _oInfo['objectrights'][0];
		}
		else{
			this.isHostObjectAttribute = false;
			params["ObjectIds"] = objectids;
			params['ObjectType'] = 'websites';
			this.ObjectRight = mergeRights(_oInfo['objectrights']);
		}
		return params;
	},
	updateAttr : function(_oPostData,_eEditItem){
		var oPostData = this.lastPostData;
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		if(oPostData['sitename'] || oPostData['sitedesc']){
			oHelper.call('wcm6_website','save', oPostData, true, function(){
				var treeWindow = $nav();
				var mainWindow = $main();
				if(mainWindow.WebSites && mainWindow.WebSites.update){
					treeWindow.refreshSiteType(PageContext.params["SiteType"], null, function(){
						$MessageCenter.sendMessage('main', 'WebSites.updateWebSite', 'WebSites', oPostData);	
					});
				}else{
					treeWindow.doAfterModifySite(PageContext.params["siteid"], false, function(){
						treeWindow.refreshMain();
					});
				}
			});
		}else{ 
			if(oPostData['datapath']){//判断存放路径是否已经存在
				oHelper.call('wcm6_website', 'existsSimilarDataPath', oPostData, true, function(transport, json){
					var _sDesc = transport.responseText.trim();
					if(_sDesc == ''){
					//if(com.trs.util.JSON.value(json, "result") == 'false'){
						var oHelper = new com.trs.web2frame.BasicDataHelper();
						oHelper.call('wcm6_publish',"saveSitePublishConfig", oPostData, true);
					}else{
						$alert("存放位置与站点[" + _sDesc + "]重复。", function(){
							$dialog().hide();
							setTimeout(function(){
								UIEditPanel.setValue(_eEditItem, _eEditItem.getAttribute("value"));
								UIEditPanel.edit(_eEditItem);
							}, 100);
						});
					}
				});   
			}else{
				oHelper.call('wcm6_publish',"saveSitePublishConfig", oPostData, true);
			}
		}
	},
	saveFailure : function(transport){
		//TODO
		$alert(transport.responseText);
	},
	call : function(){
		var oPost = Object.extend(this._getBasic4Get(), {"ObjectId":this.params["ObjectId"]});
//		if(this.isHostObjectAttribute){			
			Object.extend(oPost,{"ContainsRight":true});
//		}
		Object.extend(oPost, {"SelectFields":"",fieldsToHtml: 'sitedesc,sitename'});
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		aCombine.push(oHelper.combine('wcm6_website', 'findbyid', oPost));
		aCombine.push(oHelper.combine('wcm6_publish', 'getSitePublishConfig', oPost));
		oHelper.multiCall(aCombine, (this.getOnSuccess()));			
	},
	updateSiteDesc : function(_oPostData){
		$("SiteDesc").innerText = _oPostData["SiteDesc"];
	},
	callBack : function(){
		var website_editTemplates = document.getElementsByName('website_editTemplate');
		var website_selectTemplates = document.getElementsByName('website_selectTemplate');
		for (var i = 0; i < website_editTemplates.length; i++){
			var editRightValue = website_editTemplates[i].getAttribute("rightValue");
			var editRightIndex = website_editTemplates[i].getAttribute("rightIndex");
			if(!checkRight(editRightValue, editRightIndex)){
				var templateId = website_editTemplates[i].getAttribute("templateId");
				var newTemplateRightIndex = 21;//新建模板的权限
				var editTargetRightIndex = 1;//修改站点的权限
				if(templateId != "0" || !checkRight(this.ObjectRight, newTemplateRightIndex)
						|| !checkRight(this.ObjectRight, editTargetRightIndex)){
					website_editTemplates[i].removeAttribute("href");
					website_editTemplates[i].onclick = null;
				}
			}
		}
		for (var i = 0; i < website_selectTemplates.length; i++){
			var selectRightIndex = website_selectTemplates[i].getAttribute("rightIndex");
			if(!checkRight(this.ObjectRight, selectRightIndex)){			
				website_selectTemplates[i].style.display = "none";
			}
		}
	}
});

//extendfield master define
com.trs.wcm.ExtendfieldMaster = Class.create("wcm.ExtendfieldMaster");
Object.extend(com.trs.wcm.ExtendfieldMaster.prototype,com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ExtendfieldMaster.prototype,{	
	getBasic4Save : function(){
		var oPostData = {};
		var hostType = this.params["HostObjectType"];
		var hostId = this.params["HostObjectId"];
		
		oPostData['HostId'] = hostId;		
		switch(hostType){
			case "website" : {
				oPostData['HostType'] = 103;
				break;
			}
			case "channel" : {
				oPostData['HostType'] = 101;
				break;
			}
			default : {
				oPostData['HostType'] = 1;
				break;
			}
		}		 
		
		return oPostData;
	},
	arrangeParams : function(_oInfo){
		/**
		 * put param name and value into params
		 * names: ObjectType,ObjectId,ObjectIds,HostType,HostObjectId,ObjectRight
		 * set this.isHostObjectAttribute = true if none selected objects, else set false
		 */
		var params = {};
		var objectids = _oInfo["ObjectIds"];
		if(_oInfo["channelid"]){
			params["HostObjectType"] = 'channel';
			params["HostType"] = 'extendFieldInChannel';
			params["HostObjectId"] = _oInfo["channelid"];
		}
		else if(_oInfo["siteid"]){
			params["HostObjectType"] = 'website';
			params["HostType"] = 'extendFieldInSite';
			params["HostObjectId"] = _oInfo["siteid"];
		}
		else if(_oInfo["sitetype"]){
			params["HostObjectType"] = 'WebSiteRoot';
			params["HostType"] = 'extendFieldInSys';
			params["HostObjectId"] = _oInfo["sitetype"];
		}
		if(objectids.length==0){
			this.isHostObjectAttribute = true;
			params["ObjectIds"] = null;
			params["ObjectType"] = params["HostObjectType"];			
			params["ObjectId"] = params["HostObjectId"];
		}
		else if(objectids.length==1){
			this.isHostObjectAttribute = false;
			params["ObjectIds"] = null;
			params["ObjectId"] = objectids[0];
			params['ObjectType'] = 'extendfield';
			this.ObjectRight = _oInfo['ObjectRights'][0];
		}
		else{
			this.isHostObjectAttribute = false;
			params["ObjectIds"] = objectids;
			params['ObjectType'] = 'extendfields';
			this.ObjectRight = mergeRights(_oInfo['ObjectRights']);
		}
		return params;
	},
	saveSuccess : function(){		
		$MessageCenter.sendMessage('main','PageContext.updateCurrRows',null,[]);
	}
});
var OperatorsExtend = {
	_fireEvent : function(_sObject,_sType){
		var oInfo = OperAttrPanel.origParams;
		var sObjectType = PageContext.params['type'].toLowerCase();
		var sType = ("" + sObjectType.charAt(0)).toUpperCase() + sObjectType.substring(1);
		eval("var oScope = com.trs.wcm." + sType + "Master;");
		if(!oScope){//fix lowercase or uppercase.
			var _sObjectType_ = PageContext.params['type'];
			var _sType_ = ("" + _sObjectType_.charAt(0)).toUpperCase() + _sObjectType_.substring(1);
			eval("var oScope = com.trs.wcm." + _sType_  + "Master;");
		}
		//alert("var oScope = com.trs.wcm." + sType + "Master;")
		if(oScope&&oScope.prototype.exec){
			oScope.prototype.exec.apply(OperAttrPanel,[_sObject,_sType,OperAttrPanel.params,OperAttrPanel.origParams]);
			return;
		}				
		var iObjectIds = OperAttrPanel.params["ObjectIds"]||OperAttrPanel.params["ObjectId"];
		switch(sObjectType){
			case "document":
				var hostType = OperAttrPanel.params["HostObjectType"];
				var hostId = OperAttrPanel.params["HostObjectId"];
				var hostParams = {};
				if(hostType=="website"){
					hostParams['siteid'] = hostId;
				}
				else{
					hostParams[hostType+'id'] = hostId;
				}
				if(_sObject=="document"||_sObject=="documents"){
					$documentMgr[_sType.toLowerCase()](iObjectIds,hostParams);
				}
				else if(_sObject=="documentInChannel"){
					$documentMgr[_sType.toLowerCase()](0,hostParams);
				}
				else if(_sObject=="documentInSite"){
					var iSiteid = OperAttrPanel.params["HostObjectId"];
					$documentMgr[_sType.toLowerCase()](0,hostParams);
				}
				else if(_sObject=="channel"){
					$channelMgr[_sType.toLowerCase()](iObjectIds);
				}
				else if(_sObject=="website"){
					$webSiteMgr[_sType.toLowerCase()](iObjectIds);
				}
				break;
			case 'template':
				//获取足够的参数?
				var oParams = {};
				Object.extend(oParams, {
					hostPanelType: _sObject, 
					hostObjectType: OperAttrPanel.params["HostObjectType"], 
					hostObjectId: OperAttrPanel.params["HostObjectId"],
					objectIds: iObjectIds,
					containsChildren: PageContext.params.containsChildren || false,
					isSolo: _sObject == 'template',
					num: oInfo.num || 0
				});
				if(_sObject == 'website'){
					$webSiteMgr[_sType.toLowerCase()](iObjectIds, oParams);
				}else if(_sObject == 'channel' || _sObject == 'channelMaster'){
					$channelMgr[_sType.toLowerCase()](iObjectIds, oParams);
				}else{
					$templateMgr[_sType.toLowerCase()](iObjectIds, oParams);
				}
				break;
			case 'channel':
				//获取足够的参数?
				var oParams = {};
				Object.extend(oParams, {
					hostPanelType: _sObject, 
					hostObjectType: OperAttrPanel.params["HostObjectType"], 
					hostObjectId: OperAttrPanel.params["HostObjectId"],
					objectIds: iObjectIds
				});
				if(_sObject == 'website'){
					$webSiteMgr[_sType.toLowerCase()](iObjectIds, oParams);
				}else{
					$channelMgr[_sType.toLowerCase()](iObjectIds, oParams);
				}
				break;
			case 'website':
				$webSiteMgr[_sType.toLowerCase()](iObjectIds);
				break;
			case 'extendfield':
				var oParams = {};
				Object.extend(oParams, {
					hostPanelType: _sObject, 
					hostObjectType: OperAttrPanel.params["HostObjectType"], 
					hostObjectId: OperAttrPanel.params["HostObjectId"],
					objectIds: iObjectIds
				});
				if(_sObject == 'website'){
					$webSiteMgr[_sType.toLowerCase()](iObjectIds, oParams);
				}else if(_sObject == 'channel'){
					$channelMgr[_sType.toLowerCase()](iObjectIds, oParams);
				}else {
					$ExtendFieldMgr.setParams(PageContext.params);
					$ExtendFieldMgr[_sType.toLowerCase()](iObjectIds);
				}
				break;
		}
	}
}
var AttributeHelper = {
	GetTemplate : function(_sObjectType){
		var url = '';
		var eTemplate = null;
		try{
			//var sMappingObjectType = PageContext.params["type"];
			var sMappingObjectType = OperAttrPanel.params["ObjectType"];
			if(!OperAttrPanel.Path[sMappingObjectType]){
				sMappingObjectType = PageContext.params["type"];
			}
			var base = OperAttrPanel.Path[sMappingObjectType]["templater"];
			url = base + sMappingObjectType+'.html';
			var r = new Ajax.Request(url, {
				asynchronous:false,
				method:'GET'
			});
			if(r.responseIsSuccess()){
				new Insertion.Bottom($(_ATTR_PANEL_['ID_ALL_TEMPLATES']),r.transport.responseText);
				eTemplate = $(_ATTR_PANEL_['ID_ATTR_TEMPLATE_PRE']+_sObjectType);
				if(!eTemplate){
					alert('模板页面"'+url+'"中不存在控件[ID="'+_ATTR_PANEL_['ID_ATTR_TEMPLATE_PRE']+_sObjectType+'"].');
					return null;
				}
			}
			else if(r.transport.status==404){
				alert('模板页面"'+url+'"不存在.');
				return null;
			}
			else{
				alert('向模板页面"'+url+'"发送请求时出现未知错误,status='+r.transport.status);
				return null;
			}
		}catch(err){
			alert('指定"'+_sObjectType+'"类型的属性模板"'
				+_ATTR_PANEL_['ID_ATTR_TEMPLATE_PRE']
				+_sObjectType+'"时出错:\n'+'模板页面"'+url+'"不存在.');
			return null;
		}
		return eTemplate;
	}
}
var OperatorListener = {
	mousemove : function(element){
		if(this.LastHoverId){
			var elLastHover = $(this.LastHoverId);
			Element.removeClassName(elLastHover,_OPER_PANEL_['CSS_OPER_ROW_OUTER_ACTIVE']);
		}
		Element.addClassName(element,_OPER_PANEL_['CSS_OPER_ROW_OUTER_ACTIVE']);
		this.LastHoverId = element.id;
	},
	mouseout : function(element){
		if(this.LastHoverId){
			var elLastHover = $(this.LastHoverId);
			Element.removeClassName(elLastHover,_OPER_PANEL_['CSS_OPER_ROW_OUTER_ACTIVE']);
		}
		delete this.LastHoverId;
	},
	fireEvent : function(element){
		var sKey = element.getAttribute(_OPER_PANEL_['ATT_OPER_KEY']);
		var sObject = element.getAttribute(_OPER_PANEL_['ATT_OPER_OBJECT']);
		OperatorsExtend._fireEvent(sObject,sKey);
	},
	moreclick : function(element, event){
		if(element.bubbleMore){
			var x = Event.pointerX(event)+4;
			var y = Event.pointerY(event)+4;
			element.bubbleMore.bubble([x,y],function(_Point){
				return [_Point[0]-this.offsetWidth,_Point[1]];
			});
		}
	},
	moreattrclick : function(element, event){
		if(element.onMore){
			element.onMore(event);
		}
	}
};
var HostOperatorPanel,ObjectOperatorPanel,ObjectAttributePanel;
Event.observe(window,'load',function(){
	HostOperatorPanel = new com.trs.wcm.OperatorPanel('host_operators');
	ObjectOperatorPanel = new com.trs.wcm.OperatorPanel('object_operators');
	ObjectAttributePanel = new com.trs.wcm.AttributePanel('object_attribute');

//	Object.extend(HostOperatorPanel,OperatorsExtend);
//	Object.extend(ObjectOperatorPanel,OperatorsExtend);

	Event.dispatch(document,['mousemove','mouseout','click'],null,OperatorListener);
	PageContext.windowLoaded = true;
	PageContext.notify();
});
Event.observe(window,'unload',function(){
	HostOperatorPanel.destroy();
	ObjectOperatorPanel.destroy();
	ObjectAttributePanel.destroy();
	OperAttrPanel.destroy();
	OperAttrPanel = HostOperatorPanel = ObjectOperatorPanel = ObjectAttributePanel = null;
});
//$log().setLevel(-1);
Object.extend(UIEditPanel,{
	directFireSecondClick : true,
	sOnUpdate : 'PageContext.updateAttribute(this.$postdata, this);'
});
Object.extend(UISelect,{
	sOnChange : 'PageContext.updateAttribute(this.$postdata, this);'
});
Object.extend(UIFilterSelect,{
	onChange : function(){
		PageContext.updateAttribute(this.$postdata, this);
	}
});
Object.extend(PageContext, {
	editTemplate : function(_nTempId, _nTempType, _nHostId, _nHostType){
		_nTempId = parseInt(_nTempId);
		var oPageParams = {
			temptype: _nTempType,
			hostid: _nHostId, 
			hosttype: _nHostType,
			isTypeStub: true
		};
		$templateMgr.edit(_nTempId, oPageParams, function(_nSavedTempId){
			if(_nTempId > 0) {
				PageContext.__doAfterEditTemplate();
				return;
			}
			//else 
			var oPostData = {
				objectid: _nHostId, 
				objecttype: _nHostType,
				templateid: _nSavedTempId,
				templatetype: _nTempType
			};
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.call('wcm6_template', 'setDefaultTemplate', oPostData, true
				, PageContext.__doAfterEditTemplate);	
		}); 
	},
	__doAfterEditTemplate : function(){
		try{
			PageContext.reload();
			if($main().location.href.indexOf('template_index.html') != -1) {
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
			}
		}catch(err){
			//just skip it
		}
	},
	selectTemplate : function(_nRawTempId, _nTempType, _nEmployerId, _nEmployerType){
		var oPageParams = {
			rawTempId: _nRawTempId,
			tempType: _nTempType,
			employerId: _nEmployerId,
			employerType: _nEmployerType
		};
		$templateMgr.selectTemplate(oPageParams, PageContext.reload); 
	}
});