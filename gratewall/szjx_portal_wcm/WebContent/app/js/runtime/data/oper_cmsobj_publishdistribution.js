Ext.apply(wcm.LANG, {
SERVICE_ALL :'全部服务',
SERVICE_USE :'已启用', 
SERVICE_STOP :'未启用',
PUBLISHDISTRUBUTION_CONFIRM_1 : '存放路径不能为空',
PUBLISHDISTRUBUTION_CONFIRM_2 : '服务器地址不能为空！',
PUBLISHDISTRUBUTION_CONFIRM_3 : '服务器端口不能为空！',
PUBLISHDISTRUBUTION_CONFIRM_4 : '存放目录不能为空',
PUBLISHDISTRUBUTION_CONFIRM_5 : '用户名不能为空',
PUBLISHDISTRUBUTION_CONFIRM_6 : '密码不能为空',
PUBLISHDISTRUBUTION_CONFIRM_7 : '服务器端口不是数字！',
PUBLISHDISTRUBUTION_CONFIRM_8 : '与服务器交互时出现错误！',
PUBLISHDISTRUBUTION_CONFIRM_9 : '存放目录:',
PUBLISHDISTRUBUTION_CONFIRM_10 : '存放路径:',
PUBLISHDISTRUBUTION_SURE : '确定',
PUBLISHDISTRUBUTION_UNIT : '个',
PUBLISHDISTRUBUTION_DISTRIBUTE : '站点分发',
PUBLISHDISTRUBUTION_VALID_1 : '新建站点分发',
PUBLISHDISTRUBUTION_VALID_2 : '选择对象',
PUBLISHDISTRUBUTION_VALID_3 : '修改站点分发',
PUBLISHDISTRUBUTION_VALID_4 : '确实要将这',
PUBLISHDISTRUBUTION_VALID_5 : '个分发点删除吗? ',
PUBLISHDISTRUBUTION_VALID_6 : '个分发点都启用吗? ',
PUBLISHDISTRUBUTION_VALID_7 : '启用进度',
PUBLISHDISTRUBUTION_VALID_8 : '正在启用...',
PUBLISHDISTRUBUTION_VALID_9 : '个分发点都禁用吗? ',
PUBLISHDISTRUBUTION_VALID_10 : '禁用进度',
PUBLISHDISTRUBUTION_VALID_11 : '正在禁用...',
PUBLISHDISTRUBUTION_VALID_12 : '确实要将所有分发点都禁用吗? ',
PUBLISHDISTRUBUTION_VALID_13 : '确实要将所有分发点都启用吗? ',
PUBLISHDISTRUBUTION_VALID_14 : '修改这个分发点',
PUBLISHDISTRUBUTION_VALID_15 : '禁用这个分发点',
PUBLISHDISTRUBUTION_VALID_16 : '启用这个分发点',
PUBLISHDISTRUBUTION_VALID_17 : '删除这个分发点',
PUBLISHDISTRUBUTION_VALID_18 : '新建分发点',
PUBLISHDISTRUBUTION_VALID_19 : '禁用所有分发点',
PUBLISHDISTRUBUTION_VALID_20 : '启用所有分发点',
PUBLISHDISTRUBUTION_VALID_21 : '禁用这些分发点',
PUBLISHDISTRUBUTION_VALID_22 : '启用这些分发点',
PUBLISHDISTRUBUTION_VALID_23 : '删除这些分发点',
PUBLISHDISTRUBUTION_VALID_24 : '新建',
PUBLISHDISTRUBUTION_VALID_25 : '请选择要删除的站点分发',
PUBLISHDISTRUBUTION_VALID_26 : '删除',
PUBLISHDISTRUBUTION_VALID_27 : '删除这个/些站点分发',
PUBLISHDISTRUBUTION_VALID_28 : '刷新',
PUBLISHDISTRUBUTION_VALID_29 : '刷新列表',
PUBLISHDISTRUBUTION_VALID_30 : '站点分发列表',
PUBLISHDISTRUBUTION_VALID_31 : '分发类型',
PUBLISHDISTRUBUTION_VALID_32 : '服务器地址',
PUBLISHDISTRUBUTION_VALID_33 : '分发路径',
PUBLISHDISTRUBUTION_VALID_34 : '启用',
PUBLISHDISTRUBUTION_VALID_35 : '启用这个/些分发点',
PUBLISHDISTRUBUTION_VALID_36 : '禁用',
PUBLISHDISTRUBUTION_VALID_37 : '禁用这个/些分发点',
PUBLISHDISTRUBUTION_VALID_38 : '启用所有',
PUBLISHDISTRUBUTION_VALID_39 : '禁用所有',
PUBLISHDISTRUBUTION_VALID_40 : '确定要删除这',
PUBLISHDISTRUBUTION_VALID_41 : '确定要启用这',
PUBLISHDISTRUBUTION_VALID_42 : '确定要禁用这',
PUBLISHDISTRUBUTION_VALID_43 : '个分发点?',
PUBLISHDISTRUBUTION_VALID_44 : '连接服务器'
});

