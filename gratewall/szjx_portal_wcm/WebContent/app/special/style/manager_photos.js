//调整图片大小
function resizeIfNeed(_imgloaded){
	if(!_imgloaded){
		return;
	}
	var height = _imgloaded.height;
	var width = _imgloaded.width;
	if(height > 90 || width > 105){	
		if(height > width){
			_imgloaded.height = 80;				
			width = 80*width/height;
			height = 80;
			//对宽度进行再次处理
			if(width>97){
				width = 97;
			}
		}else{
			_imgloaded.width = 97;					
			height = 97 * height/width;
			width = 97;
			//对高度进行再次处理
			if(height>80){
				height = 80;
			}
		}
	}
	_imgloaded.width = width;
	_imgloaded.height = height;
	_imgloaded.style.left = Math.floor((104-width)/2)+"px";
	_imgloaded.style.top = Math.floor((90-height)/2)+"px";
}

// 翻页代码
function gotoPage(_nPageIndex){
	var event = window.event || event;
	if(event)
		top.window.Event.stop(event);
	var eFrom = document.getElementById('frmPage'); 
	eFrom.PageIndex.value = _nPageIndex;
	eFrom.submit();
}

//选中一个文件对象
var nLastSelectedId = "";
function changeSelected(_nIdNumber,_event){
	var event = window.event || _event;
	_nIdNumber = _nIdNumber || -1;
	if(_nIdNumber==-1){
		return;
	}
	
	//获取checkBox的选中状态
	var attrCheckbox = document.getElementById("checkbox_attr_" + _nIdNumber);
	var oSrcElement = event.srcElement ||  event.target;
	var srcElementTagName = oSrcElement.tagName;
	if(srcElementTagName=="INPUT"){
		//单击了checkbox
		if(oSrcElement.checked==true){
			document.getElementById("div_attr_"+_nIdNumber).className = "attrs_selected";
		}else{
			document.getElementById("div_attr_"+_nIdNumber).className = "attrs";
		}

	}else{
		//去除上次已经选中的样式
		removeOtherAtts(_nIdNumber);

		//单击了不是checkbox,则获取当前checkbox对象
		var oChekcBoxElement = document.getElementById("checkbox_attr_"+_nIdNumber);
		if(oChekcBoxElement.checked==true){
			oChekcBoxElement.checked = false;
			document.getElementById("div_attr_"+_nIdNumber).className = "attrs";
		}else{
			oChekcBoxElement.checked = true;
			document.getElementById("div_attr_"+_nIdNumber).className = "attrs_selected";
		}
		
		//去除上次选中样式,如果是两次单击同一个，则不需要删除
		if(nLastSelectedId !=_nIdNumber){
			removeLastAttrClass();
		}
	}

	nLastSelectedId = _nIdNumber;
}

//去除上次选中的属性样式
function removeLastAttrClass(){
	if(nLastSelectedId==""){
		return;
	}
	var oLastElement = document.getElementById("div_attr_" + nLastSelectedId);
	if(!oLastElement){
		return;
	}
	oLastElement.className = "attrs";

	//去掉checkbox框的选中
	var oChekcBoxElement = document.getElementById("checkbox_attr_" + nLastSelectedId);
	if(!oChekcBoxElement){
		return;
	}
	oChekcBoxElement.checked=false;
}

//单击的不是checkbox的时候，去掉所有已经选中的属性
function removeOtherAtts(_nCurrIdNumber){
	var allDivAttrs = document.getElementsByTagName("DIV");
	if(!allDivAttrs){
		return;
	}

	for(var i=0;i<allDivAttrs.length;i++){
		var aDivAttr = allDivAttrs[i];
		if(!aDivAttr || !aDivAttr.IdNumber || (aDivAttr.IdNumber==_nCurrIdNumber)){
			continue;
		}
		aDivAttr.className = "attrs";
		
		//去掉checkbox的选中
		var aCheckBox = document.getElementById("checkbox_attr_" + aDivAttr.IdNumber);
		if(!aCheckBox){
			continue;
		}
		aCheckBox.checked = false;
	}
}

//删除选中的图片
function deleteSelectedPics(_nPageStyleId){
	var sSelectedPicNames = getSelectedPicNames();
	deleteUploadImages(sSelectedPicNames,_nPageStyleId);
}
//删除选中的上传图片
function deleteUploadImages(_sSelectedPicNames,_nPageStyleId){
	if(!_sSelectedPicNames || _sSelectedPicNames==""){
		top.window.Ext.Msg.alert("请选择要删除的图片！");
		return false;
	}

	_nPageStyleId = _nPageStyleId || 0;
	if(_nPageStyleId==0){
		top.window.Ext.Msg.alert("没有传入页面风格对象id！");
	}

	//确认删除
	top.window.Ext.Msg.confirm("您确实要删除选中的图片吗？",{
		yes : function (){
			//直接删除
			doDeleteUploadImages(_sSelectedPicNames,_nPageStyleId);
		},
		no : function (){
			//ignore
		}
	});
}

//执行删除选中的上传图片
function doDeleteUploadImages(_sSelectedPicNames,_nPageStyleId){
	var postData = {
		ObjectId : _nPageStyleId,
		DeleteImgNames : _sSelectedPicNames
	}
	//ProcessBar.init();
	//ProcessBar.start("正在删除图片，请稍后......");
	top.window.BasicDataHelper.call("wcm61_pagestyle", "deleteUploadImages", postData, 'true', function(){
		//ProcessBar.close();
		document.location.reload();
	});
}

//获取当前选中图片名称
function getSelectedPicNames(){
	var allDivAttrs = document.getElementsByTagName("DIV");
	if(!allDivAttrs){
		return;
	}

	var sSelectedPicNames = "";
	for(var i=0;i<allDivAttrs.length;i++){
		var aDivAttr = allDivAttrs[i];
		if(!aDivAttr || !aDivAttr.getAttribute("IdNumber")){
			continue;
		}
		
		//去掉checkbox的选中
		var aCheckBox = document.getElementById("checkbox_attr_" + aDivAttr.getAttribute("IdNumber"));
		if(!aCheckBox || (aCheckBox.checked==false)){
			continue;
		}
		
		//拼接选中的图片名称
		if(sSelectedPicNames!=""){
			sSelectedPicNames += ",";
		}
		sSelectedPicNames += aDivAttr.getAttribute("fileName");
	}
	return sSelectedPicNames;
}

//打开图片
function openPic(_nCurrIdNumber){
	var currImg = document.getElementById("div_img_" + _nCurrIdNumber);
	if(!currImg){
		return;
	}
	top.window.open(currImg.src);
}

//缩放图片
function img_zoom(_nCurrIdNumber){
	var currImg = document.getElementById("div_img_" + _nCurrIdNumber);
	if(!currImg){
		return;
	}
	var zoom = parseInt(currImg.style.zoom, 10) || 100; 
　　　　　　zoom += event.wheelDelta / 12; 
　　　　　　if (zoom> 0){
		currImg.style.zoom = zoom + "%"; 
	}
　　　　　　return false; 
}
window.m_cbCfg = {
	btns : [
		{
			text : '关闭'
		}
	]
}
