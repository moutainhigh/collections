Ext.apply(wcm.LANG, {
WATERMARK_PROCESS_1 : "不是有效的水印图片![",
WATERMARK_PROCESS_2 : "点击查看原图\n\r",
WATERMARK_PROCESS_3 : '执行进度，请稍候...',
WATERMARK_PROCESS_4 : '正在上传文件:',
WATERMARK_PROCESS_5 : '成功上传文件.',
WATERMARK_PROCESS_6 : '与服务器交互时出错啦！',
WATERMARK_PROCESS_7 : '确定',
WATERMARK_PROCESS_8 : '个',
WATERMARK_PROCESS_9 : '水印',
WATERMARK_PROCESS_10 : '水印名称',
WATERMARK_PROCESS_11 : '上传水印',
WATERMARK_PROCESS_12 : '编辑水印',
WATERMARK_PROCESS_13 : '确实要将这',
WATERMARK_PROCESS_14 : '个水印删除吗? ',
WATERMARK_PROCESS_15 : '编辑这个水印',
WATERMARK_PROCESS_16 : '删除这个水印',
WATERMARK_PROCESS_17 : '上传新水印',
WATERMARK_PROCESS_18 : '删除这些水印',
WATERMARK_PROCESS_19 : '上传',
WATERMARK_PROCESS_20 : '请选择要删除的水印.',
WATERMARK_PROCESS_21 : '删除',
WATERMARK_PROCESS_22 : '删除这个/些水印',
WATERMARK_PROCESS_23 : '刷新',
WATERMARK_PROCESS_24 : '刷新列表',
WATERMARK_PROCESS_25 : '水印列表管理',
WATERMARK_PROCESS_26 : '显示名称',
WATERMARK_PROCESS_27 : '创建时间',
WATERMARK_PROCESS_28 : '水印名称',
WATERMARK_PROCESS_29 : '创建者',
WATERMARK_PROCESS_30 : '只支持.jpg,.gif,.jpeg,.png,.bmp格式图片'
});

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

(function(){
var sName = wcm.LANG['WATERMARK'] || '水印';
wcm.PageOper.registerPanels({
watermarkInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
watermarkInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
watermarkInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
watermark : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_watermark&methodname=jFindbyid'
},
watermarks : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

