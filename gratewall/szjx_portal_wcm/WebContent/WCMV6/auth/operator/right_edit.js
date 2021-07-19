function dealWithClickTable(event){//处理表格的单击事件
	event = window.event || event;
	var srcElement = Event.element(event);   
	switch(srcElement.tagName.toUpperCase()){
		case 'A':
			selectCurrRow(srcElement);
			break;
		case 'DIV':
			clickTreeNode(getFirstHTMLChild(srcElement));
			break;
		case 'INPUT'://单击checkbox
			if(srcElement.getAttribute("boxType") == 'tree'){
				clickTreeNodeBox(srcElement);
			}else{
				clickRightBox(srcElement);
			}
			break;
		case 'IMG'://单击了禁用权限图标
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
		totalInputs[i].checked = currBoxNode.checked;
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
		onSuccess :loadTreeNodeSuccessCallBack,
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
	var currPaddingLeft = parseInt(getFirstHTMLChild(currTrDomObj).style.paddingLeft || 0);
	var needShow = currANode.parentNode.className.indexOf("Opened") >= 0 ? true : false;
	var display = needShow ? '' : 'none';
	var nextTrDomObj = getNextHTMLSibling(currTrDomObj);
	
	while(nextTrDomObj){
		var nextDivDomObj = getFirstHTMLChild(getFirstHTMLChild(nextTrDomObj));
		var nextPaddingLeft = parseInt(getFirstHTMLChild(nextTrDomObj).style.paddingLeft || 0);

		if(nextPaddingLeft <= currPaddingLeft){
			break;//已经到达同一级
		}

		if(nextTrDomObj.getAttribute("parentId") == currDivDomObj.id){//第一级子节点
			nextTrDomObj.setAttribute("needShow", needShow);//设置是否显示隐藏状态
			nextTrDomObj.style.display = display;

			//隐藏对应权限列表checkbox
			toggleRightNode(display, nextDivDomObj.id);
			$("h_" + nextDivDomObj.id).style.display = display;
		}else{//非第一级节点
			if(!needShow){//当前单击节点隐藏，所有孙节点(包括孙以下)都要隐藏
				nextTrDomObj.style.display = 'none';
				toggleRightNode('none', nextDivDomObj.id);
				$("h_" + nextDivDomObj.id).style.display = 'none';
			}else{//当前单击节点显示，所有孙节点(包括孙以下)根据自己状态显示
				if(nextTrDomObj.getAttribute("needShow")){
					nextTrDomObj.style.display = '';
					toggleRightNode('', nextDivDomObj.id);
					$("h_" + nextDivDomObj.id).style.display = '';
				}else{
					nextTrDomObj.style.display = 'none';
					toggleRightNode('none', nextDivDomObj.id);
					$("h_" + nextDivDomObj.id).style.display = 'none';
				}                    
			}
		}            
		nextTrDomObj = getNextHTMLSibling(nextTrDomObj);
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
function loadTreeNodeSuccessCallBack(originalRequest){
	var tempChangeAreaDomObj = $('tempChangeArea');
	if(originalRequest.responseText.startsWith('<font')){//没有注册选件
		return;
	}
	tempChangeAreaDomObj.innerHTML = originalRequest.responseText;
	var rowSpanDomObj = getFirstHTMLChild(tempChangeAreaDomObj);
	
	var oldTrDomObj = findElement(window.lastClickTreeNode, "TR");
	var paddingLeft = getFirstHTMLChild(oldTrDomObj).style.paddingLeft;
	paddingLeft = parseInt(paddingLeft || 0);
	var parentRowInfoObj = {
		paddingLeft : paddingLeft,
		id : window.lastClickTreeNode.parentNode.id
	};
	while(rowSpanDomObj){
		rowInfoObj = {
			id          : rowSpanDomObj.id,
			desc        : rowSpanDomObj.getAttribute("desc"),
			hasChild    : rowSpanDomObj.getAttribute("hasChild"),
			classPre    : rowSpanDomObj.getAttribute("classPre"),
			rightValue  : rowSpanDomObj.getAttribute("RV"),
			channelType : rowSpanDomObj.getAttribute("channelType"),
			rightId		: rowSpanDomObj.getAttribute("rightId"),
			title       : rowSpanDomObj.getAttribute("title")
		};
		oldTrDomObj = addNewRow(oldTrDomObj, rowInfoObj, parentRowInfoObj);
		rowSpanDomObj = getNextHTMLSibling(rowSpanDomObj);
	}
}

//向页面中添加新的一个行
function addNewRow(oldRow, newRowInfoObj, parentRowInfoObj){
	var newImgTemplater = $('img_templater');
	var eNewImg = newImgTemplater.cloneNode(true);
	var oldImgObj = $('h_' + oldRow.getElementsByTagName("div")[0].id);
	oldImgObj.parentNode.insertBefore(eNewImg, oldImgObj.nextSibling);
	eNewImg.id = 'h_' + newRowInfoObj.id;
	if(newRowInfoObj.rightId){
		eNewImg.getElementsByTagName("TD")[0].innerHTML = "<img  style='cursor:pointer;' src='../../images/auth/enable.gif' rightId='" + newRowInfoObj.rightId + "'/>";
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

	if(detectorNum >=3){//需要生成栏目、模板、文档的权限checkbox
		addNewRightRow('channel_', 'channel_templater', oldRow, newRowInfoObj);
		addNewRightRow('template_', 'template_templater', oldRow, newRowInfoObj);
		addNewRightRow('document_', 'document_templater', oldRow, newRowInfoObj);
	}

	//添加相应权限列
	return eNewNav;
}

//添加相应权限列checkbox
function addNewRightRow(prefix, templateType, oldRow, newRowInfoObj){
		var eNewRightTemplater = $(templateType);
		var eNewRight = eNewRightTemplater.cloneNode(true);
		var parentObj = $(prefix + getFirstHTMLChild(getFirstHTMLChild(oldRow)).id);
		parentObj.parentNode.insertBefore(eNewRight, parentObj.nextSibling);
		eNewRight.id = prefix + newRowInfoObj.id;				
		setRightNodeHTML(eNewRight, getNextHTMLSibling(oldRow));
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
	var currRowRight = getFirstHTMLChild(getFirstHTMLChild(newTreeNodeRow)).getAttribute("rightValue");
	for (var i = 0; i < inputs.length; i++){
		inputs[i].id = newRightRow.id + "_" + inputs[i].id;//修改checkbox的ｉｄ信息
		//设置是否选中
		inputs[i].checked = isAccessable4WcmObject(currRowRight, inputs[i].getAttribute("rightIndex"));
	}
}

//设置导航树节点及innerHTML信息
function setTreeNodeHTML(newRowObj, newRowInfoObj, parentRowInfoObj){
	var treeNodeTdObj = getFirstHTMLChild(newRowObj);
	var nodeClass = newRowInfoObj.classPre;
	nodeClass += newRowInfoObj.hasChild == 'true' ? "Folder" : "Page";
	var htmlStr = "<div";                
	htmlStr += " class='" + nodeClass + "' id='" + newRowInfoObj.id + "'";
	htmlStr += " title='" + newRowInfoObj.title + "' rightValue='" + newRowInfoObj.rightValue + "'";
	htmlStr += " hasChild='" + newRowInfoObj.hasChild + "' classPre='" + newRowInfoObj.classPre + "'";
	htmlStr += "><a href='#'><input name='c" + newRowInfoObj.id + "' type='checkbox' boxType='tree'/>" + newRowInfoObj.desc + "</a></div>";
	treeNodeTdObj.innerHTML = htmlStr;
	if(window.lastClickTreeNode.parentNode.className.indexOf("siteFolder") >= 0){
		treeNodeTdObj.style.paddingLeft = parentRowInfoObj.paddingLeft + 5 + "px";
	}else{
		treeNodeTdObj.style.paddingLeft = parentRowInfoObj.paddingLeft + 20 + "px";
	}
}

/*
*单击禁用权限的图标
*/
function clickRightStatusImg(currImgObj){
/*
	var params = 'serviceid=wcm6_auth&methodname=deleteOperatorRight&ObjectId='+currImgObj.getAttribute("rightId");
	new Ajax.Request('/wcm/center.do', {
		method:'get', 
		parameters:params, 
		onComplete :deleteOperatorRight,
		onFailure:failure
	});
*/
	BasicDataHelper.call('wcm6_auth', 'deleteOperatorRight', {
		ObjectId : currImgObj.getAttribute("rightId")
	}, true, deleteOperatorRight, failure);

	function deleteOperatorRight(transport, json){
		var currRowRight = transport.responseText;
		var trDomObj = findElement(currImgObj, "TR");
		currImgObj.parentNode.innerHTML = "&nbsp;";//隐藏图标		

		var idInfoArray = trDomObj.id.split("_");
		var navTreeDivId = idInfoArray[1] + "_" + idInfoArray[2];	

		//将返回的新的权限值赋给checkbox
		var totalInputs = getAllInputsInOneRow(navTreeDivId);
		for (var i = 0; i < totalInputs.length; i++){
			totalInputs[i].checked = isAccessable4WcmObject(currRowRight, totalInputs[i].getAttribute("rightIndex"));
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
	var imgArray = $("h_" +navTreeDivId).getElementsByTagName('IMG');
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
		$alert('没有选定行，请先选定某行', function(){
			$dialog().hide();
		});
		return;
	}
	window.saveType = "saveCurrRow";
	saveRights(getSaveParams({tempAttr:getRowRightsInfo(window.lastClickTreeNodeA.parentNode.id)}));
}

function saveAllRowsRights(){//保存所有行
	window.saveType = "saveAll";
	saveRights(getSaveParams(WCMRightHelper.storeRightsInfo));
}

function getSaveParams(storeRightsObj){//获得保存的文本串
	var rightsXML = "OperatorId="+OperId+"&OperatorType="+OperType+"&RightsXML=<WCMRIGHTS>";
	for (var prop in storeRightsObj){		
		rightsXML += "<WCMRIGHT><PROPERTIES>"
		//获得特定节点的信息				
				+ "<OBJTYPE>" + storeRightsObj[prop].objType + "</OBJTYPE>"
				+ "<OBJID>" + storeRightsObj[prop].objId + "</OBJID>"
				+ "<RIGHTVALUE>" + (storeRightsObj[prop].rightValue) + "</RIGHTVALUE>"
				+ "<RIGHTID>" + storeRightsObj[prop].rightId + "</RIGHTID>";				
		rightsXML += "</PROPERTIES></WCMRIGHT>";
	}
	//return encodeURI(rightsXML+"</WCMRIGHTS>");
	return rightsXML+"</WCMRIGHTS>";
}

function saveRights(params){
/*	
	params = "serviceid=wcm6_auth&methodname=saveOperatorRights&"+params;
	new Ajax.Request('/wcm/center.do', {
		method:'get', 			
		parameters:params, 
		onSuccess : saveSuccessCallBack,
		onFailure:failure,
		contentType:'application/x-www-form-urlencoded'
	});
*/
	BasicDataHelper.call('wcm6_auth', 'saveOperatorRights', params.toQueryParams(), true, saveSuccessCallBack, failure);
}

function saveSuccessCallBack(transport, json){
	$timeAlert('操作成功！',5,function(){
		$dialog().hide();
	}, null, 2);
	//将返回的ＩＤ赋给第一列，并产生图标,同时删除保留的对象和恢复颜色
	var rightIds = transport.responseText.split(","), index = 0;
	if(window.saveType == 'saveAll'){//点击保存全部按钮
		for (var navTreeDivId in WCMRightHelper.storeRightsInfo){
			var imgTrDom = $("h_" + navTreeDivId);
			if(imgTrDom.getElementsByTagName("IMG").length <= 0){
				imgTrDom.getElementsByTagName("TD")[0].innerHTML = "<img  style='cursor:pointer;' src='../../images/auth/enable.gif' rightId='" + rightIds[index] + "'/>";
			}
			toggleRightClass('removeClassName', navTreeDivId, 'updateRow');
			delete WCMRightHelper.storeRightsInfo[navTreeDivId];
			index++;
		}
	}else if(window.saveType == 'saveCurrRow'){//点击保存当前行按钮
		if(window.lastClickTreeNodeA){
			var navTreeDivId = window.lastClickTreeNodeA.parentNode.id;
			var imgTrDom = $("h_" + navTreeDivId);
			if(imgTrDom.getElementsByTagName("IMG").length <= 0){
				imgTrDom.getElementsByTagName("TD")[0].innerHTML = "<img  style='cursor:pointer;' src='../../images/auth/enable.gif' rightId='" + rightIds[index] + "'/>";
			}
		}
	}
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

function failure(){
	alert('Ajax请求失败！');
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
			totalInputs[i].fireEvent('onclick');
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
		var htmlStr = '';
		if(type == 'notBlank' || type == 'all'){
			htmlStr += '<tr id="' + (id?id:(templateType + '_templater')) + '">';
			for (var i = 0; i < length; i++){
				var rightIndex = this.types[templateType][i].rightIndex;
				htmlStr += '<td><input type="checkbox" rightIndex="' + rightIndex + '" value="" id="' + (templateType + '_' + i) + '"></td>'
			}
			htmlStr += '</tr>';
		}
		if(type == 'blank' || type == 'all'){
			htmlStr += "<tr id='" + (id?id:("blank_" + templateType + "_templater")) + "'>";
			for (var i = 0; i < length; i++){
				htmlStr += '<td id="' + (templateType + '_' + i) + '">&nbsp;</td>'
			}
			htmlStr += '</tr>';			
		}
		return htmlStr;
	},
	/*
	*获得表头信息
	*/
	getHeadHTMLForType : function(type){
		type = type.toLowerCase();
		var htmlStr = '';
		var length = this.types[type].length;
		for (var i = 0; i < length; i++){
			var rightDefObj = this.types[type][i];
			htmlStr += "<td depends='" + rightDefObj.depends + "' similars='" 
					+ rightDefObj.similars + "' id='" + (type+"_" +i+"_h") + "'>" + rightDefObj.displayName + "</td>"
		}
		return htmlStr;
	}
});

var OperId = getParameter("OperId");
var OperType = getParameter("OperType");
window.lastTab = WCMRightHelper.defaultType;
Event.observe(window, 'load', function(){
	Event.observe('saveCurrRowId', 'click', saveCurrRowRights, false);
	Event.observe('saveAllRowsId', 'click', saveAllRowsRights, false);
	$(window.lastTab + "Radio").checked = true;

	if(OperType == '204'){
		$('imgTdTable').style.display = '';
		$('imgDescription').style.display = '';
	}
});