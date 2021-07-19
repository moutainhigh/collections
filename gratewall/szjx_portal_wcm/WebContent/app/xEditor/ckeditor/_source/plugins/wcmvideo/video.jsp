<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../../../include/error.jsp"%>
<%@page import="com.trs.dev4.jdk16.servlet24.RequestUtil"%>
<%@page import="com.trs.dev4.jdk16.utils.FileUtil"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.video.content.VideoDoc"%>
<%@ page import="com.trs.components.video.content.VideoDocs"%>
<%@ page import="com.trs.components.video.VSConfig"%>
<%@ page import="com.trs.components.video.VideoDocUtil"%>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="com.trs.components.video.domain.XVideoMgr"%>
<%@ page import="com.trs.components.video.domain.WCMContextHelper"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %><!-- 点击打开视频播放界面引入的list_base.jsp页面的类 -->
<%@include file="../../../../../include/public_server.jsp"%>
<%@include file="../../../../../include/convertor_helper.jsp"%>


<%
	String strCurrPage = request.getParameter("passInputPageIndex");
	String strParametersValue = request.getParameter("parametersValue");
	String strParametersKey = request.getParameter("parametersKey");
		
	
	if((strCurrPage==null)||(strCurrPage=="")) {
		strCurrPage = "1";
	}
	if((strParametersValue==null)||(strParametersValue=="")) {
		strParametersValue = "5";
	}
	if((strParametersKey==null)||(strParametersKey=="")) {
		strParametersKey = "SiteIds";
	}

	int nCurrPage = Integer.parseInt(strCurrPage);
	int nParametersValue = Integer.parseInt(strParametersValue);
	//对象定义

	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	//指定要调用的服务/方法名
	String sServiceId = "wcm61_video", sMethodName = "query";
	//指定参数
	HashMap parameters = new HashMap();
	parameters.put(strParametersKey, nParametersValue);
	parameters.put("PageSize", "8");
	parameters.put("CurrPage", nCurrPage);

	//执行服务请求
	VideoDocs videoDocs = (VideoDocs) processor.excute(sServiceId,sMethodName, parameters);
	int nChannelId = currRequestHelper.getInt("Channelid",0);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Photo_Lib</title>
	<!-- 引入图片库界面样式 -->
	<link href="video_lib.css" rel="stylesheet" type="text/css" />
	<!-- 引入树 组件 Start -->
	<link href="../../../../../../app/js/source/wcmlib/com.trs.tree/resource/TreeNav.css" rel="stylesheet" type="text/css" />
	<link href="../../../../../../app/nav_tree/nav_tree.css" rel="stylesheet" type="text/css" />

	<script src="../../../../../../app/js/runtime/myext-debug.js"></script>
	<script src="../../../../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../../../../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../../../../js/source/wcmlib/com.trs.tree/TreeNav.js"></script>
	<script src="../../../../../js/data/locale/nav_tree.js"></script>
	<script src="../../../../../js/data/locale/tree.js"></script>
	<script src="../../../../../js/easyversion/ajax.js"></script>
	<!-- 引入树 组件 End -->
	<!-- 引入分页 组件 Start -->
	<script src="pagenav/jquery-1.7.1.min.js"></script><!-- 引入jquery的js-->
	<link href="pagenav/jquery-ui-1.8.16.custom.css" rel="stylesheet" /><!-- 引入jquery-ui的css和js-->
	<script src="pagenav/jquery-ui-1.8.16.custom.min.js"></script>
	<link href="pagenav/jquery.ui.pagenav.css" rel="stylesheet" /><!-- 引入分页组件pagenav的css和js-->
	<script src="pagenav/jquery.ui.pagenav.js"></script>
	<!-- 引入分页 组件 End -->
	<!--AJAX Start-->
	<script src="../../../../../../app/js/data/locale/system.js"></script>
	<script src="../../../../../../app/js/data/locale/ajax.js"></script>
	<script src="../../../../../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!-- AJAX End -->

