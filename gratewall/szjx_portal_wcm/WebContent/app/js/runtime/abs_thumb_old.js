Ext.ns("wcm", "wcm.util");
wcm.util.Observable = function() {
this.events = {};
if (this.listeners) {
this.on(this.listeners);
delete this.listeners;
}
};
wcm.util.Observable.prototype = {
fireEvent : function() {
if (this.eventsSuspended !== true) {
var ce = this.events[arguments[0].toLowerCase()];
if (typeof ce == "object") {
return ce.fire.apply(ce, Array.prototype.slice.call(arguments,
1));
}
}
return true;
},
filterOptRe : /^(?:scope|delay|buffer|single)$/,
addListener : function(eventName, fn, scope, o) {
if (typeof eventName == "object") {
o = eventName;
for (var e in o) {
if (this.filterOptRe.test(e)) {
continue;
}
if (typeof o[e] == "function") {
this.addListener(e, o[e], o.scope, o);
} else {
this.addListener(e, o[e].fn, o[e].scope, o[e]);
}
}
return;
}
o = (!o || typeof o == "boolean") ? {} : o;
eventName = eventName.toLowerCase();
var ce = this.events[eventName] || true;
if (typeof ce == "boolean") {
ce = new wcm.util.Event(this, eventName);
this.events[eventName] = ce;
}
ce.addListener(fn, scope, o);
},
removeListener : function(eventName, fn, scope) {
var ce = this.events[eventName.toLowerCase()];
if (typeof ce == "object") {
ce.removeListener(fn, scope);
}
},
purgeListeners : function() {
for (var evt in this.events) {
if (typeof this.events[evt] == "object") {
this.events[evt].clearListeners();
}
}
},
relayEvents : function(o, events) {
var createHandler = function(ename) {
return function() {
return this.fireEvent.apply(this, Ext.combine(ename,
Array.prototype.slice.call(arguments, 0)));
};
};
for (var i = 0, len = events.length; i < len; i++) {
var ename = events[i];
if (!this.events[ename]) {
this.events[ename] = true;
};
o.on(ename, createHandler(ename), this);
}
},
addEvents : function(o) {
if (!this.events) {
this.events = {};
}
if (typeof o == 'string') {
for (var i = 0, a = arguments, v; v = a[i]; i++) {
if (!this.events[a[i]]) {
o[a[i]] = true;
}
}
} else {
Ext.applyIf(this.events, o);
}
},
hasListener : function(eventName) {
var e = this.events[eventName];
return typeof e == "object" && e.listeners.length > 0;
},
suspendEvents : function() {
this.eventsSuspended = true;
},
resumeEvents : function() {
this.eventsSuspended = false;
},
getMethodEvent : function(method) {
if (!this.methodEvents) {
this.methodEvents = {};
}
var e = this.methodEvents[method];
if (!e) {
e = {};
this.methodEvents[method] = e;
e.originalFn = this[method];
e.methodName = method;
e.before = [];
e.after = [];
var returnValue, v, cancel;
var obj = this;
var makeCall = function(fn, scope, args) {
if ((v = fn.apply(scope || obj, args)) !== undefined) {
if (typeof v === 'object') {
if (v.returnValue !== undefined) {
returnValue = v.returnValue;
} else {
returnValue = v;
}
if (v.cancel === true) {
cancel = true;
}
} else if (v === false) {
cancel = true;
} else {
returnValue = v;
}
}
}
this[method] = function() {
returnValue = v = undefined;
cancel = false;
var args = Array.prototype.slice.call(arguments, 0);
for (var i = 0, len = e.before.length; i < len; i++) {
makeCall(e.before[i].fn, e.before[i].scope, args);
if (cancel) {
return returnValue;
}
}
if ((v = e.originalFn.apply(obj, args)) !== undefined) {
returnValue = v;
}
for (var i = 0, len = e.after.length; i < len; i++) {
makeCall(e.after[i].fn, e.after[i].scope, args);
if (cancel) {
return returnValue;
}
}
return returnValue;
};
}
return e;
},
beforeMethod : function(method, fn, scope) {
var e = this.getMethodEvent(method);
e.before.push({
fn : fn,
scope : scope
});
},
afterMethod : function(method, fn, scope) {
var e = this.getMethodEvent(method);
e.after.push({
fn : fn,
scope : scope
});
},
removeMethodListener : function(method, fn, scope) {
var e = this.getMethodEvent(method);
for (var i = 0, len = e.before.length; i < len; i++) {
if (e.before[i].fn == fn && e.before[i].scope == scope) {
e.before.splice(i, 1);
return;
}
}
for (var i = 0, len = e.after.length; i < len; i++) {
if (e.after[i].fn == fn && e.after[i].scope == scope) {
e.after.splice(i, 1);
return;
}
}
}
};
wcm.util.Observable.prototype.on = wcm.util.Observable.prototype.addListener;
wcm.util.Observable.prototype.un = wcm.util.Observable.prototype.removeListener;
wcm.util.Observable.capture = function(o, fn, scope) {
o.fireEvent = o.fireEvent.createInterceptor(fn, scope);
};
wcm.util.Observable.releaseCapture = function(o) {
o.fireEvent = wcm.util.Observable.prototype.fireEvent;
};
(function() {
var createBuffered = function(h, o, scope) {
var task = new wcm.util.DelayedTask();
return function() {
task.delay(o.buffer, h, scope, Array.prototype.slice.call(
arguments, 0));
};
};
var createSingle = function(h, e, fn, scope) {
return function() {
e.removeListener(fn, scope);
return h.apply(scope, arguments);
};
};
var createDelayed = function(h, o, scope) {
return function() {
var args = Array.prototype.slice.call(arguments, 0);
setTimeout(function() {
h.apply(scope, args);
}, o.delay || 10);
};
};
wcm.util.Event = function(obj, name) {
this.name = name;
this.obj = obj;
this.listeners = [];
};
wcm.util.Event.prototype = {
addListener : function(fn, scope, options) {
scope = scope || this.obj;
if (!this.isListening(fn, scope)) {
var l = this.createListener(fn, scope, options);
if (!this.firing) {
this.listeners.push(l);
} else {
this.listeners = this.listeners.slice(0);
this.listeners.push(l);
}
}
},
createListener : function(fn, scope, o) {
o = o || {};
scope = scope || this.obj;
var l = {
fn : fn,
scope : scope,
options : o
};
var h = fn;
if (o.delay) {
h = createDelayed(h, o, scope);
}
if (o.single) {
h = createSingle(h, this, fn, scope);
}
if (o.buffer) {
h = createBuffered(h, o, scope);
}
l.fireFn = h;
return l;
},
findListener : function(fn, scope) {
scope = scope || this.obj;
var ls = this.listeners;
for (var i = 0, len = ls.length; i < len; i++) {
var l = ls[i];
if (l.fn == fn && l.scope == scope) {
return i;
}
}
return -1;
},
isListening : function(fn, scope) {
return this.findListener(fn, scope) != -1;
},
removeListener : function(fn, scope) {
var index;
if ((index = this.findListener(fn, scope)) != -1) {
if (!this.firing) {
this.listeners.splice(index, 1);
} else {
this.listeners = this.listeners.slice(0);
this.listeners.splice(index, 1);
}
return true;
}
return false;
},
clearListeners : function() {
this.listeners = [];
},
fire : function() {
var ls = this.listeners, scope, len = ls.length;
if (len > 0) {
this.firing = true;
var args = Array.prototype.slice.call(arguments, 0);
for (var i = 0; i < len; i++) {
var l = ls[i];
try{
if (l.fireFn
.apply(l.scope || this.obj || window, arguments) === false) {
this.firing = false;
return false;
}
}catch(error){
if(Ext.isDebug()){
alert("error:" + error.message + "\n" + l.fireFn);
throw error;
}
}
}
this.firing = false;
}
return true;
}
};
})();
Ext.ns("wcm.dd.DragDrop");
wcm.dd.Caches = [];
Event.observe(window, 'unload', function(){
var caches = wcm.dd.Caches;
for (var i = 0; i < caches.length; i++){
try{
caches[i].destroy();
}catch(error){
}
caches[i]= null;
}
wcm.dd.Caches = [];
});
wcm.dd.BaseDragDrop = function(config){
this.init(config);
wcm.dd.Caches.push(this);
};
Ext.extend(wcm.dd.BaseDragDrop, wcm.util.Observable, {
id : null,
dragElId : null,
handleElId : null,
deltaX : 0,
deltaY : 0,
lastPointer : [],
setCapture : function(element, win){
if(this.captureEnable == false) return;
if(element.setCapture){
element.setCapture();
}else if(window.captureEvents){
if(!win){
var doc = element.ownerDocument || document;
win = doc.parentWindow || doc.defaultView;
}
win.captureEvents(18);
}
},
releaseCapture : function(element, win){
if(this.captureEnable == false) return;
if(element.releaseCapture){
element.releaseCapture();
}else if(window.releaseEvents){
if(!win){
var doc = element.ownerDocument || document;
win = doc.parentWindow || doc.defaultView;
}
win.releaseEvents(18);
}
},
init : function(config){
this.id = this.dragElId = this.handleElId = config["id"];
Ext.apply(this, config);
Ext.EventManager.on(this.handleElId, "mousedown", this.handleMouseDown, this);
this.addEvents('beforestartdrag', 'startdrag', 'drag', 'enddrag', 'dispose', 'destroy');
},
b4StartDrag : function(e){
var dom = Event.element(e);
if(this.isValidChild(dom)) return true;
return false;
},
isValidChild : function(dom){
while(dom && dom.tagName != "BODY"){
if(dom.id == this.handleElId) return true;
dom = dom.parentNode;
}
return false;
},
handleMouseDown : function(e){
Event.stop(e.browserEvent);
if(e.button != 0) return;
if(!this.b4StartDrag(e)) return;
if(this.fireEvent('beforestartdrag', e) === false) return;
this.dragging = false;
var el = $(this.handleElId);
var doc = el.ownerDocument || document;
Ext.EventManager.on(doc, "mousemove", this.handleMouseMove, this);
Ext.EventManager.on(doc, "mouseup", this.handleMouseUp, this);
this.setCapture(el);
el = $(this.dragElId);
var xy = this.getXY(e);
this.lastPointer = xy;
this.deltaX = xy[0] - parseInt(Element.getStyle(el, 'left'), 10);
this.deltaY = xy[1] - parseInt(Element.getStyle(el, 'top'), 10);
},
getXY : function(e){
return [parseInt(e.getPageX(), 10), parseInt(e.getPageY(), 10)];
},
startDrag : function(x, y, e){
this.fireEvent('startdrag', x, y, e, this);
},
drag : function(x, y, e){
this.fireEvent('drag', x, y, e, this);
},
handleMouseMove : function(e){
var xy = this.getXY(e);
if(Math.abs(xy[0] - this.lastPointer[0]) < 2 && Math.abs(xy[1] - this.lastPointer[1]) < 2){
return;
}
if(!this.dragging){
this.dragging = true;
this.startDrag(xy[0], xy[1], e);
}
this.drag(xy[0], xy[1], e);
},
endDrag : function(x, y, e){
this.fireEvent('enddrag', x, y, e, this);
},
release : function(e){},
dispose : function(e){
this.fireEvent('dispose');
this.release(e);
var el = $(this.handleElId);
var doc = el.ownerDocument || document;
Ext.EventManager.un(doc, "mousemove", this.handleMouseMove, this);
Ext.EventManager.un(doc, "mouseup", this.handleMouseUp, this);
this.releaseCapture(el);
},
handleMouseUp : function(e){
if(this.dragging){
var xy = this.getXY(e);
this.endDrag(xy[0], xy[1], e);
}
this.dispose(e);
},
destroy : function(){
this.fireEvent('destroy');
Ext.EventManager.un(this.handleElId, "mousedown", this.handleMouseDown, this);
this.purgeListeners();
delete this.dragging;
delete this.handleElId;
delete this.dragElId;
delete this.id;
}
});
wcm.dd.DragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
drag : function(x, y, e){
wcm.dd.DragDrop.superclass.drag.apply(this, arguments);
var dom = $(this.dragElId);
dom.style.left = (x - this.deltaX) + "px";
dom.style.top = (y - this.deltaY) + "px";
}
});
Ext.ns("wcm", "wcm.util", "wcmXCom");
Ext.apply(Ext, {
removeNode : Ext.isIE ? function(){
var removeIframes = function(dom){
if(!dom) return;
if(dom.tagName == "IFRAME"){
dom.src = Ext.blankUrl || '';
return;
}
var aIframes = dom.getElementsByTagName("iframe");
for (var i = 0; i < aIframes.length; i++){
try{
if(aIframes[i].contentWindow && aIframes[i].contentWindow.onFrameClose){
aIframes[i].contentWindow.onFrameClose();
}
}catch(error){
}
aIframes[i].src = Ext.blankUrl || '';
}
};
return function(n){
if(n && n.tagName != 'BODY'){
removeIframes(n);
var d = n.ownerDocument.createElement('div');
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
wcm.ComponentMgr = function(){
var all = {};
var types = {};
return {
register : function(c){
all[c.getId()] = c;
},
unregister : function(c){
delete all[c.getId()];
},
all : function(){
return all;
},
get : function(id){
if(!id) return null;
return all[id.id || id];
},
registerType : function(type, cls){
types[type.toUpperCase()] = cls;
cls.type = type;
},
create : function(config, defaultType){
return new types[(config["type"] || defaultType).toUpperCase()](config);
}
};
}();
(function(){
wcm.ComponentMgr0 = Ext.apply({}, wcm.ComponentMgr);
var topMgr = function(){
if(!window.$MsgCenter) return wcm.ComponentMgr0;
var acutalTop = $MsgCenter.getActualTop();
if(acutalTop == window || !acutalTop.wcm.ComponentMgr) return wcm.ComponentMgr0;
return acutalTop.wcm.ComponentMgr;
};
for (var sFn in wcm.ComponentMgr){
wcm.ComponentMgr[sFn] = function(){
var $mgr = topMgr();
return $mgr[this].apply($mgr, arguments);
}.bind(sFn);
}
})();
wcmXCom.get = wcm.ComponentMgr.get;
wcmXCom.reg = wcm.ComponentMgr.registerType;
(function(){
var cache = {};
wcm.GarbageCollector = {
register : function(c){
cache[c.getId()] = c;
},
unregister : function(c){
delete cache[c.getId()];
},
collect : function(){
for (var id in cache){
var c = cache[id];
if(c.autoDestory){
c.destroy();
delete cache[id];
}
}
}
};
Event.observe(window, 'beforeunload', wcm.GarbageCollector.collect);
})();
wcm.Component = function(config){
Ext.apply(this, config);
wcm.ComponentMgr.register(this);
wcm.GarbageCollector.register(this);
wcm.Component.superclass.constructor.call(this);
this.initComponent();
};
Ext.apply(wcm.Component, {
AUTO_ID : 2
});
Ext.extend(wcm.Component, wcm.util.Observable, {
hidden : false,
disabled : false,
rendered : false,
autoDestory : true,
hideMode : 'display',
disabledCls : 'wcm-disabled',
removeNode : true,
initComponent : function(){
this.addEvents(
'beforeshow',
'show',
'beforehide',
'hide',
'disable',
'enable',
'beforerender',
'render',
'beforedestroy',
'destroy'
);
},
getId : function() {
return this.id || (this.id = "wcm-comp-" + window.$MsgCenter.genId());//(++wcm.ComponentMG.AUTO_ID));
},
getElement : function(id){
var id = id || this.getId();
return this.getWin().$(id);
},
getDoc : function(){
return this.getWin().document;
},
getWin : function(){
var win = this.getWin0();
var container = this.renderTo || this.container;
if(container){
var doc = win.$(container).ownerDocument;
return doc.parentWindow || doc.defaultView;
}
return win;
},
getWin0 : function(){
return window;
},
show : function(){
if(this.fireEvent("beforeshow", this) !== false){
this.render();
if(this.rendered){
this.onShow();
}
this.hidden = false;
this.fireEvent("show", this);
}
return this;
},
onShow : function(){
Element.removeClassName(this.getElement(), "wcm-hide-" + this.hideMode);
},
hide : function(){
if(this.fireEvent("beforehide", this) !== false){
if(this.rendered){
this.onHide();
}
this.hidden = true;
this.fireEvent("hide", this);
}
return this;
},
onHide : function(){
Element.addClassName(this.getElement(), "wcm-hide-" + this.hideMode);
},
setVisible : function(visible){
return this[visible ? 'show' : 'hide']();
},
enable : function(){
if(this.rendered){
this.onEnable();
}
this.disabled = false;
this.fireEvent('enable', this);
return this;
},
onEnable : function(){
var dom = this.getElement();
Element.removeClassName(dom, this.disabledCls);
dom.disabled = false;
},
disable : function(){
if(this.rendered){
this.onDisable();
}
this.disabled = true;
this.fireEvent('disable', this);
return this;
},
onDisable : function(){
var dom = this.getElement();
Element.addClassName(dom, this.disabledCls);
dom.disabled = true;
},
setDisabled : function(disabled){
return this[disabled ? "disable" : "enable"]();
},
focus : function(){
if(this.rendered){
try{
this.getElement().focus();
}catch(error){
}
}
return this;
},
blur : function(){
if(this.rendered){
try{
this.getElement().blur();
}catch(error){
}
}
return this;
},
render : function(){
if(!this.rendered && this.fireEvent("beforerender", this) !== false){
this.rendered = true;
this.onRender();
this.fireEvent("render", this);
this.afterRender(this.container);
if (this.hidden) {
this.hide();
}
if (this.disabled) {
this.disable();
}
}
return this;
},
onRender : Ext.emptyFn,
afterRender : Ext.emptyFn,
destroy : function() {
if (this.fireEvent("beforedestroy", this) !== false) {
this.beforeDestroy();
if (this.rendered) {
var dom = this.getElement();
if(dom){
Event.stopAllObserving(dom);
if(this.removeNode){
Element.remove(dom);
}
}
}
this.onDestroy();
wcm.ComponentMgr.unregister(this);
wcm.GarbageCollector.unregister(this);
this.fireEvent("destroy", this);
this.purgeListeners();
}
},
beforeDestroy :Ext.emptyFn,
onDestroy : Ext.emptyFn
});
wcmXCom.reg('component', wcm.Component);
wcm.BoxComponent = Ext.extend(wcm.Component, {
initComponent : function(){
wcm.BoxComponent.superclass.initComponent.apply(this, arguments);
this.addEvents('resize', 'move');
},
setWidth : function(width){
this.setSize(width);
},
setHeight : function(height){
this.setSize(undefined, height);
},
setSize : function(width, height){
if(!this.rendered){
this.width = width;
this.height = height;
return this;
} 
if(width == this.lastWidth 
&& height == this.lastHeight){
return this;
}
if(width == undefined && height == undefined) return this;
var dom = this.getElement();
if(width){
this.lastWidth = width;
dom.style.width = width;
}
if(height){
this.lastHeight = height;
dom.style.height = height;
}
this.onResize(width, height);
this.fireEvent('resize', this, width, height);
},
setLeft : function(left){
this.setPosition(left);
},
setTop : function(top){
this.setPosition(undefined, top);
},
setPosition : function(left, top){
if(!this.rendered){
this.left = left;
this.top = top;
return this;
}
if(left == this.lastLeft 
&& top == this.lastTop){
return this;
}
if(left == undefined && top == undefined) return this;
var dom = this.getElement();
if(left){
this.lastLeft = left;
dom.style.left = left;
}
if(top){
this.lastTop = top;
dom.style.top = top;
}
this.onPosition(left, top);
this.fireEvent('move', this, left, top);
},
afterRender : function(){
wcm.BoxComponent.superclass.afterRender.apply(this, arguments);
this.setSize(this.width, this.height);
this.setPosition(this.left, this.top);
},
onResize : function(width, height){
},
onPosition : function(left, top){
}
});
wcmXCom.reg('box', wcm.BoxComponent);
wcm.Container = Ext.extend(wcm.BoxComponent, {
initComponent : function(){
wcm.Container.superclass.initComponent.apply(this, arguments);
this.addEvents('add', 'remove');
},
initItems : function(){
if(!this.items){
this.items = [];
}
},
add : function(comp){
var a = arguments;
if(a.length > 1){
for (var i = 0; i < a.length; i++){
this.add(a[i]);
}
return;
} 
if(!this.items){
this.initItems();
}
this.items.push(comp);
comp.ownerCt = this;
this.fireEvent('add', this, comp, this.items.length);
return comp;
},
remove : function(comp){
if(!comp) return;
if(!this.items) return;
this.items.remove(comp);
delete comp.ownerCt;
comp.destroy();
this.fireEvent('remove', this, comp);
},
onRender : function(){
wcm.Container.superclass.onRender.apply(this, arguments);
var items = this.items;
if (items) {
for (var i = 0; i < items.length; i++){
if(!items[i].rendered){
items[i].render(items[i]);
}
}
}
},
beforeDestroy : function() {
var items = this.items;
if (items) {
for (var i = 0; i < items.length; i++){
items[i].destroy();
}
}
delete this.items;
wcm.Container.superclass.beforeDestroy.apply(this, arguments);
}
});
wcmXCom.reg('container', wcm.Container);
(function(){
var htmlTemplate = [
'<div class="{0} l">',
'<div class="r">',
'<div class="c" id="{1}">{2}</div>',
'</div>',
'</div>'
].join("");
wcm.Panel = Ext.extend(wcm.Container, {
baseZIndex : 0,
closable : true,
maskable : true,
closeAction : 'close',
draggable : true,
constrainBound : false, 
initComponent : function(){
wcm.Panel.superclass.initComponent.apply(this, arguments);
},
getDragElId : function(){
return this.header;
},
setTitle : function(title){
Element.update(this.getElement(this.titleId), title);
},
onRender : function(){
wcm.Panel.superclass.onRender.apply(this, arguments);
var id = this.getId();
var container = this.getWin().document.body;
if(this.renderTo){
container = this.getElement(this.renderTo);
}
new Insertion.Bottom(container, String.format('<div class="{0}" id="{1}"></div>', this.baseCls || id, id));
this.initHeader();
this.initBody();
this.initFooter();
this.getElement().style.zIndex = this.baseZIndex + $MsgCenter.genId(100);
if(this.draggable){
var caller = this;
var win = this.getWin();
if(win.wcm.dd){
this.dragger = Ext.apply(new win.wcm.dd.DragDrop({id : id, handleElId : this.getDragElId()}), {
drag : function(x, y, event){
if(caller.constrainBound){
if(x - this.deltaX <= 0) return;
if(y - this.deltaY <= 0) return;
var pageWidth = win.document.documentElement.offsetWidth;
if(x - parseInt(this.deltaX, 10) + parseInt(caller.width,10) >= pageWidth) return;
var pageHeight = win.document.documentElement.offsetHeight;
if(y - parseInt(this.deltaY, 10) + parseInt(caller.height,10) >= pageHeight) return;
}
caller.setPosition((x - this.deltaX) + "px", (y - this.deltaY) + "px");
}
});
}
}
},
center : function(){
var box = this.getCenterXY();
this.setPosition(box.x, box.y);
},
getCenterXY : function(){
var doc = this.getWin().document;
var container = Ext.isIE ? doc.body : doc.documentElement;
if(this.renderTo){
container = this.getElement(this.renderTo);
}
var dom = this.getElement();
var box = Element.getDimensions(dom);
var l = (parseInt(container.clientWidth, 10) - box["width"]) / 2 + parseInt(container.scrollLeft, 10);
var left = l + "px";
var t = (parseInt(container.clientHeight, 10) - box["height"]) / 2 + parseInt(container.scrollTop, 10);
var top = t + "px";
return {x : left, y : top};
},
afterRender : function(){
var box = this.getCenterXY();
if(!this.left){
this.left = box.x;
}
if(!this.top){
this.top = box.y;
}
wcm.Panel.superclass.afterRender.apply(this, arguments);
},
syncShield : function(){
setTimeout(function(){
var dom = this.getElement();
if(!dom) return;
if(!this.shieldId){
this.shieldId = this.getId() + "-sld";
}
var sldDom = this.getElement(this.shieldId);
if(!sldDom){
var doc = this.getWin().document;
sldDom = doc.createElement("iframe");
sldDom.id = this.shieldId;
sldDom.src = Ext.isSecure ? Ext.SSL_SECURE_URL : '';
sldDom.frameBorder = 0;
Element.addClassName(sldDom, "wcm-panel-shield");
dom.parentNode.appendChild(sldDom);
}
this.synZIndex();
if(!this.maskable){
Position.clone(dom, sldDom);
}else{
var doc = this.getDoc();
var container = Ext.isIE ? doc.body : doc.documentElement;
sldDom.style.width = parseInt(container.scrollWidth, 10)+"px";
sldDom.style.height = parseInt(container.scrollHeight, 10)+"px"
}
}.bind(this), 10);
},
onResize : function(){
wcm.Panel.superclass.onResize.apply(this, arguments);
this.syncShield();
},
onPosition : function(){
wcm.Panel.superclass.onPosition.apply(this, arguments);
this.syncShield();
},
synZIndex : function(){
var zIndex = this.baseZIndex + $MsgCenter.genId(100);
this.getElement().style.zIndex = zIndex;
this.getElement(this.shieldId).style.zIndex = zIndex - 1;
},
onShow : function(){
this.synZIndex();
try{
$MsgCenter.cancelKeyDown();
}catch(error){
}
wcm.Panel.superclass.onShow.apply(this, arguments);
var dom = this.getElement(this.shieldId);
Element.removeClassName(dom, "wcm-hide-" + this.hideMode);
},
onHide : function(){
var dom = this.getElement(this.shieldId);
if(!dom || Element.hasClassName(dom, "wcm-hide-" + this.hideMode)){
return;
}
Element.addClassName(dom, "wcm-hide-" + this.hideMode);
wcm.Panel.superclass.onHide.apply(this, arguments);
try{
$MsgCenter.enableKeyDown();
}catch(error){
}
},
close : function(){
if(this.fireEvent("beforeclose", this) !== false){
this.hide();
this.fireEvent('close', this);
this.destroy();
}
},
beforeDestroy : function(){
if(this.dragger){
this.dragger.destroy();
delete this.dragger;
}
if(this.closable && this.closer){
var closer = this.getElement(this.closer);
if(closer){
Event.stopAllObserving(closer);
closer.onblur = null;
}
}
var dom = this.getElement(this.shieldId);
Element.remove(dom);
wcm.Panel.superclass.beforeDestroy.apply(this, arguments);
},
initHeader : function(){
var dom = this.getElement();
var id = this.getId();
this.titleId = id + "-title";
this.toolsId = id + "-tools";
var sContent = String.format([
'<div class="spt"></div>',
'<div class="title" id="{0}"></div>',
'<div class="tools" id="{1}"></div>'
].join(""), this.titleId, this.toolsId);
this.header = id + "-header";
new Insertion.Bottom(dom, String.format(htmlTemplate, "header", this.header, sContent));
if(this.closable){
this.closer = id + "-closer";
this.addTool('close', this.closer, function(event){
Event.stop(event);
this[this.closeAction]();
}.bind(this));
}
if(this.title){
this.setTitle(this.title);
}
},
addTool : function(cls, id, cmd){
var tools = this.getElement(this.toolsId);
var sHtml = String.format('<a class="{0}" href="#" onfocus="this.blur();" id="{1}"></a>', cls, id);
new Insertion.Top(tools, sHtml);
var dom = this.getElement(id);
Event.observe(dom, 'click', cmd, false);
},
initBody : function(){
var dom = this.getElement();
this.bodyId = this.getId() + "-body";
new Insertion.Bottom(dom, String.format(htmlTemplate, "body", this.bodyId, ""));
},
initFooter : function(){
var dom = this.getElement();
this.footerId = this.getId() + "-footer";
new Insertion.Bottom(dom, String.format(htmlTemplate, "footer", this.footerId, ""));
}
});
wcmXCom.reg('panel', wcm.Panel);
})();
(function(){
var htmlTemplate = [
'<div class="wcm-btn wcm-btn-left {2}" id="{0}">',
'<div class="wcm-btn-right">',
'<div class="wcm-btn-center">',
'<a class="wcm-btn-text" href="#" onfocus="this.blur();">{1}</a>',
'</div>',
'</div>',
'</div>'
].join("");
wcm.Button = Ext.extend(wcm.Component, {
onRender : function(ownerCt){
var id = this.getId();
var text = this.text;
this.container = this.renderTo || ownerCt;
new Insertion.Bottom($(this.container), String.format(htmlTemplate, id, text, this.extraCls||""));
var dom = this.getElement();
if(this.tip) dom.title = this.tip;
if(this.cmd) this.on('click', this.cmd, this.scope || this);
Event.observe(dom, 'click', this.onClick.bind(this));
},
onClick : function(event){
Event.stop(event);
if(!this.disabled && this.fireEvent("beforeclick", this) !== false){
this.fireEvent("click", this, event);
}
}
});
wcmXCom.reg('button', wcm.Button);
})();
(function(){
var htmlTemplate = [
'<div class="wcm-btn wcm-btn-left {2}" id="{0}">',
'<div class="wcm-btn-right">',
'<div class="wcm-btn-center">',
'<a class="wcm-btn-text" href="#" onfocus="this.blur();">{1}</a>',
'</div>',
'</div>',
'</div>'
].join("");
var btnItem = [
'<table class="btn_item {2}" id="{0}" {2}>',
'<tr>',
'<td class="btn_cont" nowrap="true">{1}</td>',
'</tr>',
'</table>'
].join('');
var btnSep = [
'<div class="btnOps_sep"></div>'
].join('');
var moreBtnTemplate = [
'<div class="wcm-btn-more-btn" id="{0}_more_btn" style="display: none"></div>',
'<div class="wcm-btn-more" id="{0}_more" style="display: none">{3}</div>' 
].join("");
var findOperItem = function(target){
while(target!=null&&target.tagName.toUpperCase()!='BODY'){
if(Element.hasClassName(target, 'btn_item')){
return target;
}
target = target.parentNode;
}
return null;
}
wcm.ButtonWithMore = Ext.extend(wcm.Button, {
operResult : {},
onRender : function(ownerCt){
var id = this.getId();
var text = this.text;
this.container = this.renderTo || ownerCt;
if(this.more){
var btnOpers = this.moreOpers;
var moreBtn = this.getMoreBtnHtml(btnOpers);
new Insertion.Bottom($(this.container), String.format(htmlTemplate+moreBtnTemplate, id, text, this.extraCls||"",moreBtn));
this.onClickItem();
}else{
new Insertion.Bottom($(this.container), String.format(htmlTemplate, id, text, this.extraCls||""));
}
var dom = this.getElement();
if(this.tip) dom.title = this.tip;
if(this.more){
Element.show(id+'_more_btn');
Event.observe(id+'_more_btn','click', this.showMore.bind(this));
}
if(this.cmd) this.on('click', this.cmd, this.scope || this);
Event.observe(dom, 'click', this.onClick.bind(this));
},
onClickItem : function(){
var aOperResult = this.operResult;
var id = this.getId();
Ext.get(id+'_more').on('click', function(event, target){
var target = findOperItem(target);
if(target==null)return;
var operItem = aOperResult.json[target.id];
if(operItem==null || !operItem.cmd)return;
if(operItem.disabled)return;
operItem.cmd.call();
});
Ext.get(id+'_more').on('mouseover', function(event, target){
var target = findOperItem(target);
if(target==null)return;
Element.addClassName(target, 'item_active');
});
Ext.get(id+'_more').on('mouseout', function(event, target){
var target = findOperItem(target);
if(target==null)return;
Element.removeClassName(target, 'item_active');
});
},
onClick : function(event){
Event.stop(event);
if(!this.disabled && this.fireEvent("beforeclick", this) !== false){
this.fireEvent("click", this, event);
}
},
getMoreBtnHtml : function(_btnOpers){
var moreBtnHtml = [];
var json = {};
for (var i = 0,nLen = _btnOpers.length; i < nLen; i++){
if(moreBtnHtml.length>0){
moreBtnHtml.push(btnSep);
}
json[_btnOpers[i].id.toLowerCase()] = _btnOpers[i];
moreBtnHtml.push(String.format(btnItem, _btnOpers[i].id, _btnOpers[i].text, _btnOpers[i].extraCls||""));
}
this.operResult = {
html : moreBtnHtml.join(''),
json : json
};
return moreBtnHtml.join('');
},
showMore : function(event){
var sPanelId = this.getId()+'_more';
var x = event.x + 4;
var y = event.y + 4;
var oBubbler = new wcm.BubblePanel(sPanelId);
oBubbler.bubble([x,y], function(_Point){
return [_Point[0], _Point[1]];
});
Event.stop(event);
}
});
wcmXCom.reg('button', wcm.ButtonWithMore);
})();
wcm.MessageBox = function(){
var dlg = null;
var id = 'wcm-mb-dlg';
var iconId = id + "-icon";
var msgId = id + "-msg";
var btnsId = id + "-buttons";
var htmlTemplate = [
'<table border=0 cellspacing=0 cellpadding=0 class="dlg-table">',
'<tr>',
'<td class="icon" id="{0}"></td>',
'<td><div class="msg-wrapper">',
'<table class="dlg-table"><tr><td id="{1}" class="msg"></td></tr></table>',
'</div></td>',
'</tr>',
'<tr>',
'<td colspan="2" class="buttons" id="{2}"></td>',
'</tr>',
'</table>'
].join("");
var getDialog = function(){
if(!dlg){
dlg = new wcm.Panel({
baseZIndex : 10000,
baseCls : 'dlg ' + id,
id : id,
closeAction : 'hide',
draggable : true,
width : '328px',
height : '169px'
});
dlg.render();
new Insertion.Bottom(dlg.getElement(dlg.bodyId), String.format(htmlTemplate, iconId, msgId, btnsId));
}
return dlg;
};
return {
getDlg : getDialog,
alert : function(title, msg, fn, scope){
this.show({
title : title,
icon : 'icon-info',
msg : msg,
btns : [
{text : this.buttonText["ok"], cmd : fn}
]
});
return this;
},
confirm : function(title, msg, fn, scope){
this.show({
title : title,
icon : 'icon-question',
msg : msg,
btns : [
{text : this.buttonText[fn["yes"]?"yes":"ok"], cmd : fn["yes"] || fn["ok"] || fn},
{text : this.buttonText[fn["yes"]?"no":"cancel"], cmd : fn["no"] || fn["cancel"], extraCls : 'wcm-btn-close'}
]
});
return this;
},
warn : function(title, msg, fn, scope){
this.show({
title : title,
icon : 'icon-warning',
msg : msg,
btns : [
{text : this.buttonText["ok"], cmd : fn}
]
});
return this;
},
error : function(title, msg, fn, scope){
this.show({
title : title,
icon : 'icon-error',
msg : msg,
btns : [
{text : this.buttonText["ok"], cmd : fn}
]
});
return this;
},
show : function(options){
this.setTitle(options["title"]);
this.setIcon(options["icon"]);
this.setMsg(options["msg"]);
this.setButtons(options["btns"]);
getDialog().show();
getDialog().center();
},
hide : function(){
getDialog().hide();
},
setTitle : function(title){
title = title || wcm.LANG.DIALOG_SYSTEM_ALERTION || "系统提示信息";
getDialog().setTitle(title);
},
setIcon : function(icon){
icon = icon || "icon-info";
var dom = dlg.getElement(iconId);
dom.className = "icon " + icon;
},
setMsg : function(msg){
msg = msg || "";
var dom = getDialog().getElement(msgId);
Element.update(dom, msg);
},
setButtons : function(btns){
var dlg = getDialog();
var btnsDom = dlg.getElement(btnsId);
var btnDom = Element.first(btnsDom);
while(btnDom){
var btn = wcmXCom.get(btnDom);
btn ? dlg.remove(btn) : Element.remove(btnDom);
btnDom = Element.first(btnsDom);
}
for (var i = 0; i < btns.length; i++){
btns[i]["cmd"] = btns[i]["cmd"] ? btns[i]["cmd"].createSequence(this.hide, this) : this.hide;
var btn = new wcm.Button(Ext.applyIf(btns[i], {renderTo : btnsId, scope : this}));
dlg.add(btn);
btn.show();
}
},
$ : function(id){
return dlg.getElement(id);
},
buttonText : {
ok : wcm.LANG.DIALOG_BTN_OK || "确定",
cancel : wcm.LANG.DIALOG_BTN_CANCEL || "取消",
yes : wcm.LANG.DIALOG_BTN_YES || "是",
no : wcm.LANG.DIALOG_BTN_NO || "否"
}
};
}();
wcm.FaultDialog = function(){
var dlg = null;
var id = 'wcm-fault-dlg';
var iconId = id + "-icon";
var msgId = id + "-msg";
var detailId = id + "-detail";
var isDetailHidden = true;
var customClose = null;
var htmlTemplate = [
'<table border=0 cellspacing=0 cellpadding=0 class="dlg-table">',
'<tr>',
'<td class="icon" id="{0}">',
'<div class="detail" key="detail">', wcm.LANG.DIALOG_SHOW_DETAIL || '显示详细信息', '</div>',
'<div class="copy" key="copy">', wcm.LANG.DIALOG_COPY_DETAIL || '复制此信息', '</div>',
'<div class="close" key="close">', wcm.LANG.DIALOG_CLOSE_FRAME || '关闭提示窗', '</div>',
'</td>',
'<td><div style="height:200px;overflow:auto;">',
'<table class="dlg-table">',
'<tr><td id="{1}" class="msg"></td></tr>',
'<tr><td>',
'<textarea id="{2}" name="{2}" style="display:none;"></textarea>',
'</td>',
'</tr></table>',
'</div></td>',
'</tr>',
'</table>'
].join("");
var getDialog = function(){
if(!dlg){
dlg = new wcm.Panel({
baseZIndex : 10000,
baseCls : 'dlg ' + id,
closeAction : 'hide',
id : id
});
dlg.render();
dlg.on('beforehide', function(){
if(customClose){
var customClose0 = customClose;
customClose = null;
customClose0();
}
});
new Insertion.Bottom(dlg.getElement(dlg.bodyId), String.format(htmlTemplate, iconId, msgId, detailId));
bindEvents();
}
return dlg;
};
var bindEvents = function(){
Event.observe(iconId, 'click', function(event){
var srcElement = Event.element(event);
var key = srcElement.getAttribute("key");
if(!key) return;
try{
eval(key + "(event)");
}catch(error){
}
}, false);
};
var close = function(){
getDialog().hide();
};
var copy = function(){
window.setTimeout(function(){
var dom = getDialog().getElement(detailId);
try{
Ext.copy(dom.value);
alert(wcm.LANG.DIALOG_CLIPBOARD_COPYED || "已经复制到剪切板中!");
}catch(ex){
alert(wcm.LANG.DIALOG_COPY_NOT_SUPPORTED || '您的浏览器不支持自动复制操作,请手工拷贝!');
dom.focus();
dom.select();
}
}, 10);
};
var detail = function(event){
var dlg = getDialog();
if(isDetailHidden){
dlg.setWidth("700px");
}else{
dlg.setWidth("450px");
}
dlg.center();
Element[isDetailHidden ? 'show' : 'hide'](dlg.getElement(detailId));
var dom = Event.element(event || window.event);
if(dom.getAttribute('key') == 'detail'){
var sDetail = wcm.LANG.DIALOG_SHOW_DETAIL || '显示详细信息';
if(isDetailHidden) sDetail = wcm.LANG.DIALOG_HIDE_DETAIL || '隐藏详细信息';
Element.update(dom, sDetail);
}
isDetailHidden = !isDetailHidden;
};
var codeRegExp = /^\s*\[ERR-(\d+)\]\s*/;
var getFaultCode = function(_fault){
if(_fault && _fault.code) return _fault.code;
codeRegExp.test(_fault.message||"");
return RegExp.$1 || 0;
}
var format = function(sContent){
sContent = sContent || "";
return sContent.replace(/&nbsp;/g, " ");
}
return {
show : function(_fault, _title, _fClose){
var nCode = getFaultCode(_fault);
if(nCode >= 200000){
var message = (_fault.message||"").replace(codeRegExp, "");
Ext.Msg.warn($transHtml(message), _fClose);
return;
}
this.setTitle(_title);
customClose = _fClose;
var dlg = getDialog();
if(_fault){
var msg = $transHtml(_fault.message);
if(msg.length > 250){
msg = msg.substr(0, 250) + "...";
}
if(wcm.LANG.LOCALE == 'en')if(nCode != 11111) msg = "An error occurs in the system.";;
Element.update(dlg.getElement(msgId), msg);
dlg.getElement(detailId).value = format(_fault.detail);
}
dlg.show();
},
setTitle : function(title){
title = title || wcm.LANG.DIALOG_SERVER_ERROR || "与服务器交互时出现错误";
if(wcm.LANG.LOCALE == 'en') title = "Error";
getDialog().setTitle(title);
},
setMsg : function(msg){
msg = msg || "";
var dom = getDialog().getElement(msgId);
Element.update(dom, msg);
},
setDetail : function(detail){
detail = detail || "";
var dom = getDialog().getElement(detailId);
Element.update(dom, detail);
},
close : function(){
getDialog().hide();
}
};
}();
wcm.ReportDialog = function(){
var dlg = null;
var id = 'wcm-report-dlg';
var iconId = id + "-icon";
var msgId = id + "-msg";
var contentId = id + "-content";
var info = null;
var customClose = null;
var htmlTemplate = [
'<table border=0 cellspacing=0 cellpadding=0 class="dlg-table">',
'<tr>',
'<td class="icon" id="{0}">',
'<div class="copy" key="copy">', wcm.LANG.DIALOG_COPY_DETAIL || '复制此信息', '</div>',
'<div class="close" key="close">', wcm.LANG.DIALOG_CLOSE_FRAME || '关闭提示窗', ' </div>',
'</td>',
'<td><div style="height:200px;overflow:auto;">',
'<table class="dlg-table">',
'<tr><td id="{1}" class="msg"></td></tr>',
'<tr><td><div id="{2}" class="content"></div></td></tr>',
'</table>',
'</div></td>',
'</tr>',
'</table>'
].join("");
var getDialog = function(){
if(!dlg){
dlg = new wcm.Panel({
baseZIndex : 10000,
baseCls : 'dlg ' + id,
closeAction : 'hide',
id : id,
width : '550px'
});
dlg.render();
dlg.on('beforehide', function(){
if(customClose){
var customClose0 = customClose;
customClose = null;
customClose0();
}
});
new Insertion.Bottom(dlg.getElement(dlg.bodyId), String.format(htmlTemplate, iconId, msgId, contentId));
bindEvents();
}
return dlg;
};
var bindEvents = function(){
Event.observe(iconId, 'click', function(event){
var srcElement = Event.element(event);
var key = srcElement.getAttribute("key");
if(!key) return;
try{
eval(key + "()");
}catch(error){
}
}, false);
};
var close = function(){
getDialog().hide();
};
var copy = function(){
try{
Ext.copy(info.Summary);
alert(wcm.LANG.DIALOG_CLIPBOARD_COPYED || "已经复制到剪切板中!");
}catch(ex){
alert(wcm.LANG.DIALOG_COPY_NOT_SUPPORTED || '您的浏览器不支持自动复制操作,请手工拷贝!');
}
};
var m_arrTypeNames = ['UNKNOWN', 'SUCCESS', 'WARN', 'ERROR'];
function getTypeName(_nType){
_nType = parseInt(_nType);
if(isNaN(_nType) || _nType < 3 || _nType > 5) {
_nType = 2;
}
return m_arrTypeNames[_nType - 2];
}
var transTitle = function(_sTitle, _sOption, report, index){
if(Ext.isFunction(_sOption)) _sOption = _sOption(report, index);
var str = _sTitle;
var reg = new RegExp('\~[^-]+\-([0-9]+)\~', 'i');
var matches = str.match(reg);
var nId = 0;
if(matches != null && (nId = parseInt(matches[1])) > 0) {
str = '<span class="option_' + _sOption + '" onclick="wcm.ReportDialog.renderOption('
+ nId + ')">&nbsp;&nbsp;</span>' + str.replace(reg, '');
}
return str;
}
var titleTransOptions = null;
var build = function(_report){
info = {
isSuccess: $v(_report, 'reports.is_success') === 'true',
title: $v(_report, 'reports.title'),
reports: $a(_report, 'reports.report') || []
};
var sSummary = info.title + '\n';
var sContent = '';
var options = titleTransOptions;
var bNeedTransTitle = (options != null && options.option != null);
for (var i = 0; i < info.reports.length;){
var report = info.reports[i++];
var sTitle = $v(report, 'title');
var sDetails = $v(report, 'ERROR_INFO');
var sType = $v(report, 'TYPE');
if(sType == '') {
sType = '3';
}
var bHasDetail = (sDetails != null && sDetails.trim() != '');
sTitle = bNeedTransTitle ? transTitle(sTitle, options.option, report, i-1) : $transHtml(sTitle);
sContent += '<div class="' + (bHasDetail ? 'innerTitle_withTip' : 'innerTitle') + '"><b>' + i + '.</b> \
<b class="rpdlg-type' + sType + '">&nbsp;&nbsp;&nbsp;</b>\ ' + sTitle + '</div>';
if(bHasDetail) {
sDetails = sDetails.replace(/</g, "&lt;");
sDetails = sDetails.replace(/>/g, "&gt;");
sDetails = sDetails.replace(/\n/g, "<br />");
sContent += '<div class="tip">(<a href="#" onclick="wcm.ReportDialog.switchDetail(' + i + ', this);return false;">'+(wcm.LANG.DIALOG_DETAIL_INFO||'详细信息') + '</a>)</div>\
<div id="divDetails_' + i + '" style="margin-top:-13px; display:none">\
<div class="commentHeader"></div>\
<div class="commentbox" readonly>' + sDetails + '</div>\
</div>';
}
sContent += '<div class="sep">&nbsp;</div>';
sSummary += i + '. [' + getTypeName(sType) + ']' + sTitle + '\n';
sSummary += '[' + (wcm.LANG.DIALOG_DETAIL_INFO||'详细信息') + '] ' + (sDetails || '') + '\n\n';
}
info.Summary = sSummary;
info.content = sContent;
};
return {
show : function(_report, _title, _fClose, _titleTransOptions){
customClose = _fClose;
titleTransOptions = _titleTransOptions;
build(_report);
this.setTitle(_title);
this.setIcon(info.isSuccess ? 'success' : 'error');
this.setMsg(info.title);
this.setContent(info.content);
var dlg = getDialog();
dlg.show();
},
setIcon : function(icon){
var dlg = getDialog();
dlg.getElement(iconId).className = "icon " + icon;
},
setTitle : function(title){
title = title || wcm.LANG.DIALOG_SYSTEM_ALERTION || "系统提示信息";
if(wcm.LANG.LOCALE == 'en') title = "Confirm";
getDialog().setTitle(title);
},
setMsg : function(msg){
msg = msg || "";
var dom = getDialog().getElement(msgId);
Element.update(dom, msg);
},
setContent : function(content){
content = content || "";
var dom = getDialog().getElement(contentId);
Element.update(dom, content);
},
close : function(){
getDialog().hide();
},
renderOption : function(_sObjId){
var options = titleTransOptions;
if(options.renderOption) {
options.renderOption(_sObjId);
}
},
switchDetail : function(_sId, _oLnk){
var dlg = getDialog();
var detail = dlg.getElement('divDetails_' + _sId);
if(_oLnk._bDispay === true ) {
_oLnk.innerHTML = wcm.LANG.DIALOG_DETAIL_INFO || '详细信息';
_oLnk._bDispay = false;
Element.hide(detail);
}else{
_oLnk.innerHTML = wcm.LANG.DIALOG_HIDE_INFO || '隐藏显示';
_oLnk._bDispay = true;
Element.show(detail);
}
dlg.center();
}
};
}();
Ext.Msg = Ext.Msg || {};
Ext.apply(Ext.Msg, {
$success : function(msg, fn, scope){
wcm.MessageBox.show({
icon : 'icon-success',
msg : msg,
btns : [
{text : wcm.MessageBox.buttonText["ok"], cmd : fn}
]
});
},
$fail : function(msg, fn, scope){
wcm.MessageBox.show({
icon : 'icon-fail',
msg : msg,
btns : [
{text : wcm.MessageBox.buttonText["ok"], cmd : fn}
]
});
},
$timeAlert : function(msg,iSeconds,fn,title,icon){
iSeconds = iSeconds || 5;
var msg = msg+'<br><br><div style="font-size:12px;color:gray;">' + String.format("本窗口在<span id=\'_dialog_timecounter_\' style=\'font-size:11px;padding:0 2px;color:red\'>{0}</span>秒内将自动消失",iSeconds) + '.</div>';
var dlg = wcm.MessageBox.getDlg();
var $timeAlertHandler = null;
dlg.on('beforehide', function(){
if($timeAlertHandler) clearTimeout($timeAlertHandler);
(fn || Ext.emptyFn).call(dlg);
dlg.un('beforehide', arguments.callee);
});
wcm.MessageBox.show({
title : title,
icon : icon || 'icon-info',
msg : msg,
btns : [
{text : wcm.MessageBox.buttonText["ok"]}
]
});
var cnt = 0;
$timeAlertHandler = setTimeout(
function(){
var tc = wcm.MessageBox.$('_dialog_timecounter_');
if(!tc) return;
if((++cnt)>=iSeconds){
var dlg = wcm.MessageBox.getDlg();
if(dlg.hidden) return;
wcm.MessageBox.hide();
}
else{
tc.innerHTML = iSeconds-cnt;
$timeAlertHandler = setTimeout(arguments.callee,1000);
}
}
,1000);
},
alert : function(msg, fn, scope){
wcm.MessageBox.alert("", msg, fn, scope);
},
confirm : function(msg, fn, scope){
wcm.MessageBox.confirm("", msg, fn, scope);
},
show : function(options){
wcm.MessageBox.show(options);
},
report : function(_report, _title, _fClose, _titleTransOptions){
wcm.ReportDialog.show.apply(wcm.ReportDialog, arguments);
},
fault : function(_report, _title, _fClose){
wcm.FaultDialog.show.apply(wcm.FaultDialog, arguments);
},
warn : function(msg, fn, scope, opt){
opt = opt || {};
wcm.MessageBox.warn(opt.title || "", msg, fn, scope);
},
error : function(msg, fn, scope, opt){
opt = opt || {};
wcm.MessageBox.error(opt.title || "", msg, fn, scope);
}
});
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
var supportsString = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
supportsString.data = _sTxt;
transferable.setTransferData("text/unicode",supportsString,_sTxt.length*2);
clipboard.setData(transferable,null,Components.interfaces.nsIClipboard.kGlobalClipboard);
}catch(err){}
}
};
(function(){
var frmTemplate = '<iframe src="{0}" id="{1}" style="height:100%;width:100%;overflow:auto;" frameborder="0" onload="wcm.CrashBoard.contentLoaded(\'{2}\', this);"></iframe>';
var htmlTemplate = [
'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
'<tr><td><div id="{2}" style="overflow:visible;">{0}</div></td></tr>',
'<tr><td class="buttons" id="{1}"></td></tr>',
'</table>'
].join("");
wcm.CrashBoard = Ext.extend(wcm.Panel, {
baseCls : 'wcm-cbd',
maskable : false,
appendParamsToUrl : true,
initComponent : function(){
wcm.CrashBoard.superclass.initComponent.apply(this, arguments);
},
notify : function(params){
if(this.callback){
this.callback(params);
}
},
onPosition : function(left, top){
wcm.CrashBoard.superclass.onPosition.apply(this, arguments);
},
onResize : function(width, height){
wcm.CrashBoard.superclass.onResize.apply(this, arguments);
if(!height) return;
var dom = this.getElement(this.contentId);
dom.style.height = height;
if(Ext.isIE6){
var dom = this.getElement(this.frmId);
if(dom) dom.style.height = height;
}
},
getDragElId : function(){
return this.getId();
},
onRender : function(){
wcm.CrashBoard.superclass.onRender.apply(this, arguments);
var id = this.getId();
this.contentId = id + "-content";
this.btnsId = id + "-buttons";
var dom = this.getElement(this.bodyId);
new Insertion.Bottom(dom, String.format(htmlTemplate, this.getContent(), this.btnsId, this.contentId));
if(this.btns){
this.addBtns(this.btns);
delete this.btns;
}
},
afterRender : function(){
var centered = this.left == undefined && this.top == undefined;
wcm.CrashBoard.superclass.afterRender.apply(this, arguments);
if(centered){
this.center();
}
},
getContentEl : function(){
return this.getElement(this.getId() + "-content");
},
getContent : function(){
var id = this.getId();
if(this.src){
if(this.appendParamsToUrl){
var search = $toQueryStr(this.params || {});
this.src = this.src +(this.src.indexOf("?") >= 0 ? "&" : "?") + search;
}
this.frmId = id + "-iframe";
return String.format(frmTemplate, this.buildSrc(), this.frmId, id);
}
return this.el ? $(this.el).innerHTML : this.html;
},
buildSrc : function(){
var params = {};
params[wcm.CrashBoard["fromcb"]] = 1;
var search = $toQueryStr(params, this.params || {});
return src = this.src +(this.src.indexOf("?") >= 0 ? "&" : "?") + search;
},
resetBtns : function(config){
this.clearBtns();
this.addBtns(config["btns"]);
},
clearBtns : function(){
var btnsDom = this.getElement(this.btnsId);
var dom = Element.first(btnsDom);
while(dom){
this.remove(wcmXCom.get(dom));
dom = Element.first(btnsDom);
}
},
addBtns : function(btns){
if(!btns) return;
var btnsDom = this.getElement(this.btnsId);
for (var i = 0; i < btns.length; i++){
var cpyBtn = Ext.applyIf({
renderTo : btnsDom,
scope : this,
cmd : btns[i]["cmd"] ? this.close.createInterceptor(btns[i]["cmd"], this) : this.close
}, btns[i]);
var btn = new wcm.Button(cpyBtn);
btn.on('beforeclick', function(){
return this.inited;
}, this);
this.add(btn).render();
}
}
});
wcmXCom.reg('crashboard', wcm.Button);
})();
Ext.apply(wcm.CrashBoard, {
fromcb : '_fromcb_',
frmIndentyAttr : '-cb-id-',
contentLoaded : function(id, frm){
if((frm.src == Ext.blankUrl)||(frm.src == "")) return;
var cb = wcmXCom.get(id);
if(!cb) return;
frm[wcm.CrashBoard.frmIndentyAttr] = id;
var win = null;
try{
win = frm.contentWindow;
if(win && win.m_cbCfg){
cb.resetBtns(win.m_cbCfg);
}
}catch(error){
}
try{
if(win && win.init){
win.init(cb.params);
}
}catch(error){
}
cb.inited = true;
}
});
wcm.CrashBoarder = function(id){
var frm = null;
if(id && !String.isString(id) && (frm = id.frameElement)){
id = frm[wcm.CrashBoard.frmIndentyAttr];
}
this.id = id;
this.cb = wcmXCom.get(id);
};
Ext.apply(wcm.CrashBoarder, {
get : function(id){
return new wcm.CrashBoarder(id);
}
});
Ext.apply(wcm.CrashBoarder.prototype, {
notify : function(params){
if(this.cb){
this.cb.notify(params);
}
},
getCrashBoard : function(){
return this.cb;
},
show : function(config){
this.cb = wcmXCom.get(this.id);
if(this.cb){
this.cb.destroy();
delete this.cb;
}
Ext.applyIf(config, {id : this.id});
this.cb = new wcm.CrashBoard(config);
this.cb.show();
},
hide : function(){
if(this.cb){
this.cb.hide();
}else{
window.close();
}
},
close : function(){
if(this.cb){
this.cb.close();
}else{
window.close();
}
}
});

wcm.dd.SiteThumbDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
getThumbList : function(){
return (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;
},
b4StartDrag : function(event){
var dom = Event.element(event.browserEvent);
if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return false;
this.item = this.getThumbList().find(dom);
return this.item != null;
},
startDrag : function(x, y, event){
var item = this.item;
this.shadow = item.getDom();
Element.addClassName(this.shadow, 'dragging');
this.nextSibling = Element.next(this.shadow);
this.root = $(this.id);
this.trashBox = $('trash_box');
this.sortable = this._isSortable(this.item);
var dom = document.createElement("div");
dom.style.position = 'absolute';
dom.innerHTML = this._getHint(this.item);
document.body.appendChild(dom);
this.dragEl = dom;
wcm.dd.SiteThumbDragDrop.superclass.startDrag.apply(this, arguments);
},
_isSortable : function(){
return true;
},
_getHint : function(item){
return 'text';
},
_moveAfter : function(_eCurr,_ePrev){
if(_ePrev!=null){
alert(_ePrev.innerHTML);
}
else{
alert('i"m the first.');
}
},
_moveIntoTrashBox : function(item){
alert('move into trashbox');
return true;
},
inTrashBox : function(x, y){
return this.trashBox && Position.within(this.trashBox, x, y);
},
drag : function(x, y, event){
wcm.dd.SiteThumbDragDrop.superclass.drag.apply(this, arguments);
this.dragEl.style.left = (x + 2) + "px";
this.dragEl.style.top = (y + 2) + "px";
if(!this.sortable) return;
if(this.inTrashBox(x, y)){
Element.addClassName(this.trashBox, 'trashbox_dragging_on');
return;
}
if(this.trashBox){
Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
}
var eThumb = Element.first(this.root);
while(eThumb){
var page = Position.page(eThumb);
var iTop = page[1];
var iLeft = page[0];
var iRight = iLeft + eThumb.offsetWidth;
var iBottom = iTop + eThumb.offsetHeight;
var iCenter = (iLeft + iRight) / 2;
if(eThumb!=this.shadow){
if(y>=iTop&&y<=iBottom){
if(x>=iLeft&&x<=iCenter){
eThumb.parentNode.insertBefore(this.shadow, eThumb);
break;
}
else if(x<=iRight&&x>iCenter){
eThumb.parentNode.insertBefore(this.shadow, eThumb.nextSibling);
break;
}
}
}
eThumb = Element.next(eThumb);
}
},
release : function(){
if(!this.dragging) return;
delete this.root;
delete this.shadow;
delete this.nextSibling;
delete this.item;
delete this.trashBox;
Element.remove(this.dragEl);
delete this.dragEl;
wcm.dd.SiteThumbDragDrop.superclass.release.apply(this, arguments);
},
endDrag : function(x, y, event){
wcm.dd.SiteThumbDragDrop.superclass.endDrag.apply(this, arguments);
var bMoved = true;
if(this.inTrashBox(x, y)){
Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
bMoved = this._moveIntoTrashBox(this.item);
if(bMoved){
}
}else{
if(Element.next(this.shadow) != this.nextSibling){
var previous = Element.previous(this.shadow);
bMoved = this._moveAfter(this.item, this.getThumbList().find(previous));
}
}
Element.removeClassName(this.shadow, 'dragging');
if(!bMoved){
this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
}
}
});
wcm.dd.ChannelThumbDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
getThumbList : function(){
return (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;
},
b4StartDrag : function(event){
var dom = Event.element(event.browserEvent);
if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return false;
this.item = this.getThumbList().find(dom);
return this.item != null;
},
getWin : function(){
try{
return $MsgCenter.getActualTop();
}catch(error){
return window;
}
},
startDrag : function(x, y, event){
this.page = Position.getPageInTop(window.frameElement);
var item = this.item;
this.shadow = item.getDom();
Element.addClassName(this.shadow, 'dragging');
this.nextSibling = Element.next(this.shadow);
this.root = $(this.id);
this.trashBox = $('trash_box');
var win = this.getWin();
var dom = win.document.createElement("div");
dom.style.position = 'absolute';
dom.innerHTML = this._getHint(this.item);
win.document.body.appendChild(dom);
this.dragEl = dom;
var dom = document.createElement("div");
dom.style.position = 'absolute';
dom.className = "drag-line";
document.body.appendChild(dom);
this.line = dom;
wcm.dd.ChannelThumbDragDrop.superclass.startDrag.apply(this, arguments);
},
_getHint : function(item){
return 'text';
},
_moveAfter : function(_eCurr,_ePrev){
if(_ePrev!=null){
alert(_ePrev.innerHTML);
}
else{
alert('i"m the first.');
}
},
_moveTo : function(_eCurr, _eTarget){
alert('move to');
},
_moveIntoTrashBox : function(item){
alert('move into trashbox');
return true;
},
inTrashBox : function(x, y){
return this.trashBox && Position.within(this.trashBox, x, y);
},
drag : function(x, y, event){
wcm.dd.ChannelThumbDragDrop.superclass.drag.apply(this, arguments);
this.isActive = false;
this.dragEl.style.left = this.page[0] + (x + 5) + "px";
this.dragEl.style.top = this.page[1] + (y + 5) + "px";
if(this.inTrashBox(x, y)){
Element.addClassName(this.trashBox, 'trashbox_dragging_on');
return;
}
if(this.trashBox){
Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
}
if(this.relateEl){
Element.removeClassName(this.relateEl, 'dragginghover');
delete this.relateEl;
}
Element.hide(this.line);
var eThumb = Element.first(this.root);
while(eThumb){
var page = Position.page(eThumb);
var iLeft = page[0];
var iTop = page[1];
var iRight = iLeft + eThumb.offsetWidth;
var iBottom = iTop + eThumb.offsetHeight;
if(eThumb!=this.shadow){
if(y>=iTop&&y<=iBottom){
if(x>=iLeft&&x<=iRight){
Element.addClassName(eThumb, 'dragginghover');
this.relateEl = eThumb;
this.bMoveTo = true;
this.isActive = true;
break;
}
var previous = Element.previous(eThumb);
if(previous != this.shadow){
var iPreRight = 0;
if(previous){
var previousPage = Position.page(previous);
if(previousPage[1] == iTop){
iPreRight = previousPage[0] + previous.offsetWidth;
}
}
if(x < iLeft && x > iPreRight){
this.line.style.left = (iPreRight + 2)+"px";
this.line.style.top = iTop+"px";
this.line.style.height = eThumb.offsetHeight+"px";
Element.show(this.line);
this.relateEl = previous;
this.bMoveTo = false;
this.isActive = true;
break;
}
}
var next = Element.next(eThumb);
if(next != this.shadow){
var iNextLeft = iRight+20;
if(next){
var nextPage = Position.page(next);
if(nextPage[1] == iTop){
iNextLeft = nextPage[0];
}
}
if(x > iRight && x < iNextLeft){
this.line.style.left = (iRight + 2)+"px";
this.line.style.top = iTop+"px";
this.line.style.height = eThumb.offsetHeight+"px";
Element.show(this.line);
this.relateEl = eThumb;
this.bMoveTo = false;
this.isActive = true;
break;
}
}
}
}
eThumb = Element.next(eThumb);
}
},
release : function(){
if(!this.dragging) return;
delete this.root;
Element.removeClassName(this.shadow, 'dragging');
delete this.shadow;
delete this.nextSibling;
delete this.item;
delete this.trashBox;
Element.remove(this.line);
delete this.line;
Element.remove(this.dragEl);
delete this.dragEl;
if(this.relateEl){
Element.removeClassName(this.relateEl, 'dragginghover');
delete this.relateEl;
}
wcm.dd.ChannelThumbDragDrop.superclass.release.apply(this, arguments);
},
endDrag : function(x, y, event){
wcm.dd.ChannelThumbDragDrop.superclass.endDrag.apply(this, arguments);
var bMoved = true;
if(this.inTrashBox(x, y)){
Element.removeClassName(this.trashBox, 'trashbox_dragging_on');
bMoved = this._moveIntoTrashBox(this.item);
if(bMoved){
}
}else{
if(this.isActive){
var sMethod = this.bMoveTo ? '_moveTo' : '_moveAfter';
bMoved = this[sMethod](this.item, this.getThumbList().find(this.relateEl));
}
}
if(this.relateEl && this.bMoveTo){
Element.removeClassName(this.relateEl, 'dragginghover');
}
if(!bMoved){
this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
}
}
});
wcm.dd.AccrossFrameDragDrop = function(dd){
dd.addListener('startdrag', this.init, this);
dd.addListener('enddrag', this.destroy, this);
};
Ext.apply(wcm.dd.AccrossFrameDragDrop.prototype, {
getWinInfos0 : function(){
if(!this.winInfos){
this.winInfos = this.getWinInfos();
}
return this.winInfos;
},
getWinInfos : function(){
return [];
},
getWin : function(winInfo){
return winInfo.document ? winInfo : winInfo.win;
},
init : function(){
this.dd = arguments[3];
var winInfos = this.getWinInfos0();
for (var i = 0; i < winInfos.length; i++){
this.addDragWin(winInfos[i]);
}
},
addDragWin : function(winInfo){
var win = this.getWin(winInfo);
var doc = win.document;
win.Ext.EventManager.on(doc, 'mouseover', this.onmouseover, this, winInfo);
win.Ext.EventManager.on(doc, 'mouseout', this.onmouseout, this, winInfo);
},
enterMe : function(event, target, opt){},
startDrag : function(event, target, opt){},
onmouseover : function(event, target, opt){
if(!this.dd || !this.dd.dragging) return;
if(!opt['-accrossdraginfo-']){
var dragInfo = opt['-accrossdraginfo-'] = {};
var win = this.getWin(opt);
var doc = win.document;
win.Ext.EventManager.on(doc, 'mousemove', this.onmousemove, this, opt);
win.Ext.EventManager.on(doc, 'mouseup', this.onmouseup, this, opt);
var box = doc.documentElement || doc.body;
dragInfo['page'] = Position.getPageInTop(box);
(opt['startDrag'] || this.startDrag).apply(this ,arguments);
}
(opt['enterMe'] || this.enterMe).apply(this ,arguments);
},
leaveMe : function(event, target, opt){},
onmouseout : function(event, target, opt){
if(!this.dd || !this.dd.dragging) return;
(opt['leaveMe'] || this.leaveMe).apply(this ,arguments);
},
onmousemove : function(event, target, opt){
(opt['drag'] || this.drag).apply(this, arguments);
}, 
drag : function(event, target, opt){
var dragInfo = opt['-accrossdraginfo-'];
var page = dragInfo['page'];
var xy = this.dd.getXY(event);
this.dd.dragEl.style.left = page[0] + (xy[0] + 5) + "px";
this.dd.dragEl.style.top = page[1] + (xy[1] + 5) + "px";
},
destroy : function(){
delete this.dd;
var winInfos = this.getWinInfos0();
for (var i = 0; i < winInfos.length; i++){
this.removeDragWin(winInfos[i]);
}
delete this.winInfos;
},
removeDragWin : function(winInfo){
var win = this.getWin(winInfo);
var doc = win.document;
win.Ext.EventManager.un(doc, 'mouseover', this.onmouseover, this);
win.Ext.EventManager.un(doc, 'mouseout', this.onmouseout, this);
if(!winInfo['-accrossdraginfo-']) return;
winInfo['-accrossdraginfo-'] = null;
win.Ext.EventManager.un(doc, 'mousemove', this.onmousemove, this);
win.Ext.EventManager.un(doc, 'mouseup', this.onmouseup, this);
},
endDrag : function(event, target, opt){},
onmouseup : function(event, target, opt){
(opt['endDrag'] || this.endDrag).apply(this, arguments);
var dd = this.dd;
this.destroy();
dd.dispose();
}
});
wcm.dd.GridDragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
b4StartDrag : function(event){
var dom = Event.element(event.browserEvent);
var row = wcm.Grid.findRow(dom);
this.row = row;
if(!row) return false;
if(Ext.isIE){
return row != null;
}
while(dom && row.dom != dom){
if(dom.tagName == 'A' || dom.tagName == "BODY") return false;
dom = dom.parentNode;
}
return true;
},
getWin : function(){
try{
return $MsgCenter.getActualTop();
}catch(error){
return window;
}
},
startDrag : function(x, y, event){
this.page = Position.getPageInTop(window.frameElement);
var row =this.row;
this.shadow = row.dom;
Element.addClassName(this.shadow, 'dragging');
this.nextSibling = Element.next(this.shadow);
this.root = $(this.rootId || this.id);
var win = this.getWin();
var dom = win.document.createElement("div");
dom.className = "grid_row_hint";
this.sortable = this._isSortable(this.row);
dom.innerHTML = this._getHint(this.row);
win.document.body.appendChild(dom);
this.dragEl = dom;
wcm.dd.GridDragDrop.superclass.startDrag.apply(this, arguments);
},
_isSortable : function(){
return true;
},
_getHint : function(row){
return 'text';
},
_move : function(srcRow, iPosition, dstRow, eTargetMore){
},
drag : function(x, y, event){
wcm.dd.GridDragDrop.superclass.drag.apply(this, arguments);
this.dragEl.style.left = this.page[0] + (x + 5) + "px";
this.dragEl.style.top = this.page[1] + (y + 5) + "px";
if(!this.sortable) return;
var eRow = Element.first(this.root);
while(eRow){
var offset = Position.page(eRow);
var iTop = parseInt(offset[1],10);
var iLeft = parseInt(offset[0],10);
var iRight = iLeft + eRow.offsetWidth;
var iBottom = iTop + eRow.offsetHeight;
var iCenter = (iTop + iBottom) / 2;
if(eRow!=this.shadow){
if(x>=iLeft&&x<=iRight){
if(y>=iTop&&y<=iCenter){
eRow.parentNode.insertBefore(this.shadow, eRow);
break;
}
else if(y<=iBottom&&y>iCenter){
eRow.parentNode.insertBefore(this.shadow, eRow.nextSibling);
break;
}
}
}
eRow = Element.next(eRow);
}
},
release : function(){
if(!this.dragging) return;
delete this.page;
delete this.root;
delete this.row;
Element.removeClassName(this.shadow, 'dragging');
delete this.shadow;
Element.remove(this.dragEl);
delete this.dragEl;
delete this.nextSibling;
wcm.dd.GridDragDrop.superclass.release.apply(this, arguments);
},
endDrag : function(x, y, event){
wcm.dd.GridDragDrop.superclass.endDrag.apply(this, arguments);
var bMoved = true;
if(Element.next(this.shadow) != this.nextSibling){
var previous = Element.previous(this.shadow);
if(previous == null || previous.tagName != this.shadow.tagName){
var next = Element.next(this.shadow);
var nextRow = next ? wcm.Grid.findRow(next) : null;
bMoved = this._move(this.row, 1, nextRow);
}
else{
var previous = Element.previous(this.shadow);
var previousRow = previous ? wcm.Grid.findRow(previous) : null;
var next = Element.next(this.shadow);
var nextRow = next ? wcm.Grid.findRow(next) : null;
bMoved = this._move(this.row, 0, previousRow, nextRow);
}
if(!bMoved){
this.shadow.parentNode.insertBefore(this.shadow, this.nextSibling);
}
}
}
});

