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
var isSSL = location.href.indexOf("https://")!=-1;

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

	window.parent.SetBtnsRow(false);
	window.parent.SetAutoSize( true ) ;
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
	if ( ! oImage ){
		Element.show('tr_imagesrc');
		Element.hide('tr_imageoptions');
		Element.hide('btnLockSizes');
		Element.hide('btnResetSize');
		return ;
	}
	Element.hide('tr_imagesrc');
	Element.show('tr_imageoptions');
	Element.show('btnLockSizes');
	Element.show('btnResetSize');
	$('div_sep').style.height = '320px';
	parent.dialogHeight = '370px' ;
	parent.dialogTop = parseInt(parent.dialogTop,10)-90;
	$('linkImage').checked = bCurrLinkIsSelf;
	$('txtAlt').value = oImage.getAttribute('alt',2)||'' ;
	var tmp = oImage.getAttribute('vspace',2);
	$('txtVSpace').value = (tmp>0)?tmp:'';
	tmp = oImage.getAttribute('hspace',2);
	$('txtHSpace').value = (tmp>0)?tmp:'';
	$('txtBorder').value = oImage.getAttribute('border',2)||'' ;
	$('cmbAlign').value = (oImage.getAttribute('align',2)||'').toLowerCase();

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
function Ok(_bUploaded)
{
	if(oImage==null&&!_bUploaded){
		UploadFile(function(){Ok(true);});
		return;
	}

	var bHasImage = ( oImage != null ) ;
	
	if ( !bHasImage ){
		oImage = FCK.CreateElement( 'IMG' ) ;
		if(!sUploadFileName.match(/^(http|https|ftp):/ig)){
	//		SetAttribute( oImage, 'src', $('PicUpload').value ) ;
	//		SetAttribute( oImage, 'src', '../../system/read_image.jsp?FileName='+sUploadFileName ) ;
			SetAttribute( oImage, 'src', '/wcm/WCMV6/system/read_image.jsp?FileName='+sUploadFileName ) ;
			SetAttribute( oImage, 'border', 0 ) ;
	//		SetAttribute( oImage, '_fcksavedurl', '../../system/read_image.jsp?FileName='+sUploadFileName ) ;
			SetAttribute( oImage, '_fcksavedurl', '/wcm/WCMV6/system/read_image.jsp?FileName='+sUploadFileName ) ;
	//		SetAttribute( oImage, '_fcksavedurl', $('PicUpload').value ) ;
			SetAttribute( oImage, 'UploadPic', sUploadFileName ) ;
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
	else
		oEditor.FCKUndo.SaveUndoStep() ;
	
	UpdateImage( oImage, bHasImage) ;

	return true ;
}

function UpdateImage( oImage, bHasImage )
{
	if($('txtAlt').value){
		oImage.setAttribute("alt"   , $('txtAlt').value ) ;
	}
	else{
		oImage.removeAttribute("alt") ;
	}
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
	if($('cmbAlign').getAttribute('value')){
		oImage.setAttribute("align" , $('cmbAlign').getAttribute('value')) ;
	}
	else{
		oImage.removeAttribute("align") ;
	}
	var oLinkImage = $('linkImage');
	if (oLinkImage.checked != bCurrLinkIsSelf) {//选中情况改变
		if (oLinkImage.checked) {
			// Add the anchor
			var sLnkUrl = oImage.getAttribute('_fcksavedurl',2)||oImage.src;
			if ( oLink )	// Modifying an existent link.
				oLink.href = sLnkUrl ;
			else{			// Creating a new link.
//				sLnkUrl = "javascript:void(0)/*"+new Date().getTime()+"*/;";
				if ( !bHasImage )
					oEditor.FCKSelection.SelectNode( oImage ) ;
				oLink = oEditor.FCK.CreateLink( sLnkUrl )[0] ;
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
	}
}
var isSSL = location.href.indexOf("https://")!=-1;
window.$alert = window.alert;
var YUI_ThrowError = true;
function UploadFile(_fCallBack){
	var sFileName = $("PicUpload").value;
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
	if(!FileUploadHelper.validFileExt(sFileName, "jpg,gif,png,bmp")){
		return false;
	}
	$('btnOk').disabled = true;
	Element.show('upload_message');
	$('upload_message').innerHTML = '正在上传,请稍候';
	var nCount = 0;
	var lTimeout = setInterval(function(){
		if(nCount>=40){
			$('upload_message').innerHTML = '正在上传,请稍候.';
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
			$('upload_message').innerHTML = '上传完成.';
			Element.hide('upload_message');
			var sResponseText = _transport.responseText;
			FileUploadHelper.fileUploadedAlert(sResponseText,function(){
				var fileNames = null;
				eval("fileNames="+sResponseText);
				sUploadFileName = fileNames[0];
				_fCallBack();
				setTimeout(function(){
					window.parent.Cancel(true);
				},10);
			},function(){
				$('btnOk').disabled = false;
			});
		}
	}
	YUIConnect.setForm('fm_pic',true,isSSL);
	try{
		YUIConnect.asyncRequest('POST','../../../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=PicUpload',callBack);
	}catch(err){
		$('btnOk').disabled = false;
		clearInterval(lTimeout);
		Element.hide('upload_message');
	}
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
			icon.alt = link.innerHTML = '隐藏图片选项';
			Element.show(imageOptions);
			$('div_sep').style.height = '450px';
			parent.dialogHeight = '500px' ;
			parent.dialogTop = parseInt(parent.dialogTop,10)-120;
		} else {
			icon.src = 'common/images/expand.gif';
			icon.alt = link.innerHTML = '显示图片选项';
			Element.hide(imageOptions);
			$('div_sep').style.height = '130px';
			parent.dialogHeight = '180px' ;
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
	lockButton.title = bLockRatio ? '锁定比例' : '不锁定比例' ;

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
