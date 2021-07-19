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
<%@include file="../include/public_server.jsp"%>
<%
	// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限操作检索配置！");
	}
	//参数接收
	int nTaskId = currRequestHelper.getInt("taskId",0);
	int nServerId = currRequestHelper.getInt("serverId",0);

	//参数校验
	TRSServer server = TRSServer.findById(nServerId);
	if(server == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("search_server_dowith.jsp.trsserver_notfound", "没有找到ID={0}的TRSServer!"), new int[]{nServerId}));

	SearchTask task = SearchTask.findById(nTaskId);
	if(task == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("search_server_dowith.jsp.cannot_find_search_task", "没有找到ID为[{0}]的检索任务!"), new int[]{nTaskId}));

	//业务逻辑
	DatasFromTRSServer newServer = new DatasFromTRSServer();
	TRSConnection currConnection = newServer.getValidConnection(server.getIP(),server.getPort() + "",server.getUserName(),server.getPassword());
	if(currConnection == null){
		out.println("false");
	}else{
		task.setTRServerId(nServerId);
		ISearchTaskMgr oSearchTaskMgr = (ISearchTaskMgr) DreamFactory
				.createObjectById("ISearchTaskMgr");
		task = oSearchTaskMgr.saveTask(loginUser, task);
	}
%>