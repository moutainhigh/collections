<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gwssi.common.*"%>
<%@ page import="java.net.URL"%>
<%@ page import="org.apache.axis.client.*"%>
<freeze:html>
<head>
</head>
<script language="javascript">
// ���Ӷ���
function func_record_addRecord()
{
	window.location="txn40201111.do";
}
</script>
<freeze:body>
	<freeze:title caption="�������Խ��" />
	<freeze:form action="/txn40201111">
		<table width="100%" border="1" align="center" cellspacing="0"
			class="frame-body" id="chaxun">
			<tr>
				<td colspan="4" class="title-row" align="left">
					���Խ��
				</td>
			</tr>
			<tr align="center" class="framerow">
				<td width="20%" align="right">
					��������
				</td>
				<td width="30%" align="left">
					<div id="code" class="entNameContentDiv" align="left">
					<freeze:out value="${record.zts}"></freeze:out>
					</div>
				</td>
			</tr>
			<tr align="center">
				<td><input type="button" value="���԰�ť" onClick="func_record_addRecord();"></td>
			</tr>
		</table>
		<br>
	</freeze:form>
</freeze:body>
</freeze:html>