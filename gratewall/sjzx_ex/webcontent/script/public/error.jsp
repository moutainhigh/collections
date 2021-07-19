<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.component.thread.ThreadValue"%>
<%@ page import="cn.gwssi.common.web.util.ErrorMessages"%>

<freeze:html>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<head>
<title>处理服务时错误</title>

<style type="text/css">
.oddrow {
	font-family:宋体;
	font-size:10pt;
	color: #000000;
	background-color: #F9F9F9;
	height: 24px
}

.evenrow {
	font-family:宋体;
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
<B>处理服务时发生了错误</B>
</td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="oddrow"><td width="25%" align="right">响应页面：</td><td width="75%"><%=request.getAttribute("javax.servlet.error.request_uri")%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="evenrow"><td align="right">请求服务：</td><td><%=request.getAttribute("inner-flag:request-path")%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="oddrow"><td align="right">流水编号：</td><td><%=ThreadValue.getFlowNo()%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="evenrow"><td align="right">线程编号：</td><td><%=ThreadValue.getThreadId()%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="oddrow"><td align="right">错误信息：</td><td><%=ErrorMessages.fatalMessage(request)%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
<tr class="evenrow"><td align="right">异常信息：</td><td><%=request.getAttribute("javax.servlet.error.exception")%></td></tr>
<tr height="1px" bgcolor="#0000FF"><td colspan="2"></td></tr>
</table>
<p align="center">这里加入应用相关的内容，比如跳转到首页等</p>
</freeze:body>
</freeze:html>