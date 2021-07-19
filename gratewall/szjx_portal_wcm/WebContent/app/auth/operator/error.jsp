<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isErrorPage="true"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CMyException"%>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %> 
<%@ page import="com.trs.infra.support.config.ConfigServer"%>
<!------- WCM IMPORTS END ------------>
<%! 
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
	//prevents caching at the proxy server
	response.setDateHeader("max-age", 0);
	response.setStatus(200);
	response.setHeader("TRSException", "true");
%>

<%
//why@2002-04-27: [1]增加站点根目录配置  [2]增加不折行处理
int nErrNo; //错误编号
String sErrType, sErrMsg, sErrDetail; //错误类型,错误信息,详细信息
String sRootPath = "/wcm"; //站点根目录 //why@2002-04-27:需要从系统配置中读取

if (exception instanceof CMyException) {
	CMyException myException = (CMyException) exception;
	nErrNo = myException.getErrNo();
	sErrType = myException.getErrNoMsg();
	sErrMsg = myException.getMyMessage();
	sErrDetail = myException.getStackTraceText();
} else {
	nErrNo = ExceptionNumber.ERR_UNKNOWN;
	sErrType = LocaleServer.getString("error.label.unknown", "未知");
	sErrMsg = LocaleServer.getString("operator.error.label.unknownError", "未知错误!");
	sErrDetail = CMyException.getStackTraceText(exception);
}
//将异常信息输出到后台
if(nErrNo == ExceptionNumber.ERR_USER_NOTLOGIN){
    System.out.println(sErrMsg);
}else{
	//将异常信息输出到后台
	//System.out.println(sErrDetail);
	//将异常信息输出到日志文件
	logger.error(sErrDetail);
}
sErrDetail = CMyString.transDisplay(sErrDetail);
if(nErrNo < 2000000) sErrMsg = "An error occurs in the system.";

//caohui@2003-01-14:处理不同异常的表现方式
String sUrl = null;
boolean bNotLogin = false;
if (nErrNo == ExceptionNumber.ERR_USER_NOTLOGIN) {//处理用户登录超时的问题
	response.setStatus(401);
	response.setHeader("TRSNotLogin", "true");
	bNotLogin = true;
	//sUrl = "../include/not_login.htm";
	//request.getRequestDispatcher(sUrl).forward(request,response);
}
String sShowDetailInfo = ConfigServer.getServer().getSysConfigValue("SHOW_ERROR_DETAILINFO", "false");
sErrDetail = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrDetail : LocaleServer.getString("error.jsp.label.syserror", "系统出现错误");
sErrMsg = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrMsg : LocaleServer.getString("error.jsp.label.syserror", "系统出现错误");
out.clear();
%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE WCMAnt:param="error.jsp.wcmsystemtipinfo">WCM系统提示信息</title>
	<LINK href="../../../console/style/style.css" rel="stylesheet" type="text/css">
	<SCRIPT LANGUAGE="JavaScript" src="../../../console/js/CTRSHashtable.js"></script>
	<SCRIPT LANGUAGE="JavaScript" src="../../../console/js/CTRSRequestParam.js"></script>
	<SCRIPT LANGUAGE="JavaScript" src="../../../console/js/CTRSAction.js"></script>
	<SCRIPT src="../../../console/js/CWCMDialogHead.js"></SCRIPT>
    <script type="text/javascript" src="../../js/runtime/myext-debug.js"></script>
	<script src="../../js/data/locale/include.js"></script>
	<LINK href="../../css/wcm-common.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var sRootPath = '<%=sRootPath%>';
	function copyToClipboard(){
		var sDetailMsg = $("msgForCopy").innerText;
		window.clipboardData.setData("Text", sDetailMsg);
		alert(wcm.LANG.INCLUDE_ALERT_1 || "已经复制到剪切板中!");
	}

	function doDispatch(_sUrl){
		window.top.location.href = _sUrl;
	}

	function gotoNotLoginMsg(){
		var sURL = "../include/not_login.htm";
		if(window.location.href.indexOf("/WCMV6/main3") < 0 
			&& window.location.href.indexOf("/WCMV6/") > 0){
			sURL = sRootPath + "/console/include/not_login.htm"; //TODO 从配置中取得
		}
		if(window.location.href.indexOf("/app/") > 0){
			sURL = sRootPath + "/console/include/not_login.htm?FromUrl=" + encodeURIComponent(top.location.href); //TODO 从配置中取得
		}
		//window.location.href = sURL;		
		window.top.location.href = sURL;
	}
	<%
		if(bNotLogin){
			out.println("gotoNotLoginMsg();");
		}
	%>
	function showDetail(){
		try{
		var objSpan = $("id_spanErrDetail");
		if( objSpan==null ) return;

		var bCurrShowed = ( objSpan.style.display=="inline" );
		var sDisplay, sTitle;
		if( bCurrShowed ){
			sDisplay = "none";
			sTitle = wcm.LANG.INCLUDE_TITLE_10 || "显示详细信息";
		}
		else{
			sDisplay = "inline";
			sTitle = wcm.LANG.INCLUDE_TITLE_11 || "隐藏详细信息";
		}

		objSpan.style.display = sDisplay;  //显示或隐藏
		
		//更改链接标题
		var objLink = $("id_linkShowDetail");
		if( objLink!=null ) {
			objLink.innerHTML = sTitle;
		}
		}catch(err){
			alert(err.stack);
		}
	}
	window._sessionId = window.setTimeout(function(){
		new Image().src = '../main/refresh.jsp?r='+new Date().getTime();
		window._sessionId = window.setTimeout(arguments.callee, 60000);
	}, 60000);
	window.onunload = function(){
		window.clearTimeout(window._sessionId);
	};
