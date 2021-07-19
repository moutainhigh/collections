<%@ page import="java.util.ArrayList,java.util.List,java.util.Enumeration" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.presentation.locale.LocaleServer" %> 
<%@ page import="com.trs.service.ServiceHelper" %> 
<!------- WCM IMPORTS END ------------>


<%
//1.页面状态设定
	ResponseHelper rspsHelper = new ResponseHelper(response);
	rspsHelper.initCurrentPage(request);
//2.参数获取
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();

	User loginUser	= null;
	try{
		loginUser = (new LoginHelper(request,application)).getLoginUser();
	}catch(Exception e){}
	if( loginUser == null){
		loginUser	= User.getSystem();
	}
	ContextHelper.clear();
	ContextHelper.initContext(loginUser);
%>