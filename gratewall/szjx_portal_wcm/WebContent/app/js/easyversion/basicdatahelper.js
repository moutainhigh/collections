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
if(!window.$toQueryStr){
	var $toQueryStr = function(_oParams, _bUpper){
		var arrParams = _oParams || {};
		var arrParamsSAparts = [];
		for (var param in arrParams){
			var value = arrParams[param];
			if(typeof value=='function' || typeof value=='object')continue;
			param = _bUpper ? param.trim().toUpperCase() : param.trim();
			arrParamsSAparts.push(param + '=' + encodeURIComponent(value + ''));
		}
		return arrParamsSAparts.join('&');
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
