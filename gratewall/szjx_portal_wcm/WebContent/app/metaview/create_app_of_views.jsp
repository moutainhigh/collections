<%
/** Title:			chnlsyn_test.jsp
 *  Description:
 *		WCM5.2 文档分发测试工具
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2007-8-15
 *  Vesion:			1.0
 *  Last EditTime:	2007-8-15
 *	Update Logs:
 *		caohui@2007-8-16, First Create
 *
 *  Parameters:
 *		see viewdocuments_test.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.util.HashMap" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException(LocaleServer.getString("app_of_views.noRight","您没有权限执行这个校验！"));
	}

// 2 初始化工作
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "MetaDataDef", sMethodName = "createViewRelation";

	// 标准模式
	processor.excute(sServiceId, sMethodName);
	
// 3 开始输出页面内容
	//out.clear();
	out.print(LocaleServer.getString("create_app_of_views.label.createViewRelation", "所有视图的应用产生完成！"));
%>