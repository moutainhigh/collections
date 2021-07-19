<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ include file="../document/document_addedit_import.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在 ../../include/public_server.jsp 中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%-- @include file="../document/document_addedit_include.jsp" --%>
<%
	int nIsReadOnly = currRequestHelper.getInt("isReadOnly", 0);
	boolean bIsReadOnly = (nIsReadOnly==1);
	String sReadOnly = (bIsReadOnly)?"readonly":"";
	
	//初始化（获取数据）
	int nDocumentId	  = 0;
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	//
	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	Document currDocument = null;
	Channel currChannel = null;
	Channel docChannel = null;
	boolean isNewsOrPics = false;//栏目类型是否是图片或头条新闻
	//获得currDocument对象
	if(bIsReadOnly){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("video_batch_add.jsp.noDocID","未指定查看文档的ID！"));
	}
	currDocument = Document.createNewInstance();
//工作流相关处理
	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);
	if(nFlowDocId>0){
		//校验权限
		WCMProcessServiceHelper.validateWorkFlowRight(loginUser, currDocument, nFlowDocId, FlowNode.ACTION_EDIT, LocaleServer.getString("video_batch_add.jsp.modify","修改"));
	}
	//获得currChannel对象
	if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("video_batch_add.jsp.lanmufail","获取栏目 ID为[{0}]的栏目失败 ！"),new int[]{nChannelId}));
		}
		if(currChannel.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,currChannel+LocaleServer.getString("video_batch_add.jsp.refreshlanmu","已被删除!请刷新您的栏目树."));
		}
		isNewsOrPics = (currChannel.getType() == Channel.TYPE_TOP_NEWS || currChannel.getType() == Channel.TYPE_TOP_PICS);
	}
	//获得docChannel对象
	docChannel = currChannel;
	if(currChannel==null&&docChannel==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("video_batch_add.jsp.notsetuplanmu","未指定新建视频所属栏目！"));
	}
	//权限校验
	if(currChannel != null && !AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_ADD)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("video_batch_add.jsp.noRigthNew","您没有权限新建文档！"));
	}

	if (currDocument.getStatusId() < 0){
		throw new WCMException(CMyString.format(LocaleServer.getString("video_batch_add.jsp.notOperate","ID为[{0}]的文档在废稿箱中，您暂时无法对其进行操作！"),new int[]{nDocumentId}));
	}
	String sOfficeSid = currRequestHelper.getString("OfficeSid");
	String[] officeInfo = null;
	if(sOfficeSid!=null){
		officeInfo = (String[])session.getAttribute(sOfficeSid);
	}

	String sTitle = "";//文档标题	
	int nDocType = Document.DOC_TYPE_HTML;//默认文档类型为HTML
	String sContent = "";//文档内容
	String sDocLink = "";//链接型文档链接
	String sDocFileName = "";//文件型文档文件名
	String sChannelName = "";//所属栏目名称
	String sOutlineTitle = "";//首页标题
	String sDocSubTitle = "";//副标题
	String sDocKeyWords = "";//关键字
	String sDocAbstract = "";//摘要
	String sDocAuthor = "";//作者
	String sRelTime = "";//文档撰写时间
	//TODO
	ConfigServer oConfigServer = ConfigServer.getServer();
	String sCanNewDocSource = oConfigServer.getSysConfigValue("ENABLE_SOURCE_EDIT", "true");
	boolean bCanNewDocSource = "true".equalsIgnoreCase(sCanNewDocSource)||"1".equals(sCanNewDocSource);
	//置顶信息
	boolean bIsCanTop = false;//是否在当前栏目有置顶权限
	//有修改文档的权限时才可做置顶设置
	bIsCanTop = AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_EDIT);
	boolean bTopped = false;//是否置顶
	boolean bTopForever = false;//是否永久置顶
	int nTopPosition = 0;//置顶位置
	CMyDateTime dtTopValidTime = null;//置顶有效时间
	Channels quoteChannels = null;//文档引用栏目
	int nCurrDocSource = 0;//文档来源
	Documents toppedDocuments = null;//currChannel中已置顶的文档

	//新建文档设置缺省值
	currDocument.setType(Document.TYPE_HTML);
	currDocument.setChannel(nChannelId);
	if(officeInfo!=null){
		sTitle = PageViewUtil.toHtmlValue(officeInfo[0]);
		sContent = PageViewUtil.toHtmlValue(officeInfo[1]);
	}
	sRelTime = CMyDateTime.now().toString("yyyy-MM-dd HH:mm");
	dtTopValidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
	//获得当前文档所属栏目名称
