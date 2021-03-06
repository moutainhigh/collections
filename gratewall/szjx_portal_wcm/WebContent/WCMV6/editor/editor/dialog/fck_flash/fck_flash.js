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
 * Scripts related to the Flash dialog window (see fck_flash.html).
 */

var oEditor		= window.parent.InnerDialogLoaded() ;
var FCK			= oEditor.FCK ;
var FCKLang		= oEditor.FCKLang ;
var FCKConfig	= oEditor.FCKConfig ;

//#### Dialog Tabs

// Set the dialog tabs.
//window.parent.AddTab( 'Info', oEditor.FCKLang.DlgInfoTab ) ;

if ( FCKConfig.FlashUpload )
	window.parent.AddTab( 'Upload', FCKLang.DlgLnkUpload ) ;

if ( !FCKConfig.FlashDlgHideAdvanced )
	window.parent.AddTab( 'Advanced', oEditor.FCKLang.DlgAdvancedTag ) ;

// Function called when a dialog tag is selected.
function OnDialogTabChange( tabCode )
{
	ShowE('divInfo'		, ( tabCode == 'Info' ) ) ;
	ShowE('divUpload'	, ( tabCode == 'Upload' ) ) ;
	ShowE('divAdvanced'	, ( tabCode == 'Advanced' ) ) ;
}
var sFlashExt = "swf";
var sVideoExt = "asf,avi,mpg,mpeg,mpe,mov,rm,rmvb,wmv";
var sAudioExt = "wav,mid,midi,mp3,mpa,mp2,ra,ram,rm,wma";
var sAcceptExt = sFlashExt;

// Get the selected flash embed (if available).
var oFakeImage = FCK.Selection.GetSelectedElement() ;
var oEmbed ;
if ( oFakeImage )
{
	if ( oFakeImage.tagName == 'IMG' && oFakeImage.getAttribute('_fckflash') )
		oEmbed = FCK.GetRealElement( oFakeImage ) ;
	else
		oFakeImage = null ;
}
window.onload = function()
{
	// Translate the dialog box texts.
	oEditor.FCKLanguageManager.TranslatePage(document) ;
	// Load the selected element information (if any).
	LoadSelection() ;

	window.parent.SetBtnsRow(false);
	window.parent.SetAutoSize( true ) ;

	// Activate the "OK" button.
//	window.parent.SetOkButton( true ) ;
}
var isAddMode = false;
function LoadSelection()
{
	if ( ! oEmbed ){
		isAddMode = true;
		ShowE('tr_local',true );
		ShowE('tr_mediatype',true );
		$('div_sep').style.height = '110px';
		return ;
	}
	ShowE('tr_local',false );
	ShowE('tr_mediatype',false );
	$('div_sep').style.height = '80px';
	var sMediaType = GetAttribute( oEmbed, 'mediatype', 'flash' ) ;
	$('rd_'+sMediaType).checked = true;
	GetE('txtWidth').value  = GetAttribute( oEmbed, 'width', '' ) ;
	GetE('txtHeight').value = GetAttribute( oEmbed, 'height', '' ) ;

	// Get Advances Attributes
	GetE('txtAttId').value		= oEmbed.id ;
	switch(sMediaType){
		case 'flash':
			GetE('chkAutoPlay').checked	= GetAttribute( oEmbed, 'play', 'true' ) == 'true' ;
			break;
		case 'video':
		case 'audio':
			GetE('chkAutoPlay').checked	= GetAttribute( oEmbed, 'autostart', '1' ) != '0' ;
			break;
	}
	GetE('chkLoop').checked		= GetAttribute( oEmbed, 'loop', 'true' ) == 'true' ;
	GetE('chkMenu').checked		= GetAttribute( oEmbed, 'menu', 'true' ) == 'true' ;
	GetE('cmbScale').value		= GetAttribute( oEmbed, 'scale', '' ).toLowerCase() ;
	
	GetE('txtAttTitle').value		= oEmbed.title ;

	if ( oEditor.FCKBrowserInfo.IsIE )
	{
		GetE('txtAttClasses').value = oEmbed.getAttribute('className') || '' ;
		GetE('txtAttStyle').value = oEmbed.style.cssText ;
	}
	else
	{
		GetE('txtAttClasses').value = oEmbed.getAttribute('class',2) || '' ;
		GetE('txtAttStyle').value = oEmbed.getAttribute('style',2) ;
	}

	UpdatePreview() ;
}
var sUploadFileName = null;

