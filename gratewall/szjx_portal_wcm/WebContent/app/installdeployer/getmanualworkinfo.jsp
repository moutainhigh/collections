<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.update.wcm.UpdateFixController"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="java.lang.StringBuffer"%>
<%@include file="../../include/public_server.jsp"%>
<!------- WCM IMPORTS END ------------>
<%
	//权限的判断
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行更新fix操作！");
	}
	UpdateFixController controller = UpdateFixController.newInstance(loginUser);
	String sManualWorkInfo = (String)controller.getParameter("MANUALWORKINFO");
	out.clear();
	out.print(CMyString.transDisplay(sManualWorkInfo));
%>