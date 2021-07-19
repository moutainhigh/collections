<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.wcm.content.domain.IDefaultFormatMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.DefaultFormat" %>
<%@ page import="com.trs.components.wcm.content.domain.DefaultFormatMgr" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.util.CMyFile" %>

<%@include file="../../include/public_server.jsp"%>
<%
	
	int nChannelId = currRequestHelper.getInt("Toolbar",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	int nItemCount = currRequestHelper.getInt("ItemCount",44);
	String sUserName = loginUser.getName();
	String sToolbarValue = null;
	String sAdvToolbarValue = null;
	String excludeToolbar = null;
	boolean hasToolbarFounded = false;
	boolean hasAdvToolbarFounded = false;
	if(nChannelId != 0){
		Channel currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
		}
		sToolbarValue = currChannel.getPropertyAsString("Toolbar");
		if(sToolbarValue != null && sToolbarValue.trim().length() != 0){
			String[] configGroup = sToolbarValue.split("&");
			for(int i=0; i < configGroup.length;i++){
				if(configGroup[i].indexOf("=") < 0 && hasToolbarFounded == false){
					sToolbarValue =  configGroup[i];
					hasToolbarFounded = true;
				}
				if(configGroup[i].indexOf("=") > 0 && configGroup[i].split("=")[0].equals(sUserName)){
					sToolbarValue = configGroup[i].split("=")[1];
					hasToolbarFounded = true;
					break;
				}
			}
		}
		if(!hasToolbarFounded){
			sToolbarValue = null;
		}
		sAdvToolbarValue = currChannel.getPropertyAsString("AdvToolbar");
		if(sAdvToolbarValue != null  && sAdvToolbarValue.trim().length() != 0){
			String[] configGroup = sAdvToolbarValue.split("&");
			for(int i=0; i < configGroup.length;i++){
				if(configGroup[i].indexOf("=") < 0 && hasAdvToolbarFounded == false){
					sAdvToolbarValue =  configGroup[i];
					hasAdvToolbarFounded = true;
				}
				if(configGroup[i].indexOf("=") > 0 && configGroup[i].split("=")[0].equals(sUserName)){
					sAdvToolbarValue = configGroup[i].split("=")[1];
					hasAdvToolbarFounded = true;
					break;
				}
			}
		}
		if(!hasAdvToolbarFounded){
			sAdvToolbarValue = null;
		}
		excludeToolbar = currRequestHelper.getString("excludeToolbar");
		if(excludeToolbar != null){
			excludeToolbar = excludeToolbar.toLowerCase();
		}
	}
	out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>FCKeditor</title>
<meta name="robots" content="noindex, nofollow" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="public" />
<link rel="stylesheet" type="text/css" href="../../css/wcm-common.css">
<script src="../../js/easyversion/lightbase.js"></script>
<script src="../../js/easyversion/extrender.js"></script>
<script src="../../js/runtime/myext-debug.js"></script>
<script src="../../js/source/wcmlib/WCMConstants.js"></script> 
<script src="../../js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>

<style type="text/css">
.accessibility{background:white;border:1px solid green;padding:4px;position:absolute;left:100px;top:100px;height:80%;overflow:auto;width:500px;z-index:9999990;}
.accessibility_class{padding-right:10px;}
.accessibility_warning{padding-right:10px;}
.accessibility_num{padding-right:10px;}
.accessibility_title{font-size:14px;}
.accessibility_close{font-size:12px;float:right;}
.accessibility li{margin-bottom:10px;}
.accessibility table{margin-top:5px;table-layout:fixed;border-collapse:collapse;}
.accessibility td{overflow:hidden;white-space:nowrap;padding-right:10px;cursor:pointer;}
.FCK__Flash
{
	border: #a9a9a9 1px solid;
	background-position: center center;
	background-image: url(css/images/fck_flashlogo.gif);
	background-repeat: no-repeat;
	width: 80px;
	height: 80px;
}
#xEditingArea{
	align:center;
	margin:0 auto;
}
.auto_message{
	float:right;
	margin-right:5px;
	border:1px solid black;
	padding:5px;
	background-color:#FFFFCC;
}
</style>
</head>
<body>
<script>
<%
	String sAttributes ="";
	if(nChannelId != 0){
		sAttributes = returnAttributes(nChannelId);
	}
