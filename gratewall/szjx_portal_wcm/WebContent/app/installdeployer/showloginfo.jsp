<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CMyFile"%>
<%@ page import="com.trs.infra.support.config.ConfigServer"%>
<%@ page import="java.io.File"%>
<%@include file="../../include/public_server.jsp"%>
<!------- WCM IMPORTS END ------------>
<%
	//权限的判断
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行更新fix操作！");
	}
	String sTimeStamp = request.getParameter("TimeStamp");
	String sResult = "";
	if(CMyString.isEmpty(sTimeStamp)){
		sResult = "必须传入升级时间";
		out.clear();
		out.println(sResult);
		return;
	}
	//获取wcm的应用路径
	String sWCMPath =  ConfigServer.getServer().getInitProperty("WCM_PATH");
	String sLogFile = CMyString.setStrEndWith(sWCMPath,File.separatorChar) + "WEB-INF" +File.separator +"classes" + File.separator + "updatefix" + File.separator +"update" + File.separator + "logs" + File.separator + sTimeStamp + ".log";
	if(!CMyFile.fileExists(sLogFile)){
		sResult = "日志文件不存在";
		out.clear();
		out.println(sResult);
		return;
	}
	sResult = CMyFile.readFile(sLogFile);
	out.clear();
	out.println(CMyString.transDisplay(sResult));
%>