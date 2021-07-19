function isHTMLElement(_oTextRange, _oParentElement){
	if(_oParentElement == null || _oParentElement.tagName == "BODY")
		return false;
	try{
		var sRangeHTML			= _oTextRange.htmlText;
		//TextRange会产生多余的\r\n，需要排除	
		var reg = /\r\n/g;
		var bContainsInvalidChar = (sRangeHTML.search(reg)>0);
		if(bContainsInvalidChar){
			sRangeHTML = sRangeHTML.replace(reg, '');		
		}

		var sParentHTML	= _oParentElement.outerHTML;
		if(bContainsInvalidChar && sParentHTML.search(reg)>=0){
			sParentHTML = sParentHTML.replace(reg, '');
		}
		if(sParentHTML == sRangeHTML){
			return true;
		}

		sParentHTML = _oParentElement.innerHTML;
		if(bContainsInvalidChar && sParentHTML.search(reg)>=0){
			sParentHTML = sParentHTML.replace(reg, '');
		}
		if(sParentHTML == sRangeHTML){
			return true;
		}
	}catch(err){
		return false;
	}
	return false;	
}
/*
function isHTMLElement(_oFont, _oParentElement){
	if(_oParentElement == null || _oParentElement.tagName == "BODY")
		return false;
	if(_oFont.childNodes.length!=_oParentElement.childNodes.length)
		return false;
	var nCnLength = _oFont.childNodes.length;
	if(nCnLength==0)
		return true;
	if(_oFont.childNodes[0].nodeType!=_oParentElement.childNodes[0].nodeType)
		return false;
	if(_oFont.childNodes[0].nodeType==3&&_oParentElement.childNodes[0].nodeType==3){
		if(_oFont.childNodes[0].nodeValue!=_oParentElement.childNodes[0].nodeValue)
			return false;
	}
	if(_oFont.childNodes[nCnLength-1].nodeType!=_oParentElement.childNodes[nCnLength-1].nodeType)
		return false;
	if(_oFont.childNodes[nCnLength-1].nodeType==3&&_oParentElement.childNodes[nCnLength-1].nodeType==3){
		if(_oFont.childNodes[nCnLength-1].nodeValue!=_oParentElement.childNodes[nCnLength-1].nodeValue)
			return false;
	}
	return true;
}
*/
FCK.TRSExtend = {
	_doFontSize : function(oFont,_sFontSize,_bSet){
		if(_bSet){
			oFont.style.fontSize = _sFontSize;
		}
		var nCount = oFont.childNodes.length, objTemp ,sTagTemp;
		for(var i=0; i<nCount; i++){
			objTemp = oFont.childNodes[i];
			sTagTemp = objTemp.tagName;
			if(sTagTemp == "TD" || sTagTemp == "FONT"){
				FCK.TRSExtend._doFontSize(objTemp,_sFontSize,true);
			}
			else if(objTemp.nodeType==1){
				if(objTemp.style.fontSize){
					objTemp.style.fontSize = _sFontSize;
				}
				FCK.TRSExtend._doFontSize(objTemp,_sFontSize,objTemp.style.fontSize!=null);
			}
		}
	},
	_getFontSize : function(oFont){
		var nCount = oFont.childNodes.length, objTemp;
		var sFontSize = Element.getStyle(oFont,'fontSize');
		for(var i=0; i<nCount; i++){
			objTemp = oFont.childNodes[i];
			if(objTemp.nodeType==1){
				var sTmpSize = FCK.TRSExtend._getFontSize(objTemp,sFontSize);
				if(sTmpSize!=''&&sTmpSize!=sFontSize){
					return null;
				}
			}
		}
		return sFontSize;
	},
	getFontSize : function(){
		// Gets the actual selection.
		var oSel = FCK.EditorDocument.selection ;
		var oRange = oSel.createRange() ;
		var sRangeHTML = oRange.htmlText;
		var sCount = oRange.text.length;
		if ( oSel.type.toLowerCase()== 'text' ){
			var eParent = oRange.parentElement() ;
			if(eParent != null){
				var sFontSize = Element.getStyle(eParent,'fontSize');
			}
			var oFont = document.createElement("SPAN") ;
			oFont.innerHTML = sRangeHTML ;
			oFont.style.fontSize = sFontSize;
			var sPasteHTML = "";
			sFontSize = FCK.TRSExtend._getFontSize(oFont);
			delete oFont;
			return sFontSize;
		}
		else if ( oSel.type.toLowerCase()== 'control' ){
			var oElement = FCKSelection.GetSelectedElement();
			return FCK.TRSExtend._getFontSize(oElement);
		}
		else{
			var eParent = oRange.parentElement() ;
			if(eParent != null && eParent.innerHTML.length==0 && eParent.tagName != "BODY"){
				return Element.getStyle(eParent,'fontSize');
			}
			return '';
		}
	},
	doFontSize : function(_sFontSize){
		var oSel = FCK.EditorDocument.selection ;
		var oRange = oSel.createRange() ;
		if (true|| oSel.type.toLowerCase() != 'none' || oRange.htmlText!=''){
			if(oRange.text==''){
				oRange.pasteHTML('&nbsp;') ;
				oRange.collapse(true) ;	
				oRange.moveStart('character',-1);
				oRange.select();
			}
			FCK.EditorDocument.execCommand( 'FontSize', false, 1 ) ; 
			var eFontNews = FCK.EditorDocument.getElementsByTagName('font');
			for(var i=0;i<eFontNews.length;i++){
				var eFont = eFontNews[i];
				if(eFont.getAttribute('size')){
					eFont.removeAttribute('size');
					FCK.TRSExtend._doFontSize(eFont,_sFontSize,true);
	//				eFont.style.fontSize = _sFontSize;
				}
			}
		}
		else{
			var eParent = oRange.parentElement() ;
			if(eParent != null && eParent.innerHTML.length==0 && eParent.tagName != "BODY"){
				eParent.style.fontSize = _sFontSize;
			}
			else{
				//TODO
				oRange.pasteHTML('<span style="font-size:'+_sFontSize+'">&nbsp;</span>') ;
				oRange.collapse(true) ;	
				oRange.moveStart('character',-1);
				oRange.select();
//				oRange.collapse(true);
//				oRange.select();
			}
		}
		return;
		// Gets the actual selection.
		var oSel = FCK.EditorDocument.selection ;
		var oRange = oSel.createRange() ;
		var sRangeHTML = oRange.htmlText;
		if ( oSel.type.toLowerCase()== 'text' ){
//			var sCount = (oRange.text)?oRange.text.length:0;
			var eParent = oRange.parentElement() ;
			var oFont = document.createElement("Font") ;
			oFont.innerHTML = sRangeHTML ;
			var bIsHtml = isHTMLElement(oFont, eParent);
//			var bIsHtml = isHTMLElement(oRange, eParent);
			if(bIsHtml){
				FCK.TRSExtend._doFontSize(eParent,_sFontSize,true);
				return;
			}
//			var sPasteHTML = "";
			FCK.TRSExtend._doFontSize(oFont,_sFontSize,true);
//			sPasteHTML = oFont.outerHTML;
			try{
				oFont = FCK.InsertElementAndGetIt(oFont);
				if(oFont&&oFont[0])FCKSelection.SelectNode(oFont[0]);
//				FCK.InsertHtmlAndSelect(sPasteHTML,sCount);
			}catch(err){
				try{
					var aCells = FCKTableHandler.GetSelectedCells() ;
					for(var i=0;i<aCells.length;i++){
						FCK.TRSExtend._doFontSize(aCells[i],_sFontSize);
					}
				}catch(err2){
				}
			}
//			delete oFont;
		}
		else if ( oSel.type.toLowerCase()== 'control' ){
			var oElement = FCKSelection.GetSelectedElement();
			FCK.TRSExtend._doFontSize(oElement,_sFontSize);
		}
		else{
			var eParent = oRange.parentElement() ;
			if(eParent != null && eParent.innerHTML.length==0 && eParent.tagName != "BODY"){
				eParent.style.fontSize = _sFontSize;
			}
			else{
				//TODO
				oRange.pasteHTML('<span style="font-size:'+_sFontSize+'">&nbsp;</span>') ;
//				oRange.collapse(true) ;	
				oRange.moveStart('character',-1);
				oRange.select();
				oRange.collapse(true);
				oRange.select();
			}
		}
	}
};
var myActualTop = (top.actualTop||top);
var eTitle = myActualTop.$('DocTitle');
var nTitleLength = myActualTop.window.autoTitleLength||30;
var bRepeatDetect = false;
var bPasteTitle = false, bPasteBody = false;
function AutoPaste(_bNotInterval){
	if(FCKConfig.AutoDetectPaste){
		if(!_bNotInterval){
			FCK.autopasteInterval = setInterval(function(){AutoPaste(true)},500);
		}
		try{
			var sClipText = clipboardData.getData("Text")||'';
			if(sClipText==''){
				return;
			}
			if(sClipText == FCKConfig.lastClipText){
				return;
			}
			if(sClipText.length<=nTitleLength && !/<.*?>|\n/.test(sClipText)){
				if(eTitle){
					if((!bRepeatDetect&&bPasteTitle)||eTitle.value!=''){
						return;
					}
					FCKConfig.lastClipText = sClipText;
					eTitle.focus();
					eTitle.value = sClipText;
					bPasteTitle = true;
					eTitle.select();
					return;
				}
			}
			var sCurrHtml = FCK.EditorDocument.body.innerHTML;
			if((!bRepeatDetect&&bPasteBody)||sCurrHtml.length>20||!FCK.isBlankContent(sCurrHtml)){
				return;
			}
			top.window.focus();
			FCKConfig.lastClipText = sClipText;
			bPasteBody = true;
			FCK.EditorDocument.body.focus();
			FCK.Paste();
		}catch(err){}
		if(!bRepeatDetect&&bPasteTitle&&bPasteBody){
			clearInterval(FCK.autopasteInterval);
		}
	}
}
function ClearAutoPasteData(){
	if(FCKConfig.AutoDetectPaste){
		window.clipboardData.clearData('Text');
	}
}
FCK.PasteFromWord = function()
{
	try{
		var html = FCK.GetClipboardHTML2();
		var reColGroup = /<COLGROUP>/gi;
		var reExcel = /<\w[^>]* class="?xl"?/gi ;
		if(reColGroup.test(html) || reExcel.test( html )){//为Excel
			html = cleanExcelCode(html);
		}
		else{
			//判断是否为Word
			var re = /<\w[^>]* class="?MsoNormal"?/gi ;
			var re2 = /<v:imagedata /gi;
			if ( re.test( html ) || re2.test( html ) ){
				if(window.OfficeActiveX)window.OfficeActiveX.FormatWordClip();
				html = FCK.GetClipboardHTML2();
			}
		}
		var sHtml = CleanWord(html) ;
	}catch(err){
	}
	FCK.InsertHtml( sHtml ) ;
}

