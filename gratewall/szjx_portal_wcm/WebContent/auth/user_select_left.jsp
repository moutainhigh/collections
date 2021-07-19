<%
/** Title:			user_select_left.jsp
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
 *		see user_select_left.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.common.PageViewConstants" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
	int nTreeType = currRequestHelper.getInt("TreeType", 0);
%>
<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE>TRS WCM 5.2 用户选择树::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
  <LINK href="../style/style.css" rel="stylesheet" type="text/css">
  <%@include file="../include/public_client_normal.jsp"%>
</HEAD>

<BODY>
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
<!--~--- ROW1 ---~-->
<TR>
    <TD align="left" valign="top" bgcolor="#FFFFFF">
		<script src="../js/CWCMTreeDepends.js"></script>
		<script src="../js/CTRSTree_res_auth.js"></script>
		<script src="../auth/group_tree.jsp?OperType=<%=PageViewConstants.TREE_OPERATION_GRPUSER_SEL_SPECIAL%>&TreeType=<%=nTreeType%>&ShowAll=1"></script>
	 </TD>
</TR>
<!--~- END ROW1 -~-->
</TABLE>
<!--~ END TABLE1 ~-->
</BODY>
</HTML>