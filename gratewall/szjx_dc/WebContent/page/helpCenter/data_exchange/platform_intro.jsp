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

</style>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<body>
<div class="title_nav">当前位置：帮助中心 > <span>平台介绍</span></div>
<table align="center" width="100%" >
	<tr>
	<td>
	<p class="top">
		&nbsp&nbsp“数据交换平台”，是以深圳市市场和质量监督管理委员会数据中心为基础，进一步完善具有统一性、权威性的企业信用信息数据库，提供强大的共享交换功能，实现各委办局之间数据交换的统一管理和监控，强化各委办局的业务协同，实现对企业的无缝监管，为政府各部门开展协同监管、信用约束和管理决策提供支持，为社会公众提供准确便捷的信息服务。数据交换平台是企业信用数据交换平台的管理平台，实现交换数据的统一运行监控和质量管理。为数据自动归集和数据共享服务建设集中统一的数据共享交换管理平台，实现对共享交换过程和共享交换内容的全流程、智能化、可视化监控与管理，也为数据归集和数据共享服务接口的动态配置，灵活扩展提供平台支撑。数据交换平台包括资源管理、共享服务、采集任务、运行监控、日志分析、系统管理等功能，通过工商内网、政务专网、互联网，面向内部系统、区县分局、外部系统提供数据交换服务。数据交换平台通过实时监控模块全程监控数据交换过程，详细记录交换数据的内容、方式、数据量、频率等，以动态图表的形式对平台运行状况进行统计和分析，为数据交换平台不断完善，为进一步提高服务质量提供依据。
	</p>
	
	</td>
	</tr>
</table>
	<img src="<%=request.getContextPath()%>/static/images/helpRes/data_exchange/ptjs.png" align="center" width="100%"/>
</body>
</html>