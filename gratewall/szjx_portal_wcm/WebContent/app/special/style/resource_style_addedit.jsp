<%
/** Title:				resource_style_addedit.jsp
 *  Description:
 *        资源框架样式的的编辑/新建页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			2010年5月5日
 *  Vesion:				1.0
 *  Last EditTime:	none
 *  Update Logs:	none
 *  Parameters:		see resource_style_addedit.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import = "com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page import="com.trs.components.common.publish.widget.StyleItem" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer" %>
<!-- WCM IMPORTS  @ END -->
<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.addHeader("Cache-Control","no-store"); //Firefox
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1);
	response.setDateHeader("max-age", 0);
%>
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	// 1、获取参数
	int nResourceStyleId = currRequestHelper.getInt("ResourceStyleId",0);
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	
	// 2、判断当前是编辑还是新建
	boolean bIsEdit = false;
	if(nResourceStyleId>0){
		bIsEdit = true;
	}

	// 3、获取对象
	ResourceStyle oResourceStyle = null;
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap parameters = new HashMap();
	HashMap hmResStyleItemMap = new HashMap();
	boolean bHasRight = false;
	if(bIsEdit){// 编辑
		// 获取 应用模式（Id:nAppModeId） 对象
		oResourceStyle = ResourceStyle.findById(nResourceStyleId);
		if(oResourceStyle == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("resource_style_addedit.jsp.fail2get_widget_type", "获取资源类型[Id:{0}]失败"), new int[]{nResourceStyleId}));
		}
		if(!oResourceStyle.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, CMyString.format(LocaleServer.getString("layout_addedit.jsp.locked", "当前对象被[{0}]锁定，您不能修改!"),  new Object[]{oResourceStyle.getLockerUser()}));
		}
		//判断修改的权限
		bHasRight = SpecialAuthServer.hasRight(loginUser, oResourceStyle, SpecialAuthServer.STYLE_EDIT);
		if (!bHasRight) {
			throw new WCMException(CMyString.format(LocaleServer.getString("resource_style_addedit.jsp.have_noright2_alter_style", "您没有权限修改风格【{0}】！"), new String[]{oResourceStyle.getStyleDesc()}));
		}
		parameters.put("ObjectId",String.valueOf(nResourceStyleId));
		parameters.put("ObjectType",String.valueOf(ResourceStyle.OBJ_TYPE));
		hmResStyleItemMap = (HashMap)processor.excute("wcm61_styleitem","queryStyleItemMap",parameters);
	}else{// 新建 创建新的对象
		oResourceStyle = new ResourceStyle();
		bHasRight = SpecialAuthServer.hasRight(loginUser, oResourceStyle, SpecialAuthServer.STYLE_ADD);
		if (!bHasRight) {
			throw new WCMException(LocaleServer.getString("resource_style_addedit.jsp.have_noright2_new_style","您没有权限新建风格！"));
		}
	}
	// 4、获取  相关信息
	
	String sResourceStyleName = CMyString.showNull(oResourceStyle.getStyleName(),"");
	String sCssFlag = CMyString.showNull(oResourceStyle.getCssFlag(),"");
	String sTemFileName = CMyString.showNull(oResourceStyle.getTemplate(),"resource_template_1.css");

	processor.reset();
	parameters = new HashMap();
	parameters.put("ObjectId",String.valueOf(nPageStyleId));
	String sImageUploadPath = (String)processor.excute("wcm61_pagestyle","findStyleImageDir",parameters);

	// 5、判断 中文名、英文名 是否允许修改
	boolean bCanEditNameAndFlag = true;
	if(bIsEdit){
		int nIsPrivate = oResourceStyle.getIsPrivate();
		bCanEditNameAndFlag = nIsPrivate==1?true:false;
	}

	out.clear();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><%=bIsEdit?(LocaleServer.getString("resource_style_addedit.jsp.edit_widget_style","编辑资源框架样式")):(LocaleServer.getString("resource_style_addedit.jsp.create_widget_style","新建资源框架样式"))%> </title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="./resource_style_addedit.css"/>
	<link href="../../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<!-- 公共js @ begin -->
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../../js/easyversion/elementmore.js"></script>
	<script src="../../js/easyversion/ajax.js"></script>
	<script src="../../js/easyversion/lockutil.js"></script>
	<script src="../js/adapter4Top.js"></script>
	<!-- 公共js @ end -->
	<!--validator start-->
	<script src="../../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<!--validator end-->
	<script language="javascript" src="./formDataForRequest.js" type="text/javascript"></script>
	<script type="text/javascript" src="./resource_style_addedit.js"></script>
	<!-- color selector js @ BEGIN -->
	<script type="text/javascript" src="./js/moreColorSelector.js"></script>
	<!-- color selector js @ END -->
	<!-- line selector js @ BEGIN -->
	<script type="text/javascript" src="./js/borderStyleSelector.js"></script>
	<!-- line selector js @ END -->
	<script type="text/javascript">
	<!--
		// 当页面加载完之后，初始化模板以及校验组件
		Event.observe(window, 'load', function (){
			changeTemplate("<%=sTemFileName%>",<%=nPageStyleId%>,<%=nResourceStyleId%>)
		});
		function init(){
			ValidationHelper.addValidListener(function(){
				//按钮有效处理
				wcmXCom.get('btnSave').enable();
			}, "frm");
			ValidationHelper.addInvalidListener(function(){
				//按钮失效处理
				wcmXCom.get('btnSave').disable();
			}, "frm");
			ValidationHelper.initValidation();
		}
		function save(){
			// 数据校验
			if(!ValidationHelper.doValid("frm")){
				ValidationHelper.failureRPCCallBack();
				return false;
			}
			// 整合数据
			var eFrom = $("frm");
			var oXML = getStyleXML(eFrom);
			var styleJson = getPostStyleJson(eFrom);

			// 判断标识是否可用，判断名称是否可用
			var eStyleNameInput = eFrom.StyleName;
			var eCssFlagInput = eFrom.CssFlag;
			if(eCssFlagInput.getAttribute("ValueCanUsed") == "true" && eStyleNameInput.getAttribute("ValueCanUsed") == "true"){
				doSave(oXML, styleJson);
			}else{
				var sCssFlag = eCssFlagInput.value;
				var cssFlagSuccessFun = function (_transport, _json){
					var size = _transport.responseText.trim();
					if(size > 0){
						eCssFlagInput.setAttribute("ValueCanUsed",false);
						ValidationHelper.failureRPCCallBack("风格标识已经被使用");
						top.window.wcm.CrashBoarder.get('resource-cssflag-used-info').show({
							title : "风格标识已经被使用",
							src : root_path + "cssflag_used_info.jsp?CssFlag=" + sCssFlag 
								+ "&StyleType=" + <%=ResourceStyle.OBJ_TYPE%>
								+ "&PageStyleId=" + <%=nPageStyleId%>
								+ "&CurrStyleId=" + <%=nResourceStyleId%>,
							width:'600',
							height:'300'
						});
						return false;
					}else{
						eCssFlagInput.setAttribute("ValueCanUsed", true);
						var sStyleName = eStyleNameInput.value;
						var nameSuccessFun = function (_transport, _json){
							 var nameUsedsize = _transport.responseText.trim();
							 if(nameUsedsize > 0){
								eStyleNameInput.setAttribute("ValueCanUsed",false);
								ValidationHelper.failureRPCCallBack("风格名称已经被使用");
								// 报告名称重复的信息
								top.window.wcm.CrashBoarder.get('resource-stylename-used-info').show({
									title : "风格名称已经被使用",
									src : root_path + "stylename_used_info.jsp?StyleName=" + encodeURIComponent(sStyleName) 
									+ "&StyleType=" + <%=ResourceStyle.OBJ_TYPE%>
									+ "&PageStyleId=" + <%=nPageStyleId%>
									+ "&CurrStyleId=" + <%=nResourceStyleId%>,
									width:'600',
									height:'300'
								});
								return false;
							 }else{
								eStyleNameInput.setAttribute("ValueCanUsed",true);
								doSave(oXML, styleJson);
							 }
						};
						StyleNameUsedCheck(eStyleNameInput,<%=ResourceStyle.OBJ_TYPE%>,<%=nPageStyleId%>,<%=nResourceStyleId%>,null, nameSuccessFun);
					}
				};
				CssFlagUsedCheck(eCssFlagInput,<%=ResourceStyle.OBJ_TYPE%>,<%=nPageStyleId%>,<%=nResourceStyleId%>,$('StyleName_mgr'), cssFlagSuccessFun);
			}
		}
		// 更换模板
		function changeTemplate(_sTemFileName,_nPageStyleId,_nResourceStyleId){
			// 发送 保存 请求
			var sPostStr = "PageStyleId="+_nPageStyleId+"&ResourceStyleId="+_nResourceStyleId+"&TemFileName="+encodeURIComponent(_sTemFileName);
			new top.window.ajaxRequest({
				url : WCMConstants.WCM6_PATH + 'special/style/resource_style_addedit_for_ajax.jsp',
				method : 'get',
				parameters : sPostStr,
				onSuccess : function(_transport){
					var oRelXml = _transport.responseText;
					document.getElementById("body").innerHTML = oRelXml;
				}
			});
		}

		Event.observe(window, 'unload', function(){
			var nResourceStyleId= <%=nResourceStyleId%>;
			var nObjType = <%=oResourceStyle.getWCMType()%>;
			if(nResourceStyleId != 0){
				LockerUtil.unlock(nResourceStyleId, nObjType);
			}
		});
	//-->
	</script>
</head>
<body>
<form id="frm">
	<input type="hidden" name="ObjectId" id="ObjectId" value="<%=nResourceStyleId%>" ParamType="Style">
	<input type="hidden" name="PageStyleId" value="<%=nPageStyleId%>" ParamType="Style">
	<input type="hidden" name="StyleDesc" id="StyleDesc" value="<%=sResourceStyleName%>" ParamType="Style" >
	<div class="style_set_container">
		<div class="head">
			<div class="css_name">
				<div style="float:left;padding-right:0px;" WCMAnt:param="resource_style_addedit.jsp.display_name_a">显示名称：</div>
				<div style="float:left">
					<input type="text" class="inputtext" name="StyleName" id="StyleName" ParamType="Style" value="<%=sResourceStyleName%>" validation_desc="显示名称"  WCMAnt:paramattr="validation_desc:resource_style_addedit.jsp.display_name" validation="desc:'显示名称',type:'string',required:true,max_len:50,showid:'StyleName_mgr'" onkeyup="$('StyleDesc').value=this.value" onblur="StyleNameUsedCheck(this,<%=ResourceStyle.OBJ_TYPE%>,<%=nPageStyleId%>,<%=nResourceStyleId%>,$('StyleName_mgr'),blurNameSucFunc);" ValueCanUsed="false" ><br>
					<span id="StyleName_mgr" class="warningClassKey"></span>
				</div>
			</div>
			<div class="css_id" style="position:relative;">
				<div style="float:left;padding-right:0px;" WCMAnt:param="resource_style_addedit.jsp.styleId">风格标识：</div>
				<div style="float:left">
				<!-- validation="desc:'标识',required:true,format:'/^[A-Za-z0-9]+$/',showid:'CssFlag_mgr'"  -->
					<input type="text" class="inputtext" name="CssFlag" id="CssFlag" ParamType="Style" value="<%=sCssFlag%>" validation_desc="标识"  WCMAnt:paramattr="validation_desc:resource_style_addedit.jsp.id"  validation="desc:'标识',required:true,type:'COMMON_CHAR2',max_len:50,showid:'CssFlag_mgr'" onblur="CssFlagUsedCheck(this,<%=ResourceStyle.OBJ_TYPE%>,<%=nPageStyleId%>,<%=nResourceStyleId%>,$('CssFlag_mgr'), blurCssFlagSucFunc);" ValueCanUsed="false" <%=bIsEdit?"disabled":""%>><br>
					<span id="CssFlag_mgr" class="warningClassKey" style="position:absolute;left:0px;"></span>
				</div>
			</div>
			<div class="css_type">
				<span WCMAnt:param="resource_style_addedit.jsp.sel_style_master">选择风格模板：</span>
				<span >
					<select name="Template" ParamType="Style" style="width:160px;" onchange="changeTemplate(this.value,<%=nPageStyleId%>,<%=nResourceStyleId%>)">
						<option value="resource_template_1.css" title="头部图片背景有边框" <%="resource_template_1.css".equals(sTemFileName)?"selected":""%> WCMAnt:param="resource_style_addedit.jsp.head_bgPic_have_border">头部图片背景有边框</option>
						<option value="resource_template_2.css" title="头部图片背景无边框" <%="resource_template_2.css".equals(sTemFileName)?"selected":""%> WCMAnt:param="resource_style_addedit.jsp.head_bgPic_haveno_border">头部图片背景无边框</option>
						<option value="resource_template_3.css" title="头部尾部边框都是图片" <%="resource_template_3.css".equals(sTemFileName)?"selected":""%> WCMAnt:param="resource_style_addedit.jsp.head_and_bottom_bgPic_have_border">头部尾部边框都是图片</option>
						<option value="resource_template_4.css" title="头部尾部边框都是图片" <%="resource_template_4.css".equals(sTemFileName)?"selected":""%> WCMAnt:param="resource_style_addedit.jsp.content_border_haveno_head_and_bottom">内容边框无头部和尾部</option>
					</select>
				</span>
				
			</div>
			<!--
			<%
				if(!bIsEdit&&nPageStyleId>0){
			%>
			<div style="width:300px;position:absolute;left:10px;top:45px;">
				<label for="CopyToSystemResourceStyle" style="">
					<input type="checkbox" name="CopyToSystemResourceStyle" style="width:auto;" id="CopyToSystemResourceStyle" ParamType="Style" value="1" checked>
					在系统级别的资源可用风格中创建此风格
				</label>
			</div>
			<%
				}
			%>
			-->
		</div>
		<div class="body" id="body">
			<%@include file="./resource_style_addedit_1.jsp"%>
		</div>
	</div>
</form>
	<!-- 边框样式选择器 @ BEGIN -->
		<iframe src="about:blank" id="iframe_BorderStyleSelector_mask" style="display:none;position:absolute;filter:alpha(opacity=0);"></iframe>
		<div style="position:absolute;width:116px;background-color:#ffffff;border:1px solid grey;cursor:pointer;display:none;" id="div_BorderStyleList" onmouseout="if( !Position.within(this, Event.pointerX(event), Event.pointerY(event)) ){borderStyleSelector.hideStyleList();}">
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)" style="padding-right:18px">
				<span style="width:100%;height:10px;border-bottom-style:none;text-align:center;" value="none" WCMAnt:param="resource_style_addedit.jsp.none">无</span>
			</div>
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)">
				<span class="border_style_opt" style="border-bottom-style:dotted;" value="dotted"></span>
			</div>
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)">
				<span class="border_style_opt" style="border-bottom-style:dashed;" value="dashed"></span>
			</div>
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)">
				<span class="border_style_opt" style="border-bottom-style:solid;" value="solid"></span>
			</div>
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)">
				<span class="border_style_opt" style="border-bottom-style:double;" value="double"></span>
			</div>
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)">
				<span class="border_style_opt" style="border-bottom-style:groove;" value="groove"></span>
			</div>
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)">
				<span class="border_style_opt" style="border-bottom-style:ridge;" value="ridge"></span>
			</div>
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)">
				<span class="border_style_opt" style="border-bottom-style:outset;" value="outset"></span>
			</div>
		</div>
	<!-- 边框样式选择器 @ END -->
	<!-- 颜色选择器 @ BEGIN -->
		<!-- 颜色选择器的mask容器，用于ie6下遮挡select @ BEGIN -->
			<iframe src="about:blank" id="iframe_colorselector_mask" style="display:none;position:absolute;border:0px;filter:alpha(opacity=0);"></iframe>
		<!-- 颜色选择器的mask容器，用于ie6下遮挡select @ END -->
		<!-- 颜色选择容器 @ begin -->
			<div id="div_color" style="display:none;position:absolute;border:solid 1px #999999;background-color:#f9f8f7;font-size:12px;z-Index:9999;" onblur="if( !Position.within(this, Event.pointerX(event), Event.pointerY(event)) ){colorSelector.hideColorSelector();}" WCMAnt:param="resource_style_addedit.jsp.color_sel_container">颜色选择器容器</div>
		<!-- 颜色选择容器 @ END -->
		<!-- 更多颜色的选择容器 @ BEGIN -->
			<div id="div_MoreColor" style="display:none;position:absolute;border:solid 1px #999999;background-color:#f9f8f7;font-size:12px;" onblur="if( !Position.within(this, Event.pointerX(event), Event.pointerY(event)) ){colorSelector.hideMoreColorSelector();}" WCMAnt:param="resource_style_addedit.jsp.more_color_sel_container">更多颜色选择器容器</div>
		<!-- 更多颜色的选择容器 @ end -->
	<!-- 颜色选择器 @ END -->
</body>
</html>
<%!
	String sOptions = "1px=1px~2px=2px~3px=3px~4px=4px~5px=5px~6px=6px";
	public String drawBorderWidthSelector(String _sSelectDomId,String _sSelectedValue,String _sOptions)throws WCMException {
		String sItemSplit = "~";//每一项分割符
		String sTextValueSplit = "=";//某一项中的key=value分割符
		String[] sItem = null;
		StringBuffer sDomHtmlBuffer = new StringBuffer();
		sDomHtmlBuffer.append("<input type='text' class='inputSelect_input inputtext' ");
		sDomHtmlBuffer.append("id='input_"+_sSelectDomId+"' ");
		sDomHtmlBuffer.append("name='"+_sSelectDomId+"' ");
		sDomHtmlBuffer.append("value='"+_sSelectedValue+"' ");
		
		sDomHtmlBuffer.append("isInputable='true' ParamType='StyleItem'/>");

		sDomHtmlBuffer.append("<span class='inputSelect_span'>");
		sDomHtmlBuffer.append("<select id='select_"+_sSelectDomId+"' class='inputSelect_select' onchange=document.getElementById('input_" + _sSelectDomId + "').value=this.value>");

		sItem = _sOptions.split(sItemSplit);
		for(int i=0;i<sItem.length;i++){
			String[] sItemContent = sItem[i].split(sTextValueSplit);
			String sItemName = sItemContent[0];
			String sItemValue = sItemContent[0];
			if(sItemContent.length>1){
				sItemValue = sItemContent[1];
			}
			if("".equals(sItemValue)) continue;
			String sIsSelected=sItemValue.equals(_sSelectedValue.trim())?"selected":"";
			sDomHtmlBuffer.append("<option id='option_"+i+"' value='"+sItemName+"' _value='"+sItemValue+"'"+sIsSelected+">"+sItemName+"</option> ");
		}

		sDomHtmlBuffer.append("</select>");
		sDomHtmlBuffer.append("</span>");
		sDomHtmlBuffer.append("<span id='msg_"+_sSelectDomId+"'></span>");
		return sDomHtmlBuffer.toString();
	}

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