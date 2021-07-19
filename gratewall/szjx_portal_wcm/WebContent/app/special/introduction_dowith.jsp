<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="error_for_dialog.jsp"%>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@include file="../include/public_server.jsp"%>
<%
	try{
		String sEnterDirectly = currRequestHelper.getString("recommand");
		if(sEnterDirectly.trim().equals("true")){
			ConfigServer.getServer().updateConfigValue("SPECIAL_ENTERDIRECTLY", "true");
		}
	}catch(Throwable ex){
		throw new WCMException(LocaleServer.getString("introduction_dowith.jsp.label.fail2save_sys_config", "保存系统配置时出错"), ex);
	}
%>