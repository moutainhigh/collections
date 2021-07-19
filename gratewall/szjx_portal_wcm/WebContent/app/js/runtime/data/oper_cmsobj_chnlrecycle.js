Ext.apply(wcm.LANG, {
CHNLRECYCLE_UNIT : '个',
CHNLRECYCLE : '栏目回收站',
CHNLRECYCLE_CHNLDESC : '显示名称',
CHNLRECYCLE_CHNLNAME : '唯一标识',
CHNLRECYCLE_OPERUSER : '删除者',
CHNLRECYCLE_CHANNELID : '栏目ID',
CHNLRECYCLE_1 : '还原这个栏目',
CHNLRECYCLE_2 : '还原这个栏目到原位置',
CHNLRECYCLE_3 : '删除这个栏目',
CHNLRECYCLE_4 : '清空回收站',
CHNLRECYCLE_5 : '清空当前栏目的栏目回收站',
CHNLRECYCLE_6 : '还原所有栏目',
CHNLRECYCLE_7 : '还原栏目回收站中的所有栏目',
CHNLRECYCLE_8 : '清空当前站点的栏目回收站',
CHNLRECYCLE_9 : '还原这些栏目',
CHNLRECYCLE_10 : '还原这些栏目到原位置',
CHNLRECYCLE_11 : '删除这些栏目',
CHNLRECYCLE_12 : '确实要还原栏目回收站中的所有栏目吗？',
CHNLRECYCLE_13 : '确实要删除栏目回收站中的所有栏目吗？',
CHNLRECYCLE_14 : '确实要还原此栏目吗？',
CHNLRECYCLE_15 : '确实要还原这{0}个栏目吗？',
CHNLRECYCLE_16 : "确实要删除此栏目吗？",
CHNLRECYCLE_17 : "确实要删除这{0}个栏目吗？",
CHNLRECYCLE_18 : '还原',
CHNLRECYCLE_19 : '还原栏目',
CHNLRECYCLE_20 : '彻底删除',
CHNLRECYCLE_21 : '彻底删除栏目',
CHNLRECYCLE_22 : '刷新',
CHNLRECYCLE_23 : '刷新列表',
CHNLRECYCLE_24 : '栏目回收站列表管理',
CHNLRECYCLE_25 : '还原栏目...',
CHNLRECYCLE_26 : '删除栏目...',
CHNLRECYCLE_CHNL : '栏目'
});

