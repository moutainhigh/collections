/*
 * TRS WCM JSLib 6.1.0.1(2008-09-17)
 * Copyright(c) 2008, TRS WCM.
 */

var WCMConstants = {
	DEBUG : true,
	WCM_APPNAME : 'wcm',
	WCM6_PATH : '/wcm/app/',
	WCM_LOCAL_URL : '/wcm/app/localxml/',
	WCM_ROMOTE_URL : '/wcm/center.do',
	WCM_NOT_LOGIN_PAGE : '/wcm/console/include/not_login.htm'
}
var MyExt = Ext = (function(){
    var ua = navigator.userAgent.toLowerCase();
	var isStrict = document.compatMode == "CSS1Compat",
	isOpera = ua.indexOf("opera") > -1,
	isSafari = (/webkit|khtml/).test(ua),
	isSafari3 = isSafari && ua.indexOf('webkit/5') != -1,
	isIE = !isOpera && ua.indexOf("msie") > -1,
	isIE7 = !isOpera && ua.indexOf("msie 7") > -1,
	isGecko = !isSafari && ua.indexOf("gecko") > -1,
	isGecko3 = !isSafari && ua.indexOf("rv:1.9") > -1,
	isBorderBox = isIE && !isStrict,
	isWindows = (ua.indexOf("windows") != -1 || ua.indexOf("win32") != -1),
	isMac = (ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") != -1),
	isAir = (ua.indexOf("adobeair") != -1),
	isLinux = (ua.indexOf("linux") != -1),
	isSecure = window.location.href.toLowerCase().indexOf("https") === 0;

	// remove css image flicker
	if(isIE && !isIE7){
		try{
			document.execCommand("BackgroundImageCache", false, true);
		}catch(e){}
	}
	return {
        isOpera : isOpera,
        isSafari : isSafari,
        isSafari3 : isSafari3,
        isSafari2 : isSafari && !isSafari3,
        isIE : isIE,
        isIE6 : isIE && !isIE7,
        isIE7 : isIE7,
        isGecko : isGecko,
        isGecko2 : isGecko && !isGecko3,
        isGecko3 : isGecko3,
        isBorderBox : isBorderBox,
        isLinux : isLinux,
        isWindows : isWindows,
        isMac : isMac,
        isAir : isAir,
        useShims : ((isIE && !isIE7) || (isMac && isGecko && !isGecko3)),
		isStrict : isStrict,
        isSecure : isSecure,
        emptyFn : function(){},
		ns : function(){
            var a=arguments, o=null, i, j, d, rt;
            for (i=0; i<a.length; ++i) {
                d=a[i].split(".");
                rt = d[0];
                eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = ' + rt + ';');
                for (j=1; j<d.length; ++j) {
                    o[d[j]]=o[d[j]] || {};
                    o=o[d[j]];
                }
            }
        },
		applyIf : function(o, c){
			if(o && c){
				for(var p in c){
					if(typeof o[p] == "undefined"){ o[p] = c[p]; }
				}
			}
			return o;
		},
		apply : function(o, c){
			if(o && c){
				for(var p in c){
					o[p] = c[p];
				}
			}
			return o;
		},
		$break : {},
		isDebug : function(){
			return (window.WCMConstants && WCMConstants.DEBUG) 
						|| !!getParameter('isdebug', top.location.search);
		},
		errorMsg : function(_Error){
			if(Ext.isIE){
				var oCaller = arguments.caller;			
				var tmpStack = [];
				while(oCaller&&oCaller.callee){
					tmpStack.push(oCaller.callee);
					oCaller = oCaller.caller;
					if (!oCaller||tmpStack.include(oCaller.callee)) {
						break;
					}
				}
				return _Error.message + '\n' + tmpStack.join('\n------------------------\n');
			}
			else{
				return _Error.message + '\n' + _Error.stack;
			}
		},
		isArray : function(v){
			return v && typeof v.length == 'number' && typeof v.splice == 'function';
		},
		isFunction : function(_object){
			return typeof _object=="function";
		},
		isString : function(_object){
			return (typeof _object=="string")||
				((_object!=null)&&(_object.constructor!=null)&&(_object.constructor==String||_object.constructor.toString().trim().indexOf('function String()')==0));
		},
		isNumber : function(_object){
			return (typeof _object=="number")||
				((_object!=null)&&(_object.constructor!=null)&&(_object.constructor==Number||_object.constructor.toString().trim().indexOf('function Number()')==0));
		},
		isBoolean : function(_object){
			return (typeof _object=="boolean")||
				((_object!=null)&&(_object.constructor!=null)&&(_object.constructor==Boolean||_object.constructor.toString().trim().indexOf('function Boolean()')==0));
		},
		isSimpType : function(_object){
			return this.isString(_object) || this.isNumber(_object) || this.isBoolean(_object);
		}
	};
})();
if(!window.$){
	function $(element) {
		if (arguments.length > 1) {
			for (var i = 0, elements = [], length = arguments.length; i < length; i++)
				elements.push($(arguments[i]));
			return elements;
		}
		if(element==null)return null;
		if(element.nodeName && element.nodeType)
			return element;
		return document.getElementById(element) || document.getElementsByName(element)[0] || element;
	}
}
var $A = function(iterable) {
  if (!iterable) return [];
  if (iterable.toArray) {
    return iterable.toArray();
  } else {
    var results = [];
    for (var i = 0; i < iterable.length; i++)
      results.push(iterable[i]);
    return results;
  }
}
Function.prototype.bind = function() {
  var __method = this, args = $A(arguments), object = args.shift();
  return function() {
    return __method.apply(object, args.concat($A(arguments)));
  }
}
function getParameter(_sName, _sQuery){
	if(_sName ==null ||_sName=='undefined') 
		return '';
	var query = _sQuery || location.search;
	if(query == null || query.length==0) return '';
	var arr = query.substring(1).split('&');
	_sName = _sName.toUpperCase();
	for (var i=0,n=arr.length; i<n; i++){
		if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
			return arr[i].substring(_sName.length + 1);
		}
	}
	return '';
}
var Try = Ext.Try = {
  these : function() {
    var returnValue;

    for (var i = 0; i < arguments.length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) {}
    }

    return returnValue;
  }
}

