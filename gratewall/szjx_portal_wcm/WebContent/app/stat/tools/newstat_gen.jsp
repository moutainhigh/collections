<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@page import="com.trs.infra.support.config.ConfigServer"%>
<%@page import="com.trs.infra.util.CMyString"%>
<%@page import="com.trs.components.stat.tool.StatPageGener"%>
<%@page import="java.io.File"%>
<%@include file="../../../include/public_server.jsp"%>
<%
if(!loginUser.isAdministrator()){
	out.println("Access Forbidden.");
	return;
}
String msg = "";
if("true".equals(request.getParameter("make"))){
		String wcmDir = ConfigServer.getServer().getInitProperty("WCM_PATH");
		File thizFile = new File(wcmDir,request.getServletPath());
		File srcDir = new File(thizFile.getParentFile().getParentFile(),"doc_stat");
		StatPageGener gener = new StatPageGener();
		gener.setSrcDir(srcDir.getAbsolutePath());
		gener.setDestDir(request.getParameter("destdir"));
		gener.setPrefix(request.getParameter("prefix"));
		gener.setPrefixDesc(CMyString.getStr(request.getParameter("desc")));
		gener.setStatSQL(CMyString.getStr(request.getParameter("statsql")));
		gener.makePages();//*/
		msg = "<div style='color:green;margin:20px'>====生成成功====</div>";
}
%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
<head>
	<meta http-equiv="ContentType" content="text/html;charset=utf-8">
	<title>统计生成工具</title>
	<style>
		span{padding:10px;margin:10px};
	</style>
</head>
<body>
<%=msg%>
<form method="post">
	<input type="hidden" name="make" value="true">
	<span>页面目录(英文):</span><input type="text" name="destdir" value=""><br />
	<span>页面前缀(英文):</span><input type="text" name="prefix" value=""><br />
	<span>统计描述(中文):</span><input type="text" name="desc" value=""><br />
	<span>统计SQL语句(多个语句以逗号分隔):</span><br /><textarea name="statsql" cols="80" rows="20"></textarea>
	<input type="submit" value="提交">
</form>
</body>
</html>