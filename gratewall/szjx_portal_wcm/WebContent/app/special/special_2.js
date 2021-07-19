
var Channels = {
	servicesName : 'wcm61_channel',
	loadMethodName : 'createChannelTreeHTMLOfIds',
	findMethodName : 'findById',
	saveMethodName : 'saveForSpecial',
	changeOrderMethodName : 'changeOrder',
	deleteMethodName : 'delete'
};

function keyDownEvent(event){
	event = window.event || event;
	if(event.keyCode == Event.KEY_RETURN){
		saveSpecial(Event.element(event), event);	
	}
}

function saveSpecial(sInputElement, event){
	event = window.event || event;
	sInputElement = $(sInputElement);
	if(sInputElement.getAttribute("_value") == sInputElement.value){
		return;
	}
	var sObjId =  sInputElement.getAttribute("id").substring(5);
	if(!$("node_" + sObjId)){
		if(com.trs.tree.TreeNav.oPreSrcElementA){
			sObjId = com.trs.tree.TreeNav.oPreSrcElementA.parentNode.getAttribute("id").substring(5) || 0;
		}
		if(!$("node_" + sObjId)){
			return;
		}
	}

	if(window.isInValidKeyDownFire && event.type == 'blur') {
		window.isInValidKeyDownFire = null;
		return;
	}

	//校验长度的合法性
	var nMaxLength = sInputElement.getAttribute("maxlen");
	if(nMaxLength){
		if(sInputElement.value.byteLength() > parseInt(nMaxLength, 10)){
			try{
				if(event.type == 'keydown'){
					window.isInValidKeyDownFire = true;
				}
				var desc = sInputElement.getAttribute("desc");
				alert(String.format("{0}长度过长，最大长度为{1}.",desc,nMaxLength));
				setTimeout(function(){
					sInputElement.focus();
				}, 10);
			}catch(error){
			}
			return;
		}
	}
	var oParams = {objectId : sObjId};
	var sValue = sInputElement.value;
	oParams[sInputElement.name] = sValue;
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call(Channels["servicesName"], Channels["saveMethodName"], oParams, true, function(){
	});
}

