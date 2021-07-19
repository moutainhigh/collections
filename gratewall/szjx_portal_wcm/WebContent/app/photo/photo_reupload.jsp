<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int nSiteId = currRequestHelper.getInt("LibId",0);
	int nDocId = currRequestHelper.getInt("ObjectId",0);
	Document currDocument = Document.findById(nDocId);
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_reupload.jsp.doc_notfound", "没有找到ID为[{0}]的文档!"), new int[]{nDocId}));
	}
	String sWaterMarkFile = currDocument.getAttributeValue("WaterMarkFile");
	String sWaterMarkPos = currDocument.getAttributeValue("WaterMarekPos");
	if(sWaterMarkFile == null){
		sWaterMarkFile = "0";
	}
	Channel currChannel = null;
	WCMFilter filter = new WCMFilter("", "LibId=?", "crtime desc","");
	if(nSiteId ==0){
		currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_reupload.jsp.channel_notfound", "没有找到ID为[{0}]的栏目!"), new int[]{nChannelId}));
		}
		nSiteId = currChannel.getSiteId();
	}
	filter.addSearchValues(0, nSiteId);
	Watermarks currWatermarks = null;
	Watermark newWatermark = null;
	try{
		currWatermarks = Watermarks.openWCMObjs(ContextHelper.getLoginUser(),filter);
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("photo_reupload.jsp.label.fail2get_watermark_coll", "获取站点水印集合失败！"), e);
	}
	int nSize = currWatermarks.size();
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title WCMAnt:param="photo_reload.jsp.title">重新上传图片</title>
	<style>
		.input_file{
			float:left;
			width:220px;
			height:20px;
			border:1px solid black;	
			margin-top:2px;
		}
		label{
			cursor:hand;
		}
		button{
			height: 22px;
			background: #eeeeee;
			border: solid 1px silver;
			font-size: 12px;
			padding: -5px 3px;
			margin: -3px 3px;
			cursor:hand;
		}
		td.attrname{
			font-size:12px;
			font-weight:bold;
			text-align:right;
		}
	</style>
	<script src="../../app/js/easyversion/lightbase.js"></script>
	<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script src="../../app/js/data/locale/photo.js"></script>
	<script src="../../app/js/easyversion/extrender.js"></script>
	<script src="../../app/js/easyversion/ajax.js"></script>
	<script src="../../app/js/easyversion/basicdatahelper.js"></script>
	<script src="../../app/js/easyversion/web2frameadapter.js"></script>
	<!--ProcessBar-->
	<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
	<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
	<!--others-->
	<script src="../js/source/wcmlib/util/YUIConnection.js"></script>	
	<script>
	function onOk(cb){	
		var isSSL = location.href.indexOf("https://")!=-1;		
		var fn = $("PicUpload").value;	
		try{
			FileUploadHelper.validFileExt(fn, ".jpg,.gif,.jpeg,.png,.bmp");
		}catch(err){
			Ext.Msg.alert(err.message, function(){
				$("PicUpload").focus();

			});

			return false;
		}
		var okCommand = parent.document.getElementsByTagName("button");
		okCommand[1].disabled = true;
		ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_30 || '上传图片');
		var callBack1 = {
			"upload":function(_transport){
				var sResponseText = _transport.responseText;			
				if(sResponseText.match(/<!--ERROR-->/img)){
					var texts = sResponseText.split('<!--##########-->');
					Ext.Msg.fault({
						message : texts[1],
						detail : texts[2]
					},wcm.LANG.PHOTO_CONFIRM_34 || '上传文件失败,与服务器交互时出错啦！');
					ProcessBar.close();
				}
				else{
					$("PhotoFile").value = _transport.responseText;					
					
					var posEls = [$("LT"),$("CM"),$("RB")];
					var t = [];
					var posEl = null;
					for(var i=0;i<3;i++){
						posEl = posEls[i];
						if(posEl.checked){
							t.push(posEl.value);
						}
					}					
					$("WatermarkPos").value = t.join(",");					
					BasicDataHelper.call("wcm6_photo","save","form_imageinfo",true,function(_transport,_json){
						ProcessBar.close();
						setTimeout(function(){
							cb.callback();
							cb.close();
						}, 1000);
					});		
				}
			}
		}

		try{
			YUIConnect.setForm("form_pic",true,isSSL);
			YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=PicUpload',callBack1);
		}catch(err){
			ProcessBar.close();
			Ext.Msg.alert(err.message);
		}
		return false;
	}
	var m_hValidateFiles = {
		toStr : function(){
			var str = [];
			for(var p in this){
				if(p != "toStr"){
					str.push(p);
				}
			}
			return str.join(",");
		}
	};
	function init(_args){
		$("PhotoDocId").value = _args.ObjectId;
		var el = $("selwatermark");
		if(el.value != -1){
			var op = el.options[el.selectedIndex];
			showWaterMark(op);	
			var pos = op.getAttribute("_picpos");
			selectedPos(pos);
		}
		Event.observe($("selwatermark"),"change",changeWatermark);
		
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;
			$("BmpConverType").value = _transport.responseText;
		});
		BasicDataHelper.call("wcm6_photo","getSupportedFormat",null,false,function(_transport,_json){			
			var sValue = _transport.responseText;
			if(sValue.trim().length > 0){
				eval("var j = "+sValue);
				Object.extend(m_hValidateFiles,j);						
			}
		});
	}
	function selectedPos(pos){
		if(pos.indexOf("1") != -1){
			$("LT").checked = true;
		}
		if(pos.indexOf("2") != -1){
			$("CM").checked = true;
		}
		if(pos.indexOf("3") != -1){
			$("RB").checked = true;
		}
	}
	function changeWatermark(){
		$("RB").checked = true;
		var el = $("selwatermark");
		var op = el.options[el.selectedIndex];
		if(op.value == -1){
			Element.hide($("watermarkpic"));
			Element.hide($("div_watermarkpos"));					
			$("WatermarkFile").value = "";
		}else{
			showWaterMark(op);
		}
		el = null;
		op = null;
	}

	function showWaterMark(op){
		var imgLoaded = new Image();
		imgLoaded.onload = function(){
			resizeIfNeed(imgLoaded.height,imgLoaded.width);
			imgLoaded.onload = null;
		}
		imgLoaded.src = op.getAttribute("_picsrc") + "?r="+Math.random();
		$("watermarkpic").src = op.getAttribute("_picsrc");
		Element.show($("watermarkpic"));					
		Element.show($("div_watermarkpos"));					
		$("WatermarkFile").value = op.getAttribute("_picfile");
	} 
	function resizeIfNeed(height,width){
		var h = height,w = width;
		if(height > 124 || width > 97){	
			if(height > width){				
				h = 124;	
				w = 124 * width/height;
			}else{				
				w = 97;
				h = 97 * height/width;
			}			
		}

		$("watermarkpic").width = w;
		$("watermarkpic").height = h;
		
		$("watermarkpic").style.display = "inline";
	}

	function convertBmp(_typeSel){
		$("BmpConverType").value = _typeSel.value;
	}

	//全选或反全选水印位置
	function selectAllPos(){
		var poses = [$("LT"),$("CM"),$("RB")];
		var unchecked = false;
		for(var i=0;i<poses.length;i++){
			if(!poses[i].checked){
				unchecked = true;
				poses[i].checked = true;
			}
		}

		if(!unchecked){
			for(var i=0;i<poses.length;i++){				
				poses[i].checked = false;				
			}
		}
		
		poses = null;
	}

	function truncateStr(_srcstr){
		if(_srcstr.length > 14){
			_srcstr = _srcstr.substr(0,12)+"...";
		}

		return _srcstr;
	}
	</script>
