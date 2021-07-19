<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.VideoDocUtil"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="java.util.List"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.components.video.domain.XVideoMgr"%>
<%@ include file="../../include/public_server.jsp" %>
<%
	String fmsUrl = VSConfig.getUploadFMSAppUrl();

	String videoStrings = null;
	Document doc = null;
	XVideo xVideo = null;
	VideoDocUtil vUtil = new VideoDocUtil();

	String sDocId = request.getParameter("docId");
	int docId = (sDocId == null) ? -1 : Integer.parseInt(sDocId);
	if (docId >= 0) {
		List list = XVideo.findXVideosByDocId(docId);
		xVideo = (XVideo)list.get(0);
		if (xVideo != null) {
			videoStrings = xVideo.getFileName();
		}
	} else {
		videoStrings = request.getParameter("v");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="player.jsp.playvideo">播放视频</title>
<script type="text/javascript" src="../js/opensource/prototype.js"></script>
<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>

</head>

<body>
<script src="../js/com.trs.util/Common.js"></script>
<% 
	boolean isHighQualityIp = VSConfig.isHighQualityIp(request.getRemoteAddr());

	if (videoStrings == null) {
		out.print(CMyString.format(LocaleServer.getString("player.jsp.notExit","该视频不存在!qryStr=[{0}]"),new String[]{ CMyString.transDisplay(request.getQueryString())}));
		return;
	}
	
	//多码流怎么办?
	String videos[] = videoStrings.split(";");
	String video = null;
	String videoHq = null;
	String videoPt = null;
	if (videos.length == 1 ) {
		video = videos[0];
	} else {
		for(int i = 0 ; i < videos.length; i++) {
			if(videos[i].indexOf("hq") == -1) {
				videoPt = videos[i];
			} else {
				videoHq = videos[i];
			}
		}
		if (isHighQualityIp) {
			video = videoHq;
		} else {
			video = videoPt;
		}
	}
	xVideo = xVideo.findByFileName(videos[0]);
	String playUrl = XVideoMgr.getPlayUrl(xVideo);
	//if(xVideo.getCreateType()==50){
	//	fmsUrl = VSConfig.getVideoRootUrl();
	//}
%>
<div id="dvUpd" style="margin-top:5px;width:auto" align="center">

	<div id="flashcontent" WCMAnt:param="player.jsp.notDisplay">您的浏览器和Flash环境异常, 导致该内容无法显示!<a href="./flashversion.html"><font color="red">请尝试点击这里观看或报告环境信息</font></a></div>
	<noscript WCMAnt:param="player.jsp.noJavascript">您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>
	<script type="text/javascript">
			function changePlayURL(videoUrl) {
				var flexApp = FABridge.flash.root();
				flexApp.changeSource("<%= CMyString.filterForJs(fmsUrl) %>/" + videoUrl, "", "");
			}
	</script>
	<script type="text/javascript" src="./js/opensource/FABridge.js" ></script>
	<script type="text/javascript">
			var flashvars = {videoSource:"<%=CMyString.filterForJs(playUrl) %>",
			                 autoPlay:"true",
			                 lang:"cn.xml",
							 isAutoBandWidthDetection:"false",
							 logoAlpha:"0.5"
				};
			var params = {
				allowFullScreen:"true",
				quality:"high",
				scale:"exactfit",
				allowScriptAccess:"always"
			};
			var attributes = {};
			attributes.id = "SimplePlayer";
			swfobject.embedSWF("TRSVideoPlayer.swf", "flashcontent", 
			                   "480", "360", "9.0.124", false, flashvars, params, attributes);
	</script>
</div>
<div align="center">
<%
if (videos.length > 1) {
%>
	<input type="radio" name="playQuality" value="高清效果" WCMAnt:paramattr="value:player.jsp.hq" onclick="changePlayURL('<%=CMyString.filterForHTMLValue(videoHq) %>')" <%if(isHighQualityIp){%>checked<%}%>> <span WCMAnt:param="player.jsp.hq" >高清效果</span>
	<input type="radio" name="playQuality" value="普通效果" WCMAnt:paramattr="value:player.jsp.lq" onclick="changePlayURL('<%=CMyString.filterForHTMLValue(videoPt) %>')" <%if(!isHighQualityIp){%>checked<%}%>> <span WCMAnt:param="player.jsp.lq" >普通效果</span>
<%
}
%>
</div>
</body>
</html>