//#### The OK button was hit.
function Ok(_bUploaded)
{
	if(isAddMode&&!_bUploaded){
		UploadFile(function(){Ok(true);});
		return false;
	}
	var oOrig = oEmbed;
	if ( !oEmbed )
	{
		oEmbed		= FCK.EditorDocument.createElement( 'EMBED' ) ;
		oFakeImage  = null ;
	}
	UpdateEmbed( oEmbed ) ;
	if(isAddMode){
		if(!sUploadFileName.match(/^(http|https|ftp|rtsp|mms):/ig)){
			oEmbed.src = 'javascript:void(0);/*'+sUploadFileName+'*/' ;
			SetAttribute( oEmbed, "uploadPic" , sUploadFileName ) ;
		}
		else{
			oEmbed.src = sUploadFileName ;
			if($('cbIgnore') && $('cbIgnore').checked){
				SetAttribute( oEmbed, "ignore" , "1" ) ;
			}
		}
		var bRealPlayer = sUploadFileName.match(/(^rtsp:|\.rmvb$|\.rm$)/ig);
		if(bRealPlayer){
				SetAttribute( oEmbed, 'type'			, 'audio/x-pn-realaudio-plugin' ) ;
				SetAttribute( oEmbed, 'console'			, 'clip1' ) ;
				SetAttribute( oEmbed, 'controls'			, 'Imagewindow,ControlPanel' ) ;
		}
	}

	if ( !oFakeImage )
	{
		oFakeImage	= oEditor.FCKDocumentProcessor_CreateFakeImage( 'FCK__Flash', oEmbed ) ;
		oFakeImage.setAttribute( '_fckflash', 'true', 0 ) ;
		oFakeImage	= FCK.InsertElementAndGetIt( oFakeImage ) ;
	}
	else
		oEditor.FCKUndo.SaveUndoStep() ;
	
	oEditor.FCKFlashProcessor.RefreshView( oFakeImage, oEmbed ) ;
	return true ;
}

function UpdateEmbed( e )
{
	var sMediaType = $$F('mediatype');
	SetAttribute( e, 'mediatype' ,  sMediaType) ;
	switch(sMediaType){
		case 'flash':
			SetAttribute( e, 'type'			, 'application/x-shockwave-flash' ) ;
			SetAttribute( e, 'pluginspage'	, 'http://www.macromedia.com/go/getflashplayer' ) ;
			SetAttribute( e, 'play', GetE('chkAutoPlay').checked ? 'true' : 'false' ) ;
			break;
		case 'video':
		case 'audio':
			SetAttribute( e, 'autostart' , $('chkAutoPlay').checked) ;
			break;
	}
	if(GetE('txtWidth').value){
		SetAttribute( e, "width" , GetE('txtWidth').value ) ;
	}
	else{
		e.removeAttribute("width");
	}
	if(GetE('txtHeight').value){
		SetAttribute( e, "height", GetE('txtHeight').value ) ;
	}
	else{
		e.removeAttribute("height");
	}
	
	// Advances Attributes

	SetAttribute( e, 'id'	, GetE('txtAttId').value ) ;
	SetAttribute( e, 'scale', GetE('cmbScale').value ) ;
	
	SetAttribute( e, 'loop', GetE('chkLoop').checked ? 'true' : 'false' ) ;
	SetAttribute( e, 'menu', GetE('chkMenu').checked ? 'true' : 'false' ) ;

	SetAttribute( e, 'title'	, GetE('txtAttTitle').value ) ;

	if ( oEditor.FCKBrowserInfo.IsIE )
	{
		SetAttribute( e, 'className', GetE('txtAttClasses').value ) ;
		e.style.cssText = GetE('txtAttStyle').value ;
	}
	else
	{
		SetAttribute( e, 'class', GetE('txtAttClasses').value ) ;
		SetAttribute( e, 'style', GetE('txtAttStyle').value ) ;
	}
}

var ePreview ;

function SetPreviewElement( previewEl )
{
	ePreview = previewEl ;
	var sInputId = (currType=='local')?'FileUpload':'txtUrl';
	
	if ( GetE(sInputId).value.length > 0 )
		UpdatePreview() ;
}

function UpdatePreview()
{
	if ( !ePreview )
		return ;
		
	while ( ePreview.firstChild )
		ePreview.removeChild( ePreview.firstChild ) ;

	var sInputId = 'FileUpload';
	if ( GetE(sInputId).value.length == 0 )
		ePreview.innerHTML = '&nbsp;' ;
	else
	{
		var oDoc	= ePreview.ownerDocument || ePreview.document ;
		var e		= oDoc.createElement( 'EMBED' ) ;
		
		e.src		= GetE(sInputId).value ;
//		e.type		= 'application/x-shockwave-flash' ;
		e.width		= '100%' ;
		e.height	= '100%' ;
		
		ePreview.appendChild( e ) ;
	}
}

