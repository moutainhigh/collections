<div class="example">
	<div class="example_title" WCMAnt:param="resource_style_addedit_common.jsp.forexample">
		示例：
	</div>
	<div class="example_image">
		<img src="./css_template/resource_template_2.gif" border=0 alt="" width="395px">
	</div>
	<div class="example_desc">
		&nbsp;&nbsp;&nbsp;&nbsp;<span WCMAnt:param="resource_style_addedit_common.jsp.consultleftpicsetrightstylenewpicnewresstyle">参照左侧示意图片设置右侧对应的样式，通过上传新的图片，设置新的值来生成新的资源风格。</span>
	</div>
</div>
<div class="design">
	<div class="design_container">
		<table border=0 cellSpacing=0 cellPadding=0 class="switch_btn_box">
			<tr>
				<td class="btn btn_clicked" onclick="switchStyleSet(event,this);" ForSetBox="base_set_box"><a href="#"  WCMAnt:param="resource_style_addedit_common.jsp.base">基本</a></td>
				<td class="blank_fixed" width="5px">&nbsp;</td>
				<td class="btn btn_unclick" onclick="switchStyleSet(event,this);" ForSetBox="advanced_set_box"><a href="#" WCMAnt:param="resource_style_addedit_common.jsp.advanced">高级</a></td>
				<td class="blank_fixed">&nbsp;</td>
		</table>
		<div class="base_set_box" id="base_set_box">
			<div>
				<span style="padding-left:10px;font-size:12px;color:red;" WCMAnt:param="resource_style_addedit_common.jsp.atentionputunitcomeacrosswidthheightmargin">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
			</div>
			<!-- 头部 @ BEGIN -->
				<div class="fieldset_box">
					<div class="fieldset_box_head">
						<div class="fieldset_box_head_left" WCMAnt:param="resource_style_addedit_common.jsp.firsthead">1、头部</div>
					</div>
					<div class="fieldset_box_body">
						<div class="fieldset_box_content">
							<table border=0 cellspacing=0 cellpadding=0 width="100%">
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.height">高度：</td>
									<%
										String p_w_head_height = getStyleItemValue(hmResStyleItemMap,"p_w_head_height");
										p_w_head_height = CMyString.showNull( p_w_head_height, "30px" );
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_head_height" value="<%=p_w_head_height%>" validation="desc:'高度输入内容',required:true,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.heightformatbeintandunitptempx","高度格式必须为:整数+单位(pt/em/px)")%>',max_len:30,showid:'p_w_head_height_mgr'" ParamType="StyleItem"  validation_desc="高度输入内容" WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.heightinputcontent"><span id="p_w_head_height_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.leftbgpic">左侧背景图：</td>
									<%
										String sHeadBgImageUrl = getStyleItemValue(hmResStyleItemMap,"p_w_head_background");
										sHeadBgImageUrl = CMyString.showNull( sHeadBgImageUrl, "" );
									%>
									<td class="set_value">
										<input type="hidden" name="p_w_head_background" id="p_w_head_background" value="<%=CMyString.filterForHTMLValue(sHeadBgImageUrl)%>" ParamType="StyleItem" >
										<span style="vertical-align:middle;">
										  <iframe src="./image_for_style_upload.jsp?InputId=p_w_head_background&FileUrl=<%=CMyString.filterForHTMLValue(sHeadBgImageUrl)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="p_w_head_background_iframe" name="p_w_head_background_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no" noresize></iframe>
										</span>
										<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
											<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('p_w_head_background')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
										</span>
									</td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.rightbgpic">右侧背景图：</td>
									<%
										String p_w_more_background = getStyleItemValue(hmResStyleItemMap,"p_w_more_background");
										p_w_more_background = CMyString.showNull( p_w_more_background, "" );
									%>
									<td class="set_value">
									<input type="hidden" name="p_w_more_background" value="<%=CMyString.filterForHTMLValue(p_w_more_background)%>" ParamType="StyleItem" id="p_w_more_background">
									<span style="vertical-align:middle;"><iframe src="./image_for_style_upload.jsp?InputId=p_w_more_background&FileUrl=<%=CMyString.filterForHTMLValue(p_w_more_background)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="p_w_more_background_iframe" name="p_w_more_background_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe></span>
										<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
											<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('p_w_more_background')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
										</span>
									</td>
								</tr>
								<tr>
									<td class="set_title" style="width:90px" WCMAnt:param="resource_style_addedit_common.jsp.widthofrightpic">右侧图宽度：</td>
									<%
										String p_w_more_width = getStyleItemValue(hmResStyleItemMap,"p_w_more_width");
										p_w_more_width = CMyString.showNull( p_w_more_width, "auto" );
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_more_width" value="<%=p_w_more_width%>" validation="desc:'宽度输入内容',required:false,max_len:30,type:'string',format:/((^[1-9]\d*|^0)(px|pt|em)$)|(^auto$)/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.widthformatbeintandunitptempxauto","宽度格式必须为:整数+单位(pt/em/px)或者auto")%>',showid:'p_w_more_width_mgr'" ParamType="StyleItem"   validation_desc="宽度输入内容" WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.widthinputcontent"><span id="p_w_more_width_mgr"></span>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
				</div>
			<!-- 头部 @ END -->
			<!-- 内容 @ BEGIN -->
				<div class="fieldset_box">
					<div class="fieldset_box_head">
						<div class="fieldset_box_head_left" WCMAnt:param="resource_style_addedit_common.jsp.secondcontent">2、内容</div>
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
										<input type="text" class="inputtext" name="p_w_content_padding" value="<%=p_w_content_padding%>" validation="desc:'间距',required:false,type:'string',max_len:30,showid:'p_w_content_padding_mgr'" ParamType="StyleItem" validation_desc="间距"   WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.spacing"><span id="p_w_content_padding_mgr"></span>
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
										<label for="p_w_content_background_repeat_x"><input type="radio" class="radio" <%=p_w_content_background_repeat.equals("repeat-x")?"checked ":""%> ParamType="StyleItem" id="p_w_content_background_repeat_x" name="p_w_content_background_repeat" value="repeat-x" WCMAnt:param="resource_style_addedit_common.jsp.repeat.x">横向</label>
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
		<div class="advanced_set_box box_hide" id="advanced_set_box">
			<div>
				<span style="padding-left:10px;font-size:12px;color:red;" WCMAnt:param="resource_style_addedit_common.jsp.atentionputunitcomeacrosswidthheightmargin">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
			</div>
			<!-- 标题 @ BEGIN -->
				<div class="fieldset_box">
					<div class="fieldset_box_head">
						<div class="fieldset_box_head_left" WCMAnt:param="resource_style_addedit_common.jsp.firsttitle">1、标题</div>
					</div>
					<div class="fieldset_box_body">
						<div class="fieldset_box_content">
							<table border=0 cellspacing=0 cellpadding=0 width="100%">
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.wetherdiplay">是否显示：</td>
									<%
										String p_w_title_display = getStyleItemValue(hmResStyleItemMap,"p_w_title_display");
										p_w_title_display = CMyString.showNull(p_w_title_display,"inline");
									%>
									<td class="set_value">
										<label for="p_w_title_display_inline"><input type="radio" class="radio" <%=p_w_title_display.equals("inline")?"checked ":""%> ParamType="StyleItem" id="p_w_title_display_inline" name="p_w_title_display" value="inline" WCMAnt:param="resource_style_addedit_common.jsp.yes">是</label>
										<label for="p_w_title_display_none"><input type="radio" class="radio" <%=p_w_title_display.equals("none")?"checked ":""%> ParamType="StyleItem" id="p_w_title_display_none" name="p_w_title_display" value="none" WCMAnt:param="resource_style_addedit_common.jsp.no">否</label>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.fontsize">字体大小：</td>
									<%
										String p_w_head_font_size = getStyleItemValue(hmResStyleItemMap,"p_w_head_font_size");
										p_w_head_font_size = CMyString.showNull(p_w_head_font_size,"14px");
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_head_font_size" ParamType="StyleItem" value="<%=p_w_head_font_size%>" validation="desc:'字体大小输入内容',required:false,max_len:5,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.fontsizeformatintunitptempx","字体大小格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_head_font_size_mgr'" validation_desc="字体大小输入内容"  WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.fontsizeinputcontent"><span id="p_w_head_font_size_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.fontstyle">字体样式：</td>
									<%
										String p_w_title_font_weight = getStyleItemValue(hmResStyleItemMap,"p_w_title_font_weight");
										p_w_title_font_weight = CMyString.showNull(p_w_title_font_weight,"normal");
									%>
									<td class="set_value">
										<label for="p_w_title_font_weight_normal"><input type="radio" class="radio" <%=p_w_title_font_weight.equals("normal")?"checked ":""%> ParamType="StyleItem" id="p_w_title_font_weight_normal" name="p_w_title_font_weight" value="normal" WCMAnt:param="resource_style_addedit_common.jsp.nomal">正常</label>
										<label for="p_w_title_font_weight_bold"><input type="radio" class="radio" <%=p_w_title_font_weight.equals("bold")?"checked ":""%> ParamType="StyleItem" id="p_w_title_font_weight_bold" name="p_w_title_font_weight" value="bold" WCMAnt:param="resource_style_addedit_common.jsp.bold">粗体</label>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.consulttoheadleft">与头部左边的间距：</td>
									<%
										String p_w_title_padding_left =getStyleItemValue(hmResStyleItemMap,"p_w_title_padding_left");
										p_w_title_padding_left = CMyString.showNull(p_w_title_padding_left,"20px");
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_title_padding_left" ParamType="StyleItem" value="<%=p_w_title_padding_left%>" validation="desc:'间距输入内容',required:false,type:'string',max_len:30,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.leftspacingformatintunitptempx","左间距格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_title_padding_left_mgr'"  validation_desc="间距输入内容" WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.consultinputcontent"><span id="p_w_title_padding_left_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.consulttouphead">与头部上边的间距：</td>
									<%
										String p_w_title_padding_top = getStyleItemValue(hmResStyleItemMap,"p_w_title_padding_top");
										p_w_title_padding_top = CMyString.showNull(p_w_title_padding_top,"2px");
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_title_padding_top" ParamType="StyleItem" value="<%=p_w_title_padding_top%>" validation="desc:'间距输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.upspacingformatintunitptempx","上间距格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_title_padding_top_mgr'"  validation_desc="间距输入内容" WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.consultinputcontent"><span id="p_w_title_padding_top_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.nolinkfontsize">无链接时字体颜色：</td>
									<td class="set_value">
										<span >
											<%
												String p_w_title_color = getStyleItemValue(hmResStyleItemMap,"p_w_title_color");
												p_w_title_color = CMyString.showNull(p_w_title_color,"#000000");
											%>
											<select name="p_w_title_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=p_w_title_color%>">
												<%
													if("".equals(p_w_title_color)){
												%>
												<option value="" WCMAnt:param="resource_style_addedit_common.jsp.pleaseselect">请选择</option>
												<%
													}else{
												%>
												<option style="background-color:<%=p_w_title_color%>" value="<%=p_w_title_color%>" selected>&nbsp;</option>
												<%
													}
												%>
											</select>
										</span>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.linkundercommonstatus">一般状态下链接：</td>
									<td class="set_value">
										<span >
										<%
											String p_w_title_a_color = getStyleItemValue(hmResStyleItemMap,"p_w_title_a_color");
										%>
											<select name="p_w_title_a_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_w_title_a_color) ? "#000000" : p_w_title_a_color%>">
												<%
													p_w_title_a_color = CMyString.showNull(p_w_title_a_color,"#000000");
													if("".equals(p_w_title_a_color)){
												%>
												<option value="" WCMAnt:param="resource_style_addedit_common.jsp.pleaseselect">请选择</option>
												<%
													}else{
												%>
												<option style="background-color:<%=p_w_title_a_color%>" value="<%=p_w_title_a_color%>" selected>&nbsp;</option>
												<%
													}
												%>
											</select>
										</span>
										<%
											String p_w_title_a_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_w_title_a_text_decoration");
											p_w_title_a_text_decoration = CMyString.showNull(p_w_title_a_text_decoration,"none");
										%>
										<label for="p_w_title_a_text_decoration"><input type="checkbox" name="p_w_title_a_text_decoration" id="p_w_title_a_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_w_title_a_text_decoration.equals("underline")?"checked":""%>  WCMAnt:param="resource_style_addedit_common.jsp.underline">下划线</label>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.mousestopoverlink">鼠标停留在链接上：</td>
									<td class="set_value">
										<span >
										<%
											String p_w_title_a_hover_color = getStyleItemValue(hmResStyleItemMap,"p_w_title_a_hover_color");
										%>
											<select name="p_w_title_a_hover_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_w_title_a_hover_color) ? "#000000" : p_w_title_a_hover_color%>">
											<%
											
												p_w_title_a_hover_color = 
												CMyString.showNull(p_w_title_a_hover_color,"#000000");
												if("".equals(p_w_title_a_hover_color)){
											%>
												<option value="" WCMAnt:param="resource_style_addedit_common.jsp.pleaseselect">请选择</option>
											<%
												}else{
											%>
												<option style="background-color:<%=p_w_title_a_hover_color%>" value="<%=p_w_title_a_hover_color%>" selected>&nbsp;</option>
											<%
												}
											%>
											</select>
										</span>
										<%
											String p_w_title_a_hover_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_w_title_a_hover_text_decoration");
											p_w_title_a_hover_text_decoration = CMyString.showNull(p_w_title_a_hover_text_decoration,"none");
										%>
										<label for="p_w_title_a_hover_text_decoration"><input type="checkbox" name="p_w_title_a_hover_text_decoration" id="p_w_title_a_hover_text_decoration"class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_w_title_a_hover_text_decoration.equals("underline")?"checked":""%>  WCMAnt:param="resource_style_addedit_common.jsp.underline">下划线</label>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
				</div>
			<!-- 标题 @ END -->
			<!-- 更多 @ BEGIN -->
				<div class="fieldset_box">
					<div class="fieldset_box_head">
						<div class="fieldset_box_head_left" style="width:120px;" WCMAnt:param="resource_style_addedit_common.jsp.secondmorepartstoset">2、更多部分的设置</div>
					</div>
					<div class="fieldset_box_body">
						<div class="fieldset_box_content">
							<table border=0 cellspacing=0 cellpadding=0 width="100%">
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.wetherdiplay">是否显示：</td>
									<%
										String p_w_more_a_display = getStyleItemValue(hmResStyleItemMap,"p_w_more_a_display");
										p_w_more_a_display = CMyString.showNull(p_w_more_a_display,"inline");
									%>
									<td class="set_value">
										<label for="p_w_more_a_display_inline"><input type="radio" class="radio" <%=p_w_more_a_display.equals("inline")?"checked ":""%> ParamType="StyleItem" id="p_w_more_a_display_inline" name="p_w_more_a_display" value="inline" WCMAnt:param="resource_style_addedit_common.jsp.wetherdiplay">是</label>
										<label for="p_w_more_a_display_none"><input type="radio" class="radio" <%=p_w_more_a_display.equals("none")?"checked ":""%> ParamType="StyleItem" id="p_w_more_a_display_none" name="p_w_more_a_display" value="none" WCMAnt:param="resource_style_addedit_common.jsp.no">否</label>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.fontsize">字体大小：</td>
									<%
										String p_w_more_font_size = getStyleItemValue(hmResStyleItemMap,"p_w_more_font_size");
										p_w_more_font_size = CMyString.showNull(p_w_more_font_size,"12px");
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_more_font_size" ParamType="StyleItem" value="<%=p_w_more_font_size%>" validation="desc:'字体大小输入内容',required:false,max_len:5,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.fontsizeformatintunitptempx","字体大小格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_more_font_size_mgr'"  validation_desc="字体大小输入内容"  WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.fontsizeinputcontent"><span id="p_w_more_font_size_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.fontstyle">字体样式：</td>
									<%
										String p_w_more_font_weight = getStyleItemValue(hmResStyleItemMap,"p_w_more_font_weight");
										p_w_more_font_weight = CMyString.showNull(p_w_more_font_weight,"normal");
									%>
									<td class="set_value">
										<label for="p_w_more_font_weight_normal"><input type="radio" class="radio" <%=p_w_more_font_weight.equals("normal")?"checked ":""%> ParamType="StyleItem" id="p_w_more_font_weight_normal" name="p_w_more_font_weight" value="normal"  WCMAnt:param="resource_style_addedit_common.jsp.nomal">正常</label>
										<label for="p_w_more_font_weight_bold"><input type="radio" class="radio" <%=p_w_more_font_weight.equals("bold")?"checked ":""%> ParamType="StyleItem" id="p_w_more_font_weight_bold" name="p_w_more_font_weight" value="bold" WCMAnt:param="resource_style_addedit_common.jsp.bold">粗体</label>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.consulttorighthead">与头部右边的间距：</td>
									<%
										String p_w_more_padding_right =getStyleItemValue(hmResStyleItemMap,"p_w_more_padding_right");
										p_w_more_padding_right = CMyString.showNull(p_w_more_padding_right,"20px");
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_more_padding_right" ParamType="StyleItem" value="<%=p_w_more_padding_right%>" validation="desc:'间距输入内容',required:false,type:'string',max_len:30,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.rightspacingformatintunitptempx","右间距格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_more_padding_right_mgr'"   validation_desc="间距输入内容" WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.consultinputcontent"><span id="p_w_more_padding_right_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.consulttouphead">与头部上边的间距：</td>
									<%
										String p_w_more_padding_top = getStyleItemValue(hmResStyleItemMap,"p_w_more_padding_top");
										p_w_more_padding_top = CMyString.showNull(p_w_more_padding_top,"2px");
									%>
									<td class="set_value">
										<input type="text" class="inputtext" name="p_w_more_padding_top" ParamType="StyleItem" value="<%=p_w_more_padding_top%>" validation="desc:'间距输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("resource_style_addedit_translate.jsp.upspacingformatintunitptempx","上间距格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_more_padding_top_mgr'"   validation_desc="间距输入内容" WCMAnt:paramattr="validation_desc:resource_style_addedit_common.jsp.consultinputcontent"><span id="p_w_more_padding_top_mgr"></span>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.linkundercommonstatus">一般状态下链接：</td>
									<td class="set_value">
										<span >
										<%
												String p_w_more_a_color = getStyleItemValue(hmResStyleItemMap,"p_w_more_a_color");
										%>
											<select name="p_w_more_a_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_w_more_a_color) ? "#000000" : p_w_more_a_color%>">
												<%
												
													p_w_more_a_color = CMyString.showNull(p_w_more_a_color,"#000000");
													if("".equals(p_w_more_a_color)){
												%>
												<option value=""  WCMAnt:param="resource_style_addedit_common.jsp.pleaseselect">请选择</option>
												<%
													}else{
												%>
												<option style="background-color:<%=p_w_more_a_color%>" value="<%=p_w_more_a_color%>" selected>&nbsp;</option>
												<%
													}
												%>
											</select>
										</span>
										<%
											String p_w_more_a_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_w_more_a_text_decoration");
											p_w_more_a_text_decoration = CMyString.showNull(p_w_more_a_text_decoration,"none");
										%>
										<label for="p_w_more_a_text_decoration"><input type="checkbox" name="p_w_more_a_text_decoration" id="p_w_more_a_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_w_more_a_text_decoration.equals("underline")?"checked":""%>   WCMAnt:param="resource_style_addedit_common.jsp.underline">下划线</label>
									</td>
								</tr>
								<tr>
									<td class="set_title" WCMAnt:param="resource_style_addedit_common.jsp.mousestopoverlink">鼠标停留在链接上：</td>
									<td class="set_value">
										<span >
										<%
											String p_w_more_a_hover_color = getStyleItemValue(hmResStyleItemMap,"p_w_more_a_hover_color");
										%>
											<select name="p_w_more_a_hover_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_w_more_a_hover_color) ? "#000000" : p_w_more_a_hover_color%>">
											<%
												
												p_w_more_a_hover_color = 
												CMyString.showNull(p_w_more_a_hover_color,"#000000");
												if("".equals(p_w_more_a_hover_color)){
											%>
												<option value=""  WCMAnt:param="resource_style_addedit_common.jsp.pleaseselect">请选择</option>
											<%
												}else{
											%>
												<option style="background-color:<%=p_w_more_a_hover_color%>" value="<%=p_w_more_a_hover_color%>" selected>&nbsp;</option>
											<%
												}
											%>
											</select>
										</span>
										<%
											String p_w_more_a_hover_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_w_more_a_hover_text_decoration");
											p_w_more_a_hover_text_decoration = CMyString.showNull(p_w_more_a_hover_text_decoration,"none");
										%>
										<label for="p_w_more_a_hover_text_decoration"><input type="checkbox" name="p_w_more_a_hover_text_decoration" id="p_w_more_a_hover_text_decoration"class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_w_more_a_hover_text_decoration.equals("underline")?"checked":""%>   WCMAnt:param="resource_style_addedit_common.jsp.underline">下划线</label>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
				</div>
			<!-- 更多 @ END -->
		</div>
	</div>
</div>