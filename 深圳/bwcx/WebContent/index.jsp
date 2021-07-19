<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>不忘初心 牢记使命</title>
<link href="statics/css/layout.css" rel="stylesheet" type="text/css" />
<!--有动态显示效果必加js-->
<script type="text/javascript" src="statics/js/jquery-V30.min.js"></script>
<script type="text/javascript" src="statics/js/focus.js"></script>
<!--微信转发图文设置js begin-->
<script type="text/javascript" src="statics/js/wxshare_v1.js"></script>
</head>


<body>
	<!--深网顶部导航条-->
	<div
		style="height: 35px; overflow: hidden; width: 100%; background: url('statics/images/zt-dd.gif')"></div>
	<!--头图-->
	<div class="header">
		<img src="statics/images/cx_header.jpg" />
	</div>
	
	
	<!--内容 开始-->
	<div class="content">
	
		<!--1 开始banner-->
		<%@include file="template/01.jsp"%>
		<!-- </div> 2018_ye-->

		<!--2 开始媒体新闻-->
		<%@include file="template/02.jsp"%>

		<!-- 03 理论学习-->
		<%@include file="template/03.jsp"%>
		<!-- 04方案计划 -->
		<%@include file="template/04.jsp"%>
		<!--05影像资料-->
		<%@include file="template/05.jsp"%>

		<!--06征求意见-->
		<%@include file="template/06.jsp"%>

	</div>
	<!--内容 结束-->

	<!--底部 开始-->
	<div class="footer">

		<p>深圳市市场监督管理局-不忘初心，牢记使命主题教育领导办公室</p>
	</div>
	<!--底部 结束-->
</body>
</html>
