<%--
/** Title:			Noname1.jsp
 *  Description:
 *		处理计划调度的添加修改页面
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
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<%@ page import="com.trs.service.IScheduleService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nScheduleId = currRequestHelper.getInt("ScheduleId", 0);
	Schedule currSchedule = null;
	if(nScheduleId > 0){
		currSchedule = Schedule.findById(nScheduleId);
		if(currSchedule == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nScheduleId+"]的计划调度！");
		}
	}else{//nScheduleId==0 create a new group
		currSchedule = Schedule.createNewInstance();
	}
	
//5.权限校验

//6.业务代码
	
	try{
		currSchedule = (Schedule)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nScheduleId, Schedule.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存计划调度时因属性值不正确而失败中止！", ex);
	}
	/**
		TODO 建议改为向Service发出请求
	IScheduleService currScheduleService = (IScheduleService)DreamFactory.createObjectById("IScheduleService");
	currScheduleService.save(currSchedule);
		**/
	currSchedule.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改计划调度:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
if(window.opener){
	window.opener.CTRSAction_refreshMe();
	window.opener.focus();
	window.close();
}else{
	window.returnValue = true;
	window.close();
}

</SCRIPT>
</BODY>
</HTML>