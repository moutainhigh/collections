<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" isErrorPage="true"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CMyException"%>
<%@ page import="weibo4j.model.WeiboException"%>
<%@ page import="t4j.TBlogException"%>
<%@ page import="com.trs.scm.sdk.model.SinaErrorMessage"%>
<%@ page import="com.trs.scm.sdk.model.TencentErrorMessage"%>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.infra.util.ExceptionNumber"%>
<%@ page import="com.trs.scm.sdk.util.SCMExceptionNumber"%>
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
	response.setHeader("SCMException", "true");
int nErrNo; //错误编号
String sErrType, sErrMsg, sErrDetail; //错误类型，错误信息，详细信息
String sRootPath = CMyString.showNull(request.getContextPath(), "/wcm"); //WCM根目录
sRootPath = CMyString.setStrEndWith(sRootPath, '/');

if (exception instanceof CMyException) {
	CMyException myException = (CMyException) exception;
	nErrNo = myException.getErrNo();
	sErrType = myException.getErrNoMsg();
	sErrMsg = myException.getMyMessage();
	sErrDetail = myException.getStackTraceText();
} else {
	nErrNo = ExceptionNumber.ERR_UNKNOWN;
	sErrType = "微博平台错误！";
	sErrMsg = exception.getMessage();
	sErrDetail = CMyException.getStackTraceText(exception);
}
//将异常信息输出到后台
if(nErrNo == ExceptionNumber.ERR_USER_NOTLOGIN){
    System.out.println(sErrMsg);
}else{
	//将异常信息输出到日志文件
	logger.error(sErrDetail);
}
sErrDetail = CMyString.transDisplay(sErrDetail);

int nSCMGroupId = 0, nAccountId = 0;
SCMGroup oCurrGroup = null;
Account oCurrAccount = null;
if(session.getAttribute("SCMGroupId") != null){
	nSCMGroupId = Integer.parseInt(session.getAttribute("SCMGroupId").toString());
	oCurrGroup = SCMGroup.findById(nSCMGroupId);
}
if(session.getAttribute("SCMAccountId") != null){
	nAccountId = Integer.parseInt(session.getAttribute("SCMAccountId").toString());
	oCurrAccount = Account.findById(nAccountId);
}

if(oCurrGroup != null && oCurrAccount != null){
	sErrMsg = "当前分组：[" + oCurrGroup.getGroupName() + "]，当前账号：[" + oCurrAccount.getAccountName() +"]出现异常：\n" + sErrMsg + "\n\n";
}

//if(nErrNo < 2000000) sErrMsg = LocaleServer.getString("error.label.systemerror", "系统出现错误。");

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
sErrDetail = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrDetail : LocaleServer.getString("error.jsp.syserror", "系统出现错误");
sErrMsg = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrMsg : LocaleServer.getString("error.jsp.syserror", "系统出现错误");
out.clear();
%>
<script>
	var sRootPath = '<%=sRootPath%>';
	function gotoNotLoginMsg(){
		var sURL = "../include/not_login.htm";
		if(window.location.href.indexOf("/WCMV6/main3") < 0 
			&& window.location.href.indexOf("/WCMV6/") > 0){
			sURL = sRootPath + "console/include/not_login.htm"; //TODO 从配置中取得
		}
		if(window.location.href.indexOf("/app/") > 0){
			sURL = sRootPath + "console/include/not_login.htm?FromUrl=" + encodeURIComponent(top.location.href); //TODO 从配置中取得
		}
		//window.location.href = sURL;		
		window.top.location.href = sURL;
	}
	<%
		if(bNotLogin){
			out.println("gotoNotLoginMsg();");
		}
	%>
</script>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE WCMAnt:param="error.jsp.trswcmsystemtipinfo">TRS SCM系统提示信息</TITLE>
	<LINK href="<%=sRootPath%>console/style/style.css" rel="stylesheet" type="text/css">
	<LINK href="<%=sRootPath%>app/css/wcm-common.css" rel="stylesheet" type="text/css">

