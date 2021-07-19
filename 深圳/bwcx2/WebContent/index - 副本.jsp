<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9"/>
<link rel="shortcut icon" href="statics/images/favicon.ico" type="image/x-icon"/> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>不忘初心 牢记使命</title>
<link href="statics/css/layout.css" rel="stylesheet" type="text/css" />
<!--有动态显示效果必加js-->
<script type="text/javascript" src="statics/js/jquery-V30.min.js"></script>
<script type="text/javascript" src="statics/js/focus.js"></script>

<!--微信转发图文设置js begin-->
<!-- <script type="text/javascript" src="statics/js/wxshare_v1.js"></script> -->
<style>
.szsclogo {
	width: 1000px;
	margin: 0 auto;
}

.szsclogo a{
    display:block;
    color: #333;
    font-size: 18px;
    text-decoration: none;
}


.szsclogo  a:focus{outline:none;}


a{font-size: 16px !important;}
</style>
</head>


<body>
	<!--深网顶部导航条-->
	<!-- <div style="height: 65px; overflow: hidden; width: 100%; background: url('statics/images/banner1.jpg');margin-bottom: 7px">
		<div class="szsclogo"><a href="http://amr.sz.gov.cn/" target="_blank"><img src="statics/images/banner.jpg"></a></div>
	</div> -->
	<!--头图-->
	<div class="header">
	</div>
	
	
	<!--内容 开始-->
	<div class="content">
		
		<%@include file='search.jsp' %>
		
		
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
		<p>深圳市市场监督管理局不忘初心牢记使命主题教育领导小组办公室</p>
	</div>
	<!--底部 结束-->
	
	
</body>
</html>
