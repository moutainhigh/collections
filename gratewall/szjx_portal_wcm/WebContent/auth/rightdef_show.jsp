<%--
/** Title:			rightdef_show.jsp
 *  Description:
 *		权限的显示页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 11:37:00
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see rightdef_show.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nRightDefId = currRequestHelper.getInt("RightDefId", 0);
	RightDef currRightDef = RightDef.findById(nRightDefId);
	if(currRightDef == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "参数有误，没有找到ID为["+nRightDefId+"]的权限！");
	}

//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("auth.label.info", "权限信息")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
		WCMDialogHead.draw("<%=LocaleServer.getString("auth.label.info", "权限信息")%>",true);
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
		<TD NOWRAP> <%=LocaleServer.getString("auth.label.objtype", "对象类型")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml((currRightDef.getObjType()==1)?"系统属性":WCMTypes.getObjName(currRightDef.getObjType(), true))%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("auth.label.index", "权限索引")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currRightDef.getPropertyAsString("RIGHTINDEX"))%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("auth.label.name", "权限名称")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currRightDef.getPropertyAsString("RIGHTNAME"))%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("auth.label.desc", "权限说明")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currRightDef.getPropertyAsString("RIGHTDESC"))%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("auth.label.sysdefined", "系统定义权限")%></TD> <TD>&nbsp;<%=currRightDef.isSysDefined()?"是":"否"%></TD>
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