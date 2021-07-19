<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.cms.content.WCMSystemObject" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	int objType = currRequestHelper.getInt("objType", 0);
	int objId = currRequestHelper.getInt("objId", 0);
	String sClassPre = "",sId="",sDesc="";
	int nSiteType = 0;
	if(objType != WCMSystemObject.OBJ_TYPE){
		BaseChannel obj = (BaseChannel)BaseObj.findById(objType, objId);
		if(obj == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("nav_tree_select_node.object.not.found", "没有找到ID为{0}的栏目"), new String[]{String.valueOf(objId), WCMTypes.getLowerObjName(objType)}));
		}
		WebSite site = obj.getSite();
		boolean bIsSite = obj.isSite();
		sClassPre = bIsSite ? "site" : "channel";
		sId = (bIsSite ? "s" : "c") + "_" + obj.getId();
		nSiteType = site.getType();
		sDesc = CMyString.transDisplay(obj.getDesc());
	}else{
		sClassPre = "SiteType" + objId;
		sId = "r_" + objId;
		nSiteType = objId;
		sDesc = getDesc(objId);
	}
	out.clear();
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="nav_tree_select.jsp.title">Navigator Tree</title>
	<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
	<link href="../js/source/wcmlib/com.trs.tree/resource/TreeNav.css" rel="stylesheet" type="text/css" />
	<link href="nav_tree.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.TreeView{border:0px;overflow:auto;width:97%;height:100%;}	
	.TreeView div.siteRoot{
		background:transparent url(../images/nav_tree/bg.png) 1px -215px no-repeat;	
		line-height:26px; 
		text-indent:22px;
	}
	.TreeView div.siteRootOpened{
		background:transparent url(../images/nav_tree/bg.png) 1px -191px no-repeat;	
		line-height:26px; 
		text-indent:22px;
	}
	.TreeView div.channelRoot{
		background:transparent url(../images/nav_tree/bg.png) 9px -481px no-repeat;	
		text-indent:32px;
	}
	.TreeView div.channelRootOpened{
		background:transparent url(../images/nav_tree/bg.png) 9px -457px no-repeat;	
		text-indent:32px;
	}
	.TreeView div{
		overflow:visible;
	}
</style>
</head>
<body style="margin:0px;padding:0px;background:#FFFFFF;">
<div class="TreeView" id="ChannelNav">
	<div classPre="<%=sClassPre%>" id="<%=sId%>" SiteType="<%=nSiteType%>" OnlyNode="true">
		<A title="<%=sDesc%>" href="#" name="a<%=sId%>"><%=sDesc%></A>
	</div><ul style="display:none;"></ul>
</div>
	<script src="../js/easyversion/lightbase.js"></script>
	<script src="../js/easyversion/extrender.js"></script>
	<script language="javascript">
		initExtCss();
	</script>
	<script src="../js/easyversion/elementmore.js"></script>
    <script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../js/data/locale/nav_tree.js"></script>
	<script src="../js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../app/js/data/locale/tree.js"></script>
	<script src="../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
	<script src="../js/easyversion/ajax.js"></script>
<SCRIPT LANGUAGE="JavaScript">

var m_bIsLocal = false;
var m_bDebug = false;
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
	var sURL = "tree_html_creator.jsp" + (location.search||'?1=1') + "&Type=0&FromSelect=1&ParentType=" + sParentType + "&ParentId=" + sParentId;
<%
	if(objType == WCMSystemObject.OBJ_TYPE){
%>
	sURL += "&ShowSiteCheckBox=1"; 
<%
	}
%>
	return sURL;
}

//覆写点击节点A元素触发的事件
com.trs.tree.TreeNav.doActionOnClickA = function(_event, _elElementA){
	Event.stop(_event);
	return false;
}

com.trs.tree.TreeNav.observe('onload', function(){
	$('<%=sId%>').fireEvent('onclick');
});

com.trs.tree.TreeNav.__getSelectValue = function(_oNodeElementDiv){
	var sValue = _oNodeElementDiv.id;
	var nPos = sValue.indexOf("_");
	
	if(nPos<=0)
		return sValue;

	return sValue.substring(nPos+1);
}


</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
var TreeNav = com.trs.tree.TreeNav;
//设置树的类型
//TreeNav.setType(TreeNav.TYPE_RADIO);
TreeNav.setType(getParameter("IsRadio") == 1 ? TreeNav.TYPE_RADIO:TreeNav.TYPE_CHECKBOX);
if(getParameter("ShowHead") && getParameter("ShowHead") == 1){
	com.trs.tree.TreeNav.HEAD_ENABLE = true;
}

//设置记录checkbox Value在LI元素上的属性名称
TreeNav.setAttrNameRelatedValue("_objectid");

