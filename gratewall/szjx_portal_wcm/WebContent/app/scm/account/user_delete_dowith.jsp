<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>

<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.auth.persistent.Users"%>
<%@ page import="com.trs.scm.persistent.Account"%>
<%@ page import="com.trs.scm.persistent.Accounts"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@include file="../../include/public_server.jsp"%>

<%
	// 2 获取参数
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	int nUserId = currRequestHelper.getInt("UserId",0);
	
	// 3 调用服务删除微博
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmgroup",sMethodName="deleteAdminOfGroup";
	
	Integer oResult = (Integer)processor.excute(sServiceId,sMethodName);
	
	// 4 获取用户可以管理的所有分组。
	if(nUserId==loginUser.getId()){//判断用户删除的是不是自己
		//4.1获取用户管理的第一个非空分组
		processor.reset();
		HashMap oUserIdParas = new HashMap();
		oUserIdParas.put("UserId", String.valueOf(loginUser.getId()));
		SCMGroups oGroups = (SCMGroups)processor.excute("wcm61_scmgroup","getGroupsOfUser", oUserIdParas);
		for (int i = 0; i < oGroups.size(); i++) {
			SCMGroup oSCMGroup = (SCMGroup) oGroups.getAt(i);
			if(oSCMGroup == null) continue;
			//4.1.2 判断该分组是否有可管理的微博帐号
			int nChangeSessionSCMGroupId = oSCMGroup.getId();
			if(nChangeSessionSCMGroupId == nSCMGroupId) continue;

			processor.reset();
			HashMap oSCMGroupIdParams = new HashMap();
			oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nChangeSessionSCMGroupId));
			Accounts oAccounts = (Accounts) processor.excute("wcm61_scmaccount",
					"findAccountsOfGroup", oSCMGroupIdParams);
			//4.1.3 如果分组下有帐号则退出循环
			if(oAccounts.size() > 0) {
				int nChangeSessionAccountId = oAccounts.getAt(0).getId();
				//改变session中的分组id和AccountID
				session.setAttribute("SCMGroupId",String.valueOf(nChangeSessionSCMGroupId));
				session.setAttribute("SCMAccountId",String.valueOf(nChangeSessionAccountId));
				break;
			}
		}
	}
	if(oResult.intValue()>0){
%>
	1
<%
	}else{
%>
	0
<%
	}
%>