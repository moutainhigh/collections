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
CHANNELSYN_VALID_12 :'删除这个栏目汇总',
CHANNELSYN_VALID_13 :'删除这些栏目汇总',
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
CHANNELSYN_VALID_38 : '修改栏目汇总',
CHANNELSYN_VALID_39 : '确实要删除这{0}个栏目分发吗?',
CHANNELSYN_VALID_40 : '确实要删除这{0}个栏目汇总吗?'
});

Ext.ns('wcm.domain.ChannelSynColMgr');
(function(){
var m_oMgr = wcm.domain.ChannelSynColMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.ChannelSynColMgr, {
add : function(event){
var oPageParams = event.getContext();
Object.extend(oPageParams,{"ObjectId":0});
Object.extend(oPageParams,{"ChannelId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'channelsyn/docsyn_col_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELSYN_VALID_8 ||'新建栏目汇总...', CMSObj.afteradd(event)
);
},
edit : function(event){
var oPageParams = event.getContext();
var sId = event.getIds().join();
Object.extend(oPageParams,{"ObjectId":sId});
Object.extend(oPageParams,{"ChannelId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'channelsyn/docsyn_col_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELSYN_VALID_14 ||'修改栏目汇总', CMSObj.afteredit(event)
);
},
"delete" : function(event){
var oPageParams = {};
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = String.format(wcm.LANG.CHANNELSYN_VALID_40 || '确实要删除这{0}个栏目汇总吗?', sHint);
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
var pageObjMgr = wcm.domain.ChannelSynColMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'channelsyncol',
desc : wcm.LANG.CHANNELSYN_VALID_38 ||'修改这个栏目汇总',
title:'修改这个栏目汇总...',
rightIndex : 13,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'delete',
type : 'channelsyncol',
desc : wcm.LANG.CHANNELSYN_VALID_12 ||'删除这个栏目汇总',
title:'删除这个栏目汇总...',
rightIndex : 13,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'channelsyncolInChannel',
desc : wcm.LANG.CHANNELSYN_VALID_8 ||'新建栏目汇总',
title:'新建栏目汇总...',
rightIndex : 13,
order : 3,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'delete',
type : 'channelsyncols',
desc : wcm.LANG.CHANNELSYN_VALID_13 ||'删除这些栏目汇总',
title:'删除这些栏目汇总...',
rightIndex : 13,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.ChannelSynCols', 'wcm.ChannelSynCol');
WCMConstants.OBJ_TYPE_CHANNELSYNCOL = 'ChannelSynCol';
wcm.ChannelSynCols = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNELSYNCOL;
wcm.ChannelSynCols.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChannelSynCols, wcm.CMSObjs, {
});
wcm.ChannelSynCol = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNELSYNCOL;
wcm.ChannelSynCol.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNELSYNCOL, 'wcm.ChannelSynCol');
Ext.extend(wcm.ChannelSynCol, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['CHANNELSYNCOL'] || '文档同步';
wcm.PageOper.registerPanels({
channelsyncolInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
channelsyncolInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
channelsyncolInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL_SHORT'] || '{0}操作任务', sName),
displayNum : 5
},
channelsyncol : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_channelsyncol&methodname=jFindbyid',
params : function(opt){
return "channelId=" + opt.event.getHost().getId() + "&asTarget=true";
}
},
channelsyncols : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

Ext.ns('wcm.domain.MyExtMgr');
(function(){
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.MyExtMgr, {
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
var pageObjMgr = wcm.domain.MyExtMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'synundo',
type : 'channelsyncol',
desc : '立即执行分发',
title: '立即执行分发',
rightIndex : 13,
fn : pageObjMgr['synundo']
});
reg({
key : 'synundo',
type : 'channelsyncols',
desc : '立即执行分发',
title: '立即执行分发',
rightIndex : 13,
fn : pageObjMgr['synundo']
});
})();

