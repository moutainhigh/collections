function dealWithClickTable(event){//处理表格的单击事件
	event = window.event || event;
	var srcElement = Event.element(event);   
	var parentId = isMoreRow(srcElement);
	if(parentId){
		RowsLoadMgr.appendRowsFormCache(parentId)
		return;
	}
	switch(srcElement.tagName.toUpperCase()){
		case 'A':
			selectCurrRow(srcElement);
			break;
		case 'DIV':
			clickTreeNode(Element.first(srcElement));
			break;
		case 'INPUT'://单击checkbox
			if(srcElement.getAttribute("boxType") == 'tree'){
				clickTreeNodeBox(srcElement);
			}else{
				clickRightBox(srcElement);
			}
			break;
		case 'SPAN'://单击了禁用权限图标
			clickRightStatusImg(srcElement);
			break;
	}
}    

function getInputSelect(name){
	var elements = document.getElementsByName(name);
	for (var i = 0; i < elements.length; i++){
		if(elements[i].checked){
			return elements[i];
		}
	}
}

function clickTreeNodeBox(currBoxNode){
	var inputSelect = getInputSelect("rightTypeTab");
	currBoxNode[inputSelect.value + "_status"] = currBoxNode.checked;
	var currDivDomObj = findElement(currBoxNode, "DIV");
	var navTreeDivId = currDivDomObj.id;
	if(!window.oSelectedAllIds){
		window.oSelectedAllIds ={};
	}
	window.oSelectedAllIds[navTreeDivId] = true;
//	var totalInputs = getAllInputsInOneRow(navTreeDivId);
	var totalInputs = $(inputSelect.value + "_" + navTreeDivId).getElementsByTagName("input");
	for (var i = 0; i < totalInputs.length; i++){
		if(totalInputs[i].checked != currBoxNode.checked){
			totalInputs[i].checked = currBoxNode.checked;
			if(totalInputs[i].fireEvent){
				totalInputs[i].fireEvent('onclick');
			}else{
				var evt = document.createEvent("Events");
				evt.initEvent("click", true, false);
				totalInputs[i].dispatchEvent(evt);
			}
		}
	}
	//保存已修改的权限信息
	if(!WCMRightHelper.storeRightsInfo[navTreeDivId]){
		WCMRightHelper.storeRightsInfo[navTreeDivId] = {};
		toggleRightClass('addClassName', navTreeDivId, 'updateRow');
	}
	Object.extend(WCMRightHelper.storeRightsInfo[navTreeDivId], getRowRightsInfo(navTreeDivId));
}

function selectCurrRow(currANode){//选中表格某行，同时取消选中原先行
	currANode.blur();//去掉虚框

	//改变导航树中选中节点的样式
	var currTrDomObj = findElement(currANode, "TR");
	var currDivDomObj = currANode.parentNode;
	var lastClickTrDomObj = findElement(window.lastClickTreeNodeA, "TR");
	var lastClickDivDomObj = (window.lastClickTreeNodeA || {}).parentNode;

	window.lastClickTreeNodeA = currANode;//记录最后点击的树节点

	if(lastClickDivDomObj){
		toggleRightClass('removeClassName', lastClickDivDomObj.id, 'Selected');
	}
	toggleRightClass('addClassName', currDivDomObj.id, 'Selected');
}

function clickTreeNode(currANode){//单击树节点的处理
	/*
	* add by ffx @2012-06-21
	* 权限设置页面, 双击某空白处会显示系统中的多个站点的问题处理
	* 如果点击的节点是select，则直接返回，不作任何处理
	*/
	if(currANode.tagName.toUpperCase() == "SELECT"){
		return;
	}
	showLoading();
	window.lastClickTreeNode = currANode;	
	setCurrTreeNodeStyle(currANode);//改变当前选中节点的样式，及清除原选中节点样式
	
	if(currANode.getAttribute("isLoaded")){//已经载入过
		toggleTreeNode(currANode);//显示/隐藏子节点   
		hideLoading();
		return; 
	}
	
	//发送请求去获得下一级树节点
	currANode.setAttribute("isLoaded", true);
	var idInfo = currANode.parentNode.id.split("_");
	var params = "ViewerIsCurrUser=1&InRightSetPage=1&ParentType=" + idInfo[0] + "&ParentId=" + idInfo[1] + "&OperatorId="+OperId+"&OperatorType="+OperType;
	new Ajax.Request("../../nav_tree/tree_html_creator.jsp", {
		method:'post', 
		parameters:params, 
		onSuccess :loadTreeNodeSuccessCallBack.bind(window, currANode),
		onFailure:failure,
		onComplete : hideLoading,
		contentType:'application/x-www-form-urlencoded'
	});
}

function showLoading(){
	$('loading').style.display = '';
}
function hideLoading(){
	$('loading').style.display = 'none';
}

