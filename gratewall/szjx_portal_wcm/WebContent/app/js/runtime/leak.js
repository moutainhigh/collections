Ext = {version: '2.2'};
window["undefined"] = window["undefined"];
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
		if(!Ext.isString(element))
			return element;
		return document.getElementById(element) || document.getElementsByName(element)[0];
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

Ext.apply = function(o, c, defaults){
    if(defaults){
        Ext.apply(o, defaults);
    }
    if(o && c && typeof c == 'object'){
        for(var p in c){
            o[p] = c[p];
        }
    }
    return o;
};
(function(){
    var idSeed = 0;
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
    Ext.apply(Ext, {
        isStrict : isStrict,
        isSecure : isSecure,
        isReady : false,
        enableGarbageCollector : true,
        enableListenerCollection:false,
        SSL_SECURE_URL : "javascript:false",
        emptyFn : function(){},
        applyIf : function(o, c){
            if(o && c){
                for(var p in c){
                    if(typeof o[p] == "undefined"){ o[p] = c[p]; }
                }
            }
            return o;
        },
        id : function(el, prefix){
            prefix = prefix || "ext-gen";
            el = Ext.getDom(el);
            var id = prefix + (++idSeed);
            return el ? (el.id ? el.id : (el.id = id)) : id;
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
            if(overrides){
                var p = origclass.prototype;
                for(var method in overrides){
                    p[method] = overrides[method];
                }
            }
        },
        namespace : function(){
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
        urlEncode : function(o){
            if(!o){
                return "";
            }
            var buf = [];
            for(var key in o){
                var ov = o[key], k = encodeURIComponent(key);
                var type = typeof ov;
                if(type == 'undefined'){
                    buf.push(k, "=&");
                }else if(type != "function" && type != "object"){
                    buf.push(k, "=", encodeURIComponent(ov), "&");
                }else if(Ext.isArray(ov)){
                    if (ov.length) {
	                    for(var i = 0, len = ov.length; i < len; i++) {
	                        buf.push(k, "=", encodeURIComponent(ov[i] === undefined ? '' : ov[i]), "&");
	                    }
	                } else {
	                    buf.push(k, "=&");
	                }
                }
            }
            buf.pop();
            return buf.join("");
        },
        urlDecode : function(string, overwrite){
            if(!string || !string.length){
                return {};
            }
            var obj = {};
            var pairs = string.split('&');
            var pair, name, value;
            for(var i = 0, len = pairs.length; i < len; i++){
                pair = pairs[i].split('=');
                name = decodeURIComponent(pair[0]);
                value = decodeURIComponent(pair[1]);
                if(overwrite !== true){
                    if(typeof obj[name] == "undefined"){
                        obj[name] = value;
                    }else if(typeof obj[name] == "string"){
                        obj[name] = [obj[name]];
                        obj[name].push(value);
                    }else{
                        obj[name].push(value);
                    }
                }else{
                    obj[name] = value;
                }
            }
            return obj;
        },
        escapeRe : function(s) {
            return s.replace(/([.*+?^${}()|[\]\/\\])/g, "\\$1");
        },
        // internal
        callback : function(cb, scope, args, delay){
            if(typeof cb == "function"){
                if(delay){
                    cb.defer(delay, scope, args || []);
                }else{
                    cb.apply(scope, args || []);
                }
            }
        },
        getDom : function(el){
            if(!el || !document){
                return null;
            }
            return el.dom ? el.dom : (typeof el == 'string' ? document.getElementById(el) : el);
        },
        getDoc : function(){
            return Ext.get(document);
        },
        getBody : function(){
            return Ext.get(document.body || document.documentElement);
        },
        num : function(v, defaultValue){
            if(typeof v != 'number'){
                return defaultValue;
            }
            return v;
        },
        destroy : function(){
            for(var i = 0, a = arguments, len = a.length; i < len; i++) {
                var as = a[i];
                if(as){
		            if(typeof as.destroy == 'function'){
		                as.destroy();
		            }
		            else if(as.dom){
		                as.removeAllListeners();
		                as.remove();
		            }
                }
            }
        },
        removeNode : isIE ? function(){
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
        // inpired by a similar function in mootools library
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
        value : function(v, defaultValue, allowBlank){
            return Ext.isEmpty(v, allowBlank) ? defaultValue : v;
        },
		isArray : function(v){
			return v && typeof v.length == 'number' && typeof v.splice == 'function';
		},
		isDate : function(v){
			return v && typeof v.getFullYear == 'function';
		},
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
	    
        useShims : ((isIE && !isIE7) || (isMac && isGecko && !isGecko3))
    });
    // in intellij using keyword "namespace" causes parsing errors
    Ext.ns = Ext.namespace;
})();

// prevent IE leaks
if(Ext.isIE) {
    function fnCleanUp() {
        var p = Function.prototype;
        delete p.createSequence;
        delete p.defer;
        delete p.bind;
        delete p.createDelegate;
        delete p.createCallback;
        delete p.createInterceptor;
		delete p.bindAsEventListener;
	window.detachEvent("onunload", fnCleanUp);
    }
    window.attachEvent("onunload", fnCleanUp);
}
Ext.ns("Ext", "Ext.util", "Ext.lib");
Ext.applyIf(Ext, {
	blankUrl : Ext.isSecure ? Ext.SSL_SECURE_URL : "",
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
	},
	isTrans : function(_object){
		return (typeof _object=="object") && _object.responseText;
	},
	result : function(_trans){
		var text = _trans.responseText;
		if(!text)return null;
		return text.replace(/^\s*<result>(.*)<\/result>\s*$/ig, '$1');
	}
});
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
}
Ext.apply(Function.prototype, {
    createCallback : function(){
        // make args available, in function below
        var args = arguments;
        var method = this;
        return function() {
            return method.apply(window, args);
        };
    },
	bindAsEventListener : function(obj){
		/*
        var method = this;
		var callArgs = Array.prototype.splice.call(arguments, 1);
        return function(event) {
			callArgs = [event].concat(callArgs);
			return method.apply(obj || window, callArgs);
		};*/
		var callArgs = Array.prototype.slice.call(arguments, 1);
		return this.createDelegate(obj, callArgs, 1);
	},
    createDelegate : function(obj, args, appendArgs){
        var method = this;
        return function() {
            var callArgs = args || arguments;
            if(appendArgs === true){
                callArgs = Array.prototype.slice.call(arguments, 0);
                callArgs = callArgs.concat(args);
            }else if(typeof appendArgs == "number"){
                callArgs = Array.prototype.slice.call(arguments, 0); // copy arguments first
                var applyArgs = [appendArgs, 0].concat(args); // create method call params
                Array.prototype.splice.apply(callArgs, applyArgs); // splice them in
            }
            return method.apply(obj || window, callArgs);
        };
    },
    defer : function(millis, obj, args, appendArgs){
        var fn = this.createDelegate(obj, args, appendArgs);
        if(millis){
            return setTimeout(fn, millis);
        }
        fn();
        return 0;
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
Ext.applyIf(String, {
	scriptFragment: '(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)',
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
Ext.applyIf(String.prototype, {
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
	lTrim : function () {
		return this.replace(/^\s*/, "");
	},
	rTrim : function () {
		return this.replace(/\s*$/, "");
	},
	trim : function () {
		return this.rTrim().lTrim();
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
	equals : function(_sc){
		return this==_sc;
	},
	equalsI : function(_sc){
		return _sc!=null && this.toLowerCase()==(''+_sc).toLowerCase();
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
Ext.applyIf(Number.prototype, {
    constrain : function(min, max){
        return Math.min(Math.max(this, min), max);
    }
});
Date.prototype.getElapsed = function(date) {
	return Math.abs((date || new Date()).getTime()-this.getTime());
};
Ext.apply(Array.prototype, {
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
	select: function(iterator) {
		var results = [];
		this.each(function(value, index) {
		  if (iterator(value, index))
			results.push(value);
		});
		return results;
	},
	compact: function() {
		return this.select(function(value) {
		  return value != undefined || value != null;
		});
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
			_object = _object.toLowerCase();
			try{
				this.each(function(_element){
					if(!Ext.isString(_element))return;
					matched = _element.toLowerCase()==_object;
					if(matched)throw $break;
				});
			}catch(err){
			}
			return matched;
		}
		return this.include(_object);
	}
});

Array.isArray = Ext.isArray;
Number.isNumber = Ext.isNumber;
Boolean.isBoolean = Ext.isBoolean;
String.isString = Ext.isString;
Ext.isObject = function(_object){
	return _object && !Ext.isSimpType(_object)
				&& !Ext.isArray(_object) && !Ext.isFunction(_object)
				&& !_object.nodename && !_object.nodetype;
}
Ext.toSource = function(o){
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
		case '<': 
			result += '&lt;';
			break;
		case '>': 
			result += '&gt;';
			break;
		case '"': 
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
function $toQueryStr(_oParams, _bUpper){
	var arrParams = _oParams || {};
	var arrParamsSAparts = [];
	for (var param in arrParams){
		var value = arrParams[param];
		if(!Ext.isSimpType(value) && !Ext.isArray(value))continue;
		param = _bUpper ? param.trim().toUpperCase() : param.trim();
		arrParamsSAparts.push(param + '=' + encodeURIComponent(value + ''));
	}
	return arrParamsSAparts.join('&');
}
function $toQueryStr2(_oParams, _bUpper){
	var arrParams = _oParams || {};
	var arrParamsSAparts = [];
	for (var param in arrParams){
		var value = arrParams[param];
		if(!Ext.isSimpType(value) && !Ext.isArray(value))continue;
		param = _bUpper ? param.trim().toUpperCase() : param.trim();
		arrParamsSAparts.push(param + '=' + value);
	}
	return arrParamsSAparts.join('&');
}
function $openMaxWin(_sUrl, _sName, _bReplace, _bResizable){
	var nWidth	= window.screen.width - 12;
	var nHeight = window.screen.height - 60;
	var nLeft	= 0;
	var nTop	= 0;
	var sName	= _sName || "";
	sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
	var oWin = window.open(_sUrl, sName, "resizable=" 
		+ (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" 
		+ nLeft + ",menubar =no,toolbar =no,width=" 
		+ nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no", _bReplace);
	if(oWin)oWin.focus();
	return oWin;
}
function $openCentralWin(_sUrl, _sName){
	var _WIN_WIDTH = window.screen.availWidth;
	var _WIN_HEIGHT = window.screen.availHeight;
	var y = _WIN_HEIGHT * 0.12;
	var x = _WIN_HEIGHT * 0.17;
	var w = _WIN_WIDTH - 2 * x;
	var h = w * 0.618;

	var sFeature = 'resizable=yes,top=' + y + ',left='
			+ x + ',menubar =no,toolbar =no,width=' + w + ',height='
			+ h + ',scrollbars=yes,location =no,status=no,titlebar=no';
	
	var sName	= _sName || "";
	sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
	var oWin = window.open(_sUrl, sName, sFeature);
	if(oWin) oWin.focus();
}
function $openCenterWin(_sUrl, _sName, _width, _height, _sFeature){
	if(!_width || !_height){
		$openCentralWin(_sUrl, _sName);
		return;
	}
	var _WIN_WIDTH = window.screen.availWidth;
	var _WIN_HEIGHT = window.screen.availHeight;
	var l = (_WIN_WIDTH - _width) / 2;
	var t = (_WIN_HEIGHT - _height) / 2;
	sFeature = "left="+l + ",top=" + t +",width=" 
		+ _width + ",height=" + _height + "," + _sFeature;
	var sName	= _sName || "";
	sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
	var oWin = window.open(_sUrl, sName, sFeature);
	if(oWin) oWin.focus();
}

(function() {
    var libFlyweight;
    Ext.lib.Dom = {
        getViewWidth : function(full) {
            return full ? this.getDocumentWidth() : this.getViewportWidth();
        },
        getViewHeight : function(full) {
            return full ? this.getDocumentHeight() : this.getViewportHeight();
        },
        getDocumentHeight: function() {
            var scrollHeight = (document.compatMode != "CSS1Compat") ? document.body.scrollHeight : document.documentElement.scrollHeight;
            return Math.max(scrollHeight, this.getViewportHeight());
        },
        getDocumentWidth: function() {
            var scrollWidth = (document.compatMode != "CSS1Compat") ? document.body.scrollWidth : document.documentElement.scrollWidth;
            return Math.max(scrollWidth, this.getViewportWidth());
        },
        getViewportHeight: function(){
            if(Ext.isIE){
                return Ext.isStrict ? document.documentElement.clientHeight :
                         document.body.clientHeight;
            }else{
                return self.innerHeight;
            }
        },
        getViewportWidth: function() {
            if(Ext.isIE){
                return Ext.isStrict ? document.documentElement.clientWidth :
                         document.body.clientWidth;
            }else{
                return self.innerWidth;
            }
        },
        isAncestor : function(p, c) {
            p = Ext.getDom(p);
            c = Ext.getDom(c);
            if (!p || !c) {
                return false;
            }
            if (p.contains && !Ext.isSafari) {
                return p.contains(c);
            } else if (p.compareDocumentPosition) {
                return !!(p.compareDocumentPosition(c) & 16);
            } else {
                var parent = c.parentNode;
                while (parent) {
                    if (parent == p) {
                        return true;
                    }
                    else if (!parent.tagName || parent.tagName.toUpperCase() == "HTML") {
                        return false;
                    }
                    parent = parent.parentNode;
                }
                return false;
            }
        },
        getRegion : function(el) {
            return Ext.lib.Region.getRegion(el);
        },
        getY : function(el) {
            return this.getXY(el)[1];
        },
        getX : function(el) {
            return this.getXY(el)[0];
        },
        getXY : function(el) {
            var p, pe, b, scroll, bd = (document.body || document.documentElement);
            el = Ext.getDom(el);
            if(el == bd){
                return [0, 0];
            }
            if (el.getBoundingClientRect) {
                b = el.getBoundingClientRect();
                scroll = fly(document).getScroll();
                return [b.left + scroll.left, b.top + scroll.top];
            }
            var x = 0, y = 0;
            p = el;
            var hasAbsolute = fly(el).getStyle("position") == "absolute";
            while (p) {
                x += p.offsetLeft;
                y += p.offsetTop;
                if (!hasAbsolute && fly(p).getStyle("position") == "absolute") {
                    hasAbsolute = true;
                }
                if (Ext.isGecko) {
                    pe = fly(p);
                    var bt = parseInt(pe.getStyle("borderTopWidth"), 10) || 0;
                    var bl = parseInt(pe.getStyle("borderLeftWidth"), 10) || 0;
                    x += bl;
                    y += bt;
                    if (p != el && pe.getStyle('overflow') != 'visible') {
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
                var dbd = fly(bd);
                x += parseInt(dbd.getStyle("borderLeftWidth"), 10) || 0;
                y += parseInt(dbd.getStyle("borderTopWidth"), 10) || 0;
            }
            p = el.parentNode;
            while (p && p != bd) {
                if (!Ext.isOpera || (p.tagName != 'TR' && fly(p).getStyle("display") != "inline")) {
                    x -= p.scrollLeft;
                    y -= p.scrollTop;
                }
                p = p.parentNode;
            }
            return [x, y];
        },
        setXY : function(el, xy) {
            el = Ext.fly(el, '_setXY');
            el.position();
            var pts = el.translatePoints(xy);
            if (xy[0] !== false) {
                el.dom.style.left = pts.left + "px";
            }
            if (xy[1] !== false) {
                el.dom.style.top = pts.top + "px";
            }
        },
        setX : function(el, x) {
            this.setXY(el, [x, false]);
        },
        setY : function(el, y) {
            this.setXY(el, [false, y]);
        }
    };
    Ext.lib.Event = function() {
        var loadComplete = false;
        var listeners = [];
        var unloadListeners = [];
        var retryCount = 0;
        var onAvailStack = [];
        var counter = 0;
        var lastError = null;
        return {
            POLL_RETRYS: 200,
            POLL_INTERVAL: 20,
            EL: 0,
            TYPE: 1,
            FN: 2,
            WFN: 3,
            OBJ: 3,
            ADJ_SCOPE: 4,
            _interval: null,
            startInterval: function() {
                if (!this._interval) {
                    var self = this;
                    var callback = function() {
                        self._tryPreloadAttach();
                    };
                    this._interval = setInterval(callback, this.POLL_INTERVAL);
                }
            },
            onAvailable: function(p_id, p_fn, p_obj, p_override) {
                onAvailStack.push({ id:         p_id,
                    fn:         p_fn,
                    obj:        p_obj,
                    override:   p_override,
                    checkReady: false    });
                retryCount = this.POLL_RETRYS;
                this.startInterval();
            },
            addListener: function(el, eventName, fn) {
                el = Ext.getDom(el);
                if (!el || !fn) {
                    return false;
                }
                if ("unload" == eventName) {
                    unloadListeners[unloadListeners.length] =
                    [el, eventName, fn];
                    return true;
                }
                // prevent unload errors with simple check
                var wrappedFn = function(e) {
                    return typeof Ext != 'undefined' ? fn(Ext.lib.Event.getEvent(e)) : false;
                };
                var li = [el, eventName, fn, wrappedFn];
                var index = listeners.length;
                listeners[index] = li;
                this.doAdd(el, eventName, wrappedFn, false);
                return true;
            },
            removeListener: function(el, eventName, fn) {
                var i, len;
                el = Ext.getDom(el);
                if(!fn) {
                    return this.purgeElement(el, false, eventName);
                }
                if ("unload" == eventName) {
                    for (i = 0,len = unloadListeners.length; i < len; i++) {
                        var li = unloadListeners[i];
                        if (li &&
                            li[0] == el &&
                            li[1] == eventName &&
                            li[2] == fn) {
                            unloadListeners.splice(i, 1);
                            return true;
                        }
                    }
                    return false;
                }
                var cacheItem = null;
                var index = arguments[3];
                if ("undefined" == typeof index) {
                    index = this._getCacheIndex(el, eventName, fn);
                }
                if (index >= 0) {
                    cacheItem = listeners[index];
                }
                if (!el || !cacheItem) {
                    return false;
                }
                this.doRemove(el, eventName, cacheItem[this.WFN], false);
                delete listeners[index][this.WFN];
                delete listeners[index][this.FN];
                listeners.splice(index, 1);
                return true;
            },
            getTarget: function(ev, resolveTextNode) {
                ev = ev.browserEvent || ev;
                var t = ev.target || ev.srcElement;
                return this.resolveTextNode(t);
            },
            resolveTextNode: function(node) {
                if (Ext.isSafari && node && 3 == node.nodeType) {
                    return node.parentNode;
                } else {
                    return node;
                }
            },
            getPageX: function(ev) {
                ev = ev.browserEvent || ev;
                var x = ev.pageX;
                if (!x && 0 !== x) {
                    x = ev.clientX || 0;
                    if (Ext.isIE) {
                        x += this.getScroll()[1];
                    }
                }
                return x;
            },
            getPageY: function(ev) {
                ev = ev.browserEvent || ev;
                var y = ev.pageY;
                if (!y && 0 !== y) {
                    y = ev.clientY || 0;
                    if (Ext.isIE) {
                        y += this.getScroll()[0];
                    }
                }
                return y;
            },
            getXY: function(ev) {
                ev = ev.browserEvent || ev;
                return [this.getPageX(ev), this.getPageY(ev)];
            },
            getRelatedTarget: function(ev) {
                ev = ev.browserEvent || ev;
                var t = ev.relatedTarget;
                if (!t) {
                    if (ev.type == "mouseout") {
                        t = ev.toElement;
                    } else if (ev.type == "mouseover") {
                        t = ev.fromElement;
                    }
                }
                return this.resolveTextNode(t);
            },
            getTime: function(ev) {
                ev = ev.browserEvent || ev;
                if (!ev.time) {
                    var t = new Date().getTime();
                    try {
                        ev.time = t;
                    } catch(ex) {
                        this.lastError = ex;
                        return t;
                    }
                }
                return ev.time;
            },
            stopEvent: function(ev) {
                this.stopPropagation(ev);
                this.preventDefault(ev);
            },
            stopPropagation: function(ev) {
                ev = ev.browserEvent || ev;
                if (ev.stopPropagation) {
                    ev.stopPropagation();
                } else {
                    ev.cancelBubble = true;
                }
            },
            preventDefault: function(ev) {
                ev = ev.browserEvent || ev;
                if(ev.preventDefault) {
                    ev.preventDefault();
                } else {
                    ev.returnValue = false;
                }
            },
            getEvent: function(e) {
                var ev = e || window.event;
                if (!ev) {
                    var c = this.getEvent.caller;
                    while (c) {
                        ev = c.arguments[0];
                        if (ev && Event == ev.constructor) {
                            break;
                        }
                        c = c.caller;
                    }
                }
                return ev;
            },
            getCharCode: function(ev) {
                ev = ev.browserEvent || ev;
                return ev.charCode || ev.keyCode || 0;
            },
            _getCacheIndex: function(el, eventName, fn) {
                for (var i = 0,len = listeners.length; i < len; ++i) {
                    var li = listeners[i];
                    if (li &&
                        li[this.FN] == fn &&
                        li[this.EL] == el &&
                        li[this.TYPE] == eventName) {
                        return i;
                    }
                }
                return -1;
            },
            elCache: {},
            getEl: function(id) {
                return document.getElementById(id);
            },
            clearCache: function() {
            },
            _load: function(e) {
                loadComplete = true;
                var EU = Ext.lib.Event;
                if (Ext.isIE) {
                    EU.doRemove(window, "load", EU._load);
                }
            },
            _tryPreloadAttach: function() {
                if (this.locked) {
                    return false;
                }
                this.locked = true;
                var tryAgain = !loadComplete;
                if (!tryAgain) {
                    tryAgain = (retryCount > 0);
                }
                var notAvail = [];
                for (var i = 0,len = onAvailStack.length; i < len; ++i) {
                    var item = onAvailStack[i];
                    if (item) {
                        var el = this.getEl(item.id);
                        if (el) {
                            if (!item.checkReady ||
                                loadComplete ||
                                el.nextSibling ||
                                (document && document.body)) {
                                var scope = el;
                                if (item.override) {
                                    if (item.override === true) {
                                        scope = item.obj;
                                    } else {
                                        scope = item.override;
                                    }
                                }
                                item.fn.call(scope, item.obj);
                                onAvailStack[i] = null;
                            }
                        } else {
                            notAvail.push(item);
                        }
                    }
                }
                retryCount = (notAvail.length === 0) ? 0 : retryCount - 1;
                if (tryAgain) {
                    this.startInterval();
                } else {
                    clearInterval(this._interval);
                    this._interval = null;
                }
                this.locked = false;
                return true;
            },
            purgeElement: function(el, recurse, eventName) {
                var elListeners = this.getListeners(el, eventName);
                if (elListeners) {
                    for (var i = 0,len = elListeners.length; i < len; ++i) {
                        var l = elListeners[i];
                        this.removeListener(el, l.type, l.fn);
                    }
                }
                if (recurse && el && el.childNodes) {
                    for (i = 0,len = el.childNodes.length; i < len; ++i) {
                        this.purgeElement(el.childNodes[i], recurse, eventName);
                    }
                }
            },
            getListeners: function(el, eventName) {
                var results = [], searchLists;
                if (!eventName) {
                    searchLists = [listeners, unloadListeners];
                } else if (eventName == "unload") {
                    searchLists = [unloadListeners];
                } else {
                    searchLists = [listeners];
                }
                for (var j = 0; j < searchLists.length; ++j) {
                    var searchList = searchLists[j];
                    if (searchList && searchList.length > 0) {
                        for (var i = 0,len = searchList.length; i < len; ++i) {
                            var l = searchList[i];
                            if (l && l[this.EL] === el &&
                                (!eventName || eventName === l[this.TYPE])) {
                                results.push({
                                    type:   l[this.TYPE],
                                    fn:     l[this.FN],
                                    obj:    l[this.OBJ],
                                    adjust: l[this.ADJ_SCOPE],
                                    index:  i
                                });
                            }
                        }
                    }
                }
                return (results.length) ? results : null;
            },
            _unload: function(e) {
                var EU = Ext.lib.Event, i, j, l, len, index;
                for (i = 0,len = unloadListeners.length; i < len; ++i) {
                    l = unloadListeners[i];
                    if (l) {
                        var scope = window;
                        if (l[EU.ADJ_SCOPE]) {
                            if (l[EU.ADJ_SCOPE] === true) {
                                scope = l[EU.OBJ];
                            } else {
                                scope = l[EU.ADJ_SCOPE];
                            }
                        }
                        l[EU.FN].call(scope, EU.getEvent(e), l[EU.OBJ]);
                        unloadListeners[i] = null;
                        l = null;
                        scope = null;
                    }
                }
                unloadListeners = null;
                if (listeners && listeners.length > 0) {
                    j = listeners.length;
                    while (j) {
                        index = j - 1;
                        l = listeners[index];
                        if (l) {
                            EU.removeListener(l[EU.EL], l[EU.TYPE],
                                    l[EU.FN], index);
                        }
                        j = j - 1;
                    }
                    l = null;
                    EU.clearCache();
                }
                EU.doRemove(window, "unload", EU._unload);
            },
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
            doAdd: function () {
                if (window.addEventListener) {
                    return function(el, eventName, fn, capture) {
                        el.addEventListener(eventName, fn, (capture));
                    };
                } else if (window.attachEvent) {
                    return function(el, eventName, fn, capture) {
                        el.attachEvent("on" + eventName, fn);
                    };
                } else {
                    return function() {
                    };
                }
            }(),
            doRemove: function() {
                if (window.removeEventListener) {
                    return function (el, eventName, fn, capture) {
                        el.removeEventListener(eventName, fn, (capture));
                    };
                } else if (window.detachEvent) {
                    return function (el, eventName, fn) {
                        el.detachEvent("on" + eventName, fn);
                    };
                } else {
                    return function() {
                    };
                }
            }()
        };
    }();
    var E = Ext.lib.Event;
    E.on = E.addListener;
    E.un = E.removeListener;
    if(document && document.body) {
        E._load();
    } else {
        E.doAdd(window, "load", E._load);
    }
    E.doAdd(window, "unload", E._unload);
    E._tryPreloadAttach();
    Ext.lib.Region = function(t, r, b, l) {
        this.top = t;
        this[1] = t;
        this.right = r;
        this.bottom = b;
        this.left = l;
        this[0] = l;
    };
    Ext.lib.Region.prototype = {
        contains : function(region) {
            return ( region.left >= this.left &&
                     region.right <= this.right &&
                     region.top >= this.top &&
                     region.bottom <= this.bottom    );
        },
        getArea : function() {
            return ( (this.bottom - this.top) * (this.right - this.left) );
        },
        intersect : function(region) {
            var t = Math.max(this.top, region.top);
            var r = Math.min(this.right, region.right);
            var b = Math.min(this.bottom, region.bottom);
            var l = Math.max(this.left, region.left);
            if (b >= t && r >= l) {
                return new Ext.lib.Region(t, r, b, l);
            } else {
                return null;
            }
        },
        union : function(region) {
            var t = Math.min(this.top, region.top);
            var r = Math.max(this.right, region.right);
            var b = Math.max(this.bottom, region.bottom);
            var l = Math.min(this.left, region.left);
            return new Ext.lib.Region(t, r, b, l);
        },
        constrainTo : function(r) {
            this.top = this.top.constrain(r.top, r.bottom);
            this.bottom = this.bottom.constrain(r.top, r.bottom);
            this.left = this.left.constrain(r.left, r.right);
            this.right = this.right.constrain(r.left, r.right);
            return this;
        },
        adjust : function(t, l, b, r) {
            this.top += t;
            this.left += l;
            this.right += r;
            this.bottom += b;
            return this;
        }
    };
    Ext.lib.Region.getRegion = function(el) {
        var p = Ext.lib.Dom.getXY(el);
        var t = p[1];
        var r = p[0] + el.offsetWidth;
        var b = p[1] + el.offsetHeight;
        var l = p[0];
        return new Ext.lib.Region(t, r, b, l);
    };
    Ext.lib.Point = function(x, y) {
        if (Ext.isArray(x)) {
            y = x[1];
            x = x[0];
        }
        this.x = this.right = this.left = this[0] = x;
        this.y = this.top = this.bottom = this[1] = y;
    };
    Ext.lib.Point.prototype = new Ext.lib.Region();
    function fly(el) {
        if (!libFlyweight) {
            libFlyweight = new Ext.Element.Flyweight();
        }
        libFlyweight.dom = el;
        return libFlyweight;
    }
})();


Ext.DomHelper = function(){
    var tempTableEl = null;
    var emptyTags = /^(?:br|frame|hr|img|input|link|meta|range|spacer|wbr|area|param|col)$/i;
    var tableRe = /^table|tbody|tr|td$/i;
    // build as innerHTML where available
    var createHtml = function(o){
        if(typeof o == 'string'){
            return o;
        }
        var b = "";
        if (Ext.isArray(o)) {
            for (var i = 0, l = o.length; i < l; i++) {
                b += createHtml(o[i]);
            }
            return b;
        }
        if(!o.tag){
            o.tag = "div";
        }
        b += "<" + o.tag;
        for(var attr in o){
            if(attr == "tag" || attr == "children" || attr == "cn" || attr == "html" || typeof o[attr] == "function") continue;
            if(attr == "style"){
                var s = o["style"];
                if(typeof s == "function"){
                    s = s.call();
                }
                if(typeof s == "string"){
                    b += ' style="' + s + '"';
                }else if(typeof s == "object"){
                    b += ' style="';
                    for(var key in s){
                        if(typeof s[key] != "function"){
                            b += key + ":" + s[key] + ";";
                        }
                    }
                    b += '"';
                }
            }else{
                if(attr == "cls"){
                    b += ' class="' + o["cls"] + '"';
                }else if(attr == "htmlFor"){
                    b += ' for="' + o["htmlFor"] + '"';
                }else{
                    b += " " + attr + '="' + o[attr] + '"';
                }
            }
        }
        if(emptyTags.test(o.tag)){
            b += "/>";
        }else{
            b += ">";
            var cn = o.children || o.cn;
            if(cn){
                b += createHtml(cn);
            } else if(o.html){
                b += o.html;
            }
            b += "</" + o.tag + ">";
        }
        return b;
    };
    // build as dom
    var createDom = function(o, parentNode){
        var el;
        if (Ext.isArray(o)) {                       // Allow Arrays of siblings to be inserted
            el = document.createDocumentFragment(); // in one shot using a DocumentFragment
            for(var i = 0, l = o.length; i < l; i++) {
                createDom(o[i], el);
            }
        } else if (typeof o == "string") {         // Allow a string as a child spec.
            el = document.createTextNode(o);
        } else {
            el = document.createElement(o.tag||'div');
            var useSet = !!el.setAttribute; // In IE some elements don't have setAttribute
            for(var attr in o){
                if(attr == "tag" || attr == "children" || attr == "cn" || attr == "html" || attr == "style" || typeof o[attr] == "function") continue;
                if(attr=="cls"){
                    el.className = o["cls"];
                }else{
                    if(useSet) el.setAttribute(attr, o[attr]);
                    else el[attr] = o[attr];
                }
            }
            Ext.DomHelper.applyStyles(el, o.style);
            var cn = o.children || o.cn;
            if(cn){
                createDom(cn, el);
            } else if(o.html){
                el.innerHTML = o.html;
            }
        }
        if(parentNode){
           parentNode.appendChild(el);
        }
        return el;
    };
    var ieTable = function(depth, s, h, e){
        tempTableEl.innerHTML = [s, h, e].join('');
        var i = -1, el = tempTableEl;
        while(++i < depth){
            el = el.firstChild;
        }
        return el;
    };
    // kill repeat to save bytes
    var ts = '<table>',
        te = '</table>',
        tbs = ts+'<tbody>',
        tbe = '</tbody>'+te,
        trs = tbs + '<tr>',
        tre = '</tr>'+tbe;
    var insertIntoTable = function(tag, where, el, html){
        if(!tempTableEl){
            tempTableEl = document.createElement('div');
        }
        var node;
        var before = null;
        if(tag == 'td'){
            if(where == 'afterbegin' || where == 'beforeend'){ // INTO a TD
                return;
            }
            if(where == 'beforebegin'){
                before = el;
                el = el.parentNode;
            } else{
                before = el.nextSibling;
                el = el.parentNode;
            }
            node = ieTable(4, trs, html, tre);
        }
        else if(tag == 'tr'){
            if(where == 'beforebegin'){
                before = el;
                el = el.parentNode;
                node = ieTable(3, tbs, html, tbe);
            } else if(where == 'afterend'){
                before = el.nextSibling;
                el = el.parentNode;
                node = ieTable(3, tbs, html, tbe);
            } else{ // INTO a TR
                if(where == 'afterbegin'){
                    before = el.firstChild;
                }
                node = ieTable(4, trs, html, tre);
            }
        } else if(tag == 'tbody'){
            if(where == 'beforebegin'){
                before = el;
                el = el.parentNode;
                node = ieTable(2, ts, html, te);
            } else if(where == 'afterend'){
                before = el.nextSibling;
                el = el.parentNode;
                node = ieTable(2, ts, html, te);
            } else{
                if(where == 'afterbegin'){
                    before = el.firstChild;
                }
                node = ieTable(3, tbs, html, tbe);
            }
        } else{ // TABLE
            if(where == 'beforebegin' || where == 'afterend'){ // OUTSIDE the table
                return;
            }
            if(where == 'afterbegin'){
                before = el.firstChild;
            }
            node = ieTable(2, ts, html, te);
        }
        el.insertBefore(node, before);
        return node;
    };
    return {
    useDom : false,
    markup : function(o){
        return createHtml(o);
    },
    applyStyles : function(el, styles){
        if(styles){
           el = Ext.fly(el);
           if(typeof styles == "string"){
               var re = /\s?([a-z\-]*)\:\s?([^;]*);?/gi;
               var matches;
               while ((matches = re.exec(styles)) != null){
                   el.setStyle(matches[1], matches[2]);
               }
           }else if (typeof styles == "object"){
               for (var style in styles){
                  el.setStyle(style, styles[style]);
               }
           }else if (typeof styles == "function"){
                Ext.DomHelper.applyStyles(el, styles.call());
           }
        }
    },
    insertHtml : function(where, el, html){
        where = where.toLowerCase();
        if(el.insertAdjacentHTML){
            if(tableRe.test(el.tagName)){
                var rs;
                if(rs = insertIntoTable(el.tagName.toLowerCase(), where, el, html)){
                    return rs;
                }
            }
            switch(where){
                case "beforebegin":
                    el.insertAdjacentHTML('BeforeBegin', html);
                    return el.previousSibling;
                case "afterbegin":
                    el.insertAdjacentHTML('AfterBegin', html);
                    return el.firstChild;
                case "beforeend":
                    el.insertAdjacentHTML('BeforeEnd', html);
                    return el.lastChild;
                case "afterend":
                    el.insertAdjacentHTML('AfterEnd', html);
                    return el.nextSibling;
            }
            throw 'Illegal insertion point -> "' + where + '"';
        }
        var range = el.ownerDocument.createRange();
        var frag;
        switch(where){
             case "beforebegin":
                range.setStartBefore(el);
                frag = range.createContextualFragment(html);
                el.parentNode.insertBefore(frag, el);
                return el.previousSibling;
             case "afterbegin":
                if(el.firstChild){
                    range.setStartBefore(el.firstChild);
                    frag = range.createContextualFragment(html);
                    el.insertBefore(frag, el.firstChild);
                    return el.firstChild;
                }else{
                    el.innerHTML = html;
                    return el.firstChild;
                }
            case "beforeend":
                if(el.lastChild){
                    range.setStartAfter(el.lastChild);
                    frag = range.createContextualFragment(html);
                    el.appendChild(frag);
                    return el.lastChild;
                }else{
                    el.innerHTML = html;
                    return el.lastChild;
                }
            case "afterend":
                range.setStartAfter(el);
                frag = range.createContextualFragment(html);
                el.parentNode.insertBefore(frag, el.nextSibling);
                return el.nextSibling;
            }
            throw 'Illegal insertion point -> "' + where + '"';
    },
    insertBefore : function(el, o, returnElement){
        return this.doInsert(el, o, returnElement, "beforeBegin");
    },
    insertAfter : function(el, o, returnElement){
        return this.doInsert(el, o, returnElement, "afterEnd", "nextSibling");
    },
    insertFirst : function(el, o, returnElement){
        return this.doInsert(el, o, returnElement, "afterBegin", "firstChild");
    },
    // private
    doInsert : function(el, o, returnElement, pos, sibling){
        el = Ext.getDom(el);
        var newNode;
        if(this.useDom){
            newNode = createDom(o, null);
            (sibling === "firstChild" ? el : el.parentNode).insertBefore(newNode, sibling ? el[sibling] : el);
        }else{
            var html = createHtml(o);
            newNode = this.insertHtml(pos, el, html);
        }
        return returnElement ? Ext.get(newNode, true) : newNode;
    },
    append : function(el, o, returnElement){
        el = Ext.getDom(el);
        var newNode;
        if(this.useDom){
            newNode = createDom(o, null);
            el.appendChild(newNode);
        }else{
            var html = createHtml(o);
            newNode = this.insertHtml("beforeEnd", el, html);
        }
        return returnElement ? Ext.get(newNode, true) : newNode;
    },
    overwrite : function(el, o, returnElement){
        el = Ext.getDom(el);
        el.innerHTML = createHtml(o);
        return returnElement ? Ext.get(el.firstChild, true) : el.firstChild;
    }
    };
}();

(function(){
    var createBuffered = function(h, o, scope){
        var task = new Ext.util.DelayedTask();
        return function(){
            task.delay(o.buffer, h, scope, Array.prototype.slice.call(arguments, 0));
        };
    };
    var createSingle = function(h, e, fn, scope){
        return function(){
            e.removeListener(fn, scope);
            return h.apply(scope, arguments);
        };
    };
    var createDelayed = function(h, o, scope){
        return function(){
            var args = Array.prototype.slice.call(arguments, 0);
            setTimeout(function(){
                h.apply(scope, args);
            }, o.delay || 10);
        };
    };
    Ext.util.Event = function(obj, name){
        this.name = name;
        this.obj = obj;
        this.listeners = [];
    };
    Ext.util.Event.prototype = {
        addListener : function(fn, scope, options){
            scope = scope || this.obj;
            if(!this.isListening(fn, scope)){
                var l = this.createListener(fn, scope, options);
                if(!this.firing){
                    this.listeners.push(l);
                }else{ // if we are currently firing this event, don't disturb the listener loop
                    this.listeners = this.listeners.slice(0);
                    this.listeners.push(l);
                }
            }
        },
        createListener : function(fn, scope, o){
            o = o || {};
            scope = scope || this.obj;
            var l = {fn: fn, scope: scope, options: o};
            var h = fn;
            if(o.delay){
                h = createDelayed(h, o, scope);
            }
            if(o.single){
                h = createSingle(h, this, fn, scope);
            }
            if(o.buffer){
                h = createBuffered(h, o, scope);
            }
            l.fireFn = h;
            return l;
        },
        findListener : function(fn, scope){
            scope = scope || this.obj;
            var ls = this.listeners;
            for(var i = 0, len = ls.length; i < len; i++){
                var l = ls[i];
                if(l.fn == fn && l.scope == scope){
                    return i;
                }
            }
            return -1;
        },
        isListening : function(fn, scope){
            return this.findListener(fn, scope) != -1;
        },
        removeListener : function(fn, scope){
            var index;
            if((index = this.findListener(fn, scope)) != -1){
                if(!this.firing){
                    this.listeners.splice(index, 1);
                }else{
                    this.listeners = this.listeners.slice(0);
                    this.listeners.splice(index, 1);
                }
                return true;
            }
            return false;
        },
        clearListeners : function(){
            this.listeners = [];
        },
        fire : function(){
            var ls = this.listeners, scope, len = ls.length;
            if(len > 0){
                this.firing = true;
                var args = Array.prototype.slice.call(arguments, 0);
                for(var i = 0; i < len; i++){
                    var l = ls[i];
                    if(l.fireFn.apply(l.scope||this.obj||window, arguments) === false){
                        this.firing = false;
                        return false;
                    }
                }
                this.firing = false;
            }
            return true;
        }
    };
})();

Ext.EventManager = function(){
    var docReadyEvent, docReadyProcId, docReadyState = false;
    var resizeEvent, resizeTask, textEvent, textSize;
    var E = Ext.lib.Event;
    var D = Ext.lib.Dom;
    // fix parser confusion
    var xname = 'Ex' + 't';
    var elHash = {};
    var addListener = function(el, ename, fn, wrap, scope){
        var id = Ext.id(el);
        if(!elHash[id]){
            elHash[id] = {};
        }
        var es = elHash[id];
        if(!es[ename]){
            es[ename] = [];
        }
        var ls = es[ename];
        ls.push({
            id: id,
            ename: ename,
            fn: fn,
            wrap: wrap,
            scope: scope
        });
         E.on(el, ename, wrap);
        if(ename == "mousewheel" && el.addEventListener){ // workaround for jQuery
            el.addEventListener("DOMMouseScroll", wrap, false);
            E.on(window, 'unload', function(){
                el.removeEventListener("DOMMouseScroll", wrap, false);
            });
        }
        if(ename == "mousedown" && el == document){ // fix stopped mousedowns on the document
            Ext.EventManager.stoppedMouseDownEvent.addListener(wrap);
        }
    }
    var removeListener = function(el, ename, fn, scope){
        el = Ext.getDom(el);
        var id = Ext.id(el), es = elHash[id], wrap;
        if(es){
            var ls = es[ename], l;
            if(ls){
                for(var i = 0, len = ls.length; i < len; i++){
                    l = ls[i];
                    if(l.fn == fn && (!scope || l.scope == scope)){
                        wrap = l.wrap;
                        E.un(el, ename, wrap);
                        ls.splice(i, 1);
                        break;
                    }
                }
            }
        }
        if(ename == "mousewheel" && el.addEventListener && wrap){
            el.removeEventListener("DOMMouseScroll", wrap, false);
        }
        if(ename == "mousedown" && el == document && wrap){ // fix stopped mousedowns on the document
            Ext.EventManager.stoppedMouseDownEvent.removeListener(wrap);
        }
    }
    var removeAll = function(el){
        el = Ext.getDom(el);
        var id = Ext.id(el), es = elHash[id], ls;
        if(es){
            for(var ename in es){
                if(es.hasOwnProperty(ename)){
                    ls = es[ename];
                    for(var i = 0, len = ls.length; i < len; i++){
                        E.un(el, ename, ls[i].wrap);
                        ls[i] = null;
                    }
                }
                es[ename] = null;
            }
            delete elHash[id];
        }
    }
     var fireDocReady = function(){
        if(!docReadyState){
            docReadyState = Ext.isReady = true;
            if(Ext.isGecko || Ext.isOpera) {
                document.removeEventListener("DOMContentLoaded", fireDocReady, false);
            }
        }
        if(docReadyProcId){
            clearInterval(docReadyProcId);
            docReadyProcId = null;
        }
        if(docReadyEvent){
            docReadyEvent.fire();
            docReadyEvent.clearListeners();
       }
    };
    var initDocReady = function(){
        docReadyEvent = new Ext.util.Event();
        if(Ext.isReady){
            return;
        }
        // no matter what, make sure it fires on load
        E.on(window, 'load', fireDocReady);
        if(Ext.isGecko || Ext.isOpera) {
            document.addEventListener('DOMContentLoaded', fireDocReady, false);
        }
        else if(Ext.isIE){
			document.onreadystatechange = function(){
				if(document.readyState == 'complete'){
					document.onreadystatechange = null;
					fireDocReady();
				}
            };
        }
        else if(Ext.isSafari){
            docReadyProcId = setInterval(function(){
                var rs = document.readyState;
                if(rs == 'complete') {
                    fireDocReady();
                 }
            }, 10);
        }
    };
    var createBuffered = function(h, o){
        var task = new Ext.util.DelayedTask(h);
        return function(e){
            // create new event object impl so new events don't wipe out properties
            e = new Ext.EventObjectImpl(e);
            task.delay(o.buffer, h, null, [e]);
        };
    };
    var createSingle = function(h, el, ename, fn, scope){
        return function(e){
            Ext.EventManager.removeListener(el, ename, fn, scope);
            h(e);
        };
    };
    var createDelayed = function(h, o){
        return function(e){
            // create new event object impl so new events don't wipe out properties
            e = new Ext.EventObjectImpl(e);
            setTimeout(function(){
                h(e);
            }, o.delay || 10);
        };
    };
    var listen = function(element, ename, opt, fn, scope){
        var o = (!opt || typeof opt == "boolean") ? {} : opt;
        fn = fn || o.fn; scope = scope || o.scope;
        var el = Ext.getDom(element);
        if(!el){
            throw "Error listening for \"" + ename + '\". Element "' + element + '" doesn\'t exist.';
        }
        var h = function(e){
            // prevent errors while unload occurring
            if(!window[xname]){
                return;
            }
            e = Ext.EventObject.setEvent(e);
            var t;
            if(o.delegate){
                t = e.getTarget(o.delegate, el);
                if(!t){
                    return;
                }
            }else{
                t = e.target;
            }
            if(o.stopEvent === true){
                e.stopEvent();
            }
            if(o.preventDefault === true){
               e.preventDefault();
            }
            if(o.stopPropagation === true){
                e.stopPropagation();
            }
            if(o.normalized === false){
                e = e.browserEvent;
            }
            fn.call(scope || el, e, t, o);
        };
        if(o.delay){
            h = createDelayed(h, o);
        }
        if(o.single){
            h = createSingle(h, el, ename, fn, scope);
        }
        if(o.buffer){
            h = createBuffered(h, o);
        }
        addListener(el, ename, fn, h, scope);
        return h;
    };
    var propRe = /^(?:scope|delay|buffer|single|stopEvent|preventDefault|stopPropagation|normalized|args|delegate)$/;
    var pub = {
       addListener : function(element, eventName, fn, scope, options){
            if(typeof eventName == "object"){
                var o = eventName;
                for(var e in o){
                    if(propRe.test(e)){
                        continue;
                    }
                    if(typeof o[e] == "function"){
                        // shared options
                        listen(element, e, o, o[e], o.scope);
                    }else{
                        // individual options
                        listen(element, e, o[e]);
                    }
                }
                return;
            }
            return listen(element, eventName, options, fn, scope);
        },
        removeListener : function(element, eventName, fn, scope){
            return removeListener(element, eventName, fn, scope);
        },
        removeAll : function(element){
            return removeAll(element);
        },
        onDocumentReady : function(fn, scope, options){
			if(!docReadyEvent){
                initDocReady();
			}
			if(docReadyState || Ext.isReady){ // if it already fired
				options || (options = {});
				fn.defer(options.delay||0, scope);
			}else{
				docReadyEvent.addListener(fn, scope, options);
			}
        },
        ieDeferSrc : false,
        textResizeInterval : 50
    };
    pub.on = pub.addListener;
    pub.un = pub.removeListener;
    pub.stoppedMouseDownEvent = new Ext.util.Event();
    return pub;
}();
Ext.onReady = Ext.EventManager.onDocumentReady;
// Initialize doc classes
(function(){
	var inited;
    var initExtCss = function(){
        // find the body element
        var bd = document.body || document.getElementsByTagName('body')[0];
        if(!bd){ return false; }
		if(inited) return true;
		inited = true;
        var cls = [' ',
                Ext.isIE ? "ext-ie " + (Ext.isIE6 ? 'ext-ie6' : 'ext-ie7')
                : Ext.isGecko ? "ext-gecko " + (Ext.isGecko2 ? 'ext-gecko2' : 'ext-gecko3')
                : Ext.isOpera ? "ext-opera"
                : Ext.isSafari ? "ext-safari" : ""];
        if(Ext.isMac){
            cls.push("ext-mac");
        }
        if(Ext.isLinux){
            cls.push("ext-linux");
        }
        if(Ext.isBorderBox){
            cls.push('ext-border-box');
        }
        if(Ext.isStrict){ // add to the parent to allow for selectors like ".ext-strict .ext-ie"
            var p = bd.parentNode;
            if(p){
                p.className += ' ext-strict';
            }
        }
        bd.className += cls.join(' ');
		clearInterval(Ext.initExtCssThreadId);
        return true;
    }
    if(!initExtCss()){
        Ext.initExtCssThreadId = setInterval(initExtCss, 50);
        Ext.onReady(initExtCss);
    }
})();
Ext.EventObject = function(){
    var E = Ext.lib.Event;
    // safari keypress events for special keys return bad keycodes
    var safariKeys = {
        3 : 13, // enter
        63234 : 37, // left
        63235 : 39, // right
        63232 : 38, // up
        63233 : 40, // down
        63276 : 33, // page up
        63277 : 34, // page down
        63272 : 46, // delete
        63273 : 36, // home
        63275 : 35  // end
    };
    // normalize button clicks
    var btnMap = Ext.isIE ? {1:0,4:1,2:2} :
                (Ext.isSafari ? {1:0,2:1,3:2} : {0:0,1:1,2:2});
    Ext.EventObjectImpl = function(e){
        if(e){
            this.setEvent(e.browserEvent || e);
        }
    };
    Ext.EventObjectImpl.prototype = {
        browserEvent : null,
        button : -1,
        shiftKey : false,
        ctrlKey : false,
        altKey : false,
        BACKSPACE: 8,
        TAB: 9,
        NUM_CENTER: 12,
        ENTER: 13,
        RETURN: 13,
        SHIFT: 16,
        CTRL: 17,
        CONTROL : 17, // legacy
        ALT: 18,
        PAUSE: 19,
        CAPS_LOCK: 20,
        ESC: 27,
        SPACE: 32,
        PAGE_UP: 33,
        PAGEUP : 33, // legacy
        PAGE_DOWN: 34,
        PAGEDOWN : 34, // legacy
        END: 35,
        HOME: 36,
        LEFT: 37,
        UP: 38,
        RIGHT: 39,
        DOWN: 40,
        PRINT_SCREEN: 44,
        INSERT: 45,
        DELETE: 46,
        ZERO: 48,
        ONE: 49,
        TWO: 50,
        THREE: 51,
        FOUR: 52,
        FIVE: 53,
        SIX: 54,
        SEVEN: 55,
        EIGHT: 56,
        NINE: 57,
        A: 65,
        B: 66,
        C: 67,
        D: 68,
        E: 69,
        F: 70,
        G: 71,
        H: 72,
        I: 73,
        J: 74,
        K: 75,
        L: 76,
        M: 77,
        N: 78,
        O: 79,
        P: 80,
        Q: 81,
        R: 82,
        S: 83,
        T: 84,
        U: 85,
        V: 86,
        W: 87,
        X: 88,
        Y: 89,
        Z: 90,
        CONTEXT_MENU: 93,
        NUM_ZERO: 96,
        NUM_ONE: 97,
        NUM_TWO: 98,
        NUM_THREE: 99,
        NUM_FOUR: 100,
        NUM_FIVE: 101,
        NUM_SIX: 102,
        NUM_SEVEN: 103,
        NUM_EIGHT: 104,
        NUM_NINE: 105,
        NUM_MULTIPLY: 106,
        NUM_PLUS: 107,
        NUM_MINUS: 109,
        NUM_PERIOD: 110,
        NUM_DIVISION: 111,
        F1: 112,
        F2: 113,
        F3: 114,
        F4: 115,
        F5: 116,
        F6: 117,
        F7: 118,
        F8: 119,
        F9: 120,
        F10: 121,
        F11: 122,
        F12: 123,
        setEvent : function(e){
            if(e == this || (e && e.browserEvent)){ // already wrapped
                return e;
            }
            this.browserEvent = e;
            if(e){
                // normalize buttons
                this.button = e.button ? btnMap[e.button] : (e.which ? e.which-1 : -1);
                if(e.type == 'click' && this.button == -1){
                    this.button = 0;
                }
                this.type = e.type;
                this.shiftKey = e.shiftKey;
                // mac metaKey behaves like ctrlKey
                this.ctrlKey = e.ctrlKey || e.metaKey;
                this.altKey = e.altKey;
                // in getKey these will be normalized for the mac
                this.keyCode = e.keyCode;
                this.charCode = e.charCode;
                // cache the target for the delayed and or buffered events
                this.target = E.getTarget(e);
                // same for XY
                this.xy = E.getXY(e);
            }else{
                this.button = -1;
                this.shiftKey = false;
                this.ctrlKey = false;
                this.altKey = false;
                this.keyCode = 0;
                this.charCode = 0;
                this.target = null;
                this.xy = [0, 0];
            }
            return this;
        },
        stopEvent : function(){
            if(this.browserEvent){
                if(this.browserEvent.type == 'mousedown'){
                    Ext.EventManager.stoppedMouseDownEvent.fire(this);
                }
                E.stopEvent(this.browserEvent);
            }
        },
        preventDefault : function(){
            if(this.browserEvent){
                E.preventDefault(this.browserEvent);
            }
        },
        isNavKeyPress : function(){
            var k = this.keyCode;
            k = Ext.isSafari ? (safariKeys[k] || k) : k;
            return (k >= 33 && k <= 40) || k == this.RETURN || k == this.TAB || k == this.ESC;
        },
        isSpecialKey : function(){
            var k = this.keyCode;
            return (this.type == 'keypress' && this.ctrlKey) || k == 9 || k == 13  || k == 40 || k == 27 ||
            (k == 16) || (k == 17) ||
            (k >= 18 && k <= 20) ||
            (k >= 33 && k <= 35) ||
            (k >= 36 && k <= 39) ||
            (k >= 44 && k <= 45);
        },
        stopPropagation : function(){
            if(this.browserEvent){
                if(this.browserEvent.type == 'mousedown'){
                    Ext.EventManager.stoppedMouseDownEvent.fire(this);
                }
                E.stopPropagation(this.browserEvent);
            }
        },
        getCharCode : function(){
            return this.charCode || this.keyCode;
        },
        getKey : function(){
            var k = this.keyCode || this.charCode;
            return Ext.isSafari ? (safariKeys[k] || k) : k;
        },
        getPageX : function(){
            return this.xy[0];
        },
        getPageY : function(){
            return this.xy[1];
        },
        getTime : function(){
            if(this.browserEvent){
                return E.getTime(this.browserEvent);
            }
            return null;
        },
        getXY : function(){
            return this.xy;
        },
        getTarget : function(selector, maxDepth, returnEl){
            return returnEl ? Ext.get(this.target) : this.target;
        },
        getRelatedTarget : function(){
            if(this.browserEvent){
                return E.getRelatedTarget(this.browserEvent);
            }
            return null;
        },
        getWheelDelta : function(){
            var e = this.browserEvent;
            var delta = 0;
            if(e.wheelDelta){ 
                delta = e.wheelDelta/120;
            }else if(e.detail){ 
                delta = -e.detail/3;
            }
            return delta;
        },
        hasModifier : function(){
            return ((this.ctrlKey || this.altKey) || this.shiftKey) ? true : false;
        },
        within : function(el, related){
            var t = this[related ? "getRelatedTarget" : "getTarget"]();
            return t && Ext.fly(el).contains(t);
        },
        getPoint : function(){
            return new Ext.lib.Point(this.xy[0], this.xy[1]);
        }
    };
    return new Ext.EventObjectImpl();
}();



(function(){
var D = Ext.lib.Dom;
var E = Ext.lib.Event;
var A = Ext.lib.Anim;
// local style camelizing for speed
var propCache = {};
var camelRe = /(-[a-z])/gi;
var camelFn = function(m, a){ return a.charAt(1).toUpperCase(); };
var view = document.defaultView;
Ext.Element = function(element, forceNew){
    var dom = typeof element == "string" ?
            document.getElementById(element) : element;
    if(!dom){ // invalid id/element
        return null;
    }
    var id = dom.id;
    if(forceNew !== true && id && Ext.Element.cache[id]){ // element object already exists
        return Ext.Element.cache[id];
    }
    this.dom = dom;
    this.id = id || Ext.id(dom);
};
var El = Ext.Element;
El.prototype = {
    originalDisplay : "",
    visibilityMode : 1,
    defaultUnit : "px",
    clean : function(forceReclean){
        if(this.isCleaned && forceReclean !== true){
            return this;
        }
        var ns = /\S/;
        var d = this.dom, n = d.firstChild, ni = -1;
 	    while(n){
 	        var nx = n.nextSibling;
 	        if(n.nodeType == 3 && !ns.test(n.nodeValue)){
 	            d.removeChild(n);
 	        }else{
 	            n.nodeIndex = ++ni;
 	        }
 	        n = nx;
 	    }
 	    this.isCleaned = true;
 	    return this;
 	},
    scrollIntoView : function(container, hscroll){
        var c = Ext.getDom(container) || Ext.getBody().dom;
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
    // private
    scrollChildIntoView : function(child, hscroll){
        Ext.fly(child, '_scrollChildIntoView').scrollIntoView(this, hscroll);
    },
    autoHeight : function(animate, duration, onComplete, easing){
        var oldHeight = this.getHeight();
        this.clip();
        this.setHeight(1); // force clipping
        setTimeout(function(){
            var height = parseInt(this.dom.scrollHeight, 10); // parseInt for Safari
            if(!animate){
                this.setHeight(height);
                this.unclip();
                if(typeof onComplete == "function"){
                    onComplete();
                }
            }else{
                this.setHeight(oldHeight); // restore original height
                this.setHeight(height, animate, duration, function(){
                    this.unclip();
                    if(typeof onComplete == "function") onComplete();
                }.createDelegate(this), easing);
            }
        }.createDelegate(this), 0);
        return this;
    },
    contains : function(el){
        if(!el){return false;}
        return D.isAncestor(this.dom, el.dom ? el.dom : el);
    },
    isVisible : function(deep) {
        var vis = !(this.getStyle("visibility") == "hidden" || this.getStyle("display") == "none");
        if(deep !== true || !vis){
            return vis;
        }
        var p = this.dom.parentNode;
        while(p && p.tagName.toLowerCase() != "body"){
            if(!Ext.fly(p, '_isVisible').isVisible()){
                return false;
            }
            p = p.parentNode;
        }
        return true;
    },
     setVisible : function(visible, animate){
        if(!animate || !A){
			this.setDisplayed(visible);
        }
        return this;
    },
    isDisplayed : function() {
        return this.getStyle("display") != "none";
    },
    toggle : function(){
        this.setVisible(!this.isVisible());
        return this;
    },
    setDisplayed : function(value) {
        if(typeof value == "boolean"){
           value = value ? this.originalDisplay : "none";
        }
        this.setStyle("display", value);
        return this;
    },
    focus : function() {
        try{
            this.dom.focus();
        }catch(e){}
        return this;
    },
    blur : function() {
        try{
            this.dom.blur();
        }catch(e){}
        return this;
    },
    addClass : function(className){
        if(Ext.isArray(className)){
            for(var i = 0, len = className.length; i < len; i++) {
            	this.addClass(className[i]);
            }
        }else{
            if(className && !this.hasClass(className)){
                this.dom.className = this.dom.className + " " + className;
            }
        }
        return this;
    },
    radioClass : function(className){
        var siblings = this.dom.parentNode.childNodes;
        for(var i = 0; i < siblings.length; i++) {
        	var s = siblings[i];
        	if(s.nodeType == 1){
        	    Ext.get(s).removeClass(className);
        	}
        }
        this.addClass(className);
        return this;
    },
    removeClass : function(className){
        if(!className || !this.dom.className){
            return this;
        }
        if(Ext.isArray(className)){
            for(var i = 0, len = className.length; i < len; i++) {
            	this.removeClass(className[i]);
            }
        }else{
            if(this.hasClass(className)){
                var re = this.classReCache[className];
                if (!re) {
                   re = new RegExp('(?:^|\\s+)' + className + '(?:\\s+|$)', "g");
                   this.classReCache[className] = re;
                }
                this.dom.className =
                    this.dom.className.replace(re, " ");
            }
        }
        return this;
    },
    // private
    classReCache: {},
    toggleClass : function(className){
        if(this.hasClass(className)){
            this.removeClass(className);
        }else{
            this.addClass(className);
        }
        return this;
    },
    hasClass : function(className){
        return className && (' '+this.dom.className+' ').indexOf(' '+className+' ') != -1;
    },
    replaceClass : function(oldClassName, newClassName){
        this.removeClass(oldClassName);
        this.addClass(newClassName);
        return this;
    },
    getStyles : function(){
        var a = arguments, len = a.length, r = {};
        for(var i = 0; i < len; i++){
            r[a[i]] = this.getStyle(a[i]);
        }
        return r;
    },
    getStyle : function(){
        return view && view.getComputedStyle ?
            function(prop){
                var el = this.dom, v, cs, camel;
                if(prop == 'float'){
                    prop = "cssFloat";
                }
                if(v = el.style[prop]){
                    return v;
                }
                if(cs = view.getComputedStyle(el, "")){
                    if(!(camel = propCache[prop])){
                        camel = propCache[prop] = prop.replace(camelRe, camelFn);
                    }
                    return cs[camel];
                }
                return null;
            } :
            function(prop){
                var el = this.dom, v, cs, camel;
                if(prop == 'opacity'){
                    if(typeof el.style.filter == 'string'){
                        var m = el.style.filter.match(/alpha\(opacity=(.*)\)/i);
                        if(m){
                            var fv = parseFloat(m[1]);
                            if(!isNaN(fv)){
                                return fv ? fv / 100 : 0;
                            }
                        }
                    }
                    return 1;
                }else if(prop == 'float'){
                    prop = "styleFloat";
                }
                if(!(camel = propCache[prop])){
                    camel = propCache[prop] = prop.replace(camelRe, camelFn);
                }
                if(v = el.style[camel]){
                    return v;
                }
                if(cs = el.currentStyle){
                    return cs[camel];
                }
                return null;
            };
    }(),
    setStyle : function(prop, value){
        if(typeof prop == "string"){
            var camel;
            if(!(camel = propCache[prop])){
                camel = propCache[prop] = prop.replace(camelRe, camelFn);
            }
            if(camel == 'opacity') {
                this.setOpacity(value);
            }else{
                this.dom.style[camel] = value;
            }
        }else{
            for(var style in prop){
                if(typeof prop[style] != "function"){
                   this.setStyle(style, prop[style]);
                }
            }
        }
        return this;
    },
    applyStyles : function(style){
        Ext.DomHelper.applyStyles(this.dom, style);
        return this;
    },
    getX : function(){
        return D.getX(this.dom);
    },
    getY : function(){
        return D.getY(this.dom);
    },
    getXY : function(){
        return D.getXY(this.dom);
    },
    getOffsetsTo : function(el){
        var o = this.getXY();
        var e = Ext.fly(el, '_internal').getXY();
        return [o[0]-e[0],o[1]-e[1]];
    },
    setX : function(x){
		D.setX(this.dom, x);
        return this;
    },
    setY : function(y){
		D.setY(this.dom, y);
        return this;
    },
    setLeft : function(left){
        this.setStyle("left", this.addUnits(left));
        return this;
    },
    setTop : function(top){
        this.setStyle("top", this.addUnits(top));
        return this;
    },
    setRight : function(right){
        this.setStyle("right", this.addUnits(right));
        return this;
    },
    setBottom : function(bottom){
        this.setStyle("bottom", this.addUnits(bottom));
        return this;
    },
    setXY : function(pos){
		D.setXY(this.dom, pos);
        return this;
    },
    getRegion : function(){
        return D.getRegion(this.dom);
    },
    getHeight : function(contentHeight){
        var h = this.dom.offsetHeight || 0;
        h = contentHeight !== true ? h : h-this.getBorderWidth("tb")-this.getPadding("tb");
        return h < 0 ? 0 : h;
    },
    getWidth : function(contentWidth){
        var w = this.dom.offsetWidth || 0;
        w = contentWidth !== true ? w : w-this.getBorderWidth("lr")-this.getPadding("lr");
        return w < 0 ? 0 : w;
    },
    getComputedHeight : function(){
        var h = Math.max(this.dom.offsetHeight, this.dom.clientHeight);
        if(!h){
            h = parseInt(this.getStyle('height'), 10) || 0;
            if(!this.isBorderBox()){
                h += this.getFrameWidth('tb');
            }
        }
        return h;
    },
    getComputedWidth : function(){
        var w = Math.max(this.dom.offsetWidth, this.dom.clientWidth);
        if(!w){
            w = parseInt(this.getStyle('width'), 10) || 0;
            if(!this.isBorderBox()){
                w += this.getFrameWidth('lr');
            }
        }
        return w;
    },
    getSize : function(contentSize){
        return {width: this.getWidth(contentSize), height: this.getHeight(contentSize)};
    },
    getStyleSize : function(){
        var w, h, d = this.dom, s = d.style;
        if(s.width && s.width != 'auto'){
            w = parseInt(s.width, 10);
            if(Ext.isBorderBox){
               w -= this.getFrameWidth('lr');
            }
        }
        if(s.height && s.height != 'auto'){
            h = parseInt(s.height, 10);
            if(Ext.isBorderBox){
               h -= this.getFrameWidth('tb');
            }
        }
        return {width: w || this.getWidth(true), height: h || this.getHeight(true)};
    },
    getViewSize : function(){
        var d = this.dom, doc = document, aw = 0, ah = 0;
        if(d == doc || d == doc.body){
            return {width : D.getViewWidth(), height: D.getViewHeight()};
        }else{
            return {
                width : d.clientWidth,
                height: d.clientHeight
            };
        }
    },
    getValue : function(asNumber){
        return asNumber ? parseInt(this.dom.value, 10) : this.dom.value;
    },
    // private
    adjustWidth : function(width){
        if(typeof width == "number"){
            if(this.autoBoxAdjust && !this.isBorderBox()){
               width -= (this.getBorderWidth("lr") + this.getPadding("lr"));
            }
            if(width < 0){
                width = 0;
            }
        }
        return width;
    },
    // private
    adjustHeight : function(height){
        if(typeof height == "number"){
           if(this.autoBoxAdjust && !this.isBorderBox()){
               height -= (this.getBorderWidth("tb") + this.getPadding("tb"));
           }
           if(height < 0){
               height = 0;
           }
        }
        return height;
    },
    setWidth : function(width){
        width = this.adjustWidth(width);
		this.dom.style.width = this.addUnits(width);
        return this;
    },
     setHeight : function(height){
        height = this.adjustHeight(height);
		this.dom.style.height = this.addUnits(height);
        return this;
    },
     setSize : function(width, height){
        if(typeof width == "object"){ // in case of object from getSize()
            height = width.height; width = width.width;
        }
        width = this.adjustWidth(width); height = this.adjustHeight(height);
		this.dom.style.width = this.addUnits(width);
		this.dom.style.height = this.addUnits(height);
        return this;
    },
    setBounds : function(x, y, width, height){
		this.setSize(width, height);
		this.setXY(x, y);
        return this;
    },
    setRegion : function(region){
        this.setBounds(region.left, region.top, region.right-region.left, region.bottom-region.top);
        return this;
    },
	getFitPosition : function(point){
		var dom = this.dom;
		var body = dom.ownerDocument.body;
		var offset = Element.getDimensions(dom);
		var left = point[0];
		var right = left + parseInt(offset['width'], 10);
		if(right >= parseInt(body.offsetWidth, 10)){
			left = Math.max(left - offset["width"], 0);
		}
		var top = point[1];
		var bottom = top + parseInt(offset['height'], 10);
		if(bottom >= parseInt(body.offsetHeight, 10)){
			top = Math.max(top - offset["height"], 0);
		}
		return [left, top];
	},
	setFitPosition : function(point){
		var dom = this.dom;
		point = this.getFitPosition(point);
		dom.style.left = point[0] + "px";
		dom.style.top = point[1] + "px";
	},
    addListener : function(eventName, fn, scope, options){
        Ext.EventManager.on(this.dom,  eventName, fn, scope || this, options);
    },
    removeListener : function(eventName, fn, scope){
        Ext.EventManager.removeListener(this.dom,  eventName, fn, scope || this);
        return this;
    },
    removeAllListeners : function(){
        Ext.EventManager.removeAll(this.dom);
        return this;
    },
     setOpacity : function(opacity){
		var s = this.dom.style;
		if(Ext.isIE){
			s.zoom = 1;
			s.filter = (s.filter || '').replace(/alpha\([^\)]*\)/gi,"") +
					   (opacity == 1 ? "" : " alpha(opacity=" + opacity * 100 + ")");
		}else{
			s.opacity = opacity;
		}
        return this;
    },
    getLeft : function(local){
        if(!local){
            return this.getX();
        }else{
            return parseInt(this.getStyle("left"), 10) || 0;
        }
    },
    getRight : function(local){
        if(!local){
            return this.getX() + this.getWidth();
        }else{
            return (this.getLeft(true) + this.getWidth()) || 0;
        }
    },
    getTop : function(local) {
        if(!local){
            return this.getY();
        }else{
            return parseInt(this.getStyle("top"), 10) || 0;
        }
    },
    getBottom : function(local){
        if(!local){
            return this.getY() + this.getHeight();
        }else{
            return (this.getTop(true) + this.getHeight()) || 0;
        }
    },
    position : function(pos, zIndex, x, y){
        if(!pos){
           if(this.getStyle('position') == 'static'){
               this.setStyle('position', 'relative');
           }
        }else{
            this.setStyle("position", pos);
        }
        if(zIndex){
            this.setStyle("z-index", zIndex);
        }
        if(x !== undefined && y !== undefined){
            this.setXY([x, y]);
        }else if(x !== undefined){
            this.setX(x);
        }else if(y !== undefined){
            this.setY(y);
        }
    },
    clearPositioning : function(value){
        value = value ||'';
        this.setStyle({
            "left": value,
            "right": value,
            "top": value,
            "bottom": value,
            "z-index": "",
            "position" : "static"
        });
        return this;
    },
    getPositioning : function(){
        var l = this.getStyle("left");
        var t = this.getStyle("top");
        return {
            "position" : this.getStyle("position"),
            "left" : l,
            "right" : l ? "" : this.getStyle("right"),
            "top" : t,
            "bottom" : t ? "" : this.getStyle("bottom"),
            "z-index" : this.getStyle("z-index")
        };
    },
    getBorderWidth : function(side){
        return this.addStyles(side, El.borders);
    },
    getPadding : function(side){
        return this.addStyles(side, El.paddings);
    },
    setPositioning : function(pc){
        this.applyStyles(pc);
        if(pc.right == "auto"){
            this.dom.style.right = "";
        }
        if(pc.bottom == "auto"){
            this.dom.style.bottom = "";
        }
        return this;
    },
    // private
	setOverflow : function(v){
    	if(v=='auto' && Ext.isMac && Ext.isGecko2){ // work around stupid FF 2.0/Mac scroll bar bug
    		this.dom.style.overflow = 'hidden';
        	(function(){this.dom.style.overflow = 'auto';}).defer(1, this);
    	}else{
    		this.dom.style.overflow = v;
    	}
	},

    getAnchorXY : function(anchor, local, s){
        //Passing a different size is useful for pre-calculating anchors,
        //especially for anchored animations that change the el size.
        var w, h, vp = false;
        if(!s){
            var d = this.dom;
            if(d == document.body || d == document){
                vp = true;
                w = D.getViewWidth(); h = D.getViewHeight();
            }else{
                w = this.getWidth(); h = this.getHeight();
            }
        }else{
            w = s.width;  h = s.height;
        }
        var x = 0, y = 0, r = Math.round;
        switch((anchor || "tl").toLowerCase()){
            case "c":
                x = r(w*.5);
                y = r(h*.5);
            break;
            case "t":
                x = r(w*.5);
                y = 0;
            break;
            case "l":
                x = 0;
                y = r(h*.5);
            break;
            case "r":
                x = w;
                y = r(h*.5);
            break;
            case "b":
                x = r(w*.5);
                y = h;
            break;
            case "tl":
                x = 0;
                y = 0;
            break;
            case "bl":
                x = 0;
                y = h;
            break;
            case "br":
                x = w;
                y = h;
            break;
            case "tr":
                x = w;
                y = 0;
            break;
        }
        if(local === true){
            return [x, y];
        }
        if(vp){
            var sc = this.getScroll();
            return [x + sc.left, y + sc.top];
        }
        //Add the element's offset xy
        var o = this.getXY();
        return [x+o[0], y+o[1]];
    },
    getAlignToXY : function(el, p, o){
        el = Ext.get(el);
        if(!el || !el.dom){
            throw "Element.alignToXY with an element that doesn't exist";
        }
        var d = this.dom;
        var c = false; //constrain to viewport
        var p1 = "", p2 = "";
        o = o || [0,0];
        if(!p){
            p = "tl-bl";
        }else if(p == "?"){
            p = "tl-bl?";
        }else if(p.indexOf("-") == -1){
            p = "tl-" + p;
        }
        p = p.toLowerCase();
        var m = p.match(/^([a-z]+)-([a-z]+)(\?)?$/);
        if(!m){
           throw "Element.alignTo with an invalid alignment " + p;
        }
        p1 = m[1]; p2 = m[2]; c = !!m[3];
        //Subtract the aligned el's internal xy from the target's offset xy
        //plus custom offset to get the aligned el's new offset xy
        var a1 = this.getAnchorXY(p1, true);
        var a2 = el.getAnchorXY(p2, false);
        var x = a2[0] - a1[0] + o[0];
        var y = a2[1] - a1[1] + o[1];
        if(c){
            //constrain the aligned el to viewport if necessary
            var w = this.getWidth(), h = this.getHeight(), r = el.getRegion();
            // 5px of margin for ie
            var dw = D.getViewWidth()-5, dh = D.getViewHeight()-5;
            //If we are at a viewport boundary and the aligned el is anchored on a target border that is
            //perpendicular to the vp border, allow the aligned el to slide on that border,
            //otherwise swap the aligned el to the opposite border of the target.
            var p1y = p1.charAt(0), p1x = p1.charAt(p1.length-1);
           var p2y = p2.charAt(0), p2x = p2.charAt(p2.length-1);
           var swapY = ((p1y=="t" && p2y=="b") || (p1y=="b" && p2y=="t"));
           var swapX = ((p1x=="r" && p2x=="l") || (p1x=="l" && p2x=="r"));
           var doc = document;
           var scrollX = (doc.documentElement.scrollLeft || doc.body.scrollLeft || 0)+5;
           var scrollY = (doc.documentElement.scrollTop || doc.body.scrollTop || 0)+5;
           if((x+w) > dw + scrollX){
                x = swapX ? r.left-w : dw+scrollX-w;
            }
           if(x < scrollX){
               x = swapX ? r.right : scrollX;
           }
           if((y+h) > dh + scrollY){
                y = swapY ? r.top-h : dh+scrollY-h;
            }
           if (y < scrollY){
               y = swapY ? r.bottom : scrollY;
           }
        }
        return [x,y];
    },
    alignTo : function(element, position, offsets){
        var xy = this.getAlignToXY(element, position, offsets);
        this.setXY(xy);
        return this;
    },

    hide : function(){
        this.setVisible(false);
        return this;
    },
    show : function(){
        this.setVisible(true);
        return this;
    },
	update: function(html, loadScripts, callback) {
        if(typeof html == "undefined"){
            html = "";
        }
		this.dom.innerHTML = html.stripScripts();
		setTimeout(function() {
			html.evalScripts();
			if(typeof callback == "function"){
				callback();
			}
		}, 50);
		return this;
	},
    unselectable : function(){
        this.dom.unselectable = "on";
        this.swallowEvent("selectstart", true);
        this.applyStyles("-moz-user-select:none;-khtml-user-select:none;");
        this.addClass("x-unselectable");
        return this;
    },
    getCenterXY : function(){
        return this.getAlignToXY(document, 'c-c');
    },
    center : function(centerIn){
        this.alignTo(centerIn || document, 'c-c');
        return this;
    },
    isBorderBox : function(){
        return noBoxAdjust[this.dom.tagName.toLowerCase()] || Ext.isBorderBox;
    },
    getBox : function(contentBox, local){
        var xy;
        if(!local){
            xy = this.getXY();
        }else{
            var left = parseInt(this.getStyle("left"), 10) || 0;
            var top = parseInt(this.getStyle("top"), 10) || 0;
            xy = [left, top];
        }
        var el = this.dom, w = el.offsetWidth, h = el.offsetHeight, bx;
        if(!contentBox){
            bx = {x: xy[0], y: xy[1], 0: xy[0], 1: xy[1], width: w, height: h};
        }else{
            var l = this.getBorderWidth("l")+this.getPadding("l");
            var r = this.getBorderWidth("r")+this.getPadding("r");
            var t = this.getBorderWidth("t")+this.getPadding("t");
            var b = this.getBorderWidth("b")+this.getPadding("b");
            bx = {x: xy[0]+l, y: xy[1]+t, 0: xy[0]+l, 1: xy[1]+t, width: w-(l+r), height: h-(t+b)};
        }
        bx.right = bx.x + bx.width;
        bx.bottom = bx.y + bx.height;
        return bx;
    },
    getFrameWidth : function(sides, onlyContentBox){
        return onlyContentBox && Ext.isBorderBox ? 0 : (this.getPadding(sides) + this.getBorderWidth(sides));
    },
    setBox : function(box, adjust){
        var w = box.width, h = box.height;
        if((adjust && !this.autoBoxAdjust) && !this.isBorderBox()){
           w -= (this.getBorderWidth("lr") + this.getPadding("lr"));
           h -= (this.getBorderWidth("tb") + this.getPadding("tb"));
        }
        this.setBounds(box.x, box.y, w, h);
        return this;
    },
    getMargins : function(side){
        if(!side){
            return {
                top: parseInt(this.getStyle("margin-top"), 10) || 0,
                left: parseInt(this.getStyle("margin-left"), 10) || 0,
                bottom: parseInt(this.getStyle("margin-bottom"), 10) || 0,
                right: parseInt(this.getStyle("margin-right"), 10) || 0
            };
        }else{
            return this.addStyles(side, El.margins);
         }
    },
    // private
    addStyles : function(sides, styles){
        var val = 0, v, w;
        for(var i = 0, len = sides.length; i < len; i++){
            v = this.getStyle(styles[sides.charAt(i)]);
            if(v){
                 w = parseInt(v, 10);
                 if(w){ val += (w >= 0 ? w : -1 * w); }
            }
        }
        return val;
    },
    createProxy : function(config, renderTo, matchBox){
        config = typeof config == "object" ?
            config : {tag : "div", cls: config};
        var proxy;
        if(renderTo){
            proxy = Ext.DomHelper.append(renderTo, config, true);
        }else {
            proxy = Ext.DomHelper.insertBefore(this.dom, config, true);
        }
        if(matchBox){
           proxy.setBox(this.getBox());
        }
        return proxy;
    },
    mask : function(msg, msgCls){
        if(this.getStyle("position") == "static"){
            this.setStyle("position", "relative");
        }
        if(this._maskMsg){
            this._maskMsg.remove();
        }
        if(this._mask){
            this._mask.remove();
        }
        this._mask = Ext.DomHelper.append(this.dom, {cls:"ext-el-mask"}, true);
        this.addClass("x-masked");
        this._mask.setDisplayed(true);
        if(typeof msg == 'string'){
            this._maskMsg = Ext.DomHelper.append(this.dom, {cls:"ext-el-mask-msg", cn:{tag:'div'}}, true);
            var mm = this._maskMsg;
            mm.dom.className = msgCls ? "ext-el-mask-msg " + msgCls : "ext-el-mask-msg";
            mm.dom.firstChild.innerHTML = msg;
            mm.setDisplayed(true);
            mm.center(this);
        }
        if(Ext.isIE && !(Ext.isIE7 && Ext.isStrict) && this.getStyle('height') == 'auto'){ // ie will not expand full height automatically
            this._mask.setSize(this.dom.clientWidth, this.getHeight());
        }
        return this._mask;
    },
    unmask : function(){
        if(this._mask){
            if(this._maskMsg){
                this._maskMsg.remove();
                delete this._maskMsg;
            }
            this._mask.remove();
            delete this._mask;
        }
        this.removeClass("x-masked");
    },
    isMasked : function(){
        return this._mask && this._mask.isVisible();
    },
    remove : function(){
        Ext.removeNode(this.dom);
        delete El.cache[this.dom.id];
    },

    swallowEvent : function(eventName, preventDefault){
        var fn = function(e){
            e.stopPropagation();
            if(preventDefault){
                e.preventDefault();
            }
        };
        if(Ext.isArray(eventName)){
            for(var i = 0, len = eventName.length; i < len; i++){
                 this.on(eventName[i], fn);
            }
            return this;
        }
        this.on(eventName, fn);
        return this;
    },
    appendChild: function(el){
        el = Ext.get(el);
        el.appendTo(this);
        return this;
    },
    appendTo: function(el){
        el = Ext.getDom(el);
        el.appendChild(this.dom);
        return this;
    },
    insertBefore: function(el){
        el = Ext.getDom(el);
        el.parentNode.insertBefore(this.dom, el);
        return this;
    },
    insertAfter: function(el){
        el = Ext.getDom(el);
        el.parentNode.insertBefore(this.dom, el.nextSibling);
        return this;
    },
    wrap: function(config, returnDom){
        if(!config){
            config = {tag: "div"};
        }
        var newEl = Ext.DomHelper.insertBefore(this.dom, config, !returnDom);
        newEl.dom ? newEl.dom.appendChild(this.dom) : newEl.appendChild(this.dom);
        return newEl;
    },
    replace: function(el){
        el = Ext.get(el);
        this.insertBefore(el);
        el.remove();
        return this;
    },
    replaceWith: function(el){
        if(typeof el == 'object' && !el.nodeType && !el.dom){ // dh config
            el = this.insertSibling(el, 'before');
        }else{
            el = Ext.getDom(el);
            this.dom.parentNode.insertBefore(el, this.dom);
        }
        El.uncache(this.id);
        this.dom.parentNode.removeChild(this.dom);
        this.dom = el;
        this.id = Ext.id(el);
        El.cache[this.id] = this;
        return this;
    },
    insertHtml : function(where, html, returnEl){
        var el = Ext.DomHelper.insertHtml(where, this.dom, html);
        return returnEl ? Ext.get(el) : el;
    },
     isScrollable : function(){
        var dom = this.dom;
        return dom.scrollHeight > dom.clientHeight || dom.scrollWidth > dom.clientWidth;
    },
    scrollTo : function(side, value){
        var prop = side.toLowerCase() == "left" ? "scrollLeft" : "scrollTop";
		this.dom[prop] = value;
        return this;
    },
    translatePoints : function(x, y){
        if(typeof x == 'object' || Ext.isArray(x)){
            y = x[1]; x = x[0];
        }
        var p = this.getStyle('position');
        var o = this.getXY();
        var l = parseInt(this.getStyle('left'), 10);
        var t = parseInt(this.getStyle('top'), 10);
        if(isNaN(l)){
            l = (p == "relative") ? 0 : this.dom.offsetLeft;
        }
        if(isNaN(t)){
            t = (p == "relative") ? 0 : this.dom.offsetTop;
        }
        return {left: (x - o[0] + l), top: (y - o[1] + t)};
    },
    getScroll : function(){
        var d = this.dom, doc = document;
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
};
var ep = El.prototype;
ep.on = ep.addListener;
ep.un = ep.removeListener;
ep.autoBoxAdjust = true;
// private
El.unitPattern = /\d+(px|em|%|en|ex|pt|in|cm|mm|pc)$/i;
// private
ep.addUnits = El.addUnits = function(v, defaultUnit){
    if(v === "" || v == "auto"){
        return v;
    }
    if(v === undefined){
        return '';
    }
    if(typeof v == "number" || !El.unitPattern.test(v)){
        return v + (defaultUnit || 'px');
    }
    return v;
};
// special markup used throughout Ext when box wrapping elements
El.boxMarkup = '<div class="{0}-tl"><div class="{0}-tr"><div class="{0}-tc"></div></div></div><div class="{0}-ml"><div class="{0}-mr"><div class="{0}-mc"></div></div></div><div class="{0}-bl"><div class="{0}-br"><div class="{0}-bc"></div></div></div>';
El.borders = {l: "border-left-width", r: "border-right-width", t: "border-top-width", b: "border-bottom-width"};
El.paddings = {l: "padding-left", r: "padding-right", t: "padding-top", b: "padding-bottom"};
El.margins = {l: "margin-left", r: "margin-right", t: "margin-top", b: "margin-bottom"};
El.cache = {};
var docEl;
El.get = function(el){
    var ex, elm, id;
    if(!el){ return null; }
    if(typeof el == "string"){ // element id
        if(!(elm = document.getElementById(el))){
            return null;
        }
        if(ex = El.cache[el]){
            ex.dom = elm;
        }else{
            ex = El.cache[el] = new El(elm);
        }
        return ex;
    }else if(el.tagName){ // dom element
        if(!(id = el.id)){
            id = Ext.id(el);
        }
        if(ex = El.cache[id]){
            ex.dom = el;
        }else{
            ex = El.cache[id] = new El(el);
        }
        return ex;
    }else if(el instanceof El){
        if(el != docEl){
            el.dom = document.getElementById(el.id) || el.dom; // refresh dom element in case no longer valid,
                                                          // catch case where it hasn't been appended
            El.cache[el.id] = el; // in case it was created directly with Element(), let's cache it
        }
        return el;
    }else if(el.isComposite){
        return el;
    }else if(Ext.isArray(el)){
        return El.select(el);
    }else if(el == document){
        // create a bogus element object representing the document object
        if(!docEl){
            var f = function(){};
            f.prototype = El.prototype;
            docEl = new f();
            docEl.dom = document;
        }
        return docEl;
    }
    return null;
};
// private
El.uncache = function(el){
    for(var i = 0, a = arguments, len = a.length; i < len; i++) {
        if(a[i]){
            delete El.cache[a[i].id || a[i]];
        }
    }
};
// private
// Garbage collection - uncache elements/purge listeners on orphaned elements
// so we don't hold a reference and cause the browser to retain them
El.garbageCollect = function(){
    if(!Ext.enableGarbageCollector){
        clearInterval(El.collectorThreadId);
        return;
    }
    for(var eid in El.cache){
        var el = El.cache[eid], d = el.dom;
         if(!d || !d.parentNode || (!d.offsetParent && !document.getElementById(eid))){
            delete El.cache[eid];
            if(d && Ext.enableListenerCollection){
                Ext.EventManager.removeAll(d);
            }
        }
    }
}
El.collectorThreadId = setInterval(El.garbageCollect, 1000);
var flyFn = function(){};
flyFn.prototype = El.prototype;
var _cls = new flyFn();
// dom is optional
El.Flyweight = function(dom){
    this.dom = dom;
};
El.Flyweight.prototype = _cls;
El.Flyweight.prototype.isFlyweight = true;
El._flyweights = {};
El.fly = function(el, named){
    named = named || '_global';
    el = Ext.getDom(el);
    if(!el){
        return null;
    }
    if(!El._flyweights[named]){
        El._flyweights[named] = new El.Flyweight();
    }
    El._flyweights[named].dom = el;
    return El._flyweights[named];
};
Ext.get = El.get;
Ext.fly = El.fly;
// speedy lookup for elements never to box adjust
var noBoxAdjust = Ext.isStrict ? {
    select:1
} : {
    input:1, select:1, textarea:1
};
if(Ext.isIE || Ext.isGecko){
    noBoxAdjust['button'] = 1;
}
Ext.EventManager.on(window, 'unload', function(){
	El.garbageCollect();
	clearInterval(El.collectorThreadId);
    delete El.cache;
    delete El._flyweights;
});
})();


var WCMConstants = {
	DEBUG : true,
	WCM_APPNAME : 'wcm',
	WCM6_PATH : '/wcm/app/',
	WCM_LOCAL_URL : '/wcm/app/localxml/',
	WCM_ROMOTE_URL : '/wcm/center.do',
	WCM_NOT_LOGIN_PAGE : '/wcm/console/include/not_login.htm'
}
Ext.Msg = {
	alert : function(msg, fn, scope){
		alert(msg);
		(fn||Ext.emptyFn)();
	},
	timeAlert : function(msg, time, fn, scope){
		this.$timeAlert.apply(this, arguments);
	},
	$timeAlert : function(msg, time, fn, scope){
		alert(msg);
		(fn||Ext.emptyFn)();
	},
	$alert : function(msg, fn, scope){
		this.alert.apply(this, arguments);
	},
	$success : function(msg, fn, scope){
		this.alert.apply(this, arguments);
	},
	$fail : function(msg, fn, scope){
		this.alert.apply(this, arguments);
	},
	confirm : function(msg, fn, scope){
		if(confirm(msg)){
			(fn["ok"] ||Ext.emptyFn)();
			return;
		}
		(fn["cancel"] ||Ext.emptyFn)();
	},
	$confirm : function(msg, fn, scope){
		this.confirm.apply(this, arguments);
	},
	report : function(_report){
		this.alert(Ext.toSource(_report));
	},
	fault : function(_fault){
		this.alert(Ext.toSource(_fault));
	},
	show : function(options){
		this.alert(Ext.toSource(options));
	},
	warn : function(msg, fn, scope){
		this.alert(msg, fn, scope);
	},
	error : function(msg, fn, scope){
		this.alert(msg, fn, scope);
	}
};
//debug弹出，非debug不弹出
Ext.Msg.d$alert = function(msg, fn, scope){
	if(!Ext.isDebug()){
		return this;
	}
	Ext.Msg.$alert.apply(Ext.Msg, arguments);
	return this;
}
Ext.Msg.$error = function(err){
	Ext.Msg.$alert(Ext.errorMsg(err));
	return this;
}
Ext.Msg.d$error = function(err){
	Ext.Msg.d$alert(Ext.errorMsg(err));
	return this;
}
var ProcessBar = {
	init : function(){
	},
	addState : function(){
	},
	next : function(){
	},
	start : function(){
	},
	exit : function(){
	},
	close :function(){
	}
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
	observe : function(element, name, fn, b){
		fn.newFn = function(event){
			fn(event.browserEvent, event);
		};
		Ext.EventManager.on(element, name, fn.newFn);
	},
	element : function(event){
		return event.target || event.srcElement;
	},
	findElement: function(event, tagName) {
		var element = Event.element(event);
		while (element.parentNode && (!element.tagName ||
			(element.tagName.toUpperCase() != tagName.toUpperCase())))
		  element = element.parentNode;
		return element;
	},
	stop : function(event){
		if (event.preventDefault) {
			event.preventDefault();
			event.stopPropagation();
		} else {
			event.returnValue = false;
			event.cancelBubble = true;
		}
	},
	stopAllObserving : function (element, name){
		Ext.EventManager.removeAll(element, name);
	},
	stopObserving : function(element, name, fn){
		Ext.EventManager.un(element, name, fn.newFn || fn);
	},
	onDOMReady : function(fn){
		Ext.EventManager.on(window, 'load', fn);
	}
};
Ext.apply(Event, {
  KEY_BACKSPACE: 8,
  KEY_TAB:       9,
  KEY_RETURN:   13,
  KEY_ESC:      27,
  KEY_LEFT:     37,
  KEY_UP:       38,
  KEY_RIGHT:    39,
  KEY_DOWN:     40,
  KEY_DELETE:   46,
  KEY_PGUP:   33,
  KEY_PGDN:   34,
  KEY_HOME:   36,
  KEY_END:    35
});
Ext.ns('Element');
Ext.applyIf(Element, {
	update : function(_el, html, loadScripts, callback){
		return Ext.fly(_el).update(html, loadScripts, callback);
	},
	visible: function(element) {
		element = $(element);
		if(Ext.type(element)!='element')return;
		return $(element).style.display != 'none';
	},
	toggle: function(element) {
		element = $(element);
		if(Ext.type(element)!='element')return;
		return Element[Element.visible(element) ? 'hide' : 'show'](element);
	},
	hide: function(element) {
		element = $(element);
		if(Ext.type(element)!='element')return;
		return element.style.display = 'none';//Ext.fly(element).hide();
	},
	show: function(element) {
		element = $(element);
		if(Ext.type(element)!='element')return;
		return element.style.display = '';//Ext.fly(element).show();
	},
	remove : function(_el){
		return Ext.fly(_el).remove();
	},
	addClassName : function(_el, className){
		return Ext.fly(_el).addClass(className);
	},
	removeClassName : function(_el, _cls){
		return Ext.fly(_el).removeClass(_cls);
	},
	hasClassName : function(_el, _cls){
		return Ext.fly(_el).hasClass(_cls);
	},
	replaceClassName : function(_el, _clsOld, _clsNew){
        return Ext.fly(_el).replaceClass(_clsOld, _clsNew);
	},
	setFitPosition : function(_el, point){
        return Ext.fly(_el).setFitPosition(point);
	},
	getFitPosition : function(_el, point){
        return Ext.fly(_el).getFitPosition(point);
	},
	getDimensions: function(element) {
		element = $(element);
		if (Element.getStyle(element, 'display') != 'none')
		  return {width: element.offsetWidth, height: element.offsetHeight};

		// All *Width and *Height properties give 0 on elements with display none,
		// so enable the element temporarily
		var els = element.style;
		var originalVisibility = els.visibility;
		var originalPosition = els.position;
		els.visibility = 'hidden';
		els.position = 'absolute';
		els.display = '';
		var originalWidth = element.clientWidth;
		var originalHeight = element.clientHeight;
		els.display = 'none';
		els.position = originalPosition;
		els.visibility = originalVisibility;
		return {width: originalWidth, height: originalHeight};
	},
	getStyle :function(_el, _styleName){
		return Ext.fly(_el).getStyle(_styleName);
	}
});
var Form = (function(){
	var oSerializers = {
		input: function(element) {
			switch (element.type.toLowerCase()) {
				case 'submit':
				case 'hidden':
				case 'password':
				case 'text':
					return oSerializers.textarea(element);
				case 'checkbox':
				case 'radio':
					return oSerializers.inputSelector(element);
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
			return oSerializers[element.type == 'select-one' ?
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
	window.$F = function(element) {
		element = $(element);
		var method = element.tagName.toLowerCase();
		var parameter = oSerializers[method](element);
		if (parameter)
			return parameter[1];
		return "";
	}
	window.$$F = function $$F(_sName){
		var elements = document.getElementsByName(_sName);
		var values = [];
		for(var i=0; i<elements.length; i++){
			var element = elements[i];
			var method = element.tagName.toLowerCase();
			var parameter = oSerializers[method](element);
			if (parameter)
			  values.push(parameter[1]);
		}
		return values.join(',');
	}
	return {
		getElements : function(form) {
			form = $(form);
			var elements = [];
			for (tagName in oSerializers) {
				var tagElements = form.getElementsByTagName(tagName);
				for (var j = 0; j < tagElements.length; j++){
					elements.push(tagElements[j]);
				}
			}
			return elements;
		}
	}
})();

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
		var oRstJson = Ext.Json._json(_oJson, _sXPath, bCaseSensitive);
		return oRstJson==null ? null : (oRstJson['NODEVALUE'] || oRstJson);
	},
	array : function(_oJson,_sXPath,bCaseSensitive){
		var oRstJson = Ext.Json._json(_oJson, _sXPath, bCaseSensitive);
		return !oRstJson ? [] : (
				Ext.isArray(oRstJson) ? oRstJson : [oRstJson]
			);
	},
	parseXml : function(xml){
		var root = xml.documentElement;
		if(root == null) return null;
		var json = Ext.Json.parseElement(root);
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
					var b = Ext.Json.parseElement(childs[i]);
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
		Object.each(jsonObject, function(value, paramName){
			vReturn.push(paramName + '='
				+ encodeURIComponent(value['NODEVALUE'] || value));
		});
		return vReturn.join('&');
	},
	toUpperCase : function(_simpleJson){
		if(Ext.isEmpty(_simpleJson) || Ext.isFunction(_simpleJson)){
			return "";
		}
		if(Ext.isSimpType(_simpleJson)){
			return _simpleJson;
		}
		//var callee = arguments.callee;
//		var mapFunc = function(value){
//			return Ext.Json.toUpperCase(value);
//		}
		if(Ext.isArray(_simpleJson)) {
			//var arr = _simpleJson;
			var arr = [];
			for(var i=0,n=_simpleJson.length; i<n; i++){
				arr.push(Ext.Json.toUpperCase(_simpleJson[i]));
			}
			return arr;
//			return _simpleJson.map(Ext.Json.toUpperCase);
		}
		var retJson = {};
		for(var name in _simpleJson){
			retJson[name.toUpperCase()] = Ext.Json.toUpperCase(_simpleJson[name]);
		}
		return retJson;
	},
	eval : function(_sJson){
		try{
			eval("var json = " + _sJson);
			return Ext.Json.toUpperCase(json);
		}catch(err){
			Ext.Msg.d$error(err);
		}
	}
};

$v = Ext.Json.value;
$a = Ext.Json.array;

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
var FileUploadHelper = {
	validFileExt :function(_strPath,_sAllowExt,_fAlert){
		var sAllowExt = _sAllowExt;
		_strPath = _strPath || '';
		_fAlert = _fAlert||window.$alert||alert;
		if(_strPath.length<=0){
			(_fAlert)("没有选择文件");
			return false;
		}
		var validFileExtRe = new RegExp('.+\.('+_sAllowExt.split(',').join('|')+')$','ig');
		if(!validFileExtRe.test(_strPath)){
			(_fAlert)(String.format("只支持上传\"{0}\"格式的文件！",sAllowExt));
			return false;
		}
		return true;
	},
	fileUploadedAlert : function(sResponseText,_fNoErr,_fErr,_fAlert){
		if(sResponseText.match(/<!--ERROR-->/img)){
			var texts = sResponseText.split('<!--##########-->');
			_fAlert = _fAlert||window.$alert||alert;
			if(texts[0]==0){
				(_fAlert)(texts[1]);
			}
			else{
				if(window.FaultDialog){
					FaultDialog.show({
						code		: texts[0],
						message		: texts[1],
						detail		: texts[2],
						suggestion  : ''
					}, '与服务器交互时出错啦');
				}
			}
			if(_fErr)(_fErr)();
			else if(window.ProcessBar){
				ProcessBar.close();
			}
			return true;
		}
		else{
			(_fNoErr)();
		}
		return false;
	}
}

var Position = {
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
}

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

Ext.applyIf(Element, {
	next : function(_dom){
		if(_dom == null) return null;
		var node = _dom.nextSibling;
		while(node && node.nodeType != 1){
			node = node.nextSibling;
		}
		//fix <p>ab<b>sf</p>ddsf</b>sd<div>dsf</div>d
		return ((node == null)||(node.parentNode != _dom.parentNode)) ? null : node;
	},
	previous : function(_dom){
		if(_dom == null) return null;
		var node = _dom.previousSibling;
		while(node && node.nodeType != 1){
			node = node.previousSibling;
		}
		return node;
	},
	first : function(_dom){
		if(_dom == null) return null;
		var nodes = _dom.childNodes;
		for (var i = 0, len = nodes.length; i < len; i++){
			if(nodes[i].nodeType == 1){
				return nodes[i];
			}
		}
		return null;
	},
	last : function(_dom){
		if(_dom == null) return null;
		var nodes = _dom.childNodes;
		for (var i = nodes.length-1; i >= 0; i--){
			if(nodes[i].nodeType == 1){
				return nodes[i];
			}
		}
		return null;
	}
});

Ext.myExtend = function(){
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
			io.call(sb, sp);
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
}();
Ext.ns('Class');
Class.create = function(){
	return function(){
		this.initialize.apply(this, arguments);
	};
}
Ext.copy = function(_sTxt){
	if(Ext.isIE){
		clipboardData.setData('Text',_sTxt);
	}
	else if(Ext.isGecko){
		try{
			netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
			var clipboard = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
			if (!clipboard) return;
			var transferable = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
			if (!transferable) return;
			transferable.addDataFlavor('text/unicode');
			var supportsString 	= Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
			supportsString.data	= _sTxt;
			transferable.setTransferData("text/unicode",supportsString,_sTxt.length*2);
			clipboard.setData(transferable,null,Components.interfaces.nsIClipboard.kGlobalClipboard);
		}catch(err){}
	}
};
function closeMyWindow(){
    window.open("", "_self");//fix ie7
	window.close();
}
Ext.kaku = function(k, scope){
	if(!Ext.isFunction(k))return k;
	var callArgs = Array.prototype.slice.call(arguments, 2);
	return k.apply(scope, callArgs);
}
Ext.isTrue = function(value){
	return value==true || value=='true' || parseInt(value, 10)>0;
}
if(!document.all){//fix not ie
	document.all = function(sName){
		return document.getElementById(sName) || document.getElementsByName(sName)[0];
	};
}
if(!Ext.isIE){
	HTMLElement.prototype.fireEvent = function(sType){
		sType = sType.replace(/^on/, "");
		var evtObj = document.createEvent('MouseEvents');     
		evtObj.initMouseEvent(sType, true, true, document.defaultView, 1, 0, 0, 0, 0, false, false, true, false,   0, null);     
		this.dispatchEvent(evtObj);
	};
}

(function(){
	window.myObject = {};
myObject.each = function(object, iterator){
	try {
		for (var sName in object){
			iterator(object[sName], sName);
		}
	} catch (e) {
		if (e != Ext.$break) throw e;
	}
};
myObject.apply = function(o, c, defaults){
    if(defaults){
        Ext.apply(o, defaults);
    }
    if(o && c && typeof c == 'object'){
        for(var p in c){
            o[p] = c[p];
        }
    }
    return o;
};
myObject.clone = function(_o,_bDeep){
	if(typeof _o!='object'){
		return _o;
	}
	if(!_bDeep){
		if(Array.isArray(_o)){
			return Array.apply(null,_o);
		}
		return myObject.extend({},_o);
	}
	else{
		var oReturn = null;
		if(Array.isArray(_o)){
			oReturn = [];
			for(var i=0;i<_o.length;i++){
				oReturn.push(myObject.clone(_o[i],true));
			}
		}
		else{
			oReturn = {};
			for(var prop in _o){
				oReturn[prop] = myObject.clone(_o[prop],true);
			}
		}
		return oReturn;
	}
};
myObject.isObject = function(_object){
	return _object && !Ext.isSimpType(_object)
				&& !Ext.isArray(_object) && !Ext.isFunction(_object)
				&& !_object.nodename && !_object.nodetype;
}
myObject._parseSource=function(e){
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
			var child = myObject._parseSource(e[name]);
			if(child===false) continue;
			t.push(name + ':' + child);
		}
		return '{' + t.join(',') + '}';
	}
	if(Ext.isArray(e)){
		var tmp = e.map(function(f){
			return myObject._parseSource(f);
		});
		return '[' + tmp.join(',') + ']';
	}
	return false;
};
myObject.parseSource = function(o){
	try{
		return o.toSource();
	}catch(err){
		var sResult = myObject._parseSource(o);
		if(sResult!==false)return sResult;
		return o.toString();
	}
};
myObject.deepClone = function(obj){
	if(obj==null)return null;
	if(Ext.isSimpType(obj))return obj;
	var result = null;
	if(Ext.isArray(obj)){
		result = [];
		for(var i=0,n=obj.length;i<n;i++){
			result.push(myObject.deepClone(obj[i]));
		}
		return result;
	}
	if(!Ext.isObject(obj))return obj;
	result = {};
	for(var name in obj){
		result[name] = myObject.deepClone(obj[name]);
	}
	return result;
}  

})();
