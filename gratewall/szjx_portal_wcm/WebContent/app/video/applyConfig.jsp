<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
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
	//VSConfig.setIConfigManager(FileBasedIConfigManager.getInstance());
	//VSConfig.setIConfigManager(WCMConfigBasedManager.getInstance());
	//VSConfig.setUploadFMSAppUrl(vodUrl);
	

    String cfgFilePath = application.getRealPath("/WEB-INF/classes/trswcmx-video.properties");
	ConfigFileModifier cfgModifier = new ConfigFileModifier(cfgFilePath);
	cfgModifier.modifyProperty(VSConfig.KEY_UPLOAD_JAPP, vsUrlOutIp);
	cfgModifier.modifyProperty(VSConfig.KEY_UPLOAD_FMS, vodUrl);
	cfgModifier.modifyProperty(VSConfig.KEY_RECORD_FMS, liveUrl);
	cfgModifier.modifyProperty(VSConfig.KEY_THUMB_HOME, thumbsUrl);
	cfgModifier.modifyProperty(VSConfig.KEY_UPLOAD_JAPP_INTRANET, vsUrl);
	cfgModifier.modifyProperty(VSConfig.KEY_BITRATES, bitrates);
	cfgModifier.modifyProperty(VSConfig.KEY_TRSMAS_VERSION, trsmasVersion);
	cfgModifier.saveModifies();


	VSConfig.reloadConfigs();
	out.clear();
%>