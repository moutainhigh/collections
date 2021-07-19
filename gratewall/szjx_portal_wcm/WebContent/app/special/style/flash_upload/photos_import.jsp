<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../../../include/public_server.jsp"%>
<%
	//接受页面参数
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	String channelName = null;
	Channel currChannel = null;
	if(nChannelId == 0){
		channelName = LocaleServer.getString("photos_import.label.choose", "选择");
	}else{
		currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photos_import.jsp.channel_notfound", "没有找到ID为[{0}]的栏目"), new int[]{nChannelId}));
		}
		channelName = currChannel.getDesc();
		nSiteId = currChannel.getSiteId();
	}
	WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
	filter.addSearchValues(0, nSiteId);
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="photos_import.jsp.title"> 批量导入图片 </title>
	<!-- 公共js @ begin -->
	<script src="../../../js/easyversion/lightbase.js"></script>
	<script src="../../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../../js/easyversion/extrender.js"></script>
	<script src="../../../js/easyversion/elementmore.js"></script>
	<!-- 公共js @ end -->
	<!-- 发送ajax相关的js @ begin -->
	<script src="../../../js/easyversion/ajax.js"></script>
	<script src="../../../js/easyversion/basicdatahelper.js"></script>
	<script src="../../../js/easyversion/web2frameadapter.js"></script>
	<!-- 发送ajax相关的js @ end -->
<!--AJAX-->
<script type="text/javascript" src="./batchupload/swfobject.js" ></script>
<!--
<script src="photos_import.js" type="text/javascript"></script>
-->
<script language="javascript">
	//关闭当前CrashBoard
	function closeBoard(){
		try{
			window.top.wcm.CrashBoarder.get('cb_style_upload_images').notify();
		}catch(ex){
			//ignore
		}
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
function getRadioValue(_sRadioName){
	var radios = document.getElementsByName(_sRadioName);
	for (var i = 0; i < radios.length; i++){
		if(radios[i].checked) {
			return radios[i].value;
		}
	}
	return null;
}
//图片上传成功后主要的处理方法
function onSuccess(index,statusCode,jsonStr,fileName,size,nSuccCount){
	return 1;
}
function onComplete(index,statusCode,jsonStr,fileName,size,nSuccCount){
}
function onFailure(_response){
}
 function beforeUpload() {   
   return "&repeatNameMode="+getRadioValue("repeatNameMode");
 } 
</script>
<style>
	body{
		font-size:12px;
	}
	div{
		width:100%;
		display:block;
		line-height:24px;
	}
	span{
		float:left;
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
	.span_note{
		font-size:12px;
		font-family:"宋体";
		color:#097f12;
	}
	.operations{
		font-size:12px;
		height: 25px;
		line-height: 25px;
		padding-left: 30px;
	}
</style>
</head>

<body style="padding:0px;margin:0px;">
<div>
	<div style="padding-bottom:3px;">
		<span class="span_note" WCMAnt:param="photos_import.jsp.please_sel_rename_way">请选择文件重名后的处理方式：</span>
	</div>
	<div style="padding-bottom:3px;">
		<span style="padding-left:10px;"><input type="radio" name="repeatNameMode" value="1" checked></span>
		<span WCMAnt:param="photos_import.jsp.auto_cover">自动覆盖</span>
		<span style="padding-left:10px;"><input type="radio" name="repeatNameMode" value="2"></span>
		<span WCMAnt:param="photos_import.jsp.skip">跳过</span>
	</div>
	<div style="padding-bottom:3px;"><span class="span_note" WCMAnt:param="photos_import.jsp.please-sel_upload_pic">请选择您要上传的图片：</span></div>
	<div id="flashcontent" WCMAnt:param="photos_import.jsp.exception"  WCMAnt:param="photos_import.jsp.browser_orflash_eeror">浏览器或Flash环境异常, 导致该内容无法显示!</div>
	<noscript WCMAnt:param="photos_import.jsp.unSupport">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
	<script type="text/javascript">
		window.m_cbCfg = {
			btns : [
				{
					text : '关闭'
				}
			]
		}
		// swf, id, w, h, ver, c, useExpressInstall, quality, xiRedirectUrl, redirectUrl, detectKey
		var flashvars = {};
		flashvars.uploadUrl = WCMConstants.WCM6_PATH +"special/style/flash_upload/photos_import_dowith.jsp;jsessionid=<%=session.getId()%>?method=upload%26PageStyleId="+getParameter('PageStyleId');
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
		swfobject.embedSWF(WCMConstants.WCM6_PATH + "special/style/flash_upload/batchupload/fMultiUpload.swf" ,"flashcontent", "480", "250", "9.0.124", WCMConstants.WCM6_PATH + "special/style/flash_upload/batchupload/expressinstall.swf", flashvars, params);

		
	</script>
</div>
	<div style="display:none">
		<span WCMAnt:param="photos_import.jsp.selectFile">选择您要上传的文件(目前只支持Word格式文件)：</span>
		<span>
			<form name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile">
			</form>
			<form id="form_imageInfo">
				<input type="hidden" name="MainKindId" id="MainKindId" value="0"></input>
				<input type="hidden" name="OtherKindIds" id="OtherKindIds" value=""></input>
				<input type="hidden" name="WatermarkFile" id="WatermarkFile" value="0"></input>
				<input type="hidden" name="WatermarkPos" id="WatermarkPos" value=""></input>	
				<input type="hidden" name="UploadedFiles" id="UploadedFiles" value=""></input>
				<input type="hidden" name="SourceFiles" id="SourceFiles" value=""></input>
				<input type="hidden" name="BatchMode" id="BatchMode" value="0"></input>
				<input type="hidden" name="BmpConverType" id="BmpConverType" value=""></input>
			</form>
		</span>
	</div>
</body>
</html>