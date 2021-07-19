<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_for_dialog.jsp"%>
<%@include file="../include/public_processor.jsp"%>
<%
	//保存站点
	int nWebSiteId = ((Integer)processor.excuteMult("website", "save")).intValue();
	//保存发布信息
	processor.setAppendParameters(new String[]{
		"ObjectId", ""+nWebSiteId, 
	});
	excuteMult(processor, "wcm6_publish", "saveSitePublishConfig");
	processor.reset();
	out.clear();
%><%=nWebSiteId%>