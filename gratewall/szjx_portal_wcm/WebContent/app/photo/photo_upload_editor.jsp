<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	Watermarks currWatermarks = null;
	Watermark newWatermark = null;
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_upload_editor.jsp.channelId_notfound", "没有找到ID为[{0}]的栏目!"), new int[]{nChannelId}));
	}
	String channelName = currChannel.getDesc();
	int nSiteId = currChannel.getSiteId();
	WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
	filter.addSearchValues(0, nSiteId);
	try{
		currWatermarks = Watermarks.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("photo_upload_editor.jsp.label.fail2get_watermark_coll", "获取站点水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns:TRS_UI>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title WCMAnt:param="photo_upload_editor.jsp.title">TRS WCM 图片库图片上传</title>
	<style>
		.simpletab{
			padding-bottom:23px;
			border-bottom:1px solid gray;
			font-size:12px;
		}
		div.simpletab_item{
			float:left;
			width:60px;
			height:20px;
			background:gray;
			margin-left:4px;
			border:1px solid gray;
			padding:4px;
			text-align:center;
		}
		.simpletab_item a,.simpletab_item a:link,.simpletab_item a:visited,.simpletab_item a:hover{
			text-decoration: none;
			color:white;
		}
		div.simpletab_item_active{
			float:left;
			width:60px;
			height:21px;
			background:#ffffff;
			margin-left:4px;
			border:1px solid gray;
			border-bottom:0;
			padding:4px;
			padding-bottom:5px;
			text-align:center;
		}
		.simpletab_item_active a,.simpletab_item_active a:link,.simpletab_item_active a:visited,.simpletab_item_active a:hover{
			text-decoration: none;
			color:black;
		}
		.simpletab_item_before{
			float:left;
			padding-left:20px;
		}
		.message {
			font-family: "宋体", "Arial", "Courier New";
			font-size: 12px;
			float:left;
			height:18px;
			line-height:18px;
		}
		.input_file{
			float:left;
			width:200px;
			height:20px;
			border:1px solid black;	
			margin-top:2px;
		}
		.input_text{
			height:20px;
			border:1px solid black;
			margin:0 1px;
		}
		.input_button{
			width:60px;
			height:20px;
			border:1px solid black;
		}
		.input_select{
			height:16px;
			border:1px solid black;
			margin:0 1px;
		}
		.preview_head{
			font-weight:bold;
			color:green;
			font-size:14px;
		}
		.preview_data{
			line-height:130%;
			width:260px;
			height:207px;
			overflow:auto;
			border:1px solid black;
			padding:3px;
		}
		.ui_select{
			border:1px solid green;
			text-indent:5px;
			border-width:0 0 1px;
		}
		.ui_select_selected,.ui_select_hover{
			border:1px solid black;
		}
		.attr_name{
			height:22px;
			padding-top:4px;
			font-weight:bold;						
		}
		fieldset{			
			padding-left:5px;
		}
		legend{
			font-weight:bold;
			color:#039;
			padding:5px;
		}
		.invalid_file{
			border:1px solid red;
		}
	</style>
	<script src="../../app/js/runtime/myext-debug.js"></script>
	<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../../app/js/data/locale/photo.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
	<!--wcm-dialog start-->
	<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
	<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
	<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="../js/easyversion/resource/processbar.css">
	<!--wcm-dialog end-->
	<!--AJAX-->
	<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
	<!--CrashBoard-->
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
	<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
	<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
	<!-- Validator-->
	<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
	<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
	<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
	<script src="../js/easyversion/processbar.js"></script>
	<!--others-->
	<script src="../js/source/wcmlib/util/YUIConnection.js"></script>	
	<script src="photo_upload_editor.js"></script>
<style type="text/css">
	body{
		background:#ccc;
	}
	.quoto_kind{
		height:100px;
		width:150px;
		border:1px solid gray;
	}
body td{
	font-size:12px;
}
.attr_table{
	width:100%;
	overflow:auto;
	float:left;
}
.add_quotekind{
	float:right;
	width:17px;
	height:20px;
	margin-right:5px;
	background:url(../images/photo/add_quotechannel.gif) no-repeat center center;
}
.remove_quotekind{
	float:right;
	width:17px;
	height:20px;
	margin-right:15px;
	background:url(../images/photo/remove_quotechannel.gif) no-repeat center center;
}
.attr_quotekind_head{
	background:gray;
	color:white;
	height:22px;
}
.attr_quotekind_row{
	background:#FFFFFF;
	height:20px;
}
.attr_quotekind_column{
	overflow:hidden;
	text-align:left;
}
.button{
	margin:0 5px;
	width:80px;
	border:1px solid gray;
	height:20px;
	text-align:center;
	padding-top:2px;
}
.ext-safari .chromeTD{
	height:20px;
}
.ext-safari .chromeDefatultTD{
	height:52px;
}
	</style>
</head>
<body>
<table width="500"  border="0" style="font-size:12px">
  <tr>
    <td width="200" valign="top">
	<fieldset style="width:215px;height:340px;"><legend WCMAnt:param="photo_upload_editor.jsp.picSel">选择图片</legend>
		<table width="100%" border="0" height="100%">        
		<tr>
		  <td style="font-size:12px" valign="top" height="40">
		    <span WCMAnt:param="photo_upload_editor.jsp.picNum">上传图片数目</span><input type="text" class="input_text" style="width:20px;margin:0 5px" id="picnum" value="1" validation="type:'int',min:'1',max:'10',desc:'上传数目'" validation_desc="上传数目" WCMAnt:paramattr="validation_desc:photo_upload_editor.jsp.uploadNum"/><input type="button" class="input_button" style="width:40px;" value="OK" id="picnumbtn" /><br>
			<label for="batchupload" style="margin-left:-2px;cursor:pointer"><input type="checkbox" id="batchupload"/><span WCMAnt:param="photo_upload_editor.jsp.groupImport">批量导入(*.zip)</span></label>
		  </td>
		</tr>
		<tr>
			<td valign="top">
				<div class="message" WCMAnt:param="photo_upload_editor.jsp.selLocalFiles">选择本地图片文件：</div><br>
				<div>
				<form id="form_pic" name="fm_pic" style="margin:0;" enctype='multipart/form-data' onsubmit="Ok();return false;">
					<input type="file" class="input_file" id="PicUpload" name="PicUpload" onchange="this.className='input_file';"/>
				</form>
				</div>
				<form id="form_imageInfo">
					<input type="hidden" name="MainKindId" id="MainKindId" value="0"/>
					<input type="hidden" name="OtherKindIds" id="OtherKindIds" value=""/>
					<input type="hidden" name="WatermarkFile" id="WatermarkFile" value="0"/>
					<input type="hidden" name="WatermarkPos" id="WatermarkPos" value=""/>	
					<input type="hidden" name="UploadedFiles" id="UploadedFiles" value=""/>
					<input type="hidden" name="SourceFiles" id="SourceFiles" value=""/>
					<input type="hidden" name="BatchMode" id="BatchMode" value="0"/>
					<input type="hidden" name="BmpConverType" id="BmpConverType" value=""/>
				</form>
			</td>			
		</tr>	     
      </table>
	</fieldset>
	</td>
    <td width="300" valign="top">
	<fieldset style="width:300px;height:340px;"><legend WCMAnt:param="photo_upload_editor.jsp.picAttribute">图片属性</legend>
		<table style="font-size:12px;table-layout:fixed" width="100%" border="0" height="100%">       
        <tr>
          <td colspan="2" height="22">
			<span class="attr_name" WCMAnt:param="photo_upload_editor.jsp.mainClass">主分类：</span>
			<span id="_mainkind" _siteId="<%=nSiteId%>" style="line-height:22px;height:22px;white-space:nowrap;overflow:hidden;width:220px;"><%=channelName%></span>
          </td>
        </tr>		
		<tr height="44">
          <td colspan="2">
			<span class="attr_name" WCMAnt:param="photo_upload_editor.jsp.bmpTransStyle">BMP图片转换格式：</span><br>
			<select onchange="convertBmp(this);" id="BmpConverTypeSelect">
				<option value="bmp" WCMAnt:param="photo_upload_editor.jsp.noChange">不转换</option>
				<option value="gif">GIF</option>
				<option value="jpg">JPG</option>
			</select>
          </td>
        </tr>
		<tr>
          <td colspan="2" height="136">
			<div style="width:100%;height:22px;">
				<span class="attr_name" style="width:120px;height:22px;float:left;" WCMAnt:param="photo_upload_editor.jsp.quetoOtherClass">引用到其它分类：</span>
				<span class="remove_quotekind wcm_pointer" onClick="RemoveSelectedQuoteKind();" style="cursor:pointer" title="移除选中的引用分类" WCMAnt:paramattr="title:photo_upload_editor.jsp.remove"></span>
				<span class="add_quotekind wcm_pointer" onClick="AddQuoteKind();" style="cursor:pointer" title="增加引用分类" WCMAnt:paramattr="title:photo_upload_editor.jsp.add"></span>
			</div>
			<div class="attr_table" style="height:110px;">
				<TABLE cellSpacing=1 cellPadding=2 width="100%" style="height:110px" bgColor="black" border=0 style="font-size:12px;" id="QuoteKinds" ChannelIds="">
				<THEAD>
					<TR align="center" valign="middle" class="attr_quotekind_head">
						<TD style="width:24px;cursor:pointer;" onclick="SelectAllQuoteKinds();" WCMAnt:param="photo_upload_editor.jsp.selAll" class="selAll">全选</td>
						<TD WCMAnt:param="photo_upload_editor.jsp.className">分类名称</TD>
					</TR>
				</THEAD>
				<TBODY  id="otherkinds_holder" style="cursor:pointer;">
					<TR  class="attr_quotekind_row" valign='middle'>
						<TD style="height:20px;">
							<input type='checkbox' name='cb_quotekind' value='0' disabled style="height:20px;"/>
						</TD>
						<TD class='attr_quotekind_column'>
						</TD>
					</TR>
				</TBODY><TBODY>
					<TR bgColor="white">
						<TD colspan="2" class="chromeDefatultTD">
						</TD>
					</TR>
				</TBODY>
				</TABLE>
			</div>
			<textarea id="template_otherkinds" style="display:none">
				<for select="Channels.Channel">
					<TR class='attr_quotechanel_row' valign='middle'>
						<TD style="height:20px;">
							<input type='checkbox' name='cb_quotekind' value='{#ChannelId}' disabled/>
						</TD>
						<TD class='attr_quotekind_column'>{#ChnlDesc}
						</TD>
					</TR>
				</for>
			</select>
			</textarea>
          </td>
        </tr>
		<tr valign="top" style="height:100px;">
          <td width="160">
			<span class="attr_name" WCMAnt:param="photo_upload_editor.jsp.selectWaterMark">选择水印：</span>
			<span id="watermarks">
			<select id="selwatermark" onchange="addWaterMark(this);" style="width:120px;">
					<option value="-1" WCMAnt:param="photo_upload_editor.jsp.noWaterMark">--不添加水印--</option>
					<%
						if(nSize>0){
							for(int i=0;i<nSize;i++){
								newWatermark = (Watermark)currWatermarks.getAt(i);
								String sFileName = newWatermark.getWMPicture();
								sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_HTTP) + sFileName;
					%>
							<option value="<%=newWatermark.getId()%>" _picsrc="<%=sFileName%>" _picfile="<%=newWatermark.getWMPicture()%>"><%=newWatermark.getWMName()%></option>
					<%
							}
						}
					%>
			</select>
			</span>	
			<div id="div_watermarkpos" style="display:none">
				<span class="attr_name" title="点击全选" style="cursor:pointer" onclick="selectAllPos()" WCMAnt:param="photo_upload_editor.jsp.waterMarkPosition">水印位置：</span><br>
				<label for="LT" style="height:22px;"><span WCMAnt:param="photo_upload_editor.jsp.leftTop">左上</span><input type="checkbox" id="LT" value="1"/></label>
				<label for="CM" style="height:22px;"><span WCMAnt:param="photo_upload_editor.jsp.center">居中</span><input type="checkbox" id="CM" value="2"/></label>
				<label for="RB" style="height:22px;"><span WCMAnt:param="photo_upload_editor.jsp.rigntDown">右下</span><input type="checkbox" id="RB" value="3" checked/></label>
			</div>	
          </td>
		  <td>
			<img src=""  style="display:none" id="watermarkpic">
		  </td>
        </tr>
      </table>
	</fieldset>
	</td>
  </tr>
  <tr>
  	<table border=0 cellspacing=0 cellpadding=0 width="100%">
	<tbody>
		<tr height="30">
			<td valign="middle" align="center">
				<button type="button" onclick="Ok();" class="button" WCMAnt:param="photo_upload_editor.jsp.ok">确定</button><button type="button" onclick="window.close();" class="button" WCMAnt:param="photo_upload_editor.jsp.cancel">取消</button>
			</td>
		</tr>
	</tbody>
	</table>
	</tr>
</table>
</body>
</html>