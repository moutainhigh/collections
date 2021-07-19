<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0;">
<meta http-equiv="X-UA-Compatible" content="IE=10">
<link href="statics/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="statics/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="statics/js/themes/icon.css" />
<script type="text/javascript" src="statics/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="statics/js/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src="statics/js/locale/easyui-lang-zh_CN.js"></script>
<script src="statics/js/echarts.js"></script>
<script src="statics/js/myChart.js"></script>