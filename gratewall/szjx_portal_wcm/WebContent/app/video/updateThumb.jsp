<%@page import="com.trs.components.video.domain.XVideoMgr"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.presentation.locale.LocaleServer"%>
<%@ page import="com.trs.components.video.VideoDocUtil" %>
<%@ page import="com.trs.components.video.VideoInfo" %>
<%@ page import="com.trs.components.video.persistent.XVideo" %>

<%
	String fmsUrl = VSConfig.getUploadFMSAppUrl();
	String videoServiceUrl = VSConfig.getUploadJavaAppUrl();

	String video = null;
	String thumb = null;
	Document doc = null;
	XVideo xvideo = null;
    String playUrl = null;
    String thumbUrl = null;
	String sDocId = CMyString.filterForJs(request.getParameter("docId")) ;
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
			playUrl = XVideoMgr.getPlayUrl(xvideo);
			thumbUrl = XVideoMgr.getThumbUrl(xvideo);
		}
	} else {
		video = request.getParameter("v");
		thumb = video + ".jpg";
	}
%>

<%  
	//FJH@2007-11-19 未转换完的弹出提示，关闭窗口
	int convertFlag = xvideo.getConvertStatus();
	boolean converting = (convertFlag == 0);
	if(converting){
%>
<script type="text/javascript">
	alert("视频还在转换中，请稍候重试！");
	window.close();
</script>
<%
	return;
	} 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title  WCMAnt:param="updateThumb.jsp.capture">抓取视频缩略图</title>
<style>
div {
	font-size: 12px;
}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../js/com.trs.util/Common.js"></script>
<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
<script type="text/javascript">
function updateThumb(jsonStr) {
	var json = eval('(' + jsonStr + ')');
	new Ajax.Request("./saveThumb.jsp?docId=" + <%=docId%> + "&thumbName=" + json.curThumbName,
				{method: "get",
			     onFailure: function(transport) {
							alert("saveThumb failed");
							}
			});
	document.getElementById("imgThumb").src =
		"<%=VSConfig.getThumbsHomeUrl()%>/" + json.curThumbName + "?" + Math.random();
	if (window.opener) {
		window.opener.PageContext.loadList();
	}
}
</script>
</head>

<% 
	if (video == null) {
		out.print(CMyString.format(LocaleServer.getString("player.jsp.notExit","该视频不存在!qryStr=[{0}]"),new String[]{ CMyString.transDisplay(request.getQueryString())}));

		return;
	}

%>
<body>
<div id="con" style="margin-top:5px;width:auto">
	<div id="dvPlayer" style="margin-top:5px; width:510; float:left">

		<noscript WCMAnt:param="updateThumb.jsp.noScript">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
		<div id="flashcontent" WCMAnt:param="updateThumb.jsp.noDisplay">您的浏览器和Flash环境异常, 导致该内容无法显示!<a href="./flashversion.html"><font color="red">请尝试点击这里查看环境信息</font></a></div>
			<script type="text/javascript">
			var flashvars = {videoSource:"<%=CMyString.filterForJs(playUrl)%>",
			                 autoPlay:"true",
			                 lang:"true",
							 isAutoBandWidthDetection:"false",
							 logoAlpha:"0.5",
							 snagUrl:"<%= CMyString.filterForJs(videoServiceUrl) %>/service/makeThumb"
				};
			var params = {
				allowFullScreen:"true",
				quality:"high",
				scale:"exactfit",
				allowScriptAccess:"always"
			};
			var attributes = {};
			attributes.id = "updateThumb";
			swfobject.embedSWF("TRSVideoPlayer.swf", "flashcontent", 
			                   "500", "360", "9.0.124", false, flashvars, params, attributes);
		  </script>
		  
	</div>

	<div id="dvView" style="margin-top:5px; float:left;width:260px">
		<fieldset>
		<legend WCMAnt:param="updateThumb.jsp.capture1">视频缩略图抓取</legend>
		<div>
			<img id="imgThumb" src="<%=CMyString.filterForHTMLValue(thumbUrl)%>?<%=System.currentTimeMillis()%>" width="240" height="180" />
		</div>
		<div>&nbsp;</div>
		<div WCMAnt:param="updateThumb.jsp.clickbutton">播放时，点击左侧播放器中的"截图"按钮(<img src="./image/updateImg.png" />)可以将当时的画面作为该视频的缩略图.</div>
		<div>&nbsp;</div>
		<div  WCMAnt:param="updateThumb.jsp.notice"><font color="red">注意: 截图操作是在服务器端进行，因此有时会稍有延迟，这是正常现象。</font></div>
		<div style="height:50">&nbsp;</div>
		</fieldset>
	</div>
</div>
	
  <div style="margin: 15px 300px 100px 300px;"> 
    <input type="button" value=" 关 闭 " WCMAnt:paramattr="value:updateThumb.jsp.close" onclick="javascript:window.close();">
  </div>
		
</body>
</html>