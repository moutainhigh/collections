<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.video.WCMConfigServerBasedManager" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.dl.util.ConfigFileModifier" %>
<%@ page import="com.trs.components.video.util.StringUtil"%>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@ include file="../../include/public_server.jsp" %>
<%
	String trsVsURL = VSConfig.getUploadJavaAppIntranetUrl();
	String trsVsURLOutIp = VSConfig.getUploadJavaAppUrl();
	String aHref = (trsVsURLOutIp == null) ? "#" : trsVsURLOutIp;
	
	String vodURL = VSConfig.getUploadFMSAppUrl();
	String recordURL = VSConfig.getRecordFMSAppUrl();
	String thumbsHomeURL = VSConfig.getThumbsHomeUrl();
	String masVersion = VSConfig.getTRSMASVersion();
	String appKey = VSConfig.getAppKey();
	String maxPostSize = VSConfig.getMaxPostSize();
	String extUpload = VSConfig.getExtUpload();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="video_config.jsp.title">WCM视频库设置</title>
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
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
</head>

<script type="text/javascript">
function trim(_string) {
	if (!_string) {
		return _string;
	}
	return _string.replace(/^\s*/, "").replace(/\s*$/, "");
}

function validateURLFormat() {
	var trimedVsUrl = trim($("vsUrl").value);
	if (trimedVsUrl == "") {
		alert("请输入TRS视频服务URL!");
		$('vsUrl').focus();
		$('vsUrl').style.border = "1px solid red";
		return;	
	}
//	var regExp = /^(http|https):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?(\/)?/i;
	var ptnRootDomain = /^(http:\/\/|https:\/\/)./;
	if(!ptnRootDomain.test(trimedVsUrl.toLowerCase())) {
//		alert("所输入的内容: [" + trimedVsUrl + "]不是URL! 请重新输入.");
		$("txtResult").style.color = "red";
		$("txtResult").innerText = "不合法的url!正确格式为: http(s)://[站点](:[端口])/(路径)";
		$('vsUrl').focus();
		$('vsUrl').style.border = "1px solid red";
		return;
	}
	$("txtResult").innerText = "";
	$('vsUrl').style.border = "1px solid green";
	$("bApply").disabled = false;
	//$("bApply").focus();
}

function testTRSVideoServiceURL() {
	var trimedVsUrl = trim($("vsUrl").value);
	var appKey = trim($("appKey").value);
    $("txtResult").style.color = "green";
	$("txtResult").innerText = "正在获取其他配置, 请稍候...";
	new Ajax.Request("./testTRSVideo.jsp?videoServiceURL=" + trimedVsUrl+"&appKey="+appKey, {
		method: 'post',
		onSuccess: function(transport) { 		 		    
			eval("var obj="+transport.responseText);
			try {
				$('vsUrlOutIp').innerText = obj["fma.publicSysUrl"];
				$('A_vsUrlOutIp').href = obj["fma.publicSysUrl"];
				$('vodUrl').innerText =obj["fma.uploadFMSUrl"];
				$('liveUrl').innerText =obj["fma.recordFMSUrl"];
				$('thumbsUrl').innerText =obj["fma.publicThumbHomeUrl"];
				$('trsmasVersion').innerText =obj["trsmas.version"];
				$('extUpload').innerText =obj["extUpload"];
				$('maxSize').innerText =obj["maxPostSize"];
				var masSize = ($('maxSize').innerText)/1024/1024;
				$('maxSizeMB').innerText = masSize+'';
				if(obj["fma.transcodingKbps"] != null) {
					if( obj["fma.transcodingKbps"] != "-1" ) {
						$('bitrate').innerText =obj["fma.transcodingKbps"] + "Kbps";
					}
					if( obj["fma.transcoding.hq"] != "-1" && obj["fma.transcoding.hq"] != null ) {
						$('bitrate').innerText =$('bitrate').innerText + "," + obj["fma.transcoding.hq"] + "Kbps";
					}
				} else {
					$('bitrate').innerText ="400Kbps";
				}				
				applyConfig();
			} catch (e) {
				alert(String.format("从TRS视频服务URL不能获取到其他配置! 错误: {0}",e));
				$("txtResult").style.color = "red";
				$("txtResult").innerText = "获取其他配置失败!";
	
				$('vsUrl').focus();
				$('vsUrl').style.border = "1px solid red";
			}
	   },
		on500 : onError,
	   onFailure: onError
	});
}

function onError(){
			alert("从TRS视频服务URL不能获取到其他配置! 可能的原因:\n1)所输入的TRS视频服务URL有误;\n2)TRS视频服务没有启动或启动错误;\n请检查无误后重试.");
			$("txtResult").style.color = "red";
			$("txtResult").innerText = "获取其他配置失败!";
			$('vsUrl').focus();
			$('vsUrl').style.border = "1px solid red";
}

