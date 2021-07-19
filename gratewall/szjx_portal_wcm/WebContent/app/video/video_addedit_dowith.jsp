<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<%@include file="../include/public_processor.jsp"%>
<%
	//保存文档
	int nDocumentId = ((Integer)processor.excuteMult("wcm61_video", "save")).intValue();
	//引用
	processor.setAppendParameters(new String[]{
		"DocumentId", ""+nDocumentId, 
	});
	excuteMult(processor, "document", "setQuote");
	processor.reset();
	//置顶
	processor.setAppendParameters(new String[]{
		"DocumentId", ""+nDocumentId, 
	});
	try{
		excuteMult(processor, "document", "setTopDocument");
	}catch(Exception ex){}
	//保存发布信息
	processor.reset();
	processor.setAppendParameters(new String[]{
		"ObjectId", ""+nDocumentId, 
	});
	excuteMult(processor, "wcm6_publish", "saveDocumentPublishConfig");

	
	//保存定时撤销发布信息,wenyh@2009-05-04
	processor.reset();		
	processor.setAppendParameters(new String[]{
		"SenderId", String.valueOf(nDocumentId)		
	});	
	excuteMult(processor, "wcm6_publish", "setUnpubSchedule");

	//保存附件信息
	processor.reset();
	processor.setAppendParameters(new String[]{
		"DocId", ""+nDocumentId, 
	});
	excuteArrayMult(processor, "document", "saveAppendixes");
	//保存相关文档信息
	processor.reset();
	processor.setAppendParameters(new String[]{
		"DocId", ""+nDocumentId, 
	});
	excuteArrayMult(processor, "document", "saveRelation");
	//根据情况移动文档
	processor.reset();
	processor.setAppendParameters(new String[]{
		"ObjectIds", ""+nDocumentId, 
	});
	excuteMult(processor, "document", "move");
	//直接发布文档
	processor.reset();
	processor.setAppendParameters(new String[]{
		"ObjectIds", ""+nDocumentId, 
	});
	excuteMult(processor, "publish", "directPublish");
	out.clear();
%><result><%=nDocumentId%></result>