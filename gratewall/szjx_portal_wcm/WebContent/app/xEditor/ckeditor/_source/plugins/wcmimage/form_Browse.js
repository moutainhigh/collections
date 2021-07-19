var nChannelId	= getChannelId() || 0;
var CKEDITOR	= parent.CKEDITOR;
var dlg			= CKEDITOR.dialog.getCurrent();
var editor		= dlg.getParentEditor();
var Type		= "DOCUMENT_IMAGE_SIZE_LIMIT";
var isSSL		= location.href.indexOf("https://")!=-1;

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
/*
*通过其他样式展现 浏览文件
*/
function F_Open_dialog(){
	document.getElementById("browserAddress").click();
}
/*
*生成预览效果
*/
function preview_Image(imgSrc){
	//var imgSrc = document.getElementById("browserAddress").value;
	var imgDom = document.getElementById("preview_Box").childNodes;
	imgDom[0].src = imgSrc;
	imgDom[0].style.display = 'inline';
}
/**
*单击确定按钮时执行的代码
*/
function onOk(){
	var dom = $('browserAddress');
	var sValue = dom.value;
	var isNetAddress = sValue.match(/^(http|https|ftp|rtsp|mms):/i);

	if(!isNetAddress && !sValue.match(/\.(jpg|jpeg|gif|png|bmp)$/i)){
		alert(editor.lang.wcmimage.invalidaddress || "无效的图片地址");
		dom.focus();
		return false;
	}
	uploadImage();
	return false;
}
/**
*利用YUI对文件Form元素上传图片
*/
function uploadImage(){
	var sUploadFile = 'browserAddress';
	YUIConnect.setForm('fm_pic', true, isSSL);

	try{
		//显示上传进度
		startUploading();
		var sUrl = '../../../../../system/file_upload_dowith.jsp?ResponseType=2&FileParamName=' + sUploadFile+'&Type='+Type + '&channelId=' + nChannelId;
		YUIConnect.asyncRequest('POST', sUrl, {
			upload : function(_transport){
				//关闭上传进度
				endUploading();
				var sResponseText = _transport.responseText;

				FileUploadHelper.fileUploadedAlert(sResponseText, {
					succ : function(){
						/*renderImage函数的参数为图片路径，需传入fileNames[0]参数*/
						var fileNames = eval(sResponseText);
						/*存储src到input*/
						var imgSrc = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+fileNames[0];
						document.getElementById('fileNames').value = imgSrc;
						//renderImage(WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+fileNames[0]);
						preview_Image(imgSrc);
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
/**
*图片上传完之后的处理,插入【将此处逻辑处理为ok 插入逻辑，之前仅仅保留fileNames】
*/
function renderImage(sImgUrl){
	var oImage = editor.document.createElement('img');
	var dom = oImage.$;

	dom.setAttribute("src", sImgUrl);
	var index = sImgUrl.indexOf("FileName=");
	if(index >= 0){
		dom.setAttribute("UploadPic", sImgUrl.substring(index + ("FileName=".length)));
	}
	editor.insertElement(oImage);

	dlg.hide();
}

/**
*输入框的效果
*/
function value_Clear(){
	$('inputAddress').value = '';
}
function ok(){
	var fileNames = document.getElementById('fileNames').value;
	renderImage(fileNames);
}

/*
*根据图片的尺寸和显示框的尺寸来设定图片居中显示
*/
function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxHeight = 280;
	var maxWidth = 480;
	var loadImg = new Image();
	loadImg.src = _loadImg.src;
	var height = loadImg.height, width = loadImg.width;
	if(height > maxHeight || width > maxWidth){
		if(height > width){
			width = maxHeight * width/height;
			//要考虑到原本框的比例,如果按照高度(较长边)缩放后,宽度仍大于最大值情况
			if(width > maxWidth) {
				width = maxWidth;
				_loadImg.width = width;

			}else {
				height = maxHeight;
				_loadImg.height = height;

			}
		}else{
			height = maxWidth * height/width;
			//要考虑到原本框的比例,如果按照宽度(较长边)缩放后,高度仍大于最大值情况
			if(height > maxHeight) {
				height = maxHeight;
						_loadImg.height = height;

			}else {
				width = maxWidth;
						_loadImg.width = width;

			}
		}
	}
}
