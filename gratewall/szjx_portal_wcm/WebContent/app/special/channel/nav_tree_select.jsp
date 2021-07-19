<%
/** Title:			nav_tree_select.jsp
 *  Description:
 *		标准WCM V6 页面，用于"栏目选择的选择"。
 *  Copyright:	www.trs.com.cn
 *  Company:	TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2007-2-6 15:13
 *  Vesion:			1.0
 *  Last EditTime:	2007-2-6/2007-2-6
 *	Update Logs:
 *		CH@2007-2-6 created the file 
 *
 *  Parameters:
 *		see nav_tree_select.xml
 *
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../../include/public_server.jsp"%>
<%
	int[] pSiteTypes = {0, 1, 2, 4};
	String sSiteTypes = CMyString.showNull(currRequestHelper.getString("SiteTypes"));
	boolean bShowOneType = currRequestHelper.getBoolean("ShowOneType",false);
	//MultiSiteType可由传入的SiteTypes或ShowOneType推断
	boolean defaultValue = !bShowOneType || (sSiteTypes != null && sSiteTypes.indexOf(",") >= 0);
	boolean bMultiSiteType = currRequestHelper.getBoolean("MultiSiteType", defaultValue);
	//兼容 MultiSites 的逻辑：仅仅显示单个站点，MultiSiteType为false
	if(!currRequestHelper.getBoolean("MultiSites", false)){
		bMultiSiteType = false;
	}
	//显示指定的站点
	int nExpandSiteId = currRequestHelper.getInt("ExpandSiteId", 0);
	if(nExpandSiteId>0 && !currRequestHelper.getBoolean("MultiSites", true)){
		WebSite site = WebSite.findById(nExpandSiteId);
		if(site == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("nav_tree\nav_tree_select.jsp.website_notfound", "没有找到指定的站点![ID={0}]"), new int[]{nExpandSiteId}));
		}
		sSiteTypes = site.getPropertyAsString("SiteType");
	}
	//展开指定的节点
	int nExpandNodeId = -1;
	String sExpandNodeType = "";
	int nCurrSiteType	= currRequestHelper.getInt("CurrSiteType", -1);
	int nCurrSiteId		= currRequestHelper.getInt("CurrSiteId", 0);
	int nCurrChannelId	= currRequestHelper.getInt("CurrChannelId", 0);
	int nFristChannelSiteType = 0;
	if(nCurrChannelId>0){
		sExpandNodeType = "c";
		nExpandNodeId = nCurrChannelId;		
	}
	else if(nCurrSiteId>0){
		sExpandNodeType = "s";
		nExpandNodeId = nCurrSiteId;		
	}
	else if(nCurrSiteType>=0){
		sExpandNodeType = "r";
		nExpandNodeId = nCurrSiteType;		
	}
	String sSelectedChannelIds = currRequestHelper.getString("SelectedChannelIds");
	if(	sSelectedChannelIds != null && sSelectedChannelIds.length()>0){		
		nFristChannelSiteType = getSiteTypeOfChannel(CMyString.splitToInt(
                sSelectedChannelIds, ",")[0]);
	}
	//显示那些库
	if(bMultiSiteType && sSiteTypes == null){
		int[] pSiteTypesTemp = {0,1,2,4};
		pSiteTypes = pSiteTypesTemp;
	}
	else{		
		// 1. 指定了显示的站点类型
		if(sSiteTypes != null && (sSiteTypes=sSiteTypes.trim()).length()>0){
			pSiteTypes = CMyString.splitToInt(sSiteTypes, ",");
		}
		// 2. 根据SelectXXX和CurrXXX确定显示那些库
		else{					
			String sSelectedSiteIds = currRequestHelper.getString("SelectedSiteIds");
			String sSelectedSiteTypes = currRequestHelper.getString("SelectedSiteTypes");
			//================================================
			//========SelectedXXX 优先级高====================
			// 1. 选中指定的栏目
			if(	sSelectedChannelIds != null && sSelectedChannelIds.length()>0){			
				//pSiteTypes = getSiteTypesOfChannels(sSelectedChannelIds);			
				pSiteTypes = new int[]{nFristChannelSiteType};			
			}
			// 2. 选中指定的站点
			else if( sSelectedSiteIds != null && sSelectedSiteIds.length()>0){			
				pSiteTypes = getSiteTypesOfSites(sSelectedSiteIds);			
				sExpandNodeType = "s";
				nExpandNodeId = CMyString.splitToInt(sSelectedSiteIds, ",")[0];		
			}
			// 3. 选中指定的库
			else if( sSelectedSiteTypes != null && sSelectedSiteTypes.length()>0){			
				pSiteTypes = CMyString.splitToInt(sSelectedSiteTypes, ",");			
				sExpandNodeType = "r";
				nExpandNodeId = pSiteTypes[0];		
			}
			//================================================
			//========CurrXXX 优先级最小======================
			// 1. 当前焦点在栏目上
			else if(nCurrChannelId>0){		
				pSiteTypes = new int[]{getSiteTypeOfChannel(nCurrChannelId)};
			}
			// 2. 当前焦点在站点上
			else if(nCurrSiteId>0){			
				pSiteTypes = new int[]{getSiteTypeOfSite(nCurrSiteId)};
			}
			// 3. 当前焦点在指定库上
			else if(nCurrSiteType>=0){			
				int[] pSiteTypesTemp = {nCurrSiteType};
				pSiteTypes = pSiteTypesTemp;			
			}
			if(pSiteTypes == null){
				throw new WCMException(CMyString.format(LocaleServer.getString("nav_tree_select.jsp.not_supportmode", "不支持指定的模式！{0}"), new Object[]{currRequestHelper.toURLParameters()}));
			}
		}
	}
	out.clear();
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="nav_tree_select.jsp.title">Navigator Tree</title>
	<link href="../../css/common_tree.css" rel="stylesheet" type="text/css">
	<link href="../../nav_tree/nav_tree.css" rel="stylesheet" type="text/css">
	<style type="text/css">
		.TreeView{border:0px;overflow:auto;height:100%;}
	</style>
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../../js/source/wcmlib/Observable.js"></script>
	<script src="../../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>

	<script language="javascript" type="text/javascript" src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script language="javascript" type="text/javascript" src="../../js/easyversion/ajax.js"></script>
	<script language="javascript" type="text/javascript" src="../../js/easyversion/basicdatahelper.js"></script>
	<script language="javascript" type="text/javascript" src="../../js/easyversion/web2frameadapter.js"></script>
</head>
<body style="margin:0;padding:0;background:#FFFFFF;" onselectstart="return false;">
<div class="nav_tree" style="width:100%;height:100%;border:0;">
	<ul class="TreeView" id="ChannelNav">
	<%
		writeTreeHTML(pSiteTypes, out);				
	%>
	</ul>
</div>
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
	return "../../nav_tree/tree_html_creator.jsp" + (location.search||'?1=1') + "&Type=0&FromSelect=1&ParentType=" + sParentType + "&ParentId=" + sParentId;
}
//覆写点击节点A元素触发的事件
com.trs.tree.TreeNav.doActionOnClickA = function(_event, _elElementA){
	//Event.stop(_event);
	//return false;
	return true;
}
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

function makeURLofGetHTMLContainsChannelIds(_sNodePath){
	var sAction = "../../nav_tree/tree_html_creator.jsp"+ (location.search||'?1=1') + "&Type=2&FromSelect=1&ChannelIds=" + _sNodePath;
	if(m_bIsLocal){
		sAction = getCurrentPath() + _sNodePath + ".html";
	}
	return sAction;	
}
function makeURLofGetChildrenHTML2(_sNodePath){
	var sAction = "../../nav_tree/tree_html_creator.jsp" + (location.search||'?1=1') + "&Type=1&FromSelect=1&NodeIds=" + _sNodePath;
	if(m_bIsLocal){
		sAction = getCurrentPath() + _sNodePath + ".html";
	}	
	return sAction;	
}
function findPath(_sNodeType, _nNodeId){
	var sAction = "../../nav_tree/treenode_path_make.jsp?NodeType=" + _sNodeType + "&NodeId=" + _nNodeId;
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
				alert(String.format("指定的对象[{0}.{1}}]可能已经被删除!栏目树的同步刷新操作不能进行!",_sNodeType,_nFocusNodeId));
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
var nExpandNodeId = <%=nExpandNodeId%>;
var sExpandNodeType = "<%=sExpandNodeType%>";
function expandTree(_nSiteId){
	//======================================================
	//==========初始化过程SelectedXXX优先级大于CurrXXX======
	// 初始化选中的节点
	try{
		var sSelectedChannelIds = "<%=CMyString.filterForJs(CMyString.showNull(currRequestHelper.getString("SelectedChannelIds")))%>";
		if(sSelectedChannelIds != null && sSelectedChannelIds.length>0){
			if(getParameter("NotSelect") == null || getParameter("NotSelect") == ""){
				TreeNav.setCheckedValues(sSelectedChannelIds.split(","));
			}
			//	TODO 如果是跨库选择的那么将会有问题
			var sParentElementId = "r_<%=nFristChannelSiteType%>";
			TreeNav.updateNodeChildrenHTML(sParentElementId, makeURLofGetHTMLContainsChannelIds(sSelectedChannelIds));
		}else{
			//如果指定了当前节点，那么直接定位到当前节点上
			if(nExpandNodeId>=0){
				reloadChildrenAndFocus(sExpandNodeType, nExpandNodeId, true);
				return;
			}
		}
		var unSelectDisabledNoRight = getParameter("unSelectDisabledNoRight");
		if(unSelectDisabledNoRight == "1"){
			unSelectedTreeNode(sSelectedChannelIds || nExpandNodeId);
		}
	}catch(error){
		//兼容定位不存在节点时的错误
	}
}
/**
*取消选中的disabled的栏目
*/
function unSelectedTreeNode(channelIds){
	var channelIds = channelIds.split(",");
	for(var i = 0; i < channelIds.length; i++){
		var inputDom = $("cc_" + channelIds[i]);
		if(inputDom.disabled){
			inputDom.checked = false;
		}
	}
}
com.trs.tree.TreeNav.observe("onload", expandTree);
//ge gfc add @ 2007-7-2 14:40 从nav_tree迁移过来的工具类方法
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
	if( oFocusElement != null ){//当前处于库节点时得到ul
		oFocusElement = oFocusElement.parentNode.nextSibling || oFocusElement;
	}
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
        _element = getPreviousHTMLSibling(_element);
    }
    return findSiteId(_element);
}
function findFocusNodeSiteId(){
    var oFocusElement = com.trs.tree.TreeNav.oPreSrcElementA;
    return findSiteId(oFocusElement);
}
//-->
</SCRIPT>
<script language="javascript">
<!--
	Event.observe(window, 'load', function(){
	
		var CustomizeInfo = null;
		var topWin = (window.$MsgCenter && $MsgCenter.getActualTop()) || top;
		if(topWin && topWin.CustomizeInfo){
			CustomizeInfo = topWin.CustomizeInfo;
		}else if(topWin.topHandler && topWin.topHandler.CustomizeInfo){
			CustomizeInfo = topWin.topHandler.CustomizeInfo;
		}		
		if(!CustomizeInfo){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			oHelper.JspRequest("../../individuation/systemindividuation_get.jsp",{},false, 
				function(transport,json){				
					if(!json){
						return;
					}
					var adiv=document.getElementsByTagName("div");
					for(var i=0;i<adiv.length;i++){
						if(adiv[i].id=="r_0" && json["TEXTLIB"]=="false"){
							document.getElementById("r_0").style.display = "none"; 
						}else if(adiv[i].id=="r_1" && json["PICTURELIB"]=="false"){
							document.getElementById("r_1").style.display = "none";
						}else if(adiv[i].id=="r_2" && json["VIDEOLIB"]=="false"){
							document.getElementById("r_2").style.display = "none";
						}else if(adiv[i].id=="r_4" && json["RESOURCESLIB"]=="false"){
							document.getElementById("r_4").style.display = "none";
						}
					}
				}
			);
		}else{
			var adiv=document.getElementsByTagName("div");
			if(!CustomizeInfo["textLib"]){
				return;
			}
			for(var i=0;i<adiv.length;i++){
				if(adiv[i].id=="r_0" && CustomizeInfo["textLib"].paramValue=="false"){
					document.getElementById("r_0").style.display = "none";
				}else if(adiv[i].id=="r_1" && CustomizeInfo["pictureLib"].paramValue=="false"){
					document.getElementById("r_1").style.display = "none";
				}else if(adiv[i].id=="r_2" && CustomizeInfo["videoLib"].paramValue=="false"){
					document.getElementById("r_2").style.display = "none";
				}else if(adiv[i].id=="r_4" && CustomizeInfo["resourcesLib"].paramValue=="false"){
					document.getElementById("r_4").style.display = "none";
				}
			}
		}
	});
