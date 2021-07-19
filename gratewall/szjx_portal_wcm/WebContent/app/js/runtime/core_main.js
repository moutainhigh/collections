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

Ext.ns('wcm.SysOpers');
var sysOpersInit = function(){
var m_myOpersBuffer = {};
Ext.applyIf(wcm.SysOpers, {
resolutionRelateInfo : {
enable : true,
hostDisplayNum : 3,
objDisplayNum : 5,
smallResolution : screen.width <= 1024
},
getDisplayNum : function(operInfo){
var caller = wcm.SysOpers;
var info = caller.resolutionRelateInfo;
if(info.enable && info.smallResolution){
if(operInfo.isHost) return info.hostDisplayNum;
return info.objDisplayNum;
}
return operInfo.displayNum;
},
_preparedTypes : {},
prepareOpers : function(type){
var caller = wcm.SysOpers;
type = type.toLowerCase();
if(caller._preparedTypes[type])return;
caller._preparedTypes[type] = true;
var arrOpers = Object.deepClone(m_myOpersBuffer[type]) || [];
var result = [];
if(top.m_CustomizeInfo && top.m_CustomizeInfo.operator){
var hasFound = false;
for(var name in top.m_CustomizeInfo.operator){
if(name.toLowerCase() == type.toLowerCase()){
hasFound = true;
var operKey = top.m_CustomizeInfo.operator[name].split(";")[1].split(":")[1];
for(var m=0,nLength = operKey.split(",").length; m < nLength; m++ ){
for(var n=0, nOperLength=arrOpers.length; n<nOperLength; n++){
if(arrOpers[n].key == operKey.split(",")[m]){
result.push(arrOpers[n]);
break;
}
}
}
}
}
if(!hasFound){
wcm.SysOpers.sortByOrderAsc(arrOpers,result);
}
}else{
wcm.SysOpers.sortByOrderAsc(arrOpers,result);
}
caller.opers[type] = result;
},
sortByOrderAsc : function(arrOpers,result){
while(arrOpers.length>0){
var minOrder = parseFloat(arrOpers[0].order, 10) || (result.length+1);
var index = 0;
for(var i=1, n=arrOpers.length; i<n; i++){
var tmpOrder = parseFloat(arrOpers[i].order, 10);
if(tmpOrder && tmpOrder < minOrder){
minOrder = tmpOrder;
index = i;
}
}
if(arrOpers[index]){
result.push(arrOpers[index]);
}
arrOpers.splice(index, 1);
}
},
getOperItem : function(o, p, wcmEvent){
var caller = wcm.SysOpers;
var tmpOper = caller._findOperItem(o, p);
if(!wcmEvent)return tmpOper;
if(!caller._isVisible(wcmEvent, tmpOper))return null;
if(Ext.isFunction(tmpOper.isVisible) && !tmpOper.isVisible(wcmEvent)){
return null;
}
return tmpOper;
},
_findOperItem : function(_type, _key){
var caller = wcm.SysOpers;
var type = (_type||'').toLowerCase();
var opers = caller.getOpersByType(type);
var key = (_key||'').toLowerCase();
for(var i=0,n=opers.length;i<n;i++){
if(opers[i].key.equalsI(key))
return opers[i];
}
},
_findOperItemInBuffer : function(_type, _key){
var caller = wcm.SysOpers;
var type = (_type||'').toLowerCase();
var opers = m_myOpersBuffer[type];
var key = (_key||'').toLowerCase();
for(var i=0,n=opers.length;i<n;i++){
if(opers[i].key.equalsI(key))
return opers[i];
}
},
exec : function(o, p, q){
var caller = wcm.SysOpers;
if(Ext.isString(o)){
return caller.exec0(caller._findOperItem(o, p), q);
}
return caller.exec0(o, p);
},
exec0 : function(operItem, event){
if(!operItem)return;
if(Ext.isFunction(operItem.fn)){
return operItem.fn.apply(operItem, [event, operItem]);
}
},
getOpersByType : function(type){
if(!type)return[];
wcm.SysOpers.prepareOpers(type);
return wcm.SysOpers.opers[type.toLowerCase()] || [];
},
getOpers : function(info){
return wcm.SysOpers.getOpersByInfo(info);
},
getOpersByInfo : function(info, event){
var right = info.right;
var type = info.type;
var wcmEvent = event;
if(wcm.isEvent(info)){
var host = info.getObjsOrHost();
if(host==null)return[[], []];
right = host.getPropertyAsString('right', '');
type = host.getType();
if(host.length()>1){
type = type + 's';
}
wcmEvent = info;
}
var caller = wcm.SysOpers;
var opers = caller.getOpersByType(type);
if(opers.length==0){
return [[], []];
}
var activeOpers = [];
for(var i=0,n=opers.length;i<n;i++){
var tmpOper = opers[i];
if(!caller._isVisible(info, tmpOper))continue;
if(Ext.isFunction(tmpOper.isVisible) && !tmpOper.isVisible(wcmEvent)){
continue;
}
activeOpers.push(tmpOper);
}
var cnt = 0, index=0, n=activeOpers.length;
info.displayNum = caller.getDisplayNum(info) || activeOpers.length;
var opers1 = [], opers2 = [];
for(;cnt<info.displayNum&&index<n;index++){
var tmpOper = activeOpers[index];
if(caller.isSeparator(tmpOper, true))continue;
cnt++;
opers1.push(tmpOper);
}
var bLastSep = true;
for(;index<n;index++){
var tmpOper = activeOpers[index];
if(bLastSep && (bLastSep=caller.isSeparator(tmpOper)))continue;
if(bLastSep && !info.frompanel)continue;
opers2.push(tmpOper);
}
return [opers1, opers2];
},
getOperByQuickKey : function(wcmEvent, quickKey, info){
if(!quickKey)return null;
if(!info){
var host = wcmEvent.getObjsOrHost();
if(host==null)return null;
right = host.getPropertyAsString('right', '');
type = host.getType();
if(host.length()>1){
type = type + 's';
}
}else{
right = info.right;
type = info.type;
}
var caller = wcm.SysOpers;
var opers = caller.getOpersByType(type.toLowerCase());
if(opers==null)return null;
for(var i=0,n=opers.length;i<n;i++){
var tmpOper = opers[i];
if(!caller._isVisible(info || wcmEvent, tmpOper))continue;
if(Ext.isFunction(tmpOper.isVisible) && !tmpOper.isVisible(wcmEvent)){
continue;
}
if(Ext.isArray(tmpOper.quickKey)){
if(tmpOper.quickKey.includeI(quickKey))return tmpOper;
}
else if(quickKey.equalsI(tmpOper.quickKey))return tmpOper;
}
return null;
},
getQuickKeys : function(){
var result = {};
var caller = wcm.SysOpers;
for(var type in m_myOpersBuffer){
var opers = m_myOpersBuffer[type];
if(opers==null)continue;
for(var i=0,n=opers.length;i<n;i++){
var tmpOper = opers[i];
if(!tmpOper || !tmpOper.quickKey)continue;
if(Ext.isArray(tmpOper.quickKey)){
tmpOper.quickKey.each(function(key){
result[key] = true;
});
continue;
}
result[tmpOper.quickKey] = true;
}
}
return result;
},
isSeparator : function(tmpOper, mustShow){
return tmpOper.key==WCMConstants.OPER_TYPE_SEP && (!mustShow || !tmpOper.mustshow);
},
_isVisible : function(info, tmpOper){
var right = info.right;
if(wcm.isEvent(info)){
right = info.getCMSObj().getPropertyAsString('right', '');
}
return this.isSeparator(tmpOper) || wcm.AuthServer.hasRight(right, tmpOper.rightIndex);
},
_forceGetOpers : function(type){
var caller = wcm.SysOpers;
var type = type.toLowerCase();
var opers = m_myOpersBuffer[type];
if(opers==null){
opers = m_myOpersBuffer[type] = [];
}
return opers;
},
register : function(info){
var items = info.items || {};
var caller = wcm.SysOpers;
caller.opers = caller.opers || {};
if(Ext.isArray(items)){
items.each(function(item){
item = Ext.applyIf(item, info);
caller._register(item);
});
return caller;
}
items = Ext.applyIf(items, info);
if(items.type){
caller._preparedTypes[items.type] = null;
}
caller._register(items);
return caller;
},
_register : function(item){
var caller = wcm.SysOpers;
delete item.items;
var opers = caller._forceGetOpers(item.type);
var key = item.key;
opers.push(item);
},
unregister : function(_type, _key){
if(!_type || !_key) return;
var opers = m_myOpersBuffer[_type];
if(!opers) return;
for(var i=0;i<opers.length;i++){
if(!opers[i]) continue;
if(opers[i]['key'] == _key) break;
}
if(i >= opers.length) return;
opers.splice(i, 1);
var caller = wcm.SysOpers;
var opers = (caller.opers || {})[_type];
if(!opers) return;
for(var i=0;i<opers.length;i++){
if(!opers[i]) continue;
if(opers[i]['key'] == _key) break;
}
if(i >= opers.length) return;
opers.splice(i, 1);
},
unregisterAll : function(){
var caller = wcm.SysOpers;
for(var type in caller.opers){
delete caller.opers[type];
}
caller.opers = null;
m_myOpersBuffer = {};
},
myCreateInterceptor : function(thisFcn, fcn, scope){
if(typeof fcn != "function"){
return thisFcn;
}
var method = thisFcn;
return function() {
var result = fcn.apply(scope || this || window, arguments);
if(Ext.isBoolean(result)){
return result;
}
return method.apply(this || window, arguments);
};
},
createInterceptor : function(item){
var caller = wcm.SysOpers;
if(!Ext.isFunction(item.isVisible))return;
var operItem = caller._findOperItemInBuffer(item.type, item.key);
if(operItem==null)return;
operItem.isVisible = operItem.isVisible || function(){
return true;
};
operItem.isVisible = caller.myCreateInterceptor(operItem.isVisible, item.isVisible);
}
});
};
sysOpersInit();

