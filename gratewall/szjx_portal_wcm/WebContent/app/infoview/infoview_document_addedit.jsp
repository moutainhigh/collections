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
<%@ page import="com.trs.service.InfoViewContext" %>
<%@ page import="com.trs.service.IInfoViewInitor" %>
<%@ page import="com.trs.service.IInfoViewEnumCreator" %>
<%@ page import="com.trs.components.infoview.InfoViewMgr" %>
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
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.service.IWCMProcessService" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%@ page import="com.trs.cms.content.HTMLContent" %>
<%-- ----- WCM IMPORTS END ---------- --%>
<%@include file="./infoview_public_include.jsp"%>
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
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("infoview_document_addedit.jsp.fail2_get_docId", "获取ID为[{0}]的文档失败！"), new int[]{nDocumentId}));
		}
	}else{
		currDocument = Document.createNewInstance();		
	}
%>

<%
	// 初始化参数 —— 栏目
	// 仅仅测试指定的栏目是否存在、栏目类型是否相符、以及栏目是否设置了正确的表单
	int nCurrChannelId	= currRequestHelper.getInt("ChannelId", 0);
	int nChannelId = nCurrChannelId;
	
	//wenyh@2006-05-24 09:53
	//如果文档id大于0,为编辑模式,使用文档的所属栏目
	if(nDocumentId > 0){
		nChannelId = currDocument.getChannelId();
	}

	
	if (nChannelId <= 0){
		throw new WCMException(LocaleServer.getString("infoview_document_addedit.jsp.label.channelId_not_found_exception","栏目Id为0，无法找到栏目！"));
	}
	Channel currChannel = Channel.findById(nChannelId);
	if (currChannel == null){
		throw new WCMException(LocaleServer.getString("infoview_document_addedit.jsp.label.channel_not_found_exception","无法找到栏目！"));
	}
	if (currChannel.getType() != Channel.TYPE_INFOVIEW){
		throw new WCMException(LocaleServer.getString("infoview_document_addedit.jsp.label.unable_edit_selfinfoview_exception","该栏目不是自定义表单栏目，无法编辑其下的文档！"));
	}
	int nChnlDocId = currRequestHelper.getInt("ChnlDocId", 0);
	IInfoViewService oInfoViewService = ServiceHelper.createInfoViewService();
	List listEmployed = oInfoViewService.getEmployedInfoViews(currChannel);
	if (listEmployed == null || listEmployed.size() <= 0) {
		throw new WCMException(LocaleServer.getString("infoview_document_addedit.jsp.label.channel_config_error_exception","该栏目没有配置有效的自定义表单，无法编辑其下的文档！"));
	}
	InfoView infoview = (InfoView) listEmployed.get(0);
	int nInfoViewId = infoview.getId();

	IInfoViewInitor m_oInfoViewInitor = (IInfoViewInitor) DreamFactory
			.createObjectById("InfoViewInitor");
	InfoViewContext oInfoViewContext = new InfoViewContext(request, response);
	oInfoViewContext.setChannelId(nChannelId);
	oInfoViewContext.setDocumentId(nDocumentId);
	oInfoViewContext.setInfoViewId(nInfoViewId);
	oInfoViewContext.setUser(loginUser);
	String sInitorValues = (String)m_oInfoViewInitor.init(oInfoViewContext);
	IInfoViewEnumCreator m_oEnumCreator = (IInfoViewEnumCreator) DreamFactory
			.createObjectById("InfoViewEnumCreator");
	String sEnumScript = (String)m_oEnumCreator.enumScript(oInfoViewContext);
%>

<%
//构造可以填写的字段
	String sInfoViewToHTMLURLParameters = "";
%>

