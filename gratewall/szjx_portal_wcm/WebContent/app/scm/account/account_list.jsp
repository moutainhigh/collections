<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.scm.persistent.Account"%>
<%@ page import="com.trs.scm.persistent.Accounts"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.cms.auth.persistent.Users"%>
<%@ page import="com.trs.scm.domain.impl.SCMGroupMgr"%>
<%@ page import="com.trs.scm.domain.impl.SCMAccountMgr"%>
<%@ page import="com.trs.scm.sdk.model.MicroUser"%>
<%@ page import="com.trs.scm.sdk.model.Platform"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@ include file="../../include/public_server.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帐号管理-全部</title>
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" href="account_list.css">
<link rel="stylesheet" type="text/css" href="../css/count_common.css" />
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_no_border.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/getActualTop.js" type="text/javascript"></script>
<script src="../js/iframe_public.js"></script>
<script src="account_management.js"></script>
</head>
<body>
<%
	//获取分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",10);
	int nPageIndex = currRequestHelper.getInt("PageIndex",1);
	int nPageStart = (nPageIndex-1)*nPageSize;
	int nPageEnd = nPageStart+nPageSize-1;
	
	HashMap parameters = new HashMap();
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);

	//获取分组
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",1);
	String sServiceId = "wcm61_scmaccount" , sMethodName = "findAccountsOfGroup";
	parameters.put("SCMGroupId", String.valueOf(nSCMGroupId));
	Accounts oAccounts = (Accounts) processor.excute(sServiceId,sMethodName, parameters);
	processor.reset();

	int nPageCount = (oAccounts.size()%nPageSize==0)?(oAccounts.size()/nPageSize):(oAccounts.size()/nPageSize+1);
	
	//获取用户管理的分组
	parameters.clear();
	sServiceId = "wcm61_scmgroup" ; sMethodName = "getGroupsOfUser";
	parameters.put("UserId",String.valueOf(loginUser.getId()));
	SCMGroups oSCMGroups = (SCMGroups) processor.excute(sServiceId,sMethodName, parameters);
	processor.reset();
%>

<div class="operateGroup">
	<span style="float:left"><img src="../images/groupPic.png" border=0 class="margin_6PxBottom" />
	该组共有<%=oAccounts.size()%>个微博帐号：&nbsp;</span>
	<%
		//当前用户为分组维护人员时，才显示添加帐号权限
		SCMGroup oCurrGroup = SCMGroup.findById(nSCMGroupId);
		Users oAdmins = (new SCMGroupMgr()).getAdmins(oCurrGroup);
		boolean bIsAdminOfGroup = false;
		if(oAdmins.indexOf(loginUser) >=0 || SCMAuthServer.isAdminOfSCM(loginUser))
			bIsAdminOfGroup = true;

		if(bIsAdminOfGroup){
	%>
	<span><a href="binding_account.jsp?SCMGroupId=<%=nSCMGroupId%>" target="frame_content" class="bindaccount" title="为当前分组绑定微博帐号">绑定帐号</a></span>
	<%
		}
	%>
	
