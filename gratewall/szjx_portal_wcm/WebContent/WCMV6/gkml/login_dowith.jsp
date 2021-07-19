<%
/** Title:			login.jsp
 *  Description:
 *		WCM6 用户登录处理页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2006-12-26 10:12
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-26 / 2006-12-26
 *	Update Logs:
 *		CH@2006-12-26 created
 *  Parameters:
 *		see login.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server_nologin.jsp"%>
<%!
//常量定义
	/** 调试开关 **/
	private final static boolean  IS_DEBUG = false;
%>
<%
//3.初始化（获取数据）
	
//4.业务代码	
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	String sUserName = currRequestHelper.getString("UserName");
	String sPassWord = currRequestHelper.getString("PassWord");
	if("true".equals(currRequestHelper.getString("ForceKick"))){
		currLoginHelper.setForceKick(true);		
	}
	int nResult = currLoginHelper.login( sUserName, sPassWord );
	if(sUserName==null){
		sUserName = "";
	}

//5.清除服务器端的输出
	out.clear();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<BODY>

<form name="frmAction" action="login_dowith.jsp" method="post" target="_self" style="display:none">
	<INPUT TYPE="password" name="PassWord" value="<%=CMyString.filterForHTMLValue(sPassWord)%>">
	<INPUT TYPE="hidden" name="UserName" value="<%=CMyString.filterForHTMLValue(sUserName)%>">
	<INPUT TYPE="hidden" name="ForceKick" value="true">
</form>

<SCRIPT LANGUAGE="JavaScript">	
	var sIsdebug = '';
	if(/isdebug=1/i.test(location.href)){
		sIsdebug = 'isdebug=1';
	}
	<% if( nResult==LoginHelper.RESULT_OK ){  %>
		window.location.href="2menu.jsp"+(sIsdebug.length > 0 ? '?'+sIsdebug:'');
	<% } else if(nResult == LoginHelper.ERR_USER_LOGINED){ %>
			var confirmMsg = "用户[<%=CMyString.filterForJs(sUserName)%>]已在[<%=currLoginHelper.getLoginUser().getLoginIP()%>]登录!\r\n强行登录?";
			if(confirm(confirmMsg)){
				document.frmAction.submit();
			}else{
				window.location.href="login.html?UserName=<%=CMyString.filterForJs(sUserName)%>&"+sIsdebug;
			}
	<%} else { %>
		alert( "对不起，您输入的<%=LoginHelper.getErrorMsg(nResult)%>！" );
		window.location.href="login.html?UserName=<%=sUserName%>&"+sIsdebug;
	<% } %>	
</SCRIPT>
</BODY>
</HTML>