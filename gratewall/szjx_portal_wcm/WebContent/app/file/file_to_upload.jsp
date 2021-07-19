<%
/** Title:			file_upload.jsp
 *  Description:
 *		WCM5.2 文件上传处理页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2003-12-24
 *  Vesion:			1.0
 *  Last EditTime:	2003-12-24 2003-12-24
 *	Update Logs:
 *		CH@2003-12-24 created file
 *
 *  Parameters:
 *		see file_upload.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
//4.初始化（获取数据）
	String sFileNames = CMyString.showNull(request.getParameter("FileNames"));
	String[] aFileNames = sFileNames.split(",");
	
	StringBuffer sbUploadFileNames = new StringBuffer(sFileNames.length());

	FilesMan oFilesMan	= FilesMan.getFilesMan();

	for (int i = 0; i < aFileNames.length; i++) {
		if (CMyString.isEmpty(aFileNames[i])){
			continue;
		}
		sbUploadFileNames.append("'"+oFilesMan.copyWCMFile(aFileNames[i], FilesMan.FLAG_UPLOAD)+"'").append(",");
	}
	if(sbUploadFileNames.length() > 0){
		sbUploadFileNames.setLength(sbUploadFileNames.length() - 1);
	}
//7.结束
out.clear();%>[<%=sbUploadFileNames.toString()%>]