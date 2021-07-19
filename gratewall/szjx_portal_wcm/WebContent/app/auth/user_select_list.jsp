<%
/** Title:			user_select_list.jsp
 *  Description:
 *		标准WCM5.2 用户选择首页
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-26 20:17
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-26/2004-12-26
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *  History			Who				What
 *  2006-02-23		wenyh			去掉冗余的<HEAD>域
 *  Parameters:
 *		see user_select_list.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IGroupService" %>
<%@ page import="com.trs.service.IUserService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)	
	String sOrderField			= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType			= CMyString.showNull(currRequestHelper.getOrderType());
	boolean bAllUser	= currRequestHelper.getBoolean("AllUser", false);
	boolean bFilterRight =  currRequestHelper.getBoolean("FilterRight", false);

	Group currGroup = null;
	int	nGroupId = currRequestHelper.getInt("GroupId", 0);
	if(nGroupId>0){
		currGroup = Group.findById(nGroupId);
		if(currGroup == null)
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new int[]{nGroupId}));
	}
	
//5.权限校验
	
//6.业务代码
	String strWhere = "";
	if(!bAllUser){
		strWhere = "STATUS="+User.USER_STATUS_REG + " AND ISDELETED=0";
	}
	WCMFilter filter =currRequestHelper.getPageFilter(new WCMFilter("", strWhere, ""));
	Users currUsers = null;
	boolean bContainsChildGrp = currRequestHelper.getBoolean("ContainsChildGrp", false);
	IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	if(currGroup == null){
		currUsers = Users.openWCMObjs(loginUser, filter);
	}else{
		currUsers = currGroupService.getUsers(currGroup, filter, bContainsChildGrp);
	}

	if(bFilterRight && !loginUser.isAdministrator()){
		IUserService currUserService = (IUserService)DreamFactory.createObjectById("IUserService");
		boolean bIsGroupAdmin = currUserService.isAdminOfGroup(loginUser);
		if(!bIsGroupAdmin){
			currUsers = null;
		}else{
			if(currGroup == null){
				currUsers = currUserService.getCanMgrUsers(User.USER_STATUS_REG, null);
			}else{
				currUsers = currGroupService.getUsers(currGroup, filter, bContainsChildGrp);
			}
		}
	}
	
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	if(currUsers != null){
		currPager.setItemCount( currUsers.size() );
	}
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
//7.结束
	out.clear();
%>


<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE WCMAnt:param="user_select_list.jsp.selectuser">选择用户</TITLE>
    <LINK href="style.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
	<script src="../js/wcm52/CTRSHTMLElement.js"></script>
	<script src="../js/wcm52/CTRSHTMLTr.js"></script> 
	<script src="../js/wcm52/CWCMTreeDepends.js"></script>

	<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
	<%@include file="../wcm52/include/public_client_list.jsp"%>	
	<script>
		var m_bTransferName;
		try{m_bTransferName = window.parent.m_bTransferName;}catch(e){}

		function doUserId(_elCheckbox){
			if(_elCheckbox.checked){
				if(window.parent.addUserId){
					window.parent.addUserId(_elCheckbox.value, (m_bTransferName?_elCheckbox.getAttribute("UserName"):_elCheckbox.value));
				}
			}else{
				if(window.parent.removeUserId){
					window.parent.removeUserId(_elCheckbox.value, (m_bTransferName?_elCheckbox.getAttribute("UserName"):_elCheckbox.value));
				}
			}
		}

		function init(){
			if(window.parent.getUserIds){
				TRSHTMLElement.setElementCheckedByValue("UserIds", window.parent.getUserIds());
			}
		}
		window.onload = init;
		function showOffsprings(_nGroupId, bContainsChildGrp){
			window.location = "../auth/user_select_list.jsp?ContainsChildGrp="
								+(bContainsChildGrp?1:0)+"&GroupId="+_nGroupId;
		}
	</script>
<style type="text/css">
	font, font *{
		word-break:keep-all!important;
		word-wrap:normal!important;
	}
</style>
</HEAD>
<BODY leftmargin="5px" rightmargin="5px" topmargin="5px" bottommargin="5px">
	
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
<!--~--- ROW1 ---~-->
<TR>
  <TD height="26" background="../images/wcm52/tdbg.jpg">
  <!--~== TABLE2 ==~-->
  <TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
  <!--~--- ROW2 ---~-->
  <TR>
      <TD width="24"><IMG src="../images/wcm52/bite-blue-open.gif" width="24" height="24"></TD>
    <TD> <%=LocaleServer.getString("user.label.list", "用户列表")%></TD> 
	<TD class="navigation_channel_td">
    </TD> <TD width="15">&nbsp;</TD>
  </TR>
  <!--~- END ROW2 -~-->
  </TABLE>
  <!--~ END TABLE2 ~-->
  </TD>
</TR>
<!--~- END ROW1 -~-->
<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top" style="background-color:#ffffff;">
  <!--~== TABLE3 ==~-->
  <TABLE width="100%" border="0" cellpadding="2" height="100%" cellspacing="0" bgcolor="#FFFFFF">
  <!--~--- ROW4 ---~-->
  <TR>
    <TD height="25">
	  <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">
    <!--~== TABLE4 ==~-->
    <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
    <!--~--- ROW5 ---~-->
    <TR>
      <TD align="left" valign="top">	  
      </TD>
	  <%if(currGroup != null){%>
	  <TD width="20%" nowrap align="left">
		<input type="checkbox" value="1"<%=bContainsChildGrp?" checked ":" "%> onclick="showOffsprings(<%=currGroup.getId()%>, this.checked);"/><label WCMAnt:param="user_select_list.jsp.showchildren">显示子组用户</label>
	  </TD>
	  <%}%>
	  <TD width="60%" nowrap align="right">
		&nbsp;		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="UserName,TrueName" WCMAnt:param="user_select_list.jsp.all">全部</option>
			<option value="UserName" WCMAnt:param="user_select_list.jsp.username">用户名</option>
			<option value="TrueName" WCMAnt:param="user_select_list.jsp.truename">真实姓名</option>
		</select>
		<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
	  </TD>
	  <script>
		document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
	  </script>
    </TR>
    <!--~- END ROW5 -~-->
    </TABLE>
	  </form>
    <!--~ END TABLE4 ~-->
    </TD>
  </TR>
  <!--~- END ROW4 -~-->
  <!--~-- ROW10 --~-->
  <TR>
    <TD align="left" valign="top" height="20">
	<div style="OVERFLOW-Y: auto; HEIGHT: 300px" id="dvBody">
    <!--~== TABLE9 ==~-->
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <!--~-- ROW11 --~-->
    <TR bgcolor="#BEE2FF" class="list_th" style="position: relative;top:expression(this.offsetParent.scrollTop);">
      <TD width="50" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('UserIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("UserName", LocaleServer.getString("user.label.username", "用户名"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("TrueName", LocaleServer.getString("user.label.realname", "真实姓名"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("CrTime", LocaleServer.getString("user.label.registertime", "注册时间"), sOrderField, sOrderType)%></TD>
<%
	if(bAllUser){
%>
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("STATUS", LocaleServer.getString("user.label.statusnow", "当前状态"), sOrderField, sOrderType)%></TD>
<%
	}
%>
    </TR>
    <!--~ END ROW11 ~-->
<%
	User currUser = null;
	String sUserName = "";
	for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
	{//begin for
		if(currUsers == null) break;
		try{
			currUser = (User)currUsers.getAt(i-1);
			if(currUser == null)continue;
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("user_select_list.jsp.getrecord", "获取第[{0}]条记录失败！"), new int[]{i}), ex);
		}

		try{
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="UserIds" VALUE="<%=currUser.getId()%>" onclick="doUserId(this);" UserName="<%=CMyString.filterForHTMLValue(currUser.getName())%>"><%=i%></TD>
		<TD><%=PageViewUtil.toHtml(currUser.getName())%></TD>
		<TD><%=PageViewUtil.toHtml(currUser.getTrueName())%></TD>
		<TD align="center"><%=PageViewUtil.toHtml(currUser.getCrTime().toString("yy-MM-dd HH:mm"))%></TD>
<%
	if(bAllUser){
%>
		<TD align="center"><%=PageViewUtil.toHtml(currUser.getStatusString())%></TD>
<%
	}
%>
	</TR>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID,CMyString.format(LocaleServer.getString("user_select_list.jsp.getrecordattr", "获取第[{0}]条记录的属性失败！"), new int[]{i}), ex);
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
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), LocaleServer.getString("user.label.user", "用户"), LocaleServer.getString("user.label.unit", "个"))%>
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
	if(currUsers != null)
		currUsers.clear();
%>