<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../include/public_server.jsp"%>
<%

	Watermarks currWatermarks = null;
	Watermark newWatermark = null;
	try{
		currWatermarks = Watermarks.openWCMObjs(ContextHelper.getLoginUser(),null);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("document_photo_attachments_import.jsp.getwatermarksfailure", "获取水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="document_photo_attachments_import.jsp.batimportpics"> 批量导入图片 </title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../js/source/wcmlib/com.trs.dialog/FaultDialog.js"></script>
<script type="text/javascript" src="./batchupload/swfobject.js" ></script>
<script language="javascript">
<!--
var m_cb = null;
function init(param, cb){
	m_cb = cb;
}
//获取iptc相关信息的函数
String.prototype.getIptcInfo = function(name){
	var reg = new RegExp("##" + name + ":([\\s\\S]*)##" + name + wcm.LANG.PHOTO_CONFIRM_116 || "结束");
	var sResults = this.match(reg);
	if(sResults!=null)
		return sResults[1];
	else
		return "";
}
//获取exif相关信息的函数
String.prototype.getExifInfo = function(name){
	var reg = new RegExp(name + ":([^<]*)<br\/>");
	var sResults = this.match(reg);
	if(sResults!=null)
		return sResults[1];
	else
		return "";
}
function addWaterMark(_select){
	var el = $("selwatermark");
	var op = el.options[el.selectedIndex];
	if(op.value == -1){
		Element.hide($("watermarkpic"));
		Element.hide($("div_watermarkpos"));
		$("WatermarkFile").value = "";
	}else{
		var imgLoaded = new Image();
		imgLoaded.onload = function(){
			resizeIfNeed(imgLoaded.height,imgLoaded.width);
			imgLoaded.onload = null;
			$("watermarkpic").src = op.getAttribute("_picsrc");
			Element.show($("watermarkpic"));
		}
		imgLoaded.src = op.getAttribute("_picsrc") + "?r="+Math.random();
							
		Element.show($("div_watermarkpos"));					
		$("WatermarkFile").value = op.getAttribute("_picfile");
	}
}
function resizeIfNeed(height,width){
	var h = height,w = width;
	if(height > 50 || width > 50){
		if(height > width){
			h = 50;	
			w = 50 * width/height;
		}else if(height==width){
			w = 50;
			h = 50;
		}else{
			w = 50;
			h = 50 * height/width;
		}
	}
	$("watermarkpic").width = w;
	$("watermarkpic").height = h;
}
var result = new Array();
//图片上传成功后主要的处理方法
function onSuccess(index,statusCode,jsonStr,fileName,size,nSuccCount){
	var arrResult = (jsonStr||jsonStr).split('<!--##########-->');
	arrResult[0] = (arrResult[0] || '').trim();
	var faultInfo = {
		code		: arrResult[1],
		message		: arrResult[2],
		detail		: arrResult[3],
		suggestion  : arrResult[4]
	}
	if(arrResult[0].indexOf("<!--ERROR2-->") > 0){
		Ext.Msg.fault(faultInfo, wcm.LANG.DOCUMENT_PROCESS_51 || '与服务器交互时出现错误');
		return 0;
	}
	if(arrResult[0].indexOf("<!--ERROR1-->") > 0){
		Ext.Msg.fault(faultInfo, wcm.LANG.DOCUMENT_PROCESS_51 || '与服务器交互时出现错误');
		return 0;
	}
	var arr = jsonStr.split("#==#");
	//------------
	$("UploadedFiles").value = arr[0];
	var wmpos = [];
	if($("LT").checked){
		wmpos.push($("LT").value);
	}
	if($("CM").checked){
		wmpos.push($("CM").value);
	}
	if($("RB").checked){
		wmpos.push($("RB").value);
	}
	$("WatermarkPos").value = wmpos.join(",");
	// 如果需要添加水印，则发送ajax请求去处理
	if($("WatermarkPos").value && $("WatermarkFile").value){
		BasicDataHelper.call("wcm61_photo","addWaterMark","form_imageInfo",true,function(_transport,_json){
			//alert(_transport.responseText);
		},function(_transport,_json){
			window.DefaultAjax500CallBack(_transport,_json,this);			
			FloatPanel.disableCommand("myclose",false);
		});
	}
	var tmpObj = new Object();
	tmpObj['desc'] = fileName;
	tmpObj['src'] = arr[0];
	tmpObj['url'] = '';
	tmpObj['type'] = 20;
	result.push(tmpObj);
	return 1;
}

function onComplete(index,statusCode,jsonStr,fileName,size,nSuccCount){
}
function onOk(){
	if(m_cb.callback)
		m_cb.callback(result);
}
</script>
<style>
	body{
		font-size:12px;
		padding:5px;
		margin:0;
	}
	div{
		width:100%;
		display:block;
		line-height:24px;
	}
	span{
		float:left;
		vertical-align:middle;
	}
	.attr_name{
		vertical-align:middle;
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
	#watermarkpic{
		position:"absolute";
		right:2px;
		top:2px;
	}
	#div_watermarkpos input,#div_watermarkpos label{
		vertical-align:middle;
	}
</style>
</head>

<body>
<div class="">
	<span class="attr_name" WCMAnt:param="photos_import.jsp.selectWaterMark">选择水印：</span>
	<span id="watermarks">
	<select id="selwatermark" onchange="addWaterMark(this);" style="width:120px;">
			<option value="-1" WCMAnt:param="photos_import.jsp.noWaterMark">--不添加水印--</option>
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

	<span id="div_watermarkpos" style="display:none">
		<span class="attr_name" style="padding-left:10px;" WCMAnt:param="photos_import.jsp.waterPosition">水印位置：</span>
		<input type="checkbox" id="LT" value="1"/><label for="LT" WCMAnt:param="photos_import.jsp.leftTop">左上</label>
		<input type="checkbox" id="CM" value="2"/><label for="CM" WCMAnt:param="photos_import.jsp.center">居中</label>
		<input type="checkbox" id="RB" checked="true" value="3"/><label for="RB" WCMAnt:param="photos_import.jsp.rightDown">右下</label>
	</span>	
</div>
<img src="" style="display:none" id="watermarkpic">
<div id="flashcontent" WCMAnt:param="photos_import.jsp.exception">浏览器或Flash环境异常, 导致该内容无法显示!</div>
<noscript WCMAnt:param="photos_import.jsp.unSupport">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
	<script type="text/javascript">
	// swf, id, w, h, ver, c, useExpressInstall, quality, xiRedirectUrl, redirectUrl, detectKey
	var flashvars = {};
	flashvars.uploadUrl = WCMConstants.WCM6_PATH +"system/import_photos_appendix.jsp;jsessionid=<%=session.getId()%>?method=upload%26ResponseType=2%26channelId="+getParameter('ChannelId')+'%26Type=DOC_APPENDIX_IMAGE_SIZE_LIMIT';
	flashvars.ext = "*.jpg;*.jpeg;*.gif;*.png;*.bmp";
	flashvars.onSuccess = "onSuccess";
	flashvars.onFailure = "onFailure";
	flashvars.onComplete = "onComplete";
	flashvars.maxSize = "10485760";
	var params = {
		quality : "high",
		scale : "exactfit",
		wmode : "transparent",
		allowScriptAccess : "sameDomain"
	};
	swfobject.embedSWF(WCMConstants.WCM6_PATH + "document/batchupload/fMultiUpload.swf" ,"flashcontent", "480", "230", "9.0.124", WCMConstants.WCM6_PATH + "document/batchupload/expressinstall.swf", flashvars, params);
</script>
<div style="display:none">
	<form id="form_imageInfo">
		<input type="hidden" name="WatermarkFile" id="WatermarkFile" value=""></input>
		<input type="hidden" name="WatermarkPos" id="WatermarkPos" value=""></input>
		<input type="hidden" name="UploadedFiles" id="UploadedFiles" value=""></input>
	</form>
</div>
</body>
</html>