function setCurrTreeNodeStyle(currANode){//改变当前选中节点的样式，及清除原选中节点样式	
	//改变节点(及子节点)展开/关闭状态样式
	var currDivNode = currANode.parentNode;
	if(currDivNode.className.indexOf("Page") < 0){
		if(currDivNode.className.indexOf("Opened") > 0){
			currDivNode.className = currDivNode.className.replace(/Opened/, "");
		}else{
			currDivNode.className += "Opened";
		}
	}
}

function toggleTreeNode(currANode){//显示/隐藏子节点   
	var currTrDomObj = findElement(currANode, "TR");
	var currDivDomObj = currANode.parentNode;
	var currPaddingLeft = parseInt(Element.first(currTrDomObj).style.paddingLeft || 0);
	var needShow = currANode.parentNode.className.indexOf("Opened") >= 0 ? true : false;
	var display = needShow ? '' : 'none';
	var nextTrDomObj = Element.next(currTrDomObj);
	
	while(nextTrDomObj){
		var nextDivDomObj = Element.first(Element.first(nextTrDomObj));
		var nextPaddingLeft = parseInt(Element.first(nextTrDomObj).style.paddingLeft || 0);

		if(nextPaddingLeft <= currPaddingLeft){
			break;//已经到达同一级
		}

		if(nextTrDomObj.getAttribute("parentId") == currDivDomObj.id){//第一级子节点
			nextTrDomObj.setAttribute("needShow", needShow);//设置是否显示隐藏状态
			nextTrDomObj.style.display = display;

			//隐藏对应权限列表checkbox
			var id = nextDivDomObj.id;
			if(isMoreRow(nextTrDomObj)){
				id = nextTrDomObj.id;
			}
			toggleRightNode(display, id);
			$("h_" + id).style.display = display;
		}else{//非第一级节点
			if(!needShow){//当前单击节点隐藏，所有孙节点(包括孙以下)都要隐藏
				nextTrDomObj.style.display = 'none';
				var id = nextDivDomObj.id;
				if(isMoreRow(nextTrDomObj)){
					id = nextTrDomObj.id;
				}
				toggleRightNode('none', id);
				$("h_" + id).style.display = 'none';
			}else{//当前单击节点显示，所有孙节点(包括孙以下)根据自己状态显示
				if(nextTrDomObj.getAttribute("needShow")){
					nextTrDomObj.style.display = '';
					var id = nextDivDomObj.id;
					if(isMoreRow(nextTrDomObj)){
						id = nextTrDomObj.id;
					}
					toggleRightNode('', id);
					$("h_" + id).style.display = '';
				}else{
					nextTrDomObj.style.display = 'none';
					var id = nextDivDomObj.id;
					if(isMoreRow(nextTrDomObj)){
						id = nextTrDomObj.id;
					}
					toggleRightNode('none', id);
					$("h_" + id).style.display = 'none';
				}                    
			}
		}            
		nextTrDomObj = Element.next(nextTrDomObj);
	}
}

//显示/隐藏对应权限列表checkbox
function toggleRightNode(display, id){
	$('site_' + id).style.display = display;
	$('channel_' + id).style.display = display;
	$('template_' + id).style.display = display;
	$('document_' + id).style.display = display;
	$('flow_' + id).style.display = display;
}

/*
*改变所有行的显示样式
*/
function toggleRightClass(method, divDomObjId, styleClass){
	//eg. Element.removeClassName($('flow_' + lastClickDivDomObj.id), 'Selected');
	Element[method](findElement(divDomObjId, "TR"), styleClass);
	Element[method]('h_' + divDomObjId, styleClass);
	Element[method]('site_' + divDomObjId, styleClass);
	Element[method]('channel_' + divDomObjId, styleClass);
	Element[method]('template_' + divDomObjId, styleClass);
	Element[method]('document_' + divDomObjId, styleClass);
	Element[method]('flow_' + divDomObjId, styleClass);
}

/*
*得到某一行中的所有input
*/
function getAllInputsInOneRow(navTreeDivId){
	var totalInputs = [];
	add(totalInputs, $('site_' + navTreeDivId).getElementsByTagName("input"));
	add(totalInputs, $('channel_' + navTreeDivId).getElementsByTagName("input"));
	add(totalInputs, $('template_' + navTreeDivId).getElementsByTagName("input"));
	add(totalInputs, $('document_' + navTreeDivId).getElementsByTagName("input"));
	add(totalInputs, $('flow_' + navTreeDivId).getElementsByTagName("input"));
	return totalInputs;
}

//单击树上某个节点，请求到达之后执行的回调函数
function loadTreeNodeSuccessCallBack(currANode, originalRequest){
	var sHtml = originalRequest.responseText;
	if(sHtml.startsWith('<font')){//没有注册选件
		return;
	}
	RowsLoadMgr.appendRowsFormRequest(currANode, sHtml);
}

function isMoreRow(eRow){
	var oTr = findElement(eRow, "tr");
	if(!oTr || !oTr.getAttribute("more")) return false;	
	return oTr.getAttribute("parentId");
}


