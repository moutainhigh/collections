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
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("document_photo_setwatermark.jsp.getwatermarksfailure", "获取水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="document_photo_setwatermark.jsp.setwatermark"> 设置水印 </title>
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
var m_cb = null,m_param ;
function init(param, cb){
	m_param = param;
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
	if(height > 240 || width > 470){
		if(height > width){
			h = 240;	
			w = 240 * width/height;
		}else if(height==width){
			w = 240;
			h = 240;
		}else{
			h = 240;
			w = 240 * width/height;
		}
	}
	$("watermarkpic").style.width = w+"px";
	$("watermarkpic").style.height = h+"px";
}

function onOk(){
	$("UploadedFiles").value = m_param.filenames.join(",");
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

	if(!$("WatermarkPos").value || !$("WatermarkFile").value)
		return true;
	$("mask").style.display = "block";
	
	BasicDataHelper.call("wcm61_photo","addWaterMark","form_imageInfo",true,function(_transport,_json){
		if(m_cb.callback)
			m_cb.callback();
		m_cb.close();
	},function(_transport,_json){
		window.DefaultAjax500CallBack(_transport,_json,this);			
		FloatPanel.disableCommand("myclose",false);
	});
	return false;
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
		
	}
	.set_box{line-height:24px;height:28px;}
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
	.img {
		text-align:center;
	}
	.mask{
		position:absolute;
		display:none;
		height:275px;
		line-height:275px;
		width:100%;
		font-size:24px;
		background:gray;
		color:red;
		filter:alpha(opacity=50); 
		-moz-opacity:0.5;
		-khtml-opacity: 0.5; 
		opacity: 0.5;
	}
	#watermarkpic{
		height:240px;
	}
	#div_watermarkpos input,#div_watermarkpos label{
		vertical-align:middle;
	}
</style>
</head>

<body>
<div class="mask" id="mask"  WCMAnt:param="document_photo_setwatermark.jsp.processingandwait">
	正在处理，请稍后...
</div>
<div class="set_box">
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
<div class="img">
	<img src="" style="display:none" id="watermarkpic">
</div>
<div style="display:none">
	<form id="form_imageInfo">
		<input type="hidden" name="WatermarkFile" id="WatermarkFile" value=""></input>
		<input type="hidden" name="WatermarkPos" id="WatermarkPos" value=""></input>
		<input type="hidden" name="UploadedFiles" id="UploadedFiles" value=""></input>
	</form>
</div>
</body>
</html>