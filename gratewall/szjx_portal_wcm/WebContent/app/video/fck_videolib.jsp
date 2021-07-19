<%@page import="com.trs.dev4.util.RequestUtil"%>
<%@page import="com.trs.dev4.util.FileUtil"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.wcm.photo.IMagicImage" %>
<%@ page import="com.trs.wcm.photo.impl.MagicImageImpl" %>
<%@include file="../include/public_server.jsp"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.util.StringUtil"%>
<%
	out.clear();
	boolean isHighQualityIp = VSConfig.isHighQualityIp(request.getRemoteAddr());
%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE WCMAnt:param="fck_photolib.jsp.trswcmvideoinserttovideolib">TRS WCM插入视频库视频</TITLE>
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/photo.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<!--wcm-dialog start-->
	<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
	<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<!--wcm-dialog end-->
	<!--AJAX-->
	<script src="../../app/js/data/locale/system.js"></script>
	<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!--CrashBoard-->
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
	<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
	<!--Calendar-->
	<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar_Obj.js" type="text/javascript"></script>
	<script language="javascript" src="../../app/js/source/wcmlib/calendar/calendar_lang/cn.js" type="text/javascript" WCMAnt:locale="../../app/js/source/wcmlib/calendar/calendar_lang/$locale$.js"></script>
	<script language="javascript" src="../../app/js/source/wcmlib/calendar/CTRSCalendar.js" type="text/javascript"></script>


	<script language="javascript">
	
	function ReplaceAll(str, sptr, sptr1)
	{
	while (str.indexOf(sptr) >= 0)
	{
	   str = str.replace(sptr, sptr1);
	}
	return str;
	}
	
	// string
	function escapeHTML(str) {
	    var div = document.createElement('div');
	    div.appendChild(document.createTextNode(str));
	    return div.innerHTML;
	}
	<!--
	top.actualTop = window;
	var oEditor	= window.parent.InnerDialogLoaded() ;
	var FCK		= oEditor.FCK ;
