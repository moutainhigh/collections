var ua = navigator.userAgent.toLowerCase();
var isStrict = document.compatMode == "CSS1Compat",
	isOpera = ua.indexOf("opera") > -1,
	isChrome = ua.indexOf("chrome") > -1,
	isSafari = (/webkit|khtml/).test(ua),
	isSafari3 = isSafari && ua.indexOf('webkit/5') != -1,
	isIE = !isOpera && ua.indexOf("msie") > -1,
	isIE7 = !isOpera && ua.indexOf("msie 7") > -1,
	isIE9 = !isOpera && ua.indexOf("msie 9") > -1,
	isIE8 = !isOpera && (ua.indexOf("msie 8") > -1),
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
for(var i=0;i<vars.length;i++)
	Ext[vars[i]] = window[vars[i]];
function defExtCss(){
	var inited, initExtCssThreadId;
    window.initExtCss = function(){
        var bd = document.body;
        if(!bd){ return false; }
		clearInterval(initExtCssThreadId);
		if(bd.getAttribute('_cssrender') || inited) return true;
		inited = true;

		var cls = [];
		if(Ext.isIE)cls.push("ext-ie");
		if(Ext.isIE6)cls.push("ext-ie6");
		if(Ext.isIE7)cls.push("ext-ie7");
		if(Ext.isIE8)cls.push("ext-ie8");
		if(Ext.isIE9)cls.push("ext-ie9");

		if(Ext.isGecko)cls.push("ext-gecko");
		if(Ext.isGecko2)cls.push("ext-gecko2");
		if(Ext.isGecko3)cls.push("ext-gecko3");

		if(Ext.isOpera)cls.push("ext-opera");
		if(Ext.isSafari)cls.push("ext-safari");
		
		if(Ext.isStrict){
            var p = bd.parentNode;
            if(p){
                p.className += ' ext-strict';
            }
        }
		cls.push('res-'+screen.width+"x"+screen.height);
        bd.className += " " + cls.join(' ');
		bd.setAttribute('_cssrender', 1);
        return true;
    }
	initExtCssThreadId = setInterval(initExtCss, 50);
};
defExtCss();
(function(){
	var __genId = 1;
	Ext.genId = function(){
		return 'myext-' + __genId++;
	}
	Ext.getId = function(el){
		return el.id = el.id || Ext.genId();
	}
})();
Ext.ns('Class', 'Ext.EventManager');
Ext.EventManager.listeners = {};
Ext.EventManager.on = function(el, ename, fn, scope, opt){
	var el = $(el), ls = Ext.EventManager.listeners;
	var id = Ext.getId(el), l1 = ls[id] = ls[id] || {};
	var l2 = l1[ename] = l1[ename] || [];
	var wrap = function(ev){
		var nev = extEvent(ev || window.event);
		fn.call(scope, nev, nev.target, opt);
	};
	l2.push([fn, scope, wrap]);
	return Event.observe(el, ename, wrap);
}
Ext.EventManager.un = function(el, ename, fn, scope){
	var el = $(el), ls = Ext.EventManager.listeners;
	var id = Ext.getId(el), l1 = ls[id] = ls[id] || {};
	var l2 = l1[ename] = l1[ename] || [], wrap = fn; 
	for(var i=0;i<l2.length;i++){
		var l3 = l2[i];
		if(l3[0]==fn && l3[1]==scope){
			wrap = l3[2];
			l2.splice(i, 1);
			break;
		}
	}
	if(wrap)Event.stopObserving(el, ename, wrap);
}
Class.create = function(){
	return function(){
		this.initialize.apply(this, arguments);
	}
}
Ext.ns('Ext.Try.these');
Ext.Try.these = Try;

function defExtExtend(){
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
}
Ext.apply(Ext, {
	$break : {},
	emptyFn : function(){},
	errorMsg : function(e){
		return e.stack || '';
	},
	isDebug : function(){
		try{
			return (window.WCMConstants && WCMConstants.DEBUG)
						|| !!getParameter('isdebug', top.location.search);
		}catch(error){//避免跨越访问错误
			return false;
		}
	},
	extend : defExtExtend(),
	applyIf : function(o, c){
		if(!o || !c)return o;
		for(var p in c){
			if(typeof o[p] == "undefined")o[p] = c[p];
		}
		return o;
	},
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
	isString : function(o){
		return (typeof o=="string") || (o!=null && o.split && o.match);
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
	type : function(o){
		if(o === undefined || o === null)return false;
		if(o.htmlElement)return 'element';
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
	isTrans : function(o){
		return (typeof o=="object") && o.responseText;
	},
	result : function(_trans){
		var text = _trans.responseText;
		if(!text)return null;
		return text.replace(/^\s*<result>(.*)<\/result>\s*$/ig, '$1');
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
	getBody : function(){
		return Ext.get(document.body);
	}
});
String.isString = Ext.isString;
String.scriptFragment = '(?:<script.*?>)(?:[\f\n\r\t\v]*<\!--)?((\n|\r|.)+?)(?:[\f\n\r\t\v]*\/{0,2}-->[\f\n\r\t\v]*)?(?:<\/script>)';
Ext.applyIf(String.prototype, {
	camelize : function(){
		return this.charAt(0).toUpperCase() + this.substring(1).toLowerCase();
	},
	startsWith : function(sStart) {
		return (this.substr(0,sStart.length)==sStart);
	},
	byteLength : function(){
		var length = 0;
		this.replace(/[^\x00-\xff]/g,function(){length++;});
		return this.length+length;
	},
	equalsI : function(_sc){
		return _sc!=null && this.toLowerCase()==(''+_sc).toLowerCase();
	},
	parseQuery : function() {
		var pairs = this.match(/^\??(.*)$/)[1].split('&'), rst = {}, pair;
		for(var i=0,n=pairs.length;i<n;i++){
			pair = pairs[i].split('=');
			rst[pair[0]] = decodeURIComponent(pair[1]);
		};
		return rst;
	},
	extractScripts: function() {
		var matchAll = new RegExp(String.scriptFragment, 'img');
		var matchOne = new RegExp(String.scriptFragment, 'im');
		var arr = this.match(matchAll) || [], rst = [], t;
		for(var i=0;i<arr.length;i++){
			t = arr[i].match(matchOne)[1];
			if(t)rst.push(t);
		}
		return rst;
	},
	evalScripts: function() {
		var arr = this.extractScripts();
		for(var i=0;i<arr.length;i++){
			eval(arr[i]);
		}
	},
	stripScripts : function(){
		return this.replace(new RegExp(String.scriptFragment, 'img'), '');
	},
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
	}
});
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
	include : function(o){
		return this.indexOf(o)!=-1;
	}
});
function $toQueryStr(p0, up, encode){
	var p = p0 || {}, arr = [], fn = encode===false ? Ext.kaku : encodeURIComponent;
	for (var param in p){
		var v = p[param];
		if(!Ext.isSimpType(v) && !Ext.isArray(v))continue;
		param = up ? param.trim().toUpperCase() : param.trim();
		arr.push(param + '=' + fn(v + ''));
	}
	return arr.join('&');
}
function $toQueryStr2(p0, up){
	return $toQueryStr(p0, up, false);
}
Ext.ns('Ext.Msg');
var arr = ['error', '$alert', '$success', '$fail', '$error', 'timeAlert', '$timeAlert',
				'd$alert', 'confirm', '$confirm', 'd$error', 'report', 'fault', 'show', 'warn'];