<script language="javascript">
/*
*引入树组件
*/
	var TreeNav = com.trs.tree.TreeNav;
	//重写com.trs.tree.TreeNav的动态载入方法
	com.trs.tree.TreeNav.makeGetChildrenHTMLAction = function(_elElementLi){
		var nPos	= _elElementLi.id.indexOf("_");
		//获取父节点的类型
		var sParentType	= _elElementLi.id.substring(0, nPos);
		//获取父节点的id
		var sParentId	= _elElementLi.id.substring(nPos+1);
		//返回tree_html_creator.jsp请求页面的结果，返回的结果为子节点的代码
		return "../../../../../nav_tree/tree_html_creator.jsp" + (location.search||'?1=1') + "&Type=0&FromSelect=0&ParentType=" + sParentType + "&ParentId=" + sParentId;
	}
	com.trs.tree.TreeNav.doActionOnClickA=function (event,oSrcElement){
		var strMessage = window.event.srcElement.parentNode.id;
		strs=strMessage.split("_"); 
		var treeDomType = strs[0];//r,s,c
		var treeDomId = strs[1];//number
		switch(treeDomType){
			case "r":
				var parametersKey = "SiteIds";/*"RootIds";*/
				treeDomId = 5;
				break;
			case "s":
				var parametersKey = "SiteIds";
				break;
			case "c":
				var parametersKey = "ChannelIds";
				break;
			default:
				var parametersKey = "SiteIds";
				break;
		}
		document.getElementById("parametersValue").value = treeDomId;
		document.getElementById("parametersKey").value = parametersKey;
		var formObj = document.getElementById('passForm');
		formObj.submit();
		setTimeout("document.getElementById('div_photolib_tree').style.display = 'none'",300);
		return false;
	}
/*
*树组件展现的js
*/
	function treeShowHidden(){
		if(document.getElementById('div_photolib_tree').style.display != "block"){
			document.getElementById('div_photolib_tree').style.display = "block";
		}
		else{
			document.getElementById('div_photolib_tree').style.display = "none";
		}
	}
/*
*尺寸大小展现的js
*/
	function scaleSizeShowHidden(){
		if(document.getElementById('div_photolib_scaleSize').style.display != "block"){
			document.getElementById('div_photolib_scaleSize').style.display = "block";
		}
		else{
			document.getElementById('div_photolib_scaleSize').style.display = "none";
		}
	}
/*
*引入分页组件
*/
	var jq=jQuery.noConflict();
	jq(function(){
		jq('#page-nav').pagenav({
			//初始化参数
			recordNum : <%=videoDocs.size()%>,
			pageSize : 8,
			pageIndex : <%=nCurrPage%>,
			callback : function(pageIndex, pageSize){
				//通过form发送js值给java
				var strPageIndex = pageIndex; //定义js变量 
				document.getElementById("passInputPageIndex").value = strPageIndex;//将js变量的值放到form中的一个隐藏域中
				document.getElementById("parametersValue").value = document.getElementById("hiddenValue").value;
				document.getElementById("parametersKey").value = document.getElementById("hiddenKey").value;
				var formObj = document.getElementById('passForm');
				formObj.submit();
			}
		});
	});
