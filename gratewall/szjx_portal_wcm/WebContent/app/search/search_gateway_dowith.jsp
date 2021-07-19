<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTask" %>
<%@ page import="com.trs.wcm.trsserver.task.ISearchTaskMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.wcm.trsserver.task.SearchTaskConstants" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateway" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.trs.gateway.client.GWConnection" %>
<%@ page import="com.trs.gateway.client.GWManager" %>
<%@ page import="com.trs.gateway.client.GWConstants" %>
<%@include file="../include/public_server.jsp"%>
<%
	// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限操作检索配置！");
	}
	//参数接收
	int nTaskId = currRequestHelper.getInt("taskId",0);
	int nGatewayId = currRequestHelper.getInt("gatewayId",0);

	//参数校验
	TRSGateway gateway = TRSGateway.findById(nGatewayId);
	if(gateway == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("search_gateway_dowith.jsp.trsgateway_notfound", "没有找到ID为[{0}]的TRSGateway!"), new int[]{nGatewayId}));

	SearchTask task = SearchTask.findById(nTaskId);
	if(task == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("search_gateway_dowith.jsp.cannot_find_search_task", "没有找到ID为[{0}]的检索任务!"), new int[]{nTaskId}));

	//业务逻辑
	GWConnection gwconnection = null;
	try{
		Properties properties = new Properties();
		// 网关连接参数
		properties.setProperty(GWConstants.GW_HOST, gateway.getIP());
		properties.setProperty(GWConstants.GW_PORT,
				gateway.getPort() + "");
		properties.setProperty(GWConstants.GW_USERNAME,
				gateway.getUserName());
		properties.setProperty(GWConstants.GW_PASSWORD,
				gateway.getPassword());

		gwconnection = GWManager.getConnection(properties);
	}catch(Exception e){
	}
	if(gwconnection == null){
		out.println("false");
	}else{
		task.setTRSGWId(nGatewayId);
		ISearchTaskMgr oSearchTaskMgr = (ISearchTaskMgr) DreamFactory
				.createObjectById("ISearchTaskMgr");
		task = oSearchTaskMgr.saveTask(loginUser, task);
	}
%>