<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="weibo4j.model.WeiboException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.trs.scm.sdk.model.CommentWrapper" %>
<%@ page import="com.trs.scm.sdk.model.Comment" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.scm.sdk.model.MicroContentWrapper" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContents" %>
<%@ page import="com.trs.scm.sdk.util.CMyContentTranslate" %>
<%@ page import="com.trs.scm.sdk.model.Favorite" %>
<%@ page import="com.trs.scm.sdk.model.FavoriteWrapper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.sdk.util.CMyTimeTranslate" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ page import="com.trs.scm.sdk.model.MicroMessage" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ include file="../../include/public_server.jsp"%>
<%@ include file="register_check.jsp"%>
<%! final boolean IS_DEBUG = false;%>
<%! 
	public String getHead(Account _oAccount){
		String sNoHead = "../images/no_head.png";
		if(_oAccount == null){
			return sNoHead;
		}
		String sHead = _oAccount.getHeadPic();
		if(CMyString.isEmpty(sHead)){
			return sNoHead;
		}
		return "../file/read_image.jsp?FileName=" + sHead;
	}
	public String getCurrentLeft(int nWidth,int nOnePageCounts,Accounts oHeadTempAccounts,int nId){
		String nHeadLeft="";
		int nNowPage=1;
		int nNumI=1;
		for(int i=1;i<=oHeadTempAccounts.size();i++){
			Account oHeadAccount = (Account) oHeadTempAccounts.getAt(i-1);
			if (oHeadAccount == null) continue;
			if(oHeadAccount.getStatus()!=1) continue;
			int nTempAccountId = oHeadAccount.getId();
			if(nTempAccountId==nId){
				nNumI=i;
			}
		}
		nNowPage =(nNumI%nOnePageCounts!=0)?(nNumI/nOnePageCounts+1):(nNumI/nOnePageCounts);
		int nTemp = -(nNowPage-1)*nWidth;
		nHeadLeft = nTemp+"px";
		return nHeadLeft;
	}

	public String getErrorMsg(Exception exception){
		String sErrMsg = "";
		if (exception instanceof CMyException) {
			CMyException myException = (CMyException) exception;
			sErrMsg = myException.getMyMessage();
		} else {
			sErrMsg = exception.getMessage();
		}
		return sErrMsg;
	}
