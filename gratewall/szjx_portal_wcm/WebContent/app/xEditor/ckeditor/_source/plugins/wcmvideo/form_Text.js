var nChannelId	= getChannelId() || 0;
var CKEDITOR	= parent.CKEDITOR;
var dlg			= CKEDITOR.dialog.getCurrent();
var editor		= dlg.getParentEditor();
var Type		= "DOCUMENT_VIDEO_SIZE_LIMIT";
var isSSL		= location.href.indexOf("https://")!=-1;
var sAcceptExt = "asf,avi,mpg,mpeg,mpe,mov,rm,rmvb,wmv,mp4";//rm,rmvb,
var oEmbed ;
function getChannelId(){
	try{
		return top.getParameter("channelId");
	}catch(err1){
		try{
			return top.m_nChannelId || top.channelId;
		}catch(err2){
		}
		//donothing
	}
}

function onOk(){
	//根据输入框的地址处理
	var uploadFile = 'inputAddress'
	var sFileName = $(uploadFile).value;
	try{
		FileUploadHelper.validFileExt(sFileName, sAcceptExt);
	}catch(err){
		alert(err.message + 'CKLANGerr');
		$(uploadFile).focus();
		return;
	}
	_fCallBack(sFileName);
}

function _fCallBack(sUploadFileName){
	if ( !oEmbed )
	{
		oEmbed		= editor.document.createElement( 'EMBED' ) ;
		oFakeImage  = null ;
	}
	UpdateEmbed( oEmbed ) ;
		oEmbed.src = sUploadFileName ;
		Src = oEmbed.src;
		SetAttribute( oEmbed, "src" , Src ) ;
	if ( !oFakeImage ){
		var oldHeight = document.getElementById('setting_height').value || '200px';
		var oldwidth = document.getElementById('setting_width').value || '200px';
		
		oFakeImage = editor.createFakeElement(oEmbed,'cke_flash','flash');
		oFakeImage.setAttribute( '_fckflash', 'true', 0 ) ;
		oFakeImage.setAttribute( 'height', oldHeight ) ;
		oFakeImage.setAttribute( 'width', oldwidth ) ;
	}
	
/*生成预览*/
	document.getElementById('preview_img').style.backgroundImage = "url(images/preview_blue.png)";
	var domImg = document.getElementById('image_replace');
	domEmbed = oEmbed.$;
	domEmbed.src = Src;
	domImg.parentNode.replaceChild(domEmbed,domImg);
	return oEmbed ;
}
function Ok(){
	try{
		if(oFakeImage) {
			editor.insertElement(oFakeImage);
			setTimeout(function(){
				dlg.hide();
			},10);
		}
	}catch(err){
		alert("您还没有将视频上传");
	}
}

/**
*显示上传进度信息
*/
function startUploading(){
	var sLoadingMsg = editor.lang.wcmimage.uploading || "正在上传,请稍候...";
	Element.show('upload_message');
	$('upload_message').innerHTML = sLoadingMsg;

	var nCount = 0;
	window.UploadingTimeout = setInterval(function(){
		if(nCount>=40){
			$('upload_message').innerHTML = sLoadingMsg;
			nCount = 1;
		}
		else{
			$('upload_message').innerHTML = $('upload_message').innerHTML + '.';
			nCount++;
		}
	}, 20);
}
/**
*关闭上传进度信息
*/
function endUploading(){
	Element.hide('upload_message');
	$('upload_message').innerHTML = "";

	clearInterval(window.UploadingTimeout);
}


/*上传后处理中用到的方法*/
function UpdateEmbed( e )
{
	var sMediaType = $$F('mediatype');
	SetAttribute( e, 'mediatype' ,  sMediaType) ;
	//辨别多媒体
	/*
	*...
	*/
	if(GetE('setting_width').value){
		SetAttribute( e, "width" , GetE('setting_width').value ) ;
	}
	else{
		e.removeAttribute("width");
	}
	if(GetE('setting_height').value){
		SetAttribute( e, "height", GetE('setting_height').value ) ;
	}
	else{
		e.removeAttribute("height");
	}
	e.setAttribute('autostart' , $('setting_autoStart').checked ? 1:0);
	e.setAttribute('loop' , $('setting_loop').checked ? 1:0);
}
function SetAttribute( element, attName, attValue )
{
	if ( attValue == null || attValue.length == 0 )
		element.removeAttribute( attName, 0 ) ;			// 0 : Case Insensitive
	else
		element.setAttribute( attName, attValue, 0 ) ;	// 0 : Case Insensitive
}

function GetAttribute( element, attName, valueIfNull )
{
	var oAtt = element.attributes[attName] ;

	if ( oAtt == null || !oAtt.specified )
		return valueIfNull ? valueIfNull : '' ;

	var oValue = element.getAttribute( attName, 2 ) ;

	if ( oValue == null )
		oValue = oAtt.nodeValue ;

	return ( oValue == null ? valueIfNull : oValue ) ;
}
function GetE( elementId )
{
	return document.getElementById( elementId )  ;
}

