<%
	//发布信息
	boolean bMyIsCanPub = bIsCanPub;
	boolean bDefineSchedule = false;
	String sDocTemplateName = getTemplateAsString(null);
	String strExecTime = null;
	int nDocTemplateId = 0;
	CMyDateTime timeNow = CMyDateTime.now().dateAdd(CMyDateTime.MINUTE, 5);
	if(nDocumentId > 0) {
		com.trs.service.ITemplateService currTemplateService = ServiceHelper.createTemplateService();
		Template docTemplate = currTemplateService.getDetailTemplate(content);
		if(docTemplate != null) {
			nDocTemplateId = docTemplate.getId();
		}
		sDocTemplateName = getTemplateAsString(docTemplate);

		WCMContentPublishConfig currConfig = new WCMContentPublishConfig(loginUser, content);
		Schedule currSchedule = currConfig.getSchedule();
		bDefineSchedule = (currSchedule != null);
		if(bDefineSchedule) {
			strExecTime = currSchedule.getExeTime().toString("yyyy-MM-dd HH:mm");
		}
	}
	if(strExecTime==null){
		strExecTime = timeNow.toString("yyyy-MM-dd HH:mm");
	}
%>
<%
	Schedule unpubJob = null;
	if(nDocumentId > 0){
		unpubJob = getUnpubJob(loginUser,nDocumentId);	
	}
%>

<%
Map pubConfigMap = new HashMap();
pubConfigMap.put("DOCTEMPLATEID", String.valueOf(nDocTemplateId));
pubConfigMap.put("TRUNCATE-HTML", CMyString.filterForHTMLValue(formatShow(sDocTemplateName)));
pubConfigMap.put("TEMPLATE-HTMLVALUE", CMyString.filterForHTMLValue(sDocTemplateName));
pubConfigMap.put("MYISCANPUB", bMyIsCanPub?"":"none");
pubConfigMap.put("SCHEDULE-CHECKBOX", bDefineSchedule?"checked":"");
pubConfigMap.put("DEFINESCHEDULE-YES", bDefineSchedule?"":"none");
pubConfigMap.put("DEFINESCHEDULE-NO", bDefineSchedule?"none":"");
pubConfigMap.put("EXECTIME", strExecTime);
pubConfigMap.put("UNPUBJOB-CHECKBOX", (unpubJob==null?"":"checked"));
pubConfigMap.put("UNPUBJOB-ID", String.valueOf((unpubJob==null?0:unpubJob.getId())));
pubConfigMap.put("UNPUBJOB-DATETIME", (unpubJob==null?"none":"inline"));
pubConfigMap.put("UNPUBJOB-EXECTIME", (unpubJob==null? "":unpubJob.getExeTime().toString("yyyy-MM-dd HH:mm")));
builder.append(pubConfigMap);
%>
<%!
	private String getTemplateAsString(Template _template) {
		if(_template == null) {
			return  LocaleServer.getString("document_props.label.none", "无");
		}
		return _template.getName();
	}
%>