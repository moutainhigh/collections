<div class="example">
	<div class="example_title" WCMAnt:param="resource_style_addedit_common.jsp.forexample">
		示例：
	</div>
	<div class="example_image">
		<img src="./css_template/resource_template_4.gif" border=0 alt="" width="395px">
	</div>
	<div class="example_desc">
		&nbsp;&nbsp;&nbsp;&nbsp;<span WCMAnt:param="resource_style_addedit_common.jsp.consultleftpicsetrightstylenewpicnewresstyle">参照左侧示意图片设置右侧对应的样式，通过上传新的图片，设置新的值来生成新的资源风格。</span>
	</div>
</div>
<div class="design">
	<div class="design_container">
		<table border=0 cellSpacing=0 cellPadding=0 class="switch_btn_box">
			<tr>
				<td class="btn btn_clicked" ForSetBox="base_set_box"><a href="#"  WCMAnt:param="resource_style_addedit_common.jsp.base">基本</a></td>
				<td class="blank_fixed" width="5px">&nbsp;</td>
				<td class="blank_fixed">&nbsp;</td>
		</table>
		<div class="base_set_box" id="base_set_box">
			<div>
				<span style="padding-left:10px;font-size:12px;color:red;"  WCMAnt:param="resource_style_addedit_common.jsp.atentionputunitcomeacrosswidthheightmargin">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
			</div>
			<!-- 边框 @ BEGIN -->
				<div class="fieldset_box">
					<div class="fieldset_box_head">
						<div class="fieldset_box_head_left" WCMAnt:param="resource_style_addedit_common.jsp.firstframe">1、边框</div>
					</div>
					<div class="fieldset_box_body">
						<div class="fieldset_box_content">
							<table border=0 cellspacing=0 cellpadding=0 width="100%">
								<tr>
									<td class="set_title" style="width:90px"  WCMAnt:param="resource_style_addedit_common.jsp.lineshape">线形：</td>
									<%
										String p_w_body_border_style = getStyleItemValue(hmResStyleItemMap,"p_w_body_border_style");
										p_w_body_border_style = CMyString.showNull( p_w_body_border_style, "solid" );
									%>
									<td class="set_value">
										<select class="border_style_select" name="p_w_body_border_style" id="p_w_body_border_style" ParamType="StyleItem" onfocus="borderStyleSelector.showStyleList('p_w_body_border_style','p_w_body_border_style_value','div_BorderStyleList','iframe_BorderStyleSelector_mask');this.blur();">
											<option value="<%=p_w_body_border_style%>" selected>&nbsp;</option>
										</select>
										<span class="inputselectsp" style="padding:2px 0px 0px 2px;width:90px;">
											<iframe style="border:0px;position:absolute;height:100%;width:100%;filter:alpha(opacity=0);"></iframe>
											<span id="p_w_body_border_style_value" class="border_style_opt_blur" style="width:100%;height:100%;" onclick="borderStyleSelector.showStyleList('p_w_body_border_style','p_w_body_border_style_value','div_BorderStyleList','iframe_BorderStyleSelector_mask')">
												<%
													if("".equals(p_w_body_border_style)||"none".equals(p_w_body_border_style)){
												%>
													<span style="width:100%;height:10px;border-bottom-style:none;text-align:center;" WCMAnt:param="resource_style_addedit_common.jsp.none">无</span>
												<%
													}else{
												%>
													<span class="border_style_opt" style="border-bottom-style:<%=p_w_body_border_style%>;" value="<%=p_w_body_border_style%>">
													 </span>
												<%
													}
												%>
											</span>
										</span>
										 
									</td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px"  WCMAnt:param="resource_style_addedit_common.jsp.width">宽度：</td>
									<%
										String p_w_body_border_width = getStyleItemValue(hmResStyleItemMap,"p_w_body_border_width");
										p_w_body_border_style = CMyString.showNull( p_w_body_border_width, "1px" );
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_body_border_width" ParamType="StyleItem" value="<%=p_w_body_border_style%>" validation="desc:'边框宽度输入内容',required:false,max_len:10,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.framewidthformatbeintandunitptempx","边框宽度格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_body_border_width_mgr'"  validation_desc="边框宽度输入内容"  WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.framewidthinputcontent"><span id="p_w_body_border_width_mgr"></span>
									 </td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px"  WCMAnt:param="resource_style_addedit_common.jsp.color">颜色：</td>
									<%
										String p_w_body_border_color = getStyleItemValue(hmResStyleItemMap,"p_w_body_border_color");
										p_w_body_border_color = CMyString.showNull( p_w_body_border_color, "#000000" );
									%>
									<td class="set_value">
										<select name="p_w_body_border_color" id="p_w_body_border_color" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" ParamType="StyleItem" style="background-color:<%=p_w_body_border_color%>">
											<%
												if("".equals(p_w_body_border_color)){
											%>
												<option value=""  WCMAnt:param="resource_style_addedit_common.jsp.pleaseselect">请选择</option>
											<%
												}else{
											%>
												<option style="background-color:<%=p_w_body_border_color%>" value="<%=p_w_body_border_color%>" selected>&nbsp;</option>
											<%
												}
											%>
										</select>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
				</div>
			<!-- 边框 @ END -->
			<!-- 内容 @ BEGIN -->
				<div class="fieldset_box">
					<div class="fieldset_box_head">
						<div class="fieldset_box_head_left"  WCMAnt:param="resource_style_addedit_common.jsp.secondcontent">2、内容</div>
					</div>
					<div class="fieldset_box_body">
						<div class="fieldset_box_content">
							<table border=0 cellspacing=0 cellpadding=0 width="100%">
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.spanbetweenboundary">与边框的间距：</td>
									<%
										String p_w_content_padding = getStyleItemValue(hmResStyleItemMap,"p_w_content_padding");
										p_w_content_padding = CMyString.showNull( p_w_content_padding, "5px" );
									%>
									<td class="set_value">
										<input type="text" name="p_w_content_padding" value="<%=p_w_content_padding%>" validation="desc:'间距',required:false,type:'string',max_len:50,showid:'p_w_content_padding_mgr'" ParamType="StyleItem"  validation_desc="间距"   WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.spacing"><span id="p_w_content_padding_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.bgcolor">背景颜色：</td>
									<%
										String p_w_body_background_color = getStyleItemValue(hmResStyleItemMap,"p_w_body_background_color");
										p_w_body_background_color = CMyString.showNull( p_w_body_background_color, "transparent" );
									%>
									<td class="set_value">
										<select name="p_w_body_background_color" id="p_w_body_background_color" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" ParamType="StyleItem" style="float:left;background-color:<%=p_w_body_background_color%>">
											<%
												if("".equals(p_w_body_background_color)||"transparent".equals(p_w_body_background_color)){
											%>
												<option value="transparent" WCMAnt:param="resource_style_addedit_common.jsp.none">无</option>
											<%
												}else{
											%>
												<option style="background-color:<%=p_w_body_background_color%>" value="<%=p_w_body_background_color%>" selected>&nbsp;</option>
											<%
												}
											%>
										</select>

										<span class="resumethumb" style="vertical-align:middle;">
											<span style="display:inline-block;cursor:pointer;height:30px;vertical-align:middle;" onclick="resumeColor('p_w_body_background_color')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
										</span>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
				</div>
			<!-- 内容 @ END -->
			<!-- 内容背景图 @ BEGIN -->
				<div class="fieldset_box">
					<div class="fieldset_box_head">
						<div class="fieldset_box_head_left" style="width:100px;" WCMAnt:param="resource_style_addedit_common.jsp.thirdcontentbgpic">3、内容背景图</div>
					</div>
					<div class="fieldset_box_body">
						<div class="fieldset_box_content">
							<table border=0 cellspacing=0 cellpadding=0 width="100%">
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.uploadbgpic">上传背景图片：</td>
									<%
										String p_w_content_background = getStyleItemValue(hmResStyleItemMap,"p_w_content_background");
										p_w_content_background = CMyString.showNull( p_w_content_background, "" );
									%>
									<td class="set_value">
										<input type="hidden" name="p_w_content_background" id="p_w_content_background" value="<%=CMyString.filterForHTMLValue(p_w_content_background)%>" ParamType="StyleItem" >
										<span style="vertical-align:middle;"><iframe id="p_w_content_background_iframe" src="./image_for_style_upload.jsp?InputId=p_w_content_background&FileUrl=<%=CMyString.filterForHTMLValue(p_w_content_background)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe></span>
										<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
											<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('p_w_content_background')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
										</span>
									</td>
								</tr>
								<tr>
									<td class="set_title"  style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.picputway">图片平铺方式：</td>
									<%
										String p_w_content_background_repeat = getStyleItemValue(hmResStyleItemMap,"p_w_content_background_repeat");
										p_w_content_background_repeat = CMyString.showNull(p_w_content_background_repeat,"no-repeat");
									%>
									<td class="set_value">
										<label for="p_w_content_background_repeat_x"><input type="radio" class="radio" <%=p_w_content_background_repeat.equals("repeat-x")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_repeat_x" name="p_w_content_background_repeat" value="repeat-x"" WCMAnt:param="resource_style_addedit_common.jsp.repeat.x">横向</label>
										<label for="p_w_content_background_repeat_y"><input type="radio" class="radio" <%=p_w_content_background_repeat.equals("repeat-y")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_repeat_y" name="p_w_content_background_repeat" value="repeat-y" WCMAnt:param="resource_style_addedit_common.jsp.repeat.y">纵向</label>
										<label for="p_w_content_background_repeat_no_repeat"><input type="radio" class="radio" <%=p_w_content_background_repeat.equals("repeat")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_repeat_no_repeat" name="p_w_content_background_repeat" value="repeat"  WCMAnt:param="resource_style_addedit_common.jsp.repeat">平铺</label>
										<label for="p_w_content_background_repeat_no_repeat"><input type="radio" class="radio" <%=p_w_content_background_repeat.equals("no-repeat")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_repeat_no_repeat" name="p_w_content_background_repeat" value="no-repeat"  WCMAnt:param="resource_style_addedit_common.jsp.no.repeat">不平铺</label>
										
									</td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.x.directionposition">水平方向位置：</td>
									<%
										String p_w_content_background_pos_x = getStyleItemValue(hmResStyleItemMap,"p_w_content_background_pos_x");
										p_w_content_background_pos_x = CMyString.showNull(p_w_content_background_pos_x,"left");
									%>
									<td class="set_value">
										<label for="p_w_content_background_pos_x_left"><input type="radio" class="radio" <%=p_w_content_background_pos_x.equals("left")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_pos_x_left" name="p_w_content_background_pos_x" value="left" WCMAnt:param="resource_style_addedit_common.jsp.leftside">左边</label>
										<label for="p_w_content_background_pos_x_center"><input type="radio" class="radio" <%=p_w_content_background_pos_x.equals("center")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_pos_x_center" name="p_w_content_background_pos_x" value="center">中间</label>
										<label for="p_w_content_background_pos_x_right"><input type="radio" class="radio" <%=p_w_content_background_pos_x.equals("right")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_pos_x_right" name="p_w_content_background_pos_x" value="right" WCMAnt:param="resource_style_addedit_common.jsp.rightside">右边</label>
									</td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.y.direction.position">垂直方向位置：</td>
									<%
										String p_w_content_background_pos_y = getStyleItemValue(hmResStyleItemMap,"p_w_content_background_pos_y");
										p_w_content_background_pos_y = CMyString.showNull(p_w_content_background_pos_y,"top");
									%>
									<td class="set_value">
										<label for="p_w_content_background_pos_y_top"><input type="radio" class="radio" <%=p_w_content_background_pos_y.equals("top")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_pos_y_top" name="p_w_content_background_pos_y" value="top" WCMAnt:param="resource_style_addedit_common.jsp.y.top">顶端</label>
										<label for="p_w_content_background_pos_y_center"><input type="radio" class="radio" <%=p_w_content_background_pos_y.equals("center")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_pos_y_center" name="p_w_content_background_pos_y" value="center">中间</label>
										<label for="p_w_content_background_pos_y_bottom"><input type="radio" class="radio" <%=p_w_content_background_pos_y.equals("bottom")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_pos_y_bottom" name="p_w_content_background_pos_y" value="bottom" WCMAnt:param="resource_style_addedit_common.jsp.y.bottom">底部</label>
									</td>
								</tr>
								 
							</table>
						</div>
					</div>
					<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
				</div>
			<!-- 内容背景图 @ END -->
		</div>
	</div>
</div>