%>
	//默认排版加载默认设置
	/*
	var nChannelId = "<%=nChannelId%>";
	if(nChannelId != 0) {
		initDefaultFormat(nChannelId);
	}
	function initDefaultFormat(nChannelId){
		var oPostData = {
			ObjId : nChannelId
		};
		var BasicDataHelper = new com.trs.web2frame.BasicDataHelper();
		BasicDataHelper.call("wcm6_defaultformat","getDefaultFormatAttributes",oPostData,true,function(_transport,_json){
			if(_transport.status == 200) {
				var oAttributes = {};
				var attributes = _transport.responseText;
				if(attributes != "" || attributes != null) {
					var arrAttributes = attributes.split(";");
					for (var i=0; i<arrAttributes.length; i++) {
						var attribute = arrAttributes[i];
						var attributeKey = attribute.split(":")[0];
						var attributeValue = attribute.split(":")[1];
						oAttributes[attributeKey] = attributeValue;
					}
				}
				FCK.Attributes = oAttributes;
			}
		});
	}
	*/
	//if(!"<%=sAttributes%>") {
	var oAttributes = {};
	var attributes = "<%=sAttributes%>";
	if(attributes != "" || attributes != null) {
		var arrAttributes = attributes.split(";");
		for (var i=0; i<arrAttributes.length; i++) {
			var attribute = arrAttributes[i];
			var attributeKey = attribute.split(":")[0];
			var attributeValue = attribute.split(":")[1];
			oAttributes[attributeKey] = attributeValue;
		}
	}
	window.Attributes = oAttributes;

	initExtCss();
	function Window_OnResize(){}
	window.onresize = function(){}
	function LoadScript( url, sVersion ){
		url = url + (sVersion?"?Version="+sVersion:'');
		document.write( '<scr' + 'ipt src="' + url + '"><\/scr' + 'ipt>' ) ;
	}
	function LoadCss( url ){
		document.write( '<link href="' + url + '" type="text/css" rel="stylesheet"/>' ) ;
	}
	// Main editor scripts.
	var sSuffix = Ext.isIE ? 'ie' : 'gecko' ;
	LoadScript( 'js/fckeditorcode_' + sSuffix + '.js' ) ;
	var m_sConfigFile = getParameter('ConfigEx') || '../fckconfig.js';
	LoadScript(m_sConfigFile, getParameter('Version'));
	if(Ext.isGecko){
		document.write('<style>iframe{overflow:auto;}</style>');
	}
</script>
<div id="docbox" style="display:none" name="paste2key4format"></div>
<div id="container" class="layout_container">
	<div id="xToolbarRow" class="layout_north editor_toolbar">
		<table cellpadding="0" cellspacing="0" style="width:100%;background:transparent;">
			<tr id="xCollapsed" style="display: none">
				<td id="xExpandHandle" class="TB_Expand" colspan="3"></td>
			</tr>
			<tr id="xExpanded" style="display: none">
				<td id="xTBLeftBorder" class="TB_SideBorder" style="width: 0px; display: none;"></td>
				<td id="xCollapseHandle" style="display: none" class="TB_Collapse" valign="bottom"></td>
				<td id="xToolbar" class="TB_ToolbarSet xToolbar" style="width:100%;"></td>
				<td class="TB_SideBorder" style="width: 0px"></td>
			</tr>
		</table>
	</div>
	<div id="editor_body" class="layout_center_container">
		<div class="layout_center">
			<table border=0 cellspacing=0 cellpadding=0 width="100%" height="100%" style="table-layout:fixed;">
				<tr class="top_ruler">
					<td width="13">&nbsp;</td><td>&nbsp;</td>
				</tr>
				<tr align="center" valign="top" style="height:100%">
					<td class="left_ruler" width="13"></td>
					<td class="editor_body" valign="top">
						<div id="xEditingArea" >
							
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="layout_south toolbar_btn_basic" id="btn_container" style="display:none">
		<div id="editor_btn_source" class="toolbar_source" _action="source" fcklang="CODE">代码</div>
		<div id="editor_btn_design" class="toolbar_design toolbar_current_btn" _action="design" fcklang="DESIGN">设计</div>
		<div style="margin:0 5px;float:left;" id="spt">|</div>
		<span id="btns_ctn"></span>
		<span id="auto_message" class="auto_message" style="display:none;"></span>
	</div>
