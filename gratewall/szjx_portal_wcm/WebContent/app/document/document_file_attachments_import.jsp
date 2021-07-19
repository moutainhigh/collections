<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../../include/public_server.jsp"%>
<%
	//接受页面参数
	 
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="document_file_attachments_import.jsp.importfiles"> 批量导入文件 </title>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
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
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../js/source/wcmlib/com.trs.dialog/FaultDialog.js"></script>
<script type="text/javascript" src="./batchupload/swfobject.js" ></script>
<script language="javascript">
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
	var tmpObj = new Object();
	tmpObj['desc'] = fileName;
	tmpObj['src'] = arr[0];
	tmpObj['url'] = '';
	tmpObj['type'] = 10;
	result.push(tmpObj);
	return 1;
}
function onComplete(index,statusCode,jsonStr,fileName,size,nSuccCount){
}
function onFailure(_response){
}
function onOk(){
	if(m_cb.callback)
		m_cb.callback(result);
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
	#flashcontent{
		padding-left:10px;
	}
</style>
</head>

<body style="padding:0px;margin:0px;">
<div style="padding-left:10px;">
	<div style="padding-bottom:3px;"><span class="span_note" WCMAnt:param="document_file_attachments_import.jsp.selectuploadfile">请选择您要上传的文件：</span></div>
	<div id="flashcontent" WCMAnt:param="photos_import.jsp.exception">浏览器或Flash环境异常, 导致该内容无法显示!</div>
	<noscript WCMAnt:param="photos_import.jsp.unSupport">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
	<script type="text/javascript">
		// swf, id, w, h, ver, c, useExpressInstall, quality, xiRedirectUrl, redirectUrl, detectKey
		var flashvars = {};
		flashvars.uploadUrl = WCMConstants.WCM6_PATH +"system/import_appendix.jsp;jsessionid=<%=session.getId()%>?method=upload%26ResponseType=2%26channelId="+getParameter('ChannelId')+'%26Type=DOC_APPENDIX_FILE_SIZE_LIMIT';
		flashvars.ext = "*.*;";
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
		swfobject.embedSWF(WCMConstants.WCM6_PATH + "document/batchupload/fMultiUpload.swf" ,"flashcontent", "480", "250", "9.0.124", WCMConstants.WCM6_PATH + "document/batchupload/expressinstall.swf", flashvars, params);

		
	</script>
</div>
</body>
</html>