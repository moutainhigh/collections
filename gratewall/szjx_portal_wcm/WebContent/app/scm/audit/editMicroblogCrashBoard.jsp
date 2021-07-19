<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.scm.persistent.Account" %>
<%@ page import="com.trs.scm.persistent.Accounts" %>
<%@ page import="com.trs.scm.sdk.model.MicroUser" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 2 构造参数
	String sPublishingAccountIds = currRequestHelper.getString("PublishingAccountIds");
	int nSCMMicroContentId = currRequestHelper.getInt("SCMMicroContentId",0);

	String sServiceId = "wcm61_scmmicrocontent",sMethodName="findById";
	HashMap paramters = new HashMap();
	paramters.put("ObjectId",String.valueOf(nSCMMicroContentId));

	// 3 获取待发布微博信息
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	SCMMicroContent oSCMMicroContent = (SCMMicroContent) oProcessor.excute(sServiceId,sMethodName,paramters);
	String sContent = oSCMMicroContent.getContent();
	String sPictureName = oSCMMicroContent.getPicture();
	String sPictureInput = "<input type=\"hidden\" id=\"picture\" />";
	String sPicturePath = "";
	if(!CMyString.isEmpty(sPictureName)){
		sPicturePath = FilesMan.getFilesMan().mapFilePath(sPictureName, FilesMan.PATH_HTTP) + sPictureName;
		sPictureInput = "<input type=\"hidden\" value=\""+sPictureName+"\" id=\"picture\" />";
	}

	String sSource = oSCMMicroContent.getSource();
	int nGroupId = oSCMMicroContent.getGroupId();
	boolean isRetweeted = oSCMMicroContent.isRetweeted();

	// 4 获取待发布分组帐号信息
	oProcessor.reset();
	paramters.clear();
	paramters.put("SCMGroupId", String.valueOf(nGroupId));
	sServiceId = "wcm61_scmaccount"; sMethodName = "findAccountsOfGroup";
	Accounts oAllAccounts = (Accounts)oProcessor.excute(sServiceId,sMethodName,paramters);
	
	List<String> oPublishingAccounts = Arrays.asList(sPublishingAccountIds.split(","));
	
	int index = 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑微博</title>
<link rel="stylesheet" href="../create_microblog.css">
<link rel="stylesheet" href="../css/show_face_forward.css">
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../create_microblog.js"></script>
<script src="../js/show_face_create.js"></script>
<script>
	$(function(){
		userPicList(0);
		$("#MyFile").live('change', function(){
			uploadFile();
		});
		setFocus("myExpress");
		if($("#img_ViewThumb").attr("src")!=""){
			$("#imgContainer").css("display","inline");
			$(".deletePicture").css("display","inline");
			top.window.CrashBoard.get("createMicroblog").onResize("574px","400px");
		}
	});
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
			if(objImg.height==0 && h < 112.1){
				var pTop = (112-h)/2;
				$(objImg).parent().css("height",112-pTop);
				$(objImg).parent().css("padding-top",pTop);
			}
		}
		img.src = objImg.src;
	}
</script>
</head>
<body>

<script>
<!--

	var recIdsParam = 0;
	function showDocumentList(){
		var nWidth = 780;
		var nHeight = 400;
		var sUrl = "../../document/document_select_index.jsp?selectType=radio&recIds="+recIdsParam+"&OnlyPublished=1";
		var dialogArguments = window;
		var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
		var info = window.showModalDialog(sUrl, dialogArguments, sFeatures);
		if(info == null || info.ids[0] == null || info.infos[info.ids[0]] == null){
			return;
		}
		var recid = info.infos[info.ids[0]].recId;
		recIdsParam = recid;
		var doctitle = info.infos[info.ids[0]].docTitle;
		var siteType = info.infos[info.ids[0]].siteType;
		$.ajax({
			type:"post",
			url:"../get_document_abstract.jsp",
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
					if($("#myExpress").val() != $.trim(data)){
						if(confirm("确定要替换原来的微博内容吗？")){
							$("#myExpress").val(doctitle);
						}
					}
				}else{
					if($("#myExpress").val() != $.trim(data)){
						if(confirm("确定要替换原来的微博内容吗？")){
							$("#myExpress").val($.trim(data));
						}
					}
				}
				//定义全局变量：文档来源，如果文档是从WCM选择的，则来源于WCM
				docsource = "WCM";
			},
			error:function(){
				alert("发送请求失败！\n文档ID：" + recid + "\n文档标题：" + info.infos[info.ids[0]].docTitle);
			}
		});
	}
