<%@ page import="java.util.ArrayList,java.util.List,java.util.Enumeration" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %> 
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.service.ServiceHelper" %> 
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.domain.IObjectMemberMgr" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>

<%
//2.登录校验
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		//ge gfc @ 2007-3-2 10:57 加上未登录的标志位
		response.setHeader("TRSNotLogin", "true");
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN,LocaleServer.getString("public_server.logging.error","用户未登录或登录超时！"));
	}
	User loginUser	= currLoginHelper.getLoginUser();

	ContextHelper.clear();
	ContextHelper.initContext(loginUser);
	if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){
		request.setCharacterEncoding("ISO-8859-1");
	}
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();
%>
<%!boolean IS_WCMV61 = true;%>

<%!
public RightValue getRightValueByMember(CMSObj obj, User user) throws WCMException {
	try {
		if (user.isAdministrator()) {
			return RightValue.getAdministratorRightValue();
		}
		RightValue oRightValue;
		if(obj instanceof BaseChannel){
			if(user.getName().equalsIgnoreCase(obj.getCrUserName())){
				return RightValue.getAdministratorRightValue();
			}
			IObjectMemberMgr oObjectMemberMgr = (IObjectMemberMgr) DreamFactory.createObjectById("IObjectMemberMgr");
			if(oObjectMemberMgr.canOperate(user, obj.getWCMType(), obj.getId())){
				oRightValue = AuthServer.getRightValue(obj, user);
			}else{
				oRightValue = new RightValue();
			}
		}else{
			oRightValue = AuthServer.getRightValue(obj, user);
		}
		return oRightValue;
	} catch (Exception e) {
		throw new WCMException(CMyString.format(LocaleServer.getString("public_server.construct.failed","构造[{0}]权限信息失败!"),new Object[]{obj}), e);
	}
}
%><%out.clear();%>