Ext.ns('PageContext');
Ext.apply(PageContext, {
params : {},
getContext : function(){
return this.getContext0();
},
gridFunctions : function(){
},
getParameter : function(){
if(this.prepareParams){
var retVal = this.prepareParams.apply(this, arguments);
if(retVal!=null)return retVal;
}
return getParameter.apply(null, arguments);
},
_buildParams : function(event, actionType){
return {};
var params = Ext.Json.toUpperCase(location.search.parseQuery());
return Ext.applyIf(params, Ext.Json.toUpperCase(PageContext.initParams));
},
getContext0 : function(){
if(this.context!=null){
return this.context;
}
var bIsChannel = !!PageContext.getParameter("ChannelId");
this.context = {
isChannel : bIsChannel,
host : {
objType : PageContext.hostType,
objId : PageContext.hostId,
right : PageContext.getParameter("RightValue"),
isVirtual : PageContext.getParameter("IsVirtual")
},
href : location.href,
params : Ext.Json.toUpperCase(location.search.parseQuery())
};
return this.context;
},
initPage : function(){
PageContext.m_CurrPage.beforeinit();
this.context = null;
if(wcm.PageFilter)wcm.PageFilter.init(PageContext.getFilters());
if(wcm.PageTab){
var info = PageContext.getTabs();
PageContext.activeTabType = info.activeTabType;
wcm.PageTab.init(info);
}
if(wcm.PageOper)wcm.PageOper.init(PageContext.getOpers());
if(wcm.Grid || wcm.ThumbList){
this.gridFunctions();
this.loadList({
FilterType : (this.pageFilters)?this.pageFilters.filterType:0
});
}else{
PageContext.m_CurrPage.afterinit();
}
PageContext.getContext();
if(PageContext.literator){
PageContext.drawLiterator();
}
},
_doBeforeLoad : function(query){
},
_doBeforeBinding : function(_transport, _json){
},
_doAfterBound : function(_transport, _json){
},
renderList : function(_transport, _json, _ajaxRequest){
PageContext._doBeforeBinding(_transport, _json, _ajaxRequest);
Ext.get('wcm_table_grid').update(_transport.responseText, true);
PageContext._doAfterBound(_transport, _json, _ajaxRequest);
if(PageContext.m_CurrPage)PageContext.m_CurrPage.afterinit();
},
getPageInfo : function(){
return null;
},
getPageParams : function(info){
var locationParams = location.search.parseQuery();
this.params = Ext.Json.toUpperCase(locationParams);
Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
return Ext.apply(this.params, Ext.Json.toUpperCase(info));
},
getPageSize : function(){
var pagesize = this.params["PAGESIZE"];
if(wcm.ThumbList && !pagesize) pagesize = -1;
if(wcm.Grid && !pagesize) pagesize = 20;
if(wcm.Grid && parent.m_CustomizeInfo) pagesize = parent.m_CustomizeInfo.listPageSize.paramValue;
return pagesize;
},
loadList : function(info, _fCallBack, _bRefresh){
if(this._doBeforeLoad()===false){
if(PageContext.m_CurrPage)PageContext.m_CurrPage.afterinit();
return;
}
var aCombine = [];
var oHelper = new com.trs.web2frame.BasicDataHelper();
this.params = (_bRefresh && this.params) ? 
Ext.apply(this.params, Ext.Json.toUpperCase(info)) : this.getPageParams(info);
this.params = Ext.Json.toUpperCase(this.params);
this.params["PAGESIZE"] = this.getPageSize();
if(this.serviceId.indexOf('.jsp')==-1){
var sQueryMethod = this.methodName || 'jQuery';
oHelper.Call(this.serviceId, sQueryMethod, 
this.params, true, _fCallBack||this.renderList);
}
else{
oHelper.JspRequest(this.serviceId,
this.params, true, _fCallBack||this.renderList);
}
},
refreshList : function(info, _selectRowIds){
this.loadList(Ext.applyIf({
"SELECTIDS" : _selectRowIds.join(',')
}, info), null, true);
},
updateCurrRows : function(_currId){
PageContext.editIds = _currId;
if(wcm.Grid) var ids = wcm.Grid.getRowIds(true);
if(window.myThumbList) var ids = window.myThumbList.getIds(true);
this.refreshList(PageContext.params, ids);
},
getFilters : function(){
return Ext.applyIf({
enable : this.filterEnable
}, this.pageFilters);
},
getTabs : function(){
var tab = this.pageTabs;
return Ext.applyIf({
enable : this.tabEnable
}, tab);
},
getOpers : function(){
var oper = this.pageOpers;
if(!oper && wcm.SysOpers) oper = wcm.SysOpers.opers;
return Ext.applyIf({
enable : this.operEnable && oper != null
}, oper);
},
getRightIndex : function(_sFunctionType){
if(PageContext.rightIndexs){
return PageContext.rightIndexs[_sFunctionType] || -1;
}
return -1;
}
});
Ext.apply(PageContext, {
validExistProperty : function(extraParams){
return function(){
var wcmEvent = PageContext.event;
var obj = wcmEvent.getObjs().getAt(0) || wcmEvent.getHost();
var oPostData = {
objId : obj.getId(), 
objType : obj.getIntType()
};
var element = this.field;
oPostData[element.name] = element.value;
if(Ext.isFunction(extraParams)){
oPostData = extraParams(oPostData) || oPostData;
}else{
Ext.apply(oPostData, extraParams);
}
var warning = oPostData["warning"] || "";
delete oPostData["warning"];
warning = warning || ((this.validateObj["desc"] || element.name) + (wcm.LANG.SYSTEM_NOTUNIQUE||"不唯一"));
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.JspRequest(
WCMConstants.WCM6_PATH + "include/property_test_exist.jsp", 
oPostData, true, 
function(transport, json){
var result = transport.responseText.trim();
ValidatorHelper.execCallBack(element, result == 'true' ? warning : null);
}
);
};
}
});
Ext.apply(PageContext, {
hostType : (function(){
return PageContext.getParameter("HostType") || 
(!!PageContext.getParameter("channelid")?
WCMConstants.OBJ_TYPE_CHANNEL : 
(!!PageContext.getParameter("siteid")?
WCMConstants.OBJ_TYPE_WEBSITE : WCMConstants.OBJ_TYPE_WEBSITEROOT));
})(),
intHostType : (function(){
return PageContext.getParameter("IntHostType") || 
(!!PageContext.getParameter("channelid")? 101 : 
(!!PageContext.getParameter("siteid")? 103 : 1));
})(),
hostId : (function(){
return PageContext.getParameter("HostId") 
|| PageContext.getParameter("ChannelId") 
|| PageContext.getParameter("SiteId")
|| PageContext.getParameter("RootId")
|| PageContext.getParameter("SiteType");
})(),
tabHostType : (function(){
return PageContext.getParameter("TabHostType") || (!!PageContext.getParameter("channelid")?
WCMConstants.TAB_HOST_TYPE_CHANNEL : 
(!!PageContext.getParameter("siteid")?
WCMConstants.TAB_HOST_TYPE_WEBSITE : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT));
})(),
filteredOnEdit : function(event){
return !event.getObjs().getType().equalsI(PageContext.objectType)
&& event.getObj().getId()!=PageContext.hostId;
},
getActiveTabType : function(context){
return PageContext.activeTabType;
}
});
PageContext.m_CurrPage = $MsgCenter.$main(PageContext.getContext0());
Event.observe(window, 'load', function(){
PageContext.initPage();
listeningForMyObjs();
listeningForHosts();
if(PageContext.literator){
listeningForLiterator();
}
if(PageContext.keyProvider){
$MsgCenter.setKeyProvider(PageContext.prepareKeyProvider());
}
});
Event.observe(window, 'beforeunload', function(){
if(PageContext.m_CurrPage){
PageContext.m_CurrPage.afterdestroy();
PageContext.m_CurrPage = null;
}
});
function listeningForMyObjs(){
$MsgCenter.on({
objType : PageContext.objectType,
afteradd : function(event){
if(!$('grid_body')){
PageContext.refreshList(PageContext.params, [event.getIds()]);
}else{
PageContext.updateCurrRows(event.getIds());
}
},
afteredit : function(event){
PageContext.updateCurrRows(event.getIds());
},
afterdelete : function(event){
delete PageContext.params["SELECTIDS"];
PageContext.loadList(PageContext.params);
}
});
}
function listeningForHosts(){
$MsgCenter.on({
objType : PageContext.hostType,
afteredit : function(event){
var host = event.getObj();
if(host.getId()!=PageContext.hostId)return;
PageContext.loadList(null, null, true);
}
});
}
function listeningForLiterator(){
$MsgCenter.on({
id : 'literator_listen',
objType : [WCMConstants.OBJ_TYPE_WEBSITE, WCMConstants.OBJ_TYPE_CHANNEL, 
WCMConstants.OBJ_TYPE_CHANNELMASTER],
afteredit : function(event){
PageContext.drawLiterator();
},
aftermove : function(event){
PageContext.drawLiterator();
}
});
}
(function(){
try{
if(!window.frameElement || window.frameElement.id != 'main') return;
}catch(error){
return;
}
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_MAINPAGE,
afterinit : function(event){
Ext.apply(event.getContext(), {
tabEnable : PageContext.tabEnable,
operEnable : PageContext.operEnable,
filterEnable : PageContext.filterEnable
});
},
operpanelshow : function(event){
if(!wcm.Layout)return;
wcm.Layout.expandByChild('east', 'east_opers');
},
operpanelhide : function(event){
if(!wcm.Layout)return;
wcm.Layout.collapseByChild('east', 'east_opers');
}
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_MAINPAGE,
afterinit : function(event){
var ids = PageContext.editIds;
if(!ids) return;
delete PageContext.editIds;
ids = Ext.isArray(ids) ? ids : [ids];
if(ids.length <= 0) return;
(wcm.Grid || window.myThumbList).initEditedItems(ids);
}
});
})();
Event.observe(document, 'contextmenu', function(event){
if(WCMConstants.DEBUG || PageContext.enableContextMenu) return;
Event.stop(event || window.event);
});
(function(){
window.ProcessBar = window.ProcessBar || {};
Ext.applyIf(ProcessBar, {
start : function(){
try{
var pb = $MsgCenter.getActualTop().ProcessBar;
pb.start.apply(pb, arguments);
}catch(error){
}
},
close : function(){
try{
var pb = $MsgCenter.getActualTop().ProcessBar;
pb.close.apply(pb, arguments);
}catch(error){
}
}
});
})();

