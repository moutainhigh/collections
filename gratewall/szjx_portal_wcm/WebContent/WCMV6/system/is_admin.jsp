\<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM6 操作产生器
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2006-11-21 18:16 上午
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		
 *
 */
%>

<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<!------- WCM IMPORTS BEGIN ------------>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	out.clear();
	out.println("var global_IsAdmin = " + loginUser.isAdministrator());
%>
<%
	Cookie useridCookie = new Cookie("userid", String.valueOf(loginUser.getId()));
	useridCookie.setMaxAge(24 * 60 * 60 * 1000 * 30);
	useridCookie.setPath(request.getContextPath());
	response.addCookie(useridCookie);
%>