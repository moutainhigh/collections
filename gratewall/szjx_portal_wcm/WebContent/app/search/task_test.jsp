<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTask" %>
<%@ page import="com.trs.wcm.trsserver.task.ISearchTaskMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.wcm.trsserver.task.SearchTaskConstants" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSServer" %>
<%@ page import="com.eprobiti.trs.TRSConnection" %>
<%@ page import="com.trs.components.wcm.content.trsserver.DatasFromTRSServer" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateway" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.trs.gateway.client.GWConnection" %>
<%@ page import="com.trs.gateway.client.GWManager" %>
<%@ page import="com.trs.gateway.client.GWConstants" %>
<%@include file="../include/public_server.jsp"%>
<%
	//参数接收
	int nServerId = currRequestHelper.getInt("serverid",0);
	int nGatewayId = currRequestHelper.getInt("gatewayid",0);

	//参数校验
	TRSServer server = TRSServer.findById(nServerId);
	if(server == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("task_test.jsp.trsserver_notfound", "没有找到ID={0}的TRSServer!"), new int[]{nServerId}));

	TRSGateway gateway = TRSGateway.findById(nGatewayId);
	if(gateway == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("task_test.jsp.trsgateway_notfound", "没有找到ID={0}的TRSGateway!"), new int[]{nGatewayId}));

	//业务逻辑
	DatasFromTRSServer newServer = new DatasFromTRSServer();
	TRSConnection currConnection = newServer.getValidConnection(server.getIP(),server.getPort() + "",server.getUserName(),server.getPassword());
	if(currConnection == null){
		out.println("server");
	}else{
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
			out.println("gateway");
		}
		if(gwconnection == null){
			out.println("gateway");
		}
	}
%>