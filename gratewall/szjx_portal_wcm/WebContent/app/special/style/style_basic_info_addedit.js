Event.observe(window, 'load', previewBasicInfo);
function previewBasicInfo(){
	var eFrom = $("frmStyleData");
	if(!eFrom){
		return;
	}
	//预览的时候也添加了校验
	if(!ValidationHelper.doValid("frmStyleData")){
		return false;
	}
	var postData = {
		ObjectId : $('ObjectId').value || 0,
		StyleItemXML : top.window.getStyleXML(eFrom)
	}
	top.window.BasicDataHelper.call("wcm61_pagestyle", "previewStyle", postData, 'true', function(_thansport ,_json){
		var sCssContent = _thansport.responseText;
		var sUrl = "./stylepreview/style_basic_preview_page.jsp?CssContent=";
		top.window.setIFrameByPost("iframe_style_basic_preview", sUrl, null, {CssContent:sCssContent})
	});

}
//保存
function saveBasicInfo(_nPageStyleId){
	var eFrom = $("frmStyleData");
	if(!eFrom){
		return;
	}
	if(!ValidationHelper.doValid("frmStyleData")){
		return false;
	}
	//发送ajax请求
	var sPostStr = {
		ObjectId : $('ObjectId').value || 0,
		StyleItemXML : top.window.getStyleXML(eFrom)
	}
	top.window.BasicDataHelper.call("wcm61_pagestyle", "saveBaseStyle", sPostStr, 'true', function(){
		document.location.reload();

	});
}

//清空
function clearBasicInfo(){
	var oForm = $("frmStyleData");
	if(!oForm){
		return;
	}
	oForm.reset();
}

// 图片上传组件
function dealWithUploadedImageFile(_sSaveFilePath, _sInputId){
	if(_sSaveFilePath.length<0){
		top.window.Ext.Msg.alert('上传文件失败');
	}
	if(!_sSaveFilePath&&_sSaveFilePath==''){
		return;
	}
	var eInput = document.getElementById(_sInputId);

	eInput.value = _sSaveFilePath;
}