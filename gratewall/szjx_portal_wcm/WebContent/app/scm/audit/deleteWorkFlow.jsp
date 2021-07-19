<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp" %>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 2 获取参数
	int nCurrContentId = currRequestHelper.getInt("CurrContentId",0); //流转的对象id 

	// 3 调用服务
    String sServiceIdOfWorkFlow = "wcm61_scmmicrocontent";
	String sMethodName = "delete";
	
	HashMap oHashMap = new HashMap();
	oHashMap.put("ObjectIds",nCurrContentId);

	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);

	oProcessor.excute(sServiceIdOfWorkFlow,sMethodName,oHashMap);

%>