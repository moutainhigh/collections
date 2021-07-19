<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
String sRightValue = "0";
try{
%>
<%@include file="../include/public_server.jsp"%>
<%
	if(loginUser.isAdministrator()){
		sRightValue = RightValue.getAdministratorRightValue().toString();
	}
}catch(Throwable tx){
	//just skip it.
	//fix:ie7 etc. history go back
}finally{
	String sTarget = request.getParameter("url");
	//add by liuhm@20130408 url为//www.baidu.com也会跳转
	//if(CMyString.isEmpty(sTarget) || sTarget.indexOf("://") > -1){
	if(CMyString.isEmpty(sTarget) || sTarget.indexOf("//") > -1){
		sTarget = "../website/website_thumb.html";
	}
	String sTemp = sTarget.indexOf("?") > 0 ? "&" : "?";
	//sitetype不需要追加，默认就是显示的文字库，在此为了兼容url中传入sitetype参数
	sTarget += sTemp + "RightValue=" + sRightValue;

	if(sTarget.toLowerCase().indexOf("sitetype") < 0){
		sTarget += "&SiteType=0";//提供默认值
	}

	//防止CRLF注入，去除回车换行
	sTarget = sTarget.replace("\n","");
	sTarget = sTarget.replace("\r","");
	response.sendRedirect(sTarget);
}
%>