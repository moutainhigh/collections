<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源管理</title>
<%
	String contextpath = request.getContextPath();
%>
<style>
.top {
	width: 99%;
	padding: 5px;
	color: #555555;
	background-color: #F2F2F2;
	font-family: 宋体;
	line-height: 150%;
	font-size: 11.0pt;
	height: 25%;
	text-align: left;
}
img{margin-left: 23%}
</style>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<body>
<div class="title_nav">当前位置：帮助中心 > <span>查看</span></div>
<table align="center" width="100%" >
	<tr>
	<td>
	<p class="top">
		&nbsp&nbsp点击企业链接，系统跳转到查看企业信息页，在左侧菜单中列出了查看项，点击菜单可以切换显示，如下：
	</p>
	
	</td>
	</tr>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/fulltext/chakan.jpg" align="center" width="50%" height="50%"/>
</body>
</html>