Ext.apply(wcm.LANG,{
METARECDATA_UNITNAME:'个',
METARECDATA_TYPENAME:'记录',
METARECDATA_ADVANCESEARCH : '高级检索对象',
METARECDATA_VALID_1 : '参数不合法',
METARECDATA_BUTTON_1 : '确定',
METARECDATA_BUTTON_2 : '导入',
METARECDATA_ALERT_1 : '请选择要当前记录要移动到的目标分类!',
METARECDATA_ALERT_2 : '执行进度',
METARECDATA_ALERT_3 : '提交数据',
METARECDATA_ALERT_4 : '成功执行完成',
METARECDATA_ALERT_5 : '上传文件',
METARECDATA_ALERT_6 : '记录导入结果',
METARECDATA_ALERT_7 : '尚未选择由TRS数据库导出的XML文件.',
METARECDATA_ALERT_8 : '未选择其他XML文件.',
METARECDATA_ALERT_9 : '管理TRS映射关系',
METARECDATA_ALERT_10 : '没有指定视图ID[VIEWID]',
METARECDATA_ALERT_11 : '不符合规则！',
METARECDATA_OPERS_EDIT_DESC : '修改这条记录',
METARECDATA_OPERS_EDIT_TITLE : '修改这条记录',
METARECDATA_OPERS_PREVIEW_DESC : '预览这条记录',
METARECDATA_OPERS_PREVIEW_DESCS : '预览这些记录',
METARECDATA_OPERS_PREVIEW_TITLE : '预览这条记录发布效果',
METARECDATA_OPERS_PREVIEW_TITLES : '预览这些记录发布效果',
METARECDATA_OPERS_BASICPUBLISH_DESC : '发布这条记录',
METARECDATA_ALERT_12 : '发布这些记录',
METARECDATA_OPERS_BASICPUBLISH_TITLE : '发布这条记录，生成这条记录的细览页面以及更新相关概览页面',
METARECDATA_OPERS_BASICPUBLISH_TITLES : '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
METARECDATA_OPERS_DETAILPUBLISH_DESC : '仅发布这条记录细览',
METARECDATA_OPERS_DETAILPUBLISH_DESCS : '仅发布这些记录细览',
METARECDATA_OPERS_DETAILPUBLISH_TITLE : '仅发布这条记录细览，仅重新生成这条记录的细览页面',
METARECDATA_OPERS_DETAILPUBLISH_TITLES : '仅发布这些记录细览，仅重新生成这些记录的细览页面',
METARECDATA_OPERS_RECALLPUBLISH_DESC : '撤销发布这条记录',
METARECDATA_OPERS_RECALLPUBLISH_DESCS : '撤销发布这些记录',
METARECDATA_OPERS_RECALLPUBLISH_TITLE : '撤销发布这条记录，撤回已发布目录或页面',
METARECDATA_OPERS_RECALLPUBLISH_TITLES : '撤销发布这些记录，撤回已发布目录或页面',
METARECDATA_OPERS_EXPORT_DESC : '导出这条记录',
METARECDATA_OPERS_EXPORT_DESCS : '导出这些记录',
METARECDATA_OPERS_EXPORT_TITLE : '将这条记录导出成zip文件',
METARECDATA_OPERS_EXPORT_TITLES : '将这些记录导出成zip文件',
METARECDATA_OPERS_SEPERATE_DESC : '分隔线',
METARECDATA_OPERS_SEPERATE_TITLE : '分隔线',
METARECDATA_OPERS_CHANGESTATUS_DESC : '改变这条记录的状态',
METARECDATA_OPERS_CHANGESTATUS_DESCS : '改变这些记录的状态',
METARECDATA_OPERS_DELETE_DESC : '将记录放入废稿箱',
METARECDATA_OPERS_DELETE_TITLE : '将记录放入废稿箱',
METARECDATA_OPERS_MOVE_DESC : '移动这条记录到...',
METARECDATA_OPERS_MOVE_DESCS : '移动这些记录到...',
METARECDATA_OPERS_ADD_DESC : '新建一条记录',
METARECDATA_OPERS_ADD_TITLE : '新建一条记录',
METARECDATA_OPERS_IMPORT_DESC : '从外部导入记录',
METARECDATA_OPERS_IMPORT_TITLE : '从外部导入记录',
METARECDATA_OPERS_CREATEFROMEXCEL_DESC : '从Excel创建记录',
METARECDATA_OPERS_CREATEFROMEXCEL_TITLE : '从Excel创建记录',
METARECDATA_FLOAT_TITLE_1 : '导入记录',
METARECDATA_FLOAT_TITLE_2 : '移动记录',
METARECDATA_OPERS_RECALLPUBLISH_CONFIRM : "确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选记录么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
METARECDATA_ALERT_13 : '直接发布这条记录',
METARECDATA_ALERT_14 : '发布这条记录细览，同时发布此记录的所有引用记录',
METARECDATA_ALERT_15 : '直接撤销发布这条记录',
METARECDATA_ALERT_16 : '撤回当前记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
METARECDATA_ALERT_17 : '直接发布这些记录',
METARECDATA_ALERT_18 : '发布这些记录细览，同时发布这些记录的所有引用记录',
METARECDATA_ALERT_19 : '直接撤销发布这些记录',
METARECDATA_ALERT_20 : '撤回这些记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
METARECDATA_ALERT_21 : "确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选记录及其所有引用记录么？将<font color='red' style='font-size:14px;'>不可逆转</font>！"
});

