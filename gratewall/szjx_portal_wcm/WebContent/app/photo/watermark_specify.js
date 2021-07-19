var sImgUrl = getParameter('photo');
var sWaterMarkUrl = getParameter('watermark');
var img = new Image();
// 裁剪
var Cropper = new uvumiCropper('img',{
	coordinates:true,
	preview:false,
	toolBoxWidth:200,
	toolBox:'toolBox',
	imgDiv:{
		x:600,
		y:480
	},
	src:sImgUrl
});

function init(){
	img.onload=function(){
		document.getElementById('orignwidth').innerHTML = img.width;
		document.getElementById('orignheight').innerHTML = img.height;
	}
	img.src = sImgUrl;

	//初始化水印图片	
	var watermarkLoaded = new Image();
	watermarkLoaded.onload = function(){
		document.getElementById('watermarkwidth').innerHTML = watermarkLoaded.width;
		document.getElementById('watermarkheight').innerHTML = watermarkLoaded.height;
		resizeIfNeed(watermarkLoaded.height,watermarkLoaded.width);
		watermarkLoaded.onload = null;

		//图片宽度需要在图片加载完成后设置
		var main =$("container");
		var show = $("watermarkpic");	
		var showoffsetHeight = show.offsetHeight;
		var mainclientHeight = main.clientHeight;
		//图片在设置行高的时候以下边缘计算
		main.style.lineHeight = 2*showoffsetHeight+(mainclientHeight-showoffsetHeight)+"px";
	}
	watermarkLoaded.src = sWaterMarkUrl + "?r="+Math.random();
	$("watermarkpic").src = sWaterMarkUrl;
}

function resizeIfNeed(height,width){
	var h = height,w = width;
	if(height > 200 || width > 200){	
		if(height > width){				
			h = 200;	
			w = 200 * width/height;
		}else{				
			w = 200;
			h = 200 * height/width;
		}			
	}

	$("watermarkpic").width = w;
	$("watermarkpic").height = h;
	
	$("watermarkpic").style.display = "inline";
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
