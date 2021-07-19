Ext.apply(wcm.LANG, {
METADBFIELD_1 : '确定',
METADBFIELD_2 : '取消',
METADBFIELD_3 : '分析枚举值时发现枚举值不合法[',
METADBFIELD_4 : '按Enter追加新枚举值',
METADBFIELD_5 : '选择校验规则！',
METADBFIELD_6 : '请先选择分类法ID',
METADBFIELD_7 : '输入的不是正整数;',
METADBFIELD_8 : '已存在字段',
METADBFIELD_9 : '默认值[',
METADBFIELD_10 : ']不是合法的整型数.',
METADBFIELD_11 : ']不是合法的小数.',
METADBFIELD_12 : ']的范围不对，应该在-2^31到2^31-1之间(',
METADBFIELD_13 : ']的范围不对，应该在-2e125到2e125之间(',
METADBFIELD_14 : '中文名称中不能含有特殊字符',
METADBFIELD_15 : '中文名称不能以数字开头',
METADBFIELD_16 : '不存在对应的分类法;',
METADBFIELD_17 : '新建元数据字段',
METADBFIELD_18 : '新建第',
METADBFIELD_19 : '个',
METADBFIELD_20 : '关闭',
METADBFIELD_21 : '元数据字段',
METADBFIELD_22 : '中文名称',
METADBFIELD_23 : '英文名称',
METADBFIELD_24 : '创建者',
METADBFIELD_25 : '字段ID',
METADBFIELD_26 : '分类法ID',
METADBFIELD_27 : '新建',
METADBFIELD_28 : '新建一个字段',
METADBFIELD_29 : '修改',
METADBFIELD_30 : '修改这个字段',
METADBFIELD_31 : '删除',
METADBFIELD_32 : '删除这个/些字段',
METADBFIELD_33 : '刷新',
METADBFIELD_34 : '刷新列表',
METADBFIELD_35 : '返回',
METADBFIELD_36 : '字段描述',
METADBFIELD_37 : '字段名称',
METADBFIELD_38: '确实要将这',
METADBFIELD_39 : '个元数据字段删除吗? ',
METADBFIELD_41 : '删除这个字段',
METADBFIELD_42 : '删除这些字段',
METADBFIELD_43 : '修改元数据字段',
METADBFIELD_44 : '进度执行中，请稍候...',
METADBFIELD_45 : '默认值长度超出了库中长度，请重设.',
METADBFIELD_46 : '默认整数超长，请重新输入.',
METADBFIELD_47 : '是否类型只能选0或1,请重设.',
METADBFIELD_48 : '小数位数不能为空.',
METADBFIELD_49 : '您输入的日期 [',
METADBFIELD_50 : '] 不是合法的日期！ \n 一个正确的日期格式应当为:',
METADBFIELD_51 : '可双击枚举值框进行设置',
METADBFIELD_52 : '库中长度不能为空.',
METADBFIELD_53 : '库中长度超过了字符串类型的最大允许值（4000）.',
METADBFIELD_54 : '单选树只能选择一个默认的分类法，请重设.',
METADBFIELD_55 : '枚举值不能为空',
METADBFIELD_56 : '默认值',
METADBFIELD_57 : '取消选择',
METADBFIELD_58 : '请为默认选项设置值',
METADBFIELD_59 : '默认值的小数位数超长,请重设.',
METADBFIELD_60 : '小数位数不能超过'
});

Ext.ns('wcm.domain.MetaDBFieldMgr');
(function(){
var m_oMgr = wcm.domain.MetaDBFieldMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function addEdit(_id,event){
var url = 'metadbfield/fieldinfo_add_edit.jsp?objectId=' + _id;
var sTilte = null;
if(_id == 0){
sTitle = "新建元数据字段"+"<span style='font-size:12px;'>"+String.format("--新建第<font color=\'blue\'>[1]</font>个")+"</span>";
}else{
sTitle = wcm.LANG.METADBFIELD_43 || "修改元数据字段";
}
url += "&tableinfoid="+event.getContext().params["TABLEINFOID"];
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : sTitle,
callback : function(){
CMSObj[_id>0 ? 'afteredit' : 'afteradd'](event)();
}
});
}
Ext.apply(wcm.domain.MetaDBFieldMgr, {
add : function(event){
addEdit(0,event);
},
edit : function(event){
addEdit(event.getObj().getId(),event);
},
'delete' : function(event){
var sId = event.getObjs().getIds();
var nCount = sId.length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var msg = String.format("确实要将这{0}个元数据字段删除吗?",sHint);
Ext.Msg.confirm(msg ,{
yes : function(){
getHelper().call("wcm61_metadbfield","deleteDBFieldInfo",{"ObjectIds":sId},true, function(){
event.getObjs()["afterdelete"]()});
},
no : function(){}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MetaDBFieldMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'MetaDBField',
desc : '修改这个字段',
title : '修改这个字段...',
rightIndex : -1,
order : 2,
fn : pageObjMgr['edit'],
quickKey : ['E']
});
reg({
key : 'delete',
type : 'MetaDBField',
desc : '删除这个字段',
title : '删除这个字段...',
rightIndex : -1,
order : 3,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'MetaDBFieldInRoot',
desc : '新建一个字段',
title : '新建一个字段...',
rightIndex : -1,
order : 4,
fn : pageObjMgr['add'],
quickKey : ['N']
});
reg({
key : 'delete',
type : 'MetaDBFields',
desc : '删除这些字段',
title : '删除这些字段...',
rightIndex : -1,
order : 5,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.MetaDBFields', 'wcm.MetaDBField');
WCMConstants.OBJ_TYPE_METADBFIELD = 'MetaDBField';
wcm.MetaDBFields = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METADBFIELD;
wcm.MetaDBFields.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaDBFields, wcm.CMSObjs, {
});
wcm.MetaDBField = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METADBFIELD;
wcm.MetaDBField.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METADBFIELD, 'wcm.MetaDBField');
Ext.extend(wcm.MetaDBField, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['METADBFIELD'] || '元数据字段';
wcm.PageOper.registerPanels({
metadbfieldInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
metadbfieldInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
metadbfieldInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
metadbfield : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_metadbfield&methodname=jFindbyid'
},
metadbfields : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

