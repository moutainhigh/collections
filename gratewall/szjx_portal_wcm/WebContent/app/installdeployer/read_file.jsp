<%@ page import="com.trs.infra.support.file.FilesMan" %><%@ page import="com.trs.infra.util.CMyFile" %><%@ page import="com.trs.infra.common.WCMException" %><%@ page import="com.trs.infra.util.ExceptionNumber" %><%@ page import="com.trs.presentation.util.LoginHelper" %><%@ page import="com.trs.presentation.util.RequestHelper" %><%@ page import="com.trs.cms.auth.persistent.User" %><%@ page import="com.trs.cms.ContextHelper" %><%
	request.setCharacterEncoding("ISO8859-1");
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN, "用户未登录或登录超时！");
	}
	String sFileName = request.getParameter("FileName");
	String sDownName = request.getParameter("DownName");
	if(sFileName == null || sFileName.trim().length()==0)
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入文件名为空！");
		
	if(sDownName==null || "".equals(sDownName)) {
		sDownName = sFileName;
	}

//5.权限校验
	//TODO
//6.业务代码
	String sFilePath = "";
	String sSrcFile	 = "";
	FilesMan currFilesMan = FilesMan.getFilesMan();
	sFilePath = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_LOCAL);
	
	sSrcFile = new String(sDownName.getBytes("ISO8859-1"), "UTF-8");
//8.输出
	// 设置响应头和下载保存的文件名
	response.reset();//wenyh@2008-04-17 reset the reponse first.
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(sSrcFile, "UTF-8") + "\"");

	// 打开指定文件的流信息 
	java.io.FileInputStream fileInputStream = null;
	java.io.OutputStream outx = null;
	try{
		fileInputStream = new java.io.FileInputStream( sFilePath+sFileName );
		// 写出流信息 
		byte buffer[] = new byte[65000];
		int i; 
		outx = response.getOutputStream();
		while( (i=fileInputStream.read(buffer, 0, 65000))>0 ) { 
			outx.write(buffer, 0, i);
		} 
	}catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "写文件失败", ex);
	}finally{
		if(fileInputStream!=null){
			fileInputStream.close(); 
		}
		if(outx != null){
			outx.close();
		}
//		out.flush();
	}
%>