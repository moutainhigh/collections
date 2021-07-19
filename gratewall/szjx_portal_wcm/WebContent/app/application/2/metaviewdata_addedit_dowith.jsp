<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
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

	//定时发布
	processor.reset();	
	processor.setAppendParameters(new String[]{
		"OBJECTID", String.valueOf(nDocid),
	});
	excuteMult(processor, "wcm6_publish", "saveDocumentPublishConfig");
	//定时撤销
	processor.reset();	
	processor.setAppendParameters(new String[]{
		"SenderId", String.valueOf(nDocid),
	});
	excuteMult(processor, "wcm6_publish", "setUnpubSchedule");

	
	//处理模板
	/*
	processor.reset();	
	processor.setAppendParameters(new String[]{
		"OBJECTID", String.valueOf(nDocid),
	});
	excuteMult(processor, "wcm6_publish", "saveDocumentPublishConfig");
	*/

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
	Document oDocument = Document.findById(nDocid, "DocId");
	int nChnldocId = oChnlDoc.getId();

	//处理引用信息
	processor.reset();	
	processor.setAppendParameters(new String[]{
		"OBJECTIDS", String.valueOf(nChnldocId)		
	});
	excuteMult(processor, "wcm61_viewdocument", "quote");

	//处理镜像信息
	processor.reset();	
	processor.setAppendParameters(new String[]{
		"OBJECTIDS", String.valueOf(nChnldocId)		
	});
	excuteMult(processor, "wcm61_viewdocument", "mirror");

	//by CC 20120424 
	//删除链接引用和镜像引用关系
	processor.setAppendParameters(new String[]{
		"DocumentId", ""+nDocid, 
	});
	excuteMult(processor, "document", "setQuote");
	
	String sTitle = oDocument.getTitle();

	//by CC 20120331 增加保存并发布操作！！！
	//这里需要传送的是ChnlDocId，不是DocId
	if(processor.exists("wcm6_viewdocument", "basicPublish")){
		processor.reset();	
		HashMap hParameters = new HashMap();
		hParameters.put("OBJECTIDS", String.valueOf(nChnldocId));
		processor.reset();
		processor.excute("wcm61_viewdocument", "directPublish", hParameters);
	}
	/*
	processor.setAppendParameters(new String[]{
		"OBJECTIDS", String.valueOf(nChnldocId)
	});	
	excuteMult(processor, "wcm61_viewdocument", "directPublish");
	*/

%>({docId:<%=nDocid%>, chnldocId:<%=nChnldocId%>,docTitle:'<%=CMyString.filterForJs(sTitle)%>',channelId:<%=nChannelId%>})