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
			Event.stop(event);
			return false;
		case 'DIV':
			clickTreeNode(Element.first(srcElement));
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
		title : ''
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
	//构造权限标识节点
	var newImgTemplater = $('img_templater');
	var eNewImg = newImgTemplater.cloneNode(true);
	var oldImgObj = $('h_' + oldRow.getElementsByTagName("div")[0].id);
	oldImgObj.parentNode.insertBefore(eNewImg, oldImgObj.nextSibling);
	eNewImg.id = 'h_' + newRowInfoObj.id;
	if(newRowInfoObj.rightId && newRowInfoObj.rightId != '0'){
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
		var eNewRightTemplater = $(templateType);
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
	var imgs = newRightRow.getElementsByTagName("span");
	var currRowRight = Element.first(Element.first(newTreeNodeRow)).getAttribute("rightValue");

	for (var i = 0; i < imgs.length; i++){
		imgs[i].id = newRightRow.id + "_" + imgs[i].id;
		if(wcm.AuthServer.checkRight(currRowRight, imgs[i].getAttribute("rightIndex"))){
			imgs[i].className = "hasRight";
		}
	}
}

//设置导航树节点及innerHTML信息
function setTreeNodeHTML(newRowObj, newRowInfoObj, parentRowInfoObj){
	var treeNodeTdObj = Element.first(newRowObj);
	var nodeClass = newRowInfoObj.classPre;
	nodeClass += newRowInfoObj.hasChild == 'true' ? "Folder" : "Page";
	var sHtml = [
		'<div ',
		' class="', nodeClass, '"',
		' id="', newRowInfoObj.id, '"',
		' title="', newRowInfoObj.title, '"',
		' rightValue="', newRowInfoObj.rightValue, '"',
		' hasChild="', newRowInfoObj.hasChild, '"',
		' classPre="', newRowInfoObj.classPre, '"',
		'>',
			'<a href="#">', newRowInfoObj.desc, '</a>',
		'</div>'
	].join("");
	treeNodeTdObj.innerHTML = sHtml;
	//if(window.lastClickTreeNode.parentNode.className.indexOf("siteFolder") >= 0){
	if(Element.first($(parentRowInfoObj.id)).className.indexOf("siteFolder") >= 0){	
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
	Ext.Msg.error(wcm.LANG.AUTH_ALERT_3||'Ajax请求失败！');
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
				var rightIndex = this.types[templateType][i].rightIndex;
				aResult.push(
					'<td style="text-align:center;">',
						'<span class="noRight"', 
						' rightIndex="' + rightIndex + '"',
						' id="' + (templateType + '_' + i) + '"',
						'>&#160;</span>',
					'</td>'
				);
			}
			aResult.push('</tr>');
		}
		if(type == 'blank' || type == 'all'){
			aResult.push('<tr id="' + (id?id:("blank_" + templateType + "_templater")) + '">');
			for (var i = 0; i < length; i++){
				aResult.push('<td id="' + (templateType + '_' + i) + '">&nbsp;</td>');
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
		var length = this.types[type].length;
		var aResult = [];
		for (var i = 0; i < length; i++){
			var rightDefObj = this.types[type][i];
			aResult.push('<td id="' + (type+"_" +i+"_h") + '" title="' + rightDefObj.desc + '">' + rightDefObj.displayName + '</td>');
		}
		return aResult.join("");
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
		Element.update("tempChangeArea", "");
		return SitesLoadMgr.restoreIds(sHtml);
	},

	loadSites : function(){
		var params = "isView=1&ViewerIsCurrUser=1&InRightSetPage=1&ParentType=r&ParentId=-1&OperatorId="+OperId+"&OperatorType="+OperType;
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
	removePreSiteRows();
	var nSiteId = selObj.value;
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