function applyConfig() {
	new Ajax.Request("./applyWCMConfig.jsp", {
		method: 'get',
		parameters: "vsUrl=" + $('vsUrl').value +"&appKey=" +  trim($("appKey").value) + "&vodUrl=" + $('vodUrl').innerText + "&liveUrl=" + $('liveUrl').innerText + "&thumbsUrl=" + $('thumbsUrl').innerText +"&vsUrlOutIp=" + $('vsUrlOutIp').innerText +"&bitrate=" + $('bitrate').innerText + "&trsmasVersion=" + $('trsmasVersion').innerText + "&extUpload=" + $('extUpload').innerText + "&maxSize=" + $('maxSize').innerText,
		onSuccess: function(transport) {
			$("txtResult").style.color = "green";
			$("txtResult").innerText = "设置已成功完成!";
		},
		onFailure: function(transport) {
			$("txtResult").style.color = "red";
			$("txtResult").innerText = String.format("设置失败! 响应码: {0}",transport.status);
		}
	});
}
function applyFCKPlayer(){
	
	var result = confirm("此操作会设置默认文档插视频中的播放器参数，且只作用于本次设置后的新文档，确认设置播放器参数?");
	if(result == false)
		return;
	else
	
	
	new Ajax.Request("./applyFCKPlayer.jsp", {
		method: 'get',
		parameters: "playerWidth=" + $('playerWidth').value + "&playerHeight=" + $('playerHeight').value + "&logoCss=" +  $('logoCss').value + "&autoPlay="+$('autoPlay').value,
		onSuccess: function(transport) {
			$("txtFckResult").style.color = "green";
			$("txtFckResult").innerText = "播放器设置成功!";
		},
		onFailure: function(transport) {
			$("txtFckResult").style.color = "red";
			$("txtFckResult").innerText = String.format("设置失败! 响应码: {0}",transport.status);
		}
	});
	}
	
</script>

<body>
<div style="margin: 10px 10px 10px 10px">

<!-- fjh@2008-11-5 TRSVIDEO intranet IP 
 -->
 <div><label style="width:300px"  WCMAnt:paramattr="title:video.config1.title" title="形如http://TRSVIDEO_HOST:PORT/mas或fma" WCMAnt:param="config.jsp.url">请输入TRS视频服务URL（最好用内网IP）:</label></div>
<div>
<span>
<input type="text" class="input_text" name="vsUrl" id="vsUrl" length="50" onchange="javascript:validateURLFormat();" style="width:240px" value="<%= (trsVsURL == null) ? "" : trsVsURL %>"  title="形如http://TRSVIDEO_HOST:PORT/mas或fma" WCMAnt:paramattr="title:video.config1.title">
</span>
 <div><label style="width:300px" WCMAnt:param="config.jsp.inputTag">请输入应用标识(数字或英文)，用以和MAS进行连接:</label></div>
