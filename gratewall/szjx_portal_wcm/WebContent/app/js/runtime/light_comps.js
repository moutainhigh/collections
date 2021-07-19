function CTRSButton(id){
var itm = [
"<div class='ctrsbtn' _action='{1}'>",
"<div class='ctrsbtn_left'>",
"<div class='ctrsbtn_right'>{0}</div>",
"</div>",
"</div>"
].join('');
this.id = id;
this.init = function(){
var rst = [];
var btns = this.btns;
for(var i=0,n=btns.length;i<n;i++){
rst.push(String.format(itm, btns[i].html, btns[i].id));
}
var html = rst.join('\n');
$(this.id).innerHTML = html;
var caller = this;
Ext.get(this.id).on('click', function(event, target){
var btn = findItem(target, 'ctrsbtn');
if(btn==null)return;
var action = btn.getAttribute('_action', 2);
var item = caller.json_btns[action], fn;
if(!item || !(fn=item.action))return;
if(typeof fn=='string')eval('fn=' + fn);
fn.apply(null, [event, target]);
});
};
this.setButtons = function(buttons){
this.btns = buttons;
var json = this.json_btns = {};
for(var i=0,n=buttons.length;i<n;i++){
json[buttons[i].id] = buttons[i];
}
}
};

function CTRSButton(id){
var itm = [
"<div class='ctrsbtn' _action='{1}'>",
"<div class='ctrsbtn_left'>",
"<div class='ctrsbtn_right'>{0}</div>",
"</div>",
"</div>"
].join('');
this.id = id;
this.init = function(){
var rst = [];
var btns = this.btns;
for(var i=0,n=btns.length;i<n;i++){
rst.push(String.format(itm, btns[i].html, btns[i].id));
}
var html = rst.join('\n');
$(this.id).innerHTML = html;
var caller = this;
Ext.get(this.id).on('click', function(event, target){
var btn = findItem(target, 'ctrsbtn');
if(btn==null)return;
var action = btn.getAttribute('_action', 2);
var item = caller.json_btns[action], fn;
if(!item || !(fn=item.action))return;
if(typeof fn=='string')eval('fn=' + fn);
fn.apply(null, [event, target]);
});
};
this.setButtons = function(buttons){
this.btns = buttons;
var json = this.json_btns = {};
for(var i=0,n=buttons.length;i<n;i++){
json[buttons[i].id] = buttons[i];
}
}
};

if(!window.Calendar)Calendar={};
if(!Calendar.LANG)Calendar.LANG={};
Calendar._SDN = new Array
("日",
"一",
"二",
"三",
"四",
"五",
"六",
"日");
Calendar.LANG["LAST_MONTH"] = "上月";
Calendar.LANG["NEXT_MONTH"] = "下月";
Calendar.LANG["DD_CLOSE"] = "关闭";
Calendar.LANG["DD_TIME"] = "时间";
Calendar.LANG["DD_FORMATE"] = '日期格式不符合[yyyy-mm-dd hh:mm]!';
Calendar.LANG["DD_TIME_FORMATE"] = '时间格式不符合[hh:mm]!';
Calendar.LANG["DD_FORMATE_error"] = ']日期格式不符合[';
Calendar.LANG["DD_TIME_FORMATE1"] = '时间格式不符合[hh:mm:ss]!';
Calendar.LANG["DD_ERROR_FORMATE1"] = '不支持的日期格式[';
Calendar.LANG["DD_ERROR_FORMATE2"] = ']没有匹配日期格式[';
Calendar.LANG["DD_ERROR_FORMATE3"] = '月份应该为1到12的整数';
Calendar.LANG["DD_ERROR_FORMATE4"] = '每个月的天数应该为1到31的整数';
Calendar.LANG["DD_ERROR_FORMATE5"] = '该月不存在31号';
Calendar.LANG["DD_ERROR_FORMATE6"] = '2月最多有29天';
Calendar.LANG["DD_ERROR_FORMATE7"] = '闰年2月才有29天';
Calendar.LANG["DD_ERROR_FORMATE8"] = '小时应该是0到23的整数';
Calendar.LANG["DD_ERROR_FORMATE9"] = '分应该是0到59的整数';
Calendar.LANG["DD_ERROR_FORMATE10"] = '秒应该是0到59的整数';
Calendar.LANG["DD_ERROR_FORMATE11"] = ']日期格式不正确[';

