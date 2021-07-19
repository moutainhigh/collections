<input type="hidden" name="CustomStyle" value="" ParamType="Style" >
<div class="example">
	<div class="example_title" WCMAnt:param="content_style_addedit_2.jsp.example">
		示例：
	</div>
	<div class="example_image">
		<img src="./css_template/content_template_1.gif" border=0 alt="" width="395px">
	</div>
	<div class="example_desc" WCMAnt:param="content_style_addedit_2.jsp.new_content_style">
		&nbsp;&nbsp;&nbsp;&nbsp;参照左侧示意图片设置右侧对应的样式，通过上传新的图片，设置新的值来生成新的内容风格。
	</div>
</div>
<div class="design">
	<div class="design_container">
		<table border=0 cellSpacing=0 cellPadding=0 class="switch_btn_box">
			<tr>
				<td class="btn btn_clicked" onclick="switchStyleSet(event,this);" ForSetBox="base_set_box"><a href="#" WCMAnt:param="content_style_addedit_1.jsp.basic">基本</a></td>
				<td class="blank_fixed" width="5px">&nbsp;</td>
				<td class="btn btn_unclick" onclick="switchStyleSet(event,this);" ForSetBox="advanced_set_box"><a href="#" WCMAnt:param="content_style_addedit_1.jsp.advance">高级</a></td>
				<td class="blank_fixed">&nbsp;</td>
		</table>
		<div class="base_set_box" id="base_set_box">
			<div>
				<span style="padding-left:10px;font-size:12px;color:red;"  WCMAnt:param="content_style_addedit_2.jsp.attention">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
			</div>
		<!-- 没有文档时候的提示 @ BEGIN -->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left" style="width:120px;"  WCMAnt:param="content_style_addedit_2.jsp.haveno_doc_prompt">1、没有文档的提示</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr>
								<td class="set_title"  WCMAnt:param="content_style_addedit_2.jsp.font_type">字体类型：</td>
								<%
									String h1_font_family = getStyleItemValue(hmResStyleItemMap,"h1_font_family");
									h1_font_family = CMyString.showNull( h1_font_family, "宋体" );
									String sH1FontFamily = drawFontFamilySelected(h1_font_family);
								%>
								<td class="set_value">
									<select name="h1_font_family" ParamType="StyleItem">
										<%=sH1FontFamily%>
									</select>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_style">字体样式：</td>
								<%
									String h1_font_weight = getStyleItemValue(hmResStyleItemMap,"h1_font_weight");
									h1_font_weight = CMyString.showNull(h1_font_weight,"normal");
								%>
								<td class="set_value">
									<label for="h1_font_weight_normal"><input type="radio" class="radio" <%=h1_font_weight.equals("normal")?"checked ":""%> ParamType="StyleItem" id="h1_font_weight_normal" name="h1_font_weight" value="normal" WCMAnt:param="content_style_addedit_2.jsp.normal">正常</label>
									<label for="h1_font_weight_bold"><input type="radio" class="radio" <%=h1_font_weight.equals("bold")?"checked ":""%> ParamType="StyleItem" id="h1_font_weight_bold" name="h1_font_weight" value="bold" WCMAnt:param="content_style_addedit_2.jsp.bold">粗体</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_size">字体大小：</td>
								<%
									String h1_font_size = getStyleItemValue(hmResStyleItemMap,"h1_font_size");
									h1_font_size = CMyString.showNull( h1_font_size, "14px" );
								%>
								<td class="set_value">
									<input type="text" name="h1_font_size" class="inputtext" ParamType="StyleItem" value="<%=h1_font_size%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.font_size_format_limit","字体大小输入内容")%>',required:false,type:'string',max_len:5,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.font_size_format", "字体大小格式必须为:整数+单位(pt/em/px)")%>',showid:'h1_font_size_mgr'"/>
									<span id="h1_font_size_mgr"></span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.prompt_content_height">提示内容的高度：</td>
								<%
									String h1_line_height = getStyleItemValue(hmResStyleItemMap,"h1_line_height");
									h1_line_height = CMyString.showNull( h1_line_height, "30px" );
								%>
								<td class="set_value">
									<input type="text" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.prompt_content_limit","提示内容高度输入内容")%>',required:false,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.prompt_content","提示内容的高度格式必须为:整数+单位(pt/em/px)")%>',max_len:30" class="inputtext" name="h1_line_height" value="<%=h1_line_height%>" ParamType="StyleItem" >
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
		<!-- 没有文档时候的提示 @ END -->
		<!-- 文档标题 @ BEGIN -->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left" WCMAnt:param="content_style_addedit_2.jsp.doc_title">2、文档标题</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.title_icon">标题前图标：</td>
								<%
									String p_doc_title_background = getStyleItemValue(hmResStyleItemMap,"p_doc_title_background");
									p_doc_title_background = CMyString.showNull( p_doc_title_background, "" );
								%>
								<td class="set_value">
									<input type="hidden" name="p_doc_title_background" id="p_doc_title_background" value="<%=CMyString.filterForHTMLValue(p_doc_title_background)%>" ParamType="StyleItem" >
									<span style="vertical-align:middle;">
									<iframe src="./image_for_style_upload.jsp?InputId=p_doc_title_background&FileUrl=<%=CMyString.filterForHTMLValue(p_doc_title_background)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="p_doc_title_background_iframe" name="p_doc_title_background_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe>
									</span>
									<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
										<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('p_doc_title_background')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
									</span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.adjust_padding">边距调整(padding)：</td>
								<%
									String p_doc_title_padding = getStyleItemValue(hmResStyleItemMap,"p_doc_title_padding");
									p_doc_title_padding = CMyString.showNull( p_doc_title_padding, "0px 0px 0px 5px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="p_doc_title_padding" value="<%=p_doc_title_padding%>" ParamType="StyleItem" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.padding_format_limit","边距调整")%>',required:false,type:'string',max_len:30,format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.padding_format","边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成")%>'"/>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_type">字体类型：</td>
								<%
									String p_doc_title_font_family = getStyleItemValue(hmResStyleItemMap,"p_doc_title_font_family");
									p_doc_title_font_family = CMyString.showNull( p_doc_title_font_family, "宋体" );
									String sPDocTitleFontFamily = drawFontFamilySelected(p_doc_title_font_family);
								%>
								<td class="set_value">
									<select name="p_doc_title_font_family" ParamType="StyleItem">
										<%=sPDocTitleFontFamily%>
									</select>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_style">字体样式：</td>
								<%
									String p_doc_title_font_weight = getStyleItemValue(hmResStyleItemMap,"p_doc_title_font_weight");
									p_doc_title_font_weight = CMyString.showNull(p_doc_title_font_weight,"normal");
								%>
								<td class="set_value">
									<label for="p_doc_title_font_weight_normal"><input type="radio" class="radio" <%=p_doc_title_font_weight.equals("normal")?"checked ":""%> ParamType="StyleItem" id="p_doc_title_font_weight_normal" name="p_doc_title_font_weight" value="normal">正常</label>
									<label for="p_doc_title_font_weight_bold"><input type="radio" class="radio" <%=p_doc_title_font_weight.equals("bold")?"checked ":""%> ParamType="StyleItem" id="p_doc_title_font_weight_bold" name="p_doc_title_font_weight" value="bold">粗体</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_size">字体大小：</td>
								<%
									String p_doc_title_font_size = getStyleItemValue(hmResStyleItemMap,"p_doc_title_font_size");
									p_doc_title_font_size = CMyString.showNull( p_doc_title_font_size, "14px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="p_doc_title_font_size" ParamType="StyleItem" value="<%=p_doc_title_font_size%>" validation="desc:'字体大小输入内容',required:false,type:'string',max_len:5,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.font_size_format", "字体大小格式必须为:整数+单位(pt/em/px)")%>',showid:'p_doc_title_font_size_mgr'"  validation_desc="字体大小输入内容"  WCMAnt:paramattr="validation_desc:content_style_addedit_2.jsp.font_size_limit"/>
									<span id="p_doc_title_font_size_mgr"></span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.title_height">标题高度：</td>
								<%
									String p_doc_title_line_height = getStyleItemValue(hmResStyleItemMap,"p_doc_title_line_height");
									p_doc_title_line_height = CMyString.showNull( p_doc_title_line_height, "30px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="p_doc_title_line_height" value="<%=p_doc_title_line_height%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.title_height_limit","标题高度输入内容")%>',required:false,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.title_height","标题高度格式必须为:整数+单位(pt/em/px)")%>',max_len:30"   ParamType="StyleItem"/>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.not_visited_link">未访问的链接：</td>
								<td class="set_value">
									<span >
									<%
										String p_doc_title_a_color = getStyleItemValue(hmResStyleItemMap,"p_doc_title_a_color");
									%>
										<select name="p_doc_title_a_color" ParamType="StyleItem" class="colorSelector" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_doc_title_a_color) ? "#000000" : p_doc_title_a_color%>">
										<%
											p_doc_title_a_color = 
											CMyString.showNull(p_doc_title_a_color,"#000000");
											if("".equals(p_doc_title_a_color)){
										%>
											<option value="" WCMAnt:param="content_style_addedit_2.jsp.please_sel">请选择</option>
										<%
											}else{
										%>
											<option style="background-color:<%=p_doc_title_a_color%>" value="<%=p_doc_title_a_color%>" selected>&nbsp;</option>
										<%
											}
										%>
										</select>
									</span>
									<%
										String p_doc_title_a_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_doc_title_a_text_decoration");
										p_doc_title_a_text_decoration = CMyString.showNull(p_doc_title_a_text_decoration,"none");
									%>
									<label for="p_doc_title_a_text_decoration">
										<input type="checkbox" name="p_doc_title_a_text_decoration" id="p_doc_title_a_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_doc_title_a_text_decoration.equals("underline")?"checked":""%> WCMAnt:param="content_style_addedit_2.jsp.underline">下划线</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.visited_link">已访问的链接：</td>
								<td class="set_value">
									<span >
										<%
											String p_doc_title_a_visited_color = getStyleItemValue(hmResStyleItemMap,"p_doc_title_a_visited_color");
										%>
										<select name="p_doc_title_a_visited_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" class="colorSelector" style="background-color:<%=CMyString.isEmpty(p_doc_title_a_visited_color) ? "#000000" : p_doc_title_a_visited_color%>">
										<%
											
											p_doc_title_a_visited_color = 
											CMyString.showNull(p_doc_title_a_visited_color,"#000000");
											if("".equals(p_doc_title_a_visited_color)){
										%>
											<option value="" WCMAnt:param="content_style_addedit_2.jsp.please_sel">请选择</option>
										<%
											}else{
										%>
											<option style="background-color:<%=p_doc_title_a_visited_color%>" value="<%=p_doc_title_a_visited_color%>" selected>&nbsp;</option>
										<%
											}
										%>
										</select>
									</span>
									<%
										String p_doc_title_a_visited_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_doc_title_a_visited_text_decoration");
										p_doc_title_a_visited_text_decoration = CMyString.showNull(p_doc_title_a_visited_text_decoration,"none");
									%>
									<label for="p_doc_title_a_visited_text_decoration" >
										<input type="checkbox" name="p_doc_title_a_visited_text_decoration" id="p_doc_title_a_visited_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_doc_title_a_visited_text_decoration.equals("underline")?"checked":""%> WCMAnt:param="content_style_addedit_2.jsp.underline">下划线
									</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.mouse_stayon_link">鼠标停留在链接上：</td>
								<td class="set_value">
									<span >
									<%
											String p_doc_title_a_hover_color = getStyleItemValue(hmResStyleItemMap,"p_doc_title_a_hover_color");
									%>
										<select name="p_doc_title_a_hover_color" ParamType="StyleItem" class="colorSelector" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_doc_title_a_hover_color) ? "#000000" : p_doc_title_a_hover_color%>">
										<%
										
											p_doc_title_a_hover_color = 
											CMyString.showNull(p_doc_title_a_hover_color,"#000000");
											if("".equals(p_doc_title_a_hover_color)){
										%>
											<option value="" WCMAnt:param="content_style_addedit_2.jsp.please_sel">请选择</option>
										<%
											}else{
										%>
											<option style="background-color:<%=p_doc_title_a_hover_color%>" value="<%=p_doc_title_a_hover_color%>" selected>&nbsp;</option>
										<%
											}
										%>
										</select>
									</span>
									<%
										String p_doc_title_a_hover_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_doc_title_a_hover_text_decoration");
										p_doc_title_a_hover_text_decoration = CMyString.showNull(p_doc_title_a_hover_text_decoration,"none");
									%>
									<label for="p_doc_title_a_hover_text_decoration" >
										<input type="checkbox" name="p_doc_title_a_hover_text_decoration" id="p_doc_title_a_hover_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_doc_title_a_hover_text_decoration.equals("underline")?"checked":""%> WCMAnt:param="content_style_addedit_2.jsp.underline">下划线
									</label>
								</td>
							</tr>
							
						</table>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
		<!-- 文档标题 @ END -->
		</div>
		<div class="advanced_set_box box_hide" id="advanced_set_box">
			<div>
				<span style="padding-left:10px;font-size:12px;color:red;" WCMAnt:param="content_style_addedit_2.jsp.attention">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
			</div>
		<!-- 文档分割线 @ BEGIN -->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left" WCMAnt:param="content_style_addedit_2.jsp.doc_parting_line">1、文档分割线</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr >
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.display_ornot">是否显示：</td>
								<%
									String c_line_display = getStyleItemValue(hmResStyleItemMap,"c_line_display");
									c_line_display = CMyString.showNull( c_line_display, "none" );
								%>
								<td class="set_value">
									<label for="c_line_display_none"><input type="radio" class="radio" <%=c_line_display.equals("none")?"checked ":""%> ParamType="StyleItem" id="c_line_display_none" name="c_line_display" value="none" WCMAnt:param="content_style_addedit_2.jsp.none_display">不显示</label>
								<label for="c_line_display_block"><input type="radio" class="radio" <%=c_line_display.equals("block")?"checked ":""%> ParamType="StyleItem" id="c_line_display_block" name="c_line_display" value="block" WCMAnt:param="content_style_addedit_2.jsp.display">显示</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.parting_line_pic">分割线图片：</td>
								<%
									String c_line_background_image = getStyleItemValue(hmResStyleItemMap,"c_line_background_image");
									c_line_background_image = CMyString.showNull( c_line_background_image, "" );
								%>
								<td class="set_value">
									<input type="hidden" name="c_line_background_image" id="c_line_background_image" value="<%=CMyString.filterForHTMLValue(c_line_background_image)%>" ParamType="StyleItem" >
									<span style="vertical-align:middle;">
									<iframe src="./image_for_style_upload.jsp?InputId=c_line_background_image&FileUrl=<%=CMyString.filterForHTMLValue(c_line_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="c_line_background_image_iframe" name="c_line_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe>
									</span>
									<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
										<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('c_line_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
									</span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.container_height">容器高度：</td>
								<%
									String c_line_height = getStyleItemValue(hmResStyleItemMap,"c_line_height");
									c_line_height = CMyString.showNull( c_line_height, "30px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="c_line_height" value="<%=c_line_height%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.container_height_format_limit","容器高度输入内容")%>',required:false,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.container_height_format", "容器高度格式必须为:整数+单位(pt/em/px)")%>',max_len:30" ParamType="StyleItem" />
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.last_parting_line">最后一道分割线：</td>
								<%
									String c_last_line_display = getStyleItemValue(hmResStyleItemMap,"c_last_line_display");
									c_last_line_display = CMyString.showNull( c_last_line_display, "none" );
								%>
								<td class="set_value">
									<label for="c_last_line_display_none"><input type="radio" class="radio" <%=c_last_line_display.equals("none")?"checked ":""%> ParamType="StyleItem" id="c_last_line_display_none" name="c_last_line_display" value="none" WCMAnt:param="content_style_addedit_2.jsp.none_display">不显示</label>
									<label for="c_last_line_display_block"><input type="radio" class="radio" <%=c_last_line_display.equals("block")?"checked ":""%> ParamType="StyleItem" id="c_last_line_display_block" name="c_last_line_display" value="block" WCMAnt:param="content_style_addedit_2.jsp.display">显示</label>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
		<!-- 文档分割线 @ END -->
		<!-- 创建时间 @ BEGIN -->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left" WCMAnt:param="content_style_addedit_2.jsp.crtime">2、创建时间</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table border=0 cellspacing=0 cellpadding=0 width="100%">
						<!--不显示 文档的创建时间是否显示的设置，将该操作显示在资源块的变量中-->
							<tr style="display:none">
								<td class="set_title">是否显示：</td>
								<%
									String datetime_display = getStyleItemValue(hmResStyleItemMap,"datetime_display");
									datetime_display = CMyString.showNull( datetime_display, "none" );
								%>
								<td class="set_value">
									<label for="datetime_display_none"><input type="radio" class="radio" <%=datetime_display.equals("none")?"checked ":""%> ParamType="StyleItem" id="datetime_display_none" name="datetime_display" value="none" WCMAnt:param="content_style_addedit_2.jsp.none_display">不显示</label>
									<label for="datetime_display_inline"><input type="radio" class="radio" <%=datetime_display.equals("inline")?"checked ":""%> ParamType="StyleItem" id="datetime_display_inline" name="datetime_display" value="inline" WCMAnt:param="content_style_addedit_2.jsp.display">显示</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_type">字体类型：</td>
								<%
									String datetime_font_family = getStyleItemValue(hmResStyleItemMap,"datetime_font_family");
									datetime_font_family = CMyString.showNull( datetime_font_family, "宋体" );
									String sDatetimeFontFamily = drawFontFamilySelected(datetime_font_family);
								%>
								<td class="set_value">
									<select name="datetime_font_family" ParamType="StyleItem">
										<%=sDatetimeFontFamily%>
									</select>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_size">字体大小：</td>
								<%
								String datetime_font_size = getStyleItemValue(hmResStyleItemMap,"datetime_font_size");
								datetime_font_size = CMyString.showNull( datetime_font_size, "12px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="datetime_font_size" ParamType="StyleItem" value="<%=datetime_font_size%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.font_size_format_limit","字体大小输入内容")%>',required:false,max_len:5,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.font_size_format","字体大小格式必须为:整数+单位(pt/em/px)")%>',showid:'datetime_font_size_mgr'" /><span id="datetime_font_size_mgr"></span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.font_color">字体颜色：</td>
								<td class="set_value">
									<%
										String datetime_color = getStyleItemValue(hmResStyleItemMap,"datetime_color");
										datetime_color = 
										CMyString.showNull(datetime_color,"#000000");
									%>
									<select name="datetime_color" ParamType="StyleItem" class="colorSelector" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=datetime_color%>">
									<%
										if("".equals(datetime_color)){
									%>
										<option value="" WCMAnt:param="content_style_addedit_2.jsp.please_sel">请选择</option>
									<%
										}else{
									%>
										<option style="background-color:<%=datetime_color%>" value="<%=datetime_color%>" selected>&nbsp;</option>
									<%
										}
									%>
									</select>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.adjust_padding">边距调整(padding)：</td>
								<%
									String datetime_padding = getStyleItemValue(hmResStyleItemMap,"datetime_padding");
									datetime_padding = CMyString.showNull( datetime_padding, "0px 0px 0px 5px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="datetime_padding" value="<%=datetime_padding%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.padding_format_limit","边距调整")%>',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.padding_format","边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成")%>'" ParamType="StyleItem" />
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
		<!-- 创建时间 @ END -->
		<!-- 最新文档标识 @ BEGIN -->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left" style="width:100px;" WCMAnt:param="content_style_addedit_2.jsp.latest_docId">3、最新文档标识</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.display_ornot">是否显示：</td>
								<%
									String doc_new_icon_display = getStyleItemValue(hmResStyleItemMap,"doc_new_icon_display");
									doc_new_icon_display = CMyString.showNull( doc_new_icon_display, "none" );
								%>
								<td class="set_value">
									<label for="doc_new_icon_display_none"><input type="radio" class="radio" <%=doc_new_icon_display.equals("none")?"checked ":""%> ParamType="StyleItem" id="doc_new_icon_display_none" name="doc_new_icon_display" value="none" WCMAnt:param="content_style_addedit_2.jsp.none_display">不显示</label>
									<label for="doc_new_icon_display_inline"><input type="radio" class="radio" <%=doc_new_icon_display.equals("inline")?"checked ":""%> ParamType="StyleItem" id="doc_new_icon_display_inline" name="doc_new_icon_display" value="inline" WCMAnt:param="content_style_addedit_2.jsp.display">显示</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.latest_Id_pic">最新标识图片：</td>
								<%
									String doc_new_icon_background_image = getStyleItemValue(hmResStyleItemMap,"doc_new_icon_background_image");
									doc_new_icon_background_image = CMyString.showNull( doc_new_icon_background_image, "" );
								%>
								<td class="set_value">
									<input type="hidden" name="doc_new_icon_background_image" id="doc_new_icon_background_image" value="<%=CMyString.filterForHTMLValue(doc_new_icon_background_image)%>" ParamType="StyleItem" >
									<span style="vertical-align:middle;"><iframe src="./image_for_style_upload.jsp?InputId=doc_new_icon_background_image&FileUrl=<%=CMyString.filterForHTMLValue(doc_new_icon_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="doc_new_icon_background_image_iframe" name="doc_new_icon_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe>
									</span>
									<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
										<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('doc_new_icon_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
									</span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.container_height">容器高度：</td>
								<%
									String doc_new_icon_height = getStyleItemValue(hmResStyleItemMap,"doc_new_icon_height");
									doc_new_icon_height = CMyString.showNull( doc_new_icon_height, "7px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="doc_new_icon_height" value="<%=doc_new_icon_height%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.container_height_limit","容器高度输入内容")%>',required:false,max_len:30,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.container_height","容器高度格式必须为:整数+单位(pt/em/px)")%>',type:'string'" ParamType="StyleItem"/>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.container_width">容器宽度：</td>
								<%
									String doc_new_icon_width = getStyleItemValue(hmResStyleItemMap,"doc_new_icon_width");
									doc_new_icon_width = CMyString.showNull( doc_new_icon_width, "22px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="doc_new_icon_width" value="<%=doc_new_icon_width%>" validation="desc:'容器宽度输入内容',required:false,max_len:30,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.runtimeexception","容器宽度格式必须为:整数+单位(pt/em/px)")%>',type:'string'" ParamType="StyleItem" validation_desc="容器宽度输入内容" WCMAnt:paramattr="validation_desc:content_style_addedit_2.jsp.font_width_limit"/>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_2.jsp.adjust_margin">边距调整(margin)：</td>
								<%
									String doc_new_icon_margin = getStyleItemValue(hmResStyleItemMap,"doc_new_icon_margin");
									doc_new_icon_margin = CMyString.showNull( doc_new_icon_margin, "0px 0px 0px 0px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="doc_new_icon_margin" value="<%=doc_new_icon_margin%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_2.jsp.padding_format_limit","边距调整")%>',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'<%=LocaleServer.getString("content_style_addedit_2.jsp.label.padding_format","边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成")%>'" ParamType="StyleItem" />
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
		<!-- 文档分割线 @ END -->
		</div>
	</div>
</div>