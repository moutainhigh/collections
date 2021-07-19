<%--
/*
 *	History			Who			What
 *	2008-01-23		lhm		    安装后部署的消息获取页面
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@include file="../include/public_server.jsp"%>
<!------- WCM IMPORTS END ------------>
<%
	//请求专题初始化的数据
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_UNKNOWN, "您不是管理员，不能执行此操作！");
	}
	com.trs.components.common.publish.widget.SubjectDataInitHelper.initData(request);
	String bComplateRequest = "true";
	out.clear();
%>
{	
	complateRequest : '<%=bComplateRequest%>',
	BWAITPAGEINPUTPARAMS : 'false'
}