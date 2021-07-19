<%@ page contentType="text/html; charset=utf-8"%>       
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String contextpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=contextpath%>/static/portal/index.css"/>
<script src="<%=contextpath%>/static/script/JAZZ-UI/external/jquery-1.8.3.js" type="text/javascript"></script>
<!--[if IE 6]>
<style>
/* CSS for IE 6 only */
</style>
<![endif]-->

<!--[if IE 7]><link rel="stylesheet" type="text/css" href="css/ie7.css"><![endif]-->

<style>
body{background: #F9F9F9;}

.waitWrap{margin-top: -6px;}

#fontRed{
	color: #ff0000;
	font-size: 16px;
	font-family: "微软雅黑";
}

.titles{
	font-family: "微软雅黑";
	font-size: 16px;
}

.total{font-size: 14px;}

.borderLine{border-bottom: 1px dotted #9099b7;width: 91%;margin: 0 auto;}
.waiterWrap{
	width:330px;
	margin: 0 auto;
}

.waiterWrap tr td{padding:5px 4px 5px 20px;}


.waiterWrap tr:hover{
	background-color: #b0d0dd;
    color: #fff;}

.w200{width: 200px;}

.red{color:#ff0000}
</style>
</head>
<body>
		<div class="waitWrap">
			<table>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td  width="28px"></td>
					<td><img src="../../static/portal/images/37.png" width="61" height="61"/></td>
					<td>
						<table>
							<tr><td>&nbsp;<span class="titles">公文处理</span></td></tr>
							<tr><td class="">&nbsp;<span class="titles total">当前待处理事件：</span><span id="fontRed">39件</span></td></tr>
						</table>
					</td>
				</tr>
			</table>
			<div class="borderLine"></div>
			<div class="waiterWrap">
			<table>
				<tr>
					<td class="w200">待办事项</td>
					<td class="red">（11条）</td>
				</tr>
				
					<tr>
					<td class="w200">待办事项</td>
					<td class="red">（111条）</td>
				</tr>
			</table>
			</div>
		</div>
</body>
</html>