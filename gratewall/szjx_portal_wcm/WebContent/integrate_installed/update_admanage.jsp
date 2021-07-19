<%--
/** Title:			config_list.jsp
 *  Description:
 *		处理系统配置的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-05 10:09:22
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-05 / 2005-04-05
 *	Update Logs:
 *		NZ@2005-04-05 产生此文件
 *
 *  Parameters:
 *		see config_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.service.IAdmanageConfigService"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%
//4.初始化(获取数据)
				
	

	//5.权限校验

	//6.业务代码
	IAdmanageConfigService configSrv = (IAdmanageConfigService) DreamFactory
		.createObjectById("IAdmanageConfigService");
	
	
	configSrv.updateAdManageService();
	
	//7.结束
	out.clear();

%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2
处理添加修改系统配置:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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