<%
/** Title:			user_manager.jsp
 *  Description:
 *		WCM5.2 用户的状态和在组织中是否为管理员的管理页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see user_manager.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server_nologin.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nUserId = currRequestHelper.getInt("UserId", 0);
	String strUserName = CMyString.showNull(currRequestHelper.getString("UserName"));
	
//5.权限校验

//6.业务代码
	boolean bReturn = false;
	User currUser = User.findByName(strUserName);

//7.结束
	out.clear();
	if("system".equals(strUserName.toLowerCase())) {
		out.print("true");
	} else if(nUserId == 0) {
		out.print(currUser!=null);
	}
	else {
		out.print(currUser!=null && currUser.getId()!=nUserId);
	}
%>