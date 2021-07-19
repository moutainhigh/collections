FloatPanel.addCloseCommand("关闭");   
FloatPanel.setAfterClose(function(){
	var divElement = getFirstHTMLChild($('TreeView'));
	var sNewClassInfoName = divElement.getElementsByTagName("a")[0].innerHTML;
	var oChildElement = com.trs.tree.TreeNav._getTopChildElement(divElement);
	var bHasChild = oChildElement ? oChildElement.getElementsByTagName("div").length > 0 : false;
	try{
		top.$ClassInfoNav().refreshNode({
			classInfoId : getParameter("objectId") || 0,
			classInfoName : sNewClassInfoName,
			forceClose : true,
			hasChild : bHasChild 
		});
	}catch(error){
		alert(error.message);
		//just skip it.
	}
});

var ClassInfos = {
	servicesName : 'wcm6_ClassInfo',
	loadMethodName : 'createClassInfoTreeHTML',
	findMethodName : 'findById',
	saveMethodName : 'saveClassInfo',
	changeOrderMethodName : 'changeOrder',
	deleteMethodName : 'deleteClassInfo'
};

function keyDownEvent(event){
	event = window.event || event;
	if(event.keyCode == Event.KEY_RETURN){
		saveClassInfo(Event.element(event));	
	}
}

function saveClassInfo(sInputElement){
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
	if(sInputElement.name == 'cCode'){
		if(sInputElement.value.byteLength() > sInputElement.maxlen){
			try{
				$alert("长度过长，最大长度为20", function(){
					$dialog().hide();
					setTimeout(function(){
						sInputElement.focus();
					}, 10);
				});
			}catch(error){
			}
			return;
		}
	}
	var oParams = {objectId : sObjId};
	oParams[sInputElement.name] = sInputElement.value;
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call(ClassInfos["servicesName"], ClassInfos["saveMethodName"], oParams, true, function(){
		if(!$("node_" + sObjId)){
			return;
		}
		//更新
		sInputElement.setAttribute("_value", sInputElement.value);
		$("node_" + sObjId).setAttribute(sInputElement.name, sInputElement.value);
	});
}

function changeClassInfoOrder(oCurrDivNode, sDeriction){
	return;
	alert([oCurrDivNode.outerHTML, sDeriction].join("\n"));
	var oParams = {objectId : sObjId};
	oParams[sInputElement.name] = sInputElement.value;
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call(ClassInfos["servicesName"], ClassInfos["changeOrderMethodName"], oParams, true, function(){
		if(!$("node_" + sObjId)){
			return;
		}
		//更新
		sInputElement.setAttribute("_value", sInputElement.value);
		$("node_" + sObjId).setAttribute(sInputElement.name, sInputElement.value);
	});
}

com.trs.tree.TreeNav.onActiveNodeChange = function(divElement){
	var sCCode = divElement.getAttribute("cCode") || "";
	var sObjId = divElement.getAttribute("objectId") || "0";

	var oCCode = $('cCode');
	oCCode.value = sCCode;
	oCCode.setAttribute("_value", sCCode);
	oCCode.setAttribute("objectId", sObjId);
}

com.trs.tree.TreeNav.doActionOnClickA = function(_event,_elElementA){	
	if(_elElementA){
		var oDivNode = _elElementA.parentNode;
		var sCCode = oDivNode.getAttribute("cCode") || "";
		var sObjId = oDivNode.getAttribute("objectId") || "0";

		var oCCode = $('cCode');
		oCCode.value = sCCode;
		oCCode.setAttribute("_value", sCCode);
		oCCode.setAttribute("objectId", sObjId);
	}
	return false;
}

com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(divElement){
	var urlPrefix = '/'+(location.pathname.split('/')[1]||'wcm')+'/center.do?serviceid=' + ClassInfos.servicesName + '&methodname='; 
	var params = "&parentId=" + divElement.getAttribute("objectId");
	return urlPrefix + ClassInfos.loadMethodName + params;
}

com.trs.tree.TreeNav.observe('onload', function(){
	var divElement = getFirstHTMLChild($('TreeView'));
	divElement.setAttribute("objectId", getParameter("objectId"));
	divElement.getElementsByTagName("a")[0].innerText = getParameter("objectName");
	divElement.click();
	com.trs.tree.TreeNav.focus(divElement);

	setTimeout(function(){
		var oParams = {objectId : getParameter("objectId")};
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(ClassInfos["servicesName"], ClassInfos["findMethodName"], oParams, true, function(transport,json){
			var nObjectId = getParameter("objectId");
			var sCCode = $v(json, "ClassInfo.CCODE")
			var sCName = $v(json, "ClassInfo.CNAME")
			var divElement = getFirstHTMLChild($('TreeView'));
			divElement.setAttribute("id", "node_" + nObjectId);
			divElement.setAttribute("title", "ID:" + nObjectId);
			//divElement.setAttribute("objectId", nObjectId);
			divElement.setAttribute("cCode", sCCode);
			divElement.getElementsByTagName("a")[0].innerText = sCName;
			com.trs.tree.TreeNav.focus(divElement);
		});
	}, 10);
});

var showOperator0 = com.trs.tree.TreeManager.prototype.showOperator;
Object.extend(com.trs.tree.TreeManager.prototype, {
	insertBefore : function(aElement){
		var oCCode = $('cCode');
		if(oCCode){
			oCCode.value = "";
			oCCode.removeAttribute("_value");
			oCCode.removeAttribute("objectId");
		}
		return true;
	},
	onPositionChange : function(oCurrDivNode, sDeriction){
		if(!window.oDelayedTask){
			window.oDelayedTask = new com.trs.DelayedTask();
		}
		oDelayedTask.delay(200, changeClassInfoOrder, null, [oCurrDivNode, sDeriction]);
	},
	_insertSuccessCallBack : function(divElement, response, json, resultJson){
		top.status = new Date().getTime();
		this._insertSuccessCallBack0.apply(this, arguments);
		var objectId = $v(json, "classinfo.classinfoid");
		divElement.setAttribute("title", "ID:" + objectId);
		divElement.setAttribute("id", "node_" + objectId);
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
			msg = "不能为空！";
		}else if(oInputElement.value.byteLength() > 30){
			msg = "长度大于最大长度30！";
		}
		if(msg){
			$alert(msg, function(){
				$dialog().hide();
				oInputElement.value = sOldValue;
				oInputElement.focus();
				oInputElement.select();
			})
			return false;
		}
		return true;
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
			Name		: name,
			desc		: name
		};
	},
	_getDeleteParams : function(aElement){
		return {
			objectId	: aElement.parentNode.getAttribute("objectId") || 0
		};
	}
});