if(!window.Calendar)Calendar={};
if(!Calendar.LANG)Calendar.LANG={};
Date.parseDate = function(str, fmt) {
var a = str.split(/\W+/), b = fmt.match(/%./g);
var i = 0, h = {};
for (i = 0; i < a.length; ++i) {
if(!a[i])continue;
h[b[i]] = parseInt(a[i], 10);
}
if (h['%y'] && h['%m'] && h['%d']){
if(h['%y']<100)h['%y'] = 2000 + h['%y'];
return new Date(h['%y'], h['%m'] - 1, h['%d'],
h['%H']||0, h['%M']||0, h['%s']||0);
}
return new Date();
};
function fmt2Digit(n){
return n>=10 ? n : '0' + n;
}
Date.prototype.format = function(fm){
if(!fm)return "";
var dt=this;
fm = fm.replace(/yyyy/ig,dt.getFullYear());
var y = "" + dt.getFullYear();
y = y.substring(y.length-2);
return fm.replace(/yy/ig, y)
.replace(/mm/g,fmt2Digit(dt.getMonth()+1))
.replace(/dd/ig,fmt2Digit(dt.getDate()))
.replace(/hh/ig,fmt2Digit(dt.getHours()))
.replace(/MM/g,fmt2Digit(dt.getMinutes()))
.replace(/ss/ig,fmt2Digit(dt.getSeconds()));
}
Date.prototype.clone = function(){
return new Date(this.getFullYear(),this.getMonth(),this.getDate(),
this.getHours(),this.getMinutes(),this.getSeconds());
}
Date.prototype.compare = function(dt){
if(!dt)return 1;
var arr = ['getFullYear', 'getMonth', 'getDate'];
for(var i=0;i<arr.length;i++){
if(this[arr[i]]()!=dt[arr[i]]())return 1;
}
return 0;
}
var DateHelper = {
defaultRegExp : 'yyyy-mm-dd',
RegExpLibs : {
'yyyy-mm-dd' : /^(\d{4})-(\d{2})-(\d{2})$/,
'yyyy/mm/dd' : /^(\d{4})\/(\d{2})\/(\d{2})$/,
'yyyy-mm-dd HH:MM' : /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})$/,
'yyyy-mm-dd HH:MM:ss' : /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}:(\d{2}))$/
},
check : function(_sDate, _sFormat){
_sFormat = _sFormat || this.defaultRegExp;
var regExp = this.RegExpLibs[_sFormat];
if(!regExp) {
return String.format("不支持的日期格式[{0}]",_sFormat.toLowerCase());
}
var result = _sDate.match(regExp);
if(!result){
return String.format("[{0}]没有匹配日期格式[{1}]",_sDate, _sFormat.toLowerCase());
}
return this.checkRange(result);
},
checkRange:function(aDatePart){
var year = parseInt(aDatePart[1], 10);
var month = parseInt(aDatePart[2], 10);
var day = parseInt(aDatePart[3], 10);
if(aDatePart[4]) var hour = parseInt(aDatePart[4], 10);
if(aDatePart[5]) var minute = parseInt(aDatePart[5], 10);
if(aDatePart[6]) var second = parseInt(aDatePart[6], 10);
if(month < 1 || month > 12){
return Calendar.LANG['DD_ERROR_FORMATE3'] || "月份应该为1到12的整数";
}
if (day < 1 || day > 31){
return (Calendar.LANG["DD_ERROR_FORMATE4"] || "每个月的天数应该为1到31的整数");
}
if ((month==4 || month==6 || month==9 || month==11) && day==31){
return Calendar.LANG["DD_ERROR_FORMATE5"] || "该月不存在31号";
} 
if (month==2){
var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));
if (day>29){
return Calendar.LANG["DD_ERROR_FORMATE6"] || "2月最多有29天";
} 
if ((day==29) && (!isleap)){
return Calendar.LANG["DD_ERROR_FORMATE7"] || "闰年2月才有29天";
}
}
if(hour && hour<0 || hour>23){
return Calendar.LANG["DD_ERROR_FORMATE8"] || "小时应该是0到23的整数";
} 
if(minute && minute<0 || minute>59){
return Calendar.LANG["DD_ERROR_FORMATE9"] || "分应该是0到59的整数";
} 
if(second && second<0 || second>59){
return Calendar.LANG["DD_ERROR_FORMATE10"] || "秒应该是0到59的整数";
}
}
};
function setFitPosition(el, point){
var left = point[0], top = point[1];
var right = left + 250;
if(right >= document.body.offsetWidth){
left = Math.max(left - 250, 0);
}
var bottom = top + 120;
if(bottom >= document.body.offsetHeight){
top = Math.max(top - 120, 0);
}
el.style.left = left + "px";
el.style.top = top + "px";
}
Ext.ns('wcm.TRSCalendar');
function defTRSCalendar(){
var m_calTemp = {
dd : [
'<TABLE id=cal_dp CELLSPACING=0 border=0>',
'<TR class=DPTitle>',
'<TD align="center"><span class=DPBtn id="py_{3}" title="{8}"><<</span>&nbsp;<span class=DPBtn id="pm_{3}" title="{5}"><</span></TD>',
'<TD align=center colspan=4>{0}</TD>',
'<TD align="right" colspan=2><span class=DPBtn id="nm_{3}" title="{6}">></span>&nbsp;<span class=DPBtn id="ny_{3}" title="{9}">>></span><span class=closeBtn id="cls_{3}" title="{7}">&nbsp;&nbsp;X&nbsp;</span></TD>',
'</TR>',
'<TR>{1}</TR>',
'{2}',
'{4}',
'</TABLE>'
].join(''),
d3 : '<TD class=DPWeekName width="40">{0}</TD>',
d4 : '<TR>{0}</TR>',
d1 : '<TD class=DPCellOther>&nbsp</TD>',
d2 : '<TD class="{1}" _date="{0}">{0}</TD>',
d5 : '<TR><TD colspan="7" align="center" class=DPTime>{2}:&nbsp;&nbsp;<input class=DPInput id="hms_{0}" value="{1}"/></TD></TR>'
}
var caches = {};
function inputBlur(event, caller){
var inputEl = caller.input;
if(typeof inputEl === 'string')
inputEl = $(inputEl);
if(!inputEl.value)return;
var reFmt = !caller.withtime ? /^\d{4}\-\d{2}\-\d{2}$/g :
/^\d{4}\-\d{2}\-\d{2} ([0-1]\d|2[0-3]):([0-5]\d):([0-5]\d)$/g;
var dft = caller.dtFmt || "yyyy-mm-dd";
if(DateHelper.check(inputEl.value, dft)){
alert(String.format('[{0}]日期格式不正确[{1}!]',(inputEl.getAttribute("elname")||inputEl.getAttribute("name")||inputEl.id),DateHelper.check(inputEl.value, dft)));
inputEl.value = new Date().format(dft);
inputEl.select();
inputEl.focus();
return;
}
}
function handlerClick(event, caller){
event = event || window.event;
var value = caller.getValue ? caller.getValue() : $(caller.input).value;
caller.m_cdt = value ? Date.parseDate(value, caller.fmt) : new Date();
caller.initDDT(0, false);
var oStyle = $('cal_dd').style;
setFitPosition($('cal_dd'), [Event.pointerX(event), Event.pointerY(event)]);
oStyle.display = '';
caller.showShield();
}
function TRSCalendar(fo){
Ext.apply(this, fo);
this.fmt = !this.withtime ? '%y-%m-%d' :'%y-%m-%d %H:%M:%s';;
var txt = $(this.input), caller = this;
if(txt && txt.tagName=='INPUT')txt.onblur = function(event){
inputBlur(event, caller);
}
if(!$(fo.handler)) return;
$(fo.handler).onclick = function(event){
handlerClick(event, caller);
}
}
var genId = 1;
wcm.TRSCalendar.get = function(fo){
var id = fo.id = 'trscal-'+genId++;
if(!caches[id])
caches[id] = new TRSCalendar(fo);
return caches[id];
}
TRSCalendar.prototype = {
getDDTHtml : function (dt){
var dt = dt || new Date(), hms = dt.format('HH:MM:ss');
if(this.dtFmt == 'yyyy-mm-dd HH:MM')
hms = dt.format('HH:MM');
dt.setDate(1);
var fst = dt.getDay(), m = dt.getMonth();
var sCurrMonth = dt.format('yyyy-mm');
var rs1 = [], dcnt = 0, rs2 = [], rs3 = [], wt = '';
for(var i=0;i<fst;i++){
rs1.push(m_calTemp.d1);
}
while(dt.getMonth()==m){
var date = dt.getDate();
var className = new Date().compare(dt)==0?'DPCellToday':
(this.m_cdt.compare(dt)==0?'DPCellCurr':'DPCell');
rs1.push(String.format(m_calTemp.d2, date, className));
dcnt++;
if((dcnt+fst)%7==0){
dcnt = fst = 0;
rs2.push(String.format(m_calTemp.d4, rs1.join('')));
rs1 = [];
}
dt.setDate(date+1);
}
if(dcnt!=0){
for(var i=dcnt;i<7;i++){
rs1.push(m_calTemp.d1);
}
rs2.push(String.format(m_calTemp.d4, rs1.join('')));
rs1 = [];
}
if(this.withtime){
wt = String.format(m_calTemp.d5, this.id, hms, Calendar.LANG["DD_TIME"]||"时间");
}
var a = Calendar._SDN || ["日", "一", "二", "三", "四", "五", "六", "日"];
for(var i=0;i<7;i++){
rs3.push(String.format(m_calTemp.d3, a[i]));
}
var html = String.format(m_calTemp.dd,
sCurrMonth, rs3.join(''), rs2.join(''),
this.id, wt, Calendar.LANG["LAST_MONTH"]||"上月", Calendar.LANG["NEXT_MONTH"]||"下月", Calendar.LANG["DD_CLOSE"]||"关闭",'上年','下年');
return html;
},
initDDT : function(_dx, bYear){
if(!_dx)this.m_tdt = this.m_cdt.clone();
var y = this.m_tdt.getFullYear();
var m = this.m_tdt.getMonth();
if(bYear){
y = y + (_dx||0);
}else{
m = m + (_dx||0);
}
this.m_tdt.setDate(1);
this.m_tdt.setMonth(m);
this.m_tdt.setFullYear(y);
var calDDT = $('cal_dd');
if(!calDDT){
calDDT = document.createElement('DIV');
calDDT.style.position = 'absolute';
calDDT.style.display = 'none';
calDDT.style.zIndex = 999;
calDDT.id = 'cal_dd';
document.body.appendChild(calDDT);
}
calDDT.innerHTML = this.getDDTHtml(this.m_tdt.clone());
this.showShield();
var lst_s = null;
$('cal_dp').onclick = function(event){
var src = Event.element(event || window.event);
var cn = src.className;
if(src.tagName!='TD' ||
(cn!='DPCell' && cn!='DPCellCurr'
&& cn!='DPCellToday'))return;
if(lst_s)lst_s.className = "DPCell";
src.className = "DPCellSelect";
lst_s = src;
}
$('cal_dp').ondblclick = function(event){
var m_dt = caller.m_tdt || caller.m_cdt;
var src = Event.element(event || window.event);
var m_dt = caller.m_tdt || caller.m_cdt;
var cn = src.className;
if(src.tagName!='TD' || 
(cn!='DPCell' && cn!='DPCellCurr'
&& cn!='DPCellToday'
&& cn!='DPCellSelect'))
return;
var sdate = m_dt.getFullYear() + '-' + (m_dt.getMonth()+1)
+ '-' + src.getAttribute("_date");
if(caller.withtime)
sdate += ' ' + $('hms_'+caller.id).value;
caller.m_cdt = Date.parseDate(sdate, caller.fmt);
caller.render();
$('cal_dd').style.display = 'none';
caller.hideShield();
}
var caller = this;
$('pm_' + this.id).onclick = function(){
caller.initDDT(-1, false);
};
$('nm_' + this.id).onclick = function(){
caller.initDDT(1, false);
};
$('py_' + this.id).onclick = function(){
caller.initDDT(-1, true);
};
$('ny_' + this.id).onclick = function(){
caller.initDDT(1, true);
};
$('cls_' + this.id).onclick = function(){
$('cal_dd').style.display = 'none';
caller.hideShield();
};
if(caller.withtime){
$('hms_'+this.id).onblur = function(event){
var reFmt = /([0-1]\d|2[0-3]):([0-5]\d):([0-5]\d)$/g;
if(caller.dtFmt == 'yyyy-mm-dd HH:MM')
reFmt = /([0-1]\d|2[0-3]):([0-5]\d)$/g;
if(!reFmt.test(this.value)){
var tipMsg = Calendar.LANG["DD_TIME_FORMATE1"] || '时间格式不符合[hh:mm:ss]!';
if(caller.dtFmt == 'yyyy-mm-dd HH:MM') tipMsg = Calendar.LANG["DD_TIME_FORMATE"] || '时间格式不符合[hh:mm]!';
alert(tipMsg);
caller.m_cdt = new Date();
if(caller.dtFmt == 'yyyy-mm-dd HH:MM') this.value = new Date().format("HH:MM");
else this.value = new Date().format("HH:MM:ss");
caller.render();
this.select();
return;
}
}
$('hms_'+this.id).onkeydown = function(event){
event = window.event || event;
if(event.keyCode != 13) return;
var m_dt = caller.m_cdt;
var date = m_dt.getDate();
if(lst_s != null) date = lst_s.getAttribute("_date");
var sdate = m_dt.getFullYear() + '-' + (m_dt.getMonth()+1) + '-' + date;
sdate += ' ' + $('hms_'+caller.id).value;
caller.m_cdt = Date.parseDate(sdate, caller.fmt);
caller.render();
$('cal_dd').style.display = 'none';
caller.hideShield();
}
}
},
initShield : function(){
if($('cal_dd_shld')) return;
var dom = document.createElement('iframe');
dom.src = Ext.blankUrl;
dom.style.display = 'none';
dom.style.position = 'absolute';
dom.style.zIndex = 998;
dom.style.border = '0';
dom.id = 'cal_dd_shld';
document.body.appendChild(dom);
},
showShield : function(){
this.initShield();
var cal = $('cal_dd');
var oStyle = $('cal_dd_shld').style;
oStyle.left = cal.style.left;
oStyle.top = cal.style.top;
oStyle.width = cal.offsetWidth;
oStyle.height = cal.offsetHeight;
oStyle.display = '';
},
hideShield : function(){
$('cal_dd_shld').style.display = 'none';
},
render : function(){
var dt = this.m_cdt, el = $(this.input);
var rst = dt.format(this.dtFmt || "yyyy-mm-dd");
if(this.setValue)return this.setValue(rst);
if(el){
el.value = rst;
el.focus();
el.blur();
}
}
};
}
defTRSCalendar();

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

