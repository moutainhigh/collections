<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="java.io.File" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/file_upload_judgment.jsp"%>

<%
	//初始化（获取数据）

	RFC1867InputStream.POST_ENCODING = "UTF-8";
	FilesMan oFilesMan = FilesMan.getFilesMan();
	String sUploadTempPath = oFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest oUploadHelper = new RFC1867ServletRequest(request, sUploadTempPath);
	//request = oUploadHelper;
	
	//业务逻辑

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
		sErrorMsg = LocaleServer.getString("public_thumb_upload_dowith.jsp.label.error_import_file","传入的文件有误！");
	}else if (oFileHelper.getSize() <= 0){
		sErrorMsg = LocaleServer.getString("public_thumb_upload_dowith.jsp.label.import_file_content_isnull","传入的文件没有内容！");
	}else if(isForbidFileExt(oFileHelper.getFileName())){
		sErrorMsg	= CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.forbidfile", "系统禁止上传{0}格式的文件！"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
	}else{
		sSaveFilePath = oFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, oFileHelper.getFileType());
		oFileHelper.writeTo(new java.io.File(sSaveFilePath));
	}
	//获取文件的服务器端路径
	String sSaveFileLocalDir = "";
	String sSaveFileLocalPath = "";
	if(sSaveFilePath!=null){
		sSaveFileName = CMyFile.extractFileName(sSaveFilePath);
		sSaveFileHttpDir = oFilesMan.mapFilePath(sSaveFileName,FilesMan.PATH_HTTP);
		sSaveFileHttpPath = sSaveFileHttpDir+sSaveFileName;
		sSaveFileLocalDir = oFilesMan.mapFilePath(sSaveFileName,FilesMan.PATH_LOCAL);
		sSaveFileLocalPath = sSaveFileLocalDir + sSaveFileName;
	}

	//将文件复制到实际的目录下
	String sFileFlag = oFilesMan.getFileFlag(sSaveFileHttpPath);

	String sNewFileName = sSaveFileHttpPath;
	// 2 将文件拷贝到正式目录中
	if (sFileFlag != FilesMan.FLAG_PROTECTED) {
		sSaveFileName = oFilesMan.copyWCMFile(sSaveFileName,
				FilesMan.FLAG_PROTECTED);
		sSaveFileHttpDir = oFilesMan.mapFilePath(sSaveFileName,FilesMan.PATH_HTTP);
		sSaveFileHttpPath = sSaveFileHttpDir+sSaveFileName;
	}
	//结束
	out.clear();
%>
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE WCMAnt:param="public_pic_upload_dowith.jsp.uploadpicdowithpage">上传图片处理页面</TITLE>
</HEAD>
<BODY>
<script>
	function notifyParent(){
		var oParent = window.parent;
		if(oParent == null || oParent == this){
			alert("未定义Parent");
			return false;
		}
		// iframe所在的页面中定义dealWithUploadedFile，用来处理文件上传之后的一些处理逻辑，如显示图片等。
		if(!oParent.dealWithUploadedPicFile){
			alert("dealWithUploadedFile Interface!");
			return false;
		}
		oParent.dealWithUploadedPicFile("<%=CMyString.filterForJs(sSaveFileHttpPath)%>", "<%=CMyString.filterForJs(sSaveFileName)%>", "thumb");
	}
	<%
	if (sSaveFilePath !=null){
	%>
		notifyParent();
	<%
	}
	else{
		if (sErrorMsg == null){
			sErrorMsg = LocaleServer.getString("public_thumb_upload_dowith.jsp.label.error_import_file","传入的文件有误！");
		}
	%>
	top.window.Ext.Msg.alert("<%= sErrorMsg %>");
	<%
	}
	%>
	window.location.href="./public_pic_upload.jsp";
</script>
</BODY>
</HTML>