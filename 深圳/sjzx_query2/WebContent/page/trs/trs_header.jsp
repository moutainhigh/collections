<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>         
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中间显示姓名和时间</title>
<%
	String contextpath = request.getContextPath();
%>

<%
	User user = (User)request.getSession().getAttribute("user");
	String username = user.getUserName();
	String rootPath = request.getContextPath();
%>
</head>
<style type="text/css">
</style>
<body>
</body>
</html>