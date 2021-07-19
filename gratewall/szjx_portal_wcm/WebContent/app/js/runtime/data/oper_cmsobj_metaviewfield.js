Ext.apply(wcm.LANG,{
METAVIEWFIELD_PROCESS_1:'维护字段',
METAVIEWFIELD_PROCESS_2:'维护物理字段',
METAVIEWFIELD_PROCESS_3:'生成应用',
METAVIEWFIELD_PROCESS_4:'设为标题',
METAVIEWFIELD_PROCESS_5:'删除',
METAVIEWFIELD_PROCESS_6:'新建视图',
METAVIEWFIELD_PROCESS_7:'设置视图',
METAVIEWFIELD_PROCESS_8:'调整顺序',
METAVIEWFIELD_UNITNAME:'个',
METAVIEWFIELD_TYPENAME:'视图字段',
METAVIEWFIELD_ANOTHERNAME:'中文名称',
METAVIEWFIELD_FIELDNAME:'英文名称',
METAVIEWFIELD_CRUSER:'创建者',
METAVIEWFIELD_VIEWFIELDINFOID:'字段ID',
METAVIEWFIELD_CLASSID:'分类法ID',
METAVIEWFIELD_TITLEFIELD:'标题字段',
METAVIEWFIELD_ALERT_1:'选择校验规则！',
METAVIEWFIELD_ALERT_2:'请先选择分类法ID',
METAVIEWFIELD_ALERT_3:'输入的不是正整数;',
METAVIEWFIELD_ALERT_4:'默认值[',
METAVIEWFIELD_ALERT_5:']不是合法的整型数.',
METAVIEWFIELD_ALERT_6:']的范围不对，应该在-2^31(',
METAVIEWFIELD_ALERT_7:')到2^31-1(',
METAVIEWFIELD_ALERT_8:')之间',
METAVIEWFIELD_ALERT_9:']的范围不对，应该在-2e125(',
METAVIEWFIELD_ALERT_10:')到2e125(',
METAVIEWFIELD_ALERT_11:'中文名称中不能含有特殊字符',
METAVIEWFIELD_ALERT_12:'中文名称不能以数字开头',
METAVIEWFIELD_ALERT_13:'执行保存操作',
METAVIEWFIELD_ALERT_14:'不存在对应的分类法;',
METAVIEWFIELD_ALERT_15:'输入的不是正整数;',
METAVIEWFIELD_ALERT_16:'确实要将这',
METAVIEWFIELD_ALERT_17:'个视图字段删除吗? ',
METAVIEWFIELD_ALERT_18:'删除进度',
METAVIEWFIELD_ALERT_19:'删除',
METAVIEWFIELD_BUTTON_1:'保存',
METAVIEWFIELD_BUTTON_2:'刷新',
METAVIEWFIELD_BUTTON_3:'刷新列表',
METAVIEWFIELD_BUTTON_4:'返回',
METAVIEWFIELD_WINDOW_TITLE_1:'新建/修改视图字段',
METAVIEWFIELD_WINDOW_TITLE_2:'修改视图',
METAVIEWFIELD_WINDOW_TITLE_3:'新建/修改视图步骤1:新建或选择表',
METAVIEWFIELD_WINDOW_TITLE_4:'字段—调整位置到...',
METAVIEWFIELD_BUTTON_5 : '确定',
METAVIEWFIELD_BUTTON_6 : '取消',
METAVIEWFIELD_BUTTON_7 : '分析枚举值时发现枚举值不合法[',
METAVIEWFIELD_ALERT_20 : '按Enter追加新枚举值',
METAVIEWFIELD_ALERT_21 : ']不是合法的小数.',
METAVIEWFIELD_ALERT_22 : '默认值长度超出了库中长度，请重设.',
METAVIEWFIELD_ALERT_23 : '默认整数超长，请重新输入.',
METAVIEWFIELD_ALERT_24 : '是否类型只能选0或1,请重设.',
METAVIEWFIELD_ALERT_25 : '小数位数不能为空.',
METAVIEWFIELD_ALERT_26 : '您输入的日期 [',
METAVIEWFIELD_ALERT_27 : '] 不是合法的日期！ \n 一个正确的日期格式应当为:',
METAVIEWFIELD_ALERT_28 : '可双击枚举值框进行设置',
METAVIEWFIELD_ALERT_29 : '库中长度不能为空.',
METAVIEWFIELD_ALERT_30 : '库中长度超过了字符串类型的最大允许值（4000）.',
METAVIEWFIELD_ALERT_31 : '单选树只能选择一个默认的分类法，请重设.',
METAVIEWFIELD_ALERT_32 : '枚举值不能为空',
METAVIEWFIELD_ALERT_33 : '默认值',
METAVIEWFIELD_ALERT_34 : '请为默认选项设置值',
METAVIEWFIELD_ALERT_35 : '取消选择',
METAVIEWFIELD_ALERT_36 : '默认值的小数位数超长,请重设.',
METAVIEWFIELD_ALERT_37 : '小数位数不能超过',
METAVIEWFIELD_ALERT_38 : '为系统保留字！'
});

