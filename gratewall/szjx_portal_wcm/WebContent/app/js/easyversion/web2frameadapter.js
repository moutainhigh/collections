Ext.ns('com.trs.web2frame.BasicDataHelper', 'com.trs.web2frame.PostData', 'Form');
var easyBDH = BasicDataHelper;
var myBDH = Ext.apply({}, BasicDataHelper);
Ext.apply(myBDH, {
	_json : function(trans, caller) {
		if(caller.header('ReturnJson') == 'true')
			return Ext.Json.eval(trans.responseText);
		var cnType = (caller.header('Content-type') || '');
		if (cnType.match(/^text\/javascript/i)){
			eval(trans.responseText);
			return null;
		}
		if(cnType.indexOf('/html')!=-1)return null;
		try{
			if(trans.responseXML)
				return parseXml(trans.responseXML);
			if(cnType.indexOf('/xml')!=-1)
				return parseXml(loadXml(trans.responseText));
		}catch(err){
			//Just skip it.
		}
		return null;
	},
	_jsonUp : function(trans, caller) {
		return Ext.Json.toUpperCase(myBDH._json(trans, caller));
	},
	_ajaxEvents : function(fSuc, f500, fFail){
		return {
			onSuccess : fSuc ? function(trans, caller){
				try{
					fSuc.toString();
				}catch(error){
					return;//页面已经销毁，所以此函数不需要执行
				}
				fSuc(trans, myBDH._jsonUp(trans, caller), caller);
			} : null,
			on500 : f500 ? function(trans, caller){
				f500(trans, myBDH._jsonUp(trans, caller), caller);
			} : null,
			onFailure : fFail ? function(trans, caller){
				fFail(trans, myBDH._jsonUp(trans, caller), caller);
			} : null
		};
	},
	_makeData : function(data){
		var type = Ext.type(data);
		if(type!='element' && type!='string')return Ext.Json.toUpperCase(data);
		return com.trs.web2frame.PostData.form(data, function(k){return k;});
	},
	Call : function(s, m, data, bPost, fSuc, f500, fFail){
		easyBDH.Call({
			serviceId : s,
			methodName : m,
			data : myBDH._makeData(data),
			post : bPost,
			ajax : this._ajaxEvents(fSuc, f500, fFail)
		});
	},
	Combine : function(s, m, data){
		return {s:s, m:m, data:myBDH._makeData(data)};
	},
	MultiCall : function(arr, fSuc, f500, fFail){
		easyBDH.MultiCall({
			data : arr,
			ajax : this._ajaxEvents(fSuc, f500, fFail)
		});
	},
	JspMultiCall : function(url, arr, fSuc, f500, fFail){
		easyBDH.JspMultiCall({
			url : url,
			data : arr,
			ajax : this._ajaxEvents(fSuc, f500, fFail)
		});
	},
	JspRequest : function(url, data, bPost, fSuc, f500, fFail){
		easyBDH.JspRequest({
			url : url,
			data : myBDH._makeData(data),
			post : bPost,
			ajax : this._ajaxEvents(fSuc, f500, fFail)
		});
	}
});
com.trs.web2frame.BasicDataHelper = function(){
	this.WebService = WCMConstants.WCM_ROMOTE_URL || '/wcm/center.do';
	this.call = this.Call;
	this.combine = this.Combine;
	this.multiCall = this.MultiCall;
}
com.trs.web2frame.BasicDataHelper.prototype = myBDH;
BasicDataHelper = new com.trs.web2frame.BasicDataHelper();

Form.getElements = function(form){
	form = $(form);
	var rst = [], tags = ['input', 'select', 'textarea'], arr;
	for (var i=0;i<tags.length;i++) {
		arr = form.getElementsByTagName(tags[i]);
		for (var j = 0; j < arr.length; j++){
			rst.push(arr[j]);
		}
	}
	return rst;
}
com.trs.web2frame.PostData = {
	_elements : function(els, bCase, render){
		var lastItamName = "";
		var vData = {}, attrs = [], rst = {}, el, v;
		for(var i=0; i<els.length; i++){
			el = els[i];
			if(!el.name || el.getAttribute("ignore"))continue;
			v = $F(el);
			if(lastItamName != els[i].getAttribute("Name"))
				lastItamName = els[i].getAttribute("Name");
			else{
				if(v==null) continue;
			}
			//扩展字段默认为null值的没法被更新导致版本恢复时的报错
			if(v==null)v = "";
			var name = bCase?el.name:el.name.toUpperCase();
			if(el.getAttribute("isAttr", 2)){
				attrs.push(name + '=' + v);
				continue;
			}
			var arr = vData[name];
			if(!arr){
				arr = vData[name] = [v];
			}
			else{
				arr.push(v);
			}
		}
		if(attrs.length>0)rst.ATTRIBUTE = {NODEVALUE : attrs.join('&')};
		for(var name in vData){
			var v = $compact4Array(vData[name], "").join(",");
			rst[name] = render ? render(v) : {NODEVALUE : v};
		}
		return rst;
	},
	form : function(frmId, render) {
		var elFrm = $(frmId);
		var els = Form.getElements(frmId);
		var bCase = (elFrm.CaseSensitive)?
				(elFrm.CaseSensitive.value=='true'||elFrm.CaseSensitive.value=='1') : false;
		return this._elements(els, bCase, render);
	}
};
function $compact4Array(arr, sCompactChar){
	var rst = [];
	for (var i = 0, n = arr.length; i < n; i++){
		if(arr[i]!=sCompactChar)rst.push(arr[i]);
	}
	return rst;
}
function ajax500(trans, caller){
	var json = myBDH._json(trans, caller);
	$render500Err(trans, json);
}
function $render500Err(trans, json, isJson, fclose){
	try{
		if(window.ProcessBar != null)ProcessBar.close();
	}catch (ex){
	}
	var msg = wcm.LANG.AJAX_ALERT_2 || '与服务器交互时出现错误！';
	if(json) {
		if(isJson !== true)
			json = json.FAULT;
		Ext.Msg.fault({
			code		: $v(json, 'code', isJson),
			message		: $v(json, 'message', isJson) || msg,
			detail		: $v(json, 'detail', isJson) || msg,
			suggestion  : $v(json, 'suggestion', isJson)
		}, wcm.LANG.AJAX_ALERT_2 || '与服务器交互时出现错误', fclose);
	}else{
		Ext.Msg.$alert(trans.responseText);
	}
}
function ajaxFailure(trans){
	try{
		if(window.ProcessBar)ProcessBar.close();
	}catch (ex){
	}
	Ext.Msg.$alert(trans.responseText);
}
window.DefaultNotLogin = window.DoNotLogin = function(){
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
	alert(wcm.LANG.AJAX_ALERT_4 || '与服务器交互失败，服务器已经停止或者您的网络出现故障！');
}