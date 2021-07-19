Ext.ns("com.trs.menu");
com.trs.menu.Menu = function(menuBar){
this.menuBar = menuBar;
if(Ext.isIE){
Event.observe(window, "resize", this.onResize.bind(this), false);
Event.observe(window, "focus", this.onfocus.bind(this), false);
}else if(Ext.isGecko){
menuBar.tabIndex = 1;
}
Event.observe(menuBar, "mousemove", this.onMouseMove.bind(this), false);
Event.observe(menuBar, "mouseout", this.onMouseOut.bind(this), false);
Event.observe(menuBar, "click", this.onClick.bind(this), false);
if(!Ext.isSafari && !Ext.isGecko){
Event.observe(menuBar, "blur", this.onBlur.bind(this), false);
}else{
this.blurHide = new BlurHide(menuBar, this.onBlur.bind(this));
}
if(this.enableHotKey){
Event.observe(document, 'keydown', this.onKeyDown.bind(this), false);
}
};
Ext.apply(com.trs.menu.Menu.prototype, {
menuBar : null, 
oPreviousItem : null, 
currStatus : false, 
enableHotKey : true, 
itemMinWidth : 120,
onMouseMove : function(event){
var oSrcElement = this.dealSrcElement(event);
if(oSrcElement == null || oSrcElement == this.oPreviousItem){
return;
}
if(this.oPreviousItem){
if(this.oPreviousItem.parentNode == oSrcElement.parentNode.parentNode.parentNode){
}else{
this.hideToSameParent(oSrcElement);
}
}
this.oPreviousItem = oSrcElement;
if(this.currStatus){
this.showItems(oSrcElement);
}else{
Element.removeClassName(oSrcElement, "item_out");
Element.addClassName(oSrcElement, "item_over");
}
},
onMouseOut : function(event){
if(this.oPreviousItem == null) return;
var oSrcElement = this.dealSrcElement(event);
if(oSrcElement == null){
return;
}
var contentNode = this.getNextHTMLSibling(oSrcElement);
if(contentNode == null || !this.currStatus){
Element.removeClassName(oSrcElement, "item_over");
Element.addClassName(oSrcElement, "item_out");
if(this.isMenuDesc(oSrcElement)){
this.oPreviousItem = null;
}else{
this.oPreviousItem = oSrcElement.parentNode.parentNode;
if(this.oPreviousItem.parentNode){
this.oPreviousItem = this.getFirstHTMLChild(this.oPreviousItem.parentNode);
}
}
}
},
isMenuDesc : function(oElement){
return Element.hasClassName(oElement, "menuDesc") && !Element.hasClassName(oElement.parentNode, "item");
},
onClick : function(event){
var oSrcElement = this.dealSrcElement(event);
if(oSrcElement == null){
return;
}
if(this.isMenuDesc(oSrcElement)){
this.menuBar.focus();
this.currStatus = !this.currStatus;
var contentNode = this.getNextHTMLSibling(oSrcElement);
if(contentNode == null)return;
if(this.currStatus){
this.parseContentItems(contentNode);
this.createCover(contentNode);
}else{
contentNode.style.display = 'none';
}
}else{
var contentNode = this.getNextHTMLSibling(oSrcElement);
if(!this.isEmptyContent(contentNode)){
this.menuBar.focus();
return;
}else{
this.clickItem(oSrcElement);
}
}
},
isEmptyContent : function(contentNode){
return !this.getFirstHTMLChild(contentNode);
},
clickItem : function(itemNode){
if(Element.hasClassName(itemNode, 'disabled')) return;
if(itemNode.className.indexOf("moreItem") >= 0){
tempNode = itemNode;
while(tempNode = this.getNextHTMLSibling(tempNode.parentNode)){
tempNode = this.getFirstHTMLChild(tempNode);
if(!tempNode) break;
tempNode.style.display = '';
}
itemNode.style.display = 'none';
}else{
this.hideToTop();
this.oPreviousItem = null;
this.currStatus = false;
this.executeItemCommand(itemNode);
}
},
executeItemCommand : function(jsonObj, itemNode){
alert("请实现executeItemCommand方法\n"+itemNode.outerHTML);
},
parseContentItems : function(contentNode){
contentNode.style.width = 'auto';
var firstChild = this.getFirstHTMLChild(contentNode);
while(firstChild){
if(firstChild.getAttribute("loaded") == "false"){//需要动态载入菜单项
this.setItems(firstChild, this.loadDynamicItem(firstChild) || '');//设置菜单项
}else{
this.setItemStyle(firstChild);
var descChild = this.getFirstHTMLChild(firstChild);
if(window._hideStart_){
descChild.style.display = 'none';
}
if(descChild.className.indexOf("moreItem") >= 0){
window._hideStart_ = true;
Element.show(descChild);
}
}
firstChild = this.getNextHTMLSibling(firstChild);
}
window._hideStart_ = false;
},
loadDynamicItem : function(itemNode){
alert(wcm.LANG.Menu_4569 || "请实现动态载入菜单的方法！");
},
setItemStyle : function(itemNode){
},
showItems : function(itemNode){
if(!Ext.isSafari && !Ext.isGecko){
this.menuBar.focus();
}else{
this.blurHide.show();
}
Element.removeClassName(itemNode, "item_out");
Element.addClassName(itemNode, "item_over");
if(this.isMenuDesc(itemNode) || Element.hasClassName(itemNode, "hasChild")){
var contentNode = this.getNextHTMLSibling(itemNode);
if(contentNode){
this.parseContentItems(contentNode);
this.createCover(contentNode);
}
}
this.oPreviousItem = itemNode;
this.currStatus = true;
},
createCover : function(source){
source.style.visibility = 'hidden';
source.style.display = '';
setTimeout(function(){
var nWidth = Math.max(parseInt(source.offsetWidth, 10), this.itemMinWidth);
source.style.width = nWidth + "px";
if(!source.getAttribute("createCovered")){
source.setAttribute("createCovered", true);
var divDom = document.createElement("div");
divDom.setAttribute("ignore", true);
divDom.style.position = 'absolute';
divDom.style.left = "0px";
divDom.style.top = "0px";
divDom.style.height = source.clientHeight;
divDom.style.width = source.clientWidth;
divDom.style.overflow = 'hidden';
divDom.style.zIndex = source.style.zIndex-1;
var iframeDom = document.createElement("iframe");
iframeDom.src = '';
iframeDom.style.height = "100%";
iframeDom.style.width = "100%";
iframeDom.frameBorder = 0;
iframeDom.scrolling = 'no';
divDom.appendChild(iframeDom);
source.appendChild(divDom);
Element.addClassName(source, 'item_out');
}else{
var divDom = this.getLastHTMLChild(source);
divDom.style.height = source.clientHeight;
divDom.style.width = source.clientWidth;
} 
source.style.width = source.offsetWidth;
source.style.visibility = 'visible';
}.bind(this), 10);
},
hideToTop : function(){
this.hideToSameParent(this.getFirstHTMLChild(this.getFirstHTMLChild(this.menuBar)));
},
onBlur : function(event){
var notMenuBar = true;
var activeElement = document.activeElement;
while(activeElement && activeElement.tagName != "BODY"){
if(activeElement == this.menuBar){
notMenuBar = false;
break;
}else{
activeElement = activeElement.parentNode;
}
}
if(notMenuBar){
this.hideToTop();
this.currStatus = false;
this.oPreviousItem = null;
}
},
onResize : function(event){
if(this.oPreviousItem != null){
this.hideToTop();
this.currStatus = false;
this.oPreviousItem = null;
}
},
onfocus : function(event){
this.onResize();
},
isRealItemDesc : function(descNode){
if(descNode == null) return false;
if(descNode.parentNode.getAttribute('ignore') || descNode.className.indexOf("separate") >= 0){
return false;
}
if(descNode.style.display == 'none'){
return false;
}
return true;
},
getRealItemDesc : function(descNode, forward){
var forwardFun = (forward == 'previous' ? this.getPreviousHTMLSibing : this.getNextHTMLSibling)
while(descNode && !this.isRealItemDesc(descNode)){
var nextItemDom = forwardFun(descNode.parentNode);
descNode = this.getFirstHTMLChild(nextItemDom);
} 
return descNode;
}, 
hideToSameParent : function(oSrcElement){
if(this.oPreviousItem == null)return;
var oSrcParent = oSrcElement.parentNode.parentNode;
var oPreviousParent = null;
do{
Element.removeClassName(this.oPreviousItem, "item_over");
Element.addClassName(this.oPreviousItem, "item_out");
var contentNode = this.getNextHTMLSibling(this.oPreviousItem);
if(contentNode){
contentNode.style.display = 'none';
}
oPreviousParent = this.oPreviousItem.parentNode.parentNode;
if(Element.hasClassName(oPreviousParent, 'menuBar')){
break;
}
this.oPreviousItem = this.getFirstHTMLChild(oPreviousParent.parentNode);
}while(oSrcParent != oPreviousParent && oPreviousParent);
},
setItems : function(itemNode, content){
var tempNode = this.getNextHTMLSibling(itemNode);
var otherNode = null;
while(tempNode){
if(tempNode.getAttribute("dynamic") == null){
if(!Element.hasClassName(this.getFirstHTMLChild(tempNode), "separate")){
break;
}
}
otherNode = this.getNextHTMLSibling(tempNode);
Ext.removeNode(tempNode);
tempNode = otherNode;
}
new Insertion.After(itemNode, content);
Element[content.trim().length == 0 ? "addClassName" : "removeClassName"](itemNode, 'noChild');
if(itemNode.getAttribute("loadTime") == 'once'){
itemNode.setAttribute('loaded', true);
}
},
isOpened : function(){
var isOpened = false;
var menuDom = this.getFirstHTMLChild(this.menuBar);
while(menuDom){
var menuDescDom = this.getFirstHTMLChild(menuDom);
if(menuDescDom.className.indexOf("item_over") >= 0){
isOpened = true;
break;
}
menuDom = this.getNextHTMLSibling(menuDom);
} 
return isOpened;
},
dealSrcElement : function(event){
event = window.event || event;
var oSrcElement = Event.element(event);
if(oSrcElement.className.indexOf("Desc") < 0){
if(oSrcElement.className.indexOf("hotKey") >= 0 
|| oSrcElement.className.indexOf("radioItem") >= 0){
oSrcElement = oSrcElement.parentNode;
}else{
var tempNode = this.getFirstHTMLChild(oSrcElement);
if(!tempNode || tempNode.className.indexOf("Desc") < 0)
return null;
oSrcElement = tempNode;
}
}
return oSrcElement;
},
dealSrcElement : function(event){
event = window.event || event;
var oSrcElement = Event.element(event);
while(oSrcElement){
if(oSrcElement.className.indexOf("Desc") >= 0){
return oSrcElement;
}
if(oSrcElement.className.indexOf("menuBar") >= 0){
break;
}
oSrcElement = oSrcElement.parentNode;
}
return null;
},
addCover : function(container){
},
removeCover : function(container){
}
});
Ext.apply(com.trs.menu.Menu.prototype, {
onKeyDown : function(event){
event = window.event || event;
var charValue = String.fromCharCode(event.keyCode).toUpperCase();
if(event.altKey){
var menuDom = this.getFirstHTMLChild(this.menuBar);
while(menuDom){
var menuDescDom = this.getFirstHTMLChild(menuDom);
var hotKeyContent = this.getFirstHTMLChild(menuDescDom);
if(hotKeyContent && (hotKeyContent.innerText 
|| hotKeyContent.textContent || hotKeyContent.innerHTML) == charValue){
if(this.currStatus && menuDescDom.className.indexOf("item_over") >= 0){
}else{
this.hideToTop();
setTimeout(function(){
this.currStatus = true;
this.oPreviousItem = menuDescDom;
this.showItems(menuDescDom);
}.bind(this), 100);
}
break;
}
menuDom = this.getNextHTMLSibling(menuDom);
}
}else{
switch(event.keyCode){
case Event.KEY_UP:
this.keyTop();
break;
case Event.KEY_DOWN:
this.keyBottom();
break;
case Event.KEY_LEFT:
this.keyLeft();
break;
case Event.KEY_RIGHT:
this.keyRight();
break;
case Event.KEY_RETURN:
this.keyReturn(event);
break;
default:
}
}
},
keyTop : function(){
if(this.oPreviousItem == null) return;
if(this.isMenuDesc(this.oPreviousItem)){
var nextItemContentDom = this.getNextHTMLSibling(this.oPreviousItem);
var nextItemDescDom = this.getFirstHTMLChild(this.getLastHTMLChild(nextItemContentDom));
nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'previous');
if(nextItemDescDom == null) return;
this.showItems(nextItemDescDom);
}else{
var nextItemDom = this.getPreviousHTMLSibing(this.oPreviousItem.parentNode);
if(nextItemDom == null){
nextItemDom = this.getLastHTMLChild(this.oPreviousItem.parentNode.parentNode);
}
var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'previous');
if(nextItemDescDom == null){
nextItemDom = this.getLastHTMLChild(this.oPreviousItem.parentNode.parentNode);
nextItemDescDom = this.getRealItemDesc(this.getFirstHTMLChild(nextItemDom), 'previous');
}
if(nextItemDescDom != this.oPreviousItem){
this.hideToSameParent(nextItemDescDom);
this.showItems(nextItemDescDom);
this.oPreviousItem = nextItemDescDom;
}
}
},
keyBottom : function(){
if(this.oPreviousItem == null) return;
if(this.isMenuDesc(this.oPreviousItem)){
var nextItemContentDom = this.getNextHTMLSibling(this.oPreviousItem);
var nextItemDescDom = this.getFirstHTMLChild(this.getFirstHTMLChild(nextItemContentDom));
nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'next');
if(nextItemDescDom == null) return;
this.showItems(nextItemDescDom);
}else{
var nextItemDom = this.getNextHTMLSibling(this.oPreviousItem.parentNode);
if(nextItemDom == null){
nextItemDom = this.getFirstHTMLChild(this.oPreviousItem.parentNode.parentNode);
}
var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'next');
if(nextItemDescDom == null){
nextItemDom = this.getFirstHTMLChild(this.oPreviousItem.parentNode.parentNode);
nextItemDescDom = this.getRealItemDesc(this.getFirstHTMLChild(nextItemDom), 'next');
}
if(nextItemDescDom != this.oPreviousItem){
this.hideToSameParent(nextItemDescDom);
this.showItems(nextItemDescDom);
this.oPreviousItem = nextItemDescDom;
}
}
},
count : 0,
keyLeft : function(){
if(this.oPreviousItem == null) return;
this.count++;
if(this.count > 100){
throw "dasf";
}
if(this.isMenuDesc(this.oPreviousItem)){
var nextItemDom = this.getPreviousHTMLSibing(this.oPreviousItem.parentNode);
if(nextItemDom == null){
nextItemDom = this.getLastHTMLChild(this.oPreviousItem.parentNode.parentNode);
}
var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
if(nextItemDescDom != this.oPreviousItem){
this.hideToTop();
this.showItems(nextItemDescDom);
}
}else{
this.hideToSameParent(this.oPreviousItem);
if(this.isMenuDesc(this.oPreviousItem)){
this.keyLeft();
}
}
},
keyRight : function(){
if(this.oPreviousItem == null) return;
if(this.isMenuDesc(this.oPreviousItem)){
var nextItemDom = this.getNextHTMLSibling(this.oPreviousItem.parentNode);
if(nextItemDom == null){
nextItemDom = this.getFirstHTMLChild(this.oPreviousItem.parentNode.parentNode);
}
var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
if(nextItemDescDom != this.oPreviousItem){
this.hideToTop();
this.showItems(nextItemDescDom);
}
}else{
var nextItemContentDom = this.getNextHTMLSibling(this.oPreviousItem);
if(nextItemContentDom == null){
nextItemContentDom = this.oPreviousItem
while(!(Element.hasClassName(nextItemContentDom, "menu") 
&& !Element.hasClassName(nextItemContentDom, "item"))){
nextItemContentDom = nextItemContentDom.parentNode;
}
this.hideToTop();
this.oPreviousItem = this.getFirstHTMLChild(nextItemContentDom);
this.keyRight();
}else{
var nextItemDescDom = this.getFirstHTMLChild(this.getFirstHTMLChild(nextItemContentDom));
nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'next');
if(nextItemDescDom == null) return;
this.showItems(nextItemDescDom);
}
}
},
keyReturn : function(event){
this.clickItem(this.oPreviousItem);
Event.stop(event);
}
});
Ext.apply(com.trs.menu.Menu.prototype, {
getNextHTMLSibling: function(node){
if(node == null)return null;
var contentNode = node.nextSibling;
while(contentNode && contentNode.nodeType != 1){
contentNode = contentNode.nextSibling;
}
return contentNode;
},
getPreviousHTMLSibing: function(node){
if(node == null)return null;
var contentNode = node.previousSibling;
while(contentNode && contentNode.nodeType != 1){
contentNode = contentNode.previousSibling;
}
return contentNode;
},
getFirstHTMLChild : function(node){
if(node == null)return null;
var tempNode = null;
for (var i = 0; i < node.childNodes.length; i++){
if(node.childNodes[i].nodeType == 1){
tempNode = node.childNodes[i];
break;
}
} 
return tempNode;
},
getLastHTMLChild : function(node){
if(node == null)return null;
var tempNode = null;
for (var i = node.childNodes.length-1; i >= 0; i--){
if(node.childNodes[i].nodeType == 1){
tempNode = node.childNodes[i];
break;
}
} 
return tempNode;
}
});
com.trs.menu.MenuView = function(){
this.orphanCache = [];
this.cache = {};
var rootItem = {key:this.ROOT_KEY};
rootItem[this.INNER_ITEMS_KEY] = [];
this.put(rootItem);
};
Ext.apply(com.trs.menu.MenuView, {
getKey : function(){
var index = (arguments.callee.index || 0) + 1;
arguments.callee.index = index;
return "MENU_KEY_" + index;
}
});
Ext.apply(com.trs.menu.MenuView.prototype, {
ROOT_KEY : '_ROOT_',
INNER_ITEMS_KEY : '_ITEMS_',
getConfig : function(){
return this.get(this.ROOT_KEY)[this.INNER_ITEMS_KEY];
},
getKey : function(){
return com.trs.menu.MenuView.getKey();
},
remove : function(_sKey){
var oItem = this.cache[_sKey.toUpperCase()];
delete this.cache[_sKey.toUpperCase()];
return oItem;
},
put : function(_oItem){
if(!_oItem["key"]){
_oItem["key"] = this.getKey();
}
_oItem["key"] = _oItem["key"].toUpperCase();
this.cache[_oItem["key"]] = _oItem;
},
get : function(_sKey){
return this.cache[_sKey.toUpperCase()];
},
handleOrder : function(_oItem){
var oParentItem = this.get(_oItem["parent"]);
var nMaxChildOrder = 0;
if(oParentItem && oParentItem.MAX_CHILD_INDEX){
nMaxChildOrder = oParentItem.MAX_CHILD_INDEX;
}
if(_oItem["order"] == null){
_oItem["order"] = nMaxChildOrder + 1;
}
if(_oItem["order"] > nMaxChildOrder){
if(oParentItem){
oParentItem.MAX_CHILD_INDEX = _oItem["order"];
}
}
},
beforeRegister : function(_oItem, _sParentKey){
if(!_oItem["parent"]){
_oItem["parent"] = _sParentKey || this.ROOT_KEY;
}
this.handleOrder(_oItem);
_oItem[this.INNER_ITEMS_KEY] = [];
if(Array.isArray(_oItem["items"])){
_oItem[this.INNER_ITEMS_KEY] = _oItem["items"];
}
this.put(_oItem);
if(!_oItem[this.INNER_ITEMS_KEY] || !Array.isArray(_oItem[this.INNER_ITEMS_KEY])){
return;
}
var items = _oItem[this.INNER_ITEMS_KEY];
for (var i = 0; i < items.length; i++){
this.beforeRegister(items[i], _oItem["key"]);
}
},
register : function(_oItem){
if(!_oItem){
return;
}
if(Array.isArray(_oItem)){
for (var i = 0, length = _oItem.length; i < length; i++){
this.register(_oItem[i]);
}
return;
}
this.beforeRegister(_oItem);
if(this.attach(_oItem) === false){
this.orphanCache.push(_oItem);
}
}, 
unregister : function(_sItemKey){
if(!_sItemKey) return;
_sItemKey = _sItemKey.toUpperCase();
this.detach(_sItemKey);
this.remove(_sItemKey);
},
attachOrphan : function(){
var cache = this.orphanCache;
for (var i = 0; i < cache.length; i++){
this.attach(cache[i]);
}
this.orphanCache = [];
},
attach : function(_oItem){
var oParentItem = this.findParent(_oItem["key"]);
if(!oParentItem) return false;
oParentItem[this.INNER_ITEMS_KEY].push(_oItem);
},
detach : function(_sItemKey){
var oParentItem = this.findParent(_sItemKey);
if(!oParentItem) return;
var aItems = oParentItem[this.INNER_ITEMS_KEY];
if(!aItems || !Array.isArray(aItems)) return;
for (var i = 0; i < aItems.length; i++){
if(aItems[i].key == _sItemKey){
break;
}
}
if(i == aItems.length) return;
oParentItem[this.INNER_ITEMS_KEY].splice(i, 1);
},
findParent : function(_sItemKey){
if(!_sItemKey) return null;
var oCurrItem = this.get(_sItemKey);
if(!oCurrItem) return null;
var oParentItem = this.get(oCurrItem["parent"]);
return oParentItem;
},
findPositionInItems : function(_sItemKey){
if(!_sItemKey) return -1;
_sItemKey = _sItemKey.toUpperCase();
var oCurrItem = this.get(_sItemKey);
if(!oCurrItem) return -1;
var oParentItem = this.get(oCurrItem["parent"]);
if(!oParentItem) return -1;
var aItems = oParentItem[this.INNER_ITEMS_KEY];
if(!aItems || !Array.isArray(aItems)) return -1;
for (var i = 0; i < aItems.length; i++){
if(aItems[i].key == _sItemKey){
return i;
}
}
return -1;
}
});
Ext.apply(com.trs.menu.MenuView.prototype, {
hotKeyTemplate : ' (<span class="hotKey">{0}</span>)',
separateTemplate : '<div class="menu item"><div class="separate"></div></div>',
menuTemplate : [
'<div class="menu{0}" {6}>',
'<div class="menuDesc{1}" key="{2}">{3}{4}</div>',
'<div class="menuContent" style="display:none;">{5}</div>',
'</div>'
].join(""),
dynamicMenuTemplate : [
'<div {0} style="display:none;">',
'<div></div>',
'</div>'
].join(""),
render : function(renderTo){
this.attachOrphan();
renderTo = renderTo || document.body;
Element.update(renderTo, this.parse());
},
object2string : function(_obj){
var aResult = [];
for(var prop in _obj){
aResult.push(prop + "=\"" + _obj[prop] + "\"");
}
return aResult.join(" ");
},
sort : function(_oItem1, _oItem2){
return _oItem1["order"] - _oItem2["order"];
},
parse : function(_aConfig){
var aHTML = [];
var aConfigs = _aConfig || this.getConfig();
aConfigs.sort(this.sort);
for (var i = 0; i < aConfigs.length; i++){
var oConfig = aConfigs[i];
switch(oConfig.type){
case 'separate':
aHTML.push(this.separateTemplate);
break;
case 'dynamic':
var oOptions = {
key : oConfig["key"],
loaded : 'false',
ignore : 1
};
Ext.apply(oOptions, oConfig.params || {});
aHTML.push(String.format(this.dynamicMenuTemplate, this.object2string(oOptions)));
break;
default:
var sDesc = oConfig["desc"] || "";
var sExtraCls = "";
if(oConfig["cls"] && Ext.isString(oConfig["cls"])){
sExtraCls = " " + oConfig["cls"];
}
if(oConfig.type == "checkItem"){
sExtraCls += " " + oConfig.type;
}else if(oConfig.type == "radioItem"){
sExtraCls += " " + oConfig.type;
sDesc = "<li>" + sDesc + "</li>";
}
aHTML.push(
String.format(
this.menuTemplate,
_aConfig ? " item" : "",
(_aConfig && oConfig[this.INNER_ITEMS_KEY].length > 0 ? " hasChild" : "") + sExtraCls,
oConfig["key"],
sDesc,
oConfig["hotKey"] ? String.format(this.hotKeyTemplate, oConfig["hotKey"]) : "",
oConfig[this.INNER_ITEMS_KEY].length > 0 ? this.parse(oConfig[this.INNER_ITEMS_KEY]) : "",
this.object2string(oConfig["params"])
)
);
}
}
return aHTML.join("");
}
});
com.trs.menu.MenuInitialler = new Object();
Ext.apply(com.trs.menu.MenuInitialler, {
menuControllers : [],
init : function(menuBar){
if(Array.isArray(menuBar)){
for (var i = 0; i < menuBar.length; i++){
this.init(menuBar[i]);
}
return;
}
this.menuControllers.push(new com.trs.menu.Menu($(menuBar)));
},
destroy : function(){
var menuCons = this.menuControllers;
for (var i = 0; i < menuCons.length; i++){
try{
delete menuCons[i].menuBar;
delete menuCons[i].oPreviousItem;
menuCons[i] = null;
}catch(error){
}
}
this.menuControllers = [];
}
});
Event.observe(window, 'unload',function(){
com.trs.menu.MenuInitialler.destroy();
});
function BlurHide(el, fHideCallBack){
this.element = $(el);
this.hideCallBack = fHideCallBack;
}
Ext.apply(BlurHide.prototype, {
init : function(){
if(this.inited) return;
this.inited = true;
var el = document.createElement("a");
el.href = "#";
el.style.position = 'absolute';
el.style.left = '-1000px';
el.style.top = '-1000px';
document.body.appendChild(el);
this.focusEl = el;
Event.observe(this.element, 'mouseover', this.onMouseOver.bind(this));
Event.observe(this.element, 'mouseout', this.onMouseOut.bind(this));
Event.observe(this.focusEl, 'blur', this.onBlur.bind(this));
Event.observe(window, 'unload', this.onDestory.bind(this));
},
show : function(){
this.init();
this.focusEl.focus();
},
onMouseOver : function(){
this.inElement = true;
},
onMouseOut : function(){
this.inElement = false;
},
onBlur : function(){
if(this.inElement) return;
(this.hideCallBack || Ext.emptyFn)();
},
onDestory : function(){
delete this.element;
delete this.hideCallBack;
delete this.focusEl;
}
});

