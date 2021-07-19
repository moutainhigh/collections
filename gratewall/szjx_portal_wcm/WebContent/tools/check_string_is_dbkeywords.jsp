<%
/** Title:			check_string_is_dbkeywords.jsp
 *  Description:
 *		WCM5.2 检查字符串是否属于数据库保留关键字
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2005-2-21
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		WSW@2005-2-21 created file
 *
 *  Parameters:
 *		see check_string_is_dbkeywords.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.database.CDBKeyWords" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
//4.初始化（获取数据）
	String sValue = currRequestHelper.getString("StrValue");
	if(sValue == null || sValue.trim().length()<=0){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入待检字符串为空！");
	}
//5.权限校验

//6.业务代码
	boolean bIsDBKeyWords = CDBKeyWords.isDBKeyWords(sValue);
//7.结束
	out.clear();
	out.print(bIsDBKeyWords);
%>