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
if(Ext.isSimpType(o))return o;
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
return rst==null ? null : (rst['NODEVALUE'] != null ? rst['NODEVALUE'] : rst);
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

