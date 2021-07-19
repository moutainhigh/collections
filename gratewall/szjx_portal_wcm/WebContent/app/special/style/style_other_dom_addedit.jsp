<%
/** Title:				style_other_dom_addedit.jsp
 *  Description:
 *        提供 页面其他元素的风格在线编辑可视化操作 的入口。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年6月3日
 *  Vesion:				1.0
 *  Last EditTime:	none
 *  Update Logs:	none
 *  Parameters:		see style_other_dom_addedit.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS @ BEGIN -->
<%@ page  import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page  import = "java.util.HashMap" %>
<%@ page  import="com.trs.infra.util.CMyString" %>
<%@ page  import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page  import="com.trs.components.common.publish.widget.StyleItem" %>
<%@ page  import="com.trs.components.common.publish.widget.StyleCenterMgr" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>

<!-- WCM IMPORTS @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	// 1、获取参数
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",1);

	// 2、获取当前的 页面风格下 的页面其他元素的样式定义集合
	HashMap hmOtherDomStyleItems = null;// 页面风格项的属性
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap parameters = new HashMap();
	if(nPageStyleId>0){
		//获取页面风格的属性
		PageStyle currPageStyle = PageStyle.findById(nPageStyleId);
		if(currPageStyle==null){
			throw new WCMException(CMyString.format(LocaleServer.getString("style_other_dom_addedit.id.zero","获取页面风格[Id{0}]失败！"),new int[]{nPageStyleId}));
		}
		//权限判断
		boolean bHasRight = SpecialAuthServer.hasRight(loginUser, currPageStyle, SpecialAuthServer.STYLE_EDIT);
		if (!bHasRight) {
			throw new WCMException(CMyString.format(LocaleServer.getString("style_other_dom_addedit.noRight","您没有权限修改风格【{0}】！"),new String[]{currPageStyle.getStyleDesc()}));
		}

		parameters = new HashMap();
		parameters.put("ObjectId",String.valueOf(nPageStyleId));
		parameters.put("ObjectType",String.valueOf(StyleCenterMgr.PAGE_OTHERSTYLE_WCMTYPE));
		hmOtherDomStyleItems = ( HashMap ) processor.excute("wcm61_styleitem","queryStyleItemMap",parameters);
	}

	// 3、获取图片的上传路径
	processor.reset();
	parameters = new HashMap();
	parameters.put("ObjectId",String.valueOf(nPageStyleId));
	String sImageUploadPath = (String)processor.excute("wcm61_pagestyle","findStyleImageDir",parameters);
	sImageUploadPath = CMyString.filterForJs(sImageUploadPath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>页面其他元素</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
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
	<script language="javascript">
		//保存
		function save(){
			// 整合数据
			var eFrom = $("frm");
			if(!ValidationHelper.doValid('frm')){
				return;
			}
			var sPostStr = {
				ObjectId : $('ObjectId').value || 0,
				StyleItemXML : top.window.getStyleXML(eFrom)
			}
			top.window.BasicDataHelper.call("wcm61_pagestyle", "savePageOtherStyle", sPostStr, 'true', function(){
				document.location.reload();
			});
		}
		// 图片上传组件
		function dealWithUploadedImageFile(_sSaveFilePath, _sInputId){
			if(_sSaveFilePath.length<0){
				top.window.Ext.Msg.alert('上传文件失败');
			}
			if(!_sSaveFilePath&&_sSaveFilePath==''){
				return;
			}
			var eInput = document.getElementById(_sInputId);
			eInput.value = _sSaveFilePath;
		}
		// 将设置的背景图片清除
		function resumeBgImg(_sImgSetDomId){
			top.window.Ext.Msg.confirm('您确定要清除图片吗？', {
				yes : function(){
					$(_sImgSetDomId).value = "";
					var sIframeSrc = $(_sImgSetDomId+"_iframe").src;
					var sNewIframSrc = sIframeSrc.substring(0,sIframeSrc.indexOf("?"));
					var lParamList = sIframeSrc.substring(sIframeSrc.indexOf("?")).split("&");
					for (var i=0; i<lParamList.length; i++) {
						var lParamItemList = lParamList[i].split("=");
						if(lParamItemList[0]!="FileUrl"){
							sNewIframSrc += lParamList[i] + "&";
						}else{
							sNewIframSrc += "FileUrl=&";
						}
					}
					$(_sImgSetDomId+"_iframe").src = sNewIframSrc;
				}
			});
		}
	</script>
	<style type="text/css">
		html, body{
			height:100%;
			width:100%;
			overflow:hidden;
			padding:0px;
			margin:0px;
		}
		body,ul,li,table,td,th,form,fieldset,img,select,input{margin:0;padding:0;}
		body,table{
			font:normal normal normal 12px/150% "宋体";
			scrollbar-face-color: #f6f6f6; 
			scrollbar-highlight-color: #ffffff; 
			scrollbar-shadow-color: #cccccc; 
			scrollbar-3dlight-color: #cccccc; 
			scrollbar-arrow-color: #330000; 
			scrollbar-track-color: #f6f6f6; 
			scrollbar-darkshadow-color: #ffffff;
		}
		input,select{vertical-align:middle;width:120px;}
		iframe{vertical-align:middle;}
		ul,ol{list-style:none;}
		ul,li{clear:both;}
		/*边框样式选择器*/
		.border_style_opt_blur{
			height:20px;
			padding:1px 1px 1px 1px;
		}
		.border_style_opt_focus{
			border:1px dotted grey;
			height:20px;
		}
		.border_style_opt{
			width:100%;
			vertical-align:middle;
			line-height:1px;
			float:left;
			padding:7px 0 0;
		}
		/*可输入下拉列表框*/
		.inputSelect_input{
			height:21px;
			font-size:10pt;
			height:19px;
			width:116px;
		}
		.inputSelect_span{
			position:absolute;
			width:19px;
			height:19px;
			margin-top:4px;
			margin-left:-22px;
			text-align:left;
			overflow:hidden;
			padding:0px;
		}
		.inputSelect_select{
			position:absolute;
			clip:rect(2px 127px 18px 1px);
			margin-left:-97px;
			margin-top:0px;
			width:118px;
			height:21px;
			font-family: "宋体";
			font-weight: normal;
			font-size:14px;
			height:21px;
			color: #333333;
		}
		.radio{
			width:20px !important;
		}
		.fieldset_box{margin:10px 0 0;width:400px;}
		.fieldset_box_head{background: url(./images/filedset_head_r.gif) no-repeat 100% -30px;}
		.fieldset_box_head_left{background:url(./images/filedset_head_l.gif) no-repeat 0 -30px;background-color:#ffffff;width:80px;text-align:center;padding-left:6px;}
		.fieldset_box_body{border-left:1px solid #d0d1d5;border-right:1px solid #d0d1d5;padding:5px 10px;}
		.fieldset_box_bottom{background:url(./images/filedset_bottom_l.gif) no-repeat 0 0;height:4px;font-size:0px;line-height:4px;}
		.fieldset_box_bottom_left{background:url(./images/filedset_bottom_r.gif) no-repeat 100% 0;height:4px;font-size:0px;line-height:4px;}
		.fieldset_box_content{width:100%;}
		.set_title{text-align:right;width:120px;position:relative;height:30px;}
		.set_value{padding-left:10px;position:relative;height:30px;}
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
		.fieldset_title{
			font-family:"宋体";
			font-size:12px;
			color:#D4D4D4;
			padding-right:200px;
			width:500px;
		}
		fieldset{
			padding : 5px;
			width : 460px;
			height : 313px;
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
		}
		.leftareadiv{
			height:330px;
			width:480px;
			overflow:auto;
			padding:0 0 0 40px;
			margin:20px 0 5px 0;
		}
		.res-1024x768 fieldset{
			width:300px;
		}
		.res-1024x768 .fieldset_title{
			width:300px;
			padding-left:20px;
		}
		.res-1024x768 .leftarea{
			padding-left:10px;
		}
		.ext-gecko .fieldset_title{
			width:100px;
		}
		.ext-gecko fieldset{
			width:100px;
		}
		.ext-gecko .leftareadiv{
			width:450px;
		}
		.fileuploadiframe{
			height:30px;
			width:180px;
			float:left;
		}
		.ext-gecko .fileuploadiframe{
			width:190px;
		}
 
	</style>
</head>
<body>
<form id="frm">
	<input type="hidden" name="ObjectId" value="<%=nPageStyleId%>" ParamType="Style"/>
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td valign="top">
				<table border="0" cellspacing="0" cellpadding="0" style="border:1px #e6e6e6 solid;background-color:#fefefe;" width="100%">
					<!-- 头 begin-->
					<!--
					<tr>
						<td width="100%" class="basic_info_nav">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" height="28px">
								<tr>
									<td align="center" width="40px"><img src="./images/top_icon.gif" width="19px" height="28px" border="0"/></td>
									<td align="left" class="td_jiben_texing">页面其他元素</td>
								</tr>
							</table>
						</td>
					</tr>
					-->
					<!-- 头 end-->
					<!-- 主体 begin -->
					<tr>
						<td width="100%">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
								<tr>
									<!-- 左边 begin -->
									<td  width="100%" valign="top" height="100%" class="leftarea">
										<div style="padding-top:10px;padding-left:50px;">
											<span style="font-size:12px;color:red;" WCMAnt:param="style_other_dom_addedit.caution">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
										</div>
										<div class="leftareadiv">
											<%@include file="./style_other_dom_addedit_1.jsp"%>
										</div>
									</td>
									<!-- 左边 end -->
									<!-- 右边 begin -->
									<td align="left" class="fieldset_title" style="">
										 <fieldset class="fieldset_title" >
											<legend WCMAnt:param="style_other_dom_addedit.preview">预览:</legend>
											<span WCMAnt:param="style_other_dom_addedit.notSupport">暂不提供</span>
											<!--
											<iframe src="../../portalview/styledesign/previewpage/style_other_dom_preview_page.jsp?PageStyleId=<%=nPageStyleId%>" width="242px" height="313px" id="iframe_style_basic_preview" frameborder="0" scrolling ="auto"></iframe>-->
										  </fieldset>
									</td>
									<!-- 右边 end -->
								</tr>
							</table>
						</td>
					</tr>
					<!-- 主体 end -->
				</table>
			</td>
		</tr>
		<tr>
			<td align="center" style="padding-top:10px;">
				<img src="./images/baocun.gif" onclick="save()" width="85px" height="35px" border="0" style="cursor:hand">
		</tr>
		<tr>
			<td>
				<div class="footer"></div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
<%!
	// 获取样式值
	private String getStyleItemValue(HashMap _pageStyleItemsMap,String _sName){
		if(_sName==null || _pageStyleItemsMap==null){
			return "";
		}
		
		StyleItem aStyleItem = (StyleItem)_pageStyleItemsMap.get(_sName);
		if(aStyleItem==null){
			return null;
		}
		return aStyleItem.getClassValue();
	}
%>