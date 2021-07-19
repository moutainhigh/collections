/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2007 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Scripts related to the Image dialog window (see fck_image.html).
 */
var oEditor		= window.parent.InnerDialogLoaded() ;
var FCK			= oEditor.FCK ;
var FCKLang		= oEditor.FCKLang ;
var FCKConfig	= oEditor.FCKConfig ;
var FCKDebug	= oEditor.FCKDebug ;
var Type = "DOCUMENT_IMAGE_SIZE_LIMIT";
var isSSL = location.href.indexOf("https://")!=-1;
var nChannelId = 0;
nChannelId = getChannelId() || nChannelId;

var sImageScale = FCKConfig.ImageScale || window.ImageScale;
var doScaleValidErroMessage = "";
// Get the selected image (if available).
var oImage = FCK.Selection.GetSelectedElement() ;
if ( oImage && oImage.tagName != 'IMG' && !( oImage.tagName == 'INPUT' && oImage.type == 'image' ) )
	oImage = null ;

var UploadFileName = null;
var bCurrLink = false;
var bCurrLinkIsSelf = false;
if(oImage){
	// Get the active link.
	var oLink = FCK.Selection.MoveToAncestorNode( 'A' ) ;
	if(oLink!=null){
		bCurrLink = true;
		bCurrLinkIsSelf = (oLink.getAttribute('_fcksavedurl',2)||'').trim()==(oImage.getAttribute('_fcksavedurl',2)||'').trim();
	}
}

window.onload = function(){
	// Translate the dialog box texts.
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	GetE('btnLockSizes').title = FCKLang.DlgImgLockRatio ;
	GetE('btnResetSize').title = FCKLang.DlgBtnResetSize ;
	// Load the selected element information (if any).
	LoadSelection() ;
	$('div_sep').style.height = '130px';
	parent.dialogHeight = '200px' ;
	if(oImage)
		toggleOptions();
	parent.dialogWidth = '500px' ;
	window.parent.SetBtnsRow(false);
	if(oEditor.FCKBrowserInfo.IsIE) {
		window.parent.SetAutoSize( true ) ;
	}
	if(!window.top.actualTop.m_bIsPhotoLibPluginEnabled){
		$("NeedReEdit").disabled = true;
	}
}
var oImageOriginal ;
function UpdateOriginal( resetSize , _oScaleSize)
{
	if ( !oImage )
		return ;
	
	oImageOriginal = document.createElement( 'IMG' ) ;	// new Image() ;

	if ( resetSize ){
		oImageOriginal.onload = function(){
			this.onload = null ;
			if(_oScaleSize)
				OnSizeChanged(_oScaleSize["by"],_oScaleSize["value"]);
			else
				ResetSizes() ;
		}
	}

	oImageOriginal.src = oImage.src ;
}

