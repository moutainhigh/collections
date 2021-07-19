<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.dl.util.IPUtil" %>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	//权限校验
	int nSiteId =  currRequestHelper.getInt("SiteId", 0);
	WebSite oWebSite = WebSite.findById(nSiteId);
	boolean bHasRight = AuthServer.hasRight(loginUser,oWebSite,WCMRightTypes.DOC_PUBLISH);
	if(!bHasRight){
		throw new WCMException(3000000,LocaleServer.getString("live_add.jsp.noRight","您没有权限进行新建直播视频！"));
		}
	//
	out.clear();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title  WCMAnt:param="live_add.jsp.trsNewLive">TRS WCM 新建视频直播</title>
<style type="text/css">		
	.input_text{
		/*width:200px;*/
		height:24px;
		border:1px solid green;
		margin:0 0px;
	}
	label{
		width:150px;
		font-weight:bold;
		padding-right:5px;
	}
	.intro_txt{
		font-weight:bold;
		padding-right:5px;
	}
	body {
		font-size: 14px;
	}
	div {
		margin: 5 0 5 0;
	}
	#bApply {
		/*background-color: #cc0000;*/
		height:24px;
		width: 120px;
		margin:0 0 0 5px;
	}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script type="text/javascript" src="./js/opensource/FABridge.js" ></script>
<script type="text/javascript">
var zLiving = false;
window.onbeforeunload = function() {
    if (zLiving) {
	   event.returnValue = "现在正在进行直播, 关闭页面会导致该直播停止! 请取消本次关闭页面的操作, 等停止直播后再关闭页面!";
    }
}

// 提供给Flash调用的函数(Begin)
function showPlayLink(flvName) {
//	alert("flvName=" + flvName);
	$("sLiveName").innerHTML = flvName;
}

function liveStarted() {
	zLiving = true;
	$("exitme").disabled = true;
}

function liveStopped() {
	zLiving = false;
	$("exitme").disabled = false;
}
// 提供给Flash调用的函数(End)
</script>
</head>

<body>

<%
	String fmsUrl = VSConfig.getRecordFMSAppUrl();
%>
<div id="con" style="margin-top:5px;width:auto">
	
	<div id="dvLive" style="margin-top:5px;width:auto" align="center">

		<noscript  WCMAnt:param="live_add.jsp.noScript">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
		<div id="flashcontent"  WCMAnt:param="live_add.jsp.notDisplay">您的浏览器和Flash环境异常, 导致该内容无法显示!<a href="./flashversion.html"><font color="red">请尝试点击这里查看环境信息</font></a></div>
		<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
		<script type="text/javascript">
			// <![CDATA[
			var flashvars = {localIP:"<%= IPUtil.ip2Hex(request.getRemoteAddr()) %>_",
					fmsAppUrl:"<%= fmsUrl %>"
				};
			var params = {
					quality:"high",
					wmode:"transparent",
					scale:"exactfit",
				allowScriptAccess:"always"
			};
			var attributes = {};
			attributes.id = "fCam";
			swfobject.embedSWF("FlexMediaEncoder.swf", "flashcontent", 
			                   "950", "650", "9.0.124", false, flashvars, params, attributes);
			
			// ]]>
		</script>

	</div>
	<div id="dvMeta" style="margin-top:5px;" align="center">
		<div><span style="color: red" WCMAnt:param="live_add.jsp.notice">注意：视频直播过程中，不要关闭本窗口</span></div>
	</div>
<fieldset>
<legend WCMAnt:param="live_add.jsp.WCMparam">WCM视频选件直播参数</legend>
<div><label WCMAnt:param="live_add.jsp.MASaddr">MAS应用地址:</label><span id="sFmsAppUrl" style="color: blue"><%= fmsUrl %></span></div>
<div><label WCMAnt:param="live_add.jsp.LiveName">直播名:</label><a id="A_vsUrlOutIp"><span id="sLiveName" style="color: blue"></span></a></div>
</fieldset>

</div>

</body>
</html>