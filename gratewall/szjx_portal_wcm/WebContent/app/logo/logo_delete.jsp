<%--
/** Title:			logo_delete.jsp
 *  Description:
 *		删除Logo的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see logo_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.publish.logo.Logos" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	String sLogoIds = currRequestHelper.getString("LogoIds");

//5.权限校验

//6.业务代码
	/** TODO 建议改为向Service发出请求
	ILogoService currLogoService = (ILogoService)DreamFactory.createObjectById("ILogoService");
	currLogoService.delete(sLogoIds);
	**/
	Logos currLogos = Logos.findByIds(loginUser, sLogoIds);
	currLogos.removeAll(true);
	

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="logo_delete.jsp.trswcmdeletelogo">TRS WCM 删除Logo</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>
<%=currRequestHelper.toTRSRequestParam()%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
top.returnValue = true;
top.close();
</SCRIPT>
</BODY>
</HTML>