</script>
<style type="text/css">
#id_spanErrDetail{text-align:left;display:none;overflow:auto;padding:5px 20px;font-size:11pt;}
/*strict*/
.ext-strict .layout_center_container{top:100px;bottom:40px;}
.ext-strict .layout_center{left:0;right:0;}
/*ie6*/
.ext-ie6 .layout_container{padding-top:100px;padding-bottom:40px;}
/*all*/
.layout_north{height:100px;padding-top:20px;}
.layout_south{height:40px;}
.layout_container{background:#FFFFFF;}
.layout_center_container{background:#FFFFEF;}
div{
	 background-color:#ededed
}
</style>
</HEAD>

<BODY width="100%" height="100%" style="overflow:hidden;">
<div class="layout_container layout_bordercontainer">
	<div class="layout_north" align="center">
		<div style="background-color:#fff;width:90%;border:1px solid black">
		<IMG src="../../../images/error.gif" align="absmiddle" valign="top">
		<SPAN class="font_redbold style7"><span WCMAnt:param="error.jsp.tip">系统提示信息</span>: &nbsp;&nbsp;<%=CMyString.transDisplay(sErrMsg)%></SPAN> 
		(<A id="id_linkShowDetail" href="#" class="navigation_page_link" onclick="showDetail();return false;" WCMAnt:param="error.jsp.showDetail">显示详细信息</a>)<br>
		<span id="id_spanErrDetail" class="layout_center">
				<UL>
					<li><font color="#000000"><b WCMAnt:param="error.jsp.errNo">错误编号：</b>&nbsp;</font><%=nErrNo%></li>
					<li><font color="#000000"><b WCMAnt:param="error.jsp.errType">错误类型：</b>&nbsp;</font><%=sErrType%></li>
					<li><font color="#000000"><b WCMAnt:param="error.jsp.errDetail">详细信息：</b>&nbsp;</font><br>
					</li>
				</UL>
				<pre id="msgForCopy" name="msgForCopy" style='font-family:Arial'><%=sErrDetail%></pre>
			</span>
		</div>
	</div>
	<div class="layout_center_container" style="background-color:#ededed">
	<div id="id_spanErrDetail" class="layout_center">
		<UL>
			<li><font color="#000000"><b WCMAnt:param="error.jsp.errNo">错误编号：</b>&nbsp;</font><%=nErrNo%></li>
			<li><font color="#000000"><b WCMAnt:param="error.jsp.errType">错误类型：</b>&nbsp;</font><%=sErrType%></li>
			<li><font color="#000000"><b WCMAnt:param="error.jsp.errDetail">详细信息：</b>&nbsp;</font><br>
			</li>
		</UL>
		<pre id="msgForCopy" name="msgForCopy" style='font-family:Arial'><%=sErrDetail%></pre>
	</div>
	</div>
	<div class="layout_south" align="center">
		<INPUT type="button" id="btnCopy" name="btnCopy"
			onclick="copyToClipboard()" value="<%=LocaleServer.getString("error.label.copyToClipboard", "复制到剪切板")%>" class="inputbutton" style="display:none">
		&nbsp;&nbsp;&nbsp;
		<INPUT type="button" id="btnClose" name="btnClose" onclick="window.close()"
			value="<%=LocaleServer.getString("error.label.close", "关闭窗口")%>" class="inputbutton" style="display:none">
		<script language="javascript">
		<!--
			if(top==window){
				$('btnClose').style.display = '';
			}
			if(window.clipboardData){
				$('btnCopy').style.display = '';
			}
		//-->
		</script>
	</div>
</div>
</BODY>
</HTML>