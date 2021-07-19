
<%@page import="com.trs.components.video.VSConfig"%>
<%@page import="com.trs.components.video.util.HttpUtil"%><%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%
   String sURL=request.getParameter("videoServiceURL")+"/service/sysinfo";
   out.print(HttpUtil.getResponseTextWithPost(sURL+"?appKey="+request.getParameter("appKey"),""));
%>