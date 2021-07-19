<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="com.yunpan.bean.User"%>
<%@page import="com.yunpan.dao.UserDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");	
	response.setCharacterEncoding("UTF-8");
	String account = request.getParameter("account");
	String password = request.getParameter("password");
	password = StringUtils.saltPassword("keke", password);
	if(StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(password)){
		//根据账号和密码查询用户信息是否存在
		User user = UserDao.isExsitLogin(account, password);
		if(user==null){
			out.print("error");
		}else{
			session.setAttribute("user",user);
			out.print("success");
		}
	}else{
		out.print("empty");
	}
%>