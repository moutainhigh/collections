<%
/** Title:			group_select_left.jsp
 *  Description:
 *		WCM5.2 页面，用于“对话框选择组织（单选/多选）”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-13 15:11
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-13/2004-12-13
 *	Update Logs:
 *		WSW@2004-12-13 created the file 
 *  //TODO 预先选择、树状输出
 *
 *  Parameters:
 *		see group_select_left.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.common.PageViewConstants" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%!boolean IS_DEBUG = true;%>
<%
//4.初始化（获取数据）
	int nTreeType = currRequestHelper.getInt("TreeType", 0);

//5.权限校验

//6.业务代码	
	

//7.结束
	out.clear();
%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="group_select_left.jsp.title">TRS WCM 组织的选择::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_addedit.jsp中 --->
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></script>

</head>
<BODY style="margin:0px">	
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">
<!--~--- ROW3 ---~-->
<TR>
<TD valign="top" bgcolor="white" height="98%">	
	<!--WCM 栏目树开始-->
	<script src="../js/wcm52/CWCMTreeDepends.js"></script>
	<script src="../js/wcm52/CTRSTree_res_auth.js"></script>
	<script src="./group_tree.jsp?OperType=<%=PageViewConstants.TREE_OPERATION_GROUP_SEL%>&TreeType=<%=nTreeType%>&ShowAll=1&TreeWidth=150"></script>
	<!--WCM 栏目树结束-->
</TD>
</TR>
<!--~- END ROW3 -~-->
</TABLE>
</BODY>
</HTML>