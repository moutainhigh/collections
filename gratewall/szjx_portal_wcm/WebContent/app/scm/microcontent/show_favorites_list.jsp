<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ include file="../include/iframe_check.jsp"%>
<%
	// 1 获取参数
	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日   HH:mm");
	if(IS_DEBUG){
		System.out.println("**********nAccountId************:"+nAccountId);
		System.out.println("**********nSCMGroupId************:"+nSCMGroupId);
		System.out.println("**********PageIndex************:"+nPageIndex);
		System.out.println("**********nPageSize************:"+nPageSize);
	}
	
	String sPlatLogo = sPlat;
	boolean isHasData = true;
	try{
		sPlatLogo = "../" + PlatformFactory.getPlatform(sPlatLogo).getLogo16();
	}catch(Exception e){
		e.printStackTrace();
	}
	// 6 获取帐号头像列表
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	oProcessor.reset();
	HashMap oAccsOfGrpParams = new HashMap(); 
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId)); 
	Accounts oHeadAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,oAccsOfGrpParams); 
	// 7 调用服务
	oProcessor.reset();
	HashMap oQueryParams = new HashMap(); 
	oQueryParams.put("AccountId", String.valueOf(nAccountId));
	oQueryParams.put("PageIndex", String.valueOf(nPageIndex));
	oQueryParams.put("PageSize", String.valueOf(nPageSize));
	long nTotalNum = 0;
	long nPageCount = 0;
	List<Favorite> oFavorites = null;
	FavoriteWrapper oFavoriteWrapper = null;
	String sErrorMsg = null;
	try{
		oFavoriteWrapper = (FavoriteWrapper)oProcessor.excute("wcm61_scmfavorite","getFavorites",oQueryParams);
	}catch(Exception e){
		sErrorMsg = getErrorMsg(e);
	}
	/*使用java代码控制头像的初始分页*/
	String nHeadLeft = getCurrentLeft(520,8,oHeadAccounts,nAccountId);
	out.clear();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>官微管理-收藏</title>