/*
*管理页面中行的加载
*/
var RowsLoadMgr = {
	/*
	*每一次追加的条数
	*/
	appendCountsPreTime : 20,
	
	/*
	*在追加一行记录时，需要获取到的属性列表
	*/
	attributes : {
		id : '',
		desc : '',
		hasChild : '',
		classPre : '',
		IsV : 'isVirtual',
		RV : 'rightValue',
		channelType : '',
		rightId : '',
		title : '',
		canSetRight : ''
	},

	appendRowsFormCache : function(id){
		var item = HtmlCacheMgr.get(id);
		if(!item) return;
		var result = this.append(item["oldTrDomObj"], item["html"], item["parentRowInfoObj"]);
		if(!result) {
			this.removeRowMore(id);
			HtmlCacheMgr.remove(id);
			return;
		}
		HtmlCacheMgr.set(Object.extend(item, result));
	},

	appendRowsFormRequest : function(oRelateParent, sHtml){
		//根据oRelateParent对象，准备将要被追加的父节点信息
		var oldTrDomObj = findElement(oRelateParent, "TR");
		var paddingLeft = Element.first(oldTrDomObj).style.paddingLeft;
		paddingLeft = parseInt(paddingLeft || 0, 10);
		var parentRowInfoObj = {
			paddingLeft : paddingLeft,
			id : oRelateParent.parentNode.id
		};
		var result = this.append(oldTrDomObj, sHtml, parentRowInfoObj);
		if(!result) return;
		this.appendRowMore(result["oldTrDomObj"], parentRowInfoObj.id);
		HtmlCacheMgr.set(Object.extend({id : parentRowInfoObj.id}, result));
	},

	/*
	*将sHtml中的前appendCountsPreTime条记录添加到oRelateParent关联的元素中，
	*返回剩下的尚未添加的内容信息的上下文信息
	*/
	append : function(oldTrDomObj, sHtml, parentRowInfoObj){
		//将内容先存入交换区
		var tempChangeAreaDomObj = $('tempChangeArea');
		Element.update(tempChangeAreaDomObj, sHtml);
		var rowSpanDomObj = Element.first(tempChangeAreaDomObj);
		
		//循环添加appendCountsPreTime条记录
		var attributes = this.attributes;
		var index = 0;
		while(rowSpanDomObj){
			var rowInfoObj = {};
			for(var sKey in attributes){
				rowInfoObj[attributes[sKey] || sKey] = rowSpanDomObj.getAttribute(sKey);
			}
			oldTrDomObj = addNewRow(oldTrDomObj, rowInfoObj, parentRowInfoObj);
			Element.remove(rowSpanDomObj);
			rowSpanDomObj = Element.first(tempChangeAreaDomObj);
			index++;
			if(index >= this.appendCountsPreTime) break;
		}
		var sHtml = tempChangeAreaDomObj.innerHTML.trim();
		if(sHtml.length <= 0) return null;
		return {oldTrDomObj : oldTrDomObj, parentRowInfoObj : parentRowInfoObj, "html" : sHtml};
	},

	appendRowMore : function(oldRow, id){
		//构造权限标识节点
		var newImgTemplater = $('img_templater');
		var eNewImg = newImgTemplater.cloneNode(true);
		eNewImg.id = 'h_' + id + "_more";
		var oldImgObj = $('h_' + oldRow.getElementsByTagName("div")[0].id);
		oldImgObj.parentNode.insertBefore(eNewImg, oldImgObj.nextSibling);

		//添加导航树列
		var eNewNav = oldRow.cloneNode(true);
		eNewNav.id = id + "_more";
		eNewNav.setAttribute("more", "true");
		Element.addClassName(eNewNav, "more");
		var divNode = eNewNav.getElementsByTagName("Div")[0];
		divNode.removeAttribute("id");
		divNode.title = wcm.LANG.AUTH_NODETITLE || "单击该节点，将加载尚未显示的内容";
		var aNode = eNewNav.getElementsByTagName("A")[0];
		aNode.innerText = wcm.LANG.AUTH_MORE || "更多..."
		oldRow.parentNode.insertBefore(eNewNav, oldRow.nextSibling);

		var aType = ['site', 'channel', 'document', 'template', 'flow'];
		for (var i = 0; i < aType.length; i++){
			var eNewRightTemplater = $("blank_" + aType[i] + "_templater");
			var eNewRight = eNewRightTemplater.cloneNode(true);
			eNewRight.id = aType[i] + "_" + id + "_more";
			var parentObj = $(aType[i] + "_" + Element.first(Element.first(oldRow)).id);
			parentObj.parentNode.insertBefore(eNewRight, parentObj.nextSibling);
		}
	},

	removeRowMore : function(id){
		Element.remove('h_' + id + "_more");
		Element.remove(id + "_more");
		var aType = ['site', 'channel', 'document', 'template', 'flow'];
		for (var i = 0; i < aType.length; i++){
			Element.remove(aType[i] + "_" + id + '_more');
		}
	}
};


