<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Relations" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.AppendixToXML" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.RelationToXML" %>
<%@ page import="com.trs.components.wcm.content.domain.AppendixMgr" %>
<%@ page import="com.trs.components.wcm.content.domain.RelationMgr" %>
<%@ page import="com.trs.presentation.plugin.PluginConfig" %>
<%@ page import="com.trs.components.ckm.ICKMServer" %>
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.wcm.photo.Watermark"%>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.wcm.publish.WCMPubStatusConfig" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.process.definition.FlowEmployMgr" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%!boolean IS_DEBUG = false;%>
<!--- ????????????????????????????????????????????????????????????public_server.jsp??? --->
<%@include file="../include/public_processor.jsp"%>
<%
	boolean bIsReadOnly = processor.getParam("ReadOnly", false);
	boolean bEnablePicLib = PluginConfig.isStartPhoto();
	boolean bEnableFlashLib = PluginConfig.isStartVideo();
	ICKMServer currCKMServer = (ICKMServer)DreamFactory.createObjectById("ICKMServer");
	boolean bCKMSimSearch = currCKMServer.isEnableSimSearch();
	boolean bCKMExtract = currCKMServer.isEnableAutoExtract();
	boolean bCKMSpellCheck = currCKMServer.isEnableAutoCheck();
	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	//?????????(????????????)
	int nChannelId = processor.getParam("ChannelId", 0);
	int nDocumentId = processor.getParam("DocumentId", 0);
	int nFlowDocId = processor.getParam("FlowDocId", 0);
	int nWorklistViewType = processor.getParam("WorklistViewType", 0);
	processor.setEscapeParameters(new String[]{
		"DocumentId", "ObjectId"
	});
	processor.setSelectParameters(new String[]{"DocumentId","ObjectId","ChannelId","FromEditor","FlowDocId"});
	Document currDocument = null;
	ChnlDoc currChnlDoc = null;
	int nModal = 1;
	if(nDocumentId == 0){
		currDocument = Document.createNewInstance();
	}else{
		currDocument = (Document) processor.excute("document", "findbyid");
		if(currDocument==null){
			if(nDocumentId!=0){
				throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "?????????????????????{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
			}
		}
		currChnlDoc = ChnlDoc.findByDocAndChnl(nDocumentId,nChannelId);
		if(currChnlDoc == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "?????????????????????{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
		}
		nModal = currChnlDoc.getPropertyAsInt("MODAL",1);
	}
	nDocumentId = currDocument.getId();
	Channel currChannel = null;
	Channel docChannel = null;
	boolean isNewsOrPics = false;//??????????????????????????????????????????
	//??????currChannel??????
	if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "?????????????????????{1} [ID={0}]."), new String[]{String.valueOf(nChannelId),WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
		}
		if(currChannel.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,currChannel+LocaleServer.getString("document_addedit_label_5","????????????!????????????????????????."));
		}
		isNewsOrPics = (currChannel.getType() == Channel.TYPE_TOP_NEWS || currChannel.getType() == Channel.TYPE_TOP_PICS);
	}
	//??????docChannel??????
	if(!currDocument.isAddMode())
		docChannel = currDocument.getChannel();
	else{
		if(currChannel==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("document_addedit_label_6","?????????????????????????????????!"));
		}
		docChannel = currChannel;
	}
	//????????????????????????
	ContentExtFields currExtendedFields = null;
	IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	if(docChannel != null)
		currExtendedFields  = currChannelService.getExtFields(docChannel, null);
	String strDBName = DBManager.getDBManager().getDBType().getName();
	//????????????
	if(!bIsReadOnly && !currDocument.isAddMode() && !currDocument.canEdit(loginUser)){
			//??????????????????
			out.clear();
%>
			<script>
				if(confirm('<%=CMyString.format(LocaleServer.getString("object.locked.readonly", "?????????{0}??????,?????????????????????????"), new Object[]{currDocument.getLockerUser()})%>')){
					location.href = '?<%=CMyString.filterForJs(request.getQueryString())%>&ReadOnly=1';
				}else{
					document.write('');
					location.href = 'about:blank';
					window.open("","_self");//fix ie7
					window.close();
				}
			</script>
<%
			return;
	}
	if (currDocument.getStatusId() < 0){
		throw new WCMException(CMyString.format(LocaleServer.getString("document_addedit_label_8","{0} [Document-{1}]????????????????????????,?????????????????????????????????!"),new String[]{currDocument.getTitle(),String.valueOf(nDocumentId)}));
	}
	String sOfficeSid = processor.getParam("OfficeSid");
	String[] officeInfo = null;
	if(sOfficeSid!=null){
		officeInfo = (String[])session.getAttribute(sOfficeSid.trim());
	}
