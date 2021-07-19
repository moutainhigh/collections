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
	int nChannelId	= currRequestHelper.getInt("ChannelId", 0);
	if (nChannelId <= 0){
		throw new WCMException(LocaleServer.getString("select_order.id.zero","栏目Id为0，无法找到栏目！"));
	}
	Channel currChannel = Channel.findById(nChannelId);
	if (currChannel == null){
		throw new WCMException(LocaleServer.getString("select_order.obj.not.found","无法找到栏目！"));
	}
	if (currChannel.getType() != Channel.TYPE_INFOVIEW){
		throw new WCMException(LocaleServer.getString("select_order.channel.wrongType","该栏目不是自定义表单栏目！"));
	}

	IInfoViewService oInfoViewService = ServiceHelper.createInfoViewService();
	List listEmployed = oInfoViewService.getEmployedInfoViews(currChannel);
	if (listEmployed == null || listEmployed.size() <= 0) {
		throw new WCMException(LocaleServer.getString("select_order.channel.notFit","该栏目没有配置有效的自定义表单，无法编辑其下的文档！"));
	}
	InfoView infoview = (InfoView) listEmployed.get(0);

	String strSelectedFields = "";//CMyString.showNull(currRequestHelper.getString("SelectedFields"));
	
	boolean isExport = currRequestHelper.getBoolean("IsExport", false);
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview.fields.select.order.jsp.title">选择数据项</TITLE>
<link rel="StyleSheet" href="xmltree.css" type="text/css" />
<style languageStyle="languageStyle">
BODY {
	FONT-SIZE: 10pt; FONT-FAMILY: SimSun
}
div, body{
	scrollbar-face-color : #EDEDED;
	scrollbar-darkshadow-color : #EDEDED;
}
.button{
	border:1px solid gray;
	cursor:pointer;
	margin-bottom:2px;
}
</style>
</HEAD>
<BODY style="margin-left:8;padding:0;margin-bottom:0;margin-top:0;font-size:9pt">
<table border=0 cellspacing=0 cellpadding=0 width="100%" height="100%" style="background:#FFFFFF"">
<tbody>
	<tr>
		<td width="270">
			<div style="height:20px; width:260px; overflow: hidden;">
				<div style="margin-top:2px" WCMAnt:param="infoview.fields.select.order.jsp.allowfield">可用数据项:</div>
			</div>
			<div style="height:280px; width:270px; border:1px solid gray; overflow:auto" id="FieldsTree">
				<div style="width:245px; overflow:hidden" id="SystemFieldsTree">
				</div>
				<div style="width:245px; overflow:hidden" id="InfoViewFieldsTree">
				</div>
			</div>
		</td>
		<td width="90" align="center">
			<div style="margin-top:25px; text-align:center; width:90px;">
				<input type="button" class="button" value="添加 >>" style="width:80px" onclick="insertFields()" WCMAnt:paramattr="value:infoview.fields.select.order.jsp.addvalue">
				<br>
				<input type="button" class="button" value="<< 移除" style="width:80px" onclick="removeFields()" WCMAnt:paramattr="value:infoview.fields.select.order.jsp.deletevalue">
			</div>
			<div style="margin-top:25px; text-align:center; width:90px;">
				<input type="button" class="button" value="全部添加 >>" style="width:80px" onclick="insertAllFields()" WCMAnt:paramattr="value:infoview.fields.select.order.jsp.addallvalue">
				<br>
				<input type="button" class="button" value="<< 全部移除" style="width:80px" onclick="removeAllFields()" WCMAnt:paramattr="value:infoview.fields.select.order.jsp.deleteallvalue">
			</div>
			<div style="margin-top:25px; text-align:center; width:90px;">
				<input type="button" class="button" value="上移" style="width:80px" onclick="moveUpFields()" WCMAnt:paramattr="value:infoview.fields.select.order.jsp.moveupvalue">
				<br>
				<input type="button" class="button" value="下移" style="width:80px" onclick="moveDownFields()" WCMAnt:paramattr="value:infoview.fields.select.order.jsp.movedownvalue">
			</div>
		</td>
		<td width="270">
			<div style="height:20px; width:260px; overflow: hidden;">
				<div style="margin-top:2px" WCMAnt:param="infoview.fields.select.order.jsp.currfield">当前数据项:</div>
			</div>
			<select id="FieldsSelect" name="FieldsSelect" multiple style="width:260px;height:280px;border:1px solid gray;">
			</select>
		</td>
	</tr>
