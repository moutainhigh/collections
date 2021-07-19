<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用集成-权限新增</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=contextpath%>/static/script/home/qx_add.js" type="text/javascript"></script>
</head>
<body>
	<div name="maincontent" id="maincontent" vtype="formpanel" titledisplay="false" showborder="false" layout="auto" height="100%" >
		<div id="qxlist" name="qxlist"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
			<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
			<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="leave()"></div>
		</div>
  	</div>
</body>
</html>