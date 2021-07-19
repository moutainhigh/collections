<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>
<%@ page import="com.trs.scm.persistent.SCMGroups" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ include file="../include/public_server.jsp"%>
<%@ include file="include/workflow_check.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 构造参数
	int nSCMGroupId = 0;
	int nSessionSCMGroupId = 0;
	if(session.getAttribute("SCMGroupId")!=null){
		nSessionSCMGroupId = Integer.parseInt(session.getAttribute("SCMGroupId").toString());
	}
	// 2 判断是否接收到分组id信息，否则从session中获取。
	if(nSCMGroupId==0 && nSessionSCMGroupId>0){
		nSCMGroupId = nSessionSCMGroupId;
	}
	// 获取用户可以管理的分组
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	HashMap oParamMap = new HashMap();
	oParamMap.put("UserId", String.valueOf(loginUser.getId()));
	SCMGroups oManageGroups = (SCMGroups) oProcessor.excute("wcm61_scmgroup", "getGroupsOfUser", oParamMap);
	oProcessor.reset();
	// 如果用户可以管理的分组为空，则给出提示信息，并关闭当前页面
	if(oManageGroups == null || oManageGroups.size() == 0){
	%>
	<script language="javascript">
	<!--
		alert("您没有可维护的帐号分组，不能进行微博的发布！");
		top.window.CrashBoard.get("createMicroblog").close();
	//-->
	</script>
	<%
		return;
	}
	// 如果传过来的分组Id不在用户可以维护的分组中，则获取用户可以维护的第一个有帐号的分组
	String sServiceId = "wcm61_scmaccount",sMethodName="findAccountsOfGroup";
	HashMap oAccsOfGrpParams = new HashMap();
	Accounts oAccounts = null;
	if(nSCMGroupId!=0 && oManageGroups.indexOf(nSCMGroupId) < 0){
	%>
		<script language="javascript">
		<!--
			alert("您维护此分组的权限已被解除，将使用您的其他分组！");
			//top.window.CrashBoard.get("createMicroblog").close();
		//-->
		</script>
	<%
		for(int i=0;i<oManageGroups.size();i++){
			SCMGroup tempGrp=(SCMGroup)oManageGroups.getAt(i);
			session.setAttribute("SCMGroupId",String.valueOf(tempGrp.getId()));
			nSCMGroupId =tempGrp.getId();
			break;
		}
	}
	if(nSCMGroupId==0){
		for(int i=0;i<oManageGroups.size();i++){
			SCMGroup tempGrp=(SCMGroup)oManageGroups.getAt(i);
			session.setAttribute("SCMGroupId",String.valueOf(tempGrp.getId()));
			nSCMGroupId =tempGrp.getId();
			break;
		}
	}
	oAccsOfGrpParams.clear();
	oAccsOfGrpParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
	oAccounts = (Accounts) oProcessor.excute(sServiceId, sMethodName, oAccsOfGrpParams);
	
	if(oAccounts == null || oAccounts.size() == 0){
	%>
		<script language="javascript">
		<!--
			alert("您维护的分组中还没有绑定帐号，请进入帐号管理页面绑定帐号后，再发布微博！");
			top.window.CrashBoard.get("createMicroblog").close();
		//-->
		</script>
	<%
		return;
	}
	String sSource = "SCM";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>创建微博</title>
