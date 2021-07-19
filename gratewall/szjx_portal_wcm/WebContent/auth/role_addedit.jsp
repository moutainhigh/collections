<%
/** Title:			role_addedit.jsp
 *  Description:
 *		WCM5.2 角色添加修改页面
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
 *		see role_addedit.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nRoleId  = currRequestHelper.getInt("RoleId", 0);
	Role currRole = null;
	if(nRoleId>0){
		currRole = Role.findById(nRoleId);
		if(currRole == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nRoleId+"]的角色失败！");
		}
	}else{//nRoleId==0 create a new group
		currRole = Role.createNewInstance();
	}
//5.权限校验
	if(nRoleId>0){
		if(!AuthServer.hasRight(loginUser, currRole, WCMRightTypes.ROLE_SAVE)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限修改角色["+currRole.getId()+"]["+currRole.getName()+"]！");
		}
		if(!currRole.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "角色["+currRole.getName()+"]["+currRole.getId()+"]被用户［"+currRole.getLockerUserName()+"］锁定！您不能修改！");
		}
	}else{
		if(!loginUser.isAdministrator()){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限创建角色！");
		}
	}
//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("role.label.addedit", "新建修改角色")%>页面:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
<%@include file="../include/public_client_addedit.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function onOk(){
	var frmData = document.all("frmData");
	var frmAction = document.all("frmAction");
	WCMAction.doPost(frmData, frmAction);
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nRoleId%>,<%=Role.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="../auth/role_addedit_dowith.jsp" style="margin-top:0">
	<INPUT TYPE="hidden" NAME="RoleId" Value="<%=nRoleId%>">
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <TR>
	<TD height="25">
		<SCRIPT LANGUAGE="JavaScript">
			WCMDialogHead.draw("<%=LocaleServer.getString("role.label.addedit", "新建修改角色")%>");
		</SCRIPT>	    
	</TD>
    </TR>
    <TR>
	<TD align="center" valign="top" class="tanchu_content_td">
	    <TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
		<FORM NAME="frmData" ID="frmData" METHOD=POST ACTION="../auth/role_addedit_dowith.jsp" ONSUBMIT="return WCMAction.doPost(this, document.frmAction);">		
	    <TR>
		<TD class="tanchu_content">
		    <TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		    <TR>
				<TD align="left" valign="middle"><%=LocaleServer.getString("role.label.upper", "上级角色")%>：</TD> 
				<TD>（无）</TD>
		    </TR>
		    <TR>
				<TD width="70" align="left" valign="middle"><%=LocaleServer.getString("role.label.name", "角色名称")%>：</TD> 
				<TD><INPUT type="text" name="ROLENAME" roleid="<%=nRoleId%>" not_null=1 VALUE="<%=PageViewUtil.toHtmlValue(currRole.getName())%>" pattern="string" elname="<%=LocaleServer.getString("role.label.name", "角色名称")%>" max_len="50">&nbsp;<SPAN class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></SPAN></TD>
		    </TR>
		    <TR>
			    <TD align="left" valign="top"><%=LocaleServer.getString("role.label.desc", "简单描述")%>：</TD>
			    <TD><TEXTAREA name="ROLEDESC" pattern="string" elname="<%=LocaleServer.getString("role.label.desc", "简单描述")%>" max_len="200" rows="8"><%=PageViewUtil.toHtmlValue(currRole.getDesc())%></TEXTAREA></TD>
		    </TR>
		    </TABLE>
		</TD>
	    </TR>
	    <TR>
		<TD align="center">
		<SCRIPT SRC="../js/CTRSButton.js"></SCRIPT>
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing = "0";
			oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确认")%>", "onOk()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nRoleId%>,<%=Role.OBJ_TYPE%>);window.top.close();");
			
			oTRSButtons.draw();
		</script>
		</TD>
	    </TR>
		</FORM>
	    </TABLE>
	</TD>
    </TR>
    </TABLE>
</BODY>
</HTML>