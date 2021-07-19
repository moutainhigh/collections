<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page language="java" import="t4j.TBlog"%>
<%@ page language="java" import="t4j.TBlogException"%>
<%@ page language="java" import="t4j.http.AccessToken"%>
<%@ page language="java" import="t4j.http.RequestToken"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.util.Properties"%>
<%@ page import="org.apache.log4j.helpers.Loader"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ include file="../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false; %>
<%
	//参数设置
	Properties oConfigProperties = new Properties();
	URL url = Loader.getResource("T163Config.properties");
	if (url == null){
		throw new NullPointerException("未找到网易微博配置文件：T163Config.properties");
	}
	oConfigProperties.load(url.openStream());

	String sClientID = oConfigProperties.getProperty("client_ID");
	String sClient_Secret = oConfigProperties.getProperty("client_SERCRET");
	
	//将应用信息设置到系统变量中
	System.setProperty("tblog4j.oauth.consumerKey", sClientID);
	System.setProperty("tblog4j.oauth.consumerSecret", sClient_Secret);
	System.setProperty("tblog4j.debug", "false");

	//获取访问的URL
	// sServerName = 127.0.0.1:8080
	String sServerName = request.getHeader("X-FORWARDED-HOST");
	if (sServerName == null || sServerName.length() < 1) {  
		sServerName = request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));  
	} else if (sServerName.contains(",")) {  
		sServerName = sServerName.substring(0, sServerName.indexOf(",")).trim();  
	}
	String path = request.getContextPath();
	sServerName = sServerName + path + "/";

	TBlog tblog = new TBlog();
	RequestToken requestToken = (RequestToken)session.getAttribute("requestToken");  
	if(requestToken==null){
		requestToken = tblog.getOAuthRequestToken(); 
		session.setAttribute("requestToken",requestToken) ; 
	}
	// 链接到授权页面	
	String sBaseHost = request.getScheme()+"://"+sServerName + "app/scm/t163_authorized_callback.jsp";
	if(IS_DEBUG){
		System.out.println(requestToken.getAuthenticationURL(sBaseHost));
	}
	//不使用Cookies，重新登录帐号！
	response.sendRedirect(requestToken.getAuthenticationURL(sBaseHost));
%>