<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>	
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.publish.WCMContentPublishConfig" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<%@ page import="com.trs.components.common.job.Schedules" %>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.components.wcm.publish.domain.job.WithDrawJobWorker" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.service.IWCMProcessService" %>
<%@ page import="com.trs.components.web.IFieldHTMLParser" %>
<%@ page import="com.trs.components.web.IPropertiesBuilder" %>
<%@ page import="com.trs.components.web.document.DocumentPropertiesBuilder" %>
<%@ page import="com.trs.components.web.document.DocumentFieldHTMLParser" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentShowFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="java.util.List" %>
<%@ page import="com.trs.components.wcm.resource.DocLevel"%>
<%@ page import="com.trs.components.wcm.resource.DocLevels"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="java.util.HashMap" %>
<%@include file="document_addedit_extendedfield.jsp"%>
<%@include file="../system/doclevel_locale.jsp"%>
<%
	String sBaseProps = currChannel.getPropertyAsString("BASEPROPS");
	String sOtherProps = currChannel.getPropertyAsString("OTHERPROPS");
	String sAdvanceProps = currChannel.getPropertyAsString("ADVANCEPROPS");
	String sNeededProps = currChannel.getPropertyAsString("NeededProps","");
	Boolean isInheritFormWebSite = false;
	if(CMyString.isEmpty(sBaseProps)&&CMyString.isEmpty(sAdvanceProps)&&CMyString.isEmpty(sOtherProps)){
		WebSite currWebSite = currChannel.getSite();
		sBaseProps = currWebSite.getPropertyAsString("BASEPROPS");
		sOtherProps = currWebSite.getPropertyAsString("OTHERPROPS");
		sAdvanceProps = currWebSite.getPropertyAsString("ADVANCEPROPS");
		sNeededProps = currWebSite.getPropertyAsString("NeededProps","");
		isInheritFormWebSite = true;
	}

	IFieldHTMLParser parser = DocumentFieldHTMLParser.getInstance();
	IPropertiesBuilder builder = new DocumentPropertiesBuilder(currDocument);
	//当前栏目的ID
	String channelId = currChannel.getPropertyAsString("CHANNELID");
	//当前文档ID
	String documentId = currDocument.getPropertyAsString("DOCID");
%>
<%@include file="handler/common.jsp"%>
<%@include file="handler/docquote.jsp"%>
<%@include file="handler/docchannel.jsp"%>
<%@include file="handler/pubconfig.jsp"%>
<%@include file="handler/docreltime.jsp"%>
<%@include file="handler/filter_4_attributes.jsp"%>
<%!
	private Documents getToppedDocs(User user,Channel channel) throws WCMException{
			WCMFilter filter = new WCMFilter("","DOCORDERPRI>=1 and CHNLID=? and DOCSTATUS>0 and DOCCHANNEL>0","DOCORDERPRI desc","RECID,DOCID");
			ChnlDocs chnldocs = new ChnlDocs(user);
			filter.addSearchValues(channel.getId());
			chnldocs.open(filter);
			Documents documents = new Documents(user);
			if(chnldocs.isEmpty()){
				return documents;
			}			
			StringBuffer buff = new StringBuffer(128);
			for(int i=0,size=chnldocs.size();i<size;i++){
				ChnlDoc chnldoc = (ChnlDoc)chnldocs.getAt(i);
				if(chnldoc == null) continue;
				buff.append(',').append(chnldoc.getDocId());
			}
			chnldocs.clear();
			if(buff.length() > 1){
				documents.setSelect("DOCID,DOCTITLE,DOCCHANNEL");
				documents.addElement(buff.substring(1));				
			}
			return documents;
	}
%>

<style type="text/css">
	
	.ext-ie7 #basicprops_body .attr_input_text, .ext-ie7 #channel_select{position:static;}
	.ext-ie7 #otherprops_body .attr_input_text{position:static;}
	.ext-ie7 #advancedprops_body .attr_input_text{position:static;}
</style>
<script language="javascript">
<!--
	var firstSettingItem;
