<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>后台管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/animate.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="<%=basePath%>statics/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/StringUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/yesg.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/bootstrap-paginator.js"></script>
</head>
<style>
html {
	height: 100%;
}

body {
	height: 100%;
	background-color: #141219;
}
</style>
<body>
	<%@include file="../../pub/header.jsp"%>
	<%@include file="../../pub/main.jsp"%>
</body>
</html>
