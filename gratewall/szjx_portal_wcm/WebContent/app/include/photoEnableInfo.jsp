<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.presentation.plugin.PluginConfig" %>
<%
out.clear();
out.print("var bPhotoEnable = " + PluginConfig.isStartPhoto());
%>