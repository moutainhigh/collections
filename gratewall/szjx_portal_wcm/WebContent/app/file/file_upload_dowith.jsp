<%
/** Title:			file_upload.jsp
 *  Description:
 *		WCM5.2 文件上传处理页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2003-12-24
 *  Vesion:			1.0
 *  Last EditTime:	2003-12-24 2003-12-24
 *	Update Logs:
 *		CH@2003-12-24 created file
 *
 *  Parameters:
 *		see file_upload.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.bbs.plad.upload.*" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %> 
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.infra.util.CMyFile" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/file_upload_judgment.jsp"%>
<%
//4.初始化（获取数据）
	String strInputName = CMyString.showNull(request.getParameter("InputName"));
	String strSelfControl = CMyString.showNull(request.getParameter("SelfControl"));
	String strShowText = CMyString.showNull(request.getParameter("ShowText"));
	String strAllowExt = CMyString.showNull(request.getParameter("AllowExt"));
	String sBgColor = CMyString.showNull(request.getParameter("BgColor"));

	RFC1867InputStream.POST_ENCODING = "UTF-8";

	FilesMan	aFilesMan	= FilesMan.getFilesMan();
	String sUploadTempPath	= aFilesMan.getPathConfigValue(FilesMan.FLAG_SYSTEMTEMP, FilesMan.PATH_LOCAL);
	RFC1867ServletRequest	oUploadHelper	= new RFC1867ServletRequest(request, sUploadTempPath);
	//request	= oUploadHelper;
//5.权限校验

//6.业务代码	
	String sSaveFile = null;
	String sErrorMsg = null;
	RFC1867FormPart	oFileHelper	= null;
	String strShowName = null;
	String sRestrictSize = null;
	try{
		oFileHelper	= oUploadHelper.getFormPart("MyFile");

		if (oFileHelper.getSize() <= 0)
		{
			sErrorMsg	= LocaleServer.getString("file.upload.dowith.filenocontent", "传入的文件没有内容!");
		}else if(isForbidFileExt(oFileHelper.getFileName())){
			sErrorMsg	=CMyString.format(LocaleServer.getString("file_upload_dowith.jsp.sysdenyuploadfile", "系统禁止上传{0}格式的文件！"),new String[]{CMyFile.extractFileExt(oFileHelper.getFileName()).toUpperCase()});
		}
		else
		{
			try{
				sRestrictSize = CMyString.showNull(oUploadHelper.getParameter("RestrictSize"));
				//System.out.println("RestrictSize=" + sRestrictSize + ", size=" + oFileHelper.getSize());
				long nRestrictSize = 0;
				if(!CMyString.isEmpty(sRestrictSize)
					&& (nRestrictSize = Long.parseLong(sRestrictSize)) > 0
					&& (oFileHelper.getSize() > nRestrictSize*1000)) {
					sErrorMsg = CMyString.format(LocaleServer.getString("file_upload_dowith.filesize.toolarge","上传文件大小超过系统配置的最大值 {0}K，请重新选择文件！"),new String[]{sRestrictSize});
					throw new Exception(sErrorMsg);
				}
			}catch(Exception ex){
				//ex.printStackTrace();
				throw ex;
				//just skip it
			}
			sSaveFile = aFilesMan.getNextFilePathName(FilesMan.FLAG_UPLOAD, oFileHelper.getFileType());
			oFileHelper.writeTo(new java.io.File(sSaveFile));
		}
		strShowName = CMyFile.extractFileName(oFileHelper.getFileName(), "/");
		strShowName = CMyFile.extractFileName(strShowName, "\\");
	}catch(Throwable error){
		// sErrorMsg	+= LocaleServer.getString("file.upload.dowith.fileisworry", "传入的文件有误!");		
	}

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE><%=LocaleServer.getString("fileupload_dowith.label.titlev61", "TRS WCM 上传文件处理页面")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
  
	<META NAME="Generator" CONTENT="EditPlus">
	<META NAME="Author" CONTENT="">
	<META NAME="Keywords" CONTENT="">
	<META NAME="Description" CONTENT="">
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT src="../js/wcm52/CTRSAction.js"></SCRIPT>
</HEAD>

<BODY style="margin-left:8;margin-bottom:0;margin-top:0;font-size:9pt">
<script>
function notifyParent(){
	var oParent = window.parent;
	if(oParent == null || oParent == this){
		CTRSAction_alert("<%=LocaleServer.getString("fileupload_dowith.label.undefined", "未定义Parent")%>");
		return false;
	}

	var args = ["<%=(sSaveFile ==null?"":CMyString.filterForJs(CMyFile.extractFileName(sSaveFile)))%>", "<%=(sSaveFile ==null?"":CMyString.filterForJs(strShowName))%>", "<%=CMyString.filterForJs(strInputName)%>"];

	//来自于Crash Dialog
	if(oParent.notifyParent2CloseMe){
		window.parent.notifyParent2CloseMe(window);
		window.parent.notifyParentOnFinished(window, args);
		return;
	}

	//内嵌使用
	if(!oParent.addFile){
		CTRSAction_alert("<%=LocaleServer.getString("fileupload_dowith.label.add", "没有实现AddFile Interface!")%>");
		return false;
	}
	oParent.addFile(args[0], args[1], args[2]);
	window.location.href="file_upload.jsp?SelfControl=<%=CMyString.URLEncode(strSelfControl)%>&ShowText=<%=CMyString.URLEncode(strShowText)%>&AllowExt=<%=CMyString.URLEncode(strAllowExt)%>&InputName=<%=CMyString.URLEncode(strInputName)%>&BgColor=<%=CMyString.URLEncode(sBgColor)%>";
}

function notifyParentError(_sMsgError){
	var oParent = window.parent;
	if(oParent == null || oParent == this){
		CTRSAction_alert("<%=LocaleServer.getString("fileupload_dowith.label.undefined", "未定义Parent")%>");
		return false;
	}
	if(!oParent.notifyOnUploadFileError){
		return false;
	}
	
	oParent.notifyOnUploadFileError(_sMsgError);

}


<%
//System.out.println(sSaveFile);
	if (sSaveFile !=null)
	{
%>
	notifyParent();
<%
	}
	else
	{
		if (sErrorMsg == null)
		{
			sErrorMsg	= LocaleServer.getString("file.upload.dowith.fileisworry", "传入的文件有误!");
		}
%>
	//CTRSAction_alert("<%= sErrorMsg %>");
	notifyParentError('<%= CMyString.filterForJs(sErrorMsg) %>');
	window.location.href="file_upload.jsp?SelfControl=<%=CMyString.URLEncode(strSelfControl)%>&ShowText=<%=CMyString.URLEncode(strShowText)%>&AllowExt=<%=CMyString.URLEncode(strAllowExt)%>&InputName=<%=CMyString.URLEncode(strInputName)%>";
<%
	}
%>
</script>

</BODY>
</HTML>