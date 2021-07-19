<%--
/** Title:			jobworkertype_addedit_dowith.jsp
 *  Description:
 *		处理调度类型的添加修改页面
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
 *		see jobworkertype_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nJobWorkerTypeId = currRequestHelper.getInt("JobWorkerTypeId", 0);
	JobWorkerType currJobWorkerType = null;
	if(nJobWorkerTypeId > 0){
		currJobWorkerType = JobWorkerType.findById(nJobWorkerTypeId);
		if(currJobWorkerType == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到指定ID为["+nJobWorkerTypeId+"]的调度类型！");
		}
	}else{//nJobWorkerTypeId==0 create a new group
		currJobWorkerType = JobWorkerType.createNewInstance();
	}
	
//5.权限校验

//6.业务代码
	try{
		currJobWorkerType = (JobWorkerType)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nJobWorkerTypeId, JobWorkerType.class);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "保存调度类型时因属性值不正确而失败中止！", ex);
	}
	/**
		TODO 建议改为向Service发出请求
	IJobWorkerTypeService currJobWorkerTypeService = (IJobWorkerTypeService)DreamFactory.createObjectById("IJobWorkerTypeService");
	currJobWorkerTypeService.save(currJobWorkerType);
		**/
	currJobWorkerType.save(loginUser);

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 处理添加修改调度类型:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
var arReturn = new Array();
arReturn[0] = true;
arReturn[1] = "<%=currJobWorkerType.getId()%>";
arReturn[2] = "<%=currJobWorkerType.getId()%>";

window.returnValue = arReturn;
window.close();
</SCRIPT>
</BODY>
</HTML>