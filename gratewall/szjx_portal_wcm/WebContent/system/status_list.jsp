<%--
/** Title:			status_list.jsp
 *  Description:
 *		文档状态列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-06 15:31:19
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-06 / 2005-04-06
 *	Update Logs:
 *		NZ@2005-04-06 产生此文件
 *
 *  Parameters:
 *		see status_list.xml
 *	History			Who			What
 *	2008-04-17		wenyh		修正默认排序的问题(statusid asc)
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

//5.权限校验
	if (!AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_DOCSTATUS)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限查看文档状态列表！");
	}
//6.业务代码
	String sSelectFields = "SNAME,SDESC,SDISP,SUSED,CRUSER,CRTIME,RIGHTINDEX,ATTRIBUTE";
	WCMFilter filter = currRequestHelper.getPageFilter(null);

	
	/**
	  *TODO 改为以下方式
	  *IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	  *Statuses currStatuses = currChannelService.getStatuses(currChannel, filter);
	**/
	// wenyh@2008-04-17 添加默认排序
	String sOrder = filter.getOrder();
	if(CMyString.isEmpty(sOrder)){
		filter.setOrder("statusid asc");
	}
	Statuses currStatuses = Statuses.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currStatuses.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2<%=LocaleServer.getString("document.label.list_of_status", "文档状态列表页面")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px">	
	
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
<!--~--- ROW1 ---~-->
<TR>
  <TD height="26" class="head_td" align=left>
	<TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
	<TR>
		<TD width="24"><IMG src="../images/bite-blue-open.gif" width="24" height="24"></TD>
		<TD width="235"><%=LocaleServer.getString("document.label.list_of_status", "文档状态列表")%></TD> 
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
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.add", "新建")%>", "addNew();", "../images/button_new.gif", "<%=LocaleServer.getString("document.label.add_of_status", "新建文档状态")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.drop", "删除")%>", "deleteStatus(getStatusIds());", "../images/button_delete.gif", "<%=LocaleServer.getString("document.label.drop_of_status", "删除选定的文档状态")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("document.label.refresh_of_status", "刷新当前页面")%>");
				oTRSButtons.draw();	
			</script>      
      </TD>
	  <TD width="250" nowrap>
	  <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="SNAME,SDESC,SDISP,CRUSER">全部</option>			
			<option value="SNAME" <%="SNAME".equals(currRequestHelper.getSearchKey())?"selected":""%>>名称</option>
			<option value="SDESC" <%="SDESC".equals(currRequestHelper.getSearchKey())?"selected":""%>>说明</option>
			<option value="SDISP" <%="SDISP".equals(currRequestHelper.getSearchKey())?"selected":""%>>显示</option>
			<option value="CRUSER" <%="CRUSER".equals(currRequestHelper.getSearchKey())?"selected":""%>>创建用户</option>
		</select>
		<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
	  </form>
	  </TD>
	  
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
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('StatusIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("RIGHTINDEX", LocaleServer.getString("document.label.rightindex_of_status", "权限索引"), sOrderField, sOrderType)%></TD>
		
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SNAME", LocaleServer.getString("document.label.name_of_status", "名称"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SDESC", LocaleServer.getString("document.label.desc_of_status", "说明"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SDISP", LocaleServer.getString("document.label.show_of_status", "显示"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SUSED", LocaleServer.getString("document.label.isused_of_status", "是否使用"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("CRUSER", LocaleServer.getString("document.label.cruser_of_status", "创建用户"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("CRTIME", LocaleServer.getString("document.label.crtime_of_status", "创建时间"), sOrderField, sOrderType)%></TD>
		

		<TD bgcolor="#BEE2FF" nowrap><%=LocaleServer.getString("system.label.modify", "修改")%></TD>
    </TR>
    <!--~ END ROW11 ~-->
	<%
	Status currStatus = null;
	for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
	{//begin for
		try{
			currStatus = (Status)currStatuses.getAt(i-1);
			if(currStatus == null) continue;
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第["+i+"]篇文档状态失败！", ex);
		}

		try{
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="StatusIds" VALUE="<%=currStatus.getId()%>" <%=(currStatus.getId() < 20)?"disabled":""%>><%=i%></TD>
		
		<TD align=center><%=currStatus.getRightIndex()%></TD>
		
		<TD><A onclick="viewStatus(<%=currStatus.getId()%>)" href="#"><%=PageViewUtil.toHtml(currStatus.getName())%></A></TD>
		

		<TD NOWRAP><%=PageViewUtil.toHtml(currStatus.getDesc())%></TD>
		

		<TD align=center NOWRAP><%=PageViewUtil.toHtml(currStatus.getDisp())%></TD>
		

		<TD align=center NOWRAP><%=currStatus.isUsed()?"是":"否"%></TD>
		

		<TD align=center NOWRAP><%=PageViewUtil.toHtml(currStatus.getPropertyAsString("CRUSER"))%></TD>
		

		<TD align=center NOWRAP><%=PageViewUtil.toHtml(currStatus.getPropertyAsString("CRTIME"))%></TD>
		

		<TD align="center">&nbsp;<A onclick="edit(<%=currStatus.getId()%>);return false;" href="#"><IMG border="0" src="../images/icon_edit.gif"></A></TD>
	</TR>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]篇文档状态的属性失败！", ex);
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
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "文档状态", "个")%>
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
	function getStatusIds(){
		return TRSHTMLElement.getElementValueByName('StatusIds');
	}

	function addNew(){
		var sURL		= "status_addedit.jsp";

		var oTRSAction = new CTRSAction(sURL);
		var bResult = oTRSAction.doDialogAction(500, 450);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}

	function edit(_nStatusId){	
		var oTRSAction = new CTRSAction("status_addedit.jsp");
		oTRSAction.setParameter("StatusId", _nStatusId);
		var bResult = oTRSAction.doDialogAction(500, 450);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}
	
	function deleteStatus(_sStatusIds){
		//参数校验
		if(_sStatusIds == null || _sStatusIds.length <= 0){
			CTRSAction_alert("请选择需要删除的文档状态!");
			return;
		}
		if(!CTRSAction_confirm("您确定需要删除这些文档状态吗?"))
			return;
		
		var oTRSAction = new CTRSAction("status_delete.jsp");
		oTRSAction.setParameter("StatusIds", _sStatusIds);		
		oTRSAction.doDialogAction(500, 300);
		CTRSAction_refreshMe();
	}

		function viewStatus(nStatusId){

		var oTRSAction = new CTRSAction("../system/status_show.jsp");
		oTRSAction.setParameter("StatusId", nStatusId);
		oTRSAction.doOpenWinAction(400,300);
	
	}



</script>
</BODY>
</HTML>
<%
//6.资源释放
	currStatuses.clear();
%>