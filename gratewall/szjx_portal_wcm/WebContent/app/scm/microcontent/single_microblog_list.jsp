<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ include file="../include/iframe_check.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>官微管理-首页</title>
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" href="../css/show_face_forward.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_has_background.css" />
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.processbar.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/jquery.ui.processbar.js"></script>
<script src="../js/getActualTop.js" type="text/javascript"></script>
<script src="../js/iframe_public.js"></script>
<script src="single_microblog_list.js"></script>
<script src="../js/show_face.js"></script>
<script>
<!--
$(function(){
	userHeadList(7);
	$("#groupSelect").sSelect();
	$("#groupSelect").change(function(){
		var accountId = $("#groupSelect").find("option:selected").attr("account");
		$("#accountId").val(accountId);
		$("#scmGroupForm").submit();
	});
	$(".dropselectbox").find("h4").css("width","71px");
	$(".dropselectbox").find("ul").css("width","91px");
});
//-->
</script>
</head>
	<body>
	<script>
	<%if(!CMyString.isEmpty(outSysetmMsg)){%>
		alert("<%=outSysetmMsg%>");
		closeBrowser();
	<%}%>
	</script>
	<%
	// 0 调用服务，获取分组下的帐号信息（头像列表）。
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	oProcessor.reset();
	HashMap oAccsOfGrpParams = new HashMap(); 
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
	Accounts oHeadAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,oAccsOfGrpParams);
	// 1 获取微博列表
	oProcessor.reset();
	HashMap oQueryParams = new HashMap();
	oQueryParams.put("AccountId", String.valueOf(nAccountId));
	oQueryParams.put("PageSize", String.valueOf(nPageSize));
	oQueryParams.put("PageIndex", String.valueOf(nPageIndex));
	MicroContentWrapper oMicroContentWrapper = null;
	String sErrorMsg = null;
	try{
		oMicroContentWrapper = (MicroContentWrapper)oProcessor.excute("wcm61_scmmicrocontent","queryUserTimeline",oQueryParams);
	}catch(Exception e){
		sErrorMsg = getErrorMsg(e);
	}
	boolean isHasData = true;
	// 2 获取微博信息
	List<MicroContent> oMicroContent = null;
	long nTotalNum = 0;
	long nPageCount = 0;
	if(oMicroContentWrapper!=null){
		oMicroContent = (List<MicroContent>)oMicroContentWrapper.getMicroContents();
		nTotalNum = oMicroContentWrapper.getTotalNumber();
		nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
	}

	// 3 获取微博平台logo
	String sPlatform = Account.findById(nAccountId).getPlatform();
	String sLogoPath = "";
	try{
		sLogoPath = "../" + PlatformFactory.getPlatform(sPlatform).getLogo16();
	}catch(Exception e){
		e.printStackTrace();
	}
	/*使用java代码控制头像的初始分页*/
	String nHeadLeft = getCurrentLeft(455,7,oHeadAccounts,nAccountId);
	%>
		<div id="userListAll">
			<div class="allUserBtn">
				<div class="allStyle" onclick="getAllAccountMicroContext(<%=nSCMGroupId%>,<%=nPageSize%>,1)">全部</div>
			</div>
			<div class="userContent" style="width: 555px;float:right">
				<div class="v_show" style="width: 555px">
					<span class="prev" ><img src="../images/leftBtn.png" /></span>
					<div class="v_content" style="width: 455px;">
						<div class="v_content_list" style="left:<%=nHeadLeft%>">
							<ul id="headUlStyle">
							<%
							String sAccountIds = "";
							for(int i=1;i<=oHeadAccounts.size();i++){
								Account oHeadAccount = (Account) oHeadAccounts.getAt(i-1);
								if (oHeadAccount == null) continue;
								if(oHeadAccount.getStatus()!=1) continue;
								int nTempAccountId = oHeadAccount.getId(); 
								// 1. 获取帐号头像
								String sHeadPath = getHead(oHeadAccount);

								String sPlatformPath = "";
								//使用平台工厂获取平台logo图片
								try{
									sPlatformPath = "../" + PlatformFactory.getPlatform(oHeadAccount.getPlatform()).getLogo16();
								}catch(Exception e){
									e.printStackTrace();
								}
							%>
								<li>
									<div class="userPic" id="user_<%=i%>" onclick="userChangeState(<%=i%>,'single_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nTempAccountId%>&PageSize=10&PageIndex=1')">
										<img id="user_h<%=i%>" <%if(nAccountId!=nTempAccountId){%>class="grayPic"<%}%> src="<%=CMyString.filterForHTMLValue(sHeadPath)%>" style="width:42px;height:42px;" />
										<span class="logoPic"><img id="user_l<%=i%>" <%if(nAccountId!=nTempAccountId){%>class="grayPic"<%}%> src="<%=CMyString.filterForHTMLValue(sPlatformPath)%>" /></span>
									</div>
								</li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
					<span class="next" style="left: 490px;"><img src="../images/rightBtn.png" /></span>
				</div>
			</div>
		<!--显示用户维护的分组列表-->
		<div style="padding-top:25px;font-size:12px;float:right;margin-right:15px;">
			<form action="single_microblog_list.jsp" method="post" id="scmGroupForm">
				<%
					String sShowTitleGroup = "";
					SCMGroup oTitleSCMGroup = SCMGroup.findById(nSCMGroupId);
					if(oTitleSCMGroup!=null){
						sShowTitleGroup = oTitleSCMGroup.getGroupName();
					}
					
				%>
				<select name="SCMGroupId" class="groupSelect" id="groupSelect" title="<%=CMyString.filterForHTMLValue(sShowTitleGroup)%>">
				<%
				for (int i = 0; i < oGroups.size(); i++) {
					SCMGroup oShowSCMGroup = (SCMGroup) oGroups.getAt(i);
					if(oShowSCMGroup == null) continue;
					//2.2 判断该分组是否有可管理的微博帐号
					int nShowSCMGroupId = oShowSCMGroup.getId();
					String sShowSCMGroupName = oShowSCMGroup.getGroupName();
					//2.3 调用服务获取分组下account的个数
					oProcessor.reset();
					oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nShowSCMGroupId));
					Accounts oTempAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount", "findAccountsOfGroup", oSCMGroupIdParams);
					if(oTempAccounts == null || oTempAccounts.size()==0){continue;}
					
					// 2.4 获取第一个可维护的帐号作为默认帐号。
					int nChangeSessionAccount = 0;
					for (int j = 0; j < oTempAccounts.size(); j++) {
						Account oTeAccount = (Account) oTempAccounts.getAt(j);
						if (oTeAccount != null && oTeAccount.getStatus()==1) {
							nChangeSessionAccount = oTeAccount.getId();
							break;
						}
					}
				%>
					<option value="<%=nShowSCMGroupId%>" title="<%=CMyString.filterForHTMLValue(sShowSCMGroupName)%>" <%if(nSCMGroupId == nShowSCMGroupId){%> selected="selected"<%}%> account="<%=nChangeSessionAccount%>"> 
					<%=CMyString.filterForHTMLValue(CMyString.truncateStr(sShowSCMGroupName,8))%></option>
				<%}%>
				</select>
				<input name="AccountId" value="" type="hidden" id="accountId" />
			</form>
		</div>
		<div style="padding-top:28px;font-size:12px;float:right;width:60px">当前分组：</div>
		<div class="clearFloat"></div>
		</div>
		<div class="clearFloat"></div>