<style type="text/css">
#id_spanErrDetail{text-align:left;display:none;overflow:auto;padding:5px 20px;font-size:11pt;}
/*strict*/
.ext-strict .layout_center_container{top:150px;bottom:40px;}
.ext-strict .layout_center{left:0;right:0;}
/*ie6*/
.ext-ie6 .layout_container{padding-top:100px;padding-bottom:40px;}
/*all*/
.layout_north{height:150px;padding-top:20px;}
.layout_south{height:40px;}
.layout_container{background:#FFFFFF;}
.layout_center_container{background:#FFFFEF;}
</style>
</HEAD>

<BODY width="100%" height="100%" style="overflow:hidden;">
<%
// **************错误 开始 *********************//
String sSuggest = "";
// 1.1 无效Token、Token过期
if(nErrNo == SCMExceptionNumber.SCMERR_EXPIRED_TOKEN){
	sSuggest = "请重新绑定帐号";
// 1.2 服务器忙 -1
} else if(nErrNo == SCMExceptionNumber.SCMERR_TIME_OUT){
	sSuggest = "请稍后再刷新页面";
// 1.3 认证签名失败
} else if(nErrNo == SCMExceptionNumber.SCMERR_AUTH_FAILED){
	sSuggest = "认证失败，请重试";
// 1.4 请求频次受限
}else if(nErrNo == SCMExceptionNumber.SCMERR_REQUEST_LIMIT){
	sSuggest = "请稍后再刷新页面";
// 1.5 其它
}else if(nErrNo == SCMExceptionNumber.SCMERR_OTHERS){
	sSuggest = "请刷新页面重试";
// 1.6 不合法的用户
}else if(nErrNo == SCMExceptionNumber.SCMERR_INVALID_USER){
	sSuggest = "查看帐号是否合法";
// 1.7 不合法的参数
}else if(nErrNo == SCMExceptionNumber.SCMERR_INVALID_PARAMETER){
	sSuggest = "查看请求参数是否正确";
// 1.8 请求不合法
}else if(nErrNo == SCMExceptionNumber.SCMERR_INVALID_REQUEST){
	sSuggest = "查看请求参数是否正确";
// 1.9 服务器内部错误
}else if(nErrNo == SCMExceptionNumber.SCMERR_SERVER_ERROR){
	sSuggest = "请稍后刷新页面重试";
// 1.10 未实名认证
}else if(nErrNo == SCMExceptionNumber.SCMERR_REALNAME_AUTH){
	sSuggest = "查看帐号是否是实名制认证";
}else{
	sSuggest = "请联系系统管理员解决";
}
// **************错误 结束 *********************//


%>

<div class="layout_container layout_bordercontainer">
	<div class="layout_north" align="center">
		<IMG src="<%=sRootPath%>images/error.gif" align="absmiddle" valign="top">
		<SPAN class="font_redbold style7"><span WCMAnt:param="error.jsp.tip">错误信息</span>: <%=CMyString.transDisplay(sErrMsg)%></SPAN> 
		（<A id="id_linkShowDetail" href="javascript:void(0)" class="navigation_page_link" onclick="showDetail();return false;" WCMAnt:param="error.jsp.showDetail">显示详细信息</A>）
		<br>
		<span class="font_redbold style7">提示：<%=sSuggest%></span>
	</div>
	
	<div class="layout_center_container">
	<div id="id_spanErrDetail" class="layout_center">
		<UL>
			<li><font color="#000000"><b WCMAnt:param="error.jsp.errNo">错误编号：</b></font><%=nErrNo%></li>
			<li><font color="#000000"><b WCMAnt:param="error.jsp.errType">错误类型：</b></font><%=sErrType%></li>
			<li><font color="#000000"><b WCMAnt:param="error.jsp.errDetail">详细信息：</b></font><br>
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
	<SCRIPT LANGUAGE="JavaScript" src="<%=sRootPath%>console/js/CTRSHashtable.js"></script>
	<SCRIPT LANGUAGE="JavaScript" src="<%=sRootPath%>console/js/CTRSRequestParam.js"></script>
	<SCRIPT LANGUAGE="JavaScript" src="<%=sRootPath%>console/js/CTRSAction.js"></script>
	<SCRIPT src="<%=sRootPath%>console/js/CWCMDialogHead.js"></SCRIPT>
    <script type="text/javascript" src="<%=sRootPath%>app/js/runtime/myext-debug.js"></script>
	<script src="<%=sRootPath%>app/js/data/locale/include.js"></script>
	<script language="javascript">
	function copyToClipboard(){
		var sDetailMsg = $("msgForCopy").innerText;
		window.clipboardData.setData("Text", sDetailMsg);
		alert(wcm.LANG.INCLUDE_ALERT_1 || "已经复制到剪切板中！");
	}

	function doDispatch(_sUrl){
		window.top.location.href = _sUrl;
	}

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
</BODY>
</HTML>
