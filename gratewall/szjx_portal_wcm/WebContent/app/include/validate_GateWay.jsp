<%
/** Title:			validate_TRSServer.jsp
 *  Description:
 *		校验ftp地址是否正确
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2005-5-31
 *  Vesion:			1.0
 *  Parameters:
 *		see validate_TRSServer.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.trs.gateway.client.GWConnection" %>
<%@ page import="com.trs.gateway.client.GWManager" %>
<%@ page import="com.trs.gateway.client.GWConstants" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	out.clear();

//4.初始化(获取数据)	
	String strServer	= CMyString.showNull(currRequestHelper.getString("Server"));
	String strUser		= CMyString.showNull(currRequestHelper.getString("User"));
	int    iPort		= currRequestHelper.getInt("Port", 0);
	String strPassword	= CMyString.showNull(currRequestHelper.getString("Password"));
	String sXmlInfo = "";

//5.权限校验

//6.业务代码
	GWConnection gwconnection = null;
	try{
		Properties properties = new Properties();
		// 网关连接参数
		properties.setProperty(GWConstants.GW_HOST, strServer);
		properties.setProperty(GWConstants.GW_PORT,
				String.valueOf(iPort));
		properties.setProperty(GWConstants.GW_USERNAME,
				strUser);
		properties.setProperty(GWConstants.GW_PASSWORD,
				strPassword);

		gwconnection = GWManager.getConnection(properties);
	}catch(Exception e){
	}
	sXmlInfo += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	sXmlInfo += "<FTPREPORT><PROPERTIES><ERRMSG>";
	sXmlInfo += "<![CDATA[";
	if(gwconnection == null){
		sXmlInfo += "error";		
	}else{
		sXmlInfo += "success";
	}
	sXmlInfo += "]]>";
	sXmlInfo += "</ERRMSG></PROPERTIES></FTPREPORT>";

//7.结束
%>
<%=sXmlInfo%>