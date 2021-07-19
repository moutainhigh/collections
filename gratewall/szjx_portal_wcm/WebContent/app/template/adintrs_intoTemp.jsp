
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.List" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	String sHtml = currRequestHelper.getString("advalue");
	String sAdLocationIds = currRequestHelper.getString("adLocationIds");
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	if(parent!=window && parent.onOk){
		if("<%=CMyString.filterForJs(sHtml)%>"!=""){
			if("<%=sAdLocationIds%>" == "0"){
				parent.onOk("<%=CMyString.filterForJs(sHtml)%>");
			}else{
				parent.onOk("<%=CMyString.filterForJs(sHtml)%>","<%=CMyString.filterForJs(sAdLocationIds)%>");
			}
		}else
			parent.onCancel();
	}
//-->
</SCRIPT>
