<%@ page language="java"  contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>填写查询参数</title>
<script language="javascript">
window.onload = function(){
	document.getElementById("body-div").onresize = function(){
		//显示设置frame窗口尺寸
	    testResizeFrame();
	};
}
</script>
</head>
<%
	String methodName = request.getParameter("methodName");
	String methodNameCn = request.getParameter("methodNameCn");
%>
<freeze:body>
<table border="0" width="100%" align="center" class="frame-body" cellpadding="0" cellspacing="1">
	<form method="post" action="<%= request.getContextPath()%>/dw/runmgr/services/wstaxtest/test.jsp" target="_parent">
		<tr class="title-row" colspan="2">
			<td>填写查询参数</td>
		</tr>
		<tr class="oddrow">
			<td width="9%">方法中文名称</td>
			<td><%= methodNameCn%></td>
		</tr>
		<tr class="oddrow">
			<td>方法英文名称</td>
			<td><%= methodName%><input type="hidden" id="methodName" name="methodName" value="<%= methodName%>"/></td>
		</tr>
		<tr class="oddrow">
			<td>参数</td>
			<td>
				<table border="0">
				<tr>
					<td>查询日期：</td>
					<td><input type="text" name="cxrqq" id="cxrqq" onclick="calendar(this, 3)"/></td>
				</tr>
				<tr>
					<td>开始记录数：</td>
					<td><input type="text" name="ksjls" id="ksjls" /></td>
				</tr>
				<tr>
					<td>结束记录数：</td>
					<td><input type="text" name="jsjls" id="jsjls" /></td>
				</tr>
				</table>
			</td>
		</tr>
		<tr class="oddrow" align="center">
			<td colspan="2"><input type="submit" class="menu" value=" 提 交 "/></td>
		</tr>
	</form>
	
</table>
</freeze:body>
</freeze:html>