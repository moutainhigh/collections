<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>
<%

String width = request.getParameter("playerWidth");
String height = request.getParameter("playerHeight");
String logoCss = request.getParameter("logoCss");
String autoPlay = request.getParameter("autoPlay");

VSConfig.setFckPlayerHeight(height);
VSConfig.setFckPlayerWidth(width);
VSConfig.setFckPlayerLOGO(logoCss);
VSConfig.setFckPlayerAutoplay(autoPlay);

%>
