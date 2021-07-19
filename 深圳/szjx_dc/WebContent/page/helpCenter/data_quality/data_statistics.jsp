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
<div class="title_nav">当前位置：帮助中心 > <span>数据质量统计</span></div>
<table align="center" width="100%" >
	<tr>
	<td>
	<p class="top">
		&nbsp&nbsp数据质量统计对业务系统、分局、规则类型、业务类型四种分类按每期数据检查活动和每次活动执行情况进行使用情况统计以供全局参考。页面如下所示
	</p>
	
	</td>
	</tr>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/sjzltj.jpg" align="center" width="50%" height="50%"/>
</body>
</html>