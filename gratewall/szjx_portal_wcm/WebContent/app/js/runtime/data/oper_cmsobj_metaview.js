Ext.apply(wcm.LANG,{
METAVIEW_PROCESS_1:'新建一个视图',
METAVIEW_PROCESS_2:'修改这个视图',
METAVIEW_PROCESS_3:'删除这个视图',
METAVIEW_PROCESS_4:'删除这些视图',
METAVIEW_PROCESS_5:'生成应用',
METAVIEW_UNITNAME:'个',
METAVIEW_TYPENAME:'视图',
METAVIEW_DEFAULTORDER:'默认排序',
METAVIEW_VIEWDESC:'视图名称',
METAVIEW_VIEWID:'视图ID',
METAVIEW_CRUSER:'创建者',
METAVIEW_CRTIME:'创建时间',
METAVIEW_VIEWDESC:'名称',
METAVIEW_MAINTABLENAME:'英文名称',
METAVIEW_BUTTON_1:'下一步',
METAVIEW_BUTTON_2:'新建',
METAVIEW_BUTTON_3:'修改',
METAVIEW_BUTTON_4:'删除',
METAVIEW_BUTTON_5:'删除这个/这些视图',
METAVIEW_BUTTON_6:'刷新',
METAVIEW_BUTTON_7:'刷新列表',
METAVIEW_BUTTON_8:'视图列表管理',
METAVIEW_BUTTON_9:'导入',
METAVIEW_BUTTON_10:'导入视图',
METAVIEW_BUTTON_11:'导出',
METAVIEW_BUTTON_12:'导出这个/些视图',
METAVIEW_WINDOW_TITLE_1:'新建/修改视图步骤1:新建或选择表',
METAVIEW_WINDOW_TITLE_2:'新建/修改视图步骤2:新建或选择物理字段',
METAVIEW_WINDOW_TITLE_3:'修改视图',
METAVIEW_WINDOW_TITLE_4:'选择视图',
METAVIEW_ALERT_1:'确实要将这',
METAVIEW_ALERT_2:'个视图删除吗? ',
METAVIEW_ALERT_3:'视图删除结果',
METAVIEW_ALERT_4:'没有指定要校验的容器',
METAVIEW_ALERT_5:'为系统保留字！',
METAVIEW_ALERT_6:'中文名称中含有特殊字符',
METAVIEW_PROCESSBAR_TIP_1 : '生成应用',
METAVIEW_GENAPP_RPT : '生成应用结果',
METAVIEW_PROCESSBAR_TIP_2 : '进度执行中',
METAVIEW_FILEUP_CONFIRM : '确定',
METAVIEW_1 : '文件路径为空，上传失败！',
METAVIEW_ALERT_7:'名称中含有特殊字符'
});