<link rel="stylesheet" href="create_microblog.css">
<link rel="stylesheet" href="css/show_face_create.css">
<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/scm_common.js"></script>
<script src="create_microblog.js"></script>
<script src="js/show_face_create.js"></script>
<script>
<!--
	$(function(){
		userPicList(1);
		$("#MyFile").live('change', function(){
			uploadFile();
		});
		setFocus("myExpress");
	});
	//图片预加载
	function loadImage(url, callback) {
		var img =new Image(); //创建一个Image对象，实现图片的预下载
		img.onload =function(){
			img.onload = null;
			callback(img);
		}
		img.src = url; 
	}
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
	var docsource = "";
	var recIdsParam = 0;
	function showDocumentList(){
		var nWidth = 780;
		var nHeight = 400;
		var sUrl = "../document/document_select_index.jsp?selectType=radio&recIds="+recIdsParam+"&OnlyPublished=1";
		var dialogArguments = window;
		var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
		var info = window.showModalDialog(sUrl, dialogArguments, sFeatures);
		if(info == null || info.ids[0] == null || info.infos[info.ids[0]] == null){
			return;
		}
		var doctitle = info.infos[info.ids[0]].docTitle;
		var siteType = info.infos[info.ids[0]].siteType;
		var recid = info.infos[info.ids[0]].recId;
		recIdsParam = recid;
		$.ajax({
			type:"post",
			url:"get_document_abstract.jsp",
			dataType:"text",
			data:{RecId: recid,SiteType:siteType},
			success: function(data){
				if($.trim(data) == 1){
					alert("抱歉！获取文档失败！\n文档ID：" + recid + "\n文档标题：" + doctitle);
					return;
				}else if($.trim(data) == 2){
					alert("抱歉！文档未发布！请确认文档已通过审核并发布！\n文档ID：" + recid + "\n文档标题：" + doctitle);
					return;
				}
				if($.trim(data) == ""){
					$("#myExpress").val(doctitle);
				}else{
					$("#myExpress").val($.trim(data));
				}
				//定义全局变量：文档来源，如果文档是从WCM选择的，则来源于WCM
				docsource = "WCM";
			},
			error:function(){
				alert("发送请求失败！\n文档ID：" + recid + "\n文档标题：" + info.infos[info.ids[0]].docTitle);
			}
		});
	}
	function uploadFile(){
		$("#upLoadForm").submit();
	}
