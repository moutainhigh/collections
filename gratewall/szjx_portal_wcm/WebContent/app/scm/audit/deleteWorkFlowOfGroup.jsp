<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 2 获取参数
	String sGroupID = currRequestHelper.getString("SCMGroupId");

	// 3 调用服务
    String sServiceIdOfWorkFlow = "wcm61_scmworkflow";
	String sMethodName = "deleteWorkFlowOfGroup";
	
	HashMap oHashMap = new HashMap();
	oHashMap.put("SCMGroupId",sGroupID);

	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);

	oProcessor.excute(sServiceIdOfWorkFlow,sMethodName,oHashMap);

%>