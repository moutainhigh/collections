<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	String sMicroContentId = currRequestHelper.getString("MicroContentId");
	if(IS_DEBUG){
		System.out.println("nAccountId:"+nAccountId);
		System.out.println("sMicroContentId:"+sMicroContentId);
	}
	// 2 调用服务删除微博
	String sServiceId = "wcm61_scmmicrocontent",sMethodName="destroyMicroContent";
	try{
		Boolean oResult = (Boolean)oProcessor.excute(sServiceId,sMethodName);
	}catch(CMyException e){
		out.print(e.getMyMessage());
		return;
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
	
	out.print(1);
%>