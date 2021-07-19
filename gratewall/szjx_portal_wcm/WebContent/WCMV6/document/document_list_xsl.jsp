<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	IDocumentService currDocumentService = ServiceHelper.createDocumentService();
	File[] arFile = currDocumentService.getExitedXslFiles();
	if(arFile == null) {
		arFile = new File[0];
	}
	out.clear();
%>
<SELECT id="selectXslFile" style="width:200px;margin:0;padding:0">
	<OPTION value="">--------------------------------------------</OPTION>
<%
	File currFile = null;
	CMyDateTime myDateTime = CMyDateTime.now();
	for(int i=0; i<arFile.length; i++) {
		currFile = arFile[i];
		if(currFile == null)
			continue;
		myDateTime.setDateTime(new Date(currFile.lastModified()));
%>
		<OPTION value="<%=currFile.getName()%>">
			<%=currFile.getName()%> , <%=myDateTime.toString()%>
		</OPTION>
<%
	}
%>
</SELECT>