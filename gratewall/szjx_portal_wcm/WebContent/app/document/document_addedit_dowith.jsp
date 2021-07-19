<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@include file="../include/public_processor.jsp"%>
<%
	//保存文档
	int nDocumentId = ((Integer)processor.excuteMult("document", "save")).intValue();
	//引用
	processor.setAppendParameters(new String[]{
		"DocumentId", ""+nDocumentId, 
	});
	excuteArrayMult(processor, "document", "setQuote");
	processor.reset();
	//镜像
	processor.setAppendParameters(new String[]{
		"OBJECTIDS", ""+nDocumentId, 
	});
	excuteMult(processor, "document", "mirror");
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

	// add by caohui@2012-7-6 上午11:39:24
	// 让项目自己控制保存并发布是所有点还是当前点
	// 为了兼顾以前习惯和逻辑，默认是发布所有点
	String sDirectPublishOnSave = ConfigServer
			.getServer()
			.getSysConfigValue("DirectPublishOnSave", "true")
			.trim();
	boolean bDirectPublishOnSave = sDirectPublishOnSave.equalsIgnoreCase("true")
			|| sDirectPublishOnSave.equalsIgnoreCase("1");
	if (bDirectPublishOnSave) {// 开启了撤稿
		//直接发布文档
		processor.reset();
		processor.setAppendParameters(new String[]{
			"ObjectIds", ""+nDocumentId, 
		});
		excuteMult(processor, "publish", "directPublish");
	}


	
	//获取ChnlDocId
	processor.reset();
	processor.setAppendParameters(new String[]{
		"DocId", String.valueOf(nDocumentId)		
	});	
	int nChnlDocId = ((Integer)excuteMult(processor, "wcm61_viewdocument", "findChnlDocId")).intValue();
	
	// add by caohui@2012-7-6 上午11:39:24
	// 仅仅发布当前点
	if (!bDirectPublishOnSave) {
		// 如果给的请求是保存并发布
		if(processor.exists("publish", "directPublish")){
			//直接发布文档
			processor.reset();
			HashMap hParameters = new HashMap(1);
			hParameters.put("OBJECTIDS", String.valueOf(nChnlDocId));
			processor.excute("wcm61_viewdocument", "basicPublish", hParameters);	
		}	
	}
	out.clear();
%>({docId:<%=nDocumentId%>, chnldocId:<%=nChnlDocId%>})