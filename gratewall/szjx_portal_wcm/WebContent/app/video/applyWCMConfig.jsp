<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.WCMConfigServerBasedManager" %>
<%@ page import="com.trs.dl.util.ConfigFileModifier" %>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	String vsUrl = request.getParameter("vsUrl");
	String vodUrl = request.getParameter("vodUrl");
	String liveUrl = request.getParameter("liveUrl");
	String thumbsUrl = request.getParameter("thumbsUrl");
	String vsUrlOutIp = request.getParameter("vsUrlOutIp");
	String bitrates = request.getParameter("bitrate");
	String trsmasVersion = request.getParameter("trsmasVersion");
	String appKey = request.getParameter("appKey");
	String extUpload = request.getParameter("extUpload");
	String maxSize = request.getParameter("maxSize");
	
	//VSConfig.setMaxPostSize("209715200");
	VSConfig.setAppKey(appKey);
	VSConfig.setUploadFMSAppUrl(vodUrl);
	VSConfig.setUploadJavaAppUrl(vsUrlOutIp);
	VSConfig.setRecordFMSAppUrl(liveUrl);
	VSConfig.setThumbsHomeUrl(thumbsUrl);
	VSConfig.setUploadJavaAppIntranetUrl(vsUrl);
	VSConfig.setBitrates(bitrates);
	VSConfig.setTRSMASVersion(trsmasVersion);
	if(VSConfig.getFLVPlayerBase()==null||"".equals(VSConfig.getFLVPlayerBase())){
		VSConfig.setFLVPlayerBase("/wcm/app/video/");
	}
	VSConfig.setExtUplaod(extUpload);
	VSConfig.setMaxPostSize(maxSize);
	VSConfig.reloadConfigs();
	out.clear();
%>