Ext.ns('wcm.TabManager');
Ext.applyIf(wcm.TabManager, {
tabs : {},
tabsInOrder : {},
buffers : {},
_preparedTypes : {},
prepareTabs : function(hostType, jsonTabs){
var caller = wcm.TabManager;
hostType = hostType.toLowerCase();
if(caller._preparedTypes[hostType])return;
caller._preparedTypes[hostType] = true;
var arrTabs = caller.buffers[hostType];
var result = [];
caller.tabsInOrder[hostType] = result;
if(!arrTabs) return;
while(arrTabs.length>0){
var minOrder = parseFloat(arrTabs[0].order, 10) || (result.length+1);
var index = 0;
for(var i=1, n=arrTabs.length; i<n; i++){
var tmpOrder = parseFloat(arrTabs[i].order, 10);
if(tmpOrder && tmpOrder< minOrder){
minOrder = tmpOrder;
index = i;
}
}
var tabItem = jsonTabs[arrTabs[index].type.toLowerCase()];
if(tabItem){
result.push(tabItem);
}
arrTabs.splice(index, 1);
}
},
getTabsByInfo : function(hostType, bInOrder, context){
var caller = wcm.TabManager;
var tabs = caller.getTabs(hostType, bInOrder, context);
var result = [];
context = caller.buildContext(context);
var items = tabs.items;
for (var i = items.length - 1; i >= 0; i--){
var tab = tabs.items[i];
if(!caller._isVisible(tab, context)){
items.splice(i, 1);
delete tabs[tab.type.toLowerCase()];
}
}
return tabs;
},
getTabs : function(hostType, bInOrder, context){
var caller = wcm.TabManager;
hostType = hostType.toLowerCase();
var tabs = caller.getTabs0(hostType, context);
if(!bInOrder)return Ext.apply({}, tabs);
caller.prepareTabs(hostType, tabs);
return Ext.applyIf({
items : caller.tabsInOrder[hostType]
}, tabs);
},
getTabs0 : function(hostType, context){
if(!hostType)return {};
var caller = wcm.TabManager;
context = caller.buildContext(context);
var tabs = caller.tabs[hostType.toLowerCase()];
var result = {};
if(tabs==null)return result;
var tab = null;
for(var type in tabs){
tab = tabs[type];
tab.hostType = hostType;
tab = context && tab.wrapper ? tab.wrapper(context, tab) : tab;
result[tab.type.toLowerCase()] = tab;
}
return result;
},
getTab : function(hostType, type, context){
if(!hostType || !type)return {};
var caller = wcm.TabManager;
var tabs = caller.getTabs0(hostType, context);
return tabs[type.toLowerCase()];
},
_getParameter : function(params, key){
return params[key.toUpperCase()];
},
buildContext : function(context){
if(!Ext.isString(context))return context;
var caller = wcm.TabManager;
var params = Ext.Json.toUpperCase(context.parseQuery());
var bIsChannel = !!caller._getParameter(params, "ChannelId");
var bIsWebSite = !!caller._getParameter(params, "SiteId");
var hostType = caller._getParameter(params, "HostType") || 
(bIsChannel ? WCMConstants.OBJ_TYPE_CHANNEL : 
(bIsWebSite ? WCMConstants.OBJ_TYPE_WEBSITE : 
WCMConstants.OBJ_TYPE_WEBSITEROOT));
var intHostType =caller._getParameter(params, "IntHostType") || 
(bIsChannel ? 101 : 
(bIsWebSite ? 103 : 1));
var hostId = caller._getParameter(params, "ChannelId") 
|| caller._getParameter(params, "SiteId")
|| caller._getParameter(params, "RootId")
|| caller._getParameter(params, "SiteType");
var tabHostType = caller._getParameter(params, "TabHostType") ||
(bIsChannel ? WCMConstants.TAB_HOST_TYPE_CHANNEL : 
(bIsWebSite ? WCMConstants.TAB_HOST_TYPE_WEBSITE :
WCMConstants.TAB_HOST_TYPE_WEBSITEROOT));
return {
isChannel : bIsChannel,
host : {
objType : hostType,
objId : hostId,
intObjType : intHostType,
right : caller._getParameter(params, "RightValue"),
isVirtual : caller._getParameter(params, "IsVirtual")
},
tabHostType : tabHostType,
params : params
};
},
_isVisible : function(tab, context){
var right = context.host.right || context.right;
return wcm.AuthServer.checkRight(right, tab.rightIndex);
},
getDefaultTab : function(hostType, context){
var caller = wcm.TabManager;
context = caller.buildContext(context);
var tabs = caller.getTabs(hostType, true, context);
for(var type in tabs){
var tab = tabs[type];
if(tab==null || !caller.isTabItem(tab))continue;
if(!caller._isVisible(tab, context)) continue;
if(Ext.isFunction(tab.isVisible) 
&& !tab.isVisible(context))continue;
if(caller.isDefault(tab, hostType))
return tab;
}
if(!tabs.items)return null;
for(var i=0,n=tabs.items.length;i<n;i++){
var tab = tabs.items[i];
if(tab==null)continue;
if(!caller._isVisible(tab, context)) continue;
if(Ext.isFunction(tab.isVisible) 
&& !tab.isVisible(context))continue;
return tab;
}
return null;
},
exec : function(tabItem, opt){
if(!tabItem)return;
var caller = wcm.TabManager;
(tabItem.fn || caller._defExec).call(tabItem, tabItem, opt);
caller.rememberTabType(tabItem);
},
getTabUrl : function(tab){
if(tab.fittable===false){
return [
WCMConstants.WCM6_PATH,
'include/tab_fittable.html?tabUrl=',
encodeURIComponent(tab.url),
'&tabType=',
encodeURIComponent(tab.type)
].join('');
}
return tab.url;
},
_defExec : function(tabItem, opt){
var caller = wcm.TabManager;
var url = caller.getTabUrl(tabItem);
var cJoin = url.indexOf('?')==-1?'?':'&';
if(Ext.isString(opt)){
$('main').src = url + cJoin + opt;
return;
}
if(opt===true || window==$MsgCenter.getActualTop()){
try{
$('main').src = url + cJoin + 
$('main').contentWindow.location.search.substring(1);
return;
}catch(err){
}
}
var sSearch = location.search.substring(1);
var nQIdx = url.indexOf('?');
if(nQIdx==-1){
location.href = url + cJoin + sSearch;
return;
}
var params = Ext.Json.toUpperCase(sSearch.parseQuery());
var params2 = url.substring(nQIdx + 1).parseQuery();
for(var paramName in params2){
if(params[paramName.toUpperCase()]){
sSearch = sSearch.replace(new RegExp(paramName+'=[^&]*(&|$)', 'ig'), '');
}
}
location.href = url + cJoin + sSearch;
},
register : function(infos){
var caller = wcm.TabManager;
if(Ext.isArray(infos)){
infos.each(function(info){
caller.register(info);
});
return caller;
}
var buffer = caller.buffers[infos.hostType.toLowerCase()];
if(buffer==null){
buffer = caller.buffers[infos.hostType.toLowerCase()] = [];
}
var tabs = caller.tabs[infos.hostType.toLowerCase()];
if(!tabs){
tabs = caller.tabs[infos.hostType.toLowerCase()] = {};
}
var items = infos.items;
if(Ext.isArray(items)){
items.each(function(item, index){
item = Ext.applyIf(item, infos);
delete item.items;
item = Ext.apply({}, item);
buffer.push(item);
tabs[item.type.toLowerCase()] = item;
});
}else{
items = Ext.applyIf(items, infos);
delete items.items;
items = Ext.apply({}, items);
buffer.push(items);
tabs[items.type.toLowerCase()] = items;
}
return caller;
},
isTabItem : function(tabItem){
return Ext.isObject(tabItem) && tabItem.hostType && tabItem.type;
},
maxDisplayNum : 100,
showAll : function(){
var actualTop = $MsgCenter.getActualTop();
return !!actualTop.wcm.TabManager._showAll;
},
rememberShowAll : function(_bShowAll){
var actualTop = $MsgCenter.getActualTop();
actualTop.wcm.TabManager._showAll = _bShowAll;
},
defaultTabs : {},
rememberTabType : function(tabItem){
var actualTop = $MsgCenter.getActualTop();
var hostType = tabItem.hostType.toLowerCase();
var defaults = actualTop.wcm.TabManager.defaultTabs;
defaults[hostType] = tabItem.type;
},
isDefault : function(tabItem, hostType){
hostType = hostType.toLowerCase();
var actualTop = $MsgCenter.getActualTop();
var defaults = actualTop.wcm.TabManager.defaultTabs;
if(!defaults[hostType])return tabItem.isdefault;
return tabItem.type.equalsI(defaults[hostType]);
},
calDisplayNum : function(nDisplayNum){
var caller = wcm.TabManager;
if(caller.showAll()){
return caller.maxDisplayNum;
}
return nDisplayNum;
},
createWrapper : function(oTabItem, fWrapper){
if(!oTabItem || !fWrapper)return;
var oldWrapper = oTabItem.wrapper;
oTabItem.wrapper = oldWrapper ? function(context, tabItem){
return fWrapper(context, oldWrapper(context, tabItem));
} : fWrapper;
},
myCreateInterceptor : function(thisFcn, fcn, scope){
if(typeof fcn != "function"){
return thisFcn;
}
var method = thisFcn;
return function() {
var result = fcn.apply(scope || this || window, arguments);
if(Ext.isBoolean(result)){
return result;
}
return method.apply(this || window, arguments);
};
},
createInterceptor : function(item){
var caller = wcm.TabManager;
if(!Ext.isFunction(item.isVisible))return;
var tabItem = caller.getTab(item.hostType, item.type);
if(tabItem==null)return;
tabItem.isVisible = tabItem.isVisible || function(){
return true;
};
tabItem.isVisible = caller.myCreateInterceptor(tabItem.isVisible, item.isVisible);
}
});