//var CKEDITOR	= dialogArguments.CKEDITOR;
//var editor		= dialogArguments.editor;

	function Ok(){
		
		var PLAYER_WIDTH = document.getElementById("playerWidth").value;
		var PLAYER_HEIGHT = document.getElementById("playerHeight").value;
		var logoCss = document.getElementById("logoCss").value;
        var autoPlay = document.getElementById("autoPlay").value;
		
		var FLVPLAYER_BASE = "<%= VSConfig.getFLVPlayerBase() %>";
		var FMSAPP_URL = "<%= VSConfig.getUploadFMSAppUrl() %>";
		var childNodes = $('selected_photos').childNodes;
		var bAddToPicAppend = true;
		//try {
			for (var i = 0; i < childNodes.length; i++) {
				if(childNodes[i].nodeType != 1) continue;
				var _nPhotoId = childNodes[i].getAttribute("PhotoId",2);
				var oDocInfo = DocInfoMap[_nPhotoId];
				var sDocId = oDocInfo.docId;
				var sDocTitle = oDocInfo.docTitle;
				var sChnlId = oDocInfo.chnlId;
				var sChnlName = oDocInfo.chnlName;
				var sChnlDesc = oDocInfo.chnlDesc;
				var iOSPlayUrl = oDocInfo.iosPlayUrl;
				var str = oDocInfo.token;
				var sToken = "";
				var changeUrlScript = "";
				var changeUrlElement = "";
				var videoFiles = str.split(";");
				if(videoFiles.length == 2) {
					sToken = videoFiles[0];
				} else {
					sToken = videoFiles[0];
					var videoPt = "";
					var videoHq = "";
					for(var j = 0 ; j < videoFiles.length; j++) {
						if(videoFiles[j] == "") {
							continue;
						}
						if(videoFiles[j].indexOf("hq") == -1) {
							videoPt = videoFiles[j];
						} else {
							videoHq = videoFiles[j];
						}
					}
				}

				//兼容推送,还需兼容TRSVideo的历史数据，
	if("public" != sToken.substring(0,6)&& sToken.indexOf("/") != -1){
		sToken = "public/"+sToken;
	}		
				//alert("[fck_videolib.jsp]Ok():\nVideoId=" + nVideoId + "\noDocInfo: " + Object.parseSource(oDocInfo));
				//var PLAYER_WIDTH = "480px";
				//var PLAYER_HEIGHT = "360px";				
				var oEmbedDIV =  FCK.EditorDocument.createElement( 'DIV' ) ;
				//var oEmbedDIV =  oCKEmbedDIV.$;
				oEmbedDIV.setAttribute("__fckvideo","true");
				oEmbedDIV.setAttribute("vid",sDocId);
	//			oEmbedDIV.setAttribute("loop","false");
				oEmbedDIV.width = PLAYER_WIDTH;
				oEmbedDIV.height = PLAYER_HEIGHT;
				// 1)load html to str; 2) replace in str
				var template = '<%= FileUtil.readTextFile(RequestUtil.getRealFile(application, "/app/video/fck_videolib.html"),"GBK").replaceAll("\\n","").replaceAll("\\r","") %>';
				template = ReplaceAll(template,"%iOSPlayUrl%",iOSPlayUrl);
				template = ReplaceAll(template,"%PLAYER_HEIGHT%",PLAYER_HEIGHT);
				template = ReplaceAll(template,"%oDocInfo.sPhotoSrcs%",oDocInfo.sPhotoSrcs);
				template = ReplaceAll(template,"%PLAYER_WIDTH%",PLAYER_WIDTH);
                template = ReplaceAll(template,"%PLAYER_LOGOCSS%",logoCss);
                template = ReplaceAll(template,"%PLAYER_AUTOPLAY%",autoPlay);
				template = ReplaceAll(template,"%sChnlId%",sChnlId);
				template = ReplaceAll(template,"%sChnlDesc%",sChnlDesc);
				template = ReplaceAll(template,"%sDocId%",sDocId);
				template = ReplaceAll(template,"%sDocTitle%",escapeHTML(sDocTitle));
				template = ReplaceAll(template,"%FLVPLAYER_BASE%",FLVPLAYER_BASE);
				template = ReplaceAll(template,"%sToken%",sToken);
				template = ReplaceAll(template,"%FMSAPP_URL%",FMSAPP_URL);
				//template = template.replace("%sDocId%",sDocId);
				
				oEmbedDIV.innerHTML = template;
				oEmbedDIV.title = ''+sDocId+'/'+sDocTitle;
				oEmbedDIV.setAttribute('imgurl',oDocInfo.sPhotoSrcs);
				var oFakeImage	= oEditor.FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oEmbedDIV ) ;
				//var oCKFakeImage = FCK.createFakeElement( oCKEmbedDIV, 'cke_flash', 'flash', true );
				//var oFakeImage = oCKFakeImage.$;

				var oPara = oEditor.FCK.EditorDocument.createElement("DIV");
				oPara	= oEditor.FCK.InsertElementAndGetIt( oPara ) ;
				//var oCKPara = FCK.document.createElement("P");
				//var oPara = oCKPara.$;
				//FCK.insertElement( oCKPara ) ;
				oPara.align = 'center';
				oPara.appendChild( oFakeImage ) ;
				
				oFakeImage.src=oDocInfo.sPhotoSrcs;

				oFakeImage.setAttribute( '_fckvideo', 'true', 0 ) ;
				oFakeImage.style.width = PLAYER_WIDTH;
				oFakeImage.style.height = PLAYER_HEIGHT;
			}
		//} catch (err) {
		//	alert(err.message);
		//}

		return true;

	}
	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_TREENODE,
		afterclick : function(event){
			//负责导航树对应的页面切换
			var context = event.getContext();
			var objId = context.objId;
			var objType = context.objType;
			var sParams = '';
			var mySrc = '../video/video_list_editor.html?';
			switch(objType){
				case WCMConstants.OBJ_TYPE_WEBSITEROOT:
					mySrc += "siteType=" + 2;
					break;
				case WCMConstants.OBJ_TYPE_WEBSITE:
					mySrc += "siteid=" + objId;
					break;
				case WCMConstants.OBJ_TYPE_CHANNEL:
					mySrc += "channelid=" + objId ;
					break;
			}
			$('photo_list').src = mySrc;
			return false;
		}
	});
	var SelectedPhotoIds = [];
	var DocInfoMap = {
	};
	function SetPhotoSelected(_nPhotoId,oDocInfo,_bChecked){
		var sPhotoSrcs = oDocInfo.sPhotoSrcs;
		var eDiv = $('myphoto_'+_nPhotoId);
		if(!_bChecked && eDiv){
			SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
			delete DocInfoMap[_nPhotoId];
			$('selected_photos').removeChild(eDiv);
		}
		else if(_bChecked){
			SelectedPhotoIds.push(_nPhotoId);
			createSpan(_nPhotoId,sPhotoSrcs);
			DocInfoMap[_nPhotoId] = oDocInfo;
		}
	}
	function createSpan(_nPhotoId,sPhotoSrcs){
		var eDiv = $('myphoto_'+_nPhotoId);
		if(eDiv==null){
			eDiv = document.createElement('SPAN');
			eDiv.id = 'myphoto_'+_nPhotoId;
			eDiv.className = 'myphoto';
			eDiv.setAttribute('PhotoId',_nPhotoId);
			eDiv.src = sPhotoSrcs;
			eDiv.innerHTML = '<img src="'+ eDiv.src +'" style="width:100;height:75"><img id="opl_delete_'+
				_nPhotoId+'" class="opl_delete" PhotoId="'+_nPhotoId+'" style="display:none" src="../images/photo/cancel.png">';
			eDiv.title = '视频ID: ' + _nPhotoId;
			Event.observe(eDiv,'mouseover',function(event){
				showPic(['opl_delete_'],_nPhotoId);
			});
			Event.observe(eDiv,'mouseout',function(event){
				hidePic(['opl_delete_'],_nPhotoId);
			});
			$('selected_photos').appendChild(eDiv);
			//删除绑定
			var eDelete = $('opl_delete_'+_nPhotoId)
			Event.observe(eDelete,'click',function(event){
				eDelete.parentNode.parentNode.removeChild(eDelete.parentNode);
				RemovePhoto(_nPhotoId);
				return false;
			});
		}
		return eDiv;
	}
	
	function showPic(group,_nPhotoId){
		for(var i=0 ; i < group.length; i++){
			var eDelete = $(group[i] + _nPhotoId);
			if(eDelete){
				Element.show(eDelete);
			}
		}
	}
	function hidePic(group,_nPhotoId){
		for(var i=0 ; i < group.length; i++){
			var eDelete = $(group[i] + _nPhotoId);
			if(eDelete){
					Element.hide(eDelete);
				}
		}
	}
	function RemovePhoto(_nPhotoId){
		SelectedPhotoIds = SelectedPhotoIds.remove(_nPhotoId);
		delete DocInfoMap[_nPhotoId];
		var oScope = $('photo_list').contentWindow;
		oScope.RefreshSelected(SelectedPhotoIds);
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
		position:relative;
		background-repeat:no-repeat;
		background-position:center center;
		width:75px;
		height:75px;
		margin:5px;
		display:block;
		cursor:pointer;
	}
	.opl_delete{
		float:right;
		height:20px;
		margin-top:-20px;
		margin-right:2px;
		cursor:pointer;
	}
	.ext-gecko .opl_delete{
		top:0px;
		position:relative;
	}
	.ext-safari .opl_delete{
		top:0px;
		position:relative;
	}
	.opl_moveleft{
		height:20px;
		margin-top:-20px;
		margin-left:-60px;
	}
	.opl_moveright{
		height:20px;
		margin-top:-20px;
		margin-left:-40px;
	}
	.dragging{
		padding:0px; 
		border:3px solid green;
	}
	.dragging1{
		background:buttonface; 
		color:black;
	}
	.ext-ie8 .wcm-btn{
		display:inline;
	}
