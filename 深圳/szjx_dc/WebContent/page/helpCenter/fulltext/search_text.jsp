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
<div class="title_nav">当前位置：帮助中心 > <span>搜索</span></div>
<table align="center" width="100%" >
	<tr>
	<td>
	<p class="top">
		&nbsp&nbsp基本功能描述：全文检索主要是让用户通过检索能够查看企业的基本信息、隶属信息、出资信息、人员信息、股权冻结、股权出质、注销信息、吊销信息、变更信息、许可信息、迁移信息、清算信息、财务负责人及联络信息。主要提供搜索和查看功能。
	</p>
	
	</td>
	</tr>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/fulltext/sousuo.jpg" align="center" width="50%" height="50%"/>
</body>
</html>