try{
var myActualTop = window;
if(window.$MsgCenter){
myActualTop = $MsgCenter.getActualTop();
}
}catch(error){
}
Calendar = function (firstDayOfWeek, dateStr, onSelected, onClose, bFlatDisplay) {
this.bFlatDisplay = bFlatDisplay || false;
this.activeDiv = null;
this.currentDateEl = null;
this.getDateStatus = null;
this.getDateToolTip = null;
this.getDateText = null;
this.timeout = null;
this.onSelected = onSelected || null;
this.onClose = onClose || null;
this.dragging = false;
this.hidden = false;
this.minYear = 1970;
this.maxYear = 2050;
this.dateFormat = Calendar._TT["DEF_DATE_FORMAT"];
this.ttDateFormat = Calendar._TT["TT_DATE_FORMAT"];
this.isPopup = true;
this.weekNumbers = true;
this.firstDayOfWeek = typeof firstDayOfWeek == "number" ? firstDayOfWeek : Calendar._FD; // 0 for Sunday, 1 for Monday, etc.
this.showsOtherMonths = false;
this.dateStr = dateStr;
this.ar_days = null;
this.showsTime = false;
this.time24 = true;
this.yearStep = 2;
this.hiliteToday = true;
this.multiple = null;
this.table = null;
this.element = null;
this.tbody = null;
this.firstdayname = null;
this.monthsCombo = null;
this.yearsCombo = null;
this.hilitedMonth = null;
this.activeMonth = null;
this.hilitedYear = null;
this.activeYear = null;
this.dateClicked = false;
if (typeof Calendar._SDN == "undefined") {
if (typeof Calendar._SDN_len == "undefined")
Calendar._SDN_len = 3;
var ar = new Array();
for (var i = 8; i > 0;) {
ar[--i] = Calendar._DN[i].substr(0, Calendar._SDN_len);
}
Calendar._SDN = ar;
if (typeof Calendar._SMN_len == "undefined")
Calendar._SMN_len = 3;
ar = new Array();
for (var i = 12; i > 0;) {
ar[--i] = Calendar._MN[i].substr(0, Calendar._SMN_len);
}
Calendar._SMN = ar;
}
};
Calendar._C = null;
Calendar.is_ie = ( /msie/i.test(navigator.userAgent) &&
!/opera/i.test(navigator.userAgent) );
Calendar.is_ie5 = ( Calendar.is_ie && /msie 5\.0/i.test(navigator.userAgent) );
Calendar.is_opera = /opera/i.test(navigator.userAgent);
Calendar.is_khtml = /Konqueror|Safari|KHTML/i.test(navigator.userAgent);
Calendar.sel;
Calendar.getAbsolutePos = function(el) {
var SL = 0, ST = 0;
var is_div = /^div$/i.test(el.tagName);
if (is_div && el.scrollLeft)
SL = el.scrollLeft;
if (is_div && el.scrollTop)
ST = el.scrollTop;
var r = { x: el.offsetLeft - SL, y: el.offsetTop - ST };
if (el.offsetParent) {
var tmp = this.getAbsolutePos(el.offsetParent);
r.x += tmp.x;
r.y += tmp.y;
}
return r;
};
Calendar.isRelated = function (el, evt) {
var related = evt.relatedTarget;
if (!related) {
var type = evt.type;
if (type == "mouseover") {
related = evt.fromElement;
} else if (type == "mouseout") {
related = evt.toElement;
}
}
while (related) {
if (related == el) {
return true;
}
related = related.parentNode;
}
return false;
};
Calendar.removeClass = function(el, className) {
if (!(el && el.className)) {
return;
}
var cls = el.className.split(" ");
var ar = new Array();
for (var i = cls.length; i > 0;) {
if (cls[--i] != className) {
ar[ar.length] = cls[i];
}
}
el.className = ar.join(" ");
};
Calendar.addClass = function(el, className) {
Calendar.removeClass(el, className);
el.className += " " + className;
};
Calendar.getElement = function(ev) {
var f = Calendar.is_ie ? (myActualTop.event || window.event).srcElement : ev.currentTarget;
while (f.nodeType != 1 || /^div$/i.test(f.tagName))
f = f.parentNode;
return f;
};
Calendar.getTargetElement = function(ev) {
var f = Calendar.is_ie ? myActualTop.event.srcElement : ev.target;
while (f.nodeType != 1)
f = f.parentNode;
return f;
};
Calendar.stopEvent = function(ev) {
ev || (ev = myActualTop.event);
if (Calendar.is_ie) {
ev.cancelBubble = true;
ev.returnValue = false;
} else {
ev.preventDefault();
ev.stopPropagation();
}
return false;
};
Calendar.addEvent = function(el, evname, func) {
if (el.attachEvent) {
el.attachEvent("on" + evname, myActualTop.func||func);
} else if (el.addEventListener) {
el.addEventListener(evname, myActualTop.func||func, true);
} else {
el["on" + evname] = func;
}
};
Calendar.removeEvent = function(el, evname, func) {
if (el.detachEvent) {
el.detachEvent("on" + evname, func);
} else if (el.removeEventListener) {
el.removeEventListener(evname, func, true);
} else {
el["on" + evname] = null;
}
};
Calendar.createElement = function(type, parent) {
var el = null;
if (document.createElementNS) {
el = myActualTop.document.createElementNS("http://www.w3.org/1999/xhtml", type);
} else {
el = myActualTop.document.createElement(type);
}
if (typeof parent != "undefined") {
parent.appendChild(el);
}
return el;
};
Calendar._add_evs = function(el) {
with (Calendar) {
addEvent(el, "mouseover", dayMouseOver);
addEvent(el, "mousedown", dayMouseDown);
addEvent(el, "mouseout", dayMouseOut);
addEvent(el, "dblclick", dayMouseDblClick);
if (is_ie) {
el.setAttribute("unselectable", true);
}
}
};
Calendar.findMonth = function(el) {
if (typeof el.month != "undefined") {
return el;
} else if (typeof el.parentNode.month != "undefined") {
return el.parentNode;
}
return null;
};
Calendar.findYear = function(el) {
if (typeof el.year != "undefined") {
return el;
} else if (typeof el.parentNode.year != "undefined") {
return el.parentNode;
}
return null;
};
Calendar.showMonthsCombo = function () {
var cal = Calendar._C;
if (!cal) {
return false;
}
var cal = cal;
var cd = cal.activeDiv;
var mc = cal.monthsCombo;
if (cal.hilitedMonth) {
Calendar.removeClass(cal.hilitedMonth, "hilite");
}
if (cal.activeMonth) {
Calendar.removeClass(cal.activeMonth, "active");
}
var mon = cal.monthsCombo.getElementsByTagName("div")[cal.date.getMonth()];
Calendar.addClass(mon, "active");
cal.activeMonth = mon;
var s = mc.style;
s.display = "block";
if (cd.navtype < 0)
s.left = cd.offsetLeft + "px";
else {
var mcw = mc.offsetWidth;
if (typeof mcw == "undefined")
mcw = 50;
s.left = (cd.offsetLeft + cd.offsetWidth - mcw) + "px";
}
s.top = (cd.offsetTop + cd.offsetHeight) + "px";
};
Calendar.showYearsCombo = function (fwd) {
var cal = Calendar._C;
if (!cal) {
return false;
}
var cal = cal;
var cd = cal.activeDiv;
var yc = cal.yearsCombo;
if (cal.hilitedYear) {
Calendar.removeClass(cal.hilitedYear, "hilite");
}
if (cal.activeYear) {
Calendar.removeClass(cal.activeYear, "active");
}
cal.activeYear = null;
var Y = cal.date.getFullYear() + (fwd ? 1 : -1);
var yr = yc.firstChild;
var show = false;
for (var i = 12; i > 0; --i) {
if (Y >= cal.minYear && Y <= cal.maxYear) {
yr.innerHTML = Y;
yr.year = Y;
yr.style.display = "block";
show = true;
} else {
yr.style.display = "none";
}
yr = yr.nextSibling;
Y += fwd ? cal.yearStep : -cal.yearStep;
}
if (show) {
var s = yc.style;
s.display = "block";
if (cd.navtype < 0)
s.left = cd.offsetLeft + "px";
else {
var ycw = yc.offsetWidth;
if (typeof ycw == "undefined")
ycw = 50;
s.left = (cd.offsetLeft + cd.offsetWidth - ycw) + "px";
}
s.top = (cd.offsetTop + cd.offsetHeight) + "px";
}
};
Calendar.tableMouseUp = function(ev) {
var cal = Calendar._C;
if (!cal) {
return false;
}
if (cal.timeout) {
clearTimeout(cal.timeout);
}
var el = cal.activeDiv;
if (!el) {
return false;
}
var target = Calendar.getTargetElement(ev);
ev || (ev = myActualTop.event);
Calendar.removeClass(el, "active");
if (target == el || target.parentNode == el) {
Calendar.cellClick(el, ev);
}
var mon = Calendar.findMonth(target);
var date = null;
if (mon) {
date = new Date(cal.date);
if (mon.month != date.getMonth()) {
date.setMonth(mon.month);
cal.setDate(date);
cal.dateClicked = false;
cal.callHandler();
}
} else {
var year = Calendar.findYear(target);
if (year) {
date = new Date(cal.date);
if (year.year != date.getFullYear()) {
date.setFullYear(year.year);
cal.setDate(date);
cal.dateClicked = false;
cal.callHandler();
}
}
}
with (Calendar) {
removeEvent(myActualTop.document, "mouseup", tableMouseUp);
removeEvent(myActualTop.document, "mouseover", tableMouseOver);
removeEvent(myActualTop.document, "mousemove", tableMouseOver);
cal._hideCombos();
_C = null;
return stopEvent(ev);
}
};
Calendar.tableMouseOver = function (ev) {
var cal = Calendar._C;
if (!cal) {
return;
}
var el = cal.activeDiv;
var target = Calendar.getTargetElement(ev);
if (target == el || target.parentNode == el) {
Calendar.addClass(el, "hilite active");
Calendar.addClass(el.parentNode, "rowhilite");
} else {
if (typeof el.navtype == "undefined" || (el.navtype != 50 && (el.navtype == 0 || Math.abs(el.navtype) > 2)))
Calendar.removeClass(el, "active");
Calendar.removeClass(el, "hilite");
Calendar.removeClass(el.parentNode, "rowhilite");
}
ev || (ev = myActualTop.event);
if (el.navtype == 50 && target != el) {
var pos = Calendar.getAbsolutePos(el);
var w = el.offsetWidth;
var x = ev.clientX;
var dx;
var decrease = true;
if (x > pos.x + w) {
dx = x - pos.x - w;
decrease = false;
} else
dx = pos.x - x;
if (dx < 0) dx = 0;
var range = el._range;
var current = el._current;
var count = Math.floor(dx / 10) % range.length;
for (var i = range.length; --i >= 0;)
if (range[i] == current)
break;
while (count-- > 0)
if (decrease) {
if (--i < 0)
i = range.length - 1;
} else if ( ++i >= range.length )
i = 0;
var newval = range[i];
el.innerHTML = newval;
cal.onUpdateTime();
}
var mon = Calendar.findMonth(target);
if (mon) {
if (mon.month != cal.date.getMonth()) {
if (cal.hilitedMonth) {
Calendar.removeClass(cal.hilitedMonth, "hilite");
}
Calendar.addClass(mon, "hilite");
cal.hilitedMonth = mon;
} else if (cal.hilitedMonth) {
Calendar.removeClass(cal.hilitedMonth, "hilite");
}
} else {
if (cal.hilitedMonth) {
Calendar.removeClass(cal.hilitedMonth, "hilite");
}
var year = Calendar.findYear(target);
if (year) {
if (year.year != cal.date.getFullYear()) {
if (cal.hilitedYear) {
Calendar.removeClass(cal.hilitedYear, "hilite");
}
Calendar.addClass(year, "hilite");
cal.hilitedYear = year;
} else if (cal.hilitedYear) {
Calendar.removeClass(cal.hilitedYear, "hilite");
}
} else if (cal.hilitedYear) {
Calendar.removeClass(cal.hilitedYear, "hilite");
}
}
return Calendar.stopEvent(ev);
};
Calendar.tableMouseDown = function (ev) {
if (Calendar.getTargetElement(ev) == Calendar.getElement(ev)) {
return Calendar.stopEvent(ev);
}
};
Calendar.calDragIt = function (ev) {
var cal = Calendar._C;
if (!(cal && cal.dragging)) {
return false;
}
var posX;
var posY;
if (Calendar.is_ie) {
posY = myActualTop.event.clientY + myActualTop.document.body.scrollTop;
posX = myActualTop.event.clientX + myActualTop.document.body.scrollLeft;
} else {
posX = ev.pageX;
posY = ev.pageY;
}
cal.hideShowCovered();
var st = cal.element.style;
st.left = (posX - cal.xOffs) + "px";
st.top = (posY - cal.yOffs) + "px";
var iframeDom = cal.element.nextSibling;
Position.clone(cal.element, iframeDom);
return Calendar.stopEvent(ev);
};
Calendar.calDragEnd = function (ev) {
var cal = Calendar._C;
if (!cal) {
return false;
}
cal.dragging = false;
with (Calendar) {
removeEvent(myActualTop.document, "mousemove", calDragIt);
removeEvent(myActualTop.document, "mouseup", calDragEnd);
tableMouseUp(ev);
}
cal.hideShowCovered();
var iframeDom = cal.element.nextSibling;
iframeDom.style.display = 'none';
};
Calendar.dayMouseDown = function(ev) {
var el = Calendar.getElement(ev);
if (el.disabled) {
return false;
}
var cal = el.calendar;
cal.activeDiv = el;
Calendar._C = cal;
if (el.navtype != 300) with (Calendar) {
if (el.navtype == 50) {
el._current = el.innerHTML;
addEvent(myActualTop.document, "mousemove", tableMouseOver);
} else
addEvent(myActualTop.document, Calendar.is_ie5 ? "mousemove" : "mouseover", tableMouseOver);
addClass(el, "hilite active");
addEvent(myActualTop.document, "mouseup", tableMouseUp);
} else if (cal.isPopup) {
cal._dragStart(ev);
}
if (el.navtype == -1 || el.navtype == 1) {
if (cal.timeout) clearTimeout(cal.timeout);
cal.timeout = setTimeout("Calendar.showMonthsCombo()", 250);
} else if (el.navtype == -2 || el.navtype == 2) {
if (cal.timeout) clearTimeout(cal.timeout);
cal.timeout = setTimeout((el.navtype > 0) ? "Calendar.showYearsCombo(true)" : "Calendar.showYearsCombo(false)", 250);
} else {
cal.timeout = null;
}
return Calendar.stopEvent(ev);
};
Calendar.dayMouseDblClick = function(ev) {
Calendar.cellClick(Calendar.getElement(ev), ev || myActualTop.event);
if (Calendar.is_ie) {
try{
myActualTop.document.selection.empty();
}catch(e){
}
}
var calendar = Calendar.getElement(ev).calendar;
ev && calendar.callCloseHandler();
var srcElement = Calendar.getTargetElement(ev);
if((" "+srcElement.className+" ").indexOf("day") >= 0 && Calendar.extraDealOnDblClick){//huxiejin@2006-12-30
Calendar.extraDealOnDblClick(calendar.sel.id, calendar.sel.value);
}
};
Calendar.dayMouseOver = function(ev) {
var el = Calendar.getElement(ev);
if (Calendar.isRelated(el, ev) || Calendar._C || el.disabled) {
return false;
}
if (el.ttip) {
if (el.ttip.substr(0, 1) == "_") {
el.ttip = el.caldate.print(el.calendar.ttDateFormat) + el.ttip.substr(1);
}
el.calendar.tooltips.innerHTML = el.ttip;
}
if (el.navtype != 300) {
Calendar.addClass(el, "hilite");
if (el.caldate) {
Calendar.addClass(el.parentNode, "rowhilite");
}
}
return Calendar.stopEvent(ev);
};
Calendar.dayMouseOut = function(ev) {
with (Calendar) {
var el = getElement(ev);
if (isRelated(el, ev) || _C || el.disabled)
return false;
removeClass(el, "hilite");
if (el.caldate)
removeClass(el.parentNode, "rowhilite");
if (el.calendar)
el.calendar.tooltips.innerHTML = _TT["SEL_DATE"];
return stopEvent(ev);
}
};
Calendar.cellClick = function(el, ev) {
var cal = el.calendar;
var closing = false;
var newdate = false;
var date = null;
if (typeof el.navtype == "undefined") {
if (cal.currentDateEl) {
Calendar.removeClass(cal.currentDateEl, "selected");
Calendar.addClass(el, "selected");
closing = (cal.currentDateEl == el);
if (!closing) {
cal.currentDateEl = el;
}
}
cal.date.setDateOnly(el.caldate);
date = cal.date;
var other_month = !(cal.dateClicked = !el.otherMonth);
if (!other_month && !cal.currentDateEl)
cal._toggleMultipleDate(new Date(date));
else
newdate = !el.disabled;
if (other_month)
cal._init(cal.firstDayOfWeek, date);
} else {
if (el.navtype == 200) {
Calendar.removeClass(el, "hilite");
cal.callCloseHandler();
return;
}
date = new Date(cal.date);
if (el.navtype == 0)
date.setDateOnly(new Date());
cal.dateClicked = false;
var year = date.getFullYear();
var mon = date.getMonth();
function setMonth(m) {
var day = date.getDate();
var max = date.getMonthDays(m);
if (day > max) {
date.setDate(max);
}
date.setMonth(m);
};
switch (el.navtype) {
case 400:
Calendar.removeClass(el, "hilite");
var text = Calendar._TT["ABOUT"];
if (typeof text != "undefined") {
text += cal.showsTime ? Calendar._TT["ABOUT_TIME"] : "";
} else {
text = "Help and about box text is not translated into this language.\n" +
"If you know this language and you feel generous please update\n" +
"the corresponding file in \"lang\" subdir to match calendar-en.js\n" +
"and send it back to <mihai_bazon@yahoo.com> to get it into the distribution ;-)\n\n" +
"Thank you!\n" +
"http://dynarch.com/mishoo/calendar.epl\n";
}
CTRSAction_alert("CopyRight (c) TRS WCM");
return;
case -2:
if (year > cal.minYear) {
date.setFullYear(year - 1);
}
break;
case -1:
if (mon > 0) {
setMonth(mon - 1);
} else if (year-- > cal.minYear) {
date.setFullYear(year);
setMonth(11);
}
break;
case 1:
if (mon < 11) {
setMonth(mon + 1);
} else if (year < cal.maxYear) {
date.setFullYear(year + 1);
setMonth(0);
}
break;
case 2:
if (year < cal.maxYear) {
date.setFullYear(year + 1);
}
break;
case 100:
cal.setFirstDayOfWeek(el.fdow);
return;
case 50:
var range = el._range;
var current = el.innerHTML;
for (var i = range.length; --i >= 0;)
if (range[i] == current)
break;
if (ev && ev.shiftKey) {
if (--i < 0)
i = range.length - 1;
} else if ( ++i >= range.length )
i = 0;
var newval = range[i];
el.innerHTML = newval;
cal.onUpdateTime();
return;
case 0:
if ((typeof cal.getDateStatus == "function") &&
cal.getDateStatus(date, date.getFullYear(), date.getMonth(), date.getDate())) {
return false;
}
break;
}
if (!date.equalsTo(cal.date)) {
cal.setDate(date);
newdate = true;
} else if (el.navtype == 0)
newdate = closing = true;
}
if (newdate) {
ev && cal.callHandler();
}
if (closing) {
Calendar.removeClass(el, "hilite");
cal.setDateTimeToEl(cal.date);
}
};
Calendar.prototype.setDateTimeToEl = function(dateStr) {
var date = (dateStr)?new Date(dateStr):new Date();
if(this.sel)
this.sel.value = date.print(this.dateFormat);
}
Calendar.prototype.create = function (_par) {
var parent = null;
if (! _par) {
parent = myActualTop.document.getElementsByTagName("body")[0];
this.isPopup = true;
} else {
parent = _par;
this.isPopup = false;
}
this.date = this.dateStr ? new Date(this.dateStr) : new Date();
var table = Calendar.createElement("table");
this.table = table;
table.cellSpacing = 0;
table.cellPadding = 0;
table.width = 300;
table.calendar = this;
Calendar.addEvent(table, "mousedown", Calendar.tableMouseDown);
var div = Calendar.createElement("div");
this.element = div;
div.className = "calendar";
if (this.isPopup) {
div.style.position = "absolute";
div.style.display = "none";
}
div.appendChild(table);
var thead = Calendar.createElement("thead", table);
var cell = null;
var row = null;
var cal = this;
var hh = function (text, cs, navtype) {
cell = Calendar.createElement("td", row);
cell.colSpan = cs;
cell.className = "button";
if (navtype != 0 && Math.abs(navtype) <= 2)
cell.className += " nav";
Calendar._add_evs(cell);
cell.calendar = cal;
cell.navtype = navtype;
cell.innerHTML = "<div unselectable='on'>" + text + "</div>";
return cell;
};
row = Calendar.createElement("tr", thead);
var title_length = 6;
(this.isPopup) && --title_length;
(this.weekNumbers) && ++title_length;
hh(" ", 1, -3).ttip = "";
this.title = hh("", title_length, 300);
this.title.className = "title";
if (this.isPopup) {
this.title.ttip = Calendar._TT["DRAG_TO_MOVE"];
this.title.style.cursor = "move";
hh("&#x00d7;", 1, 200).ttip = Calendar._TT["CLOSE"];
}
row = Calendar.createElement("tr", thead);
row.className = "headrow";
this._nav_py = hh("&#x00ab;", 1, -2);
this._nav_py.ttip = Calendar._TT["PREV_YEAR"];
this._nav_pm = hh("&#x2039;", 1, -1);
this._nav_pm.ttip = Calendar._TT["PREV_MONTH"];
this._nav_now = hh(Calendar._TT["TODAY"], this.weekNumbers ? 4 : 3, 0);
this._nav_now.ttip = Calendar._TT["GO_TODAY"];
this._nav_nm = hh("&#x203a;", 1, 1);
this._nav_nm.ttip = Calendar._TT["NEXT_MONTH"];
this._nav_ny = hh("&#x00bb;", 1, 2);
this._nav_ny.ttip = Calendar._TT["NEXT_YEAR"];
row = Calendar.createElement("tr", thead);
row.className = "daynames";
if (this.weekNumbers) {
cell = Calendar.createElement("td", row);
cell.className = "name wn";
cell.innerHTML = Calendar._TT["WK"];
}
for (var i = 7; i > 0; --i) {
cell = Calendar.createElement("td", row);
if (!i) {
cell.navtype = 100;
cell.calendar = this;
Calendar._add_evs(cell);
}
}
this.firstdayname = (this.weekNumbers) ? row.firstChild.nextSibling : row.firstChild;
this._displayWeekdays();
var tbody = Calendar.createElement("tbody", table);
this.tbody = tbody;
for (i = 6; i > 0; --i) {
row = Calendar.createElement("tr", tbody);
if (this.weekNumbers) {
cell = Calendar.createElement("td", row);
}
for (var j = 7; j > 0; --j) {
cell = Calendar.createElement("td", row);
cell.calendar = this;
Calendar._add_evs(cell);
}
}
if (this.showsTime) {
row = Calendar.createElement("tr", tbody);
row.className = "time";
cell = Calendar.createElement("td", row);
cell.className = "time";
cell.colSpan = 2;
cell.innerHTML = Calendar._TT["TIME"] || "&nbsp;";
cell = Calendar.createElement("td", row);
cell.className = "time";
cell.colSpan = this.weekNumbers ? 4 : 3;
(function(){
function makeTimePart(className, init, range_start, range_end) {
var part = Calendar.createElement("span", cell);
part.className = className;
part.innerHTML = init;
part.calendar = cal;
part.ttip = Calendar._TT["TIME_PART"];
part.navtype = 50;
part._range = [];
if (typeof range_start != "number")
part._range = range_start;
else {
for (var i = range_start; i <= range_end; ++i) {
var txt;
if (i < 10 && range_end >= 10) txt = '0' + i;
else txt = '' + i;
part._range[part._range.length] = txt;
}
}
Calendar._add_evs(part);
return part;
};
function setValue(oSrcElement, nKeyCode){
var oElName = oSrcElement.name;
if(!oSrcElement.beOnFocus || oSrcElement.value==""){
if(!cal.bFlatDisplay)
oSrcElement.value = (nKeyCode - 48);
oSrcElement.beOnFocus = true;
}else{
var sTemp = oSrcElement.value + "";
if(sTemp.length > 1){
sTemp = sTemp.substring(1) +""+ (nKeyCode - 48);
if(oElName == "hour"){
if(parseInt(sTemp) > 23) return;
oSrcElement.value = sTemp;
}
if(oElName == "minute"){
if(parseInt(sTemp) > 59) return;
oSrcElement.value = sTemp;
}
} else{
sTemp = oSrcElement.value +""+ (nKeyCode - 48);
if(oElName == "hour"){
if(parseInt(sTemp) > 23){
oSrcElement.value = "0" + oSrcElement.value;
return;
}
oSrcElement.value = sTemp;
}
if(oElName == "minute"){
if(parseInt(sTemp) > 59){
oSrcElement.value = "0" + oSrcElement.value;
return;
}
oSrcElement.value = sTemp;
}
}
}
if(oElName == "hour"){
var sHour = cal.date.getHours();
if(sHour != oSrcElement.value)
cal.date.setHours(oSrcElement.value);
}
if(oElName == "minute"){
var sMinute = cal.date.getMinutes();
if(sMinute != oSrcElement.value)
cal.date.setMinutes(oSrcElement.value);
}
}
function getNumberInput(){
var event = Ext.lib.Event.getEvent();
var oSrcElement = Ext.lib.Event.getTarget(event);
var oElName = oSrcElement.name;
var nKeyCode = event.keyCode;
if(event.shiftKey) return false;
if(nKeyCode>47 && nKeyCode<58){
setValue(oSrcElement, nKeyCode);
} else {
if(nKeyCode>95 && nKeyCode<106){
setValue(oSrcElement, (nKeyCode-48));
} else {
if(nKeyCode==46 || nKeyCode==8){
oSrcElement.value = "";
}else{
return false;
}
}
}
};
function selectNumber(){
var event = Ext.lib.Event.getEvent();
var srcElement = Ext.lib.Event.getTarget(event);
srcElement.beOnFocus = false;
try{
srcElement.select();
}catch(error){}
};
function disabledFunction(){return false;}
function beFocusOut(){
var event = Ext.lib.Event.getEvent();
var oSrcElement = Ext.lib.Event.getTarget(event);
var oElName = oSrcElement.name;
oSrcElement.beOnFocus = false;
if(oSrcElement.value.length == 1)
oSrcElement.value = "0" + oSrcElement.value;
if(oElName == "hour"){
var sHour = cal.date.getHours();
if(sHour != oSrcElement.value)
cal.date.setHours(oSrcElement.value);
}
if(oElName == "minute"){
var sMinute = cal.date.getMinutes();
if(sMinute != oSrcElement.value)
cal.date.setMinutes(oSrcElement.value);
}
if(cal.bFlatDisplay){
var elPreview = document.getElementById("preview");
var elValue = document.getElementById("DateValue");
var sDate = cal.date.print(cal.dateFormat);
elPreview.innerHTML = sDate;
elValue.value = sDate;
}
};
function makeTimeInput(className, init, range_start, range_end) {
var part = Calendar.createElement("INPUT", cell);
part.name = className;
part.type = "text";
part.style.width = "24px";
part.maxLength = 2;
part.beOnFocus = false;
part.className = className;
Calendar.addEvent(part, "drag", disabledFunction);
Calendar.addEvent(part, "keydown", getNumberInput);
Calendar.addEvent(part, "mouseover", selectNumber);
Calendar.addEvent(part, "mouseup", selectNumber);
Calendar.addEvent(part, "focusout", beFocusOut);
part.value = init;
part.calendar = cal;
part.ttip = Calendar._TT["TIME_PART"];
part.navtype = 50;
part._range = [];
if (typeof range_start != "number")
part._range = range_start;
else {
for (var i = range_start; i <= range_end; ++i) {
var txt;
if (i < 10 && range_end >= 10) txt = '0' + i;
else txt = '' + i;
part._range[part._range.length] = txt;
}
}
return part;
};
var hrs = cal.date.getHours();
var mins = cal.date.getMinutes();
var t12 = !cal.time24;
var pm = (hrs > 12);
if (t12 && pm) hrs -= 12;
var H = makeTimeInput("hour", hrs, t12 ? 1 : 0, t12 ? 12 : 23);
var span = Calendar.createElement("span", cell);
span.innerHTML = ":";
span.className = "colon";
var M = makeTimeInput("minute", mins, 0, 59);
var AP = null;
cell = Calendar.createElement("td", row);
cell.className = "time";
cell.colSpan = 2;
if (t12)
AP = makeTimePart("ampm", pm ? "pm" : "am", ["am", "pm"]);
else
cell.innerHTML = "&nbsp;";
cal.onSetTime = function() {
var pm, hrs = this.date.getHours(),
mins = this.date.getMinutes();
if (t12) {
pm = (hrs >= 12);
if (pm) hrs -= 12;
if (hrs == 0) hrs = 12;
AP.innerHTML = pm ? "pm" : "am";
}
H.value = (hrs < 10) ? ("0" + hrs) : hrs;
M.value = (mins < 10) ? ("0" + mins) : mins;
};
cal.onUpdateTime = function() {
var date = this.date;
var h = parseInt(H.value, 10);
if (t12) {
if (/pm/i.test(AP.innerHTML) && h < 12)
h += 12;
else if (/am/i.test(AP.innerHTML) && h == 12)
h = 0;
}
var d = date.getDate();
var m = date.getMonth();
var y = date.getFullYear();
date.setHours(h);
date.setMinutes(parseInt(M.value, 10));
date.setFullYear(y);
date.setMonth(m);
date.setDate(d);
this.dateClicked = false;
this.callHandler();
};
})();
} else {
this.onSetTime = this.onUpdateTime = function() {};
}
var tfoot = Calendar.createElement("tfoot", table);
row = Calendar.createElement("tr", tfoot);
row.className = "footrow";
cell = hh(Calendar._TT["SEL_DATE"], this.weekNumbers ? 8 : 7, 300);
cell.className = "ttip";
if (this.isPopup) {
cell.ttip = Calendar._TT["DRAG_TO_MOVE"];
cell.style.cursor = "move";
}
this.tooltips = cell;
div = Calendar.createElement("div", this.element);
this.monthsCombo = div;
div.className = "combo";
for (i = 0; i < Calendar._MN.length; ++i) {
var mn = Calendar.createElement("div");
mn.className = Calendar.is_ie ? "label-IEfix" : "label";
mn.month = i;
mn.innerHTML = Calendar._SMN[i];
div.appendChild(mn);
}
div = Calendar.createElement("div", this.element);
this.yearsCombo = div;
div.className = "combo";
for (i = 12; i > 0; --i) {
var yr = Calendar.createElement("div");
yr.className = Calendar.is_ie ? "label-IEfix" : "label";
div.appendChild(yr);
}
this._init(this.firstDayOfWeek, this.date);
parent.appendChild(this.element);
new Insertion.After(this.element, '<iframe src="about:blank" frameborder="0" style="display:none;position:absolute;"></iframe>');
this.element.style.zIndex = 999999;
this.element.nextSibling.style.zIndex = 999998;
return this.element;
};
Calendar._keyEvent = function(ev) {
var cal = myActualTop._dynarch_popupCalendar;
if (!cal || cal.multiple)
return false;
(Calendar.is_ie) && (ev = myActualTop.event);
var act = (Calendar.is_ie || ev.type == "keypress"),
K = ev.keyCode;
if (ev.ctrlKey) {
switch (K) {
case 37: 
act && Calendar.cellClick(cal._nav_pm);
break;
case 38: 
act && Calendar.cellClick(cal._nav_py);
break;
case 39: 
act && Calendar.cellClick(cal._nav_nm);
break;
case 40: 
act && Calendar.cellClick(cal._nav_ny);
break;
default:
return false;
}
} else switch (K) {
case 32: 
Calendar.cellClick(cal._nav_now);
break;
case 27: 
act && cal.callCloseHandler();
break;
case 37: 
case 38: 
case 39: 
case 40: 
if (act) {
var prev, x, y, ne, el, step;
prev = K == 37 || K == 38;
step = (K == 37 || K == 39) ? 1 : 7;
function setVars() {
el = cal.currentDateEl;
var p = el.pos;
x = p & 15;
y = p >> 4;
ne = cal.ar_days[y][x];
};setVars();
function prevMonth() {
var date = new Date(cal.date);
date.setDate(date.getDate() - step);
cal.setDate(date);
};
function nextMonth() {
var date = new Date(cal.date);
date.setDate(date.getDate() + step);
cal.setDate(date);
};
while (1) {
switch (K) {
case 37: 
if (--x >= 0)
ne = cal.ar_days[y][x];
else {
x = 6;
K = 38;
continue;
}
break;
case 38: 
if (--y >= 0)
ne = cal.ar_days[y][x];
else {
prevMonth();
setVars();
}
break;
case 39: 
if (++x < 7)
ne = cal.ar_days[y][x];
else {
x = 0;
K = 40;
continue;
}
break;
case 40: 
if (++y < cal.ar_days.length)
ne = cal.ar_days[y][x];
else {
nextMonth();
setVars();
}
break;
}
break;
}
if (ne) {
if (!ne.disabled)
Calendar.cellClick(ne);
else if (prev)
prevMonth();
else
nextMonth();
}
}
break;
case 13: 
if (act)
Calendar.cellClick(cal.currentDateEl, ev);
break;
default:
return false;
}
return Calendar.stopEvent(ev);
};
Calendar.prototype._init = function (firstDayOfWeek, date) {
var today = new Date(),
TY = today.getFullYear(),
TM = today.getMonth(),
TD = today.getDate();
this.table.style.visibility = "hidden";
var year = date.getFullYear();
if (year < this.minYear) {
year = this.minYear;
date.setFullYear(year);
} else if (year > this.maxYear) {
year = this.maxYear;
date.setFullYear(year);
}
this.firstDayOfWeek = firstDayOfWeek;
this.date = new Date(date);
var month = date.getMonth();
var mday = date.getDate();
var no_days = date.getMonthDays();
date.setDate(1);
var day1 = (date.getDay() - this.firstDayOfWeek) % 7;
if (day1 < 0)
day1 += 7;
date.setDate(-day1);
date.setDate(date.getDate() + 1);
var row = this.tbody.firstChild;
var MN = Calendar._SMN[month];
var ar_days = this.ar_days = new Array();
var weekend = Calendar._TT["WEEKEND"];
var dates = this.multiple ? (this.datesCells = {}) : null;
for (var i = 0; i < 6; ++i, row = row.nextSibling) {
var cell = row.firstChild;
if (this.weekNumbers) {
cell.className = "day wn";
cell.innerHTML = date.getWeekNumber();
cell = cell.nextSibling;
}
row.className = "daysrow";
var hasdays = false, iday, dpos = ar_days[i] = [];
for (var j = 0; j < 7; ++j, cell = cell.nextSibling, date.setDate(iday + 1)) {
iday = date.getDate();
var wday = date.getDay();
cell.className = "day";
cell.pos = i << 4 | j;
dpos[j] = cell;
var current_month = (date.getMonth() == month);
if (!current_month) {
if (this.showsOtherMonths) {
cell.className += " othermonth";
cell.otherMonth = true;
} else {
cell.className = "emptycell";
cell.innerHTML = "&nbsp;";
cell.disabled = true;
continue;
}
} else {
cell.otherMonth = false;
hasdays = true;
}
cell.disabled = false;
cell.innerHTML = this.getDateText ? this.getDateText(date, iday) : iday;
if (dates)
dates[date.print("%Y%m%d")] = cell;
if (this.getDateStatus) {
var status = this.getDateStatus(date, year, month, iday);
if (this.getDateToolTip) {
var toolTip = this.getDateToolTip(date, year, month, iday);
if (toolTip)
cell.title = toolTip;
}
if (status === true) {
cell.className += " disabled";
cell.disabled = true;
} else {
if (/disabled/i.test(status))
cell.disabled = true;
cell.className += " " + status;
}
}
if (!cell.disabled) {
cell.caldate = new Date(date);
cell.ttip = "_";
if (!this.multiple && current_month
&& iday == mday && this.hiliteToday) {
cell.className += " selected";
this.currentDateEl = cell;
}
if (date.getFullYear() == TY &&
date.getMonth() == TM &&
iday == TD) {
cell.className += " today";
cell.ttip += Calendar._TT["PART_TODAY"];
}
if (weekend.indexOf(wday.toString()) != -1)
cell.className += cell.otherMonth ? " oweekend" : " weekend";
}
}
if (!(hasdays || this.showsOtherMonths))
row.className = "emptyrow";
}
this.title.innerHTML = Calendar._MN[month] + ", " + year;
this.onSetTime();
this.table.style.visibility = "visible";
this._initMultipleDates();
};
Calendar.prototype._initMultipleDates = function() {
if (this.multiple) {
for (var i in this.multiple) {
var cell = this.datesCells[i];
var d = this.multiple[i];
if (!d)
continue;
if (cell)
cell.className += " selected";
}
}
};
Calendar.prototype._toggleMultipleDate = function(date) {
if (this.multiple) {
var ds = date.print("%Y%m%d");
var cell = this.datesCells[ds];
if (cell) {
var d = this.multiple[ds];
if (!d) {
Calendar.addClass(cell, "selected");
this.multiple[ds] = date;
} else {
Calendar.removeClass(cell, "selected");
delete this.multiple[ds];
}
}
}
};
Calendar.prototype.setDateToolTipHandler = function (unaryFunction) {
this.getDateToolTip = unaryFunction;
};
Calendar.prototype.setDate = function (date) {
if (!date.equalsTo(this.date)) {
this._init(this.firstDayOfWeek, date);
}
};
Calendar.prototype.refresh = function () {
this._init(this.firstDayOfWeek, this.date);
};
Calendar.prototype.setFirstDayOfWeek = function (firstDayOfWeek) {
this._init(firstDayOfWeek, this.date);
this._displayWeekdays();
};
Calendar.prototype.setDateStatusHandler = Calendar.prototype.setDisabledHandler = function (unaryFunction) {
this.getDateStatus = unaryFunction;
};
Calendar.prototype.setRange = function (a, z) {
this.minYear = a;
this.maxYear = z;
};
Calendar.prototype.callHandler = function () {
if (this.onSelected) {
this.onSelected(this, this.date.print(this.dateFormat));
}
};
Calendar.prototype.callCloseHandler = function () {
if (this.onClose) {
this.onClose(this);
}
this.hideShowCovered();
};
Calendar.prototype.destroy = function () {
var el = this.element.parentNode;
el.removeChild(this.element);
Calendar._C = null;
myActualTop._dynarch_popupCalendar = null;
};
Calendar.prototype.reparent = function (new_parent) {
var el = this.element;
el.parentNode.removeChild(el);
new_parent.appendChild(el);
};
Calendar._checkCalendar = function(ev) {
var calendar = myActualTop._dynarch_popupCalendar;
if (!calendar) {
return false;
}
var el = Calendar.is_ie ? Calendar.getElement(ev) : Calendar.getTargetElement(ev);
for (; el != null && el != calendar.element; el = el.parentNode);
if (el == null) {
myActualTop._dynarch_popupCalendar.callCloseHandler();
return Calendar.stopEvent(ev);
}
};
Calendar._blurEvent = function(ev){
var extEv = Ext.EventObject.setEvent(ev);
if(!extEv.within(this)){
myActualTop._dynarch_popupCalendar.callCloseHandler();
}
};
Calendar.prototype.show = function () {
var rows = this.table.getElementsByTagName("tr");
for (var i = rows.length; i > 0;) {
var row = rows[--i];
Calendar.removeClass(row, "rowhilite");
var cells = row.getElementsByTagName("td");
for (var j = cells.length; j > 0;) {
var cell = cells[--j];
Calendar.removeClass(cell, "hilite");
Calendar.removeClass(cell, "active");
}
}
this.element.style.display = "block";
this.element.focus();
this.hidden = false;
if (this.isPopup) {
myActualTop._dynarch_popupCalendar = this;
Calendar.addEvent(myActualTop.document, "mousedown", Calendar._checkCalendar);
if(this.element.setCapture){
this.element.releaseflag = false;
setTimeout(function(){
if(this.element.releaseflag) return;
this.element.setCapture(false);
}.bind(this), 100);
}
else if(document.captureEvent)
document.captureEvent(Event.CLICK | Event.MOUSEDOWN | Event.KEYDOWN);
}
this.hideShowCovered();
};
Calendar.prototype.hide = function () {
if(this.element.releaseCapture){
this.element.releaseCapture();
this.element.releaseflag = true;
}else if(document.releaseEvent){
document.releaseEvent(Event.CLICK | Event.MOUSEDOWN | Event.KEYDOWN);
}
if (this.isPopup) {
Calendar.removeEvent(myActualTop.document, "mousedown", Calendar._checkCalendar);
}
this.element.style.display = "none";
this.hidden = true;
this.hideShowCovered();
};
Calendar.prototype.showAt = function (x, y) {
var s = this.element.style;
s.left = x + "px";
s.top = y + "px";
this.show();
};
Calendar.prototype.showAtElement = function (el, opts) {
var self = this;
var p = Calendar.getAbsolutePos(el);
if (!opts || typeof opts != "string") {
this.showAt(p.x, p.y + el.offsetHeight);
return true;
}
function fixPosition(box) {
if (box.x < 0)
box.x = 0;
if (box.y < 0)
box.y = 0;
var cp = document.createElement("div");
var s = cp.style;
s.position = "absolute";
s.right = s.bottom = s.width = s.height = "0px";
document.body.appendChild(cp);
var br = Calendar.getAbsolutePos(cp);
document.body.removeChild(cp);
if (Calendar.is_ie) {
br.y += document.body.scrollTop;
br.x += document.body.scrollLeft;
} else {
br.y += window.scrollY;
br.x += window.scrollX;
}
var tmp = box.x + box.width - br.x;
if (tmp > 0) box.x -= tmp;
tmp = box.y + box.height - br.y;
if (tmp > 0) box.y -= tmp;
};
this.element.style.display = "block";
Calendar.continuation_for_the_fucking_khtml_browser = function() {
var w = self.element.offsetWidth;
var h = self.element.offsetHeight;
self.element.style.display = "none";
var valign = opts.substr(0, 1);
var halign = "l";
if (opts.length > 1) {
halign = opts.substr(1, 1);
}
switch (valign) {
case "T": p.y -= h; break;
case "B": p.y += el.offsetHeight; break;
case "C": p.y += (el.offsetHeight - h) / 2; break;
case "t": p.y += el.offsetHeight - h; break;
case "b": break; // already there
}
switch (halign) {
case "L": p.x -= w; break;
case "R": p.x += el.offsetWidth; break;
case "C": p.x += (el.offsetWidth - w) / 2; break;
case "l": p.x += el.offsetWidth - w; break;
case "r": break; // already there
}
p.width = w;
p.height = h + 40;
self.monthsCombo.style.display = "none";
fixPosition(p);
self.showAt(p.x, p.y);
};
if (Calendar.is_khtml)
setTimeout("Calendar.continuation_for_the_fucking_khtml_browser()", 10);
else
Calendar.continuation_for_the_fucking_khtml_browser();
};
Calendar.prototype.showAtElement = function (el, opts) {
var position = Position.getPageInTop(el);
var p = {x:position[0], y:position[1]};
var input = Element.previous(el);
var opts = Calendar.initConfigs[input.id] || {};
var displayType = opts["position"] || "rb";
var valign = displayType.substr(1, 1);
var halign = displayType.substr(0, 1);
var divObj = myActualTop.$(el.previousSibling.id + "_div");
var tableObj = divObj.getElementsByTagName("table")[0];
var oldDisplay = divObj.style.display;
divObj.style.display = '';
var w = tableObj.offsetWidth;
var h = tableObj.offsetHeight;
divObj.style.display = oldDisplay;
switch (valign) {
case "T": p.y -= h; break;
case "B": p.y += el.offsetHeight; break;
case "C": p.y += (el.offsetHeight - h) / 2; break;
case "t": p.y -= h; break;
case "b": p.y += el.offsetHeight; break;
}
switch (halign) {
case "L": p.x -= w; break;
case "R": p.x += el.offsetWidth; break;
case "C": p.x += (el.offsetWidth - w) / 2; break;
case "l": p.x -= w; break;
case "r": p.x += el.offsetWidth; break;
}
var p0 = Element.getFitPosition(this.element, [p.x, p.y]);
this.showAt(p0[0], p0[1]);
};
Calendar.prototype.setDateFormat = function (str) {
this.dateFormat = str;
};
Calendar.prototype.setTtDateFormat = function (str) {
this.ttDateFormat = str;
};
Calendar.prototype.parseDate = function(str, fmt) {
if (!fmt)
fmt = this.dateFormat;
this.setDate(Date.parseDate(str, fmt));
};
Calendar.prototype.hideShowCovered = function () {
if (!Calendar.is_ie && !Calendar.is_opera)
return;
function getVisib(obj){
var value = obj.style.visibility;
if (!value) {
if (document.defaultView && typeof (document.defaultView.getComputedStyle) == "function") { // Gecko, W3C
if (!Calendar.is_khtml)
value = document.defaultView.
getComputedStyle(obj, "").getPropertyValue("visibility");
else
value = '';
} else if (obj.currentStyle) {
value = obj.currentStyle.visibility;
} else
value = '';
}
return value;
};
var tags = new Array("applet", "iframe", "select");
var el = this.element;
var p = Calendar.getAbsolutePos(el);
var EX1 = p.x;
var EX2 = el.offsetWidth + EX1;
var EY1 = p.y;
var EY2 = el.offsetHeight + EY1;
for (var k = tags.length; k > 0; ) {
var ar = document.getElementsByTagName(tags[--k]);
var cc = null;
for (var i = ar.length; i > 0;) {
cc = ar[--i];
p = Calendar.getAbsolutePos(cc);
var CX1 = p.x;
var CX2 = cc.offsetWidth + CX1;
var CY1 = p.y;
var CY2 = cc.offsetHeight + CY1;
if (this.hidden || (CX1 > EX2) || (CX2 < EX1) || (CY1 > EY2) || (CY2 < EY1)) {
if (!cc.__msh_save_visibility) {
cc.__msh_save_visibility = getVisib(cc);
}
cc.style.visibility = cc.__msh_save_visibility;
} else {
if (!cc.__msh_save_visibility) {
cc.__msh_save_visibility = getVisib(cc);
}
cc.style.visibility = "hidden";
}
}
}
};
Calendar.prototype.hideShowCovered = function () {
};
Calendar.prototype._displayWeekdays = function () {
var fdow = this.firstDayOfWeek;
var cell = this.firstdayname;
var weekend = Calendar._TT["WEEKEND"];
for (var i = 0; i < 7; ++i) {
cell.className = "day name";
var realday = (i + fdow) % 7;
if (i) {
cell.ttip = Calendar._TT["DAY_FIRST"].replace("%s", Calendar._DN[realday]);
cell.navtype = 100;
cell.calendar = this;
cell.fdow = realday;
Calendar._add_evs(cell);
}
if (weekend.indexOf(realday.toString()) != -1) {
Calendar.addClass(cell, "weekend");
}
cell.innerHTML = Calendar._SDN[(i + fdow) % 7];
cell = cell.nextSibling;
}
};
Calendar.prototype._hideCombos = function () {
this.monthsCombo.style.display = "none";
this.yearsCombo.style.display = "none";
};
Calendar.prototype._dragStart = function (ev) {
if (this.dragging) {
return;
}
this.dragging = true;
var posX;
var posY;
if (Calendar.is_ie) {
posY = myActualTop.event.clientY + myActualTop.document.body.scrollTop;
posX = myActualTop.event.clientX + myActualTop.document.body.scrollLeft;
} else {
posY = ev.clientY + myActualTop.window.scrollY;
posX = ev.clientX + myActualTop.window.scrollX;
}
var st = this.element.style;
this.xOffs = posX - parseInt(st.left);
this.yOffs = posY - parseInt(st.top);
with (Calendar) {
addEvent(myActualTop.document, "mousemove", calDragIt);
addEvent(myActualTop.document, "mouseup", calDragEnd);
}
var iframeDom = this.element.nextSibling;
iframeDom.style.display = '';
};
Date._MD = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
Date.SECOND = 1000 ;
Date.MINUTE = 60 * Date.SECOND;
Date.HOUR = 60 * Date.MINUTE;
Date.DAY = 24 * Date.HOUR;
Date.WEEK = 7 * Date.DAY;
Date.parseDate = function(str, fmt) {
var today = new Date();
var y = 0;
var m = -1;
var d = 0;
var a = str.split(/\W+/);
var b = fmt.match(/%./g);
var i = 0, j = 0;
var hr = 0;
var min = 0;
for (i = 0; i < a.length; ++i) {
if (!a[i])
continue;
switch (b[i]) {
case "%d":
case "%e":
d = parseInt(a[i], 10);
break;
case "%m":
m = parseInt(a[i], 10) - 1;
break;
case "%Y":
case "%y":
y = parseInt(a[i], 10);
(y < 100) && (y += (y > 29) ? 1900 : 2000);
break;
case "%b":
case "%B":
for (j = 0; j < 12; ++j) {
if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { m = j; break; }
}
break;
case "%H":
case "%I":
case "%k":
case "%l":
hr = parseInt(a[i], 10);
break;
case "%P":
case "%p":
if (/pm/i.test(a[i]) && hr < 12)
hr += 12;
else if (/am/i.test(a[i]) && hr >= 12)
hr -= 12;
break;
case "%M":
min = parseInt(a[i], 10);
break;
}
}
if (isNaN(y)) y = today.getFullYear();
if (isNaN(m)) m = today.getMonth();
if (isNaN(d)) d = today.getDate();
if (isNaN(hr)) hr = today.getHours();
if (isNaN(min)) min = today.getMinutes();
if (y != 0 && m != -1 && d != 0)
return new Date(y, m, d, hr, min, 0);
y = 0; m = -1; d = 0;
for (i = 0; i < a.length; ++i) {
if (a[i].search(/[a-zA-Z]+/) != -1) {
var t = -1;
for (j = 0; j < 12; ++j) {
if (Calendar._MN[j].substr(0, a[i].length).toLowerCase() == a[i].toLowerCase()) { t = j; break; }
}
if (t != -1) {
if (m != -1) {
d = m+1;
}
m = t;
}
} else if (parseInt(a[i], 10) <= 12 && m == -1) {
m = a[i]-1;
} else if (parseInt(a[i], 10) > 31 && y == 0) {
y = parseInt(a[i], 10);
(y < 100) && (y += (y > 29) ? 1900 : 2000);
} else if (d == 0) {
d = a[i];
}
}
if (y == 0)
y = today.getFullYear();
if (m != -1 && d != 0)
return new Date(y, m, d, hr, min, 0);
return today;
};
Date.prototype.getMonthDays = function(month) {
var year = this.getFullYear();
if (typeof month == "undefined") {
month = this.getMonth();
}
if (((0 == (year%4)) && ( (0 != (year%100)) || (0 == (year%400)))) && month == 1) {
return 29;
} else {
return Date._MD[month];
}
};
Date.prototype.getDayOfYear = function() {
var now = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
var then = new Date(this.getFullYear(), 0, 0, 0, 0, 0);
var time = now - then;
return Math.floor(time / Date.DAY);
};
Date.prototype.getWeekNumber = function() {
var d = new Date(this.getFullYear(), this.getMonth(), this.getDate(), 0, 0, 0);
var DoW = d.getDay();
d.setDate(d.getDate() - (DoW + 6) % 7 + 3);
var ms = d.valueOf();
d.setMonth(0);
d.setDate(4);
return Math.round((ms - d.valueOf()) / (7 * 864e5)) + 1;
};
Date.prototype.equalsTo = function(date) {
if(!date) return false;
return ((this.getFullYear() == date.getFullYear()) &&
(this.getMonth() == date.getMonth()) &&
(this.getDate() == date.getDate()) &&
(this.getHours() == date.getHours()) &&
(this.getMinutes() == date.getMinutes()));
};
Date.prototype.setDateOnly = function(date) {
var tmp = new Date(date);
this.setDate(1);
this.setFullYear(tmp.getFullYear());
this.setMonth(tmp.getMonth());
this.setDate(tmp.getDate());
};
Date.prototype.print = function (str) {
var m = this.getMonth();
var d = this.getDate();
var y = this.getFullYear();
var wn = this.getWeekNumber();
var w = this.getDay();
var s = {};
var hr = this.getHours();
var pm = (hr >= 12);
var ir = (pm) ? (hr - 12) : hr;
var dy = this.getDayOfYear();
if (ir == 0)
ir = 12;
var min = this.getMinutes();
var sec = this.getSeconds();
s["%a"] = Calendar._SDN[w]; // abbreviated weekday name [FIXME: I18N]
s["%A"] = Calendar._DN[w]; // full weekday name
s["%b"] = Calendar._SMN[m]; // abbreviated month name [FIXME: I18N]
s["%B"] = Calendar._MN[m]; // full month name
s["%C"] = 1 + Math.floor(y / 100); // the century number
s["%d"] = (d < 10) ? ("0" + d) : d; // the day of the month (range 01 to 31)
s["%e"] = d; // the day of the month (range 1 to 31)
s["%H"] = (hr < 10) ? ("0" + hr) : hr; // hour, range 00 to 23 (24h format)
s["%I"] = (ir < 10) ? ("0" + ir) : ir; // hour, range 01 to 12 (12h format)
s["%j"] = (dy < 100) ? ((dy < 10) ? ("00" + dy) : ("0" + dy)) : dy; // day of the year (range 001 to 366)
s["%k"] = hr; // hour, range 0 to 23 (24h format)
s["%l"] = ir; // hour, range 1 to 12 (12h format)
s["%m"] = (m < 9) ? ("0" + (1+m)) : (1+m); // month, range 01 to 12
s["%M"] = (min < 10) ? ("0" + min) : min; // minute, range 00 to 59
s["%n"] = "\n"; // a newline character
s["%p"] = pm ? "PM" : "AM";
s["%P"] = pm ? "pm" : "am";
s["%s"] = Math.floor(this.getTime() / 1000);
s["%S"] = (sec < 10) ? ("0" + sec) : sec; // seconds, range 00 to 59
s["%t"] = "\t"; // a tab character
s["%U"] = s["%W"] = s["%V"] = (wn < 10) ? ("0" + wn) : wn;
s["%u"] = w + 1; // the day of the week (range 1 to 7, 1 = MON)
s["%w"] = w; // the day of the week (range 0 to 6, 0 = SUN)
s["%y"] = ('' + y).substr(2, 2); // year without the century (range 00 to 99)
s["%Y"] = y; // year with the century
s["%%"] = "%"; // a literal '%' character
var re = /%./g;
if (!Calendar.is_ie5 && !Calendar.is_khtml)
return str.replace(re, function (par) { return s[par] || par; });
var a = str.match(re);
for (var i = 0; i < a.length; i++) {
var tmp = s[a[i]];
if (tmp) {
re = new RegExp(a[i], 'g');
str = str.replace(re, tmp);
}
}
return str;
};
Date.prototype.__msh_oldSetFullYear = Date.prototype.setFullYear;
Date.prototype.setFullYear = function(y) {
var d = new Date(this);
d.__msh_oldSetFullYear(y);
if (d.getMonth() != this.getMonth())
this.setDate(28);
this.__msh_oldSetFullYear(y);
};
window._dynarch_popupCalendar = null;

