<%@ page contentType="application/vnd.ms-excel;charset=GBK" %>
<html>
<head>
<meta http-equiv="content-type" content="application/vnd.ms-excel;charset=GBK" />
<style type="text/css">
.frame-body {
	font-family: 宋体;
	font-size: 9pt;
	color: #FFFFFF;
	background-color: #2975AF;
}

.title-row {
	font-family: 宋体;
	font-size: 9pt;
	text-indent:6;
	height:19px;
	color: #FFFFFF;
	background-color: #2975AF;
}

.grid-headrow {
	font-family: 宋体;
	font-size: 9pt;
	color: #000000;
	height:20px;
	background-color: #7fbfd6;
}

.oddrow {
	font-family:宋体;
	font-size:9pt;
	color: #000000;
	height:20px;
	background-color: #EEF6F9;
}

.evenrow {
	font-family:宋体;
	font-size:9pt;
	color: #000000;
	height:20px;
	background-color: #DBEEF5;
}
</style>
<title>查询历史增量查询列表</title>
<%
	// response.setContentType("application/vnd.ms-excel;charset=GBK");
	// response.setHeader("Content-Disposition:filename=test.xls");
%>
<% 
	String htmlStr = request.getParameter("htmlStr");
%>
</head>
<body>
  <table class="frame-body" width="100%" cellpadding="0" cellspacing="1" border="1">
  <%=htmlStr %>
  </table>
</body>
</html>
