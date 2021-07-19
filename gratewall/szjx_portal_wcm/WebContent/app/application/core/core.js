if(!window.Ext){
	var Ext = {version: '2.2'};
}
function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
}
function $A(m) {
	if (!m) return [];
	var rst = [];
	for (var i = 0; i < m.length; i++)
		rst.push(m[i]);
	return rst;
}
function $F(el){
	el = $(el);
	var tagNm = el.tagName, eleType = (el.type||'').toLowerCase();
	if(tagNm=='INPUT' &&
		eleType=='checkbox'){
		return el.getAttribute('isboolean', 2) ?
				(el.checked?1:0) : (el.checked?el.value:"");
	}else if(tagNm=='INPUT' && eleType=='radio'){
		return el.getAttribute('isboolean', 2) ?
				(el.checked?1:0) : (el.checked?el.value:null);
	}
	return el.value;
}
function $$F(name){
	if(typeof name!='string')name = name.name;
	var eles = document.getElementsByName(name);
	var rst = [], v;
	for(var i=0; i<eles.length; i++){
		v = $F(eles[i]);
		if(v!=null)rst.push(v);
	}
	return rst.join(',');
}
function Try() {
	for (var i = 0; i < arguments.length; i++) {
	  try {
		return (arguments[i])();
	  } catch (e) {}
	}
}
function getParameter(_sName, _sQuery){
	if(!_sName)return '';
	var query = _sQuery || location.search;
	if(!query)return '';
	var arr = query.substring(1).split('&');
	_sName = _sName.toUpperCase();
	for (var i=0,n=arr.length; i<n; i++){
		if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
			return arr[i].substring(_sName.length + 1);
		}
	}
	return '';
}
function $toQueryStr(_oParams, _bUpper){
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
function $openMaxWin(_sUrl, _sName, _bReplace, _bResizable){
	var nWidth	= window.screen.width - 12, nHeight = window.screen.height - 60;
	var nLeft	= 0, nTop	= 0, sName	= _sName || "";
	sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
	var oWin = window.open(_sUrl, sName, "resizable=" 
		+ (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" 
		+ nLeft + ",menubar =no,toolbar =no,width=" 
		+ nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no", _bReplace);
	if(oWin)oWin.focus();
	return oWin;
}
function $transHtml(_sContent) {
	if (_sContent == null)
		return '';

	var nLen = _sContent.length;
	if (nLen == 0)
		return '';

	var result = '';
	for (var i = 0; i < nLen; i++) {
		var cTemp = _sContent.charAt(i);
		switch (cTemp) {
		case '<': //&lt;
			result += '&lt;';
			break;
		case '>': //&gt;
			result += '&gt;';
			break;
		case '"': //&quot;
			result += '&quot;';
			break;
		default:
			result += cTemp;
		}// case
	}// end for
	return result;
}
Ext.apply = Object.extend = function(d, s) {
	if(!d || !s)return d;
	for (p in s)d[p] = s[p];
	return d;
}
Ext.ns = function(){
	for(var j=0;j<arguments.length;j++){
		ns = arguments[j];
		var nps = ns.split('.');
		var o = window; 
		for(var i=0 ; i<nps.length ; i++){
			o = o[nps[i]] = o[nps[i]] || {}; 
		}
	}
}
Array.isArray = function(arr){
	return arr!=null && typeof arr=='object' && arr.length!=null && arr.splice;
}
Ext.apply(Array.prototype, {
	each: function(iterator) {
		try {
			for (var i = 0; i < this.length; i++){
				iterator(this[i], i);
			}
		} catch (e) {
			if (e != Ext.$break) throw e;
		}
	},
	map: function(iterator) {
		var results = [];
		this.each(function(value, index) {
			results.push(iterator(value, index));
		});
		return results;
	},
	include : function(o){
		return this.indexOf(o)!=-1;
	},
	indexOf : function(o){
		for (var i = 0, len = this.length; i < len; i++){
			if(this[i] == o) return i;
		}
		return -1;
	},
	remove : function(o){
		var index = this.indexOf(o);
		if(index != -1){
			this.splice(index, 1);
		}
		return this;
	},
	compact: function() {
		var rst = [];
		for (var i = 0, n = this.length; i < n; i++){
			if(this[i]!=null)rst.push(this[i]);
		}
		return rst;
	}
});
Ext.apply(String, {
	scriptFragment: '(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)',
	format : function(format){
		var args = Array.prototype.slice.call(arguments, 1);
		return format.replace(/\{(\d+)\}/g, function(m, i){
			return args[i];
		});
	},
	isString : function(o){
		return (typeof o=="string") || (o!=null && o.split && o.match);
	}
});

Ext.apply(String.prototype, {
	stripTags: function() {
		return this.replace(/<\/?[^>]+>/gi, '');
	},
	escape4Xml : function(){
		return this.replace(/[<\'\">&]/g,function(c){
			switch(c){
				case '<':
					return '&lt;';
				case '>':
					return '&gt;';
				case '\'':
					return '&apos;';
				case '"':
					return '&quot;';
				case '&':
					return '&amp;';
			}
		});
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
	},
	stripScripts: function() {
		return this.replace(new RegExp(String.scriptFragment, 'img'), '');
	},
	extractScripts: function() {
		var matchAll = new RegExp(String.scriptFragment, 'img');
		var matchOne = new RegExp(String.scriptFragment, 'im');
		return (this.match(matchAll) || []).map(function(scriptTag) {
			return (scriptTag.match(matchOne) || ['', ''])[1];
		});
	},
	evalScripts: function() {
		return this.extractScripts().map(eval);
	},
	byteLength : function(){
		var length = 0;
		this.replace(/[^\x00-\xff]/g,function(){length++;});
		return this.length+length;
	},
	parseQuery : function() {
		var pairs = this.match(/^\??(.*)$/)[1].split('&'), rst = {}, pair;
		for(var i=0,n=pairs.length;i<n;i++){
			pair = pairs[i].split('=');
			rst[pair[0]] = decodeURIComponent(pair[1]);
		};
		return rst;
	},
	camelize : function(){
		return this.charAt(0).toUpperCase() + this.substring(1).toLowerCase();
	},
    endsWith : function(sEnd) {
		return (this.substr(this.length-sEnd.length)==sEnd);
	},
	startsWith : function(sStart) {
		return (this.substr(0,sStart.length)==sStart);
	},
	trim : function(){
		return this.replace(/^\s*/, "").replace(/\s*$/, "");
	}
});
Object.deepClone = function(o){
	if(!o || typeof o!='object')return o;
	var rst = {};
	if(Array.isArray(o)){
		rst = [];
		for(var i=0,n=o.length;i<n;i++)
			rst.push(Object.deepClone(o[i]));
		return rst;
	}
	for(var p in o)rst[p] = Object.deepClone(o[p]);
	return rst;
}
Ext.apply(Function.prototype, {
	bind : function() {
		var __method = this, args = $A(arguments), object = args.shift();
		return function() {
			return __method.apply(object, args.concat($A(arguments)));
		}
	},  
	createSequence : function(fcn, scope){
        if(typeof fcn != "function"){
            return this;
        }
        var method = this;
        return function() {
            var retval = method.apply(this || window, arguments);
            fcn.apply(scope || this || window, arguments);
            return retval;
        };
    },
    createInterceptor : function(fcn, scope){
        if(typeof fcn != "function"){
            return this;
        }
        var method = this;
        return function() {
            fcn.target = this;
            fcn.method = method;
            if(fcn.apply(scope || this || window, arguments) === false){
                return;
            }
            return method.apply(this || window, arguments);
        };
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
if(!window.Insertion) {
	var Insertion = {};
}
Ext.apply(Insertion, {
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
});
if(!window.Element) {
	var Element = {};
}
Ext.apply(Element, {
	hide: function() {
		for (var i = 0; i < arguments.length; i++) {
		  var el = $(arguments[i]);
		  if(el)el.style.display = 'none';
		}
	},
	show: function() {
		for (var i = 0; i < arguments.length; i++) {
		  var el = $(arguments[i]);
		  if(el)el.style.display = '';
		}
	},
	visible: function(el) {
		if (!(el = $(el))) return;
		return el.style.display != 'none';
	},
	toggle: function(el) {
		Element[Element.visible(el)?'hide':'show'](el);
	},
	hasClassName: function(el, cs) {
		if (!(el = $(el))) return;
		return (' '+el.className+' ').indexOf(' '+cs+' ')!=-1;
	},
	addClassName: function(el, cs) {
		if(Element.hasClassName(el, cs))return;
		if (!(el = $(el))) return;
		return el.className = el.className + ' ' + cs;
	},
	removeClassName: function(el, cs) {
		if (!(el = $(el))) return;
		return el.className = el.className.replace(new RegExp('(^|\\s+)'+cs+'(\\s+|$)', 'ig'), ' ');
	},
	update: function(id, html, loadScripts, callback) {
        if(typeof html == "undefined"){
            html = "";
        }
		$(id).innerHTML = html.stripScripts();
		setTimeout(function() {
			html.evalScripts();
			if(typeof callback == "function"){
				callback();
			}
		}, 50);
		return this;
	},
	getStyle: function(element, style) {
		element = $(element);
		var value = element.style[style.camelize()];
		if (!value) {
			if (document.defaultView && document.defaultView.getComputedStyle) {
				var css = document.defaultView.getComputedStyle(element, null);
				value = css ? css.getPropertyValue(style) : null;
			} else if (element.currentStyle) {
				value = element.currentStyle[style];
			}
		}
		if (window.opera && ['left', 'top', 'right', 'bottom'].include(style))
			if (Element.getStyle(element, 'position') == 'static') value = 'auto';
		return value == 'auto' ? null : value;
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
	remove : function(dom){
		Ext.removeNode(dom);
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
	}
});
if(!window.Event){
	var Event = {};
}
Ext.apply(Event, {
	KEY_BACKSPACE:	8,
	KEY_TAB:		9,
	KEY_RETURN:		13,
	KEY_ESC:		27,
	KEY_LEFT:		37,
	KEY_UP:			38,
	KEY_RIGHT:		39,
	KEY_DOWN:		40,
	KEY_DELETE:		46,
	KEY_PGUP:		33,
	KEY_PGDN:		34,
	KEY_HOME:		36,
	KEY_END:		35,
  element: function(e) {
    return e.target || e.srcElement;
  },
  blurElement: function(e) {
    return e.explicitOriginalTarget || document.activeElement;
  },
  pointerX: function(e) {
    return e.pageX || (e.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },
  pointerY: function(e) {
    return e.pageY || (e.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
  },
  stop: function(e) {
    if (e.preventDefault) {
      e.preventDefault();
      e.stopPropagation();
    } else {
      e.returnValue = false;
      e.cancelBubble = true;
    }
  },
  observers: false,
  _observe : function(el, name, fn){
	if(el == null)return;

    if (el.addEventListener) {
      el.addEventListener(name, fn, false);
    } else if (el.attachEvent) {
      el.attachEvent('on' + name, fn);
    }
  },
  _observeAndCache: function(el, name, fn) {
    if (!this.observers) this.observers = [];
	this.observers.push([el, name, fn]);
	this._observe(el, name, fn);
  },
  unloadCache: function(ev) {
	var arr = Event.observers || [];
	for (var i = 0; i < arr.length; i++) {
		if(arr[i]==null)continue;
		Event._stopObserving.apply(Event, arr[i]);
		arr[i][0] = null;
	}
	Event.observers = false;
	var arr = Event.unloadListeners || [];
	for (i = 0,len = arr.length; i < len; i++) {
		if(!arr[i])continue;
		arr[i][2].call(window, ev);
		arr[i] = null;
	}
	Event.unloadListeners = false;
  },
  observe: function(el, name, fn) {
    var el = $(el);
	if ("unload" == name) {
		var arr = this.unloadListeners = this.unloadListeners || [];
		arr.push([el, name, fn]);
		return;
	}
    this._observeAndCache(el, name, fn);
  },
  _stopObserving : function(el, name, fn){
	  if(el == null)return;

		if (el.removeEventListener) {
		  el.removeEventListener(name, fn, false);
		} else if (el.detachEvent) {
		  el.detachEvent('on' + name, fn);
		}
  },
  stopObserving: function(el, name, fn) {
    var el = $(el);
	if ("unload" == name) {
		var arr = Event.unloadListeners || [];
		for (i = 0,len = arr.length; i < len; i++) {
			var li = arr[i];
			if (!(li && li[0] == el && li[1] == name && li[2] == fn))continue;
			arr.splice(i, 1);
			return true;
		}
		return false;
	}
	var arr = Event.observers || [];
	for (var i = 0; i < arr.length; i++) {
		var li = arr[i];
		if (!(li && li[0] == el && li[1] == name && li[2] == fn))continue;
		arr.splice(i, 1);
		break;
	}
	this._stopObserving(el, name, fn);
  },
  stopAllObserving : function (el, name){
	if(!(el = $(el)))return;
	if(name=='unload' || (!name && el==window)){
		Event.unloadListeners = false;
		if(name=='unload')return;
	}
	var arr = Event.observers || [];
	for (var i = 0; i < arr.length; i++) {
		if(arr[i][0] != el)continue;
		if(name!=null && name!=arr[i][1])continue;
		Event.stopObserving.apply(Event, arr[i]);
		arr.splice(i, 1);
	}
  }
});
Event._observe(window, 'unload', Event.unloadCache);
Event.observe(window, 'unload', function(){
    var p = Function.prototype;
	delete p.bind;
	delete p.createSequence;
	delete p.createInterceptor;
	delete Object.extend;
	delete Object.clone;
	delete Object.deepClone;
	delete Object.parseSource;
	delete Object._parseSource;
});
Ext.apply(Ext, {
	$break : {},
	emptyFn : function(){},
	errorMsg : function(e){
		return e.stack || '';
	},
	isDebug : function(){return false},
	applyIf : function(o, c){
		if(!o || !c)return o;
		for(var p in c){
			if(typeof o[p] == "undefined")o[p] = c[p];
		}
		return o;
	},
	extend : function(){
		// inline overrides
		var io = function(o){
			for(var m in o){
				this[m] = o[m];
			}
		};
		var oc = Object.prototype.constructor;
		return function(sb, sp, overrides){
			if(typeof sp == 'object'){
				overrides = sp;
				sp = sb;
				sb = overrides.constructor != oc ? overrides.constructor : function(){sp.apply(this, arguments);};
			}
			var F = function(){}, sbp, spp = sp.prototype;
			F.prototype = spp;
			sbp = sb.prototype = new F();
			sbp.constructor=sb;
			sb.superclass=spp;
			if(spp.constructor == oc){
				spp.constructor=sp;
			}
			sb.override = function(o){
				Ext.override(sb, o);
			};
			sbp.override = io;
			Ext.override(sb, overrides);
			sb.extend = function(o){Ext.extend(sb, o);};
			return sb;
		};
	}(),
	override : function(origclass, overrides){
		if(!overrides)return;
		var p = origclass.prototype;
		for(var method in overrides){
			p[method] = overrides[method];
		}
	},
	isArray : Array.isArray,
	isBoolean : function(o){
		return o===true || o===false;
	},
	isFunction : function(o){
		return typeof o=="function";
	},
	isString : String.isString,
	type : function(o){
		if(o === undefined || o === null){
			return false;
		}
		if(o.htmlElement){
			return 'element';
		}
		var t = typeof o;
		if(t == 'object' && o.nodeName) {
			switch(o.nodeType) {
				case 1: return 'element';
				case 3: return (/\S/).test(o.nodeValue) ? 'textnode' : 'whitespace';
			}
		}
		if(t == 'object' || t == 'function') {
			switch(o.constructor) {
				case Array: return 'array';
				case RegExp: return 'regexp';
			}
			if(typeof o.length == 'number' && typeof o.item == 'function') {
				return 'nodelist';
			}
		}
		return t;
	},
	isEmpty : function(v, allowBlank){
		return v === null || v === undefined || (!allowBlank ? v === '' : false);
	},
	isNumber : function(o){
		return (typeof o=="number") || (o!=null && o.constructor==Number);
	},
	isSimpType : function(o){
		return this.isString(o) || this.isNumber(o) || this.isBoolean(o);
	},
	isDom : function(o){
		if(typeof o != 'object') return false;
		if(o.nodeType != null && o.nodeName != null) return true;
		return (typeof o == 'object')&&((Ext.isIE&&o.constructor===window.undefined)||
			(!Ext.isIE&&o.constructor.toString().indexOf('Element')>=0));
	},
	isObject : function(o){
		return Ext.type(o)=='object';
	},
	getActualTop : function(){
		if(window.__actualTop)return window.__actualTop;
		var actualTop = window;
		while(true){
			try{
				if(actualTop.__actualTop != null){
					window.__actualTop = actualTop.__actualTop;
					return actualTop.__actualTop;
				}
			}catch(err){
				break;
			}
			if(actualTop==top)break;
			try{
				var testDoc = actualTop.parent.window;
				actualTop = actualTop.parent;
			}catch(err){
				break;
			}
		}
		window.__actualTop = window;
		return window;
	},
	removeNode : Ext.isIE ? function(){
		var d;
		return function(n){
			if(n && n.tagName != 'BODY'){
				d = d || document.createElement('div');
				d.appendChild(n);
				d.innerHTML = '';
			}
		}
	}() : function(n){
		if(n && n.parentNode && n.tagName != 'BODY'){
			n.parentNode.removeChild(n);
		}
	}
});
Object._parseSource=function(e){
	if(e==null || Ext.isSimpType(e)){
		return '' + e;
	}
	var sType = Ext.type(e);
	if(sType == 'function'){
		return false;
	}
	if(sType == 'object'){
		var t = [];
		for(var name in e){
			var child = Object._parseSource(e[name]);
			if(child===false) continue;
			t.push(name + ':' + child);
		}
		return '{' + t.join(',') + '}';
	}
	if(Ext.isArray(e)){
		var tmp = e.map(function(f){
			return Object._parseSource(f);
		});
		return '[' + tmp.join(',') + ']';
	}
	return false;
};
Ext.toSource = Object.parseSource = function(o){
	try{
		return o.toSource();
	}catch(err){
		var sResult = Object._parseSource(o);
		if(sResult!==false)return sResult;
		return o.toString();
	}
};
if(!window.Position){
	Position = {};
}
Ext.apply(Position, {
  // set to true if needed, warning: firefox performance problems
  // NOT neeeded for page scrolling, only if draggable contained in
  // scrollable elements
  includeScrollOffsets: false,

  // must be called before calling withinIncludingScrolloffset, every time the
  // page is scrolled
  prepare: function() {
    this.deltaX =  window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft
                || 0;
    this.deltaY =  window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop
                || 0;
  },

  realOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.scrollTop  || 0;
      valueL += element.scrollLeft || 0;
      element = element.parentNode;
    } while (element);
    return [valueL, valueT];
  },

  cumulativeOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
    } while (element);
    return [valueL, valueT];
  },

  positionedOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
      if (element) {
        p = Element.getStyle(element, 'position');
        if (p == 'relative' || p == 'absolute') break;
      }
    } while (element);
    return [valueL, valueT];
  },

  offsetParent: function(element) {
    if (element.offsetParent) return element.offsetParent;
    if (element == document.body) return element;

    while ((element = element.parentNode) && element != document.body)
      if (Element.getStyle(element, 'position') != 'static')
        return element;

    return document.body;
  },

  // caches x/y coordinate pair to use with overlap
  within: function(element, x, y) {
    if (this.includeScrollOffsets)
      return this.withinIncludingScrolloffsets(element, x, y);
    this.xcomp = x;
    this.ycomp = y;
    this.offset = this.cumulativeOffset(element);

    return (y >= this.offset[1] &&
            y <  this.offset[1] + element.offsetHeight &&
            x >= this.offset[0] &&
            x <  this.offset[0] + element.offsetWidth);
  },

  withinIncludingScrolloffsets: function(element, x, y) {
    var offsetcache = this.realOffset(element);

    this.xcomp = x + offsetcache[0] - this.deltaX;
    this.ycomp = y + offsetcache[1] - this.deltaY;
    this.offset = this.cumulativeOffset(element);

    return (this.ycomp >= this.offset[1] &&
            this.ycomp <  this.offset[1] + element.offsetHeight &&
            this.xcomp >= this.offset[0] &&
            this.xcomp <  this.offset[0] + element.offsetWidth);
  },

  // within must be called directly before
  overlap: function(mode, element) {
    if (!mode) return 0;
    if (mode == 'vertical')
      return ((this.offset[1] + element.offsetHeight) - this.ycomp) /
        element.offsetHeight;
    if (mode == 'horizontal')
      return ((this.offset[0] + element.offsetWidth) - this.xcomp) /
        element.offsetWidth;
  },

  page: function(forElement) {
    var valueT = 0, valueL = 0;

    var element = forElement;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;

      // Safari fix
      if (element.offsetParent==document.body)
        if (Element.getStyle(element,'position')=='absolute') break;

    } while (element = element.offsetParent);

    element = forElement;
    do {
      valueT -= element.scrollTop  || 0;
      valueL -= element.scrollLeft || 0;
    } while (element = element.parentNode);

    return [valueL, valueT];
  },
	getAbsolutePositionInTop : function(dom, topWin){
		var valueT = 0, valueL = 0;
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		try{
			do{
				positions = Position.cumulativeOffset(dom)
				valueL += positions[0];
				valueT += positions[1];
				if(topWin == win) break;
				dom = win.frameElement;
				win = win.parent;
			}while(dom);
		}catch(error){
			//just skip it.
		}
		return [valueL, valueT]
	},
	getPageInTop : function(dom, topWin){
		var valueT = 0, valueL = 0;
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		try{
			do{
				positions = Position.page(dom)
				valueL += positions[0];
				valueT += positions[1];
				if(topWin == win) break;
				dom = win.frameElement;
				win = win.parent;
			}while(dom);
		}catch(error){
			//just skip it.
		}
		return [valueL, valueT]
	},
  clone: function(source, target) {
    var options = Object.extend({
      setLeft:    true,
      setTop:     true,
      setWidth:   true,
      setHeight:  true,
      offsetTop:  0,
      offsetLeft: 0
    }, arguments[2] || {})

    // find page position of source
    source = $(source);
    var p = Position.page(source);

    // find coordinate system to use
    target = $(target);
    var delta = [0, 0];
    var parent = null;
    // delta [0,0] will do fine with position: fixed elements,
    // position:absolute needs offsetParent deltas
    if (Element.getStyle(target,'position') == 'absolute') {
      parent = Position.offsetParent(target);
      delta = Position.page(parent);
    }

    // correct by body offsets (fixes Safari)
    if (parent == document.body) {
      delta[0] -= document.body.offsetLeft;
      delta[1] -= document.body.offsetTop;
    }

    // set position
    if(options.setLeft)   target.style.left  = (p[0] - delta[0] + options.offsetLeft) + 'px';
    if(options.setTop)    target.style.top   = (p[1] - delta[1] + options.offsetTop) + 'px';
    if(options.setWidth)  target.style.width = source.offsetWidth + 'px';
    if(options.setHeight) target.style.height = source.offsetHeight + 'px';
  }
});

// Safari returns margins on body which is incorrect if the child is absolutely
// positioned.  For performance reasons, redefine Position.cumulativeOffset for
// KHTML/WebKit only.
if (/Konqueror|Safari|KHTML/.test(navigator.userAgent)) {
  Position.cumulativeOffset = function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      if (element.offsetParent == document.body)
        if (Element.getStyle(element, 'position') == 'absolute') break;

      element = element.offsetParent;
    } while (element);

    return [valueL, valueT];
  }
}