//-->
</script>
<div class="props_column" id='props_setting' align="center">
	<!--<table cellspacing=0 cellpadding=2 border=0 width="100%">
	<tbody>
		<tr height="56" valign="bottom" align="center">
			<td _action="manageBasic">
				<div class="td_setting td_active2" id="td_basic_setting">
					<span class="setting_icon td_basic">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.basicset", "基本属性")%></span>
				</div>
			</td>
			<td _action="manageOther">
				<div class="td_setting">
					<span class="setting_icon td_other">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.otherset", "其他属性")%></span>
				</div>
			</td>
			<td _action="manageAdvanced">
				<div class="td_setting" id="td_advanced_setting">
					<span class="setting_icon td_pubset">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.pubset", "高级设置")%></span>
				</div>
			</td>
		</tr>
		<tr height="56" valign="bottom" align="center">
			<td _action="manageAttachment">
				<div class="td_setting">
					<span class="setting_icon td_attachment">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.appendix", "附件管理")%></span>
				</div>
			</td>
			<td _action="manageRelation">
				<div class="td_setting">
					<span class="setting_icon td_relations">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.related", "相关文档")%></span>
				</div>
			</td>
			<td _action="manageExtends">
				<div class="td_setting">
					<span class="setting_icon td_extends">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.extend", "扩展字段")%></span>
				</div>
			</td>
		</tr>
	</tbody>
	</table>
	-->
</div>
<%
	String sViewProps = currChannel.getPropertyAsString("VIEWPROPS");
	String sOriginalViewProps = "Basic,Other,Advanced,Attachment,Relation,Extends";
	//如果栏目为空默认采取站点的设置
	if(CMyString.isEmpty(sViewProps)){
		WebSite currWebSite = currChannel.getSite();
		sViewProps = currWebSite.getPropertyAsString("VIEWPROPS");
	}
	//如果站点仍为空则没有设置过，取默认全部显示
	if(CMyString.isEmpty(sViewProps)){
		sViewProps = sOriginalViewProps;
	}

	String[] arrTds = sViewProps.split(",");

	HashMap allHasTable = new HashMap();
	
	allHasTable.put("Basic","<td _action=\"manageBasic\"><div class=\"td_setting\" id=\"td_basic_setting\"><span class=\"setting_icon td_basic\">&nbsp;</span><span>基本属性</span></div></td>");
	
	allHasTable.put("Other","<td _action=\"manageOther\"><div class=\"td_setting\" id=\"td_other_setting\"><span class=\"setting_icon td_other\">&nbsp;</span><span>其他属性</span></div></td>");
	
	allHasTable.put("Advanced","<td _action=\"manageAdvanced\"><div class=\"td_setting\" id=\"td_advanced_setting\"><span class=\"setting_icon td_pubset\">&nbsp;</span><span>高级设置</span></div></td>");
	
	allHasTable.put("Attachment","<td _action=\"manageAttachment\"><div class=\"td_setting\"><span class=\"setting_icon td_attachment\">&nbsp;</span><span>附件管理</span></div></td>");
	
	allHasTable.put("Relation","<td _action=\"manageRelation\"><div class=\"td_setting\"><span class=\"setting_icon td_relations\">&nbsp;</span><span>相关文档</span></div></td>");
	
	allHasTable.put("Extends","<td _action=\"manageExtends\"><div class=\"td_setting\"  id=\"td_extends_setting\"><span class=\"setting_icon td_extends\">&nbsp;</span><span>扩展字段</span></div></td>");

	StringBuffer sbHtml = new StringBuffer();
	if(!"0".equals(sViewProps) && arrTds.length > 0){
		sbHtml.append("<table cellspacing=0 cellpadding=2 border=0 width=\"100%\"><tbody><tr height=\"56\" valign=\"bottom\" align=\"center\">");
		for( int index=0; index<arrTds.length; index++ ){
			sbHtml.append(allHasTable.get(arrTds[index]));
			if ((index+1) % 3 == 0 && (index+1)<arrTds.length){
				sbHtml.append("</tr><tr height=\"56\" valign=\"bottom\" align=\"center\">");
			}
		}
		sbHtml.append("</tr></tbody></table>");
	}
	//只有一个的时候,且这一个不是“相关文档”或“附件管理”，则不显示
	if(!"0".equals(sViewProps) && arrTds.length == 1 && "Basic,Other,Advanced,Extends".indexOf(arrTds[0])>-1){
		sbHtml.setLength(0);
	} 
	String sHtml = sbHtml.toString();
	if (CMyString.isEmpty(sHtml)){
		sHtml = "";
	}

	//是否显示基本属性等的逻辑，包括："Basic,Other,Advanced,Extends"
	/*
	*如果显示字段为空，则什么显示都不显示；如果有则显示第一个
	*/
	String sOriginalPropsVisible = "Basic,Other,Advanced,Extends";
	String[] allOriginalPropsVisible = sOriginalPropsVisible.split(",");
	HashMap allHasVisible = new HashMap();
	for( int num = 0; num < allOriginalPropsVisible.length; num++ ){
		allHasVisible.put(allOriginalPropsVisible[num],"none");
	}
	if(!CMyString.isEmpty(sViewProps)){
		for (int m = 0; m < arrTds.length; m++) {
			if(sOriginalPropsVisible.indexOf(arrTds[m])>-1) {
				allHasVisible.put(arrTds[m],"block");
%>
<script language="javascript">
				var tempItemId = "<%=arrTds[m]%>";
				firstSettingItem = "td_" + tempItemId.toLowerCase() + "_setting";
				Event.observe(window, 'load', function(){
					var tempDom = $(firstSettingItem);
					Element.addClassName(tempDom,"td_active2");
					//tempDom.click();
				});
</script>
<%
				break;
			}
		}
	}