Ext.ns('wcm.BubblePanel');
function defBubblePanel(){
var ua = navigator.userAgent.toLowerCase();
var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
var hide = function(ev){
if(ev.type=='blur' && this.extEl.contains(ev.blurTarget))return;
var el = this.extEl.dom;
setTimeout(function(){
el.style.display = 'none';
}, 100);
}
var bubble = function(p, map, render){
var el = this.extEl.dom;
if(map)p = map.apply(el, [p]);
var ost = el.style;
if(render){
render.apply(el, [p]);
}else if(p){
ost.left = p[0] + 'px';
ost.top = p[1] + 'px';
}
ost.display = '';
el.focus();
};
wcm.BubblePanel = function(el, fid){
el = $(el);
var extEl = this.extEl = Ext.get(el);
if(isIE){
var arr = el.getElementsByTagName("*");
for(var i=0;i<arr.length;i++)
arr[i].setAttribute('unselectable', 'on');
}
else extEl.dom.tabIndex = 100;
extEl.on('click', hide, this);
extEl.on('blur', hide, this);
this.bubble = bubble;
}
};
defBubblePanel();

Ext.ns('wcm.Layout');
Ext.apply(wcm.Layout, {
collapse : function(_sDire, _sid){
var element = $(_sid);
Element.addClassName(element, 'hide_'+_sDire.toLowerCase());
},
expand : function(_sDire, _sid){
var element = $(_sid);
Element.removeClassName(element, 'hide_'+_sDire.toLowerCase());
},
collapseByChild : function(_sDire, _sid){
var element = $(_sid);
if(!element)return;
var elDivMain = element.parentNode;
Element.addClassName(elDivMain, 'hide_'+_sDire.toLowerCase());
},
expandByChild : function(_sDire, _sid){
var element = $(_sid);
if(!element)return;
var elDivMain = element.parentNode;
Element.removeClassName(elDivMain, 'hide_'+_sDire.toLowerCase());
}
});

Ext.ns('wcm.PageFilter', 'wcm.PageFilters');
(function(){
var myTemplate = {
outer : [
'<table height="28" border="0" cellpadding="0" cellspacing="0">',
'<tr height="28">', 
'<td align="left" valign="center">',
'<div height="28" class="pagefilter_container" id="pagefilter_container">',
'{0}',
'<span class="wcm_pointer pagefilter_more_btn" id="pagefilter_more_btn"></span>',
'</div>',
'</td>',
'</tr>',
'</table>',
'<div id="more_pagefilter" class="pagefilter_more_container" style="display:none">{1}</div>'
].join(''),
inner : [
'<span class="pagefilter {2}" pagefilter_type="{0}">',
'<table border="0" cellpadding="0" cellspacing="0">',
'<tr height="23">',
'<td class="left" width="7"></td>',
'<td class="middle" nowrap="nowrap" valign="middle">',
'<a href="#" onclick="return false">{1}</a>',
'</td>',
'<td class="right" width="7"></td>',
'</tr>',
'</table>',
'</span>'
].join('')
};
Ext.apply(wcm.PageFilter, {
init : function(info){
if(!info.enable || info.items.length==0)return;
if(!$('pageinfo'))return;
this.filters = info;
var minDisplayNum = Math.min(3, info.displayNum);
info.displayNum = window.screen.width <= 1024 ? minDisplayNum : info.displayNum;
var sValue = this._getHtml(info);
$('pageinfo').style.display = 'none';
Element.update($('pageinfo'), sValue);
$('pageinfo').style.display = '';
this.bindEvents(info);
wcm.PageFilter.selectFilterByType(info.filterType);
return info;
},
_getHtml : function(info){
var html1 = [], html2 = [];
var filters = info.items;
var num = filters.length;
for(var i=0;i<num && i<info.displayNum;i++){
var filter = filters[i];
html1.push(String.format(myTemplate.inner, filter.type, 
filter.desc, this.filterClass(filter)));
}
for(var i=info.displayNum;i<num;i++){
var filter = filters[i];
html2.push(String.format(myTemplate.inner, filter.type, 
filter.desc, this.filterClass(filter)));
}
return String.format(myTemplate.outer, html1.join(''), html2.join(''));
},
_QuickGetFilters : function(){
var aFilters = [];
var eTmpRows = $('pagefilter_container').childNodes;
for(var i=0;i<eTmpRows.length;i++){
if(eTmpRows[i].tagName && Element.hasClassName(eTmpRows[i], 'pagefilter')){
aFilters.push(eTmpRows[i]);
}
}
eTmpRows = $('more_pagefilter').childNodes;
for(var i=0;i<eTmpRows.length;i++){
if(eTmpRows[i].tagName && Element.hasClassName(eTmpRows[i], 'pagefilter')){
aFilters.push(eTmpRows[i]);
}
}
return aFilters;
},
findFilter : function(srcElement, parent){
while(srcElement!=null && srcElement!=$(parent || 'pageinfo')){
if(Element.hasClassName(srcElement, 'pagefilter'))return srcElement;
srcElement = srcElement.parentNode;
}
return null;
},
fireEvent : function(){
if(!this.filters.enable)return;
var nFilterType = this.getCurrFilterType();
var oPageFilter = this.filters.get(nFilterType);
if(oPageFilter==null)return;
if(oPageFilter.fn){
(oPageFilter.fn)();
return;
}
PageContext.loadList(Ext.apply(PageContext.params, {
filterType : nFilterType
}));
},
bindEvents : function(info){
var elPageFilter = $('pageinfo');
Ext.get('pageinfo').on('click', function(event, target){
target = this.findFilter(target);
if(!target)return;
this.selectFilter(target);
this.fireEvent();
}, this);
var filters = info.items;
var num = filters.length;
if(num > info.displayNum){
Ext.get('pagefilter_more_btn').on('click', function(length, iNum, event){
var p = event.getPoint();
var x = p.x + 4;
var y = p.y + 4;
var bubblePanel = new wcm.BubblePanel($('more_pagefilter'));
bubblePanel.bubble([x,y], function(_Point){
if(length <= iNum*2){
return [_Point[0]-this.offsetWidth, _Point[1]];
}
return [_Point[0], _Point[1]];
});
}.bind(this, num, info.displayNum));
document.body.appendChild($('more_pagefilter'));
Ext.get('more_pagefilter').on('click', function(event, target){
target = this.findFilter(target);
if(!target)return;
this.selectFilter(target, 'more_pagefilter');
this.fireEvent();
}, this);
}
else{
Element.hide($('pagefilter_more_btn'));
}
},
getCurrFilterType : function(){
if(this.activeFilter)
return this.activeFilter.getAttribute('pagefilter_type');
},
selectFilter : function(_eFilter){
if(this.activeFilter==_eFilter)
return;
if(_eFilter){
if(this.activeFilter){
Element.removeClassName(this.activeFilter, 'pagefilter_active');
Element.addClassName(this.activeFilter, 'pagefilter_deactive');
}
Element.addClassName(_eFilter, 'pagefilter_active');
Element.removeClassName(_eFilter, 'pagefilter_deactive');
this.activeFilter = _eFilter;
}
},
selectFilterByType : function(_nFilterType){
var eFilters = this._QuickGetFilters();
for(var i=0;i<eFilters.length;i++){
var eFilter = eFilters[i];
var sFilterType = eFilter.getAttribute('pagefilter_type');
if(_nFilterType==sFilterType){
this.selectFilter(eFilter);
return;
}
}
if(eFilters.length>0){
this.selectFilter(eFilters[0]);
}
}
});
function Filter(filter, filters){
filter.type = filter.type + '';
Ext.apply(this, filter);
this.filters = filters;
this.setOrder = function(order){
this.order = order;
items = this.filters.items;
var currItem = null;
for (var i=0,n=items.length; i<n; i++){
items[i].type = items[i].type + '';
if(items[i].type.equalsI(this.type)){
currItem = items[i];
items[i] = null;
break;
}
}
if(currItem==null)return this;
items = this.filters.items = items.compact();
items.splice(order-1, 0, currItem);
return this;
}
}
function Filters(info){
Ext.apply(this, info);
this.items = [];
this.register = this.addFilter = function(items){
if(!items)return;
var filters = this;
if(Ext.isArray(items)){
items.each(function(item, index){
filters.register(item);
});
return filters;
}
items.type = items.type==null ? ('ft_'+$MsgCenter.genId()) : items.type;
items.order = items.order || (filters.items.length + 1);
filters.items.splice(items.order-1, 0, items);
return filters;
}
this.get = this.getFilter = function(type){
var items = this.items;
type = type + '';
for (var i=0,n=items.length; i<n; i++){
items[i].type = items[i].type + '';
if(items[i].type.equalsI(type))
return new Filter(items[i], this);
}
return new Filter({}, this);
}
}
Ext.apply(wcm.PageFilter, {
filterClass : function(filter){
if(PageContext.pageFilters.filterType==filter.type)
return 'pagefilter_active';
return 'pagefilter_deactive';
}
});
wcm.PageFilters = Filters;
})();

