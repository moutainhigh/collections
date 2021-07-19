<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@include file="../../include/public_server.jsp"%>
<%-- similar to /WCMV6/document/fck_photolib.jsp --%>
<%
	out.clear();
%>
<HTML xmlns:TRS_UI>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE>TRS WCM V6插入视频库视频</TITLE>
  <link href="../css/common.css" rel="stylesheet" type="text/css" />
	<script src="../js/com.trs.util/Common.js"></script>
	<script language="javascript">
	<!--
	top.actualTop = window;
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.logger.Logger');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.SimpleDragger');
	$import('com.trs.wcm.Grid');
	$import('com.trs.wcm.BubblePanel');
	$import('com.trs.wcm.FloatPanel');		
	//-->
	</script>
	<script language="javascript">
	<!--
	var oEditor	= window.parent.InnerDialogLoaded() ;
	var FCK		= oEditor.FCK ;
	var SelectedPhotoIds = [];
	var PhotoSrcMap = {};
	var VideoTokenMap = {};

	function createDiv(_nVideoId, sVideoImg){
//			alert("_nVideoId, sVideoImg=\n" + _nVideoId + ", " + sVideoImg);
		var eDiv = $('myphoto_'+_nVideoId);
		if(eDiv==null){
			eDiv = document.createElement('DIV');
			eDiv.id = 'myphoto_'+_nVideoId;
			eDiv.className = 'myphoto';
			eDiv.style.backgroundImage = 'url('+ sVideoImg +')';
		}
		return eDiv;
	}
	function getDiv(_nVideoId){
		return $('myphoto_'+_nVideoId);
	}
	function JustSelectVideos(_arrVideoIds, _arrImgs, _arrVideoTokens){
//			alert("JustSelectVideos(_arrVideoIds,_arrImgs)=\n" + _arrVideoIds + ", " + _arrImgs);
		for(var i=0;i<SelectedPhotoIds.length;i++){
			var eDiv = getDiv(SelectedPhotoIds[i]);
			if(eDiv){
				$('selected_videos').removeChild(eDiv);
			}
		}
		PhotoSrcMap = VideoTokenMap = {};
		SelectedPhotoIds = [];
		for(var i=0;i<_arrVideoIds.length;i++){
			SelectedPhotoIds.push(_arrVideoIds[i]);
			PhotoSrcMap[_arrVideoIds[i]] = _arrImgs[i];
			VideoTokenMap[_arrVideoIds[i]] = _arrVideoTokens[i];
			var eDiv = createDiv(_arrVideoIds[i],_arrImgs[i]);
			$('selected_videos').appendChild(eDiv);
		}
	}
	function Ok(){
		try{
		var FLVPLAYER_BASE = "<%= VSConfig.getFLVPlayerBase() %>";
		var FMSAPP_URL = "<%= VSConfig.getUploadFMSAppUrl() %>";

		for(var i=0;i<SelectedPhotoIds.length;i++){
			var nVideoId = SelectedPhotoIds[i];
			var sToken = VideoTokenMap[nVideoId];
			var oEmbedDIV = FCK.EditorDocument.createElement( 'DIV' ) ;
			oEmbedDIV.setAttribute("__fckvideo","true");
			oEmbedDIV.width = oEmbedDIV.height = 200;
			oEmbedDIV.innerHTML = '<div id="flashcontent">您的浏览器和Flash环境异常, 导致该内容无法显示!</div>'
	+'<noscript>您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>'
	+'<script type="text/javascript" src="' + FLVPLAYER_BASE + 'swfobject.js" ></'+'script>'
	+'<script type="text/javascript">'
	+'var so = new SWFObject("'+FLVPLAYER_BASE + 'FlowPlayer.swf", "player", "320", "240", "9.0.28", "#FF6600");'
	+'so.useExpressInstall("'+FLVPLAYER_BASE + 'expressinstall.swf");'
	+'var fpConfig = "{configFileName: \'' + FLVPLAYER_BASE + 'flowPlayer.js\','
	+'streamingServerURL: \'' + FMSAPP_URL + '\','
	+'videoFile: \'' + sToken + '\'}";'
	+'so.addVariable("config", fpConfig);'
	+'so.addParam("quality", "high");'
	+'so.addParam("wmode", "transparent");'
	+'so.addParam("scale", "exactfit");'
	+'so.addParam("allowScriptAccess", "sameDomain");'
	+'so.write("flashcontent");'
	+'<'+'/script>';
			var oFakeImage	= oEditor.FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oEmbedDIV ) ;
			oFakeImage.setAttribute( '_fckflash', 'true', 0 ) ;
			oFakeImage	= oEditor.FCK.InsertElementAndGetIt( oFakeImage ) ;
			oFakeImage.style.width = oFakeImage.style.height = 200;
//			oEditor.FCKFlashProcessor.RefreshView( oFakeImage, oEmbedDIV ) ;
		}

//		alert("[fck_videolib.jsp] Ok()函数尚未实现! location=" + location + "\n" + FLVPLAYER_BASE + "\n" + FMSAPP_URL);
		}catch(err){
			alert(err.message);
		}
		return true;
	}
	//-->
	</script>
