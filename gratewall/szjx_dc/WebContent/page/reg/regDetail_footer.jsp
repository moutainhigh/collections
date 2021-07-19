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
.footer_tishi{
	height:40px;
	 background-color:black; 
	position:relative;
	height:100%;
	line-height:30px;
	/* padding-left:15%; */
    font-size: 12px;
	 color: #eff1ef;	 
	font-family:simsun;

}
</style>		 
</head>
<body>
		<div region="south" height="31" class="footer_tishi" style="text-align: center;">
				深圳市市场和质量监督管理委员会	
		<%-- 	<jsp:include page="../../page/integeration/footer.jsp"/> --%>
		</div>
</body>
</html>