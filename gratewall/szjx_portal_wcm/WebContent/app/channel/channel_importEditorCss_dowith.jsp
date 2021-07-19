<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.cluster.FileMsgProcessor"%>
<%@include file="../include/public_server.jsp"%>
<%
	//获取参数
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	int nSiteId = currRequestHelper.getInt("SiteId", 0);
	String sRelateIds = currRequestHelper.getString("RelateIds");	
	String sImportFile = currRequestHelper.getString("ImportFile");	
	if(nChannelId > 0){
		Channel currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("channel.object.not.found", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
		}
	}else if(nSiteId > 0){
		WebSite currWebSite = WebSite.findById(nSiteId);
		if(currWebSite == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("website.object.not.found", "没有找到ID为{0}的站点"), new int[]{nSiteId}));
		}
	}
	//业务处理
	String sSrcFilePathName = FilesMan.getFilesMan().mapFilePath(sImportFile, FilesMan.PATH_LOCAL) + sImportFile;
	if(!sRelateIds.trim().equals("") && nChannelId > 0){
		for(int i=0; i<sRelateIds.split(",").length;i++){
			createCssFile(sSrcFilePathName,sRelateIds.split(",")[i]);
		}		
	}
	createOwnerCssFile(sSrcFilePathName,nChannelId , nSiteId);
	//结束
	out.clear();
%>
<%!
	private void createCssFile(String sSrcFilePathName ,String sChannelId) throws WCMException{
		try{
			String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
			CMyFile.copyFile(sSrcFilePathName,sDomain + "/app/editor/editor/css/channel/" + sChannelId + ".css");
			FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/editor/css/channel/" + sChannelId + ".css");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void createOwnerCssFile(String sSrcFilePathName ,int nChannelId, int nSiteId) throws WCMException{
		try{
			String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
			if(nChannelId > 0){
				CMyFile.moveFile(sSrcFilePathName,sDomain + "/app/editor/editor/css/channel/" + nChannelId + ".css");
				FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/editor/css/channel/" + nChannelId + ".css");
			}else if(nSiteId > 0){
				CMyFile.moveFile(sSrcFilePathName,sDomain + "/app/editor/editor/css/site/" + nSiteId + ".css");
				FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/editor/css/site/" + nSiteId + ".css");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>