<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.wcm.photo.IMagicImage" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.wcm.photo.impl.MagicImageImpl"%>
<%@ page import="com.trs.service.IDocumentService"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.wcm.photo.IImageLibMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel"%>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField"%>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields"%>
<%@ page import="com.trs.components.wcm.content.domain.ContentExtFieldMgr"%>
<%@ page import="com.trs.infra.common.WCMRightTypes"%>
<%@ page import="com.trs.cms.content.CMSBaseObjs"%>
<%@ page import="com.trs.cms.process.engine.ContentProcessInfo" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%@page import="com.trs.wcm.photo.impl.ImageMagickCmd"%>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nPhotoId = currRequestHelper.getInt("DocumentId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int nRecId = currRequestHelper.getInt("RecId",0);
	int nPageIndex = currRequestHelper.getInt("CurrPage",1);
	int nFlowDocId = currRequestHelper.getInt("FlowDocId",0);
	Document currDocument = Document.findById(nPhotoId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,	CMyString.format(LocaleServer.getString("photo_show.jsp.pic_notfound", "没有找到指定ID为[{0}]的图片!"), new int[]{nPhotoId}));
	}
	//权限
	BaseChannel baseChannel = null;
	if (nChannelId > 0) {
		baseChannel = Channel.findById(nChannelId);
	} else {
		baseChannel = WebSite.findById(nSiteId);
	}
	if(baseChannel == null){
		if(nChannelId != 0){
			throw new  WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_show.jsp.channel_notfound", "没有找到指定ID为[{0}]的栏目!"), new int[]{nChannelId}));
		}
		if(nChannelId == 0){
			throw new  WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_show.jsp.website_notfound", "没有找到指定ID为[{0}]的站点!"), new int[]{nSiteId}));
		}
	}

	boolean hasRight = false;
	if(nFlowDocId > 0) {
		hasRight = WCMProcessServiceHelper.hasFlowingActionRight(loginUser, nFlowDocId, WCMRightTypes.DOC_BROWSE);
	}else{
		hasRight = AuthServer.hasRight(loginUser, baseChannel, WCMRightTypes.DOC_OUTLINE);
	}
	if(!hasRight){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("photo_show.jsp.noright_scan_pic", "您没有权限浏览ID为[{0}]的图片!"), new int[]{nPhotoId}));
	}
	//检查栏目的自定义查看页面
	String sContentShowPage = CMyString.showNull(baseChannel.getPropertyAsString("ContentShowPage"));
	if(!(sContentShowPage.equals("") 
		|| sContentShowPage.equals("../photo/photo_show.jsp") 
		|| sContentShowPage.startsWith("../photo/photo_show.jsp?"))){
		String sTarget = "";
		if(sContentShowPage.indexOf("?")==-1){
			sTarget = sContentShowPage+"?"+request.getQueryString();
		}
		else{
			sTarget = sContentShowPage+"&"+request.getQueryString();
		}
		//防止CRLF注入，去除回车换行
		sTarget = sTarget.replaceAll("(?i)%0d|%0a","");
		response.sendRedirect(sTarget);
		return;
	}
	//构造getSacledImages方法，取到不同显示参数
	String[] images = CMyString.split(currDocument.getRelateWords(), ",");
	
	String image = null;
	IImageLibConfig m_libConf = (IImageLibConfig) DreamFactory
                .createObjectById("IImageLibConfig");
	//构造getQuoteKinds方法，提取参数
	ChnlDoc chnldoc = ChnlDoc.findById(nRecId);
	IDocumentService m_docService = (IDocumentService) DreamFactory
                .createObjectById("IDocumentService");
	Channels channels = m_docService.getQuoteChannels(currDocument);
	String[] OtherKinds = null;
	if (channels != null && !channels.isEmpty()) {
		OtherKinds = new String[channels.size()];
		Channel channel = null;
		for (int i = 0, size = channels.size(); i < size; i++) {
			channel = (Channel) channels.getAt(i);
			OtherKinds[i] = getChnlPath(channel);
		}
	} else {
		OtherKinds =  new String[0];
	}
	//构造getQuoteDocs方法，提取参数
	IImageLibMgr m_libManager = (IImageLibMgr) DreamFactory
                .createObjectById("IImageLibMgr");
	Documents docs = m_libManager.getDocumentsQuoteImage(nPhotoId);
	Document doc = null;
	//构造getExtendedProps方法，提取参数
	BaseChannel host = currDocument.getChannel();// getHost(_context);
	ContentExtFieldMgr m_oExtFieldMgr = (ContentExtFieldMgr) DreamFactory
                .createObjectById("ContentExtFieldMgr");
    ContentExtFields fields = m_oExtFieldMgr.getExtFields(host, null);
	ContentExtField field = null;
	String fieldValue = null;
	//取rollmg对象
	ViewDocument currViewDocument = null;
	WCMFilter filter = new WCMFilter("", "ChnlId=?", "","");
	filter.addSearchValues(0, nChannelId);
	String sChannelId = String.valueOf(nChannelId);
	String sSiteId = String.valueOf(nSiteId);
	CMSBaseObjs objects = null;
	ViewDocuments currViewDocuments = new ViewDocuments(loginUser, 1, 1);
	currViewDocuments.setPageSize(3);
	currViewDocuments.setCurrPage(nPageIndex);
	if(nChannelId != 0){
		objects = Channels.findByIds(loginUser, sChannelId);
		Channels oQueryChannels = (Channels) objects;
		currViewDocuments.open(oQueryChannels,filter);
	}else{
		objects = WebSites.findByIds(loginUser, sSiteId);
		currViewDocuments.open(objects, null);
	}
	int nSize = currViewDocuments.size();
	int PageCount = nSize%3 ==0?nSize/3:(nSize + (3-nSize%3)) /3;
	//取flowdocid
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm6_process", sMethodName = "getProcessInfoOfContent";
	HashMap parameters = new HashMap();
	parameters.put("ContentType", 605+"");
	parameters.put("ContentId", nPhotoId+"");
	ContentProcessInfo oContentProcessInfo = (ContentProcessInfo)processor.excute(sServiceId,
			sMethodName, parameters);
	nFlowDocId =  oContentProcessInfo.getContentFlowId();

	//取附件关联的文档信息
	filter = new WCMFilter("", "RelatePhotoIds=?", "","");
	filter.addSearchValues(0, nPhotoId+"");
	Appendixes currAppendixes = Appendixes.openWCMObjs(loginUser,filter);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title WCMAnt:param="photo_show.jsp.title">图片查看</title>	
	<link href="photo_show.css" rel="stylesheet" type="text/css" />		
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/photo.js"></script>
	<!--wcm-dialog start-->
	<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
	<!--wcm-dialog end-->
	<!--AJAX-->
	<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!--CrashBoard-->
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
	<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
	<script src="photo_show.js"></script>
