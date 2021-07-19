<input type="hidden" name="CustomStyle" value="" ParamType="Style" >
<div class="example">
	<div class="example_title" WCMAnt:param="content_style_addedit_1.jsp.example">
		示例：
	</div>
	<div class="example_image">
		<img src="./css_template/content_template_1.gif" border=0 alt="" width="395px">
	</div>
	<div class="example_desc"  WCMAnt:param="content_style_addedit_1.jsp.new_content_style">
		&nbsp;&nbsp;&nbsp;&nbsp;参照左侧示意图片设置右侧对应的样式，通过上传新的图片，设置新的值来生成新的内容风格。
	</div>
</div>
<div class="design">
	<div class="design_container">
		<table border=0 cellSpacing=0 cellPadding=0 class="switch_btn_box">
			<tr>
				<td class="btn btn_clicked" onclick="switchStyleSet(event,this);" ForSetBox="base_set_box"><a href="#"  WCMAnt:param="content_style_addedit_1.jsp.basic">基本</a></td>
				<td class="blank_fixed" width="5px">&nbsp;</td>
				<td class="btn btn_unclick" onclick="switchStyleSet(event,this);" ForSetBox="advanced_set_box"><a href="#"  WCMAnt:param="content_style_addedit_1.jsp.advance">高级</a></td>
				<td class="blank_fixed">&nbsp;</td>
		</table>
		<div class="base_set_box" id="base_set_box">
			<div>
				<span style="padding-left:10px;font-size:12px;color:red;"  WCMAnt:param="content_style_addedit_1.jsp.attention">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
			</div>
		<!-- 文档标题 @ BEGIN -->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left"  WCMAnt:param="content_style_addedit_1.jsp.doc_title">文档标题</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.title_icon">标题前图标：</td>
								<%
									String p_w_list_li_background = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_background");
									p_w_list_li_background = CMyString.showNull( p_w_list_li_background, "" );
								%>
								<td class="set_value">
									<input type="hidden" name="p_w_list_li_background" id="p_w_list_li_background" value="<%=CMyString.filterForHTMLValue(p_w_list_li_background)%>" ParamType="StyleItem" >

									<span style="vertical-align:middle;"><iframe src="./image_for_style_upload.jsp?InputId=p_w_list_li_background&FileUrl=<%=CMyString.filterForHTMLValue(p_w_list_li_background)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="p_w_list_li_background_iframe" name="p_w_list_li_background_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe>
									</span>
									<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
										<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('p_w_list_li_background')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
									</span>
								</td>
							</tr>
							<tr>
								<td class="set_title"  WCMAnt:param="content_style_addedit_1.jsp.distance2_icon">与图标的距离：</td>
								<%
									String p_w_list_li_padding_left = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_padding_left");
									p_w_list_li_padding_left = CMyString.showNull( p_w_list_li_padding_left, "5px" );
								%>
								<td class="set_value">
									<input type="text" class="inputtext" name="p_w_list_li_padding_left" value="<%=p_w_list_li_padding_left%>" ParamType="StyleItem" validation="desc:'<%=LocaleServer.getString("content_style_addedit_1.jsp.distance2pic_limit","与图标的距离输入内容")%>',type:'string',required:false,max_len:30,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_1.jsp.label.distance2pic_warning", "与图标的距离格式必须为:整数+单位(pt/em/px)")%>"  />
								</td>
							</tr>
							<tr>
								<td class="set_title"  WCMAnt:param="content_style_addedit_1.jsp.font_type">字体类型：</td>
								<%
									String p_w_list_li_font_family = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_font_family");
									p_w_list_li_font_family = CMyString.showNull( p_w_list_li_font_family, "宋体" );
									String sListFontFamily = drawFontFamilySelected(p_w_list_li_font_family);
								%>
								<td class="set_value">
									<select name="p_w_list_li_font_family" ParamType="StyleItem">
										<%=sListFontFamily%>
									</select>
								</td>
							</tr>
							<tr>
								<td class="set_title"  WCMAnt:param="content_style_addedit_1.jsp.font_size">字体大小：</td>
								<%
									String p_w_list_li_font_size = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_font_size");
									p_w_list_li_font_size = CMyString.showNull( p_w_list_li_font_size, "14px" );
								%>
								<td class="set_value">
									<input type="text" name="p_w_list_li_font_size" class="inputtext" ParamType="StyleItem" value="<%=p_w_list_li_font_size%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_1.jsp.font_size_limit","字体大小输入内容")%>',required:false,type:'string',max_len:5,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_1.jsp.label.font_size_warning","字体大小格式必须为:整数+单位(pt/em/px)")%>',showid:'p_w_list_li_font_size_mgr'"/>
									<span id="p_w_list_li_font_size_mgr"></span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.title_height">标题高度：</td>
								<%
									String p_w_list_li_line_height = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_line_height");
									p_w_list_li_line_height = CMyString.showNull( p_w_list_li_line_height, "30px" );
								%>
								<td class="set_value">
									<input type="text" name="p_w_list_li_line_height" class="inputtext" value="<%=p_w_list_li_line_height%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_1.jsp.titleheightinputcontent","标题高度输入内容")%>',required:false,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("metaviewdata_query.jsp.label.distance2pic_warning","<%标题高度格式必须为:整数+单位(pt/em/px)")%>',max_len:30" ParamType="StyleItem" 
									/>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.not_visited_link">未访问的链接：</td>
								<td class="set_value">
									<span >
										<%
											String p_w_list_li_a_color = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_a_color");
										%>
										<select name="p_w_list_li_a_color" ParamType="StyleItem" class="colorSelector" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_w_list_li_a_color) ? "#000000" : p_w_list_li_a_color%>">
										<%
																			p_w_list_li_a_color = 
											CMyString.showNull(p_w_list_li_a_color,"#000000");
											if("".equals(p_w_list_li_a_color)){
										%>
											<option value="" WCMAnt:param="content_style_addedit_1.jsp.please_select">请选择</option>
										<%
											}else{
										%>
											<option style="background-color:<%=p_w_list_li_a_color%>" value="<%=p_w_list_li_a_color%>" selected>&nbsp;</option>
										<%
											}
										%>
										</select>
									</span>
									<%
										String p_w_list_li_a_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_a_text_decoration");
										p_w_list_li_a_text_decoration = CMyString.showNull(p_w_list_li_a_text_decoration,"none");
									%>
									<label for="p_w_list_li_a_text_decoration">
										<input type="checkbox" name="p_w_list_li_a_text_decoration" id="p_w_list_li_a_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_w_list_li_a_text_decoration.equals("underline")?"checked":""%> ><span WCMAnt:param="content_style_addedit_1.jsp.underline">下划线</span></label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.visited_link">已访问的链接：</td>
								<td class="set_value">
									<span >
										<%
											String p_w_list_li_a_visited_color = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_a_visited_color");
										%>
										<select name="p_w_list_li_a_visited_color" ParamType="StyleItem" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" class="colorSelector" style="background-color:<%=CMyString.isEmpty(p_w_list_li_a_visited_color) ? "#000000" : p_w_list_li_a_visited_color%>">
										<%
											
											p_w_list_li_a_visited_color = 
											CMyString.showNull(p_w_list_li_a_visited_color,"#000000");
											if("".equals(p_w_list_li_a_visited_color)){
										%>
											<option value="" WCMAnt:param="content_style_addedit_1.jsp.please_select">请选择</option>
										<%
											}else{
										%>
											<option style="background-color:<%=p_w_list_li_a_visited_color%>" value="<%=p_w_list_li_a_visited_color%>" selected>&nbsp;</option>
										<%
											}
										%>
										</select>
									</span>
									<%
										String p_w_list_li_a_visited_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_a_visited_text_decoration");
										p_w_list_li_a_visited_text_decoration = CMyString.showNull(p_w_list_li_a_visited_text_decoration,"none");
									%>
									<label for="p_w_list_li_a_visited_text_decoration" >
										<input type="checkbox" name="p_w_list_li_a_visited_text_decoration" id="p_w_list_li_a_visited_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_w_list_li_a_visited_text_decoration.equals("underline")?"checked":""%> ><span WCMAnt:param="content_style_addedit_1.jsp.underline">下划线</span>
									</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.mouse_stayon_link">鼠标停留在链接上：</td>
								<td class="set_value">
									<span >
										<%
											String p_w_list_li_a_hover_color = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_a_hover_color");
										%>
										<select name="p_w_list_li_a_hover_color" ParamType="StyleItem" class="colorSelector" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');" style="background-color:<%=CMyString.isEmpty(p_w_list_li_a_hover_color) ? "#000000" : p_w_list_li_a_hover_color%>">
										<%
											
											p_w_list_li_a_hover_color = 
											CMyString.showNull(p_w_list_li_a_hover_color,"#000000");
											if("".equals(p_w_list_li_a_hover_color)){
										%>
											<option value="" WCMAnt:param="content_style_addedit_1.jsp.please_sel">请选择</option>
										<%
											}else{
										%>
											<option style="background-color:<%=p_w_list_li_a_hover_color%>" value="<%=p_w_list_li_a_hover_color%>" selected>&nbsp;</option>
										<%
											}
										%>
										</select>
									</span>
									<%
										String p_w_list_li_a_hover_text_decoration = getStyleItemValue(hmResStyleItemMap,"p_w_list_li_a_hover_text_decoration");
										p_w_list_li_a_hover_text_decoration = CMyString.showNull(p_w_list_li_a_hover_text_decoration,"none");
									%>
									<label for="p_w_list_li_a_hover_text_decoration" >
										<input type="checkbox" name="p_w_list_li_a_hover_text_decoration" id="p_w_list_li_a_hover_text_decoration" class="input_checkbox" ParamType="StyleItem" value="underline" <%=p_w_list_li_a_hover_text_decoration.equals("underline")?"checked":""%> ><span WCMAnt:param="content_style_addedit_1.jsp.underline">下划线</span>
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
				<span style="padding-left:10px;font-size:12px;color:red;" WCMAnt:param="content_style_addedit_1.jsp.attention">注意：涉及到宽度，高度，间距等，请填写单位(pt/em/px)</span>
			</div>
		<!-- 创建时间 @ BEGIN -->
			<div class="fieldset_box">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left" WCMAnt:param="content_style_addedit_1.jsp.crtime">创建时间</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<table border=0 cellspacing=0 cellpadding=0 width="100%">
							<!--不显示 文档的创建时间是否显示的设置，将该操作显示在资源块的变量中-->
							<tr style="display:none">
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.display_ornot">是否显示：</td>
								<%
									String datetime_display = getStyleItemValue(hmResStyleItemMap,"datetime_display");
									datetime_display = CMyString.showNull( datetime_display, "none" );
								%>
								<td class="set_value">
									<label for="datetime_display_none"><input type="radio" class="radio" <%=datetime_display.equals("none")?"checked ":""%> ParamType="StyleItem" id="datetime_display_none" name="datetime_display" value="none" WCMAnt:param="content_style_addedit_1.jsp.none_display">不显示</label>
								<label for="datetime_display_inline"><input type="radio" class="radio" <%=datetime_display.equals("inline")?"checked ":""%> ParamType="StyleItem" id="datetime_display_inline" name="datetime_display" value="inline" WCMAnt:param="content_style_addedit_1.jsp.display">显示</label>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.font_type">字体类型：</td>
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
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.font_size">字体大小：</td>
								<%
								String datetime_font_size = getStyleItemValue(hmResStyleItemMap,"datetime_font_size");
								datetime_font_size = CMyString.showNull( datetime_font_size, "12px" );
								%>
								<td class="set_value">
									<input type="text" name="datetime_font_size" class="inputtext" ParamType="StyleItem" value="<%=datetime_font_size%>" validation="desc:'<%=LocaleServer.getString("content_style_addedit_1.jsp.font_size_format_limit","字体大小输入内容")%>',required:false,type:'string',max_len:5,format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("content_style_addedit_1.jsp.label.font_size_format", "字体大小格式必须为:整数+单位)")%>',showid:'datetime_font_size_mgr'" />
									<span id="datetime_font_size_mgr"></span>
								</td>
							</tr>
							<tr>
								<td class="set_title" WCMAnt:param="content_style_addedit_1.jsp.font_color">字体颜色：</td>
								<td class="set_value">
									<%
										String datetime_color = getStyleItemValue(hmResStyleItemMap,"datetime_color");
										datetime_color = 
										CMyString.showNull(datetime_color,"#000000");
									%>
									<select name="datetime_color" ParamType="StyleItem" class="colorSelector" onfocus="this.blur();colorSelector.showColorSeletor(this,'div_color','div_MoreColor','iframe_colorselector_mask');"  style="background-color:<%=datetime_color%>">
									<%
										if("".equals(datetime_color)){
									%>
										<option value="" WCMAnt:param="content_style_addedit_1.jsp.please_sel">请选择</option>
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
						</table>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
		<!-- 创建时间 @ END -->
		</div>
	</div>
</div>