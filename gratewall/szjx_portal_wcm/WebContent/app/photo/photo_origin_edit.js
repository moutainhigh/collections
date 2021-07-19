var m_oActionMethod = "scaleImage";

var watermark_top = 0;
var watermark_left = 0;
/*
WebFXTabPage.prototype.select = function () {
	this.tabPane.setSelectedIndex( this.index );
	var actionType = this.element.actiontype;
	if(!actionType){
		actionType = this.element.getAttribute("actiontype",2);
	}
	//$("validate_tip").style.display = "none";	
	$("validate_tip").innerHTML = "";
	m_oActionMethod = actionType;	
};
WebFXTabPane.getCookie = function(){
	return 0;
}
*/

function ActionFile(_fn,_width,_height){
	this.fileName = _fn || "";
	this.width = _width || 432;
	this.height = _height || 640;
}

function HistoryActions(_actionFile){
	this.actionFiles = [];
	this.currIndex = 0;
	this.currActionFile = _actionFile || new ActionFile();
	this.actionFiles.push(this.currActionFile);
}
HistoryActions.prototype.undo = function(){
	var ix = this.currIndex - 1;
	if(ix == 0){
		$("btnundo").disabled = true;		
	}
	if(ix < this.actionFiles.length){
		$("btnredo").disabled = false;
	}
	this.currIndex = ix;
	this.currActionFile = this.actionFiles[this.currIndex];	
	this.showImg();
}
HistoryActions.prototype.redo = function(){
	var ix = this.currIndex + 1;
	if(ix == this.actionFiles.length-1){
		$("btnredo").disabled = true;		
	}
	$("btnundo").disabled = false;
	this.currIndex = ix;
	this.currActionFile = this.actionFiles[this.currIndex];
	this.showImg();	
}
HistoryActions.prototype.showImg = function(){
	var fn = this.currActionFile.fileName;
	var width = this.currActionFile.width;
	var height = this.currActionFile.height;
	
	window.sOriginalImg = fn;
	window.oOriginalImg = new Image();
	oOriginalImg.src = "../../file/read_image.jsp?FileName="+fn;
	$("imageElement").src = oOriginalImg.src;
	var container = Element.getDimensions(document.body);
	var containerWidth = container.width * 0.7-50;
	var containerHeight = container.height - 130;
	if(width > containerWidth || height > containerHeight){
		setSuiteSize();	
	}else{
		setOriginSize();
	}

	$("scalewidth_pixel").value = width;
	$("scaleheight_pixel").value = height;

	//百分比缩放时的校验
	reinitValid4PercentScale(width,height);
}

function reinitValid4PercentScale(width,height){
	var minRatioW = Math.ceil(75/width * 100);
	var maxRatioW = Math.floor(2000/width * 100)+"'";
	var sValidation = "type:'int',required:'true',showid:'validate_tip',value_range:'";	
	
	var percentWidth = $("scalewidth_percent");
	var temp = [minRatioW,maxRatioW,"desc:(wcm.LANG.PHOTO_CONFIRM_25 || '宽度缩放比例')"];
	percentWidth.setAttribute("validation",sValidation+temp.join(","));
	ValidationFactory.makeValidator(percentWidth);
	
	var minRatioH = Math.ceil(75/height * 100);
	var maxRatioH = Math.floor(2000/height * 100)+"'";
	var percentHeight = $("scaleheight_percent");
	var temp = [minRatioH,maxRatioH,"desc:(wcm.LANG.PHOTO_CONFIRM_26 || '高度缩放比例')"];
	percentHeight.setAttribute("validation",sValidation+temp.join(","));
	ValidationFactory.makeValidator(percentHeight);
}

HistoryActions.prototype.recordAction = function(_actionFile){
	this.currActionFile = _actionFile;	
	this.actionFiles.splice(++this.currIndex, 0, _actionFile);
	//this.actionFiles._insertAt(++this.currIndex,_actionFile);
	$("btnundo").disabled = false;
	this.showImg();
}

function setSuiteSize(_width,_height){
	if($("imageElement").readyState != 'complete'){   
          setTimeout(setSuiteSize,10);
    }else{
		var width = _width || m_oHistoryActor.currActionFile.width;
		var height = _height || m_oHistoryActor.currActionFile.height;
		var container = Element.getDimensions(document.body);
		var containerWidth = container.width * 0.7-50;
		var containerHeight = container.height - 130;

		var imgRatio = width/height;
		var conRatio = containerWidth/containerHeight;

		var imgEl = $("imageElement");	
		
		if(imgRatio > 1){
			if(imgRatio >= conRatio){
				imgEl.style.width = containerWidth;
				height = imgEl.style.height =  parseInt(containerWidth/imgRatio);
				imgEl.style.marginTop = (containerHeight - height)/2;
			}else{
				imgEl.style.height = containerHeight;
				width = imgEl.style.width = parseInt(containerHeight*imgRatio);
				imgEl.style.marginTop = 0;
			}
		}else{
			imgEl.style.height = containerHeight;
			imgEl.style.width =  parseInt(containerHeight * imgRatio);	
			imgEl.style.marginTop = "";
		}
		$("suiteSize").checked = true;
	}
}

