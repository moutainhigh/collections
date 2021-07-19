<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@include file="../../include/public_server.jsp"%>
<%
	int nChannelId = Integer.parseInt(request.getParameter("ChannelId"));
	int nSiteId = 0;
	int nSiteType = 0;
	WebSite oSite = null;
	if(nChannelId > 0){
		Channel oChannel = Channel.findById(nChannelId);
		if(oChannel != null){
			oSite = oChannel.getSite();
			nSiteId = oSite.getId();
			nSiteType = oSite.getType();
		}
	}
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="document_importoffice.html.title">智能创建文档</TITLE>
<style>
	body{
		font-size:12px;
	}
	div{
		width:100%;
		display:block;
		margin:2px 0px;
	}
	SPAN{
		float:left;
	}
	select{
		width:100px;
	}
	.attributeName{
		width:120px;
		display:inline-block;
	}
	.tip{
		margin-left:10px;
		color:#008000;
	}
	.info{
		color:#008000;
	}
</style>
<script src="../js/runtime/myext-debug.js"></script>
<script src="../js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/locale/cn.js" WCMAnt:locale="../js/locale/$locale$.js"></script>
<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/source/wcmlib/core/CMSObj.js"></script>
<script src="../js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../js/source/wcmlib/com.trs.dialog/FaultDialog.js"></script>
<script src="../../app/js/source/wcmlib/suggestion/suggestion.js"></script>

<!--validator start-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--validator end-->

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
<script type="text/javascript" src="batchupload/swfobject.js" ></script>
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : function(){
				
				FloatPanel.close();
			},
			name :  wcm.LANG.DOCUMENT_PROCESS_46 || '关闭'
		}],
		withclose : false,
		size : [500, 380]
	};
</script>
<script language="javascript">
<!--
var sDocHtml = null;
var m_nDocId = 0;
var oParams = {
	'ChannelId' : getParameter('ChannelId'),
	'FromEditor' : 1
}
function onComplete(){
	if(m_nDocId > 0){
		var info = {
			objId : m_nDocId,
			objType : WCMConstants.OBJ_TYPE_CHNLDOC
		};
		CMSObj.createFrom(info, null)['afteradd']();
		//需要关闭页面
		FloatPanel.close();
	}
}

/*Event.observe(window,'beforeunload',function(){
	if(m_nDocId > 0){
		var info = {
			objId : m_nDocId,
			objType : WCMConstants.OBJ_TYPE_CHNLDOC
		};
		CMSObj.createFrom(info, null)['afteradd']();
	}
});*/
var bError2 = false;
function onSuccess(index,statusCode,jsonStr,fileName,size,nSuccCount){	
	var arrResult = (jsonStr||jsonStr).split('<!--##########-->');
	arrResult[0] = (arrResult[0] || '').trim();
	var faultInfo = {
		code		: arrResult[1],
		message		: arrResult[2],
		detail		: arrResult[3],
		suggestion  : arrResult[4]
	}
	if(arrResult[0]=="<!--ERROR2-->"){
		if(!bError2){
			bError2 = true;
			Ext.Msg.fault(faultInfo, wcm.LANG.DOCUMENT_PROCESS_51 || '与服务器交互时出现错误');
//			alert(jsonStr);
		}
		return 0;
	}
	if(arrResult[0]=="<!--ERROR1-->"){
		Ext.Msg.fault(faultInfo, wcm.LANG.DOCUMENT_PROCESS_51 || '与服务器交互时出现错误');
		return 0;
	}

	m_nDocId = jsonStr.split('<!--RIGHT-->')[1];
	return 1;
}

function onValidationBeforeUpload(){
	return ValidationHelper.validAndDisplay('DOCKEYWORDS');
}

 function beforeUpload() {   
   return "&DocType="+$("DocType").value + "&KeyWords=" + encodeURIComponent($("DOCKEYWORDS").value);
 } 
 Event.observe(window, 'load', function(){		
		ValidationHelper.initValidation();
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
					siteType : <%=nSiteType%>,
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
		
	});
//keywords replace

function keyWordsReplace(domId){
	var oldValue = $(domId).value;
	var sValue = oldValue.trim().replace(/[ 　,,;]/g , ";"); 
	$(domId).value = sValue.replace(/;;/g , ";"); 
}
</SCRIPT>
</HEAD>

<BODY>
<div>
	<div class="info" WCMAnt:param="document_importoffice.jsp.firstsetdockeywords">请先设置文档关键词再上传文档</div>
	<span class="attributeName" WCMAnt:param="document_importoffice.jsp.batsetdockeywords">批量设置文档关键词：</span>
	<input type="text" name="DOCKEYWORDS" id="DOCKEYWORDS" value="" validation="max_len:'200',type:'string',desc:'关键词',showid:'keyWordsSp' "/>
	<span  id="keyWordsSp"></span>
