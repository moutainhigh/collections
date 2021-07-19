<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>

<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.auth.persistent.Users"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@include file="../../include/public_server.jsp"%>

<%
	// 2 获取参数
	//int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	//String sGroupName = currRequestHelper.getInt("GroupName");
	
	// 3 调用服务删除微博
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmgroup",sMethodName="save";
	Integer oResult;
	try{
		oResult = (Integer)processor.excute(sServiceId,sMethodName);
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
	
	if(oResult.intValue()>0){
		out.print(oResult.intValue());
	}else{
		out.print("返回的ID为0");
	}
%>