/*
*选中要插入图片事件
*/
function checkIf(checkDom,_id){
	/*获取input中自定义的属性*/
	var sDocId = checkDom.getAttribute("docid") + "#";
	var sDocTitle = checkDom.getAttribute("docTitle") + "#";
	var sChnlId = checkDom.getAttribute("channelid") + "#";
	var sChnlName = checkDom.getAttribute("chnlname") + "#";
	var sChnlDesc = checkDom.getAttribute("chnldesc") + "#";
	var iOSPlayUrl = checkDom.getAttribute("iosplay_url") + "#";
	var str = checkDom.getAttribute("videoFiles") + "#";
	var sPhotoSrc = checkDom.getAttribute("photo_srcs") + "#";
	/*（如果有的情况下）获取已经点击产生的存储在input内的值*/
	var inputPushsDocId = document.getElementById('sDocId').value;
	var inputPushsDocTitle = document.getElementById('sDocTitle').value;
	var inputPushsChnlId = document.getElementById('sChnlId').value;
	var inputPushsChnlName = document.getElementById('sChnlName').value;
	var inputPushsChnlDesc = document.getElementById('sChnlDesc').value;
	var inputPushiOSPlayUrl = document.getElementById('iOSPlayUrl').value;
	var inputPushstr = document.getElementById('str').value;
	var inputPushsPhotoSrc = document.getElementById('sPhotoSrc').value;
	/*如果是选中，在input中添加这个值*/
	if(document.getElementById(_id).checked) {
		inputPushsDocId += sDocId;
		inputPushsDocTitle += sDocTitle;
		inputPushsChnlId += sChnlId;
		inputPushsChnlName += sChnlName;
		inputPushsChnlDesc += sChnlDesc;
		inputPushiOSPlayUrl += iOSPlayUrl;
		inputPushstr += str;
		inputPushsPhotoSrc += sPhotoSrc;
	}
	/*如果是取消选中，在input中去掉这个值*/
	if(!(document.getElementById(_id).checked)) {
		/*判断是否已经选中过，在取消选中时去掉input中保存的这个值*/
		if(inputPushsDocId.indexOf(sDocId)!=-1){
			/*分别对应如下8个值*/
			//sDocId
			var arrsDocId = inputPushsDocId.split("#");
			var aDocId="";
			for(i=0;i<arrsDocId.length-1;i++){
				if(arrsDocId[i] == checkDom.getAttribute("docid")) {
					continue;
				}
				aDocId += arrsDocId[i]+"#";
			}
			inputPushsDocId = aDocId;
			//sDocTitle
			var arrsDocTitle = inputPushsDocTitle.split("#");
			var aDocTitle="";
			for(i=0;i<arrsDocTitle.length-1;i++){
				if(arrsDocTitle[i] == checkDom.getAttribute("docTitle")) {
					continue;
				}
				aDocTitle += arrsDocTitle[i]+"#";
			}
			inputPushsDocTitle = aDocTitle;
			//sChnlId
			var arrsChnlId = inputPushsChnlId.split("#");
			var aChnlId="";
			for(i=0;i<arrsChnlId.length-1;i++){
				if(arrsChnlId[i] == checkDom.getAttribute("channelid")) {
					continue;
				}
				aChnlId += arrsChnlId[i]+"#";
			}
			inputPushsChnlId = aChnlId;
			//sChnlName
			var arrsChnlName = inputPushsChnlName.split("#");
			var aChnlName="";
			for(i=0;i<arrsChnlName.length-1;i++){
				if(arrsChnlName[i] == checkDom.getAttribute("chnlname")) {
					continue;
				}
				aChnlName += arrsChnlName[i]+"#";
			}
			inputPushsChnlName = aChnlName;
			//sChnlDesc
			var arrsChnlDesc = inputPushsChnlDesc.split("#");
			var aChnlDesc="";
			for(i=0;i<arrsChnlDesc.length-1;i++){
				if(arrsChnlDesc[i] == checkDom.getAttribute("chnldesc")) {
					continue;
				}
				aChnlDesc += arrsChnlDesc[i]+"#";
			}
			inputPushsChnlDesc = aChnlDesc;
			//iOSPlayUrl
			var arriOSPlayUrl = inputPushiOSPlayUrl.split("#");
			var aIOSPlayUrl="";
			for(i=0;i<arriOSPlayUrl.length-1;i++){
				if(arriOSPlayUrl[i] == checkDom.getAttribute("iosplay_url")) {
					continue;
				}
				aIOSPlayUrl += arriOSPlayUrl[i]+"#";
			}
			inputPushiOSPlayUrl = aIOSPlayUrl;
			//str
			var arrstr = inputPushstr.split("#");
			var aStr="";
			for(i=0;i<arrstr.length-1;i++){
				if(arrstr[i] == checkDom.getAttribute("videoFiles")) {
					continue;
				}
				aStr += arrstr[i]+"#";
			}
			inputPushstr = aStr;
			//sPhotoSrc
			var arrsPhotoSrc = inputPushsPhotoSrc.split("#");
			var aPhotrSrc="";
			for(i=0;i<arrsPhotoSrc.length-1;i++){
				if(arrsPhotoSrc[i] == checkDom.getAttribute("photo_srcs")) {
					continue;
				}
				aPhotrSrc += arrsPhotoSrc[i]+"#";
			}
			inputPushsPhotoSrc = aPhotrSrc;
		}
	}
	/*存储到input中*/
	document.getElementById('sDocId').value = inputPushsDocId;
	document.getElementById('sDocTitle').value = inputPushsDocTitle;
	document.getElementById('sChnlId').value = inputPushsChnlId;
	document.getElementById('sChnlName').value = inputPushsChnlName;
	document.getElementById('sChnlDesc').value = inputPushsChnlDesc;
	document.getElementById('iOSPlayUrl').value = inputPushiOSPlayUrl;
	document.getElementById('str').value = inputPushstr;
	document.getElementById('sPhotoSrc').value = inputPushsPhotoSrc;
}
/*获取当前编辑器对象*/
var CKEDITOR	= parent.CKEDITOR;
var dlg			= CKEDITOR.dialog.getCurrent();
var editor		= dlg.getParentEditor();
/*
*插入图片逻辑
*/
function getSelectedArr(_id){
	var selectedStr = document.getElementById(_id).value;
	return selectedStr.split("#");
}
function ReplaceAll(str, sptr, sptr1){
	while (str.indexOf(sptr) >= 0){
		str = str.replace(sptr, sptr1);
	}
	return str;
}

