if(!window.Ext){
	var Ext = {version: '2.2'};
}

function $(el) {
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
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
function Try() {
	for (var i = 0; i < arguments.length; i++) {
	  try {
		return (arguments[i])();
	  } catch (e) {}
	}
}
Array.isArray = function(arr){
	return arr!=null && typeof arr=='object' && arr.length!=null && arr.splice;
}
Object.extend(Array.prototype, {
	last : function(){
		return this[this.length - 1];
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
String.format = function(format){
	var args = Array.prototype.slice.call(arguments, 1);
	return format.replace(/\{(\d+)\}/g, function(m, i){
		return args[i];
	});
}
Object.extend(String.prototype, {
 	camelize0 : function(){
		var oStringList = this.split('-');
		if (oStringList.length == 1) return oStringList[0];

		var camelizedString = this.indexOf('-') == 0
		  ? oStringList[0].charAt(0).toUpperCase() + oStringList[0].substring(1)
		  : oStringList[0];

		for (var i = 1, len = oStringList.length; i < len; i++) {
		  var s = oStringList[i];
		  camelizedString += s.charAt(0).toUpperCase() + s.substring(1);
		}

		return camelizedString;
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
		(eleType=='checkbox' || eleType=='radio')){
		return el.getAttribute('isboolean', 2) ?
				(el.checked?1:0) : (el.checked?el.value:null);
	}
	if(tagNm == "SPAN" && el.className.trim() == "xdRichTextBox"){
		return el.innerHTML;
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
function findItem(t, cls, attr, aPAttr){
	aPAttr = aPAttr || [];
	while(t!=null&&t.tagName!='BODY'&& t.nodeType==1){
		for (var i = 0; i < aPAttr.length; i++){
			if(dom.getAttribute(aPAttr[i]) != null) return 0;
		}
		if(cls && Element.hasClassName(t, cls))return t;
		if(attr && t.getAttribute(attr, 2)!=null)return t;
		t = t.parentNode;
	}
	return null;
}

Function.prototype.bind = function() {
  var __method = this, args = $A(arguments), object = args.shift();
  return function() {
    return __method.apply(object, args.concat($A(arguments)));
  }
}
if(!window.Element) {
	var Element = {};
}
Ext.apply(Element, {
	find : function(t, attr, cls, aPAttr){
		return findItem(t, cls, attr, aPAttr);
	},
	toggle: function(el) {
		Element[Element.visible(el)?'hide':'show'](el);
	},
	visible: function(el) {
		if (!(el = $(el))) return;
		return el.style.display != 'none';
	},
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
	update : function(el, html){
		if (!(el = $(el))) return;
		el.innerHTML = html;
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
	 }
});
if(!window.Event){
	var Event = {};
}
Object.extend(Event, {
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
	Event._stopObserving(window, 'unload', Event.unloadCache);
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
	  try{
		if (el.removeEventListener) {
		  el.removeEventListener(name, fn, false);
		} else if (el.detachEvent) {
		  el.detachEvent('on' + name, fn);
		}
	  }catch(error){
	  }
  },
  stopObserving: function(el, name, fn) {
    var el = $(el);
	if ("unload" == name) {
		var arr = Event.unloadListeners || [];
		for (var i = arr.length - 1; i >= 0; i--) {
			var li = arr[i];
			if (!(li && li[0] == el && li[1] == name && li[2] == fn))continue;
			arr.splice(i, 1);
			li[0] = null;
			return true;
		}
		return false;
	}
	var arr = Event.observers || [];
	for (var i = arr.length - 1; i >= 0; i--) {
		var li = arr[i];
		if (!(li && li[0] == el && li[1] == name && li[2] == fn))continue;
		arr.splice(i, 1);
		this._stopObserving(el, name, fn);
		return true;
	}
	return false;
  },
  stopAllObserving : function (el, name){
	if(!(el = $(el)))return;
	if(name=='unload' || (!name && el==window)){
		Event.unloadListeners = false;
		if(name=='unload')return;
	}
	var arr = Event.observers || [];
	for (var i = arr.length - 1; i >= 0; i--) {
		if(arr[i][0] != el)continue;
		if(name!=null && name!=arr[i][1])continue;
		Event._stopObserving.apply(Event, arr[i]);
		arr[i][0] = null;
		arr.splice(i, 1);
	}
  }
});
Event._observe(window, 'unload', Event.unloadCache);
Event.observe(window, 'unload', function(){
	delete Function.prototype.bind;
	delete Object.extend;
	delete Object.clone;
	delete Object.deepClone;
});
var m_genId = 0;
function genExtId(){
	return 'myext-'+(++m_genId);
}

Ext.myEvent = function(){}
function extEvent(ev){
	var rst = new Ext.myEvent();
	Ext.apply(rst, {
		browserEvent : ev,
		type : ev.type,
		target : Event.element(ev),
		blurTarget : Event.blurElement(ev),
		within : function(el){
            var t = Event.element(ev);
            while(t && t.tagName!='BODY'){
				if(t==el)return true;
				t = t.parentNode;
			}
			return false;
        },
		stop : function(){
			Event.stop(ev);
		},
		pointer : [Event.pointerX(ev), Event.pointerY(ev)],
		button : rst.button0 ? rst.button0(ev) : 0
	});
	return rst;
}
var m_extListeners = {};
function addFxWrap(id, evName, fn, scope, wrap){
	var es, ls;
	m_extListeners[id] = es = m_extListeners[id] || {};
	es[evName] = ls = es[evName] || [];
	ls.push({fn:fn,scope:scope,wrap:wrap});
}
function removeFxWrap(id, evName, fn, scope){
	var es = m_extListeners[id];
	if(!es)return;
	var ls = es[evName], l;
	if(!ls)return;
	for(var i = 0, len = ls.length; i < len; i++){
		l = ls[i];
		if(l.fn == fn && (!scope || l.scope == scope)){
			wrap = l.wrap;
			ls.splice(i, 1);
			return wrap;
		}
	}
}
Ext.myEl = function(){}
Ext.fly = Ext.get = function(el){
	el = $(el);
	if(!el.id)el.id = genExtId();
	var id = el.id, rst = new Ext.myEl();
	Ext.apply(rst, {
		dom : el,
		on : function(evName, f, scope){
			function h(ev){
				ev = ev || window.event;
				var nev = extEvent(ev);
				f.call(scope, nev, nev.target);
			}
			addFxWrap(id, evName, f, scope, h);
			Event.observe(id, evName, h);
		},
		un : function(evName, f, scope){
			var h = removeFxWrap(id, evName, f, scope);
			if(h==null)return;
			Event.stopObserving(id, evName, h);
		}
	});
	return rst;
}

function lbinit(){
	Ext.SSL_SECURE_URL = "javascript:false";
	Ext.isSecure = window.location.href.toLowerCase().indexOf("https") === 0;
	Ext.blankUrl = Ext.isSecure ? Ext.SSL_SECURE_URL : "";
	var ua = navigator.userAgent.toLowerCase();
	var isOpera = ua.indexOf("opera") > -1;
	Ext.isIE = !isOpera && ua.indexOf("msie") > -1;
	isIE7 = !isOpera && ua.indexOf("msie 7") > -1;
	Ext.isIE6 = !isOpera && !isIE7 && ua.indexOf("msie 6") > -1;
};
lbinit();

//兼容ff的部分方法
(function(){
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1) return;
	var _emptyTags = {
	   "IMG":   true,
	   "BR":    true,
	   "INPUT": true,
	   "META":  true,
	   "LINK":  true,
	   "PARAM": true,
	   "HR":    true
	};
	HTMLElement.prototype.__defineGetter__("innerText",function(){
		var text=null;
		text = this.ownerDocument.createRange();
		text.selectNodeContents(this);
		text = text.toString();
		return text;
	});
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
})();

window.DOM = window.DOM || {};
Ext.apply(window.DOM,(function(){
	(function(){
		if (document.addEventListener) {
			document.addEventListener( "DOMContentLoaded", function(){
				document.removeEventListener( "DOMContentLoaded", arguments.callee, false );//清除加载函数
				fireReady();
			}, false );
		}else{//ie
			if (document.getElementById) {
				document.write("<script id=\"ie-domReady\" defer=\"defer\" src=\"//:\" type=\"text/javascript\"><\/script>");
				document.close();
				document.getElementById("ie-domReady").onreadystatechange = function() {
					if (this.readyState === "complete") {
						fireReady();
						this.onreadystatechange = null;
						this.parentNode.removeChild(this);
					}
				};
			}
		}
		// 无论如何，确保事件可以执行，而且只执行一次
		Event.observe(window,"load",fireReady);
	})();

	/**运行函数列表*/
	function fireReady(){
		if(!DOM.isReady && DOM.readyEvents){//确保只运行一次
			DOM.isReady = true;
			for (var i = 0; i < DOM.readyEvents.length; i++){
				DOM.readyEvents[i]();
			}
			/**按照W3C标准，把数组的长度设置较小时，末尾的元素都会被清除（清除事件）*/
			DOM.readyEvents.length = 0;
		}
	}
	return {
		/**只添加事件，如果DOM已经ready，则可以直接运行*/
		ready:function (fn){
			if(DOM.isReady){
				fn();
			}else{
				DOM.readyEvents = DOM.readyEvents || [];
				DOM.readyEvents.push(fn);
			}
		}
	}
})());