var m_bIsLocal = false;

//覆写动态获取指定节点的子节点HTML方法
com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(_elElementLi){
	if(m_bIsLocal)
		return getCurrentPath() + _elElementLi.id + ".html";

	var nPos = _elElementLi.id.indexOf("_");
	if(nPos <= 0){
		alert(wcm.LANG.nav_tree_alert_1 || "不符合规则！");
		return;
	}

	var sParentType	= _elElementLi.id.substring(0, nPos);
	var sParentId		= _elElementLi.id.substring(nPos+1);
	return "tree_html_creator.jsp?Type=0&ParentType=" + sParentType + "&ParentId=" + sParentId + "&forIndividual=1";
}

// ge gfc add @ 2007-1-10 9:32
//主动刷新当前focus的节点所对应的main显示
function refreshMain(oOptions){
    oOptions = oOptions || {};
	var oFocusElementA = com.trs.tree.TreeNav.oPreSrcElementA;
    //之所以调用下面这条语句，是为了防止oFocusElementA从节点树中删除之后，引入错误
    oFocusElementA = $(oFocusElementA.name);
	com.trs.tree.TreeNav.doActionOnClickA(null, oFocusElementA, oOptions["tabType"]);
}
function refreshFocused(){
	var info = getFocusedNodeInfo();
	if(info == null) {
		return; // just skip it
	}
	refresh(info.type, info.id);
}
function getFocusedNodeInfo(){
	if(com.trs.tree.TreeNav.oPreSrcElementA == null) {
		return null;
	}
	//else
	var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA.parentNode;
	if(oFocusElement == null || oFocusElement.id == null) {
		return null;
	}
	var nPos = oFocusElement.id.indexOf("_");
	if(nPos <= 0){
		alert(wcm.LANG.nav_tree_alert_1 || "不符合规则！");
		return;
	}
	var sParentType	= oFocusElement.id.substring(0, nPos);
	var sParentId	= oFocusElement.id.substring(nPos + 1);
	var result = {
		type: sParentType,
		id: sParentId,
		name: com.trs.tree.TreeNav.oPreSrcElementA.innerHTML,
		stype: findSiteType(oFocusElement),
		libType : findRootType(oFocusElement)
	}
	if(result.type == 'c') {
		result['chnltype'] = oFocusElement.getAttribute('ChannelType', 2) || 0;
	}
	return result;
}
function getFocusNodeInfo(props){
	var result = {};
	if(com.trs.tree.TreeNav.oPreSrcElementA == null) {
		return result;
	}
	//else
	var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA.parentNode;
	if(oFocusElement == null || oFocusElement.id == null) {
		return result;
	}
	var nPos = oFocusElement.id.indexOf("_");
	if(nPos <= 0){
		alert(wcm.LANG.nav_tree_alert_1 || "不符合规则！");
		return result;
	}
	var objType	= oFocusElement.id.substring(0, nPos);
	var objId	= oFocusElement.id.substring(nPos + 1);
	if(props['objType']) result['objType'] = objType;
	if(props['objId']) result['objId'] = objId;
	return result;
}

//ge gfc add @ 2007-7-3 9:48 记录一下点击前后的状态(认为加载完成后就是一次点击)
var m_bNodeClicked = true;

function findRootType(_element){
	if(_element == null) return null;
	while(_element.getAttribute("id")==null || _element.getAttribute("id").indexOf("r") == -1){
		_element = _element.parentNode;
	}
	return _element.getAttribute("id");
}

function findSiteType(_element){
    if(_element == null) return null;
    var sSiteType = _element.getAttribute("SiteType");
    if(sSiteType == null){
		var oParentNade = _element.parentNode;
		if( oParentNade == null
				|| (oParentNade.tagName != null && oParentNade.tagName == 'BODY') ) return null;
		sSiteType = findSiteType(oParentNade);
		if(sSiteType!=null){
	        _element.setAttribute("SiteType", sSiteType);
		}
    }
    return sSiteType;
}

function findFocusNodeSiteType(){
    var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA;
/*
	if( oFocusElement != null ){//当前处于库节点时得到ul
		oFocusElement = oFocusElement.parentNode.nextSibling || oFocusElement;
	}
*/
    return findSiteType(oFocusElement);
}

function findSiteId(_element){
    if(_element == null || _element.className.indexOf("TreeView") >= 0
            || _element.tagName == 'BODY')
        return null;
    if(_element.id && _element.id.startsWith("s_")){
        return _element.id.split("_")[1];
    }
    var _element = _element.parentNode;
    if(_element.tagName == "UL"){
        _element = Element.previous(_element);
    }
    return findSiteId(_element);
}

