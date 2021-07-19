Ext.apply(wcm.LANG,{
MESSAGE : '短消息',
MESSAGE_LIST_UNIT : '个',
MESSAGE_LIST : '短消息列表',
MESSAGE_TITLE : '消息标题',
MESSAGE_CONTENT : '消息内容',
MESSAGE_CONFIRM : '确定',
MESSAGE_CANCEL : '取消',
MESSAGE_REFRESH : '刷新列表',
MESSAGE_CLOSE : '关闭窗口',
MESSAGE_1 : '发送在线短消息',
MESSAGE_2 : '标题',
MESSAGE_3 : '内容',
MESSAGE_4 : '发送者',
MESSAGE_5 : '发送时间',
MESSAGE_6 : '短消息列表详细信息',
MESSAGE_7 : '我的短消息列表',
MESSAGE_8 : '发送在线短消息',
MESSAGE_9 : '转发',
MESSAGE_10 : '回复',
MESSAGE_11 : '只能对一条信息进行',
MESSAGE_12 : '删除',
MESSAGE_13 : '确实要将这{0}个短消息删除吗? ',
MESSAGE_14 : '此',
MESSAGE_15 : '这',
MESSAGE_16 : '条',
MESSAGE_17 : '短消息吗？',
MESSAGE_18 : '设置为已读',
MESSAGE_19 : '收件箱中没有任何记录！',
MESSAGE_20 : '确实要清空收件箱吗？',
MESSAGE_21 : '发件箱中没有任何记录！',
MESSAGE_22 : '确实要清空发件箱吗？',
MESSAGE_23 : '请选择要',
MESSAGE_24 : '的项！',
MESSAGE_25 : '发给[',
MESSAGE_26 : '来自[',
MESSAGE_27 : '写新短消息',
MESSAGE_28 : '标记为已读',
MESSAGE_29 : '回复短消息',
MESSAGE_30 : '转发短消息',
MESSAGE_31 : '删除短消息',
MESSAGE_32 : '清空收件箱',
MESSAGE_33 : '清空已发短消息',
MESSAGE_tab_34 : '最新消息',
MESSAGE_tab_35 : '已收消息',
MESSAGE_tab_36 : '已发消息',
MESSAGE_37 : '处理文档',
MESSAGE_38 : '刷新',
MESSAGE_39 : '请选择要删除的短消息',
MESSAGE_40 : '请选择要转发的短消息',
MESSAGE_41 : '无法获取用户[<b>',
MESSAGE_42 : '</b>]的任何信息！',
MESSAGE_43 : '获取用户[<b>',
MESSAGE_44 : '</b>]的用户信息失败！',
MESSAGE_45 : '消息发送成功！',
MESSAGE_46 : '没有指定接收用户,组织或所有用户！',
MESSAGE_47 : '请填写标题！',
MESSAGE_48 : '标题最大允许长度为200个字符！',
MESSAGE_49 : '请填写正文！',
MESSAGE_50 : '正文最大允许长度为1000个字符！',
MESSAGE_51 : '请至少选择一种发送类型！',
MESSAGE_52 : '选择用户/组织',
MESSAGE_53 : '返回值不正确！',
MESSAGE_54 : '创建于[',
MESSAGE_55 : '系统消息不允许回复！',
MESSAGE_56 : '回复'
});