var imgPath = '../js/wcm52/calendar/calendar_style/img.gif';
var oldLink = null;
function CTRSCalendar(){
this.setActiveStyleSheet = CTRSCalendar_setActiveStyleSheet;
this.selected = CTRSCalendar_selected;
this.closeHandler = CTRSCalendar_closeHandler;
this.showCalendar = CTRSCalendar_showCalendar;
this.isDisabled = CTRSCalendar_isDisabled;
this.flatSelected = CTRSCalendar_flatSelected;
this.showFlatCalendar = CTRSCalendar_showFlatCalendar;
this.drawCalendar = CTRSCalendar_drawCalendar;
this.getHTML = CTRSCalendar_getHTML;
this.drawWithTime = CTRSCalendar_drawWithTime;
this.getHTMLWithTime = CTRSCalendar_getHTMLWithTime;
this.drawWithoutTime = CTRSCalendar_drawWithoutTime;
this.getHTMLWithoutTime = CTRSCalendar_getHTMLWithoutTime;
this.drawDialogCalendar = CTRSCalendar_drawDialogCalendar;
this.openDialog = CTRSCalendar_openDialog;
this.render = CTRSCalendar_render;
this.html = CTRSCalendar_html;
}
function CTRSCalendar_setActiveStyleSheet(link, title) {
var i, a, main;
for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
a.disabled = true;
if(a.getAttribute("title") == title) a.disabled = false;
}
}
if (oldLink) oldLink.style.fontWeight = 'normal';
oldLink = link;
link.style.fontWeight = 'bold';
return false;
}
function CTRSCalendar_selected(cal, date) {
}
function CTRSCalendar_closeHandler(cal) {
cal.hide();
_dynarch_popupCalendar = null;
}
function CTRSCalendar_showCalendar(id, format, showsTime, showsOtherMonths) {
var el = document.getElementById(id);
if (_dynarch_popupCalendar != null) {
_dynarch_popupCalendar.hide();
} else {
var cal = new Calendar(1, null, this.selected, this.closeHandler);
if (typeof showsTime == "string") {
cal.showsTime = true;
cal.time24 = (showsTime == "24");
}
if (showsOtherMonths) {
cal.showsOtherMonths = true;
}
_dynarch_popupCalendar = cal;
cal.setRange(1900, 2070);
var div = cal.create();
div.id = id + "_div";
}
_dynarch_popupCalendar.setDateFormat(format);
_dynarch_popupCalendar.parseDate(el.value);
_dynarch_popupCalendar.sel = el;
_dynarch_popupCalendar.showAtElement(el.nextSibling, "Br"); // show the calendar
if(TRSCalendar.dealForTop){
}
return false;
}
var MINUTE = 60 * 1000;
var HOUR = 60 * MINUTE;
var DAY = 24 * HOUR;
var WEEK = 7 * DAY;
function CTRSCalendar_isDisabled(date) {
var today = new Date();
return (Math.abs(date.getTime() - today.getTime()) / DAY) > 10;
}
function CTRSCalendar_flatSelected(cal, date) {
var elPreview = document.getElementById("preview");
var elValue = document.getElementById("DateValue");
elPreview.innerHTML = date;
elValue.value = date;
}
function CTRSCalendar_showFlatCalendar(_sDateFormat, _sInitDateString, _bShowTime) {
var parent = document.getElementById("display");
var cal = new Calendar(1, null, this.flatSelected, null, true);
cal.weekNumbers = false;
cal.showsTime = _bShowTime;
cal.showsOtherMonths = true;
var sDateFormat = _sDateFormat || "";
if(sDateFormat != null && sDateFormat.length > 0){
cal.setDateFormat(sDateFormat);
} else {
if(_bShowTime)
cal.setDateFormat("%Y-%m-%d %H:%M");
else
cal.setDateFormat("%Y-%m-%d");
}
var sInitDateString = _sInitDateString || "";
cal.create(parent);
cal.parseDate(sInitDateString);
cal.show();
}
function CTRSCalendar_getHTML(id, sValue, format, showsTime, showsOtherMonths, bNotSubmit, sStyle, bApplyEmpty){
var nMaxLength = 16;
if(format == "%Y-%m-%d %H:%M") nMaxLength = 16;
if(format == "%Y-%m-%d %H:%M:%S") nMaxLength = 19;
if(format == "%Y-%m-%d") nMaxLength = 10;
var arDatetime = sValue.split(' ');
if(arDatetime.length == 2) {
var arTime = arDatetime[1].split(':');
sValue = arDatetime[0] + ' ' + arTime[0] + ':' + arTime[1];
}
var sHTML = "";
var display = '';
if(TRSCalendar.needInput === false){
bNotSubmit = true;
display = 'style="display:none;"';
}
sHTML += "<input " + display + " MAXLENGTH=\""+nMaxLength+"\" DateFormat=\""+format+"\" onblur=\"checkDateString(";
if(bApplyEmpty == true || bApplyEmpty == 'true') {
sHTML += "true";
}
sHTML += ")\" class=\"inputtext\" type=\"text\" name=\""+id+"\" id=\""+id+"\" value=\""+sValue+"\"";
if(sStyle != null && sStyle.length > 0){
sHTML += " style=\""+sStyle+"\" ";
}
if(bNotSubmit == true || bNotSubmit == 'true'){
sHTML += " ignore=\"1\" ";
}
sHTML += ">";
sHTML += "<A HREF=\"###\" onclick=\"return TRSCalendar.showCalendar('"+id+"', '"+format+"',";
if(showsTime != null && showsTime.length > 0){
sHTML += " '"+showsTime+"', ";
} else {
sHTML += " null, ";
}
sHTML += ""+showsOtherMonths+");\"><div class='imgDate'></div></A>";
return sHTML;
}
function CTRSCalendar_drawCalendar(id, sValue, format, showsTime, showsOtherMonths, bNotSubmit, sStyle, bApplyEmpty){
var sHTML = CTRSCalendar_getHTML(id, sValue, format, showsTime, showsOtherMonths, bNotSubmit, sStyle, bApplyEmpty);
document.write(sHTML);
}
function CTRSCalendar_getHTMLWithTime(id, sValue, bNotSubmit, bApplyEmpty, noDefaultValue){
if(!sValue){
if(noDefaultValue){
sValue = "";
}else{
var date = new Date();
sValue = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
}
}
return CTRSCalendar_getHTML(id, sValue, '%Y-%m-%d %H:%M', '24', true, bNotSubmit, "width:110px", bApplyEmpty);
}
function CTRSCalendar_drawWithTime(id, sValue, bNotSubmit, bApplyEmpty, noDefaultValue){
var sHTML = CTRSCalendar_getHTMLWithTime(id, sValue, bNotSubmit, bApplyEmpty, noDefaultValue);
document.write(sHTML);
}
function CTRSCalendar_getHTMLWithoutTime(id, sValue, bNotSubmit, bApplyEmpty, noDefaultValue){
if(!sValue){
if(noDefaultValue){
sValue = "";
}else{
var date = new Date();
sValue = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();
}
}else{
var arrDate = sValue.split(" ");
sValue = arrDate[0];
}
return CTRSCalendar_getHTML(id, sValue, '%Y-%m-%d', null, true, bNotSubmit, "width:80px", bApplyEmpty);
}
function CTRSCalendar_drawWithoutTime(id, sValue, bNotSubmit, bApplyEmpty, noDefaultValue){
var sHTML = CTRSCalendar_getHTMLWithoutTime(id, sValue, bNotSubmit, bApplyEmpty, noDefaultValue);
document.write(sHTML);
}
function CTRSCalendar_getHTML(id, sValue, format, showsTime, showsOtherMonths, bNotSubmit, sStyle, bApplyEmpty){
var nMaxLength = 16;
if(format == "%Y-%m-%d %H:%M") nMaxLength = 16;
if(format == "%Y-%m-%d %H:%M:%S") nMaxLength = 19;
if(format == "%Y-%m-%d") nMaxLength = 10;
var arDatetime = sValue.split(' ');
if(arDatetime.length == 2) {
var arTime = arDatetime[1].split(':');
sValue = arDatetime[0] + ' ' + arTime[0] + ':' + arTime[1];
}
var sHTML = "";
var display = '';
if(TRSCalendar.needInput === false){
bNotSubmit = true;
display = 'style="display:none;"';
}
sHTML += "<input " + display + " MAXLENGTH=\""+nMaxLength+"\" DateFormat=\""+format+"\" onblur=\"checkDateString(";
if(bApplyEmpty == true || bApplyEmpty == 'true') {
sHTML += "true";
}
sHTML += ")\" class=\"inputtext\" type=\"text\" name=\""+id+"\" id=\""+id+"\" value=\""+sValue+"\"";
if(sStyle != null && sStyle.length > 0){
sHTML += " style=\""+sStyle+"\" ";
}
if(bNotSubmit == true || bNotSubmit == 'true'){
sHTML += " ignore=\"1\" ";
}
sHTML += ">";
sHTML += "<A HREF=\"###\" onclick=\"return TRSCalendar.showCalendar('"+id+"', '"+format+"',";
if(showsTime != null && showsTime.length > 0){
sHTML += " '"+showsTime+"', ";
} else {
sHTML += " null, ";
}
sHTML += ""+showsOtherMonths+");\"><div class='imgDate'></div></A>";
return sHTML;
}
function CTRSCalendar_openDialog(_id, _sInitDateString, _sDateFormat, _bShowTime){
var oEl = document.getElementById(_id);
if(!oEl){
CTRSAction_alert(Calendar.LANG["NOT_FOUND_DATAEL"]||"没有找到存储日期值得元素，操作失败！");
return;
}
var args = new Array();
args[0] = _sDateFormat || "";
args[1] = (oEl.value)?(oEl.value||""):(_sInitDateString || "");
args[2] = _bShowTime || false;
var nWidth = 310;
var nHeight = 270;
if(args[2]) nHeight = 285;
var oTRSAction = new CTRSAction("../wcm_use/calendar_dialog.htm");
var sReturn = oTRSAction.doNoScrollDialogAction(nWidth, nHeight, args);
if(!sReturn) return;
oEl.value = sReturn;
}
function CTRSCalendar_drawDialogCalendar(id, _sInitDateString, _sDateFormat, _bShowTime, sStyle, bNotSubmit){
var sFormat = _sDateFormat;
if(!sFormat) sFormat = (_bShowTime)?"%Y-%m-%d %H:%M":"%Y-%m-%d";
var nMaxLength = 16;
if(sFormat == "%Y-%m-%d %H:%M") nMaxLength = 16;
if(sFormat == "%Y-%m-%d") nMaxLength = 10;
var sHTML = ""
+"<input MAXLENGTH=\""+nMaxLength+"\" DateFormat=\""+sFormat+"\" class=\"inputtext\" onblur=\"checkDateString()\" type=\"text\" name=\""+id+"\" id=\""+id+"\" value=\""+_sInitDateString+"\"";
if(sStyle != null && sStyle.length > 0){
sHTML += " style=\""+sStyle+"\" ";
}
if(bNotSubmit){
sHTML += " ignore=\"1\" ";
}
sHTML += "><A HREF=\"###\" onclick=\"return TRSCalendar.openDialog('"+id+"', '"+_sInitDateString+"', '"+sFormat+"',";
if(_bShowTime){
sHTML += " "+_bShowTime;
} else {
sHTML += " false";
}
sHTML += ""+showsOtherMonths+");\"><div class='imgDate'></div></A>";
document.write(sHTML);
}
var TRSCalendar = new CTRSCalendar();
function isLeapYear(year){
if((year%4==0&&year%100!=0)||(year%400==0)){
return true;
}
return false;
}
function getNumber(_str){
var str = _str || "0";
if(str.length > 1 && str.charAt(0) == '0'){
return parseInt(str.substring(1));
}
return parseInt(str);
}
function toNumberString(_str){
var nNumber = getNumber(_str);
return (nNumber > 9)?(nNumber+""):("0"+nNumber);
}
function checkDateString(bApplyEmpty){
var event = Ext.lib.Event.getEvent();
var oSrcEl = event.srcElement || event.target;
var sFormat = oSrcEl.getAttribute("DateFormat") || "%Y-%m-%d";
var sDateStr = oSrcEl.value || "";
var regexp = null;
var bHasTime = false;
var bHasSecond = false;
if(sFormat == "%Y-%m-%d"){
regexp = new RegExp("^([0-9]{4})\-([0-9]{1,2})\-([0-9]{1,2})$");
bHasTime = false;
}else{
if(sFormat == "%Y-%m-%d %H:%M"){
regexp = new RegExp("^([0-9]{4})\-([0-9]{1,2})\-([0-9]{1,2}) ([0-9]{1,2}):([0-9]{1,2})$");
bHasTime = true;
}else if(sFormat == "%Y-%m-%d %H:%M:%S"){
regexp = new RegExp("^([0-9]{4})\-([0-9]{1,2})\-([0-9]{1,2}) ([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})$");
bHasTime = true;
bHasSecond = true;
} else {
regexp = new RegExp("^([0-9]{4})\-([0-9]{1,2})\-([0-9]{1,2})$");
bHasTime = false;
}
}
if(bApplyEmpty && sDateStr == "") {
return;
}
if(!bApplyEmpty && sDateStr == "") {
var aWarning = Calendar.LANG["NOT_NULL"] || "时间不能为空!";
CTRSAction_alert(aWarning);
return;
}
if(regexp.test(sDateStr)){
var dateArray = sDateStr.match(regexp);
var nYear = getNumber(dateArray[1]);
var nMonth = getNumber(dateArray[2]);
var nDay = getNumber(dateArray[3]);
if(nYear < 1900 || nYear > 2070){
nYear = (new Date()).getFullYear();
CTRSAction_alert(Calendar.LANG["YEAR_AREA"]||"[年] 必须在 1900-2070 之间！");
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + ((bHasTime)?(" " + toNumberString(dateArray[4]) + ":" + toNumberString(dateArray[5])):"");
return;
}
if(nMonth < 1 || nMonth > 12){
nMonth = 1;
CTRSAction_alert(Calendar.LANG["MONTH_AREA"]||"[月] 必须在 1-12 之间！");
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + ((bHasTime)?(" " + toNumberString(dateArray[4]) + ":" + toNumberString(dateArray[5])):"");
return;
}
if(nDay < 1 || nDay > 31){
nDay = 1;
CTRSAction_alert(Calendar.LANG["DAY_AREA"]||"[日] 必须在 1-31 之间！");
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + ((bHasTime)?(" " + toNumberString(dateArray[4]) + ":" + toNumberString(dateArray[5])):"");
return;
}
if(nMonth==2){
if(isLeapYear(nYear)&&nDay>29){
nDay = 29;
CTRSAction_alert();
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + ((bHasTime)?(" " + toNumberString(dateArray[4]) + ":" + toNumberString(dateArray[5])):"");
return;
}
if(!isLeapYear(nYear)&&nDay>28){
nDay = 28;
CTRSAction_alert(Calendar.LANG["SENCOND_MONTH_LARGEDAY"]||"闰年的二月最多只有29天！");
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + ((bHasTime)?(" " + toNumberString(dateArray[4]) + ":" + toNumberString(dateArray[5])):"");
return;
}
}
if((nMonth==4||nMonth==6||nMonth==9||nMonth==11)&&(nDay>30)){
nDay = 30;
CTRSAction_alert(nMonth +(Calendar.LANG["MONTH_LARGEDAY"]||"月份最多只有30天！"));
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + ((bHasTime)?(" " + toNumberString(dateArray[4]) + ":" + toNumberString(dateArray[5])):"");
return;
}
if(bHasTime){
var nHour = getNumber(dateArray[4]);
var nMinute = getNumber(dateArray[5]);
var nSecond = getNumber(dateArray[6]);
if(nHour < 0 || nHour > 23){
nHour = 0;
CTRSAction_alert(Calendar.LANG["HOUR_AREA"]||"[时] 必须在 0-23 之间！");
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + " " + toNumberString(nHour) + ":" + toNumberString(nMinute);
if(bHasSecond){
oSrcEl.value += ":" + toNumberString(nSecond);
}
return;
}
if(nMinute < 0 || nMinute > 59){
nMinute = 0;
CTRSAction_alert(Calendar.LANG["MINUTE_AREA"]||"[分] 必须在 0-59 之间！");
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + " " + toNumberString(nHour) + ":" + toNumberString(nMinute);
if(bHasSecond){
oSrcEl.value += ":" + toNumberString(nSecond);
}
return;
}
if(nSecond < 0 || nSecond > 59){
nSecond = 0;
CTRSAction_alert(Calendar.LANG["SECOND_AREA"]||"[秒] 必须在 0-59 之间！");
oSrcEl.value = toNumberString(nYear) + "-" + toNumberString(nMonth) + "-" + toNumberString(nDay) + " " + toNumberString(nHour) + ":" + toNumberString(nMinute);
oSrcEl.value += ":" + toNumberString(nSecond);
return;
}
}
}else{
var aWarning = [Calendar.LANG["DATA_FORMAT_ERROR"]||"您输入的日期不匹配指定的日期格式! \n 一个正确的日期格式应当为："];
if(!bHasTime){
aWarning.push("Year-Month-Day(As:2002-12-24)");
}else if(bHasSecond){
aWarning.push("Year-Month-Day Hour:Minute:Second(As:2002-12-24 11:50:30)");
}else{
aWarning.push("Year-Month-Day Hour:Minute(As:2002-12-24 11:50)");
}
CTRSAction_alert(aWarning.join(""));
var date = new Date();
oSrcEl.value = toNumberString(date.getFullYear()) + "-" + toNumberString(parseInt(date.getMonth(),10)+1) + "-" + toNumberString(date.getDate()) + ((bHasTime)?(" " + toNumberString(date.getHours()) + ":" + toNumberString(date.getMinutes())):"");
if(bHasSecond){
oSrcEl.value += ":" + toNumberString(date.getSeconds());
}
return;
}
}
if(!window.CTRSAction_alert){
CTRSAction_alert = Ext.Msg.alert;
}
Calendar.initConfigs = {};
function CTRSCalendar_html(opts){
if(opts["now"] && !opts["value"]){
opts["value"] = now2str(opts["timeable"]);
}
var id = opts["id"] || ("cal_" + (CTRSCalendar.nextId = (CTRSCalendar.nextId || 0)+1));
opts["id"] = id;
if(opts["inputHidden"]){
opts["style"] = "display:none;" + (opts["style"] || "");
}
Calendar.initConfigs[id] = opts;
return CTRSCalendar_getHTML(
id,
opts["value"] || "",
opts["format"] || ("%Y-%m-%d" + (opts["timeable"] ? " %H:%M" : "")),
opts["timeable"] ? "24" : null,
true,
opts["sumbit"] === false,
opts["style"] || (opts["timeable"] ? "width:110px;" : "width:80px;"),
!opts["required"] || ''
);
}
function CTRSCalendar_render(opts){
var sHtml = CTRSCalendar_html(opts);
var renderTo = opts["renderTo"];
if(renderTo){
if(Ext.isReady){
Element.update(renderTo, sHtml);
opts["rendered"] = true;
}
}else{
document.write(sHtml);
}
}
function now2str(bHasTime){
var date = new Date();
var result = [];
var month = date.getMonth() + 1;
result.push(
[
date.getFullYear(),
month < 10 ? "0" + month : month,
date.getDate()
].join("-")
);
if(bHasTime){
result.push(
[
date.getHours(),
date.getMinutes()
].join(":")
);
}
return result.join(" ");
}
Calendar.extraDealOnDblClick = function(id, value){
var opts = Calendar.initConfigs[id];
if(opts && opts["dblclick"]){
try{
opts["dblclick"].call(opts, value);
}catch(error){
}
}
};
Event.observe(window, 'load', function(){
var configs = Calendar.initConfigs;
for (var id in configs){
var opts = configs[id];
if(opts["renderTo"] && !opts["rendered"]){
Element.update(opts["renderTo"], CTRSCalendar_html(opts));
opts["rendered"] = true;
}
}
});
Event.observe(window, 'unload', function(){
var configs = Calendar.initConfigs;
for (var key in configs){
delete configs[key];
}
});

