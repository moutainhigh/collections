<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 2 获取参数
	int nType = currRequestHelper.getInt("Type",0);
	int nWorkFlowId = currRequestHelper.getInt("WorkFlowId",0);
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);

	// 3 调用服务删除微博
	String sServiceIdOfWorkFlow = "wcm61_scmworkflow";
	String sMethodNameOfDefaultWorkFlow = "setDefaultWorkFlow";
	String sMethodNameOfGroupWorkFlow = "setWorkFlowForGroup";
	
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);

	if(nType == 1){
		//设置默认工作流
		oProcessor.excute(sServiceIdOfWorkFlow,sMethodNameOfDefaultWorkFlow);
	}else if(nType > 1){
		//设置分组工作流
		if(nSCMGroupId > 0){
			oProcessor.excute(sServiceIdOfWorkFlow,sMethodNameOfGroupWorkFlow);
		}
	}
%>