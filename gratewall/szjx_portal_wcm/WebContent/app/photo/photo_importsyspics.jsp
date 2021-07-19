
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
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
	String channelName = null;
	Channel currChannel = null;
	Watermarks currWatermarks = null;
	Watermark newWatermark = null;
	if(nChannelId == 0){
		channelName = LocaleServer.getString("photo_importsyspics.label.choose", "选择");
	}else{
		currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_importsyspics.jsp.channelId_not.found", "没有找到指定ID为[{0}]的栏目!"), new int[]{nChannelId}));
			// "没有找到ID为"+ nChannelId + "的栏目");
		}
		channelName = currChannel.getDesc();
		nSiteId = currChannel.getSiteId();
	}
	//获取站点水印集合
	WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
	filter.addSearchValues(0, nSiteId);
	try{
		currWatermarks = Watermarks.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("photo_importsyspics.jsp.label.fail2get_watermark_coll", "获取站点水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
	//获取文档来源集合
	filter = new WCMFilter("", "", Source.DB_ID_NAME,
                Source.DB_ID_NAME);
	Sources sources = Sources.openWCMObjs(loginUser, filter);
	//获取文档状态集合
	filter = new WCMFilter("", "", Status.DB_ID_NAME, Status.DB_ID_NAME);
	Statuses statuses = Statuses.openWCMObjs(loginUser, filter);
	out.clear();
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="photo_importsyspics.jsp.title">导入系统图片到图片库</title>
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
<script src="photo_importsyspics.js"></script>
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
#watermarkpic{
	margin-left:38px;
}
.mainkindnotfound{
	cursor:pointer;
	color:red;
	font-size:16px
}
.mainkindfound{
	cursor:pointer;
	color:#483d8b
}
.sp_row_tip, .sp_row_tip_unfoldable{
	cursor:pointer;
	padding: 2px;
	padding-left: 5px;
	width: 90%;
	font-family: Courier New;
	font-size: 12px;
}
.sp_row_tip_unfoldable{/*不需要展开的*/
	cursor:auto;
}
.input_button{
	border:1px solid gray;
	cursor:pointer;
}
.divAdvancedItem{
	padding-bottom: 5px;
}
.sp_title{
	font-size: 12px;
	height: 25px;
	line-height: 25px;
	text-decoration: none;
	width: 80px;
	padding-left: 5px;
}
.spColumn{
	width: 50%;
	padding-left: 10px;
}
.opl_delete{
	position:absolute;
	right:6px;
	bottom:6px;
	height:20px;
}
.opl_moveleft{
	position:absolute;
	right:46px;
	bottom:6px;
	height:20px;
}
.opl_moveright{
	position:absolute;
	right:26px;
	bottom:6px;
	height:20px;
}

.myphoto{
	position:relative;
	background-repeat:no-repeat;
	background-position:center center;
	width:109px;
	height:105px;
	margin-top:5px;
	margin-bottom:5px;
	display:inline;		
}
</style>
<script language="javascript">
	var nSmallScreenSize = 800;//可见高度为800及以下，认为是小屏幕，无法正常展示该窗口，启用小屏模式
	var size = 585;
	if(window.screen.availHeight <= nSmallScreenSize) {
		window.m_bIsSmallScreen = true;
		size = 485;
	}
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'Ok',
			name : wcm.LANG.PHOTO_CONFIRM_1 || '确定'
		}],
		size : [900, size]
	};
	function defaultTime(_bIsEnd){
		var dt = new Date();
		var sAdaptedTime = _bIsEnd == true ? '00:00:00' : '23:59:59';
		return  dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + sAdaptedTime;
	}
	Event.observe(window, 'load', function(){	
	});