</head>

<body>
<center>
<div style="margin:20px;height:110px">
	<div style="height:25px;width:100%">
		<form id="form_pic" name="fm_pic" style="margin:0;" enctype='multipart/form-data'>
			<input type="file" class="input_file" style="width:280px;" id="PicUpload" name="PicUpload">			
		</form>
		<form id="form_imageinfo">
			<input type="hidden" name="WatermarkFile" id="WatermarkFile" value="">
			<input type="hidden" name="WatermarkPos" id="WatermarkPos" value="">
			<input type="hidden" name="PhotoDocId" id="PhotoDocId" value="">			
			<input type="hidden" name="PhotoFile" id="PhotoFile" value="">
			<input type="hidden" name="BmpConverType" id="BmpConverType" value="">
		</form>
	</div>
	<div style="margin:5px;height:20px;width:100%;">
		<table border="0" cellspacing="1" cellpadding="1" align="left">
		<tbody>
			<tr>
				<td  class="attrname" WCMAnt:param="photo_reupload.jsp.convertformat">BMP图片转换格式：</td>
				<td>
				<select onchange="convertBmp(this);" id="BmpConverTypeSelect">
					<option value="bmp"><span WCMAnt:param="photo_reupload.jsp.noconvert">不转换</span></option>
					<option value="gif">GIF</option>
					<option value="jpg">JPG</option>
				</select>
				</td>
			</tr>
			<tr>
				<td  class="attrname" WCMAnt:param="photo_reload.jsp.selectWaterMark">选择水印：</td><td>
				<span style="float:left;" id="watermarks">
					<select id="selwatermark" style="width:120px;">
						<option value="-1" WCMAnt:param="photo_reload.jsp.noWaterMark">--不添加水印--</option>
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
				</span></td>
			</tr>
		</tbody>
		</table>		
	</div>
	<div id="div_watermarkpos" style="display:none;float:left;font-size:12px;">
		<span style="font-weight:bold;margin-top:5px" title="点击全选" style="cursor:hand" onclick="selectAllPos()" WCMAnt:param="photo_reload.jsp.waterPosition">水印位置：</span>
		<label for="LT"><span WCMAnt:param="photo_reload.jsp.leftTop">左上</span><input type="checkbox" id="LT" value="1" /></label>
		<label for="CM"><span WCMAnt:param="photo_reload.jsp.center">居中</span><input type="checkbox" id="CM" value="2" /></label>
		<label for="RB"><span WCMAnt:param="photo_reload.jsp.rightDown">右下</span><input type="checkbox" id="RB" value="3"/></label>
	</div>
</div>
</center>
<div style="margin:-130px 0 0 310px">
	<img src=""  style="display:none" id="watermarkpic">
</div>
</body>
</html>