</div>
<script src="lang/cn.js" WCMAnt:locale="lang/$locale$.js"></script>
<script language="javascript">
	FCKConfig.ProcessHiddenField() ;
	FCKConfig_LoadPageConfig() ;
	FCKConfig_PreProcess() ;
	LoadScript( '../fckeditor_ex.js', getParameter('Version')) ;
	//LoadScript( '../key4formatCore.js', getParameter('Version')) ;
	
</script>
<script language="javascript">
<%
	String sImageScale = ConfigServer.getServer().getSysConfigValue("UPLOAD_IMAGE_LIMTED_SCALE","0,0,0,0");
%>
	if(FCKConfig) {
		FCKConfig.ImageScale = "<%=sImageScale%>";
	}else {
		window.ImageScale = "<%=sImageScale%>";
	}
<%
	String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
	if(CMyFile.fileExists(sDomain + "/app/editor/editor/css/site/" + nSiteId + ".css")){
%>
	LoadCss(FCKConfig.BasePath + 'css/site/<%=nSiteId%>.css' ) ;
<%
	}
	if(CMyFile.fileExists(sDomain + "/app/editor/editor/css/channel/" + nChannelId + ".css")){
%>
	LoadCss(FCKConfig.BasePath + 'css/channel/<%=nChannelId%>.css' ) ;
<%
	} 
%>
    Element.update('editor_btn_source', FCKLang.CODE);
    Element.update('editor_btn_design', FCKLang.DESIGN);
	var arr= [
			'clearstyle',FCKLang.TIP1 , FCKLang.TIP2,
			'clearclasses', FCKLang.TIP3, FCKLang.TIP4,
			'clearblank', FCKLang.TIP5, FCKLang.TIP5,
			'outdentpara', FCKLang.TIP6,FCKLang.TIP6,
			'replacenbsps', FCKLang.TIP14, FCKLang.TIP7,
			'db2sb', FCKLang.TIP8, FCKLang.TIP9,
			'sb2db', FCKLang.TIP15, FCKLang.TIP16,
			'key4format', '一键排版', '一键排版'
			//'setpageproperties', FCKLang.TIP17, FCKLang.TIP17
	];
//	if(Ext.isIE){
//		arr.push(
//			'pasteasplain', FCKLang.TIP10,FCKLang.TIP11
//		);
//	}



	var rst = [];
	for(var i=0,n=arr.length;i<n;i+=3){
		rst.push('<div unselectable="on" class="btn" _action="' + arr[i] + '">' + 
			'<div unselectable="on" class="btn_inner"' + 
			' title="' + arr[i+1] + '">' + arr[i+2] + '</div>' + 
			'</div>');
	}
	$('btns_ctn').innerHTML = rst.join('');
</script>
<script type="text/javascript">
	LoadScript( 'js/plugins.js', getParameter('Version')) ;
	
	if ( FCKBrowserInfo.IsIE ){
		// Create the default cleanup object used by the editor.
		FCK.IECleanup = new FCKIECleanup( window ) ;
		FCK.IECleanup.AddItem( FCKTempBin, FCKTempBin.Reset ) ;
		FCK.IECleanup.AddItem( FCK, FCK_Cleanup ) ;
	}
	LoadCss( FCKConfig.SkinPath + 'fck_editor.css' ) ;
	if(getParameter('Css')){
		FCKConfig.EditorAreaCSS = Ext.isArray(FCKConfig.EditorAreaCSS) ? 
					FCKConfig.EditorAreaCSS : [FCKConfig.EditorAreaCSS];
		FCKConfig.EditorAreaCSS.push(FCKConfig.BasePath + 'css/' + getParameter('Css'));
	}	
	FCK_ContextMenu_Init() ;
	FCKPlugins.Load() ;
	window.document.dir = FCKLang.Dir ;
