<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.sdk.model.MicroMessage" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../include/public_server.jsp"%>
<%! final boolean IS_DEBUG = false;%>
<%
	//获取待审微博
	int nMessageType = currRequestHelper.getInt("MessageType",1);
	boolean hasMessage = false;
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	// 1 设置工作流参数
	String sServiceIdOfProcessService = "wcm6_process";
	String sMethodNameOfQueryReviewedList = "getContentsOfUser";
	final String ContentType = String.valueOf(SCMMicroContent.OBJ_TYPE);
	
	HashMap oHashMap = new HashMap();
	oHashMap.put("ViewType", String.valueOf(1));
	oHashMap.put("ObjType", String.valueOf(SCMMicroContent.OBJ_TYPE));
	
	// 2 调用工作流服务
	IFlowContent[] oResult = (IFlowContent[])oProcessor.excute(sServiceIdOfProcessService,sMethodNameOfQueryReviewedList,oHashMap);
	int nTotalNum = 0;
	if(oResult!=null){
		nTotalNum = oResult.length;
	}else{
		out.print(1);
		return;
	}
	if(nTotalNum > 0){
		if(!hasMessage){hasMessage = true;}
	%>
		<%=loginUser.getName()%>：您有
			<span onclick="toCommentPage(1)" class="messageContentFont"><%=nTotalNum%>条待审微博</span><br/>
	<%
	}
	//获取微博未读消息
	// 1 构造参数
	String sServiceId = "wcm61_scmmessage",sMethodName="queryUnReadMessages";
	HashMap oMessageQueryParas = new HashMap();

	// 2 调用服务获取未读消息信息
	Map<Account, MicroMessage> messageMap = null;
	try{
		messageMap = (HashMap<Account, MicroMessage>)oProcessor.excute(sServiceId,sMethodName,oMessageQueryParas);
	}catch(Exception e){
	%>
			<font style="color:#000;">微博平台服务器端异常，加载未读消息失败！请稍后再试！</font><br/>
			
			<span class="messageContentFont">
				<span <%if(nMessageType==1){%>onclick="showAutoMessage();return false;"<%}else{%>onclick="showRepeatMessage();return false;"<%}%> class="messageContentFont">点击重新加载</span>
			</span>
	<%
		return;
	}
	
	// 3 构造消息遍历迭代器
	int nSizeFlag = 0;
	if(messageMap != null)
		nSizeFlag = messageMap.size();
	int nType = currRequestHelper.getInt("Type",0);
	Iterator tempIter =null;
	if(nSizeFlag!=0){
		tempIter = messageMap.entrySet().iterator();
		//遍历消息
		if(tempIter!=null){
			//1 获取用户可管理的所有分组。
			oProcessor.reset();
			HashMap oUserIdParas = new HashMap();
			oUserIdParas.put("UserId", String.valueOf(loginUser.getId()));
			SCMGroups oGroups = (SCMGroups) oProcessor.excute("wcm61_scmgroup", "getGroupsOfUser", oUserIdParas);
			if(oGroups==null && oGroups.size()==0) return;//用户没有可维护的分组。
			while (tempIter.hasNext()) {
				Map.Entry entry = (Map.Entry) tempIter.next();
				Account tempAccount = (Account) entry.getKey();
				MicroMessage tempMicroMessage = (MicroMessage) entry.getValue();
				//2 获取每个分组下所有账号。
				oProcessor.reset();
				int nTempAccountId = tempAccount.getId();
				HashMap oSCMGroupIdParams = new HashMap();
				int nIndex = -1;
				int nSendAccountId = 0;
				int nSendGroupId = 0;
				//判断session中是否存在此账号
				if(session.getAttribute("SCMGroupId")!=null){
					oProcessor.reset();
					int nTempGroupId = Integer.parseInt(session.getAttribute("SCMGroupId").toString());
					oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nTempGroupId));
					Accounts oAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
					if(oAccounts!=null && oAccounts.size()>0){
						nIndex = oAccounts.indexOf(nTempAccountId);
						if(nIndex > -1){
							nSendAccountId = nTempAccountId;
							nSendGroupId = nTempGroupId;
						}
					}
				}
				if(nIndex == -1){
					for(int i=0;i<oGroups.size();i++){
						SCMGroup oTempGroup=(SCMGroup)oGroups.getAt(i);
						int nTemGroupId = oTempGroup.getId();
						oProcessor.reset();
						oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nTemGroupId));
						Accounts oAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount","findAccountsOfGroup", oSCMGroupIdParams);
						if(oAccounts!=null && oAccounts.size()>0){
							if(oAccounts.indexOf(nTempAccountId)>-1){
								nSendAccountId = nTempAccountId;
								nSendGroupId = nTemGroupId;
							}
						}
					}
				}
				//3 每个分组下的所有账号id与当前账号id对比，看是否存在。
				
				//4 存在则记录分组id和账号id，传给页面。
				String sPlatform = tempAccount.getPlatform();

				String sAccountName = tempAccount.getAccountName();
				String sPlatChineseName = PlatformFactory.getPlatform(sPlatform).getChineseName();
				if(sPlatform.equalsIgnoreCase("Tencent")){
					if(tempMicroMessage.getUnreadCount(3) > 0){
						if(!hasMessage){hasMessage = true;}
				%>
					<%=CMyString.transDisplay(sAccountName)%>(<%=CMyString.transDisplay(sPlatChineseName)%>)：您有<span onclick="toCommentPage(3,<%=nSendGroupId%>,<%=nSendAccountId%>)" class="messageContentFont"><%=tempMicroMessage.getUnreadCount(3)%>条消息</span><br/>
				<%
					}
				}else{
					if(tempMicroMessage.getUnreadCount(2) > 0){
						if(!hasMessage){hasMessage = true;}
				%>
					<%=CMyString.transDisplay(sAccountName)%>(<%=CMyString.transDisplay(sPlatChineseName)%>)：您有<span onclick="toCommentPage(2,<%=nSendGroupId%>,<%=nSendAccountId%>)" class="messageContentFont"><%=tempMicroMessage.getUnreadCount(2)%>条评论</span><br/>
				<%}
					if(tempMicroMessage.getUnreadCount(3) > 0){
						if(!hasMessage){hasMessage = true;}
				%>
					<%=CMyString.transDisplay(sAccountName)%>(<%=CMyString.transDisplay(sPlatChineseName)%>)：您有<span onclick="toCommentPage(3,<%=nSendGroupId%>,<%=nSendAccountId%>)" class="messageContentFont"><%=tempMicroMessage.getUnreadCount(3)%>条@信息</span><br/>
				<%
					}
				}
			}
		}
	}
		
		if(!hasMessage){
			out.print("没有微博未读消息！");
		}
	%>