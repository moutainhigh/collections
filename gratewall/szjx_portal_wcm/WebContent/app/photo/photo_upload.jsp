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
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	String channelName = null;
	Channel currChannel = null;
	Watermarks currWatermarks = null;
	Watermark newWatermark = null;
	if(nChannelId == 0){
		channelName = LocaleServer.getString("photo_upload.label.choose", "选择");
	}else{
		currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_upload.jsp.channelId_notfound", "没有找到ID为[{0}]的栏目!"), new int[]{nChannelId}));
		}
		channelName = currChannel.getDesc();
		nSiteId = currChannel.getSiteId();
		//检查栏目的自定义编辑页面
		String sContentAddEditPage = CMyString.showNull(currChannel.getPropertyAsString("ContentAddEditPage"));
		if(!(sContentAddEditPage.equals("") 
			|| sContentAddEditPage.equals("../photo/photo_upload.jsp") 
			|| sContentAddEditPage.startsWith("../photo/photo_upload.jsp?"))){
			String sTarget = "";
			if(sContentAddEditPage.indexOf("?")==-1){
				sTarget = sContentAddEditPage+"?"+request.getQueryString();
			}
			else{
				sTarget = sContentAddEditPage+"&"+request.getQueryString();
			}
			//防止CRLF注入，去除回车换行
			sTarget = sTarget.replaceAll("(?i)%0d|%0a","");
			response.sendRedirect(sTarget);
			return;
		}
	}
	WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
	filter.addSearchValues(0, nSiteId);
	try{
		currWatermarks = Watermarks.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("photo_upload.jsp.label.fail2get_watermark", "获取站点水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns:TRS_UI>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title WCMAnt:param="photo_upload.jsp.title">TRS WCM 图片库图片上传</title>
	<style>
		.mainkindnotfound{
			cursor:pointer;
			color:red;
			font-size:16px
		}
		.mainkindfound{
			cursor:pointer;
			color:#483d8b
		}
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
			width:220px;
			height:20px;
			border:1px solid black;	
			margin-top:2px;
		}
		.input_text{
			height:16px;
			border:1px solid green;
			margin:0 1px;
		}
		.input_button{
			float:left;
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
			font-weight:bold;						
		}
		fieldset{			
			width:300px;
			height:295px;
			padding:10px;
		}
		legend{
			font-weight:bold;
			color:#039;
			padding:5px;
		}
		#picnum{
			width:30px;
			height:16px;
			border:1px solid green;
			padding-right:10px;
		}
		#picnumbtn{
			width:30px;
			height:20px;
			border:1px solid black;			
		}
		label{
			cursor:pointer;
			padding-left:5px;
		}
		.invalid_file{
			border:1px solid red;
		}
		#process_wating{
			display:none;
			background:url(../images/icon/waiting2.gif) no-repeat center center;
			z-index:100;
			background-color:#fff;			
			top:0px;
			left:0px;			
			-moz-opacity:0.5;
			opacity:0.5;
			filter:alpha(opacity=50);		
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
	<!--ProcessBar Start-->
	<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
	<!--ProcessBar End-->
	<!--others-->
	<script src="../js/source/wcmlib/util/YUIConnection.js"></script>	
	<script src="photo_upload.js"></script>
	<script>
		window.m_fpCfg = {
			m_arrCommands : [{
				cmd : 'Ok',
				name : wcm.LANG.PHOTO_CONFIRM_1 || '确定'
			},{
				cmd : 'cancel',
				name : wcm.LANG.PHOTO_CONFIRM_2 || '取消'
			}],
			size : [690, 330],
			withclose : false
		};	
	</script>
<div id="process_wating">
	<div style="text-align:center;margin:50px;font:bold 28px">
		<p><span WCMAnt:param="photo_upload.jsp.saving">正在保存图片...</span><p><span WCMAnt:param="photo_upload.jsp.continue">请稍候继续设置图片属性</span>
	</div>
