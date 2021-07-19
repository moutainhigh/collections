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
	//获取冲突文件列表
	UpdateFixController controller = UpdateFixController.newInstance(loginUser);
	List fileList = (ArrayList)controller.getParameter("CONFLICTFILES");
	StringBuffer listBuffer = new StringBuffer();
	listBuffer.append("{");
	String sTimeStamp = (String)controller.getParameter("TIMESTAMP");
	listBuffer.append("timestamp:'" + sTimeStamp + "',filelist:[");
	if(fileList != null){
		for(int i=0;i<fileList.size();i++){
			String sFilePath = (String)fileList.get(i);
			listBuffer.append("'" + sFilePath + "'");
			if(i < (fileList.size()-1)){
				listBuffer.append(",");
			}
		}
	}
	listBuffer.append("]}");
	out.clear();
	out.print(listBuffer.toString());
%>