//	if ( FCKConfig.ForcePasteAsPlainText || FCKConfig.AutoDetectPasteFromWord )
//		FCK.Events.AttachEvent( 'OnPaste', FCK.Paste ) ;
	Event.observe(window, 'load', function(){
		FCKeditorAPI = parent.FCKeditorAPI;
		FCKeditorAPI.editorWindow = window;
		FCKeditorAPI.__Instances[ FCK.Name ] = FCK ;
		if ( FCKBrowserInfo.IsIE )
			FCK_PreloadImages() ;
		else
			LoadToolbarSetup() ;
	});
	function LoadToolbarSetup(){
		FCKeditorAPI._FunctionQueue.Add( LoadToolbar ) ;
	}
	//加载编辑器样式文件
	<%
		if(CMyFile.fileExists(sDomain + "/app/editor/editor/css/channel/" + nChannelId + ".css")){
	%>
	FCKConfig.EditorAreaCSS = [ FCKConfig.BasePath + 'css/channel/<%=nChannelId%>.css'];
	<%
		}else if(CMyFile.fileExists(sDomain + "/app/editor/editor/css/site/" + nSiteId + ".css")){
	%>
	FCKConfig.EditorAreaCSS = [ FCKConfig.BasePath + 'css/site/<%=nSiteId%>.css'];
	<%
		}
	%>
	//编辑器工具栏定制
	var temp = new Array();
	var result = new Array();
	<%
		if(sToolbarValue != null && sToolbarValue.length()>0 && !sToolbarValue.equals("e")){
			sToolbarValue = sToolbarValue.substring(0,sToolbarValue.length()-1);
			int nCount = 0;
			int nEnter = 0;
			for(int i=0; i< sToolbarValue.split(",").length;i++){
				String sToolBari = sToolbarValue.split(",")[i];
				if(excludeToolbar !=null && (excludeToolbar.indexOf(sToolBari.toLowerCase()) != -1)){
					continue;
				}

				nCount ++;
				if(sToolBari.indexOf("+") != -1 && nEnter == 0){
					nCount = 0;
					nEnter ++;
			
	%>
					result.push(temp);
					result.push("/");
					temp = new Array();
	<%
					continue;
				}
				if(sToolBari.indexOf("+") != -1 && nEnter != 0){
					continue;
				}
				if(sToolBari.equals("Style")){
					nCount += 5;
					if(nCount >= nItemCount){
	%>
						temp.push('<%=sToolBari%>');		
	<%
					}
				}
				if(sToolBari.equals("FontName")){
					nCount += 5;
					if(nCount >= nItemCount){
	%>
						temp.push('<%=sToolBari%>');		
	<%
					}
				}
				if(sToolBari.equals("FontSize")){
					nCount += 4;
					if(nCount >= nItemCount){
	%>
						temp.push('<%=sToolBari%>');	
	<%
					}
				}
				if(nCount >= nItemCount){
					nCount = 0;
					nEnter ++;
	%>
					result.push(temp);
					result.push("/");
					temp = new Array();
	<%
					continue;
				}
	%>
				temp.push('<%=sToolBari%>');
	<%
			}
			if(sAdvToolbarValue != null && !sAdvToolbarValue.equals("null")){
	%>
			temp.push("AdvToolbar");
	<%
			}
	%>
			result.push(temp);
			FCKConfig.ToolbarSets["<%=nChannelId%>"] = result;
	<%
			if(sAdvToolbarValue != null && !sAdvToolbarValue.equals("null")){			
				if(sAdvToolbarValue.length() == 0){								
	%>
					FCKConfig.ToolbarSets["<%=nChannelId%>_ADV"] = [['Toolbar']];
	<%
				}else{
	%>
					temp = new Array();
					result = new Array();
	<%
					sAdvToolbarValue = sAdvToolbarValue.substring(0,sAdvToolbarValue.length()-1);
					int nAdvCount = 0;
					int nAdvEnter = 0;
					for(int i=0; i< sAdvToolbarValue.split(",").length;i++){
						String sAdvToolBari = sAdvToolbarValue.split(",")[i];
						if(excludeToolbar !=null && (excludeToolbar.indexOf(sAdvToolBari.toLowerCase()) != -1)){
							continue;
						}

						nAdvCount ++;
						if(sAdvToolBari.indexOf("+") != -1 && nAdvEnter==0){
							nAdvCount = 0;
							nAdvEnter ++;
	%>
							result.push(temp);
							result.push("/");
							temp = new Array();	
	<%
					continue;
				}
				if(sAdvToolBari.indexOf("+") != -1 && nAdvEnter!=0){
					continue;
				}
				if(sAdvToolBari.equals("Style")){
					nAdvCount += 5;
					if(nAdvCount >= nItemCount){
	%>
						temp.push('<%=sAdvToolBari%>');		
	<%
					}
				}
				if(sAdvToolBari.equals("FontName")){
					nAdvCount += 5;
					if(nAdvCount >= nItemCount){
	%>
						temp.push('<%=sAdvToolBari%>');		
	<%
					}
				}
				if(sAdvToolBari.equals("FontSize")){
					nAdvCount += 4;
					if(nAdvCount >= nItemCount){
	%>
						temp.push('<%=sAdvToolBari%>');	
	<%
					}
				}
				if(nAdvCount >= nItemCount){
					nAdvCount = 0;
					nAdvEnter ++;
	%>
					result.push(temp);
					result.push("/");
					temp = new Array();
	<%
					continue;
				}
	%>
					temp.push('<%=sAdvToolBari%>');
	<%
					}
	%>
					temp.push('Toolbar');
					result.push(temp);
					FCKConfig.ToolbarSets["<%=nChannelId%>_ADV"] = result;
	<%
				}
			}
		}else if(sToolbarValue != null && sToolbarValue.trim().length()>0){
	%>
			FCKConfig.ToolbarSets["<%=nChannelId%>"] = [[]];
	<%
		}
	%>
	//低分辨率下对工具栏显示的适应
	if(<%=needChange(sToolbarValue,nItemCount)%>){
		$('xToolbarRow').className = 'layout_north editor_toolbar_adv';
		$('editor_body').className = 'layout_center_container layout_center_container2';
		$('container').className = 'layout_container layout_container2';
		$('xToolbar').className = 'TB_ToolbarSet xToolbar_adv';
	}
	//自定义高级工具栏
	(function(){
		FCKLang.WCM6ToolbarBtn = FCKLang.WCM6ToolbarBtn || "切换到简易工具栏";
		FCKLang.WCM6AdvToolbarBtn = FCKLang.WCM6AdvToolbarBtn || "切换到高级工具栏";
		var TRSOptionalToolbarCommand = function(_sName){
			this.ToolBarName = _sName ;
			this.Name = 'TRSOptionalToolbar' ;
		}
		TRSOptionalToolbarCommand.prototype.Execute = function(){
			FCK.ToolbarSet.Load(this.ToolBarName) ;
			if(!<%=needChange(sToolbarValue,nItemCount)%> && <%=needChange(sAdvToolbarValue,nItemCount)%>){
				var bIsAdvanced = "<%=nChannelId%>_ADV".equalsI(this.ToolBarName);
				$('xToolbarRow').className = (bIsAdvanced) ? 'layout_north editor_toolbar_adv'
							: 'layout_north editor_toolbar';
				$('editor_body').className = (bIsAdvanced) ? 'layout_center_container layout_center_container2'
							: 'layout_center_container';
				$('container').className = (bIsAdvanced) ? 'layout_container layout_container2'
							: 'layout_container';
				$('xToolbar').className = (bIsAdvanced) ? 'TB_ToolbarSet xToolbar_adv'
												: 'TB_ToolbarSet xToolbar';
			}else if(<%=needChange(sToolbarValue,nItemCount)%> && !<%=needChange(sAdvToolbarValue,nItemCount)%>){
				var bIsAdvanced = "<%=nChannelId%>_ADV".equalsI(this.ToolBarName);
				$('xToolbarRow').className = (!bIsAdvanced) ? 'layout_north editor_toolbar_adv'
							: 'layout_north editor_toolbar';
				$('editor_body').className = (!bIsAdvanced) ? 'layout_center_container layout_center_container2'
							: 'layout_center_container';
				$('container').className = (!bIsAdvanced) ? 'layout_container layout_container2'
							: 'layout_container';
				$('xToolbar').className = (!bIsAdvanced) ? 'TB_ToolbarSet xToolbar_adv'
												: 'TB_ToolbarSet xToolbar';
			}
		}
		TRSOptionalToolbarCommand.prototype.GetState = function(){
			return FCK_TRISTATE_OFF ;
		}
		FCKCommands.RegisterCommand( 'Toolbar', new TRSOptionalToolbarCommand("<%=nChannelId%>")) ;
		var oOptionalToolbarItem = new FCKToolbarButton( 'Toolbar', FCKLang.WCM6ToolbarBtn
			,null, null, null, null, 82) ;
		FCKToolbarItems.RegisterItem( 'Toolbar', oOptionalToolbarItem ) ;

		FCKCommands.RegisterCommand( 'AdvToolbar', new TRSOptionalToolbarCommand("<%=nChannelId%>_ADV")) ;
		var oOptionalToolbarItem = new FCKToolbarButton( 'AdvToolbar', FCKLang.WCM6AdvToolbarBtn
			,null, null, null, null, 81) ;
		FCKToolbarItems.RegisterItem( 'AdvToolbar', oOptionalToolbarItem ) ;

		//排除未开启的选件按钮
		kickUnable("<%=nChannelId%>");
	})();
	function kickUnable(_nId){
		var editorCfg = window.parent.parent.editorCfg;
		if(!editorCfg) return;
		
		if(!editorCfg.enablePhotolib){
			RemoveToolbar(_nId,'PhotoLib');
			RemoveToolbar(_nId + '_ADV','PhotoLib');
		}
		if(!editorCfg.enableFlashlib){
			RemoveToolbar(_nId,'FlashLib');
			RemoveToolbar(_nId + '_ADV','FlashLib');
		}
		if(!editorCfg.enableAdintrs){
			RemoveToolbar(_nId,'AdInTrs');
			RemoveToolbar(_nId + '_ADV','AdInTrs');
		}
		if(!editorCfg.enableCkmspellcheck){
			RemoveToolbar(_nId,'CKMSpellCheck');
			RemoveToolbar(_nId + '_ADV','CKMSpellCheck');
		}
	}
	function LoadToolbar(){
		//2011-10-08保存当前站点ID，用于后面初始化页面属性时，进行判断
		FCK.nSiteId='<%=nSiteId%>';
		//2011-09-26 保存用户角色，是否为管理员；用于在后面保存页面属性时，如果是管理员，则存放到CSS样式文件中。
		if(!FCK.isAdmin)
			FCK.isAdmin = false;
		<%if(loginUser.isAdministrator()){%>
				FCK.isAdmin = true;
		<%}%>
		FCK.setCookie("tempSelect",3);
		var oToolbarSet = FCK.ToolbarSet 
			= FCKToolbarSet_Create(FCKURLParams['ToolbarLocation']) ;
		if ( oToolbarSet.IsLoaded )
			StartEditor() ;
		else{
			oToolbarSet.OnLoad = StartEditor ;
			if(!FCKConfig.ToolbarSets[FCKURLParams['Toolbar']]){
				FCKURLParams['Toolbar'] = 'WCM6';
			}
			oToolbarSet.Load( FCKURLParams['Toolbar'] || 'Default' ) ;
		}
	}
	function StartEditor(){
		FCK.ToolbarSet.OnLoad = null ;
		FCKeditorAPI._FunctionQueue.Remove( LoadToolbar ) ;
		FCK.Events.AttachEvent( 'OnStatusChange', WaitForActive ) ;
		// Start the editor.
		FCK.StartEditor() ;
	}
	function WaitForActive( editorInstance, newStatus ){
		if ( newStatus == FCK_STATUS_ACTIVE ){
			_AttachFormSubmitToAPI() ;
			FCK.SetStatus( FCK_STATUS_COMPLETE ) ;
			if ( typeof( window.parent.FCKeditor_OnComplete ) == 'function' )
				window.parent.FCKeditor_OnComplete( FCK ) ;
		}
	}
