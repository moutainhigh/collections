<%
/** Title:			object_unlock.jsp
 *  Description:
 *		WCM5.2 通用的对象解锁页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-23
 *	Update Logs:
 *	History				Who				What
 *	2006-03-23			wenyh			修正解锁的问题:如果用户未登录,则不进行解锁
 *  Parameters:
 *		see object_unlock.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	//int nObjId = currRequestHelper.getInt("ObjId", 0);
	//int nObjType = currRequestHelper.getInt("ObjType", 0);

//5.权限校验

//6.业务代码
	//WCMObjHelper.unlock(nObjType, nObjId, loginUser);
	
	ResponseHelper rspsHelper = new ResponseHelper(response);
	rspsHelper.initCurrentPage(request);
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	int nObjId = currRequestHelper.getInt("ObjId", 0);
	int nObjType = currRequestHelper.getInt("ObjType", 0);

	LoginHelper currLoginHelper = new LoginHelper(request, application);	
	
	//wenyh@20060323,只有用户登录了才进行解锁操作.
	if(currLoginHelper.checkLogin()){
		ContextHelper.clear();
		ContextHelper.initContext(currLoginHelper.getLoginUser());
		WCMObjHelper.unlock(nObjType, nObjId, currLoginHelper.getLoginUser());
	}	

//7.结束
%>

<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="object_unlock.jsp.title">TRS WCM 对象解锁页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</head>
<BODY topmargin="0" leftmargin="0">
<SCRIPT LANGUAGE="JavaScript">
	window.returnValue = true;
	window.close();
</SCRIPT>
</BODY>
</HTML>