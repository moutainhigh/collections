<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE WCMAnt:param="public_thumb_upload.jsp.title">上传风格缩略图</TITLE>
	<BASE TARGET="_self">
	<style type="text/css">
	<!--
		body{
			margin:0px;
		}
		.fileInput{
			cursor:pointer;
			width:0px;
			position:absolute;
			filter:alpha(opacity=0);
			opacity:0;
		}
		/*ff*/
		@-moz-document url-prefix(){
			.fileInput{
				left:-170px;
			}
		}
		.sc {
			font-family: "宋体";
			font-size: 12px;
			color: #333333;
			text-decoration: none;
			line-height: 18px;
			font-weight: normal;
		}
		.sc1 {
			font-family: "宋体";
			font-size: 12px;
			color: #0063B8;
			text-decoration: none;
			line-height: 18px;
			font-weight: normal;
		}
		.fileInput{
			width:45px;
		}
	//-->
	</style>
</HEAD>

<BODY >
<script>
	var FILE_TYPES = "bmp,gif,ico,jpg,jpeg,png";//支持的图片格式
	// 获取文件类型
	function getFileType(_sFileName){
		var sFileName = _sFileName || "";
		if(!sFileName || sFileName == ""){
			top.window.Ext.Msg.alert("请输入类别图片地址！");
			return null;
		}
		var nPointIndex = sFileName.lastIndexOf(".");
		if(nPointIndex < 0){
			top.window.Ext.Msg.alert("无法识别您输入的文件类型，请重新输入！");
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
	function validateFileType(_sFileName){
		if(_sFileName==null||_sFileName==""){
			alert("请输入类别图片的地址");
			return false;
		}
		var sFileType = getFileType(_sFileName);
		if(sFileType==null){
			return false;
		}
		if(!isValidFileType(sFileType)){
			top.window.Ext.Msg.alert(String.fomat("您输入了不可识别的文件类型，请重新输入！\n允许输入的文件类型包括:[{0}]",FILE_TYPES));
			return false;
		}
		return true;
	}
	// 上传文件
	function uploadFile(_eFile){
		var validate = validateFileType(_eFile.value);
		if(validate){
			document.getElementById('frmPost').submit();
		}
	}
</script>
<form id="frmPost" name="frmPost" action="./public_thumb_upload_dowith.jsp" method="post" enctype="multipart/form-data">
	<span style="width:100%;height:100%;cursor:pointer;background-color:#FFFFFF;margin:0px;padding:0px;vertical-align:middle;">
				<span style="padding-left:8px;vertical-align:middle;">
					<input type="file" id="MyFile" name="MyFile" onchange="uploadFile(this)" class="fileInput" hidefocus="true">
					<img src="../images/zt-xj_an2.gif" border=0 alt=""/>
				</span>
			</span>
	<input type="file" id="MyFile" name="MyFile" onchange="uploadFile(this)" class="fileInput" hidefocus>
</form>
</BODY>
</HTML>