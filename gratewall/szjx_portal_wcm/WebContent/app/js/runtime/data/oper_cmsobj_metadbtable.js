Ext.apply(wcm.LANG, {
METADBTABLE_1 : '传入参数不正确[tableIds]',
METADBTABLE_2 : '视图名称不能为空。',
METADBTABLE_3 : '视图名称大于最大长度60。',
METADBTABLE_4 : '没有指定要校验的容器',
METADBTABLE_5 : '为系统保留字！',
METADBTABLE_6 : '个',
METADBTABLE_7 : '元数据',
METADBTABLE_8 : '中文名称',
METADBTABLE_9 : '英文名称',
METADBTABLE_10 : '元数据描述',
METADBTABLE_11 : '创建者',
METADBTABLE_12 : '元数据ID',
METADBTABLE_13 : '默认排序',
METADBTABLE_14 : '元数据别名',
METADBTABLE_15 : '创建时间',
METADBTABLE_16 : '创建者',
METADBTABLE_17 : '新建',
METADBTABLE_18 : '新建一个元数据',
METADBTABLE_19 : '修改',
METADBTABLE_20 : '修改这个元数据',
METADBTABLE_21 : '删除',
METADBTABLE_22 : '删除这个/些元数据',
METADBTABLE_23 : '生成视图',
METADBTABLE_24: '刷新',
METADBTABLE_25 : '刷新列表',
METADBTABLE_26 : '元数据列表',
METADBTABLE_27 : '确定',
METADBTABLE_28 : '取消',
METADBTABLE_29 : '新建/修改元数据',
METADBTABLE_30 : '系统提示信息',
METADBTABLE_31 : '删除这个元数据',
METADBTABLE_32 : '删除这些元数据',
METADBTABLE_33 : '保存',
METADBTABLE_34 : '别名',
METADBTABLE_35 : '描述',
METADBTABLE_36 : '名称',
METADBTABLE_37 : '关闭',
METADBTABLE_38 : '执行保存操作'
});

Ext.ns('wcm.domain.MetaDBTableMgr');
(function(){
var m_oMgr = wcm.domain.MetaDBTableMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function addEdit(_id,event){
var url = 'metadbtable/tableinfo_add_edit.jsp?objectId=' + _id;
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : wcm.LANG.METADBTABLE_29 || '新建/修改元数据',
callback : function(){
CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();
}
});
}
Ext.apply(wcm.domain.MetaDBTableMgr, {
serviceId : "wcm61_metadbtable", 
add : function(event){
addEdit(0,event);
},
edit : function(event){
addEdit(event.getObj().getId(),event);
},
build : function(event){
var sDialogId = "tableToViewer";
wcm.CrashBoarder.get(sDialogId).show({
id : sDialogId,
title : wcm.LANG.METADBTABLE_23 || "生成视图",
src : "metadbtable/build_to_view.html",
width : "800px",
height : "500px",
maskable:true,
params : {tableIds : event.getIds()}
});
},
'delete' : function(event){
var sId = 'table_info_dialog_delete';
wcm.CrashBoarder.get(sId).show({
id : sId,
maskable : true,
title : wcm.LANG.METADBTABLE_30 || '系统提示信息',
src : 'metadbtable/tableinfo_delete_info.jsp',
width:'500px',
height:'210px',
border:false,
params : {
objectids:event.getObjs().getIds()
},
callback : function(){
ProcessBar.init(wcm.LANG.METADBTABLE_21 || '删除');
ProcessBar.start();
BasicDataHelper.call("wcm61_metadbtable", "deleteDBTableInfo", {objectids:event.getObjs().getIds()}, true, function(trans,json){
ProcessBar.close();
var isSuccess = $v(json, "REPORTS.IS_SUCCESS");
if(isSuccess == 'true'){
event.getObjs().afterdelete();
return;
}
wcm.ReportDialog.show(json, '元数据删除结果', function(){
event.getObjs().afterdelete();
});
this.close();
});
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MetaDBTableMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'build',
type : 'MetaDBTable',
desc : '生成视图',
title : '生成视图...',
rightIndex : -1,
order : 1,
fn : pageObjMgr['build']
});
reg({
key : 'edit',
type : 'MetaDBTable',
desc : '修改这个元数据',
title : '修改这个元数据...',
rightIndex : -1,
order : 2,
fn : pageObjMgr['edit'],
quickKey : ['E']
});
reg({
key : 'delete',
type : 'MetaDBTable',
desc : '删除这个元数据',
title : '删除这个元数据...',
rightIndex : -1,
order : 3,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'MetaDBTableInRoot',
desc : '新建一个元数据',
title : '新建一个元数据...',
rightIndex : -1,
order : 4,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'delete',
type : 'MetaDBTables',
desc : '删除这些元数据',
title : '删除这些元数据...',
rightIndex : -1,
order : 5,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'build',
type : 'MetaDBTables',
desc : '生成视图',
title : '生成视图...',
rightIndex : -1,
order : 6,
fn : pageObjMgr['build']
});
})();

Ext.ns('wcm.MetaDBTables', 'wcm.MetaDBTable');
WCMConstants.OBJ_TYPE_METADBTABLE = 'MetaDBTable';
wcm.MetaDBTables = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METADBTABLE;
wcm.MetaDBTables.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaDBTables, wcm.CMSObjs, {
});
wcm.MetaDBTable = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METADBTABLE;
wcm.MetaDBTable.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METADBTABLE, 'wcm.MetaDBTable');
Ext.extend(wcm.MetaDBTable, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['METADBTABLE'] || '元数据';
wcm.PageOper.registerPanels({
metadbtableInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
metadbtableInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
metadbtableInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
metadbtable : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_metadbtable&methodname=jFindbyid'
},
metadbtables : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();
ValidationHelper.bindValidations([
{
renderTo : 'TableName',
type : 'common_char',
required : '',
max_len : '16',
desc : wcm.LANG.METADBTABLE_36 || '名称'
},
{
renderTo : 'AnotherName',
type : 'string',
required : '',
max_len : '60',
desc: wcm.LANG.METADBTABLE_34 || '别名'
},
{
renderTo : 'TableDesc',
type : 'string',
required : 'false',
max_len : '120',
desc : wcm.LANG.METADBTABLE_35 || '描述'
}
]);