function LoadSelection(){
	Element.show('tr_imagesrc');
	if ( ! oImage ){
		//Element.show('tr_imagesrc');
		//Element.hide('tr_imageoptions');
		Element.hide('btnLockSizes');
		Element.hide('btnResetSize');
		return ;
	}
	//Element.hide('tr_imagesrc');
	//Element.hide('remotePic');
	//Element.show('tr_imageoptions');
	Element.show('btnLockSizes');
	Element.show('btnResetSize');
	$('div_sep').style.height = '320px';
	parent.dialogTop = parseInt(parent.dialogTop,10)-90;
	$('linkImage').checked = bCurrLinkIsSelf;
	$('txtAlt').value = oImage.getAttribute('title',2)|| oImage.getAttribute('alt',2) ||'' ;
	var tmp = oImage.getAttribute('vspace',2);
	$('txtVSpace').value = (tmp>0)?tmp:'';
	tmp = oImage.getAttribute('hspace',2);
	$('txtHSpace').value = (tmp>0)?tmp:'';
	$('txtBorder').value = oImage.getAttribute('border',2)||'' ;
	$('cmbAlign').value = (oImage.getAttribute('align',2)||'').toLowerCase();

	//是否采用远程路径也需要同步调整
	var bIgnore = oImage.getAttribute('ignore',2);
	if(!bIgnore){
		$("cbIgnore").checked = false;
	}
	var iWidth, iHeight ;

	var regexSize = /^\s*(\d+)px\s*$/i ;
	
	if ( oImage.style.width )
	{
		var aMatch  = oImage.style.width.match( regexSize ) ;
		if ( aMatch )
		{
			iWidth = aMatch[1] ;
			oImage.width = iWidth;
			oImage.style.width = '' ;
		}
	}

	if ( oImage.style.height )
	{
		var aMatch  = oImage.style.height.match( regexSize ) ;
		if ( aMatch )
		{
			iHeight = aMatch[1] ;
			oImage.height = iHeight;
			oImage.style.height = '' ;
		}
	}
	$('txtWidth').value = iWidth||oImage.getAttribute('width',2)||'' ;
	$('txtHeight').value = iHeight||oImage.getAttribute('height',2)||'';
	if($('txtWidth').value==''&&$('txtHeight').value!=''){
		UpdateOriginal(true,{by:"Height",value:$('txtHeight').value}) ;
	}
	else if($('txtWidth').value!=''&&$('txtHeight').value==''){
		UpdateOriginal(true,{by:"Width",value:$('txtWidth').value}) ;
	}
	else if($('txtWidth').value==''&&$('txtHeight').value==''){
		UpdateOriginal(true);
	}
	else{
		UpdateOriginal();
	}
	UpdatePreview() ;
}

//#### The OK button was hit.
function isShow(name){
	if($(name)){
		return $(name).style.display == "" ? true : false;
	}
	return false;
}
function Ok(_bUploaded)
{
	//if(oImage==null&&!_bUploaded){
	var uploadFile = "PicUpload";
	if(isShow("filespan") &&  $("block").offsetHeight > 0){
		uploadFile = "PicUpload1";
	}else if(isShow("textspan") &&  $("block").offsetHeight > 0){
		uploadFile = "PicUpload2";
	}
	if(!_bUploaded){
		UploadFile(function(){Ok(true);});
		return;
	}

	var bHasImage = ( oImage != null ) ;

	//if ( !bHasImage ){
	
	if (_bUploaded &&  $(uploadFile).value.trim().length > 0 ){
	//	oImage = oImage || FCK.CreateElement( 'IMG' ) ;
		oImage = document.createElement('IMG');
		setSrc(oImage,sUploadFileName);
	}
	else
		oEditor.FCKUndo.SaveUndoStep() ;
	
		UpdateImage( oImage, bHasImage) ;

	return true ;
}

