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
<!------- openOffice OR ftpOffice BEGIN ------->
<%@ page import="com.trs.service.ServiceHelper,com.trs.service.IOfficeDocumentService" %>
<%@ page import="com.trs.service.impl.OfficeExtractDocumentService" %>
<!------- openOffice OR ftpOffice END ------->
<%@ page import="com.trs.infra.support.config.*" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../../include/public_server.jsp"%>
<!--- 提供判断文件后缀是否为支持上传文件的isForbidFileExt方法 --->
<%@include file="../../../../include/file_upload_judgment.jsp"%>
<%
//4.初始化(获取数据)
try{
	String sFileParamName = request.getParameter("FileParamName");
	RFC1867InputStream.POST_ENCODING = "UTF-8";
	FilesMan aFilesMan = FilesMan.getFilesMan();
	String sUploadTempPath = aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest oUploadHelper = new RFC1867ServletRequest(request, sUploadTempPath);
	//request	= oUploadHelper;
//5.权限校验
//6.业务代码
	String sSaveFile = "";
	String sFileName = "";
	String sFileType = "";
	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(sFileParamName);
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
	Html2text convertor = new Html2text();
	OfficeExtractDocumentService oOdService = new OfficeExtractDocumentService();
	sHtmlContent = oOdService.convert2Html(sSaveFile);
	sHtmlContent = sHtmlContent.replaceAll("(?i)<meta[^>]*>","");
	sContent = convertor.html2text(sHtmlContent);
	sFileName = sFileName.replaceAll("^.*(\\/|\\\\)([^\\/\\\\]*)\\..*$","$2");
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
<%=errorCode%>
<!--##########-->
<%=ex.getMessage()%>
<!--##########-->
<%=WCMException.getStackTraceText(ex)%>
<!--##########-->
<!--ERROR-->
<%
}
%>