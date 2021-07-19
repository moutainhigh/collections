<%@ page import="com.trs.components.wcm.individuation.*" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="java.util.ArrayList" %>
<% 
	//修改编码
	String customizeLink = ConfigServer.getServer().getSysConfigValue("FIRST_URL","../website/website_thumb.html");
	IndividuationMgr mgr = new IndividuationMgr();
	ArrayList operators = new ArrayList();
	boolean bFromEditor = request.getParameter("FromEditor")!=null;
	if(bFromEditor == false) {
		bFromEditor =	(request.getRequestURI().indexOf("document/document_addedit.jsp")!=-1);
	}
%>
<script language="javascript">
<!--
	var m_CustomizeInfo = {
		documentAutoSave : 1,
		documentAutoPaste : 1,
		documentCursorIntelligent :0,
		documentAsTitleLength : 30,
		documentTabAs : 4,
		documentEnterAs : 'p',
		msgNotifyPublish : 1,
		msgNotifyCommunication : 1,
		msgNotifyWorkFlow : 1,
		msgNotifyTimeSpan : 10,
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
		defaultChannelSheet : 'document',
		m_bClassicList : true
	};
<%
	// 获取系统级的个性化定制
	Individuations individuations4System = mgr.getIndividuations4System();
	Individuations individuations4User = mgr.getLoginUserIndividuationConfigs(new String[]{"customSite", "customChannel"}, false);
	// 如果同时做了系统级的个性化定制和用户个性化定制，则以用户的个性化定制为准；否则，以系统级的个性化定制为准
	individuations4System.addElements(individuations4User);
	Individuations individuations = individuations4System;
	for (int i = 0, nSize = individuations.size(); i < nSize; i++) {
		Individuation element = (Individuation) individuations.getAt(i);
		if (element == null)
			continue;
		if(element.getParamName().equals("defaultSystemSheet") && 
				!element.getParamValue().equals("website") && 
					loginUser.isAdministrator()){
			if(element.getParamValue().equals("contentextfield")){
				customizeLink = "../contentextfield/contentextfield_list.html";
			}else if(element.getParamValue().equals("flow")){
				customizeLink = "../flow/flow_list.html";
			}
		}else if(element.getParamName().toLowerCase().equals("operator")){
			String displayNumber = element.getObjectIdsValue().split(";")[0].split(":")[1];
			operators.add(element.getParamValue() + "-" + element.getObjectIdsValue());
			continue;
		}
		boolean bSystem = false;
		if(element.getUserId() == Individuation.USERID_FOR_SYSTEM){
			bSystem = true;
		}
%>	m_CustomizeInfo["<%=element.getParamName()%>"] = {
		objectId	: "<%=element.getId()%>",
		paramValue	: "<%=element.getParamValue()%>",
		isSystem : "<%=bSystem ? 1 : 0%>"
	};
<%
	}
%>
	m_CustomizeInfo["operator"] = {
<%
	for (int i = 0, nSize = operators.size(); i < nSize; i++) {
		String[] operator = ((String)operators.get(i)).split("-");
%>		
		<%=operator[0]%> : "<%=operator[1]%>"<%=((i+1) < nSize) ? "," : ""%>
<%
	}
%>
	};
	for(var name in m_CustomizeInfo){
		if(name == "operator") continue;
		var info = m_CustomizeInfo[name];
		if(typeof info!='object'){
			info = m_CustomizeInfo[name] = {paramValue : info};
		}
		info.objectId = info.objectId || 0;
		info.paramName = name;
	}
	window.m_bClassicList = m_CustomizeInfo.ListTypeDefaultShow ? (m_CustomizeInfo.ListTypeDefaultShow.paramValue=="true" ? true : false ) : false;
	window.customizeLink ="<%=customizeLink%>";
	if(window.m_bClassicList){
		window.customizeLink = window.customizeLink.replace("_", "_classic_"); 
	}

//-->
</script>
<%	
	if(!bFromEditor){
		session.setAttribute("customSite", mgr.getObjectIdsHashtable(WCMTypes.OBJ_WEBSITE));
	}
%>