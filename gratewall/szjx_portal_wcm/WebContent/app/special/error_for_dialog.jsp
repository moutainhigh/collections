<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" isErrorPage="true"%>
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
	response.setHeader("ReturnJson", "true");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
	//prevents caching at the proxy server
	response.setDateHeader("max-age", 0);
	response.setStatus(200);
	response.setHeader("TRSException", "true");
%>
<%
	String sContextPath = CMyString.showNull(request.getContextPath(), "/wcm");
	sContextPath = CMyString.setStrEndWith(sContextPath, '/');
	String sCorePath = sContextPath + "app/special/";
%>

<%
//why@2002-04-27: [1]增加站点根目录配置  [2]增加不折行处理
int nErrNo; //错误编号
String sErrType, sErrMsg, sErrDetail; //错误类型，错误信息，详细信息

if (exception instanceof CMyException) {
	CMyException myException = (CMyException) exception;
	nErrNo = myException.getErrNo();
	sErrType = myException.getErrNoMsg();
	sErrMsg = myException.getMyMessage();
	sErrDetail = myException.getStackTraceText();
} else {
	nErrNo = ExceptionNumber.ERR_UNKNOWN;
	sErrType = LocaleServer.getString("error.label.unknown", "未知");
	sErrMsg = LocaleServer.getString("error.label.unknownError", "未知错误！");
	sErrDetail = CMyException.getStackTraceText(exception);
}
//caohui@2003-01-14:处理不同异常的表现方式
if (nErrNo == ExceptionNumber.ERR_USER_NOTLOGIN) {//处理用户登录超时的问题
	response.setStatus(401);
	response.setHeader("TRSNotLogin", "true");
%>
<script language="javascript">
<!--
	try{
		if(window != top){
			top.location.href = "<%=sCorePath%>../../include/not_login.htm";
		}
	}catch(error){}
//-->
</script>
<%
	return;
}
%>
<%
//将异常信息输出到后台
//System.out.println(sErrDetail);
//将异常信息输出到日志文件
logger.error(sErrDetail);
String sShowDetailInfo = ConfigServer.getServer().getSysConfigValue("SHOW_ERROR_DETAILINFO", "false");
sErrDetail = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrDetail :(LocaleServer.getString("error_for_dialog.jsp.label.sys_error", "系统出现错误"));
sErrMsg = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrMsg :(LocaleServer.getString("error_for_dialog.jsp.label.sys_error", "系统出现错误"));
out.clear();
%><%
	String sAjaxRequest = request.getHeader("x-requested-with");
	if("XMLHttpRequest".equalsIgnoreCase(sAjaxRequest)){
%>{fault:{
	code		: <%=nErrNo%>,
	message		: '<%=CMyString.filterForJs(sErrMsg)%>',
	detail		: '<%=CMyString.filterForJs(sErrDetail)%>',
	suggestion  : '<%=CMyString.filterForJs(sErrDetail)%>'
}}<%
	}else{
%>
<script src="<%=sCorePath%>../js/easyversion/lightbase.js"></script>
<script src="<%=sCorePath%>../js/source/wcmlib/WCMConstants.js"></script>
<script src="<%=sCorePath%>../js/easyversion/extrender.js"></script>
<script src="<%=sCorePath%>../js/source/wcmlib/core/MsgCenter.js"></script>
<script src="<%=sCorePath%>../js/source/wcmlib/core/CMSObj.js"></script>
<script src="<%=sCorePath%>../js/source/wcmlib/core/AuthServer.js"></script>

<script src="<%=sCorePath%>../js/source/wcmlib/Observable.js"></script>
<script src="<%=sCorePath%>../js/source/wcmlib/Component.js"></script>
<script src="<%=sCorePath%>../js/source/wcmlib/dialog/Dialog.js"></script>
<script src="<%=sCorePath%>../js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script language="javascript">
<!--
	Ext.Msg.warn('<%=CMyString.filterForJs(sErrMsg)%>');
//-->
</script>
<%
	}
%>