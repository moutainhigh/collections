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
<TITLE>TRS WCM V6 在线录制新视频</TITLE>
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
// uploadApp必须是 http://host.domain:port/app 的格式
//	String uploadApp = "http://218.247.176.156:8080/fma";
	String uploadApp = VSConfig.getUploadJavaAppUrl();
	String recordUrl = uploadApp + "/servlets/record";
	String fmsUrl = VSConfig.getUploadFMSAppUrl();
%>
<div id="con" style="margin-top:5px;width:auto">
	
	<div id="dvUpd" style="margin-top:5px;width:auto" align="center">

		<div id="flashcontent">您的浏览器Flash环境有些问题导致该内容无法显示!</div>
		<noscript>您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
		<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
		<script type="text/javascript">
			// <![CDATA[
		
		var so = new SWFObject("camera.swf", "fCam", "440", "330", "9.0.28", "#FF6600");
		so.useExpressInstall('expressinstall.swf');
		so.addVariable("localIP", "<%= IPUtil.ip2Hex(request.getRemoteAddr()) %>_");
		so.addVariable("fmsAppUrl", "<%= fmsUrl %>");
		so.addVariable("servletUrl", "<%= recordUrl %>");
		so.addParam("quality", "high");
		so.addParam("wmode", "transparent");
		so.addParam("scale", "exactfit");
		so.addParam("allowScriptAccess", "sameDomain");
		so.write("flashcontent");
			
			// ]]>
		</script>
	</div>
	<!-- 
	<div style="margin-top:5px;" align="center">
		<span>在线录制视频，需要您连接摄像头或DV.</span><br>
	</div>
	 -->
	<div id="dvMeta" style="margin-top:5px;" align="center">
	<form id="frmMeta" method="post">
		<input type="hidden" id="jsonMeta" name="jsonMeta" >
		<div style="width:100%;height:100%;overflow:auto;">
			<div style="padding:5px 0 0 5px">
				<div class="attr_row" align="center">
					<span>所属栏目:</span>
					<span id="DocChannel" style="color:blue;text-decoration:underline;cursor:pointer;"><%=sChannelName%></span>
				</div>
				<div class="attr_row" align="center">
					<span>标题:</span>
					<span style="width:150px;"><input type="text" name="DocTitle" id="DocTitle" value="<%=sOutlineTitle%>"></span>
				</div>
				<div class="attr_row" align="center">
					<span>副标题:</span>
					<span style="width:150px"><input type="text" name="SubDocTitle" id="SubDocTitle" value="<%=sDocSubTitle%>"></span>
				</div>
				<div class="attr_row" align="center">
					<span>关键词:</span>
					<span style="width:150px;"><input type="text" name="DOCKEYWORDS" id="DOCKEYWORDS" value="<%=sDocKeyWords%>"></span>
				</div>
				<div class="attr_row" style="height:100px;">
					<span>摘要:</span>
					<span><textarea name="DocAbstract" id="DocAbstract" cols="30" rows="5"><%=sDocAbstract%></textarea></span>
				</div>
				<div class="attr_row">
					<span>来源:</span>
					<span><%=showDocSource(loginUser, nCurrDocSource)%></span>
				</div>
				<div align="center">
					<input id="s" type="button" value="提交" disabled="disabled" onclick="javascript:saveNewRecord();">
				</div>
			</div>
		</div>
	
	<br>
	</form>
	</div>
</div>

<script type="text/javascript">
// 提供给Flash Record调用的函数(Begin)
function setMetaData(jsonStr) {
//	alert("video metadata = [\n" + jsonStr + "\n]");
	$("jsonMeta").value = jsonStr;
	$("s").disabled = false;
}
// 提供给Flash Record调用的函数(End)
</script>

</BODY>
</HTML>