/*
*缓存还没有显示的html信息，以便在需要的时候，再次添加到页面中进行显示
*/
var HtmlCacheMgr = function(){
	var cache = {};
	return {
		set : function(item){
			cache[item["id"]] = item;
		},
		remove : function(id){
			delete cache[id];
		},
		get : function(id){
			return cache[id];
		}
	};
}();

//向页面中添加新的一个行
function addNewRow(oldRow, newRowInfoObj, parentRowInfoObj){
	var canSetRight = newRowInfoObj.canSetRight;
	//构造权限标识节点
	var newImgTemplater = $('img_templater');
	var eNewImg = newImgTemplater.cloneNode(true);
	var oldImgObj = $('h_' + oldRow.getElementsByTagName("div")[0].id);
	oldImgObj.parentNode.insertBefore(eNewImg, oldImgObj.nextSibling);
	eNewImg.id = 'h_' + newRowInfoObj.id;
	if(newRowInfoObj.rightId && newRowInfoObj.rightId != '0' && canSetRight=='true'){
		eNewImg.getElementsByTagName("TD")[0].innerHTML = "<span class='enable' rightId='" + newRowInfoObj.rightId + "'></span>";
	}

	//添加导航树列
	var eNewNavTemplater = $('nav_templater');
	var eNewNav = eNewNavTemplater.cloneNode(true);
	oldRow.parentNode.insertBefore(eNewNav, oldRow.nextSibling);
	eNewNav.id = eNewNav.id + "_" + newRowInfoObj.id;
	setTreeNodeHTML(eNewNav, newRowInfoObj, parentRowInfoObj);
	eNewNav.setAttribute("parentId", parentRowInfoObj.id);
	eNewNav.setAttribute("needShow", true);

	var detectorNum = getGenenateDetector(parentRowInfoObj.id.split("_")[0]);
	if(detectorNum >=4){//需要生成站点权限checkbox
		addNewRightRow('site_', 'site_templater', oldRow, newRowInfoObj);
		addNewRightRow('flow_', 'flow_templater', oldRow, newRowInfoObj);
	}else{
		addNewRightRow('site_', 'blank_site_templater', oldRow, newRowInfoObj);
		addNewRightRow('flow_', 'blank_flow_templater', oldRow, newRowInfoObj);
	}
	
	var normalTempaltes = [
		['channel_', 'channel_templater'], 
		['template_', 'template_templater'],
		['document_', 'document_templater']
	];
	var virtualTemplates = [
		['channel_', 'channel_templater'], 
		['template_', 'blank_template_templater'],
		['document_', 'document_templater']
	];
	if(detectorNum >=3){//需要生成栏目、模板、文档的权限checkbox
		var templates = newRowInfoObj.isVirtual ? virtualTemplates : normalTempaltes;
		for (var i = 0; i < templates.length; i++){
			addNewRightRow(templates[i][0], templates[i][1], oldRow, newRowInfoObj);
		}
	}

	//添加相应权限列
	return eNewNav;
}

//添加相应权限列checkbox
function addNewRightRow(prefix, templateType, oldRow, newRowInfoObj){
	//当前登录用户有设置权限的权限
	var canSetRight = newRowInfoObj.canSetRight;
	var eNewRightTemplater;
	if(canSetRight == 'true' || templateType.indexOf("blank_") != -1){
		eNewRightTemplater = $(templateType);
	}else{
		eNewRightTemplater = $("blank_"+templateType);
	}
	var eNewRight = eNewRightTemplater.cloneNode(true);
	var parentObj = $(prefix + Element.first(Element.first(oldRow)).id);
	parentObj.parentNode.insertBefore(eNewRight, parentObj.nextSibling);
	eNewRight.id = prefix + newRowInfoObj.id;				
	setRightNodeHTML(eNewRight, Element.next(oldRow));
}

function getGenenateDetector(objType){
	switch(objType){
		case 'r'://单击了导航树的根节点
			return 4;
		case 's'://单击了导航树的站点节点
		case 'c'://单击了导航树的栏目节点
			return 3;
		default:
			return 0;
	}
}

//设置权限节点及innerHTML信息
function setRightNodeHTML(newRightRow, newTreeNodeRow){
	var inputs = newRightRow.getElementsByTagName("INPUT");
	var currRowRight = Element.first(Element.first(newTreeNodeRow)).getAttribute("rightValue");
	for (var i = 0; i < inputs.length; i++){
		inputs[i].id = newRightRow.id + "_" + inputs[i].id;//修改checkbox的ｉｄ信息
		//设置是否选中
		inputs[i].checked = wcm.AuthServer.checkRight(currRowRight, inputs[i].getAttribute("rightIndex"));
	}
}

