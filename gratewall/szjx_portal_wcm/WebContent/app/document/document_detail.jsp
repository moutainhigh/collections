<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.ajaxservice.StatusServiceProvider" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Relations" %>
<%@ page import="com.trs.components.wcm.content.persistent.Relation" %>
<%@ page import="com.trs.ajaxservice.ContentExtendValue" %>
<%@ page import="com.trs.ajaxservice.ContentExtendValues" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.content.HTMLContent" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.cms.process.engine.ContentProcessInfo" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.components.wcm.content.persistent.Documents"%>
<%@ page import="com.trs.infra.support.file.FilesMan,com.trs.infra.util.CMyFile"%>

<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement"%>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder"%>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory"%>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig"%>

<%@include file="../include/public_processor.jsp"%>
<%@include file="../system/status_locale.jsp"%>


<%
	//如果是从查看文档中查看相关文档,则无法编辑
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();
	int nCanEdit = currRequestHelper.getInt("canEdit", 0);
	int nCurrStatusId = 0;
	//String scurrDisp = null;
	Relations oRelations = null;
	ContentExtendValues oContentExtendValues = null;
	Appendixes oImageAppendixes = null;
	Appendixes oDocAppendixes = null;
	Appendixes oLinkAppendixes = null;
	String sPubTime = null;

	int nIsFormRelation = processor.getParam("isFormRelation", 0);
	nIsFormRelation = nIsFormRelation + 1;
	//processor.setSelectParameters(new String[]{"objectId"});
	//oDocument = (Document)processor.excute("document", "findbyid");
	processor.setSelectParameters(new String[]{"DocumentId","ChnlDocId","FlowDocId"});
	oRelations = (Relations)processor.excute("viewdocument", "queryRelations");
	processor.reset();
	oContentExtendValues = (ContentExtendValues)processor.excute("viewdocument", "queryExtendFields");
	processor.reset();
	processor.setAppendParameters(new String[]{
		"AppendixType", "20"
	});
	oImageAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	processor.reset();
	processor.setAppendParameters(new String[]{
		"AppendixType", "10"
	});
	oDocAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	processor.reset();
	processor.setAppendParameters(new String[]{
		"AppendixType", "40"
	});
	oLinkAppendixes = (Appendixes)processor.excute("viewdocument", "queryAppendixes");
	processor.reset();
	processor.setAppendParameters(new String[]{"ContentType" , "605"});
	processor.setEscapeParameters(new String[]{"DocumentId", "ContentId"});
	ContentProcessInfo oContentProcessInfo = (ContentProcessInfo)processor.excute("process", "getProcessInfoOfContent");
	int nFlowId =  oContentProcessInfo.getContentFlowId();
	processor.reset();
	processor.setEscapeParameters(new String[]{"DocumentId", "ObjectId"});
	Document currDocument = (Document) processor.excute("document", "findbyid");
	if(currDocument.getPubTime() != null){
		sPubTime = CMyString.showNull(currDocument.getPubTime().toString());
	}
	processor.reset();
	processor.setEscapeParameters(new String[]{"ChnlDocId", "ObjectId"});
	//System.out.println(processor.getServiceParameters());
	ViewDocument currViewDocument = (ViewDocument) processor.excute("viewdocument", "findbyid");
	String scurrDisp = LocaleServer.getString("document_query.label.unknown", "未知");
	if(currViewDocument != null){
		nCurrStatusId = currViewDocument.getStatusId();
		if(currViewDocument.getStatus() != null){
			Status currStatus = currViewDocument.getStatus();
			scurrDisp = getStatusLocale(currStatus.getDisp());
		}
	}
	processor.reset();
	// 使用服务获取，不需要再单独写权限过滤的代码
	processor.setEscapeParameters(new String[]{"ChnlDocId", "ChnlDocIds"});
	Statuses currStatuses = (Statuses)processor.excute("status", "queryDocumentStatuses");

	String sRightValue = currViewDocument.getRightValue(loginUser).toString();out.clear();
	//通过关键字获取相关文档
	WCMFilter oRelationsFilter = new WCMFilter();
	oRelationsFilter.setMaxRowNumber(5);//最多只显示5条
	DocumentMgr oDocumentMgr = (DocumentMgr) DreamFactory.createObjectById("DocumentMgr");
	Documents documentsOfKeywords = oDocumentMgr.getRelatedDocuments(currDocument, Document.GET_RELATEDDOC_BY_KEYWORDS, oRelationsFilter);

	WebSite webSite = currDocument.getChannel().getSite();
	IPublishElement publishElement = PublishElementFactory.makeElementFrom(webSite);
	WCMFolderPublishConfig publishConfig = new WCMFolderPublishConfig((IPublishFolder) publishElement);