</head>
<body style="overflow:auto">
<div style="margin:20px">
	<center>
	<div id="div_loading"><img src="../images/loading.gif"></div>
	<div id="main" style="width:1005px;display:none">		
		<div id="scaledimagnav" style="width:660px;float:left">
			<div id='nav_title' style="margin:8px;">
				<div id="divDoctitle"><%=CMyString.filterForHTMLValue(currDocument.getTitle())%></div>
				<div class="innerFrame" id="flowaction">
					<%
						if(nFlowDocId>0){
					%>
						<iframe id="gunter_template" src="../flowdoc/document_detail_show_tracing.jsp?ctype=605
						&cid=<%=nPhotoId%>&PageSize= -1"  style="width:100%;height:80px;border:0px;">
						<!-- 给工作流提供的iframe，需要给它传参数：文档id等 -->
						</iframe>
					<%
						}
					%>
					<div id="divGunter" style="font-family: georgia; margin-top: 3px; margin-left:5px; padding: 3px; color: gray; display: none; overflow: auto;">&nbsp;</div>
				</div>							
			</div>
			<div id='nav_size'>
				<%
					ImageMagickCmd.ImageObj imageObj = new ImageMagickCmd.ImageObj();
					IMagicImage magicImage = new MagicImageImpl();
					for (int i = 0, len = images.length; i < len; i++) {
						image = images[i];
						int nWidth = 0;
						int nHeight = 0;
						if(m_libConf.isCmdUsed()){
							imageObj.setFilename(image);
							nWidth = imageObj.width;
							nHeight = imageObj.height;
						}else{
							magicImage.initMagicImage(image);
							nWidth =(int) magicImage.getWidth();
							nHeight =(int) magicImage.getHeight();
						}
				%>
					<span class='nav'>
						<span class='navdesc' onmouseover='highlightNav(this)' onmouseout='normalizeNav(this)'
								onclick='showScaledImage(this)' _file='<%=image%>'>
								<%=m_libConf.getScaleDescAt(i)%>
						</span><span>(<%=nWidth%>X<%=nHeight%>)</span>
					</span>
					<span class='space'>&nbsp;</span>
				<%
					}	
				%>
				<div style='margin-top:20px'>
					<img id='currImage' src=''>
				</div>
			</div>
		</div>
		<div style="position:relative">
			<div id="sidebar" style="float:right;width:265px;margin-top:30px;">
				<div style="height:95px;padding-top:5px">				
					<div id="imageroller">
						<%
							if(nSize > 0){
								for(int i= (nPageIndex-1)*3;i < (nPageIndex-1)*3 +3 && i < nSize ;i++){
									currViewDocument = (ViewDocument) currViewDocuments.getAt(i);
									if(currViewDocument == null){
										throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_show.jsp.ondoc_notfound", "没有找到第[{0}]篇文档!"), new int[]{i}));
									}
									Document newDocument = Document.findById(currViewDocument.getDocId());
									if(newDocument == null){
										throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_show.jsp.ondoc_notfound", "没有找到第[{0}]篇文档!"), new int[]{currViewDocument.getDocId()}));
									}
						%>				
									<img  id="rollimg<%=i+1%>" style="cursor:hand;width:75px;height:75px;" src="<%=mapRollImage(newDocument.getRelateWords())%>" onclick="showPhoto(<%=newDocument.getId()%>,<%=nChannelId%>,<%=nSiteId%>,<%=currViewDocument.getChnlDocProperty("recid",0)%>,<%=nPageIndex%>);">
						 <%
								}
							}
						 %>				
					</div>				
					<div style="padding-top:2px;">
						<span style="float:left;padding-left:10px">
							<a href="#" onclick="onPrevious(<%=currDocument.getId()%>,<%=nChannelId%>,<%=nSiteId%>,<%=nRecId%>,<%=nPageIndex%>,<%=PageCount%>);" title="上一页" WCMAnt:paramattr="title:photo_show.jsp.previewpage">&lt;&lt;</a>
						</span>				
						<span style="float:right;padding-right:10px">
							<a href="#" onclick="onNext(<%=currDocument.getId()%>,<%=nChannelId%>,<%=nSiteId%>,<%=nRecId%>,<%=nPageIndex%>,<%=PageCount%>);" title="下一页" WCMAnt:paramattr="title:photo_show.jsp.nextpage">&gt;&gt;</a>
						</span>
							<span id="tiptext"><%=showRollerTip(nPageIndex,nSize)%></span>
					</div>				
				</div>
				<table><tr><td>			
				<div id="imageprops" style="margin-top:20px">
					<table border="0" cellspacing="1px" cellpadding="1px" style="border-collapse:collapse;">
					<tbody>					
						<tr>
							<td colspan="2" height="5">&nbsp;</td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.picType">图片类型</td>
							<td class="attr_value" align="left"><%=getFileType(currDocument.getRelateWords())%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.picStatus">图片状态</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getStatusName())%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.author">作者</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getAuthorName())%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.people">人物</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getPeople())%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.place">地点</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getPlace())%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.time">时间</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getReleaseTime().toString())%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.keywords">关键词</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getKeywords())%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.cruser">创建人</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getPropertyAsString("cruser"))%></td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.crtime">创建时间</td>
							<td class="attr_value" align="left"><%=trimNull(currDocument.getCrTime().toString())%>&nbsp;</td>
						</tr>
						<tr>
							<td class="attr_name" align="left" WCMAnt:param="photo_show.jsp.exif">Exif信息</td>
							<td class="attr_value" align="left" style="width:200px;"><%=(currDocument.getAbstract() == null? "" : currDocument.getAbstract())%>&nbsp;</td>
						</tr>
					</tbody>
					</table>
				</div>
				</td></tr>
				<tr><td>
				<div id="imageextprops" style="border-top:1px solid gray;padding-top:10px;">
					<table border="0" cellspacing="1px" cellpadding="1px" style="border-collapse:collapse;table-layout:fixed">
					<tbody>			
						<%
							if(!fields.isEmpty()){
								 for (int i = 0, size = fields.size(); i < size; i++) {
									field = (ContentExtField) fields.getAt(i);
										if (field != null) {
											fieldValue = currDocument.getPropertyAsString(field.getName());
						%>
											<tr>
												<td class="attr_name" align="left" valign="top" style="width:100px;break-word:break-all;word-wrap:break-word"><%=CMyString.filterForHTMLValue(field.getDesc())%></td>
												<td class="attr_value" align="left" valign="top" style="width:180px;break-word:break-all;word-wrap:break-word"><%=CMyString.transDisplay(fieldValue, false)%></td>
											</tr>										
						<%
										}
								 }
							}
						%>
					</tbody>
					</table>
				</div>
				</td></tr></table>
			</div>
		</div>
		<div style="width:660px;float:left;border-bottom:1px solid gray;margin-top:20px;text-align:left">
			<label WCMAnt:param="photo_show.jsp.describe">描述：</label>
			<span id="photodesc" style="font-size:14px;"><%=trimNull(currDocument.getContent())%></span>
		</div>
		<div style="width:660px;float:left;margin-top:20px;text-align:left">	
			<table border="0" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td id="kinds">
					<table>
						<tr>
							<td class="attr_name" style="border:0px;" align="right" WCMAnt:param="photo_show.jsp.currPosition">当前位置：</td>
							<td class="attr_value" style="border:0px;color:green" ><%=(chnldoc != null)?getChnlPath(chnldoc.getChannel()):getChnlPath(currDocument.getChannel())%></td>
						</tr>
						<tr>
							<td class="attr_name" style="border:0px;" align="right" WCMAnt:param="photo_show.jsp.toClass">隶属分类：</td>
							<td class="attr_value" style="border:0px;" ><%=getChnlPath(currDocument.getChannel())%></td>
						</tr>
						<tr>
							<td class="attr_name"  style="border:0px;" align="right" WCMAnt:param="photo_show.jsp.beQuotedByClass">被以下分类引用：</td>
							<td>&nbsp;</td>
						</tr>				
						<%
							for(int i=0;i < OtherKinds.length; i++){

						%>
						<tr>
							<td class="attr_name"  style="border:0px;" >&nbsp;</td>
							<td class="attr_value" style="border:0px;" >[<%=i+1%>].&nbsp;&nbsp;<%=OtherKinds[i]%></td>
						</tr>						
						<%
							}
						%>
					</table>
					</td>
				</tr>
				<tr>
					<td id="quotes">
					<table>
						<tr>
							<td class="attr_name"  style="border:0px;" align="right" WCMAnt:param="photo_show.jsp.beQuotedByDoc">被以下文档在内容中引用：</td>
							<td>&nbsp;</td>
						</tr>	
						<%
							 for (int i = 0, size = docs.size(); i < size; i++) {
								doc = (Document) docs.getAt(i);
								if (doc != null) {
						%>
						<tr>
							<td class="attr_name" style="border:0px;" >&nbsp;</td>
							<td class="attr_value status_<%=String.valueOf(doc.getStatusId())%>" style="border:0px;">[<%=i
							+1%>].&nbsp;&nbsp;<%=CMyString.filterForHTMLValue(doc.getTitle())%>&nbsp;&nbsp;[<%=getChnlPath(doc.getChannel())%>]</td>
						</tr>					
						<%
								}
							}
						%>
						<tr height="10">
							<td>&nbsp;</td>
						</tr>
					</table>
					</td>
				</tr>
								<tr>
					<td>
					<table>
						<tr>
							<td class="attr_name"  style="border:0px;" align="right" WCMAnt:param="photo_show.jsp.beQuotedByDocAppendix">被以下文档在附件中引用：</td>
							<td>&nbsp;</td>
						</tr>	
						<%
							 String sIds = ""; 
							 int num = 0;
							 for (int i = 0, size = currAppendixes.size(); i < size; i++) {
								Appendix appendix = (Appendix) currAppendixes.getAt(i);
								if (appendix != null) {
									doc = Document.findById(appendix.getDocId());
									if(doc == null)
										throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_show.jsp.ondoc_notfound", "没有找到第[{0}]篇文档!"), new int[]{appendix.getDocId()}));
									//排除重复的文档
									boolean hasFound = false;
									for(int j=0 ; j < sIds.split(",").length; j++){
										if(sIds.split(",")[j].equals(doc.getId() + "")){
											hasFound = true;
											break;
										}
									}
									if(hasFound){
										continue;
									}else{
										num ++ ;
										sIds += (doc.getId() +",");
									}
						%>
						<tr>
							<td class="attr_name" style="border:0px;" >&nbsp;</td>
							<td class="attr_value status_<%=String.valueOf(doc.getStatusId())%>" style="border:0px;">[<%=num%>].&nbsp;&nbsp;<%=CMyString.filterForHTMLValue(doc.getTitle())%>&nbsp;&nbsp;[<%=getChnlPath(doc.getChannel())%>]</td>
						</tr>					
						<%
								}
							}
						%>
						<tr height="10">
							<td>&nbsp;</td>
						</tr>
					</table>
					</td>
				</tr>
			</tbody>
			</table>				
		</div>
	</div>
	</center>
