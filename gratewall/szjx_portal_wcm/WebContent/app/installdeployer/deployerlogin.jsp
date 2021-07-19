<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FilenameFilter" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ include file="../include/public_license_checker.jsp"%>
<%
	//检查是否已经登录
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(currLoginHelper.checkLogin()){
		response.sendRedirect("handler_execute_index.jsp"); 
		return;																    
	}
	Cookie[] cookies = request.getCookies();
	/* 每次登陆或者刷新登陆页面都对session进行清空 */
	if(!session.isNew()){
		session.invalidate();//清空session
	}
	String randomBackground = "0";
	String background = null;
	String userId = null;
	out.clear();%><?xml encoding="UTF-8" version="1.0"?><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
	<title>TRS WCM 部署登录页</title>
    <link rel="stylesheet" type="text/css" href="../login.css">
</head>
<script language="javascript">
function validate() {
	var frmAction = $('frmAction');
	var focusUserName = function(){
		frmAction.UserName.select();
	}
	var focusPassWord = function(){
		frmAction.PassWord.focus();
	}
	var sUserName = frmAction.UserName.value;
	if(sUserName.byteLength() > 20){
		alert(WCMLANG["LOGIN_LARGERLENGTH_WARNING"],focusUserName);
		return false;
	}
	if(sUserName.toLowerCase() == "system") {
		alert(String.format(
			WCMLANG["LOGIN_ISSYSTEM_WARNNING"], sUserName),
			focusUserName
		);
		return false;
	}
	if(sUserName == ''){
		alert(WCMLANG["LOGIN_NOUSERNAME_WARNNING"], focusUserName);
		return false;
	}
	var sPassword = frmAction.PassWord.value;
	if(sPassword == ''){
		alert(WCMLANG["LOGIN_NOPASSWORD_WARNNING"], focusPassWord);
		return false;
	}	
	return true;
}
</script>
<body>
	<div class="container"> 
		<div class="logo"></div>
		<table width="439" height="122" border="0" cellpadding="0" cellspacing="0">
		<form id="frmAction" method="post" action="./deployerlogin_dowith.jsp" onsubmit="return validate();">
		<input name="FromUrl" type="hidden" value="./handler_execute_index.jsp">
		<tr valign="top"> 
			<td width="232" class="input_bg">
			<table width="232" height="64" border="0" cellpadding="0" cellspacing="0">
				<tr> 
				  <td width="72" height="27">&nbsp;</td>
				  <td width="160" valign="bottom"><input name="UserName" type="text" class="input12" size="16"></td>
				</tr>
				<tr>
				  <td height="37">&nbsp;</td>
				  <td valign="bottom"><input name="PassWord" type="password" class="input12" size="16"></td>
				</tr>
			  </table>
			  <table width="232" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td width="139" height="46" align="right" valign="bottom">
						<input id="login" type="image" src="../images/login/log.gif" width="78" height="23" border="0">
					</td>
					<td width="93" align="right" valign="bottom">
						<img src="../images/login/reset.gif" width="93" height="22" border="0" id="resetBtn">
					</td>
				</tr>
			  </table>
			</td>
			<td width="207" class="help_bg">
				<table width="207" height="98" border="0" cellpadding="0" cellspacing="0">
				<tr align="right" valign="bottom"> 
				  <td width="139">
					<a href="/wcm/console/auth/reg_newuser.jsp" target="_blank" id="linkRegNew" wcm_wrap="regNew" mouseout_wrap="../images/login/sigin.gif" mouseover_wrap="../images/login/sigin1.gif">
						<img src="../images/login/sigin.gif" id="regNew" width="56" height="15" border="0" style="margin-bottom:-5px"></a>
					</td>
				  <td width="10"><img src="../images/login/cline.gif" width="10" height="15"></td>
				  <td width="67">
					<a href="http://forum.trs.com.cn/bbs.jsp" target="_blank" id="linkHelp" wcm_wrap="help" mouseout_wrap="../images/login/helpbutton.gif" mouseover_wrap="../images/login/helpbutton1.gif">
						<img src="../images/login/helpbutton.gif" id="help" width="67" height="15" border="0" style="margin-bottom:-5px"></a>
				</td>
				</tr>
			  </table>
			</td>
		</tr>
		</form>
		</table>
		<div class="copyright"></div>
	</div>
	<div class="communicate"></div>
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/system.js"></script>
</body>
</html>