//6.????????????
	// ?????????????????????????????????
	if(officeInfo==null && docChannel != null){
		String sContentAddEditPage = CMyString.showNull(docChannel.getPropertyAsString("ContentAddEditPage"));
		if(!(sContentAddEditPage.equals("") 
			|| sContentAddEditPage.equals("../document/document_addedit.jsp") 
			|| sContentAddEditPage.startsWith("../document/document_addedit.jsp?"))){
			//??????????????????id???????????????????????????????????????DOCKIND????????????
			String sTarget = "";
			int nDocKind = currDocument.getKindId();
			if(nDocKind > 0){
				sTarget = "../application/"+nDocKind+"/metaviewdata_addedit.jsp?"+request.getQueryString();
			}else{
				if(sContentAddEditPage.indexOf("?")==-1){
					sTarget = sContentAddEditPage+"?"+request.getQueryString();
				}
				else{
					sTarget = sContentAddEditPage+"&"+request.getQueryString();
				}
			}
			//??????CRLF???????????????????????????
			sTarget = sTarget.replaceAll("(?i)%0d|%0a","");
			response.sendRedirect(sTarget);
			return;
		}
	}
	String sTitle = "";//????????????
	String sTitleColor = "";//??????????????????
	boolean bAttachPic = false;//????????????
	int nDocForm = 0;//???????????????????????????
	int nDocType = Document.DOC_TYPE_HTML;//?????????????????????HTML
	String sContent = "";//????????????
	String sDocLink = "";//?????????????????????
	String sDocFileName = "";//????????????????????????
	int nCurrDocStatus = 0;
	//??????????????????????????????????????????
	boolean bShowSaveDraftBtn = false;
	if(currDocument.isAddMode()){
		//???????????????????????????
		currDocument.setType(Document.TYPE_HTML);
		currDocument.setChannel(nChannelId);
		if(officeInfo!=null){
			sTitle = officeInfo[0];
			sContent = officeInfo[1];
		}
		bShowSaveDraftBtn = true;
	}else{//???????????????????????????
		//???????????????????????????
		sTitle = currDocument.getTitle();
		//???????????????????????????,?????????null
		sTitleColor = CMyString.showNull(currDocument.getTitleColor());
		//????????????
		bAttachPic = currDocument.isAttachPic();
		//????????????????????????????????????????????????
		nDocForm = currDocument.getPropertyAsInt("DocForm",0);
		if(nDocForm ==0) nDocForm =1;
		//???????????????????????????
		nDocType = currDocument.getType();
		//???????????????????????????
		switch(nDocType){
			case Document.DOC_TYPE_HTML:
				sContent = (nDocumentId>0)?currDocument.getHtmlContentWithImgFilter(
                    null, false):"";
				break;
			case Document.DOC_TYPE_NORMAL:
				sContent = (nDocumentId>0)?currDocument.getContent():"";
				break;
			case Document.DOC_TYPE_LINK:
				sDocLink = CMyString.showNull(currDocument.getPropertyAsString("DOCLINK"));
				break;
			case Document.DOC_TYPE_FILE:
				sDocFileName = CMyString.showNull(currDocument.getPropertyAsString("DOCFILENAME"));
				break;
		}
		nCurrDocStatus = currDocument.getStatusId();
		if(nCurrDocStatus == Status.STATUS_ID_DRAFT){
			bShowSaveDraftBtn = true;//?????????????????????????????????????????????????????????
		}
	}
	boolean bIsCanAdd = AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_ADD);
	boolean bIsCanPreview = DocumentAuthServer.hasRight(loginUser, currChannel, currDocument, WCMRightTypes.DOC_PREVIEW);
	boolean bIsCanPub = false;
	int nSiteId = processor.getParam("SiteId", 0);
	if(nSiteId == 0){
		if(currChannel != null){
			nSiteId = currChannel.getSiteId();
		}else if(docChannel != null){
			nSiteId = docChannel.getSiteId();
		}
	}
	//?????????????????????
	IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(docChannel);
	IPublishContent content =  PublishElementFactory.makeContentFrom(currDocument, folder);
	if (DocumentAuthServer.hasRight(loginUser, currChannel, currDocument,
                WCMRightTypes.DOC_PUBLISH)) {
		//=======?????????????????????????????????,?????????????????????????????????????????????????????????=====
		// 1. ???????????????????????????,??????????????????
        if (docChannel.isCanPub()) {
		// 2. ???????????????????????????????????????
            bIsCanPub = (content.getDetailTemplate() != null);
		// ??????????????????????????????????????????
			boolean bNeedStatusCheck = ConfigServer.getServer().getSysConfigValue(PublishConstants.DIRECTPUB_STATUS_CHECK, "false").trim().equalsIgnoreCase("true");
			if(bNeedStatusCheck){
				try {
					WCMPubStatusConfig statusConfig = WCMPubStatusConfig.findOf(
							WebSite.OBJ_TYPE, nSiteId);
					if (statusConfig == null) {
						bIsCanPub = false;
					} else {
						int nStatus = Status.STATUS_ID_NEW;
						if(currChnlDoc != null) nStatus = currChnlDoc.getStatusId();
						bIsCanPub = ("," + statusConfig.getStatusesCanDoPub() + ",")
								.indexOf("," + nStatus + ",") >= 0;
					}
				} catch (Exception e) {
					bIsCanPub = false;
				}
			}
        }
	}

	// ?????????????????????ID
	int nDrafrStatus = Status.STATUS_ID_DRAFT;
	out.clear();
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1);
	//prevents caching at the proxy server
	response.setDateHeader("max-age", 0);
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- /*wenyh@2011-07-14 force the IE9 use a IE8 document mode.*/ --%>
<meta http-equiv="X-UA-Compatible" content="IE=8">
<%if(nDocumentId>0){%>
<title WCMAnt:param="document_addedit.jsp.trswcmdocedit">TRS WCM ????????????</title>
<script>
document.title = document.title + ': <%=CMyString.filterForJs(currDocument.getTitle())%>';
</script>
<%}else{%>
<title WCMAnt:param="document_addedit.jsp.addtitle">TRS WCM ????????????</title>
<%}%>
<link rel="stylesheet" type="text/css" href="../js/source/wcmlib/components/processbar.css">
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<link rel="stylesheet" type="text/css"  href="../js/resource/widget.css">
<link rel="stylesheet" type="text/css"  href="../js/easyversion/resource/crashboard.css">
<link href="document_addedit.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" WCMAnt:locale="./document_addedit_$locale$.css" href="./document_addedit_cn.css" />
<link href="../../app/js/source/wcmlib/suggestion/resource/suggestion.css" rel="stylesheet" type="text/css" />
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/document.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../js/data/locale/ajax.js"></script>
<script src="../js/data/locale/template.js"></script>
<script src="../../app/js/source/wcmlib/suggestion/suggestion.js"></script>
<script language="javascript" src="../template/trsad_config.jsp" type="text/javascript"></script>
<script src="../individuation/customize_config.jsp"></script>
</head>
<body>
<script>
	var channelId = <%=nChannelId%>;
	initExtCss();
	Ext.ns('PgC', 'editorCfg');
	var bEnableAdInTrs = window.trsad_config && window.trsad_config['enable']==true;
	Ext.apply(editorCfg, {
		basePath : WCMConstants.WCM6_PATH + 'document/',
		enablePhotolib : <%=bEnablePicLib%>,
		enableFlashlib : <%=bEnableFlashLib%>,
		enableAdintrs : bEnableAdInTrs,
		enableCkmExtract : <%=bCKMExtract%>,
		enableCkmSimSearch : <%=bCKMSimSearch%>,
		enableCkmspellcheck : <%=bCKMSpellCheck%>,
		enableAutoSave : parseInt(m_CustomizeInfo.documentAutoSave.paramValue)==1,
		enableAutoPaste : parseInt(m_CustomizeInfo.documentAutoPaste.paramValue)==1,
		enterAs : m_CustomizeInfo.documentEnterAs.paramValue,
		tabSpaces : parseInt(m_CustomizeInfo.documentTabAs.paramValue),
		autoTitleLength : parseInt(m_CustomizeInfo.documentAsTitleLength.paramValue),
		editMode : <%=!bIsReadOnly%>,
		onAutoSave : function(){
			$('lbl_autosave').innerHTML =String.format("???????????????:{0}", new Date().format('yyyy-mm-dd HH:MM:ss'));
			Element.show('lbl_autosave');
			setTimeout("Element.hide('lbl_autosave')", 5000);
		}
	});
	var m_nDraftStatus = <%=nDrafrStatus%>;// ????????????
	var UserName = "<%=CMyString.filterForJs(loginUser.getName())%>";
	<%if(PluginConfig.isStartPhoto()){%>
	var m_arWatermarks = <%=makeWatermarksJson(loginUser)%>
	<%}%>
	//???????????????USE_PREDOC_DOCSOURCE???????????????????????????????????????????????????????????????  add by zhangxinjian 2011-09-14
	<% String sUsePreDocDocSource = ConfigServer.getServer().getSysConfigValue("USE_PREDOC_DOCSOURCE","true"); %>
	var use_predoc_docsource = "<%=CMyString.filterForJs(sUsePreDocDocSource)%>";