function findFocusNodeSiteId(){
    var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA;
    return findSiteId(oFocusElement);
}

function findFocusNodeChannelId(){
    var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA;
	var divNode = oFocusElement.parentNode;
	if(divNode && divNode.id && divNode.id.startsWith("c_")){
		return divNode.id.split("_")[1];
	}
	return 0;
}
/**
  *获取
 **/
function findPath(_sNodeType, _nNodeId){
	var sAction = "treenode_path_make.jsp?NodeType=" + _sNodeType + "&NodeId=" + _nNodeId;
	if(m_bIsLocal){
		sAction = getCurrentPath() + "path_" + _sNodeType + "_" + parseInt(_nNodeId, 10);
	}
	//prompt("findPath URL", sAction);
	var localAjaxRequest = new Ajax.Request(
                    sAction,
                    {method: 'get', parameters: "", asynchronous: false}
                    );
    if(localAjaxRequest.responseIsFailure()){
        return null;
    }
	return localAjaxRequest.transport.responseText;
}

function makeURLofGetChildrenHTML(_sNodePath){
	var sAction = "tree_html_creator.jsp?forIndividual=1&Type=1&NodeIds=" + _sNodePath;
	if(m_bIsLocal){
		sAction = getCurrentPath() + _sNodePath + ".html";
	}
	//prompt("makeURLofGetChildrenHTML URL", sAction);
	return sAction;
}


/**
 *
 * 刷新文字库的子站点列表
 *
 */
function refreshSiteType(_nSiteType, _sPath, callBack){
	refresh("r", _nSiteType, _sPath, callBack);
}

/**
 *
 * 刷新指定站点的一级子节点
 *
 */
function refreshSite(_nSiteId, _sPath, callBack){
	refresh("s", _nSiteId, _sPath, callBack);
}


/**
 *
 * 刷新指定栏目的一级子节点
 *
 */
function refreshChannel(_nChannelId, _sPath, callBack){
	refresh("c", _nChannelId, _sPath, callBack);
}

function getTypeByPathIndex(_nPathIndex){
	switch(_nPathIndex){
		case 0:
			return "r";
		case 1:
			return "s";
		default:
			return "c";
	}
}

function refreshNode(_sNodeType, _nNodeId, _fCallBack){
	var dom = $(_sNodeType + "_" + _nNodeId);
	if(!dom) return;
	var eTmp = com.trs.tree.TreeNav._getParentElement(dom);
	_fCallBack = _fCallBack || Ext.emptyFn;
	com.trs.tree.TreeNav.reloadChildren(eTmp, true, _fCallBack);
}

function isFocus(_sNodeType, _nNodeId){
	var linkNode = com.trs.tree.TreeNav.oPreSrcElementA;
	if(!linkNode) return false;
	var divNode = linkNode.parentNode;
	return divNode.id == (_sNodeType + "_" + _nNodeId)
}

function clickParent(_sNodeType, _nNodeId){
	var dom = $(_sNodeType + "_" + _nNodeId);
	if(!dom) return;
	var eTmp = com.trs.tree.TreeNav._getParentElement(dom);
	if(eTmp) eTmp.getElementsByTagName("a")[0].fireEvent("onclick");
}

/**
 *
 * 刷新指定节点的一级子节点
 *
 */
function refresh(_sNodeType, _nNodeId, _sNodePath, callBack, _bForceFind){
    var focusNodeName = "r_0";
    if(com.trs.tree.TreeNav.oPreSrcElementA){
        var focusNodeName = com.trs.tree.TreeNav.oPreSrcElementA.name.substring(1);
    }
    var focusNodeNameInfo = focusNodeName.split("_");
    var _oldCallBack = callBack;
    callBack = function(){//同步自定义栏目节点
        focus(focusNodeNameInfo[0], focusNodeNameInfo[1], null, _oldCallBack);
    }
    //*/
	//判断指定的节点是否存在
	var sNodeHTMLElementId = _sNodeType + "_" + parseInt(_nNodeId, 10);
	var oNodeHTMLElement = $(sNodeHTMLElementId);

	//=======================================
	//====1.如果存在,直接刷新子节点===========
	//=======================================
	if( oNodeHTMLElement != null){
		//执行刷新操作
		com.trs.tree.TreeNav.reloadChildren(sNodeHTMLElementId, false, callBack, sNodeHTMLElementId);
//        (callBack || Ext.emptyFn)(sNodeHTMLElementId);
//        delete callBack;
		return;
	}

	//=========================================================================================
	//====2.如果不存在,设置了强行装载，那么从服务器获取数据，初始化再聚焦======================
	//=========================================================================================
	//ge gfc modify @ 2007-5-24 15:19 刷新节点时不进行聚焦操作
	//reloadChildrenAndFocus(_sNodeType, _nNodeId, true, _sNodePath, true, callBack);
	if(_bForceFind){
		reloadChildrenAndFocus(_sNodeType, _nNodeId, true, _sNodePath, false, callBack);
	}
}