%><html>
<head>
<title WCMAnt:param="document_detail.jsp.title">TRS WCM 文档查看页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</title>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<link href="./document_detail_cn.css" rel="stylesheet" type="text/css" WCMAnt:locale="./document_detail_$locale$.css"/>
</head>
<body>		  
<div id="docDetail" style="width:100%;height:100%;">
<input type="hidden" name="" id="rightvalue" value="">
<input type="hidden" name="" id="statusInput" value="">
<table width="70%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="view_area" id="view_area">
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right" valign="top" bgcolor="F3F1F1">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="function_area" id="document_top_nav">
							<tr id="trOptions">
								<td align="right" valign="top" style="white-space: nowrap;height:70px;">
									<div style="position:relative;overflow:hidden;height:70px;">
								<%
									if(nFlowId>0){
								%>	
									<!-- 给工作流提供的iframe,需要给它传参数:文档id等 -->
									<iframe id="gunter_template" src="" style="position:absolute;left:0px;top:0px;width:100%;height:70px;" scrolling="no" frameborder="0" allowtransparency="true">
									</iframe>
								<%
									}
								%>
								<%
									if(nCanEdit != 1){
								%>
									<div id="cmdboxs" style="position:absolute;right:0px;top:0px;height:30px;display:none;">
									<span width="80"><span style="cursor:pointer;" _function="hide_comment" class="function doc_comment" id="comment" WCMAnt:param="document_detail.jsp.hideTip">隐藏注释</span></span>
									<span width="80" id="versionSave"><span style="cursor:pointer" _function="backup" class="func_btn version_save function" WCMAnt:param="document_detail.jsp.versionSave">版本保存</span></span>
									<span width="60" id="edit"> <span style="cursor:pointer" class="function edit"  _function="edit" WCMAnt:param="document_detail.jsp.edit">编辑</span></span>
								<%
									}
								%>
									<span style="text-align:left;white-space:nowrap;width:80;display:<%=(nCanEdit != 1 && nCurrStatusId != Status.STATUS_ID_DRAFT) ? "" : "none"%>" id="docstatus" title="点击改变文档状态" WCMAnt:paramattr="title:document_detail.jsp.editstatus" >
											<span class="select" _selectEl="status" _fieldName="status" _fieldValue="<%=nCurrStatusId%>" style="border:1px solid black;background:white;" id="oldsel"><%=scurrDisp%>&nbsp;</span>
											<span style="display:none" id="sel" >
												<select name='status' id='status' onchange="changeStatus(this.value)">
														<%
															int nSize = currStatuses.size();
															boolean bContainStatus = false;
															for(int i=0;i<nSize;i++){
																Status status = (Status)currStatuses.getAt(i);
																int nStatusId = status.getId();
																if(nCurrStatusId == nStatusId) bContainStatus = true;
																String sDisp = status.getDisp();
															
														%>
															<option value="<%=nStatusId%>" 
																<%=( (nStatusId == nCurrStatusId) ? "selected" : "" )%>
																><%=getStatusLocale(sDisp)%></option>
														<%
															}
															if(!bContainStatus){
														%>
															<option value="<%=nCurrStatusId%>" title="<%=getStatusLocale(scurrDisp)%>" selected>
																<%=scurrDisp%>
															</option>
														<%
															}
														%>
												</select>
											</span>	
									</span>
									<span width="60"> <span style="cursor:pointer" class="function close" _function="close" WCMAnt:param="document_detail.jsp.close">关闭</span></span>
									<span align="right" valign="middle" class="text">&nbsp;</span>
									</div>
									</div>

								</td>
							</tr>
							<tr>
								<td>
									<div id="divGunter" style="font-family: georgia; margin-top: 8px; color: gray; display: none; overflow: auto; width: 100%; border-top: 1px solid silver;"></div>
								</td>
							</tr>
						</table>
						<div id="document_head">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr>
							<td><div align="center" class="title" id="divDocTitle"><%=CMyString.showNull(currDocument.getTitle())%></div></td>
						  </tr>
						</table>
						<table width="100%" height="24" border="0" cellpadding="0" cellspacing="0">
						  <tr>
							<td><div align="center" class="info"><span class="info_name" WCMAnt:param="document_detail.jsp.channel">栏目:</span><%=(currDocument.getChannel()!=null)?CMyString.transDisplay(currDocument.getChannel().getDesc()):""%>  &nbsp;&nbsp;<span class="info_name" WCMAnt:param="document_detail.jsp.author">作者:</span><%=CMyString.transDisplay(CMyString.showNull(currDocument.getAuthorName()))%>  &nbsp;&nbsp;<span class="info_name" WCMAnt:param="document_detail.jsp.crtime">撰写时间:</span> <%=CMyString.transDisplay(CMyString.showNull(currDocument.getReleaseTime().toString()))%>  &nbsp;&nbsp; <span class="info_name" WCMAnt:param="document_detail.jsp.pubtime">发布时间:</span> <%=CMyString.transDisplay(sPubTime)%> </div></td>
						  </tr>
						</table>
						</div>
					</td>
				</tr>
			</table>
			<div id="document_body">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="content_outer table_fixed">
				<tr>
					  <td class="content_inner">
					  <%@include file="../swf/create_swfview.jsp"%>
					  <%
							//String sDocContent = currDocument.getPropertyAsString("DOCHTMLCON");
							String sHtmlCon = makeHTMLCon(currDocument);
							if(sHtmlCon.indexOf("<!--word1-->") >= 0){
					  %>
							<style>
								.content_outer{
									width:640px;
									overflow-x:hidden;
								}
							</style>
					  <%
							}else if(sHtmlCon.indexOf("<!--word2-->") >= 0){
					  %>
							<style>
								.content_outer{
									width:970px;
									overflow-x:hidden;
								}
							</style>
					  <%
							}
					  %>
					  <%=filterForTRS(sHtmlCon)%>
					</td>
				</tr>
				</table>
			</div>

			<div id="document_abstract">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="p_sep table_fixed">
				<tr>
				  <td class="p_title" WCMAnt:param="document_detail.jsp.docAbstract">文档摘要</td>
				</tr>
				<tr>
				  <td class="p_content">
					<table border=0 cellspacing=0 cellpadding=0>
						<tr><td class="abstract_content">
						<%=formatDocAbstract(currDocument.getPropertyAsString("DOCABSTRACT"))%>
						</td></tr>
					</table>
				  </td>
				</tr>
			  </table>
			</div>
			<div id="document_extendfields">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="p_sep table_fixed">
					<tr>
					  <td class="p_title" WCMAnt:param="document_detail.jsp.docExtent">扩展字段</td>
					</tr>
					<tr>
					  <td class="p_content">
						<table border=0 cellspacing=0 cellpadding=0>
							<tr><td class="p_content">
							<%
								for(int z=0;z<oContentExtendValues.size();z++){
									ContentExtendValue oContentExtendValue = (ContentExtendValue)oContentExtendValues.getAt(z);
									String sExtendName = oContentExtendValue.getPropertyAsString("name");
									String sExtendValue = oContentExtendValue.getPropertyAsString("value");
									String[] extendArr = sExtendValue.split("~");
									String sValue = extendArr[0];
									int nIndex = sExtendValue.lastIndexOf("~");
									if(nIndex >= 0){
										sValue = sExtendValue.substring(0, nIndex);
										if(extendArr.length >=2){
											extendArr[1] = sExtendValue.substring(nIndex+1);
										}
									}
									if(sValue.trim().equals("") || sValue.trim().equals("null")) continue;
									if(extendArr.length >=2 && extendArr[1].trim().equals("10")){
										if(sExtendValue.indexOf("\n") != -1){
											sValue = formatDocAbstract(sValue);
										}
							%>
										<tr class="extend_name"><td><%=CMyString.transDisplay(sExtendName)%>:&nbsp;</td><td></td></tr>
								<tr><td></td><td>
								<table border=0 cellspacing=0 cellpadding=0>
									<tr><td class="abstract_content">
									<%=sValue%>
									</td></tr>
								</table>
								</td></tr>
							<%
									}else if(sExtendValue.indexOf("\n") != -1){
										sValue = CMyString.transDisplay(sValue);
										sValue = formatDocAbstract(sValue);
							%>
								<tr class="extend_name"><td><%=CMyString.transDisplay(sExtendName)%>:&nbsp;</td><td></td></tr>
								<tr><td></td><td>
								<table border=0 cellspacing=0 cellpadding=0>
									<tr><td class="abstract_content">
									<%=sValue%>
									</td></tr>
								</table>
								</td></tr>
							<%
									}else{
										if(extendArr.length >=2 && extendArr[1].trim().equals("2")){
							%>
								<tr class="extend_name"><td><%=CMyString.transDisplay(sExtendName)%>:&nbsp;</td><td><input type="password" value="<%=CMyString.filterForHTMLValue(format(sValue))%>" style="border:0;" readonly></td></tr>
							<%
										}else{
							%>
								<tr class="extend_name"><td><%=CMyString.transDisplay(sExtendName)%>:&nbsp;</td><td><%=CMyString.transDisplay(format(sValue))%></td></tr>
							<%
										}
									}
								}
							%>
							</td>
							</tr>
						</table>
					   </td>
					</tr>
				  </table>
			</div>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="p_sep">
				<tr>
					<td colspan="2" class="p_title" WCMAnt:param="document_detail.jsp.appendix">附件</td>
				</tr>
				<tr>
					<td valign="top" class="p_content p_contentname" WCMAnt:param="document_detail.jsp.picAppendix">图片附件：</td>
					<td class="p_content">
						<div id="document_image_appendixes">
							<%
								for(int k=0;k<oImageAppendixes.size();k++){
									Appendix oImageAppendix = (Appendix)oImageAppendixes.getAt(k);
									String sAppfileURL = oImageAppendix.getPropertyAsString("APPFILE");
							%>
								<div style="height:90px;float:left;margin-right:5px;">
									<a href="/wcm/file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sAppfileURL)%>" target="_blank"><img src="<%=CMyString.filterForHTMLValue(mapRollImage(sAppfileURL))%>" border=0 alt="<%=CMyString.filterForHTMLValue(oImageAppendix.getAlt())%>" style="height:90px;" title="<%=CMyString.filterForHTMLValue(oImageAppendix.getAlt())%>"></a>
								</div>
							<%
								}
							%>
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top" class="p_content p_contentname" WCMAnt:param="document_detail.jsp.docAppendix">文档附件：</td>
					<td>
						<div id="document_doc_appendixes">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
							<tr>
								<td class="p_content">
								<ul class="ul_content">
							<%
								for(int m=0;m<oDocAppendixes.size();m++){
									Appendix oDocAppendix = (Appendix)oDocAppendixes.getAt(m);
							%>
									<li><a href="/wcm/file/read_file.jsp?FileName=<%=CMyString.filterForHTMLValue(CMyString.showNull(oDocAppendix.getFile()))%>" title="<%=CMyString.filterForHTMLValue(CMyString.showNull(oDocAppendix.getAlt()))%>" target="_blank" class="url"><%=CMyString.transDisplay(CMyString.showNull(oDocAppendix.getDesc()))%></a>
							<%
								}
							%>
								</ul>
								</td>
							</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top" class="p_content p_contentname" WCMAnt:param="document_detail.jsp.linkAppendix">链接附件：</td>
				<td>
					<div id="document_link_appendixes">
						<table width="100%" border="0" cellspacing="2" cellpadding="2">
						<tr>
							<td class="p_content">
							<ul class="ul_content">
						
						<%
							for(int n=0;n<oLinkAppendixes.size();n++){
								Appendix oLinkAppendix = (Appendix)oLinkAppendixes.getAt(n);

						%>
								<li><a href="<%=CMyString.filterForHTMLValue(
						/*
						*查看链接附件时，由于查看页面不具备发布页面的相对地址，所以相对路径不能指向实际页面
						*拼凑出绝对路径，供查看页面查看，[站点http+相对路径(原路径)]
						*/
						oLinkAppendix.getSrcFile().indexOf("http://")>-1 ? oLinkAppendix.getSrcFile() : publishConfig.getRootDomain() + oLinkAppendix.getSrcFile()
								
								)%>" title="<%=CMyString.filterForHTMLValue(oLinkAppendix.getAlt())%>" target="_blank" class="url"><%=CMyString.transDisplay(oLinkAppendix.getDesc())%></a>
						<%
							}
						%>
							</ul>
							</td>
						</tr>
						</table>
					</div>
				</td>
				</tr>
			</table>

			<div id="document_relations">
				<table width="95%" border="0" class="table_fixed" align="center" cellpadding="0" cellspacing="0">
					<tr>
					  <td class="p_title" WCMAnt:param="document_detail.jsp.relation">相关文档</td>
					</tr>
					<tr>
						<td class="p_content">
						<ul class="ul_content" style="margin-left:30px;">
						<%
							for(int j=0;j<oRelations.size();j++){
								Relation oRelation = (Relation)oRelations.getAt(j);
								if(oRelation == null)continue;
								int nDocId = oRelation.getRelDocId();
								Document oDocument = Document.findById(nDocId);
								if(oDocument==null)continue;
								int nChnlId = oDocument.getChannelId();
								String sRelTitle = oDocument.getTitle();
								ChnlDoc chnlDoc = ChnlDoc.findByDocAndChnl(nDocId, nChnlId);
								int nDocChnlId = chnlDoc.getChannelId();
								int nRecId = (chnlDoc!=null)?chnlDoc.getPropertyAsInt("RECID", 0):0;
								boolean bCanDetail = hasRight(loginUser,oDocument,34);
						%>
							<li><a href="document_show.jsp?isFormRelation=<%=nIsFormRelation%>&DocumentId=<%=nDocId%>&ObjectId=<%=nDocId%>&ChnlDocId=<%=nRecId%>&ChannelId=<%=nChnlId%>&DocChnlId=<%=nDocChnlId%>" onclick="if(!<%=bCanDetail%>)return false;return doRelation(this);" target="_blank" class="url"><%=CMyString.filterForHTMLValue(sRelTitle)%></a>
						<%
							}
						%>
						</ul>
						</td>
					</tr>
					<tr>
						<td class="p_content">
						<ul class="ul_content" style="margin-left:30px;">
						<%
							for(int j=0;j<documentsOfKeywords.size();j++){
								Document oDocument = (Document)documentsOfKeywords.getAt(j);
								if(oDocument==null)continue;
								int nDocId = oDocument.getId();
								int nChnlId = oDocument.getChannelId();
								String sRelTitle = oDocument.getTitle();
								ChnlDoc chnlDoc = ChnlDoc.findByDocAndChnl(nDocId, nChnlId);
								int nDocChnlId = chnlDoc.getChannelId();
								int nRecId = (chnlDoc!=null)?chnlDoc.getPropertyAsInt("RECID", 0):0;
								boolean bCanDetail = hasRight(loginUser,oDocument,34);
						%>
							<li><a href="document_show.jsp?isFormRelation=<%=nIsFormRelation%>&DocumentId=<%=nDocId%>&ObjectId=<%=nDocId%>&ChnlDocId=<%=nRecId%>&ChannelId=<%=nChnlId%>&DocChnlId=<%=nDocChnlId%>" onclick="if(!<%=bCanDetail%>)return false;return doRelation(this);" target="_blank" class="url"><%=CMyString.filterForHTMLValue(sRelTitle)%></a>
						<%
							}
						%>
						</ul>
						</td>
					</tr>
				</table>
			</div>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/list.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/data/locale/document.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>