Ext.ns('wcm.ThumbList', 'wcm.ThumbItem', 'wcm.ThumbItemMgr');
var m_ThumbItemConst = {
thumb_item_cls : 'thumb_item',
thumb_item_active_cls : 'thumb_item_active',
thumb_item_hover_cls : 'thumb_item_hover',
identify_attr : 'itemId',
thumb_item_id_prefix : 'thumb_item_',
thumb_id_prefix : 'thumb_',
thumb_attrs_id_prefix : 'thumb_attrs_',
thumb_edit_id_prefix : 'thumb_edit_',
cbx_id_prefix : 'cbx_',
desc_id_prefix : 'desc_',
thumb_selectAll : 'selectAll'
};
wcm.ThumbItemMgr = function(){
var type = {};
return {
registerType : function(_sType, _cls){
type[_sType.toUpperCase()] = _cls;
},
get : function(_sType, _sId){
if(_sId && !String.isString(_sId)){
_sId = _sId.getAttribute(m_ThumbItemConst["identify_attr"]);
}
var item = new type[_sType.toUpperCase()](_sId);
return item.wrapper ? item.wrapper() : item;
},
isThumbItem : function(_oDom){
if(!_oDom) return false;
var sThumbCls = m_ThumbItemConst["thumb_item_cls"];
return Element.hasClassName(_oDom, sThumbCls);
},
createListeners : function(node, fWrapper){
if(!node || !fWrapper)return;
var oldWrapper = node.prototype.wrapper;
node.prototype.wrapper = oldWrapper ? function(){
oldWrapper.apply(this, []);
fWrapper.apply(this, []);
return this;
} : function(){
fWrapper.apply(this, []);
return this;
};
}
};
}();
wcm.ThumbItem = function(_sId){
this.id = _sId;
wcm.ThumbItem.superclass.constructor.apply(this, arguments);
var events = ['beforeclick', 'click', 'beforehover', 'hover', 'beforeunhover', 'unhover', 'beforedblclick', 'dblclick', 'beforeedit', 'edit', 'beforesave', 'save'];
this.addEvents.apply(this, events);
};
(function(){
Ext.extend(wcm.ThumbItem, wcm.util.Observable, {
getId : function(){
return this.id;
},
getDom : function(){
return $(this["thumb_item_id_prefix"] + this.getId());
},
isActive : function(){
return Element.hasClassName(this.getDom(), this["thumb_item_active_cls"]);
},
isDisabled : function(){
var cbx = $(this["cbx_id_prefix"] + this.getId());
if(cbx){
var disabled = cbx.getAttribute('disabled', 2);
return disabled != null && disabled != false;
}
return false;
},
active : function(){
if(this.isActive()) return;
var XThumbItem = wcm.XThumbItem.get(this);
if(!XThumbItem.select()) return;
this.onActive();
XThumbItem.afterselect();
},
onActive : function(){
Element.addClassName(this.getDom(), this["thumb_item_active_cls"]);
var cbx = $(this["cbx_id_prefix"] + this.getId());
if(cbx){
cbx.checked = true;
cbx.defaultChecked = true;
}
this.list.onSelect(this);
},
deactive : function(){
if(!this.isActive()) return;
var XThumbItem = wcm.XThumbItem.get(this);
if(!XThumbItem.unselect()) return;
this.onDeactive();
XThumbItem.afterunselect();
},
onDeactive : function(){
Element.removeClassName(this.getDom(), this["thumb_item_active_cls"]);
var cbx = $(this["cbx_id_prefix"] + this.getId());
if(cbx){
cbx.checked = false;
cbx.defaultChecked = false;
}
this.list.onUnselect(this);
},
isHover : function(){
return Element.hasClassName(this.getDom(), this["thumb_item_hover_cls"]);
},
hover : function(event){
if(this.isHover()) return;
if(this.fireEvent('beforehover', event) === false) return;
this.onHover();
this.fireEvent('hover', event);
},
onHover : function(){
Element.addClassName(this.getDom(), this["thumb_item_hover_cls"]);
},
unhover : function(event){
if(!this.isHover()) return;
if(this.fireEvent('beforeunhover', event) === false) return;
this.onUnhover();
this.fireEvent('unhover', event);
},
onUnhover : function(){
Element.removeClassName(this.getDom(), this["thumb_item_hover_cls"]);
},
click : function(event){
if(this.fireEvent('beforeclick', event) === false) return;
this.fireEvent('click', event);
},
dblclick : function(event){
if(this.fireEvent('beforedblclick', event) === false) return;
this.fireEvent('dblclick', event);
},
edit : function(){
if(this.fireEvent('beforeedit') === false) return;
this.fireEvent('edit');
},
beforeSave : function(inputEl, descEl){
this.fireEvent('beforesave', inputEl, descEl);
},
save : function(inputEl, descEl){
this.fireEvent('save', inputEl, descEl);
},
afterSave : function(inputEl, descEl){
this.fireEvent('aftersave', inputEl, descEl);
},
isToggle : function(event){
if(event.ctrlKey) return true;
var oCbx = Event.element(event);
return oCbx.tagName == "INPUT" && oCbx.type.toUpperCase() == "CHECKBOX";
},
toggle : function(){
this[this.isActive() ? "deactive" : 'active']();
},
getCmdTarget : function(dom){
while(dom && dom.tagName != "BODY"){
if(dom.getAttribute("cmd")) return dom;
if(Element.hasClassName(dom, this["thumb_item_cls"])) return null;
dom = dom.parentNode;
}
return null;
},
getItemInfo : function(_oInfo){
_oInfo = _oInfo || this.itemInfo;
var result = {};
var dom = this.getDom();
if(!dom){
return {
objType : this.itemType(dom)
};
}
for(var sKey in _oInfo){
if(!_oInfo[sKey])continue;
result[sKey] = dom.getAttribute(sKey, 2);
}
result.objId = dom.getAttribute(this["identify_attr"], 2);
result.right = dom.getAttribute('right', 2);
result.objType = this.itemType(dom);
return result;
}
});
})();
(function(){
var select = function(_sItemId){
if(arguments.length > 1){
for (var i = 0; i < arguments.length; i++){
select.call(this, arguments[i]);
}
return;
}
if(!_sItemId) return;
this.get(_sItemId).active();
};
var unselect = function(_sItemId){
if(arguments.length > 1){
for (var i = 0; i < arguments.length; i++){
unselect.call(this, arguments[i]);
}
return;
}
if(!_sItemId) return;
this.get(_sItemId).deactive();
};
var selectAll = function(){
var box = $(this["container"]);
var dom = Element.first(box);
while(dom){
if(wcm.ThumbItemMgr.isThumbItem(dom)){
select.call(this, dom);
}
dom = Element.next(dom);
}
};
var unselectAll = function(){
this.selectSuspended = true;
var cache = this.cache;
for(var index = 0, len = cache.length; index < len; index++){
unselect.call(this, cache[index]);
}
this.cache = [];
delete this.selectSuspended;
};
var setLengthPreRow = function(){
this["length_pre_row"] = 0;
var r = $(this.container);
var p = Element.first(r);
if(!p){
return;
}
this["length_pre_row"] = 1;
var q = Element.next(p);
while(q){
if(p.offsetTop != q.offsetTop){
break;
}
this["length_pre_row"]++;
p = q;
q = Element.next(p);
}
};
wcm.ThumbList = function(sThumbType, sContainer){
this["thumb_type"] = sThumbType;
this["container"] = sContainer || this["container"];
this.cache = [];
};
Ext.apply(wcm.ThumbList.prototype, {
container : 'wcm_table_grid',
thumb_type : null,
length_pre_row : 0,
cache : null
});
Ext.apply(wcm.ThumbList.prototype, {
cmds : {},
addCmds : function(configs){
for(var sKey in configs){
this.cmds[sKey] = configs[sKey];
}
},
getCmd : function(sKey){
return this.cmds[sKey];
}
});
Ext.apply(wcm.ThumbList.prototype, {
init : function(info){
this.cache = [];
this.info = info || {};
if(info){
this.validCache(info["SelectedIds"]);
if(info["RecordNum"] == 0){
var dom = $('thumb_NoObjectFound');
if(dom){
Element.update('wcm_table_grid', Element.first(dom).outerHTML);
}
}
}
this.initEvents();
this.notify(true);
},
validCache : function(selectIds){
if(!selectIds) return;
var cache = [];
var aIds = selectIds.split(",");
for (var i = 0; i < aIds.length; i++){
if(this.getThumbItemDom(aIds[i])){
cache.push(aIds[i]);
}
}
this.cache = cache;
},
getIds : function(bArray){
if(bArray) return this.cache;
return this.cache.join(",");
},
initEvents : function(){
if(this.eventsInited) return;
this.eventsInited = true;
var box = this["container"];
Event.observe(box, 'click', this.clickEvent.bind(this));
Event.observe(box, 'dblclick', this.dblclickEvent.bind(this));
Event.observe(box, 'mousemove', this.mouseMoveEvent.bind(this));
Event.observe(box, 'mouseout', this.mouseOutEvent.bind(this));
Event.observe(box, 'resize', setLengthPreRow.bind(this));
Event.observe(box, 'selectstart', function(event){
var dom = Event.element(event);
if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return true;
Event.stop(event);
});
if(PageContext.contextMenuEnable){
Ext.fly('wcm_table_grid').on('contextmenu', function(event, target){
var extra = {extEvent : event, targetElement : target};
event = window.event || event;
var srcElement = Event.element(event);
var thumbItem = this.find(srcElement);
var XThumbItem = wcm.XThumbItem.get(thumbItem, extra);
XThumbItem.contextmenu();
Event.stop(event);
return false;
}, this);
}
},
clickEvent : function(event){
event = window.event || event;
var srcElement = Event.element(event);
var thumbItem = this.find(srcElement);
if(!thumbItem) return;
thumbItem.click(event);
this.notify();
},
onClick : function(thumbItem, extra){
if(extra && extra["type"] == 'toggle') return;
unselectAll.call(this);
},
dblclickEvent : function(event){
event = window.event || event;
var srcElement = Event.element(event);
var thumbItem = this.find(srcElement);
if(!thumbItem) return;
thumbItem.dblclick(event);
},
mouseMoveEvent : function(event){
event = window.event || event;
var srcElement = Event.element(event);
var thumbItem = this.find(srcElement);
if(thumbItem == this.lastThumbItem) return;
if(thumbItem && this.lastThumbItem
&& thumbItem.getId() == this.lastThumbItem.getId()){
return;
}
if(this.lastThumbItem) this.lastThumbItem.unhover(event);
if(thumbItem) thumbItem.hover(event);
this.lastThumbItem = thumbItem;
},
mouseOutEvent : function(event){
if(this.lastThumbItem){
this.lastThumbItem.unhover();
delete this.lastThumbItem;
}
},
get : function(_sId){
var thumbItem = wcm.ThumbItemMgr.get(this["thumb_type"], _sId);
thumbItem.list = this;
return thumbItem;
},
find : function(_oDom){
var box = $(this["container"]);
while(_oDom && _oDom != box){
if(wcm.ThumbItemMgr.isThumbItem(_oDom)){
return this.get(_oDom);
}
_oDom = _oDom.parentNode;
}
return null;
},
getThumbItemId : function(_itemDom){
var thumbItem = this.get(_itemDom);
return thumbItem.getId();
},
getThumbItemDom : function(_itemId){
var thumbItem = this.get(_itemId);
return thumbItem.getDom();
},
contain : function(_itemId){
return this.getThumbItemDom(_itemId) != null;
},
getFirstId : function(){
var box = $(this["container"]);
var itemDom = Element.first(box);
return this.getThumbItemId(itemDom);
},
getLastId : function(){
var box = $(this["container"]);
var itemDom = Element.last(box);
return this.getThumbItemId(itemDom);
},
getId : function(_sItemId, _sDirection, _nCount){
if(!_sItemId) return null;
var domItem = this.getThumbItemDom(_sItemId);
if(!domItem) return null;
domItem = this.getDom(domItem, _sDirection, _nCount);
if(domItem){
return this.getThumbItemId(domItem);
}
return null;
},
getDom : function(_oDomItem, _sDirection, _nCount){
while(_oDomItem && _nCount > 0){
_oDomItem = Element[_sDirection](_oDomItem)
_nCount--;
}
return _oDomItem;
},
editThumbItem : function(event){
if(this.cache.length != 1) return;
this.get(this.cache.last()).edit(event);
},
enterThumbItem : function(event){
if(this.cache.length != 1) return;
this.get(this.cache.last()).dblclick(event);
},
selectBefore : function(){
var itemId = this.getId(this.cache.last(), "previous", 1);
itemId = itemId || this.getLastId();
if(!itemId) return;
unselectAll.apply(this, arguments);
this.select(itemId);
},
selectAfter : function(){
var itemId = this.getId(this.cache.last(), "next", 1);
itemId = itemId || this.getFirstId();
if(!itemId) return;
unselectAll.apply(this, arguments);
this.select(itemId);
},
selectAbove : function(){
var count = this.getLengthPreRow();
var itemId = this.getId(this.cache.last(), "previous", count);
if(!itemId) return;
unselectAll.apply(this, arguments);
this.select(itemId);
},
selectBelow : function(){
var count = this.getLengthPreRow();
var itemId = this.getId(this.cache.last(), "next", count);
if(!itemId) return;
unselectAll.apply(this, arguments);
this.select(itemId);
},
select : function(_sItemId){
this.selectedChange = false;
select.apply(this, arguments);
this.notify();
},
onSelect : function(thumbItem){
this.selectedChange = true;
if(this.selectSuspended) return;
this.cache.push(thumbItem.getId());
},
unselect : function(_sItemId){
this.selectedChange = false;
unselect.apply(this, arguments);
this.notify();
},
onUnselect : function(thumbItem){
this.selectedChange = true;
if(this.selectSuspended) return;
this.cache.remove(thumbItem.getId());
},
toggle : function(_sItemId){
if(!_sItemId) return;
var thumbItem = this.get(_sItemId);
this[thumbItem.isActive() ? "unselect" : "select"]();
},
selectAll : function(){
this.selectedChange = false;
selectAll.apply(this, arguments);
this.notify();
},
unselectAll : function(){
this.selectedChange = false;
unselectAll.apply(this, arguments)
this.notify();
},
toggleAll : function(){
var bSelectAll = false;
var box = $(this["container"]);
var dom = Element.first(box);
while(dom){
if(wcm.ThumbItemMgr.isThumbItem(dom)){
var thumbItem = this.get(dom);
if(!thumbItem.isDisabled() && !thumbItem.isActive()){
bSelectAll = true;
break;
}
}
dom = Element.next(dom);
}
this[bSelectAll ? "selectAll" : "unselectAll"]();
},
notify : function(bForce){
if(bForce || this.selectedChange){
this.selectedChange = false;
wcm.XThumbList.get(this).selectedchange();
}
},
getLengthPreRow : function(){
if(this["length_pre_row"]) return this["length_pre_row"];
setLengthPreRow.call(this);
return this["length_pre_row"];
},
initEditedItems : function(ids){
if(!ids || ids.length <= 0) return;
var dom = this.getThumbItemDom(ids[0]);
if(!dom) return;
this.firstEditIds = true;
this.editIds = ids;
Element.addClassName(dom, "thumb-edit-item");
},
clearEditedItems : function(){
if(this.firstEditIds){
delete this.firstEditIds;
return;
}
var ids = this.editIds;
if(!ids || ids.length <= 0) return;
delete this.editIds;
var dom = this.getThumbItemDom(ids[0]);
if(!dom) return;
Element.removeClassName(dom, "thumb-edit-item");
},
getItemInfos : function(_oInfo){
var arrInfos = [];
var cache = this.cache;
for(var index = 0, len = cache.length; index < len; index++){
var thumbItem = this.get(cache[index]);
if(!thumbItem) continue;
arrInfos.push(thumbItem.getItemInfo(_oInfo));
}
return arrInfos;
}
});
})();
wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
this.on('beforedblclick', function(event){
var extra = {event : event};
var dom = Event.element(event);
if(dom.tagName == 'INPUT' && dom.type.toUpperCase() == 'TEXT') return false;
var dom = this.getCmdTarget(dom);
if(!dom)return true;
var cmd = dom.getAttribute("cmd");
if(cmd) return false;
return true;
});
this.on('beforeclick', function(event){
var extra = {event : event};
var dom = this.getCmdTarget(Event.element(event));
if(!dom)return true;
var cmd = dom.getAttribute("cmd");
if(cmd) extra["cmd"] = cmd;
var oXThumbCell = wcm.XThumbCell.get(this, extra);
if(oXThumbCell.click())
oXThumbCell.afterclick();
return false;
});
this.on('beforeclick', function(event){
if(!this.isActive()) return true;
var dom = Event.element(event);
if(dom.tagName == "INPUT"
&& dom.type.toUpperCase() == "TEXT")
return false;
var bindEl = dom.getAttribute("bind");
if(!bindEl) return true;
this.edit();
return false;
});
this.on('click', function(event){
this.getDom().focus();
if(this.isToggle(event)){
this.list.onClick(this, {type : 'toggle'});
this.toggle();
}else{
var sMethod = this.isActive() ? "deactive" : "active";
this.list.onClick(this);
this[sMethod]();
}
});
});
(function(){
var ensureInput = function(descEl, bindEl){
var editable = $(this["thumb_edit_id_prefix"] + this.getId())
var input = editable.getElementsByTagName("input")[0];
if(!input){
editable.innerHTML = bindEl.innerHTML;
input = editable.getElementsByTagName("input")[0];
}
input.value = descEl.innerHTML.unescapeHTML();
input.onblur = this.beforeSave.bind(this, input, descEl);
input.onkeydown = function(event){
event = window.event || event;
if(event.keyCode != Event.KEY_RETURN) return;
this.blur();
};
try{
input.focus();
input.select();
}catch(error){
}
};
wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
this.on('beforeedit', function(event){
var descEl = $(this["desc_id_prefix"] + this.getId());
if(!descEl) return false;
var bindEl = descEl.getAttribute("bind");
if(!bindEl) return false;
});
this.on('edit', function(event){
var sId = this.getId();
Element.hide(this["thumb_attrs_id_prefix"] + sId);
Element.show(this["thumb_edit_id_prefix"] + sId);
var descEl = $(this["desc_id_prefix"] + sId);
var bindEl = $(descEl.getAttribute("bind"));
ensureInput.call(this, descEl, bindEl);
});
});
})();
(function(){
wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
this.on('beforesave', function(inputEl, descEl){
var sValue = inputEl.value.trim();
if(sValue.escapeHTML() == descEl.innerHTML){
this.afterSave(inputEl, descEl);
return;
}
ValidatorHelper.asynValid(inputEl, {
success : this.save.bind(this, inputEl),
fail : function(warning){
Ext.Msg.warn(warning, function(){
try{
inputEl.value = sValue || descEl.innerHTML.unescapeHTML() || "";
inputEl.focus();
inputEl.select();
}catch(error){
}
});
}
});
});
this.on('save', function(inputEl){
var sServiceId = inputEl.getAttribute("serviceId") || PageContext.serviceId;
var sMethodName = inputEl.getAttribute("methodName") || "save";
var oPostData = {objectId : this.getId()};
oPostData[inputEl.name] = inputEl.value.trim();
BasicDataHelper.call(sServiceId, sMethodName, oPostData, true, function(){
var context = this.getItemInfo(this.itemInfo);
CMSObj.createFrom(context).afteredit();
}.bind(this));
});
this.on('aftersave', function(inputEl, descEl){
inputEl.style.border = '1px solid silver';
descEl.innerHTML = inputEl.value.escapeHTML();
var sId = this.getId();
Element.hide(this["thumb_edit_id_prefix"] + sId);
Element.show(this["thumb_attrs_id_prefix"] + sId);
inputEl.onblur = null;
inputEl.onkeydown = null;
});
});
})();
wcm.ThumbItemMgr.createListeners(wcm.ThumbItem, function(){
Ext.apply(this, m_ThumbItemConst);
});
Ext.ns('wcm.XThumbList', 'wcm.XThumbItem', 'wcm.XThumbCell');
WCMConstants.OBJ_TYPE_XTHUMBLIST = "XThumbList";
wcm.XThumbList = function(list, extra){
var context = (list) ? this.buildContext(list) : null;
this.objType = WCMConstants.OBJ_TYPE_XTHUMBLIST;
wcm.XThumbList.superclass.constructor.call(this, null, Ext.applyIf(context, extra));
this.addEvents(['selectedchange']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_XTHUMBLIST, 'wcm.XThumbList');
Ext.apply(wcm.XThumbList, {
get : function(list, extra){
return new wcm.XThumbList(list, extra);
}
});
Ext.extend(wcm.XThumbList, wcm.CMSObj, {
isCMSObjType : function(){
return false;
},
buildContext : function(list){
if(list == null) return null;
var context = (this._buildContext) ? this._buildContext(list) : null;
var info = Ext.applyIf(list.get().getItemInfo(), {list : list});
return Ext.applyIf(info, context);
}
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
selectedchange : function(event){
var list = event.getContext().list;
var elements = list.getItemInfos(list.get().itemInfo);
var context = {sysOpers : wcm.SysOpers, list : list};
Ext.apply(context, list.info);
Ext.apply(context, PageContext.getContext());
var oCmsObjs = CMSObj.createEnumsFrom({
objType : list.get().itemType()
}, context);
oCmsObjs.addElement(elements);
oCmsObjs.afterselect();
}
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_XTHUMBLIST,
selectedchange : function(event){
var list = event.getContext().list;
var bSelectAll = false;
var box = $(list["container"]);
var dom = Element.first(box);
var sel = $(m_ThumbItemConst.thumb_selectAll);
if(dom.id == "" && sel) {
sel.checked = false;
return;
}
while(dom){
if(wcm.ThumbItemMgr.isThumbItem(dom)){
var thumbItem = list.get(dom);
if(!thumbItem.isDisabled() && !thumbItem.isActive()){
bSelectAll = true;
break;
}
}
dom = Element.next(dom);
}
if(sel) sel.checked = bSelectAll ? false : true;
}
});
WCMConstants.OBJ_TYPE_XTHUMBITEM = "XThumbItem";
wcm.XThumbItem = function(item, extra){
var context = (item) ? this.buildContext(item) : null;
this.objType = WCMConstants.OBJ_TYPE_XTHUMBITEM;
wcm.XThumbItem.superclass.constructor.call(this, null, Ext.applyIf(context || {}, extra));
this.addEvents(['select', 'afterselect', 'unselect', 'afterunselect', 'contextmenu']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_XTHUMBITEM, 'wcm.XThumbItem');
Ext.apply(wcm.XThumbItem, {
get : function(item, extra){
return new wcm.XThumbItem(item, extra);
}
});
Ext.extend(wcm.XThumbItem, wcm.CMSObj, {
isCMSObjType : function(){
return false;
},
buildContext : function(item){
if(item == null) return null;
var context = (this._buildContext) ? this._buildContext(item) : PageContext.getContext();
var info = Ext.applyIf(item.getItemInfo(), {item : item});
return Ext.applyIf(info, context);
}
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_XTHUMBITEM,
contextmenu : function(event){
if(event.from()!=wcm.getMyFlag())return;
wcm.ThumbList.ContextMenuHelper.dispatch(event);
return false;
}
});
WCMConstants.OBJ_TYPE_XTHUMBCELL = "XThumbCell";
wcm.XThumbCell = function(item, extra){
var context = (item) ? this.buildContext(item) : null;
this.objType = WCMConstants.OBJ_TYPE_XTHUMBCELL;
wcm.XThumbCell.superclass.constructor.call(this, null, Ext.applyIf(context, extra));
this.addEvents(['click', 'afterclick']);
};
CMSObj.register(WCMConstants.OBJ_TYPE_XTHUMBCELL, 'wcm.XThumbCell');
Ext.apply(wcm.XThumbCell, {
get : function(item, extra){
return new wcm.XThumbCell(item, extra);
}
});
Ext.extend(wcm.XThumbCell, wcm.CMSObj, {
isCMSObjType : function(){
return false;
},
buildContext : function(item){
if(item == null) return null;
var context = (this._buildContext) ? this._buildContext(item) : PageContext.getContext();
var info = Ext.applyIf(item.getItemInfo(), {item : item});
return Ext.applyIf(info, context);
}
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_XTHUMBCELL,
afterclick : function(event){
var context = event.getContext();
var thumbItem = context.item;
var thumbList = thumbItem.list;
var oCmsObjs = CMSObj.createEnumsFrom({
objType : context.objType
}, context);
oCmsObjs.addElement(CMSObj.createFrom(context));
event.objs = oCmsObjs;
var cmd = thumbList.getCmd(context.cmd);
if(cmd) cmd(event);
}
});
$MsgCenter.on({
sid : 'sys_allcmsobjs',
objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
afterselect : function(event){
if(!arguments.callee.caller)return;
var list = event.getContext().list;
if(list) list.clearEditedItems();
PageContext.event = event;
var objs = event.getObjs();
if(wcm.PageOper)wcm.PageOper.render(event);
}
});
Event.observe(window, 'load', function(){
if(!PageContext.keyProvider)return;
var myThumbList = (PageContext.getThumbList || Ext.emptyFn)() || window.myThumbList;
if(!myThumbList) return;
Ext.apply(PageContext.keyProvider, {
keyA : function(event){
myThumbList.toggleAll();
try{
window.focus();
}catch(err){}
}
});
Event.observe(document, 'keydown', function(event){
var event = event || window.event;
var eTarget = Event.element(event);
var bIsTextInput = true;
if(eTarget != null){
bIsTextInput = (eTarget.nodeName.toUpperCase() == 'INPUT' && eTarget.type != 'checkbox')
||(eTarget.nodeName.toUpperCase() == 'TEXTAREA')
||(eTarget.nodeName.toUpperCase() == 'SELECT');
}
if(bIsTextInput)return;
if(event.ctrlKey || event.altKey)return;
switch(event.keyCode){
case Event.KEY_UP:
return myThumbList.selectAbove();
case Event.KEY_DOWN:
return myThumbList.selectBelow();
case Event.KEY_LEFT:
return myThumbList.selectBefore();
case Event.KEY_RIGHT:
return myThumbList.selectAfter();
case Event.KEY_RETURN:
return myThumbList.enterThumbItem(event);
case Event.KEY_F2:
return myThumbList.editThumbItem(event);
}
});
});
if(Ext.isIE6){
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
afterselect : function(event){
if(event.from()!=wcm.getMyFlag())return;
if(!arguments.callee.caller)return;
var dom = $('wcm_table_grid');
if(!dom) return;
Element.addClassName(dom, 'fix-ie6-redraw');
Element.removeClassName(dom, 'fix-ie6-redraw');
}
});
}
wcm.ThumbList.ContextMenuHelper = {
findCMEl : function(dom){
while(dom && dom.tagName != 'BODY'){
if(dom.getAttribute("contextmenu")) return dom;
dom = dom.parentNode;
}
return null;
},
init : function(event){
var context = event.getContext();
this.obj = event.getObj();
this.extEvent = context.get('extEvent');
this.browserEvent = this.extEvent.browserEvent;
this.targetElement = context.get('targetElement');
this.cmEl = this.findCMEl(this.targetElement);
this.item = context.get('item');
if(this.item){
var list = this.item.list;
this.items = list.getItemInfos(list.get().itemInfo);
}
},
destroy : function(){
delete this.obj;
delete this.extEvent;
delete this.browserEvent;
delete this.targetElement;
delete this.cmE1;
delete this.item;
delete this.items;
},
dispatch : function(event){
this.init(event);
if(this.browserEvent.ctrlKey && this.items.length > 0){
this.more(event);
}else if(this.cmEl == null){
this.none(event);
}else{
this.one(event);
}
this.destroy();
},
none : function(event){
var pcEvent = PageContext.event;
var context = pcEvent.getContext();
var relateType = PageContext.relateType || context.relateType;
var host = context.getHost();
var info = {
type : relateType.toLowerCase(),
right : host.right,
objs : host,
frompanel : true
};
var arrOpers = wcm.SysOpers.getOpersByInfo(info, pcEvent);
var wcmEvent = CMSObj.createEvent({objType : relateType}, PageContext.getContext());
var context = Ext.applyIf({
extEvent : this.extEvent,
event : this.browserEvent,
targetElement : this.targetElement,
opers : arrOpers,
wcmEvent : wcmEvent
}, PageContext.getContext());
$MsgCenter.$main(context).contextmenu();
},
one : function(event){
var itemInfo = this.item.getItemInfo();
var wcmEvent = CMSObj.createEvent(itemInfo, PageContext.getContext());
var context = Ext.applyIf({
extEvent : this.extEvent,
event : this.browserEvent,
targetElement : this.targetElement,
wcmEvent : wcmEvent
}, PageContext.getContext());
$MsgCenter.$main(context).contextmenu();
},
more : function(event){
var context = Ext.applyIf({
extEvent : this.extEvent,
event : this.browserEvent,
targetElement : this.targetElement,
wcmEvent : PageContext.event
}, PageContext.getContext());
$MsgCenter.$main(context).contextmenu();
}
};

