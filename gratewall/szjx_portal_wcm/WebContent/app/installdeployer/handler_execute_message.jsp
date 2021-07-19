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
	String sProcessInfo = (String)controller.getParameter(DeployerConstants.PROCESSOR_INFO);
	String sAllMessage = (String)controller.getParameter(DeployerConstants.ALLMESSAGE_INFO);
	String sCurrClassName = (String)controller.getParameter(DeployerConstants.CURR_CLASS_NAME);
	String sPageUrl = (String)controller.getParameter(DeployerConstants.PROCESSOR_PAGE_URL);
	String sWait = (String)controller.getParameter(DeployerConstants.B_WAIT_PAGE_INPUT_PARAMS);
	String sHasCompleted = (String)controller.getParameter(DeployerConstants.PROCESSOR_FINISHED_FLAG);
	String sNeedOpenPage = (String)controller.getParameter(DeployerConstants.NEED_OPEN_PAGE);
	String sCurrProcessDesc = (String)controller.getParameter(DeployerConstants.CURR_PROCESS_DESC);
	out.clear();
%>

{	
	processinfo : '<%=CMyString.filterForJs(sProcessInfo)%>',
	allmessage : '<%=CMyString.filterForJs(sAllMessage)%>',
	currclassname : '<%=CMyString.filterForJs(sCurrClassName)%>',
	bCompleted:'<%=CMyString.filterForJs(sHasCompleted)%>',
	pageurl : '<%=CMyString.filterForJs(sPageUrl)%>',
	bwait : '<%=CMyString.filterForJs(sWait)%>',
	needopenpage : '<%=CMyString.filterForJs(sNeedOpenPage)%>',
	currProcessDesc : '<%=CMyString.filterForJs(sCurrProcessDesc)%>'
}