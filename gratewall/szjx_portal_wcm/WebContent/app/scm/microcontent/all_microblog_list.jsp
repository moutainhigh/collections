<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.scm.persistent.SCMRetweededMC"%>
<%@ page import="com.trs.scm.persistent.SCMRetweededMCs"%>
<%@ page import="com.trs.scm.domain.impl.SCMRetweetedMCMgr"%>
<%@ page import="t4j.TBlogException" %>
<%@ page import="com.trs.scm.domain.ISCMRetweededMCMgr"%>
<%@ include file="../include/iframe_check.jsp"%>
<%
	// 1 获取用户头像
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	oProcessor.reset();
	HashMap oAccsOfGrpParams = new HashMap();
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
	Accounts oHeadAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,oAccsOfGrpParams);
	// 获取查找信息
	String sSearchContent = currRequestHelper.getString("Content");
	boolean bSearch = false;
	boolean isDelete = false;
	if(CMyString.isEmpty(sSearchContent)){
		bSearch = true;
	}
	String sJavaVersion = System.getProperty("java.version");
	String sJava = sJavaVersion.substring(0,3);
	ISCMRetweededMCMgr oSCMRetweededMCMgr = (ISCMRetweededMCMgr) DreamFactory.createObjectById("ISCMRetweededMCMgr");
	out.clear();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>官微管理-全部</title>
<link rel="stylesheet" href="all_microblog_list.css">
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_has_background.css" />
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.processbar.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/jquery.ui.processbar.js"></script>
<script src="../js/iframe_public.js"></script>
<script src="all_microblog_list.js"></script>
<script>
	$(function(){
		userHeadList(7);//头像分页个数
		$(".allStyleGray").bind("click",function(){
			window.location.href="all_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>";
		});
		//判断是否是IE，给搜索词添加黑色，为输入显示灰色字
		if($.browser.msie) {
			$("#searchWords").val("搜索您的内容").css("color","#939393");
			$("#searchWords").focus(function(){
				if($("#searchWords").val()=="搜索您的内容"){
					$("#searchWords").val("");
					$("#searchWords").css("color","#000")
				};
			}).blur(function(){
				if($("#searchWords").val()==""){
					$("#searchWords").val("搜索您的内容").css("color","#939393");
				}else{
					$("#searchWords").css("color","#000")
				};
			});
		}
		<%if(!bSearch){%>
			$("#searchWords").val("<%=sSearchContent%>");
		<%}%>
	});
