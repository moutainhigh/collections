<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.service.ServiceHelper,com.trs.service.IOfficeDocumentService" %>
<%@ page import="com.trs.infra.support.config.*" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../app/include/file_upload_judgment.jsp"%>

<%
//4.初始化（获取数据）
try{
	String sChannelId = request.getParameter("ChannelId");
	String sFileParamName = request.getParameter("FileParamName");
	RFC1867InputStream.POST_ENCODING = "UTF-8";

	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	request	= oUploadHelper;
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
%><!--ERROR1--><!--##########-->0<!--##########-->本地文件不存在.<!--##########-->本地文件不存在.<!--##########-->
<%
		return;
	}
//7.结束
	out.clear();
//业务代码
	//
	String sHtmlContent = "";
	String sContent = "";
	int nDocType = Document.TYPE_HTML;
	if("txt".equalsIgnoreCase(sFileType)){
		sContent = CMyFile.readFile(sSaveFile);
		nDocType = Document.TYPE_NORMAL;
	}
	else{
		IOfficeDocumentService oOdService = ServiceHelper.createOfficeDocumentService();
		sHtmlContent = oOdService.convert2Html(sSaveFile);
		sHtmlContent = sHtmlContent.replaceAll("(?i)<meta[^>]*>","");
	}
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
	newDoc.setContent(sContent);
	newDoc.setHtmlContent(sHtmlContent);
	oDocumentMgr.save(newDoc);
	out.println(sFileName+"[DocId="+newDoc.getId()+"]<!--RIGHT-->");
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
	if(ex instanceof com.trs.net.ftp.FtpException || ex instanceof java.net.ConnectException){
		sExMsg = "智能创建的FTP服务连接失败。";
		sSuggestion = "请检查是否参数配置不正确或者服务未启动。\n若FTP服务未启动，启动FTP服务后还需要重启wcm应用。";
	} else if(ex instanceof org.apache.axis.AxisFault){
		sExMsg = "智能创建的OfficeService服务请求失败。";
		sSuggestion = "请检查是否参数配置不正确或者服务未启动。";
	}
%>
<%@include file="../../app/include/fileupload_error_message_include.jsp"%>
<%=sSuggestion%>
<%
}
%>