</script>
<div id="colorpicker" style="position:absolute;z-index:9999;display:none;"></div>
<form name="frmData" id="frmData" style="display:none">
	<input type="hidden" name="DocEditor" value="<%=loginUser.getName()%>"/>
	<input type="hidden" name="TitleColor" value="<%=CMyString.filterForHTMLValue(("".equals(sTitleColor)?"":sTitleColor))%>"/>
	<input type="hidden" name="DocContent" value=""/>
	<input type="hidden" name="DocHtmlCon" value=""/>
	<input type="hidden" name="DirectlyPublish" value="0"/>
	<input type="hidden" name="ObjectId" value="<%=nDocumentId%>"/>
	<input type="hidden" name="ChannelId" value="<%=(docChannel!=null)?docChannel.getId():0%>"/>
<%if(bShowSaveDraftBtn){%>
	<input type="hidden" name="DocStatus" value="<%=nCurrDocStatus%>"/> 
<%}%>
	<input type="hidden" name="Force2start" value="" />
</form>

<div name="frmAction" id="frmAction" style="margin:0;padding:0;">
	<div class="layout_container">
		<div class="layout_north">
			<div class="north_padding" style="height:3px;background:#DADADA;">&nbsp;</div>
			<div class="north_padding" style="height:1px;background:#BDBDBD;">&nbsp;</div>
			<div class="north_padding" style="height:1px;background:#FFFFFF;">&nbsp;</div>
			<div class="north_padding" style="height:5px;background:#DADADA;">&nbsp;</div>
			<div class="editor_header" id="editor_header">
				<div class="toolbar_label" WCMAnt:param="document_addedit.jsp.documentTitle">????????????</div>
				<div class="top_bar_shuru">
					<input id="DocTitle" name="DocTitle" class="input_text" type="text" size="70" value="<%=CMyString.filterForHTMLValue(sTitle)%>" pattern="string" required="true" max_len="200" min_len="1" elname="??????" WCMAnt:paramattr="elname:document_addedit.jsp.headLine"/>
					<div id="CountTitle" title="????????????/???????????????" WCMAnt:paramattr="title:document_addedit.jsp.maxSize"><span id="TitlePsn">0</span><span class="sep">/</span><span id="TitleCount">0</span></div>
					<div id="DocTitleHint" _action="openSimpleEditor" title="?????????????????????" WCMAnt:paramattr="title:document_addedit.jsp.openSimpleEditor"><span id="spDocTitleHint">&nbsp;</span></div>
				</div>
				<div class="title_color" _action="titlecolor">
				<div class="title_color1">&nbsp;</div>
				<div id="title_color" class="title_color2" style="background-color:<%=CMyString.filterForHTMLValue(("".equals(sTitleColor)?"":sTitleColor))%>;">&nbsp;</div>
				</div>
				<div class="document_option" style="display:none">
					<input type="checkbox" id="AttachPic" name="AttachPic" <%=bAttachPic?"checked":""%> class="input_checkbox" isBoolean="1"/>
					<span WCMAnt:param="document_addedit.jsp.graph">???</span>
				</div>
				<!--<div class="toolbar_sep"></div>-->
				<div class="document_option" style="display:none"><span WCMAnt:param="document_addedit.jsp.form">??????</span>
					<%=getDocFormSelector()%>
				</div>
				<div class="toolbar_sep"></div>
				<div class="document_option" id="skyType"><span WCMAnt:param="document_addedit.jsp.type">??????</span>
					<%=getDocTypeSelector()%>
				</div>
				<!--
				<div class="toolbar_sep"></div>
				<div class="attachment" _action="manageAttachment" WCMAnt:param="document_addedit.jsp.appendix">????????????</div>
				<div class="relative_docs" _action="manageRelation" WCMAnt:param="document_addedit.jsp.relation">????????????</div>
				-->

					<div class="attachment" _action="manageAttachment" WCMAnt:param="document_addedit.jsp.appendix">????????????</div>
				<div class="relative_docs" onclick="window.showModalDialog('http://aaicweb03/sysImage/index.aspx');"><strong>?????????</strong>
				??????????????????????????????????????????????????????????????????
				</div>
				
			</div>
		</div>
		<div class="layout_center_container">
			<div class="layout_center">
				<div id="nothtml_editor" class="editor_wrapper" style="display:<%=nDocType!=20?"":"none"%>">
					<div id="txt_editor" class="editor_body" style="display:<%=nDocType==10?"":"none"%>">
						<div class="doccontent">
							<textarea id="_editorValue_" name="DocContent"><%=PageViewUtil.toHtmlValue(sContent)%></textarea>
						</div>
					</div>
					<!--//???????????????-->
					<div id="link_editor" class="editor_body" style="display:<%=nDocType==30?"":"none"%>">						
						<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
							<tr style="height:100px"><td>&nbsp;</td></tr>
							<tr style="height:30px;" valign="top">
								<td align="center">
									<div id="link_editor2" style="border:1px solid #b7b7a6;width:650px;padding:20px;background:#FFFFFF;">
									<span style="float:left;line-height:30px;width:100px" WCMAnt:param="document_addedit.jsp.link">????????????:</span>
									<span style="float:left;margin:4px 0 0">
										<INPUT TYPE="text" id="DOCLINK" name="DOCLINK" style="width:218px" class="input_text" required="true" pattern="string" max_len="100" min_len="1" elname="????????????" value="<%=CMyString.filterForHTMLValue(sDocLink)%>" WCMAnt:paramattr="elname:document_addedit.jsp.linkAddress"/>
										<input type="button" class="input_text" style="margin-left:5px;width:130px;" value="??????????????????" WCMAnt:paramattr="value:document_addedit.jsp.addInnerDocLink" onclick="AddInnerDocLink();">
										<input type="button" class="input_text" style="margin-left:5px;width:125px;" value="??????????????????" WCMAnt:paramattr="value:document_addedit.jsp.addInnerChannelLink" onclick="AddInnerChannelLink();">
									</span>
									</div>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
						</table>
					</div>
					<!--//???????????????-->
					<div id="file_editor" class="editor_body" style="display:<%=nDocType==40?"":"none"%>">
						<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
							<tr style="height:100px"><td>
							</td></tr>
							<td style="height:60px;" valign="top" align="center">
								<div id="file_editor2" style="border:1px solid #b7b7a6;width:500px;padding:20px;background:#FFFFFF;">
									<form id="frmUploadDocFile" name="frmUploadDocFile" style="margin:0;padding:0" enctype='multipart/form-data'>
									<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
									<tr style="height:30px;" valign="top">
										<td align="center">
											<span style="float:left;line-height:30px;width:70px" WCMAnt:param="document_addedit.jsp.selectedFile">????????????:</span>
											<INPUT TYPE="file" id="DocFile" name="DocFile" style="width:320px" class="input_text" value="">
											<INPUT TYPE="button" name="uploadDocFile" style="width:60px;height:20px;margin-left:5px;%>" class="input_text" value="??????" ignore="1" onclick="UpdateDocFile();" WCMAnt:paramattr="value:document_addedit.jsp.upLoad"/>
										</td>
									</tr>
									<tr style="height:30px;" valign="top" align="center">
										<td>
											<span style="float:left;line-height:30px;width:70px" WCMAnt:param="document_addedit.jsp.fileName">?????????:</span>
											<span style="float:left;margin:4px 0 0">
												<INPUT TYPE="text" id="DOCFILENAME" name="DOCFILENAME" readonly=true style="width:248px" class="input_text" value="<%=CMyString.filterForHTMLValue(sDocFileName)%>" required="true" pattern="string" max_len="100" min_len="1" elname="??????" WCMAnt:paramattr="elname:document_addedit.jsp.file"/>
											</span>
										</td>
									</tr>
									</table>
									</form>
								</div>
							</td>
							<tr><td></td></tr>
						</table>
					</div>
				</div>
				<div id="html_editor" style="display:<%=nDocType==20?"":"none"%>">
					
					<iframe id="_trs_editor_" style="width:100%;height:100%;" frameborder=0 src="../editor/editor.html?ChannelId=<%=nChannelId%>&SiteId=<%=nSiteId%>&DocumentId=<%=nDocumentId%>&Version=1.0.0.11"  allowtransparency="true"></iframe>
				</div>
			</div>
			<div class="layout_east" id="layout_east">
				<div class="editor_wrapper">
					<div class="editor_body" style="background:#EDEDED">
						<div id='doc_props' style="height:100%;overflow:hidden">
							<%@include file="document_addedit_props.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layout_south" id="layhout_south">
			<center>
				<TABLE border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD height="30" valign="middle" id="btns_td"></TD>
				  </TR>
				</TABLE>
			</center>
		</div>
	</div>
	<div id="lbl_autosave" style="display:none"></div>
