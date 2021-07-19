<%--
/** Title:			operargs_get.jsp
 *  Description:
 *		计划调度列表页面
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
 *		see operargs_get.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nTypeId = currRequestHelper.getInt("TypeId", 0);
	int nScheduleId = currRequestHelper.getInt("ScheduleId", 0);

//5.权限校验

//6.业务代码
	JobWorkerType currType = JobWorkerType.findById(nTypeId);
	if(currType == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "无效的TypeID，没有找到ID为["+nTypeId+"]的JobWorkerType！");
	}

	Schedule currSchedule = null;
	if(nScheduleId > 0){
		currSchedule = Schedule.findById(nScheduleId);
		if(currSchedule == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nScheduleId+"]的计划调度失败！");
		}
	}

	int nParamCount = currType.getParamCount();

//7.结束
	out.clear();
%>

<SCRIPT LANGUAGE="JavaScript">
var arReturnValue = new Array();
var arParamName = new Array();
var arParamDisp = new Array();
var arParamValue = new Array();
<%
	String sParamName = "";
	String sParamDisp = "";
	String sParamValue = "";
	for(int i=0; i<nParamCount; i++){
		sParamName = currType.getParamName(i);
		sParamDisp = currType.getParamDisp(sParamName);
		if(currSchedule != null)
			sParamValue = currSchedule.getOperArgValue(sParamName);
%>
	arParamName[<%=i%>] = "<%=sParamName%>";
	arParamDisp[<%=i%>] = "<%=sParamDisp%>";
	arParamValue[<%=i%>] = "<%=sParamValue%>";
<%
	}
%>

arReturnValue[0] = arParamName;
arReturnValue[1] = arParamDisp;
arReturnValue[2] = arParamValue;

if(window.parent.drawParamHtml) {
	window.parent.drawParamHtml(arReturnValue);
}
</SCRIPT>