%>
<script language="javascript">
	var m_sNeededProps = "<%=sNeededProps%>";
	//给innerHTML赋值时必须使用单引号，以区分'和"
	$("props_setting").innerHTML = '<%=CMyString.filterForJs(sHtml)%>';
	if($("props_setting").innerHTML == ''){
		$("props_setting").style.display = 'none';
	}
	//if ($("props_setting").innerHTML==null){
	//	$("props_setting").innerHTML ="";
	//}
</script>
<div class="props_column" id="basicprops" style="height:100%;display:<%=CMyString.filterForHTMLValue(allHasVisible.get("Basic").toString())%>">
	<div class="props_column_title" id="basicprops_title" WCMAnt:param="document_props.jsp.basicAttribute">基本属性</div>
	<div class="props_column_body" id="basicprops_body" style="height:68%;overflow-y:auto;width:100%">
	<%
		String[] confXmlFields = getXMLFieldsName().toUpperCase().split(",");
		//去除表名前缀的影响
		for(int k=0;k < confXmlFields.length; k++){
			if(confXmlFields[k].startsWith("WCMDOCUMENT.")){
				confXmlFields[k] = confXmlFields[k].substring("WCMDOCUMENT.".length());
			}	
			if(confXmlFields[k].startsWith("WCMCHNLDOC.")){
				confXmlFields[k] = confXmlFields[k].substring("WCMCHNLDOC.".length());
			}
		}
		if(CMyString.isEmpty(sBaseProps)&&CMyString.isEmpty(sAdvanceProps)&&CMyString.isEmpty(sOtherProps)){
			String sBasePropsno = "DOCCHANNEL,DOCPEOPLE,SUBDOCTITLE,DOCKEYWORDS,DOCABSTRACT,DOCSOURCENAME,DOCQUOTE";
			String[] aBaseProps = sBasePropsno.split(",");
			for (int i = 0; i < aBaseProps.length; i++){
				out.println(parser.parse(aBaseProps[i], builder));
			}
		}else{
			if(!CMyString.isEmpty(sBaseProps)){
				String[] aBaseProps = sBaseProps.split(",");				
				for (int i = 0; i < aBaseProps.length; i++){
					//如果是扩展字段的话
					if(!CMyString.isStringInArray(aBaseProps[i].toUpperCase(),confXmlFields,false)){
		%>
				<%=showSingleExtendField(aBaseProps[i],currDocument,nChannelId,strDBName,false)%>
		<%
					}else{
						out.println(parser.parse(aBaseProps[i], builder));
					}
				}
			}
		}	
	%>
	</div>
