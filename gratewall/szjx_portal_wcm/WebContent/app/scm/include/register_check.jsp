
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%
	// 判断是否申请了SCM注册码
    if (!SCMAuthServer.isStartSCM()) {
%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>注册码提示页面</title>
		<link rel="stylesheet" href="css/public.css">
</head>
<body>
	<div  class="sabrosus">
		<font size="4">此选件未正确安装或您没有购买此选件！<BR>
			系统将在&nbsp;<span style="color:#ff0000" id= "time"> 5 </span> &nbsp;秒钟后关闭本页面<br>
			或点击<a href="javaScript:window.closeExplore()" style="color:#ff0000">关闭</a>本页面
		</font>
	</div>
</body>
<script language="javascript">
	var times=6;
	clock();
	function clock()
	{
	   window.setTimeout('clock()',1000);
	   times=times-1;
	   document.getElementById("time").innerHTML =times;
	   if(times == 0){
			window.closeExplore();
	   }
	}
	function closeExplore(){
		window.opener = null;
		window.open("","_self");
		window.close();
	}
</script>
</html>
<%
       return;
    }
%>