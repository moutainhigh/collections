<%
/** Title:			groups_get.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“获取用户组列表”。
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
 *		see groups_get.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sGroupIds	= currRequestHelper.getString("GroupIds");		
	

//5.权限校验

//6.业务代码
	Groups currGroups = Groups.findByIds(loginUser, sGroupIds);
	//TODO 权限过滤	
	//IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	ObjToXmlConverter aXMLConvert = new ObjToXmlConverter();

//7.结束
	out.clear();
%>
<%=aXMLConvert.toXMLString(currGroups, null, "GroupId,GName,GDesc")%>
<%
//8.释放资源
	currGroups.clear();
%>