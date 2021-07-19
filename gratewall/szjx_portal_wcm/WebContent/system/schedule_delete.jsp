<%--
/** Title:			Noname1.jsp
 *  Description:
 *		删除计划调度的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-05 13:07:23
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-05 / 2005-04-05
 *	Update Logs:
 *		CH@2005-04-05 产生此文件
 *
 *  Parameters:
 *		see Noname1.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.Schedules" %>
<%@ page import="com.trs.service.IScheduleService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	String sScheduleIds = currRequestHelper.getString("ScheduleIds");

//5.权限校验

//6.业务代码
	/** TODO 建议改为向Service发出请求
	IScheduleService currScheduleService = (IScheduleService)DreamFactory.createObjectById("IScheduleService");
	currScheduleService.delete(sScheduleIds);
	**/
	Schedules currSchedules = Schedules.findByIds(loginUser, sScheduleIds);
	currSchedules.removeAll(true);
	

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 删除计划调度::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
window.returnValue = true;
window.close();
</SCRIPT>
</BODY>
</HTML>