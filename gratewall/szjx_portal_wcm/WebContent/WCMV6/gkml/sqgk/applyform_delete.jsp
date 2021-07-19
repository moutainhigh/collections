<%--
/** Title:			applyform_delete.jsp
 *  Description:
 *		删除ApplyForm的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 6.0
 *  Created:		2008-02-20 20:18:05
 *  Vesion:			1.0
 *  Last EditTime:	2008-02-20 / 2008-02-20
 *	Update Logs:
 *		TRS WCM 6.0@2008-02-20 产生此文件
 *
 *  Parameters:
 *		see applyform_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForms" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	String sApplyFormIds = CMyString.showNull(currRequestHelper.getString("ApplyFormIds"),
							currRequestHelper.getString("ApplyFormId"));
	if(CMyString.isEmpty(sApplyFormIds)){
		throw new WCMException("错误的请求参数，未传入ApplyFormIds或ApplyFormId.");
	}
//5.权限校验

//6.业务代码
	ApplyForms currApplyForms = ApplyForms.findByIds(loginUser, sApplyFormIds);
	currApplyForms.removeAll(true);
	

//7.结束
	out.clear();
	out.print("true");
%>