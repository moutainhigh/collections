<%
/** Title:			rolename_get_by_ids.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“获取组织名称”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-29 13:45
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-29/2004-12-29
 *	Update Logs:
 *
 *  Parameters:
 *		see rolename_get_by_ids.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Roles" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sRoleIds	= currRequestHelper.getString("RoleIds");		
	

//5.权限校验

//6.业务代码
	Roles currRoles = Roles.findByIds(loginUser, sRoleIds);
	String sRoleNames = "";
	boolean bFirst = true;
	for(int i=0; i<currRoles.size(); i++){
		Role currRole = (Role)currRoles.getAt(i);
		if(currRole == null)continue;

		if(bFirst){
			sRoleNames = currRole.getName();
			bFirst = false;
		}
		else
			sRoleNames +=  "," + currRole.getName();
	}
	currRoles.clear();

//7.结束
	out.clear();
%>
<%=sRoleNames%>