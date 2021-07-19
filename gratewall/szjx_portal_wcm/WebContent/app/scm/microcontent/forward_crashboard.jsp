<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ include file="../../include/public_server.jsp"%>
<%@ include file="../include/workflow_check.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 构造参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	String sMicroContentId = currRequestHelper.getString("MicroContentId");
	if(IS_DEBUG){
		System.out.println("nSCMGroupId:"+nSCMGroupId);
		System.out.println("nAccountId:"+nAccountId);
		System.out.println("sMicroContentId:"+sMicroContentId);
	}
	int nSessionSCMGroupId = 0;
	if(session.getAttribute("SCMGroupId")!=null){
		nSessionSCMGroupId = Integer.parseInt(session.getAttribute("SCMGroupId").toString());
	}
	// 2 判断是否接收到分组id信息，否则从session中获取。
	if(nSCMGroupId==0 && nSessionSCMGroupId>0){
		nSCMGroupId = nSessionSCMGroupId;
	}
	// 3 调用服务
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	String sPlat = null;
	oProcessor.reset();
	HashMap oAccsOfGrpParams = new HashMap();
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
	Accounts oAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName);
	out.clear();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>转发微博</title>
<link rel="stylesheet" href="../create_microblog.css">
<link rel="stylesheet" href="../css/show_face_forward.css">
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../create_microblog.js"></script>
<script src="../js/getActualTop.js"></script>
<script src="../js/show_face_forward.js"></script>
<script>
	$(function(){
		userPicList(0);
		setFocus("myExpress");
	});
</script>
</head>
<body>
	<div class="createUserContent" style="margin-left:11px">
		<div class="createUserListContent">
			<div class="v_show">
				<span class="createPrev"><img src="../images/create_left_btn.png" class="createPrePic" /></span>
				<div class="v_content">
					<div class="v_content_list">
						<ul id="headUlStyle">
							<%
							String sAccountIds = "";
							Accounts tempAccounts = Accounts.createNewInstance(loginUser);
							for(int i=1;i<=oAccounts.size();i++){
								Account oAccount = (Account) oAccounts.getAt(i-1);
								if (oAccount == null) continue;
								if(oAccount.getStatus()!=1) continue;
								String nowPlatform = Account.findById(nAccountId).getPlatform();
								String forwardPlatform = oAccount.getPlatform();
								if(!nowPlatform.equals(forwardPlatform)){continue;}
								tempAccounts.addElement(oAccount);
							}
							for(int i=1;i<=tempAccounts.size();i++){
								Account oAccount = (Account) tempAccounts.getAt(i-1);
								if(oAccount == null){
									continue;
								}
								sAccountIds=sAccountIds+","+oAccount.getId();

								String sHeadPath = oAccount.getHeadPic();
								if(CMyString.isEmpty(sHeadPath)){
									sHeadPath = "../images/no_head.png";
								}else{
									sHeadPath = "../file/read_image.jsp?FileName="+sHeadPath;
								}
								String sPlatformPath = "";
								sPlat = oAccount.getPlatform();
								sPlatformPath = "../images/"+sPlat.toLowerCase()+"_logo.png";
							%>
								<li>
									<div class="createUserPic" id="user_<%=i%>" onclick="createUserChangeState(<%=i%>)">
										<span><img id="user_h<%=i%>" src="<%=CMyString.filterForHTMLValue(sHeadPath)%>"  style="width:42px;height:42px;border: 1px solid #C0C0C0;-moz-border-radius: 8px;-webkit-border-radius: 8px;border-radius: 8px;" /></span>
										<span class="createLogoPic"><img id="user_l<%=i%>" src="<%=CMyString.filterForHTMLValue(sPlatformPath)%>" /></span>
										<span class="createSelectPic"><img id="user_t<%=i%>" src="../images/selected.png" /></span>
									</div>
								</li>
							<%
							}
							sAccountIds = sAccountIds.substring(1);
							if(IS_DEBUG){
								System.out.println("sAccountIds:"+sAccountIds);
							}
							%>
						</ul>
					</div>	
				</div>
				<span class="createNext"><img src="../images/create_right_btn.png" class="createNextPic" /></span>
			</div>
		</div>
		<div class="clearFloat"></div>
	</div>
	<div class="clearFloat"></div>
	<div class="linkOrFont" style="margin-left:11px">
		<div class="floatLeft">
			转发原因：
		</div>
		<div class="floatRight">
			<span class="countTxt">还可以输入<em id="showNum" class="wcmSelectFont">140</em>字</span>
		</div>
		<div class="clearFloat"></div>
	</div>
	<textarea id="myExpress" tabindex="1" class="textInput" wrap="physical" style="margin-left:11px"></textarea>
	<span type="text" value="" id="news_title_limit" ></span>
	<div style="margin-left:11px;margin-top:5px;position:relative;">
		<span><img src="../images/face.png" name="face" id="forwardFace" /></span>
	</div>
	<%@ include file="../include/show_face_forward.jsp"%>
	<%
		String sWorkFlowErrorMsg = "";
		int nHasWorkFlow = 0;
		try{
			nHasWorkFlow = hasWorkFlow(nSCMGroupId,oProcessor) ? 1 : 0;
		}catch(WCMException e){
			sWorkFlowErrorMsg = e.getMessage();
		}
	%>
	<script>
	var m_cbCfg = {
		btns:[
				{//绘制发布按钮
					text: '转发',
					cmd : function(){
						var workflowerror = "<%=sWorkFlowErrorMsg%>";
						if(workflowerror != ""){
							alert(workflowerror);
							return false;
						}
						var m_AccountIds="";
						var m_FinalAccountIds="";
						for(var i=0;i<<%=tempAccounts.size()%>;i++){
							if(!$("#user_h"+(i+1)).hasClass("grayPic")){
								m_AccountIds =m_AccountIds+i+",";
							}
						}
						//获取选中的头像的下标串
						m_AccountIds=m_AccountIds.substring(0,m_AccountIds.length-1);
						var m_ArraySelectIds= m_AccountIds.split(",");
						var forwardReason = $("#myExpress").val();
						forwardReason = transEnter(forwardReason);
						var lenInfo = forbidLength();
						var m_AllIds = "<%=CMyString.filterForJs(sAccountIds)%>";
						var m_ArrayAllIds = m_AllIds.split(",");
						//根据获取的头像下标串，获取头像所对应的AccountId
						for(var j=0;j<m_ArraySelectIds.length;j++){
							m_FinalAccountIds = m_FinalAccountIds + m_ArrayAllIds[m_ArraySelectIds[j]]+",";
						}
						m_FinalAccountIds = m_FinalAccountIds.substring(0,m_FinalAccountIds.length-1);
						
						//判空校验
						if(m_FinalAccountIds=="undefined" || m_FinalAccountIds=="" || m_FinalAccountIds==null ){
							alert("至少选中一个帐号！");
							return false;
						}
						else if(lenInfo < 0){
							alert("转发理由超过140个字！请编辑后重新发布！");
							return false;
						}else{
							var jsonParams = {_AccountIds:m_FinalAccountIds,_SCMGroupId:<%=nSCMGroupId%>,_Content:forwardReason,_MicroContentId:'<%=CMyString.filterForJs(sMicroContentId)%>',_hasWorkFlow:'<%=nHasWorkFlow%>'};
							this.notify(jsonParams);
						}
					}
				}
			]
	};
	</script>
</body>
</html>
