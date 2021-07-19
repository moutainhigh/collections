<%
/** Title:			login.jsp
 *  Description:
 *		WCM5.2 用户登录页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2004-12-08 11:17 上午
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-08 / 2004-12-08
 *	Update Logs:
 *		CH@2004-12-08 created
 *
 *  Parameters:
 *		see login.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../console/include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../console/include/public_server_noparam.jsp"%>
<%!
//常量定义
	/** 调试开关 **/
	private final static boolean  IS_DEBUG = false;
%>
<%
//3.初始化（获取数据）
	
//4.业务代码	
	currLoginHelper.logout();
	//TODO
	//日志记录
%>
<SCRIPT LANGUAGE="JavaScript">
	if(top != self) {
		top.location.href="./login.jsp";
		//top.location.href="../";
	}else{
		window.location.href="./login.jsp";
		//window.location.href="../";
	}
</SCRIPT>