//设置导航树节点及innerHTML信息
function setTreeNodeHTML(newRowObj, newRowInfoObj, parentRowInfoObj){
	var treeNodeTdObj = Element.first(newRowObj);
	var nodeClass = newRowInfoObj.classPre;
	nodeClass += newRowInfoObj.hasChild == 'true' ? "Folder" : "Page";
	//当前登录用户对该节点是否有设置权限的权限
	var canSetRight = newRowInfoObj.canSetRight;
	
	var aHtml = [
		'<div ',
		' class="', nodeClass, '"',
		' id="', newRowInfoObj.id, '"',
		' title="', escapeQuots(newRowInfoObj.title), '"',
		' rightValue="', newRowInfoObj.rightValue, '"',
		' hasChild="', newRowInfoObj.hasChild, '"',
		' classPre="', newRowInfoObj.classPre, '"',
		' canSetRight="',newRowInfoObj.canSetRight, '"',
		'>',
			'<a href="#">'
	];
	//如果有设置权限的权限，则显示节点前面的复选框
	if(canSetRight == 'true'){
		aHtml.push('<input name="c' + newRowInfoObj.id + '" type="checkbox" boxType="tree"/>');
	}
	aHtml.push($trans2Html(newRowInfoObj.desc) + '</a></div>');

	var sHtml = aHtml.join("");

	treeNodeTdObj.innerHTML = sHtml;
	
	//if(window.lastClickTreeNode.parentNode.className.indexOf("siteFolder") >= 0){
	if(Element.first($(parentRowInfoObj.id)).className.indexOf("siteFolder") >= 0){	
		treeNodeTdObj.style.paddingLeft = parentRowInfoObj.paddingLeft + 5 + "px";
	}else{
		treeNodeTdObj.style.paddingLeft = parentRowInfoObj.paddingLeft + 20 + "px";
	}
}

function escapeQuots(sHtml){
	sHtml = sHtml || "";
	return sHtml.replace(/'|"/ig, function($0){
		if($0 == '\'') return '&#39';
		return '&#34';
	});
}

/*
*单击禁用权限的图标
*/
function clickRightStatusImg(currImgObj){
	Ext.Msg.confirm(wcm.LANG.AUTH_28||"确认清除当前用户自己的权限，使之继承用户组、角色等逻辑权限？", {
		yes : function(){
			BasicDataHelper.call('wcm6_auth', 'deleteOperatorRight', {
				ObjectId : currImgObj.getAttribute("rightId")
			}, true, deleteOperatorRight, failure);
		}
	});
	function deleteOperatorRight(transport, json){
		var currRowRight = transport.responseText;
		var trDomObj = findElement(currImgObj, "TR");
		currImgObj.parentNode.innerHTML = "&nbsp;";//隐藏图标		

		var idInfoArray = trDomObj.id.split("_");
		var navTreeDivId = idInfoArray[1] + "_" + idInfoArray[2];	
		updateSiteInfo(navTreeDivId, {RV:currRowRight, rightId : 0});

		//将返回的新的权限值赋给checkbox
		var totalInputs = getAllInputsInOneRow(navTreeDivId);
		for (var i = 0; i < totalInputs.length; i++){
			totalInputs[i].checked = wcm.AuthServer.checkRight(currRowRight, totalInputs[i].getAttribute("rightIndex"));
		}

		if(WCMRightHelper.storeRightsInfo[navTreeDivId]){//清除保留的信息
			toggleRightClass('removeClassName', navTreeDivId, 'updateRow');
			delete WCMRightHelper.storeRightsInfo[navTreeDivId] //销毁此对象
		}
	}
}

/*
*得到权限列表中某行的信息
*/
function getRowRightsInfo(navTreeDivId){
	var totalInputs = getAllInputsInOneRow(navTreeDivId);
	var newRightValue = new Array(64);
	for (var i = 0; i < newRightValue.length; i++){//初始化数组
		newRightValue[i] = 0;
	}
	for (var i = 0; i < totalInputs.length; i++){
		if(totalInputs[i].checked){
			var rightIndex = totalInputs[i].getAttribute("rightIndex");
			newRightValue[63-rightIndex] = 1;			
		}
	}
	newRightValue = newRightValue.join("");

	var rightId = 0;
	var imgArray = $("h_" +navTreeDivId).getElementsByTagName('span');
	if(imgArray.length > 0){
		rightId = imgArray[0].getAttribute('rightId');
	}
	var infoArray = navTreeDivId.split("_");
	return {
		objType		: (infoArray[0] == 's' ? 103 : 101),
		objId		: infoArray[1],
		rightValue	: newRightValue,
		rightId		: rightId
	};
}

function saveCurrRowRights(){//保存选定行
	if(!window.lastClickTreeNodeA){
		Ext.Msg.alert(wcm.LANG.AUTH_ALERT_1||'没有选定行，请先选定某行');
		return;
	}
	window.saveType = "saveCurrRow";
	var navTreeDiv = window.lastClickTreeNodeA.parentNode;
	if(!navTreeDiv){
		Ext.Msg.alert(wcm.LANG.AUTH_ALERT_1||'没有选定行,请先选定某行');
		return;
	} 
	saveRights(getSaveParams({tempAttr:getRowRightsInfo(navTreeDiv.id)}));
}

