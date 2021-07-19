<% response.setContentType("image/gif");%><%@ page import="com.trs.infra.support.file.FilesMan" %><%@ page import="com.trs.infra.util.CMyFile" %><%@ page import="com.trs.infra.common.WCMException" %><%@ page import="com.trs.infra.util.ExceptionNumber" %><%@ page import="com.trs.presentation.util.LoginHelper" %><%@ page import="com.trs.presentation.util.RequestHelper" %><%@ page import="com.trs.cms.auth.persistent.User" %><%@ page import="com.trs.cms.ContextHelper" %><%
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN, "用户未登录或登录超时！");
	}

//3.参数获取
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();

	String sFileName = currRequestHelper.getString("FileName");
	if(sFileName == null || sFileName.trim().length()==0)
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入文件名为空！");		
	

//5.权限校验
	//TODO
//6.业务代码
	//String sFilePath = "";
	//String sSrcFile	 = "";
	try{
		FilesMan currFilesMan = FilesMan.getFilesMan();
		sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_LOCAL) + sFileName;
	}catch(Exception ex){
		
	}
	
//8.输出
	
	// 打开指定文件的流信息 
	java.io.FileInputStream fileInputStream = null; 
	try{
		fileInputStream = new java.io.FileInputStream( sFileName );
		// 写出流信息 
		byte buffer[] = new byte[65000];
		int i; 
		while( (i=fileInputStream.read(buffer, 0, 65000))>0 ) { 
			response.getOutputStream().write(buffer, 0, i);
		} 
	}catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "写文件失败", ex);
	}finally{
		if(fileInputStream!=null){
			fileInputStream.close(); 
		}
		out.flush();
//		out.clear();
	}
%>