Ext.ns('wcm.domain.MetaViewFieldMgr');
(function(){
var m_oMgr = wcm.domain.MetaViewFieldMgr;
var m_sServiceId = "wcm61_metaviewfield";
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function _doAddOrEdit(_nObjectId, event){
var urlParams = 'objectId='+ _nObjectId;
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'metaviewfield/viewfieldinfo_add_edit.jsp?' + urlParams,
title : wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_1 || '新建/修改视图字段',
callback : function(info){
CMSObj[_nObjectId>0 ? 'afteredit' : 'afteradd'](event)(info);
FloatPanel.close();
}
});
}
Ext.apply(wcm.domain.MetaViewFieldMgr, {
generate : function(event){
var nViewId = event.getContext().params["VIEWID"];
wcm.domain.MetaViewService['generate'](nViewId);
},
edit : function(event){
var _sObjectIds = event.getIds();
_doAddOrEdit(_sObjectIds,event);
},
'delete' : function(event){
var aObjectIds = event.getIds();
var nCount = aObjectIds.length;
var sHint = (aObjectIds==1)?'':' '+nCount+' ';
var msg = String.format("确实要将这{0}个视图字段删除吗?",sHint);
Ext.Msg.confirm(msg,{
yes : function(){
getHelper().call(m_sServiceId, 'deleteViewField', //远端方法名 
{"ObjectIds": aObjectIds}, //传入的参数
true, 
function(){
CMSObj.afterdelete(event)();
}
);
},
no : function(){}
});
},
setAsTitle : function(event){
var _sObjectId = event.getObj().getId();
var _params = {titleField : 1, inOutline : 1};
var params = Object.extend(_params, {ObjectId : _sObjectId})
getHelper().call(m_sServiceId, 'saveViewField', params, true, function(transport, json){
event.getObj().afteredit();
});
},
selectFields : function(event){
var _sId = event.getContext().params['VIEWID'];
var sDialogId = "editMultiTabler";
wcm.CrashBoarder.get(sDialogId).show({
id : sDialogId,
title : wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_2 || "修改视图",
src : "metadbtable/build_to_view.html",
width : "800px",
height : "500px",
maskable:true,
params : {viewId: _sId},
callback : function(params){
var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
CMSObj.createFrom(info, event.getContext()).afteredit();
}
});
},
selectDBFields : function(event){
var isSingleTable = event.getContext().params["ISSINGLETABLE"];
if(isSingleTable == false){
wcm.domain.MetaViewService.editMultiTable(event.getContext().params['VIEWID']);
}else if(isSingleTable == true){
var _params = {tableInfoId : event.getContext().params['MAINTABLEID'], 
viewId : event.getContext().params['VIEWID']
};
wcm.domain.MetaViewService.addEditStepTwo(_params, function (){
var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
CMSObj.createFrom(info, event.getContext()).afteredit();
});
}
},
setView : function(event){
var nViewId = event.getContext().params['VIEWID']
wcm.MetaViewSelector.selectView({selectIds : nViewId},function(args){
var viewId = args.ViewId || 0;
event.getContext().viewId = viewId;
var isContainsChildren = args["ContainsChildren"] || false;
var channelId = getParameter("channelid");
wcm.MetaViewSelector.setChannelView(viewId, {
channelid : channelId,
ContainsChildren : isContainsChildren
}, function(){
var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
CMSObj.createFrom(info, event.getContext()).afteredit();
});
});
},
addView : function(event){
var _id = 0 ;
var oParams = {objectId : _id};
var channelId = event.getContext().params["CHANNELID"] || 0;
if(channelId){
oParams['channelId'] = channelId;
}
var url = 'metaview/viewinfo_add_edit_step1.jsp?' + $toQueryStr(oParams);
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_3 || '新建/修改视图步骤1:新建或选择表',
callback : function(_tableInfoId, _viewId){
var _params = {tableInfoId : _tableInfoId , viewId : _viewId};
wcm.domain.MetaViewService.addEditStepTwo(_params, function(){
var channelId = getParameter("channelid");
wcm.MetaViewSelector.setChannelView(_viewId, {
channelid : channelId,
ContainsChildren : false
}, function(){
var info = {objId : 0, objType : WCMConstants.OBJ_TYPE_METAVIEWFIELD};
CMSObj.createFrom(info, event.getContext()).afteredit();
});
});
}
});
},
viewFieldPositionSet : function(event){
var oPageParams = {};
var nObjectId = event.getObj().getId();
Object.extend(oPageParams,{"ObjectId":nObjectId});
var url = 'metaviewfield/viewfield_position_set.jsp?' + $toQueryStr(oPageParams);
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : (wcm.LANG.METAVIEWFIELD_WINDOW_TITLE_4 || '字段—调整位置到...'),
callback : function(){
CMSObj.afteredit(event)();
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MetaViewFieldMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(context.host.objType == "channel"){
if(context.params["HASRIGHT"] == 0){
return false;
}
}
return true;
};
reg({
key : 'generate',
type : 'metaviewfieldInRoot',
desc : '生成应用',
title : '生成应用...',
rightIndex : -2,
order : 3,
fn : pageObjMgr['generate']
});
reg({
key : 'selectFields',
type : 'metaviewfieldInRoot',
desc : '维护字段',
title : '维护字段...',
rightIndex : -2,
order : 1,
fn : pageObjMgr['selectFields']
});
reg({
key : 'setAsTitle',
type : 'metaviewfield',
desc : wcm.LANG.METAVIEWFIELD_PROCESS_4||'设为标题',
title : wcm.LANG.METAVIEWFIELD_PROCESS_4||'设为标题',
rightIndex : -1,
order : 4,
fn : pageObjMgr['setAsTitle'],
isVisible : fnIsVisible
});
reg({
key : 'delete',
type : 'metaviewfield',
desc : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
title : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
rightIndex : -1,
order : 5,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete'],
isVisible : fnIsVisible
});
reg({
key : 'delete',
type : 'metaviewfields',
desc : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
title : wcm.LANG.METAVIEWFIELD_PROCESS_5||'删除',
rightIndex : -1,
order : 6,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete'],
isVisible : fnIsVisible
});
reg({
key : 'generate',
type : 'metaviewfieldInChannel',
desc : '生成应用',
title : '生成应用...',
rightIndex : 13,
order : 3,
fn : pageObjMgr['generate'],
isVisible : function(event){
var context = event.getContext();
if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1)){
return true;
}
return false;
}
});
reg({
key : 'add',
type : 'metaviewfieldInChannel',
desc : wcm.LANG.METAVIEWFIELD_PROCESS_6||'新建视图',
title : wcm.LANG.METAVIEWFIELD_PROCESS_6||'新建视图',
rightIndex : 13,
order : 1,
fn : pageObjMgr['addView'],
isVisible : function(event){
var context = event.getContext();
if(context.params["VIEWID"]){
return false;
}
return true;
},
quickKey : 'N'
});
reg({
key : 'setView',
type : 'metaviewfieldInChannel',
desc : '设置视图',
title : '设置视图...',
rightIndex : 13,
order : 1,
fn : pageObjMgr['setView']
});
reg({
key : 'selectFields',
type : 'metaviewfieldInChannel',
desc : '维护字段',
title : '维护字段...',
rightIndex : 13,
order : 1,
fn : pageObjMgr['selectFields'],
isVisible : function(event){
var context = event.getContext();
if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1)){
return true;
}
return false;
}
});
reg({
key : 'viewFieldPositionSet',
type : 'metaviewfield',
desc : '调整顺序',
title : '调整顺序',
rightIndex : -1,
order : 2,
fn : pageObjMgr['viewFieldPositionSet'],
isVisible : fnIsVisible
});
})();
(function(){
function selectFieldGroup(event){
var nViewId = event.getContext().params['VIEWID']
var sURL = WCMConstants.WCM6_PATH + "metaviewfieldgroup/metaviewfieldgroup_list.jsp";
var sTitle = '字段分组维护列表';
wcm.CrashBoarder.get('fieldgroup_Select').show({
title : sTitle,
src : sURL,
width: '650px',
height: '400px',
reloadable : true,
params : {MetaViewId : nViewId},
maskable : true,
callback : function(_args){
}
});
}
var reg = wcm.SysOpers.register;
reg({
key : 'maintainFieldGroup',
type : 'metaviewfieldInRoot',
desc : '维护分组',
title : '维护视图字段分组',
rightIndex : -2,
order : 3.1,
fn :selectFieldGroup,
isVisible : function(event){
var context = event.getContext();
if(context.params["ISSINGLETABLE"]){
return true;
}
return false;
}
});
reg({
key : 'maintainFieldGroup',
type : 'metaviewfieldInChannel',
desc : '维护分组',
title : '维护视图字段分组',
rightIndex : -2,
order : 1.1,
fn :selectFieldGroup,
isVisible : function(event){
var context = event.getContext();
if(context.params["VIEWID"] && (context.params["HASRIGHT"] == 1) && context.params["ISSINGLETABLE"]){
return true;
}
return false;
}
});
})();

