<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="java.util.HashMap"%>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/file_upload_judgment.jsp"%>
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
	String sFileParamName = request.getParameter("FileParamName");
	String sResponseType = request.getParameter("ResponseType");
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
	DocumentService currDocumentService = (DocumentService)DreamFactory.createObjectById("IDocumentService");
	String sSaveFile = null, sErrorMsg = null;
	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(sFileParamName);
	if(oFileHelper != null && oFileHelper.getSize() <= 0){
		out.clear();
		%>0<!--##########--><%=LocaleServer.getString("file_upload_dowith.label.noContent", "文件为空")%>
		<!--##########--><%=LocaleServer.getString("file_upload_dowith.label.noContent", "文件为空")%><!--##########-->
		<!--ERROR-->
		<%
		return;
	}else if(isForbidFileExt(oFileHelper.getFileName())){
			sErrorMsg	= LocaleServer.getString("fileupload.label.forbid","系统禁止上传") +CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()+LocaleServer.getString("fileupload.label.file", "格式的文件！");
			throw new Exception(sErrorMsg);
	}else{
		if (oFileHelper != null && oFileHelper.getSize() > 0){
			try{
				long nRestrictSize = 0;
				if(!CMyString.isEmpty(sysConfigImageSize)
					&& (nRestrictSize = Long.parseLong(sysConfigImageSize)) > 0
					&& (oFileHelper.getSize() > nRestrictSize*1024)) {
					sErrorMsg =CMyString.format(LocaleServer.getString("file_upload_dowith.filesize.toolarge","上传文件大小超过系统配置的最大值 {0}K，请重新选择文件！"),
						new String[]{sysConfigImageSize});
					throw new Exception(sErrorMsg);
				}
			}catch(Exception ex){
				//ex.printStackTrace();
				throw ex;
				//just skip it
			}
			sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, oFileHelper.getFileType());
			oFileHelper.writeTo(new java.io.File(sSaveFile));
			String sWmpic = oUploadHelper.getParameter("wmpic");
			if(sWmpic != null && sWmpic.trim().length() > 0){//加水印
				String sPos = oUploadHelper.getParameter("wmpos");
				int[] arPos = {3};
				if(sPos != null){
					arPos = CMyString.splitToInt(sPos,",");
				}
				com.trs.wcm.photo.impl.JMarkUtilX.addWatermark(sSaveFile,sWmpic,arPos);
			}
		}
		else{
			out.clear();
	%>0<!--##########--><%=LocaleServer.getString("file_upload_dowith.label.noFileFound", "本地文件不存在")%><!--##########--><%=LocaleServer.getString("file_upload_dowith.label.noFileFound", "本地文件不存在")%><!--##########-->
	<!--ERROR-->
	<%
			return;
		}
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