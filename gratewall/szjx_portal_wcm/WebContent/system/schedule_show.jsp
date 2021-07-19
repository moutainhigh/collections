<%--
/** Title:			Noname1.jsp
 *  Description:
 *		计划调度的显示页面
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
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nScheduleId = currRequestHelper.getInt("ScheduleId", 0);
	Schedule currSchedule = Schedule.findById(nScheduleId);
	if(currSchedule == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "参数有误，没有找到ID为["+nScheduleId+"]的计划调度！");
	}

//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("schedule.label.info", "计划调度信息")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
function closeWindow(){
	window.returnValue = true;
	window.close();
}
</SCRIPT>
</HEAD>

<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("schedule.label.info", "计划调度信息")%>",true);
	</SCRIPT>
</TD>
</TR>
<TR>
<TD valign="top">
	<TABLE width="100%" border="0" cellpadding="2" height="100%" cellspacing="0" bgcolor="#FFFFFF">
	<TR>
	<TD align="left" valign="top" height="10">
		<SCRIPT src="../js/CTRSButton.js"></SCRIPT>
		<script>
			//定义一个单行按钮
			var oTRSButtons = new CTRSButtons();
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.close", "关闭")%>", "closeWindow();", "../images/button_close.gif", "<%=LocaleServer.getString("system.tip.close", "关闭页面")%>");
			oTRSButtons.draw();
		</script>
	</TD>
	</TR>
	<TR>
	<TD align="left" valign="top">
		<TABLE width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="A6A6A6">
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("schedule.label.name", "名称")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("SCHNAME"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("schedule.label.cruser", "创建用户")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("CRUSER"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("schedule.label.crtime", "创建时间")%></TD> <TD NOWRAP>&nbsp;<%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("CRTIME"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("schedule.label.desc", "描述")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("SCHDESC"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("schedule.label.opertype", "类型")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(JobWorkerType.findById(currSchedule.getWorkerTypeId()).getWorkerName())%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("schedule.label.operarg", "操作参数")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("OPARGS"))%></TD>
		</TR>
		<TR bgcolor="#F6F6F6">
		<TD NOWRAP> <%=LocaleServer.getString("schedule.label.runmode", "运行模式")%></TD> <TD>&nbsp;<%=PageViewUtil.toHtml(getExeMode(currSchedule))%></TD>
		</TR>
		<%=getTimeParams(currSchedule)%>
		</TABLE>
	</TD>
	</TR>
	</TABLE>
</TD>
</TR>
</TABLE>
</BODY>
</HTML>

<%!
	private String getExeMode(Schedule _currSchedule){
	if(_currSchedule == null){
			return "";
		}
		int nMode = _currSchedule.getMode();
		String sModeDesc = "";
		switch(nMode){
			case Schedule.MODE_ONE_TIME_ONLY:
				sModeDesc = "仅仅执行一次";
				break;
			case Schedule.MODE_MORE_TIMES_DAY:
				sModeDesc = "一天运行多次";
				break;
			case Schedule.MODE_ONE_TIME_DAY:
				sModeDesc = "每天运行一次";
				break;
			default:
				return "";
		}//end switch

		return sModeDesc;
	}//end getExeMode

	private String getTimeParams(Schedule _currSchedule){
		if(_currSchedule == null){
			return "";
		}
		int nMode = _currSchedule.getMode();
		StringBuffer sbHtml = new StringBuffer();
		switch(nMode){
			case Schedule.MODE_ONE_TIME_ONLY:
				sbHtml.append("<TR bgcolor=\"#F6F6F6\">");
				sbHtml.append("<TD>");
				sbHtml.append("执行时间");
				sbHtml.append("</TD> <TD>&nbsp;");
				sbHtml.append(_currSchedule.getExeTime().toString(CMyDateTime.DEF_DATETIME_FORMAT_PRG));
				sbHtml.append("</TD></TR>");
				break;
			case Schedule.MODE_ONE_TIME_DAY:
				sbHtml.append("<TR bgcolor=\"#F6F6F6\">");
				sbHtml.append("<TD>");
				sbHtml.append("执行时间");
				sbHtml.append("</TD> <TD>&nbsp;");
				sbHtml.append(_currSchedule.getExeTime().toString("HH:mm"));
				sbHtml.append("</TD></TR>");
				break;
			case Schedule.MODE_MORE_TIMES_DAY:
				sbHtml.append("<TR bgcolor=\"#F6F6F6\">");
				sbHtml.append("<TD>");
				sbHtml.append("开始时间");
				sbHtml.append("</TD> <TD>&nbsp;");
				sbHtml.append(_currSchedule.getStartTime().toString("HH:mm"));
				sbHtml.append("</TD></TR>");
				sbHtml.append("<TR bgcolor=\"#F6F6F6\">");
				sbHtml.append("<TD>");
				sbHtml.append("结束时间");
				sbHtml.append("</TD> <TD>&nbsp;");
				sbHtml.append(_currSchedule.getEndTime().toString("HH:mm"));
				sbHtml.append("</TD></TR>");
				sbHtml.append("<TR bgcolor=\"#F6F6F6\">");
				sbHtml.append("<TD>");
				sbHtml.append("间隔时间");
				sbHtml.append("</TD> <TD>&nbsp;");
				int nParam = _currSchedule.getParam();
				int nHours = nParam/60;
				int nMinutes = nParam%60;
				if(nHours > 0)
					sbHtml.append(nHours + "小时");
				if(nMinutes > 0)
					sbHtml.append(nMinutes + "分钟");
				sbHtml.append("&nbsp;</TD></TR>");
				break;
			default:
				return "";
		}//end switch

		return sbHtml.toString();
	}//end getTimeParams
%>