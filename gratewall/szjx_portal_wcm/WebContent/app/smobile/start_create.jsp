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

<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.presentation.util.RequestHelper"%>
<%@ page import="com.trs.mobile.MyDBManager"%>
<%@ page import="com.trs.mobile.MobileCreator"%>

<%@include file="../include/public_server.jsp"%>
<%	
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_UNKNOWN, "您不是管理员，不能执行此操作！");
	}
%>
<%
	String sURL = currRequestHelper.getString("URL"),
	sUser = currRequestHelper.getString("User"), 
	sPassword = currRequestHelper.getString("Password");

	MyDBManager myDBMgr = new MyDBManager();
	myDBMgr.connect(sURL,  sUser,  sPassword);

	MobileCreator creator = MobileCreator.getInstance();
	creator.setDBMgr(myDBMgr);

	int nSiteId = currRequestHelper.getInt("SiteId", 0);
	creator.createMobileSite(nSiteId, "");
%>