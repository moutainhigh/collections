<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	//获取出错信息
	String sErrorInfo = (String)session.getAttribute("ErrorInfo");
	if(sErrorInfo != null){
		session.removeAttribute("ErrorInfo");
		out.clear();
		out.println("{errorInfo:'" + CMyString.filterForJs(sErrorInfo) + "'}");
		return;
	}

	//获取生成的用户信息
	String sSuccessInfo = (String)session.getAttribute("SuccessInfo");
	Integer nIsSuccess = (Integer)session.getAttribute("isSuccess");
	String sCurrentPageStyle = (String)session.getAttribute("currentPageStyle");
	if(sSuccessInfo != null){
		session.removeAttribute("SuccessInfo");
		if(sCurrentPageStyle==null){
			sCurrentPageStyle = "保存页面风格，";
		}
		out.clear();
		if(nIsSuccess == 0){
			out.println("{successInfo:'" + CMyString.filterForJs(sSuccessInfo) + "',isRunning:true,currentPageStyle:'"+sCurrentPageStyle+"'}");
		}else{
			out.println("{successInfo:'" + CMyString.filterForJs(sSuccessInfo) + "',isRunning:false,currentPageStyle:'"+sCurrentPageStyle+"'}");
		}
	}else{
		out.println("{isRunning:true,currentPageStyle:'"+sCurrentPageStyle+"'}");
	}
%>