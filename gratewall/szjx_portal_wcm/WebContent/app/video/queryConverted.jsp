<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@ page import="com.trs.components.video.VideoDocUtil" %>
<%@ page import="com.trs.dl.util.servlet.RequestUtil" %>
<%
	String token = request.getParameter("token");
	int docId = RequestUtil.getParameterAsInt(request, "docId");
//	old
//	String jsonResult = VideoDocUtil.queryConvertStatus(token);
	String jsonResult = VideoDocUtil.updateTranscodeStatus(token, docId);
	out.clear();
	out.println(jsonResult);
%>