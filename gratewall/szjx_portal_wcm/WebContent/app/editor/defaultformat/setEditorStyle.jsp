<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.cluster.FileMsgProcessor"%>
<%@ page import="java.io.File"%>
<%@ page import="com.trs.cms.content.CMSObj"%>
<%@ page import="com.trs.components.common.publish.domain.distribute.FileDistributeShip"%>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder"%>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory"%>
<%@ page import="com.trs.components.wcm.content.domain.ChannelMgr"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.DefaultFormat"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyFile"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites"%>
<%@ page import="java.util.HashMap" %>

<%@include file="../../include/public_server.jsp"%>
<%
	String sText = currRequestHelper.getString("cssText");
	int nObjId = currRequestHelper.getInt("ObjId",0);
	int nObjType = currRequestHelper.getInt("ObjType",0);

	//业务处理
	try{
		//String sDomain = ConfigServer.getServer().getSysConfigValue("WCM_PATH","");
		String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
		if(nObjType == Channel.OBJ_TYPE && nObjId !=0 ){
			Channel currChannel = Channel.findById(nObjId);
			int nSiteId = currChannel.getSiteId();
			CMyFile.writeFile(sDomain + "/app/editor/css/site_" + nSiteId + "/channel_" + nObjId + ".css",sText,CMyString.GET_ENCODING_DEFAULT);
			FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/css/site_" + nSiteId + "/channel_" + nObjId +".css");
		}
		else if(nObjType == WebSite.OBJ_TYPE && nObjId !=0 ){
			CMyFile.writeFile(sDomain + "/app/editor/css/site_" + nObjId + ".css",sText,CMyString.GET_ENCODING_DEFAULT);
			FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/css/site_" + nObjId + ".css");
		}else {
			String srcFilePath = sDomain + "/app/editor/defaultformat/systemConfig.css";
			String srcFileName = "systemConfig.css";
			System.out.println("[系统级默认排版正在处理...]");
			CMyFile.writeFile(sDomain + "/app/editor/defaultformat/systemConfig.css",sText,CMyString.GET_ENCODING_DEFAULT);
			FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/defaultformat/systemConfig.css");
			//保存到pub的各个站点下
			JSPRequestProcessor processor = new JSPRequestProcessor(request, response); 
			String sServiceId = "wcm6_webSite", sMethodName = "query";
			HashMap parameters = new HashMap();
			parameters.put("SiteType", "0");
			WebSites result = (WebSites) processor.excute(sServiceId,sMethodName, parameters);
			for (int i = 0; i < result.size(); i++) {
				try{
					WebSite webSite = (WebSite) result.getAt(i);
					if (webSite == null)
							continue;
					IPublishFolder folder = (IPublishFolder) PublishElementFactory.makeElementFrom(webSite);
					// 根据罗盘，获取拷贝目标地址，folder可能为site，也可能为channel
					PublishPathCompass compass = new PublishPathCompass();
					String sLocalPath = null;
					try {
						for (int j = 0; j < 2; j++) {
							boolean isPreview = false;
							if (j == 1) {
								isPreview = true;
							}
							// 第二个参数如果为true，表示在preview目录下，false 这表示在pub目录下
							String sLocalConstruct = compass.getLocalPath(folder,isPreview).replace('\\', File.separatorChar);
							sLocalPath = sLocalConstruct + srcFileName;
							// 维护分发关系
							if (!isPreview) {
								FileDistributeShip fileDistributeShip = new FileDistributeShip();
								fileDistributeShip.distributeFile(srcFilePath, folder,null, true);
							}
							// 复制，拷贝文件
							CMyFile.copyFile(srcFilePath, sLocalPath, true);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}catch (Exception ex){
					// todo
				}
			}
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	//结束
	out.clear();
%>