Ext.apply(wcm.LANG, {
CHANNELCONTENTLINK_FN_1 : "热词[{0}]已存在(不区分大小写)!",
CHANNELCONTENTLINK_SURE : '确定',
CHANNELCONTENTLINK_UNIT : '个',
CHANNELCONTENTLINK_MGR : '热词分类',
CHANNELCONTENTLINK_NAME : '热词名称',
CHANNELCONTENTLINK_DESC : '热词描述',
CHANNELCONTENTLINK_FN_2 : '新建栏目热词',
CHANNELCONTENTLINK_FN_3 : '修改栏目热词',
CHANNELCONTENTLINK_FN_4 : '按分类导入系统热词',
CHANNELCONTENTLINK_FN_5 : "您确定要删除选定的热词?",
CHANNELCONTENTLINK_FN_6 : '编辑这个热词',
CHANNELCONTENTLINK_FN_7 : '删除选定热词',
CHANNELCONTENTLINK_FN_8 : '导入系统热词',
CHANNELCONTENTLINK_FN_9 : '导入系统热词到栏目',
CHANNELCONTENTLINK_FN_10 : '删除选定热词',
CHANNELCONTENTLINK_FN_11 : '名称',
CHANNELCONTENTLINK_FN_12 : '名称最大长度为30,不区分大小写',
CHANNELCONTENTLINK_FN_13 : '链接',
CHANNELCONTENTLINK_FN_14 : '链接格式：',
CHANNELCONTENTLINK_FN_15 : '描述',
CHANNELCONTENTLINK_FN_16 : "请选择要删除的热词",
CHANNELCONTENTLINK_FN_17 : "新建",
CHANNELCONTENTLINK_FN_18 : "删除",
CHANNELCONTENTLINK_FN_19 : "导入",
CHANNELCONTENTLINK_FN_20 : "热词列表管理",
CHANNELCONTENTLINK_FN_21 : '刷新',
CHANNELCONTENTLINK_FN_22 : '刷新列表',
CHANNELCONTENTLINK_FN_23 : "当前热词列表不支持排序",
CHANNELCONTENTLINK_FN_24 : "自动排序列表不支持手动排序",
CHANNELCONTENTLINK_FN_25 : "[热词-",
CHANNELCONTENTLINK_FN_26 : '您确定要调整热词的顺序?',
CHANNELCONTENTLINK_FN_27 : '与服务器交互时出现错误' ,
CHANNELCONTENTLINK_FN_28 : '热词',
CHANNELCONTENTLINK_FN_29 : '选择对象',
CHANNELCONTENTLINK_FN_30 : '热词名称只能由汉字、字母、数字、下划线组成！',
CHANNELCONTENTLINK_FN_31 : '新建站点热词',
CHANNELCONTENTLINK_FN_32 : '导入系统热词到站点',
CHANNELCONTENTLINK_FN_33 : '修改站点热词',
CHANNELCONTENTLINK_FN_34 : '显示站点的热词',
CHANNELCONTENTLINK_FN_35 : '隐藏站点的热词'
});

