<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ include file="/app/include/public_server.jsp"%>
<%
	int nDocId = currRequestHelper.getInt("DocumentId", 0);
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	ChnlDoc chnlDoc = ChnlDoc.findByDocAndChnl(nDocId, nChannelId);
	String sUrl = "../../../../document/document_detail.jsp?" + request.getQueryString();
	sUrl += "&ChnlDocId=" + chnlDoc.getId()+"&canEdit=0";
	//防止CRLF注入，去除回车换行
	sUrl = sUrl.replaceAll("(?i)%0d|%0a","");
	response.sendRedirect(sUrl);
%>