function ok(){
	/*//选择大小尺寸插入
	var selectedSize = document.getElementById('div_selectSize').value;
	if(!selectedSize) {
		selectedSize = 2;
	}*/
	/*根据选择视频的id校验是否选择了要上传的视频*/
	var strSelectedId = document.getElementById('sDocId').value;
	alert(strSelectedId);
	if(!strSelectedId) {
		alert("没有选择要插入的视频，请选择");
		return;
	}
	var FLVPLAYER_BASE = "<%= VSConfig.getFLVPlayerBase() %>";
	var FMSAPP_URL = "<%= VSConfig.getUploadFMSAppUrl() %>";
	var bAddToPicAppend = true;
	/*获取选中的值，参数为记录值input的id，返回数组的形式*/
	var arrDocIds = getSelectedArr("sDocId");
	var arrDocTitles = getSelectedArr("sDocTitle");
	var arrChnlIds = getSelectedArr("sChnlId");
	var arrChnlNames = getSelectedArr("sChnlName");
	var arrChnlDescs = getSelectedArr("sChnlDesc");
	var arrIOSPlayUrls = getSelectedArr("iOSPlayUrl");
	var arrStrs = getSelectedArr("str");
	var arrsPhotoSrc = getSelectedArr("sPhotoSrc");
	/*遍历选中的插入对象*/
	for (var i=0; i<arrDocIds.length-1; i++) {
		var sDocId = arrDocIds[i];
		var sDocTitle = arrDocTitles[i];
		var sChnlId = arrChnlIds[i];
		var sChnlName = arrChnlNames[i];
		var sChnlDesc = arrChnlDescs[i];
		var iOSPlayUrl = arrIOSPlayUrls[i];
		var str = arrStrs[i];
		var sPhotoSrc = arrsPhotoSrc[i];
		//-------------------------------------
		var sToken = "";
		var changeUrlScript = "";
		var changeUrlElement = "";
		var videoFiles = str.split(";");
		if(videoFiles.length == 2) {
			sToken = videoFiles[0];
		} else {
			sToken = videoFiles[0];
			var videoPt = "";
			var videoHq = "";
			for(var j = 0 ; j < videoFiles.length; j++) {
				if(videoFiles[j] == "") {
					continue;
				}
				if(videoFiles[j].indexOf("hq") == -1) {
					videoPt = videoFiles[j];
				} else {
					videoHq = videoFiles[j];
				}
			}
		}
		//兼容推送
		if("public" != sToken.substring(0,6)){
			sToken = "public/"+sToken;
		}
		var PLAYER_WIDTH = "480px";
		var PLAYER_HEIGHT = "360px";				
		var oCKEmbedDIV =  editor.document.createElement( 'DIV' ) ;
		var oEmbedDIV =  oCKEmbedDIV.$;
		oEmbedDIV.setAttribute("__fckvideo","true");
		oEmbedDIV.setAttribute("vid",sDocId);
		oEmbedDIV.setAttribute("loop","false");
		oEmbedDIV.width = PLAYER_WIDTH;
		oEmbedDIV.height = PLAYER_HEIGHT;
		// 1)load html to str; 2) replace in str
		//template = ReplaceAll(template,"%sDocId%",sDocId);
		
		oEmbedDIV.innerHTML = '<noscript>您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>'
	+'<div style="text-align: center;">' 
	+'<div id=\"flashcontent_'+sDocId+'\" title=\"视频ID:'+sDocId+', 所属栏目ID:'+sChnlId+'&#13;视频当前栏目显示名称:' + sChnlDesc 
	+'\" style=\"color:green; font-size:12px; margin:0px auto;\">[视频库视频:'+sDocTitle+']</div>'
	+'</div>'
	+changeUrlScript
	+'<script type="text/javascript" src="' + FLVPLAYER_BASE + 'js/opensource/swfobject.js" ></'+'script>'
	+'<script type="text/javascript">'
	+'var flashvars = { videoSource:"' + FMSAPP_URL + '/' + sToken + '",'
	+'autoPlay:"false",'
	+'lang:"true",'
	+'isAutoBandWidthDetection:"false",'
	+'logoAlpha:"0.5" };'
	+'var params = { allowFullScreen:"true",'
	+'quality:"true",'
	+'scale:"exactfit",'
	+'allowScriptAccess:"always"};'
	+'var attributes = {};'
	+'attributes.id = "player_' + sDocId + '";'
	+'swfobject.embedSWF("' + FLVPLAYER_BASE + 'TRSVideoPlayer.swf' + '",'
	+'"flashcontent_' + sDocId + '",'  
	+ '"' + PLAYER_WIDTH + '"' + ', ' + '"' + PLAYER_HEIGHT + '"' + ', "10.0.0",' 
	+'false,flashvars,params,attributes);'
	+'<'+'/script>'
	+changeUrlElement;
		oEmbedDIV.title = ''+sDocId+'/'+sDocTitle;
		oEmbedDIV.setAttribute('imgurl',sPhotoSrc);
		//var oFakeImage	= oEditor.FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oEmbedDIV ) ;
		var oCKFakeImage = editor.createFakeElement( oCKEmbedDIV, 'cke_flash', 'flash', true );
		var oFakeImage = oCKFakeImage.$;
		//var oFakeImage	= editor.FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oEmbedDIV ) ;
		var oCKPara = editor.document.createElement("P");
		var oPara = oCKPara.$;
		editor.insertElement( oCKPara ) ;
		oPara.align = 'center';
		oPara.appendChild( oFakeImage ) ;
		oFakeImage.src=sPhotoSrc;
		oFakeImage.setAttribute( '_fckvideo', 'true', 0 ) ;
		oFakeImage.style.width = PLAYER_WIDTH;
		oFakeImage.style.height = PLAYER_HEIGHT;
	}
	dlg.hide();
}

