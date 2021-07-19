<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%
	String video = request.getParameter("v");
	String fmsUrl = VSConfig.getUploadFMSAppUrl();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Play Live</title>
<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
</head>

<body>
<% 
	if (video != null) {
%>
	<div id="dvUpd" style="margin-top:5px;width:auto" align="center">

		<div id="flashcontent">Dummy Text</div>

		<script type="text/javascript">
		// <![CDATA[
		
		var so = new SWFObject("livePlayer.swf", "fCam", "480", "360", "9", "#FF6600");
		so.useExpressInstall('expressinstall.swf');

		so.addVariable("fmsAppUrl", '<%= CMyString.filterForJs(fmsUrl) %>');
		so.addVariable("liveName", '<%= CMyString.filterForJs(replaceParams(video)) %>');
		
		so.addParam("quality", "high");
		so.addParam("wmode", "transparent");
		so.addParam("scale", "exactfit");
		so.addParam("allowScriptAccess", "sameDomain");
		so.write("flashcontent");
		// ]]>			
		</script>
	</div>
<% 
	} else {
		out.print("该视频不存在! qryStr=[" + CMyString.transDisplay(request.getQueryString()) + "]");
	}
%>
</body>
<%! 
private static String replaceParams(String param){
return param.replaceAll("<[^>]+>", "");
	}
	%>
</html>