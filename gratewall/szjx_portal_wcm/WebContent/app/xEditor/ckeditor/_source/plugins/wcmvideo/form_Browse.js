var nChannelId	= getChannelId() || 0;
var CKEDITOR	= parent.CKEDITOR;
var dlg			= CKEDITOR.dialog.getCurrent();
var editor		= dlg.getParentEditor();
var Type		= "DOCUMENT_VIDEO_SIZE_LIMIT";
var isSSL		= location.href.indexOf("https://")!=-1;
var sAcceptExt = "asf,avi,mpg,mpeg,mpe,mov,rm,rmvb,wmv";//rm,rmvb,
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
	var uploadFile = 'file_browserAddress'
	var sFileName = $(uploadFile).value;
	try{
		FileUploadHelper.validFileExt(sFileName, sAcceptExt);
	}catch(err){
		alert(err.message + 'CKLANGerr');
		$(uploadFile).focus();
		return;
	}
	//显示上传进度
	startUploading();
	//上传
	var callBack = {
		"upload":function(_transport){
			//关闭上传进度
			endUploading();
			var sResponseText = _transport.responseText;
			var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,{
				succ:function(){
					var fileNames = null;
					eval("fileNames="+sResponseText);
					sUploadFileName = fileNames[0];
					_fCallBack(sUploadFileName);
				}
			});
			if(bAlert)return;
		}
	}
	var sUrl = '../../../../../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=' + uploadFile+"&Type="+Type + "&channelId=" + nChannelId;
	YUIConnect.setForm('fm_file',true,isSSL);
	try{
		YUIConnect.asyncRequest('POST',sUrl,callBack);
	}catch(err){
		alert(err.message);	
	}
}

function mapWebFile(_fn){
	if(_fn.indexOf("W0") == 0){
		return "/webpic/"+_fn.substr(0,8)+"/"+_fn.substr(0,10)+"/"+_fn;
	}else{
		return WCMConstants.WCM6_PATH + "file/read_video.jsp?FileName=" + _fn;
	}
}

function _fCallBack(_sUploadFileName){
	if ( !oEmbed )
	{
		oEmbed		= editor.document.createElement( 'EMBED' ) ;
		oFakeImage  = null ;
	}
	UpdateEmbed( oEmbed ) ;
	if(!sUploadFileName.match(/^(http|https|ftp|rtsp|mms):/ig)){
		oEmbed.src = mapWebFile(_sUploadFileName);
		/**/
		Src = oEmbed.src;
		SetAttribute( oEmbed, "src" , Src ) ;
		/**/
		SetAttribute( oEmbed, "uploadPic" , sUploadFileName ) ;
		var bRealPlayer = sUploadFileName.match(/(^rtsp:|\.rmvb$|\.rm$)/ig);
		if(bRealPlayer){
			SetAttribute( oEmbed, 'type'			, 'audio/x-pn-realaudio-plugin' ) ;
			SetAttribute( oEmbed, 'console'			, 'clip1' ) ;
			SetAttribute( oEmbed, 'controls'		, 'Imagewindow,ControlPanel' ) ;
		}
	}
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

