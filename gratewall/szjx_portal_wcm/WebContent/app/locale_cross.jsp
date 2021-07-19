<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="include/public_server_nologin.jsp"%>


<%
	String sLocale = CMyString.showNull(request.getParameter("locale"), "zh_CN");
	
	//检查是否已经登录
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(currLoginHelper.checkLogin()){
		session.setAttribute("locale", sLocale);
		LocaleServer.setFavorLanguage(currLoginHelper.getLoginUser(), sLocale);
		String sQuery = CMyString.showNull(request.getQueryString());
		//防止CRLF注入，去除回车换行
		sQuery = sQuery.replaceAll("(?i)%0d|%0a","");
		response.sendRedirect("main.jsp?" + sQuery);
		return;
	}

	response.sendRedirect("http://enwcm.haier.com/wcm/");
%>