Ext.applyIf(String.prototype, {
	lTrim : function () {
		return this.replace(/^\s*/, "");
	},
	rTrim : function () {
		return this.replace(/\s*$/, "");
	},
	trim : function () {
		return this.rTrim().lTrim();
	},
	endsWith : function(sEnd) {
		return (this.substr(this.length-sEnd.length)==sEnd);
	},
	startsWith : function(sStart) {
		return (this.substr(0,sStart.length)==sStart);
	},
	equals : function(_sc){
		return this==_sc;
	},
	equalsI : function(_sc){
		return this.toLowerCase()==_sc.toLowerCase();
	},
	byteLength : function(){
		var length = 0;
		this.replace(/[^\x00-\xff]/g,function(){length++;});
		return this.length+length;
	},
	parseQuery : function() {
		var pairs = this.match(/^\??(.*)$/)[1].split('&');
		return pairs.inject({}, function(params, pairString) {
			var pair = pairString.split('=');
			params[pair[0]] = pair[1];
			return params;
		});
	}
});
Ext.applyIf(String, {
	escape : function(string) {
        return string.replace(/('|\\)/g, "\\$1");
    },
    leftPad : function (val, size, ch) {
        var result = new String(val);
        if(!ch) {
            ch = " ";
        }
        while (result.length < size) {
            result = ch + result;
        }
        return result.toString();
    },
    format : function(format){
        var args = Array.prototype.slice.call(arguments, 1);
        return format.replace(/\{(\d+)\}/g, function(m, i){
            return args[i];
        });
    }
});
Ext.applyIf(Array.prototype, {
	each: function(iterator) {
		try {
			for (var i = 0; i < this.length; i++){
				iterator(this[i], i);
			}
		} catch (e) {
			if (e != Ext.$break) throw e;
		}
	},
	inject: function(memo, iterator) {
		this.each(function(value, index) {
			memo = iterator(memo, value, index);
		});
		return memo;
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
	includeI : function(_object){
		if(Ext.isString(_object)){
			var matched = false;
			_object = _object.toLowerCase()
			this.each(function(_element){
				if(!Ext.isString(_element))return;
				matched = _element.toLowerCase()==_object;
				if(matched)throw $break;
			});
			return matched;
		}
		return this.include(_object);
	}
});
//ԭ��������
Array.isArray = Ext.isArray;
Number.isNumber = Ext.isNumber;
Boolean.isBoolean = Ext.isBoolean;
String.isString = Ext.isString;
Object.isObject = Ext.isObject = function(_object){
	return _object && !Ext.isSimpType(_object)
				&& !Ext.isArray(_object) && !Ext.isFunction(_object)
				&& !_object.nodename && !_object.nodetype;
}
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
		case '<': // ת����< --> &lt;
			result += '&lt;';
			break;
		case '>': // ת����> --> &gt;
			result += '&gt;';
			break;
		case '"': // ת����" --> &quot;
			result += '&quot;';
			break;
		default:
			result += cTemp;
		}// case
	}// end for
	return result;
}
function $trans2Html(_sContent, _bChangeBlank) {
	if (_sContent == null)
		return '';

	var nLen = _sContent.length;
	if (nLen == 0)
		return '';

	var result = '';
	for (var i = 0; i < nLen; i++) {
		var cTemp = _sContent.charAt(i);
		switch (cTemp) {
		case ' ':
			result += _bChangeBlank ? '&nbsp;' : ' ';
			break;
		case '<':
			result += '&lt;';
			break;
		case '>':
			result += '&gt;';
			break;
		case '\n':
			result += '<br>';
			break;
		case '"':
			result += '&quot;';
			break;
		case '&': 
			// ����unicode����
			var bUnicode = false;
			for (var j = (i + 1); j < nLen && !bUnicode; j++) {
				cTemp = _sContent.charAt(i);
				if (cTemp == '#' || cTemp == ';') {
					result += '&';
					bUnicode = true;
				}
			}
			if (!bUnicode)
				result += '&amp;';
			break;
		case 9: // Tab
			result += _bChangeBlank ? '&nbsp;&nbsp;&nbsp;&nbsp;'
					: '    ';
			break;

		default:
			result += cTemp;
		}
	}
	return result;
}
var Cookies = {};
Cookies.set = function(name, value){
     var argv = arguments;
     var argc = arguments.length;
     var expires = (argc > 2) ? argv[2] : null;
     var path = (argc > 3) ? argv[3] : '/';
     var domain = (argc > 4) ? argv[4] : null;
     var secure = (argc > 5) ? argv[5] : false;
     document.cookie = name + "=" + escape (value) +
       ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
       ((path == null) ? "" : ("; path=" + path)) +
       ((domain == null) ? "" : ("; domain=" + domain)) +
       ((secure == true) ? "; secure" : "");
};

