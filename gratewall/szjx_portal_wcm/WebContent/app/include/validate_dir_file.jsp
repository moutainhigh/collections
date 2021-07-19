<%
/** Title:			validate_dir_file.jsp
 *  Description:
 *		校验ftp地址是否正确
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2005-5-31
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		NZ@2005-5-31 created the file 
 *
 *  Parameters:
 *		see validate_dir_file.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%> 
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.io.File" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	out.clear();
//4.初始化(获取数据)	
	String strDirection = CMyString.showNull(currRequestHelper.getString("Direction"));

//5.权限校验
	
//6.业务代码
    String sXmlInfo = "";
    String sErrMsg="";
	try {
		File file = new File(strDirection);
		if(!file.isDirectory()){
			sErrMsg = LocaleServer.getString("validate_dir_file.label.noValidRoot", "指定的路径不是一个有效的目录");
		}
	}
	catch(Exception ex) {
		
		sErrMsg = ex.getMessage();
		out.print(sErrMsg);
	}
	
	if(sErrMsg.length() != 0){
		sXmlInfo += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		sXmlInfo += "<FTPREPORT><PROPERTIES><ERRMSG>";
		sXmlInfo += "<![CDATA[" + sErrMsg + "]]>";
		sXmlInfo += "</ERRMSG></PROPERTIES></FTPREPORT>";
	}
	   
//7.结束
%>
<%=sXmlInfo%>