// <embed id="ePreview" src="fck_flash/claims.swf" width="100%" height="100%" style="visibility:hidden" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer">

function BrowseServer()
{
	OpenFileBrowser( FCKConfig.FlashBrowserURL, FCKConfig.FlashBrowserWindowWidth, FCKConfig.FlashBrowserWindowHeight ) ;
}

function SetUrl( url, width, height )
{
	GetE('txtUrl').value = url ;
	
	if ( width )
		GetE('txtWidth').value = width ;
		
	if ( height ) 
		GetE('txtHeight').value = height ;

	UpdatePreview() ;

//	window.parent.SetSelectedTab( 'Info' ) ;
}

function OnUploadCompleted( errorNumber, fileUrl, fileName, customMsg )
{
	switch ( errorNumber )
	{
		case 0 :	// No errors
			alert( 'Your file has been successfully uploaded' ) ;
			break ;
		case 1 :	// Custom error
			alert( customMsg ) ;
			return ;
		case 101 :	// Custom warning
			alert( customMsg ) ;
			break ;
		case 201 :
			alert( 'A file with the same name is already available. The uploaded file has been renamed to "' + fileName + '"' ) ;
			break ;
		case 202 :
			alert( 'Invalid file type' ) ;
			return ;
		case 203 :
			alert( "Security error. You probably don't have enough permissions to upload. Please check your server." ) ;
			return ;
		default :
			alert( 'Error on file upload. Error number: ' + errorNumber ) ;
			return ;
	}

	SetUrl( fileUrl ) ;
	GetE('frmUpload').reset() ;
}

var oUploadAllowedExtRegex	= new RegExp( FCKConfig.FlashUploadAllowedExtensions, 'i' ) ;
var oUploadDeniedExtRegex	= new RegExp( FCKConfig.FlashUploadDeniedExtensions, 'i' ) ;

function CheckUpload()
{
	var sFile = GetE('txtUploadFile').value ;
	
	if ( sFile.length == 0 )
	{
		alert( 'Please select a file to upload' ) ;
		return false ;
	}
	
	if ( ( FCKConfig.FlashUploadAllowedExtensions.length > 0 && !oUploadAllowedExtRegex.test( sFile ) ) ||
		( FCKConfig.FlashUploadDeniedExtensions.length > 0 && oUploadDeniedExtRegex.test( sFile ) ) )
	{
		OnUploadCompleted( 202 ) ;
		return false ;
	}
	
	return true ;
}


var isSSL = location.href.indexOf("https://")!=-1;
window.$alert = window.alert;
var YUI_ThrowError = true;
function UploadFile(_fCallBack){
	var sFileName = $("FileUpload").value;
	if( oEmbed && sFileName.length==0 ){
		_fCallBack();
		setTimeout(function(){
			window.parent.Cancel(true);
		},10);
		return false;
	}
	if(sFileName.match(/^(http|https|ftp|rtsp|mms):/ig)){
		sUploadFileName = sFileName;
		_fCallBack();
		setTimeout(function(){
			window.parent.Cancel(true);
		},10);
		return false;
	}
	if(!FileUploadHelper.validFileExt(sFileName, sAcceptExt)){
		return false;
	}
	$('btnOk').disabled = true;
	Element.show('upload_message');
	$('upload_message').innerHTML = '????????????,?????????';
	var nCount = 0;
	var lTimeout = setInterval(function(){
		if(nCount>=40){
			$('upload_message').innerHTML = '????????????,?????????.';
			nCount = 1;
		}
		else{
			$('upload_message').innerHTML = $('upload_message').innerHTML + '.';
			nCount++;
		}
	},50);
	var callBack = {
		"upload":function(_transport){
			clearInterval(lTimeout);
			$('upload_message').innerHTML = '????????????.';
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
	YUIConnect.setForm('fm_file',true,isSSL);
	try{
		YUIConnect.asyncRequest('POST','../../../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=FileUpload',callBack);
	}catch(err){
		$('btnOk').disabled = false;
		clearInterval(lTimeout);
		Element.hide('upload_message');
	}
	return false;
}

function changeMediaType(_sType){
	switch(_sType){
		case 'flash':
			sAcceptExt = sFlashExt;
			break;
		case 'video':
			sAcceptExt = sVideoExt;
			break;
		case 'audio':
			sAcceptExt = sAudioExt;
			break;
	};
}