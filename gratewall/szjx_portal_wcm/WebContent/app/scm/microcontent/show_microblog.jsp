<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ include file="../include/iframe_check.jsp"%>
<%
	// 1 获取参数
	String sMicroContentId = currRequestHelper.getString("MicroContentId");
	String sBackPage = currRequestHelper.getString("BackPage");
	if(CMyString.isEmpty(sBackPage)){
		sBackPage="single_microblog_list.jsp";
	}
	String sMicroContentId1= sMicroContentId;
	// 2 调用服务，获取分组下的帐号信息（头像列表）。
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	HashMap oAccsOfGrpParams = new HashMap(); 
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
	Accounts oHeadAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,oAccsOfGrpParams);

	// 3 获取微博信息
	HashMap oQueryParams = new HashMap();
	oQueryParams.put("AccountId", String.valueOf(nAccountId));
	oQueryParams.put("MicroContentId", sMicroContentId);
	oQueryParams.put("PageSize", String.valueOf(nPageSize));
	oQueryParams.put("PageIndex", String.valueOf(nPageIndex));
	// 获取评论
	CommentWrapper oResult = (CommentWrapper) oProcessor.excute("wcm61_scmcomment","queryCommentsByMCId",oQueryParams);

	MicroContent oMicroContent = null;
	int nCommentCount = 0;
	try{
		oMicroContent = (MicroContent)oProcessor.excute("wcm61_scmmicrocontent", "findByMicroContentId", oQueryParams);
	}catch(Exception weiboException){
		String sErrorMsg = weiboException.getMessage();
		String sExpriedDetail = "新浪微博错误代码： 21327！错误信息：expired_token";
		if(sErrorMsg.indexOf(sExpriedDetail) > 0){
			String url = "../account/binding_account.jsp?SCMGroupId=" + nSCMGroupId + "&ExtraType=1";
			response.sendRedirect(url);
			return;
		}else{
			throw weiboException;
		}
	}

	// 4 获取微博平台logo
	String sLogoPath = "";
	try{
		sLogoPath = "../" + PlatformFactory.getPlatform(sPlat).getLogo16();
	}catch(Exception e){
		e.printStackTrace();
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
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" href="../css/show_face_forward.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_has_background.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/getActualTop.js" type="text/javascript"></script>
<script src="../js/iframe_public.js"></script>
<script src="single_microblog_list.js"></script>
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
								//只去当前头像
								//if(nTempAccountId != nAccountId) continue;
								if(IS_DEBUG){
									System.out.println("oHeadAccount.getId()" + oHeadAccount.getId());
								}
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
									<div class="userPic" id="user_<%=CMyString.filterForHTMLValue(sMicroContentId)%>" onclick="userChangeState(<%=CMyString.filterForHTMLValue(sMicroContentId)%>,'<%=sBackPage%>?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nTempAccountId%>&PageSize=10&PageIndex=1')">
										<img id="user_h<%=CMyString.filterForHTMLValue(sMicroContentId)%>" <%if(nAccountId!=nTempAccountId){%>class="grayPic"<%}%> src="<%=CMyString.filterForHTMLValue(sHeadPath)%>" style="width:42px;height:42px;" />
										<span class="logoPic"><img id="user_l<%=CMyString.filterForHTMLValue(sMicroContentId)%>" <%if(nAccountId!=nTempAccountId){%>class="grayPic"<%}%> src="<%=CMyString.filterForHTMLValue(sPlatformPath)%>" /></span>
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
					<option value="<%=nShowSCMGroupId%>" title="<%=CMyString.filterForHTMLValue(sShowSCMGroupName)%>" <%if(nSCMGroupId == nShowSCMGroupId){%> selected="selected"<%}%> account="<%=nChangeSessionAccount%>"> <%=CMyString.filterForHTMLValue(CMyString.truncateStr(sShowSCMGroupName,8))%></option>
				<%}%>
				</select>
				<input name="AccountId" value="" type="hidden" id="accountId" />
			</form>
		</div>
		<div style="padding-top:28px;font-size:12px;float:right;width:60px">当前分组：</div>
		<div class="clearFloat"></div>
		</div>
