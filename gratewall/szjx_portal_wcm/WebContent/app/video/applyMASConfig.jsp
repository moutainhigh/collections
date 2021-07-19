<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.dl.util.ConfigFileModifier" %>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>

<%
	String ip = request.getParameter("ip");
	String chnId = request.getParameter("chnId");
	if(ip ==null || chnId == null){
		request.setAttribute("errorMessage", LocaleServer.getString("applyMASConfig.jsp.setupfail","设置失败！"));
		request.getRequestDispatcher("configMAS.jsp").forward(request, response);
	
	}
	else {
		//session.setAttribute("loginModel", model);
		//response.sendRedirect(request.getContextPath()+"/authors/index.jsp");
		//request.getRequestDispatcher("/authors/index.jsp").forward(request, response);
		VSConfig.setMASIP(ip);
		VSConfig.setChnId(chnId);
		request.setAttribute("errorMessage", LocaleServer.getString("applyMASConfig.jsp.setupsuccess","设置成功！"));
		request.getRequestDispatcher("configMAS.jsp").forward(request, response);
	}
%>