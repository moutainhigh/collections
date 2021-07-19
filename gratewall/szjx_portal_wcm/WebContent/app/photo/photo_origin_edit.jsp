<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nDocId = currRequestHelper.getInt("PhotoId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	Document currDocument = Document.findById(nDocId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_origin_edit.jsp.doc_notfound", "没有找到ID为[{0}]的文档!"), new int[]{nDocId}));
	}
	String sWaterMarkFile = currDocument.getAttributeValue("WaterMarkFile");
	String sWaterMarkPos = currDocument.getAttributeValue("WaterMarekPos");
	if(sWaterMarkFile == null){
		sWaterMarkFile = "0";
	}
	Watermarks currWatermarks = null;
	Watermark newWatermark = null;
	WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
	filter.addSearchValues(0, nSiteId);
	try{
		currWatermarks = Watermarks.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("photo_origin_edit.jsp.label.fail2get_watermark_coll", "获取站点水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="photo_origin_edit.jsp.title">图片编辑</title>
<style>
    .wcm-tab-panel{
        height:200px;
    }
	.theader{
		height:29px;
		background-image:url(../../WCMV6/images/document_normal_index/button-bg.png);	
		font-size:14px;
		padding-left:10px;
	}
	button{
		height: 22px;
		background: #eeeeee;
		border: solid 1px silver;
		font-size: 12px;
		padding:0px 3px;
		margin:10px 10px;
		cursor:hand;
	}
	legend{
		font:bold 12px;
		padding:3px;
	}
	label{
		cursor:hand;
	}
	.input_text{
		border:1px solid black;
		width:90px;
		height:18px;
	}
	.attr_name{
		font:bold 12px;
	}
	.text{
		font-family:Arial;
		font-size:14px;		
	}
</style>
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
<!--wcm-dialog end-->
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
<!--others-->
<script src="../js/data/locale/wcm52.js"></script>
<script src="../js/wcm52/CTRSColorPicker.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/tabpanel/TabPanel.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/components/ProcessBar.js"></SCRIPT>
<link href="../../app/js/source/wcmlib/tabpanel/resource/tabpanel.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/components/processbar.css" rel="stylesheet" type="text/css" />
<script src="photo_origin_edit.js"></script>
<style type="text/css">
	.ext-gecko #tabPanel{float:left;height:200px;width:100%;}
</style>
<style type="text/css">
	.cropHandler{
		padding:4px;
		text-decoration:underline;
		color:blue;
		cursor:pointer;
	}
	.scopeTip{
		padding:4px;
		text-decoration:underline;
		color:blue;
		cursor:pointer;
	}
	.applyCrop{
		margin-left:4px;
		text-decoration:underline;
		color:blue;
		cursor:pointer;
	}
</style>
</head>

<body>
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
<tbody>
	<tr>
		<td class="theader" WCMAnt:param="photo_origin_edit.jsp.editPic">编辑图片</td>
	</tr>
	<tr>
		<td width="100%" height="100%">
			<table border="0" cellspacing="0" cellpadding="10" width="100%" height="100%">
			<tbody>
				<tr>
					<td style="width:70%;height:100%;background-color:#fff;border:1px solid #c3c3c3;" valign="middle" align="center">						
						<div style="height:100%;width:100%;overflow:auto;">						
							<img id="imageElement" src="../images/loading.gif" align="middle"  id="imageBlock">
						</div>						
					</td>
					<td style="width:30%;height:100%;">
						<div style="height:100%;width:100%;overflow:hidden;overflow-y:auto;">						
							<fieldset><legend WCMAnt:param="photo_origin_edit.jsp.picEdit">图片编辑</legend>
							<div style="font-size:12px;padding:10px 10px;margin-bottom:8px">
								<label for="suiteSize">
									<input type="radio" name="imagesize" id="suiteSize" onclick="setSuiteSize();" checked="true"><span WCMAnt:param="photo_origin_edit.jsp.adjustSize">适合大小</span>
								</label>
								<label for="originalSize">
									<input type="radio" name="imagesize" id="originalSize" onclick="setOriginSize();" ><span WCMAnt:param="photo_origin_edit.jsp.originalSize">原始大小</span>
								</label>
							</div>
							<div class="wcm-tab-panel wcm-s-tab-panel" id="tabPanel">
							   <div class="head-box" style="padding-left:10px;width:256px;">
									<div class="tab-head" item="tab-imgscale"><a href="#" WCMAnt:param="photo_origin_edit.jsp.lower" style="font-size:15px;font-family:Arial;">缩放</a></div>
									<div class="tab-head" item="tab-imgrotate"><a href="#" WCMAnt:param="photo_origin_edit.jsp.rot" style="font-size:15px;font-family:Arial;">旋转</a></div>
									<div class="tab-head" item="tab-imgborder"><a href="#" WCMAnt:param="photo_origin_edit.jsp.frame" style="font-size:15px;font-family:Arial;">边框</a></div>
									<div class="tab-head" item="tab-imgraise"><a href="#" WCMAnt:param="photo_origin_edit.jsp.relief" style="font-size:15px;font-family:Arial;">浮雕</a></div>
								</div>
								<div class="body-box" style="padding:10px;">
									<div class="tab-body" id="tab-imgscale" actiontype="scaleImage">
										<div style="margin:0px 0px;padding:5px 5px;">
											<div  style="margin:5px 5px">
												<span>
												<label for="scaletype_pixel">
													<input type="radio" name="scaletype" id="scaletype_pixel" onclick="changeScaleType();" checked ><span WCMAnt:param="photo_origin_edit.jsp.pixel" class="text">像素</span>
												</label>
												</span><br />
												<span style="margin:10px 5px;">
												<label for="scalewidth_pixel">
													<span WCMAnt:param="photo_origin_edit.jsp.width" class="text">宽度</span>：
													<input type="text" class="input_text" name="scalewidth_pixel" id="scalewidth_pixel" onkeyup="changeScaleValue('width_pixel')" validation="type:'int',required:'true',desc:'宽度',value_range:'75,2000',showid:'validate_tip'" forceValid="true" value="" validation_desc="宽度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.width">
												</label>
												</span><br/>
												<span style="margin:10px 5px;">
												<label for="scaleheight_pixel">
													<span WCMAnt:param="photo_origin_edit.jsp.height" class="text">高度</span>：
													<input type="text" class="input_text"  name="scaleheight_pixel" id="scaleheight_pixel" onkeyup="changeScaleValue('height_pixel')"  validation="type:'int',required:'true',desc:'高度',value_range:'75,2000',showid:'validate_tip'" forceValid="true" value="" validation_desc="高度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.height">
												</label>
												</span>
											</div>
											<div  style="margin:5px 5px">
												<span>
												<label for="scaletype_percent">
													<input type="radio" name="scaletype" id="scaletype_percent"  onclick="changeScaleType();"><span WCMAnt:param="photo_origin_edit.jsp.percent" class="text">百分比</span>
												</label>
												</span><br />
												<span style="margin:10px 5px;">
												<label for="scalewidth_percent">
													<span WCMAnt:param="photo_origin_edit.jsp.width" class="text">宽度</span>：
													<input type="text" class="input_text" name="scalewidth_percent" id="scalewidth_percent" onkeyup="changeScaleValue('width_percent')"  value="100" validation="type:'int',required:'true',value_range:'1,100',desc:'宽度',showid:'validate_tip'" validation_desc="宽度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.width">
												</label>
												</span><br/>
												<span style="margin:10px 5px;">
												<label for="scaleheight_percent">
													<span WCMAnt:param="photo_origin_edit.jsp.height" class="text">高度</span>：
													<input type="text" class="input_text"  name="scaleheight_percent" id="scaleheight_percent" onkeyup="changeScaleValue('height_percent')" value="100" validation="type:'int',required:'true',value_range:'1,100',desc:'宽度',showid:'validate_tip'" validation_desc="高度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.height">
												</label>
												</span>
											</div>
											<div>
												<label for="keepscaleratio">
													<input type="checkbox" name="keepscaleratio" id="keepscaleratio" onclick="setKeepScacleRatio()" checked="true"><span WCMAnt:param="photo_origin_edit.jsp.keepScale" class="text">保持宽高比</span>
												</label>
											</div>
										</div>
									 </div>
									 <div class="tab-body" id="tab-imgrotate" actiontype="rotateImage">
										<div style="margin:0px 0px;padding:5px 0px;">
											<div  style="margin:5px 5px">
												<span WCMAnt:param="photo_origin_edit.jsp.direction" class="text">方向</span>：
												<label for="rotate_clockwise">
													<input type="radio" name="rotatetype" id="rotate_clockwise" checked >
													<span WCMAnt:param="photo_origin_edit.jsp.Clockwise" class="text">顺时针</span>
												</label>
												<label for="rotate_anticlockwise">
													<input type="radio" name="rotatetype" id="rotate_anticlockwise">
													<span WCMAnt:param="photo_origin_edit.jsp.Counterclockwise" class="text">逆时针</span>
												</label>
											</div>
											<div  style="margin:5px 5px">
												<label for="rotate_degree">
													<span WCMAnt:param="photo_origin_edit.jsp.angle" class="text">角度</span>：
													<input type="text" class="input_text"  name="rotate_degree" id="rotate_degree" value=""  validation="type:'int',required:'true',value_range:'1,360',desc:'旋转角度',showid:'validate_tip'" validation_desc="旋转角度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.rotAngle">
													<span WCMAnt:param="photo_origin_edit.jsp.adot" class="text">度</span>
												</label>
											</div>
										</div>
									 </div>
									 <div class="tab-body" id="tab-imgborder" actiontype="addBorder">
										<div style="margin:0px 0px;padding:5px 5px;">
											<div  style="margin:5px 5px">											
												<span style="margin:10px 0px;">
												<label for="borderwidth">
													<span WCMAnt:param="photo_origin_edit.jsp.width" class="text">宽度</span>：
													<input type="text" class="input_text" name="borderwidth" id="borderwidth" onkeyup="changeBorderValue('width');" validation="type:'int',required:'true',desc:'边框宽度',value_range:'1,100',showid:'validate_tip'" value="1" 
													validation_desc="边框宽度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.frameWidth">
													<span WCMAnt:param="photo_origin_edit.jsp.pixel" class="text">像素</span>
												</label>
												</span><br/>
												<span style="margin:10px 0px;">
												<label for="borderheight">
													<span WCMAnt:param="photo_origin_edit.jsp.height" class="text">高度</span>：
													<input type="text" class="input_text"  name="borderheight" id="borderheight" onkeyup="changeBorderValue('height');" validation="type:'int',required:'true',desc:'边框高度',value_range:'1,100',showid:'validate_tip'" value="1" 
													validation_desc="边框高度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.frameHeight">
													<span WCMAnt:param="photo_origin_edit.jsp.pixel" class="text">像素</span>
												</label>
												</span>
											</div>
											<div  style="margin:5px 5px">		
												<span style="margin:10px 0px;">
												<table border="0" cellspacing="0" cellpadding="0">
												<tbody>
													<tr>
														<td style="font-size:12px;" WCMAnt:param="photo_origin_edit.jsp.color" class="text">颜色：</td>
														<td>
															<script>													
																TRSColorPicker.create("bordercolor", "#000000");
																if(!Ext.isIE){
																	document.write('<div Name="tdColor" ID="tdColor" STYLE="WIDTH:16px;HEIGHT:4px;background:black;">&nbsp;</div><input type="hidden" id="bordercolor" value="#000000"/>');
																}
															</script>
														</td>
													</tr>
												</tbody>
												</table>
												</span>
											</div>
											<div>
												<label for="keepbordereq">
													<input type="checkbox" name="keepbordereq" id="keepbordereq" onclick="setKeepBorderWidthAndHeight();" checked="true">
													<span WCMAnt:param="photo_origin_edit.jsp.keepFrameEqual" class="text">保持边框的宽高相等</span>
												</label>
											</div>
										</div>
									 </div>
									 <div class="tab-body" id="tab-imgraise" actiontype="raiseImage">
										<div style="margin:0px 0px;padding:5px 5px;">
											<div  style="margin:5px 5px" >
												<span WCMAnt:param="photo_origin_edit.jsp.type" class="text">类型</span>：
												<label for="raise_up">
													<input type="radio" name="raisetype" id="raise_up" checked>
													<span WCMAnt:param="photo_origin_edit.jsp.convex" class="text">凸出</span>
												</label>
												<label for="raise_down">
													<input type="radio" name="raisetype" id="raise_down">
													<span WCMAnt:param="photo_origin_edit.jsp.au" class="text">凹下</span>
												</label>
											</div>
											<div  style="margin:5px 5px">											
												<span style="margin:10px 0px;">
												<label for="raisewidth">
													<span WCMAnt:param="photo_origin_edit.jsp.width" class="text">宽度</span>：
													<input type="text" class="input_text" name="raisewidth" id="raisewidth" onkeyup="changeRaiseValue('width');" validation="type:'int',required:'true',desc:'浮雕宽度',value_range:'1,100',showid:'validate_tip'" value="" validation_desc="浮雕宽度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.raisewidth">
													<span WCMAnt:param="photo_origin_edit.jsp.pixel" class="text">像素</span>
												</label>
												</span><br/>
												<span style="margin:10px 0px;">
												<label for="raiseheight">
													<span WCMAnt:param="photo_origin_edit.jsp.height" class="text">高度</span>：
													<input type="text" class="input_text"  name="raiseheight" id="raiseheight" onkeyup="changeRaiseValue();" validation="type:'int',required:'true',desc:'浮雕高度',value_range:'1,100',showid:'validate_tip'" value="" validation_desc="浮雕高度" WCMAnt:paramattr="validation_desc:photo_origin_edit.jsp.raiseheight">
													<span WCMAnt:param="photo_origin_edit.jsp.pixel" class="text">像素</span>
												</label>
												</span>
											</div>
											<div>
												<label for="keepraiseeq">
													<input type="checkbox" name="keepraiseeq" id="keepraiseeq" onclick="setKeepRaiseWidthAndHeight()" checked="true">
													<span WCMAnt:param="photo_origin_edit.jsp.keepEqual" class="text">保持宽高相等</span>
												</label>
											</div>
										</div>
									 </div>
								</div>
							</div>
							<div id="validate_tip" style="float:left;margin-top:15px;margin-left:15px;height:18px;"></div>
							<div>&nbsp;</div>
							<div style="float:right;width:100%;">
								<button type="button" onclick="undo();" id="btnundo" disabled="true" WCMAnt:param="photo_origin_edit.jsp.cancel">撤销</button>
								<button type="button" onclick="redo();" id="btnredo"  disabled="true" WCMAnt:param="photo_origin_edit.jsp.redo">重做</button>
								<button type="button" onclick="applyAction();" WCMAnt:param="photo_origin_edit.jsp.apply">应用</button>
							</div>							
							</fieldset>
							<fieldset style="margin-top:20px;border:1px solid #d0d0bf"><legend WCMAnt:param="photo_origin_edit.jsp.picAttribute">图片属性</legend>
							<div style="margin:0px 20px;padding:5px 5px;">
								<!--div>
									<span class="attr_name">BMP图片转换格式：</span>
									<select id="BmpConverTypeSelect">
										<option value="bmp">不转换</option>
										<option value="gif">GIF</option>
										<option value="jpg">JPG</option>
									</select>
								</div-->
								<div>
									<span class="attr_name" WCMAnt:param="photo_origin_edit.jsp.selectWaterMark">选择水印：</span>					
									<span id="watermarks">
											<select id="selwatermark" style="width:120px;">
												<option value="-1" WCMAnt:param="photo_origin_edit.jsp.noWaterMark">--不添加水印--</option>
												<%
													if(nSize>0){
														for(int i=0;i<nSize;i++){
															newWatermark = (Watermark)currWatermarks.getAt(i);
															String sFileName = newWatermark.getWMPicture();
															sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_HTTP) + sFileName;
												%>
														<option value="<%=newWatermark.getId()%>" _picsrc="<%=sFileName%>" _picfile="<%=newWatermark.getWMPicture()%>" <%=newWatermark.getWMPicture().trim().equals(sWaterMarkFile)?"selected":""%> _picpos="<%=sWaterMarkPos%>"><%=newWatermark.getWMName()%></option>
												<%
														}
													}
												%>
										</select>
									</span>										
								</div>
								<div style="margin:10px 10px 10px 70px;display:none" id="watermarkpiccontainer">
									<img src=""  id="watermarkpic">
								</div>
							</div>
							<div id="div_watermarkpos" style="display:none;margin:0px 0px;padding:5px 5px;">
								<span class="attr_name" title="点击全选" style="cursor:hand;margin-left:20px;" onclick="selectAllPos()" WCMAnt:param="photo_origin_edit.jsp.waterPosition">水印位置：</span><br/>
								<span style="padding-left:15px;"><label for="pos_1"><input type="radio" name="pos" id="pos_1" value="1" onclick="change()" checked style="display:none"/><span WCMAnt:param="photo_origin_edit.jsp.specific">特定：</span></label></span>
								<span id="pos_1_container">
								<label for="LT" class="attr_name"><span WCMAnt:param="photo_origin_edit.jsp.leftTop">左上</span><input type="checkbox" id="LT" value="1"/></label>
								<label for="CM" class="attr_name"><span WCMAnt:param="photo_origin_edit.jsp.center">居中</span><input type="checkbox" id="CM" value="2"/></label>
								<label for="RB" class="attr_name"><span WCMAnt:param="photo_origin_edit.jsp.rightDown">右下</span><input type="checkbox" id="RB" value="3"/></label>
								</span><br/>
								<div style="display:none">
								<span style="padding-left:15px;"><label for="pos_2"><input type="radio" name="pos" id="pos_2" value="4" onclick="change()" /><span WCMAnt:param="photo_origin_edit.jsp.appointed">指定：</span></label></span>
								<span id="pos_2_container" style="display:none;">
								X : &nbsp;<input type="text" id="left" style="width:40px;height:20px;" value="0" disabled/> Y: &nbsp; <input type="text" id="top" style="width:40px;height:20px;" value="0" disabled/>
								<span class="cropHandler" WCMAnt:param="photo_origin_edit.jsp.definded" onclick="applyWaterMark()" >指定</span></span>
								</div>
							</div>
							</fieldset>
							<fieldset style="margin-top:20px;border:1px solid #d0d0bf;font-size:12px;"><legend WCMAnt:param="photo_origin_edit.jsp.piccut">图片裁剪</legend>
							<div style="margin:0px 20px;padding:5px 5px;">
								<span class="cropHandler" onclick="crop()" WCMAnt:param="photo_origin_edit.jsp.cutpic">裁剪图片</span>
								<span class="scopeTip" id="scopeTip" style="display:none;" WCMAnt:paramattr="title:channelsyncol_list.html.after_preview_cut_pic" title="预览裁剪之后的图片" onclick="previewPhoto();" WCMAnt:param="photo_origin_edit.jsp.preview_cut-pic">预览裁剪</span>
								<span class="applyCrop" id="applyCropHandler" onclick="applyCrop();" style="display:none;" WCMAnt:paramattr="title:channelsyncol_list.html.use_cutpic"  title="应用当前的裁剪操作，不保存裁剪的图片" WCMAnt:param="photo_origin_edit.jsp.use_cutpic">应用裁剪</span>								
							</div>
							</fieldset>
						</div>	
					</td>
				</tr>
			</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" valign="bottom" height="22px">
			<button type="button" onclick="onOk();" WCMAnt:param="photo_origin_edit.jsp.sure">确定</button>
			<button type="button" onclick="window.close();" WCMAnt:param="photo_origin_edit.jsp.undo">取消</button>
		</td>
	</tr>
</tbody>
</table>
</body>
</html>