<%
/** Title:			roleuser_list.jsp
 *  Description:
 *		WCM5.2 角色中用户列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-16 10:36:04
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-16 / 2004-12-16	
 *	Update Logs:
 *		wsw@2004-12-16 产生
 *
 *  Parameters:
 *		see roleuser_list.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IRoleService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nRoleId = currRequestHelper.getInt("RoleId", 0);
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());
	
	Role currRole = Role.findById(nRoleId);
	if(currRole == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nRoleId+"]的角色失败！");
	}
//5.权限校验
	if(!AuthServer.hasRight(loginUser, currRole)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限查看角色["+currRole.getId()+"]["+currRole.getName()+"]！");
	}
//6.业务代码
	IRoleService currRoleService = (IRoleService)DreamFactory.createObjectById("IRoleService");
	//TODO
	//如果带有查询条件时，则获取用户序列并不完整，传入添加用户程序的ID序列也不完整，则会导致显示不全，功能上不会有太大区分。
	WCMFilter filter = currRequestHelper.getPageFilter(null);
	Users currUsers = currRoleService.getUsers(currRole, filter);
	Users roleUsers = currRoleService.getUsers(currRole, null);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currUsers.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 <%=LocaleServer.getString("role.label.userlist", "角色中用户列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
	<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
	<%@include file="../include/public_client_list.jsp"%>
	<SCRIPT SRC="../js/CWCMRole.js"></SCRIPT>
	<SCRIPT SRC="../js/CWCMUser.js"></SCRIPT>
</HEAD>
<script src="../js/CRunningProcessBar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var ROLE_ADMINISTRATOR = "1";
var m_sUserIds = "<%=roleUsers.getIdListAsString()%>";
var m_nRoleId  = "<%=nRoleId%>";
var m_nCurrUserId = "<%=loginUser.getId()%>";
//只提供添加用户，不支持删除。
//如果支持删除，则通过查询方式找到某一个人，然后加上这一个人，将会删掉原有其他人选
function addUserToRole(){
	var sUserIds = m_sUserIds;
	var bNotDelFromAdministrator = m_nRoleId == 1 && ("," + sUserIds+",").indexOf("," + m_nCurrUserId+",") >= 0;

	var oTRSAction = new CTRSAction("../auth/user_select_index.jsp");
	sUserIds = oTRSAction.doDialogAction(700, 540, sUserIds);
	if(sUserIds==null){
		return;
	}

	if(bNotDelFromAdministrator && ("," + sUserIds+",").indexOf("," + m_nCurrUserId+",") < 0){
		CTRSAction_alert("对不起，您不能将自己从系统管理员中删除！");
		sUserIds += "," + m_nCurrUserId;
	}

	RunningProcessBar.start();	

	document.frmAction.UserIds.value	= sUserIds;
	document.frmAction.RoleId.value		= m_nRoleId;
	document.frmAction.action			= "../auth/user_add_to_role.jsp";
	document.frmAction.submit();
}


function deleteUserFromRole(){
	var sValues = TRSHTMLElement.getElementValueByName("UserIds");
	if(sValues==null || sValues.length==0){
		CTRSAction_alert("请您首先选择从角色中删除的用户！");
		return;
	}
	if(!CTRSAction_confirm("是否确认从角色中删除用户？")){
		return;
	}
	var oTRSAction = new CTRSAction("../auth/user_delete_from_role.jsp");
	oTRSAction.setParameter("UserIds", sValues);
	oTRSAction.setParameter("RoleId", m_nRoleId);
	oTRSAction.doDialogAction(500, 300);
	CTRSAction_refreshMe();
	return;
}
</SCRIPT>
<BODY leftmargin="5px" rightmargin="5px" topmargin="5px" bottommargin="5px">
	<%if(IS_DEBUG){%>
		<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
	<%}%>

	<form name="frmAction" action="" method="post">
		<input type="hidden" name="UserIds">
		<input type="hidden" name="RoleId">
	</form>

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
				<TD width="150"><%=LocaleServer.getString("role.label.textname", "名称")%>：<SPAN class="font_bluebold"><%=currRole.getName()%></SPAN></TD>
				<TD>
					<script>			
						//定义我文档管理的按钮
						var oTRSButtons = new CTRSButtons();
						//oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.add", "新建角色")%>", "WCMRole.onAddEditRole(0);", "../images/button_new.gif", "<%=LocaleServer.getString("role.tip.add", "新建一个角色")%>");
						//oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.setright", "设置权限(N)")%>", "open('organisepurview_add.htm', '', 'width=250,height=200','left=0,top=0,menubar=no,status=no,titlebar=no');", "../images/button_setright.gif","<%=LocaleServer.getString("role.tip.setright", "为当前角色设定权限，快捷键N")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.modify", "修改角色(N)")%>", "WCMRole.onAddEditRole(<%=nRoleId%>, 0);", "../images/button_edit.gif", "<%=LocaleServer.getString("role.tip.modify", "修改当前角色，快捷键N")%>","<%=((nRoleId==1)||(nRoleId==2))?"bt_table_disable":""%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.drop", "删除角色(C)")%>", "WCMRole.onDeleteSingleRole(<%=nRoleId%>);", "../images/button_delete.gif", "<%=LocaleServer.getString("role.tip.drop", "删除当前角色，快捷键C")%>","<%=((nRoleId==1)||(nRoleId==2))?"bt_table_disable":""%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("role.tip.refresh", "刷新当前页面")%>");
						oTRSButtons.draw();	
				   </script>
				</TD>
			    </TR>
			    </TABLE>
			</TD>
		    </TR>
		    <TR>
			<TD colspan="2" class="bottomline">
			    <TABLE cellspacing="0" cellpadding="0">
				<TR>
					<TD align="left"><%=LocaleServer.getString("role.label.texttype","类型")%>：<%=currRole.isSystemRole()? LocaleServer.getString("role.label.systemrole","系统角色"):LocaleServer.getString("role.label.nonsystemrole","非系统角色")%> </TD>
				</TR>
			    <TR>
					<TD align="left"><%=LocaleServer.getString("role.label.textdesc", "描述")%>：<%=PageViewUtil.toHtml(currRole.getDesc())%> </TD>
			    </TR>
			    <TR>
					<TD align="left"><%=LocaleServer.getString("role.label.textcreate", "创建")%>：<%=PageViewUtil.toHtml(currRole.getCrUserName())%> <%=LocaleServer.getString("role.label.textcreatein", "创建于")%> <%=PageViewUtil.toHtml(currRole.getCrTime().toString("yy-MM-dd HH:mm"))%> </TD>
			    </TR>
			    </TABLE>
			</TD>
		    </TR>
		    <TR>
			<TD>
				<script>
					//定义我文档管理的按钮
					var oTRSButtons = new CTRSButtons();
					//oTRSButtons.addTRSButton("新建用户(N)", "onCreateUser(<%=nRoleId%>)", "../images/button_default.gif","采集新的文档，快捷键N");
					oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.appenduser", "添加用户(N)")%>", "addUserToRole()", "../images/button_addfrom.gif","<%=LocaleServer.getString("role.tip.appenduser", "添加用户到当前角色中来，快捷键N")%>");
					oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.dropuser", "删除用户")%>", "deleteUserFromRole()", "../images/button_delete.gif","<%=LocaleServer.getString("role.tip.dropuser", "从当前角色中删除该用户，快捷键C")%>");
					//oTRSButtons.addTRSButton("<%=LocaleServer.getString("role.button.quitedropuser", "彻底删除用户")%>", "WCMUser.onDeleteUserPermanent('UserIds',1)", "../images/button_quitedelete.gif","<%=LocaleServer.getString("role.tip..quitedropuser", "彻底删除选中用户，快捷键C")%>");
					oTRSButtons.draw();	
				   </script>
			</TD> 
			<TD width="410" align="right" nowrap>
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
		    <TR>
			<TD colspan="2">
			    <div style="OVERFLOW-Y: auto; HEIGHT: 295px" id="dvBody">
				<TABLE width="100%" border="0" cellpadding="2" cellspacing="1" class="list_table">
			    <TR bgcolor="#C0DFF6" style="position: relative;top:expression(this.offsetParent.scrollTop);">
					<TD align="center"><a href="javascript:TRSHTMLElement.selectAllByName('UserIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
					<TD align="center"><%=PageViewUtil.getHeadTitle("USERNAME", LocaleServer.getString("role.label.username", "用户名"), sOrderField, sOrderType)%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("STATUS", LocaleServer.getString("role.label.statusnow", "当前状态"), sOrderField, sOrderType)%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("TRUENAME", LocaleServer.getString("role.label.realname", "真实姓名/单位"), sOrderField, sOrderType)%></TD>
					<TD align="center"><%=LocaleServer.getString("role.label.useremail", "电子邮件")%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("REGTIME", LocaleServer.getString("role.label.userapplytime", "申请时间"), sOrderField, sOrderType)%></TD> 
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
					<TD width="40" NOWRAP align="center"><INPUT TYPE="checkbox" NAME="UserIds" VALUE="<%=nUserId%>" <%=( nRoleId == 1 && (nUserId == loginUser.getId()))?"disabled":""%>><%=i%></TD>
					<TD align="left"><A HREF="#" onClick="WCMUser.onShowUser(<%=nUserId%>)"><%=PageViewUtil.toHtml(currUser.getName())%></A></TD>
					<TD align="center"><%=PageViewUtil.toHtml(currUser.getStatusString())%></TD> 
					<TD align="left"><%=PageViewUtil.toHtml(currUser.getTrueName())%></TD> 
					<TD align="center"><%=PageViewUtil.toHtml(currUser.getEmail())%></TD> 
					<TD align="center"><%=PageViewUtil.toHtml(currUser.getRegTime().toString("yy-MM-dd HH:mm"))%></TD> 
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