<%
/** Title:			file_upload.jsp
 *  Description:
 *		WCM5.2 文件上传页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2003-12-24
 *  Vesion:			1.0
 *  Last EditTime:	2003-12-24 2003-12-24
 *	Update Logs:
 *		CH@2003-12-24 created file
 *		gfc@2006-11-21 10:49 增加响应提示信息的判断函数 handleAlertion
 *  Parameters:
 *		see file_upload.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS END ------------>

<%!boolean IS_DEBUG = true;%>
<%
//4.初始化（获取数据）
	int nSelfControl = currRequestHelper.getInt("SelfControl", 1);
	int nShowText = currRequestHelper.getInt("ShowText", 1);
	boolean bSelfControl = (nSelfControl == 1);
	boolean bShowText = (nShowText == 1);
	String strInputName = CMyString.showNull(currRequestHelper.getString("InputName"));
	String strAllowExt = CMyString.showNull(currRequestHelper.getString("AllowExt"));
	String sBgColor = CMyString.showNull(currRequestHelper.getString("BgColor"));

//5.权限校验

//6.业务代码	
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE><%=LocaleServer.getString("file.upload.title", "TRS WCM 上传文件")%></TITLE>
  <BASE TARGET="_self">
<SCRIPT src="../js/CTRSRequestParam.js"></SCRIPT>
<SCRIPT src="../js/CTRSAction.js"></SCRIPT>
</HEAD>

<BODY style="margin-left:8;margin-bottom:0;margin-top:0;font-size:9pt" bgcolor="<%=CMyString.filterForHTMLValue(sBgColor)%>">
<script>
function valid(_strPath){
	var sAllowExt = "<%=CMyString.filterForJs(strAllowExt)%>";
	if(_strPath.length<=0){
		handleAlertion(CMyString.format(LocaleServer.getString("file_upload.not.selected","没有选择文件！（支持{0}格式）"),new String[]{sAllowExt.toUpperCase()}));
		return false;
	}
	
	var pattern_path = /^[a-zA-Z]+:|\\\\/g;
	if(!pattern_path.test(_strPath)){
		handleAlertion(CMyString.format(LocaleServer.getString("file_upload.wrongPath","文件路径有误！请选择{0}格式的文件！"),new String[]{sAllowExt.toUpperCase()}));
		return false;
	}
	
	//站点HTTP格式检验
	var ptnRootDomain = /^(http:\/\/|https:\/\/|ftp:\/\/)./;
	var strRootDomain = _strPath;
	strRootDomain = strRootDomain.toLowerCase();
	if(strRootDomain.match(ptnRootDomain)) {
		handleAlertion(LocaleServer.getString("file_upload.wrong.address","请不要输入http(s)或者ftp等远程访问地址！"));
		return false;
	}

	if(sAllowExt.length>0) {
		if(!validFileExt(_strPath, sAllowExt)) {
			handleAlertion(CMyString.format(LocaleServer.getString("file_upload.support","只支持上传{0}格式的文件！"),new String[]{sAllowExt.toUpperCase()}));
			return false;
		}
	}

	return true;
}

function validFileExt(_strPath, _strExts) {
	var arrayTemp = _strPath.split(".");
	if(arrayTemp.length<2) {
		return false;
	}
	var sFileExt = arrayTemp[arrayTemp.length-1].toLowerCase();
	arrayTemp = _strExts.split(",");
	var bResult = false;
	for(var i=0; i<arrayTemp.length; i++) {
		if(arrayTemp[i].toLowerCase() == sFileExt) {
			bResult = true;
		}
	}
	return bResult;
}

function handleAlertion(_sAlertion){
	if(window.parent && window.parent.doAlert) {
		window.parent.doAlert(_sAlertion);
		return;
	}
	//else
	CTRSAction_alert(_sAlertion);
}
</script>


<form name="frmPost" action="file_upload_dowith.jsp?SelfControl=<%=CMyString.URLEncode(String.valueOf(nSelfControl))%>&ShowText=<%=CMyString.URLEncode(String.valueOf(nShowText))%>&AllowExt=<%=CMyString.URLEncode(strAllowExt)%>&InputName=<%=CMyString.URLEncode(strInputName)%>&BgColor=<%=CMyString.URLEncode(sBgColor)%>" onsubmit="return valid(document.frmPost.MyFile.value);" style="margin-top:0; margin-bottom:0" method="post" enctype="multipart/form-data">
	<span style="display:<%=bShowText?"":"none"%>" WCMAnt:param="file_upload.selected.doc">选择文件：&nbsp;</span><input type="file" name="MyFile" id="fileUpload">&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:<%=bSelfControl?"":"none"%>"><input type="submit" name="MySubmit" WCMAnt:param="file_upload.selected.doc" value="上传" ></span>
</form>
</BODY>
</HTML>