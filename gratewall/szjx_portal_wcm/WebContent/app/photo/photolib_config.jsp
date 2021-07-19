<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@include file="../include/public_server.jsp"%>
<%
	//构造loadLibConf方法参数
	IImageLibConfig m_libConf = (IImageLibConfig) DreamFactory
                .createObjectById("IImageLibConfig");
	int[] sizes = m_libConf.getScaleSizes();
	int size = sizes[1];
	Boolean firstInstall = Boolean.valueOf(!m_libConf.isInstalled());
	String SupprotExt = m_libConf.getDefaultConvertImageFileExt();
	int len = sizes.length;
    String wmSize = null;

%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS=""  xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="photolib_config.jsp.title">图片库配置</title>
<link href="../css/add_edit.css" rel="stylesheet" type="text/css" />
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<style type="text/css">		
	.input_text{
		width:150px;
		height:20px;
		border:1px solid green;
		margin:0 1px;
	}
	.input_editablefalse{
		color:gray;
	}
	label{
		font-weight:bold;
		padding-right:5px;
	}
	.tablehead{
		font-size:12px;
		font-weight:bold;		
	}
	.fieldContainer{
		
		margin:15px 20px;
	}
	.ext-safari .fieldContainer{
		width:500px;
		margin:5px 10px;
	}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/photo.js"></script>
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
<script language=JavaScript src="photolib_config.js"></script>
</head>
<body>
<script language="javascript">
<!--
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'saveConf',
			name : wcm.LANG.PHOTO_CONFIRM_1 || '确定'
		}],
		size : [680, 280]
	};		
//-->
</script>
<form id="form_edit" name="form_edit" method="post" enctype="multipart/form-data" action="">	
	<div id="area_holder" style="padding:20px;margin-left:30px;">
		<input type="hidden" name="ScaleSizes" id="ScaleSizes" value="">
		<input type="hidden" name="ScaleSizeDescs" id="ScaleSizeDescs" value="">
		<input type="hidden" name="WatermarkSizes" id="WatermarkSizes" value="">
		<span style="float:left">
			<label WCMAnt:param="photolib_config.jsp.supportPicType">支持的图片格式</label>
			<input type="text" name="SupportedExt" id="SupportedExt" value="<%=CMyString.join(m_libConf
                .getSupportImageFileExt(), ",")%>" class="input_text" disabled></input>
		</span>
		<span style="padding-left:90px">
			<label WCMAnt:param="photolib_config.jsp.picTransType">BMP默认转换格式</label>
			<select name="DefaultConvertType" id="DefaultConvertType">			
				<option value="gif" <%=isDefaultType("gif",SupprotExt)%>>gif</option>
				<option value="jpg" <%=isDefaultType("jpg",SupprotExt)%>>jpg</option>
				<option value="bmp" <%=isDefaultType("bmp",SupprotExt)%> WCMAnt:param="photolib_config.jsp.noChange">不转换</option>
			</select>
		</span>
		<fieldset class="fieldContainer"><legend style="padding:5px" WCMAnt:param="photolib_config.jsp.picSet">图片设置</legend>
		<table id="imagesize_table">
			<tr>
				<td class="tablehead" WCMAnt:param="photolib_config.jsp.picSize">图片尺寸</td>
				<td class="tablehead" WCMAnt:param="photolib_config.jsp.waterMarkSize">水印尺寸</td>
				<td class="tablehead" WCMAnt:param="photolib_config.jsp.describe">描述</td>
			</tr>
			<tr><td colspan="3" width="200px">
				<div style="overflow-y:auto;height:135px;width:500px"><table>
				<%	
					 for (int i = 1; i < len; i++) {
						 size = sizes[i];
						 wmSize = String.valueOf(m_libConf.getWaterMarkSize(size));

				%>
					<tr name="imagesize_setter" id="imagesize_setter">
						<td>
							<input type="text" imgsize="true" name="ImageSize" id="ImageSize" value="<%=String.valueOf(size)%>" 
									class="input_text input_editable<%=firstInstall%>" contentEditable="<%=firstInstall%>"
									<%=isFirstInsall(firstInstall)%>
							>
							</input>
						</td>
						<td>
							<input type="text" mark="true" name="WatermarkerSize<%=i%>"  id="WatermarkerSize" value="<%=wmSize%>" class="input_text" validation="type:'int',value_range:'0,<%=size%>',required:'true',desc:'水印尺寸',showid:'validation_tip'" validation_desc="水印尺寸" WCMAnt:paramattr="validation_desc:photolib_config.jsp.waterMarkerSize"
							></input>
						</td>
						<td>
							<input type="text" desc="true" name="ImageSizeDesc<%=i%>" id="ImageSizeDesc" value="<%=m_libConf.getScaleDescAt(i)%>" class="input_text"
									validation="type:'string',required:'true',max_len:'50',desc:'图片描述',showid:'validation_tip'" validation_desc="图片描述" WCMAnt:paramattr="validation_desc:photolib_config.jsp.picDescribe"
							></input>
						</td>
						<td>
							<a href="#" onclick="removeImageSize(this)" WCMAnt:paramattr="title:photolib_config.jsp.titleAttr" title="删除这个设置"
								style="display:<%=displayAble(i,firstInstall)%>" WCMAnt:paramattr="title:photolib_config.jsp.deleteSet">
								<img src="../images/icon/Delete.png" border="0">
							</a>
						</td>
					</tr>
				<%
					 }
				%>
				</table></div>
			</td></tr>
		</table>
		</fieldset>
		<div style="margin-top:5px">
			<span>
			<a href="#" onclick="insertImageSize();" style="display:<%=displayAble(2,firstInstall)%>">
				<span WCMAnt:param="photolib_config.jsp.addConfig">添加设置</span>
			</a>
			</span>
			<span id="validation_tip" style="pading-left:40px"></span>		
		</div>
	</div>	
</form>
</body>
</html>
<%!
	private String isDefaultType(String _type, String _default) throws WCMException {
        if (_type.equals(_default)){
			return "selected";
		}

		return "";
	}
	private String isFirstInsall(Boolean _editable) throws WCMException {
        if(String.valueOf(_editable).equals("true")){
			return "validation=\"type:'int',required:'true',showid:'validation_tip'\"";
		}
		return "";
	}
	private String displayAble(int _ix, Boolean _installed) throws WCMException {
		if(String.valueOf(_installed).equals("true") && _ix > 1){	
				return "inline";		
		}

		return "none";
	}

%>