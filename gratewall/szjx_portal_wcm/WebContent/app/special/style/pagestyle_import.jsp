<%
/** Title:				view_system_skin_css_import.jsp
 *  Description:
 *        导出当前风格的CSS文件。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年3月25日
 *  Vesion:				1.0
 *  Last EditTime	:none
 *  Update Logs:	none
 *  Parameters:		see view_system_skin_css_import.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyle" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	out.clear();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title WCMAnt:param="pagestyle_import.jsp.title">导出当前风格的CSS文件</title>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<style type="text/css">
		html,body{
			height:100%;
			width:100%;
			padding:0px;
			margin:0px;
			overflow:hidden;
		}
		body{
			font-size:12px;
		}
		.content1{
			border:1px solid #acddfd;
			padding:5px;
			margin-top:10px;
		}
		.base_line{
			color:blue;
		}
		.operations_title{
			font-size:12px;
			height: 28px;
			line-height: 28px;
		}
		.operations{
			font-size:12px;
			height: 25px;
			line-height: 25px;
			padding-left: 30px;
		}
	</style>
	<script type="text/javascript">
		window.m_cbCfg = {
			btns : [
				{
					text : '确定',
					id : 'btnSave',
					cmd : function(){
						uploadFile();
						return false;
					}
				},
				{
					extraCls : 'wcm-btn-close',
					text : '取消'
				}
			]
		};
		function uploadFile(){
			var winUpload = $("frmUploadFile").contentWindow;
			var frm = winUpload.document.getElementById("frmPost");
			var sFileName = winUpload.document.getElementById("fileUpload").value;
			if(!winUpload.valid(sFileName)){
				return false;
			}

			frm.submit();
			return false;
		}

		function addFile(_sFilePath){
			if(_sFilePath==null){
				Ext.Msg.$alert("文件路径为空，上传失败！");
				return false;
			}
			dealWithUploadedFile(_sFilePath);
			return false;
		}

		function dealWithUploadedFile(_sFilePath){
			wcmXCom.get('btnSave').disable();
			var oPostData = {
				ImportMode: getRadioValue('StyleImportMode'),
				SubStyleImportMode : getRadioValue('SubStyleImportMode'),
				PageStyleZipFile :  _sFilePath
			};	
			if(top.ProcessBar)
				top.ProcessBar.start("保存风格");
			BasicDataHelper.Call('wcm61_pagestyle','importPageStyleZip',oPostData,true, function(_trans, _json){
				checkPageStyleImported();
			});
			return false;
		}
		//发送AJAX请求判断页面风格是否已经导入成功
		function checkPageStyleImported(){
			var sUrl = "style/pagestyle_import_check.jsp";
			new Ajax.Request(sUrl, {onComplete : pageStyleImportedChecked});
		}
		
		//判断页面风格是否已经导入成功
		function pageStyleImportedChecked(transport, json){
			eval("var oResult=" + transport.responseText);
			if(oResult["errorInfo"]){
			if(top.ProcessBar)
					top.ProcessBar.close();
				alert("出现异常：\n" + oResult["errorInfo"]);
			}
			if(oResult["currentPageStyle"]){
				if(top.ProcessBar){
					top.ProcessBar.setCurrInfo(oResult["currentPageStyle"]);
				}
			}
			if(oResult["successInfo"] && !oResult["isRunning"]){
				if(top.ProcessBar)
					top.ProcessBar.close();
				var c_bWin = wcm.CrashBoarder.get(window);
				c_bWin.hide();
				c_bWin.notify(true);
				var list = oResult["successInfo"].split(",");
				var sInfo = "";
				var i = 0;
				for(i;i<list.length;i++){
					sInfo = sInfo+"导入页面风格["+list[i].substring(list[i].indexOf("=")+1,list[i].indexOf("\("))+"]成功！\n";
					if(i==2){
						if(list.length>3){sInfo = sInfo +"......\n";}
						sInfo = sInfo+"共导入"+list.length+"个页面风格！";
						break;
					}
				}
				alert(sInfo);
			}
			if(oResult["isRunning"]){
				setTimeout(checkPageStyleImported, 1000);
				return;
			}
		}
		function notifyOnUploadFileError(_sErrorMsg) {
			Ext.Msg.$alert(_sErrorMsg);
		}
		function getRadioValue(_sRadioName){
			var radios = document.getElementsByName(_sRadioName);
			for (var i = 0; i < radios.length; i++){
				if(radios[i].checked) {
					return radios[i].value;
				}
			}
			return null;
		}
		function init(){
			Event.observe(document, 'click', function(event){
				var ev = window.event || event;
				var dom = Event.element(ev);
				if(dom.name == 'StyleImportMode'){
					 var value = dom.value;
					 if(value == 1){
						Element.show('SubStyleImportModeBox');
					 }else{
						Element.hide('SubStyleImportModeBox');
					 }
				}
			});			 
		}
	</script>
</head>
<body>
<div class="content1" >
	<div class="operations_title" WCMAnt:param="pagestyle_import.jsp.sel_import_file">
	选择要导入的文件（支持zip格式文件）:
	</div>
	<div id="divFileUpload">
		<IFRAME name="frmUploadFile" id="frmUploadFile" style="height:30px; width:230px" frameborder="0" vspace="0" src="../../file/file_upload.jsp?SelfControl=0&AllowExt=ZIP&ShowText=0" scrolling="NO" noresize></IFRAME>
	</div>
	<div class="operations_title" WCMAnt:param="pagestyle_import.jsp.page_style_renamed">
	页面风格出现重名时:
	</div>
	<div class="operations base_line">
		<span><input type="radio" name="StyleImportMode" id="StyleImportUpdateMode" value="1" checked></span>
		<span WCMAnt:param="pagestyle_import.jsp.auto_cover">自动覆盖</span>
		<span><input type="radio" name="StyleImportMode" id="StyleImportIgnoreMode" value="2"></span>
		<span WCMAnt:param="pagestyle_import.jsp.skip">跳过</span>
	</div>
	<div id="SubStyleImportModeBox">
		<span WCMAnt:param="pagestyle_import.jsp.when_page_style_renamed">资源风格和内容风格出现重名时：</span>
		<div class="operations base_line">
			<span><input type="radio" name="SubStyleImportMode" value="1" checked></span>
			<span WCMAnt:param="pagestyle_import.jsp.auto_cover">自动覆盖</span>
			<span><input type="radio" name="SubStyleImportMode" value="3"></span>
			<span WCMAnt:param="pagestyle_import.jsp.auto_rename">自动更名</span>
			<span><input type="radio" name="SubStyleImportMode" value="2"></span>
			<span WCMAnt:param="pagestyle_import.jsp.skip">跳过</span>
		</div>
	</div>
</div>
</body>
</html>