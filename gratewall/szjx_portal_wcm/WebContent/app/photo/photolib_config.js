function saveConf(){
	if(!ValidationHelper.doValid('form_edit')){
		return false;
	}
	$("ScaleSizes").value = makeValue("ImageSize","imgsize");
	$("ScaleSizeDescs").value = makeValue("ImageSizeDesc","desc");
	$("WatermarkSizes").value = makeValue("WatermarkerSize","mark");

	BasicDataHelper.call("wcm6_photo","saveLibConf","form_edit",true,function(_transport,_json){
		alert(wcm.LANG.PHOTO_CONFIRM_56 || "设置成功!");
		FloatPanel.close();
	});

	return false;
}

function makeValue(_elName,_sAttribute){
	var els = document.getElementsByTagName("input");
	var r = [];
	for(var i=0,p=els.length;i<p;i++){
		if(els[i].getAttribute(_sAttribute) != "true") continue;
		r.push(els[i].value);
	}

	return r.join(",");
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

Event.observe(window,"load",function(){		
	ValidationHelper.initValidation();
});