<link rel="stylesheet" href="../css/public.css"></style>
<link rel="stylesheet" href="../css/show_face_forward.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_has_background.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/getActualTop.js" type="text/javascript"></script>
<script src="../js/iframe_public.js"></script>
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
								if(IS_DEBUG){
									System.out.println("oHeadAccount.getId()"+oHeadAccount.getId());
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
									<div class="userPic" id="user_<%=i%>" onclick="userChangeState(<%=i%>,'show_favorites_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nTempAccountId%>&PageSize=10&PageIndex=1')">
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
				<form action="show_favorites_list.jsp" method="post" id="scmGroupForm">
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
		<div class="clearFloat"></div>
		</div>
		<div class="height10Px"></div>


		<!--微博列表-->
		<%
		if(oFavoriteWrapper!=null){
			nTotalNum = oFavoriteWrapper.getTotalNumber();
			oFavorites = oFavoriteWrapper.getFavorites();
		}
	if(oFavorites!=null && nTotalNum > 0){//有收藏内容
		nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
		if(oFavorites.size() == 0){
			nPageCount++;
		}
		if(IS_DEBUG){
			System.out.println("*******nTotalNum**********"+nTotalNum);
			System.out.println("*******nPageCount**********"+nPageCount);
		}
			for (int i = 1; i <= oFavorites.size(); i++) {
				Favorite tempFavorite = oFavorites.get(i-1);
				if (tempFavorite == null) continue;
				MicroContent oMicroContent = tempFavorite.getMicroContent();
				if (oMicroContent==null) continue;
				String sFavoriteTime = sDateFormat.format(tempFavorite.getFavoriteTime());
				String sMicroContentId = oMicroContent.getId();
				String sMicroContent = oMicroContent.getContent();
				String sThumbnailPic = oMicroContent.getThumbnailPic();
				String sBmiddlePic = oMicroContent.getBmiddlePic();
				int nRepostCount = oMicroContent.getRepostCount();
				Date dtCrTime = oMicroContent.getCreateDate();
				int nCommentCount = oMicroContent.getCommentCount();
				MicroUser oMicroUser = oMicroContent.getUser();
				if(oMicroUser==null) continue;
				String sName = oMicroUser.getName();
				String sHeadPic = oMicroUser.getHead();
				if(CMyString.isEmpty(sHeadPic)){
					sHeadPic = "../images/no_head.png";
				}
				if(IS_DEBUG){
					System.out.println("*************************"+ i +"**************************");
					System.out.println("-------------------------"+i+"---------------------");
					System.out.println("sFavoriteTime:"+sFavoriteTime);
					System.out.println("sMicroContentId:"+sMicroContentId);
					System.out.println("sMicroContent:"+sMicroContent);
					System.out.println("sHeadPic:"+sHeadPic);
					System.out.println("sThumbnailPic:"+sThumbnailPic);
					System.out.println("sName:"+sName);
					System.out.println("nRepostCount:"+nRepostCount);
					System.out.println("nCommentCount:"+nCommentCount);
				}
				MicroContent sRetweetedMicroContent = oMicroContent.getRetweetedMicroContent();
		%>
		<div class="messageContent padding_top5Px" id="favorite_<%=CMyString.filterForHTMLValue(sMicroContentId)%>">
			<div class="favoriteTimeShow">
				收藏于<%=CMyString.transDisplay(sFavoriteTime)%>
			</div>
			<div class="userHeadContent">
				<div class="contentHeadPic round_head">
				<img style="width:100%;height:100%;" src="<%=CMyString.filterForHTMLValue(sHeadPic)%>" />
			</div>
				<span class="logoContent">
					<img src="<%=CMyString.filterForHTMLValue(sPlatLogo)%>" />
				</span>
			</div>
			<div class="wordsContent">
			<div class="microContentWords">
				<span class="nickNamecolor"><%=CMyString.transDisplay(sName)%>:</span><%=CMyContentTranslate.microContentTranslate(sMicroContent,sPlat)%></div>
				<%
					if(sRetweetedMicroContent!=null){
						String sReBmiddlePic = sRetweetedMicroContent.getBmiddlePic();
						String sReThumbnailPic = sRetweetedMicroContent.getThumbnailPic();
						int nReCommentCount = sRetweetedMicroContent.getCommentCount();
						int nReRepostCount = sRetweetedMicroContent.getRepostCount();
						String sReContent = sRetweetedMicroContent.getContent();
						Date dtReCrTime = sRetweetedMicroContent.getCreateDate();
						String sReMicroContentId = sRetweetedMicroContent.getId();
						MicroUser oReMicroUser = sRetweetedMicroContent.getUser();
						if(oReMicroUser!=null){
						String sReUserName = oReMicroUser.getName();
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
								<span>
								<%if(dtReCrTime!=null){%>
									<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtReCrTime))%>
								<%}%>
								</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span>来自于<%=CMyString.innerText(sRetweetedMicroContent.getSource())%></span>
							</span>
							<span class="floatRight">
								<a href="javascript:void(0)" onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sReMicroContentId)%>')">转发<%if(nReRepostCount!=0){%>(<%=nReRepostCount%>)<%}%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
								<a href="javascript:void(0)" onclick="showInnerGiveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','<%=CMyString.filterForJs(sReMicroContentId)%>','show_favorites_list.jsp');return false;">评论<%if(nReCommentCount!=0){%>(<%=nReCommentCount%>)<%}%></a>
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
				<div class="messageOperate">
					<span class="floatLeft">
						<span>
						<%if(dtCrTime!=null){%>
							<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtCrTime))%>
						<%}%>
						</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span>来自于<%=CMyString.innerText(oMicroContent.getSource())%></span></span>
					<%if(oMicroContent.isRetweeted() && sRetweetedMicroContent==null){%>
					<span class="floatRight">
						<a onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',2,'show_favorites_list.jsp?PageIndex=<%=nPageIndex%>')">取消收藏</a>
					</span>
					<%}else{%>
					<span class="floatRight">
						<a onclick="showForward(<%=nSCMGroupId%>,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>')">转发<%if(nRepostCount!=0){%>(<%=nRepostCount%>)<%}%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
						<a onclick="collecte(this,<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>',2,'show_favorites_list.jsp?PageIndex=<%=nPageIndex%>')">取消收藏</a>&nbsp;&nbsp;|&nbsp;&nbsp;
						<a onclick="showGiveComment(<%=nAccountId%>,'<%=CMyString.filterForJs(sMicroContentId)%>','show_favorites_list.jsp')">评论<%if(nCommentCount!=0){%>(<%=nCommentCount%>)<%}%></a>
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
			if(oFavorites.size() == 0){//如果网易假分页当前没有数据
			%>
			<div class="sabrosus"><span class="explainWords">
			<font size="4" color="#AD251A">非常抱歉，该页未获取到数据，您可以向前查看。</font></span></div>
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
				if(oFavoriteWrapper == null && nPageIndex != 1){
				//腾讯返回的TotalNum出现错误
			%>
					<div class="sabrosus"><span class="explainWords">
					<font size="4" color="#AD251A">非常抱歉，该页未获取到数据，您可以向前查看。</font></span></div>
			<%
					//设置值使显示分页
					nTotalNum = 1;
					nPageCount = nPageIndex;
				}else{
					isHasData = false;
			%>
					<div class="sabrosus"><span class="explainWords">
					<font size="4" color="#AD251A">您没有收藏任何微博，您需要先收藏微博，然后才能查看哦！</font></span></div>
			<%
				}
			}
		}
		if(nTotalNum > 0 && isHasData){
		%>
			<div class="sabrosus">
				<%if(nPageIndex==1){%>
					<span class='disabled'> 上一页 </span>
				<%}else{%>
				<a href="show_favorites_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>" class="prevPage"> 上一页 </a>
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
					<a href="show_favorites_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=i%>"><%=i%></a>
				<%}}%>
				<%
				if(nPageIndex==nPageCount || nTotalNum==0){
				%>
					<span class='disabled'> 下一页 </span>
				<%
				}else{
				%>
				<a href="show_favorites_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nAccountId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>"> 下一页 </a>
				<%
					}
				%>
			</div>
		<%}%>
		<%@ include file="../include/show_face_forward.jsp"%>
		</div>
	</body>
</html>