var WCMConstants = {
DEBUG : false,
WCM_VERSION : 'WCM v6.1',
WCM_BUILDNO : 'b10001',
WCM_APPNAME : 'wcm',
WCM_ROOTPATH : '/wcm/',
WCM6_PATH : '/wcm/app/',
WCM_LOCAL_URL : '/wcm/app/localxml/',
WCM_ROMOTE_URL : '/wcm/center.do',
WCM_NOT_LOGIN_PAGE : '/wcm/console/include/not_login.htm',
OPER_TYPE_SEP : 'seperate',
WCM_EVENT_FLAG : 'wcm.CMSObjEvent',
TAB_HOST_TYPE_CHANNEL : 'channel',
TAB_HOST_TYPE_WEBSITE : 'website',
TAB_HOST_TYPE_WEBSITEROOT : 'websiteroot',
TAB_HOST_TYPE_MYFLOWDOCLIST : 'myFlowDocList',
TAB_HOST_TYPE_MYMSGLIST : 'myMsgList',
TAB_HOST_TYPE_TRSSERVERLIST : 'trsServerList',
TAB_HOST_TYPE_METAVIEW : 'metaview',
TAB_HOST_TYPE_CLASSINFO : 'classinfo',
OBJ_TYPE_DOCUMENT : 'document',
OBJ_TYPE_CHNLDOC : 'chnldoc',
OBJ_TYPE_WEBSITE : 'website',
OBJ_TYPE_WEBSITEROOT : 'websiteroot',
OBJ_TYPE_CHANNEL : 'channel',
OBJ_TYPE_CHANNELMASTER : 'channelMaster',
OBJ_TYPE_TREENODE : 'TreeNode',
OBJ_TYPE_CLSTREENODE : 'ClsTreeNode',
OBJ_TYPE_GRIDROW : 'GridRow',
OBJ_TYPE_CELL : 'GridCell',
OBJ_TYPE_UNKNOWN : 'UnKnown',
OBJ_TYPE_MYFLOWDOCLIST : 'myFlowDocList',
OBJ_TYPE_TRSSERVERLIST : 'trsServerList',
OBJ_TYPE_TRSSERVER : 'trsServer',
OBJ_TYPE_MYMSGLIST : 'myMsgList',
OBJ_TYPE_METAVIEW : 'metaview',
OBJ_TYPE_CLASSINFO : 'classinfo',
OBJ_TYPE_MAINPAGE : 'MainPage',
OBJ_TYPE_SYSTEM : 'System',
OBJ_TYPE_SEARCH : 'Search',
OBJ_TYPE_ALL : 'All',
OBJ_TYPE_ALL_CMSOBJS : 'All_CMSObjs',
OBJ_TYPE_DOCUMENTBAK : 'documentbak',
OBJ_TYPE_SITERECYCLE : 'SiteRecycle',
OBJ_TYPE_CHNLRECYCLE : 'Chnlrecycle',
OBJ_TYPE_INFOVIEWDOC : 'infoviewdoc',
OBJ_TYPE_REGION : 'region'
};
Ext.ns('WCMLANG', 'wcm.LANG');
WCMLANG = wcm.LANG;

