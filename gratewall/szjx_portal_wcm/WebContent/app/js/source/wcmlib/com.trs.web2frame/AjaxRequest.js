Ext.ns('Ajax.Base', 'Ajax.Request',
	'com.trs.web2frame.AjaxRequest', 'com.trs.web2frame.PostData');

Ext.apply(Ajax, {
	getTransport: function() {
		return Ext.Try.these(
				function() {return new XMLHttpRequest()},
				function() {return new ActiveXObject('Msxml2.XMLHTTP')},
				function() {return new ActiveXObject('Microsoft.XMLHTTP')}
			) || false;
	}
});

Ajax.Base = function() {};
Ajax.Base.prototype = {
	setOptions: function(options) {
		this.options = {
			method:       'post',
			asynchronous: true,
			parameters:   ''
		}
		Ext.apply(this.options, options || {});
	},
	responseIsSuccess: function() {
		return this.transport.status == undefined
			|| this.transport.status == 0
			|| (this.transport.status >= 200 && this.transport.status < 300);
	},
	responseIsFailure: function() {
		return !this.responseIsSuccess();
	}
}
Ajax.Request = function(url, options){
	Ajax.Request.superclass.constructor.call(this);
	try {
		(options['onCreate'] || Ext.emptyFn)();
	} catch (e) {}
	if(!options.onException){
		options.onException = window.DefaultAjaxException || function(a, err){
			Ext.Msg.d$alert('Ajax Error:\n------\n['+url+']\n------\n'+Ext.errorMsg(err));
		}
	}
	this.transport = Ajax.getTransport();
	this.setOptions(options);
	this.createTime = new Date().getTime();
	this.url = url;
	if(this.url.length>1000){
		Ext.Msg.d$alert(String.format("URL已经超出1000个字节({0}), 建议使用POST提交数据！",this.url.length));
	}
	this.request(url);
}
var arr_Events = ['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];
Ext.extend(Ajax.Request, Ajax.Base, {
	request: function(url) {
		var parameters = this.options.parameters || '';
		try {
			this.url = url;
			if (this.options.method.toLowerCase() == 'get' && parameters.length > 0){
				this.url += (this.url.match(/\?/) ? '&' : '?') + parameters;
			}
			this.transport.open(this.options.method, this.url,
				this.options.asynchronous);
			if (this.options.asynchronous) {
				this.transport.onreadystatechange = this.onStateChange.bind(this);
				setTimeout((function() {this.respondToReadyState(1)}).bind(this), 10);
			}
			this.setRequestHeaders();
			var body = this.options.postBody ? this.options.postBody : parameters;
			this.transport.send(this.options.method == 'post' ? body : null);
		} catch (e) {
			this.dispatchException(e);
		}
	},
	setRequestHeaders: function() {
		var requestHeaders = ['X-Requested-With', 'XMLHttpRequest'];
		if (this.options.method == 'post') {
			requestHeaders.push('Content-type',
				this.options.contentType||'multipart/form-data');
//			requestHeaders.push('Content-type',
//				'application/x-www-form-urlencoded');
			/* Force "Connection: close" for Mozilla browsers to work around
			* a bug where XMLHttpReqeuest sends an incorrect Content-length
			* header. See Mozilla Bugzilla #246651.
			*/
			if (this.transport.overrideMimeType)
				requestHeaders.push('Connection', 'close');
		}
		if (this.options.requestHeaders){
			requestHeaders.push.apply(requestHeaders, this.options.requestHeaders);
		}
		for (var i = 0; i < requestHeaders.length; i += 2){
			this.transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
		}
	},
	onStateChange: function() {
		var readyState = this.transport.readyState;
		if (readyState != 1){
		  this.respondToReadyState(this.transport.readyState);
		}
	},
	header: function(name) {
		try {
			return this.transport.getResponseHeader(name);
		} catch (e) {}
	},
	evalJson: function() {
		var transport = this.transport;
		if(this.header('ReturnJson') == 'true'){
			return com.trs.util.JSON.eval(transport.responseText);
		}
		var sContentType = (this.header('Content-type') || '');
		if (sContentType.match(/^text\/javascript/i)){
			this.evalResponse();
			return null;
		}
		if(sContentType.indexOf('/html')!=-1){
			return null;
		}
		try{
			if(transport.responseXML){
				return com.trs.util.JSON.parseXml(transport.responseXML);
			}
			if(sContentType.indexOf('/xml')!=-1){
				return com.trs.util.JSON.parseXml(
					com.trs.util.XML.loadXML(transport.responseText));
			}
		}catch(err){
			//Just skip it.
		}
		return null;
	},
	evalResponse: function() {
		try {
			return eval(this.transport.responseText);
		} catch (e) {
			this.dispatchException(e);
		}
	},
	respondToReadyState: function(readyState) {
		var event = arr_Events[readyState];
		var transport = this.transport, json = null;
		if (event == 'Complete') {
			if(this.transport.status==12029 || this.transport.status==12007){
				(this.options['onFailure']||
							window.NotifySystemError||
							Ext.emptyFn)(transport, json, this);
				this.transport.onreadystatechange = Ext.emptyFn;
				return;
			}
			this.completeTime = new Date().getTime();
			try {
				var isNotLogin = this.header('TRSNotLogin');
				if(isNotLogin=='true'){
					var bBubble = (window.DefaultNotLogin||Ext.emptyFn)() || false;
					(this.options['onFailure']||Ext.emptyFn)(transport, json, this);
					if(bBubble===false) return;
				}
				var isError = this.header('TRSException');
				json = this.evalJson();
				if(this.responseIsSuccess()&&isError!='true'){
					try{
						(this.options['onSuccess']||Ext.emptyFn)(transport, json, this);
					}catch(error){
						if(Ext.isDebug()){
							alert(error.message + "\nerror in function:\n" + (this.options['onSuccess']||Ext.emptyFn));
							throw error;
						}
					}
				}
				else{
					if (isError=='true'||this.transport.status==500){
						(this.options['on500']||
							window.DefaultAjax500CallBack||
							window.DefaultAjaxFailureCallBack||
							Ext.emptyFn)(transport, json, this);
					}
					else{
						(this.options['onFailure']||
							window.DefaultAjaxFailureCallBack||
							Ext.emptyFn)(transport, json, this);
					}
				}
			} catch (e) {
				this.dispatchException(e);
			}
		}
		try {
			(this.options['on' + event] || Ext.emptyFn)(transport, json);
		} catch (e) {
			this.dispatchException(e);
		}
		/* Avoid memory leak in MSIE: clean up the oncomplete event handler */
		if (event == 'Complete'){
			this.transport.onreadystatechange = Ext.emptyFn;
		}
	},
	dispatchException: function(exception) {
		(this.options.onException || Ext.emptyFn)(this, exception);
	}
});
com.trs.web2frame.AjaxRequest = function(url,options){
	this.__claz = 'web2frame.AjaxRequest';
	options.parameters = this.parameters(options.parameters);
	options.method = (options.method)?options.method.toLowerCase():'get';
	if(options.isLocal){
		var _onSuccess = options['onSuccess'] || Ext.emptyFn;
		Ext.apply(options,{
			onSuccess:function(transport, json){
				try{
					if(Ext.isIE && transport.status==0){
						var text	= transport.responseText;
						if(text.indexOf('<?xml')==0){
							text		= text.replace(/<\?xml[^>]*\?>(\n)?/m,'');
						}
						var xml		= transport.responseXML;
						xml.loadXML(text);
					}
				}catch(err){}
				(_onSuccess)(transport, json);
			}
		});
	}
	com.trs.web2frame.AjaxRequest.superclass.constructor.call(this, url, options);
};
Ext.extend(com.trs.web2frame.AjaxRequest, Ajax.Request, {
	parameters: function(parameter){
		parameter = parameter||'';
		if (Ext.isArray(parameter)){
			var params=[];
			for(var i=0;i<parameter.length;i+=2){
				params.push(parameter[i+1] +
							 '=' + encodeURIComponent(parameter[i+1]));
			}
			return params.join('&');
		}
		else if ((typeof parameter)!='object'){
			return parameter;
		}
		else{
			var params = [];
			for(param in parameter){
				params.push(param + 
							'=' + encodeURIComponent(parameter[param]));
			}
			return params.join('&');
		}
	}
});


/*post data*/
com.trs.web2frame.PostData = {
	_elements : function(elements,_bCaseSensitive, render){
		var vData = {}, attrs = [], rst = {};
		for(var i=0; i<elements.length; i++){
			var oElement = elements[i];
			if(!oElement.name || oElement.getAttribute("ignore"))continue;
			var sValue = $F(oElement);
			if(sValue==null)continue;
			var name = _bCaseSensitive?oElement.name:oElement.name.toUpperCase();
			if(oElement.getAttribute("isAttr", 2)){
				attrs.push(name + '=' + sValue);
				continue;
			}
			var arr = vData[name];
			if(!arr){
				arr = vData[name] = [sValue];
			}
			else{
				arr.push(sValue);
			}
		}
		if(attrs.length > 0){
			var v = attrs.join('&');
			rst.ATTRIBUTE = render ? render(v) : {NODEVALUE : v};
		}
		for(var name in vData){
			var v = vData[name].join(",");
			rst[name] = render ? render(v) : {NODEVALUE : v};
		}
		return rst;
	},
	form : function(_sFormId, render) {
		var eForm = $(_sFormId);
		var elements = Form.getElements(eForm);
		var bCaseSensitive = (eForm.CaseSensitive)?
				(eForm.CaseSensitive.value=='true'||eForm.CaseSensitive.value=='1') : false;
		return this._elements(elements, bCaseSensitive, render);
	},
	elements : function(_bCaseSensitive){
		var elements = [];
		for(var i=0; i<arguments.length; i++){
			var tagElements = document.getElementsByName(arguments[i]);
			for (var j = 0; j < tagElements.length; j++){
				elements.push(tagElements[j]);
			}
		}
		return this._elements(elements, _bCaseSensitive);
	},
	param : function(_sName, _sValue, _oData, _bCaseSensitive){
		_oData = _oData || {};
		var oTmp = null;
		if(_bCaseSensitive){
			oTmp = _oData[_sName] = {};
		}
		else{
			oTmp = _oData[_sName.toUpperCase()] = {};
		}
		oTmp['NODEVALUE'] = _sValue;
		return _oData;
	},
	params : function(_oParams,_oData){
		_oData = _oData||{};
		var bCaseSensitive = (_oParams)?_oParams['CaseSensitive']:false;
		for(var sName in _oParams){
			if(typeof _oParams[sName]=='function')continue;
			if(_oParams[sName]==null){
				if(bCaseSensitive){
					_oData[sName] = {NODEVALUE:""};
				}
				else{
					_oData[sName.toUpperCase()] = {NODEVALUE:""};
				}
			}
			else{
				if(bCaseSensitive){
					_oData[sName] = {NODEVALUE:_oParams[sName]+""};
				}
				else{
					_oData[sName.toUpperCase()] = {NODEVALUE:_oParams[sName]+""};
				}
			}
		}
		return _oData;
	}
};
/* Basic DataHelper*/
com.trs.web2frame.BasicDataHelper = function(){
	this.WebService = WCMConstants.WCM_ROMOTE_URL || '/wcm/center.do';
	this.call = this.Call;
	this.combine = this.Combine;
	this.multiCall = this.MultiCall;
};
com.trs.web2frame.BasicDataHelper.prototype = {
	_BuildXml : function(_sServiceName,_sMethodName,_oPostData){
		var myDoc = com.trs.util.XML.loadXML('<post-data>'+
					'<method type="'+_sMethodName+'">'+
					 _sServiceName+
					'</method>'+
					'<parameters>'+
					'</parameters>'+
				'</post-data>');
		var parameters = myDoc.getElementsByTagName("parameters")[0];
		com.trs.util.JSON.parseJson2Element(myDoc, parameters, _oPostData);
		return myDoc;
	},
	_BuildUrl : function(_sServiceId,_sMethod,_sParams){
		if(_sMethod == 'get'){
			_sMethod = 'query';
		}
		else if(_sMethod=='findbyids'){
			if(!_sParams.match(/objectids=/ig)){
				_sParams = 'objectids='+_sParams;
			}
		}
		var retUrlQuery = 'serviceid='+_sServiceId+'&methodname='+_sMethod;
		if(_sParams) retUrlQuery += '&' + _sParams;
		return retUrlQuery;
	},
	_DoPost : function(_sUrl,_sServiceId,_sMethod,_oOptions){
		var myDoc = this._BuildXml(_sServiceId,_sMethod,_oOptions["postBody"]);
		_oOptions["postBody"] = myDoc;
		return new com.trs.web2frame.AjaxRequest(_sUrl,_oOptions);
	},
	_DoGet : function(_sUrl,_sServiceId,_sMethod,_oOptions){
		_sUrl = (_sUrl.indexOf('?')!=-1)?(_sUrl+'&'):(_sUrl+'?');
		var sParams = _oOptions["parameters"];
		_sUrl += this._BuildUrl(_sServiceId,_sMethod,sParams);
		_oOptions["parameters"] = '';
		return new com.trs.web2frame.AjaxRequest(_sUrl,_oOptions);
	},
	_DoMultiRequest : function(_sUrl , _arrCombined , _oOptions){
		var myDoc = com.trs.util.XML.loadXML('<post-data></post-data>');
		var element = myDoc.documentElement;
		for(var i=0;i<_arrCombined.length;i++){
			var eleTmp = myDoc.createElement("method");
			eleTmp.setAttribute("type",_arrCombined[i]["methodName"]);
			eleTmp.appendChild(myDoc.createTextNode(_arrCombined[i]["serviceName"]));
			element.appendChild(eleTmp);
			eleTmp = myDoc.createElement("parameters");
			element.appendChild(eleTmp);
			com.trs.util.JSON.parseJson2Element(myDoc,eleTmp,_arrCombined[i]["params"]);
		}
		_oOptions["postBody"] = myDoc;
		return new com.trs.web2frame.AjaxRequest(_sUrl,_oOptions);
	},
	_BindEvents : function(_fSuccess,_f500,_fFailure){
		var _oOptions = {};
		if(_fSuccess)_oOptions['onSuccess'] = _fSuccess;
		if(_f500)_oOptions['on500'] = _f500;
		if(_fFailure)_oOptions['onFailure'] = _fFailure;
		return _oOptions;
	},
	_MakePostData : function(_oPost){
		var postData = null;
		if(typeof _oPost=='function'){
			throw wcm.LANG.AJAX_ALERT_7 || '参数传入错误:_oPost不能为Function类型.';
		}
		if(Ext.isString(_oPost)){
			if($(_oPost)==null){
				throw String.format("参数传入错误:不存在id为{0}的form对象.", _oPost );
			}
			postData = com.trs.web2frame.PostData.form(_oPost);
		}
		else if(Ext.type(_oPost)=='object'){
			postData = com.trs.web2frame.PostData.params(_oPost);
		}
		else if(Ext.type(_oPost)=='element'){
			postData = com.trs.web2frame.PostData.form(_oPost);
		}
		return postData;
	},
	_MakeQueryStr : function(_oPost){
		var postData = null;
		if(typeof _oPost=='function'){
			throw wcm.LANG.AJAX_ALERT_7 || '参数传入错误:_oPost不能为Function类型.';
		}
		if(Ext.isString(_oPost)){
			if($(_oPost)==null){
				throw String.format("参数传入错误:不存在id为{0}的form对象.",_oPost);
			}
			postData = com.trs.web2frame.PostData.form(_oPost);
		}
		else if(Ext.type(_oPost)=='object'){
			return $toQueryStr(_oPost, !_oPost.CaseSensitive);
		}
		else if(Ext.type(_oPost)=='element'){
			postData = com.trs.web2frame.PostData.form(_oPost);
		}
		return com.trs.util.JSON.parseJsonToParams(postData);
	},
	_Request : function(_oDataSource,_oPost,_bSend){
		var sMethod		= _oDataSource["method"];
		if(_bSend!=null&&!Ext.isBoolean(_bSend)){
			throw wcm.LANG.AJAX_ALERT_10 || '参数传入错误:_bSend只能为true,false或null.';
		}
		sMethod = (sMethod)?sMethod.toLowerCase():'call';
		var sUrl = _oDataSource["url"];
		var sServiceId = _oDataSource["serviceId"];
		var oOptions = _oDataSource["options"];
		if(sMethod == '__multicall__'){
			return this._DoMultiRequest(sUrl,_oPost,Ext.apply(oOptions,{method:'post'}));
		}
		if(_bSend && sMethod != '__jsp__'){//'POST'
			_oPost = this._MakePostData(_oPost);
			return this._DoPost(sUrl, sServiceId, sMethod,
				Ext.apply(oOptions,{ method : 'Post', postBody : _oPost }));
		}
		var queryParams = this._MakeQueryStr(_oPost);
		if(sMethod == '__jsp__'){
			Ext.apply(oOptions,{
				parameters : queryParams,
				method : (_bSend)?'POST':'GET',
				contentType : 'application/x-www-form-urlencoded'
			});
			return new com.trs.web2frame.AjaxRequest(sUrl, oOptions);
		}
		Ext.apply(oOptions,{
			parameters : queryParams,
			method : 'GET'
		});
		return this._DoGet(sUrl, sServiceId, sMethod, oOptions);
	},
	LocalCall : function(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		var sUrl = WCMConstants.WCM_LOCAL_URL +_sServiceId+'/'+_sMethodName+'.xml';
		if(!_fFailure){
			_fFailure = function(transport){
				Ext.Msg.$alert("Error "+transport.status+":<br>"+sUrl);
			}
		}
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:_sServiceId,url:sUrl,method:_sMethodName,options:oOptions};
		this._Request(oDataSource,_oPost,_bSend);
	},
	Call : function(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:_sServiceId,url:this.WebService,method:_sMethodName,options:oOptions};
		this._Request(oDataSource,_oPost,_bSend);
	},
	Combine : function(_sServiceId,_sMethodName,_oPost){
		_oPost = this._MakePostData(_oPost);
		return {"serviceName":_sServiceId,"methodName":_sMethodName,"params":_oPost};
	},
	MultiCall : function(_arrCombined,_fSuccess,_f500,_fFailure){
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:'',url:this.WebService,method:'__multicall__',options:oOptions};
		this._Request(oDataSource,_arrCombined);
	},
	JspMultiCall : function(_sUrl,_arrCombined,_fSuccess,_f500,_fFailure){
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:'',url:_sUrl,method:'__multicall__',options:oOptions};
		this._Request(oDataSource,_arrCombined);
	},
	JspRequest : function(_sUrl,_oPost,_bSend,_fSuccess,_f500,_fFailure){
		var oOptions = this._BindEvents(_fSuccess,_f500,_fFailure);
		var oDataSource = {serviceId:'',url:_sUrl,method:'__jsp__',options:oOptions};
		this._Request(oDataSource,_oPost,_bSend);
	}
};
var DataHelper = com.trs.web2frame.DataHelper = function(_sServiceId){
	com.trs.web2frame.DataHelper.superclass.constructor.call(this, _sServiceId);
	if(!_sServiceId){
		this.call = this.Call;
	}
	else{
		var caller = this;
		this.call = function(_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure){
			caller.Call(_sServiceId,_sMethodName,_oPost,_bSend,_fSuccess,_f500,_fFailure);
		}
		this.save = function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
			caller.call('save',_oPost,_bSend,_fSuccess,_f500,_fFailure);
		}
		this.findByIds = function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
			caller.call('findbyids',_oPost,_bSend,_fSuccess,_f500,_fFailure);
		}
		this.get = function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
			caller.call('get',_oPost,_bSend,_fSuccess,_f500,_fFailure);
		}
		this.remove = this._delete = this["delete"] =
			function(_oPost,_bSend,_fSuccess,_f500,_fFailure){
			caller.call('delete',_oPost,_bSend,_fSuccess,_f500,_fFailure);
		}
	}
	this.combine = this.Combine;
	this.multiCall = this.MultiCall;
	this.jspMultiCall = this.JspMultiCall;
};
Ext.extend(com.trs.web2frame.DataHelper, com.trs.web2frame.BasicDataHelper);

