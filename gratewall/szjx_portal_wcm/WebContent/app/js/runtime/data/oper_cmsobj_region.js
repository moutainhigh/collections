Ext.ns('wcm.domain.RegionMgr');
(function(){
var m_oRegionMgr = wcm.domain.RegionMgr;
var serviceId = 'wcm6_regioninfo';
function addOrEdit(event, _addOrEdit){
var websiteId = 0;
var channelId = 0;
var objectId = 0;
var host = event.getHost();
var hostId = host.getId();
if(host.getIntType() == '103')
websiteId = hostId;
else if(host.getIntType() == '101')
channelId = hostId;
if(_addOrEdit == 0)
objectId = event.getIds()[0];
FloatPanel.open(WCMConstants.WCM6_PATH + 'region/region.html?channelId=' + channelId + '&websiteId='+ websiteId + '&ObjectId=' + objectId, 
wcm.LANG.CHANNEL_81 ||'导读管理', function(_arg){
FloatPanel.close();
if(objectId > 0)
CMSObj.afteredit(event)(_arg);
else 
CMSObj.afteradd(event)(_arg);
}
);
}
Ext.apply(wcm.domain.RegionMgr, {
add : function(event){
addOrEdit(event, 1);
},
edit : function(event){
addOrEdit(event, 0);
},
editDetail : function(event){
var websiteId = 0;
var channelId = 0;
var objectId = event.getIds()[0];
var host = event.getHost();
var hostId = host.getId();
if(host.getIntType() == '103')
websiteId = hostId;
else if(host.getIntType() == '101')
channelId = hostId;
wcm.CrashBoarder.get('Trs_Region_Set_Deatail').show({
maskable:true,
reloadable : true,
title : wcm.LANG.region_2011 || '导读详细设置页面',
src : './region/cell.html',
width:'850px',
height:'400px',
params : {
channelId : channelId || 0, 
websiteId : websiteId || 0,
objectId : objectId || 0
},
callback : function(_arg){
CMSObj.afteredit(event)(_arg);
this.close();
}
});
},
showEmploy : function(event){
var objectId = event.getIds()[0];
wcm.CrashBoarder.get('Trs_Region_Show_Employ').show({
maskable:true,
reloadable : true,
title : wcm.LANG.region_2012 || '导读使用信息',
src : './region/region_employ_show.jsp',
width:'280px',
height:'300px',
params : {
objectId : objectId || 0
}
});
},
'delete' : function(event){
var _arIds = event.getIds();
if(_arIds.length == 0) return;
var nCount = _arIds.length;
if(typeof(_arIds) == 'string') {
nCount = _arIds.split(',').length;
}
var params = {
ObjectIds: _arIds, 
drop: true
};
if (confirm(String.format(wcm.LANG.region_2013 || '确实要将这{0}个导读删除吗?',nCount))){
BasicDataHelper.call(serviceId, 'delete', params, false, function(){
CMSObj.afterdelete(event)();
});
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.RegionMgr;
var reg = wcm.SysOpers.register;
if(!getParameter('ChannelId')){
reg({
key : 'add',
type : 'regionInSite',
isHost : true,
desc : wcm.LANG.region_2014 || '新建导读',
title: '新建导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'edit',
type : 'region',
desc : '修改导读',
title: '修改导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'editDetail',
type : 'region',
desc : '修改导读详细信息',
title: '修改导读详细信息...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['editDetail']
});
reg({
key : 'delete',
type : 'regions',
desc : wcm.LANG.region_2017 || '删除这些导读',
title: '删除这些导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'delete',
type : 'region',
desc : wcm.LANG.region_2018 || '删除这个导读',
title: '删除这个导读...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
}else{
reg({
key : 'add',
type : 'regionInChannel',
isHost : true,
desc : wcm.LANG.region_2014 || '新建导读',
title: '新建导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'edit',
type : 'region',
desc : wcm.LANG.region_2015 || '修改导读',
title: '修改导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'editDetail',
type : 'region',
desc : wcm.LANG.region_2016 || '修改导读详细信息',
title: '修改导读详细信息...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['editDetail']
});
reg({
key : 'delete',
type : 'regions',
desc : wcm.LANG.region_2017 || '删除这些导读',
title: '删除这些导读...',
rightIndex : 13,
order : 1,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'delete',
type : 'region',
desc : wcm.LANG.region_2018 || '删除这个导读',
title: '删除这个导读...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
}
})();

Ext.ns('wcm.Regions', 'wcm.Region');
WCMConstants.OBJ_TYPE_REGION = 'region';
wcm.Flows = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_FLOW;
wcm.Regions.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Regions, wcm.CMSObjs, {
});
wcm.Region = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_REGION;
wcm.Region.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_REGION, 'wcm.Region');
Ext.extend(wcm.Region, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG.index_4000 || '导读';
wcm.PageOper.registerPanels({
regionInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
regionInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
region : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm6_regioninfo&methodname=jFindbyid'
},
regions : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

