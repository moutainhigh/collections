<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%@ page import="com.trs.tools.Html2text" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.CharsetDetector" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.service.ServiceHelper,com.trs.service.IOfficeDocumentService" %>
	<!-- ************** -->
<%@ page import="com.trs.service.impl.OfficeExtractDocumentService" %>
	<!-- ************** -->
<%@ page import="com.trs.infra.support.config.*" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/file_upload_judgment.jsp"%>

<%
//4.初始化（获取数据）
try{
	String sChannelId = currRequestHelper.getString("ChannelId");
	//String sFileCode = request.getParameter("FileCode");
	String sDocKeyWords = currRequestHelper.getString("KeyWords");
	String sFileParamName = currRequestHelper.getString("FileParamName");
	RFC1867InputStream.POST_ENCODING = "UTF-8";
	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	//request	= oUploadHelper;
//5.权限校验

//6.业务代码
	String sSaveFile = "";
	String sFileName = "";
	String sFileType = "";
	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart("uf");
	if (oFileHelper != null && oFileHelper.getSize() > 0){
		if(isForbidFileExt(oFileHelper.getFileName())){
			String sErrorMsg	= CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.forbidfile", "系统禁止上传{0}格式的文件！"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
			sErrorMsg ="<!--ERROR1--><!--##########-->0<!--##########-->" + sErrorMsg + "<!--##########-->" + sErrorMsg + "<!--##########-->";
			out.clear();
			out.print(sErrorMsg);
			return;
		}
		sFileName = oFileHelper.getFileName();
		sFileType = oFileHelper.getFileType();
		sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, sFileType);
		oFileHelper.writeTo(new java.io.File(sSaveFile));
	}
	else{
		out.clear();
%>
<!--ERROR1--><!--##########-->0<!--##########--><%=LocaleServer.getString("import_office_doc.jsp.label.local_file_not_exists","本地文件不存在")%><!--##########--><%=LocaleServer.getString("import_office_doc.jsp.label.local_file_not_exists","本地文件不存在")%><!--##########-->
<%
		return;
	}
//7.结束
	out.clear();
