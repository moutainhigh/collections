<%
/** Title:				view_system_skin_css_upload.jsp
 *  Description:
 *        上传组件，提供上传图片文件的入口。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年3月25日
 *  Vesion:				1.0
 *  Last EditTime:	none
 *  Update Logs:	none
 *  Parameters:		FileUrl 上传文件的地址
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page import="com.trs.infra.util.CMyString" %>
<!-- WCM IMPORTS @ END -->
<!-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 -->
<%@include file="../../include/public_server.jsp"%>
<%
	String sFileUrl = CMyString.showNull(request.getParameter("FileUrl"));
	String sInputId = CMyString.showNull(request.getParameter("InputId"));
	// 图片文件的存储路径
	String sImageUploadPath = CMyString.showNull(request.getParameter("ImageUploadPath"));
	
	String sDefClass = "";
	String sUploadState = "";
	if("".equals(sFileUrl.trim())){
		sFileUrl = LocaleServer.getString("image_for_style_upload.jsp.label.please_sel_upload_pic", "请选择需要上传的图片文件。");
		sDefClass = "defTip";
	}else{
		sFileUrl = sFileUrl;
		sUploadState = LocaleServer.getString("image_for_style_upload.jsp.label.uploaded","已上传");
	}
%>
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE WCMAnt:param="image_for_style_upload.jsp.title">上传图片</TITLE>
	<BASE TARGET="_self">
	<style type="text/css">
	<!--
		html, body{
			scrollbar-face-color: #f6f6f6; 
			scrollbar-highlight-color: #ffffff; 
			scrollbar-shadow-color: #cccccc; 
			scrollbar-3dlight-color: #cccccc; 
			scrollbar-arrow-color: #330000; 
			scrollbar-track-color: #f6f6f6; 
			scrollbar-darkshadow-color: #ffffff;
			margin:0px;
			padding:0px;
			height:100%;
			width:100%;
		}
		.fileInput{
			cursor:pointer;
			width:0px;
			position:absolute;
			filter:alpha(opacity=0);
			opacity:0;
			margin:0px;
			padding:0px;
			height:23px;
			line-height:23px;
		}
		/*ff*/
		@-moz-document url-prefix(){
			.fileInput{
				left:0px;
			}
		}
		.fileText{
			margin:0px;
			padding:0px;
			width:120px;
			font-family: "宋体";
			font-size: 12px;
			text-align:right;
			vertical-align:middle;
			height:21px;
			padding-top:2px;
		}
		.defTip{
			color:#747171;
		}
		span{
			display:inline-block;
		}
	//-->
	</style>
	<script type="text/javascript">
	<!--
		var FILE_TYPES = "bmp,gif,ico,jpg,jpeg,png";//支持的图片格式
		function $alert(_sMsg){
			if(top.window.Ext&&top.window.Ext.Msg&&top.window.Ext.Msg.alert){
				top.window.Ext.Msg.alert(_sMsg);
			}else{
				alert(_sMsg);
			}
		}
		// 获取文件类型
		function getFileType(_sFileName){
			var sFileName = _sFileName || "";
			if(!sFileName || sFileName == ""){
				alert("请输入类别图片地址！");
				return null;
			}
			var nPointIndex = sFileName.lastIndexOf(".");
			if(nPointIndex < 0){
				alert("无法识别您输入的文件类型，请重新输入！");
				return false;
			}
			return sFileName.substring(nPointIndex+1);
		}
		// 校验文件类型
		function isValidFileType(_sFileType){
			if(!_sFileType) 
				return false;
			if(!FILE_TYPES || FILE_TYPES == "") 
				return true;
			if(FILE_TYPES.indexOf(",") < 0){
				return (FILE_TYPES.toUpperCase() == _sFileType.toUpperCase());
			}
			var arFileTypes = (FILE_TYPES.toUpperCase()).split(",");
			for(var i=0; i<arFileTypes.length; i++){
				if(_sFileType.toUpperCase() == arFileTypes[i]) 
					return true;
			}
			return false;
		}
		// 校验上传文件是否合法
		function validateFileType(_eFile){
			var _sFileName = _eFile.value;
			if(_sFileName==null||_sFileName==""){
				alert("请输入类别图片的地址");
				return false;
			}
			var sFileType = getFileType(_sFileName);
			if(sFileType==null){
				return false;
			}
			if(!isValidFileType(sFileType)){
				_eFile.value="";
				alert(String.fomat("您输入了不可识别的文件类型，请重新输入！\n允许输入的文件类型包括:[{0}]",FILE_TYPES));
				return false;
			}
			return true;
		}
		// 上传文件
		function uploadFile(_eFile){
			var validate = validateFileType(_eFile);
			if(validate){
				document.getElementById('frmPost').submit();
			}
		}
		function imgPreView(_eMyFileInput){
			var sImgLocalPath = _eMyFileInput.value;
			var eMyFileTextInput = document.getElementById("MyFileText");
			eMyFileTextInput.value = sImgLocalPath;
		}
	//-->
	</script>
	<style type="text/css">
		.fileInput{width:45px;}
	</style>
</HEAD>
<BODY>
 <form id="frmPost" name="frmPost" action="./image_for_style_upload_dowith.jsp?InputId=<%=CMyString.URLEncode(sInputId)%>&ImageUploadPath=<%=CMyString.URLEncode(sImageUploadPath)%>" method="post" enctype="multipart/form-data">
	<span style="height:100%;width:100%;">
		
		<!-- 上传按钮 @ BEGIN -->
			<span style="width:100%;height:100%;cursor:pointer;background-color:#FFFFFF;margin:0px;padding:0px;vertical-align:middle;">
				<span style="padding:0px;margin:0px;vertical-align:middle;">
					<input type="text" id="MyFileText" class="fileText <%=CMyString.filterForHTMLValue(sDefClass)%>" value="<%=CMyString.filterForHTMLValue(sFileUrl)%>" readonly="true">
				</span>
				<span style="padding-left:5px;vertical-align:middle;">
					<input type="file" id="MyFile" name="MyFile" onchange="uploadFile(this)" class="fileInput" hidefocus="true">
					<img src="../images/zt-xj_an2.gif" border=0 alt=""/>
				</span>
			</span>
		<!-- 上传按钮 @ END -->
	</span>
</form>
</BODY>
</HTML>