function reloadChildrenAndFocus(_sNodeType, _nFocusNodeId, _bReloadFocusChildren, _sNodePath, _bDoFocus, callBack){
	reloadNode(_sNodeType, _nFocusNodeId, _bReloadFocusChildren, _sNodePath, _bDoFocus, callBack);
}

function reloadNode(_sNodeType, _nFocusNodeId, _bReloadFocusChildren, _sNodePath, _bDoFocus, callBack){
	//树尚未初始化完，等待再发出请求
	if(!com.trs.tree.TreeNav.loaded){
        var fMethod = arguments.callee, oScope = this, args = arguments;
        setTimeout(function(){fMethod.apply(oScope, args)}, 20);
        return;
	}
	var sNodeHTMLElementId = _sNodeType + "_" + parseInt(_nFocusNodeId, 10);

	//由于还没有理清楚这个逻辑，先特殊处理一下库的Focus
	if(!_bReloadFocusChildren && _sNodeType=="r"){
		com.trs.tree.TreeNav.focus(sNodeHTMLElementId);
        (callBack || Ext.emptyFn)(sNodeHTMLElementId);
        delete callBack;
		return;
	}

	//没有传入NodePath,需要从服务器端读取NodePath
	var sNodePath = _sNodePath;
	if(_sNodeType.toLowerCase() == "r"){
		sNodePath = "0";
	}
	if(sNodePath == null || sNodePath == 'null' || typeof sNodePath == "undefined" || sNodePath.trim().length<=0){
		sNodePath = findPath(_sNodeType, _nFocusNodeId);
		if(sNodePath == null || sNodePath.trim().length<=0){
			alert(String.format("指定的对象[{0}.{1}]可能已经被删除!栏目树的同步刷新操作不能进行!",_sNodeType,_nFocusNodeId));
            $("ar_0").click();
			return;
		}
	}
	//依次判断路径
	var sNeedLoadPath = "";
	var pNodePaths = sNodePath.split(",");
	var nPathLevel = pNodePaths.length;
	//判断最后一个节点是否就是自己
	if(pNodePaths[pNodePaths.length-1].trim() == _nFocusNodeId
	&& getTypeByPathIndex(pNodePaths.length-1) == _sNodeType)
		nPathLevel = nPathLevel - 1;
	var sLastExistNodeId = null;
	var sTempNodeId = null;
	for(var i=0; i<nPathLevel; i++){
		pNodePaths[i] = pNodePaths[i].trim();
		sTempNodeId = getTypeByPathIndex(i) + "_" + parseInt(pNodePaths[i], 10);
		if( $( sTempNodeId )  != null){
			sLastExistNodeId = sTempNodeId;
			sNeedLoadPath = sTempNodeId;
			continue;
		}

		for(; i<nPathLevel; i++){
			pNodePaths[i] = pNodePaths[i].trim();
			sNeedLoadPath += "," + getTypeByPathIndex(i) + "_" + parseInt(pNodePaths[i], 10);
		}
	}
	if(_bReloadFocusChildren)
		sNeedLoadPath += "," + sNodeHTMLElementId;

	//将产生的数据更新到指定元素上同时执行聚焦
	if(sLastExistNodeId == null){
		if(Ext.isDebug()){
			alert(String.format("Root节点元素[Id=r_{0}]竟然都不存在？？？\n(reloadChildrenAndFocus)nPathLevel:{1}; sTempNodeId:{2}; NodeElement:{3};\n相关HTML代码：{4}",pNodePaths[0],nPathLevel,sTempNodeId,$(sTempNodeId),$("divTreeRegion").innerHTML));
			//alert("指定的路径["+sNodePath+"]在树中都不存在???");
		}
		return;
	}
	//var oOnUpdateComplete		= (_bDoFocus == true) ? com.trs.tree.TreeNav.focus : null;
    if(_bDoFocus){
        oOnUpdateComplete = function(){
            var result = com.trs.tree.TreeNav.focus.apply(com.trs.tree.TreeNav, arguments);
            if(result == false){
                var strMsg = wcm.LANG.nav_tree_alert_8 || "不能定位到指定树节点！造成这种现象的原因可能是：\n";
                strMsg += (wcm.LANG.nav_tree_alert_9 || "\t1)当前要定位的树节点已被其他用户删除.\n");
                strMsg += (wcm.LANG.nav_tree_alert_10 || "\t2)选择了自定义站点过滤条件，但当前要选中的站点或站点下的栏目不包含在自定义站点中.\n");
                strMsg += (wcm.LANG.nav_tree_alert_11 || "如果为2，你可以先取消自定义站点的选择，然后再次尝试执行此操作。\n");
                alert(strMsg);
                setTimeout(function(){
                    if(window.ProcessBar && ProcessBar.isProcessing()){
                        setTimeout(arguments.callee, 10);
                    }else{
                        //防止com.trs.tree.TreeNav.oPreSrcElementA存在但被其他用户删除的情况
                        if(com.trs.tree.TreeNav.oPreSrcElementA && $(com.trs.tree.TreeNav.oPreSrcElementA.name)){
                            var nameInfo = com.trs.tree.TreeNav.oPreSrcElementA.name.substr(1).split("_");
                            focus(nameInfo[0], nameInfo[1], null, refreshMain);
                        }else{//com.trs.tree.TreeNav.oPreSrcElementA存在但被其他用户删除的情况，定位到树根
                            $("ar_0").fireEvent('onclick');
                        }
                    }
                }, 200);
            }else{
                (callBack || Ext.emptyFn).apply(null, arguments);
                delete callBack;
            }
        }
    }else{
         oOnUpdateComplete = function(){
            (callBack || Ext.emptyFn).apply(null, arguments);
            delete callBack;
        }
    }
	var oOnUpdateCompleteArgs	= sNodeHTMLElementId;
	//首先需要确保指定的父结点有子结点元素
	com.trs.tree.TreeNav.ensureTopChildElementExist(sLastExistNodeId);
	//发出请求，更新子结点
	com.trs.tree.TreeNav.updateNodeChildrenHTML(sLastExistNodeId,
		makeURLofGetChildrenHTML(sNeedLoadPath),
		oOnUpdateComplete, oOnUpdateCompleteArgs);
}
function reloadChildren(_sNodeType, _nNodeId, callBack){
	//树尚未初始化完，等待再发出请求
	if(!com.trs.tree.TreeNav.loaded){
		setTimeout("reloadChildren('"+_sNodeType+"', '"+_nNodeId+"', "+callBack+")", 20);
		return;
	}

	var sNodeHTMLElementId = _sNodeType + "_" + parseInt(_nNodeId, 10);
	//判断指定节点是否存在，如果不存在需要分级载入一条路径的HTML
	var sNeedLoadChildrenParentNodeIds = sNodeHTMLElementId;
	var sLastExistNodeId = sNodeHTMLElementId;
	if($(sNodeHTMLElementId) == null){
		alert(wcm.LANG.nav_tree_alert_12 || "尚未实现");
		return;
		//获取路径
		/*var sNodePath = findPath(_sNodeType, _nNodeId);
		if(sNodePath == null || sNodePath.trim().length<=0){
			alert("指定的对象["+_sNodeType+"."+_nFocusNodeId+"]可能已经被删除!栏目树的同步刷新操作不能进行!");
			return;
		}*/
	}


	//将产生的数据更新到指定元素上
	var oOnUpdateComplete = function(){
            (callBack || Ext.emptyFn).apply(null, arguments);
			//如果移动的是当前，需要聚焦到当前节点
            delete callBack;
    }

	var oOnUpdateCompleteArgs	= sNodeHTMLElementId;
	//首先需要确保指定的父结点有子结点元素
	com.trs.tree.TreeNav.ensureTopChildElementExist(sLastExistNodeId);
	//发出请求，获取到内容，更新到指定节点的子结点区域
	com.trs.tree.TreeNav.updateNodeChildrenHTML(sLastExistNodeId,
		makeURLofGetChildrenHTML(sNeedLoadChildrenParentNodeIds),
		oOnUpdateComplete, oOnUpdateCompleteArgs);
}




