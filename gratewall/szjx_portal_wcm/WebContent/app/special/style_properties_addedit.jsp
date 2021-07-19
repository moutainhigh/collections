<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Layout" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
	<title WCMAnt:param="properties_addedit.style.modifyPage">样式修改页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="css/common.css" rel="stylesheet" type="text/css" />
<!-- 公共js @ begin -->
	<script src="../js/easyversion/lightbase.js"></script>
	<script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/easyversion/extrender.js"></script>
	<script src="../js/easyversion/elementmore.js"></script>
	<script src="js/adapter4Top.js"></script>
<!-- 公共js @ end -->
	<!--validator start-->
	<script src="../js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<link href="../js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<!--validator end-->
<!-- color selector js @ BEGIN -->
	<script type="text/javascript" src="style/js/moreColorSelector.js"></script>
<!-- color selector js @ END -->
<!-- line selector js @ BEGIN -->
	<script type="text/javascript" src="style/js/borderStyleSelector.js"></script>
<!-- line selector js @ END -->
	<script src="style_properties_addedit.js"></script>
	<link rel="stylesheet" type="text/css" href="style/resource_style_addedit.css"/>
	<link href="style_properties_addedit.css" rel="stylesheet" type="text/css" />
 </head>

 <body>
<div class="style_set_container">
<div class="body" id="body">
<div class="example">
	<div class="example_title">
		示例：
	</div>
	<div class="example_image">
		<img src="./images/thumb-box.gif" border=0 alt="" width="395px">
	</div>
	<div class="example_desc">
		&nbsp;&nbsp;&nbsp;&nbsp;参照左侧示意图片设置右侧对应的样式，设置布局的内外边距。
	</div>
