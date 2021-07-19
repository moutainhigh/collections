Ext.ns('wcm.domain.ChannelMgr');
(function(){
var m_nChannelObjType = 101;
var m_sServiceId = 'wcm6_channel';
var m_oMgr = wcm.domain.ChannelMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function edit(event, oParams){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channel/channel_add_edit.jsp?' + $toQueryStr(oParams),
title : wcm.LANG.CHANNEL_9||'新建/修改栏目',
callback : function(objId){
CMSObj[oParams["objectid"] > 0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
}
});
}
function renderMove(event, _args){
moveAsChild(_args.srcId, _args.dstId, (_args.dstType == 103), event);
}
function moveAsChild(_nSrcChnlIds, _nTargetId, _bIsSite, event){
var oPostData = {srcChannelIds : _nSrcChnlIds};
if(_bIsSite === true) {
oPostData.DstSiteId = _nTargetId;
}else{
oPostData.DstChannelId = _nTargetId;
}
getHelper().call(m_sServiceId, 'moveAsChild', oPostData, true, function(){
var objsOrHost = event.getObjsOrHost();
var items = [];
for (var i = 0; i < objsOrHost.length(); i++){
items.push({objId : objsOrHost.getAt(i).getId()});
}
var context = {dstObjectId : _nTargetId, isSite : _bIsSite};
var oCmsObjs = CMSObj.createEnumsFrom({
objType : objsOrHost.getType()
}, context);
oCmsObjs.addElement(items);
oCmsObjs.aftermove();
});
}
function renderLikecopy(event,_args){
var oPostData = {srcChannelId: _args.srcId};
if(_args.dstType == 103) {
oPostData.DstSiteId = _args.dstId;
}else{
oPostData.DstChannelId = _args.dstId;
}
var nType = _args.dstType, nDstSiteId = oPostData.DstSiteId, nDstChnlId = oPostData.DstChannelId;
ProcessBar.start(wcm.LANG.CHANNEL_8||'栏目类似创建');
getHelper().call(m_sServiceId, 'createFrom', oPostData, true, function(_trans, _json){
var bSucess = $v(_json, 'REPORTS.IS_SUCCESS');
var objectIds = $v(_json, 'REPORTS.ObjectIds.ObjectId');
ProcessBar.close();
Ext.Msg.report(_json, wcm.LANG.CHANNEL_7||'栏目类似创建结果', function(){
if(bSucess != 'true') return;
var wcmEvent = CMSObj.createEvent(event.getHost(), {
objId : _args.dstId,
objType : _args.dstType==103 ?
WCMConstants.OBJ_TYPE_WEBSITE : WCMConstants.OBJ_TYPE_CHANNELMASTER
});
CMSObj.afteradd(wcmEvent)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objectIds});
});
});
}
function resetTemplates(_nFolderId, _nObjType, _fCallBack){
getHelper().call('wcm6_template', 'impartTemplateConfig',
{objectId: _nFolderId, ObjectType:_nObjType}, true, _fCallBack);
}
function synchTemplates(_nFolderId, _nObjType, childObjIds, _fCallBack){
getHelper().call('wcm61_template', 'synTemplates',
{objectId: _nFolderId, ObjectType:_nObjType, objectIds : childObjIds}, true, _fCallBack);
}
Ext.apply(wcm.domain.ChannelMgr, {
'new' : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
edit(event, {
objectid: 0,
channelid: 0,
parentid: hostType == 101 ? hostId : 0,
siteid: hostType == 101 ? 0 : hostId
});
},
edit : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
var obj = event.getObj().getId();
edit(event, {
objectid: event.getObj().getId(),
channelid: event.getObj().getId(),
parentid: hostType == 101 ? hostId : 0,
siteid: hostType == 101 ? 0 : hostId
});
},
'import' : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
var params = {
parentid : hostId,
objecttype : hostType
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_import.html?' + $toQueryStr(params), wcm.LANG.CHANNEL_6||'栏目导入', function(){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL});
});
},
export0 : function (oPostData){
if(oPostData.ObjectIds){
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_export.html?' + $toQueryStr(oPostData)
, wcm.LANG.CHANNEL_10||'导出栏目');
}else
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_export.html?' + $toQueryStr(oPostData)
, wcm.LANG.CHANNEL_66||'导出所有栏目');
},
exportAll : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
if(event.getContext().RecordNum <= 0) {
Ext.Msg.$fail(wcm.LANG.CHANNEL_67||'没有任何要导出的栏目。');
return false;
}
var objId = hostId;
var oPostData = {};
oPostData[hostType == 101 ? 'parentChannelId' : 'parentSiteId'] = objId;
wcm.domain.ChannelMgr.export0(oPostData);
},
'export' : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
var objType = event.getObjs().getIntType();
var objIds = objType == 101 ? event.getIds().join(",") || hostId : hostId;
wcm.domain.ChannelMgr.export0({ObjectIds : objIds});
},
move : function(event){
var host = event.getHost();
var hostId = host.getId();
var hosttype = host.getIntType();
var sIds = (event.getIds().length == 0)?hostId : event.getIds();
var sUrl = WCMConstants.WCM6_PATH + 'channel/channel_select_move.html?srcId=' + sIds;
var sFolderInfo = 'folderType=' + hosttype;
sFolderInfo += '&folderId=' + hostId;
sUrl += '&' + sFolderInfo;
FloatPanel.open(sUrl, wcm.LANG.CHANNEL_5||'栏目移动',renderMove.bind(this, event));
},
likecopy : function(event){
var host = event.getHost();
var hostId = host.getId();
var sSrcId = (event.getIds().length == 0)?hostId : event.getIds();
var sSiteType = event.getContext().params["SITETYPE"];
var oParams = {srcId : sSrcId, siteType : sSiteType};
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_select_likecopy.jsp?' + $toQueryStr(oParams) , wcm.LANG.CHANNEL_LIKECOPY_2 || '类似创建',renderLikecopy.bind(this,event));
},
trash : function(event){
var hostId = event.getHost().getId();
var sIds = (event.getIds().length == 0)?hostId : event.getIds();
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
var sCon = "";
if(nCount == 1){
sCon = wcm.LANG.CHANNEL_12||"确实要将此栏目放入回收站吗?";
}else{
sCon = String.format(wcm.LANG.CHANNEL_13||("确实要将这 {0} 个栏目放入回收站吗?"),nCount);
}
getHelper().call('wcm61_special', 'findByChnlIds', {ChannelIds : sIds}, true, function(transport, _json){
try{
var oSpecials = $a(_json, "Specials.Special");
if(!oSpecials[0]){
if (confirm(sCon)){
ProcessBar.start(wcm.LANG.CHANNEL_4||'删除栏目');
getHelper().call(m_sServiceId, 'delete', {ObjectIds: sIds, drop: false}, true, function(){
ProcessBar.close();
window.setTimeout(function(){
event.getObjsOrHost().afterdelete();
}, 500);
});
}
}else {
alert("包含与专题对应的栏目，这样的栏目不能直接被删除，请到专题管理页面删除对应专题！");
}
}catch(e){
alert(e);
}
});
},
commentmgr : function(event){
var hostId = event.getHost().getId();
var sIds = (event.getIds().length == 0)?hostId : event.getIds();
var oParams = {
ChannelId : sIds
}
var sUrl = WCMConstants.WCM_ROOTPATH + 'comment/comment_mgr.jsp?' + $toQueryStr(oParams);
var sWinName = 'comment_name';
$openMaxWin(sUrl, sWinName);
},
chnlpositionset : function(event){
var oPageParams = event.getContext();
var channelId = 0;
if(event.getObjs().getAt(0)==null){
channelId = event.getHost().getId();
}
else {
channelId = event.getObjs().getAt(0).getId();
}
Object.extend(oPageParams,{"ChannelId":channelId});
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_position_set.jsp?' + $toQueryStr(oPageParams),
wcm.LANG.CHANNEL_48||'栏目-调整顺序', CMSObj.afteredit(event));
},
synTemplates : function(event){
var objId = event.getObj().getId();
var params = {
objType:m_nChannelObjType,
objId:objId,
close : 0,
ExcludeTop : 1,
ExcludeInfoview : 0,
ExcludeLink : 1
};
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channel/object_select.html',
dialogArguments : params,
title : wcm.LANG.CHANNEL_64||'选择栏目',
callback : function(args){
if(args.allChildren){
var sHtml = wcm.LANG.CHANNEL_11||"确实要同步模板到子栏目吗？<br><span style=\'color:red;font:16px;\'>注意：<br>该操作会覆盖更改所有子栏目的模板设置！</span>";
Ext.Msg.confirm(sHtml, {
ok : function(){
FloatPanel.close();
resetTemplates(objId, m_nChannelObjType);
}
});
}else{
FloatPanel.close();
synchTemplates(objId, m_nChannelObjType, args.ids.join());
}
}
});
},
preview : function(event){
var hostId = event.getHost().getId();
var aObjIds = (event.getIds().length == 0)?hostId : event.getIds().join(",");
wcm.domain.PublishAndPreviewMgr.preview(aObjIds, m_nChannelObjType);
},
publish : function(event, _sServiceId){
var hostId = event.getHost().getId();
var aObjIds = (event.getIds().length == 0)?hostId : event.getIds().join(",");
wcm.domain.PublishAndPreviewMgr.publish(aObjIds, m_nChannelObjType, _sServiceId);
},
increasingpublish : function(event){
m_oMgr.publish(event, 'increasingPublish');
},
increasepublish : function(event){
m_oMgr.increasingpublish(event);
},
fullypublish : function(event){
m_oMgr.publish(event, 'fullyPublish');
},
refreshpublish : function(event){
m_oMgr.publish(event, 'refreshPublish');
},
solopublish : function(event){
m_oMgr.publish(event, 'soloPublish');
},
recallpublish : function(event){
var sHtml = String.format("确实要{0}撤销发布{1}这个栏目么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
m_oMgr.publish(event, 'recallPublish');
}
})
},
createFromFile : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
var params = {
parentid : hostId,
objecttype : hostType
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_create_fromfile.html?' + $toQueryStr(params), '批量创建栏目', function(){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL});
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChannelMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var objs = event.getObjs();
var context = event.getContext();
if(objs.length() > 0){
var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
}else{
var chnlTypes = context.get('ChannelType') || 0;
}
if(!Array.isArray(chnlTypes)){
chnlTypes = [chnlTypes];
}
var hideChnlTypes = [1,2,11];
for (var i = 0; i < chnlTypes.length; i++){
if(hideChnlTypes.include(chnlTypes[i])) return false;
}
return true;
}
var fnIsVisible2 = function(event){
var objs = event.getObjs();
var context = event.getContext();
if(objs.length() > 0){
var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
}else{
var chnlTypes = context.get('ChannelType') || 0;
}
if(!Array.isArray(chnlTypes)){
chnlTypes = [chnlTypes];
}
var hideChnlTypes = [1,2,11,13];
for (var i = 0; i < chnlTypes.length; i++){
if(hideChnlTypes.include(chnlTypes[i])) return false;
}
return true;
}
var fnIsVisible3 = function(event){
var objs = event.getObjs();
var context = event.getContext();
if(context.params.SITETYPE && context.params.SITETYPE.indexOf("4") != -1)
return false;
if(objs.length() > 0){
var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
}else{
var chnlTypes = context.get('ChannelType') || 0;
}
if(!Array.isArray(chnlTypes)){
chnlTypes = [chnlTypes];
}
var hideChnlTypes = [1,2,11,13];
for (var i = 0; i < chnlTypes.length; i++){
if(hideChnlTypes.include(chnlTypes[i])) return false;
}
return true;
}
reg({
key : 'edit',
type : 'channel',
desc : wcm.LANG.CHANNEL_EDIT || '修改这个栏目',
title : wcm.LANG.CHANNEL_EDIT_TITLE || '修改栏目的基本属性和发布设置等相关信息',
rightIndex : 13,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'move',
type : 'channel',
desc : wcm.LANG.CHANNEL_MOVE || '移动这个栏目',
title : wcm.LANG.CHANNEL_MOVE_TITLE || '移动这个栏目到指定的栏目',
rightIndex : 12,
order : 2,
fn : pageObjMgr['move']
});
reg({
key : 'preview',
type : 'channel',
desc : wcm.LANG.CHANNEL_PREVIEW || '预览这个栏目',
title : wcm.LANG.CHANNEL_PREVIEW_TITLE || '重新生成并打开这个栏目的预览页面',
rightIndex : 15,
order : 4,
fn : pageObjMgr['preview'],
isVisible : fnIsVisible,
quickKey : 'R'
});
reg({
key : 'increasingpublish',
type : 'channel',
desc : wcm.LANG.CHANNEL_ADDPUB || '增量发布这个栏目',
title : wcm.LANG.CHANNEL_ADDPUB_TITLE || '发布栏目的首页，并且发布所有处于可发布状态的文档',
rightIndex : 17,
order : 5,
fn : pageObjMgr['increasingpublish'],
isVisible : fnIsVisible,
quickKey : 'P'
});
reg({
key : 'solopublish',
type : 'channel',
desc : wcm.LANG.CHANNEL_SOLOPUB || '仅发布此栏目的首页',
title : wcm.LANG.CHANNEL_SOLOPUB_TITLE || '重新生成并发布当前栏目的首页',
rightIndex : 17,
order : 6,
fn : pageObjMgr['solopublish'],
isVisible : fnIsVisible
});
reg({
key : 'export',
type : 'channel',
desc : wcm.LANG.CHANNEL_EXPORT || '导出这个栏目',
title : wcm.LANG.CHANNEL_EXPORT_SEL || '导出当前选中的栏目（提供下载的zip或者xml文件）',
rightIndex : 13,
order : 7,
fn : pageObjMgr['export']
});
reg({
key : 'synTemplates',
type : 'channel',
desc : wcm.LANG.CHANNEL_SYNCH || '同步模板到子栏目',
title : wcm.LANG.CHANNEL_SYNCH_TITLE || '将当前栏目的模板应用到其子栏目上',
rightIndex : 13,
order : 8,
fn : pageObjMgr['synTemplates'],
isVisible : fnIsVisible2
});
reg({
key : 'seperate',
type : 'channel',
desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
rightIndex : -1,
order : 9,
fn : pageObjMgr['seperate']
});
reg({
key : 'fullypublish',
type : 'channel',
desc : wcm.LANG.CHANNEL_FULLPUB || '完全发布这个栏目',
title : wcm.LANG.CHANNEL_FULLPUB_TITLE || '重新生成并发布当前栏目下的的所有文件',
rightIndex : 16,
order : 10,
fn : pageObjMgr['fullypublish'],
isVisible : fnIsVisible
});
reg({
key : 'refreshpublish',
type : 'channel',
desc : wcm.LANG.CHANNEL_REFRESHPUB || '更新发布这个栏目',
title : wcm.LANG.CHANNEL_REFRESHPUB_TITLE || '更新当前栏目和其相关栏目的首页',
rightIndex : 17,
order : 11,
fn : pageObjMgr['refreshpublish'],
isVisible : fnIsVisible
});
reg({
key : 'recallpublish',
type : 'channel',
desc : wcm.LANG.CHANNEL_CANCELPUB || '撤销发布这个栏目',
title : wcm.LANG.CHANNEL_CANCELPUB_PUBED || '撤回已发布目录或页面',
rightIndex : 16,
order : 12,
fn : pageObjMgr['recallpublish'],
isVisible : fnIsVisible
});
reg({
key : 'seperate',
type : 'channel',
desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
rightIndex : -1,
order : 13,
fn : pageObjMgr['seperate']
});
reg({
key : 'likecopy',
type : 'channel',
desc : wcm.LANG.CHANNEL_LIKECOPY || '类似创建',
title : wcm.LANG.CHANNEL_LIKECOPY_TITLE || '创建相似的栏目（唯一标识和存放位置不同）',
rightIndex : 13,
order : 14,
fn : pageObjMgr['likecopy']
});
reg({
key : 'docpositionset',
type : 'channel',
desc : wcm.LANG.CHANNEL_DOCPOSITIONSET || '调整顺序',
title : wcm.LANG.CHANNEL_DOCPOSITIONSET_TITLE || '调整当前栏目在站点中的顺序',
isVisible : fnIsVisible,
rightIndex : 13,
order : 35,
fn : pageObjMgr['chnlpositionset']
});
reg({
key : 'commentmgr',
type : 'channel',
desc : wcm.LANG.CHANNEL_COMMENT || '管理评论',
title : wcm.LANG.CHANNEL_COMMENT_TITLE || '管理当前栏目下的所有评论',
rightIndex : 8,
order : 16,
fn : pageObjMgr['commentmgr'],
isVisible : function(event){
try{
return $MsgCenter.getActualTop().g_IsRegister['comment'];
}catch(err){
return false;
}
}
});
reg({
key : 'new',
type : 'websiteHost',
desc : wcm.LANG.CHANNEL_NEW || '新建一个栏目',
title : wcm.LANG.CHANNEL_NEW_INSITE || '在当前站点下创建一个新栏目',
rightIndex : 11,
order : 258,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'websiteHost',
desc : wcm.LANG.CHANNEL_IMPORT || '导入栏目',
title : wcm.LANG.CHANNEL_IMPORT_INSITE || '在当前站点中导入一个子栏目（从zip文件中）',
rightIndex : 11,
order : 259,
fn : pageObjMgr['import']
});
reg({
key : 'export',
type : 'websiteHost',
desc : wcm.LANG.CHANNEL_EXPORTALL || '导出所有子栏目',
title : wcm.LANG.CHANNEL_EXPORTALL_INSITE || '导出当前站点下的所有子栏目（提供下载的zip或者xml文件）',
rightIndex : 13,
order : 260,
fn : pageObjMgr['exportAll']
});
reg({
key : 'new',
type : 'channelHost',
desc : wcm.LANG.CHANNEL_NEW || '新建一个栏目',
title : wcm.LANG.CHANNEL_NEW_INCHANNEL ||'在当前栏目中新建一个子栏目',
rightIndex : 11,
order : 18,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'channelHost',
desc : wcm.LANG.CHANNEL_IMPORT || '导入栏目',
title : wcm.LANG.CHANNEL_IMPORT_INCHANNEL || '在当前栏目中导入一个子栏目（从zip文件中）',
rightIndex : 11,
order : 19,
fn : pageObjMgr['import']
});
reg({
key : 'export',
type : 'channelHost',
desc : wcm.LANG.CHANNEL_EXPORTALL || '导出所有子栏目',
title : wcm.LANG.CHANNEL_EXPORTALL_INCHANNEL || '导出当前栏目下的所有子栏目（提供下载的zip或者xml文件）',
rightIndex : 13,
order : 20,
fn : pageObjMgr['exportAll'],
isVisible : function(event){
var hostchnl = event.getHost();
if(hostchnl.channelType && hostchnl.channelType==13)return false;
return true;
}
});
reg({
key : 'edit',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_EDIT_NOW || '修改当前栏目',
title : wcm.LANG.CHANNEL_EDIT_NOW_TITLE || '修改当前栏目的基本属性和发布设置等相关信息',
rightIndex : 13,
order : 21,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'move',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_MOVE_NOW || '移动当前栏目',
title : wcm.LANG.CHANNEL_MOVE_NOW_TITLE || '移动当前栏目到指定的位置',
rightIndex : 12,
order : 22,
fn : pageObjMgr['move']
});
reg({
key : 'trash',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
title : wcm.LANG.CHANNEL_TRASH_TITLE || '将栏目放入回收站',
rightIndex : 12,
order : 23,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'preview',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_PREVIEW_NOW || '预览当前栏目',
title : wcm.LANG.CHANNEL_PREVIEW_NOWPUB || '预览当前栏目的发布效果',
rightIndex : 15,
order : 24,
fn : pageObjMgr['preview'],
isVisible : fnIsVisible,
quickKey : 'R'
});
reg({
key : 'increasingpublish',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_ADDPUB_NOW || '增量发布当前栏目',
title : wcm.LANG.CHANNEL_ADDPUB_NOW_TITLE || '发布当前栏目和其子栏目的首页，并且发布该栏目下所有可发布状态的文档',
rightIndex : 17,
order : 25,
fn : pageObjMgr['increasingpublish'],
isVisible : fnIsVisible,
quickKey : 'P'
});
reg({
key : 'solopublish',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_SOLOPUB_NOW || '仅发布当前栏目首页',
title : wcm.LANG.CHANNEL_SOLOPUB_NOW_TITLE || '重新生成并发布当前栏目的首页',
rightIndex : 17,
order : 26,
fn : pageObjMgr['solopublish'],
isVisible : fnIsVisible
});
reg({
key : 'export',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_EXPORT_NOW || '导出当前栏目',
title : wcm.LANG.CHANNEL_EXPORT_NOW_INFO || '导出当前栏目（提供下载的zip或者xml文件）',
rightIndex : 13,
order : 27,
fn : pageObjMgr['export']
});
reg({
key : 'synTemplates',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_SYNCH || '同步模板到子栏目',
title : wcm.LANG.CHANNEL_SYNCH_TITLE || '将当前栏目的模板应用到其子栏目上',
rightIndex : 13,
order : 28,
fn : pageObjMgr['synTemplates'],
isVisible : fnIsVisible2
});
reg({
key : 'seperate',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
rightIndex : -1,
order : 29,
fn : pageObjMgr['seperate']
});
reg({
key : 'fullypublish',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_FULLPUB_NOW || '完全发布当前栏目',
title : wcm.LANG.CHANNEL_FULLPUB_NOW_TITLE || '重新生成和发布当前栏目下的所有文档',
rightIndex : 16,
order : 30,
fn : pageObjMgr['fullypublish'],
isVisible : fnIsVisible
});
reg({
key : 'refreshpublish',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_REFRESHPUB_NOW || '更新发布当前栏目',
title : wcm.LANG.CHANNEL_REFRESHPUB_SEL || '仅重新生成当前栏目的首页以及相关的概览页面',
rightIndex : 17,
order : 31,
fn : pageObjMgr['refreshpublish'],
isVisible : fnIsVisible
});
reg({
key : 'recallpublish',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_CANCELPUB_NOW || '撤销发布当前栏目',
title : wcm.LANG.CHANNEL_CANCELPUB_PUBED || '撤回已发布目录或页面',
rightIndex : 16,
rightIndex : 16,
order : 32,
fn : pageObjMgr['recallpublish'],
isVisible : fnIsVisible
});
reg({
key : 'seperate',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
rightIndex : -1,
order : 33,
fn : pageObjMgr['seperate']
});
reg({
key : 'likecopy',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_LIKECOPY || '类似创建',
title : wcm.LANG.CHANNEL_LIKECOPY_TITLE || '创建相似的栏目（唯一标识和存放位置不同）',
rightIndex : 13,
order : 34,
fn : pageObjMgr['likecopy']
});
reg({
key : 'docpositionset',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_DOCPOSITIONSET || '调整顺序',
title : wcm.LANG.CHANNEL_DOCPOSITIONSET_TITLE || '调整当前栏目在站点中的顺序',
isVisible : fnIsVisible,
rightIndex : 13,
order : 35,
fn : pageObjMgr['chnlpositionset']
});
reg({
key : 'seperate',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
rightIndex : -1,
order : 36,
fn : pageObjMgr['seperate']
});
reg({
key : 'commentmgr',
type : 'channelMaster',
desc : wcm.LANG.CHANNEL_COMMENT || '管理评论',
title : wcm.LANG.CHANNEL_COMMENT_TITLE || '管理当前栏目下的所有评论',
rightIndex : 8,
order : 37,
fn : pageObjMgr['commentmgr'],
isVisible : function(event){
try{
return $MsgCenter.getActualTop().g_IsRegister['comment'];
}catch(err){
return false;
}
}
});
reg({
key : 'move',
type : 'channels',
desc : wcm.LANG.CHANNEL_MOVESOME || '移动这些栏目',
title : wcm.LANG.CHANNEL_MOVESOME_TITLE || '移动这些栏目到指定的栏目',
rightIndex : 12,
order : 39,
fn : pageObjMgr['move']
});
reg({
key : 'increasingpublish',
type : 'channels',
desc : wcm.LANG.CHANNEL_ADDPUBSOME || '增量发布这些栏目',
title : wcm.LANG.CHANNEL_ADDPUBSOME_TITLE ||'发布选中的栏目的首页，并且发布所有可发布状态的文档',
rightIndex : 17,
order : 40,
fn : pageObjMgr['increasingpublish'],
isVisible : fnIsVisible,
quickKey : 'P'
});
reg({
key : 'preview',
type : 'channels',
desc : wcm.LANG.CHANNEL_PREVIEWSOME || '预览这些栏目',
title : wcm.LANG.CHANNEL_PREVIEWSOME_SEL || '预览当前选中栏目的发布效果',
rightIndex : 15,
order : 41,
fn : pageObjMgr['preview'],
isVisible : fnIsVisible,
quickKey : 'R'
});
reg({
key : 'solopublish',
type : 'channels',
desc : wcm.LANG.CHANNEL_SOLOPUBSOME || '仅发布这些栏目的首页',
title : wcm.LANG.CHANNEL_SOLOPUBSOME_TITLE || '重新生成并发布选中栏目的首页',
rightIndex : 17,
order : 42,
fn : pageObjMgr['solopublish'],
isVisible : fnIsVisible
});
reg({
key : 'export',
type : 'channels',
desc : wcm.LANG.CHANNEL_EXPORTPUBSOME || '导出这些栏目',
title : wcm.LANG.CHANNEL_EXPORT_SEL || '导出当前选中的栏目（提供下载的zip或者xml文件）',
rightIndex : 13,
order : 43,
fn : pageObjMgr['export']
});
reg({
key : 'seperate',
type : 'channels',
desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
rightIndex : -1,
order : 44,
fn : pageObjMgr['seperate']
});
reg({
key : 'fullypublish',
type : 'channels',
desc : wcm.LANG.CHANNEL_FULLPUBSOME || '完全发布这些栏目',
title : wcm.LANG.CHANNEL_FULLPUBSOME_TITLE || '重新生成并发布选中栏目下的所有文件',
rightIndex : 16,
order : 45,
fn : pageObjMgr['fullypublish'],
isVisible : fnIsVisible
});
reg({
key : 'refreshpublish',
type : 'channels',
desc : wcm.LANG.CHANNEL_REFRESHPUBSOME || '更新发布这些栏目',
title : wcm.LANG.CHANNEL_REFRESHPUBSOME_TITLE||'重新生成并发布选中栏目和与其相关栏目的首页',
rightIndex : 17,
order : 46,
fn : pageObjMgr['refreshpublish'],
isVisible : fnIsVisible
});
reg({
key : 'recallpublish',
type : 'channels',
desc : wcm.LANG.CHANNEL_CANCELPUBSOME || '撤销发布这些栏目',
title : wcm.LANG.CHANNEL_CANCELPUBSOME_TITLE || '撤销选中栏目的发布操作',
rightIndex : 16,
order : 47,
fn : pageObjMgr['recallpublish'],
isVisible : fnIsVisible
});
reg({
key : 'trash',
type : 'channels',
desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
title : wcm.LANG.CHANNEL_TRASH_TITLE || '将这个栏目放入回收站',
rightIndex : 12,
order : 48,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'createFromFile',
type : 'channelHost',
desc : wcm.LANG.CHANNEL_CREATE_FROMFILE|| '批量创建栏目',
title : wcm.LANG.CHANNEL_CREATE_FROMFILE_TITLE || '从文件批量创建栏目',
rightIndex : 11,
order : 22,
fn : pageObjMgr['createFromFile']
});
reg({
key : 'createFromFile',
type : 'websiteHost',
desc : wcm.LANG.CHANNEL_CREATE_FROMFILE|| '批量创建栏目',
title : wcm.LANG.CHANNEL_CREATE_FROMFILE_TITLE || '从文件批量创建栏目',
rightIndex : 11,
order : 260,
fn : pageObjMgr['createFromFile']
});
reg({
key : 'trash',
type : 'channel',
desc : wcm.LANG.CHANNEL_TRASH || '删除栏目',
title : wcm.LANG.CHANNEL_TRASH_TITLE || '将这个栏目放入回收站',
rightIndex : 12,
order : 213,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

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

Ext.ns('wcm.domain.ChnlrecycleMgr');
(function(){
var m_oMgr = wcm.domain.ChnlrecycleMgr ={
serviceId : 'wcm6_channel',
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.ChnlrecycleMgr, {
restoreall : function(event, operItem){
return m_oMgr.restore(event, operItem, true);
},
restore : function(event, operItem, _bRestoreAll){
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
var sClue = '';
if(_bRestoreAll){
sClue = wcm.LANG.CHNLRECYCLE_12 || '确实要还原栏目回收站中的所有栏目吗?';
}else{
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
if(nCount == 1){
sClue = wcm.LANG.CHNLRECYCLE_14 || '确实要还原此栏目吗?';
}else{
sClue = String.format(wcm.LANG.CHNLRECYCLE_15 || ('确实要还原这{0}个栏目吗?'),nCount);
}
}
if (confirm(sClue)){
Object.extend(params, {objectids: sIds});
if(_bRestoreAll) {
params.RestoreAll = true;
}
ProcessBar.start(wcm.LANG.CHNLRECYCLE_25||'还原栏目...');
m_oMgr.getHelper().call('wcm61_channel', 'restoreChannels', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
},
'delete' : function(event){
var sIds = event.getIds();
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
var sCon = "";
if(nCount == 1){
sCon = wcm.LANG.CHNLRECYCLE_16 || "确实要删除此栏目吗?";
}else{
sCon = String.format(wcm.LANG.CHNLRECYCLE_17 || ("确实要删除这{0}个栏目吗?"),nCount);
}
m_oMgr.getHelper().call('wcm61_special', 'findByChnlIds', {ChannelIds : sIds}, true, function(transport, _json){
try{
var oSpecials = $a(_json, "Specials.Special");
if(!oSpecials[0]){
if (confirm(sCon)){
ProcessBar.start(wcm.LANG.CHNLRECYCLE_26||'删除栏目..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', {ObjectIds: sIds, drop: true}, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
}else{
alert("包含与专题对应的栏目，这样的栏目不能直接被删除，请到专题管理页面删除对应专题！");
}
}catch(e){
}
});
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
if (confirm((wcm.LANG.CHNLRECYCLE_13 || '确实要删除栏目回收站中的所有栏目吗?') + (wcm.LANG.CHNLRECYCLE_27 || '如果包含有专题相关的栏目，则删除后会导致对应不能还原！'))){
ProcessBar.start(wcm.LANG.CHNLRECYCLE_26||'删除栏目..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChnlrecycleMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(!context.gridInfo) return true;
return context.gridInfo.RecordNum > 0;
};
reg({
key : 'restore',
type : 'chnlrecycle',
desc : wcm.LANG.CHNLRECYCLE_1 || '还原这个栏目',
title : wcm.LANG.CHNLRECYCLE_2 || '还原这个栏目到原位置',
rightIndex : 12,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'chnlrecycle',
desc : wcm.LANG.CHNLRECYCLE_3 || '删除这个栏目',
title : wcm.LANG.CHNLRECYCLE_3 || '删除这个栏目',
rightIndex : 12,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'chnlrecycleInChannel',
desc : wcm.LANG.CHNLRECYCLE_4 || '清空回收站',
title : wcm.LANG.CHNLRECYCLE_5 || '清空当前栏目的栏目回收站',
rightIndex : -1,
order : 3,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'chnlrecycleInChannel',
desc : wcm.LANG.CHNLRECYCLE_6 || '还原所有栏目',
title : wcm.LANG.CHNLRECYCLE_7 || '还原栏目回收站中的所有栏目',
rightIndex : -1,
order : 4,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'deleteall',
type : 'chnlrecycleInSite',
desc : wcm.LANG.CHNLRECYCLE_4 || '清空回收站',
title : wcm.LANG.CHNLRECYCLE_8 || '清空当前站点的栏目回收站',
rightIndex : -1,
order : 5,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'chnlrecycleInSite',
desc : wcm.LANG.CHNLRECYCLE_6 || '还原所有栏目',
title : wcm.LANG.CHNLRECYCLE_7 || '还原栏目回收站中的所有栏目',
rightIndex : -1,
order : 6,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'restore',
type : 'chnlrecycles',
desc : wcm.LANG.CHNLRECYCLE_9 || '还原这些栏目',
title : wcm.LANG.CHNLRECYCLE_10 || '还原这些栏目到原位置',
rightIndex : 12,
order : 7,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'chnlrecycles',
desc : wcm.LANG.CHNLRECYCLE_11 || '删除这些栏目',
title : wcm.LANG.CHNLRECYCLE_11 || '删除这些栏目',
rightIndex : 12,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

WCMConstants.OBJ_TYPE_CLASSINFO = 'classinfo';;
Ext.ns('wcm.domain.classinfoMgr');
(function(){
var m_oMgr = wcm.domain.classinfoMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function config(_id,_name){
var params = {objectId : _id, objectName : _name};
var url = 'classinfo/classinfo_config.html?' + $toQueryStr(params);
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : wcm.LANG.CLASSINFO_25 || '分类法维护'
});
}
Ext.apply(wcm.domain.classinfoMgr, {
"delete" : function(event){
var objsOrHost = event.getObjsOrHost();
var aIds = objsOrHost.getIds();
var nCount = aIds.length;
var sHint = (nCount == 1)? '' : nCount;
Ext.Msg.confirm( String.format("确实要将这{0}个分类法删除吗?", sHint),{
yes : function(){
getHelper().call("wcm61_classinfo", 
"deleteClassInfo", //远端方法名 
{"ObjectIds": aIds.join(",")}, //传入的参数
true, 
function(){
event.getObjsOrHost()["afterdelete"]();
}
)}
});
}, 
add : function(event){
var url = 'classinfo/classinfo_add_edit.html';
FloatPanel.open({
src : WCMConstants.WCM6_PATH + url,
title : wcm.LANG.CLASSINFO_9 || '新建一个分类法',
callback : function(objId, objName){
var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CLASSINFO};
CMSObj.createFrom(info, null)['afteradd']();
FloatPanel.close();
config.apply(window, arguments);
}
});
},
config : function(event){
var nObjectId = event.getObj().getId();
var sObjName = event.getObj().getPropertyAsString("objectName")
config(nObjectId,sObjName);
},
'import' : function(event){
var context = event.getContext();
var params = {
OwnerId: context.OwnerId,
OwnerType: context.OwnerType
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'classinfo/classinfo_import.jsp?' + $toQueryStr(params), 
wcm.LANG.CLASSINFO_13 || '导入分类法', CMSObj.afteradd(event));
},
'export' : function(event){
if(event.length()==0 && event.getObj().getId()==""){
Ext.Msg.$alert(wcm.LANG.classinfo_1001 || '必须选中至少一个分类法!');
return;
}
var oPostData = {
ClassInfoIds: event.getIds().join()||event.getObj().getId()
};
BasicDataHelper.call('wcm61_classinfo', 'exportClassInfos', oPostData, true, function(_trans, _json){
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
}
});
})();
(function(){
var pageObjMgr = wcm.domain.classinfoMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'config',
type : 'ClassInfo',
desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
rightIndex : -2,
order : 1,
fn : pageObjMgr['config'],
quickKey : ['E']
});
reg({
key : 'delete',
type : 'ClassInfo',
desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
rightIndex : -2,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'export',
type : 'ClassInfo',
desc : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
title : wcm.LANG.CLASSINFO_36 || '导出这个分类法',
rightIndex : -2,
order : 3,
fn : pageObjMgr['export']
});
reg({
key : 'config',
type : 'ClassInfoCls',
desc : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
title : wcm.LANG.CLASSINFO_11 || '维护这个分类法',
rightIndex : -2,
order : 3,
fn : pageObjMgr['config'],
quickKey : ['E']
});
reg({
key : 'delete',
type : 'ClassInfoCls',
desc : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
title : wcm.LANG.CLASSINFO_31 || '删除这个分类法',
rightIndex : -2,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'ClassInfoInRoot',
desc : '新建一个分类法',
title : '新建一个分类法...',
rightIndex : -1,
order : 5,
fn : pageObjMgr['add'],
quickKey : ['N']
});
reg({
key : 'import',
type : 'ClassInfoInRoot',
desc : '导入分类法',
title : '导入分类法...',
rightIndex : -1,
order : 6,
fn : pageObjMgr['import']
});
reg({
key : 'export',
type : 'ClassInfos',
desc : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
title : wcm.LANG.CLASSINFO_37 || '导出这些分类法',
rightIndex : -2,
order : 3,
fn : pageObjMgr['export']
});
reg({
key : 'delete',
type : 'ClassInfos',
desc : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
title : wcm.LANG.CLASSINFO_32 || '删除这些分类法',
rightIndex : -2,
order : 7,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

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

Ext.ns('wcm.domain.DocRecycleMgr');
(function(){
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
var m_oMgr = wcm.domain.DocRecycleMgr ={
serviceId : 'wcm61_viewdocument',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.DocRecycleMgr, {
view : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var pageContext = event.getContext();
var oParams = Ext.apply({
DocumentId : pageContext.docid,
ChnlDocId : event.getIds(),
FromRecycle : 1
},parseHost(event.getHost()));
$openMaxWin(WCMConstants.WCM6_PATH +
'document/document_show.jsp?' + $toQueryStr(oParams));
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
}
if(confirm(wcm.LANG.DOCRECYCLE_CONFIRM_1 || '您当前所执行的操作将彻底删除废稿箱中所有文档,您确定仍要继续清空当前废稿箱?')){
ProcessBar.start(wcm.LANG.DOCRECYLE_22 || '删除文档..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
ProcessBar.close();
$MsgCenter.getActualTop().isDeletingAll = true;
event.getObjs().afterdelete();
});
}
},
'delete' : function(event){
var sIds = event.getIds();
var hostId = event.getHost().getId();
var _params = event.getContext();
var params = {
objectids: sIds,
operation: '_delete'
}
var aIds = sIds;
if(String.isString(aIds)){
aIds = aIds.split(",");
}
if(aIds.length >= 50){
if(confirm(wcm.LANG.DOCRECYLE_27 || "确实要删除这篇/这些文档吗?")){
m_oMgr._delete0(event);
}
return;
}
m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
},
_delete0 : function(event){
ProcessBar.start(wcm.LANG.DOCRECYLE_22 || '删除文档..');
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var _params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
Object.extend(_params, {ObjectIds: sIds, drop: true});
m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', _params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
},
doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'docrecycle/document_info.html',
width:'520px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
},
restoreall : function(event, operItem){
if(confirm(wcm.LANG.DOCRECYLE_25 || '您确定要还原所有文档?')){
return m_oMgr.restore(event, operItem, true);
}
},
restore : function(event, operItem, _bRestoreAll){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
operation: '_restore',
special: true
}
if(_bRestoreAll == true) {
params['restoreAll'] = true;
Object.extend(params, {
channelids: (hostType == 101)?hostId:'',
siteids: (hostType == 103)?hostId:''
});
$MsgCenter.getActualTop().isRestoringAll = true;
}else{
Object.extend(params, {
objectids: event.getIds()
});
}
var restore = function(){
ProcessBar.start(wcm.LANG.DOCRECYLE_23 || '还原文档...');
var postData = {};
if(_bRestoreAll == true) {
postData['restoreAll'] = true;
Object.extend(postData, {
channelid: (hostType == 101)?hostId:'',
siteid: (hostType == 103)?hostId:''
});
}else{
Object.extend(postData, {
objectids: event.getIds()
});
}
m_oMgr.getHelper().call(m_oMgr.serviceId, 'restore', postData, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}.bind(this);
if(_bRestoreAll){
restore();
}else{
m_oMgr.doOptionsAfterDisplayInfo(params, restore);
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.DocRecycleMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(!context.gridInfo) return true;
return context.gridInfo.RecordNum > 0;
};
reg({
key : 'restore',
type : 'docrecycle',
desc : wcm.LANG.DOCRECYLE_1 || '还原这篇文档',
rightIndex : 33,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'docrecycle',
desc : wcm.LANG.DOCRECYLE_2 || '删除这篇文档',
rightIndex : 33,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'docrecycleInChannel',
desc : wcm.LANG.DOCRECYLE_6 || '清空废稿箱',
rightIndex : 33,
order : 3,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'docrecycleInChannel',
desc : wcm.LANG.DOCRECYLE_3 || '还原所有文档',
rightIndex : 33,
order : 4,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'deleteall',
type : 'docrecycleInSite',
desc : wcm.LANG.DOCRECYLE_6 || '清空废稿箱',
rightIndex : 33,
order : 5,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'docrecycleInSite',
desc : wcm.LANG.DOCRECYLE_3 || '还原所有文档',
rightIndex : 33,
order : 6,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'restore',
type : 'docrecycles',
desc : wcm.LANG.DOCRECYLE_4 || '还原这些文档',
rightIndex : 33,
order : 7,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'docrecycles',
desc : wcm.LANG.DOCRECYLE_5 || '删除这些文档',
rightIndex : 33,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.domain.DocumentMgr', 'wcm.domain.ChnlDocMgr');
(function(){
var m_nDocumentObjType = 605;
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
function openEditor(params){
if(params.DocumentId=='0'&&params.SiteId!=0){
FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_siteadd_step1.html?' 
+ $toQueryStr(params), wcm.LANG.DOCUMENT_PROCESS_81 || '选择要新建文档的栏目', 400, 350);
return ;
}
var iWidth = window.screen.availWidth - 12;
var iHeight = window.screen.availHeight - 30;
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params), "_blank" , sFeature);
}
function getHelper(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
function $openCentralWin(_sUrl, _sName){
var _WIN_WIDTH = window.screen.availWidth;
var _WIN_HEIGHT = window.screen.availHeight;
var y = _WIN_HEIGHT * 0.12;
var x = _WIN_HEIGHT * 0.17;
var w = _WIN_WIDTH - 2 * x;
var h = w * 0.618;
var sFeature = 'resizable=yes,top=' + y + ',left='
+ x + ',menubar =no,toolbar =no,width=' + w + ',height='
+ h + ',scrollbars=yes,location =no,status=no,titlebar=no';
var sName = _sName || "";
sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
var oWin = window.open(_sUrl, sName, sFeature);
if(oWin) oWin.focus();
}
function $openCenterWin(_sUrl, _sName, _width, _height, _sFeature){
if(!_width || !_height){
$openCentralWin(_sUrl, _sName);
return;
}
var _WIN_WIDTH = window.screen.availWidth;
var _WIN_HEIGHT = window.screen.availHeight;
var l = (_WIN_WIDTH - _width) / 2;
var t = (_WIN_HEIGHT - _height) / 2;
sFeature = "left="+l + ",top=" + t +",width=" 
+ _width + ",height=" + _height + "," + _sFeature;
var sName = _sName || "";
sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
var oWin = window.open(_sUrl, sName, sFeature);
if(oWin) oWin.focus();
}
function openQuickNew(result){
var oParams = {
DocumentId : result.DocumentId,
ChannelId : result.ChannelId,
SiteId : result.SiteId
}
var sChannelNames = result.ChannelName;
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_quicknew.html?' + $toQueryStr(oParams),
String.format(wcm.LANG.DOCUMENT_PROCESS_190 || ('文档-智能创建Office文档到栏目[{0}]'),sChannelNames[0]));
}, 10);
return false;
}
function openImport(event,result){
var oParams = {
DocumentId : result.DocumentId,
ChannelId : result.ChannelId,
SiteId : result.SiteId
}
var sChannelNames = result.ChannelName;
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH +'document/document_import.jsp?' + $toQueryStr(oParams), wcm.LANG.DOCUMENT_PROCESS_89 || '文档导入',function(objId){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:objId});
});
}, 10);
return false;
}
function openImportOffice(result){
var oParams = {
DocumentId : result.DocumentId,
ChannelId : result.ChannelId,
SiteId : result.SiteId
}
var sChannelNames = result.ChannelName;
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_importoffice.jsp?' + $toQueryStr(oParams), 
String.format(wcm.LANG.DOCUMENT_PROCESS_84 || ('文档-批量导入Office文档到栏目[{0}]'),sChannelNames[0]));
}, 10);
return false;
}
Ext.apply(wcm.domain.DocumentMgr, {
'new' : function(event){
var oParams = Ext.apply({
DocumentId : 0,
FromEditor : 1
}, parseHost(event.getHost()));
openEditor(oParams);
},
edit : function(event){
var host = event.getHost();
var oParams = {
ChnlDocId : event.getObj().getId(),
DocumentId : event.getObjs().getDocIds(),
ChannelId :event.getObj().getPropertyAsInt('currchnlid'),
SiteId :host.getType()==WCMConstants.OBJ_TYPE_WEBSITE?host.getId():0,
FromEditor : 1
};
openEditor(oParams);
},
logo : function(event){
var oParams = {
HostId : event.getObjs().getDocIds(),
HostType :605
};
$openCenterWin(WCMConstants.WCM6_PATH + 
'logo/logo_list.jsp?' + $toQueryStr(oParams),"document_logo", 900, 600, "resizable=yes");
},
quicknew : function(event){
var oParams = parseHost(event.getHost());
if(oParams.SiteId!=0){
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_siteimportoffice_step1.html?' + $toQueryStr(oParams),
wcm.LANG.DOCUMENT_PROCESS_85 || '选择要智能创建文档的栏目',openImportOffice );
return;
}
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_importoffice.jsp?' + $toQueryStr(oParams),
wcm.LANG.DOCUMENT_PROCESS_107 || '智能创建文档');
},
"import" : function(event){
var oParams = parseHost(event.getHost());
if(oParams.SiteId!=0){
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_siteimport_step1.html?' + $toQueryStr(oParams),
wcm.LANG.DOCUMENT_PROCESS_88 || '选择文档导入的目标栏目',openImport.bind(this,event));
return;
}
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_import.jsp?' + $toQueryStr(oParams),
wcm.LANG.DOCUMENT_PROCESS_89 || '文档导入',function(objId){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:objId});
});
},
exportall : function(event){
Ext.Msg.confirm(wcm.LANG.DOCUMENT_PROCESS_90 || '此操作可能需要较长时间.确实要导出所有文档吗?',{
yes : function(){
var oPostData = Ext.apply({
ExportAll: 1
}, parseHost(event.getHost()));
var context = event.getContext();
var dialogArguments = Ext.apply({}, context.get("pagecontext").params);
Ext.apply(dialogArguments,{PAGESIZE:500});
getHelper().call(m_sServiceId, "query", context.get("pagecontext").params, true,
function(_transport,_json){
Ext.apply(oPostData,{Count:_json.VIEWDOCUMENTS.NUM});
FloatPanel.open(
WCMConstants.WCM6_PATH + 'document/document_export.jsp?' + $toQueryStr(oPostData),
wcm.LANG.DOCUMENT_PROCESS_91 || '文档-导出所有文档',
null,
dialogArguments
);
}
);
}
});
},
setright : function(event){
$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=" +
m_nDocumentObjType + "&ObjId=" + event.getObjs().getDocIds(),
"document_right_set", 900, 600, "resizable=yes");
}
});
function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
var DIALOG_DOCUMENT_INFO = 'document_info_dialog';
wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
title : wcm.LANG.DOCUMENT_PROCESS_208 || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'docrecycle/document_info.html',
width:'500px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
}
function preparePostData(event){
return {
'documentids' : event.getObjs().getDocIds(),
'objectids' : event.getIds(),
'channelids' : event.getObjs().getPropertyAsInt('currchnlid')
};
}
var m_sServiceId = 'wcm61_viewdocument';
var m_nObjType = 600;
function preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid){
wcm.domain.PublishAndPreviewMgr.preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid);
}
function publish(objectids, _sMethodName, event){
_sMethodName = _sMethodName || 'publish';
var oPostData = {'ObjectIds' : objectids};
getHelper().call(m_sServiceId, _sMethodName, oPostData, true,
function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, _sMethodName, _transport, _json);
setTimeout(function(){
CMSObj.afteredit(event)();
},3000);
}
);
}
wcm.domain.ChnlDocMgr = Ext.applyIf({
changestatus : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var sId = event.getIds().join();
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
Object.extend(oPageParams,{
"DocumentId":docid,
"ObjectIds":sId,
"IsPhoto":false,
'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), wcm.LANG.DOCUMENT_PROCESS_92 || '文档-改变状态', CMSObj.afteredit(event));
},
changelevel : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
Object.extend(oPageParams,{
"ObjectIds":event.getObjs().getDocIds(),
"IsPhoto":false});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_doclevel.jsp?' + $toQueryStr(oPageParams), '文档-改变密级', CMSObj.afteredit(event));
},
preview : function(event){
var sIds = event.getIds().join();
var host = event.getHost();
var oParams = {
FolderId : host.getId() || 0,
FolderType : host.getIntType()
};
preview(sIds,m_nObjType,oParams,m_sServiceId);
},
basicpublish : function(event){
publish(event.getIds(), 'basicPublish', event);
},
detailpublish : function(event){
publish(event.getIds(), 'detailPublish', event);
},
directpublish : function(event){
var oPostData = {'ObjectIds' : event.getObjs().getDocIds(), 'ObjectType' : 605 };
getHelper().call("wcm6_publish", "directPublish", oPostData, true,
function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, "directPublish", _transport, _json);
setTimeout(function(){
CMSObj.afteredit(event)();
},3000);
}
);
},
recallpublish : function(event){
var sHtml = String.format("确定要{0}撤销发布{1}所选文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
publish(event.getIds(), 'recallPublish', event);
}
})
},
directRecallpublish : function(event){
var sHtml = String.format("确定要{0}撤销发布{1}所选文档及其所有引用文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>！');
Ext.Msg.confirm(sHtml,{
yes : function(){
publish(event.getIds(), 'recallpublishall', event);
}
})
},
copyall : function(event,operItem){
var sHtml = String.format("确定要{0}复制所有{1}文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
wcm.domain.ChnlDocMgr.copy(event, operItem, true);
}
})
},
copyEntity : function(event,bCopyall,oPostData){
ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_269 ||'执行复制文档..');
var oHelper = new com.trs.web2frame.BasicDataHelper();
var func = function(){
FloatPanel.close();
CMSObj.afteredit(event)();
}
oHelper.Call('wcm6_viewdocument',(bCopyall==true)?'copyAll':'copy',oPostData,true,
function(_transport,_json){
ProcessBar.close();
Ext.Msg.report(_json,(wcm.LANG.DOCUMENT_PROCESS_36 ||'文档复制结果'), func);
FloatPanel.hide();
},
function(_transport,_json){
$render500Err(_transport,_json);
FloatPanel.close();
}
);
},
copy : function(event,operItem,bCopyall){
var pageContext = event.getContext();
var sObjectids = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var hostChnlId = hostType == 101 ? hostId : 0;
sObjectids = (sObjectids.length!=0)?sObjectids:'0';
var channelids = event.getObjs().getPropertyAsString("currchnlid", 0);
if(!Ext.isArray(channelids)) channelids = [channelids];
var channelType = event.getObjs().getPropertyAsString("channelType", 0);
if(!Ext.isArray(channelType)) channelType = [channelType];
var bIsOneChannel = true;
var tmpChannelid = channelids[0];
for(var i=1,n=channelids.length; i<n; i++){
if(tmpChannelid!=channelids[i]){
bIsOneChannel = false;
break;
}
}
var nExcludeInfoView = 1;
for(index=0; index<channelType.length; index++){
if(channelType[index]==13){
nExcludeInfoView = 0;
break;
}
}
var args = {
IsRadio : 0,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeVirtual : 1,
ExcludeInfoView : nExcludeInfoView,
ExcludeOnlySearch : 1,
ShowOneType : 0,
MultiSites : 1,
SiteTypes : '0,4',
MultiSiteType : 0,
SelectedChannelIds : channelids.join() || hostChnlId,
NotSelect : 1,
RightIndex : 31,
canEmpty : true
};
if(bIsOneChannel){
Ext.apply(args, {
CurrChannelId : hostChnlId || tmpChannelid || 0,
ExcludeSelf : 0
});
}
getHelper().call(m_sServiceId, "query", pageContext.get("pagecontext").params, true,
function(_transport,_json){
var itemCount = 0;
if(bCopyall){
itemCount = _json.VIEWDOCUMENTS.NUM;
}
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/channel_select.html?ItemCount=' + itemCount + '&close=1',
wcm.LANG.DOCUMENT_PROCESS_93 || '文档-文档复制到...',
function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert('请选择当前文档要复制到的目标栏目!');
return false;
}
var nFromChnlId = bCopyall==true ? hostChnlId:event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
"ObjectIds" : sObjectids,
"FromChannelId" : nFromChnlId,
"ToChannelIds" : selectIds.join(',')
};
Ext.apply(oPostData, PageContext.params);
if(bCopyall){
Ext.apply(oPostData,{ITEMCOUNT:500});
}
wcm.domain.ChnlDocMgr.copyEntity(event,bCopyall,oPostData);
},
dialogArguments = args
);
});
},
moveall : function(event,operItem){
var sHtml = String.format("确定要{0}移动所有{1}文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
wcm.domain.ChnlDocMgr.move(event, operItem, true);
}
})
},
moveEntity : function(event,bMoveall,oPostData){
var func = function(){
FloatPanel.close();
CMSObj.afteredit(event)();
}
ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_270 ||'执行移动文档..');
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call('wcm6_viewdocument',(bMoveall==true)?'moveAll':'move',oPostData,true,
function(_transport,_json){
ProcessBar.close();
Ext.Msg.report(_json,(wcm.LANG.DOCUMENT_PROCESS_54 ||'文档移动结果'),func);
FloatPanel.hide();
},
function(_transport,_json){
$render500Err(_transport,_json);
FloatPanel.close();
}
);
},
move : function(event,operItem,bMoveall){
var context = event.getContext();
var sObjectids = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var hostChnlId = hostType == 101 ? hostId : 0;
sObjectids = (sObjectids.length!=0)?sObjectids:'0';
var channelids = event.getObjs().getPropertyAsString("currchnlid", 0);
if(!Ext.isArray(channelids)) channelids = [channelids];
var channelType = event.getObjs().getPropertyAsString("channelType", 0);
if(!Ext.isArray(channelType)) channelType = [channelType];
var bIsOneChannel = true;
var tmpChannelid = channelids[0];
for(var i=1,n=channelids.length; i<n; i++){
if(tmpChannelid!=channelids[i]){
bIsOneChannel = false;
break;
}
}
var nExcludeInfoView = 1;
for(index=0; index<channelType.length; index++){
if(channelType[index]==13){
nExcludeInfoView = 0;
break;
}
}
var args = {
IsRadio : 1,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeVirtual : 1,
ExcludeInfoView : nExcludeInfoView,
ExcludeOnlySearch : 1,
ShowOneType : 1,
SelectedChannelIds : channelids.join() || hostChnlId,
NotSelect : 1,
RightIndex : 31,
canEmpty : true
};
if(bIsOneChannel){
Ext.apply(args, {
CurrChannelId : hostChnlId || tmpChannelid || 0,
ExcludeSelf : 1
});
}
getHelper().call(m_sServiceId, "query", context.get("pagecontext").params, true,
function(_transport,_json){
var itemCount = 0;
if(bMoveall){
itemCount = _json.VIEWDOCUMENTS.NUM;
}
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/channel_select.html?ItemCount=' + itemCount + '&close=1',
wcm.LANG.DOCUMENT_PROCESS_94 || '文档-文档移动到...',
function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_52 ||'请选择当前文档要移动到的目标栏目!');
return false;
}
var nFromChnlId = bMoveall==true ? hostChnlId:event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
"ObjectIds" : sObjectids,
"FromChannelId" : nFromChnlId,
"ToChannelId" : selectIds.join(',')
}
Ext.apply(oPostData, PageContext.params);
if(bMoveall){
Ext.apply(oPostData,{PAGESIZE:500});
}
wcm.domain.ChnlDocMgr.moveEntity(event,bMoveall,oPostData);
},
dialogArguments = args
);
});
},
quote : function(event){
var oPostData = preparePostData(event);
var channelType = event.getObjs().getPropertyAsString("channelType", 0);
Ext.apply(oPostData,{channelType : channelType});
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_quoteto.html?' + $toQueryStr(oPostData),
wcm.LANG.DOCUMENT_PROCESS_95 || '文档-文档引用到...');
},
trash : function(event){
var oHost = parseHost(event.getHost());
var nCount = event.length();
var sHint = (nCount==1)?'':' '+nCount+' ';
var browserEvent = event.browserEvent;
var bDrop = !!(browserEvent && 
browserEvent.type=='keydown' && browserEvent.shiftKey);
var params = {
objectids: event.getIds(),
operation: bDrop ? '_forcedelete' : '_trash'
}
doOptionsAfterDisplayInfo(params, function(){
ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_123||'删除文档');
getHelper().call(m_sServiceId, 'delete',
Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
function(){
ProcessBar.close();
event.getObjs().afterdelete();
}
);
});
},
'export' : function(event){
var oPostData = preparePostData(event);
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_export.jsp?' + $toQueryStr(oPostData),
wcm.LANG.DOCUMENT_PROCESS_96 || '文档-导出文档');
},
backup : function(event){
var oPostData = {
docids: event.getObjs().getDocIds(),
ExcludeTrashed: true
};
getHelper().call('wcm6_documentBak','backup', oPostData, true,
function(_transport,_json){
Ext.Msg.report(_json,wcm.LANG.DOCUMENT_PROCESS_97 || '文档版本保存结果');
}
);
},
backupmgr : function(event){
var sDocId = event.getObjs().getDocIds();
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/docbak_list.html?DocumentId=' + sDocId,
wcm.LANG.DOCUMENT_PROCESS_98 || '文档-版本管理', CMSObj.afteredit(event));
},
docpositionset : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var objType = event.getObjs().getAt(0).objType;
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType});
var sObjType = (objType.trim()=='photo'? (wcm.LANG.PHOTO || '图片') :(wcm.LANG.DOCUMENT || '文档'));
var sTitle = String.format(wcm.LANG.DOCUMENT_PROCESS_222 || "{0}-调整顺序", sObjType);
FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams), sTitle, CMSObj.afteredit(event));
},
view : function(event){
var pageContext = event.getContext();
var host = event.getHost();
var hostType = host.getIntType();
var hostId = host.getId();
var oParams = {
DocumentId : event.getObjs().getDocIds(),
ChannelId :event.getObj().getPropertyAsInt('currchnlid'),
ChnlDocId : event.getIds(),
FromRecycle : pageContext.fromRecycle || 0
};
$openMaxWin(WCMConstants.WCM6_PATH +
'document/document_show.jsp?' + $toQueryStr(oParams));
},
commentmgr : function(event){
var oParams = Ext.apply({
DocumentId : event.getObjs().getDocIds(),
ChannelId :event.getObj().getPropertyAsInt('currchnlid'),
SiteId : 0
}, parseHost(event.getHost()));
var sUrl = WCMConstants.WCM_ROOTPATH +'comment/comment_mgr.jsp?'
+ $toQueryStr(oParams);
$openMaxWin(sUrl);
},
pasteFromTop : function(event){
wcm.domain.ChnlDocMgr._PasteInTo('copy', event);
},
quoteFromTop : function(event){
var oActualTop = $MsgCenter.getActualTop();
if(oActualTop._QuickDataCenter_!=null){
if(oActualTop._QuickDataCenter_.ChannelId == event.getHost().getId())
return false;
wcm.domain.ChnlDocMgr._PasteInTo('quote', _oPageParams);
}
}
}, wcm.domain.DocumentMgr);
Ext.apply(wcm.domain.ChnlDocMgr, {
quoteTo : function(objectids, tochannelid){
var oPostData = {
ObjectIds : objectids,
ToChannelIds : tochannelid
}
getHelper().call(m_sServiceId, 'quote', oPostData, true, 
function(transport,_json){
ReportsDialog.show(_json,wcm.LANG.DOCUMENT_PROCESS_47 || '文档引用结果',function(){
return;
});
});
},
saveorder : function(oPostData, _oCallBacks){
_oCallBacks = _oCallBacks || {};
getHelper().call(m_sServiceId, 'changeOrder', oPostData, false, 
_oCallBacks["onSuccess"], _oCallBacks["onFailure"], _oCallBacks["onFailure"]
);
},
copyToTop : function(_sDocIds, _sDocTitles, _oPageParams){
var aTop = $MsgCenter.getActualTop();
aTop._QuickDataCenter_ = {
ChnlDocIds : _sDocIds,
ChnlDocTitles : _sDocTitles,
ChannelId : _oPageParams['channelid']
};
},
cutToTop : function(_sDocIds, _sDocTitles, _oPageParams){
var aTop = $MsgCenter.getActualTop();
aTop._QuickDataCenter_ = {
IsCutting : true,
ChnlDocIds : _sDocIds,
ChnlDocTitles : _sDocTitles,
ChannelId : _oPageParams['channelid']
};
},
_PasteInTo : function(_sMethod, event){
var oActualTop = $MsgCenter.getActualTop();
if(oActualTop._QuickDataCenter_==null)return;
var caller = wcm.domain.ChnlDocMgr;
if(!caller._checkAvaliable(event))return;
var _sDocIds = oActualTop._QuickDataCenter_.ChnlDocIds;
var _sDocTitles = oActualTop._QuickDataCenter_.ChnlDocTitles;
var bIsCutting = oActualTop._QuickDataCenter_.IsCutting;
if(bIsCutting && _sMethod=='quote'){
return;
}
_sMethod = (bIsCutting && _sMethod=='copy')?'move':'copy';
var sDisplay = (_sMethod=='quote') ? (wcm.LANG.DOCUMENT_PROCESS_101 || '引用') : ((bIsCutting)?(wcm.LANG.DOCUMENT_PROCESS_102 || '移动'): (wcm.LANG.DOCUMENT_PROCESS_103 || '复制'));
var sConfirmTip = String.format(wcm.LANG.DOCUMENT_PROCESS_100 || ('您确定要{0}以下文档?\n'),sDisplay);
if(_sDocTitles){
var arrIds = _sDocIds.split(',');
var arrTitles = _sDocTitles.split(',');
for (var i = 0; i < arrIds.length; i++){
sConfirmTip += ' '+(i+1)+',[' + (wcm.LANG.DOCUMENT || '文档') + '-'+arrIds[i]+']:'+(arrTitles[i]||'')+'\n';
}
}
else{
sConfirmTip += _sDocIds;
}
if(!confirm(sConfirmTip)){
return;
}
var oPostData = null;
var nChannelId = event.getHost().getId();
if(_sMethod=='move'){
oPostData = {
"ObjectIds" : _sDocIds,
"ToChannelId" : nChannelId
}
}
else{
oPostData = {
"ObjectIds" : _sDocIds,
"ToChannelIds" : nChannelId
}
}
_sMethod = _sMethod || 'quote';
getHelper().Call(m_sServiceId, _sMethod, oPostData, true,
function(_transport,_json){
oActualTop.ReportsDialog.show(_json, String.format("文档{0}结果",sDisplay), function(){
var chnldocs = new wcm.ChnlDocs();
chnldocs.addElement({objId:0});
chnldocs.afteradd();
});
}
);
oActualTop._QuickDataCenter_ = null;
},
_checkAvaliable : function(event){
var context = event.getContext();
if(context.getHost().getType()!=WCMConstants.OBJ_TYPE_CHANNEL)return false;
if(Ext.isTrue(context.isVirtual))return false;
if(context.right && !wcm.AuthServer.hasRight(context.right, 31)){
return false;
}
return true;
},
recoverBak : function(_nVersion, _oPageParams, _fCallBack){
var nDocumentId = _oPageParams.DocumentId;
_nVersion = parseInt(_nVersion, 10);
var oPostData = {
DocumentId : nDocumentId,
Version : _nVersion
};
getHelper().call('wcm6_documentBak', 'recover', oPostData, true,
function(_transport,_json){
$timeAlert(String.format(wcm.LANG.DOCUMENT_PROCESS_105 || ("成功恢复文档[ID={0}]为版本[版本号={1}]!"),nDocumentId, _nVersion+1), 5, null, null, 2);
var documents = new wcm.Documents();
documents.addElement({objId:nDocumentId});
documents.afteredit();
if(_fCallBack)_fCallBack();
}
);
},
deleteBak : function(_nVersion, _oPageParams, _fCallBack){
var nDocumentId = _oPageParams.DocumentId;
_nVersion = parseInt(_nVersion, 10);
var oPostData = {
DocumentId : nDocumentId,
ObjectIds : _nVersion
};
getHelper().call('wcm6_documentBak','delete', oPostData,true,
function(_transport,_json){
var documentBaks = new wcm.CMSObjs({
objType : WCMConstants.OBJ_TYPE_DOCUMENTBAK
});
documentBaks.afterdelete();
if(_fCallBack)_fCallBack();
}
);
},
viewBak : function(_nVersion, _oPageParams){
var nDocumentId = _oPageParams.DocumentId;
var oPostData = {
DocumentId : nDocumentId,
Version : _nVersion
};
$openMaxWin(WCMConstants.WCM6_PATH + 
'document/document_backup_show.html?' + $toQueryStr(oPostData));
},
settopdocument : function(event){
var sDocId = event.getObjs().getDocIds();
var nChnlId = event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
channelid : nChnlId,
documentid : sDocId,
targetdocumentid : 0,
topflag : 0
};
getHelper().Call("wcm6_document", "settopdocument", oPostData, true,
function(_transport,_json){
event.getObjs().afteredit();;
});
},
editorToolBar : function(event){
var oPageParams = event.getContext();
var channelId = 0;
channelId = event.getHost().getId();
var sDialogId = "editToolbar";
wcm.CrashBoarder.get(sDialogId).show({
id : sDialogId,
title : wcm.LANG.CHANNEL_70 ||"编辑器工具栏定制",
src : "channel/eidtor_toolBar_set.jsp",
width : "800px",
height : "500px",
params : {"ChannelId": channelId},
callback : function(params){
CMSObj.afteredit(event);
}
});
},
importEditorCss : function(event){
var oPageParams = event.getContext();
var channelId = 0;
channelId = event.getHost().getId();
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_importEditorCss.jsp?ChannelId=' + channelId, wcm.LANG.CHANNEL_71 ||'样式文件导入', function(){
});
},
importEditorSiteCSS: function(event){
var oPageParams = event.getContext();
var siteId = 0;
siteId = event.getHost().getId();
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_importEditorCss.jsp?SiteId=' + siteId, wcm.LANG.CHANNEL_71 ||'样式文件导入', function(){
});
},
docFieldsSet : function(event){
var hostId = event.getHost().getId();
var params = {
ChannelId : hostId
};
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/document_field_showset.jsp?' + $toQueryStr(params), wcm.LANG.CHANNEL_74 ||'设置文档列表显示字段', function(){
CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL,objId:hostId});
});
},
docPropSet : function(event){
var hostId = event.getHost().getId();
var params = {
ChannelId : hostId
};
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/document_props_showset.jsp?' + $toQueryStr(params), wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制', function(){
CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL,objId:hostId});
});
},
trace_document : function(event){
var objId = event.getObjs().getDocIds();
$openMaxWin(WCMConstants.WCM6_PATH + 'document/trace_document.jsp?DocumentId=' + objId);
},
editAllDocuments : function(event){
var hostId = event.getHost().getId();
var params = {};
Ext.apply(params,PageContext.params);
Ext.apply(params,{
"CurrPageIndex":1,
"PageSize":-1
});
getHelper().Call("wcm61_viewdocument","query",params,true,
function(_transport,_json){
var num = _json["VIEWDOCUMENTS"]["NUM"];
var viewDocument = _json["VIEWDOCUMENTS"]["VIEWDOCUMENT"];
var chnlDocIds = [];
if(num == 0){
Ext.Msg.alert("没有需要修改的文档！");
return;
}
if(num == 1){
chnlDocIds.push(viewDocument["RECID"]);
}else if(num > 1){
for (var i = 0; i < viewDocument.length; i++){
chnlDocIds.push(viewDocument[i]["RECID"]);
}
}
wcm.domain.ChnlDocMgr.modify(event,{ChnlDocIds:chnlDocIds.join(",")});
});
},
editDocuments : function(event){
var params = {
ChnlDocIds : event.getIds().join(",")
};
wcm.domain.ChnlDocMgr.modify(event,params);
},
modify : function(event,params){
var sTitle = wcm.LANG.DOCUMENT_PROCESS_267 || '修改这篇文档属性';
if(event.getIds().length > 1){
sTitle = wcm.LANG.CHANNEL_120 || '批量修改文档属性'
}
FloatPanel.open(
WCMConstants.WCM6_PATH + 'document/documents_modify.jsp?ChannelId='+event.getHost().getId(), 
sTitle, 
function(param){
Ext.apply(param,params);
wcm.domain.ChnlDocMgr.editDocumentsEntity(event,param);
},
params
);
},
editDocumentsEntity : function(event,oPostDate){
getHelper().Call("wcm61_viewdocument", "editDocuments", oPostDate, true,
function(_transport,_json){
FloatPanel.close();
CMSObj.afteredit(event)();
},
function(_transport,_json){
$render500Err(_transport,_json);
FloatPanel.close();
});
}
});
})();
(function(){
var m_nWebSiteObjType = 103;
var pageObjMgr = wcm.domain.ChnlDocMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var host = event.getHost();
if(Ext.isTrue(host.isVirtual)){
return false;
}
return true;
};
var fnIsNotDraft = function(event){
var currObj = event.getObj();
var bDraft= currObj.getPropertyAsString("bDraft");
if("true" == bDraft){
return false;
}
return true;
}
reg({
key : 'new',
type : 'documentInSite',
desc : wcm.LANG.DOCUMENT_PROCESS_106 ||'创建一篇新文档',
title : '创建一篇新文档...',
rightIndex : 31,
order : 1,
fn : pageObjMgr['new'],
isVisible : fnIsVisible,
quickKey : 'N'
});
reg({
key : 'quicknew',
type : 'documentInSite',
desc : wcm.LANG.DOCUMENT_PROCESS_107 ||'智能创建文档',
title : '智能创建文档...',
rightIndex : 31,
order : 2,
fn : pageObjMgr['quicknew'],
isVisible : fnIsVisible
});
reg({
key : 'import',
type : 'documentInSite',
desc : wcm.LANG.DOCUMENT_PROCESS_108 ||'从外部导入文档',
title : '从外部导入文档...',
rightIndex : 31,
order : 3,
fn : pageObjMgr['import'],
isVisible : fnIsVisible
});
reg({
key : 'exportall',
type : 'documentInSite',
desc : wcm.LANG.DOCUMENT_PROCESS_109 ||'导出所有文档',
title : '导出所有文档...',
rightIndex : 34,
order : 4,
fn : pageObjMgr['exportall'],
quickKey : 'X'
});
reg({
key : 'new',
type : 'documentInChannel',
desc : wcm.LANG.DOCUMENT_PROCESS_106 ||'创建一篇新文档',
title:'创建一篇新文档...',
rightIndex : 31,
order : 1,
fn : pageObjMgr['new'],
isVisible : fnIsVisible,
quickKey : 'N'
});
reg({
key : 'quicknew',
type : 'documentInChannel',
desc : wcm.LANG.DOCUMENT_PROCESS_107 ||'智能创建一篇新文档',
title:'智能创建一篇新文档...',
rightIndex : 31,
order : 2,
fn : pageObjMgr['quicknew'],
isVisible : fnIsVisible
});
reg({
key : 'import',
type : 'documentInChannel',
desc : wcm.LANG.DOCUMENT_PROCESS_108 ||'从外部导入文档',
title:'从外部导入文档...',
rightIndex : 31,
order : 3,
fn : pageObjMgr['import'],
isVisible : fnIsVisible
});
reg({
key : 'move',
type : 'documentInChannel',
desc : wcm.LANG.DOCUMENT_PROCESS_112 ||'移动所有文档到',
title:'移动所有文档到...',
rightIndex : 56,
order : 4,
fn : pageObjMgr['moveall'],
isVisible : fnIsVisible
});
reg({
key : 'copy',
type : 'documentInChannel',
desc : wcm.LANG.DOCUMENT_PROCESS_113 ||'复制所有文档到',
title:'复制所有文档到...',
rightIndex : 57,
order : 5,
fn : pageObjMgr['copyall'],
isVisible : fnIsVisible
});
reg({
key : 'exportall',
type : 'documentInChannel',
desc : wcm.LANG.DOCUMENT_PROCESS_109 ||'导出所有文档',
title:'导出所有文档...',
rightIndex : 34,
order : 6,
fn : pageObjMgr['exportall'],
isVisible : function(event){
var context = event.getContext();
if(context.params['CHANNELTYPE'] == "11"){
return false;
}
return true;
}
});
reg({
key : 'editorToolBar',
type : 'documentInChannel',
desc : wcm.LANG.CHANNEL_72 ||'定制编辑器工具栏',
title:'定制编辑器工具栏...',
rightIndex : 13,
order : 8,
fn : pageObjMgr['editorToolBar'],
isVisible : fnIsVisible
});
reg({
key : 'docFieldsSet',
type : 'documentInChannel',
desc : wcm.LANG.CHANNEL_74 ||'设置文档列表显示字段',
title:'设置文档列表显示字段...',
rightIndex : 13,
order : 10,
fn : pageObjMgr['docFieldsSet'],
isVisible : fnIsVisible
});
reg({
key : 'docPropSet',
type : 'documentInChannel',
desc : wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制',
title:'文档编辑页面属性定制...',
rightIndex : 13,
order : 11,
fn : pageObjMgr['docPropSet'],
isVisible : fnIsVisible
});
reg({
key : 'editAllDocuments',
type : 'documentInChannel',
desc : wcm.LANG.CHANNEL_121 || '修改所有文档属性',
title:'修改所有文档属性...',
rightIndex : 32,
order : 12,
fn : pageObjMgr['editAllDocuments'],
isVisible : fnIsVisible
});
reg({
key : 'edit',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_115 ||'修改这篇文档',
title : '修改这篇文档...',
rightIndex : 32,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'preview',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_116 ||'预览这篇文档',
title : '预览这篇文档...',
rightIndex : 38,
order : 2,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'basicpublish',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_118 ||'发布这篇文档',
title : wcm.LANG.DOCUMENT_PROCESS_119 ||'发布这篇文档,生成这篇文档的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 3,
isVisible : fnIsNotDraft,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'copy',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_120 ||'复制这篇文档到',
title: '复制这篇文档到',
rightIndex : 34,
order : 4,
fn : pageObjMgr['copy'],
isVisible : function(event){
var host = event.getHost();
if(host.getIntType() != m_nWebSiteObjType){
return fnIsNotDraft(event);
}
var objs = event.getObjs();
for (var i = 0, length = objs.size(); i < length; i++){
var obj = objs.getAt(i);
if(obj.getPropertyAsInt('dockind') != 0) return false;
}
return fnIsNotDraft(event);
}
});
reg({
key : 'quote',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_121 ||'引用这篇文档到',
title: '引用这篇文档到',
rightIndex : 34,
order : 5,
isVisible : fnIsNotDraft,
fn : pageObjMgr['quote']
});
reg({
key : 'move',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_122 ||'移动这篇文档到',
title: '移动这篇文档到',
rightIndex : 33,
order : 6,
isVisible : fnIsNotDraft,
fn : pageObjMgr['move']
});
reg({
key : 'trash',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_123 ||'删除文档',
title : wcm.LANG.DOCUMENT_PROCESS_124 ||'将这篇文档放入废稿箱',
rightIndex : 33,
order : 7,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'changestatus',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_125 ||'改变这篇文档状态',
title: '改变这篇文档状态...',
rightIndex : 35,
order : 8,
isVisible : fnIsNotDraft,
fn : pageObjMgr['changestatus']
});
reg({
key : 'changelevel',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_273 || '改变这篇文档的密级',
title : '改变这篇文档的密级...',
rightIndex : 61,
order : 8,
fn : pageObjMgr['changelevel']
});
reg({
key : 'setright',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_126 ||'设置这篇文档权限',
title :'设置这篇文档权限...',
rightIndex : 61,
order : 9,
fn : pageObjMgr['setright']
});
reg({
key : 'detailpublish',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_127 ||'仅发布这篇文档细览',
title : wcm.LANG.DOCUMENT_PROCESS_128 ||'仅发布这篇文档细览,仅重新生成这篇文档的细览页面',
rightIndex : 39,
order : 10,
isVisible : fnIsNotDraft,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_239 ||'直接发布这篇文档',
title : wcm.LANG.DOCUMENT_PROCESS_240 ||'发布这篇文档,同时发布此文档的所有引用文档',
rightIndex : 39,
order : 11,
isVisible : fnIsNotDraft,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_129 ||'撤销发布这篇文档',
title : '撤销发布这篇文档...',
rightIndex : 39,
order : 12,
isVisible : fnIsNotDraft,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_254 ||'直接撤销发布这篇文档',
title : wcm.LANG.DOCUMENT_PROCESS_256 ||'撤销发布这篇文档，同步撤销这篇文档所有的引用文档',
rightIndex : 39,
order : 13,
isVisible : fnIsNotDraft,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'backup',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_131 ||'为这篇文档产生版本',
title :'为这篇文档产生版本...',
rightIndex : 32,
order : 14,
fn : pageObjMgr['backup']
});
reg({
key : 'backupmgr',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_132 ||'管理这篇文档版本',
titile : '管理这篇文档版本...',
rightIndex : 32,
order : 15,
fn : pageObjMgr['backupmgr']
});
reg({
key : 'seperate',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_134 ||'分隔线',
title : '分隔线',
rightIndex : -1,
order : 16,
fn : pageObjMgr['seperate']
});
reg({
key : 'export',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_135 ||'导出这篇文档',
title : '导出这篇文档...',
rightIndex : 34,
order : 17,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'commentmgr',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_137 ||'管理评论',
title : wcm.LANG.DOCUMENT_PROCESS_138 ||'管理文档的评论',
rightIndex : 8,
order : 18,
fn : pageObjMgr['commentmgr'],
isVisible : function(event){
try{
return $MsgCenter.getActualTop().g_IsRegister['comment'];
}catch(err){
return false;
}
}
});
reg({
key : 'docpositionset',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_139 ||'调整顺序',
title:'调整顺序',
rightIndex : 62,
order : 19,
fn : pageObjMgr['docpositionset'],
isVisible : function(event){
if(!event.getContext().get('CanSort')){
return false;
}
if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
return true;
return false;
}
});
reg({
key : 'logo',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_206 ||'文档LOGO',
title : "文档LOGO...",
rightIndex : 32,
order : 20,
fn : pageObjMgr['logo']
});
reg({
key : 'preview',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_140 ||'预览这些文档',
title : '预览这些文档...',
rightIndex : 38,
order : 1,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'basicpublish',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_142 ||'发布这些文档',
title : wcm.LANG.DOCUMENT_PROCESS_143 ||'发布这些文档,生成这些文档的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 2,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'trash',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_123 ||'删除文档',
title : wcm.LANG.DOCUMENT_PROCESS_124 ||'将这篇文档放入废稿箱',
rightIndex : 33,
order : 3,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'copy',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_144 ||'复制这些文档到',
title : "复制这些文档到...",
rightIndex : 34,
order : 4,
fn : pageObjMgr['copy'],
isVisible : function(event){
var host = event.getHost();
if(host.getIntType() != m_nWebSiteObjType) return true;
var objs = event.getObjs();
for (var i = 0, length = objs.size(); i < length; i++){
var obj = objs.getAt(i);
if(obj.getPropertyAsInt('dockind') != 0) return false;
}
return true;
}
});
reg({
key : 'move',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_145 ||'移动这些文档到',
title : "移动这些文档到",
rightIndex : 33,
order : 5,
fn : pageObjMgr['move']
});
reg({
key : 'quote',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_146 ||'引用这些文档到',
title : "引用这些文档到...",
rightIndex : 34,
order : 6,
fn : pageObjMgr['quote']
});
reg({
key : 'changestatus',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_147 ||'改变这些文档的状态',
title : '改变这些文档的状态...',
rightIndex : 35,
order : 7,
fn : pageObjMgr['changestatus']
});
reg({
key : 'changelevel',
type : 'chnldocs',
desc : '改变这些文档的密级',
title : '改变这些文档的密级...',
rightIndex : 61,
order : 7,
fn : pageObjMgr['changelevel']
});
reg({
key : 'setright',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_148 ||'设置这些文档的权限',
title : '设置这些文档的权限...',
rightIndex : 61,
order : 8,
fn : pageObjMgr['setright']
});
reg({
key : 'detailpublish',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_149 ||'仅发布这些文档细览',
title : wcm.LANG.DOCUMENT_PROCESS_150 ||'仅发布这些文档细览,仅重新生成这些文档的细览页面',
rightIndex : 39,
order : 9,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_241 ||'直接发布这些文档',
title : wcm.LANG.DOCUMENT_PROCESS_242 ||'发布这些文档，同步发布这些文档所有的引用文档',
rightIndex : 39,
order : 10,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_151 ||'撤销发布这些文档',
title : '撤销发布这些文档...',
rightIndex : 39,
order : 11,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_255 ||'直接撤销发布这些文档',
title : wcm.LANG.DOCUMENT_PROCESS_257 ||'撤销发布这些文档，同步撤销这些文档所有的引用文档',
rightIndex : 39,
order : 12,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'backup',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_153 ||'为这些文档产生版本',
title : '为这些文档产生版本...',
rightIndex : 32,
order : 13,
fn : pageObjMgr['backup']
});
reg({
key : 'seperate',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_134 ||'分隔线',
title : '分隔线',
title : '分隔线',
rightIndex : -1,
order : 14,
fn : pageObjMgr['seperate']
});
reg({
key : 'export',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_154 ||'导出这些文档',
title : '导出这些文档...',
rightIndex : 34,
order : 15,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'settopdocument',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_232 ||'取消置顶',
title : '取消置顶...',
rightIndex : 62,
order : 16,
fn : pageObjMgr['settopdocument'],
isVisible : function(event){
var docContext = event.getObj();
if(docContext.getPropertyAsString("isTopped") == "true") {
return true;
} else {
return false;
}
}
});
reg({
key : 'trace_document',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_236 ||'跟踪文档',
title : '跟踪文档...',
rightIndex : 32,
order : 17,
fn : pageObjMgr['trace_document']
});
reg({
key : 'editDocuments',
type : 'chnldocs',
desc : wcm.LANG.DOCUMENT_PROCESS_268 || '修改这些文档属性',
title : '修改这些文档属性...',
rightIndex : 32,
order : 18,
fn : pageObjMgr['editDocuments']
});
reg({
key : 'editDocuments',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_267 || '修改这篇文档属性',
title : '修改这篇文档属性...',
rightIndex : 32,
order : 18,
fn : pageObjMgr['editDocuments']
});
})();

Ext.ns('wcm.domain.FlowMgr');
(function(){
var m_oFlowMgr = wcm.domain.FlowMgr;
var serviceId = 'wcm6_process';
function __checkNoRecords(_oHostInfos){
if(_oHostInfos.num <= 0) {
Ext.Msg.$fail(wcm.LANG['FLOW_33'] || '没有任何要操作的工作流！');
return false;
}
return true;
}
function setFlowCallBack(_args){
var nFlowId = _args['FlowId'];
var nOldFlowId = _args['nOldFlowId'];
if(_args['disabled'] == true && nOldFlowId != 0){
BasicDataHelper.call('wcm6_process','disableFlowToChannel',{ObjectId:_args['ChannelId'],FlowId: 0},true,function(_transport,_json){
$MsgCenter.$main().afteredit();
});
}else if(nFlowId != nOldFlowId){
BasicDataHelper.call('wcm6_process','enableFlowToChannel',{ObjectId:_args['ChannelId'],FlowId:nFlowId},true,function(_transport,_json){
$MsgCenter.$main().afteredit();
});
}
}
function assignFlowCallBack(m_nFlowId){
return function (sSelIds){
if(sSelIds.length == 0) {
Ext.Msg.$fail(wcm.LANG['FLOW_23'] || '请选择要设置的栏目！');
return false;
}
var params = {ObjectIds:sSelIds.join(','),FlowId:m_nFlowId};
BasicDataHelper.call('wcm6_process','setChannelEmployersOfFlow',params,true,function(_transport,_json){
Ext.Msg.$timeAlert((wcm.LANG['FLOW_24'] || '设置成功！'), 3);
FloatPanel.close();
});
return false;
}
}
function _addEditCallBack(event,addOrEdit){
var arrIds = event.getIds();
var host = event.getHost();
var flowObj = event.getObjs().getAt(0);
var nLoadView = 1;
if(flowObj!=null && wcm.AuthServer.hasRight(flowObj.right, 42)){
nLoadView = 2;
}
else if(flowObj==null && wcm.AuthServer.hasRight(host.right, 41)){
nLoadView = 2;
}
if(addOrEdit==0){
var o_params = {
FlowId : 0,
LoadView : nLoadView,
OwnerType : host.getIntType(),
OwnerId : host.getId(),
ShowButtons : '0'
}
}
else {
var o_params = {
FlowId : event.getIds().join(),
LoadView : nLoadView,
OwnerType : host.getIntType(),
OwnerId : host.getId(),
ShowButtons : '0'
}
}
}
function _addEdit(event, addOrEdit, params){
var arrIds = event.getIds();
var host = event.getHost();
var flowObj = event.getObjs().getAt(0);
var nLoadView = 1;
if(flowObj!=null && wcm.AuthServer.hasRight(flowObj.right, 42)){
nLoadView = 2;
}
else if(flowObj==null && wcm.AuthServer.hasRight(host.right, 41)){
nLoadView = 2;
}
if(addOrEdit==0){
var o_params = {
FlowId : 0,
LoadView : nLoadView,
OwnerType : host.getIntType(),
OwnerId : host.getId(),
ShowButtons : '0'
}
}
else {
var o_params = {
FlowId : event.getIds().join(),
LoadView : nLoadView,
OwnerType : host.getIntType(),
OwnerId : host.getId(),
ShowButtons : '0'
}
}
Ext.apply(o_params, params||{});
wcm.Flow_AddEdit_Selector._addEditFlow(o_params, function flowAddeditCallBack(_args){
var event = this;
var fnEvent = addOrEdit==0 ? CMSObj.afteradd(event)
: CMSObj.afteredit(event);
var host = event.getHost();
if(host.getIntType()==101
&& _args['id'] > 0) {
BasicDataHelper.call('wcm6_process', 'enableFlowToChannel', {
ObjectId: host.getId(),
FlowId: _args['id']
}, false, function(_transport, _json){
fnEvent(_args['id']);
}
);
}else{
fnEvent(_args['id']);
}
}.bind(event));
}
Ext.apply(wcm.domain.FlowMgr, {
add : function(event){
_addEdit(event,0);
},
edit : function(event){
_addEdit(event,1);
},
view : function(event){
_addEdit(event,1,{readonly:true});
},
assign : function(event){
var context = event.getContext();
var currObj = event.getObjs().getAt(0);
var m_nFlowId = currObj.getId();
Object.extend(context, {FlowId : event.getIds().join()});
var params = {
IsRadio : 0,
ExcludeTop : 1,
ExcludeLink : 1,
ShowOneType : 1,
MultiSites : 0,
RightIndex : 42,
ExcludeInfoview : 0,
ExcludeOnlySearch : 1
}
BasicDataHelper.call('wcm6_process','getChannelsUseingFlow',{ObjectId:m_nFlowId,OnlyReturnIds:true,IdsValueType:0},false,function(_transport,_json){
var ids = _transport.responseText||'';
if(ids.trim().length > 0){
Object.extend(params,{SELECTEDCHANNELIDS : ids});
}
var nFlowOwnerType = currObj.getPropertyAsInt('ownerType' ,0);
var nFlowOwnerId = currObj.getPropertyAsInt('ownerId' ,0);
if(nFlowOwnerType == 103){
Object.extend(params,{MultiSites : 0,
CurrSiteId : context.siteid || nFlowOwnerId
});
}else if(nFlowOwnerType == 101){
Object.extend(params,{MultiSites : 0});
if(context.siteid)
Object.extend(params,{CurrSiteId : context.siteid});
}else{
Object.extend(params,{SiteTypes : nFlowOwnerId,CurrSiteType : context.sitetype || nFlowOwnerId});
}
var currSiteType = event.getContext().get("siteType") || 0 ;
Object.extend(params,{CurrSiteType:currSiteType,MultiSites : 0});
var currHostType = event.getHost().getProperty('objType');//获得当前工作流对象的上级对象的objType属性
if(currHostType == "channel"){
Object.extend(params,{SiteTypes:currSiteType,MultiSites : 0});
}
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'include/channel_select.html',
title : wcm.LANG['FLOW_70'] || '分配工作流',
callback : assignFlowCallBack(m_nFlowId),
dialogArguments : params
});
});
},
'import' : function(event){
var context = event.getContext();
var params = {
OwnerId: context.OwnerId,
OwnerType: context.OwnerType
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'flow/flow_import.html?' + $toQueryStr(params), 
wcm.LANG['FLOW_IMPORT'] ||'工作流导入', CMSObj.afteradd(event));
},
'export' : function(event){
if(!__checkNoRecords(event.getContext())) {
return;
}
var oPostData = {
objectIds: event.getIds().join()
};
BasicDataHelper.call(serviceId, 'exportFlows', oPostData, true, function(_trans, _json){
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
'delete' : function(event){
var o_params = {
objectids : event.getIds().join()
}
var sUrl = WCMConstants.WCM6_PATH + 'flow/workflow_employment_info.jsp';
wcm.CrashBoarder.get('workflow_info_dialog').show({
title : wcm.LANG['FLOW_32'] || '系统提示信息',
reloadable : true,
src : sUrl,
width: '450px',
height: '220px',
params : o_params,
maskable : true,
callback : function(){
var event = this;
var host = event.getHost();
BasicDataHelper.call(serviceId,'delete', Object.extend(o_params,{
'ObjectIds': event.getIds().join(), 
'drop': true
}), 
true, 
function(){
CMSObj.afterdelete(event)();
});
return false;
}.bind(event)
});
},
setemployer : function(event){
var id = event.getIds().join()|| 0;
var context = event.getContext();
var host = event.getHost();
var params = {
OwnerType: context.OwnerType,
OwnerId: context.OwnerId,
ChannelId: host.getId(),
CurrFlowId: id
};
wcm.Flow_AddEdit_Selector.selectFlow(params,setFlowCallBack);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.FlowMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'add',
type : 'flowInChannel',
isHost : true,
desc : wcm.LANG['FLOW_ADD'] || '新建工作流',
title:'新建工作流',
rightIndex : 41,
order : 2,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'setemployer',
type : 'flowInChannel',
isHost : true,
desc : wcm.LANG['FLOW_SET'] || '设置工作流',
title:'设置工作流',
rightIndex : 13,
order : 3,
fn : pageObjMgr['setemployer']
});
reg({
key : 'edit',
type : 'flow',
desc : wcm.LANG['FLOW_EDIT'] || '编辑这个工作流',
title:'编辑这个工作流',
rightIndex : 42,
order : 4,
fn : pageObjMgr['edit'],
isVisible : function(event){
var host = event.getHost();
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return false;
}
return true;
},
quickKey : 'M'
});
reg({
key : 'delete',
type : 'flow',
desc : wcm.LANG['FLOW_DELETE'] || '删除这个工作流',
title:'删除这个工作流',
rightIndex : 43,
order : 5,
fn : pageObjMgr['delete'],
isVisible : function(event){
var host = event.getHost();
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return false;
}
return true;
},
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'assign',
type : 'flow',
desc : wcm.LANG['FLOW_ASSIGN'] || '分配这个工作流到栏目',
title:'分配这个工作流到栏目',
rightIndex : 42,
order : 6,
fn : pageObjMgr['assign']
});
reg({
key : 'export',
type : 'flow',
desc : wcm.LANG['FLOW_EXPORT'] ||'导出这个工作流',
title:'导出这个工作流',
rightIndex : 44,
order : 7,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'add',
type : 'flowInSite',
isHost : true,
desc : wcm.LANG['FLOW_ADD'] || '新建工作流',
title:'新建工作流',
rightIndex : 41,
order : 8,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'flowInSite',
isHost : true,
desc : wcm.LANG['FLOW_37'] || '导入工作流',
title:'导入工作流',
rightIndex : 45,
order : 9,
fn : pageObjMgr['import'],
quickKey : 'I'
});
reg({
key : 'add',
type : 'flowInSys',
isHost : true,
desc : wcm.LANG['FLOW_ADD'] || '新建工作流',
title:'新建工作流',
rightIndex : 41,
order : 10,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'flowInSys',
isHost : true,
desc : wcm.LANG['FLOW_37'] || '导入工作流',
title:'导入工作流',
rightIndex : 45,
order : 11,
fn : pageObjMgr['import'],
quickKey : 'I'
});
reg({
key : 'delete',
type : 'flows',
desc : wcm.LANG['FLOWS_DELETE'] || '删除这些工作流',
title:'删除这些工作流',
rightIndex : 43,
order : 12,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'export',
type : 'flows',
desc : wcm.LANG['FLOWS_EXPORT'] || '导出这些工作流',
title:'导出这些工作流',
rightIndex : 44,
order : 13,
fn : pageObjMgr['export'],
quickKey : 'X'
});
})();

Ext.ns('wcm.domain.IFlowContentMgr');
(function(){
var m_oMgr = wcm.domain.IFlowContentMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function promptOption(_sTitle, _params, event){
var sTitle = _sTitle || (wcm.LANG['IFLOWCONTENT_55'] ||'处理我的工作');
var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_option.html';
wcm.CrashBoarder.get('Process_Option').show({
title : sTitle,
src : sUrl,
width: '480px',
height: '350px',
params : _params,
maskable : true,
callback : function(){
PageContext.refreshList(null, []);
}
});
}
function render(event,reSubmited){
var obj = event.getObjs().getAt(0);
var nFlowDocId = obj.getPropertyAsInt('flowDocId',0);
if(nFlowDocId==0 || nFlowDocId==null) return;
var nFlagId = obj.getPropertyAsInt('flagid',0);
if(reSubmited &&
(obj.getPropertyAsInt('worked',0) == 1 || nFlagId == 2 || nFlagId == 8)) { // 对于已经处理过的文档，不允许再重新指派
return;
}
var sEditPage = obj.getPropertyAsString('editPage');
if(!reSubmited) {
if(sEditPage && sEditPage.indexOf('infoview/infoview_document_addedit.jsp') != -1) {
$openMaxWin(WCMConstants.WCM6_PATH + "infoview/" + sEditPage + "&FlowDocId=" + nFlowDocId+"&WorklistViewType=1");
return;
}
}
var bAccepted = (obj.getPropertyAsInt('accepted', 0) != 0);
var o_params = {
FlowDocId: nFlowDocId,
title: obj.getPropertyAsString('contentTitle'),
ctype: obj.getPropertyAsInt('contentType', 0),
cid: obj.getId(),
resubmited: reSubmited || false
};
if(!reSubmited && sEditPage && sEditPage.indexOf("applyform_add_manager.jsp") > 0){//依申请公开的地址
$openMaxWin(sEditPage + "&FlowDocId=" + nFlowDocId);
return;
}
if(!reSubmited && sEditPage && sEditPage.indexOf("metaviewdata_addedit.jsp") > 0){
$openMaxWin(sEditPage + "&FlowDocId=" + nFlowDocId + "&WorklistViewType=1#tabs-process");
return;
}
if(!reSubmited && sEditPage && obj.getPropertyAsString("CanEditInFlow", 'false') == 'true' && sEditPage.indexOf("document_addedit.jsp") > 0){
$openMaxWin(WCMConstants.WCM6_PATH + "document/" + sEditPage + "&FlowDocId=" + nFlowDocId+"&WorklistViewType=1");
}else{
var sTitle = reSubmited ? (wcm.LANG['IFLOWCONTENT_56'] ||'重新指派') : (wcm.LANG['IFLOWCONTENT_57'] ||'处理文档');
var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_render.jsp';
wcm.CrashBoarder.get('Process_Render').show({
title : sTitle,
src : sUrl,
width: '600px',
height: '555px',
params : o_params,
maskable : true,
callback : function(){
CMSObj.afteredit(event)();
}
});
}
}
function reflow(_params){
var sTitle = wcm.LANG['IFLOWCONTENT_96'] || "将文档重新投入流转";
var sUrl = WCMConstants.WCM6_PATH + 'flowdoc/workflow_process_reflow.jsp';
wcm.CrashBoarder.get('Process_Reflow').show({
title : sTitle,
src : sUrl,
width: '480px',
height: '450px',
params : _params,
maskable : true,
callback : function(_args){
_startDocInFlow(_args);
}
});
}
function _startDocInFlow(_params){
var postData = {};
if(_params.reflow){
postData = _params;
}
else{
postData = {objectid: _params['ContentId']}
} 
BasicDataHelper.call('wcm6_document', 'startDocumentInFlow', postData, true, function(){
PageContext.refreshList(null, []);
});
}
Ext.apply(wcm.domain.IFlowContentMgr, {
sign : function(event){
if(event.length()==0){
Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_73'] || '必须选中至少一篇文档!');
return;
}
var ids = event.getObjs().getPropertyAsInt("flowdocid",0);
var accepteds = event.getObjs().getPropertyAsInt("accepted",0);
for(var index=0; index<accepteds.length; index++){
var accepted = accepteds[index];
if(accepted!=0){
Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_54'] || '只有尚未签收的文档才能进行签收操作！');
return;
}
}
var oPostData = {
ObjectIds: ids
};
BasicDataHelper.call('wcm6_process', 'doAccept', oPostData, true, function(_trans, _json){
CMSObj.afteredit(event)();
});
},
refuse : function(event){
if(event.length()==0){
Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_73'] || '必须选中至少一篇文档!');
return;
}
var ids = event.getObjs().getPropertyAsInt("flowdocid",0).join();
var titles = event.getObjs().getPropertyAsString("contenttitle").join();
var _params = {
ObjectIds: ids,
titles : titles,
option : 'refuse',
Action : 'refuse'
}
promptOption(wcm.LANG['IFLOWCONTENT_102'] ||'拒绝', _params,event);
},
rework : function(event){
if(event.length()==0){
Ext.Msg.$alert(wcm.LANG['IFLOWCONTENT_73'] || '必须选中至少一篇文档！');
return;
}
var ids = event.getObjs().getProperty("flowdocid").join();
var titles = event.getObjs().getProperty("contenttitle").join();
var _params = {
ObjectIds: ids,
titles : titles,
option : 'rework',
Action : 'backTo'
}
promptOption(wcm.LANG['IFLOWCONTENT_103'] ||'要求返工', _params,event);
},
dealing : function(event){
render(event,false);
},
reasign : function(event){
render(event,true);
},
cease : function(event){
if(event.length()==0)return;
var obj = event.getObjs();
var nFlowDocId = obj.getPropertyAsInt("flowdocid",0).join();
var titles = obj.getPropertyAsString("contenttitle").join();
var nIsEnd = obj.getPropertyAsInt("isend",0).join();
if(nIsEnd == 1){
reflow({
ContentId: obj.getIds(),
ContentType : obj.getPropertyAsInt("contenttype",0),
doctitle : obj.getAt(0).getPropertyAsString("contenttitle")
});
return;
}
var params = {
ObjectIds: nFlowDocId,
flowid: nFlowDocId,
titles:titles,
docs: [
{id: nFlowDocId, title:titles}
],
ctype: obj.getPropertyAsInt("contenttype",0),
cid: obj.getIds(),
option: 'cease'
};
promptOption(wcm.LANG['IFLOWCONTENT_3'] || '结束文档流转', params,event);
},
edit : function(event){
if(event.length()==0)return;
var editpage = event.getObjs().getProperty("editPage")[0];
var nFlowDocId = event.getObjs().getProperty("flowdocid");
if(editpage.indexOf("../photo/photodoc_edit.html?") == 0){
FloatPanel.open(WCMConstants.WCM6_PATH + "photo/photodoc_edit.jsp"+editpage.substr(editpage.indexOf("?")).replace("DocumentId","DocId") + "&FlowDocId=" + nFlowDocId, wcm.LANG['IFLOWCONTENT_59'] || "编辑图片信息",680,350);
}else if(editpage.indexOf("photo/photo_upload.jsp") >= 0){
FloatPanel.open(WCMConstants.WCM6_PATH + "photo/photodoc_edit.jsp"+editpage.substr(editpage.indexOf("?")).replace("DocumentId","DocId") + "&FlowDocId=" + nFlowDocId, wcm.LANG['IFLOWCONTENT_59'] || "编辑图片信息",680,350);
}
else if(editpage.indexOf("../video/video_addedit.jsp?") == 0){
window.open(editpage + "&FlowDocId=" + nFlowDocId,'_blank', 'width=800,height=560,location=no');
}else{
$openMaxWin(WCMConstants.WCM6_PATH + "document/" + editpage + "&FlowDocId=" + nFlowDocId);
}
},
deletedoc : function(event){
if(event.length()==0) return;
var nFlowDocId = event.getObjs().getPropertyAsInt("flowdocid",0);
Ext.Msg.confirm(wcm.LANG['IFLOWCONTENT_60'] || '确认是要删除当前流转的文档吗？', {
yes : function(){
var objs = event.getObjs();
var sErrorMsg = "";
for(var i=0 ;i<objs.size();i++){
var obj = event.getObjs().getAt(i);
var nDocId = obj.getPropertyAsInt("docid",0);
var flowdocid = obj.getPropertyAsInt("flowdocid",0);
var delPage = obj.getProperty("deletepage");
if(!flowdocid || !delPage) return;
if(delPage && delPage.indexOf('center.do') == -1 && delPage.indexOf('.jsp') == -1) {
delPage = BasicDataHelper['WebService'] + '?' + delPage;
}
var sActionUrl = delPage + "&FlowDocId=" + flowdocid;
var ajaxRequest = new Ajax.Request(
sActionUrl,
{
onSuccess: function(){
if(i == event.length()){
CMSObj.afterdelete(event)();
}
},
onFailure :function(){
if(!sErrorMsg){
sErrorMsg +="删除id为：[";
}
sErrorMsg += nDocId ;
},
asyn:true
}
);
if(sErrorMsg && (i ==event.length())){
if(sErrorMsg.charAt(length -1) == ","){
sErrorMsg = sErrorMsg.subString(sErrorMsg,sErrorMsg.length-1);
}
sErrorMsg +="]的文档失败！！！";
}
}
},
no : function(){
return;
}
});
},
show : function(event){
if(event.length()==0)return;
var obj = event.getObjs().getAt(0);
var showPage = obj.getPropertyAsString("showpage");
if(showPage.indexOf("../video/player.jsp?") == 0){
window.open('../video/player.jsp?docId=' + obj.getId() , '_blank', 'width=800,height=560,location=yes');
}else{
var ix = showPage.indexOf('../document/document_detail_show.html');
if(ix == 0){
var len = '../document/document_detail_show.html'.length;
showPage = '../document/document_detail.jsp' + showPage.substr(len);
}
var nFlowDocId = obj.getPropertyAsInt("trueFlowDocId",0) > 0 ? obj.getPropertyAsInt("trueFlowDocId",0) : obj.getPropertyAsInt("flowdocid",0);
if(showPage.indexOf('?') != -1){
showPage += "&FlowDocId=" + nFlowDocId;
}else{
showPage += "?FlowDocId=" + nFlowDocId;
}
$openMaxWin(showPage);
}
},
detailPublish : function(event){
var objs = event.getObjs();
BasicDataHelper.call('wcm6_publish', 'detailPublish', {
objectids : objs.getIds(),
objecttype : objs.getPropertyAsInt("contentType",0),
FlowDocId : objs.getPropertyAsInt("flowDocId",0)
}, true, function(_trans, _json){
Ext.Msg.$timeAlert(wcm.LANG['IFLOWCONTENT_61'] || '已将您的发布任务提交到后台！', 3);
})
}
});
})();
(function(){
var pageObjMgr = wcm.domain.IFlowContentMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'show',
type : 'iflowcontent',
desc : wcm.LANG['IFLOWCONTENT_68'] || '查看这篇文档',
rightIndex : -1,
order : 5,
fn : pageObjMgr['show']
});
reg({
key : 'edit',
type : 'iflowcontent',
desc : wcm.LANG['IFLOWCONTENT_69'] || '编辑这篇文档',
rightIndex : 0,
order : 6,
fn : pageObjMgr['edit'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
},
quickKey : 'M'
});
reg({
key : 'deletedoc',
type : 'iflowcontent',
desc : wcm.LANG['IFLOWCONTENT_70'] || '删除这篇文档',
title : wcm.LANG['IFLOWCONTENT_70'] || '删除这篇文档',
rightIndex : 1,
order : 7,
fn : pageObjMgr['deletedoc'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
},
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'detailPublish',
type : 'iflowcontent',
desc : wcm.LANG['IFLOWCONTENT_71'] || '发布这篇文档',
rightIndex : 2,
order : 8,
fn : pageObjMgr['detailPublish'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
},
quickKey : 'P'
});
reg({
key : 'sign',
type : 'iflowcontent',
desc : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
title : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
rightIndex : -1,
order : 1,
fn : pageObjMgr['sign'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
}
});
reg({
key : 'sign',
type : 'iflowcontents',
desc : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
title : wcm.LANG['IFLOWCONTENT_75'] || '签收工作流文档',
rightIndex : -1,
order : 1,
fn : pageObjMgr['sign'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
}
});
reg({
key : 'refuse',
type : 'iflowcontent',
desc : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
title : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
rightIndex : -1,
order : 2,
fn : pageObjMgr['refuse'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
}
});
reg({
key : 'refuse',
type : 'iflowcontents',
desc : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
title : wcm.LANG['IFLOWCONTENT_76'] || '拒绝工作流文档',
rightIndex : -1,
order : 2,
fn : pageObjMgr['refuse'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
}
});
reg({
key : 'rework',
type : 'iflowcontent',
desc : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
title : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
rightIndex : -1,
order : 3,
fn : pageObjMgr['rework'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
}
});
reg({
key : 'rework',
type : 'iflowcontents',
desc : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
title : wcm.LANG['IFLOWCONTENT_64'] || '返工工作流文档',
rightIndex : -1,
order : 3,
fn : pageObjMgr['rework'],
isVisible : function(event){
var context = event.getContext();
if(context.ViewType==1)return true;
return false;
}
});
})();

Ext.ns('wcm.domain.InfoviewDocMgr');
(function(){
Ext.apply(wcm.domain.InfoviewDocMgr, {
});
})();
(function(){
var pageObjMgr = wcm.domain.InfoviewDocMgr;
var reg = wcm.SysOpers.register;
var m_sServiceId = 'wcm61_viewdocument';
var m_nObjType = 600;
function $openCenterWin(_sUrl, _sName, _width, _height, _sFeature){
if(!_width || !_height){
$openCentralWin(_sUrl, _sName);
return;
}
var _WIN_WIDTH = window.screen.availWidth;
var _WIN_HEIGHT = window.screen.availHeight;
var l = (_WIN_WIDTH - _width) / 2;
var t = (_WIN_HEIGHT - _height) / 2;
sFeature = "left="+l + ",top=" + t +",width=" 
+ _width + ",height=" + _height + "," + _sFeature;
var sName = _sName || "";
sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
var oWin = window.open(_sUrl, sName, sFeature);
if(oWin) oWin.focus();
}
function preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid){
wcm.domain.PublishAndPreviewMgr.preview(_sDocIds, _nObjectType, _oExtraParams,_serviceid);
}
function getHelper(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
var cb = wcm.CrashBoard.get({
id : 'document_info_dialog',
title : wcm.LANG['INFOVIEW_DOC_112'] || '系统提示信息',
url : WCMConstants.WCM6_PATH + 'infoview/infoviewdoc_info.html',
width:'500px',
height:'300px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
cb.show();
}
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
function doAfterPublish(_postData, _sMethodName,_transport,_json){
if(_json!=null&&_json["REPORTS"]){
var oReports = _json["REPORTS"];
var stJson = com.trs.util.JSON;
var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
if(bIsSuccess=='false'){
Ext.Msg.report(_json, wcm.LANG['INFOVIEW_DOC_180'] || '发布校验结果');
return;
}
}
var bIsPublished = (_sMethodName.toLowerCase() != 'recallpublish');
Ext.Msg.$timeAlert((wcm.LANG['INFOVIEW_DOC_181'] || '已经将您的发布操作提交到后台了...'), 3);
var params = Object.extend({}, _postData);
params['StatusName'] = bIsPublished ? (wcm.LANG['INFOVIEW_DOC_182'] || '已发') : (wcm.LANG['INFOVIEW_DOC_183'] || '已否');
params['StatusValue'] = bIsPublished ? '10' : '15';
var objectids = params['ObjectIds'];
if(!Array.isArray(objectids)) {
params['ObjectIds'] = [objectids];
}
}
function publish(objectids, _sMethodName,_oExtraParams){
_sMethodName = _sMethodName || 'publish';
var oPostData = {'ObjectIds' : objectids};
getHelper().call(m_sServiceId, _sMethodName, oPostData, true,
function(_transport,_json){
doAfterPublish(oPostData, _sMethodName, _transport, _json);
}
);
}
function exportCurrent(event, _bExportSelected) {
var docIds = event.getIds().join();
var m_nCurrChannelId = event.getHost().getId() || 0;
var context = event.getContext();
var sSelectedIVIds = null;
if(_bExportSelected){
sSelectedIVIds = docIds;
if(!sSelectedIVIds)sSelectedIVIds = '0';
}
doExcelExport("", sSelectedIVIds, context);
}
function doExcelExport(_exportFields,_exportIVIds, context){
var sRequestUrl = WCMConstants.WCM6_PATH + "infoview/infoview_document_export_excel.jsp";
var params = 'ChannelId=' + context.pageDataParams.m_nCurrChannelId + '&SearchXML=' + context.pageDataParams.m_sSearchXML;
if(_exportFields && _exportFields.length > 0) {
params += '&ExportFields=' + _exportFields;
}
if(_exportIVIds && _exportIVIds.length > 0) {
params += '&SelectedDocIds=' + _exportIVIds;
}
if(parseInt(context.pageDataParams.m_sDocStatus) > 0){
params += '&DocStatus=' + context.pageDataParams.m_sDocStatus;
}
new Ajax.Request(sRequestUrl,{
contentType : 'application/x-www-form-urlencoded',
method : 'post',
parameters : params,
onSuccess : function(_transport){
result = _transport.responseText;
if(result && result.indexOf("<excelfile>") != -1){
var ix = result.indexOf("<excelfile>") + 11;
var ixx = result.indexOf("</excelfile>");
result = result.substring(ix,ixx);
var frm = document.getElementById("ifrmDownload");
if(frm == null) {
frm = document.createElement('iframe');
frm.style.height = 0;
frm.style.width = 0;
document.body.appendChild(frm);
}
var sUrl = "../file/read_file.jsp?FileName="+result;
frm.src = sUrl;
}else{
Ext.Msg.alert(wcm.LANG['INFOVIEW_DOC_114'] || "导出统计结果到Excel失败！");
}
},
onFailure : function(_transport){
Ext.Msg.alert(wcm.LANG['INFOVIEW_DOC_114'] || "导出统计结果到Excel失败！");
}
});
}
function openEditor(params){
if(params.DocumentId=='0'&&params.SiteId!=0){
FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_siteadd_step1.html?' 
+ $toQueryStr(params), wcm.LANG['INFOVIEW_DOC_115'] || '选择要新建文档的栏目', 400, 350);
return;
}
var iWidth = window.screen.availWidth - 12;
var iHeight = window.screen.availHeight - 30;
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params), "_blank" , sFeature);
}
function exportExcel(event, _bExportSelected) {
var docIds = event.getObjs().getPropertyAsInt('docid', 0);
if(_bExportSelected && docIds.length==0){
Ext.Msg.$alert(wcm.LANG['INFOVIEW_DOC_184'] || '必须至少选择一篇文档！');
return;
}
var m_nCurrChannelId = event.getHost().getId() || 0;
var context = event.getContext();
var pFields = context.pageDataParams.m_sCurrOutlineFields.split(",");
for(var nIndex = pFields.length-1; nIndex>=0; nIndex--){
if(pFields[nIndex] == "_EDIT" || pFields[nIndex] == "_PREVIEW" )
pFields.splice(nIndex, 1);
}
var oArgs = {
ChannelId : m_nCurrChannelId,
SelectedFields : pFields.join(","),
IsExport : 1
};
var sUrl = WCMConstants.WCM6_PATH + 'infoview/infoview_fields_select_order.jsp';
var cb = wcm.CrashBoard.get({
id : 'exportExcel',
title : wcm.LANG['INFOVIEW_DOC_113'] || '导出为Excel',
url : sUrl,
width: '680px',
height: '370px',
params : oArgs,
callback : function(sResult){
if(!sResult) {
return false;
}
var sSelectedIVIds = null;
if(_bExportSelected== true){
sSelectedIVIds = docIds;
if(!sSelectedIVIds)sSelectedIVIds = '0';
}
cb.close();
doExcelExport(sResult,sSelectedIVIds,context);
return false;
}
});
cb.show();
}
function openImport(event,result){
var oParams = {
DocumentId : result.DocumentId,
ChannelId : result.ChannelId,
SiteId : result.SiteId
}
var sChannelNames = result.ChannelName;
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH +'document/document_import.jsp?' + $toQueryStr(oParams), String.format(wcm.LANG.INFOVIEW_DOC_157 || ('文档-导入文档到栏目[{0}]'),sChannelNames[0]),CMSObj.afteradd(event));
}, 10);
return false;
}
function startDocInFlow(event){
var postData = {
objectid: event.getObj().getPropertyAsInt('docid')
}
var sServiceId = 'wcm6_document';
BasicDataHelper.call(sServiceId, 'startDocumentInFlow', postData, true, function(){
CMSObj.afteredit(event)();
});
return false;
}
Ext.apply(pageObjMgr, {
'new' : function(event){
var oParams = Ext.apply({
DocumentId : 0,
FromEditor : 1
}, parseHost(event.getHost()));
openEditor(oParams);
},
basicpublish : function(event){
publish(event.getIds(), 'basicPublish');
},
trash : function(event){
var oHost = parseHost(event.getHost());
var nCount = event.length();
var sHint = (nCount==1)?'':' '+nCount+' ';
var browserEvent = event.browserEvent;
var bDrop = !!(browserEvent && 
browserEvent.type=='keydown' && browserEvent.shiftKey);
var params = {
objectids: event.getIds(),
operation: bDrop ? '_forcedelete' : '_trash'
} 
doOptionsAfterDisplayInfo(params, function(){
ProcessBar.start(wcm.LANG['INFOVIEW_DOC_174'] || "删除表单文档");
getHelper().call(m_sServiceId, 'delete',
Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
function(){
ProcessBar.close();
event.getObjs().afterdelete();
}
);
});
},
moveall : function(event,operItem){
var sHtml = String.format("确定要{0}移动所有{1}文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
pageObjMgr.move(event, operItem, true);
}
})
},
move : function(event,operItem,bMoveall){
var context = event.getContext();
var sObjectids = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var hostChnlId = hostType == 101 ? hostId : 0;
sObjectids = (sObjectids.length!=0)?sObjectids:'0';
if(bMoveall)sObjectids = '0';
var channelids = event.getObjs().getPropertyAsString("channelid", 0);
if(!Ext.isArray(channelids)) channelids = [channelids];
var channelType = event.getObjs().getPropertyAsString("channelType", 0);
if(!Ext.isArray(channelType)) channelType = [channelType];
var bIsOneChannel = true;
var tmpChannelid = channelids[0];
for(var i=1,n=channelids.length; i<n; i++){
if(tmpChannelid!=channelids[i]){
bIsOneChannel = false;
break;
}
}
var nExcludeInfoView = 0;
var args = {
IsRadio : 1,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeInfoView : nExcludeInfoView,
ExcludeOnlySearch : 1,
ShowOneType : 1,
SelectedChannelIds : channelids.join() || hostChnlId,
NotSelect : 1,
RightIndex : 31,
canEmpty : true,
ValidInfoViewChannel : 1
};
if(bIsOneChannel){
Ext.apply(args, {
CurrChannelId : hostChnlId || tmpChannelid || 0,
ExcludeSelf : 1
});
}
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'include/channel_select.html',
title : wcm.LANG['INFOVIEW_DOC_116'] || '文档-文档移动到...',
callback : function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert('请选择当前文档要移动到的目标栏目!');
return false;
}
var nFromChnlId = bMoveall==true ? hostChnlId:event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
"ObjectIds" : sObjectids,
"FromChannelId" : nFromChnlId,
"ToChannelId" : selectIds.join(',')
}
Ext.apply(oPostData, PageContext.params);
if(bMoveall){
Ext.apply(oPostData,{PAGESIZE:500});
}
var func = function(){
FloatPanel.close();
CMSObj.afteredit(event)();
}
var oHelper = new com.trs.web2frame.BasicDataHelper();
ProcessBar.start(wcm.LANG['INFOVIEW_DOC_176'] || "移动文档");
oHelper.Call('wcm6_viewdocument',(sObjectids=='0')?'moveAll':'move',oPostData,true,
function(_transport,_json){
ProcessBar.close();
Ext.Msg.report(_json, wcm.LANG['INFOVIEW_DOC_118'] || '文档移动结果',func);
FloatPanel.hide();
},
function(_transport,_json){
ProcessBar.close();
$render500Err(_transport,_json);
FloatPanel.close();
}
);
},
dialogArguments : args
});
},
copyall : function(event,operItem){
pageObjMgr.copy(event, operItem, true);
},
copy : function(event,operItem,bCopyall){
var pageContext = event.getContext();
var sObjectids = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var hostChnlId = hostType == 101 ? hostId : 0;
sObjectids = (sObjectids.length!=0)?sObjectids:'0';
if(bCopyall)sObjectids = '0';
var channelids = event.getObjs().getPropertyAsString("channelid", 0);
if(!Ext.isArray(channelids)) channelids = [channelids];
var channelType = event.getObjs().getPropertyAsString("channelType", 0);
if(!Ext.isArray(channelType)) channelType = [channelType];
var bIsOneChannel = true;
var tmpChannelid = channelids[0];
for(var i=1,n=channelids.length; i<n; i++){
if(tmpChannelid!=channelids[i]){
bIsOneChannel = false;
break;
}
}
var nExcludeInfoView = 0;
var args = {
IsRadio : 0,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeVirtual : 1,
ExcludeInfoView : nExcludeInfoView,
ExcludeOnlySearch : 1,
ShowOneType : 1,
SelectedChannelIds : channelids.join() || hostChnlId,
NotSelect : 1,
RightIndex : 31,
canEmpty : true,
ValidInfoViewChannel : 1
};
if(bIsOneChannel){
Ext.apply(args, {
CurrChannelId : hostChnlId || tmpChannelid || 0,
ExcludeSelf : 0
});
}
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'include/channel_select.html',
title : wcm.LANG['INFOVIEW_DOC_119'] || '文档-文档复制到...',
callback : function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert(wcm.LANG['INFOVIEW_DOC_120'] || '请选择当前文档要复制到的目标栏目!');
return false;
}
var nFromChnlId = bCopyall==true ? hostChnlId:event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
"ObjectIds" : sObjectids,
"FromChannelId" : nFromChnlId,
"ToChannelIds" : selectIds.join(',')
};
var oHelper = new com.trs.web2frame.BasicDataHelper();
var func = function(){
FloatPanel.close();
CMSObj.afteredit(event)();
}
ProcessBar.start(wcm.LANG['INFOVIEW_DOC_177'] || "复制文档");
oHelper.Call('wcm6_viewdocument',(sObjectids=='0')?'copyAll':'copy',oPostData,true,
function(_transport,_json){
ProcessBar.close();
Ext.Msg.report(_json, wcm.LANG['INFOVIEW_DOC_121'] || '文档复制结果', func);
FloatPanel.hide();
},
function(_transport,_json){
ProcessBar.close();
$render500Err(_transport,_json);
FloatPanel.close();
}
);
},
dialogArguments : args
});
},
preview : function(event){
var sIds = event.getIds().join();
var host = event.getHost();
var oParams = {
FolderId : host.getId() || 0,
FolderType : host.getIntType()
};
preview(sIds,m_nObjType,oParams,m_sServiceId);
},
setUserDesignFields : function(event) {
var docIds = event.getIds().join();
var m_nCurrChannelId = event.getHost().getId() || 0;
var pageParams = event.getContext().pageDataParams;
var oArgs = {
ChannelId : m_nCurrChannelId,
SelectedFields : pageParams.m_sCurrOutlineFields
};
var sUrl = WCMConstants.WCM6_PATH + 'infoview/infoview_fields_select_order.jsp';
var cb = wcm.CrashBoard.get({
id : 'cb_setUserDesignFields',
title : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
url : sUrl,
width: '680px',
height: '370px',
params : oArgs,
callback : function(sResult){
if(sResult === false || sResult===null || sResult===window.undefined){
return;
}
if(!sResult) {
sResult = '';
}
if(sResult.byteLength()>1000){
Ext.Msg.$alert(wcm.LANG['INFOVIEW_DOC_122'] || '设置的缺省视图字段超长，长度最大限制为1000.');
return;
}
cb.close();
var frmOutlineFields = document.getElementById("frmOutlineFields");
frmOutlineFields.SelectedFields.value = sResult;
frmOutlineFields.url.value = location.href;
frmOutlineFields.submit();
return false;
}
});
cb.show();
},
'export' : function(event, elToolbar){
var exportExcels = [];
exportExcels.push({
desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
oprKey : 'item0',
cmd : function(){
exportExcel(event);
}
});
exportExcels.push({
desc : wcm.LANG['INFOVIEW_DOC_123'] || '导出Excel...(选中行)',
oprKey : 'item1',
cmd : function(){
exportExcel(event, true);
}
});
simpleMenu = new com.trs.menu.SimpleMenu({sBaseCls : 'exportExcelsbox'});
simpleMenu.show(exportExcels || [], {x:elToolbar.offsetLeft,y:elToolbar.offsetTop+50});
},
exportForSelect : function(event){
exportExcel(event, true);
},
exportExcelForAll : function(event){
exportExcel(event, false);
},
quoteDoc : function(event){
var oPostData = {
'objectids' : event.getIds(),
'channelids' : event.getObj().getPropertyAsInt('channelid'),
'channelType' : 13
}
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_quoteto.html?' + $toQueryStr(oPostData),
wcm.LANG['INFOVIEW_DOC_126'] || '文档-文档引用到...');
},
edit : function(event){
var host = event.getHost();
var oParams = {
DocumentId : event.getObj().getPropertyAsInt('docid'),
ChannelId :event.getObj().getPropertyAsInt('channelid'),
SiteId :host.getType()==WCMConstants.OBJ_TYPE_WEBSITE?host.getId():0,
FromEditor : 1
};
openEditor(oParams);
},
docpositionset : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var objType = event.getObjs().getAt(0).objType;
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType});
FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams),
wcm.LANG.INFOVIEW_DOC_161 || '表单-调整顺序', CMSObj.afteredit(event));
},
view : function(event){
var pageContext = event.getContext();
var host = event.getHost();
var hostType = host.getIntType();
var hostId = host.getId();
var oParams = {
DocumentId : event.getObj().getPropertyAsInt('docid'),
ChannelId :event.getObj().getPropertyAsInt('channelid'),
ChnlDocId : event.getIds(),
FromRecycle : pageContext.fromRecycle || 0
};
$openMaxWin(WCMConstants.WCM6_PATH +
'document/document_show.jsp?' + $toQueryStr(oParams));
},
logo : function(event){
var oParams = {
HostId : event.getObj().getPropertyAsInt('docid'),
HostType :605
};
$openCenterWin(WCMConstants.WCM6_PATH + 
'logo/logo_list.jsp?' + $toQueryStr(oParams),"infoviewdoc_logo", 900, 600, "resizable=yes");
},
"import" : function(event){
var oParams = Ext.apply({
DocumentId : event.getObj().getPropertyAsInt('docid')
}, parseHost(event.getHost()));
if(oParams.SiteId!=0){
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_siteimport_step1.html?' + $toQueryStr(oParams),
wcm.LANG.INFOVIEW_DOC_155 || '选择要文档导入的目标栏目',openImport.bind(this,event));
return;
}
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_import.jsp?' + $toQueryStr(oParams),
wcm.LANG.INFOVIEW_DOC_156 || '文档-导入文档',CMSObj.afteradd(event));
},
startFlow : function(event){
Ext.Msg.confirm(wcm.LANG['INFOVIEW_DOC_185'] || '您确实要将当前文档投入流转吗？', {
yes : function(){
startDocInFlow(event);
},
no : function(){
return;
}
});
},
setright : function(event){
var docId = event.getObj().getPropertyAsInt('docid', 0);
$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=605&ObjId=" + docId,
"document_right_set", 900, 600, "resizable=yes");
},
changestatus : function(event){
var oPageParams = event.getContext();
var sId = event.getIds().join();
var nChannelId = event.getObj().getPropertyAsInt("channelid", 0);
Object.extend(oPageParams,{
"ObjectIds":sId,
"IsPhoto":false,
'ChannelIds': nChannelId});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), wcm.LANG['INFOVIEW_DOC_186'] || '文档-改变状态', CMSObj.afteredit(event));
},
detailPublish : function(event){
publish(event.getIds(), 'detailPublish');
},
directpublish : function(event){
var oPageParams = event.getContext();
var _sIds = event.getIds();
var postData = Object.extend({},{
'ObjectIds' : _sIds
});
BasicDataHelper.call("wcm6_viewdocument", "directpublish", postData, true, function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
}.bind(this));
},
recallPublish : function(event){
var sHtml = String.format("确实要{0}撤销发布{1}这篇文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
publish(event.getIds(), 'recallPublish');
}
});
},
directRecallpublish : function(event){
var sHtml = String.format("确定要{0}撤销发布{1}所选文档及其所有引用文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
publish(event.getIds(), 'recallpublishall');
}
})
},
PublishInfo : function(event){
var nId = event.getObj().getPropertyAsInt('docid', 0);
var oArgs = {
FolderType : 605,
FolderId : nId
}
var sUrl = WCMConstants.WCM6_PATH + 'infoview/publishtask_get_by_type_id.jsp';
var cb = wcm.CrashBoard.get({
id : 'INFOVIEWDOC_PUBLISHIINFO',
title : wcm.LANG['INFOVIEW_DOC_187'] || '发布历史信息显示',
url : sUrl,
width: '600px',
height: '400px',
params : oArgs,
callback : function(_args){
}
});
cb.show();
},
'exportDoc' : function(event){
var oPostData = {
'objectids' : event.getIds(),
'channelids' : event.getObj().getPropertyAsInt("channelid", 0)
};
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_export.jsp?' + $toQueryStr(oPostData), wcm.LANG['INFOVIEW_DOC_188'] || '文档-导出文档');
},
backup : function(event){
var oPostData = {
docids: event.getObj().getPropertyAsInt('docid', 0),
ExcludeTrashed: true
};
getHelper().call('wcm6_documentBak','backup', oPostData, true,
function(_transport,_json){
Ext.Msg.report(_json,wcm.LANG['INFOVIEW_DOC_189'] || '文档版本保存结果');
}
);
},
exportall : function(event){
if(confirm(wcm.LANG['INFOVIEW_DOC_190'] || '此操作可能需要较长时间.确实要导出所有文档吗?')) {
var oPostData = Ext.apply({
ExportAll: 1
}, parseHost(event.getHost()));
var context = event.getContext();
var dialogArguments = Ext.apply({}, context.get("pagecontext").params);
Ext.apply(dialogArguments,{PAGESIZE:-1});
FloatPanel.open(
WCMConstants.WCM6_PATH + 'document/document_export.jsp?' + $toQueryStr(oPostData),
wcm.LANG['INFOVIEW_DOC_191'] || '文档-导出所有文档',
null,
dialogArguments
);
}
},
changelevel : function(event){
var oPageParams = event.getContext();
Object.extend(oPageParams,{
"ObjectIds": event.getObjs().getPropertyAsInt('docid', 0),
"IsPhoto":false
});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_doclevel.jsp?' + $toQueryStr(oPageParams), '文档-改变密级', CMSObj.afteredit(event));
},
setTop : function(event){
var docId = event.getObj().getPropertyAsInt('docid',0);
var channelId = event.getObj().getPropertyAsInt('channelid', 0);
var params = {
DocumentId : docId,
ChannelId : channelId
}
FloatPanel.open(WCMConstants.WCM6_PATH +'document/document_topset.jsp?' + $toQueryStr(params), '设置置顶', CMSObj.afteredit(event));
}
})
})();
(function(){
var pageObjMgr = wcm.domain.InfoviewDocMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var host = event.getHost();
if(Ext.isTrue(host.isVirtual)){
return false;
}
return true;
};
reg({
key : 'new',
type : 'infoviewdocInChannel',
isHost : true,
desc : wcm.LANG['INFOVIEW_DOC_127'] || '新建表单文档',
title : wcm.LANG['INFOVIEW_DOC_127'] || '新建表单文档',
rightIndex : 31,
order : 1,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'copy',
type : 'infoviewdocInChannel',
isHost : true,
desc : wcm.LANG['INFOVIEW_DOC_153'] || '复制所有文档到',
title : wcm.LANG['INFOVIEW_DOC_153'] || '复制所有文档到',
rightIndex : 57,
order : 2,
fn : pageObjMgr['copyall']
});
reg({
key : 'move',
type : 'infoviewdocInChannel',
isHost : true,
desc : wcm.LANG.INFOVIEW_DOC_162 ||'移动所有文档到',
title : wcm.LANG.INFOVIEW_DOC_162 ||'移动所有文档到',
rightIndex : 56,
order : 3,
fn : pageObjMgr['moveall']
});
reg({
key : 'exportall',
type : 'infoviewdocInChannel',
desc : wcm.LANG.INFOVIEW_DOC_192 || '导出所有文档',
title:'导出所有文档...',
rightIndex : 34,
order : 4,
fn : pageObjMgr['exportall'],
quickKey : 'X'
});
reg({
key : 'import',
type : 'infoviewdocInChannel',
isHost : true,
desc : wcm.LANG.INFOVIEW_DOC_154 ||'从外部导入文档',
title : wcm.LANG.INFOVIEW_DOC_154 ||'从外部导入文档',
rightIndex : 31,
order : 4,
fn : pageObjMgr['import']
});
reg({
key : 'setuserdesignfields',
type : 'infoviewdocInChannel',
isHost : true,
desc : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
title : wcm.LANG['INFOVIEW_DOC_13'] || '自定义视图',
rightIndex : 13,
order : 5,
fn : pageObjMgr['setUserDesignFields']
});
reg({
key : 'exportexcelall',
type : 'infoviewdocInChannel',
isHost : true,
desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
title : wcm.LANG['INFOVIEW_DOC_149'] || '导出所有',
rightIndex : 30,
order : 6,
fn : pageObjMgr['exportExcelForAll']
});
reg({
key : 'edit',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_128'] || '修改这篇文档',
title : wcm.LANG['INFOVIEW_DOC_128'] || '修改这篇文档',
rightIndex : 32,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'preview',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_129'] || '预览这篇文档',
title : wcm.LANG['INFOVIEW_DOC_130'] || '预览这篇文档发布效果',
rightIndex : 38,
order : 2,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'copy',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_133'] || '复制这篇文档到',
title : wcm.LANG['INFOVIEW_DOC_133'] || '复制这篇文档到',
rightIndex : 34,
order : 3,
fn : pageObjMgr['copy']
});
reg({
key : 'quote',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_134'] ||'引用这篇文档到',
title : wcm.LANG['INFOVIEW_DOC_134'] ||'引用这篇文档到',
rightIndex : 34,
order : 4,
fn : pageObjMgr['quoteDoc']
});
reg({
key : 'move',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_135'] || '移动这篇文档到',
title : wcm.LANG['INFOVIEW_DOC_135'] || '移动这篇文档到',
rightIndex : 33,
order : 5,
fn : pageObjMgr['move']
});
reg({
key : 'basicpublish',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_131'] ||'发布这篇文档',
title : wcm.LANG['INFOVIEW_DOC_132'] ||'发布这篇文档，生成这篇文档的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 6,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'detailpublish',
type : 'infoviewdoc',
desc : wcm.LANG.INFOVIEW_DOC_163 ||'仅发布这篇文档细览',
title : wcm.LANG.INFOVIEW_DOC_164 ||'仅发布这篇文档细览,仅重新生成这篇文档的细览页面',
rightIndex : 39,
order : 7,
fn : pageObjMgr['detailPublish']
});
reg({
key : 'directpublish',
type : 'infoviewdoc',
desc : wcm.LANG.INFOVIEW_DOC_204 ||'直接发布这篇文档',
title : wcm.LANG.INFOVIEW_DOC_205 ||'发布这篇文档,同时发布此文档的所有引用文档',
rightIndex : 39,
order : 7,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_169'] ||'撤销发布这篇文档',
title : wcm.LANG['INFOVIEW_DOC_170'] ||'撤销发布这篇文档，撤回已发布目录或页面',
rightIndex : 39,
order : 8,
fn : pageObjMgr['recallPublish']
});
reg({
key : 'directRecallpublish',
type : 'infoviewdoc',
desc : wcm.LANG.INFOVIEW_DOC_206 ||'直接撤销发布这篇文档',
title : wcm.LANG.INFOVIEW_DOC_207 ||'撤回当前文档的发布页面，并同步撤销文档的所有引用以及原文档发布页面',
rightIndex : 39,
order : 8,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'publishinfo',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_171'] || '发布历史',
title : wcm.LANG['INFOVIEW_DOC_171'] || '发布历史',
rightIndex : 39,
order : 9,
fn : pageObjMgr['PublishInfo']
});
reg({
key : 'trash',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_136'] || '删除这篇文档',
title : wcm.LANG['INFOVIEW_DOC_137'] || '删除这篇文档',
rightIndex : 33,
order : 10,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'exportexcelselectone',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
title : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel',
rightIndex : 30,
order : 11,
fn : pageObjMgr['exportForSelect']
});
reg({
key : 'export',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_172'] || '导出这篇文档',
title : wcm.LANG['INFOVIEW_DOC_172'] || '导出这篇文档',
rightIndex : 30,
order : 12,
fn : pageObjMgr['exportDoc']
});
reg({
key : 'startflow',
type : 'infoviewdoc',
desc : wcm.LANG['INFOVIEW_DOC_193'] || '开始流转',
title : wcm.LANG['INFOVIEW_DOC_193'] || '开始流转',
rightIndex : 31,
order : 13,
fn : pageObjMgr['startFlow'],
isVisible : function(event){
if(event.getObj().getPropertyAsString("CanInFlow")=='true')
return true;
return false;
}
});
reg({
key : 'changelevel',
type : 'infoviewdoc',
desc : wcm.LANG.DOCUMENT_PROCESS_273 || '改变这篇文档的密级',
title : '改变这篇文档的密级...',
rightIndex : 61,
order : 14,
fn : pageObjMgr['changelevel']
});
reg({
key : 'settop',
type : 'infoviewdoc',
desc : '设置置顶',
title : '设置置顶',
rightIndex : 62,
order : 15,
fn : pageObjMgr['setTop']
});
reg({
key : 'preview',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_194'] || '预览这些文档',
title : wcm.LANG['INFOVIEW_DOC_195'] || '预览这些文档发布效果',
rightIndex : 38,
order : 1,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'basicpublish',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_138'] || '发布这些文档',
title : wcm.LANG['INFOVIEW_DOC_139'] || '发布这些文档，生成这些文档的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 2,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'detailpublish',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_202'] ||'仅发布这些文档细览',
title : wcm.LANG['INFOVIEW_DOC_203'] ||'仅发布这些文档细览，仅重新生成这些文档的细览页面',
rightIndex : 39,
order : 3,
fn : pageObjMgr['detailPublish']
});
reg({
key : 'directpublish',
type : 'infoviewdocs',
desc : wcm.LANG.INFOVIEW_DOC_208 ||'直接发布这些文档',
title : wcm.LANG.INFOVIEW_DOC_209 ||'发布这些文档，同步发布这些文档所有的引用文档',
rightIndex : 39,
order : 3,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_167'] ||'撤销发布这些文档',
title : wcm.LANG['INFOVIEW_DOC_168'] ||'撤销发布这些文档，撤回已发布目录或页面',
rightIndex : 39,
order : 4,
fn : pageObjMgr['recallPublish']
});
reg({
key : 'directRecallpublish',
type : 'infoviewdocs',
desc : wcm.LANG.DOCUMENT_PROCESS_210 ||'直接撤销发布这些文档',
title : wcm.LANG.DOCUMENT_PROCESS_211 ||'撤回这些文档的发布页面，并同步撤销文档的所有引用以及原文档发布页面',
rightIndex : 39,
order : 4,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'copy',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_140'] || '复制这些文档到',
title : wcm.LANG['INFOVIEW_DOC_140'] || '复制这些文档到',
rightIndex : 34,
order : 5,
fn : pageObjMgr['copy']
});
reg({
key : 'move',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_141'] || '移动这些文档到',
title : wcm.LANG['INFOVIEW_DOC_141'] || '移动这些文档到',
rightIndex : 33,
order : 6,
fn : pageObjMgr['move']
});
reg({
key : 'quote',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_142'] || '引用这些文档到',
title : wcm.LANG['INFOVIEW_DOC_142'] || '引用这些文档到',
rightIndex : 34,
order : 7,
fn : pageObjMgr['quoteDoc']
});
reg({
key : 'changestatus',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_196'] || '改变这些文档的状态',
title : wcm.LANG['INFOVIEW_DOC_196'] || '改变这些文档的状态',
rightIndex : 35,
order : 7,
fn : pageObjMgr['changestatus']
});
reg({
key : 'setright',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_197'] || '设置这些文档的权限',
title : wcm.LANG['INFOVIEW_DOC_197'] || '设置这些文档的权限',
rightIndex : 61,
order : 8,
fn : pageObjMgr['setright']
});
reg({
key : 'exportexcelselectmore',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel(选中行)',
title : wcm.LANG['INFOVIEW_DOC_15'] || '导出Excel(选中行)',
rightIndex : 30,
order : 8,
fn : pageObjMgr['exportForSelect']
});
reg({
key : 'trash',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_143'] ||'删除这些文档',
title : wcm.LANG['INFOVIEW_DOC_143'] ||'删除这些文档',
rightIndex : 33,
order : 9,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'changelevel',
type : 'infoviewdocs',
desc : '改变这些文档的密级',
title : '改变这些文档的密级...',
rightIndex : 61,
order : 10,
fn : pageObjMgr['changelevel']
});
reg({
key : 'export',
type : 'infoviewdocs',
desc : wcm.LANG['INFOVIEW_DOC_198'] || '导出这些文档',
title : wcm.LANG['INFOVIEW_DOC_199'] || '将当前文档导出成zip文件',
rightIndex : 34,
order : 10,
fn : pageObjMgr['exportDoc'],
quickKey : 'X'
});
reg({
key : 'docpositionset',
type : 'infoviewdoc',
desc : wcm.LANG.INFOVIEW_DOC_160 ||'调整顺序',
title : wcm.LANG.INFOVIEW_DOC_160 ||'调整顺序',
rightIndex : 62,
order : 9,
fn : pageObjMgr['docpositionset'],
isVisible : function(event){
if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
return true;
return false;
}
});
reg({
key : 'logo',
type : 'infoviewdoc',
desc : wcm.LANG.INFOVIEW_DOC_152 ||'表单文档LOGO',
title : wcm.LANG.INFOVIEW_DOC_152 ||'表单文档LOGO',
rightIndex : 32,
order : 10,
fn : pageObjMgr['logo']
});
reg({
key : 'setright',
type : 'infoviewdoc',
desc : wcm.LANG.INFOVIEW_DOC_200 || '设置这篇文档的权限',
title : wcm.LANG.INFOVIEW_DOC_200 || '设置这篇文档的权限',
rightIndex : 61,
order : 11,
fn : pageObjMgr['setright']
});
reg({
key : 'changestatus',
type : 'infoviewdoc',
desc : wcm.LANG.INFOVIEW_DOC_201 || '改变状态',
title : wcm.LANG.INFOVIEW_DOC_201 || '改变状态',
rightIndex : 32,
order : 12,
fn : pageObjMgr['changestatus']
});
})();

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

Ext.ns('wcm.domain.MetaViewDataMgr');
(function(){
var m_oMgr = wcm.domain.MetaViewDataMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
var DIALOG_DOCUMENT_INFO = 'document_info_dialog';
wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
title : wcm.LANG.DOCUMENT_PROCESS_208 || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'docrecycle/document_info.html',
width:'500px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
}
function _addEdit(_id,event){
var nObjId = _id || 0;
var sTitle = (nObjId == 0)?(wcm.LANG.METAVIEWDATA_77 || "新建"):(wcm.LANG.METAVIEWDATA_106 || "修改");
sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
var contextParams = event.getContext().params;
var nViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
var oParams = {
ObjectId:nObjId,
ChannelId:(nObjId == 0) ? event.getHost().getId() 
: event.getObj().getPropertyAsInt("currchnlid",0),
ChnlDocId:event.getObj().getPropertyAsInt("recid",0),
FlowDocId:contextParams.FlowDocId || 0,
ViewId:nViewId
};
$openMaxWin(WCMConstants.WCM6_PATH + './application/' + nViewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
}
Ext.apply(wcm.domain.MetaViewDataMgr, {
view : function(event){
var _objId = event.getObj().getPropertyAsInt("docid",0);
var nViewId = event.getObj().getPropertyAsInt("dockind",0) || event.getContext().params["VIEWID"];
var urlParams = "?objectId=" + _objId;
var url = WCMConstants.WCM6_PATH + "application/" + nViewId + "/viewdata_detail.jsp" + urlParams;
$openMaxWin(url);
},
docpositionset : function(event){
var oPageParams = {};
var host = event.getHost();
var hostid = host.getId();
var docid = event.getObj().getPropertyAsInt("docid",0);
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
Object.extend(oPageParams,{"DocumentId":docid});
FloatPanel.open(WCMConstants.WCM6_PATH + 'metaviewdata/record_position_set.jsp?' + $toQueryStr(oPageParams), (wcm.LANG.METAVIEWDATA_25 || '记录—改变位置到'), CMSObj.afteredit(event));
},
preview : function(event){
var host = event.getHost();
var hostid = host.getId();
var oParams = {
FolderId : hostid,
FolderType : host.getType()=="website"? 103:101
};
var sIds = event.getIds().join(',');
wcm.domain.PublishAndPreviewMgr.preview(sIds,600,oParams,"wcm6_viewdocument");
},
basicpublish : function(event){
publish(event,"basicPublish");
},
recallpublish : function(event){
var sHtml = (wcm.LANG.METAVIEWDATA_130 ||"确定要<font color=\'red\' style=\'font-size:14px;\'>撤销发布</font>所选记录么？将<font color=\'red\ style=\'font-size:14px;\'>不可逆转</font>");
Ext.Msg.confirm(sHtml,{
yes : function(){
publish(event,'recallPublish');
}
});
},
detailpublish : function(event){
publish(event,'detailPublish');
},
directpublish : function(event){
var oPageParams = event.getContext();
var _sIds = event.getIds();
var postData = Object.extend({},{
'ObjectIds' : _sIds
});
BasicDataHelper.call("wcm6_viewdocument", "directpublish", postData, true, function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
}.bind(this));
},
directRecallpublish : function(event){
var sHtml = (wcm.LANG.METAVIEWDATA_139 ||"确定要<font color=\'red\' style=\'font-size:14px;\'>撤销发布</font>所选记录及其所有引用记录么？将<font color=\'red\' style=\'font-size:14px;\'>不可逆转</font>");
Ext.Msg.confirm(sHtml,{
yes : function(){
publish(event, 'recallpublishall');
}
})
},
"delete" : function(event){
var oHost = parseHost(event.getHost());
var browserEvent = event.browserEvent;
var bDrop = !!(browserEvent && 
browserEvent.type=='keydown' && browserEvent.shiftKey);
var params = {
objectids: event.getIds(),
operation: bDrop ? '_forcedelete' : '_trash'
}
doOptionsAfterDisplayInfo(params, function(){
ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_123||'删除记录');
getHelper().call('wcm6_viewdocument', 'delete',
Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
function(){
ProcessBar.close();
event.getObjs().afterdelete();
}
);
});
},
changestatus : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var _sObjectIds = event.getIds().join(',');
Object.extend(oPageParams,{"ObjectIds":_sObjectIds,"IsPhoto":false,'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), (wcm.LANG.METAVIEWDATA_31 || '记录-改变状态'), CMSObj.afteredit(event));
},
changelevel : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var _sObjectIds = event.getObjs().getPropertys("docId").join(',');
Object.extend(oPageParams,{
"ObjectIds":_sObjectIds,
"IsPhoto":false});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_doclevel.jsp?' + $toQueryStr(oPageParams), '记录-改变密级', CMSObj.afteredit(event));
},
"export" : function(event){
var _nViewId = event.getObj().getProperty('dockind');
_nViewId = _nViewId || event.getContext().params["VIEWID"];
var _chnlId = event.getContext().params["CHANNELID"];
var _sObjectIds = event.getObjs().getPropertys("docId").join(',');
try{
if(!_nViewId){
Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
return;
}
}catch(error){
Ext.Msg.alert("metaviewdata.export:" + error.message);
}
FloatPanel.open( WCMConstants.WCM6_PATH +'metaviewdata/record_export.jsp?' + $toQueryStr({
viewId : _nViewId,
channelId : _chnlId,
ObjectIds : _sObjectIds
}), (wcm.LANG.METAVIEWDATA_33 || '导出记录'), CMSObj.afteredit(event));
return;
},
setsynrule : function(event){
var channelId = event.getHost().getId();
FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/syn_rule_set.html?synRuleSetFrom=channel&channelId=' + channelId, (wcm.LANG.METAVIEWDATA_34 || '设置数据同步到WCMDocument的规则'), CMSObj.afteredit(event));
},
createfromexcel : function(event){
var _nViewId = event.getContext().params["VIEWID"];
try{
if(!_nViewId){
Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
return;
}
}catch(error){
Ext.Msg.alert("metaviewdata.createfromexcel:" + error.message);
}
wcm.domain.MetaViewDataMgr.download("/wcm/app/metaviewdata/read_excel.jsp?ViewId=" + _nViewId);
},
download : function(sURL){
var frm = $MsgCenter.getActualTop().$('iframe4download');
if(frm == null) {
frm = $MsgCenter.getActualTop().document.createElement('IFRAME');
frm.id = "iframe4download";
frm.style.display = 'none';
$MsgCenter.getActualTop().document.body.appendChild(frm);
}
frm.src = sURL;
},
'import' : function(event){
var host = event.getHost();
var hostid = host.getId();
var _nViewId = event.getContext().params["VIEWID"];
try{
if(!_nViewId){
Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
return;
}
}catch(error){
Ext.Msg.alert("metaviewdata.import:" + error.message);
}
var oParams = {
ViewId : _nViewId,
ChannelId : host.getType()=="website"?0:hostid
}
FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/view_data_import.jsp?' + $toQueryStr(oParams), (wcm.LANG.METAVIEWDATA_35 || '导入记录'), CMSObj.afteradd(event));
},
quote : function(event){
var _nchannelId = event.getObjs().getAt(0).getPropertyAsInt("currchnlid");
var _sDocIds = event.getIds().join(',');
var oPostData = {
'channelids' : _nchannelId,
'objectids' : _sDocIds
}
FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/record_quoteto.html?' + $toQueryStr(oPostData), (wcm.LANG.METAVIEWDATA_36 || '引用记录'), CMSObj.afteredit(event));
},
copy : function(event){
var _nchannelId = event.getObjs().getAt(0).getPropertyAsInt("currchnlid");
var _sDocIds = event.getIds().join(',');
var oPostData = {
'channelids' : _nchannelId,
'objectids' : _sDocIds
}
FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/record_copyto.html?' + $toQueryStr(oPostData), (wcm.LANG.METAVIEWDATA_38 || '复制记录'), CMSObj.afteredit(event));
},
move : function(event){
var _nchannelId = event.getObjs().getAt(0).getPropertyAsInt("currchnlid");
var _sDocIds = event.getIds().join(',');
var oPostData = {
'channelids' : _nchannelId,
'objectids' : _sDocIds
}
FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/record_moveto.html?' + $toQueryStr(oPostData), (wcm.LANG.METAVIEWDATA_39 || '移动记录'), CMSObj.afteredit(event));
},
add : function(event){
_addEdit(0,event);
},
edit : function(event){
var obj = event.getObj();
_addEdit(obj.getProperty("docid")||obj.getId(),event);
},
batchupdate : function(event){
var objIds = event.getContext().params["IDS"];
alert(objIds.split(",").length);
if(objIds.split(",").length > 5){
alert(wcm.LANG.metaviewdata_1001 || "要批量修改的数据超过了5个，为减少出错机会和减少对数据库的影响，请分批进行批量修改");
}
var _nViewId = event.getContext().params["VIEWID"];
var oPostData = {
'updateids' : objIds,
'viewid' : _nViewId
};
FloatPanel.open( WCMConstants.WCM6_PATH + 'metaviewdata/batchUpdate.jsp?' + $toQueryStr(oPostData), ('批量修改'), CMSObj.afteredit(event));
},
deleteEntity : function(event){
var sIds = event.getIds();
var nChnlId = event.getObj().getProperty("chnlId");
var oPageParams = {};
Ext.Msg.confirm(String.format("确实要将这{0}条记录放入废稿箱吗?",sIds.length), {
yes : function(){
ProcessBar.init(wcm.LANG.METAVIEWDATA_28 || '删除进度');
ProcessBar.addState((wcm.LANG.METAVIEWDATA_29 || '删除'), 2);
ProcessBar.start();
Object.extend(oPageParams,{"ObjectIds":sIds.join(","),"ChannelId":nChnlId});
BasicDataHelper.call('wcm6_document',"delete",oPageParams,true,function(transport, json){
ProcessBar.close();
var bSucess = $v(json, 'REPORTS.IS_SUCCESS') || true;
if(bSucess == false || bSucess == 'false'){
Ext.Msg.fault({
message : wcm.LANG.METAVIEWDATA_30 || '删除数据结果',
detail : json
});
}
event.getObjs().afterdelete();
});
}
});
},
exportall : function(event){
Ext.Msg.confirm(wcm.LANG.METAVIEWDATA_111 || '此操作可能需要较长时间。确实要导出所有记录吗？', {
yes : function(){
var _nViewId = event.getContext().params["VIEWID"];
var _nChnlId = event.getContext().params["CHANNELID"]
try{
if(!_nViewId){
Ext.Msg.alert(wcm.LANG.METAVIEWDATA_32 || "没有指定视图ID[VIEWID]");
return;
}
}catch(error){
Ext.Msg.alert("metaviewdata.export:" + error.message);
}
FloatPanel.open( WCMConstants.WCM6_PATH +'metaviewdata/record_export.jsp?' + $toQueryStr({
channelid : _nChnlId,
exportall : 1
}), (wcm.LANG.METAVIEWDATA_110 || '导出所有记录'), CMSObj.afteredit(event));
return;
}
});
},
setright : function(event){
var docId = event.getObj().getPropertyAsInt('docid', 0);
$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=605&ObjId=" + docId,
"document_right_set", 900, 600, "resizable=yes");
},
relationProMaintain : function(event){
var docId = event.getObj().getPropertyAsInt('docid', 0);
var currViewId = event.getObj().getPropertyAsInt("viewid",0)||contextParams.VIEWID||0;
wcm.MetaViewSelector.selectView({methodname : 'queryRelatingViews', MetaViewId:currViewId, ContainsChildrenBox : false},function(args){
var viewId = args.ViewId || 0;
var viewName = args.selectedNames;
if(viewId == 0)
return;
var url = WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_relations_back_select.jsp?ViewName=' + encodeURIComponent(viewName);
FloatPanel.open({
src : url,
title : String.format("{0}数据管理",viewName),
callback : function(){
},
dialogArguments : {
relations : 0,
CurrDocId : 0,
RelatedDocId : docId,
FromBackSelect : true,
RelatingViewId : viewId,
RelatedViewId : currViewId
}
});
});
}
});
})();
function $openCenterWin(_sUrl, _sName, _width, _height, _sFeature){
if(!_width || !_height){
$openCentralWin(_sUrl, _sName);
return;
}
var _WIN_WIDTH = window.screen.availWidth;
var _WIN_HEIGHT = window.screen.availHeight;
var l = (_WIN_WIDTH - _width) / 2;
var t = (_WIN_HEIGHT - _height) / 2;
sFeature = "left="+l + ",top=" + t +",width=" 
+ _width + ",height=" + _height + "," + _sFeature;
var sName = _sName || "";
sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
var oWin = window.open(_sUrl, sName, sFeature);
if(oWin) oWin.focus();
}
function preparePostData(event){
return {
'objectids' : event.getIds(),
'channelids' : event.getObj().getPropertyAsInt('channelid')
};
}
function publish(event, _sMethodName){
var objectids = event.getIds().join(',');
var oPostData = {'ObjectIds' : objectids};
BasicDataHelper.call("wcm6_viewdocument", _sMethodName, oPostData, true,
function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, _sMethodName, _transport, _json);
}
);
}
(function(){
var pageObjMgr = wcm.domain.MetaViewDataMgr;
var reg = wcm.SysOpers.register;
function fnIsNotDraft(event){
var obj = event.getObj();
var bDraft= obj.getPropertyAsString("bDraft");
if("true" == bDraft){
return false;
}
return true;
}
reg({
key : 'docpositionset',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_40 || '改变记录顺序到',
title : wcm.LANG.METAVIEWDATA_40 || '改变记录顺序到...',
rightIndex : 62,
order : 1,
fn : pageObjMgr['docpositionset']
});
reg({
key : 'edit',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_41 || '修改这条记录',
title : wcm.LANG.METAVIEWDATA_41 || '修改这条记录...',
rightIndex : 32,
order : 2,
fn : pageObjMgr['edit'],
quickKey : 'E'
});
reg({
key : 'maintainreldoc',
type : 'MetaViewData',
desc : '维护关联产品',
title : '维护关联产品...',
rightIndex : 32,
order : 2.1,
fn : pageObjMgr['relationProMaintain'],
isVisible : function(event){
var contextParam = event.getContext().params;
var bShowRelDocsMaintain = contextParam['ShowRelDocsMaintain'];
if(!bShowRelDocsMaintain){
return false;
}else{
return true;
}
}
});
reg({
key : 'preview',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_42 || '预览这条记录',
title : wcm.LANG.METAVIEWDATA_43 || '预览这条记录发布效果',
rightIndex : 38,
order : 3,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'basicpublish',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_44 || '发布这条记录',
title : wcm.LANG.METAVIEWDATA_45 || '发布这条记录，生成这条记录的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 4,
isVisible : fnIsNotDraft,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'detailpublish',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_46 || '仅发布这条记录细览',
title : wcm.LANG.METAVIEWDATA_47 || '仅发布这条记录细览，仅重新生成这条记录的细览页面',
rightIndex : 39,
order : 5,
isVisible : fnIsNotDraft,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_131 || '直接发布这条记录',
title : wcm.LANG.METAVIEWDATA_132 || '发布这条记录细览，同时发布此记录的所有引用记录',
rightIndex : 39,
order : 5,
isVisible : fnIsNotDraft,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_48 || '撤销发布这条记录',
title : wcm.LANG.METAVIEWDATA_49 || '撤销发布这条记录，撤回已发布目录或页面',
rightIndex : 39,
order : 6,
isVisible : fnIsNotDraft,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_133 || '直接撤销发布这条记录',
title : wcm.LANG.METAVIEWDATA_134 || '撤销发布这条记录，同步撤销这条记录所有的引用记录',
rightIndex : 39,
order : 6,
isVisible : fnIsNotDraft,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'export',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_50 || '导出这条记录',
title : wcm.LANG.METAVIEWDATA_51 || '将这条记录导出成zip文件',
rightIndex : 34,
order : 7,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'seperate',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_52 || '分隔线',
title : wcm.LANG.METAVIEWDATA_52 || '分隔线',
rightIndex : -1,
order : 8,
fn : pageObjMgr['seperate']
});
reg({
key : 'changestatus',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_53 || '改变这条记录状态',
title : wcm.LANG.METAVIEWDATA_53 || '改变这条记录状态',
rightIndex : 35,
order : 9,
isVisible : fnIsNotDraft,
fn : pageObjMgr['changestatus']
});
reg({
key : 'changelevel',
type : 'MetaViewData',
desc : '改变这条记录的密级',
rightIndex : 61,
order : 9,
fn : pageObjMgr['changelevel']
});
reg({
key : 'move',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_54 || '移动这条记录到',
title : wcm.LANG.METAVIEWDATA_54 || '移动这条记录到',
rightIndex : 33,
order : 10,
isVisible : fnIsNotDraft,
fn : pageObjMgr['move']
});
reg({
key : 'copy',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_55 || '复制这条记录到',
title : wcm.LANG.METAVIEWDATA_55 || '复制这条记录到',
rightIndex : 34,
order : 11,
isVisible : fnIsNotDraft,
fn : pageObjMgr['copy']
});
reg({
key : 'quote',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_56 || '引用这条记录到',
title : wcm.LANG.METAVIEWDATA_56 || '引用这条记录到',
rightIndex : 34,
order : 12,
isVisible : fnIsNotDraft,
fn : pageObjMgr['quote']
});
reg({
key : 'delete',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_129 || '删除记录',
title : wcm.LANG.METAVIEWDATA_57 || '将记录放入废稿箱',
rightIndex : 33,
order : 13,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'MetaViewDataInChannel',
desc : '新建一条记录',
title : '新建一条记录...',
rightIndex : 31,
order : 14,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'MetaViewDataInChannel',
desc : wcm.LANG.METAVIEWDATA_59 || '从外部导入记录',
title : wcm.LANG.METAVIEWDATA_59 || '从外部导入记录...',
rightIndex : 31,
order : 15,
fn : pageObjMgr['import']
});
reg({
key : 'createfromexcel',
type : 'MetaViewDataInChannel',
desc : wcm.LANG.METAVIEWDATA_60 || '从Excel创建记录',
title : wcm.LANG.METAVIEWDATA_60 || '从Excel创建记录...',
rightIndex : 31,
order : 16,
fn : pageObjMgr['createfromexcel']
});
reg({
key : 'setsynrule',
type : 'MetaViewDataInChannel',
desc : wcm.LANG.METAVIEWDATA_61 || '设置同步规则',
title : wcm.LANG.METAVIEWDATA_62 || '设置同步到文档的规则',
rightIndex : 13,
order : 17,
fn : pageObjMgr['setsynrule']
});
reg({
key : 'preview',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_63 || '预览这些记录',
title : wcm.LANG.METAVIEWDATA_64 || '预览这些记录发布效果',
rightIndex : 38,
order : 18,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'basicpublish',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_65 || '发布这些记录',
title : wcm.LANG.METAVIEWDATA_66 || '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
rightIndex : 39,
order : 19,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'detailpublish',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_67 || '仅发布这些记录细览',
title : wcm.LANG.METAVIEWDATA_68 || '仅发布这些记录细览，仅重新生成这些记录的细览页面',
rightIndex : 39,
order : 20,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_135 || '直接发布这些记录',
title : wcm.LANG.METAVIEWDATA_136 || '发布这些记录细览，同时发布这些记录的所有引用记录',
rightIndex : 39,
order : 20,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_69 || '撤销发布这些记录',
title : wcm.LANG.METAVIEWDATA_70 || '撤销发布这些记录，撤回已发布目录或页面',
rightIndex : 39,
order : 21,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_137 || '直接撤销发布这些记录',
title : wcm.LANG.METAVIEWDATA_138 || '撤销发布这些记录，同步撤销这些记录所有的引用记录',
rightIndex : 39,
order : 21,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'export',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_71 || '导出这些记录',
title : wcm.LANG.METAVIEWDATA_72 || '将这些记录导出成zip文件',
rightIndex : 34,
order : 22,
fn : pageObjMgr['export']
});
reg({
key : 'seperate',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_52 || '分隔线',
title : wcm.LANG.METAVIEWDATA_52 || '分隔线',
rightIndex : -1,
order : 23,
fn : pageObjMgr['seperate']
});
reg({
key : 'changestatus',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_73 || '改变这些记录状态',
title : wcm.LANG.METAVIEWDATA_73 || '改变这些记录状态',
rightIndex : 35,
order : 24,
fn : pageObjMgr['changestatus']
});
reg({
key : 'changelevel',
type : 'MetaViewDatas',
desc : '改变这些记录的密级',
rightIndex : 61,
order : 25,
fn : pageObjMgr['changelevel']
});
reg({
key : 'move',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_74 || '移动这些记录到',
title : wcm.LANG.METAVIEWDATA_74 || '移动这些记录到',
rightIndex : 33,
order : 25,
fn : pageObjMgr['move']
});
reg({
key : 'copy',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_75 || '复制这些记录到',
title : wcm.LANG.METAVIEWDATA_75 || '复制这些记录到',
rightIndex : 34,
order : 26,
fn : pageObjMgr['copy']
});
reg({
key : 'quote',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_76 || '引用这些记录到',
title : wcm.LANG.METAVIEWDATA_76 || '引用这些记录到',
rightIndex : 34,
order : 27,
fn : pageObjMgr['quote']
});
reg({
key : 'delete',
type : 'MetaViewDatas',
desc : wcm.LANG.METAVIEWDATA_129 || '删除记录',
title : wcm.LANG.METAVIEWDATA_57 || '将记录放入废稿箱',
rightIndex : 33,
order : 28,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'setright',
type : 'MetaViewData',
desc : wcm.LANG.METAVIEWDATA_117 || '设置这条记录权限',
title : wcm.LANG.METAVIEWDATA_117 || '设置这条记录权限',
rightIndex : 61,
order : 14,
fn : pageObjMgr['setright']
});
reg({
key : 'exportall',
type : 'MetaViewDataInChannel',
desc : wcm.LANG.METAVIEWDATA_110 || '导出所有记录',
title : wcm.LANG.METAVIEWDATA_110 || '导出所有记录...',
rightIndex : 34,
order : 29,
fn : pageObjMgr['exportall']
});
})();

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

Ext.ns('wcm.domain.photoMgr');
(function(){
var m_oMgr = wcm.domain.photoMgr;
var m_nDocumentObjType = 605;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
function uploadCallback1(fpInfo){
setTimeout(function(){
FloatPanel.open(fpInfo.src,
fpInfo.title,uploadCallback);
}, 200);
return false;
}
function uploadCallback(fpInfo){
FloatPanel.close();
setTimeout(function(){
FloatPanel.open(fpInfo.src,
fpInfo.title);
}, 200);
return false;
}
function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
var DIALOG_PHOTO_INFO = 'photo_info_dialog';
var aTop = (top.actualTop||top);
var link = WCMConstants.WCM6_PATH + 'photo/photo_info.jsp';
aTop.m_eDocumentInfo = wcm.CrashBoarder.get(DIALOG_PHOTO_INFO).show({
title : wcm.LANG.PHOTO_CONFIRM_63 || '系统提示信息',
src : link,
width: '500px',
height: '220px',
reloadable : true,
params : _params,
maskable : true,
callback : _fDoAfterDisp
});
}
function registerOnFinish(_title,_param,_sparam,event){
var mytop = top || top.actualTop;
mytop.m_eSelector = wcm.CrashBoarder.get(DIALOG_IMAGEKIND_SELECTOR).show({
title : _title,
src : WCMConstants.WCM6_PATH +'photo/channel_select.html',
width: '250px',
height: '300px',
reloadable : false,
params : _param,
maskable : true,
callback : function(_args){
if(!_args.ids || _args.ids.length == 0){
return false;
}
if(_args.mode == "radio"){
if(_args.ids && m_nCurrId != _args.ids){
var oPostData = Object.extend(_sparam,{ToChannelId:_args.ids});
BasicDataHelper.call("wcm6_viewdocument","move",oPostData,true,function(_transport,_json){
var r = $v(_json,"Reports.Is_Success");
_json.REPORTS.TITLE = wcm.LANG.PHOTO_CONFIRM_129 || "移动图片";
Ext.Msg.report(_json,wcm.LANG.PHOTO_CONFIRM_131 || '图片移动结果',func);
event.getObjs().afterdelete();
});
}
}else{
if(_args.ids && _args.ids.length > 0 && m_nCurrId != _args.ids){
var oPostData = Object.extend(_sparam,{ToChannelIds:_args.ids});
var func = function(){
FloatPanel.close();
};
BasicDataHelper.call("wcm6_viewdocument","quote",oPostData,true,function(_transport,_json){
_json.REPORTS.TITLE = wcm.LANG.PHOTO_CONFIRM_130 || "引用图片";
Ext.Msg.report(_json,wcm.LANG.PHOTO_CONFIRM_132 || '图片引用结果',func);
event.getObjs().afteredit();
});
}
}
}
});
}
function $openCenterWin(_sUrl, _sName, _width, _height, _sFeature){
if(!_width || !_height){
$openCentralWin(_sUrl, _sName);
return;
}
var _WIN_WIDTH = window.screen.availWidth;
var _WIN_HEIGHT = window.screen.availHeight;
var l = (_WIN_WIDTH - _width) / 2;
var t = (_WIN_HEIGHT - _height) / 2;
sFeature = "left="+l + ",top=" + t +",width=" 
+ _width + ",height=" + _height + "," + _sFeature;
var sName = _sName || "";
sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
var oWin = window.open(_sUrl, sName, sFeature);
if(oWin) oWin.focus();
}
function $openMaxWin(_sUrl, _sName, _bResizable){
var nWidth = window.screen.width - 12;
var nHeight = window.screen.height - 60;
var nLeft = 0;
var nTop = 0;
var sName = _sName || "";
var oWin = window.open(_sUrl, sName, "resizable=" + (_bResizable == true ? "yes" : "no") + ",top=" + nTop + ",left=" + nLeft + ",menubar =no,toolbar =no,width=" + nWidth + ",height=" + nHeight + ",scrollbars=yes,location =no,titlebar=no");
if(oWin)oWin.focus();
}
function __openPreviewPage(_sIds, _iObjectType,_extraParams){
window.open('/wcm/app/preview/index.htm?objectType='+ _iObjectType + '&objectids=' + _sIds + '&'+$toQueryStr(_extraParams||{}), 'preview_page');
}
var m_nCurrId = 0;
var DIALOG_IMAGEKIND_SELECTOR = "Dialog_ImageKind_Selector";
ChannelSelector = Class.create("ChannelSelector");
ChannelSelector.prototype = {
initialize : function(){
},
selectMainKind : function(_param,event){
var pContext = {mode:"radio",SelectedIds:event.getHost().getId(),CurrId:event.getHost().getId(),Type:event.getHost().getType()};
registerOnFinish(wcm.LANG.PHOTO_CONFIRM_52 || "选择主分类",pContext,_param,event);
},
selectOtherKinds : function(_param,event){
var pContext = {mode:"multi",SelectedIds:event.getHost().getId(),CurrId:event.getHost().getId(),Type:event.getHost().getType()};
registerOnFinish(wcm.LANG.PHOTO_CONFIRM_53 || "选择其它分类",pContext,_param,event);
},
setParams : function(_params){
Object.extend(this.m_oParams,_params ||{});
}
};
$channelSelector = new ChannelSelector();
Ext.apply(wcm.domain.photoMgr, {
upload : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
FloatPanel.open(WCMConstants.WCM6_PATH +
'photo/photo_upload.jsp?' + $toQueryStr(oPageParams),
wcm.LANG.PHOTO_CONFIRM_61 || '上传图片', uploadCallback);
},
edit : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
Object.extend(oPageParams,{"DocId":docid});
FloatPanel.open(WCMConstants.WCM6_PATH +"photo/photodoc_edit.jsp?"+$toQueryStr(oPageParams),wcm.LANG.PHOTO_CONFIRM_67 || "编辑图片信息",CMSObj.afteredit(event));
},
"delete" : function(event){
var oPageParams = event.getContext();
var _sDocIds = event.getIds();
_sDocIds = _sDocIds + '';
var nCount = (_sDocIds.indexOf(',') == -1) ? 1 : _sDocIds.split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var browserEvent = event.browserEvent;
var bDrop = !!(browserEvent && 
browserEvent.type=='keydown' && browserEvent.shiftKey);
var params = {
objectids: _sDocIds,
operation: bDrop ? '_forcedelete' : '_trash'
}
doOptionsAfterDisplayInfo(params, function(){
var aTop = (top.actualTop||top);
BasicDataHelper.call("wcm6_viewdocument", 'delete', Object.extend(oPageParams,{"ObjectIds": _sDocIds, "drop": bDrop}), true, 
function(){
event.getObjs().afterdelete();
}
);
}.bind(this));
},
importsysphoto : function(event){
var oPageParams = {};
var host = event.getHost();
var hostid = host.getId();
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
FloatPanel.open(WCMConstants.WCM6_PATH +
'photo/photo_importsyspics.jsp?' + $toQueryStr(oPageParams),
wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片', uploadCallback);
},
importphotos : function(event){
var oPageParams = {};
var host = event.getHost();
var hostid = host.getId();
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
if(!oPageParams.channelid){
FloatPanel.open(WCMConstants.WCM6_PATH + 'photo/photos_siteimport_step1.html?' + $toQueryStr(oPageParams), wcm.LANG.PHOTO_CONFIRM_112 || '选择要批量上传图片的栏目', uploadCallback1);
return;
}
FloatPanel.open(WCMConstants.WCM6_PATH +'photo/photos_import.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PHOTO_CONFIRM_113 || '批量上传图片',
function(){
event.getObjs().afterdelete();
});
},
preview : function(event){
var host = event.getHost();
var hostid = host.getId();
var oParams = {
FolderId : hostid,
FolderType : host.getType()=="website"? 103:101,
'isPhoto':true
};
var _sIds = event.getIds();
wcm.domain.PublishAndPreviewMgr.preview(_sIds,600,oParams,"wcm6_viewdocument");
},
basicpublish : function(event){
var oPageParams = event.getContext();
var _sIds = event.getIds();
var postData = Object.extend({},{
'ObjectIds' : _sIds,
'isPhoto':true
});
BasicDataHelper.call("wcm6_viewdocument", "basicpublish", postData, true, function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
}.bind(this));
},
detailpublish : function(event){
var oPageParams = event.getContext();
var _sIds = event.getIds();
var postData = Object.extend({},{
'ObjectIds' : _sIds,
'isPhoto':true
});
BasicDataHelper.call("wcm6_viewdocument", "detailpublish", postData, true, function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
}.bind(this));
},
directpublish : function(event){
var oPageParams = event.getContext();
var _sIds = event.getIds();
var postData = Object.extend({},{
'ObjectIds' : _sIds,
'isPhoto':true
});
BasicDataHelper.call("wcm6_viewdocument", "directpublish", postData, true, function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
}.bind(this));
},
quote : function(event){
var oPageParams = event.getContext();
var _sDocIds = event.getIds();
var host = event.getHost();
var currId = host.getType()=="website" ? 0:host.getId();
var param ={ObjectIds:_sDocIds,channelids:currId,isPhoto:true};
$channelSelector.setParams(param);
$channelSelector.selectOtherKinds(param,event);
},
move : function(event){
var oPageParams = event.getContext();
var _sDocIds = event.getIds();
var host = event.getHost();
var param ={ObjectIds:_sDocIds,FromChannelId:currId,isPhoto:true};
var currId = (host.getType()=="website"?0:host.getId());
$channelSelector.setParams(param);
$channelSelector.selectMainKind(param,event);
},
docpositionset : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var objType = event.getObjs().getAt(0).objType;
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType});
FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams), (objType.trim()=='photo'?(wcm.LANG.PHOTO_CONFIRM_8 || '图片'):(wcm.LANG.PHOTO_CONFIRM_68 || '文档')) +
(wcm.LANG.PHOTO_CONFIRM_69 || '-调整顺序'), CMSObj.afteredit(event));
},
commentmgr : function(event){
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
var host = event.getHost();
var hostid = host.getId();
var oParams = Ext.apply({
DocumentId : docid,
ChannelId :hostid,
SiteId : 0
}, parseHost(event.getHost()));
var sUrl = WCMConstants.WCM_ROOTPATH +'comment/comment_mgr.jsp?'
+ $toQueryStr(oParams);
$openMaxWin(sUrl);
},
recallpublish : function(event){
var host = event.getHost();
var hostid = host.getId();
var _sIds = event.getIds();
var oParams = {
FolderId : hostid,
FolderType : host.getType()=="website"? 103:101
};
var sHtml = (wcm.LANG.PHOTO_CONFIRM_151 ||"确定要<font color=\'red\' style=\'font-size:14px;\'>撤销发布</font>所选图片么？将<font color=\'red\' style=\'font-size:14px;\'>不可逆转</font>");
Ext.Msg.confirm(sHtml,{
yes : function(){
wcm.domain.PublishAndPreviewMgr.publish(_sIds, 600, 'recallPublish',oParams,"wcm6_viewdocument");
}
});
},
setright : function(event){
$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=" +
m_nDocumentObjType + "&ObjId=" + event.getObjs().getPropertys("docId",0).join(),
"document_right_set", 900, 600, "resizable=yes");
},
changestatus : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var sId = event.getIds().join();
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
Object.extend(oPageParams,{"DocumentId":docid,"ObjectIds":sId,"IsPhoto":true,'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PHOTO_CONFIRM_70 || '图片-改变状态', CMSObj.afteredit(event));
},
downLoadOrigin : function(event){
var oparam = {
PhotoId : event.getObjs().getAt(0).getPropertyAsInt("docId", 0)
}
BasicDataHelper.JspRequest(WCMConstants.WCM6_PATH+'photo/photo_download.jsp',oparam,false,function(transport){
var sFileUrl = transport.responseText;
var frm = $MsgCenter.getActualTop().$('iframe4download');
if(frm==null){
frm = $MsgCenter.getActualTop().document.createElement('IFRAME');
frm.id = "iframe4download";
frm.style.display = 'none';
$MsgCenter.getActualTop().document.body.appendChild(frm);
}
sFileUrl = WCMConstants.WCM_ROOTPATH +"file/read_file.jsp?DownName=DOCUMENT&FileName=" + sFileUrl;
frm.src = sFileUrl;
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.photoMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_71 || '编辑这幅图片',
title : wcm.LANG.PHOTO_CONFIRM_71 || '编辑这幅图片',
rightIndex : 32,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'E'
});
reg({
key : 'delete',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_72 || '删除这幅图片',
title : wcm.LANG.PHOTO_CONFIRM_72 || '删除这幅图片',
rightIndex : 33,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'preview',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_73 || '预览这幅图片',
title : wcm.LANG.PHOTO_CONFIRM_73 || '预览这幅图片',
rightIndex : 38,
order : 3,
fn : pageObjMgr['preview'],
quickKey : 'Y'
});
reg({
key : 'basicpublish',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_74 || '发布这幅图片',
title : wcm.LANG.PHOTO_CONFIRM_74 || '发布这幅图片',
rightIndex : 39,
order : 4,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'move',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
title : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
rightIndex : 33,
order : 5,
fn : pageObjMgr['move']
});
reg({
key : 'quote',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
title : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
rightIndex : 34,
order : 6,
fn : pageObjMgr['quote']
});
reg({
key : 'changestatus',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_77 || '改变这幅图片的状态',
title : wcm.LANG.PHOTO_CONFIRM_77 || '改变这幅图片的状态',
rightIndex : 35,
order : 7,
fn : pageObjMgr['changestatus']
});
reg({
key : 'upload',
type : 'photoInChannel',
desc : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片',
title : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片...',
rightIndex : 31,
order : 8,
fn : pageObjMgr['upload'],
quickKey : 'N'
});
reg({
key : 'importsysphoto',
type : 'photoInChannel',
desc : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片',
title : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片...',
rightIndex : 31,
order : 9,
fn : pageObjMgr['importsysphoto']
});
reg({
key : 'importphotos',
type : 'photoInChannel',
desc : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片',
title : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片...',
rightIndex : 31,
order : 9,
fn : pageObjMgr['importphotos']
});
reg({
key : 'upload',
type : 'photoInSite',
desc : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片',
title : wcm.LANG.PHOTO_CONFIRM_78 || '上传新图片...',
rightIndex : 31,
order : 10,
fn : pageObjMgr['upload'],
quickKey : 'N'
});
reg({
key : 'importsysphoto',
type : 'photoInSite',
desc : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片',
title : wcm.LANG.PHOTO_CONFIRM_79 || '导入系统图片...',
rightIndex : 31,
order : 11,
fn : pageObjMgr['importsysphoto']
});
reg({
key : 'importphotos',
type : 'photoInSite',
desc : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片',
title : wcm.LANG.PHOTO_CONFIRM_111 || '批量上传图片...',
rightIndex : 31,
order : 11,
fn : pageObjMgr['importphotos']
});
reg({
key : 'delete',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_80 || '删除这些图片',
title : wcm.LANG.PHOTO_CONFIRM_80 || '删除这些图片',
rightIndex : 33,
order : 12,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'preview',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_81 || '预览这些图片',
title : wcm.LANG.PHOTO_CONFIRM_81 || '预览这些图片',
rightIndex : 38,
order : 13,
fn : pageObjMgr['preview'],
quickKey : 'Y'
});
reg({
key : 'basicpublish',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_82 || '发布这些图片',
title : wcm.LANG.PHOTO_CONFIRM_82 || '发布这些图片',
rightIndex : 39,
order : 14,
fn : pageObjMgr['basicpublish'],
quickKey : 'P'
});
reg({
key : 'move',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
title : wcm.LANG.PHOTO_CONFIRM_75 || '重新分类',
rightIndex : 33,
order : 15,
fn : pageObjMgr['move']
});
reg({
key : 'quote',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
title : wcm.LANG.PHOTO_CONFIRM_76 || '增加分类',
rightIndex : 34,
order : 16,
fn : pageObjMgr['quote']
});
reg({
key : 'changestatus',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_83 || '改变这些图片的状态',
title : wcm.LANG.PHOTO_CONFIRM_83 || '改变这些图片的状态',
rightIndex : 35,
order : 17,
fn : pageObjMgr['changestatus']
});
reg({
key : 'detailpublish',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_135 ||'仅发布这幅图片细览',
title : '仅发布这幅图片细览...',
rightIndex : 39,
order : 18,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_160 ||'直接发布这幅图片',
title : wcm.LANG.PHOTO_CONFIRM_161 ||'发布这幅图片,同时发布此图片的所有引用图片',
rightIndex : 39,
order : 18,
fn : pageObjMgr['directpublish']
});
reg({
key : 'detailpublish',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_136 ||'仅发布这些图片细览',
title : wcm.LANG.PHOTO_CONFIRM_136 ||'仅发布这些图片细览...',
rightIndex : 39,
order : 19,
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_162 ||'直接发布这些图片',
title : wcm.LANG.PHOTO_CONFIRM_163 ||'发布这些图片,同时发布这些图片的所有引用图片',
rightIndex : 39,
order : 19,
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_84 || '撤销发布这幅图片',
title : wcm.LANG.PHOTO_CONFIRM_85 || '撤销发布这幅图片,撤回已发布目录或页面',
rightIndex : 39,
order : 20,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'recallpublish',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_86 || '撤销发布这些图片',
title : wcm.LANG.PHOTO_CONFIRM_87 || '撤销发布这些图片,撤回已发布目录或页面',
rightIndex : 39,
order : 21,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'setright',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_88 || '设置这幅图片的权限',
title :'设置这幅图片的权限...',
rightIndex : 61,
order : 22,
fn : pageObjMgr['setright']
});
reg({
key : 'setright',
type : 'photos',
desc : wcm.LANG.PHOTO_CONFIRM_89 || '设置这些图片的权限',
title : wcm.LANG.PHOTO_CONFIRM_89 || '设置这些图片的权限...',
rightIndex : 61,
order : 23,
fn : pageObjMgr['setright']
});
reg({
key : 'docpositionset',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
title : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
rightIndex : 62,
order : 24,
fn : pageObjMgr['docpositionset'],
isVisible : function(event){
if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
return true;
return false;
}
});
reg({
key : 'commentmgr',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_133 ||'管理评论',
title : wcm.LANG.PHOTO_CONFIRM_134 ||'管理图片的评论',
rightIndex : 8,
order : 25,
fn : pageObjMgr['commentmgr'],
isVisible : function(event){
try{
return $MsgCenter.getActualTop().g_IsRegister['comment'];
}catch(err){
return false;
}
}
});
reg({
key : 'downLoadOrigin',
type : 'photo',
desc : wcm.LANG.PHOTO_CONFIRM_169 ||'下载原图',
title : wcm.LANG.PHOTO_CONFIRM_169 ||'下载原图',
rightIndex : 32,
order : 26,
fn : pageObjMgr['downLoadOrigin']
});
})();

Ext.ns('wcm.domain.PhotoRecycleMgr');
(function(){
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
var m_oMgr = wcm.domain.PhotoRecycleMgr ={
serviceId : 'wcm61_viewdocument',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.PhotoRecycleMgr, {
view : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var pageContext = event.getContext();
var oParams = Ext.apply({
DocumentId : pageContext.docid,
ChnlDocId : event.getIds(),
FromRecycle : 1
},parseHost(event.getHost()));
$openMaxWin(WCMConstants.WCM6_PATH +
'document/document_show.jsp?' + $toQueryStr(oParams));
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
}
if(confirm(wcm.LANG.PHOTO_CONFIRM_143 || '您当前所执行的操作将彻底删除废稿箱中所有图片,您确定仍要继续清空当前废稿箱?')){
ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_144 || '删除图片...');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
ProcessBar.close();
$MsgCenter.getActualTop().isDeletingAll = true;
event.getObjs().afterdelete();
});
}
},
'delete' : function(event){
var sIds = event.getIds();
var hostId = event.getHost().getId();
var _params = event.getContext();
var params = {
objectids: sIds,
operation: '_delete'
}
var aIds = sIds;
if(String.isString(aIds)){
aIds = aIds.split(",");
}
if(aIds.length >= 50){
if(confirm(wcm.LANG.PHOTO_CONFIRM_146 || "确实要删除这些图片吗?")){
m_oMgr._delete0(event);
}
return;
}
m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
},
_delete0 : function(event){
ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_144 || '删除图片...');
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var _params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
Object.extend(_params, {ObjectIds: sIds, drop: true});
m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', _params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
},
doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
var DIALOG_PHOTO_INFO = 'photo_info_dialog';
wcm.CrashBoarder.get(DIALOG_PHOTO_INFO).show({
title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'photo/photo_info.jsp',
width:'520px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
},
restoreall : function(event, operItem){
if(confirm(wcm.LANG.PHOTO_CONFIRM_142 || '您确定要还原所有图片?')){
return m_oMgr.restore(event, operItem, true);
}
},
restore : function(event, operItem, _bRestoreAll){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
operation: '_restore',
special: true
}
if(_bRestoreAll == true) {
params['restoreAll'] = true;
Object.extend(params, {
channelids: (hostType == 101)?hostId:'',
siteids: (hostType == 103)?hostId:''
});
$MsgCenter.getActualTop().isRestoringAll = true;
}else{
Object.extend(params, {
objectids: event.getIds()
});
}
var restore = function(){
ProcessBar.start(wcm.LANG.PHOTO_CONFIRM_139 || '还原图片..');
var postData = {};
if(_bRestoreAll == true) {
postData['restoreAll'] = true;
Object.extend(postData, {
channelid: (hostType == 101)?hostId:'',
siteid: (hostType == 103)?hostId:''
});
}else{
Object.extend(postData, {
objectids: event.getIds()
});
}
m_oMgr.getHelper().call(m_oMgr.serviceId, 'restore', postData, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}.bind(this);
if(_bRestoreAll){
restore();
}else{
m_oMgr.doOptionsAfterDisplayInfo(params, restore.bind(this));
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.PhotoRecycleMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(!context.gridInfo) return true;
return context.gridInfo.RecordNum > 0;
};
reg({
key : 'restore',
type : 'photorecycle',
desc : wcm.LANG.PHOTO_CONFIRM_137 || '还原这幅图片',
rightIndex : 33,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'photorecycle',
desc :wcm.LANG.PHOTO_CONFIRM_72 || '删除这幅图片',
title:'删除这幅图片',
rightIndex : 33,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'photorecycleInChannel',
desc : wcm.LANG.PHOTO_CONFIRM_140 || '清空废稿箱',
rightIndex : 33,
order : 3,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'photorecycleInChannel',
desc : wcm.LANG.PHOTO_CONFIRM_141 || '还原所有图片',
rightIndex : 33,
order : 4,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'deleteall',
type : 'photorecycleInSite',
desc : wcm.LANG.PHOTO_CONFIRM_140 || '清空废稿箱',
rightIndex : 33,
order : 5,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'photorecycleInSite',
desc : wcm.LANG.PHOTO_CONFIRM_141 || '还原所有图片',
rightIndex : 33,
order : 6,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'restore',
type : 'photorecycles',
desc : wcm.LANG.PHOTO_CONFIRM_138 || '还原这些图片',
rightIndex : 33,
order : 7,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'photorecycles',
desc : wcm.LANG.PHOTO_CONFIRM_80 || '删除这些图片',
rightIndex : 33,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.domain.PublishAndPreviewMgr');
(function(){
var m_oMgr = wcm.domain.PublishAndPreviewMgr={
serviceId : 'wcm6_publish',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.PublishAndPreviewMgr, {
publish : function(_sIds, _iObjectType, _sMethodName ,_oExtraParams, _sServiceId){
_sMethodName = _sMethodName || 'publish';
var postData = Object.extend(_oExtraParams || {},{
'ObjectIds' : _sIds, 
'ObjectType' : _iObjectType
});
m_oMgr.getHelper().call((_sServiceId || m_oMgr.serviceId), _sMethodName, postData, true, function(_transport,_json){
m_oMgr.doAfterPublish(postData, _sMethodName,_transport,_json);
}.bind(this));
},
doAfterPublish : function(_postData, _sMethodName,_transport,_json){
if(_json!=null&&_json["REPORTS"]){
var oReports = _json["REPORTS"];
var stJson = com.trs.util.JSON;
var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
var title = stJson.value(oReports, "TITLE");
if(bIsSuccess=='false'){
if(title.indexOf(wcm.LANG.PUBLISH_7 || "图片") != -1){
if(oReports.REPORT.length){
for(var i =0;i< oReports.REPORT.length;i++){
var currItem = stJson.value(oReports.REPORT[i], "TITLE");
if( currItem != null)
oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
}
}else{
oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
}
}
if(title.indexOf(wcm.LANG.PUBLISH_8 || "视频") != -1){
if(oReports.REPORT.length){
for(var i =0;i< oReports.REPORT.length;i++){
var currItem = stJson.value(oReports.REPORT[i], "TITLE");
if( currItem != null)
oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}else{
oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}
Ext.Msg.report(_json,wcm.LANG.PUBLISH_1||'发布校验结果');
return;
}
}
var bIsPublished = (_sMethodName.toLowerCase() != 'recallpublish');
Ext.Msg.$timeAlert(wcm.LANG.PUBLISH_2||'已经将您的发布操作提交到后台了...', 3);
var params = Object.extend({}, _postData);
params['StatusName'] = bIsPublished ? wcm.LANG.PUBLISH_3||'已发' : wcm.LANG.PUBLISH_4||'已否';
params['StatusValue'] = bIsPublished ? '10' : '15';
var objectids = params['ObjectIds'];
if(!Array.isArray(objectids)) {
params['ObjectIds'] = [objectids];
}
},
_checkPreview_ : function(_sIds, _iObjectType, _extraParams, _sServiceId){
m_oMgr.getHelper().call((_sServiceId || m_oMgr.serviceId),'preview', Object.extend(Object.extend({},_extraParams||{}),{
objectIds: _sIds, 
objectType: _iObjectType
}), false, function(transport, json){
var urlCount = com.trs.util.JSON.value(json, "URLCOUNT");
if((''+_sIds).indexOf(",") >= 0){
if(urlCount == 0){
var message = "";
var dataArray = com.trs.util.JSON.array(json,"DATA");
for (var i = 0; i < dataArray.length; i++){
if(com.trs.util.JSON.value(json, "Title")!=null){
if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_7 || "图片") != -1)
dataArray[i].EXCEPTION = dataArray[i].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_8 || "视频") != -1)
dataArray[i].EXCEPTION = dataArray[i].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
message += $transHtml(dataArray[i].EXCEPTION) + "<br>";
}
Ext.Msg.$alert(message);
}else{
m_oMgr.__openPreviewPage(_sIds, _iObjectType, _extraParams);
}
}else{
if(urlCount == 0){
if(com.trs.util.JSON.value(json, "Title")!=null){
if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_7 || "图片") != -1)
json.DATA[0].EXCEPTION = json.DATA[0].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_7 || "图片");
if(com.trs.util.JSON.value(json, "Title").indexOf(wcm.LANG.PUBLISH_8 || "视频") != -1)
json.DATA[0].EXCEPTION = json.DATA[0].EXCEPTION.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
Ext.Msg.fault({
message : (json.DATA.length>0)?json.DATA[0].EXCEPTION:"",
detail : (json.DATA.length>0)?json.DATA[0].EXCEPTIONDETAIL:""
},wcm.LANG.PUBLISH_5||'预览出错');
}else{
var urls = com.trs.util.JSON.value(json, "DATA.0.URLS");
if(urls.length == 1){
var pObjId = _sIds;
var pObjectType = _iObjectType;
if(pObjectType == 600 || pObjectType == "600") {
pObjectType = "605";
}
if(m_oMgr.isMobileObj(pObjId,pObjectType)) {
window.open(WCMConstants.WCM6_PATH+"preview/mobilePreview/mobilePreviewPage.html?URL="+urls);
}else {
window.open(urls);
}
return;
}
m_oMgr.__openPreviewPage(_sIds, _iObjectType, _extraParams);
}
}
}.bind(this));
},
preview : function(_sIds, _iObjectType,_extraParams, _sServiceId){
m_oMgr._checkPreview_(_sIds,_iObjectType,_extraParams, _sServiceId);
},
__openPreviewPage : function(_sIds, _iObjectType,_extraParams){
window.open(WCMConstants.WCM6_PATH + 'preview/index.htm?objectType='+ _iObjectType + '&objectids=' + _sIds + '&'+$toQueryStr(_extraParams||{}), 'preview_page');
},
isMobileObj : function(objectId,objectType){
if(!objectType || !objectId) {
return false;
}
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isMobileObject',
method : 'GET',
parameters : 'objectid='+objectId +'&objecttype='+objectType,
asyn : false
});
if(!transport) {
transport = new ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isMobileObject',
method : 'GET',
parameters : 'objectid='+objectId +'&objecttype='+objectType,
asyn : false
}).transport;
}
var json = parseXml(loadXml(transport.responseText));
if(json.RESULT == 'true'){
return true;
}
return false;
}
});
})();

Ext.ns('wcm.domain.PublishDistributionMgr');
(function(){
var m_oMgr = wcm.domain.PublishDistributionMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.PublishDistributionMgr, {
add : function(event){
var oPageParams = event.getContext();
Object.extend(oPageParams,{"ObjectId":0});
Object.extend(oPageParams,{"siteId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PUBLISHDISTRUBUTION_VALID_1 || '新建站点分发', CMSObj.afteradd(event)
);
},
edit : function(event){
var oPageParams = event.getContext();
var sId = event.getIds().join();
Object.extend(oPageParams,{"ObjectId":sId});
Object.extend(oPageParams,{"siteId":event.getHost().getId()});
FloatPanel.open(WCMConstants.WCM6_PATH +
'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PUBLISHDISTRUBUTION_VALID_3 || '修改站点分发', CMSObj.afteredit(event)
);
},
"delete" : function(event){
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = String.format("确定要删除这{0}个分发点?",sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
BasicDataHelper.call("wcm6_distribution", 
'delete', //远端方法名 
Object.extend(PageContext.params, {"ObjectIds": sId}), //传入的参数
true, 
function(){
event.getObjs().afterdelete();
}
);
}
});
},
"enable" : function(event){
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var params = {};
var sResult = String.format("确定要启用这{0}个分发点?",sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
Object.extend(params, {"ObjectIds" :sId.join(),'Enable':true});
BasicDataHelper.call('wcm6_distribution', 
'updateStatus', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
},
"disable" : function(event){
var sId = event.getIds();
var nCount = sId.toString().split(',').length;
var params = {};
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = String.format('确定要禁用这{0}个分发点?',sHint);
Ext.Msg.confirm(sResult,{
yes : function(){
Object.extend(params, {"ObjectIds" :sId.join(),'Enable':false});
BasicDataHelper.call('wcm6_distribution', 
'updateStatus', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
},
disableall : function(event){
var params = {"FolderType" : 103};
Ext.Msg.confirm(wcm.LANG.PUBLISHDISTRUBUTION_VALID_12 ||'确实要将所有分发点都禁用吗?',{
yes : function(){
Object.extend(params, {'FolderId':event.getHost().getId()});
Object.extend(params, {'Enable':false});
BasicDataHelper.call('wcm6_distribution', 
'updateStatusAll', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
},
enableall : function(event){
var params = {"FolderType" : 103};
Ext.Msg.confirm(wcm.LANG.PUBLISHDISTRUBUTION_VALID_13 ||'确实要将所有分发点都启用吗?',{
yes : function(){
Object.extend(params, {'FolderId':event.getHost().getId()});
Object.extend(params, {'Enable':true});
BasicDataHelper.call('wcm6_distribution', 
'updateStatusAll', //远端方法名 
params, 
false, 
function(){
event.getObjs().afteredit();
}
);
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.PublishDistributionMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_14 ||'修改这个分发点',
title:'修改这个分发点',
rightIndex : 1,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'disable',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_15 ||'禁用这个分发点',
title:'禁用这个分发点',
rightIndex : 1,
order : 2,
fn : pageObjMgr['disable']
});
reg({
key : 'enable',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_16 ||'启用这个分发点',
title:'启用这个分发点',
rightIndex : 1,
order : 3,
fn : pageObjMgr['enable']
});
reg({
key : 'delete',
type : 'publishdistribution',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_17 ||'删除这个分发点',
title:'删除这个分发点',
rightIndex : 1,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'publishdistributionInSite',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_18 ||'新建分发点',
title:'新建分发点',
rightIndex : 1,
order : 5,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'disableall',
type : 'publishdistributionInSite',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_19 ||'禁用所有分发点',
title:'禁用所有分发点',
rightIndex : 1,
order : 6,
fn : pageObjMgr['disableall']
});
reg({
key : 'enableall',
type : 'publishdistributionInSite',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_20 ||'启用所有分发点',
title:'启用所有分发点',
rightIndex : 1,
order : 7,
fn : pageObjMgr['enableall']
});
reg({
key : 'disable',
type : 'publishdistributions',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_21 ||'禁用这些分发点',
title:'禁用这些分发点',
rightIndex : 1,
order : 8,
fn : pageObjMgr['disable']
});
reg({
key : 'enable',
type : 'publishdistributions',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_22 || '启用这些分发点',
title:'启用这些分发点',
rightIndex : 1,
order : 9,
fn : pageObjMgr['enable']
});
reg({
key : 'delete',
type : 'publishdistributions',
desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_17 ||'删除这些分发点',
title:'删除这些分发点',
rightIndex : 1,
order : 10,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.domain.RegionMgr');
(function(){
var m_oRegionMgr = wcm.domain.RegionMgr;
var serviceId = 'wcm6_regioninfo';
function addOrEdit(event, _addOrEdit){
var websiteId = 0;
var channelId = 0;
var objectId = 0;
var host = event.getHost();
var hostId = host.getId();
if(host.getIntType() == '103')
websiteId = hostId;
else if(host.getIntType() == '101')
channelId = hostId;
if(_addOrEdit == 0)
objectId = event.getIds()[0];
FloatPanel.open(WCMConstants.WCM6_PATH + 'region/region.html?channelId=' + channelId + '&websiteId='+ websiteId + '&ObjectId=' + objectId, 
wcm.LANG.CHANNEL_81 ||'导读管理', function(_arg){
FloatPanel.close();
if(objectId > 0)
CMSObj.afteredit(event)(_arg);
else 
CMSObj.afteradd(event)(_arg);
}
);
}
Ext.apply(wcm.domain.RegionMgr, {
add : function(event){
addOrEdit(event, 1);
},
edit : function(event){
addOrEdit(event, 0);
},
editDetail : function(event){
var websiteId = 0;
var channelId = 0;
var objectId = event.getIds()[0];
var host = event.getHost();
var hostId = host.getId();
if(host.getIntType() == '103')
websiteId = hostId;
else if(host.getIntType() == '101')
channelId = hostId;
wcm.CrashBoarder.get('Trs_Region_Set_Deatail').show({
maskable:true,
reloadable : true,
title : wcm.LANG.region_2011 || '导读详细设置页面',
src : './region/cell.html',
width:'850px',
height:'400px',
params : {
channelId : channelId || 0, 
websiteId : websiteId || 0,
objectId : objectId || 0
},
callback : function(_arg){
CMSObj.afteredit(event)(_arg);
this.close();
}
});
},
showEmploy : function(event){
var objectId = event.getIds()[0];
wcm.CrashBoarder.get('Trs_Region_Show_Employ').show({
maskable:true,
reloadable : true,
title : wcm.LANG.region_2012 || '导读使用信息',
src : './region/region_employ_show.jsp',
width:'280px',
height:'300px',
params : {
objectId : objectId || 0
}
});
},
'delete' : function(event){
var _arIds = event.getIds();
if(_arIds.length == 0) return;
var nCount = _arIds.length;
if(typeof(_arIds) == 'string') {
nCount = _arIds.split(',').length;
}
var params = {
ObjectIds: _arIds, 
drop: true
};
if (confirm(String.format(wcm.LANG.region_2013 || '确实要将这{0}个导读删除吗?',nCount))){
BasicDataHelper.call(serviceId, 'delete', params, false, function(){
CMSObj.afterdelete(event)();
});
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.RegionMgr;
var reg = wcm.SysOpers.register;
if(!getParameter('ChannelId')){
reg({
key : 'add',
type : 'regionInSite',
isHost : true,
desc : wcm.LANG.region_2014 || '新建导读',
title: '新建导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'edit',
type : 'region',
desc : '修改导读',
title: '修改导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'editDetail',
type : 'region',
desc : '修改导读详细信息',
title: '修改导读详细信息...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['editDetail']
});
reg({
key : 'delete',
type : 'regions',
desc : wcm.LANG.region_2017 || '删除这些导读',
title: '删除这些导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'delete',
type : 'region',
desc : wcm.LANG.region_2018 || '删除这个导读',
title: '删除这个导读...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
}else{
reg({
key : 'add',
type : 'regionInChannel',
isHost : true,
desc : wcm.LANG.region_2014 || '新建导读',
title: '新建导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'edit',
type : 'region',
desc : wcm.LANG.region_2015 || '修改导读',
title: '修改导读...',
rightIndex : 48,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'editDetail',
type : 'region',
desc : wcm.LANG.region_2016 || '修改导读详细信息',
title: '修改导读详细信息...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['editDetail']
});
reg({
key : 'delete',
type : 'regions',
desc : wcm.LANG.region_2017 || '删除这些导读',
title: '删除这些导读...',
rightIndex : 13,
order : 1,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'delete',
type : 'region',
desc : wcm.LANG.region_2018 || '删除这个导读',
title: '删除这个导读...',
rightIndex : 48,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
}
})();

Ext.ns('wcm.domain.ReplaceMgr');
(function(){
var m_oMgr = wcm.domain.ReplaceMgr={
serviceId : 'wcm6_replace',
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.ReplaceMgr, {
add : function(event){
var host = event.getHost();
var hostId = host.getId();
var oparameters = "ChannelId="+hostId;
oparameters += "&ObjectId=0" 
FloatPanel.open(WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + oparameters, 
wcm.LANG.REPLACE_8||'新建替换内容',
CMSObj.afteradd(event)
);
},
edit : function(event){
var sId = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var oparameters = "ChannelId="+hostId;
oparameters += "&ObjectId=" + sId;
FloatPanel.open(WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + oparameters, 
wcm.LANG.REPLACE_EDIT_2||'修改替换内容',
CMSObj.afteredit(event)
);
},
"delete" : function(event){
var sIds = event.getIds() + '';
var hostId = event.getHost().getId();
var oPageParams = {
ChannelId : hostId
};
var nCount = (sIds.indexOf(',') == -1) ? 1:sIds.split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
if (confirm(String.format(wcm.LANG.REPLACE_CONFIRM||('确实要将这{0}个替换内容删除吗?'),sHint))){
m_oMgr.getHelper().call(m_oMgr.serviceId, 
'delete', //远端方法名 
Object.extend(oPageParams, {"ObjectIds": sIds}), //传入的参数
false, 
function(){
event.getObjs().afterdelete();
}
);
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ReplaceMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'replace',
desc : wcm.LANG.REPLACE_EDIT||'修改这个替换内容',
title:'修改这个替换内容',
rightIndex : 13,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'delete',
type : 'replace',
desc : wcm.LANG.REPLACE_DELETE||'删除这个替换内容',
title:'删除这个替换内容',
rightIndex : 13,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'replaceInChannel',
desc : wcm.LANG.REPLACE_NEW||'新建替换内容',
title:'新建替换内容',
rightIndex : 13,
order : 3,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'delete',
type : 'replaces',
desc : wcm.LANG.REPLACES_DELETE||'删除这些替换内容',
title:'删除这些替换内容',
rightIndex : 13,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.domain.SiteRecycleMgr');
(function(){
var m_oMgr = wcm.domain.SiteRecycleMgr ={
serviceId : 'wcm6_website',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.SiteRecycleMgr, {
restoreall : function(event, operItem){
return m_oMgr.restore(event, operItem, true);
},
restore : function(event, operItem, _bRestoreAll){
var sClue = '';
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var params = {
siteType : hostId
};
if(_bRestoreAll){
sClue = wcm.LANG.SITERECYCLE_11 || '确实要还原站点回收站中的所有站点吗?';
}else{
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
if(nCount == 1){
sClue = wcm.LANG.SITERECYCLE_14 || '确实要还原此站点吗?';
}else{
sClue = String.format(wcm.LANG.SITERECYCLE_15 || ('确实要还原这{0}个站点吗?'),nCount);
}
}
if (confirm(sClue)){
Object.extend(params, {objectids: sIds});
if(_bRestoreAll) {
params.RestoreAll = true;
}
ProcessBar.start(wcm.LANG.SITERECYCLE_24 || '还原站点..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'restoreSites', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var params = {
siteType : hostId
};
if (confirm(wcm.LANG.SITERECYCLE_12 || '确实要删除站点回收站中的所有站点吗?')){
ProcessBar.start(wcm.LANG.SITERECYCLE_25 || '删除站点..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycle', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
},
'delete' : function(event){
var sIds = event.getIds();
sIds = sIds + '';
var nCount = (sIds.indexOf(',') == -1) ? 1 : sIds.split(',').length;
var sCon = "";
if(nCount == 1){
sCon = wcm.LANG.SITERECYCLE_13 || "确实要删除此站点吗?";
}else{
sCon = String.format(wcm.LANG.SITERECYCLE_16 || ("确实要删除这{0}个站点吗?"),nCount);
}
if (confirm(sCon)){
ProcessBar.start(wcm.LANG.SITERECYCLE_25 || '删除站点..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'delete', {ObjectIds: sIds, drop: true}, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.SiteRecycleMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var context = event.getContext();
if(!context.gridInfo) return true;
return context.gridInfo.RecordNum > 0;
};
reg({
key : 'restore',
type : 'siterecycle',
desc : wcm.LANG.SITERECYCLE_1 || '还原这个站点',
rightIndex : 2,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'siterecycle',
desc : wcm.LANG.SITERECYCLE_3 || '删除这个站点',
rightIndex : 2,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'siterecycleHost',
desc : wcm.LANG.SITERECYCLE_4 || '清空回收站',
rightIndex : -1,
order : 3,
fn : pageObjMgr['deleteall'],
isVisible : fnIsVisible
});
reg({
key : 'restoreall',
type : 'siterecycleHost',
desc : wcm.LANG.SITERECYCLE_6 || '还原所有站点',
rightIndex : -1,
order : 4,
fn : pageObjMgr['restoreall'],
isVisible : fnIsVisible
});
reg({
key : 'restore',
type : 'siterecycles',
desc : wcm.LANG.SITERECYCLE_8 || '还原这些站点',
rightIndex : 2,
order : 5,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'siterecycles',
desc : wcm.LANG.SITERECYCLE_10 || '删除这些站点',
title: '删除这些站点...',
rightIndex : 2,
order : 6,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.domain.TemplateMgr');
(function(){
var m_oMgr = wcm.domain.TemplateMgr={
serviceId : 'wcm6_template',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.TemplateMgr, {
'new' : function(event){
var result = '';
var context = event.getContext();
var host = event.getHost();
if(context) {
if(host.getType() == null) {
result += $toQueryStr(context);
}else{
result = 'hosttype=' + host.getIntType();
result += '&hostid=' + host.getId();
}
}
result += '&objectid=0';
var params = event.getContext();
if(params && params.isTypeStub) {
result += '&typestub=1';
}
$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + result);
},
__getCurrentParams : function(event){
var result = '';
var context = event.getContext();
var host = event.getHost();
var bIsSingle = event.length()<=1;
if(context) {
if(host.getType() == null) {
result += $toQueryStr(context);
}else{
result = 'hosttype=' + host.getIntType();
result += '&hostid=' + host.getId();
}
}
result += '&' + (bIsSingle ? 'objectid' : 'objectids') + '=' + event.getIds();
return result;
},
edit : function(event){
var sParams = m_oMgr.__getCurrentParams(event);
var params = event.getContext();
if(params && params.isTypeStub) {
sParams += '&typestub=1';
}
$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + sParams);
},
'import' : function(event){
var sParams = m_oMgr.__getCurrentParams(event);
FloatPanel.open(WCMConstants.WCM6_PATH + 'template/template_import.html?' + sParams, wcm.LANG.TEMPLATE_24 || '模板导入', function(){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_TEMPLATE});
});
},
__checkNoRecords : function(oHostInfos){
if(oHostInfos.RecordNum <= 0) {
Ext.Msg.$fail(wcm.LANG.TEMPLATE_ALERT || '没有任何要操作的模板!');
return false;
}
return true;
},
__getCurrentPostData : function(event, _sRenderAllName){
var sTempIds = event.getIds().join(',');
var host = event.getHost();
var context = event.getContext();
var obj = event.getObjs().getAt(0);
var bIsHost = _sRenderAllName!=null;
var oPostData = Ext.apply({
objectids : bIsHost?'':sTempIds,
hostid : host.getId(),
hostType : host.getIntType()
});
if(bIsHost) {
oPostData = {};
oPostData[_sRenderAllName] = true;
Ext.apply(oPostData,context.get("pagecontext").params);
Ext.apply(oPostData,{PAGESIZE:-1});
}
return oPostData;
},
exportAll : function(event, operItem){
return m_oMgr['export'](event, operItem, 'exportAll');
},
'export' : function(event, operItem, _sRenderAllName){
var oHostInfos = event.getContext();
if(!m_oMgr.__checkNoRecords(oHostInfos)) {
return;
}
ProcessBar.start(wcm.LANG.TEMPLATE_143 || '执行模板导出..');
var oPostData = m_oMgr.__getCurrentPostData(event, _sRenderAllName);
m_oMgr.getHelper().call(m_oMgr.serviceId, 'export', oPostData, true, function(_oXMLHttp, _json){
ProcessBar.close();
var sFileUrl = _oXMLHttp.responseText;
var frm = document.getElementById("ifrmDownload");
if(frm == null) {
frm = document.createElement('iframe');
frm.style.height = 0;
frm.style.width = 0;
document.body.appendChild(frm);
}
sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=TEMPLATE&FileName=" + sFileUrl;
frm.src = sFileUrl;
}.bind(this));
},
checkAll : function(event, operItem){
return m_oMgr.check(event, operItem, 'CheckAll');
},
check : function(event, operItem, _sRenderAllName){
var oHostInfos = event.getContext();
var sTempIds = event.getIds();
var bIsSingle = event.length()<=1;
if(!m_oMgr.__checkNoRecords(oHostInfos)) {
return;
}
if(!confirm(wcm.LANG.TEMPLATE_CONFIRM_0 || '此操作将校验模板的语法和文法细则,可能需要较长时间.确实要进行模板校验吗?')) {
return;
}
ProcessBar.start(wcm.LANG.TEMPLATE_49||'校验模板..');
var oPostData = m_oMgr.__getCurrentPostData(event, _sRenderAllName);
m_oMgr.getHelper().call(m_oMgr.serviceId, 'check', oPostData, true, function(_trans, _json){
ProcessBar.close();
var func = bIsSingle != true ? null : function(){
CMSObj.afteredit(event)();
}
Ext.Msg.report(_json, wcm.LANG.TEMPLATE_25 || '模板校验',func, {
option: function(report, index){
var nIndex = (event.length() ==1&&!_sRenderAllName) ? _json.REPORTS.REPORT.TITLE.indexOf('~Edit-'):(_json.REPORTS.REPORT[index]?_json.REPORTS.REPORT[index].TITLE.indexOf('~Edit-'):-1);
if(nIndex>-1){
return 'edit';
}
return 'no_edit';
},
renderOption:
function(sTempId){
$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?objectid=' + sTempId);
}.bind(this)
});
}.bind(this));
},
wcag2check : function(event){
var sTempIds = event.getIds();
var oHostInfos = event.getContext();
if(!m_oMgr.__checkNoRecords(oHostInfos)) {
return;
}
var oPostData = {HostType:103,HostId:0,TemplateId:sTempIds};
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.call(m_oMgr.serviceId, 'checkWCAG2TemplateText', oPostData, true, function(_trans, _json){
wcm.CrashBoarder.get('WCAG2-TEMPLATE').show({
title : wcm.LANG.TEMPLATE_55 || '模板WCAG2校验结果',
src : WCMConstants.WCM6_PATH + 'template/template_wcag2_check_result2.html',
width:'650px',
height:'350px',
maskable:true,
params : _json
});
}.bind(this));
},
preview : function(event){
window.open(WCMConstants.WCM6_PATH + 'template/template_preview.jsp?TempId=' + event.getIds());
},
previewFolder : function(event){
var _nTempId = event.getIds();
var host = event.getHost();
var _sEmploryId = event.objId;
var _nObjectType = event.objType;
var nTempType = event.tempType;
var oParams = {
objecttype: _nObjectType,
objectids: _sEmploryId,
templateid: _nTempId
}
var sChnlorSite = _nObjectType == 103 ? (wcm.LANG.TEMPLATE_CHNL||'栏目') : (wcm.LANG.TEMPLATE_SITE||'站点');
if(nTempType != 1) {
Ext.Msg.confirm(String.format(wcm.LANG.TEMPLATE_28||'当前模板并不是概览模板.<br><br>点击[确定]将按<b>默认首页模板</b>预览该{0}.',sChnlorSite), {
ok : function(){
var host = event.getHost();
var nObjectType = host.getIntType();
var sEmploryId = host.getId();
var oParams1 = {
objecttype: _nObjectType,
objectids: sEmploryId,
templateid: 0
}
m_oMgr.getHelper().call('wcm6_publish', 'preview', oParams1, true, function(_trans, _json){
if($v(_json, 'urlcount') == 0) {
try{
var errInfo = {
message: _json.DATA[0].EXCEPTION, 
detail: _json.DATA[0].EXCEPTIONDETAIL
};
$render500Err(_trans, errInfo, true);
}catch(err){
}
return;
}
try{
var sUrl = $v(_json, 'data')[0]['URLS'];
window.open(sUrl, 'preview_page');
}catch(err){
}
})
}
});
return;
}
m_oMgr.getHelper().call('wcm6_publish', 'preview', oParams, true, function(_trans, _json){
if($v(_json, 'urlcount') != '1') {
try{
var errInfo = {
message: (_json.DATA.length>0)?_json.DATA[0].EXCEPTION:"", 
detail: (_json.DATA.length>0)?_json.DATA[0].EXCEPTIONDETAIL:""
};
$render500Err(_trans, errInfo, true);
}catch(err){
}
return;
}
try{
var sUrl = $v(_json, 'data')[0]['URLS'];
window.open(sUrl, 'preview_page');
}catch(err){
}
})
}, 
'delete' : function(event){
var sTempIds = event.getIds() + '';
var oPageParams = event.getContext();
var nCount = (sTempIds.indexOf(',') == -1) ? 1 : sTempIds.split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var oPostData = {
"ObjectIds": sTempIds
};
if (confirm(String.format(wcm.LANG.TEMPLATE_30||('确实要将这{0}个模板删除吗?'),sHint))){
var fun = function(){
m_oMgr.getHelper().call(m_oMgr.serviceId,'delete', oPostData, true, 
function(){
event.getObjs().afterdelete();
}
);
}
m_oMgr.getHelper().call('wcm61_template','existNestedTemplate', oPostData, true, 
function(_transport,_json){
if(_json.REPORTS.REPORT){
Ext.Msg.report(_json,"含有嵌套模板",fun);
}else{
fun();
}
}
);
}
},
synchAll : function(event, operItem){
return m_oMgr.synch(event, operItem, 'RedistributeAll');
},
synch : function(event, operItem, _sRenderAllName){
var oHostInfos = event.getContext();
if(!m_oMgr.__checkNoRecords(oHostInfos)) {
return;
}
if(_sRenderAllName && !confirm(wcm.LANG.TEMPLATE_CONFIRM_1 || '此操作将重新分发模板附件,可能需要较长时间.确实要同步附件吗?')) {
return;
}
ProcessBar.start(wcm.LANG.TEMPLATE_50||'同步模板附件');
var oPostData = m_oMgr.__getCurrentPostData(event, _sRenderAllName);
var dStart = new Date().getTime();
m_oMgr.getHelper().call(m_oMgr.serviceId, 'redistributeAppendixes', oPostData, true, function(){
var sInterval = (new Date().getTime() - dStart)/1000;
ProcessBar.close();
Ext.Msg.$success(String.format("成功同步了模板附件!实际耗时<br><b> {0}</b>秒.",sInterval));
}.bind(m_oMgr));
},
showArrangeEmployment : function(event){
var host = event.getHost();
var sRight = host.right;
var nHostType = event.getObj().getPropertyAsInt('folderType');
var nHostId = event.getObj().getPropertyAsInt('folderId');
var nTempId = event.getIds();
var nTempType = event.tempType;
if(!wcm.AuthServer.hasRight(sRight, 24)) {
return;
}
if(!(nHostType > 0) || !(nHostId > 0)) {
return;
}
var url = WCMConstants.WCM6_PATH + 'template/channel_select.html?TemplateId=' + nTempId + '&TemplateType=' + nTempType + '&HostType=' + nHostType + '&HostId=' + nHostId;
if(nHostType == '103' && wcm.AuthServer.hasRight(sRight, 9)) {
url += '&SiteModify=1';
}
FloatPanel.open(url, wcm.LANG.EMPOLOYTO||'将模板分配给栏目或站点使用',CMSObj.afteradd(event));
}
});
})();
(function(){
var pageObjMgr = wcm.domain.TemplateMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var host = event.getHost();
var context = event.getContext();
if(Ext.isTrue(host.isVirtual) && host.getPropertyAsInt("chnlType", 0) != 0){
return false;
}
return true;
};
reg({
key : 'edit',
type : 'template',
desc : wcm.LANG.TEMPLATE_0 || '修改这个模板',
title:'修改这个模板...',
rightIndex : 23,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'preview',
type : 'template',
desc : wcm.LANG.TEMPLATE_2 || '预览这个模板',
title:'预览这个模板...',
rightIndex : 24,
order : 2,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'export',
type : 'template',
desc : wcm.LANG.TEMPLATE_4 || '导出这个模板',
title:'导出这个模板...',
rightIndex : 24,
order : 3,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'check',
type : 'template',
desc : wcm.LANG.TEMPLATE_6 || '校验这个模板',
title:'校验这个模板...',
rightIndex : 25,
order : 4,
fn : pageObjMgr['check']
});
reg({
key : 'wcag2check',
type : 'template',
desc : wcm.LANG.TEMPLATE_53 || 'WCAG2校验',
title:'WCAG2校验...',
rightIndex : 25,
order : 6.5,
fn : pageObjMgr['wcag2check']
});
reg({
key : 'delete',
type : 'template',
desc : wcm.LANG.TEMPLATE_8 || '删除这个模板',
title:'删除这个模板...',
rightIndex : 22,
order : 5,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'synch',
type : 'template',
desc : wcm.LANG.TEMPLATE_10 || '同步模板附件',
title:'同步模板附件...',
rightIndex : 28,
order : 6,
fn : pageObjMgr['synch']
});
reg({
key : 'new',
type : 'templateInChannel',
desc : wcm.LANG.TEMPLATE_12 || '新建一个模板',
title:'新建一个模板...',
rightIndex : 21,
order : 7,
fn : pageObjMgr['new'],
isVisible : fnIsVisible,
quickKey : 'N'
});
reg({
key : 'import',
type : 'templateInChannel',
desc : wcm.LANG.TEMPLATE_13 || '导入模板',
title:'导入模板',
rightIndex : 21,
order : 8,
fn : pageObjMgr['import'],
isVisible : fnIsVisible
});
reg({
key : 'export',
type : 'templateInChannel',
desc : wcm.LANG.TEMPLATE_15 || '导出所有模板',
title:'导出所有模板...',
rightIndex : 24,
order : 9,
fn : pageObjMgr['exportAll']
});
reg({
key : 'check',
type : 'templateInChannel',
desc : wcm.LANG.TEMPLATE_17 || '校验所有模板',
title:'校验所有模板...',
rightIndex : 25,
order : 10,
fn : pageObjMgr['checkAll']
});
reg({
key : 'synch',
type : 'templateInChannel',
desc : wcm.LANG.TEMPLATE_19 || '同步所有模板附件',
title : wcm.LANG.TEMPLATE_20 || '同步当前所列模板的附件',
rightIndex : 28,
order : 11,
fn : pageObjMgr['synchAll']
});
reg({
key : 'new',
type : 'templateInSite',
desc : wcm.LANG.TEMPLATE_12 || '新建一个模板',
title:'新建一个模板...',
rightIndex : 21,
order : 12,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'import',
type : 'templateInSite',
desc : wcm.LANG.TEMPLATE_13 || '导入模板',
title:'导入模板...',
rightIndex : 21,
order : 13,
fn : pageObjMgr['import']
});
reg({
key : 'export',
type : 'templateInSite',
desc : wcm.LANG.TEMPLATE_15 || '导出所有模板',
title:'导出所有模板...',
rightIndex : 24,
order : 14,
fn : pageObjMgr['exportAll']
});
reg({
key : 'check',
type : 'templateInSite',
desc : wcm.LANG.TEMPLATE_17 || '校验所有模板',
title:'校验所有模板...',
rightIndex : 25,
order : 15,
fn : pageObjMgr['checkAll']
});
reg({
key : 'synch',
type : 'templateInSite',
desc : wcm.LANG.TEMPLATE_19 || '同步所有模板附件',
title : wcm.LANG.TEMPLATE_20 || '同步当前所列模板的附件',
rightIndex : 28,
order : 16,
fn : pageObjMgr['synchAll']
});
reg({
key : 'export',
type : 'templates',
desc : wcm.LANG.TEMPLATE_22 || '导出这些模板',
title:'导出这些模板...',
rightIndex : 24,
order : 17,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'check',
type : 'templates',
desc : wcm.LANG.TEMPLATE_23 || '校验这些模板',
title:'校验这些模板...',
rightIndex : 25,
order : 18,
fn : pageObjMgr['check']
});
reg({
key : 'delete',
type : 'templates',
desc : wcm.LANG.TEMPLATE_21 || '删除这些模板',
title:'删除这些模板...',
rightIndex : 22,
order : 19,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'synch',
type : 'templates',
desc : wcm.LANG.TEMPLATE_10 || '同步模板附件',
title:'同步模板附件...',
rightIndex : 28,
order : 20,
fn : pageObjMgr['synch']
});
reg({
key : 'designvisualtemplate',
type : 'template',
desc : '设计可视化模板',
title:'设计可视化模板...',
rightIndex : 23,
order : 1.5,
fn : function(){
pageObjMgr['designVisualTemplate'].apply(this, arguments);
},
isVisible : function(event){
var obj = event.getObjs().getAt(0);
if(obj.getPropertyAsString('VisualAble') != 'true'){
return false;
}
return true;
}
});
})();

Ext.ns('wcm.domain.TemplateArgMgr');
(function(){
var m_oMgr = wcm.domain.TemplateArgMgr;
var m_sOperInputTemplate = ['<input name="argFeild" class="input_text" id="ArgValue" value="{0}"',
'validation="type:{1},no_desc:true"/>',
'<div class="text"><div class="{2}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'
].join("");
var m_sOperSelectTemplate = ['<div class="container"><SELECT id="ArgValue" _value={0} style="width:100px;height:20px;" validation="type:{2},no_desc:true">{1}</SELECT>',
'<div class="{3}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'].join("");
var m_sOperOptionTemplate = ['<option value="{0}">{1}</option>'].join('');
var m_sOperBooleanOptionTrue = [ '<option value="{0}">{1}</option>',
'<option value="0">{2}</option>'].join("");
var m_sOperBooleanOptionFalse = [ '<option value="1">{2}</option>',
'<option value="{0}">{1}</option>'].join("");
var m_sListOperComBoxOption = ['<span class="text_txt combo_cnt"><input name="ArgValue" id="ArgValue" value="{0}" class="kuang_as combo_input" validation="type:{2},no_desc:true"/>',
'<span class="combo_icon"><select id="selFieldInfo"><option>' + (wcm.LANG.TEMPLATEARG_31 || '请选择') + '</option>{1}</select></span>'].join("");
var m_sMultiTemplateFromListEditor = ['<input name="argFeild" class="input_text" id="ArgValue" onclick="Event.stop(window.event || arguments[0])" value="{0}" ',
'validation="type:{1},max_len:500,no_desc:true"/> ',
'<span id="multieditor" onclick=popupEditor(\"{2}\",\"{3}\",arguments[0]) title={4} class="text_param" style="padding-left:7px">&nbsp;&nbsp;&nbsp;</span>'].join("");
var m_sMultiTemplateFromOperEditor = ['<textarea id="ArgValue" rows="8" cols="29" style="width:100%;overflow-y:auto;overflow-x:hidden" validation="type:{1},no_desc:true,max_len:500,">{0}</textarea>',
'<div class="text"><div class="{2}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'].join("");
var m_sOperComBoxOption = ['<div class="container"><span class="text_txt combo_cntoper"><input name="ArgValue" id="ArgValue" value="{0}" class="kuang_as combo_input" validation="type:{2},no_desc:true"/>',
'<span class="combo_icon"><select id="selFieldInfo"><option>' + (wcm.LANG.TEMPLATEARG_31 || '请选择') + '</option>{1}</select></span>','</span><div class="{3}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'].join("");
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function getValidationType(stype){
if(stype){
if(stype == "STRING" || stype == "MULTI")
return 'String';
else if(stype == "NUMBER")
return 'number';
else if(stype == "INTEGER")
return 'int';
else return 'String';
}
else return 'String';
}
function getHtml(obj, fromListEditor, bChannel){
var sArgName = obj.getPropertyAsString('argName');
var value = $transHtml(obj.getPropertyAsString('value'));
var type = obj.getPropertyAsString('type');
var templateid = obj.getProperty('templateId');
var canCustom = obj.getPropertyAsString('canCustom');
var validationType = getValidationType(type);
var sEditable = 'false';
if(canCustom =='true'){
sEditable = "editable";
}
else sEditable = "readonly";
var aHtml = [];
if(type == "BOOLEAN"){
var sBooleanOptions = null;
if(value=='是')sBooleanOptions = String.format(m_sOperBooleanOptionTrue, 1, value, '否');
else if(value.toUpperCase()=='TRUE')sBooleanOptions = String.format(m_sOperBooleanOptionTrue, 1, value, 'false');
else if(value=='否')sBooleanOptions = String.format(m_sOperBooleanOptionFalse, 0, value,'是');
else if(value=='false')sBooleanOptions = String.format(m_sOperBooleanOptionFalse, 0, value,'true');
return String.format(m_sOperSelectTemplate, (value=='是' || value.toUpperCase()=='TRUE') ? 1 : 0, sBooleanOptions, validationType, (bChannel ? "syncSameNameBooleanDiv" : "display-none"));
}
else if(type == "MULTI"){
if(fromListEditor==1) return String.format(m_sMultiTemplateFromListEditor, value==null ? "" : value, validationType, 'ArgValue', sArgName, (wcm.LANG['TEMPLATEARG_2'] || "点击转到大的编辑区域"));
return String.format(m_sMultiTemplateFromOperEditor, value==null ? "" : value, validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
}
else if(m_oArgs[sArgName]){
for(var index = 0; index < m_oArgs[sArgName].length; index++){
aHtml.push(String.format(m_sOperOptionTemplate, m_oArgs[sArgName][index][0],m_oArgs[sArgName][index][1]));
}
if(canCustom == "false")return String.format(m_sOperSelectTemplate, value==null ? "" : value, aHtml.join(""), validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
else return String.format(fromListEditor ? m_sListOperComBoxOption : m_sOperComBoxOption, value==null ? "" : value, aHtml.join(""), validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
}
else 
return String.format(m_sOperInputTemplate, value==null ? "" : value, validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
}
function makePhysicalFieldsSelect(){
Event.observe('selFieldInfo', 'change', function(){
var selFieldInfo = $('selFieldInfo');
var optionCurr = selFieldInfo.options[selFieldInfo.selectedIndex];
if(optionCurr==null)return;
$('ArgValue').value = selFieldInfo.value;
$('ArgValue').focus();
return false;
});
}
function submitData(_postData, ContextEvent, event){
var elEvent = window.event || event;
var dom = $('ArgValue');
if(dom){
var validInfo = ValidatorHelper.valid(dom);
if(validInfo && !validInfo.isValid()){
Ext.Msg.warn(validInfo.getWarning(), function(){
try{
dom.focus();
}catch(error){
}
});
return;
}
var sArgName = _postData['parameterName'];
if(sArgName.trim().length == 0) {
Ext.Msg.warn("变量名为空，请检查模板中定义的变量。");
CMSObj.afteredit(ContextEvent)();
return;
}
Object.extend(_postData, {ArgValue : dom.value});
BasicDataHelper.Call('wcm6_templateArg', 'saveArgument', _postData, true, function(){
CMSObj.afteredit(ContextEvent)();
}.bind(this));
return false;
}
}
Ext.apply(wcm.domain.TemplateArgMgr, {
edit : function(event){
if(event.length() == 0){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_6'] || '没有选择任何模板变量');
return;
} 
var objs = event.getObjs();
if(objs.length() > 1){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_7'] || '只能对一个模板变量进行修改');
return;
}
var obj = event.getObjs().getAt(0);
var type = obj.getPropertyAsString('type');
var nHostType = event.getHost().getIntType();
var _postData = {
TemplateId : obj.getPropertyAsInt('templateId'),
parameterName : obj.getPropertyAsString('argName'),
HostId : event.getHost().getId(),
HostType : nHostType,
PrexName : obj.getPropertyAsString('PrexName')
};
var bChannel = nHostType == 101 ? true : false;
var sHtml = getHtml(obj, 0, bChannel);
var sId = 'edit_template_arg';
cb = new wcm.CrashBoard({
baseCls : 'wcm-cbd mycbd',//修改重写后样式定义
getDragElId : function(){
return this.header;
},
id : sId,
title : wcm.LANG['TEMPLATEARG_8'] || '修改参数值',
html : sHtml,
renderTo : document.body,
width:'300px',
height : type == 'MULTI' ? '150px' : '50px',
maskable : true, 
btns : [
{
text : wcm.LANG['TEMPLATEARG_9'] || '确定', 
cmd : function(){
var dom = this.getElement('ArgValue');
var checkDom = this.getElement('SyncSameName');
var validInfo = ValidatorHelper.valid(dom);
if(validInfo && !validInfo.isValid()){
Ext.Msg.warn(validInfo.getWarning(), function(){
try{
dom.focus();
}catch(error){
}
});
return false;
}
var sArgName = _postData['parameterName'];
if(sArgName.trim().length == 0) {
Ext.Msg.warn("变量名为空，请检查模板中定义的变量。");
return;
}
var bSyncSameName = (checkDom && checkDom.checked) ? true : false;
Object.extend(_postData,{ArgValue : dom.value, SyncSameName : bSyncSameName});
BasicDataHelper.Call('wcm6_templateArg', 'saveArgument', _postData, true, function(){
CMSObj.afteredit(event)();
this.close();
}.bind(this));
this.hide();
return false;
}
},
{
text : wcm.LANG['TEMPLATEARG_10'] || '取消'
}
]
});
cb.on('show', function(){
var dom = $('ArgValue');
if($("selFieldInfo")) {
for(var j=0; j<$("selFieldInfo").options.length;j++){
if(dom.value == $("selFieldInfo").options[j].value){
$("selFieldInfo").selectedIndex = j;
break;
}
}
makePhysicalFieldsSelect();
}
if(dom){
if(dom.tagName != "SELECT") return;
dom.value = dom.getAttribute("_value");
}
})
cb.show();
},
listEdit : function(event){
var listEvent = event;
if(event.length() == 0){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_6'] || '没有选择任何模板变量');
return;
} 
var objs = event.getObjs();
if(objs.length() > 1){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_7'] || '只能对一个模板变量进行修改');
return;
}
var obj = event.getObjs().getAt(0);
var type = obj.getPropertyAsString('type');
var _postData = {
TemplateId : obj.getPropertyAsInt('templateId'),
parameterName : obj.getPropertyAsString('argName'),
HostId : event.getHost().getId(),
HostType : event.getHost().getIntType(),
PrexName : obj.getPropertyAsString('PrexName')
};
var sHtml = getHtml(obj, 1);
var valueEl = $('value_sp_' + obj.getId());
valueEl.innerHTML = sHtml;
var dom = $('ArgValue');
dom.focus();
if($("selFieldInfo")) {
for(var j=0; j<$("selFieldInfo").options.length;j++){
if(dom.value == $("selFieldInfo").options[j].value){
$("selFieldInfo").selectedIndex = j;
break;
}
}
makePhysicalFieldsSelect();
}
if(dom && dom.tagName == "SELECT"){
dom.value = dom.getAttribute("_value");
dom.onchange = submitData.bind(window, _postData, listEvent);
}
var config = {
excludes : [{'selFieldInfo':false}, {'multieditor':false}],
element : 'ArgValue',
blur : function(){
submitData.bind(window, _postData, listEvent)();
}
};
BlurMgr.add(config);
},
cancelArgVal : function(event){
if(event.length() == 0){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_6'] || '没有选择任何模板变量！');
return;
} 
var checkedDocIds = event.getIds();
if(checkedDocIds.length == 0) {
return;
}
var objs = event.getObjs();
var nTemplateId = objs.getAt(0).getPropertyAsInt('templateId',0);
var sTempPrexName = objs.getAt(0).getPropertyAsString('PrexName');
for (var i = 0; i < objs.length(); i++){
var obj = objs.getAt(i);
var tempId = obj.getPropertyAsInt('templateId',0);
if(nTemplateId != tempId) {
Ext.Msg.$fail(String.format("无法同时取消两个模板[{0}]和[{1}]中的变量值！",nTemplateId,tempId));
return;
}
var tempPrexName = obj.getPropertyAsString('PrexName');
if(sTempPrexName != tempPrexName) {
Ext.Msg.$fail(String.format("无法同时取消两个模板标识[{0}]和[{1}]所对应模板的变量值！",sTempPrexName, tempPrexName));
return;
}
sTempPrexName = tempPrexName;
}
Ext.Msg.confirm(wcm.LANG['TEMPLATEARG_17'] || '确实要取消变量的赋值吗？',{
yes : function(){
var postData = {
templateId: nTemplateId,
parameterNames: objs.getPropertyAsString('argName')
};
if(sTempPrexName && (sTempPrexName = sTempPrexName.trim()) != ''
&& sTempPrexName.toLowerCase() != 'null') {
postData['prexName'] = sTempPrexName;
}
Object.extend(postData, PageContext.params);
BasicDataHelper.call('wcm6_templateArg', 'cancelArgVal', postData, true, function(){
CMSObj.afteredit(event)();
});
},
no: function(){
return;
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.TemplateArgMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'delete',
type : 'templateArg',
desc : wcm.LANG['TEMPLATEARG_19'] || '取消赋值',
title : wcm.LANG['TEMPLATEARG_19'] || '取消赋值...',
rightIndex : -1,
order : 2,
fn : pageObjMgr['cancelArgVal']
});
reg({
key : 'edit',
type : 'templateArg',
desc : wcm.LANG['TEMPLATEARG_18'] || '修改模板变量',
title : wcm.LANG['TEMPLATEARG_18'] || '修改模板变量...',
rightIndex : -1,
order : 1,
fn : pageObjMgr['edit']
});
reg({
key : 'delete',
type : 'templateArgs',
desc : wcm.LANG['TEMPLATEARG_19'] || '取消赋值',
title : wcm.LANG['TEMPLATEARG_19'] || '取消赋值...',
rightIndex : -1,
order : 2,
fn : pageObjMgr['cancelArgVal']
});
})();

Ext.ns('wcm.domain.TRSServerMgr');
(function(){
var m_oMgr = wcm.domain.TRSServerMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.TRSServerMgr, {
"pick" : function(event){
var sIds = event.getObjs().getIds();
var args = {
IsRadio : 1,
CurrSiteType : 0,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeVirtual : 1,
ExcludeInfoView : 1,
ExcludeOnlySearch : 1,
ShowOneType : 1,
NotSelect : 1,
canEmpty : true
};
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
wcm.LANG.TRSSERVER_7 || '选择所属栏目',
function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_32 ||'请选择当前文档要复制到的目标栏目!');
return false;
}
var oPostData = {
ServerIds :sIds,
ChannelId : selectIds[0]
}
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call('wcm6_document', 'trsSaveToWCM', oPostData, true, function(_oTrans, _json){
alert(wcm.LANG.DOCUMENT_PROCESS_237 || "提取成功！");
FloatPanel.close();
CMSObj.afteredit(event)();
},function(){
alert(wcm.LANG.DOCUMENT_PROCESS_238 || "提取失败！");
});
},
dialogArguments = args
);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.TRSServerMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'pick',
type : 'trsserver',
desc : wcm.LANG.TRSSERVER_5 || '提取',
rightIndex : -1,
order : 1,
fn : pageObjMgr['pick'],
quickKey : 'P'
});
})();

Ext.ns('wcm.domain.videoMgr');
(function(){
var m_oMgr = wcm.domain.videoMgr;
function $openCenterWin(_sUrl, _sName, _width, _height, _sFeature){
if(!_width || !_height){
$openCentralWin(_sUrl, _sName);
return;
}
var _WIN_WIDTH = window.screen.availWidth;
var _WIN_HEIGHT = window.screen.availHeight;
var l = (_WIN_WIDTH - _width) / 2;
var t = (_WIN_HEIGHT - _height) / 2;
sFeature = "left="+l + ",top=" + t +",width=" 
+ _width + ",height=" + _height + "," + _sFeature;
var sName = _sName || "";
sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
var oWin = window.open(_sUrl, sName, sFeature);
if(oWin) oWin.focus();
}
function publish(objectids, _sMethodName,_oExtraParams){
_sMethodName = _sMethodName || 'publish';
var oPostData = {'ObjectIds' : objectids,'IsVideo':true};
getHelper().call("wcm6_viewdocument", _sMethodName, oPostData, true,
function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, _sMethodName, _transport, _json);
}
);
}
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function openEditor(params){
if(params.DocumentId=='0'&&params.SiteId!=0){
FloatPanel.open(WCMConstants.WCM6_PATH + 'video/document_siteadd_step1.html?' 
+ $toQueryStr(params), wcm.LANG.VIDEO_PROCESS_81 || '选择要新建视频的栏目', 400, 350);
return;
}
var iWidth = window.screen.availWidth - 12;
var iHeight = window.screen.availHeight - 30;
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
window.open(WCMConstants.WCM6_PATH + "video/video_addedit.jsp?" + $toQueryStr(params), "_blank" , sFeature);
}
function preparePostData(event){
var obj = event.getObj();
return {
'objectids' : event.getIds(),
'channelids' : event.getObjs().getPropertyAsInt('currchnlid')
};
}
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
function doOptionsAfterDisplayInfo(_params, _fDoAfterDisp){
var DIALOG_DOCUMENT_INFO = 'document_info_dialog';
wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
title : wcm.LANG.DOCUMENT_PROCESS_208 || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'docrecycle/video_info.html',
width:'500px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
}
Ext.apply(wcm.domain.videoMgr, {
upload: function(event) {
var oParams = Ext.apply({
DocumentId : 0,
FromEditor : 1
}, parseHost(event.getHost()));
openEditor(oParams);
},
snap: function(event) {
var docId = event.getObj().docId;
var sFeature = 'location=no,menubar=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800,height=440';
window.open(WCMConstants.WCM6_PATH + "video/updateThumb.jsp?docId=" + docId, "_blank", sFeature);
},
preview : function(event){
var host = event.getHost();
var hostid = host.getId();
var oParams = {
FolderId : hostid,
FolderType : host.getType()=="website"? 103:101,
'IsVideo':true
};
var _sIds = event.getIds();
wcm.domain.PublishAndPreviewMgr.preview(_sIds,600,oParams,"wcm6_viewdocument");
},
basicpublish : function(event){
var oPageParams = event.getContext();
var _sIds = event.getIds();
var postData = Object.extend({},{
'ObjectIds' : _sIds,
'IsVideo':true
});
BasicDataHelper.call("wcm6_viewdocument", "basicpublish", postData, true, function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
}.bind(this));
},
detailpublish : function(event){
var oPageParams = event.getContext();
var _sIds = event.getIds();
var postData = Object.extend({},{
'ObjectIds' : _sIds,
'IsVideo':true
});
BasicDataHelper.call("wcm6_viewdocument", "detailpublish", postData, true, function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(postData, "detailpublish",_transport,_json);
}.bind(this));
},
directpublish : function(event){
var oPostData = {'ObjectIds' : event.getObjs().getPropertyAsString("docId").join(","), 'ObjectType' : 605 };
getHelper().call("wcm6_publish", "directPublish", oPostData, true,
function(_transport,_json){
wcm.domain.PublishAndPreviewMgr.doAfterPublish(oPostData, "directPublish", _transport, _json);
}
);
},
edit : function(event){
var obj = event.getObjs().getAt(0);
var host = event.getHost();
var oPageParams = event.getContext();
var oVideo = event.getObj();
var oParams = {
ChnlDocId : oVideo.recId,
DocumentId : oVideo.docId,
ChannelId :obj.getPropertyAsInt('channelid',0),
SiteId :host.getType()==WCMConstants.OBJ_TYPE_WEBSITE?host.getId():0,
FromEditor : 1
};
openEditor(oParams);
},
changestatus : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var sId = event.getIds().join();
Object.extend(oPageParams,{
"ObjectIds":sId,
"IsPhoto":false,
'ChannelIds':(oPageParams,host.getType()=="website"?0:hostid)});
FloatPanel.open(WCMConstants.WCM6_PATH +'document/change_status.jsp?' + $toQueryStr(oPageParams), '视频-改变状态', CMSObj.afteredit(event));
},
copy : function(event,operItem){
var pageContext = event.getContext();
var sObjectids = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var hostChnlId = hostType == 101 ? hostId : 0;
sObjectids = (sObjectids.length!=0)?sObjectids:'0';
var channelids = event.getObjs().getPropertyAsString("currchnlid", 0);
if(!Ext.isArray(channelids)) channelids = [channelids];
var channelType = event.getObjs().getPropertyAsString("channelType", 0);
if(!Ext.isArray(channelType)) channelType = [channelType];
var bIsOneChannel = true;
var tmpChannelid = channelids[0];
for(var i=1,n=channelids.length; i<n; i++){
if(tmpChannelid!=channelids[i]){
bIsOneChannel = false;
break;
}
}
var nExcludeInfoView = 1;
for(index=0; index<=channelType.length; index++){
if(channelType[index]==13)nExcludeInfoView=0;
}
var args = {
IsRadio : 0,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeVirtual : 1,
ExcludeInfoView : nExcludeInfoView,
ExcludeOnlySearch : 1,
ShowOneType : 1,
SelectedChannelIds : channelids.join() || hostChnlId,
NotSelect : 1,
RightIndex : 31,
canEmpty : true
};
if(bIsOneChannel){
Ext.apply(args, {
CurrChannelId : hostChnlId || tmpChannelid || 0,
ExcludeSelf : 0
});
}
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
wcm.LANG.VIDEO_PROCESS_93 || '视频-视频复制到...',
function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert(wcm.LANG.VIDEO_PROCESS_32 ||'请选择当前视频要复制到的目标栏目!');
return false;
}
var nFromChnlId = event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
"ObjectIds" : sObjectids,
"FromChannelId" : nFromChnlId,
"ToChannelIds" : selectIds.join(',')
};
var oHelper = new com.trs.web2frame.BasicDataHelper();
var func = function(){
FloatPanel.close();
CMSObj.afteredit(event)();
}
oHelper.Call('wcm61_video','copy',oPostData,true,
function(_transport,_json){
if(_json!=null&&_json["REPORTS"]){
var oReports = _json["REPORTS"];
var stJson = com.trs.util.JSON;
var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
var title = stJson.value(oReports, "TITLE");
if(bIsSuccess=='true'){
if(title.indexOf("文档") != -1){
oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
if(oReports.REPORT.length){
for(var i =0;i< oReports.REPORT.length;i++){
var currItem = stJson.value(oReports.REPORT[i], "TITLE");
if( currItem != null)
oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}else{
oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}
Ext.Msg.report(_json,wcm.LANG.VIDEO_PROCESS_36 ||'视频复制结果');
FloatPanel.hide();
}else{
if(title.indexOf("文档") != -1){
oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
if(oReports.REPORT.length){
for(var i =0;i< oReports.REPORT.length;i++){
var currItem = stJson.value(oReports.REPORT[i], "TITLE");
if( currItem != null)
oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}else{
oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}
Ext.Msg.report(_json,wcm.LANG.VIDEO_PROCESS_36 ||'视频复制结果');
FloatPanel.hide();
}
}
},
function(_transport,_json){
$render500Err(_transport,_json);
FloatPanel.close();
}
);
},
dialogArguments = args
);
},
quote : function(event){
var oPostData = preparePostData(event);
FloatPanel.open(WCMConstants.WCM6_PATH +
'video/video_quoteto.html?' + $toQueryStr(oPostData),
wcm.LANG.VIDEO_PROCESS_95 || '视频-视频引用到...');
},
move : function(event,operItem){
var context = event.getContext();
var sObjectids = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var hostChnlId = hostType == 101 ? hostId : 0;
sObjectids = (sObjectids.length!=0)?sObjectids:'0';
var channelids = event.getObjs().getPropertyAsString("currchnlid", 0);
if(!Ext.isArray(channelids)) channelids = [channelids];
var channelType = event.getObjs().getPropertyAsString("channelType", 0);
if(!Ext.isArray(channelType)) channelType = [channelType];
var bIsOneChannel = true;
var tmpChannelid = channelids[0];
for(var i=1,n=channelids.length; i<n; i++){
if(tmpChannelid!=channelids[i]){
bIsOneChannel = false;
break;
}
}
var nExcludeInfoView = 1;
for(index=0; index<=channelType.length; index++){
if(channelType[index]==13)nExcludeInfoView=0;
}
var args = {
IsRadio : 1,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeVirtual : 1,
ExcludeInfoView : nExcludeInfoView,
ExcludeOnlySearch : 1,
ShowOneType : 1,
SelectedChannelIds : channelids.join() || hostChnlId,
NotSelect : 1,
RightIndex : 31,
canEmpty : true
};
if(bIsOneChannel){
Ext.apply(args, {
CurrChannelId : hostChnlId || tmpChannelid || 0,
ExcludeSelf : 1
});
}
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
wcm.LANG.VIDEO_PROCESS_94 || '视频-视频移动到...',
function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert(wcm.LANG.VIDEO_PROCESS_52 ||'请选择当前视频要移动到的目标栏目!');
return false;
}
var nFromChnlId = event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
"ObjectIds" : sObjectids,
"FromChannelId" : nFromChnlId,
"ToChannelId" : selectIds.join(',')
}
var func = function(){
FloatPanel.close();
CMSObj.afteredit(event)();
}
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call('wcm6_viewdocument', 'move',oPostData,true,
function(_transport,_json){
if(_json!=null&&_json["REPORTS"]){
var oReports = _json["REPORTS"];
var stJson = com.trs.util.JSON;
var bIsSuccess = stJson.value(oReports, "IS_SUCCESS");
var title = stJson.value(oReports, "TITLE");
if(bIsSuccess=='true'){
if(title.indexOf("文档") != -1){
oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
if(oReports.REPORT.length){
for(var i =0;i< oReports.REPORT.length;i++){
var currItem = stJson.value(oReports.REPORT[i], "TITLE");
if( currItem != null)
oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}else{
oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}
Ext.Msg.report(_json,(wcm.LANG.VIDEO_PROCESS_54 ||'视频移动结果'),func);
FloatPanel.hide();
}else{
if(title.indexOf("文档") != -1){
oReports.TITLE = oReports.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
if(oReports.REPORT.length){
for(var i =0;i< oReports.REPORT.length;i++){
var currItem = stJson.value(oReports.REPORT[i], "TITLE");
if( currItem != null)
oReports.REPORT[i].TITLE = currItem.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}else{
oReports.REPORT.TITLE = oReports.REPORT.TITLE.replace(new RegExp(wcm.LANG.PUBLISH_6 || "文档","g"),wcm.LANG.PUBLISH_8 || "视频");
}
}
Ext.Msg.report(_json,(wcm.LANG.VIDEO_PROCESS_54 ||'视频移动结果'),func);
FloatPanel.hide();
}
}
},
function(_transport,_json){
$render500Err(_transport,_json);
FloatPanel.close();
}
);
},
dialogArguments = args
);
},
trash : function(event){
var oHost = parseHost(event.getHost());
var nCount = event.length();
var sHint = (nCount==1)?'':' '+nCount+' ';
var browserEvent = event.browserEvent;
var bDrop = !!(browserEvent && 
browserEvent.type=='keydown' && browserEvent.shiftKey);
var params = {
objectids: event.getIds(),
operation: bDrop ? '_forcedelete' : '_trash'
}
doOptionsAfterDisplayInfo(params, function(){
getHelper().call('wcm61_viewdocument', 'delete',
Ext.apply(oHost,{"ObjectIds": event.getIds(), "drop": bDrop}), true, 
function(){
event.getObjs().afterdelete();
}
);
});
},
segment : function(event){
var docId = event.getObj().docId;
var sFeature = 'location=no,menubar=no,status=no,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800,height=580';
window.open(WCMConstants.WCM6_PATH + "video/cutVideo.jsp?docId=" + docId, "_blank", sFeature);
},
docpositionset : function(event){
var oPageParams = event.getContext();
var host = event.getHost();
var hostid = host.getId();
var objType = event.getObjs().getAt(0).objType;
var docid = event.getObjs().getAt(0).getPropertyAsInt("docId", 0);
Object.extend(oPageParams,host.getType()=="website"?{"siteid" : hostid}:{"channelid" : hostid});
Object.extend(oPageParams,{"DocumentId":docid,"DocType":objType,"DocTypeDesc":'视频'});
FloatPanel.open(WCMConstants.WCM6_PATH + 'document/document_position_set.jsp?' + $toQueryStr(oPageParams), (objType.trim()=='video'?('视频'):(wcm.LANG.PHOTO_CONFIRM_68 || '文档')) +
(wcm.LANG.PHOTO_CONFIRM_69 || '-调整顺序'), CMSObj.afteredit(event));
},
detailpublish : function(event){
publish(event.getIds(), 'detailPublish');
},
recallpublish : function(event){
var host = event.getHost();
var hostid = host.getId();
var _sIds = event.getIds();
var oParams = {
FolderId : hostid,
FolderType : host.getType()=="website"? 103:101
};
var sHtml = String.format("确定要{0}撤销发布{1}所选视频么？将{2}不可逆转{3}！",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
wcm.domain.PublishAndPreviewMgr.publish(_sIds, 600, 'recallPublish',oParams,"wcm6_viewdocument");
}
});
},
directRecallpublish : function(event){
var sHtml = String.format("确定要{0}撤销发布{1}所选文档及其所有引用文档么？将{2}不可逆转！{3}",'<font color=\'red\' style=\'font-size:14px;\'>','</font>','<font color=\'red\' style=\'font-size:14px;\'>','</font>');
Ext.Msg.confirm(sHtml,{
yes : function(){
publish(event.getIds(), 'recallpublishall');
}
})
},
setright : function(event){
var m_nDocumentObjType = 605;
$openCenterWin(WCMConstants.WCM6_PATH + "auth/right_set.jsp?ObjType=" +
m_nDocumentObjType + "&ObjId=" + event.getObjs().getPropertys("docId",0).join(),
"document_right_set", 900, 600, "resizable=yes");
},
live: function(event) {
var host = event.getHost();
var hostId = host.getId();
var sFeature = 'location=no,resizable=no,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=0,left=0,border=0,width=1000,height=900';
window.open(WCMConstants.WCM6_PATH + "video/live_add.jsp?SiteId=" + hostId, "_blank", sFeature);
},
multiupload: function(event) {
var oPageParams = event.getContext();
var host = event.getHost();
var chnId = host.getType()==WCMConstants.OBJ_TYPE_CHANNEL?host.getId():0;
var sFeature = 'location=no,resizable=no,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=0,left=0,border=0,width=800,height=560';
window.open(WCMConstants.WCM6_PATH + "video/video_batch_add.jsp?ChannelId=" + chnId, "_blank", sFeature);
},
commentmgr : function(event){
var docid = event.getObj().docId;
var host = event.getHost();
var hostid = host.getId();
var oParams = Ext.apply({
DocumentId : docid,
ChannelId :hostid,
SiteId : 0
}, parseHost(event.getHost()));
var sUrl = WCMConstants.WCM_ROOTPATH +'comment/comment_mgr.jsp?'
+ $toQueryStr(oParams);
$openMaxWin(sUrl);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.videoMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'upload',
type : 'videoInChannel',
desc : wcm.LANG.VIDEO_PROCESS_159 || '新建视频',
title : wcm.LANG.VIDEO_PROCESS_108 || '上传新视频',
rightIndex : 31,
order : 8,
fn : pageObjMgr['upload'],
quickKey : 'N'
});
reg({
key : 'upload',
type : 'videoInSite',
desc : wcm.LANG.VIDEO_PROCESS_159 || '新建视频',
title : wcm.LANG.VIDEO_PROCESS_108 || '上传新视频',
rightIndex : 31,
order : 8,
fn : pageObjMgr['upload'],
quickKey : 'N'
});
reg({
key : 'snap',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_214 || '重新抓取缩略图',
title : wcm.LANG.VIDEO_PROCESS_215 || '抓取更合适的缩略图',
rightIndex : 32,
order : 8,
fn : pageObjMgr['snap']
});
reg({
key : 'preview',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_116 || '预览这个视频',
title : wcm.LANG.VIDEO_PROCESS_116 || '预览这个视频',
rightIndex : 38,
order : 3,
fn : pageObjMgr['preview'],
quickKey : 'Y'
});
reg({
key : 'basicpublish',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_118 || '发布这个视频',
title : wcm.LANG.VIDEO_PROCESS_118 || '发布这个视频',
rightIndex : 39,
order : 4,
fn : pageObjMgr['basicpublish'],
isVisible : function(event){
var obj = event.getObj();
var converting = obj.getPropertyAsInt('converting', 1);
return converting == 1 || converting == 3;
},
quickKey : 'P'
});
reg({
key : 'edit',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_115 ||'修改这个视频',
title : wcm.LANG.VIDEO_PROCESS_115 ||'修改这个视频',
rightIndex : 32,
order : 1,
isVisible : function(event){
var obj = event.getObj();
var converting = obj.getPropertyAsInt('converting', 1);
return converting == 1 || converting == 3;
},
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'changestatus',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_125 ||'改变这个视频状态',
title : wcm.LANG.VIDEO_PROCESS_125 ||'改变这个视频状态',
rightIndex : 35,
order : 8,
fn : pageObjMgr['changestatus']
});
reg({
key : 'move',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_122 ||'移动这个视频到',
title : wcm.LANG.VIDEO_PROCESS_122 ||'移动这个视频到',
rightIndex : 33,
order : 6,
fn : pageObjMgr['move']
});
reg({
key : 'move',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_145 ||'移动这些视频到',
title : wcm.LANG.VIDEO_PROCESS_145 ||'移动这些视频到',
rightIndex : 33,
order : 6,
fn : pageObjMgr['move']
});
reg({
key : 'trash',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_123 ||'将视频放入废稿箱',
title : wcm.LANG.VIDEO_PROCESS_124 ||'将这个视频放入废稿箱',
rightIndex : 33,
order : 7,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'trash',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_123 ||'将视频放入废稿箱',
title : wcm.LANG.VIDEO_PROCESS_123 ||'将视频放入废稿箱',
rightIndex : 33,
order : 3,
fn : pageObjMgr['trash'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'segment',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_216 || '标引与切割',
title : wcm.LANG.VIDEO_PROCESS_217 || '对这个视频进行标引和切割',
rightIndex : 31,
order : 8,
isVisible : function(event) {
var actualTop = $MsgCenter.getActualTop();
if(actualTop.autoclip==true){
return true;
}
return false;
},
fn : pageObjMgr['segment']
});
reg({
key : 'detailpublish',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_127 ||'仅发布这个视频细览',
title : wcm.LANG.VIDEO_PROCESS_128 ||'仅发布这个视频细览，仅重新生成这个视频的细览页面',
rightIndex : 39,
order : 9,
isVisible : function(event){
var obj = event.getObj();
var converting = obj.getPropertyAsInt('converting', 1);
return converting == 1 || converting == 3;
},
fn : pageObjMgr['detailpublish']
});
reg({
key : 'directpublish',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_229 ||'直接发布这个视频',
title : wcm.LANG.VIDEO_PROCESS_226 ||'发布这个视频,同时发布此视频的所有引用视频',
rightIndex : 39,
order : 11,
isVisible : function(event){
var obj = event.getObj();
var converting = obj.getPropertyAsInt('converting', 1);
return converting == 1 || converting == 3;
},
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_129 || '撤销发布这个视频',
title : wcm.LANG.VIDEO_PROCESS_130 || '撤销发布这个视频,撤回已发布目录或页面',
rightIndex : 39,
order : 18,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_230 ||'直接撤销发布这个视频',
title : wcm.LANG.VIDEO_PROCESS_231 ||'撤销发布这个视频，同步撤销这个视频所有的引用视频',
rightIndex : 39,
order : 13,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'setright',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_126 || '设置这个视频的权限',
title : wcm.LANG.VIDEO_PROCESS_126 || '设置这个视频的权限...',
rightIndex : 61,
order : 20,
fn : pageObjMgr['setright']
});
reg({
key : 'preview',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_140 || '预览这些视频',
title : wcm.LANG.VIDEO_PROCESS_140 || '预览这些视频',
rightIndex : 38,
order : 13,
fn : pageObjMgr['preview'],
quickKey : 'Y'
});
reg({
key : 'basicpublish',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_142 || '发布这些视频',
title : wcm.LANG.VIDEO_PROCESS_142 || '发布这些视频',
rightIndex : 39,
order : 14,
fn : pageObjMgr['basicpublish'],
isVisible : function(event){
var objs = event.getObjs();
for(var i=1;i<objs.size();i++){
var obj = objs.getAt(i);
var converting = obj.getPropertyAsInt('converting', 1);
if(converting == 0){
return false;
}
}
return converting == 1 || converting == 3;
},
quickKey : 'P'
});
reg({
key : 'directpublish',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_227 ||'直接发布这些视频',
title : wcm.LANG.VIDEO_PROCESS_228 ||'发布这些视频，同步发布这些视频所有的引用视频',
rightIndex : 39,
order : 10,
isVisible : function(event){
var objs = event.getObjs();
for(var i=1;i<objs.size();i++){
var obj = objs.getAt(i);
var converting = obj.getPropertyAsInt('converting', 1);
if(converting == 0){
return false;
}
}
return converting == 1 || converting == 3;
},
fn : pageObjMgr['directpublish']
});
reg({
key : 'recallpublish',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_151 || '撤销发布这些视频',
title : wcm.LANG.VIDEO_PROCESS_152 || '撤销发布这些视频,撤回已发布目录或页面',
rightIndex : 39,
order : 19,
fn : pageObjMgr['recallpublish']
});
reg({
key : 'directRecallpublish',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_232 ||'直接撤销发布这些视频',
title : wcm.LANG.VIDEO_PROCESS_233 ||'撤销发布这些视频，同步撤销这些视频所有的引用视频',
rightIndex : 39,
order : 12,
fn : pageObjMgr['directRecallpublish']
});
reg({
key : 'setright',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_148 || '设置这些视频的权限',
title : wcm.LANG.VIDEO_PROCESS_148 || '设置这些视频的权限...',
rightIndex : 61,
order : 21,
fn : pageObjMgr['setright']
});
reg({
key : 'changestatus',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_147 || '改变这些视频的状态',
title : wcm.LANG.VIDEO_PROCESS_147 || '改变这些视频的状态',
rightIndex : 35,
order : 17,
fn : pageObjMgr['changestatus']
});
reg({
key : 'detailpublish',
type : 'videos',
desc : wcm.LANG.VIDEO_PROCESS_149 ||'仅发布这些视频细览',
title : wcm.LANG.VIDEO_PROCESS_150 ||'仅发布这些视频细览，仅重新生成这些文档的细览页面',
rightIndex : 39,
order : 9,
isVisible : function(event){
var objs = event.getObjs();
for(var i=1;i<objs.size();i++){
var obj = objs.getAt(i);
var converting = obj.getPropertyAsInt('converting', 1);
if(converting == 0){
return false;
}
}
},
fn : pageObjMgr['detailpublish']
});
reg({
key : 'multiupload',
type : 'videoInChannel',
desc : wcm.LANG.VIDEO_PROCESS_110 ||'批量新建视频',
title : wcm.LANG.VIDEO_PROCESS_110 ||'批量新建视频...',
rightIndex : 31,
order : 9,
fn : pageObjMgr['multiupload']
});
reg({
key : 'docpositionset',
type : 'video',
desc : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
title : wcm.LANG.PHOTO_CONFIRM_90 || '调整顺序',
rightIndex : 62,
order : 24,
fn : pageObjMgr['docpositionset'],
isVisible : function(event){
if(event.getHost().getType() == WCMConstants.OBJ_TYPE_CHANNEL)
return true;
return false;
}
});
reg({
key : 'commentmgr',
type : 'video',
desc : wcm.LANG.VIDEO_PROCESS_221 ||'管理评论',
title : wcm.LANG.VIDEO_PROCESS_222 ||'管理视频的评论',
rightIndex : 8,
order : 40,
fn : pageObjMgr['commentmgr'],
isVisible : function(event){
try{
return $MsgCenter.getActualTop().g_IsRegister['comment'];
}catch(err){
return false;
}
}
});
})();

Ext.ns('wcm.domain.VideoRecycleMgr');
(function(){
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
var m_oMgr = wcm.domain.VideoRecycleMgr ={
serviceId : 'wcm61_video',
helpers : {},
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.VideoRecycleMgr, {
view : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var pageContext = event.getContext();
var oParams = Ext.apply({
DocumentId : pageContext.docid,
ChnlDocId : event.getIds(),
FromRecycle : 1
},parseHost(event.getHost()));
$openMaxWin(WCMConstants.WCM6_PATH +
'document/document_show.jsp?' + $toQueryStr(oParams));
},
deleteall : function(event){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
}
if(confirm(wcm.LANG.DOCRECYCLE_CONFIRM_1 || '您当前所执行的操作将彻底删除废稿箱中所有视频，您确定仍要继续清空当前废稿箱？')){
ProcessBar.start(wcm.LANG.VIDEORECYLE_22 || '删除视频..');
m_oMgr.getHelper().call(m_oMgr.serviceId, 'clearRecycleVideos', params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}
},
'delete' : function(event){
var sIds = event.getIds();
var hostId = event.getHost().getId();
var _params = event.getContext();
var params = {
objectids: sIds,
operation: '_delete'
}
var aIds = sIds;
if(String.isString(aIds)){
aIds = aIds.split(",");
}
if(aIds.length >= 50){
m_oMgr._delete0(event);
return;
}
m_oMgr.doOptionsAfterDisplayInfo(params, m_oMgr._delete0.bind(this,event));
},
_delete0 : function(event){
ProcessBar.start(wcm.LANG.VIDEORECYLE_22 || '删除视频..');
var sIds = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var _params = {
ChannelId: (hostType == 101)?hostId:'',
SiteId: (hostType == 103)?hostId:''
};
Object.extend(_params, {ObjectIds: sIds, drop: true});
m_oMgr.getHelper().call(m_oMgr.serviceId, 'deleteVideos', _params, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
},
doOptionsAfterDisplayInfo : function(_params, _fDoAfterDisp){
wcm.CrashBoarder.get('DIALOG_DOCUMENT_INFO').show({
title : wcm.LANG.DOCRECYCLE_SYSTEMINFO || '系统提示信息',
src : WCMConstants.WCM6_PATH + 'docrecycle/video_info.html',
width:'520px',
height:'205px',
maskable:true,
params : _params,
callback : _fDoAfterDisp
});
},
restoreall : function(event, operItem){
return m_oMgr.restore(event, operItem, true);
},
restore : function(event, operItem, _bRestoreAll){
var host = event.getHost();
var hostId = host.getId();
var hostType = host.getIntType();
var params = {
operation: '_restore',
special: true
}
if(_bRestoreAll == true) {
params['restoreAll'] = true;
Object.extend(params, {
channelids: (hostType == 101)?hostId:'',
siteids: (hostType == 103)?hostId:''
});
}else{
Object.extend(params, {
objectids: event.getIds()
});
}
m_oMgr.doOptionsAfterDisplayInfo(params, function(){
ProcessBar.start(wcm.LANG.VIDEORECYLE_23 || '还原视频...');
var postData = {};
if(_bRestoreAll == true) {
postData['restoreAll'] = true;
Object.extend(postData, {
channelid: (hostType == 101)?hostId:'',
siteid: (hostType == 103)?hostId:''
});
}else{
Object.extend(postData, {
objectids: event.getIds()
});
}
m_oMgr.getHelper().call(m_oMgr.serviceId, 'restore', postData, true, function(){
ProcessBar.close();
event.getObjs().afterdelete();
});
}.bind(this));
}
});
})();
(function(){
var pageObjMgr = wcm.domain.VideoRecycleMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'restore',
type : 'videorecycle',
desc : wcm.LANG.VIDEORECYLE_1 || '还原这个视频',
title : wcm.LANG.VIDEORECYLE_7 || '还原这个视频到原位置',
rightIndex : 33,
order : 1,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'videorecycle',
desc : wcm.LANG.VIDEORECYLE_2 || '删除这个视频',
title : wcm.LANG.VIDEORECYLE_2 || '删除这个视频',
rightIndex : 33,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'deleteall',
type : 'videorecycleInChannel',
desc : wcm.LANG.VIDEORECYLE_6 || '清空废稿箱',
title : wcm.LANG.VIDEORECYLE_8 || '清空当前栏目的废稿箱',
rightIndex : 33,
order : 3,
fn : pageObjMgr['deleteall']
});
reg({
key : 'restoreall',
type : 'videorecycleInChannel',
desc : wcm.LANG.VIDEORECYLE_3 || '还原所有视频',
title : wcm.LANG.VIDEORECYLE_9 || '还原废稿箱中的所有视频',
rightIndex : 33,
order : 4,
fn : pageObjMgr['restoreall']
});
reg({
key : 'deleteall',
type : 'videorecycleInSite',
desc : wcm.LANG.VIDEORECYLE_6 || '清空废稿箱',
title : wcm.LANG.VIDEORECYLE_10 || '清空当前站点的废稿箱',
rightIndex : 33,
order : 5,
fn : pageObjMgr['deleteall']
});
reg({
key : 'restoreall',
type : 'videorecycleInSite',
desc : wcm.LANG.VIDEORECYLE_3 || '还原所有视频',
title : wcm.LANG.VIDEORECYLE_9 || '还原废稿箱中的所有视频',
rightIndex : 33,
order : 6,
fn : pageObjMgr['restoreall']
});
reg({
key : 'restore',
type : 'videorecycles',
desc : wcm.LANG.VIDEORECYLE_4 || '还原这些视频',
title : wcm.LANG.VIDEORECYLE_11 || '还原这些视频到原位置',
rightIndex : 33,
order : 7,
fn : pageObjMgr['restore'],
quickKey : 'R'
});
reg({
key : 'delete',
type : 'videorecycles',
desc : wcm.LANG.VIDEORECYLE_5 || '删除这些视频',
title : wcm.LANG.VIDEORECYLE_5 || '删除这些视频',
rightIndex : 33,
order : 8,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.domain.WatermarkMgr');
(function(){
var m_oMgr = wcm.domain.WatermarkMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.WatermarkMgr, {
add : function(event){
var oPageParams = {};
var hostid = event.getHost().getId();
Object.extend(oPageParams,{ObjectId:0,siteid:hostid});
FloatPanel.open(WCMConstants.WCM6_PATH +"watermark/watermark_addedit.jsp?" + $toQueryStr(oPageParams), wcm.LANG.WATERMARK_PROCESS_11 ||'上传水印', CMSObj.afteradd(event));
},
edit : function(event){
var docid = event.getObjs().getAt(0).getId();
var oPageParams = {};
var hostid = event.getHost().getId();
Object.extend(oPageParams,{ObjectId:docid,siteid:hostid});
FloatPanel.open(WCMConstants.WCM6_PATH +"watermark/watermark_addedit.jsp?" + $toQueryStr(oPageParams), wcm.LANG.WATERMARK_PROCESS_12 ||'编辑水印', CMSObj.afteredit(event));
},
"delete" : function(event){
var sId = event.getIds();
var hostid = event.getHost().getId();
var nCount = sId.toString().split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
var sResult = (wcm.LANG.WATERMARK_PROCESS_13 ||'确实要将这') + sHint + (wcm.LANG.WATERMARK_PROCESS_14 ||'个水印删除吗? ');
Ext.Msg.confirm(sResult,{
yes : function(){
BasicDataHelper.call("wcm6_watermark", 
'delete', //远端方法名 
Object.extend({}, {"ObjectIds": sId,"LibId":hostid}), //传入的参数
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
var pageObjMgr = wcm.domain.WatermarkMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'watermark',
desc : wcm.LANG.WATERMARK_PROCESS_15 ||'编辑这个水印',
title : wcm.LANG.WATERMARK_PROCESS_15 ||'编辑这个水印...',
rightIndex : 32,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'E'
});
reg({
key : 'delete',
type : 'watermark',
desc : wcm.LANG.WATERMARK_PROCESS_16 ||'删除这个水印',
title : wcm.LANG.WATERMARK_PROCESS_16 ||'删除这个水印...',
rightIndex : 32,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'watermarkInSite',
desc : wcm.LANG.WATERMARK_PROCESS_17 ||'上传新水印',
title : wcm.LANG.WATERMARK_PROCESS_17 ||'上传新水印...',
rightIndex : 32,
order : 3,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'delete',
type : 'watermarks',
desc : wcm.LANG.WATERMARK_PROCESS_18 ||'删除这些水印',
title : wcm.LANG.WATERMARK_PROCESS_18 ||'删除这些水印...',
rightIndex : 32,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.domain.WebSiteMgr');
(function(){
var m_nWebSiteObjType = 103;
var m_sServiceId = 'wcm61_website';
var m_oMgr = wcm.domain.WebSiteMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function edit(event, oParams){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'website/website_add_edit.jsp?' + $toQueryStr(oParams),
title : wcm.LANG.WEBSITE_1||'新建/修改站点',
callback : function(objId){
CMSObj[oParams.objectid>0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
}
});
}
Ext.apply(wcm.domain.WebSiteMgr, {
"new" : function(event){
edit(event, {
objectid : 0,
sitetype : event.getContext().get("siteType") || 0
});
},
edit : function(event){
edit(event, {
objectid : event.getObj().getId(),
sitetype : event.getContext().get("siteType") || 0
});
},
quicknew : function(event){
FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_create.jsp', wcm.LANG.WEBSITE_2||"智能建站", function(objId){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
});
},
likecopy : function(event){
var id = event.getObj().getId();
FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_likecopy.html?siteid=' + id, wcm.LANG.WEBSITE_3||"类似创建站点",function(objId){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
});
},
setAppendixSize : function(event){
var id = event.getObj().getId();
wcm.CrashBoarder.get('set_appendix_size').show({
title : wcm.LANG.website_2011 || '设置这个站点上传文档附件大小',
src : WCMConstants.WCM6_PATH + 'website/website_set_doc_appendix_size.jsp',
width:'550px',
height:'200px',
maskable:true,
params : {siteId : id}
});
}
});
function download(_sUrl){
var frm = $('iframe4download');
if(!frm) {
frm = document.createElement('IFRAME');
frm.id = "iframe4download";
frm.style.display = 'none';
document.body.appendChild(frm);
}
_sUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=SITE&FileName=" + _sUrl;
frm.src = _sUrl;
}
Ext.apply(wcm.domain.WebSiteMgr, {
'import' : function(event){
FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_import.html', wcm.LANG.WEBSITE_4||'站点导入', function(objId){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:objId});
});
},
'export' : function(event){
ProcessBar.start( wcm.LANG.WEBSITE_42||'执行站点导出..');
var aIds = event.getObjsOrHost().getIds();
getHelper().call(
m_sServiceId,
'export',
{objectids: aIds.join(",")},
true,
function(_oXMLHttp, _json){
ProcessBar.close();
download(_oXMLHttp.responseText);
}
);
},
createFromFile : function(event){
var nSiteType = event.getHost().getId();
FloatPanel.open(WCMConstants.WCM6_PATH + 'website/website_create_fromfile.html?SiteType=' + nSiteType, wcm.LANG.WEBSITE_CREATE_FROMFILE||'批量创建', function(){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE,objId:0});
});
}
});
Ext.apply(wcm.domain.WebSiteMgr, {
"delete" : function(event){
this.recycle(event);
},
trash : function(event){
this.recycle(event);
},
recycle : function(event){
var aIds = event.getObjsOrHost().getIds();
var nCount = aIds.length == 1 ? "" : aIds.length;
Ext.Msg.confirm(
String.format('确实要将这{0}个站点放入站点回收站吗?<br /><br /><span style="color:red;">这是危险操作！</span>',nCount),
{ ok : function(){
verifyPassword(function(){
ProcessBar.start(wcm.LANG.WEBSITE_RECYCLE_PROCESSBAR||'将站点放入回收站');
getHelper().call(
m_sServiceId,
'delete',
{objectids:aIds.join(","), "drop":false},
true,
function(){
ProcessBar.close();
event.getObjsOrHost().afterdelete();
}
);
} ,
"删除站点-"
);
}
}
);
return false;
}
});
function resetTemplates(_nFolderId, _nObjType, _fCallBack){
getHelper().call('wcm6_template', 'impartTemplateConfig',
{objectId: _nFolderId, ObjectType:_nObjType}, true, _fCallBack);
}
function synchTemplates(_nFolderId, _nObjType, childObjIds, _fCallBack){
getHelper().call('wcm61_template', 'synTemplates',
{objectId: _nFolderId, ObjectType:_nObjType, objectIds : childObjIds}, true, _fCallBack);
}
function verifyPassword(validCallback ,operateName){
wcm.CrashBoarder.get('validate-password').show({
title : '<span style="color:red;" >'+operateName+'校验密码</span>',
src : WCMConstants.WCM6_PATH+'include/validate_password.html',
width : '400px',
height : '150px',
callback : function(params){
if(params=="true"){
this.close();
if(validCallback){
validCallback();
}
}
}
});
}
Ext.apply(wcm.domain.WebSiteMgr, {
synTemplates : function(event){
var objId = event.getObj().getId();
var params = {
objType:m_nWebSiteObjType,
objId:objId,
close : 0,
ExcludeTop : 1,
ExcludeInfoview : 0,
ExcludeLink : 1
};
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'website/object_select.html',
dialogArguments : params,
title : wcm.LANG.WEBSITE_41||'选择栏目',
callback : function(args){
if(args.allChildren){
var sHtml = wcm.LANG.WEBSITE_30||"确实要同步模板到子栏目吗?<br><span style=\'color:red;font:16px;\'>注意:<br>该操作会覆盖更改所有子栏目的模板设置!</span>";
Ext.Msg.confirm(sHtml, {
ok : function(){
verifyPassword(function(){
FloatPanel.close();
resetTemplates(objId, m_nWebSiteObjType);
},"同步模板到子栏目-");
}
});
}else{
FloatPanel.close();
synchTemplates(objId, m_nWebSiteObjType, args.ids.join());
}
}
});
}
});
Ext.apply(wcm.domain.WebSiteMgr, {
keyword : function(event){
var aId = event.getObjsOrHost().getIds();
var type = event.getObjsOrHost().objType;
var nId = parseInt(aId.join(","))||event.getContext().get('siteType');
var para = {};
if(type.toUpperCase() =="WEBSITEROOT"||type.toUpperCase()=="WEBSITEINROOT"){
para = {
siteType : nId,
siteId : 0
}
}else {
para = {
siteType : event.getContext().get('siteType'),
siteId : nId
}
}
wcm.CrashBoarder.get('DIALOG_KEYWORD_MGR').show({
title : wcm.LANG.WEBSITE_40 || '管理关键词',
src : WCMConstants.WCM6_PATH + 'keyword/keyword_list.html',
width:'850px',
height:'400px',
maskable:true,
params : para
});
}
});
function preview(_sId, _nObjectType){
wcm.domain.PublishAndPreviewMgr.preview(_sId, _nObjectType);
}
function publish(_sIds, _nObjectType, _sPublishTypeMethod){
wcm.domain.PublishAndPreviewMgr.publish(_sIds, _nObjectType, _sPublishTypeMethod);
}
Ext.apply(wcm.domain.WebSiteMgr, {
preview : function(event){
var aIds = event.getObjsOrHost().getIds();
preview(aIds.join(","), m_nWebSiteObjType);
},
quickpublish : function(event){
m_oMgr.homepublish(event);
},
homepublish : function(event){
m_oMgr.publish(event, "soloPublish");
},
increasepublish : function(event){
m_oMgr.publish(event, "increasingPublish");
},
completepublish : function(event){
m_oMgr.publish(event, "fullyPublish");
},
updatepublish : function(event){
m_oMgr.publish(event, "refreshPublish");
},
cancelpublish : function(event){
m_oMgr.publish(event, "recallPublish");
},
publish : function(event, sPublishTypeMethod){
var aIds = event.getObjsOrHost().getIds();
publish(aIds.join(","), m_nWebSiteObjType, sPublishTypeMethod);
}
});
function $openCentralWin(_sUrl, _sName){
var _WIN_WIDTH = window.screen.availWidth;
var _WIN_HEIGHT = window.screen.availHeight;
var y = _WIN_HEIGHT * 0.12;
var x = _WIN_HEIGHT * 0.17;
var w = _WIN_WIDTH - 2 * x;
var h = w * 0.618;
var sFeature = 'resizable=yes,top=' + y + ',left='
+ x + ',menubar =no,toolbar =no,width=' + w + ',height='
+ h + ',scrollbars=yes,location =no,status=no,titlebar=no';
var sName = _sName || "";
sName = sName.replace(/[^0-9a-zA-Z_]/g,'_');
var oWin = window.open(_sUrl, sName, sFeature);
if(oWin) oWin.focus();
}
Ext.apply(wcm.domain.WebSiteMgr, {
setSiteUser : function(event){
var id = event.getObj().getId();
$openCentralWin(WCMConstants.WCM6_PATH + "../console/channel/siteuser_list.jsp?SiteId=" + id, "wcm61-siteuser");
}
});
Ext.apply(wcm.domain.WebSiteMgr, {
commentmgr : function(event){
var hostId = event.getHost().getId();
var sIds = (event.getIds().length == 0)?hostId : event.getIds();
var oParams = {
SiteId : sIds
}
var sUrl = WCMConstants.WCM_ROOTPATH + 'comment/comment_mgr.jsp?' + $toQueryStr(oParams);
var sWinName = 'comment_name';
$openMaxWin(sUrl, sWinName);
}
});
Ext.apply(wcm.domain.WebSiteMgr, {
pubstat : function(event){
var hostId = event.getHost().getId();
var sIds = (event.getIds().length == 0)?hostId : event.getIds();
var oParams = {
SiteId : sIds
}
var sUrl = WCMConstants.WCM6_PATH + '../console/stat/doccount_site_byuser.jsp?' + $toQueryStr(oParams);
$openMaxWin(sUrl);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.WebSiteMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'website',
desc : wcm.LANG.WEBSITE_EDIT||'修改这个站点',
title : wcm.LANG.WEBSITE_EDIT_TITLE||'修改站点的基本属性和发布设置等相关信息',
rightIndex : 1,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'preview',
type : 'website',
desc : wcm.LANG.WEBSITE_PREVIEW||'预览这个站点',
title : wcm.LANG.WEBSITE_PREVIEW_TITLE||'重新生成并打开这个站点的预览页面',
rightIndex : 3,
order : 2,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'increasepublish',
type : 'website',
desc : wcm.LANG.WEBSITE_ADDPUB||'增量发布这个站点',
title : wcm.LANG.WEBSITE_ADDPUB_TITLE||'发布站点和栏目的首页，并且发布所有处于可发布状态的文档',
rightIndex : 5,
order : 3,
fn : pageObjMgr['increasepublish'],
quickKey : 'P'
});
reg({
key : 'homepublish',
type : 'website',
desc : wcm.LANG.WEBSITE_HOMEPUB||'仅发布这个站点首页',
title : wcm.LANG.WEBSITE_HOMEPUB_TITLE|| '只更新和发布当前站点的首页',
rightIndex : 5,
order : 4,
fn : pageObjMgr['homepublish']
});
reg({
key : 'synTemplates',
type : 'website',
desc : wcm.LANG.WEBSITE_SYNTEMP||'同步模板到栏目',
title : wcm.LANG.WEBSITE_SYNTEMP_TITLE||'将当前站点的模板应用到指定的栏目上',
rightIndex : 1,
order : 5,
fn : pageObjMgr['synTemplates']
});
reg({
key : 'seperate',
type : 'website',
desc : wcm.LANG.WEBSITE_SEP||'分隔线',
title : "分隔线",
rightIndex : -1,
order : 6,
fn : pageObjMgr['seperate']
});
reg({
key : 'export',
type : 'website',
desc : wcm.LANG.WEBSITE_EXPORT||'导出这个站点到',
title : wcm.LANG.WEBSITE_EXPORT_TITLE ||'将当前站点内容导出成XML格式文件',
rightIndex : 1,
order : 7,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'seperate',
type : 'website',
desc : wcm.LANG.WEBSITE_SEP||'分隔线',
rightIndex : -1,
order : 8,
fn : pageObjMgr['seperate']
});
reg({
key : 'completepublish',
type : 'website',
desc : wcm.LANG.WEBSITE_COMPUB||'完全发布这个站点',
title : wcm.LANG.WEBSITE_COMPUB_TITLE||'重新生成这个站点的所有文件',
rightIndex : 4,
order : 9,
fn : pageObjMgr['completepublish']
});
reg({
key : 'updatepublish',
type : 'website',
desc : wcm.LANG.WEBSITE_UPDATEPUB||'更新发布这个站点',
title : wcm.LANG.WEBSITE_UPDATEPUB_TITLE||'更新当前站点和相关栏目的首页',
rightIndex : 5,
order : 10,
fn : pageObjMgr['updatepublish']
});
reg({
key : 'seperate',
type : 'website',
desc : wcm.LANG.WEBSITE_SEP||'分隔线',
rightIndex : -1,
order : 12,
fn : pageObjMgr['seperate']
});
reg({
key : 'likecopy',
type : 'website',
desc : wcm.LANG.WEBSITE_LIKECOPY||'类似创建',
title : wcm.LANG.WEBSITE_LIKECOPY_TITLE||'创建相似的站点（唯一标识和存放位置不同）',
rightIndex : -2,
order : 13,
fn : pageObjMgr['likecopy']
});
reg({
key : 'seperate',
type : 'website',
desc : wcm.LANG.WEBSITE_SEP||'分隔线',
rightIndex : -1,
order : 14,
fn : pageObjMgr['seperate']
});
reg({
key : 'keyword',
type : 'website',
desc : wcm.LANG.WEBSITE_40||'管理关键词',
title : wcm.LANG.WEBSITE_40_TITLE||'管理当前站点下的所有关键词',
rightIndex : 1,
order : 16,
fn : pageObjMgr['keyword']
});
reg({
key : 'keyword',
type : 'websiteInRoot',
desc : wcm.LANG.WEBSITE_40||'管理关键词',
title : wcm.LANG.WEBSITE_40_TITLE||'管理当前站点下的所有关键词',
rightIndex : -2,
order : 5,
fn : pageObjMgr['keyword']
});
reg({
key : 'recycle',
type : 'website',
desc : wcm.LANG.WEBSITE_RECYCLE||'删除站点',
title : wcm.LANG.WEBSITE_RECYCLE_TITLE||'将站点放入回收站',
rightIndex : 2,
order : 15,
fn : pageObjMgr['recycle'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'new',
type : 'websiteInRoot',
desc : wcm.LANG.WEBSITE_NEW||'创建一个新站点',
title : wcm.LANG.WEBSITE_NEW_TITLE||'新建一个站点节点',
rightIndex : -2,
order : 1,
fn : pageObjMgr['new'],
quickKey : 'N'
});
reg({
key : 'quicknew',
type : 'websiteInRoot',
desc : wcm.LANG.WEBSITE_QUICKNEW||'智能创建一个新站点',
title : wcm.LANG.WEBSITE_QUICKNEW_TITLE||'按照已经完成的站点样例智能创建一个相似的新站点',
rightIndex : -2,
order : 2,
fn : pageObjMgr['quicknew'],
isVisible : function(event){
var context = event.getContext();
if(context.params["SITETYPE"] == 0){
return true;
}
return false;
}
});
reg({
key : 'import',
type : 'websiteInRoot',
desc : wcm.LANG.WEBSITE_IMPORT||'从外部导入站点',
title : wcm.LANG.WEBSITE_IMPORT_TITLE||'读取外部站点文件（只支持XML和ZIP文件），并生成相应的站点',
rightIndex : -2,
order : 3,
fn : pageObjMgr['import']
});
reg({
key : 'export',
type : 'websites',
desc : wcm.LANG.WEBSITE_EXPORTSOME||'导出这些站点到',
title : wcm.LANG.WEBSITE_EXPORTSOME_TITLE||'导出这些站点，每个站点生成相应的XML文件',
rightIndex : 1,
order : 22,
fn : pageObjMgr['export'],
quickKey : 'X'
});
reg({
key : 'recycle',
type : 'websites',
desc : wcm.LANG.WEBSITE_RECYCLE||'删除站点',
title : wcm.LANG.WEBSITE_RECYCLE_TITLE|| '将站点放入回收站',
rightIndex : 2,
order : 23,
fn : pageObjMgr['recycle'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'preview',
type : 'websites',
desc : wcm.LANG.WEBSITE_PREVIEWSOME||'预览这些站点',
title : wcm.LANG.WEBSITE_PREVIEWSOME_TITLE || '重新生成并打开这些站点的预览页面',
rightIndex : 3,
order : 24,
fn : pageObjMgr['preview'],
quickKey : 'R'
});
reg({
key : 'increasepublish',
type : 'websites',
desc : wcm.LANG.WEBSITE_ADDPUBSOME||'增量发布这些站点',
title : wcm.LANG.WEBSITE_ADDPUBSOME_TITLE||'发布选中站点和他们的栏目的首页，并且发布所有可发布状态的文档',
rightIndex : 5,
order : 25,
fn : pageObjMgr['increasepublish'],
quickKey : 'P'
});
reg({
key : 'homepublish',
type : 'websites',
desc : wcm.LANG.WEBSITE_HOMEPUBSOME||'仅发布这些站点首页',
title : wcm.LANG.WEBSITE_HOMEPUBSOME_TITLE||'重新生成并发布选中站点的首页',
rightIndex : 5,
order : 26,
fn : pageObjMgr['homepublish']
});
reg({
key : 'completepublish',
type : 'websites',
desc : wcm.LANG.WEBSITE_COMPUBSOME||'完全发布这些站点',
title : wcm.LANG.WEBSITE_COMPUBSOME_TITLE||'重新生成这些站点的所有文件',
rightIndex : 4,
order : 27,
fn : pageObjMgr['completepublish']
});
reg({
key : 'updatepublish',
type : 'websites',
desc : wcm.LANG.WEBSITE_UPDATEPUBSOME||'更新发布这些站点',
title : wcm.LANG.WEBSITE_UPDATEPUBSOME_TITLE||'更新这些站点和相关栏目的首页',
rightIndex : 5,
order : 28,
fn : pageObjMgr['updatepublish']
});
reg({
key : 'seperate',
type : 'websites',
desc : wcm.LANG.WEBSITE_SEP||'分隔线',
title : "分隔线",
rightIndex : -1,
order : 30,
fn : pageObjMgr['seperate']
});
reg({
key : 'createFromFile',
type : 'websiteInRoot',
desc : wcm.LANG.WEBSITE_BATCHNEW||'批量创建站点',
title : wcm.LANG.WEBSITE_BATCHNEW_TITLE||'从文件批量创建站点结构',
rightIndex : -2,
order : 4,
fn : pageObjMgr['createFromFile']
});
reg({
key : 'commentmgr',
type : 'website',
desc : wcm.LANG.WEBSITE_COMMENTMGR||'管理评论',
title : wcm.LANG.WEBSITE_COMMENTMGR_TITLE||'管理该站点下的所有评论',
rightIndex : 8,
order : 17,
fn : pageObjMgr['commentmgr'],
isVisible : function(event){
try{
return $MsgCenter.getActualTop().g_IsRegister['comment'];
}catch(err){
return false;
}
}
});
reg({
key : 'pubstat',
type : 'website',
desc : wcm.LANG.WEBSITE_PUBSTAT ||'发文统计',
titile : '发文统计',
rightIndex : 1,
order : 18,
fn : pageObjMgr['pubstat']
});
reg({
key : 'setappendixsize',
type : 'website',
desc : wcm.LANG.website_2011 || '设置这个站点文档附件大小',
title : wcm.LANG.website_2011 || '设置这个站点上传文档附件大小',
rightIndex : 1,
order : 10,
fn : pageObjMgr['setAppendixSize'],
isVisible : function(event){
return true;
}
});
})();



Ext.ns('wcm.domain.ChannelMgr');
(function (){
var m_oMgr = wcm.domain.ChannelMgr;
Ext.apply(wcm.domain.ChannelMgr, {
'configSCMpublishfield' : function(event){
var currObj = event.getObj();
var nHostId = currObj.getId();
var nHostType = currObj.getIntType();
var currContext = event.getContext();
var sitetype = currContext.params.SITETYPE;
var nSiteType = 0;
if(sitetype != undefined && sitetype != null){
if(sitetype == "4"){
nSiteType = 4;
}
}
var cbId = 'ConfigSCMCrashBoard';//定义弹出框的唯一标识 
var crashboard = wcmXCom.get(cbId);
if(!crashboard){
crashboard = new wcm.CrashBoard({
id: cbId,
title:'配置从WCM发布微博时使用的文档字段',
maskable:true, 
draggable : true,
width : '530px',//宽度
height : '300px',//高度
params : {HostId:nHostId,HostType:nHostType,SiteType:nSiteType},
src: WCMConstants.WCM6_PATH + 'scm/config_documentfield_wcm.jsp',//内嵌页面地址 
callback : function(params){
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call('wcm61_scmmicrocontenttemplate', 'save', 
{ObjectId:params.ObjectId,HostId:params.HostId,HostType:params.HostType,MicroContentStyle:params.MicroContentStyle},
true, function(){
alert("设置成功！");
this.close();
});
}
});
crashboard.show();
}
}
});
})();
(function(){
var fnIsVisible = function(event){
if($MsgCenter.getActualTop().g_IsRegister['SCM']){
var currContext = event.getContext();
var sitetype = currContext.params.SITETYPE;
if(sitetype != undefined && sitetype != null && ( sitetype == "4" || sitetype == "0" )){
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmgroup&methodname=isAdminsOfSCMGroups',
method : 'GET',
parameters : '',
asyn : false
});
if(transport != undefined && transport != null){
var json = parseXml(loadXml(transport.responseText));
return $a(json, "result") == 'true';
}
}
}
return false;
};
var reg = wcm.SysOpers.register;
reg({
key : 'configscmpublishfield',
type : 'channel',
desc : '配置发微博文档字段',
title : '配置从WCM发布微博时使用的文档字段',
fn : function(event){
wcm.domain.ChannelMgr.configSCMpublishfield(event);
},
isVisible : fnIsVisible
});
})();

Ext.ns('wcm.domain.ChannelMgr');
(function (){
Ext.apply(wcm.domain.ChannelMgr, {
addepress : function(event){
var hostId = event.getHost().getId();
var hostType = event.getHost().getIntType();
var oParams = {
parentid: hostType == 101 ? hostId : 0,
siteid: hostType == 101 ? 0 : hostId
};
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'epress/epresschannel_create.jsp?' + $toQueryStr(oParams),
title : wcm.LANG.epress_1000 || '新建电子报栏目', 
callback : function(objId){
FloatPanel.hide();
Ext.Msg.confirm(wcm.LANG.epress_1001 || "创建电子报栏目成功,继续设置栏目属性?",{
ok : function(){
FloatPanel.close();
var params = {
objectid: objId,
channelid: objId,
parentid: hostType == 101 ? hostId : 0,
siteid: hostType == 101 ? 0 : hostId
}
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channel/channel_add_edit.jsp?' + $toQueryStr(params),
title : wcm.LANG.CHANNEL_9||'新建/修改栏目',
callback : function(objId){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
}
});
},
no : function(){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
FloatPanel.close();
}
});
}
});
},
pubhistory : function(event){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'epress/epresschannel_pubhistory.html?ChannelId=' + event.getHost().getId(),
title : wcm.LANG.epress_1003 || '发布电子报历史索引'
});
}
});
})();
(function(){
var ePressVisible = function(){
var ePressEnabled = false;
try{
ePressEnabled = $MsgCenter.getActualTop().bEpressMainEnable;
}catch(error){
}
return ePressEnabled;
}
var fnIsVisible = function(event){
if(!ePressVisible()) {
return false;
}
var objs = event.getObjs();
var context = event.getContext();
if(objs.length() > 0){
var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
}else{
var chnlTypes = context.get('ChannelType') || 0;
}
if(!Array.isArray(chnlTypes)){
chnlTypes = [chnlTypes];
}
var hideChnlTypes = [1,2,11,13];
for (var i = 0; i < chnlTypes.length; i++){
if(hideChnlTypes.include(chnlTypes[i])) return false;
}
return true;
}
var pageObjMgr = wcm.domain.ChannelMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'seperate',
type : 'channel',
desc : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
title : wcm.LANG.CHANNEL_SEPERATE || '分隔线',
rightIndex : -1,
order : 45,
fn : pageObjMgr['seperate']
});
reg({
key : 'addepress',
type : 'channelHost',
desc : wcm.LANG.epress_1004 || '创建电子报栏目',
title : wcm.LANG.epress_1004 || '创建电子报栏目',
rightIndex : 11,
order : 46,
fn : pageObjMgr['addepress'],
isVisible : ePressVisible
});
reg({
key : 'addepress',
type : 'websiteHost',
desc : wcm.LANG.epress_1004 || '创建电子报栏目',
title : wcm.LANG.epress_1004 || '创建电子报栏目',
rightIndex : 11,
order : 300,
fn : pageObjMgr['addepress'],
isVisible : ePressVisible
});
reg({
key : 'pubhistory',
type : 'channel',
desc : wcm.LANG.epress_1003 || '发布电子报历史索引',
title : wcm.LANG.epress_1003 || '发布电子报历史索引...',
rightIndex : 17,
order : 46,
fn : pageObjMgr['pubhistory'],
isVisible : fnIsVisible
});
reg({
key : 'pubhistory',
type : 'channelMaster',
desc : wcm.LANG.epress_1003 || '发布电子报历史索引',
title : wcm.LANG.epress_1003 || '发布电子报历史索引...',
rightIndex : 17,
order : 46,
fn : pageObjMgr['pubhistory'],
isVisible : fnIsVisible
});
})();

(function(){
function createMobileChannel(event, args){
var data = {
SrcChnlId : event.getObj().getId(), 
DestHostId : args.dstId,
DestHostType : args.dstType,
containChildren : args.containChildren,
DocSDate : args.DocSDate
};
BasicDataHelper.call('wcm61_mobileportal', 'createMobileChannel', data, true, function(transport, json){
alert("创建成功");
});
}
var fnIsVisible = function(event){
var srcChannelId = event.getObj().getId();
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isCanCreateMobile',
method : 'GET',
parameters : 'objectid='+srcChannelId +'&objecttype=101',
asyn : false
});
var json = parseXml(loadXml(transport.responseText));
if(json.RESULT == 'false'){
return false;
}
return true;
}
var createMobilechnl = function(event){
var host = event.getHost();
var hostId = host.getId();
var sSrcId = (event.getIds().length == 0)?hostId : event.getIds();
var sSiteType = event.getContext().params["SITETYPE"];
var oParams = {srcId : sSrcId, siteType : sSiteType};
FloatPanel.open(WCMConstants.WCM6_PATH + 'mobile/channel_select_4_create_channel.jsp?' + $toQueryStr(oParams) , '创建移动门户栏目',createMobileChannel.bind(this,event));
}
wcm.SysOpers.register({
key : 'create4mobile',
type : 'channel',
desc : '生成移动门户栏目',
title : '生成移动门户栏目',
rightIndex : 13,
order : 2,
fn : createMobilechnl,
isVisible : fnIsVisible
});
wcm.SysOpers.register({
key : 'create4mobile',
type : 'channelMaster',
desc : '生成移动门户栏目',
title : '生成移动门户栏目',
rightIndex : 13,
order : 3,
fn : createMobilechnl,
isVisible : fnIsVisible
});
})();

Ext.ns('wcm.domain.ChannelMgr');
(function (){
var m_oMgr = wcm.domain.ChannelMgr;
Ext.apply(wcm.domain.ChannelMgr, {
publishpublisheddoc : function(event){
wcm.CrashBoarder.get('publish_published_docs').show({
title : '发布已发文档及其概览',
src : WCMConstants.WCM6_PATH + 'document/publish_publisheddocs_and_folder.html',
width:'570px',
height:'250px',
maskable:true,
callback:function(args){
var extrtParam = {
'StartDocCrtime' : args['starttime'],
'EndDocCrtime' : args['endtime']
}
var aIds = event.getObjsOrHost().getIds();
wcm.domain.PublishAndPreviewMgr.publish(aIds, 101, 'publishpublisheddoc', extrtParam);
this.close();
}
});
}
});
})();
(function(){
var fnIsVisible = function(event){
var objs = event.getObjs();
var context = event.getContext();
if(objs.length() > 0){
var chnlTypes = objs.getPropertyAsInt("chnlType", 0);
}else{
var chnlTypes = context.get('ChannelType') || 0;
}
if(!Array.isArray(chnlTypes)){
chnlTypes = [chnlTypes];
}
var hideChnlTypes = [1,2,11];
for (var i = 0; i < chnlTypes.length; i++){
if(hideChnlTypes.include(chnlTypes[i])) return false;
}
return true;
}
var pageObjMgr = wcm.domain.ChannelMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'publishpublisheddoc',
type : 'channel',
desc : '发布已发文档及其概览',
title : '发布栏目的首页，并且发布指定时间范围的已发文档',
rightIndex : 17,
order : 6.1,
fn : pageObjMgr['publishpublisheddoc'],
isVisible : fnIsVisible
});
reg({
key : 'publishpublisheddoc',
type : 'channelmaster',
desc : '发布已发文档及其概览',
title : '发布栏目的首页，并且发布指定时间范围的已发文档',
rightIndex : 17,
order : 24.1,
fn : pageObjMgr['publishpublisheddoc'],
isVisible : fnIsVisible
});
})();

Ext.ns('wcm.domain.DocumentMgr');
(function(){
Ext.apply(wcm.domain.DocumentMgr, {
defaultformat : function(event){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'editor/defaultformat/read.jsp?ObjId=' + event.getHost().getId() + "&ObjType=" + event.getHost().getIntType(),
title : '默认排版设置',
callback : function(){
CMSObj[oParams["objectid"] > 0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.DocumentMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'defaultformat',
type : 'documentInSite',
desc : '默认排版设置',
title : '默认排版设置的相关配置',
rightIndex : 1,
order : 500,
fn : pageObjMgr['defaultformat']
});
})();

Ext.ns('wcm.domain.DocumentMgr');
(function(){
Ext.apply(wcm.domain.DocumentMgr, {
docPropSet : function(event){
var hostId = event.getHost().getId();
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'website/document_props_showset.jsp?WebsiteId='+hostId,
title : wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制',
callback : function(){
CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE,objId:hostId});
}
});
},
docViewProp : function(event){
var hostId = event.getHost().getId();
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'website/document_props_viewset.jsp?WebsiteId='+hostId,
title : '文档编辑页面属性视图定制',
callback : function(){
CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_WEBSITE,objId:hostId});
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.DocumentMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'docPropSet',
type : 'documentInSite',
desc : wcm.LANG.CHANNEL_75 ||'文档编辑页面属性定制',
title:'文档编辑页面属性定制...',
rightIndex : 13,
order : 501,
fn : pageObjMgr['docPropSet']
});
reg({
key : 'docViewProp',
type : 'documentInSite',
desc : '页面属性视图定制',
title:'文档编辑页面属性视图定制...',
rightIndex : 13,
order : 502,
fn : pageObjMgr['docViewProp']
});
})();

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

Ext.ns('wcm.domain.DocumentMgr');
(function(){
Ext.apply(wcm.domain.DocumentMgr, {
defaultformat : function(event){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'editor/defaultformat/read.jsp?ObjId=' + event.getHost().getId() + "&ObjType=" + event.getHost().getIntType(),
title : '默认排版设置',
callback : function(){
CMSObj[oParams["objectid"] > 0 ? 'afteredit' : 'afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:objId});
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.DocumentMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'defaultformat',
type : 'documentInChannel',
desc : '默认排版设置',
title : '默认排版设置的相关配置',
rightIndex : 13,
order : 99,
fn : pageObjMgr['defaultformat']
});
})();

Ext.ns('wcm.domain.DocumentMgr');
(function(){
Ext.apply(wcm.domain.DocumentMgr, {
'showCrashBoard' : function(event){
var currObj = event.getObj();
var nRecId = currObj.getPropertyAsInt('objId');//获取文档的ChnlDocId
var nStatusId = currObj.getPropertyAsInt('docstatusId');
if(nStatusId != 10){
var sDocTitle = currObj.getPropertyAsString('doctitle');
alert('抱歉，文档还未发布！请确认文档已审核通过并且发布！\n文档标题：' + sDocTitle);
return;
}
var cbId = 'SCMCrashBoard';
var crashboard = wcmXCom.get(cbId);
if(!crashboard){
crashboard = new wcm.CrashBoard({
id: cbId,
title:'从WCM创建微博',
maskable:true, 
draggable : true,
width : '574px',//宽度
height : '415px',//高度
params : {RecId:nRecId,SCMGroupId:0,SiteType:0},
src: WCMConstants.WCM6_PATH + 'scm/create_microblog_wcm.jsp',//内嵌页面地址
callback : function(params){
var patt = new RegExp('非常抱歉');//要查找的字符串
if(patt.test(params)){
return;
}
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call(
'wcm61_scmmicrocontent', 
'save', 
{AccountIds:params._AccountIds, SCMGroupId:params._SCMGroupId, Content:params._MicroContent, Picture:params._Picture,Source:params._Source},
true,
function(){
if(params._hasWorkFlow == '1'){
alert("微博已提交审核人员，请您耐心等待审核结果。");
}else{
alert("已提交发布队列，请您稍后，即刻发布微博！");
}
this.close();
},
function(_transport,_json){$render500Err(_transport,_json);this.close();},
function(_transport,_json){}
);
}
});
crashboard.show();
}
}
} )
})();
(function(){
var fnIsVisible = function(event){
var currObj = event.getObj();
var nStatusId = currObj.getPropertyAsInt('docstatusId');
if(nStatusId == 10){
if($MsgCenter.getActualTop().g_IsRegister['SCM']){
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmgroup&methodname=isAdminsOfSCMGroups',
method : 'GET',
parameters : '',
asyn : false
});
if(transport != undefined && transport != null){
var json = parseXml(loadXml(transport.responseText));
return $a(json, "result") == 'true';
}
}
}
return false;
};
var reg = wcm.SysOpers.register;
reg({
key : 'createmicrocontentinfo', //唯一标识该操作
type : 'chnldoc', //操作面板的类型参考《TRSWCM6.5二次开发-操作面板》
desc : '发布一条微博', //操作的显示名称
title : '发布一条微博', //操作的提示信息
order : 3.1,
fn : function(event){
wcm.domain.DocumentMgr.showCrashBoard(event);
},
isVisible : fnIsVisible
});
})();


Ext.ns('wcm.domain.DocumentMgr');
(function(){
Ext.apply(wcm.domain.DocumentMgr, {
docViewProp : function(event){
var hostId = event.getHost().getId();
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channel/document_props_viewset.jsp?WebsiteId='+hostId,
title : '文档编辑页面属性视图定制',
callback : function(){
CMSObj.afteredit(event)({objType:WCMConstants.OBJ_TYPE_CHANNEL,objId:hostId});
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.DocumentMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'docViewProp',
type : 'documentInChannel',
desc : '页面属性视图定制',
title : '文档编辑页面属性视图定制...',
rightIndex : 13,
order : 99,
fn : pageObjMgr['docViewProp']
});
})();

(function(){
Ext.apply(wcm.domain.ChnlDocMgr, {
settopdocumentforever : function(event){
cb = new wcm.CrashBoard({
title : wcm.LANG.DOCUMENT_PROCESS_276||'置顶设置',
src : WCMConstants.WCM6_PATH+'document/document_settopforever.jsp',
width:'600px',
height:'310px',
maskable:true,
appendParamsToUrl : true,
params : {
DocumentId : event.getObjs().getDocIds(),
ChannelId : event.getObj().getPropertyAsInt('currchnlid')
},
callback : function(oPostData) {
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call("wcm6_document", "settopdocument", oPostData, true,
function(_transport,_json){
event.getObjs().afteredit();
});
this.close();
}
});
cb.show();
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ChnlDocMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var host = event.getHost();
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return false;
}
return true;
};
reg({
key : 'settopdocumentforever',
type : 'chnldoc',
desc : wcm.LANG.DOCUMENT_PROCESS_276||'置顶设置',
title : '置顶设置...',
rightIndex : 62,
order : 16.5,
fn : pageObjMgr['settopdocumentforever'],
isVisible : fnIsVisible
});
})();

(function(){
Ext.apply(wcm.domain.InfoviewDocMgr, {
commentmgr : function(event){
var oParams = Ext.apply({
DocumentId : event.getObj().getPropertyAsInt('docid'),
ChannelId :event.getHost().getId() || 0
}, parseHost(event.getHost()));
var sUrl = WCMConstants.WCM_ROOTPATH +'comment/comment_mgr.jsp?'
+ $toQueryStr(oParams);
$openMaxWin(sUrl);
}
});
function parseHost(host){
if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
return {ChannelId:host.getId(),SiteId:0};
}
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return {SiteId:host.getId(),ChannelId:0};
}
return {};
}
})();
(function(){
var pageObjMgr = wcm.domain.InfoviewDocMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'commentmgr',
type : 'infoviewdoc',
desc : wcm.LANG.DOCUMENT_PROCESS_137 ||'管理评论',
title : wcm.LANG.DOCUMENT_PROCESS_138 ||'管理文档的评论',
rightIndex : 8,
order : 10,
fn : pageObjMgr['commentmgr'],
isVisible : function(event){
try{
return $MsgCenter.getActualTop().g_IsRegister['comment'];
}catch(err){
return false;
}
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

Ext.ns('wcm.domain.MetaViewDataMgr');
(function(){
Ext.apply(wcm.domain.MetaViewDataMgr, {
showCrashBoard : function(event){
var currObj = event.getObj();
var nRecId = currObj.getPropertyAsInt('recid');//获取文档的ChnlDocId
var nStatusId = currObj.getPropertyAsInt('docstatusId');
if(nStatusId != 10){
var sDocTitle = currObj.getPropertyAsString('doctitle');
alert('抱歉，文档还未发布！请确认文档已审核通过并且发布！\n文档标题：' + sDocTitle);
return;
}
var cbId = 'SCMCrashBoard';
var crashboard = wcmXCom.get(cbId);
if(!crashboard){
crashboard = new wcm.CrashBoard({
id: cbId,
title:'从WCM创建微博',
maskable:true, 
draggable : true,
width : '574px',//宽度
height : '415px',//高度
params : {RecId:nRecId,SCMGroupId:0,SiteType:4},
src: WCMConstants.WCM6_PATH + 'scm/create_microblog_wcm.jsp',//内嵌页面地址
callback : function(params){
var patt = new RegExp('非常抱歉');//要查找的字符串
if(patt.test(params)){
return;
}
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call(
'wcm61_scmmicrocontent', 
'save', 
{AccountIds:params._AccountIds, SCMGroupId:params._SCMGroupId, Content:params._MicroContent, Picture:params._Picture,Source:params._Source},
true,
function(){
if(params._hasWorkFlow == '1'){
alert("微博已提交审核人员，请您耐心等待审核结果。");
}else{
alert("已提交发布队列，请您稍后，即刻发布微博！");
}
this.close();
},
function(_transport,_json){$render500Err(_transport,_json);this.close();},
function(_transport,_json){}
);
}
});
crashboard.show();
}
}
} )
})();
(function(){
var fnIsVisible = function(event){
var currObj = event.getObj();
var nStatusId = currObj.getPropertyAsInt('docstatusId');
if(nStatusId == 10){
if($MsgCenter.getActualTop().g_IsRegister['SCM']){
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmgroup&methodname=isAdminsOfSCMGroups',
method : 'GET',
parameters : '',
asyn : false
});
if(transport != undefined && transport != null){
var json = parseXml(loadXml(transport.responseText));
return $a(json, "result") == 'true';
}
}
}
return false;
};
var reg = wcm.SysOpers.register;
reg({
key : 'createmicrocontentinfo', //唯一标识该操作
type : 'MetaViewData', //操作面板的类型参考《TRSWCM6.5二次开发-操作面板》
desc : '发布一条微博', //操作的显示名称
title : '发布一条微博', //操作的提示信息
order : 3.1,
fn : wcm.domain.MetaViewDataMgr['showCrashBoard'], //调用在上面定义的方法
isVisible : fnIsVisible
});
})();


(function(){
Ext.apply(wcm.domain.MetaViewDataMgr, {
settopdocumentforever : function(event){
cb = new wcm.CrashBoard({
title : wcm.LANG.DOCUMENT_PROCESS_276||'置顶设置',
src : WCMConstants.WCM6_PATH+'document/document_settopforever.jsp',
width:'600px',
height:'310px',
maskable:true,
appendParamsToUrl : true,
params : {
DocumentId : event.getObj().docid,
ChannelId : event.getObj().getPropertyAsInt('currchnlid')
},
callback : function(oPostData) {
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call("wcm6_document", "settopdocument", oPostData, true,
function(_transport,_json){
event.getObjs().afteredit();
});
this.close();
}
});
cb.show();
},
settopdocumentquit : function(event){
var sDocId = event.getObj().docid;
var nChnlId = event.getObj().getPropertyAsInt('currchnlid');
var oPostData = {
channelid : nChnlId,
documentid : sDocId,
targetdocumentid : 0,
topflag : 0
};
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call("wcm6_document", "settopdocument", oPostData, true,
function(_transport,_json){
event.getObjs().afteredit();;
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.MetaViewDataMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var host = event.getHost();
if(host.getType()==WCMConstants.OBJ_TYPE_WEBSITE){
return false;
}
return true;
};
reg({
key : 'settopdocumentforever',
type : 'MetaViewData',
desc : wcm.LANG.DOCUMENT_PROCESS_276||'置顶设置',
title : '置顶设置...',
rightIndex : 62,
order : 16.5,
fn : pageObjMgr['settopdocumentforever'],
isVisible : fnIsVisible
});
reg({
key : 'settopdocumentquit',
type : 'MetaViewData',
desc : wcm.LANG.DOCUMENT_PROCESS_232 ||'取消置顶',
title : '取消置顶...',
rightIndex : 62,
order : 16,
fn : pageObjMgr['settopdocumentquit'],
isVisible : function(event){
var docContext = event.getObj();
if(docContext.getPropertyAsString("isTopped") == "true") {
return true;
} else {
return false;
}
}
});
})();

Ext.ns('wcm.domain.TemplateMgr');
(function(){
var m_oTemplateMgr = wcm.domain.TemplateMgr;
Ext.apply(m_oTemplateMgr, {
'addOrEditVisualTemplate' : function(event, bAdd){
var postData = {};
var context = event.getContext();
var host = event.getHost();
if(context) {
if(host.getType() == null) {
postData = context;
}else{
postData['hosttype'] = host.getIntType();
postData['hostid'] = host.getId();
}
}
postData['objectid'] = (bAdd == true ? 0 : event.getIds()[0]);
if(context && context.isTypeStub) {
postData['typestub'] = 1;
}
wcm.CrashBoarder.get('visualtemplate_addedit').show({
title : wcm.LANG.visualTemplate_101 || '新建修改可视化模板',
src : WCMConstants.WCM6_PATH + 'template/visualtemplate_add_edit.jsp',
width:'700px',
height:'400px',
maskable:true,
params : postData,
callback : function(){
CMSObj.afteradd(event)({objType:WCMConstants.OBJ_TYPE_TEMPLATE});
this.hide();
}
});
},
'editGeneralTemplate' : function(event){
var sParams = m_oTemplateMgr.__getCurrentParams(event);
var params = event.getContext();
if(params && params.isTypeStub) {
sParams += '&typestub=1';
}
$openMaxWin(WCMConstants.WCM6_PATH + 'template/template_add_edit.jsp?' + sParams);
},
designVisualTemplate : function(event){
var nTempId = event.getIds();
var hostType = event.getHost().getIntType();
var hostId = event.getHost().getId();
window.open('../special/design.jsp?templateId=' + nTempId + 
'&HostType=' + hostType + '&HostId=' + hostId);
},
edit : function(event){
var bVisual = event.getObj().getPropertyAsString("VisualAble");
if(bVisual == 'true'){
m_oTemplateMgr.addOrEditVisualTemplate(event, false);
}else{
m_oTemplateMgr.editGeneralTemplate(event);
}
}
})
var editTemplate = wcm.SysOpers.getOperItem('template','edit');
Ext.apply(editTemplate, {
fn : function(event){
var bVisual = event.getObj().getPropertyAsString("VisualAble");
if(bVisual == 'true'){
m_oTemplateMgr.addOrEditVisualTemplate(event, false);
}else{
m_oTemplateMgr.edit(event);
}
}
})
Ext.apply(m_oTemplateMgr, {
'addVisualTemplate' : function(event){
m_oTemplateMgr.addOrEditVisualTemplate(event, true);
}
})
})();
(function(){
var pageObjMgr = wcm.domain.TemplateMgr;
var reg = wcm.SysOpers.register;
var fnIsVisible = function(event){
var host = event.getHost();
var context = event.getContext();
if(Ext.isTrue(host.isVirtual) && host.getPropertyAsInt("chnlType", 0) != 0){
return false;
}
return true;
};
reg({
key : 'newvisualtemplate',
type : 'templateInChannel',
desc : wcm.LANG.visualTemplate_102 || '新建可视化模板',
title:'新建可视化模板...',
rightIndex : 21,
order : 7,
fn : pageObjMgr['addVisualTemplate'],
isVisible : fnIsVisible
});
reg({
key : 'newvisualtemplate',
type : 'templateInSite',
desc : wcm.LANG.visualTemplate_102 || '新建可视化模板',
title:'新建可视化模板...',
rightIndex : 21,
order : 12,
fn : pageObjMgr['addVisualTemplate'],
isVisible : fnIsVisible
});
})();

Ext.ns('wcm.domain.WebSiteMgr');
(function (){
var m_oMgr = wcm.domain.WebSiteMgr;
Ext.apply(wcm.domain.WebSiteMgr, {
'configWebSiteSCMpublishfield' : function(event){
var currObj = event.getObj();
var nHostId = currObj.getId();
var nHostType = currObj.getIntType();
var cbId = 'ConfigWebSiteSCMCrashBoard';//定义弹出框的唯一标识 
var crashboard = wcmXCom.get(cbId);
if(!crashboard){
crashboard = new wcm.CrashBoard({
id: cbId,
title:'配置从WCM发布微博时使用的文档字段',
maskable:true, 
draggable : true,
width : '530px',//宽度
height : '300px',//高度
params : {HostId:nHostId,HostType:nHostType,SiteType:0},
src: WCMConstants.WCM6_PATH + 'scm/config_documentfield_wcm.jsp',//内嵌页面地址 
callback : function(params){
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call('wcm61_scmmicrocontenttemplate', 'save', 
{ObjectId:params.ObjectId,HostId:params.HostId,HostType:params.HostType,MicroContentStyle:params.MicroContentStyle},
true, function(){
alert("设置成功！");
this.close();
});
}
});
crashboard.show();
}
}
});
})();
(function(){
var fnIsVisible = function(event){
if($MsgCenter.getActualTop().g_IsRegister['SCM']){
var currContext = event.getContext();
var sitetype = currContext.params.SITETYPE;
if(sitetype != undefined && sitetype != null && sitetype == "0" ){
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmgroup&methodname=isAdminsOfSCMGroups',
method : 'GET',
parameters : '',
asyn : false
});
if(transport != undefined && transport != null){
var json = parseXml(loadXml(transport.responseText));
return $a(json, "result") == 'true';
}
}
}
return false;
};
var reg = wcm.SysOpers.register;
reg({
key : 'configwebsitescmpublishfield',
type : 'website',
desc : '配置发微博文档字段',
title : '配置从WCM发布微博时使用的文档字段',
order: 19,
fn : function(event){
wcm.domain.WebSiteMgr.configWebSiteSCMpublishfield(event);
},
isVisible : fnIsVisible
});
})();

(function(){
var fnIsVisible = function(event){
var srcChannelId = event.getObj().getId();
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isCanCreateMobile',
method : 'GET',
parameters : 'objectid='+srcChannelId +'&objecttype=103',
asyn : false
});
var json = parseXml(loadXml(transport.responseText));
if(json.RESULT == 'false'){
return false;
}
return true;
}
wcm.SysOpers.register({
key : 'create4mobile',
type : 'website',
desc : '智能创建移动站点',
title : '智能创建移动站点',
rightIndex : -2,
order : 2,
fn : function(event){
var siteId = event.getObj().getId();
$openMaxWin(WCMConstants.WCM6_PATH + 'mobile/mobile_create_from_site.html?siteid='+siteId, window.location.hostname.replace(/\.|-/g, "_") + 'mobile_create_from_site', true, true);
},
isVisible : fnIsVisible
});
})();

Ext.ns('wcm.domain.WebSiteMgr');
(function(){
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.WebSiteMgr, {
confphotolib : function(event){
FloatPanel.open(WCMConstants.WCM6_PATH +"photo/photolib_config.jsp",(wcm.LANG.PHOTO_LIB || "图片库设置"),680,280);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.WebSiteMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'confphotolib',
type : 'WebSiteInRoot',
desc : wcm.LANG.PHOTO_LIB || '图片库设置',
title : wcm.LANG.PHOTO_LIB || '图片库设置',
rightIndex : -2,
order : 4,
fn : pageObjMgr['confphotolib'],
isVisible : function(event){
var host = event.getHost();
if(host.getId()==1){
return true;
}
return false;
}
});
wcm.SysOpers.createInterceptor({
key : 'quicknew',
type : 'WebSiteInRoot',
isVisible : function(event){
var host = event.getHost();
if(host.getId()==1){
return false;
}
}
})
})();

Ext.ns('wcm.domain.WebSiteMgr');
(function (){
var m_oMgr = wcm.domain.WebSiteMgr;
Ext.apply(wcm.domain.WebSiteMgr, {
publishpublisheddoc : function(event){
wcm.CrashBoarder.get('publish_published_docs').show({
title : '发布已发文档及其概览',
src : WCMConstants.WCM6_PATH + 'document/publish_publisheddocs_and_folder.html',
width:'570px',
height:'250px',
maskable:true,
callback:function(args){
var extrtParam = {
'StartDocCrtime' : args['starttime'],
'EndDocCrtime' : args['endtime']
}
var aIds = event.getObjsOrHost().getIds();
wcm.domain.PublishAndPreviewMgr.publish(aIds, 103, 'publishpublisheddoc', extrtParam);
this.close();
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.WebSiteMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'publishpublisheddoc',
type : 'website',
desc : '发布已发文档及其概览',
title : '发布站点和栏目的首页，并且发布指定时间范围的已发文档',
rightIndex : 5,
order : 4.1,
fn : pageObjMgr['publishpublisheddoc']
});
})();

Ext.ns('wcm.domain.WebSiteMgr');
(function(){
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.WebSiteMgr, {
confvideolib : function(){
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=200,left=200,border=0,width=560,height=360';
window.open(WCMConstants.WCM6_PATH + "video/config.jsp", "_blank", sFeature);
},
sample : function(){
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=100,left=100,border=0,width=1100,height=650';
window.open(WCMConstants.WCM6_PATH + "video/manSample.jsp", "_blank",sFeature);
}, 
confmas : function(){
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,titlebar=no,toolbar=no,top=200,left=200,border=0,width=560,height=360';
window.open(WCMConstants.WCM6_PATH + "video/configMAS.jsp", "_blank",sFeature);
},
deleteFail : function(event){
window.open(WCMConstants.WCM6_PATH + "video/delete.jsp", "_blank",800,500);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.WebSiteMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'confvideolib',
type : 'WebSiteInRoot',
desc : wcm.LANG.VIDEO_PROCESS_219 || '视频库设置',
title : wcm.LANG.VIDEO_PROCESS_219 || '视频库设置',
rightIndex : -2,
order : 4,
fn : pageObjMgr['confvideolib'],
isVisible : function(event){
var host = event.getHost();
if(host.getId()==2){
return true;
}
return false;
}
});
reg({
key : 'sample',
type : 'WebSiteInRoot',
desc : wcm.LANG.VIDEO_PROCESS_234 || '样本库管理',
title : wcm.LANG.VIDEO_PROCESS_234 || '样本库管理',
rightIndex : -2,
order : 5,
fn : pageObjMgr['sample'],
isVisible : function(event){
var host = event.getHost();
var autoclip=$MsgCenter.getActualTop().autoclip;
if(host.getId()==2 && autoclip==true){
return true;
}
return false;
}
});
reg({
key : 'confmas',
type : 'WebSiteInRoot',
desc : wcm.LANG.videolib_102 || 'MAS推送设置',
title : wcm.LANG.videolib_102 || 'MAS推送设置',
rightIndex : -2,
order : 4,
fn : pageObjMgr['confmas'],
isVisible : function(event){
var host = event.getHost();
if(host.getId()==2){
return true;
}
return false;
}
});
reg({
key : 'deleteFail',
type : 'WebSiteInRoot',
desc : wcm.LANG.VIDEO_PROCESS_223 ||'删除转码失败的视频',
title : wcm.LANG.VIDEO_PROCESS_223 ||'删除转码失败的视频...',
rightIndex : 39,
order : 9,
fn : pageObjMgr['deleteFail'],
isVisible : function(event){
var host = event.getHost();
if(host.getId()==2){
return true;
}
return false;
}
});
wcm.SysOpers.createInterceptor({
key : 'quicknew',
type : 'WebSiteInRoot',
isVisible : function(event){
var host = event.getHost();
if(host.getId()==2){
return false;
}
}
})
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function newChannel(event,result){
var oPostData = {
objectid: 0,
channelid: 0,
parentid: result.hostType == 101 ? result.hostIds : 0,
siteid: result.hostType == 101 ? 0 : result.hostIds
}
setTimeout(function(){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channel/channel_add_edit.jsp?' + $toQueryStr(oPostData),
title : wcm.LANG.CHANNEL_9||'新建/修改栏目',
callback : function(objId){
CMSObj['afteradd'](event)(objId);
}
});
}, 10);
return false;
}
function setImport(event,result){
var oPostData = {
objecttype : result.hostType,
parentid : result.hostIds
};
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH + 'channel/channel_import.html?' + $toQueryStr(oPostData), '栏目导入', function(){
var items = {objType:WCMConstants.OBJ_TYPE_CHANNEL};
var contextType = result.hostType == 101 ? WCMConstants.OBJ_TYPE_CHANNEL : WCMConstants.OBJ_TYPE_WEBSITE;
var context = {objType:contextType, objId:result.hostIds};
cmsobj = new CMSObj.createEnumsFrom(items, context);
cmsobj.addElement(items);
cmsobj.afteradd()
});
}, 10);
return false;
}
function exportAll(result){
var hostId = parseInt(result.hostIds);
var hostType = parseInt(result.hostType);
var oPostData = {};
oPostData[hostType == 101 ? 'parentChannelId' : 'parentSiteId'] = hostId;
setTimeout(function(){
wcm.domain.ChannelMgr.export0(oPostData);
}, 10);
return false;
}
reg({
key : 'channel_add',
desc : wcm.LANG['CHANNEL'] || '栏目',
parent : 'add',
order : '2',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
hidesitetype : 1,
isRadio : 1,
rightIndex : 11,
ExcludeTop : 1,
ExcludeLink : 1
};
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData1), wcm.LANG.SELECT_OBJECT || '选择对象' ,newChannel.bind(this,event));
}
});
reg({
key : 'channel_import',
desc : wcm.LANG['CHANNEL'] || '栏目',
parent : 'import',
order : '2',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData2 = {
hidesitetype : 1,
isRadio : 1,
rightIndex : 11,
ExcludeTop : 1,
ExcludeLink : 1
};
Ext.apply(oPostData2, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData2), wcm.LANG.SELECT_OBJECT || '选择对象' ,setImport.bind(this,event));
}
});
reg({
key : 'channel_export',
desc : String.format("所有子栏目"),
parent : 'export',
order : '2',
cmd :function(event){
var oPostData3 = {
hidesitetype : 1,
isRadio : 1,
rightIndex : 13,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeInfoView : 0
};
var host = event.getHost();
var hostType = host.getIntType();
Ext.apply(oPostData3, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData3), wcm.LANG.SELECT_OBJECT || '选择对象' ,exportAll);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function setRegion(event,result){
var sType = result.hostType;
var bInSite = sType == 103 ? true : false;
var oPostData = {
ChannelId : bInSite ? 0 : result.hostIds,
SiteId : bInSite ? result.hostIds : 0,
ObjectId : 0
}
setTimeout(function(){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_add_edit.jsp?' + $toQueryStr(oPostData),
title : bInSite ? (wcm.LANG.CHANNELCONTENTLINK_FN_31 || '新建站点热词...') : (wcm.LANG.CHANNELCONTENTLINK_FN_2 || '新建栏目热词...'),
callback : function(info){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNELCONTENTLINK, objId:info});
}
});
}, 10);
return false;
}
reg({
key : 'channelcontentlink_add', 
desc : wcm.LANG['CHANNELCONTENTLINK_FN_28'] || '热词',
parent : 'add',
order : '10',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
hidesitetype : 1,
isRadio : 1,
rightIndex : 13,
excludeVirtual :1,
ExcludeInfoView : 0
}
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH +
'include/host_select.html?' + $toQueryStr(oPostData1),
wcm.LANG.CHANNELCONTENTLINK_FN_29 || '选择对象', setRegion.bind(this,event));
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function setRegion(event,result){
var oPostData = {
ChannelId : result.hostIds,
ObjectId : 0
}
setTimeout(function(){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channelsyn/docsyn_dis_add_edit.jsp?' + $toQueryStr(oPostData),
title : wcm.LANG.CHANNELSYN_VALID_6 || '新建栏目分发',
callback : function(info){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNELSYN, objId:info});
}
});
}, 10);
return false;
}
reg({
key : 'channelsyn_add',
desc : wcm.LANG['CHANNELSYN'] || '栏目分发',
parent : 'add',
order : '8',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
hidesitetype : 1,
hidesite : 1,
isRadio : 1,
rightIndex : 13,
excludeVirtual : 1,
siteTypes : "0,4",
ExcludeInfoView : 0
}
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH +
'include/host_select.html?' + $toQueryStr(oPostData1),
wcm.LANG.CHANNELSYN_VALID_7 || '选择对象', setRegion.bind(this,event));
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function setRegion(event,result){
var oPostData = {
ChannelId : result.hostIds,
ObjectId : 0
}
setTimeout(function(){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'channelsyn/docsyn_col_add_edit.jsp?' + $toQueryStr(oPostData),
title : wcm.LANG.CHANNELSYN_VALID_8 || '新建栏目汇总',
callback : function(info){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHANNELSYNCOL, objId:info});
}
});
}, 10);
return false;
}
reg({
key : 'channelsyncol_add',
desc : wcm.LANG['CHANNELSYNCOL'] || '栏目汇总',
parent : 'add',
order : '9',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
hidesitetype : 1,
hidesite : 1,
isRadio : 1,
rightIndex : 13,
excludeVirtual : 1,
siteTypes : "0,4",
ExcludeInfoView : 0
}
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH +
'include/host_select.html?' + $toQueryStr(oPostData1),
wcm.LANG.CHANNELSYN_VALID_7 || '选择对象', setRegion.bind(this,event));
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
var fCls = function(event, descNode){
var m = 'addClass';
if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
}
reg({
key : 'classinfo_add', 
desc : wcm.LANG['CLASSINFO'] || '分类法',
parent : 'add',
order : '15',
cls : fCls,
cmd : function(event){
wcm.domain.classinfoMgr.add(event);
}
});
reg({
key : 'classinfo_import', 
desc : wcm.LANG['CLASSINFO'] || '分类法',
parent : 'import',
order : '5',
cls : fCls,
cmd : function(event){
wcm.domain.classinfoMgr['import'](event);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function setRegion(event,result){
var oPostData = {
HostType : result.hostType,
HostId : result.hostIds,
ObjectId : 0
}
setTimeout(function(){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_addedit.jsp?' + $toQueryStr(oPostData),
title : wcm.LANG.CONTENTEXTFIELD_CONFIRM_3 || '新建扩展字段',
callback : function(info){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CONTENTEXTFIELD, objId:info});
}
});
}, 10);
return false;
}
reg({
key : 'contentextfield_add', 
desc : wcm.LANG['CONTENTEXTFIELD'] || '扩展字段',
parent : 'add',
order : '5',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
isRadio : 1,
rightIndex : 19,
excludeVirtual :1,
ExcludeInfoView : 0
}
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH +
'include/host_select.html?' + $toQueryStr(oPostData1),
wcm.LANG.CONTENTEXTFIELD_CONFIRM_11 || '选择对象', setRegion.bind(this,event));
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
reg({
key:"addDelegate",
desc:"我的委托",
parent:"GRFW",
order:2,
cmd : function(event){
wcm.CrashBoarder.get('my_workflow_delegate').show({
title : '委托管理界面',
src : WCMConstants.WCM6_PATH +'flowdoc/my_delegate.jsp',
width:'360px',
height:'300px',
top:'50px',
left:'350px',
maskable: true,
btns : [
{
text : '关闭', 
cmd : function(){
this.close();
return false;
}
}
]
});
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function getHelper(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
reg({
key : 'document_add', 
desc : wcm.LANG['DOCUMENT'] || '文档',
parent : 'add',
order : '3',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData = {
isRadio : 1,
excludeVirtual : 1,
hidesitetype : 1,
hidesite : 1,
siteTypes : '0',
rightIndex : 31,
showOneType : 1,
ExcludeInfoView : 0
}
Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
oPostData.ExcludeInfoView = 0;
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
wcm.LANG.SELECT_OBJECT || '选择对象' , function(result){
var params = {
DocumentId : 0,
FromEditor : 1,
ChannelId : result.hostIds
};
var iWidth = window.screen.availWidth - 12;
var iHeight = window.screen.availHeight - 30;
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,' 
+ 'toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
window.open(WCMConstants.WCM6_PATH + "document/document_addedit.jsp?" + $toQueryStr(params),
"_blank" , sFeature);
}
);
}
});
reg({
key : 'document_import', 
desc : wcm.LANG['DOCUMENT'] || '文档',
parent : 'import',
order : '3',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData = {
isRadio : 1,
excludeVirtual : 1,
hidesitetype : 1,
hidesite : 1,
siteTypes : '0',
rightIndex : 31,
showOneType : 1,
ExcludeInfoView : 0
}
Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
wcm.LANG.SELECT_OBJECT || '选择对象',function(result){
var params = {
DocumentId : 0,
ChannelId : result.hostIds
};
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_import.jsp?' + $toQueryStr(params),
'文档-导入文档',function(objId){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:objId});
});
}, 10);
return false;
});
}
});
reg({
key : 'document_export', 
desc : String.format("所有文档"),
parent : 'export',
order : '3',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData = {
isRadio : 1,
ExcludeTop : 1,
ExcludeLink : 1,
hidesitetype : 1,
hidesite : 0,
siteTypes : '0',
rightIndex : 34,
showOneType : 1,
ExcludeInfoView : 0
}
Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
wcm.LANG.SELECT_OBJECT || '选择对象',function(result){
var oParams ;
var nChannelId = (result.hostType==101)?result.hostIds:0;
var nSiteId = (result.hostType==103)?result.hostIds:0;
if(nChannelId ==0) {
oParams = {
SiteIds : nSiteId
}
}else {
oParams = {
ChannelIds : nChannelId
}
}
getHelper().call('wcm61_viewdocument', "query",oParams, true,
function(_transport,_json){
var params = {
ExportAll : 1,
ChannelId : nChannelId,
SiteId : nSiteId,
Count : _json.VIEWDOCUMENTS.NUM
}
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH +
'document/document_export.jsp?' + $toQueryStr(params),
'文档-导出所有文档');
}, 10);
}
);
return false;
});
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function add(result){
var oPostData = {
FlowId : 0,
LoadView : 2,
OwnerType : result.hostType,
OwnerId : result.hostIds,
ShowButtons : 0
}
setTimeout(function(){
var cb = wcm.CrashBoarder.get('Dialog_Workflow_Editor');
cb.show({
title : wcm.LANG['FLOW_ADD_EDIT'] || '新建/修改工作流',
src : WCMConstants.WCM6_PATH + 'flow/flow_addedit.jsp',
reloadable : true,
top: '40px',
left: '300px',
width: '850px',
height: '500px',
params : oPostData,
maskable : true,
callback : function(_args){
var result = this;
var id = result.hostIds;
var type = result.hostType;
if(type==101&& _args['id'] > 0) {
BasicDataHelper.call('wcm6_process', 'enableFlowToChannel', {
ObjectId: id,
FlowId: _args['id']
}, false, function(_transport, _json){
$MsgCenter.$main().afteredit();
}
);
}else{
$MsgCenter.$main().afteredit();
}
}.bind(result)
});
}, 10);
return false;
}
reg({
key : 'flow_add', 
desc : wcm.LANG['FLOW'] || '工作流',
parent : 'add',
order : '11',
cmd : function(event){
var oPostData1 = {
isRadio : 1,
excludeVirtual :1,
rightIndex : 41,
ExcludeInfoView : 0
}
var host = event.getHost();
var hostType = host.getIntType();
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH +
'include/host_select.html?' + $toQueryStr(oPostData1),
wcm.LANG.CONFIRM_11 || '选择对象', add);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
})();



(function(){
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
reg({
key : 'metadbtable_add', 
desc : wcm.LANG['METADBTABLE'] || '元数据',
parent : 'add',
order : '13',
cls : function(event, descNode){
var m = 'addClass';
if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
},
cmd : function(event){
wcm.domain.MetaDBTableMgr.add(event);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
reg({
key : 'metaview_add', 
desc : wcm.LANG['METAVIEW'] || '视图',
parent : 'add',
order : '14',
cls : function(event, descNode){
var m = 'addClass';
if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
},
cmd : function(event){
wcm.domain.MetaViewMgr.add(event);
}
});
reg({
key : 'metaview_import', 
desc : wcm.LANG['METAVIEW'] || '视图',
parent : 'import',
order : '14',
cls : function(event, descNode){
var m = 'addClass';
if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
},
cmd : function(event){
wcm.domain.MetaViewMgr['import'](event);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
var oPostData = {
isRadio : 1,
right : 31,
excludeVirtual : 1,
hidesitetype : 1,
hidesite : 1,
siteTypes : '4',
showOneType : 1,
closebyme : 1
}
var getViewIdByChannel = function(channelId, fCallback){
new Ajax.Request(WCMConstants.WCM6_PATH + 'application/common/get_viewid_by_channel.jsp?channelid='+channelId, {
onSuccess : function(transport, json){
if(fCallback) fCallback(transport.responseText);
}
});
}
reg({
key : 'metaviewdata_add', 
desc : wcm.LANG.METAVIEWDATA_108 || '记录',
parent : 'add',
order : '16',
cls : function(event, descNode){
var m = 'addClass';
if(RegsiterMgr.isValidPlugin('metadata')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
},
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
if(hostType==101){
oPostData.channelids = oPostData.selectedChannelIds = host.getId();
}else if(hostType==1){
oPostData.CurrSiteType = 4;
}else{
oPostData.CurrSiteId = host.getId();
}
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
wcm.LANG.SELECT_OBJECT || '选择对象' , function(result){
var sTitle = (wcm.LANG.METAVIEWDATA_77 || "新建")
sTitle += (wcm.LANG.METAVIEWDATA_108 || "记录");
var oParams = {
ObjectId:0,
ChannelId:result.hostIds
};
getViewIdByChannel(result.hostIds, function(viewId){
if(viewId == 0){
Ext.Msg.alert(String.format('栏目[{0}]没有绑定视图', result.hostIds));
return;
}
FloatPanel.close();
$openMaxWin(WCMConstants.WCM6_PATH + './application/' + viewId + '/metaviewdata_addedit.jsp?' + $toQueryStr2(oParams));
});
}
);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function setRegion(result){
var oPostData = {
channelid : result.hostIds
}
setTimeout(function(){
FloatPanel.open(WCMConstants.WCM6_PATH +
'photo/photo_upload.jsp?' + $toQueryStr(oPostData),
wcm.LANG.PHOTO_CONFIRM_61 || '上传图片', uploadCallback);
}, 10);
return false;
}
function uploadCallback(fpInfo){
setTimeout(function(){
FloatPanel.open(fpInfo.src,
fpInfo.title);
}, 10);
return false;
}
reg({
key : 'photo_add', 
desc : wcm.LANG['PHOTO'] || '图片',
parent : 'add',
order : '12',
cls : function(event, descNode){
var m = 'addClass';
if(RegsiterMgr.isValidPlugin('photo')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
},
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
isRadio : 1,
excludeVirtual :1,
SiteTypes : 1,
hidesite : 1,
hidesitetype : 1,
rightIndex : 31
}
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH +
'include/host_select.html?' + $toQueryStr(oPostData1),
wcm.LANG.PHOTO_CONFIRM_62 || '选择对象', setRegion);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function setRegion(event,result){
var oPostData = {
SiteId : result.hostIds,
ObjectId : 0
}
setTimeout(function(){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPostData),
title : wcm.LANG.PUBLISHDISTRUBUTION_VALID_1 || '新建站点分发',
callback : function(info){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION, objId:info});
}
});
}, 10);
return false;
}
reg({
key : 'publishdistribution_add', 
desc : wcm.LANG['PUBLISHDISTRIBUTION'] || '站点分发',
parent : 'add',
order : '6',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
hidesite : 0,
hidesitetype : 1,
hidechannel : 1,
isRadio : 1,
rightIndex : 1,
excludeVirtual : 1
}
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH +
'include/host_select.html?' + $toQueryStr(oPostData1),
wcm.LANG.PUBLISHDISTRUBUTION_VALID_2 || '选择对象', setRegion.bind(this,event));
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function newReplace(event,result){
var oPostData = {
ChannelId : result.hostIds,
ObjectId : 0
};
setTimeout(function(){
FloatPanel.open(
WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + $toQueryStr(oPostData),
wcm.LANG.REPLACE_8||'新建替换内容',
CMSObj.afteradd(event)
);
}, 10);
return false;
}
reg({
key : 'replace_add', 
desc : wcm.LANG['REPLACE'] || '替换内容',
parent : 'add',
order : '7',
cmd : function(event){
var oPostData = {
hidesitetype : 1,
hidesite : 1,
isRadio : 1,
rightIndex : 13,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeInfoView : 0
}
var host = event.getHost();
var hostType = host.getIntType();
Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
wcm.LANG.SELECT_OBJECT || '选择对象' ,
newReplace.bind(this,event)
);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
reg({
key : 'scm',
desc : '社会化内容管理',
parent : 'XJ',
cls : function(event, descNode){
if(g_IsRegister['SCM']){
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmgroup&methodname=isAdminsOfSCMGroups',
method : 'GET',
parameters : '',
asyn : false
});
if(transport != undefined && transport != null){
if (transport.responseText != undefined && transport.responseText != null){
var json = parseXml(loadXml(transport.responseText));
var isPass = $a(json, "result") == 'true';
if(isPass){
Ext.fly(descNode)[ isPass ? 'removeClass' : 'addClass']('disabled');
}else{
transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_scmworkflow&methodname=isAuditorOfSCM',
method : 'GET',
parameters : '',
asyn : false
});
if (transport.responseText != undefined && transport.responseText != null){
json = parseXml(loadXml(transport.responseText));
isPass = $a(json, "result") == 'true';
Ext.fly(descNode)[ isPass ? 'removeClass' : 'addClass']('disabled');
}else{
Ext.fly(descNode)[ false ? 'removeClass' : 'addClass']('disabled');
}
}
}else{
Ext.fly(descNode)[ false ? 'removeClass' : 'addClass']('disabled');
}
}
}else{
Ext.fly(descNode)[ false ? 'removeClass' : 'addClass']('disabled');
}
},
cmd : function(event){
window.open(WCMConstants.WCM6_PATH + 'scm/index.jsp');
}
});
})();

Ext.ns("wcm.menu.TabAdapter", "wcm.menu.OperatorAdapter");
(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
reg({
key : 'KSTD',
desc : wcm.LANG['SYSMENU_1'] || '快速通道',
hotKey : 'Q',
order : 1,
cls : 'wcm-hide-display',
items: [
{
key : 'quickLocate',
desc : wcm.LANG['SYSMENU_2'] || '快速定位',
order : 1,
cmd : function(event){
wcm.CrashBoarder.get('quicklocate').show({
title : wcm.LANG['SYSMENU_2'] || '快速定位',
src : WCMConstants.WCM6_PATH + "nav_tree/quicklocate.html",
left: '180px',
top: '60px',
width: '400px',
height: '270px',
reloadable : false,
params : [],
maskable : true,
callback : function(info){
$('nav_tree').contentWindow.focus(info[0], info[1], null, function(){
try{
var treeNodeDom = $('nav_tree').contentWindow.$(info.join('_'));
if(treeNodeDom==null)return;
treeNodeDomA = treeNodeDom.getElementsByTagName('A')[0];
if(treeNodeDomA==null)return;
var oTreeNode = $('nav_tree').contentWindow.wcm.TreeNode.fly(treeNodeDomA);
var bReturn = oTreeNode.click();
if(bReturn!==false){
oTreeNode.afterclick();
}
}catch(err){}
});
this.close();
}
});
}
}
]
});
wcm.menu.OperatorAdapter = {
execute : function(opers, event){
var sysOpers = event.getContext().sysOpers;
opers = opers[0].concat(opers[1]);
var menuItems = [];
for (var i = 0; i < opers.length; i++){
var oper = opers[i];
if(oper["key"] == 'seperate'){
menuItems.push({type : 'separate', order : i});
continue;
}
menuItems.push({
desc : oper["desc"],
parent : 'opers',
order : i,
params : {dynamic:''},
cmd : sysOpers.exec.bind(window, oper, event)
});
}
return menuItems;
}
};
reg({
key : 'CZRW',
desc : wcm.LANG['SYSMENU_3'] || '操作任务',
hotKey : 'O',
order : 2,
items: [
{key : 'add', desc : (wcm.LANG['SYSMENU_4'] || '新建'), order : 1},
{key : 'import',desc : (wcm.LANG['SYSMENU_5'] || '导入'), order : 2},
{key : 'export',desc : (wcm.LANG['SYSMENU_6'] || '导出'), order : 3},
{type : 'separate', order : 4},
{
key : 'opers',
type : 'dynamic',
order : 5,
items : function(event){
try{
if(!event || !event.getContext()){
return;
}
var opers = event.getContext().sysOpers.getOpers(event);
return wcm.menu.OperatorAdapter.execute(opers, event);
}catch(error){
}
}
}
]
});
reg({
key : 'ST',
desc : wcm.LANG['SYSMENU_7'] || '视图',
hotKey : 'V',
order : 3
});
reg({
type:'checkItem',
key : 'navigate',
desc : wcm.LANG['SYSMENU_44'] || '导航栏',
parent : 'ST',
order : 1,
cmd : function(event){
var hideCls = Element.hasClassName('main_center_container', 'hide_west');
var sMethod = hideCls ? 'expand' : 'collapse';
wcm.Layout[sMethod]('west', 'main_center_container');
},
cls : function(event, descNode){
var hideCls = Element.hasClassName('main_center_container', 'hide_west');
var sMethod = hideCls ? 'removeClass' : 'addClass';
Ext.fly(descNode)[sMethod]('checkItem');
}
});
function getMainSearch(){
try{
var mainWin = $('main').contentWindow;
var excludeRegExp = /[\?&](?:ISSEARCH|DISABLETAB|SITETYPE|SITEID|CHANNELID|RIGHTVALUE|ISVIRTUAL|CHANNELTYPE|TABURL)=[^&]*(?:&$)?/ig;
var currMainSearch = mainWin.location.search.replace(excludeRegExp, "");
var joinRegExpget = /^[\?&]?/;
currMainSearch = currMainSearch.replace(joinRegExpget,"");
}catch(error){
return '';
}
}
reg({
type:'checkItem',
key : 'classic',
desc : wcm.LANG['SYSMENU_43'] || '经典模式',
parent : 'ST',
order : 0.5,
cmd : function(event){
window.m_bClassicList = !window.m_bClassicList;
$MsgCenter.$main({params : getMainSearch()}).redirect();
},
cls : function(event, descNode){
Ext.fly(descNode)[window.m_bClassicList ? 'addClass' : 'removeClass']('checkItem');
}
});
var operpanelhided = false;
$MsgCenter.on({
objType : WCMConstants.OBJ_TYPE_MAINPAGE,
beforeinit : function(event){
$MsgCenter.$main()["operpanel" + (operpanelhided?'hide' : 'show')]();
}
});
reg({
type:'checkItem',
key : 'attribute',
desc : wcm.LANG['SYSMENU_8'] || '属性栏',
parent : 'ST',
order : 2,
cmd : function(event){
$MsgCenter.$main()["operpanel" + (operpanelhided?'show' : 'hide')]();
operpanelhided = !operpanelhided;
},
cls : function(event, descNode){
event = wcm.MenuContext.getEvent('mainpage');
try{
Ext.fly(descNode)[event.getContext().operEnable ? 'removeClass' : 'addClass']('disabled')
}catch(error){
}
Ext.fly(descNode)[operpanelhided ? 'removeClass' : 'addClass']('checkItem')
}
});
wcm.menu.TabAdapter = {
execute : function(defaultTab, tabs, event){
var tabItems = tabs.items;
var menuItems = [];
var context = event.getContext();
for (var i = 0; i < tabItems.length; i++){
var tab = tabItems[i];
tab = tabs[tab.type.toLowerCase()];
if(Ext.isFunction(tab.isVisible)
&& !tab.isVisible(context)){
continue;
}
menuItems.push({
type: defaultTab.type == tab.type ? 'radioItem' : '',
desc : Ext.kaku(tab["desc"], null, context),
parent : 'workwindow',
order : i,
params : {dynamic:''},
cmd : getTabMgr().exec.bind(window, tab, true)
});
}
return menuItems;
}
};
var getTabMgr = function(){
try{
var mainWin = $('main').contentWindow;
return mainWin.wcm.TabManager || wcm.TabManager;
}catch(error){
return wcm.TabManager;
}
};
reg({
key : 'workwindow',
desc : wcm.LANG['SYSMENU_9'] || '工作窗口',
parent : 'ST',
order : 3,
cls : function(event, descNode){
if(!event || !event.getContext()){
return;
}
var hostType = event.getHost().getType();
if(hostType == WCMConstants.OBJ_TYPE_CHANNELMASTER){
hostType = WCMConstants.OBJ_TYPE_CHANNEL;
}
var tabMgr = getTabMgr();
var context = event.getContext();
var defaultTab = tabMgr.getDefaultTab(hostType, context);
Ext.fly(descNode)[!defaultTab ? 'addClass' : 'removeClass']('disabled');
},
items : [
{
type : 'dynamic',
order : 1,
items : function(event, itemNode){
if(!event || !event.getContext()){
return;
}
var hostType = event.getHost().getType();
if(hostType == WCMConstants.OBJ_TYPE_CHANNELMASTER){
hostType = WCMConstants.OBJ_TYPE_CHANNEL;
}
var tabMgr = getTabMgr();
var context = event.getContext();
var defaultTab = tabMgr.getDefaultTab(hostType, context);
if(!defaultTab) return;
var tabs = tabMgr.getTabsByInfo(hostType, true, context);
return wcm.menu.TabAdapter.execute(defaultTab, tabs, event);
}
}
]
});
var getMyFlag = function(sPrefix){
return sPrefix + "_" + m_sUserId + "_" + window.location.hostname.replace(/\.|-/g, "_");
}
var skipTo = function(jsonObj){
if(jsonObj["Path"]){
var winObj = $openMaxWin('/wcm/console/index/index.jsp?Path=' + jsonObj["Path"], getMyFlag('WCM52'));
if(winObj){
winObj.focus();
}else{
var msg = "窗口可能被拦截工具拦截，当前操作失效" + "！\n";
msg += "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
msg += "为此给你带来不便，我们深表歉意！";
Ext.Msg.alert(msg);
}
}else{
Ext.Msg.alert( "想跳转到52页面时，竟然都不存在path参数！！！");
}
}
function hasOperRight(menuName){
if(v6To52[menuName]== '' || v6To52[menuName]== 'undefined'){return true;}
return globalTabDisabled[v6To52[menuName]];
}
window.gSkipTo = skipTo;
reg({
key : 'XZFW',
desc : '协作服务',
hotKey : 'C',
order : 4
});
reg({
key : 'calendar',
desc : '日程安排',
parent : 'XZFW',
order : 2,
cmd : function(event){
skipTo({Path:'calendar,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('calendar') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'communication',
desc : '通讯录',
parent : 'XZFW',
order : 3,
cmd : function(event){
skipTo({Path:'contact,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('contact') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'favorite',
desc : '收藏夹',
parent : 'XZFW',
order : 5,
cmd : function(event){
skipTo({Path:'favorite,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('favorite') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'GRFW',
desc : '个人服务',
hotKey : 'P',
order : 5
});
reg({
key : 'myinformation',
desc :'我的信息',
parent : 'GRFW',
order : 1,
cmd : function(event){
skipTo({Path:'myInformation,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('myInformation') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'myright',
desc : '我的权限',
parent : 'GRFW',
order : 2,
cmd : function(event){
var sFeatures = "status=yes,toolbar=no,menubar=no,location=no,resizable =yes";
window.open('auth/operator/right_view.jsp?OperId=' + m_sUserId + '&OperType=' + userObjType, getMyFlag('myright'), sFeatures);
}
});
reg({
type : 'separate',
parent : 'GRFW',
order : 3
});
reg({
key : 'individuate',
desc :'个性化定制',
parent : 'GRFW',
order : 4,
cmd : function(event){
var isAdmin = false;
if(wcm.AuthServer.isAdmin())
isAdmin = true;
var sUrl = 'individuation/individual.html?path=login&isAdmin=' + isAdmin;
if(window.showModalDialog){
var sFeatures = "dialogHeight:450px;dialogWidth:560px;status:no;scroll:no";
if(!Ext.isIE){
var l = window.screen.width/2 - 280, t = window.screen.height/2 - 150;
sFeatures += ";dialogTop:"+t+"px;dialogLeft:"+l+"px";
}
window.showModalDialog(sUrl, top, sFeatures);
}else{
var sFeatures = "height=450px,width=560px,status=no,scroll=no";
var l = window.screen.width/2 - 280, t = window.screen.height/2 - 150;
sFeatures += ",top="+t+"px,left="+l+"px";
window.open(sUrl, getMyFlag('individuate'), sFeatures);
}
}
});
reg({
key : 'XTGL',
desc : '系统管理',
hotKey : 'M',
order : 6
});
reg({
key : 'publishmonitor',
desc : '发布监控',
parent : 'XTGL',
order : 1,
cmd : function(event){
skipTo({Path:'publicMonitor,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('publicMonitor') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'stathome',
desc : '统计分析',
parent : 'XTGL',
order : 2,
cmd : function(event){
skipTo({Path:'statHome,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('statHome') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'actionlog',
desc :'操作日志',
parent : 'XTGL',
order : 3,
cmd : function(event){
skipTo({Path:'actionLog,0'});
}
});
reg({
key : 'usercontrol',
desc : '用户管理',
parent : 'XTGL',
order : 4,
cmd : function(event){
skipTo({Path:'userControl,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('userControl') ? 'addClass' : 'removeClass']('disabled');
}
});
if(Ext.isDebug()){
reg({
type : 'separate',
parent : 'XTGL',
order : 5
});
reg({
key : 'wcmtools',
desc :'WCM工具',
parent : 'XTGL',
order : 7,
cmd : function(event){
window.open("/wcm/wcm_use/index.html","wcmtools");
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('wcmTools') ? 'addClass' : 'removeClass']('disabled');
}
});
}
reg({
key : 'XTPZ',
desc : '配置管理',
parent : 'XTGL',
order : 6
});
reg({
key : 'otherconfig',
desc : '属性配置',
parent : 'XTPZ',
order : 1,
cmd : function(event){
skipTo({Path:'otherconfig,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('otherconfig') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'systemconfig',
desc : '系统配置',
parent : 'XTPZ',
order : 2,
cmd : function(event){
skipTo({Path:'systemconfig,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('systemconfig') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'sechedual',
desc : '计划调度',
parent : 'XTPZ',
order : 3,
cmd : function(event){
skipTo({Path:'sechedual,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('sechedual') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'hotword',
desc : '热词管理',
parent : 'XTPZ',
order : 4,
cmd : function(event){
skipTo({Path:'hotword,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('hotWord') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'searchconfig',
desc : '检索配置',
parent : 'XTPZ',
order : 5,
cmd : function(event){
$openMaxWin(WCMConstants.WCM6_PATH + 
'search/search_list.jsp');
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('searchconfig') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'XJ',
desc : '选件',
hotKey : 'X',
order : 8
});
reg({
key : 'NRHD',
desc : '内容互动',
parent : 'XJ',
order : 1,
items : [
{
key : 'questionnaire',
desc : '问卷调查',
order : 1,
cmd : function(event){
skipTo({Path:'questionnaire,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('questionnaire') ? 'addClass' : 'removeClass']('disabled');
}
},
{
key : 'commentonline',
desc : '在线评论',
order : 2,
cmd : function(event){
skipTo({Path:'commentonLine,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('commentonLine') ? 'addClass' : 'removeClass']('disabled');
}
}
],
cls : function(event, descNode){
Ext.fly(descNode)[(hasOperRight('questionnaire') && hasOperRight('commentonLine')) ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'adpluin',
desc : '广告管理',
parent : 'XJ',
order : 2,
cmd : function(event){
skipTo({Path:'adPluin,0'});
},
cls : function(event, descNode){
if(!bAdMainEnable) {
descNode.style.display = "none";
return;
}
Ext.fly(descNode)[hasOperRight('adPluin') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'autoinfor',
desc : '智能信息处理',
parent : 'XJ',
order : 3,
cmd : function(event){
skipTo({Path:'autoInfor,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('autoInfor') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'infoview_list',
desc :'自定义表单',
parent : 'XJ',
order : 4,
cmd : function(event){
skipTo({Path:'infoview_list,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[(hasOperRight('infoview_list') || !RegsiterMgr.isValidPlugin('infoview')) ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'infogate',
desc : '数据网关',
parent : 'XJ',
order : 5,
cmd : function(event){
skipTo({Path:'infogate,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('infogate') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'subscribe',
desc : '邮件订阅',
parent : 'XJ',
order : 6,
cmd : function(event){
skipTo({Path:'subscribe,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('subscribe') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'interview',
desc : '嘉宾访谈',
parent : 'XJ',
order : 6,
cmd : function(event){
skipTo({Path:'interview,0'});
},
cls : function(event, descNode){
Ext.fly(descNode)[hasOperRight('interview') ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'special',
desc : '专题制作',
parent : 'XJ',
order : 7,
cmd : function(event){
var winObj = $openMaxWin('special/index.html?AdvanceManageStyle=1&userName='+encodeURIComponent(m_sUserName), getMyFlag('special'));
if(winObj){
winObj.focus();
}else{
var msg = "窗口可能被拦截工具拦截，当前操作失效！\n";
msg += "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
msg += "为此给你带来不便，我们深表歉意！";
Ext.Msg.alert(msg);
}
},
cls : function(event, descNode){
}
});
reg({
key : 'govinfo',
desc : '政府信息公开',
parent : 'XJ',
order : 8,
cmd : function(event){
var winObj = open('../WCMV6/gkml/2menu.jsp', 'replace');
if(winObj){
winObj.focus();
}else{
var msg = "窗口可能被拦截工具拦截，当前操作失效！\n";
msg += "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
msg += "为此给你带来不便，我们深表歉意！";
Ext.Msg.alert(msg);
}
},
cls : function(event, descNode){
Ext.fly(descNode)[!g_IsRegister['metadata'] ? 'addClass' : 'removeClass']('disabled');
}
});
reg({
key : 'stat',
desc : '绩效考核',
parent : 'XJ',
order : 9,
cmd : function(event){
var winObj = open('stat/index.jsp', window.location.hostname.replace(/\.|-/g, "_")+"_stat");
if(winObj){
winObj.focus();
}else{
var msg = "窗口可能被拦截工具拦截，当前操作失效！\n";
msg += "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、网易助手等。然后尝试再次操作！\n";
msg += "为此给你带来不便，我们深表歉意！";
Ext.Msg.alert(msg);
}
},
cls : function(event, descNode){
}
});
reg({
key : 'BZ',
desc :'帮助',
hotKey : 'H',
order : 9
});
reg({
key : 'backfeedOnline',
desc : '在线反馈',
parent : 'BZ',
order : 1,
cmd : function(event){
window.open('http://www.trs.com.cn', window.location.hostname.replace(/\.|-/g, "_") + 'backfeedOnline');
}
});
reg({
key : 'contact',
desc :'联系我们',
parent : 'BZ',
order : 2,
cmd : function(event){
if(!Ext.isIE) return;
var oLink = $("link_for_mail");
if(!oLink){
oLink = document.createElement("a");
oLink.id = 'link_for_mail';
oLink.href = "mailto:support@trs.com.cn";
oLink.style.display = 'none';
document.body.appendChild(oLink);
}
oLink.click();
},
cls : function(event, descNode){
if(Ext.isIE) return;
if($('link_for_mail')) return;
new Insertion.Bottom(descNode, '<a id="link_for_mail" href="mailto:support@trs.com.cn" class="mailto"></a>');
}
});
reg({
key : 'aboutwcm',
desc :'关于WCM',
parent : 'BZ',
order : 3,
cmd : function(event){
if(window.showModalDialog){
var sFeatures = "dialogHeight:350px;dialogWidth:410px;status:no;scroll:no;";
showModalDialog("main/about.jsp", null, sFeatures);
}else{
var sFeatures = "height:350px,width:410px,status:no;scroll:no;";
window.open('main/about.jsp', window.location.hostname.replace(/\.|-/g, "_") + 'aboutwcm', sFeatures);
}
}
});
})();
wcm.menu.HostSelectAdapter = {
execute : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData = {};
if(hostType==101){
oPostData.channelids = host.getId();
oPostData.CurrChannelId = host.getId();
}else if(hostType == 1){
oPostData.sitetype = host.getId();
}else{
oPostData.siteids = host.getId();
oPostData.CurrSiteId = host.getId();
}
return oPostData;
}
};

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function newTemp(result){
var oPostData = {
HostType : result.hostType,
HostId : result.hostIds,
ObjectId : 0
};
setTimeout(function(){
$openMaxWin(WCMConstants.WCM6_PATH +'template/template_add_edit.jsp?' + $toQueryStr(oPostData));
}, 10);
return false;
}
function setImport(event,result){
var oPostData = {
HostType : result.hostType,
HostId : result.hostIds,
ObjectId : 0
};
setTimeout(function(){
FloatPanel.open(
WCMConstants.WCM6_PATH + 'template/template_import.html?' + $toQueryStr(oPostData), 
wcm.LANG.TEMPLATE_24 || '模板导入', 
CMSObj.afteradd(event)
);
}, 10);
return false;
}
function exportAll(result){
var oPostData = {
HostType : result.hostType,
HostId : result.hostIds,
ContainsChildren : true,
exportAll : true
};
wcm.domain.TemplateMgr.getHelper().call(wcm.domain.TemplateMgr.serviceId, 
'export', oPostData, true, function(_oXMLHttp, _json){
var sFileUrl = _oXMLHttp.responseText;
var frm = document.getElementById("ifrmDownload");
if(frm == null) {
frm = document.createElement('iframe');
frm.style.height = 0;
frm.style.width = 0;
document.body.appendChild(frm);
}
sFileUrl = WCMConstants.WCM_ROOTPATH + "file/read_file.jsp?DownName=TEMPLATE&FileName=" + sFileUrl;
frm.src = sFileUrl;
}.bind(this));
return false;
}
reg({
key : 'template_add', 
desc : wcm.LANG['TEMPLATE'] || '模板',
parent : 'add',
order : '4',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
hidesitetype : 1,
isRadio : 1,
rightIndex : 21,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeInfoView : 0
};
Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData1),
wcm.LANG.SELECT_OBJECT || '选择对象' ,
newTemp
);
}
});
reg({
key : 'template_import', 
desc : wcm.LANG['TEMPLATE'] || '模板',
parent : 'import',
order : '4',
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData2 = {
hidesitetype : 1,
isRadio : 1,
rightIndex : 21,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeInfoView : 0
};
Ext.apply(oPostData2, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData2), 
wcm.LANG.SELECT_OBJECT || '选择对象' ,
setImport.bind(this,event)
);
}
});
reg({
key : 'template_export', 
desc : String.format("所有模板"),
parent : 'export',
order : '4',
cmd : function(event){
var oPostData3 = {
hidesitetype : 1,
isRadio : 1,
rightIndex : 24,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeInfoView : 0
};
var host = event.getHost();
var hostType = host.getIntType();
Ext.apply(oPostData3, wcm.menu.HostSelectAdapter.execute(event));
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData3),
wcm.LANG.SELECT_OBJECT || '选择对象' ,
exportAll
);
}
});
})();

(function(){
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
var fCls = function(event, descNode){
var m = 'addClass';
if(RegsiterMgr.isValidPlugin('video')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
}
reg({
key : 'video_add', 
desc : wcm.LANG['VIDEO'] || '视频',
parent : 'add',
order : '999',
cls : fCls,
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData = {
isRadio : 1,
excludeVirtual : 1,
hidesitetype : 1,
hidesite : 1,
siteTypes : '2',
rightIndex : 31,
showOneType : 1,
ExcludeInfoView : 0
}
Ext.apply(oPostData, wcm.menu.HostSelectAdapter.execute(event));
oPostData.ExcludeInfoView = 0;
FloatPanel.open(WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData),
wcm.LANG.SELECT_OBJECT || '选择对象' , function(result){
var params = {
DocumentId : 0,
FromEditor : 1,
ChannelId : result.hostIds
};
var iWidth = window.screen.availWidth - 12;
var iHeight = window.screen.availHeight - 30;
var sFeature = 'location=no,resizable=yes,menubar=no,scrollbars=no,status=no,titlebar=no,' 
+ 'toolbar=no,top=0,left=0,border=0,width='+iWidth+',height='+iHeight;
window.open(WCMConstants.WCM6_PATH + "video/video_addedit.jsp?" + $toQueryStr(params),
"_blank" , sFeature);
}
);
}
});
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
})();

(function(){
var reg = wcm.MenuView.register.bind(wcm.MenuView);
function newSite(event,result){
var oPostData = {
sitetype : result.sitetype[0].id || 0,
ObjectId : 0
};
setTimeout(function(){
FloatPanel.open({
src : WCMConstants.WCM6_PATH + 'website/website_add_edit.jsp?' + $toQueryStr(oPostData),
title : wcm.LANG.WEBSITE_1||'新建/修改站点',
callback : function(info){
CMSObj['afteradd'](event)({objType:WCMConstants.OBJ_TYPE_WEBSITE, objId:info});
}
});
}, 10);
return false;
}
var fCls = function(event, descNode){
var m = 'addClass';
if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
m = 'removeClass';
}
Ext.fly(descNode)[m]('disabled');
};
reg({
key : 'website_add', 
desc : wcm.LANG['WEBSITE'] || '站点',
parent : 'add',
order : '1',
cls : fCls,
cmd : function(event){
var host = event.getHost();
var hostType = host.getIntType();
var oPostData1 = {
hidesite : 1,
hidechannel : 1,
rightIndex : -2,
isRadio : 1
};
if(hostType == 1){
oPostData1.sitetype = host.getId();
}else{
oPostData1.sitetype = event.getContext().get('sitetype');
}
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/host_select.html?'+ $toQueryStr(oPostData1), 
wcm.LANG.SELECT_OBJECT || '选择对象' ,
newSite.bind(this,event)
);
}
});
reg({
key : 'website_import', 
desc : wcm.LANG['WEBSITE'] || '站点',
parent : 'import',
order : '1',
cls : fCls,
cmd : function(event){
wcm.domain.WebSiteMgr['import'](event);
}
});
})();

(function(){
return;
var reg = wcm.MenuView.register.bind(wcm.MenuView);
reg({
key : 'document_import',
desc : wcm.LANG.extensions_1001 || '文档',
parent : 'import',
order : 3,
cmd : function(event){
}
});
reg({
key : 'template_import',
desc : wcm.LANG.extensions_1002 || '模板',
parent : 'import',
order : 4,
cmd : function(event){
}
});
reg({
key : 'document_export',
desc : wcm.LANG.extensions_1003 || '所有文档',
parent : 'export',
order : 3,
cmd : function(event){
}
});
reg({
key : 'template_export',
desc : wcm.LANG.extensions_1004 || '所有模板',
parent : 'export',
order : 4,
cmd : function(event){
}
});
reg({
key : 'document_add',
desc : wcm.LANG.extensions_1001 || '文档',
parent : 'add',
order : 3,
cmd : function(event){
}
});
reg({
key : 'template_add',
desc : wcm.LANG.extensions_1002 || '模板',
parent : 'add',
order : 4,
cmd : function(event){
}
});
reg({
key : 'extendfield_add',
desc : wcm.LANG.extensions_1005 || '扩展字段',
parent : 'add',
order : 5,
cmd : function(event){
}
});
reg({
key : 'distribution_add',
desc : wcm.LANG.extensions_1006 || '站点分发',
parent : 'add',
order : 6,
cmd : function(event){
}
});
reg({
key : 'replace_add',
desc : wcm.LANG.extensions_1007 || '替换内容',
parent : 'add',
order : 7,
cmd : function(event){
}
});
reg({
key : 'docsyndis_add',
desc : wcm.LANG.extensions_1008 || '栏目分发',
parent : 'add',
order : 8,
cmd : function(event){
}
});
reg({
key : 'docsyncol_add',
desc : wcm.LANG.extensions_1009 || '栏目汇总',
parent : 'add',
order : 9,
cmd : function(event){
}
});
reg({
key : 'workflow_add',
desc : wcm.LANG.extensions_1010 || '工作流',
parent : 'add',
order : 10,
cmd : function(event){
}
});
reg({
key : 'photo_add',
desc : wcm.LANG.extensions_1011 || '图片',
parent : 'add',
order : 11,
cmd : function(event){
}
});
})();

(function(){
var isCanCreateMobile = -1;
var isCanCreateMobileFn = function(){
var transport = ajaxRequest({
url : WCMConstants.WCM_ROOTPATH + 'center.do?serviceid=wcm61_mobileportal&methodname=isCanCreateMobile',
parameters : 'objectid=0&objecttype=0',
asyn : false
});
var json = parseXml(loadXml(transport.responseText));
if(json.RESULT == 'false'){
return false;
}
return true;
}
var reg = wcm.MenuView.register.bind(wcm.MenuView);
reg({
key : 'smobile',
desc : '智能创建移动门户',
parent : 'XJ',
cls : function(event, descNode){
if(isCanCreateMobile === -1){
isCanCreateMobile = 0;
if(wcm.AuthServer.isAdmin() && isCanCreateMobileFn()){
isCanCreateMobile = 1;
}
}
Ext.fly(descNode)[isCanCreateMobile==0 ? 'addClass' : 'removeClass']('disabled');
},
cmd : function(event){
window.open(WCMConstants.WCM6_PATH + 'smobile/index.jsp');
}
});
})();

(function(){
var oTabItem = {
type : 'advisor',
desc : '顾问',
url : WCMConstants.WCM6_PATH + 'advisor/advisor_list.jsp',
fittable : false,
rightIndex : '13',
order : '9',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
HostId : PageContext.hostId,
HostType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '9'}, oTabItem)]
});
})();

(function(){
var oTabItem = {
type : 'right',
desc : wcm.LANG['RIGHTSET'] || '权限',
fittable : false,
url : WCMConstants.WCM6_PATH + 'auth/right_set.jsp',
rightIndex : -1,
order : '4.5',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
ObjId : PageContext.hostId,
ObjType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [oTabItem]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[oTabItem]
});
})();

(function(){
var oTabItem = {
type : 'channel',
desc : function(context){
if(context && context.host && (context.host.objType==WCMConstants.OBJ_TYPE_CHANNELMASTER 
|| context.host.objType==WCMConstants.OBJ_TYPE_CHANNEL)){
return wcm.LANG['CHILDCHANNEL'] || '子栏目';
}
return wcm.LANG['CHANNEL'] || '栏目';
},
url : WCMConstants.WCM6_PATH + 'channel/channel_thumb.html',
rightIndex : '-1',
order : '2',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({isdefault:true}, oTabItem)]
});
})();
(function(){
var tabType = 'channel';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channel/channel_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'channelcontentlink',
desc : wcm.LANG['CHANNELCONTENTLINK'] || '热词',
url : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_list.html',
rightIndex : '13',
order : '10',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) || (context.params.SITETYPE != 0 && context.params.SITETYPE != 4)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items : [Ext.applyIf({order : '6',rightIndex:'1'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '10'}, oTabItem)]
});
})();
(function(){
var tabType = 'channelcontentlink';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'channelsyn',
desc : wcm.LANG['DOCUMENTSYN'] || '文档同步',
url : WCMConstants.WCM6_PATH + ((getParameter['CONTAINSRIGHT'])? 'channelsyn/channelsyn_list.html' : 'channelsyn/channelsyn_list.html'),
rightIndex : '13',
order : '8',
isVisible : function(context){
var host = context.host;
if((Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE']==0) || (context.params.SITETYPE != 0 && context.params.SITETYPE != 4)){//检索栏目
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '8'}, oTabItem)]
});
})();
(function(){
var tabType = 'channelsyn';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyn_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'channelsyncol',
desc : wcm.LANG['DOCUMENTSYN'] || '文档同步',
url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyncol_list.html',
rightIndex : '13',
order : '8'
}
})();
(function(){
var tabType = 'channelsyncol';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyncol_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'chnlrecycle',
desc : wcm.LANG['CHNLRECYCLE'] || '栏目回收站',
url : WCMConstants.WCM6_PATH + 'chnlrecycle/chnlrecycle_list.html',
rightIndex : '-1,12',
order : '98'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '98'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '97'}, oTabItem)]
});
})();
(function(){
var tabType = 'chnlrecycle';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'chnlrecycle/chnlrecycle_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'classinfo',
desc : wcm.LANG['CLASSINFO'] || '分类法',
url : WCMConstants.WCM6_PATH + 'classinfo/classinfo_list.html',
rightIndex : '-2',
order : '6',
isVisible : function(context){
return context.params['SITETYPE'] == '4';
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '6'}, oTabItem)]
});
})();
(function(){
var tabType = 'classinfo';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'classinfo/classinfo_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'contentextfield',
desc : wcm.LANG['CONTENTEXTFIELD'] || '扩展字段',
url : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_list.html',
rightIndex : 19,
order : '7',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) || Ext.isTrue(context.params.CHANNELTYPE == 13)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '6'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items :[Ext.applyIf({order : '3'}, oTabItem)]
});
})();
(function(){
var tabType = 'contentextfield';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'docrecycle',
desc : wcm.LANG['DOCRECYCLE'] || '废稿箱',
url : WCMConstants.WCM6_PATH + 'docrecycle/docrecycle_list.html',
rightIndex : '18',
order : '9',
isVisible : function(context){
var host = context.host;
if(context.params['SITETYPE'] == '1' || context.params['SITETYPE'] == '2'){
return false;
}
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE']==0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '9'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '9'}, oTabItem)]
});
})();
(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'docrecycle/docrecycle_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'document',
desc : wcm.LANG.DOCUMENT || '文档',
url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
rightIndex : '30-34,38-39',
order : 1
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({isdefault:true}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items : [oTabItem]
});
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'document/document_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'filter',
desc : '筛选器',
url : WCMConstants.WCM6_PATH + 'filter/filter_list.jsp',
fittable : false,
rightIndex : '13',
order : '10',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
HostId : PageContext.hostId,
HostType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '10'}, oTabItem)]
});
})();

(function(){
var oTabItem = {
type : 'flow',
desc : wcm.LANG['FLOW'] || '工作流',
url : WCMConstants.WCM6_PATH + 'flow/flow_list.html',
rightIndex : '41-45',
order : '5',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({url : WCMConstants.WCM6_PATH + 'flow/flow_chnl_list.html',
rightIndex : '13,41-45'
}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items :[Ext.applyIf({order : '3'}, oTabItem)]
});
})();
(function(){
var tabType = 'flow';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flow/flow_classic_list.html'
}, tabItem);
}
var wrapper1 = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flow/flow_classic_chnl_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper1);
})();

(function(){
var oTabItem1 = {
type : 'flowdoc1',
desc : wcm.LANG['FLOWDOC_FORDEAL'] || '待处理',
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=1',
rightIndex : '-1',
order : 1,
isdefault : true
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
items :[oTabItem1]
});
oTabItem2 = {
type : 'flowdoc2',
desc : wcm.LANG['FLOWDOC_DEALED'] || '已处理',
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=2',
rightIndex : '-1',
order : 2
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
items :[oTabItem2]
});
oTabItem3 = {
type : 'flowdoc3',
desc : wcm.LANG['FLOWDOC_FROMME'] || '我发起',
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=3',
rightIndex : '-1',
order : 3
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
items :[oTabItem3]
});
})();
(function(){
var tabType = 'flowdoc1';
var wrapper = function(context, tabItem){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=1'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'flowdoc2';
var wrapper = function(context, tabItem){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=2'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'flowdoc3';
var wrapper = function(context, tabItem){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=3'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'logo',
desc : wcm.LANG['LOGO'] || '栏目Logo',
url : WCMConstants.WCM6_PATH + 'logo/logo_list.jsp',
fittable : false,
rightIndex : '13',
order : '8',
extraCls : 'logoTabCls',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
HostId : PageContext.hostId,
HostType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '8'}, oTabItem)]
});
})();

(function(){
var oTabItem1 = {
type : 'message0',
desc : wcm.LANG['MESSAGE_tab_34'] || '最新消息',
url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=0',
rightIndex : '-1',
order : '1',
isdefault : true
}
oTabItem2 = {
type : 'message2',
desc : wcm.LANG['MESSAGE_tab_35'] || '已收消息',
url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=2',
rightIndex : '-1',
order : 2
}
oTabItem3 = {
type : 'message1',
desc : wcm.LANG['MESSAGE_tab_36'] || '已发消息',
url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=1',
rightIndex : '-1',
order : 3
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
items : [Ext.applyIf({order : '1'}, oTabItem1)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
items : [Ext.applyIf({order : '2'}, oTabItem2)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
items :[Ext.applyIf({order : '3'}, oTabItem3)]
});
})();
(function(){
var tabType = 'message0';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=0'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'message2';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=2'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'message1';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=1'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'metadbtable',
desc : wcm.LANG['METADBTABLE'] || '元数据',
url : WCMConstants.WCM6_PATH + 'metadbtable/metadbtable_thumb.html',
rightIndex : '-2',
order : '4',
isVisible : function(context){
return context.params['SITETYPE'] == '4';
}
} 
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '4'}, oTabItem)]
});
})();
(function(){
var tabType = 'metadbtable';
var wrapper = function(context, tabItem){
if($MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(!context.params['TABLEINFOID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metadbfield/metadbfield_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metadbtable';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(context.params['TABLEINFOID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metadbtable/metadbtable_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metadbtable';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(!context.params['TABLEINFOID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metadbfield/metadbfield_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'metarecdata',
desc : wcm.LANG['METARECDATA'] || '记录列表',
url : WCMConstants.WCM6_PATH + 'metarecdata/metarecdata_list.html',
rightIndex : '-1',
order : '8'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CLASSINFO,
items : [Ext.applyIf({order : '8'}, oTabItem)]
});
})();
(function(){
var tabType = 'metarecdata';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metarecdata/metarecdata_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CLASSINFO, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metarecdata';
var wrapper = function(context, tabItem){
if(context.params.VIEWID == "0" || context.params.FILTERTYPE){
var sUrl = 'viewclassinfo/classinfo_document_list.html';
if(context.params.VIEWID != "0"){
sUrl = 'viewclassinfo/metarecdata_list.html';
}
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + sUrl
}, tabItem);
}
return tabItem;
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CLASSINFO, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'metaview',
desc : wcm.LANG['METAVIEW'] || '视图',
url : WCMConstants.WCM6_PATH + 'metaview/metaview_thumb.html',
rightIndex : '-2',
order : '4',
isVisible : function(context){
if(context.params['SITETYPE'] == '4' && (context.params['CHANNELTYPE'] == '0' || !context.params['CHANNELTYPE'] )){
return true;
}
return false;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '5'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '1.2',rightIndex : '13'}, oTabItem)]
});
})();
(function(){
var tabType = 'metaview';
var wrapper = function(context, tabItem){
if(context.params['VIEWID'] || context.params['CHANNELID']){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metaviewfield/metaviewfield_list.html'
}, tabItem);
}
return tabItem;
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metaview';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(context.params['VIEWID'] || context.params['CHANNELID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metaview/metaview_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metaview';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(!context.params['VIEWID'] && !context.params['CHANNELID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metaviewfield/metaviewfield_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'publishdistribution',
desc : wcm.LANG['PUBLISHDISTRIBUTION'] || '站点分发',
url : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_list.html',
rightIndex : '1',
order : '7'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '7'}, oTabItem)]
});
})();
(function(){
var tabType = 'publishdistribution';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'region',
desc : wcm.LANG.region_2345 || '导读',
url : WCMConstants.WCM6_PATH + 'region/region_list.html',
rightIndex : '-1',
order : '6',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual)){
return false;
}
if(context.params['SITETYPE']==0 || context.params['SITETYPE']==4){
return true;
}
return false;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items :[Ext.applyIf({rightIndex:48}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({rightIndex:48}, oTabItem)]
});
})();
(function(){
var tabType = 'region';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'region/region_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'replace',
desc : wcm.LANG['REPLACE'] || '替换内容',
url : WCMConstants.WCM6_PATH + 'replace/replace_list.html',
rightIndex : '13',
order : '6',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '6'}, oTabItem)]
});
})();
(function(){
var tabType = 'replace';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'replace/replace_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'siterecycle',
desc : wcm.LANG['SITERECYCLE'] || '站点回收站',
url : WCMConstants.WCM6_PATH + 'siterecycle/siterecycle_list.html',
rightIndex : '-1,2',
order : '96',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '96'}, oTabItem)]
});
})();
(function(){
var tabType = 'siterecycle';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'siterecycle/siterecycle_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'template',
desc : wcm.LANG['TEMPLATE'] || '模板',
url : WCMConstants.WCM6_PATH + 'template/template_list.html',
rightIndex : '21-25,28',
order : '3',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({}, oTabItem)]
});
})();
(function(){
var tabType = 'template';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'template/template_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'templateArg',
desc : wcm.LANG['TEMPLATEARG'] || '模板变量',
url : WCMConstants.WCM6_PATH + 'template/template_arg_list.html',
rightIndex : '53',
order : '100'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '100'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '100'}, oTabItem)]
});
})();
(function(){
var tabType = 'templateArg';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'template/template_arg_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'trsserver',
desc : wcm.LANG['DOCUMENT'] || '文档',
url : WCMConstants.WCM6_PATH + 'trsserver/trsserver_classic_list.html?ViewType=1', //'trsserver/trsserver_classic_list.html?ViewType=1',
rightIndex : '-1',
order : 1,
isdefault : true
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_TRSSERVERLIST,
items :[oTabItem]
});
})();

(function(){
var oTabItem = {
type : 'viewoper',
desc : wcm.LANG['VIEWOPER'] || '访问控制',
fittable : false,
url : WCMConstants.WCM6_PATH + 'auth/view_oper_set.jsp',
rightIndex : -1,
order : 4,
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
ObjId : PageContext.hostId,
ObjType : PageContext.intHostType,
FilterRight : 0
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [oTabItem]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[ oTabItem]
});
})();

(function(){
var oTabItem = {
type : 'watermark',
desc : wcm.LANG['WATERMARK'] || '水印',
url : WCMConstants.WCM6_PATH + 'watermark/watermark_thumb.html',
rightIndex : '-1',
order : '8',
isVisible : function(context){
if(context.params['SITETYPE']!=1){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '8'}, oTabItem)]
});
})();
(function(){
var tabType = 'watermark';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'watermark/watermark_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'website',
desc : wcm.LANG['WEBSITE'] || '站点',
url : WCMConstants.WCM6_PATH + 'website/website_thumb.html',
rightIndex : '-1',
order : '1'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({isdefault:true}, oTabItem)]
});
})();
(function(){
var tabType = 'website';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'website/website_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
return Ext.applyIf({
desc : wcm.LANG['DOCRECYCLE'] || '废稿箱',
url : WCMConstants.WCM6_PATH + 'photorecycle/recycle_thumb.html',
rightIndex : '18',
order : '9',
isVisible : function(context){
return true;
}
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'photorecycle/recycle_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
return Ext.applyIf({
desc : wcm.LANG.videorecycle_101 || '废稿箱',
url : WCMConstants.WCM6_PATH + 'video/videorecycle_thumb.html',
rightIndex : '18',
order : '9',
isVisible : function(context){
return true;
}
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'video/videorecycle_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['CHANNELTYPE']!=13)return tabItem;
return Ext.applyIf({
desc : wcm.LANG['INFOVIEWDOC'] || '表单',
url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['CHANNELTYPE']!=13)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'infoview/infoviewdoc_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
var bClassicList = 0;
if(context.params['SITETYPE'] != 4)return tabItem;
if($MsgCenter.getActualTop().m_bClassicList) bClassicList = 0;
return Ext.applyIf({
desc : wcm.LANG['METAVIEWDATA'] || '璧勬簮',
url : WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_list_redirect.jsp?bClassicList=' + bClassicList ,
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
return Ext.applyIf({
desc : wcm.LANG.PHOTO || '图片',
url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
rightIndex : '30-34,38-39',
order : '1',
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'photo/photo_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
return Ext.applyIf({
desc : wcm.LANG.video_1201 || '视频',
url : WCMConstants.WCM6_PATH + 'video/video_thumb.html',
rightIndex : '30-34,38-39',
order : '1',
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'video/video_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();


Ext.ns('wcm.Channels', 'wcm.Channel');
WCMConstants.OBJ_TYPE_CHANNEL = 'channel';
wcm.Channels = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNEL;
wcm.Channels.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Channels, wcm.CMSObjs, {
});
wcm.Channel = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHANNEL;
wcm.Channel.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHANNEL, 'wcm.Channel');
Ext.extend(wcm.Channel, wcm.CMSObj, {
});

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

Ext.ns('wcm.Chnlrecycles', 'wcm.Chnlrecycle');
WCMConstants.OBJ_TYPE_CHNLRECYCLE = 'Chnlrecycle';
wcm.Chnlrecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHNLRECYCLE;
wcm.Chnlrecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Chnlrecycles, wcm.CMSObjs, {
});
wcm.Chnlrecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHNLRECYCLE;
wcm.Chnlrecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CHNLRECYCLE, 'wcm.Chnlrecycle');
Ext.extend(wcm.Chnlrecycle, wcm.CMSObj, {
});

Ext.ns('wcm.classinfos', 'wcm.classinfo');
WCMConstants.OBJ_TYPE_CLASSINFO = 'classinfo';
wcm.classinfos = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CLASSINFO;
wcm.classinfos.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.classinfos, wcm.CMSObjs, {
});
wcm.classinfo = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CLASSINFO;
wcm.classinfo.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_CLASSINFO, 'wcm.classinfo');
Ext.extend(wcm.classinfo, wcm.CMSObj, {
});

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

Ext.ns('wcm.DocBaks', 'wcm.DocBak');
WCMConstants.OBJ_TYPE_DOCBAK = 'DocBak';
wcm.DocBaks = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCBAK;
wcm.DocBaks.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.DocBaks, wcm.CMSObjs, {
});
wcm.DocBak = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCBAK;
wcm.DocBak.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_DOCBAK, 'wcm.DocBak');
Ext.extend(wcm.DocBak, wcm.CMSObj, {
});

Ext.ns('wcm.DocRecycles', 'wcm.DocRecycle');
WCMConstants.OBJ_TYPE_DOCRECYCLE = 'DocRecycle';
wcm.DocRecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCRECYCLE;
wcm.DocRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.DocRecycles, wcm.CMSObjs, {
});
wcm.DocRecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCRECYCLE;
wcm.DocRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_DOCRECYCLE, 'wcm.DocRecycle');
Ext.extend(wcm.DocRecycle, wcm.CMSObj, {
});

Ext.ns('wcm.Documents', 'wcm.Document', 'wcm.ChnlDoc', 'wcm.ChnlDocs');
wcm.Documents = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCUMENT;
wcm.Documents.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Documents, wcm.CMSObjs, {
getDocIds : function(){
return this.getIds();
}
});
wcm.Document = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_DOCUMENT;
wcm.Document.superclass.constructor.call(this, _config, _context);
var arrRights ={
"view":30,
"new":31,
"edit":32,
"trash":33,
"detail":34,
"preview":38,
"publish":39
};
for(rightName in arrRights){
var cameRightName = rightName.camelize();
var nRightIndex = parseInt(arrRights[rightName], 10);
this['can'+cameRightName] = function(nRightIndex){
return wcm.AuthServer.hasRight(this.right, nRightIndex);
}.bind(this, nRightIndex);
}
this.addEvents(['preview', 'afterpreview', 'publish', 'afterpublish']);
}
CMSObj.register(WCMConstants.OBJ_TYPE_DOCUMENT, 'wcm.Document');
Ext.extend(wcm.Document, wcm.CMSObj, {
getDocId : function(){
return this.getId();
},
getRecId : function(){
return 0;
},
getDocIds : function(){
return [this.getDocId()];
},
getRelIds : function(){
return [this.getRecId()];
},
getChannelId : function(){
return this.getPropertyAsInt('DocChannel', 0);
}
});
wcm.ChnlDocs = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHNLDOC;
wcm.ChnlDocs.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.ChnlDocs, wcm.CMSObjs, {
getDocIds : function(){
var rstIds = [];
for(var i=0,n=this.m_objs.length;i<n;i++){
rstIds.push(this.m_objs[i].getDocId());
}
return rstIds;
},
getRelIds : function(){
return this.getIds();
}
});
wcm.ChnlDoc = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_CHNLDOC;
wcm.ChnlDoc.superclass.constructor.call(this, _config, _context);
}
CMSObj.register(WCMConstants.OBJ_TYPE_CHNLDOC, 'wcm.ChnlDoc');
Ext.extend(wcm.ChnlDoc, wcm.Document, {
getDocId : function(){
return this.getPropertyAsInt("DocId", 0);
},
getRecId : function(){
return this.getId();
},
getCurrChannelId : function(){
return this.getPropertyAsInt('ChnlId', 0);
}
});

Ext.ns('wcm.Flows', 'wcm.Flow');
WCMConstants.OBJ_TYPE_FLOW = 'flow';
wcm.Flows = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_FLOW;
wcm.Flows.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Flows, wcm.CMSObjs, {
});
wcm.Flow = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_FLOW;
wcm.Flow.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_FLOW, 'wcm.Flow');
Ext.extend(wcm.Flow, wcm.CMSObj, {
getIntType : function(){
return 401;
}
});

Ext.ns('wcm.IFlowContents', 'wcm.IFlowContent');
WCMConstants.OBJ_TYPE_IFLOWCONTENT = 'IFlowContent';
wcm.IFlowContents = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_IFLOWCONTENT;
wcm.IFlowContents.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.IFlowContents, wcm.CMSObjs, {
});
wcm.IFlowContent = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_IFLOWCONTENT;
wcm.IFlowContent.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_IFLOWCONTENT, 'wcm.IFlowContent');
Ext.extend(wcm.IFlowContent, wcm.CMSObj, {
getId : function(){
return this.getPropertyAsInt('DocId', 0);
}
});

Ext.ns('wcm.InfoviewDocs', 'wcm.InfoviewDoc');
WCMConstants.OBJ_TYPE_INFOVIEWDOC = 'infoviewdoc';
wcm.InfoviewDocs = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_INFOVIEWDOC;
wcm.InfoviewDocs.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.InfoviewDocs, wcm.CMSObjs, {
});
wcm.InfoviewDoc = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_INFOVIEWDOC;
wcm.InfoviewDoc.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_INFOVIEWDOC, 'wcm.InfoviewDoc');
Ext.extend(wcm.InfoviewDoc, wcm.CMSObj, {
});

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

Ext.ns('wcm.MetaViewDatas', 'wcm.MetaViewData');
WCMConstants.OBJ_TYPE_METAVIEWDATA = 'MetaViewData';
wcm.MetaViewDatas = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METAVIEWDATA;
wcm.MetaViewDatas.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.MetaViewDatas, wcm.CMSObjs, {
});
wcm.MetaViewData = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_METAVIEWDATA;
wcm.MetaViewData.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_METAVIEWDATA, 'wcm.MetaViewData');
Ext.extend(wcm.MetaViewData, wcm.CMSObj, {
});

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

Ext.ns('wcm.photos', 'wcm.photo');
WCMConstants.OBJ_TYPE_PHOTO = 'photo';
wcm.photos = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PHOTO;
wcm.photos.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.photos, wcm.CMSObjs, {
});
wcm.photo = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PHOTO;
wcm.photo.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_PHOTO, 'wcm.photo');
Ext.extend(wcm.photo, wcm.CMSObj, {
getDocId : function(){
return this.getPropertyAsInt("DocId", 0);
}
});

Ext.ns('wcm.photorecycles', 'wcm.photorecycle');
WCMConstants.OBJ_TYPE_PHOTORECYCLE = 'photorecycle';
wcm.photorecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PHOTORECYCLE;
wcm.photorecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.photorecycles, wcm.CMSObjs, {
});
wcm.photorecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PHOTORECYCLE;
wcm.photorecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_PHOTORECYCLE, 'wcm.photorecycle');
Ext.extend(wcm.photorecycle, wcm.CMSObj, {
});

Ext.ns('wcm.PublishDistributions', 'wcm.PublishDistribution');
WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION = 'PublishDistribution';
wcm.PublishDistributions = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION;
wcm.PublishDistributions.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.PublishDistributions, wcm.CMSObjs, {
});
wcm.PublishDistribution = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION;
wcm.PublishDistribution.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_PUBLISHDISTRIBUTION, 'wcm.PublishDistribution');
Ext.extend(wcm.PublishDistribution, wcm.CMSObj, {
});

Ext.ns('wcm.Regions', 'wcm.Region');
WCMConstants.OBJ_TYPE_REGION = 'region';
wcm.Flows = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_FLOW;
wcm.Regions.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Regions, wcm.CMSObjs, {
});
wcm.Region = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_REGION;
wcm.Region.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_REGION, 'wcm.Region');
Ext.extend(wcm.Region, wcm.CMSObj, {
});

Ext.ns('wcm.Replaces', 'wcm.Replace');
WCMConstants.OBJ_TYPE_REPLACE = 'Replace';
wcm.Replaces = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_REPLACE;
wcm.Replaces.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Replaces, wcm.CMSObjs, {
});
wcm.Replace = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_REPLACE;
wcm.Replace.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_REPLACE, 'wcm.Replace');
Ext.extend(wcm.Replace, wcm.CMSObj, {
getIntType : function(){
return 105;
}
});

Ext.ns('wcm.SiteRecycles', 'wcm.SiteRecycle');
WCMConstants.OBJ_TYPE_SITERECYCLE = 'SiteRecycle';
wcm.SiteRecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_SITERECYCLE;
wcm.SiteRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.SiteRecycles, wcm.CMSObjs, {
});
wcm.SiteRecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_SITERECYCLE;
wcm.SiteRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_SITERECYCLE, 'wcm.SiteRecycle');
Ext.extend(wcm.SiteRecycle, wcm.CMSObj, {
});

Ext.ns('wcm.Templates', 'wcm.Template');
WCMConstants.OBJ_TYPE_TEMPLATE = 'template';
wcm.Templates = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
wcm.Templates.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Templates, wcm.CMSObjs, {
});
wcm.Template = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
wcm.Template.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_TEMPLATE, 'wcm.Template');
Ext.extend(wcm.Template, wcm.CMSObj, {
getIntType : function(){
return 102;
}
});

Ext.ns('wcm.TemplateAgrs', 'wcm.TemplateArg');
WCMConstants.OBJ_TYPE_TEMPLATEARG = 'TemplateArg';
wcm.Templates = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
wcm.Templates.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Templates, wcm.CMSObjs, {
});
wcm.Template = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
wcm.Template.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_TEMPLATE, 'wcm.Template');
Ext.extend(wcm.Template, wcm.CMSObj, {
});

Ext.ns('wcm.TRSServers', 'wcm.TRSServer');
WCMConstants.OBJ_TYPE_TRSSERVER = 'trsServer';
wcm.TRSServers = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TRSSERVER;
wcm.TRSServers.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.TRSServers, wcm.CMSObjs, {
});
wcm.TRSServer = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TRSSERVER;
wcm.TRSServer.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_TRSSERVER, 'wcm.TRSServer');
Ext.extend(wcm.TRSServer, wcm.CMSObj, {
});

Ext.ns('wcm.Videos', 'wcm.Video');
WCMConstants.OBJ_TYPE_VIDEO = 'video';
wcm.Videos = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_VIDEO;
wcm.Videos.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Videos, wcm.CMSObjs, {
});
wcm.Video = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_VIDEO;
wcm.Video.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_VIDEO, 'wcm.Video');
Ext.extend(wcm.Video, wcm.CMSObj, {
});

Ext.ns('wcm.VideoRecycles', 'wcm.VideoRecycle');
WCMConstants.OBJ_TYPE_VIDEORECYCLE = 'videorecycle';
wcm.VideoRecycles = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_VIDEORECYCLE;
wcm.VideoRecycles.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.VideoRecycles, wcm.CMSObjs, {
});
wcm.VideoRecycle = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_VIDEORECYCLE;
wcm.VideoRecycle.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_VIDEORECYCLE, 'wcm.VideoRecycle');
Ext.extend(wcm.VideoRecycle, wcm.CMSObj, {
});

Ext.ns('wcm.Watermarks', 'wcm.Watermark');
WCMConstants.OBJ_TYPE_WATERMARK = 'Watermark';
wcm.Watermarks = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_WATERMARK;
wcm.Watermarks.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Watermarks, wcm.CMSObjs, {
});
wcm.Watermark = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_WATERMARK;
wcm.Watermark.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_WATERMARK, 'wcm.Watermark');
Ext.extend(wcm.Watermark, wcm.CMSObj, {
});

Ext.ns('wcm.WebSites', 'wcm.WebSite');
WCMConstants.OBJ_TYPE_WEBSITE = 'website';
wcm.WebSites = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_WEBSITE;
wcm.WebSites.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.WebSites, wcm.CMSObjs, {
});
wcm.WebSite = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_WEBSITE;
wcm.WebSite.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_WEBSITE, 'wcm.WebSite');
Ext.extend(wcm.WebSite, wcm.CMSObj, {
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter()) return;
var items = this.getArgs().items;
items.applyCfgs({
oprKey : 'quicklocatemenu',
desc : wcm.LANG['TREE_QUICKLOCATE'] || '快速定位',
iconCls : 'nav_option_locate',
cmd : function(){
try{
var treeWin = $('nav_tree').contentWindow;
treeWin.TblFunction['quickLocate']();
}catch(error){
alert(error.message);
}
}
},{
oprKey : 'individuate',
desc : wcm.LANG['TREE_INDIVIDUATE'] || '设置定制的站点',
iconCls : 'nav_option_more',
cmd : function(){
try{
var treeWin = $('nav_tree').contentWindow;
treeWin.TblFunction['moreAction']();
}catch(error){
}
}
},{
oprKey : 'refresh',
desc : wcm.LANG['TREE_REFRESH'] || '刷新',
iconCls : 'nav_option_refresh',
cmd : function(){
try{
var treeWin = $('nav_tree').contentWindow;
treeWin.TblFunction['refreshTree']();
}catch(error){
}
}
});
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.fromTree()) return;
if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
var items = this.getArgs().items;
items.applyCfgs({
oprKey : 'separate',
order : 70
},{
operItem : ['documentInChannel', 'new'],
order : 71,
desc : wcm.LANG['TREE_NEWDOC'] || '新建文档',
title : "新建文档",
oprKey : 'newDoc',
visible : function(){
var wcmEvent = this.getArgs().wcmEvent;
var context = wcmEvent.getContext();
if(context.get('sitetype') != 0) return false;
return true;
}
},{
operItem : ['channelHost', 'new'],
order : 72,
desc : wcm.LANG['TREE_NEWCHILDCHNL'] || '新建子栏目',
title : "新建子栏目...",
oprKey : 'newChildChannel'
},{
operItem : ['templateInChannel', 'new'],
order : 73,
desc : wcm.LANG['TREE_NEWTEMP'] || '新建模板',
title : "新建模板...",
oprKey : 'newChnlTemp'
},{
operItem : ['templateInChannel', 'import'],
order : 74,
desc : wcm.LANG['TREE_IMPORTTEMP'] || '导入模板 ',
title : "导入模板...",
oprKey : 'importChnlTemplate'
});
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(wcm.LANG['TREE_REPLACE_THISCHNL'] || '这个栏目', '');
});
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(this.fromTree()) return;
if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
var items = this.getArgs().items;
items.select('edit', '/', 
'preview', 'synTemplates', 'increasingpublish', 'solopublish', '/', 
'refreshpublish', 'fullypublish', 'recallpublish', 'export', '/',
'likecopy', 'move', 'pubhistory', 'docpositionset', '/',
'trash', 'region','/'
);
try{
var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISCHNL'] || '这(?:个|些)栏目(?:的)?');
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(regExp, '');
});
}catch(error){
}
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_CHNLDOC)) return;
var items = this.getArgs().items;
items.select('edit','trash', '/', 
'changestatus', 'docpositionset','detailpublish','directpublish', 'recallpublish', '/', 
'copy', 'quote', 'move','/',
'export','logo','settopdocument','settopdocumentforever','trace_document', '/',
'setright','backup','backupmgr');
try{
var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISDOC'] || '这(?:篇|些)文档(?:的)?');
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(regExp, '');
if(item.oprKey == "backup") item.desc = item.desc.replace(wcm.LANG.chnldoc_1001 || "为", "");
});
}catch(error){
}
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_INFOVIEWDOC)) return;
var items = this.getArgs().items;
items.select('edit', 'move','copy', 'quote','/',
'detailpublish','recallpublish','basicpublish','preview', 'publishinfo','/',
'trash', 'export','exportexcelselectOne','changestatus','/',
'docpositionset','setright','startflow','logo'
);
try{
var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISDOC'] || '这(?:篇|些)文档(?:的)?');
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(regExp, '');
if(item.oprKey == "backup") item.desc = item.desc.replace(wcm.LANG.infoviewdoc_1001 || "为", "");
});
}catch(error){
}
});
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_INFOVIEWDOC)) return;
wcm.CrashBoardCopy = wcm.CrashBoard;
try{
wcm.CrashBoard = $('main').contentWindow.wcm.CrashBoard;
}catch(error){
alert(error.message);
}
});
wcm.cms.menu.CMSMenu.on('click', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_INFOVIEWDOC)) return;
wcm.CrashBoard = wcm.CrashBoardCopy;
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_METAVIEWDATA)) return;
var items = this.getArgs().items;
items.select('edit', 'delete', '/',
'docpositionset', 'setright', '/',
'preview', 'basicpublish', 'detailpublish','recallpublish', '/', 
'copy', 'move', 'quote', '/',
'export', '/',
'changestatus');
try{
var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISREC'] || '这(?:条|些)记录(?:的)');
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(regExp, '');
});
}catch(error){
}
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_PHOTO)) return;
var items = this.getArgs().items;
items.select('edit','delete', '/', 
'changestatus', 'docpositionset','detailpublish','recallpublish', '/', 
'quote', 'move','/',
'setright');
try{
var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISDOC'] || '这(?:篇|些)文档(?:的)?');
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(regExp, '');
if(item.oprKey == "backup") item.desc = item.desc.replace(wcm.LANG.photo4list_1001 || "为", "");
});
}catch(error){
}
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_TEMPLATE)) return;
var items = this.getArgs().items;
items.select('edit', 'wcag2check', 'preview' );
try{
var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISTEMPLATE'] || '这(?:个|些)模板(?:的)?');
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(regExp, '');
});
}catch(error){
}
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_TRSSERVER)) return;
var items = this.getArgs().items;
items.select('pick');
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.fromTree()) return;
if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITE)) return;
var items = this.getArgs().items;
items.applyCfgs({
oprKey : 'separate',
order : 50
},{
operItem : ['websiteHost', 'new'],
order : 51,
desc : wcm.LANG['TREE_NEWCHNL'] || '新建栏目',
oprKey : 'newChannel'
},{
operItem : ['templateInSite', 'new'],
order : 52,
desc : wcm.LANG['TREE_NEWTEMP'] || '新建模板',
oprKey : 'newTemplate'
},{
operItem : ['templateInSite', 'import'],
order : 53,
desc : wcm.LANG['TREE_IMPORTTEMP'] || '导入模板',
oprKey : 'importTemplate'
});
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(wcm.LANG['TREE_REPLACE_THISSITE'] || '这个站点', '');
});
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(this.fromTree()) return;
if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITE)) return;
var items = this.getArgs().items;
items.select('edit', 'recycle', '/', 
'preview', 'synTemplates', '/', 
'increasepublish', 'homepublish', 'updatepublish', 'completepublish', '/',
'export', 'likecopy', '/', 
'keyword');
try{
var regExp = new RegExp(wcm.LANG['TREE_REPLACE_THISSITE'] || '这(?:个|些)站点(?:的)?');
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(regExp, '');
});
}catch(error){
}
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITEROOT)) return;
var items = this.getArgs().items;
var wcmEvent = this.getArgs().wcmEvent;
if(wcmEvent==null)return;
var context = wcmEvent.getContext();
if(context==null) return;
if(context.get('sitetype') == 3) return;
items.applyCfgs({
operItem : ['websiteInRoot', 'new'],
desc : wcm.LANG['TREE_NEWSITE'] || '新建站点',
oprKey : 'newSite'
},{
operItem : ['websiteInRoot', 'quicknew'],
desc : wcm.LANG['TREE_QUICKNEWSITE'] || '智能创建站点',
oprKey : 'quicknewSite'
},{
operItem : ['websiteInRoot', 'import'],
desc : wcm.LANG['TREE_IMPORTSITE'] || '从外部导入站点',
oprKey : 'importSite'
},{
oprKey : 'keywordMgr',
desc : wcm.LANG['TREE_KEYWORDMGR'] || '管理关键词',
iconCls : 'keyword',
cmd : function(event){
wcm.domain.WebSiteMgr['keyword'](event);
},
cls : function(){
if(wcm.AuthServer.isAdmin())return '';
return 'display-none';
}
},{
operItem : ['websiteInRoot', 'confphotolib'],
desc : wcm.LANG['PHOTO_LIB'] || '图片库设置',
oprKey : 'confphotolib'
},{
operItem : ['websiteInRoot', 'confvideolib'],
desc : wcm.LANG['VIDEO_PROCESS_219'] || '视频库设置',
oprKey : 'confvideolib'
},{
operItem : ['websiteInRoot', 'sample'],
desc : wcm.LANG['VIDEO_PROCESS_234'] || '样本库管理',
oprKey : 'sample'
},{
operItem : ['websiteInRoot', 'confmas'],
desc : wcm.LANG.websiteroot_101 || '推送设置',
oprKey : 'confmas'
},{
operItem : ['websiteInRoot', 'deleteFail'],
desc : wcm.LANG['VIDEO_PROCESS_223'] || '删除转码失败的视频',
oprKey : 'deleteFail'
});
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
var items = this.getArgs().items;
var wcmEvent = this.getArgs().wcmEvent;
var context = wcmEvent.getContext();
if(context.get('sitetype') != 4) return;
if(context.get('chnltype') == 11) return;
items.applyCfgs({
oprKey : 'addrecord',
desc : wcm.LANG['METAVIEWDATA_126'] || '新建记录',
title:'新建一条记录...',
iconCls : 'addrecord',
order : 71.1,
cmd : function(wcmEvent){
var obj = wcmEvent.getObj();
var params = {
DocumentId : 0,
FromEditor : 1,
SiteId:0,
ChannelId:obj.getId()
};
$openMaxWin('/wcm/app/metaviewdata/document_addedit_redirect.jsp?' + $toQueryStr(params));
},
visible : function(){
var wcmEvent = this.getArgs().wcmEvent;
var context = wcmEvent.getContext();
return wcm.AuthServer.checkRight(context.right, 31);
}
});
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.fromTree()) return;
if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
var items = this.getArgs().items;
var wcmEvent = this.getArgs().wcmEvent;
var context = wcmEvent.getContext();
if(context.get('sitetype') != 1) return;
items.applyCfgs({
operItem : ['photoInChannel', 'upload'],
order : 71.1,
desc : wcm.LANG['PHOTO_CONFIRM_61'] || '上传图片',
oprKey : 'uploadphoto'
});
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITE)) return;
var items = this.getArgs().items;
var wcmEvent = this.getArgs().wcmEvent;
var context = wcmEvent.getContext();
if(context.get('sitetype') != 2) return;
items.applyCfgs({
oprKey : 'separate',
order : 90
},{
operItem : ['videoInSite', 'live'],
order : 99,
desc : wcm.LANG['VIDEO_PROCESS_220'] || '新建视频直播',
oprKey : 'live'
});
items.applyCfgsMapping(function(item){
var desc = item.desc || '';
item.desc = desc.replace(wcm.LANG['TREE_REPLACE_THISSITE'] || '这个站点', '');
});
});

wcm.cms.menu.CMSMenu.on('beforeshow', function(){
if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
var items = this.getArgs().items;
var wcmEvent = this.getArgs().wcmEvent;
var context = wcmEvent.getContext();
if(context.get('sitetype') != 2) return;
items.applyCfgs({
operItem : ['videoInChannel', 'upload'],
order : 71.1,
desc : wcm.LANG['VIDEO_PROCESS_159'] || '新建视频',
oprKey : 'uploadVideo'
});
});

