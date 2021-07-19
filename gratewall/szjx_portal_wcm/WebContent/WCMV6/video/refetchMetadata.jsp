<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.VideoDocUtil" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%
	String token = request.getParameter("token");
	String sDocId = request.getParameter("docId");
	int docId = (sDocId == null) ? -1 : Integer.parseInt(sDocId);

	VideoDocUtil vUtil = new VideoDocUtil();
	String jsonMeta = vUtil.refetchMetadata(token);
	out.clear();
	out.println(jsonMeta);
%>