</script>
<script src="fckeditor.js"></script>
<script src="../wcag2.js"></script>
<script>
	if(Ext.isIE && window.OfficeActiveX){
		OfficeActiveX.Init();
	}
	Element.show('btn_container');
	if(FCKConfig && FCKConfig.AutoAppendStyle){
		try{
			var css = document.styleSheets(2);
			if(css && css.rules[0] && css.rules[0].style.width){
				$("xEditingArea").style.width = document.styleSheets(2).rules[0].style.width;
			}	
		}catch(ex){}
	}
</script>
<script language="javascript">
<%
	//默认排版设置，加载当前栏目样式

	int nChannelID = currRequestHelper.getInt("Toolbar",0);
	String sUrl = "";
	if(nChannelID != 0) {
		Channel channel = Channel.findById(nChannelID);
		sUrl = DefaultFormat.getCssFileRelativePath(channel);
	}
	/*star(暂时这样处理)*/
	//DefaultFormatMgr mgr = new DefaultFormatMgr();
	//DefaultFormat defaultformat = mgr.getDefaultFormat(channel);
	/*end*/
	//boolean isWord = defaultformat.getPropertyAsBoolean("FORWORD", true);
	//if(!isWord){
%>
		FCKConfig.EditorAreaCSS.push("<%=sUrl%>");