Ext.ns('WCMLANG', 'wcm.LANG');
WCMLANG = wcm.LANG;
Ext.apply(wcm.LANG, {
LOCALE : 'cn',
WCM_WELCOME : '欢迎您使用TRS WCM系统',
NOW_DOING_WAIT : '正在{0},请耐心等候.',
TIME_WAIT_DESC : '等待时间：',
TIME_UNIT_SECOND : '秒',
SYSTEM_ALERT : '系统提示',
WEBSITE : '站点',
CHANNEL : '栏目',
DOCUMENT : '文档',
SITERECYCLE : '站点回收站',
CHNLRECYCLE : '栏目回收站',
SYTEM_SUOYOU : '所有',
EASY_SERVER_OVER : '执行时间超出常规时间，',
EASY_SERVER_ERROR : '可能出现网络故障，请刷新后重新处理。',
LOGIN_ISSYSTEM_WARNNING : '{0}是系统保留帐号！',
LOGIN_NOUSERNAME_WARNNING : '请输入用户名！',
LOGIN_NOPASSWORD_WARNNING : '请输入密码！',
LOGIN_LARGERLENGTH_WARNING:'用户名长度超过最大长度20！',
LOGIN_DOWITH_FORCE_LOGIN: "用户[{0}]已在[{1}]登录!\n您希望强行登录吗?",
LOGIN_DOWITH_ERRMSG : "\n{0}！",
MSGCENTER_DOCUMENT_NAME : "文档",
MSGCENTER_CHANNEL_NAME : "栏目",
MSGCENTER_WEBSITE_NAME : "站点",
ABSLIST_OPER_LOADING : "正在加载...",
ABSLIST_OPER_MORE : "更多操作",
ABSLIST_OPER_DETAIL : "详细信息",
ABSLIST_OPER_DETAIL_OBJS : '当前共选中<span class="num">{0}</span>{1}. ',
OPER_TITLE_INROOT : '库{0}操作任务',
OPER_TITLE_INSITE_SHORT : '{0}操作任务',
OPER_TITLE_INSITE : '站点{0}操作任务',
OPER_TITLE_INCHANNEL : '栏目{0}操作任务',
OPER_TITLE_INCLASSINFO : '分类法{0}操作任务',
OPER_TITLE_INCHANNEL_SHORT : '{0}操作任务',
OPER_TITLE_INCHANNELMASTER : '子栏目操作任务',
OPER_TITLE_OBJ : '{0}操作任务',
LIST_QUERY_ALL_DESC : '全部',
LIST_QUERY_INPUT_DESC : '..输入',
LIST_QUERY_JSC_DESC : '检索词',
LIST_QUERY_FLOAT : '要求为小数！',
LIST_QUERY_INT_MIN : '要求为整数！',
LIST_QUERY_INT_MAX : '要求在-2147483648~2147483647(-2^31~2^31-1)之间的数字！', 
LIST_QUERY_MAX_LEN : '<span style="width:180px;overflow-y:auto;">当前检索字段限制为[<b>{0}</b>]个字符长度，当前为[<b>{1}</b>]！\<br><br><b>提示：</b>每个汉字长度为2。</span>',
LIST_QUERY_DEFAULTORDER : '默认排序',
LIST_QUERY_NOTDEFAULT : '非默认排序时不保存拖动排序结果',
PHOTO : '图片',
METAVIEWDATA : '资源',
INFOVIEWDOC : '文档',
WATERMARK : '水印',
TEMPLATEARG : '模板变量',
TEMPLATE : '模板',
REPLACE : '替换内容',
PUBLISHDISTRIBUTION : '站点分发',
DISTRIBUTE : '分发',
METAVIEW : '视图',
METARECDATA : '记录列表',
METADBTABLE : '元数据',
MESSAGE_tab_34 : '最新消息',
MESSAGE_tab_35 : '已收消息',
MESSAGE_tab_36 : '已发消息',
LOGO : '栏目Logo',
FLOWDOC_FORDEAL : '待处理',
FLOWDOC_DEALED : '已处理',
FLOWDOC_FROMME : '我发起',
FLOW : '工作流',
DOCRECYCLE : '废稿箱',
CONTENTEXTFIELD : '扩展字段',
CLASSINFO : '分类法',
DOCUMENTSYN : '文档同步',
CHANNELSYNCOL : '栏目汇总',
CHANNELSYN : '栏目分发',
CHANNELCONTENTLINK : '热词',
RIGHTSET : '权限',
PLUGIN_NOREG : '此选件未正确安装或您没有购买此选件！',
NORIGHT_TABCHANGE : '对不起，您没有权限处理此节点下的任何操作！',
SYSTEMINFO : '系统提示信息',
NAV_VIEW : '视图',
NAV_SEARCH : '检索',
NAV_TREE : '导航',
CHILDCHANNEL : '子栏目',
TREE_QUICKLOCATE : '快速定位',
TREE_INDIVIDUATE : '设置定制的站点',
TREE_REFRESH : '刷新',
TREE_NEWSITE : '新建站点',
TREE_QUICKNEWSITE : '智能创建站点',
TREE_IMPORTSITE : '从外部导入站点',
TREE_KEYWORDMGR : '管理关键词',
TREE_NEWCHNL : '新建栏目',
TREE_NEWTEMP : '新建模板',
TREE_IMPORTTEMP : '导入模板',
TREE_REPLACE_THISSITE : '这(?:个|些)站点(?:的)?',
TREE_REPLACE_THISDOC : '这(?:篇|些)文档(?:的)?',
TREE_REPLACE_THISTEMPLATE : '这(?:个|些)模板(?:的)?',
TREE_REPLACE_THISREC : '这(?:条|些)记录(?:的)?',
TREE_REPLACE_THISCHNL : '这个栏目',
TREE_NEWCHILDCHNL : '新建子栏目',
TREE_NEWDOC : '新建文档',
SURE : '确定',
TEXT_LIB : '文字库',
SEARCHTITLE :'文档检索的位置',
CRTIME : '创建时间设置',
PUBTIME :'发布时间设置',
ALERTPLACE : '请指定要搜索的位置！',
ALERTTIME :'：结束时间不应该小于开始时间！',
SYS_CHANNELSELECT_NOMODE : '没选中任何模式',
SYS_CHANNELSELECT_NONE : '请选择要设置的栏目！',
ALERTUNEQUAL : "所选的栏目非表单栏目，或与源栏目使用的表单不一致。",
CLOSE : '关闭',
UNDO : '暂未实现',
ABSLIST_OPER_DETAILMORE : '更多属性',
PHOTO_LIB : '图片库设置',
DIALOG_SYSTEM_ALERTION : "系统提示信息",
DIALOG_BTN_OK : "确定",
DIALOG_BTN_CANCEL : "取消",
DIALOG_BTN_YES : "是",
DIALOG_BTN_NO : "否",
DIALOG_SHOW_DETAIL : '显示详细信息',
DIALOG_COPY_DETAIL : '复制此信息',
DIALOG_CLOSE_FRAME : '关闭提示窗',
DIALOG_CLIPBOARD_COPYED : "已经复制到剪切板中！",
DIALOG_COPY_NOT_SUPPORTED : '您的浏览器不支持自动复制操作，请手工拷贝！',
DIALOG_SERVER_ERROR : "与服务器交互时出现错误",
DIALOG_DETAIL_INFO : '详细信息',
DIALOG_HIDE_INFO : '隐藏显示',
DIALOG_TIP1 : '本窗口在',
DIALOG_TIP2 : '秒内将自动消失',
PUBLISH_1 : '发布校验结果',
PUBLISH_2 : '已经将您的发布操作提交到后台了...',
PUBLISH_3 : '已发',
PUBLISH_4 : '已否',
PUBLISH_5 : '预览出错',
PUBLISH_6 : '文档',
PUBLISH_7 : '图片',
PUBLISH_8 : '视频',
PAGENAV1 : '条',
PAGENAV2 : '记录',
PAGENAV3 : "单击当前页可输入跳转页号",
PAGENAV4 : "首页",
PAGENAV5 : "更多",
PAGENAV6 : "尾页",
PAGENAV7 : "共",
PAGENAV8 : "页",
PAGENAV9 : "<span class=\"nav_pagesize\">{0}</span>{1}/页",
PAGENAV10 : "跳转到第",
PAGENAV11 : "页",
UPLOAD_1 : '\n原因：文件路径不存在或者非本地文件路径。',
UPLOAD_2 : "没有选择",
UPLOAD_3 : "只支持上传\"",
UPLOAD_4 : "\"格式的文件！",
UPLOAD_5 : '与服务器交互时出错啦！',
UPLOAD_6 : "文件！",
ALERTCANCEL : "未选中任何热词进行取消替换,您确认不进行取消替换?",
UNSELECTALL : '取消全选',
SELECTALL : '全选',
ANALYZE : '正在自动分析文档中命中的热词...',
NOHEAD : '无标题',
AUTO : '自动',
INVALID_PARAM : '无效的参数！',
TIPS : '提示：',
NOPICFOUNDED :"未选择任何要导入的图片",
SYSTEM_NOTUNIQUE : "不唯一",
ALERT_MESSAGE_1 : '您是初次访问系统，可能要设置一些系统的配置，',
ALERT_MESSAGE_2 : '设置入口',
ALERT_MESSAGE_3 : ' 您也可以以后再设置，设置入口是在菜单栏的配置管理下面的系统配置项。',
ALERT_MESSAGE_4 : "您在本系统中使用的密码为弱密码，为了确保您个人账号的安全，请点击<a href='#' onclick='onEditPassword();return false'>重设密码</a>进行重新设定！",
TRSSERVER_1 : '配置TRSServer信息',
TRSSERVER_2 : '刷新列表',
TRSSERVER_3 : 'TRSServer数据列表',
TRSSERVER_4 : '配置窗口',
TRSSERVER_5 : '提取',
TRSSERVER_6 : '提取为WCM数据',
TRSSERVER_7 : '选择所属栏目',
TRSSERVER_8 : '参数配置',
TRSSERVER_9 : '新建检索任务',
TRSSERVER_10 : '修改检索任务',
TRSSERVER_11 : "请选择需要删除的检索任务!",
TRSSERVER_12 : "您确定需要删除选定的检索任务吗?"
});

