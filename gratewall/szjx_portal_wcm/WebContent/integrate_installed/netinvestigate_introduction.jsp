<%
/** Title:			integrate_introduction.jsp
			 *  Description:
			 *		WCM5.2 扩展功能介绍页面
			 *  Copyright: 		www.trs.com.cn
			 *  Company: 		TRS Info. Ltd.
			 *  Author:			CH
			 *  Created:		2005-01-03 21:05
			 *  Vesion:			1.0
			 *  Last EditTime:	2005-01-03/2005-01-03
			 *	Update Logs:
			 *		CH@2005-01-03 Created integrate_introduction.jsp
			 *
			 */

			%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService"%>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants"%>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig"%>
<!------- WCM IMPORTS END ------------>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
			.createObjectById("IComponentEntryConfigService");

	EntryConfig currConfig = configSrv.getTypedEntryConfig(ComponentEntryConfigConstants.TYPE_POLL);
	String sUrl = currConfig.getLinkPath();
	if (!sUrl.endsWith("/"))
		sUrl += "/";
	sUrl += "poll_list.jsp";
	
	String sUserLoginEncodInfo = currLoginHelper.createPluginEncodeUserInfo();
	sUrl += "?" + sUserLoginEncodInfo;
	
	if (!currConfig.isEnable()) {
		String sRedirectUrl = "../integrate/netinvestigate_introduction.jsp";
		currRequestHelper.getResponse().sendRedirect(sRedirectUrl);
	}
	else if (currConfig.isEnterDirectly()){
		String sNeedRedirect = request.getParameter("redirectable");													 
		//System.out.println("-->" + sNeedRedirect);
		if (sNeedRedirect == null || sNeedRedirect.trim().compareToIgnoreCase("false") != 0){
			currRequestHelper.getResponse().sendRedirect(sUrl);
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2
扩展功能介绍页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<link href="../style/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
<!-- WCM按钮的style-->
<style>
.button_table_2{
	height: 16px;
	font-size:9px;
	cursor:hand;
}
.button_table_diable_2{
	height: 16px;
	font-size:9px;
	color:#999999;
}
</style>
<script src="../js/TRSButton.js"></script>
<!-- 结束：WCM按钮的style-->

<script language="javascript" type="text/javascript">
	var win = null;
	function newWindow(mypage,myname,w,h,scroll){
		LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
		settings =
			'height='+h+',width='+w+',top='+TopPosition+',left='+LeftPosition+',scrollbars='+scroll+',resizable'
		win = window.open(mypage,myname,settings)
		if(win.window.focus){
			win.window.focus();
		} 
	}
	
</script>
<%@include file="../include/public_client_list.jsp"%>
<script>
function close(){
	var oTRSAction = new CTRSAction("entry_switch_dowith.jsp");
	oTRSAction.setParameter("EntryConfigId", <%=currConfig.getId()%>);
	var bResult = oTRSAction.doDialogAction(500, 200);
	if(bResult){
		CTRSAction_refreshMe();
	}
}

function setOptions(){
	var oTRSAction = new CTRSAction("entryconfig_addedit.jsp");
	oTRSAction.setParameter("EntryConfigId", <%=currConfig.getId()%>);	
	var bResult = oTRSAction.doDialogAction(520, 280);
	if(bResult){
		document.location.href = "../integrate_installed/netinvestigate_introduction.jsp?redirectable=false";
	}
}

var m_sPluginURL = "<%=sUrl%>";
function gotoPlugin(){
	window.location.href = m_sPluginURL;
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="A6A6A6">
			<tr>
				<td height="26" background="../images/tdbg.jpg">
				<table width="100%" height="26" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="24"><img src="../images/bite-blue-open.gif" width="24"
							height="24"></td>
						<td>问卷调查介绍</td>
						<td width="15">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td valign="top">
				<table width="100%" border="0" cellpadding="2" cellspacing="0"
					bgcolor="#FFFFFF">
					<tr>
						<td valign="top">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr>
								<td>
								<table width="100%" border="0" cellpadding="2">
									<tr>
										<td bgcolor="#Ffffff">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td height="20">
												&nbsp;&nbsp;&nbsp;&nbsp;问卷调查子系统做为TRS内容协作平台(WCM)的一个外挂模块，可以方便地供内部用户在WCM系统内进行问卷调查的创建、发布及管理。利用该选件，您既可以发布独立的问卷，也可以发布嵌入到页面中的调查。<BR>
												<BR>
												&nbsp;&nbsp;&nbsp;&nbsp;<font color="green">该选件已经启用。</font><span>点击进入<a
													href="###"  onclick="gotoPlugin();return false;">问卷调查列表</a></span><BR>
												<P
													style="MARGIN-LEFT: 42.25pt; TEXT-INDENT: -21pt; LINE-HEIGHT: 20pt; tab-stops: list 42.25pt">&nbsp;</P>
												</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>



</table>
</body>
</html>