<%--
/** Title:			schedule_jobexcuteresult_list.jsp
 *  Description:
 *		计划任务执行失败结果列表查看页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-10-10 10:54:23
 *  Vesion:			1.0
 *  Last EditTime:	2005-10-10 / 2005-10-10
 *	Update Logs:
 *		Wenyh@2005-10-10 产生此文件
 *
 *  Parameters:
 *		see schedule_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<%@ page import="com.trs.components.common.job.Schedules" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.job.JobFailedResult" %>
<%@ page import="com.trs.infra.util.job.JobFailedResults" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%!
	private JobFailedResults getFaieldResults(int _nScheduleId)throws WCMException{
		WCMFilter filter = null;
		if(_nScheduleId>0){
		 	filter = new WCMFilter("","JOBSCHEDULEID=?","");
			filter.addSearchValues(0,_nScheduleId);
		}
		
		return JobFailedResults.openWCMObjs(filter);
	}
%>
<%
	int nScheduleId = currRequestHelper.getInt("ScheduleId",0);
	JobFailedResults m_results = getFaieldResults(nScheduleId);
	
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( m_results.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
	
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2<%=LocaleServer.getString("schedule.label.list", "计划调度列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>
</HEAD>
<BODY topmargin="0" leftmargin="0" style="margin:5px">
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
<!--~--- ROW1 ---~-->
<TR>
  <TD height="26" class="head_td" align=left>
	<TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
	<TR>
		<TD width="24"><IMG src="../images/bite-blue-open.gif" width="24" height="24"></TD>
		<TD width="235">计划任务执行失败原因记录查看</TD>
		<TD class="navigation_channel_td">&nbsp;</TD> 
		<TD width="28">&nbsp;</TD>
	</TR>
	</TABLE>
  </TD>
</TR>
<!--~- END ROW1 -~-->
<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top">
  <!--~== TABLE3 ==~-->
  <TABLE width="100%" border="0" cellpadding="2" height="100%" cellspacing="0" bgcolor="#FFFFFF">
  <!--~-- ROW10 --~-->
  <TR>
    <TD align="left" valign="top" height="20">
    <!--~== TABLE9 ==~-->
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <!--~-- ROW11 --~-->
    <TR bgcolor="#BEE2FF" class="list_th">
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('SecurityIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<TD bgcolor="#BEE2FF">失败原因</TD>
		<TD bgcolor="#BEE2FF">执行时间</TD>
    </TR>
    <!--~ END ROW11 ~-->
	<%
		Schedule schedule = null;
		JobFailedResult result = null;
		for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
			result = (JobFailedResult)m_results.getAt(i-1);
			if(result == null) continue;
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="ResultIds" VALUE="<%=result.getId()%>"><%=i%></TD>
		<TD><%=result.getPropertyAsString("JOBFAIELDROOTCAUSE")%></TD>
		<TD><%=((CMyDateTime)result.getProperty("CRTIME")).toString("yyyy-MM-dd HH:mm")%></TD>
	</TR>
	<%
    	}//End for.
	%>
    </TABLE>
    <!--~ END TABLE9 ~-->
    </TD>
  </TR>
  <!--~ END ROW10 ~-->
  <!--~-- ROW32 for PageIndex --~-->
  <TR>
    <TD class="navigation_page_td" valign="top">
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "任务执行失败原因记录", "个")%>
    </TD>
  </TR>
  <!--~ END ROW32 for PageIndex ~-->
  </TABLE>
  <!--~ END TABLE3 ~-->
  </TD>
</TR>
<!--~- END ROW3 -~-->
</TABLE>
</BODY>
</HTML>
<%
//6.资源释放
	m_results.clear();
%>