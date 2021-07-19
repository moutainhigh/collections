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
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="java.io.*" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../app/include/public_server_nologin.jsp"%>
<%@include file="../../../app/include/file_upload_judgment.jsp"%>

<%!
	private static PrintWriter logWriter;

	public void jspInit(){
		try{
			if(logWriter == null){
				//logWriter = new PrintWriter("e:/upload.log");
				logWriter = new PrintWriter("/data/TRS/trswcm/wcmlogs/upload.log");				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	public void jspDestroy(){
		try{
			if(logWriter != null){
				logWriter.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
%>


<%
	DebugTimer timer = new DebugTimer();
	timer.start();

	String fileNameValue = new String(request.getParameter("fileNameValue").getBytes("ISO8859_1"), "UTF-8");
	String pathFlag = request.getParameter("pathFlag");
	String sType = request.getParameter("Type");

	RFC1867InputStream.POST_ENCODING = "UTF-8";

	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	request	= oUploadHelper;

	String sSaveFile = null;

	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(request.getParameter("fileNameParam"));

	out.clear();
	if (oFileHelper == null){
		out.print(LocaleServer.getString("metaviewdata.label.fileError", "{Error:'传入的文件有误！'}"));
		return;
	}else if (oFileHelper.getSize() <= 0){
		out.print(LocaleServer.getString("metaviewdata.label.noContent", "{Error:'传入的文件没有内容'}"));
		return;
	}else if(isForbidFileExt(oFileHelper.getFileName())){
			String sFileExt = CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase();
			out.print("{Error:'系统禁止上传" +sFileExt+"格式的文件！'}");
			return;
	}else{
		timer.stop();
		long nUploadTime = timer.getTime();
		String sRemoteHost = request.getRemoteHost();
		//String sFileSize = com.trs.infra.support.file.FileHelper.convertFileSize(oFileHelper.getSize());
		String sFileSize = String.valueOf(oFileHelper.getSize());
		String sInfo = "用户["+sRemoteHost+"]上传文件耗时[" + nUploadTime + "]ms,文件大小为:["+ sFileSize +"]";
		logWriter.println(sInfo);
		logWriter.flush();

		String fileExt = CMyString.showNull(oFileHelper.getFileType());
		String sFlag = FilesMan.FLAG_UPLOAD;
		sSaveFile = aFilesMan.getNextFilePathName(sFlag, fileExt);
		File fSaveFile = new java.io.File(sSaveFile);
		oFileHelper.writeTo(fSaveFile);
		String sPath = CMyString.setStrEndWith(FilesMan.getFilesMan().mapFilePath(fSaveFile.getName(),FilesMan.PATH_HTTP),'/');
		String fileHttpPath = sPath+fSaveFile.getName();
		out.print("{Message:'" + fSaveFile.getName() + "',Path:'"+fileHttpPath+"', Size:'"+sFileSize+"'}");
	}
%>