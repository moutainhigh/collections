<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
 
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@include file="../include/public_server.jsp"%>
<%
	int nObjectId = currRequestHelper.getInt("siteId", 0);
	WebSite oWebSite = WebSite.findById(nObjectId);
	String sFilesize = CMyString.filterForHTMLValue(oWebSite.getAttributeValue("filesize"));
	String sImagesize = CMyString.filterForHTMLValue(oWebSite.getAttributeValue("imagesize"));
	String sImageindocsize = CMyString.filterForHTMLValue(oWebSite.getAttributeValue("imageindocsize"));
	String sFlashsize = CMyString.filterForHTMLValue(oWebSite.getAttributeValue("flashsize"));
	String sVideosize = CMyString.filterForHTMLValue(oWebSite.getAttributeValue("videosize"));
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> New Document </title>
	<link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css">
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<style type="text/css">
	html,body{
		padding:0px;
		margin:0px;
		height:100%;
		width:100%;
	}
	body{
		background: #ffffff;
		font-size:12px;
	}
	.dvbox{
		padding-top:8px;
		padding-left:8px;
	}
	.dvbox input{
		width: 120px;
		border:1px solid #b4b4b4; 
	}
	.attr_name{
		font-weight: normal;
		width: 220px;
		margin-bottom: 5px;
		padding-right: 5px;
		display:inline-block;
	}
	</style>
	<link rel="stylesheet" type="text/css" WCMAnt:locale="website_set_doc_appendix_size_$locale$.css" href="website_set_doc_appendix_size_cn.css" /></head>
</head>
<script src="../js/easyversion/lightbase.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/data/locale/template.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../js/easyversion/ajax.js"></script>
<script src="../js/easyversion/basicdatahelper.js"></script>
<script src="../js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!--wcm-dialog end-->
<script language="javascript">
<!--
	window.m_cbCfg = {
		btns : [
			{
				id : 'btnSubmit',
				text : '确定',
				cmd : function(){
					return submit(); 
				}
			},{
				text : '取消'
			}
		]
	};
	function submit(){
		if(!ValidationHelper.doValid('formData')) {
			return false;
		}
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var PostDataUtil = com.trs.web2frame.PostData;
		var postdata = PostDataUtil.form('formData', function(m){return m;});
		oHelper.call('wcm6_website', 'save', postdata, true, function(){
			 var cb = wcm.CrashBoarder.get(window);
			 cb.close();
		});
		return false;
	}
	Event.observe(window, 'load', function(){
		ValidationHelper.initValidation();
	});
//-->
</script>
<body>
<div id="formData" name="formData">
	<input type="hidden" name="objectId" id="objectId" value="<%=nObjectId%>">
	<div class="dvbox">
		<span class="attr_name" WCMAnt:param="website_set_doc_appendix_size.jsp.doc_appendix_size_a">文档文件附件大小：</span>
		<span><input type="text" name="filesize" id="filesize" WCMAnt:paramattr="validation_desc:website_set_doc_appendix_size.jsp.doc_appendix_size" validation="type:'int',required:'false', min:'1', desc:'文档文件附件大小',showid:'pTitle1'" validation_desc="文档文件附件大小" value="<%=sFilesize%>" isattr="1"/>KB<span id="pTitle1"></span></span>
	</div>
	<div class="dvbox">
		<span class="attr_name" WCMAnt:param="website_set_doc_appendix_size.jsp.pic_appendix_size_a">文档图片附件大小：</span>
		<span><input type="text" name="imagesize" id="imagesize" WCMAnt:paramattr="validation_desc:website_set_doc_appendix_size.jsp.pic_appendix_size" validation="type:'int',required:'false', min:'1', desc:'文档图片附件大小',showid:'pTitle2'" validation_desc="文档图片附件大小" value="<%=sImagesize%>" isattr="1"/>KB<span id="pTitle2"></span></span>
	</div>
	<div class="dvbox">
		<span class="attr_name" WCMAnt:param="website_set_doc_appendix_size.jsp.insertinto_doc_appendix_size">文档插入图片大小：</span>
		<span><input type="text" name="imageindocsize" id="imageindocsize" validation="type:'int',required:'false', min:'1', desc:'文档插入图片大小',showid:'pTitle3'" validation_desc="文档插图大小" WCMAnt:paramattr="validation_desc:appendix_size.pic" value="<%=sImageindocsize%>" isattr="1"/>KB<span id="pTitle3"></span></span>
	</div>
	<div class="dvbox">
		<span class="attr_name" WCMAnt:param="website_set_doc_appendix_size.jsp.insertinto_doc_flash_a">文档插入flash大小：</span>
		<span><input type="text" name="flashsize" id="flashsize" WCMAnt:paramattr="validation_desc:website_set_doc_appendix_size.jsp.insertinto_doc_flash" validation="type:'int',required:'false', min:'1', desc:'文档插入flash大小',showid:'pTitle4'" validation_desc="文档插入flash大小" value="<%=sFlashsize%>" isattr="1"/>KB<span id="pTitle4"></span></span>
	</div>
	<div class="dvbox">
		<span class="attr_name" WCMAnt:param="website_set_doc_appendix_size.jsp.insertinto_doc_audio_vedio_a">文档插入视频或音频大小：</span>
		<span><input type="text" name="videosize" id="videosize" WCMAnt:paramattr="validation_desc:website_set_doc_appendix_size.jsp.insertinto_doc_audio_vedio" validation="type:'int',required:'false', min:'1', desc:'文档插入视频或音频大小',showid:'pTitle5'" validation_desc="文档插入视频或音频大小" value="<%=sVideosize%>" isattr="1"/>KB<span id="pTitle5"></span></span>
	</div>
</div>
</body>
</html>