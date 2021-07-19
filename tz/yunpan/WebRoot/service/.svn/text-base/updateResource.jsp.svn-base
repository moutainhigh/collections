<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="com.yunpan.bean.Resource"%>
<%@page import="com.yunpan.dao.ResourceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	/*
	 	业务：根据ID修改文件名称信息,利用ajax
	 	时间:2014-11-23 01:34
	 	作者:keke老师
	 	类:ResourceDao.deleteResource(Integer id)
	*/
	String id = request.getParameter("id");
	String name = request.getParameter("name");;
	if(StringUtils.isNotEmpty(id)){
		boolean flag = ResourceDao.updateResource(new Integer(id),name);
		if(flag){
			out.print("success");//request/response.getWriter()
		}else{
			out.print("fail");
		}
	}else{
		out.print("fail");
	}
%>