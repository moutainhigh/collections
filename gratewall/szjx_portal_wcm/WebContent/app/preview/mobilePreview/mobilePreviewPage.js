m_phoneScreanHeight = 720;//手机预览区域高度；
m_phoneScreanwidth  = 1003;//手机预览区域宽度；
m_phoneMarginTop = 5;
m_defaultClass = "ipad_S";
m_defaultAndrodiSize = "40";
m_iframSrc = getParameter("URL") || "http://www.qianduan.net";
m_signStr = getParameter("sign")
if(m_signStr != "")
{
	m_iframSrc += m_iframSrc.indexOf("?")>-1? "&sign="+m_signStr: "?sign="+m_signStr;
}

//定义移动设备对象的尺寸，数据分别为：上边框，下边框,左边框,右边框，总宽度，总高度；
var iPhone	=	 MobileEquipment( "iphone",78,76,17,14,248,480);
var iPad	=	 MobileEquipment(   "ipad",55,58,45,50,518,680);
//var iPad	=	 MobileEquipment(   "ipad",87,93,75,77,790,1042);
var android_37 = MobileEquipment("android",49,74,18,19,249,477,"37");
var android_40 = MobileEquipment("android",75,81,25,25,279,538,"40");
var android_47 = MobileEquipment("android",58,74,26,25,309,590,"47");
var android_55 = MobileEquipment("android",60,58,24,24,352,652,"55");
var setSizeForAndroid = function(event){
	if($("37").checked) {
		$("androidSize").value = "37";
		setClass('phoneBox','android_S');
	}
	if($("40").checked) {
		$("androidSize").value = "40";
		setClass('phoneBox','android_S');
	}
	if($("47").checked) {
		$("androidSize").value = "47";
		setClass('phoneBox','android_S');
	}
	if($("55").checked) {
		$("androidSize").value = "55";
		setClass('phoneBox','android_S');
	}
}
function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
}
function getParameter(_sName, _sQuery){
	if(!_sName)return '';
	var query = _sQuery || location.search;
	if(!query)return '';
	var arr = query.substring(1).split('&');
	_sName = _sName.toUpperCase();
	for (var i=0,n=arr.length; i<n; i++){
		if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
			return arr[i].substring(_sName.length + 1);
		}
	}
	return '';
}
function changeClass(_id,type){
	var className = $(_id).className;
	var mobile = parserMobile(className);
	if(!type) {
		type = parserHSType(className);
		if(type.substring(0,1) == "H") type = "S";
		else type = "H";
	}
	className = mobile + "_" + type;
	setClass(_id,className)
}
function setClass(_id,className){
	if(!className) {
		className = m_defaultClass;
	}
	//改变phoneBody的样式
	parser(_id,className);
	//确定当前选中状态 || 设置iframeStyle的样式
	var mobile = parserMobile(className);
	var type = parserHSType(className);
	if(mobile == "iphone") {
		$("iphoneBtn").style.backgroundImage = "url(images/iphoneHover.png)";
		$("ipadBtn").style.backgroundImage = "url(images/ipad.png)";
		$("androidBtn").style.backgroundImage = "url(images/android.png)";
		$("forAndroidSize").style.display = "none";
		setIframeStyle(iPhone,type);
	}
	if(mobile == "ipad") {
		$("iphoneBtn").style.backgroundImage = "url(images/iphone.png)";
		$("ipadBtn").style.backgroundImage = "url(images/ipadHover.png)";
		$("androidBtn").style.backgroundImage = "url(images/android.png)";
		$("forAndroidSize").style.display = "none";
		setIframeStyle(iPad,type);
	}
	if(mobile == "android") {
		//EventUtil.addHandler($("forAndroidSize"),"click",setSizeForAndroid);
		$("iphoneBtn").style.backgroundImage = "url(images/iphone.png)";
		$("ipadBtn").style.backgroundImage = "url(images/ipad.png)";
		$("androidBtn").style.backgroundImage = "url(images/androidHover.png)";
		$("forAndroidSize").style.display = "block";
		var size = m_defaultAndrodiSize;
		if($("androidSize")) {
			size = $("androidSize").value;
		}
		switch(size){
			case "37" : setIframeStyle(android_37,type);
				break;
			case "47" : setIframeStyle(android_47,type);
				break;
			case "55" : setIframeStyle(android_55,type);
				break;
			case "40" : setIframeStyle(android_40,type);
				break;
			default: setIframeStyle(android_40,type);
				break;
		}
	}
}

