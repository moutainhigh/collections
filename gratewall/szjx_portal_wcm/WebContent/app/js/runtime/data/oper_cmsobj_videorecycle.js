Ext.apply(wcm.LANG, {
VIDEORECYCLE_UNIT : '个',
VIDEORECYLE : '视频',
VIDEOTITLE : '视频标题',
VIDEOKEYWORDS : '关键字',
OPERUSER : '删除者',
VIDEORECYLE_1 : '还原这个视频',
VIDEORECYLE_2 : '删除这个视频',
VIDEORECYLE_3 : '还原所有视频',
VIDEORECYLE_4 : '还原这些视频',
VIDEORECYLE_5 : '删除这些视频',
VIDEORECYLE_6 : '清空废稿箱',
VIDEORECYLE_7 : '还原这个视频到原位置',
VIDEORECYLE_8 : '清空当前栏目的废稿箱',
VIDEORECYLE_9 : '还原废稿箱中的所有视频',
VIDEORECYLE_10 : '清空当前站点的废稿箱',
VIDEORECYLE_11 : '还原这些视频到原位置',
VIDEORECYCLE_CONFIRM_1 : '您当前所执行的操作将彻底删除废稿箱中所有视频，您确定仍要继续清空当前废稿箱？',
VIDEORECYCLE_SYSTEMINFO : '系统提示信息',
TRUE : '确定',
CANCEL : '取消',
VIDEORECYLE_DESC_1 : '您正准备强制删除如下视频：',
VIDEORECYLE_DESC_2 : '您正准备删除如下视频：',
VIDEORECYLE_DESC_3 : '您正准备还原如下视频：',
VIDEORECYLE_DESC_4 : '您正准备还原如下视频（含有不能还原的视频）：',
VIDEORECYLE_DESC_5 : '您正准备将如下视频放入废稿箱：',
VIDEORECYLE_DESC_6 : '由于以下原因，您无法进行视频还原操作！',
VIDEORECYLE_DESC_7 : '无法还原：视频所在栏目尚在栏目回收站内',
VIDEORECYLE_DESC_8 : '无法还原：视频所属栏目尚在栏目回收站内',
VIDEORECYLE_DESC_9 : '无法还原：引用视频的实体尚在废稿箱内',
VIDEORECYLE_DESC_10 : '......视频较多，不一一列出！^-^',
VIDEORECYLE_DESC_11 : '未知操作',
VIDEORECYLE_12 : '恢复视频',
VIDEORECYLE_13 : '恢复这个/些视频',
VIDEORECYLE_14 : '恢复所有视频',
VIDEORECYLE_15 : '彻底删除视频',
VIDEORECYLE_16 : '彻底删除这个/些视频',
VIDEORECYLE_17 : '清空回收站',
VIDEORECYLE_18 : '返回视频列表',
VIDEORECYLE_19 : '刷新',
VIDEORECYLE_20 : '刷新列表',
VIDEORECYLE_21 : '废稿箱列表管理',
VIDEORECYLE_22 : '删除视频...',
VIDEORECYLE_23 : '还原视频...'
});

Ext.ns('wcm.domain.VideoRecycleMgr');
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
var m_oMgr = wcm.domain.VideoRecycleMgr ={
serviceId : 'wcm61_video',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.VideoRecycleMgr, {
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
if(confirm(wcm.LANG.DOCRECYCLE_CONFIRM_1 || '您当前所执行的操作将彻底删除废稿箱中所有视频，您确定仍要继续清空当前废稿箱？')){
ProcessBar.start(wcm.LANG.VIDEORECYLE_22 || '删除视频..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycleVideos', params, true, function(){
ProcessBar.close();
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
m_oMgr._delete0(event);
return;
}
m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
},
_delete0 : function(event){
ProcessBar.start(wcm.LANG.VIDEORECYLE_22 || '删除视频..');
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var _params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
Object.extend(_params, {ObjectIds: sIds, drop: true});
m_oMgr.getHelper().call(m_oMgr.serviceId, 'deleteVideos', _params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
},
doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'docrecycle/video_info.html',
width:'520px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
},
restoreall : function(event, operItem){
return m_oMgr.restore(event, operItem, true);
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
}else{
Object.extend(params, {
objectids: event.getIds()
});
}
m_oMgr.doOptionsAfterDisplayInfo(params, function(){
ProcessBar.start(wcm.LANG.VIDEORECYLE_23 || '还原视频...');
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
}.bind(this));
}
});
})();
(function(){
var pageObjMgr = wcm.domain.VideoRecycleMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'restore',
type : 'videorecycle',
desc : wcm.LANG.VIDEORECYLE_1 || '还原这个视频',
title : wcm.LANG.VIDEORECYLE_7 || '还原这个视频到原位置',
rightIndex : 33,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'videorecycle',
desc : wcm.LANG.VIDEORECYLE_2 || '删除这个视频',
title : wcm.LANG.VIDEORECYLE_2 || '删除这个视频',
rightIndex : 33,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'videorecycleInChannel',
desc : wcm.LANG.VIDEORECYLE_6 || '清空废稿箱',
title : wcm.LANG.VIDEORECYLE_8 || '清空当前栏目的废稿箱',
rightIndex : 33,
order : 3,
fn : pageObjMgr['deleteall']
});
reg({
key : 'restoreall',
type : 'videorecycleInChannel',
desc : wcm.LANG.VIDEORECYLE_3 || '还原所有视频',
title : wcm.LANG.VIDEORECYLE_9 || '还原废稿箱中的所有视频',
rightIndex : 33,
order : 4,
fn : pageObjMgr['restoreall']
});
reg({
key : 'deleteall',
type : 'videorecycleInSite',
desc : wcm.LANG.VIDEORECYLE_6 || '清空废稿箱',
title : wcm.LANG.VIDEORECYLE_10 || '清空当前站点的废稿箱',
rightIndex : 33,
order : 5,
fn : pageObjMgr['deleteall']
});
reg({
key : 'restoreall',
type : 'videorecycleInSite',
desc : wcm.LANG.VIDEORECYLE_3 || '还原所有视频',
title : wcm.LANG.VIDEORECYLE_9 || '还原废稿箱中的所有视频',
rightIndex : 33,
order : 6,
fn : pageObjMgr['restoreall']
});
reg({
key : 'restore',
type : 'videorecycles',
desc : wcm.LANG.VIDEORECYLE_4 || '还原这些视频',
title : wcm.LANG.VIDEORECYLE_11 || '还原这些视频到原位置',
rightIndex : 33,
order : 7,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'videorecycles',
desc : wcm.LANG.VIDEORECYLE_5 || '删除这些视频',
title : wcm.LANG.VIDEORECYLE_5 || '删除这些视频',
rightIndex : 33,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.VideoRecycles', 'wcm.VideoRecycle');
WCMConstants.OBJ_TYPE_VIDEORECYCLE = 'videorecycle';
wcm.VideoRecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_VIDEORECYCLE;
wcm.VideoRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.VideoRecycles, wcm.CMSObjs, {
});
wcm.VideoRecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_VIDEORECYCLE;
wcm.VideoRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_VIDEORECYCLE, 'wcm.VideoRecycle');
Ext.extend(wcm.VideoRecycle, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['VIDEORECYCLE'] || '视频';
wcm.PageOper.registerPanels({
vInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
videorecycleInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
videorecycleInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
videorecycle : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_video&methodname=recyleFindById',
params : function(opt){
var host = opt.event.getHost()
switch(host.getType()){
case "website" :
return "siteId=" + host.getId();
case "channel" :
return "channelId=" + host.getId();
}
}
},
videorecycles : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

