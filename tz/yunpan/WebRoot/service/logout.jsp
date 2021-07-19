<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="com.yunpan.bean.User"%>
<%@page import="com.yunpan.dao.UserDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//注销所有，清空session里面存储的值	
	session.invalidate();
	//重定向到登陆页面中去
	response.sendRedirect("../login.jsp");
%>