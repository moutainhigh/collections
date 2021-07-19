<%--
/** Title:			securekey_addedit.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“WCM52交互密钥管理/列表”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2006-05-21 13:02
 *  Vesion:			1.0
 *  Last EditTime:	2006-05-21/2006-05-21
 *	Update Logs:
 *		wenyh@2006-05-21 created the file 
 *
 *  Parameters:
 *		see securekey_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.service.ISecurityService" %>
<%@ page import="com.trs.infra.support.security.SecureKey" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 新增密钥::::::::::::::::::::::::::::::::::..</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<script>
	function onChangeAlg(_oSelect){
		var selectedValue = _oSelect.value;
		var keySize = document.getElementById("secureKeySize");
		if("DES" == selectedValue){			
			keySize.value = 56;
			keySize.disabled = true;			
		}else{
			keySize.value = 512;
			keySize.disabled = false;
		}		
	}

	function onOK(){try{		
		var oTRSAction = new CTRSAction("securekey_addedit_dowith.jsp");
		var keyName = document.getElementById("secureKeyName").value;
		if(keyName == null || keyName.length <= 0){
			CTRSAction_alert("请输入[密钥名称]");
			return;
		}

		var keySize = document.getElementById("secureKeySize").value;		
		var keyAlg = document.getElementById("secureKeyAlg").value;
		if("DSA" == keyAlg){
			if(keySize<512 || keySize>1024 || (keySize % 64) != 0){
				CTRSAction_alert("密钥长度必须介于512/1024之间,并且必须为64的整数倍!");
				return;
			}
		}

		oTRSAction.setParameter("KeyName",keyName);
		oTRSAction.setParameter("KeySize",keySize);
		oTRSAction.setParameter("KeyAlg",keyAlg);
		oTRSAction.doAction();}catch(e){alert(e.description)}
	}
</script>
</HEAD>
<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("syssecurekey.lable.add", "添加系统密钥")%>");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD align="center" valign="top" class="tanchu_content_td">	
	<INPUT TYPE="hidden" name="SecureKeyId" value="0">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10">
	<TR>
	<TD class="tanchu_content">
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0">
		<TR><TD width="60" align="left">密钥名称</TD>
			<TD><input type="text" id="secureKeyName" name="secureKeyName" pattern="string" elname="密钥名称" not_null="1">
			&nbsp;&nbsp;<span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD></TR>
		<TR><TD colspan="2" align="left" height="10">&nbsp;</TD></TR>
		<TR><TD>密钥长度</TD><TD><input type="text" id="secureKeySize" name="secureKeySize" value="512">&nbsp;&nbsp;<span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD></TR>
		<TR><TD colspan="2" align="left" height="10">&nbsp;</TD></TR>
		<TR><TD>密钥算法</TD><TD>
			<select id="secureKeyAlg" name="" value="DSA" onChange="onChangeAlg(this)">
				<option value="DSA">DSA</option>
				<option value="DES">DES</option></select>&nbsp;&nbsp;
			<span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD></TR>
		</TABLE>
	</TD></TR></TABLE>
</TD></TR>
<TR><TD align="center" vAlign="top">
<script>
	var oTRSButtons		= new CTRSButtons();
	
	oTRSButtons.cellSpacing	= "0";
	oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

	oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确定")%>", "onOK();");
	oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "window.close();");
	
	oTRSButtons.draw();	
</script>
</TD></TR>
</TABLE>
</BODY>
</HTML>