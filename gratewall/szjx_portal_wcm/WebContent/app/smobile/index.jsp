<%@ page contentType="text/html;charset=utf-8" %>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>

<%@ page import="com.trs.infra.I18NMessage" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.persistent.db.DBConnectionConfig" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.infra.util.database.ConnectionPool" %>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>

<%@ page import="com.trs.infra.util.CMyString" %>



<%@include file="../include/public_server.jsp"%>
<%	
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_UNKNOWN, "您不是管理员，不能执行此操作！");
	}
%>
<%
	DBManager dbMgr = DBManager.getDBManager();
	String sURL = dbMgr.getDBConnConfig().getConnectionURL(), sUser = dbMgr
			.getDBConnConfig().getConnectionUser(), sPassword = dbMgr
			.getDBConnConfig().getConnectionPassword();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>  创建移动站点第一步：设置WCM所在数据库信息 </title>
	<style type="text/css">
		.row{
			line-height:2em;
		}
		.label{
			display:inline-block;
			*display:inline;
			width:150px;
		}
	</style>
</head>

<body>
<h1>1.创建移动站点第一步：设置WCM所在数据库信息</h1>

<form method="post" action="create_mobile_site_2.jsp">
	<div class="row">
		<span class="label">数据库连接字符串：</span><input type="text" name="URL" value="<%=CMyString.filterForHTMLValue(sURL)%>" size="<%=sURL.length()+10%>"><BR/>
	</div>
	<div class="row">
		<span class="label">用户名：</span><input type="text" name="User" value="<%=CMyString.filterForHTMLValue(sUser)%>">
	</div>
	<div class="row">
		<span class="label">密码：</span><input type="password" name="Password"  value="">
	</div>
	<div class="row">
		<input TYPE="submit" value="下一步" style="padding:2px 20px;margin-left:20px;" /> 
		<input type="reset" style="padding:2px 20px;margin-left:20px;" />
	</div>
</form>
</body>
</html>
