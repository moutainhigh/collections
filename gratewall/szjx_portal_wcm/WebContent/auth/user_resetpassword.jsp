<%
/** Title:			user_resetpassword.jsp
 *  Description:
 *		WCM5.2 重置用户密码
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			nz
 *  Created:		2005-6-1
 *  Vesion:			1.0
 *  Last EditTime:		
 *	Update Logs:
 *		nz@2005-6-1 产生
 *
 *  Parameters:
 *		see user_resetpassword.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.service.impl.UserService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nUserId   = currRequestHelper.getInt("UserId",  0);
	User  currUser  = null;
	currUser = User.findById(nUserId);
	if(currUser == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nUserId+"]的用户失败！");
	}
	
//5.权限校验

//6.业务代码
	UserService currUserService = (UserService)DreamFactory.createObjectById("IUserService");
	currUserService.resetPassword(currUser);
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 重置用户密码::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
window.returnValue = true;
window.close();
</SCRIPT>
</BODY>
</HTML>