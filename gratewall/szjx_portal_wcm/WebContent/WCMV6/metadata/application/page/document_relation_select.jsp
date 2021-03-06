<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%
	int viewId = 0;
	String sChannelId = request.getParameter("channelid");
	if(sChannelId != null){
		int nChannelId = Integer.parseInt(sChannelId);
		IMetaViewEmployerMgr m_oMetaViewEmployerMgr = (IMetaViewEmployerMgr) DreamFactory
					.createObjectById("IMetaViewEmployerMgr");
		MetaView view = m_oMetaViewEmployerMgr.getViewOfEmployer(Channel.findById(nChannelId));
		if(view != null){
			viewId = view.getId();
		}
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script language="javascript">
<!--
	var index = window.location.href.lastIndexOf("/");
	var sHref = window.location.href.substr(0, index);
	href = sHref + "/../../";
	document.write("<base href='"  + href + "'/>");

	viewId = <%=viewId%>;
//-->
</script>
<title>document_list</title>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/wcm_list_abstract.css" rel="stylesheet" type="text/css" />
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
	//Import Mgrs
	$import('com.trs.wcm.domain.DocumentMgr');
	$import('com.trs.wcm.domain.PublishMgr');
</script>
<script src="../common/wcm_list_abstract.js" label="PageScope"></script>
<script src="application/js/document_relation_select.js" label="PageScope"></script>
<style label="PageScope">
	.doctitle{
		text-align:left;
		padding-left:0!important;
		padding-left:2px;
	}
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
	.grid{
		border-top:1px solid black;
	}
	/*??????*/
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
		background-position:0 bottom;
	}
	.document_modal_3{
		width:14px;
		background-image:url(../images/document_normal_index/shortcutdoc3.gif);
		background-repeat:no-repeat;
		background-position:0 bottom;
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
				<tr class="list_view">
					<td class="list_view_border"></td>
					<td valign="top">
						<DIV id='objects_grid' class="grid">
							<table class="wcm_table_layout" border=0 cellspacing=0 cellpadding=0>
								<tr style="height:29px">
									<td>
										<DIV class="grid_head" id="grid_head">
											<!--???????????????-->
											<SPAN class="grid_head_column" onclick="Grid.toggleAllRows();" style="width:50px">
												??????
											</SPAN>
											<SPAN class="grid_head_column doctitle" grid_dywidth="doctitle,1.0" grid_sortby="wcmdocument.doctitle">
												????????????
											</SPAN>
											<SPAN class="grid_head_column" style="width:60px" grid_sortby="wcmdocument.crtime">
												????????????
											</SPAN>
											<SPAN class="grid_head_column" style="width:40px" grid_sortby="wcmdocument.cruser">
												?????????
											</SPAN>
											<SPAN class="grid_head_column" style="width:30px;border-right:0;" grid_sortby="wcmdocument.docstatus">
												??????
											</SPAN>
										</DIV>
									</td>
								</tr>
								<tr>
									<td valign="top">
										<div id="grid_data" class="grid_data"></div>
										<!--Objects??????????????????-->
										<textarea id="objects_template" select="ViewDocuments.ViewDocument" style='display:none'>
											<for select="." blankRef="divNoObjectFound">
											<DIV class="grid_row {@DisableWhenIsCurrDocId(#DocId,1)}" grid_rowid="{#DocId}" right="{#Right}" ChannelId="{#DocChannel.Id}" currchnlid="{#ChnlId}" docid='{#DocId}' DocTitle='{#DocTitle}'>
												<SPAN class="grid_column" style="width:50px">
													<input type="checkbox" class="grid_checkbox" name="DocId" value="{#DocId}" {@DisableWhenIsCurrDocId(#DocId,2)}/>{$$INDEX}
												</SPAN>
												<SPAN class="grid_column_autowrap doctitle" style="font-size:14px" align="left">
													<span class="{@PageContext.isTopped(#Topped)}"></span><span class="document_modal_{#Modal}"></span>{#DocTitle}
												</SPAN>
												<SPAN class="grid_column" style="width:60px">
													{#CRTime}
												</SPAN>
												<SPAN class="grid_column" style="width:40px" title="{#CRUSER}">
													{#CRUSER}
												</SPAN>
												<SPAN class="grid_column" style="width:30px;border-right:0;">
													{#DOCSTATUS.Name}
												</SPAN>
											</DIV>
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
						<DIV style="float:left">
							<form style="margin:0" onsubmit="doSearch();return false;" target="">
							<select name="SearchKey" id="SearchKey" style="width:60px;">
								<option value="DocTitle">??????</option>
								<option value="CrUser">?????????</option>
								<option value="DocSourceName">??????</option>
							</select>
							<input type="text" class="input_text" style="width:60px;" name="SearchValue" id="SearchValue" value="">
							<input type="submit" class="input_text" style="cursor:pointer;" value="??????">
							</form>
						</DIV>
						<DIV id="list_navigator" class="list_navigator_div" style="float:right">
						</DIV>
					</td>
					<td class="list_view_border"></td>
				</tr>
				<tr style="height:1px;background:black;"><td></td><td></td><td></td></tr>
			</tbody>
			</table>
		</td>
	</tr>
</table>
<div id="divNoObjectFound" style="display:none;">
	<div class="no_object_found">????????????, ?????????????????????????????????..-____-|||</div>
</div>
<script language="javascript">
<!--
	function doSearch(){
		delete PageContext.params['DocTitle'];
		delete PageContext.params['CrUser'];
		delete PageContext.params['DocSourceName'];
		if($('SearchValue').value!=''){
			PageContext.params[$('SearchKey').value] = $('SearchValue').value;
			PageContext.refresh($toQueryStr(PageContext.params));
		}
	}
	PageContext.LoadPage();
//-->
</script>
</body>
</html>