Cookies.get = function(name){
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	var j = 0;
	while(i < clen){
		j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return Cookies.getCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if(i == 0)
			break;
	}
	return null;
};

Cookies.clear = function(name) {
  if(Cookies.get(name)){
    document.cookie = name + "=" +
    "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
};

Cookies.getCookieVal = function(offset){
   var endstr = document.cookie.indexOf(";", offset);
   if(endstr == -1){
       endstr = document.cookie.length;
   }
   return unescape(document.cookie.substring(offset, endstr));
};

var Event = {
  KEY_BACKSPACE: 8,
  KEY_TAB:       9,
  KEY_RETURN:   13,
  KEY_ESC:      27,
  KEY_LEFT:     37,
  KEY_UP:       38,
  KEY_RIGHT:    39,
  KEY_DOWN:     40,
  KEY_DELETE:   46,

  element: function(event) {
    return event.target || event.srcElement;
  },

  isLeftClick: function(event) {
    return (((event.which) && (event.which == 1)) ||
            ((event.button) && (event.button == 1)));
  },

  pointerX: function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },

  pointerY: function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
  },

  stop: function(event) {
    if (event.preventDefault) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      event.returnValue = false;
      event.cancelBubble = true;
    }
  },

  // find the first node with the given tagName, starting from the
  // node the event was triggered on; traverses the DOM upwards
  findElement: function(event, tagName) {
    var element = Event.element(event);
    while (element.parentNode && (!element.tagName ||
        (element.tagName.toUpperCase() != tagName.toUpperCase())))
      element = element.parentNode;
    return element;
  },

  observers: false,

  _observeAndCache: function(element, name, observer, useCapture) {
    if (!this.observers) this.observers = [];
    if (element.addEventListener) {
      this.observers.push([element, name, observer, useCapture]);
      element.addEventListener(name, observer, useCapture);
    } else if (element.attachEvent) {
      this.observers.push([element, name, observer, useCapture]);
      element.attachEvent('on' + name, observer);
    }
  },

  unloadCache: function() {
    if (!Event.observers) return;
    for (var i = 0; i < Event.observers.length; i++) {
      Event.stopObserving.apply(this, Event.observers[i]);
      Event.observers[i][0] = null;
    }
    Event.observers = false;
  },

  observe: function(element, name, observer, useCapture) {
    var element = $(element);
    useCapture = useCapture || false;

    if (name == 'keypress' &&
        (navigator.appVersion.match(/Konqueror|Safari|KHTML/)
        || element.attachEvent))
      name = 'keydown';

    this._observeAndCache(element, name, observer, useCapture);
  },

  stopObserving: function(element, name, observer, useCapture) {
    var element = $(element);
    useCapture = useCapture || false;

    if (name == 'keypress' &&
        (navigator.appVersion.match(/Konqueror|Safari|KHTML/)
        || element.detachEvent))
      name = 'keydown';

    if (element.removeEventListener) {
      element.removeEventListener(name, observer, useCapture);
    } else if (element.detachEvent) {
      element.detachEvent('on' + name, observer);
    }
  }
};