</script>
<style type="text/css">
.ext-strict .layout_border_center{border:1px solid black;border-width:1px;position:absolute;left:0;right:0;width:auto;}
.ext-ie6 .layout_border_center{border:1px solid black;border-width:1px;}
.ext-strict .layout_center_container{top:5px;bottom:5px;}
.ext-strict .layout_center{left:205px;right:5px;bottom:2px;top:0;}
.ext-ie6 .layout_container{padding-top:5px;padding-bottom:5px;}
.ext-ie6 .layout_center_container{padding-left:205px;padding-right:5px;padding-bottom:2px;}
.layout_west{width:192px;}
.ext-ie6 .fieldset{position:absolute;top:447px;left:205px;background:#FFFFFF;padding:0;height:136px;width:100%;}
.ext-strict .fieldset{position:absolute;top:445px;left:206px;background:#FFFFFF;height:131px;width:75.3%;}
.ext-gecko  .fieldset{position:absolute;top:445px;left:204px;background:#FFFFFF;height:118px;width:73.5%;}
.ext-ie #selected_photos{height:115px;}
.ext-gecko #selected_photos{height:105px;}
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
		document.write('#NavTree iframe{height:'+(window.m_fpCfg.size[1]-59)+'px!important;}');
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
	<input type="hidden" name="OtherKindIds" id="OtherKindIds" value=""></input>
	<input type="hidden" name="WatermarkFile" id="WatermarkFile" value="0"></input>
	<input type="hidden" name="WatermarkPos" id="WatermarkPos" value=""></input>		
	<input type="hidden" name="BmpConverType" id="BmpConverType" value=""></input>
	<input type="hidden" name="PicIds" id="PicIds" value=""></input>
</form>
<div class="layout_center_container">
	<div class="layout_west">
		<table border=0 cellspacing=0 cellpadding=0 style="width:192px;height:100%;position:absolute;">
		<tbody>
			<tr height="24">
				<td class="attr_column_title" onclick="showController(1)">
		<div WCMAnt:param="photo_importsyspics.jsp.navTree">导航树</div>
				</td>
			</tr>
			<tr id="NavTree">
				<td><iframe src="../nav_tree/nav_tree_inner.html?siteTypes=0" id="treeNav" allowtransparency="true" scrolling="no" frameborder="0" style="width:192px;height:100%;"></iframe></td>
			</tr>
			<tr height="24">
				<td class="attr_column_title" onclick="showController(2)" WCMAnt:param="photo_importsyspics.jsp.waterMark"><div>水印/图片分类</div></td>
			</tr>
			<tr style="display:none;height:100%;" id="Classic" valign="top">
				<td>
			<div class="div_classic" style="border:1px solid gray;height:100%;">
			<div class="attr_name">
				<span WCMAnt:param="photo_importsyspics.jsp.bmpTransStyle">BMP图片转换格式：</span>
				<select onchange="convertBmp(this);" id="BmpConverTypeSelect">
					<option value="bmp" WCMAnt:param="photo_importsyspics.jsp.noChange">不转换</option>
					<option value="gif">GIF</option>
					<option value="jpg">JPG</option>
				</select>
			</div>
			<div class="attr_name">
				<span WCMAnt:param="photo_importsyspics.jsp.waterMarkSel">选择水印：</span>
				<span id="watermarks">
					<select id="selwatermark" onchange="addWaterMark(this);" style="width:120px;">
							<option value="-1" WCMAnt:param="photo_importsyspics.jsp.noWaterMark">--不添加水印--</option>
							<%
								if(nSize>0){
									for(int i=0;i<nSize;i++){
										newWatermark = (Watermark)currWatermarks.getAt(i);
										String sFileName = newWatermark.getWMPicture();
										sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_HTTP) + sFileName;
							%>
									<option value="<%=newWatermark.getId()%>" _picsrc="<%=sFileName%>" _picfile="<%=newWatermark.getWMPicture()%>"><%=newWatermark.getWMName()%></option>
							<%
									}
								}
							%>
					</select>
				</span>
			</div>
			<div width=15px>&nbsp;</div>
			<div id="div_watermarkpos" style="display:none" >										
				<span title="点击全选" style="cursor:hand;margin-bottom :10px" onclick="selectAllPos()" WCMAnt:paramattr="title:photo_importsyspics.jsp.selectAll" WCMAnt:param="photo_importsyspics.jsp.waterMarkPosition">水印位置：</span><br />
				<label for="LT"><span WCMAnt:param="photo_importsyspics.jsp.leftTop">左上</span><input type="checkbox" id="LT" value="1"/></label>
				<label for="CM"><span WCMAnt:param="photo_importsyspics.jsp.center">居中</span><input type="checkbox" id="CM" value="2"/></label>
				<label for="RB"><span WCMAnt:param="photo_importsyspics.jsp.rightDown">右下</span><input type="checkbox" id="RB"  checked="true" value="3"/></label>
				<div>
					<img src=""  style="display:none;" id="watermarkpic">
				</div>
			</div>
			<div width=15px>&nbsp;</div>
			<div class="attr_name">
				<span WCMAnt:param="photo_importsyspics.jsp.mainClass">主分类：</span>
				<span id="mainkind" class=<%=nChannelId==0?"mainkindnotfound":"mainkindfound"%> _mainkindId="<%=nChannelId%>" _siteId="<%=nSiteId%>"><%=channelName%></span>
			</div>
			<div>
				<span WCMAnt:param="photo_importsyspics.jsp.otherClass">其它分类：</span>
				<span style="cursor:pointer;color:#483d8b" title="选择图片的其它分类" id="selOtherKinds" WCMAnt:paramattr="title:photo_importsyspics.jsp.selectOherClass" WCMAnt:param="photo_importsyspics.jsp.choose">选择</span><br />			
				<span id="otherkinds_holder">
					<select name="otherkinds" id="otherkinds" style="width:150px" multiple size="6" >	
					</select>
				</span>
			</div>
			</div>
			<div id="divMaskCrTimeInterval" style="position: absolute; -moz-opacity:80; filter:alpha(opacity=40); background: silver; display: none">&nbsp;</div>
			<div id="divMaskPubTimeInterval" style="position: absolute; -moz-opacity:80; filter:alpha(opacity=40); background: silver; display: none">&nbsp;</div>
				</td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="layout_center layout_border_center" id="centerDiv">
		<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;position:absolute;">
			<tr style="height:100%;">
				<td><iframe name="photo_list" id='photo_list' src="photo_syspics_list_noright.html" scrolling="no" frameborder="0" style="width:100%;height:100%;overflow:hidden;"></iframe>
				</td>
			</tr>
		</table>
	</div>
	<fieldset style="display:none;" class="fieldset" id="field">
		<legend style="line-height:24px;font-size:12px;cursor:hand;" onclick="Tobeimport()"><img src="../images/icon/SortDown.gif" border=0 alt="" id="toImportPic"/><span WCMAnt:param="photo_importsyspics.jsp.selectedRelation">待导入的图片</span></legend>
		<DIV id="selected_photos" style="position:absolute;width:99%;;overflow:auto;overflow-y:hidden;white-space:nowrap;">
		</DIV>
	</fieldset>
</div>
</body>
<script language="javascript">
<!--
	var nSiteId = <%=nSiteId%>;
	//小屏幕，兼容窗口显示不全的问题
	if(window.m_bIsSmallScreen) {
		if($("field")){
			$("field").style.top = "340px";
		}
	}
	/*
	Element.addClassName($("field"),"small-res");
	.small-res{top:340px!important;}
	*/

//-->
</script>
</html>