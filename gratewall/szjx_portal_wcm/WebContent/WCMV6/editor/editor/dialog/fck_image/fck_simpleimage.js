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

if(oImage){
	// Get the active link.
	var oLink = FCK.Selection.MoveToAncestorNode( 'A' ) ;
}

window.onload = function(){
	// Translate the dialog box texts.
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	// Load the selected element information (if any).
	LoadSelection() ;

	window.parent.SetBtnsRow(false);
	window.parent.SetAutoSize( true ) ;
}
function getImageProperties(){
	if (oImage) {
		var properties = {};

		// The image source.
		properties.src = oImage.src;

		if (oImage.naturalWidth&&oImage.naturalHeight) {
			// Gecko.
			properties.naturalWidth = oImage.naturalWidth;
			properties.naturalHeight = oImage.naturalHeight;
		} else {
			// IE.
			var cssWidth = oImage.style.width;
			var cssHeight = oImage.style.height;
			var legacyWidth = oImage.getAttribute('width',2);
			var legacyHeight = oImage.getAttribute('height',2);
			oImage.style.width = '';
			oImage.style.height = '';
			oImage.removeAttribute('width');
			oImage.removeAttribute('height');
			properties.naturalWidth = oImage.width;
			properties.naturalHeight = oImage.height;
			oImage.style.width = cssWidth||legacyWidth||'';
			oImage.style.height = cssHeight||legacyHeight||'';
//			oImage.width = legacyWidth||'';
//			oImage.height = legacyHeight||'';
		}

		// Current dimensions (CSS trumps legacy attributes).
		properties.width = oImage.style.width || (_IE ?
			oImage.currentStyle.width : oImage.getAttribute('width',2));
		properties.height = oImage.style.height || (_IE ?
			oImage.currentStyle.height : oImage.getAttribute('height',2));

		properties.link = (oLink!=null);
		properties.a = oLink;
		// Determine alignment and text wrapping properties.
		if (oImage.align && oImage.align != '') {
			var align = oImage.align.toLowerCase();
			properties.align = align;
		}
		else if((oImage.style.cssFloat && oImage.style.cssFloat != '')
			|| (oImage.style.styleFloat && oImage.style.styleFloat != '')) {
			var cssFloat = oImage.style.cssFloat || oImage.style.styleFloat;
			if (cssFloat) {
				cssFloat = cssFloat.toLowerCase();
			}
			if (cssFloat == 'left' || cssFloat == 'right') {
				// The image is floated left or right using CSS, allowing text to wrap
				// around it.
				properties.align = cssFloat;
			}
		}
		return properties;
	}
	return null;
}
var currProperties = null;
function LoadSelection(){
	if ( ! oImage ){
		Element.show('tr_imagesrc');
		Element.hide('tr_imageoptions');
		return ;
	}
	Element.hide('tr_imagesrc');
	Element.show('tr_imageoptions');
	$('div_sep').style.height = '240px';
	parent.dialogHeight = '280px' ;
	parent.dialogTop = parseInt(parent.dialogTop,10)-60;
	var properties = currProperties = getImageProperties();
	// Initialize the imageWidth drop-down and the associated imageWidthPx
	// text box.
	var oImageWidth = $('imageWidth');
	var oImageWidthPx = $('imageWidthPx');
	if (!properties.width || properties.width == 'auto') {
		// No width specified; default to 'original.'
		oImageWidth.value = 'original';
		oImageWidthPx.value = '';
	} else {
		var css =
			/([\d.]+)(%|e[mx]\b|p[xtc]\b|in\b|[cm]m\b)/i.exec(properties.width);
		var value = css[1] ? parseFloat(css[1]) : null;
		var unit = css[2] ? css[2].toLowerCase() : null;
		if (!isNaN(value)) {
			if (!unit || unit == 'px') {
				// Pixel width.
				oImageWidth.value = 'custom';
				oImageWidthPx.value = value;
			} else if (unit == '%') {
				// Percentage width.
				oImageWidth.value = 'fit';
				oImageWidthPx.value = '';
			} else {
				// Unknown; default to 'original.'
				oImageWidth.value = 'original';
				oImageWidthPx.value = '';
			}
		}
	}
	// Must call this manually.
	handleChangeWidth();

	// Initialize the imagePosition drop-down and the associated wrapText
	// checkbox.
	if (properties.align) {
		$('cmbAlign').value = properties.align;
	}

	// Initialize the linkImage checkbox.
	$('linkImage').checked = properties.link;
}

