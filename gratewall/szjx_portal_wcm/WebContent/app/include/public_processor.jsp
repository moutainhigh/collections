<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="java.util.Map, java.util.HashMap" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<%
//2.登录校验
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		response.setHeader("TRSNotLogin", "true");
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN, LocaleServer.getString("ublic_processor.logging.error","用户未登录或登录超时！"));
	}
	User loginUser	= currLoginHelper.getLoginUser();

	ContextHelper.clear();
	ContextHelper.initContext(loginUser);
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
%>
<%!
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
            .getLogger(com.trs.webframework.controler.JSPRequestProcessor.class);

	private Object excuteMult(JSPRequestProcessor processor, String serviceid, String methodname){
		try{
			return processor.excuteMult(serviceid, methodname);
		}catch(Exception ex){
			logger.error("Error to execute " + serviceid + "." + methodname, ex);
			//Just skip it.
		}
		return null;
	}
	private Object[] excuteArrayMult(JSPRequestProcessor processor, String serviceid, String methodname){
		try{
			return processor.excuteArrayMult(serviceid, methodname);
		}catch(Exception ex){
			logger.error("Error to execute " + serviceid + "." + methodname, ex);
			//Just skip it.
		}
		return null;
	}
%>