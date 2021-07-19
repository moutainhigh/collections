Ext.ns("Ajax.Request");
function getTransport(){
return Try(
function() {return new XMLHttpRequest()},
function() {return new ActiveXObject('Msxml2.XMLHTTP')},
function() {return new ActiveXObject('Microsoft.XMLHTTP')}
) || false;
}
function emptyFn(){}
function ajaxRequest(fo){
var transport = getTransport();
var bSynSend = fo.asyn==false;
var url = fo.url;
var method = (fo.method || 'GET').toLowerCase();
var parameters = fo.parameters || '';
if (method == 'get' && parameters.length > 0)
url += (url.match(/\?/) ? '&' : '?') + parameters;
transport.open(method, url, !bSynSend);
if(!bSynSend){
var caller = this;
var callback = function(){
var readyState = transport.readyState;
if (readyState != 4) return;
if(transport.status==12029 || transport.status==12007){
(fo.onFailure||window.NotifySystemError||emptyFn)(transport, caller);
transport.onreadystatechange = emptyFn;
return;
}
var isNotLogin = caller.header('TRSNotLogin');
if(isNotLogin=='true'){
if(!window.DefaultNotLogin){
try{
var actualTop = $MsgCenter ? $MsgCenter.getActualTop() : top;
actualTop.location.href = WCMConstants.WCM_NOT_LOGIN_PAGE + 
'?FromUrl=' + encodeURIComponent(actualTop.location.href);
}catch(err){
alert(wcm.LANG.AJAX_ALERT_3 || '您已退出系统，请重新登录');
}
}else{
window.DefaultNotLogin();
}
return;
}
var isError = caller.header('TRSException');
if(caller.responseIsSuccess() && isError!='true'){
if(fo.onSuccess)fo.onSuccess(transport, caller);
}
else{
if (isError=='true'||transport.status==500){
(fo.on500||window.ajax500||emptyFn)(transport, caller);
}
(fo.onFailure||window.ajaxFail||emptyFn)(transport, caller);
}
if(fo.onComplete)fo.onComplete(transport, caller);
try{
transport.onreadystatechange = emptyFn;
}catch(err){}
}
transport.onreadystatechange = callback;
}
var requestHeaders = ['X-Requested-With', 'XMLHttpRequest'];
if (method == 'post') {
requestHeaders.push('Content-type', fo.contentType||'application/x-www-form-urlencoded');
if (transport.overrideMimeType)
requestHeaders.push('Connection', 'close');
}
for (var i = 0; i < requestHeaders.length; i += 2){
transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
}
this.header = function(name) {
return this.transport.getResponseHeader(name);
}
this.transport = transport;
this.fo = fo;
this.responseIsSuccess = function() {
var status = this.transport.status;
return status == undefined
|| status == 0
|| (status >= 200 && status < 300);
}
this.responseIsFailure = function() {
return !this.responseIsSuccess();
}
transport.send(method=='post'?(fo.postBody||parameters):null);
return transport;
}
Ajax.Request = function(url, opts){
var asyn = opts['asynchronous'];
ajaxRequest.call(this, Ext.apply({url:url, asyn:asyn==null?true:false}, opts));
}

