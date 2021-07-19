<%
/** Title:			user_addedit.jsp
 *  Description:
 *		WCM5.2 用户创建修改页面
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
 *		see user_addedit.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!
	boolean bSupportCustomizeSite = "TRUE".equalsIgnoreCase(
	ConfigServer.getServer().getInitProperty("SUPPORT_CUSTOMIZE_SITE")
	);
	boolean IS_DEBUG = false;	
%>

<%
//4.初始化(获取数据)
	int nUserId   = currRequestHelper.getInt("UserId",  0);
	int nGroupId  = currRequestHelper.getInt("GroupId", 0);
	User  currUser  = null;
	Group currGroup = null;
	if(nUserId>0){
		currUser = User.findById(nUserId);
		if(currUser == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nUserId+"]的用户失败！");
		}
	}else{//create new user
		currUser = User.createNewInstance();
	}

	if(nGroupId>0){//create user in group management
		currGroup = Group.findById(nGroupId);
		if(currGroup == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nGroupId+"]的组织失败！");
		}
	}

	boolean isAddUser = (nUserId == 0);

//5.权限校验
	if(nGroupId>0){
		if(!(loginUser.isAdministrator() || AuthServer.hasRight(loginUser,currGroup))){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限在组织["+currGroup.getId()+"]["+currGroup.getName()+"]下创建用户！");
		}

		if(nUserId>0){
			if(!currUser.canEdit(loginUser)){
				throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "用户["+currUser.getName()+"]["+currUser.getId()+"]被用户［"+currUser.getLockerUserName()+"］锁定！您不能修改！");
			}
		}
	}else{
		if(!(nUserId>0)){//create new user
			if(!loginUser.isAdministrator()){
				throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限创建用户！");
			}
		}else{//edit exsited user
			if(!AuthServer.hasRight(loginUser, currUser, WCMRightTypes.USER_SAVE)){
				throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起，您没有权限修改用户！");
			}
		}
	}
//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("user.label.addedit", "用户创建修改")%>页面:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<BASE TARGET="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
<%@include file="../include/public_client_addedit.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function validUserName(_obj){
	var obj = _obj;

	if(userNameExist(<%=nUserId%>, obj.value)) {
		CTRSAction_alert("你输入的用户名已存在，请改用其它用户名！");
		return;
	}

	var pattern_url = /^([a-z]|[A-Z]|[\u4E00-\u9FA5])+(\w|\.|[\u4E00-\u9FA5])*$/g;
	if(!pattern_url.test(obj.value)){
		//CTRSAction_alert("用户名须由汉字、英文字母（不区分大小写）、数字、点或下划线组成，且必须以英文字母或汉字开头！");
		CTRSAction_alert("您输入的用户名不符合规则，请重新输入！");
		//obj.value = "";
		return false;
	}
	return true;	
}

function checkPassword(_obj){
	var obj = _obj;
	if(obj.value != document.all("PASSWORD").value){
		CTRSAction_alert("您在[<%=LocaleServer.getString("user.label.affirmpassword", "确认密码")%>]框中输入的值与[<%=LocaleServer.getString("user.label.password", "密码")%>]框中的值不一致，请重新输入！");
		obj.value = "";
		return false;
	}
	return true;
}

function userNameExist(_nId, _sName){
	var nId = _nId || 0;
	var sName = TRSString.trim(_sName);
	var oTRSAction = new CTRSAction("../auth/user_exist.jsp");
	oTRSAction.setParameter("UserId",  nId);
	oTRSAction.setParameter("UserName",  sName);
	var sResult = oTRSAction.doXMLHttpAction();
	if(sResult=="true") {
		return true;
	}
	if(sResult != "false") {
		return false;
	}
	return false;
}

function onOk(){
	var frmUser = document.all("frmUser");
	var frmAction = document.all("frmAction");
	if(!TRSValidator.validate(frmUser)) {
		return;
	}
	if(!validUserName(frmUser.USERNAME)) {
		return;
	}
<%
	if(nUserId==0){
%>
	if(!checkPassword(document.getElementById("confirmPassword"))) {
		return;
	}
<%
	}
%>
	WCMAction.doPost(frmUser, frmAction);
}
</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nUserId%>,<%=User.OBJ_TYPE%>);">
<%if(IS_DEBUG){%>
	<a href="../wcm_use/page_config_reload.jsp">重新装载当前页面的配置信息</a><BR>
<%}%>
<FORM NAME="frmAction" ID="frmAction" METHOD="POST" ACTION="../auth/user_addedit_dowith.jsp" style="margin-top:0">
	<input name="UserId" type="hidden" value="<%=nUserId%>">
	<input name="GroupId" type="hidden" value="<%=nGroupId%>">
	<input name="ObjectXML" type="hidden">
</FORM>
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <TR>
	<TD height="25">
		<SCRIPT LANGUAGE="JavaScript">
			WCMDialogHead.draw("<%=LocaleServer.getString("user.label.addedit", "用户创建修改")%>");
		</SCRIPT>	    
	</TD>
    </TR>
    <TR>
	<TD align="center" valign="top" class="tanchu_content_td">
		<FORM NAME="frmUser" ID="frmUser" METHOD="POST" ACTION="../auth/user_addedit_dowith.jsp" ONSUBMIT="return WCMAction.doPost(this, document.frmAction);">
	    <TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	    <TR>
		<TD class="tanchu_content">
		    <TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		    <TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.username", "用户登录名")%>：</TD>
				<TD><INPUT type="text" name="USERNAME" <%=isAddUser?"":"readonly style='background:silver'"%> value="<%=PageViewUtil.toHtmlValue(currUser.getName())%>" userid="<%=nUserId%>" min_len="3" max_len="20" not_null="1" pattern="string" elname="<%=LocaleServer.getString("user.label.username", "用户登录名")%>">&nbsp;&nbsp; <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		    </TR>
		    <TR style="display:<%=isAddUser?"":"none"%>">
				<TD align="left" valign="top">&nbsp;</TD> <TD>
				<P>
				<SPAN class=\"font_red\"><%=LocaleServer.getString("user.label.rule_of_username", "用户登录名填写规则")%>：</SPAN><BR><%=LocaleServer.getString("user.label.rule1_of_username", "1)由汉字、英文字母（不区分大小写）、数字、点或下划线组成，且必须以英文字母或汉字开头")%> <BR><%=LocaleServer.getString("user.label.rule2_of_username", "2)长度为3-20个字符（每个英文字母或数字算一个字符，每个汉字算两个字符）")%><BR><%=LocaleServer.getString("user.label.rule3_of_username", "3)中间不可以出现空格符、单引号、双引号减号、逗号等非法字符")%><BR><%=LocaleServer.getString("user.label.rule4_of_username", "4)system, admin是系统的保留账号")%>
				</P>
				</TD>
		    </TR>
			<TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.nickname", "用户昵称")%>：</TD>
				<TD><INPUT type="text" name="NICKNAME" value="<%=PageViewUtil.toHtmlValue(currUser.getNickName())%>" max_len="40" not_null="1" pattern="string" elname="<%=LocaleServer.getString("user.label.nickname", "用户昵称")%>">&nbsp;&nbsp; <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>&nbsp;&nbsp;</TD>
		    </TR>
			<%
			if(nUserId==0){
			%>
			<TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.password", "密码")%>：</TD>
				<TD><INPUT type="password" name="PASSWORD" min_len="8" style="width:155px" onpaste="return false;" elname="<%=LocaleServer.getString("user.label.password", "密码")%>" max_len="50" not_null="1" pattern="string" elname="<%=LocaleServer.getString("user.label.password", "密码")%>">&nbsp;&nbsp; <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>&nbsp;&nbsp;（<%=LocaleServer.getString("system.label.start_of_character_min", "最少")%> 8 <%=LocaleServer.getString("system.label.end_of_character_min", "个字符")%>）</TD>
		    </TR>
			<TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.affirmpassword", "确认密码")%>：</TD>
				<TD><INPUT id="confirmPassword" type="password" min_len="8" max_len="50" not_null="1" onpaste="return false;" style="width:155px" elname="<%=LocaleServer.getString("user.label.affirmpassword", "确认密码")%>">&nbsp;&nbsp; <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>&nbsp;&nbsp;</TD>
		    </TR>
			<%
			}
			%>
			<TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.email", "电子信箱")%>：</TD>
				<TD><INPUT type="text" name="EMAIL" value="<%=PageViewUtil.toHtmlValue(currUser.getEmail())%>" isemail="1" not_null="1" pattern="string" max_len="50" elname="<%=LocaleServer.getString("user.label.email", "电子信箱")%>">&nbsp;&nbsp; <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>&nbsp;&nbsp;</TD>
		    </TR>
			<TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.realname", "真实姓名")%>：</TD>
				<TD><INPUT type="text" name="TRUENAME" pattern="string" max_len="64" elname="<%=LocaleServer.getString("user.label.realname", "真实姓名")%>" value="<%=PageViewUtil.toHtmlValue(currUser.getTrueName())%>"></TD>
		    </TR>
			<TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.address", "详细地址")%>：</TD>
				<TD><INPUT type="text" name="ADDRESS" pattern="string" max_len="100" elname="<%=LocaleServer.getString("user.label.address", "详细地址")%>" value="<%=PageViewUtil.toHtmlValue(currUser.getAddress())%>"></TD>
		    </TR>
			<TR>
				<TD width="80" align="left" valign="middle"><%=LocaleServer.getString("user.label.telephone", "联系电话")%>：</TD>
				<TD><INPUT type="text" name="TEL" pattern="string" max_len="50" elname="<%=LocaleServer.getString("user.label.telephone", "联系电话")%>" value="<%=PageViewUtil.toHtmlValue(currUser.getTel())%>"></TD>
		    </TR>
		<%
			if(bSupportCustomizeSite){
		%>
			<TR>
				<TD width="80" align="left" valign="middle">默认打开的站点：</TD>
				<TD><INPUT type="text" name="DEFAULTSITEID" FieldName="Attribute" IsAttr=1 pattern="integer" elname="默认打开的站点：" value="<%=PageViewUtil.toHtmlValue(currUser.getAttributeValue("DEFAULTSITEID"))%>"></TD>
		    </TR>
		<%
			}
		%>

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
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nUserId%>,<%=User.OBJ_TYPE%>);window.top.close();");
			
			oTRSButtons.draw();
		</script>
		</TD>
	    </TR>
	    </TABLE>
		</FORM>
	</TD>
    </TR>
    </TABLE>
</BODY>
</HTML>