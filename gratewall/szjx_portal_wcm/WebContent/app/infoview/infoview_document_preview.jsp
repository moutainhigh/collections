<%
/** Title:			document_publish.jsp
 *  Description:
 *		WCM5.2 处理文档发布的页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/14
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		
 *
 *  Parameters:
 *		see document_publish.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IPublishService" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<!------- WCM IMPORTS END ------------>
<%@include file="./infoview_public_include.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nChannelId	= currRequestHelper.getInt("ChannelId", 0);
	boolean bInfoviewPrint	= currRequestHelper.getBoolean("InfoviewPrint", false);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_preview.channel.getFail","获取频道ID为[{0}]的频道失败！"),new int[]{nChannelId}));
	}
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = Document.findById(nDocumentId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("document_preview.doc.getFail","传入文档ID无效，没有找到ID为 [{0}] 的文档！"),new int[]{nDocumentId}));
	}
//5.权限校验
//6.业务代码	
	IPublishService currPublishService = (IPublishService)DreamFactory.createObjectById("IPublishService");
	String[] warningHolder = new String[1];
	String sPreviewUrl = null;
	try{
		IPublishElement publishElement = PublishElementFactory.lookupElement(currChannel.getWCMType(), nChannelId);
		if(publishElement.isFolder()){
			WCMFolderPublishConfig oPublishConfig = new WCMFolderPublishConfig((IPublishFolder)publishElement);
			int nTemplateId = oPublishConfig.getInfoviewPrintTemplateId();
			if(nTemplateId > 0)
				sPreviewUrl = currPublishService.previewInfoviewPrint(currDocument, currChannel, warningHolder, nTemplateId);
		}
	}catch(Exception ex){
		//do nothing 
	}

%>
<SCRIPT LANGUAGE="JavaScript">
	window.location.href = "<%=PageViewUtil.toHtmlValue(sPreviewUrl)%>";
	window.focus();
</SCRIPT>