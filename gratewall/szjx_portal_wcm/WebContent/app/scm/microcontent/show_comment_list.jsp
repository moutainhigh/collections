<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ include file="../include/iframe_check.jsp"%>
<%
	// 1 获取参数
	int nCommentType = currRequestHelper.getInt("CommentType",1);
	long nTotalNum = 0;
	long nPageCount = 0;
	boolean isHasData = true;
	List<Comment> oComments = null;
	
	// 2 获取头像列表数据
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	oProcessor.reset();
	HashMap oAccsOfGrpParams = new HashMap();
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
	Accounts oHeadAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,oAccsOfGrpParams);
	String sPlatLogo = "";
	int nCommentSize = 0;
	// 3 获取微博平台logo
	try{
		sPlatLogo = "../" + PlatformFactory.getPlatform(sPlat).getLogo16();
	}catch(Exception e){
		e.printStackTrace();
	}
	CommentWrapper oResult = null;
	String sErrorMsg = null;
	if(nCommentType == 1){
		oProcessor.reset();
		HashMap oQueryParams = new HashMap();
		oQueryParams.put("AccountId", String.valueOf(nAccountId));
		try{
			oResult = (CommentWrapper) oProcessor.excute("wcm61_scmcomment", "queryCommentsToMe",oQueryParams);
		}catch(Exception e){
			sErrorMsg = getErrorMsg(e);
		}
	}else{
		// 4 获取评论数据
		oProcessor.reset();
		try{
			oResult = (CommentWrapper) oProcessor.excute("wcm61_scmcomment", "queryCommentsByMe");
		}catch(Exception e){
			sErrorMsg = getErrorMsg(e);
		}
	}
	/*使用java代码控制头像的初始分页*/
	String nHeadLeft = getCurrentLeft(520,8,oHeadAccounts,nAccountId);
	out.clear();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>官微管理-首页</title>
