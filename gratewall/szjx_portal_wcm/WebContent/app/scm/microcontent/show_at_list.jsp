<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ include file="../include/iframe_check.jsp"%>
<%
	// 1 获取参数
	int nType = currRequestHelper.getInt("Type",1);
	String sPlatLogo = "";
	boolean isHasData = true;
	try{
		sPlatLogo = "../" + PlatformFactory.getPlatform(sPlat).getLogo16();
	}catch(Exception e){
		e.printStackTrace();
	}
	// 7 获取头像列表
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	oProcessor.reset();
	HashMap oAccsOfGrpParams = new HashMap();
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
	Accounts oHeadAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,oAccsOfGrpParams);
	long nTotalNum = 0;
	long nPageCount = 0;
	List<Comment> oComments = null;
	MicroContentWrapper oMCResult = null;
	CommentWrapper oCommentResult = null;
	String sErrorMsg = null;
	if(nType == 1){
		oProcessor.reset();
		HashMap oQueryParams = new HashMap(); 
		oQueryParams.put("AccountId", String.valueOf(nAccountId)); 
		oQueryParams.put("PageIndex", String.valueOf(nPageIndex));
		oQueryParams.put("PageSize", String.valueOf(nPageSize));
		try{
			oMCResult = (MicroContentWrapper) oProcessor.excute("wcm61_scmmicrocontent", "queryMentions",oQueryParams);
		}catch(Exception e){
			sErrorMsg = getErrorMsg(e);
		}
	}else{
		if(!Platform.T163PlatFormName.equalsIgnoreCase(sPlat)){
			oProcessor.reset();
			HashMap oQueryCommentParams = new HashMap(); 
			oQueryCommentParams.put("AccountId", String.valueOf(nAccountId)); 
			try{
				oCommentResult = (CommentWrapper) oProcessor.excute("wcm61_scmcomment", "queryCommentMentions",oQueryCommentParams);}
			catch(Exception e){
				sErrorMsg = getErrorMsg(e);
			}
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
<title>官微管理-@我</title>
<link rel="stylesheet" href="../css/public.css"></style>
<link rel="stylesheet" href="../css/show_face_forward.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_has_background.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/getActualTop.js" type="text/javascript"></script>
<script src="../js/iframe_public.js"></script>
<script src="show_at_list.js"></script>
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
				<div class="v_show" style="width:620px">
					<span class="prev"><img src="../images/leftBtn.png" /></span>
					<div class="v_content" style="width:520px;">
						<div class="v_content_list" style="left:<%=nHeadLeft%>">
							<ul id="headUlStyle">
							<%
							String sAccountIds = "";
							for(int i=1;i<=oHeadAccounts.size();i++){
								Account oHeadAccount = (Account) oHeadAccounts.getAt(i-1);
								if (oHeadAccount == null) continue;
								if(oHeadAccount.getStatus()!=1) continue;
								int nTempAccountId = oHeadAccount.getId();
								if(IS_DEBUG){
									System.out.println("oHeadAccount.getId()"+oHeadAccount.getId());
								}
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
									<div class="userPic" id="user_<%=i%>" onclick="userChangeState(<%=i%>,'show_at_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nTempAccountId%>&Type=1')">
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
				<form action="show_at_list.jsp" method="post" id="scmGroupForm">
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
					<li id="showMicro" onclick="changeAtType('#showMicro','show_at_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&Type=1')" <%if(nType==1){%> class="currentSelected"<%}else{%>class="notSelected"<%}%>>@我的微博</li>
					<%if(!Platform.T163PlatFormName.equalsIgnoreCase(sPlat)){%>
					<li id="sentComment" onclick="changeAtType('#sentComment','show_at_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&Type=2')" <%if(nType==2){%> class="currentSelected"<%}else{%>class="notSelected"<%}%>>@我的评论</li>
					<%}%>
				</ul>
			</div>
		</div>
		<!--@我的微博列表-->
		<%
		if(nType==1){
			List<MicroContent> oMicroContents = null;
			if(oMCResult != null){
				nTotalNum = oMCResult.getTotalNumber();
				oMicroContents = (List<MicroContent>)oMCResult.getMicroContents();
			}
		if(oMicroContents != null && nTotalNum > 0){
			for(int i=1;i<=oMicroContents.size();i++){
				MicroContent tempMicroContent = oMicroContents.get(i-1);
				if(tempMicroContent==null){continue;}
				int nCommentCount = tempMicroContent.getCommentCount();
				int nRepostCount = tempMicroContent.getRepostCount();
				String sBmiddlePic = tempMicroContent.getBmiddlePic();
				boolean bIsFavorite = tempMicroContent.isFavorited();
				String sContent = tempMicroContent.getContent();
				Date dtCrTime = tempMicroContent.getCreateDate();
				String sMicroContentId = tempMicroContent.getId();
				String sThumbnailPic = tempMicroContent.getThumbnailPic();
				if(tempMicroContent.getUser()==null) continue;//当微博被删除后，头像为空就会报错
				String sUserName = tempMicroContent.getUser().getName();
				String sHeadPic = tempMicroContent.getUser().getHead();
				if(CMyString.isEmpty(sHeadPic)){
					sHeadPic = "../images/no_head.png";
				}
				if(IS_DEBUG){
					System.out.println("************主微博（"+i+"）**************");
					System.out.println("nCommentCount："+nCommentCount);
					System.out.println("nRepostCount："+nRepostCount);
					System.out.println("sBmiddlePic："+sBmiddlePic);
					System.out.println("sContent："+sContent);
					System.out.println("dtCrTime"+dtCrTime);
					System.out.println("sMicroContentId："+sMicroContentId);
					System.out.println("sThumbnailPic："+sThumbnailPic);
					System.out.println("sUserName："+sUserName);
					System.out.println("sHeadPic:"+sHeadPic);
				}		
				MicroContent sRetweetedMicroContent = tempMicroContent.getRetweetedMicroContent();
			%>
		<div class="messageContent">
			<div class="userHeadContent">
				<div class="contentHeadPic round_head">
					<img style="width:52px;height:52px;" src="<%=CMyString.filterForHTMLValue(sHeadPic)%>" />
					<span class="logoContent">
						<img src="<%=CMyString.filterForHTMLValue(sPlatLogo)%>" />
					</span>
				</div>
			</div>
			<div class="wordsContent">
			<div class="microContentWords" style="line-height:24px">
				<span class="blueColor" style="float:left"><%=CMyString.transDisplay(sUserName)%>：</span><%=CMyContentTranslate.microContentTranslate(sContent,sPlat)%></div>
				<%
					if(sRetweetedMicroContent!=null){
						String sReBmiddlePic = sRetweetedMicroContent.getBmiddlePic();
						String sReThumbnailPic = sRetweetedMicroContent.getThumbnailPic();
						int nReCommentCount = sRetweetedMicroContent.getCommentCount();
						int nReRepostCount = sRetweetedMicroContent.getRepostCount();
						String sReContent = sRetweetedMicroContent.getContent();
						Date dtReCrTime = sRetweetedMicroContent.getCreateDate();
						String sReMicroContentId = sRetweetedMicroContent.getId();
						if(sRetweetedMicroContent.getUser()!=null){//当微博被删除后，头像为空就会报错
						String sReUserName = sRetweetedMicroContent.getUser().getName(); 
						if(IS_DEBUG){
							System.out.println("************副微博**************");
							System.out.println("sReBmiddlePic："+sReBmiddlePic);
							System.out.println("sReThumbnailPic："+sReThumbnailPic);
							System.out.println("nReCommentCount："+nReCommentCount);
							System.out.println("nReRepostCount："+nReRepostCount);
							System.out.println("sReContent："+sReContent);
							System.out.println("dtReCrTime："+dtReCrTime);
							System.out.println("sReMicroContentId："+sReMicroContentId);
							System.out.println("sReUserName："+sReUserName);
						}
				%>
				<!--如果有二级微博显示-->
				<div class="padding5_0Px positionRelative floatLeft">
					<div class="upTriangle"></div>
					<div class="innerMicroContent">
					<div class="microContentWords" style="line-height:24px">
						<span class="blueColor" style="float:left">@<%=CMyString.transDisplay(sReUserName)%>：</span><%=CMyContentTranslate.microContentTranslate(sReContent,sPlat)%></div>
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
								<%}%></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span>来自于<%=CMyString.transDisplay(CMyString.innerText(sRetweetedMicroContent.getSource()))%>
								</span>
							</span>
							<span class="floatRight">
								<a href="javascript:void(0)" onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sReMicroContentId)%>')">转发<%if(nReRepostCount!=0){%>(<%=nReRepostCount%>)<%}%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
								<a href="javascript:void(0)" onclick="showInnerGiveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','<%=CMyString.filterForJs(sReMicroContentId)%>','show_at_list.jsp');return false;">评论<%if(nReCommentCount!=0){%>(<%=nReCommentCount%>)<%}%></a>
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
						<img src="<%=CMyString.filterForHTMLValue(sThumbnailPic)%>?width=120" onclick="changeImg(this,'<%=CMyString.filterForJs(sThumbnailPic)%>?width=120','<%=CMyString.filterForJs(sBmiddlePic)%>?width=440')"  onload="autoResizeImg(120,120,this)"/>
					<%}else{%>
						<div style="height:10px;"></div>
					<%}%>
				</div>
				<%
					}
				if(tempMicroContent.isRetweeted() && sRetweetedMicroContent==null){
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
				<div class="messageOperate padding_top10Px">
					<span class="floatLeft">
						<span>
						<%if(dtCrTime!=null){%>
							<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtCrTime))%>
						<%}%>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span>来自于<%=CMyString.transDisplay(CMyString.innerText(tempMicroContent.getSource()))%></span>
					</span>
					<%if(!tempMicroContent.isRetweeted() || sRetweetedMicroContent!=null){%>
					<span class="floatRight">
						<a onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>')">转发<%if(nRepostCount!=0){%>(<%=nRepostCount%>)<%}%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
						<%if(bIsFavorite){%>
							<a href="javascript:void(0)" onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',2,'show_at_list.jsp?PageIndex=<%=nPageIndex%>')">取消收藏</a>
						<%}else{%>
							<a href="javascript:void(0)" onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',1,'show_at_list.jsp?PageIndex=<%=nPageIndex%>')">收藏</a>
						<%}%>&nbsp;&nbsp;|&nbsp;&nbsp;
						<a onclick="showGiveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','show_at_list.jsp')">评论<%if(nCommentCount!=0){%>(<%=nCommentCount%>)<%}%></a>
					</span>
					<%}%>
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
								<!--<input type="checkbox" class="leftCheckBox" />同时转发到我的微博-->
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
				if(oMCResult == null && nPageIndex == 1){
					isHasData = false;
			%>
				<div style="padding-left:20px;padding-top:20px;text-align:center;"><font size="4" color="#AD251A">您还没有任何@我的微博信息！有人@你的微博之后才能看哦！</font></div>
			<%
				}else{
				//腾讯返回的TotalNum出现错误
				String alertInfo = "";
				if(nPageCount>1){
					isHasData = true;
					alertInfo = "非常抱歉，该页未获取到@我的微博信息，您可以向前或向后查看。";
				}else{
					isHasData = false;
					alertInfo = "非常抱歉，未获取到@我的微博信息！";
				}
	%>
				<div class="sabrosus"><span class="explainWords">
				<font size="4" color="#AD251A"><%=CMyString.transDisplay(alertInfo)%></font></span></div>
			<%
				}
			}
		}
		//END OF @微博
		}else{ //@我的评论列表
			if(oCommentResult != null){
				nTotalNum = oCommentResult.getTotalNumber();
				oComments = (List<Comment>) oCommentResult.getComments();
				nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
			}
		if(oComments != null && oComments.size() > 0 && nTotalNum > 0){
			for(int i=1;i<=oComments.size();i++){
				Comment	tempComment = oComments.get(i-1);
				if(tempComment==null){continue;}
				String sCommentId = tempComment.getCommentId();
				String sCommentContent = tempComment.getContent();
				Date sCommentCreateDate = tempComment.getCreateDate();
				Comment oReComment = tempComment.getReplyComment();
				MicroUser oCommentMicroUser = tempComment.getUser();
				String sCommentHead = "";
				String sCommentName = "";
				if(oCommentMicroUser != null){
					sCommentHead = oCommentMicroUser.getHead();
					sCommentName = oCommentMicroUser.getName();
				}
				if(CMyString.isEmpty(sCommentHead)){
					sCommentHead = "../images/no_head.png";
				}
				if(IS_DEBUG){
					System.out.println("***********回复内容********");
					System.out.println(sCommentContent);
				}
				MicroContent oCommentReMicroContent = tempComment.getReplyMicroContent();
				String sCommentReMicroContentId = "";
				if(oCommentReMicroContent != null){
					sCommentReMicroContentId = oCommentReMicroContent.getId();
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
					<div class="microContentWords" style="line-height:24px">
						<span  style="color:#0078b6;float:left">
							<%=CMyString.transDisplay(sCommentName)%>：
						</span><%=CMyContentTranslate.microContentTranslate(sCommentContent,sPlat)%><span class="grayColor">(<%=CMyTimeTranslate.getTimeString(sCommentCreateDate)%>)</span>
					</div>
					<%if(oCommentReMicroContent != null){%>
					<p style='line-height:20px;padding-top:5px;padding-bottom:5px;'>
						<div class="microContentWords" style="line-height:24px;font-size:12px">
							<span style="float:left">
								<span class="grayColor">评论</span>
								<span style="color:#0078b6"><%=(oCommentReMicroContent != null ? oCommentReMicroContent.getUser().getName() : "我")%></span>
								<span class="grayColor">的微博：</span>
							</span>
							<span class="blueColor">

								<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sCommentReMicroContentId)%>&BackPage=show_at_list.jsp" target="frame_content"><%=CMyContentTranslate.commentMicroContentTranslate(oCommentReMicroContent.getContent(),sPlat)%></a>

							</span>
						</div>
					</p>
					<%}else{%>
						<div class="positionRelative">
							<div class="innerMicroContent" style="width:520px">
								<span>十分抱歉！原微博已被删除！</span>
							</div>
						</div>
						<div class="clearFloat"></div>
					<%}%>
					<p style="font-size:12px;">
						<span class="grayColor">来自</span>
						<span class="blueColor"><%=CMyString.transDisplay(CMyString.innerText(tempComment.getSource()))%></span>
						<%if(oCommentReMicroContent != null){%>
						<span class="padding_right10Px floatRight blueColor" onclick="showGiveRelayComment('<%=CMyString.filterForHTMLValue(sCommentId)%>')">回复</span>
						<%}%>
						<div class="clearFloat"></div>
					</p>
					<%if(oCommentReMicroContent != null){%>
					<div id="giveComment_<%=CMyString.filterForHTMLValue(sCommentId)%>" class="giveCommentBigDiv">
						<div class="giveCommentTriangle"></div>
						<div class="commentContent">
							<textarea style="overflow-y:auto" name="giveRelay<%=CMyString.filterForHTMLValue(sCommentId)%>" id="giveRelay<%=CMyString.filterForHTMLValue(sCommentId)%>" class="giveCommentText"></textarea>
							<div class="giveCommentBottomDiv">
								<span class="leftFaceSpan">
									<img src="../images/relay_face.png" class="leftFacePic" border=0 />
									<!--<input type="checkbox" class="leftCheckBox" />同时转发到我的微博-->
								</span>
								<span class="rightButton">
									<input onclick="saveRelayComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sCommentReMicroContentId)%>','<%=CMyString.filterForHTMLValue(sCommentId)%>')" type="image" src="../images/relay_btn.png" complete="complete"/>
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
				if(oCommentResult == null && nPageIndex == 1){
					isHasData = false;
			%>
				<div style="padding-left:20px;padding-top:20px;text-align:center;"><font size="4" color="#AD251A">您还没有任何@我的评论信息！有人@您的评论之后才能看哦！</font></div>
			<%
				}else{
				//腾讯返回的TotalNum出现错误
				String alertInfo = "";
				if(nPageCount>1){
					isHasData = true;
					alertInfo = "非常抱歉，该页未获取到@我的评论信息!";
				}else{
					isHasData = false;
					alertInfo = "非常抱歉，未获取到@我的评论信息！";
				}
			%>
				<div class="sabrosus"><span class="explainWords">
				<font size="4" color="#AD251A"><%=CMyString.transDisplay(alertInfo)%></font></span></div>
			<%
				}
			}
		}
		}
		if(IS_DEBUG){
			System.out.println("*********************"+nPageCount);
		}
		if(nTotalNum > 0 && isHasData){
		%>
		<div class="sabrosus">
			<%if(nPageIndex==1){
				//清空未读消息
				oProcessor.reset();
				HashMap oParams = new HashMap();
				oParams.put("AccountId",String.valueOf(nAccountId));
				oParams.put("MessageType",String.valueOf(MicroMessage.UNREAD_AT));
				try{
					oProcessor.excute("wcm61_scmmessage", "setMessageRead",oParams);
				}catch(Exception e){
					System.out.println("清空未读消息失败！");
				}
			%>
				<span class='disabled'> 上一页 </span>
			<%}else{%>
			<a href="show_at_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>&Type=<%=nType%>"> 上一页 </a>
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
				<a href="show_at_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=i%>&Type=<%=nType%>"><%=i%></a>
			<%}}%>
			<%
			if(nPageIndex==nPageCount || nTotalNum==0){
			%>
				<span class='disabled'> 下一页 </span>
			<%
			}else{
			%>
			<a href="show_at_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>&Type=<%=nType%>"> 下一页 </a>
			<%
				}
			%>
		</div>
		<%}%>
		<%@ include file="../include/show_face_forward.jsp"%>
	</body>
</html>