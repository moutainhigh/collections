//控件位置、大小等非常用方法
var camelRe = /(-[a-z])/gi, propCache = {};
var camelFn = function(m, a){ return a.charAt(1).toUpperCase(); };
function getStyle(el, prop){
	var view = document.defaultView;
	var v, cs, camel;
	if(view && view.getComputedStyle){
		if(prop == 'float')prop = "cssFloat";
		if(v = el.style[prop])return v;
		if(cs = view.getComputedStyle(el, "")){
			if(!(camel = propCache[prop])){
				camel = propCache[prop] = prop.replace(camelRe, camelFn);
			}
			return cs[camel];
		}
		return null;
	}
	if(prop == 'float')prop = "styleFloat";
	if(!(camel = propCache[prop])){
		camel = propCache[prop] = prop.replace(camelRe, camelFn);
	}
	if(v = el.style[camel])return v;
	if(cs = el.currentStyle)return cs[camel];
	return null;
}
Ext.applyIf(Element, {
	getStyle : function(el, prop){
		return getStyle(el, prop);
	},
	getDimensions: function(el) {
		el = $(el);
		if (Element.getStyle(el, 'display') != 'none')
		  return {width: el.offsetWidth, height: el.offsetHeight};
		var els = el.style;
		var ovi = els.visibility;
		var ops = els.position;
		els.visibility = 'hidden';
		els.position = 'absolute';
		els.display = '';
		var owd = el.clientWidth;
		var ohg = el.clientHeight;
		els.display = 'none';
		els.position = ops;
		els.visibility = ovi;
		return {width: owd, height: ohg};
	},
	next : function(dom){
		if(dom == null) return null;
		var node = dom.nextSibling;
		while(node && node.nodeType != 1){
			node = node.nextSibling;
		}
		return ((node == null)||(node.parentNode != dom.parentNode)) ? null : node;
	},
	previous : function(dom){
		if(!dom) return null;
		var dom = dom.previousSibling;
		while(dom && dom.nodeType != 1){
			dom = dom.previousSibling;
		}
		return dom;
	},
	first : function(dom){
		if(!dom) return null;
		var nodes = dom.childNodes;
		for (var i = 0, len = nodes.length; i < len; i++){
			if(nodes[i].nodeType == 1)return nodes[i];
		}
		return null;
	},
	last : function(dom){
		if(!dom) return null;
		var nodes = dom.childNodes;
		for (var i = nodes.length-1; i >= 0; i--){
			if(nodes[i].nodeType == 1)return nodes[i];
		}
		return null;
	},
	remove : function(dom){
		Ext.removeNode(dom);
	}
});
function insertion(_el, _content, type){
    var oEle = $(_el), content = _content.stripScripts();
	function app(arr){
		var el = oEle, here = oEle, fn = 'insertBefore';
		if(type=='beforeBegin'){
			el = oEle.parentNode;
		}else if(type=='afterBegin'){
			here = oEle.firstChild;
		}else if(type=='beforeEnd'){
			fn = 'appendChild';
			here = null;
		}else if(type=='afterEnd'){
			el = oEle.parentNode;
			here = oEle.nextSibling;
		}
		for(var i=arr.length-1;i>=0;i--){
			el[fn](arr[i], here);
		}
	}
    if (oEle.insertAdjacentHTML) {
      try {
        oEle.insertAdjacentHTML(type, content);
      } catch (e) {
        if (oEle.tagName != 'TBODY')throw e;
		var div = document.createElement('div');
		div.innerHTML = '<table><tbody>' + content + '</tbody></table>';
		var arr = $A(div.childNodes[0].childNodes[0].childNodes);
		app(arr);
      }
    }
	else {
		var range = oEle.ownerDocument.createRange();
		if(type=='beforeBegin'){
			range.setStartBefore(oEle);
		}else if(type=='afterBegin'){
			range.selectNodeContents(oEle);
			range.collapse(true);
		}else if(type=='beforeEnd'){
			range.selectNodeContents(oEle);
			range.collapse(oEle);
		}else if(type=='afterEnd'){
			range.setStartAfter(oEle);
		}
		var arr = [range.createContextualFragment(content)];
		app(arr);
    }
    setTimeout(function() {_content.evalScripts()}, 10);
}
var Insertion = {
	Before: function(e, content) {
		return insertion(e, content, 'beforeBegin');
	},
	Top: function(e, content) {
		return insertion(e, content, 'afterBegin');
	},
	Bottom: function(e, content) {
		return insertion(e, content, 'beforeEnd');
	},
	After: function(e, content) {
		return insertion(e, content, 'afterEnd');
	}
};
var Position = {
	page: function(oriEl) {
		var t = 0, l = 0;
		var el = oriEl;
		do {
			t += el.offsetTop  || 0;
			l += el.offsetLeft || 0;
			if (el.offsetParent==document.body
				&& getStyle(el, 'position')=='absolute') break;
		} while (el = el.offsetParent);
		el = oriEl;
		do {
			t -= el.scrollTop  || 0;
			l -= el.scrollLeft || 0;
		} while (el = el.parentNode);
		return [l, t];
	},
	offsetParent: function(el) {
		if (el.offsetParent) return el.offsetParent;
		var bd = document.body;
		if (el == bd) return el;
		while ((el = el.parentNode) && el != bd){
			var ps = getStyle(el, 'position');
			if (ps == 'relative' ||  ps== 'absolute')continue;
			return el;
		}
		return bd;
	},
	clone: function(es, et, cfg) {
		cfg = Object.extend({
			setLeft:    true,
			setTop:     true,
			setWidth:   true,
			setHeight:  true,
			offsetTop:  0,
			offsetLeft: 0
		}, cfg || {})
		es = $(es);
		var p = Position.page(es);
		et = $(et);
		var delta = [0, 0];
		var parent = null;
		if (getStyle(et, 'position') == 'absolute') {
			parent = Position.offsetParent(et);
			delta = Position.page(parent);
		}
		var bd = document.body;
		if (parent == bd) {
			delta[0] -= bd.offsetLeft;
			delta[1] -= bd.offsetTop;
		}
		var o = et.style;
		if(cfg.setLeft)   o.left  = (p[0] - delta[0] + cfg.offsetLeft) + 'px';
		if(cfg.setTop)    o.top   = (p[1] - delta[1] + cfg.offsetTop) + 'px';
		if(cfg.setWidth)  o.width = es.offsetWidth + 'px';
		if(cfg.setHeight) o.height = es.offsetHeight + 'px';
	},
	getPageInTop : function(dom, topWin){
		var t = 0, l = 0;
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		try{
			do{
				positions = Position.page(dom)
				l += positions[0];
				t += positions[1];
				if(topWin == win) break;
				dom = win.frameElement;
				win = win.parent;
			}while(dom);
		}catch(error){
			//just skip it.
		}
		return [l, t]
	},
	within: function(el, x, y) {
		this.offset = this.cumulativeOffset(el);
		return (y >= this.offset[1] &&
			y <  this.offset[1] + el.offsetHeight &&
			x >= this.offset[0] &&
			x <  this.offset[0] + el.offsetWidth);
	},
	cumulativeOffset: function(el) {
		var t = 0, l = 0;
		do {
			t += el.offsetTop  || 0;
			l += el.offsetLeft || 0;
			if (Ext.isSafari && el.offsetParent == document.body)
		        if (getStyle(el, 'position') == 'absolute') break;
			el = el.offsetParent;
		} while (el);
		return [l, t];
	}
}
Ext.apply(Ext.myEvent.prototype, {
	getScroll: function() {
		var dd = document.documentElement, db = document.body;
		if (dd && (dd.scrollTop || dd.scrollLeft)) {
			return [dd.scrollTop, dd.scrollLeft];
		} else if (db) {
			return [db.scrollTop, db.scrollLeft];
		} else {
			return [0, 0];
		}
	},
	getPageX: function() {
		var ev = this.browserEvent;
		var x = ev.pageX;
		if (!x && 0 !== x) {
			x = ev.clientX || 0;
			if (Ext.isIE) {
				x += this.getScroll()[1];
			}
		}
		return x;
	},
	getPageY: function() {
		var ev = this.browserEvent;
		var y = ev.pageY;
		if (!y && 0 !== y) {
			y = ev.clientY || 0;
			if (Ext.isIE) {
				y += this.getScroll()[0];
			}
		}
		return y;
	}
});
function getDomScroll(d){
	var doc = document;
	if(d == doc || d == doc.body){
		var l, t;
		if(Ext.isIE && Ext.isStrict){
			l = doc.documentElement.scrollLeft || (doc.body.scrollLeft || 0);
			t = doc.documentElement.scrollTop || (doc.body.scrollTop || 0);
		}else{
			l = window.pageXOffset || (doc.body.scrollLeft || 0);
			t = window.pageYOffset || (doc.body.scrollTop || 0);
		}
		return {left: l, top: t};
	}else{
		return {left: d.scrollLeft, top: d.scrollTop};
	}
}
function getDomXY(el) {
	var p, pe, b, scroll, bd = (document.body || document.documentElement);
	el = el.dom || el;
	if(el == bd)return [0, 0];
	if (el.getBoundingClientRect) {
		b = el.getBoundingClientRect();
		scroll = getDomScroll(document);
		return [b.left + scroll.left, b.top + scroll.top];
	}
	var x = 0, y = 0;
	p = el;
	var hasAbsolute = getStyle(el, "position") == "absolute";
	while (p) {
		x += p.offsetLeft;
		y += p.offsetTop;
		if (!hasAbsolute && getStyle(p, "position") == "absolute") {
			hasAbsolute = true;
		}
		if (Ext.isGecko) {
			var bt = parseInt(getStyle(p, "borderTopWidth"), 10) || 0;
			var bl = parseInt(getStyle(p, "borderLeftWidth"), 10) || 0;
			x += bl;
			y += bt;
			if (p != el && getStyle(p, 'overflow') != 'visible') {
				x += bl;
				y += bt;
			}
		}
		p = p.offsetParent;
	}
	if (Ext.isSafari && hasAbsolute) {
		x -= bd.offsetLeft;
		y -= bd.offsetTop;
	}
	if (Ext.isGecko && !hasAbsolute) {
		x += parseInt(getStyle(bd, "borderLeftWidth"), 10) || 0;
		y += parseInt(getStyle(bd, "borderTopWidth"), 10) || 0;
	}
	p = el.parentNode;
	while (p && p != bd) {
		if (!Ext.isOpera || (p.tagName != 'TR' && getStyle(p, "display") != "inline")) {
			x -= p.scrollLeft;
			y -= p.scrollTop;
		}
		p = p.parentNode;
	}
	return [x, y];
}
Ext.apply(Ext.myEl.prototype, {
	scrollIntoView : function(container, hscroll){
        var c = container.dom || $(container);
        var el = this.dom;
        var o = this.getOffsetsTo(c),
            l = o[0] + c.scrollLeft,
            t = o[1] + c.scrollTop,
            b = t+el.offsetHeight,
            r = l+el.offsetWidth;
        var ch = c.clientHeight;
        var ct = parseInt(c.scrollTop, 10);
        var cl = parseInt(c.scrollLeft, 10);
        var cb = ct + ch;
        var cr = cl + c.clientWidth;
        if(el.offsetHeight > ch || t < ct){
        	c.scrollTop = t;
        }else if(b > cb){
            c.scrollTop = b-ch;
        }
        c.scrollTop = c.scrollTop; // corrects IE, other browsers will ignore
        if(hscroll !== false){
			if(el.offsetWidth > c.clientWidth || l < cl){
                c.scrollLeft = l;
            }else if(r > cr){
                c.scrollLeft = r-c.clientWidth;
            }
            c.scrollLeft = c.scrollLeft;
        }
        return this;
    },
	getXY : function(){
        return getDomXY(this.dom);
    },
    getOffsetsTo : function(el){
        var o = this.getXY();
        var e = Ext.fly(el, '_internal').getXY();
        return [o[0]-e[0],o[1]-e[1]];
    }
});
//if(!document.getElementsByClassName){
	//firefox 3 beta supports the getElementsByClassName itself.
	document.getElementsByClassName = function(cls, p) {
		if(p && p.getElementsByClassName) return p.getElementsByClassName(cls);
		var arr = ($(p) || document.body).getElementsByTagName('*');
		var rst = [];
		var regExp = new RegExp("(^|\\s)" + cls + "(\\s|$)");
		for(var i=0,n=arr.length;i<n;i++){
			if (arr[i].className.match(regExp))
				rst.push(arr[i]);
		}
		return rst;
	}
	Event.observe(window, 'unload', function(){
		document.getElementsByClassName = null;
	});
