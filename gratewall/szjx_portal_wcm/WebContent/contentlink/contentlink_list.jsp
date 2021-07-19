<%--
/** Title:			contentlink_list.jsp
 *  Description:
 *		热词列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-01 15:08:21
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2005-04-01
 *	Update Logs:
 *		NZ@2005-04-01 产生此文件
 *
 *  Parameters:
 *		see contentlink_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLink" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinks" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkTypes" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.impl.ChannelService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

	int nLinkTypeId = currRequestHelper.getInt("LinkTypeId",0);

	ContentLinkType type = ContentLinkType.findById(nLinkTypeId);
	if(type == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,"没有找到[Id="+nLinkTypeId+"]的分类！");
	}

	boolean zOperateAble = AuthServer.hasRight(loginUser,null,WCMRightTypes.SYS_CONTENTLINK);
//5.权限校验

	if(!zOperateAble){
		String sErrMsg = "对不起，您没有权限管理系统热词及其分类！";
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, sErrMsg);
	}

//6.业务代码
	String sWhere = currRequestHelper.getWhereSQL();
	String sSelectFields = "LINKNAME,LINKURL,LINKTITLE";
	WCMFilter filter = currRequestHelper.getPageFilter(null);

	filter.mergeWith(new WCMFilter("","ContentLinkType="+nLinkTypeId,""));
	String sOrderFilter = filter.getOrder();
	if(CMyString.isEmpty(sOrderFilter)){
		filter.setOrder("ContentLinkId asc");
	}
	
	if(IS_DEBUG) {
		System.out.println("typeid:"+nLinkTypeId);
		System.out.println("where:"+sWhere);
		System.out.println("order:"+currRequestHelper.getOrderSQL());
	}
	
	
/*
	ChannelService currChannelService = (ChannelService)DreamFactory.createObjectById("IChannelService");
	ContentLinks currContentLinks = currChannelService.getContentLinks(currWebSite, filter);*/
	ContentLinks currContentLinks = new ContentLinks(loginUser);	
	currContentLinks.open(filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currContentLinks.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 热词列表页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
			<TD width="235"><%=type.getTypeName()%>类热词列表</TD> 
			<TD class="navigation_channel_td"></TD> 
			<TD>&nbsp;</TD>
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
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.add", "新建")%>", "addNew();", "../images/button_new.gif", "新建热词");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.drop", "删除")%>", "deleteContentLink(getContentLinkIds());", "../images/button_delete.gif", "删除选定的热词");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("contentlink.tip.refresh", "刷新当前页面")%>");
				oTRSButtons.draw();	
			</script>      
      </TD>
	  <TD width="250" nowrap align="right">
	  <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="LINKNAME,LINKURL,LINKTITLE">全部</option>
			<option value="LINKNAME"><%=LocaleServer.getString("contentlink.label.content", "内容")%></option>
			<option value="LINKTITLE"><%=LocaleServer.getString("contentlink.label.desc", "说明")%></option>
			<option value="LINKURL"><%=LocaleServer.getString("contentlink.label.url", "URL")%></option>
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
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('ContentLinkIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<!--TD bgcolor="#BEE2FF"><%=LocaleServer.getString("system.label.view", "查看")%></TD-->		
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("LINKNAME", LocaleServer.getString("contentlink.label.content", "内容"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("LINKTITLE", LocaleServer.getString("contentlink.label.desc", "说明"), sOrderField, sOrderType)%></TD>	
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("LINKURL", LocaleServer.getString("contentlink.label.url", "URL"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF"><%=LocaleServer.getString("system.label.modify", "修改")%></TD>
    </TR>
    <!--~ END ROW11 ~-->
	<%
	ContentLink currContentLink = null;
	for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
	{//begin for
		try{
			currContentLink = (ContentLink)currContentLinks.getAt(i-1);
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第["+i+"]个热词失败！", ex);
		}

		if(currContentLink == null) continue;

		try{
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="ContentLinkIds" VALUE="<%=currContentLink.getId()%>"><%=i%></TD>
		<!--TD align=center><A target="_blank" href="contentlink_show.jsp?ContentLinkId=<%=currContentLink.getId()%>"><%=LocaleServer.getString("system.label.view", "查看")%></A></TD-->
		
		<TD>&nbsp;<%=PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKNAME"))%></TD>
		<TD>&nbsp;<%=PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKTITLE"))%></TD>
		<TD>&nbsp;<%=PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKURL"))%></TD>
		

		<TD align="center">&nbsp;
		<A onclick="edit(<%=currContentLink.getId()%>);return false;" href="#"><IMG border="0" src="../images/icon_edit.gif"></A>
		</TD>
	</TR>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]个热词的属性失败！", ex);
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
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "热词", "个")%>
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
	function getContentLinkIds(){
		return TRSHTMLElement.getElementValueByName('ContentLinkIds');
	}

	function addNew(){
		var sURL = "contentlink_addedit.jsp";

		var oTRSAction = new CTRSAction(sURL);		
		oTRSAction.setParameter("ContentLinkTypeId", <%=nLinkTypeId%>);
		var bResult = oTRSAction.doDialogAction(500, 300);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}

	function edit(_nContentLinkId){	
		var oTRSAction = new CTRSAction("contentlink_addedit.jsp");
		oTRSAction.setParameter("ContentLinkId", _nContentLinkId);
		oTRSAction.setParameter("ContentLinkTypeId", <%=nLinkTypeId%>);
		var bResult = oTRSAction.doDialogAction(500, 300);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}
	
	function deleteContentLink(_sContentLinkIds){
		//参数校验
		if(_sContentLinkIds == null || _sContentLinkIds.length <= 0){
			CTRSAction_alert("请选择需要删除的热词!");
			return;
		}
		if(!CTRSAction_confirm("您确定需要删除选定的热词吗?"))
			return;
		
		var oTRSAction = new CTRSAction("contentlink_delete.jsp");
		oTRSAction.setParameter("ContentLinkIds", _sContentLinkIds);		
		oTRSAction.doDialogAction(500, 300);
		CTRSAction_refreshMe();
	}
</script>
</BODY>
</HTML>
<%
//6.资源释放
	currContentLinks.clear();
%>