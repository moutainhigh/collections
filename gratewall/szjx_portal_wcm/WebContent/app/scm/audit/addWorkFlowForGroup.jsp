<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	String sSelectedGroupID = currRequestHelper.getString("SelectedGroupID");

	// 2 调用服务
	String sServiceIdOfWorkFlow = "wcm61_scmworkflow";
	String sMethodName = "addWorkFlowForGroup";
	HashMap oHashMap = new HashMap();
	oHashMap.put("SCMGroupId",sSelectedGroupID);
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	oProcessor.excute(sServiceIdOfWorkFlow,sMethodName,oHashMap);
%>