<%--
/** Title:			infoview_list.jsp
 *  Description:
 *		WCM5.2 自定义表单的列表页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.04.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.04.12	fcr created
 *		2006.05.23	wenyh 添加表单的删除前的提示确认
 *		2006.08.29	wenyh 添加分发发布相关资源文件的方法(相关文件放置在infoview/infoview_pubsrc目录下)
 *
 *  Parameters:
 *		see infoview_list.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%
	out.println(request.getRequestURI());
	out.println("<br>");
	out.println(request.getRequestURL());
	out.println("<br>");
	out.println(request.getServletPath());
	out.println("<br>");
%>