/*
*选择尺寸大小
*/
function selectSize(dom){
	var selectSize = dom.getAttribute('value');
	document.getElementById('div_selectSize').value = selectSize;
	setTimeout("document.getElementById('div_photolib_scaleSize').style.display = 'none'",300);
	document.getElementById('size_change').innerHTML = dom.innerHTML;
	//document.getElementById('size_change').style.backgroundColor = "blue"; onblur事件触发背景色再改变
	return false;
}

</script>
<!-- 隐藏的发送当前也的表单 -->
<form  method="post" action="video.jsp" id ="passForm"> 
	<input id = "passInputPageIndex" type = "hidden" name="passInputPageIndex">
	<input id = "parametersValue" type = "hidden" name="parametersValue">
	<input id = "parametersKey" type = "hidden" name="parametersKey">
</form>
<input id = "hiddenValue" type = "hidden" value="<%=nParametersValue%>">
<input id = "hiddenKey" type = "hidden" value="<%=strParametersKey%>">
<!-- 隐藏的选择的视频Start -->
<input id = "sDocId" type = "hidden">
<input id = "sDocTitle" type = "hidden">
<input id = "sChnlId" type = "hidden">
<input id = "sChnlName" type = "hidden">
<input id = "sChnlDesc" type = "hidden">
<input id = "iOSPlayUrl" type = "hidden">
<input id = "str" type = "hidden">
<input id = "sPhotoSrc" type = "hidden">
<!-- 隐藏的选择的视频End -->
<input id = "div_selectSize" type = "hidden">

<style type="text/css">
/*
*树组件css
*/
	.div_photolib_tree{
		position:absolute;
		width:177px;
		height:150px;
		border:1px solid #e2e3ea;
		z-index:1000;
		left:34px;
		top:32px;
		background:white;
		display:none;
	}
/*
*分页组件css
*/
	.page-nav{
		font-size:12px;
		position:absolute;
		top:350px;
		left:25%;
		width:350px;
		clear:both;
	}
/*
*上传按钮css
*/
	.btn_div{
		color:white;
		font-size:14px;
		text-align:center;
		line-height:29px;
		font-weight:600;
		font-family:'微软雅黑';
		position:absolute;
		right:25px;
		top:345px;
		background:url(images/btn_div.png) no-repeat;
		width:76px;
		height:29px;
		cursor:hand;
	}
