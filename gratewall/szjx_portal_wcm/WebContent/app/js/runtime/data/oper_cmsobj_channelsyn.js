Ext.apply(wcm.LANG, {
CHANNELSYN_SURE : '确定',
CHANNELSYN_CANCEL : '取消',
CHANNELSYN_UNIT : '个',
CHANNELSYN_DOCUMENTSYN : '文档同步',
CHANNELSYN_VALID_1 : "汇总开始时间大于汇总结束时间",
CHANNELSYN_VALID_2 : "文档创建时间范围错误",
CHANNELSYN_VALID_3 : "请选择要汇总到哪个/些栏目",
CHANNELSYN_VALID_4 : '选择栏目',
CHANNELSYN_VALID_5 : '验证成功！',
CHANNELSYN_VALID_6 : '新建栏目分发',
CHANNELSYN_VALID_7 : '选择对象',
CHANNELSYN_VALID_8 : '新建栏目汇总',
CHANNELSYN_VALID_9 : '修改栏目分发',
CHANNELSYN_VALID_10 : '确实要删除这',
CHANNELSYN_VALID_11 : '个栏目分发吗? ',
CHANNELSYN_VALID_12 :'删除这个栏目分发',
CHANNELSYN_VALID_13 :'删除这些栏目分发',
CHANNELSYN_VALID_14 :'修改栏目汇总',
CHANNELSYN_VALID_15 : '个栏目汇总吗?',
CHANNELSYN_VALID_16 : '修改这个栏目汇总',
CHANNELSYN_VALID_17 : '删除这个栏目汇总',
CHANNELSYN_VALID_18 : '删除这些栏目汇总',
CHANNELSYN_VALID_19 : '修改这个栏目分发',
CHANNELSYN_VALID_20 : "所选的栏目中有与源栏目使用的表单不一致！",
CHANNELSYN_VALID_21 : "以下栏目会和当前栏目形成回路:\n",
CHANNELSYN_VALID_22 : "请选择要分发到哪个/些栏目",
CHANNELSYN_VALID_23 : "新建",
CHANNELSYN_VALID_24 : "删除",
CHANNELSYN_VALID_25 : "刷新",
CHANNELSYN_VALID_26 : "请选择要删除的栏目分发",
CHANNELSYN_VALID_27 : "删除这个/些栏目分发",
CHANNELSYN_VALID_28 : "刷新列表",
CHANNELSYN_VALID_29 : "栏目分发列表",
CHANNELSYN_VALID_30 : "请选择要删除的栏目汇总",
CHANNELSYN_VALID_31 : "删除这个/些栏目汇总",
CHANNELSYN_VALID_32 : "栏目汇总列表",
CHANNELSYN_VALID_33 : "切换为栏目汇总列表",
CHANNELSYN_VALID_34 : "切换为栏目分发列表",
CHANNELSYN_VALID_35 : "分发开始时间大于分发结束时间",
CHANNELSYN_VALID_36 : '目标栏目',
CHANNELSYN_VALID_37 : '源栏目',
CHANNELSYN_VALID_38 : '修改栏目分发',
CHANNELSYN_VALID_39 : '确实要删除这{0}个栏目分发吗?',
CHANNELSYN_VALID_40 : '确实要删除这{0}个栏目汇总吗?',
CHANNELSYN_VALID_41 : '选择筛选条件'
});

Ext.ns('wcm.domain.ChannelSynMgr');
(function(){
var m_oMgr = wcm.domain.ChannelSynMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.ChannelSynMgr, {
add : function(event){
var oPageParams = event.getContext();
Object.extend(oPageParams,{"ObjectId":0});
Object.extend(oPageParams,{"ChannelId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'channelsyn/docsyn_dis_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELSYN_VALID_6 || '新建栏目分发...', CMSObj.afteradd(event)
);
},
edit : function(event){
var oPageParams = event.getContext();
var sId = event.getIds().join();
Object.extend(oPageParams,{"ObjectId":sId});
Object.extend(oPageParams,{"ChannelId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'channelsyn/docsyn_dis_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELSYN_VALID_9 || '修改栏目分发', CMSObj.afteredit(event)
);
},
"delete" : function(event){
var oPageParams = {};
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = String.format(wcm.LANG.CHANNELSYN_VALID_39 || '确实要删除这{0}个栏目分发吗?', sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
BasicDataHelper.call("wcm6_documentSyn", 
'delete', //远端方法名 
Object.extend(oPageParams, {"ObjectIds": sId,"channelId":event.getHost().getId()}), //传入的参数
true, 
function(){
event.getObjs().afterdelete();
}
);
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChannelSynMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'channelsyn',
desc : wcm.LANG.CHANNELSYN_VALID_38 ||'修改这个栏目分发',
title:'修改这个栏目分发',
rightIndex : 13,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'delete',
type : 'channelsyn',
desc : wcm.LANG.CHANNELSYN_VALID_12 ||'删除这个栏目分发',
title:'删除这个栏目分发',
rightIndex : 13,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'channelsynInChannel',
desc : wcm.LANG.CHANNELSYN_VALID_6 ||'新建栏目分发',
title:'新建栏目分发...',
rightIndex : 13,
order : 3,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'delete',
type : 'channelsyns',
desc : wcm.LANG.CHANNELSYN_VALID_13 ||'删除这些栏目分发',
rightIndex : 13,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.ChannelSyns', 'wcm.ChannelSyn');
WCMConstants.OBJ_TYPE_CHANNELSYN = 'ChannelSyn';
wcm.ChannelSyns = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNELSYN;
wcm.ChannelSyns.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChannelSyns, wcm.CMSObjs, {
});
wcm.ChannelSyn = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNELSYN;
wcm.ChannelSyn.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNELSYN, 'wcm.ChannelSyn');
Ext.extend(wcm.ChannelSyn, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['CHANNELSYN'] || '文档同步';
wcm.PageOper.registerPanels({
channelsynInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
channelsynInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
channelsynInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL_SHORT'] || '{0}操作任务', sName),
displayNum : 5
},
channelsyn : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_channelsyn&methodname=jFindbyid',
params : function(opt){
return "channelId=" + opt.event.getHost().getId() + "&asTarget=false";
}
},
channelsyns : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

Ext.ns('wcm.domain.ChannelSynMgr');
(function(){
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.ChannelSynMgr, {
"synundo" : function(event){
var oPageParams = {};
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = String.format('确实要立即执行这{0}个栏目分发吗?', sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
BasicDataHelper.call("wcm61_docsyn", 
'synUndoContent', //远端方法名 
Object.extend(oPageParams, {"ObjectIds": sId,"channelId":event.getHost().getId()}), //传入的参数
true, 
function(){
}
);
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChannelSynMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'synundo',
type : 'channelsyn',
desc : '立即执行分发',
title: '立即执行分发',
rightIndex : 13,
fn : pageObjMgr['synundo']
});
reg({
key : 'synundo',
type : 'channelsyns',
desc : '立即执行分发',
title: '立即执行分发',
rightIndex : 13,
fn : pageObjMgr['synundo']
});
})();

