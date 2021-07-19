<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.gkml.sqgk.constant.ApplyFormConstants"%>
<%@ page import="com.trs.components.metadata.service.MailConfigsHelper"%>
<%@ page import="com.trs.components.gkml.sqgk.persistent.MailConfig" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.infra.util.database.TableInfo" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.trs.infra.util.CMyBitsValue" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.FileOutputStream" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%
	out.clear();
	try{
		DBManager.getDBManager().sqlExecuteUpdate("delete from "  + MailConfig.DB_TABLE_NAME);

		Enumeration paramNames = request.getParameterNames();
		
		while(paramNames.hasMoreElements()){
			String paramName = (String)paramNames.nextElement();
			if("_".equals(paramName)) continue;
			paramName = CMyString.getStr(paramName, true);

			String paramValue = request.getParameter(paramName);
			paramValue = CMyString.getStr(paramValue, true);

			if("mailBody".equalsIgnoreCase(paramName)){
				saveMailBody(paramValue);
				continue;
			}
			MailConfig currMailConfig = MailConfig.createNewInstance();
			currMailConfig.setProperty("paramName", paramName);
			currMailConfig.setProperty("paramValue",  paramValue);
			currMailConfig.save(loginUser);
		}
		//reload the configs.
		MailConfigsHelper.loadConfigs();
		out.print("true");
	}catch(Exception eOuter){
		eOuter.printStackTrace();
		out.print(eOuter.getMessage());
	}
%>

<%!
	private void saveMailBody(String sMailBody) throws Exception{
		if(sMailBody == null) return;
		URL templateFile = this.getClass().getResource(ApplyFormConstants.PATH_OF_RESULT_MAIL_TEMPLATE);
		FileOutputStream fos = new FileOutputStream(templateFile.getFile());
		fos.write(sMailBody.getBytes());
		fos.close();
	}
%>