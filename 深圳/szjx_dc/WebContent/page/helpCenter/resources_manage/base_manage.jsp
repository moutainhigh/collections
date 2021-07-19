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
<div class="title_nav">当前位置：帮助中心 > <span>标准管理</span></div>
<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp基本功能描述：标准管理页签主要是负责管理系统中的标准相关的信息。包括标准规范管理、标准元数据管理、标准代码集管理、系统代码集管理四个菜单。
	</p>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/resources_manage/bzgl.jpg" align="center" width="50%" height="50%"/>
	
	<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp【查询】按照要求选择查询条件，即可查出符合查询条件的数据信息，查询结果显示在查询条件下方的列表中。点击【增加】，弹出增加标准规范页面，填入规范发布名称、规范发布单位、规范发布时间等必填项，并上传标准规范文档（当前系统支持.doc格式的文件）。然后点击【保存】按钮，完成增加操作。
点击【返回】取消增加标准，并返回标准规范管理页面。
		
	</p>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/resources_manage/bzgl2.jpg" align="center" width="50%" height="50%"/>
	
	<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp点击标准规范列表中某条记录对应的笔形   按钮【修改】，在编辑标准规范页面，修改规范发布名称、规范发布单位、规范发布时间或上传文件。然后点击【保存】按钮，完成修改对象信息操作。
点击【返回】取消修改，并返回标准规范管理页面。
		
	</p>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/resources_manage/bzgl3.jpg" align="center" width="50%" height="50%"/>
</body>
</html>