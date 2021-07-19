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
Element.remove(dom);
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
closable : true,
maskable : true,
closeAction : 'close',
draggable : true,
initComponent : function(){
wcm.Panel.superclass.initComponent.apply(this, arguments);
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
this.getElement().style.zIndex = $MsgCenter.genId(100);
if(this.draggable){
var caller = this;
var win = this.getWin();
if(win.wcm.dd){
this.dragger = Ext.apply(new win.wcm.dd.DragDrop({id : id, handleElId : this.header}), {
drag : function(x, y, event){
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
var zIndex = $MsgCenter.genId(100)
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
'<div class="wcm-btn wcm-btn-left" id="{0}">',
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
new Insertion.Bottom($(this.container), String.format(htmlTemplate, id, text));
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
{text : this.buttonText[fn["yes"]?"no":"cancel"], cmd : fn["no"] || fn["cancel"]}
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
if(_fault.code) return _fault.code;
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
Ext.Msg=Ext.Msg||{};
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
var msg = msg+'<br><br><div style="font-size:12px;color:gray;">' + String.format("本窗口在<span id='_dialog_timecounter_' style='font-size:11px;padding:0 2px;color:red'>{0}</span>秒内将自动消失",iSeconds) + '.</div>';
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
var frmTemplate = '<iframe src="{0}" id="{1}" style="height:100%;width:100%;" frameborder="0" onload="wcm.CrashBoard.contentLoaded(\'{2}\', this);"></iframe>';
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

//dialog适配器
var dialogInit = function(){
	if(!window.$MsgCenter) return;

	var acutalTop = $MsgCenter.getActualTop();

	if(acutalTop == window || !acutalTop.wcm.MessageBox) return;

	/**
	 * @class wcm.MessageBox
	*/
	wcm.MessageBox = acutalTop.wcm.MessageBox;

	/**
	 * @class wcm.FaultDialog
	*/
	wcm.FaultDialog = acutalTop.wcm.FaultDialog;

	/**
	 * @class wcm.ReportDialog
	*/
	wcm.ReportDialog = acutalTop.wcm.ReportDialog;

	//wcm.LANG = acutalTop.wcm.LANG;
};
dialogInit();