function setOriginSize(){
	var width = m_oHistoryActor.currActionFile.width;
	var height = m_oHistoryActor.currActionFile.height;
	var containerHeight = Element.getDimensions(document.body).height-130;
	var imgEl = $("imageElement");	

	imgEl.style.height = height;
	imgEl.style.width = width;
	if(containerHeight > height){
		imgEl.style.marginTop = (containerHeight - height)/2;
	}

	$("originalSize").checked = true;
}

var m_oHistoryActor = null;//new HistoryActions();
var PageContext = {};
var watermarkchanged = false;
Object.extend(PageContext,{	
	init :function(){
		this.params = {PhotoId:getParameter("PhotoId"),WaterMarkFile:""}
	},
	loadOriginImg : function(_photoId){
		BasicDataHelper.call("wcm6_photo","loadOriginImg",{PhotoId:_photoId || this.params.PhotoId},false,function(_transport,_json){			
			PageContext.loadWatermarks(_photoId);	
			Object.extend(PageContext.params,{SrcFile:$v(_json,"doctitle")||""});
			var originImg = new ActionFile($v(_json,"fn"),$v(_json,"width"),$v(_json,"height"));			
			m_oHistoryActor = new HistoryActions(originImg);
			m_oHistoryActor.showImg();
		});
	},
	loadWatermarks : function(_photoId){
		var el = $("selwatermark");
		if(el.value != -1){
			var op = el.options[el.selectedIndex];
			showWaterMark(op);	
			var pos = op.getAttribute("_picpos");
			selectedPos(pos);
		}
		Event.observe($("selwatermark"),"change",function(){
			watermarkchanged = true;
			var el = $("selwatermark");
			var op = el.options[el.selectedIndex];
			if(op.value == -1){
				Element.hide($("watermarkpiccontainer"));
				Element.hide($("div_watermarkpos"));
				Object.extend(PageContext.params,{WaterMarkFile:""});
			}else{
				$("RB").checked = true;
				showWaterMark(op);
			}
		});
	},
	loadDefaultConverType : function(){
		BasicDataHelper.call("wcm6_photo","getDefaultBmpConverType",null,false,function(_transport,_json){			
			$("BmpConverTypeSelect").value = _transport.responseText;			
		});
	}
});
function selectedPos(pos){
	if(pos.indexOf("1") != -1){
		$("LT").checked = true;
	}
	if(pos.indexOf("2") != -1){
		$("CM").checked = true;
	}
	if(pos.indexOf("3") != -1){
		$("RB").checked = true;
	}
}
function showWaterMark(op){
	var imgLoaded = new Image();
	imgLoaded.onload = function(){
		resizeIfNeed(imgLoaded.height,imgLoaded.width);
		imgLoaded.onload = null;
	}
	imgLoaded.src = op.getAttribute("_picsrc") + "?r="+Math.random();
	$("watermarkpic").src = op.getAttribute("_picsrc");
	Element.show($("watermarkpiccontainer"));					
	Element.show($("div_watermarkpos"));					
	Object.extend(PageContext.params,{WaterMarkFile:op.getAttribute("_picfile")});
}
PageContext.init();

function undo(){
	m_oHistoryActor.undo();
}

function redo(){
	m_oHistoryActor.redo();
}