Ext.ns('wcm.domain.MessageMgr');
(function(){
var m_oMgr = wcm.domain.MessageMgr;
serviceId = 'wcm6_message';
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function sendMsg(_params,event,addOrEdit){
var sUrl = WCMConstants.WCM6_PATH + 'message/message_sending.html';
wcm.CrashBoarder.get('Send_Msg').show({
title : wcm.LANG['MESSAGE_8'] || '发送在线短消息',
src : sUrl,
width: '700px',
height: '500px',
params : _params,
maskable : true,
callback : function(_objId){
CMSObj[addOrEdit==1 ? 'afteradd' : 'afteredit'](event)(_objId);
}
});
}
function reSend(event, _bForward){
var sOptionDesc = _bForward ? (wcm.LANG['MESSAGE_9'] || '转发') : (wcm.LANG['MESSAGE_10'] ||'回复');
var objs = event.getObjs();
var _arIds = event.getIds();
if(!m_oMgr.checkSubmit(_arIds, sOptionDesc)) {
return;
}
if(_arIds.length != 1) {
Ext.Msg.$fail(String.format("只能对一条信息进行{0}",sOptionDesc));
return;
}
var nCurrId = _arIds[0];
var obj = objs.getAt(0);
var params = null;
params = {
uname: _bForward ? '' : obj.getPropertyAsString('crUser'),
raw_title: _bForward ? getForwardTitle(obj.getPropertyAsString('title')) 
: getReplyTitle(obj.getPropertyAsString('title')),
raw_msg: getQuoteMsg(obj.getPropertyAsString('mbody'))
};
if(params == null) {
return;
}
if(params.uname.toLowerCase() == "system"){
Ext.Msg.warn(wcm.LANG['MESSAGE_55'] || '不允许给system用户发送短消息');
return;
}
sendMsg(params,event,0);
}
function getReplyTitle(_sRawTitle){
return 'Re: ' + (_sRawTitle || '');
}
function getForwardTitle(_sRawTitle){
return 'Fw: ' + (_sRawTitle || '');
}
function getQuoteMsg(_sRawMsg){
return '<br>' + '----- Original Message -----<br>' + (_sRawMsg || '');
}
Ext.apply(wcm.domain.MessageMgr, {
newMsg : function(event){
var params = {};
sendMsg(params,event,1);
},
forward : function(event){
reSend(event, true,0);
},
reply : function(event){
reSend(event, false,0);
},
'delete' : function(event){
_arIds = event.getIds();
if(!m_oMgr.checkSubmit(_arIds, wcm.LANG['MESSAGE_12'] || '删除')) {
return;
}
var nCount = _arIds.length;
if(typeof(_arIds) == 'string') {
nCount = _arIds.split(',').length;
}
var params = {
ObjectIds: _arIds, 
drop: true
};
if (confirm(String.format(wcm.LANG.MESSAGE_13||('确实要将这{0}个短消息删除吗?'),nCount))){
BasicDataHelper.call(serviceId, 'delete', params, false, function(){
CMSObj.afterdelete(event)();
});
}
},
signAsReaded : function(event){
var _arIds = event.getIds();
if(!m_oMgr.checkSubmit(_arIds, wcm.LANG['MESSAGE_18'] || '设置为已读')) {
return;
}
BasicDataHelper.call(serviceId, 'setReadFlag', {Readed: true, ObjectIds: _arIds}, false, function(){
CMSObj.afteredit(event)();
});
},
clearInbox : function(event){
var context = event.getContext();
if(context.RecordNum <= 0){
Ext.Msg.$fail(wcm.LANG['MESSAGE_19'] || '收件箱中没有任何记录！');
return;
}
Ext.Msg.confirm((wcm.LANG['MESSAGE_20'] || '确实要清空收件箱吗？'),{
yes : function(){
BasicDataHelper.call(serviceId, 'clearInbox', null, false, function(){
$MsgCenter.$main().afteredit();
});
}
});
},
clearOutbox : function(event){
var context = event.getContext();
if(context.RecordNum <= 0){
Ext.Msg.$fail(wcm.LANG['MESSAGE_21'] || '发件箱中没有任何记录！');
return;
}
Ext.Msg.confirm((wcm.LANG['MESSAGE_22'] || '确实要清空发件箱吗？'),{
yes : function(){
BasicDataHelper.call(serviceId, 'clearOutbox', null, false, function(){
$MsgCenter.$main().afteredit();
});
}
});
},
checkSubmit : function(_arIds, _sOptionName){
if(_arIds.length == 0) {
Ext.Msg.$fail(String.format("请选择要{0}的项！",_sOptionName));
return false;
}
return true;
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MessageMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'newMsg',
type : 'myMsgListInRoot',
isHost : true,
desc : wcm.LANG['MESSAGE_27'] || '写新短消息',
rightIndex : -1,
order : 1,
fn : pageObjMgr['newMsg'],
quickKey : 'N'
});
reg({
key : 'signAsReaded',
type : 'message',
desc : wcm.LANG['MESSAGE_28'] || '标记为已读',
rightIndex : -1,
order : 2,
fn : pageObjMgr['signAsReaded'],
isVisible : function(event){
var context = event.getContext();
if(context.ReadFlag==0)return true;
return false;
}
});
reg({
key : 'reply',
type : 'message',
desc : wcm.LANG['MESSAGE_29'] || '回复短消息',
rightIndex : -1,
order : 3,
fn : pageObjMgr['reply'],
isVisible : function(event){
var context = event.getContext();
if(context.ReadFlag==1)return false;
return true;
}
});
reg({
key : 'forward',
type : 'message',
desc : wcm.LANG['MESSAGE_30'] || '转发短消息',
rightIndex : -1,
order : 4,
fn : pageObjMgr['forward']
});
reg({
key : 'deleteMsg',
type : 'message',
desc : wcm.LANG['MESSAGE_31'] || '删除短消息',
rightIndex : -1,
order : 5,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'clearInbox',
type : 'myMsgListInRoot',
isHost : true,
desc : wcm.LANG['MESSAGE_32'] || '清空收件箱',
rightIndex : -1,
order : 2,
fn : pageObjMgr['clearInbox'],
isVisible : function(event){
var context = event.getContext();
if(context.ReadFlag==2)return true;
return false;
}
});
reg({
key : 'clearOutbox',
type : 'myMsgListInRoot',
isHost : true,
desc : wcm.LANG['MESSAGE_33'] || '清空已发短消息',
rightIndex : -1,
order : 2,
fn : pageObjMgr['clearOutbox'],
isVisible : function(event){
var context = event.getContext();
if(context.ReadFlag==1)return true;
return false;
}
});
reg({
key : 'deleteMsg',
type : 'messages',
desc : wcm.LANG['MESSAGE_31'] || '删除短消息',
rightIndex : -1,
order : 5,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'signAsReaded',
type : 'messages',
desc : wcm.LANG['MESSAGE_28'] || '标记为已读',
rightIndex : -1,
order : 2,
fn : pageObjMgr['signAsReaded'],
isVisible : function(event){
var context = event.getContext();
if(context.ReadFlag==0)return true;
return false;
}
});
})();

Ext.ns('wcm.Messages', 'wcm.Message');
WCMConstants.OBJ_TYPE_MESSAGE = 'message';
wcm.Messages = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_MESSAGE;
wcm.Messages.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Messages, wcm.CMSObjs, {
});
wcm.Message = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_MESSAGE;
wcm.Message.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_MESSAGE, 'wcm.Message');
Ext.extend(wcm.Message, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['MESSAGE'] || '短消息';
var m_sDetailTemplate = [
'<span class="message_attr_value" title="{0}"><B>{3}:&nbsp</B>{0}</span>',
'<B>{4}:&nbsp;</B>{1}<br>',
'<B>{5}:</B>&nbsp;&nbsp;{2}<br>',
''
].join('');
function getContentTypeDesc(type){
if(type==605)return wcm.LANG['IFLOWCONTENT_50'] || '文档';
}
wcm.PageOper.registerPanels({
myMsgListInRoot : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', wcm.LANG['MESSAGE_LIST'] || '短消息列表'),
displayNum : 5,
detailTitle : wcm.LANG['MESSAGE_6'] ||'短消息列表详细信息'
},
myMsgList : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', wcm.LANG['MESSAGE_LIST'] || '短消息列表'),
displayNum : 5,
detailTitle : wcm.LANG['MESSAGE_6'] || '短消息列表详细信息',
detail : function(){
return wcm.LANG['MESSAGE_7'] || '我的短消息列表';
}
},
message : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_message&methodname=jFindbyid',
detail : function(cmsobjs, opt){
var obj = cmsobjs.getAt(0);
return String.format(m_sDetailTemplate, $transHtml(obj.getProperty('title')),
obj.getProperty('cruser'),
obj.getProperty('crtime'),wcm.LANG['MESSAGE_2'] || "标题",
wcm.LANG['MESSAGE_4'] ||"发送者",
wcm.LANG['MESSAGE_5'] ||"发送时间");
}
},
messages : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

