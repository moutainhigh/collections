<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="weibo4j.model.WeiboException" %>
<%@ page import="t4j.TBlogException" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.trs.scm.sdk.util.CMyContentTranslate" %>
<%@ page import="com.trs.scm.sdk.util.CMyTimeTranslate" %>
<%@ page import="com.trs.ajaxservice.ProcessService" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.cms.process.definition.Flows" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ page import="com.trs.scm.process.FlowContentSCMMicroContentImpl" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%@ include file="../include/register_check.jsp"%>
<%! static final boolean IS_DEBUG = false;%>

<%
		// 获取参数
		int nPageSize = currRequestHelper.getInt("PageSize",10);
		int nPageIndex = currRequestHelper.getInt("PageIndex",1);

		// 设置参数
		String sServiceIdOfProcessService = "wcm61_scmworkflow";
		String sMethodNameOfQueryReviewedList = "queryFlowContentsDoneByUser";
		
		HashMap oHashMap = new HashMap();
		oHashMap.put("UserId", loginUser.getId());
		// 其他属性：
		// CurrUser、SearchValue、OrderBy
		JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
		IFlowContent[] oResult = (IFlowContent[])oProcessor.excute(sServiceIdOfProcessService,sMethodNameOfQueryReviewedList,oHashMap);

		//设置分页总数
		int nTotalNum = oResult == null ? 0 : oResult.length;
		
		int nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
		if(IS_DEBUG){
			System.out.println("nTotalNum: "+nTotalNum);
			System.out.println("nPageCount: "+nPageCount);
		}
%>

<!DOCTYPE html>
<html>
<head>
<title>审核管理-已审</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" href="microblog_checked_list.css">
<link rel="stylesheet" href="microblog_checking_list.css">
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/iframe_public.js"></script>
<script src="microblog_checked_list.js"></script>

