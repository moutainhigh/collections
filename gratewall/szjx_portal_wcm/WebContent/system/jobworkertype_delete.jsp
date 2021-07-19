<%--
/** Title:			jobworkertype_delete.jsp
 *  Description:
 *		删除调度类型的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-06 00:17:46
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		CH@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see jobworkertype_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.JobWorkerTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	String sJobWorkerTypeIds = currRequestHelper.getString("JobWorkerTypeIds");

//5.权限校验

//6.业务代码
	/** TODO 建议改为向Service发出请求
	IJobWorkerTypeService currJobWorkerTypeService = (IJobWorkerTypeService)DreamFactory.createObjectById("IJobWorkerTypeService");
	currJobWorkerTypeService.delete(sJobWorkerTypeIds);
	**/
	JobWorkerTypes currJobWorkerTypes = JobWorkerTypes.findByIds(loginUser, sJobWorkerTypeIds);
	currJobWorkerTypes.removeAll(true);
	

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 删除调度类型::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
var arReturn = new Array();
arReturn[0] = true;
arReturn[1] = "0";
arReturn[2] = "0";

window.returnValue = arReturn;
window.close();
</SCRIPT>
</BODY>
</HTML>