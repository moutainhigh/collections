<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.plugin.auth.model.User"%>
<!DOCTYPE html>
<html>
<%
	String contextpath = request.getContextPath();
%>
<%
	User user = (User) request.getSession().getAttribute("user");
	String username = "";
	if(user==null){
		username = "未登录用户";
	}else{
		username = user.getUserName();
	}
%>

<style>
.userInfo{
	color: #fff;font-family:'微软雅黑';
}
</style>
<head>
<title>深圳市市场和质量监督管理委员会-公共头部</title>
</head>
<body>
	<div id="header" class="header">
		<div class="banner" style="position: relative;">
			<div class="userInfo" style="position: absolute;right: 10px;top:47px;">当前用户:<%=username%>&nbsp;&nbsp;&nbsp;&nbsp;<span id="content" class="userInfo"></span></div>
		</div>
	</div>
	<script type="text/javascript">
	var date, txt = "";
	var week = new Array("星期日", "星期一", "星期二","星期三","星期四", "星期五","星期六");
	date = new Date();
	txt+=date.getFullYear()+"年"+(date.getMonth() + 1)+"月"+date.getDate()+"日  ";
	txt+=week[date.getDay()];
	document.getElementById("content").innerText = txt;
	</script>
</body>
</html>