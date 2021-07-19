Ext.apply(wcm.LANG, {
DOCRECYCLE_UNIT : '篇',
DOCRECYLE : '文档',
DOCTITLE : '文档标题',
DOCKEYWORDS : '关键字',
OPERUSER : '删除者',
DOCRECYLE_1 : '还原这篇文档',
DOCRECYLE_2 : '删除这篇文档',
DOCRECYLE_3 : '还原所有文档',
DOCRECYLE_4 : '还原这些文档',
DOCRECYLE_5 : '删除这些文档',
DOCRECYLE_6 : '清空废稿箱',
DOCRECYLE_7 : '还原这篇文档到原位置',
DOCRECYLE_8 : '清空当前栏目的废稿箱',
DOCRECYLE_9 : '还原废稿箱中的所有文档',
DOCRECYLE_10 : '清空当前站点的废稿箱',
DOCRECYLE_11 : '还原这些文档到原位置',
DOCRECYCLE_CONFIRM_1 : '您当前所执行的操作将彻底删除废稿箱中所有文档，您确定仍要继续清空当前废稿箱？',
DOCRECYCLE_SYSTEMINFO : '系统提示信息',
TRUE : '确定',
CANCEL : '取消',
DOCRECYLE_DESC_1 : '您正准备强制删除如下文档：',
DOCRECYLE_DESC_2 : '您正准备删除如下文档：',
DOCRECYLE_DESC_3 : '您正准备还原如下文档：',
DOCRECYLE_DESC_4 : '您正准备还原如下文档（含有不能还原的文档）：',
DOCRECYLE_DESC_5 : '您正准备将如下文档放入废稿箱：',
DOCRECYLE_DESC_6 : '由于以下原因，您无法进行文档还原操作！',
DOCRECYLE_DESC_7 : '无法还原：文档所在栏目尚在栏目回收站内',
DOCRECYLE_DESC_8 : '无法还原：文档所属栏目尚在栏目回收站内',
DOCRECYLE_DESC_9 : '无法还原：引用文档的实体尚在废稿箱内',
DOCRECYLE_DESC_10 : '......文档较多，不一一列出！^-^',
DOCRECYLE_DESC_11 : '未知操作',
DOCRECYLE_DESC_12 : '您正准备复制如下文档:',
DOCRECYLE_DESC_13 : '您正准备移动如下文档:',
DOCRECYLE_DESC_14 : '您正准备引用如下文档:',
DOCRECYLE_12 : '还原文档',
DOCRECYLE_13 : '还原这篇/些文档',
DOCRECYLE_14 : '还原所有文档',
DOCRECYLE_15 : '彻底删除文档',
DOCRECYLE_16 : '彻底删除这篇/些文档',
DOCRECYLE_17 : '清空废稿箱',
DOCRECYLE_18 : '返回文档列表',
DOCRECYLE_19 : '刷新',
DOCRECYLE_20 : '刷新列表',
DOCRECYLE_21 : '废稿箱列表管理',
DOCRECYLE_22 : '删除文档...',
DOCRECYLE_23 : '还原文档...',
DOCRECYLE_24 : '部分文档所在的栏目已经在回收站，无法彻底删除这些文档！',
DOCRECYLE_25 : '您确定要还原所有文档?',
DOCRECYLE_26 : '您确定要还原这篇/这些文档?',
DOCRECYLE_27 : '确实要删除这篇/这些文档吗?',
DOCRECYLE_28 : '部分文档所在的栏目已经在回收站,无法还原所有文档!',
DOCRECYLE_29 : '] 和 ['
});

