<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM5.2 模板语法检测
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see template_parser.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.util.CmsObjUnlockTools" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
	int nObjType = currRequestHelper.getInt("ObjType",0);
	int nObjId = currRequestHelper.getInt("ObjId",0); 
	
	CMSObj lockedObj = CmsObjUnlockTools.findLockedObj(nObjType,nObjId);
	
	//1.权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起,您没有权限强制解锁对象!只有管理员可以使用该工具.");
	}
	
	CmsObjUnlockTools.forceUnLocke(loginUser,lockedObj);
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 对象强制解锁工具::::::::::::::::::::::::::::::::::::::::::..</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>
</HTML>