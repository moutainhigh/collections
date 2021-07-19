Ext.ns("wcm.LANG", "WCMLANG");
var YUIConnect = {
	_tid : 0,
	asyncRequest:function(method, uri, callback){
		var id = this._tid;
		var frmId = 'yui' + id;
		var io = $(frmId);
		var fm = this.fm;
		fm.action = uri;
		fm.enctype = 'multipart/form-data';
		fm.method = 'POST';
		fm.target = frmId;
		try{
			fm.submit();
		}catch(err){
			alert(err.message+ WCMLANG["UPLOAD_1"] || '\n原因：文件路径不存在或者非本地文件路径。');
			return;
		}
		var uploadCallback = function(){
			var obj = {};
			obj.tId = id;
			var doc = io.contentWindow.document;
			obj.responseText = doc.body ? doc.body.innerHTML : null;
			obj.responseXML = doc.XMLDocument ? doc.XMLDocument : doc;
			obj.argument = callback.argument;
			if(callback.upload){
				callback.upload.apply(callback.scope || null, [obj]);
			}
			Event.stopObserving(io, 'load', uploadCallback);
			setTimeout(function(){ document.body.removeChild(io); }, 100);
		};
		Event.observe(io, 'load', uploadCallback);
		this._tid++;
	},
	setForm:function(formId, isUpload, secureUri){
		secureUri = secureUri ? 'javascript:false' : "about:blank";
		var frmId = 'yui' + this._tid;		
		try{
			var io = document.createElement('<IFRAME id="' + frmId + '" name="' + frmId + '">');
		}catch(error){
			var io = document.createElement('IFRAME');
		}
		if(!io.getAttribute("name")){
			io.id = frmId;
			io.name = frmId;
		}

		var ua = navigator.userAgent.toLowerCase();
		var isIE = ua.indexOf("msie") > -1;
		if(isIE){
			io.src = secureUri;
		}

		io.style.position = 'absolute';
		io.style.top = '-1000px';
		io.style.left = '-1000px';
		document.body.appendChild(io);
		this.fm = $(formId);
	}
};
var FileUploadHelper = {
	validFileExt :function(_strPath, _sAllowExt){
		var sAllowExt = _sAllowExt.replace(/\./ig,'');
		_strPath = _strPath || '';
		if(_strPath.length<=0){
			throw new Error( WCMLANG["UPLOAD_2"] || "没有选择文件！");
		}
		var validFileExtRe = new RegExp('\.('+_sAllowExt.split(',').join('|')+')$','ig');
		if(!validFileExtRe.test(_strPath)){
			throw new Error(String.format("只支持上传\"{0}\"格式的文件！",sAllowExt));
		}
		return true;
	}
}