<style type="text/css">
	body {
		padding-top:0px;
		background-color: #CCCCCC;
	}
	.input_btn{
		width:60px;
		margin-right:10px;
	}
	.input_text{
		border:1px solid gray;
		width:60px;
	}
	.message{
		font-weight:bold;
		color:green;
		line-height:20px;
	}
	.myphoto{
		background-repeat:no-repeat;
		background-position:center center;
		width:90px;
		height:90px;
		display:block;
	}
</style>
</HEAD>

<BODY style="margin:0;padding:0;">
		<table height="100%" width="100%">
			<tr>
				<td align="left" valign="top">
	<TABLE width="700" height="100%" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD valign="top">
				<DIV id="docs_explorer" style="padding:4px;height:512px;overflow:auto;">
					<TABLE width="700" height="500" border="0" cellpadding="0" cellspacing="1" style="font-size:12px">
						<TR height="500">
							<TD width="200" valign="top">
								<iframe src="../nav_tree/nav_tree_4_videolib_select.html" scrolling="no" frameborder="0" style="width:194px;height:350px;border:1px solid gray"></iframe>
								<div style="height:146px;margin-top:2px;border:1px solid gray;overflow:auto;width:196px;background:#FFF">
<!-- TODO: show selected video info -->
								</div>
							</TD>
							<TD width="500">
								<iframe name="photo_list" id='photo_list' src="javascript:void(0);" scrolling="no" frameborder="0" style="width:100%;height:100%;border:1px solid gray"></iframe>
							</TD>
						</TR>
					</TABLE>
				</DIV>
			</TD>
		</TR>
	</TABLE>
				</td>
				<td width="10" align="center" valign="top">
					<div style="width:2px;border-left:1px solid gray;background:white;overflow:hidden;height:503px;"></div></td>
				<td width="130" valign="top">
					<input id="btnOk" class="input_btn" onclick="window.parent.Ok();"
						type="button" value="确定" fcklang="DlgBtnOK"/>
					<div style="height:5px;overflow:hidden"></div>
					<input name="button2" class="input_btn" type="button" value="取消" onclick="window.parent.Cancel();" fcklang="DlgBtnCancel">
					<div style="height:6px;overflow:hidden"></div>
					<div style="height:2px;width:120px;border-top:1px solid gray;background:white;overflow:hidden;"></div>
					<div style="height:6px;overflow:hidden"></div>
					<span class="message">待插入的视频:</span>
					<div style="height:420px;border:1px solid gray;background:#FFF;overflow:auto;" id="selected_videos">
					</div>
				</td>
			</tr>
		</table>
	<script language="javascript">
	<!--
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	window.parent.SetBtnsRow(false);
	window.parent.SetAutoSize( true ) ;	
	//-->
	</script>
</BODY>
</HTML>