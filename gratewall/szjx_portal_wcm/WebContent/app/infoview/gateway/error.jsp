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
	response.setStatus(500);
 
%>

<%
//why@2002-04-27: [1]增加站点根目录配置  [2]增加不折行处理
int nErrNo; //错误编号
String sErrType, sErrMsg, sErrDetail; //错误类型，错误信息，详细信息
String sRootPath = "/wcm"; //站点根目录 //why@2002-04-27:需要从系统配置中读取

if (exception instanceof CMyException) {
	CMyException myException = (CMyException) exception;
	nErrNo = myException.getErrNo();
	sErrType = myException.getErrNoMsg();
	sErrMsg = myException.getMyMessage();
	sErrDetail = myException.getStackTraceText();
} else {
	nErrNo = ExceptionNumber.ERR_UNKNOWN;
	sErrType = LocaleServer.getString("error.jsp.label.unknown","未知");
	sErrMsg =  LocaleServer.getString("error.jsp.label.unknownerror","未知错误！");
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

//caohui@2003-01-14:处理不同异常的表现方式
String sUrl = null;
if (nErrNo == ExceptionNumber.ERR_USER_NOTLOGIN) {//处理用户登录超时的问题
	response.setStatus(401);
	sUrl = "../../include/not_login.htm";
	//request.getRequestDispatcher(sUrl).forward(request,response);
}
String sShowDetailInfo = ConfigServer.getServer().getSysConfigValue("SHOW_ERROR_DETAILINFO", "false");
sErrDetail = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrDetail : LocaleServer.getString("error.jsp.label.occurerror","系统出现错误");
sErrMsg = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrMsg : LocaleServer.getString("error.jsp.label.occurerror","系统出现错误");
out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="error.jsp.wcmsysinfo">TRS WCM系统提示信息::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript" src="../../js/CTRSHashtable.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../js/CTRSRequestParam.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../js/CTRSAction.js"></script>
<SCRIPT src="../../js/CWCMDialogHead.js"></SCRIPT>
<script language="javascript">
	var oTransitionPage = document.all("TransitionPage");
	if(oTransitionPage && oTransitionPage.style){
		oTransitionPage.style.display = "none";
	}
	// resizing the window
	//var m_nRawHeight = document.body.clientHeight;
	//var m_nAdjustedHeight = 250;
	//window.onload = function(){
		//resizeTo(600, 500);
	//}
	
	function copyToClipboard(){
		var sDetailMsg = document.all("msgForCopy").innerText;
		window.clipboardData.setData("Text", sDetailMsg);
		alert(wcm.LANG.error_3000 || "已经复制到剪切板中！");
	}

	function doDispatch(_sUrl){
		window.top.location.href = _sUrl;
	}
		
	<%
		if(sUrl != null){
			out.println("doDispatch(\""+sUrl+"\");");
		}
	%>
	
	var dialogHeight = window.top.dialogHeight;
	var defaultHeight = "400 px";
	var dialogWidth = window.top.dialogWidth;
	var defaultWidth = "500 px";
	function showDetail( ){
		var objSpan = document.getElementById("id_spanErrDetail");
		if( objSpan==null ) return;

		var bCurrShowed = ( objSpan.style.display=="inline" );
		var sDisplay, sTitle;
		if( bCurrShowed ){
			sDisplay = "none";
			sTitle = wcm.LANG.error_4000 || "显示详细信息";
			if(window.top.dialogHeight && window.top.dialogHeight > dialogHeight) {
				window.top.dialogHeight = dialogHeight;
			}
			if(window.top.dialogWidth && window.top.dialogWidth > dialogWidth) {
				window.top.dialogWidth = dialogWidth;
			}

			// resizing the window
			//window.resizeTo(document.body.clientWidth, m_nAdjustedHeight);
		}
		else{
			sDisplay = "inline";
			sTitle = wcm.LANG.error_5000 || "隐藏详细信息";
			if(window.top.dialogHeight && window.top.dialogHeight < defaultWidth) {
				window.top.dialogHeight = defaultHeight;
			}
			if(window.top.dialogWidth && window.top.dialogWidth < defaultWidth) {
				window.top.dialogWidth = defaultWidth;
			}
			// resizing the window
			//window.resizeTo(document.body.clientWidth, m_nRawHeight);
		}

		objSpan.style.display = sDisplay;  //显示或隐藏
		
		//更改链接标题
		var objLink = document.getElementById("id_linkShowDetail");
		if( objLink!=null ) {
			objLink.innerText = sTitle;
		}
	}	

	function getHeight(){
		var spanHeight = (document.body.clientHeight - document.all.tdinfo.clientHeight) - 120;
		if (spanHeight >= 0) return spanHeight;
		else 	return 1;
	}
</script>
</HEAD>

<BODY width="100%" height="100%" style="overflow:hidden">
<script src="../../js/CRunningProcessBar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
RunningProcessBar.close();
</SCRIPT>
<iframe id="refreshFrame" src="" style="display:none;" width=0 height=0></iframe>

<SCRIPT LANGUAGE="JavaScript">
<!--
	function setSessionActive(){
		window.refreshFrame.location.href = "info.html";
		//alert("end");
	}
	window.setTimeout("setSessionActive();", 100000);
//-->
</SCRIPT>

<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="0">
	<TR>
		<SCRIPT src="../../js/CWCMDialogHead.js"></SCRIPT>
		<TD height="25"><SCRIPT LANGUAGE="JavaScript">
			WCMDialogHead.draw(wcm.LANG.error_6000 || "系统信息",true);
		</SCRIPT></TD>
	</TR>
	<TR> 
		<TD align="center" valign="top" class="tanchu_content_td">
		<div width="100%" bgcolor="a6a6a6">
		<table width="98%" cellpadding="0" cellspacing="1" bgcolor="gray">
			<tr>
				<td valign="top">
				<TABLE id = tbinfo width="100%" height="100%" bgcolor="#FFFFFF" align="center"
					cellpadding="3" cellspacing="0" bordercolor="red" border="0">
					<TR>
						<TD  width="100%" id=tdinfo align="center" valign="top"><IMG src="../images/error.gif" align="absmiddle" valign="top"><SPAN class="font_redbold style7"> 
						<%
							out.println(CMyString.format(LocaleServer.getString("error.jsp.sysmessage", "系统提示信息:{0}"), new String[]{CMyString.transDisplay(sErrMsg)}));
						%>
						</SPAN> （<A id="id_linkShowDetail" href="#"
							class="navigation_page_link" onclick="showDetail();return false;" WCMAnt:param="error.jsp.showdetails">显示详细信息</A>）</TD>

					</TR>
					<TR bgcolor="#FFFFFF"> 
						<TD align="center" valign="top" width="80%">
						<div id="id_spanErrDetail"
							STYLE="text-align:left;height:expression(getHeight());width:expression(document.body.clientWidth - 50);display:none;overflow:auto;overflow-x:scroll;overflow-y:scroll;">
						<UL>
							<li><font color="#000000"><b WCMAnt:param="error.jsp.errornum">错误编号：</b></font><%=nErrNo%></li>
							<li><font color="#000000"><b  WCMAnt:param="error.jsp.errotype">错误类型：</b></font><%=sErrType%></li>
							<li><font color="#000000"><b  WCMAnt:param="error.jsp.detailoferror">详细信息：</b></font><br>
							</li>
						</UL>
						<pre id="msgForCopy" name="msgForCopy" style='font-family:Arial'><%=sErrDetail%></pre>
						</div>
						</TD>
					</TR>
				</TABLE>
				</td>
			</tr>
		</table>
		</div>
		</TD>
	</TR>
	<TR>
		<TD>
		<TABLE width="100%" border="0" align="center" cellpadding="0"
			cellspacing="8">
			<TR>
				<TD>
				<DIV align="center"><INPUT type="button" name="btnCopy"
					onclick="copyToClipboard()" WCMAnt:paramattr="value:error.jsp.copyto" value="复制到剪切板" class="inputbutton">&nbsp;&nbsp;&nbsp;
				<INPUT type="button" name="btnClose" onclick="window.close()"
					 WCMAnt:paramattr="value:error.jsp.closewindow" value="关闭窗口" class="inputbutton"></DIV>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>

</BODY>
</HTML>