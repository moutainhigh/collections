<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>         
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>深圳工商-应用集成公共底部</title>
<link type="text/css" href="../../static/css/admin/global.css" rel="stylesheet" />
<%
	/* //User user = (User)request.getSession().getAttribute("user"); */
	String username = "长软测试三";//;user.getUserName();
%>
</head>
<body>
	<div id="footer" class="integeratrion_footer">
		<span><%=username%>&nbsp;&nbsp;欢迎您！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="content"></span>
	</div>
</body>
<script type="text/javascript">
	var date, txt = "今天是  ";
	var week = new Array("星期日", "星期一", "星期二","星期三","星期四", "星期五","星期六");
	date = new Date();
	txt+=date.getFullYear()+"年"+(date.getMonth() + 1)+"月"+date.getDate()+"日  ";
	txt+=week[date.getDay()];
	document.getElementById("content").innerText = txt;
</script>
</html>