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
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@include file="./infoview_public_include.jsp"%>
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//1.初始化(获取数据)
//	int nSiteId    = currRequestHelper.getInt("SiteId",    0);
//	if (nSiteId == 0)
//	{
//		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入站点ID错误，必须传入一个有效的站点ID！");
//	}
//	WebSite oWebSite = (WebSite) WebSite.findById(nSiteId);
//	if (oWebSite == null)
//	{
//		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "没有找到ID为" + nSiteId + "]的站点");
//	}

	InfoView aInfoView = null;
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	if (nInfoViewId > 0){
		aInfoView = InfoView.findById(nInfoViewId);
		if (aInfoView == null){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("infoview.huoquid", "获取ID为[{0}]的自定义表单失败！"),new int[]{nInfoViewId}));
		}
	}else{
		aInfoView = InfoView.createNewInstance();
	}

//2.权限校验
	if (nInfoViewId > 0){
		if(!aInfoView.canEdit(loginUser)){
            throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, CMyString.format(LocaleServer.getString("infoview.lockerinfo","自定义表单[{0}]被用户［{1}]锁定！您不能修改！"),new String[]{String.valueOf(aInfoView.getId()),aInfoView.getLockerUserName()}));
		}
	}

	String sFileSelectDesc = (nInfoViewId > 0) ? LocaleServer.getString("infoview.label.changefile","更换文件") : LocaleServer.getString("infoview.label.choosefile", "选择文件");
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM <%= LocaleServer.getString("infoview.label.addedit_of_infoview", "添加/修改自定义表单") %>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
</HEAD>
<BODY onUnload="unlock(<%= nInfoViewId %>,<%= InfoView.OBJ_TYPE %>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./infoview_addedit_dowith.jsp" style="margin-top:0;display:none">
	<INPUT TYPE="hidden" NAME="SubmitType" id="SubmitType" value="0">
	<INPUT TYPE="hidden" NAME="InfoViewId" id="InfoViewId" value="<%= nInfoViewId %>">
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD align="center" valign="top" class="tanchu_content_td">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
<FORM NAME="frmData" id="frmData">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<TR>
			<TD align="left" nowrap><%= LocaleServer.getString("infoview.label.name_of_infoview", "表单名称") %>:</TD>
			<TD align="left" colspan="2">
				<INPUT TYPE="hidden" name="SITEID" value="<%= aInfoView.getSiteID() %>">
				<INPUT name="INFOVIEWNAME" id="infoviewName" WCMAnt:paramattr="infoview_addedit.jsp.desc_of_infoview" elname="表单名称" type="text" size="30" style="width:380px" value="<%= PageViewUtil.toHtmlValue(aInfoView.getName()) %>"
				pattern="string" max_len="50" not_null="1"/><span class="font_red">&nbsp;*</span>
			</TD>
		</TR>
		<TR>
			<TD align="left" nowrap><%= LocaleServer.getString("infoview.label.desc_of_infoview", "表单描述") %>:</TD>
			<TD align="left" colspan="2">
				<INPUT name="INFOVIEWDESC" id="infoviewDesc" WCMAnt:paramattr="infoview_addedit.jsp.desc_of_infoview"  elname="表单描述" type="text" size="30" style="width:380px" pattern="string" max_len="200" not_null="1"  value="<%= PageViewUtil.toHtmlValue(aInfoView.getDesc()) %>" onfocus="filedDesc();"><span class="font_red">&nbsp;*</span>
			</TD>
		</TR>
		<TR>
			<TD height="40" align="left" colspan="3">
				<IFRAME name="frmUploadFile" id="frmUploadFile" height="24" width="100%" frameborder="0" vspace="0" src="../file/file_upload.jsp?SelfControl=1&ShowText=1&AllowExt=XSN" scrolling="NO" noresize></IFRAME>
			</TD>
		</TR>
		<TR>
			<TD align="left" nowrap><%= LocaleServer.getString("infoview.label.file_of_infoview", "表单文件") %>:</TD>
			<TD align="left" colspan="2">
				<INPUT name="INFOPATHFILE" id="infopathfile" elname="<%= LocaleServer.getString("infoview.label.file_of_infoview", "表单文件")%>" type="text" size="30" style="width:380px" validation="required:'true',type:'string',max_len:'200',desc:'表单文件'" value="<%= PageViewUtil.toHtmlValue(aInfoView.getInfoPathFile()) %>" readonly="true"><span class="font_red">&nbsp;*</span>
			</TD>
		</TR>
		<TR>
			<TD align="left" nowrap>&nbsp;</TD>
			<TD align="left" height="50" colspan="2"><span>&nbsp;&nbsp;<%=LocaleServer.getString("infoview.message.uploaddesc","请选择您要使用的自定义表单文件，然后点击“上传”按钮．")%></span><BR>
				<span class="font_red">&nbsp;&nbsp;<%=LocaleServer.getString("infoview.message.uploaddesc2", "请注意，由MS InfoPath创建的表文件后缀名为XSN。")%></span>
			</TD>
		</TR>
		</TABLE>
	</TD>
	</TR>