/**
 *  定位到指定站点
 **/
function focusSite(_nSiteId, _sNodePath, callBack){
	focus("s", _nSiteId, _sNodePath, callBack);
}

/**
 *  定位到指定栏目
 **/
function focusChannel(_nChannelId, _sNodePath, callBack){
	focus("c", _nChannelId, _sNodePath, callBack);
}

/**
 *
 * 聚焦到指定节点
 *
 */
function focus(_sNodeType, _nNodeId, _sNodePath, callBack){
	//ge gfc add @ 2007-7-3 9:48 记录一下点击前后的状态
	m_bNodeClicked = true;

	//判断指定的节点是否存在
	var sNodeHTMLElementId = _sNodeType + "_" + parseInt(_nNodeId, 10);
	var oNodeHTMLElement = $(sNodeHTMLElementId);

	//=======================================
	//====1.如果存在,直接聚焦到节点===========
	//=======================================
	if( oNodeHTMLElement != null){
		//执行刷新操作
		var oTreeViewContainer = $('TreeViewContainer');
//		var nLeft = oTreeViewContainer.scrollLeft;
		com.trs.tree.TreeNav.focus(oNodeHTMLElement);
		if(oTreeViewContainer)oTreeViewContainer.scrollLeft -= 40;
        (callBack || Ext.emptyFn)(sNodeHTMLElementId);
        delete callBack;
		return;
	}

	//=========================================================
	//====2.如果不存在,需要获取数据，初始化再聚焦================
	//=========================================================
	reloadChildrenAndFocus(_sNodeType, _nNodeId, false, _sNodePath, true, callBack);
}

