<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="expected_error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.infoview.InfoViewDataHelper" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@include file="../../include/public_server_nologin.jsp"%>
<%
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document document = null;
	if(nDocumentId > 0){
		document  = Document.findById(nDocumentId);
	}
	if(document == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nDocumentId+"]的文档失败！");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="./css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	body, table{
		font-size: 10pt;
	}
	.td_tip{
		width: 100px;
		background: aliceblue;
		text-align: right;
	}
	.td_content{
		background: #fff;
		padding: 8px;
	}
	.flow_frame, .flow_frame_hot{
		height: 40px;
		line-height: 40px;
		border: 2px solid #FFBE69;
		background: #FFFDDB;
		text-align: center;
		padding-left: 5px;
		padding-right: 5px;
		color: #9E0000;
		font-weight: bold;
		margin-bottom: 5px;
		
	}
	.flow_frame_hot{
		border: 2px solid #FF6970;
		background: #FFE7DB;
	}
	.flow_frame_sep{
		height: 40px;
		line-height: 40px;
		width: 20px;
		background-image: url(./images/arrow.gif);
		background-repeat: no-repeat;
		background-position: center center;
		margin-bottom: 5px;
	}
</style>
</head>
<body>
	<table width="800" height="100%" border="0" align="center" cellpadding="2" cellspacing="0" style="padding: 10px;" bgcolor="#FFFFFF">
		<tr>
			<td valign="top" align="center" height="30px">
				<div style="text-align: right; margin-top: 10px;  width: 95%; border-bottom: 2px solid red">
					<a href="#" onclick="window.opener=null;window.close();return false;" onfocus="this.blur()" WCMAnt:param="document_detail.close">关闭窗口</a>
				</div>			
			</td>
		</tr>
		<tr>
			<td valign="top" style="padding: 2px; padding-top: 10px;" align="center">
				<table border="0" cellspacing="1" cellpadding="5" width="90%" bgcolor="#BDBDBD">
				<tbody>
					<tr>
						<td class="td_tip" WCMAnt:param="document_detail.search">查询编号</td>
						<td class="td_content"><%=CMyString.transDisplay(document.getPropertyAsString("RandomSerial"))%></td>
					</tr>
					<tr>
						<td class="td_tip" WCMAnt:param="document_detail.post">提交人</td>
						<td class="td_content"><%=CMyString.transDisplay(InfoViewDataHelper.getFieldValue(document, "PostUser"))%></td>
					</tr>
					<tr>
						<td class="td_tip" WCMAnt:param="document_detail.post.time">提交时间</td>
						<td class="td_content"><%=CMyString.transDisplay(InfoViewDataHelper.getFieldValue(document, "CrTime"))%></td>
					</tr>
					<tr>
						<td class="td_tip" WCMAnt:param="document_detail.title">主题</td>
						<td class="td_content"><%=CMyString.transDisplay(InfoViewDataHelper.getFieldValue(document, "主题"))%></td>
					</tr>
					<tr>
						<td class="td_tip" WCMAnt:param="document_detail_53.jsp.consultcontent">咨询内容</td>
						<td class="td_content"><%=CMyString.transDisplay(InfoViewDataHelper.getFieldValue(document, "内容"))%></td>
					</tr>
					<tr>
						<td class="td_tip" WCMAnt:param="document_detail_53.jsp.handlestate">处理状态</td>
						<td class="td_content" id="tdFlowFrames" align="center">
						</td>
					</tr>
				</tbody>
				</table>
			</td>
		</tr>
	</table>
<script language="javascript">
<!--
	var sPath = '<%=document.getPropertyAsString("FlowOperationMaskEnum")%>';
	drawFlowPath(sPath, '<%=document.getPropertyAsString("FlowOperationMark")%>');
	document.title = '<%=document.getPropertyAsString("DocTitle")%>';
	function drawFlowPath(_sPath, _sCurrStatus){
		var sPath = _sPath;
		if(sPath.length == 0) {
			return;
		}
		var parts = sPath.split(',');
		if(parts.length <= 2) {
			return;
		}
		//else
		var arContent = [];
		for (var i = 1; i < parts.length - 1; i++){
			var sName = parts[i];
			arContent.push(getSimpleFrameHtml(sName, (sName == _sCurrStatus)));
		}
		if(!(arContent.length > 0)) {
			return;
		}
		var sContent = arContent.join(getSepFrameHtml());

		document.getElementById('tdFlowFrames').innerHTML = sContent;
	}
	function getSimpleFrameHtml(_sName, _bIsHot){
		var sClass = (_bIsHot == true ? 'flow_frame_hot' : 'flow_frame');
		return '<span class="' + sClass + '">' + _sName + '</span>'
	}
	function getSepFrameHtml(){
		return '<span class="flow_frame_sep">&nbsp;</span>';
	}
//-->
</script>
</body>
</html>