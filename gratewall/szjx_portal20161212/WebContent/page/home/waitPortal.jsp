<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String contextpath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=contextpath%>/static/portal/index.css" />
<script
	src="<%=contextpath%>/static/script/JAZZ-UI/external/jquery-1.8.3.js"
	type="text/javascript"></script>
<!--[if IE 7]>
<style>
/* CSS for IE 7 only */
</style>
<![endif]-->

<!--[if IE 7]>
	<link rel="stylesheet" type="text/css" href="css/ie7.css">
<![endif]-->

<style>
body {
	background: #F9F9F9;
	font-family: "微软雅黑";
}

img {
	border: 0
}

.waitWrap {
	margin-top: -6px;
}

#fontRed {
	color: red;
	font-size: 16px;
}

.titles {
	font-size: 16px;
}

.total {
	font-size: 14px;
}

.borderLine {
	border-bottom: 1px solid #EAEAEA;
	width: 91%;
	margin: 8px auto;
}

.waiterWrap {
	width: 330px;
	margin: -4px auto;
}

.waiterWrap tr td { /* padding: 5px 12px 5px 20px; */
	padding: 4px 12px 4px 20px;
}

.waiterWrap tr:hover {
	background-color: #b0d0dd;
	color: #fff;
}

.w200 {
	width: 200px;
}

.red {
	color: #ff0000
}
</style>

<!--[if IE 7]>
<style>
div.borderLine{margin-top:-8px !important;border-bottom:1px dotted #9099b7;width: 91%;}

</style>
<![endif]-->
</head>
<body>
	<div class="waitWrap">
		<table>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="28px"></td>
				<td><a href="http://wsjgj/oa" target="_blank"><img
						src="../../static/portal/images/37.png" width="50" height="50" /></a></td>
				<td>
					<table>
						<tr>
							<td>&nbsp;<span class="titles">公文处理</span></td>
						</tr>
						<tr>
							<td class="">&nbsp;<span class="titles total">当前待处理事件：</span><span
								id="fontRed"></span></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div class="borderLine"></div>
		<div class="waiterWrap">
			<div id="items"></div>
			<table id="bus">
				<!-- <tr>
					<td class="w200">待办事项</td>
					<td class='red'>（11条）</td>
				</tr> -->
			</table>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			oaList();
		});
		function oaList() {
			var fontRed;
			$
					.ajax({
						url : "../../home/getOaWaitByWebService.do",
						type : 'post',
						dataType : 'json',
						success : function(data) {
							var i = 0;
							var divstr = "";
							var oaWait = 0;
							$(data)
									.each(
											function() {
												i = i + 1;
												oaWait = oaWait + this.count;
												if (this.count > 0) {
													divstr += "<tr onclick='javascript:window.location.href="+this.url+";'><td class='w200'><a target='_blank' href="+this.url+">"
															+ this.typeName
															+ "事项</a></td><td class='red'>（"
															+ this.count
															+ "条）</td></tr>";
												}
											});
							if (oaWait == 0) {
								$("#fontRed").html(oaWait + "件");
								$("#fontRed").css("color", "red");
								$("#items").html("当前无待办");
							} else {
								$("#fontRed").html(oaWait + "件");
								$("#bus").append(divstr);
							}

						},
						error : function() {
							setTimeout("returnOrgNo()", 3000);
						}
				});
		}
	</script>
</body>
</html>