<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_processor.jsp"%>
<%
	processor.excute("wcm61_advisorcenter", "createXMLForAdvisor");
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="create_xml_for_advisor.jsp.trswcmcreateadvisorxml">TRS WCM 生成顾问xml</TITLE>
</HEAD>
<BODY>
<SCRIPT LANGUAGE="JavaScript">
top.returnValue = true;
top.close();
</SCRIPT>
</BODY>
</HTML>