Ext.ns('wcm.domain.ChannelContentLinkMgr');
(function(){
var m_oMgr = wcm.domain.ChannelContentLinkMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.ChannelContentLinkMgr, {
add : function(event){
var oPageParams = event.getContext();
var bInChannel = event.getHost().getType() == "website" ? false : true;
Object.extend(oPageParams,{"ObjectId":0});
Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
Object.extend(oPageParams,{"ContainsSite": PageContext.params["CONTAINSSITE"] ? 1:0});
FloatPanel.open(WCMConstants.WCM6_PATH +
'channelcontentlink/channelcontentlink_add_edit.jsp?' + $toQueryStr(oPageParams),
bInChannel ? (wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词...') : (wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词'),
CMSObj.afteradd(event)
);
},
edit : function(event){
var oPageParams = event.getContext();
var bInChannel = event.getHost().getType() == "website" ? false : true;
var sId = event.getIds().join();
Object.extend(oPageParams,{"ObjectId":sId});
Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
Object.extend(oPageParams,{"ContainsSite": PageContext.params["CONTAINSSITE"] ? 1:0});
FloatPanel.open(WCMConstants.WCM6_PATH +
'channelcontentlink/channelcontentlink_add_edit.jsp?' + $toQueryStr(oPageParams), bInChannel ? (wcm.LANG.CHANNELCONTENTLINK_FN_3 || '修改栏目热词'):(wcm.LANG.CHANNELCONTENTLINK_FN_33 || '修改站点热词'), CMSObj.afteredit(event)
);
},
set : function(event){
var oPageParams = event.getContext();
var bInChannel = event.getHost().getType() == "website" ? false : true;
Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'channelcontentlink/channelcontentlink_import_list.html?' + $toQueryStr(oPageParams), wcm.LANG.CHANNELCONTENTLINK_FN_4 || '按分类导入系统热词', CMSObj.afteradd(event)
);
},
'delete' : function(event){
var oPageParams = {};
var sId = event.getIds();
var bInChannel = event.getHost().getType() == "website" ? false : true;
Object.extend(oPageParams,{"ChannelId": bInChannel ? event.getHost().getId() : 0});
Object.extend(oPageParams,{"SiteId": bInChannel ? 0:event.getHost().getId()});
Ext.Msg.confirm("您确定要删除选定的热词?", {
yes : function(){
Object.extend(oPageParams,{"ObjectId":sId,'ObjectIds':sId});
BasicDataHelper.call('wcm6_contentlink',"delete",oPageParams,true,function(){
event.getObjs().afterdelete();
});
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChannelContentLinkMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'channelcontentlink',
desc : wcm.LANG.CHANNELCONTENTLINK_FN_6 || '编辑这个热词',
title : wcm.LANG.CHANNELCONTENTLINK_FN_6 || '编辑这个热词...',
rightIndex : 13,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'E'
});
reg({
key : 'delete',
type : 'channelcontentlink',
desc : wcm.LANG.CHANNELCONTENTLINK_FN_7 || '删除选定热词',
title : wcm.LANG.CHANNELCONTENTLINK_FN_7 || '删除选定热词...',
rightIndex : 13,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'channelcontentlinkInChannel',
desc : wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词',
title : wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词...',
rightIndex : 13,
order : 3,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'add',
type : 'channelcontentlinkInSite',
desc : wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词',
title : wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词',
rightIndex : 1,
order : 3,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'set',
type : 'channelcontentlinkInChannel',
desc : wcm.LANG.CHANNELCONTENTLINK_FN_8 || '导入系统热词',
title : wcm.LANG.CHANNELCONTENTLINK_FN_9 || '导入系统热词到栏目',
rightIndex : 13,
order : 4,
fn : pageObjMgr['set']
});
reg({
key : 'set',
type : 'channelcontentlinkInSite',
desc : wcm.LANG.CHANNELCONTENTLINK_FN_8 || '导入系统热词',
title : wcm.LANG.CHANNELCONTENTLINK_FN_32 || '导入系统热词到站点',
rightIndex : 1,
order : 4,
fn : pageObjMgr['set']
});
reg({
key : 'delete',
type : 'channelcontentlinks',
desc : wcm.LANG.CHANNELCONTENTLINK_FN_10 || '删除选定热词',
title : wcm.LANG.CHANNELCONTENTLINK_FN_10 || '删除选定热词...',
rightIndex : 13,
order : 5,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.ChannelContentLinks', 'wcm.ChannelContentLink');
WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK = 'ChannelContentLink';
wcm.ChannelContentLinks = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK;
wcm.ChannelContentLinks.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChannelContentLinks, wcm.CMSObjs, {
});
wcm.ChannelContentLink = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK;
wcm.ChannelContentLink.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK, 'wcm.ChannelContentLink');
Ext.extend(wcm.ChannelContentLink, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['CHANNELCONTENTLINK'] || '热词管理';
wcm.PageOper.registerPanels({
channelcontentlinkInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
channelcontentlinkInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
channelcontentlinkInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
channelcontentlink : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_channelcontentlink&methodname=jFindbyid'
},
channelcontentlinks : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();
ValidationHelper.bindValidations([
{
renderTo : 'LINKNAME',
methods : PageContext.validExistProperty({objType : 1952046669})
}
]);

Ext.ns('wcm.domain.ChannelContentLinkMgr');
(function (){
Ext.apply(wcm.domain.ChannelContentLinkMgr, {
createFromFile : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
var params = {
parentid : hostId,
objecttype : hostType
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_create_fromfile.html?' + $toQueryStr(params),'批量创建', 
CMSObj.afteradd(event)
);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChannelContentLinkMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'createFromFile',
type : 'channelcontentlinkInChannel',
desc : '批量创建热词',
title : '批量创建热词...',
rightIndex : 13,
order : 3.1,
fn : pageObjMgr['createFromFile']
});
reg({
key : 'createFromFile',
type : 'channelcontentlinkInSite',
desc : '批量创建热词',
title : '批量创建热词...',
rightIndex : 1,
order : 3.1,
fn : pageObjMgr['createFromFile']
});
})();