function UpdateImage( oImage, bHasImage )
{
	//是否需要再编辑
	var bEdit = $("NeedReEdit") && $("NeedReEdit").checked;
	var src = getSrc(oImage);
	if(bEdit && !src.match(/^(http|https|ftp):/ig)){
		src = edit(oImage,src);
	}else{
		oImage = FCK.CreateElement( 'IMG' ) ;
		setSrc(oImage,src);
		changeImage(oImage);
	}
}
var isSSL = location.href.indexOf("https://")!=-1;
window.$alert = window.alert;
var YUI_ThrowError = true;
function UploadFile(_fCallBack){
	var uploadFile = "PicUpload";
	if(isShow("filespan") &&  $("block").offsetHeight > 0){
		uploadFile = "PicUpload1";
	}else if(isShow("textspan") &&  $("block").offsetHeight > 0){
		uploadFile = "PicUpload2";
	}
	var sFileName = $(uploadFile).value;
	
	if(uploadFile == "PicUpload2" && !sFileName.match(/^(http|https|ftp|rtsp|mms):/ig)){
		alert(FCKLang.INVALIDFILE);
		return false;
	}
	if( oImage && sFileName.length==0 ){
		_fCallBack();
		setTimeout(function(){
			window.parent.Cancel(true);
		},10);
		return false;
	}
	if(sFileName.match(/^(http|https|ftp):/ig)){
		sUploadFileName = sFileName;
		_fCallBack();
		setTimeout(function(){
			window.parent.Cancel(true);
		},10);
		return false;
	}
	try{
		FileUploadHelper.validFileExt(sFileName, "jpg,gif,png,bmp");
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
			$(uploadFile).focus();
		});
		return;
	}
	$('btnOk').disabled = true;
	Element.show('upload_message');
	$('upload_message').innerHTML = FCKLang.LOADING;
	var nCount = 0;
	var lTimeout = setInterval(function(){
		if(nCount>=40){
			$('upload_message').innerHTML = FCKLang.LOADING;
			nCount = 1;
		}
		else{
			$('upload_message').innerHTML = $('upload_message').innerHTML + '.';
			nCount++;
		}
	},20);
	var callBack = {
		"upload":function(_transport){
			clearInterval(lTimeout);
			$('upload_message').innerHTML = FCKLang.LOADED;
			Element.hide('upload_message');
			var sResponseText = _transport.responseText;
			var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,{
				succ:function(){
					var fileNames = null;
					eval("fileNames="+sResponseText);
					sUploadFileName = fileNames[0];
					var sImgSrc = WCMConstants.WCM6_PATH + "system/read_image.jsp?FileName="+sUploadFileName;
					var img = new Image();
					img.onload = function(){
						if(doScaleValid(this)) {
							_fCallBack();
							setTimeout(function(){
								window.parent.Cancel(true);
							},10);
						}else {
							Ext.Msg.$alert(doScaleValidErroMessage);
							return false;
						}
					}
					img.src = sImgSrc;
				}
			});
			if(bAlert)return;
		}
	}
	YUIConnect.setForm('fm_pic',true,isSSL);
	try{
		var sUrl = '../../../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=' + uploadFile+'&Type='+Type + '&channelId=' + nChannelId;
		YUIConnect.asyncRequest('POST',sUrl,callBack);
		clearInterval(lTimeout);
		Element.hide('upload_message');
	}catch(err){
	}
	$('btnOk').disabled = false;
	return false;
}

window.$alert = window.alert;

var showOptions = false;
// Shows/hides the "more options" part of the UI.
function toggleOptions(){
	var icon = $('toggleIcon');
	var link = $('toggleLink');
	var imageOptions = $('tr_imageoptions');
	if (icon && link) {
		showOptions = Element.visible(imageOptions);
		if (!showOptions) {
			icon.src = 'common/images/collapse.gif';
			icon.title = link.innerHTML = FCKLang.HIDEPIC;
			Element.show(imageOptions);
			//Element.show('remotePic');
			$('div_sep').style.height = '450px';
			parent.dialogHeight = '580px' ;
			parent.dialogWidth = '500px' ;
			parent.dialogTop = parseInt(parent.dialogTop,10)-120;
		} else {
			icon.src = 'common/images/expand.gif';
			icon.title = link.innerHTML = FCKLang.SHOWPIC;
			Element.hide(imageOptions);
			//Element.show('remotePic');
			$('div_sep').style.height = '130px';
			parent.dialogHeight = '250px' ;
			parent.dialogWidth = '500px' ;
			parent.dialogTop = parseInt(parent.dialogTop,10)+120;
		}
	}
}


var eImgPreview ;
var eImgPreviewLink ;

function UpdatePreview()
{
	if ( !eImgPreview || !eImgPreviewLink )
		return ;
	if ( GetE('txtUrl').value.length == 0 )
		eImgPreviewLink.style.display = 'none' ;
	else
	{
//		eImgPreview.src = GetE('txtUrl').value;
		eImgPreview.style.display = '' ;
		UpdateImage( eImgPreview, true ) ;

		if ( GetE('txtLnkUrl').value.trim().length > 0 )
			eImgPreviewLink.href = 'javascript:void(0);' ;
		else{
			//SetAttribute( eImgPreviewLink, 'href', '' ) ;
			eImgPreviewLink.removeAttribute('href') ;
		}
		eImgPreviewLink.style.display = '' ;
	}
}
var bLockRatio = true ;

