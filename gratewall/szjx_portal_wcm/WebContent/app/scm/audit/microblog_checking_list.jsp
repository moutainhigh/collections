<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="weibo4j.model.WeiboException" %>
<%@ page import="t4j.TBlogException" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.trs.scm.sdk.util.CMyContentTranslate" %>
<%@ page import="com.trs.scm.sdk.util.CMyTimeTranslate" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.ajaxservice.ProcessService" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.cms.process.definition.Flows" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.engine.FlowDocs" %>
<%@ page import="com.trs.cms.process.ProcessConstants" %>
<%@ page import="com.trs.cms.process.definition.FlowNode" %>
<%@ page import="com.trs.cms.process.definition.FlowNodes" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ page import="com.trs.scm.sdk.model.MicroContent" %>
<%@ page import="com.trs.scm.domain.SCMAuthServer" %>
<%@ page import="com.trs.scm.process.FlowContentSCMMicroContentImpl" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%@ include file="../include/register_check.jsp"%>
<%! static final boolean IS_DEBUG = false;%>

<%
		// 获取参数
		int nPageSize = currRequestHelper.getInt("PageSize",10);
		int nPageIndex = currRequestHelper.getInt("PageIndex",1);

		// 设置参数
		String sServiceIdOfProcessService = "wcm6_process";
		String sMethodNameOfQueryReviewedList = "getContentsOfUser";
		final String ContentType = String.valueOf(SCMMicroContent.OBJ_TYPE);
		
		HashMap oHashMap = new HashMap();
		oHashMap.put("ViewType", String.valueOf(1));
		oHashMap.put("ObjType", String.valueOf(SCMMicroContent.OBJ_TYPE));

		JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);

		// 其他属性：
		// CurrUser、SearchValue、OrderBy
		IFlowContent[] oResult = (IFlowContent[])oProcessor.excute(sServiceIdOfProcessService,sMethodNameOfQueryReviewedList,oHashMap);
		
		int nTotalNum = oResult.length;
		int nPageCount = (nTotalNum%nPageSize==0)?(nTotalNum/nPageSize):(nTotalNum/nPageSize+1);
%>

<!DOCTYPE html>
<html>
<head>
<title>审核管理-待审</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/jquery.ui.select.css" />
<link rel="stylesheet" type="text/css" href="../css/select_no_border.css" />
<link rel="stylesheet" href="../css/public.css" />
<link rel="stylesheet" href="microblog_checking_list.css" />
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/jquery.ui.select.js"></script>
<script src="../js/iframe_public.js"></script>
<script src="microblog_checking_list.js"></script>
<!-- 引入crashboard的样式文件 -->
<script src="../js/getActualTop.js" type="text/javascript"></script>
<script>
	 //弹出编辑微博页面
	 function editMicorblog(Accounts,id){
		var CrashBoarderInfo = {
			id : 'createMicroblog', //id
			title : '编辑微博',//弹出窗口名称
			maskable : true, //是否绘制遮布
			draggable : true,//是否可以拖动
			url : 'audit/editMicroblogCrashBoard.jsp',//弹出框显示的内容的页面地址
			width : '574px',//宽度
			height : '290px',//高度
			params : {PublishingAccountIds:Accounts,SCMMicroContentId:id},//传给弹出框页面的参数(需要获取组id)
			callback : function(params){//回调函数
				$.ajax({
					type:"post",
					data:{
						PublishingAccountIds:params._AccountIds,
						Content:params._MicroContent,
						SCMMicroContentId:params._MicroContentId,
						Picture:params._Picture
					},
					dataType:"text",
					url:"save_microcontent_editing.jsp",
					success:function(data){
						if($.trim(data)==1){
							window.location.reload();
						}else{
							alert("保存失败！"+$.trim(data));
						}
					},
					error:function(){
						alert("保存失败！");
					}
				}); 
			}
		};
		getActualTop().CrashBoard.show(CrashBoarderInfo);//弹出crashBoard框
	}
</script>

<!-- 引入获取当前window的top对象函数文件 -->
<script language="javascript" src="../js/getActualTop.js" type="text/javascript"></script>

