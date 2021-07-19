<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.cluster.FileMsgProcessor"%>
<%@include file="../../include/public_server.jsp"%>
<%
	//业务处理
	String sText = "";
	//'initStyleSelect' : initStyleSelect, 'nSiteId' : FCK.nSiteId
	//初始化时，需要判断，选用默认样式还是站点样式进行初始化initStyleSelect：0为默认样式，1为站点样式
	int initStyleSelect = currRequestHelper.getInt("initStyleSelect",0);
	int nSiteId = currRequestHelper.getInt("nSiteId",0);
	try{
		String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
		if(initStyleSelect == 0 || nSiteId == 0){
			sText = CMyFile.readFile(sDomain + "/app/editor/editor/css/editor.css","UTF-8");
		} else {
			if(CMyFile.fileExists(sDomain + "/app/editor/editor/css/" + nSiteId + ".css")){
				sText = CMyFile.readFile(sDomain + "/app/editor/editor/css/" + nSiteId + ".css", "UTF-8");
			} else {
				sText = CMyFile.readFile(sDomain + "/app/editor/editor/css/editor.css","UTF-8");
			}
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	//结束
	out.clear();
	out.print(sText);
%>