<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@include file="../include/public_server.jsp"%>
<%
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	int nSiteId = currRequestHelper.getInt("SiteId", 0);

%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title id="tlDoc" WCMAnt:param="channel_import.html.title">import some channels</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/channel.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--定义了CrashBoard对象的实现-->
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<!--定义CrashBoard窗口相关样式-->
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />

<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'uploadFile',
		name : wcm.LANG.CHANNEL_TRUE||'确定'
	}],
	size : [450, 170]
};
</script>
<script>
	function uploadFile(){
		if($("check").checked){
	<%
			String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
			if(nChannelId > 0){
				sDomain += "/app/editor/editor/css/channel/" + nChannelId + ".css";
			}else{
				sDomain += "/app/editor/editor/css/site/" + nSiteId + ".css";
			}
			if(CMyFile.fileExists(sDomain)){
				CMyFile.deleteFile(sDomain);
			}
	%>
			FloatPanel.close();
			return false;
		}			
		var winUpload = $("frmUploadFile").contentWindow;
		var frm = winUpload.document.getElementById("frmPost");
		var sFileName = winUpload.document.getElementById("fileUpload").value;
		if(!winUpload.valid(sFileName)){
			return false;
		}
		sFileName = sFileName.substring(sFileName.lastIndexOf("\\")+1);
		document.getElementById("RealFileName").value = sFileName;

		frm.submit();
		return false;
	}

	function addFile(_sFilePath){
		if(_sFilePath==null){
			doAlert(wcm.LANG.CHANNEL_ALERT_2||"文件路径为空，上传失败！");
			return false;
		}
		//ProcessBar.start();
		var temp = "ChannelId=" + getParameter("channelId");
		<%
			if(nSiteId > 0){
		%>
			temp = "SiteId=" + getParameter("siteId");
		<%
			}
		%>
		var oPostData = temp + "&ImportFile=" + _sFilePath + "&RelateIds=" + $F("SelectedIds");
		var sURL = "channel_importEditorCss_dowith.jsp";
		//ProcessBar.start('导入样式文件');
		new Ajax.Request(sURL, {  
			method: 'get', 
			parameters:oPostData,
			onSuccess:function(transport){
			   notifyFPCallback(transport);
			   FloatPanel.close();
		   }
		}); 
	}
	function notifyOnUploadFileError(_sErrorMsg) {
		doAlert(_sErrorMsg);
	}
	function doAlert(_sAlertion){
		Ext.Msg.$alert(_sAlertion);
	}
	function toDefault(){
	}

	function showExample(){
		wcm.CrashBoarder.get("cb").show({
			title : wcm.LANG.channel_importEditor_1001 || "编辑器自定义样式文件示例",
			src : WCMConstants.WCM6_PATH+'channel/editorCssExample.html',
			width: '680px',
			height: '500px',
			maskable : true
		});
	}
	//是否显示"选择栏目"的链接
	function showLink(){
		var oChkbox = document.getElementById("syn");
		var oLink = document.getElementById("ChnlSlt");
		if(oChkbox.checked){
			if(oLink.style.display == "none"){
				oLink.style.display = "inline";
			}
		}else{
			oLink.style.display = "none";
		}
	}
	//选择需要同步到的栏目
	function selectChnl(event){
		Event.stop(window.event || event);
		var selectedChannelIds = document.getElementById("SelectedIds").value;
		var chnlId = <%=nChannelId%>;
		wcm.CrashBoarder.get("chnlSltCB").show({
			title : wcm.LANG.channel_importEditor_1002 ||  "选择要同步的栏目",
			src : WCMConstants.WCM6_PATH+'include/channel_select_forCB.html',
			width: '400px',
			height: '450px',
			maskable : true,			params:{isRadio:0,currChannelId:chnlId,ExcludeSelf:1,SELECTEDCHANNELIDS:selectedChannelIds,ExcludeTop:1,ExcludeVirtual:1,MultiSiteType:0,SiteTypes:'0'},
			callback : function(params){
				document.getElementById("SelectedIds").value = params[0];
            }

		});
	}
</SCRIPT>
<style type="text/css">
	#divBox{
		background: #ffffff;
		padding:1px;
		border: 1px solid lightblue;
	}
	#divContainer{
		background: #ffffff;
		padding:5px;
	}
	#divClue{
		color:red;
		font-size:14px;
	}
	body{
		background:whitesmoke;
	}
	.textStyle{
		padding:10px;
		background: #ffffff;
		font-size:14px;
		padding-top:5px;
	}
</style>
</HEAD>

<BODY>
<div id="divBox">
<div id="divContainer">
	<div id="divFileUpload">
		<IFRAME name="frmUploadFile" id="frmUploadFile" style="height:40; width:330" frameborder="0" vspace="0" src="../../file/file_upload.jsp?SelfControl=0&AllowExt=CSS" scrolling="NO" noresize></IFRAME>
		<INPUT TYPE="hidden" NAME="RealFileName" id="RealFileName">
	</div>
	<div id="divClue"  style="padding-left:10px;"><span WCMAnt:param="channel_importEditorCss.jsp.supportfile">仅支持css格式文件</span>
		<span style="padding-left:30px;"></span><a href="#" onclick="showExample();" WCMAnt:param="channel_importEditorCss.jsp.watchexample">查看示例</a>
	</div>
</div>
<div style="background:#ffffff">&nbsp;</div>
<div class="textStyle">
<input type="checkbox" name="check" id="check"/><label for="check" WCMAnt:param="channel_importEditorCss.jsp.restoredefaultstyle">还原默认样式</label>
<input type="hidden" name="SelectedIds" id="SelectedIds"/>
<%
	if(nChannelId > 0){
%>
<input type="checkbox" name="syn" id="syn" onclick="showLink()"><label for="syn" WCMAnt:param="channel_importEditorCss.jsp.synchannel">同步到栏目</label><span style="padding-left:20px;"></span><a id="ChnlSlt" href="#" onclick="selectChnl(event)" style="display:none;" WCMAnt:param="channel_importEditorCss.jsp.watchexample" WCMAnt:param="channel_importEditorCss.jsp.selectchannel">选择栏目</a>
<%
	}
%>
<br/>
</div>
</div>
</BODY>
</HTML>