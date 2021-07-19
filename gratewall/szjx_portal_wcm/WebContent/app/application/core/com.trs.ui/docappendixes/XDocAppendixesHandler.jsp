<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.AppendixToXML" %>
<%@ page import="com.trs.components.wcm.content.domain.AppendixMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ include file="../../../../include/public_server.jsp"%>
<%
	int nObjectId = currRequestHelper.getInt("ObjectId", 0);
	Document document = Document.findById(nObjectId);
	out.clear();
%>
[
	[<%=Appendix.FLAG_DOCAPD%>, "<%=getAppendixsXml(document, Appendix.FLAG_DOCAPD)%>"],
	[<%=Appendix.FLAG_DOCPIC%>, "<%=getAppendixsXml(document, Appendix.FLAG_DOCPIC)%>"],
	[<%=Appendix.FLAG_LINK%>, "<%=getAppendixsXml(document, Appendix.FLAG_LINK)%>"]
]	
<%!
/**
*返回文档指定类型的附件
*/
private String getAppendixsXml(Document _currDocument, int nAppendixType) throws WCMException{
	if(_currDocument == null || _currDocument.isAddMode()) return "<Appendixes Num='0'/>";
	
	try{
		AppendixMgr m_oAppendixMgr = (AppendixMgr) DreamFactory.createObjectById("AppendixMgr");
		// 3.执行操作（获取指定文档的附件）
		Appendixes appendixes = m_oAppendixMgr.getAppendixes(_currDocument, nAppendixType ,null);
		// 将附件转换成为XML
		AppendixToXML appendixToXML = new AppendixToXML();
		return CMyString.filterForJs(appendixToXML.toXmlString(null, appendixes));
	}catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("XDocAppendixesHandler.jsp.label.collectionconvert2xmlfail", "转换Appendixs集合对象为XML字符串失败！"), ex);
	}
}
%>