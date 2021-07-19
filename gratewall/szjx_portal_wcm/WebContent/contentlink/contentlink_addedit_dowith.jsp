<%--
/** Title:			contentlink_list.jsp
 *  Description:
 *		处理内容超链接的添加修改页面
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
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLink" %>
<%@ page import="com.trs.service.impl.ChannelService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nContentLinkId = currRequestHelper.getInt("ContentLinkId", 0);
	ContentLink currContentLink = null;
	if(nContentLinkId > 0){
		currContentLink = ContentLink.findById(nContentLinkId);
		if(currContentLink == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nContentLinkId+"]的内容超链接！");
		}
	}else{//nContentLinkId==0 create a new group
		currContentLink = ContentLink.createNewInstance();
	}
	
//5.权限校验

//6.业务代码
	try{
		currContentLink = (ContentLink)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nContentLinkId, ContentLink.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存内容超链接时因属性值不正确而失败中止！", ex);
	}

	ChannelService currChannelService = (ChannelService)DreamFactory.createObjectById("IChannelService");
	currChannelService.saveContentLink(currContentLink);


//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改内容超链接              :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
if(window.opener){
	window.opener.CTRSAction_refreshMe();
	window.opener.focus();
	window.close();
}else{
	window.returnValue = true;
	window.close();
}

</SCRIPT>
</BODY>
</HTML>