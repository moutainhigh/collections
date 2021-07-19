<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接收页面参数
	Document currDocument = null;
	ChnlDoc currChnlDoc =null;
	String sDocs = currRequestHelper.getString("objectids");	
	String[] str = sDocs.split(",");
	int[] intStr = new int[str.length]; 
	//获取整数型文档集合
	for(int i=0;i<str.length;i++){ 
		intStr[i] = Integer.parseInt(str[i]); 
	} 
	int nSize = intStr.length;
%>
<HTML>
<HEAD>
<TITLE>display of document quoted info before operators</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/photo.js"></script>
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/TempEvaler.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!-- CarshBoard Inner Start -->
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<script src="photo_info.js"></script>
<script>
	window.m_cbCfg = {
		btns : [
			{
				id : 'btnSubmit',
				text : wcm.LANG.PHOTO_CONFIRM_1 ||'确定',
				cmd : function(){
					closeframe(true);
				}
			},
			{
				id : 'btnCancel',
				text : wcm.LANG.PHOTO_CONFIRM_2 ||'取消',
				cmd : function(){
				}
			}
		]
	}
</script>
<style type="text/css">
	.area_errInfo{
		font-size: 12px;
		width: 100%;
		overflow: auto;
		border: 0;
		padding: 3px;
	}
	.alertion_title{
		color: red;
	}
	body{
		background: #ffffff;
		padding:0px;
		margin:0px;
		font-family: Georgia;
	}
	button{
		color: #1571D5;
		border: solid 1px silver;
		padding-left: 5px;
		padding-right: 5px;
		height: 20px;
		line-height: 17px;
		width: 70px;
		text-align: center;
		background: #ffffff;
	}
	li{
		line-height: 18px;
		list-style-type: decimal;
		margin-left: -10px;
		margin-top: 5px;
		border-bottom: solid 1px #efefef;
	}
</style>
</HEAD>

<BODY style="overflow:hidden;">
<div style="padding-top:5px;">
	<table border=0 align="center" cellspacing=1 cellpadding=3 style="font-size:12px; width: 100%; background: silver;">
	<tbody>
		<tr>
			<td style="width: 80px; height: 200px; background: #ffffff" align="center">
				<img id="imClue" src="../images/include/7.gif">
			</td>
			<td style="background: #ffffff;" align="left" valign="top">
				<div style="width: 100%; height: 30px; line-height: 30px; overflow: visible; border-bottom: 1px solid aliceblue; font-size: 14px; padding-left: 10px; font-weight: normal;">
					<div id="divOptionsDesc">
						<span class="alertion_title" id="spOperation"></span>
					</div>
				</div>
				<div style="height: 160px; overflow-y : auto; overflow-x :hidden">
					<div style="width: 100%; padding: 3px;" id="divDocInfo"></div>
				</div>
				<textarea id="txtDocInfo" select="." style="display: none;">
				<ul>
					<for1 select=".">
					<li>
						<span WCMAnt:paramattr="title:photo_info.jsp.picid" title="图片ID={#docid}">
							{#docTitle}, 
						</span>
						<span WCMAnt:param="photo_info.jsp.categories">隶属于分类</span>
						<span>
							[<span class="alertion_title">{#channel}</span>]
						</span>
						<span style="display: {#refChannels,none}">
							<span WCMAnt:param="photo_info.jsp.classified">,被分类</span>
							<for2 select="refChannels">
								[<span class="alertion_title">{#.}</span>]
							</for2>
							<span WCMAnt:param="photo_info.jsp.reference">引用</span>
						</span>
						<span style="display: {#refdoccontents,none}">
							<span WCMAnt:param="photo_info.jsp.doc">,被文档</span>
							<for2 select="refdoccontents">
								[<span class="alertion_title">{#.}</span>]
							</for2>
							<span WCMAnt:param="photo_info.jsp.refercon">做为内容引用</span>
						</span>
						<span style="display: {#refdocappendixs,none}">
							<span WCMAnt:param="photo_info.jsp.doc">,被文档</span>
							<for2 select="refdocappendixs">
								[<span class="alertion_title">{#.}</span>]
							</for2>
							<span WCMAnt:param="photo_info.jsp.accessories">做为附件引用</span>
						</span><br>
						<span style="color: red; font-weight: bold; display: {#restore_flag,none}">{@getOperationDesc(#restore_flag)}</span>
					</li>
					</for1>
				</ul>
				</textarea>
			</td>
		</tr>
	</tbody>
	</table>
</div>
</BODY>
</HTML>