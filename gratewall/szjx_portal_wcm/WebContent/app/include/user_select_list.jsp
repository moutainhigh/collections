<%
/** Title:			user_select_main.jsp
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
 *
 *  Parameters:
 *		see user_select_main.xml
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
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)	
	String sOrderField			= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType			= CMyString.showNull(currRequestHelper.getOrderType());
	boolean bTransferName	= currRequestHelper.getInt("TransferName", 0) > 0;
	boolean bAllUser	= currRequestHelper.getBoolean("AllUser", false);
	boolean bFromProcess	= currRequestHelper.getBoolean("FromProcess", false);

	Group currGroup = null;
	int	nGroupId = currRequestHelper.getInt("GroupId", 0);
	if(nGroupId>0){
		currGroup = Group.findById(nGroupId);
		if(currGroup == null)
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("select_list.id.zero","没有找到ID为[{0}]指定的用户组！"),new int[]{nGroupId}));
	}
	
//5.权限校验
	
//6.业务代码
	String strWhere = currRequestHelper.getWhereSQL();
	if(!bAllUser){
		if(strWhere==null || strWhere.equals("")) {
			strWhere = "STATUS="+User.USER_STATUS_REG + " AND ISDELETED=0";
		} else {
			strWhere = "("+strWhere+") AND (STATUS="+User.USER_STATUS_REG + " AND ISDELETED=0)";
		}
	}
	WCMFilter filter = currRequestHelper.getPageFilter(new WCMFilter("", strWhere, ""));
	Users currUsers = null;
	if(currGroup == null){
		currUsers = Users.openWCMObjs(loginUser, filter);
	}else{
		IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
		currUsers = currGroupService.getUsers(currGroup, filter);
	}
	
	
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currUsers.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
//7.结束
	out.clear();
%>


<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="user_select_list.jsp.trswcmuserselectlistpage">TRS WCM 用户选择列表页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的Javascript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script LANGUAGE="Javascript" src="../js/wcm52/CTRSHashtable.js"></script>
<script LANGUAGE="Javascript" src="../js/wcm52/CTRSRequestParam.js"></script>
<script LANGUAGE="Javascript" src="../js/wcm52/CTRSAction.js"></script>
<script LANGUAGE="Javascript" src="../js/wcm52/CTRSHTMLTr.js"></script>
<script LANGUAGE="Javascript" src="../js/wcm52/CTRSHTMLElement.js"></script>
<script LANGUAGE="Javascript" src="../js/wcm52/CTRSButton.js"></script>
<!-- 设置参数 -->
<%=currRequestHelper.toTRSRequestParam()%>
<script src="../js/wcm52/CTRSHTMLElement.js"></script>
<script LANGUAGE="Javascript">
function getTopWin(){
	return window.parent.getParentWin();
}

function setOperator(_nType, _nId, _sName, _bAdd){
	getTopWin().setOperator(_nType, _nId, _sName, _bAdd);
}


var m_nCount = 0;
var MAX_COUNT = 50;
	

function pageOnLoad(){
	var sUserIds = null;
	try{
		sUserIds = getTopWin().getUserIds();
	}catch(NaN){};

	if(sUserIds == null) {
		m_nCount ++;
		if(m_nCount>MAX_COUNT){
			CTRSAction_alert("<%=LocaleServer.getString("uder.select.list.retimeload","页面加载超时，请重新刷新!")%>");
			return;
		}
		window.setTimeout("pageOnLoad()",200);
		return;
	}
	TRSHTMLElement.setElementCheckedByValue("UserIds", sUserIds);
}

function waitLoad(){
	if(m_nCount>=MAX_COUNT){
		CTRSAction_alert("System Err!");
		return null;
	}
	m_nCount++;
	window.setTimeOut("pageOnLoad()", 30);
}
</script>
</HEAD>
<BODY leftmargin="5px" rightmargin="5px" topmargin="5px" bottommargin="5px" onload="pageOnLoad()">
	
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="user_select_list.jsp.trswqcmuserselectlistpage">TRS WCM 用户选择列表页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
.grid_row_active{
	background-color:#FFFFCC;
	font-size:14px;
	height:29px;
	line-height: 29px;
	vertical-align:bottom;
	font-family: 'Times New Roman';
}
.list_tr, .list_tr_selected{
	height: 29px;		
	line-height: 29px;
	font-family: 'Times New Roman';
}
</style>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px">	
	
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="98%" border="0" cellpadding="0" cellspacing="1" class="list_table">
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
	  <TD width="100%" nowrap align="right">
      <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="UserName,TrueName" WCMAnt:param="user_select_list.jsp.all">全部</option>
			<option value="UserName" WCMAnt:param="user_select_list.jsp.username">用户名</option>
			<option value="TrueName" WCMAnt:param="user_select_list.jsp.trueusername">真实姓名</option>
		</select>
		<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
	  </form>
	  </TD>
	  <script>
		document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
	  </script>
		<TD align="right" valign="middle">
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
	<div style="OVERFLOW-Y: auto; HEIGHT: 300px" id="dvBody">
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <!--~-- ROW11 --~-->
    <TR bgcolor="#DADADA" class="list_th" style="position: relative;top:expression(this.offsetParent.scrollTop);">
      <TD width="50" height="20" NOWRAP bgcolor="#DADADA"><a href="javascript:TRSHTMLElement.selectAllByName('UserIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<TD bgcolor="#DADADA"><%=PageViewUtil.getHeadTitle("UserName", LocaleServer.getString("user.label.username", "用户名"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#DADADA"><%=PageViewUtil.getHeadTitle("TrueName", LocaleServer.getString("user.label.realname", "真实姓名"), sOrderField, sOrderType)%></TD>
<%
	if(bAllUser){
%>
		<TD  bgcolor="#DADADA"><%=PageViewUtil.getHeadTitle("STATUS", LocaleServer.getString("user.label.statusnow", "当前状态"), sOrderField, sOrderType)%></TD>
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
		try{
			currUser = (User)currUsers.getAt(i-1);
			if(currUser == null)continue;
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("select_list.record.getFailed","获取第[{0}]条记录失败！"), new int[]{i}), ex);
		}

		if(bTransferName){
			sUserName = " UserName=\""+CMyString.filterForHTMLValue(currUser.getName())+"\"";
		}else{
			sUserName = "";
		}
		String sTrueName = currUser.getName();
		if(bFromProcess && currUser.getTrueName() != null && !currUser.getTrueName().equals("")){
			sTrueName = currUser.getTrueName();
		}

		try{
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" NAME="UserIds" VALUE="<%=currUser.getId()%>" onclick="setOperator(<%=User.OBJ_TYPE%>, <%=currUser.getId()%>, '<%=sTrueName%>', this.checked)" ><%=i%></TD>
		<TD><%=PageViewUtil.toHtml(currUser.getName())%></TD>
		<TD><%=PageViewUtil.toHtml(currUser.getTrueName())%></TD>
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
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, CMyString.format(LocaleServer.getString("select_list.record.attribute.getFailed","获取第[{0}]条记录的属性失败！"),new int[]{i}), ex);
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
	<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), LocaleServer.getString("user.label.userfanye", "用户"), LocaleServer.getString("user.label.numutin", "个"))%>
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
	currUsers.clear();
%>