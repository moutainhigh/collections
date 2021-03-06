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
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="java.util.List"%>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>
<%!boolean IS_DEBUG = false;%>
<!--- ????????????????????????????????????????????????????????????public_server.jsp??? --->
<%@include file="../include/public_processor.jsp"%>
<%
	String uploadApp = VSConfig.getUploadJavaAppUrl();
	String uploadUrl = uploadApp + "/service/upload";
	String appKey = VSConfig.getAppKey();
	String extUpload = VSConfig.getExtUpload();
	String size = VSConfig.getMaxPostSize();
	if(size == null||"".equals(size)){
		size = "1048576000";
	}

	boolean bIsReadOnly = processor.getParam("ReadOnly", false);
	boolean bEnablePicLib = PluginConfig.isStartPhoto();
	boolean bEnableFlashLib = PluginConfig.isStartVideo();
	ICKMServer currCKMServer = (ICKMServer)DreamFactory.createObjectById("ICKMServer");
	boolean bCKMSimSearch = currCKMServer.isEnableSimSearch();
	boolean bCKMExtract = currCKMServer.isEnableAutoExtract();
	boolean bCKMSpellCheck = currCKMServer.isEnableAutoCheck();

	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	//???????????????????????????
	int nChannelId = processor.getParam("ChannelId", 0);
	int nDocumentId = processor.getParam("DocumentId", 0);
	processor.setEscapeParameters(new String[]{
		"DocumentId", "ObjectId"
	});
	processor.setSelectParameters(new String[]{"DocumentId","ObjectId","ChannelId","FromEditor","FlowDocId"});
	Document currDocument = null;
	ChnlDoc currChnlDoc = null;
	int nModal = 1;

	StringBuffer tokens = new StringBuffer();
	if(nDocumentId == 0){
		currDocument = Document.createNewInstance();
	}else{
		currDocument = (Document) processor.excute("document", "findbyid");
		if(currDocument==null){
			if(nDocumentId!=0){
				throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("video.addedit.jsp.notExsit","ID???[{0}]?????????????????????"),new int[] {nDocumentId}));
			}
		}
		currChnlDoc = ChnlDoc.findByDocAndChnl(nDocumentId,nChannelId);
		if(currChnlDoc == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("video.addedit.jsp.docfail","???????????? ID???[{0}]???ChnlDoc?????? ???"),new int[]{nDocumentId}));
		}
		nModal = currChnlDoc.getPropertyAsInt("MODAL",1);

		List xvideos = XVideo.findXVideosByDocId(nDocumentId);
		for (int k = 0; k < xvideos.size(); k++) {
			XVideo xvideo = (XVideo) xvideos.get(k);
			if (xvideo == null || xvideo.getFileName() == null) {
				continue;
			}
			tokens.append(xvideo.getFileName()).append(';');
		}
	}
	nDocumentId = currDocument.getId();
	Channel currChannel = null;
	Channel docChannel = null;
	boolean isNewsOrPics = false;//??????????????????????????????????????????
	//??????currChannel??????
	if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("video.addedit.jsp.lanmufail","???????????? ID???[{0}]??????????????? ???"),new int[]{nChannelId}));
		}
		if(currChannel.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,currChannel+LocaleServer.getString("video.addedit.jsp.refreshlanmu","????????????!????????????????????????."));
		}
		isNewsOrPics = (currChannel.getType() == Channel.TYPE_TOP_NEWS || currChannel.getType() == Channel.TYPE_TOP_PICS);
	}
	//??????docChannel??????
	if(!currDocument.isAddMode())
		docChannel = currDocument.getChannel();
	else{
		if(currChannel==null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("video.addedit.jsp.notsetuplanmu","????????????????????????????????????"));
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
		try{
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, CMyString.format(LocaleServer.getString("document_addedit.label.document", "??????[{0}],[{1}]?????????[{2}]???????????????????????????"),new String[]{currDocument.getTitle(),String.valueOf(currDocument.getId()),currDocument.getLockerUserName()}));
		}catch(WCMException myEx){
			//??????????????????
			out.clear();
%>
			<script>
				if(confirm('<%=CMyString.filterForJs(myEx.getMessage())%>\n' + '<%=LocaleServer.getString("document_addedit.label.isEnterReadonly", "?????????????????????????????????????????????")%>')){
					location.href = '?<%=request.getQueryString()%>&ReadOnly=1';
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
	}
	if (currDocument.getStatusId() < 0){
		throw new WCMException(CMyString.format(LocaleServer.getString("document_addedit.label.id.zero","ID???[{0}]???????????????????????????????????????????????????????????????"),new int[]{nDocumentId}));
	}
	String sOfficeSid = processor.getParam("OfficeSid");
	String[] officeInfo = null;
	if(sOfficeSid!=null){
		officeInfo = (String[])session.getAttribute(sOfficeSid.trim());
	}
//6.????????????
	
	String sTitle = "";//????????????
	String sTitleColor = "";//??????????????????
	boolean bAttachPic = false;//????????????
	int nDocType = Document.DOC_TYPE_HTML;//?????????????????????HTML
	String sContent = "";//????????????
	String sDocLink = "";//?????????????????????
	String sDocFileName = "";//????????????????????????
	if(currDocument.isAddMode()){
		//???????????????????????????
		currDocument.setType(Document.TYPE_HTML);
		currDocument.setChannel(nChannelId);
		if(officeInfo!=null){
			sTitle = PageViewUtil.toHtmlValue(officeInfo[0]);
			sContent = PageViewUtil.toHtmlValue(officeInfo[1]);
		}
	}else{//???????????????????????????
		//???????????????????????????
		sTitle = PageViewUtil.toHtmlValue(currDocument.getTitle());
		//???????????????????????????,?????????null
		sTitleColor = CMyString.showNull(currDocument.getTitleColor());
		//????????????
		bAttachPic = currDocument.isAttachPic();
		//???????????????????????????
		nDocType = currDocument.getType();
		//???????????????????????????
		switch(nDocType){
			case Document.DOC_TYPE_HTML:
				sContent = (nDocumentId>0)?PageViewUtil.toHtmlValue(currDocument.getHtmlContentWithImgFilter(
                    null, false)):"";
				break;
			case Document.DOC_TYPE_NORMAL:
				sContent = (nDocumentId>0)?PageViewUtil.toHtmlValue(currDocument.getContent()):"";
				break;
			case Document.DOC_TYPE_LINK:
				sDocLink = PageViewUtil.toHtmlValue(currDocument.getPropertyAsString("DOCLINK"));
				break;
			case Document.DOC_TYPE_FILE:
				sDocFileName = PageViewUtil.toHtmlValue(currDocument.getPropertyAsString("DOCFILENAME"));
				break;
		}
	}
	boolean bIsCanAdd = AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_ADD);
	boolean bIsCanPreview = DocumentAuthServer.hasRight(loginUser, currChannel, currDocument, WCMRightTypes.DOC_PREVIEW);

	boolean bIsCanPub = false;
	//?????????????????????
	IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(docChannel);
	IPublishContent content =  PublishElementFactory.makeContentFrom(currDocument, folder);
	if (DocumentAuthServer.hasRight(loginUser, currChannel, currDocument,
                WCMRightTypes.DOC_PUBLISH)) {
		//=======?????????????????????????????????????????????????????????????????????????????????????????????=====
		// 1. ????????????????????????????????????????????????
        if (docChannel.isCanPub()) {
		// 2. ???????????????????????????????????????
            bIsCanPub = (content.getDetailTemplate() != null);
        }
	}

	out.clear();
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<%if(nDocumentId>0){%>
<title WCMAnt:param="video_addedit.jsp.edittitle">TRS WCM ????????????</title>
<script>
document.title = document.title + ': <%=CMyString.filterForJs(currDocument.getTitle())%>';
</script>
<%}else{%>
<title WCMAnt:param="video_addedit.jsp.addtitle">TRS WCM ????????????</title>
<%}%>
<link rel="stylesheet" type="text/css" href="../js/source/wcmlib/components/processbar.css">
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<link rel="stylesheet" type="text/css"  href="../js/resource/widget.css">
<link href="video_addedit.css" rel="stylesheet" type="text/css" />
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
	initExtCss();
	Ext.ns('PgC', 'editorCfg');
	var bEnableAdInTrs = window.trsad_config && window.trsad_config['enable']==true;
	Ext.apply(editorCfg, {
		basePath : WCMConstants.WCM6_PATH + 'video/',
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
			$('lbl_autosave').innerHTML = String.format('???????????????:{0}',new Date().format('yyyy-mm-dd HH:MM:ss'));
			Element.show('lbl_autosave');
			setTimeout("Element.hide('lbl_autosave')", 5000);
		}
	});
	var UserName = "<%=CMyString.filterForJs(loginUser.getName())%>";
