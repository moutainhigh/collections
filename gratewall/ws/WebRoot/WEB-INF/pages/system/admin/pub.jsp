<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>statics/css/compone.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/bootstrapSwitch/bootstrap-switch.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/bootstrapTable/bootstrap-table.css"/>
<script type="text/javascript" src="<%=basePath%>statics/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/StringUtil.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/yesg.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/bootstrapSwitch/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/bootstrapTable/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/bootstrapTable/locale/bootstrap-table-zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/bootstrapEdit/edit.css"/>
<script type="text/javascript" src="<%=basePath%>plugins/bootstrapEdit/editable.js"></script>
</head>