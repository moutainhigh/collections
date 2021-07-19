<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyles" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	//获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	PageStyle oPageStyle = null;
	String sPageStyleName = "";//页面风格名称
	String sPageStyleDesc = "";
	boolean bHasRight = false;
	if(nPageStyleId>0){
		oPageStyle = PageStyle.findById(nPageStyleId);
		if(oPageStyle==null){
			throw new WCMException(CMyString.format(LocaleServer.getString("style_name_addedit.id.zero","获取页面风格[Id={0}]失败！"),new int[]{nPageStyleId}));
		}
		//权限判断
		bHasRight = SpecialAuthServer.hasRight(loginUser, oPageStyle, SpecialAuthServer.STYLE_EDIT);
		if (!bHasRight) {
			throw new WCMException(CMyString.format(LocaleServer.getString("style_name_addedit.noRight","您没有权限修改风格【{0}】！"),new String[]{ oPageStyle.getStyleDesc()}));
		}
		//获取风格
		sPageStyleName = oPageStyle.getStyleName();
		sPageStyleDesc = oPageStyle.getStyleDesc();
	}else{
		oPageStyle = new PageStyle();
		//权限判断
		bHasRight = SpecialAuthServer.hasRight(loginUser, oPageStyle, SpecialAuthServer.STYLE_ADD);
		if (!bHasRight) {
			throw new WCMException(LocaleServer.getString("style_name_addedit.newStyle.noRight","您没有权限新建风格！"));
		}
	}
%>
<html>
<head>
	<title WCMAnt:param="style_name_addedit.stylemanage">风格管理</title>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
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
		}
		.red_span{
			font-family:"宋体";
			font-size:12px;
			color:red;
		}
		.footer{
			position:absolute;
			left:0px;
			bottom:0px;
			width:100%;
			height:56px;
			background: transparent url(../images/list/list-bg-bottom.gif) center bottom repeat-x;
		}
		input{
			border:1px solid #b4b4b4;	
		}
	</style>
	<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<!-- 公共js @ begin -->
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<!-- 校验所需js @ BEGIN -->
	<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<!-- 校验所需js @ END -->
	<!--Ajax Start-->
	<script src="../../js/easyversion/ajax.js"></script>
	<script src="../../js/easyversion/basicdatahelper.js"></script>
	<script src="../../js/easyversion/web2frameadapter.js"></script>
	<!--Ajax End-->
	<SCRIPT LANGUAGE="JavaScript">
		function checkStyleDesc(bSave){
			BasicDataHelper.Call('wcm61_pagestyle', 'checkStyleDesc', {StyleDesc : $('StyleDesc').value, objectId : $('ObjectId').value}, true, 
				function(_transport,_json){
					var bExsit = $a(_json, 'result');
					if(bExsit[0] == 'true'){
						$('StyleDesc').setAttribute("DescCanUsed", 'false');
						 Ext.Msg.alert("当前的中文名字已经被使用，请重新输入！");
					}else{
						$('StyleDesc').setAttribute("DescCanUsed", 'true');
						if(bSave)
							save();
					}
				}
			);
		}
		//保存页面风格的名称
		function doSaveStyleName(){
			// 数据校验
			if(!ValidationHelper.doValid("frm")){
				return false;
			}
			if($('StyleDesc').getAttribute("DescCanUsed") == 'false'){
				checkStyleDesc(true);
				return false;
			}else{
				save();
			}
			
		}
		function save(){
			BasicDataHelper.call("wcm61_PageStyle", 'save', 'frm', 'true', function(){ 
				parent.document.location.reload();
				try{
					if(parent.parent.opener)
						parent.parent.opener.location.reload();
				}catch(err){
					//do nothing
				}
			});
		}
		Event.observe(window,"load", function(){
			ValidationHelper.initValidation();
		});
	</SCRIPT>
</head>
<body style="padding:0px;margin:0px;">
<form id="frm" style="padding:0px;margin:0px;">
	<input type="hidden" name="ObjectId" value="<%=nPageStyleId%>" />
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
		<tr>
			<td valign="top" style="padding:5 10 0 10">
				<table border="0" cellspacing="0" cellpadding="0" style="border:1px #acddfd solid;background-color:#ffffff;padding-bottom:20px;" width="100%">
					<tr>
						<td width="100%" style="padding:20 0 20 20">
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td class="name_td_left" WCMAnt:param="style_name_addedit.showName">显示名称:</td>
									<td><input type="text" name="StyleDesc" id="StyleDesc" DescCanUsed="false" value="<%=sPageStyleDesc%>" validation="desc:'风格名称',required:true,max_len:50,type:'string',rpc:'checkStyleDesc'"/> <span class="note_span_right"></span></td>
								</tr>
								<tr>
									<td class="name_td_left" WCMAnt:param="style_name_addedit.enName">英文名称:</td>
									<td><input type="text" name="StyleName" value="<%=sPageStyleName%>" <%=(!"".equals(sPageStyleName)? "disabled='true'" : "")%>/> <span class="note_span_right" WCMAnt:param="style_name_addedit.pic_catalog_style">系统将根据英文名称生成图片、目录和风格文件</span></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="center"><img src="./images/baocun.gif"  id="savebutton" onclick="doSaveStyleName();" width="85px" height="35px" border="0" style="cursor:hand;"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div class="footer">
					
				</div>
			</td>
		</tr>
	</table>
<form>
</body>
</html>