<%
	//}
%>
</script>
</body>
</html>
<%!
	private boolean needChange(String sToolbarValue,int nItemCount){ 
		if(sToolbarValue != null && sToolbarValue.length()>0 && !sToolbarValue.equals("e")){
			int nCount = 0;
			int nEnter = 0;
			for(int i=0; i< sToolbarValue.split(",").length;i++){
				String sToolBari = sToolbarValue.split(",")[i];
				nCount ++;
				if(sToolBari.indexOf("+") != -1){
					nCount = 0;
					nEnter ++;
				}
				if(sToolBari.equals("Style")) nCount += 5;
				if(sToolBari.equals("FontName")) nCount += 5;
				if(sToolBari.equals("FontSize")) nCount += 4;
				if(nCount >= nItemCount){
					nCount = 0;
					nEnter ++;
				}
				if(nEnter > 2) return true;
			}
		}
		return false;
	}
%>
<%!
private String returnAttributes(int nChannelId) throws Exception {
	String sAttributes = "";
	String sFor = "";//判断是否采用系统级的标志
	int nObjId = nChannelId;//currRequestHelper.getInt("ObjId",0);
	int nObjType = Channel.OBJ_TYPE;//currRequestHelper.getInt("ObjType",0);
	//栏目级
	if(nObjType == Channel.OBJ_TYPE) {
		Channel channel = Channel.findById(nObjId);
		IDefaultFormatMgr mgr = (IDefaultFormatMgr) DreamFactory.createObjectById("IDefaultFormatMgr");
		DefaultFormat defaultFormat = mgr.getDefaultFormat(channel);
		if(defaultFormat != null) {
			if(!defaultFormat.isStatus() && !defaultFormat.isInherit()) {
				sFor += "isInherit:0;";
				sFor += "isStatus:0;";
			}else{
				sAttributes = defaultFormat.getPropertyAsString("Attribute");
				sFor = defaultFormat.getPropertyAsString("Attribute");
				//word粘贴是否开启默认排版配置
				boolean isWord = defaultFormat.getPropertyAsBoolean("FORWORD", false);
				boolean isInherit = defaultFormat.getPropertyAsBoolean("INHERIT", true);
				boolean isStatus = defaultFormat.getPropertyAsBoolean("STATUS", false);
				if(isWord){
					sAttributes += "isForWord:1;";
				}else{
					sAttributes += "isForWord:0;";
				}
				if(isInherit) {
					sAttributes += "isInherit:1;";
					sAttributes += "isStatus:0;";
				}else if(isStatus) {
					sAttributes += "isStatus:1;";
				}else {
					sAttributes += "isStatus:0;";
				}
			}
		}
	}else {//站点级
		if( nObjId != 0 ) {
			int nObjectId = DefaultFormat.getObjectId(nObjType, nObjId);
			DefaultFormat defaultFormat = null;
			if (nObjectId != 0) {
				defaultFormat = DefaultFormat.findById(nObjectId);
				//word粘贴是否开启默认排版配置
				boolean isWord = defaultFormat.getPropertyAsBoolean("FORWORD", false);
				boolean isInherit = defaultFormat.getPropertyAsBoolean("INHERIT", true);
				boolean isStatus = defaultFormat.getPropertyAsBoolean("STATUS", false);
				sAttributes = defaultFormat.getPropertyAsString("Attribute");
				if(!isInherit) {
					sFor = defaultFormat.getPropertyAsString("Attribute");
					if(!isStatus) {
						sAttributes="";
					}
				}
				if(isWord){
					sAttributes += "isForWord:1;";
				}else{
					sAttributes += "isForWord:0;";
				}
				if(isInherit) {
					sAttributes += "isInherit:1;";
					sAttributes += "isStatus:0;";
				}else if(isStatus) {
					sAttributes += "isStatus:1;";
				}else {
					sAttributes += "isStatus:0;";
				}
			}
		}
	}
	//系统级
	if("".equals(sFor)) {
		String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
		String sPath = sDomain + "/app/editor/defaultformat/systemConfig.txt";
		if(CMyFile.fileExists(sPath)) {
			sAttributes = CMyFile.readFile(sPath);
		}
	}
	return sAttributes;
}
%>