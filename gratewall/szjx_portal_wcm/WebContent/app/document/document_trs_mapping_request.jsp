<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	response.setHeader("ReturnJson", "true");
//6.业务代码
	IDocumentService currDocumentService = ServiceHelper.createDocumentService();
	File[] arFile = currDocumentService.getExitedMappingFiles();
	if(arFile == null) {
		arFile = new File[0];
	}
//7.结束
	out.clear();
%>[<%
	File currFile = null;
	CMyDateTime myDateTime = CMyDateTime.now();
	boolean bFirst = true;
	for(int i=0; i<arFile.length; i++) {
		currFile = arFile[i];
		if(currFile == null)
			continue;
		myDateTime.setDateTime(new Date(currFile.lastModified()));
		if(bFirst){
			bFirst = false;
		}
		else{
%>,<%
		}
%>{"FileName":"<%=currFile.getName()%>","LastModified":"<%=myDateTime.toString()%>"}<%
	}
%>]