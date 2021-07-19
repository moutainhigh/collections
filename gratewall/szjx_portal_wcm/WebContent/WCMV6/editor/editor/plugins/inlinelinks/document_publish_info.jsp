<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.ajaxservice.JSONHelper" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@include file="../../../../../include/public_server.jsp"%>
<%!
	private void pushItem(Document document,ArrayList items) throws WCMException{
		String sTitle = document.getTitle();
		String sPageHttpURL = null;
		IPublishContent content = (IPublishContent)PublishElementFactory.makeElementFrom(document);
		if (content.isLink()) {
			sPageHttpURL = content.getLinkUrl();
		} else {
			PublishPathCompass compass = new PublishPathCompass();
			sPageHttpURL = compass.getHttpUrl(content, 0);
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
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	String sDocumentIds = currRequestHelper.getString("DocumentIds");
	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	ArrayList arrayList = new ArrayList();
	Document document = null;  
	//获得currDocument对象
	if(nDocumentId > 0){
		document  = Document.findById(nDocumentId);
		if(document != null){
			pushItem(document,arrayList);
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
							pushItem(document,arrayList);
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