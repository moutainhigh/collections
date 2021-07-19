var nChannelId	= getChannelId() || 0;
var CKEDITOR	= parent.CKEDITOR;
var dlg			= CKEDITOR.dialog.getCurrent();
var editor		= dlg.getParentEditor();
var Type		= "DOCUMENT_IMAGE_SIZE_LIMIT";
var isSSL		= location.href.indexOf("https://")!=-1;


/**
*单击确定按钮时执行的代码
*/
function onOk(){

	//valid first
	var dom = $($('browserAddressRadio').checked ? 'browserAddress' : 'inputAddress');
	var sValue = dom.value;
	var isNetAddress = sValue.match(/^(http|https|ftp|rtsp|mms):/i);

	if(!isNetAddress && !sValue.match(/\.(jpg|jpeg|gif|png|bmp)$/i)){
		alert(editor.lang.wcmimage.invalidaddress || "无效的图片地址");
		dom.focus();
		return false;
	}

	if($('browserAddressRadio').checked){//使用浏览本地文件
		uploadImage();
	}else{
		if(isNetAddress){//使用网络地址
			renderImage(sValue);
		}else{//直接输入了本地文件地址
			uploadLocalImage(sValue);
		}
	}
	return false;
}


/**
*利用YUI对文件Form元素上传图片
*/
function uploadImage(){
	var sUploadFile = $('browserAddressRadio').checked ? 'browserAddress' : 'inputAddress';
	YUIConnect.setForm('fm_pic', true, isSSL);

	try{
		//显示上传进度
		startUploading();
		var sUrl = '../../../../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=' + sUploadFile+'&Type='+Type + '&channelId=' + nChannelId;
		YUIConnect.asyncRequest('POST', sUrl, {
			upload : function(_transport){
				//关闭上传进度
				endUploading();
				var sResponseText = _transport.responseText;

				debugger
				FileUploadHelper.fileUploadedAlert(sResponseText, {
					succ : function(){
						var fileNames = eval(sResponseText);
						renderImage(WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+fileNames[0]);
					},
					err : function(texts){
						alert(texts);
					}
				});
			}
		});
	}catch(err){
		alert(err.message);
		//just skip it.
	}
}


