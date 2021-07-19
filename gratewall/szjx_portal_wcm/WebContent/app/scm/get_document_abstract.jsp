<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../include/public_server.jsp"%>
<%! final boolean IS_DEBUG = false;%>
<%	
	JSPRequestProcessor oProcessor = new JSPRequestProcessor();
	int nRecId = currRequestHelper.getInt("RecId",0);
	int nSiteType = currRequestHelper.getInt("SiteType",0);
	ChnlDoc oChnlDoc = ChnlDoc.findById(nRecId);
	if(oChnlDoc == null){
		out.print(1);
		return;
	}

	Document oCurrDocument = oChnlDoc.getDocument();
	if(oCurrDocument == null){
		out.print(1);
		return;
	}

	if(oChnlDoc.getStatusId() !=10){
		out.print(2);
		return;
	}
	HashMap hParameters = new HashMap();
	hParameters.put("RecId", String.valueOf(nRecId));
	hParameters.put("SiteType", String.valueOf(nSiteType));

	Object result = oProcessor.excute("wcm61_scmmicrocontenttemplate","getSCMMicroContent", hParameters);
	
	String sContent = ((String) result).toString();

	if(CMyString.isEmpty(sContent)){
		sContent = (String)oCurrDocument.getProperty("DOCTITLE");
	}
	// 不再用JSON返回数据
	out.print(CMyString.filterForHTMLValue(sContent));
%>