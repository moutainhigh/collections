<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.addHeader("Cache-Control","no-store"); //Firefox
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1);
	response.setDateHeader("max-age", 0);
%>
<%
try{
%>
<%@include file="../include/public_processor.jsp"%>
<%@ page import="com.trs.components.common.message.Messages" %>
<%
	Messages messages = (Messages)processor.excute("message", "query");
	out.clear();
%><result><%=messages.size()%>;<%=messages.getIdListAsString()%></result>
<%
}catch(WCMException ex){
	if(ex.getErrNo()==ExceptionNumber.ERR_USER_NOTLOGIN){
		return;
	}
	throw ex;
}
%>