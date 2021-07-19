<%--
/** Title:			schedule_list.jsp
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
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

	int nTypeId = currRequestHelper.getInt("TypeId", 0);

//5.权限校验
	if (!ContextHelper.getLoginUser().isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限查看计划调度列表！");
	}
//6.业务代码
	JobWorkerType currType = null;
	
	String sSelectFields = "SCHID,SCHNAME,SCHDESC,OPTYPE,OPARGS";
	WCMFilter filter = currRequestHelper.getPageFilter(null);

	if(nTypeId > 0){
		currType = JobWorkerType.findById(nTypeId);
		if(currType == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "无效的TypeID，没有找到ID为["+nTypeId+"]的JobWorkerType！");
		}
		filter.mergeWith(new WCMFilter("", " OPTYPE="+nTypeId, ""));
	}
	filter.mergeWith(new WCMFilter("", " OPTYPE not in (1,4,6,100)", ""));
	Schedules currSchedules = Schedules.openWCMObjs(loginUser, filter);
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currSchedules.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//确定是否可以新建
boolean canNew=true;
if((nTypeId==0)||(nTypeId==1) ||(nTypeId==4)||(nTypeId==5)||(nTypeId==6)||(nTypeId==7)||(nTypeId==10))  canNew=false;

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2<%=LocaleServer.getString("schedule.label.list", "计划调度列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px">	
	
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
<!--~--- ROW1 ---~-->
<TR>
<TD height="26" class="head_td">
	<TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
	<TR>
		<TD width="24"><IMG src="../images/bite-blue-open.gif" width="24" height="24"></TD>
		<TD width="235"><%=LocaleServer.getString("schedule.label.list", "计划调度列表")%></TD> 
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
	<TR>
	<TD height="25" colspan="2" class="bottomline">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
		<TR>
		<TD width="40" NOWRAP valign="center"><%=LocaleServer.getString("schedule.label.textname", "名称")%>：</TD>
		<TD width="180"><SPAN class="font_bluebold"><%=(nTypeId>0)?currType.getWorkerName():LocaleServer.getString("schedule.label.textnamedefault", "所有的计划调度")%></SPAN></TD>
		<TD>
			<script>			
				//定义我文档管理的按钮
				var oTRSButtons = new CTRSButtons();
		<%
			if(nTypeId > 0){
		%>
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("schedule.button.modifyworker", "修改调度类型")%>", "editWorkerType()", "../images/button_edit.gif", "<%=LocaleServer.getString("schedule.tip.modifyworker", "修改当前调度类型")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("schedule.button.dropworker", "删除调度类型")%>", "deleteWorkerType()", "../images/button_delete.gif", "<%=LocaleServer.getString("schedule.tip.dropworker", "删除当前调度类型")%>"<%=(nTypeId>100)?"":", \"bt_table_disable\""%>);
		<%
			} else {
		%>
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("schedule.button.addworker", "新建调度类型")%>", "addWorkerType()", "../images/button_newtopgroup.gif", "<%=LocaleServer.getString("schedule.tip.addworker", "新建一个调度类型")%>");
				//oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("schedule.tip.refresh", "刷新当前页面")%>");
				
		<%
			}
		%>
				oTRSButtons.draw();
			</script>
		</TD>
		</TR>
		</TABLE>
	</TD>
	</TR>
  <!--~--- ROW4 ---~-->
  <TR>
    <TD height="25">
    <!--~== TABLE4 ==~-->
    <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
    <!--~--- ROW5 ---~-->
    <TR>
      <TD align="left" valign="top">
			<script>
				//定义一个单行按钮
				var oTRSButtons = new CTRSButtons();
			<%
				if (nTypeId>=0){

			%>
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("schedule.button.add", "新建调度任务")%>", "addNew();", "../images/button_new.gif", "<%=LocaleServer.getString("schedule.tip.add", "新建计划调度")%>","<%=canNew?"":"bt_table_disable"%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("schedule.button.drop", "删除调度任务")%>", "deleteSchedule(getScheduleIds());", "../images/button_delete.gif", "<%=LocaleServer.getString("schedule.tip.drop", "删除选定的计划调度")%>","<%=canNew?"":"bt_table_disable"%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("schedule.tip.refresh", "刷新当前页面")%>");
				oTRSButtons.draw();	
			<%
				}
			%>
			</script>
      </TD>
	  <TD width="100%" nowrap align="right">
	  <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="SCHNAME,SCHDESC,OPARGS">全部</option>
			<option value="SCHNAME">名称</option>
			<option value="SCHDESC">描述</option>
			<option value="OPARGS">参数</option>
		</select>
		<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">
	  </form>
	  </TD>
	  <script>
		document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
	  </script>
    </TR>
    <!--~- END ROW5 -~-->	
    </TABLE>
    <!--~ END TABLE4 ~-->
    </TD>
  </TR>
  <!--~- END ROW4 -~-->
  <!--~-- ROW10 --~-->
  <TR>
    <TD align="left" valign="top" height="20">
    <!--~== TABLE9 ==~-->
	<div style="OVERFLOW-Y: auto; HEIGHT: 360px" id="dvBody">
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <!--~-- ROW11 --~-->
    <TR bgcolor="#BEE2FF" class="list_th" style="position: relative;top:expression(this.offsetParent.scrollTop);">
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('ScheduleIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SCHNAME", LocaleServer.getString("schedule.label.name", "名称"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SCHDESC", LocaleServer.getString("schedule.label.desc", "描述"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("OPTYPE", LocaleServer.getString("schedule.label.opertype", "操作类型"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("OPARGS", LocaleServer.getString("schedule.label.operarg", "操作参数"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=LocaleServer.getString("system.label.modify", "修改")%></TD>
    </TR>
    <!--~ END ROW11 ~-->
	<%
	Schedule currSchedule = null;
	for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
	{//begin for
		try{
			currSchedule = (Schedule)currSchedules.getAt(i-1);
			if(currSchedule == null) continue;
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第["+i+"]个计划调度任务失败！", ex);
		}

		try{
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="ScheduleIds" VALUE="<%=currSchedule.getId()%>"><%=i%></TD>
		<TD><A onclick="viewSchedule(<%=currSchedule.getId()%>)" href="#"><%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("SCHNAME"))%></A></TD>
		<TD><%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("SCHDESC"))%></TD>
		<TD><%=PageViewUtil.toHtml(JobWorkerType.findById(currSchedule.getWorkerTypeId()).getWorkerName())%></TD>
		<TD><%=PageViewUtil.toHtml(currSchedule.getPropertyAsString("OPARGS"))%></TD>
		<TD align="center">&nbsp;<A onclick="edit(<%=currSchedule.getId()%>);return false;" href="#"><IMG border="0" src="../images/icon_edit.gif"></A></TD>
	</TR>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]个计划调度任务的属性失败！", ex);
		}
	}//end for	
%>
    </TABLE>
	</DIV>
    <!--~ END TABLE9 ~-->
    </TD>
  </TR>
  <!--~ END ROW10 ~-->
  <!--~-- ROW32 for PageIndex --~-->
  <TR>
    <TD class="navigation_page_td" valign="top">
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "调度任务", "个")%>
    </TD>
  </TR>
  <!--~ END ROW32 for PageIndex ~-->
  </TABLE>
  <!--~ END TABLE3 ~-->
  </TD>
</TR>
<!--~- END ROW3 -~-->
</TABLE>
<script>
	function getScheduleIds(){
		return TRSHTMLElement.getElementValueByName('ScheduleIds');
	}

	function addNew(){
		var sURL		= "../system/schedule_addedit.jsp";

		var oTRSAction = new CTRSAction(sURL);
		oTRSAction.setParameter("TypeId", <%=nTypeId%>);
		var bResult = oTRSAction.doDialogAction(600, 480);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}

	function edit(_nScheduleId){	
		var oTRSAction = new CTRSAction("../system/schedule_addedit.jsp");
		oTRSAction.setParameter("ScheduleId", _nScheduleId);
		var bResult = oTRSAction.doDialogAction(600, 480);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}
	
	function deleteSchedule(_sScheduleIds){
		//参数校验
		if(_sScheduleIds == null || _sScheduleIds.length <= 0){
			CTRSAction_alert("请选择需要删除的计划调度!");
			return;
		}
		if(!CTRSAction_confirm("您确定需要删除这些计划调度吗?"))
			return;
		
		var oTRSAction = new CTRSAction("schedule_delete.jsp");
		oTRSAction.setParameter("ScheduleIds", _sScheduleIds);		
		oTRSAction.doDialogAction(500, 300);
		CTRSAction_refreshMe();
	}

	function addWorkerType(){
		var oTRSAction = new CTRSAction("jobworkertype_addedit.jsp");
		oTRSAction.setParameter("JobWorkerTypeId", 0);
		var arResult = oTRSAction.doDialogAction(400, 380);
		if(arResult && arResult[0]){
			if(window.top.refreshNavItem)
				window.top.refreshNavItem(1, "../system/worktype_nav.jsp", arResult[1]);
			if(window.top.gotoMain)
				window.top.gotoMain("../system/schedule_list.jsp?TypeId="+arResult[2]);
		}
	}

	function editWorkerType(){
		var oTRSAction = new CTRSAction("jobworkertype_addedit.jsp");
		oTRSAction.setParameter("JobWorkerTypeId", <%=nTypeId%>);
		var arResult = oTRSAction.doDialogAction(400, 380);
		if(arResult && arResult[0]){
			if(window.top.refreshNavItem)
				window.top.refreshNavItem(1, "../system/worktype_nav.jsp", arResult[1]);
			if(window.top.gotoMain)
				window.top.gotoMain("../system/schedule_list.jsp?TypeId="+arResult[2]);
		}
	}

	function deleteWorkerType(){
		if(!CTRSAction_confirm("是否确认删除当前调度类型？")){
			return;
		}
		var oTRSAction = new CTRSAction("jobworkertype_delete.jsp");
		oTRSAction.setParameter("JobWorkerTypeIds", <%=nTypeId%>);
		var arResult = oTRSAction.doDialogAction(500, 500);
		if(arResult && arResult[0]){
			if(window.top.refreshNavItem)
				window.top.refreshNavItem(1, "../system/worktype_nav.jsp", arResult[1]);
			if(window.top.gotoMain)
				window.top.gotoMain("../system/schedule_list.jsp?TypeId="+arResult[2]);
		}
	}

	function viewSchedule(nScheduleId){

		var oTRSAction = new CTRSAction("../system/schedule_show.jsp");
		oTRSAction.setParameter("ScheduleId", nScheduleId);
		oTRSAction.doOpenWinAction(400,300);
	
	}



</script>
</BODY>
</HTML>
<%
//6.资源释放
	currSchedules.clear();
%>