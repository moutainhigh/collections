<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%
/*
	下面的<!--ERROR2--><!--##########-->等注释语句都是有用的。
	它们都是作为条件判断的依据。返回的json对象会根据这些注释语句进行划分。
	如：app/document/document_importoffice.jsp的onSuccess方法
*/
%>
<!--ERROR2-->
<!--##########-->
<%=errorCode%>
<!--##########-->
<%=CMyString.transDisplay(ex.getMessage())%>
<!--##########-->
<%
	String sErrDetail = WCMException.getStackTraceText(ex);
	sErrDetail = CMyString.transDisplay(sErrDetail);
	String sShowDetailInfo = ConfigServer.getServer().getSysConfigValue("SHOW_ERROR_DETAILINFO", "false");
	sErrDetail = "true".equalsIgnoreCase(sShowDetailInfo) ? sErrDetail : LocaleServer.getString("fileupload_error_message_include.jsp.syserror", "系统出现错误");
%>
<%=sErrDetail%>
<!--##########-->
<!-- ERROR -->