function SwitchLock( lockButton )
{
	bLockRatio = !bLockRatio ;
	lockButton.className = bLockRatio ? 'BtnLocked' : 'BtnUnlocked' ;
	lockButton.title = bLockRatio ? FCKLang.DlgImgLockRatio: FCKLang.UnDlgImgLockRatio;

	if ( bLockRatio )
	{
		if ( GetE('txtWidth').value.length > 0 )
			OnSizeChanged( 'Width', GetE('txtWidth').value ) ;
		else
			OnSizeChanged( 'Height', GetE('txtHeight').value ) ;
	}
}

// Fired when the width or height input texts change
function OnSizeChanged( dimension, value )
{
	// Verifies if the aspect ration has to be mantained
	if ( oImageOriginal && bLockRatio )
	{
		var e = dimension == 'Width' ? GetE('txtHeight') : GetE('txtWidth') ;
		var self = dimension == 'Width' ? GetE('txtWidth') : GetE('txtHeight') ;
		if ( value.length == 0)
		{
			e.value = '' ;
			UpdatePreview() ;
			return;
		}
		else if( isNaN( value ))// ||parseInt(value)!=value)
		{
			e.value = '' ;
			return ;
		}
		if ( dimension == 'Width' )
			value = value == 0 ? 0 : Math.round( oImageOriginal.height * ( value  / oImageOriginal.width ) ) ;
		else
			value = value == 0 ? 0 : Math.round( oImageOriginal.width  * ( value / oImageOriginal.height ) ) ;

		if ( !isNaN( value ) )
			e.value = value ;
	}

	UpdatePreview() ;
}

// Fired when the Reset Size button is clicked
function ResetSizes()
{
	if ( ! oImageOriginal ) return ;

	GetE('txtWidth').value  = oImageOriginal.width ;
	GetE('txtHeight').value = oImageOriginal.height ;

	UpdatePreview() ;
}

function Check(_eInput){
	var sValue = _eInput.value;
	if(isNaN(sValue)){
		_eInput.value = '';
		return false;
	}
	return true;
}

function show(blockid){
	if(blockid == "filespan"){
		Element.show("filespan");
		Element.hide("textspan");
		$("filespanradio").checked = true;
		$("textspanradio").checked = false;
	}else{
		Element.hide("filespan");
		Element.show("textspan");
		$("textspanradio").checked = true;
		$("filespanradio").checked = false;
	}
}

function edit(oImage,sourceURL){
	//如果是本地路径图片，需要先上传
	sourceURL = uploadLocalImg(sourceURL);
	var sSrc =  WCMConstants.WCM6_PATH + "system/read_image.jsp?FileName="+sourceURL;
	var parameters = "photo="+encodeURIComponent(sSrc) + "&width=" + ($("txtWidth").value || oImage.getAttribute('width',2)||'')  + "&height=" + ($("txtHeight").value || oImage.getAttribute('height',2)||'') ;
	var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters;
	var dialogArguments = window;
	var nWidth	= window.screen.width - 12;
	var nHeight = window.screen.height - 60;
	var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
	var bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
	if(bound == null){
		return;
	}
	oImage = FCK.CreateElement( 'IMG' ) ;
	setSrc(oImage,bound.FN);
	changeImage(oImage);
}

function setSrc(oImage,sUploadFileName){
	if(!sUploadFileName.match(/^(http|https|ftp):/ig)){
		if(sUploadFileName.indexOf("W")<0){
			//如果是本地路径图片，需要先上传
			sUploadFileName = uploadLocalImg(sUploadFileName);
			SetAttribute( oImage, 'src', WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+sUploadFileName ) ;
			SetAttribute( oImage, '_fcksavedurl', WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+sUploadFileName ) ;
		}else{
			SetAttribute( oImage, 'src', mapfile(sUploadFileName) ) ;
			SetAttribute( oImage, '_fcksavedurl', mapfile(sUploadFileName) ) ;
		}		
		SetAttribute( oImage, 'UploadPic', sUploadFileName ) ;
		SetAttribute( oImage, 'border', 0 ) ;
	}
	else{
		SetAttribute( oImage, 'src', sUploadFileName ) ;
		SetAttribute( oImage, 'border', 0 ) ;
		SetAttribute( oImage, '_fcksavedurl', sUploadFileName ) ;
		if($('cbIgnore') && $('cbIgnore').checked){
			SetAttribute( oImage, "ignore" , "1" ) ;
		}
	}
}

