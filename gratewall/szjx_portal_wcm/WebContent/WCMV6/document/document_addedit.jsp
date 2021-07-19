<%@ page contentType="text/html;charset=UTF-8" buffer="40kb" pageEncoding="utf-8"%>
<%@include file="document_addedit_import.jsp"%>
<%!boolean IS_DEBUG = false;%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>
<%
try{
%>
<%@include file="document_addedit_include.jsp"%>
<%
	out.clear();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM V6 <%=(nDocumentId>0)?"编辑文档":"新建文档"%></TITLE>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/document_addedit.css" rel="stylesheet" type="text/css" />
<script>
	top.actualTop = window;
</script>
<!-- ~Imports Begin~    -->
<script src="../js/com.trs.util/Common.js"></script>
<script label="Imports">
	$import('com.trs.logger.Logger');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.util.JSON');
	$import('com.trs.util.XML');
	$import('com.trs.util.CommonHelper');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.wcm.FloatPanelTop');
	$import('com.trs.wcm.util.FileUploadHelper');
	$import('com.trs.wcm.util.LockedUtil');
	$import('com.trs.util.UITransformer');
	$import('com.trs.util.YUIConnection');
	$import('com.trs.wcm.SimpleDragger');
	$import('com.trs.wcm.ui.ButtonGroup');
	$import('com.trs.wcm.ui.Buttons');
	$import('com.trs.wcm.BubblePanel');
	$import('opensource.fckpanel');
	$import('opensource.fckcolorselector');
	$import('com.trs.wcm.TemplateSelector');
	$import('com.trs.validator.Validator');
	$import('wcm52.calendar.Calendar');
	$import('com.trs.crashboard.CrashBoard');
</script>
<!-- ~Imports END~    -->
<%@include file="../individuation/init_individuation_config.jsp"%>
<script>
//	Calendar = TRSCalendar = {};
//	TRSCalendar.drawWithTime = function(){}
//	UITransformer = {};
//	UITransformer.transformAll = function(){}
//	UnLockMe = function(){}
</script>
<script src="./document_addedit.js" label="PageScope"></script>
<script language="javascript" src="../template/trsad_config.jsp" type="text/javascript"></script>
<script src="./CTRSButton.js"></script>
</HEAD>
<BODY>
<input type="hidden" id="DocEditor" name="DocEditor" value="<%=loginUser.getName()%>"/>
<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
<!--
	<tr class="editor_top">
		<td>
			<div class="editor_top_btn_bg">
			</div>
		</td>
	</tr>
-->
	<tr style="height:3px;background:#DADADA;">
		<td>
		</td>
	</tr>
	<tr style="height:1px;background:#BDBDBD;">
		<td>
		</td>
	</tr>
	<tr style="height:1px;background:#FFFFFF;">
		<td>
		</td>
	</tr>
	<tr style="height:5px;background:#DADADA;">
		<td>
		</td>
	</tr>
	<tr class="editor_title_tr">
		<td>
			<div class="editor_title">
				<div class="toolbar_label">文章标题</div>
				<div class="top_bar_shuru">
					<input id="DocTitle" name="DocTitle" class="input_text" type="text" size="70" value="<%=sTitle%>" validation="max_len:'200',min_len:'1',type:'string',required:true,desc:'标题'"/>
					<div id="CountTitle" title="当前位置/最大字符数"><span id="TitlePsn">0</span>/<span id="TitleCount">0</span></div>
					<div id="DocTitleHint" onclick="OpenSimpleEditor(1);" title="打开简易编辑器"></div>
				</div>
				<div class="title_color" onclick="SetTitleColor(this);">
				<div class="title_color1"></div>
				<div id="title_color" class="title_color2" style="background-color:<%=("".equals(sTitleColor)?"#000000":sTitleColor)%>;"></div>
				</div>
				<div class="document_option" style="%>">
					<input type="checkbox" id="AttachPic" name="AttachPic" value="1|0" <%=bAttachPic?"checked":""%> class="input_checkbox" isBoolean="yes" onclick="AttachPic(this.checked);" />
					图
				</div>
				<div class="toolbar_sep"></div>
				<div class="document_option">类型
					<%=getDocTypeSelector()%>
				</div>
				<div class="toolbar_sep"></div>
				<div class="toolbar_btn_basic attachment" onclick="ManageAttachment();">附件管理</div>
				<div class="toolbar_btn_basic relative_docs" onclick="ManageRelativeDoc();">相关文档</div>
			</div>
		</td>
	</tr>
	<!--
	<tr class="editor_toolbar" id="xToolbar_row">
		<td><div id="xToolbar" class="xToolbar" style="display:;"></div></td>
		<td>&nbsp;</td>
	</tr>
	-->
	<tr valign="top" align="center" height="100%">
		<td>
		<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;table-layout:fixed;">
		<col></col><col width="250"></col>
		<tr>
		<td class="editor_container">
			<div style="width:100%;height:100%;display:">
				<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
					<tr class="editor_body" align="center" valign="top">
						<td>
							<!--//文本型文档-->
							<div id="txt_editor" style="display:none">
								<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
								<tr class="other_blank"><td>
								</td></tr>
								<tr><td valign="top" align="center"><div id="txt_editor2">
								<textarea id="_editorValue_" name="DocContent" style="width:100%;height:99.5%;border:0;overflow:visible"><%=sContent%></textarea></div>
								</td></tr>
								</table>
							</div>
							<!--//链接型文档-->
							<div id="link_editor" style="display:none">
								<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
									<tr class="other_blank"><td>
									</td></tr>
									<tr style="height:100px"><td>
									</td></tr>
									<tr style="height:30px;" valign="top">
										<td align="center">
											<div id="link_editor2" style="border:1px solid #b7b7a6;width:500px;padding:20px;background:#FFFFFF;">
											<span style="float:left;line-height:30px;width:70px">链接地址:</span>
											<span style="float:left;margin:4px 0 0">
												<INPUT TYPE="text" id="DOCLINK" name="DOCLINK" style="width:248px" class="input_text" value="<%=sDocLink%>">
											</span>
											</div>
										</td>
									</tr>
									<tr><td></td></tr>
								</table>
							</div>
							<!--//文件型文档-->
							<div id="file_editor" style="display:none">
								<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
									<tr class="other_blank"><td>
									</td></tr>
									<tr style="height:100px"><td>
									</td></tr>
									<td style="height:60px;" valign="top" align="center">
										<div id="file_editor2" style="border:1px solid #b7b7a6;width:500px;padding:20px;background:#FFFFFF;">
										<form id="frmUploadDocFile" name="frmUploadDocFile" style="margin:0;width:100%;height:100%;" enctype='multipart/form-data'>
											<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
											<tr style="height:30px;" valign="top">
												<td align="center">
													<span style="float:left;line-height:30px;width:70px">选择文件:</span>
													<INPUT TYPE="file" name="DocFile" style="width:320px" class="input_text" value="">
													<INPUT TYPE="button" name="uploadDocFile" style="width:60px;height:20px;margin-left:5px;%>" class="input_text" value="上传" onclick="UpdateDocFile();">
												</td>
											</tr>
											<tr style="height:30px;" valign="top" align="center">
												<td>
													<span style="float:left;line-height:30px;width:70px">文件名:</span>
													<span style="float:left;margin:4px 0 0">
														<INPUT TYPE="text" id="DOCFILENAME" name="DOCFILENAME" readonly=true style="width:248px" class="input_text" value="<%=sDocFileName%>">
													</span>
												</td>
											</tr>
											</table>
										</form>
										</div>
									</td>
									<tr><td></td></tr>
								</table>
							</div>
							<!--//HTML文本型文档-->
							<table id="html_editor" border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;;display:none">
							<!--
								<tr class="top_ruler">
									<td></td><td></td>
								</tr>
								<tr>
									<td class="left_ruler"></td>
									<td class="editor_body" style="padding:15px;" valign="top">
										<iframe id="_trs_editor_" style="width:100%;height:100%" frameborder=0 allowTransparency="true" src="../editor/trs_iframe.html"></iframe>
									</td>
								</tr>
							-->
							<tr>
								<td>
									<iframe id="_trs_editor_" style="width:100%;height:100%;display:none" frameborder=0 src="../editor/trs_iframe.html"></iframe>
								</td>
							</tr>
							</table>
						</td>
					</tr>
					<!--
					<tr class="bottom_editor_toolbar">
						<td align="left" style="-moz-user-select:none;" onselectstart="return false;" ondragstart="return false;">
							<span id="btns4html">
							<div id="editor_btn_source" class="toolbar_btn_basic toolbar_source" onclick="SwitchMode('Source');ExecuteCommand('Source')">代码</div>
							<div id="editor_btn_design" class="toolbar_btn_basic toolbar_design toolbar_current_btn" onclick="SwitchMode('Design');ExecuteCommand('Design')">设计</div>
							</span>
						</td>
					</tr>
					-->
				</table>
			</div>
		</td>
		<td class="info_container" valign="top" style="width:250px;">
			<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
				<tr height="50" id="props_blank" class="props_blank1">
					<td></td>
				</tr>
				<tr class="attr_column_head" onclick="ShowAttrColumn('doc_basic');">
					<td>
						<div class="attr_column_title" style="cursor:pointer;" id="doc_basic_attr_title">
							基本属性
						</div>
					</td>
				</tr>
				<tr class="attr_column_content" id="doc_basic_attr_content" valign="top">
					<td>
						<div style="width:100%;height:100%;overflow:auto;">
							<div style="padding:5px 0 0 5px">
								<div class="attr_row">
									<span class="attr_name">所属栏目:</span>
									<span id="DocChannel" class="attr_value"><%=sChannelName%></span>
								</div>
								<div class="attr_row">
									<span class="attr_name">首页标题:</span>
									<span class="attr_input_text" style="width:150px;">
									<input type="text" name="DOCPEOPLE" value="<%=sOutlineTitle%>" validation="max_len:'200',type:'string',desc:'首页标题'">
									</span>
								</div>
								<div class="attr_row">
									<span class="attr_name">副标题:</span>
									<span class="attr_input_text" style="width:150px">
									<input type="text" name="SubDocTitle" value="<%=sDocSubTitle%>" validation="max_len:'200',type:'string',desc:'副标题'"></span>
								</div>
								<div class="attr_row">
									<span class="attr_name">关键词:</span>
									<span class="attr_input_text" style="width:150px;">
									<input type="text" name="DOCKEYWORDS" value="<%=sDocKeyWords%>" validation="max_len:'200',type:'string',desc:'关键词'">
									</span>
								</div>
								<div class="attr_row" style="height:200px;">
									<span class="attr_name"><span style="float:left">摘要:</span><span id="DocAbstractHint" onclick="OpenSimpleEditor(2);" title="打开简易编辑器"></span></span>
									<%if(bCKMExtract){%>
									<span class="wcm_pointer" style="float:right;margin-right:15px;color:red;" onclick="doCKM('Extract');">自动摘要</span>
									<%}%>
									<div></div>
									<span class="attr_textarea"><textarea style="margin:0 5px" name="DocAbstract" cols="25" rows="10" validation="max_len:'1000',type:'string',desc:'摘要'"><%=sDocAbstract%></textarea>
									</span>
								</div>
								<div class="attr_row">
									<span class="attr_name" style="width:35px;">来源:</span>
									<span class="attr_input_text" style="width:180px;">
										<input type="text" name="DocSourceName" style="width:158px;float:left;" value="<%=sDocSourceName%>" validation="max_len:'100',type:'string',desc:'来源'">
										<span id="DocSourceSpan">
										<%=showDocSource(loginUser, 0)%>
										</span>
									</span>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr class="attr_column_head" onclick="ShowAttrColumn('doc_other');">
					<td>
						<div class="attr_column_title" style="cursor:pointer;" id="doc_other_attr_title">
							其他属性
						</div>
					</td>
				</tr>
				<tr class="attr_column_content" id="doc_other_attr_content" valign="top" style="display:none">
					<td>
						<div style="width:100%;height:100%;overflow:auto;">
							<div style="padding:5px 0 0 5px">
								<div class="attr_row">
									<span class="attr_name">作者:</span>
									<span class="attr_input_text" style="width:150px">
									<input type="text" name="DocAuthor" value="<%=sDocAuthor%>" validation="max_len:'50',type:'string',desc:'作者'">
									</span>
								</div>
								<div class="attr_row">
									<span class="attr_name">撰写时间:</span>
									<script>
										TRSCalendar.drawWithTime("DocRelTime","<%=sRelTime%>");
									</script>											
								</div>
								<div class="attr_row">
									<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px">扩展字段:</span>
									<div></div>
									<span class="attr_table">
										<TABLE cellSpacing=1 cellPadding=2 width="100%" bgColor="#b7b7a6" border=0>
										<THEAD>
											<TR align="center" valign="middle" class="attr_extendfield_head">
												<TD>字段名称</TD>
												<TD>字段值</TD>
											</TR>
										</THEAD>
										<TBODY id="attr_extendfield_tbody" >
											<%=showExtendFields(currExtendedFields,currDocument,strDBName,false)%>
										</TBODY>
										</TABLE>
									</span>
								</div>
								<div class="attr_row">
									<span id="QuoteChannels" class="attr_name" style="width:100px;" ChannelIds="<%=getQuoteChannelIds(quoteChannels)%>">引用到<span class="num" id="QuoteChannelsNum"><%=(quoteChannels!=null)?quoteChannels.size():0%></span>个栏目:</span>
									<span class="remove_quotechannel wcm_pointer" onClick="RemoveSelectedQuoteChannel();" title="移除选中的引用栏目"></span>
									<span class="add_quotechannel wcm_pointer" onClick="AddQuoteChannel();" title="增加引用栏目"></span>
									<div></div>
									<span class="attr_table">
										<TABLE cellSpacing=1 cellPadding=2 width="100%" bgColor="#b7b7a6" border=0>
										<THEAD>
											<TR align="center" valign="middle" class="attr_quotechanel_head">
												<TD style="width:24px;cursor:pointer;" onclick="SelectAllQuoteChannel();">全选</TD>
												<TD>栏目名称</TD>
											</TR>
										</THEAD>
										<TBODY id="attr_quotechanel_tbody">
											<%=showQuoteChannels(quoteChannels)%>
										</TBODY>
										</TABLE>
									</span>
								</div>
							</div>
						</div>
					</td>
				</tr>
<%if(bIsCanTop){%>
				<tr class="attr_column_head" onclick="ShowAttrColumn('doc_settop');">
					<td>
						<div class="attr_column_title" style="cursor:pointer;" id="doc_settop_attr_title">
							置顶设置
						</div>
					</td>
				</tr>
				<tr class="attr_column_content" id="doc_settop_attr_content" style="display:none">
					<td>
						<div style="width:100%;height:100%;overflow:auto;">
							<div style="padding:5px 0 0 5px">
								<div class="attr_row">
									<span class="attr_table" style="width:230px">
										<TABLE cellSpacing=0 cellPadding=0 style="width:100%" border=0>
											<TR valign="middle">
												<TD class="attr_settop_column"  style="width:20px;"><input type="radio" id="pri_set_0" name="pri_set" value="0" checked onclick="PriSet(0);"></TD>
												<TD class="attr_settop_column">不置顶</TD>
											</TR>
											<TR valign="middle">
												<TD class="attr_settop_column"  style="width:20px;"><input type="radio" id="pri_set_2" name="pri_set" value="2" onclick="PriSet(2);"></TD>
												<TD class="attr_settop_column">永久置顶</TD>
											</TR>
											<TR valign="middle">
												<TD class="attr_settop_column"  style="width:20px;"><input type="radio" id="pri_set_1" name="pri_set" value="1" onclick="PriSet(1);"></TD>
												<TD class="attr_settop_column">
													<span style="float:left;line-height:24px">限时置顶</span>
													<span id="pri_set_deadline" style="display:<%=(bTopped&&!bTopForever)?"":"none"%>">
													<span class="" style="width:150px;margin-top:2px">
														<script>
															TRSCalendar.drawWithTime("TopInvalidTime","<%=dtTopValidTime%>");
														</script>
													</span>
													</span>
												</TD>
											</TR>
										</TABLE>
									</span>
								</div>
								<div class="attr_row">
									<span class="attr_table" style="width:230px">
										<TABLE cellSpacing=1 cellPadding=2 style="width:100%" bgColor="#b7b7a6" border=0>
										<THEAD>
											<TR align="left" valign="middle" style="color:blue;" class="attr_topsort_head">
												<TD>置顶文档,拖拽当前文档可排序:</TD>
											</TR>
										</THEAD>
										<TBODY id="attr_topsort_tbody" Topped="<%=bTopped%>">
											<%=showToppedDocs(toppedDocuments,nDocumentId)%>
										</TBODY>
										</TABLE>
									</span>
								</div>
							</div>
						</div>
					</td>
				</tr>
<%
}//end if
%>
				<tr class="attr_column_head" onclick="ShowAttrColumn('doc_publish');">
					<td>
						<div class="attr_column_title" style="cursor:pointer;" id="doc_publish_attr_title">
							发布设置
						</div>
					</td>
				</tr>
				<tr class="attr_column_content" id="doc_publish_attr_content" style="display:none">
					<td>
						<div style="width:100%;height:100%;overflow:auto;">
							<div style="padding:5px 0 0 5px">
								<div class="attr_row">
									<span class="attr_name">选择模板:</span>
									<span class="attr_name" style="width:16px;padding-top:0;float:left; margin-top: 5px;"><IMG border="0" style="cursor:pointer;%>" align="absmiddle" title="设置模板" src="../images/icon/TempSelect.gif" onclick="SelectTemps();"></span>
									<span id="spDetailTemp" _origTempId="<%=nDocTemplateId%>">
										<span _tempid="<%=nDocTemplateId%>" _tempname="<%=sDocTemplateName%>"   class="attr_suggestion tempEntity" style="width:130px;overflow:hidden;"><%=sDocTemplateName%></span>
									</span>
								</div>
<%
if(bIsCanPub){
%>
								<div class="attr_row">
									<span class="attr_name" style="width:80px">定时发布<input type="checkbox" name="cb_publish_time" onclick="setPublishOnTime(this.checked);" style="%>" <%=(bDefineSchedule)?"checked":""%>>:</span>
									<span id="sp_PublishOnTime" style="width:150px;display:<%=bDefineSchedule?"":"none"%>">
										<script>
											TRSCalendar.drawWithTime("ScheduleTime","<%=strExecTime%>");
										</script>											
									</span>
									<span id="sp_NotPublish" class="attr_value" style="display:<%=!bDefineSchedule?"":"none"%>;">
									未定义
									</span>
								</div>
							</div>
						</div>
					</td>
				</tr>
<%
}
%>
				<tr class="customize_area" id="customize_area" valign="top">
					<td>
						<div id="autosave_message" style="margin-bottom:10px;float:right;margin-right:10px;font-weight:bold;color:blue;">
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
	</td>
	</tr>
	<tr class="bottom_toolbar" id="bottom_toolbar" style="visibility:hidden">
		<td align="center" valign="top" style="-moz-user-select:none;padding-top:10px;" onselectstart="return false;" ondragstart="return false;">
<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
		<TR>
			<TD height="30" align="center" valign="middle">
			<script>
				//定义一个TYPE_ROMANTIC_BUTTON按钮
				var oTRSButtons			= new CTRSButtons();
				
				oTRSButtons.cellSpacing	= "0";
				oTRSButtons.nType			= TYPE_ROMANTIC_BUTTON;

				oTRSButtons.addTRSButton("预览", "Preview();");
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("document.button.saveclose", "保存并关闭")%>", "SaveExit();");
			<%if(!isNewsOrPics){%>
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("document.button.saveadd", "保存并新建")%>", "SaveAddNew();");
			<%}
			if(bIsCanPub && !isNewsOrPics) {%>
				oTRSButtons.addTRSButton("发布并新建", "PublishAddNew();");

				oTRSButtons.addTRSButton("<%=LocaleServer.getString("document.button.savepublish", "保存并发布")%>", "SavePublish();");

			<%}%>
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("document.button.cancel", "取消")%>", "SimpleExit();");
				
				oTRSButtons.draw();	
			</script>			
			</TD>
      </TR>
  </TABLE>
  <!--div onmouseover="Element.addClassName(this,'btm_toolbar_icon_outer_over');" onmouseout="Element.removeClassName(this,'btm_toolbar_icon_outer_over');" class="btm_toolbar_icon_outer">
			<div class="btm_toolbar_icon publish" onclick="Preview();" >预览</div>
			</div>
			<div class="btm_toolbar_icon_sep"></div>
			<script>
			///*
				var buttonGroup = new ButtonGroup('save_tab',false);
				buttonGroup.addItem(new ButtonGroupItem("保存...",window.SaveExit,'bgr_icon save_exit','保存并退出'));
				buttonGroup.addItem(new ButtonGroupItem("保存并新建",window.SaveAddNew,'bgr_icon save_addnew','保存并新建'));
				<%if(bIsCanPub){%>
				buttonGroup.addItem(new ButtonGroupItem("发布并新建",window.PublishAddNew,'bgr_icon publish_addnew','发布并新建'));
				buttonGroup.addItem(new ButtonGroupItem("保存并发布",window.SavePublish,'bgr_icon save_publish','保存并发布'));
				<%}%>
				buttonGroup.draw();
//				setTimeout(function(){
//					buttonGroup.bindEvent();
//				},100);
			//*/
			</script>
			<div class="btm_toolbar_icon_sep"></div>
			<div onmouseover="Element.addClassName(this,'btm_toolbar_icon_outer_over');" onmouseout="Element.removeClassName(this,'btm_toolbar_icon_outer_over');" class="btm_toolbar_icon_outer">
			<div class="btm_toolbar_icon exit" onclick="SimpleExit();">退出</div>
			</div-->
		</td>
	</tr>
</table>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCAPD%>">
<%=getAppendixsXml(currDocument,Appendix.FLAG_DOCAPD)%>
</textarea>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCPIC%>">
<%=getAppendixsXml(currDocument,Appendix.FLAG_DOCPIC)%>
</textarea>
<textarea style="display:none" id="appendix_<%=Appendix.FLAG_LINK%>">
<%=getAppendixsXml(currDocument,Appendix.FLAG_LINK)%>
</textarea>
<textarea style="display:none" id="relations">
<%=getRelationsXML(currDocument)%>
</textarea>
<SCRIPT>
	try{
		//根据文档类型选择编辑面板
		SwitchDocEditPanel('<%=nDocType%>');
		if('<%=nDocType%>'!='20'){
			$('bottom_toolbar').style.visibility = 'visible';
		}
		$('DocType').value = '<%=nDocType%>';
		//置顶设置相关
		$('pri_set_<%=bTopped?(bTopForever?2:1):0%>').checked = true;
		PageContext.LastTopFlag = <%=bTopped?(bTopForever?2:1):0%>;
		PageContext.DefineSchedule = <%=bDefineSchedule%>;
		var eCurrDocPriSetRow = document.getElementsByClassName("current_doc_row")[0];
		if(eCurrDocPriSetRow){
			new com.trs.wcm.PriSetDragger(eCurrDocPriSetRow);
		}
	}catch(err){}
	//缓存附件管理中的数据
	//Appendix.FLAG_DOCAPD
	//Appendix.FLAG_DOCPIC
	//Appendix.FLAG_LINK
	//currDocument
	var Appendixs = null;
	try{
		Appendixs = {
			Type_<%=Appendix.FLAG_DOCAPD%>:
				com.trs.util.JSON.parseXml(
					com.trs.util.XML.loadXML($('appendix_<%=Appendix.FLAG_DOCAPD%>').value)),
			Type_<%=Appendix.FLAG_DOCPIC%>:
				com.trs.util.JSON.parseXml(
					com.trs.util.XML.loadXML($('appendix_<%=Appendix.FLAG_DOCPIC%>').value)),
			Type_<%=Appendix.FLAG_LINK%>:
				com.trs.util.JSON.parseXml(
					com.trs.util.XML.loadXML($('appendix_<%=Appendix.FLAG_LINK%>').value))
		}
	}catch(err){
		Appendixs = {
			Type_<%=Appendix.FLAG_DOCAPD%>:{},
			Type_<%=Appendix.FLAG_DOCPIC%>:{},
			Type_<%=Appendix.FLAG_LINK%>:{}
		}
	}
	//缓存相关文档管理中的数据
	var Relations = null;
	try{
		Relations = com.trs.util.JSON.parseXml(
				com.trs.util.XML.loadXML($('relations').value));
	}catch(err){
		Relations = {};
	}
	//当前编辑中的文档ID,新文档为0
	var bIsReadOnly = <%=bIsReadOnly%>;
	var bEnablePicLib = <%=bEnablePicLib%>;
	var bEnableFlashLib = <%=bEnableFlashLib%>;
	var bEnableAdInTrs = window.trsad_config && window.trsad_config['enable']==true;
	var CKMConfig = {
		bCKMExtract : <%=bCKMExtract%>,
		bCKMSpellCheck : <%=bCKMSpellCheck%>,
		bCKMSimSearch : <%=bCKMSimSearch%>
	};
	var CurrDocId = '<%=nDocumentId%>';
	var DocChannelId = '<%=(docChannel!=null)?docChannel.getId():0%>';
	var CurrChannelId = '<%=(currChannel!=null)?currChannel.getId():0%>';
	var bIsCanTop = <%=bIsCanTop%>;
	var bIsCanPub = <%=bIsCanPub%>;
	var bIsNewsOrPics = <%=isNewsOrPics%>;
	//扩展字段字段名数组
	var ExtendFieldElements = null;
	try{
		ExtendFieldElements = [<%=getExtendFieldNames(currExtendedFields,currDocument,strDBName)%>];
	}catch(err){
		ExtendFieldElements = [];
	}
	PageContext.TitleColor = '<%=sTitleColor%>';
//	window.onfocus = function(){
//		try{
//			GetEditorBody().focus();
//		}catch(err){}
//	}
	window.UserName = '<%=loginUser.getName()%>';
</SCRIPT>
<iframe src="../include/processbar.html" id='processbar' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
<iframe src="../include/window.html" id='floatpanel' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
<!--
<iframe src="document_insert_image_window.html" id='myimage_window' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
-->
</BODY>
</HTML>
<%
}catch(Throwable ex){
	out.clear();
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
%>
<HTML xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM V6 编辑/修改文档</TITLE>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/document_addedit.css" rel="stylesheet" type="text/css" />
<script src="../js/com.trs.util/Common.js"></script>
<script>
	top.actualTop = window;
</script>
<script label="Imports">
	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.util.LockedUtil');
</script>
</HEAD>
<BODY>
<%
	int errorCode = 0;
	if(ex instanceof WCMException){
		WCMException myEx = (WCMException)ex;
		errorCode = myEx.getErrNo();
	}
%>
<textarea id="errorMsg" style="display:none">
<%=ex.getMessage()%>
</textarea>
<textarea id="errorStackTrace" style="display:none">
<%=WCMException.getStackTraceText(ex)%>
</textarea>
<script>
	var CurrDocId = '<%=nDocumentId%>';
	if(CurrDocId!=0){
//		LockerUtil.unlock(CurrDocId,605);
	}
	FaultDialog.show({
		code		: <%=errorCode%>,
		message		: $('errorMsg').value,
		detail		: $('errorStackTrace').value,
		suggestion  : ''
	}, '与服务器交互时出错啦！',function(){top.window.close();});
</script>
</BODY>
</HTML>
<%
}
%>