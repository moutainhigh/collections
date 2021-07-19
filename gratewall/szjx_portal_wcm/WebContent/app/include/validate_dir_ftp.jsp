<%
/** Title:			validate_dir_ftp.jsp
 *  Description:
 *		校验ftp地址是否正确
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2005-5-31
 *  Vesion:			1.0
 *  Last EditTime:	2005-8-9
 *	Update Logs:
 *		WYH@2006.04.12	增加对FTP是否使用被动模式(passive mode:pasv)的设置
 *						@see publishdistirbution_addedit.jsp		
 *		FCR @ 2005-8-5 重新实现了检验代码，以实现对SFTP的支持。
 *		NZ@2005-5-31 created the file 
 *
 *  Parameters:
 *		see validate_dir_ftp.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.uftp.FtpConfig" %>
<%@ page import="com.trs.infra.util.uftp.FtpConnectionCache" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.io.File" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	out.clear();

//4.初始化(获取数据)	
	String strType		= CMyString.showNull(currRequestHelper.getString("Type"));
	String strServer	= CMyString.showNull(currRequestHelper.getString("Server"));
	String strUser		= CMyString.showNull(currRequestHelper.getString("User"));
	int    iPort		= currRequestHelper.getInt("Port", 0);
	String strPassword	= CMyString.showNull(currRequestHelper.getString("Password"));
	String strDataPath	= CMyString.showNull(currRequestHelper.getString("DataPath"));
	boolean  zAnonynous	= currRequestHelper.getBoolean("Anonymous", false);
	boolean  zPassiveMode = currRequestHelper.getBoolean("PassiveMode", false);

//5.权限校验

//6.业务代码

	FtpConfig	ftpConfig	= new FtpConfig();
	String[]	forReturn	= new String[1];
	String		sXmlInfo	= "";

	ftpConfig.setProtocol(strType);
	ftpConfig.setHost(strServer);
	ftpConfig.setPort(iPort);
	ftpConfig.setAnonymous(zAnonynous);
	ftpConfig.setUserName(strUser);
	ftpConfig.setPassWord(strPassword);
	ftpConfig.setRootPath(strDataPath);
	ftpConfig.setPassive(zPassiveMode);

	if (!FtpConnectionCache.verifyConfig(ftpConfig, forReturn))
	{
		if(true){
			throw new WCMException(com.trs.infra.util.ExceptionNumber.INFO_ERROR_MSG, LocaleServer.getString("error.label.connecterror", "连接到服务器时,发生错误。"),new Exception(forReturn[0]));
		}

		StringBuffer	sb	= new StringBuffer(512);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<FTPREPORT><PROPERTIES><ERRMSG>");
		sb.append("<![CDATA[");
		sb.append(forReturn[0]);
		sb.append("]]>");
		sb.append("</ERRMSG></PROPERTIES></FTPREPORT>"); 

		sXmlInfo	= sb.toString();
	}


//7.结束
%>
<%=sXmlInfo%>