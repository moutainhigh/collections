<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>

<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.auth.persistent.Users"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@include file="../../include/public_server.jsp"%>

<%
	// 2 获取参数
	//int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	//int nUserId = currRequestHelper.getInt("UserId",0);
	
	// 3 调用服务删除微博
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmgroup",sMethodName="saveAdminsOfGroup";
		
	try{
		Object oResult = processor.excute(sServiceId,sMethodName);
%>
	1
<%
	}catch (Exception e){
%>
	0
<%
	}
%>