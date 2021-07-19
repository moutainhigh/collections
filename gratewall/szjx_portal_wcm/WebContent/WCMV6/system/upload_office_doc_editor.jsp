<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
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
	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(sFileParamName);
	if(oFileHelper != null && isForbidFileExt(oFileHelper.getFileName())){
		String sErrorMsg	= "系统禁止上传"+CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()+"格式的文件！";
		throw new WCMException(sErrorMsg);
	}else if (oFileHelper != null && oFileHelper.getSize() > 0){
		sFileName = oFileHelper.getFileName();
		sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, oFileHelper.getFileType());
		oFileHelper.writeTo(new java.io.File(sSaveFile));
	}
	else{
		out.clear();
%>0<!--##########-->本地文件不存在.<!--##########-->本地文件不存在.<!--##########-->
<!--ERROR-->
<%
		return;
	}
//7.结束
	out.clear();
//业务代码
	//
	IOfficeDocumentService oOdService = ServiceHelper.createOfficeDocumentService();
	String sHtmlContent = oOdService.convert2Html(sSaveFile);
	sFileName = sFileName.replaceAll("^.*(\\/|\\\\)([^\\/\\\\]*)\\..*$","$2");
	sHtmlContent = sHtmlContent.replaceAll("(?i)<meta[^>]*>","");
	out.println(sFileName);
	out.println(sHtmlContent);
	out.flush();
}catch(Throwable ex){
	int errorCode = 0;
	if(ex instanceof WCMException){
		WCMException myEx = (WCMException)ex;
		errorCode = myEx.getErrNo();
	}
%>
<%@include file="../../app/include/fileupload_error_message_include.jsp"%>
<%
}
%>