﻿Ext.ns("wcm.MenuView", "wcm.MenuContext");
Object.extend(com.trs.menu.Menu.prototype, {
enableHotKey : false,
executeItemCommand : function(descNode){
var sKey = descNode.getAttribute("key");
if(!sKey) return;
var oItem = wcm.MenuView.get(sKey);
if(!oItem || !oItem["cmd"]){
return;
}
var event = wcm.MenuContext.getEvent() || wcm.MenuContext.getEvent('mainpage');
oItem["cmd"].call(oItem, event, descNode);
},
loadDynamicItem : function(itemNode){
var sKey = itemNode.getAttribute("key");
if(!sKey) return;
var oItem = wcm.MenuView.get(sKey);
if(!oItem || !oItem["items"]){
return "";
}
var event = wcm.MenuContext.getEvent() || wcm.MenuContext.getEvent('mainpage');
var appendItems = oItem["items"].call(oItem, event, itemNode);
if(!appendItems) return "";
if(!Array.isArray(appendItems)){
appendItems = [appendItems];
}
if(appendItems.length <= 0) return "";
wcm.MenuView.register(appendItems);
return wcm.MenuView.parse(appendItems);
},
setItemStyle : function(itemNode){
var descNode = Element.first(itemNode);
var sKey = descNode.getAttribute("key");
if(!sKey) return;
var oItem = wcm.MenuView.get(sKey);
if(!oItem || !oItem["cls"]){
return;
}
oItem["cls"].call(oItem, wcm.MenuContext.getEvent(), descNode);
}
});
wcm.MenuView = new com.trs.menu.MenuView();
(function(){
var events = {};
wcm.MenuContext = {
getEvent : function(type){
type = type || 'cmsobj';
return events[type.toLowerCase()];
},
clearEvents : function(){
events = {};
},
setEvent : function(type, _event){
events[type.toLowerCase()] = _event;
}
};
})();
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
afterselect : function(event){
if(!event.fromMain()) return;
wcm.MenuContext.setEvent('cmsobj', event);
}
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_MAINPAGE,
afterinit : function(event){
wcm.MenuContext.setEvent('mainpage', event);
},
afterdestroy : function(event){
operpanelhided = false;
wcm.MenuContext.clearEvents();
}
});
Ext.applyIf(Event, {
KEY_RETURN: 13,
KEY_LEFT: 37,
KEY_UP: 38,
KEY_RIGHT: 39,
KEY_DOWN: 40
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_SYSTEM,
onkeydown : function(wcmEvent){
var context = wcmEvent.getContext();
if(!context) return;
var extEvent = context.extEvent, event = extEvent.browserEvent;
var kc = event.keyCode;
if(kc==18)return;
if(!event.altKey && 
!((kc>=37 && kc<=40) || kc==13))return;
if(!event.altKey && wcmEvent.from()!=wcm.getMyFlag())return;
var menu = com.trs.menu.MenuInitialler.menuControllers[0];
menu.onKeyDown(event);
Event.stop(event);
return false;
}
});

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

