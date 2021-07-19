<%--
/** Title:			Document_addedit.jsp
 *  Description:
 *		文档位置设置页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see document_position_set.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = Document.findById(nDocumentId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_position_set_dowith.jsp.notfinddocid", "没有找到ID为[{0}]的文档！"), new int[]{nDocumentId}));
	}

	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_position_set_dowith.jsp.notfindchnlid", "没有找到ID为[{0}]的栏目！"), new int[]{nChannelId}));
	}

	ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(currDocument, currChannel);
	int nCurrDocOrder = currChnlDoc.getDocOrder();

	int nNewDocOrder = currRequestHelper.getInt("DocOrder", 0);
//5.权限校验

//6.业务代码
	WCMFilter filter = new WCMFilter("", "DocOrderPri=0", "", "DocId");
	//IChannelService channelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	//Documents documents = channelService.getDocuments(currChannel, filter);
	Documents documents = com.trs.components.wcm.content.domain.DocumentsGetHelper.getDocuments(currChannel, filter);
	int nDocOrder = documents.indexOf(nDocumentId);
	int nSize = documents.size();
	
	//计算拖动模式和目标DocId
	int nTargetDocId = 0;
	int nPosition = DocumentMgr.POSITION_BEFORE;
	if(nNewDocOrder<0 || nNewDocOrder >= documents.size()){//移动到最后面
		nNewDocOrder = documents.size();
		nPosition = DocumentMgr.POSITION_AFTER;
		nTargetDocId = documents.getIdAt(nNewDocOrder-1);
	}else if( nNewDocOrder == 0 ){//移动到最前面
		nTargetDocId  = documents.getIdAt(0);
	}
	else if( nNewDocOrder > 0 ){//指定位置
		nTargetDocId  = documents.getIdAt(nNewDocOrder-1);		
		Document toDocument = Document.findById(nTargetDocId);
		ChnlDoc toChnlDoc = ChnlDoc.findByDocAndChnl(toDocument, currChannel);
		int nToDocOrder = toChnlDoc.getDocOrder();
		
		if(nCurrDocOrder > nToDocOrder){
			nPosition = DocumentMgr.POSITION_AFTER;
		}
	}
	if(nTargetDocId != nDocumentId){
		//判断是否能够改变文档顺序
		if (!DocumentAuthServer.hasRight(loginUser, currChannel,currDocument, WCMRightTypes.DOC_POSITIONSET)) {
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, CMyString.format(LocaleServer.getString("document_position_set_dowith.jsp.norightatcurrchnlmodifyrecordsort", "没有权限在当前栏目[{0}][ID={1}]下改变记录顺序！"), new String[]{currChannel.getName(),String.valueOf(currChannel.getId())}));
		}
		DocumentMgr documentMgr = (DocumentMgr) DreamFactory
                .createObjectById("DocumentMgr");
		documentMgr.moveDocumentInChannel(currDocument, Document.findById(nTargetDocId), currChannel, nPosition);
	}
	
//7.结束
	out.clear();
%>