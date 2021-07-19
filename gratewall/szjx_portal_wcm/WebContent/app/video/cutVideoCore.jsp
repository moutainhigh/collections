<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.video.persistent.XVideo" %>
<%@ page import="com.trs.components.video.util.HttpUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>视频标引与切割</title>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/prototype.js" ></script>

<script type="text/javascript" src="./js/opensource/FABridge.js" ></script>
<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
<script src="../video/js/com.trs.util/Common.js"></script>
<style>
body {
	font-size: 12px;
}
</style>
<script label="Imports">
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.util.JSON');
</script>
</head>
<%
	String fmsUrl = VSConfig.getUploadFMSAppUrl();
	String videoServiceUrl = VSConfig.getUploadJavaAppIntranetUrl();

	String video = null;
	String thumb = null;
	Document doc = null;
	XVideo xvideo = null;

	String sDocId = request.getParameter("docId");
	int docId = (sDocId == null) ? -1 : Integer.parseInt(sDocId);
	if (docId >= 0) {
		//默认使用高品质视频，如果没有，则使用低品质的。
		xvideo = XVideo.findByDocIdAndQuality(docId, XVideo.QUALITY_HIGH);
		if(xvideo == null ) {
			xvideo = XVideo.findByDocIdAndQuality(docId, XVideo.QUALITY_LOW);
		}
		if (xvideo != null) {
			video = xvideo.getFileName();
			thumb = xvideo.getThumb();
		}
	} else {
		video = request.getParameter("v");
		thumb = video + ".jpg";
	}

%>
<body>

<div style="margin-top:5px">
<div id="r" style="margin-top:5px;width:auto">
	<div style="margin-top:5px;width:auto">
		<div id="myAlternativeContent">
			<a href="http://www.adobe.com/go/getflashplayer">
					Get Adobe Flash player
			</a>
		</div>
<%	
	String segments = HttpUtil.getResponseTextWithPost(videoServiceUrl + "/service/autoSegment", "fileName=" + video);
	if(segments != null) {
		segments = URLDecoder.decode(segments, "UTF-8");
	} else {
		segments = "";
	}
%>
		<script type="text/javascript">
			var flashvars = {servletUrl:"<%= CMyString.filterForJs(videoServiceUrl) %>/service/cutVideo",
					videoSource:"<%= CMyString.filterForJs(fmsUrl) %>/<%= CMyString.filterForJs(replaceParams(video)) %>",
			                segments:"<%= CMyString.filterForJs(segments)%>"};
			var params = {allowFullScreen:"true"};
			var attributes = {};
			attributes.id = "videoClip";
			swfobject.embedSWF("TRSVideoClip.swf", "myAlternativeContent", 
			                   "770", "520", "9.0.124", false, flashvars, params, attributes);
		</script>
	</div>

</div>
</div>

<script type="text/javascript">
function sendSegmentService(segments) {
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var oPostData = {
					ObjectId : <%=docId%>,
					Segments : segments
				};
	oHelper.Call(
					'wcm61_video',
					'clipSegments',
					oPostData,
					true,
					function(_transport,_json){
						var id = com.trs.util.JSON.value(_json,'result');
						if( id == 0 ) {
							alert("WCM已经成功请求TRSVIDEO进行标引与切割！");
						} else {
							alert("WCM请求TRSVIDEO进行标引与切割失败！");
						}
					},
					window.onFailure,
					window.onFailure
				);
}

window.onFailure = function(_transport,_json){
	alert("WCM视频标引与切割服务没有响应！");
}
</script>
</body>
<%! 
private static String replaceParams(String param){
return param.replaceAll("<[^>]+>", "");
	}
	%>
</html>