function saveAllRowsRights(fCallBack){//保存所有行
	window.saveType = "saveAll";
	saveRights(getSaveParams(WCMRightHelper.storeRightsInfo), fCallBack);
}

function getSaveParams(storeRightsObj){//获得保存的文本串
	var aResult = [];
	aResult.push("OperatorId=", OperId, "&OperatorType=", OperType, "&RightsXML=<WCMRIGHTS>");
	for (var prop in storeRightsObj){	
		//获得特定节点的信息				
		var obj = storeRightsObj[prop];
		aResult.push(
			"<WCMRIGHT><PROPERTIES>",
				"<OBJTYPE>", obj.objType, "</OBJTYPE>",
				"<OBJID>", obj.objId, "</OBJID>",
				"<RIGHTVALUE>", obj.rightValue, "</RIGHTVALUE>",
				"<RIGHTID>", obj.rightId, "</RIGHTID>",	
			"</PROPERTIES></WCMRIGHT>"
		);
	}
	aResult.push("</WCMRIGHTS>");
	return aResult.join("");
}

function saveRights(params, fCallBack){
	BasicDataHelper.call('wcm6_auth', 'saveOperatorRights', params.parseQuery(), true, function(transport, json){
		saveSuccessCallBack(transport, json);
		if(fCallBack) fCallBack();
	}, failure);
}

function getRightInfo(id){
	return WCMRightHelper.storeRightsInfo[id] || {rightValue:0};
}

function saveSuccessCallBack(transport, json){
	Ext.Msg.timeAlert(wcm.LANG.AUTH_ALERT_2||'操作成功！',5);
	//将返回的ＩＤ赋给第一列，并产生图标,同时删除保留的对象和恢复颜色
	var rightIds = transport.responseText.split(","), index = 0;
	if(window.saveType == 'saveAll'){//点击保存全部按钮
		for (var navTreeDivId in WCMRightHelper.storeRightsInfo){
			updateSiteInfo(navTreeDivId, {RV:WCMRightHelper.storeRightsInfo[navTreeDivId]["rightValue"], rightId: rightIds[index]});
			var imgTrDom = $("h_" + navTreeDivId);
			if(imgTrDom.getElementsByTagName("span").length <= 0){
				imgTrDom.getElementsByTagName("TD")[0].innerHTML = "<span class='enable' rightId='" + rightIds[index] + "'></span>";
			}
			toggleRightClass('removeClassName', navTreeDivId, 'updateRow');
			delete WCMRightHelper.storeRightsInfo[navTreeDivId];
			index++;
		}
	}else if(window.saveType == 'saveCurrRow'){//点击保存当前行按钮
		if(window.lastClickTreeNodeA){
			var navTreeDivId = window.lastClickTreeNodeA.parentNode.id;			
			updateSiteInfo(navTreeDivId, {RV:getRightInfo(navTreeDivId)["rightValue"], rightId: rightIds[index]});
			var imgTrDom = $("h_" + navTreeDivId);
			if(imgTrDom.getElementsByTagName("span").length <= 0){
				imgTrDom.getElementsByTagName("TD")[0].innerHTML = "<span class='enable' rightId='" + rightIds[index] + "'></span>";
			}
		}
	}
}

function updateSiteInfo(sSiteId, info){
	if(sSiteId.indexOf("s_") < 0) return;
	SitesLoadMgr.updateSiteInfo(sSiteId.split("_")[1], info);
}

function showType(type){//显示当前选中的权限列表
	$(type + "Radio").checked = true;
	if(window.lastTab){
		Element.hide(window.lastTab + "RightTab");
	}
	window.lastTab = type;
	Element.show(type + "RightTab");

//	for(divId in WCMRightHelper.storeRightsInfo){
	for(divId in window.oSelectedAllIds){
		var oCheckbox = $("c" + divId);
		if(!oCheckbox)continue;
		oCheckbox.checked = oCheckbox[type + "_status"] || false;
	}
}


/*
*从节点startDomObj开始，沿dom树向上知道标记为tagName，
*返回最后匹配的节点，否则null
*/
function findElement(startDomObj, tagName){
	if(startDomObj == null) return null;
	startDomObj = $(startDomObj);
	while (startDomObj.parentNode && (!startDomObj.tagName ||
		(startDomObj.tagName.toUpperCase() != tagName.toUpperCase())))
	  startDomObj = startDomObj.parentNode;
	return startDomObj;
}

