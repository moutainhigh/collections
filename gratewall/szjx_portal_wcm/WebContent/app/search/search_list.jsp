<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTask" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTasks" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSServer" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSServers" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateway" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateways" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限操作检索配置！");
	}
	//业务代码	
	WCMFilter extraFilter = new WCMFilter();
	String sWhere = currRequestHelper.getWhereSQL();
	extraFilter.setWhere(sWhere);
	WCMFilter filter = currRequestHelper.getPageFilter(extraFilter);
	SearchTasks tasks = SearchTasks.openWCMObjs(loginUser,filter);
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount(tasks.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

	//结束
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="search_list.jsp.searchtasklistpage">检索任务列表页面</title>
	<%-- 
		/*
			ffx@2012-06-08 force the IE9 or IE8 use a IE8 document mode.
			防止IE9或IE8使用IE7文档模式解析，导致结构混乱
		*/ 
	--%>
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
	<script src="../../app/js/data/locale/logo.js"></script>
	<script src="../js/data/locale/wcm52.js"></script>
	<script src="../../app/js/data/locale/system.js"></script>
	<!-- dialog  Start -->
	<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
	<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
	<script src="../js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
	<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>
	<script src="../../app/js/source/wcmlib/pagecontext/PageOper.js"></script>
	<!--AJAX-->
	<script src="../../app/js/data/locale/ajax.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!-- Dialog 引入的资源 BEGIN --->
	<link type="text/css" rel="StyleSheet" href="../../style/style_CTRSCrashBoard.css" />
	<script type="text/javascript" src="search_list.js"></script>
	<!--carshboard-->
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
	<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
	<!--ProcessBar Start-->
	<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
	<!--ProcessBar End-->
	<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
	<%@include file="../../include/public_client_list.jsp"%>
	<style>
		.grid_row_odd{
			background-color:#FFFFFF;
			cursor : pointer;
		}
		.grid_row_even{
			background-color:#efefef;
			cursor : pointer;
		}
		.grid_row_active{
			background-color:#ffffef;
			cursor : pointer;
		}
		.object_edit{
			background-image: url(../images/icon/Update.png);
			background-position: center;
			background-repeat: no-repeat;
			width : 16px;
			height : 16px;
			display:-moz-inline-box; display:inline-block;
		}
		.wcm_attr_value_withborder{
			padding:0;
			border:1px solid blue;
		}
		#selectContainer{
			width:100%;
			height:100%;
		}
		.object_delete{
			background-image: url(../images/icon/Delete.png);
			background-position: center;
			background-repeat: no-repeat;
			width : 16px;
			height : 16px;
			display:-moz-inline-box; display:inline-block;
		}
	</style>