<%
	
	if(oMicroContent!=null && oMicroContent.size() != 0){
	// 4 微博内容遍历
	for(int i=1;i<=oMicroContent.size();i++){
		MicroContent tempMicroContent = oMicroContent.get(i-1);
		String sBmiddlePic = tempMicroContent.getBmiddlePic();
		if(tempMicroContent.getUser()==null) continue;//当微博被删除后，头像为空就会报错
		String sUserHead = tempMicroContent.getUser().getHead();
		if(CMyString.isEmpty(sUserHead)){
			sUserHead = "../images/no_head.png";
		}
		//获取微博信息
		String sUserName = tempMicroContent.getUser().getName();
		String sUserId = tempMicroContent.getUser().getId();
		String sMicroContentId = tempMicroContent.getId();
		String sThumbnailPic = tempMicroContent.getThumbnailPic();
		String sContent = tempMicroContent.getContent();
		boolean bIsFavorite = tempMicroContent.isFavorited();
		Date dtCrTime = tempMicroContent.getCreateDate();
		int nRepostCount = tempMicroContent.getRepostCount();
		int nCommentCount = tempMicroContent.getCommentCount();
%> 
		<!--微博显示-->
		<div class="messageContent" id="microMessage_<%=i%>">
			<div class="userHeadContent">
			<div class="contentHeadPic round_head">
				<img style="width:100%;height:100%;" src="<%=CMyString.filterForHTMLValue(sUserHead)%>" />
			</div>
			<span class="logoContent">
			<img src="<%=CMyString.filterForHTMLValue(sLogoPath)%>" />
			</span>
			</div>
			<div class="wordsContent positionRelative">
			<div class="microContentWords">
			<div class="nickNamecolor"><%=CMyString.transDisplay(sUserName)%>:</div><%=CMyContentTranslate.microContentTranslate(sContent,sPlatform)%></div>
				<%
				MicroContent sRetweetedMicroContent = tempMicroContent.getRetweetedMicroContent();
					if(sRetweetedMicroContent!=null && !CMyString.isEmpty(sRetweetedMicroContent.getContent())){
						String sReBmiddlePic = sRetweetedMicroContent.getBmiddlePic();
						String sReThumbnailPic = sRetweetedMicroContent.getThumbnailPic();
						int nReCommentCount = sRetweetedMicroContent.getCommentCount();
						int nReRepostCount = sRetweetedMicroContent.getRepostCount();
						Date dtReCrTime = sRetweetedMicroContent.getCreateDate();
						String sReContent = sRetweetedMicroContent.getContent();
						String sReMicroContentId = sRetweetedMicroContent.getId();
						if(sRetweetedMicroContent.getUser()!=null){//当微博被删除后，头像为空就会报错
						String sReUserName = sRetweetedMicroContent.getUser().getName();
				%>
				<!--如果有二级微博显示-->
				<div class="padding5_0Px positionRelative floatLeft">
					<div class="upTriangle"></div>
					<div class="innerMicroContent">
					<div class="microContentWords">
						<span class="blueColor floatLeft">@<%=CMyString.transDisplay(sReUserName)%>：</span><%=CMyContentTranslate.microContentTranslate(sReContent,sPlatform)%> </div>
						<%if(!CMyString.isEmpty(sReThumbnailPic)){%>
						<div class="paddingTopBottom5Px">
								<img src="<%=CMyString.filterForHTMLValue(sReThumbnailPic)%>?width=120" onclick="changeImg(this,'<%=CMyString.filterForJs(sReThumbnailPic)%>?width=120','<%=CMyString.filterForJs(sReBmiddlePic)%>?width=440')" onload="autoResizeImg(120,120,this)"/>
						</div>
						<%}%>
						<div class="messageOperate">
							<span class="floatLeft">
								<span>
								<%if(dtReCrTime!=null){%>
									<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtReCrTime))%>
								<%}%>
								</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span><%=CMyString.transDisplay(CMyString.innerText(sRetweetedMicroContent.getSource()))%></span>
							</span>
							<span class="floatRight">
								<a href="javascript:void(0)" onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sReMicroContentId)%>')">转发<%if(nReRepostCount!=0){%>(<%=nReRepostCount%>)<%}%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
								<a href="javascript:void(0)" onclick="showInnerGiveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','<%=CMyString.filterForJs(sReMicroContentId)%>');return false;">评论<%if(nReCommentCount!=0){%>(<%=nReCommentCount%>)<%}%></a>
							</span>
							<div class="clearFloat"></div>
						</div>
						<!--内部评论开始-->
						<div class="clearFloat"></div>
						<div id="giveComment_<%=CMyString.filterForHTMLValue(sMicroContentId)%>_<%=CMyString.filterForHTMLValue(sReMicroContentId)%>" class="giveCommentBigDiv">
							<div class="giveInnerCommentTriangle left624Px"></div>
							<div class="commentContent">
								<textarea style="overflow-y:auto" name="giveRelay<%=CMyString.filterForHTMLValue(sReMicroContentId)%>" id="giveRelay<%=CMyString.filterForHTMLValue(sReMicroContentId)%>_<%=i%>" class="giveCommentText width633Px" autocomplete="off"></textarea>
								<div class="giveCommentBottomDiv">
									<span class="leftFaceSpan">
										<img src="../images/relay_face.png" class="leftFacePic" border=0 /> 
										<!--<input type="checkbox" class="leftCheckBox" />同时转发到我的微博-->
									</span>
									<span class="rightButton">
										<input onclick="saveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sReMicroContentId)%>','<%=CMyString.filterForJs(sReMicroContentId)%>_<%=i%>')" type="image" src="../images/relay_btn.png" complete="complete"/>
									</span>
									<div class="clearFloat"></div>
									<div style="padding:3px 10px 3px 10px;" id="showComments_<%=CMyString.filterForHTMLValue(sMicroContentId)%>_<%=CMyString.filterForHTMLValue(sReMicroContentId)%>">正在努力为您加载......</div>
								</div>
							</div>
						</div>
						<!--内部评论结束-->
					</div>
				</div>
				<%
						}//END OF MicroUser is not null
					}//END OF sRetweetedMicroContent is not null

				//如果是原创微博，显示原创微博的图片内容
				if(!tempMicroContent.isRetweeted()){
				%>
				<!--如果没有二级微博判断图片显示-->
				<div class="padding5_0_Px_cur">
					<%if(!CMyString.isEmpty(sThumbnailPic)){%>
						<img src="<%=CMyString.filterForHTMLValue(sThumbnailPic)%>?width=120" onclick="changeImg(this,'<%=CMyString.filterForJs(sThumbnailPic)%>?width=120','<%=CMyString.filterForJs(sBmiddlePic)%>?width=440')" onload="autoResizeImg(120,120,this)"/>
					<%}else{%>
						<div style="height:10px;"></div>
					<%}%>
				</div>
				<%	
				}	
				if(tempMicroContent.isRetweeted() && (sRetweetedMicroContent==null || CMyString.isEmpty(sRetweetedMicroContent.getContent()))){
				%>
					<!--如果转发微博为空，则做出提示-->
					<div class="padding5_0Px positionRelative floatLeft">
						<div class="upTriangle"></div>
						<div class="innerMicroContent">
							<span>十分抱歉！该转发的原始微博已被删除，您无法查看该转发的原始微博！</span>
							<div class="clearFloat"></div>
						</div>
						<div class="clearFloat"></div>
					</div>
				<%
					}
				%>
				<div class="messageOperateIndex">
					 <span class="floatLeft">
						<span>
						<%if(dtCrTime!=null){%>
							<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtCrTime))%>
						<%}%>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						<span><%=CMyString.transDisplay(CMyString.innerText(tempMicroContent.getSource()))%></span>
					 </span>
					 <span class="floatRight">
						<span class="handType" onclick="deleteOperate(this,'microMessage_<%=i%>',<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',<%=nPageIndex%>,<%=nPageSize%>)">
							<a href="javascript:void(0)">删除</a>
						</span>
						<%if(!tempMicroContent.isRetweeted() || !(sRetweetedMicroContent==null || CMyString.isEmpty(sRetweetedMicroContent.getContent()))){%>
						&nbsp;&nbsp;|&nbsp;&nbsp;
						<span class="handType" onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>')">
							<a href="javascript:void(0)">转发<%if(nRepostCount!=0){%>(<%=nRepostCount%>)<%}%></a>
						</span>&nbsp;&nbsp;|&nbsp;&nbsp;
						<span class="handType">
						<%if(bIsFavorite){%>
							<a href="javascript:void(0)" onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',2,'single_microblog_list.jsp?PageIndex=<%=nPageIndex%>')">取消收藏</a>
						<%}else{%>
							<a href="javascript:void(0)" onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',1,'single_microblog_list.jsp?PageIndex=<%=nPageIndex%>')">收藏</a>
						<%}%>
						</span>&nbsp;&nbsp;|&nbsp;&nbsp;
						<span class="handType" onclick="operateCommentOn('<%=CMyString.filterForJs(sMicroContentId)%>')">
							<a onclick="showGiveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','single_microblog_list.jsp')">评论<%if(nCommentCount!=0){%>(<%=nCommentCount%>)<%}%></a>
						</span>
						<%}%>
					 </span>
					 <div class="clearFloat"></div>
				</div>
				<div class="clearFloat"></div>
				<div id="giveComment_<%=CMyString.filterForHTMLValue(sMicroContentId)%>" class="giveCommentBigDiv">
					<div class="giveCommentTriangle"></div>
					<div class="commentContent">
						<textarea style="overflow-y:auto" name="giveRelay<%=CMyString.filterForHTMLValue(sMicroContentId)%>" id="giveRelay<%=CMyString.filterForHTMLValue(sMicroContentId)%>_<%=i%>" class="giveCommentText" autocomplete="off"></textarea>
						<div class="giveCommentBottomDiv">
							<span class="leftFaceSpan">
								<img src="../images/relay_face.png" class="leftFacePic" border=0 /> 
							</span>
							<span class="rightButton">
								<input onclick="saveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','<%=CMyString.filterForJs(sMicroContentId)%>_<%=i%>')" type="image" src="../images/relay_btn.png" complete="complete"/>
							</span>
							<div class="clearFloat"></div>
							<div style="padding:3px 10px 3px 10px;" id="showComments_<%=CMyString.filterForHTMLValue(sMicroContentId)%>">正在努力为您加载......</div>
						</div>
					</div>
				</div>
			   </div>
			   <div class="clearFloat"></div>
		  </div>
		<%}// END OF FOR loop
		if(oMicroContent.size() == 0){//如果假分页当前没有数据
			%>
			<div class="sabrosus"><span class="explainWords">
			<font size="4" color="#AD251A">非常抱歉，该页未获取到数据，您可以向前查看。</font></span></div>
		<%}%>
	<%}else{
		if(!CMyString.isEmpty(sErrorMsg)){
	%>
		<div class="sabrosus"><span class="explainWords">
					<font size="4" color="#AD251A"><%=sErrorMsg%></font></span></div>
	<%
		return;
		}else{
			if(nPageIndex == 1 && nTotalNum == 0){
				isHasData = false;
			%>
				<div class="sabrosus"><span class="explainWords">
					<font size="4" color="#AD251A">您没有在本系统发布任何微博信息！点击左边的创建微博，创建一条微博吧！</font></span></div>
			<%}else{
				 if(nTotalNum >= (nPageIndex -1)* nPageSize ){%>
				<div class="sabrosus"><span class="explainWords">
					<font size="4" color="#AD251A">非常抱歉，微博平台服务器忙，未获取到当前页数据，请您稍后重试！</font></span></div>
				<%}else{%>
				<div class="sabrosus"><span class="explainWords">
				<font size="4" color="#AD251A">非常抱歉，该页未获取到数据，您可以向前查看。</font></span></div>
			<%
				 }
			}
		}
	}%>
	<%if(isHasData){%>
		<div class="sabrosus">
			<%if(nPageIndex==1){
				//清空未读消息
				oProcessor.reset();
				HashMap oParams = new HashMap();
				oParams.put("AccountId",String.valueOf(nAccountId));
				oParams.put("MessageType",String.valueOf(MicroMessage.UNREAD_MICROCONTENT));
				try{
					oProcessor.excute("wcm61_scmmessage", "setMessageRead",oParams);
				}catch(Exception e){
					System.out.println("清空未读消息失败！");
				}
			%>
				<span class='disabled'> 上一页 </span>
			<%}else{%>
			<a href="single_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>" class="prevPage"> 上一页 </a>
			<%}
			if(nPageIndex != 0 && nPageCount == 0){nPageCount = nPageIndex;}
			int nStartNum = nPageIndex<=5?1:nPageIndex-5;
			int nEndNum = nPageIndex<=5?11:nPageIndex+5;
			for(int i=nStartNum;i<=nEndNum && i<=nPageCount;i++){
				if(i==nPageIndex){
			%>
					<span class="current"><%=i%></span>
			<%
				}else{
			%>
				<a href="single_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=i%>"><%=i%></a>
			<%}}%>
			<%
			if(nPageIndex==nPageCount || nTotalNum==0){
			%>
				<span class='disabled'> 下一页 </span>
			<%
			}else{
			%>
			<a href="single_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>"> 下一页 </a>	
			<%
				}
			%>
		</div>
	<%}%>
	<%@ include file="../include/show_face_forward.jsp"%>
	<!--ProcessBar MaskDom Start-->
		<iframe src="about:blank" style="display:none;" id="process_mask_iframe" FRAMEBORDER="0"></iframe>
	<!--ProcessBar MaskDom Start-->
	</body>
</html>