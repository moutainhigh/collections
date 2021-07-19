<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.VideoDocUtil" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	String token = request.getParameter("token");
	String sDocId = request.getParameter("docId");
	String qryStr = request.getQueryString();
	int docId = (sDocId == null) ? -1 : Integer.parseInt(sDocId);
	
	VideoDocUtil vUtil = new VideoDocUtil();
	vUtil.appendMetadataToAttribute(docId, qryStr, token);
	out.clear();
	out.print("token:" + CMyString.transDisplay(token) + "<br>");
	out.print("sDocId:" + CMyString.transDisplay(sDocId) + "<br>");
	out.print("qryStr:" + CMyString.transDisplay(qryStr) + "<br>");
%>