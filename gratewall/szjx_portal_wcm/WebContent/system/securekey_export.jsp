<%--
/** Title:			securekey_export.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“WCM52交互密钥的导出”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2006-05-22 13:51
 *  Vesion:			1.0
 *  Last EditTime:	2006-05-22/2006-05-22
 *	Update Logs:
 *		wenyh@2006-05-22 created the file 
 *
 *  Parameters:
 *		see securekey_export.xml
 *
 */
--%>
 
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.service.ISecurityService" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,"对不起,只有系统管理员才能删除密钥!");
	}

	int nSecureKeyId = currRequestHelper.getInt("SecureKeyId",0);
	boolean zIsPrivate = currRequestHelper.getBoolean("IsPrivateKey",false);
	ISecurityService service = (ISecurityService)DreamFactory.createObjectById("ISecurityService");
	String sResult = service.exportKey(zIsPrivate,nSecureKeyId);
	
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 密钥导出::::::::::::::::::::::::::::::::::::::::::::::::::::..</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>
<SCRIPT LANGUAGE="JavaScript">
	window.returnValue = "<%=PageViewUtil.toHtmlValue(sResult)%>";
	window.close();
</SCRIPT>
</BODY>
</HTML>