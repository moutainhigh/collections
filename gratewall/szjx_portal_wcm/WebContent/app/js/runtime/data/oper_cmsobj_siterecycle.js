Ext.apply(wcm.LANG, {
SITERECYCLE_UNIT : '个',
SITERECYCLE : '站点回收站',
SITERECYCLE_DESC :'显示名称',
SITERECYCLE_NAME : '唯一标识',
SITERECYCLE_SITEID : '站点ID',
SITERECYCLE_OPERUSER : '删除者',
SITERECYCLE_1 : '还原这个站点',
SITERECYCLE_2 : '还原这个站点到原位置',
SITERECYCLE_3 : '删除这个站点',
SITERECYCLE_4 : '清空回收站',
SITERECYCLE_5 : '删除站点回收站中的所有站点',
SITERECYCLE_6 : '还原所有站点',
SITERECYCLE_7 : '还原站点回收站中的所有站点',
SITERECYCLE_8 : '还原这些站点',
SITERECYCLE_9 : '还原这些站点到原位置',
SITERECYCLE_10 : '删除这些站点',
SITERECYCLE_11 : '确实要还原站点回收站中的所有站点吗？',
SITERECYCLE_12 : '确实要删除站点回收站中的所有站点吗？',
SITERECYCLE_13 : '确实要删除此站点吗？',
SITERECYCLE_14 : '确实要还原此站点吗？',
SITERECYCLE_15 : '确实要还原这{0}个站点吗？',
SITERECYCLE_16 : '确实要删除这{0}个站点吗？',
SITERECYCLE_17 : '还原',
SITERECYCLE_18 : '还原站点',
SITERECYCLE_19 : '彻底删除',
SITERECYCLE_20 : '彻底删除站点',
SITERECYCLE_21 : '刷新',
SITERECYCLE_22 : '刷新列表',
SITERECYCLE_23 : '站点回收站列表管理',
SITERECYCLE_24 : '还原站点...',
SITERECYCLE_25 : '删除站点...',
SITERECYCLE_SITE : '站点'
});

Ext.ns('wcm.domain.SiteRecycleMgr');
(function(){
var m_oMgr = wcm.domain.SiteRecycleMgr ={
serviceId : 'wcm6_website',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.SiteRecycleMgr, {
restoreall : function(event, operItem){
return m_oMgr.restore(event, operItem, true);
},
restore : function(event, operItem, _bRestoreAll){
var sClue = '';
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var params = {
siteType : hostId
};
if(_bRestoreAll){
sClue = wcm.LANG.SITERECYCLE_11 || '确实要还原站点回收站中的所有站点吗?';
}else{
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
if(nCount == 1){
sClue = wcm.LANG.SITERECYCLE_14 || '确实要还原此站点吗?';
}else{
sClue = String.format(wcm.LANG.SITERECYCLE_15 || ('确实要还原这{0}个站点吗?'),nCount);
}
}
if (confirm(sClue)){
Object.extend(params, {objectids: sIds});
if(_bRestoreAll) {
params.RestoreAll = true;
}
ProcessBar.start(wcm.LANG.SITERECYCLE_24 || '还原站点..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'restoreSites', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var params = {
siteType : hostId
};
if (confirm(wcm.LANG.SITERECYCLE_12 || '确实要删除站点回收站中的所有站点吗?')){
ProcessBar.start(wcm.LANG.SITERECYCLE_25 || '删除站点..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
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
sCon = wcm.LANG.SITERECYCLE_13 || "确实要删除此站点吗?";
}else{
sCon = String.format(wcm.LANG.SITERECYCLE_16 || ("确实要删除这{0}个站点吗?"),nCount);
}
if (confirm(sCon)){
ProcessBar.start(wcm.LANG.SITERECYCLE_25 || '删除站点..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', {ObjectIds: sIds, drop: true}, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.SiteRecycleMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(!context.gridInfo) return true;
return context.gridInfo.RecordNum > 0;
};
reg({
key : 'restore',
type : 'siterecycle',
desc : wcm.LANG.SITERECYCLE_1 || '还原这个站点',
rightIndex : 2,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'siterecycle',
desc : wcm.LANG.SITERECYCLE_3 || '删除这个站点',
rightIndex : 2,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'siterecycleHost',
desc : wcm.LANG.SITERECYCLE_4 || '清空回收站',
rightIndex : -1,
order : 3,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'siterecycleHost',
desc : wcm.LANG.SITERECYCLE_6 || '还原所有站点',
rightIndex : -1,
order : 4,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'restore',
type : 'siterecycles',
desc : wcm.LANG.SITERECYCLE_8 || '还原这些站点',
rightIndex : 2,
order : 5,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'siterecycles',
desc : wcm.LANG.SITERECYCLE_10 || '删除这些站点',
title: '删除这些站点...',
rightIndex : 2,
order : 6,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.SiteRecycles', 'wcm.SiteRecycle');
WCMConstants.OBJ_TYPE_SITERECYCLE = 'SiteRecycle';
wcm.SiteRecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_SITERECYCLE;
wcm.SiteRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.SiteRecycles, wcm.CMSObjs, {
});
wcm.SiteRecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_SITERECYCLE;
wcm.SiteRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_SITERECYCLE, 'wcm.SiteRecycle');
Ext.extend(wcm.SiteRecycle, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['SITERECYCLE'] || '站点回收站';
wcm.PageOper.registerPanels({
siterecycleHost : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
websiteInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
websiteInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
siterecycle : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_website&methodname=recycleFindById'
},
siterecycles : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