Ext.ns('wcm.domain.ChnlrecycleMgr');
(function(){
var m_oMgr = wcm.domain.ChnlrecycleMgr ={
serviceId : 'wcm6_channel',
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.ChnlrecycleMgr, {
restoreall : function(event, operItem){
return m_oMgr.restore(event, operItem, true);
},
restore : function(event, operItem, _bRestoreAll){
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
var sClue = '';
if(_bRestoreAll){
sClue = wcm.LANG.CHNLRECYCLE_12 || '确实要还原栏目回收站中的所有栏目吗?';
}else{
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
if(nCount == 1){
sClue = wcm.LANG.CHNLRECYCLE_14 || '确实要还原此栏目吗?';
}else{
sClue = String.format(wcm.LANG.CHNLRECYCLE_15 || ('确实要还原这{0}个栏目吗?'),nCount);
}
}
if (confirm(sClue)){
Object.extend(params, {objectids: sIds});
if(_bRestoreAll) {
params.RestoreAll = true;
}
ProcessBar.start(wcm.LANG.CHNLRECYCLE_25||'还原栏目...');
m_oMgr.getHelper().call('wcm61_channel', 'restoreChannels', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
},
'delete' : function(event){
var sIds = event.getIds();
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
var sCon = "";
if(nCount == 1){
sCon = wcm.LANG.CHNLRECYCLE_16 || "确实要删除此栏目吗?";
}else{
sCon = String.format(wcm.LANG.CHNLRECYCLE_17 || ("确实要删除这{0}个栏目吗?"),nCount);
}
m_oMgr.getHelper().call('wcm61_special', 'findByChnlIds', {ChannelIds : sIds}, true, function(transport, _json){
try{
var oSpecials = $a(_json, "Specials.Special");
if(!oSpecials[0]){
if (confirm(sCon)){
ProcessBar.start(wcm.LANG.CHNLRECYCLE_26||'删除栏目..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', {ObjectIds: sIds, drop: true}, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
}else{
alert("包含与专题对应的栏目，这样的栏目不能直接被删除，请到专题管理页面删除对应专题！");
}
}catch(e){
}
});
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
if (confirm((wcm.LANG.CHNLRECYCLE_13 || '确实要删除栏目回收站中的所有栏目吗?') + (wcm.LANG.CHNLRECYCLE_27 || '如果包含有专题相关的栏目，则删除后会导致对应不能还原！'))){
ProcessBar.start(wcm.LANG.CHNLRECYCLE_26||'删除栏目..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChnlrecycleMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(!context.gridInfo) return true;
return context.gridInfo.RecordNum > 0;
};
reg({
key : 'restore',
type : 'chnlrecycle',
desc : wcm.LANG.CHNLRECYCLE_1 || '还原这个栏目',
title : wcm.LANG.CHNLRECYCLE_2 || '还原这个栏目到原位置',
rightIndex : 12,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'chnlrecycle',
desc : wcm.LANG.CHNLRECYCLE_3 || '删除这个栏目',
title : wcm.LANG.CHNLRECYCLE_3 || '删除这个栏目',
rightIndex : 12,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'chnlrecycleInChannel',
desc : wcm.LANG.CHNLRECYCLE_4 || '清空回收站',
title : wcm.LANG.CHNLRECYCLE_5 || '清空当前栏目的栏目回收站',
rightIndex : -1,
order : 3,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'chnlrecycleInChannel',
desc : wcm.LANG.CHNLRECYCLE_6 || '还原所有栏目',
title : wcm.LANG.CHNLRECYCLE_7 || '还原栏目回收站中的所有栏目',
rightIndex : -1,
order : 4,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'deleteall',
type : 'chnlrecycleInSite',
desc : wcm.LANG.CHNLRECYCLE_4 || '清空回收站',
title : wcm.LANG.CHNLRECYCLE_8 || '清空当前站点的栏目回收站',
rightIndex : -1,
order : 5,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'chnlrecycleInSite',
desc : wcm.LANG.CHNLRECYCLE_6 || '还原所有栏目',
title : wcm.LANG.CHNLRECYCLE_7 || '还原栏目回收站中的所有栏目',
rightIndex : -1,
order : 6,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'restore',
type : 'chnlrecycles',
desc : wcm.LANG.CHNLRECYCLE_9 || '还原这些栏目',
title : wcm.LANG.CHNLRECYCLE_10 || '还原这些栏目到原位置',
rightIndex : 12,
order : 7,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'chnlrecycles',
desc : wcm.LANG.CHNLRECYCLE_11 || '删除这些栏目',
title : wcm.LANG.CHNLRECYCLE_11 || '删除这些栏目',
rightIndex : 12,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.Chnlrecycles', 'wcm.Chnlrecycle');
WCMConstants.OBJ_TYPE_CHNLRECYCLE = 'Chnlrecycle';
wcm.Chnlrecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHNLRECYCLE;
wcm.Chnlrecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Chnlrecycles, wcm.CMSObjs, {
});
wcm.Chnlrecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHNLRECYCLE;
wcm.Chnlrecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHNLRECYCLE, 'wcm.Chnlrecycle');
Ext.extend(wcm.Chnlrecycle, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['CHNLRECYCLE'] || '栏目回收站';
wcm.PageOper.registerPanels({
chnlrecycleInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
chnlrecycleInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE_SHORT'] || '{0}操作任务', sName),
displayNum : 5
},
chnlrecycleInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL_SHORT'] || '{0}操作任务', sName),
displayNum : 5
},
chnlrecycle : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_channel&methodname=recycleFindbyid'
},
chnlrecycles : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

