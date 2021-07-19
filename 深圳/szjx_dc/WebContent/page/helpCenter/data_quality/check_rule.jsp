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
<div class="title_nav">当前位置：帮助中心 > <span>检查规则配置</span></div>
<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp检查规则配置提供对数据检查规则的增、删、查、导出等维护功能。数据检查规则包括单表和关联规则，每个规则必须与被检查的一张数据表对应。点击检查规则配置菜单从左侧的树中选择相应系统下的数据表进行单表规则与关联规则的配置。
	</p>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/jcgzpz.jpg" align="center" width="50%" height="50%"/>
	
	<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp在查询规则列表中对规则进行删除及导出操作，选中一条或多条数据，点击【删除】按钮，将未使用的规则删除。（注：已执行过的规则，在活动及规则包未删除的情况下不能删除）
点击【导出】按钮，能将列表中的规则导出为excel。
点击规则名称，有如下所示弹出框显示
	</p>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/jcgzpz2.gif" align="center" width="50%" height="50%"/>
	
	<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp在该页面能修改【规则有效性】、【是否疑似】、【规则描述】等信息。
点击【预览】，有弹出框显示示例数据。
点击【保存】，修改成功。
点击【取消】，则返回列表页。

	</p>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/jcgzpz3.jpg" align="center" width="50%" height="50%"/>
</body>
</html>