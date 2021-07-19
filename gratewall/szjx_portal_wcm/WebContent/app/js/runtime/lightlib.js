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
(function(){
if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1) return;
var _emptyTags = {
"IMG": true,
"BR": true,
"INPUT": true,
"META": true,
"LINK": true,
"PARAM": true,
"HR": true
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
}else{
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
Event.observe(window,"load",fireReady);
})();
function fireReady(){
if(!DOM.isReady && DOM.readyEvents){
DOM.isReady = true;
for (var i = 0; i < DOM.readyEvents.length; i++){
DOM.readyEvents[i]();
}
DOM.readyEvents.length = 0;
}
}
return {
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
}catch(error){
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
var nWidth = window.screen.width - 12, nHeight = window.screen.height - 60;
var nLeft = 0, nTop = 0, sName = _sName || "";
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
}
}
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
var result = sUrl.match(/^([^\?]+)\??(.*)$/);
var params = {};
if(result && result[1]) sUrl = result[1];
if(result && result[2]) params = result[2].parseQuery();
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
$(sId).submit();
document.body.removeChild($(sId).parentNode);
}

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
t += el.offsetTop || 0;
l += el.offsetLeft || 0;
if (el.offsetParent==document.body
&& getStyle(el, 'position')=='absolute') break;
} while (el = el.offsetParent);
el = oriEl;
do {
t -= el.scrollTop || 0;
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
if (ps == 'relative' || ps== 'absolute')continue;
return el;
}
return bd;
},
clone: function(es, et, cfg) {
cfg = Object.extend({
setLeft: true,
setTop: true,
setWidth: true,
setHeight: true,
offsetTop: 0,
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
if(cfg.setLeft) o.left = (p[0] - delta[0] + cfg.offsetLeft) + 'px';
if(cfg.setTop) o.top = (p[1] - delta[1] + cfg.offsetTop) + 'px';
if(cfg.setWidth) o.width = es.offsetWidth + 'px';
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
}
return [l, t]
},
within: function(el, x, y) {
this.offset = this.cumulativeOffset(el);
return (y >= this.offset[1] &&
y < this.offset[1] + el.offsetHeight &&
x >= this.offset[0] &&
x < this.offset[0] + el.offsetWidth);
},
cumulativeOffset: function(el) {
var t = 0, l = 0;
do {
t += el.offsetTop || 0;
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
c.scrollTop = c.scrollTop;
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
if(window.HTMLElement){
HTMLElement.prototype.fireEvent = function(sType){
sType = sType.replace(/^on/, "");
var evtObj = document.createEvent('MouseEvents');
evtObj.initMouseEvent(sType, true, true, document.defaultView, 1, 0, 0, 0, 0, false, false, true, false, 0, null);
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