function changeClassInfoOrder(oCurrDivNode, sDeriction){
	var srcObjectId = oCurrDivNode.getAttribute("id").substring(5);
	var dstObjectId = 0;
	var sMethodName = '_getPreviousElement';//sDeriction == 'up' ? '_getPreviousElement' : '_getNextElement';
	var oDestDivNode = $TreeNav[sMethodName](oCurrDivNode, "DIV");
	if(oDestDivNode){
		dstObjectId = oDestDivNode.getAttribute("id").substring(5);
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call(Channels["servicesName"], Channels["changeOrderMethodName"], {
		SrcChannelId : srcObjectId,
		DstChannelId : dstObjectId
	}, true);
}

com.trs.tree.TreeNav.doActionOnClickA = function(_event,_elElementA){
	Event.stop(_event || window.event);
	return false;
}

com.trs.tree.TreeNav.methodType = 'get';
com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(divElement){
	var urlPrefix = '/'+(location.pathname.split('/')[1]||'wcm')+'/center.do?serviceid=' + Channels.servicesName + '&methodname='; 
	var params = "&ChannelId=" + divElement.getAttribute("objectId");
	return urlPrefix + Channels.loadMethodName + params;
}

function getFirstHTMLChild(domNode){
	if(domNode == null) return null;
	for (var i = 0; i < domNode.childNodes.length; i++){
		if(domNode.childNodes[i].nodeType == 1){
			return domNode.childNodes[i];
		}
	}
	return null;
}

com.trs.tree.TreeNav.observe('onload', function(){
	var divElement = getFirstHTMLChild($('TreeView'));
	divElement.setAttribute("objectId", $("hostId").value);
	divElement.getElementsByTagName("a")[0].innerText = getParameter("objectName") || "";
	divElement.fireEvent("onclick");
	com.trs.tree.TreeNav.focus(divElement);

	setTimeout(function(){
		var oParams = {objectId : $("hostId").value};
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(Channels["servicesName"], Channels["findMethodName"], oParams, true, function(transport,json){
			var nObjectId = $("hostId").value;
			var sCName = $v(json, "Channel.CHNLDESC") || "" ;
			var divElement = getFirstHTMLChild($('TreeView'));
			divElement.setAttribute("id", "node_" + nObjectId);
			divElement.setAttribute("title", "ID:" + nObjectId);
			var sText = sCName;
			if($("specialName")){
				sText = $("specialName").value
			}
			divElement.getElementsByTagName("a")[0].innerText = sText;

			//用于栏目的新建修改页面中，栏目的定位
			if(m_nObjectId != 0 && m_nObjectId != m_nParentId){
				com.trs.tree.TreeNav.focus($('node_'+m_nObjectId));
			}else{
				com.trs.tree.TreeNav.focus(divElement);
			}
		});
	}, 10);
});
var showOperator0 = com.trs.tree.TreeManager.prototype.showOperator;
var _insertSuccessCallBack0 = com.trs.tree.TreeManager.prototype._insertSuccessCallBack;
Object.extend(com.trs.tree.TreeManager.prototype, {
	alert : function(msg, _fCallback){
		var dlg = wcm.MessageBox.getDlg();
		fCallback = function(){
			dlg.un('beforehide', fCallback);
			if(_fCallback) _fCallback();
		};
		dlg.on('beforehide', fCallback);
		Ext.Msg.alert(msg);
	},
	insertBefore : function(aElement){
		if(this.waittingServerResp){
			return false;
		}
		this.waittingServerResp = true;
		button_disable();
		return true;
	},
	beforeModify : function(aElement){
		button_disable();
		return true;
	},
	onPositionChange : function(oCurrDivNode, sDeriction){
		if(!window.oDelayedTask){
			window.oDelayedTask = new com.trs.DelayedTask();
		}
		oDelayedTask.delay(200, changeClassInfoOrder, null, [oCurrDivNode, sDeriction]);
	},
	_insertSuccessCallBack : function(divElement, response, json, resultJson){
		this.waittingServerResp = false;
		_insertSuccessCallBack0.apply(this, arguments);
		var objectId = $v(json, "RESULT");
		divElement.setAttribute("title", "ID:" + objectId);
		divElement.setAttribute("id", "node_" + objectId);

		var objId = divElement.getAttribute(this.params["identityAttribute"]);
		var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CHANNEL};
		var parentElement = com.trs.tree.TreeNav._getParentElement(divElement);
		var parentId = parentElement.getAttribute(this.params["identityAttribute"]);
		var context = {objId : parentId, objType : WCMConstants.OBJ_TYPE_CHANNEL};

		//在栏目导航资源块上新增或修改栏目时，需要执行回调函数，刷新资源块
		if(getParameter("bDataOper4Chnl")){
			var c_bWin = wcm.CrashBoarder.get(window);
			c_bWin.notify();
		}

		var oPostData = {ObjectId:objectId,ObjectType:101,ParentId:parentId,DATAPATH:objectId};
		BasicDataHelper.call("wcm6_publish","savePublishConfig",oPostData,true,function(_transport,_json){
			button_enable();
		});
	},
	_modifySuccessCallBack : function(divElement, response, json, resultJson){
		var objId = divElement.getAttribute(this.params["identityAttribute"]);
		var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};

		//在栏目导航资源块上新增或修改栏目时，需要执行回调函数，刷新资源块
		if(getParameter("bDataOper4Chnl")){
			var c_bWin = wcm.CrashBoarder.get(window);
			c_bWin.notify();
		}
		button_enable();
	},
	_deleteSuccessCallBack : function(divElement, response, json, resultJson){
		var objId = divElement.getAttribute(this.params["identityAttribute"]);
		var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
	},
	showOperator : function(element){
		showOperator0.apply(this, arguments);
		var operatorContainer = $(this.params["operatorContainerId"]);
		if(!operatorContainer) return;
		if(operatorContainer.style.display == ''){
			var left = parseInt(operatorContainer.style.left, 10);
			var width = parseInt($('TreeView').offsetWidth, 10);
			if(left >= width - 20){
				var realLeft = left - parseInt(operatorContainer.offsetWidth, 10) - 40;
				if(realLeft < 0) realLeft = 10;
				operatorContainer.style.left = realLeft;
			}
		}
	},
	_initParams : function(){
		this.setParams({
			servicesName : Channels.servicesName,
			identityAttribute : 'objectId',
			addURL : Channels.saveMethodName,
			deleteURL : Channels.deleteMethodName,
			modifyURL : Channels.saveMethodName
		});	
	},
	inputBlurEvent : function(oInputElement, sURL, fCallBack, sMsg, event){
		// TODO check the valid.
		event = window.event || event;
		if(this.inValidKeyDownFire && event.type == 'blur') {
			delete this.inValidKeyDownFire;
			return;
		}
		var aElement = Element.previous(oInputElement);
		var sOldValue = aElement.innerHTML.unescapeHTML();
		var bErrMsg = this._validInput(oInputElement, sOldValue);
		if(event.type == 'keydown' && bErrMsg){
			this.inValidKeyDownFire = true;
		}
		if(bErrMsg){
			this.alert(bErrMsg, function(){
				setTimeout(function(){
					if(oInputElement.value.length == 0){
						oInputElement.value = sOldValue;
					}
					oInputElement.focus();
					oInputElement.select();		
				}, 10);
			});
			return;
		}
		this.isShowInput = false;
		oInputElement.onkeydown = null;
		oInputElement.onblur = null;
		aElement.style.display = '';
		this.params["isChanged"] = oInputElement.value.escapeHTML() == aElement.innerHTML ? false : true;
		aElement.innerHTML = oInputElement.value.escapeHTML();
		Element.remove(oInputElement);	
		setTimeout(function(){
			try{
				$TreeNav.focus(aElement.parentNode);
			}catch(error){
			}
		}, 10);
		if(!this.params[sURL] || !this.params["isChanged"]){
			button_enable();
			return;
		}
		if(this.params["servicesName"]){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.Call(this.params["servicesName"], this.params[sURL], 
					this._getSaveParams(aElement), true, this[fCallBack].bind(this), 
					this.failureCallBack.bind(this, sMsg));
		}else{
			new Ajax.Request(this.params[sURL], {
				method : 'get',
				parameters : $toQueryStr(this._getSaveParams(aElement)),
				onSuccess : this[fCallBack].bind(this),
				onFailure : this.failureCallBack.bind(this, sMsg)
			});		
		}
	},
	cancelInput : function(oInputElement, sURL, fCallBack, sMsg, event){
		this.isShowInput = false;
		var parentElement = oInputElement.parentNode;
		if(sURL == "modifyURL" || parentElement.getAttribute(this.params["identityAttribute"])){//modify
			var aElement = Element.previous(oInputElement);
			oInputElement.value = aElement.innerHTML.unescapeHTML();
			oInputElement.blur();
		}else{//add
			var tempNode = $TreeNav._getPreviousElement(parentElement, "DIV");
			if(tempNode){//has previous sibling.
				Element.remove(parentElement);
				setTimeout(function(){
					try{
						$TreeNav.focus(tempNode);
					}catch(error){
					}
				}, 10);
			}else{//no previous sibling.
				tempNode = $TreeNav._getParentElement(parentElement);
				if(tempNode){
					if($TreeNav._getNextElement(parentElement)){//has next sibling.
						Element.remove(parentElement);
						setTimeout(function(){
							try{
								$TreeNav.focus(tempNode);
							}catch(error){
							}
						}, 10);
					}else{//no sibling.
						Element.remove(parentElement.parentNode);
						$TreeNav.reloadNodeStyle(tempNode);					
						setTimeout(function(){
							try{
								$TreeNav.focus(tempNode);
							}catch(error){
							}
						}, 10);
					}
				}
			}
			button_enable();
		}
	},
	_validInput : function(oInputElement, sOldValue){
		var msg = '';
		if(oInputElement.value.trim() == ''){
			msg = "不能为空！";
		}else if(oInputElement.value.byteLength() > 50){
			msg = "长度大于最大长度50！";
		}
		return msg;
	},
	getRequestResult : function(response, json){
		/*
		 *期望返回的内容
			{
				objectId : '',
				isSuccess : '',
				detailMsg : ''
			};
		*/
		return {
			OBJECTID : com.trs.util.JSON.value(json, "CLASSINFO.CLASSINFOID") || 0,
			ISSUCCESS : "true"
		};
	},
	_getSaveParams : function(aElement){
		var parentElement = aElement.parentNode;
		var nChnlOrder = 0;
		if(Element.previous(parentElement)){
			nChnlOrder = Element.previous(parentElement).getAttribute("chnlOrder");
		}
		parentElement.setAttribute("chnlOrder", nChnlOrder);
		var _parentElement = $TreeNav._getParentElement(parentElement);
		var parentId = _parentElement ? _parentElement.getAttribute("id").substring(5) : 0;
		var name = aElement.innerHTML.unescapeHTML();
		var nObjectId = 0;
		if(parentElement.getAttribute("id") != null && parentElement.getAttribute("id").substring(5) != ""){
			nObjectId = parentElement.getAttribute("id").substring(5);
		}
		return {
			objectId	: nObjectId,
			parentId	: parentId,
			CHNLNAME	: name,
			CHNLDESC	: name,
			CANPUB		: 1,
			SPECIALID	: getParameter("objectId"),
			bDataOper4Chnl : getParameter("bDataOper4Chnl") || false,
			ChnlOrder : nChnlOrder
		};
	},
	_getDeleteParams : function(aElement){
		return {
			ObjectIds	: aElement.parentNode.getAttribute("id").substring(5) || 0,
			drop		: false
		};
	},
	failureCallBack : function(msg, response, json){
		button_enable();
		this.restoreCurrNode();
		this.isLocking = false;
		if(window.DefaultAjax500CallBack) DefaultAjax500CallBack(response, json);
		else alert(msg);
	}
});

function button_enable(){
	if(wcmXCom.get('ParambtnUp')){
		wcmXCom.get('ParambtnUp').enable();
		wcmXCom.get('ParambtnDown').enable();
		wcmXCom.get('ParambtnCancel').enable();
	}
}
function button_disable(){
	if(wcmXCom.get('ParambtnUp')){
		wcmXCom.get('ParambtnUp').disable();
		wcmXCom.get('ParambtnDown').disable();
		wcmXCom.get('ParambtnCancel').disable();	
	}
}