</tbody>
</table>
<form name="frmAction" id="frmAction" style="display:none">
<textarea style="display:none" id="TemplateXML"><%=infoview.getTemplateFileContent()%></textarea>
</form>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script type="text/javascript" src="xmltree.js"></script>
<script>
var m_cb = null;
function init(params, cb){
	var sDefaultValue = params.SelectedFields;//"<%=strSelectedFields%>";
	refreshTree(sDefaultValue);
	insertOptions(sDefaultValue);
	m_cb = cb;
}
function onOk(){
	var selectedFields = getSelectedFields();
	var bIsExport = <%=isExport%>;
	if(bIsExport 
		&& (!selectedFields || !(selectedFields.length > 0))) {
		alert(wcm.LANG['INFOVIEW_DOC_94'] || '选择的字段长度必须大于0');
		return false;
	}
	m_cb.callback(selectedFields);
	return false;
}
function refreshTree(_sDefaultValue) {
	refreshInfoViewTree(_sDefaultValue);
	refreshSystemTree(_sDefaultValue);
}
var oTree;
function refreshInfoViewTree(_sDefaultValue) {
	var sXMLString = document.getElementById("TemplateXML").value;
	oTree = XMLTree.createInstance(sXMLString, "checkbox", _sDefaultValue, _sDefaultValue);
	document.getElementById("InfoViewFieldsTree").innerHTML = oTree.toString();
	refreshSystemTree(_sDefaultValue);
}
var oSystemTree;
<%if(isExport) {%>
var arrSystemFields = [
	{name  : wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",	value : "DOCTITLE"},
	{name  : wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",	value : "CRTIME"},
	{name  : wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",		value : "CRUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",		value : "POSTUSER"},
	{name  : "发稿人IP",		value : "POSTIP"},
	{name  : wcm.LANG['INFOVIEW_DOC_99'] || "状态",		value : "_DOCSTATUS"},
	{name  : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",	value : "DOCNO"},
	{name  : wcm.LANG['INFOVIEW_DOC_101'] || "查询编号",	value : "RANDOMSERIAL"}
];
var oSystemFieldTextMap = {
	DOCTITLE	: wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",
	CRTIME		: wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",
	CRUSER		: wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",
	POSTUSER	: wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",
	POSTIP : "发稿人IP",
	_DOCSTATUS	: wcm.LANG['INFOVIEW_DOC_99'] || "状态",
	DOCNO : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",
	RANDOMSERIAL  : wcm.LANG['INFOVIEW_DOC_101'] ||"查询编号"
};
<%}else{%>
var arrSystemFields = [
	{name  : wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",	value : "DOCTITLE"},
	{name  : wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",	value : "CRTIME"},
	{name  : wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",		value : "CRUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",		value : "POSTUSER"},
	{name  : "发稿人IP",		value : "POSTIP"},
	{name  : wcm.LANG['INFOVIEW_DOC_99'] || "状态",		value : "_DOCSTATUS"},
	{name  : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",	value : "DOCNO"},
	{name  : wcm.LANG['INFOVIEW_DOC_101'] || "查询编号",	value : "RANDOMSERIAL"},
	{name  : wcm.LANG['INFOVIEW_DOC_102'] || "预览",		value : "_PREVIEW"},
	{name  :  wcm.LANG['INFOVIEW_DOC_103'] || "编辑",		value : "_EDIT"}
];
var oSystemFieldTextMap = {
	DOCTITLE  : wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",
	CRTIME    : wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",
	CRUSER    : wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",
	POSTUSER	: wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",
	POSTIP : "发稿人IP",
	_DOCSTATUS: wcm.LANG['INFOVIEW_DOC_99'] || "状态",
	DOCNO : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",
	RANDOMSERIAL  : wcm.LANG['INFOVIEW_DOC_101'] || "查询编号",
	_PREVIEW  : wcm.LANG['INFOVIEW_DOC_102'] || "预览",
	_EDIT     : wcm.LANG['INFOVIEW_DOC_103'] || "编辑"
};
<%}%>
function refreshSystemTree(_sDefaultValue) {
	oSystemTree = new dTree("oSystemTree");
	oSystemTree.add("SystemFieldsRoot", "-1",  wcm.LANG['INFOVIEW_DOC_104'] || "系统数据项");
	var arrDefaultValues = new Array();
	if(_sDefaultValue) {
		arrDefaultValues = _sDefaultValue.split(",");
	}
	for(var i=0; i<arrSystemFields.length; i++) {
		var sName = arrSystemFields[i].name;
		var sValue = arrSystemFields[i].value;
		var sDisplay = "";
		for(var j=0; j<arrDefaultValues.length; j++) {
			if(arrDefaultValues[j] == sValue) {
				sDisplay = "checked disabled";
				break;
			}
		}
		oSystemTree.add(sValue, "SystemFieldsRoot", '<input type="checkbox" name="FieldIds" value="' + sValue + '" ' + sDisplay + '>' + sName);
	}
	document.getElementById("SystemFieldsTree").innerHTML = oSystemTree.toString();
}
function insertOptions(_sDefaultValue) {
	var oSelect = document.getElementById("FieldsSelect");
	var arrValues = new Array();
	if(_sDefaultValue) {
		arrValues = _sDefaultValue.split(",");
	}
	for(var i=0; i<arrValues.length; i++) {
		var sValue = arrValues[i];
		var sText = sValue;
		var sMapText = oSystemFieldTextMap[sText];
		if(sMapText) {
			sText = sMapText;
		} else {
			var nIndex = sText.lastIndexOf(":");
			sText = sText.substring(nIndex + 1);
		}
		var oOption = document.createElement("option");
		oSelect.options.add(oOption);
		oOption.innerHTML = sText;
		oOption.value = sValue;
	}
}
function currViewFields() {
	removeAllFields();
}
function insertFields() {
	var oTreeDiv = document.getElementById("FieldsTree");
	var oElements = oTreeDiv.getElementsByTagName("input");
	for(var i=0; i<oElements.length; i++) {
		if(oElements[i].type != "checkbox")
			continue;
		if(oElements[i].disabled)
			continue;
		if(oElements[i].checked) {
			insertOptions(oElements[i].value);
		}
	}
	var sSelectedFields = getSelectedFields();
	refreshTree(sSelectedFields);
}
function insertAllFields() {
	var oTreeDiv = document.getElementById("FieldsTree");
	var oElements = oTreeDiv.getElementsByTagName("input");
	for(var i=0; i<oElements.length; i++) {
		if(oElements[i].type != "checkbox")
			continue;
		if(oElements[i].disabled)
			continue;
		insertOptions(oElements[i].value);
	}
	var sSelectedFields = getSelectedFields();
	refreshTree(sSelectedFields);
}
function removeFields() {
	var oSelect = document.getElementById("FieldsSelect");
	if(oSelect.selectedIndex<0)return;
	var nFirstIndex = -1;
	for(var i=oSelect.options.length; i>0; i--) {
		var oOption = oSelect.options[i-1];
		if(!oOption.selected)
			continue;
		oSelect.removeChild(oOption);
		nFirstIndex = i-1;
	}
	var sSelectedFields = getSelectedFields();
	refreshTree(sSelectedFields);
	var nOptCnt = oSelect.options.length;
	if(nOptCnt > nFirstIndex) {
		oSelect.options[nFirstIndex].selected = true;
	}
	else if(nOptCnt > 1){
		oSelect.options[nOptCnt-1].selected = true;
	}
}
function removeAllFields() {
	var oSelect = document.getElementById("FieldsSelect");
	for(var i=oSelect.options.length; i>0; i--) {
		var oOption = oSelect.options[i-1];
		oSelect.removeChild(oOption);
	}
	var sSelectedFields = getSelectedFields();
	refreshTree(sSelectedFields);
}
function moveUpFields() {
	var oSelect = document.getElementById("FieldsSelect");
	for(var i=0; i<oSelect.options.length; i++) {
		var oOption = oSelect.options[i];
		if(!oOption.selected)
			continue;
		moveNode(oOption, "up");
	}
}
function moveDownFields() {
	var oSelect = document.getElementById("FieldsSelect");
	for(var i=oSelect.options.length; i>0; i--) {
		var oOption = oSelect.options[i-1];
		if(!oOption.selected)
			continue;
		moveNode(oOption, "down");
	}
}
function moveNode(_oNode, _sUpOrDown) {
	if(_sUpOrDown == "up") {
		var oPrevious = _oNode.previousSibling;
		if(oPrevious == null || oPrevious.selected)
			return false;
		_oNode.parentNode.insertBefore(_oNode, oPrevious);
	} else if(_sUpOrDown == "down") {
		var oNext = _oNode.nextSibling;
		if(oNext == null || oNext.selected)
			return false;
		_oNode.parentNode.insertBefore(oNext, _oNode);
	}
	return true;
}
function getSelectedFields() {
	var oSelect = document.getElementById("FieldsSelect");
	var arrFields = new Array();
	for(var i=0; i<oSelect.options.length; i++) {
		var oOption = oSelect.options[i];
		arrFields.push(oOption.value);
	}
	return arrFields.toString();
}
</script>
</BODY>
</HTML>