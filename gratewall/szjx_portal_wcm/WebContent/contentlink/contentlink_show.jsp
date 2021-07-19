<%--
/** Title:			contentlink_list.jsp
 *  Description:
 *		内容超链接的显示页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-01 15:08:22
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
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nContentLinkId = currRequestHelper.getInt("ContentLinkId", 0);
	ContentLink currContentLink = ContentLink.findById(nContentLinkId);
	if(currContentLink == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "参数有误，没有找到ID为["+nContentLinkId+"]的内容超链接！");
	}

//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("contentlink.label.info", "内容超链接信息")%>              :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_help.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function closeWindow(){
	window.returnValue = true;
	window.close();
}
</SCRIPT>
</HEAD>

<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("contentlink.label.info", "内容超链接信息")%>");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD valign="top">
	<TABLE width="100%" border="0" cellpadding="2" height="100%" cellspacing="0" bgcolor="#FFFFFF">
	<TR>
	<TD align="left" valign="top" height="10">
		<SCRIPT src="../js/CTRSButton.js"></SCRIPT>
		<script>
			//定义一个单行按钮
			var oTRSButtons = new CTRSButtons();
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.close", "关闭")%>", "closeWindow();", "../images/button_close.gif", "<%=LocaleServer.getString("system.tip.close", "关闭页面")%>");
			oTRSButtons.draw();
		</script>
	</TD>
	</TR>
	<TR>
	<TD align="left" valign="top">
		<TABLE width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="A6A6A6">
		
		<TR bgcolor="#F6F6F6">
		<TD><%=LocaleServer.getString("contentlink.label.name", "名称")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKNAME"))%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD><%=LocaleServer.getString("contentlink.label.title", "标题")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKTITLE"))%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD><%=LocaleServer.getString("contentlink.label.url", "URL")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKURL"))%></TD>
		</TR>
		

		</TABLE>
	</TD>
	</TR>
	</TABLE>
</TD>
</TR>
</TABLE>
</BODY>
</HTML>