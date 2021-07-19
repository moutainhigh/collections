<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%
	out.clear();
	String rand = (String)session.getAttribute("rand");
	if(rand == null || !rand.equals(request.getParameter("RAND"))){
		out.print("false");
		return;
	}
	out.print("true");
%>