<%
/** Title:			role_list.jsp
 *  Description:
 *		WCM5.2 角色列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-16 10:36:04
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-16 / 2006-05-11	
 *	Update Logs:
 *		wsw@2004-12-16 产生
 *		wenyh@2006-05-11 加入权限的设置与查看
 *
 *  Parameters:
 *		see role_list.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Roles" %>
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
//4.初始化(获取数据)
	Roles currRoles = null;
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());
	WCMFilter filter = currRequestHelper.getPageFilter(new WCMFilter("","VIEWABLE=1",""));
	currRoles = Roles.openWCMObjs(loginUser, filter);
	if(currRoles == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取角色集合失败！");
	}
//5.权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限查看角色列表！");
	}
//6.业务代码
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currRoles.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 <%=LocaleServer.getString("role.label.list", "角色列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
	<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
	<%@include file="../include/public_client_list.jsp"%>
	<SCRIPT SRC="../js/CWCMRole.js"></SCRIPT>
</HEAD>
<SCRIPT LANGUAGE="JavaScript">
function onViewMembers(_roleId){
	//var sUrl = "../auth/roleuser_list.jsp?RoleId="+_roleId;
	//window.location.replace(sUrl);
	var sRoleId=_roleId.toString();
	if(window.top.refreshNavItem)
			window.top.refreshNavItem(3, "../auto/role_nav.jsp", sRoleId);
	if(window.top.gotoMain)
			window.top.gotoMain("../auth/roleuser_list.jsp?RoleId="+_roleId);
}

function onSetRight(){
	CTRSAction_alert("set right");
}
</SCRIPT>
<BODY leftmargin="5px" rightmargin="5px" topmargin="5px" bottommargin="5px">
	<%if(IS_DEBUG){%>
		<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
	<%}%>
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <TR>
	<TD height="26" class="head_td">
	    <TABLE width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
	    <TR>
		    <TD width="24"><IMG src="../images/bite-blue-open.gif" width="24" height="24"></TD>
			<TD width="235"><%=LocaleServer.getString("role.label.list", "角色管理")%></TD> 
			<TD class="navigation_channel_td">&nbsp;</TD> 
			<TD width="28">&nbsp;</TD>
	    </TR>
	    </TABLE>
	</TD>
    </TR>
    <TR>
	<TD valign="top">
	    <TABLE width="100%" border="0" cellpadding="2" height="100%" cellspacing="0" bgcolor="#FFFFFF">
	    <TR>
		<TD align="left" valign="top" height="20">
		    <TABLE width="100%" border="0" cellspacing="5" cellpadding="0">
		    <TR>
			    <TD colspan="2"></TD>
		    </TR>
		    <TR>
			<TD colspan="2" class="bottomline">
			    <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
			    <TR>
				<!-- TD width="150">名称：<SPAN class="font_bluebold"></SPAN></TD -->
				<TD>
					<script>			
						//定义我文档管理的按钮
						var oTRSButtons = new CTRSButtons();
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.add", "新建角色")%>", "WCMRole.onAddEditRole(0);", "../images/button_new.gif", "<%=LocaleServer.getString("role.tip.add", "新建一个角色")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.drop", "删除角色")%>", "WCMRole.onDeleteRole('RoleIds');", "../images/button_delete.gif", "<%=LocaleServer.getString("role.tip.drop", "删除选中角色")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("role.tip.refresh", "刷新当前页面")%>");
						oTRSButtons.draw();	
				   </script>
				</TD>
				<TD width="410" align="right" nowrap>
				<form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
				<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
				<select name="SearchKey">
					<option value="ROLENAME,CRUSER">全部</option>
					<option value="ROLENAME">角色名称</option>
					<option value="CRUSER">创建用户</option>
				</select>
				<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
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
			<TD colspan="2">
				<div style="OVERFLOW-Y: auto; HEIGHT: 360px" id="dvBody">
			    <TABLE width="100%" border="0" cellpadding="2" cellspacing="1" class="list_table">
			    <TR bgcolor="#C0DFF6" style="position: relative;top:expression(this.offsetParent.scrollTop);">
					<TD align="center"><a href="javascript:TRSHTMLElement.selectAllByName('RoleIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
					<TD align="center" NOWRAP><%=PageViewUtil.getHeadTitle("ROLENAME", LocaleServer.getString("role.label.name", "角色名"), sOrderField, sOrderType)%></TD> 
					<TD align="center" NOWRAP><%=PageViewUtil.getHeadTitle("ROLEDESC", LocaleServer.getString("role.label.desc", "角色描述"), sOrderField, sOrderType)%></TD>
					<TD align="center" NOWRAP><%=PageViewUtil.getHeadTitle("CRUSER", LocaleServer.getString("role.label.cruser", "创建用户"), sOrderField, sOrderType)%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("CRTIME", LocaleServer.getString("role.label.crtime", "创建时间"), sOrderField, sOrderType)%></TD> 
					<%
					if(loginUser.isAdministrator()) {
					%>
					<TD align="center" NOWRAP><%=LocaleServer.getString("user.label.resetpassword1", "权限管理")%></TD> 
					<%
					}
					%>
					<!-- <TD align="center">< % =LocaleServer.getString("role.label.setright", "设置权限")% ></TD> --> 
					<TD align="center" NOWRAP><%=LocaleServer.getString("system.label.modify", "修改")%></TD> 
			    </TR>
				<%
					Role currRole = null;
					int nRole = -1;
					for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
						currRole = (Role) currRoles.getAt(i-1);
						if(currRole == null) continue;
						nRole = currRole.getId();
						try{
				%>
			    <TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
					<TD width="40" NOWRAP align="center"><INPUT TYPE="checkbox" NAME="RoleIds" VALUE="<%=nRole%>" <%=currRole.isRemoveable()?"":"disabled"%>><%=i%></TD>
					<TD align="left" NOWRAP><A HREF="#" onClick="onViewMembers(<%=nRole%>)"><%=PageViewUtil.toHtml(currRole.getName())%></A></TD>
					<TD align="left"><%=PageViewUtil.toHtml(currRole.getDesc())%></TD> 
					<TD align="center" NOWRAP><%=PageViewUtil.toHtml(currRole.getCrUserName())%></TD> 
					<TD align="center" NOWRAP><%=PageViewUtil.toHtml(currRole.getCrTime().toString("yy-MM-dd HH:mm"))%></TD> 
					<%
					if(loginUser.isAdministrator()) {
					%>
					<TD align="center" NOWRAP><a href="#" <%=currRole.isRemoveable()?"onClick=\"WCMRole.onViewRoleRight("+nRole+");\"":"disabled"%>>查看</a>&nbsp;|&nbsp;<a href="#" <%=currRole.isRemoveable()?"onClick=\"WCMRole.onSetRoleRight("+nRole+");\"":"disabled"%>>设置</a></TD> 
					<%
					}
					%>					
					<!-- <TD align="center"><A HREF="#" onClick="onSetRight(< %=nRole% >)"><IMG border="0" src="../images/icon_edit.gif"></A></TD> -->
					<TD align="center" NOWRAP><A HREF="#" <%=currRole.isRemoveable()?"onClick=\"WCMRole.onAddEditRole("+nRole+", 0);\"":"disabled"%>><%=LocaleServer.getString("system.label.modify", "修改")%></A></TD> 
			    </TR>
				<%
						}catch(Exception ex){
							throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]个角色的属性失败！", ex);
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
	    <TR>
		<TD class="navigation_page_td" valign="top"><%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "角色", "个")%></TD>
	    </TR>
	    </TABLE>
	</TD>
    </TR>
    </TABLE>
</BODY>
</HTML>