function failure(transport, json){
	try{
		if(json && json.FAULT){
			wcm.FaultDialog.show({
				code		: $v(json,'fault.code'),
				message		: $v(json,'fault.message'),
				detail		: $v(json,'fault.detail'),
				suggestion  : $v(json,'fault.suggestion')
			});
		}else{
			Ext.Msg.error(wcm.LANG.AUTH_ALERT_3||'Ajax请求失败!');
		}
	}catch(error){
		Ext.Msg.error(wcm.LANG.AUTH_ALERT_3||'Ajax请求失败!');
	}
}  

//单击权限checkbox的处理
function clickRightBox(srcElement){ 
	if(!window.execTime){
		window.execTime = 0;
		var currTrDom = findElement(srcElement, "TR");
		var infoArray0 = currTrDom.id.split("_");
		
		window.navTreeDivId = infoArray0[1] + "_" + infoArray0[2];
		window.totalInputs = getAllInputsInOneRow(navTreeDivId);
	}
	window.execTime++;

	var infoArray = srcElement.id.split("_");
	var index = infoArray.pop();
	var type = infoArray.pop();
	var headDom = $(type + "_" + index + "_h"); 

	//获得相似和依赖权限
	var rightIndexs = [srcElement.getAttribute("rightIndex")];
	var checked = srcElement.checked;
	var depends = headDom.getAttribute("depends");
	depends = (depends == 'null' ? [] : depends.split(","));
	var similars = headDom.getAttribute("similars");
	similars = (similars == 'null' ? [] : similars.split(","));
	if(checked){
		rightIndexs = rightIndexs.concat(depends);
	}else{
		rightIndexs = rightIndexs.concat(similars);
	}

	//联动修改checkbox状态
	for (var i = 0; i < totalInputs.length; i++){
		var rightIndex = totalInputs[i].getAttribute("rightIndex");
		if(!totalInputs[i].getAttribute("dealed") && rightIndexs.include(rightIndex)){
			totalInputs[i].checked = checked;
			totalInputs[i].setAttribute("dealed", true);
			/*已经对depends和similars进行了处理，这里就不需要再触发一个onclick事件了
			if(Ext.isIE){
				totalInputs[i].fireEvent('onclick');
			}
			else{
				var evt = document.createEvent("Event");
				evt.initEvent("click", true, false);
				totalInputs[i].dispatchEvent(evt);
			}*/
		}	
	}
	window.execTime--;
	if(window.execTime == 0){
		//保存已修改的权限信息
		if(!WCMRightHelper.storeRightsInfo[navTreeDivId]){
			WCMRightHelper.storeRightsInfo[navTreeDivId] = {};
			toggleRightClass('addClassName', navTreeDivId, 'updateRow');
		}
		Object.extend(WCMRightHelper.storeRightsInfo[navTreeDivId], getRowRightsInfo(navTreeDivId));
		for (var i = 0, len=totalInputs.length; i < len; i++){
			totalInputs[i].removeAttribute("dealed");
			totalInputs[i] = null;
		}
		window.execTime = null;
		window.navTreeDivId = null;
		window.totalInputs = null;
	}
}

//将collect元素添加至array中
function add(array, collect){
	for (var i = 0; i < collect.length; i++){
		array.push(collect[i]);
	}
}

WCMRightHelper = new Object();
Object.extend(WCMRightHelper, {
	defaultType:'site',//默认显示站点权限列表
	storeRightsInfo : {},//保存已经修改过的权限信息
	types : {},
	addRightDef : function(rightIndex, displayName, desc, type, depends, similars){
		if(type){
			type = type.toLowerCase();
			if(!this.types[type]){
				this.types[type] = [];
			}
			this.types[type].push({
				rightIndex	: rightIndex,
				displayName	: displayName,
				desc		: desc,
				type		: type,
				depends		: depends,
				similars	: similars
			});
		}
	},
	/*
	*templateType:site,channel,template,document,flow
	*type:blank, notBlank, all
	*/
	getTemplaterHTML : function(templateType, type, id){//
		templateType = templateType.toLowerCase();
		var length = this.types[templateType].length;
		var aResult = [];
		if(type == 'notBlank' || type == 'all'){
			aResult.push('<tr id="', id?id:(templateType + '_templater'), '">');
			for (var i = 0; i < length; i++){
				var rightDefObj = this.types[templateType][i];
				aResult.push(
					'<td>',	
						'<input type="checkbox" value=""',
						' rightIndex="', rightDefObj.rightIndex, '"',
						' id="', templateType, '_', i, '"',
						' title="', rightDefObj.desc, '"',
					'"/></td>'	
				);
			}
			aResult.push('</tr>');
		}
		if(type == 'blank' || type == 'all'){
			aResult.push('<tr id="', id?id:("blank_" + templateType + '_templater'), '">');
			for (var i = 0; i < length; i++){
				aResult.push('<td id="', templateType, '_', i, '">&nbsp;</td>');
			}
			aResult.push('</tr>');
		}
		return aResult.join("");
	},
	/*
	*获得表头信息
	*/
	getHeadHTMLForType : function(type){
		type = type.toLowerCase();
		var aResult = [];
		var length = this.types[type].length;
		for (var i = 0; i < length; i++){
			var rightDefObj = this.types[type][i];
			aResult.push(
				'<td',
					' depends="', rightDefObj.depends, '"',
					' similars="', rightDefObj.similars, '"',
					' id="', type, '_', i, '_h', '"',
					' title="', rightDefObj.desc, '"',
				'>',
					rightDefObj.displayName,
				'</td>'
			);
		}
		return aResult.join("");
	}
});

