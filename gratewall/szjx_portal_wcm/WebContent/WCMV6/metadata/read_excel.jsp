<% response.setContentType("application/vnd.ms-excel");%><%@ page import="com.trs.infra.support.file.FilesMan" %><%@ page import="com.trs.infra.util.CMyFile" %><%@ page import="com.trs.infra.common.WCMException" %><%@ page import="com.trs.infra.util.ExceptionNumber" %><%@ page import="com.trs.presentation.util.LoginHelper" %><%@ page import="com.trs.presentation.util.RequestHelper" %><%@ page import="com.trs.cms.auth.persistent.User" %><%@ page import="com.trs.cms.ContextHelper" %><%
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN, "用户未登录或登录超时！");
	}

//3.参数获取
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();

	int nViewId = currRequestHelper.getInt("ViewId", 0);
	String sFileName = com.trs.components.metadata.definition.MetaDataDefHelper.getViewApplicationPath(nViewId) + "data.xml";	

//5.权限校验
	//TODO
//6.业务代码
	//String sFilePath = "";
	//String sSrcFile	 = "";
	try{
		java.io.File f = new java.io.File(sFileName);
		if(f.exists()){
			sFileName = f.getAbsolutePath();
		}else{
			FilesMan currFilesMan = FilesMan.getFilesMan();
			sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_LOCAL) + sFileName;
		}
	}catch(Exception ex){
		throw new WCMException("传入视图关联的XML文件不存在！[ViewId="+nViewId+"]", ex);
	}
	
//8.输出
	//response.reset();//wenyh@2007-07-23 reset the reponse first.

	// 设置响应头和下载保存的文件名
	response.setHeader("Content-Disposition", "attachment;filename=\"data.xls\""); 
	
	// 打开指定文件的流信息 
	java.io.FileInputStream fileInputStream = null; 
	java.io.OutputStream os = null;
	try{
		fileInputStream = new java.io.FileInputStream( sFileName );
		// 写出流信息 
		byte buffer[] = new byte[65000];
		int i; 
		os = response.getOutputStream();
		while( (i=fileInputStream.read(buffer, 0, 65000))>0 ) { 
			os.write(buffer, 0, i);
		} 
		os.flush();
	}catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "写文件失败", ex);
	}finally{
		if(fileInputStream!=null){
			try{
				fileInputStream.close(); 
				fileInputStream = null;
			}catch(Exception e){
				//Ignore.
			}
		}
		if(os != null){
			try{
				os.close();
				os = null;
			}catch(Exception e){
				//Ignore.
			}
		}
		//wenyh@2007-08-23 17:30 去掉out.clear(),不需要
		//out.clear();
	}
%>