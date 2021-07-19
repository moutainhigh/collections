function saveConf(){
	if(!ValidationHelper.doValid('form_edit')){
		return false;
	}

	$("ScaleSizes").value = makeValue("ImageSize");
	$("ScaleSizeDescs").value = makeValue("ImageSizeDesc");
	$("WatermarkSizes").value = makeValue("WatermarkerSize");

	BasicDataHelper.call("wcm6_photo","saveLibConf","form_edit",true,function(_transport,_json){
		alert("设置成功!");
		FloatPanel.close();
	});

	return false;
}

function makeValue(_elName){
	var els = document.getElementsByName(_elName);
	var r = [];
	for(var i=0,p=els.length;i<p;i++){
		r.push(els[i].value);
	}

	return r.join(",");
}

function isDefaultType(_type,_default){		
	if (_type == _default || (_type == -1 && _default.length == 0)){
		return "selected";
	}

	return "";
}

function isFirstInsall(_editable){		
	if(_editable == "true"){
		return "validation=\"type:'int',required:'true',desc:'图片尺寸',showid:'validation_tip'\"";
	}else{
		return "";
	}
}

function insertImageSize(){	
	var setters = document.getElementsByName("imagesize_setter");
	var setter = setters[setters.length-1];	
	setter.parentNode.appendChild(setter.cloneNode(true));
}

function removeImageSize(_el){
	var setter = _el.parentNode.parentNode;
	setter.parentNode.removeChild(setter);
}

function displayAble(_ix,_installed){
	//_installed = "true";//for test.
	if(_installed == "true" && _ix > 1){	
			return "inline";		
	}

	return "none";
}

Event.observe(window,"load",function(){		
	BasicDataHelper.call("wcm6_photo","loadLibConf",null,false,function(_transport,_json){		
		var sValue = TempEvaler.evaluateTemplater('template_config', _json);		
		Element.update($("area_holder"),sValue);
		ValidationHelper.initValidation();
	});
});