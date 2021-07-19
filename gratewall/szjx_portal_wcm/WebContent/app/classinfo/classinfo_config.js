function myclose(){
	setTimeout(function(){
		FloatPanel.close();
	},100);
	return false;
}
/*
FloatPanel.setAfterClose(function(){
	var isChanged = getParameter("isChanged");
	var divElement = getFirstHTMLChild($('TreeView'));
	if(isChanged || divElement.getElementsByTagName("a")[0].innerHTML != getParameter("objectName")){
		$MessageCenter.sendMessage('main','PageContext.RefreshList',"PageContext",[getParameter("objectId")],true,true);
	}
});//*/

var ClassInfos = {
	servicesName : 'wcm61_classinfo',
	loadMethodName : 'createClassInfoTreeHTML',
	findMethodName : 'findById',
	saveMethodName : 'saveClassInfo',
	changeOrderMethodName : 'changeOrder',
	deleteMethodName : 'deleteClassInfo'
};

function keyDownEvent(event){
	event = window.event || event;
	if(event.keyCode == Event.KEY_RETURN){
		saveClassInfo(Event.element(event), event);	
	}
}

function saveClassInfo(sInputElement, event){
	event = window.event || event;
	sInputElement = $(sInputElement);
	if(sInputElement.getAttribute("_value") == sInputElement.value){
		return;
	}
	var sObjId =  sInputElement.getAttribute("objectId");
	if(!$("node_" + sObjId)){
		if(com.trs.tree.TreeNav.oPreSrcElementA){
			sObjId = com.trs.tree.TreeNav.oPreSrcElementA.parentNode.getAttribute("objectId") || 0;
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
				alert(String.format( "{0}长度过长,最大长度为{1}.",desc,nMaxLength));
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
	oHelper.Call(ClassInfos["servicesName"], ClassInfos["saveMethodName"], oParams, true, function(){
		if(!$("node_" + sObjId)){
			return;
		}
		$("node_" + sObjId).setAttribute(sInputElement.id || sInputElement.name, sValue);

		//更新
		var sCurrObjId = com.trs.tree.TreeNav.oPreSrcElementA.parentNode.getAttribute("objectId") || 0;
		//ajax请求时时间延迟，请求回来当前活动节点可能已变化，如果变化了，就不再设置_value值。
		if(sCurrObjId == sObjId){
			sInputElement.setAttribute("_value", sValue);
		}
		var info = {objId : sObjId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
		CMSObj.createFrom(info, null)['afteredit']();
	});
}

function changeClassInfoOrder(oCurrDivNode, sDeriction){
	var srcObjectId = oCurrDivNode.getAttribute("objectId");
	var dstObjectId = 0;
	var sMethodName = '_getPreviousElement';//sDeriction == 'up' ? '_getPreviousElement' : '_getNextElement';
	var oDestDivNode = $TreeNav[sMethodName](oCurrDivNode, "DIV");
	if(oDestDivNode){
		dstObjectId = oDestDivNode.getAttribute("objectId");
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call(ClassInfos["servicesName"], ClassInfos["changeOrderMethodName"], {
		SrcObjectId : srcObjectId,
		DstObjectId : dstObjectId
	}, true);
}

//由div的内容设置元素的内容
function setElementValue(divElement){
	var sObjId = divElement.getAttribute("objectId") || "0";
	var sCDesc = divElement.getAttribute("cDesc") || "";
	var sCCode = divElement.getAttribute("cCode") || "";

	//alert("描述：" + sCDesc+  "编码：" + sCCode);
	var oCDesc = $('cDesc');
	oCDesc.value = sCDesc;
	oCDesc.setAttribute("_value", sCDesc);
	oCDesc.setAttribute("objectId", sObjId);

	var oCCode = $('cCode');
	oCCode.value = sCCode;
	oCCode.setAttribute("_value", sCCode);
	oCCode.setAttribute("objectId", sObjId);
	
}

com.trs.tree.TreeNav.onActiveNodeChange = function(divElement){
	setElementValue(divElement);
}

com.trs.tree.TreeNav.doActionOnClickA = function(_event,_elElementA){
	Event.stop(_event || window.event);
	if(_elElementA){
		setElementValue(_elElementA.parentNode);
	}
	return false;
}

com.trs.tree.TreeNav.methodType = 'get';
com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(divElement){
	var urlPrefix = '/'+(location.pathname.split('/')[1]||'wcm')+'/center.do?serviceid=' + ClassInfos.servicesName + '&methodname='; 
	var params = "&parentId=" + divElement.getAttribute("objectId");
	return urlPrefix + ClassInfos.loadMethodName + params;
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
	divElement.setAttribute("objectId", getParameter("objectId"));
	divElement.getElementsByTagName("a")[0].innerText = getParameter("objectName");
	//divElement.click();
	divElement.fireEvent("onclick");
	com.trs.tree.TreeNav.focus(divElement);

	setTimeout(function(){
		var oParams = {objectId : getParameter("objectId")};
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(ClassInfos["servicesName"], ClassInfos["findMethodName"], oParams, true, function(transport,json){
			var nObjectId = getParameter("objectId");
			var sCCode = $v(json, "ClassInfo.CCODE") || "" ;
			var sCDesc = $v(json, "ClassInfo.CDESC") || "" ;
			var sCName = $v(json, "ClassInfo.CNAME") || "" ;
			var divElement = getFirstHTMLChild($('TreeView'));
			divElement.setAttribute("id", "node_" + nObjectId);
			divElement.setAttribute("title", "ID:" + nObjectId);
			//divElement.setAttribute("objectId", nObjectId);
			divElement.setAttribute("cCode", sCCode);
			divElement.setAttribute("cDesc", sCDesc);
			divElement.getElementsByTagName("a")[0].innerText = sCName;
			com.trs.tree.TreeNav.focus(divElement);
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
		var oCDesc = $('cDesc');
		if(oCDesc){
			oCDesc.value = "";
			oCDesc.removeAttribute("_value");
			oCDesc.removeAttribute("objectId");
		}
		var oCCode = $('cCode');
		if(oCCode){
			oCCode.value = "";
			oCCode.removeAttribute("_value");
			oCCode.removeAttribute("objectId");
		}
		return true;
	},
	changeRelation : function(oCurrDivNode,detParentDivNode){
		var srcObjectId = oCurrDivNode.getAttribute("objectId");
		var dstParentId = detParentDivNode.getAttribute("objectId");
		var oParams = {
			objectId :srcObjectId || 0,
			parentId : dstParentId,
			PreviousId : -1,
			cName : oCurrDivNode.getElementsByTagName("a")[0].innerText,
			cDesc: oCurrDivNode.getAttribute("cDesc")
		};
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		//重新设置parentId，并插入到最前面
		oHelper.Call(ClassInfos["servicesName"], ClassInfos["saveMethodName"], oParams, true,function(){
			//先获取到顶级父节点，刷新整棵树
			var doms = document.getElementsByClassName("Root");
			var tempElement = null;
			if(doms&&doms.length>0){
				tempElement = doms[0];
			}else{
				tempElement = document.getElementsByClassName("RootOpened")[0];
			}
			com.trs.tree.TreeNav.updateNodeChildrenHTML(tempElement);
			//document.location.reload();
		});
	},
	onPositionChange : function(oCurrDivNode, sDeriction){
		if(!window.oDelayedTask){
			window.oDelayedTask = new com.trs.DelayedTask();
		}
		oDelayedTask.delay(200, changeClassInfoOrder, null, [oCurrDivNode, sDeriction]);
	},
	_insertSuccessCallBack : function(divElement, response, json, resultJson){
		_insertSuccessCallBack0.apply(this, arguments);
		var objectId = $v(json, "classinfo.classinfoid");
		divElement.setAttribute("title", "ID:" + objectId);
		divElement.setAttribute("id", "node_" + objectId);
		var sCDesc = $v(json, "classinfo.cdesc") || "";
		divElement.setAttribute("cDesc", sCDesc);

		var objId = divElement.getAttribute(this.params["identityAttribute"]);
		var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
		var parentElement = com.trs.tree.TreeNav._getParentElement(divElement);
		var parentId = parentElement.getAttribute(this.params["identityAttribute"]);
		var context = {objId : parentId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
		CMSObj.createFrom(info, context)['afteradd']();
	},
	_modifySuccessCallBack : function(divElement, response, json, resultJson){
		var objId = divElement.getAttribute(this.params["identityAttribute"]);
		var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
		CMSObj.createFrom(info, null)['afteredit']();
	},
	_deleteSuccessCallBack : function(divElement, response, json, resultJson){
		var objId = divElement.getAttribute(this.params["identityAttribute"]);
		var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
		CMSObj.createFrom(info, null)['afterdelete']();
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
			servicesName : ClassInfos.servicesName,
			identityAttribute : 'objectId',
			addURL : ClassInfos.saveMethodName,
			deleteURL : ClassInfos.deleteMethodName,
			modifyURL : ClassInfos.saveMethodName
		});	
	},
	_validInput : function(oInputElement, sOldValue){
		var msg = '';
		if(oInputElement.value.trim() == ''){
			msg = wcm.LANG.CLASSINFO_19 || "不能为空！";
		}else if(oInputElement.value.byteLength() > 50){
			msg = wcm.LANG.CLASSINFO_20 || "长度大于最大长度50！";
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
		var _parentElement = $TreeNav._getParentElement(parentElement);
		var parentId = _parentElement ? _parentElement.getAttribute("objectId") : 0;
		var previousElement = $TreeNav._getPreviousElement(parentElement, "DIV");
		var	PreviousId = previousElement ? previousElement.getAttribute("objectId") : -1;
		var name = aElement.innerHTML.unescapeHTML();
		return {
			objectId	: parentElement.getAttribute("objectId") || 0,
			parentId	: parentId,
			PreviousId	: PreviousId,
			cName		: name,
			cDesc		: $F('cDesc') || name
		};
	},
	_getDeleteParams : function(aElement){
		return {
			objectId	: aElement.parentNode.getAttribute("objectId") || 0
		};
	},
	failureCallBack : function(msg, response, json){
		this.restoreCurrNode();
		this.isLocking = false;
		if(window.DefaultAjax500CallBack) DefaultAjax500CallBack(response, json);
		else alert(msg);
	}
});