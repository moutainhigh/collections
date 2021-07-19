<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>

<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	Watermark currWatermark = null;
	String sFileName = "";
	FilesMan currFilesMan = FilesMan.getFilesMan();
	if(nObjectId !=0){
		currWatermark = Watermark.findById(nObjectId);
		if(currWatermark == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("watermark_addedit.jsp.watermark_notfound", "没有找到ID为[{0}]的水印!"), new int[]{nObjectId}));
		}
		sFileName = currWatermark.getWMPicture();
		sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_HTTP) + sFileName;
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS=""  xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>add or edit a certain contentextfield</title>
<style type="text/css">	
	.input_file{		
		width:280px;
		height:20px;
		border:1px solid black;	
		margin-top:2px;
	}
	.input_text{
		width:205px;
		height:20px;
		border:1px solid green;
		margin:0 1px;
	}
	.invalid_file{
		border:1px solid red;
	}
	label{
		font-weight:bold;
		padding-right:5px;
	}
</style>
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/watermark.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
	<!--FloatPanel Inner Start-->
	<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<!--wcm-dialog start-->
	<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
	<!--wcm-dialog end-->
	<!--AJAX-->
	<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!-- Validator-->
	<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<!--others-->
	<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
	<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
	<script label="PageScope" src="watermark_addedit.js"></script>
</head>
<body>
<script language="javascript">
<!--	
	window.m_fpCfg = {
			m_arrCommands : [{
				cmd : 'saveWaterMark',
				name : wcm.LANG.WATERMARK_PROCESS_7 || '确定'
			}],
			size : [680, 200]
		};			
//-->
</script>
<form id="addEditForm" name="addEditForm" method="post" enctype="multipart/form-data" action="" onsubmit="return false;">	
	<div id="area_holder" style="padding:20px">
		<input type="hidden" id="LibId" name="LibId" value="<%=nObjectId==0?0:currWatermark.getLibId()%>"></input>
		<input type="hidden" id="NotQuickEdit" name="NotQuickEdit" value="true"></input>
		<input type="hidden" name="ObjectId" value="<%=nObjectId==0?0:currWatermark.getId()%>"></input>
		<div style="padding:20px">
			<div>
				<label for="wmname" WCMAnt:param="watermark_addedit.jsp.wmname">水印名称：</label>
				<input type="text" name="wmname" value="<%=nObjectId==0?"":CMyString.filterForHTMLValue(currWatermark.getWMName())%>" id="wmname" class="input_text" validation="required:'true',type:'string',max_len:'50',desc:'水印名称'" validation_desc="水印名称" WCMAnt:paramattr="validation_desc:watermark_addedit.jsp.watermarkName"/>
			</div>
			<div style="padding-top:20px">
				<label for="wmpicture" WCMAnt:param="watermark_addedit.jsp.filename">水印图片：</label>
				<input type="file" id="wmpicture_in" name="wmpicture_in" value="<%=nObjectId==0?"":sFileName%>" class="input_file"></input>			
				<input type="hidden" name="wmpicture" id="wmpicture" value="<%=nObjectId==0?"":currWatermark.getWMPicture()%>"></input>
			</div>
			<div id="pic_showarea" style="display:<%=nObjectId==0?"none":""%>;float:right;margin-top:-70px;margin-right:20px">
				<img src="<%=nObjectId==0?"":sFileName%>" title="<%=nObjectId==0?"":currWatermark.getWMPicture()%>" style="border:0;cursor:pointer" id="wmpic">
			</div>		
		</div>		
	</div>	
</form>
</body>
</html>