Ext.ns('wcm.ObjectLCMonitorServer', 'wcm.ObjectLCListener');
Ext.ns('WCMLANG', 'wcm.LANG');
WCMLANG = wcm.LANG;
(function(){
var __window_flag = location.href + ";" + new Date().getTime();
try{
if(window.frameElement && window.frameElement.id=='main')__window_flag += ';#main;';
}catch(error){
}
var makeProperties = function(_config){
var oResult = {};
for(var _sPropName in _config){
if(typeof _config[_sPropName]!='function'){
oResult[_sPropName.toUpperCase()] = _config[_sPropName];
}
}
return oResult;
};
wcm.ObjectLCListener = function(_config){
_config.__genId = window.$MsgCenter.genId();
var props = makeProperties(_config);
Ext.apply(this, _config);
this.getType = function(){
return props.OBJTYPE;
}
this.getGenId = function(){
return this.__genId;
}
this.getWindowFlag = function(){
return __window_flag;
}
};
wcm.getMyFlag = function(){
return __window_flag;
};
})();
(function(){
wcm.ObjectLCMonitorServer = function(){
wcm.ObjectLCMonitorServer.superclass.constructor.call(this);
this.m_hsListeners = {};
};
Ext.extend(wcm.ObjectLCMonitorServer, Object, {
addListener : function(_listener){
if(!_listener)return this;
if(Ext.isArray(_listener)){
for(var i=0,n=_listener.length;i<n;i++){
this.addListener(_listener[i]);
}
return this;
}
if(typeof _listener.getGenId != 'function'){
_listener = new wcm.ObjectLCListener(_listener);
}
var arr = this.m_hsListeners[this.getKey(_listener)];
if(!arr){
arr = this.m_hsListeners[this.getKey(_listener)] = [];
}
arr.push(_listener);
return this;
},
getKey : function(_listener){
return _listener.getType();
},
getListeners : function(_cmsobj, eventName, _window){
var listeners = this.m_hsListeners[_cmsobj.getType()]|| [];
if(_cmsobj.getType()!=WCMConstants.OBJ_TYPE_UNKNOWN){
listeners = listeners.concat(this.m_hsListeners[WCMConstants.OBJ_TYPE_ALL]);
}
if(_cmsobj.isCMSObjType()){
listeners = listeners.concat(this.m_hsListeners[WCMConstants.OBJ_TYPE_ALL_CMSOBJS]);
}
var resultCurrWind = [];
var resultOtherWind = [];
var listener = null;
eventName = eventName.toLowerCase();
for(var i=0,n=listeners.length;i<n;i++){
listener = listeners[i];
if(listener && listener[eventName]){
try{
if(listener.getWindowFlag()==_window.wcm.getMyFlag()){
resultCurrWind.push(Ext.apply({
currWin : true
}, listener));
}else{
resultOtherWind.push(Ext.apply({
currWin : false
}, listener));
}
}catch(err){
}
}
}
return resultCurrWind.concat(resultOtherWind);
},
removeListener : function(_listener){
if(!_listener)return this;
if(Ext.isArray(_listener)){
for(var i=0,n=_listener.length;i<n;i++){
this.removeListener(_listener[i]);
}
return this;
}
if(typeof _listener.getGenId != 'function'){
_listener = new wcm.ObjectLCListener(_listener);
}
var arr = this.m_hsListeners[this.getKey(_listener)];
if(!arr)return this;
var newArr = [];
for(var i=0,n=arr.length;i<n;i++){
try{
if(arr[i].getGenId()!=_listener.getGenId()){
newArr.push(arr[i]);
}else{
arr[i]._window = null;
}
}catch(err){
}
}
this.m_hsListeners[this.getKey(_listener)] = newArr;
return this;
}
});
var __monitorServer = new wcm.ObjectLCMonitorServer();
Ext.applyIf(__monitorServer, {
setActualTop : function(){
},
genId : function(n){
this.__genId = (this.__genId || 0) + 1;
if(this.__genId<=n)this.__genId = n;
return this.__genId;
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
getMonitorServer : function(){
var actualTop = this.getActualTop();
if(actualTop == window)return __monitorServer;
return actualTop.$MsgCenter.getMsgCenter();
}
});
var _$MsgCenter = __monitorServer.getMonitorServer();
window.$MsgCenter = {
genId : function(n){
return _$MsgCenter.genId(n);
},
getMsgCenter : function(){
return _$MsgCenter;
},
getActualTop : function(){
return _$MsgCenter.getActualTop();
},
getListeners : function(_cmsobj, eventName, _window){
return _$MsgCenter.getListeners(_cmsobj, eventName, _window);
},
getListener : function(sid){
return window.__forCollectHash[sid];
},
addListener : function(_listener){
_listener = new wcm.ObjectLCListener(_listener);
var objTypes = _listener.getType();
if(Ext.isArray(objTypes)){
for (var i = 0; i < objTypes.length; i++){
if(!objTypes[i]) continue;
this.addListener(Ext.applyIf({
objType : objTypes[i]
}, _listener));
}
return;
}
window.__forCollect.push(_listener);
window.__forCollectHash[_listener.sid || _listener.getGenId()] = _listener;
return _$MsgCenter.addListener(_listener);
},
removeListener : function(_listener){
return _$MsgCenter.removeListener(_listener);
},
destroy : function(){
if(this.keyProvider){
_$MsgCenter.keyProvider = this.lastKeyProvider;
this.lastKeyProvider = null;
}
delete __monitorServer;
delete _$MsgCenter;
delete this.$main;
try{
this.getActualTop().focus();
}catch(err){}
},
setKeyProvider : function(provider){
this.keyProvider = true;
this.lastKeyProvider = _$MsgCenter.keyProvider;
_$MsgCenter.keyProvider = provider;
},
keyDownEanbled : function(){
return (_$MsgCenter.winCountInOpen || 0) <= 0;
},
cancelKeyDown : function(){
var count = _$MsgCenter.winCountInOpen || 0;
count++;
_$MsgCenter.winCountInOpen = count;
},
enableKeyDown : function(){
var count = _$MsgCenter.winCountInOpen || 0;
if(count <= 0) return;
count--;
_$MsgCenter.winCountInOpen = count;
}
}
$MsgCenter.on = $MsgCenter.addListener;
$MsgCenter.un = $MsgCenter.removeListener;
window.__forCollect = [];
window.__forCollectHash = {};
Ext.EventManager.on(window, 'unload', function(){
var lStartTime = new Date().getTime();
if(window.__forCollect.length>0){
window.$MsgCenter.un(window.__forCollect);
}
var lEndTime = new Date().getTime();
window.$MsgCenter.destroy();
window.$MsgCenter = null;
window.__actualTop = null;
window.__forCollect = null;
window.__forCollectHash = null;
});
Ext.EventManager.on(document, 'keydown', function(ev){
try{
var msgCenter = window.$MsgCenter.getMsgCenter();
if(!window.$MsgCenter.keyDownEanbled())return;
var event = ev.browserEvent;
var eTarget = Event.element(event);
var bIsTextInput = true;
if(eTarget != null){
bIsTextInput = (eTarget.tagName == 'INPUT' && eTarget.type != 'checkbox')
||(eTarget.tagName == 'TEXTAREA')
||(eTarget.tagName == 'SELECT');
}
if(bIsTextInput)return;
try{
if(new wcm.SystemObj({}, {extEvent:ev}).onkeydown()==false)return;
}catch(err){}
var worker = msgCenter.keyProvider;
if(!worker || (worker.checkSpecSrcElement 
&& worker.checkSpecSrcElement(eTarget)) === false) {
return;
}
var mapping = {33 : 'PgUp', 34 : 'PgDn', 35 : 'Home', 36 : 'End'};
if(event.ctrlKey){
var c = mapping[event.keyCode] || '';
if(worker['ctrl'+c]&&worker['ctrl'+c](event)==false){
Event.stop(event);
return false;
}
return;
}
if(event.altKey)return;
var fn = 'key' + String.fromCharCode(event.keyCode).toUpperCase();
if(event.keyCode==46){
fn = event.shiftKey ? 'keyShiftdelete' : 'keyDelete';
}
if(worker[fn]&&worker[fn](event)==false){
Event.stop(event);
return false;
}
}catch(err){
}finally{}
});
})();

Ext.ns('wcm.CMSObj', 'wcm.CMSObjs', 'wcm.CMSObjEvent', 'wcm.Context');
Ext.ns('CMSObj');
(function(){
var events = ['add', 'afteradd', 'select', 'afterselect',
'edit', 'afteredit', 'delete', 'afterdelete', 'unselect', 'afterunselect',
'save', 'aftersave', 'aftermove'
];
wcm.isEvent = function(event){
return event.className == WCMConstants.WCM_EVENT_FLAG;
}
wcm.CMSObjEvent = function(info){
Ext.apply(this, info);
this._from = wcm.getMyFlag();
}
wcm.CMSObjEvent.prototype = {
className : WCMConstants.WCM_EVENT_FLAG || 'wcm.CMSObjEvent',
getObjs : function(){
return this.objs;
},
getIds : function(){
var objs = this.objs;
if(objs==null || objs.length()==0)return [];
return objs.getIds();
},
length : function(){
return this.objs?this.objs.length():0;
},
getContext : function(){
return this.context;
},
setContext : function(context){
this.context = makeContext(context);
},
getHost : function(){
return this.context?this.context.getHost():null;
},
getObj : function(){
return this.objs.getAt(0) || this.getHost();
},
getObjsOrHost : function(){
var objs = this.objs;
if(objs==null || objs.length()==0)return this.getHost();
return objs;
},
getCMSObj : function(){
var obj = this.objs.getAt(0);
if(obj!=null && obj.isCMSObjType())return obj;
return this.getHost();
},
from : function(){
return this._from;
},
fromMain : function(){
return this._from.indexOf(';#main;')!=-1;
}
}
var makeProperties = function(_config){
var oResult = {};
for(var _sPropName in _config){
if(_sPropName!=null && typeof _config[_sPropName]!='function'){
oResult[_sPropName.toUpperCase()] = _config[_sPropName];
}
}
return oResult;
};
wcm.Context = function(_context){
var props = makeProperties(_context);
Ext.apply(this, _context);
props.HOST = props.HOST || _context;
this.getRelateType = function(){
return props.RELATETYPE;
}
this.getHost = function(){
return new wcm.CMSObj(props.HOST);
};
this.get = function(_key){
_key = _key.toUpperCase();
var params = props.PARAMS || {};
return props[_key] || params[_key];
};
this.set = function(_props){
Ext.apply(this, _props);
Ext.apply(props, makeProperties(_props));
};
}
function makeContext(_context){
if(_context==null || _context.getRelateType)
return _context;
return new wcm.Context(_context);
}
function __fireEventWithEventObj(eventName, eventObject){
var listeners = $MsgCenter.getListeners(this, eventName, window);
eventObject.type = eventName;
for(var i=0;i<listeners.length;i++){
try{
var oListener = listeners[i];
if(!oListener || !oListener[eventName])continue;
if(eventObject.cancelBubble && !oListener.currWin)continue;
if((oListener[eventName]).call(this, eventObject)===false)
return false;
}catch(err){
if(Ext.isDebug()){
throw err;
}
}
}
}
var fireEvent = function(_sEventName){
if(!window.$MsgCenter)return true;
var eventObject = new wcm.CMSObjEvent({
context : makeContext(this.context),
objs : this
});
if( __fireEventWithEventObj.apply(this, ['before' + _sEventName, eventObject])===false)return false;
if( __fireEventWithEventObj.apply(this, [_sEventName, eventObject])===false)return false;
return true;
}
var createBinding = function(fn){
var args = Array.prototype.slice.call(arguments, 1);
return function() {
return fn.apply(this, args);
};
}
var setContext = function(_context){
this.context = makeContext(_context);
};
var applyContext = function(_context){
var context = this.context;
context != null ? context.set(_context) : this.setContext(_context);
};
wcm.CMSObj = function(_config, _context){
var props = makeProperties(_config);
Ext.apply(this, _config);
this.objId = props.OBJID;
this.objType = this.objType || props.OBJTYPE;
this.properties = props;
this.context = makeContext(_context);
this.setContext = setContext;
this.applyContext = applyContext;
var caller = this;
for(var i=0;i<events.length;i++){
this[events[i]] = createBinding(fireEvent, events[i]);
}
this.addEvents = function(events){
if(Ext.isArray(events)){
for(var i=0;i<events.length;i++){
this[events[i]] = createBinding(fireEvent, events[i]);
}
return;
}
this[events] = createBinding(fireEvent, events);
}
}
Ext.apply(CMSObj, {
createEvent : function(cmsobj, context){
if(!cmsobj || !cmsobj.objType)
return null;
if(!cmsobj.isCMSObj){
var items = cmsobj.items || cmsobj;
delete cmsobj.items;
cmsobj = new CMSObj.createEnumsFrom(cmsobj, context);
cmsobj.addElement(items);
}
return new wcm.CMSObjEvent({
context : makeContext(context || cmsobj.context),
objs : cmsobj
});
}
});
})();
Ext.extend(wcm.CMSObj, Object, {
isCMSObj : function(){
return true;
},
isCMSObjType : function(){
var objType = this.objType;
return objType != WCMConstants.OBJ_TYPE_UNKNOWN
&& objType != WCMConstants.OBJ_TYPE_GRIDROW
&& objType != WCMConstants.OBJ_TYPE_CELL
&& objType != WCMConstants.OBJ_TYPE_TREENODE
&& objType != WCMConstants.OBJ_TYPE_MYFLOWDOCLIST
&& objType != WCMConstants.OBJ_TYPE_MYMSGLIST
&& objType != WCMConstants.OBJ_TYPE_TRSSERVERLIST
&& objType != WCMConstants.OBJ_TYPE_MAINPAGE;
},
privateMe : function(type){
this.objType = type;
this.isCMSObjType = function(){return false;}
return this;
},
getAt : function(index){
return this;
},
getIds : function(){
return [this.getId()];
},
getObjs : function(){
return [this];
},
length : function(){
return 1;
},
size : function(){
return this.length();
},
isCMSObjs : function(){
return false;
},
getType : function(){
return this.objType;
},
getTypeName : function(){
return this.objType;
},
getIntType : function(){
switch(this.objType.toUpperCase()){
case WCMConstants.OBJ_TYPE_DOCUMENT.toUpperCase():
case WCMConstants.OBJ_TYPE_CHNLDOC.toUpperCase():
return 605;
case WCMConstants.OBJ_TYPE_CHANNEL.toUpperCase():
case WCMConstants.OBJ_TYPE_CHANNELMASTER.toUpperCase():
return 101;
case WCMConstants.OBJ_TYPE_WEBSITE.toUpperCase():
return 103;
case WCMConstants.OBJ_TYPE_WEBSITEROOT.toUpperCase():
return 1;
}
return -1;
},
getId : function(){
return this.objId;
},
getProperties : function(){
return this.properties;
},
getProperty : function(_sPropName, _sDefault){
return this.properties[_sPropName.toUpperCase()] || _sDefault || null;
},
getPropertyAsString : function(_sPropName, _sDefault){
return this.properties[_sPropName.toUpperCase()] || _sDefault || '';
},
getPropertyAsInt : function(_sPropName, _nDefault){
return parseInt(this.properties[_sPropName.toUpperCase()] || (''+ _nDefault), 10);
},
getClass : function(){
return CMSObj.getRegistered(this.objType);
},
toString : function(){
return this.getTypeName() + "[ID=" + this.getId() + "]";
}
});
wcm.CMSObjs = function(_config, _context){
wcm.CMSObjs.superclass.constructor.call(this, _config, _context);
this.objId = 0;
this.objType = this.objType || _config.objType;
this.m_objs = [];
this.addElement(_config.items);
}
Ext.extend(wcm.CMSObjs, wcm.CMSObj, {
createElement : function(info){
Ext.applyIf(info, {
objType : this.objType
});
return CMSObj.createFrom(info, this.context)
},
isCMSObjs : function(){
return true;
},
length : function(){
return this.m_objs.length;
},
addElement : function(_oCmsObjs){
if(_oCmsObjs==null)return;
if(Ext.isArray(_oCmsObjs)){
for(var i=0,n=_oCmsObjs.length;i<n;i++){
this.m_objs.push(this.createElement(_oCmsObjs[i]));
}
return;
}
this.m_objs.push(this.createElement(_oCmsObjs));
return this;
},
getAt : function(index){
return this.m_objs[index];
},
remove : function(cmsobj){
if(cmsobj.getType()!=this.getType())return cmsobj;
var result = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
if(this.m_objs[i].getId()!=cmsobj.getId()){
result.push(this.m_objs[i]);
}
}
this.m_objs = result;
return cmsobj;
},
removeAt : function(index){
var cmsobj = this.m_objs[index];
this.m_objs.splice(index, 1);
return cmsobj;
},
getIds : function(){
var rstIds = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
rstIds.push(this.m_objs[i].getId());
}
return rstIds;
},
getProperty : function(_sPropName, _sDefault){
var rst = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
rst.push(this.m_objs[i].getProperty(_sPropName, _sDefault));
}
return rst;
},
getPropertys : function(_sPropName, _sDefault){
var rst = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
rst.push(this.m_objs[i].getProperty(_sPropName, _sDefault));
}
return rst;
},
getPropertyAsString : function(_sPropName, _sDefault){
var rst = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
rst.push(this.m_objs[i].getPropertyAsString(_sPropName, _sDefault));
}
return rst;
},
getPropertyAsInt : function(_sPropName, _nDefault){
var rst = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
rst.push(this.m_objs[i].getPropertyAsInt(_sPropName, _nDefault));
}
return rst;
},
getObjs : function(){
return this.m_objs;
},
toString : function(){
var arr = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
arr.push(this.m_objs[i].toString());
}
return this.getTypeName() + "[objs=" + arr.join() + "]";
}
});
(function(){
var __extendClasses = {};
Ext.apply(CMSObj, {
register : function(_sClassType, _fCons){
__extendClasses[_sClassType] = _fCons;
},
getRegistered : function(_sClassType){
return __extendClasses[_sClassType];
},
createFrom : function(info, _context){
var sClass = CMSObj.getRegistered(info.objType);
var fClass = null;
try{
if(sClass!=null && (fClass=eval(sClass))){
return new fClass(info, _context);
}
}catch(err){
}
return new wcm.CMSObj(info, _context);
},
createEnumsFrom : function(info, _context){
var sClass = CMSObj.getRegistered(info.objType);
var fClass = null;
try{
if(sClass!=null && (fClass=eval(sClass+'s'))){
return new fClass(info, _context);
}
}catch(err){
}
return new wcm.CMSObjs(info, _context);
}
});
})();
Ext.apply(CMSObj, {
newObj : function(info, wcmEvent){
info = Ext.apply({
objType : wcmEvent.objs.getType()
}, info);
return CMSObj.createFrom(info, wcmEvent.context);
},
afteradd : function(event){
return function(objInfo){
if(Ext.isSimpType(objInfo)){
objInfo = {objId : objInfo};
}else if(Ext.isTrans(objInfo)){
objInfo = {objId : Ext.result(objInfo)};
}
objInfo = Ext.applyIf(objInfo || {}, {objId : 0, objType : event.objs.getType()});
var cmsobj = CMSObj.createFrom(objInfo, event.context);
cmsobj.afteradd();
}
},
afteredit : function(event){
return function(){
event.getObjsOrHost().afteredit();
}
},
afterdelete : function(event){
return function(){
event.getObjsOrHost().afterdelete();
}
}
});
Ext.ns('wcm.MainPage');
wcm.MainPage = function(info, context){
this.objType = WCMConstants.OBJ_TYPE_MAINPAGE;
wcm.MainPage.superclass.constructor.call(this, info, context);
this.addEvents(['beforeinit', 'afterinit', 'destroy', 'afterdestroy',
'afteredit', 'refresh', 'afterrefresh', 'operpanelhide', 'operpanelshow', 'redirect', 'contextmenu']);
}
Ext.extend(wcm.MainPage, wcm.CMSObj);
CMSObj.register(WCMConstants.OBJ_TYPE_MAINPAGE, 'wcm.MainPage');
Ext.ns('wcm.CurrPage');
WCMConstants.OBJ_TYPE_CURRPAGE = 'CurrPage_' + wcm.getMyFlag();
wcm.CurrPage = function(info, context){
this.objType = WCMConstants.OBJ_TYPE_CURRPAGE;
wcm.CurrPage.superclass.constructor.call(this, info, context);
this.addEvents(['beforeinit', 'afterinit', 'destroy', 'afterdestroy',
'afteredit', 'refresh', 'afterrefresh', 'redirect', 'contextmenu']);
}
Ext.extend(wcm.CurrPage, wcm.CMSObj);
CMSObj.register(WCMConstants.OBJ_TYPE_CURRPAGE, 'wcm.CurrPage');
Ext.ns('wcm.SystemObj');
wcm.SystemObj = function(info, context){
this.objType = WCMConstants.OBJ_TYPE_SYSTEM;
wcm.SystemObj.superclass.constructor.call(this, info, context);
this.addEvents(['onkeydown']);
}
Ext.extend(wcm.SystemObj, wcm.CMSObj);
CMSObj.register(WCMConstants.OBJ_TYPE_SYSTEM, 'wcm.SystemObj');
(function(){
$MsgCenter.$main = function(context){
var info = {
objId : 0,
href : location.href,
params : Ext.Json.toUpperCase(location.search.parseQuery())
};
return new wcm.MainPage(info, context || window.mainContext);
}
$MsgCenter.$currPage = function(context){
var info = {
objId : 0,
href : location.href,
params : Ext.Json.toUpperCase(location.search.parseQuery())
};
return new wcm.CurrPage(info, context);
};
})();

