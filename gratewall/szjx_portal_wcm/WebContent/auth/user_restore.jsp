<%
/** Title:			role_delete.jsp
 *  Description:
 *		WCM5.2 角色删除页面。
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
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.service.IUserService" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	String sUserIds = currRequestHelper.getString("UserIds");
	Users currUsers = null;
	currUsers = Users.findByIds(loginUser, sUserIds);
	if(currUsers == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取要删除的用户集合失败！");
	}
//5.权限校验
	//if(!AuthServer.hasRight(loginUser, currUsers, WCMRightTypes.ROLE_DEL)){
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有删除用户的权限！");
	}
//6.业务代码
	IUserService currUserService = (IUserService)DreamFactory.createObjectById("IUserService");
	currUserService.restore(currUsers);
//7.结束
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 角色删除页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</head>
<BODY topmargin="0" leftmargin="0">
	<%if(IS_DEBUG){%>
		<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
	<%}%>
<SCRIPT LANGUAGE="JavaScript">
	window.returnValue = true;
	window.close();
</SCRIPT>
</BODY>
</HTML>