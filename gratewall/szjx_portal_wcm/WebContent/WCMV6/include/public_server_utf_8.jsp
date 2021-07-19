<%@page import="com.trs.cms.auth.domain.AuthServer"%>
<%@page import="com.trs.DreamFactory"%>
<%@page import="com.trs.cms.auth.persistent.RightValue"%>
<%@ page import="java.util.ArrayList,java.util.List,java.util.Enumeration" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.cms.auth.domain.IObjectMemberMgr" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>

<%
//2.
	LoginHelper currLoginHelper = new LoginHelper(request, application);
	if(!currLoginHelper.checkLogin()){
		//ge gfc @ 2007-3-2 10:57 
		response.setHeader("TRSNotLogin", "true");
		throw new WCMException(ExceptionNumber.ERR_USER_NOTLOGIN, "\u7528\u6237\u672A\u767B\u5F55\u6216\u767B\u5F55\u8D85\u65F6\uFF01");
	}
	User loginUser	= currLoginHelper.getLoginUser();

	ContextHelper.clear();
	ContextHelper.initContext(loginUser);
%>
<%!
public RightValue getRightValueByMember(CMSObj obj, User user) throws WCMException {
	try {
		if (user.isAdministrator()) {
			return RightValue.getAdministratorRightValue();
		}
		RightValue oRightValue;
		if(obj instanceof BaseChannel){
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
		throw new WCMException("构造[" + obj + "]权限信息失败!", e);
	}
}
%>