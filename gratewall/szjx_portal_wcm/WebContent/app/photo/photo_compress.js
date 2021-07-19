//获取图片路径属性 src
var photo = getParameter("photo");
var FileName=getParameter("FileName");
var width = getParameter("Width");
var height = getParameter("Height");
var bAddTitleName = getParameter("bAddTitleName");
//
var sWatermarkFile = getParameter("WaterMarkFile");
var sWatermarkPos = getParameter("WatermarkPos");

//var FileName=
var OrigFileName;
var OrigData = {};
var img= new Image();
img.onload = function(){
	window.imgLoaded = true;
	if(window.pageLoaded)init();
};
if(photo){
	img.src=photo;
	OrigFileName = photo;
}else if(FileName){
	img.src="../file/read_image.jsp?FileName="+FileName;
	OrigFileName = FileName;
}
function init(){
		if(bAddTitleName==1) {
			$("photo_FileNames").style.display = "inline";
		}
		if($("photo_FileName")) {
			$("photo_FileName").value = getFileName(img.src);
		}
		//获取文件属性
		$("show_photo_img").src=img.src;
		$("orignWidth").innerHTML = img.width+"px";
		$("orignHeight").innerHTML = img.height+"px";
	
		$('widthRatio').value = '100';
		$('heightRatio').value = '100';
		// 如果只传一个参数，则按照等比原则进行缩放
		if(height || width){
			if(!height){
				height= parseInt(img.height*width/img.width);
			}
			if(!width){
				width= parseInt(img.width*height/img.height);
			}
		}
		//缩放图片参数
		$("newWidth").value= width || img.width;
		$("newHeight").value=height || img.height;
		OrigData.width=width || img.width;
		OrigData.height =height || img.height;
		//initValidPercentScale();
		resizePhoto();
		changeScaleType();
		ValidationHelper.initValidation();	
		if(height || width){
			applyAction();
		}
		width =null;
		height = null;
}
// 初始化缩放范围
function initValidPercentScale(){
	var maxWidth = img.width*2;
	var maxHeight = img.height*2;
	var minWidth = parseInt(img.width*0.15);
	var minHeight = parseInt(img.height*0.15);
	var newWidth = $('newWidth')
	newWidth.setAttribute("validation","type:'int',required:'true',value_range:'"+minWidth+","+maxWidth+"',desc:'宽度',showid:'validate_tip'");
	ValidationFactory.makeValidator(newWidth);
	var newHeight = $('newHeight')
	newHeight.setAttribute("validation","type:'int',required:'true',value_range:'"+minHeight+","+maxHeight+"',desc:'高度',showid:'validate_tip'");
	ValidationFactory.makeValidator(newHeight);
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
function $(el){
	el=(typeof(el)=="string")?document.getElementById(el):el;
	return el;
}

function resizePhoto(){
	//img.src=$("show_photo_img").getAttribute("src");
	//获取边宽的大小
	var maxWidth =$("show_photo").offsetWidth-5 ;
	var maxHeight = $('show_photo').offsetHeight-5;
	var showPhotoImg=$("show_photo_img");
	if($("suitablesize").checked){
		var xScaleRatio =maxWidth/img.width;
		var yScaleRatio =maxHeight/img.height;
		if(xScaleRatio<yScaleRatio){
			showPhotoImg.style.width=maxWidth+"px";
			showPhotoImg.style.height= img.height*xScaleRatio+"px";
			//设置显示样式
			showPhotoImg.style.marginTop = (maxHeight-img.height*xScaleRatio)/2+"px";
			showPhotoImg.style.marginLeft = "0px";
		}else{
			showPhotoImg.style.width=img.width*yScaleRatio+"px";
			showPhotoImg.style.height= maxHeight+"px";
			//设置显示样式
			showPhotoImg.style.marginLeft = (maxWidth-img.width*yScaleRatio)/2+"px";
			showPhotoImg.style.marginTop = "0px";
		}
	}else{
		//图片显示原始大小
		showPhotoImg.style.width=img.width+"px";
		showPhotoImg.style.height=img.height+"px";
		if(maxWidth>img.width){
			showPhotoImg.style.marginLeft = (maxWidth-img.width)/2+"px";
		}else{
			showPhotoImg.style.marginLeft="0px";
		}
		if(maxHeight>img.height){
			showPhotoImg.style.marginTop = (maxHeight-img.height)/2+"px";
		}else{
			showPhotoImg.style.marginTop="0px";
		}
	}
}
var MyNumber = new Object();
MyNumber.parseInt = function(_string) {
	if(_string == null || _string == "") {
		return 0;
	}
	var integer = parseInt(_string);
	if(_string - integer < 0.5) {
		return (integer);
	} else {
		return (integer + 1);
	}
}
function changeScaleType(){
	var newWidth = $("newWidth");
	var newHeight = $("newHeight");
	var widthRatio = $("widthRatio");
	var heightRatio = $("heightRatio");	
	if($("scaletype_pixel").checked){		
		newWidth.disabled = false;
		newHeight.disabled = false;
		widthRatio.disabled = true;
		heightRatio.disabled = true;
	}else{
		newWidth.disabled = true;
		newHeight.disabled = true;
		widthRatio.disabled = false;
		heightRatio.disabled = false;	
	}
}
function changeScaleValue(_type){
	var newWidth = $("newWidth");
	var newHeight = $("newHeight");
	var widthRatio = $("widthRatio");
	var heightRatio = $("heightRatio");	

	var zKeep = $("keep_scale_ratio").checked;
	var valueChanged = null;
	var nImgWidth = img.width;
	var nImgHeight = img.height;
	switch(_type) {
		case "newWidth" :
			valueChanged = newWidth.value;
			var  widthRatioValue = MyNumber.parseInt(valueChanged * 100 / nImgWidth);
			widthRatio.value = widthRatioValue;
			if(zKeep) {
				heightRatio.value = widthRatio.value;
				newHeight.value = MyNumber.parseInt(nImgHeight * heightRatio.value / 100);
			}
			break;
		case "newHeight" :
			valueChanged = newHeight.value;
			var heightRatioValue = MyNumber.parseInt(valueChanged * 100 /  nImgHeight);
			heightRatio.value = heightRatioValue;
			if(zKeep) {
				widthRatio.value = heightRatio.value;
				newWidth.value = MyNumber.parseInt(nImgWidth * widthRatio.value / 100);
			}
			break;
		case "widthRatio" :
			valueChanged = widthRatio.value;
			var newWidthValue = MyNumber.parseInt(nImgWidth * valueChanged / 100);
			newWidth.value = newWidthValue;
			if(zKeep) {
				heightRatio.value = valueChanged;
				newHeight.value = MyNumber.parseInt(nImgHeight * heightRatio.value / 100);
			}
			break;
		case "heightRatio" :
			valueChanged = heightRatio.value;
			var newHeightValue = MyNumber.parseInt(nImgHeight * valueChanged/ 100);
			newHeight.value = newHeightValue;
			if(zKeep) {
				widthRatio.value = valueChanged;
				newWidth.value = MyNumber.parseInt(nImgWidth * widthRatio.value / 100);
			}
			break;
		default :
	}
}

function resetAll(){
	$("newWidth").value=OrigData.width;
	$("newHeight").value=OrigData.height;

	$('widthRatio').value = '100';
	$('heightRatio').value = '100';
}

function updataInfo(_info,_type){
	$('info').innerHTML = _info;
	if(!_type)
		return;
	$(_type).className="input_red";
}
function returnDefault(_el){
	$(_el).className="";
	$("info").innerHTML="&nbsp;";
}
function crop(){
	nWidth = 900;
	nHeight = 600;
	var parameters = "photo="+encodeURIComponent(img.src);
	var sUrl = "photo_crop.html?"+parameters;
	var dialogArguments = window;
	var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
	var cropInfo = window.showModalDialog(sUrl, dialogArguments, sFeatures);
	if(!cropInfo)
		return;
	reSetImg(cropInfo.FN);
}
function reSetImg(FN){
	img.src = "../../file/read_image.jsp?FileName="+FN;
	//$("show_photo_img").src=img.src;
	setTimeout(init,500);
}

function applyAction(){	
	//validate & prepare data.	
	// 压缩图片
	FileName = img.src.substring(img.src.indexOf("FileName=")+"FileName=".length);
	var data = {FileName:FileName,FolderExt:"W0"};
	var zValidated = false;
	zValidated = ValidationHelper.validAndDisplay("newWidth","newHeight");
	Object.extend(data,{ScaleWidth:$F("newWidth"),ScaleHeight:$F("newHeight")});
	
	if(!zValidated) return;
	
	ProcessBar.init(wcm.LANG.PHOTO_CONFIRM_27 || '正在处理，请稍候...');	
	ProcessBar.addState(wcm.LANG.PHOTO_CONFIRM_27 || '正在处理，请稍候...');
	ProcessBar.start();	
	BasicDataHelper.call("wcm6_photo","scaleImage",data,true,function(_transport,_json){
		reSetImg($v(_json,"fn"));
		ProcessBar.exit();
	});
}
function onOK(){
	var photo_FileName;
	if($("photo_FileName")) {
		photo_FileName = $("photo_FileName").value;
		if(photo_FileName.length>200) {
			var max_len = $("photo_FileName").getAttribute("validation_max_len");
			var desc = $("photo_FileName").getAttribute("validation_desc");
			ValidationHelper.doAlert(String.format(ValidatorLang.TEXT45 || '{0}大于最大长度{1}.',desc,max_len));
			return false;
		}
	}
	//document.getElementById("photo_FileName").value = getFileName(img.src);
	//var FileName= photo_FileName || getFileName(img.src);
	var FileName= getFileName(img.src);
	//进行水印处理
	if(m_arWatermarks && $F('wmsel') != -1){
		var wmpos = [];
		var wmposcheckbox = [$("LT"),$("CM"),$("RB")];
		for(var i=0;i<3;i++){
			if(wmposcheckbox[i].checked){
				wmpos.push(wmposcheckbox[i].value);
			}
		}
		if(wmpos.length == 0){
			wmpos.push(3);
		}

		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var oPostData = {wmpos:wmpos.join(","),wmpic:$F('wmsel'),pic:FileName};
		oHelper.JspRequest(
			"getWaterMark.jsp",
			oPostData,  false,
			function(transport, json){
				var result = transport.responseText;
				if(!result) window.close();
				callback(result);	
			}
		);
	}else{
		callback(FileName,photo_FileName);
	}
}

function callback(result,imageName){
	var parames ={FN:result,imageName:imageName}
	if(OrigFileName.indexOf(result)<0){
		window.returnValue=parames;
	}else{
		window.returnValue=null;
	}
	window.returnValue=parames;
	var callback = getParameter("callback");
	if(callback && window.dialogArguments)
		window.dialogArguments[callback](parames);
	window.close();	
}

function getFileName(_src){
	if(_src.indexOf("FileName=")>=0){
		return _src.substring(_src.indexOf("FileName=")+9);
	}else{
		return _src;
	}
}

function initPhotowms(){
	if(!m_arWatermarks) return;
	if(m_arWatermarks.length > 0){
		var wmsel = $("wmsel");
		for(var i=0,len=m_arWatermarks.length;i<len;i++){
			var wm = m_arWatermarks[i];
			var op = document.createElement("option");
			op.value = wm.v;
			op.text = wm.n;
			wmsel.add(op);
		}
		Event.observe($("wmsel"),'change',showwm);
		if(sWatermarkFile && sWatermarkFile!=0 && sWatermarkFile!="null") {
			$("wmsel").value = sWatermarkFile;
			showwm();
		}
		if(sWatermarkPos) {
			var arr = sWatermarkPos.split(",");
			for (var i=0; i<arr.length; i++) {
				switch(arr[i]){
					case "1":$("LT").checked = true;
						break;
					case "2":$("CM").checked = true;
						break;
					case "3":$("RB").checked = true;
						break;
					default:$("RB").checked = true;
						break;
				}
			}
		}else {
			$("RB").checked = true;
		}
	}

	Event.observe($("wmpos"),'click',selectAllPos);
}
//全选或反全选水印位置
function selectAllPos(){
	var poses = [$("LT"),$("CM"),$("RB")];
	var unchecked = false;
	for(var i=0;i<poses.length;i++){
		if(!poses[i].checked){
			unchecked = true;
			poses[i].checked = true;
		}
	}

	if(!unchecked){
		for(var i=0;i<poses.length;i++){				
			poses[i].checked = false;				
		}
	}
	
	poses = null;
}
function showwm(){
	var v = $F("wmsel");	
	if(v == -1){
		Element.hide($("watermarkpiccontainer"));
	}else{
		//showImage
		$("watermarkpic").src=mapfile(v);
		var imageLoader = new Image();
		imageLoader.onload = function(){
			var height = imageLoader.height,width = imageLoader.width;		
			var h=height,w=width;
			if(height > 64 || width > 100){	
				if(height > width){				
					h = 64;	
					w = 64 * width/height;
				}else{				
					w = 100;
					h = 100 * height/width;
				}			
			}
			
			$("watermarkpic").width = w;
			$("watermarkpic").height = h;
			imageLoader.onload = null;
		}
		imageLoader.src = mapfile(v);	
		Element.show($("watermarkpiccontainer"));
	}
}
function mapfile(fn){
	var r = ["/webpic/",fn.substring(0,8),"/",fn.substring(0,10),"/",fn];
	return r.join('');
}