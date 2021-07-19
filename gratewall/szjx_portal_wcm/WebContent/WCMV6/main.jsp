<%
/** Title:			main3.jsp
 *  Description:
 *		WCM V6 主页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2006-12-27 10:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-27 / 2006-12-27
 *	Update Logs:
 *		CH@2006-12-27 created
 *  Parameters:
 *		see main3.xml
 *
 */ 
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../console/include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@ page import="com.trs.presentation.plugin.PluginConfig" %>
<%@ page import="com.trs.infra.util.CMyBitsValue" %>
<%@ page import="com.trs.components.wcm.MyPlugin" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@include file="../console/include/public_server.jsp"%>
<%@include file="individuation/init_individuation_config.jsp"%>
<%@include file="init_page_config.jsp"%>
<%
	boolean bWithDeptFilter = "true".equalsIgnoreCase(ConfigServer
			.getServer().getSysConfigValue("WITH_DEPT_FILTER", "false"));
%>

<HTML>
<HEAD>
<script language="javascript">
<!--
	var sTitle = "TRS WCM V6.0 内容管理新篇章";
	var isGKML = location.search.indexOf("navMode") >= 0;
	if(isGKML){
		sTitle = "政府信息公开服务系统";
	}
	document.write("<title>" + sTitle + "</title>");
//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="js/com.trs.util/Common.js"></script>
<script src="js/opensource/animator.js"></script>
</style>
<script>
	$import('com.trs.logger.Logger');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.dialog.FaultDialog');
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.util.CommonHelper');
	$import('com.trs.wcm.MessageCenter');
    $import('com.trs.wcm.BubblePanel');
    $import('com.trs.crashboard.CrashBoard');
    $import("com.trs.pontoon.Pontoon");
	$import('wcm52.calendar.Calendar');
	$import('com.trs.suggestion.suggestion');
	$import('com.trs.menu.Menu');
</script>
<script language="javascript" src="menu/menuExtend.js" type="text/javascript"></script>
<script language="javascript" src="menu/menu.js" type="text/javascript"></script>
<script language="javascript" src="tree_menu/tree_menu.js" type="text/javascript"></script>
<script language="javascript" src="main.js"></script>
<script language="javascript" src="system/is_admin.jsp" type="text/javascript"></script>

<link rel="icon" href="images/wcm6.ico" type="image/x-icon" />
<link href="css/common.css" rel="stylesheet" type="text/css" />
<link href="./communications/alertions.css" rel="stylesheet" type="text/css" />
<link href="css/attribute_footer_tab.css" rel="stylesheet" type="text/css">
<link href="main.css" rel="stylesheet" type="text/css" />
<link href="css/menu.css" rel="stylesheet" type="text/css" />
<link href="tree_menu/tree_menu.css" rel="stylesheet" type="text/css" />
</HEAD>

