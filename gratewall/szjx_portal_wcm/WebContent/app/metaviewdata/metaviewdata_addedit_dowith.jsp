<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_processor.jsp"%>
<%
	//괏닸鑒앴	
	MetaViewData metaViewData = (MetaViewData)processor.excuteMult("wcm61_metaviewdata", "saveMetaViewData");
	processor.reset();

	//뇹잿맒숭
	processor.setAppendParameters(new String[]{
		"DocId", String.valueOf(metaViewData.getDocumentId()), 
	});
	excuteArrayMult(processor, "document", "saveAppendixes");

	//뇹잿친겼
	processor.reset();	
	processor.setAppendParameters(new String[]{
		"OBJECTID", String.valueOf(metaViewData.getDocumentId())		
	});
	excuteMult(processor, "wcm6_publish", "saveDocumentPublishConfig");
	
	//뇹잿零땅
	processor.reset();
	processor.setAppendParameters(new String[]{
		"DocumentId", String.valueOf(metaViewData.getDocumentId()), 
	});
	try{
		excuteMult(processor, "document", "setTopDocument");
	}catch(Exception ex){}
%>