Ext.ns('wcm.PageOper');
(function(){
var m_sDetailTitle = (wcm.LANG.ABSLIST_OPER_DETAIL||'详细信息');
var myTemplate = {
operpanel : [
'<div class="pageoper" id="{0}">',
'<div class="pageoper_header">',
'<div class="pageoper_title" id="{0}_title">', (wcm.LANG.ABSLIST_OPER_LOADING||'正在加载...'), '</div>',
'<div class="pageoper_collapse" id="{0}_toggle"></div>',
'</div>',
'<div class="pageoper_body" id="{0}_body">',
'<div class="pageoper_content" id="{0}_content">',
'<div class="oper_items" id="{0}_items"></div>',
'</div>',
'<div class="pageoper_more_btn {0}_more_btn" id="{0}_more_btn" title="',
(wcm.LANG.ABSLIST_OPER_MORE || '更多操作'), '" style="display:"></div>',
'</div>',
'<div class="pageoper_underside"></div>',
'<div class="{0}_sep"></div>',
'</div>',
'<div class="oper_items_more" id="{0}_more" style="display: none"></div>'
].join(''),
detailpanel : [
'<div class="pageoper" id="{0}">',
'<div class="pageoper_header">',
'<div class="pageoper_title" id="{0}_title">', m_sDetailTitle, '</div>',
'<div class="pageoper_collapse" id="{0}_toggle"></div>',
'</div>',
'<div class="pageoper_body" id="{0}_body">',
'<div class="pageoper_content pageoper_flexible_content" id="{0}_content">',
'</div>',
'<div class="pageoper_more_btn {0}_more_btn" id="{0}_more_btn" title="',
(wcm.LANG.ABSLIST_OPER_DETAILMORE || '更多属性'), '" style="display:none">',
(wcm.LANG.ABSLIST_OPER_DETAILMORE || '更多属性'),
'</div>',
'</div>',
'<div class="pageoper_underside"></div>',
'<div class="{0}_sep"></div>',
'</div>'
].join(''),
operitem : [
'<div class="oper_item {4}" {3} _type="{0}" _key="{1}">',
'<div class="oper_item_row">',
'<div class="oper_item_row_icon {1}"></div>',
'<div class="oper_item_row_desc">{2}&nbsp;{5}</div>',
'</div>',
'</div>'
].join(''),
seperator : [
'<div class="oper_item_seperate oper_item_more" title="分隔线" _type="{0}" _key="seperate">',
'<div class="oper_item_row {0} seperate">&nbsp;</div>',
'</div>'
].join('')
};
var m_TransHelper = {
inputDomHtml : [
'<input type="text" name="{0}" value="{1}"/>'
].join(''),
validElement : function(input, sValidation, info){
if(sValidation) {
input.setAttribute("validation", sValidation);
}
info = info || {};
var validInfo = ValidatorHelper.asynValid(input, {
success : info["success"],
fail : function(warning){
Ext.Msg.warn(warning, function(){
if(Element.visible(input)){
input.value = info["oldValue"] || "";
input.select();
}
});
}
});
},
basicAction : function(valueDom, wcmEvent, info){
var sServiceId = valueDom.getAttribute('_serviceId', 2) || PageContext.serviceId;
var sMethodName = valueDom.getAttribute('_methodName', 2) || 'save';
var inputDom = this;
var obj = wcmEvent.getObjs().getAt(0) || wcmEvent.getHost();
var sFieldName = valueDom.getAttribute("_fieldName", 2);
if(!sFieldName)return;
var oPost = Ext.apply({
OBJECTID : obj.getId()
}, info);
oPost[sFieldName.trim().toUpperCase()] = inputDom.value || valueDom.getAttribute("_fieldValue", 2);
var pageParams = Ext.Json.toUpperCase(PageContext._buildParams(wcmEvent, sMethodName, valueDom));
if(pageParams!=null && pageParams.FORCE){
oPost = Ext.apply(oPost, pageParams.FORCE);
delete pageParams.FORCE;
oPost = Ext.applyIf(oPost, pageParams);
}else{
oPost = Ext.applyIf(oPost, pageParams);
}
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.call(sServiceId, sMethodName, oPost, false,
function(wcmEvent){
var valueDom = this;
var sActionCallback = valueDom.getAttribute('_callback', 2) || '';
var fActionCallback = PageContext[sActionCallback] || window[sActionCallback];
if(Ext.isFunction(fActionCallback)){
fActionCallback(valueDom, wcmEvent);
}else{
wcmEvent.getObj().afteredit();
}
}.bind(valueDom, wcmEvent),
function(transport, json){
var lastValue = valueDom.getAttribute('_lastFieldValue');
inputDom.value = lastValue;
valueDom.setAttribute('_fieldValue', lastValue);
if(inputDom.tagName == 'INPUT'){
Element.update(valueDom, $transHtml(inputDom.value));
}else{
var optionEl = inputDom.options[inputDom.selectedIndex];
var spanEl = valueDom.getElementsByTagName('SPAN')[0];
if(spanEl){
Element.update(spanEl, optionEl.innerHTML);
}
(window.$render500Err||emptyFn)(transport, json);
}
}
);
},
inputKeyPress : function(valueDom, wcmEvent, event){
var inputDom = this;
event = event || window.event;
if(event.keyCode==13){
inputDom.blur();
}
},
suggestionKeyPress : function(valueDom, wcmEvent, event){
var inputDom = this;
event = event || window.event;
if(event.keyCode==13){
inputDom.blur();
return;
}
var suggest = valueDom.getAttribute('_suggestionFn', 2);
var fSuggest = (PageContext[suggest] || window[suggest]);
if(!fSuggest)return;
fSuggest.apply(inputDom, [event, valueDom, wcmEvent]);
},
inputBlurEffect : function(inputDom, valueDom, wcmEvent, event){
inputDom.onblur = inputDom.onkeypress = null;
event = event || window.event;
valueDom.removeAttribute('active');
Element.removeClassName(valueDom, 'wcm_attr_value_edit');
var lastValue = valueDom.getAttribute('_fieldValue', 2);
valueDom.setAttribute('_fieldValue', inputDom.value);
valueDom.setAttribute('_lastFieldValue', lastValue);
var sDomType = valueDom.className
.replace(/wcm_attr_value(_withborder)?/ig, '').trim();
if(sDomType != "suggestionType"){
Element.update(valueDom, $transHtml(inputDom.value));
}
var sAction = valueDom.getAttribute('_action', 2);
var fAction = PageContext[sAction] || window[sAction] || m_TransHelper.basicAction;
if(inputDom.value!=lastValue){
fAction.apply(inputDom, [valueDom, wcmEvent]);
}
},
inputBlur : function(valueDom, wcmEvent, event){
var inputDom = this;
var lastValue = valueDom.getAttribute('_fieldValue', 2);
if(inputDom.value == lastValue || 
!ValidationHelper.hasValid(valueDom, m_TransHelper.getName(valueDom))){
m_TransHelper.inputBlurEffect(inputDom, valueDom, wcmEvent, event);
return;
}
var sValidation = valueDom.getAttribute("validation", 2);
var sValidDesc = valueDom.getAttribute("validation_desc", 2);
if(sValidDesc) inputDom.setAttribute("validation_desc", sValidDesc);
var info = {
oldValue : m_TransHelper.getValue(valueDom),
success : function(){
m_TransHelper.inputBlurEffect(inputDom, valueDom, wcmEvent, event);
}
};
m_TransHelper.validElement(inputDom, sValidation, info);
},
selectChange : function(valueDom, wcmEvent, event){
var selector = this;
selector.blur();
},
selectBlur : function(valueDom, wcmEvent, event){
var selector = this;
event = event || window.event;
selector.onblur = selector.onchange = null;
valueDom.removeAttribute('active');
var lastValue = valueDom.getAttribute('_fieldValue', 2);
if(selector.selectedIndex==-1)selector.selectedIndex=0;
valueDom.setAttribute('_fieldValue', selector.value);
valueDom.setAttribute('_lastFieldValue', lastValue);
var optionEl = selector.options[selector.selectedIndex];
var spanEl = valueDom.getElementsByTagName('SPAN')[0];
if(spanEl){
Element.update(spanEl, optionEl.innerHTML);
Element.hide(selector);
Element.show(spanEl);
}
var sAction = valueDom.getAttribute('action', 2);
var fAction = PageContext[sAction] || window[sAction] || m_TransHelper.basicAction;
if(selector.value!=lastValue){
fAction.apply(selector, [valueDom, wcmEvent]);
}
},
getValueDomInput : function(valueDom){
return String.format(m_TransHelper.inputDomHtml, $transHtml(valueDom.getAttribute('_fieldName')),
$transHtml(valueDom.getAttribute('_fieldValue')));
},
getName : function(valueDom){
return valueDom.getAttribute('_fieldName');
},
getValue : function(valueDom){
return valueDom.getAttribute('_fieldValue');
},
inputDomEvent : function(input, valueDom, pageoper){
input.onkeypress = m_TransHelper.inputKeyPress.bind(input, valueDom, pageoper.event);
input.onblur = m_TransHelper.inputBlur.bind(input, valueDom, pageoper.event);
input.select();
},
selectDomEvent : function(selector, valueDom, pageoper){
selector.onchange = m_TransHelper.selectChange.bind(selector, valueDom, pageoper.event);
selector.onblur = m_TransHelper.selectBlur.bind(selector, valueDom, pageoper.event);
selector.focus();
},
suggestionDomEvent : function(input, valueDom, pageoper){
input.onkeypress = m_TransHelper.suggestionKeyPress.bind(input, valueDom, pageoper.event);
input.onblur = m_TransHelper.inputBlur.bind(input, valueDom, pageoper.event);
input.select();
},
setCalendarValue : function(valueDom, inputDom){
var sValidation = valueDom.getAttribute("validation", 2);
var info = {
oldValue : m_TransHelper.getValue(valueDom),
success : function(){
var lastValue = valueDom.getAttribute('_fieldValue', 2);
valueDom.setAttribute('_fieldValue', inputDom.value);
Element.update(valueDom, $transHtml(inputDom.value));
var sAction = valueDom.getAttribute('action', 2);
var fAction = PageContext[sAction] || window[sAction] || m_TransHelper.basicAction;
if(inputDom.value!=lastValue){
fAction.apply(inputDom, [valueDom, PageContext.event]);
}
}
};
m_TransHelper.validElement(inputDom, sValidation, info);
}
}
var m_oValueDomTrans = {
input : function(valueDom){
Element.addClassName(valueDom, 'wcm_attr_value_edit');
Element.update(valueDom, m_TransHelper.getValueDomInput(valueDom));
var inputDom = valueDom.getElementsByTagName('input')[0];
if(inputDom!=null){
m_TransHelper.inputDomEvent(inputDom, valueDom, this);
}
},
combox : function(valueDom){
},
suggestionType : function(valueDom){
var valueDomId = valueDom.id;
var inputDom = $(valueDomId).getElementsByTagName('input')[0];
if(inputDom!=null){
m_TransHelper.suggestionDomEvent(inputDom, valueDom, this);
}
},
select : function(valueDom){
var selectEl = $(valueDom.getAttribute('_selectEl', 2));
if(!selectEl)return;
var spanEl = valueDom.getElementsByTagName('SPAN')[0];
if(!spanEl){
Element.update(valueDom, '');
spanEl = document.createElement('SPAN');
valueDom.appendChild(spanEl);
}
Element.hide(spanEl);
valueDom.appendChild(selectEl);
selectEl.value = valueDom.getAttribute('_fieldValue', 2);
Element.show(selectEl);
m_TransHelper.selectDomEvent(selectEl, valueDom, this);
}
}
Ext.apply(wcm.PageOper, {
defaults : {},
transHelper : m_TransHelper,
registerPanels : function(infos){
for(type in infos){
this.defaults[type.toLowerCase()] = infos[type];
}
return this;
},
init : function(info){
this.enable = info.enable;
if(!info.enable){
wcm.Layout.collapseByChild('east', 'east_opers');
return;
}
this.drawConstructor(info);
},
drawConstructor : function(info){
var constructor = [];
constructor.push(String.format(myTemplate.operpanel, 'pageoper_1'));
constructor.push(String.format(myTemplate.operpanel, 'pageoper_2'));
constructor.push(String.format(myTemplate.detailpanel, 'pageoper_3'));
Ext.get('east_opers').update(constructor.join(''), false, function(){
document.body.appendChild($('pageoper_1_more'));
document.body.appendChild($('pageoper_2_more'));
});
Ext.get('east_opers').on('click', function(event, target){
var sClassName = ','+target.className.replace(/\s/g, ',')+',';
if(sClassName.indexOf(',pageoper_collapse,')!=-1
|| sClassName.indexOf(',pageoper_expand,')!=-1){
return this.togglePanel(target);
}
if(sClassName.indexOf(',pageoper_more_btn,')!=-1){
return this.showMorePanel(event, target);
}
}, this);
Ext.getBody().on('click', function(event, target){
var sClassName = ','+target.className.replace(/\s/g, ',')+',';
if(sClassName.indexOf(',oper_item,')!=-1
|| sClassName.indexOf(',oper_item_row,')!=-1){
return this.doClickOperItem(event, target);
}
if(sClassName.indexOf(',oper_item_row_icon,')!=-1
|| sClassName.indexOf(',oper_item_row_desc,')!=-1){
return this.doClickOperItem(event, target.parentNode);
}
}, this);
Ext.getBody().on('mouseover', function(event, target){
var sClassName = ','+target.className.replace(/\s/g, ',')+',';
if(sClassName.indexOf(',oper_item_row,')!=-1){
return this.doMouseOverOperItem(event, target);
}
if(sClassName.indexOf(',oper_item_row_icon,')!=-1
|| sClassName.indexOf(',oper_item_row_desc,')!=-1){
return this.doMouseOverOperItem(event, target.parentNode);
}
}, this);
Ext.getBody().on('mouseout', function(event, target){
var sClassName = ','+target.className.replace(/\s/g, ',')+',';
if(sClassName.indexOf(',oper_item_row,')!=-1){
return this.doMouseOutOperItem(event, target);
}
if(sClassName.indexOf(',oper_item_row_icon,')!=-1
|| sClassName.indexOf(',oper_item_row_desc,')!=-1){
return this.doMouseOutOperItem(event, target.parentNode);
}
}, this);
Ext.get('pageoper_3').on('click', function(event, target){
var eventinfo = this.getValue3EventInfo(target);
this.clickValueDom(eventinfo);
}, this);
Ext.get('pageoper_3').on('mousemove', function(event, target){
var mouseinfo = this.getValue3EventInfo(target);
this.unhoverLastValueDom(mouseinfo);
this.hoverValueDom(mouseinfo);
}, this);
},
clickValueDom : function(eventInfo){
var valueDom = eventInfo.valueDom;
var rowDom = eventInfo.rowDom;
if(!rowDom || !valueDom)return;
if(!Ext.get(rowDom).hasClass('editable'))return;
if(valueDom.getAttribute('active'))return;
var extValueDom = Ext.get(valueDom);
valueDom.setAttribute('active', '1');
this.renderValueDom(valueDom);
this.last3eventinfo = eventInfo;
},
renderValueDom : function(valueDom){
var sDomType = valueDom.className
.replace(/wcm_attr_value(_withborder)?/ig, '').trim();
sDomType = sDomType || 'input';
var fn = m_oValueDomTrans[sDomType];
if(!fn)return;
fn.call(this, valueDom);
},
getValue3EventInfo : function(srcElement){
var valueDom = null, rowDom = null;
while(srcElement!=null && srcElement!=$('pageoper_3')){
if(Element.hasClassName(srcElement, 'wcm_attr_value')){
valueDom = srcElement;
}
else if(Element.hasClassName(srcElement, 'attribute_row')){
rowDom = srcElement;
}
srcElement = srcElement.parentNode;
}
return {
valueDom : valueDom,
rowDom : rowDom
};
},
unhoverLastValueDom : function(mouseinfo){
if(!this.last3mouseinfo)return;
var valueDom = this.last3mouseinfo.valueDom;
if(mouseinfo.valueDom==valueDom)return;
var rowDom = this.last3mouseinfo.rowDom;
if(!rowDom || !valueDom)return;
if(!Ext.get(rowDom).hasClass('editable'))return;
Ext.get(valueDom).removeClass('wcm_attr_value_withborder');
},
hoverValueDom : function(mouseinfo){
var valueDom = mouseinfo.valueDom;
var rowDom = mouseinfo.rowDom;
if(!rowDom || !valueDom)return;
if(!Ext.get(rowDom).hasClass('editable'))return;
Ext.get(valueDom).addClass('wcm_attr_value_withborder');
this.last3mouseinfo = mouseinfo;
},
doClickOperItem : function(event, target){
var flyTarget = Ext.fly(target);
target = flyTarget.hasClass('oper_item')?target:target.parentNode;
var type = target.getAttribute('_type', 2);
var key = target.getAttribute('_key', 2);
wcm.SysOpers.exec(type, key, this.event);
return false;
},
doMouseOverOperItem : function(event, target){
var flyTarget = Ext.fly(target);
flyTarget.addClass('oper_item_active');
},
doMouseOutOperItem : function(event, target){
var flyTarget = Ext.fly(target);
flyTarget.removeClass('oper_item_active');
},
_prepare : function(event){
this.event = event;
var context = event.getContext();
this.host = context.getHost();
this.relateType = PageContext.relateType || context.relateType;
if(!this.relateType){
Ext.Msg.d$alert(wcm.LANG.AbsList_1002 || '页面PageContext.getContext()方法没有指定relateType.');
return false;
}
var bCustomizeInfo = false;
var defaultNum4Panel1 = 5;
var defaultNum4Panel2 = 7;
if(top.m_CustomizeInfo && top.m_CustomizeInfo.operator){
for(var name in top.m_CustomizeInfo.operator){
if(name.toLowerCase() == this.relateType.toLowerCase()){
bCustomizeInfo = true;
defaultNum4Panel1 = top.m_CustomizeInfo.operator[name].split(";")[0].split(":")[1] || 5;
break;
}
}
}
this.objs = event.getObjs();
var def1 = this.defaults[this.relateType.toLowerCase()];
this.panel1Info = {
isHost : true,
type : this.relateType.toLowerCase(),
right : this.host.right,
objs : this.host,
displayNum : bCustomizeInfo ? defaultNum4Panel1 : (def1?def1.displayNum:defaultNum4Panel1),
frompanel : true
};
var panel2Type = this.objs.objType;
var panel2Right = '';
var data = this.objs;
if(this.objs.length()==0){
panel2Type = this.host.getType();
panel2Right = this.host.right;
data = this.host;
}else if(this.objs.length()>1){
panel2Type = panel2Type + 's';
panel2Right = this._mergeRight(this.objs);
}else{
panel2Right = this.objs.getAt(0).right;
}
var def2 = this.defaults[panel2Type.toLowerCase()];
if(top.m_CustomizeInfo && top.m_CustomizeInfo.operator){
for(var name in top.m_CustomizeInfo.operator){
if(name.toLowerCase() == panel2Type.toLowerCase()){
bCustomizeInfo = true;
defaultNum4Panel2 = top.m_CustomizeInfo.operator[name].split(";")[0].split(":")[1] || 7;
break;
}
}
}
this.panel3Info = this.panel2Info = {
isHost : false,
type : panel2Type.toLowerCase(),
right : panel2Right,
objs : data,
displayNum : bCustomizeInfo ? defaultNum4Panel2 : (def2?def2.displayNum: defaultNum4Panel2),
frompanel : true,
isHidden : def2? def2.isHidden : false
}
},
_mergeRight : function(objs){
var arrRight = [];
for (var i=0,n=objs.length(); i<n; i++){
arrRight.push(objs.getAt(i).right);
}
return wcm.AuthServer.mergeRights(arrRight);
},
render : function(event){
if(!this.enable)return;
if(this._prepare(event)===false)return;
var type = event.type;
var arrOpers = wcm.SysOpers.getOpersByInfo(this.panel1Info, this.event);
var values = this._getHtml(arrOpers, this.panel1Info);
Ext.get('pageoper_1_items').update(values[0]);
Ext.get('pageoper_1_more').update(values[1]);
if(arrOpers[0]==null || arrOpers[0].length==0){
Element.hide('pageoper_1');
}else{
Element.show('pageoper_1');
}
if(arrOpers[1]==null || arrOpers[1].length==0){
Element.hide('pageoper_1_more_btn');
}else{
Element.show('pageoper_1_more_btn');
}
arrOpers = wcm.SysOpers.getOpersByInfo(this.panel2Info, this.event)
values = this._getHtml(arrOpers, this.panel2Info);
Ext.get('pageoper_2_items').update(values[0]);
Ext.get('pageoper_2_more').update(values[1]);
if(this.panel2Info.isHidden || arrOpers[0]==null || arrOpers[0].length==0){
Element.hide('pageoper_2');
}else{
Element.show('pageoper_2');
}
if(arrOpers[1]==null || arrOpers[1].length==0){
Element.hide('pageoper_2_more_btn');
}else{
Element.show('pageoper_2_more_btn');
}
var def = this.defaults[this.panel1Info.type];
if(def){
Ext.get('pageoper_1_title').update(def.title);
$('pageoper_1_title').setAttribute("title", def.title);
}
def = this.defaults[this.panel2Info.type];
if(def){
Ext.get('pageoper_2_title').update(def.title);
$('pageoper_2_title').setAttribute("title", def.title);
}
var caller = this;
Ext.get('pageoper_3_content').update('');
Element.hide('pageoper_3_more_btn');
this._generateDetail(function(trans){
var html = '';
if(Ext.isString(trans)){
html = trans;
}else{
html = trans.responseText;
}
Ext.get('pageoper_3_content').update(html, true, function(){
this.last3mouseinfo = null;
this.last3clickinfo = null;
}.bind(caller));
var cmsobjs = this.panel3Info.objs;
var type = this.panel3Info.type;
var def = this.defaults[type];
if(!(def==null || def.detailMore==null || 
(!def.detailMore && cmsobjs.length()>1))){
Element.show('pageoper_3_more_btn');
}
}.bind(this));
},
getQuickKeyTip : function(keys){
var tip = this.getQuickKeyTipText(keys);
return '(<span class="quickkey_tip">' + tip + '</span>)';
},
getQuickKeyTipText : function(keys){
var tip = '';
if(Ext.isArray(keys)){
if(keys[0].equalsI('Delete')||keys[0].equalsI('ShiftDelete')){
tip = 'Del';
}else{
tip = keys[0].charAt(0);
}
}else{
tip = keys.charAt(0);
}
return tip;
},
_getOperHtml : function(oper, bInMore){
if(wcm.SysOpers.isSeparator(oper)){
return String.format(myTemplate.seperator, oper.type, oper.key);
}
var quickKeyTip = '';
var quickKeyTipText = '';
if(oper.quickKey){
quickKeyTipText = "("+this.getQuickKeyTipText(oper.quickKey)+")";
quickKeyTip = this.getQuickKeyTip(oper.quickKey);
}
return String.format(myTemplate.operitem, oper.type, oper.key,
oper.desc, "title='"+(oper.title||oper.desc)+quickKeyTipText+"'",
(bInMore?'oper_item_more':''),
quickKeyTip
);
},
_getHtml : function(arrOpers, info){
var value1 = [], value2 = [];
for(var i=0,n=arrOpers[0].length;i<n;i++){
value1.push(this._getOperHtml(arrOpers[0][i]));
}
for(var i=0,n=arrOpers[1].length;i<n;i++){
value2.push(this._getOperHtml(arrOpers[1][i], true));
}
return [value1.join('\n'), value2.join('\n')];
},
_generateDetail : function(callBack){
var cmsobjs = this.panel3Info.objs;
var type = this.panel3Info.type;
var def = this.defaults[type];
if(def==null || def.detail===false){
Ext.fly('pageoper_3').hide();
return;
}
var fn = def.detail;
fn = typeof fn=="function" ? fn : this._detail;
Ext.get('pageoper_3_title').update(def.detailTitle || m_sDetailTitle);
$('pageoper_3_title').setAttribute("title",def.detailTitle || m_sDetailTitle);
Ext.fly('pageoper_3').show();
var html = fn.apply(this, [cmsobjs, {
def : def,
event : this.event
}, callBack]);
if(Ext.isString(html)){
callBack(html);
}
},
_detail : function(cmsobjs, opt, callBack){
if(cmsobjs.length()>1){
return String.format("当前共选中<span class=\"num\">{0}</span>{1}. ", 
(WCMLANG.LOCALE == 'en' ? ' ' : '') + cmsobjs.length() + (WCMLANG.LOCALE == 'en' ? ' ' : ''), 
PageContext.PageNav.UnitName + (WCMLANG.LOCALE == 'en' ? PageContext.PageNav.TypeName.toLowerCase() : PageContext.PageNav.TypeName) + (WCMLANG.LOCALE == 'en' ? 's' : ''));
}
def = opt.def;
if(!def.url){
Ext.Msg.d$alert('url未设置:\n'+Ext.toSource(def));
return '';
}
var extraParams = "";
if(def.params){
extraParams = "&" + def.params.call(def, opt);
}
var url = def.url.startsWith('?')?(WCMConstants.WCM_ROMOTE_URL+def.url) : def.url;
new Ajax.Request(url, {
method : 'GET',
parameters : ['ObjectIds=', cmsobjs.getIds(), '&ObjectId=', cmsobjs.getIds(), extraParams].join(''),
onSuccess : callBack
});
},
showMorePanel : function(event, target){
var flyTarget = Ext.fly(target);
var sPanelId = target.id.substring(0, target.id.length-'_more_btn'.length);
if(sPanelId=='pageoper_3'){
return this.onDetailMore(target);
}
var point = event.getPoint();
var x = point.x + 4;
var y = point.y + 4;
var d = Element.getDimensions(sPanelId + '_more');
if(d.height + y > document.body.offsetHeight) y = Math.max(y - d.height, 0);
var oBubbler = new wcm.BubblePanel(sPanelId + '_more');
oBubbler.bubble([x,y], null, function(_Point){
this.style.right = '20px';
this.style.top = _Point[1] + 'px';
});
},
onDetailMore : function(target){
var fn = def.detailMore;
fn = typeof fn=="function" ? fn : this._detailMore;
Ext.fly(target).show();
fn.apply(this, [this.event, wcm.SysOpers.getOpersByType(type), def]);
},
_detailMore : function(event, opers){
var oper = opers['edit'] || opers['addedit'];
if(!oper || oper.fn)return;
(oper.fn)(event);
},
togglePanel : function(target){
var flyTarget = Ext.fly(target);
var sPanelId = target.id.substring(0, target.id.length-'_toggle'.length);
if(flyTarget.hasClass('pageoper_collapse')){
var height = target._offsetHeight = target.offsetHeight;
Ext.get(sPanelId + '_content').hide();
flyTarget.replaceClass('pageoper_collapse', 'pageoper_expand');
}
else if(flyTarget.hasClass('pageoper_expand')){
var height = target._offsetHeight || target.offsetHeight;
Ext.get(sPanelId + '_content').show();
flyTarget.replaceClass('pageoper_expand', 'pageoper_collapse');
}
}
});
})();

Ext.ns('PageContext');
Ext.applyIf(PageContext, {
PageNav : {
UnitName : wcm.LANG.PAGENAV1 || '条',
TypeName : wcm.LANG.PAGENAV2 || '记录'
}
});
Ext.apply(PageContext.PageNav, {
go : function(_iPage, _maxPage){
delete PageContext.params["SELECTIDS"];
PageContext.loadList({
CurrPage : (_iPage<1)? 1 : ((_iPage > _maxPage) ? _maxPage : _iPage)
}, null, true);
},
applyKeyProvider : function(info){
if(!PageContext.keyProvider)return;
Ext.apply(PageContext.keyProvider, {
ctrlPgUp : function(event){
PageContext.PageNav.go(info.CurrPageIndex-1, info.PageCount);
},
ctrlPgDn : function(event){
PageContext.PageNav.go(info.CurrPageIndex+1, info.PageCount);
},
ctrlEnd : function(event){
PageContext.PageNav.go(info.PageCount, info.PageCount);
},
ctrlHome : function(event){
PageContext.PageNav.go(1, info.PageCount);
}
});
},
EffectMe : function(event, _oElement){
event = event || window.event;
switch(event.type){
case 'blur':
var newNo = parseInt(_oElement.value, 10);
_oElement.lastNo = _oElement.lastNo || "";
if(isNaN(newNo)){
_oElement.value = _oElement.lastNo;
}
else{
_oElement.value = newNo;
}
if(_oElement.lastNo!=_oElement.value){
PageContext.PageNav.go(parseInt(_oElement.value, 10), PageContext.PageNav["PageCount"]);
}
break;
case 'keydown':
if(event.keyCode==13){
_oElement.blur();
return;
}
break;
}
}
});
PageContext.getPageNavHtml = function(iCurrPage, iPages, info){
var sHtml = '';
if(iCurrPage!=1){
sHtml += '<span class="nav_page" title="' + (wcm.LANG.PAGENAV4 || "首页") + '" onclick="PageContext.PageNav.go(1, ' + iPages + ');">1</span>';
}else{
sHtml += '<span class="nav_page nav_currpage">1</span>';
}
var i,j;
if(iPages-iCurrPage<=1){
i = iPages-3;
}
else if(iCurrPage<=2){
i = 2;
}
else{
i = iCurrPage-1;
}
var sCenterHtml = '';
var nFirstIndex = 0;
var nLastIndex = 0;
for(j=0;j<3&&i<iPages;i++){
if(i<=1)continue;
j++;
if(j==1)nFirstIndex = i;
if(j==3)nLastIndex = i;
if(iCurrPage!=i){
sCenterHtml += '<span class="nav_page" onclick="PageContext.PageNav.go('+i+','+iPages+');">'+i+'</span>';
}else{
sCenterHtml += '<span class="nav_page nav_currpage">'+i+'</span>';
}
}
if(nFirstIndex!=0&&nFirstIndex!=2){
sHtml += '<span class="nav_morepage" title="' + (wcm.LANG.PAGENAV5 || "更多") + '">...</span>';
}
sHtml += sCenterHtml;
if(nLastIndex!=0&&nLastIndex!=iPages-1){
sHtml += '<span class="nav_morepage" title="' + (wcm.LANG.PAGENAV5 || "更多") + '">...</span>';
}
if(iCurrPage!=iPages){
sHtml += '<span class="nav_page" title="' + (wcm.LANG.PAGENAV6 || "尾页") + '" onclick="PageContext.PageNav.go(' + iPages + ',' + iPages + ');">'+iPages+'</span>';
}else{
sHtml += '<span class="nav_page nav_currpage" title="' + (wcm.LANG.PAGENAV6 || "尾页") + '">'+iPages+'</span>';
}
return sHtml;
}
PageContext.getNavigatorHtml = function(info){
var iRecordNum = info.Num;
if(iRecordNum==0)return '';
var iCurrPage = info.CurrPageIndex;
var iPageSize = info.PageSize;
var iPages = info.PageCount;
var aHtml = [
'<span class="nav_page_detail">',
String.format("共<span class=\"nav_pagenum\">{0}</span>页",iPages),
',',
'<span class="nav_recordnum">', iRecordNum, '</span>',
PageContext.PageNav["UnitName"], 
(WCMLANG.LOCALE == 'en' ? PageContext.PageNav["TypeName"].toLowerCase() : PageContext.PageNav["TypeName"]),
(WCMLANG.LOCALE == 'en' ? '(s)' : ''),
',',
String.format("<span class=\"nav_pagesize\">{0}</span>{1}/页", iPageSize, PageContext.PageNav["UnitName"])
];
if(iPages > 1){
aHtml.push(
',',
String.format("跳转到第{0}页",'<input type="text" name="nav-go" id="nav-go" value="" onkeydown="PageContext.PageNav.EffectMe(arguments[0], this);" onblur="PageContext.PageNav.EffectMe(arguments[0], this);"/>')
);
}
aHtml.push('.</span>');
var sHtml = aHtml.join("");
if(iPages>1){
sHtml += PageContext.getPageNavHtml(iCurrPage, iPages, info);
}
return sHtml;
}
PageContext.destroyPageNavHtml = function(){
var dom = $('nav-go');
if(dom){
dom.onkeydown = null;
dom.onblur = null;
}
var dom = $('list_navigator');
if(dom){
var nodes = dom.getElementsByTagName('span');
for (var i = 0; i < nodes.length; i++){
if(nodes[i].onclick) nodes[i].onclick = null;
}
}
}
PageContext.drawNavigator = function(info){
Ext.apply(PageContext.PageNav, info);
var eNavigator = $('list_navigator');
if(!eNavigator)return;
PageContext.destroyPageNavHtml();
var sHtml = PageContext.getNavigatorHtml(info);
Element.update(eNavigator, sHtml);
PageContext.PageNav.applyKeyProvider(info);
}