<%
	// 权限校验
	int nWorklistViewType = currRequestHelper.getInt("WorklistViewType", 0);
	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);
	if(nDocumentId > 0){
		boolean hasRight = false;
		if(nFlowDocId > 0) {
			hasRight = WCMProcessServiceHelper.hasFlowingActionRight(loginUser, nFlowDocId, WCMRightTypes.DOC_EDIT);
		}else{
			hasRight = AuthServer.hasRight(loginUser, currDocument, WCMRightTypes.DOC_EDIT);
		}
		if(!hasRight){
			if(nFlowDocId > 0){
				response.sendRedirect("infoview_document_show_inflowdoc.jsp?DocumentId=" + nDocumentId + "&ChannelId=" + nChannelId + "&FlowDocId=" + nFlowDocId + "&WorklistViewType=" + nWorklistViewType);
			}
			else{
				throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("infoview_document_addedit.jsp.noright_change_doc", "您没有权限修改ID为[{0}]的文档！"), new int[]{nDocumentId}));
				//"您没有权限修改ID为["+nDocumentId+"]的文档！");
			}
		}
		// 不明白最初为何要注释这段，解锁代码是可以得到执行的。 add by ly @2008.1.2
		// 因为用于编辑的HTML不是有系统生成的，无法添加解锁的JavaScript代码，所以不能在这里锁定
		if(!currDocument.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED,CMyString.format(LocaleServer.getString("infoview_document_addedit.jsp.noright_change_doc_a", "文档[{0}][{1}]被用户［{2}］锁定！您不能修改！"), new Object[]{String.valueOf(nDocumentId)}));
		}
	}else{
		if(currChannel != null && !AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_ADD)){
            throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,LocaleServer.getString("infoview_document_addedit.noRight","您没有权限新建文档！"));
		}		
	}

	if(nDocumentId == 0){
		currDocument.setType(Document.TYPE_NORMAL);
		currDocument.setChannel(nChannelId);
	}
	String sContent = "";
	boolean bFlowDocInFlow = false;
	boolean bNewDocInFlow  = false;
	int nFlowId		= 0;
	int nFlowNodeId = 0;
	String sPostUserName = "";
	if(nDocumentId > 0){
		sContent = currDocument.getContent();
		HTMLContent oHTMLContent = new HTMLContent(sContent);
		sContent = oHTMLContent.parseHTMLContent(null);
		if(nFlowDocId > 0){
			//指定编辑页面从哪个工作列表中来(待处理-1,已处理-2,我发起-3)
			if(nWorklistViewType == 1) {
				FlowDoc flowdoc = FlowDoc.findById(nFlowDocId);
				if(flowdoc == null) {
					throw new WCMException(CMyString.format(LocaleServer.getString("infoview_document_addedit.object.not.found","没有找到的[FlowDocId={0}]流转文档对象！"),new int[]{nFlowDocId}));
				}
				bFlowDocInFlow = !flowdoc.isWorked();
				if(bFlowDocInFlow){
					nFlowNodeId = flowdoc.getNode() == null ? 0 : flowdoc.getNode().getId();
					sPostUserName = flowdoc.getPostUserName();
				}
			}
			sContent = oInfoViewService.filterContentInFlow(infoview, sContent, nFlowDocId);
		}
	}else{
		String sFlowSetting = ConfigServer.getServer().getSysConfigValue(
                    "DOC_ADD_FLOW_SETTING", "0");
		if(sFlowSetting != null && sFlowSetting.equals("1")) {
			IWCMProcessService processService = ServiceHelper.createWCMProcessService();
			Flow flow = processService.getFlowOfEmployer(currChannel);
			if(flow != null) {
				bNewDocInFlow = true;
				nFlowId = flow.getId();
			}
		}

	}
	boolean bShowTitle = CMyString.isEmpty(infoview.getTitlePattern()) || LocaleServer.getString("infoview.document.addedit.title1", "自定义表单数据").equals(infoview.getTitlePattern());
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="infoview.document.addedit.jsp.title">TRS WCM 添加/修改表单数据</TITLE>
<style>
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
.pb_cv{position:absolute;top:0px;left:0px;width:100%;height:100%;-moz-opacity:0.5;opacity:0.5;z-index:999990;filter:alpha(opacity=50);background-color:#FFFFFF;}
.pb_to{position:absolute;z-index:999998;width:380px;height:180px;left:50%;top:50%;}
.pb_tb{position:absolute;z-index:999999;width:380px;height:120px;left:-50%;top:-50%;width:100%;background:#FFFFFF;}
#pb_wt{margin:0 2px;width:30px;color:red;}
#pb_ev{font-weight:bold;}
#dvValidError{font-size:12px;padding:10px;}
.dv_option_tip{font-size:14px;font-weight:bold;padding:10px;height:30px;}
</style>
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
</HEAD>
<BODY onUnload="unlock(<%= nDocumentId %>,<%= Document.OBJ_TYPE %>);">
<form name="frmAction" id="frmAction" action="./infoview_document_addedit_dowith.jsp" style="margin:0;display:none">
	<input name="ChannelId" type="hidden" value="<%=nChannelId%>">
	<input name="CurrChannelId" type="hidden" value="<%=nCurrChannelId%>">
	<input name="ChnlDocId" type="hidden" value="<%=nChnlDocId%>">
	<input name="DocumentId" type="hidden" value="<%=nDocumentId%>">
	<input name="InfoViewId" type="hidden" value="<%=nInfoViewId%>">
	<input name="FlowDocPostXMLData" type="hidden" id="hdFlowDocPostXMLData">
	<input name="NewFlowDocMapData" type="hidden" id="hdNewFlowDocMapData">
	<input name="DocTitle" type="hidden" id="DocTitle" value="<%=CMyString.filterForHTMLValue(currDocument.getTitle())%>">
	<input name="FlowDocId" type="hidden" id="hdFlowDocId" value="<%=nFlowDocId%>">
	<input name="SkipDocSaving" type="hidden" id="hdSkipDocSaving" value="0">
	<textarea style="display:none" name="ObjectXML"><%=infoview.getTemplateFileContent()%></textarea>
	<textarea style="display:none" name="DataXML"><%=sContent.replaceAll("&", "&amp;")%></textarea>
</form>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" id="action_body">
	<%if(bShowTitle){%>
	<TR height="24">
		<TD colspan="2">
		<span WCMAnt:param="infoview.document.addedit.jsp.doctitle">标题：</span>
		<input type="text" name="_DocTitle" id="_DocTitle" style="width:90%;" class="xdTextBox" value="<%=CMyString.transDisplay(currDocument.getTitle())%>" title="标题若为空，系统会按照配置的规则自动生成标题" WCMAnt:paramattr="title:infoview.document.addedit.jsp.newtitle">
		</TD>
	</TR><%}%>
	<TR>
		<td id="infoview"></td>
		<td width="260px" valign="top" id="right-panel" style="border-left: 1px silver solid;display:none;">
			<div id="dvValidError" style="display:none;">
				<div class="dv_option_tip" WCMAnt:param="infoview.document.addedit.jsp.validinfo">校验信息</div>
				<div id="validMessageContainer" style="color:red;"></div>
			</div>
			<div id="dvFlowDocPanel" style="border: 0px solid red; overflow:hidden;display:none;height:100%;">
				<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				<tr><td class="dv_option_tip">
				<div WCMAnt:param="infoview.document.addedit.jsp.infoviewflow">表单流转</div>
				</td>
				</tr>
				<tr><td>
				<iframe id="frmFlowDoc" src="../include/blank.html" frameborder="NO" border="0" width="100%" height="100%" style="overflow-x: hidden;  overflow-y: auto;"></iframe>
				</td>
				</tr>
				</table>
			</div>
		</td>
	</TR>
	<TR height="32">
		<TD align="center" valign="middle" id="btns_td" colspan="2"></TD>
	</TR>
</TABLE>
<script src="../../app/js/easyversion/cssrender.js"></script>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/ctrsbutton.js"></script>
<script src="../../app/js/easyversion/crashboard.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/processbar.js"></script>
<script src="../../app/js/easyversion/calendar3.js"></script>
<script src="infoview_multiview.js"></script>
<script src="infoview_document_addedit.js"></script>
<script>
var m_nInfoViewId = <%=nInfoViewId%>;
var m_nInfoViewDocId = <%=nDocumentId%>;
var m_nFlowDocId = <%=nFlowDocId%>;
var m_bFlowDocInFlow = <%=bFlowDocInFlow%>;
var m_nFlowId = <%=nFlowId%>;
var m_bNewDocInFlow  = <%=bNewDocInFlow%>;
var m_sInitorValues = <%=sInitorValues%>;
var m_nFlowNodeId = <%=nFlowNodeId%>;
var m_sLoginUserName = '<%=CMyString.filterForJs(loginUser.getName())%>';
var m_sPostUserName = '<%=CMyString.filterForJs(sPostUserName)%>';
var m_myArrBtns = [{
		html : '<%=LocaleServer.getString("infoview.document.addedit.okbutton" ,"确定")%>',
		action : doSubmit,
		id : 'ok'
	}, {
		html : '<%=LocaleServer.getString("infoview.document.addedit.cancelbton" ,"取消")%>',
		action : function(){
			window.close();
		},
		id : 'exit'
	}
];
var oTRSButton = new CTRSButton('btns_td');
oTRSButton.setButtons(m_myArrBtns);
oTRSButton.init();
</script>
</BODY>
</HTML>