/*
*尺寸大小css
*/
	.div_photolib_scaleSize{
		position:absolute;
		width:140px;
		height:150px;
		border:1px solid #e2e3ea;
		z-index:1000;
		left:130px;
		top:32px;
		background:white;
		display:none;	
	}
	.div_selected_scaleSize{
		height:24px;
		width:100%;
		border-bottom:1px solid #e2e3ea;
		font-family:'微软雅黑';
		font-size:14px;
		cursor:hand;
		line-height:24px;
	}
	.div_input{
		overflow:hidden;
	}
</style>
</head>

<body>
<!-- 顶部菜单 -->
	<div id="" class="div_header">
		<div id="" class="div_input">
			视频库
		</div>
		<div id="" class="div_input_img" onclick="treeShowHidden()">
			
		</div>
		<!-- <div id="size_change" class="div_input">
				尺寸大小
		</div>
		<div id="" class="div_input_img" onclick="scaleSizeShowHidden()">
			
		</div>
		 -->
		<div id="" class="div_search" contentEditable="true">
			
		</div>
	</div>
<!-- 隐藏的树组件界面 -->
<div id="div_photolib_tree" class="div_photolib_tree" tabIndex="100">
	<DIV class="TreeView">
		<DIV id="r_2" title="视频库 "SiteType="2" classPre="SiteType1" isRoot="true" style="zoom:1">
			<A href="#">视频库</A>
		</DIV>
		<ul></ul>
	</DIV >
</div>
<!-- 隐藏的尺寸大小界面 
<div id="div_photolib_scaleSize" class="div_photolib_scaleSize">
	<div id="" class="div_selected_scaleSize" value="0" onclick="selectSize(this)">缩略图(75 x 75)</div>
	<div id="" class="div_selected_scaleSize" value="1" onclick="selectSize(this)">图片列表(124)</div>
	<div id="" class="div_selected_scaleSize" value="2" onclick="selectSize(this)">发布概览(133)</div>
	<div id="" class="div_selected_scaleSize" value="3" onclick="selectSize(this)">中图(240)</div>
	<div id="" class="div_selected_scaleSize" value="4" onclick="selectSize(this)">大图(500)</div>
	<div id="" class="div_selected_scaleSize" value="5" onclick="selectSize(this)">超大图(640)</div>
</div>-->
<!-- 图片库界面的展现 -->
	<div id="" class="div_box">
		<ul id="" class="">