</FORM>
	</TABLE>
</TD>
</TR>
</TABLE>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script> 
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- Component End -->
<SCRIPT LANGUAGE="JavaScript">
var m_cb = null;
function init(param, cb){
	m_cb = cb;
}
function onOk()
{
	if(!doValid())return false;
	var postData = {
		InfoViewId : $('InfoViewId').value,
		SubmitType : $('SubmitType').value,
		ObjectXML  : getPostData($('frmData'))
	}
	
	ProcessBar.start("<%=LocaleServer.getString("infoview.addedit.processbar", "创建表单")%>");
	
	new ajaxRequest({
		url : './infoview_addedit_dowith.jsp',
		method : 'post',
		parameters : $toQueryStr(postData),
		onSuccess : function(trans){
			ProcessBar.close();

			var sResponseText = trans.responseText;
			eval("var result="+sResponseText);
			if(result["error"]){
				Ext.Msg.alert(String.format(wcm.LANG.fail2save_infoview || "保存表单失败{0}?", result["error"]));
				//"<%=LocaleServer.getString("infoview.addedit.saveinfoviewerror", "保存表单失败")%>" + result["error"]);
			}
			else{
				m_cb.callback();
				m_cb.close();
			}
		}
	});
	return false;
}
function onNext(){
	if(!doValid())return false;
	$('SubmitType').value = 1;
	var postData = {
		InfoViewId : $('InfoViewId').value,
		SubmitType : $('SubmitType').value,
		ObjectXML  : getPostData(document.getElementById('frmData'))
	}
	ProcessBar.start("<%=LocaleServer.getString("infoview.addedit.processbar", "创建表单")%>");
	new ajaxRequest({
			url : './infoview_addedit_dowith.jsp',
			method : 'post',
			parameters : $toQueryStr(postData),
			onSuccess : function(_transport){
				ProcessBar.close();
				var sResponseText = _transport.responseText;
				eval("var result="+sResponseText);
				if(result["error"]){
					Ext.Msg.alert(String.format(wcm.LANG.fail2save_infoview || "保存表单失败{0}?", result["error"]));
				}
				if(result["id"]){
					var infoviewId = result["id"];
					var w = $openMaxWin('../infoview/infoview_addedit_setting.jsp?InfoViewId=' + infoviewId);
					m_cb.callback();
					m_cb.close(); 
				}	
			}
		});
	return false;
}
function doValid(){
	if($('infoviewName').value==''){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.name", "请输入表单名称")%>');return false;
	}
	if(byteLength($('infoviewName').value) > 50){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.namelength", "表单名称的长度不能大于50")%>');return false;
		return false;
	}
	if($('infoviewDesc').value==''){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.desc", "请输入表单描述")%>');
		filedDesc();
		return false;
	}
	if(byteLength($('infoviewDesc').value) > 150){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.namedesclength", "表单名称描述的长度不能大于200")%>');return false;
		return false;
	}
	var specialChars = checkSpecialChars($('infoviewName').value);
	if(specialChars){
		Ext.Msg.alert(String.format(wcm.LANG.inputcontainerrorchar || "您输入的表单名称不能包含以下特殊字符{0} ?",specialChars), function(){
			$('infoviewName').focus();
		});
		return false;
	}
	specialChars = checkSpecialChars($('infoviewDesc').value);
	if(specialChars){
		Ext.Msg.alert(String.format(wcm.LANG.inputcontainerrorchar || "您输入的表单描述不能包含以下特殊字符{0} ?",specialChars), function(){
			$('infoviewDesc').focus();
		});
		return false;
	}
	if($('infopathfile').value==''){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.selectinfofile", "请选择表单文件")%>');
		return false;
	}
	if(byteLength($('infopathfile').value) > 200){
		Ext.Msg.alert('<%=LocaleServer.getString("infoview.selectinfofilelength", "表单文件名称的长度不能大于200")%>');return false;
		return false;
	}
	return true;
}
function checkSpecialChars(_sCode) {
	var regExp = /[<>\[\]{}#*%$%&^!~\-`]/g;
	var sResult = _sCode.match(regExp)
	return Array_distinct(sResult);
}
function Array_distinct(_array){
	if(!_array || !_array.length) return _array;
	var newArray = new Array();
	for(var i=0; i<_array.length; i++){
		var oEl = _array[i];
		if(!oEl || Array_isExist(newArray, oEl)) continue;
		newArray[newArray.length] = oEl;
	}
	return newArray;
}
function Array_isExist(_array, _element){
	if(!_array || !_element) return false;
	if(!_array.length){
		return (_array == _element);
	}
	for(var i=0; i<_array.length; i++){
		if(_element == _array[i]) return true;
	}
	return false;
}
function notifyOnUploadFileError(_sMsg){
	Ext.Msg.alert(_sMsg);
}
function getPostData(frmData){
	var elements = frmData.getElementsByTagName('INPUT');
	if(elements.length==0)
	elements = frmData.elements;
	var result = ['<WCMOBJ><PROPERTIES>'];
	for (var i = 0; i < elements.length; i++){
		var element = elements[i];
		var name = element.name.toUpperCase();
		result.push("<", name, "><![CDATA[", element.value, "]]></", name, ">");
	}
	result.push('</PROPERTIES></WCMOBJ>');
	return result.join("");
}
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + value);
	}
	return rst.join('&');
}
function filedDesc(){
	var infoviewName = $('infoviewName').value;
	var infoviewDesc = $('infoviewDesc').value;
	if(infoviewDesc == '' && infoviewName != '') {
		$('infoviewDesc').value = infoviewName;
	}
}
function addFile(_sFilePath)
{
	if (_sFilePath == null || _sFilePath.length==0)
	{
		Ext.Msg.alert("<%=LocaleServer.getString("infoview.fileupinfo2", "请先点击“上传”，将文件上传到服务器后再重试！")%>");
		return;
	}
	if (_sFilePath.length < 4)
	{
		Ext.Msg.alert("<%=LocaleServer.getString("infoview.fileupinfo1", "文件名无效，不是标准的书生表单文件！")%>");
		return;
	}
	if (_sFilePath.indexOf(".xsn") < 0)
	{
		Ext.Msg.alert("<%=LocaleServer.getString("infoview.fileupinfo", "文件后缀不是XSN，不是标准的InfoPath表单文件！")%>");
		return;
	}
	$('infopathfile').value = _sFilePath;
}
function unlockObj(_nObjId, _nObjType){
	var postData = {
		ObjId :  _nObjId,
		ObjType : _nObjType
	};
	new ajaxRequest({
		url : '../include/object_unlock.jsp',
		method : 'post',
		parameters : $toQueryStr(postData),
		onFailure : function(_transport){
			Ext.Msg.alert('<%=LocaleServer.getString("infoview.unlockerror", "解锁失败")%>');
		}
	});
}
function unlock(_nObjId, _nObjType){
	if(_nObjId>0) {
		unlockObj(_nObjId, _nObjType);
	}
}
function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el);
	return el;
}
function $openMaxWin(_sUrl, _sName, _bReplace, _bResizable){
	var nWidth	= window.screen.width - 12;
	var nHeight = window.screen.height - 60;
	var nLeft	= 0;
	var nTop	= 0;
	var sName	= _sName || "";
	sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
	var oWin = window.open(_sUrl, sName, "resizable=" 
		+ (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" 
		+ nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no", _bReplace);
	if(oWin)oWin.focus();
	return oWin;
}
function byteLength(str){
	var length = 0;
	str.replace(/[^\x00-\xff]/g,function(){length++;});
	return str.length+length;
}
</SCRIPT>
</BODY>
</HTML>