//		sChannelName = (docChannel!=null)?docChannel.getName():"";
	sChannelName = (docChannel!=null)?CMyString.filterForHTMLValue(docChannel.getDesc()):"";

	//判断是否可发布
	IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(docChannel);
	IPublishContent content =  PublishElementFactory.makeContentFrom(currDocument, folder);
	boolean bIsCanPub = false;
	if (DocumentAuthServer.hasRight(loginUser, docChannel, currDocument,
                WCMRightTypes.DOC_PUBLISH)) {
		//=======由于直接发布不考虑状态，需要需要将逻辑直接暴露在特殊使用场景中=====
		// 1. 栏目如果不允许发布，不能发布细览
        if (docChannel.isCanPub()) {
		// 2. 只要设置细览模板就可以发布
            bIsCanPub = (content.getDetailTemplate() != null);
        }
	}

	//文档发布属性所需参数
	int nDocTemplateId = 0;
	
	String strExecTime = "";
	boolean bDefineSchedule = false;

	CMyDateTime timeNow = CMyDateTime.now();
	strExecTime = timeNow.toString("yyyy-MM-dd HH:mm");

	String strDBName = DBManager.getDBManager().getDBType().getName();
	String appKey = VSConfig.getAppKey();
	String extUpload = VSConfig.getExtUpload();
	String size = VSConfig.getMaxPostSize();
	if(size == null||"".equals(size)){
		size = "1048576000";
	}
	
%>
<%
	out.clear();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="video_batch_add.jsp.uploadvideo">TRS WCM 批量上传新视频</TITLE>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/document_addedit.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/com.trs.util/Common.js"></script>
<script type="text/javascript" src="./js/opensource/json2.js" ></script>
<script type="text/javascript" src="./js/opensource/FABridge.js" ></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>

<script label="PageScope">
	//当前编辑中的文档ID,新文档为0
	var DocChannelId = '<%=(docChannel!=null)?docChannel.getId():0%>';
</script>
<script src="./video_batch_add.js" label="PageScope"></script>
<style type="text/css">
.uploadedInfo {
	overflow: hidden; 
	white-space: nowrap; 
	text-overflow: ellipsis;
	width: 400px;
	padding: 0 5px;
}
</style>
</head>
<body style="overflow: hidden;">

<%
	String uploadApp = VSConfig.getUploadJavaAppUrl();
	String uploadUrl = uploadApp + "/service/superUpload";
%>
<input type="hidden" id="extUpload"  value="<%=extUpload%>">
<div style="width:800px; height:500px">
<div id="con" style="margin:5px 5px; width:800px; height:360px">
	<div id="dvOperation" style="margin:5px 5px; width:500px;height:355px; float:left">
		<fieldset>
		<legend WCMAnt:param="video_batch_add.jsp.uploadvideo1">批量上传视频</legend>
		<div style="width:100%;height:40px;overflow:auto;">
			<div style="padding:10px 10px 0 10px">
				<div class="attr_row">
					<span WCMAnt:param="video_batch_add.jsp.belonglanmu">所属栏目:</span>
					<span id="DocChannel" style="color:green;font-weight:bold;"><%=sChannelName%></span>
				</div>
			</div>
		</div>
		<% if (VSConfig.isAutoClipSample()) { %>
			<div id="dvUpd" style="margin:5px 10px; width:100%;">
			<noscript>您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
			<div id="flashcontent">您的浏览器和Flash环境异常, 导致该内容无法显示!<a href="./flashversion.html"><font color="red">请尝试点击这里查看环境信息</font></a></div>
			<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>

			<div id="flashcontent">您的浏览器和Flash环境异常, 导致该内容无法显示!<a href="./flashversion.html"><font color="red">请尝试点击这里观看或报告环境信息</font></a></div>
						<noscript>您使用的浏览器不支持或没有启用javascript, 请启用javascript后再访问!</noscript>
						<script type="text/javascript">
								var flashvars = {uploadUrl:"<%= uploadUrl %>" 
									};
								var params = {
									quality:"high",
									wmode:"transparent",
									scale:"exactfit",
									allowScriptAccess:"sameDomain"
								};
								var attributes = {};
								attributes.id = "dvUpd";
								swfobject.embedSWF("vMultiUpload_autoClip.swf", "flashcontent", 
												   "470", "240", "9.0.124", false, flashvars, params, attributes);
						</script>
			</div>
			<% }  else { %>		
			<div id="dvUpd" style="width:100%;">
			<script type="text/javascript" src="./js/opensource/swfobject.js" ></script>

			<div id="flashcontent" WCMAnt:param="video_batch_add.jsp.noDisplay">您的浏览器和Flash环境异常, 导致该内容无法显示!<a href="./flashversion.html"><font color="red">请尝试点击这里查看环境信息</font></a></div>
			<noscript WCMAnt:param="video_batch_add.jsp.noScript">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
			<script type="text/javascript">
			var uploads = document.getElementById("extUpload").value;
            if(null==uploads||0==uploads.length||uploads=="null")
             {	var flashvars = {uploadUrl:"<%= uploadUrl %>?appKey=<%= appKey%>",
								maxSize:"<%= size %>",
			                    lang:""
								
									
								
						};}
						else{
							var flashvars = {uploadUrl:"<%= uploadUrl %>?appKey=<%= appKey%>",
								maxSize:"<%= size %>",
								extUpload:"<%= extUpload%>",
								lang:""
									
								
						};
						}
			var params = {
				wmode:"transparent",
				quality:"high",
				scale:"exactfit",
				allowScriptAccess:"always"
			};
			var attributes = {};
			attributes.id = "muiltUpp";
			swfobject.embedSWF("vMultiUpload.swf", "flashcontent", "470", "240", "9.0.124", false, flashvars, params, attributes);
			</script>
		</div>
			<% } %>			
		<div style="margin:20px 10px;">
			<div style="color:red;height:30px" WCMAnt:param="video_batch_add.jsp.notice">注意：上传过程中请不要关闭本页！</div>
		</div>
		</fieldset>
	</div>
