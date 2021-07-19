<%
/** Title:			group_list.jsp
 *  Description:
 *		WCM5.2 组织列表页面
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
 *		see group_list.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IUserService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	boolean isAdmin = ContextHelper.getLoginUser().isAdministrator();
	Groups currGroups = null;
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());
	WCMFilter filter = currRequestHelper.getPageFilter(null);
	currGroups = Groups.openWCMObjs(loginUser, filter);
	if(currGroups == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取组织集合失败！");
	}
//5.权限校验
	IUserService aUserService = (IUserService)DreamFactory.createObjectById("IUserService");

	/*if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限查看组织列表！");
	}*/
//6.业务代码
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currGroups.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
//7.结束
	out.clear();
%>

<%!
	private String getLocatePath(Group _currGroup, User _loginUser, boolean _isAdmin)throws WCMException{
		if(_currGroup == null)
			return "";

		String sPath = String.valueOf(_currGroup.getId());
		Group parentGroup = getParent(_currGroup, _loginUser, _isAdmin);
		if(parentGroup == null)
			return sPath;

		return getLocatePath(parentGroup, _loginUser, _isAdmin) + "," + sPath;
	}

	private Group getParent(Group _currGroup, User _loginUser, boolean _isAdmin) throws WCMException {
		if(_isAdmin) {
			return _currGroup.getParent();
		} else {
			Group parentGroup = _currGroup.getParent();
			while(true) {
				if(parentGroup == null)
					break;
				if(AuthServer.hasRight(_loginUser,parentGroup))
					break;
				parentGroup = parentGroup.getParent();
			}
			return parentGroup;
		}
	}
%>
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 <%=LocaleServer.getString("group.label.list", "组织列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
	<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
	<%@include file="../include/public_client_list.jsp"%>
	<SCRIPT SRC="../js/CWCMGroup.js"></SCRIPT>
</HEAD>
<SCRIPT LANGUAGE="JavaScript">
function onViewMembers(_groupId,_locatePath){
	//var sUrl = "../auth/grpuser_list.jsp?GroupId="+_groupId;
	//window.location.replace(sUrl);
	if(window.top.refreshNavItem)
			window.top.refreshNavItem(<%=isAdmin?"2":"1"%>, "../auth/group_nav.jsp", _locatePath);
	if(window.top.gotoMain) {
			window.top.gotoMain("../auth/grpuser_list.jsp?GroupId="+_groupId);
		}
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
			<TD width="235"><%=LocaleServer.getString("group.label.list", "组织管理")%></TD> 
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
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.addgroup", "新建组织")%>", "WCMGroup.onAddEditGroup(0, 0);", "../images/button_newtopgroup.gif", "<%=LocaleServer.getString("group.tip.addgroup", "新建一个组织")%>","<%=isAdmin?"":"bt_table_disable"%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.dropgroup", "删除组织")%>", "WCMGroup.onDeleteGroup('GroupIds');", "../images/button_delete.gif", "<%=LocaleServer.getString("group.tip.addgroup", "删除选择的组织")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("group.tip.refresh", "刷新当前页面")%>");
						oTRSButtons.draw();	
				   </script>
				</TD>
				<TD width="410" align="right" nowrap>
				<form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
				<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
				<select name="SearchKey">
					<option value="GNAME,CRUSER,GDESC">全部</option>
					<option value="GNAME">组织名称</option>
					<option value="CRUSER">创建用户</option>
					<option value="GDESC">组织描述</option>
				</select>
				<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
				</form>
				</TD>
	  <script>
		document.frmSearch.SearchKey.value = "<%=currRequestHelper.getSearchKey()%>";
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
					<TD align="center"><a href="javascript:TRSHTMLElement.selectAllByName('GroupIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
					<TD align="center"><%=PageViewUtil.getHeadTitle("GNAME", LocaleServer.getString("group.label.name", "组织名"), sOrderField, sOrderType)%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("GDESC", LocaleServer.getString("group.label.desc", "组织描述"), sOrderField, sOrderType)%></TD>
					<TD align="center" NOWRAP><%=PageViewUtil.getHeadTitle("CRUSER", LocaleServer.getString("group.label.cruser", "创建用户"), sOrderField, sOrderType)%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("CRTIME", LocaleServer.getString("group.label.crtime", "创建时间"), sOrderField, sOrderType)%></TD> 
					<%
					if(loginUser.isAdministrator()) {
					%>
					<TD align="center" NOWRAP><%=LocaleServer.getString("user.label.resetpassword1", "权限管理")%></TD> 
					<%
					}
					%>
					<TD align="center" NOWRAP><%=LocaleServer.getString("system.label.modify", "修改")%></TD> 
			    </TR>
				<%
					Group  currGroup	= null;
					int nGroupId = -1;
					int nCount = currPager.getFirstItemIndex();
					for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
						currGroup = (Group) currGroups.getAt(i-1);
						if(currGroup == null || !AuthServer.hasRight(loginUser,currGroup)) continue;
						nGroupId  = currGroup.getId();
						try{
				%>
			    <TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
					<TD width="40" NOWRAP align="left"><INPUT TYPE="checkbox" NAME="GroupIds" VALUE="<%=nGroupId%>" <%=(loginUser.isAdministrator() || loginUser.equals(currGroup.getCrUser()))?"":"disabled"%>><%=nCount++%></TD>
					<TD align="left"><A HREF="#" onClick='onViewMembers(<%=nGroupId%>,"<%=getLocatePath(currGroup, loginUser, isAdmin)%>")'><%=PageViewUtil.toHtml(currGroup.getName())%></A></TD>
					<TD align="left"><%=PageViewUtil.toHtml(currGroup.getDesc())%></TD> 
					<TD align="left" NOWRAP><%=PageViewUtil.toHtml(currGroup.getCrUserName())%></TD> 
					<TD align="center"><%=PageViewUtil.toHtml(currGroup.getCrTime().toString("yy-MM-dd HH:mm"))%></TD> 
					<%
					if(loginUser.isAdministrator()) {
					%>
					<TD align="center" NOWRAP><a href="#" onClick="WCMGroup.onViewGroupRight(<%=currGroup.getId()%>);">查看</a>&nbsp;|&nbsp;<a href="#" onClick="WCMGroup.onSetGroupRight(<%=currGroup.getId()%>);">设置</a></TD> 
					<%
					}
					%>
					<TD align="center" NOWRAP><A HREF="#" onClick="WCMGroup.onAddEditGroup(<%=nGroupId%>, <%=currGroup.getParentId()%>);"><IMG border="0" src="../images/icon_edit.gif"></TD> 
			    </TR>
				<%
						}catch(Exception ex){
							throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]个组织的属性失败！", ex);
						}
					}//end for
				%>
			    </TABLE>
				</DIV>
			</TD>
		    </TR>
		    </TABLE>
		</TD>
	    </TR>
	    <TR>
		<TD class="navigation_page_td" valign="top"><%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "组织", "个")%></TD>
	    </TR>
	    </TABLE>
	</TD>
    </TR>
    </TABLE>
</BODY>
</HTML>