<%
		// 微博内容遍历
		if(oMicroContent!=null){//如果微博内容不为空，则显示微博内容
		MicroUser oUser = oMicroContent.getUser();
		if(oUser==null) System.out.println("头像为空！");;//当微博被删除后，头像为空就会报错
		String sUserHead = oUser.getHead();
		if(CMyString.isEmpty(sUserHead)){
			sUserHead = "../images/no_head.png";
		}
		//获取微博信息
		String sUserName = oUser.getName();
		String sUserId = oUser.getId();
		String sThumbnailPic = oMicroContent.getThumbnailPic();
		String sBmiddlePic = oMicroContent.getBmiddlePic();
		String sContent = oMicroContent.getContent();
		boolean bIsFavorite = oMicroContent.isFavorited();
		Date dtCrTime = oMicroContent.getCreateDate();
		int nRepostCount = oMicroContent.getRepostCount();
		nCommentCount = oMicroContent.getCommentCount();
		if(IS_DEBUG){
			System.out.println("微博id:"+sMicroContentId);
		}
%>
		<!--微博显示-->
		<div class="messageContent" id="microMessage_<%=CMyString.filterForHTMLValue(sMicroContentId)%>" style="border:0px;">
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
				<span class="nickNamecolor"><%=CMyString.transDisplay(sUserName)%>:</span><%=CMyContentTranslate.microContentTranslate(sContent,sPlat)%></div>
				<%
				MicroContent sRetweetedMicroContent = oMicroContent.getRetweetedMicroContent();
					if(sRetweetedMicroContent!=null){
						String sReBmiddlePic = sRetweetedMicroContent.getBmiddlePic();
						String sReThumbnailPic = sRetweetedMicroContent.getThumbnailPic();
						int nReCommentCount = sRetweetedMicroContent.getCommentCount();
						int nReRepostCount = sRetweetedMicroContent.getRepostCount();
						Date dtReCrTime = sRetweetedMicroContent.getCreateDate();
						String sReContent = sRetweetedMicroContent.getContent();
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
							System.out.println("sReMicroContentId："+sReMicroContentId);
							System.out.println("sReUserName："+sReUserName);
						}
				%>
				<!--如果有二级微博显示-->
				<div class="padding5_0Px positionRelative floatLeft">
					<div class="upTriangle"></div>
					<div class="innerMicroContent">
					<div class="microContentWords">
						<span class="blueColor floatLeft">@<%=CMyString.transDisplay(sReUserName)%>：</span><%=CMyContentTranslate.microContentTranslate(sReContent,sPlat)%></div>
						<%if(!CMyString.isEmpty(sReThumbnailPic)){%>
						<div class="paddingTopBottom5Px">
							<img src="<%=CMyString.filterForHTMLValue(sReThumbnailPic)%>?width=120" onclick="changeImg(this,'<%=CMyString.filterForJs(sReThumbnailPic)%>?width=120','<%=CMyString.filterForJs(sReBmiddlePic)%>?width=440')" onload="autoResizeImg(120,120,this)"/>
						</div>
						<%}%>
						<div class="messageOperate">
							<span class="floatLeft">
								<a href="javascript:void(0)">
								<%if(dtReCrTime!=null){%>
									<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtReCrTime))%>
								<%}%>
								</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span><%=CMyString.transDisplay(CMyString.innerText(sRetweetedMicroContent.getSource()))%></span>
							</span>
							<span class="floatRight">
								<a href="javascript:void(0)" onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sReMicroContentId)%>')">转发<%if(nReRepostCount!=0){%>(<%=nReRepostCount%>)<%}%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
								<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sReMicroContentId)%>&BackPage=<%=sBackPage%>">原文评论<%if(nReCommentCount!=0){%>(<%=nReCommentCount%>)<%}%></a>
							</span>
						</div>
						<div class="clearFloat"></div>
					</div>
				</div>
				<%
						}//End OF User is not null
					}// END of sRetweetedMicroContent is not null
					//如果是原创微博，显示原创微博的图片内容
				if(!oMicroContent.isRetweeted()){
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
				if(oMicroContent.isRetweeted() && sRetweetedMicroContent==null){
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
							<span><%=CMyString.transDisplay(CMyString.innerText(oMicroContent.getSource()))%></span>
					 </span>
					 <%if(!oMicroContent.isRetweeted() || sRetweetedMicroContent!=null){%>
					 <span class="floatRight">
						<span class="handType" onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>')">
							<a href="javascript:void(0)">转发<%if(nRepostCount!=0){%>(<%=nRepostCount%>)<%}%></a>
						</span>&nbsp;&nbsp;|&nbsp;&nbsp;
						<span class="handType">
						<%if(bIsFavorite){%>
							<a href="javascript:void(0)" onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',2,'show_microblog.jsp?PageIndex=<%=nPageIndex%>&BackPage=<%=sBackPage%>&MicroContentId=<%=CMyString.filterForJs(sMicroContentId)%>')">取消收藏</a>
						<%}else{%>
							<a href="javascript:void(0)" onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',1,'show_microblog.jsp?PageIndex=<%=nPageIndex%>&BackPage=<%=sBackPage%>&MicroContentId=<%=CMyString.filterForJs(sMicroContentId)%>')">收藏</a>
						<%}%>
						</span>&nbsp;&nbsp;|&nbsp;&nbsp;
						<span class="handType" onclick="operateCommentOn('<%=CMyString.filterForJs(sMicroContentId)%>')">
						<script>
							function showCommentDown(microContentId){
								$("#giveComment_"+microContentId).slideToggle();
							}
						</script>
							<a onclick="showCommentDown('<%=CMyString.filterForJs(sMicroContentId)%>')">评论<%if(nCommentCount!=0){%>(<%=nCommentCount%>)<%}%></a>
						</span>
					 </span>
					 <%}%>
				</div>
				<div class="clearFloat"></div>
				<div id="giveComment_<%=CMyString.filterForHTMLValue(sMicroContentId)%>">
					<div class="giveCommentTriangle"></div>
					<div class="commentContent">
						<textarea style="overflow-y:auto" name="giveRelay<%=CMyString.filterForHTMLValue(sMicroContentId)%>" id="giveRelay<%=CMyString.filterForHTMLValue(sMicroContentId)%>" class="giveCommentText" autocomplete="off"></textarea>
						<div class="giveCommentBottomDiv">
							<span class="leftFaceSpan">
								<img src="../images/relay_face.png" class="leftFacePic" border=0 /> 
							</span>
							<span class="rightButton">
								<input onclick="saveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','<%=CMyString.filterForJs(sMicroContentId)%>')" type="image" src="../images/relay_btn.png" complete="complete"/>
							</span>
							<div class="clearFloat"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearFloat"></div>
		</div>
		<%}%>
		<!--微博评论-->
		<%
		long nTotalNum = nCommentCount;
		long nPageCount = 0;
		if(oResult!=null){	
		%>
		<div style="line-height:1.75;font-size:12px;font-weight:bold;padding-left:3%;">全部评论<%if(!sPlat.equals("T163")){%>(<%=nTotalNum%>)<%}%></div>
		<%
			List<Comment> oComments  =(List<Comment>)oResult.getComments();
			if(oComments != null && nTotalNum != 0){
				nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
				if(oComments.size() == 0){
					nPageCount++;
				}

			if(IS_DEBUG){
				System.out.println("*******nTotalNum**********"+nTotalNum);
				System.out.println("*******nPageCount**********"+nPageCount);
			}
			if(nTotalNum<=0){
				nTotalNum=0;
			}
		// 2.结果
		%>
		<div class="commentContentList" style="border-top:2px solid #ccc;">
			<ul style="width:100%;list-style:none;margin:0px;padding:0px;">
		<%
			for(int j=1;j<=oComments.size();j++){
				Comment tempComment = oComments.get(j-1);
				if(tempComment==null){continue;}
				String sHeadPathPic = tempComment.getUser().getHead();
				if(CMyString.isEmpty(sHeadPathPic)){
					sHeadPathPic = "../images/no_head.png";
				}
				String sHeadName = tempComment.getUser().getName();
				String lastTime = CMyTimeTranslate.getTimeString(tempComment.getCreateDate());
				String sCommentId = tempComment.getCommentId();
				String sCommentContent = tempComment.getContent();
				if(IS_DEBUG){
					System.out.println("*********第"+j+"条评论*************");
					System.out.println("tempComment.getCommentId():"+sCommentId);
					System.out.println("tempComment.getContent():"+sCommentContent);
					System.out.println("lastTime:"+lastTime);
					System.out.println("sHeadPathPic:"+sHeadPathPic);
				}
			%>
			<li style="width:100%;margin:0px;padding-top:10px;padding-bottom:10px;zoom:1;border-bottom:1px dotted gray;">
				<div style="margin-left:10px;float:left">
					<span>
						<img src="<%=CMyString.filterForHTMLValue(sHeadPathPic)%>" style="width:50px;height:50px;border: 1px solid #C0C0C0;-moz-border-radius: 8px;-webkit-border-radius: 8px;border-radius: 8px;" border=0 />
					</span>
				</div>
				<div style="width:700px;float:right;line-height:22px;">
					<div style="width:690px;zoom:1;">
						<div class="microContentWords" style="font-size:12px;">
							<span style="color:#0078b6;white-space:nowrap"><%=CMyString.transDisplay(sHeadName)%>:</span><%=CMyContentTranslate.microContentTranslate(tempComment.getContent(),sPlat)%>
						</div>
						<div style="margin-top:5px;font-size:12px">
							<span style="float:left;" class="blueColor">
								<%=lastTime%>
							</span>
							<span class="floatRight blueColor" style="cursor:pointer;padding-right:15px" onclick="showRelayComment('<%=CMyString.filterForJs(sCommentId)%>')">回复
							</span>
							<div class="clearFloat"></div>
						</div>
					</div>
					<div id="giveComment_<%=CMyString.filterForHTMLValue(sCommentId)%>" style="width:686px" class="giveCommentBigDiv">
						<div class="giveCommentTriangle" style="left:652px"></div>
						<div class="commentContent">
							<textarea style="overflow-y:auto;width:660px" name="giveRelay<%=CMyString.filterForHTMLValue(sCommentId)%>" id="giveRelay<%=CMyString.filterForHTMLValue(sCommentId)%>" class="giveCommentText" autocomplete="off"></textarea>
							<div class="giveCommentBottomDiv">
								<span class="leftFaceSpan">
									<img src="../images/relay_face.png" class="leftFacePic" border=0 /> 
									<!--<input type="checkbox" class="leftCheckBox" />同时转发到我的微博-->
								</span>
								<span class="rightButton">
									<input onclick="relayComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sCommentId)%>','<%=CMyString.filterForJs(sMicroContentId)%>')" type="image" src="../images/relay_btn.png" complete="complete"/>
								</span>
								<div class="clearFloat"></div>
							</div>
						</div>
					</div>
				</div>
				<div style="clear:both"></div>
			</li>
			<%
				}// END OF FOR LOOP
			if(oComments.size() == 0){//如果假分页当前没有数据
				%>
				<div class="sabrosus"><span class="explainWords">
				<font size="4" color="#AD251A">非常抱歉，该页未获取到数据，您可以向前查看。</font></span></div>
			<%
			}
			%>
			</ul>
			<!--分页的地方-->
			<div style="clear:both"></div>
			<div class="sabrosus">
			<%if(nPageIndex==1){%>
				<span class='disabled'> 上一页 </span>
			<%}else{%>
			<a href="show_microblog.jsp?AccountId=<%=nAccountId%>&SCMGroupId=<%=nSCMGroupId%>&MicroContentId=<%=CMyString.filterForHTMLValue(sMicroContentId)%>&PageIndex=<%=(nPageIndex-1)%>&BackPage=<%=sBackPage%>" target="_self" class="prevPage"> 上一页 </a>
			<%}
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
				<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sMicroContentId)%>&PageIndex=<%=i%>&BackPage=<%=sBackPage%>" target="_self"><%=i%></a>
			<%}}%>
			<%
			if(nPageIndex==nPageCount || nTotalNum==0){
			%>
				<span class='disabled'> 下一页 </span>
			<%
			}else{
			%>
			<a href="show_microblog.jsp?MicroContentId=<%=CMyString.filterForHTMLValue(sMicroContentId)%>&PageIndex=<%=(nPageIndex+1)%>&BackPage=<%=sBackPage%>" target="_self"> 下一页 </a>
			<%
				}
			%>
		</div>
		<%
		}
		}else{// END if Result not null
		%>
			<div style="color:#777777;font-size:12px;padding-left:4px;padding-left:91px;">暂时还没有人评论过这条微博，亲给个评论吧！<div>
		<%
		}
		%>
		</div>
		<%@ include file="../include/show_face_forward.jsp"%>
	</body>
</html>