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
<%@ page import="com.eprobiti.trs.TRSConnection" %>
<%@ page import="com.eprobiti.trs.TRSView" %>
<%@ page import="com.trs.components.wcm.content.trsserver.DatasFromTRSServer" %>
getValidConnection
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
	DatasFromTRSServer newServer = new DatasFromTRSServer();
	TRSConnection currConnection = newServer.getValidConnection(strServer,iPort + "",strUser,strPassword);
	sXmlInfo += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	sXmlInfo += "<FTPREPORT><PROPERTIES><ERRMSG>";
	sXmlInfo += "<![CDATA[";
	if(currConnection == null){
		sXmlInfo += "error";		
	}else{
		TRSView[] trsView = currConnection.getViews("");
		String sDBName = "";
		if(trsView.length > 0){
			for(int i = 0 ; i < trsView.length ; i++){
				sDBName += (trsView[i].getDatabases() + ",");
				sXmlInfo += (trsView[i].getName() + ",");
			}
			sXmlInfo = sXmlInfo.substring(0,sXmlInfo.length() -1) + "-" + sDBName.substring(0,sDBName.length() -1);
		}
	}
	sXmlInfo += "]]>";
	sXmlInfo += "</ERRMSG></PROPERTIES></FTPREPORT>";

//7.结束
%>
<%=sXmlInfo%>