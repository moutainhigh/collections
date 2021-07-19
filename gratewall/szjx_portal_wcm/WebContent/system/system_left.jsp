<%
/** Title:			left.jsp
 *  Description:
 *		标准WCM5.2 通用导航页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-11 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-11/2004-12-11
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see left.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->

<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）

//5.权限校验

boolean bShow = AuthServer.hasAttributeRight(loginUser);
//6.业务代码

//7.结束
	out.clear();
%>


<HTML>
<HEAD>
<TITLE>TRS WCM 5.2首页导航::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<script src="../js/CWCMNavigation.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSHashtable.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSRequestParam.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSAction.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSArray.js"></script>

</HEAD>

<BODY margin="5px">
<!--~== 导航 TABLE BEGIN  ==~-->
<TABLE width="100%" height=100% border="0" cellpadding="0" cellspacing="1" class="navigation_table"　id="idNavigationTable">
<% if (bShow){
	if(loginUser.isAdministrator()){%>

	<script>WCMNavigation.drawItem("计划调度配置导航", "../system/worktype_nav.jsp");</script>
	<!---[导航]导航内容 BEGIN-->
	<TR style="display:none">
	  <TD>	  
		<iframe src="about:blank" name="NavFrames" frameborder="NO" border="0" framespacing="0" width="100%" height="100%" Scrolling="NO"></iframe>
	  </TD>
	</TR>
	<!---[导航]导航内容 END-->
	<script>WCMNavigation.drawItem("系统配置导航", "../system/systemconfig_nav.jsp");</script>
	<!---[导航]导航内容 BEGIN-->
	<TR style="display:none">
	  <TD>	  
		<iframe src="about:blank" name="NavFrames" frameborder="NO" border="0" framespacing="0" width="100%" height="100%" Scrolling="NO"></iframe>
	  </TD>
	</TR>
	<!---[导航]导航内容 END-->

	<script>WCMNavigation.drawItem("属性配置导航", "../system/otherconfig_nav.jsp");</script>
	<!---[导航]导航内容 BEGIN-->
	<TR style="display:none">
	  <TD>
		<iframe src="about:blank" name="NavFrames" frameborder="NO" border="0" framespacing="0" width="100%" height="100%" Scrolling="NO"></iframe>
	  </TD>
	</TR>
	<!---[导航]导航内容 END-->
	<script>
	WCMNavigation.openItem(2);
	</script>
<%}else{%>
	<script>WCMNavigation.drawItem("属性配置导航", "../system/otherconfig_nav.jsp");</script>
	<!---[导航]导航内容 BEGIN-->
	<TR style="display:none">
	  <TD>
		<iframe src="about:blank" name="NavFrames" frameborder="NO" border="0" framespacing="0" width="100%" height="100%" Scrolling="NO"></iframe>
	  </TD>
	</TR>
	<!---[导航]导航内容 END-->
	<script>
	WCMNavigation.openItem(1);
	</script>
<%		}
}else{%>
	<tr><td bgcolor="white"><IMG src="../images/warning.gif" align="absmiddle" valign="button"><br>
	<font  color="blue"><b>对不起，您没有权限查看此导航!</b></font>
	</tr></td>
<%}%>
</TABLE>
<!--~ 导航 TABLE END  ~-->


</BODY>
</HTML>