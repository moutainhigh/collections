<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%
	String sViewId = request.getParameter("viewId");
	String sChannelId = request.getParameter("channelId");
	String sObjectId = request.getParameter("objectId");
	if(sChannelId == null || sViewId == null || sObjectId == null){
		throw new Exception("没有传入正确的参数：[viewId:" + sViewId + ",objectId:" + sObjectId + ",channelId:" + sChannelId + "];");
	}
	StringBuffer sb = new StringBuffer(80);
	sb.append("application/" + sViewId);
	sb.append("/detail_of_view.jsp");
	sb.append("?objectId=" + sObjectId);
	sb.append("&ObjectId=" + sObjectId);
	sb.append("&channelId=" + sChannelId);
	sb.append("&ChannelId=" + sChannelId);
//	String sPath = "application/" + sViewId + "/detail_of_view.jsp?objectId=" + sObjectId + "&channelId=" + sChannelId;
	response.sendRedirect(sb.toString());
%>