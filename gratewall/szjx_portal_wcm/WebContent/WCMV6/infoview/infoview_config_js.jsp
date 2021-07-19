<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="infoview_config.jsp"%>
		var infoview_config = {}; 
		infoview_config.enable 	= false;
<%
	if(bIsInfoviewEnable){
%>
		infoview_config.enable 	= true;
<%
	}
%>