</div>
<div class="props_column" id="otherprops" style="display:<%=CMyString.filterForHTMLValue(allHasVisible.get("Other").toString())%>;height:100%">
	<div class="props_column_title" id="otherprops_title" WCMAnt:param="document_props.jsp.otherAttribute">其他属性</div>
	<div class="props_column_body" id="otherprops_body" style="height:69%;overflow-y:auto;width:100%;">
	<%
		if(CMyString.isEmpty(sOtherProps)&&CMyString.isEmpty(sBaseProps)&&CMyString.isEmpty(sAdvanceProps)){
			String sOtherPropsno = "DOCAUTHOR,DOCRELTIME,REFTOCHANNEL";
			String[] aOtherProps = sOtherPropsno.split(",");
			for (int i = 0; i < aOtherProps.length; i++){
				if(aOtherProps[i].toLowerCase().equals("reftochannel"))continue;
				out.println(parser.parse(aOtherProps[i], builder));
			}
		}else{
			if(!CMyString.isEmpty(sOtherProps)){
				String[] aOtherProps = sOtherProps.split(",");
				for (int m = 0; m < aOtherProps.length; m++){
					if(aOtherProps[m].toLowerCase().equals("reftochannel"))continue;
					//如果是扩展字段的话
					if(!CMyString.isStringInArray(aOtherProps[m].toUpperCase(),confXmlFields,false)){
		%>
				<%=showSingleExtendField(aOtherProps[m],currDocument,nChannelId,strDBName,false)%>
		<%
					}else{
					out.println(parser.parse(aOtherProps[m], builder));
					}
				}
			}
		}	
	%>
	<%
		// 文档密级 add by ffx @2010-12-20
		DocLevel currDocLevel = currDocument.getDocLevel();
		int nCurrLevelId = currDocLevel.getId();
		String sLevelName = currDocLevel.getLName();
		processor = new JSPRequestProcessor(request, response);
		String sServiceId = "wcm61_doclevel", sMethodName = "query";
		DocLevels docLevels = (DocLevels)processor.excute(sServiceId, sMethodName);
		
	%>
		<div class="attr_row" style="margin-top:5px;display:none;">
			<span class="attr_name" WCMAnt:param="document_addedit_props.jsp.doclevel">文档密级:</span>
			<select name="DOCLEVEL" id="DOCLEVEL"  class="attr_input_text" style="width:142px;height:20px;">
				<%
					for (int i = 0, nSize = docLevels.size(); i < nSize; i++) {
						DocLevel level = (DocLevel) docLevels.getAt(i);
						if (level == null)
							continue;
						int nDocLevelId = level.getId();
				%>
					<option value="<%=nDocLevelId%>" <%=(nDocLevelId == nCurrLevelId)?"selected selected=true" :""%> ><%=CMyString.transDisplay(getDocLevelLocale(level.getLName()))%>
				<%}%>
			</select>
		</div>
	</div>
