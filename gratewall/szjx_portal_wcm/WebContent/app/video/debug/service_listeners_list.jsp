<%--
 *  Description:
 *		列出给定的服务方法的Listener，以便检查所配置的Listener是否有效
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="/include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.webframework.config.ServiceConfigHelper" %>
<%@ page import="com.trs.webframework.config.ServiceListenerConfig" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="/include/public_server.jsp"%>
<%
// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行这个校验！");
	}

// 2 获取参数
	String sServiceId	= CMyString.showNull(request.getParameter("ServiceId"));	
	String sMethodName	= CMyString.showNull(request.getParameter("MethodName"));


// 3 业务操作
	ServiceListenerConfig[] pServiceListenerConfigs = null;
	if(sServiceId.length() > 0 && sMethodName.length()>0){
		if(sServiceId.indexOf("wcm6_")<0)
			sServiceId = "wcm6_" + sServiceId;

		pServiceListenerConfigs = ServiceConfigHelper
                .findServiceListenerConfigs(sServiceId, sMethodName);
	}else{
		pServiceListenerConfigs = new ServiceListenerConfig[0];
	}
%>

<form>
请输入服务名：<INPUT TYPE="text" NAME="ServiceId" value="<%=CMyString.filterForHTMLValue(sServiceId)%>">方法名：<INPUT TYPE="text" NAME="MethodName" value="<%=CMyString.filterForHTMLValue(sMethodName)%>">&nbsp;<INPUT TYPE="submit" value="查询">
</form>
<P>
共【<%=pServiceListenerConfigs.length%>】个监听器列表：<BR>
<%
	for(int i=0; i<pServiceListenerConfigs.length; i++){
		ServiceListenerConfig serviceConfig = pServiceListenerConfigs[i];
		out.println((i+1) + "、"+serviceConfig.getClassName()+" <BR>");
		try {
			serviceConfig.getServiceListener();
		} catch (Throwable e) {
			out.println("Listener实例化失败！<BR>");
			out.println(com.trs.infra.util.CMyException.getStackTraceText(e));
			out.println("================<BR>");
		}
	}
%>