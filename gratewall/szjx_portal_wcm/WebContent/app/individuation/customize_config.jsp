<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.individuation.*" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="java.util.ArrayList" %>
<%@include file="../include/public_server.jsp"%>
<% 
	String mainFirstLink = "website/website_thumbs_index.html";
	IndividuationMgr mgr = new IndividuationMgr();
	boolean bFromEditor = request.getParameter("FromEditor")!=null;
	if(bFromEditor == false) {
		bFromEditor =	(request.getRequestURI().indexOf("document/document_addedit.jsp")!=-1);
	}
	out.clear();
%>
	var m_CustomizeInfo = {
		documentAutoSave : 1,
		documentAutoPaste : 1,
		documentCursorIntelligent :0,
		documentAsTitleLength : 30,
		documentTabAs : 4,
		documentEnterAs : 'p',
		listPageSize : 20,
		listFilterSize : 6,
		sheetCount : 6,
		documentDefaultShow : 0,
		documentSynDefaultShow : 'dis',
		noSheetNoShowAgain : 0,
		background : 'background-6.jpg',
		randomBackground : '1',
		noSheetDealType : 2,
		defaultSystemSheet : 'website',
		defaultSiteSheet : 'channel',
		defaultChannelsheet : 'document'
	};
<%
	// ��ȡϵͳ���ĸ��Ի�����
	Individuations individuations4System = mgr.getIndividuations4System();
	Individuations individuations4User = mgr.getLoginUserIndividuationConfigs(new String[]{"customSite", "customChannel"}, false);
	// ���ͬʱ����ϵͳ���ĸ��Ի����ƺ��û����Ի����ƣ������û��ĸ��Ի�����Ϊ׼��������ϵͳ���ĸ��Ի�����Ϊ׼
	individuations4System.addElements(individuations4User);
	Individuations individuations = individuations4System;
	for (int i = 0, nSize = individuations.size(); i < nSize; i++) {
		Individuation element = (Individuation) individuations.getAt(i);
		if (element == null)
			continue;
		if(element.getParamName().equals("defaultSystemSheet") && 
				!element.getParamValue().equals("website") && 
					loginUser.isAdministrator()){
			if(element.getParamValue().equals("extendfield")){
				mainFirstLink = "extendfield/extendfield_index.html";
			}else if(element.getParamValue().equals("workflow")){
				mainFirstLink = "workflow/workflow_list.html";
			}
		}
%>	m_CustomizeInfo["<%=element.getParamName()%>"] = {
		objectId	: "<%=element.getId()%>",
		paramValue	: "<%=element.getParamValue()%>"
	};
<%
	}
%>
	for(var name in m_CustomizeInfo){
		var info = m_CustomizeInfo[name];
		if(typeof info!='object'){
			info = m_CustomizeInfo[name] = {paramValue : info};
		}
		info.objectId = info.objectId || 0;
		info.paramName = name;
	}
<%	
	if(!bFromEditor){
		session.setAttribute("customSite", mgr.getObjectIdsHashtable(WCMTypes.OBJ_WEBSITE));
	}
	mainFirstLink += "?SiteType=0";
%>