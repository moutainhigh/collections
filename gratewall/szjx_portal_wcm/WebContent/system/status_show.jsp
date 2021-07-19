<%--
/** Title:			status_show.jsp
 *  Description:
 *		文档状态的显示页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 15:31:19
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see status_show.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nStatusId = currRequestHelper.getInt("StatusId", 0);
	Status currStatus = Status.findById(nStatusId);
	if(currStatus == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "参数有误，没有找到ID为["+nStatusId+"]的文档状态！");
	}

//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("document.label.info_of_status", "文档状态信息")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
		WCMDialogHead.draw("<%=LocaleServer.getString("document.label.info_of_status", "文档状态信息")%>",true);
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
		<TD NOWRAP> <%=LocaleServer.getString("document.label.rightindex_of_status", "权限索引")%></TD> <TD>&nbsp;<%=currStatus.getRightIndex()%></TD>
		</TR>
		
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.name_of_status", "名称")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currStatus.getName())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.desc_of_status", "说明")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currStatus.getDesc())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.show_of_status", "显示")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currStatus.getDisp())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.isused_of_status", "是否使用")%></TD> <TD>&nbsp;<%=currStatus.isUsed()?"是":"否"%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.cruser_of_status", "创建用户")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currStatus.getPropertyAsString("CRUSER"))%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("document.label.crtime_of_status", "创建时间")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currStatus.getPropertyAsString("CRTIME"))%></TD>
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