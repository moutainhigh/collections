<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>

<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ include file="../../include/public_server.jsp"%>

<%
	// 2 获取参数
	//int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	//String sGroupName = currRequestHelper.getInt("GroupName");
	
	// 3 调用服务删除微博
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmgroup",sMethodName="delete";
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