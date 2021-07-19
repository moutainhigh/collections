<%
/** Title:			user_select_top.jsp
 *  Description:
 *		标准WCM5.2 用户选择首页
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-26 20:17
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-26/2004-12-26
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see user_select_top.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>


<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <TITLE>TRS WCM 5.2 <%=LocaleServer.getString("user.label.select", "用户选择")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
  <LINK href="../style/style.css" rel="stylesheet" type="text/css">  
</HEAD>

<BODY>
 <%@include file="../include/public_client_normal.jsp"%>
<script src="../js/CWCMDialogHead.js"></script>
<script>WCMDialogHead.draw("<%=LocaleServer.getString("user.label.select", "用户选择")%>")</script>
</BODY>
</HTML>