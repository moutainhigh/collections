/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/


CKEDITOR.plugins.add( 'officeactivex', 
{
	lang : ['zh-cn','en'],

	init : function( editor )
	{
		if(CKEDITOR.env.ie && CKEDITOR.OfficeActiveX.GetActiveXState() != CKEDITOR.OfficeActiveX.ActiveXState_ERROR_STOP){
			CKEDITOR.OfficeActiveX.Init(editor);
		}
	}
});
(function(){
	/**
	*编辑器ActiveX对象
	*/
	var m_OfficeActiveX = null;
	
	/**
	*当前应用的host地址
	*/
	var ServerName = window.location.host;

	/**
	*当前应用的名称
	*/
	var AppName = window.location.pathname.split('/')[1];

	/**
	*当前应用使用的协议
	*/
	var Protocol = window.location.protocol;

	/**
	*WCM服务器的URL地址
	*/
	var WCM_URL = Protocol + "//" + ServerName;

	/**
	*支持单独制定SOAP服务器的地址，默认使用WCM服务器地址作为SOAP
	*/
	var ServerUrl = "" || WCM_URL;

	/**
	*是否启用单个应用的上传模式，默认为false
	*/
	var SingleSOAPApp = false;
	
	/**
	*上传的SOAP地址
	*/
	var SAOP_URL = SingleSOAPApp ? ServerUrl + "/soap/servlet/rpcrouter" : ServerUrl + "/" + AppName + "/services";
	
	/**
	*存放已经上传了的文件，可用于标识文件是否已经上传，避免重复上传
	*key,value => localFileName, uploadedFileName
	*/
	var UploadedCache = {};

	/**
	*是否启用ActiveX控件，如果为null，那么将由用户选择
	*/
	var ActiveXState = null;
	
	var editor = null;

	/**
	*判断指定文件名是否为webpic地址
	*/
	function IsWebPicFile(_sSrc){
		var sFlag = Protocol + "//" + ServerName +  "/webpic/W0";
		if(_sSrc && _sSrc.indexOf(sFlag) == 0){
			return true;
		}
		sFlag = "/webpic/W0";
		if(_sSrc && _sSrc.indexOf(sFlag) == 0){
			return true;
		}
		return false;
	}
	
	/**
	*获取符合规则的文件名称
	*/
	function ExtractFileName(_sFileName){
		if(_sFileName.length > 13 && _sFileName.substring(0, 2) == "U0"){
			return _sFileName;
		}
		var nStartPos = _sFileName.indexOf("<FILENAME>");
		var nEndPos = _sFileName.indexOf("</FILENAME>");
		if(nStartPos < 0 || nEndPos < 0 || nEndPos < nStartPos){
			alert((editor.lang.officeactivex.soapconfigerror || "WCM SOAP服务配置有误!") + "\n FileName="+_sFileName+" \nSOAP Return Info=["+m_OfficeActiveX.GetErrorInfo()+"]");
			return;
		}
		return _sFileName.substring(nStartPos + "<FILENAME>".length, nEndPos);
	}

	/**
	*根据传入的文件名，上传指定的文件
	*/
	function UploadLocalFileByFileName(strLocalFile){
		if(strLocalFile == null || strLocalFile.length <= 0){
			return null;
		}

		//已经上传，则直接返回上传后的值
		if(UploadedCache[strLocalFile]){
			return UploadedCache[strLocalFile];
		}

		//如果是webpic下的图片，则认为是已经上传，不需要做处理
		if(IsWebPicFile(strLocalFile)){
			return strLocalFile.substring(strLocalFile.lastIndexOf("/") + 1);
		}
		
		//不符合本地文件的规则，则忽略
		if(!strLocalFile.match(/^(file:\/{2,})/ig)){
			return null;
		}
		
		//获取格式化后的上传文件名
		var sSrc = decodeURIComponent(strLocalFile.replace(/^(file:\/{2,})/ig,'').replace(/\//g,'\\'));

		//根据本地文件名获得上传时的文件名  
		try{
			var strUploadPicName = CKEDITOR.OfficeActiveX.UploadFile(sSrc);

			//如果获取上传文件名出错或文件名不符合规则,则忽略
			if(!strUploadPicName || strUploadPicName.indexOf("U0") != 0){
				return null;
			}

			//记录已经处理过的记录
			UploadedCache[strLocalFile] = strUploadPicName;  
		}catch(e){			
			alert(
				e + 
				String.format(editor.lang.officeactivex.errorreason || "\n可能原因:\n1,未设置当前站点为信任站点.\n2,防火墙对SOAP端口[{0}]的拦截.", location.port)
			);
			return null;
		}
		return strUploadPicName;
	}


	/**
	*上传tag的属性attrName所指定的本地文件
	*/
	function UploadLocalFilesByTagName(editor, tagName, attrName){
		tagName = tagName.trim();
		attrName = attrName.trim();
		return arguments.callee.impl[editor.mode].apply(this, arguments);
	}

	/**
	*UploadLocalFilesByTagName的具体实现
	*/
	UploadLocalFilesByTagName.impl = {
		/**
		*编辑器在源码模式下的上传
		*/
		source : function(editor, tagName, attrName){
			//获取编辑器的html内容
			var sHtml = editor.getDate();

			//构造当前需要处理的标记及属性的正则
			var regElement = new RegExp("<" + tagName + "\\s[^>]*" + attrName + "\\s*=[^>]*>", 'ig');
			
			//对匹配的元素进行正则替换
			sHtml = sHtml.replace(regElement, function(tag){
				//判断是否已经upload了
				var regUploadPic = new RegExp("\\sUploadPic\\s*=(\"|\')(.*?)\\1", 'ig');
				var sUploadPic = (regUploadPic.exec(tag) || [])[2];
				if(sUploadPic) return tag;

				//获取需要上传的属性值
				var regAttr = new RegExp("\\s" + attrName + "\\s*=(\"|\')(.*?)\\1",'ig');

				tag = tag.replace(regAttr, function(_a0, _a1, _a2){
					var strLocalFile = _a2;

					//上传本地文件
					strUploadPicName = UploadLocalFileByFileName(strLocalFile);

					//如果获取上传文件名出错,则返回原标记的内容
					if(!strUploadPicName){
						return tag;
					}

					//将得到的上传文件名添加到各自IMG对象的UploadPic属性中
					return " " + attrName + "=\"" + strLocalFile + "\" UploadPic=\"" + strUploadPicName + "\"";  
				});

				return tag;
			});

			//将替换后的内容，设置到编辑器中
			editor.setData(sHtml);
		},

		/**
		*编辑器在所见即所得模式下的上传
		*/
		wysiwyg : function(editor, tagName, attrName){
			//校验数据有效性
			if(tagName == null || tagName.length<=0 
				|| attrName==null || attrName.length<=0) return;

			var document = editor.document.$;
			if(!document) return;

			//获取指定的元素集合
			var doms = document.getElementsByTagName(tagName);

			//遍历所有类IMG对象
			for(var i = 0; i < doms.length; i++) {
				var dom = doms[i];
				if(!dom) continue;

				var strLocalFile = dom.getAttribute(attrName, 2);
				var strUploadPicName = UploadLocalFileByFileName(strLocalFile);

				if(strUploadPicName){
					//将得到的上传文件名添加到各自IMG对象的UploadPic属性中
					dom.setAttribute("UploadPic", strUploadPicName);  
				}		
			}
		}
	};


	//Office ActiveXObject 控件对象,用于在客户端处理Office文档
	CKEDITOR.OfficeActiveX = {

		/**
		*ActiveX安装成功
		*/
		ActiveXState_OK : "0",

		/**
		*ActiveX安装失败，但下次继续安装
		*/
		ActiveXState_ERROR_RESUME : "1",

		/**
		*ActiveX安装失败，下次不再安装
		*/
		ActiveXState_ERROR_STOP : "-1",

		/**
		*对ActiveX控件进行初始化
		*/
		Init : function(_editor){
			editor = _editor;

			//创建ActiveX控件
			var dom = document.createElement('div');
			dom.innerHTML =  "<OBJECT id=\"WORD_CLIENT\" CLASSID=" +
					"\"clsid:D6641A7A-B6F8-4FC7-A382-624DDBAEF96F\" " +
					"codeBase=\"" + CKEDITOR.plugins.getPath( 'officeactivex' ) + "WCMOffice.cab#Version=1,0,0,29\" style=\"display:none\"></OBJECT>";
			document.body.appendChild(dom);

			//获取控件
			m_OfficeActiveX = document.getElementById("WORD_CLIENT");
			
			if(m_OfficeActiveX == null){
				var sMsg = editor.lang.officeactivex.installerror || "文件上传及Office抽取控件安装失败.";
				sMsg += (editor.lang.officeactivex.enablesite || "\n开启此控件需要设置当前站点为信任站点.");
				sMsg += (editor.lang.officeactivex.installagain || "\n您以后是否尝试再次安装此控件?");

				SetCookie('ActiveXState', confirm(sMsg) ? this.ActiveXState_ERROR_RESUME : this.ActiveXState_ERROR_STOP);
				return;
			}

			try{				
				m_OfficeActiveX.SetSOAPURL(SAOP_URL, "urn:FileService", "sendFileBase64", WCM_URL); 
			}catch(e){
				var sMsg = String.format(editor.lang.officeactivex.setsoaperror || "设置SOAP服务地址[{0}]失败.", SAOP_URL);
				sMsg += (editor.lang.officeactivex.soaperrorreason || "\n失败原因可能是由于文件上传及Office抽取控件没有安装成功或被禁用.");
				sMsg += (editor.lang.officeactivex.soapinstallagain || "\n您以后是否尝试再次进行SOAP地址设置?");

				SetCookie('ActiveXState', confirm(sMsg) ? this.ActiveXState_ERROR_RESUME : this.ActiveXState_ERROR_STOP);
				return;
			}

			SetCookie('ActiveXState', this.ActiveXState_OK);
		},

		/**
		*获取控件的安装状态，控件安装之后有三种状态：安装成功—— 0；安装失败，下次继续安装—— 1；安装失败，下次不再安装—— -1
		*/
		GetActiveXState : function(){
			if(ActiveXState != null) return ActiveXState;
			
			var oCookie = LoadCookie();
			ActiveXState = oCookie['ActiveXState'] || this.ActiveXState_OK;

			//避免cookie过期
			SetCookie('ActiveXState', ActiveXState);

			return ActiveXState;
		},
		
		/**
		*获取控件是否可用
		*/
		IsUsed : function(){
			return (m_OfficeActiveX != null) && (this.GetActiveXState() == this.ActiveXState_OK);
		},

		FormatWordClip : function(){//格式化word代码,转换图片为本地文件名
			if(!this.IsUsed()) return;
			try{
				//格式化word源码,去除多余的格式信息
				m_OfficeActiveX.SetOfficeFilter(true);
				m_OfficeActiveX.PasteContent(false); 
			}catch(e){
				//Just Skip it.
			}
		},

		GetWordContent : function(){
			if(!this.IsUsed()) return;
			try{
				//格式化word源码,去除多余的格式信息
				return m_OfficeActiveX.GetClipData();
			}catch(e){
				//Just Skip it.
			}
		},

		/**
		*上传指定的本地文件，返回上传之后的文件名
		*/
		UploadFile : function(sFileName){
			if(!this.IsUsed()) return "";
			return ExtractFileName(m_OfficeActiveX.UploadFile(sFileName));
		},

		/**
		*上传指定编辑器中的本地文件
		*/
		UploadLocals : function(editor){
			if(!this.IsUsed()) return;

			editor = editor || CKEDITOR.currentInstance;

			//根据图片的本地文件名获得uploadpic的文件名,并添加这个属性到各IMG对象中
			//上传Img中的图片
			UploadLocalFilesByTagName(editor, "IMG", "src");
			//上传A中的本地文件
			UploadLocalFilesByTagName(editor, "A", "href");
			//上传Flash,音频,视频
			UploadLocalFilesByTagName(editor, "EMBED", "src");
		}
	};


	/*-----------------------cookie related-----------------------*/
	function SetCookie(_sCookieName, _sCookieValue){
		var myCookie = '';
		var sSaveValue = null;
		sSaveValue = escape(_sCookieValue);
		myCookie += _sCookieName+"="+sSaveValue+"";
		var expires = new Date();
		expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
		if(document.domain =="localhost")
			myCookie += "; path=/; expires=" + expires.toGMTString()+";";
		else
			myCookie += "; path=/; expires=" + expires.toGMTString()+";domain="+document.domain;
		document.cookie = myCookie;
	}

	function LoadCookie(){
		var myCookies = document.cookie.split(";");
		var oCookieData = {};
		for(var i=0; i<myCookies.length; i++){
			var cookiePair = myCookies[i].split("=");
			if(cookiePair[0].trim()=='expires')continue;
			oCookieData[cookiePair[0].trim()] = unescape(cookiePair[1]);
		}
		return oCookieData;
	}

	function ClearCookie(_sCookieName){
		var myCookie = '';
		var sSaveValue = null;
		myCookie += _sCookieName+"=false";
		var expires = new Date();
		expires.setTime(expires.getTime() - 1);
		if(document.domain =="localhost")
			myCookie += "; path=/; expires=" + expires.toGMTString() + ";";
		else{
			myCookie += "; path=/; expires=" + expires.toGMTString() + ";domain=" + document.domain;
		}
		document.cookie = myCookie;
	}

	/*---------------------------String utility-------------------*/
	if(!String.format){
		String.format = function(format){
			var args = Array.prototype.slice.call(arguments, 1);
			return format.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
	}
	if(!String.prototype.trim){
		String.prototype.trim = function(){
			return this.replace(/^\s*/, "").replace(/\s*$/, "");
		}
	}
})();
