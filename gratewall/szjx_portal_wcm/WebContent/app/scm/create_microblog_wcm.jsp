<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ include file="../include/public_server.jsp"%>
<%@ include file="include/workflow_check.jsp"%>
<%! static final boolean IS_DEBUG = false;%>

<!DOCTYPE html>
<html>
<head>
<title>从WCM创建微博</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/show_face_create.css">
<link rel="stylesheet" href="create_microblog.css">
<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/scm_common.js"></script>
<script src="create_microblog.js"></script>
<script src="js/show_face_create.js"></script>
<script>
	//等比例缩放
	function AutoResizeImage(maxWidth,maxHeight,objImg){
		var img = new Image();
		img.onload = function(){
			var w = this.width;
			var h = this.height;
			var hRatio;//高度的比例
			var wRatio;//宽度的比例
			var Ratio = 1;//比例
			wRatio = maxWidth / w;
			hRatio = maxHeight / h;
			if (maxWidth ==0 && maxHeight==0){
				Ratio = 1;
			}else if (maxWidth==0){//
				if (hRatio<1) Ratio = hRatio;
			}else if (maxHeight==0){
				if (wRatio<1) Ratio = wRatio;
			}else if (wRatio<1 || hRatio<1){
				Ratio = (wRatio<=hRatio?wRatio:hRatio);
			}
			if (Ratio<1){
				w = w * Ratio;
				h = h * Ratio;
			}
			if(h>0){
				objImg.height = h;
				objImg.width = w;
			}else{
				objImg.height = 112;
				objImg.width = 112;
			}
			//当缩略的图片高度小于112时，垂直居中显示。
			if(objImg.height < 112){
				var pTop = (112-objImg.height)/2;
				$(objImg).parent().css("height",112-pTop);
				$(objImg).parent().css("padding-top",pTop);
			}else{
				$(objImg).parent().css("height",112);
				$(objImg).parent().css("padding-top",0);
			}
		}
		img.src = objImg.src;
	}
</script>
</head>
<body>
<%
	// 2 构造参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nRecId = currRequestHelper.getInt("RecId",0);
	int nSiteType = currRequestHelper.getInt("SiteType",0);
	ChnlDoc oChnlDoc = ChnlDoc.findById(nRecId);
	if(oChnlDoc == null){
%>
			<div  class="sabrosus">
			<font size="4">
					<b>非常抱歉，未找到对应的文档！</b>
			</font>
			</div>
<%		return;
	}

	Document oCurrDocument = oChnlDoc.getDocument();
	if(oCurrDocument == null){
%>
			<div  class="sabrosus">
			<font size="4">
					<b>非常抱歉，未找到对应的文档！</b>
			</font>
			</div>
<%		return;
	}

	//判断文档是否已发布
	int nStatusId = oChnlDoc.getStatusId();
	if(nStatusId != Status.STATUS_ID_PUBLISHED){
		//文档未发布
	%>
		<div  class="sabrosus">
			<font size="4">
					<b>非常抱歉，对应的文档还未发布！文档标题：<%=oCurrDocument.getTitle()%></b>
			</font>
			</div>
	<%
		return;
	}
%>

	<!--用户管理的分组-->
	<%
		String sServiceIdOfSCMGroup = "wcm61_scmgroup";
        String sMethodNameofManagedGroupsOfUser = "getGroupsOfUser";

		int nUserId = loginUser.getId();
		HashMap oHashMap = new HashMap();
		oHashMap.put("UserId", String.valueOf(nUserId));

		//调用服务，获取用户管理的所有分组
		SCMGroups oAllManagedSCMGroups = (SCMGroups) oProcessor.excute(sServiceIdOfSCMGroup,
		sMethodNameofManagedGroupsOfUser,oHashMap);

		int nSelectedSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);
		if(nSelectedSCMGroupId == 0 && session.getAttribute("SCMGroupId") !=null){
			nSelectedSCMGroupId = Integer.parseInt(session.getAttribute("SCMGroupId").toString());
		}
	%>
	<div class="configContentOut">	
				<%
				if(oAllManagedSCMGroups.size() == 0){
					//无管理的分组
				%>
					<div  class="sabrosus">
						<font size="4" color="#AD251A">
							<b>非常抱歉，您还不是分组管理员，不能发布微博！</b>
						</font>
					</div>
				<%
					return;
				}else{
				%>
				<form action="create_microblog_wcm.jsp" id="queryAccountsForm" method="post" style="margin-left:15px;margin-top:5px;">
					选择分组：
					<select class="groupSelect workflowSelect" id="groupList"  onchange="submitChange()">
					<%
						boolean isFirst = false;
						for(int groupindex = 0; groupindex < oAllManagedSCMGroups.size(); groupindex++){
							SCMGroup oCurrGroup = (SCMGroup)oAllManagedSCMGroups.getAt(groupindex);
							if(oCurrGroup == null){
								continue;
							}
							if(groupindex ==0 && oCurrGroup != null){
								isFirst = true;
							}else if(!isFirst && oCurrGroup != null){
								isFirst = true;
							}
							if(nSelectedSCMGroupId == 0 && isFirst){
								nSelectedSCMGroupId = oCurrGroup.getId();
							}
					%>
						<option value=<%=oCurrGroup.getId()%> <%if(nSelectedSCMGroupId == oCurrGroup.getId()) {%> selected="selected"<%}%>><%=CMyString.transDisplay(oCurrGroup.getGroupName())%></option>
					<%
						}//END FOR LOOP
					%>
						<input type="hidden" name="RecId" value=<%=nRecId%> />
						<input type="hidden" name="SiteType" value=<%=nSiteType%> />
						<input id="selectedgroupinput" type="hidden" name="SCMGroupId" value=0 />
					</select>
				</form>
				<%
				} //END IF
				%>
				<div class="clearFloat"></div>	
	</div>