/**
 *  移动栏目后做的操作
 **/
function doAfterMove(_nSrcChannelIds, _nTargetId, _bIsSite, _bNotFocus, callBack){
    if(!_nSrcChannelIds || !_nTargetId){
        alert(wcm.LANG.nav_tree_alert_13 || '没有指定源或目标id');
        return;
    }
    var focusNodeName = "r_0";
    if(com.trs.tree.TreeNav.oPreSrcElementA){
        var focusNodeName = com.trs.tree.TreeNav.oPreSrcElementA.name.substring(1);
    }
    var focusNodeNameInfo = focusNodeName.split("_");
	var arChnlIds = _nSrcChannelIds.split(',');
    for (var i = 0; i < arChnlIds.length; i++){
         //先remove掉现有的元素，然后再进行操作
        com.trs.tree.TreeNav.removeNode("c_" + arChnlIds[i], _bNotFocus);
    }
    var perfix = _bIsSite ? 's' : 'c';
    refresh(perfix, _nTargetId, null, function(){
        focus(focusNodeNameInfo[0], focusNodeNameInfo[1], null, callBack);
    });
}

/**
 *  删除栏目做的操作
 **/
function doAfterDelChannel(_sChannelIds){
	var pChannelId = _sChannelIds.split(",");
	for(var i=0; i<pChannelId.length; i++)
		com.trs.tree.TreeNav.removeNode("c_" + pChannelId[i]);
}

/**
 *  删除站点做的操作
 **/
function doAfterDelSite(_sSiteIds){
	var pSiteId = _sSiteIds.split(",");
	for(var i=0; i<pSiteId.length; i++)
		com.trs.tree.TreeNav.removeNode("s_" + pSiteId[i]);
}


function doAfterAddChannel(_nChannelId, _bNotFocus){
	//定位到指定的节点，同时刷新同级节点列表
	reloadChildrenAndFocus("c", _nChannelId, false, null, (_bNotFocus != true));
}

function doAfterAddSite(_nSiteId){
	//定位到指定的节点，同时刷新同级节点列表
	reloadChildrenAndFocus("s", _nSiteId, false, null);
}


/**
 *  修改栏目后做的操作
 *	 TODO 最好区分一下修改的内容（名称/排序/其它）
 **/
function doAfterModifyChannel(_nChannelId, _bNotFocus, callBack){
	//定位到指定的节点，同时刷新同级节点列表
    var focusNodeName = "r_0";
    if(com.trs.tree.TreeNav.oPreSrcElementA){
        var focusNodeName = com.trs.tree.TreeNav.oPreSrcElementA.name.substring(1);
    }
    var focusNodeNameInfo = focusNodeName.split("_");
    reloadChildrenAndFocus("c", _nChannelId, false, null, !_bNotFocus, function(){
        if(_bNotFocus){
            focus(focusNodeNameInfo[0], focusNodeNameInfo[1], null, callBack);
        }else{
            (callBack || Ext.emptyFn)();
        }
    });
}

