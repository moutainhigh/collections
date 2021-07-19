<%response.setContentType("application/vnd.ms-excel");%>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<%
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN,  LocaleServer.getString("read_excel.notLogging","用户未登录或登录超时！"));
	}
	int nViewId = 0;
	try{
		nViewId = Integer.parseInt(request.getParameter("ViewId"));
	}catch(Exception e){}
	String sFileName = com.trs.components.metadata.definition.MetaDataDefHelper.getViewApplicationPath(nViewId) + "data.xml";	

	// 设置响应头和下载保存的文件名
	response.reset();//wenyh@2008-04-17 reset the reponse first.
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=\"data.xls\"");

	// 打开指定文件的流信息 
	java.io.FileInputStream fileInputStream = null;
	java.io.OutputStream outx = null;
	try{
		fileInputStream = new java.io.FileInputStream( sFileName );
		// 写出流信息 
		byte buffer[] = new byte[65000];
		int i; 
		outx = response.getOutputStream();
		while( (i=fileInputStream.read(buffer, 0, 65000))>0 ) { 
			outx.write(buffer, 0, i);
		}
		outx.flush();
	}catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("read_excel.writedoc.failed","写文件失败"), ex);
	}finally{
		if(fileInputStream!=null){
			fileInputStream.close(); 
		}
		if(outx != null){
			outx.close();
		}
	}
%>