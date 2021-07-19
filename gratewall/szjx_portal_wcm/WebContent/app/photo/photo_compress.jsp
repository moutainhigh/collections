<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.Watermark"%>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.presentation.plugin.PluginConfig" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	int nSiteId = currRequestHelper.getInt("SiteId",0);
out.clear();%>
<?xml version="1.0" encoding="UTF-8"?>
<html>
 <head>
  <title WCMAnt:param="photo_conpress.jsp.title"> 图片编辑 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/photo.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
  <!-- Validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script type="text/javascript" src="photo_compress.js"></script>
  <style>
	*{
		padding:0;
		margin:0;
	 }
	body{
		padding:20px;
		font-size:12px;
	}
	.show_photo_outter{
		width:72%;
		height:95%;
		border:1px solid gray;
		padding:10px;
		margin-top:10px;
		float:left
	}

	.show_photo{
		width:98%;
		height:98%;
		overflow:auto;
	}
	.property_photo{
		height:110px;
	}
	.compress_photo{
		height:270px;
	}
	.photo_properties{
		float:left;
		margin:10px 0px 0px 10px;
		padding:10px;
		border:1px solid gray;
		height:95%;
		overflow-x:hidden;
		overflow-y:auto;
		width:22%;
	}

	fieldset div{
		margin:8px 10px;
	}
	fieldset input[type='text']{
		width:100px;
		border:1px solid black;
	}
	.crop_photo{
		height:50px;
	}
	.container{
		overflow:hidden;width:100%;height:94%;
	}
	.ext-gecko .container{
		height:100%;
	}
	#photo_FileNames{
		display:none;
	}
  </style>
  <script>
	//设置水印选择
	var m_arWatermarks = null;
	<%if(PluginConfig.isStartPhoto()){%>
		var m_arWatermarks = <%=makeWatermarksJson(loginUser,nSiteId)%>
	<%}%>
	
	window.onload=function(){
		window.pageLoaded = true;
		if(window.imgLoaded){
			setTimeout(init, 0);
		}
		initPhotowms();
		//resizePhoto(img);
	}
  </script>
 </head>

 <body scroll=no style="height:87%;">
