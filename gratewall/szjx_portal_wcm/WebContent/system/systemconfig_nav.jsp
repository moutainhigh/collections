<%
/** Title:			systemconfig_nav.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“文档管理”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-11 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-11/2004-12-11
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
if (request.getProtocol().equals("HTTP/1.0"))
	response.setHeader("Pragma", "cache");
if (request.getProtocol().equals("HTTP/1.1"))
	response.setHeader("Cache-Control", "cache");
%>
<%!boolean IS_DEBUG = true;%>
<%
//4.初始化（获取数据）

//7.结束
	out.clear();
%>

<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<TR>
	<TD class="navigation_content_table">
	<!--WCM 导航树开始-->
	<script src="../js/CTRSTree.js"></script>
	<script src="../js/CWCMTreeDepends.js"></script>
	<script src="../js/CTRSTree_res_system.js"></script>
	<script src="../system/systemconfig_tree.jsp"></script>
	<!--WCM 导航树结束-->
	</TD>
	</TR>
</TABLE>