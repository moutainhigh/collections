<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_for_dialog.jsp"%>
<%@include file="../include/public_processor.jsp"%>
<%
	//保存栏目
	int nChannelId = ((Integer)processor.excuteMult("channel", "save")).intValue();
	//保存发布信息
	processor.reset();
	processor.setAppendParameters(new String[]{
		"ObjectId", ""+nChannelId, 
	});
	excuteMult(processor, "wcm6_publish", "saveChannelPublishConfig");

	//保存定时撤销发布信息,wenyh@2009-05-04
	processor.reset();		
	processor.setAppendParameters(new String[]{
		"SenderId", String.valueOf(nChannelId)		
	});	
	excuteMult(processor, "wcm6_publish", "setUnpubSchedule");

	processor.reset();
	//保存表单的配置
	processor.setAppendParameters(new String[]{
		"ChannelId", ""+nChannelId, 
	});
	excuteMult(processor, "wcm6_infoview", "setEmployedInfoView");
	//保存工作流的配置
	processor.reset();
	processor.setAppendParameters(new String[]{
		"ObjectId", ""+nChannelId, 
	});
	excuteMult(processor, "wcm6_process", "enableFlowToChannel");
	excuteMult(processor, "wcm6_process", "disableFlowToChannel");
	//保存继承扩展字段信息
	processor.reset();
	processor.setAppendParameters(new String[]{
		"HostId", ""+nChannelId, 
	});
	excuteMult(processor, "wcm6_extendfield", "inheritExtendFields");
	out.clear();
%><%=nChannelId%>