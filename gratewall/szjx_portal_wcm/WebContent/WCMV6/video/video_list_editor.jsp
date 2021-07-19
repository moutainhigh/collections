<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.trs.components.video.VSConfig" %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>视频列表</title>

<!-- ~Common CSS Begin~ -->
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/wcm_list_abstract.css" rel="stylesheet" type="text/css" />
<!-- ~Common CSS End~ -->

<!-- ~Imports Begin~    -->
<script src="../js/com.trs.util/Common.js"></script>
<script label="TagParser">
//	$import('com.trs.ajaxframe.TagParser');
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.dialog.Dialog');
</script>
<script label="Imports">
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.util.CommonHelper');
   $import('com.trs.wcm.SheetChanger');
	$import('com.trs.wcm.QuickKey');
	$import('com.trs.wcm.SimpleDragger');
	$import('com.trs.wcm.Grid');
	$import('com.trs.wcm.PageFilter');
	$import('com.trs.wcm.BubblePanel');
	$import('com.trs.wcm.FloatPanel');
	$import('com.trs.wcm.domain.DocumentMgr');	
	$import('com.trs.wcm.domain.ChannelMgr');	
</script>
<!-- ~Imports END~    -->

<!-- ~PageJavaScript Begin~    -->
<script src="../common/wcm_list_abstract.js" label="PageScope"></script>
<script src="video_list_editor.js" label="PageScope" desc="页面的描述文件"></script>
<!-- ~PageJavaScript END~    -->

<style label="PageScope">
	/*可变的列[文档标题]所使用的样式*/
	.doctitle{
		text-align:left;
		padding-left:0!important;
		padding-left:2px;
	}

	/**页面自己的样式**/
	.object_preview{
		background-image:url(../images/icon/Preview.png);
		background-repeat:no-repeat;
		background-position:center center;
	}
	.objectcannot_preview{
		background-image:url(../images/icon/CannotPreview.png);
		background-repeat:no-repeat;
		background-position:center center;
	}
	.document_switcher{
		float: right;
		height: 29px;
		width: 28px;
		margin-right:5px;
		overflow:hidden;
	}
	.document_mode_active{
		float:left;
		width:27px;
		height:29px;
		background:url(../images/document_normal_index/ico-bg1.png) no-repeat center center;
	}
	.document_mode_deactive{
		float:left;
		width:27px;
		height:29px;
		background:url(../images/document_normal_index/ico-bg.png) no-repeat center center;
	}
	.document_mode_normal{
		float:left;
		width:27px;
		height:29px;
		background:url(../images/icon/list.png) no-repeat center center;
	}
	.document_mode_read{
		float:left;
		width:27px;
		height:29px;
		background:url(../images/icon/file.png) no-repeat center center;
	}
	/*HTML*/
	.doctype_20{
		background-image:url(../images/icon/style.png);
		background-repeat:no-repeat;
		background-position:center center;
	}
	.doctype_10{
		background-image:url(../images/icon/style.png);
		background-repeat:no-repeat;
		background-position:center center;
	}
	/* ls@07-0109 */
.document_attribute_value_docindextitle{
	width:70px;
}
body, *{
	font-size: 12px;
}
</style>
</head>

