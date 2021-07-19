Ext.apply(wcm.LANG, {
CONTENTEXTFIELD_PROCESS_1 : '执行进度，请稍候...',
CONTENTEXTFIELD_PROCESS_2 : '提交数据',
CONTENTEXTFIELD_PROCESS_3 : '成功完成',
CONTENTEXTFIELD_CONFIRM_1 : "确定要同步创建到所有子对象吗？",
CONTENTEXTFIELD_CONFIRM_2 : "同步扩展字段到子对象成功！",
CONTENTEXTFIELD_CONFIRM_3 :'新建/修改扩展字段',
CONTENTEXTFIELD_CONFIRM_4 : '新建扩展字段',
CONTENTEXTFIELD_CONFIRM_5 : "您确定要删除扩展字段吗?",
CONTENTEXTFIELD_CONFIRM_6 : '同步到子对象',
CONTENTEXTFIELD_CONFIRM_7 : '修改这个扩展字段',
CONTENTEXTFIELD_CONFIRM_8 : '删除这个扩展字段',
CONTENTEXTFIELD_CONFIRM_9 : '删除这些扩展字段',
CONTENTEXTFIELD_CONFIRM_10 : '确定',
CONTENTEXTFIELD_CONFIRM_11 : '选择对象',
CONTENTEXTFIELD_UNIT : '个',
CONTENTEXTFIELD_CONTENTEXTFIELD : '扩展字段',
CONTENTEXTFIELD_OUTNAME : '显示名称',
CONTENTEXTFIELD_FIELDNAME : '字段名称',
CONTENTEXTFIELD_SHOWEXT : '显示子对象的扩展字段',
CONTENTEXTFIELD_HIDEEXT : '隐藏子对象的扩展字段',
CONTENTEXTFIELD_CONFIRM_12 : "同步删除子对象的同一字段",
CONTENTEXTFIELD_CONFIRM_13 : '新建',
CONTENTEXTFIELD_CONFIRM_14 : '删除',
CONTENTEXTFIELD_CONFIRM_15 : '删除这个/些扩展字段',
CONTENTEXTFIELD_CONFIRM_16 : '刷新',
CONTENTEXTFIELD_CONFIRM_17 : '刷新列表',
CONTENTEXTFIELD_CONFIRM_18 : '扩展字段列表',
CONTENTEXTFIELD_CONFIRM_19 : '同步',
CONTENTEXTFIELD_CONFIRM_20 : '请选择要删除的扩展字段.',
CONTENTEXTFIELD_CONFIRM_21 : '请选择要同步的扩展字段.',
CONTENTEXTFIELD_CONFIRM_22 : '按Enter追加新枚举值',
CONTENTEXTFIELD_CONFIRM_23 : '取消',
CONTENTEXTFIELD_CONFIRM_24 : '取消选择',
CONTENTEXTFIELD_CONFIRM_25 : '请为默认选项设置值',
CONTENTEXTFIELD_CONFIRM_26 : "分析枚举值时发现枚举值不合法[",
CONTENTEXTFIELD_CONFIRM_27 : '默认值',
CONTENTEXTFIELD_CONFIRM_28 : "]不是合法的整型数.",
CONTENTEXTFIELD_CONFIRM_29 : "默认整数超长，请重新输入.",
CONTENTEXTFIELD_CONFIRM_30 : "]的范围不对，应该在-2^31到2^31-1之间(",
CONTENTEXTFIELD_CONFIRM_31 : "]不是合法的小数.",
CONTENTEXTFIELD_CONFIRM_32 : "]的范围不对，应该在-2e125到2e125之间(",
CONTENTEXTFIELD_CONFIRM_33 : "默认值长度超出了库中长度，请重设.",
CONTENTEXTFIELD_CONFIRM_34 : "新建扩展字段",
CONTENTEXTFIELD_CONFIRM_35 : "修改这个扩展字段",
CONTENTEXTFIELD_CONFIRM_37 : "描述信息不能为空.",
CONTENTEXTFIELD_CONFIRM_38 : '小数位数不能超过18.',
CONTENTEXTFIELD_CONFIRM_39 : '默认值的小数位数超长,请重设.',
CONTENTEXTFIELD_CONFIRM_40 : '枚举值长度超出了库中长度，请重设.',
CONTENTEXTFIELD_CONFIRM_41 : '选择子对象',
CONTENTEXTFIELD_CONFIRM_42 : "确定要同步创建到以上选中子对象吗?",
CONTENTEXTFIELD_CONFIRM_43 : "继承父对象扩展字段",
CONTENTEXTFIELD_CONFIRM_44 : "选择父对象",
CONTENTEXTFIELD_CONFIRM_45 : "请选择要继承的扩展字段!",
CONTENTEXTFIELD_CONFIRM_46 : "枚举值不允许存在值相同情况.(值为空时默认会取描述为值)",
CONTENTEXTFIELD_CONFIRM_47 : "继承",
CONTENTEXTFIELD_CONFIRM_48 : "调整顺序",
CONTENTEXTFIELD_CONFIRM_49 : "扩展字段-调整顺序"
});

