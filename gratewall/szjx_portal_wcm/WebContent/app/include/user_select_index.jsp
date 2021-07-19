<%
/** Title:			user_select_index.jsp
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
 *		see user_select_index.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
//4.初始化(获取数据)	
	int nTransferName = currRequestHelper.getInt("TransferName", 0);
	int nTreeType = currRequestHelper.getInt("TreeType", 0);
	boolean bTransferName	= currRequestHelper.getInt("TransferName", 0) > 0;
	int nbAllUser	= currRequestHelper.getInt("AllUser", 0);
	int nbFromProcess	= currRequestHelper.getInt("FromProcess", 0);
	boolean bForSendMsg = currRequestHelper.getBoolean("ForSendMsg", false);
	// 最初进入用户选择页面时，显示用户所属组织（如果有多个，则显示第一个组织）下的用户
	Groups groups = loginUser.getGroups();
	int nGroupId = 0;
	if(!loginUser.isAdministrator() && bForSendMsg && groups != null && groups.size() != 0){
		Group group = (Group)groups.getAt(0);
		if(group != null)
			nGroupId = group.getId();
	}

%>


<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE WCMAnt:param="user_select_index.jsp.title">TRS WCM6.1 用户选择首页::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<script>
function getMainWin(){
	return window.main;
}

function getParentWin(){
	return window.parent;
}
</script>
</HEAD>
<body style="padding:0px;margin:0px;overflow:hidden;">
<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;table-layout:fixed;">
<tbody>
	<tr>
		<td style="width:150px;">
			<iframe src="user_select_left.jsp?TreeType=<%=nTreeType%>&GroupId=<%=nGroupId%>" name="left" width="100%" height="100%" frameborder="no" border="0"  scrolling="no"></iframe>
		</td>
		<td>
			<iframe src="user_select_list.jsp?TransferName=<%=nTransferName%>&AllUser=<%=nbAllUser%>&FromProcess=<%=nbFromProcess%>&GroupId=<%=nGroupId%>" name="main" frameborder="no" border="0" width="100%" height="100%"  scrolling="no"></iframe>
		</td>
	</tr>
</tbody>
</table>	
</body>
</HTML>