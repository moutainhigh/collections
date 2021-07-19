<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.govinfo.GovInfoViewFinder" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	//获取政府信息公开视图的Id
	MetaView oMetaView = GovInfoViewFinder.findMetaViewOfGovInfo(null);
	MetaView oChildMetaView = GovInfoViewFinder.findMetaViewOfChildGovInfo(null);
	int nMetaViewId = 39;
	int nChildMetaViewId = 43;
	if(oMetaView != null){
		nMetaViewId = oMetaView.getId();
	}
	if(oChildMetaView != null){
		nChildMetaViewId = oChildMetaView.getId();
	}
	String sGovInfoViewId = nMetaViewId + "," + nChildMetaViewId;
	out.clear();
	out.println(sGovInfoViewId);
%>