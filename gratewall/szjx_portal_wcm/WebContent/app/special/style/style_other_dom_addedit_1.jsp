<!-- 最新 @ BEGIN -->
	<div class="fieldset_box">
		<div class="fieldset_box_head">
			<div class="fieldset_box_head_left" style="width:100px;" WCMAnt:param="dom_addedit_1.icon_latest">1、图标-最新</div>
		</div>
		<div class="fieldset_box_body">
			<div class="fieldset_box_content">
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.image">图片：</td>
						<%
							String new_icon_background_image = getStyleItemValue(hmOtherDomStyleItems,"new_icon_background_image");
							new_icon_background_image = CMyString.showNull( new_icon_background_image, "" );
						%>
						<td class="set_value">
							<input type="hidden" name="new_icon_background_image" id="new_icon_background_image" value="<%=CMyString.filterForHTMLValue(new_icon_background_image)%>" ParamType="StyleItem" >
							<span style="vertical-align:middle;">
							<iframe src="./image_for_style_upload.jsp?InputId=new_icon_background_image&FileUrl=<%=CMyString.filterForHTMLValue(new_icon_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="new_icon_background_image_iframe" name="new_icon_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe>
							</span>
							<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
								<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('new_icon_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerHight">容器高度：</td>
						<%
							String new_icon_height = getStyleItemValue(hmOtherDomStyleItems,"new_icon_height");
							new_icon_height = CMyString.showNull( new_icon_height, "7px" );
						%>
						<td class="set_value">
							<input type="text" name="new_icon_height" value="<%=new_icon_height%>" ParamType="StyleItem" validation="desc:'容器高度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器高度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerWidth">容器宽度：</td>
						<%
							String new_icon_width = getStyleItemValue(hmOtherDomStyleItems,"new_icon_width");
							new_icon_width = CMyString.showNull( new_icon_width, "22px" );
						%>
						<td class="set_value">
							<input type="text" name="new_icon_width" value="<%=new_icon_width%>" ParamType="StyleItem" validation="desc:'容器宽度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器宽度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.marginSet">边距调整(margin)：</td>
						<%
							String new_icon_margin = getStyleItemValue(hmOtherDomStyleItems,"new_icon_margin");
							new_icon_margin = CMyString.showNull( new_icon_margin, "0px 0px 0px 0px" );
						%>
						<td class="set_value">
							<input type="text" name="new_icon_margin" value="<%=new_icon_margin%>" ParamType="StyleItem" validation="desc:'边距调整',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成'">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
	</div>
<!-- 最新 @ END -->
<!-- 热点 @ BEGIN -->
	<div class="fieldset_box">
		<div class="fieldset_box_head">
			<div class="fieldset_box_head_left" style="width:100px;" WCMAnt:param="dom_addedit_1.icon_hotspot">2、图标-热点</div>
		</div>
		<div class="fieldset_box_body">
			<div class="fieldset_box_content">
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.image">图片：</td>
						<%
							String hot_icon_background_image = getStyleItemValue(hmOtherDomStyleItems,"hot_icon_background_image");
							hot_icon_background_image = CMyString.showNull( hot_icon_background_image, "" );
						%>
						<td class="set_value">
							<input type="hidden" name="hot_icon_background_image" id="hot_icon_background_image" value="<%=CMyString.filterForHTMLValue(hot_icon_background_image)%>" ParamType="StyleItem" >
							<span style="vertical-align:middle;"><iframe src="./image_for_style_upload.jsp?InputId=hot_icon_background_image&FileUrl=<%=CMyString.filterForHTMLValue(hot_icon_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="hot_icon_background_image_iframe" name="hot_icon_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe>
							</span>
							<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
								<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('hot_icon_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerHeight">容器高度：</td>
						<%
							String hot_icon_height = getStyleItemValue(hmOtherDomStyleItems,"hot_icon_height");
							hot_icon_height = CMyString.showNull( hot_icon_height, "7px" );
						%>
						<td class="set_value">
							<input type="text" name="hot_icon_height" value="<%=hot_icon_height%>" ParamType="StyleItem" validation="desc:'容器高度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器高度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerWidth">容器宽度：</td>
						<%
							String hot_icon_width = getStyleItemValue(hmOtherDomStyleItems,"hot_icon_width");
							hot_icon_width = CMyString.showNull( hot_icon_width, "22px" );
						%>
						<td class="set_value">
							<input type="text" name="hot_icon_width" value="<%=hot_icon_width%>" ParamType="StyleItem" validation="desc:'容器宽度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器宽度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.marginset">边距调整(margin)：</td>
						<%
							String hot_icon_margin = getStyleItemValue(hmOtherDomStyleItems,"hot_icon_margin");
							hot_icon_margin = CMyString.showNull( hot_icon_margin, "0px 0px 0px 0px" );
						%>
						<td class="set_value">
							<input type="text" name="hot_icon_margin" value="<%=hot_icon_margin%>" ParamType="StyleItem" validation="desc:'边距调整',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成'">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
	</div>
<!-- 热点 @ END -->
<!-- 推荐 @ BEGIN -->
	<div class="fieldset_box">
		<div class="fieldset_box_head">
			<div class="fieldset_box_head_left" style="width:100px;" WCMAnt:param="dom_addedit_1.icon_advised">3、图标-推荐</div>
		</div>
		<div class="fieldset_box_body">
			<div class="fieldset_box_content">
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.image">图片：</td>
						<%
							String recommendation_icon_background_image = getStyleItemValue(hmOtherDomStyleItems,"recommendation_icon_background_image");
							recommendation_icon_background_image = CMyString.showNull( recommendation_icon_background_image, "" );
						%>
						<td class="set_value">
							<input type="hidden" name="recommendation_icon_background_image" id="recommendation_icon_background_image" value="<%=CMyString.filterForHTMLValue(recommendation_icon_background_image)%>" ParamType="StyleItem" >
							<span style="vertical-align:middle;">
							<iframe src="./image_for_style_upload.jsp?InputId=recommendation_icon_background_image&FileUrl=<%=CMyString.filterForHTMLValue(recommendation_icon_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="recommendation_icon_background_image_iframe" name="recommendation_icon_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe></span>
							<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
								<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('recommendation_icon_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerHeight">容器高度：</td>
						<%
							String recommendation_icon_height = getStyleItemValue(hmOtherDomStyleItems,"recommendation_icon_height");
							recommendation_icon_height = CMyString.showNull( recommendation_icon_height, "7px" );
						%>
						<td class="set_value">
							<input type="text" name="recommendation_icon_height" value="<%=recommendation_icon_height%>" ParamType="StyleItem" validation="desc:'容器高度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器高度格式必须为:整数+单位(pt/em/px)'">
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerWidth">容器宽度：</td>
						<%
							String recommendation_icon_width = getStyleItemValue(hmOtherDomStyleItems,"recommendation_icon_width");
							recommendation_icon_width = CMyString.showNull( recommendation_icon_width, "22px" );
						%>
						<td class="set_value">
							<input type="text" name="recommendation_icon_width" value="<%=recommendation_icon_width%>" ParamType="StyleItem" validation="desc:'容器宽度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器宽度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.marginset">边距调整(margin)：</td>
						<%
							String recommendation_icon_margin = getStyleItemValue(hmOtherDomStyleItems,"recommendation_icon_margin");
							recommendation_icon_margin = CMyString.showNull( recommendation_icon_margin, "0px 0px 0px 0px" );
						%>
						<td class="set_value">
							<input type="text" name="recommendation_icon_margin" value="<%=recommendation_icon_margin%>" ParamType="StyleItem" validation="desc:'边距调整',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成'">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
	</div>
<!-- 推荐 @ END -->
<!-- 视频 @ BEGIN -->
	<div class="fieldset_box">
		<div class="fieldset_box_head">
			<div class="fieldset_box_head_left" style="width:100px;" WCMAnt:param="dom_addedit_1.icon_video">4、图标-视频</div>
		</div>
		<div class="fieldset_box_body">
			<div class="fieldset_box_content">
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.image">图片：</td>
						<%
							String video_icon_background_image = getStyleItemValue(hmOtherDomStyleItems,"video_icon_background_image");
							video_icon_background_image = CMyString.showNull( video_icon_background_image, "" );
						%>
						 <td class="set_value">
							<input type="hidden" name="video_icon_background_image" id="video_icon_background_image" value="<%=CMyString.filterForHTMLValue(video_icon_background_image)%>" ParamType="StyleItem"><span style="vertical-align:middle;"><iframe src="./image_for_style_upload.jsp?InputId=video_icon_background_image&FileUrl=<%=CMyString.filterForHTMLValue(video_icon_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="video_icon_background_image_iframe" name="video_icon_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe></span>
							<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
								<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('video_icon_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerHeight">容器高度：</td>
						<%
							String video_icon_height = getStyleItemValue(hmOtherDomStyleItems,"video_icon_height");
							video_icon_height = CMyString.showNull( video_icon_height, "7px" );
						%>
						<td class="set_value">
							<input type="text" name="video_icon_height" value="<%=video_icon_height%>" ParamType="StyleItem" validation="desc:'容器高度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器高度格式必须为:整数+单位(pt/em/px)'">
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerWidth">容器宽度：</td>
						<%
							String video_icon_width = getStyleItemValue(hmOtherDomStyleItems,"video_icon_width");
							video_icon_width = CMyString.showNull( video_icon_width, "22px" );
						%>
						<td class="set_value">
							<input type="text" name="video_icon_width" value="<%=video_icon_width%>" ParamType="StyleItem" validation="desc:'容器宽度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器宽度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.marginset">边距调整(margin)：</td>
						<%
							String video_icon_margin = getStyleItemValue(hmOtherDomStyleItems,"video_icon_margin");
							video_icon_margin = CMyString.showNull( video_icon_margin, "0px 0px 0px 0px" );
						%>
						<td class="set_value">
							<input type="text" name="video_icon_margin" value="<%=video_icon_margin%>" ParamType="StyleItem" validation="desc:'边距调整',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成'">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
	</div>
<!-- 视频 @ END -->
<!-- 音频 @ BEGIN -->
	<div class="fieldset_box">
		<div class="fieldset_box_head">
			<div class="fieldset_box_head_left" style="width:100px;"  WCMAnt:param="dom_addedit_1.icon_audio">5、图标-音频</div>
		</div>
		<div class="fieldset_box_body">
			<div class="fieldset_box_content">
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.image">图片：</td>
						<%
							String audio_icon_background_image = getStyleItemValue(hmOtherDomStyleItems,"audio_icon_background_image");
							audio_icon_background_image = CMyString.showNull( audio_icon_background_image, "" );
						%>
						<td class="set_value">
							<input type="hidden" name="audio_icon_background_image" id="audio_icon_background_image" value="<%=CMyString.filterForHTMLValue(audio_icon_background_image)%>" ParamType="StyleItem" >
							<span style="vertical-align:middle;"><iframe src="./image_for_style_upload.jsp?InputId=audio_icon_background_image&FileUrl=<%=CMyString.filterForHTMLValue(audio_icon_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="audio_icon_background_image_iframe" name="audio_icon_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe></span>
							<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
								<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('audio_icon_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td class="set_title"  WCMAnt:param="dom_addedit_1.containerHeight">容器高度：</td>
						<%
							String audio_icon_height = getStyleItemValue(hmOtherDomStyleItems,"audio_icon_height");
							audio_icon_height = CMyString.showNull( audio_icon_height, "7px" );
						%>
						<td class="set_value">
							<input type="text" name="audio_icon_height" value="<%=audio_icon_height%>" ParamType="StyleItem" validation="desc:'容器高度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器高度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" WCMAnt:param="dom_addedit_1.containerWidth">容器宽度：</td>
						<%
							String audio_icon_width = getStyleItemValue(hmOtherDomStyleItems,"audio_icon_width");
							audio_icon_width = CMyString.showNull( audio_icon_width, "22px" );
						%>
						<td class="set_value">
							<input type="text" name="audio_icon_width" value="<%=audio_icon_width%>" ParamType="StyleItem" validation="desc:'容器宽度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'容器宽度格式必须为:整数+单位(pt/em/px)'"/>
						</td>
					</tr>
					<tr>
						<td class="set_title"  WCMAnt:param="dom_addedit_1.marginset">边距调整(margin)：</td>
						<%
							String audio_icon_margin = getStyleItemValue(hmOtherDomStyleItems,"audio_icon_margin");
							audio_icon_margin = CMyString.showNull( audio_icon_margin, "0px 0px 0px 0px" );
						%>
						<td class="set_value">
							<input type="text" name="audio_icon_margin" value="<%=audio_icon_margin%>" ParamType="StyleItem" validation="desc:'边距调整',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成'">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
	</div>
<!-- 音频 @ END -->
<!-- 文档 @ BEGIN -->
	<div class="fieldset_box">
		<div class="fieldset_box_head">
			<div class="fieldset_box_head_left" style="width:100px;"  WCMAnt:param="dom_addedit_1.icon_doc">6、图标-文档</div>
		</div>
		<div class="fieldset_box_body">
			<div class="fieldset_box_content">
				<table border=0 cellspacing=0 cellpadding=0 width="100%">
					<tr>
						<td class="set_title" style="width:110px"  WCMAnt:param="dom_addedit_1.image">图片：</td>
						<%
							String doc_icon_background_image = getStyleItemValue(hmOtherDomStyleItems,"doc_icon_background_image");
							doc_icon_background_image = CMyString.showNull( doc_icon_background_image, "" );
						%>
						<td class="set_value">
							<input type="hidden" name="doc_icon_background_image" id="doc_icon_background_image" value="<%=CMyString.filterForHTMLValue(doc_icon_background_image)%>" ParamType="StyleItem" ><span style="vertical-align:middle;"><iframe src="./image_for_style_upload.jsp?InputId=doc_icon_background_image&FileUrl=<%=CMyString.filterForHTMLValue(doc_icon_background_image)%>&ImageUploadPath=<%=CMyString.filterForHTMLValue(sImageUploadPath)%>" id="doc_icon_background_image_iframe" name="doc_icon_background_image_iframe" frameborder="no" border="0" framespacing="0" class="fileuploadiframe" scrolling="no"></iframe></span>
							<span style="vertical-align:middle;display:inline-block;" class="resumeBgImg">
								<span style="display:inline-block;cursor:pointer;height:27px;vertical-align:middle;" onclick="resumeBgImg('doc_icon_background_image')"><img src="../images/zt-xj_an1.gif" border=0 alt="" /></span>
							</span>
						</td>
					</tr>
					<tr>
						<td class="set_title" style="width:110px"  WCMAnt:param="dom_addedit_1.containerHeight">容器高度：</td>
						<%
							String doc_icon_height = getStyleItemValue(hmOtherDomStyleItems,"doc_icon_height");
							doc_icon_height = CMyString.showNull( doc_icon_height, "7px" );
						%>
						<td class="set_value">
							<input type="text" name="doc_icon_height" value="<%=doc_icon_height%>" ParamType="StyleItem" validation="desc:'容器高度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("dom_addedit_1.warningheight","容器高度格式必须为:整数+单位(pt/em/px)")%>'"  validation_desc="容器高度输入内容" WCMAnt:paramattr="validation_desc:dom_addedit_1.heightdescrible"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" style="width:110px"  WCMAnt:param="dom_addedit_1.containerWidht">容器宽度：</td>
						<%
							String doc_icon_width = getStyleItemValue(hmOtherDomStyleItems,"doc_icon_width");
							doc_icon_width = CMyString.showNull( doc_icon_width, "22px" );
						%>
						<td class="set_value">
							<input type="text" name="doc_icon_width" value="<%=doc_icon_width%>" ParamType="StyleItem" validation="desc:'容器宽度输入内容',required:false,max_len:30,type:'string',format:/(^[1-9]\d*|^0)(px|pt|em)$/,warning:'<%=LocaleServer.getString("dom_addedit_1.warningwidth","容器宽度格式必须为:整数+单位(pt/em/px)")%>'" validation_desc="容器宽度输入内容" WCMAnt:paramattr="validation_desc:dom_addedit_1.widthdescrible"/>
						</td>
					</tr>
					<tr>
						<td class="set_title" style="width:110px"  WCMAnt:param="dom_addedit_1.marginset">边距调整(margin)：</td>
						<%
							String doc_icon_margin = getStyleItemValue(hmOtherDomStyleItems,"doc_icon_margin");
							doc_icon_margin = CMyString.showNull( doc_icon_margin, "0px 0px 0px 0px" );
						%>
						<td class="set_value">
							<input type="text" name="doc_icon_margin" value="<%=doc_icon_margin%>" ParamType="StyleItem" validation="desc:'边距调整',required:false,max_len:30,type:'string',format:'/^\s*(?:(?:[1-9]\d*|0)(?:px|pt|em)(\s+|$)){0,4}$/',warning:'<%=LocaleServer.getString("dom_addedit_1.marginwarning","边距格式必须为：由空格分开的0-4组整数+单位(pt/em/px)组成")%>'" validation_desc="边距调整" WCMAnt:paramattr="validation_desc:dom_addedit_1.marginsetattr">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="fieldset_box_bottom"><div class="fieldset_box_bottom_left"></div></div>
	</div>
<!-- 文档 @ END -->