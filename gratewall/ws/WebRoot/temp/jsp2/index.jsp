<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>11</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=no" />
<link rel="shortcut icon" href="<%=basePath%>statics/images/fav.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="<%=basePath%>statics/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="<%=basePath%>bootstrap/js/bootstrap.min.js"></script>
<!-- http://www.html5tricks.com/css3-toggle-switch-button.html -->
<style>
body {
	background-image: radial-gradient(#4f6875, #263238);
}

.white{color: white;}
</style>
</head>
<body>
	<div class="container" style="margin-top: 350px;">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label white" >用户名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="username" value="admin"/>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label white">密码</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" id="pwd" value="admin"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-5">
							<a onclick="dologin(this)" class="btn btn-default">登入</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<script>
	   function dologin(obj){
		   window.location.href = "login"
	   }
		
	</script>
</body>
</html>