Ext.ns('wcm.AuthServer');
(function(){
function isAdmin(){
return $MsgCenter.getActualTop().global_IsAdmin;
} var arr = [];
for(var i=0;i<64;i++){
arr.push([0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]);
}
arr[14] = [11,13,12,15,16,17,19,18,53,55,0,0,0,0,0,0,0,0,0,0];
arr[24] = [21,23,22,25,24,21,24,28,0,0,0,0,0,0,0,0,0,0,0,0];
arr[25] = [21,23,21,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
arr[30] = [18,31,32,33,33,34,38,39,34,34,34,35,36,37,56,54,8,0,0,0];
arr[33] = [18,18,33,33,56,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
arr[34] = [18,32,33,33,38,39,35,36,37,56,54,0,0,0,0,0,0,0,0,0];
arr[35] = [36,37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
m_pSimilarIndexs = arr;
var m_pSpecialIndexs = [38,39];
function _hasSimlarRight(srg, nrix) {
var oas = wcm.AuthServer;
if(m_pSimilarIndexs.length <= nrix)return false;
var exitRecursion = false;
if(!oas.sriCache){
oas.sriCache = {};
exitRecursion = true;
}
if(oas.sriCache[nrix])return false;
oas.sriCache[nrix] = true;
var arr = m_pSimilarIndexs[nrix] || [];
var bResult = false;
for (var i = 0; i < arr.length; i++) {
var nsi = arr[i];
if (nsi == 0)break;
if (oas.hasRight(srg, nsi)) {
bResult = true;
break;
}
} 
if(exitRecursion)oas.sriCache = null;
return bResult;
}
function _hasRight(srg, rix){
var len = srg.length;
if(rix>=len)return false;
var nri = len-1-rix;
return srg.charAt(nri)=='1';
}
Ext.apply(wcm.AuthServer, {
isAdmin : isAdmin,
getRightValue : function(){
return getParameter("RightValue") || '0';
},
hasRight : function(srg, rix){
rix = parseInt(rix, 10);
if(rix == -1)return true;
if(rix == -2){
if(isAdmin())return true;
return false;
}
if(!srg)return false;
if (rix == 64 && srg.indexOf("1") >= 0)
return true;
if (_hasRight(srg, rix))return true;
if (_hasSimlarRight(srg, rix))return true;
return false;
},
checkRight : function(rightValue, rightIndex){
if(rightIndex == -1)return true;
if(!rightValue) return false;
if(Ext.isNumber(rightIndex)){
return this.hasRight(rightValue, rightIndex);
}else if(Ext.isString(rightIndex)){
var rightIndexs = rightIndex.split(",");
if(rightIndexs.length>1){
for (var i = 0; i < rightIndexs.length; i++){
if(this.checkRight(rightValue, rightIndexs[i]))
return true;
}
return false;
}
var rightIndexs = rightIndex.split("-");//仅为负数的时候，不应该作为区间判断，如-2
if(rightIndexs[0] == '' || rightIndexs.length==1){
return this.hasRight(rightValue, parseInt(rightIndex, 10));
}
for (var i = parseInt(rightIndexs[0]); i <= parseInt(rightIndexs[1]); i++){
if(this.hasRight(rightValue, i))
return true;
}
return false;
}else if(Ext.isArray(rightIndex)){
for (var i = 0; i < rightIndex.length; i++){
if(this.checkRight(rightValue, rightIndex[i]))
return true;
}
return false;
}
return false;
},
mergeRights : function(arr){
var maxLen = 0, rst = '', tmpidx = -1;
for(var i=0;i<arr.length;i++){
arr[i] = arr[i] || '';
if(arr[i].length>maxLen){
maxLen = arr[i].length;
}
}
for(var i=0;i<maxLen;i++){
var b = maxLen>i&&m_pSpecialIndexs.include(maxLen-1-i);
var c0 = b?'0':'1', c1 = b?'1':'0';
for(var j=0;j<arr.length;j++){
tmpidx = i+arr[j].length-maxLen;
if(tmpidx>=0&&arr[j].charAt(tmpidx)==c1){
c0 = c1;
break;
}
}
rst += c0;
}
return rst;
}
});
})();