<!--wcm-dialog end-->
<!-- CarshBoard Outer Start -->
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<!-- CarshBoard Outer End -->
<script src="./document_detail.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	$('statusInput').value = <%=nCurrStatusId%>;
	$('rightvalue').value = "<%=CMyString.filterForJs(sRightValue)%>";
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function doRelation(_aHref){
		var sUrl = _aHref.getAttribute('href',2);
		$openMaxWin(sUrl);
		return false;
	}
	function changeStatus(_statusId){
		BasicDataHelper.call('wcm6_viewdocument', 'changeStatus',
			{
				objectIds: getParameter('ChnlDocId'),
				statusId: _statusId,
				ExcludeTrashed: true
			}, 
			true,
			function(){
				var myDocument = CMSObj.createFrom({
					objType : WCMConstants.OBJ_TYPE_DOCUMENT,
					objId : getParameter("DocumentId")
				});
				myDocument.afteredit();
			}
		);
		return true;
	}
//-->
</SCRIPT>

<script language="javascript">
<!--
	var iRand = new Date().getTime()%5+1;
	document.getElementById('view_area').style.backgroundImage = 'url(../images/document/document_show/bg_tu'+iRand+'.gif)';
	if(<%=nFlowId%> > 0){
		var oPostData = "ctype=605"
						+"&cid="+ getParameter('DocumentId')
						+"&PageSize= -1";
		$('gunter_template').src= "../flowdoc/document_detail_show_tracing.jsp?"+oPostData;
	}
