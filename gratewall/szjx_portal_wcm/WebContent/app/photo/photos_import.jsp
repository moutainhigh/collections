<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	String channelName = null;
	Channel currChannel = null;
	Watermarks currWatermarks = null;
	Watermark newWatermark = null;
	if(nChannelId == 0){
		channelName = LocaleServer.getString("photos_import.label.choose", "选择");
	}else{
		currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photos_import.id.zero","没有找到ID为{0}的栏目"),new int[]{nChannelId}));
		}
		channelName = currChannel.getDesc();
		nSiteId = currChannel.getSiteId();
	}
	WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
	filter.addSearchValues(0, nSiteId);
	try{
		currWatermarks = Watermarks.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("photos_import.jsp.label.fail2get_site_shuiyin_coll", "获取站点水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="photos_import.html.title"> 批量导入图片 </title>
<meta name="generator" content="editplus" />
<meta name="author" content="guoyl@live.com" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/photo.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/suggestion/suggestion.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/suggestion/resource/suggestion.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--FloatPanel Inner Start-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script type="text/javascript" src="../../app/document/batchupload/swfobject.js" ></script>
<script src="photos_import.js" type="text/javascript"></script>
<script language="javascript">
<!--
	window.m_fpCfg = {
		m_arrCommands : [{		
		name : wcm.LANG.PHOTO_CONFIRM_115 || '关闭',
		cmd  : 'myclose'
	}],
	withclose : false,
	size : [490, 420]
	};	
//获取iptc相关信息的函数
String.prototype.getIptcInfo = function(name){
	var reg = new RegExp("##" + name + ":([\\s\\S]*)##" + name + wcm.LANG.PHOTO_CONFIRM_116 || "结束");
	var sResults = this.match(reg);
	if(sResults!=null)
		return sResults[1];
	else
		return "";
}
//获取exif相关信息的函数
String.prototype.getExifInfo = function(name){
	var reg = new RegExp(name + ":([^<]*)<br\/>");
	var sResults = this.match(reg);
	if(sResults!=null)
		return sResults[1];
	else
		return "";
}

function onComplete(){
}

Event.observe(window,'load',function(){
	$("MainKindId").value = getParameter("ChannelId")||0;
	PageContext.mainKindId=$("MainKindId").value;
	PageContext.loadMainKind();
	PageContext.loadDefaultBmpConverType();
	initKeywords();
});
function keyWordsReplace(domId){
	var oldValue = $(domId).value;
	var sValue = oldValue.trim().replace(/[ 　,,;]/g , ";"); 
	$(domId).value = sValue.replace(/;;/g , ";"); 
}
function initKeywords(){
	Event.observe($('DOCKEYWORDS'), 'blur', function(){
		keyWordsReplace('DOCKEYWORDS');
	});
	if(!$('DOCKEYWORDS'))return;
	var sg1 = new wcm.Suggestion();
	var sInputValue = "";
	sg1.init({
		el : 'DOCKEYWORDS',
		autoComplete : false,
		requestOnFocus : false,
		execute : function(){
			//TODO override the method.
			if(sInputValue != ""&&sInputValue.charAt(sInputValue.length-1)!=";"){
				sInputValue += ";";
			}
			var sNewInputValue = this.getListValue();
			var inputValueArr = [];
			var oldInputValueArr = sInputValue.split(/[\s　,,;; . ,]/g );
			var newInputValueArr = sNewInputValue.split(/[\s　,,;; . ,]/g );
			for(var z=0;z<newInputValueArr.length;z++){
				if(!oldInputValueArr.include(newInputValueArr[z]))inputValueArr.push(newInputValueArr[z]);
			}
			sNewInputValue = inputValueArr.join(";")
			sInputValue += sNewInputValue;
			this.setInputValue(sInputValue);
			//alert(this.getInputValue());
		},
		request : function(sValue){			
			var items = [];
			var nLen = sValue.length;
			var arr = ["," , " " , "," , ";" , ";", ",", "."];
			if(sValue=="")sInputValue="";
			var arr1 = sValue.split(/[\s　,,;; . ,]/g );
			var arr2 = sInputValue.split(";");
			var arr3 = [];
			for(var m=0;m<arr2.length;m++){
				if(arr1.include(arr2[m]))arr3.push(arr2[m]);
			}
			if(arr.include(sValue.charAt(nLen-1))&&(!arr2.include(arr1[arr1.length-1])))arr3.push(arr1[arr1.length-1]);
			sInputValue = arr3.join(";");
			if(arr.include(sValue.charAt(nLen-1)))return;
			for (var i = 0; i < nLen; i++){
				var sChar = sValue.charAt(nLen-i-1);
				if(arr.include(sChar)){
					sValue = sValue.slice(nLen-i,nLen);
					break;
				}
				continue;
			}
			var oPostData = {
				siteType : 1,
				siteId : <%=nSiteId%>,
				kname : sValue
			}
			BasicDataHelper.JspRequest('../keyword/keyword_create.jsp',oPostData,false,function(_trans,_json){
				//debugger
				var json = com.trs.util.JSON.eval(_trans.responseText.trim());
				for(var i=0;i<json.length;i++){
					items.push(json[i]);
				}
				sg1.setItems(items);
			}.bind(this));
		}
	});
}
var bError2 = false;
//图片上传成功后主要的处理方法
function onSuccess(index,statusCode,jsonStr,fileName,size,nSuccCount){
	FloatPanel.disableCommand("myclose",true);
	var arr = jsonStr.split("#==#");
	var whatInfo = arr[1];
	var sresults = arr[2];
	$("UploadedFiles").value = arr[0];	
	$("SourceFiles").value = fileName;
	var wmpos = [];
	var wmposcheckbox = $("LT","CM","RB");
	for(var i=0;i<3;i++){
		if(wmposcheckbox[i].checked){
			wmpos.push(wmposcheckbox[i].value);
		}
	}
	$("WatermarkPos").value = wmpos.join(",");
	BasicDataHelper.call("wcm6_photo","saveImageInfo","form_imageInfo",true,function(_transport,_json){
		var params = {
			ObjectId:_transport.responseText,
			ChannelId:$("MainKindId").value,
			DocKeyWords : $("DOCKEYWORDS").value
		};
		if(whatInfo!="noinfo" && sresults.length>0){
			$("alertWindows").innerHTML =  String.format("抽取图片Extf或Iptc信息[{0}]中...",_transport.responseText);
			if(whatInfo=="exif"){
				params.DocAbstract=sresults;
				var sRelTime = sresults.getExifInfo(wcm.LANG.PHOTO_CONFIRM_117 || "拍摄时间");
				if(sRelTime.length>0)params.DocRelTime=sRelTime;
				
			}else if(whatInfo=="iptc"){
				var docTitle = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_118 || "大标题");
				if(docTitle.length>0){
					params.DocTitle=docTitle;
				}else{
					docTitle = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_119 || "标题");
					if(docTitle.length>0)params.DocTitle=docTitle;
				}
				

				var docAuthor = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_120 || "作者");
				if(docAuthor.length>0)params.DocAuthor=docAuthor;

				var docContent = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_121 || "说明内容");
				if(docContent.length>0)params.DocContent=docContent;

				var docKeywords = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_109 || "关键词");
				if(docKeywords.length>0)params.DocKeywords=docKeywords;

				var docPlace = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_122 || "地点");
				if(docPlace.length>0)params.DocPlace=docPlace;

				var docPeople = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_123 || "人物");
				if(docPeople.length>0)params.DocPeople=docPeople;

				var docRelTime = sresults.getIptcInfo(wcm.LANG.PHOTO_CONFIRM_117 || "拍摄时间");
				if(docRelTime.length>0)params.DocRelTime=docRelTime;

			}
		}
		BasicDataHelper.call("wcm6_document","save",params,true,function(_transport,_json){
			$("alertWindows").innerHTML =  wcm.LANG.PHOTO_CONFIRM_124 || "保存图片相关信息完毕！";
		});
		notifyFPCallback(_transport);
		//$("iptcinfo").innerHTML = jsonStr;
	},function(_transport,_json){
			//if fail
		window.DefaultAjax500CallBack(_transport,_json,this);			
		FloatPanel.disableCommand("myclose",false);
	});
	var arrResult = (jsonStr||jsonStr).split('<!--##########-->');
	arrResult[0] = (arrResult[0] || '').trim();
	if(arrResult[0]=="<!--ERROR2-->"){
		if(!bError2){
			bError2 = true;
			Ext.Msg.fault({
				code		: arrResult[1],
				message		: arrResult[2],
				detail		: arrResult[3],
				suggestion  : arrResult[4] 
			}, wcm.LANG.PHOTO_CONFIRM_125 || '与服务器交互时出现错误');
		}
		return 0;
	}
	if(arrResult[0]=="<!--ERROR1-->"){
		Ext.Msg.fault({
			code		: arrResult[1],
			message		: arrResult[2],
			detail		: arrResult[3],
			suggestion  : arrResult[4] 
		}, wcm.LANG.PHOTO_CONFIRM_125 || '与服务器交互时出现错误');
		return 0;
	}
	return 1;
}
function onComplete(index,statusCode,jsonStr,fileName,size,nSuccCount){
	setTimeout(function(){FloatPanel.disableCommand("myclose",false);},500);
}
</script>
<style>
	body{
		font-size:12px;
	}
	div{
		width:100%;
		display:block;
		line-height:24px;
	}
	/*
	span{
		float:left;
	}
	*/
	.mainkindnotfound{
		cursor:pointer;
		color:red;
		font-size:16px
	}
	.mainkindfound{
		cursor:pointer;
		color:#483d8b
	}
	fieldset{width:96%;}
