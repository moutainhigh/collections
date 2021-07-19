<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="java.io.File" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/file_upload_judgment.jsp"%>
<%@include file="include/picture_check.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 初始化（获取数据）
	RFC1867InputStream.POST_ENCODING = "UTF-8";
	FilesMan oFilesMan = FilesMan.getFilesMan();
	String sUploadTempPath = oFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest oUploadHelper = new RFC1867ServletRequest(request, sUploadTempPath);
	//request = oUploadHelper;
	
	// 2 业务逻辑
	//文件在服务器上的绝对路径，包含文件名称，
	//如："D:/TRS/TRSPortal61B1085New/WCMData/upload/U0200908/U020090813/U020090813324428901476.gif"
	String sSaveFilePath = null;

	// 文件在上传到服务器之后的新的文件名称，如：U020090813324428901476.gif
	String sSaveFileName = "";

	// 文件的相对目录，用于http访问，不包含文件名称，如：/upload/U0200908/U020090813/
	String sSaveFileHttpDir = "";

	// 文件的相对路径，用于http访问，包含文件名称，如：/upload/U0200908/U020090813/U020090813324428901476.gif
	String sSaveFileHttpPath = "";
	String sErrorMsg = null;
	RFC1867FormPart oFileHelper = oUploadHelper.getFormPart("MyFile");
	if (oFileHelper == null){
		sErrorMsg = LocaleServer.getString("file_upload_dowith_pic.jsp.label.error2import_file", "传入的文件有误！");
	}else if (oFileHelper.getSize() <= 0){
		sErrorMsg = LocaleServer.getString("file_upload_dowith_pic.jsp.label.import_files_content_is_null", "传入的文件没有内容！");
	}else if(isForbidFileExt(oFileHelper.getFileName())){
		sErrorMsg	= CMyString.format(LocaleServer.getString("file_upload_dowith_pic.jsp.forbid_upload_file", "系统禁止上传[{0}]格式的文件!"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
	}else{
		sSaveFilePath = oFilesMan.getNextFilePathName(FilesMan.FLAG_WEBFILE, oFileHelper.getFileType());
		oFileHelper.writeTo(new java.io.File(sSaveFilePath));
	}
	//获取文件的服务器端路径
	boolean bLegalPic = false;
	if(sSaveFilePath!=null){
		bLegalPic = isLegalPic(sSaveFilePath);
		if(bLegalPic){
			sSaveFileName = CMyFile.extractFileName(sSaveFilePath);
			sSaveFileHttpDir = oFilesMan.mapFilePath(sSaveFileName,FilesMan.PATH_HTTP);
			sSaveFileHttpPath = sSaveFileHttpDir+sSaveFileName;
		}
	}
	if(IS_DEBUG){
		System.out.println("sSaveFileName:"+sSaveFileName);
		System.out.println("sSaveFileHttpDir:"+sSaveFileHttpDir);
		System.out.println("sSaveFileHttpPath:"+sSaveFileHttpPath);
	}
	//结束
	out.clear();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>图片缩略图</title>
	</head>
	<body>
	</body>
		<script src="js/jquery-1.7.2.min.js"></script>
				<script src="js/iframe_public.js"></script>
		<!-- 引入crashboard的组件js文件 -->
		<script src="js/jquery.ui.crashboard.js"></script>
		<script>
		if(<%=bLegalPic%>){
			if(top.window.CrashBoard!=null){
				top.window.CrashBoard.get("createMicroblog").onResize("574px","400px");
			}
			$(window.parent.document).find("#img_ViewThumb").attr("src","<%=sSaveFileHttpPath%>");
			$(window.parent.document).find("#imgContainer").css("display","inline");
			$(window.parent.document).find(".deletePicture").css("display","inline");
			$(window.parent.document).find("#picture").val("<%=sSaveFileName%>");
			$(window.parent.document).find('#MyFile').replaceWith('<input type="file" name="MyFile" size="1" class="fileInput" style="position: absolute; width: 45px; cursor: pointer; left: 58px;" hideFocus="true" id="MyFile" />');
		}else{
			alert("非常抱歉！上传的文件不合法！现仅支持JPG/JPEG/GIF/PNG格式图片，图片大小必须在1k ~ 2M之间！");
			$(window.parent.document).find('#MyFile').replaceWith('<input type="file" name="MyFile" size="1" style="position: absolute; width: 45px; cursor: pointer; left: 58px;" class="fileInput" hideFocus="true" id="MyFile" />');
		}
		</script>
</html>