Ext.Msg.alert = function(msg, fn, scope){
	alert(msg);
	(fn||Ext.emptyFn)();
}
for(var i=0,n=arr.length;i<n;i++){
	Ext.Msg[arr[i]] = function(){
		Ext.Msg.alert.apply(Ext.Msg, arguments);
	};
}
Ext.apply(Ext.myEl.prototype, {
	update : function(html, loadScripts, callback){
		Element.update(this.dom, html, loadScripts, callback);
	},
	show : function(html, loadScripts, callback){
		Element.show(this.dom);
	},
	hide : function(html, loadScripts, callback){
		Element.hide(this.dom);
	},
	isVisible : function(){
		return Element.visible(this.dom);
	},
	hasClass : function(cls){
		return Element.hasClassName(this.dom, cls);
	},
	addClass : function(cls){
		Element.addClassName(this.dom, cls);
	},
	removeClass : function(cls){
		Element.removeClassName(this.dom, cls);
	},
	replaceClass : function(oldCls, newCls){
		this.removeClass(oldCls);
        this.addClass(newCls);
	},
	remove : function(){
		Ext.removeNode(this.dom);
	},
	contains : function(t){
		return Element.resides(t, this.dom);
	}
});
var btnMap = Ext.isIE ? {1:0,4:1,2:2} :
			(Ext.isSafari ? {1:0,2:1,3:2} : {0:0,1:1,2:2});
Ext.apply(Ext.myEvent.prototype, {
	getPoint : function(){
		return {x:this.pointer[0], y:this.pointer[1]};
	},
	button0 : function(e){
		var button = e.button ? btnMap[e.button] : (e.which ? e.which-1 : -1);
		if(e.type == 'click' && button == -1){
			button = 0;
		}
		return button;
	}
});
Ext.kaku = function(k, scope){
	if(!Ext.isFunction(k))return k;
	var callArgs = Array.prototype.slice.call(arguments, 2);
	return k.apply(scope, callArgs);
}
Ext.isTrue = function(value){
	return value==true || value=='true' || parseInt(value, 10)>0;
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
Ext.apply(Element, {
	toggle : function(el){
		el = $(el);
		return Element[Element.visible(el) ? 'hide' : 'show'](el);
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
	resides : function(t, p){
		while(t && t.tagName!='BODY'){
			if(t==p)return true;
			t = t.parentNode;
		}
		return false;
	}
});
Ext.apply(Function.prototype, {
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
if(Ext.isIE) {
    function fnCleanUp() {
        var p = Function.prototype;
        delete p.createSequence;
        delete p.createInterceptor;
        window.detachEvent("onunload", fnCleanUp);
    }
    window.attachEvent("onunload", fnCleanUp);
}
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
		if(Ext.isSimpType(o) || Ext.isDom(o))return o;
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
function setIFrameByPost(iFrameId, sUrl, aPostParams){
	//1.build params and url.
	var result = sUrl.match(/^([^\?]+)\??(.*)$/);
	var params = {};
	if(result && result[1]) sUrl = result[1];
	if(result && result[2]) params = result[2].parseQuery();
	//2.create form 
	var aParams = [];
	var sId = 'frm-' + new Date().getTime();
	var aHtml = ['<form method="post" id="', sId, '" target="', iFrameId, '">'];
	for (var sKey in params){
		if(!aPostParams || aPostParams.include(sKey)){
			aHtml.push('<input type="hidden" name="', sKey + '" value="', $transHtml(params[sKey]) + '" />');
		}else{
			aParams.push(sKey + "=" + params[sKey]);
		}
	}
	aHtml.push('</form>');
	var dom = document.createElement("div");
	document.body.appendChild(dom);
	dom.innerHTML = aHtml.join("");
	$(sId).action = sUrl + "?" + aParams.join("&");
	//3.submit the form and get the iframe content.
	$(sId).submit();
	//4.destroy form
	document.body.removeChild($(sId).parentNode);
}