//-->
</script>
	<script src="../drag.js"></script>
	<script src="nav_tree_select.js"></script>
</body>
</html>
<%!	
	public void writeTreeHTML(int[] _pSiteTypes, JspWriter out)
            throws Exception {				
        int[] pSiteTypes = { 0, 1, 2, 4 };
        if (_pSiteTypes != null && _pSiteTypes.length > 0) {
            pSiteTypes = _pSiteTypes;
        }
		for (int i = 0; i < pSiteTypes.length; i++) {
			int nSiteType = pSiteTypes[i];
            out.println("<div title=\"" + getDesc(nSiteType)
                    + " \" id=\"r_" + nSiteType + "\" classPre=\"SiteType"
                    + nSiteType + "\" SiteType=\"" + nSiteType + "\" OnlyNode=\"true\">");
            out.println("<a href=#>" + getDesc(nSiteType) + "</a>");
            out.println("</div>");
            out.println("<ul SiteType=\"" + nSiteType + "\"></ul>");
        }
    }
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
	private int[] getSiteTypesOfSites(String _sSiteIds) throws Exception {
        return new int[] { getSiteTypeOfSite(CMyString.splitToInt(
                _sSiteIds, ",")[0]) };
    }
	private int[] getSiteTypesOfChannels(String _sChannelIds) throws Exception {
        return new int[] { getSiteTypeOfChannel(CMyString.splitToInt(
                _sChannelIds, ",")[0]) };
    }
    private int getSiteTypeOfChannel(int _nChannelId) throws Exception {
        final String SQL_GET_SITETYPE = "select SiteType from WCMWebSite"
                + " where exists("
                + "select WCMChannel.ChannelId from WCMChannel"
                + "  where WCMChannel.ChannelId=?"
                + " and WCMChannel.SiteId=WCMWebSite.SiteId"//
                + ")";
        int nFirstLoadSiteType = DBManager.getDBManager().sqlExecuteIntQuery(
                SQL_GET_SITETYPE, new int[] { _nChannelId });
        return nFirstLoadSiteType;
    }
    private int getSiteTypeOfSite(int _nSiteId) throws Exception {
        final String SQL_GET_SITETYPE = "select SiteType from WCMWebSite"
                + " where SiteId=?";
        return DBManager.getDBManager().sqlExecuteIntQuery(SQL_GET_SITETYPE,
                new int[] { _nSiteId });
    }
%>