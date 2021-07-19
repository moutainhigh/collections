<%
/** Title:			role_delete.jsp
 *  Description:
 *		WCM5.2 角色删除页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see role_delete.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Roles" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.IRoleService" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	String strRoleIds = CMyString.showNull(currRequestHelper.getString("RoleIds"));
	Roles currRoles = null;
	currRoles = Roles.findByIds(loginUser, strRoleIds);
//5.权限校验
//6.业务代码
	IRoleService currRoleService = (IRoleService)DreamFactory.createObjectById("IRoleService");
	currRoleService.delete(currRoles);
//7.结束
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 角色删除页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</head>
<BODY>
<SCRIPT LANGUAGE="JavaScript">
var arReturn = new Array();
arReturn[0] = true;
arReturn[1] = "0";
arReturn[2] = "0";

window.returnValue = arReturn;
window.close();
</SCRIPT>
</BODY>
</HTML>