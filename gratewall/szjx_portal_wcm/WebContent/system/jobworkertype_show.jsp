<%--
/** Title:			jobworkertype_show.jsp
 *  Description:
 *		调度类型的显示页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-06 00:17:47
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		CH@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see jobworkertype_show.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nJobWorkerTypeId = currRequestHelper.getInt("JobWorkerTypeId", 0);
	JobWorkerType currJobWorkerType = JobWorkerType.findById(nJobWorkerTypeId);
	if(currJobWorkerType == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "参数有误，没有找到ID为["+nJobWorkerTypeId+"]的调度类型！");
	}

//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 调度类型信息:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
		WCMDialogHead.draw("调度类型信息",true);
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
		<TD> 名称</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currJobWorkerType.getPropertyAsString("OPNAME"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD> 描述</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currJobWorkerType.getPropertyAsString("OPDESC"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD> 参数</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currJobWorkerType.getPropertyAsString("PARAM"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD> Bean</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currJobWorkerType.getPropertyAsString("OPBEAN"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD> 创建用户</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currJobWorkerType.getPropertyAsString("CRUSER"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD> 创建时间</TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currJobWorkerType.getPropertyAsString("CRTIME"))%></TD>
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