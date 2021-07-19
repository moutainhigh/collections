<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.scm.sdk.model.MicroMessage" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="weibo4j.model.WeiboException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%@ include file="register_check.jsp"%>
<%! final boolean IS_DEBUG = false;%>
<%
	// 2 获取当前日期、构造参数。
	SimpleDateFormat oFormat = new SimpleDateFormat("yyyy-MM-dd");
	String showToday = oFormat.format(new Date());
	JSPRequestProcessor oProcessor = new JSPRequestProcessor();
	String sIFrameSrc = "microcontent/single_microblog_list.jsp";
	int nSCMGroupId = 0;
	int nAccountId = 0;
	SCMGroup oSCMGroup = null;
	Account oAccount = null;
	HashMap oSCMGroupIdParams = new HashMap();
	Accounts oAccounts = null;
	String outSysetmMsg = "";
	// 3 使用WCM用户判断用户是否拥有可以维护分组
	HashMap oUserIdParas = new HashMap();
	oUserIdParas.put("UserId", String.valueOf(loginUser.getId()));
	SCMGroups oGroups = (SCMGroups) oProcessor.excute("wcm61_scmgroup", "getGroupsOfUser", oUserIdParas);
	int DefaultSCMGroupId = 0;
	if(oGroups!=null && oGroups.size()>0){// 2.1 有可以维护的分组
		// 4 判断用户是否拥有可以维护的帐号
		for (int i = 0; i < oGroups.size(); i++) {
			oSCMGroup = (SCMGroup) oGroups.getAt(i);
			if(oSCMGroup == null) continue;
			// 4.1 判断该分组是否有可管理的微博帐号
			int nGroupId = oSCMGroup.getId();
			DefaultSCMGroupId = nGroupId;
			oProcessor.reset();
			oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nGroupId));
			oAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
			oProcessor.reset();
			// 4.2 如果分组下有帐号则退出循环
			for (int j = 0; j < oAccounts.size(); j++) {
				oAccount = (Account) oAccounts.getAt(j);
				if (oAccount != null) {
					break;//退出内层for循环
				}
			}
			if (oAccount != null) break;//退出外层for循环
		}
		//5 若用户存在可用帐号，则判断session中的值
		if(oAccount!=null){
			// 5.1 判断session中是否有分组id，若没有则使用系统自动获取的分组，若有则使用session中的值
			if(session.getAttribute("SCMGroupId")!=null){
				nSCMGroupId = Integer.parseInt(session.getAttribute("SCMGroupId").toString());
				SCMGroup oGroupInSession = null;
				// 5.2 判断用户对分组是否有维护权限
				if(oGroups.indexOf(nSCMGroupId) > -1){
					oGroupInSession = SCMGroup.findById(nSCMGroupId);
					if(session.getAttribute("SCMAccountId")!=null){
						nAccountId = Integer.parseInt(session.getAttribute("SCMAccountId").toString());
						// 5.3 判断帐号是否在分组下（权限）
						oProcessor.reset();
						oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
						Accounts oTempAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
						// 5.4 判断分组下是否有此帐号
						if(oTempAccounts.indexOf(nAccountId) > -1){
							oSCMGroup = oGroupInSession;//使用session中存放的分组
							oAccount = Account.findById(nAccountId);//使用session中存放的帐号
						}else{
							// 5.5 判断分组下有没有帐号，若有则使用第一个帐号
							if(oTempAccounts!=null && oTempAccounts.size() > 0){
								for (int i = 0; i < oAccounts.size(); i++) {
									Account oTemAccount = (Account) oAccounts.getAt(i);
									if (oTemAccount != null) {
										// 5.6 分组下判断用户是否被解除绑定，使用分组下第一个帐号进入
										if (oTemAccount.getStatus() == 1) {
											oAccount = oTemAccount;
											oSCMGroup = oGroupInSession;
											break;//获取第一个帐号，退出for循环
										}
									}
								}
							}else{
								if(oGroups.size()==1){
									sIFrameSrc = "account/binding_account.jsp?SCMGroupId="+nSCMGroupId;
								}else{
									sIFrameSrc = "account/all_accounts_list.jsp";
								}
							}
						}
					}
				}
			}
		}else{
			if(oGroups.size()==1){
				sIFrameSrc = "account/binding_account.jsp?SCMGroupId="+DefaultSCMGroupId;
			}else{
				sIFrameSrc = "account/all_accounts_list.jsp";
			}
		}
	// 6 没有可以维护的分组
	}else{
		//6.1 用户是系统管理员,跳转到权限管理页面
		if(SCMAuthServer.isAdminOfSCM(loginUser)){
			sIFrameSrc = "account/rights_management.jsp";
		}else if(SCMAuthServer.isAuditorOfSCM(loginUser)){
			// 6.2 用户是SCM审核人员,跳转到审核微博页面
			sIFrameSrc = "audit/microblog_checking_list.jsp";
		}else{
			// 6.3 用户不是系统管理员,用户也不是维护人员，也不是审核人员，没有权限进入SCM系统。
			outSysetmMsg = "抱歉，您没有可以维护的分组暂不能进入SCM系统!系统将关闭浏览器的当前页面!";
		}
	}
	// 7 若session中没有分组，将分组和用户帐号存入session中 。
	if(oSCMGroup != null){
		session.setAttribute("SCMGroupId",String.valueOf(oSCMGroup.getId()));
		nSCMGroupId = oSCMGroup.getId();
		if(oAccount != null){
			session.setAttribute("SCMAccountId",String.valueOf(oAccount.getId()));
			nAccountId = oAccount.getId();
		}
	}
	// 8 判断iframe中显示的页面。
	int nFlagToPage = 0;
	if(sIFrameSrc.contains("single")){
		nFlagToPage = 1;
	}else if(sIFrameSrc.contains("accounts")){
		nFlagToPage = 2;
	}else if(sIFrameSrc.contains("audit")){
		nFlagToPage = 4;
	}else if(sIFrameSrc.contains("binding")){
		nFlagToPage = 5;
	}else{
		nFlagToPage = 3;
	}
	out.clear();
%>