<%
/** Title:			grpuser_list.jsp
 *  Description:
 *		WCM5.2 组织中用户列表页面。
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
 *		see grpuser_list.xml
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
<%@ page import="com.trs.infra.common.WCMRightTypes" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nGroupId = currRequestHelper.getInt("GroupId", 0);
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());
	
	Group currGroup = Group.findById(nGroupId);
	if(currGroup == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nGroupId+"]的组织失败！");
	}


//5.权限校验
	if(!(loginUser.isAdministrator()||AuthServer.hasRight(loginUser, currGroup,WCMRightTypes.GROUP_SAVE))){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限查看组织["+currGroup.getId()+"]["+currGroup.getName()+"]！");
	}
//6.业务代码
	IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	//TODO
	//如果带有查询条件时，则获取用户序列并不完整，传入添加用户程序的ID序列也不完整，则会导致显示不全，功能上不会有太大区分。
	WCMFilter filter = currRequestHelper.getPageFilter(null);
	Users currUsers = currGroupService.getUsers(currGroup, filter);
	Users groupUsers = currGroupService.getUsers(currGroup, null);

	boolean bCanDoDelSelf = loginUser.isAdministrator() || 
	(!loginUser.isAdministrator() && (loginUser.equals(currGroup.getCrUser()) || !currGroup.isAdministrator(loginUser) ));
	
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currUsers.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <TITLE>TRS WCM 5.2 <%=LocaleServer.getString("group.label.userlist", "组织中用户列表")%>页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
    <LINK href="../style/style.css" rel="stylesheet" type="text/css">
	<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
	<%@include file="../include/public_client_list.jsp"%>
	<SCRIPT SRC="../js/CWCMGroup.js"></SCRIPT>
	<SCRIPT SRC="../js/CWCMUser.js"></SCRIPT>
</HEAD>
<script src="../js/CRunningProcessBar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var m_sUserIds = "<%=groupUsers.getIdListAsString()%>";
var m_nGroupId = "<%=nGroupId%>";
var m_nCurrUserId = "<%=loginUser.getId()%>";
var m_bCanDoDelSelf = <%=bCanDoDelSelf%>;

//只提供添加用户，不支持删除。
//如果支持删除，则通过查询方式找到某一个人，然后加上这一个人，将会删掉原有其他人选
function addUserToGroup(){
	var sUserIds = m_sUserIds;

	var bCanNotDelFromGroup = !m_bCanDoDelSelf && ("," + sUserIds + ",").indexOf("," + m_nCurrUserId+",") >= 0;
	
	var oTRSAction = new CTRSAction("../auth/user_select_index.jsp");
	sUserIds = oTRSAction.doDialogAction(700, 500, sUserIds);
	if(sUserIds==null){
		return;
	}

	if(bCanNotDelFromGroup && ("," + sUserIds+",").indexOf("," + m_nCurrUserId+",") < 0){
		CTRSAction_alert("对不起，您不能将自己从当前组织中删除！因为您不是系统管理员！");
		sUserIds += "," + m_nCurrUserId;
	}


	RunningProcessBar.start();	
	

	document.frmAction.UserIds.value = sUserIds;
	document.frmAction.GroupId.value = m_nGroupId;
	document.frmAction.action		 = "../auth/user_add_to_group.jsp";
	document.frmAction.submit();


//	oTRSAction = new CTRSAction("../auth/user_add_to_group.jsp");
//	oTRSAction.setParameter("UserIds", sUserIds);
//	oTRSAction.setParameter("GroupId",  m_nGroupId);
//	oTRSAction.doDialogAction(500, 300);
//	CTRSAction_refreshMe();

	return;
}

function deleteUserFromGroup(){
	var sValues = TRSHTMLElement.getElementValueByName("UserIds");
	if(sValues==null || sValues.length==0){
		CTRSAction_alert("请您首先选择要从组中删除的用户！");
		return;
	}
	if(!CTRSAction_confirm("是否确认从组中删除用户？")){
		return;
	}
	var oTRSAction = new CTRSAction("../auth/user_delete_from_group.jsp");
	oTRSAction.setParameter("UserIds", sValues);
	oTRSAction.setParameter("GroupId", m_nGroupId);
	var bResult = oTRSAction.doDialogAction(500, 300);
	if(bResult) {
		CTRSAction_refreshMe();
	}else{
		window.top.refreshNavigator();
		window.top.gotoMain("../auth/group_list.jsp");
	}
	return;
}
</SCRIPT>
<BODY leftmargin="5px" rightmargin="5px" topmargin="5px" bottommargin="5px">
	<%if(IS_DEBUG){%>
		<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
	<%}%>

	<form name="frmAction" action="" method="post">
		<input type="hidden" name="UserIds">
		<input type="hidden" name="GroupId">
	</form>

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
				<TD width="150"><%=LocaleServer.getString("group.label.textname", "名称")%>：<SPAN class="font_bluebold"><%=currGroup.getName()%></SPAN></TD>
				<TD width="20">&nbsp;</TD>
				<TD width="20">&nbsp;</TD>
				<TD>
					<script>			
						//定义我文档管理的按钮
						var oTRSButtons = new CTRSButtons();
						// oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.addtopgroup", "新建顶级组")%>", "WCMGroup.onAddEditGroup(0, 0);", "../images/button_newtopgroup.gif", "<%=LocaleServer.getString("group.tip.addtopgroup", "新建一个顶级组织")%>");
						//oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.setgroupright", "设置权限(N)")%>", "open('organisepurview_add.htm', '', 'width=250,height=200','left=0,top=0,menubar=no,status=no,titlebar=no');", "../images/button_setright.gif","<%=LocaleServer.getString("group.tip.setgroupright", "为当前组织设定权限，快捷键N")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.modifygroup", "修改组织")%>", "WCMGroup.onAddEditGroup(<%=nGroupId%>, <%=currGroup.getParentId()%>);", "../images/button_edit.gif", "<%=LocaleServer.getString("group.tip.modifygroup", "修改当前组织")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.dropgroup", "删除组织")%>", "WCMGroup.onDeleteSingleGroup(<%=nGroupId%>);", "../images/button_delete.gif", "<%=LocaleServer.getString("group.tip.dropgroup", "删除当前组织")%>", "<%=(loginUser.isAdministrator() || loginUser.equals(currGroup.getCrUser()))?"":"bt_table_disable"%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.addchildgroup", "新建子组")%>", "WCMGroup.onAddEditGroup(0, <%=nGroupId%>);", "../images/button_newgroup.gif", "<%=LocaleServer.getString("group.tip.addchildgroup", "在当前组织下建立子组")%>");
						oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("group.tip.refresh", "刷新当前页面")%>");
						oTRSButtons.draw();	
				   </script>
				</TD>
			    </TR>
			    </TABLE>
			</TD>
		    </TR>
		    <TR>
			<TD colspan="2" class="bottomline">
				<TABLE cellspacing="0" cellpadding="0" border="0">
				<TR>
					<TD width="400">
						<TABLE cellspacing="0" cellpadding="0">
						<TR>
							<TD align="left"><%=LocaleServer.getString("group.label.textdesc", "描述")%>：<%=PageViewUtil.toHtml(currGroup.getDesc())%> </TD>
						</TR>
						<TR>
							<TD align="left"><%=LocaleServer.getString("group.label.textcreate", "创建")%>：<%=PageViewUtil.toHtml(currGroup.getCrUserName())%> <%=LocaleServer.getString("group.label.textcreatein", "创建于")%> <%=PageViewUtil.toHtml(currGroup.getCrTime().toString("yy-MM-dd HH:mm"))%> </TD>
						</TR>
						</TABLE>
					</TD>
					<TD width="410" align="right" nowrap>
					<form name="frmSearch" style="margin-top:0; margin-bottom:0" onsubmit="CTRSAction_doSearch(this);return false;">
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
			<TD>
				<script>
					//定义我文档管理的按钮
					var oTRSButtons = new CTRSButtons();
					oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.adduser", "新建用户(N)")%>", "WCMGroup.onCreateUser(<%=nGroupId%>)", "../images/button_new.gif","<%=LocaleServer.getString("group.tip.adduser", "创建一个用户，快捷键N")%>");
					oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.appenduser", "添加用户(N)")%>", "addUserToGroup()", "../images/button_addfrom.gif","<%=LocaleServer.getString("group.tip.appenduser", "添加用户到当前组织，快捷键N")%>");
					//oTRSButtons.addTRSButton("<%=LocaleServer.getString("user.button.reguser", "开通账号")%>", "WCMGroup.doOperation(3, <%=nGroupId%>);", "../images/button_reguser.gif", "<%=LocaleServer.getString("user.tip.reguser", "开通当前用户账号")%>");					
					oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.setgrpmanager", "指定为组管理员")%>", "WCMGroup.doOperation(1, <%=nGroupId%>)", "../images/button_reguser.gif","<%=LocaleServer.getString("group.tip.setgrpmanager", "指定选中用户为组管理员")%>");
					oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.cancelgrpmanager", "取消组管理员身份")%>", "WCMGroup.doOperation(2, <%=nGroupId%>)", "../images/button_forbiduser.gif","<%=LocaleServer.getString("group.tip.cancelgrpmanager", "取消选中用户组管理员身份")%>");
					oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.dropuser", "删除用户")%>", "deleteUserFromGroup()", "../images/button_delete.gif","<%=LocaleServer.getString("group.tip.dropuser", "从组织中删除用户，快捷键C")%>");
					//oTRSButtons.addTRSButton("<%=LocaleServer.getString("group.button.quitedropuser", "彻底删除用户")%>", "WCMUser.onDeleteUserPermanent('UserIds',1)", "../images/button_quitedelete.gif","<%=LocaleServer.getString("group.tip.quitedropuser", "彻底删除选中的用户，快捷键C")%>","<%=loginUser.isAdministrator()?"":"bt_table_disable"%>");
					oTRSButtons.draw();	
				   </script>
			</TD> 
			<TD width="410" align="right">
				&nbsp;
			</TD>			
		    </TR>
		    <TR>
			<TD colspan="2">
				<div style="OVERFLOW-Y: auto; HEIGHT: 300px" id="dvBody">
			    <TABLE width="100%" border="0" cellpadding="2" cellspacing="1" class="list_table">
			    <TR bgcolor="#C0DFF6" style="position: relative;top:expression(this.offsetParent.scrollTop);">
					<TD align="center"><a href="javascript:TRSHTMLElement.selectAllByName('UserIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
					<TD align="center"><%=PageViewUtil.getHeadTitle("USERNAME", LocaleServer.getString("group.label.username", "用户名"), sOrderField, sOrderType)%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("STATUS", LocaleServer.getString("group.label.statusnow", "当前状态"), sOrderField, sOrderType)%></TD> 
					<TD align="center"><%=PageViewUtil.getHeadTitle("TRUENAME", LocaleServer.getString("group.label.realname", "真实姓名/单位"), sOrderField, sOrderType)%></TD>
					<TD align="center"><%=LocaleServer.getString("group.label.isgroupadmin", "是否为组管理员")%></TD> 
			    </TR>
				<%
					int nLoginUserId = loginUser.getId();
					User  currUser	= null;
					for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){
						currUser = (User) currUsers.getAt(i-1);
						if(currUser == null) continue;
						
						int nCurrUserId = currUser.getId();
						boolean bGroupAdmin = currGroup.isAdministrator(currUser) ;


						try{
				%>
			    <TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
					<TD width="40" NOWRAP align="center"><INPUT TYPE="checkbox" NAME="UserIds" VALUE="<%=nCurrUserId%>" <%=(!bCanDoDelSelf && (nLoginUserId == nCurrUserId))?"disabled":""%>><%=i%></TD>
					<TD align="left"><A HREF="#" onClick="WCMUser.onShowUser(<%=currUser.getId()%>)"><%=PageViewUtil.toHtml(currUser.getName())%></A></TD>
					<TD align="center"><%=PageViewUtil.toHtml(currUser.getStatusString())%></TD> 
					<TD align="left"><%=PageViewUtil.toHtml(currUser.getTrueName())%></TD> 
					<TD align="center"><%=bGroupAdmin?"是":"否"%></TD> 
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