<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.video.persistent.XVideo" %>
<%@ page import="com.trs.components.video.util.HttpUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.trs.dev4.jdk16.servlet24.RequestUtil" %>
<%@ include file="../../include/public_server.jsp" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>样本库管理</title>
<script src="../../console/js/opensource/prototype.js" ></script>
<script src="../../app/video/js/opensource/FABridge.js" ></script>
<script src="../../app/video/js/opensource/swfobject.js"></script>
<script src="../../console/js/com.trs.util/Common.js"></script>
<style>
body {
	font-size: 12px;
}
</style>
</head>
<%
	String sDocId = request.getParameter("docId");
%>
<body>
<div style="margin-top:5px" align="center">
		<div id="myConsle">
			<a href="http://www.adobe.com/go/getflashplayer">
				Get Adobe Flash player
			</a>
		</div>
	
		<script type="text/javascript">
			var flashvars = {
					<%
					String url=VSConfig.getUploadJavaAppUrl();
					if(url.endsWith("mas")){
					%>
			 		ip : "<%=CMyString.filterForJs(url)%>/clipLab"	
				 	<%}else{%>
				 	ip : "<%=CMyString.filterForJs(url)%>"	
					 <%}%>			
				};
			var params = {allowFullScreen:"true"};
			var attributes = {};
			attributes.id = "SimplePlayer";
			<%if(url.endsWith("mas")){
				%>
			swfobject.embedSWF("TRSSamplesManage.swf", "myConsle", 
			                   "1024", "600", "9.0.124", false, flashvars, params);
			<%}else{%>
			swfobject.embedSWF("TRSSamplesManage_TRSVIDEO.swf", "myConsle", 
	                   "1024", "600", "9.0.124", false, flashvars, params);
			 <%}%>	
		</script>
</div>
<div align="center"> 
<br>
</div>
<script type="text/javascript">
function backToList() {
	if(window.opener){
		window.opener.$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
	}
	window.opener = null;
	window.close();
}
</script>
</body>
</html>