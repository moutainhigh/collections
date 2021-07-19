<%
/** Title:			select_index.jsp
 *  Description:
 *		WCM5.2 通用选择页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-29 13:54
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-29 / 2004-12-29	
 *	Update Logs:
 *		WSW@2004-12-29 产生
 *		niuzhao@2005-12-22 页面的标题从“通用选择”修改为了“用户/组织选择”
 *
 *  Parameters:
 *		see select_index.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	String sUserIds = CMyString.showNull(currRequestHelper.getString("UserIds"));
	String sGroupIds = CMyString.showNull(currRequestHelper.getString("GroupIds"));
	String sRoleIds = CMyString.showNull(currRequestHelper.getString("RoleIds"));
	boolean bSingle = currRequestHelper.getBoolean("bSingle", true);
	int nbAllUser	= currRequestHelper.getInt("AllUser", 0);
	int nFromCb = currRequestHelper.getInt("_FROMCB_", 0);
	int nUserOrGroup = currRequestHelper.getInt("UserOrGroup", 0);
	int nbFromProcess =  currRequestHelper.getInt("FromProcess", 0);
	int nForSendMsg =  currRequestHelper.getInt("ForSendMsg", 0);

//5.权限校验
	
//6.业务代码

//7.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="select_index.jsp.title">TRS WCM 用户/组织选择::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
.ext-gecko .contentTb{
	height: 100%;
}
</style>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../../app/js/wcm52/CTRSHTMLElement.js"></script>
<script src="../../app/js/wcm52/CTRSHTMLTr.js"></script>  
<script src="../../app/js/wcm52/CTRSSimpleTabforselect.js"></script>
<script src="../../app/js/wcm52/CTRSArray.js"></script>
<script LANGUAGE="Javascript" src="../../app/js/wcm52/CWCMObj.js"></script>
<script LANGUAGE="Javascript" src="../../app/js/wcm52/CWCMObjHelper.js"></script>
<script LANGUAGE="Javascript" src="../../app/js/wcm52/CWCMConstants.js"></script>
<script LANGUAGE="Javascript" src="../../app/js/wcm52/CTRSArray.js"></script>  
<script LANGUAGE="Javascript" src="../../app/js/wcm52/CTRSButton.js"></script>  
<script src="../../app/js/wcm52/CTRSHashtable.js"></script>
<script src="../../app/js/wcm52/CTRSRequestParam.js"></script>
<script src="../../app/js/wcm52/CTRSAction.js"></script>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/userSelect.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!-- dialog  Start -->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script> 
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- dialog  End -->
<!-- CarshBoard Inner Start -->
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<script LANGUAGE="Javascript">
var m_sUserIds = "<%=CMyString.filterForJs(sUserIds)%>";
var m_sGroupIds = "<%=CMyString.filterForJs(sGroupIds)%>";
var m_sRoleIds = "<%=CMyString.filterForJs(sRoleIds)%>";
var m_nUserOrGroup = "<%=nUserOrGroup%>";

var m_arUserIds = m_sUserIds.split(",");
var m_arGroupIds = m_sGroupIds.split(",");
var m_arRoleIds = m_sRoleIds.split(",");
var m_cb = null;
<%
	if(nFromCb == 1){
%>
window.m_cbCfg = {
		btns : [
			{
				text : wcm.LANG['SELECT_USER_CONFIRM'] || '确定',
				cmd : function(){
					return onOk();
				}
			},
			{
				text : wcm.LANG['SELECT_USER_CANCEL'] || '取消'
			}
		]
	};
<%
	}
%>
function init(params, cb){
	m_cb = cb;
	if(m_nUserOrGroup==1){
		TRSSimpleTab.openItem(null, 1);
	}
}
function isExists(_arSrc, _sId){
	if(_arSrc == null || _arSrc.length <= 0)
		return false;
	for(var i=0; i<_arSrc.length; i++){
		if(_arSrc[i] == _sId) return true;
	}
	return false;
}

function addId(_nType, _nId){
	if(_nId == null || _nId <= 0)
		return;

	var arIds = new Array();
	var arName = "";
	switch(_nType){
	case OBJTYPE_USER:
		arIds = m_arUserIds;
		arName = "m_arUserIds";
		break;
	case OBJTYPE_GROUP:
		arIds = m_arGroupIds;
		arName = "m_arGroupIds";
		break;
	case OBJTYPE_ROLE:
		arIds = m_arRoleIds;
		arName = "m_arRoleIds";
		break;
	default:
		alert(String.format( "无效的类型传入[{0}]！",_nType));
		return;
	}

	if(isExists(arIds, _nId))
		return;

	if(arIds[0] == null){
		arIds[0] = _nId;
	} else {
		arIds[arIds.length] = _nId;
	}
	eval(arName+" = arIds;");
}

function removeId(_nType, _nId){
	if(_nId == null || _nId <= 0)
		return;

	var arIds = new Array();
	var arName = "";
	switch(_nType){
	case OBJTYPE_USER:
		arIds = m_arUserIds;
		arName = "m_arUserIds";
		break;
	case OBJTYPE_GROUP:
		arIds = m_arGroupIds;
		arName = "m_arGroupIds";
		break;
	case OBJTYPE_ROLE:
		arIds = m_arRoleIds;
		arName = "m_arRoleIds";
		break;
	default:
		alert(String.format( "无效的类型传入[{0}]！",_nType));
		return;
	}

	TRSArray.remove(arIds, _nId);
	eval(arName+" = arIds;");
}
</script>
</HEAD>
<BODY style="background:#ededed;">
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<!--~--- ROW5 ---~-->
<TR>
<TD align="center" valign="top" class="tanchu_content_td">
<!--~== TABLE4 ==~-->
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" class="contentTb">
<!--~-- ROW10 --~-->
<TR>
<TD align="left" width="80%">
	<!--~== TABLE1 ==~-->
	<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<!--~--- ROW1 ---~-->
	<TR>
	<TD height="25">
			<!--开始标签列表-->
			<script>
				TRSSimpleTab.nCurrIndex = 0;
				TRSSimpleTab.addItem(wcm.LANG['SELECT_USER_USERSELECT'] || "用户选择");
				TRSSimpleTab.addItem(wcm.LANG['SELECT_USER_ORGELECT'] || "组织选择");
				TRSSimpleTab.draw();
			</script>
			<!--结束标签列表-->	
	</TD>
	</TR>
	
	<TR>
	<TD width="100%" bgcolor="#FFFFFF" style="BORDER-left: #a6a6a6 1px solid; BORDER-right: #a6a6a6 1px solid; BORDER-bottom: #a6a6a6 1px solid" align="center" valign="top" class="contentTb">
			<div id="id_TRSSimpleTab0" style="display:inline;width:100%;height:400px">
				<IFRAME src="../include/user_select_index.jsp?AllUser=<%=nbAllUser%>&FromProcess=<%=nbFromProcess%>&ForSendMsg=<%=nForSendMsg%>" id="user_select" name="user_select" frameborder="NO" border="0" framespacing="0" width="100%" height="100%"></IFRAME>
			</div>
			<div id="id_TRSSimpleTab1" style="display:none;width:100%;height:400px;">
				<IFRAME src="../include/group_select_index.jsp?TreeType=0" id="group_select" name="group_select" frameborder="NO" border="0" framespacing="0" width="100%" height="100%"></IFRAME>
			</div>
			<BR>
	</TD>
	</TR>
	<!--~ END ROW10 ~-->
	</TABLE>
	<!--~ END TABLE1 ~-->
</TD>
<TD width="20%" height="100%">
<div style="width:100%;height:400px;">
<IFRAME src="../include/selected_list.jsp?UserIds=<%=CMyString.filterForHTMLValue(sUserIds)%>&GroupIds=<%=CMyString.filterForHTMLValue(sGroupIds)%>&RoleIds=<%=CMyString.filterForHTMLValue(sRoleIds)%>&FromProcess=<%=nbFromProcess%>" id="selected_list" name="selected_list" frameborder="NO" border="0" framespacing="0" width="100%" height="100%"></IFRAME>
</div>
</TD>
</TR>
</TABLE>
<!--~ END TABLE4 ~-->
</TD>
</TR>
<!--~- END ROW5 -~-->
</TABLE>
<!--~ END TABLE1 ~-->

<script LANGUAGE="Javascript">
var subWindow_right = document.getElementById("selected_list").contentWindow;

function getWindow(){
	subWindow_right = document.getElementById("selected_list").contentWindow;
}

function getRightWindow(){
	if(subWindow_right != null && subWindow_right.WCMOperatorHelper != null)
		return subWindow_right;
	return null;
}

function setOperator(_nType, _nId, _sName, _bAdd){
	if(_bAdd){
		getRightWindow().WCMOperatorHelper.insert(_nType, _nId, _sName);
	} else {
		getRightWindow().WCMOperatorHelper.removeOperator(_nType, _nId, _sName);
	}
}

function getUserIds(){
	return clearStr(TRSArray.toString(m_arUserIds, ","));
}

function canDoGetIds(){
	var bResult = (getRightWindow() != null);
	return bResult;
}

function getGroupIds(){
	return clearStr(TRSArray.toString(m_arGroupIds, ","));
}

function getRoleIds(){
	return m_sRoleIds;
}

function getUserNames(){
	return clearStr(getRightWindow().WCMOperatorHelper.getUserNames());
}

function getGroupNames(){
	return clearStr(getRightWindow().WCMOperatorHelper.getGroupNames());
}

function getRoleNames(){
	return clearStr(getRightWindow().WCMOperatorHelper.getRoleNames());
}

function getFrameName(_nType){
	switch(_nType){
	case 204:
		return "user_select";
	case 201:
		return "group_select";
	case 203:
		return "role_select";
	default:
		alert(String.format("无效的对象类型[window.top.getFrameName]"));
		return "";
	}
}

function getCheckBoxName(_nType){
	switch(_nType){
	case 204:
		return "UserIds";
	case 201:
		return "GroupIds";
	case 203:
		return "RoleIds";
	default:
		alert(String.format("无效的对象类型[window.top.getFrameName]"));
		return "";
	}
}

function notify(_nType, _nId){
	var sFrameName = getFrameName(_nType);
	var sCheckBoxName = getCheckBoxName(_nType);

	if(sFrameName == null || sFrameName.length <= 0){
		alert(String.format("没有找到正确的Frame名称！[window.top.notify]"));
		return;
	}

	if(sCheckBoxName == null || sCheckBoxName.length <= 0){
		return;
	}

	var subWindow_left = document.getElementsByName(sFrameName)[0].contentWindow.getMainWin();

	
	var arCheckBoxs = eval("subWindow_left.document.getElementsByName('"+sCheckBoxName+"')");
	if(arCheckBoxs == null){
		return;
	}
	
	for(var i=0; i<arCheckBoxs.length; i++){
		if(arCheckBoxs[i].value == _nId){
			arCheckBoxs[i].checked = false;
		}
	}
}

function onOk(){
	var arReturnValue = new Array();
	arReturnValue[0] = getUserIds();
	arReturnValue[1] = getUserNames();
	arReturnValue[2] = getGroupIds();
	arReturnValue[3] = getGroupNames();
	if(m_cb){
		m_cb.hide();
		m_cb.callback(arReturnValue);
	}
	else{
		var cbr = wcm.CrashBoarder.get('select_user_or_group');
		cbr.hide();
		cbr.notify(arReturnValue);
	}
	return false;
}

function clearStr(_string) {
	var strSearch=/,/i;
	if(_string==null)
		return "";
	if(_string.search(strSearch)==0) {
		return _string.substring(1);
	}
	return _string;
}
</script>
</BODY>
</HTML>