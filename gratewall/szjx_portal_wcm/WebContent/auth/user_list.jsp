<%
/** Title:			user_list.jsp
 *  Description:
 *		WCM5.2 用户列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-16 10:36:04
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-16 / 2006-05-11
 *	Update Logs:
 *		wsw@2004-12-16 产生
 *		wenyh@2006-05-11 加入权限的设置与查看,并调整了重置密码的功能入口
 *
 *  Parameters:
 *		see user_list.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
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
	int nStatusId = currRequestHelper.getInt("StatusId", User.USER_STATUS_ALL);
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());
	Users currUsers = null;
	WCMFilter filter = currRequestHelper.getPageFilter(null);		
//5.权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限查看用户列表！");
	}
//6.业务代码
	IUserService currUserService = (IUserService)DreamFactory.createObjectById("IUserService");
	currUsers = currUserService.getUsers(nStatusId, filter);
	if(currUsers == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取用户集合失败！");
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
    <TITLE>TRS WCM 5.2 <%=LocaleServer.getString("user.label.list", "用户列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
	<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
	<%@include file="../include/public_client_list.jsp"%>
	<SCRIPT SRC="../js/CWCMGroup.js"></SCRIPT>
	<SCRIPT SRC="../js/CWCMUser.js"></SCRIPT>
</HEAD>
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
			<TD width="235"><%=LocaleServer.getString("user.label.list", "用户管理")%></TD> 
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
				<TD align="2">
					<script>			
						//定义我文档管理的按钮
						var oTRSButtons = new CTRSButtons();
					<%
						if(nStatusId==User.USER_STATUS_ALL) {
					%>
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.adduser", "新建用户")%>", "WCMGroup.onCreateUser(0);", "../images/button_new.gif", "<%=LocaleServer.getString("user.tip.adduser", "新建一个用户")%>");
					<%
						}
					%>
						//oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.setright", "设置权限(N)")%>", "open('right_set.jsp?ObjType=<%=User.OBJ_TYPE%>&ObjId=1', '', 'width=250,height=200','left=0,top=0,menubar=no,status=no,titlebar=no');", "../images/button_setright.gif","<%=LocaleServer.getString("user.tip.setright", "为当前用户设定权限，快捷键N")%>");
					<%
						if(nStatusId==User.USER_STATUS_APPLY || nStatusId==User.USER_STATUS_FORBID) {
					%>
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.reguser", "开通账号")%>", "WCMGroup.doOperation(3, 0);", "../images/button_reguser.gif", "<%=LocaleServer.getString("user.tip.reguser", "开通当前用户账号")%>");
					<%
						}
					%>
					<%
						if(nStatusId==User.USER_STATUS_APPLY || nStatusId==User.USER_STATUS_REG) {
					%>
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.forbiduser", "停用账号")%>", "WCMGroup.doOperation(4, 0);", "../images/button_forbiduser.gif", "<%=LocaleServer.getString("user.tip.forbiduser", "停用当前用户账号")%>");
					<%
						}
					%>
					<%
						if(nStatusId==User.USER_STATUS_DEL) {
					%>
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.restore", "恢复用户(R)")%>", "WCMUser.onRestoreUser('UserIds', 0);", "../images/button_restore.gif", "<%=LocaleServer.getString("user.tip.restore", "恢复当前用户，快捷键R")%>");
					<%
						} else {
					%>
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.dropuser", "删除用户(C)")%>", "WCMUser.onDeleteUserPermanent('UserIds', 0);", "../images/button_delete.gif", "<%=LocaleServer.getString("user.tip.dropuser", "删除当前用户，快捷键C")%>");
					<%
						}
					%>
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.label.resetpassword", "重置密码")%>", "WCMUser.onResetPassword(TRSHTMLElement.getElementValueByName('UserIds'), 0);", "../images/button_resetpassword.gif", "<%=LocaleServer.getString("user.label.resetpassword", "重置密码")%>","<%=loginUser.isAdministrator()?"bt_table":"bt_table_disable"%>");
						//oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.quitedropuser", "彻底删除用户(D)")%>", "WCMUser.onDeleteUserPermanent('UserIds', 1);", "../images/button_quitedelete.gif", "<%=LocaleServer.getString("user.tip.quitedropuser", "彻底删除当前用户，快捷键D")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("user.tip.refresh", "刷新当前页面")%>");
						oTRSButtons.draw();	
				   </script>
				</TD>
				<TD width="310" align="right" nowrap>
				<form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
				<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
				<select name="SearchKey">
					<option value="USERNAME,TRUENAME">全部</option>
					<option value="USERNAME">用户名</option>
					<option value="TRUENAME">真实姓名</option>
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
					<TD align="center" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('UserIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
					<TD align="center"  NOWRAP><%=PageViewUtil.getHeadTitle("USERNAME", LocaleServer.getString("user.label.username", "用户名"), sOrderField, sOrderType)%></TD> 
					<TD align="center" width=50   NOWRAP><%=PageViewUtil.getHeadTitle("STATUS", LocaleServer.getString("user.label.statusnow", "当前状态"), sOrderField, sOrderType)%></TD> 
					<TD align="center"   NOWRAP><%=PageViewUtil.getHeadTitle("TRUENAME", LocaleServer.getString("user.label.realname", "真实姓名/单位"), sOrderField, sOrderType)%></TD>
					<TD align="center" width=100 NOWRAP><%=PageViewUtil.getHeadTitle("CRTIME", LocaleServer.getString("user.label.registertime", "注册时间"), sOrderField, sOrderType)%></TD> 
					<TD align="center" width=100 NOWRAP><%=PageViewUtil.getHeadTitle("REGTIME", LocaleServer.getString("user.label.applytime", "开通时间"), sOrderField, sOrderType)%></TD> 
					<%
					if(loginUser.isAdministrator()) {
					%>
					<TD align="center" NOWRAP><%=LocaleServer.getString("user.label.resetpassword1", "权限管理")%></TD> 
					<%
					}
					%>
					<TD align="center" width=50 NOWRAP><%=LocaleServer.getString("system.label.modify", "修改用户")%></TD> 
			    </TR>
				<%
					User  currUser	= null;
					for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
						currUser = (User) currUsers.getAt(i-1);
						if(currUser == null) continue;
						int nUserId = currUser.getId();
						try{
				%>
			    <TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
					<TD width="40" NOWRAP align="center"><INPUT TYPE="checkbox" NAME="UserIds" VALUE="<%=nUserId%>" <%=(nUserId == loginUser.getId())?"disabled":""%>><%=i%></TD>
					<TD align="left" NOWRAP><A HREF="#" onClick="WCMUser.onShowUser(<%=nUserId%>)"><%=PageViewUtil.toHtml(currUser.getName())%></A></TD>
					<TD align="center" NOWRAP><%=PageViewUtil.toHtml(currUser.getStatusString())%></TD> 
					<TD align="left" NOWRAP><%=PageViewUtil.toHtml(currUser.getTrueName())%></TD> 
					<TD align="center" NOWRAP><%=PageViewUtil.toHtml(currUser.getCrTime().toString("yy-MM-dd HH:mm"))%></TD> 
					<TD align="center" NOWRAP><%=((currUser.getStatus()!=0)?PageViewUtil.toHtml(currUser.getRegTime().toString("yy-MM-dd HH:mm")):"")%></TD> 
					<%
					if(loginUser.isAdministrator()) {
					%>
					<TD align="center" NOWRAP><a href="#" <%=(loginUser.getId()==nUserId)?"disabled":"onClick=\"WCMUser.onViewUserRight("+nUserId+");return false;\""%>>查看</a>&nbsp;|&nbsp;<a href="#" <%=(loginUser.getId()==nUserId)?"disabled":"onClick=\"WCMUser.onSetUserRight("+nUserId+");return false;\""%>>设置</a></TD> 
					<%
					}
					%>
					<TD align="center" NOWRAP><A HREF="#" onClick="WCMUser.onEditUser(<%=currUser.getId()%>)"><IMG border="0" src="../images/icon_edit.gif"></A></TD> 
			    </TR>
				<%
						}catch(Exception ex){
							throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]个用户的属性失败！", ex);
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
		<TD class="navigation_page_td" valign="top"><%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 15), currRequestHelper.getInt("PageMaxCount", 1000), "用户", "个")%></TD>
	    </TR>
	    </TABLE>
	</TD>
    </TR>
    </TABLE>
</BODY>
</HTML>