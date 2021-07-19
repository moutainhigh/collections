<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.VideoDocUtil"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString"%>

<%
	String fmsUrl = VSConfig.getUploadFMSAppUrl();

	String video = null;
	Document doc = null;
	VideoDocUtil vUtil = new VideoDocUtil();

	String sDocId = request.getParameter("docId");
	int docId = (sDocId == null) ? -1 : Integer.parseInt(sDocId);
	if (docId >= 0) {
		doc = Document.findById(docId);
		if (doc != null) {
			video = doc.getAttributeValue("TOKEN");
		}
	} else {
		video = request.getParameter("v");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Play Preview</title>
<script type="text/javascript" src="../js/opensource/prototype.js"></script>
<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
</head>

<body>
<% 
	if (video == null) {
		out.print("该视频不存在! qryStr=[" + CMyString.transDisplay(request.getQueryString()) + "]");
		return;
	}

%>
<div id="dvUpd" style="margin-top:5px;width:auto" align="center">

	<div id="flashcontent">您的浏览器和Flash环境异常, 导致该内容无法显示!</div>
	<noscript>您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>
	<script type="text/javascript">
	// <![CDATA[
	var so = new SWFObject("FlowPlayer.swf", "player", "320", "263", "9.0.28", "#FF6600");
	so.useExpressInstall("expressinstall.swf");

	var fpConfig = "{";
	fpConfig += "configFileName: 'flowPlayer.js',";
	fpConfig += "streamingServerURL: '<%= CMyString.filterForJs(fmsUrl) %>',";
	fpConfig += "videoFile: '<%= CMyString.filterForJs(video) %>'";
	fpConfig += "}";
	so.addVariable("config", fpConfig);
	
	so.addParam("quality", "high");
	so.addParam("wmode", "transparent");
	so.addParam("scale", "exactfit");
	so.addParam("allowScriptAccess", "sameDomain");
	so.write("flashcontent");
	// ]]>			
	</script>
</div>

<% 
	if (vUtil.hasNoMetadata(doc)) {
%>
<script type="text/javascript">
function refetchMetadata(docId, token) {
    new Ajax.Request( "refetchMetadata.jsp?docId=" + docId + "&token=" + token,
                      {method: "post", 
                      onComplete: handleRequest} );

}

function handleRequest(request) {
	//alert( "XHR Response:\n" + request.responseText );
	var jsonStr = request.responseText;
	$("jsonMeta").innerHTML = jsonStr;
	var metadata = eval('(' + jsonStr + ')');
	$("token").innerHTML = metadata.token;
	$("duration").innerHTML = metadata.duration;
	$("fps").innerHTML = metadata.fps;
	$("w").innerHTML = metadata.width;
	$("h").innerHTML = metadata.height;
	$("bps").innerHTML = metadata.bps;

	$("lnkSave").style.display = "inline";
	$("lnkFetch").style.display = "none";
}

function saveMetadata(docId, token) {
// THUMB=7f001_1176882217609.jpg&DURATION=4&WIDTH=320&HEIGHT=240&FPS=1000&BITRATE=0
	var toAppend = "THUMB=" + $("token").innerHTML + ".jpg&DURATION=" + $("duration").innerHTML	+ "&WIDTH=" + $("w").innerHTML + "&HEIGHT=" + $("h").innerHTML + "&FPS=" + $("fps").innerHTML + "&BITRATE=" + $("bps").innerHTML;

    new Ajax.Request( "saveMetadata.jsp?" + toAppend,
                      {method: "post", 
					   postBody: "docId=" + docId + "&token=" + token,
                      onComplete: handleSaveRequest} );

}

function handleSaveRequest(request) {
	//alert( "XHR Response:\n" + request.responseText );
	$("saveOk").innerHTML = request.responseText;

	$("lnkSave").style.display = "none";
	$("lnkFetch").style.display = "none";
}
</script>
<div>
<a id="lnkFetch" href="javascript:refetchMetadata('<%= docId %>', '<%= video %>');">重新获取视频元数据</a>
<a id="lnkSave" href="javascript:saveMetadata('<%= docId %>', '<%= video %>');" style="display:none;">保存到对象</a>
<span id="saveOk"></span>
</div>
<div>元信息:<span id="jsonMeta"></span></div>
<div>token: <span id="token"></span></div>
<div>时长: <span id="duration"></span></div>
<div>帧率: <span id="fps"></span></div>
<div>视频宽: <span id="w"></span></div>
<div>视频高: <span id="h"></span></div>
<div>比特率: <span id="bps"></span></div>
<div></div>
<div></div>
<%
	}
%>

</body>
</html>