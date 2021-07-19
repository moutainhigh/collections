<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.dl.util.IPUtil" %>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ include file="../document/document_addedit_import.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%@include file="../document/document_addedit_include.jsp"%>
<%
	//
	out.clear();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM V6 新建视频直播</TITLE>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/document_addedit.css" rel="stylesheet" type="text/css" />
<script src="../js/com.trs.util/Common.js"></script>
<script label="Imports">
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.dialog.Dialog');
</script>
<script label="PageScope">
	//当前编辑中的文档ID,新文档为0
	var CurrDocId = '<%=nDocumentId%>';
	var DocChannelId = '<%=(docChannel!=null)?docChannel.getId():0%>';
	var CurrChannelId = '<%=(currChannel!=null)?currChannel.getId():0%>';
	var bIsCanTop = <%=bIsCanTop%>;
	var bIsCanPub = <%=bIsCanPub%>;
</script>
<script src="./video_add.js" label="PageScope"></script>
<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
<style type="text/css">
.attr_input_text{
	float:center;
	overflow:hidden;
	border: #b7b7a6 1px solid;
}
.attr_input_text input{
	width:100%;
	height:18px;
	border:0;
}
.attr_textarea{
	margin:0 5px;
	width:228px;
}
.attr_textarea textarea{
	border:0;
	border: #b7b7a6 1px solid;
}
</style>
</HEAD>
<BODY>

<%
	String fmsUrl = VSConfig.getUploadFMSAppUrl();
%>
<div id="con" style="margin-top:5px;width:auto">
	
	<div id="dvUpd" style="margin-top:5px;width:auto" align="center">

		<div id="flashcontent">浏览器或Flash环境异常, 导致该内容无法显示!</div>
		<noscript>您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>
		<script type="text/javascript">
			// <![CDATA[
			
			// swf, id, w, h, ver, c, useExpressInstall, quality, xiRedirectUrl, redirectUrl, detectKey
			var so = new SWFObject("live2.swf", "fCam", "800", "480", "9.0.28", "#FF6600");
			so.addVariable("localIP", "<%= IPUtil.ip2Hex(request.getRemoteAddr()) %>_");   // liveName
			so.addVariable("fmsAppUrl", "<%= fmsUrl %>");
			so.addParam("quality", "high");
			so.addParam("wmode", "transparent");
			so.addParam("scale", "exactfit");
			so.addParam("allowScriptAccess", "sameDomain");
			so.write("flashcontent");
			
			// ]]>
		</script>

	</div>
	<div id="dvMeta" style="margin-top:5px;" align="center">
		<div>视频直播的地址等参数如下，您可以将它们配置到嘉宾访谈的首页模板中!</div>
		<div>fmsAppUrl: <span id="sFmsAppUrl" style="color: blue"><%= fmsUrl %></span></div>
		<div>liveName: <span id="sLiveName" style="color: blue"></span></div>
		<div><span style="color: red">注意：视频直播过程中，不要关闭本窗口!</span></div>
	</div>
</div>

<script type="text/javascript">
// 提供给Flash调用的函数(Begin)
function showPlayLink(flvName) {
//	alert("flvName=" + flvName);
	$("sLiveName").innerHTML = flvName;
}
// 提供给Flash调用的函数(End)
</script>

</BODY>
</HTML>