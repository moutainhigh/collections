<%
/** Title:			channels_get.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“获取栏目列表”。
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
 *		see channels_get.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sUserIds	= currRequestHelper.getString("UserIds");		
	

//5.权限校验

//6.业务代码
	Users currUsers = Users.findByIds(loginUser, sUserIds);
	String sUserNames = "";
	boolean bFirst = true;
	for(int i=0; i<currUsers.size(); i++){
		User currUser = (User)currUsers.getAt(i);
		if(currUser == null)continue;

		if(bFirst){
			sUserNames = currUser.getName();
			bFirst = false;
		}
		else
			sUserNames +=  "," + currUser.getName();
	}
	currUsers.clear();

//7.结束
	out.clear();
%>
<%=sUserNames%>