</div>
<table width="670"  border="0" style="font-size:12px">
  <tr>
    <td valign="top">
	<fieldset style="width:280px"><legend WCMAnt:param="photo_upload.jsp.selectPic">选择图片</legend>
		<table width="100%" border="0" height="100%">        
		<tr>
		  <td style="font-size:12px" valign="top" height="30px">
		    <span WCMAnt:param="photo_upload.jsp.picCount">上传图片数目</span><input type="text" id="picnum" value="1" validation="type:'int',min:'1',max:'10'" desc="上传数目" validation_desc="上传数目" WCMAnt:paramattr="validation_desc:photo_upload.jsp.upCount"/>&nbsp;&nbsp;
			<input type="button" value="OK" id="picnumbtn" />
			<label for="batchupload" style="padding-left:10px;cursor:pointer"><input type="checkbox" id="batchupload"/><span WCMAnt:param="photo_upload.jsp.volumeImport">批量导入(*.zip)</span></label>
		  </td>
		</tr>
		<tr>
			<td valign="top">
				<div class="message" WCMAnt:param="photo_upload.jsp.selectPicFromLocal">选择本地图片文件：</div>
				<div>
				<form id="form_pic" name="fm_pic" style="margin:0;" enctype='multipart/form-data' onsubmit="Ok();return false;">
					<input type="file" class="input_file" style="width:280px;" id="PicUpload" name="PicUpload">
				</form>
				</div>
				<form id="form_imageInfo">
					<input type="hidden" name="MainKindId" id="MainKindId" value="<%=nChannelId%>"></input>
					<input type="hidden" name="OtherKindIds" id="OtherKindIds" value=""></input>
					<input type="hidden" name="WatermarkFile" id="WatermarkFile" value="0"></input>
					<input type="hidden" name="WatermarkPos" id="WatermarkPos" value=""></input>	
					<input type="hidden" name="UploadedFiles" id="UploadedFiles" value=""></input>
					<input type="hidden" name="SourceFiles" id="SourceFiles" value=""></input>
					<input type="hidden" name="BatchMode" id="BatchMode" value="0"></input>
					<input type="hidden" name="BmpConverType" id="BmpConverType" value=""></input>
				</form>
			</td>			
		</tr>	     
      </table>
	</fieldset>
	</td>
    <td valign="top">
	<fieldset><legend WCMAnt:param="photo_upload.jsp.picAttribute">图片属性</legend>
		<table style="font-size:12px" width="100%" border="0" height="100%">       
        <tr>
          <td>
			<span class="attr_name" WCMAnt:param="photo_upload.jsp.mainClass">主分类：</span>
			<span id="mainkind" class=<%=nChannelId==0?"mainkindnotfound":"mainkindfound"%>><%=channelName%></span>
          </td>
        </tr>		
		<tr>
          <td>
			<span class="attr_name" WCMAnt:param="photo_upload.jsp.otherClass">其它分类：</span>
			<span style="cursor:pointer;color:#483d8b" title="选择图片的其它分类" id="selOtherKinds" WCMAnt:param="photo_upload.jsp.select">选择</span><br />			
			<span id="otherkinds_holder">
				<select name="otherkinds" id="otherkinds" multiple size="4" style="width:150">
				</select>
			</span>
          </td>
        </tr>
		<tr>
          <td>
			<span class="attr_name" WCMAnt:param="photo_upload.jsp.picTransStye">BMP图片转换格式：</span>
			<select onchange="convertBmp(this);" id="BmpConverTypeSelect">
				<option value="bmp" WCMAnt:param="photo_upload.jsp.noChange">不转换</option>
				<option value="gif">GIF</option>
				<option value="jpg">JPG</option>
			</select>
          </td>
        </tr>
		<tr>
          <td>
			<span class="attr_name" WCMAnt:param="photo_upload.jsp.selectWaterMark">选择水印：</span>
			<span id="watermarks">
			<select id="selwatermark" onchange="addWaterMark(this);" style="width:120px;">
					<option value="-1" WCMAnt:param="photo_upload.jsp.noWaterMark">--不添加水印--</option>
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
          </td>
        </tr>
		<tr>
          <td>
			<div id="div_watermarkpos" style="display:none">
				<span class="attr_name" title="点击全选" style="cursor:pointer" onclick="selectAllPos()" WCMAnt:param="photo_upload.jsp.waterPosition" WCMAnt:paramattr="title:photo_upload.jsp.clickToSelAll">水印位置：</span><br />
				<label for="LT"><span WCMAnt:param="photo_upload.jsp.leftTop">左上</span><input type="checkbox" id="LT" value="1"/></label>
				<label for="CM"><span WCMAnt:param="photo_upload.jsp.center">居中</span><input type="checkbox" id="CM" value="2"/></label>
				<label for="RB"><span WCMAnt:param="photo_upload.jsp.rightDown">右下</span><input type="checkbox" id="RB" checked="true" value="3"/></label>
			</div>			
          </td>
        </tr>
      </table>
	</fieldset>
	</td>
  </tr>
</table>
<div style="margin:-300 0 0 550">
	<img src=""  style="display:none" id="watermarkpic">
</div>
<script language="javascript">
<!--
	var nSiteId = <%=nSiteId%>;
//-->
</script>
</body>
</html>