(function(){
var ua = navigator.userAgent.toLowerCase();
var isStrict = document.compatMode == "CSS1Compat",
	isOpera = ua.indexOf("opera") > -1,
	isChrome = ua.indexOf("chrome") > -1,
	isSafari = (/webkit|khtml/).test(ua),
	isSafari3 = isSafari && ua.indexOf('webkit/5') != -1,
	isIE = !isOpera && ua.indexOf("msie") > -1,
	isIE7 = !isOpera && ua.indexOf("msie 7") > -1,
	isIE8 = !isOpera && (ua.indexOf("msie 8") > -1 || ua.indexOf("msie 9") > -1),
	isIE6 = !isOpera && !isIE7 && ua.indexOf("msie 6") > -1,
	isGecko = !isSafari && ua.indexOf("gecko") > -1,
	isGecko2 = isGecko && ua.indexOf("firefox/2") > -1,	
	isGecko3 = !isSafari && ua.indexOf("rv:1.9") > -1,
	isSecure = window.location.href.toLowerCase().indexOf("https") === 0;
    // remove css image flicker
	if(isIE && !isIE7){
        try{
            document.execCommand("BackgroundImageCache", false, true);
        }catch(e){}
    }
	var vars = ['isStrict', 'isOpera', 'isChrome', 'isSafari', 'isSafari3', 'isIE', 'isIE7', 'isIE8', 'isIE6', 'isGecko', 'isGecko2', 'isGecko3', 'isSecure'];
	for(var i=0;i<vars.length;i++) Ext[vars[i]] = eval(vars[i]);
})();
(function(){
	var inited, initExtCssThreadId;
    window.initExtCss = function(){
        var bd = document.body;
        if(!bd){ return false; } 
		clearInterval(initExtCssThreadId);
		if(bd.getAttribute('_cssrender') || inited) return true;
		inited = true;
        var cls = [' ',
                Ext.isIE ? "ext-ie " + (Ext.isIE6 ? 'ext-ie6' : 'ext-ie7')
                : Ext.isGecko ? "ext-gecko " + (Ext.isGecko2 ? 'ext-gecko2' : 'ext-gecko3')
                : Ext.isOpera ? "ext-opera"
                : Ext.isSafari ? "ext-safari" : ""];
		if(Ext.isIE8)cls.push("ext-ie8");
        if(Ext.isStrict){
            var p = bd.parentNode;
            if(p){
                p.className += ' ext-strict';
            }
        }
		cls.push('res-'+screen.width+"x"+screen.height);
        bd.className += cls.join(' ');
		bd.setAttribute('_cssrender', 1);
        return true;
    }
	initExtCssThreadId = setInterval(initExtCss, 50);
})();

Ext.ns('com.trs.util.ItemParser');
/**
*枚举值解析器
*/
com.trs.util.ItemParser = function(){
	/**
	*item之间的分隔符
	*/
	var itemSeparator = '~';

	/**
	*item内部label和value之间的分隔符
	*/
	var labelValueSeparator = '`';

	return {
		parse : function(sItems){
			if(!sItems) return [];
			var aItems = sItems.split(itemSeparator);
			var result = [];
			for (var i = 0; i < aItems.length; i++){
				//add by wyw
				if(aItems[i].trim()=="")continue;
				var aItem = aItems[i].split(labelValueSeparator);
				result.push({label : aItem[0], value : aItem[1] || aItem[0]});
			}
			return result;
		}
	}
}();
if(!Ext.isIE)
{
	HTMLElement.prototype.__defineGetter__("innerText",function()
	{
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
	      return str + ">";

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
//		window[u] = this;
		this.__defineGetter__("uniqueID",function(){return u});
		return u;
	});
};