//-->
</script>
</body>
</html>
<%!

	private String filterForTRS(String _sContent){
		return _sContent.replaceAll(
                "(?is)<TRS_COMMENT([^>]*)>(.*?)</TRS_COMMENT>", "<SPAN class=\"fck_comment\" _trscomment=\"true\" contentEditable=\"false\" onresizestart=\"return false\"$1>$2</SPAN>").replaceAll("<trs_page_separator></trs_page_separator>",
				"<table border=0 cellspacing=0 cellpadding=0 width=\"100%\"><tbody><tr><td style=\"font-size:8px;line-height:8px;\" height=\"8\" align=\"center\" valign=\"middle\"><span style=\"width:40%;height:1px;overflow:hidden;background:gray;\"></span>"+LocaleServer.getString("document_detail_label_sep","分页符")
			+"<span style=\"width:40%;height:1px;overflow:hidden;background:gray;\"></span></td></tr></tbody></table>");
	}

	private String makeHTMLCon(Document _document) throws WCMException {
        switch (_document.getType()) {
        case Document.TYPE_HTML:
            return  _document.getHtmlContentWithImgFilter(
                    null, false);
        case Document.TYPE_NORMAL:
            return CMyString.transDisplay(_document
                    .getContent(), false) ;
        case Document.TYPE_FILE:
			String sDocfilename = _document.getPropertyAsString("DOCFILENAME");
			return "<a href=/wcm/file/read_file.jsp?FileName=" + sDocfilename + " target='_blank' style='color:black;'>" + sDocfilename + "</a>";
        case Document.TYPE_LINK:
			String sPubURL = _document.getPropertyAsString("DOCLINK");
			return "<a href=" + CMyString.filterForHTMLValue(sPubURL) + " target='_blank' style='color:black;'>" + CMyString.transDisplay(sPubURL) + "</a>";
        default:
            return LocaleServer.getString("document_detail.label.unknownDoc", "未知类型文档") 
			+ "[Type=" + _document.getType()
                    + "]";
        }
    }
	private String mapRollImage(String _fns) throws WCMException{
			return "/wcm/file/read_image.jsp?FileName=" + _fns;
	}
	private String formatDocAbstract(String _sAbstract) throws WCMException{
		if(_sAbstract == null) return "";
		return _sAbstract.replaceAll("\n", "<br>");
	}
	private String format(String _sAbstract) throws WCMException{
		if(_sAbstract == null) return "";
		return _sAbstract.replaceAll(",", ", ");
	}
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		if(_objCurrent instanceof ViewDocument){
			return ((ViewDocument)_objCurrent).hasRight(_currUser,_nRightIndex);
		}
		else if(_objCurrent instanceof Document){
			return DocumentAuthServer.hasRight(_currUser,((Document)_objCurrent).getChannel(),(Document)_objCurrent,_nRightIndex);
		}
		else if(_objCurrent instanceof ChnlDoc){
			return DocumentAuthServer.hasRight(_currUser,(ChnlDoc)_objCurrent,_nRightIndex);
		}
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
%>