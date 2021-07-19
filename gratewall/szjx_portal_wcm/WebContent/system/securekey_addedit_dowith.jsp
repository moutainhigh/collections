<%--
/** Title:			securekey_addedit_dowith.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“WCM52交互密钥管理/列表”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2006-05-22 09:09
 *  Vesion:			1.0
 *  Last EditTime:	2006-05-22/2006-05-22
 *	Update Logs:
 *		wenyh@2006-05-22 created the file 
 *
 *  Parameters:
 *		see securekey_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.service.ISecurityService" %>
<%@ page import="com.trs.infra.support.security.SecureKey" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
	String sKeyName = currRequestHelper.getString("KeyName");
	if(CMyString.isEmpty(sKeyName)){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,"没有指定有效的密钥名称");
	}

	int nKeySize = currRequestHelper.getInt("KeySize",0);
	String sKeyAlg = currRequestHelper.getString("KeyAlg");
	if(CMyString.isEmpty(sKeyAlg)){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,"没有指定有效的密钥名称");
	}

	ISecurityService service = (ISecurityService)DreamFactory.createObjectById("ISecurityService");
	SecureKey secureKey = new SecureKey();
	secureKey.setKeyName(sKeyName);
	secureKey.setKeySize(nKeySize);
	secureKey.setAlgorithm(sKeyAlg);

	service.saveKey(secureKey);
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 新增系统密钥:::::::::::::::::::::::::::::::::::::..</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
if(window.opener){
	window.opener.CTRSAction_refreshMe();
	window.opener.focus();
	window.close();
}else{
	window.returnValue = true;
	window.close();
}

</SCRIPT>
</BODY>
</HTML>