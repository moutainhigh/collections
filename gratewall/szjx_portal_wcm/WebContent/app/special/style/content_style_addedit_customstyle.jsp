<div class="design" style="width:805px;height:300px;">
	<div class="design_container" style="width:100%;">
		<!-- 自定义风格样式 @ BEGIN -->
			<div class="fieldset_box" style="width:100%">
				<div class="fieldset_box_head">
					<div class="fieldset_box_head_left" style="width:120px;background-color:#f6f6f6" WCMAnt:param="content_style_addedit_customstyle.jsp.custom_define_style">自定义风格样式</div>
				</div>
				<div class="fieldset_box_body">
					<div class="fieldset_box_content">
						<%
							String sCustomStyle = CMyString.showNull(oContentStyle.getCustomStyle(),LocaleServer.getString("content_style_addedit_customstyle.jsp.label.please_input_css_content", "请输入css文本..."));
						%>
						<textarea name="CustomStyle" style="height:200px;width:100%;" id="CustomStyle" validation_desc="自定义风格样式" WCMAnt:paramattr="validation_desc:content_style_addedit_customstyle.jsp.custom_define_style" validation="desc:'自定义风格样式',required:true,type:'string'" onfocus="focusCustome(this)" ParamType="Style" ><%=sCustomStyle%></textarea>
						<div style="height:100%;text-align:right">
								<img src="./images/qingkong.gif" onclick="clearCustome()"  width="85px" height="35px" border="0" style="cursor:hand;margin-right:15px;"/>
						</div>
					</div>
				</div>
				<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
			</div>
		<!-- 自定义风格样式 @ END -->
	</div>
</div>