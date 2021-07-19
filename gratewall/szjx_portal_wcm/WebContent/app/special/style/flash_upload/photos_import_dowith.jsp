<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import = "java.io.File" %>
<%@ page  import = "com.trs.infra.util.CMyFile" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.support.config.*" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../../include/file_upload_judgment.jsp"%>

<%
//4.初始化（获取数据）
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	int repeatNameMode = currRequestHelper.getInt("repeatNameMode",1);
try{
	RFC1867InputStream.POST_ENCODING = "UTF-8";
	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	//request	= oUploadHelper;
//5.权限校验

//6.业务代码
	
	//获取页面风格的存储文件的路径,路径为：sImgAbsolutePath==D:\TRS\TRSPortal61_view\Tomcat\webapps\portal\portalview\v_common\fengge1\
	JSPRequestProcessor processor = new JSPRequestProcessor(oUploadHelper, response);
	HashMap parameters = new HashMap();
	parameters.put("ObjectId",String.valueOf(nPageStyleId));
	String sImgAbsolutePath = (String)processor.excute("wcm61_pagestyle","findStyleImageDir",parameters);
	String sFolderPath = sImgAbsolutePath;
	if(sFolderPath!=null && (sFolderPath.endsWith("\\") || sFolderPath.endsWith("/") )){
		sFolderPath = sFolderPath.substring(0,sFolderPath.length()-1);
	}
	//保证存放风格的路径已经存在
	File fStyleFolder =  new File(sFolderPath);
	if(!fStyleFolder.exists()){
		CMyFile.makeDir(sFolderPath, true);
	}

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
		//sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, sFileType);
		sSaveFile = sImgAbsolutePath + sFileName;//保持上传前的名字
		if(repeatNameMode == 2 && CMyFile.fileExists(sSaveFile)){
			return;
		}
		oFileHelper.writeTo(new java.io.File(sSaveFile));
	}
	else{
		out.clear();
%><!--ERROR1--><!--##########-->0<!--##########--><%=LocaleServer.getString("import_photos_doc.label.notFound", "本地文件不存在")%><!--##########--><%=LocaleServer.getString("import_photos_doc.label.notFound", "本地文件不存在")%><!--##########-->
<%
		return;
	}//end else
//7.结束
	out.clear();
	////////////////////////////////////////////
	String whatInfo ="exif";
	String results = "";//ImagesMetaDatas.getWCMExif(sSaveFile); //成功返回空
	//首先获取exif信息，如果没有exif信息则取iptc信息
	if(results.trim().length()<10){
		 whatInfo = "iptc";
		 results = "";//ImagesMetaDatas.getWCMIptc(sSaveFile);//成功返回空
		 if(results.trim().length()<10){
			whatInfo = "noinfo";
		 }
	}
	/////////////////////////////////
	//返回文件名回调用
	out.print("U020100507383739534733.jpg#==#noinfo#==#");
	//System.out.println("sFileName==" + sFileName);
	//System.out.println("sSaveFile==" + sSaveFile);
	//System.out.println("sFileType==" + sFileType);
	//System.out.println("111==" + CMyFile.extractFileName(sSaveFile)+"#==#"+whatInfo+"#==#"+results);
	//out.print(CMyFile.extractFileName(sSaveFile)+"#==#"+whatInfo+"#==#"+results);
	out.flush();
//业务代码
}catch(Throwable ex){
	ex.printStackTrace();
	int errorCode = 0;
	if(ex instanceof WCMException){
		WCMException myEx = (WCMException)ex;
		errorCode = myEx.getErrNo();
	}
	String sSuggestion = LocaleServer.getString("import_photos_doc.label.failure", "图片上传失败！");
%>
<%@include file="../../../include/fileupload_error_message_include.jsp"%>
<%=sSuggestion%>
<%
}
%>