<div>
<span>
<input type="text" onkeyup="value=value.replace(/[\W]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" class="input_text" name="appKey" id="appKey" length="50"  style="width:240px" value="<%= (appKey == null) ? "" : appKey %>">
</span>
<span><input id="bApply" type="button"  value="<%=LocaleServer.getString("key","设置")%>"  WCMAnt:paramattr="value:congfig.jsp.title.setup"  onclick="javascript:testTRSVideoServiceURL();" title="向TRS视频服务获取其余配置, 并保存和应用所有配置" WCMAnt:paramattr="title:config.jsp.savaAllSetup"></span></div>
<div><span id="txtResult" style="width:400px;"></span></div>
<fieldset>
<legend WCMAnt:param="config.jsp.info">TRS MAS服务配置信息</legend>
<span id="maxSize" style="display:none"></span>
<div><label WCMAnt:param="config.jsp.version">TRS MAS版本:</label><span id="trsmasVersion"><%=(masVersion == null)? "<font color='red'>"+LocaleServer.getString("nowebinfo","尚未配置!")+"</font>" : masVersion%></span></div>
<div><label WCMAnt:param="config.jsp.serviceUrl">外网服务URL:</label><a id="A_vsUrlOutIp"><span id="vsUrlOutIp"><%= (trsVsURLOutIp == null) ? "<font color='red'>"+LocaleServer.getString("nowebinfo","尚未配置!")+"</font>" : trsVsURLOutIp %></span></a></div>
<div><label WCMAnt:param="config.jsp.dianbo">视频点播URL:</label><span id="vodUrl"><%= (vodURL == null) ? "<font color='red'>"+LocaleServer.getString("nowebinfo","尚未配置!")+"</font>" : vodURL %></span></div>
<div><label WCMAnt:param="config.jsp.record">视频录制URL:</label><span id="liveUrl"><%= (recordURL == null) ? "<font color='red'>"+LocaleServer.getString("nowebinfo","尚未配置!")+"</font>" : recordURL %></span></div>
<div><label WCMAnt:param="config.jsp.suoluvtu">视频缩略图URL:</label><span id="thumbsUrl"><%= (thumbsHomeURL == null) ? "<font color='red'>"+LocaleServer.getString("nowebinfo","尚未配置!")+"</font>" : thumbsHomeURL %></span></div>
<div><label WCMAnt:param="config.jsp.mubiaomaliu">转码目标码流:</label><span id="bitrate"><%=(VSConfig.getBitrates() == null) ? "<font color='red'>"+LocaleServer.getString("nowebinfo","尚未配置!")+"</font>" : VSConfig.getBitrates() %></span></div>
</fieldset>
<fieldset>
<legend WCMAnt:param="config.jsp.configinfo">视频选件配置信息</legend>
<div><label WCMAnt:param="config.jsp.record1">版本信息:</label><span><%=VSConfig.getVersion() %></span></div>
<div><label WCMAnt:param="config.jsp.extUpload">允许上传的后缀:</label><span id="extUpload"><%=VSConfig.getExtUpload() == null?"":VSConfig.getExtUpload() %></span></div>
<div><label WCMAnt:param="config.jsp.maxSize">上传限制(MB):</label><span id = "maxSizeMB"><%=VSConfig.getMaxPostSizeMB() == null?"":VSConfig.getMaxPostSizeMB() %></span></div>
<div><label WCMAnt:param="config.jsp.index">播放器目录URL:</label><span><%=(VSConfig.getFLVPlayerBase() == null ? ("/wcm/app/video/"):(VSConfig.getFLVPlayerBase())) %></span></div>
</fieldset>
<fieldset>
<legend WCMAnt:param="config.jsp.playerParams">设置文档插视频播放器的默认参数(注：调整此设置只作用于此后新插入视频库视频的文档)</legend>
<input type="button" WCMAnt:paramattr="value:config.jsp.button.setupPlayer" value="设置播放器" onclick="javascript:applyFCKPlayer();"/>
<div><span id="txtFckResult" style="width:400px;"></span></div>
<br><label WCMAnt:param="config.jsp.playerHeight">播放器宽度：</label><input id="playerWidth" type="text" value="<%=StringUtil.avoidEmpty(VSConfig.getFckPlayerWidth(),"480")%>"></br>
<br><label WCMAnt:param="config.jsp.playerWidth">播放器高度：</label><input id="playerHeight" type="text" value="<%=StringUtil.avoidEmpty(VSConfig.getFckPlayerHeight(),"360")%>"></br>
<br><label WCMAnt:param="config.jsp.playerLOGO">播放器LOGO：</label><input id="logoCss" type="text" value="<%=StringUtil.avoidEmpty(VSConfig.getFckPlayerLOGO(),"0")%>"><img src="./image/answer.gif" WCMAnt:paramattr="title:config.jsp.title.logo" title="Logo的透明度, 0-1之间的小数, 小数点后1位, 如0.4. 值越大越不透明" WCMAnt:paramattr="alt:config.jsp.alt.logo" alt="Logo的透明度"/></br>
<br><label WCMAnt:param="config.jsp.playerAutoPlay">是否自动播放：</label>
<select id="autoPlay" style="width:80px">
                                <option value="false" <%="false".equals(VSConfig.getFckPlayerAutoPlay())?"selected='selected'":"" %> WCMAnt:param="config.jsp.autoPlay.no">否</option>
                                <option value="true" <%="true".equals(VSConfig.getFckPlayerAutoPlay())?"selected='selected'":"" %> WCMAnt:param="config.jsp.autoPlay.yes">是</option>
                                </select>
                                <img src="./image/answer.gif" WCMAnt:paramattr="title:config.jsp.title.logoTips" title="若不自动播放，则会显示视频缩略图" WCMAnt:paramattr="alt:config.jsp.alt.logoTips" alt="是否自动播放"/>

</br>


</fieldset>
<%
	String downloadHomeUrl = null;
	try {
		downloadHomeUrl = VSConfig.getDownloadHomeUrl();
	} catch (Exception e) {
	}
	
	if (downloadHomeUrl != null) {
%>
<div><label WCMAnt:param="config.jsp.download">视频下载URL:</label><span><%= downloadHomeUrl %></span></div>
<%
	}
%>
</div>
</body>
</html>