</div>
<div class="props_column" id="advancedprops" style="display:<%=CMyString.filterForHTMLValue(allHasVisible.get("Advanced").toString())%>;height:100%">
	<div class="props_column_title" id="advancedprops_title" WCMAnt:param="document_props.jsp.upperSet">高级设置</div>
	<div class="props_column_body" id="advancedprops_body" style="height:69%;overflow-y:auto;">
		<%
			//置顶信息
			boolean bIsCanTop = false;//是否在当前栏目有置顶权限
			//有修改文档的权限时才可做置顶设置
			bIsCanTop = DocumentAuthServer.hasRight(loginUser, currChannel, currDocument, WCMRightTypes.DOC_POSITIONSET);
			boolean bTopped = false;//是否置顶
			boolean bTopForever = false;//是否永久置顶
			CMyDateTime dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
			if(nDocumentId>0){
				ChnlDoc topChnlDoc = ChnlDoc.findByDocAndChnl(currDocument,currChannel);
				dtTopInvalidTime = topChnlDoc.getInvalidTime();
				if(dtTopInvalidTime != null && dtTopInvalidTime.isNull()){
					dtTopInvalidTime = null;
				}
				bTopped = (topChnlDoc.getPropertyAsInt("DOCORDERPRI", 0) > 0);
				//dtTopInvalidTime = currDocumentService.getTopTime(currDocument, currChannel);
				//bTopped = currDocumentService.isDocumentTopped(currDocument, currChannel);
				if(bTopped && dtTopInvalidTime == null)	bTopForever = true;
				if(dtTopInvalidTime == null){
					dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
				}
			}
			String sTopInvalidTime = dtTopInvalidTime.toString("yyyy-MM-dd HH:mm");
			Documents toppedDocuments = null;
			if(bIsCanTop && currChannel != null) {
				toppedDocuments = getToppedDocs(loginUser,currChannel);
			}
		%>
		<!--<div style="display:<%=bIsCanTop?"":"none"%>;">-->
		<div style="display:none">
			<div class="attr_row" style="height:24px;line-height:24px;">
				<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.topSet">置顶设置:</span>
			</div>
			<div class="attr_row" style="padding-left:2px;overflow:visible;height:100%;">
				<div class="topset_row" _action="topset">
					<input type="radio" id="pri_set_0" name="TopFlag" value="0">
					<label for="pri_set_0" WCMAnt:param="document_props.jsp.noSet">不置顶</label>
				</div>
				<div class="topset_row" _action="topset">
					<input type="radio" id="pri_set_2" name="TopFlag" value="2">
					<label for="pri_set_2" WCMAnt:param="document_props.jsp.topForEver">永久置顶</label>
				</div>
				<div class="topset_row">
					<div _action="topset">
						<input type="radio" id="pri_set_1" name="TopFlag" value="1">
						<label for="pri_set_1" WCMAnt:param="document_props.jsp.topTimeVal">限时置顶</label>
					</div>
					<div id="pri_set_deadline" style="display:<%=(!bTopped || bTopForever)?"none":""%>">
						<span style="padding-left:15px;">
							<input type="text" name="TopInvalidTime" id="TopInvalidTime" elname="限时置顶" value="<%=sTopInvalidTime%>" class="calendarText" WCMAnt:paramattr="elname:document_addedit_props.jsp.topintime"/>
							<button type="button" class="DTImg" id="btnTopInvalidTime"><img src="../images/icon/TRSCalendar.gif" border=0 alt="" class="img"></button>
						</span>
					</div>
				</div>
				<div class="attr_row" style="padding-left:2px;overflow:visible;height:100%;display:'';" id="display_padding">&nbsp;</div>
			</div>
			<div class="attr_row" id="topset_order" style="display:<%=(!bTopped)?"none":""%>">
				<div class="attr_row" style="height:24px;line-height:24px;">
					<span class="attr_name" style="width:180px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.topOrder">置顶排序:</span>
				</div>
				<div class="attr_row" style="overflow:visible;height:100%;">
					<div class="attr_table" id="topset_order_table" style="width:100%;">
						<table border=0 cellspacing=1 cellpadding=0 style="table-layout:fixed;background:gray;" class="extrict">
						<thead>
							<tr bgcolor="#CCCCCC" align=center valign=middle>
								<td width="32" WCMAnt:param="document_props.jsp.order">序号</td>
								<td WCMAnt:param="document_props.jsp.docTitle">文档标题</td>
								<td width="40" WCMAnt:param="document_props.jsp.listOrder">排序</td>
							</tr>
						</thead>
						<tbody id="topset_order_tbody">
						<%
							if(toppedDocuments==null||toppedDocuments.size()==0){
						%>
							<tr bgcolor="#FFFFFF" align=center valign=middle>
								<td>&nbsp;</td>
								<td align=left>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						<%
							}else{
								for(int i=0, n=toppedDocuments.size(); i<n; i++){
									Document topDoc = (Document)toppedDocuments.getAt(i);
									if(topDoc==null)continue;
									int nTopDocId = topDoc.getId();
									String sDocTitle = CMyString.truncateStr(topDoc.getTitle(), 55, "...");
									String sDocTitle2 = PageViewUtil.toHtmlValue(topDoc.getTitle());
									String sDocTitle3 = PageViewUtil.toHtmlValue(sDocTitle);
									if(nTopDocId!=nDocumentId){
						%>
							<tr bgcolor="#FFFFFF" align=center valign=middle _docid="<%=nTopDocId%>" _doctitle="<%=sDocTitle3%>">
								<td><%=i+1%></td>
								<td align=left title="<%=nTopDocId%>-<%=sDocTitle2%>"><div style="overflow:hidden"><%=CMyString.filterForHTMLValue(sDocTitle)%></div></td>
								<td>&nbsp;</td>
							</tr>
						<%
										continue;
									}//end if
						%>
							<tr bgcolor="#FFFFCF" align=center valign=middle _currdoc="1">
								<td><%=i+1%></td>
								<td align=left style="color:red;" WCMAnt:param="document_props.jsp.currDocument">--当前文档--</td>
								<td>
									<span class="topset_up" title="上移" _action="topsetUp" WCMAnt:paramattr="title:document_props.jsp.upper">&nbsp;</span>
									<span class="topset_down" title="下移" _action="topsetDown" WCMAnt:paramattr="title:document_props.jsp.lower">&nbsp;</span>
								</td>
							</tr>
						<%
								}//end for
							}
						%>
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%	//发布的输出
			if(CMyString.isEmpty(sAdvanceProps)&&CMyString.isEmpty(sBaseProps)&&CMyString.isEmpty(sOtherProps)){
				out.println(parser.parse("PUBCONFIG", builder));
			}else{
				if(!CMyString.isEmpty(sAdvanceProps)){
					String[] aAdvanceProps = sAdvanceProps.split(",");
					for (int n = 0; n < aAdvanceProps.length; n++){
						if(aAdvanceProps[n].toLowerCase().equals("orderpri"))continue;
						//如果是扩展字段的话
						if(!CMyString.isStringInArray(aAdvanceProps[n].toUpperCase(),confXmlFields,false)){
			%>
					<%=showSingleExtendField(aAdvanceProps[n],currDocument,nChannelId,strDBName,false)%>
			<%
						}else{
							out.println(parser.parse(aAdvanceProps[n], builder));
						}
					}
				}
			}
		%>
		<%
			int nFlowId = 0;
			IWCMProcessService processService = ServiceHelper.createWCMProcessService();
			Flow flow = processService.getFlowOfEmployer(currChannel);
			if(flow != null) {
				nFlowId = flow.getId();
			}
			// 编辑草稿状态的文档，也可以指定流转参数信息
			if(nDocumentId == 0 || (nDocumentId > 0 && nCurrDocStatus == Status.STATUS_ID_DRAFT)){
				String sFlowSetting = ConfigServer.getServer().getSysConfigValue(
							"DOC_ADD_FLOW_SETTING", "0");
				if(sFlowSetting != null && sFlowSetting.equals("1") && nFlowId>0) {
		%>
		<div class="attr_row" style="height:24px;line-height:24px;">
			<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.flowinfo">流转信息:</span>
		</div>
		<div style="height:45%;" id="frmFlowDocContainer">
			<iframe id="frmFlowDoc"  src="../flowdoc/workflow_process_init_for_doc.jsp?FlowId=<%=nFlowId%>" height="100%" scrolling="auto" frameborder="0"></iframe>
		</div>
		<%
			}
		}else if(nFlowDocId > 0 && nWorklistViewType == 1){
		%>
		<div class="attr_row" style="height:24px;line-height:24px;">
			<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.flowinfo">流转信息:</span>
		</div>
		<div style="height:100%;" id="frmFlowDocContainer">
			<iframe id="frmFlowDoc" src="../flowdoc/workflow_process_render_for_doc.jsp?FlowDocId=<%=nFlowDocId%>" height="100%" scrolling="auto" frameborder="0" ></iframe>
		</div>
		<%
			}
		%>
	</div>
