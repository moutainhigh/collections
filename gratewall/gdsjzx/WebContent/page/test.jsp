<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>内资1</title>

<style type="text/css">
table{
border-spacing:inherit;
}

</style>
</head>

<body>
<%@ page language="java" import="java.io.*"	pageEncoding="UTF-8"%> 
<% 		response.setContentType("application/msexcel");
		File file = new File("D:/null.xls"); 
		FileInputStream in = new FileInputStream(file); 
		byte[] buffer = new byte[in.available()]; 
		in.read(buffer); 
		
		//拼接导出路径()
		String var="导出报表";
		
		response.getOutputStream().write(var.getBytes("UTF-8"));
		response.getOutputStream().write(buffer); 
		response.getOutputStream().flush(); 
		out.clear(); 
		out = pageContext.pushBody(); 
		in.close();
%>

</body>





</html>




