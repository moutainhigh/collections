<%
/** Title:			user_add_to_role.jsp
 *  Description:
 *		WCM5.2 从角色中删除用户页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-16 10:36:04
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-16 / 2004-12-16	
 *	Update Logs:
 *		wsw@2004-12-16 产生
 *
 *  Parameters:
 *		see user_add_to_role.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.service.IRoleService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nRoleId  = currRequestHelper.getInt("RoleId", 0);
	String sUserIds = currRequestHelper.getString("UserIds");
	Role currRole = null;
	Users currUsers = null;
	currRole = Role.findById(nRoleId);
	if(currRole == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nRoleId+"]的角色失败！");
	}
	currUsers = Users.findByIds(loginUser, sUserIds);
	if(currUsers==null){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "获取用户集合失败！");
	}
//5.权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限在本角色中删除用户！");
	}
//6.业务代码
	IRoleService currRoleService = (IRoleService)DreamFactory.createObjectById("IRoleService");
	User currUser = null;
	for(int i=0; i<currUsers.size(); i++){
		try{
			currUser = (User)currUsers.getAt(i);
			currRoleService.removeUser(currUser, currRole);
		}catch(Exception e){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "在当前角色删除第 "+i+" 个用户时失败！", e);
		}
	}
//7.结束
	
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 从角色中删除用户页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
	<BASE TARGET="_self">
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>

<BODY topmargin="0" leftmargin="0">
	<%if(IS_DEBUG){%>
		<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
	<%}%>
<SCRIPT LANGUAGE="JavaScript">
	window.close();
</SCRIPT>
</BODY>
</HTML>