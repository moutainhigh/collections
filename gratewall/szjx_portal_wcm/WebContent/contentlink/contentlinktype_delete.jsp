<%--
/** Title:			contentlinktype_delete.jsp
 *  Description:
 *		删除ContentLinkType的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WenYehui
 *  Created:		2006-12-11 13:13:09
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-11 / 2006-12-11
 *	Update Logs:
 *		WenYehui@2006-12-11 产生此文件
 *
 *  Parameters:
 *		see contentlinktype_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	String sContentLinkTypeIds = currRequestHelper.getString("ContentLinkTypeIds");

//5.权限校验

//6.业务代码
	/** TODO 建议改为向Service发出请求
	IContentLinkTypeService currContentLinkTypeService = (IContentLinkTypeService)DreamFactory.createObjectById("IContentLinkTypeService");
	currContentLinkTypeService.delete(sContentLinkTypeIds);
	**/
	ContentLinkTypes currContentLinkTypes = ContentLinkTypes.findByIds(loginUser, sContentLinkTypeIds);
	currContentLinkTypes.removeAll(true);
	

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 删除ContentLinkType</TITLE>
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