function applyAction(){	
	//validate & prepare data.	
	var data = {FileName:m_oHistoryActor.currActionFile.fileName};
	var zValidated = false;
	switch(m_oActionMethod){
		case "scaleImage": 
			zValidated = ValidationHelper.validAndDisplay("scalewidth_pixel","scaleheight_pixel");
			Object.extend(data,{ScaleWidth:$F("scalewidth_pixel"),ScaleHeight:$F("scaleheight_pixel")});
			break;
		case "rotateImage": 
			zValidated = ValidationHelper.validAndDisplay("rotate_degree");
			var rotateD = $F("rotate_degree");
			if($("rotate_anticlockwise").checked){
				rotateD = 360 - rotateD
			}
			Object.extend(data,{Degree:rotateD});
			break;
		case "addBorder": 
			zValidated = ValidationHelper.validAndDisplay("borderwidth","borderheight");
			if($F("borderwidth") == "0" && $F("borderheight") =="0") return;
			Object.extend(data,{BorderWidth:$F("borderwidth"),BorderHeight:$F("borderheight"),BorderColor:$F("bordercolor")});
			break;
		case "raiseImage": 
			zValidated = ValidationHelper.validAndDisplay("raisewidth","raiseheight");
			var raiseUp = $("raise_up").checked?"true":"false";
			Object.extend(data,{RaiseWidth:$F("raisewidth"),RaiseHeight:$F("raiseheight"),IsRaised:raiseUp});
			break;	
		default:
			break;
	}	

	if(!zValidated) return;
	ProcessBar.init(wcm.LANG.PHOTO_CONFIRM_27 || '正在处理，请稍候...');	
	ProcessBar.addState(wcm.LANG.PHOTO_CONFIRM_27 || '正在处理，请稍候...');
	ProcessBar.start();	
	BasicDataHelper.call("wcm6_photo",m_oActionMethod,data,true,function(_transport,_json){
		var actionResult = new ActionFile($v(_json,"fn"),$v(_json,"width"),$v(_json,"height"));
		m_oHistoryActor.recordAction(actionResult);
		//m_oHistoryActor.showImg();重复显示图片
		ProcessBar.close();
	});
}

function changeScaleType(){
	var elPixelWidth = $("scalewidth_pixel");
	var elPixelHeight = $("scaleheight_pixel");
	var elPercentWidth = $("scalewidth_percent");
	var elPercentHeight = $("scaleheight_percent");	
	if($("scaletype_pixel").checked){		
		elPixelWidth.disabled = false;
		elPixelHeight.disabled = false;
		elPercentWidth.disabled = true;
		elPercentHeight.disabled = true;
	}else{
		elPixelWidth.disabled = true;
		elPixelHeight.disabled = true;
		elPercentWidth.disabled = false;
		elPercentHeight.disabled = false;	
	}
}

function changeScaleValue(_type){
	var elPixelWidth = $("scalewidth_pixel");
	var elPixelHeight = $("scaleheight_pixel");
	var elPercentWidth = $("scalewidth_percent");
	var elPercentHeight = $("scaleheight_percent");	

	var zKeep = $("keepscaleratio").checked;
	var valueChanged = null;
	var nImgWidth = m_oHistoryActor.currActionFile.width;
	var nImgHeight = m_oHistoryActor.currActionFile.height;
	switch(_type) {
		case "width_pixel" :
			valueChanged = elPixelWidth.value;
			elPercentWidth.value = MyNumber.parseInt(valueChanged * 100 /  nImgWidth);
			if(zKeep) {
				elPercentHeight.value = elPercentWidth.value;
				elPixelHeight.value = MyNumber.parseInt(nImgHeight * elPercentHeight.value / 100);
			}
			break;
		case "height_pixel" :
			valueChanged = elPixelHeight.value;
			elPercentHeight.value = MyNumber.parseInt(valueChanged * 100 /  nImgHeight);
			if(zKeep) {
				elPercentWidth.value = elPercentHeight.value;
				elPixelWidth.value = MyNumber.parseInt(nImgWidth * elPercentWidth.value / 100);
			}
			break;
		case "width_percent" :
			valueChanged = elPercentWidth.value;
			elPixelWidth.value = MyNumber.parseInt(nImgWidth * valueChanged / 100);
			
			if(zKeep) {
				elPercentHeight.value = valueChanged;
				elPixelHeight.value = MyNumber.parseInt(nImgHeight * elPercentHeight.value / 100);
			}
			break;
		case "height_percent" :
			valueChanged = elPercentHeight.value;
			elPixelHeight.value = MyNumber.parseInt(nImgHeight * valueChanged/ 100);
			if(zKeep) {
				elPercentWidth.value = valueChanged;
				elPixelWidth.value = MyNumber.parseInt(nImgWidth * elPercentWidth.value / 100);
			}
			break;
		default :
	}
}

function setKeepScacleRatio(){
	if($("keepscaleratio").checked){
		var percentWidth = $F("scalewidth_percent");
		$("scaleheight_percent").value = percentWidth
		$("scaleheight_pixel").value = MyNumber.parseInt(m_oHistoryActor.currActionFile.height * percentWidth / 100);	
	}
	
}

function changeBorderValue(_type){
	if($("keepbordereq").checked){
		var elBorderWidth = $("borderwidth");
		var elBorderHeight = $("borderheight");
		if("width" == _type){
			elBorderHeight.value = elBorderWidth.value;
		}else{
			elBorderWidth.value = elBorderHeight.value;
		}
	}
}

