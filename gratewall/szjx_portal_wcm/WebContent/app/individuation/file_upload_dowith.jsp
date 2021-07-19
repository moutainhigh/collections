<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.io.*" %>

<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.regex.Pattern" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/file_upload_judgment.jsp"%>
<%
	String fileNameValue = new String(request.getParameter("fileNameValue").getBytes("ISO8859_1"), "UTF-8");

	RFC1867InputStream.POST_ENCODING = "UTF-8";

	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	//request	= oUploadHelper;

	String sSaveFile = null;

	RFC1867FormPart	oFileHelper	= oUploadHelper.getFormPart(oUploadHelper.getParameter("fileNameParam"));

	if (oFileHelper == null){
		out.print(LocaleServer.getString("indivadual.fileupload.dowith.fileerror" ,"{Error:'传入的文件有误！'}"));
		return;
	}else if (oFileHelper.getSize() <= 0){
		out.print(LocaleServer.getString("indivadual.fileupload.dowith.filenone" ,"{Error:'传入的文件没有内容'}"));
		return;
	}else if(isForbidFileExt(oFileHelper.getFileName())){
		String sErrorMsg	= CMyString.format(LocaleServer.getString("fileupload.label.forbid","系统禁止上传{0}格式的文件！"),new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
		out.print(sErrorMsg);
		return;
	}else{
		sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_USERTEMP, oFileHelper.getFileType());
		if(!isValidFile(sSaveFile)){
			out.print(LocaleServer.getString("indivadual.fileupload.dowith.errortype" ,"{Error:'传入的文件格式不对！'}"));
			return;
		}
		oFileHelper.writeTo(new java.io.File(sSaveFile));
	}

	{//load to picture directory
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
			File inputFile = new File(sSaveFile);
			String webpicPath = FilesMan.getFilesMan().getPathConfigValue(FilesMan.FLAG_WEBFILE,FilesMan.PATH_LOCAL);
			webpicPath = CMyString.setStrEndWith(webpicPath,'/');
			File outputFile = new File(webpicPath+"images/login/background");
			outputFile = new File(outputFile, String.valueOf(loginUser.getId()));

			if(!outputFile.exists()){
				if(!outputFile.mkdir()){
					throw new Exception(LocaleServer.getString("fileupload.label.createFailed","创建文件目录失败！"));
				}
			}
            bis = new BufferedInputStream(new FileInputStream(inputFile));           
			bos = new BufferedOutputStream(new FileOutputStream(new File(outputFile, fileNameValue)));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
			inputFile.delete();
			out.print("{Message:'"  + loginUser.getId() + "/" + CMyString.transDisplay(fileNameValue) + "'}");
        } catch (Exception e) {
			e.printStackTrace();
            throw e;
        } finally {
            if(bis != null){
                bis.close();
            }
            if(bos != null){
                bos.close();
            }
        }		
	}//end load to picture directory
%>

<%!
	private boolean isValidFile(String fileName){
		Pattern suffixPattern = Pattern.compile("(.)+\\.(jpg|bmp|gif|png|jpeg)$", Pattern.CASE_INSENSITIVE);
		if(suffixPattern.matcher(fileName).matches()){
			return true;
		}					
		return false;
	}
%>