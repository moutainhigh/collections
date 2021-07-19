<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="java.io.File" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%
	request.setCharacterEncoding("ISO8859-1");
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN, LocaleServer.getString("infoview.export.xsnfile.loginerror", "用户未登录或登录超时！"));
	}
	int nInfoViewId = Integer.parseInt(request.getParameter("InfoViewId"));
	if(nInfoViewId < 0){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("infoview.export.xsnfile.infoviewid", "传入的表单ID必须大于0！"));
	}

	InfoView oInfoView = InfoView.findById(nInfoViewId);
	String sInfoPathName = oInfoView.getInfoPathFile();
	if(sInfoPathName == null || sInfoPathName.trim().length()==0){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("infoview.export.xsnfile.filekong", "传入文件名为空！"));
	}
	FilesMan currFilesMan = FilesMan.getFilesMan();
	String sBasePath = currFilesMan.mapFilePath(sInfoPathName, FilesMan.PATH_LOCAL);
	
	String sFilePath = sBasePath + File.separator + sInfoPathName;

	String sSrcFile = "";
	sSrcFile = new String(sInfoPathName.getBytes("ISO8859-1"), "UTF-8");

	// 设置响应头和下载保存的文件名
	response.reset();
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(sSrcFile, "UTF-8") + "\"");
	
	java.io.FileInputStream fileInputStream = null;
	java.io.OutputStream outx = null;
	try{
		fileInputStream = new java.io.FileInputStream(sFilePath);	
		byte buffer[] = new byte[65000];
		int i;
		outx = response.getOutputStream();
		while((i = fileInputStream.read(buffer, 0, 65000))>0){
			outx.write(buffer, 0, i);
		}
	}catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("infoview.export.xsnfile.writefileerr", "写文件失败"),  ex);
	}finally{
		if(fileInputStream!=null){
			fileInputStream.close();
		}
		if(outx != null){
			outx.close();
		}
	}
%>