%>
<%
	int nHeadPage = 1;
	if(session.getAttribute("HeadPage")!=null){
		nHeadPage = Integer.parseInt(session.getAttribute("HeadPage").toString());
	}
	// 2 获取当前日期、构造参数。
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nPageIndex = currRequestHelper.getInt("PageIndex",1);

	SCMGroup oSCMGroup = null;
	Account oAccount = null;
	HashMap oSCMGroupIdParams = new HashMap();
	Accounts oAccounts = null;
	String outSysetmMsg = "";
	
	// 3 使用WCM用户判断用户是否拥有可以维护分组
	HashMap oUserIdParas = new HashMap();
	oUserIdParas.put("UserId", String.valueOf(loginUser.getId()));
	SCMGroups oGroups = (SCMGroups) oProcessor.excute("wcm61_scmgroup", "getGroupsOfUser", oUserIdParas);
	if(nSCMGroupId != 0){
		oSCMGroup = SCMGroup.findById(nSCMGroupId);
		oProcessor.reset();
		oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
		oAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
		if(nAccountId == 0 && oAccounts != null && oAccounts.size() > 0 ){
			oAccount = (Account)oAccounts.getAt(0);
		}else{
			oAccount = Account.findById(nAccountId);
		}
	}
	if(oAccount == null ){
		if(oGroups!=null && oGroups.size()>0){// 2.1 有可以维护的分组
			// 4 判断用户是否拥有可以维护的帐号
			for (int i = 0; i < oGroups.size(); i++) {
				oSCMGroup = (SCMGroup) oGroups.getAt(i);
				if(oSCMGroup == null) continue;
				// 4.1 判断该分组是否有可管理的微博帐号
				int nGroupId = oSCMGroup.getId();
				oProcessor.reset();
				oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nGroupId));
				oAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
				oProcessor.reset();
				// 4.2 如果分组下有帐号则退出循环
				for (int j = 0; j < oAccounts.size(); j++) {
					oAccount = (Account) oAccounts.getAt(j);
					if (oAccount != null) break;//退出内层for循环
				}
				if (oAccount != null) break;//退出外层for循环
			}
			//5 若用户存在可用帐号，则判断session中的值
			if(oAccount!=null){
				// 5.1 判断session中是否有分组id，若没有则使用系统自动获取的分组，若有则使用session中的值
				if(session.getAttribute("SCMGroupId")!=null && nSCMGroupId == 0){
					nSCMGroupId = Integer.parseInt(session.getAttribute("SCMGroupId").toString());
					SCMGroup oGroupInSession = null;
					// 5.2 判断用户对分组是否有维护权限()
					if(oGroups.indexOf(nSCMGroupId) > -1){
						// 5.3 判断session中分组是否存在
						oGroupInSession = SCMGroup.findById(nSCMGroupId);
						// 5.4 判断session中的帐号是否存在
						if(session.getAttribute("SCMAccountId")!=null){
							nAccountId = Integer.parseInt(session.getAttribute("SCMAccountId").toString());
							// 5.5 判断帐号是否在分组下（权限）
							oProcessor.reset();
							oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
							Accounts oTempAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
							Account oAccountInSession = null;
							// 5.6 判断分组下是否有此帐号
							if(oTempAccounts.indexOf(nAccountId) > -1){
								oAccountInSession = Account.findById(nAccountId);
							// 5.7 帐号存在分组下使用session中的分组对象和帐号对象
								oSCMGroup = oGroupInSession;//使用session中存放的分组
								oAccount = oAccountInSession;//使用session中存放的帐号
							}else{
								// 5.8 判断分组下有没有帐号，若有则使用第一个帐号
								if(oTempAccounts!=null && oTempAccounts.size() > 0){
									for (int i = 0; i < oAccounts.size(); i++) {
										Account oTemAccount = (Account) oAccounts.getAt(i);
										if (oTemAccount != null) {
											// 5.9 分组下判断用户是否被解除绑定，使用分组下第一个帐号进入
											if (oTemAccount.getStatus() == 1) {
												oAccount = oTemAccount;
												oSCMGroup = oGroupInSession;
												break;//获取第一个帐号，退出for循环
											}
										}
									}
								}
							}
						}
					}
					
				}else if(nSCMGroupId != 0){
					//处理request传来的参数
					nHeadPage = 1;
					boolean isRightGroup = false;
					SCMGroup oGroupInRequest = null;
					// 5.2 判断用户对分组是否有维护权限()
					if(oGroups.indexOf(nSCMGroupId) > -1){
						isRightGroup = true;
						oGroupInRequest = SCMGroup.findById(nSCMGroupId);
					}
					// 5.3 判断request中分组是否存在
					if(isRightGroup){
						// 5.4 判断request中的帐号是否存在
							// 5.5 判断帐号是否在分组下（权限）
							oProcessor.reset();
							oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
							Accounts oTempAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
							boolean isRightAccout = false;
							Account oAccountInRequest = null;
						if(nAccountId != 0){
							// 5.6 判断分组下是否有此帐号
							if(oTempAccounts.indexOf(nAccountId) > -1){
								isRightAccout = true;
								oAccountInRequest = Account.findById(nAccountId);
							}
							// 5.7 帐号存在分组下使用request中的分组对象和帐号对象
							if(isRightAccout){
								oSCMGroup = oGroupInRequest;//使用request中存放的分组
								oAccount = oAccountInRequest;//使用request中存放的帐号
							}else{
								// 5.8 判断分组下有没有帐号，若有则使用第一个帐号
								if(oTempAccounts!=null && oTempAccounts.size() > 0){
									for (int i = 0; i < oAccounts.size(); i++) {
										Account oTemAccount = (Account) oAccounts.getAt(i);
										if (oTemAccount != null) {
											// 5.9 分组下判断用户是否被解除绑定，使用分组下第一个帐号进入
											if (oTemAccount.getStatus() == 1) {
												oAccount = oTemAccount;
												oSCMGroup = oGroupInRequest;
												break;//获取第一个帐号，退出for循环
											}
										}
									}
								}
							}
						}else{
							// 5.8 判断分组下有没有帐号，若有则使用第一个帐号
								if(oTempAccounts!=null && oTempAccounts.size() > 0){
									for (int i = 0; i < oAccounts.size(); i++) {
										Account oTemAccount = (Account) oAccounts.getAt(i);
										if (oTemAccount != null) {
											// 5.9 分组下判断用户是否被解除绑定，使用分组下第一个帐号进入
											if (oTemAccount.getStatus() == 1) {
												oAccount = oTemAccount;
												oSCMGroup = oGroupInRequest;
												break;//获取第一个帐号，退出for循环
											}
										}
									}
								}
						}
					}
				}
			}
		// 6 没有可以维护的分组
		}else{
			// 6.1 用户是SCM管理员
			if(SCMAuthServer.isAuditorOfSCM(loginUser)){
				// 6.2 用户是SCM审核人员
				outSysetmMsg = "抱歉，您还不是任何分组的维护人员。</br>您可以联系SCM管理员，将您添加到分组维护人员后，再访问当前页面！";
			}else{
				outSysetmMsg = "抱歉，您没有可以维护的分组暂不能进入SCM系统!</br>系统将关闭浏览器的当前页面！";
			}
		}
	}
	// 7 若session中没有分组，将分组和用户帐号存入session中 。
	if(oSCMGroup != null){
		session.setAttribute("SCMGroupId",String.valueOf(oSCMGroup.getId()));
		if(oAccount != null){
			session.setAttribute("SCMAccountId",String.valueOf(oAccount.getId()));
		}
	}

	// 如果没有权限进入系统，则给出相应的提示信息
	if(!CMyString.isEmpty(outSysetmMsg)){
		if(SCMAuthServer.isAuditorOfSCM(loginUser)){
%>
	<div style="text-align: center;font-size:12px;padding-left:20px;padding-top:40px;"><span class="explainWords">
			<font size="4" color="#AD251A"><%=outSysetmMsg%></font></span></div>
<%
		}else{
%>
	<script language="javascript">
	<!--
		alert("<%=outSysetmMsg%>");
		window.top.close();
	//-->
	</script>
<%	}
	return;
}
	String sPlat = null;
	if(oAccount !=null){
		sPlat = oAccount.getPlatform();
		nAccountId = oAccount.getId();
		nSCMGroupId = oSCMGroup.getId();
	}else{
	%>
		<div style="text-align: center;font-size:12px;padding-left:20px;padding-top:40px;"><font size="4" color="#AD251A" style="font-family:微软雅黑 !important">您没有任何可维护的帐号，请到帐号管理页面绑定帐号！</font></div>
	<%
		return;
	}
	
	out.clear();
%>