</script>
<div id="colorpicker" style="position:absolute;z-index:9999;display:none;"></div>
<input type="hidden" id="jsonMeta" name="jsonMeta" >
<input type="hidden" id="extUpload"  value="<%=extUpload%>">
<form name="frmData" id="frmData" style="display:none">
	<input type="hidden" name="DocEditor" value="<%=loginUser.getName()%>"/>
	<input type="hidden" name="TitleColor" value="<%=("".equals(sTitleColor)?null:sTitleColor)%>"/>
	<input type="hidden" name="DocContent" value=""/>
	<input type="hidden" name="DocHtmlCon" value=""/>
	<input type="hidden" name="DirectlyPublish" value="0"/>
	<input type="hidden" name="ObjectId" value="<%=nDocumentId%>"/>
	<input type="hidden" name="ChannelId" value="<%=(docChannel!=null)?docChannel.getId():0%>"/>
	<input type="hidden" name="SrcFileName" value=""/>
	<input type="hidden" name="Duration" value=""/>
	<input type="hidden" name="Width" value=""/>
	<input type="hidden" name="Height" value=""/>
	<input type="hidden" name="Fps" value=""/>
	<input type="hidden" name="Bitrate" value=""/>
	<input type="hidden" name="sampleId" value=""/>
</form>
<div name="frmAction" id="frmAction" style="margin:0;padding:0;">
	<div class="layout_container">
		<div class="layout_north">
			<div class="north_padding" style="height:3px;background:#DADADA;">&nbsp;</div>
			<div class="north_padding" style="height:1px;background:#BDBDBD;">&nbsp;</div>
			<div class="north_padding" style="height:1px;background:#FFFFFF;">&nbsp;</div>
			<div class="north_padding" style="height:5px;background:#DADADA;">&nbsp;</div>
			<div class="editor_header" id="editor_header">
				<div class="toolbar_label" WCMAnt:param="video_addedit.jsp.documentTitle">????????????</div>
				<div class="top_bar_shuru">
					<input id="DocTitle" name="DocTitle" class="input_text" type="text" size="70" value="<%=sTitle%>" pattern="string" max_len="200" min_len="1" elname="??????" WCMAnt:paramattr="elname:document_addedit.jsp.headLine"/>
					<div id="CountTitle" title="????????????/????????????" WCMAnt:paramattr="title:document_addedit.jsp.maxSize"><span id="TitlePsn">0</span><span class="sep">/</span><span id="TitleCount">0</span></div>
				</div>
				<!--
				<div class="toolbar_sep"></div>
				<div class="attachment" _action="manageAttachment" WCMAnt:param="document_addedit.jsp.appendix">????????????</div>
				<div class="relative_docs" _action="manageRelation" WCMAnt:param="document_addedit.jsp.relation">????????????</div>
				-->
			</div>
		</div>
		<div class="layout_center_container">
			<div class="layout_center">
				<% if (nDocumentId == 0) { %>
						<fieldset style="width:98%;height:250;">
								<legend WCMAnt:param="video_addedit.jsp.uploadvideo">????????????</legend>
								<p WCMAnt:param="video_addedit.jsp.clickhere">????????????????????????????????????</p>
								<div id="dvUpd" align="center" style="width:100%; height:200;">
									<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
									<div id="flashcontent" WCMAnt:param="video_addedit.jsp.notDisplay">??????????????????Flash????????????, ???????????????????????????!<a href="./flashversion.html"><font color="red">????????????????????????????????????????????????</font></a></div>
									<noscript WCMAnt:param="video_addedit.jsp.noScript">?????????????????????????????????????????????javascript, ?????????javascript????????????!</noscript>
								<% if (VSConfig.isAutoClipSample()) { %>
								<script type="text/javascript">	
								<%
								if(uploadApp.endsWith("fma")){
								%>
								var flashvars = {uploadUrl:"<%= uploadUrl %>",
												maxSize:"1048576000",
											 	ip : "<%=uploadApp%>",
												needFinalSubmit:"true" 
									};
								<%}else{%>
								var flashvars = {uploadUrl:"<%= uploadUrl %>?appKey=<%= appKey%>",
												maxSize:"1048576000",
										 		ip : "<%=uploadApp%>/clipLab",
										 		needFinalSubmit:"true" 
									};
								<%}%>	
								
								var params = {
									wmode:"transparent",
									quality:"high",
									scale:"exactfit",
									allowScriptAccess:"sameDomain"
								};
								var attributes = {};
								attributes.id = "vUpload";
								swfobject.embedSWF("vUpload_autoClip.swf", "flashcontent", 
												   "480", "360", "9.0.124", false, flashvars, params, attributes);
								</script>
								<% }  else { %>
								<script type="text/javascript">
									var uploads = document.getElementById("extUpload").value;
						            if(null==uploads||0==uploads.length||uploads=="null")
									{var flashvars = {uploadUrl:"<%= uploadUrl %>?appKey=<%= appKey%>",
												maxSize:"<%= size %>",
												lang:""
												};}
									 else{var flashvars = 
										 {uploadUrl:"<%= uploadUrl %>?appKey=<%= appKey%>",
												maxSize:"<%= size %>",
												extUpload:"<%= extUpload%>",
												lang:""
										 };
											
																
															
												}
											var params = {
												wmode:"Opaque",
												quality:"high",
												scale:"exactfit",
												allowScriptAccess:"always"
											};
											var attributes = {};
											attributes.id = "vUpload";
											swfobject.embedSWF("vUpload.swf", "flashcontent", 
															   "400", "150", "9.0.124", false, flashvars, params, attributes);
								</script>
								<% } %>			
								</div>
								<div align="left" style="margin-top:5px;">
									<li><div WCMAnt:param="video_addedit.jsp.support">??????????????????: .avi | .dat | .mpg | .wmv | .asf | .rm | .rmvb | .mov | .flv | .mp4 | .3gp | .dv | .divx | .qt | .asx???</div></li>
									<li><div WCMAnt:param="video_addedit.jsp.noClose">????????????????????????????????????.</div></li>
								</div>
							</fieldset>
					<% } else {%>
						<div style="width:100%;height:100%;">
							<iframe src="./smallPlayer.jsp?v=<%=tokens.toString() %>"></iframe>
						</div>
					<% } %>
	
			</div>
			<div class="layout_east" id="layout_east">
				<div class="editor_wrapper">
					<div class="editor_body" style="background:#EDEDED">
						<div id='doc_props' style="height:100%;overflow:hidden">
							<%@include file="video_addedit_props.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layout_south" id="layhout_south">
			<center>
				<TABLE border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD height="30" align="center" valign="middle" id="btns_td">
						</TD>
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
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<script src="../js/easyversion/ctrsbutton.js"></script>
<script src="../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
<script src="../js/easyversion/calendar2.js"></script>
<script src="../js/easyversion/ajax.js"></script>
<script src="../js/easyversion/basicdatahelper.js"></script>
<script src="../js/easyversion/web2frameadapter.js"></script>
<script src="../js/easyversion/bubblepanel.js"></script>
<script src="../js/easyversion/crashboard.js"></script>
<script src="../js/easyversion/lockutil.js"></script>
<script src="../js/easyversion/processbar.js"></script>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../js/easyversion/validator52.js"></script>
<script src="../js/source/wcmlib/com.trs.validator/lang/cn.js" WCMAnt:locale="../../app/js/source/wcmlib/com.trs.validator/lang/$locale$.js"></script>
<script src="../js/easyversion/colorpicker.js"></script>
<script src="../js/easyversion/yuiconnect.js"></script>
<script language="javascript">
	//$('DocType').value = <%=nDocType%>;
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
	var m_myArrBtns = [];
	Event.observe(window, 'load', function(){
		<%if(!bIsReadOnly){%>
			m_myArrBtns.push({
				html : wcm.LANG.DOCUMENT_PROCESS_21 || "???????????????",
				action : 'PgC.SaveExit',
				id : 'saveexit'
			});
		<%}%>
		<%if(!bIsReadOnly && !isNewsOrPics && bIsCanAdd){%>
			m_myArrBtns.push({
				html : wcm.LANG.DOCUMENT_PROCESS_22 || "???????????????",
				action : 'PgC.SaveAddNew',
				id : 'saveaddnew'
			});
		<%}if(!bIsReadOnly && !isNewsOrPics && bIsCanAdd && bIsCanPub){%>

		<%}if(!bIsReadOnly && bIsCanPub){%>

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
	wcm.TRSCalendar.get({input:'DocRelTime',handler:'btnDocRelTime'});
	wcm.TRSCalendar.get({input:'TopInvalidTime',handler:'btnTopInvalidTime'});
	wcm.TRSCalendar.get({input:'ScheduleTime',handler:'btnScheduleTime'});
	wcm.TRSCalendar.get({input:'UNPUBTIME',handler:'btnCalunpubtime'});
</script>
<script src="../js/data/cmsobj/document.js"></script>
<script src="../js/source/wcmlib/Observable.js"></script>
<script src="../js/source/wcmlib/Component.js"></script>
<script src="../js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../js/source/wcmlib/com.trs.suggestion/suggestion.js"></script>
<script src="video_addedit.js"></script>
</body>
</html>
<%!
	private String getAppendixsXml(Document _currDocument,int nAppendixType) throws WCMException{
		try{
			AppendixMgr m_oAppendixMgr = (AppendixMgr) DreamFactory
					.createObjectById("AppendixMgr");
			// 3.?????????????????????????????????????????????
			Appendixes appendixes = m_oAppendixMgr.getAppendixes(_currDocument,nAppendixType ,null);
			if(appendixes==null) return "";
            // ?????????????????????XML
            AppendixToXML appendixToXML = new AppendixToXML();
			return PageViewUtil.toHtmlValue(appendixToXML.toXmlString(null, appendixes));
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("video.addedit.jsp.changefail","??????Appendixs???????????????XML??????????????????"), ex);
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
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("video.addedit.jsp.changefail1","??????Relations???????????????XML??????????????????"), ex);
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
        sb.append("<option value='").append(Document.DOC_TYPE_NORMAL).append("'>");
        sb.append(getDocType(Document.DOC_TYPE_NORMAL)).append("</option>");
        sb.append("<option value='").append(Document.DOC_TYPE_LINK).append("'>");
        sb.append(getDocType(Document.DOC_TYPE_LINK)).append("</option>");
        sb.append("<option value='").append(Document.DOC_TYPE_FILE).append("'>");
        sb.append(getDocType(Document.DOC_TYPE_FILE)).append("</option>");
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
%>