</head>
	<body class="bodyColor">
		<div id="userList" class="lineHeight63">
			<div class="showSelectContain">
				<a href="microblog_checking_list.jsp" target="frame_content"><span style="color:#0078b6">待审</span></a>&nbsp;<span class="grayColor">|</span>&nbsp;<span class="currentSelect">已审</span>
				<div class="clearFloat"></div>
			</div>
		</div>
		<%
		int nStart = (nPageIndex - 1) * nPageSize;
		int nEnd = nTotalNum == 0 ? 0 : ((nPageCount == nPageIndex) ? nTotalNum : nPageIndex * nPageSize);
	//无审核微博提示：
	if(nEnd == 0){
%>
<div class="sabrosus"><span class="explainWords">
	<font size="4" color="#AD251A">无审核微博</font></span></div>
<%
	return;
	}
			for(int i = nEnd-1; i >= nStart; i--){
				if(oResult[i] == null){
					continue;
				}
				FlowContentSCMMicroContentImpl oCurrFlowContent = null;
				StringBuffer ReviewURLString = new StringBuffer();
				FlowDoc oProcessAction = null;

				if(oResult[i] instanceof FlowContentSCMMicroContentImpl){
						oCurrFlowContent = (FlowContentSCMMicroContentImpl)oResult[i];
						SCMMicroContent  oCurrSCMMicroContent = (SCMMicroContent) oCurrFlowContent.getSubstance();
						if(oCurrSCMMicroContent != null){
							int nCurrContentId = oCurrSCMMicroContent.getId();
							String sContent = oCurrSCMMicroContent.getContent();
							String sPicPath = oCurrSCMMicroContent.getPicture();
							String sPassedTime = CMyDateTime.getStr(oCurrSCMMicroContent.getReviewTime(),
									CMyDateTime.DEF_DATETIME_FORMAT_PRG);

							//获取指定文档的流转轨迹
							IFlowServer oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
							FlowDocs oFlowDocsResult = oFlowServer.getFlowDocs(oCurrFlowContent, null);
							String sProcessTime = "";
							int nSize = oFlowDocsResult.size();
							ReviewURLString.append("<div class=\"reviewList\">");
							for (int j = 0; j < nSize; j++) {
								FlowDoc oCurrFlowDoc = (FlowDoc) oFlowDocsResult.getAt(j);
								if(oCurrFlowDoc == null){
									continue;
								}
								
								sProcessTime = CMyDateTime.getStr(oCurrFlowDoc
										.getPostTime(), CMyDateTime.DEF_DATETIME_FORMAT_PRG);
								if(j == 0 && CMyString.isEmpty(sPassedTime)){
									sPassedTime = sProcessTime;
								}
								User oPostUser = oCurrFlowDoc.getPostUser();
								String sPostUser = oPostUser.getName();
								String sDesc = CMyString.transDisplay(oCurrFlowDoc.getPostDesc());
								ReviewURLString.append("<div class=\"reviewSingleWrap\">");
								ReviewURLString.append("<span>").append(sProcessTime).append("，").append(sPostUser).append("提交，");
								ReviewURLString.append("操作：");
								if(j == nSize -1){
									ReviewURLString.append(oCurrFlowDoc.getPostDesc());
								}else{
									ReviewURLString.append(ProcessConstants.getFlagDesc(oCurrFlowDoc.getFlag()));
								}
								ReviewURLString.append("</span>");
								if(j != nSize -1){
									ReviewURLString.append("<div class=\"reviewSingle\">");
									ReviewURLString.append("<div class=\"reviewTitle\">");
									ReviewURLString.append("审核意见：");
									ReviewURLString.append("</div>");
									ReviewURLString.append("<div class=\"reviewContent\">");
									ReviewURLString.append(CMyString.isEmpty(sDesc) ? "无审核意见" : sDesc);
									ReviewURLString.append("</div>");
									ReviewURLString.append("</div>");
								}
								if(oProcessAction ==null && oPostUser.getId() == loginUser.getId()){
									oProcessAction = oCurrFlowDoc;
								}
								ReviewURLString.append("</div>");
							} // END INNER FOR LOOP
							ReviewURLString.append("</div>");
							//获取微博发布的信息
							int nGroupId  = oCurrSCMMicroContent.getGroupId();
							SCMGroup oCurrGroup = SCMGroup.findById(nGroupId);
							//待发布帐号列表
							String[] pAccountIds = oCurrSCMMicroContent.getPublishAccountIds().split(",");
							//获取已发布帐号列表
							oHashMap.clear();
							oHashMap.put("SCMMicroContentId", String.valueOf(nCurrContentId));
							Accounts oPostedAccounts = (Accounts)oProcessor.excute("wcm61_scmmicrocontent","findPostedAccounts",oHashMap);
		%>
			<!--第i条审核-->
			<div id="checked<%=i%>">
				<div class="checkedMessageTop">
					<div class="checkedMessageTopUp">
					<%if (oProcessAction != null && oProcessAction.getFlag() == ProcessConstants.FLAG_BACK){%>
						<span class="floatLeft"><img src="../images/return.png" /></span>
						<span class="floatLeftMarginLeft5px">已返工</span>
					<%}else{%>
						<span class="floatLeftMarginLeft5px"><img src="../images/passed_left.png" /></span>
						<span class="floatLeftMarginLeft5px">已通过</span>
					<%}%>
						<span class="floatLeftMarginLeft5px"><img src="../images/passed_down.png" class="pointerHand" onclick="slideUpDown(this,'../images/passed_down.png','../images/passed_right.png','infomationFlow<%=i%>')" /></span>
						<span class="floatRight font12PxColor">评审于：<%=CMyString.transDisplay(sPassedTime)%></span>
						<div class="clearFloat"></div>
						<div class="height8Px"></div>
					</div>
					<div class="font12PxColor checkedMessageTopDown" id="infomationFlow<%=i%>">
						<%=ReviewURLString%>
						<div class="clearFloat"></div>
					</div>
				</div>
				<div class="checkedMessageBottom contentMicroMessage">
					<p class="paddingLeftRight5Px" style="word-wrap:break-word">
					<%if(!CMyString.isEmpty(sContent)){%>
						<%=CMyContentTranslate.sinaContentTranslate(sContent)%>
					<%}else{%>
						转发微博。
					<%}%>
					</p>
					<%
						if(!CMyString.isEmpty(sPicPath)){;
					%>
					<div class="padding5_0Px">
					<%
					String sJavaVersion = System.getProperty("java.version");
					String sJava = sJavaVersion.substring(0,3);
					String sImgType = sPicPath.substring(sPicPath.length()-3);
					//判断是否是jdk1.5和是否为gif图片，gif图片jdk1.5不支持缩略图显示
					if(sImgType.equalsIgnoreCase("gif") && sJava.equals("1.5")){
					%>
						<img src="../file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sPicPath)%>&Width=120" onclick="allChangePic2(this,'<%=CMyString.filterForJs(sPicPath)%>')" class="cursorBig" width="120" width="120" isBig="1"onload="autoResizeImg2(120,120,this)"/>

					<%
						}else{
					%>
						<img src="../file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sPicPath)%>&ScaleWidth=120" onclick="allChangePic(this,'<%=CMyString.filterForHTMLValue(sPicPath)%>')" class="cursorBig"  onload="autoResizeImg(120,120,this)"/>
					</div>
					<%}}%>
					<!--转发微博-->
					<%
					if(oCurrSCMMicroContent.isRetweeted()){
						//获取微博的平台帐号和转发的微博ID
						String sMicroContentId = oCurrSCMMicroContent.getRetweetedID();
						// 2 查找账号集合
						Accounts oPublishAccounts = Accounts.findByIds(loginUser, oCurrSCMMicroContent.getPublishAccountIds());

						// 3 权限校验
						for (int k = 0, nPublishSize = oPublishAccounts.size(); k < nPublishSize; k++) {
							Account oAccount = (Account) oPublishAccounts.getAt(k);
							if (oAccount == null)
								continue;
							boolean hasRight = false;
							try{
								hasRight = SCMAuthServer.hasRightInAccount(oCurrSCMMicroContent.getCrUser(), oAccount,SCMAuthServer.ACCOUNT_USE);
							}catch(Exception e){
								//do nothing
							}
							if (!hasRight) {
								oPublishAccounts.remove(oAccount,false);
							}
						}

						if(oPublishAccounts != null && oPublishAccounts.size() > 0){
							// 设置参数
							String sServiceIdOfMicroContentService = "wcm61_scmmicrocontent";
							String sMethodNameOfFindMicroContent = "findByMicroContentId";
							
							oHashMap.put("MicroContentId", String.valueOf(sMicroContentId));
							oHashMap.put("AccountId",String.valueOf(oPublishAccounts.getAt(0).getId()));
							Account oAccountPlatf=(Account)oPublishAccounts.getAt(0);
							String sAccountPlat = oAccountPlatf.getPlatform();
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
							if(oCurrMicroContent!=null){
								if(oCurrMicroContent.isRetweeted()){
									oCurrMicroContent = oCurrMicroContent.getRetweetedMicroContent();
								}
								String sReBmiddlePic = oCurrMicroContent.getBmiddlePic();
								String sReThumbnailPic = oCurrMicroContent.getThumbnailPic();

								int sReCommentCount = oCurrMicroContent.getCommentCount();
								int sReRepostCount = oCurrMicroContent.getRepostCount();
								 Date dtReCrTime = oCurrMicroContent.getCreateDate();
								String sReContent = oCurrMicroContent.getContent();
								String sReMicroContentId = oCurrMicroContent.getId();
								if(oCurrMicroContent.getUser()!=null){//当微博被删除后，头像为空就会报错
									String sReUserName = oCurrMicroContent.getUser().getName();
						%>
									<!--如果有转发微博显示-->
									<div class="positionRelative">
										<div class="upTriangle"></div>
										<div class="innerAllMicroContent">
										<div style="word-wrap:break-word">
											<span class="blueColor">@<%=CMyString.transDisplay(sReUserName)%>：</span>
											<%=CMyContentTranslate.microContentTranslate(sReContent,sAccountPlat)%></div>
											<div class="padding5_0_Px_cur">
												<%if(!CMyString.isEmpty(sReThumbnailPic)){%>
													<img src="<%=CMyString.filterForHTMLValue(sReThumbnailPic)%>?width=120" onclick="changeImg(this,'<%=CMyString.filterForJs(sReThumbnailPic)%>?width=120','<%=CMyString.filterForJs(sReBmiddlePic)%>?width=440')" onload="autoResizeImg(120,120,this)"/>
												<%}else{%>
													<div style="height:10px;"></div>
												<%}%>	
											</div>
											<div class="messageOperate">
												<span class="floatLeft">
													<span>
													<%if(dtReCrTime!=null){%>
														<%=CMyString.filterForJs(CMyTimeTranslate.getTimeString(dtReCrTime))%>
													<%}%>
													</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<span href="javascript:void(0)"><%=oCurrMicroContent.getSource()%></span>
												</span>
											</div>
											<div class="clearFloat"></div>
										</div>
									</div>
						<%	
								}else{// ENF IF FOR RETWEET MICROCONTENT's USER NOT NULL
						%>
									<div class="positionRelative">
										<div class="upTriangle"></div>
											<span class="floatLeft">该微博已被删除！</span>	
										<div class="clearFloat"></div>
									</div>
						<%
								}// ENF IF FOR RETWEET MICROCONTENT's USER NOT NULL
							}else{
							%>
							<div class="positionRelative">
								<div class="upTriangle"></div>
								<div class="innerAllMicroContent">
									<span>十分抱歉！该转发的原始微博已被删除，您无法查看该转发的原始微博！</span>
									<div class="clearFloat"></div>
								</div>
							</div>
							<%
							} // END IF FOR MICROCONTENT NOT NULL
						}else{
			%>
							<div class="positionRelative">
								<div class="upTriangle"></div>
								<div class="innerAllMicroContent">
									<span>十分抱歉！转发该微博的微博帐号已从本系统中删除或者解除绑定，您无法查看该转发的原始微博！</span>
									<div class="clearFloat"></div>
								</div>
							</div>
				<%
						}
					} // END IF FOR RETWEET MICROCONTENT
					
					String sPublishingAccountNames = "";
					String sPublishedAccountNames = "";
					String sTempAccountname = "";
					for(int index = 0; index < pAccountIds.length; index++){
						Account oTempAccount = Account.findById(Integer.valueOf(pAccountIds[index]));
						if(oTempAccount == null || oTempAccount.getStatus() <= 0){
							continue;
						}
						sTempAccountname = oTempAccount.getAccountName() + "(" + PlatformFactory.getPlatform(oTempAccount.getPlatform()).getChineseName() + ")";
						if(oPostedAccounts.indexOf(Integer.valueOf(pAccountIds[index])) < 0){
							sPublishingAccountNames += sTempAccountname + "\n";
						}else{
							sPublishedAccountNames += sTempAccountname + "\n";
						}
					}
			%>
					<!--提示审核用户，微博的发布信息-->
					<div class="positionRelative" style="magin-left:20px">
						<span class="contentMicroMessageLeft">&nbsp<%=CMyString.isEmpty(sPublishedAccountNames)?"待" : "已"%>发布分组：<%=CMyString.transDisplay(oCurrGroup.getGroupName())%>&nbsp&nbsp&nbsp&nbsp</span>
					<%if(!CMyString.isEmpty(sPublishedAccountNames)){%>
						<span class="contentMicroMessageLeft" title="<%=CMyString.filterForHTMLValue(sPublishedAccountNames)%>" style="cursor:pointer">→ 已发布帐号&nbsp&nbsp&nbsp&nbsp</span>
					<%}
						if(!CMyString.isEmpty(sPublishingAccountNames)){
					%>
						<span class="contentMicroMessageLeft" title="<%=CMyString.filterForHTMLValue(sPublishingAccountNames)%>" style="cursor:pointer">→ 待发布帐号</span>
					<%}%>
					</div>
					<div class="clearFloat"></div>
				</div>
		<%
				}//END IF FOR oCurrSCMMicroContent not NULL
			}
			}// END OUTER FOR LOOP
		%>
		<%
		if(nTotalNum > 0){
		%>
			<div class="sabrosus">
				<%if(nPageIndex==1){%>
					<span class='disabled'> 上一页 </span>
				<%}else{%>
					<a href="microblog_checked_list.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>" class="prevPage"> 上一页 </a>
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
					<a href="microblog_checked_list.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=i%>"><%=i%></a>
				<%
					}//end if
				}//end for
				%>
				<%
				if(nPageIndex==nPageCount){
				%>
					<span class='disabled'> 下一页 </span>
				<%
				}else{
				%>
				<a href="microblog_checked_list.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>"> 下一页 </a>
				<%
					}
				%>
			</div>
		<%
		}
		%>
	</body>
</html>
