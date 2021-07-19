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

img{margin-left: 23%;}
</style>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<body>
<div class="title_nav">当前位置：帮助中心 > <span>数据源管理</span></div>
<p class="top">
		&nbsp&nbsp数据源管理页签主要包括数据源管理菜单和结构装载管理菜单。
角色权限描述：系统管理员等
菜单路径：资源管理，数据源管理
</p>
<img src="<%=request.getContextPath()%>/static/images/helpRes/resources_manage/sjygl.jpg" align="center" width="50%" height="50%"/>
<p class="top">
		&nbsp&nbsp【查询】按照要求选择查询条件，即可查出符合查询条件的数据信息，查询结果显示在查询条件下方的列表中。
</p>
<img src="<%=request.getContextPath()%>/static/images/helpRes/resources_manage/sjygl2.png" align="center" width="50%" height="50%"/>
</body>
</html>