//}
//if(!Ext.isIE){//fix ie9, because ie9 like forefox
if(window.HTMLElement){// && !HTMLElement.prototype.fireEvent){
	HTMLElement.prototype.fireEvent = function(sType){
		sType = sType.replace(/^on/, "");
		var evtObj = document.createEvent('MouseEvents');     
		evtObj.initMouseEvent(sType, true, true, document.defaultView, 1, 0, 0, 0, 0, false, false, true, false,   0, null);     
		this.dispatchEvent(evtObj);
	};
}
if(!Ext.isIE){
	HTMLElement.prototype.__defineGetter__("innerText", function(){
		var text = this.ownerDocument.createRange();
		text.selectNodeContents(this);
		return text.toString();
	});
	HTMLElement.prototype.__defineSetter__("innerText", function (sText) {
		this.innerHTML = sText.replace(/\&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\n/g,'<br>');
	});
};
Ext.applyIf(String.prototype, {
	stripTags: function() {
		return this.replace(/<\/?[^>]+>/gi, '');
	},
	escapeHTML: function() {
		var div = document.createElement('div');
		var text = document.createTextNode(this);
		div.appendChild(text);
		return div.innerHTML;
	},
	unescapeHTML: function() {
		var div = document.createElement('div');
		div.innerHTML = this.stripTags();
		return div.childNodes[0] ? div.childNodes[0].nodeValue : '';
	}
});