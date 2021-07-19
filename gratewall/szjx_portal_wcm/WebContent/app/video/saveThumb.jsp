<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.components.video.VideoDocUtil" %>
<%@ page import="com.trs.components.video.VideoInfo" %>
<%@ page import="com.trs.components.video.persistent.XVideo" %>
<%@ page import="com.trs.dl.util.servlet.RequestUtil" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ include file="../../include/public_server.jsp" %>

<%
	String thumbName = RequestUtil.getParameterAsTrimed(request, "thumbName", "");
	if("".equals(thumbName)) {
		return;
	}
	String sDocId = request.getParameter("docId");
	int docId = (sDocId == null) ? -1 : Integer.parseInt(sDocId);
	if (docId >= 0) {
		//默认使用高品质视频，如果没有，则使用低品质的。
		XVideo xvideo = XVideo.findByDocIdAndQuality(docId, XVideo.QUALITY_HIGH);
		if (xvideo != null) {
			xvideo.setThumb(thumbName);
			xvideo.save(ContextHelper.getLoginUser());
		}
		xvideo = XVideo.findByDocIdAndQuality(docId, XVideo.QUALITY_LOW);
		if (xvideo != null) {
			xvideo.setThumb(thumbName);
			xvideo.save(ContextHelper.getLoginUser());
		}
	}
	out.clear();
%>