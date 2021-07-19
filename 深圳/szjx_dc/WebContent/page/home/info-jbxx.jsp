<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会-快搜-详细信息</title>
<%
	String rootPath = request.getContextPath();
%>
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
html,body{
	margin:0px;
	padding:0px;
}

.left-title{
	height:30px;
	line-height: 30px;
	width:50%;
	float:left;
}

.right-title{
	height:30px;
	line-height: 30px;
	width:20%;
	float:right;
}

.header-table{
	height:30px;
	line-height:30px;
	padding-left:20px;
	background-color:rgb(238,245,253);
}

.info-table{
	border-collapse: separate;
    border-top: 1px solid rgb(214,233,240);
}

.left-cell{
	background-color:rgb(238,245,253);
	text-align: right;
	border-color: rgb(214,233,240);
    border-style: solid;
    border-width: 0 1px 1px 0;
    padding: 8px 5px 8px;
}

.right-cell{
	background:none;
	text-align: left;
	border-color: rgb(214,233,240);
    border-style: solid;
    border-width: 0 1px 1px 0;
    padding: 8px 5px 8px;
}
</style>
</head>
<body>
	<div style="height:30px;">
		<div class="left-title">字号名称:广州欢网科技有限公司</div>
		<div class="right-title">注册号:4000000000010</div>
	</div>
	<div>
		<div class="header-table">基本信息</div>
		<table width="100%" cellspacing="0" cellpadding="0" class="info-table">
			<thead>
				<tr>
					<th width="15%"></th>
					<th width="35%"></th>
					<th width="15%"></th>
					<th width="*"></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="left-cell">注册号</td>
					<td class="right-cell">4000000000010</td>
					<td class="left-cell">旧注册号</td>
					<td class="right-cell"></td>
			    </tr>
			    <tr>
					<td class="left-cell">个体户状态</td>
					<td class="right-cell">已开业</td>
					<td class="left-cell">住所（街道）</td>
					<td class="right-cell"></td>
			    </tr>
			    <tr>
					<td class="left-cell">住所（村）</td>
					<td class="right-cell"></td>
					<td class="left-cell">住所（楼宇）</td>
					<td class="right-cell"></td>
			    </tr>
			    <tr>
					<td class="left-cell">个住所（其他）</td>
					<td class="right-cell"></td>
					<td class="left-cell">住所（门牌号）</td>
					<td class="right-cell"></td>
			    </tr>
			    <tr>
					<td class="left-cell">字号名称</td>
					<td class="right-cell" colspan="3">广州欢网科技有限公司</td>
			    </tr>		    			    			    			    			    
			</tbody>
		</table>
	</div>
</body>
</html>