Ext.ns('wcm.PageTab');
(function(){
var myTemplate = {
outer : [
'<table cellspacing=0 cellpadding=0 border=0 id="pagetab" class="pagetab">',
'<tbody>',
'<tr valign=middle align=center id="pagetab_container">',
'{0}',
'<td class="tab_item_more {1}" id="pagetab_more">&nbsp;&nbsp;</td>',
'</tr>',
'</tbody>',
'</table>'
].join(''),
inner : [
'<td class="tab_item {0}" id="tab_{1}" _type="{1}" _url={2}><span title="{3}">{3}</span></td>'
].join('')
};
function Tab(tab, tabs){
Ext.apply(this, tab);
this.tabs = tabs;
this.setOrder = function(order){
tab.order = order;
items = this.tabs.items;
var currItem = null;
for (var i=0,n=items.length; i<n; i++){
if(items[i].type.equalsI(this.type)){
currItem = items[i];
items[i] = null;
break;
}
}
if(currItem == null)return this;
items = this.tabs.items = items.compact();
items.splice(order-1, 0, currItem);
return this;
}
this.hide = function(fn){
if(!Ext.isFunction(fn)){
fn = function(){
return false;
}
}
if(!Ext.isFunction(tab.isVisible)){
this.isVisible = tab.isVisible = fn.bind(this);
}
this.isVisible = tab.isVisible = tab.isVisible.createInterceptor(fn, this);
}
}
function Tabs(tabs){
Ext.apply(this, tabs);
this.register = this.addTab = function(infos){
var tabs = this;
if(Ext.isArray(infos)){
infos.each(function(info){
tabs.addTab(info);
});
return tabs;
}
if(tabs.hostType.toLowerCase()!=infos.hostType.toLowerCase())
return tabs;
var items = infos.items || {};
if(Ext.isArray(items)){
items.each(function(item, index){
item = Ext.applyIf(item, infos);
item.order = item.order || (tabs.items.length + index + 1);
delete item.items;
tabs.items.splice(item.order-1, 0, item);
});
}else{
items = Ext.applyIf(items, infos);
items.order = items.order || (tabs.items.length+1);
delete items.items;
tabs.items.splice(items.order-1, 0, items);
}
tabs.items = tabs.items.compact();
return tabs;
}
this.get = this.getTab = function(type){
var items = this.items;
for (var i=0,n=items.length; i<n; i++){
if(items[i].type.equalsI(type))
return new Tab(items[i], this);
}
return new Tab({}, this);
}
}
Ext.apply(wcm.PageTab, {
tabs : {},
init : function(info){
if(!info.enable){
wcm.Layout.collapseByChild('south', 'south_tabs');
return;
}
info.displayNum = this._tabDisplayNums[info.hostType];
this.info = info;
var extInfo = Ext.apply({}, info);
extInfo.displayNum = wcm.TabManager.calDisplayNum(this._tabDisplayNums[info.hostType]);
extInfo.tabs = (extInfo.tabs)?extInfo.tabs.compact():[];
var sValue = this.getTabsHtml(extInfo);
Element.update($('south_tabs'), sValue);
this.disable(getParameter("disableTab"));
this.bindEvents(extInfo);
},
disable : function(disabled){
var sMethod = disabled ? 'addClassName' : 'removeClassName';
Element[sMethod]('pagetab', 'disabled');
},
findTabItem : function(srcElement, parent){
while(srcElement!=null && srcElement!=$(parent || 'pagetab_container')){
if(Element.hasClassName(srcElement, 'tab_item'))return srcElement;
srcElement = srcElement.parentNode;
}
return null;
},
findTabItemMore : function(srcElement, parent){
while(srcElement!=null && srcElement!=$(parent || 'pagetab_container')){
if(Element.hasClassName(srcElement, 'tab_item_more'))return srcElement;
srcElement = srcElement.parentNode;
}
return null;
},
bindEvents : function(info){
var inited = !!this.extInfo;
this.extInfo = info;
if(inited) return;
var tabCnt = Ext.get('south_tabs');
tabCnt.on('click', function(event, origTarget){
if(Element.hasClassName('pagetab', 'disabled')) return;
var target = this.findTabItem(origTarget);
if(target){
if(Ext.fly(target).hasClass('tab_item5_disabled') 
|| Ext.fly(target).hasClass('tab_item4_disabled')
|| Ext.fly(target).hasClass('tab_item_disabled'))return;
var tabType = target.getAttribute("_type");
var tabItem = this.extInfo.getTab(tabType);
wcm.TabManager.exec(tabItem);
return;
}
var target = this.findTabItemMore(origTarget);
if(target){
this.moreAction(target, event);
}
}, this);
},
moreAction : function(target, event){
wcm.TabManager.rememberShowAll(!Element.hasClassName(target, 'tab_item_more_open'));
var extTarget = Ext.fly(target);
var bIsOpen = extTarget.hasClass('tab_item_more_open');
var extendInfo = bIsOpen ? {} : {displayNum : wcm.TabManager.maxDisplayNum};
var sValue = this.getTabsHtml(Ext.applyIf(extendInfo, this.info));
extTarget.removeClass(bIsOpen?'tab_item_more_open':'tab_item_more_close');
extTarget.addClass(!bIsOpen?'tab_item_more_open':'tab_item_more_close');
Element.update($('south_tabs'), sValue);
},
getTabs : function(info){
var tabs = wcm.TabManager.getTabs(info.hostType, true, PageContext.getContext0());
return new Tabs(Ext.apply(info, {
items : tabs.items
}));
},
getTabsHtml : function(info){
return String.format(myTemplate.outer, this._getInnerHtml(info),
this._getMoreClass(info.displayNum, info.num));
},
_getInnerHtml : function(info){
var result = [];
var caller = this;
var context = PageContext.getContext();
var oCounter = {num : 0};
info.items.each(function(tab, index){
var tabDesc = Ext.kaku(tab.desc, null, context) || tab.type;
var url = wcm.TabManager.getTabUrl(tab);
result.push(String.format(myTemplate.inner,
caller._getTabClass(tab, info, context, oCounter),
tab.type,
url,
tabDesc));
});
info.num = oCounter.num;
return result.length>0 ? result.join('') : '';
},
_getMoreClass : function(displayNum, num){
if((displayNum > num && !wcm.TabManager.showAll()) || this.info.displayNum > num) return 'more_display_none';
if(displayNum>=wcm.TabManager.maxDisplayNum)return 'tab_item_more_open';
return 'tab_item_more_close';
},
_getTabClass : function(tab, info, context, oCounter){
var rightIndex = tab.rightIndex;
var currTabType = info.activeTabType;
var len = Ext.kaku(tab.desc, null, context).length;
var identify = len < 4 ? "" : (len >= 5 ? "5" : "4");
var extraTabCls = tab.extraCls;
if(tab.type.equalsI(currTabType)){
oCounter.num++;
return 'tab_item' + identify + '_active' + (extraTabCls ? " " + extraTabCls + '_active' : "");
}
if(oCounter.num>=info.displayNum){
oCounter.num++;
return 'display_none';
}
if(Ext.isFunction(tab.isVisible) && !tab.isVisible(context)){
return 'display_none';
}
if(!wcm.AuthServer.checkRight(wcm.AuthServer.getRightValue(), rightIndex)){
return 'tab_item' + identify + '_disabled' + (extraTabCls ? " " + extraTabCls + '_disabled' : "");
}
oCounter.num++;
return 'tab_item' + identify + '_deactive' + (extraTabCls ? " " + extraTabCls + '_deactive' : "");
}
});
})();
Ext.apply(wcm.PageTab, {
_tabDisplayNums : (function(){
var result = {}, num = parent.m_CustomizeInfo ? parent.m_CustomizeInfo.sheetCount.paramValue : 7;
result[WCMConstants.TAB_HOST_TYPE_CHANNEL] = num;
result[WCMConstants.TAB_HOST_TYPE_WEBSITE] = num;
result[WCMConstants.TAB_HOST_TYPE_WEBSITEROOT] = num;
result[WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST] = num;
result[WCMConstants.TAB_HOST_TYPE_MYMSGLIST] = num;
result[WCMConstants.TAB_HOST_TYPE_CLASSINFO] = num;
return result;
})()
});
(function(){
var tabTemplate = [
'<div class="more-tab {0}" _type="{1}" _url="{2}"><a href="#" onclick="return false">{3}</a></div>',
'<table border="0" cellpadding="0" cellspacing="0">',
'<tr height="23">',
'<td class="left" width="7"></td>',
'<td class="middle" nowrap="nowrap" valign="middle">',
'<a href="#">{3}</a>',
'</td>',
'<td class="right" width="7"></td>',
'</tr>',
'</table>',
'</span>'
].join('');
var tabTemplate = [
'<div class="more-tab {0}" _type="{1}" _url="{2}">',
'<a href="#" onclick="return false;">{3}</a>',
'</div>'
].join('');
function findTabItem(dom){
while(dom && dom.tagName != 'BODY'){
if(dom.getAttribute('_type')) return dom;
dom = dom.parentNode;
}
return null;
}
Ext.apply(wcm.PageTab, {
initTabsMore : function(){
if(this.oBubbler) return;
var dom = document.createElement('div');
dom.id = 'more-tabs';
Element.addClassName(dom, "more-tabs");
document.body.appendChild(dom);
dom.innerHTML = this.getTabsMoreHtml();
this.initTabsMoreEvents(dom);
this.oBubbler = new wcm.BubblePanel(dom);
},
initTabsMoreEvents : function(dom){
Ext.get(dom).on('click', function(event, origTarget){
if(Element.hasClassName('pagetab', 'disabled')) return;
var target = findTabItem(origTarget);
if(!target) return;
var tabType = target.getAttribute("_type");
var tabItem = this.extInfo.getTab(tabType);
wcm.TabManager.exec(tabItem);
}, this);
},
getTabsMoreClass : function(tab, info, context, oCounter){
var rightIndex = tab.rightIndex;
var currTabType = info.activeTabType;
if(Ext.isFunction(tab.isVisible) && !tab.isVisible(context)){
return 'display_none';
}
if(!wcm.AuthServer.checkRight(wcm.AuthServer.getRightValue(), rightIndex)){
return 'tab_item_disabled';
}
oCounter.num++;
if(tab.type.equalsI(currTabType)){
return 'display_none';
}
return 'more-tab';
},
getTabsMoreHtml : function(){
var result = [];
var caller = this;
var context = PageContext.getContext();
var oCounter = {num : 0};
var info = this.info;
info.items.each(function(tab, index){
var cls = caller.getTabsMoreClass(tab, info, context, oCounter);
if(oCounter.num <= info.displayNum) return;
var tabDesc = Ext.kaku(tab.desc, null, context) || tab.type;
var url = wcm.TabManager.getTabUrl(tab);
result.push(String.format(tabTemplate, cls, tab.type, url, tabDesc));
});
return result.length>0 ? result.join('') : '';
},
moreAction : function(target, extEvent){
this.initTabsMore();
var dom = document.getElementById('more-tabs');
if(dom.innerHTML == '') return;
this.oBubbler.bubble(null, null, function(){
this.style.left = extEvent.getPageX() + "px";
this.style.bottom = '30px';
});
}
});
})();

Ext.ns('PageContext.literator');
PageContext.literator = {
enable : false,
litid : 'literator_path',
url : WCMConstants.WCM6_PATH + 'include/entity_path.jsp',
params : {
sitetypeid : PageContext.getParameter('SiteType'),
siteid : PageContext.getParameter('SiteId'),
channelid : PageContext.getParameter('ChannelId')
},
width : 350
};
Ext.apply(PageContext, {
drawLiterator : function(){
if(!PageContext.literator)return;
var config = PageContext.literator;
(config.doBefore || Ext.emptyFn)();
if(!config.enable)return;
var eLit = $(config.litid);
if(eLit == null)return;
Ext.get(eLit).on('click', function(event, target){
if(target.tagName=='A')return;
var config = this;
var eLit = $(config.litid);
if(!eLit.getAttribute('showmore'))return;
if(!Element.hasClassName(target, 'literator_more_btn'))return;
var oBubbler = new wcm.BubblePanel('literator_more');
var point = event.getPoint();
var x = point.x + 4;
var y = point.y + 4;
oBubbler.bubble([x,y], function(_Point){
return [_Point[0]-this.offsetWidth,_Point[1]];
});
}.bind(config));
var url = config.url;
if(url == null)return;
var params = $toQueryStr(config.params);
params += (params.length>0 ? '&' : '') + 'random=' + Math.random();
new Ajax.Request(url, {
method : 'get',
parameters : params,
onSuccess : function(_tran){
var config = this;
var litid = config.litid;
var lit = $(litid);
lit.innerHTML = _tran.responseText;
PageContext.createWardOfLiterator();
PageContext.bindEventsOfLiterator(litid);
}.bind(config)
});
},
createWardOfLiterator : function(_sLitId){
var config = PageContext.literator;
if(!config.enable)return;
var literator = $(config.litid);
if(!window.literatorInited){
literator.style.width = getWidthOfLiterator(config.litid) + 'px';
literator.style.display = 'inline-block';
literator.style.whiteSpace = "nowrap";
literator.style.overflow = "hidden";
literator.style.textOverflow = "ellipsis";
literator.style.margin = "0px 5px";
literator.scrollLeft = literator.offsetWidth;
var createWard = function(sInnerText, oParentNode, oNode){
var ward = document.createElement("span");
oParentNode.insertBefore(ward, oNode);
ward.style.visibility = 'hidden';
ward.style.color = "blue";
ward.innerText = sInnerText;
return ward;
};
var foreWard = createWard("<<", literator.parentNode, literator);
var backWard = createWard(">>", literator.parentNode, getNextHTMLSibling(literator));
}else{
var foreWard = getPreviousHTMLSibling(literator);
}
if(literator.scrollLeft > 0){
foreWard.style.visibility = 'visible';
}
},
bindEventsOfLiterator : function(_sLitId){
if(window.literatorInited){
return;
}
var config = PageContext.literator;
if(!config.enable)return;
var literator = $(config.litid);
var foreWard = getPreviousHTMLSibling(literator);
var backWard = getNextHTMLSibling(literator);
Event.observe(foreWard, 'mouseover', function(){
backWard.style.visibility = 'visible';
window.foreWardHandler = setInterval(function(){
var oldScrollLeft = literator.scrollLeft;
literator.scrollLeft = parseInt(literator.scrollLeft, 10) - 5;
if(literator.scrollLeft == oldScrollLeft){
foreWard.style.visibility = 'hidden';
}
},40);
});
Event.observe(foreWard, 'mouseout', function(){
clearInterval(window.foreWardHandler);
});
Event.observe(backWard, 'mouseover', function(){
foreWard.style.visibility = 'visible';
window.backWardHandler = setInterval(function(){
var oldScrollLeft = literator.scrollLeft;
literator.scrollLeft = parseInt(literator.scrollLeft, 10) + 5;
if(literator.scrollLeft == oldScrollLeft){
backWard.style.visibility = 'hidden';
}
},40);
});
Event.observe(backWard, 'mouseout', function(){
clearInterval(window.backWardHandler);
});
Event.observe(window, 'resize', function(_sLitId){
literator.style.width = getWidthOfLiterator(_sLitId);
literator.scrollLeft = literator.offsetWidth;
foreWard.style.visibility = 'hidden';
backWard.style.visibility = 'hidden';
if(literator.scrollLeft > 0){
foreWard.style.visibility = 'visible';
}
}.bind(window, _sLitId));
window.literatorInited = true;
},
traceLiterator : function(siteOrChannelId, siteOrChannel, right){
var context = {
objId : siteOrChannelId,
objType : arguments.length <= 1 ? 
WCMConstants.OBJ_TYPE_WEBSITEROOT :
(siteOrChannel ? WCMConstants.OBJ_TYPE_WEBSITE : WCMConstants.OBJ_TYPE_CHANNEL)
};
context.tabType = PageContext.getActiveTabType(context);
$MsgCenter.$main(context).redirect();
}
});
function getWidthOfLiterator(_sLitId){
var literator = $(_sLitId);
var bodyWidth = parseInt(document.body.offsetWidth, 10);
var sQueryBox = $('list-order-box') ? 'list-order-box' : "query_box";
var queryBoxWidth = 300;
if($(sQueryBox)){
var width = parseInt($(sQueryBox).offsetWidth, 10);
if(width > queryBoxWidth){
queryBoxWidth = width;
}
}
var previousSibling = getPreviousHTMLSibling(literator);
var previousWidth = 0;
if(previousSibling && previousSibling.id != sQueryBox){
previousWidth = previousSibling.offsetWidth;
}
var width = bodyWidth - queryBoxWidth - previousWidth - 250;
if($MsgCenter.getActualTop().m_bClassicList || location.href.indexOf('_classic_') > 0)
width = bodyWidth - previousWidth - 250;
return width > 0 ? width : 30;
}
function getPreviousHTMLSibling(domNode){
if(domNode == null) return null;
var tempNode = domNode.previousSibling;
while(tempNode && tempNode.nodeType != 1){
tempNode = tempNode.previousSibling;
}
return tempNode;
}
function getNextHTMLSibling(domNode){
if(domNode == null) return null;
var tempNode = domNode.nextSibling;
while(tempNode && tempNode.nodeType != 1){
tempNode = tempNode.nextSibling;
}
return ((tempNode == null)||(tempNode.parentNode != domNode.parentNode)) ? null : tempNode;
}

PageContext.keyProvider = {};
PageContext.prepareKeyProvider = function(){
if(!wcm.SysOpers)return PageContext.keyProvider;
var registerKeys = wcm.SysOpers.getQuickKeys();
var execOper = function(event){
if(event.keyCode >= 97 && event.keyCode <= 122){
return;
}
var wcmEvent = PageContext.event;
var c = String.fromCharCode(event.keyCode);
if(event.keyCode==Event.KEY_DELETE && event.shiftKey){
c = 'Shiftdelete';
}
else if(event.keyCode==Event.KEY_DELETE){
c = 'Delete';
}
try{
var oper = wcm.SysOpers.getOperByQuickKey(wcmEvent, c)
|| wcm.SysOpers.getOperByQuickKey(wcmEvent, c, {
right : wcmEvent.getHost().right,
type : PageContext.relateType || wcmEvent.getContext().relateType
});
}catch(error){
}
if(!oper)return;
wcmEvent.browserEvent = event;
wcm.SysOpers.exec(oper, wcmEvent);
wcmEvent.browserEvent = null;
}
for(var key in registerKeys){
PageContext.keyProvider['key'+key.camelize()] = execOper;
}
return PageContext.keyProvider;
};

PageContext.filterEnable = true;
PageContext.setFilters = function(filters, info){
this.pageFilters = new wcm.PageFilters(Ext.apply({
displayNum : 6,
filterType : PageContext.getParameter('FilterType') || 0
}, info));
this.pageFilters.register(filters);
};
PageContext.operEnable = true;
PageContext.setRelateType = function(sRelateType){
this.relateType = sRelateType;
};
PageContext.addGridFunctions = function(actions){
for(var actionName in actions){
wcm.Grid.addFunction(actionName, actions[actionName]);
}
};
Ext.apply(wcm.Grid, {
rowType : function(){
return PageContext.objectType;
},
initRowInfo : function(){
var excludeAttrNames = /^(?:id|height|title|length|rowid|language|datafld|class|lang|hidefocus|dir|contenteditable|dataformatas|disabled|accesskey|tabindex|implementation|[v]?align|border.*|choff|bgcolor|ch|on.+)$/i;
return function(dom){
var attrs = dom.attributes;
var result = {};
for (var i = 0, length = attrs.length; i < length; i++){
var attr = attrs[i];
if(!attr.specified || excludeAttrNames.test(attr.nodeName)) continue;
result[attr.nodeName] = true;
}
return result;
}
}(),
defRowInfo : function(){
if(this.rowInfo) return this.rowInfo;
var rows = $('grid_body').rows;
for(var i=0, n=rows.length; i<n; i++){
if(Element.hasClassName(rows[i], 'grid_row')) break;
}
if(i >= n) return {};
this.rowInfo = this.initRowInfo(rows[i]);
return this.rowInfo;
}
});
PageContext.tabEnable = true;
PageContext.setTabs = function(tabs){
this.pageTabs = wcm.PageTab.getTabs(Ext.applyIf(tabs, {
hostType : PageContext.tabHostType
}));
}
PageContext.gridDraggable = false;
PageContext.initDraggable = function(){
};
Event.observe(window, 'load', function(){
if(PageContext.gridDraggable){
PageContext.initDraggable();
}
});

