<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String contextpath = request.getContextPath();
%>
<%
	User user = (User) request.getSession().getAttribute("user");
	String username = user.getUserName();
%>
<link rel="stylesheet" href="<%=contextpath%>/static/portal/index.css" />
<script	src="<%=contextpath%>/static/script/JAZZ-UI/external/jquery-1.8.3.js"
	type="text/javascript">
</script>
<style>
.topNav{width: 1000px;margin: 0px auto;}

.userCenter{color:#fff;padding-left:10px;}
</style>
</head>
<body>
	
	<div class="top" style="background: url(<%=contextpath%>/static/portal/images/banner1.jpg) repeat-x;width:100%;">
		<div class="topWrap" style="position:relative;width: 1000px;margin: auto;">
			<img src="../../static/portal/images/banner.jpg" OLDSRC="banner.jpg" OLDID="145" RELATED="1" />
			<p id="userName" style="position:absolute;top:40px;color:white;font-family:'微软雅黑'; right:15px;">
				当前用户&nbsp;&nbsp;:<a href="http://wsjgj/oa/" class="userCenter" target="_blank"><%=username%></a>
			</p>
		</div>
	</div>
</body>
</html>