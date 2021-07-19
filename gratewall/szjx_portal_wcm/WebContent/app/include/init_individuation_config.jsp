<%@ page import="com.trs.components.wcm.individuation.*" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="java.util.ArrayList" %>
<%@include file="../include/public_server.jsp"%>


<% 
	String mainFirstLink = "website/website_thumbs_index.html";
	IndividuationMgr mgr = new IndividuationMgr();
	ArrayList operators = new ArrayList();  
	boolean bFromEditor = request.getParameter("FromEditor")!=null;
	if(bFromEditor == false) {
		bFromEditor =	(request.getRequestURI().indexOf("document/document_addedit.jsp")!=-1);
	}
%>
<script language="javascript">
<!--
	if(!window.PageContext){
		var PageContext = {
			params : {},
			individuationConfig : {}
		};
	}
	/*
	*个性化配置的默认值
	*/
	PageContext.individuationConfig = {
		documentAutoSave : {
			objectId	: 0,
			paramName	: 'documentAutoSave',
			paramValue	: 1
		},
		documentAutoPaste : {
			objectId	: 0,
			paramName	: 'documentAutoPaste',
			paramValue	: 1
		},
		documentCursorIntelligent : {
			objectId	: 0,
			paramName	: 'documentCursorIntelligent',
			paramValue	: 0
		},
		documentAsTitleLength : {
			objectId	: 0,
			paramName	: 'documentAsTitleLength',
			paramValue	: 30
		},
		documentTabAs : {
			objectId	: 0,
			paramName	: 'documentTabAs',
			paramValue	: 4
		},
		documentEnterAs : {
			objectId	: 0,
			paramName	: 'documentEnterAs',
			paramValue	: 'p'
		},
		msgNotifyPublish : {
			objectId	: 0,
			paramName	: 'msgNotifyPublish',
			paramValue	: 1
		},
		msgNotifyCommunication : {
			objectId	: 0,
			paramName	: 'msgNotifyCommunication',
			paramValue	: 1
		},
		msgNotifyWorkFlow : {
			objectId	: 0,
			paramName	: 'msgNotifyWorkFlow',
			paramValue	: 1
		},
		msgNotifyTimeSpan : {
			objectId	: 0,
			paramName	: 'msgNotifyTimeSpan',
			paramValue	: 10
		},
		listPageSize : {
			objectId	: 0,
			paramName	: 'listPageSize',
			paramValue	: 20
		},
		listFilterSize : {
			objectId	: 0,
			paramName	: 'listFilterSize',
			paramValue	: 6
		},
		sheetCount : {
			objectId	: 0,
			paramName	: 'sheetCount',
			paramValue	: 6
		},
		documentDefaultShow : {
			objectId	: 0,
			paramName	: 'documentDefaultShow',
			paramValue	: 'normal'
		},
		documentSynDefaultShow : {
			objectId	: 0,
			paramName	: 'documentSynDefaultShow',
			paramValue	: 'dis'
		},
		noSheetNoShowAgain : {
			objectId	: 0,
			paramName	: 'noSheetNoShowAgain',
			paramValue	: 0
		},
		background : {
			objectId	: 0,
			paramName	: 'background',
			paramValue	: 'background-6.jpg'
		},
		randomBackground : {
			objectId	: 0,
			paramName	: 'randomBackground',
			paramValue	: '1'
		},
		noSheetDealType : {
			objectId	: 0,
			paramName	: 'noSheetDealType',
			paramValue	: 2
		},
		defaultSystemSheet : {
			objectId	: 0,
			paramName	: 'defaultSystemSheet',
			paramValue	: 'website'
		},
		defaultSiteSheet : {
			objectId	: 0,
			paramName	: 'defaultSiteSheet',
			paramValue	: 'channel'
		},
		defaultChannelsheet : {
			objectId	: 0,
			paramName	: 'defaultChannelsheet',
			paramValue	: 'document'
		}
	};
	if(!Object.extend){
		Object.extend = function(destination, source) {
		  for (property in source) {
			destination[property] = source[property];
		  }
		  return destination;
		}
	}
	Object.extend(PageContext.individuationConfig, {
		<%
			Individuations individuations = mgr.getLoginUserIndividuationConfigs(new String[]{"customSite", "customChannel"}, false);
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
				}else if(element.getParamName().equals("operator")){
					String displayNumber = element.getObjectIdsValue().split(";")[0].split(":")[1];
					operators.add(element.getParamValue() + ";" + displayNumber);
					continue;
				}
		%>
				<%=element.getParamName()%> : {
					objectId	: "<%=element.getId()%>",
					paramName	: "<%=element.getParamName()%>",
					paramValue	: "<%=element.getParamValue()%>"
				},
		<%
			}
		%>
				operators : {
		<%
			for (int i = 0, nSize = operators.size(); i < nSize; i++) {
				String[] operator = ((String)operators.get(i)).split(";");
		%>
					<%=operator[0]%> : <%=operator[1]%><%=((i+1) < nSize) ? "," : ""%>
		<%
			}
		%>
				}
	});
//-->
</script>
<%	
	if(!bFromEditor){
		session.setAttribute("customSite", mgr.getObjectIdsHashtable(WCMTypes.OBJ_WEBSITE));
	}

	mainFirstLink += "?SiteType=0";
%>