/**
 *  修改站点后做的操作
 *	 TODO 最好区分一下修改的内容（名称/排序/其它）
 **/
function doAfterModifySite(_nSiteId, _bDoFocus, callBack){
	//定位到指定的节点，同时刷新同级节点列表
    var focusNodeName = "r_0";
    if(com.trs.tree.TreeNav.oPreSrcElementA){
        var focusNodeName = com.trs.tree.TreeNav.oPreSrcElementA.name.substring(1);
    }
    var focusNodeNameInfo = focusNodeName.split("_");
    reloadChildrenAndFocus("s", _nSiteId, false, null, _bDoFocus, function(){
        if(!_bDoFocus){
            focus(focusNodeNameInfo[0], focusNodeNameInfo[1], null, callBack);
        }else{
            (callBack || Ext.emptyFn)();
        }
    });
}

function doAfterModifyFocusNode(_bDoFocus, callBack){
    if(com.trs.tree.TreeNav.oPreSrcElementA){
        var focusNodeName = com.trs.tree.TreeNav.oPreSrcElementA.name.substring(1);
    }
    var focusNodeNameInfo = focusNodeName.split("_");
    if(focusNodeNameInfo[0] == 'c'){
        doAfterModifyChannel(focusNodeNameInfo[1], !_bDoFocus, callBack);
    }else{
        doAfterModifySite(focusNodeNameInfo[1], _bDoFocus, callBack);
    }
}

function getCurrElementInfo(){
	var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA;
	if( oFocusElement != null ){
		oFocusElement = oFocusElement.parentNode;
	}
	var nChannelType = oFocusElement == null ? "0" : (oFocusElement.getAttribute("ChannelType") || "0");
	return {
		"RightValue" : oFocusElement == null ? "0" : oFocusElement.getAttribute("RV"),
		"ChannelType" : nChannelType
	};
}

//获得指定节点（未指定时当前焦点节点）的路径信息 hxj
function getPathNodeInfo(oPreSrcElementA){
    var path = '';
    var nodeType = '';
    var nodeId = '';
    var rightValue = '';
    var channelType = '';
    var oCurrentElement = oPreSrcElementA || com.trs.tree.TreeNav.oPreSrcElementA;
    if(oCurrentElement) {
        oCurrentElement = oCurrentElement.parentNode;
        var infoArray = oCurrentElement.id.split("_");
        nodeType = infoArray[0] || 0;
        nodeId = infoArray[1];
        rightValue = oCurrentElement.getAttribute("RV") || 0;
        channelType = oCurrentElement.getAttribute("ChannelType") || 0;
    }

    while(oCurrentElement){
        path = "\\" + oCurrentElement.innerText.trim() + path;
        if(oCurrentElement.getAttribute("isRoot") == true){
            break;
        }
        oCurrentElement = oCurrentElement.parentNode.previousSibling;
    }
    return {path:path, nodeType:nodeType, nodeId:nodeId, rightValue:rightValue, channelType:channelType};
}

