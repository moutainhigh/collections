<%--
/*
 *	History			Who			What
 *	2008-01-23		lhm		    安装后部署的消息获取页面
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.update.wcm.UpdateFixController"%>
<%@ page import="com.trs.deployer.common.DeployerConstants"%>
<%@ page import="com.trs.update.wcm.WCMUpdateProcessInfo"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@include file="../../include/public_server.jsp"%>
<!------- WCM IMPORTS END ------------>
<%
	//权限的判断
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行更新fix操作！");
	}
	String sFixKey = (String)request.getParameter("Key");
	UpdateFixController controller = UpdateFixController.newInstance(loginUser);
	boolean bUpdated = controller.isUpdated(sFixKey);
	String sCurrerror = controller.getError(sFixKey);
	String sTimeStamp = (String)controller.getUpdateTimeStamp();
	String sErrorInfo = (String)controller.getParameter("ERRORMSG");
	String sHasCompleted = (String)controller.getParameter("PROCESSORFINISHEDFLAG");
	if(!bUpdated && "buildResource".equalsIgnoreCase(sFixKey) && "true".equals(sHasCompleted)){
		bUpdated = true;
	}
	out.clear();
%>
{	
	timestamp : '<%=CMyString.filterForJs(sTimeStamp)%>',
	bCompleted:'<%=CMyString.filterForJs(sHasCompleted)%>',
	errorinfo:'<%=CMyString.filterForJs(sErrorInfo)%>',
	updated : '<%=CMyString.filterForJs(bUpdated + "")%>',
	currerror : '<%=CMyString.filterForJs(sCurrerror)%>'
}