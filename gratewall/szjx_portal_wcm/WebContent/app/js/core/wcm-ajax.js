//数据提交助手
if ( window.DOMParser &&
	  window.XMLSerializer &&
	  window.Node && Node.prototype && Node.prototype.__defineGetter__ ) {
   if (!Document.prototype.loadXML) {
      Document.prototype.loadXML = function (s) {
         var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
         while (this.hasChildNodes())
            this.removeChild(this.lastChild);
         for (var i = 0; i < doc2.childNodes.length; i++) {
            this.appendChild(this.importNode(doc2.childNodes[i], true));
         }
      };
	}
	Document.prototype.__defineGetter__( "xml", function () {
	   return (new XMLSerializer()).serializeToString(this);
	});
}
function buildXml(s, m, data){
	var doc = loadXml(String.format('<post-data><method type="{0}">{1}</method>'+
						'<parameters></parameters></post-data>', m, s));
	var pa = doc.getElementsByTagName("parameters")[0];
	jsonIntoEle(doc, pa, data, true);
	return doc;
}
function loadXml(str){
	var doc = Try(
	  function() {return new ActiveXObject('Microsoft.XMLDOM');},
	  function() {return document.implementation.createDocument("","",null);}
	);
	doc.loadXML(str);
	return doc;
}
function xmlTextNode(xmlDoc, value){
	if(value.match(/<!\[CDATA\[|\]\]>/img))
		return xmlDoc.createTextNode(value);
	return xmlDoc.createCDATASection(value);
}
function jsonIntoEle(xmlDoc, parent, json, bAlwaysNode){
	if(json==null || typeof json!='object')return;
	if(Array.isArray(json)){
		var arr = [];
		for(var i=0,n=json.length;i<n;i++){
			var value = json[i];
			if(value==null)continue;
			if(typeof value!='object'){
				arr.push(value);
				continue;
			}
			var newEle = parent;
			if(i!=0){
				newEle = xmlDoc.createElement(parent.nodeName);
				parent.parentNode.appendChild(newEle);
			}
			jsonIntoEle(xmlDoc, newEle, value);
			continue;
		}
		if(arr.length>0){
			parent.appendChild(xmlTextNode(xmlDoc, arr.join()));
		}
		return;
	}
	for(var name in json){
		if(!name) continue;
		var value = json[name];
		if(value==null)continue;
		if(typeof value=='object'){
			var newEle = xmlDoc.createElement(name);
			parent.appendChild(newEle);
			jsonIntoEle(xmlDoc, newEle, value);
			continue;
		}
		value = '' + value;
		if(!bAlwaysNode && name.toUpperCase()!='NODEVALUE'){
			parent.setAttribute(name, value);
			continue;
		}
		var elChild = xmlTextNode(xmlDoc, value);
		if(name.toUpperCase()=='NODEVALUE'){
			parent.appendChild(elChild);
			continue;
		}
		var newEle = xmlDoc.createElement(name);
		parent.appendChild(newEle);
		newEle.appendChild(elChild);
	}
}
var m_remoteUrl = window.WCMConstants ? WCMConstants.WCM_ROMOTE_URL : '/wcm/center.do';
var BasicDataHelper = {
	_DoPost : function(_sUrl, s, m, fo, ajax){
		ajax.postBody = buildXml(s, m, fo);
		ajax.url = _sUrl;
		ajax.method = 'post';
		ajax.contentType = 'multipart/form-data';
		return new ajaxRequest(ajax);
	},
	_DoGet : function(_sUrl, s, m, fo, ajax){
		var c = (_sUrl.indexOf('?')!=-1)?'&':'?';
		ajax.url = _sUrl + c + 'serviceid=' + s + '&methodname=' + m;
		ajax.parameters = $toQueryStr(fo);
		return new ajaxRequest(ajax);
	},
	_DoMultiPost : function(_sUrl, infos, ajax){
		var doc = loadXml('<post-data></post-data>');
		var root = doc.documentElement;
		for(var i=0;i<infos.length;i++){
			var fo = infos[i];
			var mel = doc.createElement("method");
			root.appendChild(mel);
			mel.setAttribute("type", fo.m);
			mel.appendChild(doc.createTextNode(fo.s));
			var pel = doc.createElement("parameters");
			root.appendChild(pel);
			jsonIntoEle(doc, pel, fo.data, true);
		}
		ajax.postBody = doc;
		ajax.url = _sUrl;
		ajax.method = 'post';
		ajax.contentType = 'multipart/form-data';
		return new ajaxRequest(ajax);
	},
	Call : function(fo){
		return this[fo.post?'_DoPost':'_DoGet'](m_remoteUrl,
			fo.serviceId, fo.methodName, fo.data, fo.ajax || {});
	},
	MultiCall : function(fo){
		return this._DoMultiPost(m_remoteUrl,
			fo.data, fo.ajax || {});
	},
	Combine : function(s, m, data){
		return {s:s, m:m, data:data};
	},
	JspMultiCall : function(fo){
		return this._DoMultiPost(fo.url,
			fo.data, fo.ajax || {});
	},
	JspRequest : function(fo){
		var ajax = fo.ajax;
		ajax.url = ajax.url || fo.url;
		var method = (ajax.method || 'GET').toLowerCase();
		var post = fo.post || method == 'post';
		ajax.method = post ? 'post' : 'get';
		if(fo.data){
			ajax.parameters = $toQueryStr(fo.data);
		}
		return new ajaxRequest(ajax);
	}
};
Ext.ns('com.trs.web2frame.BasicDataHelper', 'com.trs.web2frame.PostData', 'Form', 'wcm.LANG');
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
	this.WebService = window.WCMConstants ? WCMConstants.WCM_ROMOTE_URL : '/wcm/center.do';
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
		var vData = {}, attrs = [], rst = {}, el, v;
		for(var i=0; i<els.length; i++){
			el = els[i];
			if(!el.name || el.getAttribute("ignore"))continue;
			v = $F(el);
			if(v==null)continue;
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
		if(attrs.length>0)rst.ATTRIBUTE = attrs.join('&');
		for(var name in vData){
			var v = vData[name].join(",");
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
	try{
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
	}catch(ex){
		alert(trans.responseText);
	}
}
function ajaxFailure(trans){
	try{
		if(window.ProcessBar)ProcessBar.close();
	}catch (ex){
	}
	try{
		Ext.Msg.$alert(trans.responseText);
	}catch (ex){
		alert(trans.responseText);
	}
}
window.DefaultNotLogin = window.DoNotLogin = function(){
	try{
		var actualTop = $MsgCenter ? $MsgCenter.getActualTop() : top;
		actualTop.location.href = WCMConstants.WCM_NOT_LOGIN_PAGE + 
			'?FromUrl=' + encodeURIComponent(actualTop.location.href);
	}catch(err){
		alert(wcm.LANG.AJAX_ALERT_3 || '您未登录或者被踢出，请重新访问登录页登陆。');
	}
	return false;
}
window.NotifySystemError = function(){
	alert(wcm.LANG.AJAX_ALERT_4 || '与服务器交互失败，服务器已经停止或者您的网络出现故障！');
}
//JSON转换
Ext.ns('Ext.Json', 'com.trs.util.JSON');
function parseXml(xml){
	var root = xml.documentElement;
	if(root == null) return null;
	var vReturn = {}, json = parseElement(root);
	vReturn[root.nodeName.toUpperCase()] = json;
	return vReturn;
}
function parseElement(ele){
	if(ele == null)return null;
	var json = {}, attrs = ele.attributes, hasAttr = false, hasValue = false, hasNode = false;
	for(var i=0;attrs && i<attrs.length;i++){
		hasAttr = true;
		json[attrs[i].nodeName.toUpperCase()] = attrs[i].nodeValue.trim();
	}
	var childs = ele.childNodes;
	for(var i=0;childs&&i<childs.length;i++){
		var ndn = childs[i].nodeName.toUpperCase();
		switch(ndn){
			case '#TEXT':
			case '#CDATA-SECTION':
				hasValue = true;
				json.NODEVALUE = childs[i].nodeValue.trim();
				break;
			case '#COMMENT':
				break;
			default:
				hasNode = true;
				var a = json[ndn], b = parseElement(childs[i]);
				if(!a)json[ndn] = b;
				else if(Array.isArray(a))a.push(b);
				else json[ndn] = [a, b];
		}
	}
	if(!hasAttr && !hasNode){
		if(!hasValue)return '';
		return json.NODEVALUE;
	}
	return json;
}
Ext.Json = com.trs.util.JSON = {
	toUpperCase : function(o){
		if(Ext.isEmpty(o) || Ext.isFunction(o))return "";
		if(Ext.isSimpType(o))return o;
		var fn = Ext.Json.toUpperCase, rst = {};
		if(Ext.isArray(o)) {
			rst = [];
			for(var i=0,n=o.length; i<n; i++){
				rst.push(fn(o[i]));
			}
			return rst;
		}
		for(var name in o){
			rst[name.toUpperCase()] = fn(o[name]);
		}
		return rst;
	},
	_json : function(json, xp, bCase){
		var rst = json, arrXp;
		if(!xp)return rst;
		try{
			xp = bCase ? xp.trim() : xp.trim().toUpperCase();
			arrXp = xp.split('.');
			for(var i=0; i<arrXp.length; i++){
				rst = rst[arrXp[i]];
			}
		}catch(err){
			return null;
		}
		return rst;
	},
	value : function(json, xp, bCase){
		var rst = Ext.Json._json(json, xp, bCase);
		return rst==null ? null : (rst['NODEVALUE'] || rst);
	},
	array : function(json, xp, bCase){
		var rst = Ext.Json._json(json, xp, bCase);
		return !rst ? [] : (Ext.isArray(rst) ? rst : [rst]);
	},
	eval : function(_sJson){
		try{
			eval("var json = " + _sJson);
			return Ext.Json.toUpperCase(json);
		}catch(err){
			Ext.Msg.d$error(err);
		}
	},
	parseXml : function(xml){
		return parseXml(xml);
	}
};
var $v = Ext.Json.value, $a = Ext.Json.array;