function getSrc(oImage){
	var imgsrc = oImage.getAttribute("uploadpic") || oImage.getAttribute("oldsrc");
	if(imgsrc == null){
		imgsrc = oImage.getAttribute("src");
		//对直接引用web上的图片需要先上传再处理
		if(!imgsrc.match(/^(http|https|ftp):/ig) && imgsrc.indexOf("W0") >=0){
			var sGroup = imgsrc.split("/");
			imgsrc = sGroup[sGroup.length - 1];
			if(imgsrc.indexOf("=") > 0)
				imgsrc = imgsrc.split("=")[imgsrc.split("=").length - 1];
		}
	}
	return imgsrc;
}
function changeImage(oImage){
	var bHasImage = ( oImage != null ) ;
	var bEdit = $("NeedReEdit") && $("NeedReEdit").checked;
	if($('txtAlt').value){
		oImage.setAttribute("title"   , $('txtAlt').value ) ;
		oImage.setAttribute("alt"   , $('txtAlt').value ) ;
	}
	else{
		oImage.removeAttribute("alt") ;
		oImage.removeAttribute("title") ;
	}
	if(!bEdit){
		if($('txtWidth').value){
			oImage.setAttribute("width" , $('txtWidth').value ) ;
		}
		else{
			oImage.removeAttribute("width") ;
		}
		if($('txtHeight').value){
			oImage.setAttribute("height", $('txtHeight').value ) ;
		}
		else{
			oImage.removeAttribute("height") ;
		}
	}
	if($('txtVSpace').value){
		oImage.setAttribute("vspace", $('txtVSpace').value ) ;
	}
	else{
		oImage.removeAttribute("vspace") ;
	}
	if($('txtHSpace').value){
		oImage.setAttribute("hspace", $('txtHSpace').value ) ;
	}
	else{
		oImage.removeAttribute("hspace") ;
	}
	if($('txtBorder').value){
		oImage.style.borderWidth = '';
		oImage.setAttribute("border", $('txtBorder').value ) ;
	}
	else{
		oImage.removeAttribute("border") ;
		oImage.style.borderWidth = 0;
	}
	if($('cmbAlign').value){
		oImage.setAttribute("align" , $('cmbAlign').value) ;
	}
	else{
		oImage.removeAttribute("align") ;
	}
	var oLinkImage = $('linkImage');
	if (oLinkImage.checked != bCurrLinkIsSelf) {//选中情况改变
		if (oLinkImage.checked) {
			// Add the anchor
			var sLnkUrl = oImage.getAttribute('_fcksavedurl',2)||oImage.src;
			if ( oLink ){	// Modifying an existent link.
				oLink.href = sLnkUrl ;
				oLink.appendChild(oImage);
			}else{			// Creating a new link.
//				sLnkUrl = "javascript:void(0)/*"+new Date().getTime()+"*/;";
				if ( !bHasImage )
					oEditor.FCKSelection.SelectNode( oImage ) ;
				//oLink = oEditor.FCK.CreateLink( sLnkUrl )[0] ;
				oLink = FCK.CreateElement( 'A' ) ;
				oLink.href = sLnkUrl;
				oLink.target = '_blank';
				oLink.appendChild(oImage);
				if ( !bHasImage ){
					oEditor.FCKSelection.SelectNode( oLink ) ;
					oEditor.FCKSelection.Collapse( false ) ;
				}
			}
			if( !bHasImage ){
				SetAttribute( oLink, 'UploadPic', sUploadFileName ) ;
			}
			else if(oImage.getAttribute('UploadPic',2)){
				SetAttribute( oLink, 'UploadPic', oImage.getAttribute('UploadPic',2) ) ;
			}
			SetAttribute( oLink, '_fcksavedurl', sLnkUrl ) ;
			SetAttribute( oLink, 'target', '_blank' ) ;
		} else {
			// Remove the anchor
			FCK.ExecuteNamedCommand( 'Unlink' ) ;
		}
	}else if(oLink != null){
		oLink.appendChild(oImage);
	}
}
function getChannelId(){
	try{
		var v = window.parent.actualTop.channelId;
		//兼容父页面没有定义该变量,但定义了同名的元素的情况
		return v.nodeType?v.value:v;
	}catch(err){
		//donothing
	}
}

