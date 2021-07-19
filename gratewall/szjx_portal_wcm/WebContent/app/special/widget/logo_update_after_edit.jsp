<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>

<%
	// 获取参数
	String fileName = request.getParameter("fileName");
	String pathFlag = request.getParameter("pathFlag");
	pathFlag = CMyString.isEmpty(pathFlag)?FilesMan.FLAG_PROTECTED:pathFlag;
	if(CMyString.isEmpty(fileName)){
		out.println("{Error:''}");
	}else{
		// 拷贝这个文件到目标目录，并更换名字
		String sSaveFileName = FilesMan.getFilesMan().moveWCMFile(fileName,pathFlag,false);
		out.println("{Message:'"+sSaveFileName+"'}");
	}
%>