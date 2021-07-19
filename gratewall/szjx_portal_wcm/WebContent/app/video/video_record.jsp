<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.dl.util.IPUtil" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@include file="video_edit_import.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>
<%
	//初始化（获取数据）
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = null;
	Channel docChannel = null;

	//获得currChannel对象
	if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,  CMyString.format(LocaleServer.getString("video_record.jsp.reqFail","获取栏目ID为[{0}]的栏目失败！"),new int[]{nChannelId}));
		}
		if(currChannel.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,currChannel+LocaleServer.getString("video_record.jsp.refreshLanmu","已被删除!请刷新您的栏目树."));
		}
	}
	//获得docChannel对象
	docChannel = currChannel;
	if(currChannel==null&&docChannel!=null){
		currChannel = docChannel;
	}
	if(currChannel==null&&docChannel==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("video_record.jsp.undefnNewLm","未指定新建文档所属栏目！"));
	}
	//获得当前文档所属栏目名称
	String sChannelName = "";//所属栏目名称
	ConfigServer oConfigServer = ConfigServer.getServer();
	sChannelName = (docChannel!=null)?CMyString.filterForHTMLValue(docChannel.getDesc()):"";
%>
<%
	out.clear();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="video_record.jsp.title">TRS WCM 在线录制新视频</TITLE>

<script src="../js/com.trs.util/Common.js"></script>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script type="text/javascript" src="./js/opensource/FABridge.js" ></script>
<script src="./video_record.js"></script>
<style type="text/css">
body {
	font-size:12px;
	padding:0;
	margin:0;
	background :transparent;
	border:0;
	overflow:hidden;
}
.firstCol {
	vertical-align:top;
	width:55px;
}
</style>
</head>
<body>

<%
	String uploadApp = VSConfig.getUploadJavaAppUrl();
	String recordUrl = uploadApp + "/service/record";
	String fmsUrl = VSConfig.getUploadFMSAppUrl();
%>
<div id="con" style="margin:10px 10px;width:auto">
	<div style="margin:0 10px;">
		<span WCMAnt:param="video_record.jsp.videoTitle">视频标题：</span>
		<span><input type="text" name="DocTitle" id="DocTitle" style="width:480"><font color="red">&nbsp;*</font></span>
	</div>
	<div style="margin-top:5px; width:5; height:100%; float:left"></div>
	<div id="dvUpd" style="margin-top:5px;width:430; float:left">
	<fieldset>
		<legend WCMAnt:param="video_record.jsp.videoRecord">视频录制</legend>
		<div id="flashcontent" WCMAnt:param="video_record.jsp.notDisplay">您的浏览器Flash环境有些问题导致该内容无法显示!</div>
		<noscript WCMAnt:param="video_record.jsp.noscript">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
		<div id="flashcontent" WCMAnt:param="video_record.jsp.noFlash">您的浏览器和Flash环境异常, 导致该内容无法显示!<a href="./flashversion.html"><font color="red">请尝试点击这里查看环境信息</font></a></div>
		<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>
						<noscript WCMAnt:param="video_record.jsp.noscirpt1">您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>
						<script type="text/javascript">
								var flashvars = {localIP:"<%= IPUtil.ip2Hex(request.getRemoteAddr()) %>_ ",
												fmsAppUrl:"<%= fmsUrl %>",
												servletUrl:"<%= recordUrl %>"
									};
								var params = {
									quality:"high",
									wmode:"transparent",
									scale:"exactfit",
									allowScriptAccess:"always"
								};
								var attributes = {};
								attributes.id = "fCam";
								swfobject.embedSWF("camera.swf", "flashcontent", 
												   "440", "360", "9.0.124", false, flashvars, params, attributes);
						</script>
	</fieldset>
	</div>
	<!-- 
	<div style="margin-top:5px;" align="center">
		<span>在线录制视频，需要您连接摄像头或DV.</span>
	</div>
	 -->
	<div style="margin-top:5px; width:5; height:100%; float:left"></div>
	<div id="dvMeta" style="margin-top:5px;width:320;float:left">
		<input type="hidden" id="jsonMeta" name="jsonMeta" >
		<fieldset>
		<legend WCMAnt:param="video_record.jsp.videoAttr">视频基本属性</legend>
		<div style="width:100%;height:360;overflow:auto;">
			<div style="padding:5px 0 0 5px">
				<div>
					<span class="firstCol" WCMAnt:param="video_record.jsp.lanmu">所属栏目:</span>
					<span id="DocChannel" style="color:green;font-weight:bold; height:20"><%=sChannelName%></span>
				</div>
				<div>
					<span class="firstCol" WCMAnt:param="video_record.jsp.startpageTitle">首页标题:</span>
					<span style="width:150px"><input type="text" name="DOCPEOPLE" id="DOCPEOPLE" style="width:240"></span>
				</div>
				<div>
					<span class="firstCol" WCMAnt:param="video_record.jsp.subTitle">副标题:</span>
					<span style="width:150px"><input type="text" name="SubDocTitle" id="SubDocTitle" style="width:240"></span>
				</div>
				<div>
					<span class="firstCol" WCMAnt:param="video_record.jsp.keyword">关键词:</span>
					<span style="width:150px;"><input type="text" name="DOCKEYWORDS" id="DOCKEYWORDS" style="width:240"></span>
				</div>
				<div>
					<span class="firstCol" WCMAnt:param="video_record.jsp.abstrat">摘要:</span>
					<span><textarea name="DocAbstract" id="DocAbstract" cols="30" rows="5" style="width:240" ></textarea></span>
				</div>
			</div>
		</div>
	</fieldset>
	</div>
	<div style="padding:30px 360px;height:100px">
		<span><input id="s" type="button" value="保存并退出" WCMAnt:paramattr="value:video_record.jsp.saveadnquit" disabled="disabled" onclick="javascript:saveNewRecord();"></span>
	</div>
</div>


<script type="text/javascript">
// 提供给Flash Record调用的函数(Begin)
var DocChannelId = '<%=(docChannel!=null)?docChannel.getId():0%>';

function setMetaData(jsonStr) {
//	alert("video metadata = [\n" + jsonStr + "\n]");
	$("jsonMeta").value = jsonStr;
	$("s").disabled = false;
}
// 提供给Flash Record调用的函数(End)
</script>

</BODY>
</HTML>