Ext.apply(wcm.LANG, {
AJAX_ALERT_1 : '尚未登陆，请重新登录',
AJAX_ALERT_2 : '与服务器交互时出现错误！',
AJAX_ALERT_3 : '您已退出系统，请重新登录',
AJAX_ALERT_4 : '与服务器交互失败，服务器已经停止或者您的网络出现故障！',
AJAX_ALERT_5 : 'URL已经超出1000个字节(',
AJAX_ALERT_6 : '), 建议使用POST提交数据！',
AJAX_ALERT_7: '参数传入错误:_oPost不能为Function类型.',
AJAX_ALERT_8: '参数传入错误:不存在id为',
AJAX_ALERT_9: '的form对象.',
AJAX_ALERT_10: '参数传入错误:_bSend只能为true,false或null.',
AJAX_ALERT_11: '与服务器交互时出现错误！',
AJAX_ALERT_12: '与服务器交互时发生了以下异常：\n'
});

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
Document.prototype.__defineGetter__( "xml", function () {
return (new XMLSerializer()).serializeToString(this);
});
}
function buildXml(s, m, data){
var doc = loadXml(String.format('<post-data><method type="{0}">{1}</method>'+
'<parameters></parameters></post-data>', m, s));
var pa = doc.getElementsByTagName("parameters")[0];
jsonIntoEle(doc, pa, data, true);
return doc;
}
function loadXml(str){
var doc = Try(
function() {return new ActiveXObject('Microsoft.XMLDOM');},
function() {return document.implementation.createDocument("","",null);}
);
doc.loadXML(str);
return doc;
}
function xmlTextNode(xmlDoc, value){
if(value.match(/<!\[CDATA\[|\]\]>/img))
return xmlDoc.createTextNode(value);
return xmlDoc.createCDATASection(value);
}
function jsonIntoEle(xmlDoc, parent, json, bAlwaysNode){
if(json==null || typeof json!='object')return;
if(Array.isArray(json)){
var arr = [];
for(var i=0,n=json.length;i<n;i++){
var value = json[i];
if(value==null)continue;
if(typeof value!='object'){
arr.push(value);
continue;
}
var newEle = parent;
if(i!=0){
newEle = xmlDoc.createElement(parent.nodeName);
parent.parentNode.appendChild(newEle);
}
jsonIntoEle(xmlDoc, newEle, value);
continue;
}
if(arr.length>0){
parent.appendChild(xmlTextNode(xmlDoc, arr.join()));
}
return;
}
for(var name in json){
if(!name) continue;
var value = json[name];
if(value==null)continue;
if(typeof value=='object'){
var newEle = xmlDoc.createElement(name);
parent.appendChild(newEle);
jsonIntoEle(xmlDoc, newEle, value);
continue;
}
value = '' + value;
if(!bAlwaysNode && name.toUpperCase()!='NODEVALUE'){
parent.setAttribute(name, value);
continue;
}
var elChild = xmlTextNode(xmlDoc, value);
if(name.toUpperCase()=='NODEVALUE'){
parent.appendChild(elChild);
continue;
}
var newEle = xmlDoc.createElement(name);
parent.appendChild(newEle);
newEle.appendChild(elChild);
}
}
if(!window.$toQueryStr){
var $toQueryStr = function(_oParams, _bUpper){
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
}
var m_remoteUrl = window.WCMConstants ? WCMConstants.WCM_ROMOTE_URL : '/wcm/center.do';
var BasicDataHelper = {
_DoPost : function(_sUrl, s, m, fo, ajax){
ajax.postBody = buildXml(s, m, fo);
ajax.url = _sUrl;
ajax.method = 'post';
ajax.contentType = 'multipart/form-data';
return new ajaxRequest(ajax);
},
_DoGet : function(_sUrl, s, m, fo, ajax){
var c = (_sUrl.indexOf('?')!=-1)?'&':'?';
ajax.url = _sUrl + c + 'serviceid=' + s + '&methodname=' + m;
ajax.parameters = $toQueryStr(fo);
return new ajaxRequest(ajax);
},
_DoMultiPost : function(_sUrl, infos, ajax){
var doc = loadXml('<post-data></post-data>');
var root = doc.documentElement;
for(var i=0;i<infos.length;i++){
var fo = infos[i];
var mel = doc.createElement("method");
root.appendChild(mel);
mel.setAttribute("type", fo.m);
mel.appendChild(doc.createTextNode(fo.s));
var pel = doc.createElement("parameters");
root.appendChild(pel);
jsonIntoEle(doc, pel, fo.data, true);
}
ajax.postBody = doc;
ajax.url = _sUrl;
ajax.method = 'post';
ajax.contentType = 'multipart/form-data';
return new ajaxRequest(ajax);
},
Call : function(fo){
return this[fo.post?'_DoPost':'_DoGet'](m_remoteUrl,
fo.serviceId, fo.methodName, fo.data, fo.ajax || {});
},
MultiCall : function(fo){
return this._DoMultiPost(m_remoteUrl,
fo.data, fo.ajax || {});
},
Combine : function(s, m, data){
return {s:s, m:m, data:data};
},
JspMultiCall : function(fo){
return this._DoMultiPost(fo.url,
fo.data, fo.ajax || {});
},
JspRequest : function(fo){
var ajax = fo.ajax;
ajax.url = ajax.url || fo.url;
var method = (ajax.method || 'GET').toLowerCase();
var post = fo.post || method == 'post';
ajax.method = post ? 'post' : 'get';
if(fo.data){
ajax.parameters = $toQueryStr(fo.data);
}
return new ajaxRequest(ajax);
}
};

Ext.ns('com.trs.web2frame.BasicDataHelper', 'com.trs.web2frame.PostData', 'Form');
var easyBDH = BasicDataHelper;
var myBDH = Ext.apply({}, BasicDataHelper);
Ext.apply(myBDH, {
_json : function(trans, caller) {
if(caller.header('ReturnJson') == 'true')
return Ext.Json.eval(trans.responseText);
var cnType = (caller.header('Content-type') || '');
if (cnType.match(/^text\/javascript/i)){
eval(trans.responseText);
return null;
}
if(cnType.indexOf('/html')!=-1)return null;
try{
if(trans.responseXML)
return parseXml(trans.responseXML);
if(cnType.indexOf('/xml')!=-1)
return parseXml(loadXml(trans.responseText));
}catch(err){
}
return null;
},
_jsonUp : function(trans, caller) {
return Ext.Json.toUpperCase(myBDH._json(trans, caller));
},
_ajaxEvents : function(fSuc, f500, fFail){
return {
onSuccess : fSuc ? function(trans, caller){
try{
fSuc.toString();
}catch(error){
return;
}
fSuc(trans, myBDH._jsonUp(trans, caller), caller);
} : null,
on500 : f500 ? function(trans, caller){
f500(trans, myBDH._jsonUp(trans, caller), caller);
} : null,
onFailure : fFail ? function(trans, caller){
fFail(trans, myBDH._jsonUp(trans, caller), caller);
} : null
};
},
_makeData : function(data){
var type = Ext.type(data);
if(type!='element' && type!='string')return Ext.Json.toUpperCase(data);
return com.trs.web2frame.PostData.form(data, function(k){return k;});
},
Call : function(s, m, data, bPost, fSuc, f500, fFail){
easyBDH.Call({
serviceId : s,
methodName : m,
data : myBDH._makeData(data),
post : bPost,
ajax : this._ajaxEvents(fSuc, f500, fFail)
});
},
Combine : function(s, m, data){
return {s:s, m:m, data:myBDH._makeData(data)};
},
MultiCall : function(arr, fSuc, f500, fFail){
easyBDH.MultiCall({
data : arr,
ajax : this._ajaxEvents(fSuc, f500, fFail)
});
},
JspMultiCall : function(url, arr, fSuc, f500, fFail){
easyBDH.JspMultiCall({
url : url,
data : arr,
ajax : this._ajaxEvents(fSuc, f500, fFail)
});
},
JspRequest : function(url, data, bPost, fSuc, f500, fFail){
easyBDH.JspRequest({
url : url,
data : myBDH._makeData(data),
post : bPost,
ajax : this._ajaxEvents(fSuc, f500, fFail)
});
}
});
com.trs.web2frame.BasicDataHelper = function(){
this.WebService = WCMConstants.WCM_ROMOTE_URL || '/wcm/center.do';
this.call = this.Call;
this.combine = this.Combine;
this.multiCall = this.MultiCall;
}
com.trs.web2frame.BasicDataHelper.prototype = myBDH;
BasicDataHelper = new com.trs.web2frame.BasicDataHelper();
Form.getElements = function(form){
form = $(form);
var rst = [], tags = ['input', 'select', 'textarea'], arr;
for (var i=0;i<tags.length;i++) {
arr = form.getElementsByTagName(tags[i]);
for (var j = 0; j < arr.length; j++){
rst.push(arr[j]);
}
}
return rst;
}
com.trs.web2frame.PostData = {
_elements : function(els, bCase, render){
var lastItamName = "";
var vData = {}, attrs = [], rst = {}, el, v;
for(var i=0; i<els.length; i++){
el = els[i];
if(!el.name || el.getAttribute("ignore"))continue;
v = $F(el);
if(lastItamName != els[i].getAttribute("Name"))
lastItamName = els[i].getAttribute("Name");
else{
if(v==null) continue;
}
if(v==null)v = "";
var name = bCase?el.name:el.name.toUpperCase();
if(el.getAttribute("isAttr", 2)){
attrs.push(name + '=' + v);
continue;
}
var arr = vData[name];
if(!arr){
arr = vData[name] = [v];
}
else{
arr.push(v);
}
}
if(attrs.length>0)rst.ATTRIBUTE = {NODEVALUE : attrs.join('&')};
for(var name in vData){
var v = $compact4Array(vData[name], "").join(",");
rst[name] = render ? render(v) : {NODEVALUE : v};
}
return rst;
},
form : function(frmId, render) {
var elFrm = $(frmId);
var els = Form.getElements(frmId);
var bCase = (elFrm.CaseSensitive)?
(elFrm.CaseSensitive.value=='true'||elFrm.CaseSensitive.value=='1') : false;
return this._elements(els, bCase, render);
}
};
function $compact4Array(arr, sCompactChar){
var rst = [];
for (var i = 0, n = arr.length; i < n; i++){
if(arr[i]!=sCompactChar)rst.push(arr[i]);
}
return rst;
}
function ajax500(trans, caller){
var json = myBDH._json(trans, caller);
$render500Err(trans, json);
}
function $render500Err(trans, json, isJson, fclose){
try{
if(window.ProcessBar != null)ProcessBar.close();
}catch (ex){
}
var msg = wcm.LANG.AJAX_ALERT_2 || '与服务器交互时出现错误！';
if(json) {
if(isJson !== true)
json = json.FAULT;
Ext.Msg.fault({
code : $v(json, 'code', isJson),
message : $v(json, 'message', isJson) || msg,
detail : $v(json, 'detail', isJson) || msg,
suggestion : $v(json, 'suggestion', isJson)
}, wcm.LANG.AJAX_ALERT_2 || '与服务器交互时出现错误', fclose);
}else{
Ext.Msg.$alert(trans.responseText);
}
}
function ajaxFailure(trans){
try{
if(window.ProcessBar)ProcessBar.close();
}catch (ex){
}
Ext.Msg.$alert(trans.responseText);
}
window.DefaultNotLogin = window.DoNotLogin = function(){
try{
var actualTop = $MsgCenter ? $MsgCenter.getActualTop() : top;
actualTop.location.href = WCMConstants.WCM_NOT_LOGIN_PAGE + 
'?FromUrl=' + encodeURIComponent(actualTop.location.href);
}catch(err){
alert(wcm.LANG.AJAX_ALERT_3 || '您已退出系统，请重新登录');
}
return false;
}
window.NotifySystemError = function(){
alert(wcm.LANG.AJAX_ALERT_4 || '与服务器交互失败，服务器已经停止或者您的网络出现故障！');
}

