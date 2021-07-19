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
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	if (nInfoViewId <= 0){
		throw new WCMException(LocaleServer.getString("infoview_fields_select.id.zero","InfoViewId为0，无法找到InfoView！"));
	}
	InfoView currInfoView = InfoView.findById(nInfoViewId);
	if (currInfoView == null){
		throw new WCMException(LocaleServer.getString("infoview_fields_select.obj.not.found","无法找到InfoView！"));
	}

	String strSelectedFields = CMyString.showNull(currRequestHelper.getString("SelectedFields"));
	
	String strType = CMyString.showNull(currRequestHelper.getString("Type"));
	if(!strType.equalsIgnoreCase("radio")) {
		strType = "checkbox";
	} else {
		strType = "radio";
	}
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview.fields.select.jsp.title">选择数据项</TITLE>
<style languageStyle="languageStyle">
BODY {
	FONT-SIZE: 10pt; FONT-FAMILY: SimSun
}
.box_outer {
	border:1px solid #C0C0C0;
	/*border:1px solid #000000;*/
	width:198px;
	height:308px;
	clip:rect(0px,181px,18px,0px);
	overflow:hidden;
}
.box_inner {
	border:1px solid #F4F4F4;
	/*border:1px solid #000000;*/
	width:194px;
	height:308px;
	clip:rect(0px,179px,16px,0px);
	overflow:hidden;
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
</style>
<script src="com.trs.util/Common.js"></script>
<script src="com.trs.util/Other.js"></script>
<script src="com.trs.util/XML.js"></script>
<link rel="StyleSheet" href="xmltree.css" type="text/css" />
<script type="text/javascript" src="xmltree.js"></script>
<script>
var oTree;
function refreshTree(_sDefaultValue) {
	var sXMLString = document.getElementById("TemplateXML").value;
	oTree = XMLTree.createInstance(sXMLString, "<%=strType%>", _sDefaultValue);
	document.getElementById("FieldsTree").innerHTML = oTree.toString();
}
function window_onload() {
	var sDefaultValue = "<%=CMyString.filterForJs(strSelectedFields)%>";
	refreshTree(sDefaultValue);
}
window.onload = window_onload;

function getSelectedFields() {
	var arrFields = new Array();
	var oTreeDiv = document.getElementById("FieldsTree");
	var oElements = oTreeDiv.getElementsByTagName("input");
	for(var i=0; i<oElements.length; i++) {
		if(oElements[i].disabled)
			continue;
		if(oElements[i].checked) {
			arrFields.push(oElements[i].value);
		}
	}
	return arrFields.toString();
}
function ok() {
	window.returnValue = getSelectedFields();
	if(window.returnValue == null) {
		window.returnValue = '';
	}
	window.close();
}
function cancel() {
	window.close();
}
</script>
</HEAD>
<BODY style="margin-left:8;margin-bottom:0;margin-top:8px;font-size:9pt">
<div style="width:100%; height:370px; background:#FFFFFF">
	<div style="height:370px; width:100%; border:1px solid #C0C0C0; overflow:auto" id="FieldsTree">
	</div>
</div>
<div style="width:100%; height:30px; overflow:hidden;">
	<div style="position:absolute; top:5; text-align:center; width:100%">
		<input type="button" style="width:80px" value="确  定" onclick="ok()" WCMAnt:paramattr="value:infoview.fields.select.jsp.onvalue">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" style="width:80px" value="取  消" onclick="window.close();" WCMAnt:paramattr="value:infoview.fields.select.jsp.cancelvalue">
	</div>
</div>
<form name="frmAction" id="frmAction">
<textarea style="display:none" id="TemplateXML"><%=currInfoView.getTemplateFileContent()%></textarea>
</form>
</BODY>
</HTML>