</div>
<div class="design">
	<div class="design_container">
		<div>
			<span style="padding-left:10px;font-size:12px;color:red;">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
		</div>
		<div class="base_set_box" id="base_set_box">
			 <form id="data">
			<!--外边距开始-->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left">外边距</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						 <!----><div class="row">
							<span class="set_title" WCMAnt:param="properties_addedit.topmargin">上外边距：</span>
							<input type="text" class="inputtext" id="margin-top" name="trs-margin-top" value="" onchange="changeOtherValue(this)" validation="desc:'上外边距输入内容',type:'string',format:/(^\-?[1-9]\d*|^0)(px|pt|em)$/,warning:'上外边距格式必须为:整数+单位(pt/em/px)',max_len:30,showid:'margin-top-msg'">
							<span id="margin-top-msg" class="valid-result"></span>
						 </div>
						 <div class="row">
							<span class="set_title" style="width:60px" WCMAnt:param="properties_addedit.rightmarign">右外边距：</span>
							<input type="text" class="inputtext" id="margin-right" name="trs-margin-right" value="" onchange="changeOtherValue(this)" validation="desc:'右外边距输入内容',type:'string',format:/(^\-?[1-9]\d*|^0)(px|pt|em)$/,warning:'右外边距式必须为:整数+单位(pt/em/px)',max_len:30,showid:'margin-right-msg'">
							<span id="margin-right-msg" class="valid-result"></span>
						 </div>
						 <div class="row">
							<span class="set_title" style="width:60px" WCMAnt:param="properties_addedit.bottommargin">下外边距：</span>
							<input type="text" class="inputtext" id="margin-bottom"  name="trs-margin-bottom" value="" onchange="changeOtherValue(this)" validation="desc:'下外边距输入内容',type:'string',format:/(^\-?[1-9]\d*|^0)(px|pt|em)$/,warning:'下外边距格式必须为:整数+单位(pt/em/px)',max_len:30,showid:'margin-bottom-msg'">
							<span id="margin-bottom-msg" class="valid-result"></span>
						 </div>
						 <div class="row">
							<span class="set_title" style="width:60px" WCMAnt:param="properties_addedit.leftmargin">左外边距：</span>
							<input type="text" class="inputtext"  id="margin-left" name="trs-margin-left" value="" onchange="changeOtherValue(this)" validation="desc:'左外边距输入内容',type:'string',format:/(^\-?[1-9]\d*|^0)(px|pt|em)$/,warning:'左外边距格式必须为:整数+单位(pt/em/px)',max_len:30,showid:'margin-left-msg'">
							<span id="margin-left-msg" class="valid-result"></span>
						 </div>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
			<!--外边距结束-->

			<!--边框开始-->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left">边框设置：</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<tbody><tr>
								<td style="width: 55px;" class="set_title">线&nbsp;&nbsp;形：</td>
								
								<td class="set_value" >
									<select onfocus="borderStyleSelector.showStyleList('trs-border-style','p_w_body_border_style_value','div_BorderStyleList','iframe_BorderStyleSelector_mask');this.blur();" paramtype="StyleItem" id="trs-border-style" name="trs-border-style" class="border_style_select">
										<option value="none" class="none_style">&nbsp;</option>
									</select>
									<span style="padding: 2px 0px 0px 2px; width: 90px;height:15px;" class="inputselectsp">
										<iframe style="border: 0px none; position: absolute; height: 100%; width: 100%;filter:alpha(opacity=0);"></iframe>
										<span onclick="borderStyleSelector.showStyleList('trs-border-style','p_w_body_border_style_value','div_BorderStyleList','iframe_BorderStyleSelector_mask')" style="width: 100%; height: 100%;" class="border_style_opt_blur" id="p_w_body_border_style_value">
											<span id='replaceEl' class="none_style" value="none">无</span>
										</span>
									</span>
								</td>
							</tr>
							<tr>
								<td style="width: 55px;" class="set_title">宽&nbsp;&nbsp;度：</td>
								
								<td class="set_value">
									<input type="text" validation="desc:'边框宽度输入内容',required:false,max_len:10,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'宽度格式必须为:整数+单位(pt/em/px)',showid:'p_w_body_border_width_mgr'" value="0px" paramtype="StyleItem" name="trs-border-width" class="inputtext">
								 </td>
							</tr>
							<tr>
								<td style="width:1px;height:1px;"></td>
								<td style="height:10px;"><div id="p_w_body_border_width_mgr" style="height:13px;"></div></td>
							</tr>
							<tr>
								<td style="width: 55px;" class="set_title">颜&nbsp;&nbsp;色：</td>
								<td class="set_value">
									<select style="background-color:#000000;" paramtype="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" id="trs-border-color" name="trs-border-color">
											<option value="#000000">&nbsp;</option>
									</select>
								</td>
							</tr>
						</tbody></table>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
			<!--边框结束-->

			<!--内边距开始-->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left">内边距</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<div class="row">
							<span class="set_title" WCMAnt:param="properties_addedit.toppadding">上内边距：</span>
							<input type="text" class="inputtext"  id="padding-top" name="trs-padding-top" value="" onchange="changeOtherValue(this)" validation="desc:'上内边距输入内容',type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'上内边距格式必须为:整数+单位(pt/em/px)',max_len:30,showid:'padding-top-msg'">
							<span id="padding-top-msg" class="valid-result"></span>
						</div>
						<div class="row">
							<span class="set_title" WCMAnt:param="properties_addedit.rightpadding">右内边距：</span>
							<input type="text" class="inputtext"  id="padding-right" name="trs-padding-right" value="" onchange="changeOtherValue(this)" validation="desc:'右内边距输入内容',type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'右内边距格式必须为:整数+单位(pt/em/px)',max_len:30,showid:'padding-right-msg'">
							<span id="padding-right-msg" class="valid-result"></span>
						</div>
						<div class="row">
							<span class="set_title" WCMAnt:param="properties_addedit.bottompadding">下内边距：</span>
							<input type="text" class="inputtext" id="padding-bottom"  name="trs-padding-bottom" value="" onchange="changeOtherValue(this)" validation="desc:'下内边距输入内容',type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'下内边距格式必须为:整数+单位(pt/em/px)',max_len:30,showid:'padding-bottom-msg'">
							<span id="padding-bottom-msg" class="valid-result"></span>
						</div>
						<div class="row">
							<span class="set_title" WCMAnt:param="properties_addedit.leftpadding">左内边距：</span>
							<input type="text" class="inputtext"  id="padding-left" name="trs-padding-left" value="" onchange="changeOtherValue(this)" validation="desc:'左内边距输入内容',type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'左内边距格式必须为:整数+单位(pt/em/px)',max_len:30,showid:'padding-left-msg'">
							<span id="padding-left-msg" class="valid-result"></span>
						</div>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
			<!--内边距结束-->
			</form>
		</div>
	</div>
</div>
</div>
</div>
	<!-- 边框样式选择器 @ BEGIN -->
		<iframe src="about:blank" id="iframe_BorderStyleSelector_mask" style="display:none;position:absolute;filter:alpha(opacity=0);"></iframe>
		<div style="position:absolute;width:116px;background-color:#ffffff;border:1px solid grey;cursor:pointer;display:none;" id="div_BorderStyleList" onmouseout="if( !Position.within(this, Event.pointerX(event), Event.pointerY(event)) ){borderStyleSelector.hideStyleList();}">
			<div class="border_style_opt_blur" onmouseout="this.className='border_style_opt_blur'" onmouseover="this.className='border_style_opt_focus'" onclick="borderStyleSelector.selectStyle(this)" style="padding-right:18px">
				<span style="width:100%;height:20px;border-bottom-style:none;text-align:center;" value="none">无</span>
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
			<div id="div_color" style="display:none;position:absolute;border:solid 1px #999999;background-color:#f9f8f7;font-size:12px;z-Index:9999;" onblur="if( !Position.within(this, Event.pointerX(event), Event.pointerY(event)) ){colorSelector.hideColorSelector();}">颜色选择器容器</div>
		<!-- 颜色选择容器 @ END -->
		<!-- 更多颜色的选择容器 @ BEGIN -->
			<div id="div_MoreColor" style="display:none;position:absolute;border:solid 1px #999999;background-color:#f9f8f7;font-size:12px;">更多颜色选择器容器</div>
		<!-- 更多颜色的选择容器 @ end -->
	<!-- 颜色选择器 @ END -->
 </body>
</html>