// Updates the editor DOM based on the new image properties requested by
// the user.
function UpdateImage(bHasImage) {
	// Update existing image.
	// Get current properties from the editor DOM.
	var current = currProperties;
	if(current==null){
		current = getImageProperties();
	}
	// Update image size.
	var oImageWidth = $('imageWidth');
	var oImageWidthPx = $('imageWidthPx');
	var widthType = oImageWidth.value;
	var widthValue = oImageWidthPx.value;

	// Calculate the requested width/height.
	var requested = calculateDimensions(widthType, widthValue,
		current.naturalWidth, current.naturalHeight);

	// Update the size of the image.
	if (requested.width != current.width) {
		oImage.style.width = requested.width;
	}
	if (requested.height != current.height) {
		oImage.style.height = requested.height;
	}

	// Remove legacy size, if any.
	oImage.removeAttribute('width');
	oImage.removeAttribute('height');

	// Update link.
	var a = current.a;
	var oLinkImage = $('linkImage');
	if (oLinkImage.checked != current.link) {
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
	var oImagePosition = $('cmbAlign');
	// Remove legacy attribute.
	oImage.removeAttribute('align');
	if(oImagePosition.value != ''){
		oImage.align = oImagePosition.value;
		oImage.style.cssFloat = '';
		oImage.style.styleFloat = '';
	}
}

// Calculates the dimensions of the image based on the type and value of
// the user's width selection as well as the original aspect ratio of the
// image.
function calculateDimensions(widthType, widthValue, opt_naturalWidth,
	opt_naturalHeight) {
	// The width/height dimensions to return.
	var dim = {};
	if (widthType == 'fit') {
		// Fit to page width.
		dim.width = '100%';
		dim.height = null;
	} else if (opt_naturalHeight && opt_naturalWidth) {
		// We know the real width and height.
		switch (widthType) {
			case 'custom':
				dim.width = parseFloat(widthValue);
				break;
			case 'xs':
				dim.width = Math.min(160, opt_naturalWidth);
				break;
			case 's':
				dim.width = Math.min(320, opt_naturalWidth);
				break;
			case 'm':
				dim.width = Math.min(640, opt_naturalWidth);
				break;
			case 'l':
				dim.width = Math.min(1024, opt_naturalWidth);
				break;
			case 'xl':
				dim.width = Math.min(1600, opt_naturalWidth);
				break;
			case 'original':
			default:
				dim.width = opt_naturalWidth;
				break;
		}
		dim.height = (dim.width == opt_naturalWidth) ? opt_naturalHeight
		: (dim.width * (opt_naturalHeight / opt_naturalWidth));
		dim.width += 'px';
		dim.height += 'px';
	} else {
		// We don't know the real width and height.
		switch (widthType) {
			case 'custom':
				dim.width = parseFloat(widthValue) + 'px';
				break;
			case 'xs':
				dim.width = '160px';
				break;
			case 's':
				dim.width = '320px';
				break;
			case 'm':
				dim.width = '640px';
				break;
			case 'l':
				dim.width = '1024px';
				break;
			case 'xl':
				dim.width = '1600px';
				break;
			case 'original':
			default:
				dim.width = null;
				break;
		}
		dim.height = null;
	}
	return dim;
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
		SetAttribute( oImage, 'src', $('PicUpload').value ) ;
		SetAttribute( oImage, 'border', 0 ) ;
		SetAttribute( oImage, '_fcksavedurl', $('PicUpload').value ) ;
		SetAttribute( oImage, 'UploadPic', sUploadFileName ) ;
	}
	else
		oEditor.FCKUndo.SaveUndoStep() ;
	
	UpdateImage( bHasImage) ;

	return true ;
}

var isSSL = location.href.indexOf("https://")!=-1;
window.$alert = window.alert;
function UploadFile(_fCallBack){
	var sFileName = $("PicUpload").value;
	if(!FileUploadHelper.validFileExt(sFileName, "jpg,gif,png,bmp")){
		return false;
	}
	$('btnOk').disabled = true;
	Element.show('upload_message');
	$('upload_message').innerHTML = '正在上传,请稍候';
	var lTimeout = setInterval(function(){
		$('upload_message').innerHTML = $('upload_message').innerHTML + '.';
	},5);
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
			});
		}
	}
	YUIConnect.setForm('fm_pic',true,isSSL);
	YUIConnect.asyncRequest('POST','../../../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=PicUpload',callBack);
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
			$('div_sep').style.height = '430px';
			parent.dialogHeight = '480px' ;
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
function showHideOtherLink(_bShow){
	if(_bShow){
		Element.show('imageLinkOther');
	}
	else{
		Element.hide('imageLinkOther');
	}
}

// Updates the dialog UI after the value of the width type drop-down changes.
function handleChangeWidth() {
	var oImageWidth = $('imageWidth');
	var oImageWidthPx = $('imageWidthPx');
	if (oImageWidth && oImageWidthPx) {
		if (oImageWidth.value == 'custom') {
			// Custom size; enable & show pixel input box.
			oImageWidthPx.disabled = false;
			oImageWidthPx.style.visibility = 'visible';
			$('imageWidthPxLabel').style.visibility =
			  'visible';
		} else {
			// Disable & hide pixel input box.
			oImageWidthPx.disabled = true;
			oImageWidthPx.style.visibility = 'hidden';
			$('imageWidthPxLabel').style.visibility =
			  'hidden';
		}
	}
}
