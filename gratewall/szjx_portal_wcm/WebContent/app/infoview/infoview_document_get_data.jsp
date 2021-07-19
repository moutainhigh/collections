<%
/** Title:			infoview_document_get_data.jsp
 *  Description:
 *		WCM5.2 自定义表单的文档列表页面（./infoview/document_list_of_channel.jsp）。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2006.04.17
 *  Vesion:			1.0
 *	Update Logs:
 *
 *  Parameters:
 *		see infoview_document_get_data.xml
 *
 */
%>

<%@ page contentType="text/xml;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
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
<%-- ----- WCM IMPORTS END ---------- --%>

<%@include file="../include/public_server.jsp"%>

<%
	// 初始化参数 —— 栏目
	// 仅仅测试指定的栏目是否存在、栏目类型是否相符、以及栏目是否设置了正确的表单
	int nChannelId	= currRequestHelper.getInt("ChannelId", 0);
	if (nChannelId <= 0){
		throw new WCMException(LocaleServer.getString("infoview_document_get_data.id.not.found","栏目Id为0，无法找到栏目！"));
	}
	Channel currChannel = Channel.findById(nChannelId);
	if (currChannel == null){
		throw new WCMException(LocaleServer.getString("infoview_document_get_data.channel.not.found","无法找到栏目！"));
	}
	if (currChannel.getType() != Channel.TYPE_INFOVIEW){
		throw new WCMException(LocaleServer.getString("infoview_document_get_data.form.not.fit","该栏目不是自定义表单栏目，无法查看其下的文档！"));
	}

	IInfoViewService oInfoViewService = ServiceHelper.createInfoViewService();
	List listEmployed = oInfoViewService.getEmployedInfoViews(currChannel);
	if (listEmployed == null || listEmployed.size() <= 0) {
		throw new WCMException(LocaleServer.getString("infoview_document_get_data.form.not.effective","该栏目没有配置有效的自定义表单，无法查看其下的文档！"));
	}
	InfoView infoview = (InfoView) listEmployed.get(0);
%>

<%
	// 初始化参数 —— 文档
	// 仅仅测试指定的文档是否存在
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = null;
	if(nDocumentId <= 0){
		throw new WCMException(LocaleServer.getString("infoview_document_get_data.id.zero","文档Id为0，无法找到文档！"));
	} else {
		currDocument  = Document.findById(nDocumentId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("infoview_document_get_data.getFailed", "获取ID为[{0}]的文档失败！"),new int[]{nDocumentId}));
		}
	}
%>

<%
	// 权限校验
	if(!AuthServer.hasRight(loginUser, currDocument, WCMRightTypes.DOC_BROWSE)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("infoview_document_get_data.noRight", "您没有权限查看ID为[{0}]的文档！"),new int[]{nDocumentId}));
	}
out.clear();
%><%= currDocument.getContent() %>