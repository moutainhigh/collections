<%--
/** Title:			config_list.jsp
 *  Description:
 *		处理系统配置的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-05 10:09:22
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-05 / 2005-04-05
 *	Update Logs:
 *		NZ@2005-04-05 产生此文件
 *
 *  Parameters:
 *		see config_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.helper.HttpChecker"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%
	String sTarget = currRequestHelper.getString("TargetUrl");
	//System.out.println("target->" + sTarget);
	boolean bAbnormal = false;
	try{
		int respCode = HttpChecker.getResponseCodeForHttp(sTarget);
		bAbnormal = (respCode != 200);
	}catch(Throwable ex){
		bAbnormal = true;
	}

	
	//7.结束
	out.clear();

%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2
处理添加修改系统配置:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
<script>
function closeWindow(){
	window.returnValue=<%=!bAbnormal%>;
	window.close();
}
</script>
</HEAD>

<BODY>
	<TABLE bgcolor="#f6f6f6" width="100%" height="100%" border="0" cellspacing="0" cellpadding="10" align="center" valign="center">
	<TR>
	<TD>
		<TABLE bgcolor="#ffffff" width="100%" height="100%" border="0" cellspacing="2" cellpadding="0" align="center" valign="center">
		<!-- 请注意在此修改相应字段名，addedit页面的字段名必须与该对象数据表字段名相对应 -->
		
		<TR>
		<TD align="center">
		<%
			if (bAbnormal){
				out.println("<span style=\"color:darkred\">无法联接到指定http地址</span>, 请检查链接是否有效！");
				
			}else{
				out.println("<span style=\"color:green\">可以联接到该链接地址</span>, 但并能不保证该链接一定是有效的.");
			}
		%></TD>
		</TR>
		<TR>
		<TD align="center">
			<script src="../js/CTRSButton.js"></script>
			<script>
				//定义一个TYPE_ROMANTIC_BUTTON按钮
				var oTRSButtons		= new CTRSButtons();
				
				oTRSButtons.cellSpacing	= "0";
				oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;
	
				oTRSButtons.addTRSButton("关 闭", "closeWindow();");
				
				oTRSButtons.draw();	
			</script>
		</TD>
		</TR>		
		</TABLE>
	</TD>
	</TR>
	</TABLE>
</BODY>
</HTML>