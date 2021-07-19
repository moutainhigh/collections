<%--
/** Title:			contentlink_list.jsp
 *  Description:
 *		内容超链接的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-01 15:08:21
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2005-04-01
 *	Update Logs:
 *		NZ@2005-04-01 产生此文件
 *
 *  Parameters:
 *		see contentlink_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLink" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinks" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyString" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	//int nSiteId = currRequestHelper.getInt("SiteId", 0);
	int nLinkTypeId = currRequestHelper.getInt("ContentLinkTypeId", 0);
	String sLinkName = currRequestHelper.getString("LinkName");

//5.权限校验

//6.业务代码
	//String sWhere = "SiteId=" + nSiteId + " AND LinkName='"+CMyString.filterForSQL(sLinkName)+"'";
	String sWhere = "ContentLinkType=? AND LinkName=?";
	WCMFilter filter = new WCMFilter("", sWhere, "", "CONTENTLINKID,SITEID,LINKNAME,LINKURL", 1);
	filter.addSearchValues(0,nLinkTypeId);
	filter.addSearchValues(1,sLinkName);
	ContentLinks allLinks = ContentLinks.openWCMObjs(loginUser, filter);
	ContentLink currLink = null;
	if(allLinks != null && !allLinks.isEmpty()){
		currLink = (ContentLink) allLinks.getAt(0);
	}

	ObjToXmlConverter aXMLConvert = new ObjToXmlConverter();

//7.结束
	out.clear();
%>
<%
	if(currLink != null){
		out.println(aXMLConvert.toXMLString(currLink, null, "CONTENTLINKID,SITEID,LINKNAME,LINKURL"));
	} else {
		out.println("null");
	}
	if(true){
		return;
	}
%>