FCK.GetClipboardHTML2 = function()
{
	var oDiv = document.getElementById( '___FCKHiddenDiv' ) ;

	if ( !oDiv )
	{
		oDiv = document.createElement( 'DIV' ) ;
		oDiv.id = '___FCKHiddenDiv' ;

		var oDivStyle = oDiv.style ;
		oDivStyle.position		= 'absolute' ;
		oDivStyle.overflow		= 'hidden' ;
		oDivStyle.width			= oDivStyle.height		= 1 ;
		oDivStyle.top			= oDivStyle.left		= -100 ;
		document.body.appendChild( oDiv ) ;
	}
	try{
		oDiv.style.display = '';
		oDiv.style.visibility = 'visible';
		oDiv.innerHTML = '' ;
		oDiv.contentEditable = true;
		oDiv.focus();
		document.execCommand( 'Paste' ) ;
		document.body.focus();
	}catch(ex){
//		alert(ex.message);
	}
	var sData = oDiv.innerHTML ;
	if(sData == ''){
		oDiv.innerHTML = '' ;
		var oTextRange = document.body.createTextRange() ;
		oTextRange.moveToElementText( oDiv ) ;
		oTextRange.execCommand( 'Paste' ) ;
		sData = oDiv.innerHTML ;
		oDiv.innerHTML = '' ;
	}
	return sData ;
}

