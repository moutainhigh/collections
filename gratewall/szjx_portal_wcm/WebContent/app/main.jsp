<%
/** Title:			main.jsp
 *  Description:
 *		WCM.1 主页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2006-12-27 10:25
 *  Vesion:			1.0
 *
 */ 
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error.jsp"%>
<%@ page import="com.trs.presentation.plugin.PluginConfig" %>
<%@ page import="com.trs.wcm.photo.IImageLibConfig"%>
<%@ page import="com.trs.wcm.photo.impl.ImageLibConfigImpl"%>
<%@ page import="com.trs.infra.util.CMyBitsValue" %>
<%@ page import="com.trs.components.wcm.MyPlugin" %>
<%@ page import="com.trs.infra.util.CMyDateTime"%>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService"%>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants"%>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig"%>
<%@include file="include/public_server.jsp"%>
<%@include file="first_access_init.jsp"%>
<%@include file="reminder_weakpassword.jsp"%>
<%
	String sLoginTimeoutLimitValue = ConfigServer.getServer().getSysConfigValue("LOGIN_TIMEOUT_LIMIT", "0");
	out.clear();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<%-- 
		/*
			wenyh@2011-07-14 force the IE9 use a IE8 document mode.
			加在此页面主要是一些iframe页的考虑,IE9渲染子页面时会使用父页面的文档模式
		*/ 
	--%>
	<title WCMAnt:param="main.jsp.trswcmcontentmanagementnewcharp">内网门户内容管理——深圳市市场和质量监督委员会</title>
	<link rel="icon" href="images/wcm.ico" type="image/x-icon" />
	<link href="css/common_main.css" rel="stylesheet" type="text/css">
<link href="js/resource/full_widgets.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--
	nLoginTimeoutLimitValue = <%=Integer.parseInt(sLoginTimeoutLimitValue)%>;
	if(nLoginTimeoutLimitValue <= 0){
		nLoginTimeoutLimitValue = 60*1000;
	} else {
		nLoginTimeoutLimitValue = (nLoginTimeoutLimitValue + 1) * 60 * 1000;
	}
//-->
</script>
	<script src="js/runtime/lightlib.js"></script>
	<script src="js/runtime/core_main.js"></script>
<script src="js/runtime/data/locale_all.js"></script>
	<script src="js/runtime/ajax_bdh.js"></script>
	<script src="js/runtime/menu_cmsmenu.js"></script>
<script src="js/runtime/data/main.js"></script>
	<%@include file="video/video_extend.jsp"%>
	<script src="main/main.js"></script>
	<%@include file="main.4.extension.jsp"%>
<link href="js/resource/widget.css" rel="stylesheet" type="text/css">
<link href="main/main.css" rel="stylesheet" type="text/css">
<link href="special/master/master_select_list.css" rel="stylesheet" type="text/css">
<link href="main/CMSMenu.css" rel="stylesheet" type="text/css">
</head>
<BODY>
<script language="javascript">
	initExtCss();
</script>
<%@include file="individuation/customize_config_include.jsp"%>
<%
	boolean bWithDeptFilter = "true".equalsIgnoreCase(ConfigServer
			.getServer().getSysConfigValue("WITH_DEPT_FILTER", "false"));
%><script language="javascript">
<!--
	var bAccess = <%=bAccess%>;
	var m_bWithDeptFilter = <%=bWithDeptFilter%>;
	var bRightTimeToReminder = <%=bRightTimeToReminder%>;
//-->
</script>
<%@include file="main/plugins.jsp"%>
<%@include file="main/menu.jsp"%>	
<script language="javascript">
<!--
	var global_IsAdmin = <%=loginUser.isAdministrator()%>;
	wcm.MenuView.render("menuBar");
	com.trs.menu.MenuInitialler.init("menuBar");
	var tabs = wcm.TabManager.defaultTabs = {};
	tabs[WCMConstants.TAB_HOST_TYPE_CHANNEL.toLowerCase()] = 
		m_CustomizeInfo.defaultChannelSheet.paramValue;
	tabs[WCMConstants.TAB_HOST_TYPE_WEBSITE.toLowerCase()] = 
		m_CustomizeInfo.defaultSiteSheet.paramValue;
	tabs[WCMConstants.TAB_HOST_TYPE_WEBSITEROOT.toLowerCase()] = 
		m_CustomizeInfo.defaultSystemSheet.paramValue;
	if(getParameter('objType')=='r'&& getParameter('tabType')){
		tabs[WCMConstants.TAB_HOST_TYPE_WEBSITEROOT.toLowerCase()] = getParameter('tabType');
	}else if(getParameter('objType')=='s'&& getParameter('tabType')){
		tabs[WCMConstants.TAB_HOST_TYPE_WEBSITE.toLowerCase()] = getParameter('tabType');
	}
//-->
</script>
<div tabindex="1" style="position:absolute;left:-1000px;top:-1000px;width:0px;height:0px;overflow:hidden;" id="fix-focus-element"></div>
<div class="layout_container" id="main_container">
	<div class="layout_north" id="main_north">
	</div>
	<div class="layout_center_container" id="main_center_container">
		<div class="layout_west" id="main_west">			
			<div class="tb_west">
				<span class="tree_navhome">&nbsp;</span>
				<div id="navtab_box" class="navtab_box"></div>
			</div>
			<div class="dv_navtree" id="frms"><!--iframe//--></div>
		</div>
		<div class="toggle_wrapper"><div class="nav_toggle" id="nav_toggle"></div></div>
		<div class="layout_center" id="main_center"><!--iframe//--></div>
	</div>
	<div class="layout_south" id="main_south"></div>
</div>
<script language="javascript">
$('main_center').innerHTML = [
	"<iframe id='main'",
		" src='main/portal.jsp?url=", encodeURIComponent(window.customizeLink), "'",
		" scrolling='no' frameborder='0' allowtransparency='true'",
	"></iframe>"			
].join("");
wcm.navTabs.init({
	activeIndex : getParameter("navType") || 2,
	fn : true
});
</script>
<script src="js/runtime/components_dd.js"></script>
<script src="../console/js/CWCMDialogHead.js"></script>
<script src="main.jsp.compress.js"></script>
</BODY>
<script language="javascript">
<!--
	jQuery.noConflict();//解决$命名冲突
//-->
</script>


<script>
  document.getElementById("main").src="/wcm/app/document/document_list_redirect.jsp?SiteId=4&SiteType=0&RightValue=110001110100001001111101111111111010011111011111011101100111010&"
</script>
</HTML>