var ValidatorLang =
{
LOCALE : 'cn',
TEXT1 : '时间的time_format格式不对',
TEXT2 : '没有指定field域',
TEXT3 : '没有指定sub_type域',
TEXT4 : '域指定不合法',
TEXT5 : '不应包含\\/:*?\"<>|等字符',
TEXT6 : '普通字符，\\/:*?\"<>|等字符除外',
TEXT7 : "没有匹配日期格式:",
TEXT8 : "小时应该是0到23的整数",
TEXT9 : "分应该是0到59的整数",
TEXT10 : "秒应该是0到59的整数",
TEXT11 : "指定的",
TEXT12 : "不是分割的两个数字！",
TEXT13 : "不是个数字！",
TEXT14 : "月份应该为1到12的整数",
TEXT15 : "每个月的天数应该为1到31的整数",
TEXT16 : "该月不存在31号",
TEXT17 : "2月最多有29天",
TEXT18 : "闰年2月才有29天",
TEXT19 : "小时应该是0到23的整数",
TEXT20 : "分应该是0到59的整数",
TEXT21 : "秒应该是0到59的整数",
TEXT22 : "日期格式错误：",
TEXT23 : '{0}不能为空.',
TEXT24 : '{0}必须和{1}一致.',
TEXT25 : '{0}必须为数字.',
TEXT26 : '{0}必须为整数.',
TEXT27 : '{0}必须为小数.',
TEXT28 : '{0}必须为双精度.',
TEXT29 : '{0}最小长度为{1}.',
TEXT30 : '{0}最大长度为{1}.',
TEXT31 : '{0}最小值为 {1}.',
TEXT32 : '{0}最大值为{1}.',
TEXT33 : '{0}长度范围为{1}~{2}.',
TEXT34 : '{0}值范围为{1}~{2}.',
TEXT35 : '{0}期望格式为:http(s)://[站点](:[端口])/(子目录).',
TEXT36 : '{0}期望格式为:xxx.xxx.xxx.xxx..',
TEXT37 : '{0}符合IPV4格式，期望格式为: 192.9.200.114.',
TEXT38 : '{0}由字母,数字,下划线组成.',
TEXT39 : '{0}由字母开始的字母,数字,下划线组成.',
TEXT40 : '必须选择一个{0}',
TEXT41 : '{0}格式必须为\"hh:mm\"，如：09:03',
TEXT42 : '{0}格式必须为\"h:m\"，如：9:3' ,
TEXT43 : '{0}格式不合法.',
TEXT44 : '{0}小于最小长度{1}.',
TEXT45 : '{0}大于最大长度{1}.',
TEXT46 : '{0}小于最小值({1}).',
TEXT47 : '{0}大于最大值({1}).',
TEXT48 : '{0}长度超出范围{1}~{2}.',
TEXT49 : '{0}值超出范围{1}~{2}.',
TEXT50 : '{0}正确格式为：http(s)://[站点](:[端口])/(子目录).',
TEXT51 : '{0}不合法,正确格式为:xxx.xxx.xxx.xxx..',
TEXT52 : '{0}不符合IPV4格式！正确格式为: 192.9.200.114.',
TEXT53 : '{0}IPV4最高位不能为0！正确格式为: 192.9.200.114.',
TEXT54 : '{0}IPV4任一位数值不能超过255！正确格式为: 192.9.200.114.',
TEXT55 : '{0}不是由字母、数字、下划线组成.',
TEXT56 : '{0}不是由字母开始的字母,数字,下划线组成.',
TEXT57 : '小时必须在0~23之间',
TEXT58 : '分钟必须在0~59之间',
TEXT59 : '秒必须在0~59之间',
TEXT60 : '{0}由中文或字母开头的字符串组成.',
TEXT61 : '{0}不是由中文或字母开头的字符串组成.'
}
Ext.ns('com.trs.validator', 'ValidatorLang');
var $ValidatorConfigs = com.trs.validator.ValidatorConfigs = {
PREFIX_HINT_SPAN_ID : 'com_trs_wcm_ajax_hint_node_',
PREFIX_WARNING_SPAN_ID : 'com_trs_wcm_ajax_warning_node_',
PREFIX_MESSAGE_SPAN_ID : 'com_trs_wcm_ajax_message_node_',
WARNING_LOG_PATH : WCMConstants.WCM6_PATH+'com.trs.validator/images/error.gif',
MESSAGE_LOG_PATH : WCMConstants.WCM6_PATH+'com.trs.validator/images/info.gif',
WARNING_CLASSNAME_KEY : "warningClassKey",
MESSAGE_CLASSNAME_KEY : "messageClassKey",
WARNING_CLASSNAME_MOUSE : "warningClassMouse",
MESSAGE_CLASSNAME_MOUSE : "messageClassMouse",
REQUIREDCLASS : "requiredClass",
UserLanguage : 'auto',
DATE_FORMAT_DEFAULT : "yyyy-mm-dd",
TIME_FORMAT_DEFAULT : "hh:MM:ss",
MOUSE_MODE : false,
SHOW_ALL_MODE : false,
FOCUS_MODE : true,
REQUIRED_HINT : true,
DRAFT_MODE : false,
WARNING_BORDER : 'red 1px solid',
DESC : "desc" .toLowerCase(),
TYPE : "type" .toLowerCase(),
REQUIRED : "required" .toLowerCase(),
EQUALS_WITHS : "equals_with" .toLowerCase(),
MIN_LEN : "min_len" .toLowerCase(),
MAX_LEN : "max_len" .toLowerCase(),
LENGTH_RANGE : "length_range" .toLowerCase(),
MIN : "min" .toLowerCase(),
MAX : "max" .toLowerCase(), 
VALUE_RANGE : "value_range" .toLowerCase(),
DATE_FORMAT : "date_format" .toLowerCase(),
FORMAT : "format" .toLowerCase(),
MESSAGE : "message" .toLowerCase(),
WARNING : "warning" .toLowerCase(), 
METHODS : "methods" .toLowerCase(),
SHOWID : "showid" .toLowerCase(),
COMMON_CHAR : "common_char" .toLowerCase(),
COMMON_CHAR2 : "common_char2" .toLowerCase(),
NO_CLASS : "no_class" .toLowerCase(),
BLUR_SHOW : 'blur_show' .toLowerCase(),
RPC : "rpc" .toLowerCase(),
CLOSE : "close" .toLowerCase(),
TIME_FORMAT : "time_format" .toLowerCase(),
EXCLUDE : "exclude" .toLowerCase(),
SUBTYPE : "sub_type" .toLowerCase(),
RELATION : "relation" .toLowerCase(),
FIELD : "field" .toLowerCase(),
NO_DESC : "no_desc" .toLowerCase(),
REQUIRE_CONTAINER : "require_container".toLowerCase(),
NO_REQUIRE_HINT : "no_require_hint" .toLowerCase(),
MESSAGE_INFO : {
"zh-cn" : {
REQUIRED : ValidatorLang.TEXT23 || '{0}不能为空.',
EQUALS_WITHS : ValidatorLang.TEXT24 || '{0}必须和{1}一致.',
NUMBER : ValidatorLang.TEXT25 || '{0}必须为数字.',
INT : ValidatorLang.TEXT26 || '{0}必须为整数.',
FLOAT : ValidatorLang.TEXT27 || '{0}必须为小数.',
DOUBLE : ValidatorLang.TEXT28 || '{0}必须为双精度.',
MIN_LEN : ValidatorLang.TEXT29 || '{0}最小长度为{1}.',
MAX_LEN : ValidatorLang.TEXT30 || '{0}最大长度为{1}.',
MIN : ValidatorLang.TEXT31 || '{0}最小值为{1}.',
MAX : ValidatorLang.TEXT32 || '{0}最大值为{1}.',
LENGTH_RANGE : ValidatorLang.TEXT33 || '{0}长度范围为{1}~{2}.',
VALUE_RANGE : ValidatorLang.TEXT34 || '{0}值范围为{1}~{2}.',
URL : ValidatorLang.TEXT35 || '{0}期望格式为:http(s)://[站点](:[端口])/(子目录).',
LINK : ValidatorLang.TEXT36 || '{0}期望格式为:xxx.xxx.xxx.',
IPV4 : ValidatorLang.TEXT37 || '{0}符合IPV4格式，期望格式为: 192.9.200.114.',
COMMON_CHAR : ValidatorLang.TEXT38 || '{0}由字母,数字,下划线组成.',
COMMON_CHAR2 : ValidatorLang.TEXT39 || '{0}由字母开始的字母,数字,下划线组成.',
SELECT : ValidatorLang.TEXT40 || '必须选择一个{0}',
TIME_ONE : ValidatorLang.TEXT41 || '{0}格式必须为"hh:mm"，如：09:03',
TIME_TWO : ValidatorLang.TEXT42 || '{0}格式必须为"h:m"，如：9:3',
STRING2 : ValidatorLang.TEXT60 || '{0}由中文或字母开头的字符串组成.'
}
},
WARNING_INFO : {
"zh-cn" : {
DEFAULT : ValidatorLang.TEXT43 || '{0}格式不合法.',
REQUIRED : ValidatorLang.TEXT23 || '{0}不能为空.',
EQUALS_WITHS : ValidatorLang.TEXT24 || '{0}必须和{1}一致.',
NUMBER : ValidatorLang.TEXT25 || '{0}必须为数字.',
INT : ValidatorLang.TEXT26 || '{0}必须为整数.',
FLOAT : ValidatorLang.TEXT27 || '{0}必须为小数.',
DOUBLE : ValidatorLang.TEXT28 || '{0}必须为双精度.', 
MIN_LEN : ValidatorLang.TEXT44 || '{0}小于最小长度{1}.',
MAX_LEN : ValidatorLang.TEXT45 || '{0}大于最大长度{1}.',
MIN : ValidatorLang.TEXT46 || '{0}小于最小值({1}).',
MAX : ValidatorLang.TEXT47 || '{0}大于最大值({1}).',
LENGTH_RANGE : ValidatorLang.TEXT48 || '{0}长度不在范围{1}~{2}.',
VALUE_RANGE : ValidatorLang.TEXT49 || '{0}值不在范围{1}~{2}.',
URL : ValidatorLang.TEXT50 || '{0}正确格式为：http(s)://[站点](:[端口])/(子目录).',
LINK : ValidatorLang.TEXT51 || '{0}不合法,正确格式为:xxx.xxx.xxx.',
IPV4 : ValidatorLang.TEXT52 || '{0}不符合IPV4格式！正确格式为: 192.9.200.114.',
IPV4_HIGH_EQUAL_0 : ValidatorLang.TEXT53 || '{0}IPV4最高位不能为0！正确格式为: 192.9.200.114.',
IPV4_BEYOND_255 : ValidatorLang.TEXT54 || '{0}IPV4任一位数值不能超过255！正确格式为: 192.9.200.114.',
COMMON_CHAR : ValidatorLang.TEXT55 || '{0}不是由字母、数字、下划线组成.',
COMMON_CHAR2 : ValidatorLang.TEXT56 || '{0}不是由字母开始的字母,数字,下划线组成.',
SELECT : ValidatorLang.TEXT40 || '必须选择一个{0}',
TIME : ValidatorLang.TEXT41 || '{0}格式必须为"hh:mm"，如：09:03',
HOUR : ValidatorLang.TEXT57 || '小时必须在0~23之间',
MINUTE : ValidatorLang.TEXT58 || '分钟必须在0~59之间',
SECOND : ValidatorLang.TEXT59 || '秒必须在0~59之间',
STRING2 : ValidatorLang.TEXT61 || '{0}不是由中文或字母开头的字符串组成.'
}
},
setMouseMode : function(bool){
$ValidatorConfigs.MOUSE_MODE = bool;
},
getMouseMode : function(){
return $ValidatorConfigs.MOUSE_MODE;
},
setShowAllMode : function(bool){
$ValidatorConfigs.SHOW_ALL_MODE = bool;
},
getShowAllMode : function(){
return $ValidatorConfigs.SHOW_ALL_MODE;
},
setFocusMode : function(bool){
$ValidatorConfigs.FOCUS_MODE = bool;
},
getFocusMode : function(){
return $ValidatorConfigs.FOCUS_MODE;
},
setRequiredHint : function(bool){
$ValidatorConfigs.REQUIRED_HINT = bool;
},
getRequiredHint : function(){
return $ValidatorConfigs.REQUIRED_HINT;
},
setDraftMode : function(bool){
$ValidatorConfigs.DRAFT_MODE = bool;
},
getDraftMode : function(){
return $ValidatorConfigs.DRAFT_MODE;
}
};
if(!window.Class){
var Class = {};
}
Ext.apply(Class, {
create: function() {
return function() {
this.initialize.apply(this, arguments);
}
}
});
Ext.applyIf(Array.prototype, {
last : function(){
return this[this.length - 1];
}
});
Ext.applyIf(Event, {
pointerX: function(event) {
return event.pageX || (event.clientX +
(document.documentElement.scrollLeft || document.body.scrollLeft));
},
pointerY: function(event) {
return event.pageY || (event.clientY +
(document.documentElement.scrollTop || document.body.scrollTop));
}
});
var AttributeHelper = {
prefix : 'attr_',
autoId : 0,
cache : {},
setAttribute : function(element, sAttrName, sAttrValue){
element = $(element);
if(!element) return false;
var _id = this.getId(element);
var cache = this.cache;
var attributes = cache[_id];
if(!attributes){
attributes = cache[_id] = {};
}
attributes[sAttrName.toUpperCase()] = sAttrValue;
},
getAttribute : function(element, sAttrName){
element = $(element);
if(!element) return null;
var _id = this.getId(element);
var cache = this.cache;
var attributes = cache[_id];
if(!attributes){
return null;
}
return attributes[sAttrName.toUpperCase()];
},
removeAttribute : function(element, sAttrName){
element = $(element);
if(!element) return;
var _id = this.getId(element);
var cache = this.cache;
var attributes = cache[_id];
if(!attributes){
return;
}
delete attributes[sAttrName.toUpperCase()];
},
getId : function(element){
var _id = element.getAttribute("_id");
if(_id != undefined) return _id;
_id = element.name || element.id || (this.prefix + (++this.autoId));
_id = _id.toUpperCase();
element.setAttribute("_id", _id);
return _id;
}
};
AbstractValidator = Class.create();
AbstractValidator.prototype = {
initialize : function(_field) {
this.field = $(_field);
this.validateObj = AttributeHelper.getAttribute(this.field, "_VALIDATEOBJ_");
this.warning = "";
},
execute : function(){
this.warning = "";
if(this.validateObj[$ValidatorConfigs.CLOSE] != undefined){
try{
if(eval(this.validateObj[$ValidatorConfigs.CLOSE]))
return true;
}catch(error){
alert("AbstractValidator.prototype:execute:" + error.message);
return false;
}
}
if($F(this.field).trim() == ''){
if(ValidationHelper.hasRequired(this.validateObj)){
this.warning += this.getReplaceInfoContent("REQUIRED", "WARNING_INFO");
return false;
}else{
return true;
}
}
if(this.validateObj[$ValidatorConfigs.FORMAT]){
eval("this.outerformat = " + this.validateObj[$ValidatorConfigs.FORMAT] + ";");
if($F(this.field).trim() != '' && !this.outerformat.test($F(this.field))){
this.warning += this.validateObj[$ValidatorConfigs.WARNING] || this.getWarning();
return false;
}
}
if($F(this.field).trim() != '' && this.format && !this.format.test($F(this.field))){
this.warning += this.getWarning();
return false;
}
var arrayMethod = [];
arrayMethod.push(this.method || Ext.emptyFn);
var strMethods = this.validateObj[$ValidatorConfigs.METHODS];
if(strMethods){
if(String.isString(strMethods)){
Array.prototype.push.apply(arrayMethod, strMethods.split(","));
}else{
if(!Array.isArray(strMethods)){
strMethods = [strMethods];
}
Array.prototype.push.apply(arrayMethod, strMethods);
}
}
var result = arrayMethod[0].call(this);
if(!result || arrayMethod.length <= 1) return result;
result = true;
for (var i = 1; i < arrayMethod.length; i++){
if(Ext.isFunction(arrayMethod[i])){
var temp = arrayMethod[i].call(this);
if(temp == null)result = null;
if(temp === false)result = false;
continue;
}
arrayMethod[i] = arrayMethod[i].trim();
if(arrayMethod[i] != ""){
eval("var temp = " + arrayMethod[i]);
if(temp == null)result = null;
if(temp === false)result = false;
}
}
return result;
},
getDesc : function(){
var sDesc = this.field.getAttribute("validation_desc");
if(sDesc != null) return sDesc;
return this.validateObj[$ValidatorConfigs.DESC];
},
getWarning : function(){
return ValidationHelper.getReplaceInfoContent("DEFAULT", "WARNING_INFO", this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
},
getReplaceInfoContent : function(info, infoType, args, _desc){
var desc = '';
if(this.validateObj[$ValidatorConfigs.NO_DESC] == undefined){
desc = this.getDesc() || _desc || this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id;
}
return ValidationHelper.getReplaceInfoContent(info, infoType, desc, args);
},
checkSyntaxValidation:function(){
return true;
},
getMessage : function(){
return this.validateObj[$ValidatorConfigs.MESSAGE] || '';
}
};
if(ValidatorLang.LOCALE == 'en'){
Ext.apply(AbstractValidator.prototype, {
getDesc : function(_desc){
return "It ";
},
getReplaceInfoContent : function(info, infoType, args, _desc){
var desc = this.getDesc() || _desc;
return ValidationHelper.getReplaceInfoContent(info, infoType, desc, args);
}
});
}
var NumberValidator = Class.create();
Object.extend(NumberValidator.prototype, AbstractValidator.prototype);
Object.extend(NumberValidator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
this.type = this.validateObj[$ValidatorConfigs.TYPE].trim().toUpperCase();
if(this.validateObj[$ValidatorConfigs.TYPE].trim().toLowerCase() == 'int'){
this.parseMethod = parseInt;
this.format = new RegExp('^-?\\d+(e[+-]?\\d+)?$', "i");
}else{
this.parseMethod = parseFloat;
this.format = new RegExp('^-?\\d+(\\.\\d+)?(e[+-]?\\d+)?$', "i");
}
if(!this.checkValidationSyntax()) return false;
},
method : function(){
if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
if(this.minValue > this.parseMethod($F(this.field)) || this.parseMethod($F(this.field)) > this.maxValue){
this.warning += this.getReplaceInfoContent("VALUE_RANGE", "WARNING_INFO", [this.minValue, this.maxValue]);
return false;
}
return true;
}
var flag = true;
if(this.validateObj[$ValidatorConfigs.MIN]){
if(this.parseMethod($F(this.field)) < this.minValue){
flag = false;
this.warning += this.getReplaceInfoContent("MIN", "WARNING_INFO", this.minValue);
}
}
if(this.validateObj[$ValidatorConfigs.MAX]){
if(this.parseMethod($F(this.field)) > this.maxValue){
flag = false;
this.warning += this.getReplaceInfoContent("MAX", "WARNING_INFO", this.maxValue);
}
}
return flag;
},
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
var message = "";
if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
message += this.getReplaceInfoContent("VALUE_RANGE", "MESSAGE_INFO", [this.minValue, this.maxValue]);
return message;
}
if(this.validateObj[$ValidatorConfigs.MIN]){
message += this.getReplaceInfoContent("MIN", "MESSAGE_INFO", this.minValue);
}
if(this.validateObj[$ValidatorConfigs.MAX]){
message += this.getReplaceInfoContent("MAX", "MESSAGE_INFO", this.maxValue);
}
return message;
},
getWarning : function(){
return this.getReplaceInfoContent(this.type, "WARNING_INFO", null, this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
},
checkValidationSyntax : function(){
if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
var valueRange = this.validateObj[$ValidatorConfigs.VALUE_RANGE].split(",");
for (var i = 0; i < valueRange.length; i++){
valueRange[i] = (valueRange[i] + "").trim();
}
if((valueRange.length != 2) ||
(valueRange[0] != "" && isNaN(this.parseMethod(valueRange[0]))) || 
(valueRange[1] != "" && isNaN(this.parseMethod(valueRange[1])))){
alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是分割的两个数字！",this.validateObj[$ValidatorConfigs.VALUE_RANGE]));
return false;
}
this.minValue = (valueRange[0] === "" ? Number.NEGATIVE_INFINITY : this.parseMethod(valueRange[0]));
this.maxValue = (valueRange[1] === "" ? Number.POSITIVE_INFINITY : this.parseMethod(valueRange[1]));
}
if(this.validateObj[$ValidatorConfigs.MIN]){
this.minValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MIN].trim());
if(isNaN(this.minValue)){
alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN]));
return false;
}
}
if(this.validateObj[$ValidatorConfigs.MAX]){
this.maxValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MAX.trim()]);
if(isNaN(this.maxValue)){
alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN]));
return false;
}
}
return true;
}
});
var StringValidator = Class.create();
Object.extend(StringValidator.prototype, AbstractValidator.prototype);
Object.extend(StringValidator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
this.getLength();
if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
if(this.minLen > this.length || this.length > this.maxLen){
this.warning += this.getReplaceInfoContent("LENGTH_RANGE", "WARNING_INFO", [this.minLen, this.maxLen]);
return false;
}
return true;
}
var flag = true;
if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
if(this.length < this.minLen){
flag = false;
this.warning += this.getReplaceInfoContent("MIN_LEN", "WARNING_INFO", [this.minLen]);
}
}
if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
if(this.length > this.maxLen){
flag = false;
this.warning += this.getReplaceInfoContent("MAX_LEN", "WARNING_INFO", [this.maxLen]);
}
}
return flag;
},
getLength : function(){
this.length = ($F(this.field)||'').byteLength();
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
var message = "";
if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
message += this.getReplaceInfoContent("LENGTH_RANGE", "MESSAGE_INFO", [this.minLen, this.maxLen]);
return message;
}
if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
message += this.getReplaceInfoContent("MIN_LEN", "MESSAGE_INFO", this.minLen);
}
if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
message += this.getReplaceInfoContent("MAX_LEN", "MESSAGE_INFO", this.maxLen);
}
if(message == '' && ValidationHelper.hasRequired(this.validateObj)){
message = this.getReplaceInfoContent("REQUIRED", "MESSAGE_INFO");
}
return message;
},
checkValidationSyntax : function(){
if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
var lengthRange = this.validateObj[$ValidatorConfigs.LENGTH_RANGE].split(",");
for (var i = 0; i < lengthRange.length; i++){
lengthRange[i] = lengthRange[i].trim();
}
if((lengthRange.length != 2) ||
(lengthRange[0] != "" && isNaN(parseInt(lengthRange[0]))) || 
(lengthRange[1] != "" && isNaN(parseInt(lengthRange[1])))){
alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是分割的两个数字！",this.validateObj[$ValidatorConfigs.LENGTH_RANGE]));
return false;
}
this.minLen = (lengthRange[0] === "" ? 0 : parseInt(lengthRange[0]));
this.maxLen = (lengthRange[1] === "" ? Number.POSITIVE_INFINITY : parseInt(lengthRange[1]));
}
if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
this.minLen = parseInt((""+this.validateObj[$ValidatorConfigs.MIN_LEN]).trim());
if(isNaN(this.minLen)){
alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN_LEN]));
return false;
}
}
if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
this.maxLen = parseInt((""+this.validateObj[$ValidatorConfigs.MAX_LEN]).trim());
if(isNaN(this.maxLen)){
alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MAX_LEN]));
return false;
}
}
return true;
}
});
var CommonCharValidator = Class.create();
Object.extend(CommonCharValidator.prototype, StringValidator.prototype);
Object.extend(CommonCharValidator.prototype, {
format : /^\w*$/,
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
var message = StringValidator.prototype.getMessage.call(this);
if(message != "")
message += this.getReplaceInfoContent("COMMON_CHAR", "MESSAGE_INFO", null, ' ');
else
message = this.getReplaceInfoContent("COMMON_CHAR", "MESSAGE_INFO");
return message;
},
getWarning : function(){
return this.getReplaceInfoContent("COMMON_CHAR", "WARNING_INFO");
}
});
var URLValidator = Class.create();
Object.extend(URLValidator.prototype, AbstractValidator.prototype);
Object.extend(URLValidator.prototype, {
format : /^(http|https):\/\/(?:(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)|localhost)(:(\d+))?(\/)?/i, 
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("URL", "MESSAGE_INFO");
},
method : function(){
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
}, 
getWarning:function(){
return this.validateObj[$ValidatorConfigs.WARNING] || this.getReplaceInfoContent("URL", "WARNING_INFO");
}
});
var IPV4Validator = Class.create();
Object.extend(IPV4Validator.prototype, AbstractValidator.prototype);
Object.extend(IPV4Validator.prototype, {
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("IPV4", "MESSAGE_INFO");
},
getWarning : function(){
},
method : function(){
var format = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
if(format.exec($F(this.field)) == null){
this.warning += this.getReplaceInfoContent("IPV4", 'WARNING_INFO');
return false;
}
if (RegExp.$1 == 0){
this.warning += this.getReplaceInfoContent("IPV4_HIGH_EQUAL_0", 'WARNING_INFO');
return false;
}
if (Math.max(RegExp.$1, RegExp.$2, RegExp.$3, RegExp.$4) > 255){
this.warning += this.getReplaceInfoContent('IPV4_BEYOND_255', "WARNING_INFO");
return false;
}
return true;
}
});
var DateValidator = Class.create();
Object.extend(DateValidator.prototype, AbstractValidator.prototype);
Object.extend(DateValidator.prototype, {
formatRegExp : /^(yy|yyyy)(-|\/)(m{1,2})(\2)(d{1,2})(\W+(h{1,2})(:)(M{1,2})((\8)(s{0,2}))?)?$/,
dateRegExp : /^(\d{2}|\d{4})(-|\/)(\d{1,2})(\2)(\d{1,2})(\W+(\d{1,2})(:)(\d{1,2})((\8)(\d{0,2}))?)?$/,
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
var sFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT];
var sTemp = sFormat.replace(/yy|mm|dd|hh|ss/gi, "t").replace(/m|d|h|s/ig, '\\d{1,2}').replace(/t/ig, '\\d{2}');
var oRegExp = new RegExp("^"+sTemp+"$");
var sValue = $F(this.field);
if(!oRegExp.test(sValue)){
this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());
if(!this.validateObj["showDefault"]){
this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
this.field.select();
this.field.focus();
}
return false;
}
var matchs = $F(this.field).match(this.dateRegExp);
if(!matchs){
this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());
this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
this.field.select();
this.field.focus();
return false;
}
var year = parseInt(matchs[1], 10);
var month = parseInt(matchs[3], 10);
var day = parseInt(matchs[5], 10);
var hour = parseInt(matchs[7], 10);
var minute = parseInt(matchs[9], 10);
var second = parseInt(matchs[12], 10);
if(matchs[1].length != this.formatMatchs[1].length
|| matchs[2] != this.formatMatchs[2]){
this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());
this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
this.field.select();
this.field.focus();
return false;
}
if(month < 1 || month > 12){
this.warning = ValidatorLang.TEXT14 || "月份应该为1到12的整数";
return false;
}
if (day < 1 || day > 31){
this.warning = (ValidatorLang.TEXT15 || "每个月的天数应该为1到31的整数");
return false;
}
if ((month==4 || month==6 || month==9 || month==11) && day==31){
this.warning = ValidatorLang.TEXT16 || "该月不存在31号";
return false;
} 
if (month==2){
var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));
if (day>29){
this.warning = ValidatorLang.TEXT17 || "2月最多有29天";
return false;
} 
if ((day==29) && (!isleap)){
this.warning = ValidatorLang.TEXT18 || "闰年2月才有29天";
return false;
}
}
if(hour && hour<0 || hour>23){
this.warning = ValidatorLang.TEXT19 || "小时应该是0到23的整数";
return false;
} 
if(minute && minute<0 || minute>59){
this.warning = ValidatorLang.TEXT20 || "分应该是0到59的整数";
return false;
} 
if(second && second<0 || second>59){
this.warning = ValidatorLang.TEXT21 || "秒应该是0到59的整数";
return false;
} 
return true;
},
checkValidationSyntax : function(){
var timeFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT]
= this.validateObj[$ValidatorConfigs.DATE_FORMAT] || $ValidatorConfigs.DATE_FORMAT_DEFAULT;
this.formatMatchs = timeFormat.match(this.formatRegExp);
if(!this.formatMatchs){
alert((ValidatorLang.TEXT22 || "日期格式错误：") + timeFormat.toLowerCase());
return false;
}
return true;
}
});
var Custom_radio_Validator = Class.create();
Object.extend(Custom_radio_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_radio_Validator.prototype, {
method : function(){
var radioArray = this.field.name ? document.getElementsByName(this.field.name) : [this.field];
for (var i = 0; i < radioArray.length; i++){
if(radioArray[i].checked)
return true;
}
return false;
}
});
var Custom_select_Validator = Class.create();
Object.extend(Custom_select_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_select_Validator.prototype, {
method : function(){
var excludeValue = this.validateObj[$ValidatorConfigs.EXCLUDE];
if(excludeValue == undefined){
excludeValue = this.field.options[0] ? this.field.options[0].value : "";
}
var selectValue = $F(this.field);
if(excludeValue == selectValue){
this.warning += this.getReplaceInfoContent("SELECT", "WARNING_INFO");
return false;
}
return true;
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("SELECT", "MESSAGE_INFO");
}
});
var Custom_time_Validator = Class.create();
Object.extend(Custom_time_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_time_Validator.prototype, {
_format : [/^(\d\d):(\d\d)(:(\d\d))?$/, /^(\d{1,2}):(\d{1,2})(:(\d{1,2}))?$/],
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
if($F(this.field).match(this._format[this._type])){
if(parseInt(RegExp.$1) > 23){
this.warning += this.getReplaceInfoContent("HOUR", "WARNING_INFO");
return false;
}
if(parseInt(RegExp.$2) > 59){
this.warning += this.getReplaceInfoContent("MINUTE", "WARNING_INFO");
return false;
}
if(RegExp.$4 && parseInt(RegExp.$4) > 59){
this.warning += this.getReplaceInfoContent("SECOND", "WARNING_INFO");
return false;
}
return true;
}else{
this.warning += this.getReplaceInfoContent("TIME", "WARNING_INFO");
return false;
}
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
if(this._type == 0){
return this.getReplaceInfoContent("TIME_ONE", "MESSAGE_INFO");
}else if(this._type == 1){
return this.getReplaceInfoContent("TIME_TWO", "MESSAGE_INFO");
}
},
checkValidationSyntax : function(){
var timeFormat = this.validateObj[$ValidatorConfigs.TIME_FORMAT];
if(timeFormat != undefined){
timeFormat = timeFormat.trim();
if(timeFormat.replace(/h|m|s|:/ig,'') != ""){
alert("Custom_time_Validator.prototype:checkValidationSyntax:" + ValidatorLang.TEXT1 || '时间的time_format格式不对');
return false;
}
var timeArray = timeFormat.split(":");
if(timeArray[0].length == 2){
this._type = 0;
}else{
this._type = 1;
}
}else{
if($ValidatorConfigs.TIME_FORMAT_DEFAULT == 'hh:mm:ss'){
this._type = 0;
}else{
this._type = 1;
}
}
return true;
}
});
var Custom_combin_Validator = Class.create();
Object.extend(Custom_combin_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_combin_Validator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
var innerValidationIns = ValidationFactory._makeValidator(this._subtype, this.field);
return true;
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return "";
},
checkValidationSyntax : function(){
this._field = this.validateObj[$ValidatorConfigs.FIELD];
this._subtype = this.validateObj[$ValidatorConfigs.SUBTYPE];
if(this._field == undefined){
alert("Custom_combin_Validator.prototype:checkValidationSyntax:" + ValidatorLang.TEXT2 || '没有指定field域');
return false;
}
if(this._subtype == undefined){
alert("Custom_combin_Validator.prototype:checkValidationSyntax:" + ValidatorLang.TEXT3 || '没有指定sub_type域');
return false;
}
this._relation = this.validateObj[$ValidatorConfigs.RELATION];
if(this._relation == undefined){
this._relation = "=";
}else{
this._relation = this._relation.trim();
}
if(this._relation.replace(/>|=|</g, "") != ''){
alert("Custom_combin_Validator.prototype:checkValidationSyntax:relation" + ValidatorLang.TEXT4 || '域指定不合法');
return false;
}
}
});
var Custom_link_Validator = Class.create();
Object.extend(Custom_link_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_link_Validator.prototype, {
format:/^([^.]+\.){1,}[^.]+$/,
getWarning:function (){
return this.getReplaceInfoContent("LINK", "WARNING_INFO");
},
method : function(){
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
}, 
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("LINK", "MESSAGE_INFO");
}
});
var Custom_common_char2_Validator = Class.create();
Object.extend(Custom_common_char2_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_common_char2_Validator.prototype, {
method : function(){
if(!$F(this.field).match(/^[A-Za-z]\w*$/)){
this.warning += this.getReplaceInfoContent("COMMON_CHAR2", "WARNING_INFO");
return false;
}
var comCharVal = new CommonCharValidator(this.field);
var result = comCharVal.execute();
this.warning = comCharVal.warning;
return result;
},
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("COMMON_CHAR2", "MESSAGE_INFO");
}
});
var Custom_string2_Validator = Class.create();
Object.extend(Custom_string2_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_string2_Validator.prototype, {
method : function(){
if($F(this.field).match(/^[^A-Za-z\u4E00-\u9FA5]/)){
this.warning += this.getReplaceInfoContent("STRING2", "WARNING_INFO");
return false;
}
var stringVal = new StringValidator(this.field);
var result = stringVal.execute();
this.warning = stringVal.warning;
return result;
},
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("STRING2", "MESSAGE_INFO");
}
});
var Custom_filename_Validator = Class.create();
Object.extend(Custom_filename_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_filename_Validator.prototype, {
method : function(){
if($F(this.field).match(/[\\\/\:\*\?\"\<\>\|]/)){
this.warning += ValidatorLang.TEXT5 || '不应包含\\/:*?"<>|等字符'; //TODO改为从配置中取
return false;
}
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
},
getMessage:function (){
return ValidatorLang.TEXT6 || '普通字符，\\/:*?"<>|等字符除外'; //TODO改为从配置中取
}
});
var Custom_uri_Validator = Class.create();
Object.extend(Custom_uri_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_uri_Validator.prototype, {
format : /^(((\w+):\/\/)|(mailto:))([^/:]+)(:\d*)?([^# ]*)/i, 
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("URL", "MESSAGE_INFO");
},
method : function(){
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
}, 
getWarning:function(){
return this.validateObj[$ValidatorConfigs.WARNING] || this.getReplaceInfoContent("URL", "WARNING_INFO");
}
});
var Custom_time_Validator = Class.create();
Object.extend(Custom_time_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_time_Validator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
var sValue= $F(this.field);
var aInfo = sValue.split(":");
if(this.aTimeFormat.length != aInfo.length || !/^(\d+(:\d+)?)?$/.test(sValue)){
this.warning = (ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase();
return false;
}
var hour = parseInt(aInfo[0], 10);
var minute = parseInt(aInfo[1], 10);
var second = parseInt(aInfo[2], 10);
if(hour && hour<0 || hour>23){
this.warning = ValidatorLang.TEXT8 || "小时应该是0到23的整数";
return false;
} 
if(minute && minute<0 || minute>59){
this.warning = ValidatorLang.TEXT9 || "分应该是0到59的整数";
return false;
} 
if(second && second<0 || second>59){
this.warning = ValidatorLang.TEXT10 || "秒应该是0到59的整数";
return false;
} 
return true;
},
checkValidationSyntax : function(){
var timeFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT]
= this.validateObj[$ValidatorConfigs.DATE_FORMAT] || $ValidatorConfigs.TIME_FORMAT_DEFAULT;
this.aTimeFormat = timeFormat.split(":");
return true;
}
});
Ext.ns('com.trs.validator', 'ValidatorLang');
var ValidationFactory = Class.create();
Ext.apply(ValidationFactory, {
getValidateObj : function(_eField){
_eField = $(_eField);
var myValidateObj = ValidationHelper.getRegisterValidations(_eField.name || _eField.id);
var sValidation = _eField.getAttribute("validation");
if(!sValidation){
return Ext.apply({}, myValidateObj);
}
var cSplit = ";";
if(sValidation.indexOf(cSplit) < 0){
cSplit = ",";
}
var oValidateObj = {};
var sRegExp = "([^\\s:]+)\\s*:\\s*(?:\\'(.*?)\\'|([^,\\']*?))\\s*(?:,|$)";
var regExp = new RegExp(sRegExp.replace(/,/g, cSplit), "g");
while(regExp.exec(sValidation)){
var $1 = RegExp.$1;
var $2 = RegExp.$2 || RegExp.$3;
oValidateObj[$1.trim()] = ($2 || "").trim();
}
return Ext.apply(oValidateObj, myValidateObj);
},
makeValidator : function(_field){
var eField = $(_field);
var validateObj = this.getValidateObj(eField);
AttributeHelper.setAttribute(eField, "_VALIDATEOBJ_", validateObj);
var validatorIns = null;
var sType = validateObj[$ValidatorConfigs.TYPE];
if(!sType){
validatorIns = new AbstractValidator(eField);
}else{
validatorIns = this._makeValidator(sType, eField);
}
AttributeHelper.setAttribute(eField, "_VALIDATORINS_", validatorIns);
return validatorIns;
},
_makeValidator : function(type, _field){
var validatorIns = null;
switch(type.toLowerCase()){
case "int":
case "float":
case "double":
case "number":
validatorIns = new NumberValidator(_field);
break;
case "date":
validatorIns = new DateValidator(_field);
break;
case "url":
validatorIns = new URLValidator(_field);
break;
case "string":
validatorIns = new StringValidator(_field);
break;
case "ip":
validatorIns = new IPV4Validator(_field);
break;
case "common_char":
validatorIns = new CommonCharValidator(_field);
break;
default:
eval("validatorIns = new Custom_" + type.toLowerCase() + "_Validator(_field)");
}
return validatorIns;
}
});
var ValidationHelper = Class.create();
ValidationHelper.refreshValid = function(_field){
var eField = $(_field);
var sValidation = eField.getAttribute("validation");
ValidationHelper.popElements(eField);
changeBorderStyle(eField);
if(sValidation){
ValidationFactory.makeValidator(_field);
ValidationHelper.pushElements(eField);
ValidationHelper.forceValid(eField);
}
};
ValidationHelper.doAlert = function(_sMsg, _func){
try{
Ext.Msg.alert(_sMsg, function(){
ValidationHelper.exec(_func);
});
}catch (ex){
alert(_sMsg);
ValidationHelper.exec(_func);
}
};
ValidationHelper.exec = function(_func){
_func = _func || Ext.emptyFn;
try{
_func();
}catch(error){
}
}
ValidationHelper.validFuns = {};
ValidationHelper.addValidListener = function(callBackFun, validationId){
validationId = validationId || '$NoValidationId';
if(!ValidationHelper.validFuns[validationId]){
ValidationHelper.validFuns[validationId] = [callBackFun];
return;
}
if(!ValidationHelper.validFuns[validationId].include(callBackFun)){
ValidationHelper.validFuns[validationId].push(callBackFun);
}
};
ValidationHelper.invalidFuns = {};
ValidationHelper.addInvalidListener = function(callBackFun, validationId){
validationId = validationId || '$NoValidationId';
if(!ValidationHelper.invalidFuns[validationId]){
ValidationHelper.invalidFuns[validationId] = [callBackFun];
return;
}
if(!ValidationHelper.invalidFuns[validationId].include(callBackFun)){
ValidationHelper.invalidFuns[validationId].push(callBackFun);
}
};
ValidationHelper.doSubmit = function(_field){
var sameValidationIdControls = ValidationHelper.getSameValidationIdControls(_field);
for (var i = 0; i < sameValidationIdControls.length; i++){
if(AttributeHelper.getAttribute(sameValidationIdControls[i], "isValid") == false){
ValidationHelper.execInvalidFuns(_field);
return false;
}
}
ValidationHelper.execValidFuns(_field);
return true;
};
ValidationHelper.doSubmitAll = function(){
var validateControlsSimpleClone = ValidationHelper.getCloneControls(ValidationHelper.validateControls);
validateControlsSimpleClone.sort(ValidationHelper.sortFun);
while(validateControlsSimpleClone.length > 0){
var topObj = validateControlsSimpleClone.pop();
if(topObj == null) continue;
if(!ValidationHelper.doSubmit(topObj)){
while(validateControlsSimpleClone.length > 0){
if(ValidationHelper.getControlValidationId(topObj) == 
ValidationHelper.getControlValidationId(validateControlsSimpleClone.last()))
validateControlsSimpleClone.pop();
else break;
}
}
}
};
ValidationHelper.sortFun = function(_field1, _field2){
if(!_field1 || !_field2) return -1;
var id1 = ValidationHelper.getControlValidationId(_field1);
var id2 = ValidationHelper.getControlValidationId(_field2);
if(id1 < id2)return -1;
if(id1 > id2)return 1;
return 0;
};
ValidationHelper.getCloneControls = function(arrayObj){
var cloneArrayObj = [];
for (var i = 0; i < arrayObj.length; i++){
cloneArrayObj[i] = arrayObj[i];
}
return cloneArrayObj;
};
ValidationHelper.execInvalidFuns = function(_field){
var validationId = ValidationHelper.getControlValidationId(_field);
if(ValidationHelper.invalidFuns[validationId]){
ValidationHelper.invalidFuns[validationId].each(function(invalidFun){invalidFun();});
}
return false;
};
ValidationHelper.execValidFuns = function(_field){
var validationId = ValidationHelper.getControlValidationId(_field);
if(ValidationHelper.validFuns[validationId]){
ValidationHelper.validFuns[validationId].each(function(validFun){validFun();});
}
return false;
};
ValidationHelper.getSameValidationIdControls = function(_field){
var sameValidationIdControls = [];
var validationId = ValidationHelper.getControlValidationId(_field);
ValidationHelper.validateControls.each(function(element){
if(element == null) return;
var tempValidationId = ValidationHelper.getControlValidationId(element)
if(validationId == tempValidationId){
sameValidationIdControls.push(element);
}
});
return sameValidationIdControls;
};
ValidationHelper.getControlValidationId = function(_field){
_field = $(_field);
if(_field == null) return;
var validationId = _field.getAttribute("validationId");
if(!validationId && _field.form){
validationId = _field.form.getAttribute("validationId") || _field.form.id || _field.form.name;
}
return validationId ? validationId : '$NoValidationId';
};
ValidationHelper.getReplaceInfoContent = function(info, infoType, desc, _args){
var hintString = $ValidatorConfigs[infoType]["zh-cn"][info];
if(ValidatorLang.LOCALE == 'en' && !hintString.startsWith("{0}")){
desc = (desc||"").toLowerCase();
}
hintString = hintString.replace('{0}', desc);
if (_args == null) return hintString;
if (typeof _args != 'object'){
return hintString.replace('{1}', _args);
}
for (var i = 0; i < _args.length; i++){
hintString = hintString.replace('{' + (i+1) + '}', _args[i]);
} 
return hintString;
};
ValidationHelper.initIsValid = function(element){
var isValid = AttributeHelper.getAttribute(element, "_VALIDATORINS_").execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
if(isValid) {
ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}else{
ValidationHelper.addBoardToDomTree(element, "WARNING");
}
};
ValidationHelper.hasRequired = function(validateObj){
var required = validateObj[$ValidatorConfigs.REQUIRED];
if($ValidatorConfigs.getDraftMode()){
return false;
}
return !(required=='0' || required=='false' || (required==null&&required!=''));
};
ValidationHelper.makeRequiredHint = function(_field){
try{
var validateObj = AttributeHelper.getAttribute(_field, "_VALIDATEOBJ_");
if(validateObj[$ValidatorConfigs.REQUIRED] != undefined){
if(validateObj[$ValidatorConfigs.NO_REQUIRE_HINT]){
return;
}
if(!ValidationHelper.hasRequired(validateObj)){
return;
}
var index = AttributeHelper.getAttribute(_field, "_VALID_INDEX_");
var requireContainer = validateObj[$ValidatorConfigs.REQUIRE_CONTAINER];
if(requireContainer){
var sHTML = "<span class='" + $ValidatorConfigs.REQUIREDCLASS + "' id='" + $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index + "'>*</span>";
new Insertion.Bottom($(requireContainer), sHTML);
return;
}
if(_field.style.position != 'absolute'){
afterObj = "<span class='" + $ValidatorConfigs.REQUIREDCLASS + "' id='" + $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index + "'>*</span>";
new Insertion.After(_field, afterObj);
}else{
var requiredObj = document.createElement("span");
requiredObj.id = $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index;
requiredObj.innerHTML = "*";
requiredObj.className = $ValidatorConfigs.REQUIREDCLASS;
var offsets = Position.cumulativeOffset(_field);
requiredObj.style.top = offsets[1] + "px";
requiredObj.style.left = (offsets[0] + _field.offsetWidth + 1) + 'px';
requiredObj.style.position = "absolute";
document.body.appendChild(requiredObj);
}
}
}catch(error){
alert("ValidationHelper.makeRequiredHint:" + error.message);
}
};
ValidationHelper.makeInfoBoard = function(_field, _option){
var sContent = _option.content;
sContent = 
'<span id="sp_' + _option.id.trim() + '">' + sContent + '</span>';
var oInsertionNode = document.createElement('span');
oInsertionNode.style.textAlgin = 'justify';
oInsertionNode.id = _option.id;
oInsertionNode.className = _option.className;
oInsertionNode.innerHTML = sContent;
oInsertionNode.style.position = 'absolute';
oInsertionNode.style.display = 'none';
oInsertionNode.style.opacity = '0.85';
oInsertionNode.style.filter = 'alpha(opacity=85)';
return oInsertionNode;
};
ValidationHelper.getOption = function(element, _infoType, _eventType){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
return {
id: $ValidatorConfigs["PREFIX_" + _infoType + "_SPAN_ID"] + _eventType + "_" + index, 
content: _infoType == "MESSAGE" ? validatorIns.getMessage() : validatorIns.warning, 
logoSrc: $ValidatorConfigs[_infoType + "_LOG_PATH"], 
className: $ValidatorConfigs[_infoType + "_CLASSNAME_" + _eventType]
};
};
ValidationHelper.addBoardToDomTree = function(element, infoType){
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
var showControlId = validateObj[$ValidatorConfigs.SHOWID];
if(!showControlId && element.form){
showControlId = element.form.getAttribute($ValidatorConfigs.SHOWID);
}
var option = ValidationHelper.getOption(element, infoType, "KEY");
boardObj = ValidationHelper.makeInfoBoard(element, option);
if(showControlId){
if(typeof(showControlId) == 'function'){
showControlId = showControlId();
}else if(typeof(showControlId) == 'string'){
showControlId = $(showControlId);
}
showControlId.innerHTML = '';
showControlId.appendChild(boardObj);
boardObj.style.position = 'static';
var noClass = validateObj[$ValidatorConfigs.NO_CLASS];
if(noClass == undefined && element.form){
noClass = element.form.getAttribute($ValidatorConfigs.NO_CLASS);
}
if(noClass != undefined){
boardObj.className = '';
}
}else{
if(element.style.position=='absolute'){
var offsets = Position.cumulativeOffset(element);
boardObj.style.top = offsets[1] + "px";
boardObj.style.left = (offsets[0] + element.offsetWidth + 5) + 'px';
document.body.appendChild(boardObj);
}else{
var afterElement = element;
if(ValidationHelper.hasRequired(validateObj))
afterElement = element.nextSibling;
new Insertion.After(afterElement, boardObj.outerHTML);
afterElement.nextSibling.style.position = 'static';
return afterElement.nextSibling;
}
}
return boardObj;
};
ValidationHelper.showAllTime = function(element){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
Element.show(boardObjMessage);
};
ValidationHelper.keyDownEvent = function(element, event){
if(event.keyCode == Event.KEY_TAB || event.keyCode == Event.KEY_RETURN){
if(element.releaseCapture){
element.releaseCapture();
}
}else{
if(element.setCapture){
element.setCapture();
}
}
};
ValidationHelper.keyUpEvent = function(element){
if(element.releaseCapture){
element.releaseCapture();
}
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
var isValid = validatorIns.execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
if(isValid) {
if(!boardObjMessage){
boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjWarning) Element.hide(boardObjWarning);
Element.show(boardObjMessage);
}
changeBorderStyle(element);
return ValidationHelper.doSubmit(element);
ValidationHelper.doSubmit(element);
}else{
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjMessage) Element.hide(boardObjMessage);
Element.show(boardObjWarning);
}
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
return ValidationHelper.execInvalidFuns(element);
ValidationHelper.execInvalidFuns(element);
}
};
ValidationHelper.changeEvent = function(element){
element = $(element);
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var isValid = validatorIns.execute();
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
if(isValid){
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
if(validateObj[$ValidatorConfigs.RPC]){
ValidationHelper.currRPC = new Object();
ValidationHelper.currRPC.element = element;
eval("(" + validateObj[$ValidatorConfigs.RPC] + ")();");
}else{
changeBorderStyle(element);
if(!boardObjMessage){
boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjWarning) Element.hide(boardObjWarning);
Element.show(boardObjMessage);
}
AttributeHelper.setAttribute(element, 'isValid', true);
ValidationHelper.doSubmit(element);
}
}else{
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjMessage) Element.hide(boardObjMessage);
Element.show(boardObjWarning);
}
AttributeHelper.setAttribute(element, 'isValid', false);
ValidationHelper.execInvalidFuns(element);
}
};
ValidationHelper.successRPCCallBack = function(message){
changeBorderStyle(ValidationHelper.currRPC.element);
AttributeHelper.setAttribute(ValidationHelper.currRPC.element, "isValid", true);
var index = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALID_INDEX_");
var validateObj = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALIDATEOBJ_");
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
if(boardObjMessage && validateObj[$ValidatorConfigs["BLUR_SHOW"]] == undefined){
Element.hide(boardObjMessage);
}
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
if(boardObjWarning){
Element.hide(boardObjWarning);
}
return true;
};
ValidationHelper.failureRPCCallBack = function(warning){
try{
changeBorderStyle(ValidationHelper.currRPC.element, $ValidatorConfigs["WARNING_BORDER"]);
var index = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
AttributeHelper.setAttribute(ValidationHelper.currRPC.element, "isValid", false);
AttributeHelper.getAttribute(ValidationHelper.currRPC.element,"_VALIDATORINS_").warning = warning;
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(ValidationHelper.currRPC.element, "WARNING");
}
var index = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALID_INDEX_");
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = warning;
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjMessage) Element.hide(boardObjMessage);
Element.show(boardObjWarning);
}
ValidationHelper.currRPC.element.focus();
return ValidationHelper.execInvalidFuns(ValidationHelper.currRPC.element);
}catch(error){
}
};
ValidationHelper.focusEvent = function(element){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
var warningBorderstyle = $ValidatorConfigs["WARNING_BORDER"].split(" ");
if(AttributeHelper.getAttribute(element, "isValid") == false && element.style.borderBottomColor == warningBorderstyle[0] 
&& element.style.borderBottomWidth == warningBorderstyle[1] && element.style.borderBottomStyle == warningBorderstyle[2]){
if(boardObjMessage) Element.hide(boardObjMessage);
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
Element.show(boardObjWarning);
return;
}
if(boardObjWarning) Element.hide(boardObjWarning);
if(!boardObjMessage){
boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}
Element.show(boardObjMessage);
};
ValidationHelper.blurEvent = function(element){
element = $(element);
if(element.releaseCapture){
element.releaseCapture();
}
var validatorIns =AttributeHelper.getAttribute( element, "_VALIDATORINS_");
var isValid = validatorIns.execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
if(AttributeHelper.getAttribute(element, "isValid") == false){
if(boardObjMessage) Element.hide(boardObjMessage);
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
Element.show(boardObjWarning);
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
}else{
if(boardObjWarning) Element.hide(boardObjWarning);
changeBorderStyle(element);
if(boardObjMessage && validateObj[$ValidatorConfigs["BLUR_SHOW"]] == undefined){
Element.hide(boardObjMessage);
} 
ValidationHelper.doSubmit(element);
if(validateObj[$ValidatorConfigs.RPC]){
ValidationHelper.currRPC = new Object();
ValidationHelper.currRPC.element = element;
eval("(" + validateObj[$ValidatorConfigs.RPC] + ")();");
}else{
}
}
};
ValidationHelper.mouseOverEvent = function(element, event){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index);
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index);
if(AttributeHelper.getAttribute(element, "isValid") == false){
if(!boardObjWarning){
var option = ValidationHelper.getOption(element, "WARNING", "MOUSE");
boardObjWarning = ValidationHelper.makeInfoBoard(element, option);
document.body.appendChild(boardObjWarning);
boardObjWarning.style.left = Event.pointerX(window.event || event);
boardObjWarning.style.top = Event.pointerY(window.event || event);
} 
if(boardObjMessage){
Element.hide(boardObjMessage);
}
Element.show(boardObjWarning);
}else{
if(!boardObjMessage){
var option = ValidationHelper.getOption(element, "MESSAGE", "MOUSE");
boardObjMessage = ValidationHelper.makeInfoBoard(element, option);
document.body.appendChild(boardObjMessage);
boardObjMessage.style.left = Event.pointerX(window.event || event);
boardObjMessage.style.top = Event.pointerY(window.event || event);
}
if(boardObjWarning){
Element.hide(boardObjWarning);
} 
Element.show(boardObjMessage);
}
};
ValidationHelper.mouseOutEvent = function(element){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index);
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index);
if(boardObjMessage){
Element.hide(boardObjMessage);
}
if(boardObjWarning){
Element.hide(boardObjWarning);
}
};
ValidatorHelper = ValidationHelper;
ValidationHelper.valid = function(){
var arrRetVal = [];
for(var i=0;i<arguments.length;i++){
var element = arguments[i];
element = $(element);
if(element.getAttribute("forceValid") != "true" && (element.disabled || element.style.display == 'none')) continue;
if(element.getAttribute("validation") == undefined) continue;
var validatorIns = ValidationFactory.makeValidator(element);
var isValid = validatorIns.execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
if(isValid){
changeBorderStyle(element);
}else{
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
}
var validInfo = new Object();
validInfo.getMessage = function(){
return this.getMessage() || '';
}.bind(validatorIns);
validInfo.getWarning = function(){
return this.warning || '';
}.bind(validatorIns);
validInfo.isValid = function(){
return this.isValid;
}.bind({isValid:isValid});
validInfo['id'] = element.id || element.name;
arrRetVal.push(validInfo);
}
return (arrRetVal.length==0)?null:(arrRetVal.length==1)?arrRetVal[0]:arrRetVal;
};
ValidatorHelper.validAndDisplay = function(){
var validInfo = ValidationHelper.valid.apply(ValidationHelper, arguments);
if(!Array.isArray(validInfo)){
validInfo = [validInfo];
}
var result = true;
var warning = '';
var firstInvalid = null;
validInfo.each(function(element){
if(!element.isValid()){
result = false;
warning += element.getWarning() + "<br>";
if(firstInvalid == null) {
firstInvalid = element;
}
}
});
if(!result){
ValidationHelper.doAlert(warning, function(){
if(firstInvalid != null) {
try{
$(firstInvalid['id']).select();
$(firstInvalid['id']).focus();
}catch(err){
}
}
});
}
return result;
};
ValidatorHelper.asynValid = function(element, options){
element = $(element);
AttributeHelper.setAttribute(element, "asynOptions", options);
var validatorIns = ValidationFactory.makeValidator(element);
var isValid = validatorIns.execute();
if(isValid != undefined){
AttributeHelper.setAttribute(element, "isValid", isValid);
changeBorderStyle(element, isValid ? null : $ValidatorConfigs["WARNING_BORDER"]);
(options[isValid ? "success" : "fail"] || Ext.emptyFn)(validatorIns.warning || "");
}
};
ValidatorHelper.execCallBack = function(element, warning){
var isValid = !warning;
AttributeHelper.setAttribute(element, "isValid", isValid);
changeBorderStyle(element, isValid ? null : $ValidatorConfigs["WARNING_BORDER"]);
var options = AttributeHelper.getAttribute(element, "asynOptions");
if(!options)return;
(options[isValid ? "success" : "fail"] || Ext.emptyFn)(warning || "");
};
ValidatorHelper.forceValid = function(element){
element = $(element);
if(!ValidationHelper.hasValid(element)) return;
var validatorIns = AttributeHelper.getAttribute(element,"_VALIDATORINS_");
if(validatorIns == undefined){
ValidationHelper.registerValidation(element);
}
ValidationHelper.changeEvent(element);
};
ValidationHelper.doValid = function(parentId, _fDoAfterValidate, isExcludeHide){
var parentControl = $(parentId);
var inputArray = $A(parentControl.getElementsByTagName("INPUT"));
var textAreaArray = $A(parentControl.getElementsByTagName("TEXTAREA"));
var selectArray = $A(parentControl.getElementsByTagName("SELECT"));
var warning = "";
var flag = false;
var allControls = inputArray.concat(textAreaArray, selectArray);
if(isExcludeHide){
var _allControls = [];
for (var i = 0; i < allControls.length; i++){
if(Element.visible(allControls[i])){
_allControls.push(allControls[i]);
}
}
allControls = _allControls;
}
var firstInValidControl = null;
for (var i = 0; i < allControls.length; i++){
var validInfo = ValidationHelper.valid(allControls[i]);
if(!validInfo)continue;
if(!validInfo.isValid()){
if(flag == false) firstInValidControl = allControls[i];
flag = true;
warning += validInfo.getWarning() + "<br>";
}
}
if(flag){
if(_fDoAfterValidate && typeof(_fDoAfterValidate) == 'function') {
_fDoAfterValidate(warning, firstInValidControl);
return;
}
ValidationHelper.doAlert(warning, function(){
try{
$dialog().hide();
}catch(err){};
firstInValidControl.focus();
if(firstInValidControl.select){
firstInValidControl.select();
}
return false;
});
}
return !flag;
};
ValidationHelper.validateControls = [];
ValidationHelper.initValidation = function(){
var inputArray = $A(document.getElementsByTagName("INPUT"));
var textAreaArray = $A(document.getElementsByTagName("TEXTAREA"));
var selectArray = $A(document.getElementsByTagName("SELECT"));
inputArray.concat(textAreaArray, selectArray).each(function(element){
if(element.disabled || element.style.display == 'none' || element.offsetWidth == 0)
return;
var sType = element.type;
if(!sType) {
return;
}
if(sType.toLowerCase() == "hidden")
return;
ValidationHelper.registerValidation(element);
});
ValidationHelper.doSubmitAll();
};
ValidationHelper._registeredValidations_ = {
};
ValidationHelper.getRegisterValidations = function(sId){
sId = sId.toUpperCase();
return ValidationHelper._registeredValidations_[sId];
};
ValidationHelper.setRegisterValidations = function(sId, oJson){
sId = sId.toUpperCase();
ValidationHelper._registeredValidations_[sId] = oJson;
};
ValidationHelper.bindValidations = function(_arr){
_arr = Ext.isArray(_arr) ? _arr : [_arr];
for (var i = 0; i < _arr.length; i++){
var item = _arr[i];
ValidationHelper.setRegisterValidations(item.renderTo, Ext.apply({}, item));
}
};
ValidationHelper.hasValid = function(element, sId){
element = $(element);
if(element.getAttribute("validation") != null){
return true;
}
return ValidationHelper.getRegisterValidations(sId || element.name || element.id) != null;
};
ValidationHelper.registerValidations = function(_arr){
_arr = Ext.isArray(_arr) ? _arr : [_arr];
for (var i = 0; i < _arr.length; i++){
var item = _arr[i];
var element = $(item.renderTo);
if(element==null)continue;
ValidationHelper.setRegisterValidations(element.name || element.id, Ext.apply({}, item));
if(element.getAttribute("validation") == window.undefined){
element.setAttribute("validation", "");
}
ValidationHelper.registerValidation(element);
}
};
ValidationHelper.registerValidation = function(element){
element = $(element);
if(!ValidationHelper.hasValid(element)) return;
if(ValidationHelper.validateControls.include(element)) return;
var index = ValidationHelper.validateControls.length;
AttributeHelper.setAttribute(element, "_VALID_INDEX_", index);
ValidationHelper.validateControls.push(element);
var validatorIns = ValidationFactory.makeValidator(element);
if($ValidatorConfigs.getRequiredHint()){
ValidationHelper.makeRequiredHint(element);
}
ValidationHelper.initIsValid(element);
var bindMethod = {};
AttributeHelper.setAttribute(element, "bindMethod", bindMethod);
bindMethod.keyUpEvent = ValidationHelper.keyUpEvent.bind(ValidationHelper, element);
bindMethod.keyDownEvent = ValidationHelper.keyDownEvent.bind(ValidationHelper, element);
Event.observe(element, 'keydown', bindMethod.keyDownEvent, false);
Event.observe(element, 'keyup', bindMethod.keyUpEvent, false);
if($ValidatorConfigs.getShowAllMode()){
ValidationHelper.showAllTime(element);
}else if($ValidatorConfigs.getFocusMode()){
bindMethod.blurEvent = ValidationHelper.blurEvent.bind(ValidationHelper, element);
Event.observe(element, 'blur', bindMethod.blurEvent, false);
if(element.tagName.toUpperCase() == "SELECT"){
bindMethod.changeEvent = ValidationHelper.changeEvent.bind(ValidationHelper, element);
Event.observe(element, 'change', bindMethod.changeEvent, false);
}else{
bindMethod.focusEvent = ValidationHelper.focusEvent.bind(ValidationHelper, element);
Event.observe(element, 'focus', bindMethod.focusEvent, false);
}
}
if($ValidatorConfigs.getMouseMode()){
bindMethod.mouseOverEvent = ValidationHelper.mouseOverEvent.bind(ValidationHelper, element, event);
bindMethod.mouseOutEvent = ValidationHelper.mouseOutEvent.bind(ValidationHelper, element, event);
Event.observe(element, 'mouseover', bindMethod.mouseOverEvent, false);
Event.observe(element, 'mouseout', bindMethod.mouseOutEvent, false);
}
return true;
};
ValidationHelper.pushElements = function(){
var elementArray = $.apply($, arguments);
if(elementArray.constructor != Array) elementArray = [elementArray];
for (var i = 0; i < elementArray.length; i++){
element = elementArray[i];
var registerStatus = ValidationHelper.registerValidation(element);
if(registerStatus){
if(element.value != "" && AttributeHelper.getAttribute(element, "isValid")){
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
if(validateObj[$ValidatorConfigs.RPC]){
ValidationHelper.currRPC = new Object();
ValidationHelper.currRPC.element = element;
eval("(" + validateObj[$ValidatorConfigs.RPC] + ")();");
}
}else{
ValidationHelper.doSubmit(element);
}
}
}
};
ValidationHelper.popElements = function(){
var elementArray = $.apply($, arguments);
if(elementArray.constructor != Array) elementArray = [elementArray];
var doms = ValidationHelper.validateControls;
for (var i = 0, length = doms.length; i < length; i++){
if(!elementArray.include(doms[i])){
continue;
}
ValidationHelper.unregisterElement(doms[i]);
if(AttributeHelper.getAttribute(doms[i], "isValid") == false){
AttributeHelper.removeAttribute(doms[i], "isValid");
ValidationHelper.doSubmit(doms[i]);
} 
doms[i] = null;
}
};
ValidationHelper.notify = function(){
var elementArray = $.apply($, arguments);
if(elementArray.constructor != Array) elementArray = [elementArray];
for (var i = 0; i < elementArray.length; i++){
var element = elementArray[i];
if(element.disabled || element.style.display == 'none'){
ValidationHelper.popElements(element);
}else{
ValidationHelper.pushElements(element);
}
}
};
ValidationHelper.unregisterElement = function(element){
if(!element) return;
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var aIdInfo = [
$ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index,
$ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index,
$ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index,
$ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index,
$ValidatorConfigs.PREFIX_HINT_SPAN_ID + index
];
for (var i = 0; i < aIdInfo.length; i++){
var dom = $(aIdInfo[i]);
if(dom && dom.parentNode){
dom.parentNode.removeChild(dom);
}
}
var bindMethod = AttributeHelper.getAttribute(element, "bindMethod");
for(var tempEvent in bindMethod){
Event.stopObserving(element, tempEvent.replace("Event", '').toLowerCase(), bindMethod[tempEvent], false);
delete bindMethod[tempEvent];
}
var _VALIDATORINS_ = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
delete _VALIDATORINS_["field"];
AttributeHelper.removeAttribute(element, "bindMethod");
AttributeHelper.removeAttribute(element, "_VALIDATORINS_");
AttributeHelper.removeAttribute(element, "_VALIDATEOBJ_");
AttributeHelper.removeAttribute(element, "asynOptions");
};
ValidationHelper.isValid = function(element){
return AttributeHelper.getAttribute(element, "isValid");
};
Event.observe(window, 'unload', function(){
var doms = ValidationHelper.validateControls;
var length = doms.length;
for (var i = 0; i < length; i++){
ValidationHelper.unregisterElement(doms[i]);
doms[i] = null;
}
ValidationHelper.validateControls = [];
});
function changeBorderStyle(element, newBorder){
if(AttributeHelper.getAttribute(element, "_oldBorderStyle_") == undefined){
var oldBorderWidth = Element.getStyle(element, "borderWidth") || '';
var oldBorderStyle = Element.getStyle(element, "borderStyle") || '';
var oldBorderColor = Element.getStyle(element, "borderColor") || '';
AttributeHelper.setAttribute(element, "_oldBorderWidth_", oldBorderWidth);
AttributeHelper.setAttribute(element, "_oldBorderStyle_", oldBorderStyle);
AttributeHelper.setAttribute(element, "_oldBorderColor_", oldBorderColor);
}
if(newBorder){
element.style.border = newBorder;
}else{
if(element.tagName!='TEXTAREA' && AttributeHelper.getAttribute(element, "_oldBorderStyle_") == 'none'){
element.style.borderWidth = '';
element.style.borderStyle = 'none';
element.style.borderColor = '';
}else{
var borderWidth = AttributeHelper.getAttribute(element, "_oldBorderWidth_");
if(borderWidth.startsWith("0")){
element.style.borderWidth = '';
}else{
element.style.borderWidth = borderWidth;
}
var borderColor = AttributeHelper.getAttribute(element, "_oldBorderColor_");
var borderStyle = AttributeHelper.getAttribute(element, "_oldBorderStyle_");
if(borderColor=="#000000" && borderStyle.toLowerCase()=="inset"){
element.style.borderColor = "#ffffff";
}else{
element.style.borderColor = AttributeHelper.getAttribute(element, "_oldBorderColor_");
}
element.style.borderStyle = borderStyle;
}
}
}

