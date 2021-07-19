<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.StyleItem" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	//获取当前用户的权限
	 
	//获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",1);

	// 获取资源分类
	PageStyle currPageStyle = null;
	HashMap pageStyleItemsMap = null;//页面风格项的属性
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap parameters = new HashMap();
	String sServiceId = "";
	String sMethodName = "";
	if(nPageStyleId>0){
		currPageStyle = PageStyle.findById(nPageStyleId);
		if(currPageStyle==null){
			throw new WCMException(CMyString.format(LocaleServer.getString("style_basic_info_addedit.id.zero","获取页面风格[Id{0}]失败！"),new int[]{nPageStyleId}));
		}
		//权限判断
		boolean bHasRight = SpecialAuthServer.hasRight(loginUser, currPageStyle, SpecialAuthServer.STYLE_EDIT);
		if (!bHasRight) {
			throw new WCMException(CMyString.format(LocaleServer.getString("style_basic_info_addedit.noRight","您没有权限修改风格【{0}】！"),new String[]{currPageStyle.getStyleDesc()}));
		}
		//获取页面风格的属性
		sServiceId = "wcm61_styleitem";
		sMethodName = "queryStyleItemMap";
		parameters = new HashMap();
		parameters.put("ObjectId",String.valueOf(nPageStyleId));
		parameters.put("ObjectType",String.valueOf(PageStyle.OBJ_TYPE));
		pageStyleItemsMap = ( HashMap ) processor.excute(sServiceId,sMethodName,parameters);
	}
	//获取页面风格的每项内容
	String sBGColor = "";//背景颜色
	String sBGImage = ""; //背景图片
	String sBGRepeat = "";//平铺方式
	String sTemplateBoxWidth = "";//内容宽度
	String sFontFamily = ""; //字体
	String sFontColor = "";//字体颜色
	String sFontSize = "";//字体大小
	String sAColor = "";//未访问的链接
	String sAVisitedColor = "";//已访问的链接
	String sHoverColor = "";//鼠标移到链接上
	String sDecoration = ""; //未访问链接下划线
	String sVisitedDecoration = ""; //访问过的链接下划线
	String sHoverDecoration = "";//鼠标移动连接上下划线
	if(pageStyleItemsMap!=null){
		sBGColor = getStyleItemValue("body_background_color",pageStyleItemsMap);//背景颜色
		sBGImage = getStyleItemValue("body_background_image",pageStyleItemsMap); //背景图片
		sBGRepeat = getStyleItemValue("body_background_repeat",pageStyleItemsMap);//平铺方式
		sTemplateBoxWidth = getStyleItemValue("template_box_width",pageStyleItemsMap);//内容宽度
		sFontFamily = getStyleItemValue("font_family",pageStyleItemsMap); //字体
		sFontColor = getStyleItemValue("font_color",pageStyleItemsMap);//字体颜色
		sFontSize = getStyleItemValue("font_size",pageStyleItemsMap);//字体大小
		sAColor = getStyleItemValue("a_color",pageStyleItemsMap);//未访问的链接
		sAVisitedColor = getStyleItemValue("a_visited_color",pageStyleItemsMap);//已访问的链接
		sHoverColor = getStyleItemValue("a_hover_color",pageStyleItemsMap);//鼠标移到链接上
		sDecoration = getStyleItemValue("a_text_decoration",pageStyleItemsMap); //未访问链接下划线
		sVisitedDecoration = getStyleItemValue("a_visited_text_decoration",pageStyleItemsMap); //访问过的链接下划线
		sHoverDecoration = getStyleItemValue("a_hover_text_decoration",pageStyleItemsMap);//鼠标移动连接上下划线
	}
	processor.reset();
	parameters = new HashMap();
	parameters.put("ObjectId",String.valueOf(nPageStyleId));
	String sImageUploadPath = (String)processor.excute("wcm61_pagestyle","findStyleImageDir",parameters);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>页面风格基本信息</title>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- 公共js @ begin -->
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<!-- 公共js @ end -->
	<!--validator start-->
	<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<!--validator end-->
	<script type="text/javascript" src="./js/moreColorSelector.js"></script>
	<!-- 颜色选择器js @ END -->
	<script type="text/javascript" src="./style_basic_info_addedit.js"></script>
	<!-- 发送ajax相关的js @ end -->
	 <script language="javascript">
		 <!--
			// 将缩略图还原为“”的状态
		function resumeThumb(){
			top.window.Ext.Msg.confirm('您确定要清除背景图吗？', {
				yes : function(){
					$("body_background_image").value = "";
					$("body_background_image_iframe").src = "./image_for_style_upload.jsp?InputId=body_background_image&FileUrl=&ImageUploadPath=<%=CMyString.filterForJs(sImageUploadPath)%>";
				},
				no : function(){
					// doNothing
				}
			});
		}
		// 将设置的背景颜色清除
		function resumeColor(_sColorSelId){
			var eColorSel = $(_sColorSelId);
			var eOpt = eColorSel.options[ eColorSel.selectedIndex ];
			eOpt.style.backgroundColor = "transparent";
			eColorSel.style.backgroundColor = "transparent";
			eOpt.innerHTML = "无";
			eOpt.value = "transparent";
		}
		//判断是否为IE8浏览器，如果是重设padding-bottom值
		function setResumePadding(){
		var isIE8 = navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/8./i)=="8.";

			if(isIE8){
				document.getElementById("resumethumbBox").style.paddingBottom = 2;
			}
		}
	 //-->
	 </script>
	<style type="text/css">
		html, body{
			padding:0px;
			margin:0px;
			width:100%;
			height:100%;
			width:100%;
			overflow:hidden;
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
		.basic_info_nav{
			background:url(./images/top_bg.gif) repeat-x;
			height:28px;
			font-family:"宋体";
			font-size:12px;
			color:#333333;
		}
		.td_jiben_texing{
			font-family:"宋体";
			font-size:12px;
			color:#333333;
			font-weight:bold;
		}
		.font12{
			font-family:"宋体";
			font-size:12px;
		}
		.select_width{
			width:103px;
		}
		.line_middle{
			background:url(./images/line_repeat.jpg) repeat-x center;
		}
		.fieldset_title{
			font-family:"宋体";
			font-size:12px;
			color:#D4D4D4;
		}
		.sc {
			font-family: "宋体";
			font-size: 12px;
			color: #333333;
			text-decoration: none;
			line-height: 18px;
			font-weight: normal;
		}
		.sc1 {
			font-family: "宋体";
			font-size: 12px;
			color: #0063B8;
			text-decoration: none;
			line-height: 18px;
			font-weight: normal;
		}
		.footer{
			position:absolute;
			left:0px;
			bottom:0px;
			width:100%;
			height:56px;
			background: transparent url(../images/list/list-bg-bottom.gif) center bottom repeat-x;
		}
		 .leftarea{
			padding-left:50px;
			width:40%;
			height:100%;
			padding-top:10px;
		}
		.res-1024x768 .leftarea{
			padding-left:10px;
			width:55%;
		}
		.leftcontent{
			width:100%;
			height:100%;
		}
		.rightcontent{
			padding-left:50px;
			padding-right:100px;
			height:100%;
			padding-top:10px;
		}
		.res-1024x768 .rightcontent{
			padding:10px;
		}
		.resumethumb{
			padding-bottom:25px;
		}
 
		.divarea{
			width:100%;
			height:100%;
			overflow:auto;
		}
		.fontclass tr{
			padding-top:100px;
		}
		.fileuploadiframe{
			height:30px;
			width:180px;
		}
		.ext-gecko .fileuploadiframe{
			width:190px;
		}
		*html .spanHeight{
			_height:28px;
			_overflow:hidden;
		}
	</style>
</head>
<body onload="setResumePadding()">
	<form id="frmStyleData" name="frmStyleData" style="padding:0px;margin:0px;height:100%;">
	<input type="hidden" name="ObjectId" value="<%=nPageStyleId%>" ParamType="Style"/>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
		<tr>
			<td valign="top" height="85%" border="1px solid blue;">
				<table height="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px #e6e6e6 solid;background-color:#fefefe;" width="100%">
					<!-- 头 begin-->
					<!--
					<tr>
						<td width="100%" class="basic_info_nav" height="28px">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" height="28px">
								<tr>
									<td align="center" width="40px"><img src="./images/top_icon.gif" width="19px" height="28px" border="0"/></td>
									<td align="left" class="td_jiben_texing">基本特性</td>
								</tr>
							</table>
						</td>
					</tr>
					-->
					<!-- 头 end-->
					<!-- 主体 begin -->
					<tr>
						<td width="100%" height="100%" table-layout="fixed">
							<div class="divarea">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%" table-layout="fixed">
								<tr>
									<!-- 左边 begin -->
									<td class="leftarea" valign="top" height="100%" table-layout="fixed">
							 
										<table border="0" cellspacing="0" cellpadding="0" height="100%" width="100%" table-layout="fixed">
											<tr>
												<td width="100%" align="left" class="contenttd"><span style="padding-left:10px;font-size:12px;color:red;" WCMAnt:param="info_addedit.notice">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span></td>
											</tr>
											<!--页面背景-->
											<tr>
												<td width="100%" align="left" class="contenttd">
													<table border="0" cellspacing="0" cellpadding="0" class="font12" class="leftcontent">
														<tr>
															<td width="70px" align="center" WCMAnt:param="info_addedit.notice.bgset">背景设置</td>
															<td class="line_middle">&nbsp;</td>
														</tr>
														<tr>
															<td valign="top" align="center"><img src="./images/yemianbeijing.gif" width="64px" height="64px" border="0"></td>
															<td>
																<table border="0" cellspacing="0" cellpadding="1px" width="100%" class="font12">
																	<tr>
																		<td width="65px" WCMAnt:param="info_addedit.notice.bgcolor">背景颜色:</td>
																		<td>
																			<select name="body_background_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" class="select_width" style="background-color:<%=CMyString.isEmpty(sHoverColor) ? "#FFFFFF" : sBGColor%>">
																				<%
																					if("".equals(sBGColor)){
																						sBGColor = "#FFFFFF";
																					}
																					if("".equals(sBGColor)){
																				%>
																					<option value="" WCMAnt:param="info_addedit.notice.choice">请选择</option>
																				<%
																					}else{
																				%>
																					<option style="background-color:<%=sBGColor%>" value="<%=sBGColor%>" selected>&nbsp;</option>
																				<%
																					}
																				%>
																			</select>
																			<span style="vertical-align:middle;">
																				<span style="display:inline-block;cursor:pointer;height:30px;vertical-align:middle;" onclick="resumeColor('body_background_color')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
																			</span>
																		</td>
																	</tr>
																	<tr>
																		<td valign="top" class="font12" WCMAnt:param="info_addedit.notice.bgpic" style="padding-top:10px;">背景图片:</td>
																		<td valign="middle">
																			<input type="hidden" name="body_background_image" value="<%=sBGImage%>" ParamType="StyleItem" id="body_background_image">
																			<span style="vertical-align:middle;">
																				<IFRAME name="body_background_image" id="body_background_image_iframe" class="fileuploadiframe" frameborder="0" vspace="0" src="./image_for_style_upload.jsp?InputId=body_background_image&FileUrl=<%=CMyString.filterForHTMLValue(sBGImage)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" scrolling="NO" noresize></IFRAME>
																			</span>
																			<span id="resumethumbBox" class="resumethumb spanHeight" style="vertical-align:middle;display:inline-block;">
																				<span style="display:inline-block;cursor:pointer;height:30px;vertical-align:middle;" onclick="resumeThumb()"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
																			</span>
																		</td>
																	</tr>
																	<tr>
																		<td class="font12" WCMAnt:param="info_addedit.notice.tilemode">平铺方式:</td>
																		<td>
																			<input type="radio" name="body_background_repeat" ParamType="StyleItem" value="repeat-x" <%="repeat-x".equalsIgnoreCase(sBGRepeat)?"checked":""%>/><span class="font12" WCMAnt:param="info_addedit.notice.landscape">横向</span>
																			<input type="radio" name="body_background_repeat" ParamType="StyleItem" value="repeat-y" <%="repeat-y".equalsIgnoreCase(sBGRepeat)?"checked":""%>/><span class="font12" WCMAnt:param="info_addedit.notice.portrait">纵向</span>
																			<input type="radio" name="body_background_repeat" ParamType="StyleItem" value="repeat" <%="repeat".equalsIgnoreCase(sBGRepeat)?"checked":""%> <%="".equals(sBGRepeat)?"checked" : ""%>/><span class="font12" WCMAnt:param="info_addedit.notice.tile">平铺</span>
																			<input type="radio" name="body_background_repeat" ParamType="StyleItem" value="no-repeat" <%="no-repeat".equalsIgnoreCase(sBGRepeat)?"checked":""%>/><span class="font12">不平铺</span>
																		</td>
																	</tr>
																	<tr>
																		<td class="set_title" style="width:90px">水平方向位置：</td>
																		<%
																			String body_background_pos_x = getStyleItemValue("body_background_pos_x",pageStyleItemsMap);
																			body_background_pos_x = CMyString.showNull(body_background_pos_x,"left");
																		%>
																		<td class="set_value">
																			<label for="body_background_pos_x_left"><input type="radio" class="radio" <%=body_background_pos_x.equals("left")?"checked ":""%> ParamType="StyleItem" id="body_background_pos_x_left" name="body_background_pos_x" value="left">左边</label>
																			<label for="body_background_pos_x_center"><input type="radio" class="radio" <%=body_background_pos_x.equals("center")?"checked ":""%> ParamType="StyleItem" id="body_background_pos_x_center" name="body_background_pos_x" value="center">中间</label>
																			<label for="body_background_pos_x_right"><input type="radio" class="radio" <%=body_background_pos_x.equals("right")?"checked ":""%> ParamType="StyleItem" id="body_background_pos_x_right" name="body_background_pos_x" value="right">右边</label>
																		</td>
																	</tr>
																	<tr>
																		<td class="set_title" style="width:90px">垂直方向位置：</td>
																		<%
																			String body_background_pos_y = getStyleItemValue("body_background_pos_y",pageStyleItemsMap);
																			body_background_pos_y = CMyString.showNull(body_background_pos_y,"top");
																		%>
																		<td class="set_value">
																			<label for="body_background_pos_y_top"><input type="radio" class="radio" <%=body_background_pos_y.equals("top")?"checked ":""%> ParamType="StyleItem" id="body_background_pos_y_top" name="body_background_pos_y" value="top">顶端</label>
																			<label for="body_background_pos_y_center"><input type="radio" class="radio" <%=body_background_pos_y.equals("center")?"checked ":""%> ParamType="StyleItem" id="body_background_pos_y_center" name="body_background_pos_y" value="center">中间</label>
																			<label for="body_background_pos_y_bottom"><input type="radio" class="radio" <%=body_background_pos_y.equals("bottom")?"checked ":""%> ParamType="StyleItem" id="body_background_pos_y_bottom" name="body_background_pos_y" value="bottom">底部</label>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!--内容区-->
											<tr>
												<td width="100%" align="left" class="contenttd">
													<table border="0" cellspacing="0" cellpadding="0" class="font12" class="leftcontent">
														<tr>
															<td width="70px" align="center" WCMAnt:param="info_addedit.notice.pageset">页面设置</td>
															<td class="line_middle">&nbsp;</td>
														</tr>
														<tr>
															<td valign="top" align="center">&nbsp;</td>
															<td>
																<table border="0" cellspacing="0" cellpadding="1px" width="100%" class="font12">
																	<tr>
																		<td width="65px" WCMAnt:param="info_addedit.notice.pagewidth">页面宽度:</td>
																		<td>
																			<input type="text" name="template_box_width" ParamType="StyleItem" value="<%=CMyString.isEmpty(sTemplateBoxWidth) ? "950px" : sTemplateBoxWidth%>" validation="desc:'内容宽度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'内容宽度格式必须为:整数+单位(pt/em/px)'"/>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!--默认字体-->
											<tr>
												<td width="100%" align="left" class="contenttd">
													<table border="0" cellspacing="0" cellpadding="0" class="font12" class="leftcontent">
														<tr>
															<td width="70px" align="center" WCMAnt:param="info_addedit.notice.fontset">字体设置</td>
															<td class="line_middle">&nbsp;</td>
														</tr>
														<tr>
															<td valign="top" align="center"><img src="./images/morenziti.gif" width="64px" height="64px" border="0"></td>
															<td>
																<table border="0" cellspacing="0" cellpadding="1px" width="100%" class="font12">
																	<tr>
																		<td width="65px" WCMAnt:param="info_addedit.notice.fontstyle">字体类型:</td>
																		<td>
																			<select name="font_family" ParamType="StyleItem" class="select_width">
																				<%=drawFontFamilySelected(sFontFamily)%>
																			</select>
																		</td>
																	</tr>
																	<tr>
																		<td class="font12" WCMAnt:param="info_addedit.notice.fontcolor">字体颜色:</td>
																		<td>
																			<select name="font_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" class="select_width" style="background-color:<%=CMyString.isEmpty(sFontColor) ? "#000000" : sFontColor%>">
																				<%
																					if("".equals(sFontColor)){
																						sFontColor = "#000000";
																					}
																					if("".equals(sFontColor)){
																				%>
																					<option value="" WCMAnt:param="info_addedit.notice.choice">请选择</option>
																				<%
																					}else{
																				%>
																					<option style="background-color:<%=sFontColor%>" value="<%=sFontColor%>" selected>&nbsp;</option>
																				<%
																					}
																				%>
																			</select>
																		</td>
																	</tr>
																	<tr>
																		<td class="font12" WCMAnt:param="info_addedit.notice.fontsize">字体大小:</td>
																		<td>
																			<input type="text" name="font_size" ParamType="StyleItem" class="select_width" value="<%=CMyString.isEmpty(sFontSize) ? "14px" : sFontSize%>" validation="desc:'字体大小输入内容',required:false,max_len:5,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'字体大小格式必须为:整数+单位(pt/em/px)'" />
																		</td>
																	</tr>
																	<tr>
																		<td width="100px" WCMAnt:param="info_addedit.notice.noactive.link">未访问的链接:</td>
																		<td>
																			<span class="font12" WCMAnt:param="info_addedit.notice.color">颜色:</span>
																			<select name="a_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" class="select_width" style="background-color:<%=CMyString.isEmpty(sAColor) ? "#000000" : sAColor%>">
																				<%
																					if("".equals(sAColor)){
																						sAColor = "#000000";
																					}
																					if("".equals(sAColor)){
																				%>
																					<option value="" WCMAnt:param="info_addedit.notice.choice">请选择</option>
																				<%
																					}else{
																				%>
																					<option style="background-color:<%=sAColor%>" value="<%=sAColor%>" selected>&nbsp;</option>
																				<%
																					}
																				%>
																				</select>
																				<span style="padding-left:10px;">
																					<input name="a_text_decoration" ParamType="StyleItem" type="checkbox" value="underline" <%="underline".equalsIgnoreCase(sDecoration)?"checked":""%>/> <span WCMAnt:param="info_addedit.notice.underline">下划线</span>
																				</span>
																		</td>
																	</tr>
																	<tr>
																		<td width="100px" WCMAnt:param="info_addedit.notice.visted.link">已访问的链接:</td>
																		<td>
																			<span class="font12" WCMAnt:param="info_addedit.notice.link">颜色:</span>
																			<select name="a_visited_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" class="select_width" style="background-color:<%=CMyString.isEmpty(sAVisitedColor) ? "#000000" : sAVisitedColor%>">
																				<%
																					if("".equals(sAVisitedColor)){
																						sAVisitedColor = "#000000";
																					}
																					if("".equals(sAVisitedColor)){
																				%>
																					<option value="" WCMAnt:param="info_addedit.notice.choice">请选择</option>
																				<%
																					}else{
																				%>
																					<option style="background-color:<%=sAVisitedColor%>" value="<%=sAVisitedColor%>" selected>&nbsp;</option>
																				<%
																					}
																				%>
																			</select>
																			<span style="padding-left:10px;">
																				 <input  name="a_visited_text_decoration" ParamType="StyleItem" type="checkbox" value="underline" <%="underline".equalsIgnoreCase(sVisitedDecoration)?"checked":""%>/> <span WCMAnt:param="info_addedit.notice.underline">下划线</span>
																			</span>
																		</td>
																		 
																	</tr>
																	<tr>
																		<td width="100px" WCMAnt:param="info_addedit.notice.mousemove">鼠标移到链接上:</td>
																		<td>
																			<span class="font12" WCMAnt:param="info_addedit.notice.color">颜色:</span>
																			<select name="a_hover_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" class="select_width" style="background-color:<%=CMyString.isEmpty(sHoverColor) ? "#000000" : sHoverColor%>">
																				<%
																					if("".equals(sHoverColor)){
																						sHoverColor = "#000000";
																					}
																					if("".equals(sHoverColor)){
																				%>
																					<option value="" WCMAnt:param="info_addedit.notice.choice">请选择</option>
																				<%
																					}else{
																				%>
																					<option style="background-color:<%=sHoverColor%>" value="<%=sHoverColor%>" selected>&nbsp;</option>
																				<%
																					}
																				%>
																			</select>
																			<span style="padding-left:10px;">
																				<input name="a_hover_text_decoration" ParamType="StyleItem" type="checkbox" value="underline" <%="underline".equalsIgnoreCase(sHoverDecoration)?"checked":""%>/> <span WCMAnt:param="info_addedit.notice.underline">下划线</span>
																			</span>
																		</td>
																		 
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>

										</table>
									</td>
									<!-- 左边 end -->
									<!-- 右边 begin -->
									<td align="left" class="fieldset_title rightcontent" >
										 <fieldset style="padding:0px;height:80%;">
											<legend WCMAnt:param="info_addedit.notice.preview">预览:</legend>
											<iframe src="" width="100%" height="100%" id="iframe_style_basic_preview" name="iframe_style_basic_preview" frameborder="0" scrolling ="auto"></iframe>
										  </fieldset>
										<!--img  src="./images/no_picture.jpg" width="242" height="313px" /-->
									</td>
									<!-- 右边 end -->
								</tr>
							</table>
							</div>
						</td>
					</tr>
					<tr>
						<td align="center" height="40px;" style="padding-top:15px;">
							<img src="./images/yulan.gif" onclick="previewBasicInfo()" width="85px" height="35px" border="0" style="cursor:pointer;margin-right:20px;">
							<img src="./images/baocun.gif" onclick="saveBasicInfo(<%=nPageStyleId%>)" width="85px" height="35px" border="0" style="cursor:hand">
						</td>
					</tr>
					<!-- 主体 end -->
				</table>
			</td>
		</tr>
		<tr>
			<td height="56px">
				<div class="footer"></div>
			</td>
		</tr>
	</table>
	</form>
<!-- 颜色选择器 @ BEGIN -->
	<!-- 颜色选择器的mask容器，用于ie6下遮挡select @ BEGIN -->
		<iframe src="about:blank" id="iframe_colorselector_mask"  style="display:none;position:absolute;filter:alpha(opacity=0);border:0px;"></iframe>
	<!-- 颜色选择器的mask容器，用于ie6下遮挡select @ END -->
	<!-- 颜色选择容器 @ begin -->
		<div id="div_color" style="display:none;position:absolute;border:solid 1px #999999;background-color:#f9f8f7;font-size:12px;" WCMAnt:param="info_addedit.notice.colorContainer">颜色选择器容器</div>
	<!-- 颜色选择容器 @ END -->
	<!-- 更多颜色的选择容器 @ BEGIN -->
		<div id="div_MoreColor" style="display:none;position:absolute;border:solid 1px #999999;background-color:#f9f8f7;font-size:12px;" onblur="if( !Position.within(this, Event.pointerX(event), Event.pointerY(event)) ){colorSelector.hideMoreColorSelector();}" WCMAnt:param="info_addedit.notice.more.colorContainer">更多颜色选择器容器</div>
	<!-- 更多颜色的选择容器 @ end -->
<!-- 颜色选择器 @ END -->
</body>
</html>
<%!
	private String getStyleItemValue(String _sName,HashMap _pageStyleItemsMap){
		if(_sName==null || _pageStyleItemsMap==null){
			return "";
		}
		
		StyleItem aStyleItem = (StyleItem)_pageStyleItemsMap.get(_sName);
		if(aStyleItem==null){
			return "";
		}
		return CMyString.showNull(aStyleItem.getClassValue(),"");
	}
	//构造字体类型
	String fontFamily[] = {"宋体", "新宋体", "楷体", "仿宋", "黑体", "隶书", "微软雅黑", "Arial", "Comic Sans MS","Courier New", "Tahoma", "Times New Roman", "Verdana"};

	private String drawFontFamilySelected(String _fontFamily) throws WCMException {
		StringBuffer sFontFamilyHtmlBuffer = new StringBuffer();
		for (int i=0; i<fontFamily.length; i++) {
			String sIsSelected=fontFamily[i].equalsIgnoreCase(_fontFamily)?"selected":"";
			sFontFamilyHtmlBuffer.append("<option value='"+fontFamily[i]+"' "+sIsSelected+">"+fontFamily[i]+"</option> ");
		}
		return sFontFamilyHtmlBuffer.toString();
	}
%>