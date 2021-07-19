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
 *
 *  Parameters:
 *		see file_upload.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
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

	//ge gfc add 2008-4-28 如果已配置了大小限制，则在file_upload_dowith.jsp上加上一个大小限制参数
	//TODO 从配置中读取，单位为K
	int nRestrictFileSize = 0;
	int nRestrictImageSize =0;
	String sRestrictFileSize  = ConfigServer.getServer().getSysConfigValue("FILE_SIZE_LIMIT", "0");
	try{
		if(!CMyString.isEmpty(sRestrictFileSize)){
			nRestrictFileSize = Integer.parseInt(sRestrictFileSize);
		}
	}catch(Exception ex){
		//ex.printStackTrace();
		//just skip it
	}	
	String sRestrictImageSize = ConfigServer.getServer().getSysConfigValue("IMAGE_SIZE_LIMIT", "0");
	try{
		if(!CMyString.isEmpty(sRestrictImageSize)){
			nRestrictImageSize = Integer.parseInt(sRestrictImageSize);
		}
	}catch(Exception ex){
		//ex.printStackTrace();
		//just skip it
	}	
	//System.out.println("nRestrictFileSize=" + nRestrictFileSize + ", nRestrictImageSize=" + nRestrictImageSize);
	

	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE>TRS WCM<%= LocaleServer.getString("infoview.file.upload.title", "上传文件") %></TITLE>
  <script src="../../../app/js/runtime/myext-debug.js"></script>
</HEAD>
<BODY style="margin-left:8;margin-bottom:0;margin-top:0;font-size:9pt" bgcolor="<%=CMyString.filterForHTMLValue(sBgColor)%>">
<script>
function $(id){
	if(typeof id != 'string')return id;
	return document.getElementById(id);
}
var m_sAllowExt = null, m_nRestrictSize = 0, m_cb;
function init(_args, cb){
	m_cb = cb;
	m_sAllowExt = _args.AllowExt || "<%=CMyString.filterForJs(strAllowExt)%>";
	if(m_sAllowExt.length > 0) {
		$('spTip').innerHTML = m_sAllowExt;
		$('dvTip').style.display = '';
	}
	m_nRestrictSize = _args['InlineImg'] ? '<%=nRestrictImageSize%>' : '<%=nRestrictFileSize%>';
	if(m_nRestrictSize > 0) {
		$('spTipSize').innerHTML = m_nRestrictSize;
		$('dvTipSize').style.display = '';
		$('hdRestrictSize').value = m_nRestrictSize;
	}

	//$('dvEditor').innerHTML = '<OBJECT id="id_EditBox" CLASSID="clsid:D6641A7A-B6F8-4FC7-A382-624DDBAEF96F"  codeBase="WCMOffice.ocx#Version=-1,-1,-1,-1"></OBJECT>';
	
}
function valid(_strPath){
	var sAllowExt = m_sAllowExt;
	if(_strPath.length<=0){
		alert("没有选择文件！");
		return false;
	}
	if(!doValidate(_strPath)) {
		return false;
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
function doValidate(_strPath){
	var sAllowExt = m_sAllowExt;
	if(sAllowExt.length > 0 && !validFileExt(_strPath, sAllowExt)) {
		alert(String.format("只支持上传{0}格式的文件！",sAllowExt.toUpperCase()));
		$('frmPost').reset();
		return false;
	}

	//如果不是本地文件，则不做大小校验
	if(!isLocalFile(_strPath))
		return true;
	try{
		var objEditActive = $("id_EditBox");
		var nFileSize = objEditActive.GetFileSize(_strPath);
		nFileSize = parseInt(nFileSize);
		if(nFileSize == -1){
			alert("文件无效，请重新选择文件！");
			return false;
		}
		if(nFileSize > m_nRestrictSize * 1024){
			alert(String.format("文件大小[{0}K]大于最大限制[({1})K]，请重新选择文件！",Math.ceil(nFileSize/1024),m_nRestrictSize));
			return false;
		}
	}catch(err){}
	return true;
}

function isLocalFile(_str){
	var str = _str || "";
	if(!str || str.length <= 0){
		return false;
	}
	//wenyh@20060412 如果文件名称是[protocol://seraddr]形式,则认为不是本地文件
	var pattern = /\w+:\/{2,2}[^\/]\w*/i;

	return !pattern.test(str);
}
function notifyOnUploadFileOk(args){
	if(!m_cb)return;
	if(m_cb.callback)m_cb.callback(args);
	m_cb.close();
}
function notifyParentError(_sMsg){
}
</script>

<iframe src="" id="blank" name="blank" width="" height="" style="display:none"></iframe>
<form target="blank" name="frmPost" id="frmPost" action="file_upload_dowith.jsp?SelfControl=<%=CMyString.URLEncode(String.valueOf(nSelfControl))%>&ShowText=<%=CMyString.URLEncode(String.valueOf(nShowText))%>&InputName=<%=CMyString.URLEncode(strInputName)%>&BgColor=<%=CMyString.URLEncode(sBgColor)%>" onsubmit="return valid($('frmPost').MyFile.value);" style="margin-top:0; margin-bottom:0" method="post" enctype="multipart/form-data">
<table border="0" cellspacing="2" cellpadding="1" style="font-size: 12px;">
<tbody>
	<tr>
		<td style="display:<%=bShowText?"":"none"%>" WCMAnt:param="file_upload.pleaseselect">
			请选择文件后进行上传
		</td>
	</tr>
	<tr>
		<td>
			<span><input type="file" name="MyFile" onchange="doValidate(this.value);"></span>
			<span>
				<input type="submit" value="上传" style="display:<%=bSelfControl?"":"none"%>" WCMAnt:paramattr="value:file_upload.upload1">
				<input type="hidden" name="FrameName" id="hdFrameName" value="">
				<input type="hidden" name="RestrictSize" id="hdRestrictSize" value="">
			</span>		
		</td>
	</tr>
</tbody>
</table>
<span id="dvTip" style="display: none; width: 100%; padding: 3px; color: #010101">
	<span WCMAnt:param="file_upload.limittip">限定格式为&nbsp;<span id="spTip" style="color: blue; font-family: 'Courier New'"></span>&nbsp;的文件&nbsp;&nbsp;</span>
</span>
<span id="dvTipSize" style="display: none; width: 100%; padding: 3px; color: #010101">
	<span  WCMAnt:param="file_upload.limitsize">限定大小为&nbsp;<span id="spTipSize" style="color: red; font-family: 'Courier New'"></span>K&nbsp;的文件</span>
</span>
</form>
<div id="dvEditor" style="display: none"></div>
</BODY>
</HTML>