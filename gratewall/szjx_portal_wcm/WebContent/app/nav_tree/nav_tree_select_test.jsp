<%
/** Title:			nav_tree_select.jsp
 *  Description:
 *		标准WCM V6 页面，用于“栏目选择的选择”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
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

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>

<%
	int[] pSiteTypes = null;
	String sSiteTypes = currRequestHelper.getString("SiteTypes");
	if(sSiteTypes != null && (sSiteTypes=sSiteTypes.trim()).length()>0){
		pSiteTypes = CMyString.splitToInt(sSiteTypes, ",");
	}
%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="nav_tree_select_test.jsp.navtree">资源树</title>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/nav_tree.css" rel="stylesheet" type="text/css" />
<style>
	body{
		height:100%;
		width:100%;
		padding:0;
		background:transparent;
		margin:0;
		border:0;
	}
	.wcm_fixed_layout{
		table-layout:fixed;
		width:100%;
		height:100%;
	}
	.nav_top_tr{
		height:43px;
	}
	.nav_body_tr{
	}
	.nav_top_td{
		background-image: url(../images/nav_tree/top_bg_e.png);
		background-repeat: no-repeat;
		float: left;
		height: 43px;
		width: 296px;
		padding:0px;
		padding-left:10px;
		overflow: hidden;
	}
	.nav_tree {
		float:left;
		/*height:100%;*/
		/*width:290px!important;*//*FF,MOZ系列*/
		/*width:292px;*/
		overflow:hidden;
		border:0;
		padding:0px;
		margin-top:-1px!important;/*FF,MOZ系列*/
		margin-top:0px;
		/*border:1px solid #686868;*/
		border-top:0;
		background:#FFFFFF;
		padding: 5px;
	}
	.nav_btns{
		float:left;
		width:90px!important;/*FF,MOZ系列*/
		width:100px;
		height:100%;
		padding-left:10px;
	}
	.user_welcome{
		float:right;
		font-size: 12px;
		font-weight: bold;
		color: #40608f;
		width:92px;
		height: 15px;
		overflow:hidden;
		text-decoration: none;
		text-align:center;
	}
	.user_btns{
		float:right;
		width:72px!important;/*FF,MOZ系列*/
		width:92px;
		height:28px;
		padding-left:20px;
	}
	.nav_tree_toolbar_tr{
		height:18px;
	}
	.nav_tree_toolbar{
		float:left;
		height:100%;
		width:190px!important;/*FF,MOZ系列*/
		width:192px;
		overflow:hidden;
		border:0;
		padding:0px;
		margin-top:0px;
		border:1px solid #686868;
		border-top:0;
		border-bottom:0;
		background:#FFFFFF;
	}
	.refresh_btn{
		float: right;
		background-image: url(../images/nav_tree/icon_gfd1.png);
		background-repeat: no-repeat;
		height: 15px;
		width: 15px;
		overflow:hidden;
		margin:3px 0 0 5px;
	}
	.position_btn{
		float: right;
		background-image: url(../images/nav_tree/icon_gfd1.png);
		background-repeat: no-repeat;
		height: 15px;
		width: 15px;
		overflow:hidden;
		margin:3px 0 0 5px;
	}


</style>
<script src="../js/com.trs.util/Common.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	$import("com.trs.tree.TreeNav");
</SCRIPT>
<SCRIPT language="javascript" label="PageScope">
<!--
	
//-->
</SCRIPT>

<script>
if(getParameter("IsRadioBox") == 1){
}
</script>
<link href="../css/wcm_tree.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript">
<!--
var m_bIsLocal = false;
var m_bDebug = true;
//覆写动态获取指定节点的子节点HTML方法
com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(_elElementLi){
	if(m_bIsLocal)
		return getCurrentPath() + _elElementLi.id + ".html";
	
	var nPos = _elElementLi.id.indexOf("_");
	if(nPos <= 0){
		alert(wcm.LANG.nav_tree_select_test_1000 || "不符合规则！");
		return;
	}

	var sParentType	= _elElementLi.id.substring(0, nPos);
	var sParentId		= _elElementLi.id.substring(nPos+1);
	return "tree_html_creator.jsp" + location.search + "&Type=0&FromSelect=1&ParentType=" + sParentType + "&ParentId=" + sParentId;
}

//覆写点击节点A元素触发的事件
com.trs.tree.TreeNav.doActionOnClickA = function(_event, _elElementA){
	Event.stop(_event);
	return false;
}

com.trs.tree.TreeNav.__getSelectValue = function(_oNodeElementDiv){
	var sValue = _oNodeElementDiv.id;
	var nPos = sValue.indexOf("_");
	
	if(nPos<=0)
		return sValue;

	return sValue.substring(nPos+1);
}


//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
var TreeNav = com.trs.tree.TreeNav;
//设置树的类型
//TreeNav.setType(TreeNav.TYPE_RADIO);
TreeNav.setType(getParameter("IsRadio") == 1 ? TreeNav.TYPE_RADIO:TreeNav.TYPE_CHECKBOX);

//设置记录checkbox Value在LI元素上的属性名称
TreeNav.setAttrNameRelatedValue("_objectid");

function getCheckValues(){
	return TreeNav.getCheckValues('ChannelNav');
}

function getCheckNames(){
	return TreeNav["SelectedNames"];
}

function makeURLofGetHTMLContainsChannelIds(_sNodePath){
	var sAction = "tree_html_creator.jsp"+location.search + "&Type=2&FromSelect=1&ChannelIds=" + _sNodePath;
	if(m_bIsLocal){
		sAction = getCurrentPath() + _sNodePath + ".html";
	}
	//prompt("makeURLofGetHTMLContainsChannelIds URL", sAction);
	return sAction;	
}