</head>
<body class="bodyColor">
	<div id="userList" class="lineHeight63">
		<div class="showSelectContain">
			<span class="currentSelect">待审</span>&nbsp;<span class="grayColor">|</span> &nbsp;<a href="microblog_checked_list.jsp" target="frame_content"><span style="color:#0078b6">已审</span></a>
			<div class="clearFloat"></div>
		</div>
	</div>

	<%
	int nStart = (nPageIndex - 1) * nPageSize;
	int nEnd = nTotalNum == 0 ? 0 : ((nPageCount == nPageIndex) ? nTotalNum : nPageIndex * nPageSize);
	if(IS_DEBUG){
		System.out.println("nTotalNum: " + nTotalNum);
		System.out.println("nPageCount: " + nPageCount);
		System.out.println("nPageIndex: " + nPageIndex);
		System.out.println("nStart: " + nStart);
		System.out.println("nEnd: " + nEnd);
	}
	//无审核微博提示：
	if(nEnd == 0){
%>
<div class="sabrosus"><span class="explainWords">
	<font size="4" color="#AD251A">无待审微博</font></span></div>
<%
	}
	for(int i = nStart; i < nEnd; i++){
		FlowContentSCMMicroContentImpl oCurrFlowContent = null;
		if(oResult[i] instanceof FlowContentSCMMicroContentImpl){
			oCurrFlowContent = (FlowContentSCMMicroContentImpl)oResult[i];
		}else{
			continue;
		}

		SCMMicroContent oCurrSCMMicroContent = (SCMMicroContent) oCurrFlowContent.getSubstance();
		if(oCurrSCMMicroContent == null){
			continue;
		}
		Date dtSubmitTime = oCurrSCMMicroContent.getCrTime().toDate();
		String sContent = oCurrSCMMicroContent.getContent();
		String sPicPath = oCurrSCMMicroContent.getPicture();
		int nCurrContentId = oCurrFlowContent.getId(); //微博ID
		String sPublishingIds = oCurrSCMMicroContent.getPublishAccountIds();
		int nCurrFlowDocId = oCurrFlowContent.getFlowDoc().getId();
		int nParentId = FlowDoc.findById(nCurrFlowDocId).getParentId();
	%>
	<!--第一条待审-->
		<div class="contentMicroMessage" id="contentMicroMessage<%=i%>">
			<div class="clearFloat"></div>
			<div class="padding10Px5Px">
				<div class="checkingMessage" style="word-wrap:break-word">
				<%if(!CMyString.isEmpty(sContent)){%>
					<%=CMyContentTranslate.sinaContentTranslate(sContent)%>
				<%}else{%>转发微博。<%}%>
				</div>
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
					<img src="../file/read_image.jsp?FileName=<%=CMyString.filterForHTMLValue(sPicPath)%>&ScaleWidth=120" onclick="allChangePic(this,'<%=CMyString.filterForJs(sPicPath)%>')" class="cursorBig" onload="autoResizeImg(120,120,this)"/>
				<%}%>
					</div>
					<%}%>
				<!-- 转发微博显示 -->
				<%
					if(oCurrSCMMicroContent.isRetweeted()){
						//获取微博的平台帐号和转发的微博ID

						String sMicroContentId = oCurrSCMMicroContent.getRetweetedID();
						// 2 查找账号集合
						Accounts oAccounts = Accounts.findByIds(loginUser, oCurrSCMMicroContent.getPublishAccountIds());

						// 3 权限校验
						for (int j = 0, nSize = oAccounts.size(); j < nSize; j++) {
							Account oAccount = (Account) oAccounts.getAt(j);
							if (oAccount == null)
								continue;
							boolean hasRight = false;
							try{
								hasRight = SCMAuthServer.hasRightInAccount(oCurrSCMMicroContent.getCrUser(), oAccount,SCMAuthServer.ACCOUNT_USE);
							}catch(Exception e){
								//do nothing
							}
							if (!hasRight) {
								oAccounts.remove(oAccount,false);
							}
						}
						if(oAccounts != null && oAccounts.size() > 0){

							// 设置参数
							String sServiceIdOfMicroContentService = "wcm61_scmmicrocontent";
							String sMethodNameOfFindMicroContent = "findByMicroContentId";

							oHashMap.put("MicroContentId", sMicroContentId);
							oHashMap.put("AccountId",String.valueOf(oAccounts.getAt(0).getId()));

							MicroContent oCurrMicroContent = null;
							Account oAccountPlatf=(Account)oAccounts.getAt(0);
							String sAccountPlat = oAccountPlatf.getPlatform();
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
								if(oCurrMicroContent!=null){	//如果转发微博不为空
								String sReBmiddlePic = oCurrMicroContent.getBmiddlePic();
								String sReThumbnailPic = oCurrMicroContent.getThumbnailPic();
								int sReCommentCount = oCurrMicroContent.getCommentCount();
								int sReRepostCount = oCurrMicroContent.getRepostCount();
								 Date dtReCrTime = oCurrMicroContent.getCreateDate();
								String sReContent = oCurrMicroContent.getContent();
								String sReMicroContentId = oCurrMicroContent.getId();
								if(oCurrMicroContent.getUser()!=null){//当微博被删除后，头像为空就会报错
									String sReUserName = oCurrMicroContent.getUser().getName();		
									
									if(IS_DEBUG){
										System.out.println("************副微博**************");
										System.out.println("sReBmiddlePic："+sReBmiddlePic);
										System.out.println("sReThumbnailPic："+sReThumbnailPic);
										System.out.println("sReCommentCount："+sReCommentCount);
										System.out.println("sReRepostCount："+sReRepostCount);
										System.out.println("sReContent："+sReContent);
										//System.out.println("sReCreateDate："+sReCreateDate);
										System.out.println("sReMicroContentId："+sReMicroContentId);
										System.out.println("sReUserName："+sReUserName);
									}

						%>
									<!--如果有转发微博显示-->
									<div class="positionRelative">
										<div class="upTriangle"></div>
										<div class="innerAllMicroContent">
										<div style="word-wrap:break-word">
											<span class="blueColor">@<%=CMyString.transDisplay(sReUserName)%>：</span>
											<%=CMyContentTranslate.microContentTranslate(sReContent,sAccountPlat)%>
											</div>
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
														<%=CMyTimeTranslate.getTimeString(dtReCrTime)%>
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
							}
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
							}// END IF FOR MICROCONTENT NOT NULL
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
					%>
				<!-- 审核操作 -->
				<%
					//获取指定文档的流转轨迹
					IFlowServer oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
					FlowDocs oFlowDocsResult = oFlowServer.getFlowDocs(oCurrFlowContent, null);

					StringBuffer URLString = new StringBuffer();
					URLString.append("<p><b>流转情况:</b></p>");
					int nSize = oFlowDocsResult.size();
					URLString.append("<div class=\"reviewList\">");
					for (int j = 0; j < nSize; j++) {
						FlowDoc oCurrFlowDoc = (FlowDoc) oFlowDocsResult.getAt(j);
						if(oCurrFlowDoc == null){
							continue;
						}
						
						String sProcessTime = CMyDateTime.getStr(oCurrFlowDoc
								.getPostTime(), CMyDateTime.DEF_DATETIME_FORMAT_PRG);
						String sPostUser = oCurrFlowDoc.getPostUserName();
						String sDesc = CMyString.transDisplay(oCurrFlowDoc.getPostDesc());
						URLString.append("<div class=\"reviewSingleWrap\">");
						URLString.append("<span>").append(sProcessTime).append("，").append(sPostUser).append("提交，");
						URLString.append("操作：");
						if(j == nSize -1){
							URLString.append(oCurrFlowDoc.getPostDesc());
						}else{
							URLString.append(ProcessConstants.getFlagDesc(oCurrFlowDoc.getFlag()));
						}
						URLString.append("</span>");
						if(j != nSize -1){
							URLString.append("<div class=\"reviewSingle\">");
							URLString.append("<div class=\"reviewTitle\">");
							URLString.append("审核意见：");
							URLString.append("</div>");
							URLString.append("<div class=\"reviewContent\">");
							URLString.append(CMyString.isEmpty(sDesc)? "无审核意见" : sDesc);
							URLString.append("</div>");
							URLString.append("</div>");
						}
						
						URLString.append("</div>");
					}
					URLString.append("</div>");
					URLString.append("<p><b>审核意见:</b>(<=250个字)</p>");

					//获取待发布微博的信息
					int nGroupId  = oCurrSCMMicroContent.getGroupId();
					SCMGroup oCurrGroup = SCMGroup.findById(nGroupId);
					String[] pAccountIds = sPublishingIds.split(",");
					String sAccountNames = "";
					String sTempAccountname = "";
					for(int index = 0; index < pAccountIds.length; index++){
						Account oTempAccount = Account.findById(Integer.valueOf(pAccountIds[index]));
						if(oTempAccount == null || oTempAccount.getStatus() <= 0){
							continue;
						}
						sTempAccountname = oTempAccount.getAccountName() + "(" + PlatformFactory.getPlatform(oTempAccount.getPlatform()).getChineseName() + ")";
						sAccountNames += sTempAccountname + (index == pAccountIds.length-1 ? "" : "\n");
					}
				%>
				<div class="positionRelative">
						<!--提示微博发布的相关信息-->
						<span class="contentMicroMessageLeft"><%=CMyString.transDisplay(CMyTimeTranslate.getTimeString(dtSubmitTime))%>&nbsp&nbsp&nbsp&nbsp</span>
						<span class="contentMicroMessageLeft"><%=CMyString.transDisplay(oCurrGroup.getGroupName())%>&nbsp&nbsp&nbsp&nbsp</span>
						<span class="contentMicroMessageLeft" title="<%=CMyString.filterForHTMLValue(sAccountNames)%>" style="cursor:pointer">→ 待发布帐号</span>
						<!--审核操作-->
						<span class="contentMicroMessageRight">
							<span class="marginRight15Px pointerHand" onclick="passClick(this,<%=i%>,<%=nEnd%>)">
								<img src="../images/pass.png" /><span>通过</span>
							</span>
							<!--返工状态的微博，发起人不具有返工操作入口-->
					<%
						if(!(oCurrSCMMicroContent.getFlowStatusId() == Status.STATUS_ID_NO && oCurrSCMMicroContent.getCrUser().getName().equals(loginUser.getName()))){
						%>
							<span class="marginRight15Px pointerHand" onclick="returnClick(this,<%=i%>,<%=nEnd%>);">
								<img src="../images/return.png" class="marginBottom_2Px" />返工
							</span>
					<%
							}
						FlowDoc oCurrFlowDoc = (FlowDoc) oFlowDocsResult.getAt(nSize-1);
						FlowNode oCurrFlowNode = oCurrFlowDoc.getNode();

						boolean bIsAdminOfCurrGroup = false;
						oProcessor.reset();
						oHashMap.clear();
						oHashMap.put("UserId",String.valueOf(loginUser.getId()));
						try{
							SCMGroups oGroups = (SCMGroups)oProcessor.excute("wcm61_scmgroup","getGroupsOfUser",oHashMap);
							if(oGroups.indexOf(oCurrGroup) >= 0){
								bIsAdminOfCurrGroup = true;
							}
						}catch(Exception e){
							String sErrorMsg = e.getMessage();
							sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("<--"));
						%>
							<script>alert(<%=sErrorMsg%>);</script>
						<%}
						
						if(oCurrFlowNode.canEditDoc() && bIsAdminOfCurrGroup){
					%>
							<!--工作流中设置了修改权限，且当前用户是分组管理员，才显示编辑入口-->
							<span class="marginRight15Px pointerHand" onclick="editMicorblog('<%=CMyString.filterForJs(sPublishingIds)%>',<%=nCurrContentId%>);">
								<img src="../images/edit.png" class="marginBottom_2Px" />编辑
							</span>
					<%
						}
						if(oCurrFlowNode.canDelDoc()){
					%>
							<!--工作流中设置了删除权限，才显示删除入口-->
							<span class="marginRight15Px pointerHand" onclick="deleteMicroMessage(<%=nCurrContentId%>)">
								<img src="../images/delete_flag.png" class="marginBottom_2Px" />删除
							</span>
					<%}%>
						</span>
						<div class="clearFloat"></div>
						<div class="checkingContainer" id="checkingContainer<%=i%>">
							<div class="checkingTriangle"></div>
							<div class="checkingContent" id="checkingContent<%=i%>">
								<%=URLString%>
								<textarea class="checkingTextarea" id="checkingTextarea<%=i%>"></textarea>
								<div class="checkingBtn pointerHand" onclick="pass(<%=nCurrFlowDocId%>,$('#checkingTextarea<%=i%>').val(),<%=nCurrContentId%>)">通 过</div>
					<%
					
					//获取下一节点的信息
					String sNextUser = "";
					FlowNodes oNextNodes = ((FlowDoc)oFlowDocsResult.getAt(0)).getNode().getNextNodes(loginUser);
					if(oNextNodes != null && oNextNodes.size() > 0){
						sNextUser = "下一流转节点：";
						for(int k = 0; k < oNextNodes.size(); k++){
							FlowNode oCurrNode = (FlowNode)oNextNodes.getAt(k);
							if(oCurrNode.isEndNode()){
								sNextUser += "结束。";
								break;
							}else{
								Users oNextUsers= oCurrNode.getOperUsers(loginUser);
								int nUserSize = oNextUsers.size();
								if(oNextUsers != null && nUserSize > 0){
									for(int j = 0; j < nUserSize; j++){
										sNextUser += ((User)oNextUsers.getAt(j)).getName() + (j != nUserSize - 1 ? "，" : "");
										if(sNextUser.split("，").length > 5){
											sNextUser += "...";
											break;
										}
									}
								}
							}
						}
					}
					
					%>
								<font style="float:right; margin-right:10px;margin-bottom:16px;"><%=sNextUser%></font>
							</div>
							<script>
								$("#checkingContainer<%=i%>").css("display","inline");
								var conHeight = $("#checkingContent<%=i%>").find("div:eq(0)").height();
								$("#checkingContainer<%=i%>").css("display","none");
								$("#checkingContainer<%=i%>").height(conHeight + 170);
							</script>
							<div class="clearFloat"></div>
						</div>
						<div class="clearFloat"></div>
						<div class="checkingContainer" id="returnContainer<%=i%>">
							<div class="returnTriangle"></div>
							<div class="checkingContent" id="returnContent<%=i%>">
								<%=URLString%>
								<textarea class="checkingTextarea"  id="returnTextarea<%=i%>"></textarea>
								<div class="checkingBtn pointerHand" onclick="rework(<%=nCurrFlowDocId%>,$('#returnTextarea<%=i%>').val(),<%=nCurrContentId%>)">返 工</div>
							<%
							//获取上一节点的信息
							String sPreUser = "上一流转节点：";
							FlowNode oPreNode = ((FlowDoc)oFlowDocsResult.getAt(0)).getPreNode();
							if(oPreNode != null){
								if(!oPreNode.isStartNode()){
									Users oPreUsers= oPreNode.getOperUsers(loginUser);
									if(oPreUsers != null && oPreUsers.size() > 0){
										for(int j = 0; j < oPreUsers.size(); j++){
											sPreUser += (j == 0 ? "" : "，") + ((User)oPreUsers.getAt(j)).getName();
											if(sPreUser.split("，").length > 5){
												sPreUser += "。。。";
												break;
											}
										}
									}
								}else{
									sPreUser += oCurrSCMMicroContent.getCrUser().getName();
								}
							}
							%>
								<font style="float:right; margin-right:10px;margin-bottom:16px;"><%=sPreUser%></font>
							</div>
							<div class="clearFloat"></div>
							<script>
								$("#returnContainer<%=i%>").css("display","inline");
								var conHeight = $("#returnContent<%=i%>").find("div:eq(0)").height();
								$("#returnContainer<%=i%>").css("display","none");
								$("#returnContainer<%=i%>").height(conHeight + 170);
							</script>
						</div>
						<div class="clearFloat"></div>
					</div>
				</div>
		<div class="clearFloat"></div>
		</div>
		
	<%
	}// END FOR：待审微博

	if(nTotalNum > 0){
	%>
		<div class="sabrosus">
			<%if(nPageIndex==1){%>
				<span class='disabled'> 上一页 </span>
			<%}else{%>
				<a href="microblog_checking_list.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex-1)%>" class="prevPage"> 上一页 </a>
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
				<a href="microblog_checking_list.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=i%>"><%=i%></a>
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
			<a href="microblog_checking_list.jsp?PageSize=<%=nPageSize%>&PageIndex=<%=(nPageIndex+1)%>"> 下一页 </a>
			<%
				}
			%>
		</div>
	<%
	}
	%>
</body>
</html>