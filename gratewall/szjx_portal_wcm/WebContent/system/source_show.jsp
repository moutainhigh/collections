<%--
/** Title:			test.jsp
 *  Description:
 *		文档来源的显示页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-01 15:34:11
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2005-04-01
 *	Update Logs:
 *		CH@2005-04-01 产生此文件
 *
 *  Parameters:
 *		see test.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Security" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nSourceId = currRequestHelper.getInt("SourceId", 0);
	Source currSource = Source.findById(nSourceId);
	if(currSource == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "参数有误，没有找到ID为["+nSourceId+"]的文档来源！");
	}
	Security currSecurity = currSource.getSecurity();

//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("document.label.info_of_source", "文档来源信息")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
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
		WCMDialogHead.draw("<%=LocaleServer.getString("document.label.info_of_source", "文档来源信息")%>",true);
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
		<TD NOWRAP> <%=LocaleServer.getString("document.label.name_of_source", "来源名称")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSource.getPropertyAsString("SRCNAME"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.desc_of_source", "来源描述")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSource.getPropertyAsString("SRCDESC"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.link_of_source", "来源链接")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSource.getPropertyAsString("SRCLINK"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.cruse_of_sourcer", "创建用户")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSource.getPropertyAsString("CRUSER"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.crtime_of_source", "创建时间")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSource.getPropertyAsString("CRTIME"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.docsecurity", "安全级别")%></TD>
		<%if(currSecurity==null) {%>
		<TD>&nbsp;无</TD>
		<%} else {%>
		<TD>&nbsp;<span title="<%=PageViewUtil.toHtml(currSecurity.getDesc())%>"><%=PageViewUtil.toHtml(currSecurity.getDisp())%></span></TD>
		<%}%>
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