//-->
</script>
</head>
<body>
	<div class="createUserContent" style="margin-left:11px">
		<div class="createUserListContent">
			<div class="v_show">
				<span class="createPrev"><img src="images/create_left_btn.png" class="createPrePic" /></span>
				<div class="v_content">
					<div class="v_content_list">
						<ul id="headUlStyle">
							<%
							String sAccountIds = "";
							for(int i=0;i<oAccounts.size();i++){
								Account oAccount = (Account) oAccounts.getAt(i);
								if (oAccount == null || oAccount.getStatus()!=1){
									continue;
								}
								sAccountIds +=","+oAccount.getId();
								String sHeadPath = oAccount.getHeadPic();
								if(CMyString.isEmpty(sHeadPath)){
									sHeadPath = "images/no_head.png";
								}else{
									sHeadPath = "file/read_image.jsp?FileName="+sHeadPath;
								}
								String sName = oAccount.getAccountName();
								String sPlatformPath = "./images/"+oAccount.getPlatform().toLowerCase()+"_logo.png";
								int nFlag = i+1;
							%>
								<li>
									<div class="createUserPic" id="user_<%=nFlag%>" onclick="createUserChangeState(<%=nFlag%>)">
										<span><img id="user_h<%=nFlag%>" src="<%=CMyString.filterForHTMLValue(sHeadPath)%>"  style="width:42px;height:42px;border: 1px solid #C0C0C0;-moz-border-radius: 8px;-webkit-border-radius: 8px;border-radius: 8px;" title="<%=CMyString.filterForHTMLValue(sName)%>" /></span>
										<span class="createLogoPic"><img id="user_l<%=nFlag%>" src="<%=CMyString.filterForHTMLValue(sPlatformPath)%>" title="<%=CMyString.filterForHTMLValue(sName)%>" /></span>
										<span class="createSelectPic"><img id="user_t<%=nFlag%>" src="images/selected.png" title="<%=CMyString.filterForHTMLValue(sName)%>_<%=nFlag%>" /></span>
									</div>
								</li>
							<%
							}
							if(sAccountIds.length()>0){
								sAccountIds = sAccountIds.substring(1);
							}
							if(IS_DEBUG){
								System.out.println("sAccountIds:"+sAccountIds);
							}
							%>
						</ul>
					</div>
				</div>
				<span class="createNext"><img src="images/create_right_btn.png" class="createNextPic" /></span>
			</div>
		</div>
		<div class="clearFloat"></div>
	</div>
	<div class="clearFloat"></div>
	<div class="linkOrFont" style="margin-left:11px">
		<div class="floatLeft">
			<a href="javascript:void(0)" class="fromWCM" onclick="showDocumentList()" tabindex="2">从WCM选择</a>
			<span style="line-height:23px;font-family:微软雅黑">&nbsp;&nbsp;(您可以从WCM中直接选择要发布的内容)</span>
		</div>
		<div class="floatRight" style="margin-top:4px;">
			<span class="countTxt">还可以输入<em id="showNum" class="wcmSelectFont">140</em>字</span>
		</div>
		<div class="clearFloat"></div>
	</div>
	<textarea id="myExpress" class="textInput" tabindex="1" style="margin-left:11px;overflow-y:auto"></textarea>
	<span type="text" value="" id="news_title_limit" ></span>
	<div style="margin-left:11px;margin-top:5px;position:relative;">
		<form action="upload_file.jsp" name="upLoadForm" id="upLoadForm" method="post" enctype="multipart/form-data" target="setPicIframe">
			<span id="showFaceSpan">
				<img id="showFace" src="images/face.png" name="face" style="vertical-align:middle;"/>
				<input type="button" id="faceText" class="textStyle" border=0 onFocus="this.blur()" value="表情" style="vertical-align:middle;"/>
			</span>&nbsp;
			<span style="cursor:pointer">
				<input type="file" name="MyFile" style="cursor:pointer;width: 45px;left: 58px; position: absolute;" class="fileInput" hideFocus="true" id="MyFile" />
				<img src="images/picture.png" name="pic" id="showPic" style="vertical-align: middle;" /> <label class="textStyle">图片</label>
			</span>&nbsp;
			<span class="deletePicture" id="deletePicture" >
				<span style="cursor:pointer;" onclick="deletePicture()">
					<img src="images/delete_flag.png" name="deletePic" style="vertical-align:middle;" />
					<label class="textStyle">删除</label>
				</span>&nbsp;
			</span>
		</form>
	</div>
	<center style="margin:0px;padding:0px;hieght:113px;display:none" id="imgContainer">
		<div style="width:112px;height:112px;vercial-align:middle;112px;margin:0px;padding:0px;">
			<img id="img_ViewThumb" onload="AutoResizeImage(112,112,this)"/>
		</div>
		<IFRAME id="setPicIframe" name="setPicIframe" frameborder="no" border="2px solid red" framespacing="0" width="0" height="0" scrolling="no" ></IFRAME>
		<input type="hidden" id="picture"/>
	</center>
	<%@ include file="include/create_face.jsp"%>
	<%
		String sWorkFlowErrorMsg = "";
		int nHasWorkFlow = 0;
		try{
			nHasWorkFlow = hasWorkFlow(nSCMGroupId,oProcessor) ? 1 : 0;
		}catch(WCMException e){
			sWorkFlowErrorMsg = e.getMessage();
		}
	%>
	<script language="javascript">
	<!--
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
					if(<%=oAccounts.size()%>>0){
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
							if(docsource != ""){
								//若从WCM中选取文档，则给出发布文章的连接地址
								var jsonParams = {_MicroContent:microContent,_AccountIds:m_FinalAccountIds,_SCMGroupId:'<%=nSCMGroupId%>',_Picture:picture,_Source:docsource,_hasWorkFlow:'<%=nHasWorkFlow%>'};
								this.notify(jsonParams);
							}else{
								var jsonParams = {_MicroContent:microContent,_AccountIds:m_FinalAccountIds,_SCMGroupId:'<%=nSCMGroupId%>',_Picture:picture,_Source:"<%=CMyString.filterForJs(sSource)%>",_hasWorkFlow:'<%=nHasWorkFlow%>'};
								this.notify(jsonParams);
							}
						}
					}
				}
			}
		]
	};
	//-->
	</script>
</body>
</html>