var BasicDataHelper = new com.trs.web2frame.BasicDataHelper();

var $render500Err = window.DefaultAjax500CallBack = function(_trans, _json, _bIsJson, _fClose){
	try{
		if(window.ProcessBar != null) 
			ProcessBar.close();
	}catch (ex){
		//just skip it
	}
	try{
		var elDiv = $('daton_loading');
		if (elDiv){
			Element.remove(elDiv);
		}
		var sDefaultMsg = wcm.LANG.AJAX_ALERT_11 || '与服务器交互时出现错误！';
		if(_bIsJson === true) {
			Ext.Msg.fault({
				code		: _json.code,
				message		: _json.message || sDefaultMsg,
				detail		: _json.detail || sDefaultMsg,
				suggestion  : _json.suggestion 
			}, wcm.LANG.AJAX_ALERT_11 || '与服务器交互时出现错误', _fClose);
		}else if(_json){
			var json= _json;
			if(window._dealWith500){
				window._dealWith500($v(json,'fault.message'), _trans, _json);
			}
			else{
				Ext.Msg.fault({
					code		: $v(json,'fault.code'),
					message		: $v(json,'fault.message') || sDefaultMsg,
					detail		: $v(json,'fault.detail') || sDefaultMsg,
					suggestion  : $v(json,'fault.suggestion')
				}, wcm.LANG.AJAX_ALERT_11 || '与服务器交互时出现错误', _fClose);
			}
		}else{
			if(window._dealWith500){
				window._dealWith500(_trans.responseText, _trans, _json);
			}
			else{
				Ext.Msg.$alert(_trans.responseText);
			}
		}

	}catch (ex){
		if(Ext.isDebug()){
			throw ex;
		}
		//alert(ex.description);
		try{
			Ext.Msg.$alert((wcm.LANG.AJAX_ALERT_12 || '与服务器交互时发生了以下异常：\n') + _trans.responseText.stripScripts());
		}catch (ex){
			//alert(ex.description);
		}
	}
}
window.DefaultAjaxFailureCallBack = function(_oTransport,_sJson){
	try{
		if(window.ProcessBar) 
			ProcessBar.close();
	}catch (ex){
		//just skip it
	}
	try{
		var elDiv = $('daton_loading');
		if (elDiv){
			Element.remove(elDiv);
		}
		if(window._dealWithFailure){
			window._dealWithFailure($v(_sJson,'fault.message'),_oTransport,_sJson);
		}else{
			Ext.Msg.fault(_sJson);
		}
	}catch(ex){
		if(Ext.isDebug())throw ex;
		Ext.Msg.d$error(ex);
	}
}
window.DefaultAjaxException = function(_ajaxRequest, _err){
	var sMessage = Ext.errorMsg(_err);
	try{
		var transport = _ajaxRequest.transport;
		sMessage = 'url:' + _ajaxRequest.url + '\nresponse:\n' + transport.responseText 
			+ "-----------------------------\n" + sMessage;
	}catch(err){
	}
	if(Ext.isDebug())throw _err;
	Ext.Msg.d$alert(sMessage);
}
window.DefaultNotLogin = window.DoNotLogin = function(){
	//alert('not login...');
	try{
		var actualTop = $MsgCenter ? $MsgCenter.getActualTop() : top;
		actualTop.location.href = WCMConstants.WCM_NOT_LOGIN_PAGE + 
			'?FromUrl=' + encodeURIComponent(actualTop.location.href);
	}catch(err){
		alert(wcm.LANG.AJAX_ALERT_3 || '您已退出系统，请重新登录');
	}
	return false;
}
window.NotifySystemError = function(){
	alert(wcm.LANG.AJAX_ALERT_3 || '与服务器交互失败，服务器已经停止或者您的网络出现故障！');
}