</script>
</head>
	<body>
		<script>
		<%if(!CMyString.isEmpty(outSysetmMsg)){%>
			alert("<%=outSysetmMsg%>");
			closeBrowser();
		<%}%>
		</script>
		<div id="userListAll">
			<div class="allUserBtn">
				<div class="allStyleGray">全部</div>
			</div>
			<div class="userContent" style="width: 555px;float:right">
				<div class="v_show" style="width: 555px;">
					<span class="prev" ><img src="../images/leftBtn.png" /></span>
					<div class="v_content" style="width: 455px;">
						<div class="v_content_list">
							<ul id="headUlStyle">
		<%
			String sAccountIds = "";
			for(int i=1;i<=oHeadAccounts.size();i++){
				Account oHeadAccount = (Account) oHeadAccounts.getAt(i-1);
				if (oHeadAccount == null)
					continue;
				int nTempAccountId = oHeadAccount.getId();

				String sHeadPath = getHead(oHeadAccount);
				String sPlatformPath = "";
				//使用平台工厂获取平台logo图片
				try{
					sPlatformPath = "../" + PlatformFactory.getPlatform(oHeadAccount.getPlatform()).getLogo16();
				}catch(Exception e){
					e.printStackTrace();
				}
				%>
				<!--显示用户头像 -->
					<li>
						<div class="userPic" id="user_<%=i%>" onclick="userChangeState(<%=i%>,'single_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&AccountId=<%=nTempAccountId%>&PageSize=10&PageIndex=1')">
							<img id="user_h<%=i%>" class="grayPic" src="<%=CMyString.filterForHTMLValue(sHeadPath)%>" style="width:42px;height:42px;" />
							<span class="logoPic"><img id="user_l<%=i%>" class="grayPic" src="<%=CMyString.filterForHTMLValue(sPlatformPath)%>" /></span>
						</div>
					</li>
				<%
				}// END OF FOR LOOP TO SHOW HEADPICs
				%>
					</ul>
				</div>
			</div>
			<span class="next" style="left:490px;"><img src="../images/rightBtn.png" /></span>
			</div>
			</div>
			<!--显示用户维护的分组列表-->
			<div style="padding-top:25px;font-size:12px;float:right;margin-right:15px;">
				<form action="all_microblog_list.jsp" method="post" id="scmGroupForm">
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

			//获取当前分组的微博信息
			oProcessor.reset();
			oSCMGroupIdParams.clear();
			oSCMGroupIdParams.put("SCMGroupId",String.valueOf(nSCMGroupId));
			oSCMGroupIdParams.put("OrderBy","CrTime desc");

			if(!bSearch){
				oSCMGroupIdParams.put("CONTENT", sSearchContent);
			}
			SCMMicroContents oSCMMicroContents = (SCMMicroContents) oProcessor.excute("wcm61_scmmicrocontent", "query",oSCMGroupIdParams);
			if(oSCMMicroContents != null && oSCMMicroContents.size() != 0){
			%>
				<div class="widthBg height49Px">
					<div class="ulContainer height47Px">
						<div class="searchContainer">
							<div class="searchImg" onclick="searchHtml()"></div>
							<div id="searchText" class="searchDivModel">
								<input type="text" name="searchWords" id="searchWords" class="searchWordsStyle" placeholder="搜索您的内容" onkeydown="entersearch()" />
							</div>
						</div>
					</div>
				</div>

			<%}%>
		<div class="clearFloat"></div>
		<%
			boolean bEmpty = false;
			String sNotice = "当前还没有发布任何微博信息！<BR>您可以通过左上角“创建微博”发布第一条微博，也可以通过WCM发布微博！";
			if(oSCMMicroContents == null || oSCMMicroContents.size() == 0){
				bEmpty = true;
				if(!bSearch){
					sNotice = "没有搜到您需要的微博信息！";
				}
			}
			if(bEmpty){
		%>
		<div class="sabrosus"><span class="explainWords"><font size="4"  color="#AD251A">
		<BR><%=sNotice%><BR>
		</font></span></div>
		<%
				return;
			}
			int nTotalNum = oSCMMicroContents.size();
			int nPageCount = nTotalNum/nPageSize;
			if(nTotalNum%nPageSize > 0){
				nPageCount += 1;
			}
			//尾页删除微博后，刷新尾页会出现尾页已无数据的情况
			if(nPageIndex > nPageCount){
				nPageIndex--;
			}
			int nBeginNumber = (nPageIndex-1)*nPageSize;
			int nEndNumber = nPageIndex*nPageSize;
			
			if(nEndNumber > nTotalNum){
				nEndNumber = nTotalNum;
			}
			//循环遍历微博列表
			for(int i=nBeginNumber; i < nEndNumber; i++){
				SCMMicroContent tempSCMMicroContent=(SCMMicroContent)oSCMMicroContents.getAt(i);
				if(tempSCMMicroContent==null){continue;}
				int nSCMMicroContentId = tempSCMMicroContent.getId();
				String sContent = tempSCMMicroContent.getContent();
				String sSource = tempSCMMicroContent.getSource();
				String sPic = tempSCMMicroContent.getPicture();
				String sUser = tempSCMMicroContent.getCrUserName();
				Date dtCrTime = tempSCMMicroContent.getCrTime().getDateTime();
				boolean isRetweeted = tempSCMMicroContent.isRetweeted();
				int nTempGroupId = tempSCMMicroContent.getGroupId();
				int nFlowStatusId = tempSCMMicroContent.getFlowStatusId();
				String sGroupIds = tempSCMMicroContent.getPublishAccountIds();
				String sReMicroContentId = "";
				SCMRetweededMC oSCMRetweededMC = null;
				Accounts oPublishAccounts = null;
				if(isRetweeted){
					sReMicroContentId = tempSCMMicroContent.getRetweetedID();
					oSCMRetweededMC = oSCMRetweededMCMgr.findBySCMMCIdAndMCId(nSCMMicroContentId, sReMicroContentId);
					
					oPublishAccounts = Accounts.findByIds(loginUser, tempSCMMicroContent.getPublishAccountIds());
					
				}
				
				//多级审核中，nFlowStatusId = 16 可能只是一个节点已受理，但还未最终审核完成
				if(nFlowStatusId==10 || nFlowStatusId==0){
		%>
		<div class="messageContent">
			<!--第一条微博-->
			<div class="wordsContent positionRelative widthPer99">
			<%if(!isRetweeted){%>
			<div class="microContentWords">
				<span class="nickNamecolor"><%=CMyString.transDisplay(sUser)%>:</span><%=CMyContentTranslate.sinaContentTranslate(sContent)%></div>
			<%}else{
					if(oPublishAccounts != null && oPublishAccounts.size() > 0){	
			%>
			<div class="microContentWords">
				<span class="nickNamecolor"><%=CMyString.transDisplay(sUser)%>:</span><%=CMyContentTranslate.sinaContentTranslate(sContent)%></div>
			<%}}%>
			<div class="padding5_0Px">
			<%if(isRetweeted){
				if(oSCMRetweededMC!=null){
					String sReBmiddlePic = oSCMRetweededMC.getBmiddlePic();
					String sReThumbnailPic = oSCMRetweededMC.getThumbnailPic();
					Date dtReCrTime = oSCMRetweededMC.getCreateDate().getDateTime();
					String sReContent = oSCMRetweededMC.getContent();
					String sAccountNickName = oSCMRetweededMC.getAccountNickName();
					Account oAccountPlat = (Account)oPublishAccounts.getAt(0);
					String sAccountPlat=oAccountPlat.getPlatform();
					if(sAccountNickName !=null){//当微博被删除后，头像为空就会报错
				%>

				<div class="padding5_0Px positionRelative floatLeft">
				<div class="upTriangle"></div>
				<div class="innerAllMicroContent marginBottom10">
				<div class="microContentWords">
					<span class="blueColor floatLeft">@<%=CMyString.transDisplay(sAccountNickName)%>：</span>
					<%=CMyContentTranslate.microContentTranslate(sReContent,sAccountPlat)%></div>
					<%if(!CMyString.isEmpty(sReThumbnailPic)){%>
					<div class="paddingTopBottom5Px">
						<img src="<%=CMyString.filterForHTMLValue(sReThumbnailPic)%>?width=120" onclick="changeImg(this,'<%=CMyString.filterForJs(sReThumbnailPic)%>?width=120','<%=CMyString.filterForJs(sReBmiddlePic)%>?width=440')" onload="autoResizeImg(120,120,this)"/>
					</div>
					<%}else{%>
						<div style="height:5px;"></div>
					<%}%>
					<div class="messageOperateIndex">
						<span>
							<span class="time">
							<%if(dtReCrTime!=null){%>
								<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtReCrTime))%>
							<%}%>
							</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="source"><%=CMyString.transDisplay(CMyString.innerText(oSCMRetweededMC.getSource()))%></span>
						</span>
					</div>
				</div>
			</div>
			<%}}else{
			%>
				<!--如果转发微博为空，则做出提示-->
				<div class="padding5_0Px positionRelative floatLeft">
					<div class="upTriangle"></div>
					<div class="innerAllMicroContent">
						<span>十分抱歉！该转发的原始微博已被删除，您无法查看该转发的原始微博！</span>
						<div class="clearFloat"></div>
					</div>
					<div class="clearFloat"></div>
				</div>
			<%	}
			}else{%>
				<%if(sPic!=null && !sPic.equals("")){
					String sImgType = sPic.substring(sPic.length()-3);
					//判断是否是jdk1.5和是否为gif图片，gif图片jdk1.5不支持缩略图显示
					if(sImgType.equalsIgnoreCase("gif") && sJava.equals("1.5")){
					%>
						<img src="../file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sPic)%>&Width=120" onclick="allChangePic2(this,'<%=CMyString.filterForJs(sPic)%>')" class="cursorBig" width="120" width="120" isBig="1" onload="autoResizeImg2(120,120,this)"/>
					<%
						}else{
					%>
					<img src="../file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sPic)%>&ScaleWidth=120" onclick="allChangePic(this,'<%=CMyString.filterForJs(sPic)%>')" class="cursorBig" onload="autoResizeImg(120,120,this)"/>
				<%}}else{%>
					<div style="height:5px;"></div>
				<%}
			}%>
			<div style="height:15px;"></div>
			</div>
			<div class="divTable" style="height:130px;padding-bottom:11px;" id="divTable<%=i%>">
				<div class="transcommenttable" id="tableForward<%=i%>">
					<div style="height:120px;width:140px;">正在为您加载！</div>
				</div>
				<img class="imgTriangle" style="position:absolute;top:131px;right:10px;z-index:1002;" src="../images/triangle.png" />
			</div>
			<!--转评对比-->
			<div class="clearFloat"></div>
				<div class="messageOperateIndex">
					<span class="floatLeft">
						<span> <%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtCrTime))%> </span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span>来自<%=CMyString.transDisplay(CMyString.innerText(sSource))%></span>
					</span>
					<span class="floatRight">
						<img src="../images/duibi.png" class="pointerHand" onclick="showRepostNum(<%=i%>,<%=nSCMMicroContentId%>,<%=nSCMGroupId%>)" />
					</span>
					<span class="floatRight deleteStyle" style="cursor:auto;">|</span>
					<span class="floatRight deleteStyle" onclick="deleteMicroBlog(this,<%=nSCMMicroContentId%>,<%=nPageIndex%>,<%=nPageSize%>,<%=bSearch%>,'<%=sSearchContent%>')">删除</span>
					<div class="clearFloat"></div>
				</div>
			</div>
			<div class="clearFloat"></div>
		</div>
		<%
				}else{// 1、2、15是在审核状态
		%>
		<!--审核中的微博-->
		<!--第二条微博-->
		<div class="messageContent">
			<div class="wordsContent positionRelative widthPer99">
			<div class="microContentWords">
				<span class="nickNamecolor"><%=CMyString.transDisplay(sUser)%>:</span><%=CMyContentTranslate.sinaContentTranslate(sContent)%></div>
				<div class="padding5_0Px">
				<%if(isRetweeted){

					String sServiceIdOfMicroContentService = "wcm61_scmmicrocontent";
					String sMethodNameOfFindMicroContent = "findByMicroContentId";
					HashMap oHashMap = new HashMap();
					oHashMap.put("MicroContentId", sReMicroContentId);
					oHashMap.put("AccountId",String.valueOf(oPublishAccounts.getAt(0).getId()));
					MicroContent oCurrMicroContent = null;
					try{
						oCurrMicroContent = (MicroContent)oProcessor.excute(sServiceIdOfMicroContentService,sMethodNameOfFindMicroContent,oHashMap);
					}catch(Exception e){
						//屏蔽微博已被删除的错误
						if(e instanceof WeiboException && ((WeiboException)e).getErrorCode() != 20101){
								throw e;
						}
						if(e instanceof TBlogException && ((TBlogException)e).getMessageCode() != 40402){
								throw e;
						}
					}
				if(oCurrMicroContent != null){
					if(oCurrMicroContent.isRetweeted()){
						oCurrMicroContent = oCurrMicroContent.getRetweetedMicroContent();
					}
					String sReBmiddlePic = oCurrMicroContent.getBmiddlePic();
					String sReThumbnailPic = oCurrMicroContent.getThumbnailPic();
					Date dtReCrTime = oCurrMicroContent.getCreateDate();
					String sReContent = oCurrMicroContent.getContent();
					Account oAccountPlatform = (Account)oPublishAccounts.getAt(0);
					String sAccountPlatform=oAccountPlatform.getPlatform();
					if(oCurrMicroContent.getUser()!=null){//当微博被删除后，头像为空就会报错
						String sAccountNickName = oCurrMicroContent.getUser().getName();
				%>

				<div class="padding5_0Px positionRelative floatLeft">
				<div class="upTriangle"></div>
				<div class="innerAllMicroContent marginBottom10">
				<div class="microContentWords">
					<span class="blueColor floatLeft">@<%=CMyString.transDisplay(sAccountNickName)%>：</span>
					<%=CMyContentTranslate.microContentTranslate(sReContent,sAccountPlatform)%></div>
					<%if(!CMyString.isEmpty(sReThumbnailPic)){%>
					<div class="paddingTopBottom5Px">
						<img src="<%=CMyString.filterForHTMLValue(sReThumbnailPic)%>?width=120" onclick="changeImg(this,'<%=CMyString.filterForJs(sReThumbnailPic)%>?width=120','<%=CMyString.filterForJs(sReBmiddlePic)%>?width=440')" onload="autoResizeImg(120,120,this)"/>
					</div>
					<%}else{%>
						<div style="height:5px;"></div>
					<%}%>
					<div class="messageOperateIndex">
						<span>
							<span class="time">
							<%if(dtReCrTime!=null){%>
								<%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtReCrTime))%>
							<%}%>
							</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="source">
							<%=CMyString.transDisplay(CMyString.innerText(oCurrMicroContent.getSource()))%>
							</span>
						</span>
					</div>
				</div>
			</div>
				<%}
					isDelete=false;
				}else{
					isDelete=true;
			%>
				<!--如果转发微博为空，则做出提示-->
				<div class="padding5_0Px positionRelative floatLeft">
					<div class="upTriangle"></div>
					<div class="innerAllMicroContent">
						<span>十分抱歉！该转发的原始微博已被删除，您无法查看该转发的原始微博！</span>
						<div class="clearFloat"></div>
					</div>
					<div class="clearFloat"></div>
				</div>
			<%	}}else{%>
					<%if(sPic!=null && !sPic.equals("")){
					String sImgType = sPic.substring(sPic.length()-3);
					if(sImgType.equalsIgnoreCase("gif") && sJava.equals("1.5")){
					%>
						<img src="../file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sPic)%>&Width=120" onclick="allChangePic2(this,'<%=CMyString.filterForJs(sPic)%>')" class="cursorBig" width="120" width="120" isBig="1" onload="autoResizeImg2(120,120,this)"/>

					<%
						}else{
					%>
					<img src="../file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sPic)%>&ScaleWidth=120" onclick="allChangePic(this,'<%=CMyString.filterForJs(sPic)%>')" class="cursorBig"  onload="autoResizeImg(120,120,this)"/>


					<%}}else{%>
						<div style="height:5px;"></div>
					<%}
					isDelete=false;
					}%>
					<div style="height:15px;"></div>
				</div>
				<div class="divTable2" id="divTable<%=i%>">
					<div class="tableForward2" id="tableForward<%=i%>">
						<div style="height:120px;width:140px;">正在为您加载！</div>
					</div>
					<img class="imgFlowTriangle" src="../images/triangle.png" />
				</div>
				<div class="clearFloat"></div>
				<div class="messageOperateIndex">
					<span class="floatLeft">
						<span> <%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtCrTime))%> </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						<span>来自<%=CMyString.transDisplay(CMyString.innerText(sSource))%></span>
					</span>
					<span class="floatRight">
						<img src="../images/check_state.png" class="pointerHand" onclick="showFlowContent(<%=i%>,<%=nSCMMicroContentId%>)" />
					</span>
					<%if(isDelete){%>
					<span class="floatRight deleteStyle" style="cursor:auto;">|</span>
					<span class="floatRight deleteStyle" onclick="deleteMicroMessage(<%=nSCMMicroContentId%>)">删除</span>
					<%}%>
					<div class="clearFloat"></div>
				</div>
			</div>
			<div class="clearFloat"></div>
		</div>
		<!--审核中的微博-->
		<%
				}
			}
		%>
		<div class="sabrosus">
			<%if(nPageIndex==1){%>
				<span class='disabled'> 上一页 </span>
			<%}else{%>
			<a href="all_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%><%if(!bSearch){%>&Content=<%=sSearchContent%><%}%>" class="prevPage"> 上一页 </a>
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
				<a href="all_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=i%><%if(!bSearch){%>&Content=<%=sSearchContent%><%}%>"><%=i%></a>
			<%}}%>
			<%
			if(nPageIndex==nPageCount || nPageCount==0){
			%>
				<span class='disabled'> 下一页 </span>
			<%
			}else{
			%>
			<a href="all_microblog_list.jsp?SCMGroupId=<%=nSCMGroupId%>&PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%><%if(!bSearch){%>&Content=<%=sSearchContent%><%}%>"> 下一页 </a>
			<%
				}
			%>
		</div>
		<!--ProcessBar MaskDom Start-->
		<iframe src="about:blank" style="display:none;" id="process_mask_iframe" FRAMEBORDER="0"></iframe>
		<!--ProcessBar MaskDom Start-->
	</body>
</html>