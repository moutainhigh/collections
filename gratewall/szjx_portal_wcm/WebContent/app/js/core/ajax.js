Ext.ns("Ajax.Request");
function getTransport(){
	return Try(
		function() {return new XMLHttpRequest()},
		function() {return new ActiveXObject('Msxml2.XMLHTTP')},
		function() {return new ActiveXObject('Microsoft.XMLHTTP')}
	) || false;
}
function emptyFn(){}
function ajaxRequest(fo){
	var transport = getTransport();
	var bSynSend = fo.asyn==false;
	var url = fo.url;
	var method = (fo.method || 'GET').toLowerCase();
	var parameters = fo.parameters || '';
	if (method == 'get' && parameters.length > 0)
		url += (url.match(/\?/) ? '&' : '?') + parameters;
	transport.open(method, url, !bSynSend);
	if(!bSynSend){
		var caller = this;
		var callback = function(){
			var readyState = transport.readyState;
			if (readyState != 4) return;
			if(transport.status==12029 || transport.status==12007){
				(fo.onFailure||window.NotifySystemError||emptyFn)(transport, caller);
				transport.onreadystatechange = emptyFn;
				return;
			}
			var isNotLogin = caller.header('TRSNotLogin');
			if(isNotLogin=='true'){
				if(!window.DefaultNotLogin){
					try{
						var actualTop = $MsgCenter ? $MsgCenter.getActualTop() : top;
						actualTop.location.href = WCMConstants.WCM_NOT_LOGIN_PAGE + 
							'?FromUrl=' + encodeURIComponent(actualTop.location.href);
					}catch(err){
						alert(wcm.LANG.AJAX_ALERT_3 || '您未登录或者被踢出，请重新访问登录页登陆。');
					}
				}else{
					window.DefaultNotLogin();
				}
				return;
			}
			var isError = caller.header('TRSException');
			if(caller.responseIsSuccess() && isError!='true'){
				if(fo.onSuccess)fo.onSuccess(transport, caller);
			}
			else{
				if (isError=='true'||transport.status==500){
					(fo.on500||window.ajax500||emptyFn)(transport, caller);
				}
				(fo.onFailure||window.ajaxFail||emptyFn)(transport, caller);
			}
			if(fo.onComplete)fo.onComplete(transport, caller);
			try{
				transport.onreadystatechange = emptyFn;
			}catch(err){}
		}
		transport.onreadystatechange = callback;
	}
	var requestHeaders = ['X-Requested-With', 'XMLHttpRequest'];
	if (method == 'post') {
		requestHeaders.push('Content-type', fo.contentType||'application/x-www-form-urlencoded');
		if (transport.overrideMimeType)
			requestHeaders.push('Connection', 'close');
	}
	for (var i = 0; i < requestHeaders.length; i += 2){
		transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
	}
	this.header = function(name) {
		return this.transport.getResponseHeader(name);
	}
	this.transport = transport;
	this.fo = fo;
	this.responseIsSuccess = function() {
		var status = this.transport.status;
		return status == undefined
		|| status == 0
		|| (status >= 200 && status < 300);
	}
	this.responseIsFailure = function() {
		return !this.responseIsSuccess();
	}
	transport.send(method=='post'?(fo.postBody||parameters):null);
}
Ajax.Request = function(url, opts){
	var asyn = opts['asynchronous'];
	ajaxRequest.call(this, Ext.apply({url:url, asyn:asyn==null?true:false}, opts));
}