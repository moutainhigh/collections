<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.CMyUnZipFile" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.infra.util.soap.OcrSoapClient" %>
<%@ page import="java.util.Enumeration,java.util.HashMap,java.util.Hashtable" %>
<%@ page import="java.io.File,java.io.IOException" %>
<%@ page import="java.rmi.RemoteException,javax.xml.rpc.ServiceException" %>
<%@ page import="com.trs.cms.content.HTMLContent" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.net.ftp.FtpURL" %>
<%@ page import="com.trs.net.ftp.impl.FtpClientImpl" %>
<%@include file="../../../../../app/include/public_server.jsp"%>
<%@include file="../../../../../app/include/file_upload_judgment.jsp"%>
<%!
	String					mFtpHost			= "192.9.200.97";
	int						nFtpPort			= 21;
	String					mFtpUploadPath		= "SourFiles";
	String					mFtpDownloadPath	= "DestFiles";
	String					mFtpUserName		= "trs";
	String					mFtpPwd				= "trs";
	FtpClientImpl			m_oFtpClient		= null;

	String					mSoapUrl			= "http://127.0.0.1:8000";
	String					mSoapUrn			= "urn:Office2HTML";
	String					mSoapUserName		= "";
	String					mSoapPwd			= "";
	String					mSoapEncoding		= "GBK";

	private FtpClientImpl openFtpClient() throws IOException {
		FtpURL url = new FtpURL("ftp://trs:trs@abc:21");
		url.setHost(mFtpHost);
		url.setPort(nFtpPort);
		url.setUserName(mFtpUserName);
		url.setPassWord(mFtpPwd);
		m_oFtpClient = new FtpClientImpl(url);
		m_oFtpClient.open();
		return m_oFtpClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trs.service.IOfficeDocumentService#ftpUpload(java.lang.String)
	 */
	void ftpUpload(String _sFileName) throws IOException {
		try {
			m_oFtpClient = openFtpClient();
			File fSaveFile = new File(_sFileName);
			String sShortFileName = CMyFile.extractFileName(_sFileName, null);
			m_oFtpClient.upload(fSaveFile, mFtpUploadPath + "/" + sShortFileName);
			fSaveFile.delete();
		}
		finally {
			if (m_oFtpClient != null)
				m_oFtpClient.close();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trs.service.IOfficeDocumentService#ftpDownload(java.lang.String, java.lang.String)
	 */
	void ftpDownload(String _sFileName, String _sDestFileName) throws IOException {
		try {
			m_oFtpClient = openFtpClient();
			File fDestZip = new File(_sDestFileName);
			m_oFtpClient.download(mFtpDownloadPath + "/" + _sFileName, fDestZip);
			m_oFtpClient.deleteFile(mFtpDownloadPath + "/" + _sFileName);
		}
		finally {
			if (m_oFtpClient != null)
				m_oFtpClient.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trs.service.IOfficeDocumentService#renderHtmlFormat(java.lang.String)
	 */
	String renderHtmlFormat(String _sZipFile) throws CMyException {
		String sUnZipFile = CMyFile.extractFilePath(_sZipFile);
		sUnZipFile += "temp" + System.currentTimeMillis() + java.io.File.separator;
		CMyUnZipFile uzf = new CMyUnZipFile(_sZipFile, sUnZipFile);
		try {
			uzf.doUnZipAnd();
			CMyFile.deleteFile(_sZipFile);
		}
		catch (CMyException e) {
			throw new WCMException(ExceptionNumber.ERR_ZIP_UNZIP, "Zip文件解压失败!", e);
		}
		//
		Hashtable allFiles = uzf.getAllFileNameIndex();
		String sTmpFile = null;
		String sShortFileName = CMyFile.extractFileName(_sZipFile, null);
		String sHtmlFileName = sShortFileName.replaceAll("\\..*$", ".html");
		HashMap hmSrcFile = new HashMap();
		Enumeration allFileKeys = allFiles.keys();
		String sHtmlFileNameAbs = null;
		while (allFileKeys.hasMoreElements()) {
			sTmpFile = (String) allFileKeys.nextElement();
			if (!sTmpFile.equalsIgnoreCase(sHtmlFileName)) {
				hmSrcFile.put(sTmpFile.toLowerCase(), allFiles.get(sTmpFile));
			}
			else {
				sHtmlFileNameAbs = (String) allFiles.get(sTmpFile);
			}
		}
		String sContent = CMyFile.readFile(sHtmlFileNameAbs, mSoapEncoding);
		String sResult = HTMLContent.saveHTMLContentFromOut(sContent, hmSrcFile);
		HTMLContent aHTMLContent = new HTMLContent(sResult);
		sResult = aHTMLContent.parseHTMLContent(null);
		new File(sHtmlFileNameAbs).delete();

		return sResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trs.service.IOfficeDocumentService#soapConvert(java.lang.String)
	 */
	String soapConvert(String _sFileName) throws RemoteException, ServiceException {
		OcrSoapClient soapClient = new OcrSoapClient();
		soapClient.setServer(mSoapUrl, mSoapUserName, mSoapPwd);
		soapClient.setUrn(mSoapUrn);
		String sDestFile = soapClient.ConvertOfficeFile(_sFileName);
		return sDestFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.trs.service.IOfficeDocumentService#convert2Html(java.lang.String)
	 */
	String convert2Html(String _sFileName) throws Exception {
		String sShortFileName = CMyFile.extractFileName(_sFileName, null);
		String sUploadFilePath = CMyFile.extractFilePath(_sFileName);
		// 1
		ftpUpload(_sFileName);
		// 2
		String sZipFileName = soapConvert(sShortFileName);
		// 3
		ftpDownload(sZipFileName, sUploadFilePath + sZipFileName);
		// 4
		return renderHtmlFormat(sUploadFilePath + sZipFileName);
	}

%>
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
	String sHtmlContent = convert2Html(sSaveFile);
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
<%@include file="../../../../../app/include/fileupload_error_message_include.jsp"%>
<%
}
%>