function mapfile(fn){
	var r = ["/webpic/",fn.substring(0,8),"/",fn.substring(0,10),"/",fn];
	return r.join('');
}

function uploadLocalImg(sUploadFileName){
	//如果是本地路径图片，需要先上传
	if(sUploadFileName.match(/^(file:\/{2,})/ig)){
		sUploadFileName = decodeURIComponent(sUploadFileName.replace(/^(file:\/{2,})/ig,'')
				.replace(/\//g,'\\'));
		sUploadFileName = oEditor.OfficeActiveX.m_OfficeActiveX.UploadFile(sUploadFileName);
		sUploadFileName = oEditor.OfficeActiveX._ExtractFileName(sUploadFileName);				
	}
	return sUploadFileName;
}

function doScaleValid(oImage){//返回是否合法
	//当前图片尺寸
	var imgWidth = oImage.width;
	var imgHeight = oImage.height;

	var bVaildScale = false;
	var bVaildRate = false;
	//解析体统配置的图片尺寸限制
	var setWidth = sImageScale.split(",")[0];
	var setHeight = sImageScale.split(",")[1];
	if(setWidth>0 || setHeight>0) {
		bVaildScale = true;
	}
	var setRate = sImageScale.split(",")[2];
	if(setRate > 0) {
		bVaildRate = true;
	}
	var isConsiderRate = sImageScale.split(",")[3]>0;
	
	//图片比例是否符合的优先级高于图片宽高是否符合，先判断比例
	/* 1 校验图片比例 */
	if(bVaildRate) {
		/* 1.1 考虑横竖幅*/
		if(isConsiderRate) {
			var imageRate = imgWidth>imgHeight ? imgWidth/imgHeight : imgHeight/imgWidth;
			if(setRate<1) {
				setRate = 1/setRate;
			}
			/*
			if(setWidth>setHeight) {
				doScaleValidErroMessage = "图片太宽了!\n\n不符合系统自动配置的(横幅)比例：[ 宽/高 = " + setRate + "]";
			}else {
				doScaleValidErroMessage = "图片太高了!\n\n不符合系统自动配置的(竖幅)比例：[ 高/宽 = " + setRate + "]";
			}*/
			if(imageRate>setRate) {
				doScaleValidErroMessage = "图片不符合系统自动配置比例：[ " + setRate + "]";
				return false;
			}
		/* 1.2 不考虑横竖幅*/
		}else {
			//若不考虑图片横竖幅，则默认为横幅：imgWidth/imgHeight
			var imageDefaultRate = imgWidth/imgHeight;
			if((setRate<1 && imageDefaultRate<setRate) || (setRate>=1 && imageDefaultRate>setRate)) {
				doScaleValidErroMessage = "图片不符合系统默认配置的宽高比：[ 宽/高 = " + setRate + "]";
				return false
			}
		}
	}
	/* 2 校验图片规格 */
	if((imgHeight>setHeight || imgWidth>setWidth) && bVaildScale) {
		doScaleValidErroMessage = "上传图片尺寸规格超过限制，支持规格为[" + setWidth + "x" + setHeight +"]"
		return false;
	}
	/* 3 条件都满足，返回true */
	return true;
}