function makeURLofGetChildrenHTML2(_sNodePath){
	var sAction = "tree_html_creator.jsp" + location.search + "&Type=1&FromSelect=1&NodeIds=" + _sNodePath;
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
	

	//没有传入NodePath,需要从服务器端读取NodePath
	if(_sNodePath == null || _sNodePath.trim().length<=0){
		_sNodePath = findPath(_sNodeType, _nFocusNodeId);	
		if(_sNodePath == null || _sNodePath.trim().length<=0){
			alert(String.format("指定的对象[{0}.{1}]可能已经被删除!栏目树的同步刷新操作不能进行!",_sNodeType,_nFocusNodeId));
			return;
		}
	}
	
	//依次判断路径
	var sNeedLoadPath = "";
	var pNodePaths = _sNodePath.split(",");
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
	if(_bReloadFocusChildren)
		sNeedLoadPath += "," + sNodeHTMLElementId;

	//将产生的数据更新到指定元素上同时执行聚焦
	if(sLastExistNodeId == null){
		alert(String.format("Root节点元素[Id=r_{0}]竟然都不存在？？？\n(reloadChildrenAndFocus)相关信息：NodeType:{1}, _nFocusNodeId:{2}\n相关HTML代码：{3}",pNodePaths[0],_sNodeType,_nFocusNodeId，$("divTreeRegion").innerHTML);
		//alert("指定的路径["+_sNodePath+"]在树中都不存在???");
		return;
	}
	var oOnUpdateComplete		= com.trs.tree.TreeNav.focus;
	var oOnUpdateCompleteArgs	= sNodeHTMLElementId;
	com.trs.tree.TreeNav.updateNodeChildrenHTML(sLastExistNodeId, 
		makeURLofGetChildrenHTML2(sNeedLoadPath), 
		oOnUpdateComplete, oOnUpdateCompleteArgs);
}


function expandTree(_nSiteId){
	//使用传入的值初始化树
	var sSelectedChannelIds = getParameter("SelectedChannelIds");
	var sExpandSiteId = _nSiteId || getParameter("ExpandSiteId");
	var sCurrChannelId = getParameter("CurrChannelId");
	if(sSelectedChannelIds != null && sSelectedChannelIds.length>0){
		TreeNav.setCheckedValues(sSelectedChannelIds.split(","));
		var sParentId = "r_0";
		TreeNav.updateNodeChildrenHTML(sParentId, makeURLofGetHTMLContainsChannelIds(sSelectedChannelIds));
	}
	//展开指定的站点
	else if(sExpandSiteId != null && sExpandSiteId.length>0){
		reloadChildrenAndFocus("s", sExpandSiteId, true);
	}
	//展开指定的栏目
	else if(sCurrChannelId != null && sCurrChannelId.length>0){
		reloadChildrenAndFocus("c", sCurrChannelId, false);
	}
}



Event.onDOMReady(function(){
	//??????? IF???做什么用的? caohui注释掉@2007-1-31 16:54 
	//if(getParameter("ExpandSiteId") != '') {
		expandTree();
	//}
});
//-->
</SCRIPT>

</head>

<body style="margin:0;padding:0;background:#FFFFFF;">
<div id="dy_adjust">
</div>
<script>
	var nEditorWidth = 380;
	var nEditorHeight = 250;
	function adjustScale(){
		var sStyleId = 'dy_adjust';
		var eStyleDiv = $(sStyleId);
		nEditorWidth = Element.getDimensions(document.body)["width"] - 10;
		nEditorHeight = Element.getDimensions(document.body)["height"] - 95;
		var cssStr = '.nav_tree{\
				width: ' + nEditorWidth + 'px;\
				height: ' + nEditorHeight + 'px;\
			}';
		var eStyle = $style(cssStr);
		$removeChilds(eStyleDiv);
		eStyleDiv.appendChild(eStyle);
	}
//	adjustScale();
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
if( m_bDebug ){
	document.write("<a href=# onclick=\"alert(getCheckValues())\">测试获取的值</a> || <a href=# onclick=\"alert(getCheckNames())\">测试获取值的名称</a><P>");
}

//-->
</SCRIPT>
<div class="nav_tree" style="width:95%;height:92%;overflow:auto;border:0;">
		<ul class="TreeView" id="ChannelNav">
			<%
				writeTreeHTML(pSiteTypes, out);
			%>
		</ul>
</div>
</body>
</html>
<%!
	public void writeTreeHTML(int[] _pSiteTypes, JspWriter out)
            throws Exception {
        int[] pSiteTypes = { 0, 1, 2 };
        String[] pSiteTypeNames = {LocaleServer.getString("nav_tree_select_test.jsp.label.word_coll", "文字库"), LocaleServer.getString("nav_tree_select_test.jsp.label.pic_coll","图片库"), LocaleServer.getString("nav_tree_select_test.jsp.label.vedio_coll","视频库") };
        if (_pSiteTypes != null && _pSiteTypes.length > 0) {
            pSiteTypes = _pSiteTypes;
        }
        for (int i = 0; i < pSiteTypes.length; i++) {
            int nSiteType = pSiteTypes[i];
            out.println("<div title=\"" + pSiteTypeNames[nSiteType]
                    + "\" id=\"r_" + nSiteType + "\" classPre=\"SiteType"
                    + nSiteType + "\" OnlyNode=\"true\">");
            out.println("<a href=#>" + pSiteTypeNames[nSiteType] + "</a>");
            out.println("</div>");

            out.println("<ul></ul>");
        }
    }
%>