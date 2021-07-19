<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.cms.CMSConstants" %>

<%@ page import="com.trs.ajaxservice.JSONHelper" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@include file="../include/public_server.jsp"%>
<%!
	private String getUrl(CMSObj obj)throws WCMException{
		String sPageHttpURL = null;
		if(obj instanceof Document){
			IPublishContent content = (IPublishContent)PublishElementFactory.makeElementFrom(obj);
			IPublishFolder folder = content.getRealHome();
			content.setFolder(folder);
			if(content.getBodyType() == CMSConstants.CONTENT_BODY_FILE){
				PublishPathCompass compass = new PublishPathCompass();
				String sFileName = content.getFileName();
				sPageHttpURL = compass.getAbsoluteHttpPath(content) + sFileName;
			}else if (content.isLink()) {
				sPageHttpURL = content.getLinkUrl();
			} else {
				PublishPathCompass compass = new PublishPathCompass();
				sPageHttpURL = compass.getHttpUrl(content, 0);
			}
		}else{
			IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(obj);
			if (folder.isLink()) {
				sPageHttpURL = folder.getLinkUrl();
			} else {
				PublishPathCompass compass = new PublishPathCompass();
				sPageHttpURL = compass.getAbsoluteHttpPath(folder);
			}
		}
		return sPageHttpURL;
	}

	private void pushItem(Document document,ArrayList items, boolean isAbsoluteUrl) throws WCMException{
		String sTitle = document.getTitle();
		String sPageHttpURL = getUrl(document);

		//获取相对站点的地址
		if(!isAbsoluteUrl){
			Channel channel = document.getChannel();
			WebSite site = channel.getSite();
			String sSiteHttpURL = getUrl(site);
			sPageHttpURL = sPageHttpURL.substring(sSiteHttpURL.length());
			if(!sPageHttpURL.startsWith("/")){
				sPageHttpURL = "/" + sPageHttpURL;
			}
		}

		HashMap oHashMap = new HashMap();
		oHashMap.put("ID",""+document.getId());
		oHashMap.put("TITLE",sTitle);
		oHashMap.put("PUBURL",sPageHttpURL);
		items.add(oHashMap);
	}
%>
<%
	response.setHeader("ReturnJson", "true");
	boolean bIsAbsoluteUrl = currRequestHelper.getBoolean("isAbsoluteUrl", false);
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	String sDocumentIds = currRequestHelper.getString("DocumentIds");
	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	ArrayList arrayList = new ArrayList();
	Document document = null;  
	//获得currDocument对象
	if(nDocumentId > 0){
		document  = Document.findById(nDocumentId);
		if(document != null){
			pushItem(document,arrayList, bIsAbsoluteUrl);
		}
	}
	else{
		if(sDocumentIds!=null){
			String[] arrDocumentIds = sDocumentIds.split(",");
			for(int i=0;i<arrDocumentIds.length;i++){
				try{
					nDocumentId = Integer.parseInt(arrDocumentIds[i]);
					if(nDocumentId > 0){
						document  = Document.findById(nDocumentId);
						if(document != null){
							pushItem(document,arrayList, bIsAbsoluteUrl);
						}
					}
				}catch(Exception ex){
				}
			}
		}
	}
	out.clear();
	HashMap[] hashMaps = new HashMap[arrayList.size()];
	arrayList.toArray(hashMaps);
	HashMap oHashMap = new HashMap();
	oHashMap.put("DOCUMENTS",hashMaps);
	out.println(JSONHelper.toSimpleJSON2(oHashMap,0));
%>