function getCheckValues(){
	return TreeNav.getCheckValues('ChannelNav', true);
}

function getCheckNames(){
	return TreeNav["SelectedNames"];
}

function getCheckTypeNames(){
	return TreeNav["SelectedTypeNames"];
}
	
function makeURLofGetHTMLContainsChannelIds(_sNodePath){
	var sAction = "tree_html_creator.jsp"+ (location.search||'?1=1') + "&Type=2&FromSelect=1&ChannelIds=" + _sNodePath;
	if(m_bIsLocal){
		sAction = getCurrentPath() + _sNodePath + ".html";
	}
	//prompt("makeURLofGetHTMLContainsChannelIds URL", sAction);
	return sAction;	
}

function makeURLofGetChildrenHTML2(_sNodePath){
	var sAction = "tree_html_creator.jsp" + (location.search||'?1=1') + "&Type=1&FromSelect=1&NodeIds=" + _sNodePath;
	if(m_bIsLocal){
		sAction = getCurrentPath() + _sNodePath + ".html";
	}	
	return sAction;	
}

function findPath(_sNodeType, _nNodeId){
	var sAction = "treenode_path_make.jsp?NodeType=" + _sNodeType + "&NodeId=" + _nNodeId;
	if(m_bIsLocal){
		sAction = getCurrentPath() + "path_" + _sNodeType + "_" + _nNodeId;
	}
	var localAjaxRequest = new Ajax.Request(
                    sAction,
                    {method: 'get', parameters: "", asynchronous: false}
                    );
	return localAjaxRequest.transport.responseText;
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

function reloadChildrenAndFocus(_sNodeType, _nFocusNodeId, _bReloadFocusChildren, _sNodePath){
	var sNodeHTMLElementId = _sNodeType + "_" + _nFocusNodeId;
	
	var sNeedLoadPath = "";
	//载入指定的库
	if(_sNodeType.toLowerCase() == "r"){
		sNeedLoadPath = sNodeHTMLElementId;
		sLastExistNodeId = sNodeHTMLElementId;
	}
	//载入不存在的栏目节点
	else{
		var sNodePath = _sNodePath;
		if(sNodePath == null || typeof sNodePath == "undefined" || sNodePath.trim().length<=0){
			sNodePath = findPath(_sNodeType, _nFocusNodeId);	
			if(sNodePath == null || sNodePath.trim().length<=0){
				alert(String.format("指定的对象[{0}.{1}]可能已经被删除!栏目树的同步刷新操作不能进行!",_sNodeType,_nFocusNodeId));
				return;
			}
		}

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
			sTempNodeId = getTypeByPathIndex(i) + "_" + pNodePaths[i];		
			if( $( sTempNodeId )  != null){
				sLastExistNodeId = sTempNodeId;			
				sNeedLoadPath = sTempNodeId;
				continue;
			}		
			
			for(; i<nPathLevel; i++){
				pNodePaths[i] = pNodePaths[i].trim();
				sNeedLoadPath += "," + getTypeByPathIndex(i) + "_" + pNodePaths[i];
			}
		}

		//将产生的数据更新到指定元素上同时执行聚焦
		if(sLastExistNodeId == null){
			/*
			*定位不到时，不给出提示信息
			alert("Root节点元素[Id=r_"+pNodePaths[0]+"]竟然都不存在？？？\n"
			+"(reloadChildrenAndFocus)相关信息：NodeType:"+_sNodeType+", _nFocusNodeId:"+_nFocusNodeId+"\n"
			+"相关HTML代码："+$("ChannelNav").innerHTML);
			*/
			return;
		}

		if(_bReloadFocusChildren)
			sNeedLoadPath += "," + sNodeHTMLElementId;
	}
	var oOnUpdateComplete		= com.trs.tree.TreeNav.focus;
	var oOnUpdateCompleteArgs	= sNodeHTMLElementId;
	com.trs.tree.TreeNav.updateNodeChildrenHTML(sLastExistNodeId, 
		makeURLofGetChildrenHTML2(sNeedLoadPath), 
		oOnUpdateComplete, oOnUpdateCompleteArgs);
}
//-->
</SCRIPT>
</body>
</html>
<%!
	private String getDesc(int siteType){
		String desc = LocaleServer.getString("nav_tree.label.textWarehouse", "文字库");
		switch(siteType){
			case 1:
				desc = LocaleServer.getString("nav_tree.label.picWarehouse", "图片库");
				break;
			case 2:
				desc = LocaleServer.getString("nav_tree.label.videoWarehouse", "视频库");
				break;
			case 4:
				desc = LocaleServer.getString("nav_tree.label.resourceWarehouse", "资源库");
				break;
		}
		return desc;
	}
%>