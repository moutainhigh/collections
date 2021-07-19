<%@page import="com.yunpan.util.StringUtils"%>
<%@page import="com.yunpan.dao.ResourceDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//获取客户端发送过来的参数
	String ids = request.getParameter("ids");//为什么是string而不是数组
	if(StringUtils.isNotEmpty(ids)){
		//调用批量删除的方法
		boolean flag = ResourceDao.deleteResources(ids);
		if(flag){
			out.print("success");
		}else{
			out.print("fail");
		}
	}else{
		out.print("fail");
	}
%>