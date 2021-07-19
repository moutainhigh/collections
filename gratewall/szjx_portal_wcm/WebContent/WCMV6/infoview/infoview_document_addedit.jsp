<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%!boolean IS_DEBUG = false;%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="infoview_config.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>
<%		
	String sTarget = null;
	//获取表单选件是否已经部署并可用
	if (bIsInfoviewEnable) {
		sTarget = "/wcm/infoview/infoview_document_addedit.jsp"
				+ "?" + request.getQueryString();

	}else{
		sTarget = "infoview_no_use.html";
	}
%>
<script language="javascript">
<!--
	top.location.href = '<%=CMyString.filterForJs(sTarget)%>';
//-->
</script>