<link rel="stylesheet" href="../css/public.css"></style>
<link rel="stylesheet" href="../css/show_face_forward.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_has_background.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/iframe_public.js"></script>
<script src="show_comment_list.js"></script>
<script src="../js/show_face.js"></script>
<script>
<!--
$(function(){
	userHeadList(8);
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
		<div id="userListUl">
			<div class="userContent" style="width:620px;float:right">
				<div class="v_show" style="width:620px;">
					<span class="prev" ><img src="../images/leftBtn.png" /></span>
					<div class="v_content" style="width:520px">
						<div class="v_content_list" style="left:<%=nHeadLeft%>">
							<ul id="headUlStyle">
							<%
							String sAccountIds = "";
							for(int i=1;i<=oHeadAccounts.size();i++){
								 Account oHeadAccount = (Account) oHeadAccounts.getAt(i-1);
								
								if (oHeadAccount == null)
									continue;
								int nTempAccountId = oHeadAccount.getId();

								// 1.获取帐号头像
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
									<div class="userPic" id="user_<%=i%>" onclick="userChangeState(<%=i%>,'show_comment_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nTempAccountId%>&PageSize=5&PageIndex=1&CommentType=1')">
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
					<span class="next" style="left:555px;"><img src="../images/rightBtn.png" /></span>
				</div>
			</div>
			<!--显示用户维护的分组列表-->
			<div style="padding-top:25px;font-size:12px;float:right;margin-right:15px;">
				<form action="show_comment_list.jsp" method="post" id="scmGroupForm">
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
						<option value="<%=nShowSCMGroupId%>" title="<%=CMyString.filterForHTMLValue(sShowSCMGroupName)%>" <%if(nSCMGroupId == nShowSCMGroupId){%> selected="selected"<%}%> account="<%=nChangeSessionAccount%>"> <%=CMyString.filterForHTMLValue(CMyString.truncateStr(sShowSCMGroupName,8))%></option>
					<%}%>
					</select>
					<input name="AccountId" value="" type="hidden" id="accountId" />
				</form>
			</div>
			<div style="padding-top:28px;font-size:12px;float:right;width:60px">当前分组：</div>
			<div class="clearFloat"></div>
		</div>
		<div class="widthBg">
			<div class="ulContainer">
				<ul>
					<li id="showComment" onclick="changeComment('#showComment','show_comment_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=10&PageIndex=1&CommentType=1')" <%if(nCommentType==1){%> class="currentSelected"<%}else{%>class="notSelected"<%}%>>收到的评论</li>
					<li id="sentComment" onclick="changeComment('#sentComment','show_comment_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=10&PageIndex=1&CommentType=2')" <%if(nCommentType==2){%> class="currentSelected"<%}else{%>class="notSelected"<%}%>>发出的评论</li>
				</ul>
			</div>
		</div>
	<%
	if(nCommentType==1){
		// 4 获取评论数据
		if(oResult != null){
			oComments= (List<Comment>)oResult.getComments();
			nTotalNum = oResult.getTotalNumber();
		}
		if(oComments!=null && nTotalNum!=0){
			for(int i=1;i<=oComments.size();i++){
				nCommentSize = oComments.size();
				Comment tempComment = oComments.get(i-1);
				String sCommentId = tempComment.getCommentId();
				String sContent = tempComment.getContent();
				String sCreateDate = CMyTimeTranslate.getTimeString(tempComment.getCreateDate());
				MicroUser oMicroUser = tempComment.getUser();
				if(oMicroUser == null) continue;
				String sCommentUserId = oMicroUser.getId();
				String sCommentHead = oMicroUser.getHead();
				if(CMyString.isEmpty(sCommentHead)){
					sCommentHead = "../images/no_head.png";
				}
				String sCommentName = oMicroUser.getName();
				
				if(IS_DEBUG){
					System.out.println("**************"+i+"***************");
					System.out.println("sCommentId"+sCommentId);
					System.out.println("sContent"+sContent);
					System.out.println("sCreateDate"+sCreateDate);
					System.out.println("sCommentUserId"+sCommentUserId);
					System.out.println("sCommentHead"+sCommentHead);
					System.out.println("sCommentName"+sCommentName);
				}
				
				String sCommentMicroContent = "";
				String sMicroContenId = "";
				MicroContent oRepliedMC = tempComment.getReplyMicroContent();
				if(oRepliedMC != null){
					sCommentMicroContent = oRepliedMC.getContent();
					sCommentMicroContent = CMyContentTranslate.commentMicroContentTranslate(sCommentMicroContent,sPlat);
					sMicroContenId = oRepliedMC.getId();
				}
			%>
				<!--第一条评论-->
				<div class="messageContent">
					<div class="userHeadContent">
						<div class="contentHeadPic round_head">
							<img style="width:100%;height:100%;" src="<%=CMyString.filterForHTMLValue(sCommentHead)%>" />
						</div>
						<span class="logoContent">
							<img src="<%=CMyString.filterForHTMLValue(sPlatLogo)%>" />
						</span>
					</div>
					<div class="relayWordsContent">
						<div class="marginTop0Px microContentWords" style="line-height:22px;">
							<span style="color:#0078b6;float:left"><%=CMyString.transDisplay(sCommentName)%>：</span><%=CMyContentTranslate.microContentTranslate(sContent,sPlat)%>
							<span class="grayColor">(<%=CMyString.transDisplay(sCreateDate)%>)</span>
						</div>
						<%if(oRepliedMC != null){%>
						<p style="line-height:20px;padding-top:5px;padding-bottom:5px;">
						<div class="microContentWords" style="line-height:22px;font-size:12px">
						<span style="float:left">
							<span class="grayColor">评论</span>
							<span style="color:#0078b6"> 我 </span>
							<span class="grayColor">的微博：</span>
						</span>
						<span class="blueColor">
							<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sMicroContenId)%>&BackPage=show_comment_list.jsp" target="frame_content"><%=sCommentMicroContent%></a>
							</span>&nbsp;</div>
						</p>
						<%}else{%>
							<div class="positionRelative">
								<div class="innerMicroContent" style="width:520px">
									<span>十分抱歉！原微博已被删除！</span>
								</div>
							</div>
							<div class="clearFloat"></div>
						<%}%>
						<p style="font-size:12px">
							<span class="grayColor">来自</span>
							<span style ="color:#999"><%=CMyString.transDisplay(CMyString.innerText(tempComment.getSource()))%></span>
							<%if(oRepliedMC != null){%>
							<span class="padding_right10Px floatRight blueColor" onclick="showRelayComment('<%=CMyString.filterForJs(sCommentId)%>')">回复</span>
							<%}%>
							<div class="clearFloat"></div>
						</p>
						<%if(oRepliedMC != null){%>
						<div id="giveComment_<%=CMyString.filterForHTMLValue(sCommentId)%>" class="giveCommentBigDiv">
							<div class="giveCommentTriangle"></div>
							<div class="commentContent">
							<textarea style="overflow-y:auto" name="giveRelay<%=CMyString.filterForHTMLValue(sCommentId)%>" id="giveRelay<%=CMyString.filterForHTMLValue(sCommentId)%>" class="giveCommentText" autocomplete="off"></textarea>
								<div class="giveCommentBottomDiv">
									<span class="leftFaceSpan">
										<img src="../images/relay_face.png" class="leftFacePic" border=0 /> 
										<!--<input type="checkbox" class="leftCheckBox" />同时转发到我的微博-->
									</span>
									<span class="rightButton">
										<input onclick="relayComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sCommentId)%>','<%=CMyString.filterForJs(sMicroContenId)%>')" type="image" src="../images/relay_btn.png" complete="complete"/>
									</span>
									<div class="clearFloat"></div>
								</div>
							</div>
						</div>
						<%}%>
					</div>
					<div class="clearFloat"></div>
				</div>
	<%
		}
		}else{
			if(!CMyString.isEmpty(sErrorMsg)){
			%>
				<div class="sabrosus"><span class="explainWords">
							<font size="4" color="#AD251A"><%=sErrorMsg%></font></span></div>
			<%
				return;
			}else{
				isHasData = false;
	%>
			<div style="padding-left:20px;padding-top:20px;text-align:center;"><font size="4" color="#AD251A">您还没有收到任何评论信息！微博越活跃评论数越多哦！</font></div>
	<%		}
		}
	}else{
		//发出的评论
		if(oResult != null){
			nTotalNum = oResult.getTotalNumber();
			oComments= (List<Comment>)oResult.getComments();
		}
		if(oComments != null && nTotalNum > 0){
			for(int i=1;i<=oComments.size();i++){
				nCommentSize=oComments.size();
				Comment tempComment = oComments.get(i-1);
				String sCommentId = tempComment.getCommentId();
				String sContent = tempComment.getContent();
				String sCreateDate = CMyTimeTranslate.getTimeString(tempComment.getCreateDate());
				MicroUser oMicroUser = tempComment.getUser();
				if(oMicroUser == null) continue;
				String sCommentUserId = oMicroUser.getId();
				String sCommentHead = oMicroUser.getHead();
				if(CMyString.isEmpty(sCommentHead)){
					sCommentHead = "../images/no_head.png";
				}
				String sCommentName = oMicroUser.getName();

				String sCommentMicroContent = "";
				String sMicrocontentId = "";
				MicroContent oRepliedMC = tempComment.getReplyMicroContent();
				if(oRepliedMC != null){
					sCommentMicroContent = oRepliedMC.getContent();
					sCommentMicroContent = CMyContentTranslate.commentMicroContentTranslate(sCommentMicroContent,sPlat);
					sMicrocontentId = oRepliedMC.getId();
				}
				if(IS_DEBUG){
					System.out.println("**************"+i+"***************");
					System.out.println("sCommentId"+sCommentId);
					System.out.println("sContent"+sContent);
					System.out.println("sCreateDate"+sCreateDate);
					System.out.println("sCommentUserId"+sCommentUserId);
					System.out.println("sCommentHead"+sCommentHead);
					System.out.println("sCommentName"+sCommentName);
					System.out.println("sCommentContent"+sCommentMicroContent);
				}
			%>
				<!--第一条评论-->
				<div class="messageContent">
					<div class="userHeadContent">
						<div class="contentHeadPic round_head">
							<img style="width:100%;height:100%;" src="<%=CMyString.filterForHTMLValue(sCommentHead)%>" />
						</div>
						<span class="logoContent">
							<img src="<%=CMyString.filterForHTMLValue(sPlatLogo)%>" />
						</span>
					</div>
					<div class="relayWordsContent">
						<div class="microContentWords" style="line-height:22px">
							<%=CMyContentTranslate.microContentTranslate(sContent,sPlat)%>
							<span class="grayColor">(<%=CMyString.transDisplay(sCreateDate)%>)</span>
						</div>
						<%if(oRepliedMC != null){%>
						<p style='line-height:20px;padding-top:5px;padding-bottom:5px;'>
						<div class="microContentWords" style="line-height:22px;font-size:12px">
						<span style="float:left">
							<span class="grayColor">评论</span>
							<span style="color:#0078b6"><%=CMyString.transDisplay(oRepliedMC.getUser().getName())%></span>
							<span class="grayColor">的微博：</span></span><span class="blueColor">
							<%if(!CMyString.isEmpty(sCommentMicroContent)){%>
							<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sMicrocontentId)%>&BackPage=show_comment_list.jsp" target="frame_content"><%=sCommentMicroContent%></a>
							<%}%>
							</span>&nbsp;
						</div>
						</p>
						<%}else{%>
							<div class="positionRelative">
								<div class="innerMicroContent" style="width:520px">
									<span>被评论的原微博已经删除</span>
								</div>
							</div>
							<div class="clearFloat"></div>
						<%}%>
						<p style="font-size:12px">
							<span class="grayColor">来自</span>
							<span style ="color:#999"><%=CMyString.transDisplay(CMyString.innerText(tempComment.getSource()))%></span>
							<span class="padding_right30Px floatRight blueColor" onclick="deleteComment(<%=nAccountId%>,'<%=CMyString.filterForHTMLValue(sCommentId)%>')">删除</span>
							<div class="clearFloat"></div>
						</p>
					</div>
					<div class="clearFloat"></div>
				</div>
			<%
			}
		}else{
			if(!CMyString.isEmpty(sErrorMsg)){
				%>
				<div class="sabrosus"><span class="explainWords">
							<font size="4" color="#AD251A"><%=sErrorMsg%></font></span></div>
			<%
					return;
			}else{
				isHasData = false;
		%>
			<div style="padding-left:20px;padding-top:20px;text-align:center;"><font size="4" color="#AD251A">您没有发出任何评论信息！点击左边关注的微博，去发出一条评论吧！</font></div>
		<%
			}
		}
	}
		nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
		if(IS_DEBUG){
			System.out.println("*********nPageCount************"+nPageCount);
		}
		if(nTotalNum > 1 && nCommentSize > 0 && isHasData){
		%>
		<div class="sabrosus">
			<%if(nPageIndex==1){
				//清空未读消息
				oProcessor.reset();
				HashMap oParams = new HashMap();
				oParams.put("AccountId",String.valueOf(nAccountId));
				oParams.put("MessageType",String.valueOf(MicroMessage.UNREAD_COMMENT));
				try{
					oProcessor.excute("wcm61_scmmessage", "setMessageRead",oParams);
				}catch(Exception e){
					System.out.println("清空未读消息失败！");
				}
			%>
				<span class='disabled'> 上一页 </span>
			<%}else{%>
			<a href="show_comment_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>&CommentType=<%=nCommentType%>"> 上一页 </a>
			<%}
			if(nPageIndex != 0 && nPageCount == 0){nPageCount = nPageIndex;}
			int nStartNum = nPageIndex<=5?1:nPageIndex-5;
			int nEndNum = nPageIndex<=5?11:nPageIndex+5;
			if(IS_DEBUG){
				System.out.println("nStartNum:"+nStartNum);
				System.out.println("nEndNum:"+nEndNum);
			}			
			for(int i=nStartNum;i<=nEndNum && i<=nPageCount;i++){
				if(i==nPageIndex){
			%>
					<span class="current"><%=i%></span>
			<%
				}else{
			%>
				<a href="show_comment_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=i%>&CommentType=<%=nCommentType%>"><%=i%></a>
			<%}}%>
			<%
			if(nPageIndex==nPageCount){
			%>
				<span class='disabled'> 下一页 </span>
			<%
			}else{
			%>
			<a href="show_comment_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>&CommentType=<%=nCommentType%>"> 下一页 </a>
			<%
				}
			%>
		</div>
		<%}%>
		<%@ include file="../include/show_face_forward.jsp"%>
	</body>
</html>