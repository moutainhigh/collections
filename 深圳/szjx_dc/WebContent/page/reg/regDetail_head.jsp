<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>详细页面头部</title>
<%
	String rootPath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>

<script type="text/javascript">
</script>
		
<style type="text/css">
body{
 overflow-x: hidden; overflow-y: hidden;
}
.yincangdiv { overflow-x: hidden; overflow-y: hidden; }
</style>		 
</head>
<body>
		<div class="yincangdiv" height="156">
			<jsp:include page="../../page/home/header.jsp"/>
			<jsp:include page="../../page/trs/trs_header.jsp"/>
		</div>
</body>
</html>