<%
/** Title:			user_select_bottom.jsp
 *  Description:
 *		标准WCM5.2 用户选择首页
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-26 20:17
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-26/2004-12-26
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see user_select_bottom.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>


<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 用户选择首页::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
</HEAD>

<BODY>
<!--~== TABLE1 ==~-->
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
<!--~--- ROW1 ---~-->
<TR>
  <TD style="BORDER-top: #A6A6A6 1px solid">
  <!--~== TABLE2 ==~-->
  <TABLE width="110" border="0" align="center" cellpadding="0" cellspacing="8">
  <!--~--- ROW2 ---~-->
  <TR>
	  <TD valign="top" height=26 align=center>
		<script src="../js/CTRSButton.js"></script>
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing	= "0";
			oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确定")%>", "window.parent.onOK();");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "window.parent.close();");
			
			oTRSButtons.draw();
		</script>
	  </TD>
  </TR>
  <!--~- END ROW2 -~-->
  </TABLE>
  <!--~ END TABLE2 ~-->
  </TD>
</TR>
<!--~- END ROW1 -~-->
</TABLE>
<!--~ END TABLE1 ~-->
</BODY>
</HTML>