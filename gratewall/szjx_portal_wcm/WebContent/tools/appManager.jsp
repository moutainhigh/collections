<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM5.2 应用管理工具,用于停止/启动应用.
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2005/12/16
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		
 *
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.ServerControl" %>
<%@ page import="com.trs.DreamFactory" %>
<!------- WCM IMPORTS END ------------>

<%@include file="../include/public_server.jsp"%>
<%!
	private boolean restartSystem(){
		ServerControl control = (ServerControl)DreamFactory.createObjectById("ServerControl");
		return control.restart();		
	}
%>

<%
	
	if(!loginUser.isAdministrator()){
		throw new WCMException("只有系统管理员可以访问该页面!");
	}	
	
	String username = currRequestHelper.getString("UserName");
	String password = currRequestHelper.getString("PassWord");
	boolean bRestarted = false;
	if(!CMyString.isEmpty(username)){
		if(!loginUser.getName().equals(username) || !loginUser.passwordIs(password)){
			throw new WCMException("您输入的管理员信息与实际不符!");
		}
	
		if("true".equals(currRequestHelper.getString("DoOper"))){
			bRestarted = restartSystem();
		}
	}
	
	String sResult = "failed";
	if(bRestarted){
		sResult = "ok";
	}
	
%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS内容管理————应用重启工具</title>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../js/TRSBase.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSValidator.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSValidator_res_default.js"></SCRIPT>
<script type="text/javascript" src="../js/CTRSHashtable.js"></script>
<script type="text/javascript" src="../js/CTRSRequestParam.js"></script>
<script type="text/javascript" src="../js/CTRSAction.js"></script>
<script type="text/javascript" src="../js/CTRSButton.js"></script>
<script language="javascript">
<!--
    function doOper(){
	var oForm = document.getElementById("adminInfo")
	if(!TRSValidator.validate(oForm)) {
		return false;
	}
	
	var oAction = new CTRSAction("./appManager.jsp");
	oAction.setParameter("UserName",oForm.UserName.value);
	oAction.setParameter("PassWord",oForm.PassWord.value);
	oAction.setParameter("DoOper","true");
	
	var result = oAction.doXMLHttpAction();
	
	if("faled" == result || result==void(0)){
		CTRSAction_alert("重启TRSWCM5.2失败!");
	}else{
		CTRSAction_alert("重启TRSWCM5.2成功!");
	}
    }
--->
</script>
</head>
<body>
	<table align="center">
	<tr><td>当前时间:<%=CMyDateTime.now()%></td></tr>
	<tr><td><hr size="1" align="left" ></td></tr>
	<tr><td><b>操作</b>:</td></tr>
	<tr><td><form id="adminInfo" method="post">
		<table><tr><td align="right">管理员名称</td>
			   <td>&nbsp;&nbsp;<input type="text" name="UserName" size="15" pattern="string"  elname="用户名" not_null=1 style="width:100px" class="inputtext">
			   </td></tr>
		       <tr><td align="right">管理员密码</td>
		           <td>&nbsp;&nbsp;<input type="password" name="PassWord" size="15" max_len="50" pattern="string"  elname="密码" not_null=1 min_len='8' style="width:100px" class="inputtext"></td></tr></table>
		</form>
	</td></tr>
	<tr><td align="right">
	<script>
		var oTRSButtons	= new CTRSButtons();
		oTRSButtons.cellSpacing	= "0";
		oTRSButtons.nType = TYPE_SMALLROMANTIC_BUTTON;
		oTRSButtons.addTRSButton("确  定", "doOper()");
		oTRSButtons.draw();	
	</script>	
	</td></tr></table>			
</body>
</html>