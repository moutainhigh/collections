<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.scm.sdk.model.MicroContentWrapper" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContents" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ page import="com.trs.scm.sdk.model.Comment" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	String sCommentId = currRequestHelper.getString("CommentId");
	// 2 调用服务删除微博
	String sServiceId = "wcm61_scmcomment",sMethodName="destroyComment";
	if(IS_DEBUG){
		System.out.println("nAccountId:"+nAccountId);
		System.out.println("CommentId:"+sCommentId);
	}
	Boolean oResult = (Boolean)oProcessor.excute(sServiceId,sMethodName);
	if(oResult.booleanValue()==true){out.print(1);}else{out.print(0);
	}
%>