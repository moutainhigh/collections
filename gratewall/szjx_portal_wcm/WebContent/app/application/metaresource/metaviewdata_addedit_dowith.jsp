<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="/app/include/public_processor.jsp"%>
<%
	//保存数据	
	MetaViewData metaViewData = (MetaViewData)processor.excuteMult("wcm61_metaviewdata", "saveMetaViewData");
	int nDocid = metaViewData.getDocumentId();
	int nChannelId = metaViewData.getChannelId();

	processor.reset();

	//处理附件
	processor.setAppendParameters(new String[]{
		"DocId", String.valueOf(nDocid), 
	});
	excuteArrayMult(processor, "document", "saveAppendixes");

	//处理模板
	processor.reset();	
	processor.setAppendParameters(new String[]{
		"OBJECTID", String.valueOf(nDocid)		
	});
	excuteMult(processor, "wcm6_publish", "saveDocumentPublishConfig");
	
	//处理置顶
	processor.reset();
	processor.setAppendParameters(new String[]{
		"DocumentId", String.valueOf(nDocid), 
	});
	try{
		excuteMult(processor, "document", "setTopDocument");
	}catch(Exception ex){}

	//获取ChnlDocId
	processor.reset();
	
	//保存后返回的metaviewdata对象没有加载chnldoc属性，故此处用直接findby的方式获取
	ChnlDoc oChnlDoc = ChnlDoc.findByDocAndChnl(nDocid, nChannelId);
	int nChnldocId = oChnlDoc.getId();

%>({docId:<%=nDocid%>, chnldocId:<%=nChnldocId%>})