<%
/** Title:			user_select_main.jsp
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
 *		see user_select_main.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Roles" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)	
	String sUserIds = CMyString.showNull(currRequestHelper.getString("UserIds"));
	String sGroupIds = CMyString.showNull(currRequestHelper.getString("GroupIds"));
	String sRoleIds = CMyString.showNull(currRequestHelper.getString("RoleIds"));
	boolean bFromProcess = currRequestHelper.getBoolean("FromProcess", false);

//5.权限校验
	
//6.业务代码
	Users currUsers = null;
	if(sUserIds.length() > 0){
		currUsers = Users.findByIds(loginUser, sUserIds);
	}

	Groups currGroups = null;
	if(sGroupIds.length() > 0){
		currGroups = Groups.findByIds(loginUser, sGroupIds);
	}

	Roles currRoles = null;
	if(sRoleIds.length() > 0){
		currRoles = Roles.findByIds(loginUser, sRoleIds);
	}

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="selected_list.jsp.title">TRS WCM 已选择列表::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">

</HEAD>

<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25" valign="top">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<TR>
	<TD style="BORDER-bottom: #A6A6A6 1px solid; width:10px; height:25px">&nbsp;</TD>
	<TD width="120" align="center" style="BORDER-top: #A6A6A6 1px solid;BORDER-right: #A6A6A6 1px solid;BORDER-left: #A6A6A6 1px solid; background-color:#FFFFFF" WCMAnt:param="selected_list.jsp.selectedUserOrGroup">已选择用户(组)</TD> <TD style="BORDER-bottom: #A6A6A6 1px solid;">&nbsp;</TD>
	</TR>
	</TABLE>
</TD>
</TR>
<TR>
<TD align="center" valign="top" style="BORDER-bottom: #A6A6A6 1px solid;BORDER-right: #A6A6A6 1px solid;BORDER-left: #A6A6A6 1px solid; background-color:#FFFFFF; padding:5px">
	<TABLE id="tbOperators" name="tbOperators" width="100%" border="0" cellspacing="0" cellpadding="2">
	
	</TR>
	</TABLE>
</TD>
</TR>
</TABLE>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSArray.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMOperator.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<%
	if(!(currUsers == null || currUsers.isEmpty())){
		User currUser = null;
		for(int i=0; i<currUsers.size(); i++){
			currUser = (User) currUsers.getAt(i);
			if(currUser == null) continue;
			String sTrueName = currUser.getName();
			if(bFromProcess && currUser.getTrueName() != null && !currUser.getTrueName().equals("")){
				sTrueName = currUser.getTrueName();
			}
%>
			WCMOperatorHelper.insert(<%=User.OBJ_TYPE%>, <%=currUser.getId()%>, "<%=CMyString.filterForJs(sTrueName)%>");
<%
		}
	}
%>

<%
	if(!(currGroups == null || currGroups.isEmpty())){
		Group currGroup = null;
		for(int i=0; i<currGroups.size(); i++){
			currGroup = (Group) currGroups.getAt(i);
			if(currGroup == null) continue;
%>
			WCMOperatorHelper.insert(<%=Group.OBJ_TYPE%>, <%=currGroup.getId()%>, "<%=CMyString.filterForJs(currGroup.getName())%>");
<%
		}
	}
%>

<%
	if(!(currRoles == null || currRoles.isEmpty())){
		Role currRole = null;
		for(int i=0; i<currRoles.size(); i++){
			currRole = (Role) currRoles.getAt(i);
%>
			WCMOperatorHelper.insert(<%=Role.OBJ_TYPE%>, <%=currRole.getId()%>, "<%=CMyString.filterForJs(currRole.getName())%>");
<%
		}
	}
%>
</SCRIPT>
</BODY>
</HTML>