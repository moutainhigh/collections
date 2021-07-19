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
<!-- 
<script type="text/javascript" src="<%= request.getContextPath() %>/js/opensource/prototype.js" ></script>
 -->
<script type="text/javascript" src="./js/opensource/FABridge.js" ></script>
<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
<script src="../video/js/com.trs.util/Common.js"></script>
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
<iframe style="width:800px;height:550px" frameborder=false src="cutVideoCore.jsp?docId=<%=CMyString.filterForHTMLValue(sDocId) %>"></iframe>
</div>
<div align="center"> 
<br>
<span id="info"><font color="red">如果您是第一次对此视频进行标引，TRSVIDEO的智能标引程序大约需要运行1-2分钟，请耐心等待</font></span>
<br><input type="button" value=" 关 闭 " onclick="javascript:backToList();">
</div>
<script type="text/javascript">
function waitLoading() {
	oSpan = document.getElementById("info");
	if(oSpan.readyState == "complete") {
		document.getElementById("info").innerHTML = "";
	}
}
function backToList() {
	if(window.opener){
		window.opener.PageContext.loadList();
	}
	window.opener = null;
	window.close();
}
</script>
</body>
</html>