<BODY>
<!--
<iframe src="include/mainPageLoading.html" id='frmLoadingMask' frameborder="0" style="position:absolute;left:0;top:0;width:100%;height:100%; z-index:999;" allowTransparency="true"></iframe>
-->
<iframe src="include/processbar.html" id='processbar' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
<iframe src="include/window.html" id='floatpanel' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
<iframe src="blank.html" id='coverall' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none;'></iframe>
<iframe src="blank.html" id='document_redirect' frameborder="0" style='display:none' allowtransparency="true"></iframe>
<iframe id="alertions" src="blank.html" width="0" height="0" style="display: none"></iframe>
<iframe id="otherFieldsContainer" src="blank.html" frameborder="0" style='position:absolute;z-index:999;width:400px;height:200px;display:none;filter:alpha(opacity=95)'></iframe>
<IFRAME src="../index/noop.jsp" id="noop" name="noop" frameborder="0" border="0" framespacing="0" style="display: none"></IFRAME>
<div id="divMainBody" style="display: ">
<center>
	<div class="div_body" align="left">
	<table class="wcm6_table_layout" border=0 cellspacing=0 cellpadding=0>
	  <tr class="index_tr_menu menu_bg" style="height:30px;overflow:visible;">
		<td align="left" valign="top" colSpan="3" style="padding-left:10px;">
			<div style="height:100%;width:100%;overflow:visible;">
				<%@include file="menu/menu.jsp"%>
			</div>
		</td>
	  </tr>
	  <tr valign="top">
		<td id="td_nav" class="index_td_nav" nav_status="advanced">
			<div id="nav_advanced" class="nav_div_advanced">
				<iframe id='nav_tree' src="nav_tree/nav_tree.html" allowtransparency="true" scrolling="no" frameborder="0"></iframe>
			</div>
		</td>
		<td valign="middle" class="index_td_sep">
			<div id="arrow_toggle" class="index_arrow index_arrow_left" onclick="toggleNav();"></div>
		</td>
		<td>
			<table class="wcm6_table_layout" border=0 cellspacing=0 cellpadding=0>
				<tr>
					<td id="main_td">
					<iframe id='main' src="<%=mainFirstLink%>" allowtransparency="true" scrolling="no" frameborder="0"></iframe>
					</td>
					<td id="oper_attr_panel_td" style="width:190px;" rowSpan="2">
					<iframe id='oper_attr_panel' src="common/oper_attr_panel.html" allowtransparency="true" scrolling="no" frameborder="0"></iframe>
					</td>
					<td id="no_panel_td" style="width:20px;display:none" rowSpan="2">
					</td>
				</tr>
				<tr style="height:37px;">
					<td id="footer_td" onresize="resizeTab(this)"><iframe id="footer" src="include/footer_tab.html" frameborder="0" scrolling="no" allowtransparency="true"></iframe></td>
					<script language="javascript">
						function resizeTab(_eFooter){
							var region = $('divRatatingMask');
							if(region == null || !Element.visible(region)) {
								return;
							}

							var dimFooter = Element.getDimensions(_eFooter);
							region.style.width = dimFooter.width;

							delete _eFooter;
						}
					</script>
				</tr>
			</table>
		</td>
	  </tr>
	  <tr class="index_tr_footer">
		<td></td>   
	  </tr>
	</table>
	</div>
</center>
</div>
<script language="javascript">
<!--
//	$MessageCenter.register('alertions', $('alertions'));
	window.setTimeout(function(){
		$('alertions').src = './communications/alertions.html';
	}, 5000);
	// 为了保持session的活力，定期发一个空内容的请求
	function loadNoop(){
		var now =new Date();
		try{
			$("noop").contentWindow.location.reload("../index/noop.jsp?random="+now.getTime());	
		}catch(err){
			//TODO
			window.clearInterval(m_sNoopInt);
			if(confirm('系统已关闭！是否关闭本窗口？')) {
				window.opener = null;
				window.close();
			}
		}
	}
	var m_sNoopInt = window.setInterval(loadNoop, 60000);
//-->
</script>
<div id="divGlobalMask" class="coverall_iframe" style="display: none; position: absolute; background: #ffffff; z-index: 998;">
	<div id="divRatatingMask" style="position: absolute; border-top: 1px solid gray; border-left: 1px solid gray; border-right: 1px solid gray;">
	</div>
</div>
<div style="text-align:center;display: none; position: absolute; width:207px;height: 61px; line-height: 25px; padding-left: 10px; padding-right: 30px;z-index: 998;background:url('images/icon/page_switching_blank.gif') center center no-repeat;" id="divRotatingBar">
	<span id="spTitle"></span>
</div>
<div class="customSite" id="customSites" style="display:none;"></div>
<textarea name="customSite_template" id="customSite_template" style="display:none;">
	<div id="deselectAllCustomSite" style="display:none;">
		<li id="li_0" triggerEvent="true" title="取消选中的自定义站点" >取消选中的自定义站点</li>	
	</div>
	<for select="Individuation" blankref="divNoObjectFound"> 
	   <div>
			<li id="li_{#INDIVIDUATIONID}" triggerEvent="true" title="{#ParamValue}" objectIdsValue="{#OBJECTIDSVALUE}">{#ParamValue}</li>	
	   </div>
	</for>
</textarea>
<div id="divNoObjectFound" style="display:none;">
	<div setCustomSite="true" style="cursor:pointer;">没有进行站点个性化设置，现在设置?</div>
</div>
<SCRIPT LANGUAGE="JavaScript">
<!--
	var m_bWithDeptFilter = <%=bWithDeptFilter%>;
//-->
</SCRIPT>
<%@include file="plugins.jsp"%>

<script language="javascript" src="NavTabMgr.js" type="text/javascript"></script>
<link href="NavTab.css" rel="stylesheet" type="text/css" />
<div id="MetaViewPanelCon" style="display:none;"></div>

</BODY>
</HTML>