</div>
</body>
</html>
<%!
	private String getChnlPath(Channel _channel) throws WCMException {
        if (_channel == null) {
            return "";
        }

        StringBuffer buff = new StringBuffer(128);
        buff.append(_channel.getSite().getDesc());
        buff.append("&nbsp;>&nbsp;");
        Channel parent = _channel.getParent();
        while (parent != null) {
            buff.append(parent.getDesc());
            parent = parent.getParent();
            buff.append("&nbsp;>&nbsp;");
        }

        buff.append(_channel.getDesc());

        return buff.toString();
    }
	
	private String getFileType(String _sKeywords) throws WCMException {	
		if(_sKeywords==null || (_sKeywords.trim()).equals("")) return LocaleServer.getString("photo_show.label.unknown", "未知");
		String fn = _sKeywords.split(",")[0];
		if(fn==null || (fn.trim()).equals("")) return LocaleServer.getString("photo_show.label.unknown", "未知");
		return fn.split("\\.")[1];
	}
	
	private String mapRollImage(String _fns) throws WCMException{
		String _fn = _fns.split(",")[0];
		if(_fn.indexOf("W0") == 0){
		return "/webpic/"+_fn.substring(0,8)+"/"+_fn.substring(0,10)+"/"+_fn;
		}else{
			return "../../file/read_image.jsp?FileName=" + _fn;
		}
	}

	private String showRollerTip(int _nPageIndex,int all) throws WCMException{
		int curr = _nPageIndex*3;
		int start = curr-2;
		if(start <= 0) start = 1;
		if(curr > all) curr = all;
		return  start + "-" + curr + "/" + all;
	}
	private String trimNull(String _sOldName) throws WCMException{
		return _sOldName == null ? "" : CMyString.filterForHTMLValue(_sOldName);
	}
%>