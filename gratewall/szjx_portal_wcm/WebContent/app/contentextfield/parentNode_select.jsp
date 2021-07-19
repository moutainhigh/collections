
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.components.wcm.resource.Sources" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	//获取文档状态集合
//	filter = new WCMFilter("", "", Status.DB_ID_NAME, Status.DB_ID_NAME);
//	Statuses statuses = Statuses.openWCMObjs(loginUser, filter);
	out.clear();
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="parentNode_select.jsp.title">继承父对象扩展字段</title>
<!--FloatPanel Inner Start-->
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/photo.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!--wcm-dialog start-->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/data/locale/contentextfield.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<!--CrashBoard-->
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<!--Calendar-->
<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar_Obj.js" type="text/javascript"></script>
<script language="javascript" src="../../app/js/source/wcmlib/calendar/calendar_lang/cn.js" type="text/javascript" WCMAnt:locale="../../app/js/source/wcmlib/calendar/calendar_lang/$locale$.js"></script>
<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar.js" type="text/javascript"></script>
<link href="../../app/js/source/wcmlib/calendar/calendar_style/calendar-blue.css" rel="stylesheet" type="text/css" />
<!--ProcessBar-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--page owner-->
<script src="parentNode_select.js"></script>
<style>
.attr_column_title{
	background-image: url(../images/document/attr_column_head_bg.png);
	line-height:26px;
	font-weight: bold;
	color: #000000;
	font-size: 14px;
	height: 26px;
	cursor:pointer;
}
.layout_west{
	margin-left:10px;
}
.attr_name{
	margin-top :10px;
	height:26px;						
}
</style>
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'Ok',
			name : wcm.LANG.PHOTO_CONFIRM_1 || '确定'
		}],
		size : [600, 355]
	};
	Event.observe(window, 'load', function(){	
	});
</script>
<style type="text/css">
.ext-strict .layout_border_center{border-width:1px;position:absolute;left:0;right:0;width:auto;}
.ext-ie6 .layout_border_center{border-width:1px;}
.ext-strict .layout_center_container{top:5px;bottom:5px;}
.ext-strict .layout_center{left:205px;right:5px;bottom:2px;top:0;}
.ext-ie6 .layout_container{border:0px;padding-top:5px;padding-bottom:5px;}
.ext-ie6 .layout_center_container{border:0px;padding-left:205px;padding-right:5px;padding-bottom:2px;}
.layout_west{width:192px;}
.treeNav{width:192px;height:100%;}
</style>
<script language="javascript">
<!--
	if(Ext.isIE){
		document.write('<style type="text/css">');
		document.write('.ext-strict #NavTree iframe{height:'+(window.m_fpCfg.size[1]-59)+'px!important;}');
		document.write('.ext-strict .div_classic{height:'+(window.m_fpCfg.size[1]-59)+'px!important;}');
		document.write('.ext-strict #photo_list{height:'+(window.m_fpCfg.size[1]-20)+'px!important;}');
		document.write('</style>');
	}else{
		document.write('<style type="text/css">');
		document.write('#NavTree iframe{height:'+(window.m_fpCfg.size[1]-39)+'px!important;}');
		document.write('#photo_list{height:'+(window.m_fpCfg.size[1]-20)+'px!important;}');
		document.write('</style>');
	}
//-->
</script>
</head>
<body>
<script language="javascript">
	initExtCss();
</script>
<form id="form_imageInfo" style="display:none">
	<input type="hidden" name="MainKindId" id="MainKindId" value="0"></input>
	<input type="hidden" name="PicIds" id="PicIds" value=""></input>
</form>
<div class="layout_center_container">
	<div class="layout_west">
		<table border=0 cellspacing=0 cellpadding=0 style="width:192px;height:100%;position:absolute;">
		<tbody>
			<tr height="24">
				<td class="attr_column_title">
		<div WCMAnt:param="photo_importsyspics.jsp.navTree">导航树</div>
				</td>
			</tr>
			<tr id="NavTree" valign="top">
				<td style="height:92%"><div style="height:100%;border:1px solid black;">
				<iframe src="parentTree.jsp?ChannelId=<%=nChannelId%>&SiteId=<%=nSiteId%>" id="treeNav" allowtransparency="true" scrolling="no" frameborder="0"></iframe></div></td>
			</tr>
			</div>
				</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="layout_center layout_border_center" id="centerDiv">
		<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;position:absolute;">
			<tr style="height:100%;">
				<td><iframe name="photo_list" id='photo_list' src="selectdefault.html" scrolling="no" frameborder="0" style="width:100%;height:100%;overflow:hidden;"></iframe>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>