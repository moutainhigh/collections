<%--
/** Title:			eventtype_show.jsp
 *  Description:
 *		日程事件类型的显示页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 09:52:38
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see eventtype_show.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Roles" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IUserService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nUserId = currRequestHelper.getInt("UserId",  0);
	User  currUser = currUser = User.findById(nUserId);
	if(currUser == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nUserId+"]的用户失败！");
	}

//5.权限校验

//6.业务代码
	IUserService currUserService = ServiceHelper.createUserService();

	Groups currGroups = currUserService.getGroups(currUser);

	String strGroupNames = null;
	Group currGroup = null;
	for(int i=0; i<currGroups.size(); i++) {
		currGroup = (Group)currGroups.getAt(i);
		if(currGroup == null) continue;
		if(strGroupNames == null)
			strGroupNames = currGroup.getName();
		else 
			strGroupNames += ", " + currGroup.getName();
	}

	
	Roles currRoles = currUserService.getRoles(currUser);

	String strRoleNames = null;
	Role currRole = null;
	for(int i=0; i<currRoles.size(); i++) {
		currRole = (Role)currRoles.getAt(i);
		if(currRole == null) continue;
		if(strRoleNames == null)
			strRoleNames = currRole.getName();
		else 
			strRoleNames += ", " + currRole.getName();
	}

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("event.label.info", "日程事件类型信息")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
		WCMDialogHead.draw("<%=LocaleServer.getString("user.label.info", "用户信息")%>",true);
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
		<TD NOWRAP> <%=LocaleServer.getString("user.label.username", "用户登录名")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currUser.getName())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("user.label.nickname", "用户昵称")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currUser.getNickName())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("user.label.email", "电子信箱")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currUser.getEmail())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("user.label.realname", "真实姓名")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currUser.getTrueName())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("user.label.address", "详细地址")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currUser.getAddress())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("user.label.telephone", "联系电话")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currUser.getTel())%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("user.label.belonggroup", "所属组织")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(strGroupNames)%></TD>
		</TR>
		

		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("user.label.belongrole", "所属角色")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(strRoleNames)%></TD>
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