<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 构建JSPRequestProcessor对象
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	// 2 调用服务删除微博
	oProcessor.excute("wcm61_scmmicrocontent", "delete");//没有返回类型
	out.print(1);
%>