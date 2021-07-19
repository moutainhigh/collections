<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ include file="/app/include/public_server.jsp"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control","private"); 
%>
<%
int nViewId = 0;
try{
	int nChannelId = currRequestHelper.getInt("channelId", 0);
	Channel channel = Channel.findById(nChannelId);
	IMetaViewEmployerMgr employerMgr = (IMetaViewEmployerMgr) DreamFactory.createObjectById("IMetaViewEmployerMgr");
	MetaView metaView = employerMgr.getViewOfEmployer(channel);
	nViewId = metaView.getId();
}catch(Exception e){
}
out.clear();%><%=nViewId%>