/**
*本地路径图片，使用ActiveX控件进行上传
*/
function uploadLocalImage(sUploadFileName){
	if(!CKEDITOR.OfficeActiveX.IsUsed()){
		alert(editor.lang.wcmimage.activexcontrol || "上传控件没有启用或者在此浏览器下不支持，请使用本地文件浏览的方式进行上传");
		return;
	}
	//显示上传进度
	startUploading();

	//如果是本地路径图片，需要先上传
	if(sUploadFileName.match(/^(file:\/{2,})/ig)){
		sUploadFileName = sUploadFileName.replace(/^(file:\/{2,})/ig,'');
	}

	sUploadFileName = decodeURIComponent(sUploadFileName.replace(/\//g,'\\'));
	sUploadFileName = CKEDITOR.OfficeActiveX.UploadFile(sUploadFileName);

	//关闭上传进度
	endUploading();
	
	if(sUploadFileName){
		renderImage(WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+sUploadFileName);
	}
}


/**
*图片上传完之后的处理
*/
function renderImage(sImgUrl){
	var oImage = editor.document.createElement('img');
	var dom = oImage.$;

	dom.setAttribute("src", sImgUrl);
	//dom.setAttribute("_fcksavedurl", sImgUrl);
	var index = sImgUrl.indexOf("FileName=");
	if(index >= 0){
		dom.setAttribute("UploadPic", sImgUrl.substring(index + ("FileName=".length)));
	}

	if($('txtAlt').value){
		dom.setAttribute("title"   , $('txtAlt').value ) ;
		dom.setAttribute("alt"   , $('txtAlt').value ) ;
	}
	else{
		dom.removeAttribute("alt") ;
		dom.removeAttribute("title") ;
	}
	if($('txtWidth').value){
		dom.setAttribute("width" , $('txtWidth').value ) ;
	}
	else{
		dom.removeAttribute("width") ;
	}
	if($('txtHeight').value){
		dom.setAttribute("height", $('txtHeight').value ) ;
	}
	else{
		dom.removeAttribute("height") ;
	}
	if($('txtBorder').value){
		dom.style.borderWidth = '';
		dom.setAttribute("border", $('txtBorder').value ) ;
	}
	else{
		dom.removeAttribute("border") ;
		dom.style.borderWidth = 0;
	}
	if($('txtVSpace').value){
		dom.setAttribute("vspace", $('txtVSpace').value ) ;
	}
	else{
		dom.removeAttribute("vspace") ;
	}
	if($('txtHSpace').value){
		dom.setAttribute("hspace", $('txtHSpace').value ) ;
	}
	else{
		dom.removeAttribute("hspace") ;
	}
	if($('cmbAlign').value){
		dom.setAttribute("align" , $('cmbAlign').value) ;
	}
	else{
		dom.removeAttribute("align") ;
	}

	if($('cbIgnore').checked){
		dom.setAttribute("ignore" , "1");
	}else{
		dom.removeAttribute("ignore");
	}


	if($('linkImage').checked){
		var ckLinkEl = editor.document.createElement('A');
		var domLink = ckLinkEl.$;
		domLink.setAttribute("href", dom.getAttribute("src"));
		domLink.setAttribute("UploadPic", dom.getAttribute("UploadPic"));
		domLink.setAttribute("target", '_blank');
		domLink.appendChild(dom);
	}

	editor.insertElement(ckLinkEl ? ckLinkEl : oImage);

	dlg.hide();

	//如果是需要裁剪与编辑
	if($('NeedReEdit').checked){
		var parameters = "photo="+encodeURIComponent(sImgUrl) + "&width=" + ($("txtWidth").value || dom.getAttribute('width',2)||'')  + "&height=" + ($("txtHeight").value || dom.getAttribute('height',2)||'') ;
		var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters;
		var dialogArguments = window;
		var nWidth	= window.screen.width - 12;
		var nHeight = window.screen.height - 60;
		var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
		var bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
		if(bound == null){
			return;
		}
		dom.setAttribute("src", WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+bound.FN);
		dom.setAttribute("UploadPic", bound.FN);

		if(domLink){
			domLink.setAttribute("href", dom.getAttribute("src"));
			domLink.setAttribute("UploadPic", dom.getAttribute("UploadPic"));
		}
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


Event.observe(window, 'load', function(){
	/**
	*绑定是否显示更多属性的事件
	*/
	Event.observe('img-options-trigger', 'click', function(event){
		Element.toggle('img-options');

		if(Element.visible('img-options')){
			Element.addClassName('img-options-trigger', 'collapse');
			dlg.resize(500, 400);
			dlg.layout();
		}else{
			Element.removeClassName('img-options-trigger', 'collapse');
			dlg.resize(500, 200);
			dlg.layout();
		}
	});

	Event.observe('inputAddress', 'focus', function(event){
		$('inputAddressRadio').checked = true;
	});

	Event.observe('browserAddress', 'focus', function(event){
		$('browserAddressRadio').checked = true;
		$('cbIgnore').checked = false;
	});
	


});



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

function mapfile(fn){
	var r = ["/webpic/",fn.substring(0,8),"/",fn.substring(0,10),"/",fn];
	return r.join('');
}


/**
*判断输入的字符是否为数字；如果是，返回true，否则返回false
*/
function IsDigit( e ){
	if ( !e )
		e = event ;

	var iCode = ( e.keyCode || e.charCode ) ;

	return (
			( iCode >= 48 && iCode <= 57 )		// Numbers
			|| (iCode >= 37 && iCode <= 40)		// Arrows
			|| iCode == 8						// Backspace
			|| iCode == 46						// Delete
	) ;
}
