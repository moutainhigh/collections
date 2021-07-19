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
<div class="title_nav">当前位置：帮助中心 > <span>接口调用日志</span></div>
<table align="center" width="100%" >
	<tr>
	<td>
	<p class="top">
		&nbsp&nbsp基本功能描述：主要是管理接口调用日志，主要提供查询功能。
角色权限描述：系统管理员等
菜单路径：系统管理-日志管理-接口调用日志

		
	</p>
	
	</td>
	</tr>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/log_manage/jkdy.jpg" align="center" width="50%" height="50%"/>
</body>
</html>