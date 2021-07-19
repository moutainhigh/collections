<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.cluster.FileMsgProcessor"%>
<%@include file="../../../../include/public_server.jsp"%>
<%
	String sText = currRequestHelper.getString("cssText");
	String saveToSiteFile = currRequestHelper.getString("saveSiteStyle");
	String saveToDefaultFile = currRequestHelper.getString("saveDefaultStyle");
	int nSiteId = currRequestHelper.getInt("nSiteId",0);
	//业务处理
	try{
		//String sDomain = ConfigServer.getServer().getSysConfigValue("WCM_PATH","");
		String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
		if(saveToSiteFile.equalsIgnoreCase("true")){
			CMyFile.writeFile(sDomain + "/app/editor/editor/css/" + nSiteId + ".css",sText,CMyString.GET_ENCODING_DEFAULT);
			FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/editor/css/"+ nSiteId + ".css");
		}
		if(saveToDefaultFile.equalsIgnoreCase("true")){
			CMyFile.writeFile(sDomain + "/app/editor/editor/css/editor.css",sText,CMyString.GET_ENCODING_DEFAULT);
			FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/editor/css/editor.css");
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	//结束
	out.clear();
%>