Ext.ns('wcm.MetaViewFields', 'wcm.MetaViewField');
WCMConstants.OBJ_TYPE_METAVIEWFIELD = 'metaviewfield';
wcm.MetaViewFields = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METAVIEWFIELD;
wcm.MetaViewFields.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaViewFields, wcm.CMSObjs, {
});
wcm.MetaViewField = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METAVIEWFIELD;
wcm.MetaViewField.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METAVIEWFIELD, 'wcm.MetaViewField');
Ext.extend(wcm.MetaViewField, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['METAVIEWFIELD'] || '视图字段';
wcm.PageOper.registerPanels({
metaviewfieldInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
metaviewfieldInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
metaviewfieldInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
metaviewfield : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_metaviewfield&methodname=jFindbyid'
},
metaviewfields : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();
ValidationHelper.bindValidations([
{
renderTo : 'FieldName',
type :'common_char2',
required :'',
max_len :'30',
desc :wcm.LANG.METAVIEWFIELD_FIELDNAME||'英文名称',
methods : function(){
var eleValue = this.field.value;
if(eleValue){
if(containKeyWords(eleValue)){
this.warning = String.format("[<font color=\'red\'>{0}</font>]为系统保留字!",eleValue);
return false;
} 
PageContext.validExistProperty({objType : 1886731157});
}
return true;
}
},
{
renderTo : 'AnotherName',
type:'string2',
required:'',
max_len:'100',
desc:wcm.LANG.METAVIEWFIELD_ANOTHERNAME||'中文名称'
}
]);