Event.observe(window, 'unload', Event.unloadCache, false);
if(Ext.isIE) {
    function fnCleanUp() {
        var p = Function.prototype;
        delete p.bind;
        window.detachEvent("onunload", fnCleanUp);
    }
    window.attachEvent("onunload", fnCleanUp);
}

Ext.ns('Element');
Ext.applyIf(Element, {
	update : function(_el, html, loadScripts, callback){
//		Ext.fly(_el).update(html);
		_el = $(_el);
        if(typeof html == "undefined"){
            html = "";
        }
        if(loadScripts !== true){
            _el.innerHTML = html;
            if(typeof callback == "function"){
                callback();
            }
            return _el;
        }
        var dom = _el;

        setTimeout(function(){
            var hd = document.getElementsByTagName("head")[0];
            var re = /(?:<script([^>]*)?>)((\n|\r|.)*?)(?:<\/script>)/ig;
            var srcRe = /\ssrc=([\'\"])(.*?)\1/i;
            var typeRe = /\stype=([\'\"])(.*?)\1/i;

            var match;
            while(match = re.exec(html)){
                var attrs = match[1];
                var srcMatch = attrs ? attrs.match(srcRe) : false;
                if(srcMatch && srcMatch[2]){
                   var s = document.createElement("script");
                   s.src = srcMatch[2];
                   var typeMatch = attrs.match(typeRe);
                   if(typeMatch && typeMatch[2]){
                       s.type = typeMatch[2];
                   }
                   hd.appendChild(s);
                }else if(match[2] && match[2].length > 0){
                    if(window.execScript) {
                       window.execScript(match[2]);
                    } else {
                       window.eval(match[2]);
                    }
                }
            }
            if(typeof callback == "function"){
                callback();
            }
        }, 10);
        dom.innerHTML = html.replace(/(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)/ig, "");
        return dom;
	},
	visible: function(element) {
		return $(element).style.display != 'none';
	},
	toggle: function(element) {
		element = $(element);
		return Element[Element.visible(element) ? 'hide' : 'show'](element);
	},
	hide: function(element) {
		element = $(element);
		element.style.display = 'none';
		return element;
	},
	show: function(element) {
		element = $(element);
		element.style.display = '';
		return element;
	},
	_removeNode : Ext.isIE ? function(){
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
	},
	remove : function(_el){
		Element._removeNode($(_el));
//		Ext.fly(_el).remove();
	},
	addClassName : function(_el, className){
		_el = $(_el);
        if(Ext.isArray(className)){
            for(var i = 0, len = className.length; i < len; i++) {
            	this.addClassName(className[i]);
            }
        }else{
            if(className && !this.hasClassName(className)){
                _el.className = _el.className + " " + className;
            }
        }
        return _el;
	},
	removeClassName : function(_el, _cls){
//		return Ext.fly(_el).removeClass(_cls);
		_el = $(_el);
        if(!_cls || !_el.className){
            return _el;
        }
		if(Ext.isArray(_cls)){
            for(var i = 0, len = _cls.length; i < len; i++) {
            	this.removeClassName(_cls[i]);
            }
        }else{
            if(this.hasClassName(_cls)){
                var re = new RegExp('(?:^|\\s+)' + _cls + '(?:\\s+|$)', "g");
                _el.className =
                    _el.className.replace(re, " ");
            }
        }
		return _el;
	},
	hasClassName : function(_el, _cls){
//		return Ext.fly(_el).hasClass(_cls);
		_el = $(_el);
		return _cls && (' '+_el.className+' ').indexOf(' '+_cls.trim()+' ')!=-1;
	},
	replaceClassName : function(_el, _clsOld, _clsNew){
        this.removeClassName(_clsOld);
        this.addClassName(_clsNew);
	}
});
Ext.ns('wcm.LANG');
var WCMLANG = wcm.LANG = {};
Object.each = function(object, iterator){
	try {
		for (var sName in object){
			iterator(object[sName], sName);
		}
	} catch (e) {
		if (e != Ext.$break) throw e;
	}
};

var Form = {
	serialize: function(form) {
		var elements = Form.getElements($(form));
		var queryComponents = new Array();

		for (var i = 0; i < elements.length; i++) {
			var queryComponent = Form.Element.serialize(elements[i]);
			if (queryComponent)
				queryComponents.push(queryComponent);
		}

		return queryComponents.join('&');
	},

	getElements: function(form) {
		form = $(form);
		var elements = new Array();
		for (tagName in Form.Element.Serializers) {
			var tagElements = form.getElementsByTagName(tagName);
			for (var j = 0; j < tagElements.length; j++){
				elements.push(tagElements[j]);
			}
		}
		return elements;
	},

	getInputs: function(form, typeName, name) {
		form = $(form);
		var inputs = form.getElementsByTagName('input');

		if (!typeName && !name)
			return inputs;

		var matchingInputs = new Array();
		for (var i = 0; i < inputs.length; i++) {
			var input = inputs[i];
			if ((typeName && input.type != typeName) ||
			  (name && input.name != name))
			continue;
			matchingInputs.push(input);
		}

		return matchingInputs;
	},

	disable: function(form) {
		var elements = Form.getElements(form);
		for (var i = 0; i < elements.length; i++) {
			var element = elements[i];
			element.blur();
			element.disabled = 'true';
		}
	},

	enable: function(form) {
		var elements = Form.getElements(form);
		for (var i = 0; i < elements.length; i++) {
			var element = elements[i];
			element.disabled = '';
		}
	},

	findFirstElement: function(form) {
		return Form.getElements(form).find(function(element) {
			return element.type != 'hidden' && !element.disabled &&
				['input', 'select', 'textarea'].includeI(element.tagName);
		});
	},

	reset: function(form) {
		$(form).reset();
	}
}

Form.Element = {
	serialize: function(element) {
		element = $(element);
		var method = element.tagName.toLowerCase();
		var parameter = Form.Element.Serializers[method](element);

		if (parameter) {
			var key = encodeURIComponent(parameter[0]);
			if (key.length == 0) return;

			if (parameter[1].constructor != Array)
				parameter[1] = [parameter[1]];

			return parameter[1].map(function(value) {
				return key + '=' + encodeURIComponent(value);
				}).join('&');
		}
	},

	getValue: function(element) {
		element = $(element);
		var method = element.tagName.toLowerCase();
		var parameter = Form.Element.Serializers[method](element);

		if (parameter)
			return parameter[1];
	}
}

Form.Element.Serializers = {
	input: function(element) {
		switch (element.type.toLowerCase()) {
			case 'submit':
			case 'hidden':
			case 'password':
			case 'text':
				return Form.Element.Serializers.textarea(element);
			case 'checkbox':
			case 'radio':
				return Form.Element.Serializers.inputSelector(element);
		}
		return false;
	},

	inputSelector: function(element) {
		var sIsBoolean = element.getAttribute('isboolean', 2);
		if (sIsBoolean != null && 
			( (sIsBoolean = sIsBoolean.trim().toLowerCase()) == '1' || sIsBoolean == 'true' )){
			return [element.name, element.checked ? '1' : '0'];
		}
		if (element.checked)
			return [element.name, element.value];
	},

	textarea: function(element) {
		var sIsTrans2Html = element.getAttribute('transHtml', 2);
		if (sIsTrans2Html != null &&
			( (sIsTrans2Html = sIsTrans2Html.trim().toLowerCase()) == '1' || sIsTrans2Html == 'true' )){
			return [element.name, $transHtml(element.value)];
		}
		return [element.name, element.value];
	},

	select: function(element) {
		return Form.Element.Serializers[element.type == 'select-one' ?
			'selectOne' : 'selectMany'](element);
	},

	selectOne: function(element) {
		var value = '', opt, index = element.selectedIndex;
		if (index >= 0) {
			opt = element.options[index];
			value = opt.value;
			if (!value && !('value' in opt))
				value = opt.text;
		}
		return [element.name, value];
	},

	selectMany: function(element) {
		var value = new Array();
		for (var i = 0; i < element.length; i++) {
			var opt = element.options[i];
			if (opt.selected) {
				var optValue = opt.value;
				if (!optValue && !('value' in opt)){
					optValue = opt.text;
				}
				value.push(optValue);
			}
		}
		return [element.name, value];
	}
}
var $F = Form.Element.getValue;
function $$F(_sName){
    var elements = document.getElementsByName(_sName);
	var values = [];
	for(var i=0; i<elements.length; i++){
		var element = elements[i];
		var method = element.tagName.toLowerCase();
		var parameter = Form.Element.Serializers[method](element);
		if (parameter)
		  values.push(parameter[1]);
	}
	return values.join(',');
}

// Create the loadXML method and xml getter for Mozilla
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

	Document.prototype.__defineGetter__( "xml",
	   function () {
		   return (new XMLSerializer()).serializeToString(this);
	   }
	 );
}
Ext.ns('Ext.Xml', 'com.trs.util.XML', 'Ext.Json', 'com.trs.util.JSON');

Ext.Xml = com.trs.util.XML = {
	trimElements : function(elements){
		var newElements=[];
		for(var i=0; elements&&i<elements.length; i++){
			if(elements[i].nodeName!='#text'&&elements[i].nodeName!='#comment'){
				newElements.push(elements[i]);
			}
		}
		return newElements;
	},
	loadXML : function(str){
		var xmlDoc = Try.these(
		  function() {return new ActiveXObject('Microsoft.XMLDOM');},
		  function() {return document.implementation.createDocument("","",null);}
		) || false;
		xmlDoc.loadXML(str);
		return xmlDoc;
	},
	writeXML : function(_file, _sXML) {
		if(Ext.isIE){
			var _lo=location.href.replace(/\/[^\/]*$/,'/');
			_file= _lo + _file;
			_file = _file.replace(/file:(\/)*/g,'').replace(/\//g,'\\');
			_file = unescape(_file);
			try {
				var fso = new ActiveXObject("Scripting.FileSystemObject");
				var oTextFile = fso.CreateTextFile(_file,true,true);
				oTextFile.Write(_sXML);
				oTextFile.Close();
			} catch(e) {
				throw new Error("д�ļ�'"+file+"'ʧ��:" + e.message);
			}
			return;
		}
		_file = _file.replace(/file:(\/)*/g,'').replace(/\//g,'\\');
		_file = unescape(_file);
		try {
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserWrite");
			var file = Components.classes["@mozilla.org/file/local;1"]
							.createInstance(Components.interfaces.nsILocalFile);
			file.initWithPath(_file);
			if (!file.exists())
				file.create(0, 0664);
			var outputStream = Components.classes["@mozilla.org/network/file-output-stream;1"]
							.createInstance(Components.interfaces.nsIFileOutputStream);
			outputStream.init(file, 0x04|0x08|0x20, 420, 0);
			var sXML = this._convertFromUnicode("UTF8", _sXML);
			outputStream.write(sXML, sXML.length);
			outputStream.flush();
			outputStream.close();
		} catch(e) {
			throw new Error("д�ļ�'"+_file+"'ʧ��:" + e.message);
		}
	},
	_convertFromUnicode : function(_sCharset, _sSrc){ 
		try {
			var unicodeConverter = Components.classes["@mozilla.org/intl/scriptableunicodeconverter"]
						.createInstance(Components.interfaces.nsIScriptableUnicodeConverter); 
			unicodeConverter.charset = _sCharset; 
			return unicodeConverter.ConvertFromUnicode( _sSrc ); 
		}catch(e){
		}
		return _sSrc; 
	},
	toString : function(_oXmlDoc){
		return _oXmlDoc.xml;
	}
};
Ext.Json = com.trs.util.JSON = {
	_json : function(_oJson, _sXPath, bCaseSensitive){
		var oRstJson = _oJson;
		if(_sXPath){
			try{
				var sXPath = _sXPath.trim();
				if(bCaseSensitive!=true){
					sXPath = sXPath.toUpperCase();
				}
				var arrXPaths = sXPath.split('.');
				for(var i=0; i<arrXPaths.length; i++){
					oRstJson = oRstJson[arrXPaths[i]];
				}
			}catch(err){
				return false;
			}
		}
		return oRstJson;
	},
	value : function(_oJson, _sXPath, bCaseSensitive){
		var oRstJson = this._json(_oJson, _sXPath, bCaseSensitive);
		return oRstJson==null ? null : (oRstJson['NODEVALUE']==null?oRstJson:oRstJson['NODEVALUE']);
	},
	array : function(_oJson,_sXPath,bCaseSensitive){
		var oRstJson = this._json(_oJson, _sXPath, bCaseSensitive);
		return oRstJson==null ? [] : (
				Ext.isArray(oRstJson) ? oRstJson : [oRstJson]
			);
	},
	parseXml : function(xml){
		var root = xml.documentElement;
		if(root == null) return null;
		var json = this.parseElement(root);
		var vReturn = {};
		vReturn[root.nodeName.toUpperCase()] = json;
		return vReturn;
	},
	parseElement : function(ele){
		var json = {};
		if(ele == null) {
			return json;
		}
		var attrs = ele.attributes;
		for(var i=0;i<attrs.length;i++){
			json[attrs[i].nodeName.toUpperCase()] = attrs[i].nodeValue.trim();
		}
		var childs = ele.childNodes;
		var hasNodeChild = false;
		for(var i=0;i<childs.length;i++){
			var tmpNodeName = childs[i].nodeName.toUpperCase();
			switch(tmpNodeName){
				case '#TEXT':
					var tmpNodeValue = childs[i].nodeValue;//.trim();
					if(tmpNodeValue!=''){
						json['NODEVALUE'] = tmpNodeValue;
					}
					break;
				case '#COMMENT':
					break;
				case '#CDATA-SECTION':
					var tmpNodeValue = childs[i].nodeValue;//.trim();
					json['NODEVALUE'] = tmpNodeValue;
					break;
				default:
					hasNodeChild = true;
					var a = json[tmpNodeName];
					var b = this.parseElement(childs[i]);
					if(!a){
						json[tmpNodeName]=b;
						break;
					}
					if(Ext.isArray(a)){
						a.push(b);
					}
					else{
						json[tmpNodeName] = [a,b];
					}
					break;
			}
		}
		if(!hasNodeChild && !json['NODEVALUE']){
			json['NODEVALUE'] = '';
		}
		return json;
	},
	parseJson2Xml : function(tag, jsonObject, _bAllwaysNode){
		var myDoc = com.trs.util.XML.loadXML('<'+tag+'></'+tag+'>');
		var eRoot = myDoc.documentElement;
		this.parseJson2Element(myDoc, eRoot, jsonObject, null, false, _bAllwaysNode);
		return myDoc;
	},
	parseJson2Element : function(xmlDoc, _currElement, _object,
							_currProp, _bLeafNode, _bAllwaysNode){
		var oValue = _object;
		var currElement = _currElement;
		if(oValue == null)return;
		if(Ext.isFunction(oValue))return;//Function
		if(Ext.isSimpType(oValue)){//Attribute or leaf Node
			var sValue = '' + oValue;
			if(!_bLeafNode && _bAllwaysNode != true){
				if(!Ext.isEmpty(_currProp)) {
					currElement.setAttribute(_currProp, sValue);
				}
				return;
			}
			var hasCDATA = sValue.match(/<!\[CDATA\[.*\]\]>/mg);
			var eleValue = hasCDATA ? xmlDoc.createTextNode(sValue) 
					: xmlDoc.createCDATASection(sValue);
			if(_bAllwaysNode) {
				var	childElement = xmlDoc.createElement(_currProp);
				currElement.appendChild(childElement);
				childElement.appendChild(eleValue);
			}else{
				currElement.appendChild(eleValue);
			}
			return;
		}
		var func = arguments.callee;
		var childElement = currElement;
		if(!Ext.isEmpty(_currProp)){
			childElement = xmlDoc.createElement(_currProp);
			currElement.appendChild(childElement);
		}
		if(Ext.isArray(oValue)){//Array
			oValue.each(function(_object){
				func(xmlDoc, childElement, _object, null, null, _bAllwaysNode);
			});
			return;
		}
		//Object
		Object.each(oValue, function(_object, prop){
			func(xmlDoc, childElement, oValue[prop], prop, 
				prop.equalsI('NODEVALUE'), _bAllwaysNode);
		});
	},
	parseJsonToParams : function(jsonObject){
		if(jsonObject==null)
			return '';
		if(Ext.isSimpType(jsonObject))
			return jsonObject + '';
		var vReturn = [];
		Object.each(jsonObject, function(value){
			vReturn.push(vTmp + '='
				+ encodeURIComponent(value['NODEVALUE'] || value));
		});
		return vReturn.join('&');
	},
	toUpperCase : function(_simpleJson){
		if(Ext.isEmpty(_simpleJson) || Ext.isFunction(_simpleJson)){
			return "";
		}
		if(Ext.isSimpType(oValue)){
			return _simpleJson;
		}
		var callee = arguments.callee;
		if(Ext.isArray(_simpleJson)) {
			return _simpleJson.map(function(value){
				callee(value);
			});
		}
		var retJson = {};
		for(var name in _simpleJson){
			retJson[name.toUpperCase()] = callee(_simpleJson[name]);
		}
		return retJson;
	},
	eval : function(_sJson){
		try{
			eval("var json = " + _sJson);
			return this.toUpperCase(json);
		}catch(err){
			Ext.Msg.d$error(err);
		}
	}
};

$v = Ext.Json.value;
$a = Ext.Json.array;

if(!document.getElementsByClassName){
	document.getElementsByClassName = function(className, parentElement) {
		var children = ($(parentElement) || document.body).getElementsByTagName('*');
		return $A(children).inject([], function(elements, child) {
				if (child.className.match(new RegExp("(^|\\s)" + className + "(\\s|$)")))
					elements.push(child);
				return elements;
			}
		);
	}
	Event.observe(window, 'unload', function(){
		document.getElementsByClassName = null;
	});
}
Ext.applyIf(Object, {
	extend : function(){
		Ext.apply.apply(this, arguments);
	}
});

var Insertion = {
  Before: function(element, content) {
    return Ext.get(element).insertHtml('beforeBegin', content);
  },

  Top: function(element, content) {
    return Ext.get(element).insertHtml('afterBegin', content);
  },

  Bottom: function(element, content) {
    return Ext.get(element).insertHtml('beforeEnd', content);
  },

  After: function(element, content) {
    return Ext.get(element).insertHtml('afterEnd', content);
  }
};
