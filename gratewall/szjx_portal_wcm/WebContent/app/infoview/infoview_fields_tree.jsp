<%--
/** Title:			infoview_addedit.jsp
 *  Description:
 *		WCM5.2 自定义表单的编辑修改的界面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.04.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.04.12	fcr created
 *
 *  Parameters:
 *		see infoview_addedit.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewField" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewFields" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewGroup" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewGroups" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="java.util.List" %>
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	if (nInfoViewId <= 0){
		throw new WCMException(LocaleServer.getString("infoview_fields_tree.id.zero","InfoViewId为0，无法找到InfoView！"));
	}
	InfoView currInfoView = InfoView.findById(nInfoViewId);
	if (currInfoView == null){
		throw new WCMException(LocaleServer.getString("infoview_fields_tree.obj.not.found","无法找到InfoView！"));
	}
	String sCurrSettingMode = CMyString.showNull(currRequestHelper.getString("SettingMode"),"normal");
	String strUnReadableFields = "";
	String strUnWriteableFields = "";
	String strUnReadableGroups = "";
	String strAllFields = "";
	InfoViewFields lstFields = InfoViewFields.findBy(currInfoView);
	for(int i=0;i<lstFields.size();i++){
		InfoViewField oInfoViewField = (InfoViewField)lstFields.getAt(i);
		if(oInfoViewField.isReadOnly()){
			strUnWriteableFields += "," + oInfoViewField.getXPath();
		}
		strAllFields += "," + oInfoViewField.getXPath();
	}
	if("normal".equalsIgnoreCase(sCurrSettingMode)){
		InfoViewGroups lstGroups = InfoViewGroups.findBy(currInfoView);
		for(int i=0;i<lstGroups.size();i++){
			InfoViewGroup oInfoViewGroup = (InfoViewGroup)lstGroups.getAt(i);
			if(!oInfoViewGroup.isPublicFill()){
				strUnReadableGroups += "," + oInfoViewGroup.getDesc();
			}
		}
	}
	else{
//		strUnReadableFields = "";
//		strUnWriteableFields = "";
	}
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview.fields.tree.jsp.title">选择数据项</TITLE>
<style>
BODY {
	FONT-SIZE: 10pt; FONT-FAMILY: SimSun;
}
body{padding:0;margin:0;width:100%;height:100%;overflow:auto;}
.dTreeNode{
	cursor:pointer;
	height:20px;
}
div, body{
	scrollbar-face-color : #EDEDED;
	scrollbar-darkshadow-color : #EDEDED;
}
select {
	position:relative;
	left:-4px;
	top:-4px;
	font-size:15px;
	width:200px;
	height:315px;
	line-height:16px;
	border:0px;
	color:#222222;
}
#contextmenu{
	position:absolute;
	width:120px;
	background-color:#f9f8f7;
	padding:2px;
	border:1px solid gray;
}
.contextmenu_item{
	width:100%;
	height:22px;
	line-height:22px;
	padding-left:10px;
	overflow:hidden;
	cursor:pointer;
}
.node_unread{
	padding-left:15px;
	background-image: url(unreadable.gif);
	background-repeat: no-repeat;
	background-position: 0 0;
}
.node_unwrite{
	padding-left:15px;
	background-image: url(unwriteable.gif);
	background-repeat: no-repeat;
	background-position: 0 0;
}
#bubble-panel:focus{outline:0;}
#bubble-panel{border:1px solid gray;border:1px solid gray;background:#ECECEC;padding:5px 0;cursor:pointer;width:120px;}
#bubble-panel .menuitem{padding:4px 5px 4px 15px;font-size:10pt;}
#bubble-panel .menuitem_active{background:#FFFFEF;font-weight:normal;}
.unreadable,.unwriteable,.unallwriteable,.unallreadable{display:inline;padding:0 10px;width:0;height:0;}
.readable,.writeable,.allwriteable,.allreadable{display:inline;padding:0 10px;background:url(ok.gif) no-repeat 2px center;width:12px;height:12px;}
.treenode_focus{background-color:#FFFF00;}
</style>
<link rel="StyleSheet" href="xmltree.css" type="text/css" />
<script>
	var oTree, m_oAllFields, m_oUnReadableFields, m_oUnWriteableFields, m_oUnReadableGroups;
	var m_strAllFields = "<%=CMyString.showNull(strAllFields)%>";
	var m_strUnReadableFields = "<%=CMyString.showNull(strUnReadableFields)%>";
	var m_strUnWriteableFields = "<%=CMyString.showNull(strUnWriteableFields)%>";
	var m_strUnReadableGroups = "<%=CMyString.showNull(strUnReadableGroups)%>";
	var m_sCurrMode = '<%=CMyString.filterForJs(sCurrSettingMode)%>'.toLowerCase();
	var m_nInfoViewId = <%= nInfoViewId %>;
</script>
</HEAD>
<BODY style="margin:0;font-size:9pt">
<div id="body_container" style="border:1px solid #C0C0C0;width:100%; height:100%;background:#FFFFFF;overflow:auto">
	<div style="width:100%; height:100%; padding-left:5px;overflow:visible" id="FieldsTree">
	</div>
	<form name="frmAction" id="frmAction" style="display:none;">
	<textarea style="display:none;position:absolute;left:0;top:0;" id="TemplateXML"><%=currInfoView.getTemplateFileContent()%></textarea>
	</form>
</div>
<div id=bubble-panel style="display: none; z-index: 999; position: absolute" _fxtype="bubble-panel"></div>
<div id=fly-button style="border:0;display:none; z-index: 99; cursor: pointer; position: absolute" _fxtype="fly-button"><img src="infoview/DownButton.gif" border=0></div>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/bubblepanel.js"></script>
<script src="../../app/js/easyversion/position.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script type="text/javascript" src="xmltree.js"></script>
<script type="text/javascript" src="infoview_fields_tree.js"></script>
</BODY>
</HTML>