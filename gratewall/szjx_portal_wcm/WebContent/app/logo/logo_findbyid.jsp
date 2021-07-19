<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>

<%@ page import="com.trs.components.wcm.publish.logo.Logo" %>
<%
	String r = "_error_";
	try{
		int id = Integer.parseInt(request.getParameter("LogoId"));
		Logo logo = Logo.findById(id);
		if(logo != null){
			r = String.valueOf(logo.getId());
		}
	}catch(Exception e){		
	}
	if(true){
		out.clear();
		out.print(r);
		return;
	}
%>