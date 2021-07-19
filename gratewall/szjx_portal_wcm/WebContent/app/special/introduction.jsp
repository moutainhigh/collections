<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.config.ConfigServer"%>
<!------- WCM IMPORTS END ------------>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="error_for_dialog.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%	
	//2012-03-15 by lky 去除专题视频演示直接进入创建专题页面
	/*boolean bEnterDirectly = ConfigServer.getServer().getSysConfigValue(
				"SPECIAL_ENTERDIRECTLY", "false").trim().equals("true");
	if(bEnterDirectly){*/
	if(true){
		currRequestHelper.getResponse().sendRedirect("special_1.jsp?ObjectId=0&isNew=1");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../app/document/batchupload/swfobject.js" ></script>
<title WCMAnt:param="introduction.jsp.title">TRS WCM 专题功能介绍页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;
	margin-bottom: 5px;
	font-size:12px;
}
.tableContainer{
	width:678px;
	height:100%;
}
.ext-gecko .tableContainer{
	width:690px;
	height:100%;
}
#flashcontent{
	text-align:center;
	font-weight:bold;
}
-->
</style>
</head>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function gotoPlugin(){
		var checkbox = document.getElementById("check");
		var element = document.getElementById("recommand");
		if(checkbox && checkbox.checked){
			element.value = "true";
		}
		var frm = document.getElementById("formData");
		frm.submit();
		window.location.href = "special_1.jsp?ObjectId=0&isNew=1";
	}
	function maxopen(_sUrl,_bResizable){
		var nLeft	= 0;
		var nTop	= 0;
		var nWidth	= window.screen.width - 12, nHeight = window.screen.height - 60;
		var oWin = window.open(_sUrl,"专题制作介绍", "resizable=" 
			+ "yes" + ",top=" + nTop + ",left=" 
			+ nLeft + ",menubar =no,toolbar =no,width=" 
			+ nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no", "yes");
		if(oWin)oWin.focus();
		return oWin;
	}
//-->
</SCRIPT>
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="js/adapter4Top.js"></script>
<body>
<form name="formData" id="formData" action="introduction_dowith.jsp">
<table class="tableContainer" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td>
		<table class="tableContainer" border="0" cellpadding="0" cellspacing="1"
			bgcolor="A6A6A6">
			<tr>
				<td height="26" background="../images/tdbg.jpg">
				<table width="100%" height="26" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="24"><img src="../../console/images/bite-blue-open.gif" width="24"
							height="24"></td>
						<td WCMAnt:param="introduction.jsp.intr_of_special">专题介绍</td>
						<td style="font-size:12px;width:160px;"><label for="check" style="cursor:pointer;padding-right:20px;"><input type="checkbox" name="check" id="check"/><span WCMAnt:param="introduction.jsp.donot_entry_intr_page">以后不再进入介绍页面</span></label></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td valign="top">
				<table width="100%" height="100%" border="0" cellpadding="2">
					<tr>
						<td bgcolor="#Ffffff">
						<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
							<tr>
								<td>
								<span WCMAnt:param="introduction.jsp.long_content">&nbsp;&nbsp;&nbsp;&nbsp;专题制作系统做为TRS内容协作平台(WCM)的一个单独模块，允许用户在国内外发生重大事件时，快速建立一个属于自己的专题，并根据事件的特征设计专题页面的显示布局，以及在布局中添加相应的资源块，在资源块中添加相应的文章和图片等，从而发布到公网上。</span><BR>
								<BR>
								&nbsp;&nbsp;&nbsp;<font color="green"><span onclick="maxopen('introduce.swf')" style="cursor:pointer;" WCMAnt:param="introduction.jsp.full_screen">全屏观看</span>&nbsp;&nbsp;||&nbsp;&nbsp;<span>点击进入&nbsp;&nbsp;<a
									href="###"  onclick="gotoPlugin();return false;" WCMAnt:param="introduction.jsp.new_special_page">专题新建页面</a></span></font>
									<BR><BR>
								</td>
							</tr>
							<tr>
								<td height="100%">
								<script type="text/javascript"> 
								var params = {
									quality : "high",
									scale : "exactfit",
									wmode : "transparent",
									allowScriptAccess : "sameDomain"
								};								swfobject.embedSWF("introduce.swf", "flashcontent", "100%", "100%", "9.0.124", "",params);	
								</script> 	
								<div id="flashcontent" WCMAnt:param="introduction.jsp.broswer_or_flash_error">浏览器或Flash环境异常, 导致该内容无法显示!</div> 
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
<input type="hidden" name="recommand" id="recommand" value="false"/>
</form>
</body>
</html>