var sImgUrl = getParameter('photo');
var img = new Image();

var CropperOption = {
	coordinates:true,
	preview:true,
	preview:'preview',
	toolBoxWidth:200,
	toolBox:'toolBox',
	imgDiv:{
		x:600,
		y:480
	},
	src:sImgUrl
};

//如果设置了disabledSize，则不能调整裁剪的大小
if(getParameter('disabledSize')){
	CropperOption['handles'] = false;
}

//支持裁剪固定大小的图片

(function(){
	var nWidth = getParameter('width');
	var nHeight = getParameter('height');
	if(nWidth || nHeight){

		//由于不能监听到裁剪组件的加载完成的事件，故在此轮询
		setTimeout(function(){
			if(!Cropper.widthcoord){
				setTimeout(arguments.callee, 100);
				return;
			}
			if(nWidth){
				Cropper.widthcoord.value = nWidth;
				Cropper.widthcoord.disabled = true;
			}
			if(nHeight){
				Cropper.heightcoord.value = nHeight;
				Cropper.heightcoord.disabled = true;
			}

			Cropper.updateFromInput({target : Cropper.widthcoord});
		}, 100);
	}
})();


// 裁剪
var Cropper = new uvumiCropper('img', CropperOption);


function init(){
	img.onload=function(){
		document.getElementById('orignwidth').innerHTML = img.width;
		document.getElementById('orignheight').innerHTML = img.height;
	}
	img.src = sImgUrl;
}

// 预览裁剪后的效果
function previewPhoto(){
	var src = sImgUrl
	//var src="big1.jpg";
	var parameters = "photo="+encodeURIComponent(src)+"&width="+Cropper.width+"&height="+Cropper.height+"&top="+Cropper.top+"&left="+Cropper.left;
	var sUrl = "photo_crop_preview.html?"+parameters;
	var nWidth = Math.max(500, Cropper.width+40);
	var nHeight = Math.max(450,Cropper.height+200);
	var windowTop=(window.screen.height-nHeight)/2;
	var windowLeft=(window.screen.width-nWidth)/2;
	var dialogArguments = window;
	var sFeatures = "dialogTop:"+windowTop+";dialogLeft:"+windowLeft+";center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight+"px;status:no;resizable:no;";
	 window.showModalDialog(sUrl, dialogArguments, sFeatures);
}
function getParameter(_sName, _sQuery, encode){
	if(_sName ==null ||_sName=='undefined')
		return '';
	var query = _sQuery || location.search;
	if(query == null || query.length==0) return '';
	var fn = (typeof encode == 'function') ? encode : (encode === false ? (function(k){return k;}) : decodeURIComponent);
	var arr = query.substring(1).split('&');
	_sName = _sName.toUpperCase();
	for (var i=0,n=arr.length; i<n; i++){
		if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
			return fn(arr[i].substring(_sName.length + 1));
		}
	}
	return '';
}


function onOK(){
	debugger;
	if(!confirm("你确定要裁剪此图片？")){
		return;
	}
	var filename = sImgUrl.substring(sImgUrl.indexOf("FileName=")+"FileName=".length);
	var parames = {
		Filename : filename,
		left:Cropper.left,
		top:Cropper.top,
		width:Cropper.width,
		height:Cropper.height
	}
	window.parent.onOK(parames);
}
