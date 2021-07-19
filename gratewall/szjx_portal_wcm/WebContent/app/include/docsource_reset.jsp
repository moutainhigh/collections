<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" errorPage="error.jsp"%>
<%@ page import="com.trs.components.wcm.resource.Sources" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	String sDocSourceName = currRequestHelper.getString("DocSourceName");
	out.clear();
	if(sDocSourceName != null){
		WCMFilter filter =  new WCMFilter("","SRCNAME = ?","");
		filter.addSearchValues(sDocSourceName);
		Sources sources = Sources.openWCMObjs(loginUser,filter);
		if(sources.size() > 0){
			Source source = (Source)sources.getAt(0);
			if(source != null){
				out.print(source.getId());
			}
		}
	}
%>