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
<%@ page import="java.util.ArrayList" %>
<%-- ----- WCM IMPORTS END ---------- --%>
<%@include file="./infoview_public_include.jsp"%>
<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	IInfoViewService currService = ServiceHelper.createInfoViewService();
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	InfoView infoview = currService.findById(nInfoViewId);
	if (infoview == null) {
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("infoview_addedit_setting.jsp.fail2_get_infoviewId", "获取ID为[{0}]的自定义表单失败！"), new int[]{nInfoViewId}));
	}
	int nSettingMode = currRequestHelper.getInt("SettingMode", 1);
	String strSelectedFields = "";
	String sPropertyName = "";
	boolean bCanSelectOneField = false;
	if(nSettingMode==1){
		sPropertyName = "OutlineFields";
		strSelectedFields = CMyString.showNull(infoview.getOutlineFields());
		if(CMyString.isEmpty(strSelectedFields)){
			strSelectedFields = "_EDIT,DOCTITLE,_PREVIEW,CRUSER,CRTIME,_DOCSTATUS";
		}
	}
	else if(nSettingMode==2){
		sPropertyName = "SearchFields";
		strSelectedFields = CMyString.showNull(infoview.getSearchFields());
		if(CMyString.isEmpty(strSelectedFields)){
			strSelectedFields = "DOCTITLE,CRUSER,CRTIME,_DOCSTATUS,DOCNO,RANDOMSERIAL";
		}
	}
	else if(nSettingMode==3){
		sPropertyName = "DocContentPattern";
		strSelectedFields = CMyString.showNull(infoview.getDocContentPattern());
		if(CMyString.isEmpty(strSelectedFields)){
			strSelectedFields = "DOCTITLE,CRUSER,CRTIME,DOCNO";
		}
	}
	else if(nSettingMode==4){
		sPropertyName = "OrderField";
		strSelectedFields = CMyString.showNull(infoview.getOrderField());
		if(CMyString.isEmpty(strSelectedFields)){
			strSelectedFields = "CRTIME";
		}
		bCanSelectOneField = true;
	}
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview.advance.setting.jsp.title">选择数据项</TITLE>
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
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<link rel="StyleSheet" href="xmltree.css" type="text/css" />
<script type="text/javascript" src="xmltree.js"></script>
<script>
var m_sType = '<%=bCanSelectOneField ? "radio" : "checkbox"%>';
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + encodeURIComponent(value));
	}
	return rst.join('&');
}
Ext.apply(String.prototype, {
	byteLength : function(){
		var length = 0;
		this.replace(/[^\x00-\xff]/g,function(){length++;});
		return this.length+length;
	}
});