function getParentNodeInfo(divNode){
    var node = divNode;
    do{
        node = node.parentNode;
        if(Element.hasClassName(node,'TreeView')){
            return null;;
        }
        node = Element.previous(node);
    }while(node.tagName.toUpperCase() != 'DIV' && !node.getAttribute("classPre"));

    if(node){
        var idInfo = node.id.split("_");
        return {
			prefix		: idInfo[0],
			id			: idInfo[1],
			rightValue	: node.getAttribute("RV") || 0,
			channelType	: node.getAttribute("ChannelType") || 0,
			isVirtual	: node.getAttribute("IsV")
        };
    }
    return null;
}
//判断当前聚焦节点是否在自定义栏目树中
function isFocusInIndividuation(){
	var oFocusElementA = com.trs.tree.TreeNav.oPreSrcElementA;
    if(oFocusElementA == null) return null;
    var focusAArray = document.getElementsByName(oFocusElementA.name);
    if(focusAArray == null) return null;
    if(focusAArray.length >= 2){
        return Element.hasClassName(focusAArray[1], "Selected");
    }
    oFocusElementA = focusAArray[0];
    var classPre = oFocusElementA.getAttribute("ClassPre");
    while(!classPre || classPre.indexOf("SiteType") < 0){
        if(oFocusElementA.className.indexOf("TreeView") >= 0){
            break;
        }
        oFocusElementA = oFocusElementA.parentNode;
        if(oFocusElementA.tagName == "UL"){
            oFocusElementA = Element.previous(oFocusElementA);
        }
        classPre = oFocusElementA.getAttribute("ClassPre");
    }
    return (oFocusElementA.id || oFocusElementA.name).indexOf("i") >= 0;
}
function refreshTree(callBack){
	//获取当前Focus对象的ID
	var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA;
	if( oFocusElement != null ){
		oFocusElement = oFocusElement.parentNode;
	}
	var sFocusId =  null;
	if(oFocusElement != null) sFocusId = oFocusElement.id;
    var isInViduation = isFocusInIndividuation();

	//清空所有的树
    var aTreeRoot = [0, 1, 2, 4];
	for(var i=0; i<aTreeRoot.length; i++){
		if($("r_" + aTreeRoot[i])){
			com.trs.tree.TreeNav.clearChildren("r_" + aTreeRoot[i]);
		}
	}
    com.trs.tree.TreeNav.clearChildren("i_" + 3);
    if(!isInViduation){//当前焦点节点不在自定义栏目下
        //重新装载上次Focus的节点
        if(sFocusId != null){
            var nPos = sFocusId.indexOf("_");
            if(nPos <= 0){
                alert(String.format("Id[{0}]规则不一致",sFocusId));
                return;
            }
            var sNodeType = sFocusId.substring(0, nPos);
            var sNodeId = sFocusId.substring(nPos+1);
            focus(sNodeType, sNodeId, null, callBack);
        }
    }else{//直接定位自定义栏目节点
		if(!$('i_3'))return;
        $('i_3_children').onpropertychange = function(event){
            event = event || window.event;
            if(event.propertyName.toLowerCase() == "innerhtml"
                    && $('i_3_children').innerHTML != "loading..."){
                $('i_3_children').onpropertychange = null;
                var focusArray = document.getElementsByName(sFocusId);
                if(focusArray){
                    getFirstHTMLChild(focusArray[focusArray.length-1]).click();
                }
                (callBack || Ext.emptyFn)();
            }
        };
        $("i_" + 3).click();
    }
}

var myActualTop = (top.actualTop||top);
function isAdmin(){
	return $MsgCenter.getActualTop().global_IsAdmin;
}
// ge gfc add @ 2007-1-10 10:31
// 保留现场信息, 并在刷新时加载自动focus到保存的现场
Event.observe(window, 'load', function(){
	if(myActualTop != self && myActualTop._FOCUSED_NODE_INFO != null) {
		var info = myActualTop._FOCUSED_NODE_INFO;
		focus(info.type, info.id);
	}
    //init the root right value
    if(isAdmin()){
        var rightValue = "1111111111111111111111111111111111111111111111111111111111111111";
    }else{
        var rightValue = "0";
    }
    var aRoot = [0, 1, 2, 4];
    for(var index = 0; index < aRoot.length; index++){
        if($('r_' + aRoot[index]))$('r_' + aRoot[index]).setAttribute("RV", rightValue);
    }
});
Event.observe(window, 'unload', function(){
	if(myActualTop != self) {
		var info = getFocusedNodeInfo();
		if(info == null) {
			return; // just skip it
		}
		myActualTop._FOCUSED_NODE_INFO = info;
	}
}, false);

com.trs.tree.TreeNav._getObjectTypeId = function(eCurrNode){
	var nPos = eCurrNode.id.indexOf("_");
	if(nPos <= 0){
		alert(String.format("Id[{0}]规则不一致",sFocusId));
		return {};
	}
	return {Type:eCurrNode.id.substring(0, nPos),ObjectId:eCurrNode.id.substring(nPos+1)};
}