Ext.ns('wcm.BubblePanel');
function defBubblePanel(){
var contains = function(t, p){
while(p && p.tagName!='BODY'){
if(t==p)return true;
p = p.parentNode;
}
return false;
}
var ua = navigator.userAgent.toLowerCase();
var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
var hide = function(ev){
if(ev.type=='blur' && contains(this.extEl.dom, ev.blurTarget))return;
var el = this.extEl.dom;
BpShieldMgr.hideShield(el);
el.style.display = 'none';
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
BpShieldMgr.showShield(el);
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
var BpShieldMgr = {
initShield : function(el){
if(!Ext.isIE6) return;
if($(el.id + '-shldbp')) return;
var dom = document.createElement('iframe');
dom.src = Ext.blankUrl;
dom.style.display = 'none';
dom.style.position = 'absolute';
dom.style.zIndex = el.style.zIndex - 1;
dom.id = el.id + '-shldbp';
document.body.appendChild(dom);
},
showShield : function(el){
if(!Ext.isIE6) return;
this.initShield(el);
var oStyle = $(el.id + '-shldbp').style;
if(el.style.left)
oStyle.left = (parseInt(el.style.left, 10))+"px";
if(el.style.right)
oStyle.right = (parseInt(el.style.right, 10))+"px";
oStyle.top = (parseInt(el.style.top, 10))+"px";
oStyle.width = (el.offsetWidth)+"px";
oStyle.height = (el.offsetHeight)+"px";
oStyle.display = '';
},
hideShield : function(el){
if(!Ext.isIE6) return;
$(el.id + '-shldbp').style.display = 'none';
},
destroyShield : function(){
if(!Ext.isIE6) return;
var dom = $(this.id + '-shldbp');
if(!dom) return;
dom.parentNode.removeChild(dom);
}
}

Ext.ns('wcm.CrashBoard', 'wcm.LANG');
var m_template = [
'<div class="wcm-cbd" id="{0}">',
'<div class="header l" id="header-{0}"><div class="r"><div class="c">',
'<div class="spt"></div>',
'<div class="title" id="dialogTitle-{0}">{1}</div>',
'<div class="tools" id="tools-{0}">',
'<a class="close" href="#" id="close-{0}"></a>',
'</div>',
'</div></div></div>',
'<div class="body l"><div class="r"><div class="c">',
'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
'<tr><td id="content-{0}">',
'<iframe src="{2}" id="frm-{0}" style="height:100%;width:100%;"',
' frameborder="0" onload="wcm.CrashBoard.contentLoaded(\'{0}\', this);"></iframe>',
'</td></tr>',
'<tr style="display:{5}"><td class="buttons" id="buttons-{0}" style="text-align:center;">',
'<span class="wcm-btn cbd-btn-right" id="ok-{0}-wrap"><span class="cbd-btn-left"><button class="cbd-btn" id="ok-{0}"><b>{3}</b></button></span></span>',
'<span style="display:{7}" class="wcm-btn cbd-btn-right" id="next-{0}-wrap"><span class="cbd-btn-left"><button class="cbd-btn" id="next-{0}"><b>{6}</b></button></span></span>',
'<span class="wcm-btn cbd-btn-right"><span class="cbd-btn-left" id="cancel-{0}-wrap"><button class="cbd-btn" id="cancel-{0}"><b>{4}</b></button></span></span>',
'</td></tr>',
'</table>',
'</div></div></div>',
'<div class="footer l"><div class="r"><div class="c"></div></div></div>',
'</div>',
].join("");
function defCrashBoard(){
var m_id = 0;
var cache = {};
function $cb(cfg){
cfg = Ext.apply({
id : 'cb-' + (++m_id),
title : wcm.LANG.crashborad_2011 || '系统提示框',
appendParamsToUrl : true
}, cfg);
Ext.apply(this, cfg);
this._win = window;
cache[cfg.id] = this;
}
$cb.prototype = {
getEl : function(id){
return this._win.$(id || this.id);
},
onOk : function(name){
var frm = $('frm-' + this.id), win, fn;
try{
win = frm.contentWindow;
fn = name ? win[name] : win.onOk;
}catch(err){
}
if(!fn)return;
var rst = fn(this);
if(rst===false)return;
if(this.callback)this.callback(rst);
this.close();
},
onCancel : function(){
this.close();
},
show : function(){
var t = this;
if(t.rendered) return;
t.rendered = true;
var sHtml = String.format(m_template, t.id, t.title, t.url,
t.ok || wcm.LANG.DIALOG_BTN_OK || '确定', t.cancel || wcm.LANG.DIALOG_BTN_CANCEL || '取消', t.btns==false?'none':'', t.next , t.next == undefined?'none':'');
var div = document.createElement('DIV');
document.body.appendChild(div);
div.innerHTML = sHtml;
var cbEle = t.getEl();
cbEle.style.zIndex = window.$MsgCenter ? $MsgCenter.genId(100) : 999;
t.getEl("cancel-" + t.id).onclick = t.getEl("close-" + t.id).onclick
= function(){t.onCancel();return false;};
if(this.draggable !== false) drag(cbEle, t.getEl('header-' + t.id), this.maskable);
t.getEl("ok-" + t.id).onclick = function(event){t.onOk()};
t.getEl("next-" + t.id).onclick = function(event){t.onOk('onNext')};
cbEle.style.display = '';
if(t.width) t.getEl().style.width = t.width;
if(t.height) {
var dom = t.getEl('content-' + t.id);
dom.style.height = t.height;
dom.style.height = (dom.offsetHeight - 65) + 'px';
}
var ua = navigator.userAgent.toLowerCase();
var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
var isStrict = document.compatMode == "CSS1Compat";
var docEle = isIE && !isStrict ? document.body : document.documentElement;
var left = (docEle.clientWidth - cbEle.offsetWidth) / 2 + docEle.scrollLeft + 'px';
var top = (docEle.clientHeight - cbEle.offsetHeight) / 2 + docEle.scrollTop + 'px';
cbEle.style.left = t.left || left;
cbEle.style.top = t.top || top;
this.showShield();
},
hide : function(){
this.getEl().style.display = 'none';
this.hideShield();
},
initShield : function(){
if(!this.maskable && !Ext.isIE6) return;
if($(this.id + '-shld')) return;
var dom = document.createElement('iframe');
dom.src = Ext.blankUrl;
dom.style.display = 'none';
dom.style.border = 0;
dom.frameBorder = 0;
Element.addClassName(dom, 'wcm-panel-shield');
dom.style.zIndex = this.getEl().style.zIndex - 1;
dom.id = this.id + '-shld';
document.body.appendChild(dom);
},
showShield : function(){
if(!this.maskable && !Ext.isIE6) return;
this.initShield();
var dom = this.getEl();
var oStyle = $(this.id + '-shld').style;
if(!this.maskable){
oStyle.left = (parseInt(dom.style.left, 10) )+"px";
oStyle.top = (parseInt(dom.style.top, 10) + 1)+"px";
oStyle.width = (dom.offsetWidth - 4)+"px";
oStyle.height = (dom.offsetHeight - 4)+"px";
} 
oStyle.display = '';
},
hideShield : function(){
if(!this.maskable && !Ext.isIE6) return;
$(this.id + '-shld').style.display = 'none';
},
destroyShield : function(){
if(!this.maskable && !Ext.isIE6) return;
var dom = $(this.id + '-shld');
if(!dom) return;
dom.parentNode.removeChild(dom);
},
close : function(){
try{
var t = this;
t.hide();
var dom = t.getEl("content-" + t.id);
t.getEl("frm-" + t.id).src = '';
dom.innerHTML = "";
t.getEl("close-" + t.id).onclick = null;
t.getEl("ok-" + t.id).onclick = null;
t.getEl("cancel-" + t.id).onclick = null;
t.getEl('header-' + t.id).onmousedown = null;
delete cache[t.id];
dom = t.getEl();
dom.parentNode.parentNode.removeChild(dom.parentNode);
}catch(err){}
}
};
function $toQueryStr(params){
if(!params)return '';
if(typeof params!='object')return params;
var rst = [];
for(var nm in params){
var v = params[nm], type = typeof v;
if(type!='string' && type!='number' && type!='boolean')continue;
rst.push(nm, '=', encodeURIComponent(params[nm]), '&');
}
return rst.join('');
}
wcm.CrashBoard.get = function(cfg){
if(!cfg.appendParamsToUrl)
cfg = Ext.apply({
appendParamsToUrl : true
}, cfg);
var cjoin = cfg.url.indexOf('?')==-1 ? '?' : '&';
if(cfg.appendParamsToUrl==true)
cfg.url = cfg.url + cjoin + $toQueryStr(cfg.params);
return cache[cfg.id] || (new $cb(cfg));
}
wcm.CrashBoard.contentLoaded = function(id, frm){
var cb = cache[id];
if(!cb) return;
try{
var win = frm.contentWindow;
if(!win.init)return;
}catch(err){
}
win.init(cb.params, cb);
}
}
var cbGetStyle = function(){
return window.getComputedStyle ? function(el, style){
var cs = window.getComputedStyle(el, "");
return cs ? cs[style] : null;
} : function(el, style){
return el.style[style] || el.currentStyle[style];
}
}();
function drag(o, p, maskable){
var id = o.id;
p.onmousedown=function(a){
var frm = $('frm-' + id);
if(frm) frm.style.visibility = 'hidden';
var sld = $(id + '-shld');
var d=document;if(!a)a=window.event;
var l=parseInt(cbGetStyle(o,'left'),10),t=parseInt(cbGetStyle(o,'top'),10);
var x=a.pageX?a.pageX:a.clientX,y=a.pageY?a.pageY:a.clientY;
if(p.setCapture)p.setCapture();
else if(window.captureEvents)window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
d.onmousemove=function(a){
if(!a)a=window.event;
if(!a.pageX)a.pageX=a.clientX;
if(!a.pageY)a.pageY=a.clientY;
var tx=a.pageX-x+l,ty=a.pageY-y+t;
o.style.left=tx+"px";
o.style.top=ty+"px";
if(!maskable && sld){
sld.style.left=(tx)+"px";
sld.style.top=(ty+1)+"px";
}
}
d.onmouseup=function(){
if(frm) frm.style.visibility = 'visible';
if(p.releaseCapture)p.releaseCapture();
else if(window.releaseEvents)window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
d.onmousemove=null;
d.onmouseup=null;
}
}
}
defCrashBoard();

var LockerUtil = {
unlock : function (_nObjId, _nObjType, _fAfterFail){
var params = {
"ObjId":_nObjId,
"ObjType":_nObjType,
"ActionFlag":"false"
};
var r = new ajaxRequest({
url : WCMConstants.WCM6_PATH + "include/cmsobject_locked.jsp",
method : 'get', 
asyn : false, 
parameters : $toQueryStr(params)
});
this.AfterFail = _fAfterFail;
eval(r.responseText);
return true;
},
unlockCallback : function(json){
try{
if(json.Result == "false"){
var func = this.AfterFail;
if(func && typeof(func) == 'function') {
func(json.Message, json);
}else{
}
return false;
}
}catch(err){
}
return true;
},
notLogin : window.DoNotLogin || Ext.emptyFn
};

Event.observe(window, 'load', function(){
if($('processbar'))return;
var div = document.createElement('DIV');
document.body.appendChild(div);
div.id = 'processbar';
div.style.display = 'none';
div.innerHTML = [
'<div id=pb_cv class=pb_cv></div>',
'<div class=pb_to>',
'<table class=pb_tb border=1 bordercolor=#000 bordercolordark=#fff ',
' cellspacing=0 cellpadding=0 style="font-size:12px">',
'<tr><td height=22 align=center bgcolor="#BBBBBB" style="font-size:14px;">',
wcm.LANG['WCM_WELCOME'] || '欢迎您使用TRS WCM系统','</td></tr>',
'<tr><td align=center bgcolor=#DDDDDD>',
String.format("正在{0},请耐心等候.", '<span id=pb_ev></span>'),
'<br><br>','等待时间：','<span style="color:red"><span id=pb_wt>0</span>','秒','</span>',
'<div style=\"padding:10px;\">如果系统长时间无法响应,请<a href=\"#\" id=\"pb_closer\" onclick=\"ProcessBar.close();return false;\">',
'<span style=\"color:red;\">点击这里</span></a>返回主页面</div>', 
'</td></tr></table>',
'</div>',
'</div>'
].join('');
});
var ProcessBar = function(){
var m_itv_pb;
return {
m_lStart : 0,
start : function(title){
Element.show('processbar');
document.body.scrollTop = 0;
$('pb_ev').innerHTML = title;
var lStart = this.m_lStart || new Date().getTime();
this.m_lStart = lStart;
m_itv_pb = setInterval(function(){
var lNow = new Date().getTime();
var elisTime = parseInt((lNow - lStart)/ 1000, 10);
$('pb_wt').innerHTML = elisTime;
if(elisTime>300){
alert(wcm.LANG['EASY_SERVER_OVER'] || "执行时间超出常规时间，"
+ wcm.LANG['EASY_SERVER_ERROR'] || "可能出现网络故障，请刷新后重新处理。");
ProcessBar.close();
}
}, 100);
$('pb_cv').style.height = document.body.scrollHeight;
},
exit :function(){
Element.hide('processbar');
clearInterval(m_itv_pb);
m_itv_pb = null;
},
close :function(){
if(!m_itv_pb) return;
this.m_lStart = null;
Element.hide('processbar');
clearInterval(m_itv_pb);
m_itv_pb = null;
}
}
}();

Ext.ns('TRSValidator52', 'm_Valid52Info', 'm_Valid52Helper');
var initTRSValidator52 = function(){
if(arguments.callee.inited) return;
arguments.callee.inited = true;
Ext.applyIf(m_Valid52Info, {
pre : wcm.LANG.WCM52_ALERT_30 || "您输入的",
required : wcm.LANG.WCM52_ALERT_67 || "[{0}]为空,此字段为必填!",
number : wcm.LANG.WCM52_ALERT_96 || "[{0}]不是整型数!",
'double' : wcm.LANG.WCM52_ALERT_97 || "[{0}]不是浮点数!",
scalemore : wcm.LANG.WCM52_ALERT_98 || "[{0}]小数位数超过[18]!",
scaleexceed : wcm.LANG.WCM52_ALERT_99 || "[{0}]小数位数超过[{1}]!",
max : wcm.LANG.WCM52_ALERT_68 || "[{0}]大于最大值 [{1}]!",
min : wcm.LANG.WCM52_ALERT_69 || "[{0}]小于最小值 [{1}]!",
max_len : wcm.LANG.WCM52_ALERT_70 || "[{0}]长度大于最大长度 [{1}](注:每个汉字长度为2)!",
min_len : wcm.LANG.WCM52_ALERT_71 || "[{0}]长度小于最小长度 [{1}](注:每个汉字长度为2)!",
email : wcm.LANG.WCM52_ALERT_72 || "[{0}]不符合Email格式,如xxx@xxx.com!",
url : wcm.LANG.WCM52_ALERT_73 || "[{0}]不符合URL格式,如http://www.trs.com.cn!",
ip : wcm.LANG.WCM52_ALERT_74 || "[{0}]不符合ip格式,如192.9.200.22!",
common_char : wcm.LANG.WCM52_ALERT_75 || "[{0}]不符合格式,必须为字母,下划线或者数字所组成,首字母需为字母!",
common_char2 : wcm.LANG.WCM52_ALERT_76 || "[{0}]不符合格式,必须为字母,下划线或者数字所组成!",
checkdbkeywords : wcm.LANG.WCM52_ALERT_77 || "[{0}]中含有数据库关键字,不合法!",
info : function(type, a, b, c, d){
return this.pre + String.format(this[type.toLowerCase()], a, b, c, d);
}
});
};
var m_Valid52Re = {
email : /^.+@.+$/g,
url : /^(http|https|ftp|mtsp):\/\/.+$/i,
ip : /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/,
common_char : /^[a-z][a-z0-9_]*$/i,
common_char2 : /^[a-z0-9_]+$/i,
'double' : /^-?\d+(\.\d+)?(e[\+-]?\d+)?$/,
number : /^-?\d+(e[\+-]?\d+)?$/,
required : /^.+$/
};
function formEles(_fm){
var fm = $(_fm), arr = ['input', 'textarea', 'select'], els = [];
for(var i=0,n=arr.length;i<n;i++){
var tags = fm.getElementsByTagName(arr[i]);
for (var j = 0; j < tags.length; j++){
var tag = tags[j];
if(!tag.name || tag.getAttribute("ignore"))continue;
els.push(tags[j]);
}
}
return els;
}
TRSValidator52 = {
validatorForm : function(fm, bFailStop, filter){
initTRSValidator52();
var arEls = formEles(fm), rst = {valid:true, einfos:[]}, o, e;
for(var i = 0;i<arEls.length;i++){
e = arEls[i];
if(!e.getAttribute("pattern"))continue;
if(filter && filter(e))continue;
e.elname =e.getAttribute("elname") || e.getAttribute("name");
if(!(o = this.gv(e)))continue;
if(o.validate())continue;
rst.valid = false;
rst.einfos.push(o.einfo);
if(!rst.fstEle)rst.fstEle = e;
if(bFailStop)return rst;
}
return rst;
},
gv : function(oEle){
var pattern = oEle.getAttribute("pattern").toLowerCase(), fn = this.vos[pattern];
if(fn)return fn(oEle);
return null;
},
vos : m_Valid52Helper
};
Ext.apply(m_Valid52Helper, {
_format : function(ele, attr, v){
if(v == null)return true;
if(v=='')return true;
if(!v.match(m_Valid52Re[attr])){
this.einfo = m_Valid52Info.info(attr, ele.elname);
return false;
}
return true;
},
_validator : function(oEle, attrs, helper){
var v = $F(oEle) || '';
for(var i=0,n=attrs.length;i<n;i++){
if(!oEle.getAttribute(attrs[i]))continue;
var fn = helper[attrs[i]] || m_Valid52Helper._format;
if(fn && !fn.call(this, oEle, attrs[i], v))return false;
}
return true;
},
string : function(oEle){
if(!oEle)return null;
var abc = function(ele, attr, bMax){
var nMLen = parseInt(ele.getAttribute(attr), 10);
var nLen = ($F(ele)||'').byteLength();
if((bMax && nLen>nMLen) || (!bMax && nLen<nMLen)){
this.einfo = m_Valid52Info.info(attr, oEle.elname, nMLen);
return false;
}
return true;
}
var helper = {
max_len : function(ele, attr, v){
return abc.call(this, ele, attr, true);
},
min_len : function(ele, attr, v){
return abc.call(this, ele, attr, false);
}
};
return {
validate : function(){
var attrs = ['required', 'min_len', 'max_len', 'email', 'url', 'ip', 'common_char', 'common_char2'];
return m_Valid52Helper._validator.call(this, oEle, attrs, helper);
}
};
},
number : function(oEle){
if(!oEle)return null;
var abc = function(ele, attr, v, bMax){
var sMethod = oEle.getAttribute("pattern").toLowerCase() == "number" ? parseInt : parseFloat;
var nMNum = sMethod(ele.getAttribute(attr), 10);
var nNum = sMethod(v, 10);
if((bMax && nNum>nMNum) || (!bMax && nNum<nMNum)){
this.einfo = m_Valid52Info.info(attr, oEle.elname, nMNum);
return false;
}
return true;
}
var helper = {
max : function(ele, attr, v){
return abc.call(this, ele, attr, v, true);
},
min : function(ele, attr, v){
return abc.call(this, ele, attr, v, false);
},
scale : function(ele, attr, v){
if(v.indexOf(".") > 0){
var decimalPart = v.substr(v.indexOf(".") + 1);
if(decimalPart && decimalPart.length > 0){
if(decimalPart.length > 18){
this.einfo = m_Valid52Info.info("scalemore",oEle.elname);
return false;
}
if(decimalPart.length > ele.getAttribute(attr)){
this.einfo = m_Valid52Info.info("scaleexceed",oEle.elname,ele.getAttribute(attr));
return false;
}
}
}
return true;
}
};
return {
validate : function(){
var attrs = ['required', oEle.getAttribute("pattern").toLowerCase(), 'min', 'max', 'scale'];
return m_Valid52Helper._validator.call(this, oEle, attrs, helper);
}
};
},
'double' : function(oEle){
return m_Valid52Helper.number(oEle);
}
});

if(!window.wcm) wcm = {};
if(!wcm.LANG) wcm.LANG = {};
WCMLANG = wcm.LANG;
Ext.ns('wcm.ColorPicker');
Number.prototype.toColorPart = function() {
var digits = this.toString(16);
if (this < 16) return '0' + digits;
return digits;
} 
wcm.ColorPicker = function(sid){
var m_sColorPicker = (function(){
var arrFontColors = '000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF' ;
var aColors = arrFontColors.toString().split(',') ;
var iCounter = 0 ;
var arr = ['<table border="0" cellPadding="0" cellspacing="0" style="table-layout:fixed" class="ForceBaseFont">'];
arr.push('<tr><td colspan="8">',
'<table cellspacing="0" cellpadding="0" width="100%" border="0" class="coloritem colorheader">',
'<tr unselectable="on">',
'<td unselectable="on"><div class="ColorBoxBorder" unselectable="on"><div class="ColorBox" style="background-color: #000000" unselectable="on"></div></div></td>',
'<td nowrap width="100%" align="center" unselectable="on">','自动','</td>',
'</tr>',
'</table>',
'</td></tr>'
);
while ( iCounter < aColors.length ){
arr.push('<tr>');
for ( var i = 0 ; i < 8 && iCounter < aColors.length ; i++, iCounter++ ){
arr.push('<td unselectable="on">');
arr.push('<div class="coloritem ColorBoxBorder" unselectable="on"><div class="ColorBox" style="background-color: #');
arr.push(aColors[iCounter]);
arr.push('" unselectable="on"></div></div>');
arr.push('</td>');
}
arr.push('</tr>');
}
arr.push('</table>');
return arr.join('');
})();
function getColorItem(target){
while(target!=null && target.tagName!='BODY'){
if(Element.hasClassName(target, 'coloritem'))return target;
target = target.parentNode;
}
return null;
}
this.id = sid || 'colorpicker';
var div = $(this.id);
if(div==null){
div = document.createElement('div');
div.id = this.id;
document.body.appendChild(div);
div.className = 'colorpicker';
Element.hide(div);
}
Element.update(this.id, m_sColorPicker);
var caller = this;
Ext.get(this.id).on('click', function(event, target){
var coloritem = getColorItem(target);
if(coloritem==null)return;
var sColor = '';
if(coloritem.tagName!='TABLE'){
sColor = coloritem.getElementsByTagName('DIV')[0].style.backgroundColor;
if (/rgb/i.test(sColor)){
var arr=eval(sColor.replace("rgb","new Array"));
sColor="#"+Number(arr[0]).toColorPart()+Number(arr[1]).toColorPart()+Number(arr[2]).toColorPart();
}
}
if(caller.doAfterClick)caller.doAfterClick(sColor);
});
var lastitem = null;
Ext.get(this.id).on('mouseover', function(event, target){
var coloritem = getColorItem(target);
if(coloritem==null)return;
if(coloritem==lastitem)return;
if(lastitem){
if(lastitem.tagName=='TABLE'){
Element.removeClassName(lastitem, 'colorheader1');
Element.addClassName(lastitem, 'colorheader');
}else{
Element.removeClassName(lastitem, 'ColorBoxBorder1');
Element.addClassName(lastitem, 'ColorBoxBorder');
}
}
if(coloritem.tagName=='TABLE'){
Element.removeClassName(coloritem, 'colorheader');
Element.addClassName(coloritem, 'colorheader1');
}else{
Element.removeClassName(coloritem, 'ColorBoxBorder');
Element.addClassName(coloritem, 'ColorBoxBorder1');
}
lastitem = coloritem;
});
};

if(!window.wcm) wcm = {};
if(!wcm.LANG) wcm.LANG = {};
WCMLANG = wcm.LANG;
var YUIConnect = {
asyncRequest:function(method, uri, callback){
var id = this._tid;
var frmId = 'yui' + id;
var io = $(frmId);
var fm = this.fm;
fm.action = uri;
fm.enctype = 'multipart/form-data';
fm.method = 'POST';
fm.target = frmId;
try{
fm.submit();
}catch(err){
alert(err.message+ WCMLANG["UPLOAD_1"] || '\n原因：文件路径不存在或者非本地文件路径。');
return;
}
var uploadCallback = function(){
var obj = {};
obj.tId = id;
var doc = io.contentWindow.document;
obj.responseText = doc.body ? doc.body.innerHTML : null;
obj.responseXML = doc.XMLDocument ? doc.XMLDocument : doc;
obj.argument = callback.argument;
if(callback.upload){
callback.upload.apply(callback.scope || null, [obj]);
}
Event.stopObserving(io, 'load', uploadCallback);
setTimeout(function(){ document.body.removeChild(io); }, 100);
};
Event.observe(io, 'load', uploadCallback);
this._tid++;
},
setForm:function(formId, isUpload, secureUri){
secureUri = secureUri ? 'javascript:false' : "about:blank";
var frmId = 'yui' + this._tid;
try{
var io = document.createElement('<IFRAME id="' + frmId + '" name="' + frmId + '">');
}catch(error){
var io = document.createElement('IFRAME');
}
if(!io.getAttribute("name")){
io.id = frmId;
io.name = frmId;
}
var ua = navigator.userAgent.toLowerCase();
var isIE = ua.indexOf("msie") > -1;
if(isIE){
io.src = secureUri;
}
io.style.position = 'absolute';
io.style.top = '-1000px';
io.style.left = '-1000px';
document.body.appendChild(io);
this.fm = $(formId);
}
};
var FileUploadHelper = {
validFileExt :function(_strPath,_sAllowExt){
var sAllowExt = _sAllowExt;
_strPath = _strPath || '';
if(_strPath.length<=0){
throw new Error(WCMLANG["UPLOAD_2"] || "没有选择文件！");
}
var validFileExtRe = new RegExp('.+\.('+_sAllowExt.split(',').join('|')+')$','ig');
if(!validFileExtRe.test(_strPath)){
throw new Error(String.format("只支持上传\"{0}\"格式的文件！",sAllowExt));
}
return true;
},
fileUploadedAlert : function(sResponseText, info){
if(sResponseText.match(/<!--ERROR-->/img)){
var texts = sResponseText.split('<!--##########-->');
try{
if(window.ProcessBar){
ProcessBar.close();
}
}catch(error){
}
if(info.err){
(info.err)(texts);
return;
}
if(texts[0]==0){
Ext.Msg.$alert(texts[1]);
}
else{
wcm.FaultDialog.show({
code : texts[0],
message : texts[1],
detail : texts[2],
suggestion : ''
}, WCMLANG["UPLOAD_5"] || '与服务器交互时出错啦！');
}
return true;
}
if(info.succ)(info.succ)();
return false;
}
}