</div>
<!--txt编码部分注释，此处已采用CharsetDetector.detect的方式进行编码判断-->
<!--<div>
<span WCMAnt:param="document_importoffice.html.FileEncode" class="attributeName">上传txt文件编码：</span>
<SELECT name="FileEncode" id="FileEncode">
	<OPTION value="UTF-8">UTF-8</OPTION> 
	<OPTION value="GBK" selected WCMAnt:param="website_add_edit.jsp.chianness">简体中文</OPTION> 
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.english">英语</OPTION> 
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.french">法语</OPTION>
	<OPTION value="windows-1251" WCMAnt:param="website_add_edit.jsp.eyu">俄语</OPTION> 
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.xibanya">西班牙语</OPTION>
	<OPTION value="windows-1256" WCMAnt:param="website_add_edit.jsp.alabo">阿拉伯语</OPTION> 
	<OPTION value="big5" WCMAnt:param="website_add_edit.jsp.chianness2">中文繁体</OPTION>
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.butaoya">葡萄牙语</OPTION>
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.deyu">德语</OPTION>
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.yidali">意大利语</OPTION> 
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.helan">荷兰语</OPTION> 
	<OPTION value="windows-1251" WCMAnt:param="website_add_edit.jsp.baojialiya">保加利亚语</OPTION>
	<OPTION value="iso-8859-2" WCMAnt:param="website_add_edit.jsp.bolan">波兰语</OPTION>
	<OPTION value="windows-1250" WCMAnt:param="website_add_edit.jsp.luomaniya">罗马尼亚语</OPTION> 
	<OPTION value="iso-8859-2" WCMAnt:param="website_add_edit.jsp.xiongyali">匈牙利语</OPTION>
	<OPTION value="windows-1250" WCMAnt:param="website_add_edit.jsp.jieke">捷克语</OPTION>
	<OPTION value="euc-kr" WCMAnt:param="website_add_edit.jsp.chaoxian">朝鲜语</OPTION> 
	<OPTION value="windows-874" WCMAnt:param="website_add_edit.jsp.taiguo">泰国语</OPTION>
	<OPTION value="windows-1254" WCMAnt:param="website_add_edit.jsp.tuerqi">土耳其语</OPTION> 
	<OPTION value="windows-1258" WCMAnt:param="website_add_edit.jsp.yuenan">越南语</OPTION>
	<OPTION value="windows-1251" WCMAnt:param="website_add_edit.jsp.menggu">蒙古语</OPTION> 
	<OPTION value="iso-8859-1" WCMAnt:param="website_add_edit.jsp.yinni">印尼语</OPTION>
	<OPTION value="euc-jp" WCMAnt:param="website_add_edit.jsp.japanness">日语</OPTION>
</SELECT>
</div>-->
<div class="">
	<span class="attributeName" WCMAnt:param="document_importoffice.jsp.filetype">文档类型：</span>
	<span>
	<select name="DocType" id="DocType">
		<option value="20" >HTML</option>
		<option value="40"  WCMAnt:param="document_importoffice.jsp.file">文件</option>
	</select></span>
	<span class="tip"  WCMAnt:param="document_importoffice.jsp.pdffileonlyasfiletypedoc">PDF文件只能作为文件类型的文档</span>
</div>
<div>
	<div id="flashcontent" WCMAnt:param="document_importoffice.html.errorMessage">浏览器或Flash环境异常, 导致该内容无法显示!</div>
	<noscript WCMAnt:param="document_importoffice.html.noSupportExp">您使用的浏览器不支持或没有启用javascript，请启用javascript后再访问, 谢谢!</noscript>
	<script type="text/javascript">
		// <![CDATA[
		//var sFileCode = $("FileEncode").value;
		// swf, id, w, h, ver, c, useExpressInstall, quality, xiRedirectUrl, redirectUrl, detectKey
		var flashvars = {};
		flashvars.uploadUrl = WCMConstants.WCM6_PATH +"system/import_office_doc.jsp;jsessionid=<%=session.getId()%>?method=upload%26ChannelId="+getParameter('ChannelId');
		flashvars.ext = "*.txt;*.htm;*.html;*.doc;*.docx;*.rtf;*.pdf;*.xls";
		flashvars.onSuccess = "onSuccess";
		flashvars.onComplete = "onComplete";
		flashvars.maxSize = "10485760";
		var params = {
			quality : "high",
			scale : "exactfit",
			wmode : "transparent",
			allowScriptAccess : "sameDomain"
		};
		swfobject.embedSWF("batchupload/fMultiUpload.swf","flashcontent","480", "250", "9.0.124", 'batchupload/expressinstall.swf', flashvars, params);
		// ]]>
	</script>
</div>
</BODY>
</HTML>