function isChannelCanDrop(_sSrcChannelId, _elElementA){
	var eCurrParent = _elElementA.parentNode;
	var oTypeId = com.trs.tree.TreeNav._getObjectTypeId(eCurrParent);
	var sType = oTypeId["Type"];
	var sObjectId = oTypeId["ObjectId"];
	if(sType=='r'||sType=='i')return false;
	//var bIsVirtual = eCurrParent.getAttribute("IsV",2)=='1';
	var nChnlType = eCurrParent.getAttribute("ChannelType",2) || 0;
	var bIsVirtual = nChnlType != 0;
	if(bIsVirtual)return false;
	var iCurrSiteType = findFocusNodeSiteType();
	var iSiteType = findSiteType(eCurrParent);
	if(iCurrSiteType!=iSiteType)return false;
	var iCurrSiteId = findFocusNodeSiteId();
	var iSiteId = findSiteId(eCurrParent);
	if(iCurrSiteId!=iSiteId)return false;
	if(_sSrcChannelId.indexOf(',')==-1){//单栏目
	// 自身,环路等问题
		if(sObjectId==_sSrcChannelId)return false;
		if(isSonOf(_sSrcChannelId,eCurrParent))return false;
		if(isAncestorOf(_sSrcChannelId,eCurrParent))return false;
	}
	return true;
}
function isSonOf(_nChannelId,_eCurrNode){
	var oTopChildElement = com.trs.tree.TreeNav._getTopChildElement(_eCurrNode);
	if(oTopChildElement!=null){
		var eChildrens = oTopChildElement.childNodes;//getElementsByTagName("DIV");
		for(var i=0;i<eChildrens.length;i++){
			if(eChildrens[i].tagName!='DIV')continue;
			if(com.trs.tree.TreeNav._getObjectTypeId(eChildrens[i])["ObjectId"]==_nChannelId){
				return true;
			}
		}
	}
	return false;
}
function isAncestorOf(_nChannelId,_eCurrNode){
	var eTmp = com.trs.tree.TreeNav._getParentElement(_eCurrNode);
	while(eTmp){
		if(com.trs.tree.TreeNav._getObjectTypeId(eTmp)["ObjectId"]==_nChannelId){
			return true;
		}
		eTmp = com.trs.tree.TreeNav._getParentElement(eTmp);
	}
	return false;
}
function isAncestor(objType, objId, dom){
	if(!dom) dom = com.trs.tree.TreeNav.oPreSrcElementA.parentNode;
	while(dom){
		var info = com.trs.tree.TreeNav._getObjectTypeId(dom);
		if(info["ObjectId"]==objId && info["Type"] == objType){
			return true;
		}
		dom = com.trs.tree.TreeNav._getParentElement(dom);
	}
	return false;
}
function isDocumentCanDrop(_sObjectId, _sFolderId, _elElementA){
	var eCurrParent = _elElementA.parentNode;
	var oTypeId = com.trs.tree.TreeNav._getObjectTypeId(eCurrParent);
	var sType = oTypeId["Type"];
	var sObjectId = oTypeId["ObjectId"];
	if(sType=='r'||sType=='s'||sType=='i')return false;
	var bIsVirtual = eCurrParent.getAttribute("IsV",2)=='1';
	if(bIsVirtual){
		var nChannelType = eCurrParent.getAttribute("ChannelType",2);
		if(nChannelType!=1&&nChannelType!=2)return false;
	}
	var iCurrSiteType = findFocusNodeSiteType();
	var iSiteType = findSiteType(eCurrParent);
	if(iCurrSiteType!=iSiteType)return false;
	var arr = (_sFolderId||'').split(',');
	var bIsSingleChannel = true;
	for(var i=1;i<arr.length;i++){
		if(arr[i]!=arr[0]){
			bIsSingleChannel = false;
			break;
		}
	}
	if(bIsSingleChannel){//单栏目,不能移动到自身
		if(sObjectId==_sFolderId)return false;
	}
	return true;
}
function isCanDrop(_elElementA){
	var oAcross = top.DragAcross;
	return oAcross&&
	((oAcross.ObjectType==605
		&&isDocumentCanDrop(oAcross.ObjectId,oAcross.FolderId,_elElementA))||
	(oAcross.ObjectType==101
		&&isChannelCanDrop(oAcross.ObjectId,_elElementA)));
}
com.trs.tree.TreeNav.doActionOnMouseA = function(_event,_elElementA,_bOver){
	if(_bOver){
		var eCurrParent = _elElementA.parentNode;
		if(eCurrParent == null || eCurrParent.id == null) {
			return;
		}
		var nPos = eCurrParent.id.indexOf("_");
		if(nPos <= 0){
			alert(String.format("Id[{0}]规则不一致",eCurrParent.id));
			return;
		}
		var sType = eCurrParent.id.substring(0, nPos);
		var sObjectId = eCurrParent.id.substring(nPos+1);
		if(!top.DragAcross)top.DragAcross = {};
		Object.extend(top.DragAcross,{
			TargetFolderId : sObjectId ,
			TargetFolderType : (sType=='s')?103:101
		});
	}
	else{
		if(top.DragAcross){
			Object.extend(top.DragAcross,{
				TargetFolderId : null ,
				TargetFolderType : null
			});
		}
	}
	return true;
}