Ext.ns('wcm.domain.DocRecycleMgr');
(function(){
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
var m_oMgr = wcm.domain.DocRecycleMgr ={
serviceId : 'wcm61_viewdocument',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.DocRecycleMgr, {
view : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var pageContext = event.getContext();
var oParams = Ext.apply({
DocumentId : pageContext.docid,
ChnlDocId : event.getIds(),
FromRecycle : 1
},parseHost(event.getHost()));
$openMaxWin(WCMConstants.WCM6_PATH +
'document/document_show.jsp?' + $toQueryStr(oParams));
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
}
if(confirm(wcm.LANG.DOCRECYCLE_CONFIRM_1 || '您当前所执行的操作将彻底删除废稿箱中所有文档,您确定仍要继续清空当前废稿箱?')){
ProcessBar.start(wcm.LANG.DOCRECYLE_22 || '删除文档..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
ProcessBar.close();
$MsgCenter.getActualTop().isDeletingAll = true;
event.getObjs().afterdelete();
});
}
},
'delete' : function(event){
var sIds = event.getIds();
var hostId = event.getHost().getId();
var _params = event.getContext();
var params = {
objectids: sIds,
operation: '_delete'
}
var aIds = sIds;
if(String.isString(aIds)){
aIds = aIds.split(",");
}
if(aIds.length >= 50){
if(confirm(wcm.LANG.DOCRECYLE_27 || "确实要删除这篇/这些文档吗?")){
m_oMgr._delete0(event);
}
return;
}
m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
},
_delete0 : function(event){
ProcessBar.start(wcm.LANG.DOCRECYLE_22 || '删除文档..');
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var _params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
Object.extend(_params, {ObjectIds: sIds, drop: true});
m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', _params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
},
doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'docrecycle/document_info.html',
width:'520px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
},
restoreall : function(event, operItem){
if(confirm(wcm.LANG.DOCRECYLE_25 || '您确定要还原所有文档?')){
return m_oMgr.restore(event, operItem, true);
}
},
restore : function(event, operItem, _bRestoreAll){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
operation: '_restore',
special: true
}
if(_bRestoreAll == true) {
params['restoreAll'] = true;
Object.extend(params, {
channelids: (hostType == 101)?hostId:'',
siteids: (hostType == 103)?hostId:''
});
$MsgCenter.getActualTop().isRestoringAll = true;
}else{
Object.extend(params, {
objectids: event.getIds()
});
}
var restore = function(){
ProcessBar.start(wcm.LANG.DOCRECYLE_23 || '还原文档...');
var postData = {};
if(_bRestoreAll == true) {
postData['restoreAll'] = true;
Object.extend(postData, {
channelid: (hostType == 101)?hostId:'',
siteid: (hostType == 103)?hostId:''
});
}else{
Object.extend(postData, {
objectids: event.getIds()
});
}
m_oMgr.getHelper().call(m_oMgr.serviceId, 'restore', postData, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}.bind(this);
if(_bRestoreAll){
restore();
}else{
m_oMgr.doOptionsAfterDisplayInfo(params, restore);
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.DocRecycleMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(!context.gridInfo) return true;
return context.gridInfo.RecordNum > 0;
};
reg({
key : 'restore',
type : 'docrecycle',
desc : wcm.LANG.DOCRECYLE_1 || '还原这篇文档',
rightIndex : 33,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'docrecycle',
desc : wcm.LANG.DOCRECYLE_2 || '删除这篇文档',
rightIndex : 33,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'docrecycleInChannel',
desc : wcm.LANG.DOCRECYLE_6 || '清空废稿箱',
rightIndex : 33,
order : 3,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'docrecycleInChannel',
desc : wcm.LANG.DOCRECYLE_3 || '还原所有文档',
rightIndex : 33,
order : 4,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'deleteall',
type : 'docrecycleInSite',
desc : wcm.LANG.DOCRECYLE_6 || '清空废稿箱',
rightIndex : 33,
order : 5,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'docrecycleInSite',
desc : wcm.LANG.DOCRECYLE_3 || '还原所有文档',
rightIndex : 33,
order : 6,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'restore',
type : 'docrecycles',
desc : wcm.LANG.DOCRECYLE_4 || '还原这些文档',
rightIndex : 33,
order : 7,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'docrecycles',
desc : wcm.LANG.DOCRECYLE_5 || '删除这些文档',
rightIndex : 33,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.DocRecycles', 'wcm.DocRecycle');
WCMConstants.OBJ_TYPE_DOCRECYCLE = 'DocRecycle';
wcm.DocRecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCRECYCLE;
wcm.DocRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.DocRecycles, wcm.CMSObjs, {
});
wcm.DocRecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCRECYCLE;
wcm.DocRecycle.superclass.constructor.call(this, _config, _context);
var arrRights = {
"view" : -1
};
for(rightName in arrRights){
var cameRightName = rightName.camelize();
var nRightIndex = parseInt(arrRights[rightName], 10);
this['can'+cameRightName] = function(){
return wcm.AuthServer.hasRight(this.right, nRightIndex);
}
}
}
CMSObj.register(WCMConstants.OBJ_TYPE_DOCRECYCLE, 'wcm.DocRecycle');
Ext.extend(wcm.DocRecycle, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['DOCRECYCLE'] || '废稿箱';
wcm.PageOper.registerPanels({
docrecycleInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
docrecycleInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
docrecycleInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
docrecycle : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_viewdocument&methodname=recyleFindById',
params : function(opt){
return (opt.event.getHost().getIntType()==103)?"siteid=" + opt.event.getHost().getId() : "channelid=" + opt.event.getHost().getId();
}
},
docrecycles : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

