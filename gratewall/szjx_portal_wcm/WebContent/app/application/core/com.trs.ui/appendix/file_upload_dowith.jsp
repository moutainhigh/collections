<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>

<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../../include/public_server.jsp"%>
<%@include file="../../../../include/file_upload_judgment.jsp"%>

<%
	String fileNameValue = new String(request.getParameter("fileNameValue").getBytes("ISO8859_1"), "UTF-8");
	String pathFlag = request.getParameter("pathFlag");
	String sType = request.getParameter("Type");

	RFC1867InputStream.POST_ENCODING = "UTF-8";

	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	//request	= oUploadHelper;

	String sSaveFile = null;

	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(oUploadHelper.getParameter("fileNameParam"));
	// 获取系统配置
	String sConfigValue =null;
	if(!CMyString.isEmpty(sType)){
		sConfigValue = ConfigServer.getServer().getSysConfigValue(sType.toUpperCase(), "0");
	}

	out.clear();
	if (oFileHelper == null){
		out.print(LocaleServer.getString("metaviewdata.label.fileError", "{Error:'传入的文件有误！'}"));
		return;
	}else if (oFileHelper.getSize() <= 0){
		out.print(LocaleServer.getString("metaviewdata.label.noContent", "{Error:'传入的文件没有内容'}"));
		return;
	}else if(isForbidFileExt(oFileHelper.getFileName())){
		String sErrorMsg = CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.forbidfile", "系统禁止上传{0}格式的文件！"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
		out.print("{Error:'"+sErrorMsg+"'}");
		return;
	}else{
		try{
			//System.out.println("RestrictSize=" + sRestrictSize + ", size=" + oFileHelper.getSize());
			long nConfigValue = 0;
			if(!CMyString.isEmpty(sConfigValue)
				&& (nConfigValue = Long.parseLong(sConfigValue)) > 0
				&& (oFileHelper.getSize() > nConfigValue*1000)) {
				out.print("{Error:'" + CMyString.format(LocaleServer.getString("file_upload_dowith.filesize.toolarge","上传文件大小超过系统配置的最大值 {0}K，请重新选择文件！"),new String[]{sConfigValue}) + "'}");
				return;
			}
		}catch(Exception ex){
			//ex.printStackTrace();
			throw ex;
			//just skip it
		}
		String fileExt = CMyString.showNull(oFileHelper.getFileType());
		String sFlag = CMyString.isEmpty(pathFlag)?FilesMan.FLAG_PROTECTED:pathFlag;
		sSaveFile = aFilesMan.getNextFilePathName(sFlag, fileExt);
		File fSaveFile = new java.io.File(sSaveFile);
		oFileHelper.writeTo(fSaveFile);
		String sPath = CMyString.setStrEndWith(FilesMan.getFilesMan().mapFilePath(fSaveFile.getName(),FilesMan.PATH_HTTP),'/');
		String fileHttpPath = sPath+fSaveFile.getName();
		out.print("{Message:'" + fSaveFile.getName() + "',Path:'"+fileHttpPath+"'}");
	}
%>