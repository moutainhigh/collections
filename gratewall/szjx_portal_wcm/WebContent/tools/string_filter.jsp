<%
/** Title:			string_filter.jsp
 *  Description:
 *		WCM5.2 处理字符串的转义
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see string_filter.xml
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
<%!
	final static int FILTER_FOR_HTML = 1;
	final static int FILTER_FOR_JDOM = 2;
	final static int FILTER_FOR_JS  = 3;
	final static int FILTER_FOR_SQL = 4;
	final static int FILTER_FOR_URL = 5;
	final static int FILTER_FOR_XML = 6;
%>
<%
//4.初始化（获取数据）
	String sStr = CMyString.showNull(currRequestHelper.getString("StringToFilte"));
	int nType = currRequestHelper.getInt("FilterType", 0);

//5.权限校验

//6.业务代码
	switch(nType){
	case FILTER_FOR_HTML:
		sStr = CMyString.filterForHTMLValue(sStr);
		break;
	case FILTER_FOR_JDOM:
		sStr = CMyString.filterForJDOM(sStr);
		break;
	case FILTER_FOR_JS:
		sStr = CMyString.filterForJs(sStr);
		break;
	case FILTER_FOR_SQL:
		sStr = CMyString.filterForSQL(sStr);
		break;
	case FILTER_FOR_URL:
		sStr = CMyString.filterForUrl(sStr);
		break;
	case FILTER_FOR_XML:
		sStr = CMyString.filterForXML(sStr);
		break;
	default:
		break;
	}

//7.结束
	out.clear();
%>
<%=CMyString.transDisplay(sStr)%>