Ext.ns('wcm.ProcessBar');
Ext.ns("wcm.LANG");
(function(){
var actualTop = window.$MsgCenter?$MsgCenter.getActualTop():window;
if(actualTop && actualTop.ProcessBar) {
if(actualTop.ProcessBar.isExtend){
window.ProcessBar = wcm.ProcessBar = actualTop.ProcessBar;
return;
}
}
var m_sTemplate = [
'<div class="processbar_coverall"></div>',
'<div class="processbar_table_outer">',
'<table class="processbar_table" border=1 bordercolor=#000000 ',
'bordercolordark=#ffffff cellspacing=0 cellpadding=0 style="font-size:12px">',
'<tr><td height=22 align=center bgcolor="#BBBBBB" style="font-size:14px;">',
wcm.LANG['WCM_WELCOME'] || '欢迎您使用TRS WCM系统' ,'</td></tr>',
'<tr>',
'<td align=center bgcolor=#DDDDDD>',
String.format(wcm.LANG['NOW_DOING_WAIT'] || '正在{0},请耐心等候.', '<span id=processbar_thing>{0}</span>'),
'<br><br>', wcm.LANG['TIME_WAIT_DESC'] || '等待时间：', 
'<span style="color:red;"><span id=processbar_waitingTime>0</span>',
wcm.LANG['TIME_UNIT_SECOND'] || '秒',
'</span>',
'<div style="padding:10px;">如果系统长时间无法响应,请<a href="#" id="pb_closer" onclick="ProcessBar.close();return false;">',
'<span style=\"color:red;\">点击这里</span></a>返回主页面</div>',
'</td>',
'</tr>',
'</table>',
'</div>'
].join('');
var m_intervalProcessbar = 0;
var m_sTitle = '';
ProcessBar = wcm.ProcessBar = {
isExtend : true,
init : function(title){
m_sTitle = title;
},
addState : function(){
},
next : function(title){
Element.update('processbar_thing', title);
},
start : function(title){
var processbar = $('processbar');
if(!processbar){
processbar = document.createElement('DIV');
processbar.id = 'processbar';
document.body.appendChild(processbar);
}
processbar.innerHTML = String.format(m_sTemplate, title || m_sTitle || '');
var lStart = new Date().getTime();
if(m_intervalProcessbar){
clearInterval(m_intervalProcessbar);
}
m_intervalProcessbar = setInterval(function(){
var lNow = new Date().getTime();
var elisTime = ((lNow - lStart) * 1.00 / 1000 + '');
var arr = elisTime.split('');
arr[0] = arr[0] || '0';
arr[1] = arr[1] || '.';
arr[2] = arr[2] || '0';
arr[3] = arr[3] || '0';
arr[4] = arr[4] || '0';
document.getElementById('processbar_waitingTime').innerHTML = arr.join('');
}, 100);
},
exit : function(){
var processbar = $('processbar');
if(!processbar)return;
processbar.innerHTML = '';
document.body.removeChild(processbar);
clearInterval(m_intervalProcessbar);
},
close :function(){
ProcessBar.exit();
}
};
if(actualTop == window)return;
for(var name in ProcessBar){
ProcessBar[name] = wcm.ProcessBar[name] = function(){
var actualTop = $MsgCenter.getActualTop();
var ProcessBar0 = actualTop.ProcessBar || ProcessBar;
return ProcessBar0[this].apply(ProcessBar0, arguments);
}.bind(name);
}
})();

Ext.ns('com.trs.FloatPanel');
(function(){
var m_sFloatPanelId = 'floatpanel', actualTop = $MsgCenter.getActualTop();
var m_sCurrPath = location.href.replace(new RegExp("^.*:\/\/[^\/]*((.*\/|))[^\/]+$", "ig"), "$1");
if(actualTop.FloatPanel){
window.FloatPanel = com.trs.FloatPanel = actualTop.FloatPanel;
try{
try{
if(FloatPanel.opened && FloatPanel._window==parent)return;
}catch(err){
}
if(FloatPanel.opened)return;
FloatPanel._window = null;
if(FloatPanel.opened){
var oPanel = FloatPanel.getPanel();
Element.hide(oPanel);
oPanel.src = WCMConstants.WCM6_PATH + 
'js/source/wcmlib/com.trs.floatpanel/resource/window.html';
}
FloatPanel.opened = false;
}catch(err){
}
return;
}
function calRelativePath(src){
while(src.indexOf('./')!=-1){
src = src.replace(/\/(\.\/)+/g, '/').replace(/\/[^\.\/]+\/\.\.\//g, '/');
}
src = src.replace(/(\/){2,}/g, '/');
return src;
}
window.FloatPanel = com.trs.FloatPanel = {
getPanel : function(){
return $(m_sFloatPanelId);
},
hide : function(){
var ePanel = this.getPanel();
Element.hide(ePanel);
$MsgCenter.enableKeyDown();
try{
var el = actualTop.window.$('fix-focus-element');
if(el) el.focus();
else actualTop.window.focus();
}catch(err){
}
},
openByInfo : function(info){
if(!Ext.isString(info.src))return;
this.open(info.src, info.title, info.callback, info.dialogArguments);
},
topMe : function(){
$(m_sFloatPanelId).style.zIndex = $MsgCenter.genId(10000);
},
open : function(_src, _sTitle, _iWidth, _iHeight, _fWhenClose, _args){
this.opened = true;
if(!Ext.isString(_src)){
this.openByInfo(_src);
return;
}
if(!_src.startsWith('/')){
var alink = document.createElement('A');
_src = calRelativePath(m_sCurrPath + _src);
}
$MsgCenter.cancelKeyDown();
var ePanel = this.getPanel();
this._window = ePanel.contentWindow;
ePanel.style.display = '';
if(Ext.isFunction(arguments[2]) || arguments[2]==null){
this._window.openMe(_src, _sTitle, 0, 0, arguments[2], arguments[3]);
}else{
this._window.openMe(_src, _sTitle, _iWidth, _iHeight, _fWhenClose, _args);
}
this.topMe();
ePanel.style.width = getWidth() + 'px';
ePanel.style.height = getHeight() + 'px';
},
openIn : function(_src,_sTitle,_iWidth,_iHeight,_fWhenClose){
$MsgCenter.cancelKeyDown();
var ePanel = this.getPanel();
ePanel.style.display = '';
if(Ext.isFunction(arguments[2])){
this._window.openMe(_src, _sTitle, 0, 0, arguments[2]);
}else{
this._window.openMe(_src, _sTitle, _iWidth, _iHeight,_fWhenClose);
}
this.topMe();
},
close : function(){
$MsgCenter.enableKeyDown();
this._window.closeMe();
this._window = null;
$MsgCenter.getActualTop().window.focus();
}
}
if(actualTop==window){
Event.observe(window, 'load', function(){
var pb = $(m_sFloatPanelId), os;
if(pb)return;
pb = document.createElement('IFRAME');
pb.id = m_sFloatPanelId;
pb.frameBorder = 0;
pb.src = WCMConstants.WCM6_PATH + 
'js/source/wcmlib/com.trs.floatpanel/resource/window.html';
os = pb.style;
os.position = 'absolute';
os.left = '0px';
os.top = '0px';
os.width = '100%';
os.height = '100%';
os.display = 'none';
pb.allowTransparency = true;
document.body.appendChild(pb);
});
}
if(actualTop.FloatPanel)return;
for(var name in FloatPanel){
FloatPanel[name] = function(){
var actualTop = $MsgCenter.getActualTop();
return actualTop.FloatPanel[this].apply(actualTop.FloatPanel, arguments);
}.bind(name);
}
})();
function getHeight(){
var yScroll;
if (window.innerHeight && window.scrollMaxY) {
yScroll = window.innerHeight + window.scrollMaxY;
} else if (document.body.scrollHeight > document.body.offsetHeight){
yScroll = document.body.scrollHeight;
} else {
yScroll = document.body.offsetHeight;
}
var windowHeight;
if (self.innerHeight) {
windowHeight = self.innerHeight;
} else if (document.documentElement && document.documentElement.clientHeight) {
windowHeight = document.documentElement.clientHeight;
} else if (document.body) {
windowHeight = document.body.clientHeight;
}
if(yScroll < windowHeight){
pageHeight = windowHeight;
} else {
pageHeight = yScroll;
}
return pageHeight;
}
function getWidth(){
var xScroll
if (window.innerHeight && window.scrollMaxY) {
xScroll = document.body.scrollWidth;
} else if (document.body.scrollHeight > document.body.offsetHeight){
xScroll = document.body.scrollWidth;
} else {
xScroll = document.body.offsetWidth;
}
var windowWidth
if (self.innerHeight) {
windowWidth = self.innerWidth;
} else if (document.documentElement && document.documentElement.clientHeight) {
windowWidth = document.documentElement.clientWidth;
} else if (document.body) {
windowWidth = document.body.clientWidth;
}
if(xScroll < windowWidth){
pageWidth = windowWidth;
} else {
pageWidth = xScroll;
}
return pageWidth;
}

var dialogInit = function(){
if(!window.$MsgCenter) return;
var acutalTop = $MsgCenter.getActualTop();
if(acutalTop == window || !acutalTop.wcm.MessageBox) return;
wcm.MessageBox = acutalTop.wcm.MessageBox;
wcm.FaultDialog = acutalTop.wcm.FaultDialog;
wcm.ReportDialog = acutalTop.wcm.ReportDialog;
};
dialogInit();

Ext.apply(wcm.CrashBoard.prototype, {
getWin0 : function(){
if(!window.$MsgCenter) return window;
var acutalTop = $MsgCenter.getActualTop();
if(acutalTop == window || !acutalTop.wcm.CrashBoard) return window;
return acutalTop;
},
isOpenByCB : function(){
return getParameter(wcm.CrashBoard["fromcb"]) == 1;
}
});
(function(){
if(getParameter(wcm.CrashBoard["fromcb"]) == 1) return;
var execInit = function(){
if(!window.init) return;
var search = location.search;
var params = search ? search.parseQuery() : {};
init(params);
};
var initCfg = function(){
if(!window.m_cbCfg) return;
if(m_cbCfg.btns){
var btns = m_cbCfg.btns;
for (var i = 0; i < btns.length; i++){
addBtn(btns[i]);
}
}
};
var bodyOnLoad = function(){
initCfg();
execInit();
};
Event.observe(window, 'load', bodyOnLoad, false);
var addBtn = function(btn){
var divBtns = ensureBtnContainer();
var cb = wcm.CrashBoarder.get(window);
var cpyBtn = Ext.applyIf({
renderTo : divBtns,
scope : cb,
cmd : btn["cmd"] ? closeWin.createInterceptor(btn["cmd"], cb) : closeWin
}, btn);
var btn = new wcm.Button(cpyBtn);
btn.render();
};
var m_btnsId = 'buttons_container';
var ensureBtnContainer = function(){
var divBtns = $(m_btnsId);
if(!divBtns){
divBtns = document.createElement('DIV');
divBtns.id = m_btnsId;
divBtns.align = 'center';
document.body.appendChild(divBtns);
}
return divBtns;
};
var closeWin = function(){
window.close();
};
})();