</div>

<div class="props_column" id="extendprops" style="display:<%=CMyString.filterForHTMLValue(allHasVisible.get("Extends").toString())%>;height:100%">
	<div class="props_column_title" id="extendprops_title" WCMAnt:param="document_props.jsp.extendFieldManage">扩展字段管理</div>
	<div class="props_column_body" id="extendprops_body" style="width:100%;height:69%;overflow:auto">
	<%=showExtendFields(currExtendedFields,currDocument,strDBName,false,currChannel,isInheritFormWebSite)%>
	</div>
	<input type="hidden" id="_channelId"/>
	<input type="hidden" id="_documentId"/>
</div>
<script language="javascript">
<!--
	/*
	*添加引用类型的样式控制 by@sj-2012/4/25
	*/
	onloadQuoteTypeStyle();
	function onloadQuoteTypeStyle(){
		var els = document.getElementsByClassName("_quoteTypeImg");
		for (var i=0; i<els.length; i++) {
			var quoteTypee = document.getElementById('quote_'+(i+1));
			var value = quoteTypee.getAttribute("_quoteType");
			var el = els[i];
			Element.removeClassName(el,"quote");
			Element.removeClassName(el,"mirror");
			Element.addClassName(el,value);
			document.getElementById('quote_'+(i+1)).value = value;
		}
	}
	function _onchangeQuoteTypeStyle(){
		var els = document.getElementsByClassName("_quoteTypeImg");
		for (var i=0; i<els.length; i++) {
			var quoteTypee = document.getElementById('quote_'+(i+1));
			var value = quoteTypee.value;
			var el = els[i];
			Element.removeClassName(el,"quote");
			Element.removeClassName(el,"mirror");
			Element.addClassName(el,value);
		}
	}
	Element["<%=bCKMExtract?"show":"hide"%>"]('ckmAuto');

	Event.observe(window, 'load', function(){
		if(!$('DOCKEYWORDS'))return;
		var sg1 = new wcm.Suggestion();
		var sInputValue = "";
		sg1.init({
			el : 'DOCKEYWORDS',
			autoComplete : false,
			requestOnFocus : false,
			execute : function(){
				//TODO override the method.
				if(sInputValue != ""&&sInputValue.charAt(sInputValue.length-1)!=";"){
					sInputValue += ";";
				}
				var sNewInputValue = this.getListValue();
				var inputValueArr = [];
				var oldInputValueArr = sInputValue.split(/[\s　,,;; . ,]/g );
				var newInputValueArr = sNewInputValue.split(/[\s　,,;; . ,]/g );
				for(var z=0;z<newInputValueArr.length;z++){
					if(!oldInputValueArr.include(newInputValueArr[z]))inputValueArr.push(newInputValueArr[z]);
				}
				sNewInputValue = inputValueArr.join(";")
				sInputValue += sNewInputValue;
				this.setInputValue(sInputValue);
				//alert(this.getInputValue());
			},
			request : function(sValue){			
				var items = [];
				var nLen = sValue.length;
				var arr = ["," , " " , "," , ";" , ";", ",", "."];
				if(sValue=="")sInputValue="";
				var arr1 = sValue.split(/[\s　,,;; . ,]/g );
				var arr2 = sInputValue.split(";");
				var arr3 = [];
				for(var m=0;m<arr2.length;m++){
					if(arr1.include(arr2[m]))arr3.push(arr2[m]);
				}
				if(arr.include(sValue.charAt(nLen-1))&&(!arr2.include(arr1[arr1.length-1])))arr3.push(arr1[arr1.length-1]);
				sInputValue = arr3.join(";");
				if(arr.include(sValue.charAt(nLen-1)))return;
				for (var i = 0; i < nLen; i++){
					var sChar = sValue.charAt(nLen-i-1);
					if(arr.include(sChar)){
						sValue = sValue.slice(nLen-i,nLen);
						break;
					}
					continue;
				}
				var oPostData = {
					siteType : <%=currDocument.getChannel().getSite().getType()%>,
					siteId : <%=currDocument.getChannel().getSiteId()%>,
					kname : sValue
				}
				BasicDataHelper.JspRequest('../keyword/keyword_create.jsp',oPostData,false,function(_trans,_json){
					var json = com.trs.util.JSON.eval(_trans.responseText.trim());
					for(var i=0;i<json.length;i++){
						items.push(json[i]);
					}
					sg1.setItems(items);
				}.bind(this));
			}
		});

		var $channel = document.getElementById("_channelId");
		var $documentId = document.getElementById("_documentId");
		$channel.value = "<%=channelId%>";
		$documentId.value = "<%=documentId%>";
		var $fieldObj = document.getElementById("PUBLISHFILENAME");
		if($fieldObj){
			//modified by hp @2013-4-28 改为在保存的时候做校验
			//$fieldObj.onblur = checkFiledVal;
		}	
	});

//-->
</script>