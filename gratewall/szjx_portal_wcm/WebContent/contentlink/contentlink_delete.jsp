<%--
/** Title:			contentlink_list.jsp
 *  Description:
 *		删除内容超链接的页面
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
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinks" %>
<%@ page import="com.trs.service.impl.ChannelService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	String sContentLinkIds = currRequestHelper.getString("ContentLinkIds");

//5.权限校验

//6.业务代码
	ChannelService currChannelService = (ChannelService)DreamFactory.createObjectById("IChannelService");
	ContentLinks currContentLinks = ContentLinks.findByIds(loginUser, sContentLinkIds);
	currChannelService.deleteContentLinks(currContentLinks);
	

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 删除内容超链接::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
window.returnValue = true;
window.close();
</SCRIPT>
</BODY>
</HTML>