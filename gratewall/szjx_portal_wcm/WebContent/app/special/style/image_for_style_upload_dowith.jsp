<%
/** Title:				image_for_style_upload_dowith.jsp
 *  Description:
 *        上传组件，处理图片文件的上传。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年5月5日
 *  Vesion:				1.0
 *  Last EditTime:	none
 *  Update Logs:	none
 *  Parameters:		
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page import="com.trs.bbs.plad.upload.*" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="java.io.File" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<%@include file="../../include/file_upload_judgment.jsp"%>

<%
	// 1、获取所需数据
	String sInputId = CMyString.showNull(request.getParameter("InputId"));// 上传组件的input的Id
	String sImageUploadPath = CMyString.showNull(request.getParameter("ImageUploadPath"),"");// 图片文件将来要存储的物理路径（不包括文件名称）
	RFC1867ServletRequest oUploadHelper = new RFC1867ServletRequest(request, "");
	RFC1867FormPart oFileHelper = oUploadHelper.getFormPart("MyFile");
	RFC1867InputStream.POST_ENCODING = "UTF-8";
	String sErrorMsg = "";// 记录出错信息
	String sSaveFilePath = null;
	if(isForbidFileExt(oFileHelper.getFileName())){
		sErrorMsg	= CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.forbidfile", "系统禁止上传{0}格式的文件！"), new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
	}else{
		// 2、将文件写入临时目录
		FilesMan oFilesMan = FilesMan.getFilesMan();
		String sTemSaveFilePath = oFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, oFileHelper.getFileType());
		oFileHelper.writeTo(new java.io.File(sTemSaveFilePath));

		// 3、将文件拷贝到最终的保存目录
		if(!CMyFile.fileExists(sImageUploadPath)){ 
			CMyFile.makeDir(sImageUploadPath, true);//
		}
		String sImageName = CMyFile.extractFileName(sTemSaveFilePath,null);
		CMyFile.copyFile(sTemSaveFilePath,sImageUploadPath + sImageName,false);
		sSaveFilePath = sImageUploadPath + sImageName;// 图片的最终保存地址

		//获取文件的相对路径
		if(sSaveFilePath!=null){
			sSaveFilePath = sSaveFilePath.replaceAll("\\\\", "/");
			if(sSaveFilePath.indexOf("/style_common/")>-1){
				sSaveFilePath = ".."+sSaveFilePath.substring(sSaveFilePath.indexOf("/style_common/"));
			}
		}
	}
	//结束
	out.clear();
%>
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE WCMAnt:param="image_for_style_upload_dowith.jsp.title">上传图片处理页面</TITLE>
</HEAD>
<BODY>
<script>
	function $alert(_sMsg){
		if(top.window.Ext&&top.window.Ext.Msg&&top.window.Ext.Msg.alert){
			top.window.Ext.Msg.alert(_sMsg);
		}else{
			alert(_sMsg);
		}
	}
	function notifyParent(){
		var oParent = window.parent;
		if(oParent == null || oParent == this){
			$alert("未定义Parent");
			return false;
		}
		// iframe所在的页面中定义dealWithUploadedFile，用来处理文件上传之后的一些处理逻辑，如显示图片等。
		if(!oParent.dealWithUploadedImageFile){
			$alert("dealWithUploadedFile Interface!");
			return false;
		}
		oParent.dealWithUploadedImageFile("<%=CMyString.filterForJs(sSaveFilePath)%>", "<%=CMyString.filterForJs(sInputId)%>");
	}
	
	<%
		if(!"".equals(sErrorMsg)){
	%>
		$alert("<%= sErrorMsg %>");
	<%
		}
	%>
	<%
	if (sSaveFilePath !=null&&"".equals(sErrorMsg)){
	%>
		notifyParent();
	<%
	}else{
		if("".equals(sErrorMsg)){
			sErrorMsg = LocaleServer.getString("image_for_style_upload_dowith.jsp.label.error_upload_file", "传入的文件有误！");
		}
	%>
	$alert("<%= sErrorMsg %>");
	<%
	}
	%>
	window.location.href="./image_for_style_upload.jsp?InputId=<%=CMyString.filterForJs(sInputId)%>&FileUrl="+encodeURIComponent("<%=CMyString.filterForJs(sSaveFilePath)%>")+"&ImageUploadPath="+encodeURIComponent("<%=CMyString.filterForJs(sImageUploadPath)%>");
</script>
</BODY>
</HTML>