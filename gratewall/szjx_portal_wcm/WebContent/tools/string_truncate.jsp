<%
/** Title:			string_truncate.jsp
 *  Description:
 *		WCM5.2 处理字符串的限长截断
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see string_truncate.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	String sTruncateStr = currRequestHelper.getString("TruncateStr");
	int nLength = currRequestHelper.getInt("Length", 0);
	String sExt = currRequestHelper.getString("Ext");

//5.权限校验

//6.业务代码
	sTruncateStr = CMyString.truncateStr(sTruncateStr, nLength, sExt);

//7.结束
	out.clear();
%>
<%=CMyString.transDisplay(sTruncateStr)%>