</div>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCAPD%>">
<%=getAppendixsXml(currDocument,Appendix.FLAG_DOCAPD)%>
</textarea>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCPIC%>">
<%=getAppendixsXml(currDocument,Appendix.FLAG_DOCPIC)%>
</textarea>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_LINK%>">
<%=getAppendixsXml(currDocument,Appendix.FLAG_LINK)%>
</textarea>
<textarea style="display:none" id="relations">
<%=getRelationsXML(currDocument)%>
</textarea>
<!-- add by ffx @2010-11-10  -->
<div id="dragMask">
</div>
<div class="btn_box" >
		<div class="float_btn_title" WCMAnt:param="document_addedit.jsp.quickoperate">
			????????????
		</div>
		<div class="float_btn_content">
			<TABLE>
				<TR>
					<TD>
						<DIV class="button_off" title="???????????????" id="floatbtn1" WCMAnt:paramattr="title:document_addedit.jsp.closeandsave">
							<DIV class="btn_image">
								<DIV class="btn" id="floatbtn_saveexit"></DIV>
							</DIV>
						</DIV>
					</TD>
					<%if(bIsCanPub){%>
					<TD>
						<DIV class="button_off" title="???????????????" id="floatbtn2" WCMAnt:paramattr="title:document_addedit.jsp.saveandpublish">
							<DIV class="btn_image">
								<DIV class="btn" id="floatbtn_savepublish"></DIV>
							</DIV>
						</DIV>
					</TD>
					<%}%>
				</TR>
			</TABLE>
		</div>