Ext.ns('wcm.domain.MetaRecDataMgr');
(function(){
var m_oMgr = wcm.domain.MetaRecDataMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function _addEdit(_id,event){
var nObjId = _id || 0;
var sTitle = (nObjId == 0)?(wcm.LANG.METAVIEWDATA_77 || "新建"):(wcm.LANG.METAVIEWDATA_106 || "修改");
sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
var contextParams = event.getContext().params;
var nViewId = event.getObj().getProperty('dockind') || contextParams.VIEWID;
var oParams = {
ObjectId:nObjId, 
FlowDocId:contextParams.FLOWDOCID || 0,
ViewId:nViewId || 0,
ClassInfoId : event.getHost().getId()
};
$openMaxWin(WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
}
Ext.apply(wcm.domain.MetaRecDataMgr, {
add : function(event){
_addEdit(0,event);
},
edit : function(event){
var obj = event.getObj();
_addEdit(obj.getProperty("docid")||obj.getId(),event);
},
"import" : function(event){
var _nViewId = event.getContext().params["VIEWID"];
var _nDocId = event.getHost().getId();
try{
if(!_nViewId){
Ext.Msg.alert(wcm.LANG.METARECDATA_ALERT_10 || "没有指定视图ID[VIEWID]");
return;
}
}catch(error){
Ext.Msg.alert("ViewTemplateMgr.import:" + error.message);
}
var oParams = {
DocumentId : _nDocId,
ViewId : _nViewId,
ChannelId : 0,
SiteId : 0
}
FloatPanel.open( WCMConstants.WCM6_PATH + 'metarecdata/view_data_import_cls.jsp?' + $toQueryStr(oParams), wcm.LANG.METARECDATA_FLOAT_TITLE_1 || '导入记录', CMSObj.afteredit(event));
},
createfromexcel : function(event){
wcm.domain.MetaViewDataMgr.createfromexcel(event);
},
preview : function(event){
wcm.domain.MetaViewDataMgr.preview(event);
},
basicpublish : function(event){
wcm.domain.MetaViewDataMgr.basicpublish(event);
},
directpublish : function(event){
wcm.domain.MetaViewDataMgr.directpublish(event);
},
detailpublish : function(event){
wcm.domain.MetaViewDataMgr.detailpublish(event);
},
directRecallpublish : function(event){
wcm.domain.MetaViewDataMgr.directRecallpublish(event);
},
recallpublish : function(event){
wcm.domain.MetaViewDataMgr.recallpublish(event);
},
"export" : function(event){
wcm.domain.MetaViewDataMgr['export'](event);
},
"delete" : function(event){
wcm.domain.MetaViewDataMgr['delete'](event);
},
changestatus : function(event){
wcm.domain.MetaViewDataMgr.changestatus(event);
},
move : function(event){
var _nclassinfoId = event.getHost().getId();
var _sDocIds = event.getObjs().getPropertys("docid").join(',');
var oPostData = {
'classinfoId' : _nclassinfoId,
'docIds' : _sDocIds
}
FloatPanel.open( WCMConstants.WCM6_PATH + 'metarecdata/record_moveto_cls.jsp?' + $toQueryStr(oPostData), wcm.LANG.METARECDATA_FLOAT_TITLE_2 || '移动记录', CMSObj.afteredit(event));
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MetaRecDataMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_EDIT_DESC || '修改这条记录',
title : wcm.LANG.METARECDATA_OPERS_EDIT_TITLE || '修改这条记录',
rightIndex : 32,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'E'
});
reg({
key : 'preview',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_PREVIEW_DESC || '预览这条记录',
title : wcm.LANG.METARECDATA_OPERS_PREVIEW_TITLE || '预览这条记录发布效果',
rightIndex : 38,
order : 2,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'basicpublish',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_BASICPUBLISH_DESC || '发布这条记录',
title : wcm.LANG.METARECDATA_OPERS_BASICPUBLISH_TITLE || '发布这条记录，生成这条记录的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 3,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'detailpublish',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_DESC || '仅发布这条记录细览',
title : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_TITLE || '仅发布这条记录细览，仅重新生成这条记录的细览页面',
rightIndex : 39,
order : 4,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_ALERT_13 || '直接发布这条记录',
title : wcm.LANG.METARECDATA_ALERT_14 || '发布这条记录细览，同时发布此记录的所有引用记录',
rightIndex : 39,
order : 4,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_DESC || '撤销发布这条记录',
title : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_TITLE || '撤销发布这条记录，撤回已发布目录或页面',
rightIndex : 39,
order : 5,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_ALERT_15 || '直接撤销发布这条记录',
title : wcm.LANG.METARECDATA_ALERT_16 || '撤回当前记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
rightIndex : 39,
order : 5,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'export',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_EXPORT_DESC || '导出这条记录',
title : wcm.LANG.METARECDATA_OPERS_EXPORT_TITLE || '将这条记录导出成zip文件',
rightIndex : 34,
order : 6,
fn : pageObjMgr['export']
});
reg({
key : 'seperate',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_SEPERATE_DESC || '分隔线',
title : wcm.LANG.METARECDATA_OPERS_SEPERATE_TITLE || '分隔线',
rightIndex : -1,
order : 7,
fn : pageObjMgr['seperate']
});
reg({
key : 'changestatus',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_DESC || '改变这条记录的状态',
title : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_TITLE || '改变这条记录的状态',
rightIndex : 35,
order : 8,
fn : pageObjMgr['changestatus']
});
reg({
key : 'delete',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_DELETE_DESC || '将记录放入废稿箱',
title : wcm.LANG.METARECDATA_OPERS_DELETE_TITLE || '将记录放入废稿箱',
rightIndex : 33,
order : 9,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'move',
type : 'metarecdata',
desc : wcm.LANG.METARECDATA_OPERS_MOVE_DESC || '移动这条记录到...',
title : wcm.LANG.METARECDATA_OPERS_MOVE_DESC || '移动这条记录到...',
rightIndex : 32,
order : 10,
fn : pageObjMgr['move']
});
reg({
key : 'add',
type : 'metarecdataInClassinfo',
desc : '新建一条记录',
title : '新建一条记录...',
rightIndex : -1,
order : 11,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'metarecdataInClassinfo',
desc : wcm.LANG.METARECDATA_OPERS_IMPORT_DESC || '从外部导入记录',
title : wcm.LANG.METARECDATA_OPERS_IMPORT_TITLE || '从外部导入记录...',
rightIndex : -1,
order : 12,
fn : pageObjMgr['import']
});
reg({
key : 'createfromexcel',
type : 'metarecdataInClassinfo',
desc : wcm.LANG.METARECDATA_OPERS_CREATEFROMEXCEL_DESC || '从Excel创建记录',
title : wcm.LANG.METARECDATA_OPERS_CREATEFROMEXCEL_TITLE || '从Excel创建记录...',
rightIndex : -1,
order : 13,
fn : pageObjMgr['createfromexcel']
});
reg({
key : 'preview',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_PREVIEW_DESCS || '预览这些记录',
title : wcm.LANG.METARECDATA_OPERS_PREVIEW_TITLES || '预览这些记录发布效果',
rightIndex : 38,
order : 14,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'basicpublish',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_ALERT_12 || '发布这些记录',
title : wcm.LANG.METARECDATA_OPERS_BASICPUBLISH_TITLES || '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 15,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'detailpublish',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_DESCS || '仅发布这些记录细览',
title : wcm.LANG.METARECDATA_OPERS_DETAILPUBLISH_TITLES || '仅发布这些记录细览，仅重新生成这些记录的细览页面',
rightIndex : 39,
order : 16,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_ALERT_17 || '直接发布这些记录',
title : wcm.LANG.METARECDATA_ALERT_18 || '发布这些记录细览，同时发布这些记录的所有引用记录',
rightIndex : 39,
order : 16,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_DESCS || '撤销发布这些记录',
title : wcm.LANG.METARECDATA_OPERS_RECALLPUBLISH_TITLES || '撤销发布这些记录，撤回已发布目录或页面',
rightIndex : 39,
order : 17,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_ALERT_19 || '直接撤销发布这些记录',
title : wcm.LANG.METARECDATA_ALERT_20 || '撤回这些记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
rightIndex : 39,
order : 17,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'export',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_EXPORT_DESCS || '导出这些记录',
title : wcm.LANG.METARECDATA_OPERS_EXPORT_TITLES || '将这些记录导出成zip文件',
rightIndex : 34,
order : 18,
fn : pageObjMgr['export']
});
reg({
key : 'seperate',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_SEPERATE_DESC || '分隔线',
title : wcm.LANG.METARECDATA_OPERS_SEPERATE_TITLE || '分隔线',
rightIndex : -1,
order : 19,
fn : pageObjMgr['seperate']
});
reg({
key : 'changestatus',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_DESCS || '改变这些记录的状态',
title : wcm.LANG.METARECDATA_OPERS_CHANGESTATUS_DESCS || '改变这些记录的状态',
rightIndex : 35,
order : 20,
fn : pageObjMgr['changestatus']
});
reg({
key : 'delete',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_DELETE_DESC || '将记录放入废稿箱',
title : wcm.LANG.METARECDATA_OPERS_DELETE_TITLE || '将记录放入废稿箱',
rightIndex : 33,
order : 21,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'move',
type : 'metarecdatas',
desc : wcm.LANG.METARECDATA_OPERS_MOVE_DESCS || '移动这些记录到...',
title : wcm.LANG.METARECDATA_OPERS_MOVE_DESCS || '移动这些记录到...',
rightIndex : 32,
order : 22,
fn : pageObjMgr['move']
});
})();

Ext.ns('wcm.MetaRecDatas', 'wcm.MetaRecData');
WCMConstants.OBJ_TYPE_METARECDATA = 'MetaRecData';
wcm.MetaRecDatas = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METARECDATA;
wcm.MetaRecDatas.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaRecDatas, wcm.CMSObjs, {
});
wcm.MetaRecData = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METARECDATA;
wcm.MetaRecData.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METARECDATA, 'wcm.MetaRecData');
Ext.extend(wcm.MetaRecData, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['METARECDATA'] || '记录列表';
wcm.PageOper.registerPanels({
metarecdataInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
metarecdataInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
metarecdataInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
metarecdataInClassinfo : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCLASSINFO'] || '分类法{0}操作任务', sName),
displayNum : 5
},
metarecdata : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_metarecdata&methodname=findById'
},
metarecdatas : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

Ext.ns('wcm.domain.MetaRecDataMgr');
Ext.ns('wcm.domain.MetaViewDataMgr');
(function(){
var m_oMetaRecMgr = wcm.domain.MetaRecDataMgr;
var aGovInfoViewId = [];
function initGovInfoViewId(event,_callBack){
BasicDataHelper.JspRequest(WCMConstants.WCM6_PATH+'metarecdata/get_govinfo_viewid.jsp',{},false,function(transport){
aGovInfoViewId = transport.responseText.trim().split(",");
if(_callBack)_callBack(event,aGovInfoViewId);
});
}
function addGovInfoMeta(event,aGovInfoViewId){
var nObjId = 0;
var sTitle = wcm.LANG.METAVIEWDATA_77 || "新建";
sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
var contextParams = event.getContext().params;
var nViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
if(aGovInfoViewId.include(nViewId)){
var oParams = {
ObjectId:nObjId,
ClassInfoId : event.getHost().getId(),
FlowDocId:contextParams.FlowDocId || 0,
ViewId:nViewId
};
$openMaxWin(WCMConstants.WCM6_PATH + 'application/' + nViewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
}else{
m_oMetaRecMgr.add(event);
}
}
var addOper = wcm.SysOpers.getOperItem('metarecdataInClassinfo','add');
addOper.fn = function(event){
if(aGovInfoViewId.length==0){
initGovInfoViewId(event,addGovInfoMeta);
}else{
addGovInfoMeta(event,aGovInfoViewId);
}
};
var editOper = wcm.SysOpers.getOperItem('metarecdata','edit');
var oldEdit = wcm.domain.MetaRecDataMgr['edit'];
function editGovInfoMeta(event,aGovInfoViewId){
var obj = event.getObj();
var nObjId = obj.getProperty("docid")||obj.getId();
var sTitle = wcm.LANG.METAVIEWDATA_77 || "修改";
sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
var contextParams = event.getContext().params;
var nViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
if(aGovInfoViewId.include(nViewId)){
var oParams = {
ObjectId:nObjId,
ClassInfoId : event.getHost().getId(),
FlowDocId:contextParams.FlowDocId || 0,
ViewId:nViewId
};
$openMaxWin(WCMConstants.WCM6_PATH + 'application/' + nViewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
}else{
oldEdit(event);
}
}
wcm.domain.MetaRecDataMgr['edit'] = editOper.fn = function(event){
if(aGovInfoViewId.length==0){
initGovInfoViewId(event,editGovInfoMeta);
}else{
editGovInfoMeta(event,aGovInfoViewId);
}
};
var oldView = wcm.domain.MetaViewDataMgr['view'];
function viewGovInfoMeta(event,aGovInfoViewId){
var _objId = event.getObj().getPropertyAsInt("docid",0);
var nViewId = event.getContext().params["VIEWID"];
if(aGovInfoViewId.include(nViewId)){
var urlParams = "?objectId=" + _objId;
$openMaxWin(WCMConstants.WCM6_PATH + "./application/" + nViewId + "/viewdata_detail.jsp" + urlParams);
}else{
oldView(event);
}
}
Ext.apply(wcm.domain.MetaViewDataMgr, {
view : function(event){
if(aGovInfoViewId.length==0){
initGovInfoViewId(event,viewGovInfoMeta);
}else{
viewGovInfoMeta(event,aGovInfoViewId);
}
}
});
})();

