function dealWithClickTable(event){//处理表格的单击事件
	event = window.event || event;
	var srcElement = Event.element(event);   
	switch(srcElement.tagName.toUpperCase()){
		case 'A':
			selectCurrRow(srcElement);
			Event.stop(event);
			return false;
		case 'DIV':
			clickTreeNode(getFirstHTMLChild(srcElement));
			Event.stop(event);
			return false;
	}
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
	var params = "InRightSetPage=1&isView=1&ParentType=" + idInfo[0] + "&ParentId=" + idInfo[1] + "&OperatorId="+OperId+"&OperatorType="+OperType;
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
			rightId		: rowSpanDomObj.getAttribute("RightId"),
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
		eNewImg.getElementsByTagName("TD")[0].innerHTML = "<img src='../../images/auth/enable.gif' rightId='" + newRowInfoObj.rightId + "'/>";
	}

	//添加导航树列
	var eNewNavTemplater = $('nav_templater');
	var eNewNav = eNewNavTemplater.cloneNode(true);
	oldRow.parentNode.insertBefore(eNewNav, oldRow.nextSibling);
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
	var imgs = newRightRow.getElementsByTagName("IMG");
	var currRowRight = getFirstHTMLChild(getFirstHTMLChild(newTreeNodeRow)).getAttribute("rightValue");

	for (var i = 0; i < imgs.length; i++){
		imgs[i].id = newRightRow.id + "_" + imgs[i].id;
		if(isAccessable4WcmObject(currRowRight, imgs[i].getAttribute("rightIndex"))){
			imgs[i].src = '../../images/auth/hasright.gif';
		}
	}
}

//设置导航树节点及innerHTML信息
function setTreeNodeHTML(newRowObj, newRowInfoObj, parentRowInfoObj){
	var treeNodeTdObj = getFirstHTMLChild(newRowObj);
	var nodeClass = newRowInfoObj.classPre;
	nodeClass += newRowInfoObj.hasChild == 'true' ? "Folder" : "Page";
	var htmlStr = '<div';                
	htmlStr += " class='" + nodeClass + "' id='" + newRowInfoObj.id + "'";
	htmlStr += " title='" + newRowInfoObj.title + "' rightValue='" + newRowInfoObj.rightValue + "'";
	htmlStr += " hasChild='" + newRowInfoObj.hasChild + " classPre='" + newRowInfoObj.classPre + "'";
	htmlStr += "><a href='#'>" + newRowInfoObj.desc + "</a></div>";
	treeNodeTdObj.innerHTML = htmlStr;
	if(window.lastClickTreeNode.parentNode.className.indexOf("siteFolder") >= 0){
		treeNodeTdObj.style.paddingLeft = parentRowInfoObj.paddingLeft + 5 + "px";
	}else{
		treeNodeTdObj.style.paddingLeft = parentRowInfoObj.paddingLeft + 20 + "px";
	}
}

function showType(type){//显示当前选中的权限列表
	$(type + "Radio").checked = true;
	if(window.lastTab){
		Element.hide(window.lastTab + "RightTab");
	}
	window.lastTab = type;
	Element.show(type + "RightTab");
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
	*templateType:site,channel,template,document
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
				htmlStr += '<td><img src="../../images/auth/noright.gif" rightIndex="' + rightIndex + '" id="' + (templateType + '_' + i) + '"></td>'
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
			htmlStr += "<td id='" + (type+"_" +i+"_h") + "'>" + rightDefObj.displayName + "</td>"
		}
		return htmlStr;
	}
});

var OperId = getParameter("OperId");
var OperType = getParameter("OperType");
window.lastTab = WCMRightHelper.defaultType;
Event.observe(window, 'load', function(){
	$(window.lastTab + "Radio").checked = true;

	if(OperType == '204'){
		$('imgTdTable').style.display = '';
		$('imgDescription').style.display = '';
	}
});