//-->
</script>

	<div class="createUserContent" style="margin-left:11px">
		<div class="createUserListContent">
			<div class="v_show">
				<span class="createPrev"><img src="../images/create_left_btn.png" class="createPrePic" /></span>
				<div class="v_content">
					<div class="v_content_list">
						<ul id="headUlStyle">
							<%
							// 1 显示帐号信息
							if(oAllAccounts == null || oAllAccounts.size() == 0){
							%>
								<script>alert("用户所在的分组已无可用的微博帐号！");</script>
							<%
								return;
							}
							//记录所有帐号的ID串，用于后面获取选中头像的ID
							String sAllAccountIds = "";	
							int nLength = oAllAccounts.size();
							//获取转发微博的平台
							String sPublishingPlatform = "";
							if(isRetweeted){
								sPublishingPlatform = Account.findById(Integer.valueOf(oPublishingAccounts.get(0))).getPlatform();
							}
							for(int i = 1;i <= nLength; i++){
								Account oCurrAccount = (Account)oAllAccounts.getAt(i-1);
								if(oCurrAccount == null){
									continue;
								}
								//转发微博不显示不同平台的帐号
								String sPlatform = oCurrAccount.getPlatform();
								if(isRetweeted && !sPublishingPlatform.equals(sPlatform)){
									continue;
								}
								index++;
								int nCurrAccountId = oCurrAccount.getId();
								sAllAccountIds += "" + nCurrAccountId + ",";
								String sHeadPath = oCurrAccount.getHeadPic();
								if(CMyString.isEmpty(sHeadPath)){
									sHeadPath = "../images/no_head.png";
								}else{
									sHeadPath = "../file/read_image.jsp?FileName="+sHeadPath;
								}
								String sPlatformPath = "";
								
								try{
									sPlatformPath = "../" + PlatformFactory.getPlatform(sPlatform).getLogo16();
								}catch(Exception e){
									e.printStackTrace();
								}
							%>
								<li>
									<div class="createUserPic" id="user_<%=index%>" onclick="createUserChangeState(<%=index%>)">
										<span><img id="user_h<%=index%>" src="<%=CMyString.filterForHTMLValue(sHeadPath)%>"  style="width:42px;height:42px;border: 1px solid #C0C0C0;-moz-border-radius: 8px;-webkit-border-radius: 8px;border-radius: 8px;" /></span>
										<span class="createLogoPic"><img id="user_l<%=index%>" src="<%=CMyString.filterForHTMLValue(sPlatformPath)%>" /></span>
										<span class="createSelectPic"><img id="user_t<%=index%>" src="../images/selected.png" /></span>
									</div>
								</li>
							<%
								//若非待发布帐号，则显示未选中状态
								if(oPublishingAccounts.indexOf(String.valueOf(nCurrAccountId)) < 0){
							%>
								<script>
									$("#user_h"+<%=index%>).addClass('grayPic');
									$("#user_l"+<%=index%>).addClass('grayPic');
									$("#user_t"+<%=index%>).addClass('hidden');
								</script>
							<%
								}
							}
							sAllAccountIds = sAllAccountIds.substring(0,sAllAccountIds.length()-1);
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
			<a href="javascript:void(0)" class="fromWCM" onclick="showDocumentList()">从WCM选择</a>
			<span style="line-height:23px;font-family:微软雅黑">&nbsp;&nbsp;(您可以从WCM中直接选择要发布的内容)</span>
		</div>
		<div class="floatRight">
			<span class="countTxt">还可以输入<em id="showNum" class="wcmSelectFont">140</em>字</span>
		</div>
		<div class="clearFloat"></div>
	</div>
	<textarea id="myExpress" tabindex="1" class="textInput" wrap="physical"  style="margin-left:11px;overflow-y:auto"><%=CMyString.filterForHTMLValue(sContent)%></textarea>
	<span type="text" value="" id="news_title_limit" ></span>
	<div style="margin-left:11px;margin-top:5px;position:relative;">
	<form action="../upload_file.jsp" name="upLoadForm" id="upLoadForm" method="post" enctype="multipart/form-data" target="setPicIframe">
		<span id="showFaceSpan" class="faceStyle">
			<img src="../images/face.png" name="face" id="showFace" class="margin_4PxBottom"/>
			<input type="button" id="faceText" class="textStyle" border=0 onFocus="this.blur()" value="表情" />
		</span>&nbsp;
		<%if(!isRetweeted){%>
		<span style="cursor:pointer" class="picStyle">
			<input type="file" name="MyFile" style="cursor:pointer" class="fileInput" hideFocus="true" id="MyFile" />
			<img src="../images/picture.png" class="margin_4PxBottom" name="pic" />
			<label class="textStyle">图片</label>
		</span>
		<span class="deletePicture" >
			<span style="cursor:pointer;margin-left:7px" onclick="deletePicture()">
			<img src="../images/delete_flag.png" name="deletePic" class="margin_3PxBottom"/> <label class="textStyle">删除</label></span>
		</span>
		<%}%>
	</form>
	</div>
	<center style="margin:0px;padding:0px;hieght:113px;display:none" id="imgContainer">
		<div style="width:112px;height:112px;vercial-align:middle;112px;margin:0px;padding:0px;">
			<img id="img_ViewThumb" src="<%=sPicturePath%>" onload="AutoResizeImage(112,112,this)"/>
		</div>
		<IFRAME id="setPicIframe" name="setPicIframe" frameborder="no" border="2px solid red" framespacing="0" width="0" height="0" scrolling="no" ></IFRAME>
		<%=sPictureInput%>
	</center>
	<%@ include file="../include/show_face_forward.jsp"%>
	<script>
	function uploadFile(){
		$("#upLoadForm").submit();
	}
	var m_cbCfg = {
		btns:[
				{//绘制发布按钮
					text: '确定',
					cmd : function(){
						var picture = $("#picture").val();
						var m_AccountIds="";
						var m_FinalAccountIds="";
						var m_MicroContentId=<%=nSCMMicroContentId%>;
						for(var i=1;i <= <%=index%>;i++){
							if(!$("#user_h"+i).hasClass("grayPic")){
								m_AccountIds =m_AccountIds+i+",";
							}
						}
						//获取选中的头像的下标串
						m_AccountIds=m_AccountIds.substring(0,m_AccountIds.length-1);
						var lenInfo = forbidLength();
						var m_AllIds = "<%=CMyString.filterForJs(sAllAccountIds)%>";
						var m_ArrayAllIds = m_AllIds.split(",");
						//根据获取的头像下标串，获取头像所对应的AccountId
						var m_ArraySelectIds= m_AccountIds.split(",");
						for(var j=0;j<m_ArraySelectIds.length;j++){
							//头像ID下标从1开始，帐号ID下标从0开始
							m_FinalAccountIds = m_FinalAccountIds + m_ArrayAllIds[m_ArraySelectIds[j]-1]+",";
						}
						m_FinalAccountIds = m_FinalAccountIds.substring(0,m_FinalAccountIds.length-1);
						var microContent = $.trim($("#myExpress").val());
						microContent = transEnter(microContent);
						var retweet = "<%=isRetweeted%>";
						//判空校验
						if(m_FinalAccountIds=="undefined" || m_FinalAccountIds=="" || m_FinalAccountIds==null ){
							alert("至少选中一个帐号！");
							return false;
						}else if(retweet != "true" && (microContent=="" || microContent==null || microContent == "undefined")){
							alert("请输入内容！");
							return false;
						}else if(lenInfo < 0){
							alert("微博内容超过140个字！请编辑后重新发布！");
							return false;
						}else{
							var source = "<%=CMyString.filterForJs(sSource)%>";
							if(source.length > 0){
								var jsonParams = {_MicroContent:microContent,_AccountIds:m_FinalAccountIds,_MicroContentId:m_MicroContentId,_Picture:picture,_Source:source};
								this.notify(jsonParams);
							}else if( docsource != null){
								var jsonParams = {_MicroContent:microContent,_AccountIds:m_FinalAccountIds,_MicroContentId:m_MicroContentId,_Picture:picture,_Source:docsource};
								this.notify(jsonParams);
							}
						}
					}
				}
			]
	};
	</script>
</body>
</html>