Ext.ns('wcm.domain.ContentExtFieldMgr');
(function(){
var m_oMgr = wcm.domain.ContentExtFieldMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.ContentExtFieldMgr, {
"new" : function(event){
var oPageParams = event.getContext();
Object.extend(oPageParams,{"ObjectId":0});
FloatPanel.open(WCMConstants.WCM6_PATH +
'contentextfield/contentextfield_addedit.jsp?' + $toQueryStr(oPageParams),
wcm.LANG.CONTENTEXTFIELD_CONFIRM_34 || '新建扩展字段',
CMSObj.afteradd(event)
);
},
edit : function(event){
var oPageParams = event.getContext();
var sId = event.getIds().join();
Object.extend(oPageParams,{"ObjectId":sId});
FloatPanel.open(WCMConstants.WCM6_PATH +
'contentextfield/contentextfield_addedit.jsp?' + $toQueryStr(oPageParams),
wcm.LANG.CONTENTEXTFIELD_CONFIRM_35 || '修改这个扩展字段',
CMSObj.afteredit(event));
},
'delete' : function(event){
var oPageParams = event.getContext();
var sId = event.getIds();
Ext.Msg.confirm((wcm.LANG.CONTENTEXTFIELD_CONFIRM_5 || "您确定要删除扩展字段吗?")+"<br><table><tr style='float:left'><td style='padding-bottom:" + (Ext.isIE ? '5px':'0px') + ";'><input type='checkbox' name='cbx' id='cbx' value=''></td><td style='text-align:left'><font color='red'>" + (wcm.LANG.CONTENTEXTFIELD_CONFIRM_12 ||"同步删除子对象的同一字段") + "</font></td></tr></table>", {
yes : function(){
Object.extend(PageContext.params,{"ObjectIds":sId,"ContainsChildren":this.$("cbx").checked,'HideChildren':true});
BasicDataHelper.call('wcm6_extendfield',"delete",PageContext.params,true,function(){
delete PageContext.params['ContainsChildren'];//for refresh:do not show the children.
event.getObjs().afterdelete();
});
}
});
},
sync : function(event){
var hostType = event.getHost().getType();
var hostId = event.getHost().getId();
var sId = event.getIds().reverse();
var params = {
objType: hostType == "channel" ? 101 : (hostType == "website" ? 103:1),
objId:hostId,
close : 0,
ExcludeTop : 1,
ExcludeInfoview : 1,
ExcludeLink : 1
};
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'website/object_select.html',
dialogArguments : params,
title : wcm.LANG.CONTENTEXTFIELD_CONFIRM_41||'选择子对象',
callback : function(args){
if(args.allChildren){
var sHtml = wcm.LANG.CONTENTEXTFIELD_CONFIRM_1 || "确定要同步创建到所有子对象吗?";
Ext.Msg.confirm(sHtml, {
ok : function(){
Object.extend(PageContext.params,{"ObjectIds":sId,"ImpartAll":true});
BasicDataHelper.call('wcm6_extendfield','impartExtendFields',PageContext.params,true,
function(){Ext.Msg.alert(wcm.LANG.CONTENTEXTFIELD_CONFIRM_2 || "同步扩展字段到子对象成功!");FloatPanel.close();});
}
});
}else{
var sSiteIds = [],sChannelIds = [];
for(var i =0;i< args.typenames.length;i++){
if(args.typenames[i].indexOf("s") > 0) sSiteIds.push(args.typenames[i].split("_")[1]);
else {sChannelIds.push(args.typenames[i].split("_")[1])};
}
var sHtml = wcm.LANG.CONTENTEXTFIELD_CONFIRM_42 || "确定要同步创建到以上选中子对象吗?";
Ext.Msg.confirm(sHtml, {
ok : function(){
Object.extend(PageContext.params,{"ObjectIds":sId,"ImpartAll":false,"SiteIds":sSiteIds.join(","),"ChannelIds":sChannelIds.join(",")});
BasicDataHelper.call('wcm6_extendfield','impartExtendFields',PageContext.params,true,
function(){Ext.Msg.alert(wcm.LANG.CONTENTEXTFIELD_CONFIRM_2 || "同步扩展字段到子对象成功!");FloatPanel.close();});
}
});
}
}
});
},
inhert : function(event){
var oPageParams = {};
var host = event.getHost();
var hostid = host.getId();
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
FloatPanel.open(WCMConstants.WCM6_PATH +
'contentextfield/parentNode_select.jsp?' + $toQueryStr(oPageParams),
wcm.LANG.CONTENTEXTFIELD_CONFIRM_44 || '选择父对象', function(){event.getObjs().afteradd()});
},
docpositionset : function(event){
var oPageParams = event.getContext();
var sId = event.getIds().join();
var host = event.getHost();
var hostid = host.getId();
Object.extend(oPageParams,{"channelid":hostid,"objectid":sId});
var sTitle = wcm.LANG.CONTENTEXTFIELD_CONFIRM_49 || "扩展字段-调整顺序";
FloatPanel.open(WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_position_set.jsp?' + $toQueryStr(oPageParams), sTitle, CMSObj.afteredit(event));
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ContentExtFieldMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'contentextfield',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_7 || '修改这个扩展字段',
title:'修改这个扩展字段...',
rightIndex : 19,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'delete',
type : 'contentextfield',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_8 || '删除这个扩展字段',
title:'删除这个扩展字段...',
rightIndex : 19,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'sync',
type : 'contentextfield',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_6 || '同步到子对象',
title : '同步到子对象...',
rightIndex : 19,
order : 3,
fn : pageObjMgr['sync']
});
reg({
key : 'new',
type : 'contentextfieldInChannel',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_4 || '新建扩展字段',
title:'新建扩展字段...',
rightIndex : 19,
order : 4,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'inhert',
type : 'contentextfieldInChannel',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_43 || '继承父对象扩展字段',
title:'继承父对象扩展字段...',
rightIndex : 19,
order : 4,
fn : pageObjMgr['inhert']
});
reg({
key : 'new',
type : 'contentextfieldInSite',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_4 || '新建扩展字段',
title:'新建扩展字段...',
rightIndex : 19,
order : 5,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'inhert',
type : 'contentextfieldInSite',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_43 || '继承父对象扩展字段',
title:'继承父对象扩展字段...',
rightIndex : 19,
order : 6,
fn : pageObjMgr['inhert']
});
reg({
key : 'new',
type : 'contentextfieldInRoot',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_4 || '新建扩展字段',
title:'新建扩展字段...',
rightIndex : 19,
order : 7,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'delete',
type : 'contentextfields',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_9 || '删除这些扩展字段',
title:'删除这些扩展字段',
rightIndex : 19,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'sync',
type : 'contentextfields',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_6 || '同步到子对象',
title :'同步到子对象...',
rightIndex : 19,
order : 9,
fn : pageObjMgr['sync']
});
reg({
key : 'docpositionset',
type : 'contentextfield',
desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_48 ||'调整顺序',
title:'调整顺序',
rightIndex : 19,
order : 10,
fn : pageObjMgr['docpositionset'],
isVisible : function(event){
if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
return true;
return false;
}
});
})();

Ext.ns('wcm.ContentExtFields', 'wcm.ContentExtField');
WCMConstants.OBJ_TYPE_CONTENTEXTFIELD = 'contentextfield';
wcm.ContentExtFields = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CONTENTEXTFIELD;
wcm.ContentExtFields.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ContentExtFields, wcm.CMSObjs, {
});
wcm.ContentExtField = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CONTENTEXTFIELD;
wcm.ContentExtField.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CONTENTEXTFIELD, 'wcm.ContentExtField');
Ext.extend(wcm.ContentExtField, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['CONTENTEXTFIELD'] || '扩展字段';
wcm.PageOper.registerPanels({
contentextfieldInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
contentextfieldInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
contentextfieldInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
contentextfield : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_contentextfield&methodname=jFindbyid'
},
contentextfields : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

