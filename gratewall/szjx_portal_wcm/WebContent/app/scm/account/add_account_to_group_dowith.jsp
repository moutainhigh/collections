<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="java.util.HashMap"%>

<%@include file="../../include/public_server.jsp"%>

<%
	//获取参数
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	int nFlag = currRequestHelper.getInt("Flag",-1);

	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmgroup",sMethodName = "addAccountToGroup";
	HashMap parameters = new HashMap();
		parameters.put("SCMGroupId",String.valueOf(nSCMGroupId));
		parameters.put("AccountId",String.valueOf(nAccountId));

	if(nFlag == 1){
		sMethodName = "addAccountToGroup";
	}else if(nFlag == 0){
		sMethodName = "removeAccountFromGroup";
	}


	Boolean bBoolean = (Boolean) processor.excute(sServiceId,sMethodName,parameters);

	if(bBoolean){
%>
	1
<%
	}else{
%>
	0
<%
	}
%>