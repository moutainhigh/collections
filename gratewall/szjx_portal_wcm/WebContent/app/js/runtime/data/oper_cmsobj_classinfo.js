Ext.apply(wcm.LANG, {
CLASSINFO_1 : '下一步',
CLASSINFO_2 : '个',
CLASSINFO_3 : '分类法',
CLASSINFO_4 : '分类法名称',
CLASSINFO_5 : '分类法描述',
CLASSINFO_6 : '创建者',
CLASSINFO_7 : '分类法ID',
CLASSINFO_8 : '新建',
CLASSINFO_9 : '新建一个分类法',
CLASSINFO_10 : '维护',
CLASSINFO_11 : '维护这个分类法',
CLASSINFO_12 : '导入',
CLASSINFO_13 : '导入分类法',
CLASSINFO_14 : '删除',
CLASSINFO_15 : '删除这个/些分类法',
CLASSINFO_16 : '刷新',
CLASSINFO_17 : '刷新列表',
CLASSINFO_18 : '分类法列表',
CLASSINFO_19 : '不能为空！',
CLASSINFO_20 : '长度大于最大长度50！',
CLASSINFO_21 : '长度过长，最大长度为',
CLASSINFO_22 : '确定',
CLASSINFO_23 : '分类法导入结果',
CLASSINFO_24 : '取消',
CLASSINFO_25 : '分类法维护',
CLASSINFO_26 : '确实要将这',
CLASSINFO_27 : '个分类法删除吗? ',
CLASSINFO_28 : '选择分类法',
CLASSINFO_29 : '没有指定分类法ID信息',
CLASSINFO_30 : '选择分类法节点',
CLASSINFO_31 : '删除这个分类法',
CLASSINFO_32 : '删除这些分类法',
CLASSINFO_33 : '关闭',
CLASSINFO_34 : '导入分类法',
CLASSINFO_35 : '取消选择',
CLASSINFO_36 : '导出这个分类法',
CLASSINFO_37 : '导出这些分类法',
CLASSINFO_38 : '确定',
CLASSINFO_39 : '文件路径为空，上传失败！',
CLASSINFO_40 : '导出',
CLASSINFO_41 : '导出分类法'
});

WCMConstants.OBJ_TYPE_CLASSINFO = 'classinfo';;
Ext.ns('wcm.domain.classinfoMgr');
(function(){
var m_oMgr = wcm.domain.classinfoMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function config(_id,_name){
var params = {objectId : _id, objectName : _name};
var url = 'classinfo/classinfo_config.html?' + $toQueryStr(params);
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : wcm.LANG.CLASSINFO_25 || '分类法维护'
});
}
Ext.apply(wcm.domain.classinfoMgr, {
"delete" : function(event){
var objsOrHost = event.getObjsOrHost();
var aIds = objsOrHost.getIds();
var nCount = aIds.length;
var sHint = (nCount == 1)? '' : nCount;
Ext.Msg.confirm( String.format("确实要将这{0}个分类法删除吗?", sHint),{
yes : function(){
getHelper().call("wcm61_classinfo", 
"deleteClassInfo", //远端方法名 
{"ObjectIds": aIds.join(",")}, //传入的参数
true, 
function(){
event.getObjsOrHost()["afterdelete"]();
}
)}
});
}, 
add : function(event){
var url = 'classinfo/classinfo_add_edit.html';
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : wcm.LANG.CLASSINFO_9 || '新建一个分类法',
callback : function(objId, objName){
var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
CMSObj.createFrom(info, null)['afteradd']();
FloatPanel.close();
config.apply(window, arguments);
}
});
},
config : function(event){
var nObjectId = event.getObj().getId();
var sObjName = event.getObj().getPropertyAsString("objectName")
config(nObjectId,sObjName);
},
'import' : function(event){
var context = event.getContext();
var params = {
OwnerId: context.OwnerId,
OwnerType: context.OwnerType
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'classinfo/classinfo_import.jsp?' + $toQueryStr(params), 
wcm.LANG.CLASSINFO_13 || '导入分类法', CMSObj.afteradd(event));
},
'export' : function(event){
if(event.length()==0 && event.getObj().getId()==""){
Ext.Msg.$alert(wcm.LANG.classinfo_1001 || '必须选中至少一个分类法!');
return;
}
var oPostData = {
ClassInfoIds: event.getIds().join()||event.getObj().getId()
};
BasicDataHelper.call('wcm61_classinfo', 'exportClassInfos', oPostData, true, function(_trans, _json){
var sFileUrl = _trans.responseText;
var frm = document.getElementById("ifrmDownload");
if(frm == null) {
frm = document.createElement('iframe');
frm.style.height = 0;
frm.style.width = 0;
document.body.appendChild(frm);
}
sFileUrl = WCMConstants.WCM6_PATH + "file/read_file.jsp?DownName=" + sFileUrl + "&FileName=" + sFileUrl;
frm.src = sFileUrl;
}.bind(this));
}
});
})();
(function(){
var pageObjMgr = wcm.domain.classinfoMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'config',
type : 'ClassInfo',
desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
rightIndex : -2,
order : 1,
fn : pageObjMgr['config'],
quickKey : ['E']
});
reg({
key : 'delete',
type : 'ClassInfo',
desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
rightIndex : -2,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'export',
type : 'ClassInfo',
desc : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
title : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
rightIndex : -2,
order : 3,
fn : pageObjMgr['export']
});
reg({
key : 'config',
type : 'ClassInfoCls',
desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
rightIndex : -2,
order : 3,
fn : pageObjMgr['config'],
quickKey : ['E']
});
reg({
key : 'delete',
type : 'ClassInfoCls',
desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
rightIndex : -2,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'ClassInfoInRoot',
desc : '新建一个分类法',
title : '新建一个分类法...',
rightIndex : -1,
order : 5,
fn : pageObjMgr['add'],
quickKey : ['N']
});
reg({
key : 'import',
type : 'ClassInfoInRoot',
desc : '导入分类法',
title : '导入分类法...',
rightIndex : -1,
order : 6,
fn : pageObjMgr['import']
});
reg({
key : 'export',
type : 'ClassInfos',
desc : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
title : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
rightIndex : -2,
order : 3,
fn : pageObjMgr['export']
});
reg({
key : 'delete',
type : 'ClassInfos',
desc : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
title : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
rightIndex : -2,
order : 7,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.classinfos', 'wcm.classinfo');
WCMConstants.OBJ_TYPE_CLASSINFO = 'classinfo';
wcm.classinfos = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CLASSINFO;
wcm.classinfos.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.classinfos, wcm.CMSObjs, {
});
wcm.classinfo = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CLASSINFO;
wcm.classinfo.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CLASSINFO, 'wcm.classinfo');
Ext.extend(wcm.classinfo, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['CLASSINFO'] || '分类法';
wcm.PageOper.registerPanels({
classinfoInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
classinfo : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_classinfo&methodname=jFindbyid'
},
classinfos : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();
ValidationHelper.bindValidations([
{
renderTo : 'CName',
type :'string',
required :'',
max_len :'50',
desc :wcm.LANG.TEMPLATE_TEMPNAME||'名称',
methods : PageContext.validExistProperty({objType : 694710472})
},
{
renderTo : 'CDesc',
type:'string',
max_len:'100',
desc:wcm.LANG.TEMPLATE_TEMPDESC||'描述'
}
]);