function Ok() {
	if(!valid("<%=sPropertyName%>", getSelectedFields())){
		return false;
	}
	new ajaxRequest({
		url : 'infoview_simple_save.jsp',
		contentType : 'application/x-www-form-urlencoded',
		parameters : $toQueryStr({
			InfoViewId : <%=nInfoViewId%>,
			PropertyName : '<%=sPropertyName%>',
			<%=sPropertyName%> : getSelectedFields()
		}),
		onComplete : function(trans){
			Cancel();
		},
		onFailure : function(trans){
			if(confirm(wcm.LANG['INFOVIEW_DOC_106'] || "请求发生异常，您是否需要查看更详细的原因？")){
				alert(trans.responseText);
			}
		}
	});
}
var m_PropsValid = {
	'outlinefields' : [1000, wcm.LANG['INFOVIEW_DOC_107'] || '自定义视图'],
	'searchfields' : [1000,wcm.LANG['INFOVIEW_DOC_108'] || '检索字段'],
	'doccontentpattern' : [1000, wcm.LANG['INFOVIEW_DOC_109'] ||'正文构造器'],
	'orderField' : [300, '排序字段']
};
function valid(_sPropName, _sPropValue){
	var nOffSetLen = 0;
	if(_sPropName.toLowerCase() == "outlinefields" 
		|| _sPropName.toLowerCase() == "searchfields" ){
		nOffSetLen = _sPropValue.split(",").length * 14
	}
	var oProp = m_PropsValid[_sPropName.toLowerCase()];
	if(oProp && oProp[0]<(_sPropValue.byteLength() + nOffSetLen)){
		alert( String.format('{0}长度超出字段限制[限制长度:{1},当前长度:{2}]!',oProp[1],oProp[0],(_sPropValue.byteLength() 
			+ nOffSetLen)));		
		return false;
	}
	return true;
}
function Cancel() {
	parent.Cancel();
}
function refreshTree(_sDefaultValue) {
	refreshInfoViewTree(_sDefaultValue);
	refreshSystemTree(_sDefaultValue);
}
var oTree;
function refreshInfoViewTree(_sDefaultValue) {
	var sXMLString = $("TemplateXML").value;
	oTree = XMLTree.createInstance(sXMLString, m_sType, _sDefaultValue, _sDefaultValue);
	$("InfoViewFieldsTree").innerHTML = oTree.toString();
	refreshSystemTree(_sDefaultValue);
}
var oSystemTree;
var oSystemFieldTextMap = {
	DOCTITLE	: wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",
	CRTIME		: wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",
	CRUSER		: wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",
	POSTUSER	: wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",
	_DOCSTATUS	: wcm.LANG['INFOVIEW_DOC_99'] || "文档状态",
	DOCNO : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",
	RANDOMSERIAL  : wcm.LANG['INFOVIEW_DOC_101'] || "查询编号",
	_PREVIEW  : wcm.LANG['INFOVIEW_DOC_102'] || "预览",
	_EDIT  : wcm.LANG['INFOVIEW_DOC_103'] || "编辑"
};
<%	
	if(nSettingMode==1){
%>
var arrSystemFields = [
	{name  : wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",	value : "DOCTITLE"},
	{name  : wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",	value : "CRTIME"},
	{name  : wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",		value : "CRUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",		value : "POSTUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_99'] || "状态",		value : "_DOCSTATUS"},
	{name  : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",	value : "DOCNO"},
	{name  : wcm.LANG['INFOVIEW_DOC_101'] || "查询编号",	value : "RANDOMSERIAL"},
	{name  : wcm.LANG['INFOVIEW_DOC_102'] || "预览",		value : "_PREVIEW"},
	{name  :  wcm.LANG['INFOVIEW_DOC_103'] || "编辑",		value : "_EDIT"}
];
<%	
	}
	else if(nSettingMode==2 || nSettingMode==4){
%>
var arrSystemFields = [
	{name  : wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",	value : "DOCTITLE"},
	{name  : wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",	value : "CRTIME"},
	{name  : wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",		value : "CRUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",		value : "POSTUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_99'] || "状态",		value : "_DOCSTATUS"},
	{name  : wcm.LANG['INFOVIEW_DOC_100'] || "存档编号",	value : "DOCNO"},
	{name  : wcm.LANG['INFOVIEW_DOC_101'] || "查询编号",	value : "RANDOMSERIAL"}
];
<%
	}
	else if(nSettingMode==3){
%>
var arrSystemFields = [
	{name  : wcm.LANG['INFOVIEW_DOC_95'] || "文档标题",	value : "DOCTITLE"},
	{name  : wcm.LANG['INFOVIEW_DOC_96'] || "创建时间",	value : "CRTIME"},
	{name  : wcm.LANG['INFOVIEW_DOC_97'] || "发稿人",		value : "CRUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_98'] || "发稿人(外网)",		value : "POSTUSER"},
	{name  : wcm.LANG['INFOVIEW_DOC_99'] || "存档编号",	value : "DOCNO"}
];
<%	
	}
%>
function refreshSystemTree(_sDefaultValue) {
	oSystemTree = new dTree("oSystemTree");
	oSystemTree.add("SystemFieldsRoot", "-1", wcm.LANG['INFOVIEW_DOC_104'] || "系统数据项");
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
		oSystemTree.add(sValue, "SystemFieldsRoot", '<input type=' + m_sType + ' name="FieldIds" value="' + sValue + '" ' + sDisplay + '>' + sName);
	}
	$("SystemFieldsTree").innerHTML = oSystemTree.toString();
}
function insertOptions(_sDefaultValue) {
	var oSelect = $("FieldsSelect");
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
function window_onload() {
	var sDefaultValue = "<%=strSelectedFields%>";
	refreshTree(sDefaultValue);
	if(m_sType != 'radio')
		insertOptions(sDefaultValue);
}
window.onload = window_onload;

function insertFields() {
	var oTreeDiv = $("FieldsTree");
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
	var oTreeDiv = $("FieldsTree");
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
	var oSelect = $("FieldsSelect");
	for(var i=oSelect.options.length; i>0; i--) {
		var oOption = oSelect.options[i-1];
		oSelect.removeChild(oOption);
	}
	var sSelectedFields = getSelectedFields();
	refreshTree(sSelectedFields);
}
function moveUpFields() {
	var oSelect = $("FieldsSelect");
	for(var i=0; i<oSelect.options.length; i++) {
		var oOption = oSelect.options[i];
		if(!oOption.selected)
			continue;
		moveNode(oOption, "up");
	}
}
function moveDownFields() {
	var oSelect = $("FieldsSelect");
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
	if(m_sType == 'radio'){
		return getRadioSelectedField();
	}
	var oSelect = $("FieldsSelect");
	var arrFields = new Array();
	for(var i=0; i<oSelect.options.length; i++) {
		var oOption = oSelect.options[i];
		arrFields.push(oOption.value);
	}
	return arrFields.toString();
}

function getRadioSelectedField() {
	var selectedValue = "";
	var oTreeDiv = $("FieldsTree");
	var oElements = oTreeDiv.getElementsByTagName("input");
	for(var i=0; i<oElements.length; i++) {
		if(oElements[i].type != "radio")
			continue;
		if(oElements[i].checked) {
			selectedValue = oElements[i].value;
			break;
		}
	}
	return selectedValue;
}

</script>
</HEAD>
<BODY style="margin-left:8;margin-bottom:0;margin-top:0;font-size:9pt">
<table border=0 cellspacing=0 cellpadding=0 width="100%" height="100%" style="background:#FFFFFF"">
<tbody>
	<tr>
		<td width="270">
			<div style="height:20px; width:260px; overflow: hidden;">
				<div style="margin-top:2px" WCMAnt:param="infoview.advance.setting.jsp.allowfeild">可用数据项:</div>
			</div>
			<div style="height:280px; width:270px; border:1px solid gray; overflow:auto" id="FieldsTree">
				<div style="width:245px; overflow:hidden" id="SystemFieldsTree">
				</div>
				<div style="width:245px; overflow:hidden" id="InfoViewFieldsTree">
				</div>
			</div>
		</td>
		<%
			if(!bCanSelectOneField){
		%>
		<td width="90" align="center">
			<div style="margin-top:25px; text-align:center; width:90px;">
				<input type="button" class="button" value="添加 >>" style="width:80px" onclick="insertFields()" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.addvalue">
				<br>
				<input type="button" class="button" value="<< 移除" style="width:80px" onclick="removeFields()" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.deletevalue">
			</div>
			<div style="margin-top:25px; text-align:center; width:90px;">
				<input type="button" class="button" value="全部添加 >>" style="width:80px" onclick="insertAllFields()" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.addallvalue">
				<br>
				<input type="button" class="button" value="<< 全部移除" style="width:80px" onclick="removeAllFields()" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.deleteallvalue">
			</div>
			<div style="margin-top:25px; text-align:center; width:90px;">
				<input type="button" class="button" value="上移" style="width:80px" onclick="moveUpFields()" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.moveupvalue">
				<br>
				<input type="button" class="button" value="下移" style="width:80px" onclick="moveDownFields()" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.movedownvalue">
			</div>
		</td>
		<td width="270">
			<div style="height:20px; width:260px; overflow: hidden;">
				<div style="margin-top:2px" WCMAnt:param="infoview.advance.seeting.jsp.currfield">当前数据项:</div>
			</div>
			<select id="FieldsSelect" name="FieldsSelect" multiple style="width:260px;height:280px;border:1px solid gray;">
			</select>
		</td>
		<%}%>
	</tr>
	<tr height="30">
		<td colspan="3" align="center">
			<input type="button" class="button" style="width:60px" value="确定" onclick="Ok()" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.okvalue">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="button" style="width:60px" value="取消" onclick="Cancel();" WCMAnt:paramattr="value:infoview.advance.seeting.jsp.cancelvalue">
		</td>
	</tr>
</tbody>
</table>
<form name="frmAction" id="frmAction" style="display:none;">
<textarea style="display:none" id="TemplateXML"><%=infoview.getTemplateFileContent()%></textarea>
</form>
</BODY>
</HTML>