<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>   
<!DOCTYPE html>
<html>
<head>
<title>深圳工商-公共底部</title>
<link type="text/css" href="../../static/css/admin/global.css" rel="stylesheet" />
<%
	User user = (User)request.getSession().getAttribute("user");
	String username = user.getUserName();
%>
</head>
<body>
	<div id="footer" class="footer">
		<div class="leftarea">版权所有：深圳市市场监督管理委员会             粤ICP备11069154号-9</div>
		<div class="rightarea">
			 <div class="userimg"></div>
			 <div class="username">欢迎 &nbsp;&nbsp;<%=username%>&nbsp;&nbsp;登入系统</div>
			 <div class="userleave"></div>
		</div>
	</div>
</body>
</html>