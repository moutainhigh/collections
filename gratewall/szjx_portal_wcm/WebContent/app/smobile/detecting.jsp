<%@ page contentType="text/html;charset=utf-8" %>

<%@ page import="com.trs.mobile.MobileCreator"%>

<%@include file="../include/public_server.jsp"%>
<%	
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_UNKNOWN, "您不是管理员，不能执行此操作！");
	}
%>
<%
	MobileCreator creator = MobileCreator.getInstance();
	out.clear();
	out.print(creator.getCurrMessage());%>