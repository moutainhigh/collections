<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.wcmonline.WCMOnlineService" %>
<!------- WCM IMPORTS END ------------>
<%
	// by CC 20120509 根据站点ID获取到对应的栏目信息
	String sParentId = request.getParameter("nParentId");
	String sSiteId = "";
	if("0".equals(sParentId)) sSiteId = request.getParameter("nSiteId");

	String sChannels = (new WCMOnlineService()).loadTree(sParentId,sSiteId);
	String sJsonP = request.getParameter("jsonp");
	if(sJsonP != null){
		out.println(sJsonP+"(\"" + sChannels + "\");");
	}
%>