<!--根据选择的分组，显示分组中的微博帐号-->
<%
	// 调用服务
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	HashMap oAccsOfGrpParams = new HashMap(); 
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSelectedSCMGroupId));
	Accounts oAccounts = null;
	try{
		oAccounts = (Accounts) oProcessor.excute(sServiceId,sMethodName,oAccsOfGrpParams);
	}catch(Exception weiboException){
		String sErrorMsg = weiboException.getMessage();
		String sExpriedDetail = "新浪微博错误代码： 21327！错误信息：expired_token";
		if(sErrorMsg.indexOf(sExpriedDetail) > 0){
			String url = "account/binding_account.jsp?SCMGroupId=" + nSelectedSCMGroupId + "&ExtraType=1";
			response.sendRedirect(url);
			return;
		}else{
			throw weiboException;
		}
	}
%>
	<div class="createUserContent" style="margin-left:11px">
		<div class="createUserListContent">
			<div class="v_show">
				<span class="createPrev"><img src="images/create_left_btn.png" class="createPrePic" /></span>
				<div class="v_content">
				<%
					String sAccountIds = "";
					if(oAccounts.size() > 0){
				%>
					<div class="v_content_list">
						<ul id="headUlStyle">
							<%
							for(int i=1;i<=oAccounts.size();i++){
								Account oAccount = (Account) oAccounts.getAt(i-1);
								if (oAccount == null)
									continue;
								sAccountIds=sAccountIds+","+oAccount.getId();
								
								String sHeadPath = oAccount.getHeadPic();
								if(CMyString.isEmpty(sHeadPath)){
									sHeadPath = "images/no_head.png";
								}else{
									sHeadPath = "file/read_image.jsp?FileName="+sHeadPath;
								}
								String sPlatformPath = "./images/"+oAccount.getPlatform().toLowerCase()+"_logo.png";
							%>
								<li>
									<div class="createUserPic" id="user_<%=i%>" onclick="createUserChangeState(<%=i%>)">
										<span><img id="user_h<%=i%>" src="<%=CMyString.filterForHTMLValue(sHeadPath)%>"  style="width:42px;height:42px;border: 1px solid #C0C0C0;-moz-border-radius: 8px;-webkit-border-radius: 8px;border-radius: 8px;" /></span>
										<span class="createLogoPic"><img id="user_l<%=i%>" src="<%=CMyString.filterForHTMLValue(sPlatformPath)%>" /></span>
										<span class="createSelectPic"><img id="user_t<%=i%>" src="images/selected.png" /></span>
									</div>
								</li>
							<%
							}
							if(!CMyString.isEmpty(sAccountIds)){
								sAccountIds = sAccountIds.substring(1);
							}
							if(IS_DEBUG){
								System.out.println("sAccountIds:"+sAccountIds);
							}
							%>
						</ul>
					</div>
				<%
					}else{
				%>
					<div style="text-align:center;color:#AD251A;font-size:14px;font-weight:bold;width:517px;height:47px;line-height:45px;">
						该分组没有绑定的微博帐号，请您选择其它分组或绑定账号后发布微博！
					</div>
				<%}%>
				</div>
				<span class="createNext"><img src="images/create_right_btn.png" class="createNextPic" /></span>
			</div>
		</div>
		<div class="clearFloat"></div>
	</div>