function setKeepBorderWidthAndHeight(){
	if($("keepbordereq").checked){
		$("borderheight").value = $F("borderwidth")			
	}	
}

function changeRaiseValue(_type){
	if($("keepraiseeq").checked){
		var elRaiseWidth = $("raisewidth");
		var elRaiseHeight = $("raiseheight");
		if("width" == _type){
			elRaiseHeight.value = elRaiseWidth.value;
		}else{
			elRaiseWidth.value = elRaiseHeight.value;
		}
	}
}

function setKeepRaiseWidthAndHeight(){
	if($("keepraiseeq").checked){
		$("raiseheight").value = $F("raisewidth")
	}	
}

//全选或反全选水印位置
function selectAllPos(){
	var poses = $("LT","CM","RB");
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
MyNumber.isNatureNum = function(_string) {
	if(_string == null || _string == "")
		return false;
	var pattern = /^([1-9])\d*$/g; 
    var flag = pattern.test(_string); 
	return flag;
}

function isBmp(_fn){
	if(!_fn) return false;
	var ix = _fn.lastIndexOf(".");
	if(ix == -1) return false;

	return "bmp" == _fn.substr(ix).toLowerCase();
}
function onOk(){
	var fn = m_oHistoryActor.currActionFile.fileName;
	//var bmp2type = $("BmpConverTypeSelect").value;

	var zChanged = (m_oHistoryActor.currIndex != 0);//不是原图	
//	zChanged = zChanged || (PageContext.params.WaterMarkFile != "");//加水印	
//	zChanged = zChanged || (bmp2type != "bmp" && isBmp(fn));//bmp文件要转换	
//	if(!zChanged && !watermarkchanged){
//		window.close();
//		return false;
//	}
	var opostdata = {FileName:fn};
	var original_width = m_oHistoryActor.currActionFile.width;
	var original_height = m_oHistoryActor.currActionFile.height;
	//注释掉指定水印位置的操作入口，默认特定
	//if($("pos_1") && $("pos_1").checked){
		var wmpos = [];
		var wmposcheckbox = $("LT","CM","RB");
		for(var i=0;i<3;i++){
			if(wmposcheckbox[i].checked){
				wmpos.push(wmposcheckbox[i].value);
			}
		}
		Object.extend(opostdata,{WaterMarkPos:wmpos.join(",")});
	//}else{
	//	Object.extend(opostdata,{WaterMarkPos:"4," + $("top").value + "," + $("left").value + "," + original_width + "," + original_height});
	//}	
	var postData = Object.extend(PageContext.params,opostdata);
	ProcessBar.init(wcm.LANG.PHOTO_CONFIRM_27 || '正在处理，请稍候...');	
	ProcessBar.addState(wcm.LANG.PHOTO_CONFIRM_27 || '正在处理，请稍候...');
	ProcessBar.start();	
	BasicDataHelper.call("wcm6_photo","saveOriginImg",postData,true,function(_transport,_json){
		ProcessBar.close();	
		window.close();	
		if(window.opener && window.opener.refreshImg){
			window.opener.refreshImg();
		}
	});	
}

Event.observe(window,"load",function(){
	PageContext.loadOriginImg(getParameter("PhotoId"));	
	//PageContext.loadDefaultConverType();
	ValidationHelper.initValidation();	
	$("scalewidth_percent").disabled = true;
	$("scaleheight_percent").disabled = true;
});

function resizeIfNeed(height,width){
	var h = height,w = width;
	if(height > 80 || width > 77){	
		if(height > width){				
			h = 80;	
			w = 80 * width/height;
		}else{				
			w = 77;
			h = 77 * height/width;
		}			
	}

	$("watermarkpic").width = w;
	$("watermarkpic").height = h;
	
	$("watermarkpic").style.display = "inline";
}
Event.observe(window,'load', function(){
	new wcm.TabPanel({
		id : 'tabPanel',
		activeTab : 'tab-imgscale'
	}).show();
	wcmXCom.get('tabPanel').on('tabchange', function(sCurrTab){
		var actionType = $(sCurrTab).getAttribute("actiontype",2);
		$("validate_tip").innerHTML = "";
		m_oActionMethod = actionType;	
	});
});


var imgInfoOfCrop;
var bound;
function crop(){
	var oImg = window.oOriginalImg;
	var nWidth = 900;
	var nHeight = 600;
	var parameters = "photo="+encodeURIComponent(oImg.src);
	var sUrl = WCMConstants.WCM6_PATH + "photo/photo_crop.html?"+parameters;
	var dialogArguments = window;
	var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
	var bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
	if(bound == null){
		return;
	}
	reSetImg(bound);
}
//裁剪以后图片重新载入时，需要同时修改图片显示的高度和宽度
function reSetImg(params){
	window.oOriginalImg.src = "../../file/read_image.jsp?FileName="+params.FN;
	$('imageElement').src = window.oOriginalImg.src;
	m_oHistoryActor.currActionFile.fileName=params.FN;
	m_oHistoryActor.currActionFile.width=params.WIDTH;
	m_oHistoryActor.currActionFile.height=params.HEIGHT;
	$('imageElement').style.height = params.HEIGHT;
	$('imageElement').style.width = params.WIDTH;
	$('scalewidth_pixel').value=params.WIDTH;
	$('scaleheight_pixel').value=params.HEIGHT;
	setSuiteSize();
}
function doCropWithServer(fCallback){
	BasicDataHelper.call(
		"wcm61_photo", 
		"cropImage", 
		{
			Filename : window.sOriginalImg,
			x:bound.left,
			y:bound.top,
			width:bound.width,
			height:bound.height
		}, 
		true, function(_transport, _json){
			imgInfoOfCrop = _json;
			if(fCallback)fCallback();
		}
	);
}

function previewPhoto(){
	if(imgInfoOfCrop){
		var nWidth = parseInt(imgInfoOfCrop.WIDTH, 10);
		var nHeight = parseInt(imgInfoOfCrop.HEIGHT, 10);
		nWidth = Math.min(window.screen.width - 12, nWidth);
		nHeight = Math.min(window.screen.height - 60, nHeight);
		var parameters = "photo="+encodeURIComponent("../../file/read_image.jsp?FileName="+imgInfoOfCrop.FN);
		var sUrl = WCMConstants.WCM6_PATH + "photo/photo_crop_preview.html?"+parameters;
		var dialogArguments = window;
		var sFeatures = "center:yes;dialogWidth:" + (nWidth+20) + "px;dialogHeight:" + (nHeight+60)+"px;status:no;resizable:no;";
		bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
		return;
	}
	doCropWithServer(arguments.callee);
}

function saveCropImage(fCallBack){
	if(!imgInfoOfCrop || !imgInfoOfCrop.FN){
		if(fCallBack) fCallBack();
		return;
	}
	BasicDataHelper.call(
		"wcm6_photo",
		"save",
		{
			PHOTODOCID : PageContext.pDocIds[m_nCurrIndex],
			PHOTOFILE : imgInfoOfCrop.FN,
			WATERMARKFILE : ''
		},
		true,
		function(_transport,_json){
			hideCropInfo();	
			if(fCallBack) fCallBack();
		}
	);		
}
 
function applyCrop(){
	if(imgInfoOfCrop){
		var actionResult = new ActionFile(imgInfoOfCrop.FN,imgInfoOfCrop.WIDTH,imgInfoOfCrop.HEIGHT);
		m_oHistoryActor.recordAction(actionResult);
		m_oHistoryActor.showImg();
		hideCropInfo();
		return;
	}
	doCropWithServer(arguments.callee);
}

function cancelCrop(){
	hideCropInfo();
}

function showCropInfo(bound){
	imgInfoOfCrop = null;
	Element.show('scopeTip');
	Element.show('applyCropHandler');	
}
function hideCropInfo(){
	imgInfoOfCrop = null;
	Element.hide('scopeTip');
	Element.hide('applyCropHandler');	
}

//图片水印的可视化位置指定
function applyWaterMark(){
	var oImg = window.oOriginalImg;
	var nWidth = 900;
	var nHeight = 600;
	var parameters = "photo="+encodeURIComponent(oImg.src);
	parameters += "&watermark=" + encodeURIComponent($("watermarkpic").src);
	var sUrl = WCMConstants.WCM6_PATH + "photo/watermark_specify.html?"+parameters;
	var dialogArguments = window;
	var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
	var bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
	if(bound == null){
		return;
	}
	$("top").value = bound[0];
	$("left").value =  bound[1];
}

function change(){
	var eles = document.getElementsByName("pos");
	if(eles.length <= 0) return;
	for(var i=0; i < eles.length;i++){
		var ele = eles[i];
		if(!ele.checked) continue;
		if(ele.value == 1){
			Element.show("pos_1_container");
			Element.hide("pos_2_container");
		}else if(ele.value == 4){
			Element.show("pos_2_container");
			Element.hide("pos_1_container");
		}
		break;
	}
}