Ext.ns("com.trs.menu");
(function(){
var separateTemplate = "<div class='separator'></div>";
com.trs.menu.SimpleMenu = function(config){
com.trs.menu.SimpleMenu.superclass.constructor.apply(this, arguments);
Ext.apply(this, config);
this.addEvents('beforeshow', 'show', 'beforehide', 'hide', 'beforerender', 'beforeclick', 'click', 'mouseover', 'mouse');
};
Ext.extend(com.trs.menu.SimpleMenu, wcm.util.Observable, {
sBaseCls : '',
sMenuCls : 's-menu',
sHideCls : 'display-none',
sShieldCls : 's-menuShield',
sFocusElCls : 's-focus-el',
oContainer : null,
oIframeShield : null,
items : null,
sSeparator : '/',
sItemCls : 'item',
sSelectedItemCls : 'selectedItem',
oLastSelectedItem : null,
args : null,
isInContainer : false,
maxWidth : 300,
minWidth : 150,
bindEvents : function(){
Event.observe(this.oContainer, 'mousemove', this.mouseMoveEvent.bind(this));
Event.observe(this.oContainer, 'mouseover', this.mouseOverEvent.bind(this));
Event.observe(this.oContainer, 'mouseout', this.mouseOutEvent.bind(this));
Event.observe(this.oContainer, 'click', this.clickEvent.bind(this));
Event.observe(this.oFocusEl, 'blur', this.blurEvent.bind(this));
Event.observe(window, 'unload', this.destroy.bind(this));
},
findItemTarget : function(dom){
while(dom && dom.tagName != 'BODY'){
if(Element.hasClassName(dom, this.sItemCls)) return dom;
dom = dom.parentNode;
}
return null;
},
mouseMoveEvent : function(event){
var srcElement = Event.element(window.event || event);
var dom = this.findItemTarget(srcElement);
if(dom == null) return;
if(this.oLastSelectedItem == dom) return;
if(this.oLastSelectedItem){
Element.removeClassName(this.oLastSelectedItem, this.sSelectedItemCls);
}
Element.addClassName(dom, this.sSelectedItemCls);
this.oLastSelectedItem = dom;
},
mouseOverEvent : function(event){
this.isInContainer = true;
},
mouseOutEvent : function(event){
this.isInContainer = false;
if(!this.oLastSelectedItem) return;
Element.removeClassName(this.oLastSelectedItem, this.sSelectedItemCls);
this.oLastSelectedItem = null;
},
blurEvent : function(event){
if(this.isInContainer) return;
this.hide();
},
clickEvent : function(event){
event = window.event || event;
if(this.fireEvent('beforeclick', event) === false) return;
this.hide();
this.onClick(event);
this.fireEvent('click', event);
}, 
findOprKey : function(dom){
while(dom && dom.tagName != 'BODY'){
var sOprKey = dom.getAttribute("oprKey");
if(sOprKey) return sOprKey;
if(Element.hasClassName(dom, this.sMenuCls)) return null;
dom = dom.parentNode;
}
return null;
},
onClick : function(event){
var srcElement = Event.element(event);
var sOprKey = this.findOprKey(srcElement);
if(sOprKey == null) return;
for (var i = 0, length = this.items.length; i < length; i++){
if(this.items[i] && this.items[i]["oprKey"] == sOprKey){
this.items[i]["cmd"](this.args);
break;
}
}
},
renderBox : function(){
if(this.oContainer) return;
this.oFocusEl = document.createElement("a");
this.oFocusEl.href = "#";
document.body.appendChild(this.oFocusEl);
Element.addClassName(this.oFocusEl, this.sFocusElCls);
this.oContainer = document.createElement("div");
document.body.appendChild(this.oContainer);
Element.addClassName(this.oContainer, this.sMenuCls);
Element.addClassName(this.oContainer, this.sBaseCls);
this.oIframeShield = document.createElement("iframe");
this.oIframeShield.src = Ext.blankUrl;
this.oIframeShield.frameBorder = "no";
this.oIframeShield.scrolling = "no";
document.body.appendChild(this.oIframeShield);
Element.addClassName(this.oIframeShield, this.sShieldCls);
this.bindEvents();
},
renderItems : function(event){
if(this.fireEvent('beforerender', event) === false) return;
this.oContainer.innerHTML = this.html();
},
html : function(){
var items = this.items;
if(Ext.isString(items)) return items;
var aHtml = [];
for(var index = 0; index < items.length; index++){
var oItem = items[index];
if(oItem == this.sSeparator){
if(aHtml.last() != separateTemplate){
aHtml.push(separateTemplate);
}
continue;
}
if(oItem['visible'] != null){
var visible = oItem['visible'];
if(Ext.isFunction(visible)){
visible = visible.call(this, this.args);
}
if(!visible) continue;
}
var aCls = ['item'];
var cls = oItem['cls'];
if(cls){
aCls.push(Ext.isFunction(cls) ? (cls(this.args)) : cls);
}
if(aCls.include(this.sHideCls)) continue;
aHtml.push([
'<div',
oItem['title'] != '' ? (' title="' + (oItem['title'] || oItem['desc'] || '') + '"') : "",
' class="', aCls.join(' '), '"', 
oItem['oprKey'] ? (' oprKey="' + oItem['oprKey'] + '"') : '',
oItem['oprKey'] ? (' id="' + oItem['oprKey'] + '"') : '',
'>',
'<div class="icon ', (oItem['iconCls'] || ''), '"></div>',
'<div class="desc">', oItem['desc'] || '', '</div>',
'</div>'
].join(""));
}
if(aHtml.last() == separateTemplate) aHtml.pop();
if(aHtml.length <= 0) return "";
return aHtml.join("");
},
hide : function(){
if(this.fireEvent('beforehide') === false) return;
this.onHide();
this.fireEvent('hide');
},
onHide : function(){
Element.hide(this.oContainer);
Element.hide(this.oIframeShield);
this.oLastSelectedItem = null;
this.isShow = false;
},
getArgs : function(){
return this.args;
},
getItems : function(){
return this.items;
},
show : function(items, args){
if(items){
this.items = items;
}
items = this.items;
this.args = args || {};
if(this.fireEvent('beforeshow') === false) return;
this.renderBox();
this.renderItems();
if(this.oContainer.innerHTML == "") return;
this.onShow();
this.fireEvent('show');
},
onShow : function(){
this.oContainer.style.overflow = 'visible';
var offset = Element.getDimensions(this.oContainer);
var width = Math.max(Math.min(this.maxWidth, offset["width"]), this.minWidth);
this.oContainer.style.width = width;
this.oContainer.style.overflow = 'hidden';
Element.show(this.oContainer);
var left = parseInt(this.args["x"], 10) || 0;
var right = left + parseInt(this.oContainer.offsetWidth, 10);
if(right >= parseInt(this.oContainer.ownerDocument.body.offsetWidth, 10)){
left = Math.max(left - offset["width"], 0);
}
this.oContainer.style.left = left + "px";
var top = parseInt(this.args["y"], 10) || 0;
var bottom = top + parseInt(this.oContainer.offsetHeight, 10);
if(bottom >= parseInt(this.oContainer.ownerDocument.body.offsetHeight, 10)){
top = Math.max(top - offset["height"] - 5, 0);
}
this.oContainer.style.top = top + "px";
Position.clone(this.oContainer, this.oIframeShield);
Element.show(this.oIframeShield);
this.isShow = true;
setTimeout(function(){
try{
this.oFocusEl.focus();
}catch(error){
alert(error.message);
}
}.bind(this), 10);
},
destroy : function(){
Event.stopAllObserving(this.oContainer);
delete this.oContainer;
delete this.oIframeShield;
delete this.oFocusEl;
delete this.items;
delete this.oLastSelectedItem;
delete this.args;
}
});
})();

