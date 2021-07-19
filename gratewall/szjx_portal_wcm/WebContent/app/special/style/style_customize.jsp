<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	//获取当前用户的权限，以便于控制“快速通道”中显示的入口
	 
	//获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);

	// 获取资源分类
	String sCustomStyle = "";
	String sPageStyleName = ""; //英文名
	PageStyle currPageStyle = null;
	if(nPageStyleId>0){
		currPageStyle = PageStyle.findById(nPageStyleId);
		if(currPageStyle==null){
			throw new WCMException(CMyString.format(LocaleServer.getString("style_customize.id.zero","获取页面风格[Id{0}]失败！"),new int[]{nPageStyleId}));
		}
		//权限判断
		boolean bHasRight = SpecialAuthServer.hasRight(loginUser, currPageStyle, SpecialAuthServer.STYLE_EDIT);
		if (!bHasRight) {
			throw new WCMException(CMyString.format(LocaleServer.getString("style_customize.noRight","您没有权限修改风格【{0}】！"),new String[]{currPageStyle.getStyleDesc()}));
		}

		sCustomStyle = currPageStyle.getCustomStyle();
		sPageStyleName = currPageStyle.getStyleName();
	}
	if("".equals(sCustomStyle)){
		sCustomStyle = LocaleServer.getString("style_customize.input","请输入css文本...");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title WCMAnt:param="style_customize.">自定义风格</title>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 公共js @ begin -->
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<SCRIPT LANGUAGE="JavaScript">
		//批量上传图片
		function uploadImages(_nPageStyleId){
			_nPageStyleId = _nPageStyleId || 0;
			top.window.wcm.CrashBoarder.get('upload_images').show({
				title : '批量上传图片',
				src : './flash_upload/photos_import.jsp?PageStyleId='+_nPageStyleId,
				width:'515px',
				height:'340px',
				callback : function(params){
					this.close();
					frmData.CustomStyle.focus();
				}
			});
		}
		
		//管理批量上传的图片
		function managerImages(_nPageStyleId){
			_nPageStyleId = _nPageStyleId || 0;
			top.window.wcm.CrashBoarder.get('manage_images').show({
				title : '管理上传图片',
				src : './manager_photos.jsp?PageStyleId='+_nPageStyleId,
				width:'600px',
				height:'340px',
				callback : function(params){
					this.close();
					frmData.CustomStyle.focus();
				}
			});
		}
		//聚焦文本
		function focusCustome(_textarea){
			if(_textarea.innerText=="请输入css文本..."){
				_textarea.innerText = "";
			}
		}
		
		//清空
		function clearCustome(){
			var oForm = document.getElementById("frmData");
			if(!oForm){
				return;
			}
			oForm.CustomStyle.innerText="";
		}
		//保存自定义
		function  saveCustome(_nPageStyleId){
			var oForm = document.getElementById("frmData");
			if(!oForm){
				return;
			}
			if(oForm.CustomStyle.innerText =="请输入css文本..."){
				return;
			}
			_nPageStyleId = _nPageStyleId || 0;
			//window.top.ProcessBar.init();
			//window.top.ProcessBar.start("正在执行保存操作，请稍后......");
			var postData = {
				ObjectId : _nPageStyleId,
				CustomStyle : oForm.CustomStyle.innerText
			}
			top.window.BasicDataHelper.call("wcm61_pagestyle", "saveCustomStyle", postData, "true", function(){
				//window.top.ProcessBar.close();
				document.location.reload();

			});
		}
		
	</SCRIPT>
	<style type="text/css">
		
		html, body{
			padding:0px;
			margin:0px;
			width:100%;
			height:100%;
			width:100%;
			overflow:hidden;
		}
		.name_td_left{
			font-family:"宋体";
			font-size:12px;
			font-weight:bold;
			text-align:left;
			width:66px;
		}
		.note_span_right{
			font-family:"宋体";
			font-size:12px;
			padding-left:5px;
		}
		.red_span{
			font-family:"宋体";
			font-size:12px;
			color:red;
		}
		.font12{
			font-family:"宋体";
			font-size:12px;
		}
		.note_color{
			font-size:12px;
			font-family:"宋体";
			color:#097f12;
		}
		.basic_info_nav{
			background:url(./images/top_bg.gif) repeat-x;
			height:28px;
			color:#333333;
		}
		.td_jiben_texing{
			font-family:"宋体";
			font-size:12px;
			color:#333333;
			font-weight:bold;
		}
		.footer{
			position:absolute;
			left:0px;
			bottom:0px;
			width:100%;
			height:56px;
			background: transparent url(../images/list/list-bg-bottom.gif) center bottom repeat-x;
		}
		div{
			scrollbar-face-color: #f6f6f6;
			scrollbar-highlight-color: #ffffff;
			scrollbar-shadow-color: #cccccc; 
			scrollbar-3dlight-color: #cccccc; 
			scrollbar-arrow-color: #330000; 
			scrollbar-track-color: #f6f6f6; 
			scrollbar-darkshadow-color: #ffffff;
		}
		.areatd{
			height:300px;
		}
		.res-1024x768 .areatd{
			height:200px;
		}

	</style>
</head>
<body>
	<form id="frmData" name="frmData" style="padding:0px;margin:0px;">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%" table-layout="fixed">
		<!-- 头 begin-->
		<!--
		<tr>
			<td width="100%" class="basic_info_nav" height="28px">
				<table border="0" cellspacing="0" cellpadding="0" width="100%" height="28px">
					<tr>
						<td align="center" width="40px"><img src="./images/top_icon.gif" width="19px" height="28px" border="0"/></td>
						<td align="left" class="td_jiben_texing">自定义</td>
					</tr>
				</table>
			</td>
		</tr>
		-->
		<!-- 头 end-->
		<tr>
			<td valign="top" style="padding:10 10 0 10;" height="100%" table-layout="fixed">
				<div style="height:100%;width:100%;overflow:hidden;">
					<table border="0" cellspacing="0" cellpadding="0" width="620px" height="100%" align="center" valign="top" table-layout="fixed">
						<tr>
							<td width="100%" height="100%" align="center" valign="top" style="border:1px #acddfd solid;background-color:#ffffff;padding-bottom:20px;padding-top:10px;" table-layout="fixed">
								<table height="100%" border="0" cellspacing="0" cellpadding="0" width="555px" table-layout="fixed">
									<tr>
										<td colspan="2" style="padding-top:5px" class="font12" height="50px">
											<table border="0" cellspacing="0" cellpadding="0" width="100%" class="font12">
												<tr>
													<td width="40px" WCMAnt:param="style_customize.exp">说明:</td>
													<td class="note_color" WCMAnt:param="style_customize.picServer">1.图片所在服务器目录：/template/style/style_common/<%=sPageStyleName%></td>
												</tr>
												<tr>
													<td width="40px">&nbsp;</td>
													<td style="padding-top:5px" class="note_color" WCMAnt:param="style_customize.picUploadPath">2.上传图片引用路径：../style_common/<%=sPageStyleName%>/图片名称</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="font12" width="150px" height="30px" align="left" WCMAnt:param="style_customize.style.content">输入自定义的样式内容:</td>
										<td align="right" height="30px"><img src="./images/shangchuantupian.gif" onclick="uploadImages(<%=nPageStyleId%>)" width="87px" height="24px" border="0" style="cursor:hand;" title="批量上传图片" WCMAnt:paramattr="title:style_customize.picUpload">&nbsp;&nbsp;<img src="./images/guanlitupian.gif" onclick="managerImages(<%=nPageStyleId%>)" width="63px" height="24px" border="0" style="cursor:hand;" title="管理图片" WCMAnt:paramattr="title:style_customize.picManage"></td>
									</tr>
									<tr>
										<td colspan="2" style="padding-top:5px" table-layout="fixed"  class="areatd">
											<textarea name="CustomStyle" style="height:100%;width:100%;" onfocus="focusCustome(this)"><%=sCustomStyle%></textarea>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td align="center" style="padding-right:30px;padding-top:10px;height:40px;"><img src="./images/baocun.gif" onclick="saveCustome(<%=nPageStyleId%>)"  width="85px" height="35px" border="0" style="cursor:hand;"/><img src="./images/qingkong.gif" onclick="clearCustome()"  width="85px" height="35px" border="0" style="cursor:hand;margin-left:15px;"/></td>
			</tr>
		<tr>
			<td height="56px">
				<div class="footer"></div>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>