Ext.ns('wcm.domain.PublishDistributionMgr');
(function(){
var m_oMgr = wcm.domain.PublishDistributionMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.PublishDistributionMgr, {
add : function(event){
var oPageParams = event.getContext();
Object.extend(oPageParams,{"ObjectId":0});
Object.extend(oPageParams,{"siteId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PUBLISHDISTRUBUTION_VALID_1 || '新建站点分发', CMSObj.afteradd(event)
);
},
edit : function(event){
var oPageParams = event.getContext();
var sId = event.getIds().join();
Object.extend(oPageParams,{"ObjectId":sId});
Object.extend(oPageParams,{"siteId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PUBLISHDISTRUBUTION_VALID_3 || '修改站点分发', CMSObj.afteredit(event)
);
},
"delete" : function(event){
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = String.format("确定要删除这{0}个分发点?",sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
BasicDataHelper.call("wcm6_distribution", 
'delete', //远端方法名 
Object.extend(PageContext.params, {"ObjectIds": sId}), //传入的参数
true, 
function(){
event.getObjs().afterdelete();
}
);
}
});
},
"enable" : function(event){
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var params = {};
var sResult = String.format("确定要启用这{0}个分发点?",sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
Object.extend(params, {"ObjectIds" :sId.join(),'Enable':true});
BasicDataHelper.call('wcm6_distribution', 
'updateStatus', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
},
"disable" : function(event){
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var params = {};
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = String.format('确定要禁用这{0}个分发点?',sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
Object.extend(params, {"ObjectIds" :sId.join(),'Enable':false});
BasicDataHelper.call('wcm6_distribution', 
'updateStatus', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
},
disableall : function(event){
var params = {"FolderType" : 103};
Ext.Msg.confirm(wcm.LANG.PUBLISHDISTRUBUTION_VALID_12 ||'确实要将所有分发点都禁用吗?',{
yes : function(){
Object.extend(params, {'FolderId':event.getHost().getId()});
Object.extend(params, {'Enable':false});
BasicDataHelper.call('wcm6_distribution', 
'updateStatusAll', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
},
enableall : function(event){
var params = {"FolderType" : 103};
Ext.Msg.confirm(wcm.LANG.PUBLISHDISTRUBUTION_VALID_13 ||'确实要将所有分发点都启用吗?',{
yes : function(){
Object.extend(params, {'FolderId':event.getHost().getId()});
Object.extend(params, {'Enable':true});
BasicDataHelper.call('wcm6_distribution', 
'updateStatusAll', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.PublishDistributionMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_14 ||'修改这个分发点',
title:'修改这个分发点',
rightIndex : 1,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'disable',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_15 ||'禁用这个分发点',
title:'禁用这个分发点',
rightIndex : 1,
order : 2,
fn : pageObjMgr['disable']
});
reg({
key : 'enable',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_16 ||'启用这个分发点',
title:'启用这个分发点',
rightIndex : 1,
order : 3,
fn : pageObjMgr['enable']
});
reg({
key : 'delete',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_17 ||'删除这个分发点',
title:'删除这个分发点',
rightIndex : 1,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'publishdistributionInSite',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_18 ||'新建分发点',
title:'新建分发点',
rightIndex : 1,
order : 5,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'disableall',
type : 'publishdistributionInSite',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_19 ||'禁用所有分发点',
title:'禁用所有分发点',
rightIndex : 1,
order : 6,
fn : pageObjMgr['disableall']
});
reg({
key : 'enableall',
type : 'publishdistributionInSite',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_20 ||'启用所有分发点',
title:'启用所有分发点',
rightIndex : 1,
order : 7,
fn : pageObjMgr['enableall']
});
reg({
key : 'disable',
type : 'publishdistributions',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_21 ||'禁用这些分发点',
title:'禁用这些分发点',
rightIndex : 1,
order : 8,
fn : pageObjMgr['disable']
});
reg({
key : 'enable',
type : 'publishdistributions',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_22 || '启用这些分发点',
title:'启用这些分发点',
rightIndex : 1,
order : 9,
fn : pageObjMgr['enable']
});
reg({
key : 'delete',
type : 'publishdistributions',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_17 ||'删除这些分发点',
title:'删除这些分发点',
rightIndex : 1,
order : 10,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.PublishDistributions', 'wcm.PublishDistribution');
WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION = 'PublishDistribution';
wcm.PublishDistributions = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION;
wcm.PublishDistributions.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.PublishDistributions, wcm.CMSObjs, {
});
wcm.PublishDistribution = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION;
wcm.PublishDistribution.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION, 'wcm.PublishDistribution');
Ext.extend(wcm.PublishDistribution, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['DISTRIBUTE'] || '分发';
wcm.PageOper.registerPanels({
publishdistributionInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
publishdistributionInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
publishdistributionInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
publishdistribution : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_publishdistribution&methodname=jFindbyid'
},
publishdistributions : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