</style>
</HEAD>

<BODY style="margin:0;padding:0;">
		<table height="100%" width="100%">
			<tr>
				<td align="left" valign="top">
	<TABLE width="800" height="100%" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD valign="top">
				<DIV id="docs_explorer" style="padding:4px;height:632px;width:800px;overflow:hidden;">
					<TABLE width="800" height="620" border="0" cellpadding="0" cellspacing="1" style="font-size:12px">
						<TR height="620">
							<TD width="200" valign="top" style="overflow:hidden;">
								<iframe src="../nav_tree/nav_tree_inner.html?siteTypes=2" scrolling="no" frameborder="0" style="height:450px;border:1px solid gray;overflow:hidden;"></iframe>
								<div style="height:166px;margin-top:2px;border:1px solid gray;overflow:auto;width:196px;background:#FFF">
								<br WCMAnt:param="fck_photolib.jsp.playerHeight">播放器宽度：<input id="playerWidth" type="text" value="<%=StringUtil.avoidEmpty(VSConfig.getFckPlayerWidth(),"480")%>" style="width:80px"></br>
								<br WCMAnt:param="fck_photolib.jsp.playerWidth">播放器高度：<input id="playerHeight" type="text" value="<%=StringUtil.avoidEmpty(VSConfig.getFckPlayerHeight(),"360")%>" style="width:80px"></br>
                                <br><label WCMAnt:param="fck_photolib.jsp.playerLogo">播放器LOGO：</label><input id="logoCss" type="text" value="<%=StringUtil.avoidEmpty(VSConfig.getFckPlayerLOGO(),"0")%>" style="width:80px"><img src="./image/answer.gif" WCMAnt:paramattr="title:fck_photolib.jsp.title.logoTips" title="Logo的透明度, 0-1之间的小数, 小数点后1位, 如0.4. 值越大越不透明" WCMAnt:paramattr="alt:fck_photolib.jsp.alt.logoTips" alt="Logo的透明度"/></br>
                                <br><label WCMAnt:param="fck_photolib.jsp.playerAutoplay">是否自动播放</label>
                                <select id="autoPlay" style="width:80px">
                                <option value="false" <%="false".equals(VSConfig.getFckPlayerAutoPlay())?"selected='selected'":"" %> WCMAnt:param="fck_photolib.jsp.noAutoplay">否</option>
                                <option value="true" <%="true".equals(VSConfig.getFckPlayerAutoPlay())?"selected='selected'":"" %> WCMAnt:param="fck_photolib.jsp.isAutoplay">是</option>
                                </select>
                                </br>
								</div>
							</TD>
							<TD width="600">
								<iframe name="photo_list" id='photo_list' scrolling="no" frameborder="0" style="width:100%;height:100%;border:1px solid gray;"></iframe>
								
							</TD>
						</TR>
					</TABLE>
				</DIV>
			</TD>
		</TR>
	</TABLE>
				</td>
				<td width="10" align="center" valign="top">
					<div style="width:2px;border-left:1px solid gray;background:white;overflow:hidden;height:623px;"></div></td>
				<td width="130" valign="top" id="otherContainer">
					<input id="btnOk" class="input_btn" style="margin-left:20px;width:80px" onclick="window.parent.Ok();"
						type="button" value="确定" fcklang="DlgBtnOK" WCMAnt:paramattr="value:fck_photolib.jsp.ok"/>
					<div style="height:5px;overflow:hidden"></div>
					<input name="button2" class="input_btn" style="margin-left:20px;width:80px" type="button" value="取消" WCMAnt:paramattr="value:fck_photolib.jsp.cancel" onclick="window.parent.Cancel();" fcklang="DlgBtnCancel">
					<div style="height:6px;overflow:hidden"></div>
					<div style="height:2px;width:120px;border-top:1px solid gray;background:white;overflow:hidden;"></div>
					<div style="height:6px;overflow:hidden"></div>
					<span class="message" WCMAnt:param="fck_photolib.jsp.videotobeInsert">待插入的视频:</span><br>
					<div style="position:absolute;width:120px;height:540px;border:1px solid gray;background:#FFF;overflow:auto;overflow-x:hidden;" id="selected_photos">
					</div>
				</td>
			</tr>
		</table>

</BODY>
</HTML>