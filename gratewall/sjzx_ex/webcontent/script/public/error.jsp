<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.component.thread.ThreadValue"%>
<%@ page import="cn.gwssi.common.web.util.ErrorMessages"%>

<freeze:html>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<head>
<title>�������ʱ����</title>

<style type="text/css">
.oddrow {
	font-family:����;
	font-size:10pt;
	color: #000000;
	background-color: #F9F9F9;
	height: 24px
}

.evenrow {
	font-family:����;
	font-size:10pt;
	color: #000000;
	background-color: #E5F1FF;
	height: 24px
}
</style>
</head>

<freeze:body>
<br>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="90%">
<tr><td colspan="2" style="color:#FF0000">
<H4 align="left"></H4>
</td></tr>
<tr><td colspan="2" align="center">
<B>�������ʱ�����˴���</B>
</td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="oddrow"><td width="25%" align="right">��Ӧҳ�棺</td><td width="75%"><%=request.getAttribute("javax.servlet.error.request_uri")%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="evenrow"><td align="right">�������</td><td><%=request.getAttribute("inner-flag:request-path")%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="oddrow"><td align="right">��ˮ��ţ�</td><td><%=ThreadValue.getFlowNo()%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="evenrow"><td align="right">�̱߳�ţ�</td><td><%=ThreadValue.getThreadId()%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="oddrow"><td align="right">������Ϣ��</td><td><%=ErrorMessages.fatalMessage(request)%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="evenrow"><td align="right">�쳣��Ϣ��</td><td><%=request.getAttribute("javax.servlet.error.exception")%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
</table>
<p align="center">�������Ӧ����ص����ݣ�������ת����ҳ��</p>
</freeze:body>
</freeze:html>