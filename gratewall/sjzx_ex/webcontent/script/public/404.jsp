<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.component.thread.ThreadValue, org.apache.log4j.Logger"%>

<freeze:html>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<head>
<title>请求页面不存在</title>

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

<%
Logger log = Logger.getLogger("jsp");
log.info( "页面不存在:" + request.getAttribute("javax.servlet.error.request_uri") );
%>

<freeze:body>
<br>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="90%">
<tr><td colspan="2" style="color:#FF0000">
<H4 align="left"></H4>
</td></tr>
<tr><td colspan="2" align="center">
<B>请求页面不存在</B>
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
</table>
<p align="center">这里加入应用相关的内容，比如跳转到首页等</p>
</freeze:body>
</freeze:html>
