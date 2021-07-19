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
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<%!boolean IS_DEBUG = true;%>
<%
//4.初始化（获取数据）
	int nSelfControl = currRequestHelper.getInt("SelfControl", 1);
	int nShowText = currRequestHelper.getInt("ShowText", 1);
	boolean bSelfControl = (nSelfControl == 1);
	boolean bShowText = (nShowText == 1);
	String strInputName = CMyString.showNull(currRequestHelper.getString("InputName"));
	String strAllowExt = CMyString.filterForJs(CMyString.transDisplay(currRequestHelper.getString("AllowExt")));
	String sBgColor = CMyString.showNull(currRequestHelper.getString("BgColor"));
	String sType = CMyString.showNull(currRequestHelper.getString("Type"));

//5.权限校验

//6.业务代码	
	int nRestrictLogoSize =0;
	String sRestrictLogoSize = null;
	if(!CMyString.isEmpty(sType)){
		sRestrictLogoSize= ConfigServer.getServer().getSysConfigValue(sType.toUpperCase(), "0");
	}
	try{
		if(!CMyString.isEmpty(sRestrictLogoSize)){
			nRestrictLogoSize = Integer.parseInt(sRestrictLogoSize);
		}
	}catch(Exception ex){
		//ex.printStackTrace();
		//just skip it
	}	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE><%=LocaleServer.getString("fileupload.label.title", "TRS WCM 上传文件")%></TITLE>
  <BASE TARGET="_self">
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT src="../js/wcm52/CTRSAction.js"></SCRIPT>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!-- dialog  Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT> 
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- dialog  End -->
</HEAD>

<BODY style="margin-left:0;margin-bottom:0;margin-top:0;font-size:9pt" bgcolor="<%=CMyString.filterForHTMLValue(sBgColor)%>">
<script>
function $(id){
	if(typeof id != 'string')return id;
	return document.getElementById(id);
}
var m_sAllowExt = null, m_nRestrictSize = 0;
function init(){
	m_sAllowExt = "<%=CMyString.filterForJs(strAllowExt)%>";
	if(m_sAllowExt.length > 0) {
		$('spTip').innerHTML = m_sAllowExt;
		$('dvTip').style.display = '';
	}
	m_nRestrictSize = '<%=nRestrictLogoSize%>';
	if(m_nRestrictSize > 0) {
		$('spTipSize').innerHTML = m_nRestrictSize;
		$('dvTipSize').style.display = '';
		$('hdRestrictSize').value = m_nRestrictSize;
	}
}
function valid(_strPath){
	var sAllowExt = "<%=CMyString.filterForJs(strAllowExt)%>";
	sAllowExt = sAllowExt.toUpperCase();

	sAllowExt = sAllowExt.toUpperCase();
	var aShowAllowExt = sAllowExt.split(',');
	var sShowAllowExt = aShowAllowExt[0];
	if(aShowAllowExt.length > 2){
		for(var i=1; i<aShowAllowExt.length;i++)
			if(i< (aShowAllowExt.length - 1))
				sShowAllowExt += ', '+ aShowAllowExt[i];
			else
				sShowAllowExt+= ' or ' + aShowAllowExt[i];
	}else{
		sShowAllowExt = aShowAllowExt.length == 2 ? (aShowAllowExt[0] + ' or ' + aShowAllowExt[1]) : sShowAllowExt;
	}
	

	if(_strPath.length<=0){
		Ext.Msg.alert(String.format("没有选择文件!支持{0}格式的文件",sShowAllowExt));
		return false;
	}
	
	var pattern_path = /^.+\.(<%=CMyString.encodeForRegExp(CMyString.filterForJs(strAllowExt)).replaceAll(",", "|")%>)$/i;
	if(!pattern_path.test(_strPath)){
		Ext.Msg.alert(String.format("文件格式有误！请选择{0}格式的文件！",sShowAllowExt));
		return false;
	}
	
	//站点HTTP格式检验
	var ptnRootDomain = /^(http:\/\/|https:\/\/|ftp:\/\/)./;
	var strRootDomain = _strPath;
	strRootDomain = strRootDomain.toLowerCase();
	if(strRootDomain.match(ptnRootDomain)) {
		Ext.Msg.alert("请不要输入http(s)或者ftp等远程访问地址！");
		return false;
	}

	if(sAllowExt.length>0) {
		if(!validFileExt(_strPath, sAllowExt)) {
			Ext.Msg.alert(String.format("只支持上传{0}格式的文件！",sShowAllowExt));
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
	Ext.Msg.alert(_sAlertion);
}
window.notifyOnUploadFileError = handleAlertion;
if(parent.notifyParent2CloseMe) window.notifyParent2CloseMe = parent.notifyParent2CloseMe;
if(parent.notifyParentOnFinished) window.notifyParentOnFinished = parent.notifyParentOnFinished;
function addFile(){
	if(parent != window && parent.addFile){
		parent.addFile.apply(window, arguments);
	}
}
</script>


<form name="frmPost" id="frmPost"  action="file_upload_dowith.jsp?SelfControl=<%=CMyString.URLEncode(String.valueOf(nSelfControl))%>&ShowText=<%=CMyString.URLEncode(String.valueOf(nShowText))%>&AllowExt=<%=CMyString.URLEncode(strAllowExt)%>&InputName=<%=CMyString.URLEncode(strInputName)%>&BgColor=<%=CMyString.URLEncode(sBgColor)%>" onsubmit="return valid(document.frmPost.MyFile.value);" style="margin-top:0; margin-bottom:0" method="post" enctype="multipart/form-data" target="upd-frm">
	<span style="display:<%=bShowText?"":"none"%>;padding-right:6px;"><%=LocaleServer.getString("fileupload.label.selectfile", "选择文件:")%></span><input type="file" name="MyFile" id="fileUpload">&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:<%=bSelfControl?"":"none"%>"><input type="submit" name="MySubmit" value="<%=LocaleServer.getString("fileupload.label.value", "上传")%>"></span>
	<input type="hidden" name="RestrictSize" id="hdRestrictSize" value="">
</form>
<div id="dvTip" style="display: none; width: 100%; padding-left:57px;color: #010101">
	<span WCMAnt:param="file_upload.limittip">限定格式为&nbsp;<span id="spTip" style="color: blue; font-family: 'Courier New'"></span>&nbsp;的文件&nbsp;&nbsp;</span>
</div>
<div id="dvTipSize" style="display: none; width: 100%;padding-left:57px;color: #010101">
	<span  WCMAnt:param="file_upload.limitsize">限定大小为&nbsp;<span id="spTipSize" style="color: red; font-family: 'Courier New'"></span>K&nbsp;的文件</span>
</div>
<iframe src="../include/blank.html" width="" height="" style="display:none;" name="upd-frm"></iframe>
</BODY>
</HTML>