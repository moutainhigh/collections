<%--
/** Title:			group_select_list.jsp
 *  Description:
 *		组织选择列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-17 21:26:16
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-17 / 2005-04-17
 *	Update Logs:
 *		WSW@2005-04-17 产生此文件
 *
 *  Parameters:
 *		see group_select_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
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
	int nGroupId = currRequestHelper.getInt("GroupId", 0);
	if(nGroupId>0){
		Group currGroup = Group.findById(nGroupId);
		if(currGroup == null)
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("select_list.obj.not.found","没有找到ID为[{0}]指定的用户组！"),new int[]{nGroupId}));
	}

	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

//5.权限校验

//6.业务代码
	String sSelectFields = "GNAME,GDESC,CRUSER";
	WCMFilter filter = currRequestHelper.getPageFilter(null);

	if(nGroupId > 0){
		WCMFilter newFilter = new WCMFilter("", "ParentId=" + nGroupId, "");
		filter.mergeWith(newFilter);
	}

	if(IS_DEBUG){
		System.out.println(filter.toSQL());
	}
	
	Groups currGroups = Groups.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currGroups.size() );
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="group_select_list.jsp.title">TRS WCM组织选择列表页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>
<script src="../js/wcm52/CTRSHTMLElement.js"></script>
	<script src="../js/data/locale/include.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function getTopWin(){
	return window.parent.getParentWin();
}

function setOperator(_nType, _nId, _sName, _bAdd){
	getTopWin().setOperator(_nType, _nId, _sName, _bAdd);
}

var m_nCount = 0;
var MAX_COUNT = 50;

function waitLoad(){
	if(m_nCount>=MAX_COUNT){
		alert("System Err!");
		return null;
	}
	m_nCount++;
	window.setTimeOut("pageOnLoad()", 30);
}


function pageOnLoad(){
	var sGroupIds = null;
	try{
		sGroupIds = getTopWin().getGroupIds();
	}catch(NaN){};

	if(sGroupIds == null) {
		m_nCount ++;
		if(m_nCount>MAX_COUNT){
			alert(wcm.LANG.INCLUDE_ALERT_3 || "页面加载超时，请重新刷新!");
			return;
		}
		window.setTimeout("pageOnLoad()",200);
		return;
	}
	TRSHTMLElement.setElementCheckedByValue("GroupIds", sGroupIds);
}


</SCRIPT>
</head>
<BODY topmargin="0" leftmargin="0" style="margin:5px" onload="pageOnLoad()">
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
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
			<option value="GNAME,GDESC" WCMAnt:param="group_select_list.jsp.all">全部</option>
			<option value="GNAME" WCMAnt:param="group_select_list.jsp.name">名称</option>
			<option value="GDESC" WCMAnt:param="group_select_list.jsp.desc">描述</option>
		</select>
		<input type="submit" value="<%=LocaleServer.getString("group.label.search", "检索")%>">		
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
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table" style="table-layout:fixed">
		<!--~-- ROW11 --~-->
		<TR bgcolor="#DADADA" class="list_th" style="position: relative;top:expression(this.offsetParent.scrollTop);">
			<TD bgcolor="#DADADA"  width="50" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('GroupIds');" WCMAnt:param="group_select_list.jsp.selectAll">全选</a></TD>
			<TD style="word-break:break-all" bgcolor="#DADADA"><%=PageViewUtil.getHeadTitle("GNAME", LocaleServer.getString("group.label.name", "组织名"), sOrderField, sOrderType)%></TD>
			<TD style="word-break:break-all" bgcolor="#DADADA"><%=PageViewUtil.getHeadTitle("GDESC", LocaleServer.getString("group.label.desc", "组织描述"), sOrderField, sOrderType)%></TD>
			<TD style="word-break:break-all" bgcolor="#DADADA"><%=PageViewUtil.getHeadTitle("CRUSER", LocaleServer.getString("group.label.cruser", "创建用户"), sOrderField, sOrderType)%></TD>
		</TR>
		<!--~ END ROW11 ~-->
<%
		Group currGroup = null;
		for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
		{//begin for
			try{
				currGroup = (Group)currGroups.getAt(i-1);
			} catch(Exception ex){
				throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("select_list.org.getFailed","获取第[{0}]篇组织失败！"),new int[]{i}), ex);
			}

			if(currGroup == null) continue;

			try{
%>
		<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);" style="height:auto">
			<TD width="40" NOWRAP><INPUT TYPE="checkbox" GrpName="<%=PageViewUtil.toHtmlValue(currGroup.getName())%>" NAME="GroupIds" VALUE="<%=currGroup.getId()%>" onclick="setOperator(<%=Group.OBJ_TYPE%>, <%=currGroup.getId()%>, this.getAttribute('GrpName'), this.checked)"><%=i%></TD>
			<TD><%=PageViewUtil.toHtml(currGroup.getPropertyAsString("GNAME"))%></TD>
			<TD><%=PageViewUtil.toHtml(currGroup.getPropertyAsString("GDESC"))%></TD>
			<TD><%=PageViewUtil.toHtml(currGroup.getPropertyAsString("CRUSER"))%></TD>
		</TR>
<%
			} catch(Exception ex){
				throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, CMyString.format(LocaleServer.getString("select_list.attribute.getFailed","获取第[{0}]篇组织的属性失败！"),new int[i]), ex);
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
		<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), LocaleServer.getString("group.label.org", "组织"), LocaleServer.getString("group.label.ge", "个"))%>
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
	function getGroupIds(){
		return TRSHTMLElement.getElementValueByName('GroupIds');
	}
</script>
</BODY>
</HTML>
<%
//6.资源释放
	currGroups.clear();
%>