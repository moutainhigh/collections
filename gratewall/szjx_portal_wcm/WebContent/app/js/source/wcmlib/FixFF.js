//兼容ff的部分方法
(function(){
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1) return;
	Event.prototype.__defineGetter__("fromElement",function(){
		var dom;
		if(this.type=="mouseover")
			dom=this.relatedTarget;
		else if(this.type=="mouseout")
			dom=this.target;
		if(!dom)return;
		while(dom.nodeType!=1)dom=dom.parentNode;
		return dom;
	});
	Event.prototype.__defineGetter__("toElement",function(){
		var dom;
		if(this.type=="mouseout")
			dom=this.relatedTarget;
		else if(this.type=="mouseover")
			dom=this.target;
		if(!dom)return;
		while(dom.nodeType!=1)dom=dom.parentNode;
		return dom;
	});
	HTMLElement.prototype.contains = function(dom){
		while(dom){
			if(dom == this) return true;
			dom = dom.parentNode;
		}
		return false;
	};
	HTMLElement.prototype.__defineGetter__("innerText",function(){
		var text=null;
		text = this.ownerDocument.createRange();
		text.selectNodeContents(this);
		text = text.toString();
		return text;
	});
	HTMLElement.prototype.__defineSetter__("innerText", function (sText) {
		this.innerHTML = sText.replace(/\&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\n/g,'<br>');
	});
	var _emptyTags = {
	   "IMG":   true,
	   "BR":    true,
	   "INPUT": true,
	   "META":  true,
	   "LINK":  true,
	   "PARAM": true,
	   "HR":    true
	};
	HTMLElement.prototype.__defineGetter__("outerHTML", function () {
	   var attrs = this.attributes;
	   var str = "<" + this.tagName;
	   for (var i = 0; i < attrs.length; i++)
	      str += " " + attrs[i].name + "=\"" + attrs[i].value + "\"";
	
	   if (_emptyTags[this.tagName])
	      return str + "/>";
	
	   return str + ">" + this.innerHTML + "</" + this.tagName + ">";
	});
	HTMLElement.prototype.__defineSetter__("outerHTML", function (sHTML) {
	   var r = this.ownerDocument.createRange();
	   r.setStartBefore(this);
	   var df = r.createContextualFragment(sHTML);
	   this.parentNode.replaceChild(df, this);
	});
	HTMLElement.prototype.__defineGetter__("outerText", function () {
	   var r = this.ownerDocument.createRange();
	   r.selectNodeContents(this);
	   return r.toString();
	});
	HTMLElement.prototype.__defineSetter__("outerText", function (sText) {
	   this.outerHTML = sText.replace(/\&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\n/g,'<br>');
	});
	HTMLElement.prototype.__defineGetter__("uniqueID", function(){
		if(!arguments.callee.count)arguments.callee.count=0;
		var u = "moz_id" + arguments.callee.count++;
		this.__defineGetter__("uniqueID",function(){return u});
		return u;
	});
})();

//if(!Ext.isIE){//fix ie9, because ie9 like forefox
if(window.HTMLElement){// && !HTMLElement.prototype.fireEvent){
	HTMLElement.prototype.fireEvent = function(sType){
		sType = sType.replace(/^on/, "");
		var evtObj = document.createEvent('MouseEvents');     
		evtObj.initMouseEvent(sType, true, true, document.defaultView, 1, 0, 0, 0, 0, false, false, true, false,   0, null);     
		this.dispatchEvent(evtObj);
	};
}