</div>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<script src="../js/easyversion/ctrsbutton.js"></script>
<script src="../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
<script src="../js/easyversion/calendar3.js"></script>
<script src="../js/easyversion/ajax.js"></script>
<script src="../js/easyversion/basicdatahelper.js"></script>
<script src="../js/easyversion/web2frameadapter.js"></script>
<script src="../js/easyversion/bubblepanel.js"></script>
<script src="../js/easyversion/crashboard.js"></script>
<script src="../js/easyversion/lockutil.js"></script>
<script src="../js/easyversion/processbar.js"></script>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../js/easyversion/validator52.js"></script>
<script src="../js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../js/easyversion/colorpicker.js"></script>
<script src="../js/easyversion/yuiconnect.js"></script>
<script src="../js/wcm52/CTRSHashtable.js"></script>
<script src="../js/wcm52/CTRSRequestParam.js"></script>
<script src="../js/wcm52/CTRSAction.js"></script>
<script language="javascript">
	$('DocType').value = <%=nDocType%>;
	$('DocForm').value = <%=nDocForm%>;
	PgC.IsCanTop = <%=bIsCanTop%>;
	PgC.TopFlag = !<%=bTopped%> ? 0 : (!<%=bTopForever%> ? 1 : 2);
	$('pri_set_' + PgC.TopFlag).checked = true;
	var bIsReadonly = <%=bIsReadOnly%>;
	var CurrDocId = '<%=nDocumentId%>';
	var DocChannelId = '<%=(docChannel!=null)?docChannel.getId():0%>';
	var CurrChannelId = '<%=(currChannel!=null)?currChannel.getId():0%>';
	var ChnlDocId = '<%=processor.getParam("ChnlDocId", 0)%>';
	var bIsCanPub = '<%=bIsCanPub%>';
	var nModal = '<%=nModal%>';
	var nSiteId = <%=nSiteId%>;
	var m_myArrBtns = [];
	var floatBtn_saveexit = document.getElementById("floatbtn_saveexit");
	var floatBtn_savepublish = document.getElementById("floatbtn_savepublish");
	Event.observe(window, 'load', function(){
		<%if(!bIsReadOnly){%>
			<%if(bIsCanPreview || (currDocument.isAddMode() && DocumentAuthServer.isRightIndexOwnerHas(WCMRightTypes.DOC_PREVIEW))){%>
			/*m_myArrBtns.push({
				html : wcm.LANG.DOCUMENT_PROCESS_20 || "??????",
				action : 'PgC.Preview',
				id : 'preview_1'
			});*/
			<%}%>
			<%if(bShowSaveDraftBtn){%>
			/*m_myArrBtns.push({
				html : "?????????",
				action : 'PgC.SaveAsDraft',
				id : 'saveasdraft'
			});*/
			<%}%>
			m_myArrBtns.push({
				html : wcm.LANG.DOCUMENT_PROCESS_21 || "???????????????",
				action : 'PgC.SaveExit',
				id : 'saveexit'
			});

			// ???????????????????????? add by ffx 2010-11-8
			floatBtn_saveexit.onclick = function(){
				PgC.SaveExit();
			}
			var floatbtn1 = document.getElementById("floatbtn1");
			Event.observe(floatbtn1, "mouseover", function(){
				floatbtn1.className = "button_off_over";
			});
			Event.observe(floatbtn1, "mouseout", function(){
				floatbtn1.className = "button_off";
			});
			
		<%}else{%>
			Element.addClassName(floatBtn_saveexit, "button_disabled");
		<%}%>
		<%if(!bIsReadOnly && !isNewsOrPics && bIsCanAdd){%>
			/*m_myArrBtns.push({
				html : wcm.LANG.DOCUMENT_PROCESS_22 || "???????????????",
				action : 'PgC.SaveAddNew',
				id : 'saveaddnew'
			});*/
		<%}if(!bIsReadOnly && !isNewsOrPics && bIsCanAdd && bIsCanPub){%>
			/*m_myArrBtns.push({
				html : wcm.LANG.DOCUMENT_PROCESS_23 || "???????????????",
				action : 'PgC.PublishAddNew',
				id : 'publishaddnew'
			});*/
		<%}if(!bIsReadOnly && bIsCanPub){%>
			m_myArrBtns.push({
				html : wcm.LANG.DOCUMENT_PROCESS_24 || "???????????????",
				action : 'PgC.SavePublish',
				id : 'savepublish'
			});
			// ?????????????????????????????? add by ffx 2010-11-8
			floatBtn_savepublish.onclick = function(){
				PgC.SavePublish();
			}
			var floatbtn = document.getElementById("floatbtn2");
			Event.observe(floatbtn, "mouseover", function(){
				floatbtn.className = "button_off_over";
			});
			Event.observe(floatbtn, "mouseout", function(){
				floatbtn.className = "button_off";
			});
		<%}else{%>
			Element.addClassName(floatBtn_savepublish, "button_disabled");
		<%}if(showSaveAndFlow(nDocumentId,nChannelId)){%> 
			m_myArrBtns.push({
			html : "???????????????",
			action : 'PgC.SaveFlow',
			id : 'saveflow'
			});
		<%}%>
		m_myArrBtns.push({
			html : wcm.LANG.DOCUMENT_PROCESS_25 || "??????",
			action : 'PgC.SimpleExit',
			id : 'simpleexit'
		});
		//TRSButtons
		var oTRSButton = new CTRSButton('btns_td');
		oTRSButton.setButtons(m_myArrBtns);
		oTRSButton.init();
	});
	wcm.TRSCalendar.get({input:'DocRelTime',handler:'btnDocRelTime',withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'TopInvalidTime',handler:'btnTopInvalidTime',withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'ScheduleTime',handler:'btnScheduleTime',withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
	wcm.TRSCalendar.get({input:'UNPUBTIME',handler:'btnCalunpubtime',withtime:true,dtFmt:'yyyy-mm-dd HH:MM'});
</script>
<script src="../js/data/cmsobj/document.js"></script>
<script src="../js/source/wcmlib/Observable.js"></script>
<script src="../js/source/wcmlib/Component.js"></script>
<script src="../js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../js/source/wcmlib/com.trs.suggestion/suggestion.js"></script>
<script src="document_addedit.js"></script>
<script src="extension.js"></script>
<%
	boolean m_bIsPhotoLibPluginEnabled = com.trs.presentation.plugin.PluginConfig.isStartPhoto();	
	if(m_bIsPhotoLibPluginEnabled){
		m_bIsPhotoLibPluginEnabled = ((com.trs.wcm.photo.IImageLibConfig)DreamFactory.createObjectById("IImageLibConfig")).isCmdUsed();			
		if(!m_bIsPhotoLibPluginEnabled){
			try{
				Class.forName("magick.Magick");	
				m_bIsPhotoLibPluginEnabled = true;
			}catch(Throwable tx){				
			}
		}
	}	
	if(m_bIsPhotoLibPluginEnabled){
		out.print("<script>m_bIsPhotoLibPluginEnabled=true</script>");
	}
%>
</body>
</html>
<%!
	private String getAppendixsXml(Document _currDocument,int nAppendixType) throws WCMException{
		try{
			AppendixMgr m_oAppendixMgr = (AppendixMgr) DreamFactory
					.createObjectById("AppendixMgr");
			// 3.????????????(???????????????????????????)
			Appendixes appendixes = m_oAppendixMgr.getAppendixes(_currDocument,nAppendixType ,null);
			if(appendixes==null) return "";
            // ?????????????????????XML
            AppendixToXML appendixToXML = new AppendixToXML();
			return PageViewUtil.toHtmlValue(appendixToXML.toXmlString(null, appendixes));
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("document_addedit_label_9","??????Appendixs???????????????XML???????????????!"), ex);
		}
	}
	private String getRelationsXML(Document _currDocument) throws WCMException{
		try{
			RelationMgr m_oRelationMgr = (RelationMgr) DreamFactory
                .createObjectById("RelationMgr");
			Relations relations = m_oRelationMgr.getRelations(_currDocument,null);
            RelationToXML relationToXML = new RelationToXML();
			return PageViewUtil.toHtmlValue(relationToXML.toXmlString(null, relations));
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("document_addedit_label_10","??????Relations???????????????XML???????????????!"), ex);
		}
	}
	private String getDocType(int nDocType){
		switch(nDocType){
			case Document.DOC_TYPE_HTML:
				return "HTML";
			case Document.DOC_TYPE_NORMAL:
				return LocaleServer.getString("document_addedit.label.TEXT", "?????????");
			case Document.DOC_TYPE_LINK:
				return LocaleServer.getString("document_addedit.label.LINK", "??????");
			case Document.DOC_TYPE_FILE:
				return LocaleServer.getString("document_addedit.label.FILE", "??????");
		}
		return "";
	}
	private String getDocTypeSelector(){
		StringBuffer sb = new StringBuffer();
		sb.append("<select id='DocType' name='DocType' pattern='string' required='1' ");
		sb.append(" elname='").append(LocaleServer.getString("document_addedit.jsp.docuType", "??????"));
		sb.append("' onchange='SwitchDocEditPanel(this.value);' class='select'/>");
        sb.append("<option value='").append(Document.DOC_TYPE_HTML).append("'>HTML</option>");
        //sb.append("<option value='").append(Document.DOC_TYPE_NORMAL).append("'>");
       // sb.append(getDocType(Document.DOC_TYPE_NORMAL)).append("</option>");
        //sb.append("<option value='").append(Document.DOC_TYPE_LINK).append("'>");
        //sb.append(getDocType(Document.DOC_TYPE_LINK)).append("</option>");
        sb.append("<option value='").append(Document.DOC_TYPE_FILE).append("'>");
        sb.append(getDocType(Document.DOC_TYPE_FILE)).append("</option>");
        sb.append("</select>");
		return sb.toString();
	}
	private String getDocFormSelector(){
		StringBuffer sb = new StringBuffer();
		sb.append("<select id='DocForm' name='DocForm' pattern='string' required='1' ");
		sb.append(" elname='").append(LocaleServer.getString("document_addedit.jsp.docForm", "??????"));
		sb.append("' class='select'/>");
		sb.append("<option value='").append(Document.DOC_FORM_UNKNOWN).append("'>");
		sb.append(LocaleServer.getString("document_addedit.label.unknown", "??????")).append("</option>");
        sb.append("<option value='").append(Document.DOC_FORM_LITERY).append("'>");
		sb.append(LocaleServer.getString("document_addedit.label.litery", "?????????")).append("</option>");
        sb.append("<option value='").append(Document.DOC_FORM_PIC).append("'>");
        sb.append(LocaleServer.getString("document_addedit.label.pic", "?????????")).append("</option>");
        sb.append("<option value='").append(Document.DOC_FORM_AUDIO).append("'>");
        sb.append(LocaleServer.getString("document_addedit.label.audio", "?????????")).append("</option>");
        sb.append("<option value='").append(Document.DOC_FORM_VIDEO).append("'>");
        sb.append(LocaleServer.getString("document_addedit.label.video", "?????????")).append("</option>");
        sb.append("</select>");
		return sb.toString();
	}
	private String v1(Document currDocument, String sName){
		return PageViewUtil.toHtmlValue(CMyString.showNull(currDocument.getPropertyAsString(sName)));
	}
	private String v2(Document currDocument, String sName){
		return CMyString.showNull(currDocument.getPropertyAsString(sName));
	}
	private static int m_nUnpubWorkerId = 0;
	private Schedule getUnpubJob(User loginUser,int _nDoucmentId) throws WCMException{
		Schedules unpubJobs = new Schedules(loginUser);
		WCMFilter unpubJobsFilter = new WCMFilter("", "SENDERID=? and SENDERTYPE=? and OPTYPE=?", "");
		unpubJobsFilter.setSelect("SCHID,ETIME");
		unpubJobsFilter.addSearchValues(_nDoucmentId);
		unpubJobsFilter.addSearchValues(Document.OBJ_TYPE);
		if(m_nUnpubWorkerId == 0){
			JobWorkerType worker = JobWorkerType.findByClassName(WithDrawJobWorker.class.getName());
			if(worker == null){
				return null;
			}
			m_nUnpubWorkerId = worker.getId();
		}
		unpubJobsFilter.addSearchValues(m_nUnpubWorkerId);
		unpubJobs.open(unpubJobsFilter);
		if(!unpubJobs.isEmpty()){
			return (Schedule)unpubJobs.getAt(0);
		}
		return null;
	}
	private String makeWatermarksJson(User loginUser)  {		
		try{
			Watermarks 	currWatermarks = new Watermarks(loginUser);
			currWatermarks.open(null);			
			if(!currWatermarks.isEmpty()){
				StringBuffer buff = new StringBuffer(128*currWatermarks.size());
				for(int i=0,size=currWatermarks.size();i<size;i++){
					Watermark mark = (Watermark)currWatermarks.getAt(i);
					if(mark == null) continue;
					buff.append(",{n:'");
					buff.append(CMyString.filterForJs(mark.getWMName()));
					buff.append("',v:'");
					buff.append(mark.getWMPicture());
					buff.append("'}");
				}
				if(buff.length() > 0){
					return "["+buff.substring(1)+"]";
				}
			}
		}catch(Exception e){
			//Ignore.
		}
		return "[]";
	}

	private boolean showSaveAndFlow(int nDocumentId, int nChannelId) throws WCMException {
		// ?????????DocAdd_INTO_FLOW???true???????????????????????????????????????????????????????????????
		String sDocIntoFlow = ConfigServer.getServer().getSysConfigValue("DOCADD_INTO_FLOW", "true");
		if("true".equalsIgnoreCase(sDocIntoFlow)){
			return false;
		}
		Channel currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			return false;
		}
		// ??????????????????????????????????????????????????????????????????????????????????????????
		FlowEmployMgr flowEmployMgr = (FlowEmployMgr) DreamFactory.createObjectById("FlowEmployMgr");
		if(flowEmployMgr.getFlow(currChannel) == null ){
			return false;
		}
		if(nDocumentId == 0){ // ????????????
			return true;
		}
		// ????????????????????????????????????????????????????????????true??????????????????????????????????????????false??????????????????????????????
		return WCMProcessServiceHelper.canDocumentIntoFlow(nDocumentId);
	}
%>


<script>
//var uls = window.location;

var cid = GetQueryString("ChannelId");

//57????????????
//58????????????
//61????????????
//59????????????
//60????????????
if(cid=="57"){
   sky("skyType").style.display="none";
}else{
  sky("skyType").style.display="block";
}

function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
 
function sky(domId){
    return document.getElementById(domId);
}

</script>