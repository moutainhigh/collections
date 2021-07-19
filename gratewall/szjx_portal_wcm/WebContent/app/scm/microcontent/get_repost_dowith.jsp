<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContents" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%! static final boolean IS_DEBUG = false;%>
<%
	HashMap hParameters = new HashMap();
	int nSCMGrouptId = currRequestHelper.getInt("SCMGroupId",1);
	int nNum = currRequestHelper.getInt("Num",0);
	int nSCMMicroContentId = currRequestHelper.getInt("SCMMicroContentId",0);
	SCMMicroContent oSCMMicroContent = SCMMicroContent.findById(nSCMMicroContentId);
	boolean isRetweeted = false;
if(oSCMMicroContent!=null){
	isRetweeted = oSCMMicroContent.isRetweeted();
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	hParameters.put("SCMGroupId", String.valueOf(nSCMGrouptId));
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	Accounts oGroupAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,hParameters);
	int divWidth = oGroupAccounts.size()*66+66;
	out.clear();
%>
<div style="width:<%=divWidth%>px">
<ul class="tableStyle">
	<li class="fontTitle">
		<div class="compareUserPic"></div>
		<div style="margin-right:-10px;">被转发数：</div>
		<div style="margin-right:-10px;">被评论数：</div>
	</li>
	<%
	// 1.2 获取转评数据
	oProcessor.reset();
	HashMap oParameters = new HashMap();
	oParameters.put("SCMMicroContentId",String.valueOf(nSCMMicroContentId));
	Map<Integer, MicroContent> oAccountMicroContent = (HashMap<Integer, MicroContent>) oProcessor.excute("wcm61_scmmicrocontent", "findPostedMicroContents",oParameters);
	Accounts oPostedAccounts = (Accounts) oProcessor.excute("wcm61_scmmicrocontent", "findPostedAccounts",oParameters);

	for(int j=1;j<=oGroupAccounts.size();j++){
		Account oGroupAccount = (Account) oGroupAccounts.getAt(j-1);
		int nGroupAccountId = oGroupAccount.getId();
		if (oGroupAccount == null) continue;
		if(oGroupAccount.getStatus()!=1) continue;
		if(IS_DEBUG){
			System.out.println("oGroupAccount.getId()" + oGroupAccount.getId());
		}
		// 2.获取帐号头像
		String sGroupHeadPath = oGroupAccount.getHeadPic();
		if(CMyString.isEmpty(sGroupHeadPath)){
			sGroupHeadPath = "../images/no_head.png";
		}else{
			sGroupHeadPath = "../file/read_image.jsp?FileName="+sGroupHeadPath;
		}

		String sGroupPlatformPath = "../images/"+oGroupAccount.getPlatform().toLowerCase()+"_logo.png";
		boolean bHasTheId = false;
		MicroContent oMicroContent = oAccountMicroContent.get(nGroupAccountId);
		if(oMicroContent != null || oPostedAccounts.indexOf(nGroupAccountId) >= 0){
			bHasTheId = true;
		}
		if(bHasTheId){
		%>
		<li class="compareHeadList">
			<div class="compareUserPic">
				<span>
					<img src="<%=CMyString.filterForHTMLValue(sGroupHeadPath)%>" class="small_head_size round_head" />
				</span>
				<span class="compareLogoPic top30Px">
					<img src="<%=CMyString.filterForHTMLValue(sGroupPlatformPath)%>" />
				</span>
			</div>
			<%if(oMicroContent != null){%>
				<div><%=oMicroContent.getRepostCount()%></div>
				<div><%=oMicroContent.getCommentCount()%></div>
			<%}else{%>
				<div>--</div>
				<div>--</div>
			<%}%>
		</li>
		<%}else{%>
		<li class="compareHeadList">
			<div class="compareUserPic" id='compare<%=""+nNum+j+nGroupAccountId%>' <%if(!isRetweeted){%> onclick="userChangeStatus('compare<%=""+nNum+j+nGroupAccountId%>',<%=nGroupAccountId%>,<%=nSCMMicroContentId%>)" <%}%>>
				<span><img src="<%=CMyString.filterForHTMLValue(sGroupHeadPath)%>" class="grayPic small_head_size round_head" <%if(!isRetweeted){%> title="补发微博" <%}else{%> title="转发的微博不能补发！" <%}%>/></span>
				<span class="compareLogoPic top30Px">
					<img src="<%=CMyString.filterForHTMLValue(sGroupPlatformPath)%>" class="grayPic" <%if(!isRetweeted){%> title="补发微博" <%}else{%> title="转发的微博不能补发！" <%}%>/>
				</span>
			</div>
			<div>--</div>
			<div>--</div>
		</li>
	<%}}%>
</ul>
<%}else{%>
<ul class="tableStyle" style="padding-top:12px;">
	您查询对比评论的微博不存在！
</ul>
<%}%>
</div>