<!-- 	<div id="dvMeta" style="margin-top:5px; width:120; float:left"></div> -->
	<div style="margin:5px 10px; width:250px; height:350px; float:left">
		<fieldset>
		<legend WCMAnt:param="video_batch_add.jsp.videoList">已上传视频列表</legend>
		<div id="dvMeta" style="margin:0; padding:0; width:100%; height:355px; overflow: hidden; overflow-y:auto;">
		</fieldset>
	</div>
</div>
<div>
&nbsp&nbsp&nbsp&nbsp&nbsp
</div>
<div style="margin:50px 390px">
	<input id="s" type="button" value="提  交" WCMAnt:paramattr="value:video_batch_add.jsp.submit" disabled="disabled" onclick="javascript:multiSaveMetaInfo();">
</div>

</div>

<script type="text/javascript">
// 提供给FlashUpload控件调用的函数(Begin)
function showMetaDiv() {
//	$("dvMeta").style.display = "inline";
	$("dvMeta").style.display = "block";
}
function uploadStarted() {
	$("s").disabled = true;
}

function setMetaData(fileName, jsonStr, times) {
//	alert("fileName: " + fileName + "\nmetadata(jsonStr): " + jsonStr + "\ntimes: " + times);
//	alert("$('dvMeta').innerHTML: [" + $("dvMeta").innerHTML + "]");
/*
	if ($("dvMeta").innerHTML == "") {
		$("dvMeta").innerHTML = "<b>已上传视频列表:</b>";
	}
*/
	var jsonArray = eval('(' + jsonStr + ')');
	var total = jsonArray.length;
	if (total) {
		if (total == 1) {
			var eDiv = document.createElement('div');
			eDiv.innerHTML = fileName;
			eDiv.title = fileName;
			eDiv.className = 'uploadedInfo';
			eDiv.setAttribute('doctitle', fileName);
			eDiv.setAttribute('metadata', JSON.stringify(jsonArray[0]));
			$("dvMeta").appendChild(eDiv);
			return;
		}
		for (var i = 0; i < total; i++) {
			//alert("jsonArray[" + i + "]: " + jsonArray[i] + "\n" + jsonArray[i].duration + ", " + jsonArray[i].token);
			//alert("toJSONString(): " + jsonArray[i].toJSONString());
			//alert("toJSONString(): " + JSON.stringify(jsonArray[i]));
			var eDiv = document.createElement('div');
			eDiv.innerHTML = fileName + "_Part" + i;
			eDiv.title = fileName + "_Part" + i;
			eDiv.className = 'uploadedInfo';
			eDiv.setAttribute('doctitle', fileName + "_Part" + i);
			eDiv.setAttribute('metadata', JSON.stringify(jsonArray[i]));
			$("dvMeta").appendChild(eDiv);
		}
		return;
	}
	//alert("不是期待的响应:\n" + jsonStr);
}

function enableSubmit() {
	$("s").disabled = false;
}
// 提供给FlashUpload控件调用的函数(End)
</script>

</BODY>
</HTML>