Ext.ns('wcm.domain.MetaViewMgr','wcm.domain.MetaViewService');
(function(){
var m_oMgr = wcm.domain.MetaViewMgr;
var m_sServiceId = "wcm61_metaview";
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
var addEditStepOne = function(_id, event){
var oParams = {objectId : _id};
var channelId = event.getContext().params['CHANNELID'];
if(channelId){
oParams['channelId'] = channelId;
}
var url = 'metaview/viewinfo_add_edit_step1.jsp?' + $toQueryStr(oParams);
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : wcm.LANG.METAVIEW_WINDOW_TITLE_1 || '新建/修改视图步骤1:新建或选择表',
callback : function(_tableInfoId, _viewId){
CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();
var _params = {tableInfoId : _tableInfoId , viewId : _viewId};
wcm.domain.MetaViewService.editMultiTable(_viewId);
}
});
};
Ext.apply(wcm.domain.MetaViewService, {
generate : function(objIds){
ProcessBar.start(wcm.LANG.METAVIEW_PROCESSBAR_TIP_1 || "生成应用");
getHelper().call(m_sServiceId, 'createViewRelation', {viewIds : objIds}, true, function(transport, json){
ProcessBar.exit();
var isSuccess = com.trs.util.JSON.value(json, "REPORTS.IS_SUCCESS");
if(isSuccess == true || isSuccess == 'true'){
}
});
},
addEditStepTwo : function(params,fCallBack){
var url = './metadbfield/metadbfield_list_select.html?' + $toQueryStr(params);
wcm.CrashBoarder.get('crash-board').show({
id : 'crash-board',
title : wcm.LANG.METAVIEW_WINDOW_TITLE_2 || '新建/修改视图步骤2:新建或选择物理字段',
src : url,
width:'800px',
height:'400px',
maskable:true,
callback : function (args) {
(fCallBack || Ext.emptyFn)(args)
}
});
},
editMultiTable : function(_sId){
wcm.CrashBoarder.get('editMultiTabler').show({
id : 'editMultiTabler',
title : wcm.LANG.METAVIEW_WINDOW_TITLE_3 || "修改视图",
src : "metadbtable/build_to_view.html",
width : "800px",
height : "500px",
maskable:true,
params : {viewId: _sId}
});
}
});
Ext.apply(wcm.domain.MetaViewMgr, {
'delete' : function(event){
var _arrIds = event.getObjs().getIds();
if(!confirm(String.format("确实要将这{0}个视图删除吗?",_arrIds.length ))){
return;
}
getHelper().call(m_sServiceId, "deleteView", {objectids:_arrIds + ""}, true, function(transport, json){
var isSuccess = $v(json, "REPORTS.IS_SUCCESS");
if(isSuccess == 'true'){
event.getObjs().afterdelete();
return;
}
wcm.ReportDialog.show(json, wcm.LANG.METAVIEW_ALERT_3 || '视图删除结果', function(){
event.getObjs().afterdelete();
});
});
},
edit : function(event){
var isSingleTable = event.getObj().getPropertyAsString("isSingleTable") || true;
if(isSingleTable =="true"||isSingleTable ==true )
isSingleTable = true;
else{
isSingleTable = false;
}
var _sId = event.getObj().getId();
if(isSingleTable){
addEditStepOne(_sId, event);
}else{
wcm.domain.MetaViewService.editMultiTable(_sId);
}
},
add : function(event){
addEditStepOne(0, event);
},
'export' : function(event){
if(event.length()==0){
Ext.Msg.$alert(wcm.LANG.metaview_1001 || '必须选中至少一个视图!');
return;
}
var oPostData = {
ViewIds: event.getIds().join()
};
BasicDataHelper.call(m_sServiceId, 'exportViews', oPostData, true, function(_trans, _json){
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
},
'import' : function(event){
var context = event.getContext();
var params = {
OwnerId: context.OwnerId,
OwnerType: context.OwnerType
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'metaview/metaview_import.html?' + $toQueryStr(params), 
wcm.LANG.metaview_1002 || '视图导入', CMSObj.afteradd(event));
},
'setsynrule' : function(event){
var nViewId = event.getObj().getId();
FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/syn_rule_set.html?synRuleSetFrom=metaView&viewId=' + nViewId, (wcm.LANG.METAVIEWDATA_34 || '设置数据同步到WCMDocument的规则'), CMSObj.afteredit(event));
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MetaViewMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'add',
type : 'MetaViewInRoot',
desc : '新建一个视图',
title : '新建一个视图...',
rightIndex : -1,
order : 1,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'MetaViewInRoot',
desc : '导入视图',
title : '导入视图...',
rightIndex : -1,
order : 1,
fn : pageObjMgr['import'],
quickKey : 'I'
});
reg({
key : 'edit',
type : 'MetaView',
desc : '修改这个视图',
title : '修改这个视图...',
rightIndex : -1,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'E'
});
reg({
key : 'delete',
type : 'MetaView',
desc : '删除这个视图',
title : '删除这个视图...',
rightIndex : -1,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'export',
type : 'MetaView',
desc : '导出这个视图',
title : '将当前视图以xml文件导出',
rightIndex : -1,
order : 3,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'setsynrule',
type : 'MetaView',
desc : '设置同步规则',
title : '设置同步规则...',
rightIndex : -1,
order : 4,
fn : pageObjMgr['setsynrule']
});
reg({
key : 'delete',
type : 'MetaViews',
desc : '删除这些视图',
title : '删除这些视图...',
rightIndex : -1,
order : 1,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'export',
type : 'MetaViews',
desc : '导出这些视图',
title : '将这些视图以xml文件导出',
rightIndex : -1,
order : 2,
fn : pageObjMgr['export'],
quickKey : 'X'
});
})();

Ext.ns('wcm.MetaViews', 'wcm.MetaView');
WCMConstants.OBJ_TYPE_METAVIEW = 'MetaView';
wcm.MetaViews = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METAVIEW;
wcm.MetaViews.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaViews, wcm.CMSObjs, {
});
wcm.MetaView = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METAVIEW;
wcm.MetaView.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METAVIEW, 'wcm.MetaView');
Ext.extend(wcm.MetaView, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['METAVIEW'] || '视图';
wcm.PageOper.registerPanels({
metaviewInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
metaviewInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
metaviewInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
metaview : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_metaview&methodname=jFindbyid'
},
metaviews : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();
ValidationHelper.bindValidations([
{
renderTo : 'ViewDesc',
type : 'string',
required : '',
max_len : '60',
desc : wcm.LANG.METAVIEW_VIEWDESC || '名称',
methods : function(){
var oViewDesc = this.field;
if(oViewDesc){
var msg = null;
if(/[<>;&]/.test(oViewDesc.value)){
this.warning = "<font color='red'>" 
+ (wcm.LANG.METAVIEW_ALERT_7 || "名称中含有特殊字符")
+ "<b>;&&lt;&gt;</b></font>.";
return false;
}
return true;
}
}
}
]);

Ext.ns('wcm.domain.MetaViewMgr','wcm.domain.MetaViewService');
(function(){
var m_oMgr = wcm.domain.MetaViewMgr;
var m_sServiceId = "wcm61_metaview";
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function assignMetaViewCallBack(_nMetaViewId){
return function (sSelIds){
if(sSelIds.length == 0) {
Ext.Msg.$fail('请选择要设置的栏目！');
return false;
}
var params = {ObjectIds:sSelIds.join(','),ViewId:_nMetaViewId};
getHelper().call(m_sServiceId,'setChannelEmployersOfMetaView',params,true,function(_transport,_json){
Ext.Msg.$timeAlert('设置成功！', 3);
FloatPanel.close();
});
return false;
}
}
Ext.apply(m_oMgr, {
assign : function(event){
var params = {
IsRadio : 0,
ExcludeTop : 1,
ExcludeLink : 1,
ShowOneType : 1,
MultiSites : 0,
RightIndex : -1,
ExcludeInfoview : 0,
ExcludeOnlySearch : 1
}
var _nMetaViewId = event.getObj().getId();
getHelper().call(m_sServiceId,'getChannelsUseingMetaView',{ObjectId:_nMetaViewId,OnlyReturnIds:true,IdsValueType:0},false,function(_transport,_json){
var ids = _transport.responseText||'';
if(ids.trim().length > 0){
Object.extend(params,{SELECTEDCHANNELIDS : ids});
}
});
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'include/channel_select.html',
title : '分配视图到栏目',
callback : assignMetaViewCallBack(_nMetaViewId),
dialogArguments : params
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MetaViewMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'assign',
type : 'MetaView',
desc : '分配这个视图到栏目',
title:'分配这个视图到栏目',
rightIndex : -1,
order : 5,
fn : pageObjMgr['assign']
});
})();