//业务代码
	//
	String sHtmlContent = "";
	String sContent = "";
	int nDocType = currRequestHelper.getInt("DocType",Document.TYPE_HTML);
	Html2text convertor = new Html2text();
	if("txt".equalsIgnoreCase(sFileType)){
		String encoding = CharsetDetector.detect(sSaveFile);
		//txt转html，TODO 是否有更高效的方法
		sHtmlContent = CMyFile.readFile(sSaveFile,encoding).replaceAll(" ","&nbsp;").replaceAll("\\t","&nbsp;&nbsp;&nbsp;&nbsp;").replaceAll("\\n","<br/>");
		sContent = convertor.html2text(sHtmlContent);
	}else if("htm".equalsIgnoreCase(sFileType) || "html".equalsIgnoreCase(sFileType)){
		String encoding = CharsetDetector.detect(sSaveFile);
        sHtmlContent = CMyFile.readFile(sSaveFile,encoding);
		sHtmlContent = sHtmlContent.replaceAll("(?i)<meta[^>]*>","");
		sContent = convertor.html2text(sHtmlContent);
	}else if("pdf".equalsIgnoreCase(sFileType)){//PDF文件只能够是文件类型的文档，不能通过OpenOffice转换为html文档
		nDocType = Document.TYPE_FILE;
	}else{
		//IOfficeDocumentService oOdService = ServiceHelper.createOfficeDocumentService();
		//
		OfficeExtractDocumentService oOdService = new OfficeExtractDocumentService();
		//
		sHtmlContent = oOdService.convert2Html(sSaveFile);
		sHtmlContent = sHtmlContent.replaceAll("(?i)<meta[^>]*>","");
		sContent = convertor.html2text(sHtmlContent);
	}
	/*
	*将openoffice抽取出来的html进行整理
	*匹配line-height值为%形式的数值，替换成倍数的值，举例：line-height:200%; TO line-height:2;
	*解决行距属性的继承问题
	*/
	Pattern p=Pattern.compile("(?im)line-height\\s?:\\s?((\\d{1,4})%)");
	Matcher m=p.matcher(sHtmlContent);
	StringBuffer sbHtml = new StringBuffer(sHtmlContent.length()); 
	int nStartIndex = 0; 
	int nEndIndex = 0;
	while (m.find()) {
		nStartIndex = m.start(); 
		// 追加前一次匹配结束到本次匹配开始之间的内容 
		sbHtml.append(sHtmlContent.substring(nEndIndex, nStartIndex)); 
		nEndIndex = m.end();
		String strNumber = m.group(2);
		float nN = Float.parseFloat(strNumber);
		String sN = Float.toString(nN/100);
		sbHtml.append("line-height:").append(sN); 
	}
	if (nEndIndex < sHtmlContent.length()) {
		sbHtml.append(sHtmlContent.substring(nEndIndex)); 
	}
	sHtmlContent = sbHtml.toString();
	//end

	sFileName = sFileName.replaceAll("^.*(\\/|\\\\)([^\\/\\\\]*)\\..*$","$2");
	DocumentMgr oDocumentMgr = (DocumentMgr) DreamFactory.createObjectById("DocumentMgr");
	Document newDoc = Document.createNewInstance();
	int nLastIndex = sFileName.lastIndexOf(".");
	if(nLastIndex!=-1){
		newDoc.setTitle(sFileName.substring(0, nLastIndex));
	} else {
		newDoc.setTitle(sFileName);
	}
	newDoc.setChannel(Integer.parseInt(sChannelId));
	newDoc.setType(nDocType);
	if(nDocType == Document.TYPE_FILE){
		String sName = CMyFile.extractFileName(sSaveFile); 
		newDoc.setProperty("DOCFILENAME",sName);
	}else{
		newDoc.setContent(sContent);
		newDoc.setHtmlContent(sHtmlContent);
	}
	if(!CMyString.isEmpty(sDocKeyWords)){
		newDoc.setKeywords(sDocKeyWords);
	}
	oDocumentMgr.save(newDoc);
	String sDocIntoFlow = ConfigServer.getServer().getSysConfigValue(
					"DOCIMPORT_INTO_FLOW", "false");
	if ("true".equalsIgnoreCase(sDocIntoFlow)){
		WCMProcessServiceHelper.startDocumentInFlow(loginUser, newDoc, true);
	}
	ChnlDoc newChnlDoc = ChnlDoc.findByDocument(newDoc);
	int nChnlDocId = newChnlDoc.getId();
	out.println(sFileName+"[DocId="+newDoc.getId()+"]<!--RIGHT-->"+nChnlDocId);
	out.flush();
}catch(Throwable ex){
	ex.printStackTrace();
	int errorCode = 0;
	if(ex instanceof WCMException){
		WCMException myEx = (WCMException)ex;
		errorCode = myEx.getErrNo();
	}
	String sExMsg = ex.getMessage();
	String sSuggestion = "";
	if(ex instanceof java.net.ConnectException){
		sExMsg = LocaleServer.getString("import_office_doc.jsp.label.fail2create_digital_openoffice_link","智能创建的OpenOffice服务连接失败。");
		sSuggestion = LocaleServer.getString("import_office_doc.jsp.label.please_check","请检查是否参数配置不正确或者服务未启动。");
	} else if(ex instanceof org.apache.axis.AxisFault){
		sExMsg = LocaleServer.getString("import_office_doc.jsp.label.fail2create_officeservice","智能创建的OfficeService服务请求失败。");
		sSuggestion = LocaleServer.getString("import_office_doc.jsp.label.please_check","请检查是否参数配置不正确或者服务未启动。");
	}
%>
<%@include file="../include/fileupload_error_message_include.jsp"%>
<%=sSuggestion%>
<%
}
%>