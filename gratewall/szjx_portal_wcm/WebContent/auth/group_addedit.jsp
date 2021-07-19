<%
/** Title:			group_addedit.jsp
 *  Description:
 *		WCM5.2 组织添加修改页面。
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
 *		see group_addedit.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nGroupId  = currRequestHelper.getInt("GroupId", 0);
	int nParentId = currRequestHelper.getInt("ParentId", 0);
	Group currGroup = null;
	Group parentGrp = null;
	if(nGroupId>0){
		currGroup = Group.findById(nGroupId);
		if(currGroup == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nGroupId+"]的组织失败！");
		}
	}else{//nGroupId==0 create a new group
		currGroup = Group.createNewInstance();
	}
	if(nParentId>0){
		parentGrp = Group.findById(nParentId);
		if(parentGrp == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取组织的父组织时,没有找到ID为["+nParentId+"]的组织！");
		}
	}//else no parent group, it should be a top group
//5.权限校验
	if(nGroupId>0){
		if(!AuthServer.hasRight(loginUser, currGroup, WCMRightTypes.GROUP_SAVE)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限修改组织["+currGroup.getId()+"]["+currGroup.getName()+"]！");
		}
		if(!currGroup.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "用户组["+currGroup.getName()+"]["+currGroup.getId()+"]被用户［"+currGroup.getLockerUserName()+"］锁定！您不能修改！");
		}
	}else{
		if(!AuthServer.hasRight(loginUser, parentGrp, WCMRightTypes.GROUP_SAVE)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限创建组织！");
		}
	}
//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("group.label.addedit", "新建/修改组织")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
<%@include file="../include/public_client_addedit.jsp"%>
<Script>
TRSValidator.addValidators("group_name",  "CWCMGroupNameValidator",  "../js/validator/CWCMGroupNameValidator.js");    
</Script>
<SCRIPT LANGUAGE="JavaScript">
function onOk(){
	var frmGroup = document.all("frmGroup");
	var frmAction = document.all("frmAction");
	WCMAction.doPost(frmGroup, frmAction);
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nGroupId%>,<%=Group.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="../auth/group_addedit_dowith.jsp" style="margin-top:0">
	<INPUT TYPE="hidden" NAME="GroupId" Value="<%=nGroupId%>">
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <TR>
	<TD height="25">
		<SCRIPT LANGUAGE="JavaScript">
			WCMDialogHead.draw("<%=LocaleServer.getString("group.label.addedit", "新建/修改组织")%>");
		</SCRIPT>
	</TD>
    </TR>
    <TR>
	<TD align="center" valign="top" class="tanchu_content_td">
	    <TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
		<FORM NAME="frmGroup" ID="frmGroup" METHOD=POST ACTION="../auth/group_addedit_dowith.jsp" ONSUBMIT="return WCMAction.doPost(this, document.frmAction);">		
	    <TR>
		<TD class="tanchu_content">
		    <TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		    <TR>
				<TD align="left" valign="middle"><%=LocaleServer.getString("group.label.upper", "上级组织")%>：</TD> 
				<TD><%=(parentGrp!=null?PageViewUtil.toHtmlValue(parentGrp.getName()):"（无）")%></TD>
		    </TR>
		    <TR>
				<TD width="70" align="left" valign="middle"><%=LocaleServer.getString("group.label.name_of_group", "组织名称")%>：</TD> 
				<TD><INPUT type="text" name="GNAME" groupid="<%=nGroupId%>" pattern="group_name" ParentId="<%=nParentId%>" not_null=1 GroupId="<%=nGroupId%>" VALUE="<%=PageViewUtil.toHtmlValue(currGroup.getName())%>" elname="<%=LocaleServer.getString("group.label.name_of_group", "组织名称")%>" max_len="50">&nbsp;<SPAN class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></SPAN></TD>
		    </TR>
		    <TR>
			    <TD align="left" valign="top"><%=LocaleServer.getString("group.label.desc_of_group", "简单描述")%>：</TD>
			    <TD><TEXTAREA name="GDESC" rows="8" pattern="string" elname="<%=LocaleServer.getString("group.label.desc_of_group", "简单描述")%>" max_len="200"><%=PageViewUtil.toHtmlValue(currGroup.getDesc())%></TEXTAREA></TD>
		    </TR>
			<INPUT TYPE="hidden" name="PARENTID" value="<%=nParentId%>">
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
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nGroupId%>,<%=Group.OBJ_TYPE%>);window.top.close();");
			
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