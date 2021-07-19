function Try() {
	for (var i = 0; i < arguments.length; i++) {
	  try {
		return (arguments[i])();
	  } catch (e) {}
	}
}
Event.observe(window, 'load', function(){
	autoJusty();
	//bindEvents();
});


function downloadAppendix(dom){
	var sFileName = dom.getAttribute("title");
	if(sFileName != ""){
		FileDownloader.download("/wcm/file/read_file.jsp?FileName=" + sFileName);
	}
}

function SetValueByEditor(_sElement, _sContent){
	var oElement = $(_sElement);
	if(oElement.tagName == "IFRAME"){
		if(oElement.readyState){
			if(oElement.readyState != 'complete'){
				oElement.onreadystatechange = function(){
					try{
						_SetContent_(oElement, _sContent);
						if(oElement.readyState == 'complete'){
							oElement.onreadystatechange = null;
						}
					}catch(err){
						//alert(err.message);
					}
				};
			}else{
				try{
					_SetContent_(oElement, _sContent);
				}catch(error){
					//alert(error.message);
				}
			}
		}else{
			try{	
				var win = oElement.contentWindow;
				//页面可能没加载完则，_setContent_方法不存在
				if(win && win._SetContent_){
  					_SetContent_(oElement, _sContent);
				}else{
					//iframe加载完后开始赋值。
					oElement.onload = function(){
						_SetContent_(oElement, _sContent);
					}
				}
			}catch(error){

			}
		}
	}
}

function addContentLoaded(ifrm, fCallback){
	if(ifrm.onreadystatechange){
		ifrm.onreadystatechange = fCallback();
	}else{
		win = ifrm.contentWindow;
		Event.observe(win, 'DomContentLoaded', fCallback);
	}
}

function _SetContent_(oElement, _sContent){
	if(oElement.contentWindow.setHTML){
		oElement.contentWindow.setHTML(_sContent);
	}else{
		oElement.contentWindow.document.body.innerHTML = _sContent;
		if(oElement.getAttribute("contenteditable") == "true"){
			oElement.contentWindow.document.body.contentEditable = true;
		}
		oElement.style.border = '1px solid silver';
	}
}

function autoJusty(){
	var sId = "view_metaviewdata";
	var cbSelf = wcmXCom.get(sId);
	if(!cbSelf) return;
	try{
	var box = Ext.isStrict?document.documentElement:document.body;
	var minWidth = 700, minHeight = 250, maxWidth = 900, maxHeight = 400;
	var realWidth = box.scrollWidth;		
	var realHeight = box.scrollHeight;
	realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
	realHeight = realHeight > maxHeight ? maxHeight : (realHeight < minHeight ? minHeight : realHeight);
	//alert(realWidth +":"+realHeight);
	}catch(e){
		alert(e.message)
	}
	cbSelf.setSize(realWidth+"px",realHeight+"px");	
	box.style.overflow = 'auto';
	document.body.style.overflow = 'auto';
	cbSelf.center();
}
function submitData(){
	//流转信息的校验
	var processIframe = $('XProcessIframe');
	if(m_nFlowDocId == 0 || !processIframe){
		return;
	}
	var winFlowdoc = processIframe.contentWindow;
	//如果选择的是诸如“签收”、“返工”或者“拒绝”之类的操作，则不校验，但要保存
	if(!winFlowdoc.isSpecialOption()) {
		if(!winFlowdoc.validate(false)) {
			return false;
		}
	}
	var sPostData = winFlowdoc.buildPostXMLData();
	if(sPostData && sPostData.length > 0) {
		new ajaxRequest({
			url : '/wcm/center.do',
			postBody: sPostData,
			contentType : 'multipart/form-data',
			method : 'post',
			onSuccess : function(_trans, _json){
				window.opener.CMSObj.createFrom({
					objType : 'IFlowContent',
					objId : m_nObjectId
				}).afteredit();
				window.close();
			},
			onFailure : function(_trans, _json){
				//alert(_trans.responseText);
			}
		});
	}
	return;
}

function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxHeight = 200;
	var maxWidth = 300;
	var height = _loadImg.height, width = _loadImg.width;
	if(height > maxHeight || width > maxWidth){	
		if(height > width){
			width = maxHeight * width/height;
			height = maxHeight
		}else{
			height = maxWidth * height/width;
			width = maxWidth;
		}
		_loadImg.width = width;
		_loadImg.height = height;
	}
}