<div class="clearFloat"></div>
	<!--输入框显示-->
	<%
		//获得文档所在的栏目配置的发布模板内容
		HashMap hParameters = new HashMap();
		hParameters.put("RecId", String.valueOf(nRecId));
		hParameters.put("SiteType", String.valueOf(nSiteType));
		oProcessor.reset();
		Object result = oProcessor.excute("wcm61_scmmicrocontenttemplate","getSCMMicroContent", hParameters);
		String sContent = ((String) result).toString();
		
		if(CMyString.isEmpty(sContent)){
			sContent = oCurrDocument.getTitle();
		}

		// 文档的来源只是为了区分是来自WCM还是SCM，所以这里不再取WCM文档的发布地址，而是使用简单的WCM代替
		String sSource = "WCM";
	%>
	<div class="linkOrFont" style="margin-left:11px">
		<div class="floatRight">
			<span class="countTxt">还可以输入<em id="showNum" class="wcmSelectFont">140</em>字</span>
		</div>
		<div class="clearFloat"></div>
	</div>
	<textarea id="myExpress" class="textInput" tabindex="1" style="margin-left:11px;overflow-y:auto"><%=CMyString.filterForHTMLValue(sContent)%></textarea>
	<span type="text" value="" id="news_title_limit" ></span>
	<div style="margin-left:11px;margin-top:5px;position:relative;">
		<form action="upload_file.jsp" name="upLoadForm" id="upLoadForm" method="post" enctype="multipart/form-data" target="setPicIframe">
			<span id="showFaceSpan" class="faceStyle">
				<img id="showFace" src="images/face.png" name="face" style="vertical-align:middle;"/>
				<input type="button" id="faceText" class="textStyle" border=0 onFocus="this.blur()" value="表情" style="vertical-align:middle;"/>
			</span>&nbsp;
			<span style="cursor:pointer" class="picStyle">
				<input type="file" name="MyFile" style="cursor:pointer;width: 45px;left: 58px; position: absolute;" class="fileInput" hideFocus="true" id="MyFile" />
				<img src="images/picture.png" name="pic" id="showPic" style="vertical-align: middle;" /> <label class="textStyle">图片</label>
			</span>&nbsp;
			<span class="deletePicture" id="deletePicture" >
				<span style="cursor:pointer;margin-left:7px" onclick="deletePicture1()">
					<img src="images/delete_flag.png" name="deletePic" style="vertical-align:middle;" />
					<label class="textStyle">删除</label>
				</span>&nbsp;
			</span>
		</form>
	</div>
	<center>
		<center style="margin:0px;padding:0px;hieght:113px;" id="imgContainer">
		<div style="width:112px;height:112px;vercial-align:middle;112px;margin:0px;padding:0px;">
			<img id="img_ViewThumb" src="images/no_pic.png" onload="AutoResizeImage(112,112,this)"/>
		</div>
		<IFRAME id="setPicIframe" name="setPicIframe" frameborder="no" border="2px solid red" framespacing="0" width="0" height="0" scrolling="no" ></IFRAME>
		<input type="hidden" id="picture" />
	</center>
	<%@ include file="include/create_face.jsp"%>
	<%
		String sWorkFlowErrorMsg = "";
		int nHasWorkFlow = 0;
		try{
			nHasWorkFlow = hasWorkFlow(nSelectedSCMGroupId,oProcessor) ? 1 : 0;
		}catch(WCMException e){
			sWorkFlowErrorMsg = e.getMessage();
		}
	%>
<script>
	<!--
	$(function(){
		userPicList(1);
		$("#MyFile").live('change', function(){
			uploadFile();
		});
		setFocus("myExpress");
	});
	function submitChange(){
		$("#selectedgroupinput").attr("value",$("#groupList option:selected").val());
		$('#queryAccountsForm').submit();
	}
	//-->
	</script>
	<script>
	function uploadFile(){
		$("#upLoadForm").submit();
	}
	var m_cbCfg = {
		btns:[
				{//绘制发布按钮
					text: '发布',
					cmd : function(){
						var workflowerror = "<%=sWorkFlowErrorMsg%>";
						if(workflowerror != ""){
							alert(workflowerror);
							return false;
						}
						var picture = $("#picture").val();
						var m_AccountIds="";
						var m_FinalAccountIds="";
						for(var i=0;i<<%=oAccounts.size()%>;i++){
							if(!$("#user_h"+(i+1)).hasClass("grayPic")){
								m_AccountIds =m_AccountIds+i+",";
							}
						}
						//获取选中的头像的下标串
						m_AccountIds=m_AccountIds.substring(0,m_AccountIds.length-1);
						var m_ArraySelectIds= m_AccountIds.split(",");
						var m_AllIds = "<%=CMyString.filterForJs(sAccountIds)%>";
						var m_ArrayAllIds = m_AllIds.split(",");
						//根据获取的头像下标串，获取头像所对应的AccountId
						for(var j=0;j<m_ArraySelectIds.length;j++){
							m_FinalAccountIds = m_FinalAccountIds + m_ArrayAllIds[m_ArraySelectIds[j]]+",";
						}
						m_FinalAccountIds = m_FinalAccountIds.substring(0,m_FinalAccountIds.length-1);
						var microContent = $.trim($("#myExpress").val());
						microContent = transEnter(microContent);
						var lenInfo = forbidLength();
						//判空校验
						if(m_FinalAccountIds=="undefined" || m_FinalAccountIds=="" || m_FinalAccountIds==null ){
							alert("至少选中一个帐号！");
							return false;
						}else if(microContent=="" || microContent==null || microContent == "undefined"){
							alert("请输入内容！");
							return false;
						}else if(lenInfo < 0){
							alert("微博内容超过140个字！请编辑后重新发布！");
							return false;
						}else{
							var jsonParams = {_MicroContent:microContent,_AccountIds:m_FinalAccountIds,_SCMGroupId:$('#groupList option:selected').val(),_Picture:picture,_Source:"<%=CMyString.filterForJs(sSource)%>",_hasWorkFlow:'<%=nHasWorkFlow%>'};
							this.notify(jsonParams);
						}
					}
				}
			]
	};
	</script>
</body>
</html>
