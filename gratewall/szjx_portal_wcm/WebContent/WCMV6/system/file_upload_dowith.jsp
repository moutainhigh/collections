<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
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
	String sResponseType = request.getParameter("ResponseType");
	if(sResponseType==null){
		sResponseType = "1";
	}
	RFC1867InputStream.POST_ENCODING = "UTF-8";

	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	//request	= oUploadHelper;
//5.权限校验

//6.业务代码
	String sSaveFile = null, sErrorMsg = null;
	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(sFileParamName);
	if (oFileHelper != null && oFileHelper.getSize() <= 0){	    
	    out.clear();
		%>0<!--##########--><%=LocaleServer.getString("file_upload_dowith.label.noContent", "文件为空")%>
		<!--##########--><%=LocaleServer.getString("file_upload_dowith.label.noContent", "文件为空")%><!--##########-->
		<!--ERROR-->
		<%
		return;
	}else if(isForbidFileExt(oFileHelper.getFileName())){
		 sErrorMsg	= CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.forbid_upload_filetype", "系统禁止上传{0}格式的文件！"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
			//throw new Exception(sErrorMsg);
			out.clear();
			sErrorMsg = "0<!--##########-->" + sErrorMsg;
			sErrorMsg += "<!--##########--><!--ERROR-->";
			out.print(sErrorMsg);
			return;
	}
	else if (oFileHelper != null && oFileHelper.getSize() > 0){
		sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, oFileHelper.getFileType());
		oFileHelper.writeTo(new java.io.File(sSaveFile));
	}else{
		out.clear();
	%>0<!--##########-->本地文件不存在.<!--##########-->本地文件不存在.<!--##########-->
<!--ERROR-->
<%
		return;
	}
//7.结束
	out.clear();
	String strShowName = CMyFile.extractFileName(oFileHelper.getFileName(), "/");
	strShowName = CMyFile.extractFileName(strShowName, "\\");
	if("1".equals(sResponseType)){
		out.print(CMyFile.extractFileName(sSaveFile));
	}
	else if("2".equals(sResponseType)){
		out.print("[\""+CMyFile.extractFileName(sSaveFile)+"\",\""+strShowName+"\"]");
	}
	out.flush();
	if(true) return;
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