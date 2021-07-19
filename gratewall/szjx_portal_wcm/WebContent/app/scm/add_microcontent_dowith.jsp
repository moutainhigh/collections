<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 2 构造参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	String sServiceId = "wcm61_scmmicrocontent",sMethodName="save";
	
	// 3 调用发布微博服务
	Object oResult = null;
	String sMessage = "";
	try{
		oResult = (Object)oProcessor.excute(sServiceId,sMethodName);
	}catch(Exception e){
		out.clear();
		sMessage = e.getMessage();
		if(e instanceof CMyException){
			sMessage = ((CMyException) e).getMyMessage();
			if(sMessage.indexOf("[ERR-") > 0){
				sMessage = sMessage.substring(sMessage.indexOf("[ERR-"),sMessage.indexOf("<--"));
			}
		}
	}
	if(oResult!=null){out.print(1);}else{out.print(sMessage);}
%>