<SCRIPT>
function Config_doSearch(_oForm){
	var oForm = _oForm;
	if(_oForm == null)
		oForm = document.frmSearch;
	if(oForm.SearchKey == null || oForm.SearchValue == null){
		CTRSAction_alert("Search Form Invalid!");
		return;
	}
	var SearchValue = document.getElementById("SearchValue").value;
	if(SearchValue.length && SearchValue.length > 200){
		CTRSAction_alert("对不起，您输入的检索内容过长，超出最大长度[200]，请重新输入！");
		return false;
	}

	var oTRSAction = new CTRSAction();

	oTRSAction.setParameter("SearchKey", oForm.SearchKey.value);
	oTRSAction.setParameter("SearchValue", oForm.SearchValue.value);
	oTRSAction.setParameter("PageIndex", 1);
	oTRSAction.doAction();
}
</SCRIPT>
<body>
	<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table" valign="top">
		<TR>
			<TD height="26" class="head_td">
				<TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD width="245">&nbsp;&nbsp;<span WCMAnt:param="search_list.jsp.subhead">检索任务列表</span>&nbsp;&nbsp;</TD>
						<TD></TD>
						<TD class="navigation_channel_td"></TD>
						<TD width="28">&nbsp;</TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
		<TR bgcolor="#FFFFFF">
			<TD valign="top">
				<TABLE width="100%" border="0" cellpadding="0" height="100%" cellspacing="0" bgcolor="#FFFFFF">
					<TR height="15px;">
						<TD valign=middle>
						<script>
							//定义一个单行按钮
							var oTRSButtons = new CTRSButtons();
							oTRSButtons.addTRSButton(wcm.LANG.LOGO_CONFIRM_1 || "新建", "edit(0);", "../../images/button_new.gif");
							oTRSButtons.addTRSButton(wcm.LANG.LOGO_CONFIRM_3 || "删除", "deleteTasks(getIds());", "../../images/button_delete.gif");
							oTRSButtons.addTRSButton(wcm.LANG.LOGO_CONFIRM_5 ||"刷新", "CTRSAction_refreshMe();", "../../images/button_refresh.gif");
							oTRSButtons.draw();	
						</script>
					</td>
					<td valign=middle>
						<span style="float:right;margin-bottom:-12px;"><form name="frmSearch" onsubmit="Config_doSearch(this);return false;">
							<input type="text" name="SearchValue" id="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
							<select name="SearchKey">
								<option value="TNAME,CRUSER" WCMAnt:param="search_list.jsp.allcondition">全部</option>
								<option value="TNAME" WCMAnt:param="search_list.jsp.tname">名称</option>
								<option value="CRUSER" WCMAnt:param="search_list.jsp.cruser">创建者</option>
							</select>
							<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">
							&nbsp;
						  </form></span>
						  <script>
							document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
							</script>
						</TD>
					</TR>
				<TR>
					<TD align="left" valign="top" colspan=2>
						<div style="width:100%;height:100%;overflow:auto;">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
								<TR bgcolor="#D8E2E8" class="list_th">
									<TD bgcolor="#D8E2E8" width="50" height="20" NOWRAP><a
									href="javascript:TRSHTMLElement.selectAllByName('TaskIds');" WCMAnt:param="logo_delete.jsp.selectAll" id="selectAll">全选</a></TD>		
									<TD bgcolor="#D8E2E8" width="50px" WCMAnt:param="logo_delete.jsp.edit">编辑</TD>	
									<TD bgcolor="#D8E2E8" WCMAnt:param="logo_delete.jsp.name">名称</TD>	
									<TD bgcolor="#D8E2E8" width="60px" WCMAnt:param="logo_delete.jsp.source">数据源</TD>
									<TD bgcolor="#D8E2E8" width="140px" WCMAnt:param="logo_delete.jsp.server">Server信息</TD>
									<TD bgcolor="#D8E2E8" width="140px" WCMAnt:param="logo_delete.jsp.gateway">GateWay信息</TD>
									<TD bgcolor="#D8E2E8" width="80px" WCMAnt:param="logo_delete.jsp.cruser">创建者</TD>
									<TD bgcolor="#D8E2E8" width="120px" WCMAnt:param="logo_delete.jsp.crtime">创建时间</TD>
									<TD bgcolor="#D8E2E8" width="40px" WCMAnt:param="logo_delete.jsp.delete">删除</TD>
								</TR>
			<%
				SearchTask task = null;
				TRSServer tempserver = null;
				TRSGateway tempgateway = null;
				for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
				{//begin for
					String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
					try{
						task = (SearchTask)tasks.getAt(i-1);
					} catch(Exception ex){
						throw new WCMException(CMyString.format(LocaleServer.getString("search_list.jsp.fail2get_search_task", "获取第[{0}]个检索任务失败!"), new int[]{i}), ex);
					}
					if(task == null){
						throw new WCMException(CMyString.format(LocaleServer.getString("search_list.jsp.cannot_get_search_task", "没有找到第[{0}]个检索任务!"), new int[]{i}));
					}
					tempserver = TRSServer.findById(task.getTRServerId());
					if(tempserver == null) continue;
					tempgateway = TRSGateway.findById(task.getTRSGWId());
					if(tempgateway == null) continue;
					try{
			%>
						<TR class="<%=sRowClassName%>" rowId=<%=i%> onclick="TRSHTMLTr.onSelectedTR(this);">
							<TD style="text-align:center" NOWRAP ><INPUT TYPE="checkbox" NAME="TaskIds"
								VALUE="<%=task.getId()%>"><%=i%></TD>

							<TD align=center><span class="object_edit" onclick="edit(<%=task.getId()%>)">&nbsp;</span></TD>
							<TD><%=task.getName()%></TD>
							<TD align=center name="select"><span style="width:100%;"><%=getDesc(task.getMainTableName())%></span>
							</TD>

							<TD align=center><%=CMyString.transDisplay(tempserver.getSName())%>								
							</TD>
							<TD align=center><%=CMyString.transDisplay(tempgateway.getGName())%>								
							</TD>
							<TD align="center"><%=task.getPropertyAsString("CRUSER")%></TD>
							<TD align="center"><%=convertDateTimeValueToString( task.getPropertyAsDateTime("CRTIME"))%></TD>
							<TD align="center"><a href="#"></a><span class="object_delete" onclick="deleteTasks(<%=task.getId()%>)">&nbsp;</span></a></TD>
						</TR>
			<%
					} catch(Exception ex){
						throw new WCMException(CMyString.format(LocaleServer.getString("search_list.jsp.fail2get_logo_attr", "获取第[{0}]篇Logo的属性失败!"), new int[]{i}),ex);
					}
				}//end for	
			%>	
								</TABLE>
							</div>
						</TD>
					</TR>	
			   </TABLE>
			</TD>
		</TR>
		<tr bgcolor="#FFFFFF" style="height:30px;">
			<td valign="top">
			<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 10), currRequestHelper.getInt("PageMaxCount", 1000),LocaleServer.getString("search_list.jsp.PageNav1", "检索任务"),LocaleServer.getString("search_list.jsp.PageNav2", "个"))%>
			</td>
		</tr>
	</TABLE>
</body>
</html>
<%!
	private String getDesc(String sMainTableName){
		if(sMainTableName.equalsIgnoreCase("WCMDOCUMENT")){
			return LocaleServer.getString("search_list.jsp.doc","文档");
		}else if(sMainTableName.equalsIgnoreCase("WCMCHNLDOC")){
			return LocaleServer.getString("search_list.jsp.chnldoc","栏目文档");
		}
		return LocaleServer.getString("search_list.jsp.channel","栏目");
	}

	private String convertDateTimeValueToString(CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return sDtValue;
	}
%>