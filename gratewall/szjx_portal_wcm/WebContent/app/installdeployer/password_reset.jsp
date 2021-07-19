<%
/** Title:			password_reset.jsp
 *  Description:
 *		重置用户密码
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限修改admin用户的密码！");
	}
	//还没有处理完成
	out.clear();%>
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>设置admin用户的密码</title>
	<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
	<LINK href="../style/style.css" rel="stylesheet" type="text/css">
	<LINK href="../../console/style/style.css" rel="stylesheet" type="text/css">
	<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
	<script src="../../app/js/easyversion/lightbase.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/easyversion/extrender.js"></script>
	<script src="../../app/js/easyversion/ajax.js"></script>
	<script src="../../app/js/easyversion/elementmore.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/source/wcmlib/Observable.js"></script>
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<script src="../../app/js/source/wcmlib/Component.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
	<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
	<SCRIPT SRC="../../console/auth/password_strength.js"></SCRIPT>
	<SCRIPT>
		var m_cb = null;
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					cmd : function(){
						return onOk();
					}
				}
			]
		};
		function init(param){
			m_cb = wcm.CrashBoarder.get(window);
			var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
			cbSelf.setSize("400px","200px");
			cbSelf.center();
		}

		function onOk(){
			// 如果是用去设置新密码，校验密码的有效性
			var newPassword ="";
			// 校验密码不通过，则返回
			if(!checkPassword())
				return false;

			newPassword = document.getElementById("PASSWORD").value;

			// 传递参数给上层页面
			var param = {
				NEWPASSWORD : newPassword,
				PASSWORDLEV : passwordLev
			}
			if(m_cb){
				m_cb.notify(param);
				m_cb.hide();
			}
			return false;
		}

		function checkPassword(){
			var checkPW = document.getElementById("CHECKPASSWORD");
			var PW = document.getElementById("PASSWORD");
			if(PW.value.length<8){
				alert("您在[密码]框中输入的密码小于最小长度8，请重新输入！");
				return false;
			}
			if(checkPW.value != PW.value){
				alert("您在[确认密码]框中输入的值与[密码]框中的值不一致，请重新输入！");
				checkPW.value = "";
				checkPW.focus();
				return false;
			}
			return true;
		}

	</SCRIPT>
	<style type="text/css">
		#inputNewPW input{
			width:145px;
		}
		.title{
			font-weight: bold;
			padding-left: 10px;
		}
	</style>
</HEAD>
<BODY>
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
    <TR>
		<TD height="25" class="title">
			修改用户[admin]的默认密码
		</TD>
    </TR>
	<TR>
	<TD align="center" valign="top" class="tanchu_content_td">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<DIV id="inputNewPW">
			输入新密码：<input type="password"  name="newPassword" id="PASSWORD" onkeyup="runPassword(this.value)"/>&nbsp;&nbsp;<span class="font_red">*</span>（最少8个字符）<br>
			<DIV id="PWinfo" style="padding-left:22px;">
							<TABLE style="width:155px"><TR><TD ><a href="javascript:var popup=window.open('../../console/auth/PasswordHelp.html', 'PasswordHelp', 'width=600, height=250, location=no, menubar=no, status=no, toolbar=no, scrollbars=yes, resizable=yes');"title="了解什么是密码强度？如何设置安全密码？">密码强度</a></TD>
								<TD><div class="pstrength-info" id="_text"></div></TD>
							  </TR>   </TABLE>
								<DIV style="width:145px;background-color:#dddddd" ><div class="pstrength-bar" id="_bar" style="font-size: 1px; height: 5px; width: 0px;"></div></DIV>
			</DIV>
			<BR>
			<%=LocaleServer.getString("user.label.checknewpassword", "确认新密码")%>：<input type="password"  name="newPassword" id="CHECKPASSWORD"/>&nbsp;&nbsp;<span class="font_red">*</span>
		</DIV>
		</TD>
		</TR>
		</TABLE>
	</TD>
</TR>
</TABLE>
</BODY>
</HTML>