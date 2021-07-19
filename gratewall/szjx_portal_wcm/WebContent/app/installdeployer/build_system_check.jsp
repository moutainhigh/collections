<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行政府信息公开服务系统的创建！");
	}

	String sDeleteFileName = request.getParameter("fileName");
	if(sDeleteFileName != null){
		//删除指定的用户信息文件
		deleteUserInfoFile(sDeleteFileName);
		return;
	}

	//获取出错信息
	String sErrorInfo = (String)session.getAttribute("ErrorInfo");
	if(sErrorInfo != null){
		session.removeAttribute("ErrorInfo");
		out.clear();
		out.println("{errorInfo:'" + CMyString.filterForJs(sErrorInfo) + "'}");
		return;
	}

	//获取生成的用户信息
	String sUserInfo = (String)session.getAttribute("UserInfo");
	if(sUserInfo != null){
		session.removeAttribute("UserInfo");
		String sFileName = saveUserInfoFile(sUserInfo);
		out.clear();
		out.println("{fileName:'" + sFileName + "',userInfo:'" + CMyString.filterForJs(sUserInfo) + "'}");
		//保存用户信息文件到
		return;
	}

	//还没有处理完成
	out.clear();
	out.println("{isRunning:true}");
%>

<%!
	/**
	*保存用户的信息到wcm系统的用户临时文件目录中
	*/
	private String saveUserInfoFile(String sUserInfoContent)throws Exception{
		FilesMan oFilesMan = FilesMan.getFilesMan();
		String sFileName = oFilesMan.getNextFilePathName(FilesMan.FLAG_USERTEMP, ".txt");
		String sContent = sUserInfoContent.replaceAll("<br>", "\n\r").replaceAll("<BR>", "\n\r").replaceAll("<br/>", "\n\r");
		CMyFile.writeFile(sFileName, sContent);
		return CMyFile.extractFileName(sFileName);
	}

	/**
	*删除用户信息文件
	*/
	private void deleteUserInfoFile(String sUserInfoFileName){
		try{
			
			FilesMan oFilesMan = FilesMan.getFilesMan();
			String sAbsoluteFilePath = oFilesMan.mapFilePath(sUserInfoFileName, FilesMan.PATH_LOCAL);
			CMyFile.deleteFile(sAbsoluteFilePath + sUserInfoFileName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
%>