</style>
</head>

<body>
<div>
	<div id="flashcontent" WCMAnt:param="photos_import.jsp.exception">浏览器或Flash环境异常, 导致该内容无法显示!</div>
	<div>
		<span class="attributeName"  WCMAnt:param="photos_import.jsp.set_keywordsof_pic">批量设置图片关键词：</span><input type="text" name="DOCKEYWORDS" id="DOCKEYWORDS" value="" />
	</div>
	<div>
	<fieldset><legend WCMAnt:param="photos_import.jsp.picAttribute">图片属性</legend>
		<table style="font-size:12px;display:none" width="100%" border="0" height="100%">       
        <tr>
          <td>
			<span class="attr_name" WCMAnt:param="photos_import.jsp.mainKind">主分类：</span>
			<span id="mainkind" class=<%=nChannelId==0?"mainkindnotfound":"mainkindfound"%>><%=channelName%></span>
          </td>
        </tr>		
		<tr>
          <td>
			<span class="attr_name" WCMAnt:param="photos_import.jsp.otherKinds">其它分类：</span>
			<span style="cursor:pointer;color:#483d8b" title="选择图片的其它分类" id="selOtherKinds" WCMAnt:param="photos_import.jsp.select" WCMAnt:paramattr="title:photos_import.jsp.selectOthers">选择</span><br />			
			<span id="otherkinds_holder">
				<select name="otherkinds" id="otherkinds" multiple size="4" style="width:150">
					<option value="" id="othersId"></option>
				</select>
			</span>
          </td>
        </tr>
      </table>
		<div style="margin:2 0 0 15">
			  <span>
					<span class="attr_name" WCMAnt:param="photos_import.jsp.picTransStye">BMP图片转换格式：</span>
					<select onchange="convertBmp(this);" id="BmpConverTypeSelect">
						<option value="bmp" WCMAnt:param="photos_import.jsp.noChange">不转换</option>
						<option value="gif">GIF</option>
						<option value="jpg">JPG</option>
					</select>
			  </span>
			  <span style="margin:0 0 0 15">
					<span class="attr_name" WCMAnt:param="photos_import.jsp.selectWaterMark">选择水印：</span>
					<span id="watermarks">
					<select id="selwatermark" onchange="addWaterMark(this);" style="width:120px;">
							<option value="-1" WCMAnt:param="photos_import.jsp.noWaterMark">--不添加水印--</option>
							<%
								if(nSize>0){
									for(int i=0;i<nSize;i++){
										newWatermark = (Watermark)currWatermarks.getAt(i);
										String sFileName = newWatermark.getWMPicture();
										sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_HTTP) + sFileName;
							%>
									<option value="<%=newWatermark.getId()%>" _picsrc="<%=sFileName%>" _picfile="<%=newWatermark.getWMPicture()%>"><%=newWatermark.getWMName()%></option>
							<%
									}
								}
							%>
					</select>
					</span>
			  </span>
		</div>
		<div style="margin:2 0 0 15">
			 <span id="div_watermarkpos" style="display:none">
					<span class="attr_name" title="点击全选" style="cursor:hand" onclick="selectAllPos()" WCMAnt:param="photos_import.jsp.waterPosition" WCMAnt:paramattr="title:photos_import.jsp.clickToSelAll">水印位置：</span><br />
					<label for="LT"><span WCMAnt:param="photos_import.jsp.leftTop">左上</span><input type="checkbox" id="LT" value="1"/></label>
					<label for="CM"><span WCMAnt:param="photos_import.jsp.center">居中</span><input type="checkbox" id="CM" value="2"/></label>
					<label for="RB"><span WCMAnt:param="photos_import.jsp.rightDown">右下</span><input type="checkbox" id="RB" checked="true" value="3"/></label>
			</span>	
			<span style="margin:5 0 0 30">
				<img src=""  style="display:none" id="watermarkpic">
			</span>
		</div>
	</fieldset>
	</div>
	<div id="alertWindows"></div>
	<div id="iptcinfo"></div>
	<div id="othersinfo"></div>

	<noscript WCMAnt:param="photos_import.jsp.unSupport">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
	<script type="text/javascript">
		// <![CDATA[
	
		// swf, id, w, h, ver, c, useExpressInstall, quality, xiRedirectUrl, redirectUrl, detectKey
		var flashvars = {};
		flashvars.uploadUrl = WCMConstants.WCM6_PATH +"photo/import_photos_doc.jsp;jsessionid=<%=session.getId()%>?method=upload%26ChannelId="+getParameter('ChannelId');
		flashvars.ext = "*.jpg;*.jpeg;*.gif;*.png;*.bmp";
		flashvars.onSuccess = "onSuccess";
		flashvars.onFailure = "onFailure";
		flashvars.onComplete = "onComplete";
		flashvars.maxSize = "10485760";
		var params = {
			quality : "high",
			scale : "exactfit",
			wmode : "transparent",
			allowScriptAccess : "sameDomain"
		};
		swfobject.embedSWF(WCMConstants.WCM6_PATH+"document/batchupload/fMultiUpload.swf","flashcontent","480", "250", "9.0.124", WCMConstants.WCM6_PATH+'document/batchupload/expressinstall.swf', flashvars, params);

		
		// ]]>
	</script>
</div>
	<div style="display:none">
		<span WCMAnt:param="photos_import.jsp.selectFile">选择您要上传的文件(目前只支持Word格式文件)：</span>
		<span>
			<form name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile">
			</form>
			<form id="form_imageInfo">
				<input type="hidden" name="MainKindId" id="MainKindId" value="0"></input>
				<input type="hidden" name="OtherKindIds" id="OtherKindIds" value=""></input>
				<input type="hidden" name="WatermarkFile" id="WatermarkFile" value="0"></input>
				<input type="hidden" name="WatermarkPos" id="WatermarkPos" value=""></input>	
				<input type="hidden" name="UploadedFiles" id="UploadedFiles" value=""></input>
				<input type="hidden" name="SourceFiles" id="SourceFiles" value=""></input>
				<input type="hidden" name="BatchMode" id="BatchMode" value="0"></input>
				<input type="hidden" name="BmpConverType" id="BmpConverType" value=""></input>
			</form>
		</span>
	</div>
</body>
</html>