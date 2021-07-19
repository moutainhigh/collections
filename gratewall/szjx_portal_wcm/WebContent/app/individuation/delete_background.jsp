<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.io.File" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../../include/public_server.jsp"%>

<% out.clear(); %>
<%=deleteBackground(loginUser, application, request)%>

<%!
	 private boolean deleteBackground(User loginUser, ServletContext application,
			HttpServletRequest request)throws Exception{		   
		String fileName = request.getParameter("fileName");
		//文件名为空
		if(fileName == null) return false;
		String currUserId = String.valueOf(loginUser.getId());
		//要删除的文件不属于当前用户
		if(!fileName.startsWith(currUserId + "/")) return false;
		String webpicPath = FilesMan.getFilesMan().getPathConfigValue(FilesMan.FLAG_WEBFILE,FilesMan.PATH_LOCAL);
		webpicPath = CMyString.setStrEndWith(webpicPath,'/');
        File file = new File(webpicPath+"images/login/background");
		file = new File(file, fileName);        
		if(file.isFile() && file.exists()){
			file.delete();
			return true;
		}
		return false;
    }
%>