var m_sServerName = null;
var m_sAppName = null;
var m_sProtocal = null;
function initServerName(){
	m_sProtocal = window.location.protocol;
	m_sServerName = window.location.host;
	m_sAppName = window.location.pathname.split('/')[1];
	/*
	var sLocation = window.location.href;
	var ssLocation = sLocation.split("/");
	m_sServerName = "";
	if(ssLocation.length >= 2){
		m_sProtocal = ssLocation[0];
		m_sServerName = ssLocation[2];		
		m_sAppName = ssLocation[3];
	}
	*/
}
//Office ActiveXObject 控件对象,用于在客户端处理Office文档
var OfficeActiveX = {
	m_OfficeActiveX : null,
	Init : function(){//初始化ActiveX控件
		document.write("<OBJECT id=\"WORD_CLIENT\" CLASSID=\"clsid:D6641A7A-B6F8-4FC7-A382-624DDBAEF96F\"  codeBase=\"WCMOffice.cab#Version=1,0,0,20\" style=\"display:none\"></OBJECT>");
		this.m_OfficeActiveX = $("WORD_CLIENT");
		if(this.m_OfficeActiveX == null){
			alert("没有正确安装ActiveX控件OfficeCab!");
			FCK.setCookie('EditorEnableWordClient','-1');
			return;
		}
		if(m_sServerName == null)
			initServerName();
		if(m_sServerName == ""){
			return;
		}
		var sSOAP_URL = null;
		if(FCKConfig.SingleSOAPApp){
			sSOAP_URL = m_sProtocal + "//"+ m_sServerName +"/soap/servlet/rpcrouter";
		}else{
			sSOAP_URL = m_sProtocal + "//"+ m_sServerName +"/"+m_sAppName+"/services";
		}
		var sWCM_URL = m_sProtocal + "//"+ m_sServerName;
		try{
			this.m_OfficeActiveX.SetSOAPURL(sSOAP_URL, "urn:FileService", "sendFileBase64", sWCM_URL);			
		}catch(e){
			alert("设置SOAP服务地址["+sSOAP_URL+"]失败!\n可能是由于ActiveX控件OfficeCab没有安装成功." );
			FCK.setCookie('EditorEnableWordClient','-1');
			return;
		}
	},
	IsEnableWordClient : function(){
		if(this._bIsEnableWordClient!=null)return this._bIsEnableWordClient;
//		FCK.clearCookie('EditorEnableWordClient');
		var oCookie = FCK.loadCookie();
		var sEditorEnableWordClient = oCookie['EditorEnableWordClient'];
		var bRetVal = false;
		if(sEditorEnableWordClient == '0'){
			FCK.setCookie('EditorEnableWordClient','0');
			bRetVal = false;
		}
		else if(sEditorEnableWordClient == '1'){
			FCK.setCookie('EditorEnableWordClient','1');
			bRetVal = true;
		}
		else{
			var sConfirm = '您是否需要开启Office客户端抽取控件？\n'
				+'开启ActiveX控件需要设置当前站点为信任站点.';
			if(sEditorEnableWordClient == '-1'){
				sConfirm = '您的Office客户端抽取控件没有安装成功.\n'
					+'开启ActiveX控件需要设置当前站点为信任站点.\n\n'
					+'您是否仍开启Office客户端抽取控件？';
			}
			if(confirm(sConfirm)){
				FCK.setCookie('EditorEnableWordClient','1');
				bRetVal = true;
			}
			else{
				FCK.setCookie('EditorEnableWordClient','0');
				bRetVal = false;
			}
		}
		this._bIsEnableWordClient = bRetVal;
		return bRetVal;
	},
	isUsed : function(){
		return (this.IsEnableWordClient() && this.m_OfficeActiveX != null);
	},
	FormatWordClip : function(){//格式化word代码，转换图片为本地文件名
		if(!this.isUsed()) return;
		try{
			//格式化word源码，去除多余的格式信息
			this.m_OfficeActiveX.SetOfficeFilter(true);
			this.m_OfficeActiveX.PasteContent(false);		
		}catch(e){
			//Just Skip it.
		}
	},
	UploadLocals : function(){//根据图片的本地文件名获得uploadpic的文件名，并添加这个属性到各IMG对象中
		if(!this.isUsed()) return;
		//上传Img中的图片
		this._UploadLocalFile("IMG", "src");
		//上传A中的本地文件
		this._UploadLocalFile("A", "href");
		//上传Flash、音频、视频
		this._UploadLocalFile("EMBED", "src");
	},
	_ExtractFileName : function(_sFileName){
		if(_sFileName.length>13 && _sFileName.substring(0, 2)=="U0"){
			return _sFileName;
		}
		var nStartPos = _sFileName.indexOf("<FILENAME>");
		var nEndPos = _sFileName.indexOf("</FILENAME>");
		if(nStartPos<0 || nEndPos<0 || nEndPos<nStartPos){
			//alert("WCM SOAP服务配置有误！\n SOAP Return Info=["+this.m_OfficeActiveX.GetMyMsg()+"]");
			//使用GetErrorInfo()得到的信息对可能的问题调试更有帮助
			alert("WCM SOAP服务配置有误！\n FileName="+_sFileName+" \nSOAP Return Info=["+this.m_OfficeActiveX.GetErrorInfo()+"]");
			return;
		}

		return _sFileName.substring(nStartPos + "<FILENAME>".length, nEndPos);
	},
	_IsWebPicFile : function(_element, _sSrc){
		var sFlag = m_sProtocal + "//" + m_sServerName +  "/webpic/W0";
		if(_sSrc.indexOf(sFlag) == 0){
			return true;
		}
		sFlag = "/webpic/W0";
		if(_sSrc.indexOf(sFlag) == 0){
			return true;
		}
		return false;
	},
	_VerifyAnchor : function(_element){
		if(!_element) return false;
		if(_element.tagName != "A") return true;
		if(_element.className == "b" && _element.onfocus == "h()" && _element.onclick == "return false") return false;
	},
	_UploadLocalFile : function(_sTagName,_sAttributeName){//上传指定元素的本地文件
		//记录已经处理过的文件
		var oHasDowith = {};
		if ( FCK.EditMode == FCK_EDITMODE_SOURCE ){
			//TODO swtich to FCK_EDITMODE_WYSIWYG...
			//FCK.SwitchEditMode(true) ;
			//TODO maybe something wrong, test litter
			try{
				var sHtml = FCK.EditingArea.Textarea.value ;
				var regElements = new RegExp("<"+_sTagName.trim()+"\\s[^>]*"+_sAttributeName.trim()+"\\s*=[^>]*>",'ig');
				sHtml = sHtml.replace(regElements,function(_a0){
					var regUploadPic = new RegExp("\\sUploadPic\\s*=(\"|\')(.*?)\\1",'ig');
					var sUploadPic = (regUploadPic.exec(_a0)||[])[2];
					if(sUploadPic) return _a0;
					var regAttr = new RegExp("\\s"+_sAttributeName.trim()+"\\s*=(\"|\')(.*?)\\1",'ig');
					_a0 = _a0.replace(regAttr,function(_a0,_a1,_a2){
						var strLocalFile = _a2;
						if(OfficeActiveX._IsWebPicFile(null, strLocalFile))
							strUploadPicName = strLocalFile.substring(strLocalFile.lastIndexOf("/")+1);
						else			
							strUploadPicName = oHasDowith[strLocalFile] || sUploadPic;			
						//仅当IMG的UploadPic属性值为空时进行如下处理
						if(strUploadPicName == null || strUploadPicName.length <= 0){				
							var sSrc = strLocalFile;
							if(!(sSrc == null || sSrc.length<=0 || !sSrc.match(/^(file:\/{2,})/ig))){
								sSrc = decodeURIComponent(sSrc.replace(/^(file:\/{2,})/ig,'').replace(/\//g,'\\'));
								var sUploadedFile = OfficeActiveX.m_OfficeActiveX.UploadFile(sSrc);
								if(!sUploadedFile && FCKConfig.ServerUrl){
									var sSOAP_URL = null;
									if(FCKConfig.SingleSOAPApp){
										sSOAP_URL = config.ServerUrl +"/soap/servlet/rpcrouter";
									}else{
										sSOAP_URL = config.ServerUrl +"/"+m_sAppName+"/services";
									}
									OfficeActiveX.m_OfficeActiveX.SetSOAPURL(sSOAP_URL, "urn:FileService", "sendFileBase64", "");
									sUploadedFile = OfficeActiveX.m_OfficeActiveX.UploadFile(sSrc);
								}
								strUploadPicName = OfficeActiveX._ExtractFileName(sUploadedFile);
							}
						}
						//如果获取上传文件名出错，则get错误信息
						if(strUploadPicName == "" || strUploadPicName == null)
							return _a0;
						//记录已经处理过的记录
						oHasDowith[strLocalFile] = strUploadPicName;				
						//将得到的上传文件名添加到各自IMG对象的UploadPic属性中
						return " " + _sAttributeName + "=\"" + strLocalFile + "\" UploadPic=\"" + strUploadPicName + "\"";						
					});
					return _a0;
				});
				FCK.EditingArea.Textarea.value = sHtml;
			}catch(err){
				//TODO logger
				//Just Skip it now.
			}
			return;
		}
		if(!FCK.EditorDocument)return;
		//校验数据有效性
		if(_sTagName == null || _sTagName.length<=0 
			|| _sAttributeName==null || _sAttributeName.length<=0) return;
		//获取指定的元素集合
		var arrElements = FCK.EditorDocument.getElementsByTagName(_sTagName.toUpperCase());
		//遍历所有IMG对象
		for(var i=0;i<arrElements.length;i++) {
			var strUploadPicName = null;
			var element = arrElements[i];
			if(!element || !this._VerifyAnchor(element)) continue;

			var strLocalFile = element.getAttribute(_sAttributeName,2);
			if(this._IsWebPicFile(arrElements[i], strLocalFile))
				strUploadPicName = strLocalFile.substring(strLocalFile.lastIndexOf("/")+1);
			else			
				strUploadPicName = oHasDowith[strLocalFile] || element.getAttribute('UploadPic',2);			
			//仅当IMG的UploadPic属性值为空时进行如下处理
			if(strUploadPicName == null || strUploadPicName.length <= 0){				
				//根据本地文件名获得上传时的文件名				
				try{
					var sSrc = strLocalFile;
					if(sSrc == null || sSrc.length<=0)continue;
					if(!sSrc.match(/^(file:\/{2,})/ig))continue;
					sSrc = decodeURIComponent(sSrc.replace(/^(file:\/{2,})/ig,'').replace(/\//g,'\\'));
					/*
					var sFileFlag = "file:///";
					var nPose = sSrc.indexOf(sFileFlag);
					if(nPose < 0){
						sFileFlag = "file://";
						nPose = sSrc.indexOf(sFileFlag);						
					}
					if(nPose >= 0){
						sSrc = sSrc.substring(nPose+sFileFlag.length);
						sSrc = sSrc.replace(/\//gi, "\\");						
					}else{
						if(sSrc.indexOf("\\")<=0)continue;
					}					
					*/
					var sUploadedFile = this.m_OfficeActiveX.UploadFile(sSrc);
					if(!sUploadedFile && FCKConfig.ServerUrl){
						var sSOAP_URL = null;
						if(FCKConfig.SingleSOAPApp){
							sSOAP_URL = config.ServerUrl +"/soap/servlet/rpcrouter";
						}else{
							sSOAP_URL = config.ServerUrl +"/"+m_sAppName+"/services";
						}
						this.m_OfficeActiveX.SetSOAPURL(sSOAP_URL, "urn:FileService", "sendFileBase64", "");
						sUploadedFile = this.m_OfficeActiveX.UploadFile(sSrc);
					}
					strUploadPicName = this._ExtractFileName(sUploadedFile);
				}catch(e){
					alert(e+"\n可能原因:"+'\n1,未设置当前站点为信任站点.\n2,防火墙对SOAP端口['+location.port+']的拦截.');
					continue;
				}
				//如果获取上传文件名出错，则get错误信息
				if(strUploadPicName == "" || strUploadPicName == null)
					continue;
				//记录已经处理过的记录
				oHasDowith[strLocalFile] = strUploadPicName;				
				//将得到的上传文件名添加到各自IMG对象的UploadPic属性中
				element.setAttribute("UploadPic", strUploadPicName);						
			}
//			element.setAttribute(_sAttributeName, strUploadPicName);		
		}
	}
}
Event.observe(window,'unload',function(){
	clearTimeout(myActualTop.autosave);
	if(FCK.autopasteInterval){
		clearInterval(FCK.autopasteInterval);
		FCK.autopasteInterval = false;
	}
});
function cleanExcelCode(_sHtml){
	var html = _sHtml;	
	var nStartPos = html.indexOf("<x:ClientData");
	if(nStartPos >= 0){
		var nEndPos = html.indexOf("</x:ClientData>", nStartPos);
		if(nEndPos>0){
			html = html.substring(0, nStartPos) + html.substring(nEndPos + "</x:ClientData>".length);			
		}
	}
	
	// Remove all Col tags
	html = html.replace(/<COL\s.[^>]*>/gi, "" );
	html = html.replace(/<\/?COLGROUP[^>]*>/gi, "" );
	return html;
}

// This function will be called from the PasteFromWord dialog (fck_paste.html)
// Input: oNode a DOM node that contains the raw paste from the clipboard
// bIgnoreFont, bRemoveStyles booleans according to the values set in the dialog
// Output: the cleaned string
function CleanWord( sHtml, bIgnoreFont, bRemoveStyles )
{
//	return sHtml;
	var html = sHtml ;
	bIgnoreFont = true;
	bRemoveStyles = false;
	html = html.replace(/<o:p>\s*<\/o:p>/g, '') ;
	html = html.replace(/<o:p>.*?<\/o:p>/g, '&nbsp;') ;

	// Remove mso-xxx styles.
	html = html.replace( /\s*mso-[^:]+:[^;"]+;?/gi, '' ) ;

//	html = html.replace( /(\s*BORDER(|-LEFT|-TOP|-RIGHT|-BOTTOM)): medium none;?/gi, 'border: windowtext 0.5pt solid;' ) ;
	// Remove margin styles.
	html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*;/gi, '' ) ;
	html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*"/gi, "\"" ) ;

	html = html.replace( /\s*TEXT-INDENT: 0cm\s*;/gi, '' ) ;
	html = html.replace( /\s*TEXT-INDENT: 0cm\s*"/gi, "\"" ) ;

	html = html.replace( /\s*TEXT-ALIGN: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*PAGE-BREAK-BEFORE: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*FONT-VARIANT: [^\s;]+;?"/gi, "\"" ) ;

	html = html.replace( /\s*tab-stops:[^;"]*;?/gi, '' ) ;
	html = html.replace( /\s*tab-stops:[^"]*/gi, '' ) ;

	// Remove FONT face attributes.
	if ( bIgnoreFont )
	{
		html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
		html = html.replace( /\s*face=[^ >]*/gi, '' ) ;

		html = html.replace( /([\s";])FONT-FAMILY:[^;"]*;?/gi, '$1' ) ;
	}

	// Remove Class attributes
	html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, "<$1$3") ;

	html = html.replace( /([\s";])mso-[a-z\-]+:[^;"]*;?/gi, '$1' ) ;
	// Remove styles.
	if ( bRemoveStyles )
		html = html.replace( /<(\w[^>]*) style="([^\"]*)"([^>]*)/gi, "<$1$3" ) ;

	// Remove empty styles.
	html =  html.replace( /\s*style="\s*"/gi, '' ) ;

	html = html.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/gi, '&nbsp;' ) ;

	html = html.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;

	// Remove Lang attributes
	html = html.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi, "<$1$3") ;

//	html = html.replace( /<SPAN\s*>(.*?)<\/SPAN>/gi, '$1' ) ;

//	html = html.replace( /<FONT\s*>(.*?)<\/FONT>/gi, '$1' ) ;

	// Remove XML elements and declarations
	html = html.replace(/<\\?\?xml[^>]*>/gi, '' ) ;

	// Remove Tags with XML namespace declarations: <o:p><\/o:p>
	html = html.replace(/<\/?\w+:[^>]*>/gi, '' ) ;

	// Remove comments [SF BUG-1481861].
	html = html.replace(/<\!--.*-->/g, '' ) ;

	html = html.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;

	html = html.replace( /<H\d>\s*<\/H\d>/gi, '' ) ;

	// Remove "display:none" tags.
	html = html.replace( /<(\w+)[^>]*\sstyle="[^"]*DISPLAY\s?:\s?none(.*?)<\/\1>/ig, '' ) ;
	//HTML链接中可能存在链入本页面的锚点
	html = html.replace(/(<\w+[^>]*\s)href="([^"]*)(#.+)"/ig,
	function(a0,a1,a2,a3){
		if(a2.indexOf("/WCMV6/editor/editor/fckeditor.html?InstanceName=TRS_Editor&amp;Toolbar=WCM6")!=-1){
			return a1+'href="'+a3+'"';
		}
		return a0;
	});
	if ( FCKConfig.CleanWordKeepsStructure )
	{
		// The original <Hn> tag send from Word is something like this: <Hn style="margin-top:0px;margin-bottom:0px">
		html = html.replace( /<H(\d)([^>]*)>/gi, '<h$1>' ) ;

		// Word likes to insert extra <font> tags, when using MSIE. (Wierd).
//		html = html.replace( /<(H\d)><FONT[^>]*>(.*?)<\/FONT><\/\1>/gi, '<$1>$2<\/$1>' );
//		html = html.replace( /<(H\d)><EM>(.*?)<\/EM><\/\1>/gi, '<$1>$2<\/$1>' );
	}
	else
	{
		html = html.replace( /<H1([^>]*)>/gi, '<div$1><b><font size="6">' ) ;
		html = html.replace( /<H2([^>]*)>/gi, '<div$1><b><font size="5">' ) ;
		html = html.replace( /<H3([^>]*)>/gi, '<div$1><b><font size="4">' ) ;
		html = html.replace( /<H4([^>]*)>/gi, '<div$1><b><font size="3">' ) ;
		html = html.replace( /<H5([^>]*)>/gi, '<div$1><b><font size="2">' ) ;
		html = html.replace( /<H6([^>]*)>/gi, '<div$1><b><font size="1">' ) ;

		html = html.replace( /<\/H\d>/gi, '<\/font><\/b><\/div>' ) ;

		// Transform <P> to <DIV>
//		var re = new RegExp( '(<P)([^>]*>.*?)(<\/P>)', 'gi' ) ;	// Different because of a IE 5.0 error
//		html = html.replace( re, '<div$2<\/div>' ) ;

		// Remove empty tags (three times, just to be sure).
		// This also removes any empty anchor
		html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
		html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
		html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
	}
	return html ;
}

FCK.Paste = function()
{
	// As we call ExecuteNamedCommand('Paste'), it would enter in a loop. So, let's use a semaphore.
	if ( FCK._PasteIsRunning )
		return true ;

	if ( FCKConfig.ForcePasteAsPlainText )
	{
		FCK.PasteAsPlainText() ;
		return false ;
	}

	var sHTML = FCK._CheckIsPastingEnabled( true ) ;

//	if ( sHTML === false )
//		FCKTools.RunFunction( FCKDialog.OpenDialog, FCKDialog, ['FCKDialog_Paste', FCKLang.Paste, 'dialog/fck_paste.html', 400, 330, 'Security'] ) ;
//	else
	{
		if (FCKConfig.AutoDetectPasteFromWord && sHTML.length > 0 )
		{
			var re = /<\w[^>]*(( class="?MsoNormal"?)|(="mso-))/gi ;
			var reColGroup = /<COLGROUP>/gi;
			var reExcel = /<\w[^>]* class="?xl"?/gi ;
			if ( re.test( sHTML ) || reColGroup.test(sHTML) || reExcel.test(sHTML) )
			{
				if (true || confirm( FCKLang.PasteWordConfirm ) )
				{
					FCK.PasteFromWord() ;
					return false ;
				}
			}
		}

		// Instead of inserting the retrieved HTML, let's leave the OS work for us,
		// by calling FCK.ExecuteNamedCommand( 'Paste' ). It could give better results.

		// Enable the semaphore to avoid a loop.
		FCK._PasteIsRunning = true ;

		FCK.ExecuteNamedCommand( 'Paste' ) ;

		// Removes the semaphore.
		delete FCK._PasteIsRunning ;
	}

	// Let's always make a custom implementation (return false), otherwise
	// the new Keyboard Handler may conflict with this code, and the CTRL+V code
	// could result in a simple "V" being pasted.
	return false ;
}

FCKXml.prototype.LoadUrl = function( urlToCall )
{
	this.Error = false ;

	var oXmlHttp = FCKTools.CreateXmlObject( 'XmlHttp' ) ;

	if ( !oXmlHttp )
	{
		this.Error = true ;
		return ;
	}

	oXmlHttp.open( "GET", urlToCall, false ) ;

	oXmlHttp.send( null ) ;

	if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ){
		var sContentType = oXmlHttp.getResponseHeader('Content-Type');
		if(sContentType.indexOf('/xml')!=-1){
			this.DOMDocument = oXmlHttp.responseXML ;
		}
		else{
			this.DOMDocument = FCKTools.CreateXmlObject( 'DOMDocument' ) ;
			this.DOMDocument.async = false ;
			this.DOMDocument.resolveExternals = false ;
			this.DOMDocument.loadXML( oXmlHttp.responseText ) ;
		}
	}
	else if ( oXmlHttp.status == 0 && oXmlHttp.readyState == 4 )
	{
		this.DOMDocument = FCKTools.CreateXmlObject( 'DOMDocument' ) ;
		this.DOMDocument.async = false ;
		this.DOMDocument.resolveExternals = false ;
		this.DOMDocument.loadXML( oXmlHttp.responseText ) ;
	}
	else
	{
		this.DOMDocument = null ;
	}

	if ( this.DOMDocument == null || this.DOMDocument.firstChild == null )
	{
		this.Error = true ;
		if (window.confirm( 'Error loading "' + urlToCall + '"\r\nDo you want to see more info?' ) )
			alert( 'URL requested: "' + urlToCall + '"\r\n' +
						'Server response:\r\nStatus: ' + oXmlHttp.status + '\r\n' +
						'Response text:\r\n' + oXmlHttp.responseText ) ;
	}
}
FCK.InsertHtml = function( html )
{
	html = FCKConfig.ProtectedSource.Protect( html ) ;
	html = FCK.ProtectEvents( html ) ;
	html = FCK.ProtectUrls( html ) ;
	html = FCK.ProtectTags( html ) ;

//	FCK.Focus() ;
	FCK.EditorWindow.focus() ;

	FCKUndo.SaveUndoStep() ;

	// Gets the actual selection.
	var oSel = FCK.EditorDocument.selection ;

	// Deletes the actual selection contents.
	if ( oSel.type.toLowerCase() == 'control' )
		oSel.clear() ;

	// Using the following trick, any comment in the begining of the HTML will
	// be preserved.
	html = '<span id="__fakeFCKRemove__">&nbsp;</span>' + html ;

	try{
	// Insert the HTML.
	oSel.createRange().pasteHTML( html ) ;
	}catch(err){
//		alert('不支持选中多个表格单元格进行当前操作.');
	}

	// Remove the fake node
	FCK.EditorDocument.getElementById('__fakeFCKRemove__').removeNode( true ) ;

	FCKDocumentProcessor.Process( FCK.EditorDocument ) ;
}

FCK.InsertHtmlAndSelect = function( html , _nCount)
{
	html = FCKConfig.ProtectedSource.Protect( html ) ;
	html = FCK.ProtectEvents( html ) ;
	html = FCK.ProtectUrls( html ) ;
	html = FCK.ProtectTags( html ) ;

//	FCK.Focus() ;
	FCK.EditorWindow.focus() ;

	FCKUndo.SaveUndoStep() ;

	// Gets the actual selection.
	var oSel = FCK.EditorDocument.selection ;

	// Deletes the actual selection contents.
	if ( oSel.type.toLowerCase() == 'control' )
		oSel.clear() ;

	// Using the following trick, any comment in the begining of the HTML will
	// be preserved.
	html = '<span id="__fakeFCKRemove__">&nbsp;</span>' + html ;

	try{
	// Insert the HTML.
	var oRange = oSel.createRange();
	oRange.pasteHTML( html ) ;
	oRange.moveStart('character',-1*_nCount);
	oRange.select();
	}catch(err){
//		alert('不支持选中多个表格单元格进行当前操作.');
	}

	// Remove the fake node
	FCK.EditorDocument.getElementById('__fakeFCKRemove__').removeNode( true ) ;

	FCKDocumentProcessor.Process( FCK.EditorDocument ) ;
}

FCKStyleCommand.prototype.Execute = function( styleName, styleComboItem )
{
	FCKUndo.SaveUndoStep() ;

	if ( styleComboItem.Selected )
		styleComboItem.Style.RemoveFromSelection() ;
	else{
		//TODO
		/*
		var aActiveStyles = this.GetActiveStyles();
		for (var i = 0; i < aActiveStyles.length; i++){
			aActiveStyles[i].RemoveFromSelection();
		}
		aActiveStyles = null;
		*/
		styleComboItem.Style.ApplyToSelection() ;
	}
	FCKUndo.SaveUndoStep() ;

	FCK.Focus() ;

	FCK.Events.FireEvent( "OnSelectionChange" ) ;
}

FCKExtractCommand.prototype.Execute = function(){
	var oSel = FCK.EditorDocument.selection;
	var oRange = oSel.createRange() ;
	switch(this.type){
		case 'Title':
			var eTitle = myActualTop.$('DocTitle');
			if(eTitle)eTitle.value = oRange.text;
			break;
		case 'Abstract':
			var eAbstract = myActualTop.$('DocAbstract');
			var sHtml = '';
			sHtml = oRange.htmlText;
			if(sHtml){
				sHtml = sHtml.replace(/\s+_fckxhtmljob="\d+"/ig,'');
			}
			if(eAbstract)eAbstract.value = sHtml||oRange.text;
			break;
	}
}
FCKIcon.prototype.CreateIconElement = function( document )
{
	var eIcon, eIconImage ;

	if ( this.Position )		// It is using an icons strip image.
	{
		var sPos = '-' + ( ( this.Position - 1 ) * this.Size ) + 'px' ;

		if ( FCKBrowserInfo.IsIE )
		{
			// <div class="TB_Button_Image"><img src="strip.gif" style="top:-16px"></div>

			eIcon = document.createElement( 'DIV' ) ;

			eIconImage = eIcon.appendChild( document.createElement( 'div' ) ) ;
			//eIconImage.src = FCK_SPACER_PATH ;
			eIconImage.className = "editor_toolbar_button";
			eIconImage.style.backgroundPosition	= '0px ' + sPos ;
			//eIconImage.src = this.Path ;
			//eIconImage.style.top = sPos ;
		}
		else
		{
			// <img class="TB_Button_Image" src="spacer.gif" style="background-position: 0px -16px;background-image: url(strip.gif);">

			eIcon = document.createElement( 'IMG' ) ;
			eIcon.src = FCK_SPACER_PATH ;
			eIcon.style.backgroundPosition	= '0px ' + sPos ;
			eIcon.style.backgroundImage		= 'url(' + this.Path + ')' ;
		}
	}
	else					// It is using a single icon image.
	{
		if ( FCKBrowserInfo.IsIE )
		{
			// IE makes the button 1px higher if using the <img> directly, so we
			// are changing to the <div> system to clip the image correctly.
			eIcon = document.createElement( 'DIV' ) ;			
			eIconImage = eIcon.appendChild( document.createElement( 'DIV' ) ) ;			
			//eIconImage.style.backgroundImage		= 'url(' + this.Path + ')' ;
			//eIconImage.style.width = eIconImage.style.height = "16px";
			var nEndPos = this.Path.lastIndexOf(".");
			var nStartPos = this.Path.lastIndexOf("/");
			if(nStartPos<0){
				nStartPos = this.Path.lastIndexOf("\\");
			}
			eIconImage.className = "editor_tb_"+this.Path.substring(nStartPos+1, nEndPos);
			//alert(this.Path);			
			//eIconImage = eIcon.appendChild( document.createElement( 'IMG' ) ) ;			
			//eIconImage.className = "editor_toolbar_flash"
			//eIconImage.src = this.Path ? this.Path : FCK_SPACER_PATH ;
			//alert(this.Path);
			
		}
		else
		{
			// This is not working well with IE. See notes above.
			// <img class="TB_Button_Image" src="smiley.gif">
			eIcon = document.createElement( 'IMG' ) ;
			//eIcon.src = this.Path ? this.Path : FCK_SPACER_PATH ;
			//eIcon
		}
	}

	eIcon.className = 'TB_Button_Image' ;

	return eIcon ;
}