<%if(true) {%>
	<span id="photo_FileNames" class="">图片名称 : <input id="photo_FileName" size="30" type="text" validation="type:'string',max_len:'200',showid:'validatetip',desc:'图片名称'" validation_max_len="200" validation_desc="图片名称"></span><span id="validatetip" style="margin-left:20px;">&nbsp;</span>
<%}else {%>
	<span class="edit_photo_text">编辑图片</span>
<%}%>
<hr/>
<div class="container">
	<div class="show_photo_outter" id="show_photo_outter">
		<div id="show_photo" class="show_photo" >
			<img id="show_photo_img" src="../images/loading.gif"/>
		</div>
	</div>
	 <div class="photo_properties">
		<fieldset class="property_photo" style="border:1px solid #d0d0bf"><legend style="padding:4px;" WCMAnt:param="photo_compress.jsp.picAttr">图片属性</legend>
			<div style="margin:10px"><input type="radio" id="suitablesize" name="photo_show_size" value="0" onclick="resizePhoto()"/><span WCMAnt:param="photo_compress.jsp.suitable_size">适合大小&nbsp;&nbsp;&nbsp;&nbsp;</span><input type="radio" id="orignsize" name="photo_show_size" value="1" checked onclick="resizePhoto()"/><span WCMAnt:param="photo_compress.jsp.original_size">原始大小</span></div>
			<div><span WCMAnt:param="photo_compress.jsp.original_width">原始宽度：</span><span id="orignWidth"></span></div>
			<div><span WCMAnt:param="photo_compress.jsp.original_height">原始高度：</span><span id="orignHeight"></span></div>
		</fieldset>
		<fieldset class="compress_photo" style="margin-top:10px;border:1px solid #d0d0bf"><legend style="padding:4px;" WCMAnt:param="photo_compress.jsp.zoompic">缩放图片</legend>
			<div><input id="scaletype_pixel" type="radio" name="compress_type" checked onclick="changeScaleType()"/><span WCMAnt:param="photo_compress.jsp.zoompic_aspix">按像素进行缩放</span></div>
			<div><span WCMAnt:param="photo_compress.jsp.pic_width">宽度：</span><input forceValid="true" id="newWidth" type="text" style="width:100px;"onkeyup="changeScaleValue('newWidth')" validation="type:'int',required:'true',min:1,desc:'宽度',showid:'validate_tip'" /></div>
			<div><span WCMAnt:param="photo_compress.jsp.pic_height">高度：</span><input forceValid="true" id="newHeight" type="text" style="width:100px;"onkeyup="changeScaleValue('newHeight')" validation="type:'int',required:'true',min:1,desc:'高度',showid:'validate_tip'" /></div>
			<div><input type="radio" name="compress_type" onclick="changeScaleType()"/><span WCMAnt:param="photo_compress.jsp.zoompic_aspercent">按百分比进行缩放</span></div>
			<div><span WCMAnt:param="photo_compress.jsp.pic_width">宽度：</span><input validation="type:'int',required:'true',min:1,desc:'宽度',showid:'validate_tip'" id="widthRatio" type="text"style="width:100px;"onkeyup="changeScaleValue('widthRatio')" /></div>
			<div><span WCMAnt:param="photo_compress.jsp.pic_height">高度：</span><input validation="type:'int',required:'true',min:1,desc:'高度',showid:'validate_tip'" id="heightRatio"  type="text"style="width:100px;"onkeyup="changeScaleValue('heightRatio')" /></div>
			<div><input type="checkbox" name="keep_scale_ratio" id="keep_scale_ratio" checked/><span WCMAnt:param="photo_compress.jsp.keep_scale-ratio">保持宽高比</span></div>
			<div style="float:right"><input style="width:50px" type="button"  WCMAnt:paramattr="value:photo_compress.jsp.reset" value="重置" onclick="resetAll()"/><input type="button" style="width:50px" WCMAnt:paramattr="value:photo_compress.jsp.apply" value="应用" onclick="applyAction()"/></div>
			<div id="validate_tip" >&nbsp;</div>
		</fieldset>
		<%if(PluginConfig.isStartPhoto()){%>
		<FIELDSET style="padding:2px 10px;" id="wmposcontainer"><LEGEND><span fcklang="wmsettings" WCMAnt:param="photo_compress.jsp.setwatermark">水印设置:</span></LEGEND>
			<br>					
			<span WCMAnt:param="photo_compress.jsp.selectwatermark">选择水印:</span>
				<select id="wmsel" style="width:130px;">
					<option value="-1" WCMAnt:param="photo_compress.jsp.please_setwatermark">--请选择一个水印--</option>
				</select>
			<div style="margin:10px;display:none" id="watermarkpiccontainer">
				<img src=""  id="watermarkpic">
			</div>	
			<br>
			<span title="点击全选" WCMAnt:paramattr="title:photo_compress.jsp.selAll" style="cursor:pointer" fcklang="wmpos" id="wmpos" WCMAnt:param="photo_compress.jsp.watermark_position">水印位置:</span>
			<label for="LT"><span fcklang="wmposlt" WCMAnt:param="photo_compress.jsp.left_up">左上</span><input type="checkbox" id="LT" value="1"/></label>
			<label for="CM"><span fcklang="wmposcm" WCMAnt:param="photo_compress.jsp.center">居中</span><input type="checkbox" id="CM" value="2"/></label>
			<label for="RB"><span fcklang="wmposrb" WCMAnt:param="photo_compress.jsp.right_down">右下</span><input type="checkbox" id="RB" value="3"/></label>
			<br>
		</FIELDSET>
		<fieldset class="crop_photo" style="margin-top:10px;border:1px solid #d0d0bf"><legend style="padding:4px;" WCMAnt:param="photo_compress.jsp.cutpic">裁剪图片</legend>
			<div><a href="#" onclick="crop()" WCMAnt:param="photo_compress.jsp.cutpic">裁剪图片</a></div>
		</fieldset>
		<%
			}
		%>
	</div>
  </div>
  <div style="text-align:center;margin:5px">
	<input type="button" WCMAnt:paramattr="value:photo_compress.jsp.confirm"  value="确定" style="width:100px" onclick="onOK()"/>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" WCMAnt:paramattr="value:photo_compress.jsp.cancel" value="取消" style="width:100px" onclick="window.close()"/>
  </div>
 </body>
</html>
<%!
	private String makeWatermarksJson(User loginUser,int nSiteId)  {		
		try{
			WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
			filter.addSearchValues(0, nSiteId);
			Watermarks 	currWatermarks = new Watermarks(loginUser);
			if(nSiteId==0) {
				currWatermarks.open(null);
			}else {
				currWatermarks.open(filter);
			}
			if(!currWatermarks.isEmpty()){
				StringBuffer buff = new StringBuffer(128*currWatermarks.size());
				for(int i=0,size=currWatermarks.size();i<size;i++){
					Watermark mark = (Watermark)currWatermarks.getAt(i);
					if(mark == null) continue;
					buff.append(",{n:'");
					buff.append(CMyString.filterForJs(mark.getWMName()));
					buff.append("',v:'");
					buff.append(mark.getWMPicture());
					buff.append("'}");
				}
				if(buff.length() > 0){
					return "["+buff.substring(1)+"]";
				}
			}
		}catch(Exception e){
			//Ignore.
		}
		return "[]";
	}
%>