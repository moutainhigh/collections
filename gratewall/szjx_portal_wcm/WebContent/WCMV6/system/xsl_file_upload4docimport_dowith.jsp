<%--
/*
 *	FileName	:	xsl_file_upload4docimport_dowith.jsp
 *	Description	:	文档导入时的xsl上传处理
 *
 *	History			Who			What
 *	2008-01-23		wenyh		created.
 * 
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../app/include/file_upload_judgment.jsp"%>

<%!
private String getFileName(String _strFile) {
	String strFileName = _strFile;
	try {
		java.io.File file = new java.io.File(_strFile);
		strFileName = file.getName();
	} catch(Exception ex) {
	}
	return strFileName;
}
%>
<%
//4.初始化（获取数据）
try{
	String sFileParamName = request.getParameter("FileParamName");	
	RFC1867InputStream.POST_ENCODING = "UTF-8";

	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	request	= oUploadHelper;
//5.权限校验

//6.业务代码		
	String sSaveFile = null;
	String strShowName = null;
	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(sFileParamName);
	DocumentService currDocumentService = (DocumentService)DreamFactory.createObjectById("IDocumentService");
	if (oFileHelper != null && oFileHelper.getSize() > 0){
		if(isForbidFileExt(oFileHelper.getFileName())){
			String sErrorMsg	= CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.forbidfile", "系统禁止上传{0}格式的文件！"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
			sErrorMsg ="<!--ERROR1--><!--##########-->0<!--##########-->" + sErrorMsg + "<!--##########-->" + sErrorMsg + "<!--##########-->";
			out.clear();
			out.print(sErrorMsg);
			return;
		}
		sSaveFile = getFileName(oFileHelper.getFileName());
		sSaveFile = currDocumentService.getMyDocumentImportSourceFilePath() + sSaveFile;
		oFileHelper.writeTo(new java.io.File(sSaveFile));
		strShowName = CMyFile.extractFileName(oFileHelper.getFileName(), "/");
		strShowName = CMyFile.extractFileName(strShowName, "\\");
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
	out.print(strShowName);	
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