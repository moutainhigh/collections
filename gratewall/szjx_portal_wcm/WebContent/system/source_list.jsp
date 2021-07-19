<%--
/** Title:			test.jsp
 *  Description:
 *		文档来源列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-01 15:34:11
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2005-04-01
 *	Update Logs:
 *		CH@2005-04-01 产生此文件
 *		wenyh@2006-05-23 添加所属站点属性
 *
 *  Parameters:
 *		see test.xml
 *
 */
--%> 
 
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.resource.Security" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.components.wcm.resource.Sources" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
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

	if (!AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_DOCSOURCE)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限查看文档来源列表！");
	}
//6.业务代码
	String sSelectFields = "SOURCEID,SRCNAME,SRCDESC,SRCLINK,SECURITY,CRUSER,CRTIME";
	WCMFilter filter = currRequestHelper.getPageFilter(null);

	Sources currSources = Sources.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currSources.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2<%=LocaleServer.getString("document.label.list_of_source", "文档来源列表页面")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
		<TD width="235"><%=LocaleServer.getString("document.label.list_of_source", "文档来源列表")%></TD> 
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
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.add", "新建")%>", "addNew();", "../images/button_new.gif", "<%=LocaleServer.getString("document.tip.add_of_source", "新建一个文档来源")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.drop", "删除")%>", "deleteSource(getSourceIds());", "../images/button_delete.gif", "<%=LocaleServer.getString("document.tip.drop_of_source", "删除选定的文档来源")%>");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("document.tip.refresh_of_source", "刷新当前页面")%>");
				oTRSButtons.draw();	
			</script>      
      </TD>
	  <TD width="250" nowrap>
	  <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="SRCNAME,SRCDESC,SRCLINK,CRUSER">全部</option>
			<option value="SRCNAME">来源名称</option>
			<option value="SRCDESC">来源描述</option>
			<option value="SRCLINK">来源链接</option>
			<option value="CRUSER">创建用户</option>
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
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('SourceIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SRCNAME", LocaleServer.getString("document.label.name_of_source", "来源名称"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SRCDESC", LocaleServer.getString("document.label.desc_of_source", "来源描述"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SRCLINK", LocaleServer.getString("document.label.link_of_source", "来源链接"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("SITEID", LocaleServer.getString("document.label.site_of_source", "所属站点"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("CRUSER", LocaleServer.getString("document.label.cruser_of_source", "创建用户"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=PageViewUtil.getHeadTitle("CRTIME", LocaleServer.getString("document.label.crtime_of_source", "创建时间"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF" nowrap><%=LocaleServer.getString("system.label.modify", "修改")%></TD>
    </TR>
    <!--~ END ROW11 ~-->
	<%
	Source currSource = null;
	Security currSecurity = null;
	for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
	{//begin for
		try{
			currSource = (Source)currSources.getAt(i-1);
			if(currSource == null) continue;
			currSecurity = currSource.getSecurity();
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第["+i+"]篇文档来源失败！", ex);
		}

		try{
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="SourceIds" VALUE="<%=currSource.getId()%>"><%=i%></TD>
		<TD>	<A onclick="viewSource(<%=currSource.getId()%>)" href="#"><%=PageViewUtil.toHtml(currSource.getPropertyAsString("SRCNAME"))%></A></TD>
		<TD><%=PageViewUtil.toHtml(currSource.getPropertyAsString("SRCDESC"))%></TD>
		<TD><%=PageViewUtil.toHtml(currSource.getPropertyAsString("SRCLINK"))%></TD>
		<TD><%=PageViewUtil.toHtml(getHostSite(currSource.getPropertyAsInt("SITEID",0)))%></TD>
		<TD NOWRAP><%=PageViewUtil.toHtml(currSource.getPropertyAsString("CRUSER"))%></TD>
		<TD NOWRAP><%=PageViewUtil.toHtml(currSource.getCrTime().toString("yy-MM-dd HH:mm"))%></TD>
		<TD align="center">&nbsp;<A href="#" onclick="edit(<%=currSource.getId()%>);return false;"><IMG border="0" src="../images/icon_edit.gif"></A></TD>
	</TR>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]篇文档来源的属性失败！", ex);
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
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "文档来源", "个")%>
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
	function getSourceIds(){
		return TRSHTMLElement.getElementValueByName('SourceIds');
	}

	function addNew(){
		var sURL = "source_addedit.jsp";

		var oTRSAction = new CTRSAction(sURL);
		var bResult = oTRSAction.doDialogAction(500, 360);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}

	function edit(_nSourceId){
		var oTRSAction = new CTRSAction("source_addedit.jsp");
		oTRSAction.setParameter("SourceId", _nSourceId);
		var bResult = oTRSAction.doDialogAction(500, 360);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}
	
	function deleteSource(_sSourceIds){
		//参数校验
		if(_sSourceIds == null || _sSourceIds.length <= 0){
			CTRSAction_alert("请选择需要删除的文档来源!");
			return;
		}
		if(!CTRSAction_confirm("您确定需要删除这些文档来源吗?"))
			return;
		
		var oTRSAction = new CTRSAction("source_delete.jsp");
		oTRSAction.setParameter("SourceIds", _sSourceIds);		
		oTRSAction.doDialogAction(500, 300);
		CTRSAction_refreshMe();
	}

	function viewSource(nSourceId){

		var oTRSAction = new CTRSAction("../system/source_show.jsp");
		oTRSAction.setParameter("SourceId", nSourceId);
		oTRSAction.doOpenWinAction(400,300);
	
	}

	function onSetRight(){
		var oTRSAction = new CTRSAction("../auth/sysattruser_list.jsp");
		oTRSAction.setParameter("Type", 1);
		oTRSAction.doOpenWinAction(800,500);
	
	}

</script>
</BODY>
</HTML>
<%!
	private String getHostSite(int _nSiteId) throws WCMException{
		WebSite site = WebSite.findById(_nSiteId);
		if(site == null){
			return "";
		}

		return site.getDesc();
	}
%>
<%
//6.资源释放
	currSources.clear();
%>