Ext.ns('wcm.cms.menu');
Ext.apply(Array.prototype, {
last : function(){
return this[this.length - 1];
}
});
wcm.cms.menu.CMSMenu = new com.trs.menu.SimpleMenu();
Ext.apply(wcm.cms.menu.CMSMenu, {
filter : function(type){
var args = this.getArgs();
var context = args.event.context;
if(context.fromTree){
var dom = context.element;
if(dom && dom.tagName != "A"){
if(!type) return true;
return false;
}
}
var wcmEvent = args.wcmEvent;
if(!wcmEvent) return !type || false;
var context = args.event.getContext();
if(context.isHost) return false;
var objs = wcmEvent.getObjs();
return objs!= null && objs.getType() == type;
},
fromTree : function(){
var args = this.getArgs();
var context = args.event.context;
return context.get('fromTree');
}
});
wcm.cms.menu.OperatorAdapter = function(){
return {
execute : function(aOpers, wcmEvent){
var opers = aOpers[0].concat(aOpers[1]);
var len = aOpers[0].length;
var menuItems = [];
for (var i = 0; i < opers.length; i++){
var oper = opers[i];
var menuItem = this.item(opers[i], wcmEvent);
menuItem['visible'] = i < len;
menuItems.push(menuItem);
}
return menuItems;
},
item : function(oper, wcmEvent){
var oprKey = oper["key"] || "";
if(oprKey == 'seperate') oprKey = 'separate';
return {
oprKey : oprKey.toLowerCase(),
desc : oper["desc"],
title : oper['title'] || "",
iconCls : oper["key"],
order : oper["order"],
cmd : wcm.SysOpers.exec.bind(wcm.SysOpers, oper, wcmEvent)
};
}
};
}();
(function(){
var indexOf = function(sOprKey){
sOprKey = sOprKey.toLowerCase();
var items = this.items;
for (var i = 0; i < items.length; i++){
if(items[i]["oprKey"] == sOprKey){
return i;
}
}
return -1;
};
var add = function(oItem){
if(oItem["oprKey"]){
oItem["oprKey"] = oItem["oprKey"].toLowerCase();
}
if(oItem["order"] == null){
var order;
if(this.items.length > 0){
order = this.items.last().order;
}
var order = order || this.items.length;
oItem["order"] = order + 1;
}
this.items.push(oItem);
};
var unselectAll = function(){
var items = this.items;
for (var i = 0; i < items.length; i++){
items[i]['visible'] = false;
}
};
var select = function(sOprKey){
if(Ext.isArray(sOprKey)){
select.apply(this, sOprKey);
return;
}
if(arguments.length > 1){
for (var i = 0; i < arguments.length; i++){
select.call(this, arguments[i]);
}
return;
}
if(sOprKey == "/"){
select.index = (select.index || 0) + 1;
var item = {oprKey : 'separate', order : select.index};
add.call(this, item);
return;
}
var item = this.get(sOprKey);
if(item) {
select.index = (select.index || 0) + 1;
Ext.apply(item, {
visible : true,
order : select.index
});
}
};
var handleOperItem = function(item){
if(!item) return false;
var operInfo = item['operItem'];
if(!operInfo) return false;
delete item['operItem'];
operItem = wcm.SysOpers.getOperItem(operInfo[0], operInfo[1], this.wcmEvent);
if(!operItem) return true;
var currOperItem = this.get(item["oprKey"] || operInfo[1])
if(currOperItem){
currOperItem['visible'] = true;
Ext.apply(currOperItem, item);
return;
}
var menuItem = wcm.cms.menu.OperatorAdapter.item(operItem, this.wcmEvent);
item['order'] = item['order'] || null;
Ext.apply(menuItem, item);
add.call(this, menuItem);
return true;
};
var handleNormalItem = function(item){
var menuItem = this.get(item["oprKey"]);
if(menuItem){
Ext.apply(menuItem, item);
return;
}
item['cmd'] = (item['cmd'] || Ext.emptyFn).bind(item, this.wcmEvent);
add.call(this, item);
};
wcm.cms.menu.ItemGroup = function(items, wcmEvent){
this.items = items || [];
this.wcmEvent = wcmEvent;
};
Ext.apply(wcm.cms.menu.ItemGroup.prototype, {
get : function(sOprKey){
var index = indexOf.apply(this, arguments);
return this.items[index];
},
select : function(sOprKey){
unselectAll.apply(this, arguments);
select.index = 0;
select.apply(this, arguments);
},
unapply : function(sOprKey){
if(Ext.isArray(sOprKey)){
this.unapply.apply(this, sOprKey);
return;
}
if(arguments.length > 1){
for (var i = 0; i < arguments.length; i++){
this.unapply.call(this, arguments[i]);
}
return;
}
var index = indexOf.call(this, sOprKey);
if(index == -1) return;
this.items.splice(index, 1);
},
applyCfgs : function(oItem){
if(Ext.isArray(oItem)){
this.applyCfgs.apply(this, oItem);
return;
}
if(arguments.length > 1){
for (var i = 0; i < arguments.length; i++){
this.applyCfgs.call(this, arguments[i]);
}
return;
}
if(oItem['oprKey'] == "separate"){
add.call(this, oItem);
return;
}
if(handleOperItem.call(this, oItem)) return;
handleNormalItem.call(this, oItem);
},
applyCfgsMapping : function(fMapping){
this.items.each(fMapping);
}
});
})();
(function(){
var getPosition = function(event){
var dom = Event.element(event);
var doc = dom.ownerDocument;
var win = doc.parentWindow || doc.defaultView;
var frameElement = win.frameElement;
var topOffset = Position.getPageInTop(frameElement);
var pageOffsetX = event.pageX || (event.clientX +
(doc.documentElement.scrollLeft || doc.body.scrollLeft));
var pageOffsetY = event.pageY || (event.clientY +
(doc.documentElement.scrollTop || doc.body.scrollTop));
return [topOffset[0] + pageOffsetX, topOffset[1] + pageOffsetY];
};
var showMenu = function(items, xwcmEvent){
var event = xwcmEvent.getContext().event;
var wcmEvent = xwcmEvent.getContext().wcmEvent;
var position = getPosition(event);
var itemGroup = new wcm.cms.menu.ItemGroup(items, wcmEvent);
wcm.cms.menu.CMSMenu.show(items, {
x : position[0],
y : position[1],
event : xwcmEvent,
wcmEvent : wcmEvent,
items : itemGroup
});
};
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_TREENODE,
contextmenu : function(event){
var context = event.getContext();
if(!context) return;
var dom = context.element;
var wcmEvent = context.wcmEvent;
var opers = [[],[]];
if(wcmEvent != null && dom && dom.tagName == "A"){
wcmEvent.displayNum = wcmEvent.displayNum || 6;
opers = wcm.SysOpers.getOpersByInfo(wcmEvent);
}
showMenu(wcm.cms.menu.OperatorAdapter.execute(opers, wcmEvent), event);
}
});
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_MAINPAGE,
contextmenu : function(event){
var context = event.getContext();
if(!context) return;
var wcmEvent = context.wcmEvent;
var opers = context.opers;
if(!opers){
wcmEvent.displayNum = wcmEvent.displayNum || 7;
opers = wcm.SysOpers.getOpersByInfo(wcmEvent);
}
showMenu(wcm.cms.menu.OperatorAdapter.execute(opers, wcmEvent), event);
}
});
})();
wcm.cms.menu.CMSMenu.on('beforerender', function(){
this.items.sort(function(item1, item2){
return (item1.order || 0) - (item2.order || 1);
});
var items = this.items;
for (var i = 0; i < items.length; i++){
if(items[i]['oprKey'] == 'separate'){
items[i] = "/";
}
}
});
wcm.cms.menu.CMSMenu.on('show', function(){
this.treenodecontextmenu = false;
var wcmEvent = this.getArgs().event;
if(!wcmEvent) return;
var context = wcmEvent.getContext();
if(!context) return;
if(!context.fromTree) return;
var dom = context.element;
if(!dom || dom.tagName != "A") return;
this.treenodecontextmenu = true;
Element.addClassName(dom, 'contextmenustatus');
});
wcm.cms.menu.CMSMenu.on('hide', function(){
if(!this.treenodecontextmenu) return;
var wcmEvent = this.getArgs().event;
var context = wcmEvent.getContext();
var dom = context.element;
Element.removeClassName(dom, 'contextmenustatus');
});

