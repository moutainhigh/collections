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
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.ajaxservice.PhotoServiceProvider" %>
<%@ page import="com.trs.gyl.imagesmetadatas.ImagesMetaDatas" %>
<%@ page import="java.util.HashMap"%>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.support.config.*" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/file_upload_judgment.jsp"%>

<%!
	private static HashMap paramNameMap = new HashMap();
	private void initParamesMap(){
		paramNameMap.put("DOC_APPENDIX_FILE_SIZE_LIMIT", "filesize");
		paramNameMap.put("DOC_APPENDIX_IMAGE_SIZE_LIMIT", "imagesize");
		paramNameMap.put("DOCUMENT_IMAGE_SIZE_LIMIT", "imageindocsize");
		paramNameMap.put("DOCUMENT_FLASH_SIZE_LIMIT", "flashsize");
		paramNameMap.put("DOCUMENT_VIDEO_SIZE_LIMIT", "videosize");
	}
%>
<%
//4.初始化（获取数据）
try{

	String sType = request.getParameter("Type");
	int nChannelId = 0;
	String sysConfigImageSize = null;
	int nSysConfigImageSize = 0;
	if(!CMyString.isEmpty(sType)){
		if(paramNameMap.size() == 0){
			initParamesMap();
		}
		String sWebSiteAttName = (String)paramNameMap.get(sType.toUpperCase());
		if(!CMyString.isEmpty(sWebSiteAttName)){
			String sChannelId = request.getParameter("channelId");
			if(!CMyString.isEmpty(sChannelId)){
				nChannelId = Integer.parseInt(sChannelId);
			}
			if(nChannelId > 0){
				Channel oChannel = Channel.findById(nChannelId);
				WebSite oWebSite = oChannel.getSite();
				if(oWebSite != null){
					sysConfigImageSize = oWebSite.getAttributeValue(sWebSiteAttName);
				}
			}
		}
		if(CMyString.isEmpty(sysConfigImageSize)){
			sysConfigImageSize= ConfigServer.getServer().getSysConfigValue(sType.toUpperCase(), "0");
		}
	}

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
		long nRestrictSize = 0;
		if(!CMyString.isEmpty(sysConfigImageSize)
			&& (nRestrictSize = Long.parseLong(sysConfigImageSize)) > 0
			&& (oFileHelper.getSize() > nRestrictSize*1000)) {
			String sErrorMsg =CMyString.format(LocaleServer.getString("import_photos_appendix.jsp.label.over", "上传文件大小超过系统配置的最大值 {0}K，请重新选择文件！"),
				new String[]{sysConfigImageSize});
			throw new Exception(sErrorMsg);
		}
		if(isForbidFileExt(oFileHelper.getFileName())){
			String sErrorMsg	= CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.forbidfile", "系统禁止上传{0}格式的文件！"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
			throw new Exception(sErrorMsg);
		}
		sFileName = oFileHelper.getFileName();
		sFileType = oFileHelper.getFileType();
		sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, sFileType);
		oFileHelper.writeTo(new java.io.File(sSaveFile));
	}
	else{
		out.clear();
%><!--ERROR1--><!--##########-->0<!--##########--><%=LocaleServer.getString("import_photos_doc.label.notFound", "本地文件不存在")%><!--##########--><%=LocaleServer.getString("import_photos_doc.label.notFound", "本地文件不存在")%><!--##########-->
<%
		return;
	}
//7.结束
	out.clear();
	////////////////////////////////////////////
	String whatInfo ="exif";
	String results = "";//ImagesMetaDatas.getWCMExif(sSaveFile);
	//首先获取exif信息，如果没有exif信息则取iptc信息
	if(results.trim().length()<10){
		 whatInfo = "iptc";
		 results = "";//ImagesMetaDatas.getWCMIptc(sSaveFile);
		 if(results.trim().length()<10){
			whatInfo = "noinfo";
		 }
	}
	/////////////////////////////////
	//返回文件名回调用
	out.print(CMyFile.extractFileName(sSaveFile)+"#==#"+whatInfo+"#==#"+results);
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
<%@include file="../include/fileupload_error_message_include.jsp"%>
<%=sSuggestion%>
<%
}
%>