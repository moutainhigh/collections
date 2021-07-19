<%--
/*
 *	History			Who			What
 *	2008-01-23		lhm		    安装后部署的消息获取页面
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.deployer.domain.DeployerController"%>
<%@ page import="com.trs.deployer.common.DeployerConstants"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@include file="../../include/public_server.jsp"%>
<!------- WCM IMPORTS END ------------>
<%
	DeployerController controller = DeployerController.newInstance(loginUser);
	controller.stopRunning();
	out.clear();
%>