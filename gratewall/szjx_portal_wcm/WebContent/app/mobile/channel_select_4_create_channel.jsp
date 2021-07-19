<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.service.impl.ChannelService" %>

<%@include file="../include/public_processor.jsp"%>
<%!boolean IS_DEBUG = false;%>

<html>
<head>

<title id="tlDoc" WCMAnt:param="channel_select_4_create_channel.jsp.title">创建移动门户栏目</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/core/core.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/channel.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../js/easyversion/calendar.js"></script>
<link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->

<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'getSeletedIds',
		name : wcm.LANG.CHANNEL_TRUE||'确定'
	}],
	size : [320, 390]
};
</script>
<script>
	var PageContext = {};
	Object.extend(PageContext, {
		srcId: getParameter('srcId'),
		siteType : getParameter('siteType') || 0
	});
	function getSeletedIds(){
		//需要返回type信息：栏目还是站点
		var aSelIds = $('frm' + currActiveItem).contentWindow.getCheckValues();
		if(aSelIds == null || aSelIds.length == 0){
			Ext.Msg.$alert('请选择栏目要创建的目标站点/栏目!');
			return false;		
		}
		var postData = {
			srcId: PageContext.srcId,
			dstId: aSelIds.join(","),
			dstType : currActiveItem == 'ChnlTreeNav' ? 101 : 103,
			containChildren : $('containChildren').checked,
			DocSDate : $('DOCSDATE').value
		};
		notifyFPCallback(postData);
	}

	var currActiveItem = '';

	var mapping = {
		'ChnlTreeNav' : [
			'frmChnlTreeNav', 
			"nav_tree/nav_tree_select.jsp?IsRadio=1&MultiSites=1&ExcludeSelf=1&ExcludeChildren=1&ExcludeTop=1&ShowOneType=1&ExcludeLink=1&SelectMobile=1&RightIndex=11&CurrChannelId="	+PageContext.srcId
		],
 		'SiteTreeNav' : [
			'frmSiteTreeNav', 
			"nav_tree/nav_tree_select_site.jsp?IsRadio=1&MultiSites=1&ExcludeSelf=1&ExcludeChildren=1&ExcludeTop=1&ShowOneType=1&ExcludeLink=1&SelectMobile=1&RightIndex=11&CurrChannelId=" + PageContext.srcId + "&SiteTypes=" + PageContext.siteType
		]
	};

	function setActiveTab(item){
		if(currActiveItem == item) return;
		Element.hide('div' + currActiveItem);
		initTab(item);
		Element.show('div' + item);
		currActiveItem = item;
	}

	function initTab(item){
		var dom = $('div' + item);
		if(dom.getAttribute("inited")) return;
		dom.setAttribute("inited", true);
		var config = mapping[item];
		if(!config) return;
		$(config[0]).src = WCMConstants.WCM6_PATH + config[1];
	}
</SCRIPT>
<style type="text/css">
	.selection_row{
		line-height:25px;
		height:25px;
		text-align:center;
		font-size:12px;
		color:#010101;
	}
	body{
		margin: 0;
		padding: 0;
	}
</style>
</HEAD>

<BODY>
<div style="font-size: 12px; width: 100%; height: 25px; line-height: 25px; font-weight: normal; font-size: 14px;">
	<div style="width: 100%; height: 100%;padding-left: 5px; border-bottom: 1px solid silver;" WCMAnt:param="channel_select_4_create_channel.jsp.selsiteorchnl">
		选择站点或栏目
	</div>
</div>
<div id="divOptions" style="border-bottom:1px solid #efefef; padding-top: 3px; padding-bottom: 3px;">
	<span class="selection_row"><input id="rdSiteTreeNav" name="tab-type" type="radio" value=""  onclick="setActiveTab('SiteTreeNav')">&nbsp;<span WCMAnt:param="channel_select_4_create_channel.jsp.crtosite">创建到站点</span></span>
	<span class="selection_row"><input id="rdChnlTreeNav" name="tab-type" type="radio" value="" checked onclick="setActiveTab('ChnlTreeNav')">&nbsp;<span WCMAnt:param="channel_select_4_create_channel.jsp.crtochnl">创建到栏目</span></span>
</div>
<div id="box" style="height:260px;width:270px;background:white;">
	<div id="divChnlTreeNav" style="position:relative;display:none;width:100%;height:100%; overflow: hidden">
		<iframe id="frmChnlTreeNav" src="about:blank" style="position:absolute;width:100%;height:100%;" frameborder="0" scrolling="no" allowtransparency="true"></iframe>
	</div>
	<div id="divSiteTreeNav" style="position:relative;display:none;width:100%;height:100%; overflow: hidden">
		<iframe id="frmSiteTreeNav" src="about:blank" style="position:absolute;width:100%;height:100%;" frameborder="0" scrolling="no" allowtransparency="true"></iframe>
	</div>
</div>
<div>
	<input type="checkbox" name="containChildren" id="containChildren" value="1" />
	<label for="containChildren" style="font-size:12px;">同步创建原始栏目的子栏目</label>
</div>
<div style="font-size:12px;">
	同步<input type="text" name="DOCSDATE" id="DOCSDATE" elname="文档创建时间" style="width:100px;"><button id="DOCSDATEBTN" type="button"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0 class="img"></button>
	之后创建的文档
	<div style="color:red;margin-top:3px;">
		注意：不指定时间则同步所有文档
	</div>
</div>
<script language="javascript">
<!--
	setActiveTab('ChnlTreeNav');
	
	//同步文档时间初始化
	wcm.TRSCalendar.get({
		input : 'DOCSDATE',
		handler : 'DOCSDATEBTN'
	});
//-->
</script>
</BODY>
</HTML>