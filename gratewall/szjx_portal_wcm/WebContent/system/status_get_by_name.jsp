<%
/** Title:			source_get_by_name.jsp
 *  Description:
 *		标准WCM5.2 页面，判断是否重名”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2005-8-11 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2005-8-11/2005-8-11
 *	Update Logs:
 *		CH@2005-8-11 created the file 
 *
 *  Parameters:
 *		see source_get_by_name.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
//4.初始化（获取数据）
	String strStatusName= currRequestHelper.getString("StatusName");
	
	WCMFilter filter = new WCMFilter("", "SNAME='"+CMyString.filterForSQL(strStatusName)+"'",
	"", "", 1);
	Statuses currStatuses = Statuses.openWCMObjs(loginUser, filter);
	Status currStatus = null;
	if(!currStatuses.isEmpty()){
		currStatus = (Status)currStatuses.getAt(0);
	}
	
	String sXML = "";
	if(currStatus != null){
		ObjToXmlConverter aXMLConvert = new ObjToXmlConverter();
		sXML = aXMLConvert.toXMLString(currStatus, null, "STATUSID");
	}
//7.结束
	out.clear();
%>
<%=sXML%>
	