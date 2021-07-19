<%--
/** Title:			infoview_addedit.jsp
 *  Description:
 *		WCM5.2 自定义表单的编辑修改的界面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.04.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.04.12	fcr created
 *
 *  Parameters:
 *		see infoview_addedit.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%@ page import="com.trs.cms.content.HTMLContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.cms.process.engine.ContentProcessInfo" %>
<%@include file="./infoview_public_include.jsp"%>
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	// 初始化参数 —— 文档
	// 仅仅测试指定的文档是否存在
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = null;
	if(nDocumentId > 0){
		currDocument  = Document.findById(nDocumentId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_show.document.getFailed","获取ID为[{0}]的文档失败！"),new int[]{nDocumentId}));
		}
	}
%>

<%
	// 初始化参数 —— 栏目
	// 仅仅测试指定的栏目是否存在、栏目类型是否相符、以及栏目是否设置了正确的表单
	int nChannelId	= currRequestHelper.getInt("ChannelId", 0);
	
	//wenyh@2006-05-24 09:53
	//如果文档id大于0,为编辑模式,使用文档的所属栏目
	if(nDocumentId > 0){
		nChannelId = currDocument.getChannelId();
	}

	if (nChannelId <= 0){
		throw new WCMException(LocaleServer.getString("document_show.id.zero","栏目Id为0，无法找到栏目！"));
	}
	Channel currChannel = Channel.findById(nChannelId);
	if (currChannel == null){
		throw new WCMException(LocaleServer.getString("document_show.channel.cannot.find","无法找到栏目！"));
	}
	if (currChannel.getType() != Channel.TYPE_INFOVIEW){
		throw new WCMException(LocaleServer.getString("document_show.channel.type.wrong","该栏目不是自定义表单栏目，无法编辑其下的文档！"));
	}

	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);

	IInfoViewService oInfoViewService = ServiceHelper.createInfoViewService();
	List listEmployed = oInfoViewService.getEmployedInfoViews(currChannel);
	if (listEmployed == null || listEmployed.size() <= 0) {
		throw new WCMException(LocaleServer.getString("document_show.channel.notFit","该栏目没有配置有效的自定义表单，无法编辑其下的文档！"));
	}
	InfoView infoview = (InfoView) listEmployed.get(0);
	int nInfoViewId = infoview.getId();
%>

<%
	// 权限校验
	if(nDocumentId > 0){
		boolean hasRight = false;
		if(nFlowDocId > 0) {
			hasRight = WCMProcessServiceHelper.hasFlowingActionRight(loginUser, nFlowDocId, WCMRightTypes.DOC_BROWSE);
		}else{
			hasRight = AuthServer.hasRight(loginUser, currDocument, WCMRightTypes.DOC_BROWSE);
		}
		if(!hasRight){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,  CMyString.format(LocaleServer.getString("document_show.noRight","您没有权限浏览ID为[{0}]的文档！"),new int[]{nDocumentId}));
		}
// 因为用于编辑的HTML不是有系统生成的，无法添加解锁的JavaScript代码，所以不能在这里锁定
//			if(!currDocument.canEdit(loginUser)){
//				throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "文档["+currDocument.getTitle()+"]["+currDocument.getId()+"]被用户［"+currDocument.getLockerUserName()+"］锁定！您不能修改！");
//			}
	}else{
		if(currChannel != null && !AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("document_show.newDoc.noRight","您没有权限新建文档！"));
		}
	}

	//判断是否配置了细览模板，决定是否可以显示打印按钮
	boolean bCanPrint = false;
	try{
		IPublishElement publishElement = PublishElementFactory.lookupElement(currChannel.getWCMType(), nChannelId);
		if(publishElement.isFolder()){
			WCMFolderPublishConfig oPublishConfig = new WCMFolderPublishConfig((IPublishFolder)publishElement);
			int nTemplateId = oPublishConfig.getInfoviewPrintTemplateId();
			if(nTemplateId > 0)
				bCanPrint = true;
		}
	}catch(Exception ex){
		//do nothing 因为是否有模版的判断出现异常不应该影响页面的查看
	}
	
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	processor.setAppendParameters(new String[]{"ContentType" , "605"});
	processor.setEscapeParameters(new String[]{"DocumentId", "ContentId"});
	ContentProcessInfo oContentProcessInfo = (ContentProcessInfo)processor.excute("process", "getProcessInfoOfContent");
	int nFlowId =  oContentProcessInfo.getContentFlowId();

	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview.document.show.jsp.title">TRS WCM 查看表单数据:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<style languageStyle="languageStyle">
BODY {
	FONT-SIZE: 10pt; FONT-FAMILY: SimSun
}
.xdTextBox {
	display:inline-block;
	white-space:nowrap;
	text-overflow:ellipsis;
	padding:1px;
	margin:1px;
	border: 1pt solid #dcdcdc;
	color:windowtext;
	background-color:window;
	overflow:hidden;
	text-align:left;
}
body, td{font-size:10pt;}
body{padding:0;margin:0;width:100%;height:100%;overflow:hidden;}
.ctrsbtn_left{background:url(../../images/button_left.gif) no-repeat 0 0;height:24px;}
.ctrsbtn_right{background:url(../../images/button_right.gif) no-repeat right 0;height:24px;line-height:24px;}
.ctrsbtn{cursor:pointer;background:url(../../images/button_bg_line.gif) repeat-x 4px 0;font-size:12px;height:24px;text-align:center;margin:0 5px;width:90px;display:inline-table!important;display:inline;}
.curr-view .c{background:#FFFFFF; font:bold 10pt; color:#000000; cursor:default; border-bottom:1pt solid #000000; border-top:1pt solid #FFFFFF;}
.deactive-view .c{background:#ECE9D8; font:bold 10pt; color:#933A93; cursor:hand; border-bottom:1pt solid #000000; border-top:0pt solid #000000;}
.deactive-view .l{padding-left:8px;background:url(sheet_close_head.gif) no-repeat left center;}
.deactive-view .r{padding-right:8px;background:url(sheet_close_tail.gif) no-repeat right center;}
.curr-view .l{padding-left:8px;background:url(sheet_open_head.gif) no-repeat left center;}
.curr-view .r{padding-right:8px;background:url(sheet_open_tail.gif) no-repeat right center;}
.curr-view,.deactive-view{float:left;}
</style>
</HEAD>
<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" id="action_body">
	<TR height="24">
		<TD>
		<%=LocaleServer.getString("infoview.document.show.doctitle", "标题：")%>
		<input type="text" name="DocTitle" id="DocTitle" style="width:80%" class="xdTextBox" value="<%=CMyString.transDisplay(currDocument.getTitle())%>">
		</TD>
	</TR>
	<TR>
		<TD id="infoview" width="80%">
		</TD>
		<TD id="infoview" width="20%">
		<%if(nFlowId > 0){%>
			<iframe id="frmFlowDoc" src="../include/blank.html" frameborder="NO" border="0" width="100%" height="100%" style="overflow-x: hidden;  overflow-y: hidden;"></iframe>
		<%}%>
		</TD>
	</TR>
	<TR height="32">
		<TD align="center" valign="middle" id="btns_td"></TD>
	</TR>
</TABLE>
<form name="frmAction" id="frmAction" style="margin:0;padding:0">
	<input name="ChannelId" type="hidden" value="<%=nChannelId%>">
	<input name="DocumentId" type="hidden" value="<%=nDocumentId%>">
	<input name="InfoViewId" type="hidden" value="<%=nInfoViewId%>">
	<textarea style="display:none" name="ObjectXML"><%=infoview.getTemplateFileContent()%></textarea>
	<%
		String sContent = currDocument.getContent();
		HTMLContent oHTMLContent = new HTMLContent(sContent);
		sContent = oHTMLContent.parseHTMLContent(null);	
		if(nFlowDocId > 0){
			sContent = oInfoViewService.filterContentInFlow(infoview, sContent, nFlowDocId);
		}
	%>
	<textarea style="display:none" name="DataXML"><%=sContent.replaceAll("&", "&amp;")%></textarea>
</form>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/easyversion/ctrsbutton.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="infoview_multiview.js"></script>
<script language="javascript">
if(<%=nFlowId%> > 0){
	var oPostData = "ctype=605"
					+"&cid="+ getParameter('DocumentId')
					+"&PageSize= -1&showOper=0";
	$('frmFlowDoc').src= "../flowdoc/document_detail_show_tracing.jsp?"+oPostData;
	//$('frmFlowDoc').src= "../flowdoc/workflow_process_tracing.jsp?"+oPostData;
	
}
var m_nInfoViewId = <%= nInfoViewId %>;
var m_nInfoViewDocId = <%=currDocument.getId()%>;
Event.observe(window, 'load', function(){
	wcm.MultiView.draw('infoview', {
		InfoViewId : m_nInfoViewId,
		ViewMode : 3
	});
});
var bCanPrint = '<%=bCanPrint%>';
var btnPrint = {
		html : "<%=LocaleServer.getString("infoview.document.show.print", "打印")%>",
		action : function(){
			if(bCanPrint == 'true')
				window.open('../infoview/infoview_document_preview.jsp?DocumentId=<%=nDocumentId%>&ChannelId=<%=nChannelId%>');
			else{
				var win = window.open('../infoview/infoview_document_print.html');
				win.document.write(getIframeContent());
			}
		},
		id : 'print'
	}
var m_myArrBtns = [];
m_myArrBtns.push(btnPrint);
m_myArrBtns.push({
		html : "<%=LocaleServer.getString("infoview.document.show.close", "关闭")%>",
		action : function(){
			window.close();
		},
		id : 'exit'
	});

var oTRSButton = new CTRSButton('btns_td');
oTRSButton.setButtons(m_myArrBtns);
oTRSButton.init();

function getIframeContent(){
	var docContent = document.getElementsByTagName('iframe')[0].contentWindow.document.documentElement.innerHTML;
	docContent = docContent.replace(/<script.*?>.*?<\/script>/ig, ''); 
	return "<html>"+docContent+"</html>";
}
</script>
</BODY>
</HTML>