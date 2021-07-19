<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ include file="../../../../include/public_server.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%
	Documents docs = null;
	String sObjectIds = currRequestHelper.getString("ObjectIds");
	if(CMyString.isEmpty(sObjectIds)){
		int nObjectId = currRequestHelper.getInt("ObjectId", 0);
		if(nObjectId == 0){
			throw new Exception(LocaleServer.getString("XRelatedDocHandler.jsp.label.requiredparamsisnull", "没有传入相关参数[ObjectIds]或[ObjectId]"));
		}
		docs = getRelatedDocs(nObjectId);
	}else{
		docs = Documents.findByIds(loginUser, sObjectIds);
	}
	out.clear();
%>
{
	RELATIONS : {
		RELATION : [
<%
	Document doc = null;
	for(int index = 0, length = docs.size(); index < length; index++){
		try{
			doc = (Document) docs.getAt(index);
			//by CC 20120417 过滤掉删除到废稿箱的文档
			if(doc == null || doc.getStatusId()<0) continue;
%>
			{
				RELDOC : {
					ID : <%=doc.getId()%>,
					TITLE : '<%=CMyString.filterForJs(CMyString.showNull(doc.getTitle()))%>',
					CHANNELID : <%=doc.getChannelId()%>
				}
			},
<%
		}catch(Throwable tx){
			String sTip = LocaleServer.getString("metaviewdata.label.reldocNotExist","指定的相关文档不存在");
%>
			{
				RELDOC : {
					ID : <%=doc.getId()%>,
					TITLE : '<%=CMyString.filterForJs(sTip)%>[ID=]<%=doc.getId()%>',
					CHANNELID : 0
				}
			},
<%
		}
	}
%>
		]
	}
}
<%!
	private Documents getRelatedDocs(int nObjectId)throws WCMException{
		Document doc = Document.findById(nObjectId);
		DocumentMgr oDocumentMgr = (DocumentMgr) DreamFactory.createObjectById("DocumentMgr");
		return oDocumentMgr.getRelatedDocuments(doc, Document.GET_RELATEDDOC_BY_RELATION, null);
	}
%>