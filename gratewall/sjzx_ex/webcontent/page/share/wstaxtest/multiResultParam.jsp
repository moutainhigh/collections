<%@ page language="java"  contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��д��ѯ����</title>
<script language="javascript">
window.onload = function(){
	document.getElementById("body-div").onresize = function(){
		//��ʾ����frame���ڳߴ�
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
			<td>��д��ѯ����</td>
		</tr>
		<tr class="oddrow">
			<td width="9%">������������</td>
			<td><%= methodNameCn%></td>
		</tr>
		<tr class="oddrow">
			<td>����Ӣ������</td>
			<td><%= methodName%><input type="hidden" id="methodName" name="methodName" value="<%= methodName%>"/></td>
		</tr>
		<tr class="oddrow">
			<td>����</td>
			<td>
				<table border="0">
				<tr>
					<td>��ѯ���ڣ�</td>
					<td><input type="text" name="cxrqq" id="cxrqq" onclick="calendar(this, 3)"/></td>
				</tr>
				<tr>
					<td>��ʼ��¼����</td>
					<td><input type="text" name="ksjls" id="ksjls" /></td>
				</tr>
				<tr>
					<td>������¼����</td>
					<td><input type="text" name="jsjls" id="jsjls" /></td>
				</tr>
				</table>
			</td>
		</tr>
		<tr class="oddrow" align="center">
			<td colspan="2"><input type="submit" class="menu" value=" �� �� "/></td>
		</tr>
	</form>
	
</table>
</freeze:body>
</freeze:html>