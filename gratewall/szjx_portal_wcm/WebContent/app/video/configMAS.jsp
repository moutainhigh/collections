<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.WCMConfigServerBasedManager" %>
<%@ page import="com.trs.dl.util.ConfigFileModifier" %>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@ include file="../../include/public_server.jsp" %>
<html>
	<head>
		<title WCMAnt:param="configMAS.jsp.setuppushInfo">设置MAS推送的信息</title>

		<script type="text/javascript">
			function checkForm(myForm) {
				var msg = "";
				var setFocused = false;
				if(myForm.ip.value == "") {
					msg += "MAS的IP不能为空！\n";
					myForm.ip.focus();
					setFocused = true;
				}

				if(myForm.chnId.value == "") {
					msg += "栏目ID不能为空！\n";
					if(!setFocused) {
						myForm.chnId.focus();
						setFocused = true;
					}
				}
				
				if(msg != "") {
					alert(msg);
					return false;
				}
				
				return true;
				
			}
		</script>
	</head>
	<body>
		<%
			Object obj = request.getAttribute("errorMessage");
			// /bms/processLogin.jsp
		%>
		<form action="./applyMASConfig.jsp" name="form1" method="post" onsubmit="return checkForm(this);">
		<table align="center">
				<caption WCMAnt:param="configMAS.jsp.setupPush">推送配置</caption>
			<%if(obj != null){ %>
			<tr>
				<td colspan="22">
					<span style="color : red; font-weight : bold"><%=obj %></span>
				</td>
			</tr>
			<%} %>
			<tr>
				<td WCMAnt:param="configMAS.jsp.MASIP">
					MAS的IP
				</td>
				<td>
					<input type="text" style="width:150px" name="ip" value="<%= (VSConfig.getMASIP() == null) ? "" : VSConfig.getMASIP() %>" >
				</td>
			</tr>
			<tr>
				<td WCMAnt:param="configMAS.jsp.LanmuID">
					WCM接收视频的栏目ID
				</td>
				<td>
					<input type="text" style="width:150px" name="chnId" value="<%= (VSConfig.getChnId() == null) ? "" : VSConfig.getChnId()  %>" >
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit"  value="设置" WCMAnt:paramattr="value:configMAS.jsp.setup"  >
					<input type="reset"  value="清空" WCMAnt:paramattr="value:configMAS.jsp.clear">
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>