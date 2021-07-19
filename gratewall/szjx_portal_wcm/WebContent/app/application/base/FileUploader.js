var FileUploader = Class.create();
Object.extend(FileUploader, {
	_cache_ : [],
	onUploadAll : null,
	getCache : function(){
		return this._cache_;
	},
	setUploadAll : function(fUploadAll){
		this.onUploadAll = fUploadAll;
	},
	isEmptyValue : function(){
		return this.isUploadAll();
	},
	isUploadAll : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			if(instance.isBindData) return false;
		}
		return true;
	},
	destory : function(){
		for (var i = 0, length = this._cache_.length; i < length; i++){
			var instance = this._cache_[i];
			delete instance["oRelateElement"];
			delete instance["oIframeElement"];
			delete instance["oFileControl"];
			delete instance["fChangeEvent"];
			delete instance["fUploadedCallBack"];
			delete instance["fUploadedCallBack2"];
			this._cache_[i] = null;
		}
		delete FileUploader["onUploadAll"];
		this._cache_ = [];
	}
});
Object.extend(FileUploader.prototype, {
	uploadSrc					: 'file_upload.html',
	uploadDoWithSrc				: 'file_upload_dowith.jsp',
	appendixIframeIdSuffix		: '_iframe',
	appendixFileControlId		: 'fileNameControl',
	oRelateElement				: null,
	fChangeEvent				: null,
	fUploadedCallBack			: null,
	fUploadedCallBack2			: null,
	oIframeElement				: null,
	oFileControl				: null,
	isBindData					: false,

	initialize : function(sRelateElement, fChangeEvent, fUploadedCallBack){
		//1.cache the instance, for destroy.
		FileUploader._cache_.push(this);

		//2.init apperance.
		this.oRelateElement = $(sRelateElement);
		this.fChangeEvent = fChangeEvent;
		this.fUploadedCallBack = fUploadedCallBack;
		this.oIframeElement = document.createElement('iframe');
		this.oIframeElement.id = sRelateElement + this.appendixIframeIdSuffix;
		this.oIframeElement.style.display = 'none';
		this.oIframeElement.src = this.uploadSrc;
		document.body.appendChild(this.oIframeElement);

		//3.init actions--events.
		this.initEvent();
	},
	initEvent : function(){
		//1.bind this.oRelateElement events.
		Event.observe(this.oRelateElement, 'click', this.onBrowse.bind(this));

		//2.bind this.oIframeElement events.
		Event.observe(this.oIframeElement, 'readystatechange', this.onIframeStateChanged.bind(this));
	},
	onBrowse : function(){
		try{			
			if(!this.oFileControl){
				this.oFileControl = this.oIframeElement.contentWindow.document.getElementById(this.appendixFileControlId);
				this.oFileControl.onchange = function(event){
					this.isBindData = true;
					if(this.fChangeEvent){
						this.fChangeEvent(this.oFileControl.value);
					}
				}.bind(this);
			}	
			this.oFileControl.click();
		}catch(error){
			alert(error.message);
		}
	},
	onIframeStateChanged : function(){
		if(!this.isBindData) return;
		if(this.oIframeElement.readyState.toLowerCase() != 'complete') return;
		this.onUpload();
	},
	onUpload : function(){
		var oInfoDiv = this.oIframeElement.contentWindow.document.getElementById("infoId");
		if(oInfoDiv){
			if(oInfoDiv.getAttribute("isError")){
				alert(String.format(wcm.LANG.METAVIEWDATA_109 || "上传文件失败！\n {0}", oInfoDiv.innerText));
			}else{
				this.isBindData = false;
				var fUploadCallBack = this.fUploadedCallBack2 || this.fUploadedCallBack;
				if(fUploadCallBack){
					fUploadCallBack(decodeURI(oInfoDiv.innerHTML));
				}
			}
		}
		if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
			FileUploader.onUploadAll();
		}
		this.oIframeElement.setAttribute("src", this.uploadSrc);
	},
	reset : function(){
		//reset the file control value by reload the page.
		if(!this.oFileControl) return;
		this.isBindData = false;
		this.oFileControl.form.reset();
	},
	doUpload : function(fUploadedCallBack){
		if(!this.oFileControl || !this.isBindData){
			//not trigger the browser action, so return.
			if(FileUploader.isUploadAll() && FileUploader.onUploadAll){
				FileUploader.onUploadAll();
			}
			return;
		}
		this.fUploadedCallBack2 = fUploadedCallBack;
		var sValue = this.oFileControl.value;
		var fileNameValue = sValue.substring(sValue.lastIndexOf("\\")+1);
		var sParams = "fileNameParam=" + this.appendixFileControlId + "&fileNameValue="+encodeURI(fileNameValue);
		var fileForm = this.oFileControl.form;
		fileForm.action = this.uploadDoWithSrc + "?" + sParams;
		fileForm.submit();
	}
});

// destroy the FileUploader cache.
Event.observe(window, 'unload', function(){
	FileUploader.destory();
});
/**-------------------------------------------------------------------------**/

var FileDownloader = Class.create();
Object.extend(FileDownloader, {
	download : function(sUrl){
		var frm = (top.actualTop||top).$('iframe4download');
		if(!frm) {
			frm = (top.actualTop||top).document.createElement('IFRAME');
			frm.id = "iframe4download";
			frm.style.display = 'none';
			(top.actualTop||top).document.body.appendChild(frm);
		}
		frm.src = sUrl;		
	}
});