function parser(_id,className){
	var systemName = parserMobile(className);
	//var HStype = parserHSType(className);
	if(systemName == "android") {
		//判断是android，获取要展现的尺寸
		var androidSize = m_defaultAndrodiSize;
		if($("androidSize")) {
			androidSize = $("androidSize").value;
		}
		$(_id).className = className+androidSize + " phone_common_HS";
	}else {
		$(_id).className = className + " phone_common_HS";
	}
}
function parserMobile(className){
	var num = className.indexOf("_");
	return className.substring(0,num);
}
function parserHSType(className){
	var num = className.indexOf("_");
	return className.substring(num+1,className.length);
}

function setIframeStyle(obj,HStype){
	if( obj && obj.size ) {
		var size = obj.size;
	}
	var iframeBox = "iframeStyle";
	$(iframeBox).style.position = "relative";
	var obj = calculatePosition(obj,HStype);
	if(HStype == "H") {
		$(iframeBox).style.width = obj.realWidth + "px";
		$(iframeBox).style.height = obj.realHeight + "px";
	}else {
		$(iframeBox).style.width = obj.realWidth + "px";
		$(iframeBox).style.height = obj.realHeight + "px";
	}
	$(iframeBox).style.backgroundColor = "white";

	$(iframeBox).style.top = obj.top + "px";
	$(iframeBox).style.left = obj.left + "px";
	createIframe(obj,HStype,size);
}
function createIframe(obj,HStype,size){
	if(!obj.name)return;
	var mobile = obj.name;
	var iframeParentElement = $("iframeStyle");
	if(iframeParentElement.lastChild.id == "_mobile_iframe_") {
		iframeParentElement.removeChild(iframeParentElement.lastChild);
	}
	var iframeElement = document.createElement("iframe");
	iframeParentElement.appendChild(iframeElement);
	var iframeHeight,iframeWidth;
	if(HStype == "S") {
		iframeHeight = obj.realHeight;
		iframeWidth = obj.realWidth;
	}else {
		iframeHeight = obj.realWidth;
		iframeWidth = obj.realHeight;
	}
	iframeElement.id = "_mobile_iframe_";
	iframeElement.src = m_iframSrc;//"http://www.baidu.com";
	iframeElement.style.width = "100%";
	iframeElement.style.height = "100%";
	iframeElement.style.border = 0;
	
	iframeParentElement.style.marginBottom = obj.top + "px";
	iframeParentElement.parentElement.style.paddingBottom = (obj.top + 20) + "px";
}
//定义三种模式的位置及尺寸信息
function MobileEquipment(name,borderTop,borderBottom,borderLeft,borderRight,width,height,size){
	var mobile = new Object();
	mobile.name = name;
	mobile.borderTop = borderTop;
	mobile.borderBottom = borderBottom;
	mobile.borderLeft = borderLeft;
	mobile.borderRight = borderRight;
	mobile.width = width;
	mobile.height = height;
	if(size) {
		mobile.size = size;
	}
	//mobile.top = calculatePosition(mobile).top;
	//mobile.left = calculatePosition(mobile).left;
	return mobile;
}

function calculatePosition(result,HStype){
	if(HStype == "H") {
		result.top  = (m_phoneScreanHeight-result.width)/2+result.borderLeft;
		if(m_phoneMarginTop) result.top = m_phoneMarginTop+result.borderLeft;
		result.left = (m_phoneScreanwidth-result.height)/2+result.borderTop;
		result.realHeight = result.width-result.borderLeft-result.borderRight;
		result.realWidth = result.height-result.borderTop-result.borderBottom;
	}else {
		result.top  = (m_phoneScreanHeight-result.height)/2+result.borderTop;
		if(m_phoneMarginTop) result.top = m_phoneMarginTop+result.borderTop;
		result.left = (m_phoneScreanwidth-result.width)/2+result.borderLeft;
		result.realHeight = result.height-result.borderTop-result.borderBottom;
		result.realWidth = result.width-result.borderLeft-result.borderRight;
	}
	return result;
}
var EventUtil = {
	addHandler : function(element,type,handler){
		if(!element){
			return ;
		}
		if(element.addEventListener) {
			element.addEventListener(type,handler,false);
		}else if(element.attachEvent) {
			element.attachEvent("on" + type,handler);
		}else {
			element["on" + type] = handler;
		}
	},
	removeHandler : function(element,type,handler){
		if(!element){
			return ;
		}
		if(element.removeEventListener) {
			element.removeEventListener(type,handler,false);
		}else if(element.detachEvent) {
			element.detachEvent("on" + type,handler);
		}else {
			element["on" + type] = null;
		}
	}
}
function hover(id){
	if($(id).style.backgroundImage.indexOf("Hover")<0) {
		$(id).style.backgroundImage = "url(images/" + id.substring(0,id.length-3) + "_hover.png)";
	}
}
function hoverOut(id){
	var _id = id.substring(0,id.length-3);
	var tempName = parserMobile($("phoneBox").className).toLowerCase();
	if(_id != tempName) {
		$(id).style.backgroundImage = "url(images/" + _id + ".png)";
	}
}