<body>
<script>
</script>
<div id="dy_list_adjust"></div>
<table id="table_body" class="wcm_table_layout" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top" class="wcm_list_view">
			<table class="wcm_table_layout" border=0 cellspacing=0 cellpadding=0>
			<tbody>
				<tr class="list_pageinfo">
					<td colSpan="3">
						<div class="pageinfo">
							<div class="pageinfo_left"></div>
							<div class="pageinfo_right"></div>
							<div class="document_switcher">
								<div class="wcm_pointer document_mode_active">
									<div class="document_mode_read" onclick='(top.actualTop||top).PageContext.mode="read";$changeSheet(window.location.search);'></div>
								</div>
							</div>
							<span id="pageinfo"></span>
							<!--PageInfo模板-->
							<textarea id="pageinfo_template" select="PageInfo" style='display:none'>
								<table height="28" border="0" cellpadding="0" cellspacing="0">
									<tr height="28">
										<td align="left" valign="center">
											<div height="28" class="pagefilter_container">
												<for select="Filters.Filter">
												<span class="pagefilter {#IsDefault,,PageFilter.filterClass}" pagefilter_type="{#Type}">
													<table border="0" cellpadding="0" cellspacing="0">
														<tr height="23">
															<td class="left" width="7"></td>
															<td class="middle" nowrap="nowrap" valign="middle">
																<a href="#" onclick="return false">{#Name}</a>
															</td>
															<td class="right" width="7"></td>
														</tr>
													</table>
												</span>
												</for>
												<span class="wcm_pointer pagefilter_more_btn" id="pagefilter_more_btn"></span>
											</div>
										</td>
									</tr>
								</table>
							</textarea>
						</div> 
					</td>
				</tr>
				<tr class="list_view">
					<td class="list_view_border"></td>
					<td valign="top">
						<DIV id='objects_grid' class="grid">
							<table class="wcm_table_layout" border=0 cellspacing=0 cellpadding=0 style="table-layout:fixed;">
								<tr style="height:29px">
									<td>
										<DIV class="grid_head" id="grid_head">
											<!--列表头模板-->
										</DIV>
									</td>
								</tr>
								<tr>
									<td valign="top">
										<div id="grid_data" class="grid_data"></div>
										<!--Objects列表数据模板-->
										<textarea id="objects_template" select="Documents.Document" style='display:none'>
											<for select="." blankRef="divNoObjectFound" >
		<div id="video_{#DocId}" style="float:left; " class="grid_row" grid_rowid="{#DocId}" right="{#Right}" video_img="<%=VSConfig.getThumbsHomeUrl()%>/{#ATTRIBUTE.THUMB}" video_token="{#ATTRIBUTE.TOKEN}">

			<div style="margin: 0px 5px; width:128px; height: 120px;display:block;">
				<div>
					<span>
						<img id="thumb_{#DocId}" src="<%=VSConfig.getThumbsHomeUrl()%>/{#ATTRIBUTE.TOKEN}.jpg" onclick="javascript:FloatPanel.open('./video/player.jsp?v={#ATTRIBUTE.TOKEN}', '播放视频', 400, 300);" title="视频{#DocId}: {#DocTitle}" width="128" height="96"/>
					</span>
				</div>
				<div style="width:128px; overflow:hidden; white-space: nowrap; text-overflow:ellipsis;">
					<span>
						<input id="chk_{#DocId}" type="checkbox" class="grid_checkbox wcm_pointer element_list_checkbox" name="DocId" value="{#DocId}" title="选中/取消选中"></input>
					</span>
					<span style="overflow:hidden;">
						<a href="javascript:FloatPanel.open('./video/player.jsp?v={#ATTRIBUTE.TOKEN}', '播放视频', 400, 300);" title="{#DocTitle}">{#DocTitle}</a>
					</span>
				</div>

			</div>

		</div>
											</for>
										</textarea>
									</td>
								</tr>
							</table>
						</DIV>
					</td>
					<td class="list_view_border"></td>
				</tr>
				<tr class="list_navigator">
					<td class="list_view_border"></td>
					<td>
						<DIV id="list_navigator" class="list_navigator_div">
						</DIV>
					</td>
					<td class="list_view_border"></td>
				</tr>
			</tbody>
			</table>
		</td>
	</tr>
</table>
<div id="divNoObjectFound" style="display:none;">
	<div class="no_object_found">不好意思, 没有找到符合条件的视频..-____-|||</div>
</div>
<script language="javascript">
<!--
	PageContext.LoadPage();
//-->
</script>
</body>
</html>