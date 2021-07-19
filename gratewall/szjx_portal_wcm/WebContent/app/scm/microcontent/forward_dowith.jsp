<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	String sMicroContentId = currRequestHelper.getString("MicroContentId");
	String sContent = currRequestHelper.getString("Content");
	String sAccountIds = currRequestHelper.getString("AccountIds");
	String sServiceId = "wcm61_scmmicrocontent",sMethodName="forwardMicroContent";
	if(IS_DEBUG){
		System.out.println("nSCMGroupId:"+nSCMGroupId);
		System.out.println("sMicroContentId:"+sMicroContentId);
		System.out.println("sContent:"+sContent);
		System.out.println("sAccountIds:"+sAccountIds);
	}

	String sErrorMsg = "";
	HashMap oParamters = new HashMap();
	oParamters.put("AccountIds",sAccountIds);
	oParamters.put("MicroContentId",sMicroContentId);
	oParamters.put("Content",sContent);
	oParamters.put("SCMGroupId",String.valueOf(nSCMGroupId));
	//调用转发服务
	try{
		oProcessor.excute(sServiceId,sMethodName,oParamters);
	}catch(Exception e){
		sErrorMsg = e.getMessage();
		if(sErrorMsg.indexOf("[ERR-") >= 0 && sErrorMsg.indexOf("<--") >= 0){
			sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("<--"));
		}
	}
	if(!CMyString.isEmpty(sErrorMsg)){
		out.print(sErrorMsg);
		return;
	}else{out.print(1);}
%>