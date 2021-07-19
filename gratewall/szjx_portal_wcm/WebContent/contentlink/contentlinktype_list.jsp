<%--
/** Title:			contentlinktype_list.jsp
 *  Description:
 *		ContentLinkType列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WenYehui
 *  Created:		2006-12-11 13:13:09
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-11 / 2006-12-11
 *	Update Logs:
 *		WenYehui@2006-12-11 产生此文件
 *		WenYehui@2007-01-30 完善导航树的刷新及分类列表直接进入热词列表的问题
 *
 *  Parameters:
 *		see contentlinktype_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CPager"%>
<%@ page import="com.trs.presentation.util.PageHelper"%>
<%@ page import="com.trs.presentation.util.PageViewUtil"%>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

//5.权限校验

//6.业务代码
	//String sSelectFields = "TYPENAME,TYPEDESC";
	WCMFilter filter = currRequestHelper.getPageFilter(null);	
	
	/**
	  *TODO 改为以下方式
	  *IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	  *ContentLinkTypes currContentLinkTypes = currChannelService.getContentLinkTypes(currChannel, filter);
	**/
	//ContentLinkTypes currContentLinkTypes = ContentLinkTypes.openWCMObjs(loginUser, filter);
	ContentLinkTypes currContentLinkTypes = new ContentLinkTypes(loginUser);
	currContentLinkTypes.open(filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currContentLinkTypes.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2::::::热词分类列表页面</title>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px">

<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" class="list_table">
	<TR>
		<TD height="26" class="head_td">
		<TABLE width="100%" height="26" border="0" cellpadding="0"
			cellspacing="0">
			<TR>
				<TD width="24"><IMG src="../images/bite-blue-open.gif" width="24"
					height="24"></TD>
				<TD width="235">热词分类列表</TD>
				<TD class="navigation_channel_td"><A href="#"
					class="navigation_channel_link">主页</A><SPAN
					class="navigation_channel">&gt;</SPAN><A href="#"
					class="navigation_channel_link">热词分类列表</A></TD>
				<TD width="28">&nbsp;</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
	<TR>
		<TD valign="top">
		<TABLE width="100%" border="0" cellpadding="2" height="100%"
			cellspacing="0" bgcolor="#FFFFFF">
			<TR>
				<TD height="25">
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR>
						<TD align="left" valign="top">
							<script>
								//定义一个单行按钮
								var oTRSButtons = new CTRSButtons();
								oTRSButtons.addTRSButton("新建", "addNew();", "../images/button_new.gif", "新建热词分类");
								oTRSButtons.addTRSButton("删除", "deleteContentLinkType(getContentLinkTypeIds());", "../images/button_delete.gif", "删除选定的热词分类");
								oTRSButtons.addTRSButton("刷新", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "刷新当前页面");
								oTRSButtons.draw();	
							</script>
						</TD>
						<TD width="250" nowrap>
							<form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">
								&nbsp; <input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
								<select name="SearchKey">
									<option value="TYPENAME,TYPEDESC">全部</option>
									<option value="TYPENAME">分类名称</option>
									<option value="TYPEDESC">分类描述</option>
								</select> 
								<input type="submit" value="检索">
							</form>
						</TD>						
						<script>
							document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
						</script>
					</TR>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD align="left" valign="top" height="20">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
					class="list_table">
					<TR bgcolor="#BEE2FF" class="list_th">
						<TD width="40" height="20" NOWRAP><a
							href="javascript:TRSHTMLElement.selectAllByName('ContentLinkTypeIds');">全选</a></TD>
						<TD bgcolor="#BEE2FF">查看</TD>
						
						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("TYPENAME", "分类名称", sOrderField, sOrderType)%></TD>
						

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("TYPEDESC", "分类描述", sOrderField, sOrderType)%></TD>
						

						<TD bgcolor="#BEE2FF">修改</TD>
					</TR>
		<%
			ContentLinkType currContentLinkType = null;
			for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
			{//begin for
				try{
					currContentLinkType = (ContentLinkType)currContentLinkTypes.getAt(i-1);
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]个分类失败！", ex);
				}
				if(currContentLinkType == null){
					throw new WCMException("没有找到第["+i+"]个分类！");
				}

				try{
		%>
					<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
						<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="ContentLinkTypeIds"
							VALUE="<%=currContentLinkType.getId()%>"><%=i%></TD>
						<TD align=center><A target="_blank"
							href="contentlinktype_show.jsp?ContentLinkTypeId=<%=currContentLinkType.getId()%>">查看</A></TD>
						
						<TD><a href="contentlink_list.jsp?LinkTypeId=<%=currContentLinkType.getId()%>"><%=PageViewUtil.toHtml(currContentLinkType.getTypeName())%></a></TD>
						

						<TD><%=PageViewUtil.toHtml(currContentLinkType.getTypeDesc())%></TD>
						

						<TD align="center">&nbsp;<A
							onclick="edit(<%=currContentLinkType.getId()%>);return false;"
							href="#"><IMG border="0" src="../images/icon_edit.gif"></A></TD>
					</TR>
		<%
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]个分类的属性失败！", ex);
				}
			}//end for	
		%>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD class="navigation_page_td" valign="top">
					<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 20), "热词分类", "个")%>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
<script>
	function getContentLinkTypeIds(){
		return TRSHTMLElement.getElementValueByName('ContentLinkTypeIds');
	}

	function addNew(){
		var sURL = "contentlinktype_addedit.jsp";

		var oTRSAction = new CTRSAction(sURL);
		var bResult = oTRSAction.doDialogAction(500, 260);
		if(bResult){
			CTRSAction_refreshMe();
			refreshNav();
		}
	}

	function edit(_nContentLinkTypeId){			
		var oTRSAction = new CTRSAction("contentlinktype_addedit.jsp");
		oTRSAction.setParameter("ContentLinkTypeId", _nContentLinkTypeId || 0);
		var bResult = oTRSAction.doDialogAction(500, 260);
		if(bResult){
			CTRSAction_refreshMe();
			refreshNav();
		}
	}
	
	function refreshNav(){
		if(window.top.refreshNavItem){
			var ix = window.top.mainWindow.navTree.WCMNavigation.nItemCount;
			window.top.refreshNavItem(ix, "../system/otherconfig_nav.jsp", "HW");
		}
		
	}

	function deleteContentLinkType(_sContentLinkTypeIds){
		//参数校验
		if(_sContentLinkTypeIds == null || _sContentLinkTypeIds.length <= 0){
			alert("请选择需要删除的热词分类!");
			return;
		}
		if(!CTRSAction_confirm("您确定需要删除这些热词分类吗?")){
			return;
		}
		
		var oTRSAction = new CTRSAction("contentlinktype_delete.jsp");
		oTRSAction.setParameter("ContentLinkTypeIds", _sContentLinkTypeIds);		
		oTRSAction.doDialogAction(500, 200);
		CTRSAction_refreshMe();
		refreshNav();
	}
</script>
</BODY>
</HTML>
<%
//6.资源释放
	currContentLinkTypes.clear();
%>