<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.trs.components.video.VSConfig" %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="video_list.jsp.videoList">视频列表</title>

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
	$import('com.trs.wcm.domain.ChnlDocMgr');
	$import('com.trs.wcm.domain.ChannelMgr');	
</script>
<!-- ~Imports END~    -->

<!-- ~PageJavaScript Begin~    -->
<script src="../common/wcm_list_abstract.js" label="PageScope"></script>
<script src="video_list.js" label="PageScope" desc="页面的描述文件" WCMAnt:paramattr="desc:video_list.jsp.file"></script>
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
	/* ls@07-0109
	.document_attribute_value_docindextitle{
		width:70px;
	} */
	/* ls@2007-10-15 表示置顶、引用的图标 */
	.document_topped{
		width:16px;
		background-image:url(../images/document_normal_index/topdoc.gif);
		background-repeat:no-repeat;
		background-position:0 center;
	}
	.document_modal_2{
		width:14px;
		background-image:url(../images/document_normal_index/shortcutdoc2.gif);
		background-repeat:no-repeat;
		background-position:0 center;
	}
	.document_modal_3{
		width:14px;
		background-image:url(../images/document_normal_index/shortcutdoc3.gif);
		background-repeat:no-repeat;
		background-position:0 center;
	}
	/* ls@2007-10-16 消除列表上视频底部的横线 */
	.grid_row{
		border-bottom:0px solid gray;
	}
	.video_grid_head_column{
		float:left;
		height:17px!important;
		height:29px;
		font-size:12px;
		text-align:center;
		padding:6 10;
		cursor:pointer;
	}
	.grid_row{
	height:132px!important;
	height:29px;
	cursor:pointer;
	}
	body, *{
	font-size: 12px;
}
</style>
</head>

<body>
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
							<div class="document_switcher" title="刷新当前视频列表" WCMAnt:paramattr="title:video_list.jsp.refreshList">
								<div class="wcm_pointer document_mode_active">
									<div class="document_mode_read" onclick='PageContext.RefreshList();'></div>
								</div>
							</div>
							<div id="divSearchClue" style="display: none; font-size: 14px; float: right; padding-left:5px; height: 30px; line-height: 30px; color: #789; font-weight: bold; padding-right: 10px;" WCMAnt:param="video_list.jsp.searchList">视频检索结果</div>
							<span id="pageinfo"></span>
							<!--PageInfo模板-->
							<textarea id="pageinfo_template" select="PageInfo" style='display:none'>
								<table height="28" border="0" cellpadding="0" cellspacing="0">
									<tr height="28">
										<td align="left" valign="center">
											<div height="28" class="pagefilter_container" id="pagefilter_container">
												<for select="Filters.Filter">
												<span class="pagefilter {#IsDefault;#Type,,PageFilter.filterClass}" pagefilter_type="{#Type}">
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
											<span class="video_grid_head_column" onclick="Grid.toggleAllRows();" WCMAnt:param="video_list.jsp.selectAll">全选</span>
										</DIV>
									</td>
								</tr>
								<tr>
									<td valign="top">
										<div id="grid_data" class="grid_data"></div>
										<!--视频列表数据模板-->
										<textarea id="objects_template" select="ViewDocuments" style='display:none'>
											{#HTML}
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
						<div id="list_navigator" class="list_navigator_div">
						</div>
<!-- <span onclick="Grid.toggleAllRows();" style="float:left;padding-left:20px;padding-bottom:5px;cursor:hand">全选</span> -->
					</td>
					<td class="list_view_border"></td>
				</tr>
			</tbody>
			</table>
		</td>
	</tr>
</table>
<div id="divNoObjectFound" style="display:none;">
	<div class="no_object_found" WCMAnt:param="video_list.jsp.notFound">不好意思, 没有找到符合条件的视频..-____-|||</div>
</div>

<script language="javascript">
<!--
function checkConvertingVideos() {	
	// 1. 获取所有还没转换完的视频(截图显示为"转换中")
	var arr = document.getElementsByClassName('imgConverting');
	// 2. 对每个没有转换完的视频, 每隔20秒查询其是否转换完
	arr.each(function(img) {
		//alert(img.id + "\nvideoToken: " + img.getAttribute("videoToken"));
		var curToken = img.getAttribute("videoToken");
		var curDocId = img.getAttribute("docid");
		var intervalID = setInterval("queryConvertStatus('" + curToken + "', '" + curDocId + "')", 20000);
		img.setAttribute("intervalID", intervalID);
		// ls@2007-11-19 15:53 立即查询一次，以disable掉转换失败的
		queryConvertStatus(curToken, curDocId);
	});
}

function queryConvertStatus(srcFileName, docid) {
    new Ajax.Request("./queryConverted.jsp?token=" + srcFileName + "&docId=" + docid,
					{method: "post", 
					onComplete:	gotConvertStatus.bind(this,srcFileName)
					});
}

function gotConvertStatus(srcFileName,request) {
	var resultStr = request.responseText;
	var json = eval('(' + resultStr + ')');
	if (json.THUMB) {
		var vToken = json.FILENAME;
		var curImg = document.getElementById("thumb_" + srcFileName);
		//var curArch = document.getElementById("va_" + docid + "_" + vToken);
		//alert("json.thumb: " + json.thumb + "\ncurImg: " + curImg + "\ncurArch: " + curArch);
		if (curImg) {
			curImg.src = "<%=VSConfig.getThumbsHomeUrl()%>/" + json.THUMB;
			curImg.onclick = function() {
				FloatPanel.open("./video/player.jsp?v=" + vToken, '播放视频', 640, 400);
			}
			clearInterval(curImg.getAttribute("intervalID"));
		}
	} else if (json.CONVERT_STATUS == "-1") {
		//alert("" + json.convertDone);
		var curImg = document.getElementById("thumb_" + srcFileName);
		curImg.src = "./convertFail.gif";
		clearInterval(curImg.getAttribute("intervalID"));
		var curDocId = curImg.getAttribute("docid");
		Grid.setSelectDisable(curDocId);
		var curCheckbox = document.getElementById("chk_" + curDocId);
		curCheckbox.disabled = true;
	}
}

function alertSaveConvertedFail(request) {
	alert("fail to saveConverted! responseText:\n" + request.responseText);
}
//-->
</script>

<script language="javascript">
<!--
	if (window.location.href.indexOf('disable_sheet=1') == -1) {
		PageContext.LoadPage();
	}
//-->
</script>

</body>
</html>