<%
	//遍历结果集

	for (int i = 1; i <= videoDocs.size(); i++) {
		try{
			VideoDoc videoDoc = (VideoDoc)videoDocs.getAt(i - 1);
			if (videoDoc == null)
				continue;
			Channel docChannel = null;
			if (nChannelId != 0) {
				docChannel = videoDoc.getDocChannel();
			} else {
				docChannel = videoDoc.getChannel();
			}
			//------------------------------------------------------
			boolean bCanDetail = hasRight(loginUser, videoDoc, 34);
			int nRowId = videoDoc.getChnlDocProperty("RECID", 0);
			int nDocId = videoDoc.getDocId();
			int nChnlId = videoDoc.getChannelId();
			int nDocChannelId = videoDoc.getDocChannelId();
			String sRightValue = videoDoc.getRightValue(loginUser).toString();
			boolean bTopped = videoDoc.isTopped();
			int nDocType = videoDoc.getPropertyAsInt("DOCTYPE", 0);
			int nModal = videoDoc.getChnlDocProperty("MODAL", 0);
			int nStatusId = videoDoc.getStatusId();
			String nStatusName = LocaleServer.getString("video_list_editor.jsp.unkown","未知");
			if (videoDoc.getStatus() != null) {
				nStatusName = videoDoc.getStatus().getDisp();
			}
			String sTitle = CMyString.transDisplay(videoDoc.getPropertyAsString("DOCTITLE"));
			String sCrUser = videoDoc.getPropertyAsString("CrUser");
			String sFileNameLike = videoDoc.getPropertyAsString("DOCRELWORDS");
			XVideoMgr xvideoMgr = WCMContextHelper.getXVideoMgr();
			List xvideos = videoDoc.getXvideos();
			if(xvideos == null ){
				continue;
			}
			StringBuffer tokens = new StringBuffer();
			//	fjh@2009.2.2  默认按bitrate从小到大排列；缩略图显示为高清缩略图
			XVideo xvideo = new XVideo();
			for (int k = 0; k < xvideos.size(); k++) {
				xvideo = (XVideo) xvideos.get(k);
				if (xvideo == null || xvideo.getFileName() == null) {
					continue;
				}
				tokens.append(xvideo.getFileName()).append(';');
			}
			String srcFileName = xvideo.getSrcFileName();
			/*
				兼容数据迁移到双码流srcFileName为空
				此时srcFileName=token（原来都是11位）
			*/
			if(srcFileName == null || "".equals(srcFileName.trim())) {
				srcFileName = xvideo.getFileName().substring(0,11);
			}
			String thumbRoot = "";
			thumbRoot = VSConfig.getThumbsHomeUrl();
			String thumb = thumbRoot +"/"+ xvideo.getThumb();
			int convertFlag = xvideo.getConvertStatus();
			boolean converting = (convertFlag == 0);
			boolean convertFail = (convertFlag == -1);
%>
			<li class="photolib_li" id="thumb_item_<%=nRowId%>"docId="<%=nDocId%>" channelid="<%=nDocChannelId%>" currchnlid="<%=nChnlId%>">
				<div id="" class="div_img">
					<img src="<%=adapatedImgSrc(convertFlag, thumb)%>" docid="<%=nDocId%>" videoToken="<%=srcFileName%>" style="cursor:hand;height:94px;width:112px;" onclick="<%=getImgClickScript(bCanDetail, converting, convertFail, tokens.toString())%>/>
				</div>
				<div id="" class="">
					<span class="span_font" id="desc_<%=nRowId%>" title="<%=sTitle%>"><%=filter(sTitle)%></span>
					<input type="checkbox" name="AppendixId" class='img_checkbox' id="cbx_<%=nRowId%>" value="<%=nDocId%>" photo_srcs="<%=adapatedImgSrc(convertFlag, thumb)%>" iosplay_url="<%= xvideoMgr.getiOSPlayURL(xvideo) %>" desc="视频<%=nDocId%>&#13;<%=nStatusName%>" channelid="<%=nDocChannelId%>" docid='<%=nDocId%>' videoFiles="<%=tokens.toString()%>" chnlname="<%=docChannel.getName()%>" chnldesc="<%=docChannel.getDesc()%>" docTitle="<%=sTitle%>" onclick="checkIf(this,id)">
				</div>
			</li>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
		</ul>
	</div>
<input id="thumbUrl" type="hidden" value="<%=VSConfig.getThumbsHomeUrl()%>"/>

	<!-- 分页 -->
	<div class="page-nav" id="page-nav"></div>
	<!-- 按钮 -->
	<div id="btn_div" class="btn_div" onclick="ok()">确   定</div>
</body>
</html>
<%!
	private String filter(String sTitle) throws WCMException{
		return sTitle.length() > 10 ? sTitle.substring(0,8) + ".." : sTitle;
	}

	private String adapatedImgSrc(String thumb, boolean sConverting) {
		//	alert("thumb=[" + thumb + "]\nsConverting=[" + sConverting + "]\n" + (sConverting ? "converting" : "finish"));
		if (sConverting) {
			return "../../../../../video/image/inConverting.jpg";
		} else {
			return thumb + "?"
			+ Math.random();
		}
	}

	private String adapatedImgSrc(int converFlag, String thumb) {
		if (converFlag == 0) {
			return "../../../../../video/image/inConverting.jpg";
		} else if (converFlag == -1) {
			return "./image/convertFail.gif";
		} else if (converFlag == -2 || converFlag == -3) {
			//fjh@20090317: 切割失败的提示
			return "../../../../../video/image/convertFail.gif";
		} else
		{
			return thumb + "?"
			+ Math.random();
		}
	}

	private String playlink(String tokens) {
		String sFeature = "'location=no,resizable=no,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width=640,height=450'";
		return "javascript:window.open('../../../../../video/player.jsp?v=" + tokens
				+ "', '_blank'," + sFeature + ");";
	}

	private String getImgClickScript(boolean bCanDetail, boolean converting, boolean convertFail, String tokens) {
		return (bCanDetail && !converting && !convertFail) ? playlink(tokens):"return false;";
	}

	private String statusColor(String status, String title) {
		if (status == LocaleServer.getString("video_list_editor.jsp.yifa","已发")) {
			return "<font color='green'>" + title + "</font>";
		} else {
			return title;
		}
	}
	//点击打开视频播放界面引入的list_base.jsp页面的方法
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}

%>