var OperId = getParameter("OperId");
var OperType = getParameter("OperType");
window.lastTab = WCMRightHelper.defaultType;
Event.observe(window, 'load', function(){
	Event.observe('saveCurrRowId', 'click', function(){
		saveCurrRowRights();
	}, false);
	Event.observe('saveAllRowsId', 'click', function(){
		saveAllRowsRights(); 
	}, false);
	$(window.lastTab + "Radio").checked = true;

	if(OperType == '204'){
		$('imgTdTable').style.display = '';
		$('imgDescription').style.display = '';
	}
});


/*
*站点加载管理
*/
var SitesLoadMgr = {
	content : null,
	
	isLoaded : function(){
		return SitesLoadMgr.content != null;
	},

	updateSiteInfo : function(nSiteId, info){
		Element.update("tempChangeArea", SitesLoadMgr.content);
		var dom = $('ms_' + nSiteId);
		for (var sKey in info){
			dom.setAttribute(sKey, info[sKey]);
		}
		SitesLoadMgr.content = $("tempChangeArea").outerHTML;
	},

	getSiteInfo : function(nSiteId){
		Element.update("tempChangeArea", SitesLoadMgr.content);
		var sHtml = $('ms_' + nSiteId).outerHTML;
		sHtml = sHtml.replace(/<(\/)?script>/ig, '&lt;$1script&gt;');
		Element.update("tempChangeArea", "");
		return SitesLoadMgr.restoreIds(sHtml);
	},

	loadSites : function(){
		var params = "ViewerIsCurrUser=1&InRightSetPage=1&ParentType=r&ParentId=-1&OperatorId="+OperId+"&OperatorType="+OperType;
		new Ajax.Request("../../nav_tree/tree_html_creator.jsp", {
			method:'post', 
			parameters:params, 
			onSuccess :SitesLoadMgr.sitesLoaded,
			contentType:'application/x-www-form-urlencoded'
		});
	},

	convertIds : function(sHtml){
		return sHtml.replace(/id=['"]?s_(\d+)['"]?/img, "id='ms_$1'");
	},

	restoreIds : function(sHtml){
		return sHtml.replace(/id=['"]?ms_(\d+)['"]?/img, "id='s_$1'");
	},

	sitesLoaded : function(transport){
		var sHtml = transport.responseText;
		SitesLoadMgr.content = SitesLoadMgr.convertIds(sHtml);
		(SitesLoadMgr['load'] || Ext.emptyFn)();		
	},

	observe : function(type, fn){
		SitesLoadMgr[type.toLowerCase()] = fn;
	}
};
SitesLoadMgr.loadSites();

function onChange(selObj){
	//站点尚未加载完成
	if(!SitesLoadMgr.isLoaded()){
		SitesLoadMgr.observe('load', onChange.bind(window, selObj));
		return;
	}
	var bUpdate = false;
	var objRights = WCMRightHelper.storeRightsInfo;
	for (var sKey in objRights){
		bUpdate = true;
		break;
	}
	if(bUpdate && confirm(wcm.LANG.AUTH_ALERT_4||"已经做了修改，是否保存?")){
		saveAllRowsRights(function(){
			loadSiteRows(selObj.value);
			WCMRightHelper.storeRightsInfo = {};
		});
	}else{
		loadSiteRows(selObj.value);
		WCMRightHelper.storeRightsInfo = {};
	}
	//WCMRightHelper.storeRightsInfo = {};
}

function loadSiteRows(nSiteId){
	removePreSiteRows();
	appendSiteRow(nSiteId);
	clickTreeNode(Element.first($("s_" + nSiteId)));
}

/*
*将前一次页面中站点和栏目信息清空
*/
function removePreSiteRows(){
	var aTable = ['imgTable', 'leftNavTree', 'siteRightTab', 'channelRightTab', 'templateRightTab', 'documentRightTab', 'flowRightTab'];
	for (var i = 0; i < aTable.length; i++){
		var rows = $(aTable[i]).rows;
		for (var j = rows.length - 1; j > 0; j--){
			Element.remove(rows[j]);
		}
	}
}

/*
*追加当前需要显示的站点信息
*/
function appendSiteRow(nSiteId){
	var sHtml = SitesLoadMgr.getSiteInfo(nSiteId);
	RowsLoadMgr.appendRowsFormRequest($('siteSel'), sHtml);
}

Event.observe(window, 'load', function(){
	if(!$('siteSel').value) return;
	onChange($('siteSel'));
});
