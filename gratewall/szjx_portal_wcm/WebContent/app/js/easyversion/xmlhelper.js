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
function loadXml(str){
	var doc = Try(
	  function() {return new ActiveXObject('Microsoft.XMLDOM');},
	  function() {return document.implementation.createDocument("","",null);}
	);
	doc.loadXML(str);
	return doc;
}
var XmlHelper = {
	_xvalue : function(xNode) {
		if(!xNode)return null;
		var v = xNode.nodeValue;
		if(v!= null)return v;
		var s = xNode.childNodes, nm;
		if(s.length == 0)return "";
		var rst = null, h = false;
		for(var i=0; i<s.length; i++) {
			nm = s[i].nodeName;
			if(nm == "#text" && s[i].nodeValue)
				return s[i].nodeValue;
			if(nm == "#text") rst = "";
			if(nm == "#cdata-section")
				return s[i].nodeValue || '';
			h = true;
		}
		return h ? null : rst;
	},
	_xchild : function(xNode, nm){
		var s = xNode.childNodes;
		for(var j=0,n=s.length; j<n; j++) {
			if(s[j].nodeName == nm)return s[j];
		}
	},
	queryNodes : function(xNode, xp){
		if(!xNode || !xp)return [];
		var cxp = this.xpath(xNode);
		var len = cxp.length;
		if(len>xp.length)return [];
		var rst = [];
		xp = xp.substring(len==0?0:len+1);
		var arr = xp.split("/");
		var t = xNode;
		for(var i=0; i<arr.length-1; i++) {
			t = this._xchild(t, arr[i]);
			if(t==null)break;
		}
		if(t == null)
			return rst;
		var s = t.childNodes;
		for(var i=0,n=s.length; i<n; i++) {
			if(s[i].nodeName == arr[arr.length-1]) {
				rst.push(s[i]);
			}
		}
		return rst;
	},
	xpath : function(xNode) {
		var rst = [];
		var t = xNode;
		while(t) {
			if(!t.nodeName || !t.parentNode || t.parentNode.nodeType == 9)break;
			rst.push(t.nodeName);
			t = t.parentNode;
		}
		return rst.join('/');
	}
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
		if(!name)continue;
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
