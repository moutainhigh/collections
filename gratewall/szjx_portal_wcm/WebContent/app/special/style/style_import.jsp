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
<%@ page import="com.trs.components.common.publish.widget.StyleConstants" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	int nStyleType = currRequestHelper.getInt("StyleType", 1);
	int nPageStyleId = currRequestHelper.getInt("PageStyleId", 0);
	String sServiceId = "";
	String sMethodName = "";
	String sPostDataName = "";
	if(nStyleType == StyleConstants.PAGE_STYLE_TYPE){
		sServiceId = "wcm61_pagestyle";
		sMethodName = "importPageStyleZip";
		sPostDataName = "PageStyleZipFile";
	}else if(nStyleType == StyleConstants.RESOURCE_STYLE_TYPE){
		sServiceId = "wcm61_resourcestyle";
		sMethodName = "importResourceStyleZip";
		sPostDataName = "ResourceStyleZipFile";
	}else if(nStyleType == StyleConstants.CONTENT_STYLE_TYPE){
		sServiceId = "wcm61_contentstyle";
		sMethodName = "importContentStyleZip";
		sPostDataName = "ContentStyleZipFile";
	}
	out.clear();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title WCMAnt:param="style_import.importStyle">导入风格文件</title>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<style type="text/css">
		html,body{
			width:100%;
			height:100%;
			margin:0px;
			padding:0px;
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
			var oPostData = {
				ImportMode: getRadioValue('StyleImportMode'),
				<%=sPostDataName%> :  _sFilePath,
				PageStyleId : <%=nPageStyleId%>
			};			 
			wcmXCom.get('btnSave').disable();
			if(top.ProcessBar)
				top.ProcessBar.start("导入风格！");
			BasicDataHelper.Call('<%=sServiceId%>','<%=sMethodName%>',oPostData,true, function(_trans, _json){
				var c_bWin = wcm.CrashBoarder.get(window);
				if(top.ProcessBar)
					top.ProcessBar.close();
				Ext.Msg.report(_json, '风格导入结果', function(){
					c_bWin.hide();
					c_bWin.notify('true');
				});
			});
			return false;
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
	</script>
</head>
<body>
<div class="content1" >
	<span WCMAnt:param="style_import.chose.importfile">选择要导入的文件</span>
	<div id="divFileUpload">
		<IFRAME name="frmUploadFile" id="frmUploadFile" style="height:30px; width:230px" frameborder="0" vspace="0" src="../../file/file_upload.jsp?SelfControl=0&AllowExt=ZIP&ShowText=0" scrolling="NO" noresize></IFRAME>
	</div>
	<div class="base_line" WCMAnt:param="style_import.supportZip">
		支持zip格式文件
	</div>
	<div class="operations_title" WCMAnt:param="style_import.chose.styleName.duplicate">
		如果风格重名
	</div>
	<div class="operations base_line">
		<span><input type="radio" name="StyleImportMode" value="1" checked></span>
		<span WCMAnt:param="style_import.autoCover">自动覆盖</span>
		<span><input type="radio" name="StyleImportMode" value="3"></span>
		<span  WCMAnt:param="style_import.jsp.auto.changeName">自动更名</span>
		<span><input type="radio" name="StyleImportMode" value="2"></span>
		<span WCMAnt:param="style_import.jump">跳过</span>
	</div>
</div>
</body>
</html>