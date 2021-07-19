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
<div class="title_nav">当前位置：帮助中心 > <span>数据质量检查</span></div>
<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp常规检查功能是系统最基本的功能之一，作用是执行由管理员已经定义好的当期常规检查活动，并分类显示结果。点击数据质量检查 > 常规检查管理，右边页面显示常规检查列表，如下图所示
	</p>	
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/sjzljc.jpg" align="center" width="50%" height="50%"/>
	
	<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp点击【增加常规检查】，显示新增页面
	</p>	
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/sjzljc2.jpg" align="center" width="50%" height="50%"/>
	
	<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp新建类型分为“新建”和“扩展”两种类型，选择“新建”则表示该期常规检查为第一期，“扩展”表示为上一期活动的延续。
在各个输入框中填入相关信息，字段标红为必填字段。【规则包】字段，点击字段后的【选择规则包】，可以选择登陆分局及市局建立的规则包（规则包的建立及维护将在2.3中介绍）。完成后点击【保存】或【保存并启动】。【保存并启动】将立即执行该期常规检查；【保存】后常规检查列表中将新增一期未执行过的常规检查，列表项中的【执行】也是对该期常规检查活动的启动。
完成新建常规检查后，在常规检查列表中，点击活动名称，可查看该常规检查明细，明细页面如下图所示：
		
	</p>	
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/sjzljc3.jpg" align="center" width="50%" height="50%"/>
	
	<table align="center" width="100%" >
	<p class="top">
		&nbsp&nbsp执行记录列表，默认显示当前登录人的执行记录，点击【显示全部】按钮，可以显示该常规检查活动的所有执行记录，同时可根据实际情况，选中执行记录，点击【删除】按钮，删除执行记录。执行记录列表上方的【启动】按钮，为对该期常规检查活动的启动。【统计】，对检查执行记录列表中的记录分时间和执行人进行统计，页面如下图所示
	</p>	
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_quality/sjzljc4.jpg" align="center" width="50%" height="50%"/>
</body>
</html>