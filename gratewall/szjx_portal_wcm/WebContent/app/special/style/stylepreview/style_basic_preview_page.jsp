<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.infra.util.CMyString" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	// 1、获取参数
	String sCssContent = CMyString.showNull(currRequestHelper.getString("CssContent"),"");
	String sHost = request.getServerName();
	int nPort = request.getServerPort();
	String sServerWcmdataTemplatePath = "http://" + sHost + ":" + nPort + "/template/style/style_common/";
	out.clear();
%>
<html>
<head>
	<title WCMAnt:param="style_basic_preview_page.doc-content"> 普通文档列表-文档标题超长时截断 </title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<base href="<%=sServerWcmdataTemplatePath%>">
	<style type="text/css">
		body{
			scrollbar-face-color: #f6f6f6; 
			scrollbar-highlight-color: #ffffff; 
			scrollbar-shadow-color: #cccccc; 
			scrollbar-3dlight-color: #cccccc; 
			scrollbar-arrow-color: #330000; 
			scrollbar-track-color: #f6f6f6; 
			scrollbar-darkshadow-color: #ffffff;	
		}
		<%=sCssContent%>
	</style>
</head>
<body>
	<table id="maintable" align="center" cellpadding="0" cellspacing="0" class="template_box">
		<tr>
			<td width="100%" valign="top" style="padding-top:10px">	
				<span  WCMAnt:param="style_basic_preview_page.font">我是默认字体</span>
			</td>
		</tr>
		<tr>
			<td width="100%" valign="top" style="padding-top:10px">	
				<A title="我是链接" href="#" onclick="return false;"  WCMAnt:param="style_basic_preview_page.link">我是链接</A>
			</td>
		</tr>
	</table>
</body>
</html>