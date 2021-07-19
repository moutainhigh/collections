$import('com.trs.dialog.Dialog');
var $render500Err = window.DefaultAjax500CallBack = function(_trans, _json, _bIsJson, _fClose){
	try{
		var elDiv=$('daton_loading');
		if (elDiv){
			Element.remove(elDiv);
		}
		
		var sDefaultMsg = '与服务器交互时出现错误！';
		if(_bIsJson === true) {
			FaultDialog.show({
				code		: _json.code,
				message		: _json.message || sDefaultMsg,
				detail		: _json.detail || sDefaultMsg,
				suggestion  : _json.suggestion 
			}, '与服务器交互时出现错误', _fClose);
		}else if(_json){
			var json= _json;//com.trs.util.JSON.parseXml(_trans.responseXML);
			var getNodeVal = com.trs.util.JSON.value;
			if(window._dealWith500){
				window._dealWith500(getNodeVal(json,'fault.message'),_trans,json);
			}
			else{
				FaultDialog.show({
					code		: getNodeVal(json,'fault.code'),
					message		: getNodeVal(json,'fault.message') || sDefaultMsg,
					detail		: getNodeVal(json,'fault.detail') || sDefaultMsg,
					suggestion  : getNodeVal(json,'fault.suggestion')
				}, '与服务器交互时出现错误', _fClose);
			}
		}else{
			if(window._dealWith500){
				window._dealWith500(_trans.responseText,_trans,json);
			}
			else{
				$alert(_trans.responseText);
			}
		}

		try{
			if(window.ProcessBar != null) 
				ProcessBar.close();
		}catch (ex){
			//just skip it
		}
	}catch (ex){
		//alert(ex.description);
		try{
			$alert('与服务器交互时发生了以下异常：\n' + _trans.responseText.stripScripts());
		}catch (ex){
			//alert(ex.description);
		}
	}
}
window.DefaultAjaxFailureCallBack = function(_oTransport,_sJson){
	try{
		var elDiv=$('daton_loading');
		if (elDiv){
			Element.remove(elDiv);
		}
		if(window._dealWithFailure){
			window._dealWithFailure(getNodeVal(json,'fault.message'),_trans,json);
		}
		else{
			$errorMsg('&nbsp; 与[' + com.trs.portal.ServiceExt.URL + ']交互失败了！');	
		}
		try{
			if(window.ProcessBar) 
				ProcessBar.close();
		}catch (ex){
			//just skip it
		}
	}catch(ex){
		//alert(ex.description);
	}
}
window.DefaultAjaxException = function(_ajaxRequest,_err){
	var sMessage = getErrorStack(_err);
	try{
		var transport = _ajaxRequest.transport;
		sMessage += 'ResponseText:------------\n'+transport.responseText;
	}catch(err){
	}
	//TODO logger
	//alert(sMessage);
}
window.DefaultNotLogin = window.DoNotLogin = function(){
	//alert('not login...');
	(top.actualTop || top).location.href = '/wcm/console/include/not_login.htm';
	return false;
}

Object.extend(Form.Element.Serializers, {
	inputSelector: function(element) {
		var sIsBoolean = $GS(element, 'isboolean');
		if (sIsBoolean != null && 
			( (sIsBoolean = sIsBoolean.trim().toLowerCase()) == '1' || sIsBoolean == 'true' )){
			return [element.name, element.checked ? '1' : '0'];
		}

		if (element.checked)
			return [element.name, element.value];
	},
	textarea: function(element) {
		var sIsTrans2Html = $GS(element, 'transHtml');
		if (sIsTrans2Html != null &&
			( (sIsTrans2Html = sIsTrans2Html.trim().toLowerCase()) == '1' || sIsTrans2Html == 'true' )){
			return [element.name, $transHtml(element.value)];
		}
		return [element.name, element.value];
	}
});

Object.extend(Ajax.Request.prototype, {
	setRequestHeaders: function() {
    var requestHeaders =
      ['X-Requested-With', 'XMLHttpRequest',
       'X-Prototype-Version', Prototype.Version];

    if (this.options.method == 'post') {
      requestHeaders.push('Content-type',
        this.options.contentType||'multipart/form-data');
//      requestHeaders.push('Content-type',
//        'application/x-www-form-urlencoded');

      /* Force "Connection: close" for Mozilla browsers to work around
       * a bug where XMLHttpReqeuest sends an incorrect Content-length
       * header. See Mozilla Bugzilla #246651.
       */
      if (this.transport.overrideMimeType)
        requestHeaders.push('Connection', 'close');
    }

    if (this.options.requestHeaders)
      requestHeaders.push.apply(requestHeaders, this.options.requestHeaders);

    for (var i = 0; i < requestHeaders.length; i += 2)
      this.transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
  }
});