</div>
<%
	if(oAccounts == null || oAccounts.size() == 0){
%>
		<div class="sabrosus"><span class="explainWords">
		<font size="4">尚未绑定微博帐号。<br/><br/></font>
	</span>
	</div>
<%
	}else{
		boolean bError = false;
		for(int i = nPageStart;i<=nPageEnd && i<oAccounts.size();i++){
			Account oAccount = (Account) oAccounts.getAt(i);
			if(oAccount==null)
				continue;
			int nAccountId = oAccount.getId();
			parameters.clear();
			parameters.put("AccountId", String.valueOf(nAccountId));
			// 2. 发送请求
			processor.reset();
			MicroUser oMicroUser = null;
			String sHeadPath = "";
			String sLocation = "";
			Boolean bMale = true;
			long nFriendsCount = 0;
			long nFollowersCount = 0;
			long nMicroContentCount = 0;
			String sErrorMsg = "";
			Platform oPlatform = PlatformFactory.getPlatform(oAccount.getPlatform());
			try{
				oMicroUser = (MicroUser) processor.excute("wcm61_scmmicrouser", "findRelateMicroUser", parameters);
				sHeadPath = oMicroUser.getHead();
				sLocation = oMicroUser.getLocation();
				bMale = oMicroUser.isMale();
				nFriendsCount = oMicroUser.getFriendsCount();
				nFollowersCount = oMicroUser.getFollowersCount();
				nMicroContentCount = oMicroUser.getMicroContentCount();
			}catch(Exception e){
				e.printStackTrace();
				if(!bError){bError = true;}
				String sPlatform = oPlatform.getChineseName();
				if(e instanceof CMyException){
					CMyException ex = (CMyException) e;
					sErrorMsg =ex.getMyMessage();
				}else{
					sErrorMsg = e.getMessage();
				}
				sErrorMsg = sErrorMsg.length() > 98 ? sErrorMsg.substring(0,98) + "..." : sErrorMsg;
			}
			
			//设置默认头像
			if(CMyString.isEmpty(sHeadPath)){
				sHeadPath = "../file/read_image.jsp?FileName="+oAccount.getHeadPic();
			}
			//如果还为空，不显示头像
			if(CMyString.isEmpty(oAccount.getHeadPic())){
				sHeadPath = "../images/no_head.png";
			}
			String sPlatformPath = "";
			try{
				sPlatformPath = "../" + oPlatform.getLogo16();
			}catch(Exception e){
				e.printStackTrace();
			}
			String sSexPic = "";
			if(bMale){
				sSexPic = "../images/boy.png";
			}else{
				sSexPic = "../images/girl.png";
			}
%>

<div class="personalCount" id="count1">
	<div class="userHeadContent">
		<div class="contentHeadPic"><img src="<%=CMyString.filterForHTMLValue(sHeadPath)%>" style="width:42px;height:42px;border: 1px solid #C0C0C0;-moz-border-radius: 8px;-webkit-border-radius: 8px;border-radius: 8px;" /></div>
		<span style="position:absolute;top:36px;left:36px;">
			<img src=<%=CMyString.filterForHTMLValue(sPlatformPath)%> />
		</span>
	</div>
	 <div class="expressOperate">
<%
	//若获取帐号信息没有出现异常，则显示帐号信息
	if(oMicroUser != null){
%>
		<div class="countNickNameContainer">
			<div class="express">
				<span class="countNickName"><%=CMyString.transDisplay(oAccount.getAccountName()) %></span>：<%=CMyString.transDisplay(sLocation)%>&nbsp;
				<img src=<%=CMyString.filterForHTMLValue(sSexPic)%> class="margin_5pxBottom" />
			</div>
			<div class="height31Px">关注 <%=nFriendsCount%> &nbsp;<span class="blackFont">|</span> &nbsp;粉丝  &nbsp;<%=nFollowersCount%>|&nbsp; 微博 <%=nMicroContentCount%></div>
		</div>
	<!--若出现异常，则进行提示-->
	<%}else{%>
	<div class="countNickNameContainer" style="width:500px;padding-top:1px;line-height:20px;">
			<font size="2px">非常抱歉，无法获取[<span class="countNickName"><%=CMyString.transDisplay(oAccount.getAccountName())%></span>]帐号信息。提示：<%=sErrorMsg%></font>
	</div>
	<%}%>
		<!-- 帐号操作 --->
		<div class="porsonlOperate">
			<span class="userOperaterbox">
			<span id="addaccounttogroup" class="account_to_group" href="javascript:void(0)">设置所属分组</span>
			<div class="account_to_group_list" >
				<%
				boolean bChecked = false;
				for(int j = 0;j < oSCMGroups.size();j++){
					bChecked = false;
					SCMGroup oSCMGroup = (SCMGroup)oSCMGroups.getAt(j);
					int nBoxGroupId = oSCMGroup.getId();
					String sGroupName = oSCMGroup.getGroupName();
					if(oSCMGroup == null)
						continue;
					int nGroupId = oSCMGroup.getId();
					parameters.clear();
					parameters.put("SCMGroupId", String.valueOf(nGroupId));
					Accounts oGroupAccounts = (Accounts) processor.excute("wcm61_scmaccount","findAccountsOfGroup", parameters);
					processor.reset();
					if(oGroupAccounts == null)
						bChecked = false;
					for(int x = 0;x < oGroupAccounts.size();x++){
						Account oGroupAccount = (Account) oGroupAccounts.getAt(x);
						if(oGroupAccount == null)
							continue;
						if(nAccountId == oGroupAccount.getId())
							bChecked = true;
					}
				%>
					<div class="groupSet_option">
						<div class="groupSet_inputSpan"><input onclick="addorremoveaccount(this);" name="<%=nAccountId%>" type="checkbox" <% if(bChecked){ %> checked <%}%> value="<%=nBoxGroupId%>"/></div>
						<div class="groupSet_groupName"><%=CMyString.transDisplay(sGroupName)%></div>
						<div style="clear:both"></div>
					</div>
				<%}%>
			</div>
			</span> |
			<%
			if(false){
			%>
			<span class="marginLeftRight10Px pointerHand">
				<a href="change_account_info.jsp?ObjectId=<%=oAccount.getId()%>" target="frame_content" class="modifySourceColor">修改资料</a>
			</span>|
			<%}%>
			<span onclick="relieveBind(<%=nSCMGroupId%>,<%=oAccount.getId()%>)" class="marginLeftRight10Px pointerHand">解除绑定</span>
		</div>
	 </div>
</div>

<% 
	} // END OF FOR LOOP
	if(bError){
%>
	<div style="margin-left: 50px;">
	<div style="float:left;height:45px;width:45px; background: url(../images/alert_36.png);">
	</div>
	<div class="explainWords" style="float:left;width:600px;line-height:23px;padding-top:1px;">
		<font size="2px">若帐号Token失效，您可以使用上方“绑定帐号”操作，重新绑定这些帐号；其它错误请尝试刷新页面。<br/>若微博平台仍无法正常返回帐号，您可暂时将出现异常的帐号解除绑定，稍后尝试重新绑定这些帐号。</font>
	</div>
	<div style="clear:both;"></div>
	</div>
<%}%>

	<div class="sabrosus">
	<%if(nPageIndex==1){%>
		<span class='disabled'> 上一页 </span>
	<%}else{%>		
	<a href="account_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>" class="prevPage"> 上一页 </a>
	<%}
	int nStartNum = nPageIndex<=5?1:nPageIndex-5;
	int nEndNum = nPageIndex<=5?11:nPageIndex+5;
				
	for(int i=nStartNum;i<=nEndNum && i<=nPageCount;i++){
		if(i==nPageIndex){
	%>
			<span class="current"><%=i%></span>
	<%
		}else{
	%>
		<a href="account_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=i%>"><%=i%></a>
	<%}}%>			
	<%
	if(nPageIndex==nPageCount || nPageCount==0){
	%>
		<span class='disabled'> 下一页 </span>
	<%
	}else{
	%>
	<a href="account_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>"> 下一页 </a>	
	<%
		}
	}
	%>
</div>
</body>
</html>