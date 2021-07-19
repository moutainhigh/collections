<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- 登录 -->
	<div id="user_login_loginDialog" style="display: none;width: 300px;height: 210px;overflow: hidden;">
		<div id="user_login_loginTab">
			<div title="输入方式" style="overflow: hidden;"><div>
					<div class="info">
						<div class="tip icon-tip"></div>
						<div>用户名是"孙宇"，密码是"admin"</div>
					</div>
			</div>
			<div align="center">
					<form method="post" id="user_login_loginInputForm">
						<table class="tableForm">
							<tr>
								<th>登录名</th>
								<td><input name="name" class="easyui-validatebox" data-options="required:true" value="孙宇" />
								</td>
							</tr>
							<tr>
								<th>密码</th>
								<td><input type="password" name="pwd" class="easyui-validatebox" data-options="required:true" style="width: 150px;" value="admin" /></td>
							</tr>
						</table>
					</form>
				</div>
		</div>
	</div>
	</div>
	

</body>
</html>